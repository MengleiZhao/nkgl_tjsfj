package com.braker.icontrol.contract.enforcing.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.approval.manager.CheckInfoMng;
import com.braker.icontrol.contract.enforcing.manager.ChangeMng;
import com.braker.icontrol.contract.enforcing.manager.UptClauseMng;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.enforcing.model.UptClause;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/Change")
public class ChangeController extends BaseController{

	@Autowired
	private ChangeMng changeMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private UptClauseMng uptClauseMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private ReceivPlanMng receivPlanMng;
	@Autowired
	private ContractRegisterListMng contractRegisterListMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private CgReceiveMng cgReceiveMng;
	@Autowired
	private CheckInfoMng checkInfoMng;
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	
	/**
	 * ???????????????
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-22
	 */
	@RequestMapping("/List")
	public String list(ModelMap model ){
		
		return "/WEB-INF/view/contract/change/change_list";
	}
	
	/**
	 * ?????????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-22
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination JsonPagination(Upt upt,String sort,String order,Integer page,Integer rows,String searchData,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = uptMng.List(upt, getUser(),page,rows,searchData);
		List<Upt> li= (List<Upt>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);	
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param model
	 * @return	 
	 * @createTime 2019-05-28
	 * @author ?????????
	 */
	@RequestMapping("/contract")
	public String endingContract(ModelMap model){
		return "/WEB-INF/view/contract/change/change_add_contract";
	}
	
	/**
	 * ????????????????????????????????????
	 * @param ContractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param mode
	 * @return	 
	 * @createTime 2019-05-28
	 * @author ?????????
	 */
	@RequestMapping("/contractList")
	@ResponseBody
	public JsonPagination contractList(ContractBasicInfo ContractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap mode){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = formulationMng.queryForChange(ContractBasicInfo,getUser(), page, rows);
		List<ContractBasicInfo> cbi = (List<ContractBasicInfo>) p.getList();
		for (int i = 0; i < cbi.size(); i++) {
			cbi.get(i).setNumber(i+1);
		}
		p.setList(cbi);
		return getJsonPagination(p, page);
	}
	
	
	
