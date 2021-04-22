package com.braker.icontrol.contract.Formulation.model;

/**
 * 用于采购验收中合同返回信息
 * @author 赵孟雷
 *
 */
public class ContractReturnInfo{

	private Integer fcId;      						//合同id
	
	private String fcCode;							//合同编号（流水号）
	
	private String fcTitle;							//合同名称
	
	private String fUpdateStatus;					//合同变更状态 1-有变更	0-未变更	 2020.02.17陈睿超加

	public Integer getFcId() {
		return fcId;
	}

	public void setFcId(Integer fcId) {
		this.fcId = fcId;
	}

	public String getFcCode() {
		return fcCode;
	}

	public void setFcCode(String fcCode) {
		this.fcCode = fcCode;
	}

	public String getFcTitle() {
		return fcTitle;
	}

	public void setFcTitle(String fcTitle) {
		this.fcTitle = fcTitle;
	}

	public String getfUpdateStatus() {
		return fUpdateStatus;
	}

	public void setfUpdateStatus(String fUpdateStatus) {
		this.fUpdateStatus = fUpdateStatus;
	}

}
