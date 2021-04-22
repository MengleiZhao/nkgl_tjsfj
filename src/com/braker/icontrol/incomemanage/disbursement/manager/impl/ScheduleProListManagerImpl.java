package com.braker.icontrol.incomemanage.disbursement.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.icontrol.incomemanage.disbursement.manager.ScheduleProListManager;
import com.braker.icontrol.incomemanage.disbursement.model.ScheduleProList;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class ScheduleProListManagerImpl extends BaseManagerImpl<ScheduleProList> implements ScheduleProListManager {
	
	@Autowired
	private DepartMng departMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private AttachmentMng attachmentMng;
	
	
	/**
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@Override
	public List<ScheduleProList> getScheduleProList(String sId) {
		//查询条件
		Finder finder = Finder.create(" FROM ScheduleProList WHERE sId='"+sId+"'");
		//设置其他属性
		List<ScheduleProList> li =super.find(finder);
		for(int x=0; x<li.size(); x++) {
			//序号设置
			li.get(x).setPageOrder(x+1);
		}
		return li;
	}
}