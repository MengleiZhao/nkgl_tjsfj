package com.braker.icontrol.cgmanage.cgexport.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessPrintDetail;

/**
 * 采购打印
 * @author 冉德茂
 * @createtime 2020-08-10
 * @updatetime 2020-08-10
 */
public interface ExportCgMng extends BaseManager<AcceptCheck>{

	
	
	
	/**
	 * 获取经办部门负责人审签信息
	 * @param user 当前用户
	 * @param checkList 审批记录
	 * @return 经办部门负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public TProcessPrintDetail getJBBMFZR(List<TProcessCheck> checkList,String type,AcceptCheck bean,Integer fpWId);
	/**
	 * 获取经办部门分管局长审签信息
	 * @param user 当前用户
	 * @param checkList 审批记录
	 * @return 经办部门负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public TProcessPrintDetail getJBCSFGJZ(List<TProcessCheck> checkList,String type,AcceptCheck bean,Integer fpWId);
	
	
	
	/**
	 * 组装审签数据（采购验收中服务类和工程类中的审签数据）
	 * @param checkList
	 * @param bean
	 * @param rbean
	 * @param user
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public List<TProcessPrintDetail> arrangeCheckDetailReceive(AcceptCheck bean,String type,User user) throws Exception ;
	/**
	 * 组装审签数据（采购申请审签数据）
	 * @param checkList
	 * @param bean
	 * @param rbean
	 * @param user
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年8月28日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月28日
	 */
	public List<TProcessPrintDetail> arrangeCheckDetailApply(PurchaseApplyBasic bean,String type,User user) throws Exception ;
	
	/**
	 * 获取经办部门负责人审签信息采购申请
	 * @param user 当前用户
	 * @param checkList 审批记录
	 * @return 经办部门负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public TProcessPrintDetail getJBBMFZRapply(List<TProcessCheck> checkList,String type,PurchaseApplyBasic bean,Integer fpWId);
	/**
	 * 获取经办部门负责人审签信息采购申请
	 * @param user 当前用户
	 * @param checkList 审批记录
	 * @return 经办部门负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public TProcessPrintDetail getCGGLGapply(List<TProcessCheck> checkList,String type,PurchaseApplyBasic bean,Integer fpWId);
	/**
	 * 获取办公室负责人审签信息采购申请
	 * @param user 当前用户
	 * @param checkList 审批记录
	 * @return 经办部门负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public TProcessPrintDetail getBGSFZRapply(List<TProcessCheck> checkList,String type,PurchaseApplyBasic bean,Integer fpWId);
	/**
	 * 获取经办部门分管局长审签信息采购申请
	 * @param user 当前用户
	 * @param checkList 审批记录
	 * @return 经办部门负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public TProcessPrintDetail getJBCSFGJZapply(List<TProcessCheck> checkList,String type,PurchaseApplyBasic bean,Integer fpWId);
	/**
	 * 获取经办部门分管局长审签信息采购申请
	 * @param user 当前用户
	 * @param checkList 审批记录
	 * @return 经办部门负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月10日
	 */
	public TProcessPrintDetail getCWJZapply(List<TProcessCheck> checkList,String type,PurchaseApplyBasic bean,Integer fpWId);
	
}
