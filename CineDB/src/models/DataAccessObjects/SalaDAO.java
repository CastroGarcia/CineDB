package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Sala;

public class SalaDAO {
    private final Connection conn;
    private final Sala sala;

    public SalaDAO(Connection conn, Sala sala) {
        this.conn = conn;
        this.sala = sala;
    }

    public boolean registerSala() {
        String sql = "INSERT INTO sala (num_sala, asientos, disponible) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sala.getNumSala());
            ps.setInt(2, sala.getAsientos());            
            ps.setBoolean(3, sala.isDisponible());
            ps.executeUpdate();
            System.out.println("Sala registrada");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSala() {
        String sql = "UPDATE sala SET asientos=?, disponible=? WHERE num_sala=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sala.getAsientos());            
            ps.setBoolean(2, sala.isDisponible());
            ps.setInt(3, sala.getNumSala());
            ps.executeUpdate();
            System.out.println("Sala actualizada");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSala() {
        String sql = "DELETE FROM sala WHERE num_sala=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sala.getNumSala());
            ps.executeUpdate();
            System.out.println("Sala eliminada");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Sala> getAllSalas() {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT num_sala, asientos, id_funciones, disponible FROM sala";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                salas.add(new Sala(
                    rs.getInt("num_sala"),
                    rs.getInt("asientos"),
                    rs.getBoolean("disponible")                
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salas;
    }

    public List<Sala> searchSala(String query) {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT num_sala, asientos, id_funciones, disponible FROM sala WHERE num_sala LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                salas.add(new Sala(
                    rs.getInt("num_sala"),
                    rs.getInt("asientos"),
                    rs.getBoolean("disponible")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salas;
    }
}