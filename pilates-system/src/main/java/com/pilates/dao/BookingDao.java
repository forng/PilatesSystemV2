package com.pilates.dao;

import java.util.List;

import com.pilates.model.Booking;
import com.pilates.model.BookingView;

public interface BookingDao {
	 // 原本方法（資料表用）
    List<Booking> findByMemberId(int memberId) throws Exception;

    // 新增方法（畫面用）
    List<BookingView> findViewByMemberId(int memberId) throws Exception;
}
