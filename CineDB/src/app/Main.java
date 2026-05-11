package app;

import controller.ControladorLogin;
import javax.swing.SwingUtilities;
import view.VentanaLogin;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            VentanaLogin view = new VentanaLogin();            
            new ControladorLogin(view);
            
            view.setVisible(true);
        });
    }    
    
    
    //probando el git 
}
