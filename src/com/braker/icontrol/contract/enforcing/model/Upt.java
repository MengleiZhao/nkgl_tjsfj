package com.braker.icontrol.contract.enforcing.model;

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

import org.springframework.stereotype.Controller;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;
import com.braker.core.model.Lookups;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 合同变更记录表
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_CONTRACT_UPT")
public class Upt extends BaseEntity implements EntityDao,CheckEntity{

	@Id
	@Column(name ="F_ID")
	private Integer fId_U;
	
	@Column(name ="F_CONT_ID")
	private Integer fContId_U;
	
	@Column(name ="F_UPT_CODE")
	private String fUptCode;//变更单单号
	
	@Column(name ="F_CONT_NAME_OLD")
	private String fContNameold;//原合同名称
	
	@Column(name ="F_CONT_NAME")
	private String fContName;//合同名称
	
	@Column(name ="F_CONT_UPT_TYPE")
	private String fContUptType;//合同变更类型：HTBGLX-01-补充合同,HTBGLX-02-变更合同'
	
	@Column(name ="F_CONT_CODE_OLD")
	private String fContCodeOld;//原合同号
	
	@Column(name ="F_CONT_CODE")
	private String fContCode;//新合同号
	
	@Column(name ="F_CO_OLD")
	private String fCoOld;//原变更内容
	
	@Column(name="F_CO_NEW")
	private String fCoNew;//变更后内容 
	
	@Column(name ="F_UPT_REASON")
	private String fUptReason;//变更原因
	
	@Column(name ="F_TOFILES_STATUS")
	private Integer fToFilesStatus;//归档状态 0-未归档，1-已归档，
	
	@Column(name = "F_SEALED_STATUS")
	private String fsealedStatus;//是否盖章	0-未盖章	 	1-已盖章
	
	@Column(name = "F_AMOUNT")
	private String fcAmount;//合同金额小写(元)（收入、支出用）
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Column(name ="F_UPT_DATE")
	private Date fUptdate;//变更合同签约日期
	
	@Column(name ="F_FLOW_STAUTS")
	private String fUptFlowStauts;//审批状态 0：暂存1：提交，待审-1：已退回（所有流程如驳回，则直接回退至申请人）2：审核中（2->8 系统支持8级审批）9：已审核 
	
	@Column(name ="F_UPT_STAUTS")
	private String fUptStatus;//变更单状态1-正常99-删除
	
	@Column(name ="F_OPERATOR_ID")
	private String fOperatorID;//申请人id
	
	@Column(name ="F_OPERATOR")
	private String fOperator;//申请人名称
	
	@Column(name ="F_DEPT_ID")
	private String fDeptID;//所属部门ID
	
	@Column(name ="F_DEPT_NAME")
	private String fDeptName;//所属部门名称
	
	@Column(name ="F_REQ_TIME")
	private Date fReqTime;//申请日期
	
	@Column(name ="F_USER_NAME")
	private String fUserName;//下环节处理人 姓名
	
	@Column(name ="F_USER_CODE")
	private String fUserCode;//下环节处理人 编码
	
	@Column(name ="F_N_CODE")
	private String fNCode;//下下节点节点编码
	
	@Column(name ="F_PLAN_CHANGE_STATUS")
	private Integer fPlanChangeStatus;//付款计划变更状态 0-未变更，1-已变更
	
	@Column(name ="F_SHOPPING_CHANGE_STATUS")
	private Integer fShoppingChangeStatus;//采购清单变更状态 0-未变更，1-已变更
	
	@Column(name ="F_CONTRACTOR")
	private String fContractor;//签约方名称
	
	@Column(name = "F_CONT_AMOUNT_MAX")
	private String fcAmountMax;//合同金额大写(元)（收入、支出用）

	@Column(name ="F_MARGIN_AMOUNT")
	private String fMarginAmount;//保证金金额（元）
	 
	@Column(name ="F_PAY_STAUTS")
	private String fPayStauts;//质保金是否退还0:未退还     1:已退还       2:退还中
	
	@Column(name ="F_INCOME_STAUTS")
	private String fIncomeStauts;//收入保证金是否到账0-未确认，1-已确认  2021.01.29赵孟雷添加
	
	@Column(name ="F_INCOME_DATE")
	private Date fIncomeDate;//收入保证金到账日期  2021.01.21陈睿超添加
	
	@Column(name= "F_CONT_START_TIME")
	private Date fContStartTime;// 合同开始时间
	
	@Column(name ="F_CONT_END_TIME")
	private Date fContEndTime;//合同结束时间
	
	@Column (name ="F_DZH_CODE")
	private String fDZHCode;	//党组会会议编号  2021.04.14 方淳洲加

