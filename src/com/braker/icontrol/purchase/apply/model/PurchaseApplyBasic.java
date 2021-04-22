package com.braker.icontrol.purchase.apply.model;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="T_PURCHASE_APPLY_BASIC")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseApplyBasic extends BaseEntity implements EntityDao,CheckEntity{
	@Id
	@Column(name ="F_P_ID")
	private Integer fpId;				//主键
	
	@Column(name = "F_PL_ID")
	private Integer fplId;				//配置计划的主键id（读取）废弃
	
	@Column(name = "F_P_CODE")
	private String fpCode;				//采购批次号（读取配置计划的单据编号）
	
	@Column(name = "F_P_NAME")
	private String fpName;				//采购名称  
	
	@Column(name = "F_P_OPEN_CODE")
	private String openObjCode;				//意向公开单编号
	
	@Column (name ="F_DEPT_NAME")
	private String fDeptName;			//申报部门
	
	@Column (name ="F_DEPT_ID")
	private String fDeptId;				//申报部门id
	
	@Column (name ="F_USER")
	private String fUser;				//申报人id
	
	@Column (name ="F_USER_NAME")
	private String fUserName;			//申报人
	
	@Column (name ="F_AMOUNT")
	private Double amount;			//采购金额		3.11改为申请金额
	
	@Column (name="F_REQ_TIME")
	private Date fReqTime;				//申请时间
	
	@Column (name ="F_P_PYPE")
	private String fpPype;				//采购类型   6.10后改为采购方式
	
	@Column (name ="F_DZH_CODE")
	private String fDZHCode;				//党组会会议编号
	
	/*@ManyToOne
	@JoinColumn(name = "F_ORG_TYPE",referencedColumnName="lookupscode")
	private Lookups fOrgType;//组织形式
	
	@ManyToOne
	@JoinColumn(name = "F_EVA_METHOD",referencedColumnName="lookupscode")
	private Lookups fEvaMethod;//评价方法
	
	@ManyToOne
	@JoinColumn(name = "F_P_METHOD",referencedColumnName="lookupscode")
	private Lookups fpMethod;//采购方式
*/	
	@Column (name ="F_P_METHOD")
	private String fpMethod;			//采购方式    6.10后改为采购类型
	
	@Column (name ="F_P_ITEMS_NAME")
	private String fpItemsName;			//品目名称    6.10添加
	
	@Column (name ="F_AGENCY_NAME")
	private String fAgencyName;			//代理机构
	
	@Column (name ="F_IS_IMP")
	private String fIsImp;				//进口货物服务
	
	@Column (name ="F_IS_PERS")
	private String fIsPers;				//涉密采购
	
	@Column (name ="F_REMARK")
	private String fRemark;				//采购说明		3.11后改为备注
	
	@Column (name ="F_OTHER_REMARK")
	private String fOtherRemark;		//其他需求
	
	@Column (name ="F_CHECK_STAUTS")
	private String fCheckStauts;		//采购审批状态
	
	@Column (name ="F_STAUTS")
	private String fStauts;				//采购数据的删除状态
	
	@Column (name ="F_IS_RECEIVE")
	private String fIsReceive;			//验收状态	0-暂存 1-待验收 9-已验收 -1-已退回 -4已撤回	
	
	@Column (name ="F_IS_INQUIRY")
	private String fIsInquiry;			//询价状态   焦广兴更改为 是否论证状态 
	
	@Column(name = "F_ORG_NAME")
	private String fOrgName;        	//中标组织名称
	
	@Column(name = "F_BID_AMOUNT")
	private Double fbidAmount;			//中标金额
	
	@Column (name ="F_BID_STAUTS")
	private String fbidStauts;			//中标状态	焦广兴更改为 登记状态
	
	@Column (name ="F_CONFIRM_STAUTS")
	private String fConfirmStauts;		//采购方式确认状态 0-未确认，1-已确认	2020.06.15陈睿超加
	
	@Column (name ="F_IS_IMP_FILES")
	private String fIsImpFiles;			//进口产品是否上传附件  1:上传       0：未上传
	
	@Column (name ="F_EVAL_STAUTS")
	private String fevalStauts;			//(供应商)评价状态

	@Column (name ="F_PAY_STAUTS")
	private String fpayStauts;			//付款申请的审批状态
	
	@Column (name ="F_TENDING_STAUTS")
	private String ftendingStauts;		//招标状态  10月23更改需求 不允许流标  以前功能存在流标

	@Column(name = "F_CONTRACT_STAUTS") 
	private String fContractstatus;		//合同签订状态0-未签订 1-签订中 2-已全部签订
	
	@Column(name = "F_INDEX_ID")
	private Integer indexId;			//支出科目ID
	
	@Column (name ="F_INDEX_CODE")
	private String indexCode;			//支出科目编码
	
	@Column (name ="F_INDEX_NAME")
	private String indexName;			//支出科目名称
	
	@Column (name ="F_INDEX_TYPE")
	private String indexType;			//支出科目类型
	
	@Column (name ="F_BUDGET_PRICE_BASIS")
	private String budgetPriceBasis;	//预算价格依据
	
	@Column (name ="F_PRO_DETAIL_ID")
	private Integer proDetailId;		//项目支出明细id
	
	@Column (name ="F_PRO_SIGN_NAME")
	private String proSignName;		//采购项目签报名称
	
	@Column (name ="F_PRO_SIGN_CONTENT")
	private String proSignContent;		//采购项目签报内容
	
	@Column(name = "F_USER_NAME2")
	private String userName2;			//下环节处理人姓名
	
	@Column(name = "F_USER_CODE")
	private String fuserId;				//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;				//下节点节点编码
	
	@Column(name = "F_BUYINFOS")
	private String buyInfos;			//三级联查关联
	
	@Column(name = "F_FILES_UPLOAD_STS")
	private String filesUploadSts;			//文件上传状态     null:待上传   1:待修改  2:待确认   9:已确认   4:已撤回	5:已退回

	@Column(name = "F_CONTRACT_PUTONRECORDS_STS")
	private String conPutOnRecordsSts;			//合同备案状态     null:待上传   1:待修改  2:待确认   9:已确认   4:已撤回	5:已退回

	@Column(name = "F_CHECK_PUTONRECORDS_STS")
	private String checkPutOnRecordsSts;			//验收备案状态     null:待上传   1:待修改  2:待确认   9:已确认   4:已撤回	5:已退回
	
	@Column(name = "F_AGENCY")
	private String agency;	            //代理机构
	
	@Column(name = "F_PROCESS_LEADER_NAME")
	private String processLeaderName;	//采购流程负责人名称
	
	@Column(name = "F_PLAN_STATUS")
	private String planStatus;	        //采购计划审批状态
	
	@Column(name = "F_NEEDSSTATUS")
	private String needsStatus;				//需求申请单审批状态 
	
	@Column(name = "F_AUTHORIZED")
	private String authorized;			//授权代表
	
	@Column(name = "F_LEADER_ID")
	private String projectLeaderId;		//处室负责人Id
	
	@Column(name = "F_LEADER_NAME")
	private String projectLeaderName;	//处室负责人名称
	
	@Column(name = "F_ANSWER_STATUS")
	private String fAnswerStatus;       //质疑完结状态 0:暂存 1:未完结 9:已完结
	
	@Column(name = "F_IS_USED")
	private String isUsed;       //采购单被合同使用状态 0:未使用 1:已使用
	
	
	
	@Transient
	private String applyUser;	        //通用申请人（显示用）
	
	@Transient
	private Date applyTime;              //通用申请时间（显示用）
	
	@Transient
	private String approvalStatus;       //通用审批状态（显示用）
	
	@Transient
	private int number;					//序号(数据库里没有的)
	
	@Transient
	private String fpItemsNames;		//品目名称（用于页面显示）
	
	@Transient
	private String budgetPriceBasisShow;	//预算价格依据(页面显示用)
	
	@Transient
	private String fpMethods;				//采购方式(页面显示用)
	
	@Transient
	private String fpPypes;				//采购类型(页面显示用)
	
	@Transient
	private String fReqTimes;				//申请时间(页面显示用)

	
	/*@Transient
	private String forgtype;			//采购组织形式
	
	@Transient
	private String  fevamethod;			//评价方法
	
	@Transient
	private String  fpmethod;			//采购方式
*/	
	@Transient
	private Integer contractId;			//合同id
	
	@Transient
	private Double pfAmount; 			//预算批复金额
	
	@Transient
	private String pfDate;				//预算批复时间
	
	@Transient
	private String pfDepartName;		//使用部门
	
	@Transient
	private Double syAmount;			//可用余额
	
	@Transient
	private String pfIndexType;			//资金渠道和指标类型
	
	@Transient
	private Integer conEarlyWarning;			//合同预警天数
		
	@Transient
	private Integer frId;				//主键
	
	@Transient
	private Integer fId;                //政采计划主键
	
	@Transient
	private String registerUserId;		//登记人主键主键
	
	@Transient
	private String returnReason;				//退回理由   2021-3-19  赵孟雷加
	/*public Lookups getfOrgType() {
		return fOrgType;
	}

	public void setfOrgType(Lookups fOrgType) {
		this.fOrgType = fOrgType;
	}

	public Lookups getfEvaMethod() {
		return fEvaMethod;
	}

	public void setfEvaMethod(Lookups fEvaMethod) {
		this.fEvaMethod = fEvaMethod;
	}

	public Lookups getFpMethod() {
		return fpMethod;
	}

	public void setFpMethod(Lookups fpMethod) {
		this.fpMethod = fpMethod;
	}

	public String getForgtype() {
		if (fOrgType != null) {
			return fOrgType.getName();
		}
		return forgtype;
	}

	public void setForgtype(String forgtype) {
		this.forgtype = forgtype;
	}

	public String getFevamethod() {
		if (fEvaMethod != null) {
			return fEvaMethod.getName();
		}
		return fevamethod;
	}

	public void setFevamethod(String fevamethod) {
		this.fevamethod = fevamethod;
	}*/

	/*public String getFpmethod() {
		if(StringUtils.isNotEmpty(fpMethod)){
			Lookups lookups=LookupsUtil.findByLookCode(fpMethod);
			if(lookups!=null){
				return lookups.getName();
			}
		}
		return fpmethod;
	}

	public void setFpmethod(String fpmethod) {
		this.fpmethod = fpmethod;
	}*/
	
	
	
	public String getOpenObjCode() {
		return openObjCode;
	}

	
	public String getIsUsed() {
		return isUsed;
	}


	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}


	public Integer getConEarlyWarning() {
		return conEarlyWarning;
	}


	public void setConEarlyWarning(Integer conEarlyWarning) {
		this.conEarlyWarning = conEarlyWarning;
	}


	public String getProjectLeaderId() {
		return projectLeaderId;
	}


	public void setProjectLeaderId(String projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}


	public String getAuthorized() {
		return authorized;
	}


	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}


	public String getNeedsStatus() {
		return needsStatus;
	}


	public void setNeedsStatus(String needsStatus) {
		this.needsStatus = needsStatus;
	}


	public void setOpenObjCode(String openObjCode) {
		this.openObjCode = openObjCode;
	}

	public Integer getFpId() {
		return fpId;
	}

	public String getBuyInfos() {
		return buyInfos;
	}

	public void setBuyInfos(String buyInfos) {
		this.buyInfos = buyInfos;
	}

	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}

	public String getfReqTimes() {
		return fReqTimes;
	}

	public void setfReqTimes(String fReqTimes) {
		this.fReqTimes = fReqTimes;
	}

	public String getBudgetPriceBasisShow() {
		return budgetPriceBasisShow;
	}

	public void setBudgetPriceBasisShow(String budgetPriceBasisShow) {
		this.budgetPriceBasisShow = budgetPriceBasisShow;
	}

	public String getFpMethods() {
		return fpMethods;
	}

	public void setFpMethods(String fpMethods) {
		this.fpMethods = fpMethods;
	}

	public String getFpPypes() {
		return fpPypes;
	}

	public void setFpPypes(String fpPypes) {
		this.fpPypes = fpPypes;
	}

	public Integer getFrId() {
		return frId;
	}

	public void setFrId(Integer frId) {
		this.frId = frId;
	}

	public String getfIsImpFiles() {
		return fIsImpFiles;
	}

	public void setfIsImpFiles(String fIsImpFiles) {
		this.fIsImpFiles = fIsImpFiles;
	}

	public String getBudgetPriceBasis() {
		return budgetPriceBasis;
	}

	public void setBudgetPriceBasis(String budgetPriceBasis) {
		this.budgetPriceBasis = budgetPriceBasis;
	}

	public String getPfIndexType() {
		return pfIndexType;
	}

	public void setPfIndexType(String pfIndexType) {
		this.pfIndexType = pfIndexType;
	}

	public String getProSignName() {
		return proSignName;
	}

	public void setProSignName(String proSignName) {
		this.proSignName = proSignName;
	}

	public String getProSignContent() {
		return proSignContent;
	}

	public void setProSignContent(String proSignContent) {
		this.proSignContent = proSignContent;
	}

	public String getfDZHCode() {
		return fDZHCode;
	}

	public void setfDZHCode(String fDZHCode) {
		this.fDZHCode = fDZHCode;
	}

	public String getFpItemsNames() {
		return fpItemsNames;
	}

	public void setFpItemsNames(String fpItemsNames) {
		this.fpItemsNames = fpItemsNames;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public Integer getFplId() {
		return fplId;
	}

	public void setFplId(Integer fplId) {
		this.fplId = fplId;
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

	public String getfAgencyName() {
		return fAgencyName;
	}

	public void setfAgencyName(String fAgencyName) {
		this.fAgencyName = fAgencyName;
	}

	public String getfIsImp() {
		return fIsImp;
	}

	public void setfIsImp(String fIsImp) {
		this.fIsImp = fIsImp;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	public String getfOtherRemark() {
		return fOtherRemark;
	}

	public void setfOtherRemark(String fOtherRemark) {
		this.fOtherRemark = fOtherRemark;
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

	public String getfIsReceive() {
		return fIsReceive;
	}

	public void setfIsReceive(String fIsReceive) {
		this.fIsReceive = fIsReceive;
	}

	public String getfIsInquiry() {
		return fIsInquiry;
	}

	public void setfIsInquiry(String fIsInquiry) {
		this.fIsInquiry = fIsInquiry;
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

	public String getFbidStauts() {
		return fbidStauts;
	}

	public void setFbidStauts(String fbidStauts) {
		this.fbidStauts = fbidStauts;
	}

	public String getFevalStauts() {
		return fevalStauts;
	}

	public void setFevalStauts(String fevalStauts) {
		this.fevalStauts = fevalStauts;
	}

	public String getFpayStauts() {
		return fpayStauts;
	}

	public void setFpayStauts(String fpayStauts) {
		this.fpayStauts = fpayStauts;
	}

	public String getFtendingStauts() {
		return ftendingStauts;
	}

	public void setFtendingStauts(String ftendingStauts) {
		this.ftendingStauts = ftendingStauts;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public Integer getProDetailId() {
		return proDetailId;
	}

	public void setProDetailId(Integer proDetailId) {
		this.proDetailId = proDetailId;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
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
	
	public String getfIsPers() {
		return fIsPers;
	}

	public void setfIsPers(String fIsPers) {
		this.fIsPers = fIsPers;
	}

	public String getFpItemsName() {
		return fpItemsName;
	}

	public void setFpItemsName(String fpItemsName) {
		this.fpItemsName = fpItemsName;
	}

	public String getfConfirmStauts() {
		return fConfirmStauts;
	}

	public void setfConfirmStauts(String fConfirmStauts) {
		this.fConfirmStauts = fConfirmStauts;
	}

	public String getFilesUploadSts() {
		return filesUploadSts;
	}

	public void setFilesUploadSts(String filesUploadSts) {
		this.filesUploadSts = filesUploadSts;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getProjectLeaderName() {
		return projectLeaderName;
	}

	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getProcessLeaderName() {
		return processLeaderName;
	}

	public void setProcessLeaderName(String processLeaderName) {
		this.processLeaderName = processLeaderName;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getConPutOnRecordsSts() {
		return conPutOnRecordsSts;
	}


	public void setConPutOnRecordsSts(String conPutOnRecordsSts) {
		this.conPutOnRecordsSts = conPutOnRecordsSts;
	}


	public String getCheckPutOnRecordsSts() {
		return checkPutOnRecordsSts;
	}


	public void setCheckPutOnRecordsSts(String checkPutOnRecordsSts) {
		this.checkPutOnRecordsSts = checkPutOnRecordsSts;
	}

	

	public String getfAnswerStatus() {
		return fAnswerStatus;
	}


	public void setfAnswerStatus(String fAnswerStatus) {
		this.fAnswerStatus = fAnswerStatus;
	}

	public String getReturnReason() {
		return returnReason;
	}


	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public Integer getfId() {
		return fId;
	}


	public void setfId(Integer fId) {
		this.fId = fId;
	}

	@Transient
	@Override
	public String getJoinTable() {
		return "T_PURCHASE_APPLY_BASIC";
	}

	@Transient
	@Override
	public String getEntryId() {
		return String.valueOf(fpId);
	}

	public String getfContractstatus() {
		return fContractstatus;
	}

	public void setfContractstatus(String fContractstatus) {
		this.fContractstatus = fContractstatus;
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
	public String getBeanCode() {
		
		return fpCode;
	}
	
	@Override
	public Integer getBeanId() {
		
		return fpId;
	}

	@Override
	public String getNextCheckKey() {
		
		return nCode;
	}

	@Override
	public void setCashierType(String status) {
		
		
	}
	
	@Override
	public String getUserId() {
		
		return fUser;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_P_CODE";
	}

}
