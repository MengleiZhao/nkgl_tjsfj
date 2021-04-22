package com.braker.icontrol.cgmanage.cgprocess.model;

import java.util.Date;
import java.util.List;

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
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;

/**
 * 采购招标登记的model   更改为    采购过程登记model  采购结果列表
 * @author 冉德茂
 * @createtime 2018-07-18
 * @updatetime 2018-07-18
 */
@Entity
@Table(name="T_BIDDING_REGIST")
public class BiddingRegist extends BaseEntity implements EntityDao,CheckEntity{
	@Id
	@Column(name = "F_B_ID")
	private Integer fbId;							//主键ID
	
	@Column(name ="F_P_ID")							
	private Integer fpId;							//外键ID  链接T_PURCHASE_APPLY_BASIC PurchaseApplyBasic的(F_P_ID);
	
	@Column(name ="F_R_ID")							
	private Integer frId;							//外键ID  链接T_register_APPLY_BASIC (F_P_ID);
	
	@Column(name = "F_BIDDING_CODE")
	private String fbiddingCode;					//中标编号
	
	@Column(name ="F_REG_USER_ID")
	private String fregUserId;						//登记人id
	
	@Column(name ="F_REG_USER_NAME")
	private String fregUserNAME;					//登记人
	
	@Column(name ="F_REG_DEPT_ID")
	private String fregDeptId;						//登记部门id
	
	@Column(name ="F_REG_DEPT")
	private String fregDept;						//登记部门
	
	@Column(name ="F_REG_TIME")
	private Date fregTime;							//登记时间
	
	@Column(name = "F_BIDDING_NAME")
	private String fbiddingName;					//中标商名称
	
	@Column(name = "F_BID_AMOUNT")
	private Double fbidAmount;						//中标金额 
	
	@Column(name = "F_LEGAL")
	private String flegal;							//法人代表
	
	@Column(name = "F_ADDRESS")
	private String faddress;						//办公地址
	
	@Column(name = "F_LINKMAN")
	private String flinkman;						//联系人

	@Column(name = "F_PHONE")
	private String fphone;							//联系电话
	
	@Column(name = "F_REMARK")
	private String fremark;							//备注
	
	@Column(name = "F_STAUTS")
	private String fstatus;							//数据状态

	@Column(name = "F_CONTRACT_STAUTS")
	private String fContractstatus;					//是否被签订合同0-未使用，1-一已被使用
	
	@Transient 
	private List<PurcMaterialRegisterList> purcMaterList; //中标商采购明细（沈帆加，用于app）
	/**
	 * 审批
	 */
	@Column (name ="F_CHECK_STAUTS")
	private String fCheckStauts;					//审批状态
	
	@Column(name = "F_USER_NAME2")
	private String userName2;						//下环节处理人姓名
	
	@Column(name = "F_USER_CODE")
	private String fuserId;							//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;							//下节点节点编码
	
	
	
	/**
	 * 暂时不用
	 */
	@Column(name ="F_W_ID")							
	private Integer fwId;							//外键ID  链接供应商的id (F_P_ID);
	
	@Column(name = "F_START_TIME")
	private Date fstartTime;						//开标时间
	
	@Column(name = "F_TEND_UNIT_NAME")
	private String ftendUnitName;					//招标单位
		
	@Column(name = "F_TEND_UNIT_ADDR")
	private String ftendUnitAddr;					//招标单位地址
	
	@Column(name = "F_TEND_USER_TEL")
	private String ftendUserTel;					//招标联系人电话
	
	@Column(name = "F_TEND_FAX")
	private String ftendFax;						//招标单位传真
	
	@Column(name = "F_TEND_USER")
	private String ftendUser;						//招标单位联系人
	
	@Column(name = "F_AGENT_NAME")
	private String fagentName;						//代理机构
	
	@Column(name = "F_AGENT_USER")
	private String fagentUser;						//代理机构联系人
	
	@Column(name = "F_AGENT_ADDR")
	private String fagentAddr;						//代理机构地址
	
	@Column(name = "F_AGENT_USER_TEL")
	private String fagentUserTel;					//代理联系人电话
	
	@Column(name = "F_AGENT_FAX")
	private String fagentFax;						//代理机构传真

	@Column(name = "F_BID_STAUTS")
	private String fbidStatus;						//中标登记状态    
	
	@Column(name = "F_GRADE")
	private String fgrade;							//评分  3.17 方淳洲加
	
	
	
	public String getFgrade() {
		return fgrade;
	}

	public void setFgrade(String fgrade) {
		this.fgrade = fgrade;
	}

	public Integer getFrId() {
		return frId;
	}

	public void setFrId(Integer frId) {
		this.frId = frId;
	}

	@Transient
	private Integer num;							//序号(数据库中没有)

	public Integer getFbId() {
		return fbId;
	}

