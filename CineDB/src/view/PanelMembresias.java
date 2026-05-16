package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelMembresias extends JPanel{
    private JPanel panelPrincipal;
    public DefaultTableModel dtmMembresias;
    public JTable tablaMembresias;
    private JScrollPane spPrincipal;
    public CardLayout card;
    String[] cols = { "ID", "tipo_membresia" };
    
    public PanelMembresias() {                        
        initComponents();       
    }
            
    private void initComponents() {
        // Construir Interfaz
        card = new CardLayout();
        setLayout(card);
        panelPrincipal = crearPanelPrincipal();
        
        add(panelPrincipal, "PRINCIPAL");
        
        card.show(this, "PRINCIPAL");
    }        
    
    //----- CREAR PANELES PARA LA VISTA DE MEMBRESIAS -----------------
    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(254, 254, 254));
        
        JLabel titulo = new JLabel("MEMBRESIAS");
        titulo.setFont(new Font("Segeo UI", Font.BOLD, 42));        
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
                
        dtmMembresias = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no es editable directamente
            }
        };
        tablaMembresias = new JTable(dtmMembresias);
        spPrincipal = new JScrollPane(tablaMembresias);
       
        p.add(titulo, BorderLayout.NORTH);
        p.add(spPrincipal, BorderLayout.CENTER);
        
        return p;
    }    

}
