package com.braker.icontrol.incomemanage.register.manager;

import java.util.List;
import java.util.Map;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.incomemanage.register.model.IncomeInfo;

/**
 * 收入登記的service抽象类
 * @author 冉德茂
 * @createtime 2018-08-07
 * @updatetime 2018-08-07
 */
public interface RegisterMng extends BaseManager<IncomeInfo>{
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-08-07
	 * @updatetime 2018-08-07
	 */
	public Pagination pageList(IncomeInfo bean, int pageNo, int pageSize,String searchData);
	
	/*
	 * 保存收入信息
	 * @author 冉德茂
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	public void save(IncomeInfo bean, User user);
	
	/*
	 * 根据ID删除
	 * @author 冉德茂
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	public void delete(Integer id);
	
	/**
	 * 获得收入会计科目列表
	 * @param basicType 基本支出类型 1-人员支出 2-公用支出
	 * @param parentCode  父节点代码
	 * @param year  年度
	 * @param qName 查询字段：科目名称
	 * @return 节点id 节点名称 节点代码 父节点代码
	 */
	public List<Object[]> getInComeSubject(String basicType, String parentCode, String year, String qName);
	/**
	 * 获得收入会计科目列表,根据parentCode作为KEY存入map集合
	 * @param basicType 基本支出类型 1-人员支出 2-公用支出
	 * @param parentCode  父节点代码
	 * @param year  年度
	 * @param qName 查询字段：科目名称
	 * @return 节点id 节点名称 节点代码 父节点代码
	 */
	public Map<String,Integer> getPidMap(String basicType, String parentCodes, String year, String qName);

}
