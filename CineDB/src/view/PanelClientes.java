package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.Client;

public class PanelClientes extends JPanel{
    private JPanel panelPrincipal, panelRegistrar, panelBuscar;
    public DefaultTableModel dtmClientes;
    public JTable tablaClientes, tablaBuscar;
    private JScrollPane spPrincipal, spBuscar;
    public JButton btnCrear, btnLeer, btnActualizar, btnEliminar, btnGuardar, 
            btnCancelar, btnBuscar, btnRegresar;
    public JTextField txtNombre, txtEdad, txtTelefono, txtCorreo, 
            txtIdMembership, txtBuscador;
    public CardLayout card;
    public JLabel lblTituloPanelAgregar;
    String[] cols = { "ID", "Nombre", "Edad", "Telefono", "Correo", "ID_Membresia"};
    public int idEditando = -1;
    
    public PanelClientes() {                        
        initComponents();       
    }
            
    private void initComponents() {
        // Construir Interfaz
        card = new CardLayout();
        setLayout(card);
        panelPrincipal = crearPanelPrincipal();
        panelRegistrar = crearPanelAgregar();
        panelBuscar = crearPanelBuscar();
        
        add(panelPrincipal, "PRINCIPAL");
        add(panelRegistrar, "REGISTRAR");
        add(panelBuscar, "BUSCAR");
        
        card.show(this, "PRINCIPAL");
    }        
    
    //----- CREAR PANELES PARA LAS FUNCIONES ---------------------------
    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(254, 254, 254));
        
        JLabel titulo = new JLabel("CLIENTES");
        titulo.setFont(new Font("Segeo UI", Font.BOLD, 42));        
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
                
        dtmClientes = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no es editable directamente
            }
        };
        tablaClientes = new JTable(dtmClientes);
        spPrincipal = new JScrollPane(tablaClientes);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.red);
        btnCrear = crearBoton("Nuevo", "registro.png");
        btnLeer = crearBoton("Buscar", "consulta.png");
        btnActualizar = crearBoton("Editar", "editar.png");
        btnEliminar = crearBoton("Eliminar", "basura.png");
        panelBotones.add(btnCrear);
        panelBotones.add(btnLeer);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        
        p.add(titulo, BorderLayout.NORTH);
        p.add(spPrincipal, BorderLayout.CENTER);
        p.add(panelBotones, BorderLayout.SOUTH);
        
        return p;
    }    
    
    private JPanel crearPanelAgregar() {                
        JPanel fondo = new JPanel(new BorderLayout());
                        
        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        lblTituloPanelAgregar = new JLabel();
        lblTituloPanelAgregar.setFont(new Font("Segoe UI", Font.BOLD, 28));
        JLabel lbl1 = crearLabel("Nombre:");
        JLabel lbl2 = crearLabel("Edad:");        
        JLabel lbl3 = crearLabel("Telefono:");
        JLabel lbl4 = crearLabel("Correo:");                
        JLabel lbl5 = crearLabel("ID_Membresia:");
        txtNombre = crearTxt("Ingresa tu nombre", 100);
        txtEdad = crearTxt("Ingresa tu edad", 100);        
        txtTelefono = crearTxt("Ingresa tu telefono celular", 100);
        txtCorreo = crearTxt("Ingresa tu correo electronico", 100);
        txtIdMembership = crearTxt("Ingresa el nivel de membresia", 100);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));        
        btnGuardar = crearBtn("Guardar");
        btnCancelar = crearBtn("Cancelar");
        
        formulario.add(lblTituloPanelAgregar);
        formulario.add(lbl1);
        formulario.add(txtNombre);
        formulario.add(lbl2);
        formulario.add(txtEdad);
        formulario.add(lbl3);
        formulario.add(txtTelefono);
        formulario.add(lbl4);
        formulario.add(txtCorreo);        
        formulario.add(lbl5);        
        formulario.add(txtIdMembership);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        fondo.add(formulario, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);
        
        return fondo;
    }
    
    private JPanel crearPanelBuscar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(254, 254, 254));
        
        JPanel panelBuscador = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();      
        gbc.insets = new Insets(5, 10, 5, 10);
        JLabel titulo = new JLabel("Buscar Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segeo UI", Font.BOLD, 42));
        txtBuscador = crearTxt("Buscar por ID de cliente", 50);
        btnBuscar = crearBtn("Buscar");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;                
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelBuscador.add(titulo, gbc);        
        gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        panelBuscador.add(txtBuscador, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1;
        panelBuscador.add(btnBuscar, gbc);
        
        tablaBuscar = new JTable(dtmClientes);
        spBuscar = new JScrollPane(tablaBuscar);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.red);
        btnRegresar = crearBtn("Regresar");
        panelBotones.add(btnRegresar);        
        
        p.add(panelBuscador, BorderLayout.NORTH);
        p.add(spBuscar, BorderLayout.CENTER);
        p.add(panelBotones, BorderLayout.SOUTH);
        
        return p;
    }
    
    //----- Modos (Registrar / Editar) -------------------------------
    public void activarModoRegistro() {
        idEditando = -1;
        lblTituloPanelAgregar.setText("Registrar Cliente");
        card.show(this, "REGISTRAR");
    }

    public void activarModoEdicion(int id, String nombre, String edad,
                                    String telefono, String correo,
                                    int id_membresia) {
        idEditando = id;
        txtNombre.setText(nombre);
        txtEdad.setText(edad);
        txtTelefono.setText(telefono);
        txtCorreo.setText(correo);
        txtIdMembership.setText(String.valueOf(id_membresia));
        lblTituloPanelAgregar.setText("Editar Cliente");
        card.show(this, "REGISTRAR");
    }

    public boolean esModoEdicion() {
        return idEditando != -1;
    }
    
    public void cancelarAccion() {
        idEditando = -1;
        limpiarFormulario();
        card.show(this, "PRINCIPAL");
    }
    
    //----- Metodos para los datos del formulario ----------------------
    //Recupera los datos de los campos del formulario
    public Client getFormData() {
        try {
            return new Client(
                txtNombre.getText(),
                txtEdad.getText(),
                txtTelefono.getText(),
                txtCorreo.getText(),
                Integer.parseInt(txtIdMembership.getText())
            );
        } catch (NumberFormatException e) {
            // Retorna null si el id no es un número válido
            return null;                        
        }
    }
    //Limpia los campos del formulario con los mensajes por defecto
    public void limpiarFormulario() {
        txtNombre.setText("Ingresa tu nombre");
        txtEdad.setText("Ingresa tu edad");
        txtTelefono.setText("Ingresa tu telefono celular");
        txtCorreo.setText("Ingresa tu correo electronico");
        txtIdMembership.setText("Ingresa el nivel de membresia");
        txtNombre.setForeground(Color.GRAY);
        txtEdad.setForeground(Color.GRAY);
        txtTelefono.setForeground(Color.GRAY);
        txtCorreo.setForeground(Color.GRAY);
        txtIdMembership.setForeground(Color.GRAY);
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
    
    private JTextField crearTxt(String placeholder, int lenght) {
        JTextField txt = new JTextField(placeholder, lenght);        
        txt.setForeground(Color.GRAY);
        
        txt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt.getText().equals(placeholder)) {
                    txt.setText("");
                    txt.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txt.getText().isEmpty()) {
                    txt.setText(placeholder);
                    txt.setForeground(Color.GRAY);
                }
            }
        });
        
        return txt;
    }
    
    // Metodo temporal para los botones del formulario
    private JButton crearBtn(String text) {
        JButton btn = new JButton(text);
        
        return btn;
    }
}
