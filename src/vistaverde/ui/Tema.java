package vistaverde.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Clase de tema visual del sistema Vista Verde.
 * Define colores, fuentes y metodos factory de componentes Swing.
 * PUNTO EXTRA +2: diseno con tema propio (FlatLaf si esta disponible, Nimbus de fallback).
 */
public class Tema {

    // === PALETA ===
    public static final Color COLOR_PRIMARIO    = new Color(20,  82,  120);
    public static final Color COLOR_SECUNDARIO  = new Color(39,  174,  96);
    public static final Color COLOR_ACENTO      = new Color(241, 196,  15);
    public static final Color COLOR_FONDO       = new Color(245, 248, 252);
    public static final Color COLOR_FONDO_PANEL = Color.WHITE;
    public static final Color COLOR_TEXTO       = new Color(33,   37,  41);
    public static final Color COLOR_TEXTO_SUAVE = new Color(108, 117, 125);
    public static final Color COLOR_PELIGRO     = new Color(220,  53,  69);
    public static final Color COLOR_BORDE       = new Color(206, 212, 218);
    public static final Color COLOR_HEADER      = new Color(13,   61,  92);

    // === FUENTES ===
    public static final Font FUENTE_TITULO    = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD,  16);
    public static final Font FUENTE_NORMAL    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_NEGRITA   = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FUENTE_PEQUENA   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FUENTE_BOTON     = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FUENTE_LOGO      = new Font("Segoe UI", Font.BOLD,  26);
    public static final Font FUENTE_MONEDA    = new Font("Segoe UI", Font.BOLD,  18);

    /**
     * Aplica FlatLaf si esta en classpath, o Nimbus como fallback.
     * Siempre llama esto en el main antes de crear ventanas.
     */
    public static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e1) {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e2) {
                // Usar look and feel por defecto del sistema
            }
        }
        UIManager.put("Button.arc",              8);
        UIManager.put("Component.arc",           8);
        UIManager.put("TextComponent.arc",       6);
        UIManager.put("ScrollBar.width",         8);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines",   false);
    }

    // ===== FACTORY METHODS =====

    /** Boton azul primario. */
    public static JButton crearBotonPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BOTON);
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(170, 36));
        return btn;
    }

    /** Boton verde secundario. */
    public static JButton crearBotonSecundario(String texto) {
        JButton btn = crearBotonPrimario(texto);
        btn.setBackground(COLOR_SECUNDARIO);
        return btn;
    }

    /** Boton rojo de peligro / limpiar. */
    public static JButton crearBotonPeligro(String texto) {
        JButton btn = crearBotonPrimario(texto);
        btn.setBackground(COLOR_PELIGRO);
        return btn;
    }

    /** Campo de texto con borde redondeado. */
    public static JTextField crearTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FUENTE_NORMAL);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        tf.setPreferredSize(new Dimension(200, 34));
        return tf;
    }

    /** ComboBox con los 30 numeros de casa. */
    public static JComboBox<Integer> crearComboCasas() {
        Integer[] casas = new Integer[30];
        for (int i = 0; i < 30; i++) casas[i] = i + 1;
        JComboBox<Integer> combo = new JComboBox<>(casas);
        combo.setFont(FUENTE_NORMAL);
        combo.setPreferredSize(new Dimension(90, 34));
        return combo;
    }

    /** Panel de cabecera azul oscuro con titulo. */
    public static JPanel crearPanelHeader(String titulo) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_HEADER);
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblLogo = new JLabel("  Condominio Vista Verde");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblLogo.setForeground(Color.WHITE);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FUENTE_SUBTITULO);
        lblTitulo.setForeground(new Color(173, 216, 230));
        lblTitulo.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(lblLogo,  BorderLayout.WEST);
        header.add(lblTitulo, BorderLayout.EAST);
        return header;
    }

    /** Panel con borde titulado. */
    public static JPanel crearPanelConBorde(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_FONDO_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(COLOR_BORDE), titulo,
                TitledBorder.LEFT, TitledBorder.TOP, FUENTE_NEGRITA, COLOR_PRIMARIO),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return panel;
    }

    /** Configura el estilo visual de un JTable. */
    public static void estilizarTabla(JTable tabla) {
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(173, 216, 230));
        tabla.setSelectionForeground(COLOR_TEXTO);
        tabla.setGridColor(COLOR_BORDE);
        tabla.getTableHeader().setFont(FUENTE_NEGRITA);
        tabla.getTableHeader().setBackground(COLOR_PRIMARIO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setFillsViewportHeight(true);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
    }

    // ===== DIALOGOS =====

    public static void mostrarError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarExito(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarAdvertencia(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean confirmar(Component parent, String msg) {
        return JOptionPane.showConfirmDialog(parent, msg, "Confirmar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
