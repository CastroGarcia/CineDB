package controller;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;
import models.Client;
import models.DataAccessObjects.ClientDAO;
import view.PanelClientes;

public class ControladorClientes {
    private PanelClientes view;
    private Connection conn;

    public ControladorClientes(Connection conn, PanelClientes view) {
        this.view = view;
        this.conn = conn;

        initController();
        cargarClientes();
    }

    private void initController() {
        view.btnCrear.addActionListener(e -> view.activarModoRegistro());
        view.btnLeer.addActionListener(e -> view.card.show(view, "BUSCAR"));
        view.btnActualizar.addActionListener(e -> abrirEdicion());
        view.btnGuardar.addActionListener(e -> {
            if (view.esModoEdicion()) {
                guardarEdicion();
            } else {
                guardarCliente();
            }
        });
        view.btnCancelar.addActionListener(e -> view.cancelarAccion());
        view.btnBuscar.addActionListener(e -> buscarClientes());
        view.txtBuscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) buscarClientes();
            }
        });
        view.btnRegresar.addActionListener(e -> {
            view.txtBuscador.setText("Buscar por CURP");
            view.txtBuscador.setForeground(Color.GRAY);
            cargarClientes();
            view.card.show(view, "PRINCIPAL");
        });
        view.btnEliminar.addActionListener(e -> eliminarCliente());
    }

    private void guardarCliente() {
        Client client = view.getFormData();

        if (camposVacios(client)) return;

        if (client.getCurp().length() != 18) {
            JOptionPane.showMessageDialog(view,
                "La CURP debe tener exactamente 18 caracteres", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        ClientDAO dao = new ClientDAO(conn, client);

        if (dao.registerClient()) {
            JOptionPane.showMessageDialog(view, "Cliente registrado correctamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarClientes();
            view.card.show(view, "PRINCIPAL");
        } else {
            JOptionPane.showMessageDialog(view,
                "Error al registrar el cliente (¿CURP ya existe?)", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarClientes() {
        String texto = view.txtBuscador.getText().trim();
        if (texto.isBlank()) { cargarClientes(); return; }

        view.dtmClientes.setRowCount(0);

        ClientDAO dao = new ClientDAO(conn, new Client());
        List<Client> results = dao.searchClient(texto);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "No se encontró ninguna coincidencia", "Sin resultados",
                JOptionPane.OK_OPTION);
            return;
        }

        for (Client c : results) {
            view.dtmClientes.addRow(new Object[]{
                c.getCurp(),
                c.getName(),
                c.getAge(),
                c.getPhone(),
                c.getEmail(),
                c.getIdMembership()
            });
        }
    }

    private void abrirEdicion() {
        int fila = view.tablaClientes.getSelectedRow();
        if (sinSeleccionFila(fila)) return;

        String curp      = (String) view.dtmClientes.getValueAt(fila, 0);
        String nombre    = (String) view.dtmClientes.getValueAt(fila, 1);
        String edad      = (String) view.dtmClientes.getValueAt(fila, 2);
        String telefono  = (String) view.dtmClientes.getValueAt(fila, 3);
        String correo    = (String) view.dtmClientes.getValueAt(fila, 4);
        int    id_mem    = (int)    view.dtmClientes.getValueAt(fila, 5);

        view.activarModoEdicion(curp, nombre, edad, telefono, correo, id_mem);
    }

    private void guardarEdicion() {
        Client client = view.getFormData();
        if (camposVacios(client)) return;

        client.setCurp(view.curpEditando);

        ClientDAO dao = new ClientDAO(conn, client);
        if (dao.updateClient()) {
            JOptionPane.showMessageDialog(view,
                "Cliente actualizado correctamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarClientes();
            view.card.show(view, "PRINCIPAL");
        } else {
            JOptionPane.showMessageDialog(view,
                "Error al actualizar cliente", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        int fila = view.tablaClientes.getSelectedRow();
        if (sinSeleccionFila(fila)) return;

        String curp   = (String) view.dtmClientes.getValueAt(fila, 0);
        String nombre = (String) view.dtmClientes.getValueAt(fila, 1);

        int confirmacion = JOptionPane.showConfirmDialog(view,
            "¿Deseas eliminar el cliente " + nombre + " (CURP: " + curp + ")?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) return;

        Client client = new Client();
        client.setCurp(curp);
        ClientDAO dao = new ClientDAO(conn, client);

        if (dao.deleteClient()) {
            JOptionPane.showMessageDialog(view, "Cliente eliminado correctamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarClientes();
        } else {
            JOptionPane.showMessageDialog(view, "Error al eliminar cliente",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarClientes() {
        view.dtmClientes.setRowCount(0);

        ClientDAO dao = new ClientDAO(conn, new Client());
        List<Client> clients = dao.getAllClient();

        for (Client c : clients) {
            view.dtmClientes.addRow(new Object[]{
                c.getCurp(),
                c.getName(),
                c.getAge(),
                c.getPhone(),
                c.getEmail(),
                c.getIdMembership()
            });
        }
    }

    private boolean camposVacios(Client client) {
        if (client == null) {
            JOptionPane.showMessageDialog(view,
                "El ID de membresía debe ser un número", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return true;
        }

        if (client.getCurp().isBlank()  || client.getName().isBlank()  ||
            client.getAge().isBlank()   || client.getPhone().isBlank() ||
            client.getEmail().isBlank()) {
            JOptionPane.showMessageDialog(view,
                "Rellena todos los campos", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private boolean sinSeleccionFila(int fila) {
        if (fila == -1) {
            JOptionPane.showMessageDialog(view,
                "Selecciona un cliente de la tabla", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }
}