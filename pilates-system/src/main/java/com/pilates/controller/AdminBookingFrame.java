package com.pilates.controller;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.pilates.dao.AdminBookingDao;
import com.pilates.dao.AdminBookingDaoImpl;
import com.pilates.model.AdminBookingView;

public class AdminBookingFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminBookingFrame() {

        setTitle("會員預約紀錄查詢");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("會員預約紀錄查詢", SwingConstants.CENTER);
        lblTitle.setFont(new Font("新細明體", Font.BOLD, 22));
        add(lblTitle, BorderLayout.NORTH);

        String[] columns = {
            "會員",
            "課程",
            "教練",
            "上課時間",
            "預約時間",
            "狀態"
        };

        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnBack = new JButton("返回");
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            new AdminMainFrame().setVisible(true);
            dispose();
        });

        loadBookings();
    }

    private void loadBookings() {

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("MM/dd HH:mm");

        try {

            AdminBookingDao dao = new AdminBookingDaoImpl();
            List<AdminBookingView> list = dao.findAllBookings();

            for (AdminBookingView v : list) {

                Object[] row = {
                    v.getMemberName(),
                    v.getTemplateName(),
                    v.getCoachName(),
                    v.getStartTime().format(formatter),
                    v.getBookingTime().format(formatter),
                    v.getStatus()
                };

                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "讀取失敗");
        }
    }
}