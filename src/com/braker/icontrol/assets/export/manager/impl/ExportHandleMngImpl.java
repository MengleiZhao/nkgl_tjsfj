package com.braker.icontrol.assets.export.manager.impl;

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
import com.braker.icontrol.assets.export.manager.ExportHandleMng;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.icontrol.cgmanage.cgexport.manager.ExportCgMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;


@Service
@Transactional
public class ExportHandleMngImpl extends BaseManagerImpl<Handle> implements ExportHandleMng {
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	
	@Override
	public List<TProcessPrintDetail> arrangeCheckDetailHandle(
			Handle bean, String type, User user) throws Exception {
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		String busType = "";
		if("ZCLX-02".equals(type)){
			busType = "GDZCCZ";
		}else{
			busType = "WXZCCZ";
		}
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(busType, bean.getfRecDeptId());
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
		if("ZCLX-02".equals(type)){
			TProcessPrintDetail tProcessPrintDetail0 = getDJBMFZR(listTProcessChecks);//其实是获取采购管理岗
			detailList.add(tProcessPrintDetail0);
		}
		TProcessPrintDetail tProcessPrintDetail1 = getJBBMFZR(listTProcessChecks, type, bean);
		TProcessPrintDetail tProcessPrintDetail2 = getJBCSFGJZ(listTProcessChecks, type, bean);
		TProcessPrintDetail tProcessPrintDetail3 = getJZ(listTProcessChecks);
		detailList.add(tProcessPrintDetail1);
		detailList.add(tProcessPrintDetail2);
		detailList.add(tProcessPrintDetail3);
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setFTabName("Handle");
			detailList.get(i).setFTabId(bean.getfId());
			detailList.get(i).setFTabIdName("fId");
			super.merge(detailList.get(i));
		}
		return detailList;
	}


	@Override
	public TProcessPrintDetail getJBBMFZR(List<TProcessCheck> checkList,
			String type, Handle bean) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getfRecDeptId());
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
			String type, Handle bean) {
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
	public TProcessPrintDetail getJZ(List<TProcessCheck> checkList){
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userJZ = userMng.getUserByRoleNameAndDepartName("局长", "局领导");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userJZ.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userJZ.getName());
				tProcessPrintDetail.setFuserId(userJZ.getId());
				tProcessPrintDetail.setFdeptId(userJZ.getDpID());
				tProcessPrintDetail.setFdeptName(userJZ.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("局长");
		return tProcessPrintDetail;
	}
	
	@Override
	public TProcessPrintDetail getDJBMFZR(List<TProcessCheck> checkList){
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userJZ = userMng.getUserByRoleNameAndDepartName("资产管理岗", "办公室");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userJZ.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userJZ.getName());
				tProcessPrintDetail.setFuserId(userJZ.getId());
				tProcessPrintDetail.setFdeptId(userJZ.getDpID());
				tProcessPrintDetail.setFdeptName(userJZ.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				tProcessPrintDetail.setFcheckTimes(checkbean.getFcheckTimes());
			}
		}
		tProcessPrintDetail.setfRoleName("资产管理岗");
		return tProcessPrintDetail;
	}
}
