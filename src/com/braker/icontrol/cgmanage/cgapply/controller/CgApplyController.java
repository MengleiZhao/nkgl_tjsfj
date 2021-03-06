package com.braker.icontrol.cgmanage.cgapply.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.braker.common.ftp.FileUpload;
import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.FileUpLoadUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgapply.model.BuyInfo;
import com.braker.icontrol.cgmanage.cgcheck.manager.CgCheckMng;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialList;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgBidMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgSelMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.cgmanage.cgsupplier.manager.EvalSupplierMng;
import com.braker.icontrol.cgmanage.cgsupplier.model.SupplierEvaluaInfo;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.cgmanage.procurement.manager.PurchasePlanMng;
import com.braker.icontrol.cgmanage.procurement.model.PurchasePlanBasic;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;
import com.fasterxml.jackson.databind.util.BeanUtil;
/**
 * ??????????????????????????????
 * ?????????????????????????????????????????????
 * @author ?????????
 * @createtime 2018-07-10
 * @updatetime 2018-07-10
 */
@Controller
@RequestMapping(value = "/cgsqsp")
public class CgApplyController extends BaseController{
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private CgBidMng cgbidMng;
	
	@Autowired
	private CgSelMng cgselMng;
	
	@Autowired
	private CgReceiveMng cgreceiveMng;

	@Autowired
	private CgCheckMng cgcheckMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private CgProcessMng processMng;
	@Autowired
	private FormulationMng formulationMng;
	
	@Autowired
	private TProExpendDetailMng detailMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
	@Autowired
	private LookupsMng lookupsMng;
	
	@Autowired
	private PurchasePlanMng purchasePlanMng;
	
	/*
	 * 
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model, String proId, String sign) {
		if(!StringUtil.isEmpty(proId)){
			if("0".equals(sign)){
				TBudgetIndexMgr bim = budgetIndexMgrMng.findByIndexCode(proId);
				model.addAttribute("indexCode", bim.getIndexCode());
				model.addAttribute("proId", proId);
				return "/WEB-INF/view/purchase_manage/purchase/cgsq_indexcode_list_zxk";
			}else{
				TProBasicInfo tpbi=tProBasicInfoMng.findById(Integer.valueOf(proId));
				TBudgetIndexMgr bim = budgetIndexMgrMng.findByIndexCode(tpbi.getFProCode());
				model.addAttribute("indexCode", bim.getbId());
				return "/WEB-INF/view/purchase_manage/purchase/cgsq_indexcode_list";
			}
		}else{
			return "/WEB-INF/view/purchase_manage/purchase/cgsq_list";
		}
	}
	
	/*
	 * ????????????????????????????????????list??????
	 * @author ?????????
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@RequestMapping(value = "/checkedlist")
	public String checkedlist( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/conf_plan_checked_list";
	}
	
	/**
	 * ??????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???15???
	 * @updator ?????????
	 * @updatetime 2020???6???15???
	 */
	@RequestMapping(value = "/executionlist")
	public String executionlist( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cgsq_execution_list";
	}
	
	/*
	 * ???????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-22
	 * @updatetime 2018-08-22
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cg_checkIndex";
		//return "/WEB-INF/view/choiceIndex";
	}
	
	
	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@RequestMapping(value = "/cgsqPage")
	@ResponseBody
	public JsonPagination loanPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.pageList(bean, page, rows, getUser(),searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
//    		if(!"".equals(li.get(x).getFpItemsName())){
//    			Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
//        		li.get(x).setFpItemsNames(lookups.getName());	
//    		}
    		//????????????	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}	
	
	@RequestMapping(value = "/executioncgsqPage")
	@ResponseBody
	public JsonPagination executioncgsqPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = cgsqMng.executioncgsqPage(bean, page, rows, getUser(),searchData);
		List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
		for(int x=0; x<li.size(); x++) {
			Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
			li.get(x).setFpItemsNames(lookups.getName());	
			//????????????	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}	
	
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping(value = "/cgsqPageChoose")
	@ResponseBody
	public JsonPagination cgsqPageChoose(PurchaseApplyBasic bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.pageList(bean, page, rows, null,null);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//????????????id
    		List<ContractBasicInfo> contractList=formulationMng.findByProperty("fPurchNo", li.get(x).getBeanId().toString());
    		if(contractList!=null && contractList.size()>0){
    			ContractBasicInfo contract=contractList.get(0);
            	li.get(x).setContractId(contract.getBeanId());
    		}
    		//????????????	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	
	
	
	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id,String fId,ModelMap model) {
		try {
			//????????????id?????????
			cgsqMng.delete(id,fId,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");	
	}
	
	/**
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2019-10-08
	 * @updatetime 2019-10-08
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			cgsqMng.reCall(id);
			return getJsonResult(true, "????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}

	}
	/*
	 * ??????????????????id??????????????????
	 * @author ?????????
	 * @createtime 2018-11-29
	 * @updatetime 2018-11-29
	 * /cgsqsp/getFpAmount?fpId=??????id
	 */
	@RequestMapping(value="getFpAmount")
	@ResponseBody
	String findFpAmountbyfpCode(String fpId){
		return cgsqMng.findFpAmountbyfpId(fpId);
	}
	
