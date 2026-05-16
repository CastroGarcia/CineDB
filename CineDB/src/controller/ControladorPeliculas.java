package controller;

import database.ConexionDB;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    public ControladorPeliculas(Connection conn, PanelPeliculas view) {
        this.view = view;
        this.conn = conn;

        initController();
        cargarPeliculas();
    }

    private void initController() {
        view.btnCrear.addActionListener(e -> view.activarModoRegistro());
        view.btnLeer.addActionListener(e -> view.card.show(view, "BUSCAR"));
        view.btnActualizar.addActionListener(e -> abrirEdicion());
        view.btnGuardar.addActionListener(e -> {
            if(view.esModoEdicion()) {
                guardarEdicion();
            } else {
                guardarPelicula();
            }
        });
        view.btnCancelar.addActionListener(e -> {
            view.cancelarAccion();
        });
        view.btnBuscar.addActionListener(e -> buscarPeliculas());
        view.txtBuscador.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) buscarPeliculas();
            }           
        });
        view.btnRegresar.addActionListener(e -> {    
            view.txtBuscador.setText("Buscar por nombre de pelicula");
            view.txtBuscador.setForeground(Color.GRAY);
            cargarPeliculas();
            view.card.show(view, "PRINCIPAL");
        });
        view.btnEliminar.addActionListener(e -> eliminarPelicula());
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
    
    private void buscarPeliculas() {
        String query = view.txtBuscador.getText().trim();
        
        if(query.isBlank()) {cargarPeliculas(); return;}
        
        view.dtmPeliculas.setRowCount(0);
        
        MovieDAO dao = new MovieDAO(conn, new Movie());
        List<Movie> results = dao.searchMovie(query);
        
        if(results.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No se encontro ninguna coincidencia", "Sin resultados", JOptionPane.OK_OPTION);
            return;
        }
        
        for(Movie m : results) {
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
    
    private void abrirEdicion() {
        int filaSeleccionada = view.tablaPeliculas.getSelectedRow();

        // Validar que haya una fila seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, 
                "Selecciona una pelicula para editar", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos de la fila seleccionada
        int id         = (int)    view.dtmPeliculas.getValueAt(filaSeleccionada, 0);
        String nombre  = (String) view.dtmPeliculas.getValueAt(filaSeleccionada, 1);
        String genero  = (String) view.dtmPeliculas.getValueAt(filaSeleccionada, 2);
        String duracion= (String) view.dtmPeliculas.getValueAt(filaSeleccionada, 3);
        String formato = (String) view.dtmPeliculas.getValueAt(filaSeleccionada, 4);
        String idioma  = (String) view.dtmPeliculas.getValueAt(filaSeleccionada, 5);

        // Llenar formulario y cambiar panel
        view.activarModoEdicion(id, nombre, genero, duracion, formato, idioma);
    }
    
    private void guardarEdicion() {
        Movie movie = view.getFormData();
        movie.setId(view.idEditando);

        // Validar campos vacíos
        if (camposVacios(movie)) return;

        MovieDAO dao = new MovieDAO(conn, movie);

        if (dao.updateMovie()) {
            JOptionPane.showMessageDialog(view, 
                "Pelicula actualizada correctamente", 
                "Exito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarPeliculas();
            view.card.show(view, "PRINCIPAL");
        } else {
            JOptionPane.showMessageDialog(view, 
                "Error al actualizar la pelicula", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarPelicula() {
        int filaSeleccionada = view.tablaPeliculas.getSelectedRow();
        
        // Validar que haya una fila seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, 
                "Selecciona una pelicula para eliminar", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
                        
        // Obtenemos el valor que usaremos como condicion para eliminar el registro
        int id = (int) view.dtmPeliculas.getValueAt(filaSeleccionada, 0);
        String nombre = (String) view.dtmPeliculas.getValueAt(filaSeleccionada, 1);
        
        // Confirmar antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(view, 
                "Deseas eliminar la pelicula " + nombre + "?", 
                "Confirmar eliminacion", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if(confirmacion != JOptionPane.YES_OPTION) return;
        
        Movie movie = new Movie();
        movie.setId(id);
        MovieDAO dao = new MovieDAO(conn, movie);
        
        if(dao.deleteMovie()) {
            JOptionPane.showMessageDialog(view, "Pelicula eliminada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);            
            cargarPeliculas();
        } else {
            JOptionPane.showMessageDialog(view, "Error al eliminar la pelicula", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    //----- Metodos auxiliares -------------------------
    private boolean camposVacios(Movie movie) {
        if (movie.getName().isBlank()  || movie.getGenre().isBlank()  ||
            movie.getDuration().isBlank() || movie.getFormat().isBlank() ||
            movie.getLanguage().isBlank()) {
            JOptionPane.showMessageDialog(view,
                "Rellena todos los campos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }
    
    
}