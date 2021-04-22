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
import com.braker.common.entity.Combobox;
import com.braker.core.model.Lookups;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 资产品目表
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_ASSET_BASIC_INFO")
public class AssetBasicInfo extends BaseEntity implements Combobox{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_ASS_ID")
	private Integer fAssId;
	
	@Column(name ="F_LIST_ID")
	private Integer fListId;		//入账单中的资产详情表ID

	@Column(name ="F_CONT_ID")
	private Integer fcId;			//外键合同主表ContractBasicInfo主键ID
	
	@Column(name ="F_CONT_CODE")
	private String fcCode;			//合同编号
	
	@Column(name ="F_ASS_CODE")
	private String fAssCode;//卡片编号
	
	@Column(name ="F_ASS_NAME")
	private String fAssName;//资产名称
	
	@Column(name ="F_ASS_TYPE")
	private String fAssType;//资产类型 ZCLX-01-低值易耗品，ZCLX-02-固定资产，ZCLX-03-无形资产
	
	@Column(name ="F_FIXED_TYPE")
	private String fFixedType;//固定资产类型

	@Column(name ="F_FIXED_TYPE_ID")
	private String fFixedTypeId;//固定资产分类ID
	
	@Column(name ="F_FIXED_TYPE_CODE")
	private String fFixedTypeCode;//固定资产分类CODE

	@Column(name = "F_M_MODEL")
	private String fmModel;//计量单位
	
	@Column(name="F_FORM")
	private String fForm;//资产来源
	
	@Column(name="F_FORM_SHOW")
	private String fFormShow;//资产来源显示
	
	@Column(name ="F_M_MODE")
	private String fSPModel;//规格型号
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_ACTION_DATE")
	private Date fActionDate;//取得日期
	
	@Column(name ="F_ADMIN_OFFICIAL")
	private String fAdminOfficial;//管理部门
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_FINANCIAL_DATE")
	private Date fFinancialDate;//财务入账日期
	
	@Column(name ="F_FINANCIAL_CODE")
	private String fFinancialCode;//会计凭证号
	
	@Column(name ="F_AMOUNT")
	private Double fAmount;//价值(元)
	
	@Column(name ="F_UNAPPROPRIATION_AMOUNT")
	private Double fUnappropriationAmount;//非财政拨款(元)
	
	@Column(name ="F_APPROPRIATION_AMOUNT")
	private Double fAppropriationAmount;//财政拨款(元)
	
	@ManyToOne
	@JoinColumn(name ="F_VALUE_TYPE" ,referencedColumnName ="lookupscode")
	private Lookups fValueType;//价值类型 ：ZCJZLX-01原值，ZCJZLX-02暂估值，ZCJZLX-03重置值，ZCJZLX-04评估值，ZCJZLX-05名义金额
	
	@ManyToOne
	@JoinColumn(name ="F_DEPRECIATION_STATUS" ,referencedColumnName ="lookupscode")
	private Lookups fDepreciationStatus;//折旧状态 ：ZCZJZT-01提折旧，ZCZJZT-02不提折旧，ZCZJZT-03已完成折旧
	
	@ManyToOne
	@JoinColumn(name ="F_AVAILABLE_STAUTS" ,referencedColumnName ="lookupscode")
	private Lookups fAvailableStauts;//可用状态 ：ZCKYZT-01可用，ZCKYZT-02不可用
	
	@ManyToOne
	@JoinColumn(name ="F_USED_STAUTS" ,referencedColumnName ="lookupscode")
	private Lookups fUsedStauts;//使用状态（下拉框）:ZCSYZT-01 在用，ZCSYZT-02闲置，ZCSYZT-03已处置,ZCSYZT-04待处置
	
	@ManyToOne
	@JoinColumn(name ="F_ASS_STAUTS" ,referencedColumnName ="lookupscode")
	private Lookups fAssStauts;//暂时弃用    启用使用状态这个字段  资产状态（下拉框） ：ZCZT-01-库存中，ZCZT-02-使用中，ZCZT-03-已处置
	
	@Column(name ="F_USE_NAME_ID")
	private String fUseNameID;//当前使用人ID 不写则表示在库中，未使用
	
	@Column(name ="F_USE_NAME")
	private String fUseName;//当前使用人名称
	
	@Column(name ="F_USE_DEPT_ID")
	private String fUseDeptID;//当前使用部门ID
	
	@Column(name ="F_USE_DEPT")
	private String fUseDept;//当前使用部门名称
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_RECE_DATE")
	private Date fReceDate;//最近一次领用时间
	
	@Column(name ="F_CONFIG_STAUTS")
	private String fConfigStauts;//是否在配置过程中  0：不在      1：在

	@Transient
	private String fAdminOfficialShow;//管理部门显示用
	
	@Transient
	private String assStauts;//资产状态(显示用)
	@Transient
	private Integer stockNum;//库存数量
	@Transient
	private Integer number;//序号
	@Transient
	private String fAvailableStautsShow;//可用状态显示用
	@Transient
	private String fUsedStautsShow;//使用状态显示用
	@Transient
	private String fDepreciationStatusShow;//折旧状态显示用
	@Transient
	private String fValueTypeShow;//价值类型显示用
	
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

