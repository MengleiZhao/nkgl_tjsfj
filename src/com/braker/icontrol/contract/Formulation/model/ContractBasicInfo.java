package com.braker.icontrol.contract.Formulation.model;

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
import com.braker.core.model.Lookups;

/**
 * 合同基本信息表
 * @author 陈睿超
 *
 */
@Entity
@Table(name = "T_CONTRACT_BASIC_INFO")
public class ContractBasicInfo extends BaseEntity implements EntityDao,CheckEntity{

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_CONT_ID")
	private Integer fcId;
	
	@Column(name ="F_CONT_CODE")
	private String fcCode;//合同编号（流水号）
	
	@Column(name = "F_CONT_TYPE")
	private String fcType;//合同分类, HTFL-01(采购合同),HTFL-02(非采购合同)
	
	@Column(name="F_CONT_TITLE")
	private String fcTitle;//合同名称
	
	@Column(name ="F_CONT_NUM")
	private String fcNum;//合同份数
	
	@Column(name ="F_CONTRACTOR")
	private String fContractor;//签约方名称
	
	@ManyToOne
	@JoinColumn(name ="F_CONT_STYLE",referencedColumnName ="lookupscode")
	private Lookups fContStyle;//合同形式  1.政府标准合同 2.本单位范本合同 3.部门拟制合同 4.对方拟制合同
	
	@Transient
	private String contstyle;//合同形式（显示用）1.政府标准合同 2.本单位范本合同 3.部门拟制合同 4.对方拟制合同
	
	@Column(name = "F_CONT_AMOUNT_MAX")
	private String fcAmountMax;//合同金额大写(元)（收入、支出用）

	@Column(name = "F_AMOUNT")
	private String fcAmount;//合同金额小写(元)（收入、支出用）
	
	@Column(name = "F_PLAN_TOTAL_AMOUNT")
	private Double fPlanTotalAmount;//合同付款合计金额(元)（收入、支出用）
	
	@ManyToOne
	@JoinColumn(name ="F_PAYTYPE",referencedColumnName="lookupscode")
	private Lookups fPayType;//付款方式 1.支票 2.电汇 3.银行转账 4.其他
	
	@Column(name = "F_PAYTYPE_REMARK")
	private String payTypeRemark;//付款方式其他选项文字说明

	@Transient
	private String payType;//付款方式（显示用）1.支票 2.电汇 3.银行转账 4.其他
	
	@Column(name ="F_PURCH_NAME")
	private String fPurchName;//采购订单名称
	
	@Column(name ="F_PURCH_NO")
	private String fPurchNo;//采购订单ID（支出用）
	
	@Column (name ="F_P_ITEMS_NAME")
	private String fpItemsName;	//品目名称   采购这边带出来
	
	@Column(name = "F_CG_TOTALAMOUNT")
	private String fcgtotalPrice;//中标商采购金额（元）
	
	@Column(name ="F_BUDGET_INDEX_CODE")
	private Integer fBudgetIndexCode;//预算指标ID（支出用）
	
	@Column(name ="F_BUDGET_INDEX_NAME")
	private String fBudgetIndexName;//预算指标名称（支出用）
	
	@Column(name = "F_PRO_DETAIL_ID")
	private Integer proDetailId;	//项目支出明细id
	
	@Column(name ="F_AVAILABLE_AMOUNT")
	private String fAvailableAmount;//指标可用金额（支出用）
	
	@Column(name ="F_INDEX_TYPE")
	private String indexType;//指标类型（支出用）
	
	@Column(name = "F_OPERATOR")
	private String fOperator;//申请人
	
	@Column(name ="F_OPERATOR_ID")
	private String fOperatorId;//申请人id

	@Column(name ="F_DEPT_NAME")
	private String fDeptName;//所属部门
	
	@Column(name ="F_DEPT_ID")
	private String fDeptId;//所属部门
	
