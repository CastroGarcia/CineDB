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
        new ControladorPeliculas (conn, view.panelPeliculas);
        new ControladorClientes  (conn, view.panelClientes);
        new ControladorMembresias(conn, view.panelMembresias);
        new ControladorSalas     (conn, view.panelSalas);
        new ControladorFunciones (conn, view.panelFunciones);
        new ControladorVender    (conn, view.panelVender);
    }

    private void initController() {
        view.btnClientes.addActionListener(e -> {
            view.card.show(view.panelCentral, "CLIENTES");
            view.setNavActive(view.btnClientes);
        });
        view.btnPeliculas.addActionListener(e -> {
            view.card.show(view.panelCentral, "PELICULAS");
            view.setNavActive(view.btnPeliculas);
        });
        view.btnFunciones.addActionListener(e -> {
            view.card.show(view.panelCentral, "FUNCIONES");
            view.setNavActive(view.btnFunciones);
        });
        view.btnSalas.addActionListener(e -> {
            view.card.show(view.panelCentral, "SALAS");
            view.setNavActive(view.btnSalas);
            view.panelCentral.revalidate();
            view.panelCentral.repaint();
        });
        view.btnMembresias.addActionListener(e -> {
            view.card.show(view.panelCentral, "MEMBRESIAS");
            view.setNavActive(view.btnMembresias);
        });
        view.btnVender.addActionListener(e -> {
            view.card.show(view.panelCentral, "VENDER");
            view.setNavActive(view.btnVender);
        });
        view.btnSalir.addActionListener(e -> view.botonSalir());
    }
}