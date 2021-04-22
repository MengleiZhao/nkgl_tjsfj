package com.braker.icontrol.expend.apply.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.expend.apply.manager.ApplyAttacMng;
import com.braker.icontrol.expend.apply.manager.ApplyCheckMng;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.manager.TravelAppliInfoMng;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.AbroadPlan;
import com.braker.icontrol.expend.apply.model.ApplicationAttac;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
import com.braker.icontrol.expend.apply.model.FeteCostInfo;
import com.braker.icontrol.expend.apply.model.FoodAllowanceInfo;
import com.braker.icontrol.expend.apply.model.HotelExpenseInfo;
import com.braker.icontrol.expend.apply.model.InCityTrafficInfo;
import com.braker.icontrol.expend.apply.model.InternationalTravelingExpense;
import com.braker.icontrol.expend.apply.model.LecturerInfo;
import com.braker.icontrol.expend.apply.model.MeetPlan;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.MiscellaneousFeeInfo;
import com.braker.icontrol.expend.apply.model.OfficeCar;
import com.braker.icontrol.expend.apply.model.OutsideTrafficInfo;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.ReceptionChargeInfo;
import com.braker.icontrol.expend.apply.model.ReceptionFood;
import com.braker.icontrol.expend.apply.model.ReceptionHotel;
import com.braker.icontrol.expend.apply.model.ReceptionOther;
import com.braker.icontrol.expend.apply.model.ReceptionPeopleInfo;
import com.braker.icontrol.expend.apply.model.ReceptionStrokPlan;
import com.braker.icontrol.expend.apply.model.TrainTeacherCost;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.standard.manager.HotelStandardMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 事前申请的service实现类
 * @author 叶崇晖
 * @createtime 2018-06-12
 * @updatetime 2018-06-12
 */
@Service
@Transactional
public class ApplyMngImpl extends BaseManagerImpl<ApplicationBasicInfo> implements ApplyMng {
	@Autowired
	private ApplyCheckMng checkMng;
	
	@Autowired
	private ApplyAttacMng attacMng;
	
	@Autowired
	private PersonalWorkMng personalWorkMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TNodeDataMng tNodeDataMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private PrivateInforMng privateInforMng;
	
	@Autowired
	private HotelStandardMng hotelStandardMng;
	
	@Autowired
	private TravelAppliInfoMng travelAppliInfoMng;
	
	@Autowired
	private ShenTongMng shenTongMng;
	
