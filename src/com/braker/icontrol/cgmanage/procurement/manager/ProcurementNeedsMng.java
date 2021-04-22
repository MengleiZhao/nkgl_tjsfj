package com.braker.icontrol.cgmanage.procurement.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.procurement.model.ProcurementNeedsInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;


/**
 * 需求申请的service抽象类
 * @author 方淳洲
 * @createtime 2021-03-13
 * @updatetime 2021-03-13
 */
public interface ProcurementNeedsMng extends BaseManager<ProcurementNeedsInfo>{

	Pagination pageList(PurchaseApplyBasic bean, Integer page, Integer rows,
			User user, String searchData);
	
	List<ProcurementNeedsInfo> findByCgId(Integer id);

	void save(ProcurementNeedsInfo bean, String xqsfiles, String wtsfiles,
			User user) throws Exception;

	String reCall(Integer id);

	void delete(Integer id, String fId, User user);

	List<ProcurementNeedsInfo> findByCondition(String currentDate);

}
