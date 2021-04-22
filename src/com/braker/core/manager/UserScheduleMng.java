package com.braker.core.manager;

import java.util.List;

import org.jsoup.Connection.Base;

import com.braker.common.hibernate.BaseManager;
import com.braker.core.model.UserSchedule;


public interface UserScheduleMng extends BaseManager<UserSchedule> {

	
	public List<UserSchedule> findbyCategory(String category,String value);
	
}
