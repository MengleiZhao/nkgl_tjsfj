package com.braker.icontrol.cgmanage.cgreveive.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;

/**
 * 采购验收的service抽象类
 * @author 冉德茂
 * @createtime 2018-07-17
 * @updatetime 2018-07-17
 */
public interface CgReceiveMng extends BaseManager<AcceptCheck>{
	/*
	 * 分页数据获得
	 * @author 冉德茂
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	public Pagination pageList(AcceptCheck bean,User user , Integer pageNo, Integer pageSize,String searchData);
	/*
	 * 分页数据获得
	 * @author 冉德茂
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	public Pagination pageReceiveList(AcceptCheck bean,User user , Integer pageNo, Integer pageSize);
	
	/*
	 * 保存验收信息
	 * @author 冉德茂
	 * @createtime 2018-07-18
	 * @updatetime 2018-07-18
	 */
	public void saveReceive(AcceptCheck acBean,String files, User user,String jsonList) throws Exception;
	
	/*
	 * 历史验收记录
	 * @author 冉德茂
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	public List<AcceptCheck> checkHistory(Integer id);
	
	/*
	 * 验收台账分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	public Pagination ledgerPageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user);
	
	/**
	 * 
	 * @Title checkPageList 
	 * @Description 审批分页查询
	 * @author 汪耀
	 * @Date 2020年2月20日 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @return Pagination
	 * @throws
	 */
	public Pagination checkPageList(AcceptCheck bean, Integer pageNo, Integer pageSize, User user,String searchData);
	
	/**
	 * 
	 * @Title saveCheck 
	 * @Description 保存审批信息
	 * @author 汪耀
	 * @Date 2020年2月20日 
	 * @param checkBean
	 * @param bean
	 * @param files
	 * @param user
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveCheck(TProcessCheck checkBean, AcceptCheck bean, String files, User user) throws Exception;
	
	/**
	 * 
	 * @Title findFacpIdByFpId 
	 * @Description 根据采购申请主键查询采购验收主键
	 * @author 汪耀
	 * @Date 2020年3月10日 
	 * @param id
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer findFacpIdByFpId(Integer id);
	/**
	 * 
	 * @Title findFacpIdByFcId
	 * @Description 根据合同主键查询采购验收数量
	 * @author 赵孟雷
	 * @Date 2020年8月4日 
	 * @param id
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer findFacpIdByFcId(Integer id);
	
	/**
	 * 验收撤回
	 * @author 焦广兴
	 * @createtime 2019-10-08
	 * @updatetime 2019-10-08
	 */
	public String reCall(Integer id)  throws Exception ;
	
	/**
	* @author:赵孟雷
	* @Title: FacpCode 
	* @Description: 获得验收编号
	* @return int    返回类型 
	* @date： 2020年6月30日
	* @throws
	 */
	public String getFacpCode(User user);
	
	/**
	 * 根据审批条件查询没有部门限制
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月30日
	 * @updator 陈睿超
	 * @updatetime 2020年7月30日
	 */
	public Pagination acceptpageList(AcceptCheck bean, int pageNo, int pageSize, User user);
	
	/**
	 * 验收选择采购项目查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年8月13日
	 * @updator 赵孟雷
	 * @updatetime 2020年8月13日
	 */
	public Pagination cgsqPageReceive(PurchaseApplyBasic bean, int pageNo, int pageSize, User user);
	
}
