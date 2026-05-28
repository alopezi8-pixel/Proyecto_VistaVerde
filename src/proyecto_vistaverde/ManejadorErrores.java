/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_vistaverde;

/**
 *
 * @author andre_8v8gtn3
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManejadorErrores {
    
        private static final String ARCHIVO_LOG = "errores_vistaverde.log";

    public static void registrar(Exception e, String contexto) {
        try (PrintStream ps = new PrintStream(
                new FileOutputStream(ARCHIVO_LOG, true))) {

            String fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            ps.println("=== ERROR: " + fecha + " ===");
            ps.println("Contexto: " + contexto);
            ps.println("Mensaje: " + e.getMessage());
            e.printStackTrace(ps);
            ps.println();

        } catch (IOException ex) {
            System.err.println("No se pudo escribir el log: " + ex.getMessage());
        }
    }

    public static void mostrarMensaje(javax.swing.JLabel label, String mensaje) {
        label.setForeground(java.awt.Color.RED);
        label.setText(mensaje);
    }

    public static void mostrarExito(javax.swing.JLabel label, String mensaje) {
        label.setForeground(new java.awt.Color(0, 130, 0));
        label.setText(mensaje);
    }
    
}
