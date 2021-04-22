package com.braker.icontrol.contract.Formulation.manager.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;

@Service
@Transactional
public class ContractRegisterListMngImpl extends BaseManagerImpl<ContractRegisterList> implements ContractRegisterListMng {

	@Override
	public List<ContractRegisterList> getContractRegisterList(ContractRegisterList contractRegisterList) {
		Finder finder =Finder.create(" FROM ContractRegisterList WHERE 1=1");
		if (contractRegisterList.getfDataType() != null) {
			finder.append(" AND fDataType=:fDataType");
			finder.setParam("fDataType", contractRegisterList.getfDataType());
		}
		if (contractRegisterList.getFcId() != null) {
			finder.append(" AND fcId=:fcId");
			finder.setParam("fcId", contractRegisterList.getFcId());
		}
		if (contractRegisterList.getfId_U() != null) {
			finder.append(" AND fId_U=:fId_U");
			finder.setParam("fId_U", contractRegisterList.getfId_U());
		}
		return super.find(finder);
	}

}
