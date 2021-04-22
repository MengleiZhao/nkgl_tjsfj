package com.braker.icontrol.assets.storage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.braker.common.entity.BaseEntity;
import com.braker.core.model.Lookups;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author 叶崇辉
 *
 */
@Entity
@Table(name ="T_ASSET_REGIST_LIST")
public class Regist extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_LIST_ID")
	private Integer fListId;
	
	@Column(name ="F_ID")
	private Integer fId_S;				//入账单ID
	
	@Column(name = "F_A_C_R_ID")
	private Integer acRId;			//主键ID
	
	@Column(name ="F_CONT_ID")
	private Integer fcId;			//外键合同主表ContractBasicInfo主键ID
	
	@Column(name ="F_CONT_CODE")
	private String fcCode;			//合同编号
	
	@Column(name ="F_UPT_ID")
	private Integer fId_U;			//外键变更合同主表UPT主键ID
	
	@Column(name ="F_ASS_CODE")
	private String fAssCode;//卡片编号
	
	@Column(name ="F_FIXED_TYPE")
	private String fFixedType;//固定资产分类
	
	@Column(name ="F_FIXED_TYPE_ID")
	private String fFixedTypeId;//固定资产分类ID
	
	@Column(name ="F_FIXED_TYPE_CODE")
	private String fFixedTypeCode;//固定资产分类CODE
	
	@Column(name ="F_ASS_TYPE")
	private String fAssType;//资产分类
	
	@Column(name ="F_ASS_NAME")
	private String fAssName;//资产名称
	
	@Column(name ="F_M_MODE")
	private String fMMode;//型号
	
	@Column(name = "F_M_MODEL")
	private String fmModel;//计量单位
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_ACTION_DATE")
	private Date fActionDate;//取得日期
	
	@Column(name ="F_ADMIN_OFFICIAL")
	private String fAdminOfficial;//管理部门
	
	@Column(name ="F_FINANCIAL_CODE")
	private String fFinancialCode;//会计凭证号
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_FINANCIAL_DATE")
	private Date fFinancialDate;//财务入账日期
	
	@ManyToOne
	@JoinColumn(name ="F_VALUE_TYPE" ,referencedColumnName ="lookupscode")
	private Lookups fValueType;//价值类型 ：ZCJZLX-01原值，ZCJZLX-02暂估值，ZCJZLX-03重置值，ZCJZLX-04评估值，ZCJZLX-05名义金额
	
	@ManyToOne
	@JoinColumn(name ="F_DEPRECIATION_STATUS" ,referencedColumnName ="lookupscode")
	private Lookups fDepreciationStatus;//折旧状态 ：ZCZJZT-01提折旧，ZCZJZT-02不提折旧，ZCZJZT-03已完成折旧
	
	@ManyToOne
	@JoinColumn(name ="F_AMORTIZE_STATUS" ,referencedColumnName ="lookupscode")
	private Lookups fAmortizeStatus;//摊销状态 ：TXZT-01提摊销，TXZT-02不提摊销，TXZT-03已完成摊销
	
	@Column(name ="F_AMOUNT")
	private Double amount;//价值（元）
	
	@Column(name ="F_UNAPPROPRIATION_AMOUNT")
	private Double fUnappropriationAmount;//非财政拨款(元)
	
	@Column(name ="F_APPROPRIATION_AMOUNT")
	private Double fAppropriationAmount;//财政拨款(元)
	
	@Column(name ="F_REMARK")
	private String fRemark;//备注
	
	@Transient
	private Integer number;//序号(列表用)
	
	@Transient
	private String fFixedTypes;//固定资产分类(页面显示使用)
	
	@Transient
	private String fDepreciationStatusShow;//折旧状态显示用
	
	@Transient
	private String fValueTypeShow;//价值类型显示用
	
	@Transient
	private String fAmortizeStatusShow;//摊销状态显示用
	
	@Transient
	private String fAdminOfficialShow;//管理部门显示用
	
	@Transient
	private String fAmortizeStatusCode;//摊销状态显示用  code
	
	@Transient
	private String fDepreciationStatusCode;//折旧状态code
	
	@Transient
	private String fValueTypeCode;//价值类型code
	
	public String getFcCode() {
		return fcCode;
	}

	public void setFcCode(String fcCode) {
		this.fcCode = fcCode;
	}

	public Integer getFcId() {
		return fcId;
	}

	public void setFcId(Integer fcId) {
		this.fcId = fcId;
	}

	public Integer getfId_U() {
		return fId_U;
	}

	public void setfId_U(Integer fId_U) {
		this.fId_U = fId_U;
	}

	public String getFmModel() {
		return fmModel;
	}

	public void setFmModel(String fmModel) {
		this.fmModel = fmModel;
	}

	public String getfAmortizeStatusCode() {
		return fAmortizeStatusCode;
	}

	public void setfAmortizeStatusCode(String fAmortizeStatusCode) {
		this.fAmortizeStatusCode = fAmortizeStatusCode;
	}

	public String getfDepreciationStatusCode() {
		return fDepreciationStatusCode;
	}

	public Lookups getfAmortizeStatus() {
		return fAmortizeStatus;
	}

	public void setfAmortizeStatus(Lookups fAmortizeStatus) {
		this.fAmortizeStatus = fAmortizeStatus;
	}

	public String getfAmortizeStatusShow() {
		return fAmortizeStatusShow;
	}

	public void setfAmortizeStatusShow(String fAmortizeStatusShow) {
		this.fAmortizeStatusShow = fAmortizeStatusShow;
	}

	public void setfDepreciationStatusCode(String fDepreciationStatusCode) {
		this.fDepreciationStatusCode = fDepreciationStatusCode;
	}

	public String getfValueTypeCode() {
		return fValueTypeCode;
	}

	public void setfValueTypeCode(String fValueTypeCode) {
		this.fValueTypeCode = fValueTypeCode;
	}

	public Integer getfListId() {
		return fListId;
	}

	public String getfFixedTypes() {
		return fFixedTypes;
	}

	public void setfFixedTypes(String fFixedTypes) {
		this.fFixedTypes = fFixedTypes;
	}

	public void setfListId(Integer fListId) {
		this.fListId = fListId;
	}

	public String getfFixedTypeId() {
		return fFixedTypeId;
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

	public Integer getAcRId() {
		return acRId;
	}

	public void setAcRId(Integer acRId) {
		this.acRId = acRId;
	}

	public String getfFixedType() {
		return fFixedType;
	}

	public void setfFixedType(String fFixedType) {
		this.fFixedType = fFixedType;
	}

	public Integer getfId_S() {
		return fId_S;
	}

	public void setfId_S(Integer fId_S) {
		this.fId_S = fId_S;
	}

	public String getfAssCode() {
		return fAssCode;
	}

	public void setfAssCode(String fAssCode) {
		this.fAssCode = fAssCode;
	}

	public String getfAssType() {
		return fAssType;
	}

	public void setfAssType(String fAssType) {
		this.fAssType = fAssType;
	}

	public String getfAssName() {
		return fAssName;
	}

	public void setfAssName(String fAssName) {
		this.fAssName = fAssName;
	}

	public String getfMMode() {
		return fMMode;
	}

	public void setfMMode(String fMMode) {
		this.fMMode = fMMode;
	}

	public Date getfActionDate() {
		return fActionDate;
	}

	public void setfActionDate(Date fActionDate) {
		this.fActionDate = fActionDate;
	}

	public String getfFinancialCode() {
		return fFinancialCode;
	}

	public void setfFinancialCode(String fFinancialCode) {
		this.fFinancialCode = fFinancialCode;
	}

	public Date getfFinancialDate() {
		return fFinancialDate;
	}

	public void setfFinancialDate(Date fFinancialDate) {
		this.fFinancialDate = fFinancialDate;
	}

	public Lookups getfValueType() {
		return fValueType;
	}

	public void setfValueType(Lookups fValueType) {
		this.fValueType = fValueType;
	}

	public Lookups getfDepreciationStatus() {
		return fDepreciationStatus;
	}

	public void setfDepreciationStatus(Lookups fDepreciationStatus) {
		this.fDepreciationStatus = fDepreciationStatus;
	}

	public String getfDepreciationStatusShow() {
		return fDepreciationStatusShow;
	}

	public void setfDepreciationStatusShow(String fDepreciationStatusShow) {
		this.fDepreciationStatusShow = fDepreciationStatusShow;
	}

	public String getfValueTypeShow() {
		return fValueTypeShow;
	}

	public void setfValueTypeShow(String fValueTypeShow) {
		this.fValueTypeShow = fValueTypeShow;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getfUnappropriationAmount() {
		return fUnappropriationAmount;
	}

	public void setfUnappropriationAmount(Double fUnappropriationAmount) {
		this.fUnappropriationAmount = fUnappropriationAmount;
	}

	public Double getfAppropriationAmount() {
		return fAppropriationAmount;
	}

	public void setfAppropriationAmount(Double fAppropriationAmount) {
		this.fAppropriationAmount = fAppropriationAmount;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getfAdminOfficial() {
		return fAdminOfficial;
	}

	public void setfAdminOfficial(String fAdminOfficial) {
		this.fAdminOfficial = fAdminOfficial;
	}

	public String getfAdminOfficialShow() {
		return fAdminOfficialShow;
	}

	public void setfAdminOfficialShow(String fAdminOfficialShow) {
		this.fAdminOfficialShow = fAdminOfficialShow;
	}
	
	
}
