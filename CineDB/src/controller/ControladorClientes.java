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
            if(view.esModoEdicion()) {
                guardarEdicion();
            } else {
                guardarCliente();
            }
        });
        view.btnCancelar.addActionListener(e -> {
            view.cancelarAccion();
        });
        view.btnBuscar.addActionListener(e -> buscarClientes());
        view.txtBuscador.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) buscarClientes();
            }           
        });
        view.btnRegresar.addActionListener(e -> {    
            view.txtBuscador.setText("Buscar por ID de cliente");
            view.txtBuscador.setForeground(Color.GRAY);
            cargarClientes();
            view.card.show(view, "PRINCIPAL");
        });
        view.btnEliminar.addActionListener(e -> eliminarCliente());
    }    
 
    // ---- Metodos CRUD --------------------------------------

    private void guardarCliente() {
        // Obtiene los datos de los campos
        Client client = view.getFormData(); 
            
        if(camposVacios(client)) return;

        ClientDAO dao = new ClientDAO(conn, client);

        if (dao.registerClient()) {
            JOptionPane.showMessageDialog(view, "Cliente registrado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarClientes();
            view.card.show(view, "PRINCIPAL");      
        } else {
            JOptionPane.showMessageDialog(view, "Error al registrar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarClientes() {
        String texto = view.txtBuscador.getText().trim();        
        if(texto.isBlank()) {cargarClientes(); return;}                
        int query;
        try {
            query = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
            "El buscador solo acepta números (ID)", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        view.dtmClientes.setRowCount(0);
        
        ClientDAO dao = new ClientDAO(conn, new Client());
        List<Client> results = dao.searchClient(query);
        
        if(results.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No se encontro ninguna coincidencia", "Sin resultados", JOptionPane.OK_OPTION);
            return;
        }
        
        for(Client c : results) {
            view.dtmClientes.addRow(new Object[]{
                c.getId(),
                c.getName(),
                c.getAge(),
                c.getPhone(),
                c.getEmail(),
                c.getIdMembership()
            });
        }
    }
    
    private void abrirEdicion() {
        int filaSeleccionada = view.tablaClientes.getSelectedRow();

        if(sinSeleccionFila(filaSeleccionada)) return;

        // Obtener datos de la fila seleccionada
        int id                = (int)    view.dtmClientes.getValueAt(filaSeleccionada, 0);
        String nombre         = (String) view.dtmClientes.getValueAt(filaSeleccionada, 1);
        String edad           = (String) view.dtmClientes.getValueAt(filaSeleccionada, 2);
        String telefono       = (String) view.dtmClientes.getValueAt(filaSeleccionada, 3);
        String correo         = (String) view.dtmClientes.getValueAt(filaSeleccionada, 4);
        int id_membresia      = (int) view.dtmClientes.getValueAt(filaSeleccionada, 5);

        // Llenar formulario y cambiar panel
        view.activarModoEdicion(id, nombre, edad, telefono, correo, id_membresia);
    }
    
    private void guardarEdicion() {
        Client client = view.getFormData();
        client.setId(view.idEditando);
        
        // Validar campos vacíos
        if (camposVacios(client)) return;
        
        client.setId(view.idEditando);
        
        ClientDAO dao = new ClientDAO(conn, client);
        if (dao.updateClient()) {
            JOptionPane.showMessageDialog(view, 
                "Cliente actualizado correctamente", 
                "Exito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarFormulario();
            cargarClientes();
            view.card.show(view, "PRINCIPAL");
        } else {
            JOptionPane.showMessageDialog(view, 
                "Error al actualizar cliente", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = view.tablaClientes.getSelectedRow();
        
        if(sinSeleccionFila(filaSeleccionada)) return;
                        
        // Obtenemos el valor que usaremos como condicion para eliminar el registro
        int id        = (int) view.dtmClientes.getValueAt(filaSeleccionada, 0);
        String nombre = (String) view.dtmClientes.getValueAt(filaSeleccionada, 1);
        
        // Confirmar antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(view, 
                "Deseas eliminar el cliente " + nombre + "?", 
                "Confirmar eliminacion", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if(confirmacion != JOptionPane.YES_OPTION) return;
        
        Client client = new Client();
        client.setId(id);
        ClientDAO dao = new ClientDAO(conn, client);
        
        if(dao.deleteClient()) {
            JOptionPane.showMessageDialog(view, "Cliente eliminado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);            
            cargarClientes();
        } else {
            JOptionPane.showMessageDialog(view, "Error al eliminar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarClientes() {
        view.dtmClientes.setRowCount(0);

        ClientDAO dao = new ClientDAO(conn, new Client());
        List<Client> clients = dao.getAllClient();

        for (Client m : clients) {
            view.dtmClientes.addRow(new Object[]{
                m.getId(),
                m.getName(),
                m.getAge(),
                m.getPhone(),
                m.getEmail(),
                m.getIdMembership()
            });
        }
    }
    
    //----- Metodos auxiliares -------------------------
    private boolean camposVacios(Client client) {        
        if (client == null) {
            JOptionPane.showMessageDialog(view,
                "El ID de membresía debe ser un número", "Aviso", JOptionPane.WARNING_MESSAGE);
            return true;
        }
    
        if (client.getName().isBlank()  || client.getAge().isBlank()  ||
            client.getPhone().isBlank() || client.getEmail().isBlank()){
            JOptionPane.showMessageDialog(view,
                "Rellena todos los campos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }
    
    private boolean sinSeleccionFila (int filaSeleccionada) {
        // Validar que haya una fila seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, 
                "Selecciona un cliente para eliminar", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }
    
    
}