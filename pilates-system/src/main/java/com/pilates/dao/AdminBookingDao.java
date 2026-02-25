package com.pilates.dao;
import java.util.List;
import com.pilates.model.AdminBookingView;
public interface AdminBookingDao {

    List<AdminBookingView> findAllBookings() throws Exception;
}
