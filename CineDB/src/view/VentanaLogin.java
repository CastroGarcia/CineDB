package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame{
    public JPanel panelLogin, panelRegistrar;
    public JButton btnIniciarSesion, btnRegistrar, btnCrearUsuario, btnCancelar;
    public JTextField txtUsuario, txtRegistrarUsuario;
    public JPasswordField txtContraseña, txtRegistrarContraseña, 
            txtConfirmarContraseña;
    public CardLayout card;
    
    public VentanaLogin(){
        configFrame();
        initComponents();
    }        
    
    private void configFrame() {        
        setTitle("Inicio de sesion");
        setSize(400, 700);
        setIconImage(new ImageIcon(getClass().getResource("/resources/usuario.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);                     
    }
    
    private void initComponents() {        
        card = new CardLayout();
        setLayout(card);       
        
        // Construimos interfaz
        panelLogin = crearPanelLogin();                       
        panelRegistrar = crearPanelRegistrar();
        add(panelLogin, "LOGIN");
        add(panelRegistrar, "REGISTRAR");
        
        card.show(this.getContentPane(), "LOGIN");
    }
    
    public String getTxtUsuario() {
        return txtUsuario.getText();
    }
    
    public String getTxtContraseña() {
        return txtContraseña.getText();
    }
    
    public String getTxtNuevoUsuario() {
        return txtRegistrarUsuario.getText();
    }
    
    public String getTxtNuevaContraseña() {
        return txtRegistrarContraseña.getText();
    }
    
    public String getTxtConfirmarContraseña() {
        return txtConfirmarContraseña.getText();
    }
    
    private JPanel crearPanelLogin() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(245, 245, 245));
        //p.setBackground(Color.red);
        p.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        
        // Label Imagen
        JLabel imagen = new JLabel(new ImageIcon(getClass().getResource("/resources/usuario.png")));        
        gbc.gridy = 0; gbc.gridx = 0;
        gbc.gridwidth = 2;                          
        p.add(imagen, gbc);
        
        // Label Titulo
        JLabel titulo = new JLabel("Login", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        //titulo.setOpaque(true);
        //titulo.setBackground(Color.green);
        gbc.gridy = 1; gbc.gridx = 0;
        gbc.gridwidth = 2;                          
        gbc.weightx = 1.0;                          // La celda ocupa todo el ancho
        gbc.fill = GridBagConstraints.HORIZONTAL;   // El JLabel se estira
        p.add(titulo, gbc);
        
        // Label Usuario
        gbc.gridy = 2; gbc.gridx = 0;             // Posicion
        gbc.anchor = GridBagConstraints.WEST;     // Donde se posiciona el componente en la celda
        gbc.fill = GridBagConstraints.NONE;       //Como se llena el espacio disponible        
        gbc.gridwidth = 1;                        // Celdas que ocupa el componente
        gbc.weightx = 0;
        p.add(crearLabel("Usuario: "), gbc);
        
        // Label Contraseña
        gbc.gridy = 3;
        p.add(crearLabel("Contraseña: "), gbc);
        
        // TextField Usuario
        gbc.gridy = 2; gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtUsuario = crearCampo("Nombre de usuario",150);
        p.add(txtUsuario, gbc);
        
        // TextField Contraseña
        gbc.gridy = 3;
        txtContraseña = crearCampoContraseña("Contraseña", 150);
        p.add(txtContraseña, gbc);
        
        // Boton IniciarSesion
        gbc.gridy = 4; gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        btnIniciarSesion = crearBoton("Iniciar Sesion");
        p.add(btnIniciarSesion, gbc);
        
        // Boton Registrarse
        gbc.gridy = 5;
        btnRegistrar = crearBoton("Registrarse");
        p.add(btnRegistrar, gbc);        
        
        // Fila "fantasma" que empuja los componentes hacia arriba
        gbc.gridx = 0;
        gbc.gridy = 6;          // fila al final
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;      // empuja todo hacia arriba
        gbc.fill = GridBagConstraints.VERTICAL;
        p.add(Box.createVerticalGlue(), gbc);
        
        return p;
    }
    
    private JPanel crearPanelRegistrar() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        p.setBackground(new Color(254, 254, 254));
        
        JLabel titulo = new JLabel("Crear Usuario");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 50));
        txtRegistrarUsuario = crearCampo("Escribe un nombre de usuario", 150);
        txtRegistrarContraseña = crearCampoContraseña("Escribe tu contraseña", 150);
        txtConfirmarContraseña = crearCampoContraseña("Escribe la contraseña", 150);
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);        
        btnCrearUsuario = crearBoton("Crear usuario");
        btnCancelar = crearBoton("Cancelar");
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE;
        panelBotones.add(btnCrearUsuario, gbc);
        gbc.gridy = 1;
        panelBotones.add(btnCancelar, gbc);
        
        p.add(titulo);
        p.add(Box.createVerticalStrut(50));
        p.add(crearLabel("Nombre de usuario: "));
        p.add(txtRegistrarUsuario);
        p.add(crearLabel("Contraseña: "));
        p.add(txtRegistrarContraseña);
        p.add(crearLabel("Confirmar contraseña: "));
        p.add(txtConfirmarContraseña);
        p.add(Box.createVerticalStrut(30));
        p.add(panelBotones);
                
        return p;
    }
    
    public void limpiarCamposLogin() {
        txtUsuario.setText("Nombre de usuario");
        txtContraseña.setText("Contraseña");
        txtUsuario.setForeground(Color.GRAY);
        txtContraseña.setForeground(Color.GRAY);
    }
    
    public void limpiarCamposRegistro() {
        txtRegistrarUsuario.setText("Escribe un nombre de usuario");
        txtRegistrarContraseña.setText("Escribe tu contraseña");
        txtConfirmarContraseña.setText("Escribe la contraseña");
        txtRegistrarUsuario.setForeground(Color.GRAY);
        txtRegistrarContraseña.setForeground(Color.GRAY);
        txtConfirmarContraseña.setForeground(Color.GRAY);
    }
    
    // Helpers construccion
    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setPreferredSize(new Dimension(200, 40));
        b.setMinimumSize(new Dimension(200, 40));
        b.setMaximumSize(new Dimension(200, 40));
        b.setBackground(Color.black);
        b.setForeground(Color.white);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(Color.gray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(Color.black);
            }            
        });
        
        return b;
    }
    
    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        
        return l;
    }
    
    private JTextField crearCampo(String placeholder, int length) {
        JTextField campo = new JTextField(placeholder, length);
        campo.setForeground(Color.gray);        
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
        
        return campo;
    }
    
    private JPasswordField crearCampoContraseña(String placeholder, int length) {
        JPasswordField campo = new JPasswordField(placeholder, length);
        campo.setForeground(Color.gray);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
        
        return campo;
    }
    
    public void showAlert(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }    
   
}
