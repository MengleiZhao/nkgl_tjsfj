package com.braker.icontrol.contract.Formulation.manager.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.DateUtil;
import com.braker.common.util.LookupsUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.Attac;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class FormulationMngImpl extends BaseManagerImpl<ContractBasicInfo> implements FormulationMng {

	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private ReceivPlanMng receivPlanMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	@Override
	public Pagination queryList(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize,String searchData) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE (fFlowStauts='0' or fFlowStauts='1' or fFlowStauts='-4' or fFlowStauts='9' or fFlowStauts='-1') AND fContStauts <> '99' AND fOperator = '"+user.getName()+"'");

		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append(" AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append(" AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfFlowStauts())){
			/*if(contractBasicInfo.getfFlowStauts().equals("??????")){
				contractBasicInfo.setfFlowStauts("0");
			}
			if(contractBasicInfo.getfFlowStauts().equals("?????????")){
				contractBasicInfo.setfFlowStauts("1");
			}*/
			finder.append(" AND fFlowStauts LIKE :fFlowStauts ");
			finder.setParam("fFlowStauts", "%"+contractBasicInfo.getfFlowStauts()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcAmount())){
			finder.append(" AND fcAmount LIKE :fcAmount");
			finder.setParam("fcAmount", "%"+contractBasicInfo.getFcAmount()+"%");
		}
		/*if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getcAmountBegin()))){
			finder.append(" AND fcAmount >='"+contractBasicInfo.getcAmountBegin()+"'");
		}
		if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getcAmountEnd()))){
			finder.append(" AND fcAmount <='"+contractBasicInfo.getcAmountEnd()+"'");
		}*/
		/*if(contractBasicInfo.getfReqtIME()!=null){
			finder.append(" and datediff(fReqtIME,'"+contractBasicInfo.getfReqtIME()+"')=0 ");
		}*/
		if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getfPurchNo()))){
			finder.append(" AND fPurchNo = '"+contractBasicInfo.getfPurchNo()+"'");
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fcCode like '%"+searchData+"%' or fcTitle  like '%"+searchData+"%' or fcAmount like '%"+searchData+"%' or to_date(fReqtIME, 'yyyy-mm-dd') like '%"+searchData+"%')  ");
		}
