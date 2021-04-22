package com.braker.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;

/**
 * 政府支出经济分类
 * @author wanping
 *
 */
@Entity
@Table(name="T_ECONOMIC_CLASSIFICATION_GOVERNMENT")
public class EconomicClassificationGovernment extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="F_ID")
	private Integer fid;
	
	@Column(name="F_EC_CODE")
	private String code;//科目编号
	
	@Column(name="F_EC_NAME")
	private String name;//科目名称
	
	@Column(name="F_EC_LEVE")
	private String leve;//科目级别
	
	@Column(name="F_P_ID")
	private Integer pid;//上级科目编号
	
	@Column(name="F_EC_YEAR")
	private Integer year;//年份
	
	@Column(name ="F_STATUS")
	private String status;//状态 0-无效 1-有效
	
	@Column(name ="F_EC_TYPE")
	private String type;//科目类型
	
	@Transient
	private Integer number;//序号

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeve() {
		return leve;
	}

	public void setLeve(String leve) {
		this.leve = leve;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
