package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import models.Asiento;
import models.Asiento.Estado;

public class PanelAsientos extends JPanel {
    
    private static final Color COLOR_DISPONIBLE   = new Color(220, 50, 50);
    private static final Color COLOR_ROTO         = new Color(80, 80, 80);
    private static final Color COLOR_MANTENIMIENTO = new Color(200, 140, 0);
    private static final Color COLOR_HOVER        = new Color(255, 100, 100);
    private static final Color COLOR_BG           = new Color(30, 30, 30);
    private static final Color COLOR_SCREEN       = new Color(240, 240, 240);

    public JButton btnRegresar;
    public JButton btnGuardar;
    public JLabel lblTituloSala;

    // Maps asiento id -> button, and button -> asiento
    private final Map<Integer, JButton> buttonMap = new HashMap<>();
    private final Map<JButton, Asiento> asientoMap = new HashMap<>();

    // Legend labels
    private JLabel lblDisponible, lblRoto, lblMantenimiento;

    private JPanel gridPanel;
    private JPanel screenPanel;

    public PanelAsientos() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_BG);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        lblTituloSala = new JLabel("SALA", SwingConstants.CENTER);
        lblTituloSala.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTituloSala.setForeground(Color.WHITE);

        header.add(lblTituloSala, BorderLayout.CENTER);
        
        screenPanel = new JPanel();
        screenPanel.setBackground(COLOR_BG);
        screenPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        JLabel screen = new JLabel("PANTALLA");
        screen.setHorizontalAlignment(SwingConstants.CENTER);
        screen.setFont(new Font("Segoe UI", Font.BOLD, 13));
        screen.setForeground(Color.DARK_GRAY);
        screen.setBackground(COLOR_SCREEN);
        screen.setOpaque(true);
        screen.setBorder(BorderFactory.createEmptyBorder(6, 40, 6, 40));
        screen.setPreferredSize(new Dimension(340, 30));
        screenPanel.add(screen);

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(COLOR_BG);
        topArea.add(header, BorderLayout.NORTH);
        topArea.add(screenPanel, BorderLayout.CENTER);
        
        gridPanel = new JPanel();
        gridPanel.setBackground(COLOR_BG);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBackground(COLOR_BG);
        scroll.getViewport().setBackground(COLOR_BG);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(20, 20, 20));
        footer.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        legend.setBackground(new Color(20, 20, 20));
        legend.add(makeLegendDot(COLOR_DISPONIBLE,   "Disponible"));
        legend.add(makeLegendDot(COLOR_ROTO,         "Roto"));
        legend.add(makeLegendDot(COLOR_MANTENIMIENTO,"Mantenimiento"));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnPanel.setBackground(new Color(20, 20, 20));

        btnGuardar = new JButton("Guardar cambios");
        styleFooterBtn(btnGuardar, new Color(220, 50, 50));

        btnRegresar = new JButton("Regresar");
        styleFooterBtn(btnRegresar, new Color(70, 70, 70));

        btnPanel.add(btnGuardar);
        btnPanel.add(btnRegresar);

        footer.add(legend,   BorderLayout.WEST);
        footer.add(btnPanel, BorderLayout.EAST);

        add(topArea,  BorderLayout.NORTH);
        add(scroll,   BorderLayout.CENTER);
        add(footer,   BorderLayout.SOUTH);
    }
    
    public void cargarAsientos(List<Asiento> asientos, int numSala) {
        buttonMap.clear();
        asientoMap.clear();
        gridPanel.removeAll();

        if (asientos.isEmpty()) {
            gridPanel.add(new JLabel("No hay asientos registrados para esta sala."));
            gridPanel.revalidate();
            gridPanel.repaint();
            return;
        }
        
        int maxFila = asientos.stream().mapToInt(Asiento::getFila).max().orElse(1);
        int maxCol  = asientos.stream().mapToInt(Asiento::getColumna).max().orElse(1);

        Map<String, Asiento> lookup = new HashMap<>();
        for (Asiento a : asientos) {
            lookup.put(a.getFila() + "," + a.getColumna(), a);
        }

        gridPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        for (int fila = 1; fila <= maxFila; fila++) {
            gbc.gridx = 0;
            gbc.gridy = fila;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel rowLabel = new JLabel(String.valueOf((char)('A' + fila - 1)));
            rowLabel.setForeground(Color.LIGHT_GRAY);
            rowLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            gridPanel.add(rowLabel, gbc);

            for (int col = 1; col <= maxCol; col++) {
                gbc.gridx = col;
                gbc.gridy = fila;
                gbc.anchor = GridBagConstraints.CENTER;

                Asiento a = lookup.get(fila + "," + col);
                if (a == null) {                    
                    JPanel spacer = new JPanel();
                    spacer.setOpaque(false);
                    spacer.setPreferredSize(new Dimension(38, 36));
                    gridPanel.add(spacer, gbc);
                } else {
                    JButton btn = crearBotonAsiento(a);
                    buttonMap.put(a.getId(), btn);
                    asientoMap.put(btn, a);
                    gridPanel.add(btn, gbc);
                }
            }
        }
        
        for (int col = 1; col <= maxCol; col++) {
            gbc.gridx = col;
            gbc.gridy = 0;
            JLabel colLabel = new JLabel(String.valueOf(col), SwingConstants.CENTER);
            colLabel.setForeground(Color.GRAY);
            colLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            colLabel.setPreferredSize(new Dimension(38, 16));
            gridPanel.add(colLabel, gbc);
        }

        lblTituloSala.setText("SALA " + numSala);

        gridPanel.revalidate();
        gridPanel.repaint();
    }
    
    public void ciclarEstado(JButton btn) {
        Asiento a = asientoMap.get(btn);
        if (a == null) return;
        Estado next = switch (a.getEstado()) {
            case DISPONIBLE    -> Estado.ROTO;
            case ROTO          -> Estado.MANTENIMIENTO;
            case MANTENIMIENTO -> Estado.DISPONIBLE;
        };
        a.setEstado(next);
        actualizarColorBoton(btn, next);
    }
    
    public Map<JButton, Asiento> getAsientoMap() {
        return asientoMap;
    }

    public Asiento getAsiento(JButton btn) {
        return asientoMap.get(btn);
    }

    public void addSeatClickListener(ActionListener listener) {
        for (JButton btn : asientoMap.keySet()) {
            btn.addActionListener(listener);
        }
    }    

    private JButton crearBotonAsiento(Asiento a) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(38, 36));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(a.getLabel() + " - " + a.getEstado().name());
        actualizarColorBoton(btn, a.getEstado());

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(btn.getBackground().brighter());
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                actualizarColorBoton(btn, asientoMap.get(btn).getEstado());
            }
        });
        return btn;
    }

    private void actualizarColorBoton(JButton btn, Estado estado) {
        Color c = switch (estado) {
            case DISPONIBLE    -> COLOR_DISPONIBLE;
            case ROTO          -> COLOR_ROTO;
            case MANTENIMIENTO -> COLOR_MANTENIMIENTO;
        };
        btn.setBackground(c);
        Asiento a = asientoMap.get(btn);
        if (a != null) btn.setToolTipText(a.getLabel() + " - " + estado.name());
    }

    private JPanel makeLegendDot(Color color, String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(new Color(20, 20, 20));
        JPanel dot = new JPanel();
        dot.setBackground(color);
        dot.setPreferredSize(new Dimension(14, 14));
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        p.add(dot);
        p.add(lbl);
        return p;
    }

    private void styleFooterBtn(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(7, 18, 7, 18));
    }
}