	public void setFbId(Integer fbId) {
		this.fbId = fbId;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public String getFbiddingCode() {
		return fbiddingCode;
	}

	public void setFbiddingCode(String fbiddingCode) {
		this.fbiddingCode = fbiddingCode;
	}

	public String getFregUserId() {
		return fregUserId;
	}

	public void setFregUserId(String fregUserId) {
		this.fregUserId = fregUserId;
	}

	public String getFregUserNAME() {
		return fregUserNAME;
	}

	public void setFregUserNAME(String fregUserNAME) {
		this.fregUserNAME = fregUserNAME;
	}

	public String getFregDeptId() {
		return fregDeptId;
	}

	public void setFregDeptId(String fregDeptId) {
		this.fregDeptId = fregDeptId;
	}

	public String getFregDept() {
		return fregDept;
	}

	public void setFregDept(String fregDept) {
		this.fregDept = fregDept;
	}

	public Date getFregTime() {
		return fregTime;
	}

	public void setFregTime(Date fregTime) {
		this.fregTime = fregTime;
	}

	public Double getFbidAmount() {
		return fbidAmount;
	}

	public void setFbidAmount(Double fbidAmount) {
		this.fbidAmount = fbidAmount;
	}

	public String getFlegal() {
		return flegal;
	}

	public void setFlegal(String flegal) {
		this.flegal = flegal;
	}

	public String getFaddress() {
		return faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	public String getFlinkman() {
		return flinkman;
	}

	public void setFlinkman(String flinkman) {
		this.flinkman = flinkman;
	}

	public String getFphone() {
		return fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	public String getFremark() {
		return fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
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

	public Integer getFwId() {
		return fwId;
	}

	public void setFwId(Integer fwId) {
		this.fwId = fwId;
	}

	public String getFbiddingName() {
		return fbiddingName;
	}

	public void setFbiddingName(String fbiddingName) {
		this.fbiddingName = fbiddingName;
	}

	public Date getFstartTime() {
		return fstartTime;
	}

	public void setFstartTime(Date fstartTime) {
		this.fstartTime = fstartTime;
	}

	public String getFtendUnitName() {
		return ftendUnitName;
	}

	public void setFtendUnitName(String ftendUnitName) {
		this.ftendUnitName = ftendUnitName;
	}

	public String getFtendUnitAddr() {
		return ftendUnitAddr;
	}

	public void setFtendUnitAddr(String ftendUnitAddr) {
		this.ftendUnitAddr = ftendUnitAddr;
	}

	public String getFtendUserTel() {
		return ftendUserTel;
	}

	public void setFtendUserTel(String ftendUserTel) {
		this.ftendUserTel = ftendUserTel;
	}

	public String getFtendFax() {
		return ftendFax;
	}

	public void setFtendFax(String ftendFax) {
		this.ftendFax = ftendFax;
	}

	public String getFtendUser() {
		return ftendUser;
	}

	public void setFtendUser(String ftendUser) {
		this.ftendUser = ftendUser;
	}

	public String getFagentName() {
		return fagentName;
	}

	public void setFagentName(String fagentName) {
		this.fagentName = fagentName;
	}

	public String getFagentUser() {
		return fagentUser;
	}

	public void setFagentUser(String fagentUser) {
		this.fagentUser = fagentUser;
	}

	public String getFagentAddr() {
		return fagentAddr;
	}

	public void setFagentAddr(String fagentAddr) {
		this.fagentAddr = fagentAddr;
	}

	public String getFagentUserTel() {
		return fagentUserTel;
	}

	public void setFagentUserTel(String fagentUserTel) {
		this.fagentUserTel = fagentUserTel;
	}

	public String getFagentFax() {
		return fagentFax;
	}

	public void setFagentFax(String fagentFax) {
		this.fagentFax = fagentFax;
	}

	public String getFbidStatus() {
		return fbidStatus;
	}

	public void setFbidStatus(String fbidStatus) {
		this.fbidStatus = fbidStatus;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getfContractstatus() {
		return fContractstatus;
	}

	public void setfContractstatus(String fContractstatus) {
		this.fContractstatus = fContractstatus;
	}

	@Override
	public String getJoinTable() {
		
		return "T_BIDDING_REGIST";
	}

	@Override
	public String getEntryId() {
		
		return String.valueOf(fbId);
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
		
		this.fstatus=status;
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
		
		return fbId;
	}

	@Override
	public String getUserId() {
		
		return fregUserId;
	}

	@Override
	public String getNextCheckKey() {
		
		return nCode;
	}

	public List<PurcMaterialRegisterList> getPurcMaterList() {
		return purcMaterList;
	}

	public void setPurcMaterList(List<PurcMaterialRegisterList> purcMaterList) {
		this.purcMaterList = purcMaterList;
	}

}
