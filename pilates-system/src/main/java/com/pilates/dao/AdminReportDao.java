package com.pilates.dao;


	import java.util.List;
	import com.pilates.model.RankingView;

	public interface AdminReportDao {

	    // ===== 本月總覽 =====
	    int getMonthlyBookingCount() throws Exception;

	    int getMonthlyCancelCount() throws Exception;

	    int getMonthlyCreditPoints() throws Exception;

	    int getMonthlyDebitPoints() throws Exception;
	    
	    int getQuarterRevenue() throws Exception;

	    int getQuarterSoldPoints() throws Exception;

	    // ===== 前5排行 =====
	    List<RankingView> getTop5CoachRanking() throws Exception;

	    List<RankingView> getTop5CustomerPurchaseRanking() throws Exception;

	    List<RankingView> getTop5CustomerAttendanceRanking() throws Exception;
	}