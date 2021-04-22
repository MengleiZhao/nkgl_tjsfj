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
 * 报销报销基本信息model
 * @author 叶崇晖
 * @createtime 2018-08-08
 * @updatetime 2018-08-08
 */
@Entity
@Table(name = "T_REIMB_APPLI_BASIC_INFO")
public class ReimbAppliBasicInfo extends BaseEntity implements EntityDao ,CheckEntity{
	
	
	@Id
	@Column(name = "F_R_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer rId;			//主键ID
	
	@Column(name = "F_R_CODE")
	private String rCode;			//报销单编号
	
	@Column(name = "F_G_CODE")
	private String gCode;			//申请单号（流水号）
	
	@Column(name = "F_CONT_ID")
	private String 	payId;			//合同单ID
	
	@Column(name = "F_CONT_CODE")
	private String contCode;		//合同单号（流水号）
	
	@Column(name="F_CONT_TITLE")
	private String fcTitle;        //合同名称
	
	@Column(name = "F_USER")
	private String user;			//报销人id
	
	@Column(name = "F_USER_NAME")
	private String userName;		//报销人名称
	
	@Column(name = "F_DEPT")
	private String dept;			//报销部门id
	
	@Column(name = "F_DEPT_NAME")
	private String deptName;		//报销部门名称
	
	@Column(name = "F_REQ_TIME")
	private Date reimburseReqTime;	//报销时间
	
	@Column(name = "F_AMOUNT")
	private Double amount;			//报销总金额(合同报销时为付款金额)
	
	@Column(name = "F_INDEX_NAME")
	private String indexName;		//预算指标
	
	@Column(name = "F_INDEX_ID")
	private Integer indexId;		//预算指标Id
	
	@Column(name = "F_PRO_DETAIL_ID")
	private Integer proDetailId;	//项目支出明细id
	
	@Column(name = "F_INDEX_TYPE")
	private String indexType;		//预算指标类型0位基本支出指标，1位项目支出指标
	
	@Column(name = "F_INDEX_AMOUNT")
	private Double indexAmount;		//可用预算金额
	
	@Column(name = "F_REASON")
	private String reimburseReason;	//报销事由
	
	@Column(name = "F_REIMB_TYPE")
	private String type;			//报销事项
	
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
	private String gName;			//报销摘要(对应的事前申请摘要相同，为方便数据传输这里起名和事前申请的申请摘要gName相同)
	
	@Column(name = "F_FUND_SOURCE")
	private String fundSource;		//资金来源：0-财政拨款收入、1-教育事业收入、2-科研事业收入、3-非同级拨款收入、4-其他收入、5-利息收入
	
	@Column(name = "F_WITH_LOAN")
	private Integer withLoan;		//是否冲销借款
	
	@Column(name = "F_CX_AMOUNT")
	private Double cxAmount;			//冲销金额
	
	@Column(name = "F_FOOD_NUM")
	private Integer fFoodNum;//是否重复申报伙食费
	
	@ManyToOne
	@JoinColumn(name = "F_LAON_ID",referencedColumnName="F_L_ID")
	private LoanBasicInfo loan;		//借款单id
	
	@Column(name = "F_BANK_ID")
	private String bankAccountId;	//银行账户字典id
	
	@Column(name = "F_BANK_NAME")
	private String bankAccountName;	//银行账户字典名称
	
	@Column(name = "F_REGISTER")
	private String register;//是否生成凭证 0-未生成 1-已生成
	
	@Column(name = "F_APPLY_AMOUNT")
	private Double applyAmount;		//申请金额
	
	@Column(name = "F_UPDATE_PLAN_STATUS")
	private Integer fupdateStatus;		//行程是否有变更 0-否，1-是
	
	@Column(name = "F_UPDATE_PLAN_REASON")
	private String fupdateReason;		//变更说明
	
	/*@Column(name = "F_CASHIER")
	private String cashierStatus;*/			//出纳确认状态	（0、未确认 1、已确认	）
	
	@Column(name = "F_OUTSIDE_AMOUNT")
	private Double outsideAmount;			//城市间交通费总金额（元）
	
	@Column(name = "F_CITY_AMOUNT")
	private Double cityAmount;			//市内交通费总金额（元）
	
	@Column(name = "F_HOTEL_AMOUNT")
	private Double hotelAmount;			//住宿费总金额（元）
	
	@Column(name = "F_FOOD_AMOUNT")
	private Double foodAmount;			//伙食补助费总金额（元）
	
	@Column(name = "F_TRAVEL_TYPE")
	private String travelType;			//出差类型
	
	@Column(name = "F_RECEIVPLAN_ID")
	private String receivplanid;	//合同待付款数据id
	
	@Column(name = "F_INTANGIBLE_ID")
	private String checkintangibleAssetid;	//无形资产入账单id
	
	@Column(name = "F_FIXED_ID")
	private String checkFixedAssetid;		//固定资产入账单id
	
	@Column(name = "F_ACCEPT_ID")
	private String checkacceptid;			//验收单id
	
	@Column(name = "F_INTANGIBLE_NAME")
	private String checkintangibleName;	//无形资产入账单名字
	
	@Column(name = "F_FIXED_NAME")
	private String checkFixedName;		//固定资产入账单名字
	
	@Column(name = "F_ACCEPT_NAME")
	private String checkacceptName;			//验收单名字

	@Column(name = "F_WAREHOUSE_WARRANT_ID")
	private String checkWarehouseWarrantid;		//入库单id
	
	@Column(name = "F_WAREHOUSE_WARRANT")
	private String checkWarehouseWarrant;			//入库单名字
	
	@Column(name = "F_PRO_NAME")
	private String proName;				//项目名称2020.11.11陈睿超添加，用于往来款报销
	
	@Column(name = "F_PRO_CODE")
	private String proCode;				//项目编号2021.3.24赵孟雷添加，用于往来款报销
	
	@Column(name = "F_PAYMENT_TYPE")
	private String paymentType;		//收款方式（1、银行代发2、现金3、支票4、电汇5、公务卡）2020.12.15应马经理要求调整
	
	@Column(name = "F_CASHIER_TIME")
	private Date cashierTime;		//出纳受理时间 2021-02-26沈帆加
	
	@Column(name = "F_OPPOSITE_UNIT")
	private String oppositeUnit;        //对方单位
	
	@Transient
	private Integer num;			//序号(数据库中没有)
	
	@Transient
	private Date reqTime;			//时间(工作流中用到)
	
	@Transient
	private Date reimburseReqTime1;	//报销时间开始(查询用)
	
	@Transient
	private Date reimburseReqTime2;	//报销时间结束(查询用)
	
	@Transient
	private Double pfAmount; 		//预算批复金额
	
	@Transient
	private String pfDate;			//预算批复时间
	
	@Transient
	private String pfDepartName;	//使用部门
	
	@Transient
	private Double syAmount;		//可用余额
	
	@Transient
	private String currentParam;	//使用部门
	
	@Transient
	private String reimAmountcapital;		//报销金额大写
	
	@Transient
	private String lkName;		    //来款项目名称

	public String getOppositeUnit() {
		return oppositeUnit;
	}

	public void setOppositeUnit(String oppositeUnit) {
		this.oppositeUnit = oppositeUnit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCheckWarehouseWarrantid() {
		return checkWarehouseWarrantid;
	}

	public void setCheckWarehouseWarrantid(String checkWarehouseWarrantid) {
		this.checkWarehouseWarrantid = checkWarehouseWarrantid;
	}

	public String getCheckWarehouseWarrant() {
		return checkWarehouseWarrant;
	}

	public void setCheckWarehouseWarrant(String checkWarehouseWarrant) {
		this.checkWarehouseWarrant = checkWarehouseWarrant;
	}

	public String getReimAmountcapital() {
		return reimAmountcapital;
	}

	public void setReimAmountcapital(String reimAmountcapital) {
		this.reimAmountcapital = reimAmountcapital;
	}

	public Integer getfFoodNum() {
		return fFoodNum;
	}

	public void setfFoodNum(Integer fFoodNum) {
		this.fFoodNum = fFoodNum;
	}

	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Integer getProDetailId() {
		return proDetailId;
	}

	public void setProDetailId(Integer proDetailId) {
		this.proDetailId = proDetailId;
	}

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public String getrCode() {
		return rCode;
	}

	public void setrCode(String rCode) {
		this.rCode = rCode;
	}

	public String getgCode() {
		return gCode;
	}

	public void setgCode(String gCode) {
		this.gCode = gCode;
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

	public String getContCode() {
		return contCode;
	}

	public void setContCode(String contCode) {
		this.contCode = contCode;
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

	public Date getReimburseReqTime() {
		return reimburseReqTime;
	}

	public void setReimburseReqTime(Date reimburseReqTime) {
		this.reimburseReqTime = reimburseReqTime;
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

	public String getReimburseReason() {
		return reimburseReason;
	}

	public void setReimburseReason(String reimburseReason) {
		this.reimburseReason = reimburseReason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public Date getReimburseReqTime1() {
		return reimburseReqTime1;
	}

	public void setReimburseReqTime1(Date reimburseReqTime1) {
		this.reimburseReqTime1 = reimburseReqTime1;
	}

	public Date getReimburseReqTime2() {
		return reimburseReqTime2;
	}

	public void setReimburseReqTime2(Date reimburseReqTime2) {
		this.reimburseReqTime2 = reimburseReqTime2;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
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

	@Override
	@Transient
	public String getJoinTable() {
		return "T_REIMB_APPLI_BASIC_INFO";
	}

	@Override
	@Transient
	public String getEntryId() {
		return String.valueOf(rId);
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public Integer getFupdateStatus() {
		return fupdateStatus;
	}

	public void setFupdateStatus(Integer fupdateStatus) {
		this.fupdateStatus = fupdateStatus;
	}

	public String getFupdateReason() {
		return fupdateReason;
	}

	public void setFupdateReason(String fupdateReason) {
		this.fupdateReason = fupdateReason;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getFcTitle() {
		return fcTitle;
	}

	public void setFcTitle(String fcTitle) {
		this.fcTitle = fcTitle;
	}

	public String getCheckintangibleAssetid() {
		return checkintangibleAssetid;
	}

	public void setCheckintangibleAssetid(String checkintangibleAssetid) {
		this.checkintangibleAssetid = checkintangibleAssetid;
	}

	public String getCheckFixedAssetid() {
		return checkFixedAssetid;
	}

	public void setCheckFixedAssetid(String checkFixedAssetid) {
		this.checkFixedAssetid = checkFixedAssetid;
	}

	public String getCheckacceptid() {
		return checkacceptid;
	}

	public void setCheckacceptid(String checkacceptid) {
		this.checkacceptid = checkacceptid;
	}

	public String getCheckintangibleName() {
		return checkintangibleName;
	}

	public void setCheckintangibleName(String checkintangibleName) {
		this.checkintangibleName = checkintangibleName;
	}

	public String getCheckFixedName() {
		return checkFixedName;
	}

	public void setCheckFixedName(String checkFixedName) {
		this.checkFixedName = checkFixedName;
	}

	public String getCheckacceptName() {
		return checkacceptName;
	}

	public String getReceivplanid() {
		return receivplanid;
	}

	public void setReceivplanid(String receivplanid) {
		this.receivplanid = receivplanid;
	}

	public void setCheckacceptName(String checkacceptName) {
		this.checkacceptName = checkacceptName;
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
		
		return rCode;
	}
	@Override
	public Integer getBeanId() {
		
		return rId;
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
	
		public String getFundSource() {
			return fundSource;
		}
	
		public void setFundSource(String fundSource) {
			this.fundSource = fundSource;
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

		@Override
		public String getBeanCodeField() {
			
			return "F_R_CODE";
		}

		@Override
		public String getNextCheckUserId() {
			
			return fuserId;
		}

		public Double getCxAmount() {
			return cxAmount;
		}

		public void setCxAmount(Double cxAmount) {
			this.cxAmount = cxAmount;
		}

		public Double getOutsideAmount() {
			return outsideAmount;
		}

		public void setOutsideAmount(Double outsideAmount) {
			this.outsideAmount = outsideAmount;
		}

		public Double getCityAmount() {
			return cityAmount;
		}

		public void setCityAmount(Double cityAmount) {
			this.cityAmount = cityAmount;
		}

		public Double getHotelAmount() {
			return hotelAmount;
		}

		public void setHotelAmount(Double hotelAmount) {
			this.hotelAmount = hotelAmount;
		}

		public Double getFoodAmount() {
			return foodAmount;
		}

		public void setFoodAmount(Double foodAmount) {
			this.foodAmount = foodAmount;
		}

		public String getProName() {
			return proName;
		}

		public void setProName(String proName) {
			this.proName = proName;
		}

		public String getPaymentType() {
			return paymentType;
		}

		public void setPaymentType(String paymentType) {
			this.paymentType = paymentType;
		}

		public String getCurrentParam() {
			return currentParam;
		}

		public void setCurrentParam(String currentParam) {
			this.currentParam = currentParam;
		}

		public Date getCashierTime() {
			return cashierTime;
		}

		public void setCashierTime(Date cashierTime) {
			this.cashierTime = cashierTime;
		}

		public String getLkName() {
			return lkName;
		}

		public void setLkName(String lkName) {
			this.lkName = lkName;
		}

		public String getProCode() {
			return proCode;
		}

		public void setProCode(String proCode) {
			this.proCode = proCode;
		}
}
