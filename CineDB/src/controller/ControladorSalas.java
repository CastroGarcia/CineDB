package controller;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import models.Asiento;
import models.DataAccessObjects.AsientoDAO;
import models.DataAccessObjects.SalaDAO;
import models.Sala;
import view.PanelSalas;

public class ControladorSalas {
    private final PanelSalas view;
    private final Connection conn;
    private int salaViendoAsientos = -1;

    public ControladorSalas(Connection conn, PanelSalas view) {
        this.conn = conn;
        this.view = view;
        initController();
        cargarSalas();
    }

    private void initController() {
        view.btnCrear.addActionListener(e -> {
            view.activarModoRegistro();

            int siguiente = obtenerSiguienteSala();

            view.txtNumSala.setText(String.valueOf(siguiente));
            view.txtNumSala.setForeground(Color.BLACK);
        });
        view.btnLeer.addActionListener(e -> view.card.show(view, "BUSCAR"));
        view.btnActualizar.addActionListener(e -> abrirEdicion());
        view.btnEliminar.addActionListener(e -> eliminarSala());
        view.btnVerAsientos.addActionListener(e -> abrirAsientos());

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

        // Seat panel buttons
        view.panelAsientos.btnRegresar.addActionListener(e -> {
            salaViendoAsientos = -1;
            view.card.show(view, "PRINCIPAL");
        });

        view.panelAsientos.btnGuardar.addActionListener(e -> guardarEstadosAsientos());
    }

    // ----- CRUD ----------------------------------------------------------
    private void guardarSala() {
        Sala sala = view.getFormData();
        if (sala.getNumSala() <= 0) {
            JOptionPane.showMessageDialog(view,
                "Rellena todos los campos correctamente\n(Los valores deben ser numeros mayores a 0)",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SalaDAO dao = new SalaDAO(conn, sala);
        if (dao.registerSala()) {
            // Pre-generate seats
            AsientoDAO asientoDAO = new AsientoDAO(conn);
            asientoDAO.generarAsientos(sala.getNumSala(), sala.getAsientos());

            JOptionPane.showMessageDialog(view,
                "Sala registrada correctamente con " + sala.getAsientos() + " asientos generados.",
                "Exito", JOptionPane.INFORMATION_MESSAGE);
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
        boolean disp    = (boolean) view.dtmSalas.getValueAt(fila, 2);
        view.activarModoEdicion(numSala, disp);
    }

    private void guardarEdicion() {
        Sala sala = view.getFormData();        
        SalaDAO dao = new SalaDAO(conn, sala);
        if (dao.updateSala()) {            
            AsientoDAO asientoDAO = new AsientoDAO(conn);
            int confirm = JOptionPane.showConfirmDialog(view,
                "Deseas regenerar los asientos para esta sala?\n(Se perderan los estados actuales de los asientos)",
                "Regenerar asientos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                asientoDAO.generarAsientos(sala.getNumSala(), sala.getAsientos());
            }
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
            "Deseas eliminar la sala numero " + numSala + "?\nTambien se eliminaran todos sus asientos.",
            "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conf != JOptionPane.YES_OPTION) return;

        // Delete seats first (FK integrity)
        AsientoDAO asientoDAO = new AsientoDAO(conn);
        asientoDAO.deleteAsientosBySala(numSala);

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

    // ----- Asientos ------------------------------------------------------
    private void abrirAsientos() {
        int fila = view.tablaSalas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona una sala para ver sus asientos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int numSala = (int) view.dtmSalas.getValueAt(fila, 0);
        salaViendoAsientos = numSala;

        AsientoDAO asientoDAO = new AsientoDAO(conn);
        List<Asiento> asientos = asientoDAO.getAsientosBySala(numSala);

        view.panelAsientos.cargarAsientos(asientos, numSala);

        // Wire seat click listener (re-add each time we open)
        view.panelAsientos.addSeatClickListener(e -> {
            JButton btn = (JButton) e.getSource();
            view.panelAsientos.ciclarEstado(btn);
        });

        view.card.show(view, "ASIENTOS");
    }

    private void guardarEstadosAsientos() {
        if (salaViendoAsientos == -1) return;
        AsientoDAO asientoDAO = new AsientoDAO(conn);
        Map<JButton, Asiento> mapa = view.panelAsientos.getAsientoMap();
        boolean ok = true;
        for (Asiento a : mapa.values()) {
            if (!asientoDAO.updateEstado(a.getId(), a.getEstado())) {
                ok = false;
            }
        }
        if (ok) {
            JOptionPane.showMessageDialog(view, "Estados guardados correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Hubo un error al guardar algunos estados", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ----- Search / Load -------------------------------------------------
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
            view.dtmSalas.addRow(new Object[]{
                s.getNumSala(),
                s.getAsientos(),
                s.isDisponible()
            });
        }
    }

    private void cargarSalas() {
        view.dtmSalas.setRowCount(0);
        SalaDAO dao = new SalaDAO(conn, new Sala());
        List<Sala> salas = dao.getAllSalas();
        for (Sala s : salas) {
            view.dtmSalas.addRow(new Object[]{
    s.getNumSala(),
    s.getAsientos(),
    s.isDisponible()
});
        }
    }
    
    private int obtenerSiguienteSala() {
        SalaDAO dao = new SalaDAO(conn, new Sala());
        List<Sala> salas = dao.getAllSalas();

        int max = 0;

        for (Sala s : salas) {
            if (s.getNumSala() > max) {
                max = s.getNumSala();
            }
        }

        return max + 1;
    }
}