	/*
	 * ???????????? ??????
	 * @author ?????????
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, Integer id){
		//??????????????????
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		//??????????????????id
		List<BuyInfo> list1 = cgsqMng.findByText(bean.getFpMethod());
		if(list1.size()>0){
			model.addAttribute("cgType", list1.get(0).getId());
		}else{
			model.addAttribute("cgType", "");
		}
		//??????????????????id
		List<BuyInfo> list2 = cgsqMng.findByText(bean.getFpPype());
		if(list2.size()>0){
			for(int i = 0;i<list2.size();i++){
				if("????????????".equals(bean.getFpMethod())){
					if(list2.get(i).getParentId() == 1){
						model.addAttribute("cgWay", list2.get(i).getId());
					}
				}
				if("????????????????????????".equals(bean.getFpMethod())){
					if(list2.get(i).getParentId() == 2){
						model.addAttribute("cgWay", list2.get(i).getId());
					}
				}else{
					model.addAttribute("cgType", list2.get(0).getId());
				}
			}
		}else{
			model.addAttribute("cgWay", "");
		}
		//??????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
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
		//????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		if(roleName.contains("???????????????")&&"1".equals(bean.getfCheckStauts())){
			model.addAttribute("cgglg", 1);//???????????????????????????1   ??????????????????????????????????????????
		}else{
			model.addAttribute("cgglg", 0);//???????????????????????????0   ?????????????????????????????????????????????
		}
		if("1".equals(bean.getfIsPers())){
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(),"SMCGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//???????????????id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("SMCGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//?????????????????????????????????
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//??????????????????
			String historyNodes = tProcessCheckMng.getHistoryNodes("SMCGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//????????????
			model.addAttribute("foCode", bean.getBeanCode());
		}else{
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(),"CGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//???????????????id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//?????????????????????????????????
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//??????????????????
			String historyNodes = tProcessCheckMng.getHistoryNodes("CGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//????????????
			model.addAttribute("foCode", bean.getBeanCode());
		}
		//??????????????????
		model.addAttribute("czlx","edit");
		return "/WEB-INF/view/purchase_manage/purchase/cggl_add";
	}
	
	/**
	 * ????????????????????????
	 * @param model
	 * @param id
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???16???
	 * @updator ?????????
	 * @updatetime 2020???6???16???
	 */
	@RequestMapping(value = "/executionEdit")
	public String executionEdit(ModelMap model, Integer id, String openType){
		//????????????????????????????????????
		model.addAttribute("openType", openType);
		
		//??????????????????
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		
		PurchaseApplyBasic beanCopy = new PurchaseApplyBasic();
		BeanUtils.copyProperties(bean,beanCopy);
		
		//??????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
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
		}
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
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
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
		model.addAttribute("proposer", proposer);
		
		//??????????????????
		String historyNodes = tProcessCheckMng.getHistoryNodes("CGSQ", bean.getfDeptId(), bean.getBeanCode());
		model.addAttribute("historyNodes", historyNodes);
		
		//????????????
		model.addAttribute("foCode",bean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/purchase/cggl_execution";
	}
	
