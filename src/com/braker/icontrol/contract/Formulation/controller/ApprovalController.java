package com.braker.icontrol.contract.Formulation.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Proposer;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.contract.Formulation.manager.ApprovalMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.manager.SealInfoMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.SealInfo;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/Approval")
public class ApprovalController extends BaseController{
	
	@Autowired
	private ApprovalMng approvalMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private TBasicItfMng basicItfMng;
	@Autowired
	private TProItfMng proItfMng; 
	@Autowired
	private TProItfMng itfMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private RoleMng roleMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private SealInfoMng sealInfoMng;
	
	/**
	 * ???????????????
	 * @param model
	 * @return
	 * @createtime 2018-06-12
	 * @author crc
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model){
		return "/WEB-INF/view/contract/formulation/approval/approval_list";
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
	 * @createtime 2018-06-14
	 * @author crc
	 */
	@RequestMapping(value = "/JsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows,String searchData,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p=approvalMng.queryList(contractBasicInfo,getUser(),page,rows,searchData);
		List<ContractBasicInfo> li= (List<ContractBasicInfo>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/approve")
	public String approve(ModelMap model, Integer id){
		model.addAttribute("openType", "approve");
		
		//?????????????????????
		ContractBasicInfo contractBasicInfo = approvalMng.findById(id);
		model.addAttribute("bean", contractBasicInfo);
		// ?????????????????????
		List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
		model.addAttribute("formulationAttaList", attaList);
		
		//???????????????
		SignInfo signInfo = new SignInfo();
		List<SignInfo> signInfoList = filingMng.find_Sign(contractBasicInfo);
		if(signInfoList != null && signInfoList.size() > 0){
			signInfo = signInfoList.get(0);
		}
		model.addAttribute("signInfo", signInfo);
		//????????????????????????
		List<Attachment> signInfoattaList = attachmentMng.list(signInfo);
		model.addAttribute("signInfoAttaList", signInfoattaList);
		//?????????????????????????????????????????????
		String flag = "no";
		if(roleMng.getRolesByUserId(getUser().getId()).contains("???????????????")) {
			flag = "yes";
		}
		model.addAttribute("flag", flag);
		
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(contractBasicInfo.getUserId(), "HTND", departId, contractBasicInfo.getBeanCode(), contractBasicInfo.getfNCode(), "T_CONTRACT_BASIC_INFO", "F_CONT_CODE", contractBasicInfo.getFcCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//???????????????id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(contractBasicInfo.getfOperator(), contractBasicInfo.getfDeptName(), contractBasicInfo.getfReqtIME());
		model.addAttribute("proposer", proposer);
		
		//????????????
		model.addAttribute("foCode", contractBasicInfo.getBeanCode());
		return "/WEB-INF/view/contract/formulation/approval/approval_add";
	}
	
	/**
	 * ??????????????????
	 * @param stauts
	 * @param Id
	 * @param fremark
	 * @param checkBean
	 * @param spjlFile
	 * @return
	 */
	@RequestMapping(value = "/approveResult")
	@ResponseBody
	public Result approveResult(ContractBasicInfo bean, TProcessCheck checkBean, String spjlFile) {
		try {
			//?????????????????????
			ContractBasicInfo cbiBean = approvalMng.findById(bean.getFcId());
			if("0".equals(checkBean.getFcheckResult()) && StringUtil.isEmpty(checkBean.getFcheckRemake())){
				return getJsonResult(false, "????????????????????????????????????");
			}
			approvalMng.updatefFlowStauts(cbiBean, checkBean, spjlFile, getUser());
			return getJsonResult(true, "???????????????");
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
	}
	
/*	*//**
	 * 
	 * @Title sealEdit
	 * @Description ??????????????????/??????
	 * @author ??????
	 * @Date 2020???3???1??? 
	 * @param model
	 * @param id
	 * @return
	 * @return String
	 * @throws
	 *//*
	@RequestMapping(value = "/sealEdit")
	public String sealEdit(ModelMap model, Integer id){
		model.addAttribute("fcId", id);
		
		//??????????????????
		ContractBasicInfo cbiBean = approvalMng.findById(id);
		
		//????????????
		Integer fsId = sealInfoMng.findFsIdByFcId(id);
		SealInfo siBean = new SealInfo();
		if (fsId == null) {
			//????????????????????????????????????
			siBean.setFappUserName(getUser().getName());
			siBean.setFappTime(new Date());
		} else {
			siBean = sealInfoMng.findById(fsId);
		}
		model.addAttribute("siBean", siBean);
		
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(cbiBean.getfOperatorId())[0];
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(cbiBean.getUserId(), "HTND", departId, cbiBean.getBeanCode(), cbiBean.getfNCode(), "T_CONTRACT_BASIC_INFO", "F_CONT_CODE", cbiBean.getFcCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//???????????????id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
				
		//?????????????????????????????????
		Proposer proposer = new Proposer(cbiBean.getfOperator(), cbiBean.getfDeptName(), cbiBean.getfReqtIME());
		model.addAttribute("proposer", proposer);
				
		//????????????
		model.addAttribute("foCode", cbiBean.getBeanCode());
		return "/WEB-INF/view/contract/formulation/approval/approval_seal_edit";
	}
	
	*//**
	 * 
	 * @Title saveSeal 
	 * @Description ??????????????????
	 * @author ??????
	 * @Date 2020???3???1??? 
	 * @param id
	 * @param siBean
	 * @return
	 * @return Result
	 * @throws
	 *//*
	@RequestMapping(value = "/saveSeal")
	@ResponseBody
	public Result saveSeal(Integer id, SealInfo siBean) {
		try {
			//????????????????????????
			sealInfoMng.save(siBean, id, getUser());
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	*//**
	 * 
	 * @Title sealDetail 
	 * @Description ??????????????????
	 * @author ??????
	 * @Date 2020???3???6??? 
	 * @param model
	 * @param id
	 * @return
	 * @return String
	 * @throws
	 *//*
	@RequestMapping(value = "/sealDetail")
	public String sealDetail(ModelMap model, Integer id){
		//??????????????????
		ContractBasicInfo cbiBean = approvalMng.findById(id);
		
		//????????????
		Integer fsId = sealInfoMng.findFsIdByFcId(id);
		SealInfo siBean = sealInfoMng.findById(fsId);
		model.addAttribute("siBean", siBean);
		
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(cbiBean.getfOperatorId())[0];
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(cbiBean.getUserId(), "HTND", departId, cbiBean.getBeanCode(), cbiBean.getfNCode(), "T_CONTRACT_BASIC_INFO", "F_CONT_CODE", cbiBean.getFcCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//???????????????id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
				
		//?????????????????????????????????
		Proposer proposer = new Proposer(cbiBean.getfOperator(), cbiBean.getfDeptName(), cbiBean.getfReqtIME());
		model.addAttribute("proposer", proposer);
				
		//????????????
		model.addAttribute("foCode", cbiBean.getBeanCode());
		return "/WEB-INF/view/contract/formulation/approval/approval_seal_detail";
	}*/
	
	/**
	 * 
	 * @Title queryFsId 
	 * @Description ??????????????????
	 * @author ??????
	 * @Date 2020???3???1??? 
	 * @param id
	 * @return
	 * @return Result
	 * @throws
	 */
	@RequestMapping(value = "/queryFsId")
	@ResponseBody
	public Result queryFsId(Integer id){
		try {
			Integer fsId = sealInfoMng.findFsIdByFcId(id);
			if(fsId == null){
				return getJsonResult(false, "?????????????????????");
			} else {
				return getJsonResult(true, "?????????????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
