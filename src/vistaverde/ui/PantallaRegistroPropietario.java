package vistaverde.ui;

import vistaverde.logic.Validaciones;
import vistaverde.model.Propietario;
import javax.swing.*;
import java.awt.*;

/**
 * Pantalla 3 — Registro de Propietario.
 */
public class PantallaRegistroPropietario extends JFrame {

    private JTextField       txtNombre;
    private JComboBox<Integer> cmbCasa;
    private JTextField       txtTelefono;
    private JTextField       txtCorreo;

    public PantallaRegistroPropietario() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de Propietario");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Tema.COLOR_FONDO);
        main.add(Tema.crearPanelHeader("Registro de Propietario"), BorderLayout.NORTH);

        // --- FORMULARIO ---
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Tema.COLOR_FONDO);
        form.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 8, 6, 8);
        g.anchor = GridBagConstraints.WEST;

        // Nombre
        g.gridx = 0; g.gridy = 0; g.weightx = 0.35;
        form.add(lbl("Nombre completo *"), g);
        g.gridx = 1; g.weightx = 0.65;
        txtNombre = Tema.crearTextField();
        form.add(txtNombre, g);

        // Casa
        g.gridx = 0; g.gridy = 1; g.weightx = 0.35;
        form.add(lbl("Numero de casa *"), g);
        g.gridx = 1; g.weightx = 0.65;
        cmbCasa = Tema.crearComboCasas();
        form.add(cmbCasa, g);

        // Telefono
        g.gridx = 0; g.gridy = 2; g.weightx = 0.35;
        form.add(lbl("Telefono *"), g);
        g.gridx = 1; g.weightx = 0.65;
        txtTelefono = Tema.crearTextField();
        txtTelefono.setToolTipText("8 digitos, ej: 55551234");
        form.add(txtTelefono, g);

        // Correo
        g.gridx = 0; g.gridy = 3; g.weightx = 0.35;
        form.add(lbl("Correo electronico *"), g);
        g.gridx = 1; g.weightx = 0.65;
        txtCorreo = Tema.crearTextField();
        txtCorreo.setToolTipText("ej: usuario@gmail.com");
        form.add(txtCorreo, g);

        // Nota
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        JLabel nota = new JLabel("* Todos los campos son obligatorios.");
        nota.setFont(Tema.FUENTE_PEQUENA);
        nota.setForeground(Tema.COLOR_TEXTO_SUAVE);
        form.add(nota, g);

        // Botones
        g.gridy = 5; g.insets = new Insets(14, 8, 6, 8);
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBtns.setOpaque(false);
        JButton btnLimpiar   = Tema.crearBotonPeligro("Limpiar");
        JButton btnRegistrar = Tema.crearBotonSecundario("Registrar Propietario");
        panelBtns.add(btnLimpiar);
        panelBtns.add(btnRegistrar);
        form.add(panelBtns, g);

        main.add(form, BorderLayout.CENTER);
        setContentPane(main);

        btnRegistrar.addActionListener(e -> registrar());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    private JLabel lbl(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Tema.FUENTE_NEGRITA);
        l.setForeground(Tema.COLOR_TEXTO);
        return l;
    }

    private void registrar() {
        String nombre    = txtNombre.getText().trim();
        int    numeroCasa = (Integer) cmbCasa.getSelectedItem();
        String telefono  = txtTelefono.getText().trim();
        String correo    = txtCorreo.getText().trim();

        if (!Validaciones.esNombreValido(nombre)) {
            Tema.mostrarError(this, "El nombre debe tener al menos nombre y apellido.");
            txtNombre.requestFocus(); return;
        }
        if (!Validaciones.esTelefonoValido(telefono)) {
            Tema.mostrarError(this, "El telefono debe tener 8 digitos.\nEjemplo: 55551234");
            txtTelefono.requestFocus(); return;
        }
        if (!Validaciones.esCorreoValido(correo)) {
            Tema.mostrarError(this, "El correo no tiene formato valido.\nEjemplo: usuario@gmail.com");
            txtCorreo.requestFocus(); return;
        }
        if (PantallaInicio.getCondominio().getCasa(numeroCasa).tienePropietario()) {
            Tema.mostrarError(this, "La Casa " + numeroCasa + " ya tiene propietario registrado.");
            return;
        }

        Propietario p = new Propietario(nombre, numeroCasa, telefono, correo);
        PantallaInicio.getCondominio().registrarPropietario(numeroCasa, p);
        PantallaInicio.guardarDatos();
        Tema.mostrarExito(this, "Propietario registrado exitosamente.\n\nCasa: " + numeroCasa + "\nNombre: " + nombre);
        limpiar();
    }

    private void limpiar() {
        txtNombre.setText("");
        cmbCasa.setSelectedIndex(0);
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtNombre.requestFocus();
    }
}
