package com.braker.icontrol.contract.archiving.manager.Impl;

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
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.archiving.manager.ArchivingMng;
import com.braker.icontrol.contract.archiving.model.Archiving;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.zzww.comm.manager.AttachmentMng;

@Service
@Transactional
public class ArchivingMngImpl extends BaseManagerImpl<Archiving> implements ArchivingMng{

	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	@Override
	public Pagination query_CBI(ContractBasicInfo contractBasicInfo, User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fContStauts <>'99' AND fsealedStatus = '1' AND fToFilesStatus = '0'");
		//条件查询
		if(!StringUtil.isEmpty(contractBasicInfo.getSearchTitle())){
			finder.append("AND (F_CONT_CODE LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_CONT_TITLE LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_OPERATOR LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or to_date(F_REQ_TIME,'yyyy-mm-dd') LIKE '%"+contractBasicInfo.getSearchTitle()+"%') ");
		}
		//分部门或人来归档
		/*finder.append(" AND fOperatorId =:fOperatorId ");
		finder.setParam("fOperatorId", user.getId());
		finder.append("AND fDeptName =:fDeptName ");
		finder.setParam("fDeptName", user.getDepart().getName());*/
//		
//		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
//			finder.append("AND fcCode LIKE :fcCode ");
//			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
//		}
//		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
//			finder.append("AND fcTitle LIKE :fcTitle ");
//			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
//		}
		/*if(!StringUtil.isEmpty(contractBasicInfo.getFcAmount())){
			finder.append("AND fcAmount LIKE :fcAmount ");
			finder.setParam("fcAmount", "%"+contractBasicInfo.getFcAmount()+"%");
		}*/
//		if(contractBasicInfo.getfReqtIME()!=null){
//			finder.append("AND datediff(fReqtIME,'"+contractBasicInfo.getfReqtIME()+"')=0 ");
//		}
		finder.append("order by updateTime desc");
		return super.find(finder,pageNo,pageSize);
	}

	@Override
	public Pagination query_upt(Upt upt, User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM Upt WHERE fUptFlowStauts='9' AND fUptStatus <>'99' AND  fsealedStatus = '1' AND fToFilesStatus = '0'");
		if(!StringUtil.isEmpty(upt.getSearchTitle())){
			finder.append(" AND (F_CONT_CODE LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_CODE_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_NAME_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+upt.getSearchTitle()+"%' or F_OPERATOR LIKE '%"+upt.getSearchTitle()+"%' or to_date(F_REQ_TIME,'yyyy-mm-dd') LIKE '%"+upt.getSearchTitle()+"%') ");
		}
//		if(!StringUtil.isEmpty(upt.getfContName())){
//			finder.append(" AND fContName LIKE :fContName");
//			finder.setParam("fContName", "%"+upt.getfContName()+"%");
//		}
//		if(!StringUtil.isEmpty(upt.getfUptCode())){
//			finder.append(" AND fUptCode LIKE :fUptCode");
//			finder.setParam("fUptCode", "%"+upt.getfUptCode()+"%");
//		}
//		if(upt.getfReqTimeStart()!=null){
//			finder.append(" AND DATE_FORMATE(fReqTime, '%Y-%m-%d') >="+upt.getfReqTimeStart());
//		}
//		if(upt.getfReqTimeEnd()!=null){
//			finder.append(" AND DATE_FORMATE(fReqTime, '%Y-%m-%d') <="+upt.getfReqTimeEnd());
//		}
		finder.append(" order by updateTime desc");
		return super.find(finder,pageNo,pageSize);
	}
	
	@Override
	public void save(ContractBasicInfo contractBasicInfo, Upt upt,String type, Archiving archiving,User user,String files) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		archiving.setfToUser(user.getAccountNo());
		archiving.setfToTime(new Date());
		archiving.setfToCode(StringUtil.Random("GD"));
		archiving.setCreateTime(new Date());
		archiving.setCreator(user.getAccountNo());
		if(archiving.getfToId()==null){
			archiving.setfToId(shenTongMng.getSeq("T_CONTRACT_TOFILE_SEQ"));
		}
		String sql = "";
		if("1".equals(type)){//变更
			archiving.setfUpt_tofile(upt.getfId_U());
			sql=" update T_CONTRACT_UPT SET F_TOFILES_STATUS='1',F_UPDATE_USER='"+user.getAccountNo()+"', F_UPDATE_TIME='"+sdf.format(new Date())+"',F_UPT_DATE='"+DateUtil.formatDate(upt.getfUptdate())+"' where F_ID="+upt.getfId_U();
		}else if("0".equals(type)){//原合同
			archiving.setfContId_tofile(contractBasicInfo.getFcId());
			sql=" update T_CONTRACT_BASIC_INFO SET F_TOFILES_STATUS='1',F_UPDATE_USER='"+user.getAccountNo()+"', F_UPDATE_TIME='"+sdf.format(new Date())+"',F_SIGN_TIME='"+DateUtil.formatDate(contractBasicInfo.getfSignTime())+"' where F_CONT_ID="+contractBasicInfo.getFcId();
		}
		super.saveOrUpdate(archiving);
		attachmentMng.joinEntity(archiving, files);
		Query updateFCS =getSession().createSQLQuery(sql);
		updateFCS.executeUpdate();
	}

	@Override
	public List<Archiving> findByContId(String id) {
		Finder finder = Finder.create(" FROM Archiving WHERE fContId_tofile="+Integer.valueOf(id));
		return super.find(finder);
	}

	@Override
	public Pagination query_CBI_Archiving(ContractBasicInfo contractBasicInfo,User user, Integer pageNo, Integer pageSize) {
		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fContStauts='3' ");
		//分部门或人来归档
		/*finder.append("AND fOperator =:fOperator ");
		finder.setParam("fOperator", user.getAccountNo());
		finder.append("AND fDeptName =:fDeptName ");
		finder.setParam("fDeptName", user.getDepart().getName());
		*/
		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
			finder.append("AND fcCode LIKE :fcCode ");
			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
			finder.append("AND fcTitle LIKE :fcTitle ");
			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getfOperator())){
			finder.append("AND fOperator LIKE :fOperator ");
			finder.setParam("fOperator", "%"+contractBasicInfo.getfOperator()+"%");
		}
		if(contractBasicInfo.getfReqtIME()!=null){
			finder.append("AND fReqtIME = :fReqtIME ");
			finder.setParam("fReqtIME", contractBasicInfo.getfReqtIME());
		}
		return super.find(finder,pageNo,pageSize);
	}
	

	
}
