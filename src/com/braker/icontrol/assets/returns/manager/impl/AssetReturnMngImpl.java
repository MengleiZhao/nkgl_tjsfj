package com.braker.icontrol.assets.returns.manager.impl;

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
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.LookupsUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.assets.returns.manager.AssetReturnListMng;
import com.braker.icontrol.assets.returns.manager.AssetReturnMng;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.AssetFlowMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;

@Service
@Transactional
public class AssetReturnMngImpl extends BaseManagerImpl<AssetReturn> implements AssetReturnMng {
	
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
	private AssetReturnListMng assetReturnListMng;
	
	@Autowired
	private PrivateInforMng privateInforMng;
	
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	
	@Autowired
	private AssetFlowMng assetFlowMng;
	
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;

	@Override
	public Pagination getAssetReturnList(AssetReturn assetReturn, User user, Integer page, Integer rows) {
		try {
			Finder finder =Finder.create(" FROM AssetReturn where fReturnStauts !='99' AND fAssType='"+assetReturn.getfAssType()+"'");
			finder.append(" AND fOperatorId = :fOperatorId");
			finder.setParam("fOperatorId", user.getId());
			//查询 按资产交回单
	 		if(!StringUtil.isEmpty(assetReturn.getfAssReturnCode())){
				finder.append(" AND fAssReturnCode =:fAssReturnCode");
				finder.setParam("fAssReturnCode", assetReturn.getfAssReturnCode());
			}
	 		//查询 按申请部门
	 		if(!StringUtil.isEmpty(assetReturn.getfDeptName())) {
	 			finder.append(" AND fDeptName LIKE :fDeptName");
				finder.setParam("fDeptName", "%"+assetReturn.getfDeptName()+"%");
	 		}
	 		//查询 按申请人
	 		if (!StringUtil.isEmpty(assetReturn.getfOperator())) {
	 			finder.append(" AND fOperator LIKE :fOperator");
				finder.setParam("fOperator", "%"+assetReturn.getfOperator()+"%");
	 		}
			finder.append(" ORDER BY updateTime desc");
			return super.find(finder, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void save(AssetReturn assetReturn, List<AssetReturnList> assetReturnList, User user) throws Exception {
		if (assetReturn.getfId_A() == null) {
			assetReturn.setCreateTime(new Date());
			assetReturn.setCreator(user.getAccountNo());
			assetReturn.setfDeptName(user.getDepartName());
			assetReturn.setfDeptId(user.getDpID());
			assetReturn.setfOperator(user.getName());
			assetReturn.setfOperatorId(user.getId());
			assetReturn.setfReturnStauts("1");
		}else{
			assetReturn.setUpdateTime(new Date());
			assetReturn.setUpdator(user.getAccountNo());
		}
		assetReturn.setfAcceptStauts("0");
		assetReturn = (AssetReturn) super.merge(assetReturn);
		//获得领用清单的资产名称集合
		List nameList = new ArrayList<>();
		for(int i = 0; i < assetReturnList.size(); i++){
			nameList.add(assetReturnList.get(i).getfAssName_AR());
		}
		//调用名称拼接方法
		String name = StringUtil.nameJoint(nameList, "、", 4);
		assetReturn.setfAssName(name);
		if(assetReturn.getfFlowStauts_A().equals("1")){
			assetReturn.setfReqTime(new Date());
			String str=null;
			if("ZCLX-01".equals(assetReturn.getfAssType())){
				//str="DZYHPLY";
			}else if("ZCLX-02".equals(assetReturn.getfAssType())){
				str="GDZCJH";
			}
			//得到第一个审批节点key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(),assetReturn.getJoinTable(),assetReturn.getBeanCodeField(),assetReturn.getBeanCode(), str, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(str, user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			assetReturn.setfNextUserName(nextUser.getName());
			assetReturn.setfNextUserCode(nextUser.getId());
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,assetReturn.getBeanCode());
			//设置下节点节点编码
			assetReturn.setfNextCode(firstKey+"");	
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(assetReturn.getfNextUserCode());//任务处理人ID既是下节点处理人ID
			work.setTaskId(assetReturn.getfId_A());//交回单ID
			work.setTaskCode(assetReturn.getfAssReturnCode());//资产交回单的单号
			if(assetReturn.getfAssType().equals("ZCLX-01")){
//				work.setTaskName("[领用申请]低值易耗品领用申请");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}else if(assetReturn.getfAssType().equals("ZCLX-02")){
				work.setTaskName("[资产交回]固定资产交回申请");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}
			work.setUrl("/assetReturn/approvalAssetReturn/"+assetReturn.getfId_A());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/assetReturn/detail/"+ assetReturn.getfId_A());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(new Date());//任务提交时间
			personalWorkMng.merge(work);
			//添加自己的已办信息
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(user.getId());//任务处理人ID既是下节点处理人ID
			minwork.setTaskId(assetReturn.getfId_A());//申请单ID
			minwork.setTaskCode(assetReturn.getfAssReturnCode());//资产交回单的单号
			if(assetReturn.getfAssType().equals("ZCLX-01")){
//				minwork.setTaskName("[领用申请]低值易耗品领用申请");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}else if(assetReturn.getfAssType().equals("ZCLX-02")){
				minwork.setTaskName("[资产交回]固定资产交回申请");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			}
			minwork.setUrl("/assetReturn/edit/"+assetReturn.getfId_A());//为审批页面内容显示的url,需要将数据传入不然无法访问
			minwork.setUrl1("/assetReturn/detail/"+ assetReturn.getfId_A());//查看url
			minwork.setUrl2("/assetReturn/delete/"+ assetReturn.getfId_A());//
			minwork.setTaskStauts("2");//已办
			minwork.setType("2");//任务类型：2-查看
			minwork.setTaskType("0");//任务归属（0发起人）
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(new Date());//任务提交时间
			minwork.setFinishTime(new Date());
			personalWorkMng.merge(minwork);
		}
		assetReturn=(AssetReturn) super.updateDefault(assetReturn);
		//保存交回清单
		assetReturnListMng.save(assetReturn, assetReturnList);
		
	}

	@Override
	public void delete(String id, User user) {
		AssetReturn assetReturn = findById(Integer.valueOf(id));
		assetReturn.setUpdateTime(new Date());
		assetReturn.setUpdator(user.getAccountNo());
		assetReturn.setfReturnStauts("99");
		super.saveOrUpdate(assetReturn);
		//删除工作台信息
		List<PersonalWork> workLost = personalWorkMng.findByCodeAndUser(assetReturn.getfAssReturnCode(), userMng.findById(assetReturn.getfOperatorId()));
		if(workLost != null && !workLost.isEmpty()){
			for (int j = 0; j < workLost.size(); j++) {
				personalWorkMng.deleteById(Integer.valueOf(workLost.get(j).getfId()));
			}
		}
	}

	@Override
	public void reCall(String id) {
		AssetReturn bean = super.findById(Integer.valueOf(id));
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="资产交回申请被撤回消息";
		String msg="您待审批的资产,任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean=(AssetReturn) reCall((CheckEntity) bean);
		this.updateDefault(bean);
	}

	@Override
	public AssetReturn findByCode(String code) {
		Finder finder =Finder.create(" FROM AssetReturn where fAssReturnCode='"+code+"'");
		return (AssetReturn) super.find(finder).get(0);
	}

	@Override
	public void approve(User user, AssetReturn assetReturn, TProcessCheck checkBean, String spjlFile,String getreturnList) throws Exception {
		assetReturn = this.findById(assetReturn.getfId_A());
		CheckEntity entity = (CheckEntity)assetReturn;
		String checkUrl = "/assetReturn/approvalAssetReturn/";
		String lookUrl = "/assetReturn/detail/";
		String str = null;
		if("ZCLX-01".equals(assetReturn.getfAssType())){
//			str="DZYHPLY";
		}else if("ZCLX-02".equals(assetReturn.getfAssType())){
			str="GDZCJH";
		}
		
		
		
		assetReturn = (AssetReturn)tProcessCheckMng.checkProcess(checkBean,entity,user,str,checkUrl,lookUrl,spjlFile);
		if("9".equals(assetReturn.getfFlowStauts_A())){//审批通过
			
			//List<AssetReturnList> returnList = JSON.parseObject(getreturnList,new TypeReference<List<AssetReturnList>>(){});
			
			
			
			
			List<AssetReturnList> assetReturnList = JSON.parseObject(getreturnList,new TypeReference<List<AssetReturnList>>(){});
			for (AssetReturnList arl : assetReturnList) {
				AssetBasicInfo assetBasicInfo = assetBasicInfoMng.findbyCode(arl.getfAssCode_AR());
				AssetReturnList arlbean = assetReturnListMng.findById(arl.getfListId());
				Lookups fUsedStauts=new Lookups();
				if("ZCKYZT-02".equals(arl.getfAvailableStauts_code())){//如果等于不可用
					fUsedStauts=LookupsUtil.findByLookCode("ZCSYZT-04");//待处置
				}else {
					fUsedStauts=LookupsUtil.findByLookCode("ZCSYZT-02");//闲置
				}
				Lookups fAvailableStauts=LookupsUtil.findByLookCode(arl.getfAvailableStauts_code());
				assetBasicInfo.setfAvailableStauts(fAvailableStauts);
				assetBasicInfo.setfUsedStauts(fUsedStauts);
				assetBasicInfo.setUpdateTime(new Date());
				assetBasicInfo.setUpdator(user.getName());
				assetBasicInfo.setfUseNameID(null);
				assetBasicInfo.setfUseName(null);
				assetBasicInfo.setfUseDeptID(null);
				assetBasicInfo.setfUseDept(null);
				assetBasicInfoMng.merge(assetBasicInfo);
				arlbean.setfAvailableStauts(fAvailableStauts);
				assetReturnListMng.merge(arlbean);
				
				//添加操作流水
				Lookups fOptType=LookupsUtil.findByLookCode("ZCLSCZLX-06");
				Lookups fAssType=LookupsUtil.findByLookCode(assetReturn.getfAssType());
				User assetReturnUser = userMng.findById(assetReturn.getfOperatorId());
				assetFlowMng.addFlow(assetReturnUser, fOptType, assetReturn.getBeanCode(), arl.getfAssCode_AR(), arl.getfAssName_AR(),fAssType, null, 1);
				
				
				
			}
			//保存审签信息
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCJH", assetReturn.getfDeptId());
			List<TProcessCheck> checkList = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),assetReturn.getBeanCode());
			//审签信息
			TProcessPrintDetail listTProcessCheck = null;//tProcessPrintDetailMng.getsSWGLG(checkList);
			listTProcessCheck.setFTabName("AssetReturn");
			listTProcessCheck.setFTabId(assetReturn.getfId_A());
			listTProcessCheck.setFTabIdName("fId_A");
			
			//tProcessPrintDetailMng.merge(listTProcessCheck);
		}
	}

