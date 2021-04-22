package com.braker.core.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.EconomicClassificationMng;
import com.braker.core.model.Economic;
import com.braker.core.model.EconomicClassificationGovernment;
import com.braker.core.model.User;

@Service
@Transactional
public class EconomicClassificationMngImpl extends BaseManagerImpl<EconomicClassificationGovernment>
		implements EconomicClassificationMng {

	@Override
	public Pagination list(EconomicClassificationGovernment ecg, Integer page, Integer rows) {
		Finder finder=Finder.create("FROM EconomicClassificationGovernment where status='1'");
		if(!StringUtil.isEmpty(ecg.getName())){
			finder.append(" AND name LIKE :name");
			finder.setParam("name","%" + ecg.getName() + "%");
		}
		if(!StringUtil.isEmpty(ecg.getCode())){
			finder.append(" AND code LIKE :code");
			finder.setParam("code","%" + ecg.getCode() + "%");
		}
		if(ecg.getYear() != null){
			finder.append(" AND year='"+ecg.getYear()+"'");
		}
		if(ecg.getPid() != null){
			finder.append(" AND pid="+ecg.getPid());
		}
		return super.find(finder, page, rows);
	}

	@Override
	public int queryEcgByCode(EconomicClassificationGovernment ecg) {
		Finder f = Finder.create("from EconomicClassificationGovernment Where status='1'");
		f.append(" and code = '" + ecg.getCode() + "'");
		if (ecg.getFid() != null) {
			f.append(" and fid != " + ecg.getFid());
		}
		List<Economic> code=super.find(f);
		return code.size();
	}

	@Override
	public void saveEcg(EconomicClassificationGovernment ecg, User user) {
		if (ecg.getFid() == null) {
			ecg.setCreator(user.getAccountNo());
			ecg.setCreateTime(new Date());
			ecg.setStatus("1");
		} else {
			EconomicClassificationGovernment e = findById(ecg.getFid());
			if (!e.getCode().equals(ecg.getCode())) {
				Finder finder = Finder.create(" from EconomicClassificationGovernment where pid=" + e.getCode());
				List<EconomicClassificationGovernment> li = find(finder);
				for (int i = 0; i < li.size(); i++) {
					li.get(i).setPid(Integer.valueOf(ecg.getCode()));
					saveOrUpdate(li.get(i));
				}
			}
			ecg.setStatus(e.getStatus());
			ecg.setUpdator(user.getAccountNo());
			ecg.setUpdateTime(new Date());
		}
		super.merge(ecg);
	}

	@Override
	public void deleteEcg(String id) {
		EconomicClassificationGovernment ecg = findById(Integer.valueOf(id));
		ecg.setStatus("0");
		super.merge(ecg);
	}

}
