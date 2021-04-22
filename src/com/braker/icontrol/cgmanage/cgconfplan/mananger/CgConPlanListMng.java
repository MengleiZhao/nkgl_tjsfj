package com.braker.icontrol.cgmanage.cgconfplan.mananger;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;

public interface CgConPlanListMng extends BaseManager<ProcurementPlanList>{

	/**
	 * 根据条件查询
	 * @param name 条件名称
	 * @param val 条件的值
	 * @return
	 */
	List<ProcurementPlanList> findby(String name,String val);
}
