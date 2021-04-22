package com.braker.icontrol.contract.Formulation.manager.Impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.manager.SealInfoMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.SealInfo;
import com.braker.icontrol.contract.enforcing.model.Upt;

@Service
@Transactional
public class SealInfoMngImpl extends BaseManagerImpl<SealInfo> implements SealInfoMng {
	@Autowired
	private ShenTongMng shenTongMng;

	@Override
	public void save(SealInfo bean, Integer id, User user,String type,ContractBasicInfo cbiBean,Upt uptbean) {
		if(bean.getFsId() == null) {
			//创建人、创建时间 
			bean.setCreator(user.getName());
			bean.setCreateTime(bean.getFappTime());
			
			//盖章人id
			bean.setFappUserId(user.getId());
			//自动生成编号
			String str = "HTGZ";
			bean.setFsCode(StringUtil.Random(str));
			//设置盖章信息与合同外键关联
			bean.setFcId(id);
			Integer idfsId =shenTongMng.getSeq("T_CONTRACT_SEAL_INFO_SEQ");
			bean.setFsId(idfsId);
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getName());
			bean.setUpdateTime(bean.getFappTime());
		}
		if("1".equals(type)){
			uptbean.setFsealedStatus("1");
			super.merge(uptbean);
		}else{
			cbiBean.setFsealedStatus("1");
			super.merge(cbiBean);
		}
		//保存基本信息
		bean = (SealInfo) super.saveOrUpdate(bean);
	}

	@Override
	public Integer findFsIdByFcId(Integer id) {
		Integer fsId = null;
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT F_SEAL_ID FROM T_CONTRACT_SEAL_INFO WHERE F_CONT_ID = "+id);
		SQLQuery query = getSession().createSQLQuery(builder.toString());
		List<Integer> fsIdList = query.list();
		if(fsIdList != null && fsIdList.size() > 0){
			fsId = fsIdList.get(0);
		}
		return fsId;
	}

}
