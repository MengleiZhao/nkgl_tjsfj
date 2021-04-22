package com.braker.workflow.manager.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.test.ServiceEndpoints;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.Proposer;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.procurement.manager.PurchasePlanMng;
import com.braker.icontrol.cgmanage.procurement.model.PurchasePlanBasic;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TLinkData;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TLinkDataMng;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;


/**
 * 流程审批的service实现类
 * @author 李安达
 * @createtime 2019-04-24
 * @updatetime 2019-04-24
 */
@Service
@Transactional
public class TProcessCheckMngImpl extends BaseManagerImpl<TProcessCheck> implements TProcessCheckMng {
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TNodeDataMng tNodeDataMng;
	
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	
	@Autowired
	private TLinkDataMng tLinkDataMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private RoleMng roleMng;
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private RegisterApplyMng registerApplyMng;
	@Autowired
	private PurchasePlanMng purchasePlanMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	/*
	 * 历史审批记录
	 * @author 李安达
	 * @createtime 2019-04-24
	 * @updatetime 2019-04-24
	 */
	@Override
	public List<TProcessCheck> checkHistory(Integer FPId, String foCode) throws Exception{
		Finder finder = Finder.create(" FROM TProcessCheck WHERE FPId="+FPId+" AND foCode='"+foCode+"' ");
		finder.append(" order by FpcId desc");
		List<TProcessCheck> li = super.find(finder);
		return li;
	}

	/*
	 * 送审，添加审批流程信息
	 * @author 李安达
	 * @createtime 2019-04-24
	 * @updatetime 2019-04-24
	 */
	@Override
	public Integer addProcessCheck(String departCode,String tableName,String beanCodeField,String beanCode, String FBusiArea,User user) throws Exception{
		
		// 根据资源名称和当前登陆者所属部门查询对应工作流
		TProcessDefin processdefin=tProcessDefinMng.getByBusiAndDpcode(FBusiArea, departCode);
		//据工作流查询所有节点
		List<TNodeData> nodeDataList=tNodeDataMng.findByFlowId(processdefin.getFPId());
		Boolean roleBoolean0 = user.getRoleName().contains("实物管理岗");//是否包含实物管理岗
		Boolean roleBoolean1 = user.getRoleName().contains("会议号录入岗");//是否包含会议号录入岗
		Boolean roleBoolean2 = user.getRoleName().contains("采购管理岗");//是否包含采购管理岗
		Integer firstKey= null;
		if("GWCXSQ".equals(FBusiArea)&&!user.getRoleName().contains("分管财务局长")&&"8".equals(user.getDepart().getId())){
			for (int i = nodeDataList.size()-1; i >= 0; i--) {
				if("86".equals(nodeDataList.get(i).getUserId())){
					nodeDataList.remove(i);
				}
			}
		}
		
		if("GWCXSQ".equals(FBusiArea)&&"8".equals(user.getDepart().getId())||
				(("GDZCLY".contains(FBusiArea)&&roleBoolean0))||(("GDZCJH".contains(FBusiArea)&&roleBoolean0))||
				(("CGSQ".contains(FBusiArea)&&roleBoolean1))||(("CGSQ".contains(FBusiArea)&&roleBoolean2))){
			//得到第一个审批的节点
			 firstKey=getFirstNodeSQ(processdefin.getFPId(),tableName,beanCodeField,beanCode,nodeDataList,user);
		}else{
			//得到第一个审批的节点
			 firstKey=getFirstNode(processdefin.getFPId(),tableName,beanCodeField,beanCode,nodeDataList,user);
		}
		return firstKey;
	}

	/**
	 * 得到第一个审批的节点
	 * @param nodeDataList
	 * @return
	 */
	private Integer getFirstNodeSQ(Integer flowId,String tableName,String beanCodeField,String beanCode,List<TNodeData> nodeDataList,User user)throws Exception{
		int firstKey=0;
		TNodeData startNode=null;
		//得到用户下的角色map
		Map<String,String> roleMap=getRoleIdMap(user);
		//循环节点集合
		for(TNodeData node:nodeDataList){
			//默认是得到开始的节点key
			if("Start".equals(node.getCategory())){
				startNode=node;
			}
			//如果发起人在审批流程里面，那么直接跳过前面的审批，交由他审批状态通过的下一个人审批，并且获取下一流程节点id(除了预算编制岗外)
			if("8".equals(user.getDepart().getId())){
				if(user.getId().equals(node.getUserId()) && !user.getRoleName().contains("会计岗") && user.getRoleName().contains("分管财务局长")){
					startNode=node;
					break;
				}
			}else{
				if(user.getId().equals(node.getUserId()) && !user.getRoleName().contains("会计岗")&& !user.getRoleName().contains("实物管理岗")&& !user.getRoleName().contains("会议号录入岗")&& !user.getRoleName().contains("采购管理岗")){
					startNode=node;
					break;
				}
			}
			
		}
		firstKey=getNextKey(startNode,tableName, beanCodeField,beanCode,"1");
		return firstKey;
	}
	/**
	 * 得到第一个审批的节点
	 * @param nodeDataList
	 * @return
	 */
	private Integer getFirstNode(Integer flowId,String tableName,String beanCodeField,String beanCode,List<TNodeData> nodeDataList,User user)throws Exception{
		int firstKey=0;
		TNodeData startNode=null;
		//得到用户下的角色map
		Map<String,String> roleMap=getRoleIdMap(user);
		//循环节点集合
		for(TNodeData node:nodeDataList){
			//默认是得到开始的节点key
			if("Start".equals(node.getCategory())){
				startNode=node;
			}
			//如果发起人在审批流程里面，那么直接跳过前面的审批，交由他审批状态通过的下一个人审批，并且获取下一流程节点id(除了预算编制岗外)
			if("8".equals(user.getDepart().getId())){
				if(user.getId().equals(node.getUserId()) && !user.getRoleName().contains("会计岗") && !user.getRoleName().contains("分管财务局长")){
					startNode=node;
					break;
				}
			}else{
				if(user.getId().equals(node.getUserId()) && !user.getRoleName().contains("会计岗")){
					startNode=node;
					break;
				}
			}
		}
		firstKey=getNextKey(startNode,tableName, beanCodeField,beanCode,"1");
		return firstKey;
	}
	
	
	/**
	 * 得到用户下面的所有角色集合
	 * @param user
	 * @return
	 */
	private Map<String,String> getRoleIdMap(User user)throws Exception{
		Map<String,String> roleMap=new HashMap<String,String>();
		List<Role> roles=user.getRoles();
		for(Role role:roles){
			roleMap.put(role.getId(), role.getId());
		}
		return roleMap;
	}

