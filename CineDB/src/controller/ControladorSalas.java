package controller;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;
import models.DataAccessObjects.SalaDAO;
import models.Sala;
import view.PanelSalas;

public class ControladorSalas {
    private final PanelSalas view;
    private final Connection conn;

    public ControladorSalas(Connection conn, PanelSalas view) {
        this.conn = conn;
        this.view = view;
        initController();
        cargarSalas();
    }

    private void initController() {
        view.btnCrear.addActionListener(e -> view.activarModoRegistro());
        view.btnLeer.addActionListener(e -> view.card.show(view, "BUSCAR"));
        view.btnActualizar.addActionListener(e -> abrirEdicion());
        view.btnEliminar.addActionListener(e -> eliminarSala());

        view.btnGuardar.addActionListener(e -> {
            if (view.esModoEdicion()) guardarEdicion();
            else guardarSala();
        });

        view.btnCancelar.addActionListener(e -> view.cancelarAccion());

        view.btnBuscar.addActionListener(e -> buscarSalas());
        view.txtBuscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) buscarSalas();
            }
        });

        view.btnRegresar.addActionListener(e -> {
            view.txtBuscador.setText("Buscar por numero de sala");
            view.txtBuscador.setForeground(Color.GRAY);
            cargarSalas();
            view.card.show(view, "PRINCIPAL");
        });
    }

    // ----- CRUD ----------------------------------------------------------
    private void guardarSala() {
        Sala sala = view.getFormData();
        if (sala.getNumSala() <= 0 || sala.getAsientos() <= 0 || sala.getIdFunciones() <= 0) {
            JOptionPane.showMessageDialog(view,
                "Rellena todos los campos correctamente\n(Los valores deben ser numeros mayores a 0)",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SalaDAO dao = new SalaDAO(conn, sala);
        if (dao.registerSala()) {
            JOptionPane.showMessageDialog(view, "Sala registrada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarSalas();
            view.card.show(view, "PRINCIPAL");
        } else {
            JOptionPane.showMessageDialog(view, "Error al registrar la sala", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirEdicion() {
        int fila = view.tablaSalas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona una sala para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int numSala     = (int)     view.dtmSalas.getValueAt(fila, 0);
        int asientos    = (int)     view.dtmSalas.getValueAt(fila, 1);
        int idFunciones = (int)     view.dtmSalas.getValueAt(fila, 2);
        boolean disp    = (boolean) view.dtmSalas.getValueAt(fila, 3);
        view.activarModoEdicion(numSala, asientos, idFunciones, disp);
    }

    private void guardarEdicion() {
        Sala sala = view.getFormData();
        if (sala.getAsientos() <= 0 || sala.getIdFunciones() <= 0) {
            JOptionPane.showMessageDialog(view, "Rellena todos los campos correctamente", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SalaDAO dao = new SalaDAO(conn, sala);
        if (dao.updateSala()) {
            JOptionPane.showMessageDialog(view, "Sala actualizada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarSalas();
            view.card.show(view, "PRINCIPAL");
        } else {
            JOptionPane.showMessageDialog(view, "Error al actualizar la sala", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarSala() {
        int fila = view.tablaSalas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona una sala para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int numSala = (int) view.dtmSalas.getValueAt(fila, 0);

        int conf = JOptionPane.showConfirmDialog(view,
            "Deseas eliminar la sala numero " + numSala + "?",
            "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conf != JOptionPane.YES_OPTION) return;

        Sala sala = new Sala();
        sala.setNumSala(numSala);
        SalaDAO dao = new SalaDAO(conn, sala);
        if (dao.deleteSala()) {
            JOptionPane.showMessageDialog(view, "Sala eliminada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarSalas();
        } else {
            JOptionPane.showMessageDialog(view, "Error al eliminar la sala", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarSalas() {
        String query = view.txtBuscador.getText().trim();
        if (query.isBlank()) { cargarSalas(); return; }
        view.dtmSalas.setRowCount(0);
        SalaDAO dao = new SalaDAO(conn, new Sala());
        List<Sala> results = dao.searchSala(query);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No se encontro ninguna coincidencia", "Sin resultados", JOptionPane.OK_OPTION);
            return;
        }
        for (Sala s : results) {
            view.dtmSalas.addRow(new Object[]{ s.getNumSala(), s.getAsientos(), s.getIdFunciones(), s.isDisponible() });
        }
    }

    private void cargarSalas() {
        view.dtmSalas.setRowCount(0);
        SalaDAO dao = new SalaDAO(conn, new Sala());
        List<Sala> salas = dao.getAllSalas();
        for (Sala s : salas) {
            view.dtmSalas.addRow(new Object[]{ s.getNumSala(), s.getAsientos(), s.getIdFunciones(), s.isDisponible() });
        }
    }
}