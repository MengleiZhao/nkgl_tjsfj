package com.braker.core.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.braker.common.entity.Combobox;
import com.braker.common.entity.GenericEntityNow;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_asset_type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetType extends GenericEntityNow{

	@Column(name = "TYPECODE")
    private String code;
	
	@Column(name = "TYPENAME")
    private String name;
	
	@Column(name = "TYPE_ID")
	private String typeId;
	
	@Column(name = "shaort_name")
    private String shortName;
	
	@Column(name = "ASSET_TYPE")
	private String assetType;  		//资产类型    0：固定资产   1：无形资产
	
	@Column(name = "PARENTID")
	private String parentId;		//父级ID
	
	@Column(name = "UNIT")
	private String unit;         //计量单位
	
	@Column(name = "EXPLAIN")
	private String explain;         //说明
	
	@Column(name = "LEVE")
	private Integer leve;		//级别

	public String getCode() {
		return code;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getTypeId() {
		return typeId;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLeve() {
		return leve;
	}

	public void setLeve(Integer leve) {
		this.leve = leve;
	}

}