	/**
	 * 添加审批记录
	 */
	@Override
	public Integer addCheckHistory(TProcessCheck processCheck,Integer flowId,String beanCode,String byUserId,int overFlag, User user,TNodeData node,String files) throws Exception{
		Integer id =shenTongMng.getSeq("T_PROCESS_CHECK_SEQ");
		processCheck.setFpcId(id);
		//processCheck.setFpcId(null);
		//创建人、创建时间
		processCheck.setFuserId(user.getAccountNo());
		//设置审批人，审批人id，流程定义表外键，审批人角色，审批结果，审批时间，审批内容
		processCheck.setFuserName(user.getName());
		processCheck.setFuserId(user.getId());
		processCheck.setFPId(flowId);
		processCheck.setFoCode(beanCode);
		processCheck.setFbyUserId(byUserId);//被审批人id
		processCheck.setFroleName(node.getRoleName());	//一个人对应一个岗位get(0)
		processCheck.setFroleId(node.getRoleId());
		processCheck.setFcheckTime(new Date());
		processCheck.setStauts("0");
		processCheck.setOverFlag(overFlag);
		processCheck.setNodeKey(node.getKeyId()+"");
		//保存信息
		processCheck.setFilesPid(files);
		processCheck=(TProcessCheck) super.save(processCheck);
		//如果上传附件不为空
		if(!StringUtil.isEmpty(files)){
			attachmentMng.joinEntity(processCheck,files);
		}
		return null;
	}
	
	/**
	 * 根据当前审批节点，得到审批结果的下一节点
	 * @param key 当前节点
	 * @param flowId  流程id
	 * @param checkResult  审批结果  0不通过,  1通过
	 * @return
	 */
	@Override
	public TNodeData getNextCheckKey(TNodeData node, String tableName,String beanCodeField,String beanCode,
			String checkResult) throws Exception{
		Integer nextKey=0;
		//得到下一个节点
		nextKey=getNextKey(node,tableName,beanCodeField,beanCode,checkResult);
		//根据节点key得到节点对象
		TNodeData nextNode=tNodeDataMng.getByFlowIdAndKey(node.getFPId(),nextKey);
		return nextNode;
	}
	/**
	 * 根据当前审批节点，得到审批以后的下一节点
	 * @param key 当前节点
	 * @param flowId  流程id
	 * @param checkResult  审批结果  0不通过,  1通过
	 * @return
	 */
	@Override
	public Integer getNextKey(TNodeData node,String  tableName,String beanCodeField,String beanCode,String checkResult)throws Exception{
		Integer nextKey=0;
		//获取判断节点通过的下一个节点
		if("1".equals(checkResult)){ //通过
			nextKey=node.getAgree();
			//如果有自定义的条件跳转
			if(StringUtils.isNotEmpty(node.getCustom())){
				nextKey=getNextKeyByCustom(node,tableName,beanCodeField,beanCode);
			}
		}else if("0".equals(checkResult)){//不通过
			nextKey=node.getRefuse();
			if(nextKey==null){ //如果没有设置不通过的流转节点，则默认跳回开始节点
				List<TNodeData> nodeList=tNodeDataMng.findByFlowId(node.getFPId());
				for(TNodeData nodedata:nodeList){
					if("Start".equals(nodedata.getCategory())){
						nextKey=nodedata.getKeyId();
						return nextKey;
					}
				}
			}
		}
		return nextKey;
	}


