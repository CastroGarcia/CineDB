package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class VentanaInicio extends JFrame{    
    public PanelClientes panelClientes;
    public PanelPeliculas panelPeliculas;
    public JPanel panelFondo, panelCentral, panelSideBar, panelHeader;
    public JButton btnClientes, btnPeliculas, btnFunciones, btnCartelera, 
            btnSalas, btnVender, btnSalir, btnCrear, btnLeer, btnActualizar, 
            btnEliminar;
    public JLabel lblTitulo;
    public JTextField txt1;
    public CardLayout card;
    public DefaultTableModel dtmClientes;
    public JTable tablaClientes;
    
    public VentanaInicio() {
        configFrame();
        initComponents();
    }
    
    private void configFrame() {
        setLayout(new BorderLayout());
        setTitle("Cinefan");
        //setExtendedState(MAXIMIZED_BOTH);
        setSize(1200, 700);                        
        setIconImage(new ImageIcon(getClass().getResource("/resources/entrada-de-cine.png")).getImage());
        setUndecorated(true);
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
    
    //--------- CREACION DE PANELES ------------------------------------
    private JPanel crearPanelHeader() {
        JPanel p = new JPanel(new FlowLayout()); 
        p.setPreferredSize(new Dimension(0, 130));
        p.setBackground(Color.red);
        
        JLabel imgLogo = new JLabel(resizeImage("entrada-de-cine.png", 124, 124), SwingConstants.CENTER);
        JLabel titulo = crearLabel("Cinefan");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 52));
        titulo.setForeground(Color.white);
        
        p.add(imgLogo);
        p.add(titulo);
        
        return p;
    }
    
    private JPanel crearSideBar() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.orange);
        
        btnClientes = crearBotonSideBar("Clientes", "anadir-contacto.png");
        btnPeliculas = crearBotonSideBar("Peliculas", "carrete-de-pelicula.png");
        btnFunciones = crearBotonSideBar("Funciones", "tiempo-de-la-funcion.png");
        btnCartelera = crearBotonSideBar("Carteleras", "pelicula.png");
        btnSalas = crearBotonSideBar("Sala", "cine.png");
        btnVender = crearBotonSideBar("Vender", "entradas.png");
        btnSalir = crearBotonSideBar("Salir", "salida.png");
        
        p.add(btnClientes);
        p.add(btnPeliculas);
        p.add(btnFunciones);
        p.add(btnCartelera);
        p.add(btnSalas);
        p.add(btnVender);
        p.add(Box.createVerticalStrut(260));
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        //separator.setForeground(Color.white);
        p.add(separator);       
        p.add(btnSalir);
        
        return p;        
    }
    
    private JPanel crearPanelCentral() {
        card = new CardLayout();
        JPanel p = new JPanel(card);        
        // PANELES
        panelClientes = new PanelClientes();
        panelPeliculas = new PanelPeliculas();
        
        p.add(panelClientes, "CLIENTES");
        p.add(panelPeliculas, "PELICULAS");
        
        card.show(p, "CLIENTES"); // Panel a mostrar por defecto
        
        return p;
    } 
    
    //----- Helpers construccion --------------------------------------
    private JButton crearBotonSideBar(String texto, String pathIcon) {
        JButton b = new JButton(texto, new ImageIcon(getClass().getResource("/resources/" + pathIcon)));              
        b.setBackground(Color.orange);
        b.setForeground(Color.white);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setHorizontalAlignment(SwingConstants.LEFT); // texto e icono a la izquierda
        b.setIconTextGap(10);                          // espacio entre icono y texto
        b.setMaximumSize(new Dimension(
            Integer.MAX_VALUE,
            b.getPreferredSize().height
        ));
        
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(Color.orange);
            }            
        });
        
        return b;
    }
    
    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        return lbl;
    }
    
    private JTextField crearCampo(int length) {
        JTextField campo = new JTextField(length);
        campo.setCaretColor(Color.ORANGE);
        
        return campo;
    }
    
    //----- Metodos auxiliares -------------------------------------
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        return new ImageIcon(img);
    }
    
    public void botonSalir() {
        int result = JOptionPane.showConfirmDialog(panelFondo, "Estas seguro de salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);        
        if(result == JOptionPane.NO_OPTION) return;
        
        System.exit(0);
    }
}
