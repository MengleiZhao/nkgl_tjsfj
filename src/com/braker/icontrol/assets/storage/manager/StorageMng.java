package com.braker.icontrol.assets.storage.manager;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.model.User;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.icontrol.assets.storage.model.Regist;
import com.braker.icontrol.assets.storage.model.Storage;
import com.braker.workflow.entity.TProcessCheck;

public interface StorageMng extends BaseManager<Storage>{

	/**
	 * 查询入库登记单
	 * @param Storage
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2018-07-06
	 */
	Pagination list(Storage storage,User user,Integer pageNo, Integer pageSize);
	/**
	 * 查询入库登记单
	 * @param Storage
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2018-07-06
	 */
	Pagination accountantEnteringlist(Storage storage,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 查询入库登记单
	 * @param Storage
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2018-07-06
	 */
	Pagination assetEnteringList(Storage storage,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 保存固定资产入库登记表
	 * @param Storage
	 * @param user
	 * @author 陈睿超
	 * @createtime 2018-07-07
	 */
	void save(List<Regist> rtorage,Storage storage,User user,String files)throws Exception;
	/**
	 * 保存无形资产入库登记表
	 * @param rtorage
	 * @param storage
	 * @param user
	 * @param files
	 * @throws Exception
	 * @author 赵孟雷
	 * @createtime 2020年7月20日
	 * @updator 赵孟雷
	 * @updatetime 2020年7月20日
	 */
	void saveIntangible(List<Regist> rtorage,Storage storage,User user,String files)throws Exception;
	/**
	 * 保存资产详情单
	 * @param Storage
	 * @param user
	 * @author 陈睿超
	 * @createtime 2020-07-17
	 */
	void saveAccountant(List<Regist> rtorage,Storage storage,User user,String files)throws Exception;
	
	/**
	 * 保存卡片编号登记单
	 * @param Storage
	 * @param user
	 * @author 赵孟雷
	 * @createtime 2020-07-17
	 */
	void saveRegister(List<Regist> rtorage,Storage storage,User user,String files)throws Exception;
	
	/**
	 * 删除信息（修改状态）
	 * @param id
	 * @param user
	 * @author 陈睿超
	 * @createtime 2018-07-07
	 */
	void delete(String id,User user );
	
	/**
	 * 保存低值易耗品
	 * @param rtorage
	 * @param storage
	 * @param user
	 * @param files
	 */
	void save_low(List<Regist> rtorage,Storage storage,User user,String files);
	
	/**
	 * 根据一个条件查询
	 * @param condition 条件 
	 * @param val 值
	 * @return
	 */
	Storage findbyCondition(String condition,String val);
	
	/**
	 * 查询加载固定资产增加审批页面的数据
	 * @param Storage 查询信息
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination approvalList(Storage storage,User user,Integer pageNo, Integer pageSize);
	
	/**
	 * 审批意见保存修改
	 * @param status 审批意见
	 * @param id 主键id
	 * @param spjlFile 附件
	 * @param user
	 */
	void updateStatus(String status,String id,String spjlFile,TProcessCheck checkBean,String planJson,User user)throws Exception;
	/**
	 * 无形资产审批意见保存修改
	 * @param status 审批意见
	 * @param id 主键id
	 * @param spjlFile 附件
	 * @param user
	 */
	void updateStatusIntangible(String status,String id,String spjlFile,TProcessCheck checkBean,String planJson,User user)throws Exception;

	void saveFile(File file, User user) throws Exception;
	
	/**
	 * 撤回表单，修改数据
	 * @param id
	 */
	String reCall(String id);
	
	/**
	 * 选择已操作完成的入账单
	 * @param storage
	 * @param id 合同主键id
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月31日
	 * @updator 陈睿超
	 * @updatetime 2020年7月31日
	 */
	Pagination chooseStoragelist(Storage storage,String id,String checkacceptid,User user,Integer pageNo, Integer pageSize,String selectContId,String selectUptId);
	
	
}
