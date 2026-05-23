package vistaverde.ui;

import vistaverde.logic.Validaciones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pantalla 1 — Login del Administrador.
 */
public class PantallaLogin extends JFrame {

    private JTextField     txtUsuario;
    private JPasswordField txtContrasena;
    private JLabel         lblEstado;

    public PantallaLogin() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Vista Verde - Acceso al Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Tema.COLOR_FONDO);

        // --- PANEL SUPERIOR (logo) ---
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelTop.setBackground(Tema.COLOR_HEADER);
        panelTop.setPreferredSize(new Dimension(0, 160));
        panelTop.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblIcono = new JLabel("[VV]", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblIcono.setForeground(Tema.COLOR_ACENTO);
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNombre = new JLabel("Vista Verde", SwingConstants.CENTER);
        lblNombre.setFont(Tema.FUENTE_LOGO);
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Sistema de Administracion", SwingConstants.CENTER);
        lblSub.setFont(Tema.FUENTE_NORMAL);
        lblSub.setForeground(new Color(173, 216, 230));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTop.add(Box.createVerticalGlue());
        panelTop.add(lblIcono);
        panelTop.add(Box.createVerticalStrut(6));
        panelTop.add(lblNombre);
        panelTop.add(Box.createVerticalStrut(4));
        panelTop.add(lblSub);
        panelTop.add(Box.createVerticalGlue());

        // --- PANEL FORMULARIO ---
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(Tema.COLOR_FONDO_PANEL);
        panelForm.setBorder(BorderFactory.createEmptyBorder(28, 40, 28, 40));

        JLabel lblUsuarioTxt = new JLabel("Usuario");
        lblUsuarioTxt.setFont(Tema.FUENTE_NEGRITA);
        lblUsuarioTxt.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsuario = Tema.crearTextField();
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        txtUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPassTxt = new JLabel("Contrasena");
        lblPassTxt.setFont(Tema.FUENTE_NEGRITA);
        lblPassTxt.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtContrasena = new JPasswordField();
        txtContrasena.setFont(Tema.FUENTE_NORMAL);
        txtContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        txtContrasena.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Tema.COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        lblEstado = new JLabel(" ");
        lblEstado.setFont(Tema.FUENTE_PEQUENA);
        lblEstado.setForeground(Tema.COLOR_PELIGRO);
        lblEstado.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnIngresar = Tema.crearBotonPrimario("Ingresar al Sistema");
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnIngresar.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelForm.add(lblUsuarioTxt);
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtUsuario);
        panelForm.add(Box.createVerticalStrut(14));
        panelForm.add(lblPassTxt);
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtContrasena);
        panelForm.add(Box.createVerticalStrut(8));
        panelForm.add(lblEstado);
        panelForm.add(Box.createVerticalStrut(14));
        panelForm.add(btnIngresar);

        // --- FOOTER ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(Tema.COLOR_FONDO);
        JLabel lblFooter = new JLabel("UMG - Programacion I - 2026");
        lblFooter.setFont(Tema.FUENTE_PEQUENA);
        lblFooter.setForeground(Tema.COLOR_TEXTO_SUAVE);
        footer.add(lblFooter);

        panelPrincipal.add(panelTop,   BorderLayout.NORTH);
        panelPrincipal.add(panelForm,  BorderLayout.CENTER);
        panelPrincipal.add(footer,     BorderLayout.SOUTH);
        setContentPane(panelPrincipal);

        // --- EVENTOS ---
        btnIngresar.addActionListener(e -> intentarLogin());
        txtContrasena.addActionListener(e -> intentarLogin());
        getRootPane().setDefaultButton(btnIngresar);
    }

    private void intentarLogin() {
        String usuario    = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            lblEstado.setText("Complete todos los campos.");
            return;
        }

        if (Validaciones.esCredencialValida(usuario, contrasena)) {
            SwingUtilities.invokeLater(() -> {
                new PantallaInicio().setVisible(true);
                dispose();
            });
        } else {
            lblEstado.setText("Usuario o contrasena incorrectos.");
            txtContrasena.setText("");
            txtContrasena.requestFocus();
            sacudir();
        }
    }

    private void sacudir() {
        final Point origen = getLocation();
        Timer t = new Timer(40, null);
        final int[] tick = {0};
        final int[] dir  = {1};
        t.addActionListener(e -> {
            if (tick[0]++ >= 10) { setLocation(origen); ((Timer)e.getSource()).stop(); }
            else { setLocation(origen.x + dir[0] * 6, origen.y); dir[0] = -dir[0]; }
        });
        t.start();
    }
}
