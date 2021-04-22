package com.braker.icontrol.cgmanage.cgexport.manager.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgexport.manager.ExportCgMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;


@Service
@Transactional
public class ExportCgMngImpl extends BaseManagerImpl<AcceptCheck> implements ExportCgMng {
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	
	
	@Override
	public List<TProcessPrintDetail> arrangeCheckDetailReceive(
			AcceptCheck bean, String type, User user) throws Exception {
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HWCGYS", bean.getfDepartId());
		Integer fpWId = null;
		//查询工作流
		List<TProcessCheck> listTProcessChecks = new ArrayList<TProcessCheck>();
		List<TProcessCheck> listTProcessCheck = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),bean.getBeanCode());
		for (TProcessCheck tProcessCheck : listTProcessCheck) {
			if("0".equals(tProcessCheck.getFcheckResult())){
				break;
			}else{
				User user2 = userMng.findById(tProcessCheck.getFuserId());
				tProcessCheck.setCheckDeptName(user2.getDepart().getName());
				DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
				String time =fmt.format(tProcessCheck.getFcheckTime());     // 转换成 X年X月X日
				tProcessCheck.setFcheckTimes(time);
				listTProcessChecks.add(tProcessCheck);
			}
		}
		Collections.reverse(listTProcessChecks); // 倒序排列 
		TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(listTProcessChecks, type, bean, fpWId);
		TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(listTProcessChecks, type, bean, fpWId);
		detailList.add(tProcessPrintDetail0);
		detailList.add(tProcessPrintDetail3);
		
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setFpWdDd(fpWId);
			detailList.get(i).setFpWId(null);
			detailList.get(i).setFTabName("AcceptCheck");
			detailList.get(i).setFTabId(bean.getFacpId());
			detailList.get(i).setFTabIdName("facpId");
			super.merge(detailList.get(i));
		}
		return detailList;
	}


	@Override
	public TProcessPrintDetail getJBBMFZR(List<TProcessCheck> checkList,
			String type, AcceptCheck bean, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User manageruser = new User();
		User userJBCSFGJZ = new User();
		userJBCSFGJZ = userMng.findById(bean.getUserId());
		manageruser = userJBCSFGJZ.getDepart().getManager();
		
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getfDepartId());
		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
		
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userBM.getName());
				tProcessPrintDetail.setFuserId(userBM.getId());
				tProcessPrintDetail.setFdeptId(userBM.getDpID());
				tProcessPrintDetail.setFdeptName(userBM.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("经办处室处室负责人");
		return tProcessPrintDetail;
	}


	@Override
	public TProcessPrintDetail getJBCSFGJZ(List<TProcessCheck> checkList,
			String type, AcceptCheck bean, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userJBCSFGJZ = new User();
		userJBCSFGJZ = userMng.findById(bean.getUserId());
		User manageruser = new User();
		manageruser = userJBCSFGJZ.getDepart().getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(manageruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(manageruser.getName());
				tProcessPrintDetail.setFuserId(manageruser.getId());
				tProcessPrintDetail.setFdeptId(manageruser.getDpID());
				tProcessPrintDetail.setFdeptName(manageruser.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("经办处室分管局长");
		return tProcessPrintDetail;
	}
	
	

	@Override
	public List<TProcessPrintDetail> arrangeCheckDetailApply(
			PurchaseApplyBasic bean, String type, User user) throws Exception {
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
		Integer fpWId = null;
		//查询工作流
		List<TProcessCheck> listTProcessChecks = new ArrayList<TProcessCheck>();
		List<TProcessCheck> listTProcessCheck = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),bean.getBeanCode());
		for (TProcessCheck tProcessCheck : listTProcessCheck) {
			if("0".equals(tProcessCheck.getFcheckResult())){
				break;
			}else{
				User user2 = userMng.findById(tProcessCheck.getFuserId());
				tProcessCheck.setCheckDeptName(user2.getDepart().getName());
				DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
				String time =fmt.format(tProcessCheck.getFcheckTime());     // 转换成 X年X月X日
				tProcessCheck.setFcheckTimes(time);
				listTProcessChecks.add(tProcessCheck);
			}
		}
		Collections.reverse(listTProcessChecks); // 倒序排列 
		TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZRapply(listTProcessChecks, type, bean, fpWId);
		TProcessPrintDetail tProcessPrintDetail1 = getCGGLGapply(listTProcessChecks, type, bean, fpWId);
		TProcessPrintDetail tProcessPrintDetail2 = getBGSFZRapply(listTProcessChecks, type, bean, fpWId);
		TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZapply(listTProcessChecks, type, bean, fpWId);
		TProcessPrintDetail tProcessPrintDetail4 = getCWJZapply(listTProcessChecks, type, bean, fpWId);
		detailList.add(tProcessPrintDetail0);
		detailList.add(tProcessPrintDetail1);
		detailList.add(tProcessPrintDetail2);
		detailList.add(tProcessPrintDetail3);
		detailList.add(tProcessPrintDetail4);
		
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setFpWdDd(fpWId);
			detailList.get(i).setFpWId(null);
			detailList.get(i).setFTabName("PurchaseApplyBasic");
			detailList.get(i).setFTabId(bean.getFpId());
			detailList.get(i).setFTabIdName("fpId");
			super.merge(detailList.get(i));
		}
		return detailList;
	}
	
	@Override
	public TProcessPrintDetail getJBBMFZRapply(List<TProcessCheck> checkList,
			String type, PurchaseApplyBasic bean, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getfDeptId());
		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
		
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userBM.getName());
				tProcessPrintDetail.setFuserId(userBM.getId());
				tProcessPrintDetail.setFdeptId(userBM.getDpID());
				tProcessPrintDetail.setFdeptName(userBM.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("经办处室处室负责人");
		return tProcessPrintDetail;
	}
	
	@Override
	public TProcessPrintDetail getCGGLGapply(List<TProcessCheck> checkList,
			String type, PurchaseApplyBasic bean, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userBGSFZR = userMng.getUserByRoleNameAndDepartName("采购管理岗", "办公室");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userBGSFZR.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userBGSFZR.getName());
				tProcessPrintDetail.setFuserId(userBGSFZR.getId());
				tProcessPrintDetail.setFdeptId(userBGSFZR.getDpID());
				tProcessPrintDetail.setFdeptName(userBGSFZR.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("采购管理岗");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}


	@Override
	public TProcessPrintDetail getJBCSFGJZapply(List<TProcessCheck> checkList,
			String type, PurchaseApplyBasic bean, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userJBCSFGJZ = new User();
		userJBCSFGJZ = userMng.findById(bean.getUserId());
		User manageruser = new User();
		manageruser = userJBCSFGJZ.getDepart().getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(manageruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(manageruser.getName());
				tProcessPrintDetail.setFuserId(manageruser.getId());
				tProcessPrintDetail.setFdeptId(manageruser.getDpID());
				tProcessPrintDetail.setFdeptName(manageruser.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("经办处室分管局长");
		return tProcessPrintDetail;
	}
	
	/**
	 * 获得审签中的办公室处室负责人
	 */
	@Override
	public TProcessPrintDetail getBGSFZRapply(List<TProcessCheck> checkList,String type,PurchaseApplyBasic bean,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", "办公室");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userBGSFZR.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userBGSFZR.getName());
				tProcessPrintDetail.setFuserId(userBGSFZR.getId());
				tProcessPrintDetail.setFdeptId(userBGSFZR.getDpID());
				tProcessPrintDetail.setFdeptName(userBGSFZR.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("办公室负责人");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}
	
	
	@Override
	public TProcessPrintDetail getCWJZapply(List<TProcessCheck> checkList,
			String type, PurchaseApplyBasic bean, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userFGCWJZ = userMng.getUserByRoleNameAndDepartName("分管财务局长", "局领导");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userFGCWJZ.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userFGCWJZ.getName());
				tProcessPrintDetail.setFuserId(userFGCWJZ.getId());
				tProcessPrintDetail.setFdeptId(userFGCWJZ.getDpID());
				tProcessPrintDetail.setFdeptName(userFGCWJZ.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("分管财务局长");
		return tProcessPrintDetail;
	}
}
