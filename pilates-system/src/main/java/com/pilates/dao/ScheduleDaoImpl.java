package com.pilates.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pilates.model.ClassSchedule;
import com.pilates.util.Tool;

public class ScheduleDaoImpl implements ScheduleDao{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ClassSchedule> findAvailable(int memberId) throws Exception {
		// TODO Auto-generated method stub
		 List<ClassSchedule> list = new ArrayList<>();

	        String sql = 
	        		 			"SELECT cs.id, " +
	        				    "ct.name AS template_name, " +
	        				    "c.name AS coach_name, " +
	        				    "cs.start_time, cs.end_time, " +
	        				    "cs.max_capacity, cs.current_capacity, " +
	        				    "cs.point_cost, " +
	        				    "CASE WHEN b.id IS NULL THEN 0 ELSE 1 END AS is_booked " +
	        				    "FROM class_schedule cs " +
	        				    "JOIN class_templates ct ON cs.template_id = ct.id " +
	        				    "JOIN coaches c ON cs.coach_id = c.id " +
	        				    "LEFT JOIN bookings b ON b.schedule_id = cs.id " +
	        				    "AND b.member_id = ? " +
	        				    "AND b.status = 'BOOKED' " +
	        				    "WHERE cs.start_time > NOW() " +
	        				    "ORDER BY cs.start_time";

	    

	        try (Connection conn = Tool.getDb();
	        	     PreparedStatement ps = conn.prepareStatement(sql)) {

	        	    // 👇 在這裡設定參數
	        	    ps.setInt(1, memberId);

	        	    try (ResultSet rs = ps.executeQuery()) {

	        	        while (rs.next()) {

	        	            ClassSchedule cs = new ClassSchedule();
	        	            cs.setId(rs.getInt("id"));
	        	            cs.setTemplateName(rs.getString("template_name"));
	        	            cs.setCoachName(rs.getString("coach_name"));
	        	            cs.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
	        	            cs.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
	        	            cs.setMaxCapacity(rs.getInt("max_capacity"));
	        	            cs.setCurrentCapacity(rs.getInt("current_capacity"));
	        	            cs.setPointCost(rs.getInt("point_cost"));

	        	            // 👇 加這行（剛剛新增的欄位）
	        	            boolean isBooked = rs.getInt("is_booked") == 1;
	        	            cs.setBooked(isBooked);

	        	            list.add(cs);
	        	        }
	        	    }
	        	}

	        	return list;
	}

}
