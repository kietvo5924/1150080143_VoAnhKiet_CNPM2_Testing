package fpoly.bai6;

import javax.swing.*;
import java.awt.*;

public class UserForm extends JFrame {
    private JTextField tUser = new JTextField(), tFull = new JTextField(), tEmail = new JTextField();
    private JPasswordField tPass = new JPasswordField();
    private JButton btnUpdate = new JButton("Update"), btnReset = new JButton("Reset");
    private UserService service = new UserService();

    public UserForm() {
        setTitle("NGHIENPHIM - USER EDITION");
        setSize(500, 350);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Username:")); add(tUser);
        add(new JLabel("Password:")); add(tPass);
        add(new JLabel("Fullname:")); add(tFull);
        add(new JLabel("Email:")); add(tEmail);
        add(btnReset); add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            try {
                service.createTable();
                service.updateUser(tUser.getText(), new String(tPass.getPassword()), tFull.getText(), tEmail.getText());
                JOptionPane.showMessageDialog(this, "Update Successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnReset.addActionListener(e -> {
            tUser.setText(""); tPass.setText(""); tFull.setText(""); tEmail.setText("");
        });
    }

    public static void main(String[] args) {
        new UserForm().setVisible(true);
    }
}