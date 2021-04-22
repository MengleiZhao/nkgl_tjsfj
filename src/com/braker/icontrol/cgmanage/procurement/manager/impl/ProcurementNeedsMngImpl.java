package com.braker.icontrol.cgmanage.procurement.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
import com.braker.exception.ServiceException;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.procurement.manager.ProcurementNeedsMng;
import com.braker.icontrol.cgmanage.procurement.model.ProcurementNeedsInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;


/**
 * 需求申请的service实现类
 * @author 方淳洲
 * @createtime 2021-03-13
 * @updatetime 2021-03-13
 */
@Service
@Transactional
public class ProcurementNeedsMngImpl extends BaseManagerImpl<ProcurementNeedsInfo> implements ProcurementNeedsMng {
	
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
	private AttachmentMng attachmentMng;
	
	@Autowired
	private PrivateInforMng privateInforMng;
	
	@Autowired
	private ProcurementNeedsMng procurementNeedsMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private ShenTongMng shenTongMng;
	
	@Override
	public Pagination pageList(PurchaseApplyBasic bean, Integer page,Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' AND fCheckStauts = '9' AND (fpMethod ='政府采购' OR fpMethod = '委托代理机构采购')");
		if(user != null){ //用户
			finder.append(" AND fUser = :fUser");
			finder.setParam("fUser", user.getId());
			finder.append(" AND fDeptId = :fDeptId");
			finder.setParam("fDeptId", user.getDpID());
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%' or fbidStauts like '%"+searchData+"%' or fpMethod like '%"+searchData+"%') ");
		}
		finder.append(" order by fpId desc");
		return super.find(finder, page, rows);
	}

	@Override
	public List<ProcurementNeedsInfo> findByCgId(Integer id) {
		Finder finder = Finder.create(" FROM ProcurementNeedsInfo WHERE status <> '99' AND cgId = '"+id+"' ");
		return super.find(finder);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)//事务处理
	public void save(ProcurementNeedsInfo bean, String xqsfiles,String wtsfiles, User user) throws Exception {
		Date date = new Date();
		if(bean.getpId() == null){
			bean.setCreator(user.getName());
			bean.setCreateTime(date);
			bean.setXqUserId(user.getId());
			bean.setXqUserName(user.getName());
			bean.setXqDeptId(user.getDpID());
			bean.setXqDeptName(user.getDepartName());
			Integer seq = shenTongMng.getSeq("PROCUREMENT_NEEDS_INFO_SEQ");
			bean.setpId(seq);
		}else{
			bean.setUpdator(user.getName());
			bean.setUpdateTime(date);
			bean.setXqUserId(user.getId());
			bean.setXqUserName(user.getName());
			bean.setXqDeptId(user.getDpID());
			bean.setXqDeptName(user.getDepartName());
		}
		PurchaseApplyBasic applyBasic = cgsqMng.findById(bean.getCgId());
		applyBasic.setAuthorized(bean.getAuthorized());
		cgsqMng.saveOrUpdate(applyBasic);
		//以下为工作流的节点配置（如果点送审则设置进入工作流1审核中）
		if("1".equals(bean.getFlowStatus())){
			
			//得到第一个审批节点key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "CGXQ", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("CGXQ", user.getDpID());
			Integer flowId = processDefin.getFPId();
			TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser = userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号 get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			Integer seq1 = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq1);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getpId());//申请单ID
			work.setTaskCode(bean.getpCode());//为申请单的单号
			work.setTaskName("[采购需求申请]"+bean.getCgName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/procurementNeedsCheck/check?id="+bean.getpId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/procurementNeeds/detail?id="+bean.getCgId());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(date);//任务提交时间
			personalWorkMng.merge(work);
			
			/**叶添加**/
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			Integer seq2 = shenTongMng.getSeq("personal_worktable_seq");
			work2.setfId(seq2);
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getpId());//申请单ID
			work2.setTaskCode(bean.getpCode());//为申请单的单号
			work2.setTaskName("[采购需求申请]"+bean.getCgName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/procurementNeeds/edit?id="+bean.getCgId());//修改url
			work2.setUrl1("/procurementNeeds/detail?id="+bean.getCgId());//查看url
			work2.setUrl2("/procurementNeeds/delete?id="+bean.getCgId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(date);//任务提交时间
			work2.setFinishTime(date);
			personalWorkMng.merge(work2);
		}
		//保存基本信息
		super.saveOrUpdate(bean); //新增
		//保存附件信息
		attachmentMng.joinEntity(bean, xqsfiles);
		attachmentMng.joinEntity(bean, wtsfiles);		
	}

	@Override
	public String reCall(Integer id) {
		//根据id查询对象
		List<ProcurementNeedsInfo> list = procurementNeedsMng.findByCgId(id);
		ProcurementNeedsInfo bean = list.get(0);
		
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId(), bean.getBeanCode(), "0");
		
		//给待审批人推送消息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "采购需求申请被撤回消息";
		String msg = "您待审批的  "+bean.getCgName()+",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean = (ProcurementNeedsInfo) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "操作成功";
	
	}

	@Override
	public void delete(Integer id, String fId, User user) {
		//更改采购需求的显示状态
		if(fId!=null){
			personalWorkMng.deleteById(Integer.valueOf(fId));
		}
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		bean.setfStauts("99");
		super.saveOrUpdate(bean);
		personalWorkMng.sendMessageToUser(user.getId(), 0);
		
	}

	@Override
	public List<ProcurementNeedsInfo> findByCondition(String currentDate) {
		Finder finder = Finder.create(" FROM ProcurementNeedsInfo WHERE pCode like 'XQ" + currentDate + "%'");
		return super.find(finder);
	}

}
