package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class VentanaInicio extends JFrame {

    public JButton btnPeliculas, btnClientes, btnSalas, btnCartelera, btnVender, btnCerrarSesion;
    public JPanel panelCentral;

    public VentanaInicio() {
        configFrame();
        initComponents();
    }

    private void configFrame() {
        setTitle("ES-Cine");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        java.net.URL iconURL = getClass().getResource("/resources/entrada-de-cineIcon.png");
    if (iconURL != null) {
    setIconImage(new javax.swing.ImageIcon(iconURL).getImage());
}
        
    }

    private void initComponents() {
        add(crearHeader(), BorderLayout.NORTH);
        add(crearSideBar(), BorderLayout.WEST);
        
        panelCentral = new JPanel();
        panelCentral.setBackground(Color.WHITE);
        JLabel lblBienvenida = new JLabel("Selecciona una opción del menú", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblBienvenida.setForeground(Color.GRAY);
        panelCentral.add(lblBienvenida);
        add(panelCentral, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 30, 30));
        header.setPreferredSize(new Dimension(0, 150));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        java.net.URL imgURL = getClass().getResource("/resources/entrada-de-cine1.png");
        
        JLabel titulo = new JLabel("ES-Cine");
        if (imgURL != null){
            titulo.setIcon(new javax.swing.ImageIcon(imgURL));
        }
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);

        return header;
    }

    private JPanel crearSideBar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(45, 45, 45));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        btnPeliculas   = crearBotonSidebar("Películas", "camara-de-videopetite.png");
        btnClientes    = crearBotonSidebar("Clientes", "usuario_petite.png");
        btnSalas       = crearBotonSidebar("Salas", "sala-de-conferenciaspetite.png");
        btnCartelera   = crearBotonSidebar("Cartelera","calendariopetite.png");
        btnVender      = crearBotonSidebar("vender", "monedapetite.png");

        sidebar.add(btnPeliculas);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnClientes);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnSalas);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnCartelera);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnVender);
        sidebar.add(Box.createVerticalGlue());

        btnCerrarSesion = crearBotonSidebar(" Cerrar sesión", "cerrar-sesionpetite.png");
        btnCerrarSesion.setBackground(new Color(180, 40, 40));
        sidebar.add(btnCerrarSesion);

        return sidebar;
    }

    private JButton crearBotonSidebar(String texto, String rutaIcono) {
    JButton b = new JButton(texto);
    b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    b.setBackground(new Color(70, 70, 70));
    b.setForeground(Color.WHITE);
    b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    b.setBorderPainted(false);
    b.setFocusPainted(false);
    b.setHorizontalAlignment(SwingConstants.LEFT);

    java.net.URL imgURL = getClass().getResource("/resources/"+ rutaIcono);
    
    if (imgURL != null) {
        b.setIcon(new javax.swing.ImageIcon(imgURL));
    }

    return b;
}
 
}
