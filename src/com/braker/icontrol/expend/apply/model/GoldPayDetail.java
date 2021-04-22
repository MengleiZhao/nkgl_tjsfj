package com.braker.icontrol.expend.apply.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;


/**
 * 合同保证金明细表的model
 * @author 赵孟雷
 * @createtime 2021-01-26
 * @updatetime 2021-01-26
 */
@Entity
@Table(name = "T_GOLDPAY_DETAIL")
public class GoldPayDetail extends BaseEntity{
	@Id
	@Column(name = "F_GO_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer goId;			//主键ID
	
	@Column(name = "F_R_ID")
	private Integer rId;			//报销信息ID(沈帆加)
	
	@Column(name = "F_COST_DETAILl")
	private String costDetail;		//支出事项
	
	@Column(name = "F_REMARK")
	private String remark;			//备注描述
	
	@Column(name = "F_REIMB_AMOUNT")
	private Double remibAmount;			//报销金额(沈帆加)

	@Column(name = "F_STATUS")
	private Integer fStatus;//数据状态0-事前申请，1-事后报销
	
	@Transient
	private Integer num;			//序号(数据库中没有)

	public Integer getGoId() {
		return goId;
	}

	public void setGoId(Integer goId) {
		this.goId = goId;
	}

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public String getCostDetail() {
		return costDetail;
	}

	public void setCostDetail(String costDetail) {
		this.costDetail = costDetail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getRemibAmount() {
		return remibAmount;
	}

	public void setRemibAmount(Double remibAmount) {
		this.remibAmount = remibAmount;
	}

	public Integer getfStatus() {
		return fStatus;
	}

	public void setfStatus(Integer fStatus) {
		this.fStatus = fStatus;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "GoldPayDetail [goId=" + goId + ", rId=" + rId + ", costDetail="
				+ costDetail + ", remark=" + remark + ", remibAmount="
				+ remibAmount + ", fStatus=" + fStatus + ", num=" + num
				+ ", getGoId()=" + getGoId() + ", getrId()=" + getrId()
				+ ", getCostDetail()=" + getCostDetail() + ", getRemark()="
				+ getRemark() + ", getRemibAmount()=" + getRemibAmount()
				+ ", getfStatus()=" + getfStatus() + ", getNum()=" + getNum()
				+ ", getUpdator()=" + getUpdator() + ", getUpdateTime()="
				+ getUpdateTime() + ", getCreateTime()=" + getCreateTime()
				+ ", getCreator()=" + getCreator() + ", getId()=" + getId()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + "]";
	}
	
}
