package com.braker.icontrol.cgmanage.cgconfplan.mananger;



import com.braker.common.hibernate.BaseManager;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.workflow.entity.TProcessCheck;

/**
 * 配置计划审批的service抽象类
 * @author 冉德茂
 * @createtime 2018-10-17
 * @updatetime 2018-10-17
 */
public interface CgConfPlanCheckMng extends BaseManager<ProcurementPlan>{

	
	
	/*
	 * 保存审核信息
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	public void saveCheckInfo(TProcessCheck checkBean, ProcurementPlan bean, User user,String files) throws Exception ;
	
}
