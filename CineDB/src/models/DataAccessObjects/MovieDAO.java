package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import models.Movie;

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
        format = movie.getDuration();
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
    
}
