package com.braker.icontrol.contract.Formulation.manager.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.contract.Formulation.manager.ApprovalMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.SealInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;

@Service
@Transactional
public class ApprovalMngImpl extends BaseManagerImpl<ContractBasicInfo> implements ApprovalMng{
	@Autowired
	private TBasicItfMng basicItfMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private TProItfMng proItfMng; 
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private CgApplysqMng cgApplysqMng;
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	
	@Override
	public Pagination queryList(ContractBasicInfo contractBasicInfo,User user, int pageNo, int pageSize,String searchData) {
		Finder finder=Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts =1 AND fContStauts='1' ");
		finder.append("AND fUserCode=:fUserCode ");
		finder.setParam("fUserCode", user.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append("AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfOperator())){
			finder.append("AND fOperator LIKE :fOperator ");
			finder.setParam("fOperator", "%"+contractBasicInfo.getfOperator()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcAmount())){
			finder.append("AND fcAmount LIKE :fcAmount");
			finder.setParam("fcAmount", "%"+contractBasicInfo.getFcAmount()+"%");
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fcCode like '%"+searchData+"%' or  fcTitle like '%"+searchData+"%' or fcAmount like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or to_date(fReqtIME, 'yyyy-mm-dd') like '%"+searchData+"%')  ");
		}
		/*if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getcAmountBegin()))){
			finder.append(" AND fcAmount >='"+contractBasicInfo.getcAmountBegin()+"'");
		}
		if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getcAmountEnd()))){
			finder.append(" AND fcAmount <='"+contractBasicInfo.getcAmountEnd()+"'");
		}*/
		/*if(contractBasicInfo.getfReqtIME()!=null){
			finder.append("AND datediff(fReqtIME,'"+contractBasicInfo.getfReqtIME()+"')=0");
		}*/
		finder.append("order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void updatefFlowStauts(ContractBasicInfo cbiBean, TProcessCheck checkBean, String files, User user)  throws Exception{
		CheckEntity entity = (CheckEntity)cbiBean;
		String checkUrl = "/Approval/approve?id=";
		String lookUrl = "/Formulation/detail?id=";
		cbiBean = (ContractBasicInfo) tProcessCheckMng.checkProcess(checkBean, entity, user, "HTND", checkUrl, lookUrl, files);
		
		//??????????????????
		if("9".equals(cbiBean.getfFlowStauts())){
			//??????????????????????????????
			//cbiBean.setfContStauts("9");
			//????????????????????????????????????
			cbiBean.setFsealedStatus("0");
			cbiBean.setfApproveTime(new Date());
			tProcessPrintDetailMng.arrangeContractDetail(cbiBean);
			/*if("HTFL-01".equals(cbiBean.getFcType())){//?????????????????????
				//?????????????????????????????????????????????
				PurchaseApplyBasic pabBean = cgApplysqMng.findById(Integer.valueOf(cbiBean.getfPurchNo()));
				//??????????????????????????????
				Double djAmount = budgetIndexMgrMng.deleteDjAmount(cbiBean.getfBudgetIndexCode(), cbiBean.getProDetailId(), pabBean.getAmount());
				//?????????????????????????????????
				Double syAmount = budgetIndexMgrMng.addDjAmount(cbiBean.getfBudgetIndexCode(), cbiBean.getProDetailId(), pabBean.getFbidAmount());
			}*/
		}
		//??????????????????????????????????????????
		if("-1".equals(cbiBean.getfFlowStauts())&&"HTFL-01".equals(cbiBean.getFcType())){
			//????????????
			PurchaseApplyBasic cgbean = cgsqMng.findById(Integer.valueOf(cbiBean.getfPurchNo()));
			cgbean.setIsUsed("0");
			//?????????
			BiddingRegist gysbean = cgProcessMng.findbycode(cbiBean.getfContractor(), null);
			gysbean.setfContractstatus("0");
		}
		/*//???????????????????????????????????????????????????????????????????????????????????????????????????????????????
		if("-1".equals(bean.getfFlowStauts()) && bean.getFcType().equals("HTFL-01")){
			//?????????????????????????????????????????????
			Double amount = Double.parseDouble(bean.getFcAmount());//????????????
			Double syAmount = budgetIndexMgrMng.deleteDjAmount(bean.getfBudgetIndexCode(),bean.getProDetailId(),amount);
			bean.setfAvailableAmount(syAmount+"");
		}*/
		super.saveOrUpdate(cbiBean);
	}

}
