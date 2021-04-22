package com.braker.core.manager;

import java.util.List;

import com.braker.common.entity.ComboboxBean;
import com.braker.common.entity.TreeEntity;
import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Depart;
import com.braker.core.model.Lookups;
import com.braker.core.model.User;

public interface DepartMng extends BaseManager<Depart>{
	
	/**
	 * 
	* @author:安达
	* @Title: init 
	* @Description: 初始化数据
	* @return void    返回类型 
	* @date： 2019年5月27日上午11:32:39 
	* @throws
	 */
	public  void init();
	
	public String getDeptIdStrByUser(User user);
	public String getDeptIdStrByUsercn(User user);
	/**
	 * 获得所有根节点
	 * 
	 * @return
	 */
	public List<Depart> getRoots();
	
	/**
	 * 获得子节点
	 * 
	 * @param pid
	 * @return
	 */
	public List<Depart> getChild(String pid);
	/**
	 * 获取所有门（适应tree的数据结构）
	 * @return
	 */
	public String deptTreeJson();
	
	public Pagination list(Depart bean,String sort,String order,int pageIndex,int pageSize);
	
	/**
	 * 验证部门代码是否已存在
	 * @param role
	 * @return
	 */
	public boolean isExist(Depart bean);
	
	/**
	 * 通过部门代码查找部门
	 * @param code
	 * @return
	 */
	public Depart findByCode(String code);

	/**
	 * 查询部门是否有下属部门
	 * @param depart
	 * @return
	 */
	public boolean haveChild(Depart depart);

	/**
	 * 根据查询条件获得treegrid
	 * @param depart
	 * @return
	 */
	public List<TreeEntity> getQueryTree(Depart depart);
	
	/**
	 * 通过部门名称查找部门
	 * @param code
	 * @return
	 */
	public Depart findByName(String name);
	/**
	 * 通过用户查找部门id和名称
	 * @param userId
	 * @return
	 */
	public String[] findDeptByUserId(String userId);
	/**
	 * 查询所有的部门
	 * @param bean
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<Depart> findAllDepart(Depart bean,String sort,String order);
	/**
	 * 获取按当前部门可管理的部门
	 * @createtime 2019-03-05
	 * @author 张迅
	 */
	public List<String[]> getSonCompany(Depart depart);
	
	/**
	 * 获得所有部门，不带任何条件
	 * @return
	 * @author 陈睿超
	 * @createTime 2019年8月9日
	 * @updateTime 2019年8月9日
	 */
	List<Depart> getAllDepts(String selected) ;
	
	/**
	 * 获取所有部门
	 * @param selected
	 * @return
	 * @author 陈睿超
	 * @createtime 2019年10月15日
	 */
	List<Lookups> getAllDeptsLookups(String selected);
	
	/**
	 *  获得所有部门，除了（天津现代职业技术学院、校统筹、校领导、离休人员、退休人员、党委巡查组、红色教育实践基地、质量监测站）
	 * @param selected
	 * @return
	 * @author 陈睿超
	 * @createtime 2019年10月15日
	 */
	List<Depart> getAllDeptsParts(String selected);
	
	/**
	 * 获取所有部门，除了（天津现代职业技术学院、校统筹、校领导、离休人员、退休人员、党委巡查组、红色教育实践基地、质量监测站）
	 * @param selected
	 * @return
	 * @author 陈睿超
	 * @createtime 2019年10月15日
	 */
	List<Lookups> getAllDeptsPartsLookups(String selected);
	
	/**
	 * 获取登录用户的所有部门
	 * @param deptId
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	List<ComboboxBean> getCurrentUserDept(User user);
	
	/**
	 * 获取所有部门
	 * @param user
	 * @return
	 * @author wanping
	 * @createtime 2021年2月24日
	 * @updator wanping
	 * @updatetime 2021年2月24日
	 */
	List<ComboboxBean> getAllDept(User user);
}
