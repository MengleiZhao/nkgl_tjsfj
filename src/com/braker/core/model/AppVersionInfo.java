package com.braker.core.model;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * app版本信息表的model
 * @author 沈帆
 * @createtime 2020-07-10
 * @updatetime 2020-07-10
 */
@Entity
@Table(name = "T_APP_VERSION_INFO")
public class AppVersionInfo   {
	@Id
	@Column(name = "F_V_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer vId;				//主键ID
	
	@Column(name = "F_VERSION_CODE")
	private String invoiceCode;			//版本号
	
	@Column(name = "F_VERSION_INFORMATION")
	private String versionInformation;				//版本信息
	
	@Column(name = "F_UPDATE_TIME")
	private Date updateTime;				//更新时间

	public Integer getvId() {
		return vId;
	}

	public void setvId(Integer vId) {
		this.vId = vId;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getVersionInformation() {
		return versionInformation;
	}

	public void setVersionInformation(String versionInformation) {
		this.versionInformation = versionInformation;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	

	
	
}
