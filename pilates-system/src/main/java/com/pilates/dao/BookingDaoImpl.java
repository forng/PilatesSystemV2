package com.pilates.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pilates.model.Booking;
import com.pilates.model.BookingView;
import com.pilates.util.Tool;

public class BookingDaoImpl implements BookingDao { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Booking> findByMemberId(int memberId) throws Exception {
		// TODO Auto-generated method stub
		List<Booking> list = new ArrayList<>();

        String sql = 
        		"SELECT * FROM bookings " +
                "WHERE member_id = ? " +
                "AND status = 'BOOKED'";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Booking b = new Booking(
                            rs.getInt("id"),
                            rs.getInt("member_id"),
                            rs.getInt("schedule_id"),
                            rs.getString("status"),
                            rs.getTimestamp("booked_at").toLocalDateTime()
                    );

                    list.add(b);
                }
            }
        }

        return list;
	}

	@Override
	public List<BookingView> findViewByMemberId(int memberId) throws Exception {
		// TODO Auto-generated method stub
		List<BookingView> list = new ArrayList<>();

	    String sql =
	    				"SELECT b.id AS booking_id, " +
	    			    "ct.name AS template_name, " +
	    			    "c.name AS coach_name, " +
	    			    "cs.start_time, " +
	    			    "cs.point_cost, " +
	    			    "b.booked_at " +
	    			    "FROM bookings b " +
	    			    "JOIN class_schedule cs ON b.schedule_id = cs.id " +
	    			    "JOIN class_templates ct ON cs.template_id = ct.id " +
	    			    "JOIN coaches c ON cs.coach_id = c.id " +
	    			    "WHERE b.member_id = ? AND b.status = 'BOOKED' " +
	    			    "ORDER BY cs.start_time";

	    try (Connection conn = Tool.getDb();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, memberId);

	        try (ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {

	                BookingView bv = new BookingView();

	                bv.setTemplateName(rs.getString("template_name"));
	                bv.setCoachName(rs.getString("coach_name"));
	                bv.setStartTime(
	                    rs.getTimestamp("start_time").toLocalDateTime()
	                );
	                bv.setPointCost(rs.getInt("point_cost"));
	                bv.setBookingTime(
	                	    rs.getTimestamp("booked_at").toLocalDateTime()
	                	);
	                bv.setBookingId(rs.getInt("booking_id"));

	                list.add(bv);
	            }
	        }
	    }

	    return list;
	}

}
