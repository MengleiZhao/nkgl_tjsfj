package com.braker.icontrol.assets.handle.model;

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
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 固定资产处置清单表
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_ASSET_REGISTRATION")
public class AssetRegistration extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_AR_ID")
	private Integer fARId;
	
	@Column(name ="F_ID")
	private Integer fId;
	
	@Column(name ="F_ASS_ID")
	private Integer fAssId;
	
	@Column(name="F_ASS_HANDLE_CODE")
	private String fAssHandleRegCode;//资产处置登记单号
	
	@Column(name="F_ASS_CODE")
	private String fAssCode;//处置物资编号
	
	@Column(name="F_ASS_NAME")
	private String fAssName;//处置物资名称
	
	@Column(name="F_FIXED_TYPE")
	private String fFixedType;//固定资产分类
	
	@Column(name="F_FIXED_TYPE_ID")
	private String fFixedTypeId;//固定资产分类ID
	
	@Column(name="F_FIXED_TYPE_CODE")
	private String fFixedTypeCode;//固定资产分类CODE
	
	@Column(name="F_FORM")
	private String fForm;//资产来源
	
	@Column(name="F_FORM_SHOW")
	private String fFormShow;//资产来源显示
	
	@Column(name="F_ASS_MODEL")
	private String fAssModel;//规格类型
	
	@Column(name="F_MEAS_UNIT")
	private String fMeasUnit;//计量单位
	
	@Column(name="F_HANDLE_NUM")
	private String fHandleNum;//处置数量
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_ACTION_DATE")
	private Date fActionDate;//取得日期
	
	@Column(name ="F_USED_STAUTS")
	private String fUsedStauts;//使用状态
	
	@Column(name ="F_USED_STAUTS_SHOW")
	private String fUsedStautsShow;//使用状态
	
	@Column(name ="F_OLD_AMOUNT")
	private Double fOldAmount;//账面原值(元)
	
	@Column(name ="F_RESIDUAL_VALUE")
	private Double fResidualValue;//账面净值（元）
	
	@Column(name ="F_DEPRECIATION_AMOUNT")
	private Double fDepreciationAmount;//累计折旧（元）
	
	@Column(name ="F_ASSESS_AMOUNT")
	private Double fAssAmount;//评估价值（元）
	
	@Column(name ="F_REMARK")
	private String fRemarkR;//备注
	
	@Column(name ="F_ADDRESS")
	private String fAddress;//存放地址
	
	@Transient
	private Integer number;//序号
	@Transient
	private Integer contractId;//合同id

	public String getfFormShow() {
		return fFormShow;
	}

	public void setfFormShow(String fFormShow) {
		this.fFormShow = fFormShow;
	}

	public String getfUsedStautsShow() {
		return fUsedStautsShow;
	}

	public void setfUsedStautsShow(String fUsedStautsShow) {
		this.fUsedStautsShow = fUsedStautsShow;
	}

	public String getfFixedTypeId() {
		return fFixedTypeId;
	}

	public Integer getfAssId() {
		return fAssId;
	}

	public void setfAssId(Integer fAssId) {
		this.fAssId = fAssId;
	}

	public void setfFixedTypeId(String fFixedTypeId) {
		this.fFixedTypeId = fFixedTypeId;
	}

	public String getfFixedTypeCode() {
		return fFixedTypeCode;
	}

	public void setfFixedTypeCode(String fFixedTypeCode) {
		this.fFixedTypeCode = fFixedTypeCode;
	}

	public Integer getfARId() {
		return fARId;
	}

	public void setfARId(Integer fARId) {
		this.fARId = fARId;
	}

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getfAssHandleRegCode() {
		return fAssHandleRegCode;
	}

	public void setfAssHandleRegCode(String fAssHandleRegCode) {
		this.fAssHandleRegCode = fAssHandleRegCode;
	}

	public String getfAssCode() {
		return fAssCode;
	}

	public void setfAssCode(String fAssCode) {
		this.fAssCode = fAssCode;
	}

	public String getfAssName() {
		return fAssName;
	}

	public void setfAssName(String fAssName) {
		this.fAssName = fAssName;
	}

	public String getfFixedType() {
		return fFixedType;
	}

	public void setfFixedType(String fFixedType) {
		this.fFixedType = fFixedType;
	}

	public String getfForm() {
		return fForm;
	}

	public void setfForm(String fForm) {
		this.fForm = fForm;
	}

	public String getfAssModel() {
		return fAssModel;
	}

	public void setfAssModel(String fAssModel) {
		this.fAssModel = fAssModel;
	}

	public String getfMeasUnit() {
		return fMeasUnit;
	}

	public void setfMeasUnit(String fMeasUnit) {
		this.fMeasUnit = fMeasUnit;
	}

	public String getfHandleNum() {
		return fHandleNum;
	}

	public void setfHandleNum(String fHandleNum) {
		this.fHandleNum = fHandleNum;
	}

	public Date getfActionDate() {
		return fActionDate;
	}

	public void setfActionDate(Date fActionDate) {
		this.fActionDate = fActionDate;
	}

	public String getfUsedStauts() {
		return fUsedStauts;
	}

	public void setfUsedStauts(String fUsedStauts) {
		this.fUsedStauts = fUsedStauts;
	}

	public Double getfOldAmount() {
		return fOldAmount;
	}

	public void setfOldAmount(Double fOldAmount) {
		this.fOldAmount = fOldAmount;
	}

	public Double getfResidualValue() {
		return fResidualValue;
	}

	public void setfResidualValue(Double fResidualValue) {
		this.fResidualValue = fResidualValue;
	}

	public Double getfDepreciationAmount() {
		return fDepreciationAmount;
	}

	public void setfDepreciationAmount(Double fDepreciationAmount) {
		this.fDepreciationAmount = fDepreciationAmount;
	}

	public Double getfAssAmount() {
		return fAssAmount;
	}

	public void setfAssAmount(Double fAssAmount) {
		this.fAssAmount = fAssAmount;
	}

	public String getfRemarkR() {
		return fRemarkR;
	}

	public void setfRemarkR(String fRemarkR) {
		this.fRemarkR = fRemarkR;
	}

	public String getfAddress() {
		return fAddress;
	}

	public void setfAddress(String fAddress) {
		this.fAddress = fAddress;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	
}
