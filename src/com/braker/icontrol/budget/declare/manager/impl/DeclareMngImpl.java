package com.braker.icontrol.budget.declare.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

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
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.budget.declare.manager.DeclareMng;
import com.braker.icontrol.budget.project.entity.TProBasicFunds;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicFundsMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.budget.project.manager.TProGoalDetailMng;
import com.braker.icontrol.budget.project.manager.TProGoalMng;
import com.braker.icontrol.budget.project.manager.TProPlanMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

/**
 * 上报库的service实现类
 * @author 叶崇晖
 * @createtime 2018-09-20
 * @updatetime 2018-09-20
 */
@Service
@Transactional
public class DeclareMngImpl extends BaseManagerImpl<TProBasicInfo> implements DeclareMng {
	
	@Autowired
	private TProGoalMng tProGoalMng;
	@Autowired
	private TProExpendDetailMng tProExpendDetailMng;
	@Autowired
	private TProPlanMng tProPlanMng;
	@Autowired
	private UserMng userMng ;
	@Autowired
	private TProGoalDetailMng tProGoalDetailMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TNodeDataMng tNodeDataMng;
	
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private TProBasicFundsMng tProBasicFundsMng;
	
	@Autowired
	private PrivateInforMng privateInforMng;
	
	/*
	 * 一上申报列表数据查询
	 * @author 叶崇晖
	 * @createtime 2018-09-21
	 * @updatetime 2018-09-21
	 */
	@Override
	public List<TProBasicInfo> yssbPageList(TProBasicInfo bean, int pageNo, int pageSize, User user) {
		Finder finder = Finder.create(" from TProBasicInfo where (FExt1 IS NULL OR FExt1!='0') ");
		finder.append(" and FProLibType='2' and FFlowStauts in('19','21','29','-21') and FExt4 in('10','11') and FProAppliDepart='"+user.getDepartName()+"'");
		//执行年份查询
		if(bean.getPlanStartYear() != null) {
			String y = bean.getPlanStartYear();
			finder.append(" and planStartYear='"+y+"'");
		} else {
			String y = new Date().toString();
			y = y.substring(y.length()-4);
			finder.append(" and planStartYear='"+(Integer.valueOf(y)+1)+"'");
		}
		if (!StringUtil.isEmpty(bean.getFProCode())) {
			finder.append(" and FProCode like :FProCode").setParam("FProCode", "%" + bean.getFProCode() + "%");
		}
		if (!StringUtil.isEmpty(bean.getFProName())) {
			finder.append(" and FProName like :FProName").setParam("FProName", "%" + bean.getFProName() + "%");
		}
		finder.append(" order by FExt4 desc");
		return super.find(finder);
	}

