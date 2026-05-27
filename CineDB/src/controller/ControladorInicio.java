package controller;

import java.sql.Connection;
import database.ConexionDB;
import java.sql.SQLException;
import view.VentanaInicio;

public class ControladorInicio {
    private VentanaInicio view;
    private Connection conn;

    public ControladorInicio(VentanaInicio view) {
        this.view = view;

        try {
            conn = ConexionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initController();
        new ControladorPeliculas(conn, view.panelPeliculas);
        new ControladorClientes(conn, view.panelClientes);
        new ControladorMembresias(conn, view.panelMembresias);
        new ControladorSalas(conn, view.panelSalas);
        new ControladorFunciones(conn, view.panelFunciones);
    }

    private void initController() {
        view.btnClientes.addActionListener(e  -> view.card.show(view.panelCentral, "CLIENTES"));
        view.btnPeliculas.addActionListener(e -> view.card.show(view.panelCentral, "PELICULAS"));
        view.btnFunciones.addActionListener(e -> view.card.show(view.panelCentral, "FUNCIONES"));
        //view.btnCartelera.addActionListener(e -> view.card.show(view.panelCentral, "CARTELERAS"));
        view.btnSalas.addActionListener(e -> {
            view.card.show(view.panelCentral, "SALAS");
            view.panelCentral.revalidate();
            view.panelCentral.repaint();
        });
        view.btnMembresias.addActionListener(e -> view.card.show(view.panelCentral, "MEMBRESIAS"));
        view.btnVender.addActionListener(e    -> view.card.show(view.panelCentral, "VENDER"));
        view.btnSalir.addActionListener(e     -> view.botonSalir());
    }
}
