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
 * 被接待人员信息model
 * 是被接待人员信息的model类
 * @author 叶崇晖
 * @createtime 2018-06-14
 * @updatetime 2018-06-14
 */
@Entity
@Table(name = "T_RECEPTION_APPLI_PEOPLE_INFO")
public class ReceptionPeopleInfo extends BaseEntity{
	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "F_J_ID")
	private Integer jId;				//接待信息ID

	@Column(name = "F_G_ID")
	private Integer gId; 				//申请单主键

	@Column(name = "F_R_ID")
	private Integer rId;				//报销单主键
	
	@Column(name = "F_RECEPTION_PEOPLE_NAME")
	private String receptionPeopName;	//被接待人姓名
	
	@Column(name = "F_POSITION")
	private String position;			//职务
	
	@Column(name = "F_REMARK")
	private String jDremake;			//备注
	
	@Column(name = "F_GOVERNMENT")
	private String government;			//单位
	
	@Transient
	private String status;				//状态值easyui插件中带的，装换json时用(数据库中没有)

	



	public Integer getjId() {
		return jId;
	}

	public void setjId(Integer jId) {
		this.jId = jId;
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

	public String getReceptionPeopName() {
		return receptionPeopName;
	}

	public void setReceptionPeopName(String receptionPeopName) {
		this.receptionPeopName = receptionPeopName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getjDremake() {
		return jDremake;
	}

	public void setjDremake(String jDremake) {
		this.jDremake = jDremake;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGovernment() {
		return government;
	}

	public void setGovernment(String government) {
		this.government = government;
	}
	
	
}
