package com.braker.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntityEmpty;
import com.braker.common.entity.GenericEntityNow;

@Entity
@Table(name = "USER_SCHEDULE")
public class UserSchedule extends BaseEntityEmpty{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "F_USERID_ID")
	private Integer f_userid;
	
	@Column(name = "F_MAIN_USERID_ID")
	private String fmainuserid;//主用户的id
	
	@Column(name = "F_OPERATOR")
	private String fOperator;//关联人名称
	
	@Column(name ="F_OPERATOR_ID")
	private String fOperatorId;//关联人id

	@Column(name ="F_DEPT_NAME")
	private String fDeptName;//关联人部门名称
	
	@Column(name ="F_DEPT_ID")
	private String fDeptId;//关联部门id

	@Transient
	private int number;//序号
	
	
	
	public String getFmainuserid() {
		return fmainuserid;
	}

	public void setFmainuserid(String fmainuserid) {
		this.fmainuserid = fmainuserid;
	}

	public Integer getF_userid() {
		return f_userid;
	}

	public void setF_userid(Integer f_userid) {
		this.f_userid = f_userid;
	}

	public String getfOperator() {
		return fOperator;
	}

	public void setfOperator(String fOperator) {
		this.fOperator = fOperator;
	}

	public String getfOperatorId() {
		return fOperatorId;
	}

	public void setfOperatorId(String fOperatorId) {
		this.fOperatorId = fOperatorId;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
	
}
