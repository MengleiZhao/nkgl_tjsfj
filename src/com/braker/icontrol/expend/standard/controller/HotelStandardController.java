package com.braker.icontrol.expend.standard.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.model.User;
import com.braker.icontrol.expend.apply.model.AbroadPlan;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
import com.braker.icontrol.expend.apply.model.InCityTrafficInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.standard.entity.AboardCountryStandard;
import com.braker.icontrol.expend.standard.entity.AboardStandard;
import com.braker.icontrol.expend.standard.entity.HotelStandard;
import com.braker.icontrol.expend.standard.entity.MeetStandard;
import com.braker.icontrol.expend.standard.entity.RecepStandard;
import com.braker.icontrol.expend.standard.entity.Standard;
import com.braker.icontrol.expend.standard.entity.TrainStandard;
import com.braker.icontrol.expend.standard.manager.AboardStandardMng;
import com.braker.icontrol.expend.standard.manager.HotelStandardMng;
import com.braker.icontrol.expend.standard.manager.MeetStandardMng;
import com.braker.icontrol.expend.standard.manager.RecepStandardMng;
import com.braker.icontrol.expend.standard.manager.StandardMng;
import com.braker.icontrol.expend.standard.manager.TrainStandardMng;

@Controller
@RequestMapping("/hotelStandard")
public class HotelStandardController extends BaseController {

	@Autowired
	private StandardMng standardMng;
	@Autowired
	private AboardStandardMng aboardStandardMng;
	@Autowired
	private HotelStandardMng hotelStandardMng;
	@Autowired
	private MeetStandardMng meetStandardMng;
	@Autowired
	private RecepStandardMng recepStandardMng;
	@Autowired
	private TrainStandardMng trainStandardMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	
	
	
	@RequestMapping("/list")
	public String list(String outType){
		
		/*<h2 onclick="addTabs('???????????????','${base}/hotelStandard/list?outType=travel');">???????????????</h2>
		<div></div>
		<h2 onclick="addTabs('???????????????','${base}/hotelStandard/list?outType=meet');">???????????????</h2>
		<div></div>
		<h2 onclick="addTabs('???????????????','${base}/hotelStandard/list?outType=train');">???????????????</h2>
		<div></div>
		<h2 onclick="addTabs('????????????????????????','${base}/hotelStandard/list?outType=recep');">????????????????????????</h2>
		<div></div>
		<h2 onclick="addTabs('??????????????????','${base}/hotelStandard/list?outType=aboard');">??????????????????</h2>
		<div></div>
		*/
		if ("travel".equals(outType)) {
			return "/WEB-INF/gwideal_core/standard/travel_list";
		} else if ("meet".equals(outType)) {
			return "/WEB-INF/gwideal_core/standard/meet_list";
		} else if ("train".equals(outType)) {
			return "/WEB-INF/gwideal_core/standard/train_list";
		} else if ("recep".equals(outType)) {
			return "/WEB-INF/gwideal_core/standard/recep_list";
		} else if ("aboard".equals(outType)) {
			return "/WEB-INF/gwideal_core/standard/aboard_list";
		}
		return null;
	}
	
