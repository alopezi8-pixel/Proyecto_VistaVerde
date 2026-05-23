package vistaverde.ui;

import vistaverde.logic.CorreoService;
import vistaverde.model.Casa;
import vistaverde.model.Pago;
import vistaverde.model.Propietario;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Pantalla 4 — Registro de Pago de Cuota.
 */
public class PantallaRegistroPago extends JFrame {

    private JComboBox<Integer> cmbCasa;
    private JComboBox<String>  cmbMes;
    private JComboBox<Integer> cmbAnio;
    private JLabel             lblMonto;
    private JLabel             lblPropietario;

    private static final String[] MESES = {
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    };

    public PantallaRegistroPago() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de Pago de Cuota");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Tema.COLOR_FONDO);
        main.add(Tema.crearPanelHeader("Registro de Pago"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Tema.COLOR_FONDO);
        form.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;

        // Casa
        g.gridx = 0; g.gridy = 0; g.weightx = 0.35;
        form.add(lbl("Numero de casa:"), g);
        g.gridx = 1; g.weightx = 0.65;
        cmbCasa = Tema.crearComboCasas();
        cmbCasa.addActionListener(e -> actualizarInfoCasa());
        form.add(cmbCasa, g);

        // Propietario (solo lectura)
        g.gridx = 0; g.gridy = 1; g.weightx = 0.35;
        form.add(lbl("Propietario:"), g);
        g.gridx = 1; g.weightx = 0.65;
        lblPropietario = new JLabel("-");
        lblPropietario.setFont(Tema.FUENTE_NORMAL);
        lblPropietario.setForeground(Tema.COLOR_TEXTO_SUAVE);
        form.add(lblPropietario, g);

        // Mes
        g.gridx = 0; g.gridy = 2; g.weightx = 0.35;
        form.add(lbl("Mes:"), g);
        g.gridx = 1; g.weightx = 0.65;
        cmbMes = new JComboBox<>(MESES);
        cmbMes.setFont(Tema.FUENTE_NORMAL);
        cmbMes.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        form.add(cmbMes, g);

        // Anio
        g.gridx = 0; g.gridy = 3; g.weightx = 0.35;
        form.add(lbl("Anio:"), g);
        g.gridx = 1; g.weightx = 0.65;
        int anioActual = LocalDate.now().getYear();
        cmbAnio = new JComboBox<>(new Integer[]{anioActual - 1, anioActual, anioActual + 1});
        cmbAnio.setSelectedItem(anioActual);
        cmbAnio.setFont(Tema.FUENTE_NORMAL);
        form.add(cmbAnio, g);

        // Monto (solo lectura)
        g.gridx = 0; g.gridy = 4; g.weightx = 0.35;
        form.add(lbl("Monto (Q):"), g);
        g.gridx = 1; g.weightx = 0.65;
        lblMonto = new JLabel("Q " + String.format("%.2f",
            PantallaInicio.getCondominio().getCuotaMantenimiento()));
        lblMonto.setFont(Tema.FUENTE_MONEDA);
        lblMonto.setForeground(Tema.COLOR_SECUNDARIO);
        form.add(lblMonto, g);

        // Nota
        g.gridx = 0; g.gridy = 5; g.gridwidth = 2;
        JLabel nota = new JLabel("* El monto es la cuota oficial del condominio (no editable).");
        nota.setFont(Tema.FUENTE_PEQUENA);
        nota.setForeground(Tema.COLOR_TEXTO_SUAVE);
        form.add(nota, g);

        // Botones
        g.gridy = 6; g.insets = new Insets(14, 8, 6, 8);
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBtns.setOpaque(false);
        JButton btnLimpiar   = Tema.crearBotonPeligro("Limpiar");
        JButton btnRegistrar = Tema.crearBotonSecundario("Registrar Pago");
        panelBtns.add(btnLimpiar);
        panelBtns.add(btnRegistrar);
        form.add(panelBtns, g);

        main.add(form, BorderLayout.CENTER);
        setContentPane(main);

        actualizarInfoCasa();
        btnRegistrar.addActionListener(e -> registrarPago());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    private JLabel lbl(String t) {
        JLabel l = new JLabel(t);
        l.setFont(Tema.FUENTE_NEGRITA);
        l.setForeground(Tema.COLOR_TEXTO);
        return l;
    }

    private void actualizarInfoCasa() {
        int  num  = (Integer) cmbCasa.getSelectedItem();
        Casa casa = PantallaInicio.getCondominio().getCasa(num);
        if (casa.tienePropietario()) {
            lblPropietario.setText(casa.getPropietario().getNombreCompleto());
            lblPropietario.setForeground(Tema.COLOR_TEXTO);
        } else {
            lblPropietario.setText("Sin propietario registrado");
            lblPropietario.setForeground(Tema.COLOR_PELIGRO);
        }
        lblMonto.setText("Q " + String.format("%.2f",
            PantallaInicio.getCondominio().getCuotaMantenimiento()));
    }

    private void registrarPago() {
        int    num   = (Integer) cmbCasa.getSelectedItem();
        int    mes   = cmbMes.getSelectedIndex() + 1;
        int    anio  = (Integer) cmbAnio.getSelectedItem();
        double monto = PantallaInicio.getCondominio().getCuotaMantenimiento();
        Casa   casa  = PantallaInicio.getCondominio().getCasa(num);

        if (!casa.tienePropietario()) {
            Tema.mostrarError(this, "La Casa " + num + " no tiene propietario. Registrelo primero.");
            return;
        }
        if (casa.existePago(mes, anio)) {
            Tema.mostrarAdvertencia(this, "La Casa " + num + " ya tiene pago de "
                + MESES[mes-1] + " " + anio + ".\nNo se permiten pagos duplicados.");
            return;
        }
        // Verificar meses anteriores
        for (int m = 1; m < mes; m++) {
            if (!casa.existePago(m, anio)) {
                Tema.mostrarError(this, "No se puede registrar " + MESES[mes-1]
                    + " porque falta pagar " + MESES[m-1] + " " + anio + " primero.");
                return;
            }
        }

        boolean ok = PantallaInicio.getCondominio().registrarPago(num, mes, anio, monto);
        if (ok) {
            PantallaInicio.guardarDatos();
            Propietario prop = casa.getPropietario();

            // Buscar el pago recien registrado para el correo
            Pago pago = null;
            for (Pago p : casa.getPagos()) {
                if (p.getMes() == mes && p.getAnio() == anio) { pago = p; break; }
            }

            String msg = "Pago registrado exitosamente.\n\n"
                + "Casa: "        + num  + "\n"
                + "Propietario: " + prop.getNombreCompleto() + "\n"
                + "Periodo: "     + MESES[mes-1] + " " + anio + "\n"
                + "Monto: Q "     + String.format("%.2f", monto);

            if (CorreoService.isHabilitado() && pago != null) {
                boolean sent = CorreoService.enviarComprobantePago(prop, pago);
                msg += sent ? "\n\nComprobante enviado al correo del propietario."
                            : "\n\nNo se pudo enviar el correo de confirmacion.";
            }
            Tema.mostrarExito(this, msg);
            limpiar();
        } else {
            Tema.mostrarError(this, "No se pudo registrar el pago.");
        }
    }

    private void limpiar() {
        cmbCasa.setSelectedIndex(0);
        cmbMes.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        cmbAnio.setSelectedItem(LocalDate.now().getYear());
        actualizarInfoCasa();
    }
}
