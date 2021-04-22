package com.braker.icontrol.cgmanage.questioning.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;

/**
 * 采购质疑的model
 * @author shenfan
 * @createtime 2021-03-16
 * @updatetime 2021-03-16
 */

@Entity
@Table(name="T_PURCHASING_QUERY")
public class PurchasingQuery extends BaseEntity implements EntityDao,CheckEntity{
	
	@Id
	@Column(name = "F_Q_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)   
	private Integer fqId;							//主键ID
	
	@Column(name = "F_QUERY_CODE")
	private String fQueryCode;						//任务单号
	
	@Column(name ="F_P_ID")							
	private Integer fpId;							//外键ID  链接T_PURCHASE_APPLY_BASIC (F_P_ID);
	
	@Column(name = "F_P_CODE")
	private String fpCode;							//采购项目编号
	
	@Column(name = "F_P_NAME")
	private String fpName;							//采购名称
	
	@Column (name ="F_P_PYPE")
	private String fpPype;				//采购方式
	
	@Column (name ="F_P_METHOD")
	private String fpMethod;			//采购类型
	
	@Column(name = "F_ORG_NAME")
	private String fOrgName;        	//中标组织名称
	
	@Column(name = "F_BID_AMOUNT")
	private Double fbidAmount;			//中标金额
	
	@Column (name ="F_DEPT_NAME")
	private String fDeptName;			//申报部门
	
	@Column (name ="F_DEPT_ID")
	private String fDeptId;				//申报部门id
	
	@Column (name ="F_USER")
	private String fUser;				//申报人id
	
	@Column (name ="F_USER_NAME")
	private String fUserName;			//申报人
	//审批
	@Column (name ="F_CHECK_STAUTS")
	private String fCheckStauts;					//审批状态
	
	@Column(name = "F_USER_NAME2")
	private String userName2;						//下环节处理人姓名
	
	@Column(name = "F_USER_CODE")
	private String fuserId;							//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;							//下节点节点编码
	
	@Transient
	private Integer num;							//序号(数据库中没有)
	
	@Column (name ="F_STAUTS")
	private String fStauts;				//数据状态 （0-暂存 1-发起 99-删除）
	
	@Column (name ="F_ASK_STAUTS")
	private String fAskStauts;				//质疑发起状态 (0-暂存 1-发起 )
	
	@Column (name ="F_REMARK")
	private String fRemark;				//情况简述
	
	@Column (name ="F_ANSWER_REMARK")
	private String fAnswerRemark;				//答复描述
	
	@Column (name ="F_ANSWER_STAUTS")
	private String fAnswerStauts;				//答复状态 0-未答复 1-已答复

	@Column (name ="F_ASK_TIME")
	private Date fAskTime;		  //质疑发起时间
	
	@Column (name ="F_ANSWER_TIME")
	private Date fAnswerTime;		  //答复时间
	
	@Column (name ="F_REQ_TIME")
	private Date fReqTime;		  //答复发起时间
	
	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}


	public String getfCheckStauts() {
		return fCheckStauts;
	}

	public void setfCheckStauts(String fCheckStauts) {
		this.fCheckStauts = fCheckStauts;
	}

	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	public String getFuserId() {
		return fuserId;
	}

	public void setFuserId(String fuserId) {
		this.fuserId = fuserId;
	}

	public String getnCode() {
		return nCode;
	}

	public void setnCode(String nCode) {
		this.nCode = nCode;
	}
	

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	
	public String getFpCode() {
		return fpCode;
	}

	public void setFpCode(String fpCode) {
		this.fpCode = fpCode;
	}

	public String getFpName() {
		return fpName;
	}

	public void setFpName(String fpName) {
		this.fpName = fpName;
	}



	public Integer getFqId() {
		return fqId;
	}

	public void setFqId(Integer fqId) {
		this.fqId = fqId;
	}

	public String getfQueryCode() {
		return fQueryCode;
	}

	public void setfQueryCode(String fQueryCode) {
		this.fQueryCode = fQueryCode;
	}

	public String getFpPype() {
		return fpPype;
	}

	public void setFpPype(String fpPype) {
		this.fpPype = fpPype;
	}

	public String getFpMethod() {
		return fpMethod;
	}

	public void setFpMethod(String fpMethod) {
		this.fpMethod = fpMethod;
	}

	public String getfOrgName() {
		return fOrgName;
	}

	public void setfOrgName(String fOrgName) {
		this.fOrgName = fOrgName;
	}

	public Double getFbidAmount() {
		return fbidAmount;
	}

	public void setFbidAmount(Double fbidAmount) {
		this.fbidAmount = fbidAmount;
	}

	public String getfDeptName() {
		return fDeptName;
	}

	public void setfDeptName(String fDeptName) {
		this.fDeptName = fDeptName;
	}

	public String getfDeptId() {
		return fDeptId;
	}

	public void setfDeptId(String fDeptId) {
		this.fDeptId = fDeptId;
	}

	public String getfUser() {
		return fUser;
	}

	public void setfUser(String fUser) {
		this.fUser = fUser;
	}

	public String getfUserName() {
		return fUserName;
	}

	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	
	public String getfStauts() {
		return fStauts;
	}

	public void setfStauts(String fStauts) {
		this.fStauts = fStauts;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	@Transient
	@Override
	public String getJoinTable() {
		
		return "T_PURCHASING_QUERY";
	}

	@Transient
	@Override
	public String getEntryId() {
		
		return String.valueOf(fqId);
	}

	@Override
	public void setNextCheckUserName(String userName) {
		
		this.userName2=userName;
	}

	@Override
	public void setNextCheckUserId(String userId) {
		
		this.fuserId=userId;
	}

	@Override
	public String getNextCheckUserId() {
		
		return fuserId;
	}

	@Override
	public void setNextCheckKey(String nCode) {
		
		this.nCode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.fCheckStauts=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fCheckStauts;
	}
	@Override
	public void setStauts(String status) {
		
		this.fStauts=status;
	}

	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getBeanCode() {
		
		return fQueryCode;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_QUERY_CODE";
	}

	@Override
	public Integer getBeanId() {
		
		return fqId;
	}

	@Override
	public String getUserId() {
		
		return fUser;
	}

	@Override
	public String getNextCheckKey() {
		
		return nCode;
	}

	public String getfAnswerStauts() {
		return fAnswerStauts;
	}

	public void setfAnswerStauts(String fAnswerStauts) {
		this.fAnswerStauts = fAnswerStauts;
	}

	public String getfAskStauts() {
		return fAskStauts;
	}

	public void setfAskStauts(String fAskStauts) {
		this.fAskStauts = fAskStauts;
	}

	public Date getfAskTime() {
		return fAskTime;
	}

	public void setfAskTime(Date fAskTime) {
		this.fAskTime = fAskTime;
	}

	public Date getfAnswerTime() {
		return fAnswerTime;
	}

	public void setfAnswerTime(Date fAnswerTime) {
		this.fAnswerTime = fAnswerTime;
	}

	public String getfAnswerRemark() {
		return fAnswerRemark;
	}

	public void setfAnswerRemark(String fAnswerRemark) {
		this.fAnswerRemark = fAnswerRemark;
	}

	public Date getfReqTime() {
		return fReqTime;
	}

	public void setfReqTime(Date fReqTime) {
		this.fReqTime = fReqTime;
	}
	
	
	

}
