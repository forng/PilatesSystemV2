package com.pilates.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pilates.model.AdminScheduleView;
import com.pilates.util.Tool;

public class AdminScheduleDaoImpl implements AdminScheduleDao {

    @Override
    public List<AdminScheduleView> findAllSchedules() throws Exception {

        List<AdminScheduleView> list = new ArrayList<>();

        String sql =
                "SELECT ct.name AS template_name, " +
                "c.name AS coach_name, " +
                "cs.start_time, " +
                "cs.current_capacity, " +
                "cs.max_capacity, " +
                "CASE WHEN cs.current_capacity >= cs.max_capacity " +
                "THEN '已滿' ELSE '可預約' END AS status " +
                "FROM class_schedule cs " +
                "JOIN class_templates ct ON cs.template_id = ct.id " +
                "JOIN coaches c ON cs.coach_id = c.id " +
                "ORDER BY cs.start_time";

        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                AdminScheduleView view = new AdminScheduleView();

                view.setTemplateName(rs.getString("template_name"));
                view.setCoachName(rs.getString("coach_name"));
                view.setStartTime(
                        rs.getTimestamp("start_time").toLocalDateTime()
                );
                view.setCurrentCapacity(rs.getInt("current_capacity"));
                view.setMaxCapacity(rs.getInt("max_capacity"));
                view.setStatus(rs.getString("status"));

                list.add(view);
            }
        }

        return list;
    }
}