	@Column(name = "F_REQ_TIME")
	private Date fReqtIME;//申请时间
	
	@Column(name="F_REMARK")
	private String fRemark;//合同说明
	
	@Column(name= "F_CONT_START_TIME")
	private Date fContStartTime;// 合同开始时间
	
	@Column(name ="F_CONT_END_TIME")
	private Date fContEndTime;//合同结束时间
	
	@Column(name ="F_SIGN_USER")
	private String fSignUser;//合同签署人
	
	@Column(name ="F_SIGN_TIME")
	private Date fSignTime;//合同签署时间
	
	@Column(name ="F_MARGIN_AMOUNT")
	private String fMarginAmount;//保证金金额（元）
	
	@Column(name ="F_IS_AUTHOR")
	private String fIsAuthor;//是否委托授权
	
	@Column(name ="F_WARRANTY_PERIOD")
	private String fWarrantyPeriod;//质保期
	
	@Column(name ="F_FLOW_STAUTS")
	private String fFlowStauts;//审批状态
	
	@Column(name ="F_CONT_STAUTS")
	private String fContStauts;//合同状态 -1.已终止 -9.已完结（不是终止）1.拟定 3.已结项 5.已归档 9.备案 99.已删除

	@Column(name ="F_UPDATE_STATUS")
	private String fUpdateStatus;//合同变更状态 1-有变更	0-未变更	 2020.02.17陈睿超加

	@Column(name ="F_DISPUTE_STATUS")
	private String fDisputeStatus;//合同纠纷状态 1-有纠纷	 0-没纠纷	 2020.02.17陈睿超加
	
	@Column(name ="F_TOFILES_STATUS")
	private Integer fToFilesStatus;//归档状态 0-未归档，1-已归档，
	
	@Column(name ="F_USER_NAME")
	private String fUserName;//下环节处理人 姓名
	
	@Column (name ="F_USER_CODE")
	private String fUserCode;//下环节处理人 编码
	
	@Column(name ="F_PRO_CODE")
	private String fProCode;//项目编号
	
	@Column(name ="F_N_CODE")
	private String fNCode;//下节点节点编码
	 
	@Column(name ="F_PAY_STAUTS")
	private String fPayStauts;//质保金是否退还0:未退还     1:已退还       2:退还中
	
	@Column(name ="F_INCOME_STAUTS")
	private String fIncomeStauts;//收入保证金是否到账0-未确认，1-已确认  2021.01.21陈睿超添加
	
	@Column(name ="F_INCOME_DATE")
	private Date fIncomeDate;//收入保证金到账日期  2021.01.21陈睿超添加
	
	@Column(name = "F_FUND_SOURCE")
	private String fundSource;//资金来源：0-财政拨款收入、1-教育事业收入、2-科研事业收入、3-非同级拨款收入、4-其他收入、5-利息收入
	
	@Column(name = "F_BANK_NAME")
	private String bankAccountName;//银行账户字典名称
	
	@Column(name = "F_BANK_ID")
	private String bankAccountId;//银行账户字典id
	
	@Column(name = "F_ASSIS_DEPT_ID")
	private String assisDeptId;	//协调部门id
	
	@Column(name = "F_ASSIS_DEPT_NAME")
	private String assisDeptName;//协调部门名称
	
	@Column(name = "F_STANDARD")
	private Integer standard;//是否制式合同 0:否，1：是
	
	@Column(name = "F_SEALED_STATUS")
	private String fsealedStatus;//是否盖章	0-未盖章	 	1-已盖章
	
	@Column(name ="F_APPROVE_TIME")
	private Date fApproveTime;//合同审核通过时间

	@Column(name ="F_ALL_AMOUNT")
	private String fAllAmount;//已付总额	2021.1.27方淳洲加
	
	@Column(name ="F_NOT_ALL_AMOUNT")
	private String fNotAllAmountL;//未付总额	2021.1.27方淳洲加
	
