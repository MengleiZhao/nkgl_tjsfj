package com.braker.icontrol.cgmanage.questioning.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.braker.icontrol.cgmanage.procurement.manager.ProcurementNeedsMng;
import com.braker.icontrol.cgmanage.questioning.manager.PurchasingQueryMng;
import com.braker.icontrol.cgmanage.questioning.model.PurchasingQuery;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
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
public class PurchasingQueryMngImpl extends BaseManagerImpl<PurchasingQuery> implements PurchasingQueryMng {
	
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
	public Pagination pageList(PurchasingQuery bean, Integer page,Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchasingQuery WHERE fStauts <>99 ");
		if(bean.getFpId()!=null){
			finder.append(" and fpId = '"+bean.getFpId()+"' ");
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or fpPype like '%"+searchData+"%' or fpMethod like '%"+searchData+"%' or fOrgName like '%"+searchData+"%' or fbidAmount like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or fUserName like '%"+searchData+"%') ");
		}
		finder.append(" order by fqId desc");
		return super.find(finder, page, rows);
	}
	
	@Override
	public Pagination answerPageList(PurchasingQuery bean, Integer page,Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchasingQuery WHERE fStauts <>99 and fAskStauts ='1' ");
		if(user!=null){
			finder.append(" and fUser = '"+user.getId()+"' ");
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or TO_DATE(fAskTime,'yyyy-mm-dd') like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or fUserName like '%"+searchData+"%') ");
		}
		finder.append(" order by fAnswerStauts,fAnswerTime desc");
		return super.find(finder, page, rows);
	}
	
	@Override
	public Pagination answerCheckPageList(PurchasingQuery bean, Integer page,Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchasingQuery WHERE fStauts <>99 and fAskStauts ='1' ");
		if(user!=null){
			finder.append(" and fuserId = '"+user.getId()+"' ");
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or TO_DATE(fAskTime,'yyyy-mm-dd') like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or fUserName like '%"+searchData+"%') ");
		}
		finder.append(" order by fqId desc");
		return super.find(finder, page, rows);
	}
	
	/* 质疑发起的保存
	 * @author 沈帆
	 * @createtime 2021年3月19日
	 * @updator 沈帆
	 * @updatetime 2021年3月19日
	 * @see com.braker.icontrol.cgmanage.questioning.manager.PurchasingQueryMng#saveAsk(com.braker.icontrol.cgmanage.questioning.model.PurchasingQuery, java.lang.String, com.braker.core.model.User)
	 */
	@Override
	public void saveAsk(PurchasingQuery bean, String files, User user)throws Exception{
		Date date = new Date();
		if(bean.getFqId() ==null){//新增
			//设置创建人、创建时间
			bean.setCreator(user.getName());
			bean.setCreateTime(date);
			Integer seq = shenTongMng.getSeq("PURCHASING_QUERY_SEQ");
			bean.setFqId(seq);
		}else {//修改
			bean.setUpdator(user.getName());
			bean.setUpdateTime(date);
		}
		bean.setfStauts(bean.getfAskStauts());
		PurchaseApplyBasic cgBean =cgsqMng.findById(bean.getFpId());
		bean.setfUser(cgBean.getfUser());
		bean.setfDeptId(cgBean.getfDeptId());
		bean.setfAskTime(date);
		bean.setfAnswerStauts("0");
		super.saveOrUpdate(bean);
		//保存附件
		attachmentMng.joinEntity(bean, files);
		//给采购主表设置完结状态
		Pagination p =this.pageList(bean, 1, 1000, user, "");
		List<PurchasingQuery>  list =(List<PurchasingQuery>) p.getList();
		for (PurchasingQuery PurchasingQuery : list) {
			if(PurchasingQuery.getfAskStauts().equals("0")){
				cgBean.setfAnswerStatus("0");
				break;
			}
			
			if(PurchasingQuery.getfAskStauts().equals("1")){
				cgBean.setfAnswerStatus("1");
			}
		}
		super.merge(cgBean);
	}

	public	void delete(Integer id, User user) throws Exception{
		PurchasingQuery bean =super.findById(id);
		bean.setfStauts("99");
		super.merge(bean);
		//给采购主表设置完结状态
		PurchaseApplyBasic cgBean =cgsqMng.findById(bean.getFpId());
		Pagination p =this.pageList(bean, 1, 1000, user, "");
		List<PurchasingQuery>  list =(List<PurchasingQuery>) p.getList();
		int num1 =0;//暂存的条数
		int num2 =0;//未答复的条数
		int num3 =0;//已答复的条数
		String status ="0";
		if(list!=null && list.size()>0){
			for (PurchasingQuery PurchasingQuery : list) {
				if(PurchasingQuery.getfAskStauts().equals("0")){
					num1++;	
				}
				if(PurchasingQuery.getfAskStauts().equals("1")){
					if(PurchasingQuery.getfAnswerStauts().equals("0")){
						num2++;
					}else if(PurchasingQuery.getfAnswerStauts().equals("1")){
						num3++;
					}
				}
			}
		}
		if(num1>0){
			status ="0";
		}else if(num3==list.size()){
			status ="9";
		}else {
			status ="1";
		}
		cgBean.setfAnswerStatus(status);
		super.merge(cgBean);
	}
	
	/* 质疑答复的保存
	 * @author 沈帆
	 * @createtime 2021年3月23日
	 * @updator 沈帆
	 * @updatetime 2021年3月23日
	 */
	@Override
	public void saveAnswer(PurchasingQuery bean, String files, User user)throws Exception{
		Date date = new Date();
		PurchasingQuery oldBean =super.findById(bean.getFqId());
		if(StringUtil.isEmpty(bean.getfQueryCode())){//新增
			oldBean.setfQueryCode(StringUtil.Random("ZYDF"));
		}else {//修改
			oldBean.setUpdator(user.getName());
			oldBean.setUpdateTime(date);
		}
		oldBean.setCheckStauts(bean.getCheckStauts());
		oldBean.setfAnswerTime(date);
		oldBean.setfAnswerRemark(bean.getfAnswerRemark());
		oldBean.setfReqTime(date);
		//送审
		if("1".equals(oldBean.getfCheckStauts())){
			//得到第一个审批节点key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), oldBean.getJoinTable(), oldBean.getBeanCodeField(), oldBean.getBeanCode(), "ZYDF", user);
			TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("ZYDF", user.getDpID());
			Integer flowId = processDefin.getFPId();
			TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser = userMng.findById(node.getUserId());
			//根据前面获得的角色的信息设置下一环节的用户名称/编码
			oldBean.setFuserId(nextUser.getId());
			oldBean.setUserName2(nextUser.getName());
			//设置下节点节点编码
			oldBean.setnCode(firstKey+"");
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId, oldBean.getBeanCode());

			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			Integer seq1 = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq1);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(oldBean.getFqId());//申请单ID
			work.setTaskCode(oldBean.getfQueryCode());//为申请单的单号
			work.setTaskName("[质疑答复]"+oldBean.getFpName());//任务名称模块（菜单）
			work.setUrl("/cgAsk/check?id="+oldBean.getFqId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/cgAsk/detailAnswer?id="+oldBean.getFqId());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setTaskType("1");//任务归属（1审批人
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(date);//任务提交时间
			personalWorkMng.merge(work);

			//添加一个自己的已办事项
			PersonalWork minwork = new PersonalWork();
			Integer seq2 = shenTongMng.getSeq("personal_worktable_seq");
			minwork.setfId(seq2);
			minwork.setUserId(user.getId());//任务处理人ID既是下节点处理人ID
			minwork.setTaskId(oldBean.getFpId());//申请单ID
			minwork.setTaskCode(oldBean.getfQueryCode());//为申请单的单号
			minwork.setTaskName("[质疑答复]"+oldBean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			minwork.setUrl("/cgAsk/editAnswer?id="+oldBean.getFqId());//退回修改url
			minwork.setUrl1("/cgAsk/detailAnswer?id="+oldBean.getFqId());//查看url
			minwork.setUrl2("/cgAsk/delete?id="+oldBean.getFqId());//退回删除url
			minwork.setTaskStauts("2");//待办
			minwork.setType("2");//任务类型：2-查看
			minwork.setTaskType("0");//任务归属（0发起人）
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(date);//任务提交时间
			minwork.setFinishTime(date);
			personalWorkMng.merge(minwork);
		}
		//保存附件
		attachmentMng.joinEntity(oldBean, files);
		super.merge(oldBean);
	}
	
	/*
	 * 保存采购质疑审批结果
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	public void saveCheckInfo(TProcessCheck checkBean, PurchasingQuery bean,
			User user, String spjlFile) throws Exception{
		bean=this.findById(bean.getFqId());
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/cgAsk/check?id=";
		String lookUrl ="/cgAsk/editAnswer?id=";
		bean=(PurchasingQuery)tProcessCheckMng.checkProcess(checkBean,entity,user,"ZYDF",checkUrl,lookUrl,spjlFile);
		if("9".equals(bean.getCheckStauts())){
			bean.setfAnswerStauts("1");
		}
		super.saveOrUpdate(bean);
		if("9".equals(bean.getCheckStauts())){//审批结束
			//给采购主表设置完结状态
			PurchaseApplyBasic cgBean =cgsqMng.findById(bean.getFpId());
			Pagination p =this.pageList(bean, 1, 1000, user, "");
			List<PurchasingQuery>  list =(List<PurchasingQuery>) p.getList();
			int num1 =0;//暂存的条数
			int num2 =0;//未答复的条数
			int num3 =0;//已答复的条数
			String status ="0";
			if(list!=null && list.size()>0){
				for (PurchasingQuery PurchasingQuery : list) {
					if(PurchasingQuery.getfAskStauts().equals("0")){
						num1++;	
					}
					if(PurchasingQuery.getfAskStauts().equals("1")){
						if(PurchasingQuery.getfAnswerStauts().equals("0")){
							num2++;
						}else if(PurchasingQuery.getfAnswerStauts().equals("1")){
							num3++;
						}
					}
				}
			}
			if(num1>0){
				status ="0";
			}else if(num3==list.size()){
				status ="9";
			}else {
				status ="1";
			}
			cgBean.setfAnswerStatus(status);
			super.merge(cgBean);
		}
	}
	
	/**
	 * 撤回答复申请
	 * @param id 主键id
	 * @param model
	 * @return
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	public void reCallAnswer(String id) throws Exception{
		PurchasingQuery bean =super.findById(Integer.valueOf(id));
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="质疑答复申请被撤回消息";
		String msg="您待审批质疑答复  "+bean.getFpName() +",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean=(PurchasingQuery) reCall((CheckEntity) bean);
		this.updateDefault(bean);
	}
	
	/**
	 * 撤回质疑发起
	 * @param id 主键id
	 * @param model
	 * @return
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	public void reCallAsk(String id) throws Exception{
		PurchasingQuery bean =super.findById(Integer.valueOf(id));
		bean.setfAskStauts("0");
		this.updateDefault(bean);
		//给采购主表设置完结状态
		PurchaseApplyBasic cgBean =cgsqMng.findById(bean.getFpId());
		Pagination p =this.pageList(bean, 1, 1000, null, "");
		List<PurchasingQuery>  list =(List<PurchasingQuery>) p.getList();
		for (PurchasingQuery PurchasingQuery : list) {
			if(PurchasingQuery.getfAskStauts().equals("0")){
				cgBean.setfAnswerStatus("0");
				break;
			}

			if(PurchasingQuery.getfAskStauts().equals("1")){
				cgBean.setfAnswerStatus("1");
			}
		}
		super.merge(cgBean);
	}
}
