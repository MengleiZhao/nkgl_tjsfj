package com.braker.icontrol.expend.apply.controller;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.TreeEntity;
import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.CodeUtil;
import com.braker.common.util.DateUtil;
import com.braker.common.util.MatheUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.EconomicMng;
import com.braker.core.manager.ExpenditureMatterMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.UserMng;
import com.braker.core.manager.VehicleMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Depart;
import com.braker.core.model.ExpenditureMatter;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.core.model.Vehicle;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.expend.apply.manager.ApplyAttacMng;
import com.braker.icontrol.expend.apply.manager.ApplyCheckMng;
import com.braker.icontrol.expend.apply.manager.ApplyMeetMng;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.manager.FeteCostInfoMng;
import com.braker.icontrol.expend.apply.manager.FoodAllowanceInfoMng;
import com.braker.icontrol.expend.apply.manager.HotelExpenseInfoMng;
import com.braker.icontrol.expend.apply.manager.InCityTrafficInfoMng;
import com.braker.icontrol.expend.apply.manager.InternationalTravelingExpenseInfoMng;
import com.braker.icontrol.expend.apply.manager.OutsideTrafficInfoMng;
import com.braker.icontrol.expend.apply.manager.TravelAppliInfoMng;
import com.braker.icontrol.expend.apply.manager.TravelRecordMng;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.FeteCostInfo;
import com.braker.icontrol.expend.apply.model.FoodAllowanceInfo;
import com.braker.icontrol.expend.apply.model.HotelExpenseInfo;
import com.braker.icontrol.expend.apply.model.InCityTrafficInfo;
import com.braker.icontrol.expend.apply.model.InternationalTravelingExpense;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.OfficeCar;
import com.braker.icontrol.expend.apply.model.OutsideTrafficInfo;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.TrainTeacherCost;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.standard.entity.AboardCountryStandard;
import com.braker.icontrol.expend.standard.entity.AboardStandard;
import com.braker.icontrol.expend.standard.entity.HotelStandard;
import com.braker.icontrol.expend.standard.entity.MeetStandard;
import com.braker.icontrol.expend.standard.entity.RecepStandard;
import com.braker.icontrol.expend.standard.entity.TrainStandard;
import com.braker.icontrol.expend.standard.manager.AboardCountryStandardMng;
import com.braker.icontrol.expend.standard.manager.StandardMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 支出申请的控制层
 * 本模块用于支出申请的增删改查
 * @author 叶崇晖
 * @createtime 2018-06-11
 * @updatetime 2018-06-11
 */
@Controller
@RequestMapping(value = "/apply")
public class ApplyController extends BaseController{
	
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
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	
	/*
	 * 跳转到列表页面
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12
	 */
	@RequestMapping(value = "/list")
	public String list(String applyType, ModelMap model) {
		model.addAttribute("applyType", applyType);
		return "/WEB-INF/view/expend/apply/add/apply_list";
	}
	
	/*
	 * 分页数据获得
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12
	 */
	@RequestMapping(value = "/applyPage")
	@ResponseBody
	public JsonPagination applyPage(Integer applyType ,ApplicationBasicInfo bean, ModelMap model, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = applyMng.pageList(bean, page, rows, applyType, getUser(),searchData);
		
		return getJsonPagination(p, page);
	}
	
	
	
