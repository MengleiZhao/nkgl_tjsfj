package com.braker.icontrol.cgmanage.cgconfplan.mananger.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConfPlanCheckMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;


/**
 * 配置计划审批的service实现类
 * @author 冉德茂
 * @createtime 2018-10-17
 * @updatetime 2018-10-17
 */
@Service
@Transactional
public class CgConfPlanCheckMngImpl extends BaseManagerImpl<ProcurementPlan> implements CgConfPlanCheckMng {
	@Autowired
	private TBasicItfMng basicItfMng;
	
	@Autowired
	private TProItfMng proItfMng; 
	
	@Autowired
	private PersonalWorkMng personalWorkMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	

	/*
	 * 保存审核信息
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@Override
	public void saveCheckInfo(TProcessCheck checkBean, ProcurementPlan bean, User user,String files)  throws Exception {
		bean=this.findById(bean.getFplId());
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/cgconfplancheck/check?id=";
		String lookUrl ="/cgconfplangl/detail?id=";
		bean=(ProcurementPlan)tProcessCheckMng.checkProcess(checkBean,entity,user,"CGPLANSQ",checkUrl,lookUrl,files);
		super.saveOrUpdate(bean);
	
	}
	
	
}

	
	
	


