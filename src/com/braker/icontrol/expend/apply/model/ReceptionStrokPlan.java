package com.braker.icontrol.expend.apply.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.braker.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 公务接待主要行程
 * @author 陈睿超
 *
 */
@Entity
@Table(name = "T_RECEPTION_STROK_PLAN")
public class ReceptionStrokPlan extends BaseEntity{

	@Id
	@Column(name = "F_S_P_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer fSPId;

	@Column(name = "F_G_ID")
	private Integer gId; //申请单主键

	@Column(name = "F_R_ID")
	private Integer rId;//报销单主键

	@Column(name = "F_PRO")
	private String fPro;//项目
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Column(name = "F_TIME")
	private Date fTime;//时间

	@Column(name = "F_ADDRESS")
	private String fAddress;//场所


	@Column(name = "F_EXT_1")
	private String fExt1;
	@Column(name = "F_EXT_2")
	private String fExt2;
	@Column(name = "F_EXT_3")
	private String fExt3;
	@Column(name = "F_EXT_4")
	private String fExt4;
	@Column(name = "F_EXT_5")
	private String fExt5;
	
	
	
	public Integer getfSPId() {
		return fSPId;
	}
	public void setfSPId(Integer fSPId) {
		this.fSPId = fSPId;
	}
	public Integer getgId() {
		return gId;
	}
	public void setgId(Integer gId) {
		this.gId = gId;
	}
	public Integer getrId() {
		return rId;
	}
	public void setrId(Integer rId) {
		this.rId = rId;
	}
	public String getfPro() {
		return fPro;
	}
	public void setfPro(String fPro) {
		this.fPro = fPro;
	}
	public Date getfTime() {
		return fTime;
	}
	public void setfTime(Date fTime) {
		this.fTime = fTime;
	}
	public String getfAddress() {
		return fAddress;
	}
	public void setfAddress(String fAddress) {
		this.fAddress = fAddress;
	}
	public String getfExt1() {
		return fExt1;
	}
	public void setfExt1(String fExt1) {
		this.fExt1 = fExt1;
	}
	public String getfExt2() {
		return fExt2;
	}
	public void setfExt2(String fExt2) {
		this.fExt2 = fExt2;
	}
	public String getfExt3() {
		return fExt3;
	}
	public void setfExt3(String fExt3) {
		this.fExt3 = fExt3;
	}
	public String getfExt4() {
		return fExt4;
	}
	public void setfExt4(String fExt4) {
		this.fExt4 = fExt4;
	}
	public String getfExt5() {
		return fExt5;
	}
	public void setfExt5(String fExt5) {
		this.fExt5 = fExt5;
	}
	
	
	
	
	
}
