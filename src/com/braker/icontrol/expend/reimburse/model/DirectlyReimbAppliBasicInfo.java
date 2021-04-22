package com.braker.icontrol.expend.reimburse.model;

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

import com.braker.common.entity.BaseEntity;
import com.braker.common.entity.CheckEntity;
import com.braker.common.entity.EntityDao;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;


/**
 * 直接报销申请基本信息model
 * @author 叶崇晖
 * @createtime 2018-08-03
 * @updatetime 2018-08-03
 */
@Entity
@Table(name = "T_DIRECTLY_REIMB_APPLI_BASIC_INFO")
public class DirectlyReimbAppliBasicInfo extends BaseEntity implements EntityDao ,CheckEntity{
	@Id
	@Column(name = "F_DR_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer drId;			//主键ID
	
	@Column(name = "F_DR_CODE")
	private String drCode;			//报销单编号
	
	@Column(name = "F_PRO_DETAIL_ID")
	private Integer proDetailId;	//项目支出明细id
	
	@Column(name = "F_USER")
	private String user;			//报销人ID
	
	@Column(name = "F_USER_NAME")
	private String userName;		//报销人名称
	
	@Column(name = "F_DEPT")
	private String dept;			//报销部门ID
	
	@Column(name = "F_DEPT_NAME")
	private String deptName;		//报销部门名称
	
	@Column(name = "F_REQ_TIME")
	private Date reqTime;			//报销时间
	
	@Column(name = "F_AMOUNT")
	private Double amount;			//报销总金额
	
	@Column(name = "F_INDEX_NAME")
	private String indexName;		//预算指标
	
	@Column(name = "F_INDEX_ID")
	private Integer indexId;		//预算指标Id
	
	@Column(name = "F_INDEX_TYPE")
	private String indexType;		//预算指标类型0位基本支出指标，1位项目支出指标
	
	@Column(name = "F_INDEX_AMOUNT")
	private Double indexAmount;		//可用预算金额
	
	@Column(name = "F_REIMB_TYPE")
	private String type;			//报销类型
	
	@Column(name = "F_REASON")
	private String reason;			//报销事由
	
	@Column(name = "F_FLOW_STAUTS")
	private String flowStauts;		//审批状态
	
	@Column(name = "F_STAUTS")
	private String stauts;			//报销状态
	
	@Column(name = "F_USER_NAME2")
	private String userName2;		//下环节处理人姓名
	
	@Column(name = "F_USER_ID")
	private String fuserId;			//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;			//下节点节点编码
	
	@Column(name = "F_CASHIER_TYPE")
	private String cashierType;		//出纳受理状态0未受理1已受理
	
	@Column(name = "F_EXT_1")
	private String summary;			//报销摘要(扩展字段)
	
	@Column(name = "F_FUND_SOURCE")
	private String fundSource;		//资金性质：0-财政拨款收入、1-教育事业收入、2-科研事业收入、3-非同级拨款收入、4-其他收入、5-利息收入

	@Column(name = "F_ECONOMIC_CODE")
	private String economicCode;	//资金来源：经济科目支出主键
	
	@Column(name = "F_BANK_ID")
	private String bankAccountId;	//银行账户字典id
	
	@Column(name = "F_BANK_NAME")
	private String bankAccountName;	//银行账户字典名称
	
	@Column(name = "F_WITH_LOAN")
	private Integer withLoan;		//是否冲销借款
	
	@ManyToOne
	@JoinColumn(name = "F_LAON_ID")
	private LoanBasicInfo loan;		//借款单id

	@Column(name = "F_CX_AMOUNT")
	private Double cxAmount;			//冲销金额
	
	@Column(name = "F_CASHIER_TIME")
	private Date cashierTime;		//出纳受理时间 2021-02-26沈帆加
	
	@Column(name = "F_SPECIAL_MATTER")
	private String spMatter;		//直接报销特殊事项    0否     1是 2021-04-15赵孟雷加
	
	@Column(name = "F_DIRECTLY_TYPE")
	private String dirType;		//直接报销类型  3种    人员经费      日常运行公用经费    其他费用  2021-04-15赵孟雷加
	
	@Transient
	private Integer num;			//序号(数据库中没有)
	
	@Transient
	private Date reqTime1;			//报销时间开始(查询用)
	
	@Transient
	private Date reqTime2;			//报销时间结束(查询用)
	
	@Transient
	private Double pfAmount; 		//预算批复金额
	
	@Transient
	private String pfDate;			//预算批复时间
	
	@Transient
	private String pfDepartName;	//使用部门
	
	@Transient
	private String reimAmountcapital;	//报销金额小写转大写
	
	@Transient
	private Double syAmount;		//可用余额

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getCxAmount() {
		return cxAmount;
	}

	public void setCxAmount(Double cxAmount) {
		this.cxAmount = cxAmount;
	}

	public String getReimAmountcapital() {
		return reimAmountcapital;
	}

	public void setReimAmountcapital(String reimAmountcapital) {
		this.reimAmountcapital = reimAmountcapital;
	}

	public Integer getDrId() {
		return drId;
	}

	public void setDrId(Integer drId) {
		this.drId = drId;
	}

	public String getDrCode() {
		return drCode;
	}

	public void setDrCode(String drCode) {
		this.drCode = drCode;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public Double getIndexAmount() {
		return indexAmount;
	}

	public void setIndexAmount(Double indexAmount) {
		this.indexAmount = indexAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFlowStauts() {
		return flowStauts;
	}

	public void setFlowStauts(String flowStauts) {
		this.flowStauts = flowStauts;
	}

	public String getStauts() {
		return stauts;
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

	public String getCashierType() {
		return cashierType;
	}


	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	public Date getReqTime1() {
		return reqTime1;
	}

	public void setReqTime1(Date reqTime1) {
		this.reqTime1 = reqTime1;
	}

	public Date getReqTime2() {
		return reqTime2;
	}

	public void setReqTime2(Date reqTime2) {
		this.reqTime2 = reqTime2;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@Override
	@Transient
	public String getJoinTable() {
		return "T_DIRECTLY_REIMB_APPLI_BASIC_INFO";
	}

	@Override
	@Transient
	public String getEntryId() {
		return String.valueOf(drId);
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
	public void setNextCheckKey(String nCode) {
		
		this.nCode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.flowStauts=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return flowStauts;
	}
	@Override
	public void setStauts(String status) {
		
		this.stauts=status;
	}

	@Override
	public String getBeanCode() {
		
		return drCode;
	}
	@Override
	public Integer getBeanId() {
		
		return drId;
	}

	@Override
	public String getNextCheckKey() {
		
		return nCode;
	}

	@Override
	public void setCashierType(String cashierType) {
		
		this.cashierType=cashierType;
	}
	@Override
	public String getUserId() {
		
		return user;
	}

	public Double getPfAmount() {
		return pfAmount;
	}

	public void setPfAmount(Double pfAmount) {
		this.pfAmount = pfAmount;
	}

	public String getPfDate() {
		return pfDate;
	}

	public void setPfDate(String pfDate) {
		this.pfDate = pfDate;
	}

	public String getPfDepartName() {
		return pfDepartName;
	}

	public void setPfDepartName(String pfDepartName) {
		this.pfDepartName = pfDepartName;
	}

	public Double getSyAmount() {
		return syAmount;
	}

	public void setSyAmount(Double syAmount) {
		this.syAmount = syAmount;
	}

	public Integer getProDetailId() {
		return proDetailId;
	}

	public void setProDetailId(Integer proDetailId) {
		this.proDetailId = proDetailId;
	}

	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getEconomicCode() {
		return economicCode;
	}

	public void setEconomicCode(String economicCode) {
		this.economicCode = economicCode;
	}

	public Integer getWithLoan() {
		return withLoan;
	}

	public void setWithLoan(Integer withLoan) {
		this.withLoan = withLoan;
	}

	public LoanBasicInfo getLoan() {
		return loan;
	}

	public void setLoan(LoanBasicInfo loan) {
		this.loan = loan;
	}

	public String getSpMatter() {
		return spMatter;
	}

	public void setSpMatter(String spMatter) {
		this.spMatter = spMatter;
	}

	public String getDirType() {
		return dirType;
	}

	public void setDirType(String dirType) {
		this.dirType = dirType;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_DR_CODE";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fuserId;
	}

	public Date getCashierTime() {
		return cashierTime;
	}

	public void setCashierTime(Date cashierTime) {
		this.cashierTime = cashierTime;
	}
	
	
}
