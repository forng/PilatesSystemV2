package com.pilates.controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.pilates.model.Member;
import com.pilates.service.MemberService;
import com.pilates.service.MemberServiceImpl;

public class RegisterFrame extends JFrame {

    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    public RegisterFrame() {

        setTitle("會員註冊");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblName = new JLabel("姓名：");
        lblName.setBounds(50, 40, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(130, 40, 180, 25);
        add(txtName);

        JLabel lblPhone = new JLabel("手機：");
        lblPhone.setBounds(50, 80, 80, 25);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(130, 80, 180, 25);
        add(txtPhone);

        JLabel lblEmail = new JLabel("Email：");
        lblEmail.setBounds(50, 120, 80, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(130, 120, 180, 25);
        add(txtEmail);

        JLabel lblPassword = new JLabel("密碼：");
        lblPassword.setBounds(50, 160, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(130, 160, 180, 25);
        add(txtPassword);

        JButton btnRegister = new JButton("註冊");
        btnRegister.setBounds(130, 210, 80, 30);
        add(btnRegister);

        JButton btnBack = new JButton("返回");
        btnBack.setBounds(230, 210, 80, 30);
        add(btnBack);

        // ===== 註冊按鈕 =====
        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                register();
            }
        });

        // ===== 返回登入 =====
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void register() {

        try {
            MemberService service = new MemberServiceImpl();

            Member m = new Member();
            m.setName(txtName.getText());
            m.setPhone(txtPhone.getText());
            m.setEmail(txtEmail.getText());
            m.setPassword(new String(txtPassword.getPassword()));

            service.register(m);

            JOptionPane.showMessageDialog(this, "註冊成功");

            new LoginFrame().setVisible(true);
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}