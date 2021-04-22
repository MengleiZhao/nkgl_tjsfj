package com.braker.icontrol.assets.storage.manager.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.LookupsUtil;
import com.braker.common.util.PoiUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.UserMng;
import com.braker.core.manager.impl.DepartMngImpl;
import com.braker.core.model.Depart;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.AssetFlowMng;
import com.braker.icontrol.assets.storage.manager.AssetStockMng;
import com.braker.icontrol.assets.storage.manager.AssetsAttacMng;
import com.braker.icontrol.assets.storage.manager.RegistMng;
import com.braker.icontrol.assets.storage.manager.StorageMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.icontrol.assets.storage.model.Regist;
import com.braker.icontrol.assets.storage.model.Storage;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class StorageMngImpl extends BaseManagerImpl<Storage> implements StorageMng{

	@Autowired
	private RegistMng registMng;
	@Autowired
	private AssetsAttacMng assetsAttacMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private AssetStockMng assetStockMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private LookupsMng lookupsMng;
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
	private PrivateInforMng privateInforMng;
	@Autowired
	private AssetFlowMng assetFlowMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private CgReceiveMng cgReceiveMng;
	
	@Override
	public Pagination list(Storage storage, User user,Integer pageNo, Integer pageSize) {
 		Finder finder =Finder.create(" FROM Storage where fAssStauts!='99'");
		
 		if(!StringUtil.isEmpty(storage.getfAssStorageCode())){
			finder.append(" AND fAssStorageCode LIKE :fAssStorageCode");
			finder.setParam("fAssStorageCode", "%"+storage.getfAssStorageCode()+"%");
		}
 		if(!StringUtil.isEmpty(storage.getfGainingMethod())){
 			finder.append(" AND fGainingMethod =:fGainingMethod");
 			finder.setParam("fGainingMethod", storage.getfGainingMethod());
 		}
		if(!StringUtil.isEmpty(storage.getfOperator())){
			finder.append(" AND fOperator LIKE :fOperator");
			finder.setParam("fOperator", "%"+storage.getfOperator()+"%");
		}
		if(user.getRoleName().contains("资产管理岗")){
			
		}else{
			finder.append(" AND fOperatorID = :fOperatorID");
			finder.setParam("fOperatorID", user.getId());
		}
		if(!StringUtil.isEmpty(storage.getAccountantEntering())){
			finder.append(" AND accountantEntering =:accountantEntering");
			finder.setParam("accountantEntering", storage.getAccountantEntering());
		}
		if(!StringUtil.isEmpty(storage.getAssetEntering())){
			finder.append(" AND assetEntering =:assetEntering");
			finder.setParam("assetEntering", storage.getAssetEntering());
		}
		if(!StringUtil.isEmpty(storage.getfAssType())){
			finder.append(" AND fAssType =:fAssType");
			finder.setParam("fAssType", storage.getfAssType());
		}
		if(storage.getfPurchaseDateStart()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') >='"+storage.getfPurchaseDateStart()+"'");
		}
		if(storage.getfPurchaseDateEnd()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') <='"+storage.getfPurchaseDateEnd()+"'");
		}
		finder.append(" ORDER BY accountantEntering desc,assetEntering asc,fReqTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	@Override
	public Pagination accountantEnteringlist(Storage storage, User user,Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM Storage where fAssStauts='1'  AND accountantEntering in(0,1) ");
		if(!StringUtil.isEmpty(storage.getfAssStorageCode())){
			finder.append(" AND fAssStorageCode LIKE :fAssStorageCode");
			finder.setParam("fAssStorageCode", "%"+storage.getfAssStorageCode()+"%");
		}
		if(!StringUtil.isEmpty(storage.getfGainingMethod())){
			finder.append(" AND fGainingMethod =:fGainingMethod");
			finder.setParam("fGainingMethod", storage.getfGainingMethod());
		}
		/*if(!StringUtil.isEmpty(storage.getAccountantEntering())){
			finder.append(" AND accountantEntering =:accountantEntering");
			finder.setParam("accountantEntering", storage.getAccountantEntering());
		}*/
		if(!StringUtil.isEmpty(storage.getAssetEntering())){
			finder.append(" AND assetEntering =:assetEntering");
			finder.setParam("assetEntering", storage.getAssetEntering());
		}
		if(!StringUtil.isEmpty(storage.getfAssType())){
			finder.append(" AND fAssType =:fAssType");
			finder.setParam("fAssType", storage.getfAssType());
		}
		finder.append(" ORDER BY accountantEntering,fReqTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	@Override
	public Pagination assetEnteringList(Storage storage, User user,Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM Storage where fAssStauts='1'");
		if(!StringUtil.isEmpty(storage.getfAssStorageCode())){
			finder.append(" AND fAssStorageCode LIKE :fAssStorageCode");
			finder.setParam("fAssStorageCode", "%"+storage.getfAssStorageCode()+"%");
		}
		if(!StringUtil.isEmpty(storage.getfGainingMethod())){
			finder.append(" AND fGainingMethod =:fGainingMethod");
			finder.setParam("fGainingMethod", storage.getfGainingMethod());
		}
		if(!StringUtil.isEmpty(storage.getAccountantEntering())){
			finder.append(" AND accountantEntering =:accountantEntering");
			finder.setParam("accountantEntering", storage.getAccountantEntering());
		}
		if(!StringUtil.isEmpty(storage.getAssetEntering())){
			finder.append(" AND assetEntering =:assetEntering");
			finder.setParam("assetEntering", storage.getAssetEntering());
		}
		finder.append(" ORDER BY updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void save(List<Regist> rtorage,Storage storage, User user,String files) throws Exception {
		storage.setCreateTime(new Date());
		storage.setCreator(user.getAccountNo());
		storage.setfAssStauts("1");
		storage.setfFlowStatus("9");
		storage.setfReqTime(new Date());
		storage.setUpdateTime(new Date());
		storage.setUpdator(user.getAccountNo());
		storage.setAccountantEntering("0");
		storage=(Storage) super.saveOrUpdate(storage);
		AcceptCheck acceptCheck = new AcceptCheck();
		if(!StringUtil.isEmpty(String.valueOf(storage.getFacpId()))){
			acceptCheck = cgReceiveMng.findById(storage.getFacpId());
			if("1".equals(String.valueOf(acceptCheck.getfBillingStauts()))){
				throw new ServiceException("当前验收单已提交入账操作，请勿重复入账！");
			}
			acceptCheck.setfBillingStauts(1);
			cgReceiveMng.saveOrUpdate(acceptCheck);
		}
		attachmentMng.joinEntity(storage,files);
		//删除固定资产增加单下的所有清单单据
		List<Regist> registList = registMng.findByProperty("fId_S", storage.getfId_S());
		//如果有清单就删除
		if(registList.size()>0){
			for (int i = 0; i < registList.size(); i++) {
				registMng.deleteById(registList.get(i).getfListId());
			}
		}
		//重新按照填写的添加单据
		for (int i = 0; i < rtorage.size(); i++) {
			Regist regist = rtorage.get(i);
			regist.setfId_S(storage.getfId_S());
			regist.setfAssType(storage.getfAssType());
			if(!StringUtil.isEmpty(String.valueOf(storage.getFacpId()))){
				regist.setFcId(acceptCheck.getFcId());
				regist.setfId_U(acceptCheck.getfId_U());
			}
			super.merge(regist);
			//fSumAmount+=regist.getAmount();
		}
		//storage.setfSumAmount(fSumAmount);
		//判断是否需要推送消息，
		/*if("1".equals(storage.getfFlowStatus())){
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),storage.getJoinTable(),storage.getBeanCodeField(),storage.getBeanCode(), "GDZCZJ", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCZJ", user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//根据前面获得的角色的信息设置下一环节的用户名称/编码
			storage.setfNextUserCode(nextUser.getId());
			storage.setfNextUserName(nextUser.getName());
			//设置下节点节点编码
			storage.setfNextCode(firstKey+"");
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,storage.getBeanCode());
			
			//获得资产名称集合
			List nameList = new ArrayList<>();
			for(int i=0; i<rtorage.size(); i++){
				nameList.add(rtorage.get(i).getfAssName());
			}
			//调用名称拼接方法
			String name = StringUtil.nameJoint(nameList, "、", 4);
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(storage.getfNextUserCode());//任务处理人ID既是下节点处理人ID
			work.setTaskId(storage.getfId_S());//申请单ID
			work.setTaskCode(storage.getfAssStorageCode());//为申请单的单号
			work.setTaskName("[资产入账]"+name);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/Storage/approvel/"+storage.getfId_S());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/Storage/detail/"+ storage.getfId_S());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(new Date());//任务提交时间
			personalWorkMng.merge(work);
			//添加一个自己的已办事项
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(user.getId());//任务处理人ID既是下节点处理人ID
			minwork.setTaskId(storage.getfId_S());//申请单ID
			minwork.setTaskCode(storage.getfAssStorageCode());//为申请单的单号
			minwork.setTaskName("[资产入账]"+name);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			minwork.setUrl("/Storage/edit/"+storage.getfId_S());//退回修改url
			minwork.setUrl1("/Storage/detail/"+ storage.getfId_S());//查看url
			minwork.setUrl2("/Storage/delete/"+ storage.getfId_S());//退回删除url
			minwork.setTaskStauts("2");//待办
			minwork.setType("2");//任务类型：2-查看
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(new Date());//任务提交时间
			minwork.setFinishTime(new Date());
			personalWorkMng.merge(minwork);
		}*/
	}
	@Override
	public void saveIntangible(List<Regist> rtorage,Storage storage, User user,String files) throws Exception {
		storage.setCreateTime(new Date());
		storage.setCreator(user.getAccountNo());
		storage.setfAssStauts("1");
		storage.setfFlowStatus("9");
		storage.setfReqTime(new Date());
		storage.setUpdateTime(new Date());
		storage.setUpdator(user.getAccountNo());
		storage.setAccountantEntering("0");
		storage=(Storage) super.saveOrUpdate(storage);
		AcceptCheck acceptCheck = new AcceptCheck();
		if(!StringUtil.isEmpty(String.valueOf(storage.getFacpId()))){
			acceptCheck = cgReceiveMng.findById(storage.getFacpId());
			if("1".equals(String.valueOf(acceptCheck.getfBillingStauts()))){
				throw new ServiceException("当前验收单已提交入账操作，请勿重复入账！");
			}
			acceptCheck.setfBillingStauts(1);
			cgReceiveMng.saveOrUpdate(acceptCheck);
		}
		attachmentMng.joinEntity(storage,files);
		//删除固定资产增加单下的所有清单单据
		List<Regist> registList = registMng.findByProperty("fId_S", storage.getfId_S());
		//如果有清单就删除
		if(registList.size()>0){
			for (int i = 0; i < registList.size(); i++) {
				registMng.deleteById(registList.get(i).getfListId());
			}
		}
		//重新按照填写的添加单据
		for (int i = 0; i < rtorage.size(); i++) {
			Regist regist = rtorage.get(i);
			regist.setfId_S(storage.getfId_S());
			regist.setfAssType(storage.getfAssType());
			if(!StringUtil.isEmpty(String.valueOf(storage.getFacpId()))){
				regist.setFcId(acceptCheck.getFcId());
				regist.setfId_U(acceptCheck.getfId_U());
			}
			super.merge(regist);
		}
		/*//判断是否需要推送消息，
		if("1".equals(storage.getfFlowStatus())){
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),storage.getJoinTable(),storage.getBeanCodeField(),storage.getBeanCode(), "GDZCZJ", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCZJ", user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//根据前面获得的角色的信息设置下一环节的用户名称/编码
			storage.setfNextUserCode(nextUser.getId());
			storage.setfNextUserName(nextUser.getName());
			//设置下节点节点编码
			storage.setfNextCode(firstKey+"");
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,storage.getBeanCode());
			
			//获得资产名称集合
			List nameList = new ArrayList<>();
			for(int i=0; i<rtorage.size(); i++){
				nameList.add(rtorage.get(i).getfAssName());
			}
			//调用名称拼接方法
			String name = StringUtil.nameJoint(nameList, "、", 4);
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(storage.getfNextUserCode());//任务处理人ID既是下节点处理人ID
			work.setTaskId(storage.getfId_S());//申请单ID
			work.setTaskCode(storage.getfAssStorageCode());//为申请单的单号
			work.setTaskName("[资产入账]"+name);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/Storage/approvelIntangible/"+storage.getfId_S());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/Storage/detailIntangible/"+ storage.getfId_S());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(new Date());//任务提交时间
			personalWorkMng.merge(work);
			//添加一个自己的已办事项
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(user.getId());//任务处理人ID既是下节点处理人ID
			minwork.setTaskId(storage.getfId_S());//申请单ID
			minwork.setTaskCode(storage.getfAssStorageCode());//为申请单的单号
			minwork.setTaskName("[资产入账]"+name);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			minwork.setUrl("/Storage/editIntangible/"+storage.getfId_S());//退回修改url
			minwork.setUrl1("/Storage/detailIntangible/"+ storage.getfId_S());//查看url
			minwork.setUrl2("/Storage/delete/"+ storage.getfId_S());//退回删除url
			minwork.setTaskStauts("2");//待办
			minwork.setType("2");//任务类型：2-查看
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(new Date());//任务提交时间
			minwork.setFinishTime(new Date());
			personalWorkMng.merge(minwork);
		}*/
	}
	
	
	@Override
	public void saveAccountant(List<Regist> rtorage,Storage storage, User user,String files) throws Exception {
		storage = super.findById(storage.getfId_S());
		storage.setAccountantEntering("1");
		storage.setAssetEntering("0");
		super.saveOrUpdate(storage);
		
		//资产单下的所有清单单据
		List<Regist> registList = registMng.findByProperty("fId_S", storage.getfId_S());
		if("ZCLX-02".equals(storage.getfAssType())){
			//如果有清单就更新数据
			if(registList.size()>0){
				for (int i = 0; i < registList.size(); i++) {
					//更新填写的单据
					for (int j = 0; j < rtorage.size(); j++) {
						if(String.valueOf(registList.get(i).getfListId()).equals(String.valueOf(rtorage.get(j).getfListId()))){
							Regist regist = registList.get(i);
							regist.setfFinancialCode(rtorage.get(j).getfFinancialCode());
							regist.setfFinancialDate(rtorage.get(j).getfFinancialDate());
							if(StringUtils.isNotEmpty(rtorage.get(j).getfDepreciationStatusCode())){
								Lookups lookups=LookupsUtil.findByLookCode(rtorage.get(j).getfDepreciationStatusCode());
								regist.setfDepreciationStatus(lookups);
							}
							if(StringUtils.isNotEmpty(rtorage.get(j).getfValueTypeCode())){
								Lookups lookups=LookupsUtil.findByLookCode(rtorage.get(j).getfValueTypeCode());
								regist.setfValueType(lookups);
							}
							regist.setAmount(rtorage.get(j).getAmount());
							regist.setfUnappropriationAmount(rtorage.get(j).getfUnappropriationAmount());
							regist.setfAppropriationAmount(rtorage.get(j).getfAppropriationAmount());
							super.saveOrUpdate(regist);
						}
					}
				}
			}
		}else{
			//如果有清单就更新数据
			if(registList.size()>0){
				for (int i = 0; i < registList.size(); i++) {
					//更新填写的单据
					for (int j = 0; j < rtorage.size(); j++) {
						if(String.valueOf(registList.get(i).getfListId()).equals(String.valueOf(rtorage.get(j).getfListId()))){
							Regist regist = registList.get(i);
							regist.setfFinancialCode(rtorage.get(j).getfFinancialCode());
							regist.setfFinancialDate(rtorage.get(j).getfFinancialDate());
							if(StringUtils.isNotEmpty(rtorage.get(j).getfAmortizeStatusCode())){
								Lookups lookups=LookupsUtil.findByLookCode(rtorage.get(j).getfAmortizeStatusCode());
								regist.setfAmortizeStatus(lookups);
							}
							if(StringUtils.isNotEmpty(rtorage.get(j).getfValueTypeCode())){
								Lookups lookups=LookupsUtil.findByLookCode(rtorage.get(j).getfValueTypeCode());
								regist.setfValueType(lookups);
							}
							regist.setAmount(rtorage.get(j).getAmount());
							regist.setfUnappropriationAmount(rtorage.get(j).getfUnappropriationAmount());
							regist.setfAppropriationAmount(rtorage.get(j).getfAppropriationAmount());
							super.saveOrUpdate(regist);
						}
					}
				}
			}
		}
	}
	@Override
	public void saveRegister(List<Regist> rtorage,Storage storage, User user,String files) throws Exception {
		storage = super.findById(storage.getfId_S());
		storage.setAssetEntering("1");
		super.saveOrUpdate(storage);
		//资产单下的所有清单单据
		List<Regist> registList = registMng.findByProperty("fId_S", storage.getfId_S());
		//如果有清单就更新数据
		if(registList.size()>0){
			for (int i = 0; i < registList.size(); i++) {
				//更新填写的单据
				for (int j = 0; j < rtorage.size(); j++) {
					if(String.valueOf(registList.get(i).getfListId()).equals(String.valueOf(rtorage.get(j).getfListId()))){
						Regist regist = registList.get(i);
						regist.setfAssCode(rtorage.get(j).getfAssCode());
						super.saveOrUpdate(regist);
						//资产品目表
						AssetBasicInfo assetBasicInfo = new AssetBasicInfo();
						assetBasicInfo.setfListId(regist.getfListId());
						assetBasicInfo.setfAssCode(regist.getfAssCode());
						assetBasicInfo.setFcCode(regist.getFcCode());
						assetBasicInfo.setfAssName(regist.getfAssName());
						assetBasicInfo.setfAssType(regist.getfAssType());
						assetBasicInfo.setFcId(regist.getFcId());
						assetBasicInfo.setfFixedType(regist.getfFixedType());
						assetBasicInfo.setfFixedTypeId(regist.getfFixedTypeId());
						assetBasicInfo.setfFixedTypeCode(regist.getfFixedTypeCode());
						Lookups lookups=lookupsMng.findByLookCode(storage.getfGainingMethod());
						assetBasicInfo.setfForm(lookups.getCode());
						assetBasicInfo.setfFormShow(lookups.getName());
						assetBasicInfo.setfSPModel(regist.getfMMode());
						assetBasicInfo.setFmModel(regist.getFmModel());
						assetBasicInfo.setfAdminOfficial(regist.getfAdminOfficial());
						assetBasicInfo.setfActionDate(regist.getfActionDate());
						assetBasicInfo.setfFinancialDate(regist.getfFinancialDate());
						assetBasicInfo.setfFinancialCode(regist.getfFinancialCode());
						assetBasicInfo.setfAmount(regist.getAmount());
						assetBasicInfo.setfUnappropriationAmount(regist.getfUnappropriationAmount());
						assetBasicInfo.setfAppropriationAmount(regist.getfAppropriationAmount());
						assetBasicInfo.setfValueType(regist.getfValueType());
						assetBasicInfo.setfDepreciationStatus(regist.getfDepreciationStatus());
						Lookups fAvailableStauts=LookupsUtil.findByLookCode("ZCKYZT-01");
						assetBasicInfo.setfAvailableStauts(fAvailableStauts);
						if("ZCLX-02".equals(regist.getfAssType())){
							Lookups fUsedStauts=LookupsUtil.findByLookCode("ZCSYZT-02");
							assetBasicInfo.setfUsedStauts(fUsedStauts);
						}else{
							Lookups fUsedStauts=LookupsUtil.findByLookCode("ZCSYZT-01");
							assetBasicInfo.setfUsedStauts(fUsedStauts);
						}
						assetBasicInfo.setCreateTime(new Date());
						assetBasicInfo.setCreator(user.getName());
						assetBasicInfo.setfConfigStauts("0");
						assetBasicInfoMng.saveOrUpdate(assetBasicInfo);
						//添加操作流水
						Lookups fOptType=LookupsUtil.findByLookCode("ZCLSCZLX-01");
						Lookups fAssType=LookupsUtil.findByLookCode(regist.getfAssType());
						User storageUser = userMng.findById(storage.getfOperatorID());
						assetFlowMng.addFlow(storageUser, fOptType, storage.getBeanCode(), regist.getfAssCode(), regist.getfAssName(),fAssType, null, 1);
					}
				}
			}
		}
	}

	@Override
	public void delete(String id, User user) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Storage s = findById(Integer.valueOf(id));
		s.setfAssStauts("99");
		super.saveOrUpdate(s);
		//修改验收单上的状态
		/*if(!StringUtil.isEmpty(String.valueOf(s.getFacpId()))){
			if("QDFS-01".equals(s.getfGainingMethod())||"WXZCQDFS-02".equals(s.getfGainingMethod())){
			AcceptCheck acceptCheck = cgReceiveMng.findById(s.getFacpId());
			acceptCheck.setfBillingStauts(0);
			cgReceiveMng.saveOrUpdate(acceptCheck);
			}
		}*/
		
		List<Regist> r =  registMng.findByProperty("fId_S", s.getfId_S());
		//删除子清单表
		for (int i = 0; i < r.size(); i++) {
			registMng.deleteById(r.get(i).getfListId());
		}
		//更改入库单状态为删除
		/*String sql=" update T_ASSET_STORAGE set F_ASS_STAUTS='99',F_UPDATE_USER='"+user.getAccountNo()+"',F_UPDATE_TIME='"+sdf.format(new Date())+"' where F_ID="+id;
		Query query=getSession().createSQLQuery(sql);
		query.executeUpdate();*/
		//删除工作台信息
		/*List<PersonalWork> workLost = personalWorkMng.findByCodeAndUser(s.getfAssStorageCode(),  userMng.findById(s.getfOperatorID()));
		if(workLost.size()>0){
			for (int i = 0; i < workLost.size(); i++) {
				personalWorkMng.deleteById(workLost.get(i).getfId());
			}
		}*/
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void save_low(List<Regist> regist,Storage storage, User user,String files) {
		/*if (StringUtil.isEmpty(String.valueOf(storage.getfId_S()))) {
			Storage sto=new Storage();
			sto.setfAssStorageCode(StringUtil.Random("ZCRK"));
			sto.setfPurchaseDate(new Date());
			sto.setfOperator(user.getAccountNo());
			sto.setCreateTime(new Date());
			sto.setCreator(user.getAccountNo());
			sto.setfAssStauts("1");
			sto.setfAssType("ZCLX-01");
			sto.setfAction("1");
			sto.setfRemark_S(storage.getfRemark_S());
			sto.setfAcquisitionDate(storage.getfAcquisitionDate());
			sto.setfAcquisitionType(storage.getfAcquisitionType());
			Lookups lookup = lookupsMng.findByLookCode(storage.getfBuyType().getCode());
			sto.setfBuyType(lookup);;
			sto=(Storage) super.saveOrUpdate(sto);
			attachmentMng.joinEntity(sto,files);
			//assetsAttacMng.save(sto, files, user);
			for (int i = 0; i < regist.size(); i++) {
				if(StringUtil.isEmpty(String.valueOf(regist.get(i).getfListId()))){
					regist.get(i).setfAssStorageCode_R(sto.getfAssStorageCode());
					regist.get(i).setCreateTime(new Date());
					regist.get(i).setCreator(user.getAccountNo());
				}else{
					regist.get(i).setUpdateTime(new Date());
					regist.get(i).setUpdator(user.getAccountNo());
				}
				regist.get(i).setfAction_R("1");
				super.saveOrUpdate(regist.get(i));
				assetBasicInfoMng.save(regist.get(i), sto, user);
			}
		}else{
			storage.setUpdateTime(new Date());
			storage.setUpdator(user.getAccountNo());
			//根据入库单号查出来所有的清单记录
			List<Regist> reg = registMng.findByFAssStorageCode_R(storage.getfAssStorageCode());
			for (int i = 0; i < regist.size(); i++) {
				for (int j = reg.size()-1; j >=0; j--) {
					if(!StringUtil.isEmpty(String.valueOf(regist.get(i).getfListId()))&&regist.get(i).getfListId().equals(reg.get(j).getfListId())){
						reg.remove(reg.get(j));
					}
				}
			}
			//删除不同的
			if(reg.size()>0){
				for (int i = 0; i < reg.size(); i++) {
					//减去修改原来的入库数量
					assetStockMng.updateByCode(reg.get(i),user);
					registMng.deleteById(reg.get(i).getfListId());
				}
			}
			//保存
			if(regist.size()>0){
				//assetsAttacMng.save(storage, files, user);
				for (int i = 0; i < regist.size(); i++) {
					if(StringUtil.isEmpty(String.valueOf(regist.get(i).getfListId()))){
						regist.get(i).setCreateTime(new Date());
						regist.get(i).setCreator(user.getAccountNo());
					}else{
						regist.get(i).setUpdateTime(new Date());
						regist.get(i).setUpdator(user.getAccountNo());
					}
					regist.get(i).setfAssStorageCode_R(storage.getfAssStorageCode());
					regist.get(i).setfAction_R("1");
					assetBasicInfoMng.save(regist.get(i), storage, user);
					registMng.merge(regist.get(i));
				}
			}
			storage=(Storage) super.saveOrUpdate(storage);
			attachmentMng.joinEntity(storage,files);
		}*/
		
	}

	@Override
	public Storage findbyCondition(String condition, String val) {
		Finder finder=Finder.create(" FROM Storage WHERE "+condition+"='"+val+"'");
		return (Storage) super.find(finder).get(0);
	}

	@Override
	public Pagination approvalList(Storage storage, User user, Integer pageNo,Integer pageSize) {
		Finder finder =Finder.create(" FROM Storage where fAssStauts='1' AND fFlowStatus='1' AND fNextUserCode='"+user.getId()+"'");
		if(!StringUtil.isEmpty(storage.getfAssStorageCode())){
			finder.append(" AND fAssStorageCode LIKE :fAssStorageCode");
			finder.setParam("fAssStorageCode", "%"+storage.getfAssStorageCode()+"%");
		}
		if(!StringUtil.isEmpty(storage.getfOperator())){
			finder.append(" AND fOperator LIKE :fOperator");
			finder.setParam("fOperator", "%"+storage.getfOperator()+"%");
		}
		if(!StringUtil.isEmpty(storage.getfAssType())){
			finder.append(" AND fAssType =:fAssType");
			finder.setParam("fAssType", storage.getfAssType());
		}
		if(storage.getfPurchaseDateStart()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') >='"+storage.getfPurchaseDateStart()+"'");
		}
		if(storage.getfPurchaseDateEnd()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') <='"+storage.getfPurchaseDateEnd()+"'");
		}
		finder.append(" ORDER BY updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void updateStatusIntangible(String status, String id, String spjlFile,TProcessCheck checkBean,String planJson,User user) throws Exception {
		Storage bean = super.findById(Integer.valueOf(id));
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/Storage/approvelIntangible/";
		String lookUrl ="/Storage/detailIntangible/";
		bean=(Storage)tProcessCheckMng.checkProcess(checkBean,entity,user,"WXZCZJ",checkUrl,lookUrl,spjlFile);
		super.saveOrUpdate(bean);
		if("9".equals(bean.getCheckStauts())){
			bean.setAccountantEntering("0");
			super.saveOrUpdate(bean);
			}
	}
	
	@Override
	public void  updateStatus(String status, String id, String spjlFile,TProcessCheck checkBean,String planJson,User user) throws Exception {
		Storage bean = super.findById(Integer.valueOf(id));
		CheckEntity entity=(CheckEntity)bean;
		String checkUrl="/Storage/approvel/";
		String lookUrl ="/Storage/detail/";
		bean=(Storage)tProcessCheckMng.checkProcess(checkBean,entity,user,"GDZCZJ",checkUrl,lookUrl,spjlFile);
		super.saveOrUpdate(bean);
		if("9".equals(bean.getCheckStauts())){
			bean.setAccountantEntering("0");
			super.saveOrUpdate(bean);
		}
	}

	@Override
	public void saveFile(File file, User user) throws Exception{
		
		Date d = new Date();
		PoiUtil pu = new PoiUtil();
		if (file.exists()) {
			FileInputStream fis = null;
			Workbook workBook = null;
			try {
				fis = new FileInputStream(file);
				workBook = WorkbookFactory.create(fis);
				org.apache.poi.ss.usermodel.Sheet sheetAt = workBook.getSheetAt(0);
               /* String sheetName = sheetAt.getSheetName(); //获取工作表名称
*/					int rowsOfSheet = sheetAt.getPhysicalNumberOfRows(); // 获取当前Sheet的总列数
				/*System.out.println("当前表格的总行数:" + rowsOfSheet);*/

				//获取预算年度
				Row yrow = sheetAt.getRow(1);
				Cell ycell = yrow.getCell(0);
				ycell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String years = pu.getCellValue(ycell);
				
				List<Regist> regist=new ArrayList<Regist>();
				for1:
				for (int r = 4; r < rowsOfSheet; r++) { // 从第四行开始
					/*Regist bean = new Regist();
					bean.setIndexType("0"); //指标类型(0-基本指标；1-项目指标；)
					bean.setControlType("1");//控制方式(1-刚性控制（不允许超额支出）2-合理范围内控制
					bean.setAppDate(d);////批复日期
					bean.setYears(years.split("\\：")[1]);//预算年度
					bean.setStauts("1");//指标生成状态(0-未生成（新增记录时，默认为0） 1-已生成在项目列表二下之后
					bean.setReleaseStauts("1");//指标下达状态1
					Row row = sheetAt.getRow(r);
					if (row == null) {
						continue;
					} else {
						int numberOfCells = row.getPhysicalNumberOfCells();
						for (int c = 0; c < numberOfCells; c++) { // 总列(格)
							Cell cell = row.getCell(c);
//								if(c==1) {
//									//支出分类科目编码
//									cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//									String stringCellValue = pu.getCellValue(cell);
//									/*if(stringCellValue.equals("301") || stringCellValue.equals("302") || stringCellValue.equals("303") || stringCellValue==null) {
//										continue for1;
//									}
//									bean.setExpItemCode(stringCellValue);
//								}
							if(c==1) {
								//归属部门
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								bean.setDeptName(stringCellValue);
							}
							else if(c==2) {
								//归属部门编码
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								Depart dept=DepartMngImpl.codeDepartMap.get(stringCellValue);
								bean.setDeptCode(dept.getId());
							}
							else if(c==3) {
								//上级科目编码
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								bean.setExpItemCode2(stringCellValue);
							}
							else if(c==4) {
								//科目编码
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);//设置获取格式是字符串
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								bean.setExpItemCode(stringCellValue);
							}
							else if(c==6) {
								//指标名称
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								bean.setIndexName(stringCellValue);
								bean.setExpItemName(stringCellValue);
							}
							else if(c==7) {
								//项目编码
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								bean.setIndexCode(stringCellValue);
							}
							else if(c==8) {
								//批复金额
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								bean.setYsAmount(Double.valueOf(stringCellValue));
								bean.setPfAmount(Double.valueOf(stringCellValue));
								bean.setPfzAmount(Double.valueOf(stringCellValue));
								bean.setSyAmount(Double.valueOf(stringCellValue));
								bean.setXdAmount(Double.valueOf(stringCellValue));
								bean.setDjAmount(0.0);
							}
							else if(c==9) {
								//资金性质
								String stringCellValue = pu.getCellValue(cell);
								if("财政性资金".equals(stringCellValue)){
									bean.setProperty("0");
								}else{
									bean.setProperty("1");
								}
								
							}
						}
						if(StringUtils.isNotEmpty(bean.getIndexCode())) {
							super.saveOrUpdate(bean);
						}
					}*/
				}
				
				file.delete();//删除临时文件
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("读取excel文件报错,请检查excel格式");
				//e.printStackTrace();
			}finally{
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new ServiceException("文件不存在");
		}
	}

	@Override
	public String reCall(String id) {
		Storage bean = super.findById(Integer.valueOf(id));
		if(StringUtils.isNotEmpty(String.valueOf(bean.getFacpId()))){
			AcceptCheck acceptCheck = cgReceiveMng.findById(bean.getFacpId());
			acceptCheck.setfBillingStauts(0);
			cgReceiveMng.saveOrUpdate(acceptCheck);
		}
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="资产新增申请被撤回消息";
		String msg="您待审批的资产,任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean=(Storage) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "操作成功";
	}
	@Override
	public Pagination chooseStoragelist(Storage storage, String id, String checkacceptid, User user,
			Integer pageNo, Integer pageSize, String selectContId, String selectUptId) {
		Finder finder =Finder.create(" FROM Storage where fAssStauts='1'");
		
		if(!StringUtil.isEmpty(String.valueOf(storage.getFacpId()))){
			finder.append(" AND facpId in( :facpId )");
			finder.setParam("facpId", checkacceptid);
		}
		if(!StringUtil.isEmpty(storage.getfAssStorageCode())){
			finder.append(" AND fAssStorageCode LIKE :fAssStorageCode");
			finder.setParam("fAssStorageCode", "%"+storage.getfAssStorageCode()+"%");
		}
		if(!StringUtil.isEmpty(storage.getfGainingMethod())){
			finder.append(" AND fGainingMethod =:fGainingMethod");
			finder.setParam("fGainingMethod", storage.getfGainingMethod());
		}
		if(!StringUtil.isEmpty(storage.getAccountantEntering())){
			finder.append(" AND accountantEntering =:accountantEntering");
			finder.setParam("accountantEntering", storage.getAccountantEntering());
		}
//		if(!StringUtil.isEmpty(storage.getAssetEntering())){
//			finder.append(" AND assetEntering =:assetEntering");
//			finder.setParam("assetEntering", storage.getAssetEntering());
//		}
		if(!StringUtil.isEmpty(storage.getfAssType())){
			finder.append(" AND fAssType =:fAssType");
			finder.setParam("fAssType", storage.getfAssType());
		}
		// 查询该合同下的入账明细单
		List<Regist> registList = new ArrayList<Regist>();
		List<Regist> registLists = new ArrayList<Regist>();
		if (!StringUtil.isEmpty(selectContId)) {
			registList = registMng.findByProperty("fcId", Integer.parseInt(selectContId));
		}
		if (!StringUtil.isEmpty(selectUptId)) {
			registLists = registMng.findByProperty("fId_U", Integer.parseInt(selectUptId));
			Upt upt = uptMng.findById(Integer.valueOf(selectUptId));
			if(upt!=null) {
				ContractBasicInfo cbi = formulationMng.findById(upt.getfContId_U());
				if(cbi!=null) {
					registList = registMng.findByProperty("fcId", cbi.getFcId());
				}
			}
			
			registList.addAll(registLists);
		}
		if (registList != null && !registList.isEmpty()) {
			Object[] acRIds = new Object[registList.size()];
			for (int i = 0; i < registList.size(); i++) {
				acRIds[i] = registList.get(i).getfId_S();
			}
			finder.append(" AND fId_S in (:fId_S)");
			finder.setParamList("fId_S", acRIds);
		} else {
			return null;
		}
		
		finder.append(" ORDER BY updateTime desc");
		return super.find(finder, pageNo, pageSize);
	
	}

	
	
}
