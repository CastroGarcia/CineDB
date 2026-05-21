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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.Sala;

public class PanelSalas extends JPanel {
    private JPanel panelPrincipal, panelRegistrar, panelBuscar;
    public DefaultTableModel dtmSalas;
    public JTable tablaSalas, tablaBuscar;
    private JScrollPane spPrincipal, spBuscar;
    public JButton btnCrear, btnLeer, btnActualizar, btnEliminar,
            btnGuardar, btnCancelar, btnBuscar, btnRegresar;
    public JTextField txtNumSala, txtAsientos, txtIdFunciones, txtBuscador;
    public JCheckBox chkDisponible;
    public CardLayout card;
    public JLabel lblTituloPanelAgregar;
    String[] cols = {"Num Sala", "Asientos", "ID Funcion", "Disponible"};
    public int numSalaEditando = -1;

    public PanelSalas() {
        initComponents();
    }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        panelPrincipal = crearPanelPrincipal();
        panelRegistrar = crearPanelAgregar();
        panelBuscar    = crearPanelBuscar();

        add(panelPrincipal, "PRINCIPAL");
        add(panelRegistrar, "REGISTRAR");
        add(panelBuscar,    "BUSCAR");

        card.show(this, "PRINCIPAL");
    }

    // ----- PANELES -------------------------------------------------------
    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(254, 254, 254));

        JLabel titulo = new JLabel("SALAS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));

        dtmSalas = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaSalas  = new JTable(dtmSalas);
        spPrincipal = new JScrollPane(tablaSalas);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.red);
        btnCrear      = crearBoton("Agregar",   "registro.png");
        btnLeer       = crearBoton("Mostrar",   "consulta.png");
        btnActualizar = crearBoton("Gestionar", "editar.png");
        btnEliminar   = crearBoton("Borrar",    "basura.png");
        panelBotones.add(btnCrear);
        panelBotones.add(btnLeer);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        p.add(titulo,       BorderLayout.NORTH);
        p.add(spPrincipal,  BorderLayout.CENTER);
        p.add(panelBotones, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearPanelAgregar() {
        JPanel fondo = new JPanel(new BorderLayout());

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        lblTituloPanelAgregar = new JLabel();
        lblTituloPanelAgregar.setFont(new Font("Segoe UI", Font.BOLD, 28));

        txtNumSala     = crearTxt("Numero de sala", 100);
        txtAsientos    = crearTxt("Numero de asientos", 100);
        txtIdFunciones = crearTxt("ID de la funcion asignada", 100);
        chkDisponible  = new JCheckBox("Disponible");
        chkDisponible.setSelected(true);
        chkDisponible.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnGuardar  = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        formulario.add(lblTituloPanelAgregar);
        formulario.add(crearLabel("Numero de Sala:"));
        formulario.add(txtNumSala);
        formulario.add(crearLabel("Asientos:"));
        formulario.add(txtAsientos);
        formulario.add(crearLabel("ID Funcion:"));
        formulario.add(txtIdFunciones);
        formulario.add(chkDisponible);

        fondo.add(formulario,   BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);
        return fondo;
    }

    private JPanel crearPanelBuscar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(254, 254, 254));

        JPanel panelBuscador = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel titulo = new JLabel("Buscar Sala", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        txtBuscador = crearTxt("Buscar por numero de sala", 50);
        btnBuscar   = new JButton("Buscar");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelBuscador.add(titulo, gbc);
        gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        panelBuscador.add(txtBuscador, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1;
        panelBuscador.add(btnBuscar, gbc);

        tablaBuscar = new JTable(dtmSalas);
        spBuscar    = new JScrollPane(tablaBuscar);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.red);
        btnRegresar = new JButton("Regresar");
        panelBotones.add(btnRegresar);

        p.add(panelBuscador, BorderLayout.NORTH);
        p.add(spBuscar,      BorderLayout.CENTER);
        p.add(panelBotones,  BorderLayout.SOUTH);
        return p;
    }

    // ----- Modos ---------------------------------------------------------
    public void activarModoRegistro() {
        numSalaEditando = -1;
        lblTituloPanelAgregar.setText("Registrar Sala");
        limpiarFormulario();
        card.show(this, "REGISTRAR");
    }

    public void activarModoEdicion(int numSala, int asientos, int idFunciones, boolean disponible) {
        numSalaEditando = numSala;
        txtNumSala.setText(String.valueOf(numSala));
        txtNumSala.setForeground(Color.BLACK);
        txtAsientos.setText(String.valueOf(asientos));
        txtAsientos.setForeground(Color.BLACK);
        txtIdFunciones.setText(String.valueOf(idFunciones));
        txtIdFunciones.setForeground(Color.BLACK);
        chkDisponible.setSelected(disponible);
        lblTituloPanelAgregar.setText("Editar Sala");
        card.show(this, "REGISTRAR");
    }

    public boolean esModoEdicion() { return numSalaEditando != -1; }

    public void cancelarAccion() {
        numSalaEditando = -1;
        limpiarFormulario();
        card.show(this, "PRINCIPAL");
    }

    // ----- Datos del formulario ------------------------------------------
    public Sala getFormData() {
        int numSala = 0, asientos = 0, idFunc = 0;
        try { numSala  = Integer.parseInt(txtNumSala.getText().trim()); }     catch (NumberFormatException ignored) {}
        try { asientos = Integer.parseInt(txtAsientos.getText().trim()); }    catch (NumberFormatException ignored) {}
        try { idFunc   = Integer.parseInt(txtIdFunciones.getText().trim()); } catch (NumberFormatException ignored) {}
        return new Sala(numSala, asientos, idFunc, chkDisponible.isSelected());
    }

    public void limpiarFormulario() {
        txtNumSala.setText("Numero de sala");
        txtNumSala.setForeground(Color.GRAY);
        txtAsientos.setText("Numero de asientos");
        txtAsientos.setForeground(Color.GRAY);
        txtIdFunciones.setText("ID de la funcion asignada");
        txtIdFunciones.setForeground(Color.GRAY);
        chkDisponible.setSelected(true);
    }

    // ----- Helpers -------------------------------------------------------
    private JButton crearBoton(String texto, String iconPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + iconPath));
        JButton b = new JButton(texto, icon);
        b.setBackground(Color.red);
        b.setForeground(Color.white);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(200, b.getPreferredSize().height));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(Color.blue); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(Color.red);  }
        });
        return b;
    }

    private JLabel crearLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return lbl;
    }

    private JTextField crearTxt(String placeholder, int length) {
        JTextField txt = new JTextField(placeholder, length);
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
}