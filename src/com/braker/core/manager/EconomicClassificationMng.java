package com.braker.core.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.EconomicClassificationGovernment;
import com.braker.core.model.User;

public interface EconomicClassificationMng extends BaseManager<EconomicClassificationGovernment> {

	/**
	 * 政府支出经济分类
	 * @param ecg
	 * @param page
	 * @param rows
	 * @return
	 * @author wanping
	 * @createtime 2021年2月22日
	 * @updator wanping
	 * @updatetime 2021年2月22日
	 */
	Pagination list(EconomicClassificationGovernment ecg, Integer page, Integer rows);

	/**
	 * 根据code查询经济分类科目
	 * @param ecg
	 * @return
	 * @author wanping
	 * @createtime 2021年2月23日
	 * @updator wanping
	 * @updatetime 2021年2月23日
	 */
	int queryEcgByCode(EconomicClassificationGovernment ecg);

	/**
	 * 保存经济分类科目
	 * @param ecg
	 * @param user
	 * @author wanping
	 * @createtime 2021年2月23日
	 * @updator wanping
	 * @updatetime 2021年2月23日
	 */
	void saveEcg(EconomicClassificationGovernment ecg, User user);

	/**
	 * 删除
	 * @param id
	 * @author wanping
	 * @createtime 2021年2月23日
	 * @updator wanping
	 * @updatetime 2021年2月23日
	 */
	void deleteEcg(String id);

}
