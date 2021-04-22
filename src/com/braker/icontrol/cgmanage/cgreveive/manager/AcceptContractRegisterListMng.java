package com.braker.icontrol.cgmanage.cgreveive.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;

/**
 * 采购验收的采购商品明细抽象类
 * @author 赵孟雷
 * @createtime 2020-06-30
 * @updatetime 2020-06-30
 */
public interface AcceptContractRegisterListMng extends BaseManager<AcceptContractRegisterList>{
	
	/**
	 * 根据fpId查询采购验收中的采购清单明细
	 * @param fpId
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月30日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月30日
	 */
	public List<AcceptContractRegisterList> findFpIdbyMingxi(String id);
}
