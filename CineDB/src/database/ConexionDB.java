package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionDB {
    
    private static Connection connection;
    private static final String URL = "jdbc:mysql://bhkowgplapmezf7l4mpl-mysql.services.clever-cloud.com:3306/bhkowgplapmezf7l4mpl?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "u19cxqdbwi9rqqqa";
    private static final String PASSWORD = "UQaKYA00BFtgtrrvoE4v";
        
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        System.out.println("Conexion hecha");
        return connection;
    }    
}
