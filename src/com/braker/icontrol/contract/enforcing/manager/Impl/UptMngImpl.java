package com.braker.icontrol.contract.enforcing.manager.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.DataEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.cgmanage.cgreveive.manager.AcceptContractRegisterListMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.enforcing.manager.UptClauseMng;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.enforcing.model.UptClause;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.purchase.apply.manager.PurchaseApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class UptMngImpl extends BaseManagerImpl<Upt> implements UptMng{

	@Autowired
	private UptClauseMng uptAttacMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private LookupsMng lookupsMng;  
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private ReceivPlanMng receivPlanMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private ContractRegisterListMng contractRegisterListMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private PurchaseApplyMng purchaseApplyMng;
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	@Autowired
	private AcceptContractRegisterListMng acceptContractRegisterListMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)//事务处理
	public String saveUptAndUptAttac(ContractBasicInfo contractBasicInfo,Upt upt,User user,String htbgfiles,
			List<UptClause> uptClauseList,String uptplan, String cgConfPlanJson) throws Exception {
		contractBasicInfo=formulationMng.findById(contractBasicInfo.getFcId());
		//判断验收后的数量是否小于已验收的数量
		if (!StringUtil.isEmpty(cgConfPlanJson)) {
			//采购清单变更
			List<ContractRegisterList> contractRegisterList = JSON.parseObject(cgConfPlanJson,new TypeReference<List<ContractRegisterList>>(){});
			for (int i = 0; i < contractRegisterList.size(); i++) {
				ContractRegisterList c = contractRegisterList.get(i);
				List<AcceptContractRegisterList> acrList = acceptContractRegisterListMng.findByProperty("cRId", c.getcRId());
				if (acrList != null && !acrList.isEmpty()) {
					if (acrList.get(0).getfCheckedNum() != null && acrList.get(0).getfCheckedNum() > c.getFpNum()) {
						return "采购清单数量不小于已验收数量！";
					}
				}
			}
		}
		
		if("HTFL-03".equals(contractBasicInfo.getFcType())){
			upt.setFcAmount("0.00");
		}
		
		//合同原编号
		upt.setfContCodeOld(contractBasicInfo.getFcCode());
		if(StringUtil.isEmpty(String.valueOf(upt.getfId_U()))){
			upt.setfId_U(Integer.valueOf(StringUtil.random8()));
			upt.setfUptCode(StringUtil.Random("HTBG"));
			upt.setCreator(user.getAccountNo());
			upt.setCreateTime(new Date());
			upt.setfContNameold(contractBasicInfo.getFcTitle());
		}else {
			upt.setUpdator(user.getAccountNo());
			upt.setUpdateTime(new Date());
		}
//		formulationMng.merge(contractBasicInfo);
		//保存审批状态
		upt.setfUptStatus("1");//变更单状态1：正常
		upt.setfOperatorID(user.getId());//申请人id
		upt.setfOperator(user.getName());//申请人名称
		upt.setfDeptID(user.getDepart().getId());//所属部门ID
		upt.setfReqTime(new Date());//申请日期
		upt.setFsealedStatus("0");//是否盖章 0-未盖章 1-已盖章
		upt.setfToFilesStatus(0);//归档状态 0-未归档 1-已归档
		if(upt.getfUptFlowStauts().equals("1")){
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),upt.getJoinTable(),upt.getBeanCodeField(),upt.getBeanCode(), "HTBG", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//根据前面获得的角色的信息设置下一环节的用户名称/编码
			upt.setfUserCode(nextUser.getId());
			upt.setfUserName(nextUser.getName());
			//设置下节点节点编码
			upt.setfNCode(firstKey+"");
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,upt.getBeanCode());
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(upt.getfUserCode());//任务处理人ID既是下节点处理人ID
			work.setTaskId(upt.getfId_U());//申请单ID
			work.setTaskCode(upt.getfContCode());//为申请单的单号
			work.setTaskName("[变更申请]"+upt.getfContName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/Change/approvalChange/"+upt.getfId_U());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/Change/detail/"+ upt.getfId_U());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setTaskType("1");//任务归属（1审批人
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(new Date());//任务提交时间
			Integer workId =shenTongMng.getSeq("PERSONAL_WORKTABLE_SEQ");
			work.setfId(workId);
			personalWorkMng.merge(work);
			//添加一个自己的已办事项
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(user.getId());//任务处理人ID既是下节点处理人ID
			minwork.setTaskId(upt.getfId_U());//申请单ID
			minwork.setTaskCode(upt.getfContCode());//为申请单的单号
			minwork.setTaskName("[变更申请]"+upt.getfContName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			minwork.setUrl("/Change/edit/"+upt.getfId_U());//退回修改url
			minwork.setUrl1("/Change/detail/"+ upt.getfId_U());//查看url
			minwork.setUrl2("/Change/delete/"+ upt.getfId_U());//退回删除url
			minwork.setTaskStauts("2");//待办
			minwork.setType("2");//任务类型：2-查看
			minwork.setTaskType("0");//任务归属（0发起人）
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(new Date());//任务提交时间
			minwork.setFinishTime(new Date());
			Integer minworkId =shenTongMng.getSeq("PERSONAL_WORKTABLE_SEQ");
			minwork.setfId(minworkId);
			personalWorkMng.merge(minwork);
		}
		upt=(Upt) super.saveOrUpdate(upt);
		attachmentMng.joinEntity(upt,htbgfiles);
		//修改原合同变更状态
		contractBasicInfo.setfUpdateStatus("1");
		super.merge(contractBasicInfo);
		//有填写变更内容时,保存变更内容
		/*if(uptClauseList.size()>0){
			//删除原来的变更内容
			deletebyfId(String.valueOf(upt.getfId_U()));
			//添加新的内容
			for (int i = 0; i < uptClauseList.size(); i++) {
				UptClause uc = uptClauseList.get(i);
				uc.setfId_U_AU(upt.getfId_U());
				uc.setCreateTime(new Date());
				uc.setCreator(user.getAccountNo());
				super.merge(uc);
			}
		}*/
		//转换成ReceivPlan类型的list
		//删除数据库中   变更合同的付款计划表
		getSession().createSQLQuery("delete from T_RECEIV_PLAN where F_UPT_ID ="+upt.getfId_U() +" ").executeUpdate();
		if (!StringUtil.isEmpty(uptplan)) {
			List<ReceivPlan> receivPlan = JSON.parseObject(uptplan,new TypeReference<List<ReceivPlan>>(){});
			//保存新增的计划和更新旧的计划
			for (int i = 0; i < receivPlan.size(); i++) {
				receivPlan.get(i).setfPlanId(shenTongMng.getSeq("T_RECEIV_PLAN_SEQ"));
				receivPlan.get(i).setfUpateTime_R(new Date());
				receivPlan.get(i).setPayStauts("0");
				receivPlan.get(i).setfStauts_R("0");
				receivPlan.get(i).setfUpateUser_R(user.getAccountNo());
				receivPlan.get(i).setfContId_R(contractBasicInfo.getFcId());
				receivPlan.get(i).setDataType(1);//数据类型为变更合同付款计划
				receivPlan.get(i).setfUptId_R(upt.getfId_U());//数据类型为变更合同付款计划
				receivPlanMng.merge(receivPlan.get(i));
			}
		}
		//删除数据库中   变更合同的采购清单表
		getSession().createSQLQuery("delete from T_CONTRACT_REGISTER_LIST where F_UPT_ID ="+upt.getfId_U() +" ").executeUpdate();
		if (!StringUtil.isEmpty(cgConfPlanJson)) {
			//采购清单变更
			List<ContractRegisterList> contractRegisterList = JSON.parseObject(cgConfPlanJson,new TypeReference<List<ContractRegisterList>>(){});
			for (int i = 0; i < contractRegisterList.size(); i++) {
				contractRegisterList.get(i).setcRId(shenTongMng.getSeq("T_CONTRACT_REGISTER_LIST_SEQ"));
				contractRegisterList.get(i).setCreateTime(new Date());
				contractRegisterList.get(i).setCreator(user.getAccountNo());
				contractRegisterList.get(i).setFcId(contractBasicInfo.getFcId());
				contractRegisterList.get(i).setfId_U(upt.getfId_U());
				contractRegisterList.get(i).setfDataType(1);
				contractRegisterListMng.merge(contractRegisterList.get(i));
			}
		}
		return "操作成功";
	}

	@Override
	public String getFContCode() {
		Finder finder = Finder.create(" FROM Upt");
		int num = super.countQueryResult(finder) + 1;
		String fContCode = "BG" + DateUtil.getCurrentYear() + StringUtil.autoGenericCode(num+"",4);
		return fContCode;
	}
	
	@Override
	public List<Upt> findByFContId_U(String id) {
		Finder finder = Finder.create(" FROM Upt WHERE fContId_U ="+Integer.valueOf(id)+" order by updateTime desc");
		return super.find(finder);
	}
	@Override
	public List<Upt> findByCId(String id) {
		Finder finder = Finder.create(" FROM Upt WHERE fContId_U ="+Integer.valueOf(id)+" and fUptStatus !='99'");
		return super.find(finder);
	}

	@Override
	public int findUpTInfoSize(String id) {
		Finder finder =Finder.create(" FROM Upt WHERE fContId_U="+Integer.valueOf(id)); 
		return super.find(finder).size();
	}

	@Override
	public void deletebyfId(String fId) {
		String sql=" delete from T_CONTRACT_UPT_CLAUSE where F_ID="+fId;
		SQLQuery query = getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	public void deletebyId(Upt upt) {
		//修改变更表状态
		upt.setfUptStatus("99");
		super.merge(upt);
		//修改原合同状态
		ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(upt.getfContId_U()));
		cbi.setfUpdateStatus("0");
		super.merge(cbi);
		//删除工作计划
		getSession().createSQLQuery("delete from T_RECEIV_PLAN where F_UPT_ID ="+upt.getfId_U() +" ").executeUpdate();
		//删除采购清单
		getSession().createSQLQuery("delete from T_CONTRACT_REGISTER_LIST where F_UPT_ID ="+upt.getfId_U() +" ").executeUpdate();
		//删除相关工作台信息
		List<PersonalWork> worklost = personalWorkMng.findByCodeAndUser(upt.getfContCode(), userMng.findById(upt.getfOperatorID()));
		if(worklost.size()>0){
			for (int i = 0; i < worklost.size(); i++) {
				personalWorkMng.deleteById(Integer.valueOf(worklost.get(i).getfId()));
			}
		}
		
	}

	@Override
	public Pagination queryList(Upt upt, User user, Integer pageNo,Integer pageSize,String searchData) {
		Finder finder=Finder.create(" FROM Upt WHERE fUptFlowStauts='1' AND fUptStatus='1' AND fUserCode='"+user.getId()+"'");
//		if(!user.getRoleName().contains("合同管理岗")&&!user.getRoleName().contains("系统管理员")){
//			String deptIdStr=departMng.getDeptIdStrByUser(user);
//	 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
//	 			finder.append(" AND fOperatorID =:fOperatorId ").setParam("fOperatorId", user.getId());
//	 		}else if("all".equals(deptIdStr)){//局长可以查看所有人的
//	 			
//	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
//	 			finder.append(" AND fDeptID in ("+deptIdStr+")");
//	 		}
//		}

		if(!StringUtil.isEmpty(upt.getfContCode())){
			finder.append(" AND fContCode LIKE :fContCode");
			finder.setParam("fContCode", "%"+upt.getfContCode()+"%");
		}
		if(!StringUtil.isEmpty(upt.getfContCodeOld())){
			finder.append(" AND fContCodeOld LIKE :fContCodeOld");
			finder.setParam("fContCodeOld", "%"+upt.getfContCodeOld()+"%");
		}
		if(!StringUtil.isEmpty(upt.getfContNameold())){
			finder.append(" AND fContNameold LIKE :fContNameold");
			finder.setParam("fContNameold", "%"+upt.getfContNameold()+"%");
		}
		
		if(!StringUtil.isEmpty(upt.getfContName())){
			finder.append(" AND fContName LIKE :fContName");
			finder.setParam("fContName", "%"+upt.getfContName()+"%");
		}
		if(upt.getfReqTimeStart()!=null){
			finder.append(" AND DATE_FORMATE(fReqTime, '%Y-%m-%d') >="+upt.getfReqTimeStart());
		}
		if(upt.getfReqTimeEnd()!=null){
			finder.append(" AND DATE_FORMATE(fReqTime, '%Y-%m-%d') <="+upt.getfReqTimeEnd());
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fContCode like '%"+searchData+"%' or fContCodeOld like '%"+searchData+"%' or fContNameold like '%"+searchData+"%' or fOperator like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or to_date(fReqTime, 'yyyy-mm-dd') like '%"+searchData+"%')  ");
		}
		finder.append(" order by fReqTime desc");
		
		Pagination p = super.find(finder, pageNo, pageSize);
		List<Upt> list = (java.util.List<Upt>) p.getList();
		if (list != null && !list.isEmpty()) {
			for (Upt u : list) {
				ContractBasicInfo contractBasicInfo = formulationMng.findById(u.getfContId_U());
				u.setFcType(contractBasicInfo.getFcType());
				u.setfOperatorOld(contractBasicInfo.getfOperator());
				u.setfOperatorIdOld(contractBasicInfo.getfOperatorId());
			}
		}
		
		return p;
	}

	/**
	 * 保存审批信息
	 * @createtime 2019-05-27
	 * @author 陈睿超
	 * @updatetime 2020-02-17
	 * @updator 陈睿超
	 */
	@Override
	public String updateStatus(String fId_U, String status, User user,TProcessCheck checkBean, String file,String hyjyfiles,String fDZHCode) throws Exception {
		//查询需要修改审批状态的表单
		Upt upt = super.findById(Integer.valueOf(fId_U));
		//原合同
		ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(upt.getfContId_U()));
		Double uptPlanAmount = 0d;
		Double contractPlanAmount = 0d;
		PurchaseApplyBasic pbean = new PurchaseApplyBasic();
		if("HTFL-01".equals(cbi.getFcType())){//判断是什么类型合同,如果是采购类合同
			if(!StringUtil.isEmpty(fDZHCode)){
				upt.setfDZHCode(fDZHCode);
			}
			//变更后付款计划总金额
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(t.F_RECE_PLAN_AMOUNT) AS totalAmount FROM T_RECEIV_PLAN t");
			sql.append(" WHERE t.F_UPT_ID = " + fId_U);
			sql.append(" AND t.F_DATA_TYPE = 1");
			SQLQuery query = getSession().createSQLQuery(sql.toString());
			List uptPlanlist = query.list();
			if (uptPlanlist != null && !uptPlanlist.isEmpty() && uptPlanlist.get(0) != null) {
				uptPlanAmount = (Double) uptPlanlist.get(0);
			}
			//原合同付款计划总金额
			StringBuilder contractSql = new StringBuilder();
			contractSql.append("SELECT SUM(t.F_RECE_PLAN_AMOUNT) AS totalAmount FROM T_RECEIV_PLAN t");
			contractSql.append(" WHERE t.F_CONT_ID = " + cbi.getFcId());
			contractSql.append(" AND t.F_DATA_TYPE = 0");
			SQLQuery contractQuery = getSession().createSQLQuery(contractSql.toString());
			List contractPlanlist = contractQuery.list();
			if (contractPlanlist != null && !contractPlanlist.isEmpty() && contractPlanlist.get(0) != null) {
				contractPlanAmount = (Double) contractPlanlist.get(0);
			}
			pbean = purchaseApplyMng.findById(Integer.valueOf(cbi.getfPurchNo()));
			TBudgetIndexMgr index = budgetIndexMgrMng.findById(pbean.getIndexId());
			//付款计划变更（增加）
			if (uptPlanAmount > contractPlanAmount) {
				Double d= (uptPlanAmount-contractPlanAmount)/10000;
				if (index.getDjAmount()*10000 < d) {
					return "操作失败，冻结金额不足！";
				}
				//判断申请金额是否超过可用金额
				Boolean b=budgetIndexMgrMng.checkAmounts(String.valueOf(pbean.getIndexId()), d);
				if (!b) {
					return "操作失败，可用金额不足！";
				}
			}
		}
		
		
		CheckEntity entity=(CheckEntity)upt;
		String checkUrl="/Change/approvalChange/";
		String lookUrl ="/Change/detail/";
		upt=(Upt)tProcessCheckMng.checkProcess(checkBean,entity,user,"HTBG",checkUrl,lookUrl,file);
		super.saveOrUpdate(upt);
		//更改合同主表的变更状态为1-有变更
		if("9".equals(upt.getfUptFlowStauts())){
			/*
			 * 冻结金额
			 */
			//付款计划变更（增加）
			if("HTFL-01".equals(cbi.getFcType())){//判断是什么类型合同,如果是采购类合同
				if (uptPlanAmount > contractPlanAmount) {
					Double d= uptPlanAmount-contractPlanAmount;
					budgetIndexMgrMng.addDjAmount(pbean.getIndexId(), pbean.getProDetailId(), d);
				}
				//付款计划变更（减少）
				if (uptPlanAmount < contractPlanAmount) {
					Double d= contractPlanAmount-uptPlanAmount;
					budgetIndexMgrMng.deleteDjAmount(pbean.getIndexId(), pbean.getProDetailId(), d);
				}
			}
			
			cbi.setfUpdateStatus("1");//修改合同变更状态
			
			//如果合同保证金发生改变，修改原合同合同保证金变更状态
			if(!cbi.getfMarginAmount().equals(upt.getfMarginAmount())){
				cbi.setfIncomeChangeStauts("1");
			}
//			uptAmount(cbi,upt);
			super.merge(cbi);
			
			tProcessPrintDetailMng.arrangeUptContractDetail(upt);
			
			
			//需要给印章管理岗推送消息
			//日期格式化（消息推送中使用）
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String title=upt.getfContName()+ "用印消息";
			String msg=upt.getfOperator()+"申请的  "+upt.getfContName()+"任务编号为：("+upt.getfUptCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
			User userYZ = userMng.getUserByRoleNameAndDepartName("印章管理岗", "办公室");
			privateInforMng.setMsg(title, msg, userYZ.getId(), "2");
		}
		
		if(!StringUtil.isEmpty(hyjyfiles)){
			attachmentMng.joinEntity(upt,hyjyfiles);
		}
		return "操作成功！";
	}

	@Override
	public Pagination List(Upt upt, User user, Integer pageNo, Integer pageSize,String searchData) {
		Finder finder=Finder.create(" FROM Upt WHERE fUptStatus='1'");
		if(!user.getRoleName().contains("系统管理员")){
			finder.append(" AND fOperatorID =:fOperatorId ").setParam("fOperatorId", user.getId());
//			String deptIdStr=departMng.getDeptIdStrByUser(user);
//	 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
//	 			finder.append(" AND fOperatorID =:fOperatorId ").setParam("fOperatorId", user.getId());
//	 		}else if("all".equals(deptIdStr)){//局长可以查看所有人的
//	 			
//	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
//	 			finder.append(" AND fDeptID in ("+deptIdStr+")");
//	 		}
		}

		if(!StringUtil.isEmpty(upt.getfContCode())){
			finder.append(" AND fContCode LIKE :fContCode");
			finder.setParam("fContCode", "%"+upt.getfContCode()+"%");
		}
		if(!StringUtil.isEmpty(upt.getfContCodeOld())){
			finder.append(" AND fContCodeOld LIKE :fContCodeOld");
			finder.setParam("fContCodeOld", "%"+upt.getfContCodeOld()+"%");
		}
		if(!StringUtil.isEmpty(upt.getfContNameold())){
			finder.append(" AND fContNameold LIKE :fContNameold");
			finder.setParam("fContNameold", "%"+upt.getfContNameold()+"%");
		}
		if(!StringUtil.isEmpty(upt.getfContName())){
			finder.append(" AND fContName LIKE :fContName");
			finder.setParam("fContName", "%"+upt.getfContName()+"%");
		}
		if(upt.getfReqTimeStart()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime, '%Y-%m-%d') >='"+upt.getfReqTimeStart()+"'");
		}
		if(upt.getfReqTimeEnd()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime, '%Y-%m-%d') <='"+upt.getfReqTimeEnd()+"'");
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fContCode like '%"+searchData+"%' or fContCodeOld like '%"+searchData+"%' or fContNameold like '%"+searchData+"%' or to_date(fReqTime, 'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		finder.append(" order by fReqTime desc");
		
		Pagination p = super.find(finder, pageNo, pageSize);
		
		List<Upt> list = (java.util.List<Upt>) p.getList();
		if (list != null && !list.isEmpty()) {
			for (Upt u : list) {
				ContractBasicInfo contractBasicInfo = formulationMng.findById(u.getfContId_U());
				u.setFcType(contractBasicInfo.getFcType());
				u.setfOperatorOld(contractBasicInfo.getfOperator());
				u.setfOperatorIdOld(contractBasicInfo.getfOperatorId());
			}
		}
		
		return p;
	}

	@Override
	public String reCall(String id) {
		Upt bean = super.findById(Integer.valueOf(id));
		
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="合同变更申请被撤回消息";
		String msg="您待审批合同  "+bean.getfContName() +",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean=(Upt) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		
		return "操作成功";
	}

	//根据变更付款计划变更指标冻结金额
	public void uptAmount(ContractBasicInfo cbi,Upt upt){
		Finder finder = Finder.create("from ReceivPlan where dataType =1 and fUptId_R ="+upt.getfId_U());
		Finder basefinder = Finder.create("from ReceivPlan where dataType =0 and fStauts_R = 1 and fContId_R ="+cbi.getFcId());
		List<ReceivPlan> planList = super.find(finder);
		List<ReceivPlan> baseplanList = super.find(basefinder);
		Double payamount = 0.00;//原来合同已经支付的金额
		Double newPlanamount = 0.00;//现在新的付款计划需要支付的总额
		Double camount = Double.valueOf(cbi.getFcAmount());//现在新的付款计划需要支付的总额
		for (int i = 0; i < baseplanList.size(); i++) {
			payamount += Double.valueOf(baseplanList.get(i).getfRecePlanAmount());
		}
		for (int i = 0; i < planList.size(); i++) {
			newPlanamount += Double.valueOf(planList.get(i).getfRecePlanAmount());
		}
		if(payamount+newPlanamount>camount){//已支付金额＋新的付款计划>原合同金额
			Double d= (payamount+newPlanamount-camount);
			budgetIndexMgrMng.addDjAmount(cbi.getfBudgetIndexCode(), cbi.getProDetailId(), d);
		}else if(payamount+newPlanamount<camount){//已支付金额＋新的付款计划<原合同金额
			Double d= camount-(payamount+newPlanamount);
			budgetIndexMgrMng.deleteDjAmount(cbi.getfBudgetIndexCode(), cbi.getProDetailId(), d);
			
		}

	}
	
	

}
