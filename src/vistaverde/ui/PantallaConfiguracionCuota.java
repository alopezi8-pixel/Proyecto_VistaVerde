package vistaverde.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Pantalla 5 — Configuracion de Cuota de Mantenimiento.
 */
public class PantallaConfiguracionCuota extends JFrame {

    private JLabel     lblCuotaActual;
    private JTextField txtNuevaCuota;

    public PantallaConfiguracionCuota() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Configuracion de Cuota");
        setSize(440, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Tema.COLOR_FONDO);
        main.add(Tema.crearPanelHeader("Configuracion de Cuota"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Tema.COLOR_FONDO);
        form.setBorder(BorderFactory.createEmptyBorder(24, 40, 20, 40));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(10, 8, 10, 8);
        g.anchor = GridBagConstraints.WEST;

        // Cuota actual
        g.gridx = 0; g.gridy = 0; g.weightx = 0.45;
        JLabel lCuota = new JLabel("Cuota actual:");
        lCuota.setFont(Tema.FUENTE_NEGRITA);
        form.add(lCuota, g);
        g.gridx = 1; g.weightx = 0.55;
        lblCuotaActual = new JLabel("Q " + String.format("%.2f",
            PantallaInicio.getCondominio().getCuotaMantenimiento()));
        lblCuotaActual.setFont(Tema.FUENTE_MONEDA);
        lblCuotaActual.setForeground(Tema.COLOR_PRIMARIO);
        form.add(lblCuotaActual, g);

        // Total esperado
        g.gridx = 0; g.gridy = 1;
        JLabel lTotal = new JLabel("Total esperado/mes:");
        lTotal.setFont(Tema.FUENTE_NEGRITA);
        form.add(lTotal, g);
        g.gridx = 1;
        JLabel lblTotal = new JLabel("Q " + String.format("%.2f",
            PantallaInicio.getCondominio().getTotalEsperadoMensual()));
        lblTotal.setFont(Tema.FUENTE_NORMAL);
        lblTotal.setForeground(Tema.COLOR_TEXTO_SUAVE);
        form.add(lblTotal, g);

        // Separador
        g.gridx = 0; g.gridy = 2; g.gridwidth = 2;
        form.add(new JSeparator(), g);

        // Nueva cuota
        g.gridx = 0; g.gridy = 3; g.gridwidth = 1; g.weightx = 0.45;
        JLabel lNueva = new JLabel("Nueva cuota (Q):");
        lNueva.setFont(Tema.FUENTE_NEGRITA);
        form.add(lNueva, g);
        g.gridx = 1; g.weightx = 0.55;
        txtNuevaCuota = Tema.crearTextField();
        form.add(txtNuevaCuota, g);

        // Nota
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        JLabel nota = new JLabel("* Aplica solo a pagos futuros.");
        nota.setFont(Tema.FUENTE_PEQUENA);
        nota.setForeground(Tema.COLOR_TEXTO_SUAVE);
        form.add(nota, g);

        // Boton
        g.gridy = 5; g.insets = new Insets(14, 8, 6, 8);
        JButton btnActualizar = Tema.crearBotonPrimario("Actualizar Cuota");
        btnActualizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        form.add(btnActualizar, g);

        main.add(form, BorderLayout.CENTER);
        setContentPane(main);
        btnActualizar.addActionListener(e -> actualizar());
    }

    private void actualizar() {
        String txt = txtNuevaCuota.getText().trim().replace(",", ".");
        if (txt.isEmpty()) {
            Tema.mostrarError(this, "Ingrese el nuevo monto de la cuota.");
            return;
        }
        double nueva;
        try {
            nueva = Double.parseDouble(txt);
        } catch (NumberFormatException ex) {
            Tema.mostrarError(this, "Monto no valido. Ejemplo: 1500.00");
            return;
        }
        if (nueva <= 0) {
            Tema.mostrarError(this, "El monto debe ser mayor a Q0.00");
            return;
        }
        if (!Tema.confirmar(this, "Confirmar cambio de cuota a Q" + String.format("%.2f", nueva) + "?")) return;

        PantallaInicio.getCondominio().setCuotaMantenimiento(nueva);
        PantallaInicio.guardarDatos();
        lblCuotaActual.setText("Q " + String.format("%.2f", nueva));
        txtNuevaCuota.setText("");
        Tema.mostrarExito(this, "Cuota actualizada a Q" + String.format("%.2f", nueva)
            + "\nTotal esperado mensual: Q"
            + String.format("%.2f", PantallaInicio.getCondominio().getTotalEsperadoMensual()));
    }
}
