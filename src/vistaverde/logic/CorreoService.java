package vistaverde.logic;

import vistaverde.model.Pago;
import vistaverde.model.Propietario;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Servicio de envio de correos electronicos al propietario.
 * PUNTO EXTRA +2: comprobante de pago por correo.
 *
 * Compila SIN el jar de javax.mail (usa reflexion en tiempo de ejecucion).
 * Para activar el envio real:
 *   1. Descarga jakarta.mail-2.x.x.jar desde https://eclipse-ee4j.github.io/mail/
 *   2. Copialo a la carpeta /lib del proyecto
 *   3. En NetBeans: clic derecho en Libraries -> Add JAR/Folder
 *   4. Cambia HABILITADO = true y pon tus credenciales de Gmail abajo
 *
 * NOTA Gmail: activa "Contrasenas de aplicacion" en tu cuenta Google
 * y usa esa contrasena de 16 caracteres en GMAIL_APP_PASSWORD.
 */
public class CorreoService {

    // ===== CONFIGURAR AQUI =====
    private static final String  GMAIL_USER        = "condominio.vistaverde@gmail.com";
    private static final String  GMAIL_APP_PASSWORD = "xxxx xxxx xxxx xxxx";
    private static       boolean HABILITADO         = false;
    // ===========================

    public static boolean isHabilitado()          { return HABILITADO; }
    public static void    setHabilitado(boolean v) { HABILITADO = v; }

    /**
     * Envia comprobante de pago al correo del propietario.
     * @return true si se envio correctamente.
     */
    public static boolean enviarComprobantePago(Propietario propietario, Pago pago) {
        if (!HABILITADO) {
            System.out.println("[Correo] Servicio deshabilitado.");
            return false;
        }
        try {
            enviarViaSmtp(propietario, pago);
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("[Correo] Falta el jar de javax.mail en /lib. Descargalo de eclipse-ee4j.github.io/mail/");
            return false;
        } catch (Exception e) {
            System.err.println("[Correo] Error al enviar correo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Envio via reflexion para no requerir javax.mail en tiempo de compilacion.
     * Si el jar no esta en classpath lanza ClassNotFoundException.
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    private static void enviarViaSmtp(Propietario propietario, Pago pago) throws Exception {

        // Verificar que Session exista
        Class sessionClass    = Class.forName("javax.mail.Session");
        Class authenticator   = Class.forName("javax.mail.Authenticator");
        Class mimeClass       = Class.forName("javax.mail.internet.MimeMessage");
        Class addrClass       = Class.forName("javax.mail.internet.InternetAddress");
        Class msgClass        = Class.forName("javax.mail.Message");
        Class transportClass  = Class.forName("javax.mail.Transport");
        Class passAuthClass   = Class.forName("javax.mail.PasswordAuthentication");
        Class recTypeClass    = Class.forName("javax.mail.Message$RecipientType");

        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            "smtp.gmail.com");
        props.put("mail.smtp.port",            "587");
        props.put("mail.smtp.ssl.trust",       "smtp.gmail.com");

        // PasswordAuthentication instance
        Object passAuth = passAuthClass
            .getConstructor(String.class, String.class)
            .newInstance(GMAIL_USER, GMAIL_APP_PASSWORD);

        // Authenticator anonimo via proxy dinamico
        Object auth = java.lang.reflect.Proxy.newProxyInstance(
            authenticator.getClassLoader(),
            new Class[]{ authenticator },
            (proxy, method, args) -> {
                if ("getPasswordAuthentication".equals(method.getName())) {
                    return passAuth;
                }
                return null;
            }
        );

        // Session.getInstance(props, auth)
        Method getInstance = sessionClass.getMethod("getInstance", Properties.class, authenticator);
        Object session = getInstance.invoke(null, props, auth);

        // new MimeMessage(session)
        Object message = mimeClass.getConstructor(sessionClass).newInstance(session);

        // message.setFrom(new InternetAddress(from, name))
        Object fromAddr = addrClass.getConstructor(String.class, String.class)
            .newInstance(GMAIL_USER, "Condominio Vista Verde");
        mimeClass.getMethod("setFrom", Class.forName("javax.mail.Address")).invoke(message, fromAddr);

        // InternetAddress.parse(to)
        Object[] toAddrs = (Object[]) addrClass.getMethod("parse", String.class)
            .invoke(null, propietario.getCorreoElectronico());

        // RecipientType.TO
        Object toType = recTypeClass.getField("TO").get(null);
        mimeClass.getMethod("setRecipients", recTypeClass, Class.forName("[Ljavax.mail.Address;"))
            .invoke(message, toType, toAddrs);

        // setSubject
        mimeClass.getMethod("setSubject", String.class).invoke(message, "Comprobante de Pago - Vista Verde");

        // setContent
        mimeClass.getMethod("setContent", Object.class, String.class)
            .invoke(message, buildHtml(propietario, pago), "text/html; charset=utf-8");

        // Transport.send(message)
        Method send = transportClass.getMethod("send", Class.forName("javax.mail.Message"));
        send.invoke(null, message);

        System.out.println("[Correo] Enviado a: " + propietario.getCorreoElectronico());
    }

    private static String buildHtml(Propietario p, Pago pg) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'></head>"
            + "<body style='font-family:Arial,sans-serif;color:#333'>"
            + "<div style='max-width:600px;margin:auto;border:1px solid #ccc;border-radius:8px;overflow:hidden'>"
            + "<div style='background:#0d3d5c;color:white;padding:20px;text-align:center'>"
            + "<h2 style='margin:0'>Condominio Vista Verde</h2>"
            + "<p style='margin:5px 0'>Comprobante de Pago de Mantenimiento</p></div>"
            + "<div style='padding:20px'>"
            + "<p>Estimado/a <strong>" + p.getNombreCompleto() + "</strong>,</p>"
            + "<p>Su pago ha sido registrado exitosamente.</p>"
            + "<table style='width:100%;border-collapse:collapse;margin:20px 0'>"
            + tr("#eaf2ff", "Numero de Casa",  "Casa " + p.getNumeroCasa())
            + tr("#ffffff", "Mes de Pago",     pg.getNombreMes() + " " + pg.getAnio())
            + tr("#eaf2ff", "Monto Pagado",    "Q" + String.format("%.2f", pg.getMonto()))
            + tr("#ffffff", "Estado",          pg.getEstado())
            + "</table>"
            + "<p>Gracias por mantener sus pagos al dia.</p></div>"
            + "<div style='background:#f5f5f5;padding:10px;text-align:center;font-size:.8em;color:#999'>"
            + "Condominio Vista Verde | Guatemala, 2026</div></div></body></html>";
    }

    private static String tr(String bg, String label, String value) {
        return "<tr style='background:" + bg + "'>"
            + "<td style='padding:10px;border:1px solid #ccc'><strong>" + label + "</strong></td>"
            + "<td style='padding:10px;border:1px solid #ccc'>" + value + "</td></tr>";
    }
}
