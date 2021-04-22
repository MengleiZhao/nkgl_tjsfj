package com.braker.icontrol.contract.archiving.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.EntityDao;

/**
 * 合同归档信息表
 * @author 陈睿超
 *
 */
@Entity
@Table(name = "T_CONTRACT_TOFILE")
public class Archiving extends BaseEntity implements EntityDao{

	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_TO_ID")
	private Integer fToId;
	
	@Column(name ="F_CONT_ID")
	private Integer fContId_tofile;
	
	@Column(name ="F_UPT_ID")
	private Integer fUpt_tofile;
	
	@Column(name ="F_TO_CODE")
	private String fToCode;//归档编号
	
	@Column(name ="F_TO_POSITION")
	private String fToPosition;//归档位置
	
	@Column(name ="F_TO_USER")
	private String fToUser;//归档人
	
	@Column(name ="F_TO_TIME")
	private Date fToTime;//归档时间

	public Integer getfToId() {
		return fToId;
	}

	public void setfToId(Integer fToId) {
		this.fToId = fToId;
	}

	public Integer getfContId_tofile() {
		return fContId_tofile;
	}

	public void setfContId_tofile(Integer fContId_tofile) {
		this.fContId_tofile = fContId_tofile;
	}

	public String getfToCode() {
		return fToCode;
	}

	public void setfToCode(String fToCode) {
		this.fToCode = fToCode;
	}

	public String getfToPosition() {
		return fToPosition;
	}

	public void setfToPosition(String fToPosition) {
		this.fToPosition = fToPosition;
	}

	public String getfToUser() {
		return fToUser;
	}

	public void setfToUser(String fToUser) {
		this.fToUser = fToUser;
	}

	public Date getfToTime() {
		return fToTime;
	}

	public void setfToTime(Date fToTime) {
		this.fToTime = fToTime;
	}

	public Integer getfUpt_tofile() {
		return fUpt_tofile;
	}

	public void setfUpt_tofile(Integer fUpt_tofile) {
		this.fUpt_tofile = fUpt_tofile;
	}

	@Override
	public String getJoinTable() {
		return "T_CONTRACT_TOFILE";
	}

	@Override
	public String getEntryId() {
		return String.valueOf(fToId);
	}
	
	
	
}
