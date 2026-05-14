package controller;

import java.sql.Connection;
import database.ConexionDB;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import models.DataAccessObjects.UserDAO;
import view.VentanaInicio;
import view.VentanaLogin;

public class ControladorLogin {
    private VentanaLogin view;
    private Connection conn;
    
    public ControladorLogin(VentanaLogin view){
        this.view = view;   
        
        try {
            conn = ConexionDB.getConnection();
        } catch(SQLException e) {
            e.printStackTrace();            
        }
        
        initController();
    }
    
    private void initController() {
        
        // Boton iniciarSesion
        view.btnIniciarSesion.addActionListener(e -> {
            tryLogin();
        });
        
        // Boton registrarse
        view.btnRegistrar.addActionListener(e -> {
            UserDAO user = instanceUser();
            registerAction(user);            
        });
        
        //----- VK del teclado -----------------------------
        view.txtUsuario.addKeyListener(new KeyAdapter() {
           @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tryLogin();
                }                
            } 
        });
        
        view.txtContraseña.addKeyListener(new KeyAdapter() {
           @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tryLogin();
                }                
            } 
        });                
    }
    
    //----- Metodos para sesion ------------------------------
    
    private void tryLogin() {
        UserDAO user = instanceUser();
            
        if(loginAction(user)) {
            VentanaInicio homeView = new VentanaInicio();
            new ControladorInicio(homeView);
            homeView.setVisible(true);
            view.dispose();
        } else {
            showAlert("Usuario no encontrado");
        }
    }
    
    private UserDAO instanceUser() {
        String username = view.getTxtUsuario();
        String password = view.getTxtContraseña();
        
        return new UserDAO(conn, username, password);
    }
    
    private void registerAction(UserDAO user) {
        int registerUserValue = user.registerUser();
        String results = "";
        switch(registerUserValue) {
            case 1 -> results = "Usuario ya registrado";
            case 2 -> results = "Usuario registrado correctamente";
            case -1 -> results = "Usuario no se ha podido registrar";
        }
        
        showAlert(results);
    }
    
    private boolean loginAction(UserDAO user) {
        return user.findUser();                        
    }
    
    private void showAlert(String message) {
        view.showAlert(message);
    }
}
