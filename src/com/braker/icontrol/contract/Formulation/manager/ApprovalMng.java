package com.braker.icontrol.contract.Formulation.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.SealInfo;
import com.braker.workflow.entity.TProcessCheck;

public interface ApprovalMng extends BaseManager<ContractBasicInfo>{

	/**
	 * 显示审批主页数据
	 * @param contractBasicInfo
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param searchData 
	 * @return
	 */
	Pagination queryList(ContractBasicInfo contractBasicInfo,User user, int pageNo, int pageSize, String searchData);
	
	/**
	 * 变更合同状态
	 * @param id
	 * @param checkBean
	 * @param files
	 * @param user
	 */
	void updatefFlowStauts(ContractBasicInfo cbiBean, TProcessCheck checkBean, String files, User user) throws Exception;
	
}
