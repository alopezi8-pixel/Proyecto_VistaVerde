package vistaverde.ui;

import vistaverde.logic.Persistencia;
import vistaverde.model.Condominio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Pantalla 2 — Menu Principal del sistema.
 * Contiene la instancia compartida del Condominio (Singleton estatico).
 */
public class PantallaInicio extends JFrame {

    // ===== INSTANCIA COMPARTIDA DEL CONDOMINIO =====
    private static Condominio condominio = Persistencia.cargar();

    public static Condominio getCondominio() { return condominio; }

    public static void guardarDatos() { Persistencia.guardar(condominio); }
    // =================================================

    public PantallaInicio() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Vista Verde - Menu Principal");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (Tema.confirmar(PantallaInicio.this,
                        "Desea salir del sistema?\nLos datos seran guardados automaticamente.")) {
                    guardarDatos();
                    System.exit(0);
                }
            }
        });

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Tema.COLOR_FONDO);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Tema.COLOR_HEADER);
        header.setPreferredSize(new Dimension(0, 75));
        header.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        JLabel lblLogo = new JLabel("  Condominio Vista Verde");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);

        LocalDate hoy = LocalDate.now();
        String fecha = hoy.format(DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "GT")));
        fecha = fecha.substring(0, 1).toUpperCase() + fecha.substring(1);

        JPanel panelRight = new JPanel(new GridLayout(2, 1));
        panelRight.setOpaque(false);
        JLabel lblBienvenida = new JLabel("Bienvenido, Administrador", SwingConstants.RIGHT);
        lblBienvenida.setFont(Tema.FUENTE_NORMAL);
        lblBienvenida.setForeground(new Color(200, 230, 255));
        JLabel lblFecha = new JLabel(fecha, SwingConstants.RIGHT);
        lblFecha.setFont(Tema.FUENTE_NEGRITA);
        lblFecha.setForeground(Tema.COLOR_ACENTO);
        panelRight.add(lblBienvenida);
        panelRight.add(lblFecha);

        header.add(lblLogo, BorderLayout.WEST);
        header.add(panelRight, BorderLayout.EAST);

        // --- GRID DE MODULOS ---
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(Tema.COLOR_FONDO);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 28, 16, 28));

        JLabel lblModulos = new JLabel("Modulos del Sistema");
        lblModulos.setFont(Tema.FUENTE_SUBTITULO);
        lblModulos.setForeground(Tema.COLOR_PRIMARIO);
        lblModulos.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        JPanel grid = new JPanel(new GridLayout(2, 3, 14, 14));
        grid.setBackground(Tema.COLOR_FONDO);

        grid.add(mkModulo("PROPIETARIO", "Registrar\nPropietario",  Tema.COLOR_PRIMARIO,            "propietario"));
        grid.add(mkModulo("PAGO",        "Registrar\nPago",         Tema.COLOR_SECUNDARIO,          "pago"));
        grid.add(mkModulo("CUOTA",       "Configurar\nCuota",       new Color(100, 60, 180),        "cuota"));
        grid.add(mkModulo("CUENTA",      "Estado de\nCuenta",       new Color(0,  140, 186),        "estado"));
        grid.add(mkModulo("REPORTE",     "Reporte\nGeneral",        new Color(200, 100, 0),         "reporte"));
        grid.add(mkModulo("MOROSAS",     "Casas\nMorosas",          Tema.COLOR_PELIGRO,             "morosas"));

        panelCentro.add(lblModulos, BorderLayout.NORTH);
        panelCentro.add(grid,       BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(new Color(228, 233, 238));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Tema.COLOR_BORDE));
        JLabel lblFooter = new JLabel("Universidad Mariano Galvez de Guatemala  -  Programacion I  -  2026   ");
        lblFooter.setFont(Tema.FUENTE_PEQUENA);
        lblFooter.setForeground(Tema.COLOR_TEXTO_SUAVE);
        footer.add(lblFooter);

        main.add(header,      BorderLayout.NORTH);
        main.add(panelCentro, BorderLayout.CENTER);
        main.add(footer,      BorderLayout.SOUTH);
        setContentPane(main);
    }

    /** Crea un panel/boton de modulo con color solido y hover. */
    private JPanel mkModulo(String codigo, String texto, Color color, String accion) {
        JButton btn = new JButton();
        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));

        // Codigo de modulo (texto grande)
        JLabel lblCodigo = new JLabel(codigo, SwingConstants.CENTER);
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCodigo.setForeground(new Color(255, 255, 255, 160));
        lblCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Texto descriptivo (puede ser dos lineas)
        String[] lineas = texto.split("\n");
        JLabel lbl1 = new JLabel(lineas[0], SwingConstants.CENTER);
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl1.setForeground(Color.WHITE);
        lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.add(Box.createVerticalGlue());
        btn.add(lblCodigo);
        btn.add(Box.createVerticalStrut(6));
        btn.add(lbl1);
        if (lineas.length > 1) {
            JLabel lbl2 = new JLabel(lineas[1], SwingConstants.CENTER);
            lbl2.setFont(new Font("Segoe UI", Font.BOLD, 15));
            lbl2.setForeground(Color.WHITE);
            lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.add(lbl2);
        }
        btn.add(Box.createVerticalGlue());

        Color oscuro = color.darker();
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(oscuro); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(color); }
        });
        btn.addActionListener(e -> abrirPantalla(accion));

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.add(btn);
        return wrap;
    }

    private void abrirPantalla(String accion) {
        JFrame p = null;
        switch (accion) {
            case "propietario": p = new PantallaRegistroPropietario(); break;
            case "pago":        p = new PantallaRegistroPago();        break;
            case "cuota":       p = new PantallaConfiguracionCuota();  break;
            case "estado":      p = new PantallaEstadoCuenta();        break;
            case "reporte":     p = new PantallaReporteGeneral();      break;
            case "morosas":     p = new PantallaCasasMorosas();        break;
            default: break;
        }
        if (p != null) p.setVisible(true);
    }
}
