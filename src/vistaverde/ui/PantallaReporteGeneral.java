package vistaverde.ui;

import vistaverde.model.Casa;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Pantalla 7 — Reporte General de las 30 casas.
 */
public class PantallaReporteGeneral extends JFrame {

    private DefaultTableModel modeloTabla;
    private JLabel            lblRecaudado;
    private JLabel            lblEsperado;
    private JLabel            lblFaltante;

    public PantallaReporteGeneral() {
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Reporte General del Condominio");
        setSize(760, 540);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Tema.COLOR_FONDO);
        main.add(Tema.crearPanelHeader("Reporte General"), BorderLayout.NORTH);

        // --- TABLA ---
        String[] cols = {"Casa", "Propietario", "Estado Mes Actual", "Total Año"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        Tema.estilizarTabla(tabla);

        // Renderizador de estado (coloreado)
        tabla.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t,val,sel,focus,row,col);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                if ("Pagado".equals(val)) {
                    lbl.setForeground(new Color(0,130,0));
                    lbl.setText("Pagado");
                } else {
                    lbl.setForeground(Tema.COLOR_PELIGRO);
                    lbl.setText("Pendiente");
                }
                return lbl;
            }
        });

        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(260);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 14, 0, 14));
        main.add(scroll, BorderLayout.CENTER);

        // --- PIE CON TOTALES ---
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 10));
        pie.setBackground(Tema.COLOR_HEADER);
        pie.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        lblRecaudado = new JLabel();
        lblRecaudado.setFont(Tema.FUENTE_NEGRITA);
        lblRecaudado.setForeground(new Color(144, 238, 144));

        lblEsperado = new JLabel();
        lblEsperado.setFont(Tema.FUENTE_NEGRITA);
        lblEsperado.setForeground(new Color(173, 216, 230));

        lblFaltante = new JLabel();
        lblFaltante.setFont(Tema.FUENTE_NEGRITA);
        lblFaltante.setForeground(Tema.COLOR_ACENTO);

        JButton btnRef = Tema.crearBotonSecundario("Actualizar");
        btnRef.setPreferredSize(new Dimension(100, 30));
        btnRef.addActionListener(e -> cargarDatos());

        pie.add(lblRecaudado);
        pie.add(lblEsperado);
        pie.add(lblFaltante);
        pie.add(Box.createHorizontalGlue());
        pie.add(btnRef);
        main.add(pie, BorderLayout.SOUTH);

        setContentPane(main);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        int mes  = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();

        for (Casa c : PantallaInicio.getCondominio().getCasas()) {
            String prop  = c.tienePropietario() ? c.getPropietario().getNombreCompleto() : "- Sin registrar -";
            String estado = c.existePago(mes, anio) ? "Pagado" : "Pendiente";
            double total = c.getTotalPagadoAnio(anio);
            modeloTabla.addRow(new Object[]{
                "Casa " + c.getNumeroCasa(), prop, estado, "Q " + String.format("%.2f", total)
            });
        }

        double rec  = PantallaInicio.getCondominio().getTotalRecaudadoMes(mes, anio);
        double esp  = PantallaInicio.getCondominio().getTotalEsperadoMensual();
        double falt = esp - rec;

        lblRecaudado.setText("Recaudado: Q " + String.format("%.2f", rec));
        lblEsperado.setText( "Esperado:  Q " + String.format("%.2f", esp));
        lblFaltante.setText( "Faltante:  Q " + String.format("%.2f", falt));
        lblFaltante.setForeground(falt > 0 ? new Color(255,160,80) : new Color(144,238,144));
    }
}
