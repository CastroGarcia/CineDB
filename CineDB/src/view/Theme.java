package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class Theme {
    
    public static final Color RED_PRIMARY   = new Color(226, 75, 74);
    public static final Color RED_DARK      = new Color(163, 45, 45);
    public static final Color RED_LIGHT     = new Color(252, 235, 235);

    public static final Color SIDEBAR_BG    = new Color(18, 18, 42);
    public static final Color HEADER_BG     = new Color(26, 26, 46);
    public static final Color CONTENT_BG    = new Color(245, 245, 248);

    public static final Color SURFACE       = Color.WHITE;
    public static final Color SURFACE_ALT   = new Color(248, 248, 250);

    public static final Color TEXT_PRIMARY  = new Color(20, 20, 30);
    public static final Color TEXT_SECONDARY= new Color(100, 100, 120);
    public static final Color TEXT_MUTED    = new Color(160, 160, 175);
    public static final Color TEXT_LIGHT    = new Color(220, 220, 235);

    public static final Color BORDER        = new Color(220, 220, 228);
    public static final Color BORDER_LIGHT  = new Color(235, 235, 242);

    public static final Color NAV_ACTIVE_BG = new Color(45, 45, 60);
    public static final Color NAV_HOVER_BG  = new Color(35, 35, 50);

    public static final Color GREEN_BG      = new Color(234, 243, 222);
    public static final Color GREEN_FG      = new Color(59, 109, 17);
    public static final Color AMBER_BG      = new Color(250, 238, 218);
    public static final Color AMBER_FG      = new Color(133, 79, 11);

    public static final Color TABLE_HEADER  = new Color(248, 248, 252);
    public static final Color TABLE_ROW_ALT = new Color(252, 252, 255);
    
    public static final Font FONT_HEADER    = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font FONT_TITLE     = new Font("Segoe UI", Font.PLAIN,  22);
    public static final Font FONT_SECTION   = new Font("Segoe UI", Font.PLAIN,  16);
    public static final Font FONT_LABEL     = new Font("Segoe UI", Font.BOLD,   11);
    public static final Font FONT_BODY      = new Font("Segoe UI", Font.PLAIN,  13);
    public static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN,  12);
    public static final Font FONT_NAV       = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font FONT_TABLE_HDR = new Font("Segoe UI", Font.BOLD,   12);
    public static final Font FONT_BTN       = new Font("Segoe UI", Font.PLAIN,  13);
    
    public static Border panelBorder() {
        return BorderFactory.createLineBorder(BORDER, 1);
    }
    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(16, 18, 16, 18)
        );
    }
    public static Border fieldBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        );
    }
    public static Border fieldFocusBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(RED_PRIMARY, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        );
    }    
   
    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setForeground(Color.WHITE);
        b.setBackground(RED_PRIMARY);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(RED_DARK); }
            public void mouseExited(MouseEvent e)  { b.setBackground(RED_PRIMARY); }
        });
        return b;
    }

    /** Neutral ghost button */
    public static JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setForeground(TEXT_SECONDARY);
        b.setBackground(SURFACE_ALT);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(BORDER_LIGHT); }
            public void mouseExited(MouseEvent e)  { b.setBackground(SURFACE_ALT); }
        });
        return b;
    }

    /** Red tint danger button */
    public static JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setForeground(RED_DARK);
        b.setBackground(RED_LIGHT);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(247, 193, 193), 1),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(247, 193, 193)); }
            public void mouseExited(MouseEvent e)  { b.setBackground(RED_LIGHT); }
        });
        return b;
    }

    /** Full-width login/register button */
    public static JButton loginButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        b.setForeground(Color.WHITE);
        b.setBackground(RED_PRIMARY);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setOpaque(true);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(RED_DARK); }
            public void mouseExited(MouseEvent e)  { b.setBackground(RED_PRIMARY); }
        });
        return b;
    }

    public static JButton secondaryLoginButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        b.setForeground(TEXT_SECONDARY);
        b.setBackground(SURFACE_ALT);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(9, 20, 9, 20)
        ));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(BORDER_LIGHT); }
            public void mouseExited(MouseEvent e)  { b.setBackground(SURFACE_ALT); }
        });
        return b;
    }

    public static JTextField styledField(String placeholder, int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_MUTED);
        f.setBackground(SURFACE_ALT);
        f.setBorder(fieldBorder());
        f.setText(placeholder);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(placeholder)) {
                    f.setText("");
                    f.setForeground(TEXT_PRIMARY);
                    f.setBackground(SURFACE);
                }
                f.setBorder(fieldFocusBorder());
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) {
                    f.setText(placeholder);
                    f.setForeground(TEXT_MUTED);
                    f.setBackground(SURFACE_ALT);
                }
                f.setBorder(fieldBorder());
            }
        });
        return f;
    }

    public static JPasswordField styledPasswordField(String placeholder, int cols) {
        JPasswordField f = new JPasswordField(cols);
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_MUTED);
        f.setBackground(SURFACE_ALT);
        f.setBorder(fieldBorder());
        f.setText(placeholder);
        f.setEchoChar((char) 0);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(f.getPassword()).equals(placeholder)) {
                    f.setText("");
                    f.setEchoChar('\u2022');
                    f.setForeground(TEXT_PRIMARY);
                    f.setBackground(SURFACE);
                }
                f.setBorder(fieldFocusBorder());
            }
            public void focusLost(FocusEvent e) {
                if (f.getPassword().length == 0) {
                    f.setText(placeholder);
                    f.setEchoChar((char) 0);
                    f.setForeground(TEXT_MUTED);
                    f.setBackground(SURFACE_ALT);
                }
                f.setBorder(fieldBorder());
            }
        });
        return f;
    }    

    public static JLabel titleLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    public static JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SECTION);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    public static JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_SECONDARY);
        return l;
    }

    public static JLabel bodyLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }
    
    public static void centerTable(JTable table) {
    DefaultTableCellRenderer centerRenderer =
        new DefaultTableCellRenderer();

    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel()
             .getColumn(i)
             .setCellRenderer(centerRenderer);
    }
}
}