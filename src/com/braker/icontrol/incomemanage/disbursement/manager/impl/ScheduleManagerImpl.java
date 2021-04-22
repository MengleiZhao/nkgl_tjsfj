package com.braker.icontrol.incomemanage.disbursement.manager.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.ComboboxBean;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
import com.braker.icontrol.incomemanage.disbursement.manager.ScheduleManager;
import com.braker.icontrol.incomemanage.disbursement.model.Schedule;
import com.braker.icontrol.incomemanage.disbursement.model.ScheduleProList;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class ScheduleManagerImpl extends BaseManagerImpl<Schedule> implements ScheduleManager {
	
	@Autowired
	private DepartMng departMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	
	@Autowired
	private ShenTongMng shenTongMng;
	
	
	/**
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@Override
	public Pagination pageList(Schedule bean, int pageNo, int pageSize,User user) {
		//查询条件
		Finder finder = Finder.create(" FROM Schedule WHERE stauts !='99'");
		String deptIdStr=departMng.getDeptIdStrByUser(user);
 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
 			finder.append(" and user = :user").setParam("user", user.getId());
 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
 			
 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
 			finder.append(" and deptId in ("+deptIdStr+")");
 		}
 		if(!StringUtil.isEmpty(bean.getFlowStauts())){
 			finder.append(" and flowStauts ='9'");
 		}
 		if(!StringUtil.isEmpty(bean.getSearchTitle())){
 			finder.append(" AND (deptName||applyYear||amount) like '%"+bean.getSearchTitle()+"%' ");
 		}
		finder.append(" order by reqTime desc ");
		//设置其他属性
		Pagination p = super.find(finder, pageNo, pageSize);
		List<Schedule> li = (List<Schedule>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置
			li.get(x).setNum((x+1)+(pageNo-1)*pageSize);
		}
		return p;
	}

	/**
	 * 用款计划新增
	 * @param bean
	 * @param mingxi
	 * @param model
	 * @param files
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@Override
	public void save(Schedule bean, String mingxi, User user, String files) throws Exception {
		//设置属性
		Date d = new Date();
		bean.setReqTime(d);//申请时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName()); //申请人姓名
		bean.setDeptId(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepart().getName());//申请人所属部门名称
		if (bean.getsId()==null) {
			//创建人、创建时间、申请单编号
			bean.setsId(shenTongMng.getSeq("schedule_seq"));
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setScheduleCode(StringUtil.Random("YKJH"));
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		/** 保存基本信息 **/
		//工作流的节点配置（状态1和2可以继续走审批流，该状态不会变）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), "YKJH", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("YKJH", user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (Schedule) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getsId());//申请单ID
			work.setTaskCode(bean.getScheduleCode());//为申请单的单号
			work.setTaskName("[用款计划]" + bean.getDeptName()+"用款计划");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/schedule/check?id="+bean.getsId());//审批url
			work.setUrl1("/schedule/edit?id="+ bean.getsId()+"&editType=0");//查看url
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
			work2.setTaskId(bean.getsId());//申请单ID
			work2.setTaskCode(bean.getScheduleCode());//为申请单的单号
			work2.setTaskName("[用款计划]" + bean.getDeptName()+"用款计划");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/schedule/edit?id="+ bean.getsId()+"&editType=1");//退回修改url
			work2.setUrl1("/schedule/edit?id="+ bean.getsId()+"&editType=0");//查看url
			work2.setUrl2("/schedule/delete?id="+ bean.getsId());//删除url
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
		} else {
			//保存基本信息
			bean = (Schedule) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		/** 保存明细信息 **/
		//删除数据库中   申请中
		getSession().createSQLQuery("delete from T_SCHEDULE_PRO_LIST where T_SCHEDULE_ID ="+bean.getsId()).executeUpdate();
			if(!StringUtil.isEmpty(mingxi)){
			//获取Json对象
			List<ScheduleProList> scheduleProLists = JSON.parseObject("["+mingxi.toString()+"]",new TypeReference<List<ScheduleProList>>(){});
			for (ScheduleProList scheduleProList: scheduleProLists) {
				ScheduleProList info = new ScheduleProList();
				info.setSplId(shenTongMng.getSeq("schedule_pro_list_seq"));
				info.setsId(bean.getsId());
				info.setfProId(scheduleProList.getfProId());
				info.setfProName(scheduleProList.getfProName());
				info.setfProCode(scheduleProList.getfProCode());
				info.setPfAmount(scheduleProList.getPfAmount());
				info.setFirstAmount(scheduleProList.getFirstAmount());
				info.setTwoAmount(scheduleProList.getTwoAmount());
				info.setThreeAmount(scheduleProList.getThreeAmount());
				info.setFourAmount(scheduleProList.getFourAmount());
				info.setDeptId(scheduleProList.getDeptId());
				info.setDeptName(scheduleProList.getDeptName());
				super.merge(info);
			}
		}
	}

	/**
	 * @param id
	 * @param fId
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@Override
	public void delete(Integer id, User user, String fId) {
		//事前申请的状态为99（删除）
		if(fId!=null){
			personalWorkMng.deleteById(Integer.valueOf(fId));
		}
		personalWorkMng.sendMessageToUser(user.getId(), 0);
		getSession().createSQLQuery("UPDATE T_SCHEDULE SET F_STAUTS=99 WHERE T_SCHEDULE_ID="+id).executeUpdate();
	}
	
	/**
	 * 
	 * @Description: 用款计划申请撤回
	 * @param @param id
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author 赵孟雷
	 * @date 2021年2月24日
	 */
	public String  ApplyReCall(Integer id)  throws Exception{
		//根据id查询对象
		Schedule bean = super.findById(id);
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="申请被撤回消息";
		String msg="您待审批的"+bean.getDeptName()+"用款计划申请,任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean=(Schedule) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "操作成功";
	}
	
	/** 以下位置用于用款计划审批使用  */
	
	/**
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	public Pagination pageCheckList(Schedule bean, int pageNo, int pageSize,User user,String searchTitle){
		//查询条件
		Finder finder = Finder.create(" FROM Schedule WHERE stauts !='99'");
		if(!StringUtil.isEmpty(bean.getSearchTitle())){
 			finder.append(" AND (deptName||applyYear||amount) like '%"+bean.getSearchTitle()+"%' ");
 		}
		finder.append(" and fuserId ='"+user.getId()+"'");
		finder.append(" order by reqTime desc ");
		//设置其他属性
		Pagination p = super.find(finder, pageNo, pageSize);
		List<Schedule> li = (List<Schedule>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置
			li.get(x).setNum((x+1)+(pageNo-1)*pageSize);
		}
		return p;
	}
	
	/**
	 * 保存审批信息
	 * @param checkBean
	 * @param bean
	 * @param mingxi
	 * @param user
	 * @param role
	 * @param files
	 * @throws Exception
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	public void saveCheckInfo(TProcessCheck checkBean, Schedule bean,User user,String files) throws Exception{
		bean=this.findById(bean.getsId());
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/schedule/check?id=";
		String lookUrl ="/schedule/edit?editType=0&id=";
		bean=(Schedule)tProcessCheckMng.checkProcess(checkBean,entity,user,"YKJH",checkUrl,lookUrl,files);
		super.saveOrUpdate(bean);
	}
	
	
	/**
	 * 获取台账页面数据
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@Override
	public Pagination pageLedgerList(Schedule bean, int pageNo, int pageSize,User user) {
		//查询条件
		Finder finder = Finder.create(" FROM Schedule WHERE stauts !='99' and flowStauts ='9'");

		
 		if(user.getRoleName().contains("会计岗") || ("34".equals(user.getDepart().getId()))&&user.getRoleName().contains("处室负责人")){
 			
 		}else{
 			String deptIdStr=departMng.getDeptIdStrByUser(user);
 	 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
 	 			finder.append(" and deptId = :deptId").setParam("deptId", user.getDepart().getId());
 	 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
 	 			
 	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
 	 			finder.append(" and deptId in ("+deptIdStr+")");
 	 		}
 		}

 		if(!StringUtil.isEmpty(bean.getApplyYear())){
 			finder.append(" and applyYear ='"+bean.getApplyYear()+"'");
 		}
 		if(!StringUtil.isEmpty(bean.getDeptName())){
 			finder.append(" and deptName ='"+bean.getDeptName()+"'");
 		}
		finder.append(" order by reqTime desc ");
		//设置其他属性
		Pagination p = super.find(finder, pageNo, pageSize);
		List<Schedule> li = (List<Schedule>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置
			li.get(x).setNum((x+1)+(pageNo-1)*pageSize);
		}
		return p;
	}

	@Override
	public Pagination scheduleStatistics(Schedule bean, Integer page, Integer rows, User user) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT t1.F_APPLY_YEAR, t2.* FROM T_SCHEDULE t1 INNER JOIN T_SCHEDULE_PRO_LIST t2 ON t1.T_SCHEDULE_ID = t2.T_SCHEDULE_ID WHERE t1.F_STAUTS != '99'");
		if (!StringUtil.isEmpty(bean.getApplyYear())) {
			sb.append(" AND t1.F_APPLY_YEAR ='" + bean.getApplyYear() + "'");
		}
		if(!StringUtil.isEmpty(bean.getDeptName())) {
			sb.append(" AND t2.F_DEPT_NAME ='" + bean.getDeptName() + "'");
		}
		String sql = sb.toString();
		Pagination p =super.findObjectListBySql(sql, page, rows);
		List<Object[]> dataList = (List<Object[]>) p.getList();
		List<ScheduleProList> list = new ArrayList<ScheduleProList>();
		if (dataList != null && dataList.size() > 0) {
			int i = 1;
			BigDecimal pfAmountTotal = new BigDecimal(0);//项目总额总和
			BigDecimal firstAmountTotal = new BigDecimal(0);//一季度额度总和
			BigDecimal twoAmountTotal = new BigDecimal(0);//二季度额度总和
			BigDecimal threeAmountTotal = new BigDecimal(0);//三季度额度总和
			BigDecimal fourAmountTotal = new BigDecimal(0);//四季度额度总和
			ScheduleProList scheduleProList;
			for (Object[] obj: dataList) {
				scheduleProList = new ScheduleProList();
				scheduleProList.setSplId(Integer.parseInt(String.valueOf(obj[1])));
				scheduleProList.setfProName(String.valueOf(obj[4]));
				scheduleProList.setDeptName(String.valueOf(obj[15]));
				scheduleProList.setApplyYear(String.valueOf(obj[0]));
				if (obj[16] != null) {
					scheduleProList.setPfAmount(new BigDecimal(String.valueOf(obj[16])));
					pfAmountTotal = pfAmountTotal.add(new BigDecimal(String.valueOf(obj[16])));
				}
				if (obj[6] != null) {
					scheduleProList.setFirstAmount(new BigDecimal(String.valueOf(obj[6])));
					firstAmountTotal = firstAmountTotal.add(new BigDecimal(String.valueOf(obj[6])));
				}
				if (obj[7] != null) {
					scheduleProList.setTwoAmount(new BigDecimal(String.valueOf(obj[7])));
					twoAmountTotal = twoAmountTotal.add(new BigDecimal(String.valueOf(obj[7])));
				}
				if (obj[8] != null) {
					scheduleProList.setThreeAmount(new BigDecimal(String.valueOf(obj[8])));
					threeAmountTotal = threeAmountTotal.add(new BigDecimal(String.valueOf(obj[8])));
				}
				if (obj[9] != null) {
					scheduleProList.setFourAmount(new BigDecimal(String.valueOf(obj[9])));
					fourAmountTotal = fourAmountTotal.add(new BigDecimal(String.valueOf(obj[9])));
				}
				scheduleProList.setPageOrder(i++);
				list.add(scheduleProList);
			}
			list.get(0).setPfAmountTotal(pfAmountTotal);
			list.get(0).setFirstAmountTotal(firstAmountTotal);
			list.get(0).setTwoAmountTotal(twoAmountTotal);
			list.get(0).setThreeAmountTotal(threeAmountTotal);
			list.get(0).setFourAmountTotal(fourAmountTotal);
			p.setList(list);
		}
		return p;
	}

	@Override
	public List<ComboboxBean> getStatisticsYear() {
		StringBuilder sb=new StringBuilder("SELECT DISTINCT F_APPLY_YEAR FROM T_SCHEDULE ORDER BY F_APPLY_YEAR DESC");
		String str=sb.toString();
		Pagination p=super.findObjectListBySql(str,0,100);
		List<Object[]> dataList = (List<Object[]>) p.getList();
		List<ComboboxBean> dataEntities = new ArrayList<ComboboxBean>();
		if(dataList.size()>0){
			for (Object obj : dataList) {
				ComboboxBean entity = new ComboboxBean();
				entity.setIds(String.valueOf(obj));
				entity.setTexts(String.valueOf(obj));
				entity.setCodes(String.valueOf(obj));
				dataEntities.add(entity);
			}
		}
		return dataEntities;
	}
}