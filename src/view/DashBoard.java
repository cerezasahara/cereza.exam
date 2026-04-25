package view;

import controller.notecontroller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DashBoard extends JFrame {

    public static int currentUserId;

    JTable table;
    DefaultTableModel model;
    JTextField search;

    public DashBoard() {

        setTitle("Dashboard");
        setSize(920, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        UIStyle.frame(this);

        JPanel top = new JPanel(new BorderLayout(8,8));
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        top.setOpaque(false);

        search = new JTextField();
        UIStyle.field(search);

        JButton searchBtn = new JButton("Search");
        JButton createBtn = new JButton("+ New");
        UIStyle.button(searchBtn);
        UIStyle.button(createBtn);

        JPanel left = new JPanel(new BorderLayout(6,6));
        left.setOpaque(false);
        left.add(search, BorderLayout.CENTER);
        left.add(searchBtn, BorderLayout.EAST);

        top.add(createBtn, BorderLayout.WEST);
        top.add(left, BorderLayout.CENTER);

        model = new DefaultTableModel(
            new String[]{"ID","Note","Category","Updated","Owner","Access"},0);

        table = new JTable(model);
        UIStyle.table(table);

        JScrollPane scroll = new JScrollPane(table);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);

        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton shareBtn = new JButton("Share");

        UIStyle.ghost(editBtn);
        UIStyle.ghost(deleteBtn);
        UIStyle.ghost(shareBtn);

        bottom.add(editBtn);
        bottom.add(deleteBtn);
        bottom.add(shareBtn);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> load(search.getText()));
        createBtn.addActionListener(e -> new NoteForm(this, -1, null, null).setVisible(true));

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Select a note");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row,0).toString());
            String access = model.getValueAt(row,5).toString();

            if(access.equals("VIEW")){
                JOptionPane.showMessageDialog(this,"No permission to edit");
                return;
            }

            String text = model.getValueAt(row,1).toString();
            String cat  = model.getValueAt(row,2).toString();

            new NoteForm(this, id, text, cat).setVisible(true);
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Select a note");
                return;
            }

            String access = model.getValueAt(row,5).toString();
            if(!access.equals("OWNER")){
                JOptionPane.showMessageDialog(this,"Only owner can delete");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,"Delete note?");
            if(confirm == 0){
                int id = Integer.parseInt(model.getValueAt(row,0).toString());
                notecontroller.deleteNote(id, currentUserId);
                load("");
            }
        });

        shareBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Select a note");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row,0).toString());
            new ShareForm(this, id).setVisible(true);
        });

        load("");
    }

    public void reload(){
        load(search.getText());
    }

    void load(String s){
        model.setRowCount(0);

        ArrayList<String[]> list = notecontroller.getNotes(currentUserId, s);

        for(String[] n : list){
            String preview = n[1].length()>50 ? n[1].substring(0,50)+"..." : n[1];
            String category = n[2] == null ? "None" : n[2];

            model.addRow(new Object[]{
                n[0],
                preview,
                category,
                n[3],
                n[4],
                n[5]
            });
        }
    }
}