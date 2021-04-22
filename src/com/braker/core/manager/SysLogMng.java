package com.braker.core.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.SysLog;

public interface SysLogMng extends BaseManager<SysLog>{
	
	public Pagination list(SysLog log,String sort,String order,int pageIndex,int pageSize);
	
	/**
	 * 日志归档，归档日期之前的记录都移动到日志历史表
	 * @param archiveTime 归档日期
	 */
	public void archive(String archiveDate);
}