	@RequestMapping("/add")
	public String add(ModelMap map, String outType){
		
		map.addAttribute("openType", "add");
		if ("travel".equals(outType)) {
			HotelStandard bean = new HotelStandard();
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/travel_add";
			
		} else if ("meet".equals(outType)) {
			MeetStandard bean = new MeetStandard();
			bean.setCostLevel("1");
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/meet_add";
			
		} else if ("train".equals(outType)) {
			TrainStandard bean = new TrainStandard();
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/train_add";
			
		} else if ("recep".equals(outType)) {
			RecepStandard bean = new RecepStandard();
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/recep_add";
			
		} else if ("aboard".equals(outType)) {
			AboardStandard bean = new AboardStandard();
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/aboard_add";
		}
		return null;
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, ModelMap map, String outType){
		
		map.addAttribute("openType", "edit");
		
		if ("travel".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("HotelStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/travel_add";
			
		} else if ("meet".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("MeetStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/meet_add";
			
		} else if ("train".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("TrainStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/train_add";
			
		} else if ("recep".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("RecepStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/recep_add";
			
		} else if ("aboard".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("AboardStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/aboard_add";
		}
		return null;
	}

	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Integer id, ModelMap map, String outType){
		
		if ("travel".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("HotelStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/detail/travel_detail";
			
		} else if ("meet".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("MeetStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/detail/meet_detail";
			
		} else if ("train".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("TrainStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/detail/train_detail";
			
		} else if ("recep".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("RecepStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/detail/recep_detail";
			
		} else if ("aboard".equals(outType)) {
			Standard bean = standardMng.findConfigByClassAndId("AboardStandard", id);
			map.addAttribute("bean", bean);
			return "/WEB-INF/gwideal_core/standard/detail/aboard_detail";
		}
		return null;
	}
	
	/**
	 * ?????????????????????????????????
	 * @param map
	 * @return
	 * @author ?????????
	 * @createtime 2020???4???20???
	 * @updator ?????????
	 * @updatetime 2020???4???20???
	 */
	@RequestMapping("/choose")
	public String choose(ModelMap model,String index,String editType,String tabId){
		model.addAttribute("index", index);
		model.addAttribute("editType", editType);
		model.addAttribute("tabId", tabId);
		return "/WEB-INF/gwideal_core/hotelstd/hotelstd_choose";
	}
	/**
	 * ?????????????????????????????????
	 * @param model
	 * @param index
	 * @param editType
	 * @return
	 * @author ?????????
	 * @createtime 2020???4???20???
	 * @updator ?????????
	 * @updatetime 2020???4???20???
	 */
	@RequestMapping("/chooseUser")
	public String chooseUser(ModelMap model,String index,String editType,String tabId){
		model.addAttribute("index", index);
		model.addAttribute("editType", editType);
		model.addAttribute("tabId", tabId);
		return "/WEB-INF/view/expend/apply/add/choose_userrole";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Result save(String outType, MeetStandard meetBean, TrainStandard trainBean, RecepStandard recepBean, AboardStandard aboardBean, HotelStandard travelBean){//????????????????????????????????????
		try {
			if ("travel".equals(outType)) {
				standardMng.saveHotelStandard(travelBean, getUser());
			} else if ("meet".equals(outType)) {
				standardMng.saveHotelStandard(meetBean, getUser());
			} else if ("train".equals(outType)) {
				standardMng.saveHotelStandard(trainBean, getUser());
			} else if ("recep".equals(outType)) {
				standardMng.saveHotelStandard(recepBean, getUser());
			} else if ("aboard".equals(outType)) {
				standardMng.saveHotelStandard(aboardBean, getUser());
			}
			return getJsonResult(true, Result.saveSuccessMessage);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return getJsonResult(true, Result.saveSuccessMessage);
		}
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable Integer id,String fId,String outType){
		try{
			if ("travel".equals(outType)) {
				hotelStandardMng.deleteById(id);
			} else if ("meet".equals(outType)) {
				meetStandardMng.deleteById(id);
			} else if ("train".equals(outType)) {
				trainStandardMng.deleteById(id);
			} else if ("recep".equals(outType)) {
				recepStandardMng.deleteById(id);
			} else if ("aboard".equals(outType)) {
				aboardStandardMng.deleteById(id);
			}
			personalWorkMng.deleteById(Integer.valueOf(fId));
			personalWorkMng.sendMessageToUser(getUser().getId(),0);
			return getJsonResult(true, Result.deleteSuccessMessage);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return getJsonResult(false, Result.deleteSuccessMessage);
		}
	}
	
	@RequestMapping("/pageList")
	@ResponseBody
	public JsonPagination pageList(String outType,  ModelMap model, Integer page, Integer rows, String travel_area, String meet_level, String train_level, String recep_, String aboard_){
		
		if (page == null) page = 1;
		if (rows == null) rows = 10;
		Pagination p = null;
		if ("travel".equals(outType)) {
			HotelStandard bean = new HotelStandard();
			bean.setArea(travel_area);
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
	 * ?????????????????????
	 */
	@RequestMapping("/calcCost")
	@ResponseBody
	public List<Object> calcCost(Integer configId, String outType, 
			String travelDays, String hotelDays, 
			Integer meet_personNum, Integer meet_num,
			String train_teachLevel, Integer train_personNum, Integer train_num,
			Integer recep_personNum, Integer recep_num,
			Integer car_num,
			Integer aboard_num,String userId,String day1,String day2){
		try {
			List<Object> resList = new ArrayList<Object>();
			if ("travel".equals(outType)) {
				resList = standardMng.calcCostTravel1(configId, travelDays, hotelDays,userId,day1,day2);
				
			} else if ("meet".equals(outType)) {
				resList = standardMng.calcCostMeet(configId, meet_personNum, meet_num);
				
			} else if ("train".equals(outType)) {
				resList = standardMng.calcCostTrain(configId, train_teachLevel, train_personNum, train_num);
				
			} else if ("recep".equals(outType)) {
				resList = standardMng.calcCostRecep(configId, recep_personNum, recep_num);
				
			} else if ("aboard".equals(outType)) {
				resList = standardMng.calcCostAboard(configId, aboard_num);
				
			} else if ("car".equals(outType)) {
				resList = standardMng.calcCostCar(configId, car_num);
			}
			return resList;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ?????????????????????
	 */
	@RequestMapping("/calcCostAbroad")
	@ResponseBody
	public List<Object> calcCostAbroad(Integer configId){
		try {
			List<Object> resList = new ArrayList<Object>();
				AboardCountryStandard config = (AboardCountryStandard) standardMng.findConfigByClassAndId("AboardCountryStandard",configId);			//????????????
				resList.add(config);
			return resList;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping("/getStd")
	@ResponseBody
	public Object getStd(String applyType,Integer train_trainingType, Integer configId,String meetType,Integer meet_personNum, Integer meet_num){
		if ("meet".equals(applyType)) {
			return standardMng.getMeetStd(meetType, meet_personNum,  meet_num, configId);
		}else if ("train".equals(applyType)) {
			return standardMng.getTrainStd(meetType, meet_personNum, meet_num, configId);
		}
		return null;
	}
	/**
	 * ?????????????????????????????????????????????
	 */
	@RequestMapping("/outAtandard")
	@ResponseBody
	public boolean outAtandard(String vehicleLevel,Integer userId){
		
		return true;
	}
	/**
	 * ????????????????????????????????????
	 */
	@RequestMapping("/hotelAtandard")
	@ResponseBody
	public boolean hotelAtandard(String checkInTime,String checkOUTTime,String userId,String CityId){
		
		return true;
	}
	/**
	 * 
	 * @param applyType
	 * @param train_trainingType
	 * @param configId
	 * @param meetType
	 * @param meet_personNum
	 * @param meet_num
	 * @return
	 * @author ?????????
	 * @createtime 2020???4???23???
	 * @updator ?????????
	 * @updatetime 2020???4???23???
	 */
	@ResponseBody
	@RequestMapping("/getStandard")
	public List<Object[]> getStandard(String list){
		//??????????????????????????????
		try {
			List<TravelAppliInfo> lists = JSON.parseObject(list,new TypeReference<List<TravelAppliInfo>>(){});
			//????????????????????????
			List<TravelAppliInfo> listInfo = new ArrayList<TravelAppliInfo>();
			for (TravelAppliInfo travelAppliInfo : lists) {
				String[] peopId = travelAppliInfo.getTravelAttendPeopId().split(",");
				String[] peop = travelAppliInfo.getTravelAttendPeop().split(",");
				if(peopId.length>1){
					for (int i = 0; i < peopId.length; i++) {
						TravelAppliInfo appliInfo = new TravelAppliInfo();
						appliInfo.setTravelAttendPeopId(peopId[i]);
						//appliInfo.setTravelAttendPeop(new String(peop[i].getBytes("ISO-8859-1"),"utf-8"));
						appliInfo.setTravelAttendPeop(peop[i]);
						appliInfo.setTravelAreaId(travelAppliInfo.getTravelAreaId());
						appliInfo.setTravelDateStart(travelAppliInfo.getTravelDateStart());
						appliInfo.setTravelDateEnd(travelAppliInfo.getTravelDateEnd());
						listInfo.add(appliInfo);
					}
				}else{
					TravelAppliInfo appliInfo = new TravelAppliInfo();
					appliInfo.setTravelAttendPeopId(peopId[0]);
					//appliInfo.setTravelAttendPeop(new String(peop[0].getBytes("ISO-8859-1"),"utf-8"));
					appliInfo.setTravelAttendPeop(peop[0]);
					appliInfo.setTravelAreaId(travelAppliInfo.getTravelAreaId());
					appliInfo.setTravelDateStart(travelAppliInfo.getTravelDateStart());
					appliInfo.setTravelDateEnd(travelAppliInfo.getTravelDateEnd());
					listInfo.add(appliInfo);
				}
			}
			
				HashSet<String> hashSet  = new HashSet<String>();
				for (int i =0; i<listInfo.size(); i++){ //??????????????????????????? 
					hashSet.add(listInfo.get(i).getTravelAttendPeopId());
					}
				//????????????list
				List<Object[]> obj = new ArrayList<Object[]>(); 
				//??????hashSet?????????????????????
				for (String string : hashSet) {
					List<TravelAppliInfo> listInfoss = new ArrayList<TravelAppliInfo>();
					for (int i = 0; i < listInfo.size(); i++) {
						//????????????id??????????????????????????????
						if(listInfo.get(i).getTravelAttendPeopId().equals(string)){
							listInfoss.add(listInfo.get(i));
						}
					}
					int dayNum = 0;//?????????????????????
					Double foodNum = 0.00;//???????????????????????????
					Double cityNum = 0.00;//??????????????????????????????
					String[] arr = new String[5];
					//????????????????????????????????????
					for (int i = 0; i < listInfoss.size(); i++) {
						//?????????????????????
						int days =0;
						//?????????????????????
						if(i==listInfoss.size()-1){
							//147????????????
							if("147".equals(String.valueOf(listInfoss.get(i).getTravelAreaId()))||"148".equals(String.valueOf(listInfoss.get(i).getTravelAreaId()))){
								days = DateUtil.getDaySpan(listInfoss.get(i).getTravelDateStart(), listInfoss.get(i).getTravelDateEnd())+1;
							}else{
								days = DateUtil.getDaySpan(listInfoss.get(i).getTravelDateStart(), listInfoss.get(i).getTravelDateEnd());
							}
						}else{//???????????????
							days = DateUtil.getDaySpan(listInfoss.get(i).getTravelDateStart(), listInfoss.get(i).getTravelDateEnd());
						}
						dayNum =dayNum+days;
						//????????????ID????????????????????????????????????????????????
						HotelStandard config = (HotelStandard) standardMng.findConfigByClassAndId("HotelStandard",listInfoss.get(i).getTravelAreaId());			//????????????
						//??????????????????
						foodNum = foodNum + (config.getCostFood()*days);
						//??????????????????
						cityNum = cityNum+ (config.getCostTrafficShort()*days);
						arr[4]=String.valueOf(config.getCostFood());
					}
					arr[0]=listInfoss.get(0).getTravelAttendPeop();
					arr[1]=String.valueOf(dayNum);
					arr[2]=String.valueOf(cityNum);
					arr[3]=String.valueOf(foodNum);
					obj.add(arr);
				}
			return obj;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param applyType
	 * @param train_trainingType
	 * @param configId
	 * @param meetType
	 * @param meet_personNum
	 * @param meet_num
	 * @return
	 * @author ?????????
	 * @createtime 2020???4???23???
	 * @updator ?????????
	 * @updatetime 2020???4???23???
	 */
	@ResponseBody
	@RequestMapping("/getStandardAbroad")
	public List<Object[]> getStandardAbroad(String list){
		//??????????????????????????????
		try {
			List<AbroadPlan> lists = JSON.parseObject(list,new TypeReference<List<AbroadPlan>>(){});
			AbroadPlan abroadPlan = lists.get(lists.size()-1);
			HashSet<String> hashSet  = new HashSet<String>();
			for (int i =0; i<lists.size(); i++){ //??????????????????????????? 
				hashSet.add(String.valueOf(lists.get(i).getArriveCountryId()));
			}
			//????????????list
			List<Object[]> obj = new ArrayList<Object[]>(); 
			//??????hashSet?????????????????????
			for (String string : hashSet) {
				List<AbroadPlan> listInfoss = new ArrayList<AbroadPlan>();
				for (int i = 0; i < lists.size(); i++) {
					//????????????id??????????????????????????????
					if(String.valueOf(lists.get(i).getArriveCountryId()).equals(string)){
						listInfoss.add(lists.get(i));
					}
				}
				int dayNum = 0;//?????????????????????????????????
				int feeNum = 0;//?????????????????????????????????
				Double foodStu = 0.00;//??????????????????????????????
				Double feeStu = 0.00;//???????????????????????????
				String currency =""; //??????
				String[] arr = new String[6];
				//????????????????????????????????????
				for (int i = 0; i < listInfoss.size(); i++) {
					//?????????????????????
					int daysFood =0;
					int daysFee =0;
					if(listInfoss.get(i).getArriveCountryId().equals(abroadPlan.getArriveCountryId())){
						daysFood = DateUtil.getDaySpan(listInfoss.get(i).getAbroadDate(), listInfoss.get(i).getTimeEnd())+1;
						daysFee = DateUtil.getDaySpan(listInfoss.get(i).getAbroadDate(), listInfoss.get(i).getTimeEnd())+1;
					}else{
						daysFood = DateUtil.getDaySpan(listInfoss.get(i).getAbroadDate(), listInfoss.get(i).getTimeEnd());
						daysFee = DateUtil.getDaySpan(listInfoss.get(i).getAbroadDate(), listInfoss.get(i).getTimeEnd());
					}
					dayNum =dayNum+daysFood;
					feeNum =feeNum+daysFee;
					//????????????ID????????????????????????????????????????????????
					AboardCountryStandard config = (AboardCountryStandard) standardMng.findConfigByClassAndId("AboardCountryStandard",listInfoss.get(i).getArriveCountryId());			//????????????
					//???????????????
					foodStu = config.getFoodMoney();
					//???????????????
					feeStu = config.getTrafficMoney();
					//??????
					currency = config.getCurrency();
				}
				arr[0]=listInfoss.get(0).getArriveCountry()+"("+listInfoss.get(0).getArriveCity()+")";  //?????????????????????
				arr[1]=String.valueOf(dayNum);             //????????????
				arr[2]=String.valueOf(feeNum);             //???????????????
				arr[3]=String.valueOf(foodStu);            //??????????????????????????????
				arr[4]=String.valueOf(feeStu);             //???????????????????????????
				arr[5]=String.valueOf(currency);           //??????
				obj.add(arr);
			}
			return obj;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/getStandardTravel")
	public List<Object[]> getStandardTravel(String list){
		//??????????????????????????????
		try {
			List<TravelAppliInfo> lists = JSON.parseObject(list,new TypeReference<List<TravelAppliInfo>>(){});
			//????????????????????????
			List<TravelAppliInfo> listInfo = new ArrayList<TravelAppliInfo>();
			for (TravelAppliInfo travelAppliInfo : lists) {
				String[] peopId = travelAppliInfo.getTravelAttendPeopId().split(",");
				String[] peop = travelAppliInfo.getTravelAttendPeop().split(",");
				if(peopId.length>1){
					for (int i = 0; i < peopId.length; i++) {
						TravelAppliInfo appliInfo = new TravelAppliInfo();
						appliInfo.setTravelAttendPeopId(peopId[i]);
						appliInfo.setTravelAttendPeop(peop[i]);
						appliInfo.setTravelAreaId(travelAppliInfo.getTravelAreaId());
						appliInfo.setTravelDateStart(travelAppliInfo.getTravelDateStart());
						appliInfo.setTravelDateEnd(travelAppliInfo.getTravelDateEnd());
						listInfo.add(appliInfo);
					}
				}else{
					TravelAppliInfo appliInfo = new TravelAppliInfo();
					appliInfo.setTravelAttendPeopId(peopId[0]);
					appliInfo.setTravelAttendPeop(peop[0]);
					appliInfo.setTravelAreaId(travelAppliInfo.getTravelAreaId());
					appliInfo.setTravelDateStart(travelAppliInfo.getTravelDateStart());
					appliInfo.setTravelDateEnd(travelAppliInfo.getTravelDateEnd());
					listInfo.add(appliInfo);
				}
			}
			
				HashSet<String> hashSet  = new HashSet<String>();
				for (int i =0; i<listInfo.size(); i++){ //??????????????????????????? 
					hashSet.add(listInfo.get(i).getTravelAttendPeopId());
					}
				//????????????list
				List<Object[]> obj = new ArrayList<Object[]>(); 
				//??????hashSet?????????????????????
				for (String string : hashSet) {
					List<TravelAppliInfo> listInfoss = new ArrayList<TravelAppliInfo>();
					for (int i = 0; i < listInfo.size(); i++) {
						//????????????id??????????????????????????????
						if(listInfo.get(i).getTravelAttendPeopId().equals(string)){
							listInfoss.add(listInfo.get(i));
						}
					}
					int dayNum = 0;//?????????????????????
					Double foodNum = 0.00;//???????????????????????????
					Double cityNum = 0.00;//??????????????????????????????
					String[] arr = new String[4];
					//????????????????????????????????????
					for (int i = 0; i < listInfoss.size(); i++) {
						//?????????????????????
						int days =0;
						//?????????????????????
						if(i==listInfoss.size()-1){
							//147????????????
							if("147".equals(String.valueOf(listInfoss.get(i).getTravelAreaId()))||"148".equals(String.valueOf(listInfoss.get(i).getTravelAreaId()))){
								days = DateUtil.getDaySpan(listInfoss.get(i).getTravelDateStart(), listInfoss.get(i).getTravelDateEnd())+1;
							}else{
								days = DateUtil.getDaySpan(listInfoss.get(i).getTravelDateStart(), listInfoss.get(i).getTravelDateEnd());
							}
						}else{//???????????????
							days = DateUtil.getDaySpan(listInfoss.get(i).getTravelDateStart(), listInfoss.get(i).getTravelDateEnd());
						}
						dayNum =dayNum+days;
						//????????????ID????????????????????????????????????????????????
						HotelStandard config = (HotelStandard) standardMng.findConfigByClassAndId("HotelStandard",listInfoss.get(i).getTravelAreaId());			//????????????
						//??????????????????
						foodNum = foodNum + (config.getCostFood()*days);
						//??????????????????
						cityNum = cityNum+ (config.getCostTrafficShort()*days);
					}
					arr[0]=listInfoss.get(0).getTravelAttendPeop();
					arr[1]=String.valueOf(dayNum);
					arr[2]=String.valueOf(cityNum);
					arr[3]=String.valueOf(foodNum);
					obj.add(arr);
				}
			return obj;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
