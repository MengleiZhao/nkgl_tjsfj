package com.braker.icontrol.contract.enforcing.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.enforcing.model.UptClause;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.workflow.entity.TProcessCheck;

public interface UptMng extends BaseManager<Upt>{

	/**
	 * 主页面的信息带查询
	 * @param contractBasicInfo
	 * @param user
	 * @return
	 * @author crc
	 * @param searchData 
	 * @createtime 2019-05-28
	 */
	Pagination List(Upt upt,User user, Integer pageNo, Integer pageSize, String searchData);
	
	/**
	 * 保存变更记录
	 * @param upt
	 * @param user
	 * @author crc
	 * @param cgConfPlanJson 
	 * @throws Exception 
	 * @createtime 2018-06-26
	 */
	String saveUptAndUptAttac(ContractBasicInfo contractBasicInfo,Upt upt,User user,String htbgfiles,List<UptClause> uptClauseList,String uptplan, String cgConfPlanJson) throws Exception;
	
	/**
	 * 根据合同主键查询
	 * @param id
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	List<Upt> findByFContId_U(String id);
	/**
	 * 根据合同主键查询
	 * @param id
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	List<Upt> findByCId(String id);
	
	/**
	 * 根据合同号查询是否有相关合同的变更信息
	 * @param id
	 * @return
	 */
	int findUpTInfoSize(String id );
	
	/**
	 * 根据合同变更单主键删除（物理删除）
	 * @param fId 变更单的主键
	 */
	void deletebyfId(String fId);
	
	/**
	 * 逻辑删除
	 * @param id
	 * @author 陈睿超
	 * @createtime 2020年8月17日
	 * @updator 陈睿超
	 * @updatetime 2020年8月17日
	 */
	void deletebyId(Upt upt);
	
	/**
	 * 加载审批的数据带查询
	 * @param upt 查询数据
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param searchData 
	 * @return
	 */
	Pagination queryList(Upt upt,User user, Integer pageNo, Integer pageSize, String searchData);
	
	/**
	 * 更改合同变更单状态，记录审批信息
	 * @param upt 
	 * @param status 审批状态
	 * @param user
	 * @param checkBean
	 * @param file 审批附件
	 * @return
	 */
	String updateStatus(String fId_U,String status,User user,TProcessCheck checkBean,String file,String hyjyfiles,String fDZHCode) throws Exception;
	
	/**
	 * 撤回表单，修改数据
	 * @param id
	 * @return
	 */
	String reCall(String id);
	
	/**
	 * 获取变更合同编号
	 * @return
	 * @author wanping
	 * @createtime 2020年7月7日
	 * @updator wanping
	 * @updatetime 2020年7月7日
	 */
	public String getFContCode();
}
