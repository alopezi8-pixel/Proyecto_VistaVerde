package vistaverde.ui;

import vistaverde.model.Casa;
import vistaverde.model.Pago;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Pantalla 6 — Estado de Cuenta por Casa.
 */
public class PantallaEstadoCuenta extends JFrame {

    private JComboBox<Integer>    cmbCasa;
    private JLabel                lblPropietario;
    private JLabel                lblTotalPagado;
    private DefaultListModel<String> modeloPagados;
    private DefaultListModel<String> modeloPendientes;

    private static final String[] MESES = {
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    };

    public PantallaEstadoCuenta() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Estado de Cuenta por Casa");
        setSize(640, 490);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Tema.COLOR_FONDO);
        main.add(Tema.crearPanelHeader("Estado de Cuenta"), BorderLayout.NORTH);

        // --- BARRA DE SELECCION ---
        JPanel barSel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        barSel.setBackground(Tema.COLOR_FONDO);
        barSel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));

        JLabel lblCasaTxt = new JLabel("Casa:");
        lblCasaTxt.setFont(Tema.FUENTE_NEGRITA);
        cmbCasa = Tema.crearComboCasas();
        JButton btnConsultar = Tema.crearBotonPrimario("Consultar");
        btnConsultar.setPreferredSize(new Dimension(110, 32));

        lblPropietario = new JLabel("Propietario: -");
        lblPropietario.setFont(Tema.FUENTE_NORMAL);
        lblPropietario.setForeground(Tema.COLOR_TEXTO_SUAVE);

        lblTotalPagado = new JLabel("Total pagado: Q 0.00");
        lblTotalPagado.setFont(Tema.FUENTE_NEGRITA);
        lblTotalPagado.setForeground(Tema.COLOR_SECUNDARIO);

        barSel.add(lblCasaTxt);
        barSel.add(cmbCasa);
        barSel.add(btnConsultar);
        barSel.add(Box.createHorizontalStrut(16));
        barSel.add(lblPropietario);
        barSel.add(Box.createHorizontalStrut(16));
        barSel.add(lblTotalPagado);

        // --- LISTAS ---
        JPanel panelListas = new JPanel(new GridLayout(1, 2, 14, 0));
        panelListas.setBackground(Tema.COLOR_FONDO);
        panelListas.setBorder(BorderFactory.createEmptyBorder(6, 16, 16, 16));

        // Pagados
        JPanel pPagados = Tema.crearPanelConBorde("Meses Pagados");
        pPagados.setLayout(new BorderLayout());
        modeloPagados = new DefaultListModel<>();
        JList<String> listPagados = new JList<>(modeloPagados);
        listPagados.setFont(Tema.FUENTE_NORMAL);
        listPagados.setCellRenderer(new PaidCellRenderer(true));
        pPagados.add(new JScrollPane(listPagados));
        panelListas.add(pPagados);

        // Pendientes
        JPanel pPend = Tema.crearPanelConBorde("Meses Pendientes");
        pPend.setLayout(new BorderLayout());
        modeloPendientes = new DefaultListModel<>();
        JList<String> listPend = new JList<>(modeloPendientes);
        listPend.setFont(Tema.FUENTE_NORMAL);
        listPend.setCellRenderer(new PaidCellRenderer(false));
        pPend.add(new JScrollPane(listPend));
        panelListas.add(pPend);

        main.add(barSel,      BorderLayout.NORTH);
        main.add(panelListas, BorderLayout.CENTER);
        setContentPane(main);

        btnConsultar.addActionListener(e -> consultar());
    }

    private void consultar() {
        int  num  = (Integer) cmbCasa.getSelectedItem();
        Casa casa = PantallaInicio.getCondominio().getCasa(num);

        if (!casa.tienePropietario()) {
            Tema.mostrarAdvertencia(this, "La Casa " + num + " no tiene propietario registrado.");
            lblPropietario.setText("Propietario: Sin registrar");
            lblPropietario.setForeground(Tema.COLOR_PELIGRO);
            modeloPagados.clear();
            modeloPendientes.clear();
            lblTotalPagado.setText("Total pagado: Q 0.00");
            return;
        }

        lblPropietario.setText("Propietario: " + casa.getPropietario().getNombreCompleto());
        lblPropietario.setForeground(Tema.COLOR_TEXTO);

        int anio      = LocalDate.now().getYear();
        int mesActual = LocalDate.now().getMonthValue();

        // Pagados (todos los anios)
        modeloPagados.clear();
        for (Pago p : casa.getPagos()) {
            modeloPagados.addElement(MESES[p.getMes()-1] + " " + p.getAnio()
                + "  -  Q " + String.format("%.2f", p.getMonto()));
        }

        // Pendientes del anio actual hasta hoy
        modeloPendientes.clear();
        ArrayList<Integer> pend = casa.getMesesPendientes(anio, mesActual);
        for (int m : pend) {
            modeloPendientes.addElement(MESES[m-1] + " " + anio);
        }

        lblTotalPagado.setText("Total pagado: Q " + String.format("%.2f", casa.getTotalPagado()));
    }

    /** Renderizador de celdas con color segun pagado/pendiente. */
    private static class PaidCellRenderer extends DefaultListCellRenderer {
        private final boolean pagado;
        PaidCellRenderer(boolean pagado) { this.pagado = pagado; }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel lbl = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lbl.setPreferredSize(new Dimension(0, 26));
            if (!isSelected) {
                lbl.setBackground(index % 2 == 0 ? Color.WHITE
                    : (pagado ? new Color(242,255,242) : new Color(255,245,245)));
                lbl.setForeground(pagado ? new Color(0,110,0) : new Color(180,0,0));
            }
            lbl.setText("  " + (pagado ? "v " : "x ") + value);
            return lbl;
        }
    }
}
