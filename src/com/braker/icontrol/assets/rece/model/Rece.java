package com.braker.icontrol.assets.rece.model;

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
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 资产领用表
 * @author 陈睿超
 * @updateTime 2020-07-15
 * @updater 陈睿超
 */
@Entity
@Table(name ="T_ASSET_RECE")
public class Rece extends BaseEntity  implements EntityDao,CheckEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_ID")
	private Integer fId_R;
	
	@Column(name ="F_ASS_RECE_CODE")
	private String fAssReceCode;//资产领用单号（流水号）
	
	@Column(name ="F_ASS_TYPE")
	private String fAssType;//资产类型
	
	@Column(name ="F_ASS_NAME")
	private String fAssName;//资产名称
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_RECE_DATE")
	private Date fReceTime;//预计领用时间
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_REQ_TIME")
	private Date fReqTime;//申请时间
	
	@Column(name ="F_REQ_DEPT_ID")
	private String fReqDeptID;//申请部门ID

	@Column(name ="F_REQ_DEPT")
	private String fReqDept;//申请部门
	
	@Column(name ="F_REQ_USER")
	private String fReqUser;//申请人
	
	@Column(name ="F_REQ_USER_ID")
	private String fReqUserid;//申请人的ID
	
	@Column(name ="F_RECE_DEPT_ID")
	private String fReceDeptID;//领用部门ID
	
	@Column(name ="F_RECE_DEPT")
	private String fReceDept;//领用部门
	
	@Column(name ="F_RECE_USER")
	private String fReceUser;//领用人
	
	@Column(name ="F_RECE_USER_ID")
	private String fReceUserid;//领用人的ID
	
	@Column(name ="F_RECE_NUM")
	private Double fReceNum;//合计数量
	
	@Column(name ="F_SUM_AMOUNT")
	private Double fSumAmount;//总金额(元)
	
	@Column(name ="F_RECE_REMARK")
	private String fReceRemark;//领用说明
	
	@Column(name ="F_NEXT_USER_NAME")
	private String fNextUserName;//下环节处理人 姓名
	
	@Column(name ="F_NEXT_USER_CODE")
	private String fNextUserCode;//下环节处理人 编码
	
	@Column(name ="F_NEXT_CODE")
	private String fNextCode;//下节点节点编码
	
	@Column(name ="F_FLOW_STAUTS")
	private String fFlowStauts_R;//审批状态0：暂存1：待审核-1：已退回（所有流程如驳回，则直接回退至申请人）2：审核中（2->8 系统支持8级审批）9：已审核
	
	@Column(name ="F_ASS_STAUTS")
	private String fAssStauts;//领用单状态1-正常99-删除
	
	@Column(name ="F_ACCEPT_STAUTS")
	private String fAcceptStatus;//登记状态0-未登记	1-已登记
	
	@Column(name ="F_RECE_STAUTS")
	private String fReceStatus;//确认状态  1-同意，0-不同意
	
	@Column(name ="F_CONFIRM_DATE")
	private Date confirmDate;//实际同意确认时间
	
	@Column(name ="F_CONFIG_STAUTS")
	private String fConfigStatus;//配置状态  0-未配置，1-已配置
	
	@Transient
	private Integer number;//序号
	
	@Transient
	private Date fReceTimeBegin;//领用时间开始
	
	@Transient
	private Date fReceTimeEnd;//领用时间截止
	

	
	public Integer getfId_R() {
		return fId_R;
	}

	public void setfId_R(Integer fId_R) {
		this.fId_R = fId_R;
	}

	public String getfAssReceCode() {
		return fAssReceCode;
	}

	public void setfAssReceCode(String fAssReceCode) {
		this.fAssReceCode = fAssReceCode;
	}

	public String getfAssType() {
		return fAssType;
	}

	public void setfAssType(String fAssType) {
		this.fAssType = fAssType;
	}

	public Date getfReqTime() {
		return fReqTime;
	}

	public void setfReqTime(Date fReqTime) {
		this.fReqTime = fReqTime;
	}

	public String getfReqDeptID() {
		return fReqDeptID;
	}

	public void setfReqDeptID(String fReqDeptID) {
		this.fReqDeptID = fReqDeptID;
	}

	public String getfReqDept() {
		return fReqDept;
	}

	public void setfReqDept(String fReqDept) {
		this.fReqDept = fReqDept;
	}

	public String getfReqUser() {
		return fReqUser;
	}

	public void setfReqUser(String fReqUser) {
		this.fReqUser = fReqUser;
	}

	public String getfReqUserid() {
		return fReqUserid;
	}

	public void setfReqUserid(String fReqUserid) {
		this.fReqUserid = fReqUserid;
	}

	public String getfReceDeptID() {
		return fReceDeptID;
	}

	public void setfReceDeptID(String fReceDeptID) {
		this.fReceDeptID = fReceDeptID;
	}

	public String getfReceDept() {
		return fReceDept;
	}

	public void setfReceDept(String fReceDept) {
		this.fReceDept = fReceDept;
	}

	public String getfReceUser() {
		return fReceUser;
	}

	public void setfReceUser(String fReceUser) {
		this.fReceUser = fReceUser;
	}

	public String getfReceUserid() {
		return fReceUserid;
	}

	public void setfReceUserid(String fReceUserid) {
		this.fReceUserid = fReceUserid;
	}

	public Double getfSumAmount() {
		return fSumAmount;
	}

	public void setfSumAmount(Double fSumAmount) {
		this.fSumAmount = fSumAmount;
	}

	public String getfReceRemark() {
		return fReceRemark;
	}

	public void setfReceRemark(String fReceRemark) {
		this.fReceRemark = fReceRemark;
	}

	public String getfNextUserName() {
		return fNextUserName;
	}

	public void setfNextUserName(String fNextUserName) {
		this.fNextUserName = fNextUserName;
	}

	public String getfNextUserCode() {
		return fNextUserCode;
	}

	public void setfNextUserCode(String fNextUserCode) {
		this.fNextUserCode = fNextUserCode;
	}

	public String getfNextCode() {
		return fNextCode;
	}

	public void setfNextCode(String fNextCode) {
		this.fNextCode = fNextCode;
	}

	public String getfFlowStauts_R() {
		return fFlowStauts_R;
	}

	public void setfFlowStauts_R(String fFlowStauts_R) {
		this.fFlowStauts_R = fFlowStauts_R;
	}

	public String getfAssStauts() {
		return fAssStauts;
	}

	public void setfAssStauts(String fAssStauts) {
		this.fAssStauts = fAssStauts;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getfReceTimeBegin() {
		return fReceTimeBegin;
	}

	public void setfReceTimeBegin(Date fReceTimeBegin) {
		this.fReceTimeBegin = fReceTimeBegin;
	}

	public Date getfReceTimeEnd() {
		return fReceTimeEnd;
	}

	public void setfReceTimeEnd(Date fReceTimeEnd) {
		this.fReceTimeEnd = fReceTimeEnd;
	}

	public Date getfReceTime() {
		return fReceTime;
	}

	public void setfReceTime(Date fReceTime) {
		this.fReceTime = fReceTime;
	}

	public Double getfReceNum() {
		return fReceNum;
	}

	public void setfReceNum(Double fReceNum) {
		this.fReceNum = fReceNum;
	}

	public String getfAcceptStatus() {
		return fAcceptStatus;
	}

	public void setfAcceptStatus(String fAcceptStatus) {
		this.fAcceptStatus = fAcceptStatus;
	}

	public String getfReceStatus() {
		return fReceStatus;
	}

	public void setfReceStatus(String fReceStatus) {
		this.fReceStatus = fReceStatus;
	}

	public String getfAssName() {
		return fAssName;
	}

	public void setfAssName(String fAssName) {
		this.fAssName = fAssName;
	}

	public String getfConfigStatus() {
		return fConfigStatus;
	}

	public void setfConfigStatus(String fConfigStatus) {
		this.fConfigStatus = fConfigStatus;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Override
	public void setNextCheckUserName(String userName) {
		
		this.fNextUserName=userName;
	}

	@Override
	public void setNextCheckUserId(String userId) {
		
		this.fNextUserCode=userId;
	}

	@Override
	public void setNextCheckKey(String nCode) {
		
		this.fNextCode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.fFlowStauts_R=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fFlowStauts_R;
	}
	@Override
	public void setStauts(String status) {
		
		this.fAssStauts=status;
	}
	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		
		return fAssReceCode;
	}
	@Override
	public Integer getBeanId() {
		
		return fId_R;
	}

	@Override
	public String getUserId() {
		
		return fReqUserid;
	}

	@Override
	public String getNextCheckKey() {
		
		return fNextCode;
	}

	@Override
	public String getJoinTable() {
		
		return "T_ASSET_RECE";
	}

	@Override
	public String getEntryId() {
		
		return String.valueOf(fId_R);
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_ASS_RECE_CODE";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fNextUserCode;
	}
	
}
