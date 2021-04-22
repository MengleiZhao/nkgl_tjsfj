package com.braker.icontrol.expend.apply.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.expend.apply.manager.ApplyCheckMng;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;

/**
 * 事前申请审批的service实现类
 * @author 叶崇晖
 * @createtime 2018-06-29
 * @updatetime 2018-06-29
 */
@Service
@Transactional
public class ApplyCheckMngImpl extends BaseManagerImpl<ApplicationBasicInfo> implements ApplyCheckMng{	
	
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProExpendDetailMng tProExpendDetailMng;
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	
	/*
	 * 保存审批信息
	 * @author 叶崇晖
	 * @createtime 2018-07-05
	 * @updatetime 2018-07-05
	 */
	@Override
	public void saveCheckInfo(TProcessCheck checkBean, ApplicationBasicInfo bean,List<Object> mingxi, User user ,Role role,String files)  throws Exception{
		bean=this.findById(bean.getgId());
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/applyCheck/check?id=";
		String lookUrl ="/apply/edit?id=";
		//转换type选择流程
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		if("GWCX".equals(bean.getTravelType())){
			strType = "GWCXSQ";
		}
		if("1".equals(String.valueOf(bean.getType()))){
			if("FLFSQ".equals(bean.getCommonType())){
				strType = "TYSXFLSQ";
			}
			if("SPPSSQ".equals(bean.getCommonType())){
				strType = "TYSXSPPSSQ";
			}
		}
		bean=(ApplicationBasicInfo)tProcessCheckMng.checkProcess(checkBean,entity,user,strType,checkUrl,lookUrl,files);
		if("-1".equals(bean.getFlowStauts())){//审批不通过的时候，归还冻结金额
			bean.setReimbType("0");//设置报销状态为0（未报销）
			//审批不通过的时候，归还冻结金额
			budgetIndexMgrMng.deleteDjAmount(bean.getIndexId(),bean.getProDetailId(),bean.getAmount());
		}else if("9".equals(bean.getFlowStauts())){//审批结束
			tProcessPrintDetailMng.arrangeCheckDetail(bean, null, "0", user);
		}
		super.saveOrUpdate(bean);
		
	}
}
