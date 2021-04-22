package com.braker.icontrol.cgmanage.cgcheck.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;

public interface PurcMaterialRegisterListMng extends BaseManager<PurcMaterialRegisterList>{

	/**
	 * 根据code查询中标商的采购清单明细
	 * @param tableName
	 * @param idName
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月23日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月23日
	 */
	public List<Object> getMingxi(String tableName ,String idName ,String code);
	/**
	 * 根据fpId查询中标商的采购清单明细
	 * @param tableName
	 * @param idName
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月23日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月23日
	 */
	public List<PurcMaterialRegisterList> findFpIdbyMingxi(String fpId);
}
