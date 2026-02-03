package fploy.bai5;

import javax.swing.*;
import java.awt.*;

public class JobForm extends JFrame {
    private JTextField tTitle = new JTextField(), tFile = new JTextField();
    private JTextArea tDesc = new JTextArea(3,20), tNote = new JTextArea(3,20);
    private JButton btnSave = new JButton("Save");
    private JobService service = new JobService();

    public JobForm() {
        setTitle("Add Job Title");
        setSize(450, 450);
        setLayout(new GridLayout(5, 2, 5, 5));
        add(new JLabel("Job Title*:")); add(tTitle);
        add(new JLabel("Description:")); add(new JScrollPane(tDesc));
        add(new JLabel("File Size (kB):")); add(tFile);
        add(new JLabel("Note:")); add(new JScrollPane(tNote));
        add(btnSave);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnSave.addActionListener(e -> {
            try {
                service.createTable();
                int size = tFile.getText().isEmpty() ? 0 : Integer.parseInt(tFile.getText());
                service.addJob(tTitle.getText(), tDesc.getText(), size, tNote.getText());
                JOptionPane.showMessageDialog(this, "Success!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        new JobForm().setVisible(true);
    }
}