	/**
	 * 
	* @author:安达
	* @Title: getNextKeyByCustom 
	* @Description: 根据自定义的条件跳转，获取下一个节点
	* @param node  节点
	* @param tableName   数据库表名称
	* @param beanCodeField   编码的字段名
	* @param beanCode   编码字段值
	* @return
	* @return Integer    返回类型 
	* @date： 2019年9月11日下午3:25:15 
	* @throws
	 */
	private Integer getNextKeyByCustom(TNodeData node,String  tableName,String beanCodeField,String beanCode){
		String custom=node.getCustom();// 节点里配置的 跳转条件 例如：  amount>1000,-5;amount<=1000,-3
		String[] customs=custom.split(";"); //根据分号截取 每个条件  例如得到 amount>1000,-5 和  amount<=1000,-3
		for(String str:customs){
			String condition=str.split(",")[0]; //得到跳转条件  例如  amount>1000
			Integer nextKey=Integer.parseInt(str.split(",")[1]); //得到该条件对应的下一级审批节点
			// 根据 编码和查询条件去查询有没有返回值
			String sql="select 1 from "+tableName+" where  "+beanCodeField+"='"+beanCode+"' and  "+condition;
			SQLQuery query = getSession().createSQLQuery(sql);
			List<String> list = query.list();
			//如果有返回值，说明满足该条件，则跳转到对应的下一级审批节点
			if(list!=null && list.size()>0){
				return nextKey;
			}
		}
		return node.getAgree();
	}
	/**
	 * 流程审批
	 * @author 李安达
	 * @createtime 2019-04-24
	 * @updatetime 2019-04-24
	 */
	@Override
	public CheckEntity checkProcess (TProcessCheck checkBean, CheckEntity bean, User dgy,String FBusiArea,String checkUrl,String lookUrl,String files) throws Exception {
		//如果这个审批已经被撤回，无法审批
		if("-4".equals(bean.getCheckStauts()) || "-14".equals(bean.getCheckStauts()) || "-34".equals(bean.getCheckStauts())){
			throw new ServiceException("该申请已被申请人主动撤回，无法审批");
		}
		if(!dgy.getId().equals(bean.getNextCheckUserId())){
			throw new ServiceException("该事项已审核，请勿重复操作");
		}
		String key=bean.getNextCheckKey();//节点 key
		String deptId=departMng.findDeptByUserId(bean.getUserId())[0];
		//根据资源名称和当前登陆者所属部门查询对应工作流
		TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(FBusiArea, deptId);
		Integer flowId= processDefin.getFPId();
		//根据流程id和节点的key查询指定节点
		TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId, Integer.parseInt(key));
		//获取下一审批节点的key
		TNodeData nextNode=getNextCheckKey( node, bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), checkBean.getFcheckResult());
		//日期格式化（消息推送中使用）
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int overFlag=0;//流程审批结束状态
		if("Start".equals(nextNode.getCategory())){//如果被打回原点
			//如果被打回原点，设置审批状态为已退回，设置申请状态为暂存
			bean.setCheckStauts("-1");//已退回
			bean.setStauts("0");//暂存
			bean.setNextCheckKey(nextNode.getKeyId()+"");//设置到初始节点
			bean.setNextCheckUserId("");
			bean.setNextCheckUserName("");
			if("YSSB".equals(FBusiArea)){
				bean.setCheckStauts("-21");
				bean.setStauts("10");
			}else if("ESSB".equals(FBusiArea)||"JBZCESSB".equals(FBusiArea)){
				bean.setCheckStauts("-31");
				bean.setStauts("20");
			}
			//修改自己的待办信息，将待办改为已办,任务类型 改为查看
			personalWorkMng.updateStautsAndType(bean.getBeanCode(), dgy.getId(), "2", "2");
			//修改申请人的待办信息（已办到待办）
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), bean.getUserId(), "0", "3");
			String title=work.getTaskName()+"审批消息";
			String msg="您申请的  "+work.getTaskName()+"任务编号：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"被退回原点,请及时关注.";
			privateInforMng.setMsg(title, msg, bean.getUserId(), "3");
		  }
		else if("End".equals(nextNode.getCategory())){//如果是结束节点
			//审批结束且全部通过，设置审批状态为已审批（已通过）
			overFlag=1;
			bean.setCheckStauts("9");
			bean.setNextCheckUserId("");
			bean.setNextCheckUserName("");
			bean.setNextCheckKey(nextNode.getKeyId()+"");
			//修改所有人的任务状态为已办结
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), null, "4", "2");
			if("YSSB".equals(FBusiArea)){
				bean.setCheckStauts("29");
			}else if("ESSB".equals(FBusiArea)||"JBZCESSB".equals(FBusiArea)){
				bean.setCheckStauts("39");
				String title=work.getTaskName()+"审批消息";
				String msg="预算:  "+work.getTaskName()+" 任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"二下审批通过,请及时关注.";
				User dgy1 = userMng.findById("38");//窦广禹
				privateInforMng.setMsg(title, msg, dgy1.getId(), "2");
			}else if("HTND".equals(FBusiArea)){
				String title=work.getTaskName()+"审批消息";
				String msg="合同:  "+work.getTaskName()+" 任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
				User htgl = userMng.getUserByRoleNameAndDepartName("合同管理岗", "办公室");
				privateInforMng.setMsg(title, msg, htgl.getId(), "2");
			}
			String title=work.getTaskName()+"审批消息";
			String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
			privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
		}else {
			//首先修改自己的待办信息，将待办改为已办，将审批的url改为查看的url
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), dgy.getId(), "2", "2");
			//建立下一环节处理人的待办信息
			checkUrl=checkUrl+bean.getBeanId();//审批url
			//为了防止采购登记审批后  已办事项、办结事项中的查看报错  采购登记所有的跳转路径上的id都是以采购申请的id为主
			if("CGDJ".equals(FBusiArea)){
				RegisterApplyBasic rBean = registerApplyMng.findById(bean.getBeanId());
				lookUrl=lookUrl+rBean.getFpId(); //
			} else if("CGJH".equals(FBusiArea)) {
				PurchasePlanBasic purchasePlanBasic = purchasePlanMng.findById(bean.getBeanId());
				lookUrl=lookUrl+purchasePlanBasic.getfPurchaseCode();
			} else{
				lookUrl=lookUrl+bean.getBeanId(); //
			}
			Integer applyType = JudgmentApplyType(FBusiArea);
			if(!StringUtil.isEmpty(String.valueOf(applyType))){
				checkUrl+="&editType=1&applyType="+applyType;
				lookUrl+="&editType=0&applyType="+applyType;
			}

			//得到下一节点处理人
			User nextUser=userMng.findById(nextNode.getUserId());
			//新增下一节点处理人的待办信息
			personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
			//设置下节点处理人姓名和编号		
			bean.setNextCheckUserName(nextUser.getName());
			bean.setNextCheckUserId(nextUser.getId());
			bean.setNextCheckKey(nextNode.getKeyId()+"");
			
			
		}
		//添加流程审批记录
		addCheckHistory(checkBean,flowId, bean.getBeanCode(), bean.getUserId(),overFlag,dgy, node,files);
	//	personalWorkMng.sendMessageToUser(dgy.getId(), 2);
		return bean;
	}
	
	/*
	 * 获得相应业务的工作流
	 * 李安达
	 * @param FBusiArea 业务范围
	 * @return
	 */
	@Override
	public List<TNodeData> getNodeConf(String operUserId,String FBusiArea, String departId,String foCode,String nextKey,String  tableName,String beanCodeField,String beanCode,String checkResult) { 
		try {
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(FBusiArea,departId);
			if(tProcessDefin==null || tProcessDefin.getFPId()==null ){ //如果这个部门还没设置审批流程，就返回
				List<TNodeData> nodeList=new ArrayList<TNodeData>();
				return  nodeList;
			}
			//得到审批历史记录,重新审批的 ,而且去重复
			List<TProcessCheck> checkHistoryList=findByStautsAndDistinct(tProcessDefin.getFPId(), foCode,"0");
			//得到该流程审批集合，例如 员工--组长--经理--老板  
			List<TNodeData> nodeList=tNodeDataMng.findByFlowIdAndNextKey(tProcessDefin.getFPId(),nextKey, tableName, beanCodeField, beanCode, checkResult);
			
			//转换成map
			HashMap<String,TProcessCheck> keyIdMap=processKeyMap(checkHistoryList);
			//倒序排列
			Collections.reverse(checkHistoryList);
			List<TNodeData> returnList=new ArrayList<TNodeData>();
			
			boolean clearflag=false; //清空集合状态
			for(int i=0;i<nodeList.size();i++){
				//审批节点
				TNodeData node=nodeList.get(i);
				node.setCheckInfo(new TProcessCheck());
				//根据 用户id查询 对应的用户
				User user=userMng.findById(node.getUserId());
				node.setUser(user);
				//如果审批记录里的节点key等于当前节点的key 一致
				if( keyIdMap.get(node.getKeyId()+"")!=null){
					//设置右边流程节点的审批记录
					node.setCheckInfo(keyIdMap.get(node.getKeyId()+""));
				} 
				//申请人的用户信息
				User squser=userMng.findById(operUserId);
				//如果是合同拟定
				/*if("HTND".equals(FBusiArea)){
					//申请人的用户信息
					//User squser=userMng.findById(operUserId);
					//部门负责人的id
					User fzuser=userMng.getUserByRoleNameAndDepartName("处室负责人", squser.getDepartName());
					//如果流程是合同拟定,且部门负责人的审批节点再之后的流程里显示，就跳过
					if(fzuser.getId().equals(node.getUserId()) && i>0){
						continue;
					}
					//是合同拟定流程
					//如果发起人和节点人不是同一个人
					if(!operUserId.equals(node.getUserId())){
						returnList.add(node);
					}
				}else */if("CLSQ,GWJDSQ,GWJDSQ,HYSQ,PXSQ,ZJBX,GWJDBX,GWCGBX,HYBX,PXBX,CLBX,GWCXBX,HKDJ,JKSQ,BXSQ,CGSQ".contains(FBusiArea) && (squser.getRoleName().contains("会计岗") || squser.getRoleName().contains("会议号录入岗"))){
					// 如果是会计岗做，就不要跳过流程 ,因为这个人可能有多个角色，所以用包含会计岗判断,目前拍出的是支出模块流程（除了借款和公务出行）
					returnList.add(node);
				}else {
					//如果是不是合同拟定
					returnList.add(node);
					//如果发起人在审批节点里，直接跳过他之前的所有人
					//这一段的作用是：如果审批流程为： 员工--组长--经理--老板--财务 ， 当经理发起申请的时候  
					//左边的审批流程就显示为：经理--老板--财务
					Boolean roleBoolean0 = squser.getRoleName().contains("实物管理岗");//是否包含实物管理岗
					Boolean roleBoolean1 = squser.getRoleName().contains("会议号录入岗");//是否包含会议号录入岗
					Boolean roleBoolean2 = squser.getRoleName().contains("采购管理岗");//是否包含采购管理岗
					Boolean roleBoolean3 = squser.getRoleName().contains("分管财务局长");//是否包含财务分管局长
					if(operUserId.equals(user.getId()) && !clearflag){
						if(("GWCXBX".contains(FBusiArea)||"CLBX".contains(FBusiArea))&&"8".equals(squser.getDepart().getId())||
								(("GDZCLY".contains(FBusiArea)&&roleBoolean0))||(("GDZCJH".contains(FBusiArea)&&roleBoolean0))||
								(("CGSQ".contains(FBusiArea)&&roleBoolean1))||(("CGSQ".contains(FBusiArea)&&roleBoolean2))||
							(("CLSQ".contains(FBusiArea)&&roleBoolean3))){
							//1.局领导的公务出行和出差,2.实物管理岗的资产领用和交回流程，3.会议号录入岗和采购管理岗的采购申请流程，不跳过
							
						}else{
							returnList.clear();
							clearflag=true; //只跳过第一次，第二次不跳过了
						}
					}
				}
			}
			//倒序排列
			Collections.reverse(returnList);
			return returnList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TNodeData>();
			
		}
	}
	
	/**
	 *   获取已经去重复了的节点集合
	 * @param operUserId
	 * @param FBusiArea
	 * @param departId
	 * @param foCode
	 * @param nextKey
	 * @param tableName
	 * @param beanCodeField
	 * @param beanCode
	 * @param checkResult
	 * @return
	 * @author sf
	 * @throws Exception 
	 * @createtime 2020年5月16日
	 * @updator 陈睿超
	 * @updatetime 2020年5月16日
	 */
	@Override
	public List<TNodeData> getDistinctNodeList(String operUserId,String FBusiArea, String departId,String foCode,String nextKey,String  tableName,String beanCodeField,String beanCode,String checkResult) throws Exception { 
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(FBusiArea,departId);
		//得到该流程审批集合，例如 员工--组长--经理--老板  
		List<TNodeData> nodeList=tNodeDataMng.findByFlowIdAndNextKey(tProcessDefin.getFPId(),nextKey, tableName, beanCodeField, beanCode, checkResult);
		Set<String> userIdSet=new HashSet<String>();  //保存不重复用户名的set
		for(TNodeData node:nodeList){
			userIdSet.add(node.getUserId());
		}
		List<TNodeData> newNodeList=new ArrayList<TNodeData>();//新节点集合
		for(TNodeData node:nodeList){
			for(String userId:userIdSet){
				if(node.getUserId().equals(userId)){
					newNodeList.add(node); //这里就是装入不重复的节点集合
				}
			}
		}
		return newNodeList;
	}
	/*
	 * 获得去重复以后的右侧工作流
	 * 李安达
	 * @param FBusiArea 业务范围
	 * @return
	 */
	@Override
	public List<TNodeData> getDistinctNodeConf(String operUserId,String FBusiArea, String departId,String foCode,String nextKey,String  tableName,String beanCodeField,String beanCode,String checkResult) { 
		try {
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(FBusiArea,departId);
			if(tProcessDefin==null || tProcessDefin.getFPId()==null ){ //如果这个部门还没设置审批流程，就返回
				List<TNodeData> nodeList=new ArrayList<TNodeData>();
				return  nodeList;
			}
			//得到审批历史记录,重新审批的 ,而且去重复
			List<TProcessCheck> checkHistoryList=findByStautsAndDistinct(tProcessDefin.getFPId(), foCode,"0");
			//得到去重复的节点集合
			List<TNodeData> nodeList=getDistinctNodeList( operUserId, FBusiArea,  departId, foCode, nextKey,  tableName, beanCodeField, beanCode, checkResult);
			//转换成map
			HashMap<String,TProcessCheck> keyIdMap=processKeyMap(checkHistoryList);
			//倒序排列
			Collections.reverse(checkHistoryList);
			List<TNodeData> returnList=new ArrayList<TNodeData>();
			
			boolean clearflag=false; //清空集合状态
			for(int i=0;i<nodeList.size();i++){
				//审批节点
				TNodeData node=nodeList.get(i);
				node.setCheckInfo(new TProcessCheck());
				//根据 用户id查询 对应的用户
				User user=userMng.findById(node.getUserId());
				node.setUser(user);
				//如果审批记录里的节点key等于当前节点的key 一致
				if( keyIdMap.get(node.getKeyId()+"")!=null){
					//设置右边流程节点的审批记录
					node.setCheckInfo(keyIdMap.get(node.getKeyId()+""));
				} 
				returnList.add(node);
				//如果发起人再审批节点里，直接跳过他之前的所有人
				// 这一段的作用是：如果审批流程为： 员工--组长--经理--老板--财务 ， 当经理发起申请的时候  
				//左边的审批流程就显示为：经理--老板--财务
				if(operUserId.equals(user.getId()) && !clearflag){
					returnList.clear();
					clearflag=true; //只跳过第一次，第二次不跳过了
				}
			}
			//倒序排列
			Collections.reverse(returnList);
			return returnList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TNodeData>();
			
		}
	}
	
	/**
	 * 集合转换成map，key是拒绝节点key
	 * @param nodeList
	 * @return
	 */
	private HashMap<String,TProcessCheck> processKeyMap(List<TProcessCheck> checkHistoryList)throws Exception{
		HashMap<String,TProcessCheck> map=new HashMap<String,TProcessCheck>();
		for(TProcessCheck process: checkHistoryList){
			map.put(process.getNodeKey().toString(), process);
		}
		return map;
	}
	
	/**
	 * 修改流程审批状态
	 */
	@Override
	public void updateStauts(Integer FPId,  String foCode) throws Exception{
		
		String sql="update T_PROCESS_CHECK set F_STAUTS=1  where F_P_ID="+FPId+" and F_O_CODE='"+foCode+"'";
		Query query=getSession().createSQLQuery(sql);
		query.executeUpdate();
		// 删除个人任务中 所有该项目的非完结信息
		List<PersonalWork> workLi = personalWorkMng.findByCodeAndUser(foCode, null);
		for (int i = 0; i < workLi.size(); i++) {
			personalWorkMng.delete(workLi.get(i));
		}
	}
	/**
	 * 根据状态获得审批记录
	 */
	@Override
	public List<TProcessCheck> findByStauts(Integer FPId,  String foCode,String stauts) throws Exception{
		Finder finder = Finder.create(" FROM  TProcessCheck WHERE FPId="+FPId+" AND foCode='"+foCode+"' and stauts='"+stauts+"' ");
		finder.append(" order by FpcId desc ");
		List<TProcessCheck> list = super.find(finder);
		return list;
	}
	
	/**
	 * 根据状态获得审批记录,这里是右侧显示，需要去重复
	 */
	@Override
	public List<TProcessCheck> findByStautsAndDistinct(Integer FPId, String foCode,String stauts) throws Exception{
		if(StringUtils.isEmpty(foCode)){
			return new ArrayList<TProcessCheck>();
		}
		Finder finder = Finder.create(" FROM  TProcessCheck WHERE FPId="+FPId+" AND foCode='"+foCode+"' and stauts='"+stauts+"' ");
		finder.append("  order by FpcId desc ");
		List<TProcessCheck> list = super.find(finder);
		return list;
	}
	/**
	 * 根据状态获得审批记录,这里是右侧显示，需要去重复
	 */
	@Override
	public String getHistoryNodes(String FBusiArea ,String departId,String foCode ) {
		String historyNodes="";
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(FBusiArea,departId);
		if(tProcessDefin==null || tProcessDefin.getFPId()==null ){ //如果这个部门还没设置审批流程，就返回
			return  historyNodes;
		}
		List<TProcessCheck> historyCheckList =new ArrayList<TProcessCheck>();
		try {
			historyCheckList = findByStautsAndDistinct(tProcessDefin.getFPId(),foCode,"0");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		for(TProcessCheck tProcessCheck:historyCheckList){
			if("".equals(historyNodes)){
				historyNodes=tProcessCheck.getNodeKey()+"";
			}else{
				historyNodes=historyNodes+","+tProcessCheck.getNodeKey();
			}
		}
		return historyNodes;
	}
	

	@Override
	public String JudgmentProcess(String type) {
		if("1".equals(type)){//通用是申请
			return "TYSXSQ";
		}else if("2".equals(type)){//会议申请
			return "HYSQ";
		}else if("3".equals(type)){//培训申请
			return "PXSQ";
		}else if("4".equals(type)){//差旅申请
			return "CLSQ";
		}else if("5".equals(type)){//公务接待申请	
			return "GWJDSQ";
		}else if("6".equals(type)){//公务用车申请	
			return "GCYWSQ";
		}else if("7".equals(type)){//公务出国申请	
			return "GWCGSQ";
		}
		return null;
	}
	
	@Override
	public Integer JudgmentApplyType(String FBusiArea) {
		if("TYSXSQ".equals(FBusiArea) || "TYSXFLSQ".equals(FBusiArea) || "TYSXSPPSSQ".equals(FBusiArea)){//通用是申请
			return 1;
		}else if("HYSQ".equals(FBusiArea)){//会议申请
			return 2;
		}else if("PXSQ".equals(FBusiArea)){//培训申请
			return 3;
		}else if("CLSQ".equals(FBusiArea)){//差旅申请
			return 4;
		}else if("GWJDSQ".equals(FBusiArea)){//公务接待申请	
			return 5;
		}else if("GCYWSQ".equals(FBusiArea)){//公务用车申请	
			return 6;
		}else if("GWCGSQ".equals(FBusiArea)){//公务出国申请	
			return 7;
		}else if("GWCXSQ".equals(FBusiArea)){//公务出行
			return 8;
		}
		return null;
	}

	@Override
	public CheckEntity checkProcessTYSXSQ(TProcessCheck checkBean,
			CheckEntity bean, User user, String FBusiArea, String checkUrl,
			String lookUrl, String files, String suggestDepId,ApplicationBasicInfo abi) throws Exception {
		
		String key=bean.getNextCheckKey();//节点 key
		String deptId=departMng.findDeptByUserId(bean.getUserId())[0];
		/*//根据资源名称和当前登陆者所属部门查询对应工作流
		TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(FBusiArea, deptId);
		Integer flowId= processDefin.getFPId();
		//根据流程id和节点的key查询指定节点
		TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId, Integer.parseInt(key));
		//获取下一审批节点的key
		Integer nextKey=getNextCheckKey( Integer.parseInt(key), flowId, checkBean.getFcheckResult());*/
		
		//日期格式化（消息推送中使用）
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int overFlag=0;//流程审批结束状态
		if("0".equals(checkBean.getFcheckResult())){
			//不通过
			//如果被打回原点，设置审批状态为已退回，设置申请状态为暂存
			bean.setCheckStauts("-1");//已退回
			bean.setStauts("0");//暂存
			bean.setNextCheckKey("1");//设置到初始节点
			bean.setNextCheckUserId("");
			bean.setNextCheckUserName("");
			//修改自己的待办信息，将待办改为已办,任务类型 改为查看
			personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//修改申请人的待办信息（已办到待办）
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), bean.getUserId(), "0", "3");
			String title=work.getTaskName()+"审批消息";
			String msg="您申请的  "+work.getTaskName()+"任务编号：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"被退回原点,请及时关注.";
			privateInforMng.setMsg(title, msg, bean.getUserId(), "3");
			
		}else if("1".equals(checkBean.getFcheckResult())){
			/*//通过
			//首先修改自己的待办信息，将待办改为已办，将审批的url改为查看的url
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//建立下一环节处理人的待办信息
			checkUrl=checkUrl+work.getTaskId()+"&applyType="+abi.getType();//审批url
			lookUrl=lookUrl+work.getTaskId()+"&applyType="+abi.getType(); //查看url
			
			User nextUser = userMng.findById(abi.getFuserId());
			//新增下一节点处理人的待办信息
			personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
			
			
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setNextCheckUserName(abi.getUserName2());
			bean.setNextCheckUserId(abi.getFuserId());
			
			if("0".equals(checkBean.getFcheckResult())){
				String title=work.getTaskName()+"审批消息";
				String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"审批不通过,请及时关注.";
				privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
			}else {
				String title=work.getTaskName()+"审批消息";
				String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"审批通过,请及时关注.";
				privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
				
			}*/
			
			//通过
			PersonalWork work = new PersonalWork();
			/*if(user.getRoleName().contains("会计岗")){//如果当前流程审核到会计岗，则结束流程
				overFlag=1;
				bean.setCheckStauts("9");
				bean.setNextCheckUserId("");
				bean.setNextCheckUserName("");
				bean.setNextCheckKey("");
				
				//修改所有人的任务状态为已办结
				work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), null, "4", "2");
				
				String title=work.getTaskName()+"审批消息";
				String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
				privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
			}else {
			
			}*/
			//首先修改自己的待办信息，将待办改为已办，将审批的url改为查看的url
			work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//建立下一环节处理人的待办信息
			checkUrl=checkUrl+work.getTaskId()+"&applyType="+abi.getType();//审批url
			lookUrl=lookUrl+work.getTaskId()+"&editType=0&applyType="+abi.getType();//查看url
			
			User nextUser = userMng.findById(abi.getFuserId());
			//新增下一节点处理人的待办信息
			personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
			
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setNextCheckUserName(abi.getUserName2());
			bean.setNextCheckUserId(abi.getFuserId());
		}else if("2".equals(checkBean.getFcheckResult())){
			//通过并终止
			//审批结束且全部通过，设置审批状态为已审批（已通过）
			overFlag=1;
			bean.setCheckStauts("9");
			bean.setNextCheckUserId("");
			bean.setNextCheckUserName("");
			bean.setNextCheckKey("");
			//修改所有人的任务状态为已办结
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), null, "4", "2");
			String title=work.getTaskName()+"审批消息";
			String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
			privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
			
			
			/*//通过并终止
			//通过并终止后强制推送到会计岗再审核一次
			//修改自己的待办信息，将待办改为已办,任务类型 改为查看
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//建立下一环节处理人的待办信息
			checkUrl=checkUrl+work.getTaskId()+"&applyType="+abi.getType();//审批url
			lookUrl=lookUrl+work.getTaskId()+"&editType=0&applyType="+abi.getType(); //查看url
			
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			String userId = roleMng.getUserIdByRoleName("会计岗");
			User nextUser = userMng.findById(userId);
			//新增下一节点处理人的待办信息
			personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
			
			bean.setNextCheckUserName(nextUser.getName());
			bean.setNextCheckUserId(nextUser.getId());*/
		}
		//添加流程审批记录
		//强行写成当前登入人的数据
		TNodeData node=new TNodeData();
		node.setRoleId(user.getRoles().get(0).getId());
		node.setRoleName(user.getRoleName());
		//因为是手动选择下一个人；写死了10000
		addCheckHistory(checkBean,10000, bean.getBeanCode(), bean.getUserId(),overFlag,user, node,files);
		personalWorkMng.sendMessageToUser(user.getId(), 0);
		super.merge(bean);
		return bean;
	}
	
	
	@Override
	public CheckEntity checkProcessTYSXBX(TProcessCheck checkBean,
			CheckEntity bean, User user, String FBusiArea, String checkUrl,
			String lookUrl, String files,ReimbAppliBasicInfo rabi) throws Exception {
		//新建一个消息推送信息
		//日期格式化（消息推送中使用）
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int overFlag=0;//流程审批结束状态
		if("0".equals(checkBean.getFcheckResult())){
			//不通过
			//如果被打回原点，设置审批状态为已退回，设置申请状态为暂存
			bean.setCheckStauts("-1");//已退回
			bean.setStauts("0");//暂存
			bean.setNextCheckKey("1");//设置到初始节点
			bean.setNextCheckUserId("");
			bean.setNextCheckUserName("");
			//修改自己的待办信息，将待办改为已办,任务类型 改为查看
			personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//修改申请人的待办信息（已办到待办）
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), bean.getUserId(), "0", "3");
			String title=work.getTaskName()+"审批消息";
			String msg="您申请的  "+work.getTaskName()+"任务编号：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"被退回原点,请及时关注.";
			privateInforMng.setMsg(title, msg, bean.getUserId(), "3");
		}else if("1".equals(checkBean.getFcheckResult())){
			//通过
			//首先修改自己的待办信息，将待办改为已办，将审批的url改为查看的url
			//PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//建立下一环节处理人的待办信息
			/*checkUrl=checkUrl+work.getTaskId()+"&applyType=tysx";//审批url
			lookUrl=lookUrl+work.getTaskId()+"&applyType=tysx"; //查看url
			
			User nextUser = userMng.findById(rabi.getFuserId());
			//新增下一节点处理人的待办信息
			personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
			
			
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setNextCheckUserName(rabi.getUserName2());
			bean.setNextCheckUserId(rabi.getFuserId());*/
			PersonalWork work = new PersonalWork();
			work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//建立下一环节处理人的待办信息
			checkUrl=checkUrl+work.getTaskId()+"&applyType=tysx";//审批url
			lookUrl=lookUrl+work.getTaskId()+"&applyType=tysx"; //查看url
			
			User nextUser = userMng.findById(rabi.getFuserId());
			//新增下一节点处理人的待办信息
			personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
			
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setNextCheckUserName(rabi.getUserName2());
			bean.setNextCheckUserId(rabi.getFuserId());
			if("0".equals(checkBean.getFcheckResult())){
				String title=work.getTaskName()+"审批消息";
				String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"审批通过,请及时关注.";
				privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
			}
			
			/*if(user.getRoleName().contains("会计岗")){//是否是会计岗，如果是则结束审批
				overFlag=1;
				bean.setCheckStauts("9");
				bean.setNextCheckUserId("");
				bean.setNextCheckUserName("");
				bean.setNextCheckKey("");
				bean.setNextCheckKey("");
				//出纳受理状态  0未付讫
				bean.setCashierType("0");
				//修改所有人的任务状态为已办结
				work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), null, "4", "2");
				String title=work.getTaskName()+"审批消息";
				String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
				privateInforMng.setMsg(title, msg, bean.getUserId(), "4");
			}else{
				work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
				//建立下一环节处理人的待办信息
				checkUrl=checkUrl+work.getTaskId()+"&applyType=tysx";//审批url
				lookUrl=lookUrl+work.getTaskId()+"&applyType=tysx"; //查看url
				
				User nextUser = userMng.findById(rabi.getFuserId());
				//新增下一节点处理人的待办信息
				personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
				
				//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
				bean.setNextCheckUserName(rabi.getUserName2());
				bean.setNextCheckUserId(rabi.getFuserId());
				if("0".equals(checkBean.getFcheckResult())){
					String title=work.getTaskName()+"审批消息";
					String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"审批通过,请及时关注.";
					privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
				}
			}*/
		}else if("2".equals(checkBean.getFcheckResult())){
			//通过并终止
			//审批结束且全部通过，设置审批状态为已审批（已通过）
			overFlag=1;
			bean.setCheckStauts("9");
			bean.setNextCheckUserId("");
			bean.setNextCheckUserName("");
			bean.setNextCheckKey("");
			bean.setNextCheckKey("");
			//出纳受理状态  0未付讫
			bean.setCashierType("0");
			//修改所有人的任务状态为已办结
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), null, "4", "2");
			String title=work.getTaskName()+"审批消息";
			String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
			privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
		}
		
		//添加流程审批记录
		//强行写成当前登入人的数据
		TNodeData node=new TNodeData();
		node.setRoleId(user.getRoles().get(0).getId());
		node.setRoleName(user.getRoleName());
		//因为是手动选择下一个人；写死了10001
		addCheckHistory(checkBean,10001, bean.getBeanCode(), bean.getUserId(),overFlag,user, node,files);
		personalWorkMng.sendMessageToUser(user.getId(), 0);
		super.merge(bean);
		super.merge(rabi);
		return bean;
	}

	@Override
	public List<TNodeData> getInSideAdjustNodeConf(String userId,
			String departId, String foCode) {
		try {
			//通过调减部门id查调减部门负责人
			String reduceUserId = roleMng.getUserIdByDepartIdAndRoleName(departId, "处室负责人");
			//通过调增(当前)部门负责人的id查调增部门的主管校长
			String currentManagerId = roleMng.getManagerIdByUserId(userId);
			//通过调减部门id查调减部门的主管校长
			String receiveManagerId = departMng.findById(departId).getManager().getId();
			//预算管理岗的部门负责人id
			String budgetUserId = roleMng.getUserIdByRoleName("预算管理岗");
			//财务部部门负责人id
			String financeId = roleMng.getUserIdByDepartNameAndRoleName("财务处", "处室负责人");
			
			//组装流程
			List<TNodeData> nodeList = new ArrayList<>();
			//第一级审批人-调减部门负责人id
			TNodeData nodeData1 = new TNodeData();
			nodeData1.setFPId(0);	
			nodeData1.setKeyId(1);
			nodeData1.setUserId(reduceUserId);
			nodeData1.setUser(userMng.findById(reduceUserId));
			nodeList.add(nodeData1);
			//第二级审批人-调增部门主管校长
			TNodeData nodeData2 = new TNodeData();
			nodeData2.setFPId(0);	
			nodeData2.setKeyId(2);
			nodeData2.setUserId(currentManagerId);
			nodeData2.setUser(userMng.findById(currentManagerId));
			nodeList.add(nodeData2);
			//第三级审批人-调减部门主管校长
			TNodeData nodeData3 = new TNodeData();
			nodeData3.setFPId(0);	
			nodeData3.setKeyId(3);
			nodeData3.setUserId(receiveManagerId);
			nodeData3.setUser(userMng.findById(receiveManagerId));
			nodeList.add(nodeData3);
			//第四级审批人-预算管理岗
			TNodeData nodeData4 = new TNodeData();
			nodeData4.setFPId(0);	
			nodeData4.setKeyId(4);
			nodeData4.setUserId(budgetUserId);
			nodeData4.setUser(userMng.findById(budgetUserId));
			nodeList.add(nodeData4);
			//第五级审批人-财务部部门负责人
			TNodeData nodeData5 = new TNodeData();
			nodeData5.setFPId(0);	
			nodeData5.setKeyId(5);
			nodeData5.setUserId(financeId);
			nodeData5.setUser(userMng.findById(financeId));
			nodeList.add(nodeData5);
			
			//如果审批流中调增部门的主管校长与调减部门的主管校长一致
			if(currentManagerId.equals(receiveManagerId)){
				//删除其中一个主管校长
				nodeList.remove(2);
				//重新配置流程
				nodeList.get(2).setKeyId(3);
				nodeList.get(3).setKeyId(4);
			}
			
			//得到审批历史记录,重新审批的 ,而且去重复
			List<TProcessCheck> checkHistoryList = findByStautsAndDistinct(0, foCode, "0");
			//转换成map
			HashMap<String,TProcessCheck> keyIdMap = processKeyMap(checkHistoryList);
			//倒序排列
			Collections.reverse(checkHistoryList);
			//返回集合
			List<TNodeData> returnList = new ArrayList<TNodeData>();
			
			for(int i=0;i<nodeList.size();i++){	
				//审批节点
				TNodeData node = nodeList.get(i);
				node.setCheckInfo(new TProcessCheck());
				//根据 用户id查询 对应的用户
				User user = userMng.findById(node.getUserId());
				node.setUser(user);
				//如果审批记录里的节点key等于当前节点的key 一致
				if(keyIdMap.get(String.valueOf(node.getKeyId())) != null){
					//设置右边流程节点的审批记录
					node.setCheckInfo(keyIdMap.get(String.valueOf(node.getKeyId())));
				} 
				returnList.add(node);
			}
			//倒序排列
			Collections.reverse(returnList);
			return returnList;
		} catch (Exception e) {
			return new ArrayList<TNodeData>();
		}
	}

	@Override
	public CheckEntity inSideAdjustCheckProcess(TProcessCheck checkBean,
			CheckEntity bean, User user, String checkUrl, String lookUrl,
			String files, String departId) throws Exception {
		
		//如果这个审批已经被撤回，无法审批
		if("-4".equals(bean.getCheckStauts())){
			throw new ServiceException("该申请已被申请人主动撤回，无法审批");
		}
		if(!user.getId().equals(bean.getNextCheckUserId())){
			throw new ServiceException("该事项已审核，请勿重复操作");
		}
		//自定义流程
		List<TNodeData> nodeList = getInSideAdjustNodeConf(bean.getUserId(), departId, checkBean.getFoCode());
		//倒序排列
		Collections.reverse(nodeList);
		//当前审批节点key
		Integer key = Integer.valueOf(bean.getNextCheckKey());
		//当前节点对象
		TNodeData node = null;
		//下一审批节点对象
		TNodeData nextNode = null;
		if(key == nodeList.size()){	//当前是最后级审批人
			node = nodeList.get(nodeList.size()-1);
		}else {
			node = nodeList.get(key-1);
			nextNode = nodeList.get(key);
		}
		
		//日期格式化（消息推送中使用）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int overFlag = 0;//流程审批结束状态
		if("1".equals(checkBean.getFcheckResult())) {	//通过
			if(nextNode == null){	//如果下节点对象为空，表示流程完结
				//审批结束且全部通过，设置审批状态为已审批（已通过）
				overFlag=1;
				bean.setCheckStauts("9");
				bean.setNextCheckUserId("");
				bean.setNextCheckUserName("");
				bean.setNextCheckKey(String.valueOf(key+1));
				//修改所有人的任务状态为已办结
				PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), null, "4", "2");
				String title=work.getTaskName()+"审批消息";
				String msg="您申请的  "+work.getTaskName()+"任务编号为：("+work.getTaskCode()+")于"+sdf.format(new Date())+"审批通过,请及时关注.";
				privateInforMng.setMsg(title, msg, bean.getUserId(), "2");
			}else {
				//首先修改自己的待办信息，将待办改为已办，将审批的url改为查看的url
				PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
				//建立下一环节处理人的待办信息
				checkUrl=checkUrl+work.getTaskId();//审批url
				lookUrl=lookUrl+work.getTaskId(); //查看url
				//得到下一节点处理人
				User nextUser=userMng.findById(nextNode.getUserId());
				//新增下一节点处理人的待办信息
				personalWorkMng.addPersonalWork(work,nextUser.getId(), checkUrl, lookUrl);
				//设置下节点处理人姓名和编号		
				bean.setNextCheckUserName(nextUser.getName());
				bean.setNextCheckUserId(nextUser.getId());
				bean.setNextCheckKey(String.valueOf(nextNode.getKeyId()));
			}
		}else if("0".equals(checkBean.getFcheckResult())) {	//不通过
			//如果被打回原点，设置审批状态为已退回，设置申请状态为暂存
			bean.setCheckStauts("-1");//已退回
			bean.setStauts("0");//暂存
			bean.setNextCheckKey("");//设置到初始节点
			bean.setNextCheckUserId("");
			bean.setNextCheckUserName("");
			//修改自己的待办信息，将待办改为已办,任务类型 改为查看
			personalWorkMng.updateStautsAndType(bean.getBeanCode(), user.getId(), "2", "2");
			//修改申请人的待办信息（已办到待办）
			PersonalWork work=personalWorkMng.updateStautsAndType(bean.getBeanCode(), bean.getUserId(), "0", "3");
			String title=work.getTaskName()+"审批消息";
			String msg="您申请的  "+work.getTaskName()+"任务编号：("+work.getTaskCode()+")于"+sdf.format(work.getFinishTime())+"被退回原点,请及时关注.";
			privateInforMng.setMsg(title, msg, bean.getUserId(), "3");
		}
		//添加流程审批记录
		addCheckHistory(checkBean, 0, bean.getBeanCode(), bean.getUserId(), overFlag, user, node, files);
		personalWorkMng.sendMessageToUser(user.getId(), 0);
		return bean;
	}

	@Override
	public Boolean findbyCode(String code, String status) {
		Finder finder = Finder.create(" FROM  TProcessCheck WHERE foCode='"+code+"' and stauts='"+status+"' ");
		finder.append(" order by FpcId desc");
		List<TProcessCheck> list = super.find(finder);
		if(list.size() > 0) {	//有审批记录了返回true
			return true;
		}else {	//无则返回false
			return false;
		}
	}

	@Override
	public Boolean findbyCodeES(String code, String status, Integer FPId) {
		Finder finder = Finder.create(" FROM  TProcessCheck WHERE foCode='"+code+"' and stauts='"+status+"' and FPId="+FPId);
		finder.append(" order by FpcId desc");
		List<TProcessCheck> list = super.find(finder);
		if(list.size() > 0) {	//有审批记录了返回true
			return true;
		}else {	//无则返回false
			return false;
		}
	}
	
	/**
	 * 因为目前流程配置只说了报销申请
	 * 先强制返回BXSQ
	 */
	@Override
	public String JudgmentProcessOff(String type) {
		if("1".equals(type)){
			//通用事项报销
			return "BXSQ";
		}else if("2".equals(type)){
			//查询会议信息
			return "HYBX";
		} else if ("3".equals(type)) {
			//查询培训信息
			return "PXBX";
		}else if("4".equals(type)){
			//查询差旅信息
			return "CLBX";
		}else if("5".equals(type)){
			//查询接待信息
			return "GWJDBX";
		}else if("6".equals(type)){
			//查询公务用车信息
			return "GWYCBX";
		}else if("7".equals(type)){
			//查询公务出国信息
			return "GWCGBX";
		}else if("8".equals(type)){
			//合同报销
			return "HTFKSQ";
		}else if("9".equals(type)){
			//往来款报销
			return "WLKBX";
		}else if("11".equals(type)){
			//合同报销
			return "BZJBX";
		}
		return "BXSQ";
	}

	@Override
	public List<TProcessCheck> getTrueResult(List<TProcessCheck> checkList) {
		
		List<TProcessCheck> listTProcessChecks = new ArrayList<TProcessCheck>();
		for (TProcessCheck tProcessCheck : checkList) {
			if("0".equals(tProcessCheck.getFcheckResult())){
				break;
			}else{
				listTProcessChecks.add(tProcessCheck);
			}
		}
		return checkList;
	}
}
	
	


