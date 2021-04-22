package com.braker.icontrol.cgmanage.cgcheck.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.EconomicMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.cgmanage.cgcheck.manager.CgCheckMng;
import com.braker.icontrol.cgmanage.cgcheck.model.PurchaseCheckInfo;
import com.braker.icontrol.cgmanage.cgexport.manager.ExportCgMng;
import com.braker.icontrol.cgmanage.procurement.manager.ProcurementNeedsMng;
import com.braker.icontrol.cgmanage.procurement.model.ProcurementNeedsInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.zzww.comm.manager.AttachmentMng;


/**
 * 采购申请的service实现类
 * @author 冉德茂
 * @createtime 2018-07-10
 * @updatetime 2018-07-10
 */
@Service
@Transactional
public class CgCheckMngImpl extends BaseManagerImpl<PurchaseApplyBasic> implements CgCheckMng {
	@Autowired
	private TBasicItfMng basicItfMng;
	
	@Autowired
	private ProcurementNeedsMng procurementNeedsMng;
	
	@Autowired
	private TProItfMng proItfMng; 
	
	@Autowired
	private PersonalWorkMng personalWorkMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private EconomicMng economicMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private ExportCgMng exportCgMng;
	
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-16
	 * @updatetime 2018-07-16
	 */
	@Override
	public Pagination pageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData) {		
		Finder finder =Finder.create(" FROM PurchaseApplyBasic WHERE fuserId='"+user.getId()+"' AND fStauts <> '99'");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fpCode like '%"+searchData+"%' or fpName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%' or fUserName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%') ");
		}
		/*if(!StringUtil.isEmpty(bean.getFpCode())){ //按采购编号模糊查询
			finder.append(" AND fpCode LIKE :fpCode");
			finder.setParam("fpCode", "%"+bean.getFpCode()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpName())){ //按采购名称模糊查询
			finder.append(" AND fpName LIKE :fpName");
			finder.setParam("fpName", "%"+bean.getFpName()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFpItemsName())){ //按品目名称
			finder.append(" AND fpItemsName =:fpItemsName");
			finder.setParam("fpItemsName", bean.getFpItemsName());
		}*/
		finder.append(" order by fpId desc");
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 历史审批记录
	 * @author 冉德茂
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	@Override
	public List<PurchaseCheckInfo> checkHistory(Integer id, String stauts) {
		Finder finder = Finder.create(" FROM PurchaseCheckInfo WHERE fpId="+id);
		if(stauts != null) {
			finder.append(" AND stauts='"+stauts+"'");
		}
		finder.append(" order by cId desc");
		List<PurchaseCheckInfo> li = super.find(finder);
		return li;
	}

	/*
	 * 保存审核信息
	 * @author 李安达
	 * @createtime 2019-04-129
	 * @updatetime 2019-04-129
	 */
	@Override
	public void saveCheckInfo(TProcessCheck checkBean, PurchaseApplyBasic bean, User user,String files,String hyjyfiles,String czbmyjfiles)  throws Exception {
		//保存党组会议号
		String fDZHCode = null;
		String fpPype = null;
		String fpMethod = null;
		if(!StringUtil.isEmpty(bean.getfDZHCode())) {//采购方式已由某级审批人输入
			fDZHCode = bean.getfDZHCode();
			bean = this.findById(bean.getFpId());
			bean.setfDZHCode(fDZHCode);
		}else {
			if(!StringUtil.isEmpty(bean.getFpPype()) && !StringUtil.isEmpty(bean.getFpMethod())){
				fpPype = bean.getFpPype();
				fpMethod = bean.getFpMethod();
				bean = this.findById(bean.getFpId());
				bean.setFpPype(fpPype);
				bean.setFpMethod(fpMethod);
			}else{
				bean = this.findById(bean.getFpId());
			}
		}
		
		CheckEntity entity = (CheckEntity)bean;
		String checkUrl = "/cgcheck/check?id=";
		String lookUrl = "/cgsqsp/detail?id=";
		if("1".equals(bean.getfIsPers())){
			//查询工作流
			bean = (PurchaseApplyBasic)tProcessCheckMng.checkProcess(checkBean, entity, user, "SMCGSQ", checkUrl, lookUrl, files);
		}else{
			//查询工作流
			bean = (PurchaseApplyBasic)tProcessCheckMng.checkProcess(checkBean, entity, user, "CGSQ", checkUrl, lookUrl, files);
		}
		
		if("0".equals(checkBean.getFcheckResult())){//审批不通过
			//归还冻结金额
			Double syAmount = budgetIndexMgrMng.deleteDjAmount(bean.getIndexId(), bean.getProDetailId(), bean.getAmount());
		}
		//在这里判断是不是会议号录入岗
		//只有党建办公室会议号录入岗才可以填写党组会会议号
		if(!"".equals(bean.getNextCheckUserId())){
			User userNext= userMng.findById(bean.getNextCheckUserId());
			if(userNext.getRoleName().contains("会议号录入岗") && "党建办公室".equals(userNext.getDepart().getName())){
				exportCgMng.arrangeCheckDetailApply(bean, "", null);
			}
		}
		if("9".equals(bean.getfCheckStauts())){//审批通过
			if("1".equals(bean.getfIsImp())){
				bean.setfIsImpFiles("0");
			}else if("0".equals(bean.getfIsImp())){
				bean.setfIsImpFiles("1");
			}
			//生成审签数据
			//exportCgMng.arrangeCheckDetailApply(bean, "", null);
		}
		bean = (PurchaseApplyBasic) super.saveOrUpdate(bean);
		//如果行业主管部门意见上传附件不为空
		if(!StringUtil.isEmpty(hyjyfiles)){
			attachmentMng.joinEntity(bean,hyjyfiles);
		}
		//如果财政部门意见上传附件不为空
		if(!StringUtil.isEmpty(czbmyjfiles)){
			attachmentMng.joinEntity(bean,czbmyjfiles);
		}
	}
	
}
