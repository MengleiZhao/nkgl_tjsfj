package com.braker.icontrol.assets.rece.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceList;
import com.braker.workflow.entity.TProcessCheck;

public interface ReceMng extends BaseManager<Rece>{

	/**
	 * 主页的查询显示
	 * @param rece 搜索条件
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination list(Rece rece,User user, Integer pageNo, Integer pageSize);
	
	/**
	 * 保存
	 * @param rece
	 * @param rl
	 * @param user
	 */
	void save(Rece rece,String receFlies,List<ReceList> rl,User user) throws Exception;
	
	/**
	 * 根据id去修改领用单的状态
	 * @param id
	 */
	void delete(String id,User user) throws Exception;
	
	/**
	 * 低值易耗品待审批的列表信息
	 * @param rece 搜索条件
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination approvalList(Rece rece, User user, Integer pageNo,Integer pageSize);
	
	/**
	 * 保存固定資產
	 * @param rece
	 * @param user
	 * @param stauts
	 */
	void savefixed(Rece rece,User user,List<ReceList> rl,String stauts) throws Exception;
	/**
	 * 修改领用单状态（通过，不通过），并且添加一条审批记录
	 * @param stauts 前台传过来的状态
	 * @param user
	 * @param rece
	 * @param assetCheckInfo
	 */
	void updateStauts( User user, Rece rece,TProcessCheck checkBean,String ConfigPlanjson,String file) throws Exception;
	
	/**
	 * 查看库存是否有这些货，数量还有多少（审批时）
	 * @param rece
	 * @return
	 */
	Rece storkNum(Rece rece);
	
	/**
	 * 查看库存是否有这些货，数量还有多少（保存提交申请时）
	 * @param List<ReceList>
	 * @return
	 */
	Rece storkNum(List<ReceList> receList);
	
	/**
	 * 资产调拨是选择原配置单（领用单）显示列表信息
	 * @param rece 搜索条件
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination allocalist(Rece rece, User user, Integer pageNo,Integer pageSize);
	
	/**
	 * 根据一个条件查询
	 * @param condition 条件 
	 * @param val 值
	 * @return
	 */
	Rece findbyCondition(String condition,String val);
	/**
	 * 根据code条件查询
	 * @param 
	 * @return
	 */
	Rece findCode(String fcode);
	
	/**
	 * 保存配置信息
	 * @param user
	 * @param rece
	 * @param ConfigPlanjson
	 * @throws Exception
	 * @author 陈睿超
	 * @createtime 2020年7月18日
	 * @updator 陈睿超
	 * @updatetime 2020年7月18日
	 */
	void saveConfigList( User user, Rece rece,String ConfigPlanjson) throws Exception;
	
	/**
	 * 更新配置情况并保存
	 * @param stauts
	 * @param user
	 * @param rece
	 * @param checkBean
	 * @param ConfigPlanjson
	 * @param file
	 * @throws Exception
	 * @author 陈睿超
	 * @createtime 2020年7月20日
	 * @updator 陈睿超
	 * @updatetime 2020年7月20日
	 */
	void updateConfig(String stauts, User user, Rece rece,TProcessCheck checkBean,String ConfigPlanjson,String file) throws Exception;
	
	/**
	 * 加载领用资产受理数据
	 * @param rece
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月20日
	 * @updator 陈睿超
	 * @updatetime 2020年7月20日
	 */
	Pagination acceptList(Rece rece,User user, Integer pageNo, Integer pageSize);
	
	/**
	 * 撤回
	 * @param id
	 * @author 陈睿超
	 * @createtime 2020年8月29日
	 * @updator 陈睿超
	 * @updatetime 2020年8月29日
	 */
	void reCall(String id);
}
