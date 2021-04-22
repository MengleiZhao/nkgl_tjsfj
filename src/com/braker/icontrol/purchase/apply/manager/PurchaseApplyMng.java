package com.braker.icontrol.purchase.apply.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.icontrol.assets.storage.model.AssetStock;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;

public interface PurchaseApplyMng extends BaseManager<PurchaseApplyBasic> {

	/**
	 * 查询已审批的采购订单（合同拟定用）
	 * @param PurchaseApplyBasic
	 * @return
	 * @author 陈睿超
	 * @createtime 2018-07-10
	 */
	Pagination queryList(PurchaseApplyBasic PurchaseApplyBasic, Integer pageNo, Integer pageSize, String sign);
	
	/**
	 * 根据2个条件去查询
	 * @param condition1 条件1
	 * @param val1  条件1的值
	 * @return
	 */
	List<PurchaseApplyBasic> find1Condition(String condition1,String val1);

	/**
	 * true是已经有合同了，false没有拟定通过的合同
	 * 查询采购单是否有已经拟定通过的合同
	 * @param fpId
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年3月18日
	 * @updator 陈睿超
	 * @updatetime 2020年3月18日
	 */
	Boolean checkContract(String fpId);

}
