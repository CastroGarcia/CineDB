package controller;

import view.PanelMembresias;
import java.sql.Connection;
import java.util.List;
import models.DataAccessObjects.MembershipDAO;
import models.Membership;

public class ControladorMembresias {
    private Connection conn;
    private PanelMembresias view;
    
    public ControladorMembresias(Connection conn, PanelMembresias view) {
        this.conn = conn;
        this.view = view;
        
        cargarMembresias();
    }
    
    private void cargarMembresias() {
        view.dtmMembresias.setRowCount(0);

        MembershipDAO dao = new MembershipDAO(conn, new Membership());
        List<Membership> memberships = dao.getAllMemberships();

        for (Membership m : memberships) {
            view.dtmMembresias.addRow(new Object[]{
                m.getId(),
                m.getTipo_membresia()
            });
        }
    }
}
