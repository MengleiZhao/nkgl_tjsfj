package com.braker.icontrol.cgmanage.procurement.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.procurement.model.ProcurementNeedsInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;



/**
 * 需求申请审批的service抽象类
 * @author 方淳洲
 * @createtime 2021-03-16
 * @updatetime 2021-03-16
 */
public interface ProcurementNeedsCheckMng extends BaseManager<ProcurementNeedsInfo>{

	Pagination pageList(ProcurementNeedsInfo bean, Integer page, Integer rows,
			User user, String searchData);

	void saveCheckInfo(TProcessCheck checkBean, ProcurementNeedsInfo bean,
			User user, String spjlFile) throws Exception;

}
