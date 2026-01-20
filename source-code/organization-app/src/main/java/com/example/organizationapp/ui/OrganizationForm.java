package com.example.organizationapp.ui;

import com.example.organizationapp.entity.Organization;
import com.example.organizationapp.service.DirectorService;
import com.example.organizationapp.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
public class OrganizationForm extends JFrame {

    // Inject cả 2 Service để xử lý nghiệp vụ
    private final OrganizationService orgService;
    private final DirectorService directorService;

    // Các thành phần giao diện
    private JTextField txtName, txtAddress, txtPhone, txtEmail;
    private JButton btnSave, btnBack, btnDirector;

    // Biến lưu trữ Organization vừa tạo xong (để truyền sang form Giám Đốc)
    private Organization currentSavedOrg = null;

    @Autowired
    public OrganizationForm(OrganizationService orgService, DirectorService directorService) {
        this.orgService = orgService;
        this.directorService = directorService;
        // Không dùng UIManager.setLookAndFeel để giữ màu nút tùy chỉnh
        initUI();
    }

    private void initUI() {
        setTitle("Quản Lý Tổ Chức Doanh Nghiệp");
        setSize(650, 500); // Kích thước rộng rãi
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình

        // 1. Panel chính (Nền trắng, đệm viền 25px)
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(Color.WHITE);

        // 2. Tiêu đề (Chữ đậm, màu xanh dương)
        JLabel lblTitle = new JLabel("THÊM MỚI TỔ CHỨC", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(0, 102, 204));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // 3. Form nhập liệu (Dùng GridBagLayout để căn chỉnh)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12); // Khoảng cách giữa các ô
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 15);

        // --- Dòng 1: Tên tổ chức ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblName = new JLabel("Tên tổ chức (*):");
        lblName.setFont(labelFont);
        formPanel.add(lblName, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        txtName = new JTextField();
        txtName.setFont(textFont);
        txtName.setPreferredSize(new Dimension(250, 35));
        formPanel.add(txtName, gbc);

        // --- Dòng 2: Địa chỉ ---
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblAddress = new JLabel("Địa chỉ:");
        lblAddress.setFont(labelFont);
        formPanel.add(lblAddress, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtAddress = new JTextField();
        txtAddress.setFont(textFont);
        txtAddress.setPreferredSize(new Dimension(250, 35));
        formPanel.add(txtAddress, gbc);

        // --- Dòng 3: Số điện thoại ---
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(labelFont);
        formPanel.add(lblPhone, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        txtPhone = new JTextField();
        txtPhone.setFont(textFont);
        txtPhone.setPreferredSize(new Dimension(250, 35));
        formPanel.add(txtPhone, gbc);

        // --- Dòng 4: Email ---
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        formPanel.add(lblEmail, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        txtEmail = new JTextField();
        txtEmail.setFont(textFont);
        txtEmail.setPreferredSize(new Dimension(250, 35));
        formPanel.add(txtEmail, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 4. Khu vực nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setBackground(Color.WHITE);

        // Tạo các nút với màu sắc riêng
        btnSave = createStyledButton("Lưu Dữ Liệu", new Color(40, 167, 69));   // Xanh lá
        btnBack = createStyledButton("Quay Lại", new Color(108, 117, 125));    // Xám
        btnDirector = createStyledButton("Giám Đốc", new Color(0, 123, 255)); // Xanh dương

        // YÊU CẦU: Ban đầu nút Giám Đốc phải bị khóa (Disable)
        btnDirector.setEnabled(false);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnBack);
        buttonPanel.add(btnDirector);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Thêm sự kiện xử lý
        addEvents();
    }

    // Hàm tạo nút đẹp, hiện màu chuẩn trên mọi hệ điều hành
    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);

        // Các lệnh quan trọng để hiển thị màu nút phẳng (Flat Design)
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setPreferredSize(new Dimension(160, 45)); // Kích thước nút
        return btn;
    }

    private void addEvents() {
        // --- SỰ KIỆN NÚT LƯU ---
        btnSave.addActionListener(e -> {
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();

            // Tạo đối tượng từ dữ liệu nhập
            Organization org = new Organization(name, address, phone, email);

            // Gọi Service để lưu vào Database
            String result = orgService.saveOrganization(org);

            if (result.equals("Save successfully")) {
                JOptionPane.showMessageDialog(this,
                        "Đã lưu thành công tổ chức vào Cơ sở dữ liệu!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // QUAN TRỌNG: Lưu lại object org vừa tạo để dùng cho form Giám Đốc
                this.currentSavedOrg = org;

                // Mở khóa nút Giám Đốc
                btnDirector.setEnabled(true);
                // Set lại màu nền để đảm bảo nút sáng lên (tránh bị Swing làm mờ)
                btnDirector.setBackground(new Color(0, 123, 255));
            } else {
                // Nếu lỗi: Dịch sang tiếng Việt và hiện thông báo
                String errorMsg = translateError(result);
                JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Nhập Liệu", JOptionPane.ERROR_MESSAGE);

                // Khóa lại nút Giám Đốc
                btnDirector.setEnabled(false);
            }
        });

        // --- SỰ KIỆN NÚT QUAY LẠI ---
        btnBack.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thoát chương trình?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // --- SỰ KIỆN NÚT GIÁM ĐỐC ---
        btnDirector.addActionListener(e -> {
            if (currentSavedOrg != null) {
                // Mở Form Giám Đốc (Truyền Tổ chức và Service sang)
                DirectorForm directorForm = new DirectorForm(currentSavedOrg, directorService);
                directorForm.setVisible(true);
            } else {
                // Phòng trường hợp lỗi không mong muốn
                JOptionPane.showMessageDialog(this,
                        "Vui lòng lưu thông tin tổ chức trước!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Hàm dịch thông báo lỗi từ tiếng Anh sang tiếng Việt
    private String translateError(String errorMsg) {
        if (errorMsg.contains("Organization Name cannot be empty"))
            return "Tên tổ chức không được để trống!";
        if (errorMsg.contains("Organization Name must be between"))
            return "Tên tổ chức phải từ 3 đến 255 ký tự!";
        if (errorMsg.contains("Organization Name already exists"))
            return "Tên tổ chức này đã tồn tại trong hệ thống!";
        if (errorMsg.contains("Phone must contains only digits"))
            return "Số điện thoại chỉ được chứa số và dài từ 9-12 ký tự!";
        if (errorMsg.contains("Invalid Email format"))
            return "Định dạng Email không hợp lệ (ví dụ: abc@gmail.com)!";

        return errorMsg; // Trả về lỗi gốc nếu không khớp
    }
}