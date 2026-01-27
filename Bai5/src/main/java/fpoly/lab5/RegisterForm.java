package fpoly.lab5;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterForm extends JFrame {
    private JTextField txtId, txtName, txtEmail, txtPhone, txtAddress, txtDob;
    private JPasswordField txtPass, txtConfirm;
    private JRadioButton rdoNam, rdoNu, rdoKhac;
    private JCheckBox chkTos;
    private JButton btnRegister, btnReset;
    private CustomerService service = new CustomerService();

    public RegisterForm() {
        // 1. Cài đặt giao diện hiện đại (System Look and Feel)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        DatabaseConnection.initDB();

        setTitle("Đăng Ký Tài Khoản");
        setSize(500, 750); // Tăng chiều cao một chút cho thoáng
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sử dụng BorderLayout cho khung chính
        setLayout(new BorderLayout());

        // --- TIÊU ĐỀ (HEADER) ---
        JLabel lblHeader = new JLabel("ĐĂNG KÝ TÀI KHOẢN", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(new Color(41, 128, 185)); // Màu xanh dương hiện đại
        lblHeader.setBorder(new EmptyBorder(20, 0, 20, 0)); // Padding trên dưới
        add(lblHeader, BorderLayout.NORTH);

        // --- FORM NHẬP LIỆU (CENTER) ---
        // Dùng JPanel phụ với GridBagLayout hoặc GridLayout có khoảng cách
        JPanel pnlForm = new JPanel(new GridLayout(10, 2, 15, 15)); // Khoảng cách giữa các ô là 15px
        pnlForm.setBorder(new EmptyBorder(10, 40, 10, 40)); // Lề trái phải 40px cho đẹp

        // Helper để tạo Label đẹp
        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);

        // Thêm các component
        pnlForm.add(createLabel("Mã Khách Hàng (*):", fontLabel));
        txtId = createTextField(fontInput); pnlForm.add(txtId);

        pnlForm.add(createLabel("Họ và Tên (*):", fontLabel));
        txtName = createTextField(fontInput); pnlForm.add(txtName);

        pnlForm.add(createLabel("Email (*):", fontLabel));
        txtEmail = createTextField(fontInput); pnlForm.add(txtEmail);

        pnlForm.add(createLabel("Số điện thoại (*):", fontLabel));
        txtPhone = createTextField(fontInput); pnlForm.add(txtPhone);

        pnlForm.add(createLabel("Địa chỉ (*):", fontLabel));
        txtAddress = createTextField(fontInput); pnlForm.add(txtAddress);

        pnlForm.add(createLabel("Ngày sinh (mm/dd/yyyy):", fontLabel));
        txtDob = createTextField(fontInput); pnlForm.add(txtDob);

        pnlForm.add(createLabel("Giới tính:", fontLabel));
        JPanel pnlGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rdoNam = new JRadioButton("Nam"); rdoNam.setFont(fontInput); rdoNam.setSelected(true);
        rdoNu = new JRadioButton("Nữ"); rdoNu.setFont(fontInput);
        rdoKhac = new JRadioButton("Khác"); rdoKhac.setFont(fontInput);
        ButtonGroup group = new ButtonGroup();
        group.add(rdoNam); group.add(rdoNu); group.add(rdoKhac);
        pnlGender.add(rdoNam); pnlGender.add(Box.createHorizontalStrut(15));
        pnlGender.add(rdoNu); pnlGender.add(Box.createHorizontalStrut(15));
        pnlGender.add(rdoKhac);
        pnlForm.add(pnlGender);

        pnlForm.add(createLabel("Mật khẩu (*):", fontLabel));
        txtPass = new JPasswordField(); pnlForm.add(txtPass);

        pnlForm.add(createLabel("Xác nhận MK (*):", fontLabel));
        txtConfirm = new JPasswordField(); pnlForm.add(txtConfirm);

        // Điều khoản dịch vụ (chiếm 1 dòng riêng ở dưới)
        chkTos = new JCheckBox("Tôi đồng ý với các điều khoản dịch vụ");
        chkTos.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        // Panel chứa phần Form và Checkbox
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlTos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTos.add(chkTos);
        pnlCenter.add(pnlTos, BorderLayout.SOUTH);

        add(pnlCenter, BorderLayout.CENTER);

        // --- NÚT BẤM (FOOTER) ---
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnRegister = new JButton("ĐĂNG KÝ");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setBackground(new Color(46, 204, 113)); // Màu xanh lá
        btnRegister.setPreferredSize(new Dimension(120, 40));
        btnRegister.setFocusPainted(false); // Bỏ viền khi click

        btnReset = new JButton("NHẬP LẠI");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReset.setBackground(new Color(231, 76, 60)); // Màu đỏ
        btnReset.setPreferredSize(new Dimension(120, 40));
        btnReset.setFocusPainted(false);

        pnlButton.add(btnRegister);
        pnlButton.add(btnReset);
        add(pnlButton, BorderLayout.SOUTH);

        // --- XỬ LÝ SỰ KIỆN (Logic giữ nguyên) ---
        btnRegister.addActionListener(e -> {
            String gender = rdoNam.isSelected() ? "Nam" : (rdoNu.isSelected() ? "Nữ" : "Khác");
            String msg = service.validateAndRegister(
                    txtId.getText(), txtName.getText(), txtEmail.getText(),
                    txtPhone.getText(), txtAddress.getText(),
                    new String(txtPass.getPassword()),
                    new String(txtConfirm.getPassword()),
                    chkTos.isSelected(),
                    gender,
                    txtDob.getText()
            );

            if (msg.contains("thành công")) {
                JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, msg, "Lỗi đăng ký", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReset.addActionListener(e -> {
            txtId.setText(""); txtName.setText(""); txtEmail.setText("");
            txtPhone.setText(""); txtAddress.setText(""); txtDob.setText("");
            txtPass.setText(""); txtConfirm.setText("");
            chkTos.setSelected(false);
            rdoNam.setSelected(true);
        });

        setVisible(true);
    }

    // Hàm phụ để tạo Label nhanh
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        return lbl;
    }

    // Hàm phụ để tạo TextField nhanh
    private JTextField createTextField(Font font) {
        JTextField txt = new JTextField();
        txt.setFont(font);
        // Padding bên trong ô nhập liệu
        txt.setBorder(BorderFactory.createCompoundBorder(
                txt.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return txt;
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}