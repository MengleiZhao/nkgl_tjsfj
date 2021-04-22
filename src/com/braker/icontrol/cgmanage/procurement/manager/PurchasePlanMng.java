package com.braker.icontrol.cgmanage.procurement.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.procurement.model.PurchasePlanBasic;
import com.braker.workflow.entity.TProcessCheck;

/**
 * 采购计划Service
 * 
 * @author wanping
 *
 */
public interface PurchasePlanMng extends BaseManager<PurchasePlanBasic> {

	/**
	 * 政采计划申请保存
	 * @param bean
	 * @param user
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	void save(PurchasePlanBasic bean, User user) throws Exception;

	/**
	 * 政采计划申请撤回
	 * @param id
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	void reCall(Integer id);

	/**
	 * 政采计划审批分页数据
	 * @param bean
	 * @param page
	 * @param rows
	 * @param user
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	Pagination checkPageList(PurchasePlanBasic bean, Integer page, Integer rows, User user, String searchData);

	/**
	 * 政采计划审批
	 * @param checkBean
	 * @param bean
	 * @param user
	 * @param spjlFile
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	void check(TProcessCheck checkBean, PurchasePlanBasic bean, User user, String spjlFile) throws Exception;
	
}
