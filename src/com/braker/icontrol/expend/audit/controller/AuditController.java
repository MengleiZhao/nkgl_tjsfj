package com.braker.icontrol.expend.audit.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.MatheUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgpay.manager.CgPayApplyMng;
import com.braker.icontrol.cgmanage.cgpay.manager.CgPayCheckMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgBidMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgSelMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BidRegist;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.dispute.manager.DisputeMng;
import com.braker.icontrol.contract.dispute.model.Dispute;
import com.braker.icontrol.contract.enforcing.manager.ContPayMng;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.ContPay;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.expend.apply.manager.ApplicationBasicInfoMng;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.OfficeCar;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.audit.manager.AuditInfoMng;
import com.braker.icontrol.expend.audit.model.AuditInfo;
import com.braker.icontrol.expend.cashier.manager.CashierMng;
import com.braker.icontrol.expend.loan.manager.LoanMng;
import com.braker.icontrol.expend.loan.manager.LoanPayeeMng;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.loan.model.LoanPayeeInfo;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbAttacMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAttacMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.model.ContractReimburseInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbPayeeInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;
import com.braker.icontrol.incomemanage.capital.manager.CapitalMng;
import com.braker.icontrol.incomemanage.capital.model.FundPayforInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * ????????????????????????
 * @author ?????????
 * @createtime 2018-08-16
 * @updatetime 2018-08-16
 */
@Controller
@RequestMapping(value = "/audit")
public class AuditController extends BaseController {
	
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	
	@Autowired
	private DirectlyReimbPayeeMng directlyReimbPayeeMng;
	
	@Autowired
	private DirectlyReimbAttacMng directlyReimbAttacMng;
	
	@Autowired
	private TBasicItfMng basicItfMng;
	
	@Autowired
	private TProItfMng itfMng;
	
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private ReimbPayeeMng reimbPayeeMng;
	
	@Autowired
	private ReimbAttacMng reimbAttacMng;
	
	@Autowired
	private ApplicationBasicInfoMng applicationBasicInfoMng;
	
	@Autowired
	private ApplyMng applyMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private AuditInfoMng auditInfoMng;
	
	@Autowired
	private CgPayCheckMng cgcheckpayMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private CgPayApplyMng cgPayApplyMng;
	
	@Autowired
	private CgBidMng cgbidMng;
	
	@Autowired
	private CgSelMng cgselMng;
	
	@Autowired
	private CgReceiveMng cgreceiveMng;
	
	@Autowired
	private FilingMng filingMng;
	
	
	@Autowired
	private FormulationMng formulationMng;
	
	@Autowired
	private ReceivPlanMng receivPlanMng;
	
	@Autowired
	private ContPayMng contPayMng;
	
	@Autowired
	private CapitalMng capitalMng;
	
	@Autowired
	private LoanMng loanMng;
	
	@Autowired
	private LoanPayeeMng loanPayeeMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private CashierMng cashierMng;
	
	@Autowired
	private DisputeMng disputeMng;
	
	@Autowired
	private UptMng uptMng;
	
	//??????
	@Autowired
	private AttachmentMng attachmentMng;				//??????
	
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng;	//??????????????????
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/*
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2018-08-16
	 * @updatetime 2018-08-16
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/expend/audit/audit_list";
	}
	
	/*
	 * ???????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-13
	 * @updatetime 2018-08-13
	 */
	@RequestMapping(value = "/reimbursePage")
	@ResponseBody
	public JsonPagination reimbursePage(DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean, String reimburseType,Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = null;
    	//0???????????????1???5???????????????
    	if(reimburseType.equals("0")) {
    		p = directlyReimbMng.auditPageList(drBean, page, rows, getUser());
    		List<DirectlyReimbAppliBasicInfo> li = (List<DirectlyReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//????????????	
    			li.get(x).setNum((x+1)+(page-1)*rows);
    		}
    	} else {
    		p = reimbAppliMng.auditPageList(rBean, page, rows, getUser());
    		List<ReimbAppliBasicInfo> li = (List<ReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//????????????	
    			li.get(x).setNum((x+1)+(page-1)*rows);
    		}
    	}
		return getJsonPagination(p, page);
	}
	
