package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    
    private final Connection connection;
    private final String username;
    private final String password;
    
    public UserDAO(Connection conn, String username, String password) {
        connection = conn;
        this.username = username;
        this.password = password;
    }
    
    public int registerUser() {
        if(findUser()) return 1; //Usuario ya registrado
        
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);  
            ps.setString(2, password);

            ps.executeUpdate();
            System.out.println("Usuario guardado");
            return 1;   //Usuario registrado

        } catch (Exception e) {
            e.printStackTrace();
            return -1;  //Error al registrar
        }
    }
    
    public boolean findUser() {
        boolean valid = false;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                valid = true;
            }

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return valid;
    }
}
