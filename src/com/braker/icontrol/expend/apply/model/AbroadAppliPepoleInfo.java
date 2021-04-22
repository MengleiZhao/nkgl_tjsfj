package com.braker.icontrol.expend.apply.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.braker.common.entity.BaseEntity;

/**
 * 申请出国人员信息表
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_ABROAD_APPLI_PEOPLE_INFO")
public class AbroadAppliPepoleInfo extends BaseEntity{

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_R_ID")
	private Integer frId;
	
	@Column(name ="F_A_ID")
	private Integer aId;  //出国信息ID
	
	@Column(name = "F_TRAVEL_PEOPLE_NAME")
	private String travelPeopName;	// 差旅人员姓名
	
	@Column(name = "F_POSITION")
	private String position;			//职务
	
	@Column(name = "F_ID_CARD")
	private String idCard;			//护照号
	
	@Column(name = "F_PHONENUM")
	private String phoneNum;			//联系方式
	
	@Column(name ="F_TRAVEL_DAY_NUM")
	private String fTravelDayNum;//出差天数
	
	@Column(name ="F_HOTEL_DAY_NUM")
	private String fHotelDayNum;//住宿天数
	
	@Column(name ="F_TRAFFIC_DAY_NUM")
	private String fTrafficDayNum;//交通补助天数
	
	@Column(name ="F_FOOD_DAY_NUM")
	private String fFoodDayNum;//伙食补助
	
	@Column(name ="F_REMARK")
	private String fRemark;//备注
	
	@Column(name ="F_EXT_1")
	private String fExt1;
	
	@Column(name ="F_EXT_2")
	private String fExt2;
	
	@Column(name ="F_EXT_3")
	private String fExt3;
	
	@Column(name ="F_EXT_4")
	private String fExt4;

	@Column(name ="F_EXT_5")
	private String fExt5;

	public Integer getFrId() {
		return frId;
	}

	public void setFrId(Integer frId) {
		this.frId = frId;
	}

	public Integer getaId() {
		return aId;
	}

	public void setaId(Integer aId) {
		this.aId = aId;
	}

	public String getTravelPeopName() {
		return travelPeopName;
	}

	public void setTravelPeopName(String travelPeopName) {
		this.travelPeopName = travelPeopName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getfTravelDayNum() {
		return fTravelDayNum;
	}

	public void setfTravelDayNum(String fTravelDayNum) {
		this.fTravelDayNum = fTravelDayNum;
	}

	public String getfHotelDayNum() {
		return fHotelDayNum;
	}

	public void setfHotelDayNum(String fHotelDayNum) {
		this.fHotelDayNum = fHotelDayNum;
	}

	public String getfTrafficDayNum() {
		return fTrafficDayNum;
	}

	public void setfTrafficDayNum(String fTrafficDayNum) {
		this.fTrafficDayNum = fTrafficDayNum;
	}

	public String getfFoodDayNum() {
		return fFoodDayNum;
	}

	public void setfFoodDayNum(String fFoodDayNum) {
		this.fFoodDayNum = fFoodDayNum;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	public String getfExt1() {
		return fExt1;
	}

	public void setfExt1(String fExt1) {
		this.fExt1 = fExt1;
	}

	public String getfExt2() {
		return fExt2;
	}

	public void setfExt2(String fExt2) {
		this.fExt2 = fExt2;
	}

	public String getfExt3() {
		return fExt3;
	}

	public void setfExt3(String fExt3) {
		this.fExt3 = fExt3;
	}

	public String getfExt4() {
		return fExt4;
	}

	public void setfExt4(String fExt4) {
		this.fExt4 = fExt4;
	}

	public String getfExt5() {
		return fExt5;
	}

	public void setfExt5(String fExt5) {
		this.fExt5 = fExt5;
	}
	
	
}
