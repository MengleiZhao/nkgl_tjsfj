package com.braker.icontrol.expend.apply.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;
import com.braker.icontrol.expend.standard.entity.HotelStandard;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 差旅费申请信息model
 * 是差旅费申请信息的model类
 * @author 叶崇晖
 * @createtime 2018-06-13
 * @updatetime 2018-06-13
 */
@Entity
@Table(name = "T_TRAVEL_APPLI_INFO")
public class TravelAppliInfo extends BaseEntity implements EntityDao{
	@Id
	@Column(name = "F_TR_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer trId;				//主键ID
	
	@Column(name = "F_G_ID")
	private Integer gId;				//基本信息ID
	
	@Column(name = "F_R_ID")
	private Integer rId;			//ReimbAppliBasicInfo的主键ID
	
	@Column(name = "F_TRAVEL_AREA_TIME")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date travelAreaTime;		//出行时间
	
	@Column(name = "F_TRAVEL_DATE_START")
	private Date travelDateStart;		//出差日期（开始）
	
	@Column(name = "F_TRAVEL_DATE_END")
	private Date travelDateEnd;			//出差日期（结束）
	
	@Column(name = "F_TRAVEL_DAY_NUM")
	private String travelDayNum;		//出差天数
	
	@Column(name = "F_HOTEL_DAY_NUM")
	private String hotelDayNum;			//住宿天数
	
	@Column(name = "F_ATTEND_PEOPLE")
	private String travelAttendPeop;	//出差人员
	
	@Column(name = "F_ATTEND_PEOPLE_ID")
	private String travelAttendPeopId;	//出差人员ID
	
	@Column(name = "F_TRAVAL_TYPE")
	private String travelType;			//出差类型
	
	@Column(name = "F_TRAVEL_PLACE_START")
	private String placeStart;			//出差地点（出发）
	
	@Column(name = "F_TRAVEL_PLACE_END")
	private String placeEnd;			//出差地点（到达）
	
	@Column(name = "F_VEHICLE")
	private String vehicle;				//交通工具
	
	@Column(name = "F_VEHICLE_LEVEL")
	private String vehicleLevel;		//交通工具等级
	
	@Column(name = "F_VEHICLE_OTHER")
	private String vehicleOther;		//其他交通工具
	
	@Column(name = "F_BOARD_WAGES_PLAN")
	private String wagesPlan;			//伙食费用安排
	
	@Column(name = "F_HOTEL_EXPENSE_PLAN")
	private String expensePlan;			//住宿费用安排
	
	@Column(name = "F_TRAFFIC_DAY_NUM")
	private String traDayNum;			//交通补助天数
	
	@Column(name = "F_FOOD_DAY_NUM")
	private String foodDayNum;			//伙食补助天数
	
	@Column(name = "F_HOTEL_AMOUNT")
	private Double hotelAmount;			//住宿费
	
	@Column(name = "F_FOOD_AMOUNT")
	private Double foodAmount;			//伙食补助费
	
	@Column(name = "F_LONGTRAVEL_AMOUNT")
	private Double loongTavelAmount;			//长途交通费
	
	@Column(name = "F_CITYTRAVEL_AMOUNT")
	private Double cityTavelAmount;			//市内交通费InCityTrafficInfo.java
	
	@Column(name = "F_OTHER_AMOUNT")
	private Double otherAmount;			//其他费用
	
	@Column(name = "F_REASON")
	private String reason;			//差旅申请事由
	
	@Column(name = "F_AREA_ID")
	private String travelArea;	//出差地区
	
	@Column(name = "F_AREA_NAME")
	private String travelAreaName;	//出差地区名称
	
	@Column(name = "F_AREA_CODE")
	private String areaCode;	//出行区域Code
	
	@Column(name = "F_AREA_NAMES")
	private String areaNames;	//出行区域名称
	
	@Column(name = "F_DRIVE_WAY")
	private String fDriveWay;	//乘车方式
	
	@Column(name = "F_DRIVE_WAY_CODE")
	private String fDriveWayCode;	//乘车方式code

	@Column(name = "F_STATUS")
	private Integer fStatus;//数据状态0-事前申请，1-事后报销

	@Column(name = "TRAVEL_PERSONNEL_LEVEL")  //出行人员级别
	private String travelPersonnelLevel;
	
	@Transient
	private Integer num;				//序号(数据库中没有)
	
	@Transient
	private Integer travelAreaId;				//出差地区ID

	@Column(name = "MEETING_SUMMARY_YEAR1")
	private String meetingSummaryYear1;   //校长办公会会议纪要年数
	
	@Column(name = "MEETING_SUMMARY_TIME1")
	private String meetingSummaryTime1; //校长办公会会议纪要次数
	
	@Column(name = "MEETING_SUMMARY_YEAR2")
	private String meetingSummaryYear2;   //党委会会议纪要年数
	
	@Column(name = "MEETING_SUMMARY_TIME2")
	private String meetingSummaryTime2;  //党委会会议纪要次数
	
	
	
	public String getMeetingSummaryYear1() {
		return meetingSummaryYear1;
	}

	public void setMeetingSummaryYear1(String meetingSummaryYear1) {
		this.meetingSummaryYear1 = meetingSummaryYear1;
	}

	public String getMeetingSummaryTime1() {
		return meetingSummaryTime1;
	}

	public void setMeetingSummaryTime1(String meetingSummaryTime1) {
		this.meetingSummaryTime1 = meetingSummaryTime1;
	}

	public String getMeetingSummaryYear2() {
		return meetingSummaryYear2;
	}

	public void setMeetingSummaryYear2(String meetingSummaryYear2) {
		this.meetingSummaryYear2 = meetingSummaryYear2;
	}

	public String getMeetingSummaryTime2() {
		return meetingSummaryTime2;
	}

	public void setMeetingSummaryTime2(String meetingSummaryTime2) {
		this.meetingSummaryTime2 = meetingSummaryTime2;
	}

	public String getTravelPersonnelLevel() {
		return travelPersonnelLevel;
	}

	public void setTravelPersonnelLevel(String travelPersonnelLevel) {
		this.travelPersonnelLevel = travelPersonnelLevel;
	}

	public String getTravelArea() {
		return travelArea;
	}

	public void setTravelArea(String travelArea) {
		this.travelArea = travelArea;
	}

	public Date getTravelAreaTime() {
		return travelAreaTime;
	}

	public void setTravelAreaTime(Date travelAreaTime) {
		this.travelAreaTime = travelAreaTime;
	}

	public String getfDriveWay() {
		return fDriveWay;
	}

	public void setfDriveWay(String fDriveWay) {
		this.fDriveWay = fDriveWay;
	}

	public String getfDriveWayCode() {
		return fDriveWayCode;
	}

	public void setfDriveWayCode(String fDriveWayCode) {
		this.fDriveWayCode = fDriveWayCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaNames() {
		return areaNames;
	}

	public void setAreaNames(String areaNames) {
		this.areaNames = areaNames;
	}

	public String getTravelAreaName() {
		return travelAreaName;
	}

	public void setTravelAreaName(String travelAreaName) {
		this.travelAreaName = travelAreaName;
	}

	public Integer getfStatus() {
		return fStatus;
	}

	public void setfStatus(Integer fStatus) {
		this.fStatus = fStatus;
	}

	public Integer getTravelAreaId() {
		return travelAreaId;
	}

	public void setTravelAreaId(Integer travelAreaId) {
		this.travelAreaId = travelAreaId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getTrId() {
		return trId;
	}

	public void setTrId(Integer trId) {
		this.trId = trId;
	}

	public Integer getgId() {
		return gId;
	}

	public void setgId(Integer gId) {
		this.gId = gId;
	}

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getTravelDateStart() {
		return travelDateStart;
	}

	public void setTravelDateStart(Date travelDateStart) {
		this.travelDateStart = travelDateStart;
	}

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getTravelDateEnd() {
		return travelDateEnd;
	}

	public void setTravelDateEnd(Date travelDateEnd) {
		this.travelDateEnd = travelDateEnd;
	}

	public String getTravelDayNum() {
		return travelDayNum;
	}

	public void setTravelDayNum(String travelDayNum) {
		this.travelDayNum = travelDayNum;
	}

	public String getTravelAttendPeop() {
		return travelAttendPeop;
	}

	public void setTravelAttendPeop(String travelAttendPeop) {
		this.travelAttendPeop = travelAttendPeop;
	}

	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public String getPlaceStart() {
		return placeStart;
	}

	public void setPlaceStart(String placeStart) {
		this.placeStart = placeStart;
	}

	public String getPlaceEnd() {
		return placeEnd;
	}

	public void setPlaceEnd(String placeEnd) {
		this.placeEnd = placeEnd;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getVehicleLevel() {
		return vehicleLevel;
	}

	public void setVehicleLevel(String vehicleLevel) {
		this.vehicleLevel = vehicleLevel;
	}

	public String getWagesPlan() {
		return wagesPlan;
	}

	public void setWagesPlan(String wagesPlan) {
		this.wagesPlan = wagesPlan;
	}

	public String getExpensePlan() {
		return expensePlan;
	}

	public void setExpensePlan(String expensePlan) {
		this.expensePlan = expensePlan;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getHotelDayNum() {
		return hotelDayNum;
	}

	public void setHotelDayNum(String hotelDayNum) {
		this.hotelDayNum = hotelDayNum;
	}

	public String getTraDayNum() {
		return traDayNum;
	}

	public void setTraDayNum(String traDayNum) {
		this.traDayNum = traDayNum;
	}

	public String getFoodDayNum() {
		return foodDayNum;
	}

	public void setFoodDayNum(String foodDayNum) {
		this.foodDayNum = foodDayNum;
	}

	public String getVehicleOther() {
		return vehicleOther;
	}

	public void setVehicleOther(String vehicleOther) {
		this.vehicleOther = vehicleOther;
	}

	public Double getHotelAmount() {
		return hotelAmount;
	}

	public void setHotelAmount(Double hotelAmount) {
		this.hotelAmount = hotelAmount;
	}

	public Double getFoodAmount() {
		return foodAmount;
	}

	public void setFoodAmount(Double foodAmount) {
		this.foodAmount = foodAmount;
	}

	public Double getLoongTavelAmount() {
		return loongTavelAmount;
	}

	public void setLoongTavelAmount(Double loongTavelAmount) {
		this.loongTavelAmount = loongTavelAmount;
	}

	public Double getCityTavelAmount() {
		return cityTavelAmount;
	}

	public void setCityTavelAmount(Double cityTavelAmount) {
		this.cityTavelAmount = cityTavelAmount;
	}

	public Double getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}

	public String getTravelAttendPeopId() {
		return travelAttendPeopId;
	}

	public void setTravelAttendPeopId(String travelAttendPeopId) {
		this.travelAttendPeopId = travelAttendPeopId;
	}

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	@Override
	public String getJoinTable() {
		
		return "T_TRAVEL_APPLI_INFO";
	}

	@Override
	public String getEntryId() {
		
		return String.valueOf(getTrId());
	}
	
	
}
