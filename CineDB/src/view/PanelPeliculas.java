package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import models.Movie;

public class PanelPeliculas extends JPanel {

    public DefaultTableModel dtmPeliculas;
    public JTable            tablaPeliculas, tablaBuscar;
    public JButton           btnCrear, btnLeer, btnActualizar, btnEliminar,
                             btnGuardar, btnCancelar, btnBuscar, btnRegresar;
    public JTextField        txtNombre, txtGenero, txtDuracion, txtFormato,
                             txtIdioma, txtBuscador;
    public CardLayout        card;
    public JLabel            lblTituloPanelAgregar;
    public int               idEditando = -1;

    private static final String[] COLS =
        { "ID", "Nombre", "Género", "Duración", "Formato", "Idioma" };

    public PanelPeliculas() { initComponents(); }

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

        p.add(crearHeader("Películas"), BorderLayout.NORTH);

        dtmPeliculas = new DefaultTableModel(null, COLS) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPeliculas = PanelClientes.estilizarTabla(new JTable(dtmPeliculas));
        Theme.centerTable(tablaPeliculas);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(0, 20, 16, 20));
        body.add(PanelClientes.crearScrollPane(tablaPeliculas), BorderLayout.CENTER);
        p.add(body, BorderLayout.CENTER);

        btnCrear      = Theme.primaryButton("+ Nueva");
        btnLeer       = Theme.ghostButton("Buscar");
        btnActualizar = Theme.ghostButton("Editar");
        btnEliminar   = Theme.dangerButton("Eliminar");
        p.add(crearBarra(btnCrear, btnLeer, btnActualizar, btnEliminar), BorderLayout.SOUTH);
        return p;
    }

    // ── Formulario ────────────────────────────────────────────────────────────

    private JPanel crearPanelAgregar() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.CONTENT_BG);
        root.add(crearHeader("Películas"), BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));

        lblTituloPanelAgregar = Theme.sectionLabel("");
        lblTituloPanelAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNombre   = Theme.styledField("Título de la película", 30);
        txtGenero   = Theme.styledField("Ej. Drama, Acción, Comedia", 30);
        txtDuracion = Theme.styledField("hh:mm:ss", 30);
        txtFormato  = Theme.styledField(".mkv, .mp4 ...", 30);
        txtIdioma   = Theme.styledField("Español, Inglés ...", 30);

        card.add(lblTituloPanelAgregar);
        card.add(Box.createVerticalStrut(20));
        card.add(row("Nombre",   txtNombre));
        card.add(Box.createVerticalStrut(10));
        card.add(row("Género",   txtGenero));
        card.add(Box.createVerticalStrut(10));
        card.add(row("Duración", txtDuracion));
        card.add(Box.createVerticalStrut(10));
        card.add(row("Formato",  txtFormato));
        card.add(Box.createVerticalStrut(10));
        card.add(row("Idioma",   txtIdioma));

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Theme.CONTENT_BG);
        wrap.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        wrap.add(card, BorderLayout.NORTH);
        root.add(wrap, BorderLayout.CENTER);

        btnGuardar  = Theme.primaryButton("Guardar");
        btnCancelar = Theme.ghostButton("Cancelar");
        root.add(crearBarra(btnGuardar, btnCancelar), BorderLayout.SOUTH);
        return root;
    }

    // ── Buscar ────────────────────────────────────────────────────────────────

    private JPanel crearPanelBuscar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Theme.SURFACE);
        top.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(14, 20, 14, 20)
        ));
        top.add(Theme.titleLabel("Buscar película"), BorderLayout.NORTH);

        txtBuscador = Theme.styledField("Buscar por nombre de película", 30);
        btnBuscar   = Theme.primaryButton("Buscar");
        JPanel sr = new JPanel(new BorderLayout(8, 0));
        sr.setBackground(Theme.SURFACE);
        sr.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        sr.add(txtBuscador, BorderLayout.CENTER);
        sr.add(btnBuscar,   BorderLayout.EAST);
        top.add(sr, BorderLayout.CENTER);
        p.add(top, BorderLayout.NORTH);

        tablaBuscar = PanelClientes.estilizarTabla(new JTable(dtmPeliculas));
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        body.add(PanelClientes.crearScrollPane(tablaBuscar), BorderLayout.CENTER);
        p.add(body, BorderLayout.CENTER);

        btnRegresar = Theme.ghostButton("← Regresar");
        p.add(crearBarra(btnRegresar), BorderLayout.SOUTH);
        return p;
    }

    // ── Modes ─────────────────────────────────────────────────────────────────

    public void activarModoRegistro() {
        idEditando = -1;
        lblTituloPanelAgregar.setText("Registrar película");
        card.show(this, "REGISTRAR");
    }

    public void activarModoEdicion(int id, String nombre, String genero,
                                   String duracion, String formato, String idioma) {
        idEditando = id;
        set(txtNombre,   nombre);
        set(txtGenero,   genero);
        set(txtDuracion, duracion);
        set(txtFormato,  formato);
        set(txtIdioma,   idioma);
        lblTituloPanelAgregar.setText("Editar película");
        card.show(this, "REGISTRAR");
    }

    public boolean esModoEdicion() { return idEditando != -1; }

    public void cancelarAccion() {
        idEditando = -1;
        limpiarFormulario();
        card.show(this, "PRINCIPAL");
    }

    // ── Form data ─────────────────────────────────────────────────────────────

    public Movie getFormData() {
        return new Movie(
            txtNombre.getText(), txtGenero.getText(),
            txtDuracion.getText(), txtFormato.getText(), txtIdioma.getText()
        );
    }

    public void limpiarFormulario() {
        reset(txtNombre,   "Título de la película");
        reset(txtGenero,   "Ej. Drama, Acción, Comedia");
        reset(txtDuracion, "hh:mm:ss");
        reset(txtFormato,  ".mkv, .mp4 ...");
        reset(txtIdioma,   "Español, Inglés ...");
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private JPanel crearHeader(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.SURFACE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        p.add(Theme.titleLabel(titulo), BorderLayout.WEST);
        return p;
    }

    private JPanel crearBarra(JButton... btns) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        p.setBackground(Theme.SURFACE);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        for (JButton b : btns) p.add(b);
        return p;
    }

    private JPanel row(String label, JTextField field) {
        JPanel r = new JPanel(new BorderLayout(0, 4));
        r.setBackground(Theme.SURFACE);
        r.setAlignmentX(Component.LEFT_ALIGNMENT);
        r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        r.add(Theme.fieldLabel(label), BorderLayout.NORTH);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        r.add(field, BorderLayout.CENTER);
        return r;
    }

    private void set(JTextField f, String val) {
        f.setText(val);
        f.setForeground(Theme.TEXT_PRIMARY);
        f.setBackground(Theme.SURFACE);
    }

    private void reset(JTextField f, String placeholder) {
        f.setText(placeholder);
        f.setForeground(Theme.TEXT_MUTED);
        f.setBackground(Theme.SURFACE_ALT);
    }
}