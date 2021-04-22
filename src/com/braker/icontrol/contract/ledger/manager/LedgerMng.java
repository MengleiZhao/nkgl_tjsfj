package com.braker.icontrol.contract.ledger.manager;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.budget.data.entity.BudgetData1;
import com.braker.icontrol.budget.data.entity.BudgetData2;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;

public interface LedgerMng extends BaseManager<ContractBasicInfo>{

	/**
	 * 查询所有已审核合同
	 * @param contractBasicInfo
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination findAllCBI(ContractBasicInfo contractBasicInfo,boolean selfDepart,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 查询所有的合同信息（分部门）
	 * @return
	 */
	List<ContractBasicInfo> ledger(ContractBasicInfo cb,User user);
	/**
	 * 查询所有变更的合同信息（分部门）
	 * @return
	 */
	List<Upt> ledgerUpt(Upt upt,User user);
	
	/**
	 * 导出excel转换
	 * @param cbi 转换数据
	 * @param filePath
	 * @return
	 */
	HSSFWorkbook exportExcel(List<ContractBasicInfo> cbi, String filePath);
	/**
	 * 导出excel转换
	 * @param cbi 转换数据
	 * @param filePath
	 * @return
	 */
	HSSFWorkbook exportExcelUpt(List<Upt> cbi, String filePath);
	
	/**
	 * 
	 * @Title finishContract 
	 * @Description 合同完结
	 * @author 汪耀
	 * @Date 2020年3月10日 
	 * @param fcIds
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean finishContract(String fcIds);
	
	/**
	 * 查询变更合同数据
	 * @param upt
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月22日
	 * @updator 陈睿超
	 * @updatetime 2020年6月22日
	 */
	Pagination uptList(Upt upt, User user, Integer pageNo, Integer pageSize);
	/**
	 * 查询合同变更后合同金额
	 * @param fcCode
	 * @return
	 * @author 方淳洲
	 * @createtime 2021年1月25日
	 * @updator 方淳洲
	 * @updatetime 2021年1月25日
	 */
	List<Upt> findAfterUpdateAmount(String fcCode);

	List<ReimbAppliBasicInfo> findAllAmount(String fcCode);
}
