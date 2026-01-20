package com.example.organizationapp.ui;

import com.example.organizationapp.entity.Director;
import com.example.organizationapp.entity.Organization;
import com.example.organizationapp.service.DirectorService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Class này không cần @Component vì ta sẽ tự new nó từ OrganizationForm
public class DirectorForm extends JFrame {

    private final Organization currentOrg;
    private final DirectorService service;

    private JTextField txtName, txtPhone, txtEmail;
    private JButton btnSave, btnClose;

    public DirectorForm(Organization org, DirectorService service) {
        this.currentOrg = org;
        this.service = service;
        initUI();
    }

    private void initUI() {
        setTitle("Quản Lý Giám Đốc");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng form này, không tắt app
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // --- Tiêu đề & Thông tin Organization ---
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("THÊM GIÁM ĐỐC", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));

        // Hiển thị tên công ty đang thao tác
        JLabel lblOrgInfo = new JLabel("Cho tổ chức: " + currentOrg.getOrgName().toUpperCase(), SwingConstants.CENTER);
        lblOrgInfo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblOrgInfo.setForeground(Color.GRAY);

        headerPanel.add(lblTitle);
        headerPanel.add(lblOrgInfo);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Form nhập liệu ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // Tên Giám Đốc
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tên Giám Đốc (*):"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtName = new JTextField();
        txtName.setFont(font);
        txtName.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtName, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPhone = new JTextField();
        txtPhone.setFont(font);
        txtPhone.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtPhone, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        txtEmail = new JTextField();
        txtEmail.setFont(font);
        txtEmail.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtEmail, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- Nút bấm ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        btnSave = createButton("Lưu Giám Đốc", new Color(40, 167, 69));
        btnClose = createButton("Đóng", new Color(108, 117, 125));

        btnPanel.add(btnSave);
        btnPanel.add(btnClose);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
        addEvents();
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    private void addEvents() {
        btnSave.addActionListener(e -> {
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();

            // Tạo đối tượng Director và gán Organization hiện tại vào
            Director director = new Director(name, phone, email, currentOrg);

            String result = service.saveDirector(director);

            if (result.equals("Save successfully")) {
                JOptionPane.showMessageDialog(this, "Thêm giám đốc thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Đóng form sau khi lưu xong
            } else {
                JOptionPane.showMessageDialog(this, result.replace("Error: ", ""), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClose.addActionListener(e -> dispose());
    }
}