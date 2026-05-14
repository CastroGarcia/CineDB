package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import models.Movie;
import java.util.ArrayList; 
import java.util.List; 
import java.sql.ResultSet;

public class MovieDAO {
    private final Connection conn;
    private final Movie movie;
    
    private final String name, genre, duration, format, language;
    
    public MovieDAO(Connection conn, Movie movie) {
        this.conn = conn;
        this.movie = movie;
        
        name = movie.getName();
        genre = movie.getGenre();
        duration = movie.getDuration();
        format = movie.getFormat();
        language = movie.getLanguage();
    }
    
    public boolean registerMovie() {              
        String sql = "INSERT INTO pelicula (nombre, genero, duracion, formato, idioma) VALUES (?, ?, ?, ?, ?)";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, name);  
            ps.setString(2, genre);
            ps.setString(3, duration);
            ps.setString(4, format);
            ps.setString(5, language);

            ps.executeUpdate();
            System.out.println("Pelicula registrada");
            
            return true;            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT id, nombre, genero, duracion, formato, idioma FROM pelicula";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movie m = new Movie(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("genero"),
                    rs.getString("duracion"),
                    rs.getString("formato"),
                    rs.getString("idioma")
                );
                movies.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
    
}
