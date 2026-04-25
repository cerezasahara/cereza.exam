package view;

import controller.authcontroller;
import javax.swing.*;

public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("Login");
        setSize(300,200);
        setLayout(null);
        setLocationRelativeTo(null);

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        user.setBounds(80,20,150,25);
        pass.setBounds(80,60,150,25);

        JButton login = new JButton("Login");

        add(new JLabel("User")).setBounds(10,20,70,25);
        add(user);
        add(new JLabel("Pass")).setBounds(10,60,70,25);
        add(pass);
        add(login).setBounds(90,100,100,25);

        login.addActionListener(e -> {
            int id = authcontroller.login(user.getText(), new String(pass.getPassword()));
            if(id != -1){
                DashBoard.currentUserId = id;
                new DashBoard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Invalid");
            }
        });
    }
    
    }

