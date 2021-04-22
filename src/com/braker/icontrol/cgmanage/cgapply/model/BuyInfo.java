package com.braker.icontrol.cgmanage.cgapply.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.braker.common.entity.Combobox;
import com.braker.common.entity.GenericEntityNow;
import com.braker.core.model.User;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "T_BUY_INFO")
public class BuyInfo  extends GenericEntityNow implements Combobox{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="PID")
	private Integer pid;		//id
	
	@Column(name = "T_BUY_CODE")
    private String buyCode;	//三级联查编码
	
	@Column(name = "T_BUY_NAME")
    private String name;		//三级联查名字
	
	@Column(name = "T_FATHER_ID")
    private Integer parentId;	//三级联查父类id
	
	@Column(name = "STATUS")
    private String status;		//状态 1-正常 99-删除
	

	public Integer getpId() {
		return pid;
	}

	public void setpId(Integer pid) {
		this.pid = pid;
	}

	public String getBuyCode() {
		return buyCode;
	}

	public void setBuyCode(String buyCode) {
		this.buyCode = buyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return buyCode;
	}

	@Override
	public String getGridCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSftjCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}
}
