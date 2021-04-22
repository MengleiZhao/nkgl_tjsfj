package com.braker.workflow.manager.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Condition;
import com.braker.common.hibernate.Finder;
import com.braker.common.hibernate.OrderBy;
import com.braker.common.hibernate.Updater;
import com.braker.common.page.Pagination;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.Proposer;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.loan.model.Payment;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.incomemanage.accountsCurrent.model.AccountsCurrent;
import com.braker.icontrol.incomemanage.accountsCurrent.model.AccountsRegister;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;


@Service
@Transactional
public class TProcessPrintDetailMngImpl extends BaseManagerImpl<TProcessPrintDetail> implements TProcessPrintDetailMng {
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private RoleMng roleMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private ApplyMng applyMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	@Override
	public List<TProcessCheck> applycheckList(String type, ApplicationBasicInfo bean, User user) throws Exception {
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
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getgCode(),"1");
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
		//历史审批节点
		String historyNodes=tProcessCheckMng.getHistoryNodes(strType,bean.getDept(),bean.getBeanCode());
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
		return listTProcessChecks;
	}
	
	@Override
	public List<TProcessCheck> reimbcheckList(String type, ReimbAppliBasicInfo bean,User user) throws Exception {
		//以下几行是获取事前申请的工作流
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
		if("GWCX".equals(bean.getTravelType())){
			strType = "GWCXBX";
		}
		if("1".equals(String.valueOf(bean.getType()))){
			ApplicationBasicInfo basicInfos = applyMng.findByCode(bean.getgCode());
			if("FLFSQ".equals(basicInfos.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(basicInfos.getCommonType())){
				strType = "TYSXSPPSBX";
			}
		}
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getgCode(),"1");
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
		//历史审批节点
		String historyNodes=tProcessCheckMng.getHistoryNodes(strType,bean.getDept(),bean.getBeanCode());
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
		return listTProcessChecks;
	}
	
	@Override
	public List<TProcessCheck> directlycheckList(DirectlyReimbAppliBasicInfo bean, User user) throws Exception {
		//以下几行是获取事前申请的工作流
		String strType = "";
		if("ZJBXLX-0".equals(bean.getDirType())){
			strType = "ZJBXLX-0";
		}
		if("ZJBXLX-1".equals(bean.getDirType())){
			strType = "ZJBXLX-1";
		}
		if("ZJBXLX-2".equals(bean.getDirType())){
			strType = "ZJBXLX-2";
		}
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getDrCode(),"1");
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
		//历史审批节点
		String historyNodes=tProcessCheckMng.getHistoryNodes(strType,bean.getDept(),bean.getBeanCode());
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
		return listTProcessChecks;
	}
	
	
	/**
	 * 获得审签中的经办部门负责人
	 */
	@Override
	public TProcessPrintDetail getJBBMR(List<TProcessCheck> checkList,String type
			, ApplicationBasicInfo bean, ReimbAppliBasicInfo rbean,DirectlyReimbAppliBasicInfo dbean
			,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		if(dbean==null){//除直接报销外的申请报销
			//判断事前还是事后
			if("0".equals(type)){//事前申请
				if("1".equals(bean.getDept())){//是办公室
					//判断报销人是不是部门负责人
					User fzruser = userMng.getUserByRoleIdAndDepartId("820099ee-7aca-11e9-8688-005056a17ba3", "1");//根据角色和部门id查询
					if(fzruser.getId().equals(bean.getUser())){//报销人是部门负责人
						tProcessPrintDetail.setFuserName(bean.getUserNames());
						tProcessPrintDetail.setFuserId(bean.getFuserId());
						tProcessPrintDetail.setFdeptId(bean.getDept());
						tProcessPrintDetail.setFdeptName(bean.getDeptName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake("同意");
						tProcessPrintDetail.setFcheckTime(new Date());
					}else{//报销人不是部门负责人
						for (int i = 0; i < checkList.size(); i++) {
							TProcessCheck checkbean = checkList.get(i);
							if(fzruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
								tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
								tProcessPrintDetail.setFuserName(fzruser.getName());
								tProcessPrintDetail.setFuserId(fzruser.getId());
								tProcessPrintDetail.setFdeptId(fzruser.getDpID());
								tProcessPrintDetail.setFdeptName(fzruser.getDepartName());
								tProcessPrintDetail.setFcheckResult("1");
								tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
							}
						}
					}
				}else{
					if("8".equals(bean.getDept())){//是局领导
						//判断申报人是不是财务分管领导
						Depart dept = departMng.findById(bean.getDept());
						User userBM = userMng.getUserByRoleNameAndDepartName("分管财务局长", dept.getName());
						if(bean.getUser().equals(userBM.getId())){//申报人是分管财务局长
							for (int i = 0; i < checkList.size(); i++) {
								TProcessCheck checkbean = checkList.get(i);
								if("86".equals(checkbean.getFuserId())){//匹配到相同的审批人
									tProcessPrintDetail.setFuserName(checkbean.getFuserName());
									tProcessPrintDetail.setFuserId(checkbean.getFuserId());
									tProcessPrintDetail.setFdeptId("16");
									tProcessPrintDetail.setFdeptName("办公室");
									tProcessPrintDetail.setFcheckResult("1");
									tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
									tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
								}
							}
						}else{//申报人不是财务分管领导
							for (int i = 0; i < checkList.size(); i++) {
								TProcessCheck checkbean = checkList.get(i);
								if("6".equals(checkbean.getFuserId())){//匹配到相同的审批人
									tProcessPrintDetail.setFuserName(userBM.getName());
									tProcessPrintDetail.setFuserId(userBM.getId());
									tProcessPrintDetail.setFdeptId(userBM.getDpID());
									tProcessPrintDetail.setFdeptName(userBM.getDepartName());
									tProcessPrintDetail.setFcheckResult("1");
									tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
									tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
								}
							}
						}
					}else{
						//不是办公室和局领导
						//判断申报人是不是部门负责人
						Depart dept = departMng.findById(bean.getDept());
						User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
						if(bean.getFuserId().equals(userBM.getId())){//申报人是部门负责人
							tProcessPrintDetail.setFuserName(bean.getUserNames());
							tProcessPrintDetail.setFuserId(bean.getFuserId());
							tProcessPrintDetail.setFdeptId(bean.getDept());
							tProcessPrintDetail.setFdeptName(bean.getDeptName());
							tProcessPrintDetail.setFcheckResult("1");
							tProcessPrintDetail.setFcheckRemake("同意");
							tProcessPrintDetail.setFcheckTime(new Date());
						}else{//申报人不是部门负责人
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
								}
							}
						}
					}
				}
			}else{//事后报销
				if("1".equals(rbean.getDept())){//是办公室
					//判断报销人是不是部门负责人
					User fzruser = userMng.getUserByRoleIdAndDepartId("820099ee-7aca-11e9-8688-005056a17ba3", "1");//根据角色和部门id查询
					if(fzruser.getId().equals(rbean.getUser())){//报销人是部门负责人
						tProcessPrintDetail.setFuserName(rbean.getUserName());
						tProcessPrintDetail.setFuserId(rbean.getUser());
						tProcessPrintDetail.setFdeptId(rbean.getDept());
						tProcessPrintDetail.setFdeptName(rbean.getDeptName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake("同意");
						tProcessPrintDetail.setFcheckTime(new Date());
					}else{//报销人不是部门负责人
						for (int i = 0; i < checkList.size(); i++) {
							TProcessCheck checkbean = checkList.get(i);
							if(fzruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
								tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
								tProcessPrintDetail.setFuserName(fzruser.getName());
								tProcessPrintDetail.setFuserId(fzruser.getId());
								tProcessPrintDetail.setFdeptId(fzruser.getDpID());
								tProcessPrintDetail.setFdeptName(fzruser.getDepartName());
								tProcessPrintDetail.setFcheckResult("1");
								tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
							}
						}
					}
				}else{//不是办公室
					//判断申报人是不是部门负责人
					Depart dept = departMng.findById(rbean.getDept());
					User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
					if(rbean.getUser().equals(userBM.getId())){//申报人是部门负责人
						tProcessPrintDetail.setFuserName(rbean.getUserName());
						tProcessPrintDetail.setFuserId(rbean.getUser());
						tProcessPrintDetail.setFdeptId(rbean.getDept());
						tProcessPrintDetail.setFdeptName(rbean.getDeptName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake("同意");
						tProcessPrintDetail.setFcheckTime(new Date());
					}else{//申报人不是部门负责人
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
							}
						}
					}
				}
			}
			tProcessPrintDetail.setFpWId(fpWId);
		}else {//直接报销
			if("1".equals(dbean.getDept())){//是办公室
				//判断报销人是不是部门负责人
				User fzruser = userMng.getUserByRoleIdAndDepartId("820099ee-7aca-11e9-8688-005056a17ba3", "1");//根据角色和部门id查询
				if(fzruser.getId().equals(dbean.getUser())){//报销人是部门负责人
					tProcessPrintDetail.setFuserName(dbean.getUserName());
					tProcessPrintDetail.setFuserId(dbean.getFuserId());
					tProcessPrintDetail.setFdeptId(dbean.getDept());
					tProcessPrintDetail.setFdeptName(dbean.getDeptName());
					tProcessPrintDetail.setFcheckResult("1");
					tProcessPrintDetail.setFcheckRemake("同意");
					tProcessPrintDetail.setFcheckTime(new Date());
				}else{//报销人不是部门负责人
					for (int i = 0; i < checkList.size(); i++) {
						TProcessCheck checkbean = checkList.get(i);
						if(fzruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
							tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
							tProcessPrintDetail.setFuserName(fzruser.getName());
							tProcessPrintDetail.setFuserId(fzruser.getId());
							tProcessPrintDetail.setFdeptId(fzruser.getDpID());
							tProcessPrintDetail.setFdeptName(fzruser.getDepartName());
							tProcessPrintDetail.setFcheckResult("1");
							tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
						}
					}
				}
			}else{//不是办公室
				//判断申报人是不是部门负责人
				Depart dept = departMng.findById(dbean.getDept());
				User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
				if(dbean.getFuserId().equals(userBM.getId())){//申报人是部门负责人
					tProcessPrintDetail.setFuserName(dbean.getUserName());
					tProcessPrintDetail.setFuserId(dbean.getFuserId());
					tProcessPrintDetail.setFdeptId(dbean.getDept());
					tProcessPrintDetail.setFdeptName(dbean.getDeptName());
					tProcessPrintDetail.setFcheckResult("1");
					tProcessPrintDetail.setFcheckRemake("同意");
					tProcessPrintDetail.setFcheckTime(new Date());
				}else{//申报人不是部门负责人
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
						}
					}
				}
			}
			tProcessPrintDetail.setFpWdDd(fpWId);
		}
		tProcessPrintDetail.setfRoleName("审批人");
		return tProcessPrintDetail;
	}
	
	
	
	/**
	 * 获得审签中的经办部门负责人
	 */
	@Override
	public TProcessPrintDetail getJBBMFZR(List<TProcessCheck> checkList,String type
			, ApplicationBasicInfo bean, ReimbAppliBasicInfo rbean,DirectlyReimbAppliBasicInfo dbean
			,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		if(dbean==null){//除直接报销外的申请报销
			//判断事前还是事后
			if("0".equals(type)){//事前申请
				if("1".equals(bean.getDept())){//是办公室
					//判断报销人是不是部门负责人
					User fzruser = userMng.getUserByRoleIdAndDepartId("820099ee-7aca-11e9-8688-005056a17ba3", "1");//根据角色和部门id查询
					if(fzruser.getId().equals(bean.getUser())){//报销人是部门负责人
						tProcessPrintDetail.setFuserName(bean.getUserNames());
						tProcessPrintDetail.setFuserId(bean.getFuserId());
						tProcessPrintDetail.setFdeptId(bean.getDept());
						tProcessPrintDetail.setFdeptName(bean.getDeptName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake("同意");
						tProcessPrintDetail.setFcheckTime(new Date());
					}else{//报销人不是部门负责人
						for (int i = 0; i < checkList.size(); i++) {
							TProcessCheck checkbean = checkList.get(i);
							if(fzruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
								tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
								tProcessPrintDetail.setFuserName(fzruser.getName());
								tProcessPrintDetail.setFuserId(fzruser.getId());
								tProcessPrintDetail.setFdeptId(fzruser.getDpID());
								tProcessPrintDetail.setFdeptName(fzruser.getDepartName());
								tProcessPrintDetail.setFcheckResult("1");
								tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
								/*Role role = roleMng.findById(Integer.valueOf(checkbean.getFroleId()));
								tProcessPrintDetail.setfRoleName(role.getName());*/
							}
						}
					}
				}else{//不是办公室
					//判断申报人是不是部门负责人
					Depart dept = departMng.findById(bean.getDept());
					User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
					if(bean.getFuserId().equals(userBM.getId())){//申报人是部门负责人
						tProcessPrintDetail.setFuserName(bean.getUserNames());
						tProcessPrintDetail.setFuserId(bean.getFuserId());
						tProcessPrintDetail.setFdeptId(bean.getDept());
						tProcessPrintDetail.setFdeptName(bean.getDeptName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake("同意");
						tProcessPrintDetail.setFcheckTime(new Date());
					}else{//申报人不是部门负责人
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
							}
						}
					}
				}
			}else{//事后报销
				if("1".equals(rbean.getDept())){//是办公室
					//判断报销人是不是部门负责人
					User fzruser = userMng.getUserByRoleIdAndDepartId("820099ee-7aca-11e9-8688-005056a17ba3", "1");//根据角色和部门id查询
					if(fzruser.getId().equals(rbean.getUser())){//报销人是部门负责人
						tProcessPrintDetail.setFuserName(rbean.getUserName());
						tProcessPrintDetail.setFuserId(rbean.getUser());
						tProcessPrintDetail.setFdeptId(rbean.getDept());
						tProcessPrintDetail.setFdeptName(rbean.getDeptName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake("");
						tProcessPrintDetail.setFcheckTime(new Date());
					}else{//报销人不是部门负责人
						for (int i = 0; i < checkList.size(); i++) {
							TProcessCheck checkbean = checkList.get(i);
							if(fzruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
								tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
								tProcessPrintDetail.setFuserName(fzruser.getName());
								tProcessPrintDetail.setFuserId(fzruser.getId());
								tProcessPrintDetail.setFdeptId(fzruser.getDpID());
								tProcessPrintDetail.setFdeptName(fzruser.getDepartName());
								tProcessPrintDetail.setFcheckResult("1");
								tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
							}
						}
					}
				}else{//不是办公室
					//判断申报人是不是部门负责人
					Depart dept = departMng.findById(rbean.getDept());
					User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
					if(rbean.getUser().equals(userBM.getId())){//申报人是部门负责人
						tProcessPrintDetail.setFuserName(rbean.getUserName());
						tProcessPrintDetail.setFuserId(rbean.getUser());
						tProcessPrintDetail.setFdeptId(rbean.getDept());
						tProcessPrintDetail.setFdeptName(rbean.getDeptName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake("同意");
						tProcessPrintDetail.setFcheckTime(new Date());
					}else{//申报人不是部门负责人
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
							}
						}
					}
				}
			}
			tProcessPrintDetail.setFpWId(fpWId);
		}else {//直接报销
			if("1".equals(dbean.getDept())){//是办公室
				//判断报销人是不是部门负责人
				User fzruser = userMng.getUserByRoleIdAndDepartId("820099ee-7aca-11e9-8688-005056a17ba3", "1");//根据角色和部门id查询
				if(fzruser.getId().equals(dbean.getUser())){//报销人是部门负责人
					tProcessPrintDetail.setFuserName(dbean.getUserName());
					tProcessPrintDetail.setFuserId(dbean.getFuserId());
					tProcessPrintDetail.setFdeptId(dbean.getDept());
					tProcessPrintDetail.setFdeptName(dbean.getDeptName());
					tProcessPrintDetail.setFcheckResult("1");
					tProcessPrintDetail.setFcheckRemake("同意");
					tProcessPrintDetail.setFcheckTime(new Date());
				}else{//报销人不是部门负责人
					for (int i = 0; i < checkList.size(); i++) {
						TProcessCheck checkbean = checkList.get(i);
						if(fzruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
							tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
							tProcessPrintDetail.setFuserName(fzruser.getName());
							tProcessPrintDetail.setFuserId(fzruser.getId());
							tProcessPrintDetail.setFdeptId(fzruser.getDpID());
							tProcessPrintDetail.setFdeptName(fzruser.getDepartName());
							tProcessPrintDetail.setFcheckResult("1");
							tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
						}
					}
				}
			}else{//不是办公室
				//判断申报人是不是部门负责人
				Depart dept = departMng.findById(dbean.getDept());
				User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
				if(dbean.getFuserId().equals(userBM.getId())){//申报人是部门负责人
					tProcessPrintDetail.setFuserName(dbean.getUserName());
					tProcessPrintDetail.setFuserId(dbean.getFuserId());
					tProcessPrintDetail.setFdeptId(dbean.getDept());
					tProcessPrintDetail.setFdeptName(dbean.getDeptName());
					tProcessPrintDetail.setFcheckResult("1");
					tProcessPrintDetail.setFcheckRemake("同意");
					tProcessPrintDetail.setFcheckTime(new Date());
				}else{//申报人不是部门负责人
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
						}
					}
				}
			}
			tProcessPrintDetail.setFpWdDd(fpWId);
		}
		tProcessPrintDetail.setfRoleName("经办处室处室负责人");
		return tProcessPrintDetail;
	}
	
	/*
	 * 获取经办人处室负责人（第一级）
	 */
	public TProcessPrintDetail getOperatorDepartment(List<TProcessCheck> checkList,String type
			, ApplicationBasicInfo bean, ReimbAppliBasicInfo rbean,DirectlyReimbAppliBasicInfo dbean
			,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		if(dbean==null){//除直接报销外的申请报销
			//判断事前还是事后
			if("0".equals(type)){//事前申请
				//判断申报人是不是部门负责人
				Depart dept = departMng.findById(bean.getDept());
				User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
				if(bean.getUser().equals(userBM.getId())){//申报人是部门负责人
					tProcessPrintDetail.setFuserName(bean.getUserNames());
					tProcessPrintDetail.setFuserId(bean.getUser());
					tProcessPrintDetail.setFdeptId(bean.getDept());
					tProcessPrintDetail.setFdeptName(bean.getDeptName());
					tProcessPrintDetail.setFcheckResult("1");
					tProcessPrintDetail.setFcheckRemake("同意");
					tProcessPrintDetail.setFcheckTime(new Date());
				}else{//申报人不是部门负责人
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
							checkList.remove(i);
							break;
						}
					}
				}
			}else{//事后报销
				//判断申报人是不是部门负责人
				Depart dept = departMng.findById(rbean.getDept());
				User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
				if(rbean.getUser().equals(userBM.getId())){//申报人是部门负责人
					tProcessPrintDetail.setFuserName(rbean.getUserName());
					tProcessPrintDetail.setFuserId(rbean.getUser());
					tProcessPrintDetail.setFdeptId(rbean.getDept());
					tProcessPrintDetail.setFdeptName(rbean.getDeptName());
					tProcessPrintDetail.setFcheckResult("1");
					tProcessPrintDetail.setFcheckRemake("同意");
					tProcessPrintDetail.setFcheckTime(new Date());
				}else{//申报人不是部门负责人
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
						}
					}
				}
			}
			tProcessPrintDetail.setFpWId(fpWId);
		}else {
			
		}
		tProcessPrintDetail.setfRoleName("经办人处室负责人");
		return tProcessPrintDetail;
	}

	/*
	 * 获取归口部门
	 */
	public TProcessPrintDetail getRelevantDepartment(List<TProcessCheck> checkList, ApplicationBasicInfo bean, Integer fpWId, String type) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userBGSFZR = null;
		if ("FLFSQ".equals(type)) {//福利费社情归口部门是政治部人事警务处
			userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", "政治部人事警务处");
		} else if ("SPPSSQ".equals(type)) {
			userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", "综合处");
		}
		
		
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
				checkList.remove(i);
				break;
			}
		}
		tProcessPrintDetail.setfRoleName("归口部门");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}
	
	/*
	 * 获取装备财务处处室负责人
	 */
	public TProcessPrintDetail getFinanceDepartment(List<TProcessCheck> checkList, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", "装备财务处");
		
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
				checkList.remove(i);
				break;
			}
		}
		tProcessPrintDetail.setfRoleName("装备财务处处室负责人");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}
	
	/*
	 * 获取政治部宣传教育处处室负责人
	 */
	public TProcessPrintDetail getZZBXCDepartment(List<TProcessCheck> checkList, Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", "政治部宣传教育处");
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
				checkList.remove(i);
				break;
			}
		}
		tProcessPrintDetail.setfRoleName("政治部宣传教育处处室负责人");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}
	
	/**
	 * 获得审签中的会计岗
	 */
	@Override
	public TProcessPrintDetail getJKJG(List<TProcessCheck> checkList,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		String userID = roleMng.getUserIdByRoleName("会计岗");
		User userKJG = userMng.findById(userID);
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userKJG.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userKJG.getName());
				tProcessPrintDetail.setFuserId(userKJG.getId());
				tProcessPrintDetail.setFdeptId(userKJG.getDpID());
				tProcessPrintDetail.setFdeptName(userKJG.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
			}
		}
		tProcessPrintDetail.setfRoleName("会计岗");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}

	/**
	 * 获得审签中的办公室处室负责人
	 */
	@Override
	public TProcessPrintDetail getBGSFZR(List<TProcessCheck> checkList,Integer fpWId) {
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
				checkList.remove(i);
				break;
			}
		}
		tProcessPrintDetail.setfRoleName("办公室处室负责人");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}

	/**
	 * 获得审签中的经办处室分管局长
	 */
	@Override
	public TProcessPrintDetail getJBCSFGJZ(List<TProcessCheck> checkList,
			String type, ApplicationBasicInfo bean, ReimbAppliBasicInfo rbean,
			DirectlyReimbAppliBasicInfo dbean,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User manageruser = new User();
		User userJBCSFGJZ = new User();
		if(dbean==null){//申请报销
			//判断事前还是事后
			if("0".equals(type)){//事前申请
				userJBCSFGJZ = userMng.findById(bean.getUserId());
				manageruser = userJBCSFGJZ.getDepart().getManager();
			}else {
				userJBCSFGJZ = userMng.findById(rbean.getUserId());
				manageruser = userJBCSFGJZ.getDepart().getManager();
			}
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
					checkList.remove(i);
					break;
				}
			}
			tProcessPrintDetail.setFpWId(fpWId);
		}else {//直接报销
			userJBCSFGJZ = userMng.findById(dbean.getUserId());
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
				}
			}
			tProcessPrintDetail.setFpWdDd(fpWId);
		}
		tProcessPrintDetail.setfRoleName("业务分管副局长");
		return tProcessPrintDetail;
	}


	@Override
	public TProcessPrintDetail getFGCWJZ(List<TProcessCheck> checkList,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userBGSFZR = userMng.getUserByRoleNameAndDepartName("分管财务局长", "局领导");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userBGSFZR.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFuserName(userBGSFZR.getName());
				tProcessPrintDetail.setFuserId(userBGSFZR.getId());
				tProcessPrintDetail.setFdeptId(userBGSFZR.getDpID());
				tProcessPrintDetail.setFdeptName(userBGSFZR.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				checkList.remove(i);
				break;
			}
		}
		tProcessPrintDetail.setfRoleName("分管财务副局长");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
		
	}
	
	/*
	 * 获取办公室分管局长
	 */
	public TProcessPrintDetail getOfficeDirector(List<TProcessCheck> checkList,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		List<Depart> departList = departMng.findByProperty("name", "办公室");
		Depart depart = departList.get(0);
		User officeDirector = depart.getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(officeDirector.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFuserName(officeDirector.getName());
				tProcessPrintDetail.setFuserId(officeDirector.getId());
				tProcessPrintDetail.setFdeptId(officeDirector.getDpID());
				tProcessPrintDetail.setFdeptName(officeDirector.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				checkList.remove(i);
				break;
			}
		}
		tProcessPrintDetail.setfRoleName("办公室分管副局长");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
		
	}

	@Override
	public TProcessPrintDetail getJZ(List<TProcessCheck> checkList,Integer fpWId) {
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
			}
		}
		tProcessPrintDetail.setfRoleName("局长");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}


	@Override
	public List<TProcessPrintDetail> arrangeCheckDetail(ApplicationBasicInfo bean,ReimbAppliBasicInfo rbean, String type,User user) throws Exception {
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		Integer fpWId = null;
		if("0".equals(type)){//事前
			checkList = applycheckList(type, bean, user);
			fpWId = bean.getgId();
			
			if("1".equals(bean.getType())) {//通用
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1;
				if (!"QTFYSQ".equals(bean.getCommonType())) {
					tProcessPrintDetail1 = getRelevantDepartment(checkList, bean, fpWId, bean.getCommonType());
				} else {
					tProcessPrintDetail1 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				}
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2;
				if (!"QTFYSQ".equals(bean.getCommonType())) {
					tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				} else {
					tProcessPrintDetail2 = getFinanceDepartment(checkList, fpWId);
				}
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3;
				if (!"QTFYSQ".equals(bean.getCommonType())) {
					tProcessPrintDetail3 = getFinanceDepartment(checkList, fpWId);
				} else {
					tProcessPrintDetail3 = getFGCWJZ(checkList,fpWId);
				}
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				if (!"QTFYSQ".equals(bean.getCommonType())) {
					TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
					detailList.add(tProcessPrintDetail4);
				}
			} else if (bean.getType().equals("2")) {//会议
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1 = getBGSFZR(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3 = getFinanceDepartment(checkList, fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			} else if (bean.getType().equals("3")) {//培训
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1 = getZZBXCDepartment(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3 = getFinanceDepartment(checkList, fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			} else if (bean.getType().equals("4")) {//环外出行和公务出差
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1 = getFinanceDepartment(checkList, fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail3);
			} else if (bean.getType().equals("5")) {//接待
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1 = getBGSFZR(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2 = getFinanceDepartment(checkList, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4 = getOfficeDirector(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			}
			
//			if(bean.getType().equals("1")||bean.getType().equals("2")||bean.getType().equals("3")||bean.getType().equals("5")||bean.getType().equals("7")){//2会议/3培训/5接待/出国
//				TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, bean, rbean,null,fpWId);
//				//TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
//				TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
//				TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, bean, rbean,null,fpWId);
//				TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
//				detailList.add(tProcessPrintDetail0);
//				//detailList.add(tProcessPrintDetail1);
//				detailList.add(tProcessPrintDetail2);
//				detailList.add(tProcessPrintDetail3);
//				detailList.add(tProcessPrintDetail4);
//			}else if(bean.getType().equals("4")){//差旅
//				String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
//
//				if("GWCX".equals(bean.getTravelType())){//公务出行
//					strType = "GWCXSQ";
//					if("8".equals(bean.getDept())){//如果是局领导处室
//						TProcessPrintDetail tProcessPrintDetail0 = getJBBMR(checkList, type, bean, rbean,null,fpWId);
//						detailList.add(tProcessPrintDetail0);
//					}else{
//						TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, bean, rbean,null,fpWId);
//						TProcessPrintDetail tProcessPrintDetail1 = getJBCSFGJZ(checkList, type, bean, rbean,null,fpWId);
//						detailList.add(tProcessPrintDetail0);
//						detailList.add(tProcessPrintDetail1);
//					}
//				}else{//公务出差
//					//如果是局领导处室
//					if("8".equals(bean.getDept())&&"3".equals(bean.getUser())){//如果是梁局
//						TProcessPrintDetail tProcessPrintDetail0 = getFGCWJZ(checkList,fpWId);
//						detailList.add(tProcessPrintDetail0);
//					}else if("8".equals(bean.getDept())&&!"3".equals(bean.getUser())){//如果是梁局
//						TProcessPrintDetail tProcessPrintDetail0 = getJZ(checkList,fpWId);
//						detailList.add(tProcessPrintDetail0);
//					}else{
//						TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, bean, rbean,null,fpWId);
//						//TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
//						TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, bean, rbean,null,fpWId);
//						TProcessPrintDetail tProcessPrintDetail4 = getJZ(checkList,fpWId);
//						detailList.add(tProcessPrintDetail0);
//						//detailList.add(tProcessPrintDetail1);
//						detailList.add(tProcessPrintDetail3);
//						detailList.add(tProcessPrintDetail4);
//					}
//					
//				}
//			}else if(bean.getType().equals("6")){//用车
//				
//			}
		}else{//事后
			ApplicationBasicInfo applyBean = applyMng.findByCode(rbean.getgCode());
			checkList = reimbcheckList(type,rbean, user);
			fpWId = rbean.getrId();
			if("1".equals(rbean.getType())) {//通用
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1;
				tProcessPrintDetail1 = getCNG(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2;
				tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3;
				tProcessPrintDetail3 = getFinanceDepartment(checkList,fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4;
				tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			} else if (rbean.getType().equals("2")) {//会议
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1;
				tProcessPrintDetail1 = getCNG(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2;
				tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3;
				tProcessPrintDetail3 = getFinanceDepartment(checkList,fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4;
				tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			} else if (rbean.getType().equals("3")) {//培训
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1;
				tProcessPrintDetail1 = getCNG(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2;
				tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3;
				tProcessPrintDetail3 = getFinanceDepartment(checkList,fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4;
				tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			} else if (rbean.getType().equals("4")) {//差旅
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1;
				tProcessPrintDetail1 = getCNG(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2;
				tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3;
				tProcessPrintDetail3 = getFinanceDepartment(checkList,fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4;
				tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			} else if (rbean.getType().equals("5")) {//接待
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1 = getBGSFZR(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2 = getCNG(checkList,fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3 = getFinanceDepartment(checkList, fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail4);
				//六级审批人
				TProcessPrintDetail tProcessPrintDetail5 = getOfficeDirector(checkList,fpWId);
				detailList.add(tProcessPrintDetail5);
			} else if (rbean.getType().equals("11")) {//保证金报销
				//一级审批人
				TProcessPrintDetail tProcessPrintDetail0 = getOperatorDepartment(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail0);
				//二级审批人
				TProcessPrintDetail tProcessPrintDetail1;
				tProcessPrintDetail1 = getCNG(checkList,fpWId);
				detailList.add(tProcessPrintDetail1);
				//三级审批人
				TProcessPrintDetail tProcessPrintDetail2;
				tProcessPrintDetail2 = getJBCSFGJZ(checkList, type, bean, rbean, null, fpWId);
				detailList.add(tProcessPrintDetail2);
				//四级审批人
				TProcessPrintDetail tProcessPrintDetail3;
				tProcessPrintDetail3 = getFinanceDepartment(checkList,fpWId);
				detailList.add(tProcessPrintDetail3);
				//五级审批人
				TProcessPrintDetail tProcessPrintDetail4;
				tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail4);
			}
			/*if(!rbean.getType().equals("1")){//不是通用事项报销*/
//				if("4".equals(rbean.getType())){
//					if("GWCX".equals(applyBean.getTravelType()) && "8".equals(applyBean.getDept())){//公务出行
//						//TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, applyBean, rbean,null,fpWId);
//						TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
//						TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
//						//TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, applyBean, rbean,null,fpWId);
//						TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
//						//detailList.add(tProcessPrintDetail0);
//						detailList.add(tProcessPrintDetail1);
//						detailList.add(tProcessPrintDetail2);
//						//detailList.add(tProcessPrintDetail3);
//						detailList.add(tProcessPrintDetail4);
//					}else{
//						if("GWCC".equals(applyBean.getTravelType()) && "8".equals(applyBean.getDept())){//公务出行
//							TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
//							TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
//							//TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, applyBean, rbean,null,fpWId);
//							TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
//							detailList.add(tProcessPrintDetail1);
//							detailList.add(tProcessPrintDetail2);
//							//detailList.add(tProcessPrintDetail3);
//							detailList.add(tProcessPrintDetail4);
//						}else{
//							TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, applyBean, rbean,null,fpWId);
//							TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
//							TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
//							//TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, applyBean, rbean,null,fpWId);
//							TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
//							detailList.add(tProcessPrintDetail0);
//							detailList.add(tProcessPrintDetail1);
//							detailList.add(tProcessPrintDetail2);
//							//detailList.add(tProcessPrintDetail3);
//							detailList.add(tProcessPrintDetail4);
//						}
//					}
//				}else{
//					TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, applyBean, rbean,null,fpWId);
//					TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
//					TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
//					//TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, applyBean, rbean,null,fpWId);
//					TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
//					detailList.add(tProcessPrintDetail0);
//					detailList.add(tProcessPrintDetail1);
//					detailList.add(tProcessPrintDetail2);
//					//detailList.add(tProcessPrintDetail3);
//					detailList.add(tProcessPrintDetail4);
//				}
			/*}*/
		}
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setFpDId(shenTongMng.getSeq("process_print_detail_seq"));
			if("0".equals(type)){//事前ApplicationBasicInfo bean,ReimbAppliBasicInfo
				detailList.get(i).setFTabName("ApplicationBasicInfo");
				detailList.get(i).setFTabId(bean.getgId());
				detailList.get(i).setFTabIdName("gId");
			}else{
				detailList.get(i).setFTabName("ReimbAppliBasicInfo");
				detailList.get(i).setFTabId(rbean.getrId());
				detailList.get(i).setFTabIdName("rId");
			}
			super.merge(detailList.get(i));
		}
		return detailList;
	}

	@Override
	public List<TProcessPrintDetail> arrangedirectlyCheckDetail(
			DirectlyReimbAppliBasicInfo bean, User user) throws Exception {
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		checkList = directlycheckList(bean, user);
		Integer fpWId = bean.getDrId();
		
		if("ZJBXLX-0".equals(bean.getDirType())){
			//第一级审批人
			TProcessPrintDetail tProcessPrintDetail0 = getDepartment(checkList, bean, fpWId, "");
			//第二级审批人
			TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList, fpWId);
			//第三级审批人
			TProcessPrintDetail tProcessPrintDetail2 = getCorrelationDirector(checkList, bean, fpWId);
			//第四级审批人
			TProcessPrintDetail tProcessPrintDetail3 = getFinanceDepartment(checkList, fpWId);
			//第五级审批人
			TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList, fpWId);
			
			detailList.add(tProcessPrintDetail0);
			detailList.add(tProcessPrintDetail1);
			detailList.add(tProcessPrintDetail2);
			detailList.add(tProcessPrintDetail3);
			detailList.add(tProcessPrintDetail4);
		}
		if("ZJBXLX-1".equals(bean.getDirType())){
			//第一级审批人
			TProcessPrintDetail tProcessPrintDetail0 = getDepartment(checkList, bean, fpWId, "");
			//第二级审批人
			TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList, fpWId);
			//第三级审批人
			TProcessPrintDetail tProcessPrintDetail2 = getCorrelationDirector(checkList, bean, fpWId);
			//第四级审批人
			TProcessPrintDetail tProcessPrintDetail3 = getFinanceDepartment(checkList, fpWId);
			//第五级审批人
			TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList, fpWId);
			
			detailList.add(tProcessPrintDetail0);
			detailList.add(tProcessPrintDetail1);
			detailList.add(tProcessPrintDetail2);
			detailList.add(tProcessPrintDetail3);
			detailList.add(tProcessPrintDetail4);
		}
		if("ZJBXLX-2".equals(bean.getDirType())){
			//第一级审批人
			TProcessPrintDetail tProcessPrintDetail0 = getDepartment(checkList, bean, fpWId, "");
			//第二级审批人
			TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList, fpWId);
			//第三级审批人
			TProcessPrintDetail tProcessPrintDetail2 = getFinanceDepartment(checkList, fpWId);
			
			detailList.add(tProcessPrintDetail0);
			detailList.add(tProcessPrintDetail1);
			detailList.add(tProcessPrintDetail2);
		}
		
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setFpDId(shenTongMng.getSeq("process_print_detail_seq"));
			detailList.get(i).setFpWdDd(fpWId);
			detailList.get(i).setFTabName("DirectlyReimbAppliBasicInfo");
			detailList.get(i).setFTabId(bean.getDrId());
			detailList.get(i).setFTabIdName("drId");
			super.merge(detailList.get(i));
		}
		return detailList;
	}

	@Override
	public List<TProcessCheck> LoanBasicInfocheckList(String type,
			LoanBasicInfo bean, Payment pbean, User user) throws Exception {
		
		//以下几行是获取事前申请的工作流
		String strType = null;
		List<TProcessCheck> listTProcessChecks = new ArrayList<TProcessCheck>();
		List<TProcessCheck> listTProcessCheck = new ArrayList<TProcessCheck>();
		List<TNodeData> nodeConfList = new ArrayList<TNodeData>();
		if("0".equals(type)){
			strType = "ZJBX";
			//查询工作流
			nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"JKSQ", bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getlCode(),"1");
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("JKSQ", bean.getDept());
			//历史审批节点
			String historyNodes=tProcessCheckMng.getHistoryNodes(strType,bean.getDept(),bean.getBeanCode());
			listTProcessCheck = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),bean.getBeanCode());
		}else {
			strType = "HKDJ";
			//查询工作流
			nodeConfList=tProcessCheckMng.getNodeConf(pbean.getUserId(),strType,pbean.getApplyDepId(),pbean.getBeanCode(),pbean.getNextNodeCode(), pbean.getJoinTable(), pbean.getBeanCodeField(), pbean.getCode(),"1");
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, pbean.getApplyDepId());
			//历史审批节点
			String historyNodes=tProcessCheckMng.getHistoryNodes(strType, pbean.getApplyDepName(),pbean.getBeanCode());
			listTProcessCheck = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),pbean.getBeanCode());
		}
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
		return listTProcessChecks;
	}

	@Override
	public List<TProcessPrintDetail> arrangeLoanBasicInfoCheckDetail(String type,
			Payment pbean, LoanBasicInfo bean, User user) throws Exception {
		List<TProcessCheck> checkList = LoanBasicInfocheckList(type, bean, pbean, user);
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		Integer fpWId = null;
		User userJBCSFGJZ = new User();
		User manageruser = new User();
		if("0".equals(type)){//借款
			fpWId = bean.getlId();
			TProcessPrintDetail tProcessPrintDetail0 = new TProcessPrintDetail();;
			TProcessPrintDetail tProcessPrintDetail3 = getFGCWJZ(checkList, fpWId);
			/*userJBCSFGJZ = userMng.findById(bean.getUserId());
			manageruser = userJBCSFGJZ.getDepart().getManager();
			for (int i = 0; i < checkList.size(); i++) {
				TProcessCheck checkbean = checkList.get(i);
				if(manageruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
					tProcessPrintDetail3.setFuserName(manageruser.getName());
					tProcessPrintDetail3.setFuserId(manageruser.getId());
					tProcessPrintDetail3.setFdeptId(manageruser.getDpID());
					tProcessPrintDetail3.setFdeptName(manageruser.getDepartName());
					tProcessPrintDetail3.setFcheckResult("1");
					tProcessPrintDetail3.setFcheckRemake(checkbean.getFcheckRemake());
					tProcessPrintDetail3.setFcheckTime(checkbean.getFcheckTime());
				}
			}*/

			userJBCSFGJZ = userMng.findById(bean.getUserId());
			manageruser = userJBCSFGJZ.getDepart().getManager();
			//判断申报人是不是部门负责人
			Depart dept = departMng.findById(bean.getDept());
			User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
			if(bean.getDept().equals(userBM.getId())){//申报人是部门负责人
				tProcessPrintDetail0.setFuserName(bean.getUserName());
				tProcessPrintDetail0.setFuserId(bean.getUserId());
				tProcessPrintDetail0.setFdeptId(bean.getDept());
				tProcessPrintDetail0.setFdeptName(bean.getDeptName());
				tProcessPrintDetail0.setFcheckResult("1");
				tProcessPrintDetail0.setFcheckRemake("同意");
				tProcessPrintDetail0.setFcheckTime(new Date());
			}else{//申报人不是部门负责人
				for (int i = 0; i < checkList.size(); i++) {
					TProcessCheck checkbean = checkList.get(i);
					if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
						tProcessPrintDetail0.setFuserName(userBM.getName());
						tProcessPrintDetail0.setFuserId(userBM.getId());
						tProcessPrintDetail0.setFdeptId(userBM.getDpID());
						tProcessPrintDetail0.setFdeptName(userBM.getDepartName());
						tProcessPrintDetail0.setFcheckResult("1");
						tProcessPrintDetail0.setFcheckRemake(checkbean.getFcheckRemake());
						tProcessPrintDetail0.setFcheckTime(checkbean.getFcheckTime());
					}
				}
			}
			tProcessPrintDetail0.setFpWIiD(fpWId);
			tProcessPrintDetail3.setFpWIiD(fpWId);
			tProcessPrintDetail0.setfRoleName("经办人处室负责人");
			tProcessPrintDetail3.setfRoleName("分管财务副局长");
			detailList.add(tProcessPrintDetail0);
			detailList.add(tProcessPrintDetail3);
		}else{//还款
			fpWId = pbean.getlId();
			TProcessPrintDetail tProcessPrintDetail0 = getJKJG(checkList, fpWId);
			TProcessPrintDetail tProcessPrintDetail1 = getCNG(checkList, fpWId);
			tProcessPrintDetail0.setfRoleName("会计岗");
			tProcessPrintDetail1 .setfRoleName("出纳岗");
			tProcessPrintDetail0.setFpWId(null);
			tProcessPrintDetail1.setFpWId(null);
			tProcessPrintDetail0.setFpWIPid(fpWId);
			tProcessPrintDetail1.setFpWIPid(fpWId);
			detailList.add(tProcessPrintDetail0);
			detailList.add(tProcessPrintDetail1);
		}
		for (int i = 0; i < detailList.size(); i++) {
			if("0".equals(type)){//借款Payment pbean, LoanBasicInfo bean
				detailList.get(i).setFTabName("LoanBasicInfo");
				detailList.get(i).setFTabId(bean.getlId());
				detailList.get(i).setFTabIdName("lId");
			}else{//还款
				detailList.get(i).setFTabName("Payment");
				detailList.get(i).setFTabId(Integer.valueOf(pbean.getId()));
				detailList.get(i).setFTabIdName("id");
			}
			detailList.get(i).setFpDId(shenTongMng.getSeq("process_print_detail_seq"));
			super.merge(detailList.get(i));
		}
		return detailList;
	}

	@Override
	public TProcessPrintDetail getCNG(List<TProcessCheck> checkList,
			Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		String userID = roleMng.getUserIdByRoleName("出纳岗");
		User userCNG = userMng.findById(userID);
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userCNG.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userCNG.getName());
				tProcessPrintDetail.setFuserId(userCNG.getId());
				tProcessPrintDetail.setFdeptId(userCNG.getDpID());
				tProcessPrintDetail.setFdeptName(userCNG.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
			}
		}
		tProcessPrintDetail.setfRoleName("出纳岗");
		tProcessPrintDetail.setFpWId(fpWId);
		return tProcessPrintDetail;
	}

	@Override
	public List<TProcessPrintDetail> arrangeNewCheckDetail(
			ApplicationBasicInfo bean, ReimbAppliBasicInfo rbean, String type,
			User user) throws Exception {

		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		Integer fpWId = null;
		if("0".equals(type)){//事前
			checkList = applycheckList(type, bean, user);
			for (int i = 0; i < checkList.size(); i++) {
				TProcessPrintDetail detailbean = new TProcessPrintDetail();
				TProcessCheck checkbean = checkList.get(i);
				detailbean.setFuserId(checkbean.getFuserId());
				detailbean.setFuserName(checkbean.getFuserName());
				User checkuser = userMng.findById(Integer.valueOf(checkbean.getFuserId()));
				detailbean.setFdeptId(checkuser.getDpID());
				detailbean.setFdeptName(checkuser.getDepartName());
				detailbean.setFcheckResult(checkbean.getFcheckResult());
				detailbean.setFcheckRemake(checkbean.getFcheckRemake());
				detailbean.setFcheckTime(checkbean.getFcheckTime());
				Role checkrole = roleMng.findById(Integer.valueOf(checkbean.getFroleId()));
				detailbean.setfRoleName(checkrole.getName());
				detailList.add(detailbean);
			}
			fpWId = bean.getgId();
			if(bean.getType().equals("2")||bean.getType().equals("3")||bean.getType().equals("5")||bean.getType().equals("7")){//会议/培训/接待/出国
				TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, bean, rbean,null,fpWId);
				//TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
				TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
				TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, bean, rbean,null,fpWId);
				TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
				detailList.add(tProcessPrintDetail0);
				//detailList.add(tProcessPrintDetail1);
				detailList.add(tProcessPrintDetail2);
				detailList.add(tProcessPrintDetail3);
				detailList.add(tProcessPrintDetail4);
			}else if(bean.getType().equals("4")){//差旅
				String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
				if("GWCX".equals(bean.getTravelType())){//公务出行
					strType = "GWCXSQ";
					if("8".equals(bean.getDept())){//如果是局领导处室
						TProcessPrintDetail tProcessPrintDetail0 = getJBBMR(checkList, type, bean, rbean,null,fpWId);
						detailList.add(tProcessPrintDetail0);
					}else{
						TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, bean, rbean,null,fpWId);
						TProcessPrintDetail tProcessPrintDetail1 = getJBCSFGJZ(checkList, type, bean, rbean,null,fpWId);
						detailList.add(tProcessPrintDetail0);
						detailList.add(tProcessPrintDetail1);
					}
				}else{//公务出差
					TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, bean, rbean,null,fpWId);
					//TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
					TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, bean, rbean,null,fpWId);
					TProcessPrintDetail tProcessPrintDetail4 = getJZ(checkList,fpWId);
					detailList.add(tProcessPrintDetail0);
					//detailList.add(tProcessPrintDetail1);
					detailList.add(tProcessPrintDetail3);
					detailList.add(tProcessPrintDetail4);
				}
			}else if(bean.getType().equals("6")){//用车
				
			}
		}else{//事后
			ApplicationBasicInfo applyBean = applyMng.findByCode(rbean.getgCode());
			checkList = reimbcheckList(type,rbean, user);
			fpWId = rbean.getrId();
			checkList = reimbcheckList(type, rbean, user);
			fpWId = rbean.getrId();
			if(!rbean.getType().equals("1")){//不是通用事项报销
				if("4".equals(rbean.getType())){
					if("GWCX".equals(applyBean.getTravelType()) && "8".equals(applyBean.getDept())){//公务出行
						//TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, applyBean, rbean,null,fpWId);
						TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
						TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
						//TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, applyBean, rbean,null,fpWId);
						TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
						//detailList.add(tProcessPrintDetail0);
						detailList.add(tProcessPrintDetail1);
						detailList.add(tProcessPrintDetail2);
						//detailList.add(tProcessPrintDetail3);
						detailList.add(tProcessPrintDetail4);
					}else{
						TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, applyBean, rbean,null,fpWId);
						TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
						TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
						//TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, applyBean, rbean,null,fpWId);
						TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
						detailList.add(tProcessPrintDetail0);
						detailList.add(tProcessPrintDetail1);
						detailList.add(tProcessPrintDetail2);
						//detailList.add(tProcessPrintDetail3);
						detailList.add(tProcessPrintDetail4);
					}
				}else{
					TProcessPrintDetail tProcessPrintDetail0 = getJBBMFZR(checkList, type, applyBean, rbean,null,fpWId);
					TProcessPrintDetail tProcessPrintDetail1 = getJKJG(checkList,fpWId);
					TProcessPrintDetail tProcessPrintDetail2 = getBGSFZR(checkList,fpWId);
					//TProcessPrintDetail tProcessPrintDetail3 = getJBCSFGJZ(checkList, type, applyBean, rbean,null,fpWId);
					TProcessPrintDetail tProcessPrintDetail4 = getFGCWJZ(checkList,fpWId);
					detailList.add(tProcessPrintDetail0);
					detailList.add(tProcessPrintDetail1);
					detailList.add(tProcessPrintDetail2);
					//detailList.add(tProcessPrintDetail3);
					detailList.add(tProcessPrintDetail4);
				}
			}
		}
		for (int i = 0; i < detailList.size(); i++) {
			if("0".equals(type)){//事前
				detailList.get(i).setFTabName("ApplicationBasicInfo");
				detailList.get(i).setFTabId(bean.getgId());
				detailList.get(i).setFTabIdName("gId");
			}else{
				detailList.get(i).setFTabName("ReimbAppliBasicInfo");
				detailList.get(i).setFTabId(rbean.getrId());
				detailList.get(i).setFTabIdName("rId");
			}
			super.merge(detailList.get(i));
		}
		return detailList;
	
	}

	@Override
	public List<TProcessPrintDetail> arrangeContractDetail(ContractBasicInfo bean) throws Exception {

		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", bean.getfDeptId());
		//历史审批节点
		String historyNodes=tProcessCheckMng.getHistoryNodes("HTND",bean.getfDeptId(),bean.getBeanCode());
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
		checkList = listTProcessChecks;
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getfDeptId());
		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
		//第一个审签人经办部门负责人
		TProcessPrintDetail tProcessPrintDetail0 = new TProcessPrintDetail();
		TProcessPrintDetail tProcessPrintDetail1 = new TProcessPrintDetail();
		TProcessPrintDetail tProcessPrintDetail2 = new TProcessPrintDetail();
		TProcessPrintDetail tProcessPrintDetail3 = new TProcessPrintDetail();
		if(bean.getfOperatorId().equals(userBM.getId())){//申报人是部门负责人
			tProcessPrintDetail0 .setFuserName(bean.getfOperator());
			tProcessPrintDetail0.setFuserId(bean.getfOperatorId());
			tProcessPrintDetail0.setFdeptId(bean.getfDeptId());
			tProcessPrintDetail0.setFdeptName(bean.getfDeptName());
			tProcessPrintDetail0.setFcheckResult("1");
			tProcessPrintDetail0.setFcheckRemake("同意");
			tProcessPrintDetail0.setFcheckTime(new Date());
		}else{//申报人不是部门负责人
			for (int i = 0; i < checkList.size(); i++) {
				TProcessCheck checkbean = checkList.get(i);
				if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
					tProcessPrintDetail0.setFuserName(userBM.getName());
					tProcessPrintDetail0.setFuserId(userBM.getId());
					tProcessPrintDetail0.setFdeptId(userBM.getDpID());
					tProcessPrintDetail0.setFdeptName(userBM.getDepartName());
					tProcessPrintDetail0.setFcheckResult("1");
					tProcessPrintDetail0.setFcheckRemake(checkbean.getFcheckRemake());
					tProcessPrintDetail0.setFcheckTime(checkbean.getFcheckTime());
					if(tProcessPrintDetail0.getFcheckTime() !=null){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						String time =fmt.format(tProcessPrintDetail0.getFcheckTime());     // 转换成 X年X月X日
						tProcessPrintDetail0.setFcheckTimes(time);
					}
				}
			}
		}
		tProcessPrintDetail0.setfRoleName("经办人处室负责人");
		//寻找合同法审岗
		User fsuser = userMng.getUserByRoleIdAndDepartId("2e6ce8de-834d-11e9-b85d-005056a17ba3","2");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(fsuser.getId().equals(checkbean.getFuserId())){//匹配到相同的合同法审岗
				tProcessPrintDetail1.setFuserName(fsuser.getName());
				tProcessPrintDetail1.setFuserId(fsuser.getId());
				tProcessPrintDetail1.setFdeptId(fsuser.getDpID());
				tProcessPrintDetail1.setFdeptName(fsuser.getDepartName());
				tProcessPrintDetail1.setFcheckResult("1");
				tProcessPrintDetail1.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail1.setFcheckTime(checkbean.getFcheckTime());
				if(tProcessPrintDetail1.getFcheckTime() !=null){
					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
					String time =fmt.format(tProcessPrintDetail1.getFcheckTime());     // 转换成 X年X月X日
					tProcessPrintDetail1.setFcheckTimes(time);
				}
			}else {
				if(bean.getfOperatorId().equals(fsuser.getId())){//判断发起人是不是法审岗
					tProcessPrintDetail1.setFuserName(fsuser.getName());
					tProcessPrintDetail1.setFuserId(fsuser.getId());
					tProcessPrintDetail1.setFdeptId(fsuser.getDpID());
					tProcessPrintDetail1.setFdeptName(fsuser.getDepartName());
					tProcessPrintDetail1.setFcheckResult("1");
					tProcessPrintDetail1.setFcheckRemake("同意");
					tProcessPrintDetail1.setFcheckTime(bean.getfReqtIME());
					if(tProcessPrintDetail1.getFcheckTime() !=null){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						String time =fmt.format(tProcessPrintDetail1.getFcheckTime());     // 转换成 X年X月X日
						tProcessPrintDetail1.setFcheckTimes(time);
					}
				}else{//判断发起人是不是法审岗
					
				}
			}
		}
		tProcessPrintDetail1.setfRoleName("法律法规研究处合同法审岗");
		//寻找法律法规研究处部门负责人
		User fsfzuser = departMng.findByCode("102").getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(fsfzuser.getId().equals(checkbean.getFuserId())){//匹配到相同的合同法审岗
				tProcessPrintDetail3.setFuserName(fsfzuser.getName());
				tProcessPrintDetail3.setFuserId(fsfzuser.getId());
				tProcessPrintDetail3.setFdeptId(fsfzuser.getDpID());
				tProcessPrintDetail3.setFdeptName(fsfzuser.getDepartName());
				tProcessPrintDetail3.setFcheckResult("1");
				tProcessPrintDetail3.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail3.setFcheckTime(checkbean.getFcheckTime());
				if(tProcessPrintDetail3.getFcheckTime() !=null){
					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
					String time =fmt.format(tProcessPrintDetail3.getFcheckTime());     // 转换成 X年X月X日
					tProcessPrintDetail3.setFcheckTimes(time);
				}
			}else {
				if(bean.getfOperatorId().equals(fsfzuser.getId())){//判断发起人是不是法审岗
					tProcessPrintDetail3.setFuserName(fsfzuser.getName());
					tProcessPrintDetail3.setFuserId(fsfzuser.getId());
					tProcessPrintDetail3.setFdeptId(fsfzuser.getDpID());
					tProcessPrintDetail3.setFdeptName(fsfzuser.getDepartName());
					tProcessPrintDetail3.setFcheckResult("1");
					tProcessPrintDetail3.setFcheckRemake("同意");
					tProcessPrintDetail3.setFcheckTime(bean.getfReqtIME());
					if(tProcessPrintDetail3.getFcheckTime() !=null){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						String time =fmt.format(tProcessPrintDetail3.getFcheckTime());     // 转换成 X年X月X日
						tProcessPrintDetail3.setFcheckTimes(time);
					}
				}else{//判断发起人是不是法审岗
					
				}
			}
		}
		tProcessPrintDetail3.setfRoleName("法律法规研究处部门负责人");
		//经办部门分管局长
		User manageruser = new User();
		User userJBCSFGJZ = new User();
		userJBCSFGJZ = userMng.findById(bean.getUserId());
		manageruser = userJBCSFGJZ.getDepart().getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(manageruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail2.setFuserName(manageruser.getName());
				tProcessPrintDetail2.setFuserId(manageruser.getId());
				tProcessPrintDetail2.setFdeptId(manageruser.getDpID());
				tProcessPrintDetail2.setFdeptName(manageruser.getDepartName());
				tProcessPrintDetail2.setFcheckResult("1");
				tProcessPrintDetail2.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail2.setFcheckTime(checkbean.getFcheckTime());
				if(tProcessPrintDetail2.getFcheckTime() !=null){
					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
					String time =fmt.format(tProcessPrintDetail2.getFcheckTime());     // 转换成 X年X月X日
					tProcessPrintDetail2.setFcheckTimes(time);
				}
			}
		}
		tProcessPrintDetail2.setfRoleName("业务分管副局长");
		detailList.add(tProcessPrintDetail0);
		detailList.add(tProcessPrintDetail1);
		detailList.add(tProcessPrintDetail3);
		detailList.add(tProcessPrintDetail2);
		
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setFpDId(shenTongMng.getSeq("process_print_detail_seq"));
			detailList.get(i).setFTabName("ContractBasicInfo");
			detailList.get(i).setFTabId(bean.getFcId());
			detailList.get(i).setFTabIdName("fcId");
			super.merge(detailList.get(i));
		}
		return detailList;
	}

	@Override
	public List<TProcessPrintDetail> arrangeUptContractDetail(Upt bean)
			throws Exception {

		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", bean.getfDeptID());
		//历史审批节点
		String historyNodes=tProcessCheckMng.getHistoryNodes("HTBG",bean.getfDeptID(),bean.getBeanCode());
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
		checkList = listTProcessChecks;
		//需要返回的审签数据
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getfDeptID());
		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
		//第一个审签人经办部门负责人
		TProcessPrintDetail tProcessPrintDetail0 = new TProcessPrintDetail();
		TProcessPrintDetail tProcessPrintDetail1 = new TProcessPrintDetail();
		TProcessPrintDetail tProcessPrintDetail2 = new TProcessPrintDetail();
		TProcessPrintDetail tProcessPrintDetail3 = new TProcessPrintDetail();
		
		if(bean.getfOperatorID().equals(userBM.getId())){//申报人是部门负责人
			tProcessPrintDetail0 .setFuserName(bean.getfOperator());
			tProcessPrintDetail0.setFuserId(bean.getfOperatorID());
			tProcessPrintDetail0.setFdeptId(bean.getfDeptID());
			tProcessPrintDetail0.setFdeptName(bean.getfDeptName());
			tProcessPrintDetail0.setFcheckResult("1");
			tProcessPrintDetail0.setFcheckRemake("同意");
			tProcessPrintDetail0.setFcheckTime(new Date());
		}else{//申报人不是部门负责人
			for (int i = 0; i < checkList.size(); i++) {
				TProcessCheck checkbean = checkList.get(i);
				if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
					tProcessPrintDetail0.setFuserName(userBM.getName());
					tProcessPrintDetail0.setFuserId(userBM.getId());
					tProcessPrintDetail0.setFdeptId(userBM.getDpID());
					tProcessPrintDetail0.setFdeptName(userBM.getDepartName());
					tProcessPrintDetail0.setFcheckResult("1");
					tProcessPrintDetail0.setFcheckRemake(checkbean.getFcheckRemake());
					tProcessPrintDetail0.setFcheckTime(checkbean.getFcheckTime());
					if(tProcessPrintDetail0.getFcheckTime() !=null){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						String time =fmt.format(tProcessPrintDetail0.getFcheckTime());     // 转换成 X年X月X日
						tProcessPrintDetail0.setFcheckTimes(time);
					}
				}
			}
		}
		tProcessPrintDetail0.setfRoleName("经办人处室负责人");

		//寻找合同法审岗
		User fsuser = userMng.getUserByRoleIdAndDepartId("2e6ce8de-834d-11e9-b85d-005056a17ba3","2");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(fsuser.getId().equals(checkbean.getFuserId())){//匹配到相同的合同法审岗
				tProcessPrintDetail1.setFuserName(fsuser.getName());
				tProcessPrintDetail1.setFuserId(fsuser.getId());
				tProcessPrintDetail1.setFdeptId(fsuser.getDpID());
				tProcessPrintDetail1.setFdeptName(fsuser.getDepartName());
				tProcessPrintDetail1.setFcheckResult("1");
				tProcessPrintDetail1.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail1.setFcheckTime(checkbean.getFcheckTime());
				if(tProcessPrintDetail1.getFcheckTime() !=null){
					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
					String time =fmt.format(tProcessPrintDetail1.getFcheckTime());     // 转换成 X年X月X日
					tProcessPrintDetail1.setFcheckTimes(time);
				}
			}
		}
		tProcessPrintDetail1.setfRoleName("法律法规研究处合同法审岗");
		
		//寻找装备财务处会计岗
		User userKJ = userMng.getUserByRoleIdAndDepartId("45fff390-396c-11eb-b712-005056a17ba3","34");
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userKJ.getId().equals(checkbean.getFuserId())){//匹配到相同的合同法审岗
				tProcessPrintDetail3.setFuserName(userKJ.getName());
				tProcessPrintDetail3.setFuserId(userKJ.getId());
				tProcessPrintDetail3.setFdeptId(userKJ.getDpID());
				tProcessPrintDetail3.setFdeptName(userKJ.getDepartName());
				tProcessPrintDetail3.setFcheckResult("1");
				tProcessPrintDetail3.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail3.setFcheckTime(checkbean.getFcheckTime());
				if(tProcessPrintDetail3.getFcheckTime() !=null){
					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
					String time =fmt.format(tProcessPrintDetail3.getFcheckTime());     // 转换成 X年X月X日
					tProcessPrintDetail3.setFcheckTimes(time);
				}
			}
		}
		tProcessPrintDetail3.setfRoleName("装备财务处会计岗");
				
		
		
		
		//经办部门分管局长
		User manageruser = new User();
		User userJBCSFGJZ = new User();
		userJBCSFGJZ = userMng.findById(bean.getUserId());
		manageruser = userJBCSFGJZ.getDepart().getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(manageruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail2.setFuserName(manageruser.getName());
				tProcessPrintDetail2.setFuserId(manageruser.getId());
				tProcessPrintDetail2.setFdeptId(manageruser.getDpID());
				tProcessPrintDetail2.setFdeptName(manageruser.getDepartName());
				tProcessPrintDetail2.setFcheckResult("1");
				tProcessPrintDetail2.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail2.setFcheckTime(checkbean.getFcheckTime());
				if(tProcessPrintDetail2.getFcheckTime() !=null){
					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
					String time =fmt.format(tProcessPrintDetail2.getFcheckTime());     // 转换成 X年X月X日
					tProcessPrintDetail2.setFcheckTimes(time);
				}
			}
		}
		tProcessPrintDetail2.setfRoleName("业务分管副局长");
		
		//寻找局长办公会会议纪要岗
