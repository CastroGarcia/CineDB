package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import models.Asiento;
import models.Funcion;
import models.Movie;

public class PanelVender extends JPanel {

    // ── Cards ─────────────────────────────────────────────────────────────────
    public CardLayout card;

    // ── Paso 1: ¿Cliente registrado? ─────────────────────────────────────────
    public JButton btnSi, btnNo;

    // ── Paso 2a: Buscar por CURP ──────────────────────────────────────────────
    public JTextField txtCurpVenta;
    public JButton    btnBuscarCliente, btnCancelarCurp;

    // ── Paso 2b: Solo edad ────────────────────────────────────────────────────
    public JTextField txtEdadVenta;
    public JButton    btnConfirmarEdad, btnCancelarEdad;

    // ── Paso 3: Seleccionar película ──────────────────────────────────────────
    public DefaultTableModel dtmPeliculas;
    public JTable            tablaPeliculas;
    public JButton           btnSeleccionarPelicula, btnVolverPelicula;

    // ── Paso 4: Seleccionar función ───────────────────────────────────────────
    public DefaultTableModel dtmFunciones;
    public JTable            tablaFunciones;
    public JButton           btnSeleccionarFuncion, btnVolverFuncion;

    // ── Paso 5: Seleccionar asientos ──────────────────────────────────────────
    public JPanel            gridAsientos;
    public JLabel            lblInfoAsientos;
    public JButton           btnConfirmarAsientos, btnVolverAsientos;
    public List<Asiento>     asientosSeleccionados = new ArrayList<>();

    // ── Paso 6: Boleto ────────────────────────────────────────────────────────
    public JPanel  panelBoleto;
    public JButton btnNuevaVenta;

    // ── Colors for seat grid ──────────────────────────────────────────────────
    private static final Color COL_DISPONIBLE  = new Color(220, 50, 50);
    private static final Color COL_SELECCIONADO= new Color(40, 160, 80);
    private static final Color COL_BLOQUEADO   = new Color(100, 100, 110);
    private static final Color COL_BG_GRID     = new Color(30, 30, 30);

    public PanelVender() {
        initComponents();
    }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        setBackground(Theme.CONTENT_BG);

        add(crearPaso1(),          "PASO1");
        add(crearPaso2Si(),        "PASO2_SI");
        add(crearPaso2No(),        "PASO2_NO");
        add(crearPaso3(),          "PASO3");
        add(crearPaso4(),          "PASO4");
        add(crearPaso5(),          "PASO5");
        add(crearPaso6(),          "PASO6");

