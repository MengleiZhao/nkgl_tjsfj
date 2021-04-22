package com.braker.icontrol.expend.apply.manager;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.icontrol.expend.apply.model.OutsideTrafficInfo;

/**
 * 城市间交通费service层
 * @author 赵孟雷
 *
 */
public interface OutsideTrafficInfoMng extends BaseManager<OutsideTrafficInfo>{
	/**
	 * 事前城市间交通费查询
	 */
	public Pagination outsideTrafficInfoPageList(int pageNo, int pageSize,OutsideTrafficInfo bean);
	
	/**
	 * 事后城市间交通费查询
	 */
	Pagination routsideTrafficInfoPageList(int pageNo, int pageSize,OutsideTrafficInfo bean);
}
