package com.braker.icontrol.assets.rece.manager.Impl;

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
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.assets.rece.manager.ReceConfigListMng;
import com.braker.icontrol.assets.rece.manager.ReceListMng;
import com.braker.icontrol.assets.rece.manager.ReceMng;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.rece.model.ReceList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.AssetFlowMng;
import com.braker.icontrol.assets.storage.manager.AssetListMng;
import com.braker.icontrol.assets.storage.manager.AssetStockMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.model.UptClause;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class ReceMngImpl extends BaseManagerImpl<Rece> implements ReceMng{
	
	@Autowired
	private ReceListMng receListMng;
	@Autowired
	private AssetStockMng assetStockMng;
	@Autowired
	private AssetListMng assetListMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private ReceMng receMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private RoleMng roleMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private AssetFlowMng assetFlowMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private ReceConfigListMng receConfigListMng;
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	
	
	@Override
	public Pagination list(Rece rece, User user, Integer pageNo,Integer pageSize) {
		Finder finder =Finder.create(" FROM Rece WHERE fAssType='"+rece.getfAssType()+"' AND fAssStauts<>'99' AND fReqUserid='"+user.getId()+"' ");
		if(!StringUtil.isEmpty(rece.getfReceDept())){
			finder.append("AND fReceDept LIKE :fReceDept ");
			finder.setParam("fReceDept", "%"+rece.getfReceDept()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfAssReceCode())){
			finder.append("AND fAssReceCode LIKE :fAssReceCode ");
			finder.setParam("fAssReceCode", "%"+rece.getfAssReceCode()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfReceUser())){
			finder.append("AND fReceUser LIKE :fReceUser ");
			finder.setParam("fReceUser", "%"+rece.getfReceUser()+"%");
		}
		/*if(rece.getfReceTime()!=null){
			finder.append("AND DATEDIFF(fReceTime,'"+rece.getfReceTime()+"')=0");
		}*/
		if(rece.getfReceTimeBegin()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') >='"+rece.getfReceTimeBegin()+"'");
		}
		if(rece.getfReceTimeEnd()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') <='"+rece.getfReceTimeEnd()+"'");
		}
		if(!StringUtil.isEmpty(rece.getfFlowStauts_R())){
			finder.append(" AND fFlowStauts_R ='"+rece.getfFlowStauts_R()+"'");
		}
		finder.append("order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void save(Rece rece,String receFlies, List<ReceList> receList, User user)  throws Exception{
		if(StringUtil.isEmpty(String.valueOf(rece.getfId_R()))){
			rece.setCreateTime(new Date());
			rece.setCreator(user.getAccountNo());
			rece.setfReqDept(user.getDepartName());
			rece.setfReqDeptID(user.getDpID());
			rece.setfReqUser(user.getName());
			rece.setfReqUserid(user.getId());
			rece.setfReceDeptID(user.getDpID());
			rece.setfReceDept(user.getDepartName());
			rece.setfReceUser(user.getName());
			rece.setfReceUserid(user.getId());
			rece.setfAssStauts("1");
		}else{
			rece.setUpdateTime(new Date());
			rece.setUpdator(user.getAccountNo());
		}
		rece.setfAcceptStatus("0");
		rece.setfConfigStatus("0");
		rece.setfReceStatus(null);
		rece=(Rece) super.merge(rece);
		//???????????????????????????????????????
		List nameList = new ArrayList<>();
		for(int i=0; i<receList.size(); i++){
			nameList.add(receList.get(i).getfAssName_RL());
		}
		//????????????????????????
		String name = StringUtil.nameJoint(nameList, "???", 4);
		rece.setfAssName("-");
		if(rece.getfFlowStauts_R().equals("1")){
			rece.setfReqTime(new Date());
			String str=null;
			if("ZCLX-01".equals(rece.getfAssType())){
				str="DZYHPLY";
			}else if("ZCLX-02".equals(rece.getfAssType())){
				str="GDZCLY";
			}
			//???????????????????????????key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),rece.getJoinTable(),rece.getBeanCodeField(),rece.getBeanCode(), str, user);
			//?????????????????????????????????????????????????????????????????????
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(str, user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//???????????????????????????????????????		get(0)??????????????????????????????????????????????????????????????????li????????????????????????
			rece.setfNextUserName(nextUser.getName());
			rece.setfNextUserCode(nextUser.getId());
			//????????????????????????????????????1?????????????????????
			tProcessCheckMng.updateStauts(flowId,rece.getBeanCode());
			//???????????????????????????
			rece.setfNextCode(firstKey+"");	
			
			//???????????????????????????????????????
			PersonalWork work = new PersonalWork();
			work.setUserId(rece.getfNextUserCode());//???????????????ID????????????????????????ID
			work.setTaskId(rece.getfId_R());//?????????ID
			work.setTaskCode(rece.getfAssReceCode());//?????????????????????
			if(rece.getfAssType().equals("ZCLX-01")){
				work.setTaskName("[????????????]???????????????????????????");//????????????????????????????????????+?????????????????????????????????????????????
			}else if(rece.getfAssType().equals("ZCLX-02")){
				work.setTaskName("[????????????]????????????????????????");//????????????????????????????????????+?????????????????????????????????????????????
			}
			work.setUrl("/Rece/approvalRece/"+rece.getfId_R());//??????????????????????????????url,???????????????????????????????????????
			work.setUrl1("/Rece/detail/"+ rece.getfId_R());//??????url
			work.setTaskStauts("0");//??????
			work.setType("1");//???????????????1-??????
			work.setTaskType("1");//???????????????1????????????
			work.setBeforeUser(user.getName());//?????????????????????
			work.setBeforeDepart(user.getDepartName());//?????????????????????????????????
			work.setBeforeTime(new Date());//??????????????????
			personalWorkMng.merge(work);
			//???????????????????????????
			PersonalWork minwork = new PersonalWork();
			minwork.setUserId(rece.getfReqUserid());//???????????????ID????????????????????????ID
			minwork.setTaskId(rece.getfId_R());//?????????ID
			minwork.setTaskCode(rece.getfAssReceCode());//?????????????????????
			if(rece.getfAssType().equals("ZCLX-01")){
				minwork.setTaskName("[????????????]???????????????????????????");//????????????????????????????????????+?????????????????????????????????????????????
			}else if(rece.getfAssType().equals("ZCLX-02")){
				minwork.setTaskName("[????????????]????????????????????????");//????????????????????????????????????+?????????????????????????????????????????????
			}
			minwork.setUrl("/Rece/edit/"+rece.getfId_R());//??????????????????????????????url,???????????????????????????????????????
			minwork.setUrl1("/Rece/detail/"+ rece.getfId_R());//??????url
			minwork.setUrl2("/Rece/delete/"+ rece.getfId_R());//
			minwork.setTaskStauts("2");//??????
			minwork.setType("2");//???????????????2-??????
			minwork.setTaskType("0");//???????????????0????????????
			minwork.setBeforeUser(user.getName());//?????????????????????
			minwork.setBeforeDepart(user.getDepartName());//?????????????????????????????????
			minwork.setBeforeTime(new Date());//??????????????????
			minwork.setFinishTime(new Date());
			personalWorkMng.merge(minwork);
		}
		rece=(Rece) super.updateDefault(rece);
		//????????????
		attachmentMng.joinEntity(rece, receFlies);
		//??????????????????
		receListMng.save(rece, receList);
		
	}

	@Override
	public void delete(String id,User user) {
		Rece rece = findById(Integer.valueOf(id));
		rece.setUpdateTime(new Date());
		rece.setUpdator(user.getAccountNo());
		rece.setfAssStauts("99");
		//????????????????????????lk?????????
		/*List<AssetStock> Stock = assetStockMng.findbycode(rece.getfAssCode_R());
		AssetStock oldStock = Stock.get(0);
		oldStock.setfBeforeRestNum(oldStock.getfRestNum());
		oldStock.setfRestNum(oldStock.getfRestNum()+Integer.valueOf(rece.getfReceNum()));
		oldStock.setfAfterRestNum(oldStock.getfRestNum());
		oldStock.setfUpadteUser(user.getAccountNo());
		oldStock.setfUpdateTime(new Date());
		assetStockMng.merge(oldStock);*/
		super.saveOrUpdate(rece);
		//?????????????????????
		List<PersonalWork> workLost = personalWorkMng.findByCodeAndUser(rece.getfAssReceCode(), userMng.findById(rece.getfReqUserid()));
		if(workLost.size()>0){
			for (int j = 0; j < workLost.size(); j++) {
				personalWorkMng.deleteById(Integer.valueOf(workLost.get(j).getfId()));
			}
		}
	}

	@Override
	public Pagination approvalList(Rece rece, User user, Integer pageNo,Integer pageSize) {
		Finder finder =Finder.create(" FROM Rece WHERE fAssType='"+rece.getfAssType()+"' AND fAssStauts <>'99' AND fFlowStauts_R in('1','9') AND fNextUserCode='"+user.getId()+"' ");
		/*if(!StringUtil.isEmpty(rece.getfAssName())){
			finder.append("AND fAssName LIKE :fAssName ");
			finder.setParam("fAssName", "%"+rece.getfAssName()+"%");
		}*/
		if(!StringUtil.isEmpty(rece.getfAssReceCode())){
			finder.append("AND fAssReceCode LIKE :fAssReceCode ");
			finder.setParam("fAssReceCode", "%"+rece.getfAssReceCode()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfReceUser())){
			finder.append("AND fReceUser LIKE :fReceUser ");
			finder.setParam("fReceUser", "%"+rece.getfReceUser()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfReqDept())){
			finder.append("AND fReqDept LIKE :fReqDept ");
			finder.setParam("fReqDept", "%"+rece.getfReqDept()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfReqUser())){
			finder.append("AND fReqUser LIKE :fReqUser ");
			finder.setParam("fReqUser", "%"+rece.getfReqUser()+"%");
		}
		/*if(rece.getfReceTime()!=null){
			finder.append("AND DATEDIFF(fReceTime,'"+rece.getfReceTime()+"')=0");
		}*/
		if(rece.getfReceTimeBegin()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') >='"+rece.getfReceTimeBegin()+"'");
		}
		if(rece.getfReceTimeEnd()!=null){
			finder.append(" AND DATE_FORMAT(fReqTime,'%Y-%m-%d') <='"+rece.getfReceTimeEnd()+"'");
		}
		finder.append("order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void savefixed(Rece rece, User user,List<ReceList> rl,String stauts)  throws Exception{
		/*//???????????????????????????key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(), "GDZCWX", user);
			//?????????????????????????????????????????????????????????????????????
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCWX", user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//???????????????????????????????????????		get(0)??????????????????????????????????????????????????????????????????li????????????????????????
			rece.setfNextUserName(nextUser.getName());
			rece.setfNextUserCode(nextUser.getId());
			//???????????????????????????
			rece.setfNextCode(firstKey+"");	
			//?????????
			rece.setfOperator(user.getAccountNo());
			//????????????????????????????????????1?????????????????????
			tProcessCheckMng.updateStauts(flowId,rece.getBeanCode());
			if(StringUtil.isEmpty(String.valueOf(rece.getfId_R()))){
				rece.setCreateTime(new Date());
				rece.setfReceTime(new Date());
				rece.setCreator(user.getAccountNo());
				rece.setfAssStauts("1");
				//???????????????
				rece.setfOperator(user.getAccountNo());
				rece.setfOperatorDept(user.getDepartName());
				rece.setfOperatorTime(new Date());
				//assetStockMng.updateByCode(rece,user);
				for (int i = 0; i < rl.size(); i++) {
					//??????????????????
					List<AssetStock> stock = assetStockMng.findbycode(rl.get(i).getfAssCode_RL());
					stock.get(0).setfBeforeRestNum(stock.get(0).getfRestNum());
					stock.get(0).setfRestNum(stock.get(0).getfRestNum()-Integer.valueOf(rl.get(i).getfReceNum_RL()));
					stock.get(0).setfAfterRestNum(stock.get(0).getfRestNum());
					stock.get(0).setfUpadteUser(user.getAccountNo());
					stock.get(0).setfUpdateTime(new Date());
					super.merge(stock.get(0));
					
					rl.get(i).setCreateTime(new Date());
					rl.get(i).setCreator(user.getAccountNo());
					rl.get(i).setfAssReceCode_RL(rece.getfAssReceCode());
					List<AssetStock> abi=assetStockMng.findbycode(rl.get(i).getfAssCode_RL());
					rl.get(i).setfRestNum(String.valueOf(abi.get(0).getfRestNum()));
					super.merge(rl.get(i));
				}
			}else{
				rece.setUpdateTime(new Date());
				rece.setUpdator(user.getAccountNo());
				//????????????????????????????????????
				List<ReceList> re=receListMng.findAllById(rece.getfAssReceCode());
				for (int i = 0; i < re.size(); i++) {
					for (int j = 0; j < rl.size(); j++) {
						if(re.get(i).getfListId().equals(rl.get(j).getfListId())){
							re.remove(i);
						}
					}
				}
				//???????????????
				for (int i = 0; i < re.size(); i++) {
					receListMng.deleteById(re.get(i).getfListId());
				}
				//?????????????????????????????????
				for (int i = 0; i < rl.size(); i++) {
					rl.get(i).setCreateTime(new Date());
					rl.get(i).setCreator(user.getAccountNo());
					rl.get(i).setfAssReceCode_RL(rece.getfAssReceCode());
					List<AssetStock> abi=assetStockMng.findbycode(rl.get(i).getfAssCode_RL());
					rl.get(i).setfRestNum(String.valueOf(abi.get(0).getfRestNum()));
					super.merge(rl.get(i));
				}
			}
			super.saveOrUpdate(rece);*/
	}

	@Override
	public void updateStauts( User user, Rece rece,TProcessCheck checkBean,String ConfigPlanjson,String file)  throws Exception{
		rece=this.findById(rece.getfId_R());
		CheckEntity entity=(CheckEntity)rece;
		String checkUrl="/Rece/approvalRece/";
		String lookUrl ="/Rece/detail/";
		String str=null;
		if("ZCLX-01".equals(rece.getfAssType())){
			str="DZYHPLY";
		}else if("ZCLX-02".equals(rece.getfAssType())){
			str="GDZCLY";
		}
		
		rece = (Rece)tProcessCheckMng.checkProcess(checkBean,entity,user,str,checkUrl,lookUrl,file);
		if("9".equals(rece.getfFlowStauts_R())){//????????????
			List<ReceConfigList> receConfigList = JSON.parseObject(ConfigPlanjson.toString(),new TypeReference<List<ReceConfigList>>(){});
			//???????????????????????????????????????
			List nameList = new ArrayList<>();
			for(int i=0; i<receConfigList.size(); i++){
				nameList.add(receConfigList.get(i).getfAssName_CL());
			}
			//????????????????????????
			String name = StringUtil.nameJoint(nameList, "???", 4);
			rece.setfAssName(name);
			for (int i = 0; i < receConfigList.size(); i++) {
				ReceConfigList receConfig= receConfigList.get(i);
				receConfig.setRece_CL(rece);
				receConfig.setfAssReceCode_CL(rece.getfAssReceCode());
				super.merge(receConfig);
				if(!"".equals(String.valueOf(receConfig.getfAssId()))){
					AssetBasicInfo assetBaseic = assetBasicInfoMng.findById(receConfig.getfAssId());
					assetBaseic.setfConfigStauts("1");
					assetBasicInfoMng.saveOrUpdate(assetBaseic);
				}
			}
			rece.setfConfigStatus("1");
			rece.setfReceStatus(null);
			//tProcessPrintDetailMng.arrangeReceFixedCheckDetail(rece, user);
		}
		rece = (Rece) super.saveOrUpdate(rece);
	}

	@Override
	public Rece storkNum(Rece rece) {
		
		Rece re=new Rece();
		Finder finder=Finder.create(" from ReceList where fAssReceCode_RL='"+rece.getfAssReceCode()+"'");
		List<ReceList> rl = super.find(finder);
		/*for (int i = 0; i < rl.size(); i++) {
			List<AssetStock> s = assetStockMng.findbycode(rl.get(i).getfAssCode_RL());
			if(s.get(0).getfRestNum()<Integer.valueOf(rl.get(i).getfReceNum_RL())){
				re.setfReceRemark(rl.get(i).getfAssName_RL());
				re.setfReceNum(rl.get(i).getfReceNum_RL());
			}
		}*/
		return re;
	}

	@Override
	public Pagination allocalist(Rece rece, User user, Integer pageNo,Integer pageSize) {
		Finder finder=Finder.create(" FROM Rece WHERE fFlowStauts_R='9' AND fAssStauts<>'99' AND fAssType='ZCLX-02' ");
		/*if(!StringUtil.isEmpty(rece.getfAssName())){
			finder.append("AND fAssName LIKE :fAssName ");
			finder.setParam("fAssName", "%"+rece.getfAssName()+"%");
		}*/
		if(!StringUtil.isEmpty(rece.getfAssReceCode())){
			finder.append("AND fAssReceCode LIKE :fAssReceCode ");
			finder.setParam("fAssReceCode", "%"+rece.getfAssReceCode()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfReceUser())){
			finder.append("AND fReceUser LIKE :fReceUser ");
			finder.setParam("fReceUser", "%"+rece.getfReceUser()+"%");
		}
		if(rece.getfReqTime()!=null){
			finder.append("AND DATEDIFF(fReqTime,'"+rece.getfReqTime()+"')=0");
		}
		finder.append("order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public Rece storkNum(List<ReceList> receList) {
		Rece re=new Rece();
		/*for (int i = 0; i < receList.size(); i++) {
			List<AssetStock> s = assetStockMng.findbycode(receList.get(i).getfAssCode_RL());
			if(s.get(0).getfRestNum()<Integer.valueOf(receList.get(i).getfReceNum_RL())){
				re.setfReceRemark(receList.get(i).getfAssName_RL());
				re.setfReceNum(receList.get(i).getfReceNum_RL());
			}
		}*/
		return re;
	}

	@Override
	public Rece findCode(String fcode) {
		Finder finder=Finder.create(" from Rece WHERE fAssReceCode='"+fcode+"'");
		return (Rece) super.find(finder).get(0);
	}
	@Override
	public Rece findbyCondition(String condition, String val) {
		Finder finder=Finder.create(" FROM Rece WHERE "+condition+"='"+val+"'");
		return (Rece) super.find(finder).get(0);
	}

	@Override
	public void saveConfigList(User user, Rece rece, String ConfigPlanjson)
			throws Exception {
		rece=this.findById(rece.getfId_R());
		List<ReceConfigList> receConfigList = JSON.parseObject(ConfigPlanjson.toString(),new TypeReference<List<ReceConfigList>>(){});
		List<ReceConfigList> oldreceConfigList = receConfigListMng.findbyReceid(rece.getfId_R());
		
		//?????????????????????
		for (int i = 0; i < oldreceConfigList.size(); i++) {
			receConfigListMng.deleteById(oldreceConfigList.get(i).getfConfigListId());
		}
		//??????????????????
		for (int i = 0; i < receConfigList.size(); i++) {
			ReceConfigList receConfig= receConfigList.get(i);
			receConfig.setRece_CL(rece);
			receConfig.setfAssReceCode_CL(rece.getfAssReceCode());
			/*AssetBasicInfo assetBaseic = assetBasicInfoMng.findbyCode(receConfig.getfAssCode_CL());
			Lookups lookup = lookupsMng.findByLookCode("ZCSYZT-01");
			assetBaseic.setfAvailableStauts(lookup);
			assetBaseic.setfUseNameID(rece.getfReceUserid());
			assetBaseic.setfUseName(rece.getfReceUser());
			assetBaseic.setfUseDeptID(rece.getfReceDeptID());
			assetBaseic.setfUseDept(rece.getfReceDept());
			assetBaseic.setfReceDate(new Date());
			super.merge(assetBaseic);*/
			super.merge(receConfig);
		}
		//???????????????????????????????????????
		List nameList = new ArrayList<>();
		for(int i=0; i<receConfigList.size(); i++){
			nameList.add(receConfigList.get(i).getfAssName_CL());
		}
		//????????????????????????
		String name = StringUtil.nameJoint(nameList, "???", 4);
		rece.setfAssName(name);
		rece.setfConfigStatus("1");
		rece.setfReceStatus(null);
		rece = (Rece) super.saveOrUpdate(rece);
	}

	@Override
	public void updateConfig(String stauts,User user, Rece rece, TProcessCheck checkBean,
			String ConfigPlanjson, String file) throws Exception {
		rece=this.findById(rece.getfId_R());
		if("1".equals(stauts)){//??????
			rece.setfReceStatus("1");
			rece.setConfirmDate(new Date());
			List<ReceConfigList> receConfigList = receConfigListMng.findbyReceid(rece.getfId_R());
			//??????????????????
			for (int i = 0; i < receConfigList.size(); i++) {
				ReceConfigList receConfig= receConfigList.get(i);
				AssetBasicInfo assetBaseic = assetBasicInfoMng.findById(receConfig.getfAssId());
				Lookups lookup = lookupsMng.findByLookCode("ZCSYZT-01");
				//Lookups lookup1 = lookupsMng.findByLookCode("ZCKYZT-02");
				//assetBaseic.setfAvailableStauts(lookup1);
				assetBaseic.setfUsedStauts(lookup);
				assetBaseic.setfUseNameID(rece.getfReceUserid());
				assetBaseic.setfUseName(rece.getfReceUser());
				assetBaseic.setfUseDeptID(rece.getfReceDeptID());
				assetBaseic.setfUseDept(rece.getfReceDept());
				assetBaseic.setfReceDate(new Date());
				assetBaseic.setfConfigStauts("0");
				super.merge(assetBaseic);
				//??????????????????
				Lookups fOptType=LookupsUtil.findByLookCode("ZCLSCZLX-02");
				Lookups fAssType=LookupsUtil.findByLookCode(rece.getfAssType());
				User receUser = userMng.findById(rece.getfReqUserid());
				assetFlowMng.addFlow(receUser, fOptType, rece.getBeanCode(), assetBaseic.getfAssCode(), assetBaseic.getfAssName(),fAssType, null, 1);
			}

		}else if("0".equals(stauts)){//?????????
			rece.setfReceStatus("0");
			List<ReceConfigList> receConfigList = receConfigListMng.findbyReceid(rece.getfId_R());
			//??????????????????
			for (int i = 0; i < receConfigList.size(); i++) {
				ReceConfigList receConfig= receConfigList.get(i);
				AssetBasicInfo assetBaseic = assetBasicInfoMng.findById(receConfig.getfAssId());
				Lookups lookup = lookupsMng.findByLookCode("ZCSYZT-02");
				assetBaseic.setfUsedStauts(lookup);
				assetBaseic.setfConfigStauts("0");
				assetBasicInfoMng.saveOrUpdate(assetBaseic);
			}
			String str = new String();
			if("ZCLX-01".equals(rece.getfAssType())){
				str="DZYHPLY";
			}else if("ZCLX-02".equals(rece.getfAssType())){
				str="GDZCLY";
			}
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(str, rece.getfReceDeptID());
			Integer flowId= processDefin.getFPId();
			List<TNodeData> nodeList = tNodeDataMng.findByFlowId(flowId);
			String departName=departMng.findDeptByUserId(rece.getfReqUserid())[0];
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(str,departName);
			//????????????
			List<TProcessCheck> processCheck = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),rece.getBeanCode());
			User nodeUser = userMng.findById(processCheck.get(0).getFuserId());
			rece.setfNextUserName(nodeUser.getName());
			rece.setfNextUserCode(nodeUser.getId());
			/*for (int i = 0; i < nodeList.size(); i++) {
				TNodeData node = nodeList.get(i);
				if("End".equals(node.getCategory())){
					User nodeUser = userMng.findById(node.getUserId());
					rece.setfNextUserName(nodeUser.getName());
					rece.setfNextUserCode(nodeUser.getId());
				}
			}*/
			
		}
		rece = (Rece) super.saveOrUpdate(rece);
		
	}

	@Override
	public Pagination acceptList(Rece rece, User user, Integer pageNo,
			Integer pageSize) {
		Finder finder =Finder.create(" FROM Rece WHERE fAssType='"+rece.getfAssType()+"' AND fAssStauts<>'99'AND fReceStatus=1 AND fFlowStauts_R=9 ");
		if(!StringUtil.isEmpty(rece.getfReqDept())){
			finder.append("AND fReqDept LIKE :fReqDept ");
			finder.setParam("fReqDept", "%"+rece.getfReqDept()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfReqUser())){
			finder.append("AND fReqUser LIKE :fReqUser ");
			finder.setParam("fReqUser", "%"+rece.getfReqUser()+"%");
		}
		if(!StringUtil.isEmpty(rece.getfAssReceCode())){
			finder.append("AND fAssReceCode LIKE :fAssReceCode ");
			finder.setParam("fAssReceCode", "%"+rece.getfAssReceCode()+"%");
		}
		finder.append("order by updateTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void reCall(String id) {
		
		Rece bean = super.findById(Integer.valueOf(id));
		//????????????
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//???????????????????????????
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="?????????????????????????????????";
		String msg="??????????????????????????? ,???????????????("+bean.getBeanCode()+")???"+sdf.format(new Date())+"??????????????????,???????????????.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//??????
		bean=(Rece) reCall((CheckEntity) bean);
		this.updateDefault(bean);
	}


}
