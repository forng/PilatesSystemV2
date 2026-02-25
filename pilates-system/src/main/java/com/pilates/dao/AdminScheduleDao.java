package com.pilates.dao;

import java.util.List;
import com.pilates.model.AdminScheduleView;

public interface AdminScheduleDao {

    List<AdminScheduleView> findAllSchedules() throws Exception;

}