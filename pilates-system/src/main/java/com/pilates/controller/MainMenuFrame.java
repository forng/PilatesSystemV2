package com.pilates.controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pilates.util.Session;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuFrame frame = new MainMenuFrame();
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
	public MainMenuFrame() {
		if (Session.currentMember == null) {
	        new LoginFrame().setVisible(true);
	        dispose();
	        return;
	    }

		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    setBounds(100, 100, 450, 300);
		    contentPane = new JPanel();
		    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		    setContentPane(contentPane);
		    contentPane.setLayout(null);

		    JLabel lblWelcome = new JLabel();
		    lblWelcome.setBounds(120, 40, 250, 30);
		    lblWelcome.setText("歡迎 " + Session.currentMember.getName());
		    contentPane.add(lblWelcome);

		    JLabel lblPoint = new JLabel();
		    lblPoint.setBounds(120, 70, 250, 30);
		    lblPoint.setText("目前點數：" + Session.currentMember.getPointBalance());
		    contentPane.add(lblPoint);
		    
		    JButton btnPurchase = new JButton("購買點數");
		    btnPurchase.addMouseListener(new MouseAdapter() {
		    	@Override
		    	public void mouseClicked(MouseEvent e) {
		    		new PurchaseFrame().setVisible(true);
		            dispose();
		    	}
		    });
		    btnPurchase.setBounds(150, 130, 150, 30);
		    contentPane.add(btnPurchase);

		    JButton btnBook = new JButton("預約課程");
		    btnBook.addMouseListener(new MouseAdapter() {
		    	@Override
		    	public void mouseClicked(MouseEvent e) {
		    		new BookFrame().setVisible(true);
		            dispose();
		    	}
		    });
		    btnBook.setBounds(150, 170, 150, 30);
		    contentPane.add(btnBook);

		    JButton btnMyBooking = new JButton("查看我的預約");
		    btnMyBooking.addMouseListener(new MouseAdapter() {
		    	@Override
		    	public void mouseClicked(MouseEvent e) {
		    		 new MyBookingFrame().setVisible(true);
		    	        dispose();
		    	}
		    });
		    btnMyBooking.setBounds(150, 210, 150, 30);
		    contentPane.add(btnMyBooking);
		    
		    
		    JButton btnLogout = new JButton("會員登出");
		    btnLogout.setBounds(318, 235, 120, 30);
		    btnLogout.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            Session.currentMember = null;
		            new LoginFrame().setVisible(true);
		            dispose();
		        }
		        
		   
		    });
		    contentPane.add(btnLogout);
		    
		    JButton btnBook_1 = new JButton("課程介紹");
		    btnBook_1.addMouseListener(new MouseAdapter() {
		    	@Override
		    	public void mouseClicked(MouseEvent e) {
		    		
		    		new CourseIntroFrame().setVisible(true);
		    	    dispose();	
		    	}
		    });
		    btnBook_1.setBounds(314, 170, 124, 30);
		    contentPane.add(btnBook_1);

	}

}
