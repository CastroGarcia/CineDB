package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

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
        
        // Hashea la contraseña (el "10" es el factor de costo)
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        
        String sql = "INSERT INTO usuario (NombreUsuario, Contraseña) VALUES (?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);  
            ps.setString(2, hashedPassword);

            ps.executeUpdate();
            System.out.println("Usuario guardado");
            return 2;   //Usuario registrado

        } catch (Exception e) {
            e.printStackTrace();
            return -1;  //Error al registrar
        }
    }
    
    public boolean findUser() {        
        // Solo busca por username, trae el hash guardado
        String sql = "SELECT Contraseña FROM usuario WHERE NombreUsuario = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String hashGuardado = rs.getString("Contraseña");
                // Compara la contraseña escrita contra el hash guardado
                return BCrypt.checkpw(password, hashGuardado);
            }

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return false;
    }
}
