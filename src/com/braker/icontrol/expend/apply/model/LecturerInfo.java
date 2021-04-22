package com.braker.icontrol.expend.apply.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;


/**
 * 讲师信息model
 * @author 沈帆
 * @createtime 2020-05-12
 * @updatetime 2020-05-12
 */
@Entity
@Table(name = "T_LECTURER_INFO")
public class LecturerInfo extends BaseEntity{
	@Id
	@Column(name = "F_L_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer lId;			//主键ID
	
	@Column(name = "F_T_ID")
	private Integer tId;				//培训信息ID
	
	@Column(name = "F_LECTURER_NAME")
	private String lecturerName;	// 讲师姓名
	
	@Column(name = "F_LECTURER_LEVEL")
	private String lecturerLevel;			//讲师级别
	
	@Column(name = "F_LECTURER_LEVEL_CODE")
	private String lecturerLevelCode;			//讲师级别编码
	
	@Column(name = "F_IS_OUTSIDE")
	private String isOutside;			//是否异地（0-否，1-是）
	
	@Column(name = "F_BANK_CARD")
	private String bankCard;			//银行卡号
	
	@Column(name = "F_BANK")
	private String bank;			//开户行
	
	@Column(name = "F_PHONENUM")
	private String phoneNum;			//手机号
	
	@Column(name = "F_UNIT")
	private String unit;			//单位名称
	
	@Column(name = "F_IDCARD")
	private String idCard;			//身份证号
	
	@Transient
	private String status;				//状态值easyui插件中带的，装换json时用(数据库中没有)


	public Integer getlId() {
		return lId;
	}


	public void setlId(Integer lId) {
		this.lId = lId;
	}


	public Integer gettId() {
		return tId;
	}


	public void settId(Integer tId) {
		this.tId = tId;
	}


	public String getLecturerName() {
		return lecturerName;
	}


	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}


	public String getLecturerLevel() {
		return lecturerLevel;
	}


	public void setLecturerLevel(String lecturerLevel) {
		this.lecturerLevel = lecturerLevel;
	}


	public String getIsOutside() {
		return isOutside;
	}


	public void setIsOutside(String isOutside) {
		this.isOutside = isOutside;
	}


	public String getBankCard() {
		return bankCard;
	}


	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}


	public String getPhoneNum() {
		return phoneNum;
	}


	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getLecturerLevelCode() {
		return lecturerLevelCode;
	}


	public void setLecturerLevelCode(String lecturerLevelCode) {
		this.lecturerLevelCode = lecturerLevelCode;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getIdCard() {
		return idCard;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	
	
	
}
