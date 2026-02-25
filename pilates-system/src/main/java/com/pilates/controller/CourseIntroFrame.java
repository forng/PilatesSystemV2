package com.pilates.controller;

import javax.swing.*;
import java.awt.*;

public class CourseIntroFrame extends JFrame {

    public CourseIntroFrame() {

        setTitle("課程介紹");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== 標題 =====
        JLabel titleLabel = new JLabel("課程介紹", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微軟正黑體", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // ===== 主內容區 =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        mainPanel.add(createCoursePanel(
                "和緩皮拉提斯",
                "Fei 老師",
                "適合初學者與久坐族群。\n" +
                "透過溫和伸展與核心啟動，改善肩頸僵硬與下背痠痛。\n" +
                "強調呼吸控制與動作穩定性，幫助身體回到自然平衡狀態。\n" +
                "課程強度：★☆☆☆☆"
        ));

        mainPanel.add(Box.createVerticalStrut(25));

        mainPanel.add(createCoursePanel(
                "古典皮拉提斯",
                "Mini 老師",
                "承襲 Joseph Pilates 經典動作設計。\n" +
                "強調核心力量、身體控制與精準度。\n" +
                "適合已有基礎、希望提升肌力與線條雕塑的學員。\n" +
                "課程強度：★★★☆☆"
        ));

        mainPanel.add(Box.createVerticalStrut(25));

        mainPanel.add(createCoursePanel(
                "墊上皮拉提斯",
                "Angel 老師",
                "專注核心肌群與全身穩定訓練。\n" +
                "透過流暢動作串聯，提高身體協調與肌耐力。\n" +
                "適合想提升體態與燃脂效果的學員。\n" +
                "課程強度：★★★★☆"
        ));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // ===== 底部返回 =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        JButton btnBack = new JButton("返回主選單");

        btnBack.addActionListener(e -> {
            new MainMenuFrame().setVisible(true);
            dispose();
        });

        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ===== 課程區塊 =====
    private JPanel createCoursePanel(String courseName,
                                     String teacherName,
                                     String content) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(courseName));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JLabel teacherLabel = new JLabel("授課老師：" + teacherName);
        teacherLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));

        JTextArea textArea = new JTextArea(content);
        textArea.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(panel.getBackground());

        panel.add(teacherLabel, BorderLayout.NORTH);
        panel.add(textArea, BorderLayout.CENTER);

        return panel;
    }
}