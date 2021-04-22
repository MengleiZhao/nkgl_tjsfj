package com.braker.icontrol.incomemanage.accountsCurrent.manager;

import java.util.List;

import com.braker.common.entity.DataEntity;
import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.incomemanage.accountsCurrent.model.AccountsCurrent;
import com.braker.workflow.entity.TProcessCheck;

public interface AccountsCurrentMng extends BaseManager<AccountsCurrent>{

	/*
	 * 分页查询
	 * @author 赵孟雷
	 * @createtime 2020-11-10
	 * @updatetime 2020-11-10
	 */
	public Pagination pageList(AccountsCurrent bean, int pageNo, int pageSize, User user) throws Exception;
	/*
	 * 分页查询
	 * @author 赵孟雷
	 * @createtime 2020-11-10
	 * @updatetime 2020-11-10
	 */
	public Pagination pageLedgerList(AccountsCurrent bean, int pageNo, int pageSize, User user) throws Exception;
	/**
	 * 往来款查看分页查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws Exception
	 * @author 赵孟雷
	 * @createtime 2021年3月22日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月22日
	 */
	public List<DataEntity> pageLedgerDetailList(AccountsCurrent bean, int pageNo, int pageSize, User user) throws Exception;
	/*
	 * 分页查询
	 * @author 赵孟雷
	 * @createtime 2020-11-10
	 * @updatetime 2020-11-10
	 */
	public Pagination pageCheckList(AccountsCurrent bean, int pageNo, int pageSize,  User user) throws Exception;
	/*
	 * 分页查询
	 * @author 赵孟雷
	 * @createtime 2020-11-10
	 * @updatetime 2020-11-10
	 */
	public Pagination affirmPageList(AccountsCurrent bean, int pageNo, int pageSize, Integer type, User user);
	/*
	 * 新增
	 * @author 赵孟雷
	 * @createtime 2020-11-10
	 * @updatetime 2020-11-10
	 */
	public void save(AccountsCurrent bean,String files,User user) throws Exception;
	/*
	 * 审核
	 * @author 赵孟雷
	 * @createtime 2020-11-10
	 * @updatetime 2020-11-10
	 */
	public void check(TProcessCheck checkBean,AccountsCurrent bean,String files,User user) throws Exception;
	/*
	 * 来款立项申请撤回
	 * @author 赵孟雷
	 * @createtime 2020-11-13
	 * @updatetime 2020-11-13
	 */
	public String accountsCurrentReCall(Integer id) throws Exception;
	/*
	 * 删除
	 * @author 赵孟雷
	 * @createtime 2020-11-13
	 * @updatetime 2020-11-13
	 */
	public void delete(Integer id,User user,String fId);
	public List<AccountsCurrent> findByCondition(String currentDate);
}
