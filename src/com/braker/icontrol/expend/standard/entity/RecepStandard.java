package com.braker.icontrol.expend.standard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 公务接待费用的配置信息
 * @author 张迅
 * @createtime 2019-05-24
 * @updatetime 2019-05-24
 */
@Entity
@Table(name = "T_RECEP_STANDARD")
public class RecepStandard extends Standard {
	
	
	/** 业务字段 **/
	
	@Column(name = "F_COST_FOOD1")
	private Double costFood1;						//正餐费用
	
	@Column(name = "F_COST_FOOD2")
	private Double costFood2;						//早餐费用
	
	@Column(name = "F_COST_FOOD3")
	private Double costFood3;						//宴请费用
	
	@Transient
	private int pageOrder; 							//页面显示排序

	/** getter/setter **/
	

	public int getPageOrder() {
		return pageOrder;
	}

	public void setPageOrder(int pageOrder) {
		this.pageOrder = pageOrder;
	}

	public Double getCostFood1() {
		return costFood1;
	}

	public void setCostFood1(Double costFood1) {
		this.costFood1 = costFood1;
	}

	public Double getCostFood2() {
		return costFood2;
	}

	public void setCostFood2(Double costFood2) {
		this.costFood2 = costFood2;
	}

	public Double getCostFood3() {
		return costFood3;
	}

	public void setCostFood3(Double costFood3) {
		this.costFood3 = costFood3;
	}

	
	
	
}