//		User userDWH = userMng.getUserByRoleIdAndDepartId("d3cf7b29-e9cd-11ea-86d1-005056a17ba3","1");
//		for (int i = 0; i < checkList.size(); i++) {
//			TProcessCheck checkbean = checkList.get(i);
//			if(userDWH.getId().equals(checkbean.getFuserId())){//匹配到相同的合同法审岗
//				tProcessPrintDetail4.setFuserName(userDWH.getName());
//				tProcessPrintDetail4.setFuserId(userDWH.getId());
//				tProcessPrintDetail4.setFdeptId(userDWH.getDpID());
//				tProcessPrintDetail4.setFdeptName(userDWH.getDepartName());
//				tProcessPrintDetail4.setFcheckResult("1");
//				tProcessPrintDetail4.setFcheckRemake(checkbean.getFcheckRemake());
//				tProcessPrintDetail4.setFcheckTime(checkbean.getFcheckTime());
//				if(tProcessPrintDetail4.getFcheckTime() !=null){
//					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
//					String time =fmt.format(tProcessPrintDetail4.getFcheckTime());     // 转换成 X年X月X日
//					tProcessPrintDetail4.setFcheckTimes(time);
//				}
//			}
//		}
//		tProcessPrintDetail4.setfRoleName("局长办公会会议纪要岗");
		
		
		
		detailList.add(tProcessPrintDetail0);
		detailList.add(tProcessPrintDetail1);
		detailList.add(tProcessPrintDetail3);
		detailList.add(tProcessPrintDetail2);
