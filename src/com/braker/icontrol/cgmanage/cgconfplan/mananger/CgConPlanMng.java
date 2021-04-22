package com.braker.icontrol.cgmanage.cgconfplan.mananger;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.FunctionClassMgr;
import com.braker.core.model.User;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;

/**
 * 采购配置计划申请的service抽象类
 * @author 冉德茂
 * @createtime 2018-10-11
 * @updatetime 2018-10-11
 */
public interface CgConPlanMng extends BaseManager<ProcurementPlan>{
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	public Pagination pageList(ProcurementPlan bean,User user,  int pageNo, int pageSize);
	/*
	 * 分页数据获取已经审核通过的配置计划信息进行合并
	 * @author 冉德茂
	 * @createtime 2018-10-23
	 * @updatetime 2018-10-23
	 */
	public Pagination combinepageList(ProcurementPlan bean, int pageNo, int pageSize);
	/*
	 * 查询待审批信息
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	public Pagination checkpageList(ProcurementPlan bean,User user, Integer page,Integer rows);
	/*
	 * 页面加载已经审批通过  未选择过的配置计划信息
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	public Pagination getCheckedConfplan(ProcurementPlan bean, int pageNo, int pageSize);
	
	/*
	 * 新增配置计划的保存
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	public void save(ProcurementPlan bean,String mingxi,String files,User user) throws Exception ;
	/*
	 * 合并计划单的保存(采购清单)
	 * @author 冉德茂
	 * @createtime 2018-10-24
	 * @updatetime 2018-10-24
	 */
	public void saveCombine(ProcurementPlan bean,List<ProcurementPlanList> mxList,String[] plids,User user);
	/*
	 * 根据ID删除
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	public void delete(Integer id);
	
	/*
	 * 采购计划品目明细查询
	 * @author 冉德茂
	 * @createtime 2018-10-16
	 * @updatetime 2018-10-16
	 */
	public List<Object> getMingxi(String tableName ,String idName ,Integer id);
	
	/*
	 * 采购计划品目明细查询
	 * @author 陈睿超
	 * @createtime 2020-06-28
	 * @updatetime 2020-06-28
	 */
	public List<Object> getMingxi(String tableName ,String idName ,String code);
	
	/*
	 * 获得采购计划集合
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	public List<ProcurementPlan> getPlanListByPlids(String ids) ;
	
	/*
	 * 检查目录、品牌、规格 是否符合合并要求
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	public String checkCombineFlag(List<List<Object>> list);
	/*
	 * 合并计划采购单生成新的采购单对象
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	public ProcurementPlan getCombinePlan(List<ProcurementPlan> list);
	
	/*
	 * 获得合并后的采购清单
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	public List<ProcurementPlanList> getCombineQdList(List<List<Object>> list);
	/*
	 * 根据页面选择的计划id数组，获得所有计划下的采购清单集合
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	public List<List<Object>> getQdListByPlids(String ids);
}
