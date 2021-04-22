package com.braker.icontrol.expend.apply.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.EntityDao;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 公杂费信息列表
 * @author 赵孟雷
 *
 */
@Entity
@Table(name ="T_MISCELLANEOUS_FEE")
public class MiscellaneousFeeInfo extends BaseEntity implements EntityDao{

	@Id
	@Column(name = "F_M_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer mId;//主键
	
	@Column(name = "F_G_ID")
	private Integer gId;			//ApplicationBasicInfo 的主键ID
	
	@Column(name = "F_R_ID")
	private Integer rId;			//ReimbAppliBasicInfo的主键ID
	
	@Column(name = "F_ADDRESS")
	private String fAddress;//目的地

	@Column(name = "F_DAYS")
	private Integer fDays;//天数
	
	@Column(name  = "F_APPLY_AMOUNT")
	private Double fApplyAmount;//事前申请金额(元)
	
	@Column(name  = "F_REIMB_AMOUNT")
	private Double fReimbAmount;//报销金额(元)

	@Column(name = "F_STANDARD")
	private Double Standard;//费用标准
	
	@Column(name = "F_COUNT_STANDARD")
	private Double countStandard;//总额标准（外币）
	
	@Column(name = "F_CURRENCY")
	private String currency;//币种
	
	@Column(name = "F_STATUS")
	private Integer fStatus;//数据状态0-事前申请，1-事后报销
	
	@Transient
	private Integer num;         //数量

	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
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

	public String getfAddress() {
		return fAddress;
	}

	public void setfAddress(String fAddress) {
		this.fAddress = fAddress;
	}

	public Double getfApplyAmount() {
		return fApplyAmount;
	}

	public void setfApplyAmount(Double fApplyAmount) {
		this.fApplyAmount = fApplyAmount;
	}

	public Double getfReimbAmount() {
		return fReimbAmount;
	}

	public void setfReimbAmount(Double fReimbAmount) {
		this.fReimbAmount = fReimbAmount;
	}

	public Double getStandard() {
		return Standard;
	}

	public void setStandard(Double standard) {
		Standard = standard;
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

	public Integer getfStatus() {
		return fStatus;
	}

	public void setfStatus(Integer fStatus) {
		this.fStatus = fStatus;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getfDays() {
		return fDays;
	}

	public void setfDays(Integer fDays) {
		this.fDays = fDays;
	}

	@Override
	public String getJoinTable() {
		
		return null;
	}

	@Override
	public String getEntryId() {
		
		return null;
	}
	
	
}
