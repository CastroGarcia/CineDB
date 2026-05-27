package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaLogin extends JFrame {

    public JPanel panelLogin, panelRegistrar;
    public JButton btnIniciarSesion, btnRegistrar, btnCrearUsuario, btnCancelar;
    public JTextField txtUsuario, txtRegistrarUsuario;
    public JPasswordField txtContraseña, txtRegistrarContraseña, txtConfirmarContraseña;
    public CardLayout card;

    public VentanaLogin() {
        configFrame();
        initComponents();
    }

    private void configFrame() {
        setTitle("Cinefan — Inicio de sesión");
        setSize(400, 600);
        setIconImage(new ImageIcon(getClass().getResource("/resources/usuario.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        panelLogin     = crearPanelLogin();
        panelRegistrar = crearPanelRegistrar();
        add(panelLogin,     "LOGIN");
        add(panelRegistrar, "REGISTRAR");
        card.show(getContentPane(), "LOGIN");
    }

    // ── Login panel ───────────────────────────────────────────────────────────

    private JPanel crearPanelLogin() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Theme.CONTENT_BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(32, 32, 32, 32)
        ));
        card.setMaximumSize(new Dimension(320, Integer.MAX_VALUE));

        // Logo icon
        JPanel iconBox = new JPanel();
        iconBox.setBackground(Theme.RED_PRIMARY);
        iconBox.setPreferredSize(new Dimension(56, 56));
        iconBox.setMaximumSize(new Dimension(56, 56));
        iconBox.setMinimumSize(new Dimension(56, 56));
        iconBox.setBorder(BorderFactory.createEmptyBorder());
        JLabel iconLbl = new JLabel("\uD83C\uDFAC", SwingConstants.CENTER);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconBox.add(iconLbl);
        iconBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel iconWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconWrap.setBackground(Theme.SURFACE);
        iconWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Use the app icon if emoji doesn't render
        try {
            ImageIcon ico = new ImageIcon(getClass().getResource("/resources/entrada-de-cine.png"));
            Image img = ico.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            JLabel icoLabel = new JLabel(new ImageIcon(img));
            icoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            iconWrap.add(icoLabel);
        } catch (Exception ex) {
            iconWrap.add(iconBox);
        }

        // App name
        JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        nameRow.setBackground(Theme.SURFACE);
        nameRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel namePart1 = new JLabel("Cine");
        namePart1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        namePart1.setForeground(Theme.TEXT_PRIMARY);
        JLabel namePart2 = new JLabel("fan");
        namePart2.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        namePart2.setForeground(Theme.RED_PRIMARY);
        nameRow.add(namePart1);
        nameRow.add(namePart2);

        // Fields
        txtUsuario   = Theme.styledField("Nombre de usuario", 20);
        txtContraseña = Theme.styledPasswordField("Contraseña", 20);
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtContraseña.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        btnIniciarSesion = Theme.loginButton("Iniciar sesión");
        btnRegistrar     = Theme.secondaryLoginButton("Crear cuenta");
        
        Dimension btnSize = new Dimension(240, 42);

        btnIniciarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnIniciarSesion.setPreferredSize(btnSize);
        btnIniciarSesion.setMaximumSize(btnSize);

        btnRegistrar.setPreferredSize(btnSize);
        btnRegistrar.setMaximumSize(btnSize);

        card.add(iconWrap);
        card.add(Box.createVerticalStrut(8));
        card.add(nameRow);
        card.add(Box.createVerticalStrut(28));
        card.add(fieldLabelPanel("Usuario"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtUsuario);
        card.add(Box.createVerticalStrut(12));
        card.add(fieldLabelPanel("Contraseña"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtContraseña);
        card.add(Box.createVerticalStrut(20));
        card.add(btnIniciarSesion);
        card.add(Box.createVerticalStrut(8));
        card.add(btnRegistrar);

        root.add(card);
        return root;
    }

    // ── Register panel ────────────────────────────────────────────────────────

    private JPanel crearPanelRegistrar() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Theme.CONTENT_BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(32, 32, 32, 32)
        ));

        JLabel titulo = new JLabel("Crear usuario");
        titulo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        titulo.setForeground(Theme.TEXT_PRIMARY);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Completa los campos para registrarte");
        subtitulo.setFont(Theme.FONT_BODY);
        subtitulo.setForeground(Theme.TEXT_SECONDARY);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtRegistrarUsuario     = Theme.styledField("Nombre de usuario", 20);
        txtRegistrarContraseña  = Theme.styledPasswordField("Contraseña", 20);
        txtConfirmarContraseña  = Theme.styledPasswordField("Confirmar contraseña", 20);
        txtRegistrarUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtRegistrarContraseña.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtConfirmarContraseña.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        btnCrearUsuario = Theme.loginButton("Crear usuario");
        btnCancelar     = Theme.secondaryLoginButton("Cancelar");
        
        Dimension btnSize = new Dimension(240, 42);

        btnCrearUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCrearUsuario.setPreferredSize(btnSize);
        btnCrearUsuario.setMaximumSize(btnSize);
        
        btnCancelar.setPreferredSize(btnSize);
        btnCancelar.setMaximumSize(btnSize);

        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(24));
        card.add(fieldLabelPanel("Nombre de usuario"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtRegistrarUsuario);
        card.add(Box.createVerticalStrut(12));
        card.add(fieldLabelPanel("Contraseña"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtRegistrarContraseña);
        card.add(Box.createVerticalStrut(12));
        card.add(fieldLabelPanel("Confirmar contraseña"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtConfirmarContraseña);
        card.add(Box.createVerticalStrut(20));
        card.add(btnCrearUsuario);
        card.add(Box.createVerticalStrut(8));
        card.add(btnCancelar);

        root.add(card);
        return root;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private JPanel fieldLabelPanel(String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setBackground(Theme.SURFACE);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(Theme.FONT_LABEL);
        l.setForeground(Theme.TEXT_SECONDARY);
        p.add(l);
        return p;
    }

    // ── Public API (called by controller) ─────────────────────────────────────

    public String getTxtUsuario()            { return txtUsuario.getText(); }
    public String getTxtContraseña()         { return new String(txtContraseña.getPassword()); }
    public String getTxtNuevoUsuario()       { return txtRegistrarUsuario.getText(); }
    public String getTxtNuevaContraseña()    { return new String(txtRegistrarContraseña.getPassword()); }
    public String getTxtConfirmarContraseña(){ return new String(txtConfirmarContraseña.getPassword()); }

    public void limpiarCamposLogin() {
        txtUsuario.setText("Nombre de usuario");
        txtUsuario.setForeground(Theme.TEXT_MUTED);
        txtUsuario.setBackground(Theme.SURFACE_ALT);
        txtContraseña.setText("Contraseña");
        txtContraseña.setEchoChar((char) 0);
        txtContraseña.setForeground(Theme.TEXT_MUTED);
        txtContraseña.setBackground(Theme.SURFACE_ALT);
    }

    public void limpiarCamposRegistro() {
        txtRegistrarUsuario.setText("Nombre de usuario");
        txtRegistrarUsuario.setForeground(Theme.TEXT_MUTED);
        txtRegistrarUsuario.setBackground(Theme.SURFACE_ALT);
        txtRegistrarContraseña.setText("Contraseña");
        txtRegistrarContraseña.setEchoChar((char) 0);
        txtRegistrarContraseña.setForeground(Theme.TEXT_MUTED);
        txtRegistrarContraseña.setBackground(Theme.SURFACE_ALT);
        txtConfirmarContraseña.setText("Confirmar contraseña");
        txtConfirmarContraseña.setEchoChar((char) 0);
        txtConfirmarContraseña.setForeground(Theme.TEXT_MUTED);
        txtConfirmarContraseña.setBackground(Theme.SURFACE_ALT);
    }

    public void showAlert(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}