	@Transient
	private Date fReqTimeStart;//报修时间开始(查询用)
	
	@Transient
	private Date fReqTimeEnd;//报修时间结束(查询用)
	
	@Transient
	private int number;//序号
	
	@Transient
	private String fcType;//原合同分类
	
	@Transient
	private String fOperatorOld;//原合同申请人
	
	@Transient
	private String fOperatorIdOld;//原合同申请人id
	
	@Transient
	private String searchTitle;//搜索条件	2021.1.27方淳洲加
	
	@Transient
	private String inStatus;//页面进入状态  1-用印进入   2-台账进入	2021.1.27方淳洲加
	
	@Transient
	private String checkSts;//报销状态  
	@Transient
	private Double reimAmount;//报销金额
	@Transient
	private Date reimTime;//报销时间
	
	
	
	public String getfDZHCode() {
		return fDZHCode;
	}

	public void setfDZHCode(String fDZHCode) {
		this.fDZHCode = fDZHCode;
	}

	public String getInStatus() {
		return inStatus;
	}

	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}
	
	
	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
	
	public String getfIncomeStauts() {
		return fIncomeStauts;
	}

	public void setfIncomeStauts(String fIncomeStauts) {
		this.fIncomeStauts = fIncomeStauts;
	}

	public Date getfIncomeDate() {
		return fIncomeDate;
	}

	public void setfIncomeDate(Date fIncomeDate) {
		this.fIncomeDate = fIncomeDate;
	}

	public String getfPayStauts() {
		return fPayStauts;
	}

	public void setfPayStauts(String fPayStauts) {
		this.fPayStauts = fPayStauts;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Integer getfId_U() {
		return fId_U;
	}

	public void setfId_U(Integer fId_U) {
		this.fId_U = fId_U;
	}

	public Integer getfContId_U() {
		return fContId_U;
	}

	public void setfContId_U(Integer fContId_U) {
		this.fContId_U = fContId_U;
	}

	public String getfContUptType() {
		return fContUptType;
	}

	public void setfContUptType(String fContUptType) {
		this.fContUptType = fContUptType;
	}

	public String getfContCodeOld() {
		return fContCodeOld;
	}

	public void setfContCodeOld(String fContCodeOld) {
		this.fContCodeOld = fContCodeOld;
	}

	public String getfContCode() {
		return fContCode;
	}

	public void setfContCode(String fContCode) {
		this.fContCode = fContCode;
	}

	public String getfCoOld() {
		return fCoOld;
	}

	public void setfCoOld(String fCoOld) {
		this.fCoOld = fCoOld;
	}

	public String getfCoNew() {
		return fCoNew;
	}

	public void setfCoNew(String fCoNew) {
		this.fCoNew = fCoNew;
	}

	public String getfUptFlowStauts() {
		return fUptFlowStauts;
	}

	public void setfUptFlowStauts(String fUptFlowStauts) {
		this.fUptFlowStauts = fUptFlowStauts;
	}

	public String getfContName() {
		return fContName;
	}

	public void setfContName(String fContName) {
		this.fContName = fContName;
	}

	@Transient
	@Override
	public String getJoinTable() {
		return "T_CONTRACT_UPT";
	}

	@Transient
	@Override
	public String getEntryId() {
		return String.valueOf(fId_U);
	}

	public String getfUptReason() {
		return fUptReason;
	}

	public void setfUptReason(String fUptReason) {
		this.fUptReason = fUptReason;
	}

	public Date getfUptdate() {
		return fUptdate;
	}

	public void setfUptdate(Date fUptdate) {
		this.fUptdate = fUptdate;
	}

	public String getfUptStatus() {
		return fUptStatus;
	}

	public void setfUptStatus(String fUptStatus) {
		this.fUptStatus = fUptStatus;
	}

	public String getfUserName() {
		return fUserName;
	}

	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	public String getfUserCode() {
		return fUserCode;
	}

	public void setfUserCode(String fUserCode) {
		this.fUserCode = fUserCode;
	}

	public String getfNCode() {
		return fNCode;
	}

	public void setfNCode(String fNCode) {
		this.fNCode = fNCode;
	}

	public String getfOperatorID() {
		return fOperatorID;
	}

	public void setfOperatorID(String fOperatorID) {
		this.fOperatorID = fOperatorID;
	}

	public String getfOperator() {
		return fOperator;
	}

	public void setfOperator(String fOperator) {
		this.fOperator = fOperator;
	}

	public String getfDeptID() {
		return fDeptID;
	}

	public void setfDeptID(String fDeptID) {
		this.fDeptID = fDeptID;
	}

	public Date getfReqTime() {
		return fReqTime;
	}

	public void setfReqTime(Date fReqTime) {
		this.fReqTime = fReqTime;
	}

	
	public String getfUptCode() {
		return fUptCode;
	}

	public void setfUptCode(String fUptCode) {
		this.fUptCode = fUptCode;
	}

	@Override
	public void setNextCheckUserName(String userName) {
		this.fUserName=userName;
	}

	@Override
	public void setNextCheckUserId(String userId) {
		this.fUserCode=userId;
	}

	@Override
	public void setNextCheckKey(String nCode) {
		this.fNCode=nCode;
	}

	public String getFsealedStatus() {
		return fsealedStatus;
	}

	public void setFsealedStatus(String fsealedStatus) {
		this.fsealedStatus = fsealedStatus;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		this.fUptFlowStauts=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fUptFlowStauts;
	}
	@Override
	public void setStauts(String status) {
		
		
	}

	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		
		return fContCode;
	}

	@Override
	public Integer getBeanId() {
		
		return fId_U;
	}

	@Override
	public String getUserId() {
		
		return fOperatorID;
	}

	@Override
	public String getNextCheckKey() {
		return fNCode;
	}

	public Date getfReqTimeStart() {
		return fReqTimeStart;
	}

	public void setfReqTimeStart(Date fReqTimeStart) {
		this.fReqTimeStart = fReqTimeStart;
	}

	public Date getfReqTimeEnd() {
		return fReqTimeEnd;
	}

	public void setfReqTimeEnd(Date fReqTimeEnd) {
		this.fReqTimeEnd = fReqTimeEnd;
	}

	public String getfContNameold() {
		return fContNameold;
	}

	public void setfContNameold(String fContNameold) {
		this.fContNameold = fContNameold;
	}

	public Integer getfToFilesStatus() {
		return fToFilesStatus;
	}

	public void setfToFilesStatus(Integer fToFilesStatus) {
		this.fToFilesStatus = fToFilesStatus;
	}

	public String getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(String fcAmount) {
		this.fcAmount = fcAmount;
	}

	public String getfDeptName() {
		return fDeptName;
	}

	public void setfDeptName(String fDeptName) {
		this.fDeptName = fDeptName;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_CONT_CODE";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fUserCode;
	}

	public String getFcType() {
		return fcType;
	}

	public void setFcType(String fcType) {
		this.fcType = fcType;
	}

	public String getfOperatorOld() {
		return fOperatorOld;
	}

	public void setfOperatorOld(String fOperatorOld) {
		this.fOperatorOld = fOperatorOld;
	}

	public String getfOperatorIdOld() {
		return fOperatorIdOld;
	}

	public void setfOperatorIdOld(String fOperatorIdOld) {
		this.fOperatorIdOld = fOperatorIdOld;
	}

	public Integer getfPlanChangeStatus() {
		return fPlanChangeStatus;
	}

	public void setfPlanChangeStatus(Integer fPlanChangeStatus) {
		this.fPlanChangeStatus = fPlanChangeStatus;
	}

	public Integer getfShoppingChangeStatus() {
		return fShoppingChangeStatus;
	}

	public void setfShoppingChangeStatus(Integer fShoppingChangeStatus) {
		this.fShoppingChangeStatus = fShoppingChangeStatus;
	}

	public String getfContractor() {
		return fContractor;
	}

	public void setfContractor(String fContractor) {
		this.fContractor = fContractor;
	}

	public String getFcAmountMax() {
		return fcAmountMax;
	}

	public void setFcAmountMax(String fcAmountMax) {
		this.fcAmountMax = fcAmountMax;
	}

	public String getfMarginAmount() {
		return fMarginAmount;
	}

	public void setfMarginAmount(String fMarginAmount) {
		this.fMarginAmount = fMarginAmount;
	}

	public Date getfContStartTime() {
		return fContStartTime;
	}

	public void setfContStartTime(Date fContStartTime) {
		this.fContStartTime = fContStartTime;
	}

	public Date getfContEndTime() {
		return fContEndTime;
	}

	public void setfContEndTime(Date fContEndTime) {
		this.fContEndTime = fContEndTime;
	}

	public String getCheckSts() {
		return checkSts;
	}

	public void setCheckSts(String checkSts) {
		this.checkSts = checkSts;
	}

	public Double getReimAmount() {
		return reimAmount;
	}

	public void setReimAmount(Double reimAmount) {
		this.reimAmount = reimAmount;
	}

	public Date getReimTime() {
		return reimTime;
	}

	public void setReimTime(Date reimTime) {
		this.reimTime = reimTime;
	}

	
	
}
