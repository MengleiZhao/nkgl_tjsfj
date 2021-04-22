package com.braker.icontrol.cgmanage.cgexpert.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.braker.common.entity.BaseEntityEmpty;
import com.braker.common.entity.CheckEntity;



/**
 * 专家出库的model
 * @author 焦广兴
 * @createtime 2019-06-03
 * @updatetime 2019-06-03
 */
@Entity
@Table(name="T_EXPERT_OUT")
public class ExpertOutInfo extends BaseEntityEmpty implements CheckEntity{
	
	@Id
	@Column(name = "F_O_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)   //主键ID
	private Integer foId;
	
	@Column(name = "F_O_CODE")
	private String foCode;			//出库编号
	
	@Column(name = "F_E_ID")	//专家ID 外键
	private Integer feId;

	@Column(name = "F_FLAG")	//出入库 标识  0 默认 ，1出库，2入库
	private String fflag;
	
	@Column(name = "F_USER_NAME")	//下环节处理人姓名
	private String fUserName;
	
	@Column(name = "F_USER_ID")	//下环节处理人Id
	private String fUserId;
	
	@Column(name = "F_N_CODE")	//下环节节点
	private String fNcode;
	
	@Column(name = "F_CHECK_STATUS")	//审核状态
	private String fCheckStatus;
	
	@Column(name = "F_OUT_MSG")	//出库原因
	private String foutMsg;
	
	@Column(name = "F_STATUS")		//数据状态
	private String fStatus;
	
	@Column(name ="F_REC_USER")
	private String fRecUser;//申請人
	
	@Column(name ="F_EXT_1")
	private String fRecUserId;//申请人id
	
	@Column(name ="F_REC_DEPT")
	private String fRecDept;//申请部门
	
	@Column(name ="F_REC_DEPT_ID")
	private String fRecDeptId;//申请部门编码
	
	@Column(name ="F_REQ_TIME")
	private Date fRecTime;//申请时间	
	
	
	
	public String getFoCode() {
		return foCode;
	}

	public void setFoCode(String foCode) {
		this.foCode = foCode;
	}

	public String getFflag() {
		return fflag;
	}

	public void setFflag(String fflag) {
		this.fflag = fflag;
	}

	public String getFoutMsg() {
		return foutMsg;
	}

	public void setFoutMsg(String foutMsg) {
		this.foutMsg = foutMsg;
	}

	public Integer getFoId() {
		return foId;
	}

	public void setFoId(Integer foId) {
		this.foId = foId;
	}

	public Integer getFeId() {
		return feId;
	}

	public void setFeId(Integer feId) {
		this.feId = feId;
	}

	public String getfUserName() {
		return fUserName;
	}

	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	public String getfUserId() {
		return fUserId;
	}

	public void setfUserId(String fUserId) {
		this.fUserId = fUserId;
	}

	public String getfNcode() {
		return fNcode;
	}

	public void setfNcode(String fNcode) {
		this.fNcode = fNcode;
	}

	public String getfCheckStatus() {
		return fCheckStatus;
	}

	public void setfCheckStatus(String fCheckStatus) {
		this.fCheckStatus = fCheckStatus;
	}

	public String getfStatus() {
		return fStatus;
	}

	public void setfStatus(String fStatus) {
		this.fStatus = fStatus;
	}

	public String getfRecUser() {
		return fRecUser;
	}

	public void setfRecUser(String fRecUser) {
		this.fRecUser = fRecUser;
	}

	public String getfRecUserId() {
		return fRecUserId;
	}

	public void setfRecUserId(String fRecUserId) {
		this.fRecUserId = fRecUserId;
	}

	public String getfRecDept() {
		return fRecDept;
	}

	public void setfRecDept(String fRecDept) {
		this.fRecDept = fRecDept;
	}

	public String getfRecDeptId() {
		return fRecDeptId;
	}

	public void setfRecDeptId(String fRecDeptId) {
		this.fRecDeptId = fRecDeptId;
	}

	public Date getfRecTime() {
		return fRecTime;
	}

	public void setfRecTime(Date fRecTime) {
		this.fRecTime = fRecTime;
	}

	@Override
	public void setNextCheckUserName(String userName) {
		
		this.fUserName=userName;
	}

	@Override
	public void setNextCheckUserId(String userId) {
		
		this.fUserId=userId;
	}

	@Override
	public void setNextCheckKey(String nCode) {
		
		this.fNcode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.fCheckStatus=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fCheckStatus;
	}
	@Override
	public void setStauts(String status) {
		
		
	}

	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		
		return foCode;
	}

	@Override
	public Integer getBeanId() {
		
		return foId;
	}

	@Override
	public String getUserId() {
		
		return fRecUserId;
	}

	@Override
	public String getNextCheckKey() {
		
		return fNcode;
	}
	@Override
	public String getJoinTable() {
		return "T_EXPERT_OUT";
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_O_CODE";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fUserId;
	}
}
