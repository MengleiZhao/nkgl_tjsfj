package com.braker.icontrol.contract.goldpay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.EntityDao;

/**
 * 合同质保金（收/付款）
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_WARRANTY_GOLD_P")
public class GoldPay extends BaseEntity implements EntityDao{

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_WAR_ID")
	private Integer fWarId;
	
	@Column(name ="F_CONT_ID")
	private Integer fContId_GP;
	
	@Column(name ="F_REC_USER")
	private String fRecUser;//登记人
	
	@Column(name = "F_REC_TIME")
	private Date fRecTime;//登记时间

	@Column(name ="F_PAYED_DATE")
	private Date fpayedDate;//收入保证金到账日期  2021.01.21陈睿超添加
	
	@Column(name ="F_PAY_REMARK")
	private String fPayRemark;//付款说明
	
	@Column(name ="F_WAR_TYPE")
	private String fWarType;//保证金类型
	
	@Column(name ="F_AMOUNT")
	private String fPayAmount;//付款金额
	
	@Column(name ="F_DATA_TYPE")
	private String fDataType;//数据类型0-收款，2-付款  2021.01.21陈睿超添加

	@Transient
	private String contType;//合同类型（0-原合同 1-变更合同）
	
	public Integer getfWarId() {
		return fWarId;
	}

	public void setfWarId(Integer fWarId) {
		this.fWarId = fWarId;
	}

	public Integer getfContId_GP() {
		return fContId_GP;
	}

	public void setfContId_GP(Integer fContId_GP) {
		this.fContId_GP = fContId_GP;
	}

	public String getfRecUser() {
		return fRecUser;
	}

	public void setfRecUser(String fRecUser) {
		this.fRecUser = fRecUser;
	}

	public Date getfRecTime() {
		return fRecTime;
	}

	public void setfRecTime(Date fRecTime) {
		this.fRecTime = fRecTime;
	}

	public String getfPayRemark() {
		return fPayRemark;
	}

	public void setfPayRemark(String fPayRemark) {
		this.fPayRemark = fPayRemark;
	}

	public String getfWarType() {
		return fWarType;
	}

	public void setfWarType(String fWarType) {
		this.fWarType = fWarType;
	}

	public String getfPayAmount() {
		return fPayAmount;
	}

	public void setfPayAmount(String fPayAmount) {
		this.fPayAmount = fPayAmount;
	}

	@Override
	public String getJoinTable() {
		return "T_WARRANTY_GOLD_P";
	}

	@Override
	public String getEntryId() {
		return String.valueOf(fWarId);
	}

	public String getfDataType() {
		return fDataType;
	}

	public void setfDataType(String fDataType) {
		this.fDataType = fDataType;
	}

	public Date getFpayedDate() {
		return fpayedDate;
	}

	public void setFpayedDate(Date fpayedDate) {
		this.fpayedDate = fpayedDate;
	}

	public String getContType() {
		return contType;
	}

	public void setContType(String contType) {
		this.contType = contType;
	}
	
	
	
	
}
