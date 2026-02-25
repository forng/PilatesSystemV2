package com.pilates.dao;

import java.sql.*;
import java.util.*;
import com.pilates.model.AdminBookingView;
import com.pilates.util.Tool;

public class AdminBookingDaoImpl implements AdminBookingDao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AdminBookingView> findAllBookings() throws Exception {
		// TODO Auto-generated method stub
		  List<AdminBookingView> list = new ArrayList<>();

	        String sql =
	            "SELECT m.name AS member_name, " +
	            "ct.name AS template_name, " +
	            "c.name AS coach_name, " +
	            "cs.start_time, " +
	            "b.booked_at, " +
	            "b.status " +
	            "FROM bookings b " +
	            "JOIN members m ON b.member_id = m.id " +
	            "JOIN class_schedule cs ON b.schedule_id = cs.id " +
	            "JOIN class_templates ct ON cs.template_id = ct.id " +
	            "JOIN coaches c ON cs.coach_id = c.id " +
	            "ORDER BY cs.start_time";

	        try (Connection conn = Tool.getDb();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {

	                AdminBookingView v = new AdminBookingView();

	                v.setMemberName(rs.getString("member_name"));
	                v.setTemplateName(rs.getString("template_name"));
	                v.setCoachName(rs.getString("coach_name"));
	                v.setStartTime(
	                    rs.getTimestamp("start_time").toLocalDateTime()
	                );
	                v.setBookingTime(
	                    rs.getTimestamp("booked_at").toLocalDateTime()
	                );
	                v.setStatus(rs.getString("status"));

	                list.add(v);
	            }
	        }

	        return list;
	}

}
