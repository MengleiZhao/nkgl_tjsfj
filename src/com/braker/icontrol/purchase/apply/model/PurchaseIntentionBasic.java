package com.braker.icontrol.purchase.apply.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 采购意向实体类
 * @author wanping
 *
 */
@Entity
@Table(name="T_PURCHASE_INTENTION_BASIC")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseIntentionBasic extends BaseEntity implements EntityDao,CheckEntity {
	@Id
	@Column(name ="F_ID")
	private Integer fId;				//主键
	
	@Column(name = "F_INTENTION_CODE")
	private String fIntentionCode;      //意向公开编号
	
	@Column(name = "F_PURCHASE_NAME")
	private String fPurchaseName;	    //采购项目名称
	
	@Column (name ="F_DEPT_NAME")
	private String fDeptName;			//申报部门
	
	@Column (name ="F_DEPT_ID")
	private String fDeptId;				//申报部门id
	
	@Column (name ="F_USER")
	private String fUser;				//申报人id
	
	@Column (name ="F_USER_NAME")
	private String fUserName;			//申报人
	
	@Column (name ="F_AMOUNT")
	private Double amount;			    //预算金额
	
	@Column (name="F_PURCHASE_TIME")
	private Date fPurchaseTime;			//预计采购时间
	
	@Column(name = "F_PURCHASE_DEMAND")
	private String fPurchaseDemand;		//采购需求概况
	
	@Column(name ="F_IS_FOR_SMES")
	private Integer isForSmes;			//是否面向中小企业 0-是 1-否
	
	@Column(name = "F_REMARK")
	private String remark;			    //备注
	
	@Column(name = "F_PURCHASE_FILE_ID")
	private String fPurchaseFileId;		//附件ID
	
	@Column (name="F_REQ_TIME")
	private Date fReqTime;				//申请时间
	
	@Column(name = "F_FLOW_STAUTS")
	private String flowStauts;		    //审批状态
	
	@Column (name ="F_STAUTS")
	private String stauts;             //有效状态 99-删除 
	
	@Column(name = "F_PUBLIC_STATUS")
	private String publicStatus;		//公开状态 0-未公开 1-已公开
	
	@Column (name="F_PUBLIC_TIME")
	private Date publicTime;			//公开确认时间
	
	@Column(name = "F_USER_NAME2")
	private String userName2;			//下环节处理人姓名
	
	@Column(name = "F_USER_ID")
	private String fuserId;			    //下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;				//下节点节点编码
	
	@Column(name = "F_IS_USED")
	private String isUsed;				//是否被申请单使用  0:未被使用 1:已被使用
	
	@Transient
	private Integer num;			    //序号(数据库中没有)
	
	
	
	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getfIntentionCode() {
		return fIntentionCode;
	}

	public void setfIntentionCode(String fIntentionCode) {
		this.fIntentionCode = fIntentionCode;
	}

	public String getfPurchaseName() {
		return fPurchaseName;
	}

	public void setfPurchaseName(String fPurchaseName) {
		this.fPurchaseName = fPurchaseName;
	}

	public String getfDeptName() {
		return fDeptName;
	}

	public void setfDeptName(String fDeptName) {
		this.fDeptName = fDeptName;
	}

	public String getfDeptId() {
		return fDeptId;
	}

	public void setfDeptId(String fDeptId) {
		this.fDeptId = fDeptId;
	}

	public String getfUser() {
		return fUser;
	}

	public void setfUser(String fUser) {
		this.fUser = fUser;
	}

	public String getfUserName() {
		return fUserName;
	}

	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getfPurchaseTime() {
		return fPurchaseTime;
	}

	public void setfPurchaseTime(Date fPurchaseTime) {
		this.fPurchaseTime = fPurchaseTime;
	}

	public String getfPurchaseDemand() {
		return fPurchaseDemand;
	}

	public void setfPurchaseDemand(String fPurchaseDemand) {
		this.fPurchaseDemand = fPurchaseDemand;
	}

	public Integer getIsForSmes() {
		return isForSmes;
	}

	public void setIsForSmes(Integer isForSmes) {
		this.isForSmes = isForSmes;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getfPurchaseFileId() {
		return fPurchaseFileId;
	}

	public void setfPurchaseFileId(String fPurchaseFileId) {
		this.fPurchaseFileId = fPurchaseFileId;
	}

	public Date getfReqTime() {
		return fReqTime;
	}

	public void setfReqTime(Date fReqTime) {
		this.fReqTime = fReqTime;
	}

	public String getFlowStauts() {
		return flowStauts;
	}

	public void setFlowStauts(String flowStauts) {
		this.flowStauts = flowStauts;
	}

	public String getStauts() {
		return stauts;
	}

	public void setStauts(String stauts) {
		this.stauts = stauts;
	}

	public String getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(String publicStatus) {
		this.publicStatus = publicStatus;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	public String getFuserId() {
		return fuserId;
	}

	public void setFuserId(String fuserId) {
		this.fuserId = fuserId;
	}

	public String getnCode() {
		return nCode;
	}

	public void setnCode(String nCode) {
		this.nCode = nCode;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public void setNextCheckUserName(String userName) {
		this.userName2 = userName;
	}

	@Override
	public void setNextCheckUserId(String userId) {
		this.fuserId = userId;
	}

	@Override
	public String getNextCheckUserId() {
		return fuserId;
	}

	@Override
	public void setNextCheckKey(String nCode) {
		this.nCode = nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		this.flowStauts = checkStatus;
	}

	@Override
	public String getCheckStauts() {
		return flowStauts;
	}

	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		return fIntentionCode;
	}

	@Override
	public String getBeanCodeField() {
		return "F_INTENTION_CODE";
	}

	@Override
	public Integer getBeanId() {
		return fId;
	}

	@Override
	public String getUserId() {
		return fUser;
	}

	@Override
	public String getNextCheckKey() {
		return nCode;
	}

	@Override
	public String getJoinTable() {
		return "T_PURCHASE_INTENTION_BASIC";
	}

	@Override
	public String getEntryId() {
		return String.valueOf(getfId());
	}
	
}
