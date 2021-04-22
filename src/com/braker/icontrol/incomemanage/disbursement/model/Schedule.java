package com.braker.icontrol.incomemanage.disbursement.model;

import java.math.BigDecimal;
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

@Entity
@Table(name = "T_SCHEDULE")
public class Schedule extends BaseEntity implements EntityDao,CheckEntity{
	
	@Id
	@Column(name = "T_SCHEDULE_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer sId;			//主键ID

	@Column(name = "F_SCHEDULE_CODE")
	private String scheduleCode;	//申请CODE
	
	@Column(name = "F_USER")
	private String user;			//申请人id
	
	@Column(name = "F_USER_NAME")
	private String userName;			//申请人名称
	
	@Column(name = "F_DEPT_ID")
	private String deptId;			//所属部门id
	
	@Column(name = "F_DEPT_NAME")
	private String deptName;		//所属部门名称
	
	@Column(name = "F_APPLY_YEAR")
	private String applyYear;		//申报年度
	
	@Column(name = "F_REQ_TIME")
	private Date reqTime;			//申请时间
	
	@Column(name = "F_FLOW_STAUTS")
	private String flowStauts;		//审批状态
	
	@Column(name = "F_STAUTS")
	private String stauts;			//申请状态
	
	@Column(name = "F_USER_NAME2")
	private String userName2;		//下环节处理人姓名
	
	@Column(name = "F_USER_ID")
	private String fuserId;			//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;			//下节点节点编码
	
	@Column(name = "F_AMOUNT")
	private BigDecimal amount;			//总金额
	
	@Transient
	private Integer num;		//排序使用
	
	@Transient
	private String searchTitle;//查询使用
	
	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
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

	public String getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public String getStauts() {
		return stauts;
	}

	public void setStauts(String stauts) {
		this.stauts = stauts;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
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
		this.flowStauts=checkStatus;
	}

	@Override
	public String getCheckStauts() {
		return flowStauts;
	}

	public String getApplyYear() {
		return applyYear;
	}

	public void setApplyYear(String applyYear) {
		this.applyYear = applyYear;
	}

	@Override
	public void setCashierType(String status) {
		
	}

	@Override
	public String getBeanCode() {
		return scheduleCode;
	}

	@Override
	public String getBeanCodeField() {
		return "F_SCHEDULE_CODE";
	}

	@Override
	public Integer getBeanId() {
		return sId;
	}

	@Override
	public String getUserId() {
		return user;
	}

	@Override
	public String getNextCheckKey() {
		return nCode;
	}

	@Override
	public String getJoinTable() {
		return "T_SCHEDULE";
	}

	@Override
	public String getEntryId() {
		return String.valueOf(getsId());
	}
}
