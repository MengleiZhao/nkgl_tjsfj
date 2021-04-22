package com.braker.icontrol.assets.returns.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;
import com.braker.workflow.entity.TProcessCheck;

public interface AssetReturnMng extends BaseManager<AssetReturn> {

	/**
	 * 获取交回申请列表
	 * @param assetReturn
	 * @param user
	 * @param page
	 * @param rows
	 * @return
	 * @author wanping
	 * @createtime 2020年7月20日
	 * @updator wanping
	 * @updatetime 2020年7月20日
	 */
	Pagination getAssetReturnList(AssetReturn assetReturn, User user, Integer page, Integer rows);

	/**
	 * 交回申请保存
	 * @param assetReturn
	 * @param assetReturnList
	 * @param user
	 * @author wanping
	 * @createtime 2020年7月23日
	 * @updator wanping
	 * @updatetime 2020年7月23日
	 */
	void save(AssetReturn assetReturn, List<AssetReturnList> assetReturnList, User user) throws Exception;

	/**
	 * 删除
	 * @param id
	 * @param user
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	void delete(String id, User user);

	/**
	 * 撤回
	 * @param id
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	void reCall(String id);

	/**
	 * 根据code查询
	 * @param code
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月24日
	 * @updator 陈睿超
	 * @updatetime 2020年7月24日
	 */
	AssetReturn findByCode(String code);

	/**
	 * 固定资产审批
	 * @param user
	 * @param assetReturn
	 * @param checkBean
	 * @param spjlFile
	 * @author wanping
	 * @createtime 2020年7月27日
	 * @updator wanping
	 * @updatetime 2020年7月27日
	 */
	void approve(User user, AssetReturn assetReturn, TProcessCheck checkBean, String spjlFile,String getreturnList) throws Exception;

	/**
	 * 固定资产交回登记
	 * @param id
	 * @param user
	 * @author wanping
	 * @createtime 2020年7月28日
	 * @updator wanping
	 * @updatetime 2020年7月28日
	 */
	void accept(String id, User user);
	
	/**
	 * 查询交回待审批数据
	 * @param assetReturn
	 * @param user
	 * @param page
	 * @param rows
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年8月29日
	 * @updator 陈睿超
	 * @updatetime 2020年8月29日
	 */
	Pagination getApprovalAssetReturn(AssetReturn assetReturn, User user, Integer page, Integer rows);
	
	/**
	 * 交回受理列表数据
	 * @param assetReturn
	 * @param user
	 * @param page
	 * @param rows
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年8月29日
	 * @updator 陈睿超
	 * @updatetime 2020年8月29日
	 */
	Pagination getacceptAssetReturn(AssetReturn assetReturn, User user, Integer page, Integer rows);
	
}
