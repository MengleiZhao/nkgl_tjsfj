package com.braker.icontrol.incomemanage.capital.model;

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
import com.braker.common.entity.EntityDao;

/**
 * 额度管理的model
 * 
 * @author 沈帆
 * @createtime 2021-02-20
 * @updatetime 2021-02-20
 */
@Entity
@Table(name = "T_QUOTA_MANAGE_INFO")
public class QuotaMangeInfo extends BaseEntity implements EntityDao{
	@Id
	@Column(name = "F_Q_ID")
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer fQId;			//主键ID、
	
	@Column(name = "F_AMOUNT")
	private BigDecimal fAmount;				//金额
	
	@Column(name = "F_OPERATE_USER")
	private String fOperateUser;				//录入人员
	
	@Column(name = "F_OPERATE_TIME")
	private Date fOperateTime;				//录入日期
	
	@Column(name = "F_STAUTS")
	private String fStauts;				//删除状态
	
	@Column(name = "F_CHECK_STAUTS")
	private String fCheckStauts;				//复核状态
	
	@Column(name = "F_CHECK_USER")
	private String fCheckUser;				//复核人
	
	@Column(name = "F_CHECK_TIME")
	private Date fChcekTime;				//复核日期
	
	@Column(name = "F_SUB_CODE")
	private String fSubCode;				//经济分类编码
	
	@Column(name = "F_SUB_NAME")
	private String fSubName;				//经济分类名称
	
	@Transient
	private Integer num;			//序号(数据库中没有)
	
	@Transient
	private BigDecimal skAmount;				//收款额度
	
	@Transient
	private BigDecimal bxAmount;				//报销额度

	public Integer getfQId() {
		return fQId;
	}

	public void setfQId(Integer fQId) {
		this.fQId = fQId;
	}

	public BigDecimal getfAmount() {
		return fAmount;
	}

	public void setfAmount(BigDecimal fAmount) {
		this.fAmount = fAmount;
	}

	public String getfOperateUser() {
		return fOperateUser;
	}

	public void setfOperateUser(String fOperateUser) {
		this.fOperateUser = fOperateUser;
	}

	public Date getfOperateTime() {
		return fOperateTime;
	}

	public void setfOperateTime(Date fOperateTime) {
		this.fOperateTime = fOperateTime;
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

	public String getfCheckUser() {
		return fCheckUser;
	}

	public void setfCheckUser(String fCheckUser) {
		this.fCheckUser = fCheckUser;
	}

	public Date getfChcekTime() {
		return fChcekTime;
	}

	public void setfChcekTime(Date fChcekTime) {
		this.fChcekTime = fChcekTime;
	}

	public String getfSubCode() {
		return fSubCode;
	}

	public void setfSubCode(String fSubCode) {
		this.fSubCode = fSubCode;
	}


	public String getfSubName() {
		return fSubName;
	}

	public void setfSubName(String fSubName) {
		this.fSubName = fSubName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public String getJoinTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEntryId() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getSkAmount() {
		return skAmount;
	}

	public void setSkAmount(BigDecimal skAmount) {
		this.skAmount = skAmount;
	}

	public BigDecimal getBxAmount() {
		return bxAmount;
	}

	public void setBxAmount(BigDecimal bxAmount) {
		this.bxAmount = bxAmount;
	}

	
	
	


	

	
	
	
}
