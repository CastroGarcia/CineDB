package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaInicio extends JFrame{
    public JPanel panelFondo, panelCentral, panelSideBar, panelHeader;
    public JButton btn1, btn2, btn3, btn4;
    public JLabel lblTitulo;
    public JTextField txt1;
    
    public VentanaInicio() {
        configFrame();
        initComponents();
    }
    
    private void configFrame() {
        setLayout(new BorderLayout());
        setTitle("Inicio de sesion");
        //setExtendedState(MAXIMIZED_BOTH);
        setSize(1200, 700);                        
        //setIconImage(new ImageIcon("URL").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        panelFondo = new JPanel(new BorderLayout());
        panelHeader = crearPanelHeader();
        panelCentral = crearPanelCentral();
        panelSideBar = crearSideBar();

        panelFondo.add(panelHeader, BorderLayout.NORTH);
        panelFondo.add(panelSideBar, BorderLayout.WEST);
        panelFondo.add(panelCentral, BorderLayout.CENTER);
        add(panelFondo);
    }
    
    // Creacion de paneles
    private JPanel crearPanelHeader() {
        JPanel p = new JPanel();                
        
        
        return p;
    }
    
    private JPanel crearSideBar() {
        JPanel p = new JPanel();
        
        
        return p;        
    }
    
    private JPanel crearPanelCentral() {
        JPanel p = new JPanel();
        
        return p;
    }
    
    
    
    // Helpers construccion
    private JButton crearBotonSideBar(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(Color.orange);
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
    
}
