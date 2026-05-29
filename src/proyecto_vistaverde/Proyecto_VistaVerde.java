package proyecto_vistaverde;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Proyecto_VistaVerde {
    public static void main(String[] args) {
        
        // Aplicar FlatLaf - Punto extra +2 diseño
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            ManejadorErrores.registrar(e, "Main - FlatLaf");
        }
        
        // Cargar datos guardados
        Condominio condominio = Persistencia.cargar();
        Login.setCondominio(condominio);
        
        // Abrir Login
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
