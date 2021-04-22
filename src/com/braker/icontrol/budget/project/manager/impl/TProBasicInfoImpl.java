package com.braker.icontrol.budget.project.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.DateUtil;
import com.braker.common.util.PoiUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.EconomicMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.ProjectDelay;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.PerformanceIndicatorModel;
import com.braker.icontrol.budget.project.entity.TProBasicFunds;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.PerformanceIndicatorModelMng;
import com.braker.icontrol.budget.project.manager.TProBasicAccoAttacMng;
import com.braker.icontrol.budget.project.manager.TProBasicFundsMng;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProBasicPlanAttacMng;
import com.braker.icontrol.budget.project.manager.TProBasicRenameHistoryMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.budget.project.manager.TProGoalDetailMng;
import com.braker.icontrol.budget.project.manager.TProGoalMng;
import com.braker.icontrol.budget.project.manager.TProOutcomeAttacMng;
import com.braker.icontrol.budget.project.manager.TProPlanMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class TProBasicInfoImpl extends BaseManagerImpl<TProBasicInfo> implements TProBasicInfoMng{
	
	
	@Autowired 
	private TProBasicAccoAttacMng tProBasicAccoAttacMng;
	@Autowired
	private TProBasicPlanAttacMng tProBasicPlanAttacMng;
	@Autowired
	private TProOutcomeAttacMng tProOutcomeAttacMng;
	@Autowired
	private TProExpendDetailMng tProExpendDetailMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private EconomicMng economicMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private TProPlanMng tProPlanMng;
	@Autowired
	private TProGoalMng tProGoalMng;
	@Autowired
	private TProGoalDetailMng tProGoalDetailMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private TProBasicFundsMng tProBasicFundsMng;
	@Autowired
	private PerformanceIndicatorModelMng performancModelMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private TProBasicRenameHistoryMng tProBasicRenameHistoryMng;
	@Autowired
	private TProBasicInfoMng projectMng;
	
	@Override
	public TProBasicInfo saveProject(TProBasicInfo bean, User user, String opeType, String lxyjFiles, String ssfaFiles, String fundsJson,String delIndex, String totalityPerformanceJson) throws Exception {
		//设置流转信息
		//得到第一个审批节点key
		String ywfw = null;
		if(bean.getFProOrBasic()==0){
			ywfw = "JBZCSB";
		}else if(bean.getFProOrBasic()==1 ){
			ywfw = "XMSB";
		}
 		Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), ywfw, user);
		//根据资源名称和当前登陆者所属部门查询对应工作流
		TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(ywfw, user.getDpID());
		Integer flowId= processDefin.getFPId();
		TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
		User nextUser=userMng.findById(node.getUserId());
		//设置下节点处理人
		bean.setNextAssignerName(nextUser.getName());
		bean.setNextAssignerCode(nextUser.getId());
		bean.setFExt11(firstKey+"");//设置下节点编码
		//设置审批状态 
		if ("zc".equals(opeType)) {		//暂存
			bean.setFFlowStauts("0");
		}else if("sb".equals(opeType)){	//送审
			bean.setFFlowStauts("11");
			if(nextUser !=null && nextUser.getRoleName().contains("预算管理岗")){
				bean.setFExportStauts(2);//已经到达预算管理岗 审批了，他就可以开始进行报表收报
			}else{
				bean.setFExportStauts(0);
			}
		}
		//设置所属库
		bean.setFProLibType("1");
		//设置常规信息
		if (bean.getFProId()==null) {//新增
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(new Date());
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(new Date());
			bean.setFProAppliTime(new Date());
			bean.setFProAppliPeopleId(user.getId().toString());//叶修改设置申请人ID
			bean.setFProAppliDepart(user.getDepartName());
			bean=(TProBasicInfo)super.saveOrUpdate(bean);
			String idCode=StringUtil.autoGenericCode(bean.getFProId()+"", 6);
			bean = (TProBasicInfo) super.saveOrUpdate(bean); //重新把编码放进去修改
		} else {
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(new Date());
			//设置保存状态1
			bean.setFStauts("1");
			Depart ProAppliDepart = departMng.findById(bean.getFProAppliDepartId());//申请人部门
			bean.setFProAppliDepart(ProAppliDepart.getName());
			String idCode=StringUtil.autoGenericCode(bean.getFProId()+"", 6);
			//保存项目信息
			super.updateDefault(bean);
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId, bean.getBeanCode());
		}
		//置顶操作
		setTopOne(bean);
		
		//送审
		if ("11".equals(bean.getFFlowStauts())) {
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(bean.getNextAssignerCode());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getFProId());//项目ID
			work.setTaskCode(bean.getFProCode());//项目编号
			if(bean.getFProOrBasic()==0){
				work.setTaskName("[基本支出申报]"+bean.getFProName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}else{
				work.setTaskName("[项目申报]"+bean.getFProName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}
			
			work.setUrl("/project/verdict/"+bean.getFProId());//审批地址
			work.setUrl1("/project/detail/"+ bean.getFProId());//查看地址（审批完成时使用）
			work.setTaskStauts("0");//待办
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(new Date());//任务提交时间
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getFProId());//项目ID
			work2.setTaskCode(bean.getFProCode());//项目编号
			if(bean.getFProOrBasic()==0){
				work2.setTaskName("[基本支出申报]"+bean.getFProName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}else{
				work2.setTaskName("[项目申报]"+bean.getFProName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}
			work2.setUrl("/project/edit/"+ bean.getFProId());//退回修改地址（被退回时使用）
			work2.setUrl1("/project/detail/"+ bean.getFProId());//查看地址
			work2.setUrl2("/project/delete/"+ bean.getFProId());//删除地址（被退回时使用）
			work2.setTaskStauts("2");//已办
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(new Date());//任务提交时间
			work2.setFinishTime(bean.getCreateTime());
			work2.setType("2");//任务类型（1查看）
			work2.setTaskType("0");//任务归属（0发起人）
			personalWorkMng.merge(work2);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,lxyjFiles);
		attachmentMng.joinEntity(bean,ssfaFiles);
		
		String strdelIndex[] = delIndex.split(",");
		List<TProExpendDetail> expendList = bean.getExpendList();
		// 保存项目支出明细
		tProExpendDetailMng.save(bean.getFProId(),expendList);
		// 保存项目计划
		//tProPlanMng.save(bean.getPlanList(), user, info);20190523  页面已经删除项目计划
		
		// 保存项目绩效目标总表
		//TProGoal goal = tProGoalMng.save(bean.getGoal(), info, user);20190523页面已经删除项目绩效目标
		
		// 保存资金来源明细
		JSONArray array = JSONArray.fromObject("[" + fundsJson.toString()+ "]");
		List<TProBasicFunds> fundsList = JSONArray.toList(array,TProBasicFunds.class);
		tProBasicFundsMng.save(bean.getFProId(),fundsList);
		
		if(bean.getFProOrBasic()==1){
			// 保存项目 绩效指标
			JSONArray jsonArr = JSONArray.fromObject("[" + totalityPerformanceJson.toString()+ "]");
			List<PerformanceIndicatorModel> indexList = JSONArray.toList(jsonArr,PerformanceIndicatorModel.class);
			performancModelMng.save(bean.getFProId(),indexList);
		}
		return bean;
	}
	
	private void setTopOne(TProBasicInfo bean) {
		
		//如果该项目置顶，则将其他置顶项目的序号变为99
		if (bean != null && "1".equals(bean.getFExt2())) {
			Finder f = Finder.create(" from TProBasicInfo where FExt2='1' ");
			List<TProBasicInfo> list = super.find(f);
			if (list != null && list.size() > 0) {
				for (TProBasicInfo pro: list) {
					pro.setFExt2("99");
					super.saveOrUpdate(pro);
				}
			}
		}
	}
	
	@Override
	public Pagination pageList(TProBasicInfo bean, User user, boolean isOffice, int pageNo, int pageSize) {
		
 		Finder finder = Finder.create(" from TProBasicInfo t1 where (FExt1 IS NULL OR FExt1!='0') and   (F_STAUTS IS NULL OR F_STAUTS!='99') ");
		//权限控制
		if (!isOffice) {
			//非单位用户：只能查看申报部门为本部门的
//			finder.append(" and FProAppliDepart=:departName ").setParam("departName", user.getDepartName());
		}
		//查询条件
 		if(!StringUtil.isEmpty(bean.getPlanStartYear())) {//预算年度
 			finder.append(" and t1.planStartYear like :planStartYear").setParam("planStartYear", "%" + bean.getPlanStartYear() + "%");
 		}
		if (!StringUtil.isEmpty(bean.getFProCode())) {//项目编码
			finder.append(" and t1.FProCode like :fProCode").setParam("fProCode", "%" + bean.getFProCode() + "%");
		}
		if (!StringUtil.isEmpty(bean.getFProName())) {//名称
			finder.append(" and t1.FProName like :name").setParam("name", "%" + bean.getFProName() + "%");
		}
		if(!StringUtil.isEmpty(bean.getFProClass())) {// 项目类别
			finder.append(" and t1.FProClass like :FProClass").setParam("FProClass", "%" + bean.getFProClass() + "%");
		}
		if(StringUtils.isNotEmpty(bean.getFProLibType())){//库别
			finder.append(" and t1.FProLibType = :FProLibType").setParam("FProLibType", bean.getFProLibType());
		}
		if(bean.getFProAppliTime1()!=null) {
			finder.append(" AND DATE_FORMAT(t1.FProAppliTime,'%Y-%m-%d') >= '"+bean.getFProAppliTime1()+"'");//申报开始时间
		}
		if(bean.getFProAppliTime2()!=null) {
			finder.append(" AND DATE_FORMAT(t1.FProAppliTime,'%Y-%m-%d') <= '"+bean.getFProAppliTime2()+"'");//申报结束时间
		}
		
		if(!StringUtil.isEmpty(String.valueOf(bean.getEfficiency1()))) {
			finder.append(" AND EXISTS (SELECT t2.indexCode FROM TBudgetIndexMgr t2 where t1.FProCode=t2.indexCode AND (1 - ((t2.syAmount+t2.djAmount) / t2.pfAmount))>='"+bean.getEfficiency1()+"')");
		}
		if(!StringUtil.isEmpty(String.valueOf(bean.getEfficiency2()))) {
			finder.append(" AND EXISTS (SELECT t2.indexCode FROM TBudgetIndexMgr t2 where t1.FProCode=t2.indexCode AND (1 - ((t2.syAmount+t2.djAmount) / t2.pfAmount))<='"+bean.getEfficiency2()+"')");
		}
		
		if(bean.getFFlowStauts()!=null && bean.getFFlowStauts()!="") {//审批状态
			/*if("11".equals(bean.getFFlowStauts())) {
				finder.append(" and t1.FFlowStauts in('11','12')");
			}*/
			finder.append(" and t1.FFlowStauts='"+bean.getFFlowStauts()+"'");
		}
		if (!StringUtil.isEmpty(bean.getFirstLevelCode())) {//一级分类
			finder.append(" and t1.firstLevelCode=:firstLevelCode ").setParam("firstLevelCode", bean.getFirstLevelCode());
		}
		if (!StringUtil.isEmpty(bean.getSecondLevelCode())) {//二级分类
			finder.append(" and t1.secondLevelCode=:secondLevelCode ").setParam("secondLevelCode", bean.getSecondLevelCode());
		}
		if (!StringUtil.isEmpty(bean.getFProAppliDepart())) {//申报部门
			finder.append(" and t1.FProAppliDepart like :FProAppliDepart").setParam("FProAppliDepart", "%" + bean.getFProAppliDepart() + "%");
		}
		//审批状态
		if (!StringUtil.isEmpty(bean.getFFlowStauts())) {
			finder.append(" and t1.FFlowStauts = :flowStatus").setParam("flowStatus", bean.getFFlowStauts());
		}
		//预算支出类型     区分基本支出或项目支出：0=基本支出，1=项目支出
		if (!StringUtil.isEmpty(String.valueOf(bean.getFProOrBasic()))) {
			finder.append(" and t1.FProOrBasic = :FProOrBasic").setParam("FProOrBasic", bean.getFProOrBasic());
		}
		if(user.getRoleName().contains("预算台账查看岗")){
			//预算管理查看岗可以查看所有部门的台账
			if(!StringUtil.isEmpty(bean.getFProAppliPeople())){
				finder.append(" and t1.FProAppliPeople=:FProAppliPeople").setParam("FProAppliPeople", bean.getFProAppliPeople());
			}
		}else{
			String deptIdStr=departMng.getDeptIdStrByUser(user);
	 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
	 			finder.append(" and t1.FProAppliPeopleId = :FProAppliPeopleId").setParam("FProAppliPeopleId", user.getId());
	 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
	 			
	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
	 			finder.append(" and t1.FProAppliDepartId in ("+deptIdStr+")");
	 		}
		}
		if (!StringUtil.isEmpty(bean.getSbkLx())) {//申报库类型
			if ("xmsb".equals(bean.getSbkLx())) {
				//项目申报-	 申报人是自己的
				finder.append(" and t1.FProAppliPeopleId=:applyUser ").setParam("applyUser", user.getId());
			} else if ("xmsp".equals(bean.getSbkLx())) {
				//项目审批-	显示审批状态为1、2的数据 +  审核人或者申报人是自己的 
				finder.append(" and  t1.nextAssignerCode=:userId ")
				.setParam("userId", user.getId());
				/*finder.append(" and (t1.FFlowStauts = '11' or t1.FFlowStauts='19' or t1.FFlowStauts='12') and t1.nextAssignerCode=:userId ")
					.setParam("userId", user.getId());*/
			} else if ("xmtz".equals(bean.getSbkLx())) {
				//项目台账-
				finder.append(" and t1.FFlowStauts not in('0','-11') ");
			} else if ("ysshoub".equals(bean.getSbkLx())) {
				//项目台账-
				finder.append(" and t1.FFlowStauts not in('0','-11') ");
			} else if ("myzxk".equals(bean.getSbkLx())) {
				//我的执行库，申报人是自己的 
				finder.append(" and t1.FProAppliPeopleId = :FProAppliPeopleId").setParam("FProAppliPeopleId", user.getId());
			}else if ("xmfh".equals(bean.getSbkLx())) {
				//项目复核-	显示所有审核通过的数据
				finder.append(" and t1.FFlowStauts = '3' ");
			} else if ("bmxmjx".equals(bean.getSbkLx())) {
				//部门项目结项-	显示本部门的 + 执行库的 + 未结项的
				finder.append(" and t1.FProAppliDepart=:departName ").setParam("departName", user.getDepartName());
				finder.append(" and t1.FProLibType=3  ");
			} else if ("dwxmjx".equals(bean.getSbkLx())) {
				//单位项目结项- 执行库或结转的 +  未结项的
				finder.append(" and t1.FProLibType=3 ");
			}
		}
		//查询该某审核人的所有审核任务
//		if (!StringUtil.isEmpty(bean.getNextAssignerId())) {
//			finder.append(" and nextAssignerId =:nextAssignerId").setParam("nextAssignerId", bean.getNextAssignerId());
//		}
		if (bean.getFProOrBasic()!=null) {
			finder.append(" and t1.FProOrBasic = '"+bean.getFProOrBasic()+"'");
		}
		if(bean.getFExportStauts()!=null || !StringUtil.isEmpty(String.valueOf(bean.getFExportStauts()))) {//审批状态
			finder.append(" and t1.FExportStauts='"+bean.getFExportStauts()+"'");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public List<Object[]> getOutComeSubject(String basicType, String parentCode, String year, String qName) {
		String sql = " select F_EC_ID,F_EC_NAME,F_EC_CODE,F_P_EC_ID "
				+ " from T_ECONOMIC_SUBJECT_LIB "
				+ " inner join T_YEARS_BASIC "
				+ " on T_ECONOMIC_SUBJECT_LIB.F_Y_B_ID = T_YEARS_BASIC.F_Y_B_ID "
				+ " where F_EC_TYPE='KMLX-01' ";
		
		if (!StringUtil.isEmpty(basicType)) {
			if ("personOut".equals(basicType)) {//人员基本支出
				sql = sql + " and (F_EC_CODE like '301%' or F_EC_CODE like '303%')  ";
			} else if ("commOut".equals(basicType)) {//公用基本支出
				sql = sql + " and F_EC_CODE like '302%' ";
			}else if ("project".equals(basicType)) {//项目支出
				sql = sql + " and F_EC_CODE in (302,310,301,303) ";
			}
		}
		if (!StringUtil.isEmpty(year)) {//年度
			sql = sql + " and T_YEARS_BASIC.F_PERIOD=" + year;
		}
		if (!StringUtil.isEmpty(parentCode)) {//父节点
			sql = sql + " and F_P_EC_ID=" + parentCode;
		}
		if (!StringUtil.isEmpty(qName)) {//查询-节点名称
			sql = sql + " and F_EC_NAME like '%" + qName + "%'";
		}
		
		sql = sql + " order by F_EC_CODE asc ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}
	@Override
	public List<Object[]> getOutComeSubject(String year, String codes) {
		String sql = " select F_EC_ID,F_EC_NAME,F_EC_CODE,F_P_EC_ID "
				+ " from T_ECONOMIC_SUBJECT_LIB "
				+ " inner join T_YEARS_BASIC "
				+ " on T_ECONOMIC_SUBJECT_LIB.F_Y_B_ID = T_YEARS_BASIC.F_Y_B_ID "
				+ " where 1=1 ";
		
		if (!StringUtil.isEmpty(year)) {//年度
			sql = sql + " and T_YEARS_BASIC.F_PERIOD=" + year;
		}
		if (!StringUtil.isEmpty(codes)) {//编码\
			sql = sql + " and F_EC_CODE in ("+codes+")" ;
		}
		sql = sql + " order by F_EC_CODE asc ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}
	@Override
	public Map<String,Integer> getPidMap(String basicType, String parentCodes, String year, String qName) {
		Map<String,Integer> pidMap=new HashMap<String,Integer>();
		List<Integer> pidList=getPidListByparentCodes(basicType,parentCodes,year,qName);
		if(pidList!=null && pidList.size()>0){
			for(Integer pid:pidList){
				pidMap.put(pid+"", pid);
			}
		}
		return pidMap;
	}
	public List<Integer> getPidListByparentCodes(String basicType, String parentCodes, String year, String qName){
		String sql = " select F_P_EC_ID  "
				+ " from T_ECONOMIC_SUBJECT_LIB "
				+ " inner join T_YEARS_BASIC "
				+ " on T_ECONOMIC_SUBJECT_LIB.F_Y_B_ID = T_YEARS_BASIC.F_Y_B_ID "
				+ " where F_EC_TYPE='KMLX-01' ";
		
		if (!StringUtil.isEmpty(basicType)) {
			if ("personOut".equals(basicType)) {//人员基本支出
				sql = sql + " and (F_EC_CODE like '301%' or F_EC_CODE like '303%')  ";
			} else if ("commOut".equals(basicType)) {//公用基本支出
				sql = sql + " and F_EC_CODE like '302%' ";
			}
		}
		if (!StringUtil.isEmpty(year)) {//年度
			sql = sql + " and T_YEARS_BASIC.F_PERIOD=" + year;
		}
		if (!StringUtil.isEmpty(parentCodes)) {//父节点
			sql = sql + " and F_P_EC_ID in (" + parentCodes+")";
		}
		if (!StringUtil.isEmpty(qName)) {//查询-节点名称
			sql = sql + " and F_EC_NAME like '%" + qName + "%'";
		}
		
		sql = sql + " order by F_EC_CODE asc ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}
	
	@Override
	public String downFile(String type, String fileName, String savePath) {
		
		if (!StringUtil.isEmpty(type)) {
			if ("proAcco".equals(type)) {
				tProBasicAccoAttacMng.downloadAtta(fileName,savePath);
			} else if ("proPlan".equals(type)) {
//				tProBasicPlanAttacMng.downloadAtta(fileName,savePath);
			} else if ("proOutcome".equals(type)) {
				
			}
		}
		return "0";
	}
	@Override
	public Pagination fProCode(TProBasicInfo tProBasicInfo, int pageNo,int pageSize) {
		
		Finder finder = Finder.create(" from TProBasicInfo where 1=1 and FProLibType = '3' ");//执行库
		//查询条件
		if (!StringUtil.isEmpty(tProBasicInfo.getFProCode())) {//项目编码
			finder.append(" and FProCode like :fProCode").setParam("fProCode", "%" + tProBasicInfo.getFProCode() + "%");
		}
		if (!StringUtil.isEmpty(tProBasicInfo.getFProName())) {//名称
			finder.append(" and FProName like :name").setParam("name", "%" + tProBasicInfo.getFProName() + "%");
		}
		if (!StringUtil.isEmpty(tProBasicInfo.getFProAppliDepart())) {//申报部门
			finder.append(" and FProAppliDepart = :FProAppliDepart").setParam("FProAppliDepart", "%" + tProBasicInfo.getFProAppliDepart() + "%");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public void overPro(User user, String proId) {
		
		TProBasicInfo bean = findById(Integer.valueOf(proId));
		bean.setUpdator(user.getAccountNo());
		bean.setUpdateTime(new Date());
		//结转
		bean.setFProLibType("4");
		/*saveOrUpdate(bean);*/
	}
	
	@Override
	public List<TProBasicInfo> proInfoForControlNum(Date date, String libType) {
		
		Finder finder = Finder.create(" from TProBasicInfo where 1=1");
		if (date != null) {
			//年份
			Integer year=1+Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
			finder.append(" and planStartYear= '"+year+"'");
		}
		if (!StringUtil.isEmpty(libType)) {
			finder.append(" and FProLibType =:libType ").setParam("libType", libType);
		}
		finder.append(" order by FExt2 asc,FProCode asc ");
		return super.find(finder);
	}

	@Override
	public TProBasicInfo findbyCode(String code) {
		Finder f=Finder.create(" from TProBasicInfo where FProCode='"+code+"'");
		List<TProBasicInfo> list= super.find(f);
		if(list==null || list.size()==0){
			return new TProBasicInfo();
		}
		return (TProBasicInfo) super.find(f).get(0);
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * 一上分页查询
	 * @param bean 实体对象，存放查询条件
	 * @param user 操作人
	 * @param isOffice 是否单位角色
	 * @param pageNo 当前页码
	 * @param pageSize 总页数
	 * @return
	 */
	@Override
	public Pagination yspageList(TProBasicInfo bean, User user, int pageNo, int pageSize,String sbkLx) {
		Finder finder = Finder.create(" from TProBasicInfo where (FExt1 IS NULL OR FExt1!='0') ");
		finder.append(" and FProLibType ='2' and FFlowStauts in('19','21','29') and FExt4 in('10','11')");
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	/**
	 * 一下查询
	 * @param bean 实体对象，存放查询条件
	 * @param user 操作人
	 * @param isOffice 是否单位角色
	 * @param pageNo 当前页码
	 * @param pageSize 总页数
	 * @return
	 */
	@Override
	public Pagination yxpageList(TProBasicInfo bean, User user, int pageNo, int pageSize, String sbkLx) {
		Finder finder = Finder.create(" from TProBasicInfo where (FExt1 IS NULL OR FExt1!='0') ");
		finder.append(" and FProLibType ='2' and FFlowStauts ='29' and FExt4='11'");
		return super.find(finder, pageNo, pageSize);
	}

	
	/*
	 * 叶崇晖
	 * 9/17
	 * 一上批量申报
	 */
	@Override
	public void yssb(String fproIdLi,String fproIdLi2) {
		String[] strarray2 = fproIdLi2.split(",");
		for (int i = 0; i < strarray2.length; i++) {
			//修改审批状态为21
			getSession().createSQLQuery("UPDATE t_pro_basic_info SET F_FLOW_STAUTS='21' WHERE F_PRO_ID='"+strarray2[i]+"'").executeUpdate();
		}
		
		
		String[] strarray=fproIdLi.split(","); 
		for (int i = 0; i < strarray.length; i++) {
			//修改申报状态为11
			getSession().createSQLQuery("UPDATE t_pro_basic_info SET F_EXT_4='11' WHERE F_PRO_ID='"+strarray[i]+"'").executeUpdate();
		}
	}

	@Override
	public void yssp(String fproIdLi) {
		String[] strarray=fproIdLi.split(","); 
		for (int i = 0; i < strarray.length; i++) {
			//修改审批状态为21
			getSession().createSQLQuery("UPDATE t_pro_basic_info SET F_FLOW_STAUTS='29' WHERE F_PRO_ID='"+strarray[i]+"'").executeUpdate();
		}
	}

	/*
	 * 叶崇晖
	 * 9/17
	 * 一下
	 */
	@Override
	public void yx(String fproIdLi, String commitAmountLi, String fext12Li) {
		String[] strarray1=fproIdLi.split(","); 
		String[] strarray2=commitAmountLi.split(","); 
		String[] strarray3=fext12Li.split(","); 
		for (int i = 0; i < strarray1.length; i++) {
			//保存一下控制数和备注
			getSession().createSQLQuery("UPDATE t_pro_basic_info SET F_CB_1='"+Double.valueOf(strarray2[i])+"',F_EXT_12='"+strarray3[i]+"' WHERE F_PRO_ID='"+strarray1[i]+"'").executeUpdate();
		}
	}



	@Override
	public Pagination proInfoForControlNumP(TProBasicInfo bean,Date date, String libType,Integer pageNo, Integer pageSize) {
		Finder finder = Finder.create(" from TProBasicInfo where 1=1");
		if (!StringUtil.isEmpty(bean.getFProCode()) ) {//项目编号
			finder.append(" and FProCode LIKE :FProCode");
			finder.setParam("FProCode", "%"+bean.getFProCode()+"%");
		}
		if (!StringUtil.isEmpty(bean.getFProName())) {//项目名称
			finder.append(" and FProName LIKE :FProName");
			finder.setParam("FProName", "%"+bean.getFProName()+"%");
		}
		if (!StringUtil.isEmpty(bean.getFProAppliDepart())) {//部门
			finder.append(" and FProAppliDepart LIKE :FProAppliDepart");
			finder.setParam("FProAppliDepart", "%"+bean.getFProAppliDepart()+"%");
		}
		finder.append(" order by FExt2 asc,FProCode asc ");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public List<TProBasicInfo> getList() {
		
		return null;
	}

	/**
	 * 将前台传入的项目支出明细转换成list对象
	 * @author 叶崇晖
	 * @param file
	 * @return
	 */
	@Override
	public List<TProExpendDetail> outcomeCollect(File file) {
		List<TProExpendDetail> list = new ArrayList<TProExpendDetail>();
		PoiUtil pu = new PoiUtil();//excel数字行格式工具类
		
		if (file.exists()) {
			FileInputStream fis = null;
			Workbook workBook = null;//生成poi的execlWorkbook
			try {
				fis = new FileInputStream(file);
				workBook = WorkbookFactory.create(fis);
				Sheet sheet = workBook.getSheetAt(0);//只有一个sheet页
				int rowsOfSheet = sheet.getPhysicalNumberOfRows(); // 获取当前Sheet的总行数
				
				Map<Object,Object> nameCodeMap=economicMng.getCodeMap();
				for (int r = 1; r < rowsOfSheet; r++) { // 总行
					TProExpendDetail detail = new TProExpendDetail();//创建项目支出明细对象
					Row row = sheet.getRow(r);//获得行
					if (row == null) {
						continue;
					} else {
						int numberOfCells = row.getPhysicalNumberOfCells();
						for (int c = 0; c < numberOfCells; c++) {
							Cell cell = row.getCell(c);//获得单元格
							if (c == 1) {
								//项目活动单元格
								//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								detail.setActivity(stringCellValue);
							}
							else if (c == 2) {
								//分项支出单元格
								//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								stringCellValue = StringUtil.toCH(stringCellValue);
								//List<Economic> economicList = economicMng.outcomeFindByName(stringCellValue);
								//对stringCellValue进行去空格，特殊符号，英文字母
								Object code=nameCodeMap.get(stringCellValue);
								if(code!=null) {
									detail.setSubName(stringCellValue);
									detail.setSubCode(code.toString());
								} else {
									detail.setSubName(null);
									detail.setSubCode(null);
								}
								
							}
							else if (c == 3) {
								//支出计划(万元)
								String stringCellValue = pu.getCellValue(cell);
								Double value=0d;
								if(StringUtils.isNotEmpty(stringCellValue)){
									value=Double.parseDouble(stringCellValue);
								}
								detail.setOutAmount(value);
							}
							else if (c == 4) {
								//摘要单元格
								//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								detail.setActDesc(stringCellValue);
							}
							/*else if (c == 5) {
								//子活动单元格
								//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								detail.setSonActivity(stringCellValue);
							}
							else if (c == 6) {
								//对子活动的描述单元格
								//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								detail.setSonActDesc(stringCellValue);
							}*/
							else if (c == 5) {
								break;
							}
							
						}
						list.add(detail);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}finally{
				System.gc();
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
		return list;
	}

	
	
	@Override
	public void saveCheck(TProBasicInfo bean, User user, TProcessCheck checkBean,String files)  throws Exception{
		bean=this.findById(bean.getFProId());
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/project/verdict/";
		String lookUrl ="/project/detail/";
		String ywfw = "XMSB";
		if(bean.getFProOrBasic()==0){
			ywfw = "JBZCSB";
		}
		bean=(TProBasicInfo)tProcessCheckMng.checkProcess(checkBean,entity,user,ywfw,checkUrl,lookUrl,files);
		User nextUser=userMng.findById(bean.getNextCheckUserId());
		if(nextUser !=null && nextUser.getRoleName().contains("预算管理岗")){
			bean.setFExportStauts(2);//已经到达预算管理岗 审批了，他就可以开始进行报表收报
		}
		//取出来以后重新设置值，因为项目申报的状态属性不一样
		if("-1".equals(bean.getFFlowStauts())){//打回原点
			bean.setFFlowStauts("-11");
			bean.setFExportStauts(0);
		}else if("9".equals(bean.getFFlowStauts())){//审批结束
			bean.setFFlowStauts("19");
			if(0==bean.getFProOrBasic()){
				//基本支出
				bean.setFProLibType("2");//所属库 1：备选库 2：上报库   3：执行中 4：已完结
				bean.setFExt6("2");//项目评审状态   0、未评审 1、待评 2、已通过 -1、已退回
				bean.setFExt8(DateUtil.formatDate(new Date()));//评审时间   年-月-日
				bean.setFExt4("11");//上报状态 20、二上未申报   21、二上已申报
				bean.setFExt5(null);
				bean.setFFlowStauts("29");
			}else if(1==bean.getFProOrBasic()){
				//项目支出
				bean.setFProLibType("2");//所属库 1：备选库 2：上报库   3：执行中 4：已完结
				bean.setFExt6("0");//项目评审状态  0 未评审  ，
			}
			//复核  给预算管理岗推送消息
			String title = "有新的项目已审批成功,请及时反馈!";
			String msg = "申报的项目名称:"+bean.getFProName()+",编号:"+bean.getFProCode()+",已审批成功!";
			User u1 = userMng.getUserByRoleNameAndDepartName("预算管理岗", "财务处");
			privateInforMng.setMsg(title, msg, u1.getId(), "2");
		}else{
			bean.setFFlowStauts("12");
		}
		super.saveOrUpdate(bean);

	}

	@Override
	public HSSFWorkbook exportExcel(List<TProBasicInfo> dataList,
			String filePath) {
		
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			if (dataList != null && dataList.size() > 0) {
				HSSFRow row = sheet0.getRow(2);//格式行
				for (int i = 0 ; i < dataList.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(2+i);
					hssfRow.setHeight(row.getHeight());
					TProBasicInfo data = dataList.get(i);
					
					HSSFCell cell0 = hssfRow.createCell(0);
					HSSFCell cell1 = hssfRow.createCell(1);
					HSSFCell cell2 = hssfRow.createCell(2);
					HSSFCell cell3 = hssfRow.createCell(3);
					HSSFCell cell4 = hssfRow.createCell(4);
					HSSFCell cell5 = hssfRow.createCell(5);
					HSSFCell cell6 = hssfRow.createCell(6);
					HSSFCell cell7 = hssfRow.createCell(7);
					HSSFCell cell8 = hssfRow.createCell(8);
					HSSFCell cell9 = hssfRow.createCell(9);
					HSSFCell cell10 = hssfRow.createCell(10);
					HSSFCell cell11 = hssfRow.createCell(11);
					HSSFCell cell12 = hssfRow.createCell(12);
					HSSFCell cell13 = hssfRow.createCell(13);
					HSSFCell cell14 = hssfRow.createCell(14);
					HSSFCell cell15 = hssfRow.createCell(15);
					HSSFCell cell16 = hssfRow.createCell(16);
					/*HSSFCell cell17 = hssfRow.createCell(17);
					HSSFCell cell18 = hssfRow.createCell(18);
					HSSFCell cell19 = hssfRow.createCell(19);
					HSSFCell cell20 = hssfRow.createCell(20);
					HSSFCell cell21 = hssfRow.createCell(21);
					HSSFCell cell22 = hssfRow.createCell(22);
					HSSFCell cell23 = hssfRow.createCell(23);*/
					
					cell0.setCellValue(i + 1);
					cell1.setCellValue(data.getFProCode());
					cell2.setCellValue(data.getFProName());
					cell3.setCellValue(data.getFProOrBasic()==0?"基本支出":"项目支出");
					cell4.setCellValue(data.getFirstLevelName());
					cell5.setCellValue(data.getFirstLevelCode());
					cell6.setCellValue(data.getSecondLevelName());
					cell7.setCellValue(data.getSecondLevelCode());
					cell8.setCellValue(data.getFProBudgetAmount());
					cell9.setCellValue(data.getFProAppliDepart());
					cell10.setCellValue(DateUtil.formatDate(data.getFProAppliTime(), "yyyy-MM-dd"));
					cell11.setCellValue(data.getFProAppliPeople());
					cell12.setCellValue(data.getFProAppliPeople());
					cell13.setCellValue(data.getHeaderPhone());
					cell14.setCellValue(data.getPlanStartYear());
					String FProStauts = data.getFProStauts();
					if("0".equals(FProStauts)){
						FProStauts = "暂存";
					}else if("-1".equals(FProStauts)){
						FProStauts = "已退回";
					}else if("9".equals(FProStauts)){
						FProStauts = "已完结";
					}else if("1".equals(FProStauts)){
						FProStauts = "待审批";
					}else {
						FProStauts = "未结项";
					}
					cell15.setCellValue(FProStauts);
					cell16.setCellValue("1".equals(data.getFExportStauts())?"已收报":"未收报");
					/*cell14.setCellValue(data.getFunctionType());
					cell15.setCellValue(StringUtil.numToCh(data.getFContinuousYn()));
					cell16.setCellValue(StringUtil.numToCh(data.getIsMerge()));
					cell17.setCellValue(StringUtil.numToCh(data.getIsMerge()));
					cell18.setCellValue(StringUtil.numToCh(data.getIsOrientation()));
					cell19.setCellValue(data.getPlanStartYear());
					cell20.setCellValue(data.getFProRollingCycle());
					cell21.setCellValue(data.getSecretLevel());
					cell22.setCellValue(data.getSecretDeadline());
					cell23.setCellValue(StringUtil.numToCh(data.getFProStauts()));*/
					
					cell0.setCellStyle(row.getCell(0).getCellStyle());
					cell1.setCellStyle(row.getCell(1).getCellStyle());
					cell2.setCellStyle(row.getCell(2).getCellStyle());
					cell3.setCellStyle(row.getCell(3).getCellStyle());
					cell4.setCellStyle(row.getCell(4).getCellStyle());
					cell5.setCellStyle(row.getCell(5).getCellStyle());
					cell6.setCellStyle(row.getCell(6).getCellStyle());
					cell7.setCellStyle(row.getCell(7).getCellStyle());
					cell8.setCellStyle(row.getCell(8).getCellStyle());
					cell9.setCellStyle(row.getCell(9).getCellStyle());
					cell10.setCellStyle(row.getCell(10).getCellStyle());
					cell11.setCellStyle(row.getCell(11).getCellStyle());
					cell12.setCellStyle(row.getCell(12).getCellStyle());
					cell13.setCellStyle(row.getCell(13).getCellStyle());
					cell14.setCellStyle(row.getCell(14).getCellStyle());
					cell15.setCellStyle(row.getCell(15).getCellStyle());
					cell16.setCellStyle(row.getCell(16).getCellStyle());
				}
			}
			return wb;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public void deleteProject(Integer id,String fId,User user) {
		//事前申请的状态为99（删除）
		personalWorkMng.deleteById(Integer.valueOf(fId));
		getSession().createSQLQuery("UPDATE t_pro_basic_info SET F_STAUTS=99 WHERE F_PRO_ID="+id).executeUpdate();
		personalWorkMng.sendMessageToUser(user.getId(), 0);
	}

	@Override
	public void filesUpdate(String id, String filesName) {
		TProBasicInfo bean = findById(Integer.valueOf(id));
		attachmentMng.joinEntity(bean,filesName);
	}

	@Override
	public TProBasicInfo oversave(TProBasicInfo bean,String jxmingxi) {
		String rollingCycle = bean.getFProRollingCycle();
		
		bean = super.findById(bean.getFProId());
		bean.setFProRollingCycle(rollingCycle);
		bean = (TProBasicInfo) super.merge(bean);
		
		return bean;
	}
	
	/**
	 * 合同延期预警列表
	 * **/
	@Override
	public Pagination proDelayList(int pageNo, int pageSize,String fProCode,String fProName,String deptName) {
		/*
		 * 获取三个月之后时间
		 */
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		Calendar c = Calendar.getInstance();
	    c.setTime(new Date());
	    c.add(Calendar.MONTH, 3);
	    String newDate = df.format(c.getTime());
		
		StringBuilder sb=new StringBuilder("SELECT t1.F_CONT_CODE fcCode,t1.F_CONT_TITLE fcTitle,F_AMOUNT fContAmount,t1.F_DEPT_NAME deptName,F_RECE_PROPERTY fReceProperty,F_RECE_STAGE fReceStage,DATE_FORMAT(t3.F_RECE_PLAN_TIME,'%Y-%m-%d') fRecePlanTime,t1.F_CONT_ID fcId,datediff(DATE_FORMAT(t3.F_RECE_PLAN_TIME,'%Y-%m-%d'),DATE_FORMAT(now(), '%Y-%m-%d')) delayDays");
		sb.append("	FROM T_CONTRACT_BASIC_INFO t1 inner join t_budget_index_mgr t2 on t1.F_BUDGET_INDEX_CODE=t2.F_B_ID and t2.F_INDEX_TYPE =1 inner join T_RECEIV_PLAN t3 on t1.F_CONT_ID=t3.F_CONT_ID");
		sb.append(" and t3.F_RECE_TIME is null");
		sb.append(" and t3.F_RECE_PLAN_TIME <= str_to_date(" + newDate + ",'YYYY-MM-DD')"); 
		//按合同编号模糊搜索
		if(!StringUtil.isEmpty(fProCode)){
			sb.append(" AND t1.F_CONT_CODE LIKE('%"+fProCode+"%')");
		}
		//按合同名称模糊搜索
		if(!StringUtil.isEmpty(fProName)){
			sb.append(" AND t1.F_CONT_TITLE LIKE('%"+fProName+"%')");
		}
		//按部门名称模糊搜索
		if(!StringUtil.isEmpty(deptName)){
			sb.append(" AND t1.F_DEPT_NAME LIKE('%"+deptName+"%')");
		}
		sb.append(" order by t3.F_RECE_PLAN_TIME");
		String str=sb.toString();
		Pagination p=super.findObjectListBySql(str, pageNo, pageSize);
		List<Object[]> dataList = (List<Object[]>) p.getList();
		List<ProjectDelay>list=new ArrayList<>();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (Object[] obj: dataList) {
				ProjectDelay t=new ProjectDelay();
				t.setfProCode(String.valueOf(obj[0]));
				t.setfProName(String.valueOf(obj[1]));
				t.setfContAmount(Double.valueOf((String) obj[2]));
				t.setdeptName(String.valueOf(obj[3]));
				t.setfReceProperty(String.valueOf(obj[4]));
				t.setfReceStage(String.valueOf(obj[5]));
				t.setfRecePlanTime(String.valueOf(obj[6]));		
				t.setfcId(Integer.valueOf(String.valueOf(obj[7])));
				t.setdelayDays(Integer.valueOf(String.valueOf(obj[8])));
				t.setDayNum(Integer.valueOf(String.valueOf(obj[8])));
				t.setNum(pageNo * pageSize + i - 9);
				i++;
				list.add(t);
			}
			p.setList(list);
		}
		return p;
	}

	@Override
	public TProBasicInfo saveReview(TProBasicInfo bean,User user) {
		
		//保存项目信息
		TProBasicInfo updateinfo=(TProBasicInfo)super.findById(bean.getFProId());
		//新增复核历史记录
		tProBasicRenameHistoryMng.saveHistory(updateinfo,bean.getFProName(),user);
		updateinfo.setFProName(bean.getFProName());
		//复核  给项目申报人推送消息
		String title="您申报的项目信息有更新";
		String msg="您申报的项目名称:"+updateinfo.getFProName()+",编号:"+updateinfo.getFProCode()+",项目名称被变更为:"+bean.getFProName();
		String userId=updateinfo.getUserId();
		privateInforMng.setMsg(title, msg, userId,"3");
		TProBasicInfo info = (TProBasicInfo) super.saveOrUpdate(updateinfo);
		return info;
	}

	@Override
	public List<TProBasicInfo> getbasiProByIds(String ids) {
		
		List<TProBasicInfo> list=new ArrayList<TProBasicInfo>();
		if(StringUtils.isNotEmpty(ids)){
			Finder finder = Finder.create(" from TProBasicInfo where FProId in ("+ids+")");
			return super.find(finder);
		}
		return list;
	}
	
	@Override
	public void updateExportStatesByIds(String ids){
		String sql="update t_pro_basic_info set F_EXPORT_STAUTS = 1 where f_pro_id in ("+ids+")";
		Query query=getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	public String reCall(Integer id,User user) throws Exception {
		//根据id查询对象
		TProBasicInfo bean=(TProBasicInfo)super.findById(id);
		//如果已经被收报，无法撤销,只有预算管理岗才能撤销
		if(bean.getFExportStauts() !=null && bean.getFExportStauts()==1 && !user.getRoleName().contains("预算管理岗")){
			throw new ServiceException("该项目已经被收报，无法撤销");
		}
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="[项目申报]申请被撤回消息";
		if(bean.getFProOrBasic()==0){
			title="[基本支出申报]申请被撤回消息";
		}
		String msg="您待审批的  "+bean.getFProName() +",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean.setCheckStauts("-14");//已撤回
		bean.setStauts("0");//暂存
		bean.setNextCheckKey("0");//设置到初始节点
		bean.setNextCheckUserId("");
		bean.setFProLibType("1");//设置返回备选库
		bean.setNextCheckUserName("");
		bean.setFExportStauts(0);//收报状态
		this.updateDefault(bean);
		return "操作成功";
	}

	@Override
	public void receiveReport(String receiveIdList, String fExportStauts) {
		//选中项收报
		String[] reStrarray = receiveIdList.split(",");
		if(reStrarray.length>0 && StringUtils.isNotEmpty(reStrarray[0])){
			for (int i = 0; i < reStrarray.length; i++) {
				TProBasicInfo proBean = projectMng.findById(Integer.valueOf(reStrarray[i]));
				proBean.setFExportStauts(Integer.valueOf(fExportStauts));
				super.saveOrUpdate(proBean);
			}
		}
			
	}

	@Override
	public boolean checkSecondLevelName(String departId, String year, String code,String FProCode) {
		Finder finder = Finder.create(" FROM TProBasicInfo WHERE (stauts <> 99 and stauts is not null)");
		if(!StringUtil.isEmpty(departId)){
			finder.append(" AND FProAppliDepartId='"+departId+"'");
		}
		if(!StringUtil.isEmpty(year)){
			finder.append(" AND planStartYear='"+year+"'");
		}
		if(!StringUtil.isEmpty(code)){
			finder.append(" AND secondLevelCode='"+code+"'");
		}
		List<TProBasicInfo> list = super.find(finder);
		if(list != null && list.size() > 0){//项目是否存在
			if(!StringUtil.isEmpty(FProCode)){//项目的编号不为空	null-新增	有值-暂存后修改送审、审批中修改通过
				if(list.get(0).getFProCode().equals(FProCode)){//判断是否为同一个项目, 项目编号一致，说明是同一个项目，未重复
					return true;
				}
			}
		}else {//项目不存在
			return true;
		}
		return false;
	}

	@Override
	public boolean checkSecondCode(TProBasicInfo bean) {
		List<TProExpendDetail> beanList = bean.getExpendList();
		String secondLevelCode = bean.getSecondLevelCode();
		
		for (int i = 0; i < beanList.size(); i++) {
			String subCode = beanList.get(i).getSubCode().substring(0, 3);
			//如果不相等则证明选择的经济分类科目不是该二级指标下的数据返回true
			if(!secondLevelCode.equals(subCode)){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<TProBasicInfo> getProAllList(String name, String selected) {
		Finder finder = Finder.create(" from TProBasicInfo ");
		return super.find(finder);
	}

	@Override
	public List<TProBasicInfo> yearAndDeptQueryPro(String year, String dept) {
		Finder finder = Finder.create(" from TProBasicInfo where FProId in(select FProId from TBudgetIndexMgr where years='"+year+"' and deptCode='"+dept+"') and FProOrBasic=1");
		return super.find(finder);
	}
}
