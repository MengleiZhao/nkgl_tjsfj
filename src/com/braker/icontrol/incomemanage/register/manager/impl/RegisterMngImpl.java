package com.braker.icontrol.incomemanage.register.manager.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.model.User;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.incomemanage.register.manager.RegisterMng;
import com.braker.icontrol.incomemanage.register.model.IncomeInfo;



/**
 *收入登記的的service实现类
 * @author 冉德茂
 * @createtime 2018-08-07
 * @updatetime 2018-08-07
 */
@Service
@Transactional
public class RegisterMngImpl extends BaseManagerImpl<IncomeInfo> implements RegisterMng {
	@Autowired
	private TBasicItfMng basicItfMng;
	
	@Autowired
	private TProItfMng proItfMng; 
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-25
	 * @updatetime 2018-07-25
	 */
	@Override
	public Pagination pageList(IncomeInfo bean, int pageNo, int pageSize,String searchData) {

		//Finder finder =Finder.create(" FROM BidRegist WHERE fstatus <>"+99+" ");
		Finder finder =Finder.create("  FROM IncomeInfo WHERE fstauts <>"+99+" ");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fincomeNum like '%"+searchData+"%' or indexName like '%"+searchData+"%' or faccty like '%"+searchData+"%' or faccw like '%"+searchData+"%' or fproName like '%"+searchData+"%') ");
		}
		/*if(!StringUtil.isEmpty(bean.getFincomeNum())){
			finder.append(" AND fincomeNum LIKE '%"+bean.getFincomeNum()+"%'");
		}
		if(!StringUtil.isEmpty(bean.getIndexName())){
			finder.append(" AND indexName LIKE '%"+bean.getIndexName()+"%'");
		}
		if(!StringUtil.isEmpty(bean.getFaccty())){
			finder.append(" AND faccountType.code = :faccty");
			finder.setParam("faccty", bean.getFaccty());
		}
		if(!StringUtil.isEmpty(bean.getFaccw())){
			finder.append(" AND faccountWay.code = :faccw");
			finder.setParam("faccw", bean.getFaccw());
		}*/
		/*if(!StringUtil.isEmpty(bean.getFproName())){
			finder.append(" AND fproName LIKE :fproName");
			finder.setParam("fproName", bean.getFproName());
		}*/
		return super.find(finder,pageNo,pageSize);
	}
	/*
	 * 保存收入信息
	 * @author 冉德茂
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */

	@Override
	public void save(IncomeInfo bean, User user) {
		
		bean.setFstauts("0");//新增和修改   数据的删除状态都是0  删除是99
		
		if (bean.getFincomeId()==null) {//新增
			//创建人、创建时间、发布时间  设置验收状态
			bean.setCreator(user.getName());
			bean.setCreateTime(new Date());
			//保存部门   到账类型  到账方式  到账账户
			bean.setFregisterDepart(bean.getFregisterDepart());
			bean.setFaccountType(bean.getFaccountType());
			bean.setFaccountWay(bean.getFaccountWay());
			bean.setFaccount(bean.getFaccount());
			//保存基本信息
			bean = (IncomeInfo) super.saveOrUpdate(bean);	
		} else {//修改
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(new Date());
			//保存部门   到账类型  到账方式  到账账户  字典表类型
			bean.setFregisterDepart(bean.getFregisterDepart());
			bean.setFaccountType(bean.getFaccountType());
			bean.setFaccountWay(bean.getFaccountWay());
			bean.setFaccount(bean.getFaccount());
			//保存基本信息
			bean = (IncomeInfo) super.saveOrUpdate(bean);	
	}
		
		/*//修改指标剩余金额和冻结金额
		String indexType = bean.getIndexType();
		Double num=Double.valueOf(bean.getFregisterAmount());
		
		if(indexType.equals("0")) {
			TBudgetaryIndicBasicItf bitf = basicItfMng.findById(Integer.valueOf(bean.getFecCode()));
			bitf.setResidAmount(bitf.getResidAmount()+num);//科目剩余金额添加  收入的金额
			super.merge(bitf);
			if(bitf.getParentId() != null) {
				while (basicItfMng.findById(bitf.getParentId().getBiId()) != null ? true : false) {
					bitf = basicItfMng.findById(bitf.getParentId().getBiId());
					bitf.setResidAmount(bitf.getResidAmount()+num);
					super.merge(bitf);
					if(bitf.getParentId()==null) {
						break;
					}
				}
			}
		}
		if(indexType.equals("1")) {
			TBudgetaryIndicProItf pitf = proItfMng.findById(Integer.valueOf(bean.getFecCode()));
			pitf.setResidAmount(pitf.getResidAmount()+num);
			super.merge(pitf);
			while (proItfMng.findById(pitf.getParentId()) != null ? true : false) {
				pitf = proItfMng.findById(pitf.getParentId());
				pitf.setResidAmount(pitf.getResidAmount()+num);
			}
			super.merge(pitf);
		}*/
		
		
}

	/*
	 * 根据id删除
	 * @author 冉德茂
	 * @createtime 2018-08-80
	 * @updatetime 2018-08-08
	 */
	@Override
	public void delete(Integer id) {
		IncomeInfo bean = super.findById(id);
		bean.setFstauts("99");
		super.saveOrUpdate(bean);
	}

	/** 树形图-收入会计科目  start **/
	
	@Override
	public List<Object[]> getInComeSubject(String basicType,
			String parentCode, String year, String qName) {
		String sql = " select F_EC_ID,F_EC_NAME,F_EC_CODE,F_P_EC_ID "
				+ " from t_acco_tree "
				+ " inner join T_ACCO_YEAR "
				+ " on t_acco_tree.F_Y_B_ID = T_ACCO_YEAR.F_Y_B_ID "
				+ " where F_EC_TYPE='KMLX-03' "
				+ " and left(f_ec_code,1)='6' ";
		
		if (!StringUtil.isEmpty(basicType)) {
			if ("personOut".equals(basicType)) {//人员基本支出
				sql = sql + " and (F_EC_CODE like '301%' or F_EC_CODE like '303%')  ";
			} else if ("commOut".equals(basicType)) {//公用基本支出
				sql = sql + " and F_EC_CODE like '302%' ";
			}
		}
		if (!StringUtil.isEmpty(year)) {//年度
			sql = sql + " and T_ACCO_YEAR.F_PERIOD=" + year;
		}
		if (!StringUtil.isEmpty(parentCode)) {//父节点
			sql = sql + " and F_P_EC_ID=" + parentCode;
		}
		if (!StringUtil.isEmpty(qName)) {//查询-节点名称
			sql = sql + " and F_EC_NAME like '%" + qName + "%'";
		}
		
		sql = sql + " order by F_EC_CODE asc ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}
	@Override
	public Map<String, Integer> getPidMap(String basicType, String parentCodes,
			String year, String qName) {
		Map<String,Integer> pidMap=new HashMap<String,Integer>();
		List<Integer> pidList=getPidListByparentCodes(basicType,parentCodes,year,qName);
		if(pidList!=null && pidList.size()>0){
			for(Object pid:pidList){
				pidMap.put(Integer.valueOf(pid.toString())+"", Integer.valueOf(pid.toString()));
			}
		}
		return pidMap;
	}
	public List<Integer> getPidListByparentCodes(String basicType, String parentCodes, String year, String qName){
		String sql = " select F_P_EC_ID  "
				+ " from t_acco_tree "
				+ " inner join T_ACCO_YEAR "
				+ " on t_acco_tree.F_Y_B_ID = T_ACCO_YEAR.F_Y_B_ID "
				+ " where F_EC_TYPE='KMLX-03' ";
		
		if (!StringUtil.isEmpty(basicType)) {
			if ("personOut".equals(basicType)) {//人员基本支出
				sql = sql + " and (F_EC_CODE like '301%' or F_EC_CODE like '303%')  ";
			} else if ("commOut".equals(basicType)) {//公用基本支出
				sql = sql + " and F_EC_CODE like '302%' ";
			}
		}
		if (!StringUtil.isEmpty(year)) {//年度
			sql = sql + " and T_ACCO_YEAR.F_PERIOD=" + year;
		}
		if (!StringUtil.isEmpty(parentCodes)) {//父节点
			sql = sql + " and F_P_EC_ID in (" + parentCodes+")";
		}
		if (!StringUtil.isEmpty(qName)) {//查询-节点名称
			sql = sql + " and F_EC_NAME like '%" + qName + "%'";
		}
		
		sql = sql + " order by F_EC_CODE asc ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}

	/** 树形图-收入会计科目  end **/
	
}


