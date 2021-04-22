package com.braker.icontrol.assets.rece.model;

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
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 领用资产配置单
 * @author 陈睿超
 * @createTime 2020-07-15
 *
 */
@Entity
@Table(name = "T_ASSET_RECE_CONFIG_LIST")
public class ReceConfigList extends BaseEntity{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_LIST_ID")
	private Integer fConfigListId;
	
	@ManyToOne
	@JoinColumn(name ="F_ID",referencedColumnName="F_ID")
	private Rece rece_CL;//外键rece表的主键
	
	@Column(name ="F_ASS_ID")
	private Integer fAssId;//资产品目表ID
	
	@Column(name ="F_ASS_RECE_CODE")
	private String fAssReceCode_CL;//资产领用单号（流水号）
	
	@Column(name ="F_ASS_CODE")
	private String fAssCode_CL;//卡片编码
	
	@Column(name ="F_ASS_NAME")
	private String fAssName_CL;//资产名称
	
	@Column(name ="F_FIXED_TYPE")
	private String ffixedType_CL;//固定资产分类
	
	@Column(name ="F_M_MODE")
	private String fMode_CL;//型号
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name ="F_FINANCIAL_DATE")
	private Date fFinancialDate_CL;//财务入账日期
	
	@Column(name ="F_REMARK")
	private String fRemark_RL;//备注

	@Transient
	private Integer number;//序号	
	@Transient
	private String ffixedType_show;//固定资产分类显示用	
	
	
	
	
	public Integer getfAssId() {
		return fAssId;
	}

	public void setfAssId(Integer fAssId) {
		this.fAssId = fAssId;
	}

	public Integer getfConfigListId() {
		return fConfigListId;
	}

	public void setfConfigListId(Integer fConfigListId) {
		this.fConfigListId = fConfigListId;
	}

	public Rece getRece_CL() {
		return rece_CL;
	}

	public void setRece_CL(Rece rece_CL) {
		this.rece_CL = rece_CL;
	}

	public String getfAssReceCode_CL() {
		return fAssReceCode_CL;
	}

	public void setfAssReceCode_CL(String fAssReceCode_CL) {
		this.fAssReceCode_CL = fAssReceCode_CL;
	}

	public String getfAssCode_CL() {
		return fAssCode_CL;
	}

	public void setfAssCode_CL(String fAssCode_CL) {
		this.fAssCode_CL = fAssCode_CL;
	}

	public String getfAssName_CL() {
		return fAssName_CL;
	}

	public void setfAssName_CL(String fAssName_CL) {
		this.fAssName_CL = fAssName_CL;
	}

	public String getfMode_CL() {
		return fMode_CL;
	}

	public void setfMode_CL(String fMode_CL) {
		this.fMode_CL = fMode_CL;
	}

	public Date getfFinancialDate_CL() {
		return fFinancialDate_CL;
	}

	public void setfFinancialDate_CL(Date fFinancialDate_CL) {
		this.fFinancialDate_CL = fFinancialDate_CL;
	}

	public String getfRemark_RL() {
		return fRemark_RL;
	}

	public void setfRemark_RL(String fRemark_RL) {
		this.fRemark_RL = fRemark_RL;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getFfixedType_CL() {
		return ffixedType_CL;
	}

	public void setFfixedType_CL(String ffixedType_CL) {
		this.ffixedType_CL = ffixedType_CL;
	}

	public String getFfixedType_show() {
		return ffixedType_show;
	}

	public void setFfixedType_show(String ffixedType_show) {
		this.ffixedType_show = ffixedType_show;
	}

	
	
	
}
