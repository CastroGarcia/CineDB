package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import models.Funcion;

public class FuncionDAO {
    private final Connection conn;

    public FuncionDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean registrarFuncion(Funcion f) {
        String sql = "INSERT INTO funcion (id_pelicula, num_sala, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, f.getIdPelicula());
            ps.setInt(2, f.getNumSala());
            ps.setTime(3, Time.valueOf(f.getHoraInicio()));
            ps.setTime(4, Time.valueOf(f.getHoraFin()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarFuncion(int id) {
        String sql = "DELETE FROM funcion WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Funcion> getAllFunciones() {
        List<Funcion> lista = new ArrayList<>();
        String sql = """
            SELECT f.id, f.id_pelicula, p.nombre, f.num_sala, f.hora_inicio, f.hora_fin
            FROM funcion f
            JOIN pelicula p ON f.id_pelicula = p.id
            ORDER BY f.hora_inicio
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalTime inicio = rs.getTime("hora_inicio").toLocalTime();
                LocalTime fin    = rs.getTime("hora_fin").toLocalTime();
                lista.add(new Funcion(
                    rs.getInt("id"),
                    rs.getInt("id_pelicula"),
                    rs.getString("nombre"),
                    rs.getInt("num_sala"),
                    inicio, fin
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Funcion> getFuncionesBySala(int numSala) {
        List<Funcion> lista = new ArrayList<>();
        String sql = """
            SELECT f.id, f.id_pelicula, p.nombre, f.num_sala, f.hora_inicio, f.hora_fin
            FROM funcion f
            JOIN pelicula p ON f.id_pelicula = p.id
            WHERE f.num_sala = ?
            ORDER BY f.hora_inicio
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numSala);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalTime inicio = rs.getTime("hora_inicio").toLocalTime();
                LocalTime fin    = rs.getTime("hora_fin").toLocalTime();
                lista.add(new Funcion(
                    rs.getInt("id"),
                    rs.getInt("id_pelicula"),
                    rs.getString("nombre"),
                    rs.getInt("num_sala"),
                    inicio, fin
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<Funcion> getFuncionesByPelicula(int idPelicula) {
        List<Funcion> lista = new ArrayList<>();
        String sql = """
            SELECT f.id, f.id_pelicula, p.nombre, f.num_sala, f.hora_inicio, f.hora_fin
            FROM funcion f
            JOIN pelicula p ON f.id_pelicula = p.id
            WHERE f.id_pelicula = ?
            ORDER BY f.hora_inicio
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPelicula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalTime inicio = rs.getTime("hora_inicio").toLocalTime();
                LocalTime fin    = rs.getTime("hora_fin").toLocalTime();
                lista.add(new Funcion(
                    rs.getInt("id"),
                    rs.getInt("id_pelicula"),
                    rs.getString("nombre"),
                    rs.getInt("num_sala"),
                    inicio, fin
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}