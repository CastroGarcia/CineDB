package controller;

import view.VentanaInicio;

public class ControladorInicio {
    private VentanaInicio view;
    
    public ControladorInicio(VentanaInicio view) {
        this.view = view;        
        initController();
        
        new ControladorPeliculas(view.panelPeliculas);
    }
    
    private void initController() {
        
    }    
    
}
