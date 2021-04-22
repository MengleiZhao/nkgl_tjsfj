package com.braker.icontrol.incomemanage.disbursement.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.icontrol.incomemanage.disbursement.model.ScheduleProList;

public interface ScheduleProListManager extends BaseManager<ScheduleProList> {
	
	/**
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	public List<ScheduleProList> getScheduleProList(String sId);
	
}
