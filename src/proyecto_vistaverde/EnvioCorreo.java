/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_vistaverde;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EnvioCorreo {
 
    private static final String CORREO_REMITENTE = "andreslillescas@gmail.com";
    private static final String CONTRASENA       = "mzql oqkq ypjl mgxd";


    public static boolean enviarComprobante(String destinatario,
                                             String nombrePropietario,
                                             int numeroCasa,
                                             String mes,
                                             int anio,
                                             double monto) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth",            "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host",            "smtp.gmail.com");
            props.put("mail.smtp.port",            "587");
            props.put("mail.smtp.ssl.trust",       "smtp.gmail.com");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(CORREO_REMITENTE, CONTRASENA);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(CORREO_REMITENTE, "Condominio Vista Verde"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Comprobante de Pago - Condominio Vista Verde");
            message.setContent(construirCorreo(nombrePropietario, numeroCasa, mes, anio, monto),
                "text/html; charset=utf-8");

            Transport.send(message);
            return true;

        } catch (MessagingException | UnsupportedEncodingException e) {
            ManejadorErrores.registrar(e, "EnvioCorreo - enviarComprobante");
            return false;
        }
    }

    private static String construirCorreo(String nombre, int casa,
                                           String mes, int anio, double monto) {
        return "<html><body style='font-family:Arial;'>"
            + "<h2 style='color:#0d3d5c;'>Condominio Vista Verde</h2>"
            + "<p>Estimado/a <strong>" + nombre + "</strong>,</p>"
            + "<p>Su pago ha sido registrado exitosamente.</p>"
            + "<table border='1' cellpadding='8' style='border-collapse:collapse;'>"
            + "<tr><td><strong>Casa</strong></td><td>" + casa + "</td></tr>"
            + "<tr><td><strong>Mes</strong></td><td>" + mes + " " + anio + "</td></tr>"
            + "<tr><td><strong>Monto</strong></td><td>Q" + String.format("%.2f", monto) + "</td></tr>"
            + "<tr><td><strong>Estado</strong></td><td>Pagado</td></tr>"
            + "</table>"
            + "<p>Gracias por mantener sus pagos al dia.</p>"
            + "<p style='color:#888;font-size:12px;'>Condominio Vista Verde - Guatemala 2026</p>"
            + "</body></html>";
    }  
}