	/*
	 * ?????????????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-17
	 * @updatetime 2018-08-17
	 */
	@RequestMapping(value = "/purchasePage")
	@ResponseBody
	public JsonPagination purchasePage(PurchaseApplyBasic bean, String sort, String order, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgcheckpayMng.auditPageList(bean, page, rows, getUser());
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//????????????	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * ?????????????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-22
	 * @updatetime 2018-08-22
	 */
	@RequestMapping(value = "/contractPage")
	@ResponseBody
	public JsonPagination contractPage(ContPay payBean, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = contPayMng.contractCheckPageList(payBean, page, rows, getUser(),"1");//0???checkType(0??????????????????1???????????????)
		List<ContractReimburseInfo> li = (List<ContractReimburseInfo>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//????????????	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}
	
	/**
	 * 
	* @author:??????
	* @Title: auditReimburse 
	* @Description: ??????
	* @param id
	* @param reimburseType    0??????????????????1??????????????????2??????????????????3???????????????
	* @param model
	* @param cashier
	* @return
	* @return String    ???????????? 
	* @date??? 2019???8???21?????????10:41:31 
	* @throws
	 */
	@RequestMapping("/auditReimburse")
	public String auditReimburse(Integer id ,String reimburseType ,ModelMap model, String cashier) {
		model.addAttribute("userInfo", getUser());
		if(reimburseType.equals("0")){
			//????????????id?????????
			DirectlyReimbAppliBasicInfo bean = directlyReimbMng.findById(id);
			//?????????????????????
			User user = userMng.findById(bean.getUser());
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			
			model.addAttribute("bean", bean);
			
			//?????????????????????
			DirectlyReimbPayeeInfo payeeBean = directlyReimbPayeeMng.getByDrId(id).get(0);
			List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
			if(infoList.size()>0) {
				payeeBean.setBank(infoList.get(0).getBank());//??????
				payeeBean.setBankAccount(infoList.get(0).getBankAccount());//????????????
				payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//???????????????
				payeeBean.setZfbQR(infoList.get(0).getZfbQR());//????????????????????????
				payeeBean.setWxAccount(infoList.get(0).getWxAccount());//????????????
				payeeBean.setWxQR(infoList.get(0).getWxQR());//?????????????????????
			}
			model.addAttribute("payee", payeeBean);
			
			//??????????????????
			List<Attachment> attaList = attachmentMng.list(bean);
			model.addAttribute("attaList", attaList);
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"BXSQ", bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getDrCode(),"1");
			model.addAttribute("nodeConf", nodeConfList);
			Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("BXSQ", bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());
			//????????????????????????
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
			model.addAttribute("cheterInfo", cheterInfo);
			
			model.addAttribute("detail", "1");
			if(cashier.equals("1")) {
				return "/WEB-INF/view/expend/cashier/directly_reimburse_cashier";
			} else {
				return "/WEB-INF/view/expend/audit/directly_reimburse_audit";
			}
		} else {
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
			
			//?????????????????????
			User user = userMng.findById(bean.getUser());
			
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			bean.setReqTime(bean.getReimburseReqTime());
			
			model.addAttribute("bean", bean);
			
			//?????????????????????
			ReimbPayeeInfo payeeBean = reimbPayeeMng.getByRId(id,"").get(0);
			List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
			if(infoList.size()>0) {
				payeeBean.setBank(infoList.get(0).getBank());//??????
				payeeBean.setBankAccount(infoList.get(0).getBankAccount());//????????????
				payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//???????????????
				payeeBean.setZfbQR(infoList.get(0).getZfbQR());//????????????????????????
				payeeBean.setWxAccount(infoList.get(0).getWxAccount());//????????????
				payeeBean.setWxQR(infoList.get(0).getWxQR());//?????????????????????
			}
			model.addAttribute("payee", payeeBean);
			
			//??????????????????
			List<Attachment> attaList = attachmentMng.list(bean);
			model.addAttribute("attaList", attaList);
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"BXSQ", bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getrCode(),"1");
			model.addAttribute("nodeConf", nodeConfList);
			Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("BXSQ", bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());
			//????????????????????????
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
			model.addAttribute("cheterInfo", cheterInfo);
			
			//?????????????????????????????????
			ApplicationBasicInfo abi = applicationBasicInfoMng.getByGCode(bean.getgCode());
			//??????????????????
			String type = abi.getType();
			if(type.equals("1")){
				
			} else if(type.equals("2")) {
				//??????????????????
				MeetingAppliInfo meetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "gId", abi.getgId());
				model.addAttribute("meetingBean", meetingBean);
			} else if(type.equals("3")) {
				//??????????????????
				TrainingAppliInfo trainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "gId", abi.getgId());
				model.addAttribute("trainingBean", trainingBean);
			} else if(type.equals("4")) {
				//??????????????????
				TravelAppliInfo travelBean = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", abi.getgId());
				model.addAttribute("travelBean", travelBean);
				/*//????????????????????????
				TravelPeopleInfo tPeopBean = (TravelPeopleInfo) applyMng.getObject("TravelPeopleInfo", "trId", travelBean.getTrId());
				model.addAttribute("tPeopBean", tPeopBean);*/
			} else if(type.equals("5")) {
				//??????????????????
				ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "gId", abi.getgId());
				model.addAttribute("receptionBean", receptionBean);
			} else if(type.equals("6")) {
				//????????????????????????
				OfficeCar officeBean = (OfficeCar) applyMng.getObject("OfficeCar", "abi.gId", id);
				model.addAttribute("officeCar", officeBean);
			} else if(type.equals("7")) {
				//????????????????????????
				AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "abi.gId", id);
				model.addAttribute("abroad", abroadBean);
			}
			
			model.addAttribute("detail", "1");
			model.addAttribute("detail2", "1");
			if(cashier.equals("1")) {
				return "/WEB-INF/view/expend/cashier/reimburse_cashier";
			} else {
				return "/WEB-INF/view/expend/audit/reimburse_audit";
			}
		}
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-18
	 * @updatetime 2018-08-18
	 */
	@RequestMapping("/auditPurchase")
	public String auditPurchase(Integer id ,ModelMap model, String cashier) {
		//id????????????fpid
		//??????????????????
		PurchaseApplyBasic bean = cgsqMng.findById(Integer.valueOf(id));
		model.addAttribute("bean",bean);
		//??????fpid??????????????????
		BidRegist bidregist = cgbidMng.getBidRegistByPId(Integer.valueOf(id));
		//?????????????????????????????????????????????
		WinningBidder fwbean = cgselMng.findById(bidregist.getFwId());
		model.addAttribute("fwbean", fwbean);
		
		List<AcceptCheck> historyList=cgreceiveMng.checkHistory(Integer.valueOf(id));
		AcceptCheck historybean =new AcceptCheck();
		if(historyList!=null && historyList.size()>0){
			historybean=historyList.get(0);
			//???????????????????????????
			List<Attachment> hisattac =attachmentMng.list(historybean);
			model.addAttribute("hisattac", hisattac);
		}
		model.addAttribute("historybean", historybean);
		
		
		//????????????????????????
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("??????????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"CGPAY", getUser().getDpID(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("CGPAY", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",bean.getBeanCode());
		if(cashier.equals("1")) {
			model.addAttribute("userInfo", getUser());
			return "/WEB-INF/view/expend/cashier/purchase_cashier";
		} else {
			return "/WEB-INF/view/expend/audit/purchase_audit";
		}
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-22
	 * @updatetime 2018-08-22
	 */
	@RequestMapping("/auditContract")
	public String auditContract(Integer id , ModelMap model, Integer fPlanId, Integer payId, String cashier) {
		ContractBasicInfo contractBasicInfo=filingMng.findById(Integer.valueOf(id));
		User user = userMng.findById(contractBasicInfo.getfOperatorId());	//???????????????????????????
		model.addAttribute("bean", contractBasicInfo);
		model.addAttribute("openType", "edit");
		//Upt upt=uptMng.findByFContId_U(id).get(0);
		//model.addAttribute("Upt", upt);
		//model.addAttribute("UptAttac", uptAttacMng.findByfId_U_AU(upt.getfId_U()).get(0));
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//
		SignInfo sign=new SignInfo();
		if(filingMng.find_Sign_number(contractBasicInfo)>0){
			sign = filingMng.find_Sign(contractBasicInfo).get(0);
			model.addAttribute("signInfo", sign);
		}
		// ?????????????????????
		List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
		model.addAttribute("formulationAttaList", attaList);
		//??????????????????
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		//??????????????????
		Upt upt =new Upt();
		List<Upt> uptList = uptMng.findByFContId_U(String.valueOf(id));
		if(uptList!=null&&uptList.size()>0){
			upt = uptList.get(0);
			model.addAttribute("Upt", upt);
		}
		//??????????????????
		List<Attachment> uptAttaList = attachmentMng.list(upt);
		model.addAttribute("changeAttaList", uptAttaList);
		//??????????????????
		Dispute dis =new Dispute();
		List<Dispute> disList = disputeMng.findByContId(String.valueOf(id));
		if(disList!=null&&disList.size()>0){
			dis = disList.get(0);
			model.addAttribute("dispute",dis);
		}
		//??????????????????
		List<Attachment> disputeAttaList = attachmentMng.list(dis);
		model.addAttribute("disputeAttaList", disputeAttaList);
		//????????????????????????
		ReceivPlan payBean = receivPlanMng.findById(fPlanId);
		model.addAttribute("payBean", payBean);
		//????????????????????????
		ContPay contPay=new ContPay();
		if(contPayMng.findByFPlanid(payBean.getfPlanId()).size()!=0) {
			contPay = contPayMng.findByFPlanid(payBean.getfPlanId()).get(0);
			model.addAttribute("contBean", contPay);
		}
		//????????????????????????
		List<Attachment> contPayattaList = attachmentMng.list(contPay);
		model.addAttribute("enforciongAttaList", contPayattaList);
		
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(contPay.getUserId(),"HTFKSQ", contPay.getDepateID(),contPay.getBeanCode(),contPay.getnCode(),contPay.getJoinTable(), contPay.getBeanCodeField(),contPay.getPayCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		Proposer proposer = new Proposer(user.getName(), contPay.getDepateName(), contPay.getReqTime());
		model.addAttribute("proposer", proposer);
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTFKSQ", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",contPay.getBeanCode());
		if(cashier.equals("1")) {
			model.addAttribute("userInfo", getUser());
			return "/WEB-INF/view/expend/cashier/contract_cashier";
		} else {
			return "/WEB-INF/view/expend/audit/contract_audit";
		}
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-16
	 * @updatetime 2018-08-16
	 */
	@RequestMapping(value = "/reimburseAuditResult")
	@ResponseBody
	public Result reimburseCheckResult(TProcessCheck checkBean, DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean,String spjlFile ) {
		try {
			List<Role> roleli = userMng.getUserRole(getUser().getId());
			if(drBean.getDrId() != null) {
			/*	drBean = directlyReimbMng.findById(drBean.getDrId());
				auditBean.setDrId(drBean.getDrId());*/
				directlyReimbMng.check(checkBean, drBean, getUser(),spjlFile);
			}
			if(rBean.getrId() != null) {
				/*rBean = reimbAppliMng.findById(rBean.getrId());
				auditBean.setrId(rBean.getrId());*/
				
				if("1".equals(rBean.getType())){
					//????????????????????????
					reimbAppliMng.saveCheckInfoTYSXBX(checkBean, rBean, getUser(),spjlFile);
				}else{
					reimbAppliMng.saveCheckInfo(checkBean, rBean, getUser(),spjlFile);
				}
			}
		//	auditInfoMng.saveReimburseAuditInfo(auditBean, drBean, rBean, getUser(), roleli.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-18
	 * @updatetime 2018-08-18
	 */
	@RequestMapping(value = "/purchaseAuditResult")
	@ResponseBody
	public Result purchaseAuditResult(TProcessCheck checkBean, PurchaseApplyBasic purchaseBean,String spjlFile) {
		try {
			cgPayApplyMng.savePurchaseAuditInfo(checkBean, purchaseBean, getUser(),spjlFile);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-22
	 * @updatetime 2018-08-22
	 */
	@RequestMapping(value = "/contractAuditResult")
	@ResponseBody
	public Result contractAuditResult(TProcessCheck checkBean,ContPay bean, ReceivPlan receivPlanBean, Integer payId, String result,String spjlFile) {
		try {
			receivPlanBean = receivPlanMng.findById(receivPlanBean.getfPlanId());//??????????????????
			contPayMng.saveContractCheckInfo(checkBean, bean, receivPlanBean, getUser(),spjlFile);
		//	auditInfoMng.saveContractAuditInfo(auditBean, bean, receivPlanBean, getUser(),result);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/*
	 * ??????????????????????????????
	 * @author ??????
	 * @createtime 2018-11-02
	 * @updatetime 2018-11-02
	 */
	@RequestMapping(value = "/capitalAuditResult")
	@ResponseBody
	public Result capitalAuditResult(AuditInfo auditBean, String lId) {
		try {
			//?????????,????????????
			LoanBasicInfo loadBean = loanMng.findById(Integer.valueOf(lId));
			loadBean.setFrepayStatus("9");
			loanMng.saveOrUpdate(loadBean);
			//??????,?????????????????????
			TBudgetIndexMgr index = budgetIndexMgrMng.findById(loadBean.getIndexId());
			index.setSyAmount(MatheUtil.add(index.getSyAmount(), loadBean.getlAmount()/10000));
			budgetIndexMgrMng.saveOrUpdate(index);
			//????????????????????????????????????
			auditBean.setLoadId(loadBean.getId());
			auditInfoMng.saveOrUpdate(auditBean);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/*
	 * ????????????
	 */
	@RequestMapping(value = "/cwVerdict")
	public String cwVerdict(String id, ModelMap model){
		//id?????????????????????id   lId
		//?????????????????????
		LoanBasicInfo loanbean = loanMng.findById(Integer.valueOf(id));
		loanbean.setUserName(userMng.findById(loanbean.getUser()).getName());
		//?????????????????????
		LoanPayeeInfo personbean = loanPayeeMng.findBylId(Integer.valueOf(id)).get(0);
		//??????????????????
		List<FundPayforInfo> relist = capitalMng.findByloanId(Integer.valueOf(id));
		FundPayforInfo repaybean = new FundPayforInfo();
		if(relist.size()>0){//???????????????????????????????????????
			repaybean=relist.get(0);
		}else{//???????????????????????????
			repaybean.setFoperateUser(getUser().getName());
			repaybean.setFoperateTime(new Date());
		}
		
		model.addAttribute("loanbean", loanbean);	
		model.addAttribute("payee", personbean);	
		model.addAttribute("repaybean", repaybean);	
		return "/WEB-INF/view/expend/audit/audit-verdict";
	}
	
}
