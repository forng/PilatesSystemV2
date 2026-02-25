package com.pilates.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.pilates.dao.AdminScheduleDao;
import com.pilates.dao.AdminScheduleDaoImpl;
import com.pilates.model.AdminScheduleView;

public class AdminScheduleFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminScheduleFrame() {

        setTitle("課程營運總覽");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== 標題 =====
        JLabel lblTitle = new JLabel("課程營運總覽", SwingConstants.CENTER);
        lblTitle.setFont(new Font("新細明體", Font.BOLD, 22));
        add(lblTitle, BorderLayout.NORTH);

        // ===== 表格欄位 =====
        String[] columns = {
                "課程",
                "教練",
                "上課時間",
                "已預約",
                "最大容量",
                "狀態"
        };

        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ===== 底部 =====
        JPanel bottomPanel = new JPanel();
        JButton btnBack = new JButton("返回");
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            new AdminMainFrame().setVisible(true);
            dispose();
        });

        loadSchedules();
    }

    private void loadSchedules() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/dd HH:mm");

        try {

            AdminScheduleDao dao = new AdminScheduleDaoImpl();
            List<AdminScheduleView> list = dao.findAllSchedules();

            for (AdminScheduleView v : list) {

                Object[] row = {
                        v.getTemplateName(),
                        v.getCoachName(),
                        v.getStartTime().format(formatter),
                        v.getCurrentCapacity(),
                        v.getMaxCapacity(),
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