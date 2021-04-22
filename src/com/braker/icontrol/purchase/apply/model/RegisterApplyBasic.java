package com.braker.icontrol.purchase.apply.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.poi.xwpf.converter.core.utils.StringUtils;

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;
import com.braker.common.util.LookupsUtil;
import com.braker.core.model.Lookups;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 采购登记基本数据表   
 * @author 赵孟雷
 *
 */
@Entity
@Table(name="T_REGISTER_APPLY_BASIC")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterApplyBasic extends BaseEntity implements EntityDao,CheckEntity{
	@Id
	@Column(name ="F_R_ID")
	private Integer frId;				//主键
	
	@Column(name ="F_P_ID")
	private Integer fpId;				//采购申请主键
	
	@Column (name ="F_AMOUNT")
	private Double amount;			//采购登记金额
	
	@Column (name="F_REQ_TIME")
	private Date fReqTime;				//申请时间

	@Column (name ="F_USER")
	private String fUser;				//登记人id
	
	@Column(name ="F_USER_NAME")
	private String fUserName;				//登记人
	
	@Column(name ="F_DEPT_ID")
	private String fDeptID;					//登记部门ID

	@Column(name ="F_DEPT_NAME")
	private String fDeptName;				//登记部门名称
	
	@Column (name="F_BIDDING_CODE")
	private String fbiddingCode;			//采购登记编号    用作登记编号显示和登记的采购清单做关联
	
	@Column (name ="F_CHECK_STAUTS")
	private String fCheckStauts;		//采购登记审批状态
	
	@Column (name ="F_STAUTS")
	private String fStauts;				//采购登记数据的删除状态
	
	@Column(name = "F_USER_NAME2")
	private String userName2;			//下环节处理人姓名
	
	@Column(name = "F_USER_CODE")
	private String fuserId;				//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;				//下节点节点编码
	
	@Column(name = "F_FILES_1")
	private String files01;				//附件1
	
	@Column(name = "F_FILES_2")
	private String files02;				//附件2
	
	@Column(name = "F_FILES_3")
	private String files03;				//附件3
	
	@Column(name = "F_FILES_4")
	private String files04;				//附件4
	
	@Column(name = "F_FILES_5")
	private String files05;				//附件5

	@Transient
	private int number;					//序号(数据库里没有的)
	
	
	public String getfUserName() {
		return fUserName;
	}

	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	public String getfDeptID() {
		return fDeptID;
	}

	public void setfDeptID(String fDeptID) {
		this.fDeptID = fDeptID;
	}

	public String getfDeptName() {
		return fDeptName;
	}

	public void setfDeptName(String fDeptName) {
		this.fDeptName = fDeptName;
	}

	public String getFbiddingCode() {
		return fbiddingCode;
	}

	public void setFbiddingCode(String fbiddingCode) {
		this.fbiddingCode = fbiddingCode;
	}

	public Integer getFrId() {
		return frId;
	}

	public void setFrId(Integer frId) {
		this.frId = frId;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getfReqTime() {
		return fReqTime;
	}

	public void setfReqTime(Date fReqTime) {
		this.fReqTime = fReqTime;
	}

	public String getfCheckStauts() {
		return fCheckStauts;
	}

	public void setfCheckStauts(String fCheckStauts) {
		this.fCheckStauts = fCheckStauts;
	}

	public String getfStauts() {
		return fStauts;
	}

	public void setfStauts(String fStauts) {
		this.fStauts = fStauts;
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

	public String getFiles01() {
		return files01;
	}

	public void setFiles01(String files01) {
		this.files01 = files01;
	}

	public String getFiles02() {
		return files02;
	}

	public void setFiles02(String files02) {
		this.files02 = files02;
	}

	public String getFiles03() {
		return files03;
	}

	public void setFiles03(String files03) {
		this.files03 = files03;
	}

	public String getFiles04() {
		return files04;
	}

	public void setFiles04(String files04) {
		this.files04 = files04;
	}

	public String getFiles05() {
		return files05;
	}

	public void setFiles05(String files05) {
		this.files05 = files05;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getfUser() {
		return fUser;
	}

	public void setfUser(String fUser) {
		this.fUser = fUser;
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
		
		return fbiddingCode;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_BIDDING_CODE";
	}

	@Override
	public Integer getBeanId() {
		
		return frId;
	}

	@Override
	public String getUserId() {
		
		return fUser;
	}

	@Override
	public String getNextCheckKey() {
		
		return nCode;
	}

	@Override
	public String getJoinTable() {
		
		return "T_REGISTER_APPLY_BASIC";
	}

	@Override
	public String getEntryId() {
		
		return frId.toString();
	}
	
	
}
