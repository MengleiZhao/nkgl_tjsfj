package com.braker.core.model;


/**
 * 用于返回页面指定的几个字段     已用于差旅人员选择
 * @author 赵孟雷
 *
 */
public class UserTransient {
	
	private String id;
	private String accountNo;//账号
	private String name;//姓名
	private String departName;// 部门名称
	private Integer	roleslevel;//1：市级，2：局级，3：其他人员
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Integer getRoleslevel() {
		return roleslevel;
	}
	public void setRoleslevel(Integer roleslevel) {
		this.roleslevel = roleslevel;
	}
	
}
