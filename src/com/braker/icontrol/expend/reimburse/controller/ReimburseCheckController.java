package com.braker.icontrol.expend.reimburse.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
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
import com.braker.icontrol.expend.cashier.manager.CashierMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbAttacMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceCouponMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAttacMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.model.ContractReimburseInfo;
import com.braker.icontrol.expend.reimburse.model.CostDetailInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbPayeeInfo;
import com.braker.icontrol.expend.reimburse.model.InvoiceCouponInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;
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
 * @createtime 2018-08-13
 * @updatetime 2018-08-13
 */
@Controller
@RequestMapping(value = "/reimburseCheck")
public class ReimburseCheckController extends BaseController {
	
	@Autowired
	private TProExpendDetailMng detailMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
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
	private FilingMng filingMng;
	
	@Autowired
	private FormulationMng formulationMng;
	
	@Autowired
	private ReceivPlanMng receivPlanMng;
	
	@Autowired
	private ContPayMng contPayMng;
	
	@Autowired
	private CashierMng cashierMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng;	//??????????????????
	
	@Autowired
	private DisputeMng disputeMng;
	
	@Autowired
	private UptMng uptMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private InvoiceCouponMng invoiceCouponMng;




	
	/*
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2018-08-13
	 * @updatetime 2018-08-13
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/expend/reimburse/check/reimburse_check_list";
	}
	
	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-08-13
	 * @updatetime 2018-08-13
	 */
	@RequestMapping(value = "/reimbursePage")
	@ResponseBody
	public JsonPagination reimbursePage(DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean, ContPay payBean ,String reimburseType,Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = null;
    	//0???????????????1???????????????2???????????????
    	if(reimburseType.equals("0")) {
    		p = directlyReimbMng.checkPageList(drBean, page, rows, getUser(),searchData);
    		List<DirectlyReimbAppliBasicInfo> li = (List<DirectlyReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//????????????	
    			li.get(x).setNum((x+1)+(page-1)*rows);
    		}
    	} else if(reimburseType.equals("1")){
    		p = reimbAppliMng.checkPageList(rBean, page, rows, getUser(),searchData);
    		List<ReimbAppliBasicInfo> li = (List<ReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//????????????	
    			li.get(x).setNum((x+1)+(page-1)*rows);	
    			//????????????????????????id????????????,?????????????????????
    			User user = userMng.findById(li.get(x).getUser());
    			li.get(x).setUserName(user.getName());
    		}
    	} else if(reimburseType.equals("2")){
    		p = contPayMng.contractCheckPageList(payBean, page, rows, getUser(), "0");//0???checkType(0??????????????????1???????????????)
    		List<ContractReimburseInfo> li = (List<ContractReimburseInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//????????????	
    			li.get(x).setNum((x+1)+(page-1)*rows);	
    		}
    	}
		return getJsonPagination(p, page);
	}
	
	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-08-13
	 * @updatetime 2018-08-13
	 */
	@RequestMapping(value = "/check")
	public String check(Integer id , String reimburseType, ModelMap model, Integer fPlanId, Integer payId) {
		try {
			//reimburseType 0??????????????? 1??????????????? 2???????????????11???????????????
			if(reimburseType.equals("0")){
				//????????????id?????????
				DirectlyReimbAppliBasicInfo bean = directlyReimbMng.findById(id);
				//?????????????????????
				User user = userMng.findById(bean.getUser());
				bean.setUserName(user.getName());
				bean.setDeptName(user.getDepartName());
				
				model.addAttribute("bean", bean);
				
				List<InvoiceCouponInfo> list1 = invoiceCouponMng.findByrID(bean.getDrId(),"directly-1");
				model.addAttribute("Invoicelist", list1);//??????
				
				
				Integer detailId = bean.getProDetailId();
				Integer indexId = bean.getIndexId();
				if (detailId != null && indexId != null) {
					TProExpendDetail detail = detailMng.findById(detailId);
					TBudgetIndexMgr index = indexMng.findById(indexId);
					if (detail != null && index != null) {
						//????????????
						bean.setIndexName(index.getIndexName()+"??? "+detail.getSubName()+" ???");
						//????????????
						bean.setPfAmount(detail.getOutAmount());		
						//????????????
						if (index.getAppDate() != null) {
							bean.setPfDate(index.getYears());				
						}
						//????????????
						bean.setPfDepartName(index.getDeptName());			
						//????????????
						bean.setSyAmount(detail.getSyAmount());			
					}
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//????????????
					bean.setIndexName(index.getIndexName()+"??? "+index.getIndexCode()+" ???");
					//????????????
					bean.setPfAmount(index.getPfAmount());		
					//????????????
					if (index.getAppDate() != null) {
						bean.setPfDate(index.getYears());				
					}
					//????????????
					bean.setPfDepartName(index.getDeptName());			
					//????????????
					bean.setSyAmount(index.getSyAmount());			
				}
				
				//??????????????????
				List<Attachment> attaList = attachmentMng.list(bean);
				model.addAttribute("attaList", attaList);
				String strType = "";
				if("ZJBXLX-0".equals(bean.getDirType())){
					strType = "ZJBXLX-0";
				}
				if("ZJBXLX-1".equals(bean.getDirType())){
					strType = "ZJBXLX-1";
				}
				if("ZJBXLX-2".equals(bean.getDirType())){
					strType = "ZJBXLX-2";
				}
				
				//???????????????
				List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType, bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(),bean.getBeanCodeField(), bean.getDrCode(),"1");
				model.addAttribute("nodeConf", nodeConfList);
				//?????????????????????????????????
				Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
				model.addAttribute("proposer", proposer);
				//???????????????id
				TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
				model.addAttribute("fpId", tProcessDefin.getFPId());
				//????????????
				model.addAttribute("foCode",bean.getBeanCode());
				//????????????????????????
				List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
				model.addAttribute("cheterInfo", cheterInfo);
				//????????????
				model.addAttribute("currentUser",getUser());
				
				model.addAttribute("detail", "1");
				
				//??????????????????
				List<CostDetailInfo> cost =reimbAppliMng.findByDrId(bean.getDrId());
				Integer x=0;
				Integer y=0;
				for(int i = 0;i <cost.size();i++){
					y=y+1;
					List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
					for(int j = 0;j <fp.size();j++){
						x=x+1;
						fp.get(j).setNum(x);
					}
					cost.get(i).setCouponList(fp);
					cost.get(i).setNum(y);
				}
				Integer index =cost.size();
				model.addAttribute("x", x);
				model.addAttribute("y", y);
				model.addAttribute("index", index);
				model.addAttribute("cost", cost);
				return "/WEB-INF/view/expend/reimburse/check/directly_reimburse_check";
			} else if(reimburseType.equals("1")) {
				ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
				ApplicationBasicInfo apply = applyMng.findByCode(bean.getgCode());
				String rtype = bean.getType();
				String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
				if("GWCX".equals(bean.getTravelType())){
					strType = "GWCXBX";
				}
				//?????????????????????
				User user = userMng.findById(bean.getUser());
				
				bean.setUserName(user.getName());
				bean.setDeptName(user.getDepartName());
				bean.setReqTime(bean.getReimburseReqTime());
				model.addAttribute("bean", bean);
				
				//????????????????????????
				model.addAttribute("applyBean", apply);
				if (apply != null) {
					if("FLFSQ".equals(apply.getCommonType())){
						strType = "TYSXFLBX";
					}
					if("SPPSSQ".equals(apply.getCommonType())){
						strType = "TYSXSPPSBX";
					}
					Integer detailId = apply.getProDetailId();
					Integer indexId = bean.getIndexId();
					if (detailId != null && indexId != null) {
						TProExpendDetail detail = detailMng.findById(detailId);
						TBudgetIndexMgr index = indexMng.findById(indexId);
						if (detail != null && index != null) {
							//????????????
							bean.setPfAmount(detail.getOutAmount());		
							//????????????
							if (index.getAppDate() != null) {
								bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
							}
							//????????????
							bean.setPfDepartName(index.getDeptName());			
							//????????????
							bean.setSyAmount(detail.getSyAmount());			
						}
					}
				}
				
				//??????????????????
				if(!"WLKBX".equals(strType)){
					List<Attachment> attaList = attachmentMng.list(apply);
					model.addAttribute("attaList", attaList);
				}
				List<Attachment> attaList1 = attachmentMng.list(bean);
				model.addAttribute("attaList1", attaList1);
				//???????????????
				List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType, bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getrCode(),"1");
				model.addAttribute("nodeConf", nodeConfList);
				//???????????????id
				TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
				model.addAttribute("fpId", tProcessDefin.getFPId());
				//????????????
				model.addAttribute("foCode",bean.getBeanCode());
				//?????????????????????????????????
				Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
				model.addAttribute("proposer", proposer);
				//????????????????????????
				List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
				model.addAttribute("cheterInfo", cheterInfo);
				
				if(!"WLKBX".equals(strType)){
					//?????????????????????????????????
					String type = apply.getType();
					if("8".equals(bean.getType())){
						type="8";
					}
					//?????????????????????????????????????????????
					String strTypeApply = tProcessCheckMng.JudgmentProcess(String.valueOf(type));
					if("GWCX".equals(apply.getTravelType())){
						strTypeApply = "GWCXSQ";
					}
					if("1".equals(String.valueOf(apply.getType()))){
						if("FLFSQ".equals(apply.getCommonType())){
							strTypeApply = "TYSXFLSQ";
						}
						if("SPPSSQ".equals(apply.getCommonType())){
							strTypeApply = "TYSXSPPSSQ";
						}
					}
					//???????????????id
					TProcessDefin tProcessDefinApply=tProcessDefinMng.getByBusiAndDpcode(strTypeApply, bean.getDept());
					model.addAttribute("fpIdAplly", tProcessDefinApply.getFPId());
				}
				//????????????
				model.addAttribute("foCodeAplly",bean.getBeanCode());
				//??????????????????
				List<CostDetailInfo> cost =reimbAppliMng.findByRId(bean.getrId());
				//??????????????????
				if("1".equals(rtype)){

					Integer x=0;
					for(int i = 0;i <cost.size();i++){
						List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
						for(int j = 0;j <fp.size();j++){
							x=x+1;
							fp.get(j).setNum(x);
						}
						cost.get(i).setCouponList(fp);
						cost.get(i).setNum(x);
					}
					Integer index =cost.size();
					model.addAttribute("x", x);
					model.addAttribute("index", index);
					model.addAttribute("cost", cost);
					List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(bean.getrId(),"comm-1");
					model.addAttribute("Invoicelist", list);//??????????????????
				} else if("2".equals(rtype)) {
					//??????????????????
					MeetingAppliInfo meetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "gId", apply.getgId());
					model.addAttribute("meetingBean", meetingBean);
					MeetingAppliInfo reimbMeetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "rId", bean.getrId());
					model.addAttribute("reimbMeetingBean", reimbMeetingBean);
					List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(bean.getrId(),"meeting");
					model.addAttribute("Invoicelist", list);//????????????
				} else if("3".equals(rtype)) {
					//??????????????????
					TrainingAppliInfo trainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "gId", apply.getgId());
					model.addAttribute("trainingBean", trainingBean);
					//??????????????????
					TrainingAppliInfo reimbTrainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "rId", bean.getrId());
					model.addAttribute("reimbTrainingBean", reimbTrainingBean);
					//??????????????????
					List<InvoiceCouponInfo> Invoicelist1 = invoiceCouponMng.findByrID(bean.getrId(),"trainZonghe");
					model.addAttribute("Invoicelist1", Invoicelist1);
					//???????????????
					List<InvoiceCouponInfo> Invoicelist2 = invoiceCouponMng.findByrID(bean.getrId(),"trainHotel");
					model.addAttribute("Invoicelist2", Invoicelist2);
					//????????????
					List<InvoiceCouponInfo> Invoicelist3= invoiceCouponMng.findByrID(bean.getrId(),"trainFood");
					model.addAttribute("Invoicelist3", Invoicelist3);
					//????????????????????????
					List<InvoiceCouponInfo> Invoicelist4 = invoiceCouponMng.findByrID(bean.getrId(),"trainTraffic1");
					model.addAttribute("Invoicelist4", Invoicelist4);									
					//?????????????????????
					List<InvoiceCouponInfo> Invoicelist5 = invoiceCouponMng.findByrID(bean.getrId(),"trainTraffic2");
					model.addAttribute("Invoicelist5", Invoicelist5);		
				} else if("4".equals(rtype)) {
					//??????????????????
					TravelAppliInfo travelBean = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", apply.getgId());
					model.addAttribute("travelBean", travelBean);
					List<InvoiceCouponInfo> list1 = invoiceCouponMng.findByrID(bean.getrId(),"traveloutside");
					model.addAttribute("Invoicelist1", list1);//???????????????
					List<InvoiceCouponInfo> list2 = invoiceCouponMng.findByrID(bean.getrId(),"travelhotel");
					model.addAttribute("Invoicelist2", list2);//?????????
				} else if("5".equals(rtype)) {
					//??????????????????
					ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "gId", apply.getgId());
					model.addAttribute("receptionBean", receptionBean);
					
					ReceptionAppliInfo receptionBeans = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "rId", bean.getrId());
					model.addAttribute("receptionBeanEdit", receptionBeans);
					//?????????
					List<InvoiceCouponInfo> Invoicelist2 = invoiceCouponMng.findByrID(bean.getrId(),"travelhotel");
					model.addAttribute("Invoicelist2", Invoicelist2);
					//??????
					List<InvoiceCouponInfo> InvoicelistFood= invoiceCouponMng.findByrID(bean.getrId(),"receptionfood");
					model.addAttribute("InvoicelistFood", InvoicelistFood);
					//?????????
					List<InvoiceCouponInfo> list1 = invoiceCouponMng.findByrID(bean.getrId(),"hotel");
					model.addAttribute("Invoicelist1", list1);									
					//?????????????????????
					List<InvoiceCouponInfo> costRent= invoiceCouponMng.findByrID(bean.getrId(),"costRent");
					model.addAttribute("costRentList", costRent);
					//????????????
					List<InvoiceCouponInfo> InvoicelistOther= invoiceCouponMng.findByrID(bean.getrId(),"receptionother");
					model.addAttribute("InvoicelistOther", InvoicelistOther);
				} else if("6".equals(rtype)) {
					//????????????????????????
					OfficeCar officeBean = (OfficeCar) applyMng.getObject("OfficeCar", "gId", apply.getgId());
					model.addAttribute("officeCar", officeBean);
					
					List<InvoiceCouponInfo> list1 = invoiceCouponMng.findByrID(bean.getrId(),"car");
					model.addAttribute("Invoicelist1", list1);//???????????????
				} else if("7".equals(rtype)) {
					//????????????????????????
					AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "gId", apply.getgId());
					model.addAttribute("abroad", abroadBean);
					//????????????????????????
					AbroadAppliInfo abroadEdit = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "rId", bean.getrId());
					model.addAttribute("abroadEdit", abroadEdit);
					//??????????????????
					List<InvoiceCouponInfo> Invoicelist1 = invoiceCouponMng.findByrID(bean.getrId(),"mix");
					model.addAttribute("Invoicelist1", Invoicelist1);
					//??????????????????????????????
					List<InvoiceCouponInfo> Invoicelist2 = invoiceCouponMng.findByrID(bean.getrId(),"traveloutside");
					model.addAttribute("Invoicelist2", Invoicelist2);
					//???????????????
					List<InvoiceCouponInfo> Invoicelist3= invoiceCouponMng.findByrID(bean.getrId(),"traveloutside");
					model.addAttribute("Invoicelist3", Invoicelist3);
					//???????????????
					List<InvoiceCouponInfo> Invoicelist4 = invoiceCouponMng.findByrID(bean.getrId(),"travelfete");
					model.addAttribute("Invoicelist4", Invoicelist4);									
					//??????????????????
					List<InvoiceCouponInfo> Invoicelist5 = invoiceCouponMng.findByrID(bean.getrId(),"travelother");
					model.addAttribute("Invoicelist5", Invoicelist5);
				}else if("8".equals(rtype)) {
					
					//?????????????????????
					model.addAttribute("operation", "edit");
					model.addAttribute("openType", "detail");
					//??????????????????
					List<CostDetailInfo> cost1 =reimbAppliMng.findByRId(bean.getrId());
					Integer x=0;
					for(int i = 0;i <cost1.size();i++){
						List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost1.get(i).getcId());
						for(int j = 0;j <fp.size();j++){
							x=x+1;
							fp.get(j).setNum(x);
						}
						cost1.get(i).setCouponList(fp);
						cost1.get(i).setNum(x);
					}
					Integer index =cost1.size();
					model.addAttribute("x", x);
					model.addAttribute("index", index);
					model.addAttribute("cost", cost1);
					model.addAttribute("userid", getUser().getId());
					List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(bean.getrId(),"contract-1");
					model.addAttribute("Invoicelist", list);//??????
					//??????????????????
					List<Attachment> attaList2 = attachmentMng.list(bean);
					model.addAttribute("attaList1", attaList2);
				}else if(rtype.equals("9")){

					Integer x=0;
					for(int i = 0;i <cost.size();i++){
						List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
						for(int j = 0;j <fp.size();j++){
							x=x+1;
							fp.get(j).setNum(x);
						}
						cost.get(i).setCouponList(fp);
						cost.get(i).setNum(x);
					}
					Integer index =cost.size();
					model.addAttribute("x", x);
					model.addAttribute("index", index);
					model.addAttribute("cost", cost);
					List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(bean.getrId(),"comm-1");
					model.addAttribute("Invoicelist", list);//??????????????????
				}
				
				model.addAttribute("detail", "1");
				model.addAttribute("detail2", "1");
				//????????????
				model.addAttribute("currentUser",getUser());
				if("2".equals(rtype)){
					return "/WEB-INF/view/expend/reimburse/check/reimburse_check_meeting";
				}else if("3".equals(rtype)){
					model.addAttribute("detailsp", "1");
					return "/WEB-INF/view/expend/reimburse/check/reimburse_check_train";
				}else if("4".equals(rtype)){
					return "/WEB-INF/view/expend/reimburse/check/reimburse_check_travel";
				}else if("5".equals(rtype)){
					return "/WEB-INF/view/expend/reimburse/check/reimburse_check_reception";
				}else if("6".equals(rtype)){
					model.addAttribute("type", "check");
					return "/WEB-INF/view/expend/reimburse/check/reimburse_check_car";
				}else if("7".equals(rtype)){
					return "/WEB-INF/view/expend/reimburse/check/reimburse_check_abroad";
				}else if("8".equals(rtype)){
					return "/WEB-INF/view/expend/reimburse/check/contract_reimburse_check";
				}else if("9".equals(rtype)){
					return "/WEB-INF/view/expend/reimburse/check/reimburse_check_current";
				}else 
					model.addAttribute("type", "check");
				return "/WEB-INF/view/expend/reimburse/check/reimburse_check";
			} else if(reimburseType.equals("2")) {
				ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
				ContractBasicInfo beanC = formulationMng.findById(Integer.valueOf(bean.getPayId()));
				if("1".equals(beanC.getfUpdateStatus())){
					Upt upt = uptMng.findByFContId_U(String.valueOf(beanC.getFcId())).get(0);
					model.addAttribute("upt", upt);
					model.addAttribute("contractUpdateStatus", "1");
				}else{
					model.addAttribute("contractUpdateStatus", "0");
				}
				model.addAttribute("detailType", "htbx");//??????????????????
				ReimbAppliBasicInfo rabi = reimbAppliMng.findById(Integer.valueOf(id));
				User user = userMng.findById(rabi.getUser());
				rabi.setUserName(user.getName());
				model.addAttribute("bean", rabi);
				//?????????????????????
				model.addAttribute("operation", "edit");
				model.addAttribute("openType", "detail");
				//??????????????????
				List<CostDetailInfo> cost =reimbAppliMng.findByRId(rabi.getrId());
				//??????????????????
				String type = rabi.getType();
				Integer x=0;
				for(int i = 0;i <cost.size();i++){
					List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
					for(int j = 0;j <fp.size();j++){
						x=x+1;
						fp.get(j).setNum(x);
					}
					cost.get(i).setCouponList(fp);
					cost.get(i).setNum(x);
				}
				Integer index =cost.size();
				model.addAttribute("x", x);
				model.addAttribute("index", index);
				model.addAttribute("cost", cost);
				model.addAttribute("userid", getUser().getId());
				List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(rabi.getrId(),"contract-1");
				model.addAttribute("Invoicelist", list);//??????
				//??????????????????
				List<Attachment> attaList1 = attachmentMng.list(rabi);
				model.addAttribute("attaList1", attaList1);
				//?????????????????????????????????id
				String departId=departMng.findDeptByUserId(rabi.getUserId())[0];
				//???????????????id
				TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTFKSQ", departId);
				model.addAttribute("fpId", tProcessDefin.getFPId());
				//????????????
				model.addAttribute("foCode",rabi.getBeanCode());
				//???????????????
				List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(rabi.getUserId(),"HTFKSQ", departId,rabi.getBeanCode(),rabi.getnCode(), rabi.getJoinTable(), rabi.getBeanCodeField(), rabi.getrCode(), "1");
				model.addAttribute("nodeConf", nodeConfList);
				//?????????????????????????????????
				Proposer proposer = new Proposer(rabi.getUserName(), rabi.getDeptName(), rabi.getReqTime());
				model.addAttribute("proposer", proposer);
				//????????????????????????
				List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
				model.addAttribute("cheterInfo", cheterInfo);
				
				return "/WEB-INF/view/expend/reimburse/check/contract_reimburse_check";
			} else if(reimburseType.equals("11")){
				//????????????id?????????
				ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
				//?????????????????????
				User user = userMng.findById(bean.getUser());
				bean.setUserName(user.getName());
				bean.setDeptName(user.getDepartName());
				bean.setReqTime(bean.getReimburseReqTime());
				model.addAttribute("bean", bean);
				
				List<Attachment> attaList1 = attachmentMng.list(bean);
				model.addAttribute("attaList1", attaList1);
				//???????????????
				List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"BZJBX",bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
				model.addAttribute("nodeConf", nodeConfList);
				
				//???????????????id
				TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("BZJBX", bean.getDept());
				model.addAttribute("fpId", tProcessDefin.getFPId());
				//????????????
				model.addAttribute("foCode",bean.getBeanCode());
				
				//?????????????????????????????????
				Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
				model.addAttribute("proposer", proposer);
				//????????????????????????
				List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
				model.addAttribute("cheterInfo", cheterInfo);
				
				//??????????????????
				List<InvoiceCouponInfo> Invoicelist = invoiceCouponMng.findByrID(bean.getrId(),"goldPay");
				model.addAttribute("Invoicelist", Invoicelist);
				
				return "/WEB-INF/view/expend/reimburse/check/reimburse_goldpay_check";
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/*
	 * ???????????????????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-14
	 * @updatetime 2018-08-14
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean,String spjlFile) {
		try {
			//List<Role> roleli = userMng.getUserRole(getUser().getId());
			//????????????
			if(drBean.getDrId() != null) {
				directlyReimbMng.check(checkBean, drBean, getUser(), spjlFile);
				//drBean = directlyReimbMng.findById(drBean.getDrId());
			}
			//????????????
			if(rBean.getrId() != null) {
				/*if("1".equals(rBean.getType())){
					//????????????????????????
					reimbAppliMng.saveCheckInfoTYSXBX(checkBean, rBean, getUser(), spjlFile);
				}else{*/
					reimbAppliMng.saveCheckInfo(checkBean, rBean, getUser(), spjlFile);
				/*}*/
				//rBean = reimbAppliMng.findById(rBean.getrId());
			}
			//reimbCheckInfoMng.saveCheckInfo(checkBean, drBean, rBean, getUser(), roleli.get(0));
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-21
	 * @updatetime 2018-08-21
	 */
	@RequestMapping(value = "/contractCheckResult")
	@ResponseBody
	public Result contractCheckResult(TProcessCheck checkBean,ContPay bean, ReceivPlan receivPlanBean, Integer payId,String spjlFile) {
		try {
			//TODO??????????????????
			bean= contPayMng.findById(payId);//????????????????????????
			receivPlanBean = receivPlanMng.findById(receivPlanBean.getfPlanId());//??????????????????
			contPayMng.saveContractCheckInfo(checkBean, bean, receivPlanBean, getUser(), spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	/**
	 * 
	 * <p>Title: checkCurrent</p>  
	 * <p>Description: ???????????????</p>  
	 * @param id
	 * @param reimburseType
	 * @param model
	 * @param fPlanId
	 * @param payId
	 * @return
	 * @author ?????????
	 * @createtime 2020???11???12???
	 * @updator ?????????
	 * @updatetime 2020???11???12???
	 */
	@RequestMapping(value = "/checkCurrent")
	public String checkCurrent(Integer id , String reimburseType, ModelMap model, Integer fPlanId, Integer payId) {
		try {
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
			String rtype = bean.getType();
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));

			//?????????????????????
			User user = userMng.findById(bean.getUser());
			
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			model.addAttribute("bean", bean);
			
			//??????????????????
			List<Attachment> attaList1 = attachmentMng.list(bean);
			model.addAttribute("attaList1", attaList1);
			/*if(!"1".equals(bean.getType())){*/
			
			List<TNodeData> nodeConfList = new ArrayList<TNodeData>();
			nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType, bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getrCode(),"1");
			//???????????????
			model.addAttribute("nodeConf", nodeConfList);
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			/*}*/
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());
			//?????????????????????????????????
			Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			//????????????????????????
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
			model.addAttribute("cheterInfo", cheterInfo);
			
			//????????????
			model.addAttribute("foCodeAplly",bean.getBeanCode());
			//??????????????????
			List<CostDetailInfo> cost =reimbAppliMng.findByRId(bean.getrId());
			//??????????????????

			Integer x=0;
			for(int i = 0;i <cost.size();i++){
				List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
				for(int j = 0;j <fp.size();j++){
					x=x+1;
					fp.get(j).setNum(x);
				}
				cost.get(i).setCouponList(fp);
				cost.get(i).setNum(x);
			}
			Integer index =cost.size();
			model.addAttribute("x", x);
			model.addAttribute("index", index);
			model.addAttribute("cost", cost);
			List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(bean.getrId(),"comm-1");
			model.addAttribute("Invoicelist", list);//??????????????????
			
			model.addAttribute("detail", "1");
			model.addAttribute("detail2", "1");
			//????????????
			model.addAttribute("currentUser",getUser());
			model.addAttribute("type", "check");
			return "/WEB-INF/view/expend/reimburse/check/reimburse_check_current";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
