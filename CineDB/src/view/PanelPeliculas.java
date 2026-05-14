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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import models.Movie;

public class PanelPeliculas extends JPanel{
    private JPanel panelPrincipal, panelRegistrar;
    public DefaultTableModel dtmPeliculas;
    private JTable tablaPeliculas;
    private JScrollPane sp;
    public JButton btnCrear, btnLeer, btnActualizar, btnEliminar, btnGuardar, btnCancelar;
    public JTextField txtNombre, txtGenero, txtDuracion, txtFormato, txtIdioma;
    public CardLayout card;
    String[] cols = { "ID", "Nombre", "Genero", "Duracion", "Formato", "Idioma" };
    String[][] rows = { 
        {"01", "Batman", "Accion", "2:07:00", ".mkv", "español"},
        {"02", "Al filo del mañana", "Sci-fi", "1:51:00", ".mp4", "ingles-subtitulado_español"}
    };
    
    public PanelPeliculas() {                        
        initComponents();        
        eventosCard();
    }
            
    private void initComponents() {
        // Construir Interfaz
        card = new CardLayout();
        setLayout(card);
        panelPrincipal = crearPanelPrincipal();
        panelRegistrar = crearPanelAgregar();
        
        add(panelPrincipal, "PRINCIPAL");
        add(panelRegistrar, "REGISTRAR");
        
        card.show(this, "PRINCIPAL");
    }        
    
    //----- CREAR PANELES PARA LAS FUNCIONES ---------------------------
    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(254, 254, 254));
        
        JLabel titulo = new JLabel("PELICULAS");
        titulo.setFont(new Font("Segeo UI", Font.BOLD, 42));        
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
                
        dtmPeliculas = new DefaultTableModel(null, cols);
        tablaPeliculas = new JTable(dtmPeliculas);
        sp = new JScrollPane(tablaPeliculas);
        
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
        
        p.add(titulo, BorderLayout.NORTH);
        p.add(sp, BorderLayout.CENTER);
        p.add(panelBotones, BorderLayout.SOUTH);
        
        return p;
    }    
    
    private JPanel crearPanelAgregar() {                
        JPanel fondo = new JPanel(new BorderLayout());
                        
        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        JLabel lblTitulo = new JLabel("Registrar Pelicula");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        JLabel lbl1 = crearLabel("Nombre:");
        JLabel lbl2 = crearLabel("Genero:");
        JLabel lbl3 = crearLabel("Duracion:");
        JLabel lbl4 = crearLabel("Formato:");
        JLabel lbl5 = crearLabel("Idioma:");                
        txtNombre = crearTxt(100);
        txtGenero = crearTxt(100);
        txtDuracion = crearTxt(100);
        txtFormato = crearTxt(100);
        txtIdioma = crearTxt(100);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));        
        btnGuardar = crearBtn("Guardar");
        btnCancelar = crearBtn("Cancelar");
        
        formulario.add(lblTitulo);
        formulario.add(lbl1);
        formulario.add(txtNombre);
        formulario.add(lbl2);
        formulario.add(txtGenero);
        formulario.add(lbl3);
        formulario.add(txtDuracion);
        formulario.add(lbl4);
        formulario.add(txtFormato);
        formulario.add(lbl5);
        formulario.add(txtIdioma);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        fondo.add(formulario, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);
        
        return fondo;
    }
    
    //----- Metodos Auxiliares para CRUD pelicula ----------------------
    public Movie getFormData() {
        return new Movie(
            txtNombre.getText(),
            txtGenero.getText(),
            txtDuracion.getText(),
            txtFormato.getText(),
            txtIdioma.getText()
        );
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtGenero.setText("");
        txtDuracion.setText("");
        txtFormato.setText("");
        txtIdioma.setText("");
    }
    
    //----- Eventos del cardlayout ------------------------------------
    private void eventosCard() {
        btnCrear.addActionListener(e -> card.show(this, "REGISTRAR"));
        btnCancelar.addActionListener(e -> card.show(this, "PRINCIPAL"));
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
    
    private JLabel crearLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        return lbl;
    }
    
    private JTextField crearTxt(int lenght) {
        JTextField txt = new JTextField(lenght);
        
        return txt;
    }
    
    // Metodo temporal
    private JButton crearBtn(String text) {
        JButton btn = new JButton(text);
        
        return btn;
    }
}
