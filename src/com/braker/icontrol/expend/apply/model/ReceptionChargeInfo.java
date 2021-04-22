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
 * 公务接待收费明细
 * @author 陈睿超
 *
 */
@Entity
@Table(name = "T_CHARGE_INFO")
public class ReceptionChargeInfo extends BaseEntity{

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_C_I_ID")
	private Integer cId;//主键

	@Column(name = "F_G_ID")
	private Integer gId; 				//申请单主键

	@Column(name = "F_R_ID")
	private Integer rId;				//报销单主键
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	@Column(name ="F_C_TIME")
	private Date fcTime;//日期
	
	@Column(name ="F_BREAKFAST_PEOPLE_NUM")
	private Integer breakfastPeopleNum;//早餐用餐人数
	
	@Column(name ="F_LUNCH_PEOPLE_NUM")
	private Integer lunchPeopleNum;//午餐用餐人数
	
	@Column(name ="F_DINNER_PEOPLE_NUM")
	private Integer dinnerPeopleNum;//晚餐用餐人数
	
	@Column(name ="F_HALF_DAY_PEOPLE_NUM")
	private Integer halfDayPeopleNum;//半天用车人数
	
	@Column(name ="F_DAY_PEOPLE_NUM")
	private Integer dayPeopleNum;//全天用车人数
	
	@Column(name ="F_TOTAL_MEAL")
	private Double totalMeal;//当日用餐费用合计
	
	@Column(name ="F_TOTAL_FARE")
	private Double totalFare;//当日用车费用合计
	
	
	@Transient
	private Integer number;//序号


	public Integer getcId() {
		return cId;
	}


	public void setcId(Integer cId) {
		this.cId = cId;
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

	public Date getFcTime() {
		return fcTime;
	}


	public void setFcTime(Date fcTime) {
		this.fcTime = fcTime;
	}


	public Integer getBreakfastPeopleNum() {
		return breakfastPeopleNum;
	}


	public void setBreakfastPeopleNum(Integer breakfastPeopleNum) {
		this.breakfastPeopleNum = breakfastPeopleNum;
	}


	public Integer getLunchPeopleNum() {
		return lunchPeopleNum;
	}


	public void setLunchPeopleNum(Integer lunchPeopleNum) {
		this.lunchPeopleNum = lunchPeopleNum;
	}


	public Integer getDinnerPeopleNum() {
		return dinnerPeopleNum;
	}


	public void setDinnerPeopleNum(Integer dinnerPeopleNum) {
		this.dinnerPeopleNum = dinnerPeopleNum;
	}


	public Integer getHalfDayPeopleNum() {
		return halfDayPeopleNum;
	}


	public void setHalfDayPeopleNum(Integer halfDayPeopleNum) {
		this.halfDayPeopleNum = halfDayPeopleNum;
	}


	public Integer getDayPeopleNum() {
		return dayPeopleNum;
	}


	public void setDayPeopleNum(Integer dayPeopleNum) {
		this.dayPeopleNum = dayPeopleNum;
	}


	public Double getTotalMeal() {
		return totalMeal;
	}


	public void setTotalMeal(Double totalMeal) {
		this.totalMeal = totalMeal;
	}


	public Double getTotalFare() {
		return totalFare;
	}


	public void setTotalFare(Double totalFare) {
		this.totalFare = totalFare;
	}


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
	
	
	
}