	/**
	 * ??????????????????
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/add/{id}")
	public String add(@PathVariable String id, ModelMap model){
		model.addAttribute("uptOpenType", "Cadd");
		
		ContractBasicInfo contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
		int num = cgReceiveMng.findFacpIdByFcId(contractBasicInfo.getFcId());
		contractBasicInfo.setAccNumber(num);
		model.addAttribute("bean",contractBasicInfo);
		
		//??????????????????
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(contractBasicInfo);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//??????????????????????????????
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		// ?????????????????????
		List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
		model.addAttribute("formulationAttaList", attaList);
		User user=getUser();
		//??????????????????
		Upt upt =new Upt();
		upt.setfOperatorID(user.getId());
		upt.setfOperator(user.getName());
		upt.setfDeptID(user.getDepart().getId());
		upt.setfDeptName(user.getDepart().getName());
		upt.setfReqTime(null);
		//????????????????????????
		String fContCode = uptMng.getFContCode();
		upt.setfContCode(fContCode);
		upt.setfContractor(contractBasicInfo.getfContractor());
		upt.setFcAmount(contractBasicInfo.getFcAmount());
		upt.setFcAmountMax(contractBasicInfo.getFcAmountMax());
		upt.setfMarginAmount(contractBasicInfo.getfMarginAmount());
		upt.setfContStartTime(contractBasicInfo.getfContStartTime());
		upt.setfContEndTime(contractBasicInfo.getfContEndTime());
		model.addAttribute("Upt", upt);
		//??????????????????
		List<Attachment> uptAttaList = attachmentMng.list(upt);
		model.addAttribute("changeAttaList", uptAttaList);
		String dept[]=departMng.findDeptByUserId(upt.getfOperatorID());
		//???????????????
		List<TNodeData> nodeConfList =tProcessCheckMng.getNodeConf(getUser().getId(),"HTBG", upt.getfDeptID(),upt.getBeanCode(),upt.getfNCode(), null, null, null, "1");
		//?????????????????????????????????
		Proposer proposer = new Proposer(getUser().getName(),getUser().getDepartName(), upt.getfReqTime());
		model.addAttribute("nodeConf", nodeConfList);
		model.addAttribute("proposer", proposer);
		
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", dept[0]);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",upt.getBeanCode());
		/*
		 * ???????????????????????????????????????
		 */
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefinOnchange = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpIdOnchange", tProcessDefinOnchange.getFPId());
		//????????????
		model.addAttribute("foCodeOnchange", contractBasicInfo.getBeanCode());
		return "/WEB-INF/view/contract/change/change_edit";
	}
	
	
	/**
	 * ?????????????????????
	 * @author crc
	 * @createtime 2018-06-22
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, ModelMap model){
		//??????????????????
		Upt upt =uptMng.findById(Integer.valueOf(id));
		model.addAttribute("Upt", upt);
		ContractBasicInfo contractBasicInfo = formulationMng.findById(upt.getfContId_U());
		int num = cgReceiveMng.findFacpIdByFcId(contractBasicInfo.getFcId());
		contractBasicInfo.setAccNumber(num);
		model.addAttribute("bean",contractBasicInfo);
		
		//??????????????????
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(contractBasicInfo);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//??????????????????????????????
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		// ?????????????????????
		List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
		model.addAttribute("formulationAttaList", attaList);
		User user=getUser();
		//?????????????????????????????????
		if("1".equals(upt.getfUptFlowStauts())||"9".equals(upt.getfUptFlowStauts())){
			model.addAttribute("uptOpenType", "Cdetail");
		}else {
			model.addAttribute("uptOpenType", "Cedit");
		}
		//??????????????????
		List<Attachment> uptAttaList = attachmentMng.list(upt);
		model.addAttribute("changeAttaList", uptAttaList);
		String dept[]=departMng.findDeptByUserId(upt.getfOperatorID());
		//???????????????
		List<TNodeData> nodeConfList =tProcessCheckMng.getNodeConf(upt.getUserId(),"HTBG", upt.getfDeptID(),upt.getBeanCode(),upt.getfNCode(),upt.getJoinTable(), upt.getBeanCodeField(), upt.getfContCode(), "1");
		//?????????????????????????????????
		Proposer proposer = new Proposer(upt.getfOperator(),dept[1], upt.getfReqTime());
		model.addAttribute("nodeConf", nodeConfList);
		model.addAttribute("proposer", proposer);
		
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", dept[0]);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",upt.getBeanCode());
		
		/*
		 * ???????????????????????????????????????
		 */
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefinOnchange = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpIdOnchange", tProcessDefinOnchange.getFPId());
		//????????????
		model.addAttribute("foCodeOnchange", contractBasicInfo.getBeanCode());
		
		//??????????????????
		ReceivPlan receivPlan = new ReceivPlan();
		receivPlan.setfUptId_R(Integer.valueOf(id));
		receivPlan.setDataType(1);
		List<ReceivPlan> rp = filingMng.getReceivPlan(receivPlan);
		model.addAttribute("rpSize",rp.size());
		//??????????????????
		ContractRegisterList contractRegisterList = new ContractRegisterList();
		contractRegisterList.setfId_U(Integer.valueOf(id));
		contractRegisterList.setfDataType(1);
		List<ContractRegisterList> cr = contractRegisterListMng.getContractRegisterList(contractRegisterList);
		model.addAttribute("crSize",cr.size());
		
		//??????????????????????????????
		List<ReimbAppliBasicInfo> reimbList = reimbAppliMng.findByProperty("payId", String.valueOf(upt.getfContId_U()));
		Set<String> set = new HashSet<String>();
		double init = 0;
		if (reimbList != null && !reimbList.isEmpty()) {
			BigDecimal initB = new BigDecimal(Double.toString(init));
			for (ReimbAppliBasicInfo reimbAppliBasicInfo : reimbList) {
				if (!"-1".equals(reimbAppliBasicInfo.getFlowStauts()) && !"0".equals(reimbAppliBasicInfo.getFlowStauts())
						&& !"-4".equals(reimbAppliBasicInfo.getFlowStauts()) && !"9".equals(reimbAppliBasicInfo.getFlowStauts()) && "8".equals(reimbAppliBasicInfo.getType())) {
					if (!StringUtil.isEmpty(reimbAppliBasicInfo.getReceivplanid())) {
						String[] str = reimbAppliBasicInfo.getReceivplanid().split(",");
						for (int i = 0; i < str.length; i++) {
							set.add(str[i]);
						}
					}
					//??????????????????
					if (reimbAppliBasicInfo.getAmount() != null) {
						BigDecimal param = new BigDecimal(Double.toString(reimbAppliBasicInfo.getAmount()));
						initB = initB.add(param);
					}
				}
			}
			init = Double.parseDouble(initB.toString());
		}
		model.addAttribute("totalAmount", init);
		model.addAttribute("receivPlanIndex", set);
		
		if (set.size() > 0) {
			model.addAttribute("reimbFlag", true);
		} else {
			model.addAttribute("reimbFlag", false);
		}
		
		return "/WEB-INF/view/contract/change/change_edit";
	}
	
	/**
	 * ?????????????????????
	 * @param contractBasicInfo
	 * @param uptAttac
	 * @param upt
	 * @return
	 * @author crc
	 * @createtime 2018-06-22
	 */
	@RequestMapping("/Save")
	@ResponseBody
	public Result save(ContractBasicInfo contractBasicInfo,String uptplanJson,String planJson,
			UptClause uptAttac,Upt upt,String htbgfiles, String cgConfPlanJson){
		try {
			if(StringUtil.isEmpty(upt.getfUptReason())){
				return getJsonResult(false, "????????????????????????");
			}else if(StringUtil.isEmpty(upt.getfContName())){
				return getJsonResult(false, "??????????????????????????????");
			}else{
				//????????????????????????????????????
				if(StringUtil.isEmpty(String.valueOf(upt.getfId_U()))){
					int fContCodeSize = uptMng.countByProperty("fContCode", upt.getfContCode());
					if (fContCodeSize > 0) {
						return getJsonResult(false, "????????????????????????????????????????????????????????????????????????");
					}
				}
				//??????json??????
				List<UptClause> uptClauseList = new ArrayList<UptClause>();
				if(!StringUtil.isEmpty(planJson)){
					uptClauseList = JSON.parseObject(planJson.toString(),new TypeReference<List<UptClause>>(){});
				}
				//List<UptClause> uptClauseList = JSONArray.toList(JSONArray.fromObject(planJson),UptClause.class);
				upt.setfContId_U(contractBasicInfo.getFcId());
				String result = uptMng.saveUptAndUptAttac(contractBasicInfo, upt, getUser(),htbgfiles,uptClauseList,uptplanJson, cgConfPlanJson);
				if ("?????????????????????????????????????????????".equals(result) || result.indexOf("????????????") != -1) {
					return getJsonResult(false, result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
		return getJsonResult(true, "???????????????");
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/newOrDetail/{id}")
	public Result newOrDetail(@PathVariable String id ,ModelMap model){
		ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(id));
		List<Upt> uptList = uptMng.findByFContId_U(String.valueOf(cbi.getFcId()));
		if(uptList!=null&&uptList.size()>0){
			//?????????
			return getJsonResult(true, "have");
		}else {
			//?????????
			return getJsonResult(true, "nohave");
		}
		
	}
	
	/**
	 * ??????id??????
	 * @param id
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-22
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable String id,ModelMap model,HttpServletRequest request, String contractUpdateStatus){
		if(!StringUtil.isEmpty(contractUpdateStatus)) {
			model.addAttribute("contractUpdateStatus", contractUpdateStatus);//??????????????????
		}
		
		//??????????????????
		Upt upt =uptMng.findById(Integer.valueOf(id));
		model.addAttribute("Upt", upt);
		ContractBasicInfo contractBasicInfo = formulationMng.findById(upt.getfContId_U());
		contractBasicInfo=formulationMng.findById(Integer.valueOf(upt.getfContId_U()));
		model.addAttribute("bean", contractBasicInfo);
		model.addAttribute("uptOpenType", "Cdetail");
		model.addAttribute("checkType", "detail");
		//??????????????????
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(contractBasicInfo);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//??????????????????????????????
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		// ?????????????????????
		List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
		model.addAttribute("formulationAttaList", attaList);
		//??????????????????
		List<Attachment> uptAttaList = attachmentMng.list(upt);
		model.addAttribute("changeAttaList", uptAttaList);
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(upt.getUserId(),"HTBG", upt.getfDeptID(),upt.getBeanCode(),upt.getfNCode(), upt.getJoinTable(), upt.getBeanCodeField(), upt.getfContCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		String dept[]=departMng.findDeptByUserId(upt.getfOperatorID());
		Proposer proposer = new Proposer(upt.getfOperator(),dept[1], upt.getfReqTime());
		model.addAttribute("proposer", proposer);
		//?????????????????????????????????id
		String departId=departMng.findDeptByUserId(upt.getfOperatorID())[0];
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//????????????????????????id
		TProcessDefin tProcessDefinOnchange = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpIdOnchange", tProcessDefinOnchange.getFPId());
		//????????????
		model.addAttribute("foCode",upt.getBeanCode());
		
		//???????????????????????????
		model.addAttribute("foCodeOnchange", contractBasicInfo.getBeanCode());
		
		//????????????
		Set<String> fPlanIds = new HashSet<String>();
		//??????????????????
		ReceivPlan receivPlan = new ReceivPlan();
		receivPlan.setfUptId_R(Integer.valueOf(id));
		receivPlan.setDataType(1);
		List<ReceivPlan> rp = filingMng.getReceivPlan(receivPlan);
		for (int i = 0; i < rp.size(); i++) {
			fPlanIds.add(rp.get(i).getfPlanId().toString());
		}
		model.addAttribute("receivPlanIndex", fPlanIds);
		return "/WEB-INF/view/contract/change/change_detail";
		
	}

	/**
	 * ??????????????????????????????
	 * @param id
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2019-05-24
	 */
	@ResponseBody
	@RequestMapping("/clauseList")
	public JsonPagination clauseList(String id ,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		Pagination p = new Pagination();
		List<UptClause> li= uptClauseMng.findByfId_U_AU(Integer.valueOf(id));
    	p.setList(li);
    	p.setPageSize(li.size());
    	p.setPageNo(1);
    	p.setTotalCount(li.size());
		return getJsonPagination(p, page);	
	}

	/**
	 * ?????????????????????????????????
	 * @param models
	 * @return
	 * @author ?????????
	 * @createtime 2019-05-24
	 */
	@RequestMapping("/approvalList")
	public String approvalList(ModelMap models){
		return "/WEB-INF/view/contract/change/approval/change_approval_list";
	}
	
	/**
	 * ????????????list????????????
	 * @param upt
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2019-05-24
	 */
	@ResponseBody
	@RequestMapping("/approvalJson")
	public JsonPagination approvalJson(Upt upt,String sort,String order,Integer page,Integer rows,String searchData,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = uptMng.queryList(upt, getUser(), page, rows,searchData);
		List<Upt> li= (List<Upt>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);	
	}
	
	/**
	 * ????????????????????????????????????
	 * @param id ??????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2019-05-27
	 */
	@RequestMapping("/approvalChange/{id}")
	public String approvalChange(@PathVariable String id,ModelMap model){
		model.addAttribute("uptOpenType", "Cdetail");
		//??????????????????
		Upt upt = uptMng.findById(Integer.valueOf(id));
		model.addAttribute("Upt", upt);
		//????????????
		ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(upt.getfContId_U()));
		model.addAttribute("bean",cbi);
		//????????????????????????????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		String deptName=getUser().getDepart().getName();
		if(roleName.contains("??????????????????????????????") && "?????????".equals(deptName)){
			model.addAttribute("djhCode", 1);
		}else{
			model.addAttribute("djhCode", 0);
		}
		//??????????????????
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(cbi);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//??????????????????????????????
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		// ?????????????????????
		List<Attachment> attaList = attachmentMng.list(cbi);
		model.addAttribute("formulationAttaList", attaList);
		
		//??????????????????
		List<Attachment> uptAttaList = attachmentMng.list(upt);
		model.addAttribute("changeAttaList", uptAttaList);
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(upt.getUserId(),"HTBG", upt.getfDeptID(),upt.getBeanCode(),upt.getfNCode(), "T_CONTRACT_UPT", "F_CONT_CODE", upt.getfContCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		String dept[]=departMng.findDeptByUserId(upt.getfOperatorID());
		Proposer proposer = new Proposer(upt.getfOperator(),dept[1], upt.getfReqTime());
		model.addAttribute("proposer", proposer);
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", dept[0]);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",upt.getBeanCode());
		
		/*
		 * ???????????????????????????????????????
		 */
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(cbi.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefinOnchange = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpIdOnchange", tProcessDefinOnchange.getFPId());
		//????????????
		model.addAttribute("foCodeOnchange", cbi.getBeanCode());
		
		//????????????
		Set<String> fPlanIds = new HashSet<String>();
		//??????????????????
		ReceivPlan receivPlan = new ReceivPlan();
		receivPlan.setfUptId_R(Integer.valueOf(id));
		receivPlan.setDataType(1);
		List<ReceivPlan> rp = filingMng.getReceivPlan(receivPlan);
		for (int i = 0; i < rp.size(); i++) {
			fPlanIds.add(rp.get(i).getfPlanId().toString());
		}
		model.addAttribute("receivPlanIndex", fPlanIds);
		return "/WEB-INF/view/contract/change/approval/change_approval_edit";
	}
	
	/**
	 * ??????????????????
	 * @param status ????????????
	 * @param upt 
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2019-05-27
	 */
	@ResponseBody
	@RequestMapping("/approve/{status}")
	public Result approve(@PathVariable String status,String fId,String spjlFile,TProcessCheck checkBean,String htbgjyfiles,String fDZHCode){
		String result;
		try {
			result = uptMng.updateStatus(fId, status, getUser(), checkBean, spjlFile,htbgjyfiles,fDZHCode);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, result);
	}
	
	/**
	 * ??????
	 * @param id ??????id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2019-10-08
	 * @updatetime 2018-10-08
	 */
	@ResponseBody
	@RequestMapping("/reCall")
	public Result reCall(String id,ModelMap model){
		try {
			String str = uptMng.reCall(id);
			return getJsonResult(true,str);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"?????????????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @param upt
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???2???14???
	 */
	@ResponseBody
	@RequestMapping("/uptPlanJson")
	public JsonPagination uptPlanJson(Upt upt,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	List<ReceivPlan> li = receivPlanMng.findbyUptId(upt.getfId_U());
    	Pagination p = new Pagination();
    	p.setList(li);
    	p.setPageNo(1);
    	p.setPageSize(li.size());
    	p.setTotalCount(li.size());
		return getJsonPagination(p, page);	
	}
	
	/**
	 * ??????????????????
	 * @param contractRegisterList
	 * @return
	 * @author wanping
	 * @createtime 2020???7???8???
	 * @updator wanping
	 * @updatetime 2020???7???8???
	 */
	@RequestMapping("/getContractRegisterList")
	@ResponseBody
	public JsonPagination getContractRegisterList(ContractRegisterList contractRegisterList){
    	List<ContractRegisterList> list = contractRegisterListMng.getContractRegisterList(contractRegisterList);
		Pagination p = new Pagination();
		p.setList(list);
		p.setPageNo(1);
		p.setPageSize(999999);
		p.setTotalCount(list.size());
		return getJsonPagination(p , 1);
	}
	
	/**
	 * 
	 * @return
	 * @author wanping
	 * @createtime 2020???7???13???
	 * @updator wanping
	 * @updatetime 2020???7???13???
	 */
	@ResponseBody
	@RequestMapping("/getFContCode")
	public Result getFContCode() {
		try {
			String fContCode = uptMng.getFContCode();
			return getJsonResult(true, fContCode);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	@RequestMapping("/getReceivPlan")
	@ResponseBody
	public JsonPagination getReceivPlan(String id ,ReceivPlan receivPlan, Integer page, Integer rows){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(id));
    	if("1".equals(cbi.getfUpdateStatus())){//?????????
    		Upt upt = uptMng.findByFContId_U(String.valueOf(cbi.getFcId())).get(0);
    			receivPlan.setDataType(1);
    			receivPlan.setfUptId_R(upt.getfId_U());
    	}else{//?????????
    		receivPlan.setfContId_R(cbi.getFcId());
    		receivPlan.setDataType(0);
    	}
    	List<ReceivPlan> RP = filingMng.getReceivPlan(receivPlan);
		/*for (int i = 0; i < RP.size(); i++) {
			Lookups lookups=lookupsMng.findByLookCode(RP.get(i).getfReceProof());
			RP.get(i).setfReceProofs(lookups.getName());	
		}*/
		Pagination p = new Pagination();
		p.setList(RP);
		p.setPageNo(1);
		p.setPageSize(999999);
		p.setTotalCount(RP.size());
		return getJsonPagination(p , page);
	}
	
	
	@RequestMapping("/getReceivPlans")
	@ResponseBody
	public JsonPagination getReceivPlans(String id ,ReceivPlan receivPlan, Integer page, Integer rows){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	
    	ReimbAppliBasicInfo rabi = reimbAppliMng.findById(Integer.valueOf(id));
    	if(!StringUtil.isEmpty(rabi.getReceivplanid())){
    		String[] a = rabi.getReceivplanid().split(",");
    		receivPlan = receivPlanMng.findById(Integer.valueOf(a[0]));
    	}
    	List<ReceivPlan> RP = receivPlanMng.findByTypeAndId(receivPlan);
		/*for (int i = 0; i < RP.size(); i++) {
			Lookups lookups=lookupsMng.findByLookCode(RP.get(i).getfReceProof());
			RP.get(i).setfReceProofs(lookups.getName());	
		}*/
		Pagination p = new Pagination();
		p.setList(RP);
		p.setPageNo(1);
		p.setPageSize(999999);
		p.setTotalCount(RP.size());
		return getJsonPagination(p , page);
	}
	/**
	 * ??????ID??????
	 * @param id ??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???8???17???
	 * @updator ?????????
	 * @updatetime 2020???8???17???
	 */
	@ResponseBody
	@RequestMapping("/delete/{id}")
	public Result delete(@PathVariable String id ,ModelMap model){
		try {
			//??????????????????
			Upt upt = uptMng.findById(Integer.valueOf(id));
			//??????
			uptMng.deletebyId(upt);
			return getJsonResult(true, Result.deleteSuccessMessage);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return getJsonResult(false, Result.deleteFailureMessage);
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param payId
	 * @return
	 * @author wanping
	 * @createtime 2020???9???8???
	 * @updator wanping
	 * @updatetime 2020???9???8???
	 */
	@RequestMapping("/getReceivplanid")
	@ResponseBody
	private String getReceivplanid (String payId) {
		//??????????????????????????????
		List<ReimbAppliBasicInfo> reimbList = reimbAppliMng.findByProperty("payId", payId);
		Set<String> set = new HashSet<String>();
		if (reimbList != null && !reimbList.isEmpty()) {
			for (ReimbAppliBasicInfo reimbAppliBasicInfo : reimbList) {
				if (!"-1".equals(reimbAppliBasicInfo.getFlowStauts()) && !"0".equals(reimbAppliBasicInfo.getFlowStauts())
						&& !"-4".equals(reimbAppliBasicInfo.getFlowStauts()) && !"9".equals(reimbAppliBasicInfo.getFlowStauts()) && "8".equals(reimbAppliBasicInfo.getType())) {
					if (!StringUtil.isEmpty(reimbAppliBasicInfo.getReceivplanid())) {
						String[] str = reimbAppliBasicInfo.getReceivplanid().split(",");
						for (int i = 0; i < str.length; i++) {
							set.add(str[i]);
						}
					}
				}
			}
		}
		
		if (set.size() > 0) {
			return "true";
		} else {
			return "false";
		}
	}
	
}
