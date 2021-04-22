package com.braker.icontrol.expend.reimburse.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.expend.reimburse.manager.ReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;


/**
 * 报销申请收款人的service实现类
 * @author 叶崇晖
 * @createtime 2018-08-10
 * @updatetime 2018-08-10
 */
@Service
@Transactional
public class ReimbPayeeMngImpl extends BaseManagerImpl<ReimbPayeeInfo> implements ReimbPayeeMng {

	/*
	 * 根据rId查询收款人信息
	 * @author 叶崇晖
	 * @createtime 2018-08-10
	 * @updatetime 2018-08-10
	 */
	@Override
	public List<ReimbPayeeInfo> getByRId(Integer rId,String fInnerOrOuter) {
		Finder finder = Finder.create(" FROM ReimbPayeeInfo WHERE rId="+rId);
		if(!StringUtil.isEmpty(fInnerOrOuter)){
			finder.append(" and fInnerOrOuter = '"+fInnerOrOuter+"'");
		}
		return super.find(finder);
	}
	
	@Override
	public List<ReimbPayeeInfo> getByDrId(Integer drId,String fInnerOrOuter) {
		Finder finder = Finder.create(" FROM ReimbPayeeInfo WHERE drId="+drId);
		if(!StringUtil.isEmpty(fInnerOrOuter)){
			finder.append(" and fInnerOrOuter = '"+fInnerOrOuter+"'");
		}
		return super.find(finder);
	}

	@Override
	public List<Lookups> getpayeelookupsJson(String categoryCode,
			String blanked, String selected,String fInnerOrOuter) {
		List<Lookups> list = new ArrayList<Lookups>();
		selected = StringUtil.isEmpty(selected)?null:selected;
		Finder finder = Finder.create(" FROM ReimbPayeeInfo WHERE fInnerOrOuter='"+fInnerOrOuter+"' and payeeName is not null GROUP BY payeeName,bank,bankAccount");
		List<ReimbPayeeInfo> brlist = super.find(finder);
		for (int i = 0; i < brlist.size(); i++) {
			Lookups lookups = new Lookups();
			ReimbPayeeInfo reimbPayeeInfo = brlist.get(i);
			lookups.setCode(String.valueOf(reimbPayeeInfo.getpId()));
			lookups.setName(reimbPayeeInfo.getPayeeName());
			list.add(lookups);
		}
		return list;
	}
	
	
	/*
	 * 分页查询
	 * @author 赵孟雷
	 * @createtime 2021-01-22
	 * @updatetime 2021-01-22
	 */
	@Override
	public Pagination pageList(PaymentMethodInfo bean,int pageNo, int pageSize, User user) {
		Finder finder = Finder.create(" FROM PaymentMethodInfo WHERE 1=1");
		
		if (!StringUtil.isEmpty(String.valueOf(bean.getBankAccount()))) {//开户银行
			finder.append(" AND bankAccount LIKE '%"+bean.getBankAccount()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getPayeeName()))) {//收款人
			finder.append(" AND payeeName LIKE '%"+bean.getPayeeName()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getBank()))) {//银行账号
			finder.append(" AND bank LIKE '%"+bean.getBank()+"%'");
		}
		finder.append(" order by pId desc ");
		return super.find(finder, pageNo, pageSize);
	}
}