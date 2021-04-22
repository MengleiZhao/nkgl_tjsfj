package com.braker.icontrol.cgmanage.cgconfplan.mananger.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanListMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;

@Service
@Transactional
public class CgConPlanListMngImpl extends BaseManagerImpl<ProcurementPlanList> implements CgConPlanListMng{

	@Override
	public List<ProcurementPlanList> findby(String name, String val) {
		Finder finder=Finder.create(" FROM ProcurementPlanList WHERE "+name+"="+val);
		return super.find(finder);
	}

}
