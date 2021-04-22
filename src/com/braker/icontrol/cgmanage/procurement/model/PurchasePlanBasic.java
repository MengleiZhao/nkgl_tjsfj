package com.braker.icontrol.cgmanage.procurement.model;

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
 * 采购计划实体类
 * 
 * @author wanping
 *
 */
@Entity
@Table(name = "T_PURCHASE_PLAN_BASIC")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchasePlanBasic extends BaseEntity implements EntityDao,CheckEntity {
	@Id
	@Column(name ="F_ID")
	private Integer fId;				//主键
	
	@Column(name = "F_PURCHASE_PLAN_CODE")
	private String fPurchasePlanCode;	    //采购计划编号
	
	@Column(name = "F_PURCHASE_CODE")
	private String fPurchaseCode;	    //采购项目编号
	
	@Column(name = "F_PURCHASE_NAME")
	private String fPurchaseName;	    //采购项目名称
	
	@Column(name = "F_PURCHASE_WAY")
	private String fPurchaseWay;	    //采购方式
	
	@Column (name ="F_AMOUNT")
	private Double amount;			    //预算金额
	
	@Column(name = "F_AGENCY")
	private String agency;	            //代理机构
	
	@Column(name = "F_PROJECT_LEADER_ID")
	private String projectLeaderId;	    //项目负责人ID
	
	@Column(name = "F_PROJECT_LEADER_NAME")
	private String projectLeaderName;	//项目负责人名称
	
	@Column(name = "F_AUTHORIZED_ID")
	private String authorizedId;	    //项目经办人ID
	
	@Column(name = "F_AUTHORIZED_NAME")
	private String authorizedName;	    //项目经办人名称
	
	@Column(name = "F_PROCESS_LEADER_ID")
	private String processLeaderId;	    //采购流程负责人ID
	
	@Column(name = "F_PROCESS_LEADER_NAME")
	private String processLeaderName;	//采购流程负责人名称
	
	@Column (name ="F_USER")
	private String fUser;				//申报人id
	
	@Column (name ="F_USER_NAME")
	private String fUserName;			//申报人
	
	@Column (name ="F_DEPT_ID")
	private String fDeptId;				//申报部门id
	
	@Column (name ="F_DEPT_NAME")
	private String fDeptName;			//申报部门
	
	@Column (name="F_REQ_TIME")
	private Date fReqTime;				//申请时间
	
	@Column(name = "F_FLOW_STAUTS")
	private String flowStauts;		    //审批状态
	
	@Column (name ="F_STAUTS")
	private String stauts;             //有效状态 99-删除 
	
	@Column(name = "F_USER_NAME2")
	private String userName2;			//下环节处理人姓名
	
	@Column(name = "F_USER_ID")
	private String fuserId;			    //下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;				//下节点节点编码
	
	@Transient
	private Integer num;			    //序号(数据库中没有)

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getfPurchasePlanCode() {
		return fPurchasePlanCode;
	}

	public void setfPurchasePlanCode(String fPurchasePlanCode) {
		this.fPurchasePlanCode = fPurchasePlanCode;
	}

	public String getfPurchaseCode() {
		return fPurchaseCode;
	}

	public void setfPurchaseCode(String fPurchaseCode) {
		this.fPurchaseCode = fPurchaseCode;
	}

	public String getfPurchaseName() {
		return fPurchaseName;
	}

	public void setfPurchaseName(String fPurchaseName) {
		this.fPurchaseName = fPurchaseName;
	}

	public String getfPurchaseWay() {
		return fPurchaseWay;
	}

	public void setfPurchaseWay(String fPurchaseWay) {
		this.fPurchaseWay = fPurchaseWay;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getProjectLeaderId() {
		return projectLeaderId;
	}

	public void setProjectLeaderId(String projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}

	public String getProjectLeaderName() {
		return projectLeaderName;
	}

	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}

	public String getAuthorizedId() {
		return authorizedId;
	}

	public void setAuthorizedId(String authorizedId) {
		this.authorizedId = authorizedId;
	}

	public String getAuthorizedName() {
		return authorizedName;
	}

	public void setAuthorizedName(String authorizedName) {
		this.authorizedName = authorizedName;
	}

	public String getProcessLeaderId() {
		return processLeaderId;
	}

	public void setProcessLeaderId(String processLeaderId) {
		this.processLeaderId = processLeaderId;
	}

	public String getProcessLeaderName() {
		return processLeaderName;
	}

	public void setProcessLeaderName(String processLeaderName) {
		this.processLeaderName = processLeaderName;
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

	public String getfDeptId() {
		return fDeptId;
	}

	public void setfDeptId(String fDeptId) {
		this.fDeptId = fDeptId;
	}

	public String getfDeptName() {
		return fDeptName;
	}

	public void setfDeptName(String fDeptName) {
		this.fDeptName = fDeptName;
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
		return fPurchasePlanCode;
	}

	@Override
	public String getBeanCodeField() {
		return "F_PURCHASE_PLAN_CODE";
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
		return "T_PURCHASE_PLAN_BASIC";
	}

	@Override
	public String getEntryId() {
		return String.valueOf(getfId());
	}

}