	@Autowired
	private ApplyMng applyMng;
	/*
	 * 分页查询
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12
	 */
	@Override
	public Pagination pageList(ApplicationBasicInfo bean, int pageNo, int pageSize, Integer type, User user,String searchData) {
		//查询条件
		Finder finder = Finder.create(" FROM ApplicationBasicInfo WHERE stauts in('1','0') AND user='"+user.getId()+"' AND type="+type);
		
		if(!StringUtil.isEmpty(searchData)){
//			finder.append(" AND concat(gCode,gName,DATE_FORMAT(reqTime,'%Y-%m-%d')) like '%"+searchData+"%' ");
			finder.append(" AND (gCode||gName||TO_DATE(reqTime,'yyyy-mm-dd')) like '%"+searchData+"%' ");
		}
		
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getgCode()))) {
			finder.append(" AND gCode LIKE '%"+bean.getgCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getgName()))) {
			finder.append(" AND gName LIKE '%"+bean.getgName()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime1()))) {
			//finder.append(" AND datediff(reqTime,'"+bean.getReqTime()+"')=0 ");
			finder.append(" AND DATE_FORMAT(reqTime,'%Y-%m-%d') >= '"+bean.getReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime2()))) {
			//finder.append(" AND datediff(reqTime,'"+bean.getReqTime()+"')=0 ");
			finder.append(" AND DATE_FORMAT(reqTime,'%Y-%m-%d') <= '"+bean.getReqTime2()+"'");//日期去时分秒函数
		}*/
		/*if (!StringUtil.isEmpty(bean.getFlowStauts())) {
			if(bean.getFlowStauts().equals("暂存")) {
				finder.append(" and flowStauts = '0'");
			}
			if(bean.getFlowStauts().equals("待审批")) {
				finder.append(" and flowStauts = '1'");
			}
			if(bean.getFlowStauts().equals("审批中")) {
				finder.append(" and flowStauts in('2','3','4','5','6','7','8')");
			}
			if(bean.getFlowStauts().equals("已审批")) {
				finder.append(" and flowStauts = '9'");
			}
			if(bean.getFlowStauts().equals("已退回")) {
				finder.append(" and flowStauts = '-1'");
			}
		}*/
		/*if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND concat(gCode,gName,userNames,deptName,DATE(reqTime)) like '%"+searchData+"%' ");
		}*/
		finder.append(" order by reqTime desc ");
		//设置其他属性
		Pagination p = super.find(finder, pageNo, pageSize);
		List<ApplicationBasicInfo> li = (List<ApplicationBasicInfo>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(pageNo-1)*pageSize);
			//设置申请人姓名（id查姓名）,申请人所属部门
			User u = userMng.findById(li.get(x).getUser());
			li.get(x).setUserName(u.getName());
		}
		return p;
	}
	
	/*
	 * 事前申请新增和修改的保存
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12
	 */
	@Override
	public void save(ApplicationBasicInfo bean, MeetingAppliInfo meetingBean, TrainingAppliInfo trainingBean,
			TravelAppliInfo travelBean, ReceptionAppliInfo receptionBean,OfficeCar officeBean,
			AbroadAppliInfo abroadBean, String mingxi, User user,String recePeop,String files)  throws Exception{
		
		/** 保存基本属性 **/
		
		//设置属性
		Date d = new Date();
		bean.setReqTime(d);//申请时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		if (bean.getgId()==null) {
			//获取序列
			int seq = shenTongMng.getSeq("application_basic_info_seq");
			bean.setgId(seq);
			//创建人、创建时间、申请单编号
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setgCode(StringUtil.Random(""));//编码不需要加SQ
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		
		/** 保存基本信息 **/
		//转换type选择流程
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		if("1".equals(String.valueOf(bean.getType()))){
			if("FLFSQ".equals(bean.getCommonType())){
				strType = "TYSXFLSQ";
			}
			if("SPPSSQ".equals(bean.getCommonType())){
				strType = "TYSXSPPSSQ";
			}
		}
		//工作流的节点配置（状态1和2可以继续走审批流，该状态不会变）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){
			Integer flowId =0;
			User nextUser = new User();
			/*if("1".equals(bean.getType())){
				nextUser = userMng.findById(bean.getFuserId());
			}else {*/
				//得到第一个审批节点key
				Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
				flowId= processDefin.getFPId();
				TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser=userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号		
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
			/*}*/
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			//获取t_personal_worktable表序列
			int seq = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getgId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号
			String taskName = "";
			if("1".equals(bean.getType())) {
				taskName = "[通用事项申请]" + bean.getgName();
			} else if("2".equals(bean.getType())) {
				taskName = "[会议申请]" + bean.getgName();
			} else if("3".equals(bean.getType())) {
				taskName = "[培训申请]" + bean.getgName();
			} else if("4".equals(bean.getType())) {
				taskName = "[差旅申请]" + bean.getgName();
			} else if("5".equals(bean.getType())) {
				taskName = "[公务接待申请]" + bean.getgName();
			} else if("6".equals(bean.getType())) {
				taskName = "[公务用车申请]" + bean.getgName();
			} else if("7".equals(bean.getType())) {
				taskName = "[公务出国申请]" + bean.getgName();
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/applyCheck/check?id="+bean.getgId()+"&applyType="+bean.getType());//审批url
			work.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			//同一方法里对象新增，手动添加序列
			int seq2 = shenTongMng.getSeq("personal_worktable_seq");
			work2.setfId(seq2);
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getgId());//申请单ID
			work2.setTaskCode(bean.getgCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/apply/edit?id="+ bean.getgId()+"&editType=1&applyType="+bean.getType());//退回修改url
			work2.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work2.setUrl2("/apply/delete?id="+ bean.getgId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			List mxList = getMingXiJson(mingxi, ApplicationDetail.class);
			//计算明细申请金额总数
			Double num = 0.0;
			for (int i = 0; i < mxList.size(); i++) {
				ApplicationDetail mx = (ApplicationDetail) mxList.get(i);
				if(mx.getApplySum() != null) {
					num += mx.getApplySum();
				}
			}
			budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),num);
		} else {
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
		}
		
		/** 保存附件 **/
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		/** 保存明细信息 **/
		
		//删除数据库中   申请中
		getSession().createSQLQuery("delete from T_APPLI_DETAIL where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			if(!StringUtil.isEmpty(mingxi)){
			//获取Json对象
			List<ApplicationDetail> foodAllowanceList = JSON.parseObject("["+mingxi.toString()+"]",new TypeReference<List<ApplicationDetail>>(){});
			for (ApplicationDetail applicationDetail: foodAllowanceList) {
				ApplicationDetail info = new ApplicationDetail();
				//获取序列
				int cId = shenTongMng.getSeq("appli_detail_seq");
				info.setcId(cId);
				info.setgId(bean.getgId());
				info.setCostDetail(applicationDetail.getCostDetail());
				info.setApplySum(applicationDetail.getApplySum());
				info.setRemark(applicationDetail.getRemark());
				info.setfStatus(0);
				super.merge(info);
			}
		}
	}
	
	/*
	 * 事前申请删除
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12
	 */
	@Override
	public void delete(Integer id,User user,String fId) {
		//事前申请的状态为99（删除）
		//PersonalWork pwork = personalWorkMng.findById(Integer.valueOf(fId));
		if(fId!=null){
			personalWorkMng.deleteById(Integer.valueOf(fId));
		}
		personalWorkMng.sendMessageToUser(user.getId(), 0);
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_STAUTS=99 WHERE F_G_ID="+id).executeUpdate();
		
	}
	
	/*
	 * 明细查询
	 * @author 叶崇晖
	 * @createtime 2018-06-14
	 * @updatetime 2018-06-14
	 */
	public List<Object> getMingxi(String tableName ,String idName ,Integer id) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "="+id);
		return super.find(finder);
	}
	
	/*
	 * 获取明细的Json对象
	 * @author 叶崇晖
	 * @createtime 2018-06-14
	 * @updatetime 2018-06-14
	 */
	public List getMingXiJson(String mingxi, Class tableClass) {
		//获取明细的Json对象
		List mx = null;
		JSONArray array =JSONArray.fromObject("["+mingxi.toString()+"]");
		mx = (List)JSONArray.toList(array, tableClass);
		return mx;
	}
	
	
	/*
	 * 查询单一对象的通用方法
	 * @author 叶崇晖
	 * @createtime 2018-06-14
	 * @updatetime 2018-06-14
	 */
	@Override
	public Object getObject(String tableName, String idName, Integer id) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "="+id);
		List<Object> li = super.find(finder);
		if(li.size()!=0) {
			return li.get(0);
		} else return null;
	}
	
	/*
	 * 查询多个对象的通用方法
	 * @author 叶崇晖
	 * @createtime 2018-06-14
	 * @updatetime 2018-06-14
	 */
	@Override
	public List<Object> getObjectList(String tableName, String idName, Integer id) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "="+id);
		if("ReceptionStrokPlan".equals(tableName)){
			finder.append(" order by fTime desc");
		}
		if("ReceptionChargeInfo".equals(tableName)){
			finder.append(" order by fcTime desc");
		}
		return super.find(finder);
	}
	@Override
	public List<Object> getObjectListTwo(String tableName, String idName, Integer id) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "="+id+ "and rId is null");
		if("ReceptionStrokPlan".equals(tableName)){
			finder.append(" order by fTime desc");
		}
		if("ReceptionChargeInfo".equals(tableName)){
			finder.append(" order by fcTime desc");
		}
		return super.find(finder);
	}
	
	
	/*
	 * 保存申请附件信息
	 * @author 叶崇晖
	 * @createtime 2018-07-05
	 * @updatetime 2018-07-05
	 */
	@Override
	public void saveApplyAttac(ApplicationAttac attac) {
		super.saveOrUpdate(attac);
	}
	
	/*
	 * 审批分页查询
	 * @author 叶崇晖
	 * @createtime 2018-06-29
	 * @updatetime 2018-06-29
	 */
	@Override
	public Pagination checkPageList(ApplicationBasicInfo bean, int pageNo,int pageSize, User user,String searchData) {
		//节点的编号为审批状态（暂时数据不准，为认为创造）		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
		Finder finder = Finder.create(" FROM ApplicationBasicInfo WHERE fuserId='"+user.getId()+"' AND stauts in('1','0')");	
		
		if(!StringUtil.isEmpty(searchData)){
//			finder.append(" AND concat(gCode,gName,DATE_FORMAT(reqTime,'%Y-%m-%d')) like '%"+searchData+"%' ");
			finder.append(" AND (gCode||gName||TO_DATE(reqTime,'yyyy-mm-dd')) like '%"+searchData+"%' ");
		}
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getgCode()))) {
			finder.append(" AND gCode LIKE '%"+bean.getgCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getgName()))) {
			finder.append(" AND gName LIKE '%"+bean.getgName()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime1()))) {
			finder.append(" AND DATE_FORMAT(reqTime,'%Y-%m-%d') >= '"+bean.getReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime2()))) {
			finder.append(" AND DATE_FORMAT(reqTime,'%Y-%m-%d') <= '"+bean.getReqTime2()+"'");//日期去时分秒函数
		}*/
		/*if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND concat(gCode,gName,userNames,deptName,DATE(reqTime)) like '%"+searchData+"%' ");
		}*/
		finder.append(" order by reqTime desc");//
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 台账分页查询
	 * @author 叶崇晖
	 * @createtime 2018-07-06
	 * @updatetime 2018-07-06
	 */
	@Override
	public Pagination ledgerPageList(ApplicationBasicInfo bean, String applyType, int pageNo,int pageSize, User user,String searchData) {
		Finder finder = Finder.create(" FROM ApplicationBasicInfo WHERE flowStauts='9'");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (gCode || gName || deptName) like '%"+searchData+"%' ");
		}
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getgCode()))) {
			finder.append(" AND gCode LIKE '%"+bean.getgCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getgName()))) {
			finder.append(" AND gName LIKE '%"+bean.getgName()+"%'");
		}*/
		String deptIdStr=departMng.getDeptIdStrByUser(user);
 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
 			finder.append(" and user = :user").setParam("user", user.getId());
 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
 			
 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
 			finder.append(" and dept in ("+deptIdStr+")");
 		}
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getDeptName()))) {
			finder.append(" AND deptName LIKE '%"+bean.getDeptName()+"%'");
		}*/
		finder.append(" order by reqTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	
	/**
	 * 查询需要导出的全部台账信息
	 * @author 叶崇晖
	 * @return
	 */
	@Override
	public List<ApplicationBasicInfo> ledgerList(ApplicationBasicInfo bean,User user) {
		Finder finder = Finder.create(" FROM ApplicationBasicInfo WHERE flowStauts='9' AND stauts='1'");
		String deptIdStr=departMng.getDeptIdStrByUser(user);
 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
 			finder.append(" and user = :user").setParam("user", user.getId());
 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
 			
 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
 			finder.append(" and dept in ("+deptIdStr+")");
 		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getgCode()))) {
			finder.append(" AND gCode LIKE '%"+bean.getgCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getgName()))) {
			finder.append(" AND gName LIKE '%"+bean.getgName()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getDeptName()))) {
			finder.append(" AND deptName LIKE '%"+bean.getDeptName()+"%'");
		}
		return super.find(finder);
	}

	/**
	 * 生成需要导出的台账HSSFWorkbook
	 * @author 叶崇晖
	 * @param ledgerData事前申请基本信息集合List
	 * @param filePath模板位置
	 * @return
	 */
	@Override
	public HSSFWorkbook exportLedger(List<ApplicationBasicInfo> ledgerData, String filePath) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			
			sheet0.getRow(1).createCell(1).setCellValue(df.format(new Date()));//设置导出时间
			
			HSSFRow row = sheet0.getRow(3);//格式行
			
			if(ledgerData.size()>0&&ledgerData!=null){
				for (int i = 0; i < ledgerData.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(3+i);
					if(row != null) {
						hssfRow.setHeight(row.getHeight());
					}
					
					//序号
					hssfRow.createCell(0).setCellValue(i + 1);
					//申请单编号
					hssfRow.createCell(1).setCellValue(ledgerData.get(i).getgCode());
					//申请摘要名称
					hssfRow.createCell(2).setCellValue(ledgerData.get(i).getgName());
					//申请事项
					String type="";
					switch (ledgerData.get(i).getType()) {
					case "1":type = "通用事项申请";break;
					case "2":type = "会议申请";break;
					case "3":type = "培训申请";break;
					case "4":type = "差旅申请";break;
					case "5":type = "公务接待申请";break;
					case "6":type = "公务用车申请";break;
					case "7":type = "公务出国申请";break;
					}
					hssfRow.createCell(3).setCellValue(type);
					//申请金额
					hssfRow.createCell(4).setCellValue(ledgerData.get(i).getAmount()!=null?ledgerData.get(i).getAmount():0.0);
					//预算指标
					hssfRow.createCell(5).setCellValue(ledgerData.get(i).getIndexName());
					//申请部门
					hssfRow.createCell(6).setCellValue(ledgerData.get(i).getDeptName());
					//申请人
					User user = userMng.findById(ledgerData.get(i).getUser());
					hssfRow.createCell(7).setCellValue(user!=null?user.getName():"");
					//申请时间
					hssfRow.createCell(8).setCellValue(df.format(ledgerData.get(i).getReqTime()));
					//申请事由
					if("3".equals(ledgerData.get(i).getType())){
						TrainingAppliInfo trainingBean =( TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "gId", ledgerData.get(i).getgId());
						hssfRow.createCell(9).setCellValue(trainingBean.getTrContent());
					}else if("5".equals(ledgerData.get(i).getType())){
						ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "gId", ledgerData.get(i).getgId());
						hssfRow.createCell(9).setCellValue(receptionBean.getReceptionContent());
					}else{
						hssfRow.createCell(9).setCellValue(ledgerData.get(i).getReason());
					}
				}
				return wb;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
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
	public List<ApplicationBasicInfo> reimburseList(String applyType, User user,String reqTime) {
		Finder finder = Finder.create(" FROM ApplicationBasicInfo WHERE flowStauts='9'");
		finder.append(" AND type = '"+applyType+"'");
		finder.append(" AND user = '"+user.getId()+"'");
		if(!StringUtil.isEmpty(reqTime)){
			finder.append(" AND DATE_FORMAT(reqTime,'%Y-%m-%d') ='"+reqTime+"'");
		}
		finder.append(" AND (reimbType NOT IN('1','9')  or F_REIMB_TYPE  is null)");
		finder.append(" order by updateTime desc ");
		List<ApplicationBasicInfo> list=super.find(finder);
		if (list != null && list.size() > 0) {
			int i = 0;
			for (ApplicationBasicInfo a: list) {
				i++;
				a.setNum(i);
			}
			return list;
		}		
		return null;
	}

	
	@Override
	public ApplicationBasicInfo findByCode(String code) {
		Finder finder = Finder.create(" FROM ApplicationBasicInfo WHERE gCode='"+code+"'");
		List<ApplicationBasicInfo> list = super.find(finder);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Object[]> getOutComeSubject(String basicType,
			String parentCode, String year, String qName,String indexType,String pIdS,String deptId) {
		String sql = " SELECT B.F_PRO_CODE,A.F_B_INDEX_NAME , A.F_B_INDEX_CODE, B.F_PRO_ID "
		+ " FROM T_BUDGET_INDEX_MGR A"
		+ " INNER JOIN T_PRO_BASIC_INFO B "
		+ " ON A.F_B_INDEX_CODE = B.F_PRO_CODE "
		+ " WHERE A.F_RELEASE_STAUTS='1' "
		+ " AND A.F_YEARS=" + DateUtil.getCurrentYear(); //只能获得当年指标
		
		
		if("14".equals(deptId) || "15".equals(deptId) || "16".equals(deptId) || "17".equals(deptId) || "8".equals(deptId) || "9".equals(deptId)){
			String deptIds ="'01'";
			if( "8".equals(deptId) || "9".equals(deptId)){
				deptIds +=",'02'";
			}
			sql += " AND (A.F_DEPT_CODE ='"+deptId+"' OR A.F_DEPT_CODE IS NULL OR A.F_DEPT_CODE in("+deptIds+"))";
		}else{
			sql += " AND (A.F_DEPT_CODE ='"+deptId+"' OR A.F_DEPT_CODE IS NULL)";
		}
		
		if("0".equals(indexType)){
			sql += " AND A.F_INDEX_TYPE=0";//基本支出
		}else if("1".equals(indexType)){
			sql += " AND A.F_INDEX_TYPE=1";//项目支出
		}
		sql += " AND B.F_PRO_ID in("+pIdS+")  GROUP BY B.F_PRO_CODE, A.F_B_INDEX_NAME, A.F_B_INDEX_CODE, B.F_PRO_ID ";//项目ID
		SQLQuery query = getSession().createSQLQuery(sql);
		

		return query.list();
}

	@Override
	public Map<String, Integer> getPidMap(String basicType, String parentCodes,
			String year, String qName) {
		
		/*Map<String,Integer> pidMap=new HashMap<String,Integer>();
		List<Integer> pidList=getPidListByparentCodes(basicType,parentCodes,year,qName);
		if(pidList!=null && pidList.size()>0){
			for(Integer pid:pidList){
				pidMap.put(pid+"", pid);
			}
		}
		return pidMap;*/
		
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
		
		String sql = " SELECT F_PRO_ID FROM T_PRO_EXPEND_DETAIL WHERE 1=1 ";
		
		if (!StringUtil.isEmpty(parentCodes)) {//父节点
			sql = sql + " and F_PRO_ID in (" + parentCodes+" )";
		}
		
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}
	
	
	@Override
	public List<Integer> getPidListByparentCode(String parentCodes){
		
		String sql = " SELECT F_PRO_ID FROM T_PRO_EXPEND_DETAIL WHERE 1=1 ";
		
		if (!StringUtil.isEmpty(parentCodes)) {//父节点
			sql = sql + " and F_SUB_NUM in (" + parentCodes+" )";
		}else{
			sql = sql + " and F_SUB_NUM not in ('30215','30216','30211','30217','30231','30212')";
		}
		
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public List<Object[]> getOutDetail(String projectId,String type) {
		
		String sql = "SELECT a.F_EXP_ID, "	//明细-id
				+ " a.F_SUB_NAME, "			//明细-名称
				+ " a.F_SUB_NUM, "			//明细-编码
				+ " pro.f_pro_head, "		//项目-负责人
				+ " a.F_SY_AMOUNT, "		//明细-剩余金额
				+ " ind.f_b_id, "			//指标-id
				+ " a.F_APPLI_AMOUNT, "		//明细-批复金额						
				+ " ind.F_YEARS, "			//指标-预算年度
				+ " ind.F_DEPT_NAME,"		//指标-使用部门
				+ " a.F_DJ_AMOUNT,"			//指标-冻结金额	9
				+ " ind.F_B_INDEX_NAME,"	//项目名称
				+ " ind.F_B_INDEX_CODE"		//项目名称CODE
				+ " FROM T_PRO_EXPEND_DETAIL a "
				+ " inner join t_pro_basic_info pro on a.F_PRO_ID = pro.F_PRO_ID "
				+ " inner join t_budget_index_mgr ind on ind.F_B_INDEX_CODE = pro.f_pro_code"
				+ " WHERE 1=1"
				+ " AND a.F_PRO_ID= " + projectId;
			
		if(!StringUtil.isEmpty(type)){
			sql+=" and a.F_SUB_NUM = "+type+"";
		}else{
			sql = sql + " and a.F_SUB_NUM not in ('30215','30216','30211','30217','30231','30212')";
		}
		sql = sql + " GROUP BY a.F_EXP_ID, "	//明细-id
				+ " a.F_SUB_NAME, "			//明细-名称
				+ " a.F_SUB_NUM, "			//明细-编码
				+ " pro.f_pro_head, "		//项目-负责人
				+ " a.F_SY_AMOUNT, "		//明细-剩余金额
				+ " a.F_APPLI_AMOUNT, "		//明细-批复金额						
				+ " ind.F_YEARS, "			//指标-预算年度
				+ " ind.F_DEPT_NAME,"		//指标-使用部门
				+ " a.F_DJ_AMOUNT,"			//指标-冻结金额	9
				+ " ind.F_B_INDEX_NAME,"	//项目名称
				+ " ind.F_B_INDEX_CODE"		//项目名称CODE
				+"";
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();	
	}

	@Override
	public String ApplyReCall(Integer id) throws Exception {
		//根据id查询对象
		ApplicationBasicInfo bean=(ApplicationBasicInfo)super.findById(id);
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="申请被撤回消息";
		String msg="您待审批的  "+bean.getgName() +",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//归还冻结金额
		budgetIndexMgrMng.deleteDjAmount(bean.getIndexId(),bean.getProDetailId(),bean.getAmount());
		//撤回
		bean=(ApplicationBasicInfo) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "操作成功";
	}

	@Override
	public Integer getMaxCodeToday5() {
		//返回当天最大值编码 
		Integer maxNum = 0;
		Finder finder = Finder.create(" select gCode from ApplicationBasicInfo where gCode like '%" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "%'" 
		+ " AND LENGTH(gCode)=10 ");
		List<String> list = super.find(finder);
		if (list != null && list.size() > 0) {
			for (String str: list) {
				if (str.length() > 6) {
					Integer num = Integer.valueOf(str.substring(str.length() - 6));
					if (num > maxNum) {
						maxNum = num;
					}
				}
			}
		}
		return maxNum;
	}

	@Override
	public String getDraft(Integer applyType, String userName) {
		String typeName = "";
		if (1==applyType) {
			typeName = "通用事项";
		} else if (2==applyType) {
			typeName = "会议";
		} else if (3==applyType) {
			typeName = "培训";
		} else if (4==applyType) {
			typeName = "差旅";
		} else if (5==applyType) {
			typeName = "公务接待";
		} else if (6==applyType) {
			typeName = "公车运维";
		} else if (7==applyType) {
			typeName = "公务出国";
		}
		return userName + " - " + typeName + "申请";
	}
	
	/*
	 * 差旅申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-13
	 * @updatetime 2020-02-13
	 */
	@Override
	public void saveTravel(ApplicationBasicInfo bean,
			TravelAppliInfo travelBean, User user,String travelPeop,String files,
			String outsideTraffic,						//城市间交通费Json
			String inCity,						//市内交通费Json
			String hotelJson,						//住宿费Json
			String foodJson
			)  throws Exception{
		
		/** 保存基本属性 **/
		
		//设置属性
		Date d = new Date();
		bean.setReqTime(d);//申请时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
//		TBudgetIndexMgr indexmgr = budgetIndexMgrMng.findById(bean.getIndexId());
//		bean.setIndexType(indexmgr.getIndexType());//指标类型
		if (bean.getgId()==null) {
			bean.setgId(shenTongMng.getSeq("application_basic_info_seq"));
			//创建人、创建时间、申请单编号
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setgCode(StringUtil.Random(""));//编码不需要加SQ
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		
		/** 保存基本信息 **/
		//转换type选择流程
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		if("GWCX".equals(bean.getTravelType())){
			strType = "GWCXSQ";
		}
		//工作流的节点配置（状态1和2可以继续走审批流，该状态不会变）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){
			Integer flowId =0;
			User nextUser = new User();
			if("1".equals(bean.getType())){
				nextUser = userMng.findById(bean.getFuserId());
			}else {
				//得到第一个审批节点key
				Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
				flowId= processDefin.getFPId();
				TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser=userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号		
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
			}
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getgId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号
			String taskName = "";
			if("1".equals(bean.getType())) {
				taskName = "[通用事项申请]" + bean.getgName();
			} else if("2".equals(bean.getType())) {
				taskName = "[会议申请]" + bean.getgName();
			} else if("3".equals(bean.getType())) {
				taskName = "[培训申请]" + bean.getgName();
			} else if("4".equals(bean.getType())) {
				taskName = "[差旅申请]" + bean.getgName();
			} else if("5".equals(bean.getType())) {
				taskName = "[公务接待申请]" + bean.getgName();
			} else if("6".equals(bean.getType())) {
				taskName = "[公务用车申请]" + bean.getgName();
			} else if("7".equals(bean.getType())) {
				taskName = "[公务出国申请]" + bean.getgName();
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/applyCheck/check?id="+bean.getgId()+"&applyType="+bean.getType());//审批url
			work.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getgId());//申请单ID
			work2.setTaskCode(bean.getgCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/apply/edit?id="+ bean.getgId()+"&editType=1&applyType="+bean.getType());//退回修改url
			work2.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work2.setUrl2("/apply/delete?id="+ bean.getgId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			Double num = bean.getAmount();
			budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),num);
		} else {
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
		}
		
		/** 保存附件 **/
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		
		/** 保存申请扩展信息 **/
		
		//保存差旅信息
			//删除数据库中   申请中的出差行程单
			getSession().createSQLQuery("delete from T_TRAVEL_APPLI_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			TravelAppliInfo infos = null;
			if(!StringUtil.isEmpty(travelPeop)){
				//获取出差行程单的Json对象
				List<TravelAppliInfo> rp = JSON.parseObject("["+travelPeop.toString()+"]",new TypeReference<List<TravelAppliInfo>>(){});
				for (TravelAppliInfo travelAppliInfo : rp) {
					infos = new TravelAppliInfo();
					infos.setTrId(shenTongMng.getSeq("T_TRAVEL_APPLI_INFO_SEQ"));
					infos.setgId(bean.getgId());
					infos.setTravelDateStart(travelAppliInfo.getTravelDateStart());
					infos.setTravelDateEnd(travelAppliInfo.getTravelDateEnd());
					if("GWCC".equals(bean.getTravelType())){
						infos.setTravelArea(String.valueOf(travelAppliInfo.getTravelAreaId()));
					}
					infos.setTravelAreaName(travelAppliInfo.getTravelAreaName());
					infos.setTravelAttendPeopId(travelAppliInfo.getTravelAttendPeopId());
					infos.setTravelAttendPeop(travelAppliInfo.getTravelAttendPeop());
					infos.setTravelAreaTime(travelAppliInfo.getTravelAreaTime());
					infos.setAreaNames(travelAppliInfo.getAreaNames());
					infos.setTravelPersonnelLevel(travelAppliInfo.getTravelPersonnelLevel());
					infos.setAreaCode(travelAppliInfo.getAreaCode());
					infos.setfDriveWay(travelAppliInfo.getfDriveWay());
					infos.setfDriveWayCode(travelAppliInfo.getfDriveWayCode());
					infos.setReason(travelAppliInfo.getReason());
					infos.setTravelType(bean.getTravelType());
					infos.setfStatus(0);
					/*if (travelBean != null && !travelBean.equals("")) {
						if (travelBean.getMeetingSummaryTime1() != null && !travelBean.getMeetingSummaryTime1().equals("")) {
							String[] MeetingSummaryTime1 = travelBean.getMeetingSummaryTime1().split(",");
							infos.setMeetingSummaryTime1(MeetingSummaryTime1[1]);
						}
						if (travelBean.getMeetingSummaryTime2() != null && !travelBean.getMeetingSummaryTime2().equals("")) {
							String[] MeetingSummaryTime2 = travelBean.getMeetingSummaryTime2().split(",");
							infos.setMeetingSummaryTime2(MeetingSummaryTime2[1]);
						}
						if (travelBean.getMeetingSummaryYear1() != null && !travelBean.getMeetingSummaryYear1().equals("")) {
							String[] MeetingSummaryYear1 = travelBean.getMeetingSummaryYear1().split(",");
							infos.setMeetingSummaryYear1(MeetingSummaryYear1[1]);
						}
						if (travelBean.getMeetingSummaryYear2() != null && !travelBean.getMeetingSummaryYear2().equals("")) {
							String[] MeetingSummaryYear2 = travelBean.getMeetingSummaryYear2().split(",");
							infos.setMeetingSummaryYear2(MeetingSummaryYear2[1]);
						}
					}*/
					super.merge(infos);
				}
			}
			//删除数据库中   申请中的城市间交通费
			getSession().createSQLQuery("delete from T_OUTSIDE_TRADDIC_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS='0'").executeUpdate();
			//获取出差行程单的Json对象
			if(!StringUtil.isEmpty(outsideTraffic)){
				List<OutsideTrafficInfo> outside = JSON.parseObject("["+outsideTraffic.toString()+"]",new TypeReference<List<OutsideTrafficInfo>>(){});
				for (OutsideTrafficInfo outsideTrafficInfo : outside) {
					OutsideTrafficInfo info = new OutsideTrafficInfo();
					info.setfOId(shenTongMng.getSeq("T_OUTSIDE_TRADDIC_INFO_SEQ"));
					info.setgId(bean.getgId());
					info.setfStartDate(outsideTrafficInfo.getfStartDate());
					info.setfEndDate(outsideTrafficInfo.getfEndDate());
					info.setVehicle(outsideTrafficInfo.getVehicle());
					info.setVehicleLevel(outsideTrafficInfo.getVehicleLevel());
					info.setTravelPersonnel(outsideTrafficInfo.getTravelPersonnel());
					info.setTravelPersonnelLevel(outsideTrafficInfo.getTravelPersonnelLevel());
					info.setApplyAmount(outsideTrafficInfo.getApplyAmount());
					info.setTravelType(bean.getTravelType());
					info.setVehicleId(outsideTrafficInfo.getVehicleId());
					info.setSts("0");
					super.merge(info);
				}	
			}

			//删除数据库中   申请中的城内交通费
			getSession().createSQLQuery("delete from T_INCITY_TRAFFIC_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			if(!StringUtil.isEmpty(inCity)){
				//获取城内交通费的Json对象
				List<InCityTrafficInfo> inCityList = JSON.parseObject("["+inCity.toString()+"]",new TypeReference<List<InCityTrafficInfo>>(){});
				for (InCityTrafficInfo inCityTrafficInfo : inCityList) {
					InCityTrafficInfo info = new InCityTrafficInfo();
					info.setFtId(shenTongMng.getSeq("T_INCITY_TRAFFIC_INFO_SEQ"));
					info.setgId(bean.getgId());
					info.setfPerson(inCityTrafficInfo.getfPerson());
					info.setfSubsidyDay(inCityTrafficInfo.getfSubsidyDay());
					info.setfAdjacentDay(inCityTrafficInfo.getfAdjacentDay());
					info.setfDistanceDay(inCityTrafficInfo.getfDistanceDay());
					info.setfApplyAmount(inCityTrafficInfo.getfApplyAmount());
					info.setTravelType(bean.getTravelType());
					info.setfStatus(0);
					super.merge(info);
				}
			}
			//删除数据库中   申请中的住宿费
			getSession().createSQLQuery("delete from T_HOTEL_EXPENSE_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS='0'").executeUpdate();
			if(!StringUtil.isEmpty(hotelJson)){
				//获取城内交通费的Json对象
				List<HotelExpenseInfo> hotelExpenseList = JSON.parseObject("["+hotelJson.toString()+"]",new TypeReference<List<HotelExpenseInfo>>(){});
				for (HotelExpenseInfo hotelExpenseInfo : hotelExpenseList) {
					HotelExpenseInfo info = new HotelExpenseInfo();
					info.setfHId(shenTongMng.getSeq("T_HOTEL_EXPENSE_INFO_SEQ"));
					info.setgId(bean.getgId());
					info.setCheckInTime(hotelExpenseInfo.getCheckInTime());
					info.setCheckOUTTime(hotelExpenseInfo.getCheckOUTTime());
					info.setCityId(hotelExpenseInfo.getCityId());
					info.setLocationCity(hotelExpenseInfo.getLocationCity());
					info.setTravelPersonnel(hotelExpenseInfo.getTravelPersonnel());
					info.setTravelPersonnelId(hotelExpenseInfo.getTravelPersonnelId());
					info.setApplyAmount(hotelExpenseInfo.getApplyAmount());
					info.setTravelType(bean.getTravelType());
					info.setSts("0");
					super.merge(info);
				}
			}
			//删除数据库中   申请中的伙食补助费
			getSession().createSQLQuery("delete from T_FOOD_ALLOWANCE_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
				if(!StringUtil.isEmpty(foodJson)){
				//获取城内交通费的Json对象
				List<FoodAllowanceInfo> foodAllowanceList = JSON.parseObject("["+foodJson.toString()+"]",new TypeReference<List<FoodAllowanceInfo>>(){});
				for (FoodAllowanceInfo foodAllowanceInfo : foodAllowanceList) {
					FoodAllowanceInfo info = new FoodAllowanceInfo();
					info.setFfId(shenTongMng.getSeq("T_FOOD_ALLOWANCE_INFO_SEQ"));
					info.setgId(bean.getgId());
					info.setFname(foodAllowanceInfo.getFname());
					info.setFnameId(foodAllowanceInfo.getFnameId());
					info.setfDays(foodAllowanceInfo.getfDays());
					info.setfApplyAmount(foodAllowanceInfo.getfApplyAmount());
					info.setTravelType(bean.getTravelType());
					info.setfStatus(0);
					super.merge(info);
				}
			}
	}	
		
			
	/*
	 * 公务接待申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-16
	 * @updatetime 2020-02-16
	 */
	@Override
	public void saveRecp(User user, ApplicationBasicInfo bean,
			ReceptionAppliInfo receptionBean, String hotelJson, String foodJson,
			String otherJson, String recePeop, String storkJson, String chargeJson, String gwjdghfiles,String gwjdfafiles)  throws Exception{
		
		/** 保存基本属性 **/
		
		//设置属性
		Date d = new Date();
		bean.setReqTime(d);//申请时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		if (bean.getgId()==null) {
			bean.setgId(shenTongMng.getSeq("application_basic_info_seq"));
			//创建人、创建时间、申请单编号
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setgCode(StringUtil.Random(""));//编码不需要加SQ
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		
		/** 保存基本信息 **/
		//转换type选择流程
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		if("GWCX".equals(bean.getTravelType())){
			strType = "GWCXSQ";
		}
		//工作流的节点配置（状态1和2可以继续走审批流，该状态不会变）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){
			Integer flowId =0;
			User nextUser = new User();
			if("1".equals(bean.getType())){
				nextUser = userMng.findById(bean.getFuserId());
			}else {
				//得到第一个审批节点key
				Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
				flowId= processDefin.getFPId();
				TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser=userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号		
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
			}
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getgId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号
			String taskName = "";
			if("1".equals(bean.getType())) {
				taskName = "[通用事项申请]" + bean.getgName();
			} else if("2".equals(bean.getType())) {
				taskName = "[会议申请]" + bean.getgName();
			} else if("3".equals(bean.getType())) {
				taskName = "[培训申请]" + bean.getgName();
			} else if("4".equals(bean.getType())) {
				taskName = "[差旅申请]" + bean.getgName();
			} else if("5".equals(bean.getType())) {
				taskName = "[公务接待申请]" + bean.getgName();
			} else if("6".equals(bean.getType())) {
				taskName = "[公务用车申请]" + bean.getgName();
			} else if("7".equals(bean.getType())) {
				taskName = "[公务出国申请]" + bean.getgName();
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/applyCheck/check?id="+bean.getgId()+"&applyType="+bean.getType());//审批url
			work.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getgId());//申请单ID
			work2.setTaskCode(bean.getgCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/apply/edit?id="+ bean.getgId()+"&editType=1&applyType="+bean.getType());//退回修改url
			work2.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work2.setUrl2("/apply/delete?id="+ bean.getgId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			Double num = bean.getAmount();
			budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),num);
		} else {
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
		}
		
		/** 保存附件 **/
		
		//保存附件信息
		attachmentMng.joinEntity(bean,gwjdghfiles);
		attachmentMng.joinEntity(bean,gwjdfafiles);
		
		
		/** 保存申请扩展信息 **/
		
		//保存公务接待信息
		if(receptionBean != null) {
			if (receptionBean.getjId()==null) {
				receptionBean.setjId(shenTongMng.getSeq("T_RECEPTION_APPLI_INFO_SEQ"));
				//创建人、创建时间、发布时间
				receptionBean.setCreator(user.getAccountNo());
				receptionBean.setCreateTime(d);
			} else {
				//修改人、修改时间
				receptionBean.setUpdator(user.getAccountNo());
				receptionBean.setUpdateTime(d);
			}
			receptionBean.setgId(bean.getgId());
			receptionBean = (ReceptionAppliInfo) super.saveOrUpdate(receptionBean);
			
			if("1".equals(receptionBean.getStayYN())){//判断是否住宿
				//获取住宿费的Json对象
				//获得新的住宿费信息
				JSONArray array = JSONArray.fromObject("["+hotelJson.toString()+"]");
				List rp = (List)JSONArray.toList(array, ReceptionHotel.class);
				
				//获得老的住宿费信息
				List<Object> oldrp = getObjectList("ReceptionHotel", "gId", bean.getgId());
				/*for (int i = oldrp.size()-1; i >= 0; i--) {
					ReceptionHotel oldrpi = (ReceptionHotel) oldrp.get(i);
					for (int j = 0; j < rp.size(); j++) {		
						ReceptionHotel rpi = (ReceptionHotel) rp.get(j);
						if(rpi.gethID()!=null){
							if(rpi.gethID()==oldrpi.gethID()){
								oldrp.remove(i);
							}
						}
					}
				}*/
				//删除在新明细中没有的老明细
				if(oldrp.size()>0){
					for (int i = 0; i < oldrp.size(); i++) {
						ReceptionHotel oldrpi = (ReceptionHotel) oldrp.get(i);
						super.delete(oldrpi);
					}
				}
				
				for (int i = 0; i < rp.size(); i++) {		
					ReceptionHotel rpi = (ReceptionHotel) rp.get(i);
					rpi.sethID(shenTongMng.getSeq("T_RECEPTION_HOTEL_SEQ"));
					rpi.setgId(bean.getgId());
					rpi.setCreator(user.getAccountNo());
					rpi.setCreateTime(d);
					//保存被接待人信息
					super.merge(rpi);
				}
			}
			
			//获取餐费的Json对象
			//获得新的餐费信息
			JSONArray array1 = JSONArray.fromObject("["+foodJson.toString()+"]");
			List rp1 = (List)JSONArray.toList(array1, ReceptionFood.class);
			
			//获得老的餐费信息
			List<Object> oldrp1 = getObjectList("ReceptionFood", "gId", bean.getgId());
				/*for (int i = oldrp1.size()-1; i >= 0; i--) {
					ReceptionFood oldrpi = (ReceptionFood) oldrp1.get(i);
					for (int j = 0; j < rp1.size(); j++) {		
						ReceptionFood rpi = (ReceptionFood) rp1.get(j);
						if(rpi.getfID()!=null){
							if(rpi.getfID()==oldrpi.getfID()){
								oldrp1.remove(i);
							}
						}
					}
				}*/
			//删除在新明细中没有的老明细
			if(oldrp1.size()>0){
				for (int i = 0; i < oldrp1.size(); i++) {
					ReceptionFood oldrpi = (ReceptionFood) oldrp1.get(i);
					super.delete(oldrpi);
				}
			}
			
			for (int i = 0; i < rp1.size(); i++) {		
				ReceptionFood rpi = (ReceptionFood) rp1.get(i);
				rpi.setfID(shenTongMng.getSeq("T_RECEPTION_FOOD_SEQ"));
				rpi.setgId(bean.getgId());
				rpi.setCreator(user.getName());
				rpi.setCreateTime(d);
				//保存被接待人信息
				super.merge(rpi);
			}
			
			//获取其他费用的Json对象
			//获得新的其他费用信息
			JSONArray array11 = JSONArray.fromObject("["+otherJson.toString()+"]");
			List rp11 = (List)JSONArray.toList(array11, ReceptionOther.class);
			
			//获得老的住宿费信息
			List<Object> oldrp11 = getObjectList("ReceptionOther", "gId", bean.getgId());
				/*for (int i = oldrp11.size()-1; i >= 0; i--) {
					ReceptionOther oldrpi = (ReceptionOther) oldrp11.get(i);
					for (int j = 0; j < rp11.size(); j++) {		
						ReceptionOther rpi = (ReceptionOther) rp11.get(j);
						if(rpi.getoID()!=null){
							if(rpi.getoID()==oldrpi.getoID()){
								oldrp11.remove(i);
							}
						}
					}
				}*/
			//删除在新明细中没有的老明细
			if(oldrp11.size()>0){
				for (int i = 0; i < oldrp11.size(); i++) {
					ReceptionOther oldrpi = (ReceptionOther) oldrp11.get(i);
					super.delete(oldrpi);
				}
			}
			
			for (int i = 0; i < rp11.size(); i++) {		
				ReceptionOther rpi = (ReceptionOther) rp11.get(i);
				rpi.setoID(shenTongMng.getSeq("T_RECEPTION_OTEHR_SEQ"));
				rpi.setgId(bean.getgId());
				rpi.setCreator(user.getName());
				rpi.setCreateTime(d);
				//保存信息
				super.merge(rpi);
			}
			
			//获取接待用户的Json对象
			//获得新的接待用户信息
			JSONArray arrayPeople = JSONArray.fromObject("["+recePeop.toString()+"]");
			List newpeopleList = (List)JSONArray.toList(arrayPeople, ReceptionPeopleInfo.class);
			
			//获得老的住宿费信息
			List<Object> oldpeopleList = getObjectList("ReceptionPeopleInfo", "gId", bean.getgId());
			/*for (int i = oldpeopleList.size()-1; i >= 0; i--) {
				ReceptionPeopleInfo oldrpi = (ReceptionPeopleInfo) oldpeopleList.get(i);
				for (int j = 0; j < newpeopleList.size(); j++) {		
					ReceptionPeopleInfo rpi = (ReceptionPeopleInfo) newpeopleList.get(j);
					if(rpi.getjId()!=null){
						if(rpi.getjId()==oldrpi.getjId()){
							oldpeopleList.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldpeopleList.size()>0){
				for (int i = 0; i < oldpeopleList.size(); i++) {
					ReceptionPeopleInfo oldrpi = (ReceptionPeopleInfo) oldpeopleList.get(i);
					super.delete(oldrpi);
				}
			}
			
			for (int i = 0; i < newpeopleList.size(); i++) {		
				ReceptionPeopleInfo rpi = (ReceptionPeopleInfo) newpeopleList.get(i);
				rpi.setjId(shenTongMng.getSeq("T_RECEPTION_APPLI_PEOPLE_INFO_SEQ"));
				rpi.setgId(bean.getgId());
				rpi.setCreator(user.getName());
				rpi.setCreateTime(d);
				//保存信息
				super.merge(rpi);
			}
			
			
			//获取接待主要行程安排Json对象
			//获得新的主要行程安排信息
			List<ReceptionStrokPlan> newStorkList = JSON.parseObject("["+storkJson.toString()+"]",new TypeReference<List<ReceptionStrokPlan>>(){});
			/*JSONArray arrayStork = JSONArray.fromObject("["+storkJson.toString()+"]");
			List newStorkList = (List)JSONArray.toList(arrayStork, ReceptionStrokPlan.class);*/
			
			//获得老的住宿费信息
			List<Object> oldStorkList = getObjectList("ReceptionStrokPlan", "gId", bean.getgId());
			/*for (int i = oldStorkList.size()-1; i >= 0; i--) {
				ReceptionStrokPlan oldrpi = (ReceptionStrokPlan) oldStorkList.get(i);
				for (int j = 0; j < newStorkList.size(); j++) {		
					ReceptionStrokPlan rpi = (ReceptionStrokPlan) newStorkList.get(j);
					if(rpi.getfSPId()!=null){
						if(rpi.getfSPId()==oldrpi.getfSPId()){
							oldStorkList.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldStorkList.size()>0){
				for (int i = 0; i < oldStorkList.size(); i++) {
					ReceptionStrokPlan oldrpi = (ReceptionStrokPlan) oldStorkList.get(i);
					super.delete(oldrpi);
				}
			}
			
			for (int i = 0; i < newStorkList.size(); i++) {		
				ReceptionStrokPlan rpi = (ReceptionStrokPlan) newStorkList.get(i);
				rpi.setfSPId(shenTongMng.getSeq("T_RECEPTION_STROK_PLAN_SEQ"));
				rpi.setgId(bean.getgId());
				rpi.setCreator(user.getName());
				rpi.setCreateTime(d);
				//保存信息
				super.merge(rpi);
			}
			
			//获取接待收费Json对象
			//获得新收费明细信息
			JSONArray arrayCharge = JSONArray.fromObject("["+chargeJson.toString()+"]");
			List<ReceptionChargeInfo> newChargeList = JSONObject.parseArray(arrayCharge.toString(), ReceptionChargeInfo.class);
			//List newChargeList = (List)JSONArray.toList(arrayCharge, ReceptionChargeInfo.class);
			
			//获得老的收费信息
			List<Object> oldChargeList = getObjectList("ReceptionChargeInfo", "gId", bean.getgId());
			/*for (int i = oldChargeList.size()-1; i >= 0; i--) {
				ReceptionChargeInfo oldrpi = (ReceptionChargeInfo) oldChargeList.get(i);
				for (int j = 0; j < newChargeList.size(); j++) {		
					ReceptionChargeInfo rpi = (ReceptionChargeInfo) newChargeList.get(j);
					if(rpi.getcId()!=null){
						if(rpi.getcId()==oldrpi.getcId()){
							oldChargeList.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldChargeList.size()>0){
				for (int i = 0; i < oldChargeList.size(); i++) {
					ReceptionChargeInfo oldrpi = (ReceptionChargeInfo) oldChargeList.get(i);
					super.delete(oldrpi);
				}
			}
			
			for (int i = 0; i < newChargeList.size(); i++) {		
				ReceptionChargeInfo rpi = (ReceptionChargeInfo) newChargeList.get(i);
				rpi.setcId(shenTongMng.getSeq("T_CHARGE_INFO_SEQ"));
				rpi.setgId(bean.getgId());
				rpi.setCreator(user.getName());
				rpi.setCreateTime(d);
				//保存信息
				super.merge(rpi);
			}
		}
	}	
	
	/*
	 * 公车运维申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-14
	 * @updatetime 2020-02-14
	 */
	@Override
	public void saveOfficeCar(ApplicationBasicInfo bean, User user,String officeCar,String files)  throws Exception{
		
		/** 保存基本属性 **/
		
		//设置属性
		Date d = new Date();
		bean.setReqTime(d);//申请时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		if (bean.getgId()==null) {
			//创建人、创建时间、申请单编号
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setgCode(StringUtil.Random(""));//编码不需要加SQ
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		
		/** 保存基本信息 **/
		//转换type选择流程
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		//工作流的节点配置（状态1和2可以继续走审批流，该状态不会变）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){
			Integer flowId =0;
			User nextUser = new User();
			if("1".equals(bean.getType())){
				nextUser = userMng.findById(bean.getFuserId());
			}else {
				//得到第一个审批节点key
				Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
				flowId= processDefin.getFPId();
				TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser=userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号		
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
			}
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getgId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号
			String taskName = "";
			if("1".equals(bean.getType())) {
				taskName = "[通用事项申请]" + bean.getgName();
			} else if("2".equals(bean.getType())) {
				taskName = "[会议申请]" + bean.getgName();
			} else if("3".equals(bean.getType())) {
				taskName = "[培训申请]" + bean.getgName();
			} else if("4".equals(bean.getType())) {
				taskName = "[差旅申请]" + bean.getgName();
			} else if("5".equals(bean.getType())) {
				taskName = "[公务接待申请]" + bean.getgName();
			} else if("6".equals(bean.getType())) {
				taskName = "[公车运维申请]" + bean.getgName();
			} else if("7".equals(bean.getType())) {
				taskName = "[公务出国申请]" + bean.getgName();
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/applyCheck/check?id="+bean.getgId()+"&applyType="+bean.getType());//审批url
			work.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getgId());//申请单ID
			work2.setTaskCode(bean.getgCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/apply/edit?id="+ bean.getgId()+"&editType=1&applyType="+bean.getType());//退回修改url
			work2.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work2.setUrl2("/apply/delete?id="+ bean.getgId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			Double num = bean.getAmount();
			budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),num);
		} else {
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
		}
		
		/** 保存附件 **/
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		/** 保存申请扩展信息 **/
		
		//保存公车运维信息
			
			//删除数据库中   申请中的公车运维费用明细
			getSession().createSQLQuery("delete from T_OFFICE_CAR where F_G_ID ="+bean.getgId()+" and F_STATUS='0'").executeUpdate();
			if(!StringUtil.isEmpty(officeCar)){
				//获取公车运维费用明细的Json对象
				List<OfficeCar> officeCarList = JSON.parseObject(officeCar,new TypeReference<List<OfficeCar>>(){});
				for (OfficeCar officeCarInfo : officeCarList) {
					OfficeCar info = new OfficeCar();
					info.setgId(bean.getgId());
					info.setfExpenseName(officeCarInfo.getfExpenseName());
					info.setfUseType(officeCarInfo.getfUseType());
					info.setfCarNum(officeCarInfo.getfCarNum());
					info.setfCarType(officeCarInfo.getfCarType());
					info.setfUseAmount(officeCarInfo.getfUseAmount());
					info.setfRemark(officeCarInfo.getfRemark());
					info.setfStatus(0);
					super.merge(info);
				}
			}
		}
	
	/*
	 * 培训申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-17
	 * @updatetime 2020-02-17
	 */
	@Override
	public void saveTrain(User user, ApplicationBasicInfo bean,
			TrainingAppliInfo trainingBean, String trainPlan, String files, String trainLecturer, String zongheJson, String lessonJson, String hotelJson, String foodJson, String trafficJson1, String trafficJson2)  throws Exception{
		
		/** 保存基本属性 **/
		
		//设置属性
		Date d = new Date();
		bean.setReqTime(d);//申请时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		if (bean.getgId()==null) {
			bean.setgId(shenTongMng.getSeq("application_basic_info_seq"));
			//创建人、创建时间、申请单编号
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setgCode(StringUtil.Random(""));//编码不需要加SQ
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		
		/** 保存基本信息 **/
		//转换type选择流程
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		//工作流的节点配置（状态1和2可以继续走审批流，该状态不会变）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){
			Integer flowId =0;
			User nextUser = new User();
			if("1".equals(bean.getType())){
				nextUser = userMng.findById(bean.getFuserId());
			}else {
				//得到第一个审批节点key
				Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
				flowId= processDefin.getFPId();
				TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser=userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号		
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
			}
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getgId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号
			String taskName = "";
			taskName = "[培训申请]" + bean.getgName();
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/applyCheck/check?id="+bean.getgId()+"&applyType="+bean.getType());//审批url
			work.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getgId());//申请单ID
			work2.setTaskCode(bean.getgCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/apply/edit?id="+ bean.getgId()+"&editType=1&applyType="+bean.getType());//退回修改url
			work2.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work2.setUrl2("/apply/delete?id="+ bean.getgId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			Double num = bean.getAmount();
			budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),num);
		} else {
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
		}
		
		/** 保存附件 **/
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		/** 保存申请扩展信息 **/
		
		//保存培训信息
		if(trainingBean != null) {
			if (trainingBean.gettId()==null) {
				trainingBean.settId(shenTongMng.getSeq("T_TRAINING_APPLI_INFO_SEQ"));
				//创建人、创建时间、发布时间
				trainingBean.setCreator(user.getAccountNo());
				trainingBean.setCreateTime(d);
			} else {
				//修改人、修改时间
				trainingBean.setUpdator(user.getAccountNo());
				trainingBean.setUpdateTime(d);
			}
			trainingBean.setgId(bean.getgId());
			trainingBean = (TrainingAppliInfo) super.saveOrUpdate(trainingBean);
			
			//保存讲师信息
			//获得老的信息
			List<Object> oldlec = getObjectList("LecturerInfo", "tId", trainingBean.gettId());
			//获取现有的明细
			JSONArray array =JSONArray.fromObject("["+trainLecturer.toString()+"]");
			List newlec = (List)JSONArray.toList(array, LecturerInfo.class);
			/*//比较新老明细的不同
			for (int i = oldlec.size()-1; i >= 0; i--) {
				LecturerInfo oldgad = (LecturerInfo) oldlec.get(i);
				for (int j = 0; j < newlec.size(); j++) {
					LecturerInfo gad = (LecturerInfo) newlec.get(j);
					if(gad.getlId()!=null){
						if(gad.getlId()==oldgad.getlId()){
							oldlec.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldlec.size()>0){
				for (int i = 0; i < oldlec.size(); i++) {
					LecturerInfo oldgad = (LecturerInfo) oldlec.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newlec.size(); j++) {
				LecturerInfo gad = (LecturerInfo) newlec.get(j);
				gad.setlId(shenTongMng.getSeq("T_LECTURER_INFO_SEQ"));
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				super.merge(gad);
			}
			//保存培训日程
			//获得新的培训日程信息

			List<MeetPlan> oc = JSON.parseObject("["+trainPlan.toString()+"]",new TypeReference<List<MeetPlan>>(){});
			//获得老的信息
			List<Object> oldrp = getObjectList("MeetPlan", "tId", trainingBean.gettId());
			/*List<Object> oldrp1 = new ArrayList();
			for (int i = 0; i < oldrp.size(); i++) {
				MeetPlan oldrpi = (MeetPlan) oldrp.get(i);
				for (int j = 0; j < oc.size(); j++) {		
					MeetPlan rpi = (MeetPlan) oc.get(j);
					if(rpi.gettId()!=null){
						if(rpi.gettId()!=oldrpi.gettId()){
							oldrp1.add(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldrp.size()>0){
				for (int i = 0; i < oldrp.size(); i++) {
					MeetPlan oldrpi = (MeetPlan) oldrp.get(i);
					super.delete(oldrpi);
				}
			}

			for (int i = 0; i < oc.size(); i++) {		
				MeetPlan rpi = (MeetPlan) oc.get(i);
				rpi.setPlanId(shenTongMng.getSeq("t_meet_plan_seq"));
				rpi.settId(trainingBean.gettId());
				rpi.setCreator(user.getAccountNo());
				rpi.setCreateTime(d);
				//保存培训日程信息
				super.merge(rpi);
			}
			//保存综合预算明细
			//获得老的信息
			List<Object> oldmx = getMingxi("ApplicationDetail", "gId", bean.getgId());
			//获取现有的明细
			List mx = getMingXiJson(zongheJson, ApplicationDetail.class);

			/*//比较新老明细的不同
			for (int i = oldmx.size()-1; i >= 0; i--) {
				ApplicationDetail oldgad = (ApplicationDetail) oldmx.get(i);
				for (int j = 0; j < mx.size(); j++) {
					ApplicationDetail gad = (ApplicationDetail) mx.get(j);
					if(gad.getcId()!=null){
						if(gad.getcId()==oldgad.getcId()){
							oldmx.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldmx.size()>0){
				for (int i = 0; i < oldmx.size(); i++) {
					ApplicationDetail oldgad = (ApplicationDetail) oldmx.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < mx.size(); j++) {
				ApplicationDetail gad = (ApplicationDetail) mx.get(j);
				gad.setcId(shenTongMng.getSeq("appli_detail_seq"));
				gad.setgId(bean.getgId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				super.merge(gad);
			}
			//保存讲课费
			//获得老的信息
			List<TrainTeacherCost> oldlesson = getTeacherMingxi(trainingBean.gettId(),"lesson");
			//获取现有的明细
			JSONArray array1 =JSONArray.fromObject("["+lessonJson.toString()+"]");
			List newlesson = (List)JSONArray.toList(array1, TrainTeacherCost.class);
			/*//比较新老明细的不同
			for (int i = oldlesson.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldlesson.get(i);
				for (int j = 0; j < newlesson.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldlesson.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldlesson.size()>0){
				for (int i = 0; i < oldlesson.size(); i++) {
					TrainTeacherCost oldgad = oldlesson.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newlesson.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
				gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("lesson");
				super.merge(gad);
			}
			//保存住宿费
			//获得老的信息
			List<TrainTeacherCost> oldhotel = getTeacherMingxi(trainingBean.gettId(),"hotel");
			//获取现有的明细
			JSONArray array2 =JSONArray.fromObject("["+hotelJson.toString()+"]");
			List newhotel = (List)JSONArray.toList(array2, TrainTeacherCost.class);
			/*//比较新老明细的不同
			for (int i = oldhotel.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldhotel.get(i);
				for (int j = 0; j < newhotel.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldhotel.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldhotel.size()>0){
				for (int i = 0; i < oldhotel.size(); i++) {
					TrainTeacherCost oldgad = oldhotel.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newhotel.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
				gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("hotel");
				super.merge(gad);
			}
			//保存伙食费
			//获得老的信息
			List<TrainTeacherCost> oldfood = getTeacherMingxi(trainingBean.gettId(),"food");
			//获取现有的明细
			JSONArray array3 =JSONArray.fromObject("["+foodJson.toString()+"]");
			List newfood = (List)JSONArray.toList(array3, TrainTeacherCost.class);
			/*//比较新老明细的不同
			for (int i = oldfood.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldfood.get(i);
				for (int j = 0; j < newfood.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldfood.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldfood.size()>0){
				for (int i = 0; i < oldfood.size(); i++) {
					TrainTeacherCost oldgad = oldfood.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newfood.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
				gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("food");
				super.merge(gad);
			}
			//保存城市间交通费
			//获得老的信息
			List<TrainTeacherCost> oldtraffic1 = getTeacherMingxi(trainingBean.gettId(),"traffic1");
			//获取现有的明细
			JSONArray array4 =JSONArray.fromObject("["+trafficJson1.toString()+"]");
			List newtraffic1 = (List)JSONArray.toList(array4, TrainTeacherCost.class);
			/*//比较新老明细的不同
			for (int i = oldtraffic1.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldtraffic1.get(i);
				for (int j = 0; j < newtraffic1.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldtraffic1.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldtraffic1.size()>0){
				for (int i = 0; i < oldtraffic1.size(); i++) {
					TrainTeacherCost oldgad = oldtraffic1.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newtraffic1.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
				gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("traffic1");
				super.merge(gad);
			}
			//保存市内交通费
			//获得老的信息
			List<TrainTeacherCost> oldtraffic2 = getTeacherMingxi(trainingBean.gettId(),"traffic2");
			//获取现有的明细
			JSONArray array5 =JSONArray.fromObject("["+trafficJson2.toString()+"]");
			List newtraffic2 = (List)JSONArray.toList(array5, TrainTeacherCost.class);
			/*//比较新老明细的不同
			for (int i = oldtraffic2.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldtraffic2.get(i);
				for (int j = 0; j < newtraffic2.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldtraffic2.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldtraffic2.size()>0){
				for (int i = 0; i < oldtraffic2.size(); i++) {
					TrainTeacherCost oldgad = oldtraffic2.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newtraffic2.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
				gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("traffic2");
				super.merge(gad);
			}
		}
	}
	
	/*
	 * 公务出国申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-18
	 * @updatetime 2020-02-18
	 */
	@Override
	public void saveAbroad(User user, ApplicationBasicInfo bean,
			AbroadAppliInfo abroadBean,
			String travelJson,	                    //国际旅费json
			String trafficJson1,					//城市间交通费和国外城市间交通费
			String hotelJson,	                    //住宿费json
			String foodJson,						//伙食费json
			String feeJson,							//公杂费json
			String feteJson,						//宴请费json
			String otherJson,						//其他收入json
			String abroadPlanJson,						//出访计划
			String files)  throws Exception{
		
		/** 保存基本属性 **/
		
		//设置属性
		Date d = new Date();
		bean.setReqTime(d);//申请时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		if (bean.getgId()==null) {
			//创建人、创建时间、申请单编号
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setgCode(StringUtil.Random(""));//编码不需要加SQ
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		
		/** 保存基本信息 **/
		//转换type选择流程
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		//工作流的节点配置（状态1和2可以继续走审批流，该状态不会变）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){
			Integer flowId =0;
			User nextUser = new User();
			if("1".equals(bean.getType())){
				nextUser = userMng.findById(bean.getFuserId());
			}else {
				//得到第一个审批节点key
				Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
				flowId= processDefin.getFPId();
				TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser=userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号		
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
			}
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getgId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号
			String taskName = "";
			if("1".equals(bean.getType())) {
				taskName = "[通用事项申请]" + bean.getgName();
			} else if("2".equals(bean.getType())) {
				taskName = "[会议申请]" + bean.getgName();
			} else if("3".equals(bean.getType())) {
				taskName = "[培训申请]" + bean.getgName();
			} else if("4".equals(bean.getType())) {
				taskName = "[差旅申请]" + bean.getgName();
			} else if("5".equals(bean.getType())) {
				taskName = "[公务接待申请]" + bean.getgName();
			} else if("6".equals(bean.getType())) {
				taskName = "[公车运维申请]" + bean.getgName();
			} else if("7".equals(bean.getType())) {
				taskName = "[公务出国申请]" + bean.getgName();
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/applyCheck/check?id="+bean.getgId()+"&applyType="+bean.getType());//审批url
			work.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getgId());//申请单ID
			work2.setTaskCode(bean.getgCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/apply/edit?id="+ bean.getgId()+"&editType=1&applyType="+bean.getType());//退回修改url
			work2.setUrl1("/apply/edit?id="+ bean.getgId()+"&editType=0&applyType="+bean.getType());//查看url
			work2.setUrl2("/apply/delete?id="+ bean.getgId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			/**----------------------------------------------------------------**/
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			/**----------------------------------------------------------------**/
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			Double num = bean.getAmount();
			budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),num);
		} else {
			//保存基本信息
			bean = (ApplicationBasicInfo) super.saveOrUpdate(bean);
		}
		
		/** 保存附件 **/
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		/** 保存申请扩展信息 **/
		
		//保存公务出国信息
		if(abroadBean != null) {
			if (abroadBean.getFaId()==null) {
				//创建人、创建时间、发布时间
				abroadBean.setCreator(user.getAccountNo());
				abroadBean.setCreateTime(d);
			} else {
				//修改人、修改时间
				abroadBean.setUpdator(user.getAccountNo());
				abroadBean.setUpdateTime(d);
			}
			abroadBean.setgId(bean.getgId());
			abroadBean = (AbroadAppliInfo) super.merge(abroadBean);
		
			
			//删除数据库中   申请中的出访计划信息
			getSession().createSQLQuery("delete from t_abroad_plan where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			//获得新的出访计划信息
			if(!StringUtil.isEmpty(abroadPlanJson)){
				//获取出访计划信息的Json对象
				List<AbroadPlan> rp = JSON.parseObject("["+abroadPlanJson.toString()+"]",new TypeReference<List<AbroadPlan>>(){});
				for (AbroadPlan abroadPlan : rp) {
					AbroadPlan info = new AbroadPlan();
					info.setgId(bean.getgId());
					info.setAbroadDate(abroadPlan.getAbroadDate());
					info.setTimeEnd(abroadPlan.getTimeEnd());
					info.setArriveCountryId(abroadPlan.getArriveCountryId());
					info.setArriveCountry(abroadPlan.getArriveCountry());
					info.setArriveCity(abroadPlan.getArriveCity());
					info.setRemark(abroadPlan.getRemark());
					info.setStatus(0);
					super.merge(info);
				}
			}
			
			
			//删除数据库中   申请中的国际旅费
			getSession().createSQLQuery("delete from T_INTERNATIONAL_TRAVELING_EXPENSE where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			//获得新的国际旅费
			if(!StringUtil.isEmpty(travelJson)){
				//获取国际旅费的Json对象
				List<InternationalTravelingExpense> rp = JSON.parseObject("["+travelJson.toString()+"]",new TypeReference<List<InternationalTravelingExpense>>(){});
				for (InternationalTravelingExpense internationalTravelingExpense : rp) {
					InternationalTravelingExpense info = new InternationalTravelingExpense();
					info.setgId(bean.getgId());
					info.setTimeStart(internationalTravelingExpense.getTimeStart());
					info.setTimeEnd(internationalTravelingExpense.getTimeEnd());
					info.setStartCity(internationalTravelingExpense.getStartCity());
					info.setArriveCity(internationalTravelingExpense.getArriveCity());
					info.setVehicle(internationalTravelingExpense.getVehicle());
					info.setApplyAmount(internationalTravelingExpense.getApplyAmount());
					info.setTrainSubsidies(internationalTravelingExpense.getTrainSubsidies());
					info.setfStatus(0);
					super.merge(info);
				}
			}
			
			//删除数据库中   申请中的城市间交通费
			getSession().createSQLQuery("delete from T_OUTSIDE_TRADDIC_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS='0'").executeUpdate();
			//获取出城市间交通费的Json对象
			if(!StringUtil.isEmpty(trafficJson1)){
				List<OutsideTrafficInfo> outside = JSON.parseObject("["+trafficJson1.toString()+"]",new TypeReference<List<OutsideTrafficInfo>>(){});
				for (OutsideTrafficInfo outsideTrafficInfo : outside) {
					OutsideTrafficInfo info = new OutsideTrafficInfo();
					info.setgId(bean.getgId());
					info.setTravelPersonnel(outsideTrafficInfo.getTravelPersonnel());
					info.setApplyAmount(outsideTrafficInfo.getApplyAmount());
					info.setSts("0");
					super.merge(info);
				}	
			}
			
			//删除数据库中   申请中的住宿费
			getSession().createSQLQuery("delete from T_HOTEL_EXPENSE_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS='0'").executeUpdate();
			if(!StringUtil.isEmpty(hotelJson)){
				//获取住宿费的Json对象
				List<HotelExpenseInfo> rp = JSON.parseObject("["+hotelJson.toString()+"]",new TypeReference<List<HotelExpenseInfo>>(){});
				for (HotelExpenseInfo hotelExpenseInfo : rp) {
					HotelExpenseInfo info = new HotelExpenseInfo();
					info.setgId(bean.getgId());
					info.setLocationCity(hotelExpenseInfo.getLocationCity());
					info.setCheckInTime(hotelExpenseInfo.getCheckInTime());
					info.setCheckOUTTime(hotelExpenseInfo.getCheckOUTTime());
					info.setCityId(hotelExpenseInfo.getCityId());
					info.setStandard(hotelExpenseInfo.getStandard());
					info.setHotelDay(hotelExpenseInfo.getHotelDay());
					info.setCountStandard(hotelExpenseInfo.getCountStandard());
					info.setCurrency(hotelExpenseInfo.getCurrency());
					info.setApplyAmount(hotelExpenseInfo.getApplyAmount());
					info.setSts("0");
					super.merge(info);
				}
			}
			
			//删除数据库中   申请中的伙食费
			getSession().createSQLQuery("delete from T_FOOD_ALLOWANCE_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			if(!StringUtil.isEmpty(foodJson)){
				//获取伙食费的Json对象
				List<FoodAllowanceInfo> rp = JSON.parseObject("["+foodJson.toString()+"]",new TypeReference<List<FoodAllowanceInfo>>(){});
				for (FoodAllowanceInfo foodAllowanceInfo : rp) {
					FoodAllowanceInfo info = new FoodAllowanceInfo();
					info.setgId(bean.getgId());
					info.setfAddress(foodAllowanceInfo.getfAddress());
					info.setStandard(foodAllowanceInfo.getStandard());
					info.setfDays(foodAllowanceInfo.getfDays());
					info.setCountStandard(foodAllowanceInfo.getCountStandard());
					info.setCurrency(foodAllowanceInfo.getCurrency());
					info.setfApplyAmount(foodAllowanceInfo.getfApplyAmount());
					info.setfStatus(0);
					super.merge(info);
				}
			}
			
			//删除数据库中   申请中的公杂费
			getSession().createSQLQuery("delete from T_MISCELLANEOUS_FEE where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			if(!StringUtil.isEmpty(feeJson)){
				//获取公杂费的Json对象
				List<MiscellaneousFeeInfo> rp = JSON.parseObject("["+feeJson.toString()+"]",new TypeReference<List<MiscellaneousFeeInfo>>(){});
				for (MiscellaneousFeeInfo miscellaneousFeeInfo : rp) {
					MiscellaneousFeeInfo info = new MiscellaneousFeeInfo();
					info.setgId(bean.getgId());
					info.setfAddress(miscellaneousFeeInfo.getfAddress());
					info.setStandard(miscellaneousFeeInfo.getStandard());
					info.setfDays(miscellaneousFeeInfo.getfDays());
					info.setCountStandard(miscellaneousFeeInfo.getCountStandard());
					info.setCurrency(miscellaneousFeeInfo.getCurrency());
					info.setfApplyAmount(miscellaneousFeeInfo.getfApplyAmount());
					info.setfStatus(0);
					super.merge(info);
				}
			}
			
			//删除数据库中   申请中的宴请费
			getSession().createSQLQuery("delete from T_FETE_COST_INFO where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			if(!StringUtil.isEmpty(feteJson)){
				//获取宴请费的Json对象
				List<FeteCostInfo> rp = JSON.parseObject("["+feteJson.toString()+"]",new TypeReference<List<FeteCostInfo>>(){});
				for (FeteCostInfo feteCostInfo : rp) {
					FeteCostInfo info = new FeteCostInfo();
					info.setgId(bean.getgId());
					info.setfAddress(feteCostInfo.getfAddress());
					info.setfAddressId(feteCostInfo.getfAddressId());
					info.setStandard(feteCostInfo.getStandard());
					info.setfFeeNum(feteCostInfo.getfFeeNum());
					info.setfAccompanyNum(feteCostInfo.getfAccompanyNum());
					info.setCountStandard(feteCostInfo.getCountStandard());
					info.setCurrency(feteCostInfo.getCurrency());
					info.setfApplyAmount(feteCostInfo.getfApplyAmount());
					info.setfStatus(0);
					super.merge(info);
				}
			}
			
			//删除数据库中   申请中的其他费
			getSession().createSQLQuery("delete from T_RECEPTION_OTEHR where F_G_ID ="+bean.getgId()+" and F_STATUS=0").executeUpdate();
			if(!StringUtil.isEmpty(otherJson)){
				//获取其他费的Json对象
				List<ReceptionOther> rp = JSON.parseObject("["+otherJson.toString()+"]",new TypeReference<List<ReceptionOther>>(){});
				for (ReceptionOther receptionOther : rp) {
					ReceptionOther info = new ReceptionOther();
					info.setgId(bean.getgId());
					info.setfCostName(receptionOther.getfCostName());
					info.setfCost(receptionOther.getfCost());
					info.setfRemark(receptionOther.getfRemark());
					info.setfStatus(0);
					super.merge(info);
				}
			}
			
			
			
		}
	}
	
	/*
	 * 分页查询
	 * @author 沈帆
	 * @createtime 2020-01-16
	 * @updatetime 2020-01-16
	 */
	@Override
	public Pagination reimbPageList(ApplicationBasicInfo bean, int pageNo, int pageSize, Integer type, User user,String rFlowStauts,String searchData) {
		//查询条件
		/*Finder finder = Finder.create(" FROM ApplicationBasicInfo WHERE stauts in('1','0') AND user='"+user.getId()+"' AND type="+type);
		finder.append(" AND flowStauts='9' ");
		if (!StringUtil.isEmpty(String.valueOf(bean.getgCode()))) {
			finder.append(" AND gCode LIKE '%"+bean.getgCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getgName()))) {
			finder.append(" AND gName LIKE '%"+bean.getgName()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime1()))) {
			finder.append(" AND datediff(reqTime,'"+bean.getReqTime()+"')=0 ");
			finder.append(" AND DATE_FORMAT(reqTime,'%Y-%m-%d') >= '"+bean.getReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime2()))) {
			finder.append(" AND datediff(reqTime,'"+bean.getReqTime()+"')=0 ");
			finder.append(" AND DATE_FORMAT(reqTime,'%Y-%m-%d') <= '"+bean.getReqTime2()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(bean.getFlowStauts())) {
			if(bean.getFlowStauts().equals("暂存")) {
				finder.append(" and flowStauts = '0'");
			}
			if(bean.getFlowStauts().equals("待审批")) {
				finder.append(" and flowStauts = '1'");
			}
			if(bean.getFlowStauts().equals("审批中")) {
				finder.append(" and flowStauts in('2','3','4','5','6','7','8')");
			}
			if(bean.getFlowStauts().equals("已审批")) {
				finder.append(" and flowStauts = '9'");
			}
			if(bean.getFlowStauts().equals("已退回")) {
				finder.append(" and flowStauts = '-1'");
			}
		}
		finder.append(" order by updateTime desc ");
		//设置其他属性
		Pagination p = super.find(finder, pageNo, pageSize);
		List<ApplicationBasicInfo> li = (List<ApplicationBasicInfo>) p.getList();
		for(int x=0; x<li.size(); x++) {
			ReimbAppliBasicInfo reimb =reimbAppliMng.findByCode(li.get(x).getgCode());
			if(reimb!=null){
				li.get(x).setrId(reimb.getrId());
				li.get(x).setReimbAmount(reimb.getAmount());
				li.get(x).setReimbTime(reimb.getReimburseReqTime());
				li.get(x).setReimbFlowStauts(reimb.getFlowStauts());
				li.get(x).setReimbStauts(reimb.getStauts());
			}
			//序号设置	
			li.get(x).setNum((x+1)+(pageNo-1)*pageSize);
			//设置申请人姓名（id查姓名）,申请人所属部门
			User u = userMng.findById(li.get(x).getUser());
			li.get(x).setUserName(u.getName());
		}*/
		StringBuilder sb = new StringBuilder("select a.F_G_ID as gId,a.F_G_CODE as gCode,a.F_G_NAME as gName,a.F_AMOUNT as amount,a.F_REQ_TIME as reqTime,a.F_APP_TYPE as type,b.F_R_ID as rId,b.F_AMOUNT as reimbAmount,b.F_REQ_TIME as reimbTime,b.F_FLOW_STAUTS as reimbFlowStauts,b.F_STAUTS as reimbStauts from T_APPLICATION_BASIC_INFO a left join t_reimb_appli_basic_info b on a.F_G_CODE = b.F_G_CODE");
		sb.append(" where a.F_STAUTS in('1','0')  AND a.F_USER='"+user.getId()+"' AND a.F_APP_TYPE="+type+" ");
		sb.append(" and a.F_FLOW_STAUTS='9' ");
		sb.append(" and (b.F_STAUTS!=99 or b.F_STAUTS is null)");
		if(!StringUtil.isEmpty(searchData)){
//			sb.append(" AND concat(a.F_G_CODE,a.F_G_NAME,a.F_AMOUNT,b.F_AMOUNT,DATE_FORMAT(a.F_REQ_TIME,'%Y-%m-%d'),DATE_FORMAT(b.F_REQ_TIME,'%Y-%m-%d')) like '%"+searchData+"%' ");
			sb.append(" AND (a.F_G_CODE||a.F_G_NAME||a.F_AMOUNT||b.F_AMOUNT||TO_DATE(a.F_REQ_TIME,'yyyy-mm-dd')||TO_DATE(b.F_REQ_TIME,'yyyy-mm-dd')) like '%"+searchData+"%' ");
		}
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getgCode()))) {
			sb.append(" AND a.F_G_CODE LIKE '%"+bean.getgCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime1()))) {
			sb.append(" AND DATE_FORMAT(b.F_REQ_TIME,'%Y-%m-%d') >= '"+bean.getReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReqTime2()))) {
			sb.append(" AND DATE_FORMAT(b.F_REQ_TIME,'%Y-%m-%d') <= '"+bean.getReqTime2()+"'");//日期去时分秒函数
		}*/
		
		/*if(!StringUtil.isEmpty(searchData)){
			sb.append(" AND concat(a.F_G_CODE,a.F_G_NAME,DATE_FORMAT(a.F_REQ_TIME,'%Y-%m-%d')) like '%"+searchData+"%' ");
		}*/
		if (!StringUtil.isEmpty(rFlowStauts)) {
			if("99".equals(rFlowStauts)){
				sb.append(" AND b.F_FLOW_STAUTS is null");
			}else{
				sb.append(" AND b.F_FLOW_STAUTS = '"+rFlowStauts+"'");
			}
		}
		sb.append(" ORDER BY b.F_FLOW_STAUTS NULLS FIRST,a.F_REQ_TIME DESC");
		String str=sb.toString();
		Pagination p1 =super.findObjectListBySql(str, pageNo, pageSize);
		List<Object[]> dataList = (List<Object[]>) p1.getList();
		List<ApplicationBasicInfo>list=new ArrayList<>();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (Object[] obj: dataList) {
				ApplicationBasicInfo t=new ApplicationBasicInfo();
				t.setgId(Integer.valueOf(String.valueOf(obj[0])));
				t.setgCode(String.valueOf(obj[1]));
				t.setgName(String.valueOf(obj[2]));
				t.setAmount(Double.valueOf(String.valueOf(obj[3])));
				t.setReqTime((Date) obj[4]);
				t.setType(String.valueOf(obj[5]));
				if(obj[6]!=null){
					t.setrId(Integer.valueOf(String.valueOf(obj[6])));		
				}
				t.setReimbAmount(Double.valueOf(String.valueOf(obj[7]==null?0:obj[7])));
				t.setReimbTime((Date) obj[8]);
				t.setReimbFlowStauts((String) obj[9]);
				t.setReimbStauts((String) obj[10]);
				t.setNum(pageNo * pageSize + i - (pageSize-1));
				i++;
				list.add(t);
			}
			p1.setList(list);
		}
		return p1;
	}
	
	
	@Override
	public List<Lookups> getLookupsJson(String categoryCode,String blanked,String selected) {
		Finder hql=Finder.create("FROM Lookups WHERE flag='1' ");
		hql.append("  AND category.code =:pcode ").setParam("pcode", categoryCode);
		if(!StringUtil.isEmpty(blanked)){
			hql.append(" AND code<>:code").setParam("code", blanked);
		}
		hql.append(" ORDER BY convert(orderNo,DECIMAL)");
		return super.find(hql);
	}
	
	@Override
	public List<TrainTeacherCost> getTeacherMingxi(Integer id, String costType){
		Finder hql=Finder.create("FROM TrainTeacherCost WHERE tId="+id+" and costType= '"+costType+"' ");
		return super.find(hql);
	}
	
	/**
	 * 支出申请中根据前台传过来的类型返回不同的科目编码
	 * @param type 前天传过来的类型 1-null，2-30215 ，3-30216 ，4-30211 ，5-30217 ，6-30231 ，7-30212		
	 * @return
	 * @author 赵孟雷
	 * @createTime 2021年1月21日
	 * @updateTime 2021年1月21日
	 */
	@Override
	public String acquireEncoding(String type) {
		if("1".equals(type)){//通用是申请
			return null;
		}else if("2".equals(type)){//会议申请
			return "30215";
		}else if("3".equals(type)){//培训申请
			return "30216";
		}else if("4".equals(type)){//差旅申请
			return "30211";
		}else if("5".equals(type)){//公务接待申请	
			return "30217";
		}else if("6".equals(type)){//公务用车申请	
			return "30231";
		}else if("7".equals(type)){//公务出国申请	
			return "30212";
		}
		return null;
	}
}
