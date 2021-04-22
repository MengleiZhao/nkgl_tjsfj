package com.braker.common.entity;

import javax.persistence.Entity;

/**
 * 用于组装的数据，在前台以下拉框的方式显示用
 * @author 赵孟雷
 */
@Entity
public class ComboboxBean implements Combobox{
	public String ids;
	public String codes;
	public String gridCodes;
	public String sftjCodes;
	public String texts;
	public String descs;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getGridCodes() {
		return gridCodes;
	}
	public void setGridCodes(String gridCodes) {
		this.gridCodes = gridCodes;
	}
	public String getSftjCodes() {
		return sftjCodes;
	}
	public void setSftjCodes(String sftjCodes) {
		this.sftjCodes = sftjCodes;
	}
	public String getTexts() {
		return texts;
	}
	public void setTexts(String texts) {
		this.texts = texts;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ids;
	}
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return codes;
	}
	@Override
	public String getGridCode() {
		// TODO Auto-generated method stub
		return gridCodes;
	}
	@Override
	public String getSftjCode() {
		// TODO Auto-generated method stub
		return sftjCodes;
	}
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return texts;
	}
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return descs;
	}
}
