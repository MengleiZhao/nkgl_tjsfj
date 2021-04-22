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
			/*if(contractBasicInfo.getfFlowStauts().equals("暂存")){
				contractBasicInfo.setfFlowStauts("0");
			}
			if(contractBasicInfo.getfFlowStauts().equals("待审批")){
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
//		if(!user.getRoleName().contains("系统管理员")){
//			String deptIdStr=departMng.getDeptIdStrByUser(user);
//	 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
//	 			finder.append(" AND fOperatorId =:fOperatorId ").setParam("fOperatorId", user.getId());
//	 		}else if("all".equals(deptIdStr)){//局长可以查看所有人的
//	 			
//	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
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
		if(!user.getRoleName().contains("系统管理员")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //普通岗位只能查看自己的
				finder.append(" AND fOperatorId =:fOperatorId ").setParam("fOperatorId", user.getId());
			}else if("all".equals(deptIdStr)){//局长可以查看所有人的
				
			}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
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
		if(!user.getRoleName().contains("系统管理员")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //普通岗位只能查看自己的
				finder.append(" AND fOperatorID =:fOperatorID ").setParam("fOperatorID", user.getId());
			}else if("all".equals(deptIdStr)){//局长可以查看所有人的
				
			}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
				finder.append(" AND fDeptID in ("+deptIdStr+")");
			}
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public void save_CBI(ContractBasicInfo cbiBean, String files,String filessqwts, SignInfo siBean, String planJson,String cgConfplanJson, User user,PurchaseApplyBasic cgbean) throws Exception{
		Date date = new Date();
		if(cbiBean.getFcId() == null){//新增
			//设置创建人、创建时间
			cbiBean.setCreator(user.getName());
			cbiBean.setCreateTime(date);
			//设置申请人id
			cbiBean.setfOperatorId(user.getId());
			//设置申请部门id
			cbiBean.setfDeptId(user.getDepart().getId());
			//新增，获取合同编码
			String fcCode = getFcCode(user);
			cbiBean.setFcCode(fcCode);
			cbiBean.setfReqtIME(new Date());
			Integer id =shenTongMng.getSeq("T_CONTRACT_BASIC_INFO_SEQ");
			cbiBean.setFcId(id);
		}else {//修改
			cbiBean.setUpdator(user.getName());
			cbiBean.setUpdateTime(date);
			cbiBean.setfReqtIME(new Date());
		}
		/*//是否委托授权
		cbiBean.setfIsAuthor("0");*/
		/*//设置合同形式 
		contractBasicInfo.setfContStyle(fContStyle);*/
		
		//质保金退还状态
		cbiBean.setfPayStauts("0");
		//合同变更状态
		cbiBean.setfUpdateStatus("0");
		//合同纠纷状态
		cbiBean.setfDisputeStatus("0");
		//合同盖章状态
		cbiBean.setFsealedStatus("0");
		//合同盖章状态
		cbiBean.setfToFilesStatus(0);
		//保证金变更状态
		cbiBean.setfIncomeChangeStauts("0");
		
		//设置付款方式 
		Lookups fPayType = lookupsMng.findByLookCode(cbiBean.getfPayType().getCode());
		cbiBean.setfPayType(fPayType);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		cbiBean.setKeepTime(sdf.format(cbiBean.getfContStartTime())+"-"+sdf.format(cbiBean.getfContEndTime()));
		
		cbiBean.setfAllAmount("0");
		if(cbiBean.getFcType().equals("HTFL-01")){
			cbiBean.setfNotAllAmountL(String.valueOf(cbiBean.getFcAmount()));
		}else{
			cbiBean.setfNotAllAmountL("0");
			cbiBean.setFcAmount("0");
		}
		cbiBean = (ContractBasicInfo) super.merge(cbiBean);
		
		//送审
		if("1".equals(cbiBean.getfFlowStauts())){
			if(cbiBean.getFcType().equals("HTFL-01")){
				
				
				cgbean.setIsUsed("1");
				//供应商
				/*BiddingRegist gysbean = cgProcessMng.findbycode(cbiBean.getfContractor(), null);
				gysbean.setfContractstatus("1");*/
			}
			
			/*//指标金额已于采购模块冻结，合同模块支出合同只做冻结金额的数值修改，不再冻结
			if(contractBasicInfo.getFcType().equals("HTFL-01")){
				//如果是支出合同，冻结合同金额
				Double amount = Double.parseDouble(contractBasicInfo.getFcAmount());//合同金额
				Double syAmount =budgetIndexMgrMng.addDjAmount(contractBasicInfo.getfBudgetIndexCode(),contractBasicInfo.getProDetailId(),amount);
				contractBasicInfo.setfAvailableAmount(syAmount+"");
			}*/
			
			//得到第一个审批节点key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), cbiBean.getJoinTable(), cbiBean.getBeanCodeField(), cbiBean.getBeanCode(), "HTND", user);
			TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("HTND", user.getDpID());
			Integer flowId = processDefin.getFPId();
			TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser = userMng.findById(node.getUserId());
			//根据前面获得的角色的信息设置下一环节的用户名称/编码
			cbiBean.setfUserCode(nextUser.getId());
			cbiBean.setfUserName(nextUser.getName());
			//设置下节点节点编码
			cbiBean.setfNCode(firstKey+"");
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId, cbiBean.getBeanCode());
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(cbiBean.getfUserCode());//任务处理人ID既是下节点处理人ID
			work.setTaskId(cbiBean.getFcId());//申请单ID
			work.setTaskCode(cbiBean.getFcCode());//为申请单的单号
			work.setTaskName("[拟定申请]"+cbiBean.getFcTitle());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/Approval/approve?id="+cbiBean.getFcId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/Formulation/detail?id="+cbiBean.getFcId());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setTaskType("1");//任务归属（1审批人
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(date);//任务提交时间
			Integer workId =shenTongMng.getSeq("PERSONAL_WORKTABLE_SEQ");
			work.setfId(workId);
			personalWorkMng.merge(work);
			
			//添加一个自己的已办事项
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(user.getId());//任务处理人ID既是下节点处理人ID
			minwork.setTaskId(cbiBean.getFcId());//申请单ID
			minwork.setTaskCode(cbiBean.getFcCode());//为申请单的单号
			minwork.setTaskName("[拟定申请]"+cbiBean.getFcTitle());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			minwork.setUrl("/Formulation/edit?id="+cbiBean.getFcId());//退回修改url
			minwork.setUrl1("/Formulation/detail?id="+cbiBean.getFcId());//查看url
			minwork.setUrl2("/Formulation/delete?id="+cbiBean.getFcId());//退回删除url
			minwork.setTaskStauts("2");//待办
			minwork.setType("2");//任务类型：2-查看
			minwork.setTaskType("0");//任务归属（0发起人）
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(date);//任务提交时间
			minwork.setFinishTime(date);
			Integer minworkId =shenTongMng.getSeq("PERSONAL_WORKTABLE_SEQ");
			minwork.setfId(minworkId);
			personalWorkMng.merge(minwork);
		}
		//保存合同拟定附件
		attachmentMng.joinEntity(cbiBean, files);
		
		//保存备案信息
		//是否为采购合同，不是采购合同没有付款计划
		if("HTFL-01".equals(cbiBean.getFcType())){
			//转化json对象
			List<ReceivPlan> list2 = JSON.parseObject(planJson,new TypeReference<List<ReceivPlan>>(){});
			//转换成ReceivPlan类型的list
			List<ReceivPlan> receivPlan = list2;
			//List<ReceivPlan> receivPlan = filingMng.getReceivPlan(list2, user, cbiBean);
			List<ReceivPlan> Plan = filingMng.find_ReceivPlan_List(String.valueOf(cbiBean.getFcId()));
			//删除不同的计划
			if(Plan.size() > 0){
				for (int i = 0; i < Plan.size(); i++) {
					receivPlanMng.deleteById(Plan.get(i).getfPlanId());
				}
			}
			//保存新增的计划和更新旧的计划
			if(receivPlan.size() > 0){
				for (ReceivPlan receivPlan2 : receivPlan) {
					receivPlan2.setfUpateTime_R(new Date());
					receivPlan2.setPayStauts("0");
					receivPlan2.setfStauts_R("0");
					receivPlan2.setfUpateUser_R(user.getAccountNo());
					receivPlan2.setfContId_R(cbiBean.getFcId());
					receivPlan2.setDataType(0);//合同拟定数据
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
			//保存中标商采购清单信息
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
		//删除相关工作台信息
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
				//保存合同拟定文本
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
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="合同拟定申请被撤回消息";
		String msg="您待审批合同  "+bean.getFcTitle() +",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean=(ContractBasicInfo) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		if(bean.getFcType().equals("HTFL-01")){
			//采购项目
			PurchaseApplyBasic cgbean = cgsqMng.findById(Integer.valueOf(bean.getfPurchNo()));
			cgbean.setIsUsed("0");
			/*//供应商
			BiddingRegist gysbean = cgProcessMng.findbycode(bean.getfContractor(), null);
			//修改供应商状态
			gysbean.setfContractstatus("0");*/
		}
		/*//指标金额已于采购模块冻结，合同模块支出合同只做冻结金额的数值修改，不再冻结
		if("-4".equals(bean.getfFlowStauts()) && bean.getFcType().equals("HTFL-01")){
			//审批不通过的时候，归还冻结金额
			Double amount = Double.parseDouble(bean.getFcAmount());//合同金额
			Double syAmount =budgetIndexMgrMng.deleteDjAmount(bean.getfBudgetIndexCode(),bean.getProDetailId(),amount);
			bean.setfAvailableAmount(syAmount+"");
		}*/
		return "操作成功";
	}

	/**
	 * 合同编号规则：HT+年份+部门编号+4位顺序号
	 * @author:安达
	 * @Title: getFcCode
	 * @Description: 获取合同编码
	 * @return
	 * @return String 返回类型
	 * @date： 2020年1月6日下午8:30:40
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
			lookups0.setName("无");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("验收单");
			Lookups lookups3 = new Lookups();
			lookups3.setCode("FKPJ-3");
			lookups3.setName("固定资产入账单");
			list.add(lookups0);
			list.add(lookups1);
			list.add(lookups3);
		}
		if("PMMC-2".equals(code)){
			Lookups lookups0 = new Lookups();
			lookups0.setCode("FKPJ-0");
			lookups0.setName("无");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("验收单");
			Lookups lookups4 = new Lookups();
			lookups4.setCode("FKPJ-4");
			lookups4.setName("无形资产入账单");
			list.add(lookups0);
			list.add(lookups1);
			list.add(lookups4);
		}
		if("PMMC-3".equals(code)){
			Lookups lookups0 = new Lookups();
			lookups0.setCode("FKPJ-0");
			lookups0.setName("无");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("验收单");
			/*Lookups lookups2 = new Lookups();
			lookups2.setCode("FKPJ-2");
			lookups2.setName("入库单");*/
			list.add(lookups0);
			list.add(lookups1);
			//list.add(lookups2);
		}
		if("PMMC-4".equals(code)||"PMMC-5".equals(code)){
			Lookups lookups0 = new Lookups();
			lookups0.setCode("FKPJ-0");
			lookups0.setName("无");
			Lookups lookups1 = new Lookups();
			lookups1.setCode("FKPJ-1");
			lookups1.setName("验收单");
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
		if(!user.getRoleName().contains("系统管理员")&&!user.getRoleName().contains("出纳")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //普通岗位只能查看自己的
				sb.append("AND F_OPERATOR_ID ='"+ user.getId()+"' ");
			}else if("all".equals(deptIdStr)){//局长可以查看所有人的
				
			}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
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
		if(!user.getRoleName().contains("系统管理员")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //普通岗位只能查看自己的
				finder.append("AND fOperatorID ='"+ user.getId()+"' ");
			}else if("all".equals(deptIdStr)){//局长可以查看所有人的
				
			}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
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
