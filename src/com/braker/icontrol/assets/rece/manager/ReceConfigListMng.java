package com.braker.icontrol.assets.rece.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceConfigList;

public interface ReceConfigListMng extends BaseManager<ReceConfigList>{

	
	Pagination findbyrece_CL(String assetid,Rece rece,Integer pageNo, Integer pageSize);
	
	/**
	 * 根据Assetid查询
	 * @param assetid 数据组类型数据
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月18日
	 * @updator 陈睿超
	 * @updatetime 2020年7月18日
	 */
	List<ReceConfigList> getbyAssetid(String assetid);
	
	/**
	 * 根据receid主键去查询
	 * @param receid
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月18日
	 * @updator 陈睿超
	 * @updatetime 2020年7月18日
	 */
	List<ReceConfigList> findbyReceid(Integer receid);
}
