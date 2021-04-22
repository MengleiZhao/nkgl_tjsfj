package com.braker.icontrol.expend.apply.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.braker.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 公务接待餐费明细
 * @author 沈帆
 *
 */
@Entity
@Table(name ="T_RECEPTION_FOOD")
public class ReceptionFood extends BaseEntity{

	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_F_ID")
	private Integer fID;
	
	@Column(name = "F_G_ID")
	private Integer gId;			//申请信息ID

	@Column(name = "F_R_ID")
	private Integer rId;			//报销信息ID
	
	@Column(name ="F_FOOD_TYPE")
	private String fFoodType;        //类别
	
	@Column(name ="F_COST_STD")
	private String fCostStd;  //费用标准
	
	@Column(name ="F_PERSON_NUM")
	private String fPersonNum;       //就餐人数  陈睿超2021.01.26修改成“接待人数”使用
	
	@Column(name = "F_ATTEND_NUM")
	private String attendPeopNum;	//陪餐人数 陈睿超2021.01.26添加
	
	@Column(name ="F_NUM")
	private String fNum; //次数
	
	@Column(name ="F_COST_FOOD")
	private Double fCostFood;//餐费小计
	
	@Column(name ="F_MARK")
	private String mark; //其他费用标识
	
	@Column(name ="F_ADDRESS")
	private String address; //地点 陈睿超2021.01.26添加
	
	@Column(name ="F_FOODTIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date foodTime; //日期 陈睿超2021.01.26添加


	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public Integer getgId() {
		return gId;
	}

	public void setgId(Integer gId) {
		this.gId = gId;
	}

	public Integer getfID() {
		return fID;
	}

	public void setfID(Integer fID) {
		this.fID = fID;
	}

	public String getfFoodType() {
		return fFoodType;
	}

	public void setfFoodType(String fFoodType) {
		this.fFoodType = fFoodType;
	}

	public String getfCostStd() {
		return fCostStd;
	}

	public void setfCostStd(String fCostStd) {
		this.fCostStd = fCostStd;
	}

	public String getfPersonNum() {
		return fPersonNum;
	}

	public void setfPersonNum(String fPersonNum) {
		this.fPersonNum = fPersonNum;
	}

	public String getfNum() {
		return fNum;
	}

	public void setfNum(String fNum) {
		this.fNum = fNum;
	}

	public Double getfCostFood() {
		return fCostFood;
	}

	public void setfCostFood(Double fCostFood) {
		this.fCostFood = fCostFood;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getFoodTime() {
		return foodTime;
	}

	public void setFoodTime(Date foodTime) {
		this.foodTime = foodTime;
	}

	public String getAttendPeopNum() {
		return attendPeopNum;
	}

	public void setAttendPeopNum(String attendPeopNum) {
		this.attendPeopNum = attendPeopNum;
	}

	
	
}
