package com.braker.icontrol.cgmanage.cgpay.manager.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgpay.manager.CgPayCheckMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;

/**
 * 付款申请审批的的service实现类
 * @author 冉德茂
 * @createtime 2018-08-16
 * @updatetime 2018-08-16
 */
@Service
@Transactional
public class CgPayCheckMngImpl extends BaseManagerImpl<PurchaseApplyBasic> implements CgPayCheckMng {
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	/**
	 * 分頁查詢
	 * @author 冉德茂
	 * @createtime 2018-08-16
	 * @updatetime 2018-08-16
	 */
	@Override
	public Pagination pageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user) {		
		//区分   采购审批和付款审批
		Finder finder =Finder.create(" FROM PurchaseApplyBasic WHERE  fUser='"+user.getId()+"' AND fDeptId='"+user.getDpID()+"'  AND fStauts <>"+99+" AND fpayStauts <>"+0+"  ");
		if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){ //按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		if(!StringUtil.isEmpty(bean.getfCheckStauts()) ){//审批状态
			if("2".equals(bean.getfCheckStauts())){//审核中
				finder.append(" AND fCheckStauts >0 and fCheckStauts <9");
			}else{
				finder.append(" AND fCheckStauts = :fCheckStauts");
				finder.setParam("fCheckStauts", bean.getfCheckStauts());
			}
		}
		if(!StringUtil.isEmpty(bean.getfDeptName())){ //按申报部门模糊查询
			finder.append(" AND fDeptName LIKE :fDeptName");
			finder.setParam("fDeptName", "%"+bean.getfDeptName()+"%");
		}
		finder.append(" order by fpId desc ");
		return super.find(finder,pageNo,pageSize);
	}
	
	
	
	/*
	 * 保存审核信息
	 * @author 冉德茂
	 * @createtime 2018-08-17
	 * @updatetime 2018-08-17
	 */
	@Override
	public void saveCheckInfo(TProcessCheck checkBean, PurchaseApplyBasic bean, User user ,Role role ,String files)  throws Exception{
		bean=this.findById(bean.getFpId());
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/cgpayforcheck/check?id=";
		String lookUrl ="/cgpayfor/checkdetail?id=";
		bean=(PurchaseApplyBasic)tProcessCheckMng.checkProcess(checkBean,entity,user,"CGPAY",checkUrl,lookUrl,files);
		super.saveOrUpdate(bean);
	}

	/*
	 * 财务审定分页查询
	 * @author 叶崇晖
	 * @createtime 2018-08-18
	 * @updatetime 2018-08-18
	 */
	@Override
	public Pagination auditPageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user) {
		//区分   采购审批和付款审批
		Finder finder =Finder.create(" FROM PurchaseApplyBasic WHERE fuserId='"+user.getId()+"' AND fCheckStauts=9");
		if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		return super.find(finder,pageNo,pageSize);
	}

	/*
	 * 出纳受理分页查询
	 * @author 叶崇晖
	 * @createtime 2018-08-22
	 * @updatetime 2018-08-22
	 */
	@Override
	public Pagination cashierPageList(PurchaseApplyBasic bean, int pageNo,
			int pageSize, User user) {
		Finder finder =Finder.create(" FROM PurchaseApplyBasic WHERE fCheckStauts=9 AND fuserId='"+user.getId()+"'");
		if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		return super.find(finder,pageNo,pageSize);
	}
	
}
