package com.braker.icontrol.cgmanage.procurement.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.procurement.manager.PurchasePlanMng;
import com.braker.icontrol.cgmanage.procurement.model.PurchasePlanBasic;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

/**
 * 采购计划ServiceImpl
 * 
 * @author wanping
 *
 */
@Service
@Transactional
public class PurchasePlanMngImpl extends BaseManagerImpl<PurchasePlanBasic> implements PurchasePlanMng {
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TNodeDataMng tNodeDataMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private PersonalWorkMng personalWorkMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private PrivateInforMng privateInforMng;
	
	@Autowired
	private ShenTongMng shenTongMng;

	@Override
	public void save(PurchasePlanBasic bean, User user) throws Exception {
		Date date = new Date();
		if (bean.getfId() == null) {
			//创建人、创建时间、发布时间 
			bean.setCreator(user.getName());
			bean.setCreateTime(date);
			bean.setfDeptId(user.getDpID());
			bean.setfDeptName(user.getDepartName());
			bean.setfUser(user.getId());
			bean.setfReqTime(date);
			Integer seq = shenTongMng.getSeq("PURCHASE_PLAN_BASIC_SEQ");
			bean.setfId(seq);
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getName());
			bean.setUpdateTime(date);
			bean.setfReqTime(date);
			bean.setfDeptId(user.getDpID());
			bean.setfDeptName(user.getDepartName());
		}
		
		//以下为工作流的节点配置（如果点送审则设置进入工作流1审核中）
		if ("1".equals(bean.getFlowStauts())) {
			//得到第一个审批节点key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "CGJH", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("CGJH", user.getDpID());
			Integer flowId = processDefin.getFPId();
			TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser = userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号 get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			super.saveOrUpdate(bean);//修改
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			Integer seq1 = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq1);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getfId());//意向公开申请ID
			work.setTaskCode(bean.getfPurchasePlanCode());//为申请单的单号
			work.setTaskName("[政采计划申请]"+bean.getfPurchaseName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/purchasePlanCheck/check?id="+bean.getfId());//审批url
			work.setUrl1("/purchasePlan/edit?id="+ bean.getfId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(date);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			Integer seq2 = shenTongMng.getSeq("personal_worktable_seq");
			work2.setfId(seq2);
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getfId());//申请单ID
			work2.setTaskCode(bean.getfPurchasePlanCode());//为申请单的单号
			work2.setTaskName("[政采计划申请]"+bean.getfPurchaseName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/purchasePlan/edit?id="+ bean.getfId()+"&editType=1");//退回修改url
			work2.setUrl1("/purchasePlan/edit?id="+ bean.getfId()+"&editType=0");//查看url
			work2.setUrl2("/purchasePlan/delete?id="+ bean.getfId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(date);//任务提交时间
			work2.setFinishTime(date);
			personalWorkMng.merge(work2);
		}else {
			//保存基本信息
			super.merge(bean);//修改
		}
		
		//更新采购申请主表状态
		PurchaseApplyBasic purchaseApplyBasic = cgsqMng.findUniqueByProperty("fpCode", bean.getfPurchaseCode());
		purchaseApplyBasic.setPlanStatus(bean.getFlowStauts());
		purchaseApplyBasic.setAgency(bean.getAgency());
		purchaseApplyBasic.setProcessLeaderName(bean.getProcessLeaderName());
		cgsqMng.merge(purchaseApplyBasic);
	}

	@Override
	public void reCall(Integer id) {
		//根据id查询对象
		PurchasePlanBasic bean = super.findById(id);
		
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId(), bean.getBeanCode(), "0");
		
		//给待审批人推送消息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "政采计划申请被撤回消息";
		String msg = "您待审批的  "+bean.getfPurchaseName()+",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注。";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean = (PurchasePlanBasic) reCall((CheckEntity) bean);
		this.updateDefault(bean);
	}

	@Override
	public Pagination checkPageList(PurchasePlanBasic bean, Integer page, Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchasePlanBasic WHERE fuserId='"+user.getId()+"' AND stauts in('1','0')");	
		if (!StringUtil.isEmpty(searchData)) {
			finder.append(" AND (fPurchaseCode like '%"+searchData+"%' or fPurchaseName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or amount like '%"+searchData+"%' or fPurchaseWay like '%"+searchData+"%' or agency like '%"+searchData+"%' or projectLeaderName like '%"+searchData+"%' or authorizedName like '%"+searchData+"%' or processLeaderName like '%"+searchData+"%' or fUserName like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		finder.append(" order by fReqTime desc");
		return super.find(finder, page, rows);
	}

	@Override
	public void check(TProcessCheck checkBean, PurchasePlanBasic bean, User user, String spjlFile) throws Exception {
		bean = this.findById(bean.getfId());
		CheckEntity entity = (CheckEntity)bean;
		String url = "/purchasePlanCheck/check?id=";
		String url1 = "/purchasePlan/edit?editType=0&fpCode=";
		bean = (PurchasePlanBasic)tProcessCheckMng.checkProcess(checkBean, entity, user, "CGJH", url, url1, spjlFile);
		super.saveOrUpdate(bean);
		//更新主表审批状态
		PurchaseApplyBasic purchaseApplyBasic = cgsqMng.findUniqueByProperty("fpCode", bean.getfPurchaseCode());
		purchaseApplyBasic.setPlanStatus(bean.getFlowStauts());
		cgsqMng.saveOrUpdate(purchaseApplyBasic);
	}
	
}
