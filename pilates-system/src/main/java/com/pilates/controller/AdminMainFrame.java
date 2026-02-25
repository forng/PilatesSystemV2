package com.pilates.controller;

import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {

    public AdminMainFrame() {

        setTitle("管理者模式");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== 上方標題 =====
        JLabel lblTitle = new JLabel("管理者控制台", SwingConstants.CENTER);
        lblTitle.setFont(new Font("新細明體", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        // ===== 中間功能區 =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JButton btnSchedule = new JButton("課程營運總覽");
        btnSchedule.addActionListener(e -> {
            new AdminScheduleFrame().setVisible(true);
            dispose();
        });

        JButton btnBooking = new JButton("會員預約查詢");
        btnBooking.addActionListener(e -> {
            new AdminBookingFrame().setVisible(true);
            dispose();
        });

        JButton btnReport = new JButton("營運統計");
        btnReport.addActionListener(e -> {
            new AdminReportFrame().setVisible(true);
            dispose();
        });
        centerPanel.add(btnSchedule);
        centerPanel.add(btnBooking);
        centerPanel.add(btnReport);

        add(centerPanel, BorderLayout.CENTER);
        // ===== 下方登出 =====
        JPanel bottomPanel = new JPanel();

        JButton btnLogout = new JButton("登出");
        bottomPanel.add(btnLogout);

        add(bottomPanel, BorderLayout.SOUTH);

        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
