package com.pilates.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import com.pilates.dao.MemberDao;
import com.pilates.dao.MemberDaoImpl;
import com.pilates.model.Member;
import com.pilates.util.Tool;

public class MemberServiceImpl implements MemberService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MemberService service = new MemberServiceImpl();
		try {
		    service.cancelBooking(1);
		} catch (Exception e) {
		    System.out.println("取消失敗：" + e.getMessage());
		}
		
	}

	@Override
	public void purchasePackage(int memberId, int packageId) throws Exception {
		// TODO Auto-generated method stub
		 Connection conn = null;

	        try {

	            // 1️⃣ 取得連線
	            conn = Tool.getDb();

	            // 2️⃣ 關閉自動提交（開啟交易）
	            conn.setAutoCommit(false);

	            // =========================
	            // ① 查詢方案資料
	            // =========================

	            String packageSql = "SELECT total_points, valid_days FROM packages WHERE id = ?";

	            PreparedStatement ps1 = conn.prepareStatement(packageSql);
	            ps1.setInt(1, packageId);

	            ResultSet rs = ps1.executeQuery();

	            if (!rs.next()) {
	                throw new RuntimeException("找不到方案");
	            }

	            int totalPoints = rs.getInt("total_points");
	            int validDays = rs.getInt("valid_days");


	            // =========================
	            // ② 新增 member_packages
	            // =========================

	            String insertMemberPackageSql =
	                    "INSERT INTO member_packages " +
	                    "(member_id, package_id, total_points, remaining_points, expiry_date) " +
	                    "VALUES (?, ?, ?, ?, DATE_ADD(NOW(), INTERVAL ? DAY))";

	            PreparedStatement ps2 = conn.prepareStatement(insertMemberPackageSql);

	            ps2.setInt(1, memberId);
	            ps2.setInt(2, packageId);
	            ps2.setInt(3, totalPoints);
	            ps2.setInt(4, totalPoints);
	            ps2.setInt(5, validDays);

	            ps2.executeUpdate();


	            // =========================
	            // ③ 新增 point_transactions
	            // =========================

	            String insertPointSql =
	                    "INSERT INTO point_transactions " +
	                    "(member_id, points, type, reason) " +
	                    "VALUES (?, ?, 'CREDIT', '購買方案')";

	            PreparedStatement ps3 = conn.prepareStatement(insertPointSql);

	            ps3.setInt(1, memberId);
	            ps3.setInt(2, totalPoints);

	            ps3.executeUpdate();


	            // =========================
	            // ④ 更新會員點數
	            // =========================

	            String updateMemberSql =
	                    "UPDATE members SET point_balance = point_balance + ? WHERE id = ?";

	            PreparedStatement ps4 = conn.prepareStatement(updateMemberSql);

	            ps4.setInt(1, totalPoints);
	            ps4.setInt(2, memberId);

	            ps4.executeUpdate();


	            // =========================
	            // ⑤ 全部成功 → commit
	            // =========================

	            conn.commit();

	            System.out.println("✅ 購買成功");

	        } catch (Exception e) {

	            // 只要有錯誤 → rollback
	            if (conn != null) {
	                conn.rollback();
	            }

	            System.out.println("❌ 發生錯誤，已復原");
	            throw e;

	        } finally {

	            if (conn != null) {
	                conn.close();
	            }
	        }
	    
	}

	@Override
	public void bookClass(int memberId, int scheduleId) throws Exception {
		// TODO Auto-generated method stub
		 Connection conn = null;

		    try {

		        conn = Tool.getDb();
		        conn.setAutoCommit(false);

		        // ① 查 schedule
		        String scheduleSql =
		                "SELECT max_capacity, current_capacity, point_cost " +
		                "FROM class_schedule WHERE id = ?";

		        PreparedStatement ps1 = conn.prepareStatement(scheduleSql);
		        ps1.setInt(1, scheduleId);

		        ResultSet rs = ps1.executeQuery();

		        if (!rs.next()) {
		            throw new RuntimeException("找不到課程");
		        }

		        int maxCapacity = rs.getInt("max_capacity");
		        int currentCapacity = rs.getInt("current_capacity");
		        int pointCost = rs.getInt("point_cost");

		        // 防禦式設計：>=
		        if (currentCapacity >= maxCapacity) {
		            throw new RuntimeException("課程已滿");
		        }

		        System.out.println("課程檢查通過");

		     
		        
		     // =========================
		     // ② 查會員點數
		     // =========================

		     String memberSql =
		             "SELECT point_balance FROM members WHERE id = ?";

		     PreparedStatement ps2 = conn.prepareStatement(memberSql);
		     ps2.setInt(1, memberId);

		     ResultSet rs2 = ps2.executeQuery();

		     if (!rs2.next()) {
		         throw new RuntimeException("找不到會員");
		     }

		     int pointBalance = rs2.getInt("point_balance");

		     // pointCost 是第一段查 schedule 時抓到的
		     if (pointBalance < pointCost) {
		         throw new RuntimeException("點數不足");
		     }

		     System.out.println("點數檢查通過");
		  // =========================
		  // 檢查是否已預約
		  // =========================

		  String checkBookingSql =
				  			"SELECT id, status FROM bookings " +
						    "WHERE member_id = ? AND schedule_id = ?";

		  PreparedStatement psCheck = conn.prepareStatement(checkBookingSql);
		  psCheck.setInt(1, memberId);
		  psCheck.setInt(2, scheduleId);

		  ResultSet rsCheck = psCheck.executeQuery();

		  if (rsCheck.next()) {

		      int bookingId = rsCheck.getInt("id");
		      String statusCheck = rsCheck.getString("status");

		      if ("BOOKED".equals(statusCheck)) {
		          throw new RuntimeException("已經預約過此課程");
		      }

		      if ("CANCELLED".equals(statusCheck)) {

		          // 🔹 復活預約
		          String reviveSql =
		              "UPDATE bookings SET status='BOOKED', cancelled_at=NULL WHERE id=?";

		          PreparedStatement psRevive = conn.prepareStatement(reviveSql);
		          psRevive.setInt(1, bookingId);
		          psRevive.executeUpdate();

		          System.out.println("已復活預約");

		      }

		  } else {

		      // 🔹 完全沒資料 → 新增
		      String insertBookingSql =
		          "INSERT INTO bookings (member_id, schedule_id, status) " +
		          "VALUES (?, ?, 'BOOKED')";

		      PreparedStatement ps3 = conn.prepareStatement(insertBookingSql);
		      ps3.setInt(1, memberId);
		      ps3.setInt(2, scheduleId);
		      ps3.executeUpdate();

		      System.out.println("預約資料新增完成");
		      }
		  
		  
		  
		  
		  
		  
		// =========================
		// ④ 扣會員點數
		// =========================

		String updateMemberSql =
		        "UPDATE members SET point_balance = point_balance - ? WHERE id = ?";

		PreparedStatement ps4 = conn.prepareStatement(updateMemberSql);

		ps4.setInt(1, pointCost);
		ps4.setInt(2, memberId);

		ps4.executeUpdate();

		System.out.println("會員點數扣除完成");
		// =========================
		// ⑤ 新增扣點紀錄
		// =========================

		String insertPointSql =
		        "INSERT INTO point_transactions " +
		        "(member_id, points, type, reason) " +
		        "VALUES (?, ?, 'DEBIT', '預約課程')";

		PreparedStatement ps5 = conn.prepareStatement(insertPointSql);

		ps5.setInt(1, memberId);
		ps5.setInt(2, pointCost);

		ps5.executeUpdate();

		System.out.println("扣點紀錄新增完成");
		// =========================
		// ⑥ 更新課程已預約人數
		// =========================

		String updateScheduleSql =
		        "UPDATE class_schedule " +
		        "SET current_capacity = current_capacity + ? " +
		        "WHERE id = ?";

		PreparedStatement ps6 = conn.prepareStatement(updateScheduleSql);

		ps6.setInt(1, 1);
		ps6.setInt(2, scheduleId);

		ps6.executeUpdate();

		System.out.println("課程人數更新完成");
		  
		     conn.commit();
		     System.out.println("✅ 預約成功");
		    } catch (Exception e) {

		        if (conn != null) {
		            conn.rollback();
		        }

		        throw e;

		    } finally {
		        if (conn != null) {
		            conn.close();
		        }
		    }
	}

	@Override
	public void cancelBooking(int bookingId) throws Exception {
		 Connection conn = null;

		    try {
		        conn = Tool.getDb();
		        conn.setAutoCommit(false);

		        // =========================
		        // ① 查 booking + schedule
		        // =========================
		        String sql =
		            "SELECT b.member_id, b.schedule_id, b.status, " +
		            "       s.start_time, s.point_cost, s.cancel_deadline_hours " +
		            "FROM bookings b " +
		            "JOIN class_schedule s ON b.schedule_id = s.id " +
		            "WHERE b.id = ? FOR UPDATE";

		        PreparedStatement ps1 = conn.prepareStatement(sql);
		        ps1.setInt(1, bookingId);
		        ResultSet rs = ps1.executeQuery();

		        if (!rs.next()) {
		            throw new RuntimeException("找不到預約資料");
		        }

		        int memberId = rs.getInt("member_id");
		        int scheduleId = rs.getInt("schedule_id");
		        String status = rs.getString("status");
		        java.sql.Timestamp startTime = rs.getTimestamp("start_time");
		        int pointCost = rs.getInt("point_cost");
		        int cancelDeadlineHours = rs.getInt("cancel_deadline_hours");

		        if (!"BOOKED".equals(status)) {
		            throw new RuntimeException("此預約無法取消");
		        }

		        // =========================
		        // ② 判斷是否超過取消期限
		        // =========================
		        long now = System.currentTimeMillis();
		        long deadlineMillis =
		            startTime.getTime() - (cancelDeadlineHours * 60L * 60L * 1000L);

		        if (now > deadlineMillis) {
		            throw new RuntimeException("已超過取消期限，無法取消");
		        }

		        // =========================
		        // ③ 更新 booking 狀態
		        // =========================
		        String updateBooking =
		            "UPDATE bookings SET status='CANCELLED' WHERE id = ?";
		        PreparedStatement ps2 = conn.prepareStatement(updateBooking);
		        ps2.setInt(1, bookingId);
		        ps2.executeUpdate();

		        // =========================
		        // ④ 退點
		        // =========================
		        String updateMember =
		            "UPDATE members SET point_balance = point_balance + ? WHERE id = ?";
		        PreparedStatement ps3 = conn.prepareStatement(updateMember);
		        ps3.setInt(1, pointCost);
		        ps3.setInt(2, memberId);
		        ps3.executeUpdate();

		        // =========================
		        // ⑤ 新增交易紀錄
		        // =========================
		        String insertPoint =
		            "INSERT INTO point_transactions " +
		            "(member_id, points, type, reason) " +
		            "VALUES (?, ?, 'CREDIT', '取消課程')";
		        PreparedStatement ps4 = conn.prepareStatement(insertPoint);
		        ps4.setInt(1, memberId);
		        ps4.setInt(2, pointCost);
		        ps4.executeUpdate();

		        // =========================
		        // ⑥ 課程人數 -1
		        // =========================
		        String updateSchedule =
		            "UPDATE class_schedule " +
		            "SET current_capacity = current_capacity - 1 " +
		            "WHERE id = ?";
		        PreparedStatement ps5 = conn.prepareStatement(updateSchedule);
		        ps5.setInt(1, scheduleId);
		        ps5.executeUpdate();

		        conn.commit();
		        System.out.println("✅ 取消成功");

		    } catch (Exception e) {

		        if (conn != null) conn.rollback();
		        throw e;

		    } finally {

		        if (conn != null) conn.close();
		    }
	}

	@Override
	public void register(Member member) throws Exception {
		// TODO Auto-generated method stub
		 MemberDao dao = new MemberDaoImpl();

		    // 1️⃣ 檢查手機是否重複
		    if (dao.existsByPhone(member.getPhone())) {
		        throw new RuntimeException("此手機號碼已註冊");
		    }

		    // 2️⃣ 設定預設值
		    member.setPointBalance(0);
		    member.setStatus("ACTIVE");
		    member.setCreatedAt(LocalDateTime.now());

		    // 3️⃣ 新增會員
		    dao.insert(member);

		    System.out.println("✅ 註冊成功");
	}	    
}
