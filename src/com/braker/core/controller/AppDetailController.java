package com.braker.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.util.LookupsUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.AssetTypeMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.manager.UserMng;
import com.braker.core.manager.VehicleMng;
import com.braker.core.model.AssetType;
import com.braker.core.model.Depart;
import com.braker.core.model.Lookups;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.core.model.Vehicle;
import com.braker.icontrol.assets.handle.manager.HandleMng;
import com.braker.icontrol.assets.handle.model.AssetRegistration;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.icontrol.assets.rece.manager.ReceConfigListMng;
import com.braker.icontrol.assets.rece.manager.ReceListMng;
import com.braker.icontrol.assets.rece.manager.ReceMng;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.rece.model.ReceList;
import com.braker.icontrol.assets.returns.manager.AssetReturnListMng;
import com.braker.icontrol.assets.returns.manager.AssetReturnMng;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.RegistMng;
import com.braker.icontrol.assets.storage.manager.StorageMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.icontrol.assets.storage.model.Regist;
import com.braker.icontrol.assets.storage.model.Storage;
import com.braker.icontrol.budget.adjust.entity.TIndexAdItf;
import com.braker.icontrol.budget.adjust.entity.TIndexInnerAd;
import com.braker.icontrol.budget.adjust.manager.InsideAdjustMny;
import com.braker.icontrol.budget.adjust.manager.TIndexAdItfMng;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.PerformanceIndicatorModel;
import com.braker.icontrol.budget.project.entity.TProBasicFunds;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.PerformanceIndicatorModelMng;
import com.braker.icontrol.budget.project.manager.TProBasicFundsMng;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgcheck.manager.PurcMaterialRegisterListMng;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.cgmanage.cgreveive.manager.AcceptContractRegisterListMng;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.manager.SignInfoMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.manager.FeteCostInfoMng;
import com.braker.icontrol.expend.apply.manager.FoodAllowanceInfoMng;
import com.braker.icontrol.expend.apply.manager.HotelExpenseInfoMng;
import com.braker.icontrol.expend.apply.manager.InCityTrafficInfoMng;
import com.braker.icontrol.expend.apply.manager.InternationalTravelingExpenseInfoMng;
import com.braker.icontrol.expend.apply.manager.MeetingMng;
import com.braker.icontrol.expend.apply.manager.OutsideTrafficInfoMng;
import com.braker.icontrol.expend.apply.manager.TrainingMng;
import com.braker.icontrol.expend.apply.manager.TravelAppliInfoMng;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.FeteCostInfo;
import com.braker.icontrol.expend.apply.model.FoodAllowanceInfo;
import com.braker.icontrol.expend.apply.model.HotelExpenseInfo;
import com.braker.icontrol.expend.apply.model.InCityTrafficInfo;
import com.braker.icontrol.expend.apply.model.InternationalTravelingExpense;
import com.braker.icontrol.expend.apply.model.MeetPlan;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.OutsideTrafficInfo;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.TrainTeacherCost;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.loan.manager.LoanMng;
import com.braker.icontrol.expend.loan.manager.LoanPayeeMng;
import com.braker.icontrol.expend.loan.manager.PaymentMng;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.loan.model.LoanPayeeInfo;
import com.braker.icontrol.expend.loan.model.Payment;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbDetailMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.model.AppInvoiceInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbDetail;
import com.braker.icontrol.expend.reimburse.model.ReimCXInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;
import com.fasterxml.jackson.databind.util.BeanUtil;


/**
 * APP查看详情
 * 作用：接受APP后台发送的审批请求
 * 实现方式：请求转发
 * 返回请求端值的类型：由转发后方法定义
 * @author 张迅
 * @createtime 2020-04-23
 */

@Controller
@RequestMapping(value = "/appDetail")
public class AppDetailController extends BaseController{
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private ApplyMng applyMng;
	@Autowired
	private TProExpendDetailMng detailMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private MeetingMng meetingMng;
	@Autowired
	private TrainingMng trainingMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private CgConPlanMng confplanMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private TravelAppliInfoMng travelAppliInfoMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private OutsideTrafficInfoMng outsideTrafficInfoMng;
	@Autowired
	private InCityTrafficInfoMng inCityTrafficInfoMng;
	@Autowired
	private HotelExpenseInfoMng hotelExpenseInfoMng;
	@Autowired
	private FoodAllowanceInfoMng foodAllowanceInfoMng;
	@Autowired
	private InternationalTravelingExpenseInfoMng internationalTravelingExpenseInfoMng;
	@Autowired
	private VehicleMng vehicleMng;
	@Autowired
	private FeteCostInfoMng feteCostInfoMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private ContractRegisterListMng contractRegisterListMng;
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private RegisterApplyMng registerApplyMng;
	@Autowired
	private PurcMaterialRegisterListMng purcMaterialRegisterListMng;
	@Autowired
	private CgReceiveMng cgReceiveMng;
	@Autowired
	private AcceptContractRegisterListMng acceptContractRegisterListMng;
	@Autowired
	private TProBasicInfoMng projectMng;
	@Autowired
	private TProExpendDetailMng tProExpendDetailMng;
	@Autowired
	private TProBasicFundsMng tProBasicFundsMng;
	@Autowired
	private PerformanceIndicatorModelMng performancModelMng;
	@Autowired
	private TProBasicInfoMng proMng;
	@Autowired
	private InsideAdjustMny insideAdjustMng;
	@Autowired
	TIndexAdItfMng adItfMng;
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	@Autowired
	private DirectlyReimbDetailMng directlyReimbDetailMng;
	@Autowired
	private InvoiceMng invoiceMng;
	@Autowired
	private ReimbPayeeMng reimbPayeeMng;
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	@Autowired
	private ReceMng receMng;
	@Autowired
	private ReceListMng receListMng;
	@Autowired
	private ReceConfigListMng receConfigListMng;
	@Autowired
	private AssetTypeMng assetTypeMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private HandleMng handleMng;
	@Autowired
	private AssetReturnMng assetReturnMng;
	@Autowired
	private AssetReturnListMng assetReturnListMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private SignInfoMng signInfoMng;
	@Autowired
	private StorageMng storageMng;
	@Autowired
	private RegistMng registMng;
	@Autowired
	private LoanMng loanMng;
	@Autowired
	private LoanPayeeMng loanPayeeMng;
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng;
	@Autowired
	private PaymentMng paymentMng;
	@Autowired
	private ReceivPlanMng receivPlanMng;
	
