package com.braker.icontrol.contract.Formulation.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Lookups;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.model.Attac;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;

public interface FormulationMng extends BaseManager<ContractBasicInfo>{

	/**
	 * 显示主页面信息(合同拟定)
	 * @param contractBasicInfo
	 * @param searchData 
	 * @return
	 */
	Pagination queryList(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize, String searchData);
	/**
	 * 显示主页面信息(合同拟定)
	 * @param contractBasicInfo
	 * @return
	 */
	Pagination queryListJsonSeal(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);
	
	/**
	 * 保存
	 * @param contractBasicInfo
	 * @param user
	 */
	void save_CBI(ContractBasicInfo cbiBean, String files,String filessqwts, SignInfo siBean, String planJson,String cgConfplanJson, User user,PurchaseApplyBasic cgbean) throws Exception;
	/**
	 * 根据id删除
	 * @param fcId
	 */
	void delete_CBI(Integer id, User user);
	
	/**
	 * 上传附件和保存
	 * @param attac
	 * @param user
	 */
	void save_attac(ContractBasicInfo contractBasicInfo,List<Attac> at,User user);
	
	/**
	 * 根据合同id去查询附件记录
	 * @param id
	 * @return
	 */
	List<Attac> findAttac(Integer id);
	
	/**
	 * 根据文件名称查询
	 * @param name
	 * @return
	 */
	List<Attac> findAttacByName(String name);
	
	/**
	 * 根据文件名删除
	 * @param attac
	 */
	void deleteAttac(List<Attac> attac);
	
	/**
	 * 查询字典里审批状态
	 * @param categoryCode
	 * @param blanked
	 * @return
	 */
	List<Lookups> getLookupsJson(String categoryCode,String blanked,String selected);
	
	/**
	 * 根据采购项目获得采购中标单位
	 * @param categoryCode
	 * @param blanked
	 * @param selected
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月19日
	 * @updator 陈睿超
	 * @updatetime 2020年6月19日
	 */
	List<Lookups> getfContractorlookupsJson(String categoryCode,String blanked,String selected);
	/**
	 * 根据采购编号查询
	 * @param fPurchNo
	 * @return
	 */
	ContractBasicInfo findAttacByFPurchNo(String fPurchNo);
	
	/**
	 * 根据采购编号查询
	 * @param fPurchNo
	 * @return
	 */
	List<ContractBasicInfo> findAttacByFPurchNoList(String fPurchNo);
	
	/**
	 * 根据不同状态条件查询
	 * @param condition1
	 * @param val1
	 * @param endStauts
	 * @return
	 */
	Pagination find(ContractBasicInfo cbi,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 显示主页面信息(合同变更用)
	 * @param contractBasicInfo
	 * @return
	 */
	Pagination queryForChange(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);

	/**
	 * 显示主页面信息(合同付款用)
	 * @param contractBasicInfo
	 * @return
	 */
	Pagination queryForEnforcing(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);

	/**
	 * 显示主页面信息(合同终止用)
	 * @param contractBasicInfo
	 * @return
	 */
	Pagination queryForEnding(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);
	
	/**
	 * 撤回表单，修改数据
	 * @param id
	 */
	String reCall(String id);
	
	/**
	 * 
	* @author:安达
	* @Title: getFcCode 
	* @Description: 获得合同编号
	* @return
	* @return int    返回类型 
	* @date： 2020年1月6日下午8:29:36 
	* @throws
	 */
	public String getFcCode(User user);
	
	
	/**
	 * 查询字典里审批状态
	 * @param categoryCode
	 * @param blanked
	 * @return
	 */
	List<Lookups> getLookupsJsonGist(String code,String selected);
	
	/**
	 * 收款模块查询保证金退还数据
	 * @param contractBasicInfo
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2021年1月21日
	 * @updator 陈睿超
	 * @updatetime 2021年1月21日
	 */
	Pagination incomeGoldPay(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);
	
	Pagination incomeGoldPayLedger(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);

	/**
	 * 用于查询来款登记里面的合同查询
	 * @author:赵孟雷
	 * @param contractBasicInfo
	 * @return
	 */
	Pagination queryContraList(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);
	
	
	Pagination incomeUptGoldPay(Upt upt, User user, Integer pageNo,
			Integer pageSize);
	
	Pagination incomeUptGoldPayLedger(Upt upt, User user, Integer page,
			Integer rows);
	
	Pagination queryListJsonSealUpt(Upt upt, User user, Integer pageNo,
			Integer pageSize);
}
