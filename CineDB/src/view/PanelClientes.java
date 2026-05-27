package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import models.Client;

public class PanelClientes extends JPanel {

    public DefaultTableModel dtmClientes;
    public JTable            tablaClientes, tablaBuscar;
    public JButton           btnCrear, btnLeer, btnActualizar, btnEliminar,
                             btnGuardar, btnCancelar, btnBuscar, btnRegresar;
    public JTextField        txtNombre, txtEdad, txtTelefono, txtCorreo,
                             txtIdMembership, txtBuscador;
    public CardLayout        card;
    public JLabel            lblTituloPanelAgregar;
    public int               idEditando = -1;

    private static final String[] COLS =
        { "ID", "Nombre", "Edad", "Teléfono", "Correo", "ID Membresía" };

    public PanelClientes() { initComponents(); }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        setBackground(Theme.CONTENT_BG);

        add(crearPanelPrincipal(), "PRINCIPAL");
        add(crearPanelAgregar(),   "REGISTRAR");
        add(crearPanelBuscar(),    "BUSCAR");
        card.show(this, "PRINCIPAL");
    }

    // ── Principal ─────────────────────────────────────────────────────────────

    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);

        p.add(crearPanelHeader("Clientes"), BorderLayout.NORTH);

        // Table
        dtmClientes = new DefaultTableModel(null, COLS) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaClientes = estilizarTabla(new JTable(dtmClientes));
        Theme.centerTable(tablaClientes);
        JScrollPane sp = crearScrollPane(tablaClientes);
        JPanel bodyWrap = new JPanel(new BorderLayout());
        bodyWrap.setBackground(Theme.CONTENT_BG);
        bodyWrap.setBorder(BorderFactory.createEmptyBorder(0, 20, 16, 20));
        bodyWrap.add(sp, BorderLayout.CENTER);
        p.add(bodyWrap, BorderLayout.CENTER);

        // Action bar
        btnCrear      = Theme.primaryButton("+ Nuevo");
        btnLeer       = Theme.ghostButton("Buscar");
        btnActualizar = Theme.ghostButton("Editar");
        btnEliminar   = Theme.dangerButton("Eliminar");

        p.add(crearBarraAcciones(btnCrear, btnLeer, btnActualizar, btnEliminar),
              BorderLayout.SOUTH);
        return p;
    }

    // ── Formulario ────────────────────────────────────────────────────────────

    private JPanel crearPanelAgregar() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.CONTENT_BG);

        JPanel header = crearPanelHeader("Clientes");
        root.add(header, BorderLayout.NORTH);

        // Card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));

        lblTituloPanelAgregar = Theme.sectionLabel("");
        lblTituloPanelAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNombre      = Theme.styledField("Nombre completo", 30);
        txtEdad        = Theme.styledField("Edad", 30);
        txtTelefono    = Theme.styledField("Teléfono", 30);
        txtCorreo      = Theme.styledField("Correo electrónico", 30);
        txtIdMembership= Theme.styledField("ID de membresía", 30);

        card.add(lblTituloPanelAgregar);
        card.add(Box.createVerticalStrut(20));
        card.add(buildFieldRow("Nombre",        txtNombre));
        card.add(Box.createVerticalStrut(10));
        card.add(buildFieldRow("Edad",          txtEdad));
        card.add(Box.createVerticalStrut(10));
        card.add(buildFieldRow("Teléfono",      txtTelefono));
        card.add(Box.createVerticalStrut(10));
        card.add(buildFieldRow("Correo",        txtCorreo));
        card.add(Box.createVerticalStrut(10));
        card.add(buildFieldRow("ID Membresía",  txtIdMembership));

        JPanel scrollWrap = new JPanel(new BorderLayout());
        scrollWrap.setBackground(Theme.CONTENT_BG);
        scrollWrap.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        scrollWrap.add(card, BorderLayout.NORTH);
        root.add(scrollWrap, BorderLayout.CENTER);

        btnGuardar  = Theme.primaryButton("Guardar");
        btnCancelar = Theme.ghostButton("Cancelar");
        root.add(crearBarraAcciones(btnGuardar, btnCancelar), BorderLayout.SOUTH);

        return root;
    }

    // ── Buscar ────────────────────────────────────────────────────────────────

    private JPanel crearPanelBuscar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);

        // Header with search bar
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Theme.SURFACE);
        top.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(14, 20, 14, 20)
        ));

        JLabel titulo = Theme.titleLabel("Buscar cliente");
        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setBackground(Theme.SURFACE);
        searchRow.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        txtBuscador = Theme.styledField("Buscar por ID de cliente", 30);
        btnBuscar   = Theme.primaryButton("Buscar");

        searchRow.add(txtBuscador, BorderLayout.CENTER);
        searchRow.add(btnBuscar,   BorderLayout.EAST);

        top.add(titulo,    BorderLayout.NORTH);
        top.add(searchRow, BorderLayout.CENTER);
        p.add(top, BorderLayout.NORTH);

        tablaBuscar = estilizarTabla(new JTable(dtmClientes));
        JPanel bodyWrap = new JPanel(new BorderLayout());
        bodyWrap.setBackground(Theme.CONTENT_BG);
        bodyWrap.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        bodyWrap.add(crearScrollPane(tablaBuscar), BorderLayout.CENTER);
        p.add(bodyWrap, BorderLayout.CENTER);

        btnRegresar = Theme.ghostButton("← Regresar");
        p.add(crearBarraAcciones(btnRegresar), BorderLayout.SOUTH);
        return p;
    }

    // ── Modes ─────────────────────────────────────────────────────────────────

    public void activarModoRegistro() {
        idEditando = -1;
        lblTituloPanelAgregar.setText("Registrar cliente");
        card.show(this, "REGISTRAR");
    }

    public void activarModoEdicion(int id, String nombre, String edad,
                                   String telefono, String correo, int id_mem) {
        idEditando = id;
        txtNombre.setText(nombre);       txtNombre.setForeground(Theme.TEXT_PRIMARY);
        txtEdad.setText(edad);           txtEdad.setForeground(Theme.TEXT_PRIMARY);
        txtTelefono.setText(telefono);   txtTelefono.setForeground(Theme.TEXT_PRIMARY);
        txtCorreo.setText(correo);       txtCorreo.setForeground(Theme.TEXT_PRIMARY);
        txtIdMembership.setText(String.valueOf(id_mem));
        txtIdMembership.setForeground(Theme.TEXT_PRIMARY);
        lblTituloPanelAgregar.setText("Editar cliente");
        card.show(this, "REGISTRAR");
    }

    public boolean esModoEdicion() { return idEditando != -1; }

    public void cancelarAccion() {
        idEditando = -1;
        limpiarFormulario();
        card.show(this, "PRINCIPAL");
    }

    // ── Form data ─────────────────────────────────────────────────────────────

    public Client getFormData() {
        try {
            return new Client(
                txtNombre.getText(), txtEdad.getText(),
                txtTelefono.getText(), txtCorreo.getText(),
                Integer.parseInt(txtIdMembership.getText())
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void limpiarFormulario() {
        resetField(txtNombre,       "Nombre completo");
        resetField(txtEdad,         "Edad");
        resetField(txtTelefono,     "Teléfono");
        resetField(txtCorreo,       "Correo electrónico");
        resetField(txtIdMembership, "ID de membresía");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private JPanel crearPanelHeader(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.SURFACE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        p.add(Theme.titleLabel(titulo), BorderLayout.WEST);
        return p;
    }

    private JPanel crearBarraAcciones(JButton... btns) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        p.setBackground(Theme.SURFACE);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        for (JButton b : btns) p.add(b);
        return p;
    }

    private JPanel buildFieldRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(0, 4));
        row.setBackground(Theme.SURFACE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.add(Theme.fieldLabel(labelText), BorderLayout.NORTH);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    static JTable estilizarTabla(JTable t) {
        t.setFont(Theme.FONT_BODY);
        t.setRowHeight(34);
        t.setBackground(Theme.SURFACE);
        t.setSelectionBackground(new Color(147, 197, 253, 120));;//
        t.setSelectionForeground(Color.WHITE);
        t.setSelectionForeground(Theme.TEXT_PRIMARY);
        t.setGridColor(Theme.BORDER_LIGHT);
        t.setShowVerticalLines(false);
        t.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader h = t.getTableHeader();
        h.setFont(Theme.FONT_TABLE_HDR);
        h.setBackground(Theme.TABLE_HEADER);
        h.setForeground(Theme.TEXT_SECONDARY);
        h.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER));
        h.setPreferredSize(new Dimension(0, 36));
        h.setReorderingAllowed(false);
        return t;
    }

    static JScrollPane crearScrollPane(JTable t) {
        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(BorderFactory.createLineBorder(Theme.BORDER, 1));
        sp.getViewport().setBackground(Theme.SURFACE);
        return sp;
    }

    private void resetField(JTextField f, String placeholder) {
        f.setText(placeholder);
        f.setForeground(Theme.TEXT_MUTED);
        f.setBackground(Theme.SURFACE_ALT);
    }
}