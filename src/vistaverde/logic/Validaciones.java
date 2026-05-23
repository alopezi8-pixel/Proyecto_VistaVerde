package vistaverde.logic;

import java.util.regex.Pattern;

/**
 * Métodos de validación reutilizables en todo el sistema.
 */
public class Validaciones {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern TELEFONO_PATTERN =
        Pattern.compile("^[2-9][0-9]{7}$");

    /** Valida formato de correo electronico. */
    public static boolean esCorreoValido(String correo) {
        if (correo == null || correo.trim().isEmpty()) return false;
        return EMAIL_PATTERN.matcher(correo.trim()).matches();
    }

    /** Valida telefono guatemalteco de 8 digitos. */
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) return false;
        return TELEFONO_PATTERN.matcher(telefono.trim()).matches();
    }

    /** Valida que el nombre tenga al menos dos palabras. */
    public static boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        return nombre.trim().split("\\s+").length >= 2;
    }

    /** Valida numero de casa entre 1 y 30. */
    public static boolean esCasaValida(int numeroCasa) {
        return numeroCasa >= 1 && numeroCasa <= 30;
    }

    /** Valida mes entre 1 y 12. */
    public static boolean esMesValido(int mes) {
        return mes >= 1 && mes <= 12;
    }

    /** Valida credenciales del administrador. */
    public static boolean esCredencialValida(String usuario, String contrasena) {
        return "iusr_vistaverde".equals(usuario) && "R3sidencial2026%".equals(contrasena);
    }

    /** Valida que el monto sea positivo. */
    public static boolean esMontoValido(double monto) {
        return monto > 0;
    }
}
