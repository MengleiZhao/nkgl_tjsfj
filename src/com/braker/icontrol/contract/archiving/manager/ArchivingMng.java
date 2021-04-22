package com.braker.icontrol.contract.archiving.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.archiving.model.Archiving;
import com.braker.icontrol.contract.enforcing.model.Upt;

public interface ArchivingMng extends BaseManager<Archiving>{

	/**
	 * 查询合同信息列表
	 * @param contractBasicInfo
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	Pagination query_CBI(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);
	
	Pagination query_upt(Upt upt,User user, Integer pageNo, Integer pageSize);
	
	/**
	 * 保存一条合同归档信息
	 * @param contractBasicInfo
	 * @param archiving
	 * @param user
	 * @author crc
	 * @createtime 2018-06-28
	 */
	void save(ContractBasicInfo contractBasicInfo,Upt upt,String type,Archiving archiving,User user,String files);
	
	List<Archiving> findByContId(String id);
	
	/**
	 * 查询可以归档的合同
	 * @param contractBasicInfo
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination query_CBI_Archiving(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize);
}
