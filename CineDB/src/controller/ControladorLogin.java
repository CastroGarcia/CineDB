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
            view.card.show(view.getContentPane(), "REGISTRAR");
            view.limpiarCamposLogin();
        });
        
        // Boton Crear Usuario
        view.btnCrearUsuario.addActionListener(e -> {
            String username = view.getTxtNuevoUsuario();
            String password = view.getTxtNuevaContraseña();
            String confirmPassword = view.getTxtConfirmarContraseña();
            
            if(camposVaciosRegistro(username, password, confirmPassword)) return;
            
            if(!password.equals(confirmPassword)) {
                showAlert("Las contraseñas no coinciden.");
                return;
            }
            UserDAO user = instanceNewUser(username, password);
            registerAction(user);  
            view.card.show(view.getContentPane(), "LOGIN");
            view.limpiarCamposRegistro();
        });
        
        // Boton Cancelar
        view.btnCancelar.addActionListener(e -> {
            view.limpiarCamposRegistro();
            view.card.show(view.getContentPane(), "LOGIN");
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
            view.limpiarCamposLogin();
        }
    }
    
    private UserDAO instanceUser() {
        String username = view.getTxtUsuario();
        String password = view.getTxtContraseña();
        
        return new UserDAO(conn, username, password);
    }
    
    private UserDAO instanceNewUser(String username, String password) {           
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
    
    private boolean camposVaciosRegistro(String user, String pass, 
            String confirmPass) {
        if(user.equals("Escribe un nombre de usuario") 
                || pass.equals("Escribe tu contraseña") 
                || confirmPass.equals("Escribe la contraseña")) {
            showAlert("Debes llenar todos los campos.");
            return true;
        }
        return false;
    }
    
}
