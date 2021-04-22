package com.braker.icontrol.budget.project.manager.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.icontrol.budget.project.entity.TProBasicFunds;
import com.braker.icontrol.budget.project.manager.TProBasicFundsMng;

/**
 * 
 * <p>Description: 项目资金来源实现类</p>
 * @author:安达
 * @date： 2019年5月23日下午3:11:22
 */
@Service
@Transactional
public class TProBasicFundsMngImpl  extends BaseManagerImpl<TProBasicFunds>  implements TProBasicFundsMng {

	@Override
	public int save(Integer FProId, List<TProBasicFunds> fundsList)
			throws Exception {
		//先删除老的明细
		deleteByProId(FProId);
		//新增新的明细
		for(TProBasicFunds fund:fundsList){
			fund.setFProId(FProId);
			this.save(fund);
		}
		return 0;
	}
	/**
	* 
	* @Title: deleteByProId 
	* @Description: 根据项目id删除老的支出明细
	* @param proId  //项目id
	* @return void    返回类型 
	* @date： 2019年5月23日下午8:47:11 
	* @throws
	 */
	private void deleteByProId(Integer proId){
		Query query=getSession().createSQLQuery(" delete from T_PRO_BASIC_FUNDS where F_PRO_ID="+proId);
		query.executeUpdate();
	}
}
