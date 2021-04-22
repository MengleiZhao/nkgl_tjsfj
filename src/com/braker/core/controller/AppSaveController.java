package com.braker.core.controller;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.braker.common.page.Result;
import com.braker.common.util.DateUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.expend.apply.manager.ApplyMeetMng;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.OfficeCar;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.loan.manager.LoanMng;
import com.braker.icontrol.expend.loan.manager.PaymentMng;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.loan.model.LoanPayeeInfo;
import com.braker.icontrol.expend.loan.model.Payment;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbPayeeInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;


/**
 * APP保存
 * 作用：接受APP后台发送的保存请求
 * 实现方式：请求转发
 * 返回请求端值的类型：由转发后方法定义
 * @author 沈帆
 * @createtime 2020-06-1
 */

@Controller
@RequestMapping(value = "/appSave")
public class AppSaveController extends BaseController{
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private ApplyMng applyMng;
	@Autowired
	private ApplyMeetMng meetMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	@Autowired
	private LoanMng loanMng;
	@Autowired
	private PaymentMng paymentMng;
	
	@RequestMapping(value = "/saveApp")
	@ResponseBody
	public Result saveApp(ApplicationBasicInfo bean,
			MeetingAppliInfo meetingBean, 			//会议信息
			TrainingAppliInfo trainingBean, 		//培训信息
			TravelAppliInfo travelBean,				//差旅信息
			ReceptionAppliInfo receptionBean,		//公务接待信息
			OfficeCar officeBean,					//公务用车信息
			AbroadAppliInfo abroadBean,				//公务出国信息
			String recePeop,						//被接待人Json
			String mingxi, 							//费用明细Json
			String storkJson, 						//接待主要行程Json
			String chargeJson, 						//接待收费明细Json
			String gwjdfafiles,						//接待方案附件
			String gwjdghfiles,						//接待公函附件
			String meetPlan,
			String files,							//附件的地址
			String hotelJson,	
			String foodJson,	
			String otherJson,	
			String trainPlan,
			String abroadPlan,
			String abroadPeople,
			String acco, String pwd,
			String jsonBean, String jsonMeetBean, String jsonTrainBean,
			String jsonRecepBean,String jsonAbroadBean,
			String trainLecturer,					//讲师信息
			String zongheJson,						//培训-综合预算费用
			String lessonJson,						//培训-师资讲课费
			String trafficJson1,					//城市间交通费和国外城市间交通费
			String trafficJson2,					//市内交通费
			String officeCar,
			String travelJson,	                    //国际旅费json
			String feeJson,							//公杂费json
			String feteJson,						//宴请费json
			String travelPeop,						//行程单Json
			String outsideTraffic,					//城市间交通费Json
			String inCity							//市内交通费Json
			) {
		Lock lock = new ReentrantLock();
		
		lock.lock();
		try {



			//对APP前端传来的参数进行处理
			User user = userMng.login(acco, pwd);
			bean = JSON.parseObject(jsonBean, ApplicationBasicInfo.class);
			meetingBean = JSON.parseObject(jsonMeetBean, MeetingAppliInfo.class);
			trainingBean = JSON.parseObject(jsonTrainBean, TrainingAppliInfo.class);
			receptionBean = JSON.parseObject(jsonRecepBean, ReceptionAppliInfo.class);
			abroadBean = JSON.parseObject(jsonAbroadBean, AbroadAppliInfo.class);

			/*if (meetingBean.getmId() == null || meetingBean.getmId() == 0) {
				meetingBean.setmId(null);
			}*/
			if (bean.getgId() == null || bean.getgId() == 0) {
				bean.setgId(null);
			}

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



			//判断申请金额是否超过可用金额
			Boolean b=budgetIndexMgrMng.checkAmounts(String.valueOf(bean.getIndexId()), bean.getAmount()/10000);
			if(b){
				//保存
				if ("2".equals(type)) {
					meetMng.save(user, bean, meetingBean, meetPlan, files,mingxi);
				}else if("3".equals(type)){
					applyMng.saveTrain(user, bean, trainingBean, trainPlan, files,trainLecturer,zongheJson,lessonJson,hotelJson,foodJson,trafficJson1,trafficJson2);
					//applyMng.saveTrain(user, bean, trainingBean, trainPlan, files);
				}else if("4".equals(type)){
					applyMng.saveTravel(bean,travelBean,user, travelPeop, files,outsideTraffic,inCity,hotelJson,foodJson);
				}else if("5".equals(type)){
					applyMng.saveRecp(user, bean, receptionBean, hotelJson,foodJson,otherJson,recePeop,storkJson, chargeJson, gwjdghfiles, gwjdfafiles);
				}else if("6".equals(type)){
					applyMng.saveOfficeCar(bean,user,officeCar, files);
				}else if("7".equals(type)){
					applyMng.saveAbroad(user, 
							bean, 
							abroadBean,
							travelJson,	                    //国际旅费json
							trafficJson1,					//城市间交通费和国外城市间交通费
							hotelJson,	                    //住宿费json
							foodJson,						//伙食费json
							feeJson,							//公杂费json
							feteJson,						//宴请费json
							otherJson,						//其他收入json
							abroadPlan,					//出访计划
							files);
				} else {
					applyMng.save(bean, meetingBean, trainingBean, travelBean, receptionBean, officeBean, abroadBean, mingxi, user, recePeop, files);
				}
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
	 * 直接报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-07-27
	 * @updatetime 2020-07-27
	 */
	@RequestMapping(value = "/saveDirectlyReimb")
	@ResponseBody
	public Result saveDirectlyReimb(DirectlyReimbAppliBasicInfo bean,
		  String mingxi,String fpIds,
		  String files,String form1,
		  String payerinfoJson,
		  ModelMap model,
		  String jsonBean,
		  String acco, String pwd) {
		try {
			//对APP前端传来的参数进行处理
			User user = userMng.login(acco, pwd);
			bean = JSON.parseObject(jsonBean, DirectlyReimbAppliBasicInfo.class);
			directlyReimbMng.saveApp(bean,files,user,mingxi,form1,payerinfoJson,fpIds);
		}catch (ServiceException e1) {
			e1.printStackTrace();
			return getJsonResult(false,e1.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	
	/*
	 * 通用事项报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-08-26
	 * @updatetime 2020-08-26
	 */
	@RequestMapping(value = "/saveCommon")
	@ResponseBody
	public Result saveCommon(ReimbAppliBasicInfo bean,String mingxi,String fpIds,String files,String form1,String payerinfoJson,
			  String jsonBean,
			  String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppCommon(bean, fpIds, mingxi,files, user, form1, payerinfoJson);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 公车运维报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2018-08-27
	 * @updatetime 2018-08-27
	 */
	@RequestMapping(value = "/saveCar")
	@ResponseBody
	public Result saveCar(ReimbAppliBasicInfo bean,String mingxi,String fpIds,String files,String payerinfoJson,
			  String jsonBean,
			  String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppCar(bean, fpIds, mingxi,files, user, payerinfoJson);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 会议报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-08-31
	 * @updatetime 2020-08-31
	 */
	@RequestMapping(value = "/saveMeeting")
	@ResponseBody
	public Result saveMeeting(ReimbAppliBasicInfo bean,MeetingAppliInfo meetingBean,String mingxi,String fpIds,String files,String payerinfoJson,
			  String jsonBean,String jsonMeetingBean,String meetPlan,
			  String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				meetingBean =JSON.parseObject(jsonMeetingBean, MeetingAppliInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppMeeting(bean,meetingBean, fpIds, mingxi,files, user,meetPlan, payerinfoJson);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 培训报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-09-03
	 * @updatetime 2020-09-03
	 */
	@RequestMapping(value = "/saveTrain")
	@ResponseBody
	public Result saveTrain(ReimbAppliBasicInfo bean,TrainingAppliInfo trainingBean,String zhysFpIds,String hotelFpIds,String foodFpIds,String traffic1FpIds,String traffic2FpIds,String files,String payerinfoJson,
			  String jsonBean,String jsonTrainingBean,String trainLecturer,String trainPlan, String zongheJson, String lessonJson, String hotelJson, String foodJson,
			  String trafficJson1, String trafficJson2,
			  String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				trainingBean =JSON.parseObject(jsonTrainingBean, TrainingAppliInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppTrain(bean, trainingBean, zhysFpIds,hotelFpIds,foodFpIds,traffic1FpIds,traffic2FpIds,trainLecturer,trainPlan,zongheJson,lessonJson,hotelJson,foodJson,trafficJson1,trafficJson2,payerinfoJson,files, user);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 公务接待报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-09-03
	 * @updatetime 2020-09-03
	 */
	@RequestMapping(value = "/saveReception")
	@ResponseBody
	public Result saveReception(ReimbAppliBasicInfo bean,ReceptionAppliInfo receptionAppliInfo,String rentFpIds,String hotelFpIds,String foodFpIds,String trafficFpIds,String otherFpIds,String files,String payerinfoJson,
			  String jsonBean,String jsonReceptionBean, String otherJson, String hotelJson, String foodJson,
			  String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				receptionAppliInfo =JSON.parseObject(jsonReceptionBean, ReceptionAppliInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppReception(bean,hotelFpIds,foodFpIds,trafficFpIds,rentFpIds,otherFpIds,files, user, receptionAppliInfo, hotelJson, foodJson, otherJson, payerinfoJson);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 公务出国报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-09-04
	 * @updatetime 2020-09-04
	 */
	@RequestMapping(value = "/saveAbroad")
	@ResponseBody
	public Result saveAbroad(ReimbAppliBasicInfo bean,AbroadAppliInfo abroadBean,String travelFpIds,String hotelFpIds,String feteFpIds,String trafficFpIds,String otherFpIds,String files,String payerinfoJson,
			  String jsonBean,String jsonAbroadBean, String travelJson,String trafficJson1,String feeJson,String feteJson,String abroadPlanJson,String otherJson, String hotelJson, String foodJson,
			  String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				abroadBean =JSON.parseObject(jsonAbroadBean, AbroadAppliInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppAbroad(bean, payerinfoJson, travelFpIds,hotelFpIds,feteFpIds,trafficFpIds,otherFpIds,
							 travelJson,	     
							 trafficJson1,					//城市间交通费和国外城市间交通费
							 hotelJson,	                    //住宿费json
							 foodJson,						//伙食费json
							 feeJson,							//公杂费json
							 feteJson,						//宴请费json
							 otherJson,						//其他收入json
							 abroadPlanJson,					//出访计划
							 abroadBean,						//出国信息
							files, user);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 合同报销保存
	 * @author 沈帆
	 * @createtime 2020-09-08
	 * @updatetime 2020-09-08
	 */
	@RequestMapping(value = "/saveCont")
	@ResponseBody
	public Result saveCont(ReimbAppliBasicInfo bean,String fpIds,String files,String payerinfoJson, String jsonBean,
			  String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppContract(bean,files, user, fpIds, payerinfoJson);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 差旅报销保存
	 * @author 沈帆
	 * @createtime 2020-09-08
	 * @updatetime 2020-09-08
	 */
	@RequestMapping(value = "/saveTravel")
	@ResponseBody
	public Result saveTravel(ReimbAppliBasicInfo bean,String trafficFpIds,String hotelFpIds,String files,String payerinfoJson, String jsonBean,
			String travelPeop,String outsideTraffic,String inCity,String hotelJson,String foodJson,
			String acco, String pwd) {
		try {
			synchronized (this) {
				//对APP前端传来的参数进行处理
				User user = userMng.login(acco, pwd);
				bean = JSON.parseObject(jsonBean, ReimbAppliBasicInfo.class);
				Boolean	b = new Boolean(true);
				if(b){
					reimbAppliMng.saveAppTravel(bean,files, user, trafficFpIds,hotelFpIds, payerinfoJson,travelPeop,outsideTraffic,inCity,hotelJson,foodJson);
				}else {
					return getJsonResult(false,"报销金额超出可用金额，不允许提交！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,getException(e));
		}
		return getJsonResult(true,"操作成功！");
	}
	
	@RequestMapping(value = "/saveLoan")
	@ResponseBody
	public Result saveLoan(LoanBasicInfo bean,LoanPayeeInfo payeeBean,String acco, String pwd,String jsonBean,String jsonPayeeBean,String files) {
		try {
			//对APP前端传来的参数进行处理
			User user = userMng.login(acco, pwd);
			bean = JSON.parseObject(jsonBean, LoanBasicInfo.class);
			payeeBean = JSON.parseObject(jsonPayeeBean, LoanPayeeInfo.class);
			loanMng.save(bean, payeeBean, user ,files);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	@RequestMapping(value = "/savePayment")
	@ResponseBody	
	public Result savePayment(Payment bean,String files,String jsonBean,String acco, String pwd) {
		
		try {
			//对APP前端传来的参数进行处理
			User user = userMng.login(acco, pwd);
			bean = JSON.parseObject(jsonBean, Payment.class);
			LoanBasicInfo loanBean = loanMng.findById(bean.getlId());
			int datenum = DateUtil.getDaySpanNoAbs(bean.getPayTime(),loanBean.getReqTime());
			if(datenum>0){
				return getJsonResult(false,"还款时间必须大于借款时间！");
			}
			bean = paymentMng.savePayment(bean, files, user);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
}
