package com.braker.icontrol.contract.Formulation.model;


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
 * 合同关联的中标单位采购清单数据
 * @author 陈睿超
 *
 */
@Entity
@Table(name="T_CONTRACT_REGISTER_LIST")
public class ContractRegisterList extends BaseEntity{
	
	@Id
	@Column(name = "F_C_R_ID")
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cRId;			//主键ID

	@Column(name ="F_CONT_ID")
	private Integer fcId;			//外键合同主表ContractBasicInfo主键ID
	
	@Column(name ="F_UPT_ID")
	private Integer fId_U;			//外键变更合同主表UPT主键ID

	@Column(name = "F_BIDDING_CODE")
	private String fbiddingCode;//编号
	
	@Column(name = "F_M_TYPE")
	private String fmType;//物品类型
	
	@Column(name = "F_M_NAME")
	private String fmName;//物品名称
	
	@Column(name = "F_BRAND")
	private String fBrand;//品牌
	
	@Column(name = "F_M_SPECIF")
	private String fmSpecif;//规格型号
	
	@Column(name = "F_M_MODEL")
	private String fmModel;//计量单位
	
	@Column(name = "F_P_NUM")
	private Integer fpNum;//采购数量
	
	@Column(name = "F_CHECKED_NUM")
	private Integer fCheckedNum;//已验收数量
	
	@Column(name = "F_AMOUNT")
	private Double famount;//金额
	
	@Column(name = "F_SIGN_PRICE")
	private Double fsignPrice;//单价
	
	@Column(name = "F_REMARK")
	private String fRemark;//备注
	
	@Column(name = "F_WHETHER_IMPORT")
	private String fWhetherImport;//是否进口
	
	@Column(name = "F_DATA_TYPE")
	private Integer fDataType;//数据类型0-合同拟定的数据，1-合同变更的数据.
	
	@Column(name = "F_TOTAL_PRICE")
	private Double fTotalPrice;//总价
	
	@Column(name = "F_NEW_KIND")
	private String newKind;			//品目 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_NAME")
	private String newName;			//商品名称 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_NUM")
	private Integer newNum;			//数量 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_UNIT")
	private String newUnit;			//单位 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_ISIMP")
	private String newIsImp;		//进口 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_COMPRO")
	private String newCompro;		//相关要求 新 3.26 方淳洲加
	
	
	
	
	
	
	
	
	public String getNewKind() {
		return newKind;
	}

	public void setNewKind(String newKind) {
		this.newKind = newKind;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public Integer getNewNum() {
		return newNum;
	}

	public void setNewNum(Integer newNum) {
		this.newNum = newNum;
	}

	public String getNewUnit() {
		return newUnit;
	}

	public void setNewUnit(String newUnit) {
		this.newUnit = newUnit;
	}

	public String getNewIsImp() {
		return newIsImp;
	}

	public void setNewIsImp(String newIsImp) {
		this.newIsImp = newIsImp;
	}

	public String getNewCompro() {
		return newCompro;
	}

	public void setNewCompro(String newCompro) {
		this.newCompro = newCompro;
	}

	@Transient
	private Integer  num;			//序号
	
	

	public Integer getcRId() {
		return cRId;
	}

	public void setcRId(Integer cRId) {
		this.cRId = cRId;
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

	public Integer getfDataType() {
		return fDataType;
	}

	public void setfDataType(Integer fDataType) {
		this.fDataType = fDataType;
	}

	public String getfWhetherImport() {
		return fWhetherImport;
	}

	public void setfWhetherImport(String fWhetherImport) {
		this.fWhetherImport = fWhetherImport;
	}

	public String getFbiddingCode() {
		return fbiddingCode;
	}

	public void setFbiddingCode(String fbiddingCode) {
		this.fbiddingCode = fbiddingCode;
	}

	public String getfBrand() {
		return fBrand;
	}

	public void setfBrand(String fBrand) {
		this.fBrand = fBrand;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getFamount() {
		return famount;
	}

	public void setFamount(Double famount) {
		this.famount = famount;
	}

	public Double getFsignPrice() {
		return fsignPrice;
	}

	public void setFsignPrice(Double fsignPrice) {
		this.fsignPrice = fsignPrice;
	}

	public String getFmType() {
		return fmType;
	}

	public void setFmType(String fmType) {
		this.fmType = fmType;
	}

	public String getFmName() {
		return fmName;
	}

	public void setFmName(String fmName) {
		this.fmName = fmName;
	}

	public String getFmSpecif() {
		return fmSpecif;
	}

	public void setFmSpecif(String fmSpecif) {
		this.fmSpecif = fmSpecif;
	}

	public String getFmModel() {
		return fmModel;
	}

	public void setFmModel(String fmModel) {
		this.fmModel = fmModel;
	}

	public Integer getFpNum() {
		return fpNum;
	}

	public void setFpNum(Integer fpNum) {
		this.fpNum = fpNum;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	public Integer getfCheckedNum() {
		return fCheckedNum;
	}

	public void setfCheckedNum(Integer fCheckedNum) {
		this.fCheckedNum = fCheckedNum;
	}

	public Double getfTotalPrice() {
		return fTotalPrice;
	}

	public void setfTotalPrice(Double fTotalPrice) {
		this.fTotalPrice = fTotalPrice;
	}
	
}
