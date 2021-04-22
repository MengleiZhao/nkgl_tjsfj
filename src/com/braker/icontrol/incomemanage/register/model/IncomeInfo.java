package com.braker.icontrol.incomemanage.register.model;

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
import com.braker.core.model.Lookups;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 收入信息登记的model
 * 是采购申请单据附件的model类
 * @author 冉德茂
 * @createtime 2018-08-07
 * @updatetime 2018-08-07
 */
@Entity
@Table(name = "T_INCOME_INFO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomeInfo extends BaseEntity implements java.io.Serializable{
	@Id
	@Column(name = "F_INCOME_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer fincomeId;			//主键ID
	
	@Column(name = "F_INCOME_NUM")
	private String fincomeNum;					//登记单号
	
	@Column(name = "F_EC_CODE")
	private String fecCode;				//科目编号
	
	@Column(name = "F_INDEX_NAME")
	private String indexName;		//科目编号名称
	
	@Column(name = "F_INDEX_TYPE")
	private String indexType;		//科目编号类型0位基本支出指标，1位项目支出指标
	
	@Column(name = "F_PRO_CODE")
	private String fproCode;				//到账项目编号
	
	@Column(name = "F_PRO_NAME")
	private String fproName;				//到账项目名称
	
	@Column(name = "F_REGISTER_AMOUNT")
	private Double fregisterAmount;				//收入金额
	
	@Column(name = "F_REGISTER_PERSON")
	private String fregisterPerson;				//登记人
	
	@Column(name = "F_REGISTER_TIME")
	private Date fregisterTime;				//登记时间

	/*@ManyToOne
	@JoinColumn(name = "F_REGISTER_DEPART",referencedColumnName="lookupscode")
	private Lookups fregisterDepart;	*/			//登记部门
	@Column(name = "F_REGISTER_DEPART")
	private String fregisterDepart;				//登记部门
	
	@Column(name = "F_REGISTER_DEPART_ID")
	private String fregisterDepartId;				//登记部门id
	
	@ManyToOne
	@JoinColumn(name = "F_ACCOUNT_TYPE",referencedColumnName="lookupscode")
	private Lookups faccountType;				//到账类型
	
	/*@ManyToOne
	@JoinColumn(name = "F_ACCOUNT",referencedColumnName="lookupscode")
	private Lookups faccount;				//开户行
*/	
	@Column(name = "F_ACCOUNT")
	private String faccount;				//开户行
	
	@Column(name = "F_ACCOUNT_NUM")
	private String faccountNum;				//到账帐号
	
	@ManyToOne
	@JoinColumn(name = "F_ACCOUNT_WAY",referencedColumnName="lookupscode")
	private Lookups faccountWay;			//到账方式
	
	@Column(name = "F_STAUTS")
	private String fstauts;				//删除状态
	
	@Column(name = "F_CONTRACT_NAME")
	private String contractName;		//合同名称
	
	@Column(name = "F_CONTRACT_CODE")
	private String contractCode;		//合同编号
	
	@ManyToOne
	@JoinColumn(name = "F_SOURCE_INCOME" ,referencedColumnName="lookupscode")
	private Lookups sourceOfIncome;//收入来源   SRLY-01:学校科研收入,SRLY-02:经营服务性收入
	
	@ManyToOne
	@JoinColumn(name = "F_SERVICE_PROJECT" ,referencedColumnName="lookupscode")
	private Lookups serviceProject;//经营性服务项目     详见字典表  	JYXFWXM-01:助理食品安全师,SRLY-02:经营服务性收入
	
	@Column(name ="F_INCOME_TIME")
	private Date fReqTime;//申请时间
	
	@Column(name ="F_INCOME_DEPT_ID")
	private String fReqDeptID;//申请部门ID

	@Column(name ="F_INCOME_DEPT")
	private String fReqDept;//申请部门
	
	@Column(name ="F_INCOME_USER_ID")
	private String fReqUserid;//申请人的ID
	
	@Column(name ="F_INCOME_USER")
	private String fReqUser;//申请人
	
	@Column(name ="F_FLOW_STAUTS")
	private String fFlowStauts;//审批状态   0-暂存 ，1审批中，9审批完成，-1退回
	
	@Column(name ="F_INCOME_STAUTS")
	private String fIncomeStauts;//表单状态 99-删除，1-正常
	
	@Column(name ="F_NEXT_USER_NAME")
	private String fUserName;//下环节处理人 姓名
	
	@Column (name ="F_NEXT_USER_CODE")
	private String fUserCode;//下环节处理人 编码
	
	@Column(name ="F_NEXT_CODE")
	private String fNCode;//下节点节点编码
	
	
	
	
	@Transient
	private Integer num;			//序号(数据库中没有)
	
	/*@Transient
	private String fdep;			//部门
*/	
	@Transient
	private String faccty;			//到账类型
	
	/*@Transient
	private String  facct;			//到账账户
*/	
	@Transient
	private String  faccw;			//到账方式
	
	

	


	public String getFregisterPerson() {
		return fregisterPerson;
	}

	public void setFregisterPerson(String fregisterPerson) {
		this.fregisterPerson = fregisterPerson;
	}

	public String getFproName() {
		return fproName;
	}

	public void setFproName(String fproName) {
		this.fproName = fproName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getFstauts() {
		return fstauts;
	}

	public void setFstauts(String fstauts) {
		this.fstauts = fstauts;
	}

	public Integer getFincomeId() {
		return fincomeId;
	}

	public void setFincomeId(Integer fincomeId) {
		this.fincomeId = fincomeId;
	}

	public String getFincomeNum() {
		return fincomeNum;
	}

	public void setFincomeNum(String fincomeNum) {
		this.fincomeNum = fincomeNum;
	}

	public String getFecCode() {
		return fecCode;
	}

	public void setFecCode(String fecCode) {
		this.fecCode = fecCode;
	}

	public String getFproCode() {
		return fproCode;
	}

	public void setFproCode(String fproCode) {
		this.fproCode = fproCode;
	}

	public Double getFregisterAmount() {
		return fregisterAmount;
	}

	public void setFregisterAmount(Double fregisterAmount) {
		this.fregisterAmount = fregisterAmount;
	}

	public Date getFregisterTime() {
		return fregisterTime;
	}

	public void setFregisterTime(Date fregisterTime) {
		this.fregisterTime = fregisterTime;
	}
	

	
	public Lookups getFaccountType() {
		return faccountType;
	}

	public void setFaccountType(Lookups faccountType) {
		this.faccountType = faccountType;
	}



	public String getFaccount() {
		return faccount;
	}

	public void setFaccount(String faccount) {
		this.faccount = faccount;
	}

	public String getFaccountNum() {
		return faccountNum;
	}

	public void setFaccountNum(String faccountNum) {
		this.faccountNum = faccountNum;
	}

	public Lookups getFaccountWay() {
		return faccountWay;
	}

	public void setFaccountWay(Lookups faccountWay) {
		this.faccountWay = faccountWay;
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


	public String getFaccty() {
		if (faccountType != null) {
			return faccountType.getName();
		}
		return faccty;
	}

	public void setFaccty(String faccty) {		
		this.faccty = faccty;
	}

	public String getFaccw() {
		if (faccountWay != null) {
			return faccountWay.getName();
		}
		return faccw;
	}

	public void setFaccw(String faccw) {
		this.faccw = faccw;
	}

	public String getFregisterDepart() {
		return fregisterDepart;
	}

	public void setFregisterDepart(String fregisterDepart) {
		this.fregisterDepart = fregisterDepart;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getFregisterDepartId() {
		return fregisterDepartId;
	}

	public void setFregisterDepartId(String fregisterDepartId) {
		this.fregisterDepartId = fregisterDepartId;
	}

	public Lookups getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(Lookups sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public Lookups getServiceProject() {
		return serviceProject;
	}

	public void setServiceProject(Lookups serviceProject) {
		this.serviceProject = serviceProject;
	}


	
	
	
	
	
}
