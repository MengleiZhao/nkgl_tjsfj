package com.braker.icontrol.expend.export.controller;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.Pagination;
import com.braker.common.util.MatheUtil;
import com.braker.common.util.MoneyUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.EconomicMng;
import com.braker.core.manager.ExpenditureMatterMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.UserMng;
import com.braker.core.manager.VehicleMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Depart;
import com.braker.core.model.Lookups;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.core.model.Vehicle;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.expend.apply.manager.ApplicationBasicInfoMng;
import com.braker.icontrol.expend.apply.manager.ApplyAttacMng;
import com.braker.icontrol.expend.apply.manager.ApplyCheckMng;
import com.braker.icontrol.expend.apply.manager.ApplyMeetMng;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.manager.FeteCostInfoMng;
import com.braker.icontrol.expend.apply.manager.FoodAllowanceInfoMng;
import com.braker.icontrol.expend.apply.manager.HotelExpenseInfoMng;
import com.braker.icontrol.expend.apply.manager.InCityTrafficInfoMng;
import com.braker.icontrol.expend.apply.manager.InternationalTravelingExpenseInfoMng;
import com.braker.icontrol.expend.apply.manager.OfficeCarMng;
import com.braker.icontrol.expend.apply.manager.OutsideTrafficInfoMng;
import com.braker.icontrol.expend.apply.manager.TravelAppliInfoMng;
import com.braker.icontrol.expend.apply.manager.TravelRecordMng;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
import com.braker.icontrol.expend.apply.model.FeteCostInfo;
import com.braker.icontrol.expend.apply.model.FoodAllowanceInfo;
import com.braker.icontrol.expend.apply.model.HotelExpenseInfo;
import com.braker.icontrol.expend.apply.model.InCityTrafficInfo;
import com.braker.icontrol.expend.apply.model.InternationalTravelingExpense;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.OfficeCar;
import com.braker.icontrol.expend.apply.model.OutsideTrafficInfo;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.ReceptionHotel;
import com.braker.icontrol.expend.apply.model.TrainTeacherCost;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.audit.manager.AuditInfoMng;
import com.braker.icontrol.expend.cashier.manager.CashierMng;
import com.braker.icontrol.expend.loan.manager.LoanMng;
import com.braker.icontrol.expend.loan.manager.LoanPayeeMng;
import com.braker.icontrol.expend.loan.manager.PaymentMng;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.loan.model.LoanPayeeInfo;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbDetailMng;
import com.braker.icontrol.expend.loan.model.Payment;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceCouponMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAttacMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbDetailMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.model.CostDetailInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbDetail;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbPayeeInfo;
import com.braker.icontrol.expend.reimburse.model.InvoiceCouponInfo;
import com.braker.icontrol.expend.reimburse.model.ReimCXInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbDetail;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;
import com.braker.icontrol.expend.standard.manager.AboardCountryStandardMng;
import com.braker.icontrol.expend.standard.manager.StandardMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

import freemarker.template.TemplateException;

/**
 * ???????????????????????????????????????????????????????????????
 * @author ?????????
 * @createtime 2020-06-02
 * @updatetime 2020-06-02
 *
 */
@Controller
@RequestMapping(value = "/exportApplyAndReim")
public class ExportApplyAndReimController extends BaseController{

	@Autowired
	private TProBasicInfoMng projectMng;
	
	@Autowired
	private EconomicMng economicMng;
	
	@Autowired
	private ApplyMng applyMng;
	
	@Autowired
	private ApplyMeetMng meetMng;
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private ApplyCheckMng applyCheckMng;
	
	@Autowired
	private ApplyAttacMng attacMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private StandardMng standardMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private TravelRecordMng travelRecordMng;
	
	@Autowired
	private ExpenditureMatterMng expMatterMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TProExpendDetailMng detailMng;
	
	@Autowired
	private BudgetIndexMgrMng indexMng;
	
	@Autowired
	private RoleMng roleMng;
	
	@Autowired
	private FoodAllowanceInfoMng foodAllowanceInfoMng;
	
	@Autowired
	private HotelExpenseInfoMng hotelExpenseInfoMng;
	
	@Autowired
	private TravelAppliInfoMng travelAppliInfoMng;
	
	@Autowired
	private OutsideTrafficInfoMng outsideTrafficInfoMng;
	
	@Autowired
	private InCityTrafficInfoMng inCityTrafficInfoMng;
	
	@Autowired
	private AboardCountryStandardMng aboardCountryStandardMng;
	
	@Autowired
	private InternationalTravelingExpenseInfoMng internationalTravelingExpenseInfoMng;
	
	@Autowired
	private VehicleMng vehicleMng;
	
	@Autowired
	private FeteCostInfoMng feteCostInfoMng;

	@Autowired
	private ReimbAppliMng reimbAppliMng;
	
	@Autowired
	private TProItfMng itfMng;
	
	@Autowired
	private TBasicItfMng basicItfMng;
	
	@Autowired
	private ReimbPayeeMng reimbPayeeMng;
	
	@Autowired
	private ReimbDetailMng reimbDetailMng;
	
	@Autowired
	private InvoiceMng invoiceMng;
	
	@Autowired
	private InvoiceCouponMng invoiceCouponMng;
	
	@Autowired
	private ApplicationBasicInfoMng applicationBasicInfoMng;
	
	@Autowired
	private ReimbAttacMng reimbAttacMng;
	
	@Autowired
	private AuditInfoMng auditInfoMng;
	
	@Autowired
	private CashierMng cashierMng;
	
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng;	//??????????????????
	
	@Autowired
	private OfficeCarMng officeCarMng;
	
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	
	@Autowired
	private LoanMng loanMng;
	
	@Autowired
	private LoanPayeeMng loanPayeeMng;
	
	@Autowired
	private DirectlyReimbPayeeMng directlyReimbPayeeMng;
	
	@Autowired
	private DirectlyReimbDetailMng directlyReimbDetailMng;
	
	@Autowired
	private PaymentMng paymentMng;
	
	@Autowired
	private FormulationMng formulationMng;
	
