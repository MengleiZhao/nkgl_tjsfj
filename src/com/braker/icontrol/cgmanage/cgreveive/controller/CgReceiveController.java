package com.braker.icontrol.cgmanage.cgreveive.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgBidMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgSelMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.cgmanage.cgreveive.manager.AcceptContractRegisterListMng;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;
import com.braker.icontrol.cgmanage.cgsupplier.manager.EvalSupplierMng;
import com.braker.icontrol.cgmanage.cgsupplier.model.SupplierEvaluaInfo;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.purchase.apply.manager.PurchaseApplyMng;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * ???????????????controller
 * ????????????????????????????????????
 * @author ?????????
 * @createtime 2018-07-17
 * @updatetime 2018-07-17
 */
@Controller
@RequestMapping(value = "/cgreceive")
public class CgReceiveController extends BaseController{
	@Autowired
	private CgReceiveMng cgReceiveMng;
	
	@Autowired
	private CgApplysqMng cgApplysqMng;
	
	@Autowired
	private CgProcessMng cgProcessMng;
	
	@Autowired
	private CgSelMng cgselMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private EvalSupplierMng evalSupplierMng;

	@Autowired
	private PurchaseApplyMng purchaseApplyMng;
	
	@Autowired
	private LookupsMng lookupsMng;
	
	@Autowired
	private AcceptContractRegisterListMng acceptContractRegisterListMng;
	
	@Autowired
	private RegisterApplyMng registerApplyMng;
	
