package com.braker.icontrol.cgmanage.cgcheck.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.icontrol.cgmanage.cgcheck.manager.PurcMaterialRegisterListMng;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;

@Service
@Transactional
public class PurcMaterialRegisterListMngImpl extends BaseManagerImpl<PurcMaterialRegisterList> implements PurcMaterialRegisterListMng{

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
	public List<Object> getMingxi(String tableName ,String idName ,String code){
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "='"+code+"'");
		return super.find(finder);
	}

	@Override
	public List<PurcMaterialRegisterList> findFpIdbyMingxi(String fpId) {
		Finder finder = Finder.create(" FROM PurcMaterialRegisterList WHERE fpId ='"+fpId+"'");
		return super.find(finder);
	}
}