	@Override
	public void accept(String id, User user) {
		AssetReturn assetReturn = findById(Integer.valueOf(id));
		assetReturn.setUpdateTime(new Date());
		assetReturn.setUpdator(user.getAccountNo());
		assetReturn.setfAcceptStauts("1");
		super.saveOrUpdate(assetReturn);
	}

	@Override
	public Pagination getApprovalAssetReturn(AssetReturn assetReturn,
			User user, Integer page, Integer rows) {
		Finder finder =Finder.create(" FROM AssetReturn where fReturnStauts !='99' AND fFlowStauts_A=1 AND fNextUserCode ='"+user.getId()+"' AND fAssType='"+assetReturn.getfAssType()+"'");
		//查询 按资产交回单
 		if(!StringUtil.isEmpty(assetReturn.getfAssReturnCode())){
			finder.append(" AND fAssReturnCode =:fAssReturnCode");
			finder.setParam("fAssReturnCode", assetReturn.getfAssReturnCode());
		}
 		//查询 按申请部门
 		if(!StringUtil.isEmpty(assetReturn.getfDeptName())) {
 			finder.append(" AND fDeptName LIKE :fDeptName");
			finder.setParam("fDeptName", "%"+assetReturn.getfDeptName()+"%");
 		}
 		//查询 按申请人
 		if (!StringUtil.isEmpty(assetReturn.getfOperator())) {
 			finder.append(" AND fOperator LIKE :fOperator");
			finder.setParam("fOperator", "%"+assetReturn.getfOperator()+"%");
 		}
		finder.append(" ORDER BY updateTime desc");
		return super.find(finder, page, rows);
	}

	@Override
	public Pagination getacceptAssetReturn(AssetReturn assetReturn, User user,
			Integer page, Integer rows) {
		Finder finder =Finder.create(" FROM AssetReturn where fReturnStauts !='99' AND fFlowStauts_A=9 AND fAssType='"+assetReturn.getfAssType()+"'");
		//查询 按资产交回单
 		if(!StringUtil.isEmpty(assetReturn.getfAssReturnCode())){
			finder.append(" AND fAssReturnCode =:fAssReturnCode");
			finder.setParam("fAssReturnCode", assetReturn.getfAssReturnCode());
		}
 		//查询 按申请部门
 		if(!StringUtil.isEmpty(assetReturn.getfDeptName())) {
 			finder.append(" AND fDeptName LIKE :fDeptName");
			finder.setParam("fDeptName", "%"+assetReturn.getfDeptName()+"%");
 		}
 		//查询 按申请人
 		if (!StringUtil.isEmpty(assetReturn.getfOperator())) {
 			finder.append(" AND fOperator LIKE :fOperator");
			finder.setParam("fOperator", "%"+assetReturn.getfOperator()+"%");
 		}
		finder.append(" ORDER BY updateTime desc");
		return super.find(finder, page, rows);
	}

}
