package com.braker.icontrol.expend.loan.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.expend.loan.model.Payment;
import com.braker.icontrol.expend.loan.model.RepaymentHistoryRecords;
import com.braker.workflow.entity.TProcessCheck;

public interface RepaymentHistoryRecordsMng extends BaseManager<RepaymentHistoryRecords> {

	//分页
	public Pagination pageList(Integer lId);

}