	@Column(name ="F_KEEP_TIME")
	private String keepTime;//合同履约期	2021.1.27方淳洲加
	
	@Column(name ="F_INCOME_CHANGE_STAUTS")
	private String fIncomeChangeStauts;//合同保证金是否变更 (0-未变更，1-已变更)  2021.03.26沈帆添加
	
	@Transient
	private int number;//序号
	@Transient
	private Integer fPlanId;//付款计划的id
	@Transient
	private Date fRecePlanTime;//计划付款时间
	@Transient
	private String fReceProperty;//付款性质
	@Transient
	private String fReceCondition;//付款条件
	@Transient
	private String fRecePlanAmount;//预计付款金额
	@Transient
	private Integer datenumber;//距离预计付款时间还有几天
	@Transient
	private String fRecUser;//登记人
	@Transient
	private Date fRecTime;//登记时间
	@Transient
	private String fPayRemark;//付款说明
	@Transient
	private String fWarType;//保证金类型
	@Transient
	private String fPayAmount;//付款金额
	@Transient
	private String percentage;//执行百分比
	@Transient
	private String contractor;//执行百分比
	@Transient
	private Double percentageTempStart;//搜索栏查询百分比开始
	@Transient
	private Double percentageTempEnd;//搜索栏查询百分比结束
	@Transient
	private Double cAmountBegin;//合同金额开始区间
	@Transient
	private Double cAmountEnd;//合同金额戒指区间
	@Transient
	private Integer accNumber;//验收次数
	@Transient
	private String searchTitle;//搜索条件	2021.1.27方淳洲加
	@Transient
	private String inStatus;//页面进入状态  1-用印进入   2-台账进入	2021.1.27方淳洲加
	@Transient
	private String checkSts;//报销状态  
	@Transient
	private Double reimAmount;//报销金额
	@Transient
	private Date reimTime;//报销时间
	
	
	public String getCheckSts() {
		return checkSts;
	}

	public void setCheckSts(String checkSts) {
		this.checkSts = checkSts;
	}

	public Double getReimAmount() {
		return reimAmount;
	}

	public void setReimAmount(Double reimAmount) {
		this.reimAmount = reimAmount;
	}

	public Date getReimTime() {
		return reimTime;
	}

	public void setReimTime(Date reimTime) {
		this.reimTime = reimTime;
	}

