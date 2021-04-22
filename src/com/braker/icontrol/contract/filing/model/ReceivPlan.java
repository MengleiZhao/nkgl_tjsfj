package com.braker.icontrol.contract.filing.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.braker.common.entity.BaseEntityEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 付款计划表
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_RECEIV_PLAN")
public class ReceivPlan extends BaseEntityEmpty{

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_PLAN_ID")
	private Integer fPlanId;
	
	@Column(name ="F_CONT_ID")
	private Integer fContId_R;

	@Column(name ="F_UPT_ID")
	private Integer fUptId_R;
	
	@Column(name ="F_RECE_PROPERTY")
	private String fReceProperty;//付款性质
	
	@Column(name ="F_RECE_STAGE")
	private String fRecStage;//付款阶段
	
	@Column(name ="F_RECE_CONDITION")
	private String fReceCondition;//付款条件
	
	@Column(name ="F_RECE_PROOF")
	private String fReceProof;//付款凭据
	
	@Transient
	private String fReceProofName;//付款凭据显示
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@Column(name ="F_RECE_PLAN_TIME")
	private Date fRecePlanTime;//预计付款时间
	
	@Column (name  ="F_RECE_PLAN_AMOUNT")
	private Double fRecePlanAmount;//预计付款金额
	
	@Column(name ="F_UPDATE_USER")
	private String fUpateUser_R;//修改人
	
	@Column(name ="F_UPDATE_TIME")
	private Date fUpateTime_R ;//修改时间
	
	@Column(name ="F_STAUTS")
	private String fStauts_R;//付款状态  0-未付款,1-已付款
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Column(name="F_RECE_TIME")
	private Date fReceTime ;//实际付款时间
	
	@Column(name ="F_RECE_AMOUNT")
	private String fReceAmount;//实际付款金额
	
	@Column(name ="F_SETTLE_TYPE")
	private String fSettleType;//结算方式
	
	@Column(name ="F_ORIGINAL_BILLS_NUM")
	private String fOriginalBillsNum;//原始票据张数
	
	@Column(name ="F_PAY_STAUTS")
	private String payStauts;//付款申请审批状态
	
	@Column(name ="F_DATA_TYPE")
	private Integer dataType;//数据类型 0-原合同数据  1-变更合同的数据
	
	@Transient
	private Integer datenum;//剩余天数
	
	@Transient
	private String fReceProofs;//付款凭据
	
	@Transient
	private Double fNotAllAmountL;//未付款金额
	
	public String getfReceProofs() {
		return fReceProofs;
	}

	public void setfReceProofs(String fReceProofs) {
		this.fReceProofs = fReceProofs;
	}

	public Double getfNotAllAmountL() {
		return fNotAllAmountL;
	}

	public void setfNotAllAmountL(Double fNotAllAmountL) {
		this.fNotAllAmountL = fNotAllAmountL;
	}

	public Integer getDatenum() {
		return datenum;
	}

	public void setDatenum(Integer datenum) {
		this.datenum = datenum;
	}

	public String getfSettleType() {
		return fSettleType;
	}

	public void setfSettleType(String fSettleType) {
		this.fSettleType = fSettleType;
	}

	public String getfOriginalBillsNum() {
		return fOriginalBillsNum;
	}

	public void setfOriginalBillsNum(String fOriginalBillsNum) {
		this.fOriginalBillsNum = fOriginalBillsNum;
	}

	public Integer getfPlanId() {
		return fPlanId;
	}

	public void setfPlanId(Integer fPlanId) {
		this.fPlanId = fPlanId;
	}

	public Integer getfContId_R() {
		return fContId_R;
	}

	public void setfContId_R(Integer fContId_R) {
		this.fContId_R = fContId_R;
	}

	public String getfReceProperty() {
		return fReceProperty;
	}

	public void setfReceProperty(String fReceProperty) {
		this.fReceProperty = fReceProperty;
	}

	public String getfRecStage() {
		return fRecStage;
	}

	public void setfRecStage(String fRecStage) {
		this.fRecStage = fRecStage;
	}

	public String getfReceCondition() {
		return fReceCondition;
	}

	public void setfReceCondition(String fReceCondition) {
		this.fReceCondition = fReceCondition;
	}

	public Date getfRecePlanTime() {
		return fRecePlanTime;
	}

	public void setfRecePlanTime(Date fRecePlanTime) {
		this.fRecePlanTime = fRecePlanTime;
	}

	public Double getfRecePlanAmount() {
		return fRecePlanAmount;
	}

	public void setfRecePlanAmount(Double fRecePlanAmount) {
		this.fRecePlanAmount = fRecePlanAmount;
	}

	public String getfUpateUser_R() {
		return fUpateUser_R;
	}

	public void setfUpateUser_R(String fUpateUser_R) {
		this.fUpateUser_R = fUpateUser_R;
	}

	public Date getfUpateTime_R() {
		return fUpateTime_R;
	}

	public void setfUpateTime_R(Date fUpateTime_R) {
		this.fUpateTime_R = fUpateTime_R;
	}

	public String getfStauts_R() {
		return fStauts_R;
	}

	public void setfStauts_R(String fStauts_R) {
		this.fStauts_R = fStauts_R;
	}

	public Date getfReceTime() {
		return fReceTime;
	}

	public void setfReceTime(Date fReceTime) {
		this.fReceTime = fReceTime;
	}

	public String getfReceAmount() {
		return fReceAmount;
	}

	public void setfReceAmount(String fReceAmount) {
		this.fReceAmount = fReceAmount;
	}

	public String getPayStauts() {
		return payStauts;
	}

	public void setPayStauts(String payStauts) {
		this.payStauts = payStauts;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getfUptId_R() {
		return fUptId_R;
	}

	public void setfUptId_R(Integer fUptId_R) {
		this.fUptId_R = fUptId_R;
	}

	public String getfReceProof() {
		return fReceProof;
	}

	public void setfReceProof(String fReceProof) {
		this.fReceProof = fReceProof;
	}

	public String getfReceProofName() {
		return fReceProofName;
	}

	public void setfReceProofName(String fReceProofName) {
		this.fReceProofName = fReceProofName;
	}
	
}