//		detailList.add(tProcessPrintDetail4);
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setFpDId(shenTongMng.getSeq("process_print_detail_seq"));
			detailList.get(i).setFTabName("Upt");
			detailList.get(i).setFTabId(bean.getfId_U());
			detailList.get(i).setFTabIdName("fId_U");
			super.merge(detailList.get(i));
		}
		return detailList;
	}

	@Override
	public TProcessPrintDetail getsSWGLG(List<TProcessCheck> checkList) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userSWGLG = new User(); 
		String userid =  roleMng.getUserIdByRoleName("实物管理岗");
		userSWGLG = userMng.findById(userid);
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(userSWGLG.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFuserName(userSWGLG.getName());
				tProcessPrintDetail.setFuserId(userSWGLG.getId());
				tProcessPrintDetail.setFdeptId(userSWGLG.getDpID());
				tProcessPrintDetail.setFdeptName(userSWGLG.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
			}
		}
		tProcessPrintDetail.setfRoleName("实物管理岗");
		return tProcessPrintDetail;
	}

	@Override
	public List<TProcessPrintDetail> arrangeReceFixedCheckDetail(Rece bean,	User user) throws Exception {
		List<TProcessPrintDetail> ppdList = new ArrayList<TProcessPrintDetail>();
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		TProcessPrintDetail TProcessPrintDetail = new TProcessPrintDetail();
		TProcessPrintDetail TProcessPrintDetail0 = new TProcessPrintDetail();
		TProcessPrintDetail TProcessPrintDetail1 = new TProcessPrintDetail();
		List<TProcessCheck> listTProcessChecks = new ArrayList<TProcessCheck>();
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCLY", bean.getfReqDeptID());
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
		checkList = listTProcessChecks;
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getfReqDeptID());
		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
		if(bean.getfReqUserid().equals(userBM.getId())){//申报人是部门负责人
			TProcessPrintDetail.setFuserName(bean.getfReqUser());
			TProcessPrintDetail.setFuserId(bean.getfReqUserid());
			TProcessPrintDetail.setFdeptId(bean.getfReqDeptID());
			TProcessPrintDetail.setFdeptName(bean.getfReqDept());
			TProcessPrintDetail.setFcheckResult("1");
			TProcessPrintDetail.setFcheckRemake("同意");
			TProcessPrintDetail.setFcheckTime(new Date());
		}else{//申报人不是部门负责人
			for (int i = 0; i < checkList.size(); i++) {
				TProcessCheck checkbean = checkList.get(i);
				if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
					TProcessPrintDetail.setFuserName(userBM.getName());
					TProcessPrintDetail.setFuserId(userBM.getId());
					TProcessPrintDetail.setFdeptId(userBM.getDpID());
					TProcessPrintDetail.setFdeptName(userBM.getDepartName());
					TProcessPrintDetail.setFcheckResult("1");
					TProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
					TProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				}
			}
		}
		TProcessPrintDetail.setfRoleName("经办处室处室负责人");
		
		//经办处室分管局长
		User manageruser = new User();
		User userJBCSFGJZ = new User();
		userJBCSFGJZ = userBM;
		manageruser = userJBCSFGJZ.getDepart().getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(manageruser.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				TProcessPrintDetail0.setFuserName(manageruser.getName());
				TProcessPrintDetail0.setFuserId(manageruser.getId());
				TProcessPrintDetail0.setFdeptId(manageruser.getDpID());
				TProcessPrintDetail0.setFdeptName(manageruser.getDepartName());
				TProcessPrintDetail0.setFcheckResult("1");
				TProcessPrintDetail0.setFcheckRemake(checkbean.getFcheckRemake());
				TProcessPrintDetail0.setFcheckTime(checkbean.getFcheckTime());
			}
		}
		TProcessPrintDetail0.setfRoleName("经办处室分管局长");
		
		//获取实物管理岗
		TProcessPrintDetail1 = getsSWGLG(checkList);

		ppdList.add(TProcessPrintDetail);
		ppdList.add(TProcessPrintDetail0);
		ppdList.add(TProcessPrintDetail1);
		for (int i = 0; i < ppdList.size(); i++) {
			ppdList.get(i).setFTabName("Rece");
			ppdList.get(i).setFTabId(bean.getfId_R());
			ppdList.get(i).setFTabIdName("fId_R");
			super.merge(ppdList.get(i));
		}
		return ppdList;
	}

	@Override
	public List<TProcessPrintDetail> findbytab(String FTabName,
			String FTabIdName, Integer FTabId) {
		Finder finder =Finder.create(" FROM TProcessPrintDetail WHERE FTabName='"+FTabName+"' AND FTabIdName='"+FTabIdName+"' AND FTabId="+FTabId);
		List<TProcessPrintDetail> listTProcessChecks = super.find(finder);
		for (TProcessPrintDetail check : listTProcessChecks) {
			if(check.getFcheckTime() !=null){
				DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
				String time =fmt.format(check.getFcheckTime());     // 转换成 X年X月X日
				check.setFcheckTimes(time);	
			}
		}
		return listTProcessChecks;
	}

	@Override
	public List<TProcessPrintDetail> arrangeAccountsCurrentDetail(
			AccountsCurrent bean) throws Exception {
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WLKLXSQ", bean.getDeptId());
		//历史审批节点
		String historyNodes=tProcessCheckMng.getHistoryNodes("WLKLXSQ",bean.getDeptId(),bean.getBeanCode());
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
		checkList = listTProcessChecks;
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getDeptId());
		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
		
		//第一个审签人经办部门负责人
		TProcessPrintDetail tProcessPrintDetail0 = new TProcessPrintDetail();
		TProcessPrintDetail tProcessPrintDetail1 = new TProcessPrintDetail();
		
		if(bean.getUserId().equals(userBM.getId())){//申报人是部门负责人
			tProcessPrintDetail0 .setFuserName(bean.getUserName());
			tProcessPrintDetail0.setFuserId(bean.getUserId());
			tProcessPrintDetail0.setFdeptId(bean.getDeptId());
			tProcessPrintDetail0.setFdeptName(bean.getDeptName());
			tProcessPrintDetail0.setFcheckResult("1");
			tProcessPrintDetail0.setFcheckRemake("同意");
			tProcessPrintDetail0.setFcheckTime(new Date());
		}else{//申报人不是部门负责人
			for (int i = 0; i < checkList.size(); i++) {
				TProcessCheck checkbean = checkList.get(i);
				if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
					tProcessPrintDetail0.setFuserName(userBM.getName());
					tProcessPrintDetail0.setFuserId(userBM.getId());
					tProcessPrintDetail0.setFdeptId(userBM.getDpID());
					tProcessPrintDetail0.setFdeptName(userBM.getDepartName());
					tProcessPrintDetail0.setFcheckResult("1");
					tProcessPrintDetail0.setFcheckRemake(checkbean.getFcheckRemake());
					tProcessPrintDetail0.setFcheckTime(checkbean.getFcheckTime());
					if(tProcessPrintDetail0.getFcheckTime() !=null){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						String time =fmt.format(tProcessPrintDetail0.getFcheckTime());     // 转换成 X年X月X日
						tProcessPrintDetail0.setFcheckTimes(time);
					}
				}
			}
		}
		tProcessPrintDetail0.setfRoleName("经办人处室负责人");
		
		//寻找装备财务处处室负责人
				User userKJ = userMng.getUserByRoleIdAndDepartId("0650097c-3968-11eb-b712-005056a17ba3","34");
				for (int i = 0; i < checkList.size(); i++) {
					TProcessCheck checkbean = checkList.get(i);
					if(userKJ.getId().equals(checkbean.getFuserId())){//匹配到相同的合同法审岗
						tProcessPrintDetail1.setFuserName(userKJ.getName());
						tProcessPrintDetail1.setFuserId(userKJ.getId());
						tProcessPrintDetail1.setFdeptId(userKJ.getDpID());
						tProcessPrintDetail1.setFdeptName(userKJ.getDepartName());
						tProcessPrintDetail1.setFcheckResult("1");
						tProcessPrintDetail1.setFcheckRemake(checkbean.getFcheckRemake());
						tProcessPrintDetail1.setFcheckTime(checkbean.getFcheckTime());
						if(tProcessPrintDetail1.getFcheckTime() !=null){
							DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
							String time =fmt.format(tProcessPrintDetail1.getFcheckTime());     // 转换成 X年X月X日
							tProcessPrintDetail1.setFcheckTimes(time);
						}
					}
				}
				tProcessPrintDetail1.setfRoleName("装备财务处处室负责人");
				detailList.add(tProcessPrintDetail0);
				detailList.add(tProcessPrintDetail1);
				
				for (int i = 0; i < detailList.size(); i++) {
					detailList.get(i).setFpDId(shenTongMng.getSeq("process_print_detail_seq"));
					detailList.get(i).setFTabName("AccountsCurrent");
					detailList.get(i).setFTabId(bean.getfAcaId());
					detailList.get(i).setFTabIdName("fAcaId");
					super.merge(detailList.get(i));
				}
				return detailList;
	}

	@Override
	public List<TProcessPrintDetail> arrangeAccountsRegisterDetail(
			AccountsRegister bean) throws Exception {
		List<TProcessCheck> checkList = new ArrayList<TProcessCheck>();
		List<TProcessPrintDetail> detailList = new ArrayList<TProcessPrintDetail>();
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WLKDJ", bean.getDeptId());
		//历史审批节点
		String historyNodes=tProcessCheckMng.getHistoryNodes("WLKDJ",bean.getDeptId(),bean.getBeanCode());
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
		checkList = listTProcessChecks;
		//判断申报人是不是部门负责人
		Depart dept = departMng.findById(bean.getDeptId());
		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", dept.getName());
		
		//第一个审签人经办部门负责人
		TProcessPrintDetail tProcessPrintDetail0 = new TProcessPrintDetail();
//		TProcessPrintDetail tProcessPrintDetail1 = new TProcessPrintDetail();
		
		if(bean.getUserId().equals(userBM.getId())){//申报人是部门负责人
			tProcessPrintDetail0 .setFuserName(bean.getUserName());
			tProcessPrintDetail0.setFuserId(bean.getUserId());
			tProcessPrintDetail0.setFdeptId(bean.getDeptId());
			tProcessPrintDetail0.setFdeptName(bean.getDeptName());
			tProcessPrintDetail0.setFcheckResult("1");
			tProcessPrintDetail0.setFcheckRemake("同意");
			tProcessPrintDetail0.setFcheckTime(new Date());
		}else{//申报人不是部门负责人
			for (int i = 0; i < checkList.size(); i++) {
				TProcessCheck checkbean = checkList.get(i);
				if(userBM.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
					tProcessPrintDetail0.setFuserName(userBM.getName());
					tProcessPrintDetail0.setFuserId(userBM.getId());
					tProcessPrintDetail0.setFdeptId(userBM.getDpID());
					tProcessPrintDetail0.setFdeptName(userBM.getDepartName());
					tProcessPrintDetail0.setFcheckResult("1");
					tProcessPrintDetail0.setFcheckRemake(checkbean.getFcheckRemake());
					tProcessPrintDetail0.setFcheckTime(checkbean.getFcheckTime());
					if(tProcessPrintDetail0.getFcheckTime() !=null){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						String time =fmt.format(tProcessPrintDetail0.getFcheckTime());     // 转换成 X年X月X日
						tProcessPrintDetail0.setFcheckTimes(time);
					}
				}
			}
		}
		tProcessPrintDetail0.setfRoleName("经办人处室负责人");
		
//		//寻找装备财务处会计岗
//				User userKJ = userMng.getUserByRoleIdAndDepartId("57c43a79-396b-11eb-b712-005056a17ba3","34");
//				for (int i = 0; i < checkList.size(); i++) {
//					TProcessCheck checkbean = checkList.get(i);
//					if(userKJ.getId().equals(checkbean.getFuserId())){//匹配到相同的合同法审岗
//						tProcessPrintDetail1.setFuserName(userKJ.getName());
//						tProcessPrintDetail1.setFuserId(userKJ.getId());
//						tProcessPrintDetail1.setFdeptId(userKJ.getDpID());
//						tProcessPrintDetail1.setFdeptName(userKJ.getDepartName());
//						tProcessPrintDetail1.setFcheckResult("1");
//						tProcessPrintDetail1.setFcheckRemake(checkbean.getFcheckRemake());
//						tProcessPrintDetail1.setFcheckTime(checkbean.getFcheckTime());
//						if(tProcessPrintDetail1.getFcheckTime() !=null){
//							DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
//							String time =fmt.format(tProcessPrintDetail1.getFcheckTime());     // 转换成 X年X月X日
//							tProcessPrintDetail1.setFcheckTimes(time);
//						}
//					}
//				}
//				tProcessPrintDetail1.setfRoleName("出纳岗");
				detailList.add(tProcessPrintDetail0);
//				detailList.add(tProcessPrintDetail1);
				
				for (int i = 0; i < detailList.size(); i++) {
					detailList.get(i).setFpDId(shenTongMng.getSeq("process_print_detail_seq"));
					detailList.get(i).setFTabName("AccountsRegister");
					detailList.get(i).setFTabId(bean.getfMSId());
					detailList.get(i).setFTabIdName("fMSId");
					super.merge(detailList.get(i));
				}
				return detailList;
	}
	
	
	/**
	 * 获取政治部人事警务处处长和行政保证处处长和相关部门负责人
	 * @param checkList
	 * @param bean
	 * @param fpWId
	 * @param type
	 * @author 赵孟雷
	 * @createtime 2021年4月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年4月16日
	 */
	@Override
	public TProcessPrintDetail getDepartment(List<TProcessCheck> checkList, DirectlyReimbAppliBasicInfo bean, Integer fpWId, String type) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		User userBGSFZR = null;
		if ("ZJBXLX-0".equals(bean.getDirType())) {
			userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", "政治部人事警务处");
		}
		if ("ZJBXLX-1".equals(bean.getDirType())) {
			userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", "行政保障处");
		}
		
		if ("ZJBXLX-2".equals(bean.getDirType())) {
			userBGSFZR = userMng.getUserByRoleNameAndDepartName("处室负责人", bean.getDeptName());
		}
		
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
				checkList.remove(i);
				break;
			}
		}
		tProcessPrintDetail.setfRoleName("经办人处室负责人");
		tProcessPrintDetail.setFpWdDd(fpWId);
		return tProcessPrintDetail;
	}
	
	/**
	 * 获取政治部人事警务处和行政保证处分管局长
	 * @param checkList
	 * @param fpWId
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年4月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年4月16日
	 */
	@Override
	public TProcessPrintDetail getCorrelationDirector(List<TProcessCheck> checkList, DirectlyReimbAppliBasicInfo bean,Integer fpWId) {
		TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
		List<Depart> departList = new ArrayList<Depart>();
		if ("ZJBXLX-0".equals(bean.getDirType())) {
			departList = departMng.findByProperty("name", "政治部人事警务处");
			tProcessPrintDetail.setfRoleName("业务分管副局长");
		}
		if ("ZJBXLX-1".equals(bean.getDirType())) {
			departList = departMng.findByProperty("name", "行政保障处");
			tProcessPrintDetail.setfRoleName("业务分管副局长");
		}
		
		Depart depart = departList.get(0);
		User officeDirector = depart.getManager();
		for (int i = 0; i < checkList.size(); i++) {
			TProcessCheck checkbean = checkList.get(i);
			if(officeDirector.getId().equals(checkbean.getFuserId())){//匹配到相同的审批人
				tProcessPrintDetail.setFcheckRemake(checkbean.getFcheckRemake());
				tProcessPrintDetail.setFuserName(officeDirector.getName());
				tProcessPrintDetail.setFuserId(officeDirector.getId());
				tProcessPrintDetail.setFdeptId(officeDirector.getDpID());
				tProcessPrintDetail.setFdeptName(officeDirector.getDepartName());
				tProcessPrintDetail.setFcheckResult("1");
				tProcessPrintDetail.setFcheckTime(checkbean.getFcheckTime());
				checkList.remove(i);
				break;
			}
		}

		tProcessPrintDetail.setFpWdDd(fpWId);
		return tProcessPrintDetail;
		
	}
}
