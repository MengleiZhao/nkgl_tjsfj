package com.braker.icontrol.expend.apply.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.braker.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 事前申请、事后报销   住宿费表
 * @author 赵孟雷
 *
 */
@Entity
@Table(name ="T_HOTEL_EXPENSE_INFO")
public class HotelExpenseInfo extends BaseEntity {

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_H_ID")
	private Integer fHId;           //主键ID
	
	@Column(name = "F_G_ID")
	private Integer gId;			//ApplicationBasicInfo 的主键ID
	
	@Column(name = "F_R_ID")
	private Integer rId;			//ReimbAppliBasicInfo的主键ID

	@Column(name = "F_TRAVAL_TYPE")
	private String travelType;			//出差类型
	
	@Column(name = "F_CHECK_IN_TIME")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkInTime;			//入住时间
	
	@Column(name = "F_CHECK_OUT_TIME")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkOUTTime;			//退房时间

	@Column(name = "F_HOTEL_DAY")  //住宿天数
	private Integer	hotelDay;
	
	@Column(name = "F_LOCATION_CITY")  //所在城市
	private String locationCity;
	
	@Column(name = "F_CITY_ID")  //所在城市ID HotelStandard的id
	private String CityId;
	
	@Column(name = "TRAVEL_PERSONNEL")  //住宿人员
	private String travelPersonnel;
	
	@Column(name = "TRAVEL_PERSONNEL_ID")  //住宿人员ID
	private String travelPersonnelId;
	
	@Column(name = "F_APPLY_AMOUNT")
	private Double applyAmount;			//申请金额（元）
	
	@Column(name = "F_REIMB_AMOUNT")
	private Double reimbAmount;			//报销金额（元）
	
	@Column(name = "F_STANDARD")
	private Double standard;//住宿标准
	
	@Column(name = "F_COUNT_STANDARD")
	private Double countStandard;//总额标准（外币）
	
	@Column(name = "F_CURRENCY")
	private String currency;//币种
	
	@Column(name = "F_STATUS")
	private String sts;			//状态       数据状态0-事前申请，1-事后报销

	@Transient
	private Integer num;         //数量
	
	
	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public Integer getHotelDay() {
		return hotelDay;
	}

	public void setHotelDay(Integer hotelDay) {
		this.hotelDay = hotelDay;
	}

	public Double getStandard() {
		return standard;
	}

	public void setStandard(Double standard) {
		this.standard = standard;
	}

	public Double getCountStandard() {
		return countStandard;
	}

	public void setCountStandard(Double countStandard) {
		this.countStandard = countStandard;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTravelPersonnelId() {
		return travelPersonnelId;
	}

	public void setTravelPersonnelId(String travelPersonnelId) {
		this.travelPersonnelId = travelPersonnelId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getfHId() {
		return fHId;
	}

	public void setfHId(Integer fHId) {
		this.fHId = fHId;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOUTTime() {
		return checkOUTTime;
	}

	public void setCheckOUTTime(Date checkOUTTime) {
		this.checkOUTTime = checkOUTTime;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getTravelPersonnel() {
		return travelPersonnel;
	}

	public void setTravelPersonnel(String travelPersonnel) {
		this.travelPersonnel = travelPersonnel;
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Double getReimbAmount() {
		return reimbAmount;
	}

	public void setReimbAmount(Double reimbAmount) {
		this.reimbAmount = reimbAmount;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Integer getgId() {
		return gId;
	}

	public void setgId(Integer gId) {
		this.gId = gId;
	}

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public String getCityId() {
		return CityId;
	}

	public void setCityId(String cityId) {
		CityId = cityId;
	}

	
}
