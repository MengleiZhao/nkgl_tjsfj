package com.braker.icontrol.contract.dispute.manager.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.LookupsMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.dispute.manager.DisputAttacMng;
import com.braker.icontrol.contract.dispute.manager.DisputeMng;
import com.braker.icontrol.contract.dispute.model.DisputAttac;
import com.braker.icontrol.contract.dispute.model.Dispute;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class DisputeMngImpl extends BaseManagerImpl<Dispute> implements DisputeMng{

	@Autowired
	private DisputAttacMng disputAttacMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private LookupsMng lookupsMng;
	
	@Override
	public Pagination list(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize) {
		Finder finder = Finder.create(" FROM ContractBasicInfo WHERE (fContStauts='9' OR fContStauts='7'OR fContStauts='11'OR fContStauts='1') AND fFlowStauts='9' ");
		finder.append(" AND fOperatorId =:fOperatorId ");
		finder.setParam("fOperatorId", user.getId());
		finder.append("AND fDeptName =:fDeptName ");
		finder.setParam("fDeptName", user.getDepart().getName());
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append("AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(contractBasicInfo.getfReqtIME()!=null){
			finder.append("AND datediff(fReqtIME,'"+contractBasicInfo.getfReqtIME()+"')=0 ");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcAmount())){
			finder.append("AND fcAmount LIKE :fcAmount ");
			finder.setParam("fcAmount", "%"+contractBasicInfo.getFcAmount()+"%");
		}
		finder.append("order by updateTime desc");
		return super.find(finder,pageNo,pageSize);
		
	}

	@Override
	public void save(Dispute dispute,String fhtjffiles, User user,ContractBasicInfo contractBasicInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtil.isEmpty(String.valueOf(dispute.getfDisId()))){
			dispute.setCreateTime(new Date());
			dispute.setCreator(user.getAccountNo());
			dispute.setfDisId(StringUtil.random8());
		}else {
			dispute.setUpdateTime(new Date());
			dispute.setUpdator(user.getAccountNo());
		}
		Lookups fDisType = lookupsMng.findByLookCode(dispute.getfDisType().getCode());
		dispute.setfDisType(fDisType);
		dispute.setfRecUser(user.getAccountNo());
		dispute.setfContId_D(contractBasicInfo.getFcId());
		dispute.setfRecTime(new Date());
		dispute=(Dispute) super.merge(dispute);
		//保存附件信息
		attachmentMng.joinEntity(dispute,fhtjffiles);
		String sql="update T_CONTRACT_BASIC_INFO set F_DISPUTE_STATUS='1',F_UPDATE_USER='"+user.getAccountNo()+"', F_UPDATE_TIME='"+sdf.format(new Date())+"' where F_CONT_ID="+contractBasicInfo.getFcId();
		Query query=getSession().createSQLQuery(sql);
		query.executeUpdate();
		/*if(!StringUtil.isEmpty(disputFile.get(0).getfAttacName_DA())){
			disputAttacMng.saveDisputFile(dispute, disputFile, user);
		}*/
	}

	@Override
	public int findD(String id) {
		Finder finder = Finder.create(" FROM Dispute WHERE fContId_D = "+id);
		return super.find(finder).size();
	}

	@Override
	public List<Dispute> findByContId(String id) {
		Finder finder = Finder.create(" FROM Dispute WHERE fContId_D = "+id);
		return super.find(finder);
	}

}
