/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_vistaverde;

/**
 *
 * @author andre_8v8gtn3
 */
public class Proyecto_VistaVerde {
    public static void main(String[] args) {
        
        Condominio condominio = Persistencia.cargar();
        
        
        Login.setCondominio(condominio);
        
        // Abrir la pantalla de Login
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
