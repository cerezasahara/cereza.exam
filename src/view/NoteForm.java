package view;

import controller.notecontroller;

import javax.swing.*;
import java.awt.*;

public class NoteForm extends JDialog {

    JTextArea area;
    JTextField categoryField;

    public NoteForm(JFrame parent, int noteId, String existingText, String existingCategory){
        super(parent, true);
        setTitle(noteId == -1 ? "Create Note" : "Edit Note");
        setSize(420, 320);
        setLocationRelativeTo(parent);

        UIStyle.frame(this);

        JPanel card = UIStyle.card();

        JLabel title = new JLabel(getTitle());
        title.setFont(UIStyle.TITLE);
        title.setForeground(Color.WHITE);

        area = new JTextArea(existingText == null ? "" : existingText);
        UIStyle.area(area);

        categoryField = new JTextField(existingCategory == null ? "" : existingCategory);
        UIStyle.field(categoryField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        UIStyle.button(save);
        UIStyle.ghost(cancel);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(cancel);
        bottom.add(save);

        card.add(title, BorderLayout.NORTH);
        card.add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(8,8));
        south.setOpaque(false);
        south.add(new JLabel("Category:"), BorderLayout.WEST);
        south.add(categoryField, BorderLayout.CENTER);
        south.add(bottom, BorderLayout.SOUTH);

        card.add(south, BorderLayout.SOUTH);

        add(card);

        cancel.addActionListener(e -> dispose());

        save.addActionListener(e -> {
            String txt = area.getText().trim();
            String cat = categoryField.getText().trim();

            if(txt.isEmpty()){
                JOptionPane.showMessageDialog(this,"Note is empty");
                return;
            }

            if(noteId == -1){
                notecontroller.createNote(DashBoard.currentUserId, txt);
            } else {
                notecontroller.updateNote(noteId, DashBoard.currentUserId, txt);
            }


            ((DashBoard)parent).reload();
            dispose();
        });
    }
}