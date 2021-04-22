package com.braker.icontrol.cgmanage.procurement.model;

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

/**
 * 采购需求申请基本信息的model类
 * @author 方淳洲
 * @createtime 2021-03-13
 * @updatetime 2021-03-13
 */

@Entity
@Table(name = "T_PROCUREMENT_NEEDS_INFO")
public class ProcurementNeedsInfo extends BaseEntity implements EntityDao,CheckEntity{
	
	@Id
	@Column(name = "F_P_ID")
	private Integer pId;			//主键ID
	
	@Column(name = "F_P_CODE")
	private String pCode;			//需求批次编号
	
	@Column(name = "F_CG_ID")
	private Integer cgId;			//采购批次Id
	
	@Column(name = "F_CG_CODE")
	private String cgCode;			//采购批次编号
	
	@Column(name = "F_CG_NAME")
	private String cgName;			//采购项目名
	
	@Column(name = "F_CG_AMOUNT")
	private String cgAmount;		//采购金额
	
	@Column(name = "F_CG_METHOD")
	private String cgMethod;			//采购类型
	
	@Column(name = "F_CG_TYPE")
	private String cgType;			//采购方式
	
	@Column(name = "F_CG_USER_ID")
	private String cgUserId;			//采购申请人id
	
	@Column(name = "F_CG_USER_NAME")
	private String cgUserName;			//采购申请人名字
	
	@Column(name = "F_CG_DEPT_ID")
	private String cgDeptId;			//采购申请人处室id
	
	@Column(name = "F_CG_DEPT_NAME")
	private String cgDeptName;			//采购申请人处室名字
	
	@Column(name = "F_XQ_USER_ID")
	private String xqUserId;			//需求申请人id
	
	@Column(name = "F_XQ_USER_NAME")
	private String xqUserName;			//需求申请人名字
	
	@Column(name = "F_XQ_DEPT_ID")
	private String xqDeptId;			//需求申请人处室id
	
	@Column(name = "F_XQ_DEPT_NAME")
	private String xqDeptName;			//需求申请人处室名字
	
	@Column(name = "F_PRINCIPAL_ID")
	private String principalId;		//采购申请人处室负责人id
	
	@Column(name = "F_PRINCIPAL_NAME")
	private String principalName;		//采购申请人处室负责人名字
	
	@Column(name = "F_AUTHORIZED")
	private String authorized;			//授权代表
	
	@Column(name = "F_FLOWSTATUS")
	private String flowStatus;			//审批状态
	
	@Column(name = "F_STATUS")
	private String status;			//采购需求申请单状态
	
	@Column(name = "F_NEXT_USER_NAME")
	private String userName;		//下环节处理人姓名
	
	@Column(name = "F_NEXT_USER_ID")
	private String fuserId;			//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;			//下节点节点编码
	
	@Transient
	private int number;					//序号(数据库里没有的)
	
	
	
	

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public Integer getCgId() {
		return cgId;
	}

	public void setCgId(Integer cgId) {
		this.cgId = cgId;
	}

	public String getCgCode() {
		return cgCode;
	}

	public void setCgCode(String cgCode) {
		this.cgCode = cgCode;
	}

	public String getCgName() {
		return cgName;
	}

	public void setCgName(String cgName) {
		this.cgName = cgName;
	}

	public String getCgAmount() {
		return cgAmount;
	}

	public void setCgAmount(String cgAmount) {
		this.cgAmount = cgAmount;
	}

	public String getCgMethod() {
		return cgMethod;
	}

	public void setCgMethod(String cgMethod) {
		this.cgMethod = cgMethod;
	}

	public String getCgType() {
		return cgType;
	}

	public void setCgType(String cgType) {
		this.cgType = cgType;
	}

	public String getCgUserId() {
		return cgUserId;
	}

	public void setCgUserId(String cgUserId) {
		this.cgUserId = cgUserId;
	}

	public String getCgUserName() {
		return cgUserName;
	}

	public void setCgUserName(String cgUserName) {
		this.cgUserName = cgUserName;
	}

	public String getCgDeptId() {
		return cgDeptId;
	}

	public void setCgDeptId(String cgDeptId) {
		this.cgDeptId = cgDeptId;
	}

	public String getCgDeptName() {
		return cgDeptName;
	}

	public void setCgDeptName(String cgDeptName) {
		this.cgDeptName = cgDeptName;
	}

	public String getXqUserId() {
		return xqUserId;
	}

	public void setXqUserId(String xqUserId) {
		this.xqUserId = xqUserId;
	}

	public String getXqUserName() {
		return xqUserName;
	}

	public void setXqUserName(String xqUserName) {
		this.xqUserName = xqUserName;
	}

	public String getXqDeptId() {
		return xqDeptId;
	}

	public void setXqDeptId(String xqDeptId) {
		this.xqDeptId = xqDeptId;
	}

	public String getXqDeptName() {
		return xqDeptName;
	}

	public void setXqDeptName(String xqDeptName) {
		this.xqDeptName = xqDeptName;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getAuthorized() {
		return authorized;
	}

	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public void setNextCheckUserName(String userName) {
		this.userName = userName;
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
		this.flowStatus = checkStatus;
	}

	@Override
	public String getCheckStauts() {
		return flowStatus;
	}

	@Override
	public void setStauts(String status) {
		this.status = status;
	}

	@Override
	public void setCashierType(String status) {
		
	}

	@Override
	public String getBeanCode() {
		return pCode;
	}

	@Override
	public String getBeanCodeField() {
		return "F_P_CODE";
	}

	@Override
	public Integer getBeanId() {
		return pId;
	}

	@Override
	public String getUserId() {
		return xqUserId;
	}

	@Override
	public String getNextCheckKey() {
		return nCode;
	}

	@Override
	public String getJoinTable() {
		return "T_PROCUREMENT_NEEDS_INFO";
	}

	@Override
	public String getEntryId() {
		return String.valueOf(pId);
	}

}