	public String getfForm() {
		return fForm;
	}

	public String getfConfigStauts() {
		return fConfigStauts;
	}

	public void setfConfigStauts(String fConfigStauts) {
		this.fConfigStauts = fConfigStauts;
	}

	public String getFmModel() {
		return fmModel;
	}

	public void setFmModel(String fmModel) {
		this.fmModel = fmModel;
	}

	public void setfForm(String fForm) {
		this.fForm = fForm;
	}

	public String getfFormShow() {
		return fFormShow;
	}

	public void setfFormShow(String fFormShow) {
		this.fFormShow = fFormShow;
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

	public Integer getfListId() {
		return fListId;
	}

	public void setfListId(Integer fListId) {
		this.fListId = fListId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getfAssId() {
		return fAssId;
	}

	public void setfAssId(Integer fAssId) {
		this.fAssId = fAssId;
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

	public String getfAssType() {
		return fAssType;
	}

	public void setfAssType(String fAssType) {
		this.fAssType = fAssType;
	}

	public String getfSPModel() {
		return fSPModel;
	}

	public void setfSPModel(String fSPModel) {
		this.fSPModel = fSPModel;
	}

	@Override
	public String getCode() {
		return fAssCode;
	}

	@Override
	public String getGridCode() {
		return null;
	}

	@Override
	public String getText() {
		return fAssName;
	}

	@Override
	public String getDesc() {
		return null;
	}

	public String getfFixedType() {
		return fFixedType;
	}

	public void setfFixedType(String fFixedType) {
		this.fFixedType = fFixedType;
	}

	public Date getfActionDate() {
		return fActionDate;
	}

	public void setfActionDate(Date fActionDate) {
		this.fActionDate = fActionDate;
	}

	public Date getfFinancialDate() {
		return fFinancialDate;
	}

	public void setfFinancialDate(Date fFinancialDate) {
		this.fFinancialDate = fFinancialDate;
	}

	public String getfFinancialCode() {
		return fFinancialCode;
	}

	public void setfFinancialCode(String fFinancialCode) {
		this.fFinancialCode = fFinancialCode;
	}

	public Double getfAmount() {
		return fAmount;
	}

	public void setfAmount(Double fAmount) {
		this.fAmount = fAmount;
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

	public Lookups getfAvailableStauts() {
		return fAvailableStauts;
	}

	public void setfAvailableStauts(Lookups fAvailableStauts) {
		this.fAvailableStauts = fAvailableStauts;
	}

	public Lookups getfUsedStauts() {
		return fUsedStauts;
	}

	public void setfUsedStauts(Lookups fUsedStauts) {
		this.fUsedStauts = fUsedStauts;
	}

	public Date getfReceDate() {
		return fReceDate;
	}

	public void setfReceDate(Date fReceDate) {
		this.fReceDate = fReceDate;
	}

	public Lookups getfAssStauts() {
		return fAssStauts;
	}

	public void setfAssStauts(Lookups fAssStauts) {
		this.fAssStauts = fAssStauts;
	}

	public String getAssStauts() {
		if(fAssStauts!=null){
			return fAssStauts.getName();
		}
		return assStauts;
	}

	public void setAssStauts(String assStauts) {
		this.assStauts = assStauts;
	}


	public String getfUseNameID() {
		return fUseNameID;
	}

	public void setfUseNameID(String fUseNameID) {
		this.fUseNameID = fUseNameID;
	}

	public String getfUseName() {
		return fUseName;
	}

	public void setfUseName(String fUseName) {
		this.fUseName = fUseName;
	}

	public String getfUseDeptID() {
		return fUseDeptID;
	}

	public void setfUseDeptID(String fUseDeptID) {
		this.fUseDeptID = fUseDeptID;
	}

	public String getfUseDept() {
		return fUseDept;
	}

	public void setfUseDept(String fUseDept) {
		this.fUseDept = fUseDept;
	}

	public String getfAvailableStautsShow() {
		if(fAvailableStauts!=null){
			return fAvailableStauts.getName();
		}
		return fAvailableStautsShow;
	}

	public void setfAvailableStautsShow(String fAvailableStautsShow) {
		this.fAvailableStautsShow = fAvailableStautsShow;
	}

	public String getfUsedStautsShow() {
		if(fUsedStauts!=null){
			return fUsedStauts.getName();
		}
		return fUsedStautsShow;
	}

	public void setfUsedStautsShow(String fUsedStautsShow) {
		this.fUsedStautsShow = fUsedStautsShow;
	}

	public String getfDepreciationStatusShow() {
		if(fDepreciationStatus!=null){
			return fDepreciationStatus.getName();
		}
		return fDepreciationStatusShow;
	}

	public void setfDepreciationStatusShow(String fDepreciationStatusShow) {
		this.fDepreciationStatusShow = fDepreciationStatusShow;
	}

	public String getfValueTypeShow() {
		if(fValueType!=null){
			return fValueType.getName();
		}
		return fValueTypeShow;
	}

	public void setfValueTypeShow(String fValueTypeShow) {
		this.fValueTypeShow = fValueTypeShow;
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

	@Override
	public String getSftjCode() {
		return null;
	}

	
}
