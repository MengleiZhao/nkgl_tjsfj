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
 * 采购申请审批的控制层
 * 本模块用于采购申请的审批及查看
 * @author 冉德茂
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
	 * 跳转到列表页面
	 * @author 冉德茂
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
	 * 跳转到采购选择配置计划的list页面
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@RequestMapping(value = "/checkedlist")
	public String checkedlist( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/conf_plan_checked_list";
	}
	
	/**
	 * 跳转采购执行方式确认
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月15日
	 * @updator 陈睿超
	 * @updatetime 2020年6月15日
	 */
	@RequestMapping(value = "/executionlist")
	public String executionlist( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cgsq_execution_list";
	}
	
	/*
	 * 跳转到指标选择页面
	 * @author 冉德茂
	 * @createtime 2018-08-22
	 * @updatetime 2018-08-22
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cg_checkIndex";
		//return "/WEB-INF/view/choiceIndex";
	}
	
	
	/*
	 * 分页数据获得
	 * @author 冉德茂
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
    		//序号设置	
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
			//序号设置	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}	
	
	/**
	 * 合同选取采购单时使用
	 */
	@RequestMapping(value = "/cgsqPageChoose")
	@ResponseBody
	public JsonPagination cgsqPageChoose(PurchaseApplyBasic bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.pageList(bean, page, rows, null,null);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//设置合同id
    		List<ContractBasicInfo> contractList=formulationMng.findByProperty("fPurchNo", li.get(x).getBeanId().toString());
    		if(contractList!=null && contractList.size()>0){
    			ContractBasicInfo contract=contractList.get(0);
            	li.get(x).setContractId(contract.getBeanId());
    		}
    		//序号设置	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	
	
	
	/*
	 * 采购申请删除
	 * @author 冉德茂
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id,String fId,ModelMap model) {
		try {
			//传回来的id是主键
			cgsqMng.delete(id,fId,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	
	/**
	 * 采购申请撤回
	 * @author 焦广兴
	 * @createtime 2019-10-08
	 * @updatetime 2019-10-08
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			cgsqMng.reCall(id);
			return getJsonResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}

	}
	/*
	 * 根据采购批次id获取合同金额
	 * @author 李安达
	 * @createtime 2018-11-29
	 * @updatetime 2018-11-29
	 * /cgsqsp/getFpAmount?fpId=采购id
	 */
	@RequestMapping(value="getFpAmount")
	@ResponseBody
	String findFpAmountbyfpCode(String fpId){
		return cgsqMng.findFpAmountbyfpId(fpId);
	}
	
	/*
	 * 采购申请 修改
	 * @author 冉德茂
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, Integer id){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		//查询采购类型id
		List<BuyInfo> list1 = cgsqMng.findByText(bean.getFpMethod());
		if(list1.size()>0){
			model.addAttribute("cgType", list1.get(0).getId());
		}else{
			model.addAttribute("cgType", "");
		}
		//查询采购方式id
		List<BuyInfo> list2 = cgsqMng.findByText(bean.getFpPype());
		if(list2.size()>0){
			for(int i = 0;i<list2.size();i++){
				if("政府采购".equals(bean.getFpMethod())){
					if(list2.get(i).getParentId() == 1){
						model.addAttribute("cgWay", list2.get(i).getId());
					}
				}
				if("委托代理机构采购".equals(bean.getFpMethod())){
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
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		//查询四级指标信息
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());			
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);
			//资金渠道
			if("0".equals(index.getIndexType())){
				bean.setPfIndexType("基本预算");		
			}else{
				bean.setPfIndexType("项目预算");		
			}
		}
		//只有采购管理岗才可以填写采购方式
		String roleName=getUser().getRoleName();
		if(roleName.contains("采购管理岗")&&"1".equals(bean.getfCheckStauts())){
			model.addAttribute("cgglg", 1);//如果是采购管理岗为1   可以编辑页面上的采购方式确认
		}else{
			model.addAttribute("cgglg", 0);//如果是采购管理岗为0   不可以编辑页面上的采购方式确认
		}
		if("1".equals(bean.getfIsPers())){
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(),"SMCGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("SMCGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("SMCGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode", bean.getBeanCode());
		}else{
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(),"CGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("CGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode", bean.getBeanCode());
		}
		//当前操作类型
		model.addAttribute("czlx","edit");
		return "/WEB-INF/view/purchase_manage/purchase/cggl_add";
	}
	
	/**
	 * 主管部门意见上传
	 * @param model
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月16日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月16日
	 */
	@RequestMapping(value = "/executionEdit")
	public String executionEdit(ModelMap model, Integer id, String openType){
		//是否是合同跳转过来打开的
		model.addAttribute("openType", openType);
		
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		
		PurchaseApplyBasic beanCopy = new PurchaseApplyBasic();
		BeanUtils.copyProperties(bean,beanCopy);
		
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		//查询四级指标信息
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());			
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);			
		}
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		String times ="";
		if(!"".equals(bean.getfReqTime())){
			DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
			times =fmt.format(bean.getfReqTime());     // 转换成 X年X月X日
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
		Collections.reverse(datas); //倒序排列
		String a0 = "<p style=\"text-align:center;font-family:黑体, monospace;font-size:20.0pt;border: #ffffff;\">"+bean.getProSignName()+"</p><br/>";
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
					DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
					time =fmt.format(datas.get(j).getCheckInfo().getFcheckTime());     // 转换成 X年X月X日
				}
				a0 +=time;
				a0 +=a7;
			}
		}
		a0 +=i;
		beanCopy.setProSignContent(a0);
		model.addAttribute("beanCopy", beanCopy);
		
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
		model.addAttribute("proposer", proposer);
		
		//历史审批节点
		String historyNodes = tProcessCheckMng.getHistoryNodes("CGSQ", bean.getfDeptId(), bean.getBeanCode());
		model.addAttribute("historyNodes", historyNodes);
		
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/purchase/cggl_execution";
	}
	
	/*
	 * 采购申请 查看详情
	 * @author 冉德茂
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	@RequestMapping(value ="/detail")
	public String detail(ModelMap model, Integer id, String openType){
		//是否是合同跳转过来打开的
		model.addAttribute("openType", openType);
		
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		//查询采购方式id
		List<BuyInfo> list = cgsqMng.findByText(bean.getFpPype());
		for(int i = 0;i<list.size();i++){
			if("政府采购".equals(bean.getFpMethod())){
				if(list.get(i).getParentId() == 1){
					model.addAttribute("cgType", list.get(i).getId());
				}
			}
			if("委托代理机构采购".equals(bean.getFpMethod())){
				if(list.get(i).getParentId() == 2){
					model.addAttribute("cgType", list.get(i).getId());
				}
			}else{
				model.addAttribute("cgType", list.get(0).getId());
			}
		}
		PurchaseApplyBasic beanCopy = new PurchaseApplyBasic();
		BeanUtils.copyProperties(bean,beanCopy);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		//查询四级指标信息
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());	
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);	
			//资金渠道
			if("0".equals(index.getIndexType())){
				bean.setPfIndexType("基本预算");		
			}else{
				bean.setPfIndexType("项目预算");		
			}
		}

		if("1".equals(bean.getfIsPers())){
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "SMCGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			String times ="";
			if(!"".equals(bean.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
				times =fmt.format(bean.getfReqTime());     // 转换成 X年X月X日
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
			Collections.reverse(datas); //倒序排列
			String a0 = "<p style=\"text-align:center;font-family:黑体, monospace;font-size:20.0pt;border: #ffffff;\">"+bean.getProSignName()+"</p><br/>";
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
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						time =fmt.format(datas.get(j).getCheckInfo().getFcheckTime());     // 转换成 X年X月X日
					}
					a0 +=time;
					a0 +=a7;
				}
			}
			a0 +=i;
			beanCopy.setProSignContent(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("SMCGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("SMCGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
		}else{
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			String times ="";
			if(!"".equals(bean.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
				times =fmt.format(bean.getfReqTime());     // 转换成 X年X月X日
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
			Collections.reverse(datas); //倒序排列
			String a0 = "<p style=\"text-align:center;font-family:黑体, monospace;font-size:20.0pt;border: #ffffff;\">"+bean.getProSignName()+"</p><br/>";
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
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						time =fmt.format(datas.get(j).getCheckInfo().getFcheckTime());     // 转换成 X年X月X日
					}
					a0 +=time;
					a0 +=a7;
				}
			}
			a0 +=i;
			beanCopy.setProSignContent(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("CGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
		}
		return "/WEB-INF/view/purchase_manage/purchase/cggl_detail";
	}
	
	/**
	 * 采购申请 重新请求审批流
	 * @author 焦广兴
	 * @createtime 2019-05-24
	 * @updatetime 2019-05-24
	 */
	@RequestMapping(value = "/refreshProcess")
	public String rerefreshProcess(ModelMap model, String workFlow){
		if("1".equals(workFlow)){
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "SMCGSQ", getUser().getDpID(), null, null, null, null, null, null);
			model.addAttribute("nodeConf", nodeConfList);
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
			model.addAttribute("proposer", proposer);

		}else{
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "CGSQ", getUser().getDpID(), null, null, null, null, null, null);
			model.addAttribute("nodeConf", nodeConfList);
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
			model.addAttribute("proposer", proposer);
		}
		return "/WEB-INF/view/check_system";
	}
	/**
	 * 采购台账 重新请求审批流
	 * @author 赵孟雷
	 * @createtime 2020-03-26
	 * @updatetime 2019-03-26
	 */
	@RequestMapping(value = "/refreshProcessLedger")
	public String refreshProcessLedger(ModelMap model, String fpPype,String str,String fpId){
		//业务范围
		String busiArea = null;
		//查询工作流
		List<TNodeData> nodeConfList =null;
		if("0".equals(str)){
			if ("A10".equals(fpPype)) {
				busiArea = "HWCGSQ";
			} else if ("A20".equals(fpPype)) {
				busiArea = "GCCGSQ";
			} else if ("A30".equals(fpPype)) {
				busiArea = "HWCGSQ";
			}
			//查询基本信息
			PurchaseApplyBasic bean = cgsqMng.findById(Integer.valueOf(fpId));
			//查询工作流
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
				//查询工作流
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
					//查询工作流
					nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), busiArea, bean.getfDepartId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFacpCode(), "1");
					model.addAttribute("nodeConf", nodeConfList);
				}
			}
		}
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/check_system";
	}
	
	/**
	 * 采购申请 添加按钮
	 * @author 冉德茂	
	 * 焦广兴修改
	 * @createtime 2018-07-12
	 * @updatetime 2019-05-24
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model){
		//查询基本信息
		PurchaseApplyBasic bean = new PurchaseApplyBasic();
		//获取当前登录对象获得名称和所属部门
		bean.setfUserName(getUser().getName());
		bean.setfUser(getUser().getId());
		bean.setfDeptName(getUser().getDepartName());
		bean.setfDeptId(getUser().getDpID());
		bean.setfReqTime(new Date());
		User a = userMng.getUserByRoleNameAndDepartName("处室负责人", getUser().getDepartName());
		bean.setProjectLeaderId(a.getId());
		bean.setProjectLeaderName(a.getName());
//		//自动生成编号
//		String str="CG";
//		//当前年份
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//		long date = new Date().getTime();
//		String year = format.format(date);
//		String currentYear=year.substring(0, 8);
//		//部门编号
//		String departCode = getUser().getDepart().getCode();
//		//4位顺序号
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
		
		//自动生成编号
		String str="CG";
		//当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		//查询当天数据
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
		//只有采购管理岗才可以填写采购方式
		String roleName=getUser().getRoleName();
		if(roleName.contains("采购管理岗")&&"1".equals(bean.getfCheckStauts())){
			model.addAttribute("cgglg", 1);//如果是采购管理岗为1   可以编辑页面上的采购方式确认
		}else{
			model.addAttribute("cgglg", 0);//如果是采购管理岗为0   不可以编辑页面上的采购方式确认
		}
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "CGSQ", getUser().getDpID(), null, null, null, null, null, null);
		model.addAttribute("nodeConf", nodeConfList);
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		//当前操作类型
		model.addAttribute("czlx","add");
		return "/WEB-INF/view/purchase_manage/purchase/cggl_add";
	}
	
	
	/*
	 * 采购申请的保存
	 * @author 冉德茂
	 * @createtime 2018-07-13
	 * @updatetime 2018-07-13
	 */
	
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(PurchaseApplyBasic bean, String files,String jzyjfiles, String mingxi, String czbmyjfiles,String hyzgbmyjfiles,String reasonIds) {
		try {
			//保存
			cgsqMng.save(bean, files, jzyjfiles,mingxi, getUser(),czbmyjfiles,hyzgbmyjfiles,reasonIds);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 保存执行方式确认数据
	 * @param bean
	 * @param czbmyjfiles 
	 * @param hyzgbmyjfiles
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月16日
	 * @updator 陈睿超
	 * @updatetime 2020年6月16日
	 */
	@RequestMapping(value = "/executionSave")
	@ResponseBody	
	public Result executionSave(PurchaseApplyBasic bean, String czbmyjfiles,String hyzgbmyjfiles) {
		try {
			//保存
			cgsqMng.executionSave(bean, czbmyjfiles, hyzgbmyjfiles);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 查询采购品目明细
	 * @author 冉德茂
	 * @createtime 2018-07-13
	 * @updatetime 2018-07-13
	 */
	@RequestMapping(value = "/mingxi")
	@ResponseBody
	public JsonPagination mingxi(String id) {
		Pagination p = new Pagination();
			Integer pid=Integer.valueOf(id);
			//查询采购清单信息
			List<Object> mingxiList = cgsqMng.getMingxi("PurcMaterialList", "fpId", pid);
			for(int i=0;i<mingxiList.size();i++){
				PurcMaterialList bean = (PurcMaterialList)mingxiList.get(i);
				bean.setFsignPrice(new BigDecimal(bean.getFsignPrice()).stripTrailingZeros().toPlainString());//去掉多余的o
				bean.setFamount(new BigDecimal(bean.getFamount()).stripTrailingZeros().toPlainString());//去掉多余的o
				bean.setNum(i+1);
			}
			p.setList(mingxiList);
		return getJsonPagination(p, 0);
	}
	
	/*
	 * 询比价登记查看采购基本信息
	 * @author 冉德茂
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
			//序号设置	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	/*
	 * 采购类型，采购方式，采购理由三级联查
	 * @author 方淳洲
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
	 * 采购文件上传
	 */
	@RequestMapping(value = "/cgFilelist")
	public String cgFilelist(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_list";
	}
	
	
	/**
	 * 分页采购文件查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	@RequestMapping(value = "/cgFilePage")
	@ResponseBody
	public JsonPagination cgFilePage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.cgFilePage(bean, page, rows, getUser(),searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		//序号设置	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}	
	/**
	 * 采购文件上传确认
	 */
	@RequestMapping(value = "/cgFileAffirmlist")
	public String cgFileAffirmlist(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_affirm_list";
	}
	
	
	/**
	 * 分页采购文件确认
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	@RequestMapping(value = "/cgFileAffirmPage")
	@ResponseBody
	public JsonPagination cgFileAffirmPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = cgsqMng.cgFileAffirmPage(bean, page, rows, getUser(),searchData);
		List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}	
	
	
	/**
	 * 采购文件上传 
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/addFileUp")
	public String addFileUp(ModelMap model,Integer fpId){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(fpId);
		
		model.addAttribute("bean", bean);
		//当前操作类型
		model.addAttribute("fileUp","add");
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_add";
	}
	
	
	/**
	 * 采购文件上柴 
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/editFileUp")
	public String editFileUp(ModelMap model,String type,Integer fpId){
		try {
			//查询基本信息
			PurchaseApplyBasic bean = cgsqMng.findById(fpId);
			model.addAttribute("bean",bean);
			//查询附件信息
			List<Attachment> attac = attachmentMng.list(bean);
			model.addAttribute("attac", attac);
			
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
			model.addAttribute("fpId","-100");
			//是否显示退回信息
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
	 * 采购文件下载
	 * @param model
	 * @author 赵孟雷
	 * @createtime 2021年3月17日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/downloadFile")
	public String downloadFile(ModelMap model,Integer fpId){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(fpId);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		return "/WEB-INF/view/purchase_manage/purchase/cg_file_download";
		
	}
	
	/**
	 * 采购文件的保存
	 * @param bean
	 * @param files
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/saveFileUp")
	@ResponseBody	
	public Result saveFileUp(TProcessCheck checkBean,String spjlFile,PurchaseApplyBasic bean, String files) {
		try {
			//保存
			cgsqMng.saveFileUp(bean, files,checkBean,spjlFile,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 分页查询
	 * @param bean
	 * @param page
	 * @param rows
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月15日
	 * @updator wanping
	 * @updatetime 2021年3月15日
	 */
	@RequestMapping(value = "/planPageData")
	@ResponseBody
	public JsonPagination pageData(Integer page, Integer rows, String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.planPageList(page, rows, getUser(), searchData);
    	List<PurchaseApplyBasic> list = (List<PurchaseApplyBasic>) p.getList();
    	for (int i = 0; i < list.size(); i++) {
    		//序号设置	
    		list.get(i).setNumber((i + 1) + (page - 1) * rows);
    		//项目负责人
    		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", list.get(i).getfDeptName());
    		if (userBM != null) {
    			list.get(i).setProjectLeaderName(userBM.getName());
    		}
    		//根据code获取采购计划
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
	 * 意向公开选择单
	 * @author 方淳洲
	 * @createtime 2021-03-16
	 * @updatetime 2021-03-16
	 */
	@RequestMapping(value = "/choose")
	public String choose(ModelMap model, String menuType) {
		
		model.addAttribute("menuType", menuType);
		return "/WEB-INF/view/purchase_manage/purchase/cggl_choose";
	}
	
	/**
	 * 分页查询
	 * @param bean
	 * @param page
	 * @param rows
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月10日
	 * @updator wanping
	 * @updatetime 2021年3月10日
	 */
	@RequestMapping(value = "/pageData")
	@ResponseBody
	public JsonPagination pageData(PurchaseIntentionBasic bean, Integer page, Integer rows, String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.pubPageList(bean, page, rows, getUser(), searchData);
    	List<PurchaseIntentionBasic> list = (List<PurchaseIntentionBasic>) p.getList();
    	for (int i = 0; i < list.size(); i++) {
    		//序号设置	
    		list.get(i).setNum((i + 1) + (page - 1) * rows);	
		}
    	return getJsonPagination(p, page);
	}
	
}