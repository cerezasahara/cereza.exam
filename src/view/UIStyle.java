package view;

import javax.swing.*;
import java.awt.*;

public class UIStyle {

    public static final Color BG = new Color(24,26,27);
    public static final Color CARD = new Color(44,47,51);
    public static final Color ACCENT = new Color(88,101,242);
    public static final Color MUTED = new Color(60,63,65);

    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font BODY  = new Font("Segoe UI", Font.PLAIN, 13);

    // ✅ Works for JFrame + JDialog
    public static void frame(Window w){
        if(w instanceof JFrame){
            ((JFrame) w).getContentPane().setBackground(BG);
        } else if(w instanceof JDialog){
            ((JDialog) w).getContentPane().setBackground(BG);
        }
    }

    // CARD PANEL
    public static JPanel card(){
        JPanel p = new JPanel();
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        p.setLayout(new BorderLayout(10,10));
        return p;
    }

    // PRIMARY BUTTON (BLUE)
    public static void button(JButton b){
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
    }

    // SECONDARY BUTTON (GRAY) ✅ FIXED
    public static void ghost(JButton b){
        b.setBackground(MUTED);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
    }

    // TEXT FIELD
    public static void field(JTextField f){
        f.setBackground(MUTED);
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createEmptyBorder(6,8,6,8));
    }

    // TEXT AREA
    public static void area(JTextArea a){
        a.setBackground(MUTED);
        a.setForeground(Color.WHITE);
        a.setCaretColor(Color.WHITE);
        a.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
    }

    // COMBO BOX
    public static void combo(JComboBox<?> c){
        c.setBackground(MUTED);
        c.setForeground(Color.WHITE);
    }

    // TABLE ✅ FIXED (NO MORE ERROR)
    public static void table(JTable t){
        t.setRowHeight(25);
        t.setFont(BODY);
        t.setForeground(Color.BLACK);
        t.setBackground(Color.WHITE);
        t.setSelectionBackground(new Color(200,200,255));
        t.setGridColor(Color.LIGHT_GRAY);
    }
}