package view;

import controller.authcontroller;
import controller.notecontroller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShareForm extends JDialog {

    public ShareForm(JFrame parent, int noteId){
        super(parent, true);

        setTitle("Share Note");
        setSize(350,220);
        setLocationRelativeTo(parent);

        UIStyle.frame(this); 

        JPanel panel = UIStyle.card();

        JLabel title = new JLabel("Share Note");
        title.setFont(UIStyle.TITLE);
        title.setForeground(Color.WHITE);

        JComboBox<String> userBox = new JComboBox<>();
        JComboBox<String> permBox = new JComboBox<>(new String[]{"VIEW","EDIT"});
        JTextField category = new JTextField();

        UIStyle.combo(userBox);
        UIStyle.combo(permBox);
        UIStyle.field(category);

        ArrayList<String[]> users = authcontroller.getAllUsers();
        for(String[] u : users){
            userBox.addItem(u[0] + " - " + u[1]);
        }

        JButton share = new JButton("Share");
        JButton cancel = new JButton("Cancel");

        UIStyle.button(share);

        JPanel grid = new JPanel(new GridLayout(3,2,5,5));
        grid.setOpaque(false);

        grid.add(new JLabel("User:"));
        grid.add(userBox);
        grid.add(new JLabel("Permission:"));
        grid.add(permBox);
        grid.add(new JLabel("Category:"));
        grid.add(category);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);

        bottom.add(cancel);
        bottom.add(share);

        panel.add(title, BorderLayout.NORTH);
        panel.add(grid, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);

        cancel.addActionListener(e -> dispose());

        share.addActionListener(e -> {
            if(userBox.getSelectedItem() == null) return;

            String selected = userBox.getSelectedItem().toString();
            int sharedUserId = Integer.parseInt(selected.split(" - ")[0]);

            String perm = permBox.getSelectedItem().toString();
            String cat = category.getText();

            notecontroller.shareNote(
                    noteId,
                    DashBoard.currentUserId,
                    sharedUserId,
                    cat,
                    perm
            );

            JOptionPane.showMessageDialog(this,"Shared!");
            ((DashBoard)parent).reload();
            dispose();
        });
    }
}