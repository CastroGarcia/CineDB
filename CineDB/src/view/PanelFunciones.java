package view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import models.Funcion;
import models.Movie;

public class PanelFunciones extends JPanel {
    
    public DefaultTableModel dtmFunciones;
    public JTable tablaFunciones;
    public JButton btnNueva, btnEliminar;

    public JComboBox<MovieItem> cmbPelicula;
    public JComboBox<Integer> cmbSala;
    public JTextField txtHoraInicio;   // HH:mm
    public JButton btnGenerarPreview, btnGuardar, btnCancelarForm;

    public DefaultTableModel dtmPreview;
    public JTable tablaPreview;

    public CardLayout card;
    private JPanel panelPrincipal, panelNueva;

    public PanelFunciones() {
        initComponents();
    }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        panelPrincipal = crearPanelPrincipal();
        panelNueva     = crearPanelNueva();
        add(panelPrincipal, "PRINCIPAL");
        add(panelNueva,     "NUEVA");
        card.show(this, "PRINCIPAL");
    }

    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        // Title
        JLabel titulo = new JLabel("FUNCIONES");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));

        // Table
        String[] cols = {"ID", "Pelicula", "Sala", "Hora Inicio", "Hora Fin"};
        dtmFunciones = new DefaultTableModel(null, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaFunciones = new JTable(dtmFunciones);
        tablaFunciones.setRowHeight(26);
        tablaFunciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scroll = new JScrollPane(tablaFunciones);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setBackground(Color.RED);
        btnNueva    = crearBoton("Nueva Funcion", "tiempo-de-la-funcion.png");
        btnEliminar = crearBoton("Eliminar",      "basura.png");
        btnPanel.add(btnNueva);
        btnPanel.add(btnEliminar);

        p.add(titulo,  BorderLayout.NORTH);
        p.add(scroll,  BorderLayout.CENTER);
        p.add(btnPanel,BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearPanelNueva() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel titulo = new JLabel("Nueva Funcion");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        cmbPelicula  = new JComboBox<>();
        cmbSala      = new JComboBox<>();
        txtHoraInicio = new JTextField("08:00", 8);
        txtHoraInicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnGenerarPreview = new JButton("Calcular Horario");
        btnGenerarPreview.setBackground(new Color(220, 50, 50));
        btnGenerarPreview.setForeground(Color.WHITE);
        btnGenerarPreview.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerarPreview.setBorderPainted(false);
        btnGenerarPreview.setFocusPainted(false);
        btnGenerarPreview.setCursor(new Cursor(Cursor.HAND_CURSOR));

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0;
        form.add(label("Pelicula:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        form.add(cmbPelicula, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(label("Sala:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        form.add(cmbSala, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(label("Hora de inicio (HH:mm):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        form.add(txtHoraInicio, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        form.add(btnGenerarPreview, gbc);

        String[] previewCols = {"Pelicula", "Hora Inicio", "Hora Fin", "Duracion"};
        dtmPreview = new DefaultTableModel(null, previewCols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPreview = new JTable(dtmPreview);
        tablaPreview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPreview.setRowHeight(24);
        tablaPreview.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        JScrollPane scrollPreview = new JScrollPane(tablaPreview);
        scrollPreview.setPreferredSize(new Dimension(0, 180));

        JPanel previewPanel = new JPanel(new BorderLayout(0, 5));
        previewPanel.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder("Horario generado (preview)");
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        previewPanel.setBorder(border);
        previewPanel.add(scrollPreview, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        footer.setBackground(Color.WHITE);
        btnGuardar     = new JButton("Guardar Funcion");
        btnCancelarForm = new JButton("Cancelar");
        styleBtn(btnGuardar,      new Color(220, 50, 50));
        styleBtn(btnCancelarForm, new Color(100, 100, 100));
        footer.add(btnGuardar);
        footer.add(btnCancelarForm);
        
        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setBackground(Color.WHITE);
        top.add(titulo, BorderLayout.NORTH);
        top.add(form,   BorderLayout.CENTER);

        p.add(top,          BorderLayout.NORTH);
        p.add(previewPanel, BorderLayout.CENTER);
        p.add(footer,       BorderLayout.SOUTH);
        return p;
    }

    public void cargarPeliculas(List<Movie> movies) {
        cmbPelicula.removeAllItems();
        for (Movie m : movies) {
            cmbPelicula.addItem(new MovieItem(m));
        }
    }
    
    public void cargarSalas(List<Integer> numSalas) {
        cmbSala.removeAllItems();
        for (int n : numSalas) {
            cmbSala.addItem(n);
        }
    }
    
    public void mostrarFunciones(List<Funcion> funciones) {
        dtmFunciones.setRowCount(0);
        for (Funcion f : funciones) {
            dtmFunciones.addRow(new Object[]{
                f.getId(),
                f.getNombrePelicula(),
                f.getNumSala(),
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
    public void irANueva()     {
        dtmPreview.setRowCount(0);
        card.show(this, "NUEVA");
    }
    
    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return l;
    }

    private void styleBtn(JButton b, Color bg) {
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
    }

    private JButton crearBoton(String texto, String iconPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + iconPath));
        JButton b = new JButton(texto, icon);
        b.setBackground(Color.RED);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(Color.BLUE); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(Color.RED);  }
        });
        return b;
    }

    public static class MovieItem {
        public final Movie movie;
        MovieItem(Movie m) { this.movie = m; }
        @Override public String toString() {
            return m().getName() + "  (" + m().getDuration() + ")";
        }
        private Movie m() { return movie; }
    }
}