	/*
	 * ???????????? ????????????
	 * @author ?????????
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	@RequestMapping(value ="/detail")
	public String detail(ModelMap model, Integer id, String openType){
		//????????????????????????????????????
		model.addAttribute("openType", openType);
		
		//??????????????????
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		//??????????????????id
		List<BuyInfo> list = cgsqMng.findByText(bean.getFpPype());
		for(int i = 0;i<list.size();i++){
			if("????????????".equals(bean.getFpMethod())){
				if(list.get(i).getParentId() == 1){
					model.addAttribute("cgType", list.get(i).getId());
				}
			}
			if("????????????????????????".equals(bean.getFpMethod())){
				if(list.get(i).getParentId() == 2){
					model.addAttribute("cgType", list.get(i).getId());
				}
			}else{
				model.addAttribute("cgType", list.get(0).getId());
			}
		}
		PurchaseApplyBasic beanCopy = new PurchaseApplyBasic();
		BeanUtils.copyProperties(bean,beanCopy);
		//??????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
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

		if("1".equals(bean.getfIsPers())){
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "SMCGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
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
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("SMCGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//?????????????????????????????????
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//??????????????????
			String historyNodes = tProcessCheckMng.getHistoryNodes("SMCGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());
		}else{
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
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
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//?????????????????????????????????
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//??????????????????
			String historyNodes = tProcessCheckMng.getHistoryNodes("CGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());
		}
		return "/WEB-INF/view/purchase_manage/purchase/cggl_detail";
	}
	
	/**
	 * ???????????? ?????????????????????
	 * @author ?????????
	 * @createtime 2019-05-24
	 * @updatetime 2019-05-24
	 */
	@RequestMapping(value = "/refreshProcess")
	public String rerefreshProcess(ModelMap model, String workFlow){
		if("1".equals(workFlow)){
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "SMCGSQ", getUser().getDpID(), null, null, null, null, null, null);
			model.addAttribute("nodeConf", nodeConfList);
			
			//?????????????????????????????????
			Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
			model.addAttribute("proposer", proposer);

		}else{
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "CGSQ", getUser().getDpID(), null, null, null, null, null, null);
			model.addAttribute("nodeConf", nodeConfList);
			
