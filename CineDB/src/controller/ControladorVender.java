package controller;

import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;
import models.Asiento;
import models.Client;
import models.Funcion;
import models.Movie;
import models.DataAccessObjects.AsientoDAO;
import models.DataAccessObjects.ClientDAO;
import models.DataAccessObjects.FuncionDAO;
import models.DataAccessObjects.MovieDAO;
import view.PanelVender;

public class ControladorVender {

    private final PanelVender view;
    private final Connection  conn;

    // Estado de la venta en curso
    private int     edadCliente      = 0;
    private boolean tieneMembership  = false;
    private String  nombreCliente    = "";
    private Movie   peliculaSeleccionada = null;
    private Funcion funcionSeleccionada  = null;

    public ControladorVender(Connection conn, PanelVender view) {
        this.conn = conn;
        this.view = view;
        initController();
    }

    private void initController() {

        // ── Paso 1 ────────────────────────────────────────────────────────────
        view.btnSi.addActionListener(e ->
            view.card.show(view, "PASO2_SI"));

        view.btnNo.addActionListener(e -> {
            resetEstado();
            view.card.show(view, "PASO2_NO");
        });

        // ── Paso 2a: buscar por CURP ──────────────────────────────────────────
        view.btnBuscarCliente.addActionListener(e -> buscarClientePorCurp());
        view.txtCurpVenta.addActionListener(e -> buscarClientePorCurp());

        view.btnCancelarCurp.addActionListener(e -> {
            view.txtCurpVenta.setText("");
            view.card.show(view, "PASO1");
        });

        // ── Paso 2b: edad manual ──────────────────────────────────────────────
        view.btnConfirmarEdad.addActionListener(e -> confirmarEdadManual());
        view.txtEdadVenta.addActionListener(e -> confirmarEdadManual());

        view.btnCancelarEdad.addActionListener(e -> {
            view.txtEdadVenta.setText("");
            view.card.show(view, "PASO1");
        });

        // ── Paso 3: seleccionar película ──────────────────────────────────────
        view.btnSeleccionarPelicula.addActionListener(e -> seleccionarPelicula());

        view.btnVolverPelicula.addActionListener(e ->
            view.card.show(view, tieneMembership ? "PASO2_SI" : "PASO2_NO"));

        // ── Paso 4: seleccionar función ───────────────────────────────────────
        view.btnSeleccionarFuncion.addActionListener(e -> seleccionarFuncion());

        view.btnVolverFuncion.addActionListener(e ->
            view.card.show(view, "PASO3"));

        // ── Paso 5: confirmar asientos ────────────────────────────────────────
        view.btnConfirmarAsientos.addActionListener(e -> confirmarAsientos());

        view.btnVolverAsientos.addActionListener(e ->
            view.card.show(view, "PASO4"));

        // ── Paso 6: nueva venta ───────────────────────────────────────────────
        view.btnNuevaVenta.addActionListener(e -> resetVenta());
    }

    // ── Lógica de cada paso ───────────────────────────────────────────────────

