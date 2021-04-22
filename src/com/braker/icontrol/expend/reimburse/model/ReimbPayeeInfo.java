package com.braker.icontrol.expend.reimburse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntityEmpty;
import com.braker.common.entity.PayeeDao;

/**
 * 报销申请收款人信息表的model
 * @author 叶崇晖
 * @createtime 2018-08-10
 * @updatetime 2018-08-10
 */
@Entity
@Table(name = "T_REIMB_PAYEE_INFO")
public class ReimbPayeeInfo extends BaseEntityEmpty implements PayeeDao {
	
	
	@Id
	@Column(name = "F_P_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer pId;			//主键ID
	
	@Column(name = "F_R_ID",updatable=false)
	private Integer rId;			//关联报销申请基本信息副键
	
	@Column(name = "F_D_R_ID",updatable=false)
	private Integer drId;			//直接报销申请基本信息副键
	
	@Column(name = "F_PAYEE_ID")
	private String payeeId;			//收款人id
	
	@Column(name = "F_PAYEE_NAME")
	private String payeeName;		//收款人姓名
	
	@Column(name = "F_PAYMENT_TYPE")
	private String paymentType;		//收款方式（1、银行转账2、现金3、支付吧4、微信）

	@Column(name = "F_PAYEE_ID_CARD")
	private String payeeIdCard;     //收款人身份证号    暂时不知道哪里用的
	
	@Column(name = "F_BANK")
	private String bank;			//开户银行
	
	@Column(name = "F_BANK_NAME")
	private String bankName;			//银行名称
	
	@Column(name = "F_BANK_ACCOUNT")
	private String bankAccount;		//银行账户
	
	@Column(name = "F_ZFB_ACCOUNT")
	private String zfbAccount;		//支付宝账户
	
	@Column(name = "F_WX_ACCOUNT")
	private String wxAccount;		//微信账户
	
	@Column(name = "F_ZFB_QR")
	private String zfbQR;			//支付宝二维码地址
	
	@Column(name = "F_WX_QR")
	private String wxQR;			//微信二维码地址
	
	@Column(name = "F_AMOUNT")
	private Double payeeAmount;		//应付转账金额

	@Column(name = "F_ID_CARD")
	private String idCard;			//收款人身份证号      报销里用的
	
	@Column(name = "F_INNER_OR_OUTER")
	private String fInnerOrOuter;	//0：单位内部人员     1：单位外部人员
	
	@Transient
	private String fBankName;//银行名称 显示
	
	@Transient
	private String biddingName;//中标商名称 显示
	
	@Transient
	private String fCardNo;//银行账户 显示
	
	public String getfInnerOrOuter() {
		return fInnerOrOuter;
	}

	public void setfInnerOrOuter(String fInnerOrOuter) {
		this.fInnerOrOuter = fInnerOrOuter;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getZfbAccount() {
		return zfbAccount;
	}

	public void setZfbAccount(String zfbAccount) {
		this.zfbAccount = zfbAccount;
	}

	public String getWxAccount() {
		return wxAccount;
	}

	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}

	public String getZfbQR() {
		return zfbQR;
	}

	public void setZfbQR(String zfbQR) {
		this.zfbQR = zfbQR;
	}

	public String getWxQR() {
		return wxQR;
	}

	public void setWxQR(String wxQR) {
		this.wxQR = wxQR;
	}

	public Double getPayeeAmount() {
		return payeeAmount;
	}

	public void setPayeeAmount(Double payeeAmount) {
		this.payeeAmount = payeeAmount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPayeeIdCard() {
		return payeeIdCard;
	}

	public void setPayeeIdCard(String payeeIdCard) {
		this.payeeIdCard = payeeIdCard;
	}

	@Override
	public String getIdCard() {
		return payeeIdCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getDrId() {
		return drId;
	}

	public void setDrId(Integer drId) {
		this.drId = drId;
	}

	public String getfBankName() {
		return fBankName;
	}

	public void setfBankName(String fBankName) {
		this.fBankName = fBankName;
	}

	public String getBiddingName() {
		return biddingName;
	}

	public void setBiddingName(String biddingName) {
		this.biddingName = biddingName;
	}

	public String getfCardNo() {
		return fCardNo;
	}

	public void setfCardNo(String fCardNo) {
		this.fCardNo = fCardNo;
	}

	
	
}