	/*
	 * 跳转新增页面
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12           
	 */
	@RequestMapping("/add")
	public String add(Integer type ,ModelMap model) {
		try {
			ApplicationBasicInfo bean = new ApplicationBasicInfo();
			//获取默认值
			User user = getUser();
			bean.setUser(user.getId());//用户名
			bean.setUserNames(user.getName());//用户名
			bean.setDept(user.getDepart().getId());//部门
			bean.setDeptName(user.getDepart().getName());//部门
			bean.setType(type.toString());//类型
			String gName = null;
			if(1==type){//1-通用申请
				model.addAttribute("userid", user.getId());
				gName = "通用申请";
			}else if(2==type){//2-会议申请
				gName = "会议申请";
			}else if(3==type){//3-培训申请
				gName = "培训申请";
			}else if(4==type){//4-差旅申请
				gName = "差旅申请";
			}else if(5==type){//5-接待申请
				gName = "公务接待申请";
			}else if(6==type){//6-公车运维
				gName = "公车运维申请";
			}else if(7==type){//6-公务出国
				gName = "公务出国申请";
			}
			bean.setgName(user.getName()+"-"+gName+"-"+DateUtil.formatDate(new Date()));
			//如果是差旅申请的话，需要查询人员差旅记录表获得当前登录人常用的出发地和目的地
			if(type==4) {
				String s[] = travelRecordMng.findByUserId(user.getId());
				TravelAppliInfo travelBean = new TravelAppliInfo();
				travelBean.setPlaceStart(s[0]);
				travelBean.setPlaceEnd(s[1]);
				travelBean.setLoongTavelAmount(0.00);//长途交通费
				travelBean.setCityTavelAmount(0.00);//市内交通费
				travelBean.setOtherAmount(0.00);//其他费用
				travelBean.setFoodAmount(0.00);//伙食补助费
				travelBean.setHotelAmount(0.00);//住宿费
				model.addAttribute("travelBean", travelBean);
				//if(getUser().getRoleName().contains("校长办公会会议纪要录入人")){
				model.addAttribute("xzbgsFile", "xzbgsFile");
				//}
				//if(getUser().getRoleName().contains("党委会会议纪要录入人")){
				model.addAttribute("dwhFile", "dwhFile");
				//}
			}
			model.addAttribute("bean", bean);
			//申请类型
			model.addAttribute("type", type);
			//操作类型
			model.addAttribute("operation", "add");
			//单据编号
			model.addAttribute("applyCodeAdd", CodeUtil.getApplyCode(applyMng.getMaxCodeToday5()));
			//摘要
			model.addAttribute("draftAdd", applyMng.getDraft(type, getUser().getName()));
			
			/*if(1!=type){*/
				//转换type选择流程
				String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(type));
				if("1".equals(String.valueOf(bean.getType()))){
					if("FLFSQ".equals(bean.getCommonType())){
						strType = "TYSXFLSQ";
					}
					if("SPPSSQ".equals(bean.getCommonType())){
						strType = "TYSXSPPSSQ";
					}
				}
				//查询工作流
				List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),strType, getUser().getDpID(),null,bean.getnCode(), null, null, null, "1");
				
				//2020.11.09应客户要求
				//局长申请时，公务出差还需要有陈局审批。局长-财务分管局长
				//没有局长时只需要局长审批即可,不需要陈局审批。
				if(!user.getRoleCode().contains("ab1383b1-7ac4-11e9-8688-005056a17ba3")&&strType.equals("CLSQ")&&"8".equals(user.getDpID())){
					nodeConfList.remove(0);
				}
				
				model.addAttribute("nodeConf", nodeConfList);
				//得到工作流id
				TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, getUser().getDpID());
				model.addAttribute("fpId", tProcessDefin.getFPId());
				//根据归口部门修改流程显示数据
		//		TNodeData nodedata = nodeConfList.get(nodeConfList.size()-2);
		//		User secondUser = userMng.getUserByRoleIdAndDepartId("820099ee-7aca-11e9-8688-005056a17ba3",bean.getSuggestDepId());
				//建立工作流发起人的信息
				Proposer proposer = new Proposer(user.getName(), user.getDepart().getName(), null);
				model.addAttribute("proposer", proposer);	
				//历史审批节点
				String historyNodes=tProcessCheckMng.getHistoryNodes(strType,getUser().getDpID(),bean.getBeanCode());
				model.addAttribute("historyNodes", historyNodes);
			/*}*/
			
			//查询制度中心文件
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("事前申请");
			model.addAttribute("cheterInfo", cheterInfo);
			//判断是否显示归口部门意见
			model.addAttribute("showSuggestDep", true);//新增时一定是归口部门
			//判断是否显示部门主管领导意见
			Depart depart = user.getDepart();
			if (depart != null) {
				User manager =  depart.getManager();
				if (manager != null && manager.getId().equals(user.getId())) {//当前用户是部门主管校长
					model.addAttribute("showSuggestCap", true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/WEB-INF/view/expend/apply/add/apply_add";
		}if(type==4){
			return "/WEB-INF/view/expend/apply/add/apply_add_travel1";
		}else if(type==6){
			return "/WEB-INF/view/expend/apply/add/apply_add_car";
		}else if (type==2) {
			MeetingAppliInfo meetingBean = new MeetingAppliInfo();
			model.addAttribute("meetingBean", meetingBean);
			return "/WEB-INF/view/expend/apply/add/apply_add_meeting";
		}else if (type==3) {
			TrainingAppliInfo trainingBean = new TrainingAppliInfo();
			model.addAttribute("trainingBean", trainingBean);
			return "/WEB-INF/view/expend/apply/add/apply_add_train";
		}else if (type==5) {
			ReceptionAppliInfo receptionBean = new ReceptionAppliInfo();
			receptionBean.setStayYN("1");//是否安排住宿：否
			model.addAttribute("receptionBean", receptionBean);
			return "/WEB-INF/view/expend/apply/add/apply_add_reception";
		}else if (type==7) {
			//查询公务出国信息
			AbroadAppliInfo abroadBean =new AbroadAppliInfo();
			model.addAttribute("abroad", abroadBean);
			return "/WEB-INF/view/expend/apply/add/apply_add_abroad";
		}else {
			return "/WEB-INF/view/expend/apply/add/apply_add";
		}
	}
	/*@RequestMapping("/addNew")
	public String add11(Integer type ,ModelMap model) {
		return "/WEB-INF/view/expend/apply/add/apply_add_comm";
	}*/
	
	/*
	 * 跳转修改查看页面
	 * @author 叶崇晖
	 * @createtime 2018-06-14
	 * @updatetime 2018-06-14
	 * @param id 传回来的id是主键
	 */
	@RequestMapping("/edit")
	public String edit(Integer id, ModelMap model ,String editType, String applyType) {
		
		//申请类型
		model.addAttribute("type", applyType);
		
		//获取需要修改的申请单
		ApplicationBasicInfo bean = applyMng.findById(id);
		//查询申请人信息
		User user = userMng.findById(bean.getUser());
		bean.setUserNames(user.getName());
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
					bean.setPfDate(new SimpleDateFormat("yyyy").format(index.getAppDate()));				
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
				bean.setPfDate(new SimpleDateFormat("yyyy").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);			
		}
		
		//查询附件信息
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("attaList", attaList);
		
//		if(getUser().getRoleName().contains("局长办公会会议纪要岗")){
//			model.addAttribute("xzbgsFile", "xzbgsFile");
//		}
		if(getUser().getRoleName().contains("党委会会议纪要岗")){
			model.addAttribute("dwhFile", "dwhFile");
		}
		//判断申请类型
		String type = bean.getType();
		if(type.equals("1")){
			model.addAttribute("userid", getUser().getId());
		} else if(type.equals("2")) {
			//查询会议信息
			MeetingAppliInfo meetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "gId", id);
			model.addAttribute("meetingBean", meetingBean);
			
		} else if(type.equals("3")) {
			//查询培训信息
			TrainingAppliInfo trainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "gId", id);
			model.addAttribute("trainingBean", trainingBean);
			
		} else if(type.equals("4")) {
			//查询差旅信息
			TravelAppliInfo travelBean = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", id);
			model.addAttribute("travelBean", travelBean);
			/*//查询差旅人员信息
			TravelPeopleInfo tPeopBean = (TravelPeopleInfo) applyMng.getObject("TravelPeopleInfo", "trId", travelBean.getTrId());
			model.addAttribute("tPeopBean", tPeopBean);*/

		} else if(type.equals("5")) {
			//查询接待信息
			ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "gId", id);
			model.addAttribute("receptionBean", receptionBean);
			
		} else if(type.equals("6")) {
			//查询公务用车信息
			
			OfficeCar officeBean = (OfficeCar) applyMng.getObject("OfficeCar", "gId", id);
			model.addAttribute("officeCar", officeBean);
			
		} else if(type.equals("7")) {
			//查询公务出国信息
			AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "gId", id);
			model.addAttribute("abroad", abroadBean);
		}
		
		model.addAttribute("bean", bean);
		/*if(!"1".equals(type)){*/
			//转换type选择流程
			String strType = tProcessCheckMng.JudgmentProcess(String.valueOf(type));
			if("GWCX".equals(bean.getTravelType())){
				strType = "GWCXSQ";
			}
			if("1".equals(String.valueOf(type))){
				if("FLFSQ".equals(bean.getCommonType())){
					strType = "TYSXFLSQ";
				}
				if("SPPSSQ".equals(bean.getCommonType())){
					strType = "TYSXSPPSSQ";
				}
			}
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType,bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getgCode(),"1");
//			if("8".equals(user.getDepart().getId())){
//				if("GWCXS".equals(strType)&&!user.getRoleName().contains("分管财务局长")){
//					for (int i = nodeConfList.size()-1; i >= 0; i--) {
//						if("86".equals(nodeConfList.get(i).getUserId())){
//							nodeConfList.remove(i);
//						}
//					}
//				}
//			}
//			if("GWCXS".equals(strType)&&user.getRoleName().contains("普通用户")&&!user.getRoleName().contains("处室负责人")){
//				for (int i = nodeConfList.size()-1; i >= 0; i--) {
//					if(nodeConfList.get(i).getUserId().equals(user.getDepart().getManager().getId())){
//						nodeConfList.remove(i);
//					}
//				}
//			}
//			//2020.11.09应客户要求
//			//局长申请时，公务出差还需要有陈局审批。局长-财务分管局长
//			//没有局长时只需要局长审批即可,不需要陈局审批。
//			if(!user.getRoleCode().contains("ab1383b1-7ac4-11e9-8688-005056a17ba3")&&strType.equals("CLSQ")&&"8".equals(user.getDpID())){
//				nodeConfList.remove(0);
//			}
			model.addAttribute("nodeConf", nodeConfList);
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//历史审批节点
			String historyNodes=tProcessCheckMng.getHistoryNodes(strType,bean.getDept(),bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
		/*}*/
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());	
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("事前申请");
		model.addAttribute("cheterInfo", cheterInfo);
		
		//判断是否显示部门主管领导意见
		Depart depart = user.getDepart();
		if (depart != null) {
			User manager =  depart.getManager();
			if (manager != null && manager.getId().equals(getUser().getId())) {//当前用户是申报部门部门主管校长
				model.addAttribute("showSuggestCap", true);
			}
		}

		
		//根据修改还是查看跳转不同页面
		if("0".equals(editType)){
			/*try {
				List<TProcessCheck> listTProcessCheck = tProcessPrintDetailMng.applycheckList(strType, bean, user);
				int num =1;
				for (TProcessCheck tProcessCheck : listTProcessCheck) {
					if("0".equals(tProcessCheck.getFcheckResult())){
						break;
					}else{
						TProcessPrintDetail tProcessPrintDetail = new TProcessPrintDetail();
						User user2 = userMng.findById(tProcessCheck.getFuserId());
						tProcessCheck.setCheckDeptName(user2.getDepart().getName());
						String StringNum = MatheUtil.ToCH(num);
						tProcessPrintDetail.setfRoleName("第"+StringNum+"审批人");
						tProcessPrintDetail.setFuserName(user2.getName());
						tProcessPrintDetail.setFuserId(tProcessCheck.getFuserId());
						tProcessPrintDetail.setFdeptId(user2.getDepart().getId());
						tProcessPrintDetail.setFdeptName(user2.getDepart().getName());
						tProcessPrintDetail.setFcheckResult("1");
						tProcessPrintDetail.setFcheckRemake(tProcessCheck.getFcheckRemake());
						tProcessPrintDetail.setFcheckTime(tProcessCheck.getFcheckTime());
						tProcessPrintDetail.setFTabName("ApplicationBasicInfo");
						tProcessPrintDetail.setFTabId(bean.getgId());
						tProcessPrintDetail.setFTabIdName("gId");
						tProcessPrintDetailMng.saveOrUpdate(tProcessPrintDetail);
						num+=1;
					}
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}*/
			model.addAttribute("detail", "0");
			model.addAttribute("operation", "detail");
			if(type.equals("2")){
				return "/WEB-INF/view/expend/apply/add/apply_detail_meeting";
			}else if(type.equals("3")){
				return "/WEB-INF/view/expend/apply/add/apply_detail_train";
			}else if(type.equals("4")){
				return "/WEB-INF/view/expend/apply/add/apply_detail_travel";
			}else if(type.equals("5")){
				return "/WEB-INF/view/expend/apply/add/apply_detail_reception";
			}else if(type.equals("6")){
				return "/WEB-INF/view/expend/apply/add/apply_detail_car";
			}else if(type.equals("7")){
				return "/WEB-INF/view/expend/apply/add/apply_detail_abroad";
			}else{
				return "/WEB-INF/view/expend/apply/add/apply_detail";
			}
		} if("1".equals(editType)){
			model.addAttribute("operation", "edit");
			if(type.equals("2")){
				return "/WEB-INF/view/expend/apply/add/apply_add_meeting";
			}else if(type.equals("3")){
				return "/WEB-INF/view/expend/apply/add/apply_add_train";
			}else if(type.equals("4")){
				return "/WEB-INF/view/expend/apply/add/apply_add_travel1";
			}else if(type.equals("5")){
				return "/WEB-INF/view/expend/apply/add/apply_add_reception";
			}else if(type.equals("6")){
				return "/WEB-INF/view/expend/apply/add/apply_add_car";
			}else if(type.equals("7")){
				return "/WEB-INF/view/expend/apply/add/apply_add_abroad";
			}else{
				return "/WEB-INF/view/expend/apply/add/apply_add";
			}
		} else if("detail".equals(editType)){
			model.addAttribute("detail", "detail");//驾驶舱查看详情
			return "/WEB-INF/view/expend/apply/add/apply_detail_cockpit";
		} else {
			return null;
		}
	}
	/*
	 * 跳转修改查看页面
	 * @author 赵孟雷
	 * @createtime 2018-06-14
	 * @updatetime 2019-05-09
	 * @param id 传回来的id是主键
	 */
	@RequestMapping("/edit1")
	public String edit1(Integer id, ModelMap model ,String editType) {
		//获取需要修改的申请单
		ApplicationBasicInfo bean = applyMng.findById(id);
		//查询申请人信息
		User user = userMng.findById(bean.getUser());
		bean.setUserName(user.getName());
		
		//查询附件信息
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("attaList", attaList);
		
		
		//判断申请类型
		String type = bean.getType();
		if(type.equals("1")){
			
		} else if(type.equals("2")) {
			//查询会议信息
			MeetingAppliInfo meetingBean = (MeetingAppliInfo) applyMng.getObject("MeetingAppliInfo", "gId", id);
			model.addAttribute("meetingBean", meetingBean);
			
		} else if(type.equals("3")) {
			//查询培训信息
			TrainingAppliInfo trainingBean = (TrainingAppliInfo) applyMng.getObject("TrainingAppliInfo", "gId", id);
			model.addAttribute("trainingBean", trainingBean);
			
		} else if(type.equals("4")) {
			//查询差旅信息
			TravelAppliInfo travelBean = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", id);
			model.addAttribute("travelBean", travelBean);
			/*//查询差旅人员信息
			TravelPeopleInfo tPeopBean = (TravelPeopleInfo) applyMng.getObject("TravelPeopleInfo", "trId", travelBean.getTrId());
			model.addAttribute("tPeopBean", tPeopBean);*/
			
		} else if(type.equals("5")) {
			//查询接待信息
			ReceptionAppliInfo receptionBean = (ReceptionAppliInfo) applyMng.getObject("ReceptionAppliInfo", "gId", id);
			model.addAttribute("receptionBean", receptionBean);
			
		} else if(type.equals("6")) {
			//查询公务用车信息
			
			OfficeCar officeBean = (OfficeCar) applyMng.getObject("OfficeCar", "abi.gId", id);
			model.addAttribute("officeCar", officeBean);
			
		} else if(type.equals("7")) {
			//查询公务出国信息
			AbroadAppliInfo abroadBean = (AbroadAppliInfo) applyMng.getObject("AbroadAppliInfo", "abi.gId", id);
			model.addAttribute("abroad", abroadBean);
		}
		
		model.addAttribute("bean", bean);
		if(!"0".equals(type)){
			//转换type选择流程
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(type));
			if("GWCX".equals(bean.getTravelType())){
				strType = "GWCXBX";
			}
			if("FLFSQ".equals(bean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(bean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType, bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),  bean.getBeanCodeField(), bean.getgCode(),"1");
			model.addAttribute("nodeConf", nodeConfList);
//			if("8".equals(user.getDepart().getId())){
//				if("GWCXSQ".equals(strType)&&!user.getRoleName().contains("分管财务局长")){
//					for (int i = nodeConfList.size()-1; i >= 0; i--) {
//						if("86".equals(nodeConfList.get(i).getUserId())){
//							nodeConfList.remove(i);
//						}
//					}
//				}
//			}
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(user.getName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//历史审批节点
			String historyNodes=tProcessCheckMng.getHistoryNodes(strType,bean.getDept(),bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
		}
		//对象编号
		model.addAttribute("foCode",bean.getBeanCode());
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("事前申请");
		model.addAttribute("cheterInfo", cheterInfo);
		
		//根据修改还是查看跳转不同页面
		if("0".equals(editType)){
			model.addAttribute("detail", "0");
			return "/WEB-INF/view/expend/apply/add/apply_detail_first";
		} if("1".equals(editType)){
			return "/WEB-INF/view/expend/apply/add/apply_add";
		} else if("detail".equals(editType)){
			model.addAttribute("detail", "0");//驾驶舱查看详情
			return "/WEB-INF/view/expend/apply/add/apply_detail_cockpit";
		}
		else {
			return null;
		}
	}
	
	/*
	 * 事前申请新增和修改的保存
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(ApplicationBasicInfo bean,
			MeetingAppliInfo meetingBean, 			//会议信息
			TrainingAppliInfo trainingBean, 		//培训信息
			TravelAppliInfo travelBean,				//差旅信息
			ReceptionAppliInfo receptionBean,		//公务接待信息
			OfficeCar officeBean,					//公务用车信息
			AbroadAppliInfo abroadBean,				//公务出国信息
			String recePeop,						//被接待人Json
			String chargeJson,						//接待收费明细Json
			String storkJson,						//接待主要行程json
			String gwjdfafiles,						//接待方案附件
			String gwjdghfiles,						//接待公函附件
			String mingxi, 							//费用明细Json
			String meetPlan,
			String files,							//附件的地址
			String travelJson,	                    //国际旅费json
			String trafficJson1,					//城市间交通费和国外城市间交通费
			String hotelJson,	                    //住宿费json
			String foodJson,						//伙食费json
			String feeJson,							//公杂费json
			String feteJson,						//宴请费json
			String otherJson,						//其他收入json
			String trainPlan,   					//培训日程
			String abroadPlanJson,					//出访计划
			String abroadPeople,
			String trainLecturer,					//讲师信息
			String zongheJson,						//培训-综合预算费用
			String lessonJson,						//培训-师资讲课费
			String trafficJson2,					//市内交通费
			ModelMap model) {
			Lock lock = new ReentrantLock();
			
			lock.lock();
		try {
			//判断申请类型,去除不需要保存的实体
			String type = bean.getType();
			if(type.equals("1")){
				meetingBean = null;
				trainingBean = null;
				travelBean = null;
				receptionBean = null;
				officeBean = null;
				abroadBean = null;
			} else if(type.equals("2")) {
				trainingBean = null;
				travelBean = null;
				receptionBean = null;
				officeBean = null;
				abroadBean = null;
			} else if(type.equals("3")) {
				meetingBean = null;
				travelBean = null;
				receptionBean = null;
				officeBean = null;
				abroadBean = null;
			} else if(type.equals("4")) {
				meetingBean = null;
				trainingBean = null;
				receptionBean = null;
				officeBean = null;
				abroadBean = null;
			} else if(type.equals("5")) {
				meetingBean = null;
				trainingBean = null;
				travelBean = null;
				officeBean = null;
				abroadBean = null;
			} else if(type.equals("6")) {
				meetingBean = null;
				trainingBean = null;
				receptionBean = null;
				travelBean = null;
				abroadBean = null;
			} else if(type.equals("7")) {
				meetingBean = null;
				trainingBean = null;
				travelBean = null;
				receptionBean = null;
				officeBean = null;
			}
			Boolean b = true; 
			if(!"0".equals(bean.getFlowStauts())){
				//判断申请金额是否超过可用金额
				b=budgetIndexMgrMng.checkAmounts(String.valueOf(bean.getIndexId()), bean.getAmount()/10000);
			}
			if(b){
				//保存
				if ("2".equals(type)) {//会议
					if(meetingBean.getDateStart().compareTo(new Date())<=0){
						return getJsonResult(false,"会议报到日期超过申请日期，不允许提交！");
					}
					meetMng.save(getUser(), bean, meetingBean, meetPlan, files,mingxi);
				}else if("3".equals(type)){//培训
					applyMng.saveTrain(getUser(), bean, trainingBean, trainPlan, files,trainLecturer,zongheJson,lessonJson,hotelJson,foodJson,trafficJson1,trafficJson2);
				}else if("5".equals(type)){//接待
					applyMng.saveRecp(getUser(), bean, receptionBean, hotelJson,foodJson,otherJson,recePeop,storkJson, chargeJson, gwjdghfiles, gwjdfafiles);
				}else if("7".equals(type)){//出国
					applyMng.saveAbroad(getUser(), 
							 bean, 
							 abroadBean,
							 travelJson,	                    //国际旅费json
							 trafficJson1,					//城市间交通费和国外城市间交通费
							 hotelJson,	                    //住宿费json
							 foodJson,						//伙食费json
							 feeJson,							//公杂费json
							 feteJson,						//宴请费json
							 otherJson,						//其他收入json
							 abroadPlanJson,					//出访计划
							 files);
				} else {
					applyMng.save(bean, meetingBean, trainingBean, travelBean, receptionBean, officeBean, abroadBean, mingxi, getUser(), recePeop, files);
				}
			}else {
				//lock.unlock();
				return getJsonResult(false,"申请金额超出可用金额，不允许提交！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}finally{
			lock.unlock();
			
		}
		return getJsonResult(true,"操作成功！");
	}

	/*
	 * 事前申请删除
	 * @author 叶崇晖
	 * @createtime 2018-06-12
	 * @updatetime 2018-06-12
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id,String fId) {
		try {
			//传回来的id是主键
			User user = getUser();
			applyMng.delete(id,user,fId);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	
	
	@RequestMapping(value = "/deleteApp")
	@ResponseBody
	public Result deleteApp(Integer id,String userId) {
		try {
			//传回来的id是主键
			User user = userMng.findById(userId);
			applyMng.delete(id,user,null);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	/*
	 * 查询明细
	 * @author 叶崇晖
	 * @createtime 2018-06-14
	 * @updatetime 2018-06-14
	 */
	@RequestMapping(value = "/mingxi")
	@ResponseBody
	public List<Object> mingxi(Integer id) {
		List<Object> mingxiList = new ArrayList<Object>();
		if(id != null) {		
			//查询申请明细
			mingxiList = applyMng.getMingxi("ApplicationDetail", "gId", id);
		}
		return mingxiList;
	}
	
	/*
	 * 查询培训师资费明细
	 * @author 沈帆
	 * @createtime 2020-05-13
	 * @updatetime 2020-05-13
	 */
	@RequestMapping(value = "/teacherMingxi")
	@ResponseBody
	public List<TrainTeacherCost> mingxi(Integer id,String costType) {
		List<TrainTeacherCost> mingxiList = new ArrayList<TrainTeacherCost>();
		if(id != null) {		
			//查询申请明细
			mingxiList = applyMng.getTeacherMingxi(id, costType);
		}
		return mingxiList;
	}
	
	/*
	 * 查询会议报销明细
	 * @author 沈帆
	 * @createtime 2020-05-11
	 * @updatetime 2020-05-11
	 */
	@RequestMapping(value = "/reimbmingxi")
	@ResponseBody
	public List<Object> reimbmingxi(Integer id) {
		List<Object> mingxiList = new ArrayList<Object>();
		if(id != null) {		
			//查询申请明细
			mingxiList = applyMng.getMingxi("ApplicationDetail", "rId", id);
		}
		return mingxiList;
	}
	
	/*
	 * 查询被接待人信息
	 * @author 叶崇晖
	 * @createtime 2018-06-15
	 * @updatetime 2018-06-15
	 */
	@RequestMapping(value = "/recep")
	@ResponseBody
	public List<Object> recep(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			//list = applyMng.getObjectList("ReceptionPeopleInfo", "gId", id);
			list = applyMng.getObjectListTwo("ReceptionPeopleInfo", "gId", id);
		}
		return list;
	}
	
	/**
	 * 查询接待主要行程信息
	 * @param id
	 * @return
	 * @author 陈睿超
	 * @createtime 2021年1月25日
	 * @updator 陈睿超
	 * @updatetime 2021年1月25日
	 */
	@RequestMapping(value = "/receptionStrokPlan")
	@ResponseBody
	public List<Object> receptionPlan(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
//			list = applyMng.getObjectList("ReceptionStrokPlan", "gId", id);
			list = applyMng.getObjectListTwo("ReceptionStrokPlan", "gId", id);
		}
		return list;
	}
	
	/**
	 * 查询接待收费明细
	 * @param id
	 * @return
	 * @author 陈睿超
	 * @createtime 2021年1月27日
	 * @updator 陈睿超
	 * @updatetime 2021年1月27日
	 */
	@RequestMapping(value = "/receptionChargePlan")
	@ResponseBody
	public List<Object> receptionChargePlan(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("ReceptionChargeInfo", "gId", id);
		}
		return list;
	}
	
	/*
	 * 查询会议计划
	 * @author 张迅
	 * @createtime 2020-02-13
	 * @updatetime 2020-02-13
	 */
	@RequestMapping(value = "/meetPlan")
	@ResponseBody
	public List<Object> meetPlan(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("MeetingPlan", "gId", id);
		}
		return list;
	}
	
	
	/*
	 * 查询出差人员信息
	 * @author 沈帆
	 * @createtime 2020-01-14
	 * @updatetime 2020-01-14
	 */
	@RequestMapping(value = "/travelp")
	@ResponseBody
	public List<Object> travelp(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("TravelPeopleInfo", "trId", id);
		}
		return list;
	}
	
	
	/*
	 * 查询公车运维明细信息
	 * @author 沈帆
	 * @createtime 2020-02-14
	 * @updatetime 2020-02-14
	 */
	@RequestMapping(value = "/officeCar")
	@ResponseBody
	public List<Object> officeCar(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("OfficeCar", "gId", id);
		}
		return list;
	}
	/*
	 * 查询公务接待住宿费明细信息
	 * @author 沈帆
	 * @createtime 2020-02-15
	 * @updatetime 2020-02-15
	 */
	@RequestMapping(value = "/receptionHotel")
	@ResponseBody
	public List<Object> receptionHotel(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("ReceptionHotel", "gId", id);
		}
		return list;
	}
	
	/*
	 * 查询公务接待餐费明细信息
	 * @author 沈帆
	 * @createtime 2020-02-15
	 * @updatetime 2020-02-15
	 */
	@RequestMapping(value = "/receptionFood")
	@ResponseBody
	public List<Object> receptionFood(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("ReceptionFood", "gId", id);
		}
		return list;
	}
	
	
	/*
	 * 查询公务接待住其他费用明细信息
	 * @author 沈帆
	 * @createtime 2020-02-15
	 * @updatetime 2020-02-15
	 */
	@RequestMapping(value = "/receptionOther")
	@ResponseBody
	public List<Object> receptionOther(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("ReceptionOther", "gId", id);
		}
		return list;
	}
	
	/*
	 * 查询公务出国公杂费
	 * @author 赵孟雷	
	 * @createtime 2020-05-23
	 * @updatetime 2020-05-23
	 */
	@RequestMapping(value = "/miscellaneousFee")
	@ResponseBody
	public List<Object> miscellaneousFee(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("MiscellaneousFeeInfo", "gId", id);
		}
		return list;
	}
	
	/*
	 * 查询培训日程信息
	 * @author 沈帆
	 * @createtime 2020-02-17
	 * @updatetime 2020-02-17
	 */
	@RequestMapping(value = "/trainPlan")
	@ResponseBody
	public List<Object> trainPlan(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("MeetPlan", "tId", id);
		}
		return list;
	}
	
	/*
	 * 查询培训讲师信息
	 * @author 沈帆
	 * @createtime 2020-05-12
	 * @updatetime 2020-05-12
	 */
	@RequestMapping(value = "/trainLecturer")
	@ResponseBody
	public List<Object> trainLecturer(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("LecturerInfo", "tId", id);
		}
		return list;
	}
	
	/*
	 * 查询公务出国出访计划信息
	 * @author 沈帆
	 * @createtime 2020-02-17
	 * @updatetime 2020-02-17
	 */
	@RequestMapping(value = "/abroadPlan")
	@ResponseBody
	public List<Object> abroadPlan(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("AbroadPlan", "gId", id);
		}
		return list;
	}
	
	/*
	 * 查询公务出国人员信息
	 * @author 沈帆
	 * @createtime 2020-02-17
	 * @updatetime 2020-02-17
	 */
	@RequestMapping(value = "/abroadPeople")
	@ResponseBody
	public List<Object> abroadPeople(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("AbroadAppliPepoleInfo", "aId", id);
		}
		return list;
	}
	/*
	 * 跳转到指标选择页面
	 * @author 叶崇晖
	 * @createtime 2018-08-02
	 * @updatetime 2018-08-02
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		return "/WEB-INF/view/expend/apply/applyIndex";
	}
	
	/**
	 * 事前申请台账导出
	 * @author 叶崇晖
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/export")
	public String export(ModelMap model, HttpServletResponse response, HttpServletRequest request, String gCode, String gName, String deptName){
		OutputStream out = null;
		try {
			//初始化
			response.setHeader("Content-Disposition","attachment; filename="+new String("SQSQTZ.xls".getBytes("gb2312"), "iso8859-1"));   
			out = new BufferedOutputStream(response.getOutputStream());   
			response.setContentType("application/msexcel");   
			String path = request.getSession().getServletContext().getRealPath("/resource");
			String filePath=path+"/download/事前申请导出模板.xls";
			filePath = filePath.replace("\\","/");
			//台账数据
			ApplicationBasicInfo bean = new ApplicationBasicInfo();
			bean.setgCode(gCode);
			bean.setgName(gName);
			bean.setDeptName(deptName);
			
			List<ApplicationBasicInfo> ledgerData = applyMng.ledgerList(bean,getUser());
			
			//生成excel并导出
			HSSFWorkbook workbook = applyMng.exportLedger(ledgerData, filePath);
			
			if(workbook==null){
				out.flush();   
				return null;
			}
			workbook.write(out);   
			out.flush();   
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return null;
	}
	
	private void validataApplyData(String type,
			MeetingAppliInfo meetingBean, 			//会议信息
			TrainingAppliInfo trainingBean, 		//培训信息
			TravelAppliInfo travelBean,				//差旅信息
			ReceptionAppliInfo receptionBean,		//公务接待信息
			OfficeCar officeBean,					//公务用车信息
			AbroadAppliInfo abroadBean, String a)	{			//公务出国信息
		if (type.equals("1")) {
			meetingBean = null;
			trainingBean = null;
			travelBean = null;
			receptionBean = null;
			officeBean = null;
			abroadBean = null;
		} else if(type.equals("2")) {
			trainingBean = null;
			travelBean = null;
			receptionBean = null;
			officeBean = null;
			abroadBean = null;
		} else if(type.equals("3")) {
			meetingBean = null;
			travelBean = null;
			receptionBean = null;
			officeBean = null;
			abroadBean = null;
		} else if(type.equals("4")) {
			meetingBean = null;
			trainingBean = null;
			receptionBean = null;
			officeBean = null;
			abroadBean = null;
		} else if(type.equals("5")) {
			meetingBean = null;
			trainingBean = null;
			travelBean = null;
			officeBean = null;
			abroadBean = null;
		} else if(type.equals("6")) {
			meetingBean = null;
			trainingBean = null;
			receptionBean = null;
			travelBean = null;
			abroadBean = null;
		} else if(type.equals("7")) {
			meetingBean = null;
			trainingBean = null;
			travelBean = null;
			receptionBean = null;
			officeBean = null;
		}
		
		a="2";
	}
	
	@RequestMapping(value="/calcTravelCost")
	@ResponseBody
	public Float calcTravelCost(String id, String realDates, String realDatee){
		
		try {
			
			//获取住宿支出标准
			ExpenditureMatter bean = expMatterMng.findById(Integer.valueOf(id));
			Float price = Float.valueOf(bean.getFeStandard());
			Float priceHigh = price * (1 + Float.valueOf(bean.getFext5())/100);
			//计算旺季时间段
			String dateStr1 = String.valueOf(DateUtil.getCurrentYear()) + "-" + bean.getFext3().substring(5,bean.getFext3().length());//当前年度 + 数据库中的年月 拼接
			String dateStr2 = String.valueOf(DateUtil.getCurrentYear()) + "-" + bean.getFext4().substring(5,bean.getFext4().length());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(dateStr1);
			Date date2 = sdf.parse(dateStr2);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			//住宿天数 = 出差天数 - 1
			Date dateTemp = sdf.parse(realDatee);
			Calendar calTemp = Calendar.getInstance();
			calTemp.setTime(dateTemp);
			calTemp.add(Calendar.DAY_OF_YEAR, -1);
			realDatee = sdf.format(calTemp.getTime());
			// 计算开支：根据住宿天数、旺季时间、支出标准
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			if (cal1.before(cal2)) {
				//非跨年
				int[] array = DateUtil.calcRepeatDate(sd.parse(realDates), sd.parse(realDatee), cal1.getTime(), cal2.getTime());
				Float totalCost = array[0] * price + array[1] * priceHigh;
				System.out.println(totalCost);
				return totalCost;
			} else {
				//跨年 把年初和年尾都进行计算
				String currentYear = DateUtil.getCurrentYear();//2019-12-1  2019-01-01
				cal1.set(Calendar.YEAR, Integer.valueOf(currentYear) - 1);	//2018-12-1
				cal2.set(Calendar.YEAR, Integer.valueOf(currentYear));		//2019-01-01
				int[] array1 = DateUtil.calcRepeatDate(sd.parse(realDates), sd.parse(realDatee), cal1.getTime(), cal2.getTime());
				
				cal1.add(Calendar.YEAR, 1);//2019-12-01  
				cal2.add(Calendar.YEAR, 1);//2020-01-01
				int[] array2 = DateUtil.calcRepeatDate(sd.parse(realDates), sd.parse(realDatee), cal1.getTime(), cal2.getTime());
				
				Float totalCost = array1[0] * price + array1[1] * priceHigh + array2[0] * price + array2[1] * priceHigh;
				return totalCost;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 树形图：项目以及具体的支出科目
	 */
	// 获得支出项科目树（一级节点并且有默认值）
	@RequestMapping(value = "/treeIndex")
	@ResponseBody
	public List<TreeEntity> treezZckm(String id, String qName, String sCode,String indexType,ModelMap model,String type) {
		
		qName = StringUtil.setUTF8(qName);
		if (StringUtil.isEmpty(id)) {
			//默认打开1级节点
			return getTree2(sCode,indexType,type);
		} else {
			return getTree1(id, qName,type);
		}

	}
	
	// 获得支出项科目树(0级节点)
	private List<TreeEntity> getTree2(String sCode,String indexType,String type) {
		String Stype = applyMng.acquireEncoding(type);
		List<Integer> pidList = applyMng.getPidListByparentCode(Stype);
		String pIdS = "";
		for (Integer pId : pidList) {
			pIdS += pId+",";
		}
		if(pidList.size()>0){
			pIdS = pIdS.substring(0, pIdS.length()-1);
		}
		//初始化
		List<TreeEntity> treeList = new ArrayList<>();
		List<Object[]> objList = null;
		//获得一级科目,项目指标 2 5 12 21
		objList = applyMng.getOutComeSubject(null, "0", new SimpleDateFormat("yyyy").format(new Date()), null,indexType,pIdS,getUser().getDepart().getId());

		if (objList != null && objList.size() > 0) {
			// 查询是否有下级节点
			String nodeIds = "";// 2 5 12 21
			for (Object[] array : objList) {
				if ("".equals(nodeIds)) {
					nodeIds = String.valueOf(array[3]) ;
				} else {
					nodeIds = nodeIds + "," + String.valueOf(array[3]);
				}
			}
			Map<String, Integer> pidMap = applyMng.getPidMap(null, nodeIds, new SimpleDateFormat("yyyy").format(new Date()), null);//84 85 86
			//整合生成tree
			for (Object[] array : objList) {
				// 节点id 节点名称 节点代码 父节点代码
				String nodeId = String.valueOf(array[3]);
				String nodeName = (String) array[1];
//				String nodeCode = (String) array[0];
				TreeEntity node = new TreeEntity();
				node.setText(nodeName);// 名称
				//node.setCode(nodeCode);// 节点代码
				node.setId(nodeId);// 节点id
//				node.setParentCode("");// 父节点代码
				if (pidMap.get(nodeId) != null) {
					node.setState("closed");
				} else {
					node.setLeaf(true);
				}
				treeList.add(node);
			}
		}

		return treeList;
	}

	// 获得支出项科目树（经济支出科目,子节点）
	private List<TreeEntity> getTree1(String id, String qName,String type) {

		List<TreeEntity> treeList = new ArrayList<>();
		List<Object[]> objList = null;
		// 查询数据
		String Stype = applyMng.acquireEncoding(type);
		objList = applyMng.getOutDetail(id,Stype);
		// 放入tree值
		if (objList != null && objList.size() > 0) {
			for (Object[] array : objList) {
				// 节点id 节点名称 节点代码 父节点代码
				String nodeId = String.valueOf(array[0]);
				String nodeName = (String) array[1];
				String nodeCode = (String) array[2];
				String fzrName = (String) array[3];
				String indexId = String.valueOf(array[5]) ;
				String syAmount = String.valueOf(array[4]) ; 
				BigDecimal bd = new BigDecimal(syAmount);
				syAmount = bd.toPlainString();
				String pfAmount = String.valueOf(array[6]);
				String ysDate = String.valueOf(array[7]);
				String mainDepart = String.valueOf(array[8]);
				String djAmonut = String.valueOf(array[9]);
				String indexName = String.valueOf(array[10]);
				String indexCode = String.valueOf(array[11]);
				
				
				TreeEntity node = new TreeEntity();
				node.setText(nodeName);// 名称
				node.setId(nodeId);// 节点id
				node.setCode(nodeCode);//节点代码
				node.setCol1(fzrName);//项目负责人
				node.setCol2(syAmount);//可用金额
				node.setCol3(indexId);//指标id
				node.setCol4(pfAmount);//预算批复金额
				node.setCol5(ysDate);//预算批复时间
				node.setCol6(mainDepart);//使用部门
				node.setCol7(djAmonut);//冻结金额
				node.setCol8(indexName);//指标名称
				node.setCol9(indexCode);//指标名称Code
				
				node.setLeaf(true);
				treeList.add(node);
			}
		}
		return treeList;
	}
	
	@RequestMapping(value="/choiceIndex")
	public String choiceIndex(String menuType, ModelMap model,String type){
		
		model.addAttribute("menuType", menuType);
		model.addAttribute("type", type);
		return "/WEB-INF/view/expend/apply/add/apply_choose_index";
	}
	
	/**
	 * 跳转到选择下一个审批人页面
	 * @param parentCode
	 * @param selected
	 * @param blanked
	 * @return
	 * @author 陈睿超
	 * @createTime 2019年8月12日
	 * @updateTime 2019年8月12日
	 */
	@RequestMapping("/chooseNextRole")
	public String chooseDepart(String parentCode,String selected,String blanked){
		return "/WEB-INF/view/expend/apply/add/choose_nextrole";
	}
	
	
	@ResponseBody
	@RequestMapping("/chooseDepart")
	public List<ComboboxJson> lookJson(String parentCode,String selected,String blanked){
		List<Lookups> list = departMng.getAllDeptsLookups(null);
		return getComboboxJson(list,selected);
	}
	
	/**
	 * @Description: 事情申请退回
	 * @param @param id
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author 赵孟雷
	 * @date 2019年10月8日
	 */
	@RequestMapping(value = "/applyReCall")
	@ResponseBody
	public Result applyReCall(Integer id) {
		try {
			//传回来的id是主键
			applyMng.ApplyReCall(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"撤回失败，请联系管理员！");
		}
		return getJsonResult(true,"撤回成功！");	
	}
	
	/**
	 * 
	 * @Description 校验通用事项下级审批人角色
	 * @author 汪耀
	 * @param id
	 * @return
	 * @return Boolean
	 * @throws
	 * @date 2020年1月13日
	 */
	@RequestMapping(value = "/checkNextUser")
	@ResponseBody
	public Boolean checkNextUser(String id) {
		String roles = roleMng.getRolesByUserId(id);
		if(roles.contains("会计岗")){
			return false;
		}
		return true;
	}
	
	
	
	/*
	 * 差旅申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-13
	 * @updatetime 2020-02-13
	 */
	@RequestMapping(value = "/saveTravel")
	@ResponseBody
	public Result saveTravel(ApplicationBasicInfo bean,
			TravelAppliInfo travelBean,				//差旅信息
			String travelPeop,						//行程单Json
			String outsideTraffic,					//城市间交通费Json
			String inCity,							//市内交通费Json
			String hotelJson,						//住宿费Json
			String foodJson,						//伙食费Json
			String files,							//附件的地址
			ModelMap model,
			String chalvfiles,
			String chuxingfiles) {
		Lock lock = new ReentrantLock();
		
		lock.lock();
		try {
			//判断申请类型,去除不需要保存的实体
			String type = bean.getType();
			//判断申请金额是否超过可用金额
			Boolean b=budgetIndexMgrMng.checkAmounts(String.valueOf(bean.getIndexId()), bean.getAmount()/10000);
			if(b){
				//保存
				applyMng.saveTravel(bean,travelBean,getUser(), travelPeop, files,outsideTraffic,inCity,hotelJson,foodJson);
//				TravelAppliInfo travel = (TravelAppliInfo) applyMng.getObject("TravelAppliInfo", "gId", bean.getgId());
//				attachmentMng.joinEntity(travel, chalvfiles);
//				attachmentMng.joinEntity(travel, chuxingfiles);
			}else {
				return getJsonResult(false,"申请金额超出可用金额，不允许提交！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 公车运维申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-14
	 * @updatetime 2020-02-14
	 */
	@RequestMapping(value = "/saveOfficeCar")
	@ResponseBody
	public Result saveOfficeCar(ApplicationBasicInfo bean,
			String officeCar,						//被接待人Json
			String files,							//附件的地址
			ModelMap model) {
		Lock lock = new ReentrantLock();
		
		lock.lock();
		try {
			//判断申请类型,去除不需要保存的实体
			String type = bean.getType();
			//判断申请金额是否超过可用金额
			Boolean b=budgetIndexMgrMng.checkAmounts(String.valueOf(bean.getIndexId()), bean.getAmount()/10000);
			if(b){
				//保存
				applyMng.saveOfficeCar(bean,getUser(),officeCar, files);
			}else {
				return getJsonResult(false,"申请金额超出可用金额，不允许提交！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 报销分页数据获得
	 * @author 沈帆
	 * @createtime 2020-01-16
	 * @updatetime 2020-01-16
	 */
	@RequestMapping(value = "/applyReimbPage")
	@ResponseBody
	public JsonPagination applyReimbPage(Integer reimburseType ,ApplicationBasicInfo bean, ModelMap model, Integer page, Integer rows,String rFlowStauts,String searchData){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = applyMng.reimbPageList(bean, page, rows, reimburseType, getUser(),rFlowStauts,searchData);
		
		return getJsonPagination(p, page);
	}
	
	/**
	 * 事前申请加载伙食补助信息
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年4月18日
	 * @updator 陈睿超
	 * @updatetime 2020年4月18日
	 */
	@ResponseBody
	@RequestMapping("/foodJson")
	public JsonPagination foodJsonPage(ApplicationBasicInfo bean, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		List<FoodAllowanceInfo> list = foodAllowanceInfoMng.findbygId(bean.getgId(),bean.getTravelType());
		Pagination p = new Pagination();
		p.setList(list);
		p.setPageNo(1);
		p.setPageSize(list.size());
		p.setTotalCount(list.size());
		return getJsonPagination(p, page);
	}
	
	/**
	 * 事前申请加载住宿费信息
	 * @param bean
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年4月18日
	 * @updator 陈睿超
	 * @updatetime 2020年4月18日
	 */
	@ResponseBody
	@RequestMapping("/hotelJson")
	public JsonPagination hotelJson(ApplicationBasicInfo bean, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		List<HotelExpenseInfo> list = hotelExpenseInfoMng.findbygId(bean.getgId(),bean.getTravelType());
		Pagination p = new Pagination();
		p.setList(list);
		p.setPageNo(1);
		p.setPageSize(list.size());
		p.setTotalCount(list.size());
		return getJsonPagination(p, page);
	}
	
	/**
	 * 事前申请加载宴请费信息
	 * @param bean
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年5月24日
	 * @updator 赵孟雷
	 * @updatetime 2020年5月24日
	 */
	@ResponseBody
	@RequestMapping("/feteCostJson")
	public JsonPagination feteCostJson(Integer gId, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		List<FeteCostInfo> list = feteCostInfoMng.findbygId(gId);
		Pagination p = new Pagination();
		p.setList(list);
		p.setPageNo(1);
		p.setPageSize(list.size());
		p.setTotalCount(list.size());
		return getJsonPagination(p, page);
	}

	/*
	 * 行程清单查询
	 * @author 赵孟雷
	 * @createtime 2020-04-18
	 * @updatetime 2020-04-18
	 */
	@RequestMapping(value = "/applyTravelPage")
	@ResponseBody
	public JsonPagination applyTravelPage(TravelAppliInfo bean, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = 100;}
		Pagination p = travelAppliInfoMng.travelPageList(page, rows, bean);
		if("GWCC".equals(bean.getTravelType())){
			List<TravelAppliInfo> list = (List<TravelAppliInfo>) p.getList();
			for(int x=0; x<list.size(); x++) {
				//序号设置	
				list.get(x).setTravelAreaId(Integer.valueOf(list.get(x).getTravelArea()));	
			}
			
		}
		return getJsonPagination(p, page);
	}
	/*
	 * 城市间交通费查询
	 * @author 赵孟雷
	 * @createtime 2020-04-18
	 * @updatetime 2020-04-18
	 */
	@RequestMapping(value = "/applyOutsideTrafficPage")
	@ResponseBody
	public JsonPagination applyOutsideTrafficPage(OutsideTrafficInfo bean, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = 100;}
		Pagination p = outsideTrafficInfoMng.outsideTrafficInfoPageList(page, rows, bean);
		return getJsonPagination(p, page);
	}
	/*
	 * 城市间交通费查询
	 * @author 赵孟雷
	 * @createtime 2020-05-20
	 * @updatetime 2020-05-20
	 */
	@RequestMapping(value = "/AbroadAppliPepole")
	@ResponseBody
	public JsonPagination AbroadAppliPepole(OutsideTrafficInfo bean, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = 100;}
		Pagination p = outsideTrafficInfoMng.outsideTrafficInfoPageList(page, rows, bean);
		return getJsonPagination(p, page);
	}
	/*
	 * 查询公务出国国际旅费
	 * @author 赵孟雷
	 * @createtime 2020-05-20
	 * @updatetime 2020-05-20
	 */
	@RequestMapping(value = "/internationalTravelingExpense")
	@ResponseBody
	public JsonPagination internationalTravelingExpense(Integer gId, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = 100;}
		Pagination p = new Pagination();
		List<InternationalTravelingExpense> list = internationalTravelingExpenseInfoMng.findbygId(gId);
		for (InternationalTravelingExpense expense : list) {
			Vehicle vehicle = vehicleMng.findById(Integer.valueOf(expense.getVehicle()));
			expense.setVehicleText(vehicle.getName());
		}
		p.setList(list);
		return getJsonPagination(p, page);
	}
	/*
	 * 市内交通费查询
	 * @author 赵孟雷
	 * @createtime 2020-04-18
	 * @updatetime 2020-04-18
	 */
	@RequestMapping(value = "/applyInCityPage")
	@ResponseBody
	public JsonPagination applyInCityPage(InCityTrafficInfo bean, ModelMap model, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = 100;}
		Pagination p = inCityTrafficInfoMng.inCityInfoPageList(page, rows, bean);
		return getJsonPagination(p, page);
	}
	
	/**
	 * 
	 * 差旅行程单跳转地区页面
	 * @author 陈睿超
	 * @createtime 2020年4月20日
	 * @updator 陈睿超
	 * @updatetime 2020年4月20日
	 */
	@RequestMapping("/choose")
	public String choose(ModelMap map ,String type,String index,String tabId){
		map.addAttribute("type", type);
		map.addAttribute("index", index);
		map.addAttribute("tabId", tabId);
		return "/WEB-INF/view/expend/apply/hotelstd_choose";
	}
	/**
	 * 
	 * 公务出国跳转国家城市页面
	 * @author 赵孟雷
	 * @createtime 2020年5月15日
	 * @updator 赵孟雷
	 * @updatetime 2020年5月15日
	 */
	@RequestMapping("/chooseAbroad")
	public String chooseAbroad(ModelMap map ,String type,String index){
		map.addAttribute("index", index);
		return "/WEB-INF/view/expend/apply/hotelstd_choose_abroad";
	}
	
	/**
	 * 差旅行程单选择地区
	 * @author 赵孟雷
	 * @createtime 2020年4月20日
	 * @updator 赵孟雷
	 * @updatetime 2020年4月20日
	 */
	@RequestMapping("/pageList")
	@ResponseBody
	public JsonPagination pageList(String area,String outType,  ModelMap model, Integer page, Integer rows){
		
		if (page == null) page = 1;
		if (rows == null) rows = 100;
		Pagination p = null;
		if ("travel".equals(outType)) {
			HotelStandard bean = new HotelStandard();
			bean.setArea(area);
			p = standardMng.pageListTravel(bean, getUser(), page, rows);
			
		} else if ("meet".equals(outType)) {
			MeetStandard bean = new MeetStandard();
			p = standardMng.pageListMeet(bean, getUser(), page, rows);
			
		} else if ("train".equals(outType)) {
			TrainStandard bean = new TrainStandard();
			p = standardMng.pageListTrain(bean, getUser(), page, rows);
			
		} else if ("recep".equals(outType)) {
			RecepStandard bean = new RecepStandard();
			p = standardMng.pageListRecep(bean, getUser(), page, rows);
			
		} else if ("aboard".equals(outType)) {
			AboardStandard bean = new AboardStandard();
			p = standardMng.pageListAboard(bean, getUser(), page, rows);
		}
		return getJsonPagination(p, page);
	}
	/**
	 * 公务出国选择地区加载数据方法
	 * @author 赵孟雷
	 * @createtime 2020年5月15日
	 * @updator 赵孟雷
	 * @updatetime 2020年5月15日
	 */
	@RequestMapping("/pageListAbroad")
	@ResponseBody
	public JsonPagination pageListAbroad(AboardCountryStandard bean,  ModelMap model, Integer page, Integer rows){
		if (page == null) page = 1;
		if (rows == null) rows = 1000;
		Pagination p = aboardCountryStandardMng.pageListAboard(bean, getUser(), page, rows);
		return getJsonPagination(p, page);
	}
	
	
	/**
	 * 跳转到选择行程单人员选择页面
	 * @param model
	 * @param index
	 * @param editType
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年4月20日
	 * @updatetime 2020年4月20日
	 */
	@RequestMapping("/chooseUser")
	public String chooseUser(ModelMap model,String index,String editType,String tabId){
		model.addAttribute("index", index);
		model.addAttribute("editType", editType);
		model.addAttribute("tabId", tabId);
		model.addAttribute("roleName", getUser().getRoleName());
		return "/WEB-INF/view/expend/apply/choose_userrole";
	}
	
	
	/**
	 * 
	 * @Description: 查询字典里相应数据
	 * @param @param parentCode
	 * @param @param selected
	 * @param @param blanked
	 * @param @return   
	 * @return List<ComboboxJson>  
	 * @throws
	 * @author 赵孟雷
	 * @date 2019年11月11日
	 */
	@RequestMapping("/lookupsJson")
	@ResponseBody
	public List<ComboboxJson> lookJsonCar(String parentCode,String selected,String blanked){
		List<Lookups> list = applyMng.getLookupsJson(parentCode, parentCode,selected);
		return getComboboxJson(list,selected);
	}
	
	
	/**
	 * 差旅申请和报销 重新请求审批流
	 * @author 赵孟雷
	 * @createtime 2019-05-24
	 * @updatetime 2019-05-24
	 */
	@RequestMapping(value = "/refreshProcess")
	public String rerefreshProcess(ModelMap model, String fpPype){
		User user = getUser();
		//业务范围
		String busiArea = null;
		if ("GWCC".equals(fpPype)) {
			busiArea = "CLSQ";
		} else if ("GWCX".equals(fpPype)) {
			busiArea = "GWCXSQ";
		}
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), busiArea, getUser().getDpID(), null, null, null, null, null, null);
//		if("8".equals(user.getDepart().getId())){
//			if("GWCXSQ".equals(busiArea)&&!user.getRoleName().contains("分管财务局长")){
//				for (int i = nodeConfList.size()-1; i >= 0; i--) {
//					if("86".equals(nodeConfList.get(i).getUserId())){
//						nodeConfList.remove(i);
//					}
//				}
//			}
//		}
//		if(user.getRoleName().contains("普通用户")&&!user.getRoleName().contains("处室负责人")){
//			for (int i = nodeConfList.size()-1; i >= 0; i--) {
//				if(!"8".equals(user.getDepart().getId())&&"GWCXSQ".equals(busiArea)){
//					if(nodeConfList.get(i).getUserId().equals(user.getDepart().getManager().getId())){
//						nodeConfList.remove(i);
//					}
//				}
//			}
//		}
//		//2020.11.09应客户要求
//		//局长申请时，公务出差还需要有陈局审批。局长-财务分管局长
//		//没有局长时只需要局长审批即可,不需要陈局审批。
//		if(!user.getRoleCode().contains("ab1383b1-7ac4-11e9-8688-005056a17ba3")&&busiArea.equals("CLSQ")&&"8".equals(user.getDpID())){
//			nodeConfList.remove(0);
//		}
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/check_system";
	}
	
	
	
	
	
	/**
	 * 
	 * @param list
	 * @return
	 * @author 赵孟雷	
	 * @createtime 2020年5月27日
	 * @updator 赵孟雷
	 * @updatetime 2020年5月27日
	 */
	@ResponseBody
	@RequestMapping("/getRepetition")
	public int getRepetition(String list){
		List<TravelAppliInfo> lists = JSON.parseObject(list,new TypeReference<List<TravelAppliInfo>>(){});
		int num =0;
		for (int i = 0; i < lists.size(); i++) {
			TravelAppliInfo bean  = new TravelAppliInfo();
			bean.setTravelAreaTime(lists.get(i).getTravelAreaTime());
			bean.setgId(lists.get(i).getgId());
			List<TravelAppliInfo> appliInfo = travelAppliInfoMng.travelPageRepetitionList(bean);
			for (int j = 0; j < appliInfo.size(); j++){
				if(appliInfo.get(j).getTravelAttendPeopId().equals(lists.get(i).getTravelAttendPeopId())){
					num =1;
				}
			}
		}
		return num;
	}
}
