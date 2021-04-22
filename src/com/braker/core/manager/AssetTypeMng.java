package com.braker.core.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.AssetType;
import com.braker.core.model.User;

public interface AssetTypeMng extends BaseManager<AssetType>{

	/**
	 * 显示主页数据
	 * @param assetType
	 * @return
	 */
	List<AssetType> list(String parentId,String id,String leve);
	
	/**
	 * 保存
	 * @param assetType
	 * @param user
	 */
	void save(AssetType assetType,User user);
	
	/**
	 * 删除
	 * @param fbId
	 */
	void assetType_delete(String id);
	
	/**
	 * 查询资产一级类型
	 * @param type
	 * @param fAssClass 字典表里的资产类别
	 * @param fAssClass 级别
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年7月16日
	 * @updator 陈睿超
	 * @updatetime 2020年11月02日
	 */
	public List<AssetType> getRoots(String type,Integer level,String fAssClass);
	
	/**
	 * 查询资产子类型
	 * @param pid
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年7月16日
	 * @updator 赵孟雷
	 * @updatetime 2020年7月16日
	 */
	public List<AssetType> getChild(String pid);
	
	/**
	 * 查询固定资产类别
	 * @param assetType
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月16日
	 * @updator 陈睿超
	 * @updatetime 2020年7月16日
	 */
	Pagination findFixedTypeList(AssetType assetType,Integer pageNo, Integer pageSize);
	
	/**
	 * 根据code查询
	 * @param code
	 * @return AssetType
	 * @author 陈睿超
	 * @createtime 2020年7月17日
	 * @updator 陈睿超
	 * @updatetime 2020年7月17日
	 */
	AssetType findbyCode(String code);

	List<AssetType> getAppRoots(String type);
	
}
