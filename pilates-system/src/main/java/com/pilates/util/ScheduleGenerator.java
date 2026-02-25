package com.pilates.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleGenerator {
	public static void main(String[] args) {
	    //ScheduleGenerator.generateMarch2026();
	}
    public static void generateMarch2026() {

    	String sql =
                "INSERT INTO class_schedule " +
                "(template_id, coach_id, start_time, end_time, " +
                "max_capacity, current_capacity, point_cost, " +
                "cancel_deadline_hours, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

            try (Connection conn = Tool.getDb();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                conn.setAutoCommit(false);

                LocalDate startDate = LocalDate.of(2026, 3, 1);
                LocalDate endDate = LocalDate.of(2026, 3, 31);

                while (!startDate.isAfter(endDate)) {

                    insert(ps, startDate, 10, 12, 1, 1);
                    insert(ps, startDate, 15, 17, 2, 2);
                    insert(ps, startDate, 20, 22, 3, 3);

                    startDate = startDate.plusDays(1);
                }

                conn.commit();
                System.out.println("✅ 3月課程產生完成");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void insert(PreparedStatement ps,
                                   LocalDate date,
                                   int startHour,
                                   int endHour,
                                   int templateId,
                                   int coachId) throws Exception {

            LocalDateTime start = date.atTime(startHour, 0);
            LocalDateTime end = date.atTime(endHour, 0);

            ps.setInt(1, templateId);
            ps.setInt(2, coachId);
            ps.setObject(3, start);
            ps.setObject(4, end);
            ps.setInt(5, 12);
            ps.setInt(6, 0);
            ps.setInt(7, 20);
            ps.setInt(8, 24);

            ps.executeUpdate();
        }
    }