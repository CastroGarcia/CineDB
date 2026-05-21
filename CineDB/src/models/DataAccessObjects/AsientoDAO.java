package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Asiento;
import models.Asiento.Estado;

public class AsientoDAO {
    private final Connection conn;

    public AsientoDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean generarAsientos(int numSala, int totalAsientos) {        
        String deleteSql = "DELETE FROM asiento WHERE num_sala = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setInt(1, numSala);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String sql = "INSERT INTO asiento (num_sala, fila, columna, estado) VALUES (?, ?, ?, ?)";
        int COLS = 10;
        int rows = (int) Math.ceil((double) totalAsientos / COLS);
        int count = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int fila = 1; fila <= rows && count < totalAsientos; fila++) {
                for (int col = 1; col <= COLS && count < totalAsientos; col++) {
                    ps.setInt(1, numSala);
                    ps.setInt(2, fila);
                    ps.setInt(3, col);
                    ps.setString(4, Estado.DISPONIBLE.name());
                    ps.addBatch();
                    count++;
                }
            }
            ps.executeBatch();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Asiento> getAsientosBySala(int numSala) {
        List<Asiento> lista = new ArrayList<>();
        String sql = "SELECT id, num_sala, fila, columna, estado FROM asiento WHERE num_sala = ? ORDER BY fila, columna";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numSala);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Asiento(
                    rs.getInt("id"),
                    rs.getInt("num_sala"),
                    rs.getInt("fila"),
                    rs.getInt("columna"),
                    Estado.valueOf(rs.getString("estado"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean updateEstado(int id, Estado estado) {
        String sql = "UPDATE asiento SET estado = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAsientosBySala(int numSala) {
        String sql = "DELETE FROM asiento WHERE num_sala = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numSala);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
