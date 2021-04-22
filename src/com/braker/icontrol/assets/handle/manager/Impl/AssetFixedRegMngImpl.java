package com.braker.icontrol.assets.handle.manager.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.util.DateUtil;
import com.braker.icontrol.assets.handle.manager.AssetFixedRegMng;
import com.braker.icontrol.assets.handle.model.AssetRegistration;

@Service
public class AssetFixedRegMngImpl extends BaseManagerImpl<AssetRegistration> implements AssetFixedRegMng{

	@Override
	public List<AssetRegistration> findbyfId(Integer fId) {
		Finder finder=Finder.create("FROM AssetRegistration WHERE fId="+fId);
		List<AssetRegistration> srList = super.find(finder);
		return srList;
	}

}
