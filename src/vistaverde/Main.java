package vistaverde;

import vistaverde.ui.PantallaLogin;
import vistaverde.ui.Tema;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada principal del Sistema de Administracion de Condominio Vista Verde.
 *
 * Universidad Mariano Galvez de Guatemala
 * Facultad de Ingenieria en Sistemas de Informacion
 * Programacion I — 2026
 *
 * Para ejecutar en NetBeans:
 *   1. Clic derecho en el proyecto → Properties → Run
 *   2. Main Class: vistaverde.Main
 *   3. Ejecutar con F6 o boton Run
 *
 * Credenciales de acceso:
 *   Usuario:    iusr_vistaverde
 *   Contrasena: R3sidencial2026%
 */
public class Main {

    public static void main(String[] args) {
        // Aplicar tema visual antes de crear cualquier ventana
        Tema.aplicarLookAndFeel();

        SwingUtilities.invokeLater(() -> {
            new PantallaLogin().setVisible(true);
        });
    }
}
