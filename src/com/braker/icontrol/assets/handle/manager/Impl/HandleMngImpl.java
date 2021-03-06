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
		//??????????????????????????????
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
		//????????????
		handle.setfAssType(handle.getfAssType());
		handle.setfRecDeptId(user.getDpID());
		handle=(Handle) super.saveOrUpdate(handle);
		//??????????????????   ?????????
		getSession().createSQLQuery("delete from T_ASSET_REGISTRATION where F_ID ="+handle.getfId()).executeUpdate();
		List<AssetRegistration> regList = null;
		if(handle.getfAssType().equals("ZCLX-03")){
			//???????????????
			regList = JSON.parseObject(planJson,new TypeReference<List<AssetRegistration>>(){});
		}else if(handle.getfAssType().equals("ZCLX-02")){
			//????????????
			regList = JSON.parseObject(planJson,new TypeReference<List<AssetRegistration>>(){});
		}
		//??????????????????
		List nameList = new ArrayList<>();
		for(int i=0; i<regList.size(); i++){
			nameList.add(regList.get(i).getfAssName());
		}
		String name = StringUtil.nameJoint(nameList, "???", 4);
		if(handle.getfFlowStauts().equals("1")){
			String str = null;
			if("ZCLX-03".equals(handle.getfAssType())){
				str="WXZCCZ";
			}else if("ZCLX-02".equals(handle.getfAssType())){
				str="GDZCCZ";
			}
			//???????????????????????????key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),handle.getJoinTable(),handle.getBeanCodeField(),handle.getBeanCode(), str, user);
			//?????????????????????????????????????????????????????????????????????
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(str, user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//???????????????????????????????????????		get(0)??????????????????????????????????????????????????????????????????li????????????????????????
			handle.setfNextUserName(nextUser.getName());
			handle.setfNextUserCode(nextUser.getId());
			//???????????????????????????
			handle.setfNextCode(firstKey+"");	
			//????????????????????????????????????1?????????????????????
			tProcessCheckMng.updateStauts(flowId,handle.getBeanCode());
			
			//???????????????????????????????????????
			PersonalWork work = new PersonalWork();
			work.setUserId(handle.getfNextUserCode());//???????????????ID????????????????????????ID
			work.setTaskId(handle.getfId());//?????????ID
			work.setTaskCode(handle.getfAssHandleCode());//?????????????????????
			work.setTaskName("[????????????]"+name);//????????????????????????????????????+?????????????????????????????????????????????
			work.setUrl("/Handle/approvalHandle/"+handle.getfId());//??????????????????????????????url,???????????????????????????????????????
			work.setUrl1("/Handle/approvalDetail/"+ handle.getfId());//??????url
			work.setTaskStauts("0");//??????
			work.setType("1");//???????????????1-??????
			work.setTaskType("1");//???????????????1????????????
			work.setBeforeUser(user.getName());//?????????????????????
			work.setBeforeDepart(user.getDepartName());//?????????????????????????????????
			work.setBeforeTime(new Date());//??????????????????
			personalWorkMng.merge(work);
			//??????????????????
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(handle.getfRecUserId());//???????????????ID???????????????
			minwork.setTaskId(handle.getfId());//?????????ID
			minwork.setTaskCode(handle.getfAssHandleCode());//?????????????????????
			minwork.setTaskName("[????????????]"+name);//????????????????????????????????????+?????????????????????????????????????????????
			minwork.setUrl("/Handle/edit/"+handle.getfId());//??????????????????????????????url,???????????????????????????????????????
			minwork.setUrl1("/Handle/approvalDetail/"+ handle.getfId());//??????url
			minwork.setUrl2("/Handle/delete/"+ handle.getfId());//??????url
			minwork.setTaskStauts("2");//??????
			minwork.setType("2");//???????????????2-??????
			minwork.setTaskType("0");//???????????????0????????????
			minwork.setBeforeUser(user.getName());//?????????????????????
			minwork.setBeforeDepart(user.getDepartName());//?????????????????????????????????
			minwork.setBeforeTime(new Date());//??????????????????
			minwork.setFinishTime(new Date());
			personalWorkMng.merge(minwork);
		}
		handle.setfAssName(name);
		handle=(Handle) super.saveOrUpdate(handle);
		//????????????
		attachmentMng.joinEntity(handle, LowHandleFlies);
		//??????????????????
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
		//?????????????????????
		String fMeetNumber = null;
		if(!StringUtil.isEmpty(handle.getfMeetNumber())) {//???????????????????????????????????????
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
		//??????????????????????????????????????????????????????????????????????????????
		if(user.getRoleName().contains("???????????????")){
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
		//????????????????????????????????????????????????????????????????????????
		if(!"".equals(handle.getNextCheckUserId())){
			User userNext= userMng.findById(handle.getNextCheckUserId());
			if(userNext.getRoleName().contains("??????????????????") && "???????????????".equals(userNext.getDepart().getName())){
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
		//?????????????????????
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
		//????????????
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
		//????????????
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//???????????????????????????
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="?????????????????????????????????";
		String msg="?????????????????????????????????("+bean.getBeanCode()+")???"+sdf.format(new Date())+"??????????????????,???????????????.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//??????
		bean=(Handle) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "????????????";
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
		
		//???????????????????????????
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="????????????????????????";
		String msg="????????????????????????("+bean.getBeanCode()+")???"+sdf.format(new Date())+"????????????,???????????????.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "2");
		//??????
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
			
			//??????????????????
			Lookups fOptType=LookupsUtil.findByLookCode("ZCLSCZLX-04");
			Lookups fAssType=LookupsUtil.findByLookCode(bean.getfAssType());
			User handleUser = userMng.findById(bean.getfRecUserId());
			assetFlowMng.addFlow(handleUser, fOptType, bean.getBeanCode(), li.get(i).getfAssCode(), li.get(i).getfAssName(),fAssType, li.get(i).getfMeasUnit(), 1);
			//??????????????????
			//exportHandleMng.arrangeCheckDetailHandle(handle, handle.getfAssType(), user);
		}
		
		
		return "????????????";
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
