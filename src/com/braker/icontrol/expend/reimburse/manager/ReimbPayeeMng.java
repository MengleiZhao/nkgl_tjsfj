package com.braker.icontrol.expend.reimburse.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Lookups;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.User;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;

/**
 * 报销申请收款人的service抽象类
 * @author 叶崇晖
 * @createtime 2018-08-10
 * @updatetime 2018-08-10
 */
public interface ReimbPayeeMng extends BaseManager<ReimbPayeeInfo> {
	
	/*
	 * 根据rId查询收款人信息
	 * @author 叶崇晖
	 * @createtime 2018-08-10
	 * @updatetime 2018-08-10
	 */
	public List<ReimbPayeeInfo> getByRId(Integer rId,String fInnerOrOuter);
	
	public List<ReimbPayeeInfo> getByDrId(Integer drId,String fInnerOrOuter);

	/**
	 * 获取接送数据数据
	 * @param categoryCode
	 * @param blanked
	 * @param selected
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年8月28日
	 * @updator 陈睿超
	 * @updatetime 2020年8月28日
	 */
	List<Lookups> getpayeelookupsJson(String categoryCode,	String blanked, String selected,String fInnerOrOuter);
	
	/*
	 * 分页查询
	 * @author 赵孟雷
	 * @createtime 2021-01-22
	 * @updatetime 2021-01-22
	 */
	public Pagination pageList(PaymentMethodInfo bean, int pageNo, int pageSize, User user);
}
