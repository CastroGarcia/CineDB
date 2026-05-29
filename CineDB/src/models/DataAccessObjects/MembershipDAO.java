package models.DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Membership;

public class MembershipDAO {
    private Membership membership;
    private Connection conn;
    private int id;
    private String tipo_membresia;
           
    public MembershipDAO(Connection conn, Membership membership) {
        this.conn = conn;
        this.membership = membership;
        
        id = membership.getId();
        tipo_membresia = membership.getTipo_membresia();
    }
    
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT id, tipo_membresia FROM membresia";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Membership m = new Membership(
                    rs.getInt("id"),
                    rs.getString("tipo_membresia")
                );
                memberships.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberships;
    }
}
