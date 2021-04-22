package com.braker.icontrol.cgmanage.cgprocess.manager.impl;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import oracle.net.aso.r;

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
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanListMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgBidWinRefMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BidRegist;
import com.braker.icontrol.cgmanage.cgprocess.model.BidWinningRef;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.cgmanage.cgsupplier.manager.SupplierMng;
import com.braker.icontrol.cgmanage.cgsupplier.manager.SupplierTransactionHisMng;
import com.braker.icontrol.cgmanage.cgsupplier.model.SupplierTransactionHis;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.expend.apply.model.OutsideTrafficInfo;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;


/**
 * 采购过程登记的service实现类
 * @author 冉德茂
 * @createtime 2018-07-23
 * @updatetime 2018-07-23
 */
@Service
@Transactional
public class CgProcessMngImpl extends BaseManagerImpl<BiddingRegist> implements CgProcessMng {
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private CgApplysqMng cgApplysqMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private CgBidWinRefMng bwrefMng;
	@Autowired
	private SupplierMng supplierMng;
	@Autowired
	private SupplierTransactionHisMng supplierTransMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private RoleMng roleMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private RegisterApplyMng registerApplyMng;
	@Autowired
	private CgConPlanListMng CgConPlanListMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProExpendDetailMng tProExpendDetailMng;
	@Autowired
	private ShenTongMng shenTongMng;
	@Override
	public Pagination pageList(PurchaseApplyBasic bean,User user, int pageNo, int pageSize,String searchData) {
		String sql = "select p.F_P_ID,r.F_R_ID,p.F_P_CODE,p.F_P_NAME,p.F_P_ITEMS_NAME,p.F_AMOUNT,"
				+ "r.F_USER_NAME,r.F_DEPT_NAME,r.F_REQ_TIME,p.F_BID_STAUTS from t_purchase_apply_basic p left join t_register_apply_basic r"
				+ " on p.F_P_ID = r.F_P_ID WHERE p.F_CHECK_STAUTS ='9'";
		if(!StringUtil.isEmpty(searchData)){
			sql +=" AND (p.F_P_CODE like '%"+searchData+"%' or p.F_P_NAME like '%"+searchData+"%' or p.F_AMOUNT like '%"+searchData+"%' or r.F_USER_NAME like '%"+searchData+"%' or r.F_DEPT_NAME like '%"+searchData+"%') ";
		}
				/*if(!StringUtil.isEmpty(bean.getFpCode())){
					sql += " and p.F_P_CODE like '%"+bean.getFpCode()+"%'";
				}
				if(!StringUtil.isEmpty(bean.getFpName())){
					sql += " and p.F_P_NAME like '%"+bean.getFpName()+"%'";
				}*/
				
				if(user.getRoleName().contains("采购管理岗")){
					sql += " and p.F_FILES_UPLOAD_STS = '9' ";
				}else{
					String deptIdStr=departMng.getDeptIdStrByUser(user);
			 		if("".equals(deptIdStr)){ //普通岗位只能查看自己部门申请的
			 			sql += " and p.F_DEPT_ID = '"+user.getDepart().getId()+"'";
			 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
			 			
			 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
			 			sql += " and p.F_DEPT_ID in ("+deptIdStr+")";
			 		}
			 		sql += " and p.F_P_METHOD ='自行采购'";
				}
				
				/*if(!StringUtil.isEmpty(bean.getFpItemsName())){
					sql += " and p.F_P_ITEMS_NAME='"+bean.getFpItemsName()+"'";
				}*/
				sql += " order by p.F_BID_STAUTS nulls first,p.F_UPDATE_TIME desc;";
				SQLQuery query = getSession().createSQLQuery(sql);
				List<Object[]> list = query.list();
				List<PurchaseApplyBasic> list2 = new ArrayList<PurchaseApplyBasic>();
				for (int i = 0; i < list.size(); i++) {
					PurchaseApplyBasic applyBasic = new PurchaseApplyBasic();
					applyBasic.setFpId(Integer.valueOf(String.valueOf(list.get(i)[0])));
					if("null".equals(String.valueOf(list.get(i)[1]))){
						applyBasic.setFrId(null);
					}else{
						applyBasic.setFrId(Integer.valueOf(String.valueOf(list.get(i)[1])));
					}
					
					applyBasic.setFpCode(String.valueOf(list.get(i)[2]));
					applyBasic.setFpName(String.valueOf(list.get(i)[3]));
					applyBasic.setFpItemsName(String.valueOf(list.get(i)[4]));
					applyBasic.setAmount(Double.valueOf(String.valueOf(list.get(i)[5])));
					if("null".equals(String.valueOf(list.get(i)[6]))){
						applyBasic.setfUserName("");
					}else{
						applyBasic.setfUserName(String.valueOf(list.get(i)[6]));
					}
					if("null".equals(String.valueOf(list.get(i)[7]))){
						applyBasic.setfDeptName("");
					}else{
						applyBasic.setfDeptName(String.valueOf(list.get(i)[7]));
					}
					
					applyBasic.setfReqTime(DateUtil.parseDate(String.valueOf(list.get(i)[8])));
					if("null".equals(String.valueOf(list.get(i)[9]))){
						applyBasic.setFbidStauts("");
					}else{
						applyBasic.setFbidStauts(String.valueOf(list.get(i)[9]));
					}
					
					list2.add(applyBasic);
				}
				Pagination pagination = new Pagination();
				pagination.setList(list2);
		
		return pagination;		
		/*Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fCheckStauts = '9' and fIsImpFiles='1'");	
		if(!StringUtil.isEmpty(bean.getFpCode())){//按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){//按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFbidStauts())){//按登记状态查询
			finder.append(" AND fbidStauts = :fbidStauts");
			finder.setParam("fbidStauts", bean.getFbidStauts());
		}
		if(!StringUtil.isEmpty(bean.getFpItemsName())){//按品目名称
			finder.append(" AND fpItemsName = :fpItemsName");
			finder.setParam("fpItemsName", bean.getFpItemsName());
		}
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);*/
	}
	
/*	@Override
	public void save(BiddingRegist bean, WinningBidder bean2, String files, User user) {
		bean.setFstatus("0");	//新增和修改  数据的删除状态都是0
		Date date = new Date();	//当前时间
		
		if (bean.getFbId()==null) {//新增	暂存部分信息至供应商表
			//采购过程登记
			//创建人、创建时间、发布时间  设置验收状态
			bean.setCreator(user.getName());
			bean.setCreateTime(date);
			
			//供应商
			//创建人、创建时间、发布时间  设置验收状态
			bean2.setFwCode("GYS"+StringUtil.random8());
			bean2.setCreator(user.getName());
			bean2.setCreateTime(date);
			bean2.setfRecUser(user.getName());
			bean2.setfRecUserId(user.getId());
			bean2.setfRecDept(user.getDepartName());
			bean2.setfRecDeptId(user.getDpID());
			bean2.setfRecTime(date);
			//设置供应商的黑名单和数据的删除状态
			bean2.setFisBlackStatus("0");//黑名单默认审批状态
			bean2.setFisOutStatus("0");//出库默认审批状态
			bean2.setFisBlack("0");//默认0  拉黑为1
			bean2.setFstauts("1");//默认1  删除改为99
			bean2.setFaccFreq(0);//新增  默认拉黑次数是0
			bean2.setFcheckType("in");//设置审批状态	in-入库
			bean2.setCheckStauts("0");//设置审批状态	0-暂存
			//自动生成编号
			String str="FW";
			String fwCode = StringUtil.Random(str);
			bean2.setFwCode(fwCode);
			
			//保存基本信息(供应商)
			bean2 =(WinningBidder) super.saveOrUpdate(bean2);	
			Integer fwId = bean2.getFwId();//获取主键赋值给成交记录的外键
			
			//保存基本信息(采购过程登记)
			bean.setFwId(fwId);//采购登记表中保存外键
			bean = (BiddingRegist) super.saveOrUpdate(bean);
			Integer fbId = bean.getFbId();//获取主键赋值给成交记录的外键
			
			//保存成交记录信息
			SupplierTransactionHis suptrbean = new SupplierTransactionHis();
			suptrbean.setFwId(fwId);//供应商id
			suptrbean.setFbIdId(fbId);//唯一标识   过程登记主键id
			suptrbean.setFtrTime(bean.getCreateTime());//成交时间
			suptrbean.setFsupCode(bean2.getFwCode());//供应商编码
			suptrbean.setFsupName(bean2.getFwName());//中标组织名称（供应商名称）
			suptrbean = (SupplierTransactionHis) super.saveOrUpdate(suptrbean);	
			
			//获取主键  保存外键
			BidWinningRef bwbean = new BidWinningRef();
			//保存过程登记和供应商映射信息
			bwrefMng.save(bwbean, fbId, fwId);
			
			 //如果采购验收不从首页送审，采购过程推的待办也会一直存在不变，再从首页这条数据报错    汪耀
			 * 
			 * //添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(cgsqbean.getfUser());//任务处理人ID既是下节点处理人ID
			work.setTaskId(Integer.valueOf(cgsqbean.getBeanId()));//申请单ID
			work.setTaskCode(cgsqbean.getBeanCode());//为申请单的单号
			work.setTaskName("[采购验收]"+cgsqbean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/cgreceive/receive?id="+cgsqbean.getBeanId());//审批url
			work.setUrl1("/cgsqsp/receiveledgedetail?id="+cgsqbean.getBeanId());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setBeforeUser(user.getName());//任务提交人姓名
			*//**----------------------------------------------------------------**//*
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			*//**----------------------------------------------------------------**//*
			work.setBeforeTime(date);//任务提交时间
			personalWorkMng.merge(work);
		} else {//修改
			//采购过程登记
			//修改人、修改时间
			bean.setUpdator(user.getName());
			bean.setUpdateTime(date);
			
			//通过供应商名称查询供应商
			WinningBidder bean3 = supplierMng.findByName(bean2.getFwName());
			if(bean3.getFwId() != null){	//供应商已存在
				//供应商
				//修改人、修改时间
				bean2.setUpdator(user.getName());
				bean2.setUpdateTime(date);
				bean2.setfRecUser(user.getName());
				bean2.setfRecUserId(user.getId());
				bean2.setfRecDept(user.getDepartName());
				bean2.setfRecDeptId(user.getDpID());
				
				//保存基本信息(供应商)
				bean2 = (WinningBidder) super.merge(bean2);
				//保存基本信息(采购过程登记)
				bean.setFwId(bean2.getFwId());
				bean = (BiddingRegist) super.merge(bean);
				
				//清除供应商的信息
				//获得已经存在的供应商信息明细
				List<Object> oldwinner = getMingxi("BidWinningRef", "fbIdId", bean.getFbId());
				//删除已存在中标和供应商的映射信息
				for (int i = oldwinner.size()-1; i >= 0; i--) {
					BidWinningRef oldgad = (BidWinningRef) oldwinner.get(i);
					super.delete(oldgad);
				}
			}else {
				//供应商不存在-新增供应商并暂存
				//将传上来的供应商id改为空，否则会一直修改原供应商
				bean2.setFwId(null);
				
				//供应商
				//创建人、创建时间、发布时间  设置验收状态
				bean2.setFwCode("GYS"+StringUtil.random8());;
				bean2.setCreator(user.getName());
				bean2.setCreateTime(date);
				bean2.setfRecUser(user.getName());
				bean2.setfRecUserId(user.getId());
				bean2.setfRecDept(user.getDepartName());
				bean2.setfRecDeptId(user.getDpID());
				bean2.setfRecTime(date);
				//设置供应商的黑名单和数据的删除状态
				bean2.setFisBlackStatus("0");//黑名单默认审批状态
				bean2.setFisOutStatus("0");//出库默认审批状态
				bean2.setFisBlack("0");//默认0  拉黑为1
				bean2.setFstauts("1");//默认1  删除改为99
				bean2.setFaccFreq(0);//新增  默认拉黑次数是0
				bean2.setFcheckType("in");//设置审批状态	in-入库
				bean2.setCheckStauts("0");//设置审批状态	0-暂存
				//自动生成编号
				String str="FW";
				String fwCode = StringUtil.Random(str);
				bean2.setFwCode(fwCode);
				
				//保存基本信息(供应商)
				bean2 = (WinningBidder) super.saveOrUpdate(bean2);
				
				//保存基本信息(采购过程登记)
				bean.setFwId(bean2.getFwId());
				bean = (BiddingRegist) super.saveOrUpdate(bean);
			}
			//保存供应商历史成交记录
			SupplierTransactionHis trbean = supplierTransMng.getTrbyBidid(bean.getFbId());
			trbean.setFwId(bean2.getFwId());//供应商id
			trbean.setFbIdId(bean.getFbId());//唯一标识   过程登记主键id
			trbean.setFtrTime(bean.getCreateTime());//成交时间
			trbean.setFsupCode(bean2.getFwCode());//供应商编码
			trbean.setFsupName(bean2.getFwName());//中标组织名称（供应商名称）
			trbean = (SupplierTransactionHis) super.saveOrUpdate(trbean);	
		
			//保存新的中标和供应商映射信息
			BidWinningRef bwbean = new BidWinningRef();
			bwrefMng.save(bwbean, bean.getFbId(), bean.getFwId());
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
	}*/
	
