package com.pilates.service;

import com.pilates.model.Member;

public interface MemberService {
	 // 會員購買方案
    void purchasePackage(int memberId, int packageId) throws Exception;
    //訂課
    void bookClass(int memberId, int scheduleId) throws Exception;
    //取消訂課
    void cancelBooking(int bookingId) throws Exception;
    
    void register(Member member) throws Exception;
}
