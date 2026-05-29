package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PanelMembresias extends JPanel {

    public DefaultTableModel dtmMembresias;
    public JTable            tablaMembresias;
    public CardLayout        card;

    private static final String[] COLS = { "ID", "Tipo de membresía" };

    public PanelMembresias() { initComponents(); }

    private void initComponents() {
        card = new CardLayout();
        setLayout(card);
        setBackground(Theme.CONTENT_BG);
        add(crearPanelPrincipal(), "PRINCIPAL");
        card.show(this, "PRINCIPAL");
    }

    private JPanel crearPanelPrincipal() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CONTENT_BG);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        header.add(Theme.titleLabel("Membresías"), BorderLayout.WEST);

        JLabel sub = Theme.bodyLabel("Tipos de membresía disponibles en el sistema");
        sub.setForeground(Theme.TEXT_SECONDARY);
        header.add(sub, BorderLayout.SOUTH);
        p.add(header, BorderLayout.NORTH);
        
        dtmMembresias = new DefaultTableModel(null, COLS) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaMembresias = PanelClientes.estilizarTabla(new JTable(dtmMembresias));
        Theme.centerTable(tablaMembresias);
        
        tablaMembresias.getColumnModel().getColumn(1).setCellRenderer(
            new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(
                        JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {
                    JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                    String val = value == null ? "" : value.toString().toUpperCase();
                    lbl.setText(val);
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    if (!isSelected) {
                        switch (val) {
                            case "GOLD":
                                lbl.setForeground(Theme.AMBER_FG);
                                lbl.setBackground(Theme.AMBER_BG);
                                break;
                            case "SILVER":
                                lbl.setForeground(new Color(80, 80, 100));
                                lbl.setBackground(new Color(230, 230, 240));
                                break;
                            default:
                                lbl.setForeground(Theme.RED_DARK);
                                lbl.setBackground(Theme.RED_LIGHT);
                        }
                        lbl.setOpaque(true);
                    }
                    lbl.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                    return lbl;
                }
            }
        );

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Theme.CONTENT_BG);
        body.setBorder(BorderFactory.createEmptyBorder(16, 20, 20, 20));
        
        JPanel infoCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        infoCard.setBackground(new Color(230, 245, 255));
        infoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(181, 212, 244), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        JLabel infoIcon = new JLabel("ℹ");
        infoIcon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoIcon.setForeground(new Color(24, 95, 165));
        JLabel infoText = new JLabel("Las membresías son configuradas por el administrador del sistema.");
        infoText.setFont(Theme.FONT_BODY);
        infoText.setForeground(new Color(12, 68, 124));
        infoCard.add(infoIcon);
        infoCard.add(infoText);

        body.add(infoCard, BorderLayout.NORTH);
        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Theme.CONTENT_BG);
        tableWrap.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        tableWrap.add(PanelClientes.crearScrollPane(tablaMembresias), BorderLayout.CENTER);
        body.add(tableWrap, BorderLayout.CENTER);
        p.add(body, BorderLayout.CENTER);

        return p;
    }
}