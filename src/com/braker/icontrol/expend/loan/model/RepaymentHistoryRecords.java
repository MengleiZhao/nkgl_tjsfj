package com.braker.icontrol.expend.loan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;
/**
 * 还款记录表
 * @author 赵孟雷
 *
 */
@Entity
@Table(name="T_REPAYMENT_HISTORY_RECORDS")
public class RepaymentHistoryRecords extends BaseEntity{

	@Id
	@Column(name = "RE_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer rhrId;				//主键ID
	
	@Column(name = "F_L_ID")
	private Integer lId;				//借款单编号
	
	//还款编号
	@Column(name ="F_CODE")
	private String code;
	
	//还款人
	@Column(name ="F_PAY_PERSON_ID")
	private String payPersonId;
	
	//还款人
	@Column(name ="F_PAY_PERSON")
	private String payPerson;
	
	//还款时间
	@Column(name ="F_PAY_TIME")
	private Date payTime;
	
	//还款金额
	@Column(name ="F_AMOUNT")
	private String payAmount;
	
	//剩余还款金额
	@Column(name ="F_SURPLUS_AMOUNT")
	private String surplusPayAmount;
	
	@Transient
	private Integer num;

	public Integer getRhrId() {
		return rhrId;
	}

	public void setRhrId(Integer rhrId) {
		this.rhrId = rhrId;
	}

	public Integer getlId() {
		return lId;
	}

	public void setlId(Integer lId) {
		this.lId = lId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPayPerson() {
		return payPerson;
	}

	public void setPayPerson(String payPerson) {
		this.payPerson = payPerson;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getSurplusPayAmount() {
		return surplusPayAmount;
	}

	public void setSurplusPayAmount(String surplusPayAmount) {
		this.surplusPayAmount = surplusPayAmount;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getPayPersonId() {
		return payPersonId;
	}

	public void setPayPersonId(String payPersonId) {
		this.payPersonId = payPersonId;
	}
	
}
