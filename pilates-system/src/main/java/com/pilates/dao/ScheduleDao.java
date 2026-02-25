package com.pilates.dao;

import java.util.List;
import com.pilates.model.ClassSchedule;
public interface ScheduleDao {
	
	List<ClassSchedule> findAvailable(int memberId) throws Exception;
}