        card.show(this, "PASO1");
    }

    // ── PASO 1: ¿El cliente está registrado? ─────────────────────────────────

    private JPanel crearPaso1() {
        JPanel root = wrapper();
        root.add(crearHeader("Vender"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Theme.CONTENT_BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(36, 48, 36, 48)
        ));

        JLabel ico = new JLabel("🎟", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        ico.setAlignmentX(CENTER_ALIGNMENT);

        JLabel pregunta = new JLabel("¿El cliente tiene membresía registrada?", SwingConstants.CENTER);
        pregunta.setFont(new Font("Segoe UI", Font.BOLD, 17));
        pregunta.setForeground(Theme.TEXT_PRIMARY);
        pregunta.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Con membresía se aplica un 20% de descuento", SwingConstants.CENTER);
        sub.setFont(Theme.FONT_SMALL);
        sub.setForeground(Theme.TEXT_SECONDARY);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btns.setBackground(Theme.SURFACE);
        btnSi = Theme.primaryButton("✔  Sí, está registrado");
        btnNo = Theme.ghostButton("✘  No, continuar sin membresía");
        btns.add(btnSi);
        btns.add(btnNo);

        card.add(ico);
        card.add(Box.createVerticalStrut(16));
        card.add(pregunta);
        card.add(Box.createVerticalStrut(8));
        card.add(sub);
        card.add(Box.createVerticalStrut(28));
        card.add(btns);

        centro.add(card);
        root.add(centro, BorderLayout.CENTER);
        return root;
    }

    // ── PASO 2a: Buscar cliente por CURP ─────────────────────────────────────

    private JPanel crearPaso2Si() {
        JPanel root = wrapper();
        root.add(crearHeader("Vender"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Theme.CONTENT_BG);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Theme.SURFACE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(32, 48, 32, 48)
        ));

        JLabel titulo = new JLabel("Ingresar CURP del cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(Theme.TEXT_PRIMARY);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        txtCurpVenta = Theme.styledField("Ej. GOAM900101HDFLRS01", 26);
        txtCurpVenta.setMaximumSize(new Dimension(360, 38));
        txtCurpVenta.setAlignmentX(CENTER_ALIGNMENT);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        row.setBackground(Theme.SURFACE);
        btnBuscarCliente = Theme.primaryButton("Buscar");
        btnCancelarCurp  = Theme.ghostButton("← Regresar");
        row.add(btnBuscarCliente);
        row.add(btnCancelarCurp);

        cardPanel.add(titulo);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(txtCurpVenta);
        cardPanel.add(Box.createVerticalStrut(16));
        cardPanel.add(row);

        centro.add(cardPanel);
        root.add(centro, BorderLayout.CENTER);
        return root;
    }

    // ── PASO 2b: Ingresar edad directamente ──────────────────────────────────

    private JPanel crearPaso2No() {
        JPanel root = wrapper();
        root.add(crearHeader("Vender"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Theme.CONTENT_BG);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Theme.SURFACE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(32, 48, 32, 48)
        ));

        JLabel titulo = new JLabel("¿Cuál es la edad del cliente?", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(Theme.TEXT_PRIMARY);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Menores de 18 años pagan $40 · Adultos pagan $60", SwingConstants.CENTER);
        sub.setFont(Theme.FONT_SMALL);
        sub.setForeground(Theme.TEXT_SECONDARY);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        txtEdadVenta = Theme.styledField("Edad", 10);
        txtEdadVenta.setMaximumSize(new Dimension(200, 38));
        txtEdadVenta.setAlignmentX(CENTER_ALIGNMENT);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        row.setBackground(Theme.SURFACE);
        btnConfirmarEdad = Theme.primaryButton("Continuar");
        btnCancelarEdad  = Theme.ghostButton("← Regresar");
        row.add(btnConfirmarEdad);
        row.add(btnCancelarEdad);

        cardPanel.add(titulo);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(sub);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(txtEdadVenta);
        cardPanel.add(Box.createVerticalStrut(16));
        cardPanel.add(row);

        centro.add(cardPanel);
        root.add(centro, BorderLayout.CENTER);
        return root;
    }

    // ── PASO 3: Seleccionar película ─────────────────────────────────────────

    private JPanel crearPaso3() {
        JPanel root = wrapper();
        root.add(crearHeader("Vender — Seleccionar película"), BorderLayout.NORTH);

        dtmPeliculas = new DefaultTableModel(
            null, new String[]{"ID", "Nombre", "Género", "Duración", "Formato", "Idioma"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPeliculas = PanelClientes.estilizarTabla(new JTable(dtmPeliculas));
        Theme.centerTable(tablaPeliculas);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        body.add(PanelClientes.crearScrollPane(tablaPeliculas), BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);

        btnSeleccionarPelicula = Theme.primaryButton("Seleccionar →");
        btnVolverPelicula      = Theme.ghostButton("← Regresar");
        root.add(crearBarra(btnVolverPelicula, btnSeleccionarPelicula), BorderLayout.SOUTH);
        return root;
    }

    // ── PASO 4: Seleccionar función ──────────────────────────────────────────

    private JPanel crearPaso4() {
        JPanel root = wrapper();
        root.add(crearHeader("Vender — Seleccionar función"), BorderLayout.NORTH);

        dtmFunciones = new DefaultTableModel(
            null, new String[]{"ID", "Película", "Sala", "Inicio", "Fin"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaFunciones = PanelClientes.estilizarTabla(new JTable(dtmFunciones));
        Theme.centerTable(tablaFunciones);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));
        body.add(PanelClientes.crearScrollPane(tablaFunciones), BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);

        btnSeleccionarFuncion = Theme.primaryButton("Seleccionar asientos →");
        btnVolverFuncion      = Theme.ghostButton("← Regresar");
        root.add(crearBarra(btnVolverFuncion, btnSeleccionarFuncion), BorderLayout.SOUTH);
        return root;
    }

    // ── PASO 5: Seleccionar asientos ─────────────────────────────────────────

    private JPanel crearPaso5() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COL_BG_GRID);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COL_BG_GRID);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 6, 20));
        JLabel titulo = new JLabel("Seleccionar asientos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        // Pantalla
        JLabel screen = new JLabel("▬▬▬▬  PANTALLA  ▬▬▬▬", SwingConstants.CENTER);
        screen.setFont(new Font("Segoe UI", Font.BOLD, 13));
        screen.setForeground(Color.LIGHT_GRAY);
        screen.setBorder(BorderFactory.createEmptyBorder(4, 0, 10, 0));

        // Grid
        gridAsientos = new JPanel();
        gridAsientos.setBackground(COL_BG_GRID);

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setBackground(COL_BG_GRID);
        centerWrap.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        centerWrap.add(screen, BorderLayout.NORTH);
        centerWrap.add(new JScrollPane(gridAsientos) {{
            setBackground(COL_BG_GRID);
            getViewport().setBackground(COL_BG_GRID);
            setBorder(BorderFactory.createEmptyBorder());
        }}, BorderLayout.CENTER);
        root.add(centerWrap, BorderLayout.CENTER);

        // Bottom bar
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(20, 20, 30));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblInfoAsientos = new JLabel("Selecciona tus asientos");
        lblInfoAsientos.setFont(Theme.FONT_BODY);
        lblInfoAsientos.setForeground(Color.WHITE);

        JPanel botonesBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botonesBar.setBackground(new Color(20, 20, 30));
        btnVolverAsientos    = Theme.ghostButton("← Regresar");
        btnConfirmarAsientos = Theme.primaryButton("Confirmar compra");
        botonesBar.add(btnVolverAsientos);
        botonesBar.add(btnConfirmarAsientos);

        // Leyenda
        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leyenda.setBackground(new Color(20, 20, 30));
        leyenda.add(legendItem(COL_DISPONIBLE,  "Disponible"));
        leyenda.add(legendItem(COL_SELECCIONADO,"Seleccionado"));
        leyenda.add(legendItem(COL_BLOQUEADO,   "No disponible"));

        JPanel leftBar = new JPanel(new BorderLayout());
        leftBar.setBackground(new Color(20, 20, 30));
        leftBar.add(leyenda,          BorderLayout.NORTH);
        leftBar.add(lblInfoAsientos,  BorderLayout.SOUTH);

        bottom.add(leftBar,    BorderLayout.WEST);
        bottom.add(botonesBar, BorderLayout.EAST);
        root.add(bottom, BorderLayout.SOUTH);

        return root;
    }

    private JPanel legendItem(Color color, String texto) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setBackground(new Color(20, 20, 30));
        JPanel cuadro = new JPanel();
        cuadro.setBackground(color);
        cuadro.setPreferredSize(new Dimension(14, 14));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(Theme.FONT_SMALL);
        lbl.setForeground(Color.LIGHT_GRAY);
        p.add(cuadro);
        p.add(lbl);
        return p;
    }

    // ── PASO 6: Boleto ────────────────────────────────────────────────────────

    private JPanel crearPaso6() {
        JPanel root = wrapper();
        root.add(crearHeader("Vender — Boleto generado"), BorderLayout.NORTH);

        panelBoleto = new JPanel();
        panelBoleto.setLayout(new BoxLayout(panelBoleto, BoxLayout.Y_AXIS));
        panelBoleto.setBackground(Theme.CONTENT_BG);

        JPanel scrollWrap = new JPanel(new GridBagLayout());
        scrollWrap.setBackground(Theme.CONTENT_BG);
        scrollWrap.add(panelBoleto);

        JScrollPane sp = new JScrollPane(scrollWrap);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Theme.CONTENT_BG);
        root.add(sp, BorderLayout.CENTER);

        btnNuevaVenta = Theme.primaryButton("+ Nueva venta");
        root.add(crearBarra(btnNuevaVenta), BorderLayout.SOUTH);
        return root;
    }

    // ── Public helpers ────────────────────────────────────────────────────────

    /** Reconstruye el grid de asientos según la lista recibida. */
    public void cargarGridAsientos(List<Asiento> asientos) {
        asientosSeleccionados.clear();
        gridAsientos.removeAll();

        if (asientos.isEmpty()) {
            gridAsientos.add(new JLabel("Sin asientos disponibles.") {{
                setForeground(Color.WHITE);
            }});
            gridAsientos.revalidate();
            gridAsientos.repaint();
            return;
        }

        int maxCol = asientos.stream().mapToInt(Asiento::getColumna).max().orElse(10);
        gridAsientos.setLayout(new GridLayout(0, maxCol, 5, 5));
        gridAsientos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Asiento a : asientos) {
            JButton btn = new JButton(a.getLabel());
            btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            btn.setPreferredSize(new Dimension(52, 40));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            boolean bloqueado = a.getEstado() != Asiento.Estado.DISPONIBLE;

            if (bloqueado) {
                btn.setBackground(COL_BLOQUEADO);
                btn.setEnabled(false);
            } else {
                btn.setBackground(COL_DISPONIBLE);
                btn.addActionListener(e -> toggleAsiento(btn, a));
            }
            gridAsientos.add(btn);
        }
        gridAsientos.revalidate();
        gridAsientos.repaint();
        actualizarInfoAsientos();
    }

    private void toggleAsiento(JButton btn, Asiento a) {
        if (asientosSeleccionados.contains(a)) {
            asientosSeleccionados.remove(a);
            btn.setBackground(COL_DISPONIBLE);
        } else {
            asientosSeleccionados.add(a);
            btn.setBackground(COL_SELECCIONADO);
        }
        actualizarInfoAsientos();
    }

    public void actualizarInfoAsientos() {
        int n = asientosSeleccionados.size();
        if (n == 0) {
            lblInfoAsientos.setText("Selecciona tus asientos");
        } else {
            StringBuilder sb = new StringBuilder(n + " asiento(s): ");
            asientosSeleccionados.forEach(a -> sb.append(a.getLabel()).append("  "));
            lblInfoAsientos.setText(sb.toString().trim());
        }
    }

    /** Construye y muestra el boleto en el panel de boleto. */
    public void mostrarBoleto(String nombrePelicula, int numSala, Funcion funcion,
                               List<Asiento> asientos, int edad, boolean tieneMembership,
                               String nombreCliente) {
        panelBoleto.removeAll();

        double precioPorAsiento = edad < 18 ? 40.0 : 60.0;
        double descuento        = tieneMembership ? 0.20 : 0.0;
        double precioFinal      = precioPorAsiento * (1 - descuento);
        double total            = precioFinal * asientos.size();

        // Card del boleto
        JPanel boletoCard = new JPanel();
        boletoCard.setLayout(new BoxLayout(boletoCard, BoxLayout.Y_AXIS));
        boletoCard.setBackground(Theme.SURFACE);
        boletoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(28, 36, 28, 36)
        ));
        boletoCard.setAlignmentX(CENTER_ALIGNMENT);

        // --- Encabezado ---
        JLabel cabecera = new JLabel("🎬  CINEFAN  —  BOLETO DE COMPRA", SwingConstants.CENTER);
        cabecera.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cabecera.setForeground(Theme.RED_PRIMARY);
        cabecera.setAlignmentX(CENTER_ALIGNMENT);

        JSeparator sep1 = new JSeparator();
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep1.setForeground(Theme.BORDER);

        boletoCard.add(cabecera);
        boletoCard.add(Box.createVerticalStrut(12));
        boletoCard.add(sep1);
        boletoCard.add(Box.createVerticalStrut(16));

        // --- Info ---
        if (nombreCliente != null && !nombreCliente.isBlank()) {
            boletoCard.add(filaInfo("Cliente",   nombreCliente));
            boletoCard.add(Box.createVerticalStrut(8));
        }
        boletoCard.add(filaInfo("Película",  nombrePelicula));
        boletoCard.add(Box.createVerticalStrut(8));
        boletoCard.add(filaInfo("Sala",      "Sala " + numSala));
        boletoCard.add(Box.createVerticalStrut(8));
        boletoCard.add(filaInfo("Horario",   funcion.getHorario()));
        boletoCard.add(Box.createVerticalStrut(8));

        // Asientos
        StringBuilder seatsStr = new StringBuilder();
        asientos.forEach(a -> seatsStr.append(a.getLabel()).append("   "));
        boletoCard.add(filaInfo("Asiento(s)", seatsStr.toString().trim()));
        boletoCard.add(Box.createVerticalStrut(16));

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setForeground(Theme.BORDER);
        boletoCard.add(sep2);
        boletoCard.add(Box.createVerticalStrut(14));

        // --- Precios ---
        String tipoCliente = edad < 18 ? "Menor" : "Adulto";
        boletoCard.add(filaInfo("Tipo de cliente",     tipoCliente));
        boletoCard.add(Box.createVerticalStrut(6));
        boletoCard.add(filaInfo("Precio por asiento",
            String.format("$%.2f", precioPorAsiento)));
        boletoCard.add(Box.createVerticalStrut(6));

        if (tieneMembership) {
            boletoCard.add(filaInfo("Membresía (20% desc.)",
                String.format("-$%.2f por asiento", precioPorAsiento * descuento)));
            boletoCard.add(Box.createVerticalStrut(6));
            boletoCard.add(filaInfo("Precio con descuento",
                String.format("$%.2f por asiento", precioFinal)));
            boletoCard.add(Box.createVerticalStrut(6));
        }

        boletoCard.add(Box.createVerticalStrut(8));

        // Total destacado
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(new Color(245, 235, 235));
        totalPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.RED_PRIMARY, 1),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        totalPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        JLabel lblTotalKey = new JLabel("TOTAL  (" + asientos.size() + " asiento(s))");
        lblTotalKey.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalKey.setForeground(Theme.TEXT_PRIMARY);
        JLabel lblTotalVal = new JLabel(String.format("$%.2f MXN", total));
        lblTotalVal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalVal.setForeground(Theme.RED_PRIMARY);
        totalPanel.add(lblTotalKey, BorderLayout.WEST);
        totalPanel.add(lblTotalVal, BorderLayout.EAST);
        totalPanel.setAlignmentX(CENTER_ALIGNMENT);

        boletoCard.add(totalPanel);

        panelBoleto.add(Box.createVerticalStrut(20));
        panelBoleto.add(boletoCard);
        panelBoleto.add(Box.createVerticalStrut(20));
        panelBoleto.revalidate();
        panelBoleto.repaint();
    }

    // ── Internal helpers ──────────────────────────────────────────────────────

    private JPanel filaInfo(String clave, String valor) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(Theme.SURFACE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        row.setAlignmentX(CENTER_ALIGNMENT);

        JLabel k = new JLabel(clave);
        k.setFont(new Font("Segoe UI", Font.BOLD, 13));
        k.setForeground(Theme.TEXT_SECONDARY);
        k.setPreferredSize(new Dimension(190, 20));

        JLabel v = new JLabel(valor);
        v.setFont(Theme.FONT_BODY);
        v.setForeground(Theme.TEXT_PRIMARY);

        row.add(k, BorderLayout.WEST);
        row.add(v, BorderLayout.CENTER);
        return row;
    }

    private JPanel wrapper() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);
        return p;
    }

    private JPanel crearHeader(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.SURFACE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        p.add(Theme.titleLabel(titulo), BorderLayout.WEST);
        return p;
    }

    private JPanel crearBarra(JButton... btns) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        p.setBackground(Theme.SURFACE);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        for (JButton b : btns) p.add(b);
        return p;
    }
}