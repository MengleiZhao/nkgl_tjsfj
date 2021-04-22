package com.braker.icontrol.assets.export.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.core.model.User;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessPrintDetail;

/**
 * 采购打印
 * @author 赵孟雷
 * @createtime 2020-08-10
 * @updatetime 2020-08-10
 */
public interface ExportHandleMng extends BaseManager<Handle>{

	

	
	/**
	 * 组装审签数据（资产处置中的审签数据）
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
	public List<TProcessPrintDetail> arrangeCheckDetailHandle(Handle bean,String type,User user) throws Exception ;
	
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
	public TProcessPrintDetail getJBBMFZR(List<TProcessCheck> checkList,String type,Handle bean);
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
	public TProcessPrintDetail getJBCSFGJZ(List<TProcessCheck> checkList,String type,Handle bean);
	
	/**
	 * 获取局长审签信息
	 * @param checkList 审批记录
	 * @return 局长审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月12日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月12日
	 */
	public TProcessPrintDetail getJZ(List<TProcessCheck> checkList);
	/**
	 * 获取党建部门处室负责人审签信息
	 * @param checkList 审批记录
	 * @return 党建部门处室负责人审签信息
	 * @author 赵孟雷
	 * @createtime 2020年8月12日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月12日
	 */
	public TProcessPrintDetail getDJBMFZR(List<TProcessCheck> checkList);
	
	
}
