package com.braker.icontrol.incomemanage.disbursement.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;

@Entity
@Table(name = "T_SCHEDULE_PRO_LIST")
public class ScheduleProList extends BaseEntity{
	
	@Id
	@Column(name = "T_SCHEDULE_PRO_LIST_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer splId;			//主键ID

	@Column(name="T_SCHEDULE_ID")
	private Integer sId;				//申请单ID
	
	@Column(name = "F_PRO_ID")
	private Integer fProId;				//项目ID
	
	@Column(name = "F_PRO_NAME")
	private String fProName;			//项目名称
	
	@Column(name = "F_PRO_CODE")
	private String fProCode;			//项目编号
	
	@Column(name = "F_PF_AMOUNT")
	private BigDecimal pfAmount;//项目预算金额
	
	@Column(name = "F_DEPT_ID")
	private String deptId;			//所属部门id
	
	@Column(name = "F_DEPT_NAME")
	private String deptName;		//所属部门名称
	
	@Column(name="F_FIRST_AMOUNT")
	private BigDecimal firstAmount;		//第一季度额度
	
	@Column(name="F_TWO_AMOUNT")
	private BigDecimal twoAmount;		//第二季度额度
	
	@Column(name="F_THREE_AMOUNT")
	private BigDecimal threeAmount;		//第三季度额度
	
	@Column(name="F_FOUR_AMOUNT")
	private BigDecimal fourAmount;		//第四季度额度
	
	@Transient
	private Integer pageOrder;				//序号
	
	@Transient
	private String applyYear;		//申报年度
	
	@Transient
	private BigDecimal pfAmountTotal;//项目预算金额总和
	
	@Transient
	private BigDecimal firstAmountTotal;		//第一季度额度总和
	
	@Transient
	private BigDecimal twoAmountTotal;		//第二季度额度总和
	
	@Transient
	private BigDecimal threeAmountTotal;		//第三季度额度总和
	
	@Transient
	private BigDecimal fourAmountTotal;		//第四季度额度总和

	public Integer getSplId() {
		return splId;
	}

	public void setSplId(Integer splId) {
		this.splId = splId;
	}

	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	public Integer getfProId() {
		return fProId;
	}

	public void setfProId(Integer fProId) {
		this.fProId = fProId;
	}

	public String getfProName() {
		return fProName;
	}

	public void setfProName(String fProName) {
		this.fProName = fProName;
	}

	public String getfProCode() {
		return fProCode;
	}

	public void setfProCode(String fProCode) {
		this.fProCode = fProCode;
	}


	public BigDecimal getPfAmount() {
		return pfAmount;
	}

	public void setPfAmount(BigDecimal pfAmount) {
		this.pfAmount = pfAmount;
	}

	public BigDecimal getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(BigDecimal firstAmount) {
		this.firstAmount = firstAmount;
	}

	public BigDecimal getTwoAmount() {
		return twoAmount;
	}

	public void setTwoAmount(BigDecimal twoAmount) {
		this.twoAmount = twoAmount;
	}

	public BigDecimal getThreeAmount() {
		return threeAmount;
	}

	public void setThreeAmount(BigDecimal threeAmount) {
		this.threeAmount = threeAmount;
	}

	public BigDecimal getFourAmount() {
		return fourAmount;
	}

	public void setFourAmount(BigDecimal fourAmount) {
		this.fourAmount = fourAmount;
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

	public Integer getPageOrder() {
		return pageOrder;
	}

	public void setPageOrder(Integer pageOrder) {
		this.pageOrder = pageOrder;
	}

	public String getApplyYear() {
		return applyYear;
	}

	public void setApplyYear(String applyYear) {
		this.applyYear = applyYear;
	}

	public BigDecimal getPfAmountTotal() {
		return pfAmountTotal;
	}

	public void setPfAmountTotal(BigDecimal pfAmountTotal) {
		this.pfAmountTotal = pfAmountTotal;
	}

	public BigDecimal getFirstAmountTotal() {
		return firstAmountTotal;
	}

	public void setFirstAmountTotal(BigDecimal firstAmountTotal) {
		this.firstAmountTotal = firstAmountTotal;
	}

	public BigDecimal getTwoAmountTotal() {
		return twoAmountTotal;
	}

	public void setTwoAmountTotal(BigDecimal twoAmountTotal) {
		this.twoAmountTotal = twoAmountTotal;
	}

	public BigDecimal getThreeAmountTotal() {
		return threeAmountTotal;
	}

	public void setThreeAmountTotal(BigDecimal threeAmountTotal) {
		this.threeAmountTotal = threeAmountTotal;
	}

	public BigDecimal getFourAmountTotal() {
		return fourAmountTotal;
	}

	public void setFourAmountTotal(BigDecimal fourAmountTotal) {
		this.fourAmountTotal = fourAmountTotal;
	}
	
}