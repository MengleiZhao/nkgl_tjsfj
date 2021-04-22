package com.braker.icontrol.budget.manager.manager;

import java.io.File;
import java.util.List;

import com.braker.common.entity.ComboboxBean;
import com.braker.common.entity.TreeEntity;
import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;

/**
 * 预算指标管理的service抽象类
 * @author 叶崇晖
 * @createtime 2018-09-12
 * @updatetime 2018-09-12
 */
public interface BudgetIndexMgrMng extends BaseManager<TBudgetIndexMgr> {
	/*
	 * 指标分页查询
	 * @author 叶崇晖
	 * @createtime 2018-09-12
	 * @updatetime 2018-09-12
	 */
	public Pagination pageList(TBudgetIndexMgr bean, String indexType, int pageNo, int pageSize, User user);
	
	/*
	 * 保存导入的模板文件
	 * @author 叶崇晖
	 * @createtime 2018-09-12
	 * @updatetime 2018-09-12
	 */
	public void saveFile(File file, User user) throws Exception;

	
	/**
	 * 查询一年基本指标指标金额总和
	 * @param year 需要查询的年份
	 * @return
	 */
	Double sumpfAmount(String year);
	
	
	/*
	 * 指标保存
	 * @author 叶崇晖
	 * @createtime 2018-09-13
	 * @updatetime 2018-09-13
	 */
	public void saveIndex(TBudgetIndexMgr bean);
	
	/*
	 * 生成一下通过或二上审批通过的项目的指标
	 * @author 叶崇晖
	 * @createtime 2018-09-29
	 * @updatetime 2018-09-29
	 */
	public void createIndex(List<TProBasicInfo> li);
	
	/*
	 * 指标生成
	 * @author 叶崇晖
	 * @createtime 2018-10-08
	 * @updatetime 2018-10-08
	 */
	public void generate(String fproIdLi);
	
	/**
	 * 删除指标
	 * @param bId
	 * @return
	 */
	public void deleteIndex(String bId);
	
	/**
	 * 查询已下达指标
	 * @param pageNo
	 * @param pageSize
	 * @param bean
	 * @param user
	 * @return
	 */
	Pagination insideOrProject(Integer pageNo, Integer pageSize,TBudgetIndexMgr bean,User user, String indexType);
	
	/**
	 * 根据指标编号查询唯一指标
	 * @param indexCode
	 * @return
	 */
	TBudgetIndexMgr findByIndexCode(String indexCode);
	
	
	/**
	 * 根据支出事项编码修改指标的预算年度或状态（项目结转、项目结项用）
	 * @param expItemCode
	 */
	public void saveYearOrStauts(String indexCode, String year, String stauts);
	
	/**
	 * 判断申请金额是否超出下达剩余金额
	 * @param id 指标id
	 * @param appleAmount 申请金额
	 * @return
	 */
	public Boolean checkAmount(ReimbAppliBasicInfo bean);
	/**
	 * 判断申请金额是否超出下达剩余金额
	 * @param id 指标id
	 * @param appleAmount 申请金额
	 * @return
	 */
	public Boolean checkAmounts(String id, Double appleAmount);
	/**
	 * 
	* @author:安达
	* @Title: getFirstSubject 
	* @Description: 获得当前年度的指标
	* @param indexType
	* @return
	* @return List<TBudgetIndexMgr>    返回类型 
	* @date： 2019年6月24日上午11:26:49 
	* @throws
	 */
	List<TBudgetIndexMgr>  getFirstSubject(String indexType,User user);
	
	/**
	 * 获得下一年的所有可下达指标
	 * @return
	 * @author 陈睿超
	 * @createTime2019年12月12日
	 * @updateTime2019年12月12日
	 */
	List<TBudgetIndexMgr>  getAllSubject(String indexType);
	
	
	/**
	 * 
	* @author:安达
	* @Title: findByBids 
	* @Description: 根据多个id获得集合
	* @param bids
	* @return
	* @return List<TBudgetIndexMgr>    返回类型 
	* @date： 2019年7月5日下午7:18:19 
	* @throws
	 */
	public List<TBudgetIndexMgr> findByBids(String bids);
	
	/**
	 * 根据项目ID 查询指标
	 * @param proId
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月22日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月22日
	 */
	public List<TBudgetIndexMgr> findByProId(String proId);
	
	/**
	 * 
	* @author:安达
	* @Title: addDjAmount 
	* @Description: 减少剩余金额和 增加冻结金额
	* @param indexId
	* @param proDetailId
	* @param num
	* @return void    返回类型 
	* @date： 2019年8月6日下午2:20:42 
	* @throws
	 */
	public Double addDjAmount(Integer indexId,Integer proDetailId,Double num);
	
	/**
	 * 
	* @author:安达
	* @Title: addDjAmount 
	* @Description: 减少剩余金额和 增加冻结金额
	* @param indexId
	* @param proDetailId
	* @param num
	* @return void    返回类型 
	* @date： 2019年8月6日下午2:20:42 
	* @throws
	 */
	public Double deleteDjAmount(Integer indexId,Integer proDetailId,Double num);
	
	/**
	 * 获取指标中所有的年份
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月22日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月22日
	 */
	public List<ComboboxBean> getProAllYear();
}
