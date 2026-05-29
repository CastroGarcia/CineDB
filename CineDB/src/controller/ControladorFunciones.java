package controller;

import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.DataAccessObjects.FuncionDAO;
import models.DataAccessObjects.MovieDAO;
import models.DataAccessObjects.SalaDAO;
import models.Funcion;
import models.Movie;
import models.Sala;
import view.PanelFunciones;

public class ControladorFunciones {

    private final PanelFunciones view;
    private final Connection conn;

    private final List<Funcion> funcionesPendientes = new ArrayList<>();

    public ControladorFunciones(Connection conn, PanelFunciones view) {
        this.conn = conn;
        this.view = view;
        initController();
        cargarTodo();
    }
    
    private void initController() {
        view.btnNueva.addActionListener(e -> abrirFormulario());
        view.btnEliminar.addActionListener(e -> eliminarFuncion());

        view.btnGenerarPreview.addActionListener(e -> generarPreview());
        view.btnGuardar.addActionListener(e -> guardarFunciones());
        view.btnCancelarForm.addActionListener(e -> {
            funcionesPendientes.clear();
            view.irAPrincipal();
        });
    }

    private void abrirFormulario() {
        funcionesPendientes.clear();
        view.dtmPreview.setRowCount(0);

        MovieDAO movieDAO = new MovieDAO(conn, new Movie());
        view.cargarPeliculas(movieDAO.getAllMovies());

        SalaDAO salaDAO = new SalaDAO(conn, new Sala());
        List<Integer> numeros = new ArrayList<>();
        for (Sala s : salaDAO.getAllSalas()) {
            numeros.add(s.getNumSala());
        }
        view.cargarSalas(numeros);

        view.irANueva();
    }

    private int parseDurationMinutes(String duracion) {
        try {
            String[] parts = duracion.trim().split(":");
            int hours   = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 60 + minutes;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void generarPreview() {
        Movie movie = view.getSelectedMovie();
        int numSala = view.getSelectedSala();

        if (movie == null || numSala == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona una pelicula y una sala.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String horaStr = view.txtHoraInicio.getText().trim();
        LocalTime inicio;
        try {            
            if (horaStr.length() == 5) horaStr += ":00";
            inicio = LocalTime.parse(horaStr);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, "Formato de hora invalido. Usa HH:mm (ej. 14:30)", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int duracionMin = parseDurationMinutes(movie.getDuration());
        if (duracionMin == 0) {
            JOptionPane.showMessageDialog(view, "La pelicula no tiene duracion valida (hh:mm:ss).", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int BUFFER_MIN = 15;
        LocalTime CIERRE = LocalTime.of(23, 59);

        funcionesPendientes.clear();
        view.dtmPreview.setRowCount(0);

        LocalTime current = inicio;
        while (true) {
            LocalTime fin = current.plusMinutes(duracionMin);
            if (fin.isBefore(current) || fin.isAfter(CIERRE)) break;

            Funcion f = new Funcion(0, movie.getId(), movie.getName(), numSala, current, fin);
            funcionesPendientes.add(f);

            String durStr = duracionMin + " min";
            view.dtmPreview.addRow(new Object[]{
                movie.getName(),
                current.toString().substring(0, 5),
                fin.toString().substring(0, 5),
                durStr
            });

            current = fin.plusMinutes(BUFFER_MIN);
            if (current.isBefore(fin)) break;
        }

        if (funcionesPendientes.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "No caben funciones a partir de las " + inicio.toString().substring(0, 5) +
                " con esa duracion antes del cierre (23:59).",
                "Sin funciones", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void guardarFunciones() {

        int fila = view.tablaPreview.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                view,
                "Selecciona un horario de la tabla preview.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (fila >= funcionesPendientes.size()) {
            JOptionPane.showMessageDialog(
                view,
                "Horario invalido.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Funcion funcionSeleccionada = funcionesPendientes.get(fila);

        FuncionDAO dao = new FuncionDAO(conn);

        boolean guardada = dao.registrarFuncion(funcionSeleccionada);

        if (guardada) {
            JOptionPane.showMessageDialog(
                view,
                "Funcion registrada correctamente.",
                "Exito",
                JOptionPane.INFORMATION_MESSAGE
            );

            funcionesPendientes.clear();
            cargarTodo();
            view.irAPrincipal();

        } else {

            JOptionPane.showMessageDialog(
                view,
                "Error al guardar la funcion.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarFuncion() {
        int fila = view.tablaFunciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona una funcion para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) view.dtmFunciones.getValueAt(fila, 0);
        int conf = JOptionPane.showConfirmDialog(view,
            "Eliminar la funcion #" + id + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conf != JOptionPane.YES_OPTION) return;

        FuncionDAO dao = new FuncionDAO(conn);
        if (dao.eliminarFuncion(id)) {
            JOptionPane.showMessageDialog(view, "Funcion eliminada.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarTodo();
        } else {
            JOptionPane.showMessageDialog(view, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTodo() {
        FuncionDAO dao = new FuncionDAO(conn);
        view.mostrarFunciones(dao.getAllFunciones());
    }
}
