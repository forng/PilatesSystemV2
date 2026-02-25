package com.pilates.controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.pilates.dao.MemberDao;
import com.pilates.dao.MemberDaoImpl;
import com.pilates.model.Member;
import com.pilates.util.Session;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("皮拉提斯會員管理系統");
		lblNewLabel.setFont(new Font("新細明體", Font.PLAIN, 32));
		lblNewLabel.setBounds(152, -14, 404, 98);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("手機:");
		lblNewLabel_1.setFont(new Font("新細明體", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(162, 78, 84, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("密碼:");
		lblNewLabel_1_1.setFont(new Font("新細明體", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(162, 128, 84, 29);
		contentPane.add(lblNewLabel_1_1);
		
		textField = new JTextField();
		textField.setBounds(223, 83, 248, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(223, 133, 248, 21);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("登入");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			      String phone = textField.getText();
			        String password = new String(passwordField.getPassword());

			        if (phone.isEmpty() || password.isEmpty()) {
			            JOptionPane.showMessageDialog(null, "請輸入手機與密碼");
			            return;
			        }

			        try {
			            MemberDao dao = new MemberDaoImpl();
			            Member m = dao.login(phone, password);

			            if (m == null) {
			                JOptionPane.showMessageDialog(null, "登入失敗");
			            } else {
			                JOptionPane.showMessageDialog(null, "登入成功");

			                Session.currentMember = m;

			                new MainMenuFrame().setVisible(true);
			                dispose();
			            }

			        } catch (Exception ex) {
			            ex.printStackTrace();
			            JOptionPane.showMessageDialog(null, "系統錯誤");
			        }
				
				
				
				
				
				
			}
		});
		btnNewButton.setBounds(161, 177, 85, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("新會員註冊");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new RegisterFrame().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(281, 177, 105, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("管理者模式");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String phone = textField.getText();
		        String password = new String(passwordField.getPassword());

		        if (phone.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "請輸入帳號與密碼");
		            return;
		        }

		        try {

		            MemberDao dao = new MemberDaoImpl();
		            Member m = dao.login(phone, password);

		            if (m == null) {
		                JOptionPane.showMessageDialog(null, "登入失敗");
		                return;
		            }

		            if (!"ADMIN".equals(m.getStatus())) {
		                JOptionPane.showMessageDialog(null, "您不是管理者");
		                return;
		            }

		            Session.currentMember = m;

		            new AdminMainFrame().setVisible(true);
		            dispose();

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "系統錯誤");
		        }
				
			}
		});
		btnNewButton_2.setBounds(162, 265, 138, 23);
		contentPane.add(btnNewButton_2);

	}

}
