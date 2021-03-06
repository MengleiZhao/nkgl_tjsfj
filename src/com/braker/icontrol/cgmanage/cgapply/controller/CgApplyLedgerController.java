package com.braker.icontrol.cgmanage.cgapply.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.model.Proposer;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * ??????????????????????????????
 * ???????????????????????????????????????????????????
 * @author ?????????
 * @createtime 2018-07-20
 * @updatetime 2018-07-20
 */

@Controller
@RequestMapping(value = "/cgsqLedger")
public class CgApplyLedgerController extends BaseController{
	
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TProExpendDetailMng detailMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private CgReceiveMng cgReceiveMng;
	@Autowired
	private RegisterApplyMng registerApplyMng;
	@Autowired
	private ApplyMng applyMng;
	
	/*
	 * ?????????????????????
	 * @author  ?????????
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model,String proId) {
		if(!StringUtil.isEmpty(proId)){
			TProBasicInfo tpbi=tProBasicInfoMng.findById(Integer.valueOf(proId));
			TBudgetIndexMgr bim = budgetIndexMgrMng.findByIndexCode(tpbi.getFProCode());
			
			model.addAttribute("indexCode", bim.getbId());
		}
		return "/WEB-INF/view/purchase_manage/purchase/cgsqledger_list";
	}

	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	@RequestMapping(value = "/cgledgerPage")
	@ResponseBody
	public JsonPagination noticePage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = cgsqMng.ledgerPageList(bean, page, rows, getUser(),searchData);
		List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//????????????	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	@RequestMapping(value = "/ledgerDetail")
	public String ledgerDetail(ModelMap model, Integer id){
		try {
			//????????????????????????
			PurchaseApplyBasic bean = cgsqMng.findById(id);
			model.addAttribute("bean", bean);
			PurchaseApplyBasic beanCopy = new PurchaseApplyBasic();
			BeanUtils.copyProperties(bean,beanCopy);
			//???????????????????????????
			List<Attachment> attac = attachmentMng.list(bean);
			model.addAttribute("attac", attac);
			//??????????????????????????????
			RegisterApplyBasic rBean = new RegisterApplyBasic();
			List<RegisterApplyBasic> DJList = registerApplyMng.findByProperty("fpId", id);
			if(DJList.size()>0){
				rBean = DJList.get(0);
				//??????????????????????????????
				List<Attachment> brAttac = attachmentMng.list(rBean);
				model.addAttribute("brAttac", brAttac);
				
				//???????????????????????????????????????
				List<Object> listBiddingRegist = applyMng.getMingxi("BiddingRegist", "fpId", id);
				model.addAttribute("list", listBiddingRegist);
				model.addAttribute("listIndex", listBiddingRegist.size());
				//??????????????????1
				model.addAttribute("DJ", 1);
				
				//?????????????????????
				//???????????????id
				TProcessDefin tProcessDefinT = tProcessDefinMng.getByBusiAndDpcode("HWCGYS",bean.getfDeptId());
				model.addAttribute("fpIdT", tProcessDefinT.getFPId());
				//????????????
				model.addAttribute("foCodeT", rBean.getBeanCode());
			}else{
				rBean = new RegisterApplyBasic();
				//?????????????????????0
				model.addAttribute("DJ", 0);
			}
			model.addAttribute("brBean", rBean);
			
			//??????????????????????????????
			List<AcceptCheck> YSList = cgReceiveMng.checkHistory(id);
			AcceptCheck acceptCheck = null;
			if(YSList.size()>0){
				acceptCheck =YSList.get(0);
				//??????????????????1
				model.addAttribute("YS", 1);
				//?????????????????????
				//???????????????id
				TProcessDefin tProcessDefinCheck = tProcessDefinMng.getByBusiAndDpcode("HWCGYS",bean.getfDeptId());
				model.addAttribute("fpIdC", tProcessDefinCheck.getFPId());
				//????????????
				model.addAttribute("foCodeC", acceptCheck.getBeanCode());
			}else{
				acceptCheck = new AcceptCheck();
				//?????????????????????0
				model.addAttribute("YS", 0);
			}
			model.addAttribute("check", acceptCheck);
			//????????????????????????
			Integer detailId = bean.getProDetailId();
			Integer indexId = bean.getIndexId();
			if (detailId != null && indexId != null) {
				TProExpendDetail detail = detailMng.findById(detailId);
				TBudgetIndexMgr index = indexMng.findById(indexId);
				if (detail != null && index != null) {
					//????????????
					bean.setIndexName(index.getIndexName()+"??? "+detail.getSubName()+" ???");
					//????????????
					bean.setPfAmount(detail.getOutAmount());		
					//????????????
					if (index.getAppDate() != null) {
						bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
					}
					//????????????
					bean.setPfDepartName(index.getDeptName());			
					//????????????
					bean.setSyAmount(detail.getSyAmount());		
					//????????????
					if("0".equals(index.getIndexType())){
						bean.setPfIndexType("????????????");		
					}else{
						bean.setPfIndexType("????????????");		
					}
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//????????????
				bean.setIndexName(index.getIndexName()+"??? "+index.getIndexCode()+" ???");
				//????????????
				bean.setPfAmount(index.getPfAmount()*10000);		
				//????????????
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//????????????
				bean.setPfDepartName(index.getDeptName());			
				//????????????
				bean.setSyAmount(index.getSyAmount()*10000);	
				//????????????
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("????????????");		
				}else{
					bean.setPfIndexType("????????????");		
				}
			}
			
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGSQ",bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			String times ="";
			if(!"".equals(bean.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(bean.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+bean.getfUserName()+"</td></tr>"
					+ "<tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"148\" style=\"border: #ffffff !important;text-align:left;\">"+times+"</td></tr>";
			String a4 ="<tr height=\"10px\" style=\"\"><td colspan=\"3\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\">";
			String a5 ="</td></tr><tr height=\"10px\"></tr><tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\">";
			String a6 ="</td><td  width=\"148\" style=\"border: #ffffff !important;text-align:left;\">";
			String a7 ="</td></tr>";
			String i = "</tbody></table>";
			List<TNodeData> datas = new ArrayList<TNodeData>();
			for (int z = 0; z < nodeConfList.size(); z++) {
				datas.add(nodeConfList.get(z));
			}
			Collections.reverse(datas); //????????????
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+bean.getProSignName()+"</p><br/>";
			a0 +=bean.getProSignContent();
			a0 +=aa;
			for (int j = 0; j < datas.size(); j++) {
				if("1".equals(datas.get(j).getCheckInfo().getFcheckResult())){
					a0 +=a4;
					a0 +=datas.get(j).getCheckInfo().getFcheckRemake();
					a0 +=a5;
					a0 +="<img  width=\"150\" src=\""+request.getContextPath()+"/attachment/downloadQZ/"+datas.get(j).getUser().getId()+"\"/>";
					a0 +=a6;
					String time ="";
					if(!"".equals(datas.get(j).getCheckInfo().getFcheckTime())){
						DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
						time =fmt.format(datas.get(j).getCheckInfo().getFcheckTime());     // ????????? X???X???X???
					}
					a0 +=time;
					a0 +=a7;
				}
			}
			a0 +=i;
			beanCopy.setProSignContent(a0);
			model.addAttribute("beanCopy", beanCopy);
			//???????????????id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ",bean.getfDeptId());
			model.addAttribute("fpIdApply", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCodeApply", bean.getBeanCode());
			return "/WEB-INF/view/purchase_manage/purchase/cggl_ledger_detail";
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ??????????????????????????????
	 * @author ?????????
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @createtime 2020???7???10???
	 * @updator ?????????
	 * @updatetime 2020???7???10???
	 */
	@RequestMapping(value = "/cgledgerReceive")
	@ResponseBody
	public JsonPagination cgledgerReceive(AcceptCheck bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = 100;}
		Pagination p = cgsqMng.ledgerReceiveList(bean, page, rows, getUser());
		List<AcceptCheck> li = (List<AcceptCheck>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//????????????	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}
}
