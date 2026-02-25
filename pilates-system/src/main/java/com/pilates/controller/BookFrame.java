package com.pilates.controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.pilates.dao.ScheduleDao;
import com.pilates.dao.ScheduleDaoImpl;
import com.pilates.model.ClassSchedule;
import com.pilates.service.MemberService;
import com.pilates.service.MemberServiceImpl;
import com.pilates.util.Session;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.Font;

import java.awt.Color;
import javax.swing.ListSelectionModel;

public class BookFrame extends JFrame {

    private static final long serialVersionUID = 1L;

   
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblPoint;
   
    public BookFrame() {

    	 setTitle("課程預約");
    	    setSize(800, 500);
    	    setLocationRelativeTo(null);
    	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    setLayout(new BorderLayout());

        
       

        // ===== 顯示點數 =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPoint = new JLabel("目前點數：" + Session.currentMember.getPointBalance());
       
        topPanel.add(lblPoint);

        add(topPanel, BorderLayout.NORTH);

        // ===== JTable =====
        String[] columnNames = { 	
        		 "ID",
        		    "課程",
        		    "教練",
        		    "開始時間",
        		    "剩餘名額",
        		    "點數",
        		    "狀態"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 所有儲存格不可編輯
            }
        };
     // 建立 JTable 並套用不可編輯的 TableModel
        table = new JTable(model);
       
     // 🔹 隱藏 ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        // ===== 1️⃣ 選取模式設定 =====
        table.setRowSelectionAllowed(true);       // 允許整列選取
        table.setColumnSelectionAllowed(false);   // 禁止選整欄
        table.setCellSelectionEnabled(false);     // 禁止單格選取
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能選一列

        // ===== 2️⃣ 外觀設定 =====
        table.setRowHeight(30); // 行高（讓畫面舒服）
        table.setFont(new Font("新細明體", Font.PLAIN, 14)); // 內容字體
        table.getTableHeader().setFont(new Font("新細明體", Font.BOLD, 15)); // 標題字體

        // ===== 3️⃣ 商業系統常見設定 =====
        table.getTableHeader().setReorderingAllowed(false); // 禁止拖拉欄位
        table.setGridColor(Color.LIGHT_GRAY); // 格線顏色
        table.setSelectionBackground(new Color(200, 220, 240)); // 選取顏色（柔和藍）

        // ===== 4️⃣ 保持焦點（但不顯示單格框線）=====
        table.setRowSelectionAllowed(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
       

        loadSchedules();
        
  

	
        // ===== 預約按鈕 =====
        JButton btnBook = new JButton("預約課程");
     
       

        btnBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bookClass();
            }
        });

        // ===== 返回 =====
        JButton btnBack = new JButton("返回");
       
     
        
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainMenuFrame().setVisible(true);
                dispose();
            }
        });
    
    
        // 建立底部容器
        	JPanel bottomPanel = new JPanel();
			bottomPanel.add(btnBook);
			bottomPanel.add(btnBack);

			// 把整個 panel 放到 SOUTH
			add(bottomPanel, BorderLayout.SOUTH);
    }
    			
        private void loadSchedules() {
        	DateTimeFormatter formatter =
        	        DateTimeFormatter.ofPattern("MM/dd HH:mm");

            try {
            		ScheduleDao dao = new ScheduleDaoImpl();
                List<ClassSchedule> list = dao.findAvailable(Session.currentMember.getId()); 
                // 這個方法是你之前寫的 current_capacity < max_capacity

                for (ClassSchedule cs : list) {

                	Object[] row = {
                			cs.getId(),
                		    cs.getTemplateName(),
                		    cs.getCoachName(),
                		    cs.getStartTime().format(formatter),
                		    cs.getMaxCapacity() - cs.getCurrentCapacity(),
                		    cs.getPointCost(),
                		    cs.isBooked() ? "✔ 已預約" : ""
                		};

                    model.addRow(row);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "讀取課程失敗");
            }
        }
        private void bookClass() {

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "請選擇課程");
                return;
            }

            // ✅ 先把需要的資料存起來（在刷新前）
            int scheduleId = (int) model.getValueAt(selectedRow, 0);
            int cost = (int) model.getValueAt(selectedRow, 5);

            try {

                MemberService service = new MemberServiceImpl();
                service.bookClass(Session.currentMember.getId(), scheduleId);

                JOptionPane.showMessageDialog(this, "預約成功");

                // ✅ 先更新 Session 點數
                Session.currentMember.setPointBalance(
                    Session.currentMember.getPointBalance() - cost
                );

                lblPoint.setText("目前點數：" + 
                    Session.currentMember.getPointBalance());

                // ✅ 最後再刷新表格
                model.setRowCount(0);
                loadSchedules();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } 
    }