	/*
	 * 一上批量上报
	 * @author 叶崇晖
	 * @createtime 2018-09-20
	 * @updatetime 2018-09-20
	 */
	@Override
	public void firstUpApply(String fproIdLi, User user)  throws Exception{
		
		String[] strarray1 = fproIdLi.split(","); 
		for (int i = 0; i < strarray1.length; i++) {
			//把历史审批记录全部设置为1，表示重新审批
			TProBasicInfo info = super.findById(Integer.parseInt(strarray1[i]));
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),info.getJoinTable(),info.getBeanCodeField(),info.getBeanCode(), "YSSB", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("YSSB", user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			//改变下环节处理人，下环节处理人编码（id），下节点处理编码
			String userName = nextUser.getName();
			String userCode = nextUser.getId();
			String code = firstKey+"";
			//需要添加待办信息
			tProcessCheckMng.updateStauts(flowId,info.getBeanCode());
			//修改申报状态为11,填写一上申报数F_CB_1为项目申报金额
			getSession().createSQLQuery("UPDATE t_pro_basic_info SET F_CB_1=F_PRO_BUDGET_AMOUNT,F_EXT_4='11',F_FLOW_STAUTS='21',F_USER_NAME='"+userName+"',F_USER_CODE='"+userCode+"',F_EXT_11='"+code+"',F_NEXT_ASSIGNER_ID='"+userCode+"' WHERE F_PRO_ID='"+strarray1[i]+"'").executeUpdate();
		}
	}

	
	/*
	 * 一上审批列表数据查询
	 * @author 叶崇晖
	 * @createtime 2018-09-21
	 * @updatetime 2018-09-21
	 */
	@Override
	public Pagination ysspPageList(TProBasicInfo bean, int pageNo, int pageSize, User user) {
		Finder finder = Finder.create(" from TProBasicInfo where (FExt1 IS NULL OR FExt1!='0') and nextAssignerCode='"+user.getId()+"'");
		finder.append(" and FProLibType ='2' and FFlowStauts in('21') and FExt4 in('11')");
		//执行年份查询
		if(bean.getPlanStartYear() != null) {
			String y = bean.getPlanStartYear();
			finder.append(" and planStartYear='"+y+"'");
		} else {
			String y = new Date().toString();
			y = y.substring(y.length()-4);
			finder.append(" and planStartYear='"+(Integer.valueOf(y)+1)+"'");
		}
		if (!StringUtil.isEmpty(bean.getFProCode())) {
			finder.append(" and FProCode like :FProCode").setParam("FProCode", "%" + bean.getFProCode() + "%");
		}
		if (!StringUtil.isEmpty(bean.getFProName())) {
			finder.append(" and FProName like :FProName").setParam("FProName", "%" + bean.getFProName() + "%");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	
	
	
	/*
	 * 二上申报列表数据查询
	 * @author 叶崇晖
	 * @createtime 2018-09-27
	 * @updatetime 2018-09-27
	 */
	@Override
	public Pagination essbPageList(TProBasicInfo bean, int pageNo, int pageSize, User user) {
		Finder finder = Finder.create(" from TProBasicInfo where (FExt1 IS NULL OR FExt1!='0')");
		finder.append(" and FProLibType ='2' and  FExt5 ='0'");
		finder.append(" and FProAppliPeopleId='"+user.getId()+"'");
		//执行年份查询
		if(!StringUtil.isEmpty(bean.getPlanStartYear())) {
			String y = bean.getPlanStartYear();
			finder.append(" and planStartYear='"+y+"'");
		} else {
			String y = new Date().toString();
			y = y.substring(y.length()-4);
			finder.append(" and planStartYear='"+(Integer.valueOf(y)+1)+"'");
		}
		if (!StringUtil.isEmpty(bean.getFProCode())) {
			finder.append(" and FProCode like :FProCode").setParam("FProCode", "%" + bean.getFProCode() + "%");
		}
		if (!StringUtil.isEmpty(bean.getFProName())) {
			finder.append(" and FProName like :FProName").setParam("FProName", "%" + bean.getFProName() + "%");
		}
		if (bean.getFProOrBasic()!=null) {
			finder.append(" and FProOrBasic = '"+bean.getFProOrBasic()+"'");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	
	/*
	 * 二上申报数据保存
	 * @author 叶崇晖
	 * @createtime 2018-09-28
	 * @updatetime 2018-09-28
	 */
	@Override
	public TProBasicInfo secondUpApply(TProBasicInfo bean, User user, String opeType, String lxyjFiles, String ssfaFiles,String jxmbFiles, String fundsJson,String delIndex) throws Exception {
		//得到第一个审批节点key
		Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(),  "ESSB", user);
		//根据资源名称和当前登陆者所属部门查询对应工作流
		TProcessDefin processDefin = new TProcessDefin();
		if(1==bean.getFProOrBasic()){
			processDefin=tProcessDefinMng.getByBusiAndDpcode("ESSB", user.getDpID());
		}else if(0==bean.getFProOrBasic()){
			processDefin=tProcessDefinMng.getByBusiAndDpcode("JBZCESSB", user.getDpID());
		}
		Integer flowId= processDefin.getFPId();
		TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
		User nextUser=userMng.findById(node.getUserId());
		//把历史审批记录全部设置为1，表示重新审批
		tProcessCheckMng.updateStauts(flowId, bean.getBeanCode());
		TProBasicInfo info = super.findById(bean.getFProId());
		info.setFProBudgetAmount(bean.getFProBudgetAmount());
		info.setNextAssignerName(nextUser.getName());
		info.setNextAssignerCode(nextUser.getId());
		info.setFExt11(firstKey+"");
		info.setFFlowStauts("31");
		info.setFExt4("21");
		info.setCommitAmount2(bean.getFProBudgetAmount());//二上申报数
		info=(TProBasicInfo) super.saveOrUpdate(info);
		//添加审批人个人首页代办信息
		PersonalWork work = new PersonalWork();
		work.setUserId(info.getNextAssignerCode());//任务处理人ID既是下节点处理人ID
		work.setTaskId(info.getFProId());//项目ID
		work.setTaskCode(info.getFProCode());//项目编号
		work.setTaskName("[二上申报]"+bean.getFProName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
		work.setUrl("/declare/verdict2/"+info.getFProId());//审批地址
		work.setUrl1("/project/detail/"+ info.getFProId());//查看地址（审批完成时使用）
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
		work2.setTaskId(info.getFProId());//项目ID
		work2.setTaskCode(info.getFProCode());//项目编号
		work2.setTaskName("[二上申报]"+bean.getFProName());//任务名称模块（菜单）名称+操作如：
		work2.setUrl("/project/esedit/"+ info.getFProId());//退回修改地址（被退回时使用）
		work2.setUrl1("/project/detail/"+ info.getFProId());//查看地址
		work2.setUrl2("/project/delete/"+ info.getFProId());//删除地址（被退回时使用）
		work2.setTaskStauts("2");//已办
		work2.setBeforeUser(user.getName());//任务提交人姓名
		work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
		work2.setBeforeTime(new Date());//任务提交时间
		work2.setFinishTime(info.getCreateTime());
		work2.setType("2");//任务类型（1查看）
		work2.setTaskType("0");//任务归属（0发起人）
		personalWorkMng.merge(work2);
		
		// 保存项目支出明细
		tProExpendDetailMng.save(info.getFProId(),bean.getExpendList());
		/*// 保存项目计划
		tProPlanMng.save(bean.getPlanList(), currentUser, info);*/
		/*// 保存项目绩效目标总表
		TProGoal goal = tProGoalMng.save(bean.getGoal(), info,currentUser);
		// 保存项目绩效目标明细
		JSONArray array = JSONArray.fromObject("[" + jxmingxi.toString()+ "]");
		List<TProGoalDetail> li1 = JSONArray.toList(array,TProGoalDetail.class);
		tProGoalDetailMng.save(li1, goal, currentUser);*/
		// 保存资金来源明细
		JSONArray array = JSONArray.fromObject("[" + fundsJson.toString()+ "]");
		List<TProBasicFunds> fundsList = JSONArray.toList(array,TProBasicFunds.class);
		tProBasicFundsMng.save(info.getFProId(),fundsList);
		return info;
	}

	/*
	 * 二上审批列表数据查询
	 * @author 叶崇晖
	 * @createtime 2018-09-28
	 * @updatetime 2018-09-28
	 */
	@Override
	public Pagination esspPageList(TProBasicInfo bean, int pageNo, int pageSize, User user) {
		Finder finder = Finder.create(" from TProBasicInfo where (FExt1 IS NULL OR FExt1!='0')");
		finder.append(" and FProLibType ='2' and FFlowStauts in('31') and FExt4 in('21') and FExt5 ='0'");
		finder.append(" and nextAssignerCode='"+user.getId()+"'");
		//执行年份查询
		if(bean.getPlanStartYear() != null) {
			String y = bean.getPlanStartYear();
			finder.append(" and planStartYear='"+y+"'");
		} else {
			String y = new Date().toString();
			y = y.substring(y.length()-4);
			finder.append(" and planStartYear='"+(Integer.valueOf(y)+1)+"'");
		}
		if (!StringUtil.isEmpty(bean.getFProCode())) {
			finder.append(" and FProCode like :FProCode").setParam("FProCode", "%" + bean.getFProCode() + "%");
		}
		if (!StringUtil.isEmpty(bean.getFProName())) {
			finder.append(" and FProName like :FProName").setParam("FProName", "%" + bean.getFProName() + "%");
		}
		if (bean.getFProOrBasic()!=null) {
			finder.append(" and FProOrBasic = '"+bean.getFProOrBasic()+"'");
		}
		finder.append(" order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public String reCall(Integer id) throws Exception {
		//根据id查询对象
		TProBasicInfo bean=(TProBasicInfo)super.findById(id);
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="[二上申报]申请被撤回消息";
		String msg="您待审批的  "+bean.getFProName() +",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean.setCheckStauts("-34");//已撤回
		bean.setStauts("0");//暂存
		bean.setNextCheckKey("0");//设置到初始节点
		bean.setNextCheckUserId("");
		bean.setNextCheckUserName("");
		this.updateDefault(bean);
		return "操作成功";
	}
}