	@RequestMapping("/comm")
	@ResponseBody
	//通用事项
	public Object comm(String pid){
		Map<String,Object> map = new HashMap<>();
		//基本信息
		ApplicationBasicInfo bean = applyMng.findById(Integer.valueOf(pid));
		//指标信息
//		TProExpendDetail detail = detailMng.findById(bean.getProDetailId());
//		TBudgetIndexMgr index = indexMng.findById(bean.getIndexId());
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				/*if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}*/
				bean.setPfDate(index.getYears());
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());			
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			/*if (index.getAppDate() != null) {
			bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
		}*/
			bean.setPfDate(index.getYears());
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);			
		}
		
		/*//总金额 剩余金额 预算年度
		if (index != null) {
			bean.setPfDate(index.getYears());
			bean.setPfAmount(index.getPfAmount());
		}
		if (detail != null) {
			bean.setPfAmount(detail.getOutAmount());
			bean.setSyAmount(detail.getSyAmount());
		}*/
		//费用明细
		List<Object> mxList = applyMng.getObjectList("ApplicationDetail", "gId", Integer.valueOf(pid));
		map.put("basicBean", bean);
		map.put("mxList", mxList);
		//附件
		List<Attachment> attaList = attachmentMng.list(bean);
		map.put("attaList", attaList);
		//工作流字段
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		if("1".equals(String.valueOf(bean.getType()))){
			if("FLFSQ".equals(bean.getCommonType())){
				strType = "TYSXFLSQ";
			}
			if("SPPSSQ".equals(bean.getCommonType())){
				strType = "TYSXSPPSSQ";
			}
		}
		String userId = bean.getUserId();
		User applyUser = userMng.findById(userId);
		map.put("f_userId", userId);//操作人主键
		map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
		map.put("f_area", strType);//操作人业务范围
		Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
		String userName = proposer.getUserName();
		if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
			map.put("f_userName", userName);
		}
		map.put("f_foCode", bean.getBeanCode());//表单编码
		map.put("f_nCode", bean.getNextCheckKey());//下一节点编码
		map.put("f_joinTable", "T_APPLICATION_BASIC_INFO");//表名
		map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
		map.put("f_beanCode", bean.getBeanCode());//表单编码
		//查询工作流节点
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
		TNodeData node =new TNodeData();
		node.setUser(applyUser);
		TProcessCheck checkInfo =new TProcessCheck();
		checkInfo.setFcheckTime(bean.getReqTime());
		node.setCheckInfo(checkInfo);
		nodeConfList.add(node);
		Collections.reverse(nodeConfList);
		//流程发起人信息
		//User user = userMng.findById(userId);
		map.put("流程发起人", applyUser.getName());
		map.put("发起人部门", applyUser.getDepartName());
		map.put("发起时间",  bean.getReqTime());
		//工作流节点信息
		map.put("总节点数", nodeConfList.size());
		if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
			map.put("当前流程节点人", applyUser.getName());
			map.put("当前流程节点数", 1);
		}else if(bean.getCheckStauts().equals("9")){
			map.put("当前流程节点人", "已完结");
			map.put("当前流程节点数", nodeConfList.size());
		}else{
			for(int i=0;i<nodeConfList.size();i++){
				if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
					map.put("当前流程节点人", bean.getUserName2());
					map.put("当前流程节点数", i+1);
				}
			}
		}
		map.put("nodeConfList", nodeConfList);
		return map;
	}
	
	//用车
		@RequestMapping("/car")
		@ResponseBody
		public Object car(String pid){
			Map<String,Object> map = new HashMap<>();

			//基本信息
			ApplicationBasicInfo bean = applyMng.findById(Integer.valueOf(pid));
			map.put("basicBean", bean);
			
			//指标信息
//			TProExpendDetail detail = detailMng.findById(bean.getProDetailId());
//			TBudgetIndexMgr index = indexMng.findById(bean.getIndexId());
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
//					if (index.getAppDate() != null) {
//						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
//					}
					bean.setPfDate(index.getYears());
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
//				if (index.getAppDate() != null) {
//					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
//				}
				bean.setPfDate(index.getYears());
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);			
			}
			
			//费用明细
			List<Object> mxList = applyMng.getObjectList("OfficeCar", "gId", Integer.valueOf(pid));
			map.put("mxList", mxList);
			//附件
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//工作流字段
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
			if("1".equals(String.valueOf(bean.getType()))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "TYSXSPPSSQ";
				}
			}
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getNextCheckKey());//下一节点编码
			map.put("f_joinTable", "T_APPLICATION_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//会议
		@RequestMapping("/meet")
		@ResponseBody
		public Object meet(String pid){
			Map<String,Object> map = new HashMap<>();
			
			//基本信息
			ApplicationBasicInfo bean = applyMng.findById(Integer.valueOf(pid));
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
				/*	if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}*/
					bean.setPfDate(index.getYears());
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
				/*if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}*/
				bean.setPfDate(index.getYears());
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);			
			}
			if (bean != null) {
				
				//会议信息
				List<MeetingAppliInfo> list1 = meetingMng.findByProperty("gId", Integer.valueOf(pid));
				if (list1 != null && list1.size() > 0) {
					MeetingAppliInfo meetBean = list1.get(0);
					map.put("meetBean", meetBean);
					
					//会议日程
					List<Object> planList = applyMng.getObjectList("MeetingPlan", "gId", Integer.valueOf(pid));
					map.put("planList", planList);
				}
			}
			//费用明细
			List<Object> mxList = applyMng.getObjectList("ApplicationDetail", "gId", Integer.valueOf(pid));
			map.put("mxList", mxList);
			map.put("basicBean", bean);
			//附件
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//工作流字段
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
			if("1".equals(String.valueOf(bean.getType()))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "SPPSSQ";
				}
			}
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getNextCheckKey());//下一节点编码
			map.put("f_joinTable", "T_APPLICATION_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//培训
		@RequestMapping("/train")
		@ResponseBody
		public Object train(String pid){
			Map<String,Object> map = new HashMap<>();
			
			//基本信息
			ApplicationBasicInfo bean = applyMng.findById(Integer.valueOf(pid));
			//指标信息
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
					/*if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}*/
					bean.setPfDate(index.getYears());
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
				/*if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}*/
				bean.setPfDate(index.getYears());
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);			
			}
			map.put("basicBean", bean);

			//培训信息
			List<TrainingAppliInfo> list = trainingMng.findByProperty("gId", Integer.valueOf(pid));
			if (list != null && list.size() > 0) {
				TrainingAppliInfo trainBean = list.get(0);
				map.put("trainBean", trainBean);
				
				//培训日程
				List<Object> planList = applyMng.getObjectList("MeetPlan", "tId", trainBean.gettId());
				map.put("planList", planList);
				//讲师信息
				List<Object> LecturerInfo= applyMng.getObjectList("LecturerInfo", "tId", trainBean.gettId());
				map.put("LecturerInfo", LecturerInfo);
				//综合预算
				List<Object> zongheList=applyMng.getMingxi("ApplicationDetail", "gId", bean.getgId());
				map.put("zongheList", zongheList);
				//师资费-讲课费
				List<TrainTeacherCost> lessonList=applyMng.getTeacherMingxi(trainBean.gettId(), "lesson");
				map.put("lessonList", lessonList);
				//师资费-住宿费
				List<TrainTeacherCost> hotelList=applyMng.getTeacherMingxi(trainBean.gettId(), "hotel");
				map.put("hotelList", hotelList);
				//师资费-伙食费
				List<TrainTeacherCost> foodList=applyMng.getTeacherMingxi(trainBean.gettId(), "food");
				map.put("foodList", foodList);
				//师资费-城市间交通费
				List<TrainTeacherCost> trafficList1=applyMng.getTeacherMingxi(trainBean.gettId(), "traffic1");
				map.put("trafficList1", trafficList1);
				//师资费-市内交通费
				List<TrainTeacherCost> trafficList2=applyMng.getTeacherMingxi(trainBean.gettId(), "traffic2");
				map.put("trafficList2", trafficList2);
			}
			//附件
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//工作流字段
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
			if("1".equals(String.valueOf(bean.getType()))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "TYSXSPPSSQ";
				}
			}
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getNextCheckKey());//下一节点编码
			map.put("f_joinTable", "T_APPLICATION_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//接待
		@RequestMapping("/reception")
		@ResponseBody
		public Object reception(String pid){
			Map<String,Object> map = new HashMap<>();
			
			//基本信息
			ApplicationBasicInfo bean = applyMng.findById(Integer.valueOf(pid));
			map.put("basicBean", bean);
			
			//指标信息
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
					/*if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}*/
					bean.setPfDate(index.getYears());
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
				/*if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}*/
				bean.setPfDate(index.getYears());
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);			
			}

			//接待信息
			ReceptionAppliInfo recepBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "gId", Integer.valueOf(pid));
			map.put("recepBean", recepBean);
			
			//其他费用列表
			List<Object> otherList = applyMng.getObjectList("ReceptionOther", "gId", Integer.valueOf(pid));
			map.put("otherList", otherList);
			
		/*	if (recepBean != null) {
				//接待人员列表
				List<Object> peopleList = applyMng.getObjectList("ReceptionPeopleInfo", "jId", recepBean.getjId());
				map.put("peopleList", peopleList);
			}*/
			
			//住宿费列表
			List<Object> hotelList = applyMng.getObjectList("ReceptionHotel", "gId", Integer.valueOf(pid));
			map.put("hotelList", hotelList);
			
			//餐费列表
			List<Object> foodList = applyMng.getObjectList("ReceptionFood", "gId", Integer.valueOf(pid));
			map.put("foodList", foodList);
			//附件
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//工作流字段
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
			if("1".equals(String.valueOf(bean.getType()))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "TYSXSPPSSQ";
				}
			}
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getNextCheckKey());//下一节点编码
			map.put("f_joinTable", "T_APPLICATION_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//出国
		@RequestMapping("/abroad")
		@ResponseBody
		public Object abroad(String pid){
			Map<String,Object> map = new HashMap<>();
			
			//基本信息
			ApplicationBasicInfo bean = applyMng.findById(Integer.valueOf(pid));
			map.put("basicBean", bean);
			
			//指标信息
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
					/*if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}*/
					bean.setPfDate(index.getYears());
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
				/*if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}*/
				bean.setPfDate(index.getYears());
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);			
			}
			
			//出国信息
			AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "gId", Integer.valueOf(pid));
			map.put("abroadBean", abroadBean);
			
			if (abroadBean != null) {
				//出访计划列表
				List<Object> planList = applyMng.getObjectList("AbroadPlan", "gId", bean.getgId());
				map.put("planList", planList);
				
				//国际旅费
				List<InternationalTravelingExpense> gjlist = internationalTravelingExpenseInfoMng.findbygId(bean.getgId());
				for (InternationalTravelingExpense expense : gjlist) {
					Vehicle vehicle = vehicleMng.findById(Integer.valueOf(expense.getVehicle()));
					expense.setVehicleText(vehicle.getName());
				}
				map.put("gjlist", gjlist);
				//城市间交通费
				OutsideTrafficInfo outside = new OutsideTrafficInfo();
				outside.setgId(bean.getgId());
				List outsideList=outsideTrafficInfoMng.outsideTrafficInfoPageList(1, 10000, outside).getList();
				map.put("outsideList", outsideList);
				//住宿费
				List<HotelExpenseInfo> hotellist = hotelExpenseInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				map.put("hotellist", hotellist);
				//伙食费
				List<FoodAllowanceInfo> foodlist = foodAllowanceInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				map.put("foodlist",foodlist);
				//公杂费
				List<Object> feeList = applyMng.getObjectList("MiscellaneousFeeInfo", "gId", bean.getgId());
				map.put("feeList",feeList);
				//宴请费
				List<FeteCostInfo> feteList = feteCostInfoMng.findbygId(bean.getgId());
				map.put("feteList",feteList);
				//其他费用
				List<Object> receptionList = applyMng.getObjectList("ReceptionOther", "gId", bean.getgId());
				map.put("otherList",receptionList);
			}
			//附件
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);	
			//工作流字段
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
			if("1".equals(String.valueOf(bean.getType()))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "TYSXSPPSSQ";
				}
			}
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getNextCheckKey());//下一节点编码
			map.put("f_joinTable", "T_APPLICATION_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//差旅
		@RequestMapping("/travel")
		@ResponseBody
		public Object travel(String pid){
			Map<String,Object> map = new HashMap<>();
			
			//基本信息
			ApplicationBasicInfo bean = applyMng.findById(Integer.valueOf(pid));
			map.put("basicBean", bean);
			
			//指标信息
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);			
			}
			TravelAppliInfo travel = new TravelAppliInfo();
			travel.setgId(bean.getgId());
			//差旅信息
			Pagination p = travelAppliInfoMng.travelPageList(1, 10000, travel);
			if("GWCC".equals(bean.getTravelType())){
				List<TravelAppliInfo> list = (List<TravelAppliInfo>) p.getList();
				for(int x=0; x<list.size(); x++) {
					list.get(x).setTravelAreaId(Integer.valueOf(list.get(x).getTravelArea()));	
				}
				OutsideTrafficInfo outside=new OutsideTrafficInfo();
				outside.setgId(bean.getgId());
				List outsideList=outsideTrafficInfoMng.outsideTrafficInfoPageList(1, 10000, outside).getList();
				map.put("outsideList", outsideList);
				InCityTrafficInfo incity = new InCityTrafficInfo();
				incity.setgId(bean.getgId());
				List incityList = inCityTrafficInfoMng.inCityInfoPageList(1, 10000, incity).getList();
				map.put("incityList", incityList);
				List<HotelExpenseInfo> hotellist = hotelExpenseInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				map.put("hotellist", hotellist);
				List<FoodAllowanceInfo> foodlist = foodAllowanceInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				map.put("foodlist",foodlist);
			}else if("GWCX".equals(bean.getTravelType())){
				InCityTrafficInfo incity = new InCityTrafficInfo();
				incity.setgId(bean.getgId());
				incity.setTravelType(bean.getTravelType());
				List incityList = inCityTrafficInfoMng.inCityInfoPageList(1, 10000, incity).getList();
				map.put("incityList", incityList);
				List<HotelExpenseInfo> hotellist = hotelExpenseInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				map.put("hotellist", hotellist);
				List<FoodAllowanceInfo> foodlist = foodAllowanceInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				map.put("foodlist",foodlist);
			}

			//差旅人员信息
			List<TravelAppliInfo> peopleList = (List<TravelAppliInfo>) p.getList();
			map.put("peopleList", peopleList);
			//附件
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//工作流字段
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
			if("GWCX".equals(bean.getTravelType())){
				strType = "GWCXSQ";
			}
			if("1".equals(String.valueOf(bean.getType()))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "TYSXSPPSSQ";
				}
			}
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getNextCheckKey());//下一节点编码
			map.put("f_joinTable", "T_APPLICATION_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;

		}
		
		
		@RequestMapping("/contractDraw")
		@ResponseBody
		//详情-合同拟定审批
		public Object contractDraw(String pid){
			Map<String,Object> map = new LinkedHashMap<>();
			
			//合同拟定信息
			ContractBasicInfo oldBean = formulationMng.findById(Integer.valueOf(pid));
			ContractBasicInfo bean =new ContractBasicInfo();
			BeanUtils.copyProperties(oldBean,bean);
			String fcType=lookupsMng.getNameByCategoryCodeAndCode("HTFL", bean.getFcType());
			bean.setFcType(fcType);
			//签约方信息
			SignInfo signInfo = new SignInfo();
			List<SignInfo> signInfoList = filingMng.find_Sign(bean);
			if(signInfoList != null && signInfoList.size() > 0){
				signInfo = signInfoList.get(0);
			}
			if(signInfo.getfSignType()!=null){
				String fSignType=lookupsMng.getNameByCategoryCodeAndCode("HTQYFLX", signInfo.getfSignType());
				signInfo.setfSignType(fSignType);
			}
			//付款计划
			Pagination p = filingMng.find_ReceivPlan(String.valueOf(bean.getFcId()),1,500);
			List<ReceivPlan> planList = (List<ReceivPlan>) p.getList();
			for (int i = 0; i < planList.size(); i++) {
				String fkyj=lookupsMng.getNameByCategoryCodeAndCode("FKPJ", planList.get(i).getfReceProof());
				planList.get(i).setfReceProofs(fkyj);	
			}
			map.put("planList", planList);
			//采购清单
			List<Object> mingxiList = confplanMng.getMingxi("PurcMaterialRegisterList", "fbiddingCode", bean.getfContractor());
			map.put("cgplan", mingxiList);
			List<BiddingRegist> brlist = cgProcessMng.findFbIdByFpId(null,bean.getfContractor(),null);
			if(brlist!=null&&brlist.size()>0){
				bean.setfContractor(brlist.get(0).getFbiddingName());
			}
			map.put("basicBean", bean);
			map.put("signerInfo", signInfo);
			//合同拟定的附件
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//签约方信息附件
			List<Attachment> signAttaList = attachmentMng.list(signInfo);
			map.put("signAttaList", signAttaList);
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", "HTND");//操作人业务范围
			Proposer proposer = new Proposer(bean.getfOperator(), bean.getfDeptName(), bean.getfReqtIME());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getfNCode());//下一节点编码
			map.put("f_joinTable", "T_CONTRACT_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "HTND", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getfNCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqtIME());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getfReqtIME());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getfFlowStauts().equals("-1")||bean.getfFlowStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getfFlowStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getfUserName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		@RequestMapping("/contractChange")
		@ResponseBody
		//详情-合同变更审批
		public Object contractChange(String pid){
			Map<String,Object> map = new LinkedHashMap<>();
			
			//合同变更信息
			Upt oldupdateBean = uptMng.findById(Integer.valueOf(pid));
			//合同基本信息
			ContractBasicInfo oldcontractBean = formulationMng.findById(Integer.valueOf(oldupdateBean.getfContId_U()));
			Upt updateBean =new Upt();
			ContractBasicInfo contractBean =new ContractBasicInfo();
			BeanUtils.copyProperties(oldupdateBean,updateBean);
			BeanUtils.copyProperties(oldcontractBean,contractBean);
			String fcType=lookupsMng.getNameByCategoryCodeAndCode("HTFL", contractBean.getFcType());
			contractBean.setFcType(fcType);
			//签约方信息
			SignInfo sign = new SignInfo();
			List<SignInfo> signInfoList=filingMng.find_Sign(contractBean);
			if(signInfoList!=null&&signInfoList.size()>0){
				sign = signInfoList.get(0);
			}
			//新付款计划
			//List<ReceivPlan> newPlanList = receivPlanMng.findbyUptId(updateBean.getfId_U());
			ReceivPlan receivPlan = new ReceivPlan();
			receivPlan.setfUptId_R(updateBean.getfId_U());
			receivPlan.setDataType(1);
			List<ReceivPlan> newPlanList = filingMng.getReceivPlan(receivPlan);
			for (int i = 0; i < newPlanList.size(); i++) {
				Lookups lookups=lookupsMng.findByLookCode(newPlanList.get(i).getfReceProof());
				newPlanList.get(i).setfReceProofs(lookups.getName());	
			}
			//旧付款计划
			Pagination p = filingMng.find_ReceivPlan(String.valueOf(contractBean.getFcId()),1,500);
			List<ReceivPlan> oldList = (List<ReceivPlan>) p.getList();
			if(oldList!=null&oldList.size()>0){
				for (int i = 0; i < oldList.size(); i++) {
					Lookups lookups=lookupsMng.findByLookCode(oldList.get(i).getfReceProof());
					oldList.get(i).setfReceProofs(lookups.getName());	
				}
			}
			//新采购清单
			ContractRegisterList contractRegisterList =new ContractRegisterList();
			contractRegisterList.setfDataType(1);
			contractRegisterList.setfId_U(updateBean.getfId_U());
			List<ContractRegisterList> newCgplan = contractRegisterListMng.getContractRegisterList(contractRegisterList);
			//旧采购清单
			List<Object> oldmingxiList = confplanMng.getMingxi("PurcMaterialRegisterList", "fbiddingCode", contractBean.getfContractor());
			List<BiddingRegist> brlist = cgProcessMng.findFbIdByFpId(null,contractBean.getfContractor(),null);
			if(brlist!=null&&brlist.size()>0){
				contractBean.setfContractor(brlist.get(0).getFbiddingName());
			}
			map.put("updateBean", updateBean);
			map.put("contractBean", contractBean);
			map.put("newPlanList", newPlanList);
			map.put("oldPlanList", oldList);
			map.put("newCgplan", newCgplan);
			map.put("oldCgplan", oldmingxiList);
			map.put("sign", sign);
			map.put("foCode", updateBean.getBeanCode());
			//合同拟定的附件
			List<Attachment> attaList2 = attachmentMng.list(contractBean);
			map.put("attaList2", attaList2);
			//合同变更附件
			List<Attachment> attaList1 = attachmentMng.list(updateBean);
			map.put("attaList1", attaList1);
			//签约方信息附件
			List<Attachment> signAttaList = attachmentMng.list(sign);
			map.put("signAttaList", signAttaList);
			//工作流字段
			String userId = updateBean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", "HTBG");//操作人业务范围
			Proposer proposer = new Proposer(updateBean.getfOperator(),applyUser.getDepart().getName(), updateBean.getfReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			
			map.put("f_foCode", updateBean.getBeanCode());//表单编码
			map.put("f_nCode", updateBean.getfNCode());//下一节点编码
			map.put("f_joinTable", "T_CONTRACT_UPT");//表名
			map.put("f_beanCodeField", updateBean.getBeanCodeField());//编码字段
			map.put("f_beanCode", updateBean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "HTBG", applyUser.getDepart().getId(),updateBean.getBeanCode(),updateBean.getfNCode(), updateBean.getJoinTable(),updateBean.getBeanCodeField(), updateBean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(updateBean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  updateBean.getfReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(updateBean.getfUptFlowStauts().equals("-1")||updateBean.getfUptFlowStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(updateBean.getfUptFlowStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数",nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(updateBean.getNextCheckKey())){
						map.put("当前流程节点人", updateBean.getfUserName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		@RequestMapping("/purchasePlan")
		@ResponseBody
		//详情-采购计划(采购申请)审批
		public Object purchasePlan(String pid, String userId){
			Map<String,Object> map = new HashMap<>();
			//采购信息
			PurchaseApplyBasic oldbean = cgsqMng.findById(Integer.valueOf(pid));
			PurchaseApplyBasic bean =new PurchaseApplyBasic();
			BeanUtils.copyProperties(oldbean,bean);
			String cgfs =LookupsUtil.getNameByCategoryCodeAndCode("PURCHASE_METHOD", bean.getFpMethod());
			String cglx =LookupsUtil.getNameByCategoryCodeAndCode(bean.getFpMethod(), bean.getFpPype());
			String fpItemsNames =LookupsUtil.getNameByCategoryCodeAndCode("PMMC", bean.getFpItemsName());
			String budgetPriceBasisShow =LookupsUtil.getNameByCategoryCodeAndCode("YSJGYJ", bean.getBudgetPriceBasis());
			if(fpItemsNames!=null){
				bean.setFpItemsNames(fpItemsNames);
			}
			if(cglx!=null){
				bean.setFpPype(cglx);
			}
			if(cgfs!=null){
				bean.setFpMethod(cgfs);
			}
			bean.setBudgetPriceBasisShow(budgetPriceBasisShow);
			bean.setProSignContent(StringUtil.htmlRemoveTag(bean.getProSignContent()));
			//查询四级指标信息
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());	
					//资金渠道
					if("0".equals(index.getIndexType())){
						bean.setPfIndexType("基本预算");		
					}else{
						bean.setPfIndexType("项目预算");		
					}
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);	
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
			//采购清单
			List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fpId", Integer.valueOf(pid));
			//是否采购管理岗
			User user = userMng.findById(userId);
			String roleNames = user.getRoleName();
			if (!StringUtil.isEmpty(roleNames)) {
				if (roleNames.contains("采购管理岗")) {
					map.put("isRoleCggl", true);
				} else {
					map.put("isRoleCggl", false);
				}
			}else{
				map.put("isRoleCggl", false);
			}
			//是否为党建办公室处室负责人
			if(user.getDepartName().equals("党建办公室")){
				if (!StringUtil.isEmpty(roleNames)) {
					if (roleNames.contains("处室负责人")) {
						map.put("isMeetCode", true);
					} else {
						map.put("isMeetCode", false);
					}
				}else {
					map.put("isMeetCode", false);
				}
			}else{
				map.put("isMeetCode", false);
			}
			map.put("bean", bean);
			map.put("mingxiList", mingxiList);
			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//工作流字段
			User applyUser = userMng.findById(bean.getUserId());
			map.put("f_userId", bean.getUserId());//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			String busiArea="CGSQ";
			map.put("f_area", busiArea);//操作人业务范围
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			
			
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_PURCHASE_APPLY_BASIC");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(applyUser.getId(), busiArea, applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getfReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getfCheckStauts().equals("-1")||bean.getfCheckStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getfCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//采购登记详情
		@RequestMapping("/purchaseReg")
		@ResponseBody
		public Object purchaseReg(String pid){
			
			Map<String,Object> map = new LinkedHashMap<>();
			//过程登记基本信息
			RegisterApplyBasic rBean =registerApplyMng.findById(Integer.valueOf(pid));
			//rBean = registerApplyMng.findByProperty("fpId", Integer.valueOf(pid)).get(0);
			//采购基本信息
			PurchaseApplyBasic oldbean = cgsqMng.findById(rBean.getFpId());
			PurchaseApplyBasic bean =new PurchaseApplyBasic();
			BeanUtils.copyProperties(oldbean,bean);
			String cgfs =LookupsUtil.getNameByCategoryCodeAndCode("PURCHASE_METHOD", bean.getFpMethod());
			String cglx =LookupsUtil.getNameByCategoryCodeAndCode(bean.getFpMethod(), bean.getFpPype());
			String fpItemsNames =LookupsUtil.getNameByCategoryCodeAndCode("PMMC", bean.getFpItemsName());
			String budgetPriceBasisShow =LookupsUtil.getNameByCategoryCodeAndCode("YSJGYJ", bean.getBudgetPriceBasis());
			if(fpItemsNames!=null){
				bean.setFpItemsNames(fpItemsNames);
			}
			if(cglx!=null){
				bean.setFpPype(cglx);
			}
			bean.setProSignContent(StringUtil.htmlRemoveTag(bean.getProSignContent()));
			if(cgfs!=null){
				bean.setFpMethod(cgfs);
			}
			bean.setBudgetPriceBasisShow(budgetPriceBasisShow);
			//查询四级指标信息
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					bean.setPfAmount(detail.getOutAmount());		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());	
					//资金渠道
					if("0".equals(index.getIndexType())){
						bean.setPfIndexType("基本预算");		
					}else{
						bean.setPfIndexType("项目预算");		
					}
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount()*10000);		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount()*10000);	
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
			//采购清单
			List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fpId", rBean.getFpId());
			
			//BiddingRegist brBean = cgProcessMng.findByProperty("fpId", Integer.valueOf(pid)).get(0);
			map.put("basicBean", bean);
			map.put("regBean", rBean);
			map.put("mingxiList", mingxiList);
			//查询登记过程附件信息
			List<Attachment> attaList1 = attachmentMng.list(rBean);
			map.put("attaList1", attaList1);
			//查询采购单附件信息
			List<Attachment> attaList2 = attachmentMng.list(bean);
			map.put("attaList2", attaList2);
			//查询采购登记中的中标商明细
			List<Object> listBiddingRegist = applyMng.getMingxi("BiddingRegist", "fpId", rBean.getFpId());
			List<BiddingRegist> biddingList =new ArrayList<BiddingRegist>();
			for (Object biddingRegist : listBiddingRegist) {
				biddingList.add((BiddingRegist)biddingRegist);
			}
			
			for(BiddingRegist bidding:biddingList){
				String code =bidding.getFbiddingCode();
				List<PurcMaterialRegisterList> purcMaterList = new ArrayList();
				List<Object> purcList = new ArrayList<Object>();
				if(!StringUtil.isEmpty(code)) {
					purcList = purcMaterialRegisterListMng.getMingxi("PurcMaterialRegisterList", "fbiddingCode", code);
				}
				for (Object purc : purcList) {
					purcMaterList.add((PurcMaterialRegisterList) purc);
				}
				//purcMaterList.add((PurcMaterialRegisterList) purcList);
				bidding.setPurcMaterList(purcMaterList);
			}
			map.put("biddingList", biddingList);
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			/*map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", "HWCGYS");//操作人业务范围
*/			Proposer proposer = new Proposer(rBean.getfUserName(), rBean.getfDeptName(), rBean.getfReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			
			/*map.put("f_foCode", brBean.getBeanCode());//表单编码
			map.put("f_nCode", brBean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_BIDDING_REGIST");//表名
			map.put("f_beanCodeField", brBean.getBeanCodeField());//编码字段
			map.put("f_beanCode", brBean.getBeanCode());//表单编码
*/			//查询工作流节点
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(rBean.getfUser(), "CGDJ",rBean.getfDeptID(), rBean.getBeanCode(), rBean.getnCode(), rBean.getJoinTable(), rBean.getBeanCodeField(), rBean.getFbiddingCode(), "1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			User user = userMng.findById(userId);
			map.put("流程发起人", user.getName());
			map.put("发起人部门", user.getDepartName());
			map.put("发起时间",  rBean.getfReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(rBean.getfCheckStauts().equals("-1")||rBean.getfCheckStauts().equals("-4")){
				map.put("当前流程节点人", user.getName());
				map.put("当前流程节点数", 1);
			}else if(rBean.getfCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(rBean.getNextCheckKey())){
						map.put("当前流程节点人", rBean.getUserName2());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		
		@RequestMapping("/purchaseInspect")
		@ResponseBody
		//详情-采购验收审批
		public Object purchaseInspect(String pid){
			Map<String,Object> map = new HashMap<>();
			//查询采购验收基本信息
			AcceptCheck acBean = cgReceiveMng.findById(Integer.valueOf(pid));
			//采购基本信息
			PurchaseApplyBasic bean = cgsqMng.findById(acBean.getFpId());
		/*	String cgfs =LookupsUtil.getNameByCategoryCodeAndCode("PURCHASE_METHOD", bean.getFpMethod());
			if(cgfs!=null){
				bean.setFpMethod(cgfs);
			}*/
			//验收明细
			List<AcceptContractRegisterList> acceptContractRegisterList = acceptContractRegisterListMng.findFpIdbyMingxi(String.valueOf(acBean.getFacpId()));
			map.put("basicBean", bean);
			map.put("inspectBean", acBean);
			map.put("foCode", bean.getBeanCode());
			map.put("mingxiList", acceptContractRegisterList);
			//采购验收附件
			List<Attachment> attaList = attachmentMng.list(acBean);
			map.put("attaList", attaList);
			//工作流字段
			String userId = acBean.getUserId();
			User applyUser = userMng.findById(userId);
			//map.put("f_userId", userId);//操作人主键
			//map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			String busiArea = "HWCGYS";
			//map.put("f_area", busiArea);//操作人业务范围
			Proposer proposer = new Proposer(acBean.getFacpUsername(), acBean.getfDepartName(), acBean.getFacpTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			
			//map.put("f_foCode", acBean.getBeanCode());//表单编码
			//map.put("f_nCode", acBean.getnCode());//下一节点编码
			//map.put("f_joinTable", "T_ACCEPT_CHECK");//表名
			//map.put("f_beanCodeField", acBean.getBeanCodeField());//编码字段
			//map.put("f_beanCode", acBean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, busiArea, applyUser.getDepart().getId(),acBean.getBeanCode(),acBean.getnCode(), acBean.getJoinTable(),acBean.getBeanCodeField(), acBean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  acBean.getFacpTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(acBean.getfCheckStauts().equals("-1")||acBean.getfCheckStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(acBean.getfCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(acBean.getNextCheckKey())){
						map.put("当前流程节点人", acBean.getUserName2());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//项目审批
		@RequestMapping("/project")
		@ResponseBody
		public Object project(String pid){
			Map<String,Object> map = new LinkedHashMap<>();
			
			TProBasicInfo project = projectMng.findById(Integer.valueOf(pid));
			List<TProExpendDetail> expDetailList = tProExpendDetailMng.getByProId(Integer.valueOf(pid));
			//List<TProGoal> goalList = tProGoalMng.getTProGoalByPro(pid);
			List<PerformanceIndicatorModel> goalList = performancModelMng.findByProperty("fProId", Integer.valueOf(pid));
			List<TProBasicFunds> fundsList = tProBasicFundsMng.findByProperty("FProId", Integer.valueOf(pid));
			
			map.put("project", project);
			map.put("expDetailList", expDetailList);
			map.put("goalList", goalList);
			map.put("fundsList", fundsList);
			/*if (project != null) {
				if(project.getFProOrBasic()==0){
				List<TProcessCheck> logList = checkHistoryMng.findCheckHistorys("JBZCSB",project.getBeanCode(),null);//审批记录
				map.put("logList", logList);
				}else{
					List<TProcessCheck> logList = checkHistoryMng.findCheckHistorys("XMSB",project.getBeanCode(),null);//审批记录
					map.put("logList", logList);
				}
			}*/
			
			//工作流字段 
			String userId = project.getUserId();
//			map.put("f_userId", userId);//操作人主键
//			map.put("f_departId", project.getFProAppliDepartId());//发起人部门主键
			String ywfw ="";
			if(project.getFProOrBasic()==0){
				//map.put("f_area", "JBZCSB");//操作人业务范围
				ywfw="JBZCSB";
			}else if(project.getFProOrBasic()==1){
				//map.put("f_area", "XMSB");//操作人业务范围
				ywfw="XMSB";
			}
			/*if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				User user = userMng.findById(userId);
				map.put("f_userName", user.getName());
			}*/
			User user = userMng.findById(userId);
			/*map.put("f_foCode", project.getBeanCode());//表单编码
			map.put("f_nCode", project.getFExt11());//下一节点编码
			map.put("f_joinTable", "t_pro_basic_info");//数据库表
			map.put("f_beanCodeField", project.getBeanCodeField());//编码字段
			map.put("f_beanCode", project.getFProCode());//表单编码
*/			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, ywfw, project.getFProAppliDepartId(),project.getBeanCode(),project.getFExt11(), "t_pro_basic_info", project.getBeanCodeField(), project.getFProCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(user);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(project.getFProAppliTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", user.getName());
			//map.put("发起人部门", user.getDepartName());
			map.put("发起时间", project.getFProAppliTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(project.getFFlowStauts().equals("-11")){
				map.put("当前流程节点人", user.getName());
				map.put("当前流程节点数", 1);
			}else if(project.getFFlowStauts().equals("19")||Math.abs(Integer.valueOf(project.getFFlowStauts()))>20){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(project.getNextCheckKey())){
						map.put("当前流程节点人", project.getNextAssignerName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			// 查询附件信息
			List<Attachment> attaList = attachmentMng.list(project);
			map.put("attaList", attaList);
			return map;
		}
		
		//二上审批
		@RequestMapping("/esproject")
		@ResponseBody
		public Object esproject(String pid){
			Map<String,Object> map = new HashMap<>();
			TProBasicInfo project = projectMng.findById(Integer.valueOf(pid));
			List<TProExpendDetail> expDetailList = tProExpendDetailMng.getByProId(Integer.valueOf(pid));
			//List<TProGoal> goalList = tProGoalMng.getTProGoalByPro(pid);
			List<PerformanceIndicatorModel> goalList = performancModelMng.findByProperty("fProId", Integer.valueOf(pid));
			List<TProBasicFunds> fundsList = tProBasicFundsMng.findByProperty("FProId", Integer.valueOf(pid));
			
			
			
			map.put("project", project);
			map.put("expDetailList", expDetailList);
			map.put("goalList", goalList);
			map.put("fundsList", fundsList);
			String ywfw = null;
			if(project.getFProOrBasic()==0){
				ywfw = "JBZCESSB";
			}else if(project.getFProOrBasic()==1){
				ywfw = "ESSB";
			}
			/*if (project != null) {
				List<TProcessCheck> logList = checkHistoryMng.findCheckHistorys(ywfw,project.getBeanCode(),null);//审批记录
				map.put("logList", logList);
			}*/
			
			//工作流字段 
			String userId = project.getUserId();
			User user = userMng.findById(userId);
			/*map.put("f_userId", userId);//操作人主键
			map.put("f_departId", project.getFProAppliDepartId());//发起人部门主键
			map.put("f_area", ywfw);//操作人业务范围
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				User user = userMng.findById(userId);
				map.put("f_userName", user.getName());
			}*/
			
			/*map.put("f_foCode", project.getBeanCode());//表单编码
			map.put("f_nCode", project.getFExt11());//下一节点编码
			map.put("f_joinTable", "t_pro_basic_info");//数据库表
			map.put("f_beanCodeField", project.getBeanCodeField());//编码字段
			map.put("f_beanCode", project.getFProCode());//表单编码
*/			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, ywfw, project.getFProAppliDepartId(),project.getBeanCode(),project.getFExt11(), "t_pro_basic_info", project.getBeanCodeField(), project.getFProCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(user);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(project.getFProAppliTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			/*map.put("流程发起人", user.getName());
			map.put("发起人部门", user.getDepartName());
			map.put("发起时间", project.getFProAppliTime());*/
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(project.getFFlowStauts().equals("-11")||project.getFFlowStauts().equals("-21")||project.getFFlowStauts().equals("-31")||project.getFFlowStauts().equals("-14")){
				map.put("当前流程节点人", user.getName());
				map.put("当前流程节点数", 1);
			}else if(project.getFFlowStauts().equals("19")||project.getFFlowStauts().equals("29")||project.getFFlowStauts().equals("39")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(project.getNextCheckKey())){
						map.put("当前流程节点人", project.getNextAssignerName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			// 查询附件信息
			List<Attachment> attaList = attachmentMng.list(project);
			/*List<Attachment> attaList1 = new ArrayList();
			List<Attachment> attaList2 = new ArrayList();
			for (Attachment attc:attaList){
				if (attc.getServiceType().equals("lxyj")){
					attaList1.add(attc);
				}else if(attc.getServiceType().equals("ssfa")){
					attaList2.add(attc);
				}
			}
			map.put("attaList1", attaList1);
			map.put("attaList2", attaList2);*/
			map.put("attaList", attaList);
			return map;
		}
		
		//详情-预算调整审批
		@RequestMapping("/adjust")
		@ResponseBody
		public Object adjust(String pid){
			Map<String,Object> map = new HashMap<>();
			TIndexInnerAd bean = insideAdjustMng.findById(Integer.valueOf(pid));
			List<TIndexAdItf> inList =  adItfMng.findByInId(pid,"IN");
			List<TIndexAdItf> outList =  adItfMng.findByInId(pid,"OUT");
			
			map.put("ajustBean", bean);
			map.put("inList", inList);
			map.put("outList", outList);
			
			//工作流字段 TODO 该工作流测试后，流程节点显示不正确，参考InseideCheckController.edit
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			//查询工作流节点
			User user = userMng.findById(userId);
			//List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "NBZBDZ", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getInCode(),"1");
			//如果是部门内部调整，则走配置好的流程
			List<TNodeData> nodeConfList=new ArrayList();
			if(StringUtils.isEmpty(bean.getInsideDeptId())){
				 nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "NBZBDZ", bean.getDeptCode(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getInCode(), "1");
			}else {
				 nodeConfList = tProcessCheckMng.getInSideAdjustNodeConf(bean.getUserId(), bean.getInsideDeptId(), bean.getInCode());
			}
			TNodeData node =new TNodeData();
			node.setUser(user);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getOpTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getOpTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getFlowStauts().equals("-1")||bean.getFlowStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getFlowStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getFuserName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		
		//详情-资产领用审批
		@RequestMapping("/assetGet")
		@ResponseBody
		public Object assetGet(String pid,String checkUserId){
			Map<String,Object> map = new HashMap<>();
			//领用单
			Rece bean = receMng.findById(Integer.valueOf(pid));
			//领用资产清单
			List<ReceList> recepList = receListMng.findByProperty("fAssReceCode_RL", bean.getfAssReceCode());
			//配置明细
			if("9".equals(bean.getfFlowStauts_R())){
				Pagination p = receConfigListMng.findbyrece_CL(bean.getfId_R().toString(),bean, 1, 10000);
				List<ReceConfigList> li = (List<ReceConfigList>) p.getList();
				map.put("receConfigList", li);
			}
			map.put("recepBean", bean);
			map.put("recepList", recepList);
			//map.put("foCode", bean.getBeanCode());
			User checkUser = userMng.findById(checkUserId);
			Boolean configList =false;
			if(checkUser.getRoleName().contains("实物管理岗")){
				//页面需要让实物管理岗配置领用物品信息
				configList=true;
			}
			map.put("configList", configList);
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			/*map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", "GDZCLY");//操作人业务范围
			Proposer proposer = new Proposer(bean.getfOperator(), bean.getfReceDept(), bean.getfOperatorTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getfNextCode());//下一节点编码
			map.put("f_joinTable", "T_ASSET_RECE");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
*/			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "GDZCLY", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getfNextCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getfReceTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getfNextUserName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			//固定资产附件
			List<Attachment> handleAttaList = attachmentMng.list(bean);
			map.put("attaList", handleAttaList);
			return map;
		}

		//详情-资产处置审批
		@RequestMapping("/assetHandle")
		@ResponseBody
		public Object assetHandle(String pid,String checkUserId){
			Map<String,Object> map = new HashMap<>();
			//领用单
			Handle oldbean = handleMng.findById(Integer.valueOf(pid));
			Handle bean =new Handle();
			BeanUtils.copyProperties(oldbean,bean);
			bean.setfHandleText(StringUtil.htmlRemoveTag(bean.getfHandleText()));
			//领用资产清单
			List<AssetRegistration> assetRegist = handleMng.inAndFixedHandle(bean.getfId().toString(), bean.getfAssType());
			//配置明细
			map.put("handleBean", bean);
			map.put("assetRegist", assetRegist);
			//map.put("foCode", bean.getBeanCode());
			User checkUser = userMng.findById(checkUserId);
			Boolean djhCode =false;
			//只有党建办公室会议号录入岗才可以填写党组会会议号
			String roleName=checkUser.getRoleName();
			String deptName=checkUser.getDepart().getName();
			if(roleName.contains("会议号录入岗") && "党建办公室".equals(deptName)){
				djhCode=true;
			}
			map.put("djhCode", djhCode);
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			/*map.put("f_userId", userId);//操作人主键
					map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
					map.put("f_area", "GDZCLY");//操作人业务范围
					Proposer proposer = new Proposer(bean.getfOperator(), bean.getfReceDept(), bean.getfOperatorTime());
					String userName = proposer.getUserName();
					if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
						map.put("f_userName", userName);
					}
					map.put("f_foCode", bean.getBeanCode());//表单编码
					map.put("f_nCode", bean.getfNextCode());//下一节点编码
					map.put("f_joinTable", "T_ASSET_RECE");//表名
					map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
					map.put("f_beanCode", bean.getBeanCode());//表单编码
			 */			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "GDZCCZ", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getfNextCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getfReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getfNextUserName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			//固定资产附件
			List<Attachment> handleAttaList = attachmentMng.list(bean);
			map.put("attaList", handleAttaList);
			return map;
		}
		
		//详情-无形资产处置审批
		@RequestMapping("/intangibleAssetHandle")
		@ResponseBody
		public Object intangibleAssetHandle(String pid,String checkUserId){
			Map<String,Object> map = new HashMap<>();
			//领用单
			Handle oldbean = handleMng.findById(Integer.valueOf(pid));
			Handle bean =new Handle();
			BeanUtils.copyProperties(oldbean,bean);
			bean.setfHandleText(StringUtil.htmlRemoveTag(bean.getfHandleText()));
			//领用资产清单
			List<AssetRegistration> assetRegist = handleMng.inAndFixedHandle(bean.getfId().toString(), bean.getfAssType());
			//配置明细
			map.put("handleBean", bean);
			map.put("assetRegist", assetRegist);
			//map.put("foCode", bean.getBeanCode());
			User checkUser = userMng.findById(checkUserId);
			Boolean djhCode =false;
			//只有党建办公室会议号录入岗才可以填写党组会会议号
			String roleName=checkUser.getRoleName();
			String deptName=checkUser.getDepart().getName();
			if(roleName.contains("会议号录入岗") && "党建办公室".equals(deptName)){
				djhCode=true;
			}
			map.put("djhCode", djhCode);
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			/*map.put("f_userId", userId);//操作人主键
							map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
							map.put("f_area", "GDZCLY");//操作人业务范围
							Proposer proposer = new Proposer(bean.getfOperator(), bean.getfReceDept(), bean.getfOperatorTime());
							String userName = proposer.getUserName();
							if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
								map.put("f_userName", userName);
							}
							map.put("f_foCode", bean.getBeanCode());//表单编码
							map.put("f_nCode", bean.getfNextCode());//下一节点编码
							map.put("f_joinTable", "T_ASSET_RECE");//表名
							map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
							map.put("f_beanCode", bean.getBeanCode());//表单编码
			 */			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "WXZCCZ", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getfNextCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getfReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getfNextUserName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			//固定资产附件
			List<Attachment> handleAttaList = attachmentMng.list(bean);
			map.put("attaList", handleAttaList);
			return map;
		}
		
		//详情-资产交回审批
		@RequestMapping("/assetReturn")
		@ResponseBody
		public Object assetReturn(String pid,String checkUserId){
			Map<String,Object> map = new HashMap<>();
			//领用单
			AssetReturn bean = assetReturnMng.findById(Integer.valueOf(pid));
			//领用资产清单
			List<AssetReturnList> list = new ArrayList<AssetReturnList>();
			Integer fId =bean.getfId_A();
			if (fId != null) {
				//查询申请明细
				AssetReturn assetReturn= new AssetReturn();
				assetReturn.setfId_A(fId);
				list = assetReturnListMng.findByCondition(assetReturn);
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getfFixedType_AR()!=null){
						AssetType assetType = assetTypeMng.findbyCode(list.get(i).getfFixedType_AR());
						if (assetType != null) {
							list.get(i).setfFixedTypeName_AR(assetType.getName());
						}
					}
					if (!StringUtil.isEmpty(list.get(i).getfAssCode_AR())) {
						AssetBasicInfo assetBasicInfo = assetBasicInfoMng.findbyCode(list.get(i).getfAssCode_AR());
						list.get(i).setfUseName_AR(assetBasicInfo.getfUseName());
						list.get(i).setfUseDept_AR(assetBasicInfo.getfUseDept());
						if(list.get(i).getfAvailableStauts()!=null){
							
							list.get(i).setfAvailableStauts_AR(list.get(i).getfAvailableStauts().getName());
						}
					}
				}
			}
			//配置明细
			map.put("returnBean", bean);
			map.put("assetReturnList", list);
			//map.put("foCode", bean.getBeanCode());
			User checkUser = userMng.findById(checkUserId);
			Boolean oprate =false;
			if(checkUser.getRoleName().contains("实物管理岗")){
				//页面需要让实物管理岗配置领用物品信息
				oprate=true;
			}
			map.put("oprate", oprate);
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			/*map.put("f_userId", userId);//操作人主键
					map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
					map.put("f_area", "GDZCLY");//操作人业务范围
					Proposer proposer = new Proposer(bean.getfOperator(), bean.getfReceDept(), bean.getfOperatorTime());
					String userName = proposer.getUserName();
					if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
						map.put("f_userName", userName);
					}
					map.put("f_foCode", bean.getBeanCode());//表单编码
					map.put("f_nCode", bean.getfNextCode());//下一节点编码
					map.put("f_joinTable", "T_ASSET_RECE");//表名
					map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
					map.put("f_beanCode", bean.getBeanCode());//表单编码
			 */			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "GDZCJH", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getfNextCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getfReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getfReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getfNextUserName());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			//固定资产附件
			List<Attachment> handleAttaList = attachmentMng.list(bean);
			map.put("attaList", handleAttaList);
			return map;
		}
		
		@RequestMapping("/direReim")
		@ResponseBody
		//直接报销详情
		public Object direReim(String pid){
			Map<String,Object> map = new HashMap<>();
			DirectlyReimbAppliBasicInfo reimBean =  (DirectlyReimbAppliBasicInfo) directlyReimbMng.findById(Integer.valueOf(pid));
			User user = userMng.findById(reimBean.getUser());
			reimBean.setUserName(user.getName());
			reimBean.setDeptName(user.getDepartName());
			Integer detailId = reimBean.getProDetailId();
			Integer indexId = reimBean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//指标名称
					reimBean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
					//批复金额
					reimBean.setPfAmount(detail.getOutAmount());		
					//预算年度
					if (index.getAppDate() != null) {
						reimBean.setPfDate(index.getYears());				
					}
					//使用部门
					reimBean.setPfDepartName(index.getDeptName());			
					//可用余额
					reimBean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				reimBean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				reimBean.setPfAmount(index.getPfAmount());		
				//预算年度
				if (index.getAppDate() != null) {
					reimBean.setPfDate(index.getYears());				
				}
				//使用部门
				reimBean.setPfDepartName(index.getDeptName());			
				//可用余额
				reimBean.setSyAmount(index.getSyAmount());			
			}
			map.put("reimBean", reimBean);
			List<DirectlyReimbDetail> mingxiList = directlyReimbDetailMng.getMingxi(Integer.valueOf(pid));
			map.put("mingxiList", mingxiList);
			List<AppInvoiceInfo> invoiceList =invoiceMng.findByRID("0", Integer.valueOf(pid));
			for (AppInvoiceInfo appInvoiceInfo : invoiceList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("invoiceList", invoiceList);
			List<Attachment> attaList = attachmentMng.list(reimBean);
			map.put("attaList", attaList);
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByDrId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			//工作流字段
			String userId = reimBean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			
			String strType = "";
			if("ZJBXLX-0".equals(reimBean.getDirType())){
				strType = "ZJBXLX-0";
			}
			if("ZJBXLX-1".equals(reimBean.getDirType())){
				strType = "ZJBXLX-1";
			}
			if("ZJBXLX-2".equals(reimBean.getDirType())){
				strType = "ZJBXLX-2";
			}
			
			
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), reimBean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", reimBean.getBeanCode());//表单编码
			map.put("f_nCode", reimBean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_DIRECTLY_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", reimBean.getBeanCodeField());//编码字段
			map.put("f_beanCode", reimBean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, strType, applyUser.getDepart().getId(),reimBean.getBeanCode(),reimBean.getnCode(), reimBean.getJoinTable(),reimBean.getBeanCodeField(), reimBean.getBeanCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  reimBean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(reimBean.getCheckStauts().equals("-1")||reimBean.getCheckStauts().equals("-4")||reimBean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(reimBean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(reimBean.getNextCheckKey())){
						map.put("当前流程节点人", reimBean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		@RequestMapping("/loan")
		@ResponseBody
		//详情-借款审批详情-借款审批
		public Object loan(String pid){
			Map<String,Object> map = new HashMap<>();

			LoanBasicInfo bean = loanMng.findById(Integer.valueOf(pid));
			LoanPayeeInfo payeeBean = loanPayeeMng.findBylId(Integer.valueOf(pid)).get(0);

			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			bean.setUserName(applyUser.getName());
			map.put("loanInfo", bean);
			map.put("borrower", payeeBean);
			map.put("foCode", bean.getBeanCode());

			//工作流字段

			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", "JKSQ");//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), bean.getDeptName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}

			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", bean.getJoinTable());//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getlCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "JKSQ", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getlCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			User user = userMng.findById(userId);
			map.put("流程发起人", user.getName());
			map.put("发起人部门", user.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getFlowStauts().equals("-1")||bean.getFlowStauts().equals("-4")||bean.getFlowStauts().equals("0")){
				map.put("当前流程节点人", user.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getFlowStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}

		//获取流程（整合审批记录）
		@RequestMapping("/getNodeWithLog")
		@ResponseBody
		public Object getNodeWithLog(String operUserId,String FBusiArea, String departId,String foCode,String nextKey,String  tableName,String beanCodeField,String beanCode){
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(operUserId, FBusiArea, departId,foCode,nextKey, tableName, beanCodeField, beanCode,"1");//参考ApplyCheckController 242
			return nodeConfList;
		}
		
		
		//获取流程（整合审批记录）
		@RequestMapping("/getInSideAdjustNodeConf")
		@ResponseBody
		public Object getInSideAdjustNodeConf(String operUserId,String FBusiArea, String departId,String foCode,String nextKey,String  tableName,String beanCodeField,String beanCode){

			List<TNodeData>  nodeConfList = tProcessCheckMng.getInSideAdjustNodeConf(operUserId, departId, beanCode);
			return nodeConfList;
		}
		
		//获取事前申请新增流程（整合审批记录）
		@RequestMapping("/getApplyNodeConf")
		@ResponseBody
		public Object getApplyNodeConf(String userId,String type){
			Map<String,Object> map = new HashMap<>();
			User user = userMng.findById(userId);
			String strType ="";
			if(type.equals("8")){
				 strType = "JKSQ";
			}else if(type.equals("0")){
				 strType = "ZJBX";
			}else if(type.equals("9")){
				 strType = "HKDJ";
			}else{
				 strType = tProcessCheckMng.JudgmentProcess(String.valueOf(type));
			}
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),strType, user.getDpID(),null,null, null, null, null, "1");
			TNodeData node =new TNodeData();
			node.setUser(user);
			TProcessCheck checkInfo =new TProcessCheck();
			//checkInfo.setFcheckTime(bean.getReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//获取报销申请新增流程（整合审批记录）
		@RequestMapping("/getReimbNodeConf")
		@ResponseBody
		public Object getReimbNodeConf(String userId,String pId){
			User user = userMng.findById(userId);
			ApplicationBasicInfo applyBean =applyMng.findById(Integer.valueOf(pId));
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),strType, user.getDpID(),null,null, null, null, null, "1");
			return nodeConfList;
		}
		
		
		//根据项目id判断项目类型
		@RequestMapping("/getProTypeById")
		@ResponseBody
		public Object getProTypeById(String pid){
			TProBasicInfo pro = proMng.findById(Integer.valueOf(pid));
			if (pro != null) {
				return pro.getFProOrBasic();
			}
			return null;
		}
		
		//通用事项报销申请新增
		@RequestMapping("/commReimb_add")
		@ResponseBody
		//通用事项
		public Object commReimb_add(String pid,String userId){
			Map<String,Object> map = new HashMap<>();
			//基本信息
			ApplicationBasicInfo applyBean = applyMng.findById(Integer.valueOf(pid));
			//reimburseType报销类型
			ReimbAppliBasicInfo bean = new ReimbAppliBasicInfo();
			bean.setgCode(applyBean.getgCode());
			bean.setgName(applyBean.getgName());
			bean.setFupdateStatus(0);
			//获取报销人信息
			User user =userMng.findById(userId);
			bean.setType("1");
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			bean.setWithLoan(0);
			//查询四级指标信息
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = applyBean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
				bean.setIndexName(applyBean.getIndexName());
				bean.setIndexId(applyBean.getIndexId());
			}
			
			
			//费用明细
			List<Object> mxList = applyMng.getObjectList("ApplicationDetail", "gId", Integer.valueOf(pid));
			map.put("reimbInfo", bean);
			map.put("mxList", mxList);
			//报销信息
			 
			return map;
		}
		
		//公车报销申请新增
		@RequestMapping("/carReimb_add")
		@ResponseBody
		public Object carReimb_add(String pid,String userId){
			Map<String,Object> map = new HashMap<>();
			//基本信息
			ApplicationBasicInfo applyBean = applyMng.findById(Integer.valueOf(pid));
			//reimburseType报销类型
			ReimbAppliBasicInfo bean = new ReimbAppliBasicInfo();
			bean.setgCode(applyBean.getgCode());
			bean.setgName(applyBean.getgName());
			bean.setFupdateStatus(0);
			//获取报销人信息
			User user =userMng.findById(userId);
			bean.setType("6");
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			bean.setWithLoan(0);
			//查询四级指标信息
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = applyBean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
				bean.setIndexName(applyBean.getIndexName());
				bean.setIndexId(applyBean.getIndexId());
			}


			//费用明细
			List<Object> mxList = applyMng.getMingxi("OfficeCar", "gId",applyBean.getgId());
			map.put("reimbInfo", bean);
			map.put("mxList", mxList);
			
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),strType, getUser().getDpID(),null,bean.getnCode(), null, null, null, "1");
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//通用事项报销申请修改和查看
		@RequestMapping("/commReimb_detail")
		@ResponseBody
		public Object commReimb_detail(String pid){
			Map<String,Object> map = new HashMap<>();
			//reimburseType报销类型
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(pid));
			//基本信息
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			//获取报销人信息
			String userId = bean.getUserId();
			User user =userMng.findById(userId);
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			//查询四级指标信息
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = applyBean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
				bean.setIndexName(applyBean.getIndexName());
				bean.setIndexId(applyBean.getIndexId());
			}
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);

			//费用明细
			List<Object> mxList = applyMng.getObjectList("ApplicationDetail", "rId", Integer.valueOf(pid));
			map.put("reimbInfo", bean);
			map.put("mxList", mxList);
			//发票信息
			List<AppInvoiceInfo> invoiceList =invoiceMng.findByRID("1", Integer.valueOf(pid));
			for (AppInvoiceInfo appInvoiceInfo : invoiceList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("invoiceList", invoiceList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			map.put("applyId", applyBean.getgId());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//工作流字段
			
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			return map;
		}
		
		//公车报销申请
		@RequestMapping("/carReimb_detail")
		@ResponseBody
		public Object carReimb_detail(String pid){
			Map<String,Object> map = new HashMap<>();
			//基本信息
			//传回来的id是主键
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(pid));
			//获取报销人信息
			User user = userMng.findById(bean.getUser());
			
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			
			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//查询四级指标信息
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = bean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
			}
			bean.setIndexName(applyBean.getIndexName());


			//费用明细
			List<Object> mxList = applyMng.getMingxi("OfficeCar", "rId",bean.getrId());
			map.put("reimbInfo", bean);
			map.put("applyInfo", applyBean);
			map.put("mxList", mxList);
			//发票信息
			List<AppInvoiceInfo> invoiceList =invoiceMng.findByRID("1", Integer.valueOf(pid));
			for (AppInvoiceInfo appInvoiceInfo : invoiceList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("invoiceList", invoiceList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			//map.put("nodeConfList", nodeConfList);
			map.put("applyId", applyBean.getgId());
			return map;
		}
		
		
		//会议报销申请
		@RequestMapping("/meetingReimb_detail")
		@ResponseBody
		public Object meetingReimb_detail(String pid){
			Map<String,Object> map = new HashMap<>();
			//基本信息
			//传回来的id是主键
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(pid));
			//获取报销人信息
			User user = userMng.findById(bean.getUser());

			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());

			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//查询四级指标信息
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = bean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
			}
			bean.setIndexName(applyBean.getIndexName());
			MeetingAppliInfo reimbMeetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "rId", bean.getrId());
			map.put("reimbMeetingBean", reimbMeetingBean);
			//会议日程
			List<Object> planList = applyMng.getObjectList("MeetingPlan", "rId", bean.getrId());
			map.put("planList", planList);
			//费用明细
			List<Object> mxList = applyMng.getMingxi("ApplicationDetail", "rId", bean.getrId());
			map.put("reimbInfo", bean);
			map.put("applyInfo", applyBean);
			map.put("mxList", mxList);
			//发票信息
			List<AppInvoiceInfo> invoiceList =invoiceMng.findByRID("1", Integer.valueOf(pid));
			for (AppInvoiceInfo appInvoiceInfo : invoiceList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("invoiceList", invoiceList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			//map.put("nodeConfList", nodeConfList);
			map.put("applyId", applyBean.getgId());
			return map;
		}
		
		//培训报销申请
		@RequestMapping("/trainReimb_detail")
		@ResponseBody
		public Object trainReimb_detail(String pid){
			Map<String,Object> map = new LinkedHashMap<>();
			//基本信息
			//传回来的id是主键
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(pid));
			//获取报销人信息
			User user = userMng.findById(bean.getUser());

			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());

			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//查询四级指标信息
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = bean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
			}
			bean.setIndexName(applyBean.getIndexName());
			TrainingAppliInfo reimbTrainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "rId", bean.getrId());
			map.put("reimbTrainingBean", reimbTrainingBean);
			//讲师信息
			List<Object> lectureList  = applyMng.getObjectList("LecturerInfo", "tId", reimbTrainingBean.gettId());
			map.put("lectureList", lectureList);
			//培训日程
			List<Object> trainPlan = applyMng.getObjectList("MeetPlan", "tId", reimbTrainingBean.gettId());
			map.put("reimbInfo", bean);
			map.put("applyInfo", applyBean);
			map.put("trainPlan", trainPlan);
			//综合预算费用
			List<Object>  mingxiList = applyMng.getMingxi("ApplicationDetail", "rId", bean.getrId());
			map.put("zongheList", mingxiList);
			//师资费-讲课费
			List<TrainTeacherCost> lessonList= applyMng.getTeacherMingxi(reimbTrainingBean.gettId(), "lesson");
			map.put("lessonList", lessonList);
			//师资费-住宿费
			List<TrainTeacherCost> hotelList= applyMng.getTeacherMingxi(reimbTrainingBean.gettId(), "hotel");
			map.put("hotelList", hotelList);
			//师资费-伙食费
			List<TrainTeacherCost> foodList= applyMng.getTeacherMingxi(reimbTrainingBean.gettId(), "food");
			map.put("foodList", foodList);
			//师资费-城市间交通费
			List<TrainTeacherCost> traffic1List= applyMng.getTeacherMingxi(reimbTrainingBean.gettId(), "traffic1");
			map.put("traffic1List", traffic1List);
			//师资费-市内交通费
			List<TrainTeacherCost> traffic2List= applyMng.getTeacherMingxi(reimbTrainingBean.gettId(), "traffic2");
			map.put("traffic2List", traffic2List);
			//发票信息-综合预算发票
			List<AppInvoiceInfo> zongheFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"zhys");
			for (AppInvoiceInfo appInvoiceInfo : zongheFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("zongheFpList", zongheFpList);
			//发票信息-住宿费发票
			List<AppInvoiceInfo> hotelFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"hotel");
			for (AppInvoiceInfo appInvoiceInfo : hotelFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("hotelFpList", hotelFpList);
			//发票信息-伙食费发票
			List<AppInvoiceInfo> foodFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"food");
			for (AppInvoiceInfo appInvoiceInfo : foodFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("foodFpList", foodFpList);
			//发票信息-城市间交通费发票
			List<AppInvoiceInfo> traffic1FpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"traffic1");
			for (AppInvoiceInfo appInvoiceInfo : traffic1FpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("traffic1FpList", traffic1FpList);
			//发票信息-市内交通费发票
			List<AppInvoiceInfo> traffic2FpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"traffic2");
			for (AppInvoiceInfo appInvoiceInfo : traffic2FpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("traffic2FpList", traffic2FpList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			//map.put("nodeConfList", nodeConfList);
			map.put("applyId", applyBean.getgId());
			return map;
		}
		
		//公务接待报销申请
		@RequestMapping("/receptionReimb_detail")
		@ResponseBody
		public Object receptionReimb_detail(String pid){
			Map<String,Object> map = new LinkedHashMap<>();
			//基本信息
			//传回来的id是主键
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(pid));
			//获取报销人信息
			User user = userMng.findById(bean.getUser());

			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());

			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//查询四级指标信息
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = bean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
			}
			bean.setIndexName(applyBean.getIndexName());
			ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "rId", bean.getrId());
			map.put("receptionBean", receptionBean);
			map.put("reimbInfo", bean);
			map.put("applyInfo", applyBean);
			//住宿费
			List<Object> hotelList= applyMng.getObjectList("ReceptionHotel", "rId", bean.getrId());
			map.put("hotelList", hotelList);
			//伙食费
			List<Object> foodList= applyMng.getObjectList("ReceptionFood", "rId", bean.getrId());
			map.put("foodList", foodList);
			//其他费用
			List<Object> otherList= applyMng.getObjectList("ReceptionOther", "rId", bean.getrId());
			map.put("otherList", otherList);
			//发票信息-会议室租金发票
			List<AppInvoiceInfo> rentFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"rent");
			for (AppInvoiceInfo appInvoiceInfo : rentFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("rentFpList", rentFpList);
			//发票信息-住宿费发票
			List<AppInvoiceInfo> hotelFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"hotel");
			for (AppInvoiceInfo appInvoiceInfo : hotelFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("hotelFpList", hotelFpList);
			//发票信息-伙食费发票
			List<AppInvoiceInfo> foodFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"food");
			for (AppInvoiceInfo appInvoiceInfo : foodFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("foodFpList", foodFpList);
			//发票信息-交通费发票
			List<AppInvoiceInfo> trafficFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"traffic");
			for (AppInvoiceInfo appInvoiceInfo : trafficFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("trafficFpList", trafficFpList);
			//发票信息-其他费用发票
			List<AppInvoiceInfo> otherFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"other");
			for (AppInvoiceInfo appInvoiceInfo : otherFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("otherFpList", otherFpList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			//map.put("nodeConfList", nodeConfList);
			map.put("applyId", applyBean.getgId());
			return map;
		}
		
		//公务接待报销申请
		@RequestMapping("/abroadReimb_detail")
		@ResponseBody
		public Object abroadReimb_detail(String pid){
			Map<String,Object> map = new LinkedHashMap<>();
			//基本信息
			//传回来的id是主键
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(pid));
			//获取报销人信息
			User user = userMng.findById(bean.getUser());

			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());

			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//查询四级指标信息
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = bean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
			}
			bean.setIndexName(applyBean.getIndexName());
			AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "rId", bean.getrId());
			map.put("abroadBean", abroadBean);
			map.put("reimbInfo", bean);
			map.put("applyInfo", applyBean);
			//出访计划
			List<Object> planlist = applyMng.getObjectList("AbroadPlan", "rId", bean.getrId());
			map.put("planList", planlist);
			//国际旅费
			List<InternationalTravelingExpense> travelList = internationalTravelingExpenseInfoMng.rfindbygId(bean.getrId());
			for (InternationalTravelingExpense expense : travelList) {
				Vehicle vehicle = vehicleMng.findById(Integer.valueOf(expense.getVehicle()));
				expense.setVehicleText(vehicle.getName());
			}
			map.put("travelList", travelList);
			//交通费
			OutsideTrafficInfo outside =new OutsideTrafficInfo();
			outside.setrId(bean.getrId());
			Pagination p = outsideTrafficInfoMng.routsideTrafficInfoPageList(1, 10000, outside);
			List<OutsideTrafficInfo> trafficList =(List<OutsideTrafficInfo>) p.getList(); 
			map.put("trafficList", trafficList);
			//住宿费
			List<HotelExpenseInfo> hotelList= hotelExpenseInfoMng.rfindbygId(bean.getrId(),null);
			map.put("hotelList", hotelList);
			//伙食费
			List<FoodAllowanceInfo> foodList= foodAllowanceInfoMng.rfindbygId(bean.getrId(),null);
			map.put("foodList", foodList);
			//公杂费
			List<Object> feeList = applyMng.getObjectList("MiscellaneousFeeInfo", "rId", bean.getrId());
			map.put("feeList", feeList);
			//宴请费用
			List<FeteCostInfo> feteList = feteCostInfoMng.rfindbygId(bean.getrId());
			map.put("feteList", feteList);
			//其他费用
			List<Object> otherList= applyMng.getObjectList("ReceptionOther", "rId", bean.getrId());
			map.put("otherList", otherList);
			//发票信息-国际旅费发票
			List<AppInvoiceInfo> travelFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"travel");
			for (AppInvoiceInfo appInvoiceInfo : travelFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("travelFpList", travelFpList);
			//发票信息-住宿费发票
			List<AppInvoiceInfo> hotelFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"hotel");
			for (AppInvoiceInfo appInvoiceInfo : hotelFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("hotelFpList", hotelFpList);
			//发票信息-宴请费发票
			List<AppInvoiceInfo> feteFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"fete");
			for (AppInvoiceInfo appInvoiceInfo : feteFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("foodFpList", feteFpList);
			//发票信息-交通费发票
			List<AppInvoiceInfo> trafficFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"traffic");
			for (AppInvoiceInfo appInvoiceInfo : trafficFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("trafficFpList", trafficFpList);
			//发票信息-其他费用发票
			List<AppInvoiceInfo> otherFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"other");
			for (AppInvoiceInfo appInvoiceInfo : otherFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("otherFpList", otherFpList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			//map.put("nodeConfList", nodeConfList);
			map.put("applyId", applyBean.getgId());
			return map;
		}
		
		//差旅接待报销申请
		@RequestMapping("/travelReimb_detail")
		@ResponseBody
		public Object travelReimb_detail(String pid){
			Map<String,Object> map = new LinkedHashMap<>();
			//基本信息
			//传回来的id是主键
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(pid));
			//获取报销人信息
			User user = userMng.findById(bean.getUser());

			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());

			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//查询四级指标信息
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if (applyBean != null) {
				Integer detailId = applyBean.getProDetailId();
				Integer indexId = bean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//批复金额
						bean.setPfAmount(detail.getOutAmount());		
						//批复时间
						if (index.getAppDate() != null) {
							bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
						}
						//使用部门
						bean.setPfDepartName(index.getDeptName());			
						//可用余额
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//批复金额
					bean.setPfAmount(index.getPfAmount()*10000);		
					//批复时间
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
			}
			bean.setIndexName(applyBean.getIndexName());
			AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "rId", bean.getrId());
			map.put("abroadBean", abroadBean);
			map.put("reimbInfo", bean);
			map.put("applyInfo", applyBean);
			//行程清单
			TravelAppliInfo travel = new TravelAppliInfo();
			travel.setrId(bean.getrId());
			Pagination p = travelAppliInfoMng.rtravelPageList(1, 10000, travel);
			List<TravelAppliInfo> travelPeople = (List<TravelAppliInfo>) p.getList();
			if("GWCC".equals(bean.getTravelType())){
				for(int x=0; x<travelPeople.size(); x++) {
					//序号设置	
					travelPeople.get(x).setTravelAreaId(Integer.valueOf(travelPeople.get(x).getTravelArea()));	
				}
				
			}
			map.put("travelPeople", travelPeople);
			//城市间交通费
			OutsideTrafficInfo outSide = new OutsideTrafficInfo();
			outSide.setrId(bean.getrId());
			Pagination p1 = outsideTrafficInfoMng.routsideTrafficInfoPageList(1, 10000, outSide);
			List<OutsideTrafficInfo> outSideList = (List<OutsideTrafficInfo>) p1.getList();
			map.put("outSideList", outSideList);
			//市内交通费
			InCityTrafficInfo incity = new InCityTrafficInfo();
			incity.setrId(bean.getrId());
			Pagination p2 = inCityTrafficInfoMng.rinCityInfoPageList(1, 10000, incity);
			List<InCityTrafficInfo> incityList =(List<InCityTrafficInfo>) p2.getList();
			map.put("incityList", incityList);
			//住宿费
			List<HotelExpenseInfo> hotelList= hotelExpenseInfoMng.rfindbygId(bean.getrId(),bean.getTravelType());
			map.put("hotelList", hotelList);
			//伙食费
			List<FoodAllowanceInfo> foodList= foodAllowanceInfoMng.rfindbygId(bean.getrId(),bean.getTravelType());
			map.put("foodList", foodList);
			//发票信息-住宿费发票
			List<AppInvoiceInfo> hotelFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"hotel");
			for (AppInvoiceInfo appInvoiceInfo : hotelFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("hotelFpList", hotelFpList);
			//发票信息-交通费发票
			List<AppInvoiceInfo> trafficFpList =invoiceMng.findByCostType("1", Integer.valueOf(pid),"traffic");
			for (AppInvoiceInfo appInvoiceInfo : trafficFpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("trafficFpList", trafficFpList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(pid),"");
			map.put("paymentList", paymentList);
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", strType);//操作人业务范围
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getnCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			//map.put("nodeConfList", nodeConfList);
			map.put("applyId", applyBean.getgId());
			return map;
		}
		
		//获取配置资产目录
		@RequestMapping("/getAssetItem")
		@ResponseBody
		public Object getAssetItem(){
			Map<String,Object> map = new HashMap<>();
			List<AssetType> listAssetType=assetTypeMng.getAppRoots("ZCLX-02");
			map.put("listAssetType", listAssetType);
			return map;
		}
		
		//获取配置资产列表
		@RequestMapping("/getAssetList")
		@ResponseBody
		public Object getAssetList(AssetBasicInfo assetBasicInfo){
			Map<String,Object> map = new HashMap<>();
			Pagination p = assetBasicInfoMng.findbyfFixedType(assetBasicInfo, 1, 10000);
			List<AssetBasicInfo> assetList=(List<AssetBasicInfo>) p.getList();
			map.put("assetList", assetList);
			return map;
		}
		
		//获取选中资产列表
		@RequestMapping("/getChooseAssetList")
		@ResponseBody
		public Object getChooseAssetList(String assetid){
			Map<String,Object> map = new HashMap<>();
			List<ReceConfigList> li =new ArrayList<ReceConfigList>();
			if(!StringUtil.isEmpty(assetid)){
				li = receConfigListMng.getbyAssetid(assetid);
			}
			map.put("configList", li);
			return map;
		}
		
		
		//合同报销新增
		@RequestMapping("/contReimb_add")
		@ResponseBody
		public Object contReimb_add(String userId){
			Map<String,Object> map = new HashMap<>();
			ReimbAppliBasicInfo bean = new ReimbAppliBasicInfo();
			bean.setrCode(StringUtil.Random(""));
			User user = userMng.findById(userId);	//获得合同申请人信息
			bean.setType("8");
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			map.put("bean", bean);
			//根据申请人得到申请部门id
			String departId=departMng.findDeptByUserId(user.getId())[0];
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"HTFKSQ", departId,bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getrCode(), "1");
			TNodeData node =new TNodeData();
			node.setUser(user);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			map.put("nodeConf", nodeConfList);
			return map;
		}
		
		//合同报销选择合同
		@RequestMapping("/chooseContract")
		@ResponseBody
		public Object chooseContract(String userId){
			Map<String,Object> map = new HashMap<>();
			User user =userMng.findById(userId);
			ContractBasicInfo bean = new ContractBasicInfo();
			Pagination p = formulationMng.queryForEnforcing(bean,user, 1, 10000);
			List<ContractBasicInfo> cbi = (List<ContractBasicInfo>) p.getList();
			map.put("contractList", cbi);
			return map;
		}
		
		//加载合同报销付款计划和收款人信息
		@RequestMapping("/contractInfo")
		@ResponseBody
		public Object contractInfo(String id){
			Map<String,Object> map = new HashMap<>();
			ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(id));
			ReceivPlan receivPlan= new ReceivPlan();
			if("1".equals(cbi.getfUpdateStatus())){//有变更
	    		Upt upt = uptMng.findByFContId_U(String.valueOf(cbi.getFcId())).get(0);
	    		if(upt.getfPlanChangeStatus()==1){
	    			receivPlan.setDataType(1);
	    			receivPlan.setfUptId_R(upt.getfId_U());
	    		}
	    	}else{//无变更
	    		receivPlan.setfContId_R(cbi.getFcId());
	    		receivPlan.setDataType(0);
	    	}
	    	List<ReceivPlan> RP = filingMng.getReceivPlan(receivPlan);
			for (int i = 0; i < RP.size(); i++) {
				Lookups lookups=lookupsMng.findByLookCode(RP.get(i).getfReceProof());
				RP.get(i).setfReceProofs(lookups.getName());	
			}
			map.put("receivPlan", RP);
			
			String fbiddingName = "";
			if (cbi.getfContractor() != null) {
				List<BiddingRegist> brlist = cgProcessMng.findByProperty("fbiddingCode", cbi.getfContractor());
				fbiddingName = brlist.get(0).getFbiddingName();
			}
			List<SignInfo> list = signInfoMng.findByProperty("fContId", cbi.getFcId());
			List<ReimbPayeeInfo> payeeList = new ArrayList<ReimbPayeeInfo>();
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setBiddingName(fbiddingName);
				ReimbPayeeInfo payee =new ReimbPayeeInfo();
				payee.setBiddingName(list.get(i).getBiddingName());
				payee.setPayeeName(list.get(i).getBiddingName());
				payee.setfBankName(list.get(i).getfBankName());
				payee.setfCardNo(list.get(i).getfCardNo());
				payeeList.add(payee);
			}
			
			map.put("payeeList", payeeList);
			return map;
		}
		
		//加载已审批完的验收单
		@RequestMapping("/acceptList")
		@ResponseBody
		public Object acceptList(String userId,Integer fcId){
			Map<String,Object> map = new HashMap<>();
			AcceptCheck bean =new AcceptCheck();
			User user =userMng.findById(userId);
			bean.setFcId(fcId);
			bean.setfCheckStauts("9");
			Pagination p = cgReceiveMng.acceptpageList(bean, 1, 10000, user);
			List<AcceptCheck> acceptList=(List<AcceptCheck>) p.getList();
			map.put("acceptList", acceptList);
			return map;
		}
		
		//加载固定/无形资产入帐单
		@RequestMapping("/storageList")
		@ResponseBody
		public Object storageList(String userId,String fcId,Storage storage){
			Map<String,Object> map = new HashMap<>();
			User user =userMng.findById(userId);
			Pagination p = storageMng.chooseStoragelist(storage, null, null, user, 1, 10000,fcId,null);
			if (p == null) {
				map.put("storageList", null);
				return map;
			}
			List<Storage> li= (List<Storage>) p.getList();
	    	for (int i = 0; i < li.size(); i++) {
	    		Lookups lookups=lookupsMng.findByLookCode(li.get(i).getfGainingMethod());
	    		li.get(i).setfGainingMethods(lookups.getName());	
				li.get(i).setNumber(i+1);
			}
			map.put("storageList", li);
			return map;
		}
		
		//判断付款计划是否已经发起
		@ResponseBody
		@RequestMapping(value = "/isInitiate")
		public Result isInitiate(Integer fPlanId ,String fcId){
			//获取付款计划
			List<ReimbAppliBasicInfo> raList = reimbAppliMng.findByProperty("payId", fcId);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < raList.size(); i++) {
				if (!"99".equals(raList.get(i).getStauts())) {
					sb.append(raList.get(i).getReceivplanid()).append(",");
				}
			}
			if(sb.toString().contains(fPlanId+",")){
				return getJsonResult(false, "该付款计划已暂存或已发起，不可重复选择");
			}
			return getJsonResult(true, "");
		}
		
		//查看资产入账单
		@RequestMapping("/storageDetail")
		@ResponseBody
		public Object storageDetail(Integer fId_S){
			Map<String,Object> map = new HashMap<>();
			Storage storage=storageMng.findById(fId_S);
			String fGainingMethods ="";
			if(storage.getfAssType().equals("ZCLX-02")){
				fGainingMethods =LookupsUtil.getNameByCategoryCodeAndCode("QDFS", storage.getfGainingMethod());
			}
			if(storage.getfAssType().equals("ZCLX-03")){
				fGainingMethods =LookupsUtil.getNameByCategoryCodeAndCode("WXZCQDFS", storage.getfGainingMethod());
			}
			storage.setfGainingMethods(fGainingMethods);
			map.put("storage", storage);
			List<Regist> list = new ArrayList<Regist>();
			if(fId_S != null) {		
				//查询申请明细
				list =  registMng.findByProperty("fId_S", fId_S);
				if(list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i).getfDepreciationStatus()!=null){
							if(StringUtils.isNotEmpty(list.get(i).getfDepreciationStatus().getCode())){
								list.get(i).setfDepreciationStatusCode(list.get(i).getfDepreciationStatus().getCode());
							}
							if(StringUtils.isNotEmpty(list.get(i).getfDepreciationStatus().getName())){
								list.get(i).setfDepreciationStatusShow(list.get(i).getfDepreciationStatus().getName());
							}
						}
						if(list.get(i).getfValueType()!=null){
							if(StringUtils.isNotEmpty(list.get(i).getfValueType().getCode())){
								list.get(i).setfValueTypeCode(list.get(i).getfValueType().getCode());
							}
							if(StringUtils.isNotEmpty(list.get(i).getfValueType().getName())){
								list.get(i).setfValueTypeShow(list.get(i).getfValueType().getName());
							}
						}
						if(list.get(i).getfAmortizeStatus()!=null){
							if(StringUtils.isNotEmpty(list.get(i).getfAmortizeStatus().getName())){
								list.get(i).setfAmortizeStatusShow(list.get(i).getfAmortizeStatus().getName());
							}
						}
						if(list.get(i).getfAdminOfficial()!=null){
							if(StringUtils.isNotEmpty(list.get(i).getfAdminOfficial())){
								Depart depart = departMng.findById(list.get(i).getfAdminOfficial());
								list.get(i).setfAdminOfficialShow(depart.getName());
							}
						}
					}
				}
			}
			map.put("registList", list);
			return map;
		}
		
		//合同报销详情
		@RequestMapping("/contReimb_detail")
		@ResponseBody
		public Object contReimb_detail(String id){
			Map<String,Object> map = new HashMap<>();
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(id));
			ContractBasicInfo cbi = formulationMng.findById(Integer.parseInt(bean.getPayId()));
			User user = userMng.findById(bean.getUser());
			bean.setUserName(user.getName());
			map.put("bean", bean);
			ReceivPlan receivPlan= new ReceivPlan();
			if(!StringUtil.isEmpty(bean.getReceivplanid())){
	    		String[] a = bean.getReceivplanid().split(",");
	    		receivPlan = receivPlanMng.findById(Integer.valueOf(a[0]));
	    	}
	    	List<ReceivPlan> RP = receivPlanMng.findByTypeAndId(receivPlan);
			for (int i = 0; i < RP.size(); i++) {
				Lookups lookups=lookupsMng.findByLookCode(RP.get(i).getfReceProof());
				RP.get(i).setfReceProofs(lookups.getName());	
			}
			map.put("receivPlan", RP);
			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//收款人信息
			List<ReimbPayeeInfo> paymentList = reimbPayeeMng.getByRId(Integer.valueOf(id),"");
			for (ReimbPayeeInfo reimbPayeeInfo : paymentList) {
				reimbPayeeInfo.setfBankName(reimbPayeeInfo.getBank());
				reimbPayeeInfo.setfCardNo(reimbPayeeInfo.getBankAccount());
			}
			map.put("paymentList", paymentList);
			//发票信息
			List<AppInvoiceInfo> fpList =invoiceMng.findByRID("1", Integer.valueOf(id));
			for (AppInvoiceInfo appInvoiceInfo : fpList) {
				Attachment attc =attachmentMng.findById(appInvoiceInfo.getfFileId());
				appInvoiceInfo.setfFileSrc(attc.getFileUrl());
			}
			map.put("fpList", fpList);
			
			//工作流字段
			String userId = bean.getUserId();
			User applyUser = userMng.findById(userId);
			/*map.put("f_userId", userId);//操作人主键
					map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
					map.put("f_area", "GDZCLY");//操作人业务范围
					Proposer proposer = new Proposer(bean.getfOperator(), bean.getfReceDept(), bean.getfOperatorTime());
					String userName = proposer.getUserName();
					if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
						map.put("f_userName", userName);
					}
					map.put("f_foCode", bean.getBeanCode());//表单编码
					map.put("f_nCode", bean.getfNextCode());//下一节点编码
					map.put("f_joinTable", "T_ASSET_RECE");//表名
					map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
					map.put("f_beanCode", bean.getBeanCode());//表单编码
			 */			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(userId, "HTFKSQ", applyUser.getDepart().getId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getBeanCode(),"1");
			TNodeData node =new TNodeData();
			node.setUser(applyUser);
			TProcessCheck checkInfo =new TProcessCheck();
			checkInfo.setFcheckTime(bean.getReimburseReqTime());
			node.setCheckInfo(checkInfo);
			nodeConfList.add(node);
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getReimburseReqTime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size());
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size());
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getUserName2());
						map.put("当前流程节点数", i+1);
					}
				}
			}
			map.put("nodeConfList", nodeConfList);
			return map;
		}
		
		//还款新增
		@RequestMapping("/payment_add")
		@ResponseBody
		public Object payment_add(String userId,String id){
			Map<String,Object> map = new LinkedHashMap<>();
			/*Payment bean =new Payment();
			bean.setPayTime(new Date());
			bean.setCode(StringUtil.Random("HK"));*/
			
			map.put("code",StringUtil.Random("HK"));
			User user = userMng.findById(userId);
			LoanBasicInfo loan=loanMng.findById(Integer.valueOf(id));
			loan.setUserName(user.getName());
			map.put("loan",loan);
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanId(id);
			List<ReimbAppliBasicInfo> applyReimbList =reimbAppliMng.findByLoanId(id);
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			int a = 0;
			int b = 0;
			if(applyReimbList.isEmpty()){
				a =0;
			}else{
				a=applyReimbList.size();
			}
			if(directlyList.isEmpty()){
				b=0;
			}else{
				b=directlyList.size();
			}
			map.put("cxNum",  a+b);
			map.put("totalCxAmount", totalCxAmount);
			map.put("reimbList", reimbList);
			//查询个人收款信息
			LoanPayeeInfo payeeBean = new LoanPayeeInfo();
			List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
			if(infoList.size()>0) {
				payeeBean.setBank(infoList.get(0).getBank());//银行
				payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
				payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
				payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
				payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
				payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
				payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
				payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
			}
			map.put("payee", payeeBean);
			return map;
		}
		
		//还款详情
		@RequestMapping("/payment_detail")
		@ResponseBody
		public Object payment_detail(String id){
			Map<String,Object> map = new HashMap<>();
			Payment bean = paymentMng.findById(Integer.valueOf(id));
			
			LoanBasicInfo loan=loanMng.findById(bean.getlId());
			
			if(bean==null){
				bean = new Payment();
			}
			map.put("bean", bean);
			String userId = bean.getApplierId();
			User applyUser = userMng.findById(userId);
			loan.setUserName(applyUser.getName());
			map.put("loan", loan);
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanId(id);
			List<ReimbAppliBasicInfo> applyReimbList =reimbAppliMng.findByLoanId(id);
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			int a = 0;
			int b = 0;
			if(applyReimbList.isEmpty()){
				a =0;
			}else{
				a=applyReimbList.size();
			}
			if(directlyList.isEmpty()){
				b=0;
			}else{
				b=directlyList.size();
			}
			map.put("cxNum",  a+b);
			map.put("totalCxAmount", totalCxAmount);
			map.put("reimbList", reimbList);
			//查询个人收款信息
			LoanPayeeInfo payeeBean = new LoanPayeeInfo();
			List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(userId);
			if(infoList.size()>0) {
				payeeBean.setBank(infoList.get(0).getBank());//银行
				payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
				payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
				payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
				payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
				payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
				payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
				payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
			}
			map.put("payee", payeeBean);
			List<Attachment> attaList = attachmentMng.list(bean);
			map.put("attaList", attaList);
			//工作流字段
		
			map.put("f_userId", userId);//操作人主键
			map.put("f_departId", applyUser.getDepart()!=null? applyUser.getDepart().getId() : null);//发起人部门主键
			map.put("f_area", "HKDJ");//操作人业务范围
			Proposer proposer = new Proposer(applyUser.getName(), applyUser.getDepartName(), bean.getCreatetime());
			String userName = proposer.getUserName();
			if (!StringUtil.isEmpty(userId)) {//首个审批人姓名
				map.put("f_userName", userName);
			}
			map.put("f_foCode", bean.getBeanCode());//表单编码
			map.put("f_nCode", bean.getNextNodeCode());//下一节点编码
			map.put("f_joinTable", "T_REIMB_APPLI_BASIC_INFO");//表名
			map.put("f_beanCodeField", bean.getBeanCodeField());//编码字段
			map.put("f_beanCode", bean.getBeanCode());//表单编码
			//查询工作流节点
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"HKDJ",applyUser.getDpID(),bean.getBeanCode(),bean.getNextNodeCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getBeanCode(),"1");
			Collections.reverse(nodeConfList);
			//流程发起人信息
			//User user = userMng.findById(userId);
			map.put("流程发起人", applyUser.getName());
			map.put("发起人部门", applyUser.getDepartName());
			map.put("发起时间",  bean.getCreatetime());
			//工作流节点信息
			map.put("总节点数", nodeConfList.size()+1);
			if(bean.getCheckStauts().equals("-1")||bean.getCheckStauts().equals("-4")||bean.getCheckStauts().equals("0")){
				map.put("当前流程节点人", applyUser.getName());
				map.put("当前流程节点数", 1);
			}else if(bean.getCheckStauts().equals("9")){
				map.put("当前流程节点人", "已完结");
				map.put("当前流程节点数", nodeConfList.size()+1);
			}else{
				for(int i=0;i<nodeConfList.size();i++){
					if(String.valueOf(nodeConfList.get(i).getKeyId()).equals(bean.getNextCheckKey())){
						map.put("当前流程节点人", bean.getNextUserName());
						map.put("当前流程节点数", i+2);
					}
				}
			}
			return map;
		}
}
