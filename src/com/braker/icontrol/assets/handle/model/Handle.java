package com.braker.icontrol.assets.handle.model;

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

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;
import com.braker.core.model.Lookups;

/**
 * 资产处置信息表
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_ASSET_HANDLE")
public class Handle extends BaseEntity implements EntityDao,CheckEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_ID")
	private Integer fId;
	
	@Column(name ="F_ASS_HANDLE_CODE")
	private String fAssHandleCode;//资产处置单号（流水号）
	
	@Column(name ="F_HANDLE_TITLE")
	private String fHandleTitle;//固定资产处置签报名称
	
	@Column(name ="F_HANDLE_TEXT")
	private String fHandleText;//固定资产处置签报内容
	
	@Column(name ="F_ASS_TYPE")
	private String fAssType;//资产类型 

	@Column(name ="F_ASS_NAME")
	private String fAssName;//资产名称
	
	@ManyToOne
	@JoinColumn(name ="F_HANDLE_TYPE",referencedColumnName="lookupscode")
	private Lookups fHandleType;//处置形式
	
	@Column(name ="F_ACCEPT_NAME")
	private String fAcceptName;//接受方
	
	@Column(name ="F_OLD_AMOUNT")
	private Double fOldAmount;//资产原值总额(元)
	
	@Column(name ="F_MEET_NUMBER")
	private String fMeetNumber;//党组会会议号
	
	@Column(name ="F_HANDLE_REMARK")
	private String fHandleRemark;//处置原因
	
	@Column(name ="F_ACCEPT_STAUTS")
	private String fAcceptStauts;//登记状态     0-未登记       1-已登记
	
	@Column(name ="F_HANDLE_STAUTS")
	private String fHandleStauts;//处置单状态   1-正常    99-删除
	
	@Column(name ="F_FLOW_STAUTS")
	private String fFlowStauts;//审批状态  0：暂存   1：待审核    -1：已退回（所有流程如驳回，则直接回退至申请人）   2：审核中（2->8 系统支持8级审批）    9：已审核
	
	@Column(name ="F_REC_USER_ID")
	private String fRecUserId;//申请人ID
	
	@Column(name ="F_REPLENISH_STAUTS")
	private String fRepStauts;//资产补录状态
	
	@Column(name ="F_REC_USER")
	private String fRecUser;//申请人
	
	@Column(name ="F_REC_DEPT_ID")
	private String fRecDeptId;//申请部门Id
	
	@Column(name ="F_REC_DEPT")
	private String fRecDept;//申请部门
	
	@Column(name ="F_REQ_TIME")
	private Date fReqTime;//申请时间
	
	@Column(name="F_NEXT_USER_NAME")
	private String fNextUserName;//下环节处理人姓名
	
	@Column(name ="F_NEXT_USER_CODE")
	private String fNextUserCode;//下环节处理人 编码
	
	@Column(name="F_NEXT_CODE")
	private String fNextCode;//下环节节点编码
	
	@Transient
	private Integer number;//序号
	
	@Transient
	private String fAssTypeShow;//资产类型（显示用）
	
	@Transient
	private String fAssNameShow;//资产名称（显示用）
	
	@Transient
	private String fReqTimes;//申请时间（显示用）
	
	
	public String getfReqTimes() {
		return fReqTimes;
	}

	public void setfReqTimes(String fReqTimes) {
		this.fReqTimes = fReqTimes;
	}

	public String getfRepStauts() {
		return fRepStauts;
	}

	public void setfRepStauts(String fRepStauts) {
		this.fRepStauts = fRepStauts;
	}

	public String getfAssName() {
		return fAssName;
	}

	public void setfAssName(String fAssName) {
		this.fAssName = fAssName;
	}

	public void setfAssType(String fAssType) {
		this.fAssType = fAssType;
	}

	public String getfAssNameShow() {
		return fAssNameShow;
	}

	public void setfAssNameShow(String fAssNameShow) {
		this.fAssNameShow = fAssNameShow;
	}

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getfAssHandleCode() {
		return fAssHandleCode;
	}

	public void setfAssHandleCode(String fAssHandleCode) {
		this.fAssHandleCode = fAssHandleCode;
	}

	public String getfHandleTitle() {
		return fHandleTitle;
	}

	public void setfHandleTitle(String fHandleTitle) {
		this.fHandleTitle = fHandleTitle;
	}

	public String getfHandleText() {
		return fHandleText;
	}

	public void setfHandleText(String fHandleText) {
		this.fHandleText = fHandleText;
	}


	public String getfAssType() {
		return fAssType;
	}

	public Lookups getfHandleType() {
		return fHandleType;
	}

	public void setfHandleType(Lookups fHandleType) {
		this.fHandleType = fHandleType;
	}

	public String getfAcceptName() {
		return fAcceptName;
	}

	public void setfAcceptName(String fAcceptName) {
		this.fAcceptName = fAcceptName;
	}

	public Double getfOldAmount() {
		return fOldAmount;
	}

	public void setfOldAmount(Double fOldAmount) {
		this.fOldAmount = fOldAmount;
	}

	public String getfMeetNumber() {
		return fMeetNumber;
	}

	public void setfMeetNumber(String fMeetNumber) {
		this.fMeetNumber = fMeetNumber;
	}

	public String getfHandleRemark() {
		return fHandleRemark;
	}

	public void setfHandleRemark(String fHandleRemark) {
		this.fHandleRemark = fHandleRemark;
	}

	public String getfAcceptStauts() {
		return fAcceptStauts;
	}

	public void setfAcceptStauts(String fAcceptStauts) {
		this.fAcceptStauts = fAcceptStauts;
	}

	public String getfHandleStauts() {
		return fHandleStauts;
	}

	public void setfHandleStauts(String fHandleStauts) {
		this.fHandleStauts = fHandleStauts;
	}

	public String getfFlowStauts() {
		return fFlowStauts;
	}

	public void setfFlowStauts(String fFlowStauts) {
		this.fFlowStauts = fFlowStauts;
	}

	public String getfRecUserId() {
		return fRecUserId;
	}

	public void setfRecUserId(String fRecUserId) {
		this.fRecUserId = fRecUserId;
	}

	public String getfRecUser() {
		return fRecUser;
	}

	public void setfRecUser(String fRecUser) {
		this.fRecUser = fRecUser;
	}

	public String getfRecDeptId() {
		return fRecDeptId;
	}

	public void setfRecDeptId(String fRecDeptId) {
		this.fRecDeptId = fRecDeptId;
	}

	public String getfRecDept() {
		return fRecDept;
	}

	public void setfRecDept(String fRecDept) {
		this.fRecDept = fRecDept;
	}

	public Date getfReqTime() {
		return fReqTime;
	}

	public void setfReqTime(Date fReqTime) {
		this.fReqTime = fReqTime;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getfAssTypeShow() {
		return fAssTypeShow;
	}

	public void setfAssTypeShow(String fAssTypeShow) {
		this.fAssTypeShow = fAssTypeShow;
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
		
		this.fFlowStauts=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fFlowStauts;
	}
	@Override
	public void setStauts(String status) {
		
		this.fHandleStauts=status;
	}
	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		
		return fAssHandleCode;
	}
	@Override
	public Integer getBeanId() {
		
		return fId;
	}

	@Override
	public String getUserId() {
		
		return fRecUserId;
	}

	@Override
	public String getNextCheckKey() {
		
		return fNextCode;
	}

	@Override
	public String getJoinTable() {
		
		return "T_ASSET_HANDL";
	}

	@Override
	public String getEntryId() {
		
		return String.valueOf(fId);
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_ASS_HANDLE_CODE";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fNextUserCode;
	}
	
	
	
	
}
