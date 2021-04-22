package com.braker.icontrol.assets.handle.manager.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.poi.xwpf.converter.core.utils.StringUtils;
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
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.AssetType;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.assets.allcoa.model.Alloca;
import com.braker.icontrol.assets.export.manager.ExportHandleMng;
import com.braker.icontrol.assets.handle.manager.AssetFixedRegMng;
import com.braker.icontrol.assets.handle.manager.AssetLowRegMng;
import com.braker.icontrol.assets.handle.manager.HandleMng;
import com.braker.icontrol.assets.handle.model.AssetLowRegistration;
import com.braker.icontrol.assets.handle.model.AssetRegistration;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.AssetFlowMng;
import com.braker.icontrol.assets.storage.manager.AssetStockMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.icontrol.assets.storage.model.Regist;
import com.braker.icontrol.assets.storage.model.Storage;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class HandleMngImpl extends BaseManagerImpl<Handle> implements HandleMng{

	@Autowired
	private AssetStockMng assetStockMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private AssetFlowMng assetFlowMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private AssetLowRegMng assetLowRegMng;
	@Autowired
	private AssetFixedRegMng assetFixedRegMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private HandleMng handleMng;
	@Autowired
	private ExportHandleMng exportHandleMng;
	@Autowired
	private FormulationMng formulationMng;
	
	@Override
	public Pagination applicationList(Handle handle, User user, Integer pageNo,Integer pageSize) {
		Finder finder=Finder.create(" FROM Handle WHERE fHandleStauts !='99' AND fFlowStauts IN(0,1,9,-1,-4)");
		if(!StringUtil.isEmpty(handle.getfAssHandleCode())){
			finder.append(" AND fAssHandleCode LIKE :fAssHandleCode");
			finder.setParam("fAssHandleCode", "%"+handle.getfAssHandleCode()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfRecUser())){
			finder.append(" AND fReqUser LIKE :fReqUser");
			finder.setParam("fReqUser", "%"+handle.getfRecUser()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfRecDept())){
			finder.append(" AND fRecDept LIKE :fRecDept");
			finder.setParam("fRecDept", "%"+handle.getfRecDept()+"%");
		}
		if(handle.getfReqTime()!=null){
			finder.append("AND DATEDIFF(fReqTime,'"+handle.getfReqTime()+"')=0");
		}
		if(!StringUtil.isEmpty(handle.getfAssType())){
			finder.append(" AND fAssType = :fAssType");
			finder.setParam("fAssType", handle.getfAssType());
		}
		finder.append(" AND fRecUserId ='"+user.getId()+"'");		
		finder.append(" AND fRecDeptId ='"+user.getDpID()+"'");
		finder.append(" order by updateTime desc");		
		return super.find(finder, pageNo, pageSize);
	}
	
	
	@Override
	public Pagination disposeAcceptJson(Handle handle, User user, Integer pageNo,Integer pageSize) {
		Finder finder=Finder.create(" FROM Handle WHERE fHandleStauts !='99' AND fFlowStauts ='9' and fRepStauts ='1'");
		if(!StringUtil.isEmpty(handle.getfAssHandleCode())){
			finder.append(" AND fAssHandleCode LIKE :fAssHandleCode");
			finder.setParam("fAssHandleCode", "%"+handle.getfAssHandleCode()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfAssType())){
			finder.append(" AND fAssType = :fAssType");
			finder.setParam("fAssType", handle.getfAssType());
		}
		finder.append(" order by updateTime desc");		
		return super.find(finder, pageNo, pageSize);
	}

	
	@Override
	public List<Lookups> getLookupsJson(String categoryCode,String blanked) {
		Finder hql=Finder.create("from Lookups Where flag='1' ");
		hql.append("  and category.code =:pcode ").setParam("pcode", categoryCode);
		if(!StringUtil.isEmpty(blanked)){
			hql.append(" and code<>:code").setParam("code", blanked);
		}
		hql.append(" order by convert(orderNo,DECIMAL)");
		return super.find(hql);
	}


	@Override
	public void save(String planJson, Handle handle, User user,String LowHandleFlies)  throws Exception{
		//保存更新资产处置信息
		if(StringUtil.isEmpty(String.valueOf(handle.getfId()))){
			handle.setCreateTime(new Date());
			handle.setCreator(user.getAccountNo());
			handle.setfReqTime(new Date());
			handle.setfHandleStauts("1");
			handle.setfRecDept(user.getDepartName());
			handle.setfRecUser(user.getName());
			handle.setfRecUserId(user.getId());
		}else {
			handle.setUpdateTime(new Date());
			handle.setUpdator(user.getAccountNo());
		}
		//资产类型
		handle.setfAssType(handle.getfAssType());
		handle.setfRecDeptId(user.getDpID());
		handle=(Handle) super.saveOrUpdate(handle);
		//删除数据库中   申请中
		getSession().createSQLQuery("delete from T_ASSET_REGISTRATION where F_ID ="+handle.getfId()).executeUpdate();
		List<AssetRegistration> regList = null;
		if(handle.getfAssType().equals("ZCLX-03")){
			//低值易耗品
			regList = JSON.parseObject(planJson,new TypeReference<List<AssetRegistration>>(){});
		}else if(handle.getfAssType().equals("ZCLX-02")){
			//固定资产
			regList = JSON.parseObject(planJson,new TypeReference<List<AssetRegistration>>(){});
		}
		//资产名称集合
		List nameList = new ArrayList<>();
		for(int i=0; i<regList.size(); i++){
			nameList.add(regList.get(i).getfAssName());
		}
		String name = StringUtil.nameJoint(nameList, "、", 4);
		if(handle.getfFlowStauts().equals("1")){
			String str = null;
			if("ZCLX-03".equals(handle.getfAssType())){
				str="WXZCCZ";
			}else if("ZCLX-02".equals(handle.getfAssType())){
				str="GDZCCZ";
			}
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),handle.getJoinTable(),handle.getBeanCodeField(),handle.getBeanCode(), str, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(str, user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			handle.setfNextUserName(nextUser.getName());
			handle.setfNextUserCode(nextUser.getId());
			//设置下节点节点编码
			handle.setfNextCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,handle.getBeanCode());
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(handle.getfNextUserCode());//任务处理人ID既是下节点处理人ID
			work.setTaskId(handle.getfId());//申请单ID
			work.setTaskCode(handle.getfAssHandleCode());//为申请单的单号
			work.setTaskName("[处置申请]"+name);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/Handle/approvalHandle/"+handle.getfId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/Handle/approvalDetail/"+ handle.getfId());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(new Date());//任务提交时间
			personalWorkMng.merge(work);
			//添加已办信息
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(handle.getfRecUserId());//任务处理人ID当前申请人
			minwork.setTaskId(handle.getfId());//申请单ID
			minwork.setTaskCode(handle.getfAssHandleCode());//为申请单的单号
			minwork.setTaskName("[处置申请]"+name);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			minwork.setUrl("/Handle/edit/"+handle.getfId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			minwork.setUrl1("/Handle/approvalDetail/"+ handle.getfId());//查看url
			minwork.setUrl2("/Handle/delete/"+ handle.getfId());//查看url
			minwork.setTaskStauts("2");//待办
			minwork.setType("2");//任务类型：2-查看
			minwork.setTaskType("0");//任务归属（0发起人）
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(new Date());//任务提交时间
			minwork.setFinishTime(new Date());
			personalWorkMng.merge(minwork);
		}
		handle.setfAssName(name);
		handle=(Handle) super.saveOrUpdate(handle);
		//保存附件
		attachmentMng.joinEntity(handle, LowHandleFlies);
		//保存处置清单
		if("ZCLX-03".equals(handle.getfAssType())){
			for (int i = 0; i < regList.size(); i++) {
				regList.get(i).setfId(handle.getfId());
				regList.get(i).setfAssHandleRegCode(handle.getfAssHandleCode());
				super.merge(regList.get(i));
			}
		}else if("ZCLX-02".equals(handle.getfAssType())){
			for (int i = 0; i < regList.size(); i++) {
				regList.get(i).setfId(handle.getfId());
				regList.get(i).setfAssHandleRegCode(handle.getfAssHandleCode());
				super.merge(regList.get(i));
			}
		}
		
	}


	@Override
	public Pagination approvalList(Handle handle, User user, Integer pageNo,Integer pageSize) {
		Finder finder=Finder.create(" FROM Handle WHERE fHandleStauts !='99' AND fFlowStauts =1");
		if(!StringUtil.isEmpty(handle.getfAssHandleCode())){
			finder.append(" AND fAssHandleCode LIKE :fAssHandleCode");
			finder.setParam("fAssHandleCode", "%"+handle.getfAssHandleCode()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfRecUser())){
			finder.append(" AND fReqUser LIKE :fReqUser");
			finder.setParam("fReqUser", "%"+handle.getfRecUser()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfRecDept())){
			finder.append(" AND fRecDept LIKE :fRecDept");
			finder.setParam("fRecDept", "%"+handle.getfRecDept()+"%");
		}
		if(handle.getfReqTime()!=null){
			finder.append("AND DATEDIFF(fReqTime,'"+handle.getfReqTime()+"')=0");
		}
		if(!StringUtil.isEmpty(handle.getfAssType())){
			finder.append(" AND fAssType = :fAssType");
			finder.setParam("fAssType", handle.getfAssType());
		}
		finder.append(" AND fNextUserCode ='"+user.getId()+"'");		
		finder.append(" order by updateTime desc");	
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public void updateStauts(String stauts, Handle handle, TProcessCheck checkBean,User user,String files,String files01,String files02,String files03,String files04,String planJson)  throws Exception{
		//保存党组会议号
		String fMeetNumber = null;
		if(!StringUtil.isEmpty(handle.getfMeetNumber())) {//采购方式已由某级审批人输入
			fMeetNumber = handle.getfMeetNumber();
			handle = this.findById(handle.getfId());
			handle.setfMeetNumber(fMeetNumber);
		}else {
			handle = this.findById(handle.getfId());
		}
		CheckEntity entity=(CheckEntity)handle;
		String checkUrl="/Handle/approvalHandle/";
		String lookUrl ="/Handle/detail/";
		String str = null;
		if("ZCLX-03".equals(handle.getfAssType())){
			str="WXZCCZ";
		}else if("ZCLX-02".equals(handle.getfAssType())){
			str="GDZCCZ";
		}
		
		handle=(Handle)tProcessCheckMng.checkProcess(checkBean,entity,user,str,checkUrl,lookUrl,files);
		//在这里判断是不是资产管理岗，获取处置清单上的价值信息
		if(user.getRoleName().contains("资产管理岗")){
			List<AssetRegistration> regList = JSON.parseObject(planJson,new TypeReference<List<AssetRegistration>>(){});
			List<AssetRegistration> li = handleMng.inAndFixedHandle(handle.getEntryId(), handle.getfAssType());
			for (int j = 0; j < li.size(); j++) {
				for (int i = 0; i < regList.size(); i++) {
					if(String.valueOf(li.get(j).getfARId()).equals(String.valueOf(regList.get(i).getfARId()))){
						li.get(j).setfResidualValue(regList.get(i).getfResidualValue());
						li.get(j).setfDepreciationAmount(regList.get(i).getfDepreciationAmount());
						li.get(j).setfAssAmount(regList.get(i).getfAssAmount());
						li.get(j).setfRemarkR(regList.get(i).getfRemarkR());
						assetFixedRegMng.saveOrUpdate(li.get(j));
					}
				}
			}
		}
		//只有党建办公室会议号录入岗才可以填写党组会会议号
		if(!"".equals(handle.getNextCheckUserId())){
			User userNext= userMng.findById(handle.getNextCheckUserId());
			if(userNext.getRoleName().contains("会议号录入岗") && "党建办公室".equals(userNext.getDepart().getName())){
				exportHandleMng.arrangeCheckDetailHandle(handle, handle.getfAssType(), user);
			}
		}
		
		if("9".equals(handle.getfFlowStauts())){
			handle.setfRepStauts("0");
		}
		super.updateDefault(handle);
	}


	@Override
	public Pagination handleRegList(Handle handle, User user,Integer pageNo, Integer pageSize) {
		Finder finder=Finder.create(" FROM Handle WHERE fHandleStauts !='99'  AND fFlowStauts=9");
	/*	if(!StringUtil.isEmpty(handle.getfAssHandleCode())){
			finder.append(" AND fAssHandleCode LIKE :fAssHandleCode");
			finder.setParam("fAssHandleCode", "%"+handle.getfAssHandleCode()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfAssName())){
			finder.append(" AND fAssName LIKE :fAssName");
			finder.setParam("fAssName", "%"+handle.getfAssName()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfHandleKind())){
			finder.append(" AND fHandleKind LIKE :fHandleKind");
			finder.setParam("fHandleKind", "%"+handle.getfHandleKind()+"%");
		}*/
		finder.append(" order by updateTime desc");		
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public void delete(String id, User user) {
		Handle handle = findById(Integer.valueOf(id));
		handle.setfHandleStauts("99");
		handle.setUpdateTime(new Date());
		handle.setUpdator(user.getAccountNo());
		super.merge(handle);
		//删除工作台信息
		List<PersonalWork> workLost = personalWorkMng.findByCodeAndUser(handle.getfAssHandleCode(),  userMng.findById(handle.getfRecUserId()));
		if(workLost.size()>0){
			for (int i = 0; i < workLost.size(); i++) {
				personalWorkMng.deleteById(workLost.get(i).getfId());
			}
		}
		
	}


	@Override
	public Handle findbyCode(String code) {
		Finder finder =Finder.create(" from Handle where fAssHandleCode='"+code+"'");
		return (Handle) super.find(finder).get(0);
	}


	@Override
	public List<AssetRegistration> inAndFixedHandle(String fId, String fAssType) {
		List<AssetRegistration> fixedList = new ArrayList<AssetRegistration>();
		if("".equals(fId) || "null".equals(fId)){
			fixedList = new ArrayList<AssetRegistration>();
		}else{
			fixedList = assetFixedRegMng.findbyfId(Integer.valueOf(fId));
			
		}
		//固定资产
		return fixedList;
	}


	@Override
	public Pagination ledgerPagination(Handle handle, User user,
			Integer pageNo, Integer pageSize) {
		Finder finder=Finder.create(" FROM Handle WHERE fHandleStauts !='99' AND fFlowStauts IN(9)");
		if(!StringUtil.isEmpty(handle.getfAssHandleCode())){
			finder.append(" AND fAssHandleCode LIKE :fAssHandleCode");
			finder.setParam("fAssHandleCode", "%"+handle.getfAssHandleCode()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfRecUser())){
			finder.append(" AND fReqUser LIKE :fReqUser");
			finder.setParam("fReqUser", "%"+handle.getfRecUser()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfRecDept())){
			finder.append(" AND fRecDept LIKE :fRecDept");
			finder.setParam("fRecDept", "%"+handle.getfRecDept()+"%");
		}
		if(handle.getfReqTime()!=null){
			finder.append("AND DATEDIFF(fReqTime,'"+handle.getfReqTime()+"')=0");
		}
		if(!StringUtil.isEmpty(handle.getfAssType())){
			finder.append(" AND fAssType = :fAssType");
			finder.setParam("fAssType", handle.getfAssType());
		}
		finder.append(" order by updateTime desc");		
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public String reCall(String id) {
		
		Handle bean = super.findById(Integer.valueOf(id));
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="资产处置申请被撤回消息";
		String msg="您待审批处置任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean=(Handle) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "操作成功";
	}
	
	
	@Override
	public List<AssetRegistration> getbyAssetid(String assetid) {
		List<AssetRegistration> registrations = new ArrayList<AssetRegistration>();
		String str = new String();
		assetid = assetid.substring(1, assetid.length()-1);
		if(!StringUtil.isEmpty(assetid)){
			String[] arrid = assetid.split(",");
			for (int i = 0; i < arrid.length; i++) {
				str = str + arrid[i].substring(1, arrid[i].length()-1)+",";
			}
			str = str.substring(0, str.length()-1);
		}
		Finder finder = Finder.create("FROM AssetBasicInfo WHERE fAssId in("+str+")");
		List<AssetBasicInfo> abiList = super.find(finder);
		for (int i = 0; i < abiList.size(); i++) {
			AssetBasicInfo abi = abiList.get(i);
			AssetRegistration assetRegistration = new AssetRegistration();
			assetRegistration.setfAssId(abi.getfAssId());
			if(!StringUtil.isEmpty(abi.getFcCode())){
				ContractBasicInfo cont = formulationMng.findByProperty("fcCode", abi.getFcCode()).get(0);
				assetRegistration.setContractId(cont.getFcId());
			}
			assetRegistration.setfAssCode(abi.getfAssCode());
			assetRegistration.setfAssName(abi.getfAssName());
			assetRegistration.setfForm(abi.getfForm());
			assetRegistration.setfFormShow(abi.getfFormShow());
			assetRegistration.setfFixedType(abi.getfFixedType());
			assetRegistration.setfFixedTypeId(abi.getfFixedTypeId());
			assetRegistration.setfFixedTypeCode(abi.getfFixedTypeCode());
			assetRegistration.setfAssModel(abi.getfSPModel());
			assetRegistration.setfMeasUnit(abi.getFmModel());
			assetRegistration.setfHandleNum("1");
			assetRegistration.setfActionDate(abi.getfActionDate());
			assetRegistration.setfUsedStauts(abi.getfUsedStauts().getCode());
			assetRegistration.setfUsedStautsShow(abi.getfUsedStauts().getName());
			assetRegistration.setfOldAmount(abi.getfAmount());
			assetRegistration.setfResidualValue(null);
			assetRegistration.setfDepreciationAmount(null);
			assetRegistration.setfAssAmount(null);
			assetRegistration.setfRemarkR("");
			
			registrations.add(assetRegistration);
		}
		return registrations;
	}
	
	@Override
	public String updateDisposeAccept(String id) {
		Handle bean = super.findById(Integer.valueOf(id));
		
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="资产处置受理消息";
		String msg="您处置任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"受理完成,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "2");
		//撤回
		bean.setfAcceptStauts("1");
		this.updateDefault(bean);
		
		List<AssetRegistration> li = handleMng.inAndFixedHandle(bean.getEntryId(), bean.getfAssType());
		for (int i = 0; i < li.size(); i++) {
			AssetBasicInfo assetBasicInfo = assetBasicInfoMng.findById(li.get(i).getfAssId());
			Lookups zckyzt = lookupsMng.findByLookCode("ZCKYZT-02");
			assetBasicInfo.setfAvailableStauts(zckyzt);
			Lookups zcsyzt = lookupsMng.findByLookCode("ZCSYZT-03");
			assetBasicInfo.setfUsedStauts(zcsyzt);
			assetBasicInfoMng.saveOrUpdate(assetBasicInfo);
			
			//添加操作流水
			Lookups fOptType=LookupsUtil.findByLookCode("ZCLSCZLX-04");
			Lookups fAssType=LookupsUtil.findByLookCode(bean.getfAssType());
			User handleUser = userMng.findById(bean.getfRecUserId());
			assetFlowMng.addFlow(handleUser, fOptType, bean.getBeanCode(), li.get(i).getfAssCode(), li.get(i).getfAssName(),fAssType, li.get(i).getfMeasUnit(), 1);
			//保存审签记录
			//exportHandleMng.arrangeCheckDetailHandle(handle, handle.getfAssType(), user);
		}
		
		
		return "操作成功";
	}
	
	@Override
	public Pagination replenishList(Handle handle, User user, Integer pageNo,Integer pageSize) {
		Finder finder=Finder.create(" FROM Handle WHERE fHandleStauts !='99' AND fFlowStauts ='9' and fRepStauts in('0','1')");
		if(!StringUtil.isEmpty(handle.getfAssHandleCode())){
			finder.append(" AND fAssHandleCode LIKE :fAssHandleCode");
			finder.setParam("fAssHandleCode", "%"+handle.getfAssHandleCode()+"%");
		}
		if(!StringUtil.isEmpty(handle.getfRecDept())){
			finder.append(" AND fRecDept LIKE :fRecDept");
			finder.setParam("fRecDept", "%"+handle.getfRecDept()+"%");
		}
		if(handle.getfReqTime()!=null){
			finder.append("AND DATEDIFF(fReqTime,'"+handle.getfReqTime()+"')=0");
		}
		if(!StringUtil.isEmpty(handle.getfAssType())){
			finder.append(" AND fAssType = :fAssType");
			finder.setParam("fAssType", handle.getfAssType());
		}
		finder.append(" order by updateTime desc");		
		return super.find(finder, pageNo, pageSize);
	}
	
}
