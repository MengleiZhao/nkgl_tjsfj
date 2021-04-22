package com.braker.icontrol.cgmanage.questioning.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.questioning.model.PurchasingQuery;
import com.braker.workflow.entity.TProcessCheck;



/**
 * 采购质疑的service抽象类
 * @author 沈帆
 * @createtime 2021-03-16
 * @updatetime 2021-03-16
 */
public interface PurchasingQueryMng extends BaseManager<PurchasingQuery>{

	Pagination pageList(PurchasingQuery bean, Integer page, Integer rows,
			User user, String searchData);

	public	void saveAsk(PurchasingQuery bean, String files, User user) throws Exception;

	public	void delete(Integer id, User user) throws Exception;

	public Pagination answerPageList(PurchasingQuery bean, Integer page, Integer rows,
			User user, String searchData);

	public void saveAnswer(PurchasingQuery bean, String files, User user) throws Exception;

	public void saveCheckInfo(TProcessCheck checkBean, PurchasingQuery bean,
			User user, String spjlFile) throws Exception;

	public Pagination answerCheckPageList(PurchasingQuery bean, Integer page,
			Integer rows, User user, String searchData);

	public void reCallAnswer(String id) throws Exception;

	public void reCallAsk(String id) throws Exception;


}
