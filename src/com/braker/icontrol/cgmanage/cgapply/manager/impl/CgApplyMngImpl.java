package com.braker.icontrol.cgmanage.cgapply.manager.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.EconomicMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgapply.model.BuyInfo;
import com.braker.icontrol.cgmanage.cgcheck.manager.CgCheckMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgBidMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BidRegist;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.purchase.apply.manager.PurchaseIntentionMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 采购申请的service实现类
 * @author 冉德茂
 * @createtime 2018-07-10
 * @updatetime 2018-07-10
 */
@Service
@Transactional
public class CgApplyMngImpl extends BaseManagerImpl<PurchaseApplyBasic> implements CgApplysqMng {
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private CgConPlanMng cgConfPlanMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private CgCheckMng cgcheckMng;
	@Autowired
	private EconomicMng economicMng;
	@Autowired
	private CgBidMng cgBidMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TNodeDataMng tNodeDataMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private PrivateInforMng privateInforMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private PurchaseIntentionMng purchaseIntentionMng;
	
	@Autowired
	private ShenTongMng shenTongMng;
	
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@Override
	public Pagination pageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData) {		
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99'");
		if(user != null){ //用户
			finder.append(" AND fUser = :fUser");
			finder.setParam("fUser", user.getId());
			finder.append(" AND fDeptId = :fDeptId");
			finder.setParam("fDeptId", user.getDpID());
		}
		/*if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND concat(fpItemsName) like '%"+searchData+"%' ");
		}*/
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%' or fbidStauts like '%"+searchData+"%') ");
		}
		/*if(!StringUtil.isEmpty(bean.getIndexCode())){ //按支出科目编码查询
			finder.append(" AND indexCode = :indexCode");
			finder.setParam("indexCode", "'"+bean.getIndexCode()+"'");
		}
		if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpItemsName())){ //按品目查询
			finder.append(" AND fpItemsName =:fpItemsName");
			finder.setParam("fpItemsName", bean.getFpItemsName());
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
		}*/
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	
	@Override
	public Pagination executioncgsqPage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData) {		
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' and fCheckStauts=9 and fIsImpFiles='0'");
		/*if(user != null){ //用户
			finder.append(" AND fUser = :fUser");
			finder.setParam("fUser", user.getId());
			finder.append(" AND fDeptId = :fDeptId");
			finder.setParam("fDeptId", user.getDpID());
		}*/
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or amount like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or fUserName like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		/*if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
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
		if(!StringUtil.isEmpty(bean.getFpItemsName())){ //按品目名称查询
			finder.append(" AND fpItemsName = :fpItemsName");
			finder.setParam("fpItemsName", bean.getFpItemsName());
		}
		if(!StringUtil.isEmpty(bean.getFbidStauts())){ //按采购登记状态
			finder.append(" AND fbidStauts = :fbidStauts");
			finder.setParam("fbidStauts", bean.getFbidStauts());
		}*/
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 根据id删除
	 * @author 冉德茂
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	@Override
	public void delete(Integer id,String fId,User user) {
		//更改采购的显示状态
		if(fId!=null){
			personalWorkMng.deleteById(Integer.valueOf(fId));
		}
		PurchaseApplyBasic bean = super.findById(id);
		bean.setfStauts("99");
		if(bean.getAmount() >= 500000){
			PurchaseIntentionBasic p = purchaseIntentionMng.findByCode(bean.getOpenObjCode()).get(0);
			p.setIsUsed("0");
			purchaseIntentionMng.saveOrUpdate(p);
		}
		super.saveOrUpdate(bean);
		personalWorkMng.sendMessageToUser(user.getId(), 0);
	}

	/*
	 * 保存采购计划申请信息
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)//事务处理
	public void save(PurchaseApplyBasic bean, String files,String jzyjfiles, String mingxi, User user, String czbmyjfiles,String hyzgbmyjfiles,String reasonIds) throws Exception {
		Date date = new Date();
		if (bean.getFpId()==null) {
			//创建人、创建时间、发布时间  设置验收状态
			bean.setCreator(user.getName());
			bean.setCreateTime(date);
			bean.setfDeptId(user.getDpID());
			bean.setfDeptName(user.getDepartName());
			bean.setfUser(user.getId());
			bean.setfReqTime(date);
			bean.setBuyInfos(reasonIds);
			Integer seq = shenTongMng.getSeq("purchase_apply_basic_seq");
			bean.setFpId(seq);
			bean.setIsUsed("0");
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getName());
			bean.setUpdateTime(date);
			bean.setfReqTime(date);
			bean.setBuyInfos(reasonIds);
			bean.setfDeptId(user.getDpID());
			bean.setfDeptName(user.getDepartName());
			bean.setIsUsed("0");
		}
		//保存采购方式   组织形式   评标方法   为字典表类型
//		bean.setfEvaMethod(economicMng.findLookupsByCode(bean.getfEvaMethod().getCode(), "BID_METHOD"));
		/*bean.setfOrgType(economicMng.findLookupsByCode(bean.getfOrgType().getCode(), "CGORG_TYPE"));
		if(bean.getfOrgType().getCode().equals("CGORG_TYPE_1")){
			bean.setFpMethod(economicMng.findLookupsByCode(bean.getFpMethod().getCode(), "JZCGFS"));
		}else{
			bean.setFpMethod(economicMng.findLookupsByCode(bean.getFpMethod().getCode(), "FSCGFS"));
		}*/
		
		//设置默认状态
		//bean.setFbidStauts("0");//登记状态   0-暂存		1-待登记		9-已登记		-1-已退回	-4-已撤回
		bean.setfIsReceive("0");//验收状态   0-暂存		1-待验收		9-已验收		-1-已退回	-4-已撤回
		bean.setFevalStauts("0");//评价状态	0-未评价		1-已评价
		bean.setfConfirmStauts("0");//采购方式确认方式状态 0-未确认 1-已确认
		
		/*bean.setfIsInquiry("0");//默认询价状态   0 未询价    1已询价   询价状态   焦广兴更改为 是否论证状态 
		bean.setFpayStauts("0");//付款的审批状态默认0   验收通过发起申请 通过后变为9
		bean.setFtendingStauts("0");//招标状态  10.23更改需求  不允许流标
*/		
		//以下为工作流的节点配置（如果点送审则设置进入工作流1审核中）
		if("1".equals(bean.getfCheckStauts())){
			//采购送审时冻结金额
			Double syAmount = budgetIndexMgrMng.addDjAmount(bean.getIndexId(), bean.getProDetailId(), bean.getAmount());
			//采购送审时 修改意向公开单是否被使用状态
			if(bean.getAmount() >= 500000){
				PurchaseIntentionBasic p = purchaseIntentionMng.findByCode(bean.getOpenObjCode()).get(0);
				p.setIsUsed("1");
				purchaseIntentionMng.saveOrUpdate(p);
			}
			User nextUser;
			if("1".equals(bean.getfIsPers())){//涉密采购
				//得到第一个审批节点key
				Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "SMCGSQ", user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("SMCGSQ", user.getDpID());
				Integer flowId = processDefin.getFPId();
				TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser = userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号 get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
				//把历史审批记录全部设置为1，表示重新审批
				tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			}else{
				//得到第一个审批节点key
				Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "CGSQ", user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", user.getDpID());
				Integer flowId = processDefin.getFPId();
				TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser = userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号 get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
				//把历史审批记录全部设置为1，表示重新审批
				tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			}
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			Integer seq1 = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq1);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getFpId());//申请单ID
			work.setTaskCode(bean.getFpCode());//为申请单的单号
			work.setTaskName("[采购申请]"+bean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/cgcheck/check?id="+bean.getFpId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/cgsqsp/detail?id="+bean.getFpId());//查看url
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
			work2.setTaskId(bean.getFpId());//申请单ID
			work2.setTaskCode(bean.getFpCode());//为申请单的单号
			work2.setTaskName("[采购申请]"+bean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/cgsqsp/edit?id="+bean.getFpId());//修改url
			work2.setUrl1("/cgsqsp/detail?id="+bean.getFpId());//查看url
			work2.setUrl2("/cgsqsp/delete?id="+bean.getFpId());//删除url
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
		bean = (PurchaseApplyBasic) super.saveOrUpdate(bean); //新增
		
		//删除数据库中   申请中的公车运维费用明细
		getSession().createSQLQuery("delete from T_PROCUREMENT_PLAN_LIST where F_P_ID ="+bean.getFpId()+" ").executeUpdate();
		if(!StringUtil.isEmpty(mingxi)){
			//获取公车运维费用明细的Json对象
			List<ProcurementPlanList> procurementPlanLists = JSON.parseObject("["+mingxi.toString().substring(0, mingxi.length()-1)+"]",new TypeReference<List<ProcurementPlanList>>(){});
			for (ProcurementPlanList procurementPlanList : procurementPlanLists) {
				ProcurementPlanList info = new ProcurementPlanList();
				Integer seq3 = shenTongMng.getSeq("PROCUREMENT_PLAN_LIST_SEQ");
				info.setMainId(seq3);
				info.setFpId(bean.getFpId());
				info.setFpKind(procurementPlanList.getFpKind());
				info.setFpurName(procurementPlanList.getFpurName());
				info.setFnum(procurementPlanList.getFnum());
				info.setFmeasureUnit(procurementPlanList.getFmeasureUnit());
				info.setfIsImp(procurementPlanList.getfIsImp());
				info.setFunitPrice(procurementPlanList.getFunitPrice());
				info.setFsumMoney(procurementPlanList.getFsumMoney());
				info.setFcommProp(procurementPlanList.getFcommProp());
				
				super.merge(info);
			}
		}
		
		//保存附件信息
		attachmentMng.joinEntity(bean, files);
		attachmentMng.joinEntity(bean, jzyjfiles);
		if("1".equals(bean.getfIsImp())){
		attachmentMng.joinEntity(bean, czbmyjfiles);
		attachmentMng.joinEntity(bean, hyzgbmyjfiles);
		}
		//throw new ServiceException("aaaaa");
	}
	
	/*
	 * 采购品目明细查询
	 * @author 冉德茂
	 * @createtime 2018-07-13
	 * @updatetime 2018-07-13
	 */
	public List<Object> getMingxi(String tableName ,String idName ,Integer pid) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "="+pid);
		return super.find(finder);
	}
	
	
	/*
	 * 获取明细的Json对象
	 * @author 冉德茂
	 * @createtime 2018-07-14
	 * @updatetime 2018-07-14
	 */
	public List getMingXiJson(String mingxi, Class tableClass) {
		//获取明细的Json对象
		List mx = null;
		JSONArray array =JSONArray.fromObject("["+mingxi.toString()+"]");
		mx = (List)JSONArray.toList(array, tableClass);
		return mx;
	}


	
	
	/*
	 * 审批台账分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	@Override
	public Pagination ledgerPageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData) {
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' and fCheckStauts='9' ");	
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or fUserName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		/*if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){ //按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}*/
		
		//权限
		String deptIdStr = departMng.getDeptIdStrByUser(user);
 		if("".equals(deptIdStr)) { //普通岗位只能查看自己的
 			finder.append(" and fUser = :fUser").setParam("fUser", user.getId());
 		}else if("all".equals(deptIdStr)) {//校长可以查看所有人的
 			
 		}else {//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门（设备与安技处除外）
 			finder.append(" and fDeptId in ("+deptIdStr+")");
 		}
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 根据pid查询采购批次编号
	 * @author 冉德茂
	 * @createtime 2018-07-24
	 * @updatetime 2018-07-24
	 */
	@Override
	public List<PurchaseApplyBasic> getCodeById(Integer id) {		
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fpId='"+id+"'");	
		return super.find(finder);
	}

	/*
	 * 询比价登记查看采购基本信息
	 * @author 冉德茂
	 * @createtime 2018-08-01
	 * @updatetime 2018-08-01
	 * 
	 */
	@Override
	public Pagination qingdanpageList(PurchaseApplyBasic bean, Integer page,Integer rows, User user) {		
		Finder finder =Finder.create(" FROM PurchaseApplyBasic WHERE fCheckStauts="+9+" AND fStauts <>"+99+" ");
		if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){ //按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		finder.append(" order by fReqTime desc");
		return super.find(finder,page, rows);
	}

	@Override
	public PurchaseApplyBasic findbyfpCode(String fpCode) {
		Finder finder=Finder.create(" from PurchaseApplyBasic where fpCode='"+fpCode+"'");
		return (PurchaseApplyBasic) super.find(finder).get(0);
	}
	
	/*
	 * 根据采购批次id获取合同金额
	 * @author 李安达
	 * @createtime 2018-11-29
	 * @updatetime 2018-11-29
	 * 
	 */
	@Override
	public String findFpAmountbyfpId(String fpId) {
		
		Finder finder=Finder.create(" from BidRegist where fpId="+fpId+"");
		List<BidRegist> list=super.find(finder);
		if(list !=null && list.size()>0){
			BidRegist bidRegist=list.get(0);
			return bidRegist.getFbidAmount();
		}
		return "";
	}

	@Override
	public String reCall(Integer id) throws Exception {
		//根据id查询对象
		PurchaseApplyBasic bean = (PurchaseApplyBasic)super.findById(id);
		
		//归还冻结金额
		Double syAmount = budgetIndexMgrMng.deleteDjAmount(bean.getIndexId(), bean.getProDetailId(), bean.getAmount());
		
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId(), bean.getBeanCode(), "0");
		
		//给待审批人推送消息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "采购申请被撤回消息";
		String msg = "您待审批的  "+bean.getFpName()+",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean = (PurchaseApplyBasic) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "操作成功";
	}

	@Override
	public void executionSave(PurchaseApplyBasic bean, String czbmyjfiles,
			String hyzgbmyjfiles) throws Exception {
		PurchaseApplyBasic oldbeasn = super.findById(bean.getFpId());	
		if("1".equals(oldbeasn.getfIsImp())){//是进口商品
			attachmentMng.joinEntity(oldbeasn, czbmyjfiles);
			attachmentMng.joinEntity(oldbeasn, hyzgbmyjfiles);
		}
		oldbeasn.setfIsImpFiles("1");
		super.saveOrUpdate(oldbeasn);
	}


	@Override
	public Pagination contractcgsqPage(PurchaseApplyBasic bean, int pageNo,
			int pageSize, User user) {
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' AND fbidStauts = 9 and isUsed = '0'");
		if(user != null){ //用户
			finder.append(" AND fUser = :fUser");
			finder.setParam("fUser", user.getId());
			finder.append(" AND fDeptId = :fDeptId");
			finder.setParam("fDeptId", user.getDpID());
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

	/**
	 * 台账采购验收分页查询
	 * @author 赵孟雷
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @createtime 2020年7月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年7月10日
	 */
	@Override
	public Pagination ledgerReceiveList(AcceptCheck bean, int pageNo,
			int pageSize, User user) {
		Finder finder = Finder.create(" FROM AcceptCheck WHERE fStauts!='99'");
		if(!StringUtil.isEmpty(String.valueOf(bean.getFpId()))){//按采购申请ID查询
			finder.append(" AND fpId LIKE :fpId");
			finder.setParam("fpId", bean.getFpId());
		}
		finder.append(" order by facpTime desc");
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public List<BuyInfo> cgsqspJsonList(String parentCode,String amount) {
		Finder finder = Finder.create(" FROM BuyInfo WHERE status ='1' ");
		if(StringUtil.isEmpty(parentCode)){
			if(Double.parseDouble(amount) >= 500000){
				finder.append(" and id = 1");
			}else if(Double.parseDouble(amount) >= 200000 && Double.parseDouble(amount) < 500000){
				finder.append(" and (id = 1 or id = 2 or id = 3)");
			}else{
				finder.append(" and (id = 1 or id = 3)");
			}
		}else{
			finder.append(" and parentId ="+parentCode);
		}
		finder.append(" order by id");
		return super.find(finder);
	}


	@Override
	public List<BuyInfo> findByBuyId(Integer id) {
		Finder finder = Finder.create(" FROM BuyInfo WHERE status ='1' and pid = "+id);
		return super.find(finder);
	}


	@Override
	public List<BuyInfo> findByText(String fpPype) {
		Finder finder = Finder.create(" FROM BuyInfo WHERE status ='1' and name = '"+fpPype+"'");
		return super.find(finder);
	}


	@Override
	public Pagination pubPageList(PurchaseIntentionBasic bean, Integer page, Integer rows, User user,
			String searchData) {
		Finder finder = Finder.create(" FROM PurchaseIntentionBasic WHERE publicStatus = '1' AND amount >= 500000 AND isUsed = '0'");
		
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fIntentionCode like '%"+searchData+"%' or fPurchaseName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		finder.append(" and fUser =" +user.getId());
		finder.append(" order by fReqTime desc");
		return super.find(finder, page, rows);
	}
	
	
	@Override
	public Pagination cgQusetionPage(PurchaseApplyBasic bean, Integer page,
			Integer rows, String searchData){
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fCheckStauts = '9' AND fbidStauts ='9' and fpId in (select fpId from PurchasingQuery where fStauts <>'99' )");

		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or fpPype like '%"+searchData+"%' or fpMethod like '%"+searchData+"%' or fbidAmount like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or fUserName like '%"+searchData+"%' or fOrgName like '%"+searchData+"%') ");
		}

		finder.append(" order by fReqTime desc");
		return super.find(finder, page, rows);
	}
	
	@Override
	public Pagination chooseCgPage(PurchaseApplyBasic bean, Integer page,
			Integer rows, String searchData){
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fCheckStauts = '9' AND fbidStauts ='9'");

		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or fpPype like '%"+searchData+"%' or fpMethod like '%"+searchData+"%' or fbidAmount like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or fUserName like '%"+searchData+"%' or fOrgName like '%"+searchData+"%') ");
		}

		finder.append(" order by fReqTime desc");
		return super.find(finder, page, rows);
	}
	
	@Override
	public Pagination planPageList(Integer page, Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE needsStatus = '9' AND fpMethod in('政府采购', '委托代理机构采购')");
//		if (user != null) { //用户
//			finder.append(" AND fUser = :fUser");
//			finder.setParam("fUser", user.getId());
//			finder.append(" AND fDeptId = :fDeptId");
//			finder.setParam("fDeptId", user.getDpID());
//		}
		
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or amount like '%"+searchData+"%') ");
		}
		
		finder.append(" order by planStatus nulls first,fReqTime desc");
		return super.find(finder, page, rows);
	}
	/**
	 * 分页采购文件查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	public Pagination cgFilePage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData){
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' and ((fpMethod = '委托代理机构采购' and needsStatus='9') or (fpMethod = '政府采购' and planStatus='9'))");//
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or ifnull(fpName,'') like '%"+searchData+"%' or ifnull(fpMethod,'') like '%"+searchData+"%' or ifnull(amount,'') like '%"+searchData+"%' or ifnull(fAgencyName,'') like '%"+searchData+"%' or ifnull(fUserName,'') like '%"+searchData+"%' or ifnull(processLeaderName,'') like '%"+searchData+"%') ");
		}
		finder.append(" order by filesUploadSts nulls first desc");
		return super.find(finder, pageNo, pageSize);
	}
	/**
	 * 分页采购文件确认
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	public Pagination cgFileAffirmPage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData){
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' and filesUploadSts in('2','9')");//
		if(user != null){ //用户
			finder.append(" AND fUser = :fUser");
			finder.setParam("fUser", user.getId());
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or ifnull(fpName,'') like '%"+searchData+"%' or ifnull(fpMethod,'') like '%"+searchData+"%' or ifnull(amount,'') like '%"+searchData+"%' or ifnull(fAgencyName,'') like '%"+searchData+"%' or ifnull(fUserName,'') like '%"+searchData+"%' or ifnull(processLeaderName,'') like '%"+searchData+"%') ");
		}
		finder.append(" order by filesUploadSts,fpId desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	/**
	 * 采购文件上传的保存
	 * @param bean
	 * @param files
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	public void saveFileUp(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile,User user) throws Exception{
		PurchaseApplyBasic beans = findById(bean.getFpId());
		if("2".equals(bean.getFilesUploadSts()) || "1".equals(bean.getFilesUploadSts())){
			//保存附件信息
			attachmentMng.joinEntity(beans, files);
		}
		if("9".equals(bean.getFilesUploadSts()) || "5".equals(bean.getFilesUploadSts())){
			//添加流程审批记录
			TNodeData node = new TNodeData();
			node.setRoleName("采购经办人");
			node.setRoleId("d0f606dd-7aca-11e9-8688-005056a17ba3");
			node.setKeyId(0);
			tProcessCheckMng.addCheckHistory(checkBean,Integer.valueOf("-100"), beans.getBeanCode(), user.getId(),0,user, node,spjlFile);
		}
		beans.setFilesUploadSts(bean.getFilesUploadSts());
		saveOrUpdate(beans);
	}


	@Override
	public Pagination cgRegisterAffirmPage(PurchaseApplyBasic bean, int pageNo,
			int pageSize, User user, String searchData,String index) {
		Finder finder = null;
		if("1".equals(index)){
			finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' and fbidStauts='9' and (conPutOnRecordsSts in('2') or checkPutOnRecordsSts in('2'))");
		}else{
			finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' and fbidStauts='9' and (conPutOnRecordsSts in('9') and checkPutOnRecordsSts in('9'))");
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or ifnull(fpName,'') like '%"+searchData+"%' or ifnull(fpMethod,'') like '%"+searchData+"%' or ifnull(fpPype,'') like '%"+searchData+"%' or ifnull(fOrgName,'') like '%"+searchData+"%' or ifnull(fbidAmount,'') like '%"+searchData+"%' or ifnull(fDeptName,'') like '%"+searchData+"%' or ifnull(fUserName,'') like '%"+searchData+"%') ");
		}
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public Pagination cgRegisterPage(PurchaseApplyBasic bean, int pageNo,
			int pageSize, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fStauts <> '99' and fbidStauts='9'");
		if(user != null){ //用户
			finder.append(" AND fUser = :fUser");
			finder.setParam("fUser", user.getId());
		}
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or ifnull(fpName,'') like '%"+searchData+"%' or ifnull(fpMethod,'') like '%"+searchData+"%' or ifnull(fpPype,'') like '%"+searchData+"%' or ifnull(fOrgName,'') like '%"+searchData+"%' or ifnull(fbidAmount,'') like '%"+searchData+"%' or ifnull(fDeptName,'') like '%"+searchData+"%' or ifnull(fUserName,'') like '%"+searchData+"%') ");
		}
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public void saveContractSts(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile,User user)
			throws Exception {
		PurchaseApplyBasic beans = findById(bean.getFpId());
		if("2".equals(bean.getConPutOnRecordsSts()) || "1".equals(bean.getConPutOnRecordsSts())){
			//保存附件信息
			attachmentMng.joinEntity(beans, files);
		}
		if("9".equals(bean.getConPutOnRecordsSts()) || "5".equals(bean.getConPutOnRecordsSts())){
			//添加流程审批记录
			TNodeData node = new TNodeData();
			node.setRoleName("采购经办人");
			node.setRoleId("d0f606dd-7aca-11e9-8688-005056a17ba3");
			node.setKeyId(0);
			tProcessCheckMng.addCheckHistory(checkBean,Integer.valueOf("-101"), beans.getBeanCode(), user.getId(),0,user, node,spjlFile);
		}
		beans.setConPutOnRecordsSts(bean.getConPutOnRecordsSts());
		saveOrUpdate(beans);
	}
	
	@Override
	public void saveCheckSts(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile,User user)
			throws Exception {
		PurchaseApplyBasic beans = findById(bean.getFpId());
		if("2".equals(bean.getCheckPutOnRecordsSts()) || "1".equals(bean.getCheckPutOnRecordsSts())){
			//保存附件信息
			attachmentMng.joinEntity(beans, files);
		}
		if("9".equals(bean.getCheckPutOnRecordsSts()) || "5".equals(bean.getCheckPutOnRecordsSts())){
			//添加流程审批记录
			TNodeData node = new TNodeData();
			node.setRoleName("采购经办人");
			node.setRoleId("d0f606dd-7aca-11e9-8688-005056a17ba3");
			node.setKeyId(0);
			tProcessCheckMng.addCheckHistory(checkBean,Integer.valueOf("-102"), beans.getBeanCode(), user.getId(),0,user, node,spjlFile);
		}
		beans.setCheckPutOnRecordsSts(bean.getCheckPutOnRecordsSts());
		saveOrUpdate(beans);
	}


	@Override
	public List<PurchaseApplyBasic> findByCondition(String currentDate) {
		Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fpCode like 'CG" + currentDate + "%'");
		return super.find(finder);
	}
}
