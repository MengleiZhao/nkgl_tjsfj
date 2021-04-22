package com.braker.icontrol.cgmanage.cgconfplan.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.BaseEntityEmpty;
import com.braker.icontrol.cgmanage.cginquiries.model.Sel;
/**
 * 采购申请商品清单的model
 * @author 冉德茂
 * @createtime 2018-10-11
 * @updatetime 2018-10-11
 */
@Entity
@Table(name="T_PROCUREMENT_PLAN_LIST")
public class ProcurementPlanList extends BaseEntityEmpty{
	
	@Id
	@Column(name = "F_P_L_ID")
	private Integer mainId;			//主键ID
	
	@Column(name ="F_P_ID")
	private Integer fpId;			//外键ID
	
	@Column(name = "F_KIND")
	private String fpKind;			//采购品目
	
	@Column(name = "F_PUR_CODE")
	private String fpurCode;//采购目录编码
	
	@Column(name = "F_PUR_NAME")
	private String fpurName;//采购目录名称
	
	@Column(name = "F_MEASURE_UNIT")
	private String fmeasureUnit;//计量单位
	
	@Column(name = "F_PUR_BRAND")
	private String fpurBrand;//采购品牌
	
	@Column(name = "F_SPECIF_MODEL")
	private String fspecifModel;//规格型号
	
	@Column(name = "F_NUM")
	private Integer fnum;//数量
	
	@Column(name = "F_UNIT_PRICE")
	private Double funitPrice;//参考单价
	
	@Column(name = "F_SUM_MONEY")
	private Double fsumMoney;//金额合计
	
	@Column(name = "F_COMM_PROP")
	private String fcommProp;//商品属性
	
	@Column(name = "F_NEED_TIME")
	private Date fneedTime;//需求时间
	
	@Column (name ="F_IS_IMP")
	private String fIsImp;				//进口货物服务 0-否，1-是
	
	@Transient
	private Integer num;			//序号(数据库中没有)	
	
	@Transient
	private List<Sel> selList;			//供货商集合(数据库中没有)	

	
	public String getFpKind() {
		return fpKind;
	}

	public void setFpKind(String fpKind) {
		this.fpKind = fpKind;
	}

	public Integer getMainId() {
		return mainId;
	}

	public void setMainId(Integer mainId) {
		this.mainId = mainId;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public String getFpurCode() {
		return fpurCode;
	}

	public void setFpurCode(String fpurCode) {
		this.fpurCode = fpurCode;
	}

	public String getFpurName() {
		return fpurName;
	}

	public void setFpurName(String fpurName) {
		this.fpurName = fpurName;
	}

	public String getFmeasureUnit() {
		return fmeasureUnit;
	}

	public void setFmeasureUnit(String fmeasureUnit) {
		this.fmeasureUnit = fmeasureUnit;
	}

	public String getFpurBrand() {
		return fpurBrand;
	}

	public void setFpurBrand(String fpurBrand) {
		this.fpurBrand = fpurBrand;
	}

	public String getFspecifModel() {
		return fspecifModel;
	}

	public void setFspecifModel(String fspecifModel) {
		this.fspecifModel = fspecifModel;
	}

	public Integer getFnum() {
		return fnum;
	}

	public void setFnum(Integer fnum) {
		this.fnum = fnum;
	}

	public Double getFunitPrice() {
		return funitPrice;
	}

	public void setFunitPrice(Double funitPrice) {
		this.funitPrice = funitPrice;
	}

	public Double getFsumMoney() {
		return fsumMoney;
	}

	public void setFsumMoney(Double fsumMoney) {
		this.fsumMoney = fsumMoney;
	}

	public String getFcommProp() {
		return fcommProp;
	}

	public void setFcommProp(String fcommProp) {
		this.fcommProp = fcommProp;
	}

	public Date getFneedTime() {
		return fneedTime;
	}

	public void setFneedTime(Date fneedTime) {
		this.fneedTime = fneedTime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List<Sel> getSelList() {
		return selList;
	}

	public void setSelList(List<Sel> selList) {
		this.selList = selList;
	}

	public String getfIsImp() {
		return fIsImp;
	}

	public void setfIsImp(String fIsImp) {
		this.fIsImp = fIsImp;
	}

	@Override
	public String toString() {
		return "ProcurementPlanList [mainId=" + mainId + ", fpId=" + fpId
				+ ", fpurCode=" + fpurCode + ", fpurName=" + fpurName
				+ ", fmeasureUnit=" + fmeasureUnit + ", fpurBrand=" + fpurBrand
				+ ", fspecifModel=" + fspecifModel + ", fnum=" + fnum
				+ ", funitPrice=" + funitPrice + ", fsumMoney=" + fsumMoney
				+ ", fcommProp=" + fcommProp + ", fneedTime=" + fneedTime
				+ ", num=" + num + ", getMainId()=" + getMainId()
				+ ", getFpId()=" + getFpId() + ", getFpurCode()="
				+ getFpurCode() + ", getFpurName()=" + getFpurName()
				+ ", getFmeasureUnit()=" + getFmeasureUnit()
				+ ", getFpurBrand()=" + getFpurBrand() + ", getFspecifModel()="
				+ getFspecifModel() + ", getFnum()=" + getFnum()
				+ ", getFunitPrice()=" + getFunitPrice() + ", getFsumMoney()="
				+ getFsumMoney() + ", getFcommProp()=" + getFcommProp()
				+ ", getFneedTime()=" + getFneedTime() + ", getNum()="
				+ getNum() + ", getId()=" + getId() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}
	
	
}
