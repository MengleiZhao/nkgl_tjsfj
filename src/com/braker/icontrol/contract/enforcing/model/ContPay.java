package com.braker.icontrol.contract.enforcing.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.braker.common.entity.BaseEntityEmpty;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;

/**
 * 合同付款申请基本信息model
 * @author 叶崇晖
 * @createtime 2018-08-20
 * @updatetime 2018-08-20
 */
@Entity
@Table(name = "T_CONT_PAY")
public class ContPay extends BaseEntityEmpty implements EntityDao,CheckEntity {

	@Id
	@Column(name = "T_PAY_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer payId;			//主键ID
	
	@Column(name = "F_PLAN_ID")
	private Integer planId;			//副键ID（关联付款计划）
	
	@Column(name = "T_PAY_CODE")
	private String payCode;			//合同付款申请编号
	
	@Column(name ="F_AMOUNT")
	private String fReceAmount;		//实际付款金额
	
	@Column(name = "F_OPERATOR")
	private String userName;		//申请人
	
	@Column(name = "F_OPERATOR_ID")
	private String userNameID;		//申请人ID
	
	@Column(name = "F_OPERATOR_DEPT_NAME")
	private String depateName;      //申请人部门名称
	
	@Column(name = "F_OPERATOR_DEPT_ID")
	private String depateID;      //申请人部门ID
	
	@Column(name = "F_REQ_TIME")
	private Date reqTime;			//申请时间
	
	@Column(name = "F_PAY_STAUTS")
	private String stauts;			//申请单状态
	
	@Column(name = "F_FLOW_STAUTS")
	private String flowStauts;		//审批状态
	
	@Column(name = "F_USER_NAME2")
	private String userName2;		//下环节处理人姓名
	
	@Column(name = "F_USER_ID")
	private String fuserId;			//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;			//下节点节点编码
	
	@Column(name = "F_CASHIER_TYPE")
	private String cashierType;		//出纳受理状态0未受理1已受理
	
	
	@Transient
	private String fcCode;			//合同编号（流水号）
	
	@Transient
	private String fcTitle;			//合同名称
	
	
	@Transient
	private Integer num;			//序号

	
	
	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public String getStauts() {
		return stauts;
	}

	public void setStauts(String stauts) {
		this.stauts = stauts;
	}

	public String getFlowStauts() {
		return flowStauts;
	}

	public void setFlowStauts(String flowStauts) {
		this.flowStauts = flowStauts;
	}

	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	public String getFUserId() {
		return fuserId;
	}

	public void setFUserId(String fuserId) {
		this.fuserId = fuserId;
	}

	public String getnCode() {
		return nCode;
	}

	public void setnCode(String nCode) {
		this.nCode = nCode;
	}

	public String getFcCode() {
		return fcCode;
	}

	public void setFcCode(String fcCode) {
		this.fcCode = fcCode;
	}

	public String getFcTitle() {
		return fcTitle;
	}

	public void setFcTitle(String fcTitle) {
		this.fcTitle = fcTitle;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getfReceAmount() {
		return fReceAmount;
	}

	public void setfReceAmount(String fReceAmount) {
		this.fReceAmount = fReceAmount;
	}

	public String getCashierType() {
		return cashierType;
	}


	public String getUserNameID() {
		return userNameID;
	}

	public void setUserNameID(String userNameID) {
		this.userNameID = userNameID;
	}

	public String getDepateName() {
		return depateName;
	}

	public void setDepateName(String depateName) {
		this.depateName = depateName;
	}

	public String getDepateID() {
		return depateID;
	}

	public void setDepateID(String depateID) {
		this.depateID = depateID;
	}
	

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	@Transient
	@Override
	public String getJoinTable() {
		return "T_CONT_PAY";
	}

	@Transient
	@Override
	public String getEntryId() {
		return String.valueOf(payId);
	}
	@Override
	public void setNextCheckUserName(String userName) {
		
		this.userName2=userName;
	}
	@Override
	public void setNextCheckUserId(String userId) {
		
		this.fuserId=userId;
	}
	@Override
	public void setNextCheckKey(String nCode) {
		
		this.nCode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.flowStauts=checkStatus;
	}

	@Override
	public String getCheckStauts() {
		
		return flowStauts;
	}
	@Override
	public String getBeanCode() {
		
		return payCode;
	}
	@Override
	public Integer getBeanId() {
		
		return payId;
	}

	@Override
	public String getNextCheckKey() {
		
		return nCode;
	}

	@Override
	public void setCashierType(String status) {
		
		this.cashierType = status;
	}

	@Override
	public String getUserId() {
		
		return userNameID;
	}

	@Override
	public String getBeanCodeField() {
		
		return "T_PAY_CODE";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fuserId;
	}
	
	
	
}
