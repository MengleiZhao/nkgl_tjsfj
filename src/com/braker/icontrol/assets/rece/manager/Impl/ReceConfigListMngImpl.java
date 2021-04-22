package com.braker.icontrol.assets.rece.manager.Impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Condition;
import com.braker.common.hibernate.Finder;
import com.braker.common.hibernate.OrderBy;
import com.braker.common.hibernate.Updater;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.AssetTypeMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.model.AssetType;
import com.braker.core.model.Lookups;
import com.braker.icontrol.assets.rece.manager.ReceConfigListMng;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;

@Service
@Transactional
public class ReceConfigListMngImpl extends BaseManagerImpl<ReceConfigList> implements ReceConfigListMng {

	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private AssetTypeMng assetTypeMng;
	
	
	@Override
	public Pagination findbyrece_CL(String assetid,Rece rece, Integer pageNo, Integer pageSize) {
		
		Finder finder = Finder.create("FROM ReceConfigList WHERE rece_CL.fId_R = "+rece.getfId_R());
		Pagination p = super.find(finder, pageNo, pageSize);
		List<ReceConfigList> li = (List<ReceConfigList>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			ReceConfigList receConfig = li.get(i);
			AssetType assetType = assetTypeMng.findbyCode(receConfig.getFfixedType_CL());
			li.get(i).setFfixedType_show(assetType.getName());
		}
		p.setList(li);
		return p;
	}

	@Override
	public List<ReceConfigList> getbyAssetid(String assetid) {
		
		List<ReceConfigList> receConfigList = new ArrayList<ReceConfigList>();
		String str = new String();
		assetid = assetid.substring(1, assetid.length()-1);
		if(!StringUtil.isEmpty(assetid)){
			String[] arrid = assetid.split(",");
			for (int i = 0; i < arrid.length; i++) {
				str = str + arrid[i].substring(1, arrid[i].length()-1)+",";
			}
			str = str.substring(0, str.length()-1);
		}
		Finder finder = Finder.create("FROM AssetBasicInfo WHERE fAssId in("+str+")");
		List<AssetBasicInfo> abiList = super.find(finder);
		for (int i = 0; i < abiList.size(); i++) {
			AssetBasicInfo abi = abiList.get(i);
			ReceConfigList receConfig = new ReceConfigList();
			receConfig.setfAssCode_CL(abi.getfAssCode());
			receConfig.setfAssId(abi.getfAssId());
			receConfig.setfAssName_CL(abi.getfAssName());
			receConfig.setfMode_CL(abi.getfSPModel());
			receConfig.setfFinancialDate_CL(abi.getfFinancialDate());
			AssetType assetType = assetTypeMng.findbyCode(abi.getfFixedTypeCode());
			receConfig.setFfixedType_CL(assetType.getCode());
			receConfig.setFfixedType_show(assetType.getName());
			receConfigList.add(receConfig);
		}
		return receConfigList;
	}

	@Override
	public List<ReceConfigList> findbyReceid(Integer receid) {
		Finder finder = Finder.create("FROM ReceConfigList WHERE rece_CL ="+receid);
		return super.find(finder);
	}


	
	
}
