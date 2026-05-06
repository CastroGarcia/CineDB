package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame{
    private JPanel panelCentral;
    public JButton btnIniciarSesion, btnRegistrar;
    public JTextField txtUsuario, txtContraseña;
    
    public VentanaLogin(){
        configFrame();
        initComponents();
    }
    
    private void configFrame() {
        setLayout(new BorderLayout());
        setTitle("Inicio de sesion");
        setSize(400, 550);
        //setIconImage(new ImageIcon("URL").getImage());
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
        
        // Label Titulo
        JLabel titulo = new JLabel("Login Cine", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        //titulo.setOpaque(true);
        //titulo.setBackground(Color.green);
        gbc.gridx = 0; gbc.gridy = 0;               
        gbc.gridwidth = 2;                          
        gbc.weightx = 1.0;                          // La celda ocupa todo el ancho
        gbc.fill = GridBagConstraints.HORIZONTAL;   // El JLabel se estira
        p.add(titulo, gbc);
        
        // Label Usuario
        gbc.gridy = 1; gbc.gridx = 0;             // Posicion
        gbc.anchor = GridBagConstraints.WEST;     // Donde se posiciona el componente en la celda
        gbc.fill = GridBagConstraints.NONE;       //Como se llena el espacio disponible        
        gbc.gridwidth = 1;                        // Celdas que ocupa el componente
        gbc.weightx = 0;
        p.add(crearLabel("Usuario: "), gbc);
        
        // Label Contraseña
        gbc.gridy = 2;
        p.add(crearLabel("Contraseña: "), gbc);
        
        // TextField Usuario
        gbc.gridy = 1; gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtUsuario = crearCampo(150);
        p.add(txtUsuario, gbc);
        
        // TextField Contraseña
        gbc.gridy = 2;
        txtContraseña = crearCampo(150);
        p.add(txtContraseña, gbc);
        
        // Boton IniciarSesion
        gbc.gridy = 3; gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        btnIniciarSesion = crearBoton("Iniciar Sesion");
        p.add(btnIniciarSesion, gbc);
        
        // Boton Registrarse
        gbc.gridy = 4;
        btnRegistrar = crearBoton("Registrarse");
        p.add(btnRegistrar, gbc);        
        
        // 🔥 FILA FANTASMA (LA CLAVE)
        gbc.gridx = 0;
        gbc.gridy = 5;          // fila después de todo
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;      // 👈 esto empuja TODO hacia arriba
        gbc.fill = GridBagConstraints.VERTICAL;

        p.add(Box.createVerticalGlue(), gbc);
        
        return p;
    }
    
    // Helpers construccion
    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(200, 200, 200));
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
    
}
