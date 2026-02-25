package com.pilates.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.pilates.dao.BookingDao;
import com.pilates.dao.BookingDaoImpl;
import com.pilates.model.BookingView;
import com.pilates.service.MemberService;
import com.pilates.service.MemberServiceImpl;
import com.pilates.util.Session;
import com.pilates.util.Tool;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
public class MyBookingFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JLabel lblPoint;
    public MyBookingFrame() {

    	 	setTitle("我的預約");
    	    setSize(800, 500);
    	    setLocationRelativeTo(null);
    	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPoint = new JLabel("目前點數：" + Session.currentMember.getPointBalance());
    
        topPanel.add(lblPoint);

        add(topPanel, BorderLayout.NORTH);
        String[] columns = {
            "ID",
            "課程",
            "教練",
            "上課時間",
            "點數",
            "預約時間"
        };

        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        
        //隱藏id欄位
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadMyBookings();

        JButton btnCancel = new JButton("取消預約");
        btnCancel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		  int selectedRow = table.getSelectedRow();

        	        if (selectedRow == -1) {
        	            JOptionPane.showMessageDialog(null, "請選擇預約");
        	            return;
        	        }

        	        int bookingId = (int) model.getValueAt(selectedRow, 0);

        	        try {

        	            MemberService service = new MemberServiceImpl();
        	            service.cancelBooking(bookingId);

        	            JOptionPane.showMessageDialog(null, "取消成功");
        	         // 重新查會員最新點數
        	            String sql = "SELECT point_balance FROM members WHERE id = ?";

        	            try (Connection conn = Tool.getDb();
        	                 PreparedStatement ps = conn.prepareStatement(sql)) {

        	                ps.setInt(1, Session.currentMember.getId());
        	                ResultSet rs = ps.executeQuery();

        	                if (rs.next()) {
        	                    int newPoint = rs.getInt("point_balance");

        	                    // 更新 Session
        	                    Session.currentMember.setPointBalance(newPoint);

        	                    // 更新畫面 Label
        	                    lblPoint.setText("目前點數：" + newPoint);
        	                }
        	            }
        	            // 重新整理畫面
        	            model.setRowCount(0);
        	            loadMyBookings();

        	        } catch (Exception ex) {
        	            JOptionPane.showMessageDialog(null, ex.getMessage());
        	        }
        		
        	}
        });
       
        

        JButton btnBack = new JButton("返回");
       
        

        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new MainMenuFrame().setVisible(true);
                dispose();
            }
        });
        JPanel bottomPanel = new JPanel();

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnBack);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadMyBookings() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/dd HH:mm");

        try {

            BookingDao dao = new BookingDaoImpl();
            List<BookingView> list =
                    dao.findViewByMemberId(
                            Session.currentMember.getId()
                    );
            System.out.println("會員ID：" + Session.currentMember.getId());
            System.out.println("查到筆數：" + list.size());

            for (BookingView bv : list) {

                Object[] row = {
                    bv.getBookingId(),
                    bv.getTemplateName(),
                    bv.getCoachName(),
                    bv.getStartTime().format(formatter),
                    bv.getPointCost(),
                    bv.getBookingTime().format(formatter)
                };

                model.addRow(row);
            }

        } catch (Exception e) {
        	 e.printStackTrace();   // 👈 加這行
        	    JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}