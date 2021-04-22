package com.braker.icontrol.purchase.apply.manager.Impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.model.Lookups;
import com.braker.icontrol.assets.storage.model.AssetStock;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.purchase.apply.manager.PurchaseApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;

@Service
@Transactional
public class PurchaseApplyMngImpl extends BaseManagerImpl<PurchaseApplyBasic> implements PurchaseApplyMng{

	@Override
	public Pagination queryList(PurchaseApplyBasic purchaseApplyBasic, Integer pageNo, Integer pageSize, String sign) {
		Finder finder =Finder.create(" FROM PurchaseApplyBasic WHERE fCheckStauts=9");
		if(!StringUtil.isEmpty(purchaseApplyBasic.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+purchaseApplyBasic.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(purchaseApplyBasic.getFpName())){ //按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+purchaseApplyBasic.getFpName()+"%");
		}
		if(!StringUtil.isEmpty(sign)){	//采购验收状态 tab标签页		sign： 0- (0暂存，1待验收，-1已退回)		1- 9已验收	2-审批页面
			if("1".equals(sign)){
				finder.append(" AND fIsReceive = :fIsReceive");
			}else {
				finder.append(" AND fIsReceive <> :fIsReceive");
			}
			purchaseApplyBasic.setfIsReceive("9");
			finder.setParam("fIsReceive", purchaseApplyBasic.getfIsReceive());
		}
		//待审批列表页
		if("awaitCheck".equals(purchaseApplyBasic.getfIsReceive())){	
			finder.append(" AND fIsReceive = 1");
		}
		if(!StringUtil.isEmpty(purchaseApplyBasic.getFevalStauts())){//评价状态
			finder.append(" AND fevalStauts = :fevalStauts");
			finder.setParam("fevalStauts", purchaseApplyBasic.getFevalStauts());
		}
		finder.append(" order by fReqTime desc");
		return super.find(finder,pageNo,pageSize);
	}

	@Override
	public List<PurchaseApplyBasic> find1Condition(String condition1, String val1) {
		Finder finder =Finder.create(" from PurchaseApplyBasic where "+condition1+"='"+val1+"'");
		return super.find(finder);
	}

	@Override
	public Boolean checkContract(String fpId) {
		Finder finder =Finder.create(" from ContractBasicInfo where fFlowStauts=9 and fPurchNo='"+fpId+"'");
		List<ContractBasicInfo> list = super.find(finder);
		return list.size()>0?true:false;
	}
	
}
