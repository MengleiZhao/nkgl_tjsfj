package com.braker.icontrol.assets.returns.model;

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
 * 资产交回单
 * @author wanping
 *
 */
@Entity
@Table(name ="T_ASSET_RETURN")
public class AssetReturn extends BaseEntity implements EntityDao,CheckEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_ID")
	private Integer fId_A;
	
	@Column(name ="F_ASS_RETURN_CODE")
	private String fAssReturnCode;//资产交回单
	
	@Column(name ="F_ASS_TYPE")
	private String fAssType;//资产分类 ZCLX-01-低值易耗品，ZCLX-02-固定资产，ZCLX-03-无形资产
	
	@Column(name ="F_ASSET_TYPE")
	private String fAssetType;//取得方式 新购、调拨、接受捐赠、置换、盘盈、其他
	
	@Column(name ="F_ACP_CODE")
	private String fAcpCode;//验收单单号
	
	@Column(name ="F_RETURN_OPERATOR")
	private String fReturnOperator;//交回人
	
	@Column(name ="F_RETURN_OPERATOR_ID")
	private String fReturnOperatorId;//交回人ID
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_RETURN_TIME")
	private Date fReturnTime;//交回日期
	
	
	@Column(name ="F_OPERATOR_ID")
	private String fOperatorId;//申请人ID
	
	@Column(name ="F_OPERATOR")
	private String fOperator;//申请人
	
	@Column(name ="F_DEPT_ID")
	private String fDeptId;//申请部门ID
	
	@Column(name ="F_DEPT_NAME")
	private String fDeptName;//申请部门
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_REQ_TIME")
	private Date fReqTime;//申请日期
	
	@Column(name ="F_REMARK")
	private String fRemark;//交回说明
	
	@Column(name ="F_ACCEPT_STAUTS")
	private String fAcceptStauts;//登记状态 0-未登记 1-已登记
	
	@Column(name ="F_RETURN_STAUTS")
	private String fReturnStauts;//交回单状态 1-正常 99-删除
	
	@Column(name ="F_FLOW_STAUTS")
	private String fFlowStauts_A;//审批状态 0：暂存 1：提交，待审核 -1：已退回（所有流程如驳回，则直接回退至申请人） 2：审核中（2->8 系统支持8级审批） 9：已审核
	
	@Column(name ="F_NEXT_USER_NAME")
	private String fNextUserName;//下环节处理人 姓名
	
	@Column(name ="F_NEXT_USER_CODE")
	private String fNextUserCode;//下环节处理人 编码
	
	@Column(name ="F_NEXT_CODE")
	private String fNextCode;//下节点节点编码

	@Transient
	private Integer number;//序号
	
	@Transient
	private String fAssName;//资产名称
	
	@Transient
	private String fAssNameAll;//资产名称（合）
	
	@Transient
	private Date fRealityReturnTime;//实际交回日期
	
	public Date getfRealityReturnTime() {
		return fRealityReturnTime;
	}

	public void setfRealityReturnTime(Date fRealityReturnTime) {
		this.fRealityReturnTime = fRealityReturnTime;
	}

	public Integer getfId_A() {
		return fId_A;
	}

	public void setfId_A(Integer fId_A) {
		this.fId_A = fId_A;
	}

	public String getfAssReturnCode() {
		return fAssReturnCode;
	}

	public void setfAssReturnCode(String fAssReturnCode) {
		this.fAssReturnCode = fAssReturnCode;
	}

	public String getfAssType() {
		return fAssType;
	}

	public void setfAssType(String fAssType) {
		this.fAssType = fAssType;
	}

	public String getfAssetType() {
		return fAssetType;
	}

	public void setfAssetType(String fAssetType) {
		this.fAssetType = fAssetType;
	}

	public String getfAcpCode() {
		return fAcpCode;
	}

	public void setfAcpCode(String fAcpCode) {
		this.fAcpCode = fAcpCode;
	}

	public String getfReturnOperator() {
		return fReturnOperator;
	}

	public void setfReturnOperator(String fReturnOperator) {
		this.fReturnOperator = fReturnOperator;
	}

	public String getfReturnOperatorId() {
		return fReturnOperatorId;
	}

	public void setfReturnOperatorId(String fReturnOperatorId) {
		this.fReturnOperatorId = fReturnOperatorId;
	}

	public Date getfReturnTime() {
		return fReturnTime;
	}

	public void setfReturnTime(Date fReturnTime) {
		this.fReturnTime = fReturnTime;
	}

	public String getfOperatorId() {
		return fOperatorId;
	}

	public void setfOperatorId(String fOperatorId) {
		this.fOperatorId = fOperatorId;
	}

	public String getfOperator() {
		return fOperator;
	}

	public void setfOperator(String fOperator) {
		this.fOperator = fOperator;
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

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	public String getfAcceptStauts() {
		return fAcceptStauts;
	}

	public void setfAcceptStauts(String fAcceptStauts) {
		this.fAcceptStauts = fAcceptStauts;
	}

	public String getfReturnStauts() {
		return fReturnStauts;
	}

	public void setfReturnStauts(String fReturnStauts) {
		this.fReturnStauts = fReturnStauts;
	}

	public String getfFlowStauts_A() {
		return fFlowStauts_A;
	}

	public void setfFlowStauts_A(String fFlowStauts_A) {
		this.fFlowStauts_A = fFlowStauts_A;
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

	public String getfAssName() {
		return fAssName;
	}

	public void setfAssName(String fAssName) {
		this.fAssName = fAssName;
	}

	public String getfAssNameAll() {
		return fAssNameAll;
	}

	public void setfAssNameAll(String fAssNameAll) {
		this.fAssNameAll = fAssNameAll;
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
	public String getNextCheckUserId() {
		
		return fNextUserCode;
	}

	@Override
	public void setNextCheckKey(String nCode) {
		
		this.fNextCode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.fFlowStauts_A=checkStatus;
	}

	@Override
	public String getCheckStauts() {
		
		return fFlowStauts_A;
	}

	@Override
	public void setStauts(String status) {
		
		this.fReturnStauts=status;
	}

	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		
		return fAssReturnCode;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_ASS_RETURN_CODE";
	}

	@Override
	public Integer getBeanId() {
		
		return fId_A;
	}

	@Override
	public String getUserId() {
		
		return fOperatorId;
	}

	@Override
	public String getNextCheckKey() {
		
		return fNextCode;
	}

	@Override
	public String getJoinTable() {
		
		return "T_ASSET_RETURN";
	}

	@Override
	public String getEntryId() {
		
		return String.valueOf(fId_A);
	}
}
