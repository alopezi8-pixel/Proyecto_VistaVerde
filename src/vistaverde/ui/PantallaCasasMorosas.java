package vistaverde.ui;

import vistaverde.model.Casa;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Pantalla 8 — Casas Morosas del mes actual.
 */
public class PantallaCasasMorosas extends JFrame {

    private DefaultTableModel modeloTabla;
    private JLabel            lblTitulo;
    private JLabel            lblTotal;

    private static final String[] MESES = {
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    };

    public PantallaCasasMorosas() {
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Casas Morosas");
        setSize(620, 440);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Tema.COLOR_FONDO);
        main.add(Tema.crearPanelHeader("Casas Morosas"), BorderLayout.NORTH);

        // --- SUB-HEADER ---
        JPanel infoBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 8));
        infoBar.setBackground(new Color(255, 245, 240));
        infoBar.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(255,180,150)));

        lblTitulo = new JLabel();
        lblTitulo.setFont(Tema.FUENTE_NEGRITA);
        lblTitulo.setForeground(Tema.COLOR_PELIGRO);

        lblTotal = new JLabel();
        lblTotal.setFont(Tema.FUENTE_NORMAL);
        lblTotal.setForeground(Tema.COLOR_TEXTO_SUAVE);

        JButton btnRef = Tema.crearBotonPrimario("Actualizar");
        btnRef.setPreferredSize(new Dimension(100, 28));
        btnRef.addActionListener(e -> cargarDatos());

        infoBar.add(lblTitulo);
        infoBar.add(Box.createHorizontalStrut(20));
        infoBar.add(lblTotal);
        infoBar.add(Box.createHorizontalStrut(40));
        infoBar.add(btnRef);

        // --- TABLA ---
        String[] cols = {"Numero de Casa", "Propietario", "Telefono"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        Tema.estilizarTabla(tabla);

        // Filas alternadas en tono calido
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t,val,sel,foc,row,col);
                if (!sel) c.setBackground(row%2==0 ? Color.WHITE : new Color(255,248,240));
                return c;
            }
        });

        tabla.getColumnModel().getColumn(0).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(290);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(130);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        // --- FOOTER NOTA ---
        JPanel nota = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
        nota.setBackground(new Color(240,240,240));
        nota.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Tema.COLOR_BORDE));
        JLabel lblNota = new JLabel("Use el telefono para contactar a los propietarios morosos.");
        lblNota.setFont(Tema.FUENTE_PEQUENA);
        lblNota.setForeground(Tema.COLOR_TEXTO_SUAVE);
        nota.add(lblNota);

        main.add(infoBar, BorderLayout.NORTH);
        main.add(scroll,  BorderLayout.CENTER);
        main.add(nota,    BorderLayout.SOUTH);
        setContentPane(main);
    }

    private void cargarDatos() {
        int mes  = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();

        ArrayList<Casa> morosas = PantallaInicio.getCondominio().getCasasMorosas(mes, anio);
        modeloTabla.setRowCount(0);

        for (Casa c : morosas) {
            modeloTabla.addRow(new Object[]{
                "Casa " + c.getNumeroCasa(),
                c.tienePropietario() ? c.getPropietario().getNombreCompleto() : "Sin propietario",
                c.tienePropietario() ? c.getPropietario().getTelefono()        : "-"
            });
        }

        if (morosas.isEmpty()) {
            lblTitulo.setText("Todas las casas pagaron en " + MESES[mes-1] + " " + anio);
            lblTitulo.setForeground(Tema.COLOR_SECUNDARIO);
        } else {
            lblTitulo.setText("Sin pago en " + MESES[mes-1] + " " + anio + ": " + morosas.size() + " casa(s)");
            lblTitulo.setForeground(Tema.COLOR_PELIGRO);
        }
        lblTotal.setText("Total casas: " + vistaverde.model.Condominio.TOTAL_CASAS);
    }
}
