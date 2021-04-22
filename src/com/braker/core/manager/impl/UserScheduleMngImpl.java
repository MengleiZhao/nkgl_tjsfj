package com.braker.core.manager.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.core.manager.UserScheduleMng;
import com.braker.core.model.UserSchedule;

@Service
@Transactional
public class UserScheduleMngImpl extends BaseManagerImpl<UserSchedule> implements UserScheduleMng{

	@Override
	public List<UserSchedule> findbyCategory(String category,String value) {
		Finder finder = Finder.create("FROM UserSchedule WHERE "+category+"='"+value+"'");
		return super.find(finder);
	}

}
