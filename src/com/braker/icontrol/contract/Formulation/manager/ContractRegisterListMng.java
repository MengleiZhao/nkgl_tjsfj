package com.braker.icontrol.contract.Formulation.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;

public interface ContractRegisterListMng extends BaseManager<ContractRegisterList> {

	/**
	 * 获取采购清单
	 * @param contractRegisterList
	 * @return
	 * @author wanping
	 * @createtime 2020年7月8日
	 * @updator wanping
	 * @updatetime 2020年7月8日
	 */
	List<ContractRegisterList> getContractRegisterList(ContractRegisterList contractRegisterList);
	
	
}
