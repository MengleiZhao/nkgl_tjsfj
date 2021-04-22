package com.braker.icontrol.cockpit.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Depart;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;

/**
 * 驾驶舱接口
 * @author 张迅
 *
 */
public interface CockpitMng extends BaseManager<TProBasicInfo> {

	/*支出模块数据*/
	/**
	 * 培训执行情况-数量统计
	 * 对应图表：培训会议支出统计1
	 * @return 未执行、已执行、百分比
	 */
	public String[] getTrainDataCount(Depart depart, String year,User user);
	/**
	 * 培训执行情况-金额统计
	 * 对应图表：培训会议支出统计2
	 * @return  未执行、已执行、百分比
	 */
	public String[] getTrainDataSum(Depart depart, String year,User user);
	/**
	 * 获得会议执行情况
	 * 对应图表：培训会议支出统计3
	 * @return  未执行、已执行、百分比
	 */
	public String[] getMeetingDataCount(Depart depart, String year,User user);
	/**
	 * 获得会议执行情况
	 * 对应图表：培训会议支出统计4
	 * @return  未执行、已执行、百分比
	 */
	public String[] getMeetingDataSum(Depart depart, String year,User user);
	/**
	 * 获得部门差旅费执行情况
	 * 对应图表：各部门差旅费支出情况
	 * @return 部门、已用金额
	 */
	public List<String[]> getDepartTravelSum(Depart depart, String orderType, String year,User user);
	
	/*项目预算数据*/
	/**
	 * 获得预算项目执行情况
	 * 对应图表：各部门预算执行排名
	 * @return Map键为部门名称，值为预算批复金额、已用金额、使用率
	 */
	public Map<String, Double[]> getBudgetProjectDataSum(Depart depart,String year, User user);
	
	/**
	 * 获取三公经费的执行情况
	 * @param indexName指标名称数组
	 * @return Map键为三公名称，值为控制数（批复金额）、支出数（批复-剩余-冻结）
	 */
	public Map<String, Double[]> getPublicExpensesDataSum(String[] indexName, Depart depart, String year, User user);
	
	/**
	 * 查询项目进度
	 * 对应图表：项目执行进度前五后五
	 * @param desc 输入排序条件（1-desc 2-''）
	 * @return map包括（0-批复金额，1-剩余金额，2-冻结金额，3-项目名称，4-已花金额，5-执行进度百分比（已花金额/批复金额））
	 */
	public List<HashMap<Object, Object>> getprojectProgressTopOrLastFive(String desc, Depart depart, String year,User user);
	
	
	/*合同模块数据*/
	/**
	 * 执行中合同份数
	 * @param depart 部门
	 * @param orderType
	 * @param year 年份
	 * @param user 用户
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月11日
	 * @updator 陈睿超
	 * @updatetime 2020年9月11日
	 */
	public Integer getExecutingContract(Depart depart, String orderType, String year,User user);
	
	/*资产模块*/
	/**
	 * 获取资产价值（总资产金额，固定资产金额，无形资产金额）
	 * @return 
	 * @author 陈睿超
	 * @createtime 2020年9月11日
	 * @updator 陈睿超
	 * @updatetime 2020年9月11日
	 */
	public Map<String, Double> getAssetsAmount();
	
	/**
	 * 获取资产价值
	 * @param type 资产类型
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月11日
	 * @updator 陈睿超
	 * @updatetime 2020年9月11日
	 */
	public List<Double>  getAssetsAmount(String type);
	
