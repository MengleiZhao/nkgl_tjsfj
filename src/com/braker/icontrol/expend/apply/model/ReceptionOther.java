package com.braker.icontrol.expend.apply.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.braker.common.entity.BaseEntity;
/**
 * 公务接待其他费用明细
 * @author 沈帆
 *
 */
@Entity
@Table(name ="T_RECEPTION_OTEHR")
public class ReceptionOther extends BaseEntity{

	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_O_ID")
	private Integer oID;
	
	@Column(name = "F_G_ID")
	private Integer gId;			//申请信息ID

	@Column(name = "F_R_ID")
	private Integer rId;			//报销信息ID
	
	@Column(name ="F_COST_NAME")
	private String fCostName;        //费用名称
	
	@Column(name ="F_COST")
	private Double fCost;//支出金额
	
	@Column(name ="F_REMARK")
	private String fRemark;  //备注

	@Column(name = "F_STATUS")
	private Integer fStatus;//数据状态0-事前申请，1-事后报销

	public Integer getfStatus() {
		return fStatus;
	}

	public void setfStatus(Integer fStatus) {
		this.fStatus = fStatus;
	}

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public Integer getgId() {
		return gId;
	}

	public void setgId(Integer gId) {
		this.gId = gId;
	}

	public Integer getoID() {
		return oID;
	}

	public void setoID(Integer oID) {
		this.oID = oID;
	}

	public String getfCostName() {
		return fCostName;
	}

	public void setfCostName(String fCostName) {
		this.fCostName = fCostName;
	}

	public Double getfCost() {
		return fCost;
	}

	public void setfCost(Double fCost) {
		this.fCost = fCost;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}



	
}
