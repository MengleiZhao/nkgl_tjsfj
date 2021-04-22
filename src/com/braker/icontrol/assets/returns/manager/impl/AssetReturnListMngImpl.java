package com.braker.icontrol.assets.returns.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.assets.returns.manager.AssetReturnListMng;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;

@Service
@Transactional
public class AssetReturnListMngImpl extends BaseManagerImpl<AssetReturnList> implements AssetReturnListMng {

	@Override
	public void save(AssetReturn assetReturn, List<AssetReturnList> assetReturnList) {
		getSession().createSQLQuery("DELETE FROM T_ASSET_RETURN_LIST WHERE F_ID='"+assetReturn.getfId_A()+"'").executeUpdate();
		for (int i = 0; i < assetReturnList.size(); i++) {
			assetReturnList.get(i).setAssetReturn(assetReturn);
			assetReturnList.get(i).setfReturnListStauts("1");
			super.merge(assetReturnList.get(i));
		}
	}

	@Override
	public List<AssetReturnList> findByCondition(AssetReturn assetReturn) {
		Finder finder=Finder.create(" FROM AssetReturnList WHERE fReturnListStauts='1'");
		if (assetReturn.getfId_A() != null) {
			finder.append(" AND assetReturn.fId_A =:fId_A");
			finder.setParam("fId_A", assetReturn.getfId_A());
		}
		List<AssetReturnList> list = super.find(finder);
		return list;
	}

	@Override
	public void batchDelete(String ids, User user) {
		String[] id = ids.split(",");
		for (String fListId : id) {
			AssetReturnList assetReturnList = findById(Integer.valueOf(fListId));
			assetReturnList.setUpdateTime(new Date());
			assetReturnList.setUpdator(user.getAccountNo());
			assetReturnList.setfReturnListStauts("99");
			super.saveOrUpdate(assetReturnList);
		}
	}

}