	public String getInStatus() {
		return inStatus;
	}

	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}

	public String getKeepTime() {
		return keepTime;
	}


	public void setKeepTime(String keepTime) {
		this.keepTime = keepTime;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public Integer getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(Integer accNumber) {
		this.accNumber = accNumber;
	}

	public String getfProCode() {
		return fProCode;
	}

	public void setfProCode(String fProCode) {
		this.fProCode = fProCode;
	}

	public String getfNCode() {
		return fNCode;
	}

	public void setfNCode(String fNCode) {
		this.fNCode = fNCode;
	}

	public String getfPayStauts() {
		return fPayStauts;
	}

	public void setfPayStauts(String fPayStauts) {
		this.fPayStauts = fPayStauts;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getfRecUser() {
		return fRecUser;
	}

	public void setfRecUser(String fRecUser) {
		this.fRecUser = fRecUser;
	}

	public Date getfRecTime() {
		return fRecTime;
	}

	public void setfRecTime(Date fRecTime) {
		this.fRecTime = fRecTime;
	}

	public String getfPayRemark() {
		return fPayRemark;
	}

	public void setfPayRemark(String fPayRemark) {
		this.fPayRemark = fPayRemark;
	}

	public String getfWarType() {
		return fWarType;
	}

	public void setfWarType(String fWarType) {
		this.fWarType = fWarType;
	}

	public String getfPayAmount() {
		return fPayAmount;
	}

	public void setfPayAmount(String fPayAmount) {
		this.fPayAmount = fPayAmount;
	}

	public Integer getfPlanId() {
		return fPlanId;
	}

	public void setfPlanId(Integer fPlanId) {
		this.fPlanId = fPlanId;
	}

	public Date getfRecePlanTime() {
		return fRecePlanTime;
	}

	public void setfRecePlanTime(Date fRecePlanTime) {
		this.fRecePlanTime = fRecePlanTime;
	}

	public String getfReceProperty() {
		return fReceProperty;
	}

	public void setfReceProperty(String fReceProperty) {
		this.fReceProperty = fReceProperty;
	}

	public String getfReceCondition() {
		return fReceCondition;
	}

	public void setfReceCondition(String fReceCondition) {
		this.fReceCondition = fReceCondition;
	}

	public String getfRecePlanAmount() {
		return fRecePlanAmount;
	}

	public void setfRecePlanAmount(String fRecePlanAmount) {
		this.fRecePlanAmount = fRecePlanAmount;
	}

	public Integer getDatenumber() {
		return datenumber;
	}

	public void setDatenumber(Integer datenumber) {
		this.datenumber = datenumber;
	}
	public String getfAllAmount() {
		return fAllAmount;
	}

	public void setfAllAmount(String fAllAmount) {
		this.fAllAmount = fAllAmount;
	}

	public String getfNotAllAmountL() {
		return fNotAllAmountL;
	}

	public void setfNotAllAmountL(String fNotAllAmountL) {
		this.fNotAllAmountL = fNotAllAmountL;
	}

	public String getfUserName() {
		return fUserName;
	}

	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	public String getfUserCode() {
		return fUserCode;
	}

	public void setfUserCode(String fUserCode) {
		this.fUserCode = fUserCode;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

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

	public String getFcType() {
		return fcType;
	}

	public void setFcType(String fcType) {
		this.fcType = fcType;
	}

	public String getFcTitle() {
		return fcTitle;
	}

	public void setFcTitle(String fcTitle) {
		this.fcTitle = fcTitle;
	}

	public String getFcNum() {
		return fcNum;
	}

	public void setFcNum(String fcNum) {
		this.fcNum = fcNum;
	}

	public String getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(String fcAmount) {
		this.fcAmount = fcAmount;
	}

	public String getfPurchNo() {
		return fPurchNo;
	}

	public void setfPurchNo(String fPurchNo) {
		this.fPurchNo = fPurchNo;
	}


	public Integer getfBudgetIndexCode() {
		return fBudgetIndexCode;
	}

	public void setfBudgetIndexCode(Integer fBudgetIndexCode) {
		this.fBudgetIndexCode = fBudgetIndexCode;
	}

	public String getfBudgetIndexName() {
		return fBudgetIndexName;
	}

	public void setfBudgetIndexName(String fBudgetIndexName) {
		this.fBudgetIndexName = fBudgetIndexName;
	}

	public String getfAvailableAmount() {
		return fAvailableAmount;
	}

	public void setfAvailableAmount(String fAvailableAmount) {
		this.fAvailableAmount = fAvailableAmount;
	}

	public String getfOperator() {
		return fOperator;
	}

	public void setfOperator(String fOperator) {
		this.fOperator = fOperator;
	}


	public Date getfReqtIME() {
		return fReqtIME;
	}

	public void setfReqtIME(Date fReqtIME) {
		this.fReqtIME = fReqtIME;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	public Date getfContStartTime() {
		return fContStartTime;
	}

	public void setfContStartTime(Date fContStartTime) {
		this.fContStartTime = fContStartTime;
	}

	public Date getfContEndTime() {
		return fContEndTime;
	}

	public void setfContEndTime(Date fContEndTime) {
		this.fContEndTime = fContEndTime;
	}

	public String getfSignUser() {
		return fSignUser;
	}

	public void setfSignUser(String fSignUser) {
		this.fSignUser = fSignUser;
	}

	public Date getfSignTime() {
		return fSignTime;
	}

	public void setfSignTime(Date fSignTime) {
		this.fSignTime = fSignTime;
	}

	public String getfMarginAmount() {
		return fMarginAmount;
	}

	public void setfMarginAmount(String fMarginAmount) {
		this.fMarginAmount = fMarginAmount;
	}

	public String getfIsAuthor() {
		return fIsAuthor;
	}

	public void setfIsAuthor(String fIsAuthor) {
		this.fIsAuthor = fIsAuthor;
	}

	public String getfWarrantyPeriod() {
		return fWarrantyPeriod;
	}

	public void setfWarrantyPeriod(String fWarrantyPeriod) {
		this.fWarrantyPeriod = fWarrantyPeriod;
	}

	public String getfFlowStauts() {
		return fFlowStauts;
	}

	public void setfFlowStauts(String fFlowStauts) {
		this.fFlowStauts = fFlowStauts;
	}

	public String getfContStauts() {
		return fContStauts;
	}

	public void setfContStauts(String fContStauts) {
		this.fContStauts = fContStauts;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getfOperatorId() {
		return fOperatorId;
	}

	public void setfOperatorId(String fOperatorId) {
		this.fOperatorId = fOperatorId;
	}

	@Transient
	@Override
	public String getJoinTable() {
		return "T_CONTRACT_BASIC_INFO";
	}

	@Transient
	@Override
	public String getEntryId() {
		return String.valueOf(fcId);
	}

	public String getfPurchName() {
		return fPurchName;
	}

	public void setfPurchName(String fPurchName) {
		this.fPurchName = fPurchName;
	}

	public Double getPercentageTempStart() {
		return percentageTempStart;
	}

	public void setPercentageTempStart(Double percentageTempStart) {
		this.percentageTempStart = percentageTempStart;
	}

	public Double getPercentageTempEnd() {
		return percentageTempEnd;
	}

	public void setPercentageTempEnd(Double percentageTempEnd) {
		this.percentageTempEnd = percentageTempEnd;
	}

	public Double getcAmountBegin() {
		return cAmountBegin;
	}

	public void setcAmountBegin(Double cAmountBegin) {
		this.cAmountBegin = cAmountBegin;
	}

	public Double getcAmountEnd() {
		return cAmountEnd;
	}

	public void setcAmountEnd(Double cAmountEnd) {
		this.cAmountEnd = cAmountEnd;
	}
	
	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getAssisDeptId() {
		return assisDeptId;
	}

	public void setAssisDeptId(String assisDeptId) {
		this.assisDeptId = assisDeptId;
	}

	public String getAssisDeptName() {
		return assisDeptName;
	}

	public void setAssisDeptName(String assisDeptName) {
		this.assisDeptName = assisDeptName;
	}

	public Integer getStandard() {
		return standard;
	}

	public void setStandard(Integer standard) {
		this.standard = standard;
	}

	public String getFsealedStatus() {
		return fsealedStatus;
	}

	public void setFsealedStatus(String fsealedStatus) {
		this.fsealedStatus = fsealedStatus;
	}

	public Integer getfToFilesStatus() {
		return fToFilesStatus;
	}

	public void setfToFilesStatus(Integer fToFilesStatus) {
		this.fToFilesStatus = fToFilesStatus;
	}

	public String getPayTypeRemark() {
		return payTypeRemark;
	}

	public void setPayTypeRemark(String payTypeRemark) {
		this.payTypeRemark = payTypeRemark;
	}
	
	public Double getfPlanTotalAmount() {
		return fPlanTotalAmount;
	}

	public void setfPlanTotalAmount(Double fPlanTotalAmount) {
		this.fPlanTotalAmount = fPlanTotalAmount;
	}

	public String getFpItemsName() {
		return fpItemsName;
	}

	public void setFpItemsName(String fpItemsName) {
		this.fpItemsName = fpItemsName;
	}

	public String getFcgtotalPrice() {
		return fcgtotalPrice;
	}

	public void setFcgtotalPrice(String fcgtotalPrice) {
		this.fcgtotalPrice = fcgtotalPrice;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	@Override
	public void setNextCheckUserName(String userName) {
		
		this.fUserName=userName;
	}
	public String getfUpdateStatus() {
		return fUpdateStatus;
	}

	public void setfUpdateStatus(String fUpdateStatus) {
		this.fUpdateStatus = fUpdateStatus;
	}

	public String getfDisputeStatus() {
		return fDisputeStatus;
	}

	public void setfDisputeStatus(String fDisputeStatus) {
		this.fDisputeStatus = fDisputeStatus;
	}

	@Override
	public void setNextCheckUserId(String userId) {
		
		this.fUserCode=userId;
	}
	@Override
	public void setNextCheckKey(String nCode) {
		
		this.fNCode=nCode;
	}

	@Override
	public void setCheckStauts(String checkStatus) {
		
		this.fFlowStauts=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fFlowStauts;
	}

	@Override
	public String getBeanCode() {
		
		return fcCode;
	}
	@Override
	public Integer getBeanId() {
		
		return fcId;
	}

	@Override
	public String getNextCheckKey() {
		
		return fNCode;
	}

	@Override
	public void setCashierType(String status) {
		
		
	}

	@Override
	public String getUserId() {
		
		return fOperatorId;
	}

	@Override
	public void setStauts(String status) {
		this.fContStauts=status;
		
		
	}

	public Lookups getfContStyle() {
		return fContStyle;
	}

	public void setfContStyle(Lookups fContStyle) {
		this.fContStyle = fContStyle;
	}

	public String getFcAmountMax() {
		return fcAmountMax;
	}

	public void setFcAmountMax(String fcAmountMax) {
		this.fcAmountMax = fcAmountMax;
	}

	public Lookups getfPayType() {
		return fPayType;
	}

	public void setfPayType(Lookups fPayType) {
		this.fPayType = fPayType;
	}

	public String getContstyle() {
		if(fContStyle!=null){
			return fContStyle.getName();
		}
		return contstyle;
	}

	public void setContstyle(String contstyle) {
		this.contstyle = contstyle;
	}

	public String getPayType() {
		if(fPayType!=null){
			return fPayType.getName();
		}
		return contstyle;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getfContractor() {
		return fContractor;
	}

	public void setfContractor(String fContractor) {
		this.fContractor = fContractor;
	}

	public Integer getProDetailId() {
		return proDetailId;
	}

	public void setProDetailId(Integer proDetailId) {
		this.proDetailId = proDetailId;
	}

	public String getfDeptId() {
		return fDeptId;
	}

	public void setfDeptId(String fDeptId) {
		this.fDeptId = fDeptId;
	}

	public String getfDeptName() {
		return fDeptName;
	}

	public void setfDeptName(String fDeptName) {
		this.fDeptName = fDeptName;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_CONT_CODE";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fUserCode;
	}

	public Date getfApproveTime() {
		return fApproveTime;
	}

	public void setfApproveTime(Date fApproveTime) {
		this.fApproveTime = fApproveTime;
	}


	public String getfIncomeStauts() {
		return fIncomeStauts;
	}

	public void setfIncomeStauts(String fIncomeStauts) {
		this.fIncomeStauts = fIncomeStauts;
	}

	public Date getfIncomeDate() {
		return fIncomeDate;
	}

	public void setfIncomeDate(Date fIncomeDate) {
		this.fIncomeDate = fIncomeDate;
	}

	public String getfIncomeChangeStauts() {
		return fIncomeChangeStauts;
	}

	public void setfIncomeChangeStauts(String fIncomeChangeStauts) {
		this.fIncomeChangeStauts = fIncomeChangeStauts;
	}

	
}
