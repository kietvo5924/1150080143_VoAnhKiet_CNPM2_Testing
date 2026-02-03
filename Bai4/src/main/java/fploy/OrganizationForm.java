package fploy;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class OrganizationForm extends JFrame {
    private JTextField txtUnitId, txtName;
    private JTextArea txtDescription;
    private JButton btnSave, btnCancel;
    private OrganizationService service = new OrganizationService();

    public OrganizationForm() {
        setTitle("Add Organization Unit");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        txtUnitId = new JTextField();
        txtName = new JTextField();
        txtDescription = new JTextArea(3, 20);
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        add(new JLabel("Unit Id:"));
        add(txtUnitId);
        add(new JLabel("Name*:"));
        add(txtName);
        add(new JLabel("Description:"));
        add(new JScrollPane(txtDescription));

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnCancel);
        panelButtons.add(btnSave);
        add(panelButtons);

        btnSave.addActionListener(e -> {
            try {
                service.createTable();
                boolean success = service.addUnit(txtUnitId.getText(), txtName.getText(), txtDescription.getText());
                if (success) JOptionPane.showMessageDialog(this, "Save thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> {
            txtUnitId.setText("");
            txtName.setText("");
            txtDescription.setText("");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrganizationForm().setVisible(true));
    }
}