package com.braker.core.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.AssetTypeMng;
import com.braker.core.model.AssetType;
import com.braker.core.model.Category;
import com.braker.core.model.User;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class AssetTypeMngImpl extends BaseManagerImpl<AssetType> implements AssetTypeMng {

	@Override
	public List<AssetType> list(String parentId,String id,String leve) {
		Finder finder = Finder.create(" FROM AssetType WHERE 1=1 ");
		if(!StringUtil.isEmpty(parentId)){//按品目名称查询
			finder.append(" AND parentId =:parentId");
			finder.setParam("parentId", parentId);
		}
		if(!StringUtil.isEmpty(id)){//按验收编号模糊查询
			finder.append(" AND id =:id");
			finder.setParam("id", id);
		}
		if(!StringUtil.isEmpty(leve)){//按采购编号模糊查询
			finder.append(" AND leve =:leve");
			finder.setParam("leve", leve);
		}
		finder.append(" order by updateTime desc");
		List<AssetType> assetTypes = super.find(finder);
		return assetTypes;
	}

	@Override
	public void save(AssetType assetType, User user) {
		
	}

	@Override
	public void assetType_delete(String id) {
		
	}
	
	@Override
	public List<AssetType> getRoots(String type,Integer level,String fAssClass) {
		Finder f=Finder.create("from AssetType Where flag='1' and assetType='"+type+"'");
		if(!StringUtil.isEmpty(fAssClass)){
			String code = returnfAssClass(fAssClass);
			f.append(" and code='"+code+"'");
		}
		if(level==null||0==level){
			f.append(" and parentId is null");
		}else {
			f.append(" and leve ="+level);
		}
		return super.find(f);
	}

	@Override
	public List<AssetType> getAppRoots(String type) {
		Finder f=Finder.create("from AssetType Where flag='1' and parentId is null and assetType='"+type+"' and code in ('2010000','2020000','5010401','1020000','1030000') ");
		return super.find(f);
	}
	
	@Override
	public List<AssetType> getChild(String pid ) {
		String hql = "from AssetType where flag='1' and parentId = ? ";
		return find(hql, pid);
	}

	@Override
	public Pagination findFixedTypeList(AssetType assetType, Integer pageNo,
			Integer pageSize) {
		Finder f=Finder.create("from AssetType Where flag='1' ");
		if(!StringUtil.isEmpty(assetType.getParentId())){
			f.append("and parentId =:parentId").setParam("parentId", assetType.getParentId());
		}
		if(!StringUtil.isEmpty(assetType.getName())){
			f.append("and name like:name").setParam("name", "'%"+assetType.getName()+"%'");
		}
		if(!StringUtil.isEmpty(assetType.getAssetType())){
			f.append("and assetType =:assetType").setParam("assetType", assetType.getAssetType());
		}
		return super.find(f, pageNo, pageSize);
	}

	@Override
	public AssetType findbyCode(String code) {
		Finder finder = Finder.create(" FROM AssetType WHERE code='"+code+"'");
		return (AssetType) super.find(finder).get(0);
	}

	/*
	 * 用于返回数据库资产的基本大类code
	 */
	private String returnfAssClass(String fAssClass){
		switch (fAssClass) {
		case "GDZCLB-01":
			return "1010000";
		case "GDZCLB-02":
			return "1020000";
		case "GDZCLB-03":
			return "2000000";
		case "GDZCLB-04":
			return "3000000";
		case "GDZCLB-05":
			return "4000000";
		case "GDZCLB-06":
			return "5000000";
		case "GDZCLB-07":
			return "6000000";
		case "GDZCLB-08":
			return "1030000";
		case "WXZCLB-01":
			return "6050100";
		case "WXZCLB-02":
			return "6050200";
		case "WXZCLB-03":
			return "6050300";
		case "WXZCLB-04":
			return "6050400";
		case "WXZCLB-05":
			return "6050500";
		case "WXZCLB-06":
			return "6050600";
		case "WXZCLB-07":
			return "6050700";
		}
		return null;
	}
	
}
