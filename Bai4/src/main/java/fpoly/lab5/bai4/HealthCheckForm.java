package fpoly.lab5.bai4;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HealthCheckForm extends JFrame {

    private JTextField txtAge;
    private JRadioButton rdoMale, rdoFemale;
    private JLabel lblResultValue;
    private JButton btnCalculate, btnReset;

    // Khai báo màu nền chung (Màu trắng)
    private final Color BG_COLOR = Color.WHITE;

    public HealthCheckForm() {
        // 1. Cài đặt giao diện Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        setTitle("Tính Phí Khám Bệnh");
        setSize(500, 500); // Tăng chiều cao lên 500 để không bị che
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set màu nền cho container chính
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // --- HEADER ---
        JLabel lblHeader = new JLabel("TÍNH PHÍ DỊCH VỤ", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(new Color(41, 128, 185));
        lblHeader.setBorder(new EmptyBorder(30, 0, 30, 0));
        add(lblHeader, BorderLayout.NORTH);

        // --- BODY ---
        // Dùng GridLayout với khoảng cách (vgap) lớn để các ô không dính nhau
        JPanel pnlCenter = new JPanel(new GridLayout(3, 1, 20, 20));
        pnlCenter.setBackground(BG_COLOR); // Màu nền trắng
        pnlCenter.setBorder(new EmptyBorder(10, 50, 10, 50)); // Lề 2 bên

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 16);

        // 1. Panel Tuổi (Sửa lỗi bị che: Dùng BorderLayout cho panel con này)
        JPanel pnlAge = new JPanel(new BorderLayout(0, 10)); // Khoảng cách giữa nhãn và ô nhập là 10
        pnlAge.setBackground(BG_COLOR);

        JLabel lblAge = new JLabel("Nhập tuổi bệnh nhân:");
        lblAge.setFont(fontLabel);

        txtAge = new JTextField();
        txtAge.setFont(fontInput);
        txtAge.setPreferredSize(new Dimension(200, 40)); // Ép chiều cao ô nhập là 40px
        // Tạo padding bên trong ô nhập
        txtAge.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)));

        pnlAge.add(lblAge, BorderLayout.NORTH);
        pnlAge.add(txtAge, BorderLayout.CENTER);
        pnlCenter.add(pnlAge);

        // 2. Panel Giới tính
        JPanel pnlGenderGroup = new JPanel(new BorderLayout(0, 10));
        pnlGenderGroup.setBackground(BG_COLOR);

        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(fontLabel);

        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlRadio.setBackground(BG_COLOR); // Quan trọng: set màu trắng cho panel chứa radio

        rdoMale = new JRadioButton("Nam");
        rdoMale.setFont(fontInput);
        rdoMale.setSelected(true);
        rdoMale.setBackground(BG_COLOR); // Set màu trắng cho nút radio
        rdoMale.setFocusPainted(false); // Bỏ viền focus xấu

        rdoFemale = new JRadioButton("Nữ");
        rdoFemale.setFont(fontInput);
        rdoFemale.setBackground(BG_COLOR);
        rdoFemale.setFocusPainted(false);

        ButtonGroup group = new ButtonGroup();
        group.add(rdoMale); group.add(rdoFemale);

        pnlRadio.add(rdoMale);
        pnlRadio.add(Box.createHorizontalStrut(30));
        pnlRadio.add(rdoFemale);

        pnlGenderGroup.add(lblGender, BorderLayout.NORTH);
        pnlGenderGroup.add(pnlRadio, BorderLayout.CENTER);
        pnlCenter.add(pnlGenderGroup);

        // 3. Panel Kết quả
        JPanel pnlResult = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pnlResult.setBackground(new Color(248, 248, 248)); // Màu xám cực nhạt để phân biệt xíu
        pnlResult.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));

        JLabel lblResultTitle = new JLabel("Thành tiền: ");
        lblResultTitle.setFont(fontLabel);

        lblResultValue = new JLabel("0 Euro");
        lblResultValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblResultValue.setForeground(new Color(192, 57, 43));

        pnlResult.add(lblResultTitle);
        pnlResult.add(lblResultValue);
        pnlCenter.add(pnlResult);

        add(pnlCenter, BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlButton.setBackground(BG_COLOR); // Màu nền trắng

        btnCalculate = new JButton("TÍNH TOÁN");
        btnCalculate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCalculate.setBackground(new Color(39, 174, 96));
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.setPreferredSize(new Dimension(140, 45));
        btnCalculate.setFocusPainted(false);

        btnReset = new JButton("NHẬP LẠI");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReset.setBackground(new Color(127, 140, 141));
        btnReset.setForeground(Color.WHITE);
        btnReset.setPreferredSize(new Dimension(140, 45));
        btnReset.setFocusPainted(false);

        pnlButton.add(btnCalculate);
        pnlButton.add(btnReset);
        add(pnlButton, BorderLayout.SOUTH);

        // --- SỰ KIỆN ---
        btnCalculate.addActionListener(e -> {
            try {
                if (txtAge.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tuổi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int age = Integer.parseInt(txtAge.getText().trim());
                String gender = rdoMale.isSelected() ? "Nam" : "Nữ";

                // Gọi logic từ file Utils
                int fee = HealthCheckUtils.calculateFee(age, gender);

                lblResultValue.setText(fee + " Euro");
                lblResultValue.setForeground(new Color(39, 174, 96));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tuổi phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReset.addActionListener(e -> {
            txtAge.setText("");
            rdoMale.setSelected(true);
            lblResultValue.setText("0 Euro");
            lblResultValue.setForeground(new Color(192, 57, 43));
            txtAge.requestFocus();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthCheckForm());
    }
}