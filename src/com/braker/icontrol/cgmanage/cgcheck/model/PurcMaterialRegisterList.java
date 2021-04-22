package com.braker.icontrol.cgmanage.cgcheck.model;


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
 * 采购登记中的中标商的采购明细model
 * @author 冉德茂
 * @createtime 2018-07-16
 * @updatetime 2018-07-16
 */
@Entity
@Table(name="T_PURC_MATERIAL_REGISTER_LIST")
public class PurcMaterialRegisterList extends BaseEntity{
	
	@Id
	@Column(name = "F_M_R_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer mRId;			//主键ID

	@Column(name ="F_P_ID")
	private Integer fpId;			//外键ID

	@Column(name ="F_R_ID")							
	private Integer frId;			//外键ID  链接T_register_APPLY_BASIC (F_P_ID);
	
	@Column(name = "F_P_L_ID")
	private Integer mainId;			//外键ID链接T_PROCUREMENT_PLAN_LIST 采购申请采购明细表
	
/*	@ManyToOne
	@JoinColumn(name = "F_M_TYPE",referencedColumnName="lookupscode")
	private Lookups fmType;//物品类型
*/	
	@Column(name = "F_BIDDING_CODE")
	private String fbiddingCode;//招标编号
	
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
	
	@Column(name = "F_AMOUNT")
	private Double famount;//金额
	
	@Column(name = "F_SIGN_PRICE")
	private Double fsignPrice;//单价
	
	@Column(name = "F_REMARK")
	private String fRemark;//备注
	
	@Column(name = "F_WHETHER_IMPORT")
	private String fWhetherImport;//是否进口
	
	@Column(name = "F_NEW_KIND")
	private String fpKind;			//品目 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_NAME")
	private String fpurName;			//商品名称 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_NUM")
	private Integer fnum;			//数量 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_UNIT")
	private String fmeasureUnit;			//单位 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_ISIMP")
	private String fIsImp;		//进口 新 3.26 方淳洲加
	
	@Column(name = "F_NEW_COMPRO")
	private String fcommProp;		//相关要求 新 3.26 方淳洲加
	
	@Transient
	private Integer  num;			//序号
	
	
	
	

	public String getFpKind() {
		return fpKind;
	}

	public void setFpKind(String fpKind) {
		this.fpKind = fpKind;
	}

	public String getFpurName() {
		return fpurName;
	}

	public void setFpurName(String fpurName) {
		this.fpurName = fpurName;
	}

	public Integer getFnum() {
		return fnum;
	}

	public void setFnum(Integer fnum) {
		this.fnum = fnum;
	}

	public String getFmeasureUnit() {
		return fmeasureUnit;
	}

	public void setFmeasureUnit(String fmeasureUnit) {
		this.fmeasureUnit = fmeasureUnit;
	}

	public String getfIsImp() {
		return fIsImp;
	}

	public void setfIsImp(String fIsImp) {
		this.fIsImp = fIsImp;
	}

	public String getFcommProp() {
		return fcommProp;
	}

	public void setFcommProp(String fcommProp) {
		this.fcommProp = fcommProp;
	}

	public Integer getMainId() {
		return mainId;
	}

	public void setMainId(Integer mainId) {
		this.mainId = mainId;
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

	public Integer getmRId() {
		return mRId;
	}

	public void setmRId(Integer mRId) {
		this.mRId = mRId;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
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

	public Integer getFrId() {
		return frId;
	}

	public void setFrId(Integer frId) {
		this.frId = frId;
	}
	
}
