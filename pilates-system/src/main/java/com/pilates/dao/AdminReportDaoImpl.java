package com.pilates.dao;

import java.sql.*;
import java.util.*;
import com.pilates.model.RankingView;
import com.pilates.util.Tool;

public class AdminReportDaoImpl implements AdminReportDao {

    // ===== 本月預約總人次 =====
    @Override
    public int getMonthlyBookingCount() throws Exception {

        String sql =
            "SELECT COUNT(*) FROM bookings b " +
            "JOIN class_schedule cs ON b.schedule_id = cs.id " +
            "WHERE b.status = 'BOOKED' " +
            "AND MONTH(cs.start_time) BETWEEN 1 AND 3 " +
            "AND YEAR(cs.start_time) = YEAR(CURRENT_DATE())";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    // ===== 本月取消次數 =====
    @Override
    public int getMonthlyCancelCount() throws Exception {

        String sql =
            "SELECT COUNT(*) FROM bookings b " +
            "JOIN class_schedule cs ON b.schedule_id = cs.id " +
            "WHERE b.status = 'CANCELLED' " +
            "AND MONTH(cs.start_time) BETWEEN 1 AND 3 " +
            "AND YEAR(cs.start_time) = YEAR(CURRENT_DATE())";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    // ===== 本月銷售點數 =====
    @Override
    public int getMonthlyCreditPoints() throws Exception {

        String sql =
            "SELECT IFNULL(SUM(points),0) FROM point_transactions pt " +
            "WHERE type = 'CREDIT' " +
            "AND MONTH(created_at) BETWEEN 1 AND 3 " +
            "AND YEAR(created_at) = YEAR(CURRENT_DATE())";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    // ===== 本月消耗點數 =====
    @Override
    public int getMonthlyDebitPoints() throws Exception {

        String sql =
            "SELECT IFNULL(SUM(points),0) FROM point_transactions pt " +
            "WHERE type = 'DEBIT' " +
            "AND MONTH(pt.created_at) BETWEEN 1 AND 3 " +
            "AND YEAR(pt.created_at) = YEAR(CURRENT_DATE())";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    // ===== 教練排行 Top5 =====
    @Override
    public List<RankingView> getTop5CoachRanking() throws Exception {

        List<RankingView> list = new ArrayList<>();

        String sql =
            "SELECT c.name, COUNT(*) AS total " +
            "FROM bookings b " +
            "JOIN class_schedule cs ON b.schedule_id = cs.id " +
            "JOIN coaches c ON cs.coach_id = c.id " +
            "WHERE b.status = 'BOOKED' " +
            "AND MONTH(cs.start_time) BETWEEN 1 AND 3 " +
            "AND YEAR(cs.start_time) = YEAR(CURRENT_DATE()) " +
            "GROUP BY c.name " +
            "ORDER BY total DESC LIMIT 5";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new RankingView(
                        rs.getString("name"),
                        rs.getInt("total")
                ));
            }
        }

        return list;
    }

    // ===== 客戶消費排行 Top5 =====
    @Override
    public List<RankingView> getTop5CustomerPurchaseRanking() throws Exception {

        List<RankingView> list = new ArrayList<>();

        String sql =
            "SELECT m.name, SUM(pt.points) AS total " +
            "FROM point_transactions pt " +
            "JOIN members m ON pt.member_id = m.id " +
            "WHERE pt.type = 'CREDIT' " +
            "AND MONTH(pt.created_at) BETWEEN 1 AND 3 " +
            "AND YEAR(pt.created_at) = YEAR(CURRENT_DATE()) " +
            "GROUP BY m.name " +
            "ORDER BY total DESC LIMIT 5";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new RankingView(
                        rs.getString("name"),
                        rs.getInt("total")
                ));
            }
        }

        return list;
    }

    // ===== 客戶上課排行 Top5 =====
    @Override
    public List<RankingView> getTop5CustomerAttendanceRanking() throws Exception {

        List<RankingView> list = new ArrayList<>();

        String sql =
            "SELECT m.name, COUNT(*) AS total " +
            "FROM bookings b " +
            "JOIN members m ON b.member_id = m.id " +
            "JOIN class_schedule cs ON b.schedule_id = cs.id " +
            "WHERE b.status = 'BOOKED' " +
            "AND MONTH(cs.start_time) BETWEEN 1 AND 3 " +
            "AND YEAR(cs.start_time) = YEAR(CURRENT_DATE()) " +
            "GROUP BY m.name " +
            "ORDER BY total DESC LIMIT 5";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new RankingView(
                        rs.getString("name"),
                        rs.getInt("total")
                ));
            }
        }

        return list;
    }

	@Override
	public int getQuarterRevenue() throws Exception {
		// TODO Auto-generated method stub
		String sql =
		        "SELECT IFNULL(SUM(p.price),0) " +
		        "FROM member_packages mp " +
		        "JOIN packages p ON mp.package_id = p.id " +
		        "WHERE MONTH(mp.purchase_date) BETWEEN 1 AND 3 " +
		        "AND YEAR(mp.purchase_date) = YEAR(CURRENT_DATE())";

		    try (Connection conn = Tool.getDb();
		         PreparedStatement ps = conn.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {

		        rs.next();
		        return rs.getInt(1);
		    }
	}

	@Override
	public int getQuarterSoldPoints() throws Exception {
		// TODO Auto-generated method stub
		 String sql =
			        "SELECT IFNULL(SUM(total_points),0) " +
			        "FROM member_packages mp " +
			        "WHERE MONTH(mp.purchase_date) BETWEEN 1 AND 3 " +
			        "AND YEAR(mp.purchase_date) = YEAR(CURRENT_DATE())";

			    try (Connection conn = Tool.getDb();
			         PreparedStatement ps = conn.prepareStatement(sql);
			         ResultSet rs = ps.executeQuery()) {

			        rs.next();
			        return rs.getInt(1);
			    }
	}
}