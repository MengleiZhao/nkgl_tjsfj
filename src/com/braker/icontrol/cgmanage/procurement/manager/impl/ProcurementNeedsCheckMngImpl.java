package com.braker.icontrol.cgmanage.procurement.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.procurement.manager.ProcurementNeedsCheckMng;
import com.braker.icontrol.cgmanage.procurement.model.ProcurementNeedsInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;


/**
 * 需求申请审批的service实现类
 * @author 方淳洲
 * @createtime 2021-03-16
 * @updatetime 2021-03-16
 */
@Service
@Transactional
public class ProcurementNeedsCheckMngImpl extends BaseManagerImpl<ProcurementNeedsInfo> implements ProcurementNeedsCheckMng {
	
	@Autowired
	public TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	public CgApplysqMng cgsqMng;
	
	@Override
	public Pagination pageList(ProcurementNeedsInfo bean, Integer page, Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM ProcurementNeedsInfo WHERE fuserId='"+user.getId()+"' AND status <> '99'");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (cgCode like '%"+searchData+"%' or cgName like '%"+searchData+"%' or cgAmount like '%"+searchData+"%' or TO_DATE(updateTime,'yyyy-mm-dd') like '%"+searchData+"%' or cgMethod like '%"+searchData+"%') ");
		}
		finder.append(" order by cgId desc");
		return super.find(finder, page, rows);
	}

	@Override
	public void saveCheckInfo(TProcessCheck checkBean, ProcurementNeedsInfo bean, User user, String spjlFile) throws Exception {
		CheckEntity entity = (CheckEntity)bean;
		String checkUrl = "/procurementNeedsCheck/check?id=";
		String lookUrl = "/procurementNeeds/detail?id=";
		//查询工作流
		bean = (ProcurementNeedsInfo)tProcessCheckMng.checkProcess(checkBean, entity, user, "CGXQ", checkUrl, lookUrl, spjlFile);
		if("9".equals(bean.getCheckStauts())){//审批通过
			PurchaseApplyBasic p = cgsqMng.findById(bean.getCgId());
			p.setNeedsStatus("9");
			p.setAuthorized(bean.getAuthorized());
			cgsqMng.updateDefault(p);
		}
		bean = (ProcurementNeedsInfo) super.saveOrUpdate(bean);
	}
	
}