    private void buscarClientePorCurp() {
        String curp = view.txtCurpVenta.getText().trim().toUpperCase();

        if (curp.isBlank()) {
            JOptionPane.showMessageDialog(view,
                "Ingresa una CURP", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ClientDAO dao    = new ClientDAO(conn, new Client());
        Client    client = dao.getClientByCurp(curp);

        if (client == null) {
            int opt = JOptionPane.showConfirmDialog(view,
                "No se encontró ningún cliente con esa CURP.\n¿Deseas continuar sin membresía?",
                "Cliente no encontrado",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opt == JOptionPane.YES_OPTION) {
                resetEstado();
                view.card.show(view, "PASO2_NO");
            }
            return;
        }

        // Extraer datos del cliente
        try {
            edadCliente = Integer.parseInt(client.getAge().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,
                "La edad registrada del cliente no es válida. Ingresa la edad manualmente.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            resetEstado();
            view.card.show(view, "PASO2_NO");
            return;
        }

        tieneMembership = client.getIdMembership() > 0;
        nombreCliente   = client.getName();

        String tipoCliente = edadCliente < 18 ? "Menor" : "Adulto";
        String memInfo     = tieneMembership
            ? "✔ Membresía activa — 20% de descuento aplicado"
            : "Sin membresía";

        JOptionPane.showMessageDialog(view,
            "Cliente encontrado: " + nombreCliente +
            "\nEdad: " + edadCliente + " (" + tipoCliente + ")" +
            "\n" + memInfo,
            "Cliente verificado", JOptionPane.INFORMATION_MESSAGE);

        view.txtCurpVenta.setText("");
        cargarPeliculas();
        view.card.show(view, "PASO3");
    }

    private void confirmarEdadManual() {
        String textoEdad = view.txtEdadVenta.getText().trim();
        int edad;
        try {
            edad = Integer.parseInt(textoEdad);
            if (edad < 1 || edad > 120) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,
                "Ingresa una edad válida (número entre 1 y 120)",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        edadCliente     = edad;
        tieneMembership = false;
        nombreCliente   = "";

        view.txtEdadVenta.setText("");
        cargarPeliculas();
        view.card.show(view, "PASO3");
    }

    private void seleccionarPelicula() {
        int fila = view.tablaPeliculas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view,
                "Selecciona una película de la lista", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int    id     = (int)    view.dtmPeliculas.getValueAt(fila, 0);
        String nombre = (String) view.dtmPeliculas.getValueAt(fila, 1);

        peliculaSeleccionada = new Movie();
        peliculaSeleccionada.setId(id);
        peliculaSeleccionada.setName(nombre);

        cargarFunciones(id);
        view.card.show(view, "PASO4");
    }

    private void seleccionarFuncion() {
        int fila = view.tablaFunciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view,
                "Selecciona una función de la lista", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idFuncion = (int) view.dtmFunciones.getValueAt(fila, 0);
        int numSala   = Integer.parseInt(
            view.dtmFunciones.getValueAt(fila, 2).toString().replace("Sala ", "").trim());

        // Buscamos el objeto Funcion completo desde la lista cargada
        FuncionDAO fdao = new FuncionDAO(conn);
        List<Funcion> funciones = fdao.getFuncionesByPelicula(peliculaSeleccionada.getId());
        funcionSeleccionada = funciones.stream()
            .filter(f -> f.getId() == idFuncion)
            .findFirst()
            .orElse(null);

        if (funcionSeleccionada == null) {
            JOptionPane.showMessageDialog(view,
                "Error al cargar la función seleccionada", "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        cargarAsientos(numSala);
        view.card.show(view, "PASO5");
    }

    private void confirmarAsientos() {
        List<Asiento> seleccionados = view.asientosSeleccionados;

        if (seleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Selecciona al menos un asiento", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Mostrar boleto
        view.mostrarBoleto(
            peliculaSeleccionada.getName(),
            funcionSeleccionada.getNumSala(),
            funcionSeleccionada,
            seleccionados,
            edadCliente,
            tieneMembership,
            nombreCliente
        );

        view.card.show(view, "PASO6");
    }

    // ── Carga de datos ────────────────────────────────────────────────────────

    private void cargarPeliculas() {
        view.dtmPeliculas.setRowCount(0);
        MovieDAO dao = new MovieDAO(conn, new Movie());
        for (Movie m : dao.getAllMovies()) {
            view.dtmPeliculas.addRow(new Object[]{
                m.getId(), m.getName(), m.getGenre(),
                m.getDuration(), m.getFormat(), m.getLanguage()
            });
        }
    }

    private void cargarFunciones(int idPelicula) {
        view.dtmFunciones.setRowCount(0);
        FuncionDAO dao = new FuncionDAO(conn);
        List<Funcion> funciones = dao.getFuncionesByPelicula(idPelicula);

        if (funciones.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Esta película no tiene funciones programadas", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            view.card.show(view, "PASO3");
            return;
        }

        for (Funcion f : funciones) {
            view.dtmFunciones.addRow(new Object[]{
                f.getId(),
                f.getNombrePelicula(),
                "Sala " + f.getNumSala(),
                f.getHoraInicio().toString().substring(0, 5),
                f.getHoraFin().toString().substring(0, 5)
            });
        }
    }

    private void cargarAsientos(int numSala) {
        AsientoDAO dao = new AsientoDAO(conn);
        List<Asiento> asientos = dao.getAsientosBySala(numSala);
        view.cargarGridAsientos(asientos);
    }

    // ── Reset ─────────────────────────────────────────────────────────────────

    private void resetEstado() {
        edadCliente         = 0;
        tieneMembership     = false;
        nombreCliente       = "";
        peliculaSeleccionada= null;
        funcionSeleccionada = null;
    }

    private void resetVenta() {
        resetEstado();
        view.txtCurpVenta.setText("");
        view.txtEdadVenta.setText("");
        view.dtmPeliculas.setRowCount(0);
        view.dtmFunciones.setRowCount(0);
        view.asientosSeleccionados.clear();
        view.card.show(view, "PASO1");
    }
}