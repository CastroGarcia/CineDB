package controller;

import database.ConexionDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import models.DataAccessObjects.MovieDAO;
import models.Movie;
import view.PanelPeliculas;

public class ControladorPeliculas {
    private PanelPeliculas view;
    private Connection conn;

    public ControladorPeliculas(PanelPeliculas view) {
        this.view = view;

        try {
            conn = ConexionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initController();
        cargarPeliculas();
    }

    private void initController() {
        view.btnLeer.addActionListener(e -> cargarPeliculas());
        view.btnGuardar.addActionListener(e -> guardarPelicula());
    }

    // ---- Metodos CRUD --------------------------------------

    private void guardarPelicula() {
        // Obtiene los datos de los campos
        Movie movie = view.getFormData(); 

        // Validar que no haya campos vacíos
        if (movie.getName().isBlank() || movie.getGenre().isBlank() ||
            movie.getDuration().isBlank() || movie.getFormat().isBlank() ||
            movie.getLanguage().isBlank()) {
            JOptionPane.showMessageDialog(view, "Rellena todos los campos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MovieDAO dao = new MovieDAO(conn, movie);

        if (dao.registerMovie()) {
            JOptionPane.showMessageDialog(view, "Pelicula registrada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarPeliculas();
            view.card.show(view, "PRINCIPAL");      
        } else {
            JOptionPane.showMessageDialog(view, "Error al registrar la pelicula", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPeliculas() {
        view.dtmPeliculas.setRowCount(0);

        MovieDAO dao = new MovieDAO(conn, new Movie());
        List<Movie> movies = dao.getAllMovies();

        for (Movie m : movies) {
            view.dtmPeliculas.addRow(new Object[]{
                m.getId(),
                m.getName(),
                m.getGenre(),
                m.getDuration(),
                m.getFormat(),
                m.getLanguage()
            });
        }
    }
}