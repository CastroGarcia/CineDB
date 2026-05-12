package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
    private JPanel panelCentral;
    public JButton btnIniciarSesion, btnRegistrar;
    public JTextField txtUsuario;
    public JPasswordField txtContraseña;
    
    public VentanaLogin(){
        configFrame();
        initComponents();
    }
    
    public String getTxtUsuario() {
        return txtUsuario.getText();
    }
    
    public String getTxtContraseña() {
        return txtContraseña.getText();
    }
    
    private void configFrame() {
        setLayout(new BorderLayout());
        setTitle("Inicio de sesion");
        setSize(400, 700);
        setIconImage(new ImageIcon(getClass().getResource("/resources/usuario.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);                     
    }
    
    private void initComponents() {
        // Construimos interfaz
        panelCentral = crearPanelCentral();                       
        add(panelCentral);
    }
    
    private JPanel crearPanelCentral() {
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
        JLabel titulo = new JLabel("Login Cine", SwingConstants.CENTER);
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
        txtUsuario = crearCampo(150);
        p.add(txtUsuario, gbc);
        
        // TextField Contraseña
        gbc.gridy = 3;
        txtContraseña = crearCampoContraseña(150);
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
        
        // FILA FANTASMA (EMPUJA LOS COMPONENTES HACIA ARRIBA)
        gbc.gridx = 0;
        gbc.gridy = 6;          // fila después de todo
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;      // 👈 esto empuja TODO hacia arriba
        gbc.fill = GridBagConstraints.VERTICAL;

        p.add(Box.createVerticalGlue(), gbc);
        
        return p;
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
        
        return b;
    }
    
    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        return l;
    }
    
    private JTextField crearCampo(int length) {
        JTextField campo = new JTextField(length);
        campo.setCaretColor(Color.ORANGE);
        
        return campo;
    }
    
    private JPasswordField crearCampoContraseña(int length) {
        JPasswordField campo = new JPasswordField(length);
        campo.setCaretColor(Color.ORANGE);
        
        return campo;
    }
    
    public void showAlert(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }    
   
}
