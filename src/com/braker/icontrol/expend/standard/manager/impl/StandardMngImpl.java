package com.braker.icontrol.expend.standard.manager.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.chart.BeginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
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

@Service
public class StandardMngImpl extends BaseManagerImpl<Standard> implements StandardMng {

	@Autowired
	private HotelStandardMng hotelStandardMng;
	@Autowired
	private AboardStandardMng aboardStandardMng;
	@Autowired
	private MeetStandardMng meetStandardMng;
	@Autowired
	private RecepStandardMng recepStandardMng;
	@Autowired
	private TrainStandardMng trainStandardMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	
	@Override
	public Standard saveHotelStandard(Standard bean, User user) {

		if (StringUtil.isEmpty(bean.getId())) {
			bean.setId(shenTongMng.getSeq("T_HOTEL_STANDARD_SEQ").toString());
			bean.setFlag(1);
			bean.setCreatetime(new Date());
			bean.setCreator(user);
			return (Standard)saveOrUpdate(bean);
		} else {
			bean.setUpdator(user);
			bean.setUpdateTime(new Date());
		}
		return (Standard) updateDefault(bean);
	}

	@Override
	public Pagination pageListTravel(HotelStandard bean, User user, int pageNo, int pageSize) {
		
		//??????
		Finder finder = Finder.create(" from HotelStandard WHERE flag=1  ");
		if (!StringUtil.isEmpty(bean.getArea())) {
			finder.append(" AND area LIKE '%"+bean.getArea()+"%'");
		}
		//??????
		Pagination p = super.find(finder, pageNo, pageSize);
		List<HotelStandard> list = (List<HotelStandard>) p.getList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setPageOrder(pageNo * pageSize + i - 9);
			}
		}
		p.setList(list);
		
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public Pagination pageListMeet(MeetStandard bean, User user, int pageNo,
			int pageSize) {
		
		//??????
		Finder finder = Finder.create(" from MeetStandard WHERE flag=1  ");
		if (true) {
			finder.append("");
		}
		//??????
		Pagination p = super.find(finder, pageNo, pageSize);
		List<MeetStandard> list = (List<MeetStandard>) p.getList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setPageOrder(pageNo * pageSize + i - 9);
			}
		}
		p.setList(list);		
		
		return p;
	}

	@Override
	public Pagination pageListTrain(TrainStandard bean, User user, int pageNo,
			int pageSize) {
		//??????
		Finder finder = Finder.create(" from TrainStandard WHERE flag=1  ");
		if (true) {
			finder.append("");
		}
		//??????
		Pagination p = super.find(finder, pageNo, pageSize);
		List<TrainStandard> list = (List<TrainStandard>) p.getList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setPageOrder(pageNo * pageSize + i - 9);
			}
		}
		p.setList(list);		
		
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public Pagination pageListRecep(RecepStandard bean, User user, int pageNo,
			int pageSize) {
		//??????
		Finder finder = Finder.create(" from RecepStandard WHERE flag=1  ");
		if (true) {
			finder.append("");
		}
		//??????
		Pagination p = super.find(finder, pageNo, pageSize);
		List<RecepStandard> list = (List<RecepStandard>) p.getList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setPageOrder(pageNo * pageSize + i - 9);
			}
		}
		p.setList(list);		
		
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public Pagination pageListAboard(AboardStandard bean, User user,
			int pageNo, int pageSize) {
		//??????
		Finder finder = Finder.create(" from AboardStandard WHERE flag=1  ");
		if (true) {
			finder.append("");
		}
		//??????
		Pagination p = super.find(finder, pageNo, pageSize);
		List<AboardStandard> list = (List<AboardStandard>) p.getList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setPageOrder(pageNo * pageSize + i - 9);
			}
		}
		p.setList(list);		
		
		return super.find(finder, pageNo, pageSize);
	}


	@Override
	public HotelStandard deleteStandard(Standard bean, User user) {

		if (bean == null) return null;
		bean.setFlag(0);
		bean.setUpdator(user);
		bean.setUpdateTime(new Date());
		return (HotelStandard) saveOrUpdate(bean);
	}

	@Override
	public List<Object> calcCostTravel(Integer configId, Date travelDates,
			Date travelDatee) {

		int workDaysNum = DateUtil.getDaySpan(travelDates, travelDatee) + 1;//????????????
		HotelStandard config = (HotelStandard) findConfigByClassAndId("HotelStandard",configId);			//????????????
		List<Object> resList = new ArrayList<Object>();
		
		if (travelDates == null || travelDatee == null || configId == null) return null;
		
		
		//?????????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("?????????");
		if (config.getHasBusy() == 1) {
			double cost = calcHotelCost(configId, travelDates, travelDatee);
			bean.setStandard(String.valueOf(cost));
		} else {
			double price = config.getNormalPriceMin();
			double cost = price * workDaysNum;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//???????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		if (config.getCostFood() != null) {
			double price = config.getCostFood();
			double cost = price * workDaysNum;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//???????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		if (config.getCostTrafficLong() != null) {
			double price = config.getCostFood();
			double cost = price * workDaysNum;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//???????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		if (config.getCostTrafficShort() != null) {
			double price = config.getCostTrafficShort();
			double cost = price * workDaysNum;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//??????
		bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		if (config.getCostExtras() != null) {
			double price = config.getCostExtras();
			double cost = price * workDaysNum;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		
		return resList;
	}
	
	@Override
	public List<Object> calcCostTravel1(Integer configId, String travelDays,
			String hotelDays,String userId,String day1,String day2)  throws ParseException {

		HotelStandard config = (HotelStandard) findConfigByClassAndId("HotelStandard",configId);			//????????????
		List<Object> resList = new ArrayList<Object>();
		if (travelDays == null || hotelDays == null || configId == null) return null;
		
		int normalday = 0;//????????????
		int busyday = 0;//????????????
		if(!StringUtil.isEmpty(day1) || !StringUtil.isEmpty(day2)){
			if(config.getHasBusy()==0){//???????????????????????????????????????  0????????????  1?????????
				long date1 = new Long(day1);
				long date2 = new Long(day2);
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c1.setTimeInMillis(date1);
				c2.setTimeInMillis(date2);
				Date time1 = c1.getTime();//??????????????????
		        Date time2 = c2.getTime();//??????????????????
		        normalday = DateUtil.getDaySpan(time1, time2);//??????????????????????????????
			}else{
				if(config.getHasBusy()==1){
					long date1 = new Long(day1);
					long date2 = new Long(day2);
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					c1.setTimeInMillis(date1);
					c2.setTimeInMillis(date2);
					Date time1 = c1.getTime();//??????????????????
			        Date time2 = c2.getTime();//??????????????????
					Boolean bstart =  DateUtil.isEffectiveDate(time1, config.getBusyDateStart(), config.getBusyDateEnd());//?????????????????????????????????
					Boolean bend =  DateUtil.isEffectiveDate(time2, config.getBusyDateStart(), config.getBusyDateEnd());//?????????????????????????????????
					if(bstart && bend){//??????????????????????????????????????????
						busyday = DateUtil.getDaySpan(time1, time2);
					}else if(bstart && !bend){//??????????????????????????????????????????????????????
						busyday = DateUtil.getDaySpan(time1, config.getBusyDateEnd())+1;//????????????????????????????????????
						normalday = DateUtil.getDaySpan(config.getBusyDateEnd(), time2);//????????????????????????????????????
					}else if(!bstart && bend){//??????????????????????????????????????????????????????
						busyday = DateUtil.getDaySpan(config.getBusyDateStart(), time2)+1;//????????????????????????????????????
						normalday = DateUtil.getDaySpan(time1, config.getBusyDateStart());//????????????????????????????????????
					}else if(!bstart && !bend){//?????????????????????????????????????????????????????????
						normalday = DateUtil.getDaySpan(time1, time2);
					}
				}
			}
		}
		//?????????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("?????????");
		String[] userIds = userId.split(",");
		//????????????????????????????????????
		Double costLevel = 0.00;
		for (int i = 0; i < userIds.length; i++) {
			User user = userMng.findById(userIds[i]);
			if (config.getHasBusy() ==1) {//1????????????
				if(1==user.getRoleslevel()){
					costLevel =costLevel+( config.getBusyPriceMax()*busyday+normalday*config.getNormalPriceMax());
				}
				if(2==user.getRoleslevel()){
					costLevel = costLevel+( config.getBusyPriceMid()*busyday+normalday*config.getNormalPriceMid());
				}
				if(3==user.getRoleslevel()){
					costLevel = costLevel+( config.getBusyPriceMin()*busyday+normalday*config.getNormalPriceMin());
				}
			}else{
				if (config.getHasBusy() ==0) {//0????????????
					if(1==user.getRoleslevel()){
						costLevel =costLevel+(  normalday*config.getNormalPriceMax());
					}
					if(2==user.getRoleslevel()){
						costLevel = costLevel+( normalday*config.getNormalPriceMid());
					}
					if(3==user.getRoleslevel()){
						costLevel = costLevel+( normalday*config.getNormalPriceMin());
					}
				}
			}
		}
		bean.setStandard(String.valueOf(costLevel));
		resList.add(bean);
		//???????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		if (config.getCostFood() != null) {
			double price = config.getCostFood();
			double cost = price * Double.valueOf(travelDays);
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//???????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		if (config.getCostFood() != null) {
			double price = config.getCostFood();
			double cost = price * Double.valueOf(travelDays);
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//???????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		if (config.getCostFood() != null) {
			double price = config.getCostTrafficShort();
			double cost = price * Double.valueOf(travelDays);
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		
		return resList;
	}
	
	@Override
	public List<Object> getEmptyTravelDetail() {

		List<Object> resList = new ArrayList<>();
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("?????????");
		resList.add(bean);
		
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		resList.add(bean);
		
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		resList.add(bean);
		
		bean = new ApplicationDetail();
		bean.setCostDetail("???????????????");
		resList.add(bean);
		
		bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		resList.add(bean);
		
		return resList;
	}

	@Override
	public Double calcHotelCost(Integer configId, Date travelDates,
			Date travelDatee) {

		//????????????
		if (configId == null || travelDates == null || travelDatee == null) {
			return 0.0;
		}
		//???????????????????????????????????????0??????????????????0
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(travelDates);
		cal2.setTime(travelDatee);
		if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
			return 0.0;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		try {
			
			//????????????????????????
			HotelStandard bean = (HotelStandard) hotelStandardMng.findById(configId);
			Double price = bean.getNormalPriceMin();
			Double priceHigh = bean.getBusyPriceMin();
			//?????????????????????
			cal1.setTime(bean.getBusyDateStart());
			cal2.setTime(bean.getBusyDateEnd());
			cal1.set(Calendar.YEAR, Integer.valueOf(DateUtil.getCurrentYear()));
			cal2.set(Calendar.YEAR, Integer.valueOf(DateUtil.getCurrentYear()));
			/*//???????????? = ???????????? - 1
			Calendar calTemp = Calendar.getInstance();
			calTemp.setTime(travelDatee);
			calTemp.add(Calendar.DAY_OF_YEAR, -1);
			travelDatee = calTemp.getTime();*/
			// ???????????????????????????????????????????????????????????????
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			if (cal1.before(cal2)) {
				//?????????
				int[] array = DateUtil.calcRepeatDate(travelDates, travelDatee, cal1.getTime(), cal2.getTime());
				Double totalCost = array[0] * price + array[1] * priceHigh;
				return totalCost;
			} else {
				//?????? ?????????????????????????????????
				String currentYear = DateUtil.getCurrentYear();//2019-12-1  2019-01-01
				cal1.set(Calendar.YEAR, Integer.valueOf(currentYear) - 1);	//2018-12-1
				cal2.set(Calendar.YEAR, Integer.valueOf(currentYear));		//2019-01-01
				int[] array1 = DateUtil.calcRepeatDate(travelDates, travelDatee, cal1.getTime(), cal2.getTime());
				
				cal1.add(Calendar.YEAR, 1);//2019-12-01  
				cal2.add(Calendar.YEAR, 1);//2020-01-01
				int[] array2 = DateUtil.calcRepeatDate(travelDates, travelDatee, cal1.getTime(), cal2.getTime());
				
				Double totalCost = array1[0] * price + array1[1] * priceHigh + array2[0] * price + array2[1] * priceHigh;
				return totalCost;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	
	}

	/*@Override
	public List<Object> calcCostRecep(Integer configId, Integer recep_personNum, Integer recep_num) {

		List<Object> resList = new ArrayList<>();
		//??????????????????
		RecepStandard config = null;
		if (configId == null) {
			config = (RecepStandard) getDefaultConfig("RecepStandard");
		} else {
			config = (RecepStandard) recepStandardMng.findById(configId);
		}
		//??????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		if (config.getCostFood1() != null) {
			double price = config.getCostFood1();
			double cost = price * recep_personNum * recep_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//??????
		bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		if (config.getCostFood1() != null) {
			double price = config.getCostFood2();
			double cost = price * recep_personNum * recep_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//??????
		bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		if (config.getCostFood1() != null) {
			double price = config.getCostFood3();
			double cost = price * recep_personNum * recep_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		return resList;
	}*/
	@Override
	public List<Object> calcCostRecep(Integer configId, Integer recep_personNum, Integer recep_num) {

		List<Object> resList = new ArrayList<>();
		//??????????????????
		RecepStandard config = null;
		if (configId == null) {
			config = (RecepStandard) getDefaultConfig("RecepStandard");
		} else {
			config = (RecepStandard) recepStandardMng.findById(configId);
		}
		//??????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		if (config.getCostFood1() != null) {
			double price = config.getCostFood1();
			bean.setStandard(String.valueOf(price));
		}
		resList.add(bean);
		//??????
		bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		if (config.getCostFood1() != null) {
			double price = config.getCostFood2();
			bean.setStandard(String.valueOf(price));
		}
		resList.add(bean);
		//??????
		bean = new ApplicationDetail();
		bean.setCostDetail("??????");
		if (config.getCostFood1() != null) {
			double price = config.getCostFood3();
			bean.setStandard(String.valueOf(price));
		}
		resList.add(bean);
		return resList;
	}

	
	
	
	
	@Override
	public List<Object> calcCostMeet(Integer configId, Integer meet_personNum, Integer meet_num) {

		List<Object> resList = new ArrayList<>();
		//??????????????????
		MeetStandard config = null;
		if (configId == null) {
			config = (MeetStandard) getDefaultConfig("MeetStandard");
		} else {
			config = (MeetStandard) meetStandardMng.findById(configId);
		}
		//?????????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("?????????");
		if (config.getCostHotel() != null) {
			double price = config.getCostHotel();
			double cost = price * meet_personNum * meet_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//?????????
		bean = new ApplicationDetail();
		bean.setCostDetail("?????????");
		if (config.getCostFood() != null) {
			double price = config.getCostFood();
			double cost = price * meet_personNum * meet_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("????????????");
		if (config.getCostOther() != null) {
			double price = config.getCostOther();
			double cost = price * meet_personNum * meet_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		return resList;
	}

	@Override
	public List<Object> calcCostTrain(Integer configId, String train_teachLevel, Integer train_personNum, Integer train_num) {
		
		List<Object> resList = new ArrayList<>();
		//??????????????????
		TrainStandard config = null;
		if (configId == null) {
			config = (TrainStandard) getDefaultConfig("TrainStandard");
		} else {
			config = (TrainStandard) trainStandardMng.findById(configId);
		}
		//?????????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("?????????");
		if (config.getCostHotel() != null) {
			double price = config.getCostHotel();
			double cost = price * train_personNum * train_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//?????????
		bean = new ApplicationDetail();
		bean.setCostDetail("?????????");
		if (config.getCostFood() != null) {
			double price = config.getCostFood();
			double cost = price * train_personNum * train_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//??????????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("??????????????????");
		if (config.getCostBook() != null) {
			double price = config.getCostBook();
			double cost = price * train_personNum * train_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//????????????
		bean = new ApplicationDetail();
		bean.setCostDetail("????????????");
		if (config.getCostOther() != null) {
			double price = config.getCostOther();
			double cost = price * train_personNum * train_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//?????????
		bean = new ApplicationDetail();
		bean.setCostDetail("?????????");//?????????8???????????????
		double price = 0;
		if (config.getCostOther() != null) {
			if ("1".equals(train_teachLevel)) {
				price = config.getCostTeach1();
			} else if ("2".equals(train_teachLevel)) {
				price = config.getCostTeach2();
			} else if ("3".equals(train_teachLevel)) {
				price = config.getCostTeach3();
			}
			double cost = price * train_personNum * train_num * 8;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		return resList;
	}

	@Override
	public List<Object> calcCostAboard(Integer configId, Integer aboard_num) {

		List<Object> resList = new ArrayList<>();
		//??????????????????
		AboardStandard config = null;
		if (configId == null) {
			config = (AboardStandard) getDefaultConfig("AboardStandard");
		} else {
			config = (AboardStandard) aboardStandardMng.findById(configId);
		}
		//??????????????????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("??????????????????");
		if (config.getCostTraffic() != null) {
			double price = config.getCostTraffic();
			double cost = price * aboard_num;
			bean.setStandard(String.valueOf(cost));
		}
		resList.add(bean);
		//????????????10?????????
		bean = new ApplicationDetail();
		bean.setCostDetail("????????????10?????????");
		if (aboard_num <= 10) {
			double price = config.getCostPocket1();
			double cost = price * aboard_num;
			bean.setStandard(String.valueOf(cost));
			resList.add(bean);
		} else {
			double price = config.getCostPocket1();
			double cost = price * 10;
			bean.setStandard(String.valueOf(cost));
			resList.add(bean);
		}
		//????????????10????????????
		if (aboard_num > 10) {
			bean = new ApplicationDetail();
			bean.setCostDetail("????????????10?????????");
			aboard_num = aboard_num - 10;
			double price = config.getCostPocket2();
			double cost = price * aboard_num;
			bean.setStandard(String.valueOf(cost));
			resList.add(bean);
		}
		return resList;
	}

	@Override
	public Standard findConfigByClassAndId(String className, Integer configId) {

		if (!StringUtil.isEmpty(className) && configId != null) {
			Finder finder = Finder.create(" from " + className );
			finder.append(" where id=" + configId);
			List<Object> list = super.find(finder);
			if (list != null && list.size() > 0) {
				return (Standard) list.get(0);
			}
		}
		return null;
	}

	@Override
	public Standard getDefaultConfig(String className) {
		
		if (!StringUtil.isEmpty(className)) {
			Finder finder = Finder.create(" from " + className );
			List<Object> list = super.find(finder);
			if (list != null && list.size() > 0) {
				return (Standard) list.get(0);
			}
		}
		return null;
	}

	@Override
	public List<Object> calcCostCar(Integer configId, Integer car_num) {
		List<Object> resList = new ArrayList<>();
		/*//??????????????????
		RecepStandard config = null;
		if (configId == null) {
			config = (RecepStandard) getDefaultConfig("CarStandard");
		} else {
			config = (RecepStandard) findById(configId);
		}*/
		//????????????
		ApplicationDetail bean = new ApplicationDetail();
		bean.setCostDetail("??????????????????");
		bean.setStandard("????????????");
		resList.add(bean);
		return resList;
	}

	@Override
	public Map<String,Double> getMeetStd(String meetType,Integer meet_personNum, Integer meet_num,Integer configId) {
		Map<String,Double> map = new HashMap<>();
		map.put("hotel", 0.0d);
		map.put("food", 0.0d);
		map.put("other", 0.0d);
		//??????????????????
		MeetStandard config = null;
		if (StringUtil.isEmpty(meetType)) {
			config = (MeetStandard) getDefaultConfig("MeetStandard");
		} else {
			config = (MeetStandard) meetStandardMng.findbyMeetType(Integer.valueOf(meetType));
		}
		//?????????
		if (config.getCostHotel() != null) {
			double price = config.getCostHotel();
			double cost = price ;
			map.put("hotel", cost);
		}
		if (config.getCostFood() != null) {
			double price = config.getCostFood();
			double cost = price ;
			map.put("food", cost);
		}
		if (config.getCostOther() != null) {
			double price = config.getCostOther();
			double cost = price ;
			map.put("other", cost);
		}
		
		
		/*Map<String,Double> map = new HashMap<>();
		map.put("hotel", 0.0d);
		map.put("food", 0.0d);
		map.put("other", 0.0d);
		if ("1".equals(meetType)) {
			map.put("hotel", 400d);
			map.put("food", 140d);
			map.put("other", 110d);
		} else if ("2".equals(meetType)) {
			map.put("hotel", 300d);
			map.put("food", 140d);
			map.put("other", 110d);
		} else if ("3".equals(meetType)) {
			map.put("hotel", 280d);
			map.put("food", 130d);
			map.put("other", 90d);
		}*/
		return map;
	}

	@Override
	public Map<String,Double> getTrainStd(String trainType, Integer train_personNum, Integer train_num,Integer configId) {
		Map<String,Double> map = new HashMap<>();
		
		//??????????????????
		TrainStandard config = null;
		if (StringUtil.isEmpty(trainType)) {
			config = (TrainStandard) getDefaultConfig("TrainStandard");
		} else {
			config = (TrainStandard) trainStandardMng.findbyTrainType(Integer.valueOf(trainType));
		}
		//?????????
		if (config.getCostHotel() != null) {
			Double price = config.getCostHotel();
			map.put("hotel", price);
		}
		//?????????
		if (config.getCostFood() != null) {
			Double price = config.getCostFood();
			map.put("food", price);
		}
		//??????????????????
		if (config.getCostBook() != null) {
			Double price = config.getCostBook();
			map.put("zonghe", price);
		}
		//????????????
		if (config.getCostOther() != null) {
			Double price = config.getCostOther();
			map.put("other", price);
		}
		//?????????
		Double price = 0.00;
		if (config.getCostOther() != null) {
			price = config.getCostTeach1();
			map.put("lesson1", price);
			price = config.getCostTeach2();
			map.put("lesson2", price);
			price = config.getCostTeach3();
			map.put("lesson3", price);
		}
		return map;
	}
}
