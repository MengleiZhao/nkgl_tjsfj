package com.braker.core.manager.impl;

import java.io.Serializable;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.core.manager.ShenTongMng;

/**
 * 神通数据库ServiceImpl
 * @author wanping
 *
 */
@Service
@Transactional
public class ShenTongMngImpl extends BaseManagerImpl<Serializable> implements ShenTongMng {

	@Override
	public Integer getSeq(String seqName) {
		String sql = "select nextval('" + seqName + "')";
		SQLQuery query = getSession().createSQLQuery(sql);
		Object obj = query.uniqueResult();
		int seq = Integer.parseInt(String.valueOf(obj));
		return seq;
	}

}
