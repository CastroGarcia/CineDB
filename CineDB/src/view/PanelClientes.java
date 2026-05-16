package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PanelClientes extends JPanel{
    public DefaultTableModel dtmClientes;
    public JTable tablaClientes;
    private JScrollPane sp;
    public JButton btnSalir, btnCrear, btnLeer, btnActualizar, btnEliminar;
    public JTextField txtNombre;
    private CardLayout card;
    String[] cols = { "ID", "Nombre", "Edad", "Sexo", "Telefono", "Correo" };
    String[][] rows = { 
            {"01", "Jorge Eduardo Lopez", "18", "Hombre", "8341234567", "cinefan787639@gmail.com"},
            {"02", "jeremis rodriguez manguiano Lopez", "50", "Mujer", "8523234567", "cinefan787639@gmail.com"} 
        };
        
        
    public PanelClientes() {
        setBackground(new Color(254, 254, 254));
        initComponents();
    }
    
    private void initComponents() {                
        card = new CardLayout();   
        setLayout(card);
    }

    private JPanel crearPanelInicio() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("CLIENTES");
        titulo.setFont(new Font("Segeo UI", Font.BOLD, 42));
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
                
        dtmClientes = new DefaultTableModel(rows, cols);
        tablaClientes = new JTable(dtmClientes);          
        sp = new JScrollPane(tablaClientes);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.red);
        btnCrear = crearBoton("Crear", "registro.png");
        btnLeer = crearBoton("Leer", "consulta.png");
        btnActualizar = crearBoton("Actualizar", "editar.png");
        btnEliminar = crearBoton("Eliminar", "basura.png");
        panelBotones.add(btnCrear);
        panelBotones.add(btnLeer);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        
        add(titulo, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        return p;
    }
    
    private JPanel crearPanelCrear() {
        JPanel p = new JPanel();
        setBackground(new Color(254, 254, 254));
        
        
        
        return p;
    }    
    
    //----- Helpers construcction --------------------------------------
    private JButton crearBoton(String texto, String iconPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + iconPath));
        JButton b = new JButton(texto, icon);                        
        b.setBackground(Color.red);
        b.setForeground(Color.white);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(
            200,
            b.getPreferredSize().height
        ));
        
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(Color.red);
            }            
        });
        
        return b;
    }
    
}