	@Autowired
	private UptMng uptMng;
	
	@Autowired
	private FilingMng filingMng;
	
	@Autowired
	private LookupsMng lookupsMng;
	
	@Autowired
	private ReceivPlanMng receivPlanMng;
	
	
	/*
	 * ???????????????????????????????????????
	 * @author ?????????
	 * @createtime 2020-06-02
	 * @updatetime 2020-06-02
	 */
	@RequestMapping(value = "/applyTravel")
	public String list(Integer id, ModelMap model) throws Exception {
		//??????????????????????????????
		ApplicationBasicInfo bean = applyMng.findById(id);
		//??????????????????
		TravelAppliInfo travelBean = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", id);
		model.addAttribute("travelBean", travelBean);
		
		//??????type????????????
		String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
		if("GWCX".equals(bean.getTravelType())){
			strType = "GWCXSQ";
		}
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
		//????????????
		model.addAttribute("foCode",bean.getBeanCode());
		List<TProcessCheck> listTProcessCheck = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),bean.getBeanCode());
		model.addAttribute("listTProcessCheck", listTProcessCheck);
		
		return "/WEB-INF/view/expend/export/apply/exportTravel";
	}
	/*
	 * ???????????????????????????????????????
	 * @author ?????????
	 * @createtime 2020-06-02
	 * @updatetime 2020-06-02
	 */
	@RequestMapping(value = "/reimburseTravel")
	public String reimburseTravel(String id, ModelMap model) {
		model.addAttribute("id", id);
		return "/WEB-INF/view/expend/export/reimburse/exportReimburse";
	}
	
	/**
	 * ??????????????????
	 * @param id ??????id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???2???
	 * @updator ?????????
	 * @updatetime 2020???6???2???
	 */
	@RequestMapping("/applyExprot")
	public String edit(Integer id, ModelMap model) {	
		try {
			//??????????????????????????????
			ApplicationBasicInfo bean = applyMng.findById(id);
			//?????????????????????
			User user = userMng.findById(bean.getUser());
			bean.setUserNames(user.getName());
			bean.setApplyAmountcapital(MoneyUtil.toChinese(String.valueOf(bean.getAmount())));
			//??????type????????????
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(bean.getType()));
			if("1".equals(String.valueOf(bean.getType()))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "TYSXSPPSSQ";
				}
			}
			//????????????????????????
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
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
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
				bean.setPfAmount(index.getPfAmount()*10000);		
				//????????????
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//????????????
				bean.setPfDepartName(index.getDeptName());			
				//????????????
				bean.setSyAmount(index.getSyAmount()*10000);			
			}
			//??????????????????
			List<Attachment> attaList = attachmentMng.list(bean);
			model.addAttribute("attaList", attaList);
			
			//??????????????????
			String type = bean.getType();
			if(type.equals("1")){
				model.addAttribute("userid", getUser().getId());
				//????????????
				List<Object> mingxiList = applyMng.getMingxi("ApplicationDetail", "gId", id);
				model.addAttribute("mingxiList", mingxiList);
				/*List<TProcessCheck> listTProcessChecksTY = new ArrayList<TProcessCheck>();
				List<TProcessCheck> listTProcessCheck = tProcessCheckMng.checkHistory(10000,bean.getBeanCode());
				int num =listTProcessCheck.size();
				for (TProcessCheck tProcessCheck : listTProcessCheck) {
					if("0".equals(tProcessCheck.getFcheckResult())){
						break;
					}else{
						User user2 = userMng.findById(tProcessCheck.getFuserId());
						tProcessCheck.setCheckDeptName(user2.getDepart().getName());
						DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
						String time =fmt.format(tProcessCheck.getFcheckTime());     // ????????? X???X???X???
						tProcessCheck.setFcheckTimes(time);
						String StringNum = MatheUtil.ToCH(num);
						tProcessCheck.setFroleName("???"+StringNum+"?????????");
						listTProcessChecksTY.add(tProcessCheck);
						num-=1;
					}
				}
				Collections.reverse(listTProcessChecksTY); // ???????????? 
				model.addAttribute("listTProcessChecksTY", listTProcessChecksTY);*/
				
			}else if(type.equals("2")) {
				//??????????????????
				MeetingAppliInfo meetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "gId", id);
				model.addAttribute("meetingBean", meetingBean);
				//????????????
				List<Object> listMeetingPlan = applyMng.getObjectList("MeetingPlan", "gId", id);
				model.addAttribute("listMeetingPlan", listMeetingPlan);
				
				//????????????
				List<Object> mingxiList = applyMng.getMingxi("ApplicationDetail", "gId", id);
				model.addAttribute("mingxiList", mingxiList);
				
			} else if(type.equals("3")) {
				//??????????????????
				TrainingAppliInfo trainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "gId", id);
				model.addAttribute("trainingBean", trainingBean);
				//????????????
				List<Object> listLecturer = applyMng.getObjectList("LecturerInfo", "tId", trainingBean.gettId());
				model.addAttribute("listLecturer", listLecturer);
				//????????????
				List<Object> listMeetPlan = applyMng.getObjectList("MeetPlan", "tId", trainingBean.gettId());
				model.addAttribute("listMeetPlan", listMeetPlan);
				//????????????
				List<Object> listZongHe = applyMng.getMingxi("ApplicationDetail", "gId", id);
				model.addAttribute("listZongHe", listZongHe);
				//?????????????????????
				List<TrainTeacherCost> listTeacherCost = applyMng.getTeacherMingxi(trainingBean.gettId(), "lesson");
				model.addAttribute("listTeacherCost", listTeacherCost);
				//?????????????????????
				List<TrainTeacherCost> listHotel = applyMng.getTeacherMingxi(trainingBean.gettId(), "hotel");
				model.addAttribute("listHotel", listHotel);
				//?????????????????????
				List<TrainTeacherCost> listFood = applyMng.getTeacherMingxi(trainingBean.gettId(), "food");
				model.addAttribute("listFood", listFood);
				//??????????????????????????????
				List<TrainTeacherCost> listTraffic1 = applyMng.getTeacherMingxi(trainingBean.gettId(), "traffic1");
				model.addAttribute("listTraffic1", listTraffic1);
				//???????????????????????????
				List<TrainTeacherCost> listTraffic2 = applyMng.getTeacherMingxi(trainingBean.gettId(), "traffic2");
				model.addAttribute("listTraffic2", listTraffic2);
			} else if(type.equals("4")) {
				//??????????????????
				TravelAppliInfo travelBean = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", id);
				model.addAttribute("travelBean", travelBean);
				//??????
				Pagination p = travelAppliInfoMng.travelPageList(1, 100, travelBean);
				List<TravelAppliInfo> Travellist = (List<TravelAppliInfo>) p.getList();
				model.addAttribute("travellist", Travellist);
				if(!"GWCX".equals(bean.getTravelType())){
					List<OutsideTrafficInfo> outsidelist =new ArrayList<OutsideTrafficInfo>();
					OutsideTrafficInfo Outsidebean = (OutsideTrafficInfo) applyMng.getObject("OutsideTrafficInfo", "gId", id);
					if(Outsidebean!=null){
						Pagination outsidep = outsideTrafficInfoMng.outsideTrafficInfoPageList(1, 100, Outsidebean);
						outsidelist = (List<OutsideTrafficInfo>) outsidep.getList();
					}
					model.addAttribute("outsidelist", outsidelist);
				}
				//???????????????
				List<InCityTrafficInfo> inCitylist =new ArrayList<InCityTrafficInfo>();
				InCityTrafficInfo InCitybean = (InCityTrafficInfo) applyMng.getObject("InCityTrafficInfo", "gId", id);
				if(InCitybean!=null){
					Pagination InCitybeanp = inCityTrafficInfoMng.inCityInfoPageList(1, 100, InCitybean);
					inCitylist = (List<InCityTrafficInfo>) InCitybeanp.getList();
				}
				model.addAttribute("inCitylist", inCitylist);
				//?????????
				List<HotelExpenseInfo> hotellist = hotelExpenseInfoMng.findbygId(bean.getgId(),null);
				model.addAttribute("hotellist", hotellist);
				//????????????
				List<FoodAllowanceInfo> foodlist = foodAllowanceInfoMng.findbygId(bean.getgId(),null);
				model.addAttribute("foodlist", foodlist);
				
				/*//????????????????????????
				TravelPeopleInfo tPeopBean = (TravelPeopleInfo) applyMng.getObject("TravelPeopleInfo", "trId", travelBean.getTrId());
				model.addAttribute("tPeopBean", tPeopBean);*/
				
			} else if(type.equals("5")) {
				//??????????????????
				ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "gId", id);
				model.addAttribute("receptionBean", receptionBean);
				//?????????
				List<Object> listHotel = applyMng.getObjectList("ReceptionHotel", "gId", id);
				model.addAttribute("listHotel", listHotel);
				//??????
				List<Object> listFood = applyMng.getObjectList("ReceptionFood", "gId", id);
				model.addAttribute("listFood", listFood);
				//????????????
				List<Object> listOther = applyMng.getObjectList("ReceptionOther", "gId", id);
				model.addAttribute("listOther", listOther);
				//????????????
				List<Object> objectListTwo = applyMng.getObjectListTwo("ReceptionPeopleInfo", "gId", id);
				model.addAttribute("objectListTwo", objectListTwo);
				//??????????????????
				List<Object> xcList = applyMng.getObjectListTwo("ReceptionStrokPlan", "gId", id);
				model.addAttribute("xcList", xcList);
				//????????????
				List<Object> sfList = applyMng.getObjectList("ReceptionChargeInfo", "gId", id);
				model.addAttribute("sfList", sfList);
				
			} else if(type.equals("6")) {
				//????????????????????????
				
				OfficeCar officeBean = (OfficeCar) applyMng.getObject("OfficeCar", "gId", id);
				model.addAttribute("officeCar", officeBean);
				
			} else if(type.equals("7")) {
				//????????????????????????

				AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "gId", id);
				model.addAttribute("abroad", abroadBean);
				//??????????????????
				List<Object> listAbroadPlan = applyMng.getObjectList("AbroadPlan", "gId", id);
				model.addAttribute("listAbroadPlan", listAbroadPlan);
				
				//????????????
				List<InternationalTravelingExpense> listInternationalTravelingExpense = internationalTravelingExpenseInfoMng.findbygId(id);
				for (InternationalTravelingExpense expense : listInternationalTravelingExpense) {
					Vehicle vehicle = vehicleMng.findById(Integer.valueOf(expense.getVehicle()));
					expense.setVehicleText(vehicle.getName());
				}
				model.addAttribute("listInternationalTravelingExpense", listInternationalTravelingExpense);
				
				//??????????????????????????????
				List<Object> listOutsideTrafficInfo = applyMng.getObjectList("OutsideTrafficInfo", "gId", id);
				model.addAttribute("listOutsideTrafficInfo", listOutsideTrafficInfo);
				
				//?????????
				List<HotelExpenseInfo> listHotelExpenseInfo = hotelExpenseInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				model.addAttribute("listHotelExpenseInfo", listHotelExpenseInfo);
				//?????????
				List<FoodAllowanceInfo> listFoodAllowanceInfo = foodAllowanceInfoMng.findbygId(bean.getgId(),bean.getTravelType());
				model.addAttribute("listFoodAllowanceInfo", listFoodAllowanceInfo);
				//?????????
				List<Object> listMiscellaneousFeeInfo = applyMng.getObjectList("MiscellaneousFeeInfo", "gId", id);
				model.addAttribute("listMiscellaneousFeeInfo", listMiscellaneousFeeInfo);
				//????????????
				List<FeteCostInfo> listFeteCostInfo = feteCostInfoMng.findbygId(id);
				model.addAttribute("listFeteCostInfo", listFeteCostInfo);
				//????????????
				List<Object> listReceptionOther = applyMng.getObjectList("ReceptionOther", "gId", id);
				model.addAttribute("listReceptionOther", listReceptionOther);
			}
			model.addAttribute("bean", bean);
			/*if(!"1".equals(type)){*/
				//??????type????????????
				strType = tProcessCheckMng.JudgmentProcess(String.valueOf(type));
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
				//???????????????
				List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getgCode(),"1");
				model.addAttribute("nodeConf", nodeConfList);
				//?????????????????????????????????
				Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
				model.addAttribute("proposer", proposer);
				//???????????????id
				TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
				model.addAttribute("fpId", tProcessDefin.getFPId());
				//??????????????????
				String historyNodes=tProcessCheckMng.getHistoryNodes(strType,bean.getDept(),bean.getBeanCode());
				model.addAttribute("historyNodes", historyNodes);
				//????????????
				List<TProcessPrintDetail> listTProcessChecks = tProcessPrintDetailMng.findbytab("ApplicationBasicInfo","gId",bean.getgId());
				// new ArrayList<TProcessPrintDetail>();
				model.addAttribute("listTProcessChecks", listTProcessChecks);
			/*}*/
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());	
			//????????????????????????
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
			model.addAttribute("cheterInfo", cheterInfo);
			
			//??????????????????????????????????????????
			Depart depart = user.getDepart();
			if (depart != null) {
				User manager =  depart.getManager();
				if (manager != null && manager.getId().equals(getUser().getId())) {//?????????????????????????????????????????????
					model.addAttribute("showSuggestCap", true);
				}
			}
			model.addAttribute("id", id);
			if(type.equals("2")){
				return "/WEB-INF/view/expend/export/apply/meeting";
			}else if(type.equals("3")){
				return "/WEB-INF/view/expend/export/apply/train";
			}else if(type.equals("4")){
				if("GWCX".equals(bean.getTravelType())){//????????????
					return "/WEB-INF/view/expend/export/apply/travelcx";
				}else{//??????
					return "/WEB-INF/view/expend/export/apply/travel";
				}
			}else if(type.equals("5")){
				return "/WEB-INF/view/expend/export/apply/reception";
			}else if(type.equals("6")){
				return "/WEB-INF/view/expend/apply/add/apply_add_car";
			}else if(type.equals("7")){
				return "/WEB-INF/view/expend/export/apply/abroad";
			}else{
				return "/WEB-INF/view/expend/export/apply/common";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ??????????????????
	 * @param id ????????????id
	 * @param model
	 * @param editType
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???2???
	 * @updator ?????????
	 * @updatetime 2020???6???2???
	 */
	@RequestMapping("/reimburseExprot")
	public String edit(Integer id, ModelMap model ,String editType) {
		try {
			
			//????????????id?????????
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
			
			//?????????????????????
			User user = userMng.findById(bean.getUser());
			
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			bean.setReimAmountcapital(MoneyUtil.toChinese(String.valueOf(bean.getAmount())));
			model.addAttribute("bean", bean);
			//????????????????????????
			ApplicationBasicInfo apply = applyMng.findByCode(bean.getgCode());
			model.addAttribute("applyBean", apply);
			//???????????????
			List<ReimbPayeeInfo> reimbPayeeInfolist = reimbPayeeMng.getByRId(bean.getrId(),"");
			model.addAttribute("reimbPayeeInfolist", reimbPayeeInfolist);
			
			if (apply != null) {
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
				}else if(indexId != null){
					TBudgetIndexMgr index = indexMng.findById(indexId);
					//????????????
					bean.setPfAmount(index.getPfAmount()*10000);		
					//????????????
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//????????????
					bean.setPfDepartName(index.getDeptName());			
					//????????????
					bean.setSyAmount(index.getSyAmount()*10000);			
				}
				bean.setIndexName(apply.getIndexName());
			}
			//??????????????????
			List<Attachment> attaList = attachmentMng.list(apply);
			model.addAttribute("attaList", attaList);
			List<Attachment> attaList1 = attachmentMng.list(bean);
			model.addAttribute("attaList1", attaList1);
			String reimburseType = apply.getType();
			//??????type????????????
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(reimburseType));
			if("GWCX".equals(bean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(apply.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(apply.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			model.addAttribute("nodeConf", nodeConfList);
			
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());
			//?????????????????????????????????
			ApplicationBasicInfo abi = applicationBasicInfoMng.getByGCode(bean.getgCode());
			//????????????
//			List<TProcessPrintDetail> listTProcessChecks = tProcessPrintDetailMng.arrangeCheckDetail(null,bean,"1",user);
//			model.addAttribute("listTProcessChecks", listTProcessChecks);
			List<TProcessPrintDetail> listTProcessChecks = tProcessPrintDetailMng.findbytab("ReimbAppliBasicInfo","rId",bean.getrId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			
			
			/*//?????????????????????????????????????????????
			String strTypeApply = tProcessCheckMng.JudgmentProcess(String.valueOf(reimburseType));
			if("GWCX".equals(bean.getTravelType())){
				strTypeApply = "GWCXSQ";
			}
			//???????????????id
			TProcessDefin tProcessDefinApply=tProcessDefinMng.getByBusiAndDpcode(strTypeApply, bean.getDept());
			model.addAttribute("fpIdAplly", tProcessDefinApply.getFPId());
			//????????????
			model.addAttribute("foCodeAplly",bean.getBeanCode());*/
			
			//?????????????????????????????????
			Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			//????????????????????????
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
			model.addAttribute("cheterInfo", cheterInfo);
			
			
			
			//??????????????????
			List<CostDetailInfo> cost =reimbAppliMng.findByRId(bean.getrId());
			//??????????????????
			String type = abi.getType();
			if(type.equals("1")){
				//????????????
				
				List<Object> mingxiList = applyMng.getMingxi("ApplicationDetail", "rId", id);
				//List<ReimbDetail> mingxiList = reimbDetailMng.getMingxi(id);
				model.addAttribute("mingxiList", mingxiList);
				/*List<TProcessCheck> listTProcessChecksTY = new ArrayList<TProcessCheck>();
				List<TProcessCheck> listTProcessCheck = tProcessCheckMng.checkHistory(10001,bean.getrCode());
				int num =listTProcessCheck.size();
				for (TProcessCheck tProcessCheck : listTProcessCheck) {
					if("0".equals(tProcessCheck.getFcheckResult())){
						break;
					}else{
						User user2 = userMng.findById(tProcessCheck.getFuserId());
						tProcessCheck.setCheckDeptName(user2.getDepart().getName());
						DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
						String time =fmt.format(tProcessCheck.getFcheckTime());     // ????????? X???X???X???
						tProcessCheck.setFcheckTimes(time);
						String StringNum = MatheUtil.ToCH(num);
						tProcessCheck.setFroleName("???"+StringNum+"?????????");
						listTProcessChecksTY.add(tProcessCheck);
						num-=1;
					}
				}
				Collections.reverse(listTProcessChecksTY); // ???????????? 
				model.addAttribute("listTProcessChecksTY", listTProcessChecksTY);*/
			} else if(type.equals("2")) {
				//??????????????????
				MeetingAppliInfo meetingBeanApply = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "gId", apply.getgId());
				model.addAttribute("meetingBeanApply", meetingBeanApply);
				MeetingAppliInfo meetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "rId", id);
				model.addAttribute("meetingBean", meetingBean);
				//????????????
				List<Object> listMeetingPlan = applyMng.getObjectList("MeetingPlan", "rId", id);
				model.addAttribute("listMeetingPlan", listMeetingPlan);
				
				//????????????
				List<Object> mingxiList = applyMng.getMingxi("ApplicationDetail", "rId", id);
				model.addAttribute("mingxiList", mingxiList);
				
				
			} else if(type.equals("3")) {
				//????????????????????????
				TrainingAppliInfo trainingBeanApply = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "gId", apply.getgId());
				model.addAttribute("trainingBeanApply", trainingBeanApply);
				
				//????????????????????????
				TrainingAppliInfo trainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "rId", id);
				model.addAttribute("trainingBean", trainingBean);
				
				//????????????
				List<Object> listLecturer = applyMng.getObjectList("LecturerInfo", "tId", trainingBean.gettId());
				model.addAttribute("listLecturer", listLecturer);
				//????????????
				List<Object> listMeetPlan = applyMng.getObjectList("MeetPlan", "tId", trainingBean.gettId());
				model.addAttribute("listMeetPlan", listMeetPlan);
				//????????????
				List<Object> listZongHe = applyMng.getMingxi("ApplicationDetail", "rId", id);
				model.addAttribute("listZongHe", listZongHe);
				//?????????????????????
				List<TrainTeacherCost> listTeacherCost = applyMng.getTeacherMingxi(trainingBean.gettId(), "lesson");
				model.addAttribute("listTeacherCost", listTeacherCost);
				//?????????????????????
				List<TrainTeacherCost> listHotel = applyMng.getTeacherMingxi(trainingBean.gettId(), "hotel");
				model.addAttribute("listHotel", listHotel);
				//?????????????????????
				List<TrainTeacherCost> listFood = applyMng.getTeacherMingxi(trainingBean.gettId(), "food");
				model.addAttribute("listFood", listFood);
				//??????????????????????????????
				List<TrainTeacherCost> listTraffic1 = applyMng.getTeacherMingxi(trainingBean.gettId(), "traffic1");
				model.addAttribute("listTraffic1", listTraffic1);
				//???????????????????????????
				List<TrainTeacherCost> listTraffic2 = applyMng.getTeacherMingxi(trainingBean.gettId(), "traffic2");
				model.addAttribute("listTraffic2", listTraffic2);		
				
			} else if(type.equals("4")) {
				//??????????????????
				TravelAppliInfo travelBean = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "rId", id);
				model.addAttribute("travelBean", travelBean);
				
				TravelAppliInfo travelBeanApply = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", apply.getgId());
				model.addAttribute("travelBeanApply", travelBeanApply);
				//??????
				Pagination p = travelAppliInfoMng.rtravelPageList(1, 100, travelBean);
				List<TravelAppliInfo> Travellist = (List<TravelAppliInfo>) p.getList();
				model.addAttribute("travellist", Travellist);
				if(!"GWCX".equals(bean.getTravelType())){
					OutsideTrafficInfo outsidebean = new OutsideTrafficInfo();
					outsidebean.setrId(id);
					Pagination outsidep = outsideTrafficInfoMng.routsideTrafficInfoPageList(1, 100, outsidebean);
					List<OutsideTrafficInfo> outsidelist = (List<OutsideTrafficInfo>) outsidep.getList();
					model.addAttribute("outsidelist", outsidelist);
				}
				//???????????????
				InCityTrafficInfo InCitybean = (InCityTrafficInfo) applyMng.getObject("InCityTrafficInfo", "rId", id);
				Pagination InCitybeanp = inCityTrafficInfoMng.rinCityInfoPageList(1, 100, InCitybean);
				List<InCityTrafficInfo> inCitylist = (List<InCityTrafficInfo>) InCitybeanp.getList();
				model.addAttribute("inCitylist", inCitylist);
				//?????????
				List<HotelExpenseInfo> hotellist = hotelExpenseInfoMng.rfindbygId(bean.getrId(),null);
				model.addAttribute("hotellist", hotellist);
				//????????????
				List<FoodAllowanceInfo> foodlist = foodAllowanceInfoMng.rfindbygId(bean.getrId(),null);
				model.addAttribute("foodlist", foodlist);
				
			} else if(type.equals("5")) {
				//??????????????????
				ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "rId", bean.getrId());
				model.addAttribute("receptionBean", receptionBean);
				
				ReceptionAppliInfo receptionBeans = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "rId", bean.getrId());
				model.addAttribute("receptionBeanEdit", receptionBeans);
				//??????
				List<Object> listHotel = applyMng.getObjectList("ReceptionHotel", "rId", bean.getrId());
				model.addAttribute("listHotel", listHotel);
				//??????
				List<Object> listFood = applyMng.getObjectList("ReceptionFood", "rId", bean.getrId());
				model.addAttribute("listFood", listFood);
				//????????????
				List<Object> listOther = applyMng.getObjectList("ReceptionOther", "rId", bean.getrId());
				model.addAttribute("listOther", listOther);
				//????????????
				List<Object> objectListTwo = applyMng.getObjectList("ReceptionPeopleInfo", "rId", bean.getrId());
				model.addAttribute("objectListTwo", objectListTwo);
				//??????????????????
				List<Object> xcList = applyMng.getObjectList("ReceptionStrokPlan", "rId", bean.getrId());
				//List<Object> xcList = applyMng.getObjectListTwo("ReceptionStrokPlan", "gId", id);
				model.addAttribute("xcList", xcList);
				//????????????
				List<Object> sfList = applyMng.getObjectList("ReceptionChargeInfo", "rId", bean.getrId());
				//List<Object> sfList = applyMng.getObjectList("ReceptionChargeInfo", "gId", id);
				model.addAttribute("sfList", sfList);
				
			} else if(type.equals("6")) {
				//????????????????????????
				OfficeCar officeBean = (OfficeCar) applyMng.getObject("OfficeCar", "gId", abi.getgId());
				model.addAttribute("officeCar", officeBean);
				/*Integer x=0;
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
				model.addAttribute("cost", cost);*/
				
				List<InvoiceCouponInfo> list1 = invoiceCouponMng.findByrID(bean.getrId(),"car");
				model.addAttribute("Invoicelist1", list1);//???????????????
			} else if(type.equals("7")) {
				//????????????????????????
				AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "rId",  bean.getrId());
				model.addAttribute("abroad", abroadBean);
				//??????????????????
				AbroadAppliInfo abroadEdit = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "rId", bean.getrId());
				model.addAttribute("abroadEdit", abroadEdit);
				List<Object> abroadPlanlist = applyMng.getObjectList("AbroadPlan", "rId", bean.getrId());
				model.addAttribute("abroadPlanlist", abroadPlanlist);
				List<InternationalTravelingExpense> expenselist = internationalTravelingExpenseInfoMng.rfindbygId(bean.getrId());
				for (InternationalTravelingExpense expense : expenselist) {
					Vehicle vehicle = vehicleMng.findById(Integer.valueOf(expense.getVehicle()));
					expense.setVehicleText(vehicle.getName());
				}
				model.addAttribute("expenselist", expenselist);
				
				//??????????????????????????????
				List<Object> listOutsideTrafficInfo = applyMng.getObjectList("OutsideTrafficInfo", "rId", bean.getrId());
				model.addAttribute("listOutsideTrafficInfo", listOutsideTrafficInfo);
				
				//?????????
				List<HotelExpenseInfo> listHotelExpenseInfo = hotelExpenseInfoMng.rfindbygId(bean.getrId(),bean.getTravelType());
				model.addAttribute("listHotelExpenseInfo", listHotelExpenseInfo);
				//?????????
				List<FoodAllowanceInfo> listFoodAllowanceInfo = foodAllowanceInfoMng.rfindbygId(bean.getrId(),bean.getTravelType());
				model.addAttribute("listFoodAllowanceInfo", listFoodAllowanceInfo);
				//?????????
				List<Object> listMiscellaneousFeeInfo = applyMng.getObjectList("MiscellaneousFeeInfo", "rId",  bean.getrId());
				model.addAttribute("listMiscellaneousFeeInfo", listMiscellaneousFeeInfo);
				//????????????
				List<FeteCostInfo> listFeteCostInfo = feteCostInfoMng.rfindbygId(bean.getrId());
				model.addAttribute("listFeteCostInfo", listFeteCostInfo);
				//????????????
				List<Object> listReceptionOther = applyMng.getObjectList("ReceptionOther", "rId",  bean.getrId());
				model.addAttribute("listReceptionOther", listReceptionOther);
			}
			List<ReimbPayeeInfo> listPayee = reimbPayeeMng.getByRId(id,"");
			Integer index1 = 0;
			Integer index2 = 0;
			for (ReimbPayeeInfo reimbPayeeInfo : listPayee) {
				if("0".equals(reimbPayeeInfo.getfInnerOrOuter())){
					index1++;
				}else if("1".equals(reimbPayeeInfo.getfInnerOrOuter())){
					index2++;
				}
			}
			if(index1 == 0){
				model.addAttribute("index1", "0");
			}
			if(index2 == 0){
				model.addAttribute("index2", "0");
			}
			model.addAttribute("listPayee", listPayee);
			model.addAttribute("id", id);
			if(type.equals("1")){
				return "/WEB-INF/view/expend/export/reimburse/common";
			}else if(type.equals("2")){
				return "/WEB-INF/view/expend/export/reimburse/meeting";
			}else if(type.equals("3")){
				return "/WEB-INF/view/expend/export/reimburse/train";
			}else if(type.equals("4")){
				if("GWCX".equals(apply.getTravelType())){//????????????
					return "/WEB-INF/view/expend/export/reimburse/travelcx";
				}else{
					return "/WEB-INF/view/expend/export/reimburse/travel";
				}
			}else if(type.equals("5")){
				return "/WEB-INF/view/expend/export/reimburse/reception";
			}else if(type.equals("6")){
				model.addAttribute("type", "detail");
				return "/WEB-INF/view/expend/reimburse/reimburse/reimburse_car_detail";
			}else if(type.equals("7")){
				return "/WEB-INF/view/expend/export/reimburse/abroad";
			}
			return "/WEB-INF/view/expend/reimburse/reimburse/reimburse_detail";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ???????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???6???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("/loanyExprot")
	public String loanyExprot(Integer id ,ModelMap model){
		try {
			LoanBasicInfo bean = loanMng.findById(id);
			User user = userMng.findById(bean.getUserId());
			bean.setUserName(user.getName());
			model.addAttribute("bean", bean);
			//???????????????
			LoanPayeeInfo payeeBean = loanPayeeMng.findBylId(bean.getlId()).get(0);
			List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(bean.getUserId());
			model.addAttribute("payerinfoList", infoList);
			model.addAttribute("payee", payeeBean);
			List<TProcessPrintDetail> listTProcessChecks  = tProcessPrintDetailMng.findbytab("LoanBasicInfo","lId",bean.getlId());
//			List<TProcessPrintDetail> listTProcessChecks  = tProcessPrintDetailMng.arrangeLoanBasicInfoCheckDetail("0", null, bean, user);
//			for (TProcessPrintDetail check : listTProcessChecks) {
//				if(check.getFcheckTime() !=null){
//					DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
//					String time =fmt.format(check.getFcheckTime());     // ????????? X???X???X???
//					check.setFcheckTimes(time);
//				}
//			}
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			return "/WEB-INF/view/expend/export/loan/loan";
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???6???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("paymentExprot")
	public String paymentExprot(Integer id ,ModelMap model){
		try {
			Payment bean =  paymentMng.findByLId(Integer.valueOf(id));
			model.addAttribute("id", id);
			model.addAttribute("bean", bean);
			LoanBasicInfo loan=loanMng.findById(Integer.valueOf(bean.getlId()));
			model.addAttribute("loan", loan);
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanId(String.valueOf(id));
			List<ReimbAppliBasicInfo> applyReimbList =reimbAppliMng.findByLoanId(String.valueOf(id));
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
			model.addAttribute("cxNum",  a+b);
			model.addAttribute("totalCxAmount", totalCxAmount);
			User user = userMng.findById(bean.getUserId());
			
			List<TProcessPrintDetail> listTProcessChecks  = new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("Payment", "id",Integer.valueOf(bean.getId()));
			for (TProcessPrintDetail check : listTProcessChecks) {
				if(check.getFcheckTime() !=null){
					DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
					String time =fmt.format(check.getFcheckTime());     // ????????? X???X???X???
					check.setFcheckTimes(time);
				}
			}
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			return "/WEB-INF/view/expend/export/loan/payment";
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * ??????????????????
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???5???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("/PastePage")
	public String pastePage(String id,ModelMap model){
		ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(id));
		model.addAttribute("code", bean.getrCode());
		model.addAttribute("amount", bean.getAmount());
		return "/WEB-INF/view/expend/export/PastePage";
	}
	/**
	 * ??????????????????????????????
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???5???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("/PastePageDir")
	public String PastePageDir(String id,ModelMap model){
		DirectlyReimbAppliBasicInfo bean = directlyReimbMng.findById(Integer.valueOf(id));
		model.addAttribute("code", bean.getDrCode());
		model.addAttribute("amount", bean.getAmount());
		return "/WEB-INF/view/expend/export/PastePage";
	}
	
	/**
	 * ??????????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???6???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("/requestApply")
	public String requestApply(String id ,ModelMap model){
		ReimbAppliBasicInfo bean = reimbAppliMng.findById(Integer.valueOf(id));
		ApplicationBasicInfo abi = applicationBasicInfoMng.getByGCode(bean.getgCode());
		return "redirect:/exportApplyAndReim/applyExprot?id="+abi.getgId();
	}
	
	
	
	/**
	 * ????????????????????????
	 * @param id ????????????id
	 * @param reimburseExprotDirectly
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???6???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("/reimburseExprotDirectly")
	public String reimburseExprotDirectly(Integer id, ModelMap model ,String editType) {
		try {
			//????????????id?????????
			DirectlyReimbAppliBasicInfo bean = directlyReimbMng.findById(id);
			User user = userMng.findById(bean.getUser());
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReimAmountcapital(MoneyUtil.toChinese(String.valueOf(bean.getAmount())));
			model.addAttribute("bean", bean);
			List<ReimbPayeeInfo> listPayee = reimbPayeeMng.getByDrId(bean.getDrId(),"");
			model.addAttribute("listPayee", listPayee);
			//?????????????????????????????????
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			List<TProcessPrintDetail> listTProcessChecks  = tProcessPrintDetailMng.findbytab("DirectlyReimbAppliBasicInfo", "drId", bean.getDrId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			model.addAttribute("id", id);
			//??????????????????
			List<DirectlyReimbDetail> listDirectly = directlyReimbDetailMng.getMingxi(id);
			model.addAttribute("listDirectly", listDirectly);
			return "/WEB-INF/view/expend/export/reimburse/directly";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "/WEB-INF/view/expend/export/reimburse/directly";
		}
	}
	
	
	/**
	 * ????????????????????????
	 * @param id ????????????id
	 * @param reimburseExprotDirectly
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???6???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("/reimburseExprotGoldpay")
	public String reimburseExprotGoldpay(Integer id, ModelMap model ,String editType) {
		try {
			//????????????id?????????
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
			
			model.addAttribute("bean", bean);
			List<ReimbPayeeInfo> listPayee = reimbPayeeMng.getByRId(id,"");
			model.addAttribute("listPayee", listPayee);
			List<TProcessPrintDetail> listTProcessChecks  = tProcessPrintDetailMng.findbytab("ReimbAppliBasicInfo", "rId", bean.getrId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			model.addAttribute("id", id);
			return "/WEB-INF/view/expend/export/reimburse/goldpay";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "/WEB-INF/view/expend/export/reimburse/directly";
		}
	}
	/**
	 * ??????????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???6???
	 * @updator ?????????
	 * @updatetime 2020???6???6???
	 */
	@RequestMapping("/requestLoan")
	public String requestLoan(String id ,ModelMap model){
		Payment bean =  paymentMng.findByLId(Integer.valueOf(id));
		LoanBasicInfo loan=loanMng.findById(Integer.valueOf(bean.getlId()));
		return "redirect:/exportApplyAndReim/loanyExprot?id="+loan.getlId();
	}
	
	@RequestMapping("/contractExport")
	public String contractExport(Integer id,ModelMap model){
		try {
			//????????????id?????????
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
			//?????????????????????
			User user = userMng.findById(bean.getUser());
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			bean.setReimAmountcapital(MoneyUtil.toChinese(String.valueOf(bean.getAmount())));
			model.addAttribute("bean", bean);
			//???????????????
			List<ReimbPayeeInfo> reimbPayeeInfolist = reimbPayeeMng.getByRId(bean.getrId(),"");
			model.addAttribute("listPayee", reimbPayeeInfolist);
			
			//????????????
			ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(bean.getPayId()));
			ReceivPlan receivPlan = new ReceivPlan();
			if("1".equals(cbi.getfUpdateStatus())){//?????????
				Upt upt = uptMng.findByFContId_U(String.valueOf(cbi.getFcId())).get(0);
				if(upt.getfPlanChangeStatus()==1){
					receivPlan.setDataType(1);
					receivPlan.setfUptId_R(upt.getfId_U());
				}
			}else{//?????????
				receivPlan.setfContId_R(cbi.getFcId());
				receivPlan.setDataType(0);
			}
			List<ReceivPlan> newRP = new ArrayList<ReceivPlan>();
			String[] a = bean.getReceivplanid().split(",");
			for (int h = 0; h < a.length; h++) {
				ReceivPlan receivPlan2 = receivPlanMng.findById(Integer.valueOf(a[h]));
				newRP.add(receivPlan2);
			}
			for (int i = 0; i < newRP.size(); i++) {
				Lookups lookups=lookupsMng.findByLookCode(newRP.get(i).getfReceProof());
				newRP.get(i).setfReceProofs(lookups.getName());	
			}
			model.addAttribute("receivPlanList", newRP);
			//????????????
			List<TProcessPrintDetail> listTProcessChecks = new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("ReimbAppliBasicInfo", "rId", bean.getrId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			model.addAttribute("id", id);
			return "/WEB-INF/view/expend/export/reimburse/contract";
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			return "/WEB-INF/view/expend/export/reimburse/contract";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "/WEB-INF/view/expend/export/reimburse/contract";
		}
	}
	
	/**
	 * ?????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021???1???29???
	 * @updator wanping
	 * @updatetime 2021???1???29???
	 */
	@RequestMapping(value="/currentExprot")
	public String currentExprot(Integer id, ModelMap model){
		try {
			model.addAttribute("urlbase", "http://111.202.125.165:8081/nkgl_school");
			//????????????id?????????
			ReimbAppliBasicInfo bean = reimbAppliMng.findById(id);
			DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
			String time =fmt.format(bean.getReimburseReqTime());     // ????????? X???X???X???
			model.addAttribute("time",time);	
			//?????????????????????
			User user = userMng.findById(bean.getUser());
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			bean.setReqTime(bean.getReimburseReqTime());
			bean.setReimAmountcapital(MoneyUtil.toChinese(String.valueOf(bean.getAmount())));
			model.addAttribute("bean", bean);
			
			//??????????????????
			List<CostDetailInfo> cost =reimbAppliMng.findByRId(bean.getrId());
			
			List<ReimbPayeeInfo> listPayee = reimbPayeeMng.getByRId(id, "0");
			model.addAttribute("listPayee", listPayee);
			//????????????
			List<Object> mingxiList = applyMng.getMingxi("ApplicationDetail", "rId", id);
			model.addAttribute("mingxiList", mingxiList);
			String payeeName = "";
			String paymentType = "";
			for (int i = 0; i < listPayee.size(); i++) {
				payeeName = payeeName + listPayee.get(i).getPayeeName()+",";
				//?????????????????????????????????
//				if(!paymentType.contains(listPayee.get(i).getPaymentType())){
//					paymentType = paymentType + listPayee.get(i).getPaymentType() + ",";
//				}
			}
			if("".equals(payeeName)){
				model.addAttribute("payeeName", "");
			}else{
				model.addAttribute("payeeName", payeeName.subSequence(0, payeeName.length()-1));
			}
			if("".equals(paymentType)){
				model.addAttribute("paymentType", "");
			}else{
				model.addAttribute("paymentType", paymentType.subSequence(0, paymentType.length()-1));
			}
			//??????type????????????
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(9));
			if("GWCX".equals(bean.getTravelType())){
				strType = "GWCXBX";
			}
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getrCode(),"1");
			model.addAttribute("nodeConf", nodeConfList);
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//????????????
			List<TProcessCheck> checkList = tProcessCheckMng.checkHistory(tProcessDefin.getFPId(),bean.getBeanCode());
			checkList = tProcessCheckMng.getTrueResult(checkList);
			Collections.reverse(checkList);
			List<TProcessCheck> financeList = new ArrayList<TProcessCheck>();
			for (int i = checkList.size()-1; i >= 0; i--) {
				model.addAttribute("role"+(i+1), checkList.get(i).getFuserName());
				model.addAttribute("date"+(i+1), fmt.format(checkList.get(i).getFcheckTime()));
				User checkuser = userMng.findById(checkList.get(i).getFbyUserId());
				if(i>(checkList.size()-4)&&"32".equals(checkuser.getDpID())){
					financeList.add(checkList.get(i));
				}
			}
			String financeCheck = new String();
			for (int i = 0; i < financeList.size(); i++) {
				financeCheck+=financeList.get(i).getFuserName()+",";
			}
			if(financeList.size()>0){
				model.addAttribute("financeCheck", financeCheck.substring(0, financeCheck.length()-1));
			}
			
			return "/WEB-INF/view/expend/export/reimburse/current";
		} catch (Exception e) {
			e.printStackTrace();
			return "/WEB-INF/view/expend/export/reimburse/current";
		}
	}
	
	
	/**
	 * ????????????ofd??????
	 * @return
	 */
	@RequestMapping(value = "openOFD")
	public void openOFD(String priceId , HttpServletRequest request, HttpServletResponse response) throws Exception, TemplateException, DocumentException {
		String filePath = "/root/ftp/test.ofd";
        System.out.println("filePath:" + filePath);
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] bs = new byte[1024];
        int len = 0;
        response.reset(); // ????????????
        if (true) { // ??????????????????
            URL u = new URL("file:///" + filePath);
            String contentType = u.openConnection().getContentType();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "inline;filename="
                    + "test.ofd");
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(bs)) > 0) {
            out.write(bs, 0, len);
        }
        out.flush();
        out.close();
        br.close();
	}
}
