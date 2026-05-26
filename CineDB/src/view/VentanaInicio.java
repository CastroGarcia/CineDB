package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import java.awt.Font;

public class VentanaInicio extends JFrame {

    public PanelClientes   panelClientes;
    public PanelPeliculas  panelPeliculas;
    public PanelMembresias panelMembresias;
    public PanelSalas      panelSalas;
    public PanelFunciones  panelFunciones;

    public JPanel    panelFondo, panelCentral, panelSideBar, panelHeader;
    public JButton   btnClientes, btnPeliculas, btnFunciones,/* btnCartelera,*/
                     btnSalas, btnMembresias, btnVender, btnSalir;
    public CardLayout card;

    // Track which nav button is active
    private JButton navActivo;

    public VentanaInicio() {
        configFrame();
        initComponents();
    }

    private void configFrame() {
        setLayout(new BorderLayout());
        setTitle("Cinefan");
        setSize(1200, 700);
        try {
            setIconImage(new ImageIcon(
                getClass().getResource("/resources/entrada-de-cine.png")).getImage());
        } catch (Exception ignored) {}
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        panelFondo   = new JPanel(new BorderLayout());
        panelFondo.setBackground(Theme.CONTENT_BG);

        panelHeader  = crearHeader();
        panelSideBar = crearSideBar();
        panelCentral = crearPanelCentral();

        panelFondo.add(panelHeader,  BorderLayout.NORTH);
        panelFondo.add(panelSideBar, BorderLayout.WEST);
        panelFondo.add(panelCentral, BorderLayout.CENTER);
        add(panelFondo);
    }

    // ── Header ────────────────────────────────────────────────────────────────

    private JPanel crearHeader() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        p.setPreferredSize(new Dimension(0, 60));
        p.setBackground(Theme.HEADER_BG);

        // Try to load icon
        try {
            ImageIcon ico = new ImageIcon(
                getClass().getResource("/resources/entrada-de-cine.png"));
            Image img = ico.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel iconLbl = new JLabel(new ImageIcon(img));
            iconLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
            p.add(iconLbl);
        } catch (Exception ignored) {}

        // "Cine" + "fan" in two colors
        JLabel part1 = new JLabel("Cine");
        part1.setFont(Theme.FONT_HEADER);
        part1.setForeground(Color.WHITE);
        JLabel part2 = new JLabel("fan");
        part2.setFont(Theme.FONT_HEADER);
        part2.setForeground(Theme.RED_PRIMARY);

        p.add(part1);
        p.add(part2);
        return p;
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────

    private JPanel crearSideBar() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(0,1,0,2));
        p.setBackground(Theme.SIDEBAR_BG);
        p.setPreferredSize(new Dimension(220, 0));
        p.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnClientes   = crearNavBtn("Clientes",   "anadir-contacto.png");
        btnPeliculas  = crearNavBtn("Películas",  "carrete-de-pelicula.png");
        btnFunciones  = crearNavBtn("Funciones",  "tiempo-de-la-funcion.png");
        //btnCartelera =  crearNavBtn("Carteleras", "pelicula.png");
        btnSalas      = crearNavBtn("Salas",      "cine.png");
        btnMembresias = crearNavBtn("Membresías", "anadir-contacto.png");
        btnVender     = crearNavBtn("Vender",     "entradas.png");

        p.add(btnClientes);
        p.add(btnPeliculas);
        p.add(btnFunciones);
        //p.add(btnCartelera);
        p.add(btnSalas);
        p.add(btnMembresias);
        p.add(btnVender);
        p.add(Box.createVerticalStrut(20));
        // Divider
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setForeground(new Color(255, 255, 255, 25));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        p.add(sep);
        p.add(Box.createVerticalStrut(10));

        btnSalir = crearNavBtn("Salir", "salida.png");
        btnSalir.setForeground(Theme.RED_PRIMARY);
        p.add(btnSalir);

        // Default active: clientes
        setNavActive(btnClientes);
        return p;
    }

    private JButton crearNavBtn(String texto, String iconPath) {
    JButton b = new JButton(texto);

    b.setFont(Theme.FONT_NAV);
    b.setForeground(new Color(160, 160, 190));
    b.setBackground(Theme.SIDEBAR_BG);

    b.setBorderPainted(false);
    b.setFocusPainted(false);
    b.setOpaque(true);
    b.setContentAreaFilled(true);
    b.setDoubleBuffered(true);

    b.setCursor(new Cursor(Cursor.HAND_CURSOR));

    b.setHorizontalAlignment(SwingConstants.LEADING);
    b.setIconTextGap(14);

    Dimension btnSize = new Dimension(220, 60);
    b.setPreferredSize(btnSize);
    b.setMinimumSize(btnSize);
    b.setMaximumSize(btnSize);

    b.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 3, 0, 0, Theme.SIDEBAR_BG),
        BorderFactory.createEmptyBorder(10, 18, 10, 18)//
    ));

    try {
        ImageIcon ico = new ImageIcon(
            getClass().getResource("/resources/" + iconPath));

        Image img = ico.getImage()
            .getScaledInstance(26, 26, Image.SCALE_SMOOTH);

        b.setIcon(new ImageIcon(img));

    } catch (Exception ignored) {}

    b.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            if (b != navActivo) {
                b.setBackground(new Color(45,45,55));
                b.setForeground(new Color(200,200,220));
                b.repaint();
                
                SwingUtilities.getWindowAncestor(b).repaint();
            }
        }

        public void mouseExited(MouseEvent e) {
            if (b != navActivo) {
                b.setBackground(Theme.SIDEBAR_BG);
                b.repaint();
            }
        }
    });

    return b;
}

    public void setNavActive(JButton btn) {
        if (navActivo != null) {
            navActivo.setBackground(Theme.SIDEBAR_BG);
            navActivo.setForeground(new Color(160, 160, 190));

            navActivo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, Theme.SIDEBAR_BG),
                BorderFactory.createEmptyBorder(0, 14, 0, 14)
            ));
        }
        
        navActivo = btn;
        btn.setBackground(new Color(40, 40, 60));
        btn.setForeground(Theme.RED_PRIMARY);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, Theme.RED_PRIMARY),
            BorderFactory.createEmptyBorder(0, 14, 0, 14)
        ));
    }

    // ── Central panel ─────────────────────────────────────────────────────────

    private JPanel crearPanelCentral() {
        card = new CardLayout();
        JPanel p = new JPanel(card);
        p.setBackground(Theme.CONTENT_BG);

        panelClientes   = new PanelClientes();
        panelPeliculas  = new PanelPeliculas();
        panelMembresias = new PanelMembresias();
        panelSalas      = new PanelSalas();
        panelFunciones  = new PanelFunciones();

        p.add(panelClientes,   "CLIENTES");
        p.add(panelPeliculas,  "PELICULAS");
        p.add(panelMembresias, "MEMBRESIAS");
        p.add(panelSalas,      "SALAS");
        p.add(panelFunciones,  "FUNCIONES");

        card.show(p, "CLIENTES");
        return p;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    public void botonSalir() {
        UIManager.put("OptionPane.background", Theme.SURFACE);
        UIManager.put("Panel.background", Theme.SURFACE);

        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 16));

        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));

        UIManager.put("Button.background", Theme.RED_PRIMARY);
        UIManager.put("Button.foreground", Color.WHITE);
    
        int r = JOptionPane.showConfirmDialog(
            panelFondo, "¿Estás seguro de que quieres salir?",
            "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) System.exit(0);
    }
}