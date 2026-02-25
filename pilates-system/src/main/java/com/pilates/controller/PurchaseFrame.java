package com.pilates.controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.pilates.dao.PackageDao;
import com.pilates.dao.PackageDaoImpl;
import com.pilates.model.Package;
import com.pilates.service.MemberService;
import com.pilates.service.MemberServiceImpl;
import com.pilates.util.Session;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PurchaseFrame extends JFrame {

    private JPanel contentPane;
    private JComboBox<Package> comboBox;
    private JLabel lblPoint;

    public PurchaseFrame() {

        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ===== 顯示目前點數 =====
        lblPoint = new JLabel("目前點數：" + Session.currentMember.getPointBalance());
        lblPoint.setBounds(120, 30, 200, 30);
        contentPane.add(lblPoint);

        // ===== 方案下拉選單 =====
        comboBox = new JComboBox<>();
        comboBox.setBounds(120, 80, 200, 30);
        contentPane.add(comboBox);

        loadPackages();

        // ===== 購買按鈕 =====
        JButton btnPurchase = new JButton("購買");
        btnPurchase.setBounds(120, 130, 90, 30);
        contentPane.add(btnPurchase);

        btnPurchase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                purchase();
            }
        });

        // ===== 返回主選單 =====
        JButton btnBack = new JButton("返回");
        btnBack.setBounds(230, 130, 90, 30);
        contentPane.add(btnBack);

        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainMenuFrame().setVisible(true);
                dispose();
            }
        });
        }
        
     // 🔵 STEP2 
        private void loadPackages() {
            try {
                PackageDao dao = new PackageDaoImpl();
                List<Package> list = dao.findAll();

                for (Package p : list) {
                    comboBox.addItem(p);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "讀取方案失敗");
            }
        }
        
        private void purchase() {

            Package selected = (Package) comboBox.getSelectedItem();

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "請選擇方案");
                return;
            }

            try {
                MemberService service = new MemberServiceImpl();

                service.purchasePackage(
                        Session.currentMember.getId(),
                        selected.getId()
                );

                // 更新 Session 點數
                Session.currentMember.setPointBalance(
                        Session.currentMember.getPointBalance() + selected.getTotalPoints()
                );

                lblPoint.setText("目前點數：" + Session.currentMember.getPointBalance());

                JOptionPane.showMessageDialog(this, "購買成功");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "購買失敗");
            }
        }

    }