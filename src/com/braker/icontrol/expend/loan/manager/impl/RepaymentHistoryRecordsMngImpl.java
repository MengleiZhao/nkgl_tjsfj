package com.braker.icontrol.expend.loan.manager.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.icontrol.expend.loan.manager.RepaymentHistoryRecordsMng;
import com.braker.icontrol.expend.loan.model.RepaymentHistoryRecords;

@Service
@Transactional
public class RepaymentHistoryRecordsMngImpl extends BaseManagerImpl<RepaymentHistoryRecords> implements RepaymentHistoryRecordsMng {

	@Override
	public Pagination pageList(Integer lId) {

		Finder finder = Finder.create(" from RepaymentHistoryRecords where lId="+lId+" ");
		finder.append(" ORDER BY payTime DESC ");
		
		Pagination p =  super.find(finder, 0, 10000);
		List<RepaymentHistoryRecords> list = (List<RepaymentHistoryRecords>) p.getList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setNum(i+1);
			}
		}
		return p;
	
	}
}
