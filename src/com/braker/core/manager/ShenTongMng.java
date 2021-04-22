package com.braker.core.manager;

/**
 * 神通数据库Service
 * @author wanping
 *
 */
public interface ShenTongMng {

	/**
	 * 根据序列名获取序列值
	 * @param seqName
	 * @return
	 * @author wanping
	 * @createtime 2021年3月30日
	 * @updator wanping
	 * @updatetime 2021年3月30日
	 */
	public Integer getSeq(String seqName);
}
