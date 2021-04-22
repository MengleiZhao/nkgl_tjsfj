package com.braker.icontrol.incomemanage.quotaManage.manager;

import java.io.File;
import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.EconomicClassificationGovernment;
import com.braker.core.model.Lookups;
import com.braker.core.model.User;
import com.braker.icontrol.incomemanage.capital.model.QuotaMangeInfo;

/**
 * 额度管理的service抽象类
 * @author 沈帆
 * @createtime 2021-02-20
 * @updatetime 2021-02-20
 */
public interface QuotaManageMng extends BaseManager<QuotaMangeInfo>{

	/*
	 * 保存额度登记信息
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2018-02-23
	 */
	public void save(QuotaMangeInfo bean, User user);

	/*
	 * 根据ID删除
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	public void delete(Integer id);

	public Pagination quotaPage(QuotaMangeInfo bean, Integer page,
			Integer rows, User user, String searchData);

	public List<Lookups> getLookupsJson(String parentCode, String blanked,
			String selected);

	List<EconomicClassificationGovernment> getEconomicList(String code);

	public void recall(Integer id);

	public Pagination quotaCheckPage(QuotaMangeInfo bean, Integer page,
			Integer rows, User user, String searchData);

	public void saveCheckResult(String ids, String result, User user);

	public void saveFile(File file, User user) throws Exception;

	public List<QuotaMangeInfo> getCurrencyYearData(List<EconomicClassificationGovernment> ecomList);
}
