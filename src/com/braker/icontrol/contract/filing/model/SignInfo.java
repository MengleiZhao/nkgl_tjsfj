package com.braker.icontrol.contract.filing.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntityEmpty;
import com.braker.common.entity.EntityDao;

/**
 * 签约信息表
 * @author 陈睿超
 *
 */
@Entity
@Table(name ="T_CONTRACT_SIGN_INFO")
public class SignInfo extends BaseEntityEmpty implements EntityDao{

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="F_SIGN_ID")
	private Integer fSignId;
	
	@Column(name ="F_CONT_ID")
	private Integer fContId;
	
	@Column (name ="F_SIGN_NAME")
	private String fSignName;//签约方名称
	
	@Column (name ="F_SIGN_TYPE")
	private String fSignType;//签约方类型
	
	@Column(name ="F_BANK_NAME")
	private String fBankName;//银行名称
	
	@Column(name ="F_BANK")
	private String fBank;//开户行
	
	@Column(name ="F_CARD_NO")
	private String fCardNo;//银行账户
	
	@Column(name ="F_CONC_USER")
	private String fConcUser ;//对方联系人
	
	@Column(name ="F_CONC_TEL")
	private String fConcTel;//联系电话
	
	@Column (name ="F_SIGN_USER")
	private String fSignUser_SI;//签署人
	
	@Column(name ="F_SIGN_TIME")
	private Date f_SignTime;//签署时间
	
	@Column (name ="F_OFFICIAL_USER")
	private String fOfficialUser;//法定代表人

	@Column (name ="F_IS_OFFICIAL_USER")
	private String fIsOfficialUser;//是否法定代表人签字 0-否，1-是
	
	@Column (name ="F_LEGAL_USER")
	private String fLegalUser;//授权代表人

	@Transient
	private String biddingName;//中标商名称
	
	
	

	
	public Integer getfSignId() {
		return fSignId;
	}

	public void setfSignId(Integer fSignId) {
		this.fSignId = fSignId;
	}

	public Integer getfContId() {
		return fContId;
	}

	public void setfContId(Integer fContId) {
		this.fContId = fContId;
	}

	public String getfSignName() {
		return fSignName;
	}

	public void setfSignName(String fSignName) {
		this.fSignName = fSignName;
	}

	public String getfSignType() {
		return fSignType;
	}

	public void setfSignType(String fSignType) {
		this.fSignType = fSignType;
	}

	public String getfBankName() {
		return fBankName;
	}

	public void setfBankName(String fBankName) {
		this.fBankName = fBankName;
	}

	public String getfCardNo() {
		return fCardNo;
	}

	public void setfCardNo(String fCardNo) {
		this.fCardNo = fCardNo;
	}

	public String getfConcUser() {
		return fConcUser;
	}

	public void setfConcUser(String fConcUser) {
		this.fConcUser = fConcUser;
	}

	public String getfConcTel() {
		return fConcTel;
	}

	public void setfConcTel(String fConcTel) {
		this.fConcTel = fConcTel;
	}

	public String getfSignUser_SI() {
		return fSignUser_SI;
	}

	public void setfSignUser_SI(String fSignUser_SI) {
		this.fSignUser_SI = fSignUser_SI;
	}

	public Date getF_SignTime() {
		return f_SignTime;
	}

	public void setF_SignTime(Date f_SignTime) {
		this.f_SignTime = f_SignTime;
	}

	public String getfBank() {
		return fBank;
	}

	public void setfBank(String fBank) {
		this.fBank = fBank;
	}

	public String getfOfficialUser() {
		return fOfficialUser;
	}

	public void setfOfficialUser(String fOfficialUser) {
		this.fOfficialUser = fOfficialUser;
	}

	public String getfIsOfficialUser() {
		return fIsOfficialUser;
	}

	public void setfIsOfficialUser(String fIsOfficialUser) {
		this.fIsOfficialUser = fIsOfficialUser;
	}

	public String getfLegalUser() {
		return fLegalUser;
	}

	public void setfLegalUser(String fLegalUser) {
		this.fLegalUser = fLegalUser;
	}

	@Transient
	@Override
	public String getJoinTable() {
		return "T_CONTRACT_SIGN_INFO";
	}

	@Transient
	@Override
	public String getEntryId() {
		return String.valueOf(fSignId);
	}

	public String getBiddingName() {
		return biddingName;
	}

	public void setBiddingName(String biddingName) {
		this.biddingName = biddingName;
	}

	
	
	
	
}
