package com.braker.icontrol.contract.goldpay.manager.Impl;

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
import com.braker.core.manager.ShenTongMng;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.goldpay.manager.GoldPayMng;
import com.braker.icontrol.contract.goldpay.model.GoldPay;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class GoldPayMngImpl extends BaseManagerImpl<GoldPay> implements GoldPayMng{

	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	@Override
	public Pagination find(GoldPay bean, User user,Integer pageNo,Integer pageSize) {
		Finder finder =Finder.create(" FROM GoldPay WHERE fStauts<>'99'");
		/*if(!StringUtil.isEmpty(bean.getfWarCode())){
			finder.append("AND fWarCode LIKE :fWarCode ");
			finder.setParam("fWarCode", "%"+bean.getfWarCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+bean.getFcTitle()+"%");
		}*/
		
		finder.append("ORDER BY updateTime DESC");
		Pagination p = super.find(finder, pageNo, pageSize);
		return p;
	}

	@Override
	public void save(GoldPay bean, User user,String files) {
		
	}

	@Override
	public List<GoldPay> findByContId(String id, String dataType) {
		Finder finder =Finder.create(" FROM GoldPay WHERE fContId_GP ="+Integer.valueOf(id)+"AND fDataType='"+dataType+"'");
		return super.find(finder);
	}

	@Override
	public void incomeSave(ContractBasicInfo contractBasicInfo, User user,
			GoldPay goldPay) {
		String contType =goldPay.getContType();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtil.isEmpty(String.valueOf(goldPay.getfWarId()))){
			goldPay.setfWarId(shenTongMng.getSeq("warranty_gold_p_seq"));
			goldPay.setCreateTime(new Date());
			goldPay.setCreator(user.getName());
		}else{
			goldPay.setUpdateTime(new Date());
			goldPay.setUpdator(user.getName());
		}
		goldPay.setfRecUser(user.getName());
		goldPay.setfDataType("0");
		goldPay=(GoldPay) super.saveOrUpdate(goldPay);
		String sql ="";
		if(contType.equals("0")){
			sql ="UPDATE T_CONTRACT_BASIC_INFO SET F_INCOME_STAUTS='1',F_UPDATE_USER='"+user.getName()+"', F_UPDATE_TIME='"+sdf.format(new Date())+"', F_INCOME_DATE='"+sdf.format(goldPay.getFpayedDate())+"' WHERE F_CONT_ID="+goldPay.getfContId_GP();
		}else{
			sql ="UPDATE T_CONTRACT_UPT SET F_INCOME_STAUTS='1',F_UPDATE_USER='"+user.getName()+"', F_UPDATE_TIME='"+sdf.format(new Date())+"', F_INCOME_DATE='"+sdf.format(goldPay.getFpayedDate())+"' WHERE F_ID="+goldPay.getfContId_GP();
		}
		Query query=getSession().createSQLQuery(sql);
		query.executeUpdate();
		
	}

	/**
	 * 如果查出来有值说明有正在变更或者已变更的数据
	 */
	@Override
	public Boolean queryHasChanged(Integer id) {
		Query query = getSession().createSQLQuery("SELECT count(1) from T_CONTRACT_BASIC_INFO cbi LEFT JOIN t_contract_upt cu on cbi.F_CONT_ID=cu.F_CONT_ID WHERE cbi.F_CONT_ID="+id+" and ( cu.F_FLOW_STAUTS ='1')");
		int i =(int) query.list().get(0);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}


}
