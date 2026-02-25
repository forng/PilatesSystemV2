package com.pilates.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.pilates.dao.AdminReportDao;
import com.pilates.dao.AdminReportDaoImpl;
import com.pilates.model.RankingView;

import java.awt.*;
import java.util.List;
import com.pilates.util.ExcelExporter;
public class AdminReportFrame extends JFrame {

    private AdminReportDao dao = new AdminReportDaoImpl();

    public AdminReportFrame() {

        setTitle("營運統計報表");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== 標題 =====
        JLabel titleLabel = new JLabel("📊 Q1 營運統計", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微軟正黑體", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // ===== 主內容區 =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JScrollPane mainScroll = new JScrollPane(mainPanel);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScroll, BorderLayout.CENTER);

        try {

            // ===== KPI 區塊 =====
            JPanel kpiPanel = new JPanel(new GridLayout(3, 2, 30, 30));
            kpiPanel.setBorder(BorderFactory.createTitledBorder("Q1 營運總覽"));
            kpiPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

            kpiPanel.add(createKpiLabel("Q1總營收",
                    dao.getQuarterRevenue() + " 元"));

            kpiPanel.add(createKpiLabel("Q1售出點數",
                    String.valueOf(dao.getQuarterSoldPoints())));

            kpiPanel.add(createKpiLabel("Q1消耗點數",
                    String.valueOf(dao.getMonthlyDebitPoints())));

            kpiPanel.add(createKpiLabel("Q1平均單點價格",
                    String.format("%.2f 元",
                            (double) dao.getQuarterRevenue() /
                            Math.max(1, dao.getQuarterSoldPoints()))));

            kpiPanel.add(createKpiLabel("Q1預約總人次",
                    String.valueOf(dao.getMonthlyBookingCount())));

            kpiPanel.add(createKpiLabel("Q1取消次數",
                    String.valueOf(dao.getMonthlyCancelCount())));
            
            
            
            mainPanel.add(kpiPanel);
            mainPanel.add(Box.createVerticalStrut(30));

            // ===== 教練排行 =====
            mainPanel.add(createRankingSection(
                    "教練排行 Top5",
                    dao.getTop5CoachRanking(),
                    "教練",
                    "上課人次"
            ));

            mainPanel.add(Box.createVerticalStrut(30));

            // ===== 客戶消費排行 =====
            mainPanel.add(createRankingSection(
                    "客戶消費排行 Top5",
                    dao.getTop5CustomerPurchaseRanking(),
                    "會員",
                    "購買點數"
            ));

            mainPanel.add(Box.createVerticalStrut(30));

            // ===== 客戶上課排行 =====
            mainPanel.add(createRankingSection(
                    "客戶上課排行 Top5",
                    dao.getTop5CustomerAttendanceRanking(),
                    "會員",
                    "上課次數"
            ));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "報表載入失敗");
        }

        // ===== 底部按鈕區 =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));

        JButton btnExport = new JButton("匯出 Excel");
        btnExport.addActionListener(e -> {
            com.pilates.util.ExcelExporter.exportQ1Report();
        });
        
        
        JButton btnBack = new JButton("返回");

        btnBack.addActionListener(e -> {
            new AdminMainFrame().setVisible(true);
            dispose();
        });

        bottomPanel.add(btnExport);
        bottomPanel.add(btnBack);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ===== KPI Label 樣式 =====
    private JLabel createKpiLabel(String title, String value) {
        JLabel label = new JLabel(title + "： " + value);
        label.setFont(new Font("微軟正黑體", Font.BOLD, 18));
        return label;
    }

    // ===== 排行區塊 =====
    private JPanel createRankingSection(String title,
                                        List<RankingView> list,
                                        String col1,
                                        String col2) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        String[] columns = {col1, col2};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        styleTable(table);

        for (RankingView r : list) {
            model.addRow(new Object[]{r.getName(), r.getTotal()});
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    // ===== 表格樣式 =====
    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("微軟正黑體", Font.BOLD, 15));
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(220, 235, 250));
    }
}