package com.braker.icontrol.assets.returns.model;

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

import com.braker.common.entity.BaseEntity;
import com.braker.core.model.Lookups;

/**
 * 入库资产清单
 * @author wanping
 * @updateTime 2020-07-20
 * @updater wanping
 */
@Entity
@Table(name ="T_ASSET_RETURN_LIST")
public class AssetReturnList extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_LIST_ID")
	private Integer fListId;
	
	@ManyToOne
	@JoinColumn(name ="F_ID",referencedColumnName="F_ID")
	private AssetReturn assetReturn;//外键T_ASSET_RETURN表的主键
	
	@Column(name ="F_ASS_RECE_CODE")
	private String fAssReceCode_AR;//资产领用单号（流水号）
	
	@Column(name ="F_ASS_CODE")
	private String fAssCode_AR;//卡片编码
	
	@Column(name ="F_ASS_NAME")
	private String fAssName_AR;//资产名称
	
	@Column(name ="F_FIXED_TYPE")
	private String fFixedType_AR;//固定资产分类
	
	@Column(name ="F_ASS_SPECIF")
	private String fAssSpecif_AR;//规格型号
	
	@Column(name ="F_RECE_DATE")
	private Date fReceDate;//领用时间

	@Column(name ="F_RETURN_LIST_STAUTS")
	private String fReturnListStauts;//交回清单状态 1-正常 99-删除
	
	@Column(name ="F_REMARK")
	private String fRemark_AR;//备注
	
	@ManyToOne
	@JoinColumn(name ="F_AVAILABLE_STAUTS" ,referencedColumnName ="lookupscode")
	private Lookups fAvailableStauts;//可用状态 ：ZCKYZT-01可用，ZCKYZT-02不可用
	
	
	
	@Transient
	private Integer fNumber;//数量	
	
	@Transient
	private String fFixedTypeName_AR;//固定资产分类(显示用)
	
	@Transient
	private String fUseName_AR;//领用人(显示用)
	
	@Transient
	private String fUseDept_AR;//领用部门(显示用)
	
	@Transient
	private String fAvailableStauts_AR;//可用状态(显示用) ZCKYZT-01可用，ZCKYZT-02不可用
	
	@Transient
	private String fAvailableStauts_code;//可用状态(穿code用) ZCKYZT-01可用，ZCKYZT-02不可用
	
	public Integer getfListId() {
		return fListId;
	}

	public void setfListId(Integer fListId) {
		this.fListId = fListId;
	}

	public String getfAssReceCode_AR() {
		return fAssReceCode_AR;
	}

	public void setfAssReceCode_AR(String fAssReceCode_AR) {
		this.fAssReceCode_AR = fAssReceCode_AR;
	}

	public String getfAssCode_AR() {
		return fAssCode_AR;
	}

	public void setfAssCode_AR(String fAssCode_AR) {
		this.fAssCode_AR = fAssCode_AR;
	}

	public String getfAssName_AR() {
		return fAssName_AR;
	}

	public void setfAssName_AR(String fAssName_AR) {
		this.fAssName_AR = fAssName_AR;
	}

	public AssetReturn getAssetReturn() {
		return assetReturn;
	}

	public void setAssetReturn(AssetReturn assetReturn) {
		this.assetReturn = assetReturn;
	}

	public String getfReturnListStauts() {
		return fReturnListStauts;
	}

	public void setfReturnListStauts(String fReturnListStauts) {
		this.fReturnListStauts = fReturnListStauts;
	}

	public String getfRemark_AR() {
		return fRemark_AR;
	}

	public void setfRemark_AR(String fRemark_AR) {
		this.fRemark_AR = fRemark_AR;
	}

	public Integer getfNumber() {
		return fNumber;
	}

	public void setfNumber(Integer fNumber) {
		this.fNumber = fNumber;
	}

	public String getfAssSpecif_AR() {
		return fAssSpecif_AR;
	}

	public void setfAssSpecif_AR(String fAssSpecif_AR) {
		this.fAssSpecif_AR = fAssSpecif_AR;
	}

	public String getfFixedType_AR() {
		return fFixedType_AR;
	}

	public void setfFixedType_AR(String fFixedType_AR) {
		this.fFixedType_AR = fFixedType_AR;
	}

	public Date getfReceDate() {
		return fReceDate;
	}

	public void setfReceDate(Date fReceDate) {
		this.fReceDate = fReceDate;
	}

	public String getfFixedTypeName_AR() {
		return fFixedTypeName_AR;
	}

	public void setfFixedTypeName_AR(String fFixedTypeName_AR) {
		this.fFixedTypeName_AR = fFixedTypeName_AR;
	}

	public String getfUseName_AR() {
		return fUseName_AR;
	}

	public void setfUseName_AR(String fUseName_AR) {
		this.fUseName_AR = fUseName_AR;
	}

	public String getfUseDept_AR() {
		return fUseDept_AR;
	}

	public void setfUseDept_AR(String fUseDept_AR) {
		this.fUseDept_AR = fUseDept_AR;
	}

	public String getfAvailableStauts_AR() {
		return fAvailableStauts_AR;
	}

	public void setfAvailableStauts_AR(String fAvailableStauts_AR) {
		this.fAvailableStauts_AR = fAvailableStauts_AR;
	}

	public Lookups getfAvailableStauts() {
		return fAvailableStauts;
	}

	public void setfAvailableStauts(Lookups fAvailableStauts) {
		this.fAvailableStauts = fAvailableStauts;
	}

	public String getfAvailableStauts_code() {
		return fAvailableStauts_code;
	}

	public void setfAvailableStauts_code(String fAvailableStauts_code) {
		this.fAvailableStauts_code = fAvailableStauts_code;
	}

}