	/**
	 * 固定资产价值的增加和减少金额
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	public List<Double> getFixedUpOrDownAmount();
	
	/**
	 * 
	 * @param type
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	public Map<String, Object[]> getFixedAvailableAmount(String type);
	
	/**
	 * 待报废资产占比
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	public Map<String, Double> getFixedAvailablePercentage();
	
	
	
	/** 下钻一级获取数据方法 **/
	public Map<String,Double> getData1(Depart depart, String year,User user);
	public Map<String,Double> getData2(Depart depart, String year,User user);
	public Map<String,Double> getData3(Depart depart, String year,User user);
	public Map<String,Double> getData4(Depart depart, String year,User user);
	public Map<String,Double> getData5(Depart depart, String year,User user);
	public Map<String,Double> getData6(Depart depart, String year,User user);
	public Map<String,Double> getData7(Depart depart, String year,User user);
	public Map<String,Double> getData8(String departName, String year,User user);
	public Map<String,Double> getData9(Depart depart, String year,User user);
	public Map<String,Double> getData10(Depart depart, String year,String strType,User user);
	public Map<String,Double> getData11(Depart depart, String year,String strType,User user);
	public Map<String,Double> getData10_(Depart depart, String year,String strType,User user);
	public Map<String,Double> getData11_(Depart depart, String year,String strType,User user);
	public Map<String,Double> getData12(String departName, String year,User user);
	public Map<String,Double> getData13(Depart depart, String year, String itemName, String dates, String datee,User user);
	public Map<String,Double> getData14(Depart depart, String year, String itemName,User user);
	public Map<String,Double> getExcessData(String code);
	/** 下钻二级获取数据方法 **/
	public Map<String,Double> getData15(Depart depart, String year, String indexType,User user);
	public Map<String,Double> getData16(String year, String departName,User user);
	public Map<String,Double> getData17(Depart depart,String indexType,String year,User user);
	public Map<String,Double> getData18(Depart depart, String year, String indexType,User user);
	public Map<String,Double> getData19(String year, String departName,User user);
	public Map<String,Double> getData20(Depart depart);
	public Pagination getData26(Depart depart,String itemName, int pageNo, int pageSize);
	public Pagination getData27(Depart depart,String itemName, String month,int pageNo, int pageSize);
	//getData28:指标详情信息>支出明细>搜素查询 total-budget1-1.jsp
	public Pagination getData28(Depart depart,String code, String moneyStart,String moneyEnd,String fType,String userName, int pageNo, int pageSize);
	public Pagination getData29(Depart depart, int pageNo, int pageSize);
	public Pagination getData8(String departName, String year, int pageNo, int pageSize);
	public Pagination getDepartData(TBudgetIndexMgr bean, String departName, String year,
			int pageNo, int pageSize);
	public Pagination getFrozenAList(User user,String code, int pageNo, int pageSize);
	//public Pagination getUnitOutcomeList(Depart depart, int pageNo, int pageSize);
	
	/**
	 * 合同管理驾驶舱
	 * @param department
	 * @return
	 * @author wanping
	 * @createtime 2020年9月14日
	 * @updator wanping
	 * @updatetime 2020年9月14日
	 */
	public Map<String, Object> getContractAmount(String department);
	
	/**
	 * 获得采购管理数据
	 * 对应图表：采购登记、采购中的金额、次数和总次数
	 * @return  
	 */
	public List<Object> getCGDataSum(Depart depart, String year,User user);
	/**
	 * 获得采购管理数据
	 * 对应图表：根据采购类型显示柱形图
	 * @return  
	 */
	public List<Object> getCGTypeDataSum(Depart depart, String year,User user);
	/**
	 * 获得采购管理数据
	 * 对应图表：根据采购类型显示柱形图    政府采购数据
	 * @return  
	 */
	public List<Object> getCGTypeZCDataSum(Depart depart, String year,User user);
	/**
	 * 获得采购管理数据
	 * 对应图表：根据采购类型显示柱形图     非政府采购数据
	 * @return  
	 */
	public List<Object> getCGTypeFZCDataSum(Depart depart, String year,User user);
	/**
	 * 获得基本和项目指标综合剩余金额指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getSYIndexDataSum(String year,User user);
	/**
	 * 获得基本和项目指标综合冻结金额指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getDJIndexDataSum(String year,User user);
	/**
	 * 获得基本和项目指标综合报销金额指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getBXIndexDataSum(String year,User user);
	/**
	 * 获得会议费指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getMeetingIndexDataSum(String year,User user);
	/**
	 * 获得培训费指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getTrainIndexDataSum(String year,User user);
	/**
	 * 获得差旅费指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getTravelIndexDataSum(String year,User user);
	/**
	 * 获得公务接待费指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getReceptionIndexDataSum(String year,User user);
	/**
	 * 获得出国费指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> getAbroadIndexDataSum(String year,User user);
	/**
	 * 获得项目前六名指标数据
	 * 对应图表：
	 * @return  
	 */
	public List<Object> queryProIndexDataSum(String year,User user);
	
	
}
