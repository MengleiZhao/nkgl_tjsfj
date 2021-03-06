package com.braker.icontrol.assets.maintain.controller;

import java.util.Date;
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
import com.braker.core.manager.CheterMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.assets.maintain.manager.MaintainMng;
import com.braker.icontrol.assets.maintain.manager.MaintainRegMng;
import com.braker.icontrol.assets.maintain.model.Maintain;
import com.braker.icontrol.assets.maintain.model.MaintainReg;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
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
@RequestMapping("/Maintain")
public class MaintainController extends BaseController{

	@Autowired
	private MaintainMng maintainMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private MaintainRegMng maintainRegMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/**
	 * ????????????list????????????
	 * @param model
	 * @return
	 * @createTime 2019-04-22
	 * @author ?????????
	 */
	@RequestMapping("/list")
	public String list(ModelMap model){
		return "/WEB-INF/view/assets/maintain/maintain_fixed_list";
	}
	
	/**
	 * ????????????????????????????????????
	 * @param maintain
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @createTime 2019-04-22
	 * @author ?????????
	 */
	@ResponseBody
	@RequestMapping("/jsonPagination")
	public JsonPagination jsonPagination(Maintain maintain,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		if(page==null){page=1;}
		Pagination p = maintainMng.list(maintain, getUser(),sort, order, page, rows);
		List<Maintain> list = (List<Maintain>) p.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNumber(i+1);
		}
		p.setList(list);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????
	 * @param model
	 * @return
	 * @createTime 2019-04-22
	 * @author ?????????
	 */
	@RequestMapping("/add")
	public String add(ModelMap model){
		Maintain m=new Maintain();
		m.settAssetMainCode(StringUtil.Random("WX"));
		User user=getUser();
		m.setfMainDept(user.getDepartName());
		m.setfMainDeptID(user.getDepart().getId());
		m.setfMainUser(user.getName());
		m.setfMainUserID(user.getId());
		m.setfMainTime(new Date());
		model.addAttribute("bean", m);
		model.addAttribute("openType", "add");
		model.addAttribute("detailType", "detail");
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("??????????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"GDZCWX", user.getDpID(),null,null, null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(user.getName(), user.getDepart().getName(), null);
		model.addAttribute("proposer", proposer);

		return "/WEB-INF/view/assets/maintain/maintain_add";
	}

	/**
	 * ??????????????????????????????
	 * @param model
	 * @return
	 * @createTime 2019-04-23
	 * @author ?????????
	 */
	@RequestMapping("/chooseAss")
	public String chooseAss(ModelMap model){
		return "/WEB-INF/view/assets/maintain/maintain_add_asset";
	}
	
	/**
	 * ??????????????????????????????
	 * @param abi
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @createTime 2019-04-22
	 * @author ?????????
	 */
	@ResponseBody
	@RequestMapping("/chooseJsonPagination")
	public JsonPagination chooseJsonPagination(AssetBasicInfo abi, String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	Pagination p = assetBasicInfoMng.maintainchoose(abi, page, rows);
		List<AssetBasicInfo> abiList = (List<AssetBasicInfo>) p.getList();
		for (int i = 0; i < abiList.size(); i++) {
			abiList.get(i).setNumber(i+1);
		}
		p.setList(abiList);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????
	 * @param model
	 * @return
	 * @createTime 2019-04-23
	 * @author ?????????
	 */
	@RequestMapping("/BudgetIndexCode")
	public String BudgetIndexCode(ModelMap model){
		return "/WEB-INF/view/choiceIndex";
	}
	
	/**
	 * ?????????????????????
	 * @param maintain
	 * @param files
	 * @param storageFiles
	 * @param model
	 * @return
	 * @createTime 2019-04-23
	 * @author ?????????
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Result save(Maintain maintain,String maintainFiles ,String storageFiles,ModelMap model){
		try {
			maintainMng.save(maintain, maintainFiles, storageFiles, getUser());
			return getJsonResult(true,Result.saveSuccessMessage );
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,Result.saveFailureMessage );
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param parentCode
	 * @param selected
	 * @param blanked
	 * @return
	 * @createTime 2019-04-23
	 * @author ?????????
	 */
	@RequestMapping("/lookupsJson")
	@ResponseBody
	public List<ComboboxJson> lookJson(String parentCode,String selected,String blanked){
		List<Lookups> list = maintainMng.getLookupsJson(parentCode, parentCode);
		return getComboboxJson(list,selected);
	}
	
	/**
	 * ??????
	 * @param id ??????
	 * @param model
	 * @return
	 * @createTime 2019-04-23
	 * @author ?????????
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable String id ,ModelMap model){
		Maintain bean = maintainMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		model.addAttribute("openType", "detail");
		model.addAttribute("detailType", "detail");
		//????????????
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("maintianAttaList", attaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"GDZCWX", bean.getfMainDeptID(),bean.getBeanCode(),bean.getfNextCode(), bean.getJoinTable(), bean.getBeanCodeField(),bean.gettAssetMainCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getfMainUser(), bean.getfMainDept(), bean.getfMainTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCWX",bean.getfMainDeptID());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",bean.getBeanCode());
		
		return "/WEB-INF/view/assets/maintain/maintain_add";
	}

	/**
	 * ??????
	 * @param id ??????
	 * @param model
	 * @return
	 * @createTime 2019-04-23
	 * @author ?????????
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id,ModelMap model){
		Maintain bean = maintainMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		model.addAttribute("openType", "edit");
		model.addAttribute("detailType", "detail");
		//????????????
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("maintianAttaList", attaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("??????????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"GDZCWX", bean.getfMainDeptID(),bean.getBeanCode(),bean.getfNextCode(), bean.getJoinTable(), bean.getBeanCodeField(),bean.gettAssetMainCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getfMainUser(), bean.getfMainDept(), bean.getfMainTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCWX",bean.getfMainDeptID());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",bean.getBeanCode());
				
		return "/WEB-INF/view/assets/maintain/maintain_add";
	}
	
	/**
	 * ??????id????????????
	 * @param id ??????
	 * @param model
	 * @return
	 * @createTime 2019-04-2
	 * @author ?????????
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable String id,ModelMap model){
		try {
			maintainMng.delete(Integer.valueOf(id));
			return getJsonResult(true, Result.deleteSuccessMessage);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return getJsonResult(false, Result.deleteFailureMessage);
		}
	}

	/**
	 * ????????????????????????list??????
	 * @param model
	 * @return
	 * @createTime 2019-04-24
	 * @author ?????????
	 */
	@RequestMapping("/approvalList")
	public String approvalList(ModelMap model){
		return "/WEB-INF/view/assets/maintain/approval/maintain_fixed_approval_list";
	}
	
	/**
	 * ?????????????????????????????????
	 * @param maintain 
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @createTime 2019-04-24
	 * @author ?????????
	 */
	@RequestMapping("/approvalJson")
	@ResponseBody
	public JsonPagination approvalJson(Maintain maintain, String sort,String order,Integer page,Integer rows,ModelMap model){
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		if(page==null){page=1;}
		Pagination p = maintainMng.approvalList(maintain, getUser(), sort, order, page, rows);
		List<Maintain> list = (List<Maintain>) p.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNumber(i+1);
		}
		p.setList(list);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @createTime 2019-04-24
	 * @author ?????????
	 */
	@RequestMapping("/approvalMaintain/{id}")
	public String approvalMaintain(@PathVariable String id ,ModelMap model){
		Maintain bean = maintainMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		model.addAttribute("openType", "app");
		model.addAttribute("detailType", "detail");
		//????????????
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("maintianAttaList", attaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("??????????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"GDZCWX", bean.getfMainDeptID(),bean.getBeanCode(),bean.getfNextCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.gettAssetMainCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getfMainUser(), bean.getfMainDept(), bean.getfMainTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCWX",bean.getfMainDeptID());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",bean.getBeanCode());
		
		return "/WEB-INF/view/assets/maintain/approval/maintain_add_approval";
	}
	
	/**
	 * ??????????????????
	 * @param stauts
	 * @return
	 * @createTime 2019-04-24
	 * @author ?????????
	 */
	@RequestMapping("/approve/{stauts}")
	@ResponseBody
	public Result approve(@PathVariable String stauts,String id,TProcessCheck checkBean,String spjlFile,ModelMap model){
		try {
			if("btg".equals(stauts)){
				 checkBean = new TProcessCheck("0", checkBean.getFcheckRemake());
			 }else{
				 checkBean = new TProcessCheck("1", checkBean.getFcheckRemake());
			 }
			maintainMng.approveMaintain(id, getUser(), checkBean, spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	/**
	 * ???????????????list??????
	 * @param model
	 * @return
	 * @createTime 2019-04-24
	 * @author ?????????
	 */
	@RequestMapping("/registrationList")
	public String registrationList(ModelMap model){
		return "/WEB-INF/view/assets/maintain/registration/maintain_registration_list";
	}
	
	/**
	 * ????????????????????????
	 * @param maintainReg
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @createTime 2019-04-24
	 * @author ?????????
	 */
	@RequestMapping("/registrationJson")
	@ResponseBody
	public JsonPagination registrationJson(MaintainReg maintainReg, String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	Pagination p=maintainRegMng.registrationJson(maintainReg, getUser(), sort, order, page, rows);
		List<MaintainReg> abiList = (List<MaintainReg>) p.getList();
		for (int i = 0; i < abiList.size(); i++) {
			abiList.get(i).setNumber(i+1);
		}
		p.setList(abiList);
		return getJsonPagination(p, page);
	}

	/**
	 * ??????????????????
	 * @param model
	 * @return
	 * @createTime 2019-04-26
	 * @author ?????????
	 */
	@RequestMapping("/addReg")
	public String addReg(ModelMap model){
		User user = getUser();
		MaintainReg maintainReg=new MaintainReg();
		maintainReg.settMianRegCode(StringUtil.Random("WXDJ"));
		maintainReg.setfMainRegDept(user.getDepartName());
		maintainReg.setfMainRegUser(user.getName());
		model.addAttribute("bean", maintainReg);
		model.addAttribute("openType", "add");
		model.addAttribute("detailType", "detail");
		return "/WEB-INF/view/assets/maintain/registration/maintain_registration_add";
	}
	
	
	/**
	 * ?????????????????????
	 * @param model
	 * @return
	 * @createTime 2019-04-26
	 * @author ?????????
	 */
	@RequestMapping("/registration/{id}")
	public String registration(@PathVariable String id,ModelMap model){
		Maintain maitain=maintainMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", maitain);
		model.addAttribute("openType", "add");
		model.addAttribute("detailType", "detail");
		//??????????????????
		model.addAttribute("checkinfo", "1");
		return "/WEB-INF/view/assets/maintain/registration/maintain_registration_add";
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param model
	 * @return
	 * @createTime 2019-04-26
	 * @author ?????????
	 */
	@RequestMapping("/chooseMain")
	public String chooseMain(ModelMap model){
		return "/WEB-INF/view/assets/maintain/registration/maintain_choose_list";
	}

	/**
	 * ??????????????????????????????????????????
	 * @param maintain 
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @createTime 2019-04-26
	 * @author ?????????
	 */
	@RequestMapping("/chooseMainJson")
	@ResponseBody
	public JsonPagination chooseMainJson(Maintain maintain,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p=maintainMng.regList(maintain, getUser(), sort, order, page, rows);
		List<Maintain> abiList = (List<Maintain>) p.getList();
		for (int i = 0; i < abiList.size(); i++) {
			abiList.get(i).setNumber(i+1);
		}
		p.setList(abiList);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ????????????????????????
	 * @param maintainReg
	 * @param model
	 * @createTime 2019-04-25
	 * @author ?????????
	 */
	@RequestMapping("/saveRegistration")
	@ResponseBody
	public Result saveRegistration(MaintainReg maintainReg,ModelMap model){
		try {
			maintainRegMng.saveRegistration(maintainReg, getUser());
			return getJsonResult(true, Result.saveSuccessMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(true, Result.saveFailureMessage);
		}
		
	}
	
	/**
	 * ????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @createTime 2019-04-26
	 * @author ?????????
	 */
	@RequestMapping("/detailReg/{id}")
	public String detailReg(@PathVariable String id ,ModelMap model){
		MaintainReg maintainReg=maintainRegMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", maintainReg);
		model.addAttribute("openType", "detil");
		model.addAttribute("detailType", "detail");
		return "/WEB-INF/view/assets/maintain/registration/maintain_registration_add";
	}
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @createTime 2019-04-26
	 * @author ?????????
	 */
	@RequestMapping("/editReg/{id}")
	public String editReg(@PathVariable String id ,ModelMap model){
		MaintainReg maintainReg=maintainRegMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", maintainReg);
		model.addAttribute("openType", "edit");
		model.addAttribute("detailType", "detail");
		return "/WEB-INF/view/assets/maintain/registration/maintain_registration_add";
	}
	/**
	 * ??????????????????
	 * @param id
	 * @param model
	 * @return
	 * @createTime 2019-04-26
	 * @author ?????????
	 */
	@RequestMapping("/deleteReg/{id}")
	@ResponseBody
	public Result deleteReg(@PathVariable String id ,ModelMap model){
		try {
			maintainRegMng.deleteReg(id);
			return getJsonResult(true, Result.deleteSuccessMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, Result.deleteFailureMessage);
		}
	}
	
}
