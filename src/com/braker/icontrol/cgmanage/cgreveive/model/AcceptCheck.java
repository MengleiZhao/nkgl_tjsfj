package com.braker.icontrol.cgmanage.cgreveive.model;

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

/**
 * 采购验收的model
 * @author 冉德茂
 * @createtime 2018-07-17
 * @updatetime 2018-07-17
 */

@Entity
@Table(name="T_ACCEPT_CHECK")
public class AcceptCheck extends BaseEntity implements EntityDao,CheckEntity{
	
	@Id
	@Column(name = "F_ACP_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)   
	private Integer facpId;							//主键ID
	
	@Column(name = "F_ACP_CODE")
	private String facpCode;						//验收单编号
	
	@Column(name ="F_P_ID")							
	private Integer fpId;							//外键ID  链接T_PURCHASE_APPLY_BASIC (F_P_ID);
	
	@Column(name = "F_P_CODE")
	private String fpCode;							//采购批次号（读取配置计划的单据编号）
	
	@Column(name = "F_P_NAME")
	private String fpName;							//采购名称
	
	@Column (name ="F_P_ITEMS_NAME")
	private String fpItemsName;						//品目名称    6.10添加
	
	@Column (name ="F_AMOUNT")
	private Double amount;							//采购金额
	
	@Column (name ="F_DEPT_ID")
	private String fDepartId;							//验收部门id
	
	@Column (name ="F_DEPT_NAME")
	private String fDepartName;						//验收部门名称
	
	@Column(name="F_ACP_USER_ID")
	private String facpUserId;						//验收人
	
	@Column(name="F_ACP_USERNAME")
	private String facpUsername;					//验收人姓名
	
	@Column(name = "F_ACP_TIME")
	private Date facpTime;							//验收时间
	
	@Column(name = "F_ACP_ADDR")
	private String facpAddr;						//验收地点	
	
	@Column(name = "F_QUALITY_IS_OK")
	private String fqualityIsOk;					//质量是否合格
	
	@Column(name = "F_IS_MATCH")
	private String fisMatch;						//验收合格
	
	@Column(name = "F_QUALITY_REMARK")
	private String fqualityRemark;					//质量说明

	@Column(name = "F_MATCH_REMARK")
	private String fmatchRemark;					//验收意见
	
	@Column (name ="F_STAUTS")
	private String fStauts;							//删除状态
	
	@Column (name ="F_MATCH_STAUTS")
	private String fMatchStauts;					//验收状态  1：已验收    0：未验收
	//审批
	@Column (name ="F_CHECK_STAUTS")
	private String fCheckStauts;					//验收审批状态
	
	@Column(name = "F_USER_NAME2")
	private String userName2;						//下环节处理人姓名
	
	@Column(name = "F_USER_CODE")
	private String fuserId;							//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;							//下节点节点编码
	
	@Column(name ="F_CONT_ID")
	private Integer fcId;							//合同Id
	
	@Column(name ="F_CONT_CODE")
	private String fcCode;							//合同编号（流水号）
	
	@Column(name="F_CONT_TITLE")
	private String fcTitle;							//合同名称
	
	@Column(name ="F_UPT_ID")
	private Integer fId_U;							//变更合同Id
	
	@Column(name ="F_BILLING_STAUTS")
	private Integer fBillingStauts;					//入账状态
	
	@Transient
	private String fpItemsNames;		            //品目名称（用于页面显示）
	
	@Transient
	private Integer num;							//序号(数据库中没有)
	
	@Transient
	private String cname;							//商品名称(数据库中没有)
	
	
	public Integer getfBillingStauts() {
		return fBillingStauts;
	}

	public void setfBillingStauts(Integer fBillingStauts) {
		this.fBillingStauts = fBillingStauts;
	}

	public Integer getfId_U() {
		return fId_U;
	}

	public void setfId_U(Integer fId_U) {
		this.fId_U = fId_U;
	}

	public String getfMatchStauts() {
		return fMatchStauts;
	}

	public void setfMatchStauts(String fMatchStauts) {
		this.fMatchStauts = fMatchStauts;
	}

	public String getFqualityRemark() {
		return fqualityRemark;
	}

	public void setFqualityRemark(String fqualityRemark) {
		this.fqualityRemark = fqualityRemark;
	}

	public Integer getFacpId() {
		return facpId;
	}

	public void setFacpId(Integer facpId) {
		this.facpId = facpId;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public Date getFacpTime() {
		return facpTime;
	}

	public void setFacpTime(Date facpTime) {
		this.facpTime = facpTime;
	}

	public String getFacpAddr() {
		return facpAddr;
	}

	public void setFacpAddr(String facpAddr) {
		this.facpAddr = facpAddr;
	}

	public String getFqualityIsOk() {
		return fqualityIsOk;
	}

	public void setFqualityIsOk(String fqualityIsOk) {
		this.fqualityIsOk = fqualityIsOk;
	}

	public String getFisMatch() {
		return fisMatch;
	}

	public void setFisMatch(String fisMatch) {
		this.fisMatch = fisMatch;
	}

	public String getFmatchRemark() {
		return fmatchRemark;
	}

	public void setFmatchRemark(String fmatchRemark) {
		this.fmatchRemark = fmatchRemark;
	}

	public String getFacpCode() {
		return facpCode;
	}

	public void setFacpCode(String facpCode) {
		this.facpCode = facpCode;
	}

	public String getfDepartId() {
		return fDepartId;
	}

	public void setfDepartId(String fDepartId) {
		this.fDepartId = fDepartId;
	}

	public String getfDepartName() {
		return fDepartName;
	}

	public void setfDepartName(String fDepartName) {
		this.fDepartName = fDepartName;
	}

	public String getFacpUserId() {
		return facpUserId;
	}

	public void setFacpUserId(String facpUserId) {
		this.facpUserId = facpUserId;
	}

	public String getFacpUsername() {
		return facpUsername;
	}

	public void setFacpUsername(String facpUsername) {
		this.facpUsername = facpUsername;
	}

	public String getfStauts() {
		return fStauts;
	}

	public void setfStauts(String fStauts) {
		this.fStauts = fStauts;
	}

	public String getfCheckStauts() {
		return fCheckStauts;
	}

	public void setfCheckStauts(String fCheckStauts) {
		this.fCheckStauts = fCheckStauts;
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

	
	public String getFpCode() {
		return fpCode;
	}

	public void setFpCode(String fpCode) {
		this.fpCode = fpCode;
	}

	public String getFpName() {
		return fpName;
	}

	public void setFpName(String fpName) {
		this.fpName = fpName;
	}

	public String getFpItemsName() {
		return fpItemsName;
	}

	public void setFpItemsName(String fpItemsName) {
		this.fpItemsName = fpItemsName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFpItemsNames() {
		return fpItemsNames;
	}

	public void setFpItemsNames(String fpItemsNames) {
		this.fpItemsNames = fpItemsNames;
	}

	public Integer getFcId() {
		return fcId;
	}

	public void setFcId(Integer fcId) {
		this.fcId = fcId;
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

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Transient
	@Override
	public String getJoinTable() {
		
		return "T_ACCEPT_CHECK";
	}

	@Transient
	@Override
	public String getEntryId() {
		
		return String.valueOf(facpId);
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
	public String getNextCheckUserId() {
		
		return fuserId;
	}

	@Override
	public void setNextCheckKey(String nCode) {
		
		this.nCode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.fCheckStauts=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fCheckStauts;
	}
	@Override
	public void setStauts(String status) {
		
		this.fStauts=status;
	}

	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		
		return facpCode;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_ACP_CODE";
	}

	@Override
	public Integer getBeanId() {
		
		return facpId;
	}

	@Override
	public String getUserId() {
		
		return facpUserId;
	}

	@Override
	public String getNextCheckKey() {
		
		return nCode;
	}
	
	
	

}
