package com.braker.icontrol.assets.allcoa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.assets.allcoa.manager.AllocaListMng;
import com.braker.icontrol.assets.allcoa.manager.AllocaMng;
import com.braker.icontrol.assets.allcoa.model.Alloca;
import com.braker.icontrol.assets.allcoa.model.AllocaList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.StorageMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/Alloca")
public class AllcoaController extends BaseController{
	
	@Autowired
	private AllocaMng allocaMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private AllocaListMng allocaListMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private StorageMng storageMng;

	
	/**
	 * ?????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-13
	 */
	@RequestMapping("/AppList")
	public String AppList(ModelMap model){
		return "/WEB-INF/view/assets/alloca/alloca_list";
	}
	
	/**
	 * ???????????????????????????
	 * @param allcoa
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-13
	 */
	@RequestMapping("/appJsonPagination")
	@ResponseBody
	public JsonPagination appJsonPagination(Alloca allcoa, String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = allocaMng.appJsonPagination(allcoa, getUser(), page, rows);
		List<Alloca> a = (List<Alloca>) p.getList();
		for (int i = 0; i < a.size(); i++) {
			a.get(i).setNumber(i+1);
		}
		p.setList(a);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ??????????????????????????????
	 * @param model
	 * @return
	 */
	@RequestMapping("/choose")
	public String choose(ModelMap model){
		return "/WEB-INF/view/assets/alloca/allcoa_add_asset";
	}
	
	/**
	 * ???????????????????????????????????????????????????????????????
	 * @param abi ????????????
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/allAllcoaJsonPagination")
	public JsonPagination allAllcoa(AssetBasicInfo abi, String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = assetBasicInfoMng.allAllcoa(abi, page, rows);
		List<AssetBasicInfo> abiList = (List<AssetBasicInfo>) p.getList();
		for (int i = 0; i < abiList.size(); i++) {
			abiList.get(i).setNumber(i+1);
		}
		p.setList(abiList);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param alloca
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2019-06-17
	 */
	@ResponseBody
	@RequestMapping("/aListJsonPagination")
	public JsonPagination allocListJson(Alloca alloca, String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = new Pagination();
		List<AllocaList> allocaList = allocaListMng.findbyAllocaCode(alloca.getfAssAllcoaCode());
		for (int i = 0; i < allocaList.size(); i++) {
			allocaList.get(i).setNumber(i+1);
		}
		p.setList(allocaList);
		p.setPageNo(page);
		p.setPageSize(allocaList.size());
		p.setTotalCount(allocaList.size());
		return getJsonPagination(p, page);
	}
	
	
	
	/**
	 * ?????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-13
	 */
	@RequestMapping("/add")
	public String add(ModelMap model){
		Alloca alloca=new Alloca();
		User user=getUser();
		alloca.setfAssAllcoaCode(StringUtil.Random("DB"));
		alloca.setfRecDept(user.getDepartName());
		alloca.setfRecDeptId(user.getDpcode());
		alloca.setfRecUser(user.getName());
		alloca.setfRecUserId(user.getId());
		alloca.setfOutDept(user.getDepartName());
		alloca.setfOutDeptId(user.getDpID());
		alloca.setfOutUser(user.getName());
		alloca.setfOutUserID(user.getId());
		model.addAttribute("bean", alloca);
		model.addAttribute("openType", "add");
		model.addAttribute("detailType", "detail");
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"ZCDB", user.getDpID(),null,alloca.getfNextCode(), null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(alloca.getfRecUser(), user.getDepartName(), null);
		model.addAttribute("proposer", proposer);

		return "/WEB-INF/view/assets/alloca/alloca_add";
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/receCodeList")
	public String receCodeList(ModelMap model){
		return "/WEB-INF/view/assets/alloca/allcoa_add_receCode";
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/nameAndDept")
	public String nameAndDept(ModelMap model){
		return "/WEB-INF/view/assets/alloca/allcoa_add_nameAndDept";
	}
	
	/**
	 * ????????????????????????
	 * @param user
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/allocaNameAndDeptList")
	@ResponseBody
	public JsonPagination allocaNameAndDeptList(User user,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = userMng.getNameAndDept(user, page, rows);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ????????????????????????
	 * @param model
	 * @param alloca 
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ModelMap model,Alloca alloca,String allocaFlies,String planJson){
		try {
			allocaMng.save(alloca, getUser(),allocaFlies,planJson);
			return getJsonResult(true, "???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable String id,ModelMap model,String ledger){
		Alloca alloca = new Alloca();
		if("detailLedger".equals(ledger)){
			alloca = allocaMng.findbyCondition("fAssAllcoaCode",id);
			model.addAttribute("detailType", "ledger");
		}else {
			model.addAttribute("detailType", "detail");
			alloca = allocaMng.findById(Integer.valueOf(id));
		}
		model.addAttribute("bean", alloca);
		model.addAttribute("openType", "detail");
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(alloca.getfRecUserId())[0];
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(alloca.getUserId(),"ZCDB", departId,alloca.getBeanCode(),alloca.getfNextCode(), alloca.getJoinTable(), alloca.getBeanCodeField(), alloca.getfAssAllcoaCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("ZCDB",departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//??????id
		model.addAttribute("foCode",alloca.getBeanCode());
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(alloca.getfRecUser(), alloca.getfRecDept(), alloca.getfReqTime());
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/assets/alloca/alloca_detail";
	}
	
	/**
	 * ???????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable String id,String fId,ModelMap model){
		try {
			allocaMng.deleteById(Integer.valueOf(id),getUser());
			return getJsonResult(true, "???????????????");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	/**
	 * ???????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id,ModelMap model){
		Alloca alloca = allocaMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", alloca);
		model.addAttribute("openType", "edit");
		model.addAttribute("detailType", "detail");
		
		//??????????????????
		List<Attachment> allocaFiles = attachmentMng.list(alloca);
		model.addAttribute("allocaListFiles", allocaFiles);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(alloca.getfRecUserId())[0];
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(alloca.getUserId(),"ZCDB", departId,alloca.getBeanCode(),alloca.getfNextCode(),alloca.getJoinTable(),  alloca.getBeanCodeField(), alloca.getfAssAllcoaCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(alloca.getfRecUser(), alloca.getfRecDept(), alloca.getfReqTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("ZCDB",departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//??????id
		model.addAttribute("foCode",alloca.getBeanCode());
		
		return "/WEB-INF/view/assets/alloca/alloca_add";
	}
	
	/**
	 * ???????????????????????????list??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/approvalList")
	public String approvalList(ModelMap model){
		return "/WEB-INF/view/assets/alloca/approval/alloca_approval_list";
	}
	
	/**
	 * ????????????????????????list????????????
	 * @param allcoa
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/approvalJsonPagination")
	@ResponseBody
	public JsonPagination approvalJsonPagination(Alloca allcoa, String sort,String order,Integer page,Integer rows,ModelMap model){
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		if(page==null){page=1;}
		Pagination p = allocaMng.approvalJsonPagination(allcoa, getUser(), page, rows);
		List<Alloca> a = (List<Alloca>) p.getList();
		for (int i = 0; i < a.size(); i++) {
			a.get(i).setNumber(i+1);
		}
		p.setList(a);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-14
	 */
	@RequestMapping("/approvalAlloca/{id}")
	public String approvalAlloca(@PathVariable String id,ModelMap model){
		Alloca alloca = allocaMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", alloca);
		model.addAttribute("openType", "app");
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(alloca.getfRecUserId())[0];
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(alloca.getUserId(),"ZCDB", departId,alloca.getBeanCode(),alloca.getfNextCode(),alloca.getJoinTable(),  alloca.getBeanCodeField(),  alloca.getfAssAllcoaCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(alloca.getfRecUser(), alloca.getfRecDept(), alloca.getfReqTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("ZCDB",departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",alloca.getBeanCode());		
		return "/WEB-INF/view/assets/alloca/approval/alloca_approval_base";
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param stauts
	 * @param alloca
	 * @param assetCheckInfo
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-15
	 */
	@RequestMapping("/approvel/{stauts}")
	@ResponseBody
	public Result approvel(@PathVariable String stauts,Alloca alloca,TProcessCheck checkBean,String spjlFile,ModelMap model){
		try {
			checkBean = new TProcessCheck(stauts,checkBean.getFcheckRemake());
			allocaMng.updateStauts(alloca, getUser(), stauts, checkBean, spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	/**
	 * ???????????????????????????
	 * @param parentCode
	 * @param selected
	 * @param blanked
	 * @return
	 */
	@RequestMapping("/lookupsJson")
	@ResponseBody
	public List<ComboboxJson> lookJson(String parentCode,String selected,String blanked){
		List<Lookups> list = allocaMng.getLookupsJson(parentCode, parentCode);
		return getComboboxJson(list,selected);
	}
	
	
	
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping("/departCodeList")
	public String departCodeList(ModelMap model){
		return "/WEB-INF/view/assets/alloca/alloca_add_inDeptNameAndDept";
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping("/inDeptNameAndDepart")
	@ResponseBody
	public List<Depart> allocaInDeptNameAndDepart(Depart bean,String sort,String order,ModelMap model){
		List<Depart> list = departMng.findAllDepart(bean, sort, order);
		return list;
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping("/chooseFixed")
	public String chooseFixed(ModelMap model){
		return "/WEB-INF/view/assets/alloca/alloca_choose_fixed_list";
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
			allocaMng.reCall(id);
			return getJsonResult(true,"???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"?????????????????????????????????");
		}
	}
	
}
