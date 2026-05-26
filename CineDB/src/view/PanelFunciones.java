package view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import models.Funcion;
import models.Movie;

public class PanelFunciones extends JPanel {

    public DefaultTableModel dtmFunciones;
    public JTable            tablaFunciones;
    public JButton           btnNueva, btnEliminar;

    public JComboBox<MovieItem> cmbPelicula;
    public JComboBox<Integer>   cmbSala;
    public JTextField           txtHoraInicio;
    public JButton              btnGenerarPreview, btnGuardar, btnCancelarForm;

    public DefaultTableModel dtmPreview;
    public JTable            tablaPreview;

    public CardLayout card;

    public PanelFunciones() { initComponents(); }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        setBackground(Theme.CONTENT_BG);
        add(crearPanelPrincipal(), "PRINCIPAL");
        add(crearPanelNueva(),     "NUEVA");
        card.show(this, "PRINCIPAL");
    }

    // ── Principal ─────────────────────────────────────────────────────────────

    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        header.add(Theme.titleLabel("Funciones"), BorderLayout.WEST);
        p.add(header, BorderLayout.NORTH);

        String[] cols = { "ID", "Película", "Sala", "Hora inicio", "Hora fin" };
        dtmFunciones = new DefaultTableModel(null, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaFunciones = PanelClientes.estilizarTabla(new JTable(dtmFunciones));
        tablaFunciones.setRowHeight(36);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(0, 20, 16, 20));
        body.add(PanelClientes.crearScrollPane(tablaFunciones), BorderLayout.CENTER);
        p.add(body, BorderLayout.CENTER);

        btnNueva    = Theme.primaryButton("+ Nueva función");
        btnEliminar = Theme.dangerButton("Eliminar");
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        barra.setBackground(Theme.SURFACE);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        barra.add(btnEliminar);
        barra.add(btnNueva);
        p.add(barra, BorderLayout.SOUTH);
        return p;
    }

    // ── Nueva función ─────────────────────────────────────────────────────────

    private JPanel crearPanelNueva() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(Theme.CONTENT_BG);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        header.add(Theme.titleLabel("Nueva función"), BorderLayout.WEST);
        p.add(header, BorderLayout.NORTH);

        // Scrollable content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Theme.CONTENT_BG);
        content.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // Form card
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Theme.SURFACE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        formCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 12, 12);

        // Row 1 labels
        gbc.gridy = 0; gbc.weightx = 1;
        gbc.gridx = 0; formCard.add(Theme.fieldLabel("Película"), gbc);
        gbc.gridx = 1; formCard.add(Theme.fieldLabel("Sala"), gbc);
        gbc.gridx = 2; gbc.insets = new Insets(0, 0, 12, 0);
        formCard.add(Theme.fieldLabel("Hora de inicio (HH:mm)"), gbc);

        // Row 2 fields
        cmbPelicula   = new JComboBox<>();
        cmbSala       = new JComboBox<>();
        txtHoraInicio = new JTextField("08:00", 8);
        styleCombo(cmbPelicula);
        styleCombo(cmbSala);
        styleField(txtHoraInicio);

        gbc.gridy = 1; gbc.insets = new Insets(4, 0, 0, 12);
        gbc.gridx = 0; formCard.add(cmbPelicula,   gbc);
        gbc.gridx = 1; formCard.add(cmbSala,        gbc);
        gbc.gridx = 2; gbc.insets = new Insets(4, 0, 0, 0);
        formCard.add(txtHoraInicio, gbc);

        // Calc button
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 3;
        gbc.insets = new Insets(16, 0, 0, 0);
        btnGenerarPreview = Theme.ghostButton("Calcular horario →");
        formCard.add(btnGenerarPreview, gbc);

        content.add(formCard);
        content.add(Box.createVerticalStrut(16));

        // Preview card
        JPanel previewCard = new JPanel(new BorderLayout(0, 8));
        previewCard.setBackground(Theme.SURFACE);
        previewCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        previewCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel previewHeader = new JPanel(new BorderLayout());
        previewHeader.setBackground(Theme.TABLE_HEADER);
        previewHeader.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        JLabel previewTitle = Theme.fieldLabel("Horario generado (preview)");
        previewTitle.setForeground(Theme.TEXT_SECONDARY);
        previewHeader.add(previewTitle, BorderLayout.WEST);

        String[] previewCols = { "Película", "Hora inicio", "Hora fin", "Duración" };
        dtmPreview = new DefaultTableModel(null, previewCols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPreview = PanelClientes.estilizarTabla(new JTable(dtmPreview));
        tablaPreview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Theme.centerTable(tablaFunciones);

        previewCard.setBorder(BorderFactory.createLineBorder(Theme.BORDER, 1));
        previewCard.add(previewHeader, BorderLayout.NORTH);
        previewCard.add(new JScrollPane(tablaPreview) {{
            setBorder(BorderFactory.createEmptyBorder());
            getViewport().setBackground(Theme.SURFACE);
            setPreferredSize(new Dimension(0, 150));
        }}, BorderLayout.CENTER);

        content.add(previewCard);

        JScrollPane scrollContent = new JScrollPane(content);
        scrollContent.setBorder(BorderFactory.createEmptyBorder());
        scrollContent.getViewport().setBackground(Theme.CONTENT_BG);
        p.add(scrollContent, BorderLayout.CENTER);

        // Footer
        btnGuardar      = Theme.primaryButton("Guardar función");
        btnCancelarForm = Theme.ghostButton("Cancelar");
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        footer.setBackground(Theme.SURFACE);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        footer.add(btnCancelarForm);
        footer.add(btnGuardar);
        p.add(footer, BorderLayout.SOUTH);

        return p;
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public void cargarPeliculas(List<Movie> movies) {
        cmbPelicula.removeAllItems();
        for (Movie m : movies) cmbPelicula.addItem(new MovieItem(m));
    }

    public void cargarSalas(List<Integer> nums) {
        cmbSala.removeAllItems();
        for (int n : nums) cmbSala.addItem(n);
    }

    public void mostrarFunciones(List<Funcion> funciones) {
        dtmFunciones.setRowCount(0);
        for (Funcion f : funciones) {
            dtmFunciones.addRow(new Object[] {
                f.getId(),
                f.getNombrePelicula(),
                "Sala " + f.getNumSala(),
                f.getHoraInicio().toString().substring(0, 5),
                f.getHoraFin().toString().substring(0, 5)
            });
        }
    }

    public Movie getSelectedMovie() {
        MovieItem item = (MovieItem) cmbPelicula.getSelectedItem();
        return item == null ? null : item.movie;
    }

    public int getSelectedSala() {
        Object sel = cmbSala.getSelectedItem();
        return sel == null ? -1 : (int) sel;
    }

    public void irAPrincipal() { card.show(this, "PRINCIPAL"); }
    public void irANueva()     { dtmPreview.setRowCount(0); card.show(this, "NUEVA"); }

    // ── Styling helpers ───────────────────────────────────────────────────────

    private void styleCombo(JComboBox<?> c) {
        c.setFont(Theme.FONT_BODY);
        c.setBackground(Theme.SURFACE_ALT);
        c.setBorder(Theme.fieldBorder());
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    }

    private void styleField(JTextField f) {
        f.setFont(Theme.FONT_BODY);
        f.setBackground(Theme.SURFACE_ALT);
        f.setBorder(Theme.fieldBorder());
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    }

    // ── Inner class ───────────────────────────────────────────────────────────

    public static class MovieItem {
        public final Movie movie;
        MovieItem(Movie m) { this.movie = m; }
        @Override public String toString() {
            return movie.getName() + "  (" + movie.getDuration() + ")";
        }
    }
}