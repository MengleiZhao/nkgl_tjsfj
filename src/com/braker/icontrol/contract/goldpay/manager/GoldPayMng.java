package com.braker.icontrol.contract.goldpay.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.goldpay.model.GoldPay;

public interface GoldPayMng extends BaseManager<GoldPay>{

	/**
	 * 显示保证金管理主页面数据，查询已审核已备案的合同
	 * @param contractBasicInfo
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2018-07-04
	 */
	Pagination find(GoldPay bean,User user,Integer pageNo,Integer pageSize);
	
	/**
	 * 新增一条付款信息
	 * @param contractBasicInfo
	 * @param user
	 * @param GoldPay
	 * @author 陈睿超
	 * @createtime 2018-07-04
	 */
	void save(GoldPay bean,User user,String files);
	
	/**
	 * 根據合同的id查詢
	 * @param id
	 * @param dataType 数据类型0-收款，2-付款 
	 * @return
	 */
	List<GoldPay> findByContId(String id, String dataType);
	
	/**
	 * 保存收入保证金数据
	 * @param contractBasicInfo
	 * @param user
	 * @param goldPay
	 * @author 陈睿超
	 * @createtime 2021年1月21日
	 * @updator 陈睿超
	 * @updatetime 2021年1月21日
	 */
	void incomeSave(ContractBasicInfo contractBasicInfo,User user,GoldPay goldPay);
	
	/**
	 * 查询该合同是否在变更中或者已变更
	 * @param id 原合同主键ID
	 * @return true:有正在变更或者已变更的数据,false:没有正在变更或者已变更的数据
	 * @author 陈睿超
	 * @createtime 2021年2月1日
	 * @updator 陈睿超
	 * @updatetime 2021年2月1日
	 */
	Boolean queryHasChanged(Integer id);
	
}
