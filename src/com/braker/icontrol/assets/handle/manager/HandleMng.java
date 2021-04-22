package com.braker.icontrol.assets.handle.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Lookups;
import com.braker.core.model.User;
import com.braker.icontrol.assets.handle.model.AssetRegistration;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.workflow.entity.TProcessCheck;

public interface HandleMng extends BaseManager<Handle>{

	/**
	 * 加载资产处置申请的List页面数据
	 * @param handle 搜索条件
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination applicationList(Handle handle,User user,Integer pageNo, Integer pageSize);
	/**
	 * 加载资产处置申请(无形资产处置补录单)的List页面数据
	 * @param handle 搜索条件
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination replenishList(Handle handle,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 查询字典里相应数据
	 * @param categoryCode
	 * @param blanked
	 * @return
	 */
	List<Lookups> getLookupsJson(String categoryCode,String blanked);
	
	/**
	 * 保存修改物资处置申请表
	 * @param handle
	 * @param user
	 */
	void save(String  planJson,Handle handle,User user,String LowHandleFlies) throws Exception;
	
	/**
	 * 加载资产处置审批的List页面数据
	 * @param handle 搜索条件
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination approvalList(Handle handle,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 更改处置单审批状态和添加一条审批记录
	 * @param stauts
	 * @param handle
	 * @param ACI
	 * @param user
	 */
	void updateStauts(String stauts,Handle handle,TProcessCheck checkBean,User user,String files,String files01,String files02,String files03,String files04,String planJson) throws Exception;
	
	/**
	 * 选择处置单的数据
	 * @param handle
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination handleRegList(Handle handle,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 删除连同工作台一并删除
	 * @param handle
	 * @param user
	 */
	void delete(String id,User user);
	
	/**
	 * 根据处置单编号来查
	 * @param code
	 * @return
	 */
	Handle findbyCode(String code);
	
	/**
	 * 加载处置清单数据
	 * @param fId
	 * @param fAssType
	 * @return
	 */
	 List<AssetRegistration> inAndFixedHandle(String fId , String fAssType);
	
	/**
	 * 查询所有处置清单（带查询，不带部门/人）
	 * @param handle
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination ledgerPagination(Handle handle,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 撤回表单，修改数据
	 * @param id
	 */
	String reCall(String id);
	
	
	/**
	 * 根据Assetid查询
	 * @param assetid 数据组类型数据
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年7月22日
	 * @updator 赵孟雷
	 * @updatetime 2020年7月22日
	 */
	List<AssetRegistration> getbyAssetid(String assetid);
	
	
	/**
	 * 处置受理，修改数据
	 * @param id
	 */
	String updateDisposeAccept(String id);
	
	/**
	 * 加载资产处置受理的List页面数据
	 * @param handle 搜索条件
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination disposeAcceptJson(Handle handle,User user,Integer pageNo, Integer pageSize);
}