	@Override
	public List<Object> getMingxi(String tableName, String idName, Integer pid) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "="+pid);
		return super.find(finder);
	}
	
	@Override
	public void save(RegisterApplyBasic rBean, PurchaseApplyBasic pabBean,User user,String jsonList,String supJsonList,String files01,String files02,String files03,String files04,String files05) throws Exception {
		Date date = new Date();
		if(rBean.getFrId() == null){
			//创建人、创建时间
			rBean.setCreator(user.getName());
			rBean.setCreateTime(date);
			rBean.setfUser(user.getId());
			//登记人、登记部门、登记时间
			rBean.setfUserName(user.getName());
			rBean.setfDeptID(user.getDepart().getId());
			rBean.setfDeptName(user.getDepart().getName());
			rBean.setfReqTime(new Date());
			//自动生成编号
			String str="CGDJ";
			String fbiddingCode = StringUtil.Random(str);
			rBean.setFbiddingCode(fbiddingCode);
			
			//设置采购申请表与采购登记表的外键关联
			rBean.setFpId(pabBean.getFpId());
			Integer seq = shenTongMng.getSeq("REGISTER_APPLY_BASIC_SEQ");
			rBean.setFrId(seq);
		}else {
			//修改人、修改时间
			rBean.setUpdator(user.getName());
			rBean.setUpdateTime(date);
			rBean.setfUser(user.getId());
			//登记人、登记部门、登记时间
			rBean.setfUserName(user.getName());
			rBean.setfDeptID(user.getDepart().getId());
			rBean.setfDeptName(user.getDepart().getName());
			rBean.setfReqTime(new Date());
		}
		//查询采购计划信息
		PurchaseApplyBasic pabBean2 = cgApplysqMng.findById(pabBean.getFpId());
		
		//设置采购登记状态为待登记		0-暂存	1-待登记		9-已登记		-1-已退回	-4-已撤回
		pabBean2.setFbidStauts(rBean.getfCheckStauts());
		rBean.setfStauts(rBean.getfCheckStauts());
		cgApplysqMng.saveOrUpdate(pabBean2);
		
		//以下为工作流的节点配置（如果点送审则设置进入工作流1审核中）
		if("1".equals(rBean.getfCheckStauts())) {
			
			//得到第一个审批节点key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), rBean.getJoinTable(), rBean.getBeanCodeField(), rBean.getBeanCode(), "CGDJ", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("CGDJ", user.getDpID());
			Integer flowId = processDefin.getFPId();
			TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId, firstKey);
			User nextUser = userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号
			rBean.setUserName2(nextUser.getName());
			rBean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			rBean.setnCode(String.valueOf(firstKey));
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId, rBean.getBeanCode());
			
			//保存基本信息
			rBean = (RegisterApplyBasic) super.saveOrUpdate(rBean);
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			Integer seq1 = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq1);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(rBean.getFrId());//申请单ID
			work.setTaskCode(rBean.getFbiddingCode());//为申请单的单号
			work.setTaskName("[采购过程登记]"+pabBean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/cgprocess/check?id="+rBean.getFrId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/cgprocess/detail?id="+pabBean.getFpId());//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型：1-审批
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(date);//任务提交时间
			personalWorkMng.merge(work);
					
			//添加一个自己的已办事项
			PersonalWork minwork = new PersonalWork();
			Integer seq2 = shenTongMng.getSeq("personal_worktable_seq");
			minwork.setfId(seq2);
			minwork.setUserId(user.getId());//任务处理人ID既是下节点处理人ID
			minwork.setTaskId(rBean.getFrId());//申请单ID
			minwork.setTaskCode(rBean.getFbiddingCode());//为申请单的单号
			minwork.setTaskName("[采购过程登记]"+pabBean.getFpName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			minwork.setUrl("/cgprocess/edit?id="+pabBean.getFpId());//退回修改url
			minwork.setUrl1("/cgprocess/detail?id="+pabBean.getFpId());//查看url
			minwork.setTaskStauts("2");//待办
			minwork.setType("2");//任务类型：2-查看
			minwork.setTaskType("0");//任务归属（0发起人）
			minwork.setBeforeUser(user.getName());//任务提交人姓名
			minwork.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			minwork.setBeforeTime(date);//任务提交时间
			minwork.setFinishTime(date);
			personalWorkMng.merge(minwork);
		}else {
			//保存基本信息
			rBean = (RegisterApplyBasic) super.saveOrUpdate(rBean);
		}
		//保存附件信息
		attachmentMng.joinEntity(rBean, files01);
		attachmentMng.joinEntity(rBean, files02);
		attachmentMng.joinEntity(rBean, files03);
		attachmentMng.joinEntity(rBean, files04);
		attachmentMng.joinEntity(rBean, files05);
		
		//删除数据库中   申请采购登记中的供应商
		getSession().createSQLQuery("delete from T_BIDDING_REGIST where F_P_ID ="+rBean.getFpId()+" ").executeUpdate();
		//获取申请采购登记中的供应商
		List<BiddingRegist> bidLists = new ArrayList<BiddingRegist>();
		if(!StringUtil.isEmpty(jsonList)){
			List<BiddingRegist> bidList = JSON.parseObject("["+jsonList.toString().substring(0, jsonList.length()-1)+"]",new TypeReference<List<BiddingRegist>>(){});
			for (BiddingRegist registList : bidList) {
				BiddingRegist info = new BiddingRegist();
				Integer seq3 = shenTongMng.getSeq("BIDDING_REGIST_SEQ");
				info.setFbId(seq3);
				info.setFpId(rBean.getFpId());
				info.setFrId(rBean.getFrId());
				info.setFbiddingCode(registList.getFbiddingCode());
				info.setFbiddingName(registList.getFbiddingName());
				info.setFbidAmount(registList.getFbidAmount());
				info.setFgrade(registList.getFgrade());
				info.setFbidStatus(registList.getFbidStatus());
				info.setFlegal(registList.getFlegal());
				info.setFlinkman(registList.getFlinkman());
				info.setfContractstatus("0");
				info.setFphone(registList.getFphone());
				super.merge(info);
				bidLists.add(info);
			}	
		}
		/*List<ProcurementPlanList> mingxiList = CgConPlanListMng.findby("fpId", String.valueOf(rBean.getFpId()));
		List<PurcMaterialRegisterList> purcMaterialRegisterLists = new ArrayList<PurcMaterialRegisterList>();
		//删除数据库中   申请采购登记中的供应商的中标明细
		getSession().createSQLQuery("delete from T_PURC_MATERIAL_REGISTER_LIST where F_P_ID ="+rBean.getFpId()+" ").executeUpdate();
		//获取申请采购登记中的供应商的中标明细
		if(!StringUtil.isEmpty(supJsonList)){
			List<PurcMaterialRegisterList> purList = JSON.parseObject("["+supJsonList.toString().substring(0, supJsonList.length()-1)+"]",new TypeReference<List<PurcMaterialRegisterList>>(){});
			double fbidAmount = 0.00;
			for (PurcMaterialRegisterList materialRegisterList : purList) {
				PurcMaterialRegisterList info = new PurcMaterialRegisterList();
				info.setFpId(rBean.getFpId());
				info.setFrId(rBean.getFrId());
				info.setMainId(materialRegisterList.getMainId());
				info.setFbiddingCode(materialRegisterList.getFbiddingCode());
				info.setFmName(materialRegisterList.getFmName());
				info.setfBrand(materialRegisterList.getfBrand());
				info.setFmSpecif(materialRegisterList.getFmSpecif());
				info.setFmModel(materialRegisterList.getFmModel());
				info.setFpNum(materialRegisterList.getFpNum());
				info.setFamount(materialRegisterList.getFamount());
				info.setFsignPrice(materialRegisterList.getFsignPrice());
				info.setfWhetherImport(materialRegisterList.getfWhetherImport());
				super.merge(info);
				fbidAmount += materialRegisterList.getFamount();
				purcMaterialRegisterLists.add(info);
			}	
			pabBean2.setFbidAmount(fbidAmount);
			rBean.setAmount(fbidAmount);
			cgApplysqMng.saveOrUpdate(pabBean2);
			super.saveOrUpdate(rBean);
			if(mingxiList!=null){
				for (int i = 0; i < mingxiList.size(); i++) {//主要查询中标商的采购明细金额、数量是否等于小于采购申请的明细金额、数量
					Double countMoney =mingxiList.get(i).getFsumMoney();//总金额
					Double countMoney1 =0.00;
					for (int j = 0; j < purList.size(); j++) {
						if(mingxiList.get(i).getMainId()==purList.get(j).getMainId()){
							countMoney1=countMoney1+purList.get(j).getFamount();
						}
					}
					if(!"PMMC-5".equals(pabBean.getFpItemsName())&&!"PMMC-4".equals(pabBean.getFpItemsName())){//如果是工程、服务类的品目  则不计算数量
						int sum =mingxiList.get(i).getFnum();//数量
						int sum1 = 0;
						for (int j = 0; j < purList.size(); j++) {
							if(String.valueOf(mingxiList.get(i).getMainId()).equals(String.valueOf(purList.get(j).getMainId()))){
								sum1=sum1+purList.get(j).getFpNum();
							}
						}
						BigDecimal s = new BigDecimal(sum);
						BigDecimal s1 = new BigDecimal(sum1);
						int s2 = s.compareTo(s1);
						if(s2==-1 || s2==1){
							throw new ServiceException("中标商的商品数量和申请的数量不一致！");
						}
					}
					BigDecimal c = new BigDecimal(countMoney);
					BigDecimal c1 = new BigDecimal(countMoney1);
					int c2 = c.compareTo(c1);
					if(c2==-1){
						throw new ServiceException("中标商的商品总价和申请的总价不一致！");
					}
				}
				for (int h = 0; h < bidLists.size(); h++) {//查询每个中标商的中标金额和中标商采购明细中的金额是否一致
					Double countMoney =bidLists.get(h).getFbidAmount();
					Double countMoney1 =0.00;
					for (int j = 0; j < purList.size(); j++) {
						if(purList.get(j).getFbiddingCode().equals(bidLists.get(h).getFbiddingCode())){
							countMoney1=countMoney1+purList.get(j).getFamount();
						}
					}
					BigDecimal c = new BigDecimal(countMoney);
					BigDecimal c1 = new BigDecimal(countMoney1);
					int c2 = c.compareTo(c1);
					if(c2==-1 || c2==1){
						throw new ServiceException("中标商的商品总价和中标商的中标金额不一致！");
					}
				}
			}
		}*/
	}
	
	@Override
	public Pagination checkPageList(PurchaseApplyBasic bean, Integer pageNo, Integer pageSize, User user,String searchData) {
		String sql = "select p.F_P_ID,r.F_R_ID,p.F_P_CODE,p.F_P_NAME,p.F_P_ITEMS_NAME,p.F_AMOUNT,"
				+ "r.F_USER_NAME,r.F_DEPT_NAME,r.F_REQ_TIME,p.F_BID_STAUTS from t_purchase_apply_basic p left join t_register_apply_basic r"
				+ " on p.F_P_ID = r.F_P_ID WHERE r.F_STAUTS !='99' and r.F_USER_CODE='"+user.getId()+"' ";
		if(!StringUtil.isEmpty(searchData)){
			sql += " AND (p.F_P_CODE like '%"+searchData+"%' or p.F_P_NAME like '%"+searchData+"%' or p.F_AMOUNT like '%"+searchData+"%' or r.F_USER_NAME like '%"+searchData+"%' or r.F_DEPT_NAME like '%"+searchData+"%') ";
		}
				/*if(!StringUtil.isEmpty(bean.getFpCode())){
					sql += " and p.F_P_CODE like '%"+bean.getFpCode()+"%'";
				}
				if(!StringUtil.isEmpty(bean.getFpName())){
					sql += " and p.F_P_NAME like '%"+bean.getFpName()+"%'";
				}
				if(!StringUtil.isEmpty(bean.getFpItemsName())){
					sql += " and p.F_P_ITEMS_NAME='"+bean.getFpItemsName()+"'";
				}*/
				sql += " order by r.F_REQ_TIME desc;";
				SQLQuery query = getSession().createSQLQuery(sql);
				List<Object[]> list = query.list();
				List<PurchaseApplyBasic> list2 = new ArrayList<PurchaseApplyBasic>();
				for (int i = 0; i < list.size(); i++) {
					PurchaseApplyBasic applyBasic = new PurchaseApplyBasic();
					applyBasic.setFpId(Integer.valueOf(String.valueOf(list.get(i)[0])));
					applyBasic.setFrId(Integer.valueOf(String.valueOf(list.get(i)[1])));
					applyBasic.setFpCode(String.valueOf(list.get(i)[2]));
					applyBasic.setFpName(String.valueOf(list.get(i)[3]));
					applyBasic.setFpItemsName(String.valueOf(list.get(i)[4]));
					applyBasic.setAmount(Double.valueOf(String.valueOf(list.get(i)[5])));
					if("null".equals(String.valueOf(list.get(i)[6]))){
						applyBasic.setfUserName("");
					}else{
						applyBasic.setfUserName(String.valueOf(list.get(i)[6]));
					}
					if("null".equals(String.valueOf(list.get(i)[7]))){
						applyBasic.setfDeptName("");
					}else{
						applyBasic.setfDeptName(String.valueOf(list.get(i)[7]));
					}
					
					applyBasic.setfReqTime(DateUtil.parseDate(String.valueOf(list.get(i)[8])));
					applyBasic.setFbidStauts(String.valueOf(list.get(i)[9]));
					list2.add(applyBasic);
				}
				Pagination pagination = new Pagination();
				pagination.setList(list2);
		
		return pagination;
		/*Finder finder = Finder.create(" FROM PurchaseApplyBasic WHERE fbidStauts = '9' and fuserId='"+user.getId()+"'");
		if(!StringUtil.isEmpty(bean.getFpCode())){//按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){//按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpItemsName())){//按品目名称
			finder.append(" AND fpItemsName = :fpItemsName");
			finder.setParam("fpItemsName", bean.getFpItemsName());
		}
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);*/
	}

	@Override
	public void saveCheck(TProcessCheck checkBean, RegisterApplyBasic bean, String files, User user) throws Exception {
		bean = registerApplyMng.findById(bean.getFrId());
		CheckEntity entity = (CheckEntity)bean;
		
		String checkUrl = "/cgprocess/check?id=";
		String lookUrl = "/cgprocess/detail?id=";
		
		bean = (RegisterApplyBasic) tProcessCheckMng.checkProcess(checkBean, entity, user, "CGDJ", checkUrl, lookUrl, files);
		
		//当审批不通过时
		if("0".equals(checkBean.getFcheckResult())){
			//根据外键关联查询采购申请对象
			PurchaseApplyBasic pabBean = cgApplysqMng.findById(bean.getFpId());
			
			//设置采购登记状态为已退回
			pabBean.setFbidStauts("-1");
			//保存采购申请信息
			cgApplysqMng.saveOrUpdate(pabBean);
		}
		
		//当审批完全通过时
		if("9".equals(bean.getfCheckStauts())){
			//根据外键关联查询采购申请对象
			PurchaseApplyBasic pabBean = cgApplysqMng.findById(bean.getFpId());
			//设置采购登记状态为已登记
			pabBean.setFbidStauts("9");
			Double fbidAmount = 0.00;
			List<BiddingRegist> brlist = cgProcessMng.findFbIdByFpId(bean.getFpId(), null,null);
			for (int i = 0; i < brlist.size(); i++) {
				fbidAmount = fbidAmount +brlist.get(i).getFbidAmount();
				if("是".equals(brlist.get(i).getFbidStatus())){
					pabBean.setfOrgName(brlist.get(i).getFbiddingName());
					pabBean.setFbidAmount(brlist.get(i).getFbidAmount());
				}
			}
			cgApplysqMng.saveOrUpdate(pabBean);
			BigDecimal fbidAmounts = new BigDecimal(fbidAmount);
			BigDecimal amounts = new BigDecimal(pabBean.getAmount());
			//申请金额大于登记金额时    归还预算
			if(amounts.compareTo(fbidAmounts)>0){
				//采购送审时冻结金额
				Double syAmount = budgetIndexMgrMng.deleteDjAmount(pabBean.getIndexId(), pabBean.getProDetailId(), (amounts.subtract(fbidAmounts)).doubleValue());
			}
			//保存采购申请信息
		}
		//保存过程登记信息
		super.saveOrUpdate(bean);
	}

	@Override
	public String reCall(Integer id) throws Exception {
		//根据外键关联查询采购申请对象
		PurchaseApplyBasic pabBean = cgApplysqMng.findById(id);
		
		//根据id查询采购过程登记对象
		RegisterApplyBasic rBean = registerApplyMng.findByProperty("fpId", id).get(0);
		//删除采购验收待办
		personalWorkMng.deleteDb(rBean.getNextCheckUserId(), rBean.getBeanCode(), "0");
		
		//若审批状态为待审批
		if("1".equals(rBean.getfCheckStauts())){
			//设置采购登记审批状态为已撤回
			rBean.setfCheckStauts("-4");
			//设置采购登记状态为已撤回
			pabBean.setFbidStauts("-4");
			//保存采购申请信息
			cgApplysqMng.saveOrUpdate(pabBean);
		}
		
		//给待审批人发送消息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "采购过程登记申请被撤回消息";
		String msg = "您待审批的  "+pabBean.getFpName() +",任务编号：("+rBean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, rBean.getNextCheckUserId(), "3");
				
		//撤回
		rBean = (RegisterApplyBasic) reCall((CheckEntity) rBean);
		this.updateDefault(rBean);
		return "操作成功";
	}

	@Override
	public Integer findFbIdByFpId(Integer id) {
		Integer fbId = null;
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT F_B_ID FROM T_BIDDING_REGIST WHERE F_P_ID = "+id);
		SQLQuery query = getSession().createSQLQuery(builder.toString());
		List<Integer> fbIdList = query.list();
		if(fbIdList != null && fbIdList.size() > 0){
			fbId = fbIdList.get(0);
		}
		return fbId;
	}

	@Override
	public List<BiddingRegist> findFbIdByFpId(Integer FpId, String id,String fContractstatus) {
		Finder finder = Finder.create(" FROM BiddingRegist WHERE 1=1");
		if(!StringUtil.isEmpty(String.valueOf(FpId))){
			finder.append(" AND fpId ="+FpId);
		}
		if(!StringUtil.isEmpty(id)){
			finder.append(" AND fbiddingCode ='"+id+"'");
		}
		if(!StringUtil.isEmpty(String.valueOf(fContractstatus))){
			finder.append(" AND fContractstatus ='"+fContractstatus+"'");
		}
		finder.append(" order by fpId desc");
		return super.find(finder);
	}

	@Override
	public BiddingRegist findbycode(String code,String fContractstatus) {
		Finder finder = Finder.create(" FROM BiddingRegist WHERE 1=1");
		if(!StringUtil.isEmpty(String.valueOf(code))){
			finder.append(" AND fbiddingCode ='"+code+"'");
		}
		if(!StringUtil.isEmpty(String.valueOf(fContractstatus))){
			finder.append(" AND fContractstatus ='"+fContractstatus+"'");
		}
		finder.append(" order by fpId desc");
		List<BiddingRegist> list =(List<BiddingRegist>) super.find(finder);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