			//?????????????????????????????????
			Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
			model.addAttribute("proposer", proposer);
		}
		return "/WEB-INF/view/check_system";
	}
	/**
	 * ???????????? ?????????????????????
	 * @author ?????????
	 * @createtime 2020-03-26
	 * @updatetime 2019-03-26
	 */
	@RequestMapping(value = "/refreshProcessLedger")
	public String refreshProcessLedger(ModelMap model, String fpPype,String str,String fpId){
		//????????????
		String busiArea = null;
		//???????????????
		List<TNodeData> nodeConfList =null;
		if("0".equals(str)){
			if ("A10".equals(fpPype)) {
				busiArea = "HWCGSQ";
			} else if ("A20".equals(fpPype)) {
				busiArea = "GCCGSQ";
			} else if ("A30".equals(fpPype)) {
				busiArea = "HWCGSQ";
			}
			//??????????????????
			PurchaseApplyBasic bean = cgsqMng.findById(Integer.valueOf(fpId));
			//???????????????
			nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), busiArea, bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
		}else{
			if("1".equals(str)){
				if ("A10".equals(fpPype)) {
					busiArea = "HWCGYS";
				} else if ("A20".equals(fpPype)) {
					busiArea = "GCCGYS";
				} else if ("A30".equals(fpPype)) {
					busiArea = "HWCGYS";
				}
				
				BiddingRegist bean = null;
				List<BiddingRegist> listBiddingRegist =processMng.findByProperty("fpId",Integer.valueOf(fpId) );
				if(listBiddingRegist.isEmpty()){
					bean = new BiddingRegist();
				}else{
					bean = listBiddingRegist.get(0);
				}
				//???????????????
				nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), busiArea, bean.getFregDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFbiddingCode(), "1");
				model.addAttribute("nodeConf", nodeConfList);
			}else{
				if("2".equals(str)){
					if ("A10".equals(fpPype)) {
						busiArea = "HWCGYS";
					} else if ("A20".equals(fpPype)) {
						busiArea = "GCCGYS";
					} else if ("A30".equals(fpPype)) {
						busiArea = "HWCGYS";
					}
					
					AcceptCheck bean =null;
					List<AcceptCheck> listAcBean =cgreceiveMng.findByProperty("fpId", Integer.valueOf(fpId));
					if(listAcBean.isEmpty()){
						bean = new AcceptCheck();
					}else{
						bean = listAcBean.get(0);
					}
					//???????????????
					nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), busiArea, bean.getfDepartId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFacpCode(), "1");
					model.addAttribute("nodeConf", nodeConfList);
				}
			}
		}
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/check_system";
	}
	
	/**
	 * ???????????? ????????????
	 * @author ?????????	
	 * ???????????????
	 * @createtime 2018-07-12
	 * @updatetime 2019-05-24
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model){
		//??????????????????
		PurchaseApplyBasic bean = new PurchaseApplyBasic();
		//???????????????????????????????????????????????????
		bean.setfUserName(getUser().getName());
		bean.setfUser(getUser().getId());
		bean.setfDeptName(getUser().getDepartName());
		bean.setfDeptId(getUser().getDpID());
		bean.setfReqTime(new Date());
		User a = userMng.getUserByRoleNameAndDepartName("???????????????", getUser().getDepartName());
		bean.setProjectLeaderId(a.getId());
		bean.setProjectLeaderName(a.getName());
//		//??????????????????
//		String str="CG";
//		//????????????
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//		long date = new Date().getTime();
//		String year = format.format(date);
//		String currentYear=year.substring(0, 8);
//		//????????????
//		String departCode = getUser().getDepart().getCode();
//		//4????????????
//		List<PurchaseApplyBasic> list = cgsqMng.findAll();
//		List<Integer> lists = new ArrayList<Integer>();
//		for(PurchaseApplyBasic purchaseApplyBasic:list){
//			Integer fpCode =Integer.valueOf(purchaseApplyBasic.getBeanCode().substring(purchaseApplyBasic.getBeanCode().length()-3,purchaseApplyBasic.getBeanCode().length()));
//			lists.add(fpCode);
//		}
//		Integer codeFour = 0;
//		if(lists.isEmpty()){
//			codeFour =1;
//		}else{
//			codeFour = Collections.max(lists)+1;
//		}
//		String codeFourStr ="";
//		if(codeFour<10){
//			codeFourStr ="00"+codeFour;
//		}else{
//			if(codeFour<100){
//				codeFourStr ="0"+codeFour;
//			}else{
//				codeFourStr = String.valueOf(codeFour);
//			}
//		}
//		
//		bean.setFpCode(str+currentYear+codeFourStr);
		
		//??????????????????
		String str="CG";
		//????????????
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		//??????????????????
		List<PurchaseApplyBasic> list = cgsqMng.findByCondition(currentDate);
		int dataSize = list.size() + 1;
		String dataCode;
		if(dataSize < 10){
			dataCode ="00" + dataSize;
		}else{
			if(dataSize < 100){
				dataCode ="0" + dataSize;
			}else{
				dataCode = String.valueOf(dataSize);
			}
		}
		bean.setFpCode(str + currentDate + dataCode);
		
		
		model.addAttribute("bean", bean);
		//????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		if(roleName.contains("???????????????")&&"1".equals(bean.getfCheckStauts())){
			model.addAttribute("cgglg", 1);//???????????????????????????1   ??????????????????????????????????????????
		}else{
			model.addAttribute("cgglg", 0);//???????????????????????????0   ?????????????????????????????????????????????
		}
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "CGSQ", getUser().getDpID(), null, null, null, null, null, null);
		model.addAttribute("nodeConf", nodeConfList);
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		//??????????????????
		model.addAttribute("czlx","add");
		return "/WEB-INF/view/purchase_manage/purchase/cggl_add";
	}
	
	
	/*
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2018-07-13
	 * @updatetime 2018-07-13
	 */
	
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(PurchaseApplyBasic bean, String files,String jzyjfiles, String mingxi, String czbmyjfiles,String hyzgbmyjfiles,String reasonIds) {
		try {
			//??????
			cgsqMng.save(bean, files, jzyjfiles,mingxi, getUser(),czbmyjfiles,hyzgbmyjfiles,reasonIds);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/**
	 * ??????????????????????????????
	 * @param bean
	 * @param czbmyjfiles 
	 * @param hyzgbmyjfiles
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???16???
	 * @updator ?????????
	 * @updatetime 2020???6???16???
	 */
	@RequestMapping(value = "/executionSave")
	@ResponseBody	
	public Result executionSave(PurchaseApplyBasic bean, String czbmyjfiles,String hyzgbmyjfiles) {
		try {
			//??????
			cgsqMng.executionSave(bean, czbmyjfiles, hyzgbmyjfiles);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-07-13
	 * @updatetime 2018-07-13
	 */
	@RequestMapping(value = "/mingxi")
	@ResponseBody
	public JsonPagination mingxi(String id) {
		Pagination p = new Pagination();
			Integer pid=Integer.valueOf(id);
			//????????????????????????
			List<Object> mingxiList = cgsqMng.getMingxi("PurcMaterialList", "fpId", pid);
			for(int i=0;i<mingxiList.size();i++){
				PurcMaterialList bean = (PurcMaterialList)mingxiList.get(i);
				bean.setFsignPrice(new BigDecimal(bean.getFsignPrice()).stripTrailingZeros().toPlainString());//???????????????o
				bean.setFamount(new BigDecimal(bean.getFamount()).stripTrailingZeros().toPlainString());//???????????????o
				bean.setNum(i+1);
			}
			p.setList(mingxiList);
		return getJsonPagination(p, 0);
	}
	
	/*
	 * ???????????????????????????????????????
	 * @author ?????????
	 * @createtime 2018-08-01
	 * @updatetime 2018-08-01
	 */
	@RequestMapping(value = "/cginquiriesPage")
	@ResponseBody
	public JsonPagination loanPage(PurchaseApplyBasic bean, String sort, String order, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.qingdanpageList(bean, page, rows, getUser());;
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//????????????	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	/*
	 * ??????????????????????????????????????????????????????
	 * @author ?????????
	 * @createtime 2021-03-10
	 * @updatetime 2021-03-10
	 */
	@RequestMapping(value = "/cgsqspJson")
	@ResponseBody
	public List<ComboboxJson> cgsqspJson(String parentCode,String amount,String selected,String type){
		try {
			if(!StringUtil.isEmpty(type)){
				List<BuyInfo> list = null;
				return getComboboxJson(list,selected);
			}
			List<BuyInfo> list=cgsqMng.cgsqspJsonList(parentCode,amount);
			return getComboboxJson(list,selected);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/cgFilelist")
	public String cgFilelist(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_list";
	}
	
	
	/**
	 * ????????????????????????
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author ?????????
	 * @createtime 2021???3???15???
	 * @updator ?????????
	 * @updatetime 2021???3???15???
	 */
	@RequestMapping(value = "/cgFilePage")
	@ResponseBody
	public JsonPagination cgFilePage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.cgFilePage(bean, page, rows, getUser(),searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		//????????????	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = "/cgFileAffirmlist")
	public String cgFileAffirmlist(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_affirm_list";
	}
	
	
	/**
	 * ????????????????????????
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author ?????????
	 * @createtime 2021???3???15???
	 * @updator ?????????
	 * @updatetime 2021???3???15???
	 */
	@RequestMapping(value = "/cgFileAffirmPage")
	@ResponseBody
	public JsonPagination cgFileAffirmPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = cgsqMng.cgFileAffirmPage(bean, page, rows, getUser(),searchData);
		List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//????????????	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}	
	
	
	/**
	 * ?????????????????? 
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???3???16???
	 * @updator ?????????
	 * @updatetime 2021???3???16???
	 */
	@RequestMapping(value = "/addFileUp")
	public String addFileUp(ModelMap model,Integer fpId){
		//??????????????????
		PurchaseApplyBasic bean = cgsqMng.findById(fpId);
		
		model.addAttribute("bean", bean);
		//??????????????????
		model.addAttribute("fileUp","add");
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_add";
	}
	
	
	/**
	 * ?????????????????? 
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???3???16???
	 * @updator ?????????
	 * @updatetime 2021???3???16???
	 */
	@RequestMapping(value = "/editFileUp")
	public String editFileUp(ModelMap model,String type,Integer fpId){
		try {
			//??????????????????
			PurchaseApplyBasic bean = cgsqMng.findById(fpId);
			model.addAttribute("bean",bean);
			//??????????????????
			List<Attachment> attac = attachmentMng.list(bean);
			model.addAttribute("attac", attac);
			
			//????????????
			model.addAttribute("foCode",bean.getBeanCode());
			model.addAttribute("fpId","-100");
			//????????????????????????
			if("5".equals(bean.getFilesUploadSts())){
				List<TProcessCheck> check = tProcessCheckMng.checkHistory(-100,bean.getBeanCode());
				String returnReason = "";
				for (TProcessCheck tProcessCheck : check) {
					if("5".equals(tProcessCheck.getFcheckResult())){
						returnReason = tProcessCheck.getFcheckRemake();
					}
				}
				bean.setReturnReason(returnReason);
				model.addAttribute("returnReason","1");
			}else{
				model.addAttribute("returnReason","");
			}
			
			if("2".equals(type)){
				model.addAttribute("fileUp", "check");
				return "/WEB-INF/view/purchase_manage/purchase/cg_file_edit";
			}
			if("0".equals(type)){
				model.addAttribute("fileUp", "detail");
				return "/WEB-INF/view/purchase_manage/purchase/cg_file_edit";
			}
			model.addAttribute("fileUp", "edit");
			return "/WEB-INF/view/purchase_manage/purchase/cg_file_edit";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * ??????????????????
	 * @param model
	 * @author ?????????
	 * @createtime 2021???3???17???
	 * @updator ?????????
	 * @updatetime 2021???3???17???
	 */
	@RequestMapping(value = "/downloadFile")
	public String downloadFile(ModelMap model,Integer fpId){
		//??????????????????
		PurchaseApplyBasic bean = cgsqMng.findById(fpId);
		//??????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_download";
		
	}
	
	/**
	 * ?????????????????????
	 * @param bean
	 * @param files
	 * @author ?????????
	 * @createtime 2021???3???16???
	 * @updator ?????????
	 * @updatetime 2021???3???16???
	 */
	@RequestMapping(value = "/saveFileUp")
	@ResponseBody	
	public Result saveFileUp(TProcessCheck checkBean,String spjlFile,PurchaseApplyBasic bean, String files) {
		try {
			//??????
			cgsqMng.saveFileUp(bean, files,checkBean,spjlFile,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/**
	 * ????????????
	 * @param bean
	 * @param page
	 * @param rows
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021???3???15???
	 * @updator wanping
	 * @updatetime 2021???3???15???
	 */
	@RequestMapping(value = "/planPageData")
	@ResponseBody
	public JsonPagination pageData(Integer page, Integer rows, String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.planPageList(page, rows, getUser(), searchData);
    	List<PurchaseApplyBasic> list = (List<PurchaseApplyBasic>) p.getList();
    	for (int i = 0; i < list.size(); i++) {
    		//????????????	
    		list.get(i).setNumber((i + 1) + (page - 1) * rows);
    		//???????????????
    		User userBM = userMng.getUserByRoleNameAndDepartName("???????????????", list.get(i).getfDeptName());
    		if (userBM != null) {
    			list.get(i).setProjectLeaderName(userBM.getName());
    		}
    		//??????code??????????????????
    		PurchasePlanBasic purchasePlanBasic = purchasePlanMng.findUniqueByProperty("fPurchaseCode", list.get(i).getFpCode());
    		if (purchasePlanBasic != null) {
    			list.get(i).setfId(purchasePlanBasic.getfId());
    			list.get(i).setApplyUser(purchasePlanBasic.getfUserName());
    			list.get(i).setApplyTime(purchasePlanBasic.getfReqTime());
    			list.get(i).setApprovalStatus(purchasePlanBasic.getFlowStauts());
    		}
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2021-03-16
	 * @updatetime 2021-03-16
	 */
	@RequestMapping(value = "/choose")
	public String choose(ModelMap model, String menuType) {
		
		model.addAttribute("menuType", menuType);
		return "/WEB-INF/view/purchase_manage/purchase/cggl_choose";
	}
	
	/**
	 * ????????????
	 * @param bean
	 * @param page
	 * @param rows
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021???3???10???
	 * @updator wanping
	 * @updatetime 2021???3???10???
	 */
	@RequestMapping(value = "/pageData")
	@ResponseBody
	public JsonPagination pageData(PurchaseIntentionBasic bean, Integer page, Integer rows, String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.pubPageList(bean, page, rows, getUser(), searchData);
    	List<PurchaseIntentionBasic> list = (List<PurchaseIntentionBasic>) p.getList();
    	for (int i = 0; i < list.size(); i++) {
    		//????????????	
    		list.get(i).setNum((i + 1) + (page - 1) * rows);	
		}
    	return getJsonPagination(p, page);
	}
	
}