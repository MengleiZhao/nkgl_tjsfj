package com.braker.icontrol.cgmanage.cgconfplan.model;

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
 * 采购计划配置管理的model
 * @author 冉德茂
 * @createtime 2018-10-11
 * @updatetime 2018-10-11
 */

@Entity
@Table(name="T_PROCUREMENT_PLAN")
public class ProcurementPlan extends BaseEntity  implements EntityDao,CheckEntity{

	@Id
	@Column(name = "F_PL_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)   //主键ID
	private Integer fplId;
	
	@Column(name = "F_LIST_NUM")
	private String flistNum;			//单据编号 自动生成
	
	@Column(name = "F_DEPT_NAME")
	private String fdeptName;		//预算单位 暂时不用
	
	@Column(name = "F_REQ_USER_ID")
	private String freqUserId;		//申请人ID
	
	@Column(name = "F_REQ_DEPT")
	private String freqDept;		//申请部门
	
	@Column(name = "F_REQ_DEPT_ID")
	private String freqDeptId;		//申请部门编号
	
	@Column(name = "F_REQ_TIME")
	private Date freqTime;			//申请日期
	
	@Column(name = "F_PROCUR_TYPE")
	private String fprocurType;		//配置采购类型
	
	@Column(name = "F_REQ_LINK_MEN")
	private String freqLinkMen;		//申请部门联系人
	
	@Column(name = "F_LINK_TEL")
	private String flinkTel;		//申请部门联系人电话
	
	@Column(name = "F_REQ_CONTENT")
	private String freqContent;		//配置申请内容
	
	@Column(name = "F_REMARK")
	private String fremark;		//备注
	
	@Column(name = "F_USER_NAME2")
	private String userName2;		//下环节处理人姓名
	
	@Column(name = "F_USER_CODE")
	private String fuserId;			//下环节处理人编码
	
	@Column(name = "F_N_CODE")
	private String nCode;			//下节点节点编码
	
	@Column(name = "F_CHECK_STAUTS")
	private String fcheckStauts;			//计划审批状态
	
	@Column(name = "F_STAUTS")
	private String fstauts;			//数据删除状态
	
	@Column(name = "F_IS_CHECKED")
	private String fisChecked;			//采购是否已选择  状态  0 未选择  1已选择
	
	@Column(name = "F_BRAND_MODEL")
	private String fbrandModel;			//品牌和规格
	
	@Column(name = "F_BRAND_NUM")
	private String fbrandAndNum;			//品牌和数量
	
	@Column(name = "F_COMBINE_STAUTS")
	private int fCombineStauts;			//计划合并状态   0：未被合并过      1：被合并过
	
	@Transient
	private Integer num;			//序号(数据库中没有)
	
	@Transient
	private String combineState;			//是否可合并(数据库中没有)
	
	
	
	


	public String getFreqUserId() {
		return freqUserId;
	}

	public void setFreqUserId(String freqUserId) {
		this.freqUserId = freqUserId;
	}

	public int getfCombineStauts() {
		return fCombineStauts;
	}

	public void setfCombineStauts(int fCombineStauts) {
		this.fCombineStauts = fCombineStauts;
	}

	public String getCombineState() {
		return combineState;
	}

	public void setCombineState(String combineState) {
		this.combineState = combineState;
	}

	public String getFbrandModel() {
		return fbrandModel;
	}

	public void setFbrandModel(String fbrandModel) {
		this.fbrandModel = fbrandModel;
	}


	public String getFbrandAndNum() {
		return fbrandAndNum;
	}

	public void setFbrandAndNum(String fbrandAndNum) {
		this.fbrandAndNum = fbrandAndNum;
	}

	public String getFisChecked() {
		return fisChecked;
	}

	public void setFisChecked(String fisChecked) {
		this.fisChecked = fisChecked;
	}

	public String getFprocurType() {
		return fprocurType;
	}

	public void setFprocurType(String fprocurType) {
		this.fprocurType = fprocurType;
	}

	public String getFstauts() {
		return fstauts;
	}

	public void setFstauts(String fstauts) {
		this.fstauts = fstauts;
	}

	public Integer getFplId() {
		return fplId;
	}

	public void setFplId(Integer fplId) {
		this.fplId = fplId;
	}

	public String getFlistNum() {
		return flistNum;
	}

	public void setFlistNum(String flistNum) {
		this.flistNum = flistNum;
	}

	public String getFdeptName() {
		return fdeptName;
	}

	public void setFdeptName(String fdeptName) {
		this.fdeptName = fdeptName;
	}

	public String getFreqDept() {
		return freqDept;
	}

	public void setFreqDept(String freqDept) {
		this.freqDept = freqDept;
	}

	public Date getFreqTime() {
		return freqTime;
	}

	public void setFreqTime(Date freqTime) {
		this.freqTime = freqTime;
	}

	public String getFreqLinkMen() {
		return freqLinkMen;
	}

	public void setFreqLinkMen(String freqLinkMen) {
		this.freqLinkMen = freqLinkMen;
	}

	public String getFlinkTel() {
		return flinkTel;
	}

	public void setFlinkTel(String flinkTel) {
		this.flinkTel = flinkTel;
	}

	public String getFreqContent() {
		return freqContent;
	}

	public void setFreqContent(String freqContent) {
		this.freqContent = freqContent;
	}

	public String getFremark() {
		return fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
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

	public String getFcheckStauts() {
		return fcheckStauts;
	}

	public void setFcheckStauts(String fcheckStauts) {
		this.fcheckStauts = fcheckStauts;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	@Transient
	@Override
	public String getJoinTable() {
		return "T_PROCUREMENT_PLAN";
	}

	@Transient
	@Override
	public String getEntryId() {
		return String.valueOf(fplId);
	}

	public String getFreqDeptId() {
		return freqDeptId;
	}

	public void setFreqDeptId(String freqDeptId) {
		this.freqDeptId = freqDeptId;
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
		
		this.fcheckStauts=checkStatus;
	}
	@Override
	public String getCheckStauts() {
		
		return fcheckStauts;
	}
	@Override
	public void setStauts(String status) {
		
		this.fstauts=status;
	}
	@Override
	public String getBeanCode() {
		
		return flistNum;
	}
	@Override
	public Integer getBeanId() {
		
		return fplId;
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
		
		return freqUserId;
	}

	@Override
	public String getBeanCodeField() {
		
		return "F_LIST_NUM";
	}

	@Override
	public String getNextCheckUserId() {
		
		return fuserId;
	}

	
	
	
	
}