	/*
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/receive/receive_list";
	}
	
	
	/*
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2020-06-30
	 * @updatetime 2020-06-30
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model) {
		model.addAttribute("user", getUser());
		return "/WEB-INF/view/purchase_manage/receive/select_purchase_pro";
	}
	
	/*
	 * ?????????????????????   ---- ??????????????????
	 * @author ?????????
	 * @createtime 2020-06-30
	 * @updatetime 2020-06-30
	 */
	@RequestMapping(value = "/add_cg")
	public String add_cg(ModelMap model,Integer fpId,String code,String name) {
		//??????????????????????????????
		PurchaseApplyBasic pBean = cgApplysqMng.findById(fpId);
		AcceptCheck bean = new AcceptCheck();
		//??????????????????
		String code1 = "CGYS"+DateUtil.getNowDate("yyyyhhmmss")+getUser().getDpcode()+StringUtil.autoGenericCode((int)((Math.random()*9+1)*1000)+"",4);
		bean.setFacpCode(code1);
		//????????????????????????ID???????????????????????????????????????
		bean.setFpId(fpId);
		bean.setFpCode(pBean.getFpCode());
		bean.setAmount(pBean.getAmount());
		bean.setFpName(pBean.getFpName());
		bean.setFpItemsName(pBean.getFpItemsName());
		//????????????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("acAttac", attac);
		
		//????????????
		model.addAttribute("openType", "add");
		
		//???????????????????????????????????????
		bean.setFacpUserId(getUser().getId());
		bean.setFacpUsername(getUser().getName());
		bean.setfDepartId(getUser().getDpID());
		bean.setfDepartName(getUser().getDepartName());
		bean.setFacpTime(new Date());
		model.addAttribute("bean", bean);
		if("PMMC-4".equals(pBean.getFpItemsName())||"PMMC-5".equals(pBean.getFpItemsName())){
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "HWCGYS", bean.getfDepartId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFacpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			
			//???????????????id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HWCGYS", bean.getfDepartId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
					
			//?????????????????????????????????
			Proposer proposer = new Proposer(bean.getFacpUsername(), bean.getfDepartName(), bean.getFacpTime());
			model.addAttribute("proposer", proposer);
			
			//????????????
			model.addAttribute("foCode", bean.getBeanCode());
		}
		
		
		return "/WEB-INF/view/purchase_manage/receive/receive_cg";
	}
	
	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	@RequestMapping(value = "/pageList")
	@ResponseBody
	public JsonPagination pageList(AcceptCheck bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgReceiveMng.pageList(bean, getUser(), page, rows,searchData);
    	List<AcceptCheck> li = (List<AcceptCheck>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
    		li.get(x).setFpItemsNames(lookups.getName());
			//????????????	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}	
	
	/**
	 * 
	 * @Title edit 
	 * @Description ????????????/??????
	 * @author ??????
	 * @Date 2020???2???20??? 
	 * @param model
	 * @param id
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, Integer id) {
		//??????????????????????????????
		AcceptCheck bean = cgReceiveMng.findById(id);
		PurchaseApplyBasic pBean = cgApplysqMng.findById(bean.getFpId());
		//????????????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("acAttac", attac);
		
		//????????????
		model.addAttribute("openType", "edit");
		model.addAttribute("bean", bean);
		if("PMMC-4".equals(pBean.getFpItemsName())||"PMMC-5".equals(pBean.getFpItemsName())){
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "HWCGYS", bean.getfDepartId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFacpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			
			//???????????????id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HWCGYS", bean.getfDepartId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
					
			//?????????????????????????????????
			Proposer proposer = new Proposer(bean.getFacpUsername(), bean.getfDepartName(), bean.getFacpTime());
			model.addAttribute("proposer", proposer);
			
			//????????????
			model.addAttribute("foCode", bean.getBeanCode());
		}
		
		
		return "/WEB-INF/view/purchase_manage/receive/receive_cg";		
	}
	
	/**
	 * 
	 * @Title save 
	 * @Description ????????????
	 * @author ??????
	 * @Date 2020???2???20??? 
	 * @param acBean
	 * @param pabBean
	 * @param files
	 * @return
	 * @return Result
	 * @throws
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(AcceptCheck acBean,String files,String jsonList) {
		try {
			cgReceiveMng.saveReceive(acBean,files, getUser(),jsonList);
		} catch (ServiceException ec) {
			ec.printStackTrace();
			return getJsonResult(false,ec.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
		return getJsonResult(true,"???????????????");
	}
	
	/**
	 * 
	 * @Title detail 
	 * @Description ????????????
	 * @author ??????
	 * @Date 2020???2???20??? 
	 * @param model
	 * @param id
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/detail")
	public String detail(ModelMap model, Integer id,String type) {
		//??????????????????????????????
		AcceptCheck bean = cgReceiveMng.findById(id);
		PurchaseApplyBasic pBean = cgApplysqMng.findById(bean.getFpId());
		//????????????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("acAttac", attac);
		
		//????????????
		model.addAttribute("openType", "detail");
		
		model.addAttribute("bean", bean);
		if("PMMC-4".equals(pBean.getFpItemsName())||"PMMC-5".equals(pBean.getFpItemsName())){
			//???????????????
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "HWCGYS", bean.getfDepartId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFacpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			
			//???????????????id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HWCGYS", bean.getfDepartId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
					
			//?????????????????????????????????
			Proposer proposer = new Proposer(bean.getFacpUsername(), bean.getfDepartName(), bean.getFacpTime());
			model.addAttribute("proposer", proposer);
			
			//????????????
			model.addAttribute("foCode", bean.getBeanCode());
		}
		model.addAttribute("type", type);
		return "/WEB-INF/view/purchase_manage/receive/receive_cg";		
	}
	
	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	@RequestMapping(value = "/history")
	@ResponseBody
	public JsonPagination checkHistory(String id) {
		Pagination p = new Pagination();
		if(id != null) {
			p.setList(cgReceiveMng.checkHistory(Integer.valueOf(id)));
		}
		return getJsonPagination(p, 0);
	}
	
	/**
	 * ?????????????????????????????????
	 * @createtime 2019-05-28
	 * @author ?????????
	 * @return
	 */
	@RequestMapping(value = "/receiveContractJSP")
	public String list(ModelMap model, String fpId){
		if(!StringUtil.isEmpty(fpId)){
			model.addAttribute("fPurchNo", fpId);
		}
		return "/WEB-INF/view/purchase_manage/receive/receive_contract_list";
	}
	
	/**
	 * 
	 * @Title checkList 
	 * @Description ??????????????????
	 * @author ??????
	 * @Date 2020???2???20??? 
	 * @param model
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/checkList")
	public String checkList(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/receive/receive_check_list";
	}
	
	/**
	 * 
	 * @Title checkPageList 
	 * @Description ????????????????????????
	 * @author ??????
	 * @Date 2020???2???20??? 
	 * @param bean
	 * @param page
	 * @param rows
	 * @return 
	 * @return JsonPagination
	 * @throws
	 */
	@RequestMapping(value = "/checkPageList")
	@ResponseBody
	public JsonPagination checkPageList(AcceptCheck bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgReceiveMng.checkPageList(bean, page, rows, getUser(),searchData);
    	List<AcceptCheck> li = (List<AcceptCheck>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
    		li.get(x).setFpItemsNames(lookups.getName());
			//????????????	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/**
	 * 
	 * @Title check 
	 * @Description ????????????
	 * @author ??????
	 * @Date 2020???2???20??? 
	 * @param model
	 * @param id
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/check")
	public String check(ModelMap model, Integer id, Integer type){
		if (type != null) {
			model.addAttribute("checkFlag", "index");
		}
		//??????????????????????????????
		AcceptCheck bean = cgReceiveMng.findById(id);
		//????????????????????????
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("acAttac", attac);
		//????????????
		model.addAttribute("openType", "check");
		
		model.addAttribute("bean", bean);
		//???????????????
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "HWCGYS", bean.getfDepartId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFacpCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//???????????????id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("HWCGYS", bean.getfDepartId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
				
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getFacpUsername(), bean.getfDepartName(), bean.getFacpTime());
		model.addAttribute("proposer", proposer);
		
		//????????????
		model.addAttribute("foCode", bean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/receive/receive_cg";
	}
	
	/**
	 * 
	 * @Title checkResult 
	 * @Description ????????????
	 * @author ??????
	 * @Date 2020???2???20??? 
	 * @param checkBean
	 * @param acptbean
	 * @param bean
	 * @param spjlFile
	 * @return
	 * @return Result
	 * @throws
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean, AcceptCheck bean, String spjlFile) {
		try {
			cgReceiveMng.saveCheck(checkBean, bean, spjlFile, getUser());
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	/**
	 * ????????????
	 * @author ?????????
	 * @createtime 2019-10-08
	 * @updatetime 2019-10-08
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			cgReceiveMng.reCall(id);
			return getJsonResult(true, "????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @param fpId
	 * @return
	 * @author ?????????
	 * @createtime 2020???3???18???
	 * @updator ?????????
	 * @updatetime 2020???3???18???
	 */
	@ResponseBody
	@RequestMapping("/checkContract")
	public Result checkContract(String fpId){
		Boolean bool;
		try {
			bool = purchaseApplyMng.checkContract(fpId);
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false, "????????????????????????????????????");
		}
		if(!bool){
			return getJsonResult(false, "???????????????????????????????????????");
		}else {
			return null;
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @param id
	 * @return
	 * @author ?????????
	 * @createtime 2020???6???23???
	 * @updator ?????????
	 * @updatetime 2020???6???23???
	 */
	@RequestMapping(value = "/acceptContractRegisterList")
	@ResponseBody
	public List<AcceptContractRegisterList> acceptContractRegisterList(String id,String code) {
		List<AcceptContractRegisterList> list = new ArrayList<AcceptContractRegisterList>();
		if(!"".equals(id)) {
			//??????????????????
			list = acceptContractRegisterListMng.findFpIdbyMingxi(id);
		}
		if(list.size()>0){
			for (AcceptContractRegisterList acceptContractRegisterList : list) {
				acceptContractRegisterList.setFcCode(code);
			}
		}
		return list;
	}
	
	/**
	 * ??????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???7???30???
	 * @updator ?????????
	 * @updatetime 2020???7???30???
	 */
	@RequestMapping("/chooseAcceptCheck")
	public String chooseAcceptCheck(String id ,ModelMap model){
		model.addAttribute("id", id);
		return "/WEB-INF/view/contract/enforcing/enforcing_choose_acceptcheck";
	}
	
	/**
	 * ?????????????????????????????????
	 * @param bean
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???7???30???
	 * @updator ?????????
	 * @updatetime 2020???7???30???
	 */
	@RequestMapping("/acceptCheckPagination")
	@ResponseBody
	public JsonPagination acceptCheckPagination(AcceptCheck bean,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=10000;}
		Pagination p = cgReceiveMng.acceptpageList(bean, page, rows, getUser());
		return getJsonPagination(p , page);
	}
	
	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping(value = "/cgsqPageReceive")
	@ResponseBody
	public JsonPagination cgsqPageReceive(PurchaseApplyBasic bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgReceiveMng.cgsqPageReceive(bean, page, rows, getUser());
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		List<RegisterApplyBasic> registerApplyBasic = registerApplyMng.findByProperty("fpId", li.get(x).getFpId());
    		if(registerApplyBasic.size()>0){
    			li.get(x).setRegisterUserId(registerApplyBasic.get(0).getfUser());
    		}
    		//????????????	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
}
