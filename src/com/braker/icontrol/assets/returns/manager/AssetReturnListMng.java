package com.braker.icontrol.assets.returns.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.core.model.User;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;

public interface AssetReturnListMng extends BaseManager<AssetReturnList> {

	/**
	 * 保存交回清单
	 * @param assetReturn
	 * @param assetReturnList
	 * @author wanping
	 * @createtime 2020年7月23日
	 * @updator wanping
	 * @updatetime 2020年7月23日
	 */
	void save(AssetReturn assetReturn, List<AssetReturnList> assetReturnList);

	/**
	 * 根据条件查询交回清单列表
	 * @param assetReturn
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	List<AssetReturnList> findByCondition(AssetReturn assetReturn);

	/**
	 * 批量删除
	 * @param ids
	 * @param user
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	void batchDelete(String ids, User user);

}
