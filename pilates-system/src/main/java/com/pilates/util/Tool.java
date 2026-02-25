package com.pilates.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Tool {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    try (Connection conn = Tool.getDb()) {

            if (conn != null) {
                System.out.println("✅ 資料庫連線成功！");
            }

        } catch (Exception e) {
            System.out.println("❌ 連線失敗");
            e.printStackTrace();
        }
	}
	//連線
			public static Connection getDb()
			{
				String url="jdbc:mysql://localhost:3306/pilates";
				String user="root";
				String password="1234";
				Connection conn=null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection(url, user, password);
				} catch (ClassNotFoundException e) {
					System.out.println("no Driver");
					e.printStackTrace();
				} catch (SQLException e) {
					System.out.println("no Connection");
					e.printStackTrace();
				}
				
				return conn;
			}

}
