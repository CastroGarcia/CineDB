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
    
    private final int idMembership;
    private final String name, age, phone, email;
    
    public ClientDAO(Connection conn, Client client) {
        this.conn = conn;
        this.client = client;
        
        name = client.getName();
        age = client.getAge();
        phone = client.getPhone();
        email = client.getEmail();
        idMembership = client.getIdMembership();
    }
    
    public boolean registerClient() {              
        String sql = "INSERT INTO cliente (nombre, edad, telefono, correo, id_membresia) VALUES (?, ?, ?, ?, ?)";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, name);  
            ps.setString(2, age);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setInt(5, idMembership);

            ps.executeUpdate();
            System.out.println("Cliente registrado");
            
            return true;            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Client> searchClient(int query) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, nombre, edad, telefono, correo, id_membresia FROM cliente WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("edad"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt("id_membresia")
                ));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return clients;
    }
    
    public boolean updateClient() {
        String sql = "UPDATE cliente SET nombre=?, edad=?, telefono=?, correo=?, id_membresia=? WHERE id=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, client.getName());
            ps.setString(2, client.getAge());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getEmail());
            ps.setInt(5, client.getIdMembership());
            ps.setInt(6, client.getId());
            
            ps.executeUpdate();
            System.out.println("Cliente actualizado");
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteClient() {
        String sql = "DELETE FROM cliente WHERE id=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, client.getId());            
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
        String sql = "SELECT id, nombre, edad, telefono, correo, id_membresia FROM cliente";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client m = new Client(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("edad"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt("id_membresia")
                );
                clients.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }
    
}
