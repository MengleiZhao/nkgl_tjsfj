package com.braker.icontrol.budget.release.manager;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Depart;
import com.braker.icontrol.budget.release.entity.TIndexDetail;

/**
 * 指标（支出）流水service抽象类
 * @author 叶崇晖
 *
 */
public interface IndexDetailMng extends BaseManager<TIndexDetail> {
	
	public List<TIndexDetail> findByIndexCode(String indexCode);
	//查询
	public Pagination searchByIndexCode(String indexCode,String userName,String time1,String time2,int pageNo, int pageSize);
	
	/**
	 * 导出excel
	 * @param dataList
	 * @param filePath
	 * @return
	 */
	HSSFWorkbook exportExcel(List<TIndexDetail> index_dataList, String filePath);
	
	/*
	 * 指标支出信息	
	 * @author 焦广兴		
	 * @createtime 2019-03-26
	 * @updatetime 2019-03-26
	 * fType:费用类型     1、收入	2、还款	3、直接报销	4、申请报销	5、借款	6、采购支付	7、合同报销
	 */
	public Pagination indexDetailData1(Depart depart, String year,String fType,String type, int pageNo, int pageSize,String searchIndexName, String searchDeptName);
	
	
	/*
	 * 预算超额预警指标支出信息	
	 * @author 沈帆		
	 * @createtime 2019-05-05
	 * @updatetime 2019-05-05
	 * fType:费用类型     1、收入	2、还款	3、直接报销	4、申请报销	5、借款	6、采购支付	7、合同报销
	 */
	public Pagination searchByFtype(String indexCode,String userName,String time1,String time2,int pageNo, int pageSize,String fType );

	/**
	 * 组装因公出国数据
	 * @param year
	 * @param month
	 * @param bean
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	public Map<String, Object> abroadamount(Integer year, Integer month);
	
	/**
	 * 组装公务接待数据
	 * @param year
	 * @param month
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	public Map<String, Object> receptionamount(Integer year, Integer month);
	
	/**
	 * 组装会议数据
	 * @param year
	 * @param month
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	public Map<String, Object> meetingamount(Integer year, Integer month);
	
	/**
	 * 组装培训数据
	 * @param year
	 * @param month
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	public Map<String, Object> trainingamount(Integer year, Integer month);
	
	/**
	 * 组装出差数据
	 * @param year
	 * @param month
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	public Map<String, Object> travelamount(Integer year, Integer month);
	
}
