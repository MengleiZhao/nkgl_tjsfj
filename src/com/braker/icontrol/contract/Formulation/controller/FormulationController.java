package com.braker.icontrol.contract.Formulation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.Formulation.model.ContractReturnInfo;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.purchase.apply.manager.PurchaseApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/Formulation")
public class FormulationController extends BaseController{
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TProExpendDetailMng detailMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private PurchaseApplyMng purchaseApplyMng;
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private ContractRegisterListMng contractRegisterListMng;
	
	/**
	 * ???????????????
	 * @param model
	 * @return
	 * @createtime 2018-06-12
	 * @author crc
	 */
	@RequestMapping(value = "/list")
	public String list(){
		return "/WEB-INF/view/contract/formulation/formulation_list";
	}
	
	/**
	 * ???????????????
	 * @param model
	 * @return
	 * @createtime 2021-01-25
	 * @author crc
	 */
	@RequestMapping(value = "/listGoldPay")
	public String listGoldPay(){
		return "/WEB-INF/view/contract/formulation/formulation_seal_list";
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @createtime 2018-06-12
	 * @author crc
	 */
	@RequestMapping(value = "/JsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model,String searchData){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	Pagination p=formulationMng.queryList(contractBasicInfo,getUser(),page,rows,searchData);
    	List<ContractBasicInfo> li= (List<ContractBasicInfo>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	/**
	 * ???????????????????????????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @createtime 2018-06-12
	 * @author ?????????
	 */
	@RequestMapping(value = "/JsonPaginationSeal")
	@ResponseBody
	public JsonPagination JsonPaginationSeal(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p=formulationMng.queryListJsonSeal(contractBasicInfo, getUser(), page, rows);
		List<ContractBasicInfo> li= (List<ContractBasicInfo>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
	
	
	/**
	 * ?????????????????????
	 * @param model
	 * @return
	 * @createtime 2018-06-12
	 * @author crc
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model){
		model.addAttribute("openType", "add");
		User user = getUser();
		//??????????????????
		ContractBasicInfo bean = new ContractBasicInfo();
		//????????????
		String fcCode = formulationMng.getFcCode(user);
		bean.setFcCode(fcCode);
		//???????????????????????????????????????????????????
		bean.setfOperator(user.getName());
		bean.setfDeptName(user.getDepartName());
		bean.setfReqtIME(new Date());
		//?????????????????????
		bean.setStandard(0);
		model.addAttribute("bean", bean);
		SignInfo signInfo = new SignInfo();
		signInfo.setfIsOfficialUser("1");
		model.addAttribute("signInfo", signInfo);
		
		
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "HTND", getUser().getDpID(), null, null, null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/contract/formulation/formulation_add";
	}
	
	/**
	 * ??????/??????
	 * @param contractBasicInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(ContractBasicInfo cbiBean, String files, String filessqwts, SignInfo siBean, String planJson,String cgConfplanJson){
		try {
			//????????????
			PurchaseApplyBasic cgbean = null;
			if (!StringUtil.isEmpty(cbiBean.getfPurchNo())) {
				cgbean = cgsqMng.findById(Integer.valueOf(cbiBean.getfPurchNo()));
			}
			if("1".equals(cbiBean.getfFlowStauts())){
				//??????
				if(StringUtil.isEmpty(cbiBean.getFcTitle())){
					return getJsonResult(false, "????????????????????????");
				}else if(StringUtil.isEmpty(cbiBean.getFcNum())){
					return getJsonResult(false, "????????????????????????");
				}else if(StringUtil.isEmpty(cbiBean.getFcType())){
					return getJsonResult(false, "????????????????????????");
				}else if(cbiBean.getFcNum().length() > 2){
					return getJsonResult(false, "???????????????????????????????????????");
				}else if("HTFL-01".equals(cbiBean.getFcType()) && (StringUtil.isEmpty(cbiBean.getfPurchName()) || StringUtil.isEmpty(cbiBean.getfPurchNo()))){
					return getJsonResult(false, "????????????????????????");
				}else if(cgbean != null && "1".equals(cgbean.getIsUsed())){
					return getJsonResult(false, "????????????????????????????????????????????????");
				}else{
					formulationMng.save_CBI(cbiBean, files, filessqwts, siBean, planJson,cgConfplanJson, getUser(),cgbean);
					return getJsonResult(true, Result.saveSuccessMessage);
				}
			}else{
				//??????????????????
				formulationMng.save_CBI(cbiBean, files, filessqwts, siBean, planJson,cgConfplanJson, getUser(),cgbean);
				return getJsonResult(true, Result.saveSuccessMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	/**
	 * ??????
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id){
		try {
			formulationMng.delete_CBI(id, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"?????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, Integer id){
		model.addAttribute("openType", "edit");
		
		//??????????????????
		ContractBasicInfo bean = formulationMng.findById(id);
		model.addAttribute("bean", bean);
		//?????????????????????
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("formulationAttaList", attaList);
		
		//???????????????
		SignInfo signInfo = new SignInfo();
		List<SignInfo> signInfoList = filingMng.find_Sign(bean);
		if(signInfoList != null && signInfoList.size() > 0){
			signInfo = signInfoList.get(0);
		}
		model.addAttribute("signInfo", signInfo);
		//????????????????????????
		List<Attachment> signInfoattaList = attachmentMng.list(signInfo);
		model.addAttribute("signInfoAttaList", signInfoattaList);
		
		//????????????????????????
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getfBudgetIndexCode();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//????????????
				bean.setfAvailableAmount(detail.getSyAmount()*10000+"");
			}
		} else if(indexId != null) {
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//????????????
			bean.setfAvailableAmount(index.getSyAmount()+"");
		}
		
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(bean.getfOperatorId())[0];
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "HTND", departId, bean.getBeanCode(), bean.getfNCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFcCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//???????????????id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getfOperator(), bean.getfDeptName(), bean.getfReqtIME());
		model.addAttribute("proposer", proposer);
		
		//????????????
		model.addAttribute("foCode", bean.getBeanCode());
		return "/WEB-INF/view/contract/formulation/formulation_add";
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String detail(ModelMap model, Integer id, String contractUpdateStatus){
		if(!StringUtil.isEmpty(contractUpdateStatus)) {
			model.addAttribute("contractUpdateStatus", contractUpdateStatus);//??????????????????
		}
		
		model.addAttribute("openType", "detail");
		
		//??????????????????
		ContractBasicInfo bean = formulationMng.findById(id);
		model.addAttribute("bean", bean);
		//?????????????????????
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("formulationAttaList", attaList);
		
		//???????????????
		SignInfo signInfo = new SignInfo();
		List<SignInfo> signInfoList = filingMng.find_Sign(bean);
		if(signInfoList != null && signInfoList.size() > 0){
			signInfo = signInfoList.get(0);
		}
		model.addAttribute("signInfo", signInfo);
		//????????????????????????
		List<Attachment> signInfoattaList = attachmentMng.list(signInfo);
		model.addAttribute("signInfoAttaList", signInfoattaList);
		//?????????????????????????????????id
		String departId = departMng.findDeptByUserId(bean.getfOperatorId())[0];
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "HTND", departId, bean.getBeanCode(), bean.getfNCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFcCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//???????????????id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getfOperator(), bean.getfDeptName(), bean.getfReqtIME());
		model.addAttribute("proposer", proposer);
		
		//????????????
		model.addAttribute("foCode", bean.getBeanCode());
		return "/WEB-INF/view/contract/formulation/formulation_detail";
	}
	
	/**
	 * ??????????????????????????????
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fProCode")
	public String fProCodeList(){
		return "/WEB-INF/view/contract/formulation/formulation_add_fProCode";
	}
	
	/**
	 * ?????????????????????????????????
	 * @param tProBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fProCodejsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(TProBasicInfo tProBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = tProBasicInfoMng.fProCode(tProBasicInfo, page, rows);
		
		return getJsonPagination(p, page);
	}
	
	/**
	 * ???????????????????????????
	 * @param parentCode
	 * @param selected
	 * @param blanked
	 * @return
	 */
	@RequestMapping(value = "/lookupsJson")
	@ResponseBody
	public List<ComboboxJson> lookJson(String parentCode,String selected,String blanked){
		List<Lookups> list = formulationMng.getLookupsJson(parentCode, parentCode,selected);
		return getComboboxJson(list,selected);
	}
	
	/**
	 * ?????????????????????
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/BudgetIndexCode")
	public String BudgetIndexCode(){
		return "/WEB-INF/view/choiceIndex";
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
	@RequestMapping(value = "/reCall")
	public Result reCall(String id){
		try {
			formulationMng.reCall(id);
			return getJsonResult(true,"???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"?????????????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-07-10
	 */
	@RequestMapping("/PurchNoList")
	public String purchNoList(ModelMap model){
		return "/WEB-INF/view/contract/formulation/formulation_add_PurchNo";
	}
	
	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping(value = "/cgsqPageChoose")
	@ResponseBody
	public JsonPagination cgsqPageChoose(PurchaseApplyBasic bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.contractcgsqPage(bean, page, rows, getUser());
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
	
	/**
	 * ?????????????????????
	 * @param parentCode ??????????????????id
	 * @param selected ?????????????????????id
	 * @param blanked 
	 * @return 
	 * @author ?????????
	 * @createtime 2020???6???19???
	 * @updator ?????????
	 * @updatetime 2020???6???19???
	 */
	@RequestMapping(value = "/fContractorlookupsJson")
	@ResponseBody
	public List<ComboboxJson> fContractorlookJson(String parentCode,String selected,String blanked){
		List<Lookups> list = formulationMng.getfContractorlookupsJson(parentCode, blanked,selected);
		return getComboboxJson(list,selected);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/findBiddingRegist")
	public BiddingRegist findBiddingRegist(String parentCode,String fContractstatus,String code,String blanked){
		BiddingRegist brlist = cgProcessMng.findbycode(code,fContractstatus);
		return brlist;
	}
	
	/**
	 * ????????????????????????
	 * @param fPurchNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAttacByFPurchNoList")
	public List<ContractReturnInfo> findAttacByFPurchNoList(String fPurchNo){
		List<ContractBasicInfo> list = formulationMng.findAttacByFPurchNoList(fPurchNo);
		List<ContractReturnInfo> returnInfos = new ArrayList<ContractReturnInfo>();
		if(list.size()>0){
			for(int i = 0; i < list.size(); i++) {
				ContractReturnInfo info = new ContractReturnInfo();
					/*if("1".equals(list.get(i).getfUpdateStatus())){//???????????????
						Upt upt = uptMng.findByProperty("fContId_U", list.get(i).getFcId()).get(0);
						info.setFcId(upt.getfId_U());
						info.setFcCode(upt.getfContCode());
						info.setFcTitle(upt.getfContName());
						info.setfUpdateStatus("1");
						
					}else{*/
						info.setFcId(list.get(i).getFcId());
						info.setFcCode(list.get(i).getFcCode());
						info.setFcTitle(list.get(i).getFcTitle());
						//info.setfUpdateStatus(list.get(i).getfUpdateStatus());
					/*}*/
					returnInfos.add(info);
			}
		}
		return returnInfos;
	}
	
	/**
	 * ????????????Id??????????????????
	 * @param fPurchNo
	 * @return
	 * @author ?????????
	 * @createTime 2020-08-18
	 */
	@RequestMapping(value = "/findAttacByFcId")
	@ResponseBody
	public List<Object> findAttacByFcId(Integer fcId,String fUpdateStatus){
		List<Object> list = null;
		ContractBasicInfo basicInfo = formulationMng.findById(fcId);
		if("1".equals(basicInfo.getfUpdateStatus())){
			Upt upt = uptMng.findByCId(String.valueOf(basicInfo.getFcId())).get(0);
			//??????????????????
			if(!"1".equals(upt.getFsealedStatus())){
				list = new ArrayList<Object>();
				list.add(false);
			}else{
				list = cgsqMng.getMingxi("ContractRegisterList", "fId_U", upt.getfId_U());
			}
		}else{ 
			list = cgsqMng.getMingxi("ContractRegisterList", "fcId", fcId);
		}
		return list;
	}
	
	/**
	 * ????????????Id??????????????????
	 * @return
	 * @author ?????????
	 * @createTime 2021-01-27
	 */
	@RequestMapping(value = "/findContractByFcId")
	@ResponseBody
	public List<Object> findContractByFcId(Integer fcId){
		List<Object> list = new ArrayList<Object>();
		ContractBasicInfo basicInfo = formulationMng.findById(fcId);
		List<SignInfo> sign = filingMng.find_Sign(basicInfo);
		
		if("1".equals(basicInfo.getfUpdateStatus())){
			Upt upt = uptMng.findByCId(String.valueOf(basicInfo.getFcId())).get(0);
			basicInfo.setfMarginAmount(upt.getfMarginAmount());
			list.add(basicInfo);
			list.add(sign);
		}else{ 
			list.add(basicInfo);
			list.add(sign);
		}
		return list;
	}
	
	
	/**
	 * ????????????????????????????????????
	 * @param code
	 * @param selected
	 * @return
	 */
	@RequestMapping("/lookupsJsonGist")
	@ResponseBody
	public List<ComboboxJson> lookupsJsonGist(String code,String selected){
		List<Lookups> list = formulationMng.getLookupsJsonGist(code,selected);
		return getComboboxJson(list,selected);
	}
	
	/**
	 * ?????????????????????????????????????????????????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???8???18???
	 * @updator ?????????
	 * @updatetime 2020???8???18???
	 */
	@ResponseBody
	@RequestMapping(value = "/isUnderReview")
	public Result isUnderReview(Integer id ,ModelMap model){
		//?????????
		ContractBasicInfo basicInfo = formulationMng.findById(id);
		if("1".equals(basicInfo.getfUpdateStatus())){//?????????
			Upt upt = uptMng.findByCId(String.valueOf(basicInfo.getFcId())).get(0);
			if(!"1".equals(upt.getFsealedStatus())){
				return getJsonResult(false, "????????????????????????????????????????????????");
			}
			return getJsonResult(true, upt.getfId_U().toString());
		}
		return getJsonResult(true, "");
	}
	
	
}
