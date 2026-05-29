package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import models.Client;

public class ClientDAO {
    private final Connection conn;
    private final Client client;

    private final String curp, name, age, phone, email;
    private final int idMembership;

    public ClientDAO(Connection conn, Client client) {
        this.conn = conn;
        this.client = client;

        curp         = client.getCurp();
        name         = client.getName();
        age          = client.getAge();
        phone        = client.getPhone();
        email        = client.getEmail();
        idMembership = client.getIdMembership();
    }

    public boolean registerClient() {
        String sql = "INSERT INTO cliente (curp, nombre, edad, telefono, correo, id_membresia) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curp);
            ps.setString(2, name);
            ps.setString(3, age);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setInt   (6, idMembership);
            ps.executeUpdate();
            System.out.println("Cliente registrado");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Busca clientes cuya CURP contenga el texto dado (LIKE %query%). */
    public List<Client> searchClient(String query) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT curp, nombre, edad, telefono, correo, id_membresia FROM cliente WHERE curp LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(new Client(
                    rs.getString("curp"),
                    rs.getString("nombre"),
                    rs.getString("edad"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt   ("id_membresia")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }

    /** Busca un cliente por CURP exacta. Retorna null si no existe. */
    public Client getClientByCurp(String curpBuscar) {
        String sql = "SELECT curp, nombre, edad, telefono, correo, id_membresia FROM cliente WHERE curp = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curpBuscar.toUpperCase().trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Client(
                    rs.getString("curp"),
                    rs.getString("nombre"),
                    rs.getString("edad"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt   ("id_membresia")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateClient() {
        String sql = "UPDATE cliente SET nombre=?, edad=?, telefono=?, correo=?, id_membresia=? WHERE curp=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getAge());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getEmail());
            ps.setInt   (5, client.getIdMembership());
            ps.setString(6, client.getCurp());
            ps.executeUpdate();
            System.out.println("Cliente actualizado");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClient() {
        String sql = "DELETE FROM cliente WHERE curp=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.getCurp());
            ps.executeUpdate();
            System.out.println("Cliente eliminado");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Client> getAllClient() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT curp, nombre, edad, telefono, correo, id_membresia FROM cliente";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(new Client(
                    rs.getString("curp"),
                    rs.getString("nombre"),
                    rs.getString("edad"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt   ("id_membresia")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }
}