//		if(!user.getRoleName().contains("???????????????")){
//			String deptIdStr=departMng.getDeptIdStrByUser(user);
//	 		if("".equals(deptIdStr)){ //?????????????????????????????????
//	 			finder.append(" AND fOperatorId =:fOperatorId ").setParam("fOperatorId", user.getId());
//	 		}else if("all".equals(deptIdStr)){//??????????????????????????????
//	 			
//	 		}else{//?????????????????????????????????????????? ?????????????????????????????????????????????
//	 			finder.append(" AND fDeptId in ("+deptIdStr+")");
//	 		}
//		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public Pagination queryListJsonSeal(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' and fsealedStatus='1' and fIncomeStauts='1' and fPayStauts='0' AND fContStauts <> '99'");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append(" AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append(" AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfFlowStauts())){
			finder.append(" AND fFlowStauts LIKE :fFlowStauts ");
			finder.setParam("fFlowStauts", "%"+contractBasicInfo.getfFlowStauts()+"%");
		}
		if(!user.getRoleName().contains("???????????????")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //?????????????????????????????????
				finder.append(" AND fOperatorId =:fOperatorId ").setParam("fOperatorId", user.getId());
			}else if("all".equals(deptIdStr)){//??????????????????????????????
				
			}else{//?????????????????????????????????????????? ?????????????????????????????????????????????
				finder.append(" AND fDeptId in ("+deptIdStr+")");
			}
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	
	@Override
	public Pagination queryListJsonSealUpt(Upt upt,User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM Upt WHERE fUptFlowStauts='9' and fsealedStatus='1' and fIncomeStauts='1' and (fPayStauts='0' or fPayStauts is null ) ");
		if(!StringUtil.isEmpty(upt.getfContCode())){
			finder.append(" AND fContCode LIKE :fContCode ");
			finder.setParam("fContCode", "%"+upt.getfContCode()+"%");
		}
		if(!StringUtil.isEmpty(upt.getfContName())){
			finder.append(" AND fContName LIKE :fContName ");
			finder.setParam("fContName", "%"+upt.getfContName()+"%");
		}
		if(!user.getRoleName().contains("???????????????")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //?????????????????????????????????
				finder.append(" AND fOperatorID =:fOperatorID ").setParam("fOperatorID", user.getId());
			}else if("all".equals(deptIdStr)){//??????????????????????????????
				
			}else{//?????????????????????????????????????????? ?????????????????????????????????????????????
				finder.append(" AND fDeptID in ("+deptIdStr+")");
			}
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public void save_CBI(ContractBasicInfo cbiBean, String files,String filessqwts, SignInfo siBean, String planJson,String cgConfplanJson, User user,PurchaseApplyBasic cgbean) throws Exception{
		Date date = new Date();
		if(cbiBean.getFcId() == null){//??????
			//??????????????????????????????
			cbiBean.setCreator(user.getName());
			cbiBean.setCreateTime(date);
			//???????????????id
			cbiBean.setfOperatorId(user.getId());
			//??????????????????id
			cbiBean.setfDeptId(user.getDepart().getId());
			//???????????????????????????
			String fcCode = getFcCode(user);
			cbiBean.setFcCode(fcCode);
			cbiBean.setfReqtIME(new Date());
			Integer id =shenTongMng.getSeq("T_CONTRACT_BASIC_INFO_SEQ");
			cbiBean.setFcId(id);
		}else {//??????
			cbiBean.setUpdator(user.getName());
			cbiBean.setUpdateTime(date);
			cbiBean.setfReqtIME(new Date());
		}
		/*//??????????????????
		cbiBean.setfIsAuthor("0");*/
		/*//?????????????????? 
		contractBasicInfo.setfContStyle(fContStyle);*/
		
		//?????????????????????
		cbiBean.setfPayStauts("0");
		//??????????????????
		cbiBean.setfUpdateStatus("0");
		//??????????????????
		cbiBean.setfDisputeStatus("0");
		//??????????????????
		cbiBean.setFsealedStatus("0");
		//??????????????????
		cbiBean.setfToFilesStatus(0);
		//?????????????????????
		cbiBean.setfIncomeChangeStauts("0");
		
		//?????????????????? 
		Lookups fPayType = lookupsMng.findByLookCode(cbiBean.getfPayType().getCode());
		cbiBean.setfPayType(fPayType);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy???MM???dd???");
		cbiBean.setKeepTime(sdf.format(cbiBean.getfContStartTime())+"-"+sdf.format(cbiBean.getfContEndTime()));
		
		cbiBean.setfAllAmount("0");
		if(cbiBean.getFcType().equals("HTFL-01")){
			cbiBean.setfNotAllAmountL(String.valueOf(cbiBean.getFcAmount()));
		}else{
			cbiBean.setfNotAllAmountL("0");
			cbiBean.setFcAmount("0");
		}
		cbiBean = (ContractBasicInfo) super.merge(cbiBean);
		
		//??????
		if("1".equals(cbiBean.getfFlowStauts())){
			if(cbiBean.getFcType().equals("HTFL-01")){
				
				
				cgbean.setIsUsed("1");
				//?????????
				/*BiddingRegist gysbean = cgProcessMng.findbycode(cbiBean.getfContractor(), null);
				gysbean.setfContractstatus("1");*/
			}
			
			/*//???????????????????????????????????????????????????????????????????????????????????????????????????????????????
			if(contractBasicInfo.getFcType().equals("HTFL-01")){
				//??????????????????????????????????????????
				Double amount = Double.parseDouble(contractBasicInfo.getFcAmount());//????????????
				Double syAmount =budgetIndexMgrMng.addDjAmount(contractBasicInfo.getfBudgetIndexCode(),contractBasicInfo.getProDetailId(),amount);
				contractBasicInfo.setfAvailableAmount(syAmount+"");
			}*/
			
			//???????????????????????????key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), cbiBean.getJoinTable(), cbiBean.getBeanCodeField(), cbiBean.getBeanCode(), "HTND", user);
			TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("HTND", user.getDpID());
			Integer flowId = processDefin.getFPId();
			TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser = userMng.findById(node.getUserId());
			//?????????????????????????????????????????????????????????????????????/??????
			cbiBean.setfUserCode(nextUser.getId());
			cbiBean.setfUserName(nextUser.getName());
			//???????????????????????????
			cbiBean.setfNCode(firstKey+"");
			//????????????????????????????????????1?????????????????????
			tProcessCheckMng.updateStauts(flowId, cbiBean.getBeanCode());
			
			//???????????????????????????????????????
			PersonalWork work = new PersonalWork();
			work.setUserId(cbiBean.getfUserCode());//???????????????ID????????????????????????ID
			work.setTaskId(cbiBean.getFcId());//?????????ID
			work.setTaskCode(cbiBean.getFcCode());//?????????????????????
			work.setTaskName("[????????????]"+cbiBean.getFcTitle());//????????????????????????????????????+?????????????????????????????????????????????
			work.setUrl("/Approval/approve?id="+cbiBean.getFcId());//??????????????????????????????url,???????????????????????????????????????
			work.setUrl1("/Formulation/detail?id="+cbiBean.getFcId());//??????url
			work.setTaskStauts("0");//??????
			work.setType("1");//???????????????1-??????
			work.setTaskType("1");//???????????????1?????????
			work.setBeforeUser(user.getName());//?????????????????????
			work.setBeforeDepart(user.getDepartName());//?????????????????????????????????
			work.setBeforeTime(date);//??????????????????
			Integer workId =shenTongMng.getSeq("PERSONAL_WORKTABLE_SEQ");
			work.setfId(workId);
			personalWorkMng.merge(work);
			
			//?????????????????????????????????
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(user.getId());//???????????????ID????????????????????????ID
			minwork.setTaskId(cbiBean.getFcId());//?????????ID
			minwork.setTaskCode(cbiBean.getFcCode());//?????????????????????
			minwork.setTaskName("[????????????]"+cbiBean.getFcTitle());//????????????????????????????????????+?????????????????????????????????????????????
			minwork.setUrl("/Formulation/edit?id="+cbiBean.getFcId());//????????????url
			minwork.setUrl1("/Formulation/detail?id="+cbiBean.getFcId());//??????url
			minwork.setUrl2("/Formulation/delete?id="+cbiBean.getFcId());//????????????url
			minwork.setTaskStauts("2");//??????
			minwork.setType("2");//???????????????2-??????
			minwork.setTaskType("0");//???????????????0????????????
			minwork.setBeforeUser(user.getName());//?????????????????????
			minwork.setBeforeDepart(user.getDepartName());//?????????????????????????????????
			minwork.setBeforeTime(date);//??????????????????
			minwork.setFinishTime(date);
			Integer minworkId =shenTongMng.getSeq("PERSONAL_WORKTABLE_SEQ");
			minwork.setfId(minworkId);
			personalWorkMng.merge(minwork);
		}
		//????????????????????????
		attachmentMng.joinEntity(cbiBean, files);
		
		//??????????????????
		//????????????????????????????????????????????????????????????
		if("HTFL-01".equals(cbiBean.getFcType())){
			//??????json??????
			List<ReceivPlan> list2 = JSON.parseObject(planJson,new TypeReference<List<ReceivPlan>>(){});
			//?????????ReceivPlan?????????list
			List<ReceivPlan> receivPlan = list2;
			//List<ReceivPlan> receivPlan = filingMng.getReceivPlan(list2, user, cbiBean);
			List<ReceivPlan> Plan = filingMng.find_ReceivPlan_List(String.valueOf(cbiBean.getFcId()));
			//?????????????????????
			if(Plan.size() > 0){
				for (int i = 0; i < Plan.size(); i++) {
					receivPlanMng.deleteById(Plan.get(i).getfPlanId());
				}
			}
			//??????????????????????????????????????????
			if(receivPlan.size() > 0){
				for (ReceivPlan receivPlan2 : receivPlan) {
					receivPlan2.setfUpateTime_R(new Date());
					receivPlan2.setPayStauts("0");
					receivPlan2.setfStauts_R("0");
					receivPlan2.setfUpateUser_R(user.getAccountNo());
					receivPlan2.setfContId_R(cbiBean.getFcId());
					receivPlan2.setDataType(0);//??????????????????
					if(receivPlan2.getfPlanId()==null){
						Integer planId =shenTongMng.getSeq("T_RECEIV_PLAN_SEQ");
						receivPlan2.setfPlanId(planId);
					}
					receivPlanMng.merge(receivPlan2);
				}
			}else if(Plan.size() == 0){
				filingMng.save_ReceivPlan(receivPlan);
			}
			getSession().createSQLQuery("DELETE FROM T_CONTRACT_REGISTER_LIST WHERE F_CONT_ID='"+cbiBean.getFcId()+"'").executeUpdate();
			//?????????????????????????????????
			List<ContractRegisterList> contractRegisterList = new ArrayList<ContractRegisterList>();
			List<PurcMaterialRegisterList> registerlist = JSON.parseObject(cgConfplanJson,new TypeReference<List<PurcMaterialRegisterList>>(){});
			for (int i = 0; i < registerlist.size(); i++) {
				ContractRegisterList contractRegister = new ContractRegisterList();
				contractRegister.setFcId(cbiBean.getFcId());
//				contractRegister.setFbiddingCode(registerlist.get(i).getFbiddingCode());
//				contractRegister.setFmType(registerlist.get(i).getFmType());
//				contractRegister.setFmName(registerlist.get(i).getFmName());
//				contractRegister.setfBrand(registerlist.get(i).getfBrand());
//				contractRegister.setFmSpecif(registerlist.get(i).getFmSpecif());
//				contractRegister.setFmModel(registerlist.get(i).getFmModel());
//				contractRegister.setFpNum(registerlist.get(i).getFpNum());
//				contractRegister.setFamount(registerlist.get(i).getFamount());
//				contractRegister.setFsignPrice(registerlist.get(i).getFsignPrice());
//				contractRegister.setfRemark(registerlist.get(i).getfRemark());
//				contractRegister.setfWhetherImport(registerlist.get(i).getfWhetherImport());
				contractRegister.setNewKind(registerlist.get(i).getFpKind());
				contractRegister.setNewName(registerlist.get(i).getFpurName());
				contractRegister.setNewNum(registerlist.get(i).getFnum());
				contractRegister.setNewUnit(registerlist.get(i).getFmeasureUnit());
				contractRegister.setNewIsImp(registerlist.get(i).getfIsImp());
				contractRegister.setNewCompro(registerlist.get(i).getFcommProp());
				contractRegister.setfCheckedNum(0);
				contractRegister.setfDataType(0);
				Integer cRId  =shenTongMng.getSeq("T_CONTRACT_REGISTER_LIST_SEQ");
				contractRegister.setcRId(cRId);
				contractRegister = (ContractRegisterList) super.merge(contractRegister);
				contractRegisterList.add(contractRegister);
			}
		}
		List<String> files2 = new ArrayList<String>();
		files2.add(filessqwts);
		filingMng.save_SignInfo(siBean, user, cbiBean, files2);
		
		cbiBean.setUpdator(user.getName());
		update(cbiBean);
	}

	@Override
	public void delete_CBI(Integer id, User user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql="UPDATE T_CONTRACT_BASIC_INFO SET F_CONT_STAUTS='99',F_UPDATE_USER='"+user.getAccountNo()+"', F_UPDATE_TIME='"+sdf.format(new Date())+"'  WHERE F_CONT_ID ='"+id+"'";
		Query  contStauts=getSession().createSQLQuery(sql);
		contStauts.executeUpdate();
		//???????????????????????????
		ContractBasicInfo cbi = findById(id);
		List<PersonalWork> worklost = personalWorkMng.findByCodeAndUser(cbi.getFcCode(), userMng.findById(cbi.getfOperatorId()));
		if(worklost.size()>0){
			for (int i = 0; i < worklost.size(); i++) {
				personalWorkMng.deleteById(Integer.valueOf(worklost.get(i).getfId()));
			}
		}
	}

	@Override
	public void save_attac(ContractBasicInfo contractBasicInfo,List<Attac> at, User user) {
		Query query =getSession().createSQLQuery("DELETE FROM T_CONTRACT_ATTAC WHERE F_CONT_CODE='"+contractBasicInfo.getFcCode()+"' AND F_ATTAC_TYPE='4'");
		query.executeUpdate();
		for (int i = 0; i < at.size(); i++) {
			if(!StringUtil.isEmpty(at.get(i).getfAttacName())){
				at.get(i).setfContId(contractBasicInfo.getFcId());
				at.get(i).setfContCode_A(contractBasicInfo.getFcCode());
				//????????????????????????
				at.get(i).setfAttacType("4");
				at.get(i).setfUploadTime(new Date());
				at.get(i).setfUpdateUser(user.getAccountNo());
				at.get(i).setfUpdateTime(new Date());
				super.saveOrUpdate(at.get(i));
			}
		}
	}
	
	@Override
	public List<Attac> findAttac(Integer id){
		Finder finder =Finder.create(" FROM Attac WHERE fAttacType='4' AND fContId="+id);
		return super.find(finder);
	}

	@Override
	public List<Attac> findAttacByName(String name) {
		Finder finder = Finder.create(" FROM Attac WHERE fAttacName='"+name+"'");
		return  super.find(finder);
	}

	@Override
	public void deleteAttac(List<Attac> attac) {
		for (int i = 0; i < attac.size(); i++) {
			super.delete(attac.get(i));
		}
	}
	
	@Override
	public List<Lookups> getLookupsJson(String categoryCode,String blanked,String selected) {
		List<Lookups> list=LookupsUtil.getLookupsSelect(categoryCode,blanked);
		return list;
		/*
		 * Finder hql=Finder.create("FROM Lookups WHERE flag='1' ");
		 * hql.append("  AND category.code =:pcode ").setParam("pcode", categoryCode);
		 * if(!StringUtil.isEmpty(blanked)){
		 * hql.append(" AND code<>:code").setParam("code", blanked); }
		 * hql.append(" ORDER BY convert(orderNo,DECIMAL)"); return super.find(hql);
		 */
	}

	@Override
	public ContractBasicInfo findAttacByFPurchNo(String fPurchNo) {
		Finder finder=Finder.create(" FROM ContractBasicInfo WHERE fPurchNo='"+fPurchNo+"'");
		List<ContractBasicInfo> list= super.find(finder);
		if(list==null || list.size()==0){
			return new ContractBasicInfo();
		}else{
			return (ContractBasicInfo) super.find(finder).get(0);
		}
	}

	@Override
	public Pagination find(ContractBasicInfo cbi,User user,Integer pageNo, Integer pageSize) {
		Finder finder=Finder.create(" FROM ContractBasicInfo WHERE fContStauts in(1,7,9,11) AND fFlowStauts='9'");
		finder.append("AND fOperator =:fOperator ");
		finder.setParam("fOperator", user.getName());
		finder.append("AND fDeptName =:fDeptName ");
		finder.setParam("fDeptName", user.getDepart().getName());
		if(!StringUtil.isEmpty(cbi.getFcCode())){
			finder.append("AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+cbi.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(cbi.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+cbi.getFcTitle()+"%");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public Pagination queryForChange(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fContStauts <> '99' AND fContStauts <> '-1' AND fContStauts <> '5' AND fUpdateStatus='0' and fToFilesStatus ='1' ");
		finder.append("AND fOperator =:fOperator ");
		finder.setParam("fOperator", user.getName());
		finder.append("AND fDeptName =:fDeptName ");
		finder.setParam("fDeptName", user.getDepart().getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append("AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfFlowStauts())){
			finder.append("AND fFlowStauts LIKE :fFlowStauts ");
			finder.setParam("fFlowStauts", "%"+contractBasicInfo.getfFlowStauts()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFsealedStatus())){
			finder.append("AND fsealedStatus =:fsealedStatus ");
			finder.setParam("fsealedStatus", contractBasicInfo.getFsealedStatus());
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public Pagination queryForEnding(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fContStauts <> (99,5)");
		finder.append("AND fOperator =:fOperator ");
		finder.setParam("fOperator", user.getName());
		finder.append("AND fDeptName =:fDeptName ");
		finder.setParam("fDeptName", user.getDepart().getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append("AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfFlowStauts())){
			finder.append("AND fFlowStauts LIKE :fFlowStauts ");
			finder.setParam("fFlowStauts", "%"+contractBasicInfo.getfFlowStauts()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcAmount())){
			finder.append("AND fcAmount LIKE :fcAmount");
			finder.setParam("fcAmount", "%"+contractBasicInfo.getFcAmount()+"%");
		}
		if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getcAmountBegin()))){
			finder.append(" AND fcAmount >='"+contractBasicInfo.getcAmountBegin()+"'");
		}
		if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getcAmountEnd()))){
			finder.append(" AND fcAmount <='"+contractBasicInfo.getcAmountEnd()+"'");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public String reCall(String id) {
		ContractBasicInfo bean = super.findById(Integer.valueOf(id));
		//????????????
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//???????????????????????????
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="?????????????????????????????????";
		String msg="??????????????????  "+bean.getFcTitle() +",???????????????("+bean.getBeanCode()+")???"+sdf.format(new Date())+"??????????????????,???????????????.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//??????
		bean=(ContractBasicInfo) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		if(bean.getFcType().equals("HTFL-01")){
			//????????????
			PurchaseApplyBasic cgbean = cgsqMng.findById(Integer.valueOf(bean.getfPurchNo()));
			cgbean.setIsUsed("0");
			/*//?????????
			BiddingRegist gysbean = cgProcessMng.findbycode(bean.getfContractor(), null);
			//?????????????????????
			gysbean.setfContractstatus("0");*/
		}
		/*//???????????????????????????????????????????????????????????????????????????????????????????????????????????????
		if("-4".equals(bean.getfFlowStauts()) && bean.getFcType().equals("HTFL-01")){
			//?????????????????????????????????????????????
			Double amount = Double.parseDouble(bean.getFcAmount());//????????????
			Double syAmount =budgetIndexMgrMng.deleteDjAmount(bean.getfBudgetIndexCode(),bean.getProDetailId(),amount);
			bean.setfAvailableAmount(syAmount+"");
		}*/
		return "????????????";
	}

	/**
	 * ?????????????????????HT+??????+????????????+4????????????
	 * @author:??????
	 * @Title: getFcCode
	 * @Description: ??????????????????
	 * @return
	 * @return String ????????????
	 * @date??? 2020???1???6?????????8:30:40
	 * @throws
	 */
	@Override
	public String getFcCode(User user){
		Finder finder =Finder.create(" FROM ContractBasicInfo");
		int num= super.countQueryResult(finder)+1;
		String fcCode="HT"+DateUtil.getCurrentYear()+StringUtil.autoGenericCode(num+"",4);
		return fcCode;
	}

	@Override
	public List<Lookups> getfContractorlookupsJson(String categoryCode,	String blanked, String selected) {
		List<Lookups> list = new ArrayList<Lookups>();
		selected = StringUtil.isEmpty(selected)?null:selected;
		List<BiddingRegist> brlist = new ArrayList<BiddingRegist>();
		if(!StringUtil.isEmpty(categoryCode)){
			brlist = cgProcessMng.findFbIdByFpId(Integer.valueOf(categoryCode), selected,blanked);
		}
		if(brlist!=null && brlist.size()>0){
			
			for (int i = 0; i < brlist.size(); i++) {
				Lookups lookups = new Lookups();
				BiddingRegist biddingRegist = brlist.get(i);
				lookups.setCode(String.valueOf(biddingRegist.getFbiddingCode()));
				lookups.setName(biddingRegist.getFbiddingName());
				list.add(lookups);
			}
		}
		return list;
	}

	@Override
	public List<ContractBasicInfo> findAttacByFPurchNoList(String fPurchNo) {
		Finder finder=Finder.create(" FROM ContractBasicInfo WHERE fPurchNo='"+fPurchNo+"' AND fsealedStatus=1");
		return super.find(finder);
	}

	@Override
	public List<Lookups> getLookupsJsonGist(String code,String selected) {
		List<Lookups> list = new ArrayList<Lookups>();
		if("PMMC-1".equals(code)){
			Lookups lookups0 = new Lookups();
			lookups0.setCode("FKPJ-0");
			lookups0.setName("???");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("?????????");
			Lookups lookups3 = new Lookups();
			lookups3.setCode("FKPJ-3");
			lookups3.setName("?????????????????????");
			list.add(lookups0);
			list.add(lookups1);
			list.add(lookups3);
		}
		if("PMMC-2".equals(code)){
			Lookups lookups0 = new Lookups();
			lookups0.setCode("FKPJ-0");
			lookups0.setName("???");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("?????????");
			Lookups lookups4 = new Lookups();
			lookups4.setCode("FKPJ-4");
			lookups4.setName("?????????????????????");
			list.add(lookups0);
			list.add(lookups1);
			list.add(lookups4);
		}
		if("PMMC-3".equals(code)){
			Lookups lookups0 = new Lookups();
			lookups0.setCode("FKPJ-0");
			lookups0.setName("???");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("?????????");
			/*Lookups lookups2 = new Lookups();
			lookups2.setCode("FKPJ-2");
			lookups2.setName("?????????");*/
			list.add(lookups0);
			list.add(lookups1);
			//list.add(lookups2);
		}
		if("PMMC-4".equals(code)||"PMMC-5".equals(code)){
			Lookups lookups0 = new Lookups();
			lookups0.setCode("FKPJ-0");
			lookups0.setName("???");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("?????????");
			list.add(lookups0);
			list.add(lookups1);
		}
		return list;
	}

	@Override
	public Pagination queryForEnforcing(ContractBasicInfo contractBasicInfo,
			User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fsealedStatus=1 AND fContStauts <> '99' AND fContStauts <> '-1' AND fToFilesStatus = 1 AND fcType='HTFL-01'");
		/*finder.append("AND fOperator =:fOperator ");
		finder.setParam("fOperator", user.getName());*/
		finder.append("AND fDeptName =:fDeptName ");
		finder.setParam("fDeptName", user.getDepart().getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append("AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfFlowStauts())){
			finder.append("AND fFlowStauts LIKE :fFlowStauts ");
			finder.setParam("fFlowStauts", "%"+contractBasicInfo.getfFlowStauts()+"%");
		}
		finder.append(" order by updateTime desc");
		Pagination p = super.find(finder, pageNo, pageSize);
		List<ContractBasicInfo> list = (List<ContractBasicInfo>) p.getList();
		/*for (int i = 0; i < list.size(); i++) {
			BiddingRegist br = cgProcessMng.findbycode(list.get(i).getfContractor(),null);
			list.get(i).setContractor(br.getFbiddingName());
		}*/
		p.setList(list);
		return p;
	}

	@Override
	public Pagination incomeGoldPay(ContractBasicInfo contractBasicInfo,
			User user, Integer pageNo, Integer pageSize) {
		StringBuffer sb = new StringBuffer("SELECT * FROM T_CONTRACT_BASIC_INFO cbi WHERE cbi.F_CONT_STAUTS <> 99 AND F_FLOW_STAUTS='9'"+
				"AND F_SEALED_STATUS='1' AND F_MARGIN_AMOUNT<>'0.00' AND F_MARGIN_AMOUNT IS NOT NULL AND (F_INCOME_CHANGE_STAUTS ='0' OR F_INCOME_CHANGE_STAUTS = NULL) and F_TOFILES_STATUS ='1' ");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			sb = sb.append("AND (F_CONT_CODE || F_CONT_TITLE || F_DEPT_NAME || F_MARGIN_AMOUNT || TO_DATE(F_INCOME_DATE,'yyyy-mm-dd') LIKE '%"+contractBasicInfo.getFcCode()+"%') ");
		}
		sb.append("ORDER BY F_INCOME_STAUTS desc,F_REQ_TIME desc  ");
		Pagination p  = super.findBySql(new ContractBasicInfo(), sb.toString(), pageNo, pageSize);
		return p;
	}
	
	@Override
	public Pagination incomeUptGoldPay(Upt upt,
			User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM Upt WHERE fUptFlowStauts='9' AND fsealedStatus=1 AND fMarginAmount <> '99' AND fMarginAmount IS NOT NULL and fToFilesStatus='1' ");
		finder.append(" and fContId_U in (select fcId from ContractBasicInfo where fIncomeChangeStauts ='1' )");
		if(!StringUtil.isEmpty(upt.getfUptCode())){
			finder.append("AND (fContCode || fContName || fDeptName || fMarginAmount || TO_DATE(fIncomeDate,'yyyy-mm-dd') LIKE '%"+upt.getfUptCode()+"%') ");
		}
		finder.append("ORDER BY fIncomeStauts DESC,fReqTime desc");
		Pagination p = super.find(finder, pageNo, pageSize);
		return p;
	}
	
	@Override
	public Pagination incomeGoldPayLedger(ContractBasicInfo contractBasicInfo,
			User user, Integer pageNo, Integer pageSize) {
		StringBuffer sb = new StringBuffer("SELECT * FROM T_CONTRACT_BASIC_INFO cbi WHERE cbi.F_CONT_STAUTS <> '99' AND F_FLOW_STAUTS='9'"+
				"AND F_SEALED_STATUS='1' AND F_MARGIN_AMOUNT<>'0.00' AND F_MARGIN_AMOUNT IS NOT NULL and F_TOFILES_STATUS ='1' AND (F_INCOME_CHANGE_STAUTS ='0' OR F_INCOME_CHANGE_STAUTS = NULL)");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			sb = sb.append("AND (F_CONT_CODE || F_CONT_TITLE || F_DEPT_NAME || F_MARGIN_AMOUNT || TO_DATE(F_INCOME_DATE,'yyyy-mm-dd') LIKE '%"+contractBasicInfo.getFcCode()+"%') ");
		}
		if(!user.getRoleName().contains("???????????????")&&!user.getRoleName().contains("??????")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //?????????????????????????????????
				sb.append("AND F_OPERATOR_ID ='"+ user.getId()+"' ");
			}else if("all".equals(deptIdStr)){//??????????????????????????????
				
			}else{//?????????????????????????????????????????? ?????????????????????????????????????????????
				sb.append("AND F_DEPT_ID in ("+deptIdStr+") ");
			}
		}
		sb.append("ORDER BY F_INCOME_STAUTS desc,F_REQ_TIME desc ");
		Pagination p  = super.findBySql(new ContractBasicInfo(), sb.toString(), pageNo, pageSize);
		return p;
	}
	
	@Override
	public Pagination incomeUptGoldPayLedger(Upt upt,
			User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM Upt WHERE fUptFlowStauts='9' AND fsealedStatus=1 AND fMarginAmount <> '99' AND fMarginAmount IS NOT NULL and fToFilesStatus='1'");
		finder.append(" and fContId_U in (select fcId from ContractBasicInfo where fIncomeChangeStauts ='1' )");
		if(!StringUtil.isEmpty(upt.getfUptCode())){
			finder.append("AND (fContCode LIKE '%"+upt.getfUptCode()+"%' or fContName LIKE '%"+upt.getfUptCode()+"%' or fDeptName LIKE '%"+upt.getfUptCode()+"%' or fMarginAmount LIKE '%"+upt.getfUptCode()+"%' or TO_DATE(fIncomeDate,'yyyy-mm-dd') LIKE '%"+upt.getfUptCode()+"%')");
		}
		if(!user.getRoleName().contains("???????????????")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //?????????????????????????????????
				finder.append("AND fOperatorID ='"+ user.getId()+"' ");
			}else if("all".equals(deptIdStr)){//??????????????????????????????
				
			}else{//?????????????????????????????????????????? ?????????????????????????????????????????????
				finder.append("AND fDeptID in ("+deptIdStr+") ");
			}
		}
		finder.append("ORDER BY fIncomeStauts DESC,fReqTime desc");
		Pagination p = super.find(finder, pageNo, pageSize);
		return p;
	}
	
	@Override
	public Pagination queryContraList(ContractBasicInfo contractBasicInfo,
			User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE (fFlowStauts='0' or fFlowStauts='1' or fFlowStauts='-4' or fFlowStauts='9' or fFlowStauts='-1') AND fContStauts <> '99'");
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append(" AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append(" AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfFlowStauts())){
			finder.append(" AND fFlowStauts LIKE :fFlowStauts ");
			finder.setParam("fFlowStauts", "%"+contractBasicInfo.getfFlowStauts()+"%");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
}
