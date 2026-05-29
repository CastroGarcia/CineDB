package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.Sala;

public class PanelSalas extends JPanel {

    public DefaultTableModel dtmSalas;
    public JTable            tablaSalas, tablaBuscar;
    public JButton           btnCrear, btnLeer, btnActualizar, btnEliminar,
                             btnGuardar, btnCancelar, btnBuscar, btnRegresar,
                             btnVerAsientos;
    public JTextField        txtNumSala, txtAsientos, txtBuscador;
    public JCheckBox         chkDisponible;
    public CardLayout        card;
    public JLabel            lblTituloPanelAgregar;
    public int               numSalaEditando = -1;

    public static final int ASIENTOS_FIJOS = 60;

    private static final String[] COLS = { "Num. sala", "Asientos", "Disponible" };

    public PanelAsientos panelAsientos;

    public PanelSalas() { initComponents(); }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        setBackground(Theme.CONTENT_BG);

        panelAsientos = new PanelAsientos();

        add(crearPanelPrincipal(), "PRINCIPAL");
        add(crearPanelAgregar(),   "REGISTRAR");
        add(crearPanelBuscar(),    "BUSCAR");
        add(panelAsientos,         "ASIENTOS");
        card.show(this, "PRINCIPAL");
    }    

    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        header.add(Theme.titleLabel("Salas"), BorderLayout.WEST);
        p.add(header, BorderLayout.NORTH);

        dtmSalas = new DefaultTableModel(null, COLS) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaSalas = PanelClientes.estilizarTabla(new JTable(dtmSalas));
        Theme.centerTable(tablaSalas);
        
        tablaSalas.getColumnModel().getColumn(2).setCellRenderer( new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
            int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column);
                
                boolean disp = false;

                if (value != null) {
                    String texto = value.toString().trim().toLowerCase();

                    disp =
                        texto.equals("true") ||
                        texto.equals("disponible") ||
                        texto.equals("si") ||
                        texto.equals("sí") ||
                        texto.equals("1");
                }

                l.setHorizontalAlignment(SwingConstants.CENTER);
                l.setText(disp ? "Disponible" : "No disponible");
                l.setFont(new Font("Segoe UI", Font.BOLD, 12));

                if (!isSelected) {
                    l.setForeground(disp ? Theme.GREEN_FG : Theme.RED_DARK);
                    l.setBackground(disp ? Theme.GREEN_BG : Theme.RED_LIGHT);
                } else {
                    l.setForeground(Color.WHITE);
                    l.setBackground(table.getSelectionBackground());
                }

                l.setOpaque(true);
                return l;
            }
        });

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(0, 20, 16, 20));
        body.add(PanelClientes.crearScrollPane(tablaSalas), BorderLayout.CENTER);
        p.add(body, BorderLayout.CENTER);

        btnCrear       = Theme.primaryButton("+ Agregar");
        btnLeer        = Theme.ghostButton("Buscar");
        btnActualizar  = Theme.ghostButton("Editar");
        btnEliminar    = Theme.dangerButton("Eliminar");
        btnVerAsientos = Theme.ghostButton("Ver asientos");

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        barra.setBackground(Theme.SURFACE);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        barra.add(btnVerAsientos);
        barra.add(btnLeer);
        barra.add(btnActualizar);
        barra.add(btnEliminar);
        barra.add(btnCrear);
        p.add(barra, BorderLayout.SOUTH);
        return p;
    }    

    private JPanel crearPanelAgregar() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.CONTENT_BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        lblTituloPanelAgregar = Theme.sectionLabel("");
        header.add(lblTituloPanelAgregar, BorderLayout.WEST);
        root.add(header, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));

        txtNumSala = Theme.styledField("Número de sala", 20);
        txtNumSala.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        chkDisponible = new JCheckBox("Sala disponible");
        chkDisponible.setFont(Theme.FONT_BODY);
        chkDisponible.setForeground(Theme.TEXT_PRIMARY);
        chkDisponible.setBackground(Theme.SURFACE);
        chkDisponible.setSelected(true);
        chkDisponible.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoAsientos = Theme.bodyLabel(
            "Esta sala tendrá " + ASIENTOS_FIJOS + " asientos generados automáticamente.");
        infoAsientos.setForeground(Theme.TEXT_SECONDARY);
        infoAsientos.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(fieldRow("Número de sala", txtNumSala));
        card.add(Box.createVerticalStrut(14));
        card.add(chkDisponible);
        card.add(Box.createVerticalStrut(12));
        card.add(infoAsientos);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Theme.CONTENT_BG);
        wrap.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        wrap.add(card, BorderLayout.NORTH);
        root.add(wrap, BorderLayout.CENTER);

        btnGuardar  = Theme.primaryButton("Guardar");
        btnCancelar = Theme.ghostButton("Cancelar");
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        barra.setBackground(Theme.SURFACE);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        barra.add(btnCancelar);
        barra.add(btnGuardar);
        root.add(barra, BorderLayout.SOUTH);
        return root;
    }    

    private JPanel crearPanelBuscar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Theme.SURFACE);
        top.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(14, 20, 14, 20)
        ));
        top.add(Theme.titleLabel("Buscar sala"), BorderLayout.NORTH);

        txtBuscador = Theme.styledField("Buscar por número de sala", 20);
        btnBuscar   = Theme.primaryButton("Buscar");
        JPanel sr = new JPanel(new BorderLayout(8, 0));
        sr.setBackground(Theme.SURFACE);
        sr.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        sr.add(txtBuscador, BorderLayout.CENTER);
        sr.add(btnBuscar,   BorderLayout.EAST);
        top.add(sr, BorderLayout.CENTER);
        p.add(top, BorderLayout.NORTH);

        tablaBuscar = PanelClientes.estilizarTabla(new JTable(dtmSalas));
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        body.add(PanelClientes.crearScrollPane(tablaBuscar), BorderLayout.CENTER);
        p.add(body, BorderLayout.CENTER);

        btnRegresar = Theme.ghostButton("← Regresar");
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        barra.setBackground(Theme.SURFACE);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        barra.add(btnRegresar);
        p.add(barra, BorderLayout.SOUTH);
        return p;
    }    

    public void activarModoRegistro() {
        numSalaEditando = -1;
        lblTituloPanelAgregar.setText("Registrar sala");
        limpiarFormulario();
        card.show(this, "REGISTRAR");
    }

    public void activarModoEdicion(int numSala, boolean disponible) {
        numSalaEditando = numSala;
        txtNumSala.setText(String.valueOf(numSala));
        txtNumSala.setForeground(Theme.TEXT_PRIMARY);
        chkDisponible.setSelected(disponible);
        lblTituloPanelAgregar.setText("Editar sala");
        card.show(this, "REGISTRAR");
    }

    public boolean esModoEdicion() { return numSalaEditando != -1; }

    public void cancelarAccion() {
        numSalaEditando = -1;
        limpiarFormulario();
        card.show(this, "PRINCIPAL");
    }    

    public Sala getFormData() {
        int numSala = 0;
        try { numSala = Integer.parseInt(txtNumSala.getText().trim()); }
        catch (NumberFormatException ignored) {}
        return new Sala(numSala, ASIENTOS_FIJOS, chkDisponible.isSelected());
    }

    public void limpiarFormulario() {
        txtNumSala.setText("Número de sala");
        txtNumSala.setForeground(Theme.TEXT_MUTED);
        txtNumSala.setBackground(Theme.SURFACE_ALT);
        chkDisponible.setSelected(true);
    }    

    private JPanel fieldRow(String label, JTextField f) {
        JPanel r = new JPanel(new BorderLayout(0, 4));
        r.setBackground(Theme.SURFACE);
        r.setAlignmentX(Component.LEFT_ALIGNMENT);
        r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        r.add(Theme.fieldLabel(label), BorderLayout.NORTH);
        r.add(f, BorderLayout.CENTER);
        return r;
    }
}