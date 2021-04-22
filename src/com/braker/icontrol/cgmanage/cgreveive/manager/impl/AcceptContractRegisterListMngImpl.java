package com.braker.icontrol.cgmanage.cgreveive.manager.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.icontrol.cgmanage.cgreveive.manager.AcceptContractRegisterListMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;


/**
 * 采购验收的采购商品明细抽象类
 * @author 赵孟雷
 * @createtime 2020-06-30
 * @updatetime 2020-06-30
 */
@Service
@Transactional
public class AcceptContractRegisterListMngImpl extends BaseManagerImpl<AcceptContractRegisterList> implements AcceptContractRegisterListMng {

	@Override
	public List<AcceptContractRegisterList> findFpIdbyMingxi(String id) {
		Finder finder = Finder.create(" FROM AcceptContractRegisterList WHERE facpId ='"+id+"'");
		return super.find(finder);
	}

}
