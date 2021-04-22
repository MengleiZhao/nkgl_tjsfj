package com.braker.icontrol.cgmanage.cgreveive.manager.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;
import com.braker.icontrol.cgmanage.cgexport.manager.ExportCgMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BidRegist;
import com.braker.icontrol.cgmanage.cgreveive.manager.AcceptContractRegisterListMng;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.purchase.apply.manager.PurchaseApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;


/**
 * 采购验收的service实现类
 * @author 冉德茂
 * @createtime 2018-07-17
 * @updatetime 2018-07-17
 */
@Service
@Transactional
public class CgReceiveMngImpl extends BaseManagerImpl<AcceptCheck> implements CgReceiveMng {
	@Autowired
	private CgReceiveMng cgReceiveMng;
	@Autowired
	private CgApplysqMng cgApplysqMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private PurchaseApplyMng purchaseApplyMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private ContractRegisterListMng contractRegisterListMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private ExportCgMng exportCgMng;
	@Autowired
	private AcceptContractRegisterListMng acceptContractRegisterListMng;
	
	@Override
	public Pagination pageList(AcceptCheck bean, User user, Integer pageNo, Integer pageSize,String searchData) {
		Finder finder = Finder.create(" FROM AcceptCheck WHERE 1=1");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (facpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(facpTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		/*if(!StringUtil.isEmpty(bean.getFacpCode())){//按采购编号模糊查询
			finder.append(" AND facpCode LIKE :facpCode");
			finder.setParam("facpCode", "%"+bean.getFacpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){//按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}*/
		if(user.getRoleName().contains("采购管理岗")){
			
		}else{
			String deptIdStr=departMng.getDeptIdStrByUser(user);
	 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
	 			finder.append(" and facpUserId = :facpUserId").setParam("facpUserId", user.getId());
	 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
	 			
	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
	 			finder.append(" and fDepartId in ("+deptIdStr+")");
	 		}
		}
		finder.append(" order by facpTime desc");
		return super.find(finder, pageNo, pageSize);
	}
	@Override
	public Pagination pageReceiveList(AcceptCheck bean, User user, Integer pageNo, Integer pageSize) {
		Finder finder = Finder.create(" FROM AcceptCheck WHERE fBillingStauts=0 ");
		if(!StringUtil.isEmpty(bean.getFpItemsName())){//按品目名称查询
			finder.append(" AND fpItemsName =:fpItemsName");
			finder.setParam("fpItemsName", bean.getFpItemsName());
		}
		if(!StringUtil.isEmpty(bean.getFacpCode())){//按验收编号模糊查询
			finder.append(" AND facpCode LIKE :facpCode");
			finder.setParam("facpCode", "%"+bean.getFacpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpCode())){//按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFcCode())){//按合同编号模糊查询
			finder.append(" AND fcCode LIKE :fcCode");
			finder.setParam("fcCode", "%"+bean.getFcCode()+"%");
		}
		finder.append(" order by facpTime desc");
		Pagination p = super.find(finder, pageNo, pageSize);
		List<AcceptCheck> aclist = (List<AcceptCheck>) p.getList();
		for (int i = 0; i < aclist.size(); i++) {
			List<AcceptContractRegisterList> acrList = acceptContractRegisterListMng.findFpIdbyMingxi(String.valueOf(aclist.get(i).getFacpId()));
			String str = new String();
			for (int j = 0; j < acrList.size(); j++) {
				if(j==0){
					str = acrList.get(j).getFmName();
				}else {
					str = str+","+acrList.get(j).getFmName();
				}
			}
			aclist.get(i).setCname(str);
		}
		p.setList(aclist);
		return p;
	}
	
	@Override
	public void saveReceive(AcceptCheck acBean, String files, User user,String jsonList) throws Exception {
		Date date = new Date();
		if(acBean.getFacpId() == null) {
			//创建人、创建时间
			acBean.setCreator(user.getName());
			acBean.setCreateTime(date);
			
			//验收人、验收部门、验收时间
			acBean.setFacpUserId(user.getId());
			acBean.setFacpUsername(user.getName());
			acBean.setfDepartId(user.getDpID());
			acBean.setfDepartName(user.getDepartName());
			acBean.setFacpTime(date);
		}else {
			//修改人、修改时间
			acBean.setUpdator(user.getName());
			acBean.setUpdateTime(date);
			acBean.setFacpTime(date);
		}
		if("PMMC-4".equals(acBean.getFpItemsName())||"PMMC-5".equals(acBean.getFpItemsName())){
			//工作流的节点配置（状态1可以继续走审批流，该状态不会变）
			if("1".equals(acBean.getfCheckStauts())){
				//业务范围
				String busiArea = "HWCGYS";
				//得到第一个审批节点key
				Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), acBean.getJoinTable(), acBean.getBeanCodeField(), acBean.getBeanCode(), busiArea, user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode(busiArea, user.getDpID());
				Integer flowId = processDefin.getFPId();
				TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				User nextUser = userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号
				acBean.setUserName2(nextUser.getName());
				acBean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				acBean.setnCode(String.valueOf(firstKey));
				//把历史审批记录全部设置为1，表示重新审批
				tProcessCheckMng.updateStauts(flowId, acBean.getBeanCode());
				
				//保存验收信息
				acBean = (AcceptCheck)super.saveOrUpdate(acBean);
					
				//添加审批人个人首页代办信息
				PersonalWork work = new PersonalWork();
				work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
				work.setTaskId(acBean.getFacpId());//申请单ID
				work.setTaskCode(acBean.getFacpCode());//为申请单的单号
				work.setTaskName("[采购验收]"+acBean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
				work.setUrl("/cgreceive/check?id="+acBean.getFacpId()+"&type=0");//为审批页面内容显示的url,需要将数据传入不然无法访问
				work.setUrl1("/cgreceive/detail?id="+acBean.getFacpId()+"&type=0");//查看url
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
				work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
				work2.setTaskId(acBean.getFacpId());//申请单ID
				work2.setTaskCode(acBean.getFacpCode());//为申请单的单号
				work2.setTaskName("[采购验收]"+acBean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
				work2.setUrl("/cgreceive/edit?id="+acBean.getFacpId()+"&type=0");//退回修改url
				work2.setUrl1("/cgreceive/detail?id="+acBean.getFacpId()+"&type=0");//查看url
				work2.setTaskStauts("2");//已办
				work2.setType("2");//任务类型（2查看）
				work2.setTaskType("0");//任务归属（0发起人）
				work2.setBeforeUser(user.getName());//任务提交人姓名
				work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
				work2.setBeforeTime(date);//任务提交时间
				work2.setFinishTime(date);
				personalWorkMng.merge(work2);
			}else {
				//保存验收信息
				acBean = (AcceptCheck)super.saveOrUpdate(acBean);
			}
		}else {
			//保存验收信息
			acBean.setfMatchStauts("1");
			acBean.setfBillingStauts(0);
			acBean = (AcceptCheck)super.saveOrUpdate(acBean);
		}
		//保存附件信息
		attachmentMng.joinEntity(acBean, files);
		
		//删除数据库中   验收中的明细
		getSession().createSQLQuery("delete from T_ACCEPT_CONTRACT_REGISTER_LIST where F_ACP_ID ="+acBean.getFacpId()+" ").executeUpdate();
		if(!StringUtil.isEmpty(jsonList)){
			//获取验收中的明细的Json对象
			List<AcceptContractRegisterList> acceptContractRegisterLists = JSON.parseObject("["+jsonList.toString().substring(0, jsonList.length()-1)+"]",new TypeReference<List<AcceptContractRegisterList>>(){});
			for (AcceptContractRegisterList acceptContractRegisterList : acceptContractRegisterLists) {
				AcceptContractRegisterList info = new AcceptContractRegisterList();
				info.setFcId(acceptContractRegisterList.getFcId());
				info.setfId_U(acceptContractRegisterList.getfId_U());
				info.setFacpId(acBean.getFacpId());
				info.setcRId(acceptContractRegisterList.getcRId());
				info.setFmName(acceptContractRegisterList.getFmName());
				info.setFacpTime(date);
				if(!"PMMC-4".equals(acBean.getFpItemsName())&&!"PMMC-5".equals(acBean.getFpItemsName())){
					ContractRegisterList contractRegisterList = contractRegisterListMng.findById(acceptContractRegisterList.getcRId());
					int fCheckedNum = contractRegisterList.getfCheckedNum()==null?0:contractRegisterList.getfCheckedNum();
					int fNowCheckedNum = acceptContractRegisterList.getfNowCheckedNum()==null?0:acceptContractRegisterList.getfNowCheckedNum();
					if((fCheckedNum+fNowCheckedNum)>contractRegisterList.getFpNum()){
						throw new ServiceException("验收数量超出采购数量，无法验收！");
					}
					contractRegisterList.setfCheckedNum(fCheckedNum+fNowCheckedNum);
					contractRegisterListMng.saveOrUpdate(contractRegisterList);
					info.setFpNum(acceptContractRegisterList.getFpNum());
					info.setFmModel(acceptContractRegisterList.getFmModel());
					info.setFmSpecif(acceptContractRegisterList.getFmSpecif());
					info.setfCheckedNum(acceptContractRegisterList.getfCheckedNum());
					info.setfNowCheckedNum(acceptContractRegisterList.getfNowCheckedNum());
				}else{
					info.setFmatchRemark(acceptContractRegisterList.getFmatchRemark());
				}
				super.merge(info);
			}
		}
	}
	
	@Override
	public List<AcceptCheck> checkHistory(Integer id) {
		Finder finder = Finder.create(" FROM AcceptCheck WHERE fpId="+id);
		List<AcceptCheck> li = super.find(finder);
		return li;
	}
	
	@Override
	public Pagination ledgerPageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user) {
		//查询已审批、已验收的数据
		/*Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fIsReceive = '9' AND fevalStauts = '1'");*/
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fIsReceive = '9'");
		if(!StringUtil.isEmpty(bean.getFpCode())){//按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){//按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		
		String deptIdStr = departMng.getDeptIdStrByUser(user);
		if("".equals(deptIdStr)){//普通岗位只能查看自己的
			finder.append(" AND fUser = :fUser").setParam("fUser", user.getId());
		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
		 			
		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
		 	finder.append(" AND fDeptId in ("+deptIdStr+")");
		}
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public Pagination checkPageList(AcceptCheck bean, Integer pageNo, Integer pageSize, User user,String searchData) {
		Finder finder = Finder.create(" FROM AcceptCheck WHERE fCheckStauts = '1'");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or fDepartName like '%"+searchData+"%' or facpUsername like '%"+searchData+"%' or TO_DATE(facpTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		/*if(!StringUtil.isEmpty(bean.getFpCode())){//按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){//按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}*/
	 	finder.append(" and fuserId = :fuserId").setParam("fuserId", user.getId());
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void saveCheck(TProcessCheck checkBean, AcceptCheck bean, String files, User user) throws Exception {
		bean = this.findById(bean.getFacpId());
		CheckEntity entity = (CheckEntity)bean;
		
		String checkUrl = "/cgreceive/check?id=";
		String lookUrl = "/cgreceive/detail?id=";
		
		//根据外键关联查询采购申请对象
		PurchaseApplyBasic pabBean = cgApplysqMng.findById(bean.getFpId());
		//业务范围
		String busiArea = "HWCGYS";
		bean = (AcceptCheck) tProcessCheckMng.checkProcess(checkBean, entity, user, busiArea, checkUrl, lookUrl, files);
		
		//当审批不通过时
		if("0".equals(checkBean.getFcheckResult())){
			//设置采购申请的验收状态为已退回
			pabBean.setfIsReceive("-1");
			//保存采购申请信息
			cgApplysqMng.saveOrUpdate(pabBean);
		}
		
		//当审批完全通过时
		if("9".equals(bean.getfCheckStauts())){
			//设置验收状态为已验收
			bean.setfMatchStauts("1");
			bean.setfBillingStauts(0);
			//生成审签数据
			exportCgMng.arrangeCheckDetailReceive(bean, "", null);
		}
		//保存验收信息
		super.saveOrUpdate(bean);
	}

	@Override
	public String reCall(Integer id) throws Exception {
		//根据外键关联查询采购申请对象
		PurchaseApplyBasic pabBean = cgApplysqMng.findById(id);
		
		//根据id查询采购验收对象
		AcceptCheck acBean = cgReceiveMng.findById(id);
		//删除采购验收待办
		personalWorkMng.deleteDb(acBean.getNextCheckUserId(), acBean.getBeanCode(), "0");
		//设置采购验收审批状态为已撤回
		acBean.setfCheckStauts("-4");
		
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="采购验收申请被撤回消息";
		String msg="您待审批的  "+acBean.getFpName() +",任务编号：("+acBean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, acBean.getNextCheckUserId(), "3");
		
		//撤回
		acBean=(AcceptCheck) reCall((CheckEntity) acBean);
		this.updateDefault(acBean);
		return "操作成功";
	}

	@Override
	public Integer findFacpIdByFpId(Integer id) {
		Integer facpId = null;
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT F_ACP_ID FROM T_ACCEPT_CHECK WHERE F_P_ID = "+id);
		SQLQuery query = getSession().createSQLQuery(builder.toString());
		List<Integer> facpIdList = query.list();
		if(facpIdList != null && facpIdList.size() > 0){
			facpId = facpIdList.get(0);
		}
		return facpId;
	}
	
	/**
	 * 验收编号规则：CGYS+年份+部门编号+4位顺序号
	 * @author:赵孟雷
	 * @Title: getFacpCode
	 * @Description: 获取采购验收编码
	 * @return
	 * @return String 返回类型
	 * @date： 2020年6月30日
	 * @throws
	 */
	@Override
	public String getFacpCode(User user){
		Finder finder =Finder.create(" FROM AcceptCheck");
		int num= super.countQueryResult(finder)+1;
		String facpCode="CGYS"+DateUtil.getCurrentYear()+user.getDpcode()+StringUtil.autoGenericCode(num+"",4);
		return facpCode;
	}
	
	@Override
	public Pagination acceptpageList(AcceptCheck bean, int pageNo,
			int pageSize, User user) {
		
		ContractBasicInfo cbi = formulationMng.findById(bean.getFcId());
		
		Finder finder = Finder.create(" FROM AcceptCheck WHERE 1=1");
		if("1".equals(cbi.getfUpdateStatus())){
			Upt upt = uptMng.findByFContId_U(String.valueOf(cbi.getFcId())).get(0);
			if(!StringUtil.isEmpty(String.valueOf(bean.getFcId()))){//合同id
				finder.append(" AND (fcId = :fcId OR fId_U = :fId_U)");
				finder.setParam("fcId", bean.getFcId());
				finder.setParam("fId_U", upt.getfId_U());
			}
		}else{
			if(!StringUtil.isEmpty(String.valueOf(bean.getFcId()))){//合同id
				finder.append(" AND fcId = :fcId");
				finder.setParam("fcId", bean.getFcId());
			}
		}
		if(!StringUtil.isEmpty(bean.getfCheckStauts())){
			finder.append(" AND fCheckStauts = :fCheckStauts");
			finder.setParam("fCheckStauts", bean.getfCheckStauts());
		}
		if(!StringUtil.isEmpty(bean.getFpCode())){//按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){//按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		finder.append(" order by fpId desc");
		Pagination p = super.find(finder, pageNo, pageSize);
    	List<AcceptCheck> li = (List<AcceptCheck>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
    		li.get(x).setFpItemsNames(lookups.getName());
			//序号设置	
			li.get(x).setNum((x+1)+(pageNo-1)*pageSize);	
		}
    	p.setList(li);
		return p;
	}
	
	@Override
	public Integer findFacpIdByFcId(Integer id) {
		Finder finder = Finder.create(" FROM AcceptCheck WHERE fcId="+id+" and fMatchStauts='1' and fStauts!='99'");
		List<AcceptCheck> li = super.find(finder);
		if(li.size()>0){
			return li.size();
		}else{
			return 0;
		}
		
	}
	@Override
	public Pagination cgsqPageReceive(PurchaseApplyBasic bean, int pageNo,
			int pageSize, User user) {
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' AND fbidStauts = 9");
		if(user != null){ //用户
			if(user.getRoleName().contains("实物管理岗")){
				finder.append(" AND fpItemsName in('PMMC-1','PMMC-3')");
			}else{
				finder.append(" AND fDeptId = :fDeptId");
				finder.setParam("fDeptId", user.getDpID());
				finder.append(" AND fpItemsName in('PMMC-2','PMMC-4','PMMC-5')");
			}
		}
		if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){ //按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpMethod())){ //按采购类型查询
			finder.append(" AND fpMethod = :fpMethod");
			finder.setParam("fpMethod", bean.getFpMethod());
		}
		if(!StringUtil.isEmpty(bean.getFbidStauts())){ //按采购登记状态
			finder.append(" AND fbidStauts = :fbidStauts");
			finder.setParam("fbidStauts", bean.getFbidStauts());
		}
		if(!StringUtil.isEmpty(bean.getfDeptName())){ //按采购申请部门模糊查询
			finder.append(" AND fDeptName = :fDeptName");
			finder.setParam("fDeptName", bean.getfDeptName());
		}
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}
}
