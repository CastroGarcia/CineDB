package controller;

import view.VentanaInicio;
import view.VentanaLogin;

public class ControladorLogin {
    private VentanaLogin view;
    
    public ControladorLogin(VentanaLogin view){
        this.view = view;
        initController();
    }
    
    private void initController() {
        
        // Boton iniciarSesion
        view.btnIniciarSesion.addActionListener(e -> {
            System.out.println("Sesion iniciada");
            
            VentanaInicio homeView = new VentanaInicio();
            new ControladorInicio(homeView);
            homeView.setVisible(true);
            view.dispose();
        });
        
        // Boton registrarse
        view.btnRegistrar.addActionListener(e -> {
            System.out.println("Nuevo usuario registrado\nAhora inicia sesion con el nuevo usuario");
        });
    }
    
}
