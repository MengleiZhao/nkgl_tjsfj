package com.braker.icontrol.assets.handle.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.LookupsUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.assets.handle.manager.HandleMng;
import com.braker.icontrol.assets.handle.manager.RegistrationMng;
import com.braker.icontrol.assets.handle.model.AssetRegistration;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.AssetFlowMng;
import com.braker.icontrol.assets.storage.manager.AssetStockMng;
import com.braker.icontrol.assets.storage.manager.RegistMng;
import com.braker.icontrol.assets.storage.manager.StorageMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.icontrol.assets.storage.model.Regist;
import com.braker.icontrol.assets.storage.model.Storage;
import com.braker.icontrol.cgmanage.cgreveive.manager.AcceptContractRegisterListMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/Handle")
public class HandleController extends BaseController{

	@Autowired
	private HandleMng handleMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private RegistrationMng registrationMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private AssetStockMng assetStockMng;
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private AssetFlowMng assetFlowMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private StorageMng storageMng;
	@Autowired
	private RegistMng registMng;
	@Autowired
	private AcceptContractRegisterListMng acceptContractRegisterListMng;
	
	
	/**
	 * ???????????????????????????List??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/lowapplicationList")
	public String lowapplicationList(ModelMap model){
		return "/WEB-INF/view/assets/handle/handle_low_list";
	}
	
	/**
	 * ?????????????????????????????????List??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/fixedapplicationList")
	public String fixedapplicationList(ModelMap model,String fAssType){
		model.addAttribute("fAssType", fAssType);
		return "/WEB-INF/view/assets/handle/handle_fixed_list";
	}
	/**
	 * ?????????????????????????????????List??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020-07-21
	 */
	@RequestMapping("/intangibleApplicationList")
	public String intangibleApplicationList(ModelMap model,String fAssType){
		model.addAttribute("fAssType", fAssType);
		return "/WEB-INF/view/assets/handle/handle_intangible_list";
	}
	
	/**
	 * ???????????????????????????List????????????
	 * @param handle ????????????
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/applicationJson")
	@ResponseBody
	public JsonPagination applicationJson(Handle handle,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		if(page==null){page=1;}
		Pagination p = handleMng.applicationList(handle, getUser(), page, rows);
		List<Handle> li= (List<Handle>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}

	/**
	 * ???????????????????????????
	 * @param parentCode
	 * @param selected
	 * @param blanked
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/lookupsJson")
	@ResponseBody
	public List<ComboboxJson> lookJson(String parentCode,String selected,String blanked){
		List<Lookups> list = handleMng.getLookupsJson(parentCode, parentCode);
		return getComboboxJson(list,selected);
	}
	
	/**
	 * ????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/addApplication")
	public String addApplication(String fAssType,ModelMap model){
		Handle handle=new Handle();
		User user = getUser();
		handle.setfAssHandleCode(StringUtil.Random("ZCCZ"));
		handle.setfAssType(fAssType);
		handle.setfRecUserId(user.getId());
		handle.setfRecUser(user.getName());
		handle.setfRecDept(user.getDepartName());
		handle.setfReqTime(new Date());
		model.addAttribute("bean", handle);
		model.addAttribute("openType", "add");
		model.addAttribute("detailType", "detail");
		
		//?????????????????????????????????
		Proposer proposer = new Proposer(user.getName(), user.getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		
		if(fAssType.equals("ZCLX-02")){
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"GDZCCZ", user.getDpID(),null,null, null, null, null, "1");
			model.addAttribute("nodeConf", nodeConfList);
			model.addAttribute("type", "ZCLX-02");
			return "/WEB-INF/view/assets/handle/handle_base_add_fixed";
		}
		if(fAssType.equals("ZCLX-03")){
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"WXZCCZ", user.getDpID(),null,null, null, null, null, "1");
			model.addAttribute("nodeConf", nodeConfList);
			model.addAttribute("type", "ZCLX-03");
			return "/WEB-INF/view/assets/handle/handle_base_add_intangible";
		}
		return null;
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param fId handle?????????
	 * @param fAssType ???????????????????????????????????????
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assetRegJson")
	public List<AssetRegistration> regJsonPagination(String fId , String fAssType,String sort,String order,Integer page,Integer rows,ModelMap model){
		List<AssetRegistration> list = handleMng.inAndFixedHandle(fId, fAssType);
		for (int i = 0; i < list.size(); i++) {
			AssetBasicInfo abi = assetBasicInfoMng.findById(Integer.valueOf(fId));//????????????
			Regist regist = registMng.findById(abi.getfListId());//????????????????????????
			Storage storage = storageMng.findById(regist.getfId_S());//????????????
			if (storage.getFacpId() != null) {
				AcceptContractRegisterList acceptContractRegisterList = acceptContractRegisterListMng.findById(storage.getFacpId());//????????????
				list.get(i).setContractId(acceptContractRegisterList.getFcId());
			}
			//ContractBasicInfo cbi = formulationMng.findById(acceptContractRegisterList.getFcId());//???????????????
		}
		return list;
	}
	
	
	
	
	
	/**
	 * ????????????????????????????????????
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/AssCodeList")
	public String receCodeList(ModelMap model,String Type){
		model.addAttribute("type", Type);
		return "/WEB-INF/view/assets/handle/handle_add_assCode";
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/nameAndDept")
	public String nameAndDept(ModelMap model){
		return "/WEB-INF/view/assets/handle/handle_add_nameAndDept";
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
	 * @createtime 2018-08-16
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
	 * ??????????????????????????????
	 * @param ABI
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/AssCodeJson")
	@ResponseBody
	public JsonPagination AssCodeJson(AssetBasicInfo ABI,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p=assetBasicInfoMng.allStork(ABI, page, rows);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????????????????
	 * @param handle
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/applicationSave")
	@ResponseBody
	public Result applicationSave(String  planJson,Handle handle,String LowHandleFlies,ModelMap model){
		try {
			
			handleMng.save(planJson,handle, getUser(),LowHandleFlies);
			return getJsonResult(true, "???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/detail/{id}")
	public String Detail(@PathVariable String id,ModelMap model,String ledger){
		Handle handle = new Handle();
		if("detailLedger".equals(ledger)){
			handle = handleMng.findbyCode(id);
			model.addAttribute("detailType", "ledger");
		}else{
			model.addAttribute("detailType", "detail");
			handle = handleMng.findById(Integer.valueOf(id));
		}
		model.addAttribute("bean", handle);

		Handle beanCopy = new Handle();
		BeanUtils.copyProperties(handle,beanCopy);
		
		model.addAttribute("openType", "detail");
		//???????????????
		List<Attachment> handleAttaList = attachmentMng.list(handle);
		model.addAttribute("handleList", handleAttaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(handle.getfRecUserId())[0];
		//?????????????????????????????????
		Proposer proposer = new Proposer(handle.getfRecUser(), handle.getfRecDept(), handle.getfReqTime());
		model.addAttribute("proposer", proposer);	
		
		//????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		if(roleName.contains("???????????????")||roleName.contains("???????????????")){
			model.addAttribute("swglg", 1);//???????????????????????????1   ????????????????????????????????????
		}else{
			model.addAttribute("swglg", 0);//???????????????????????????0   ???????????????????????????????????????
		}
		
		//??????id
		model.addAttribute("foCode",handle.getBeanCode());
		if("ZCLX-02".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"GDZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(), handle.getJoinTable(), handle.getBeanCodeField(), handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-02");
			model.addAttribute("nodeConf", nodeConfList);
			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			return "/WEB-INF/view/assets/handle/handle_base_detail_fixed";
		}else if("ZCLX-03".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WXZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"WXZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(), handle.getJoinTable(), handle.getBeanCodeField(), handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-03");
			model.addAttribute("nodeConf", nodeConfList);
			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			return "/WEB-INF/view/assets/handle/handle_base_detail_intangible";
		}
		return null;
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id,ModelMap model){
		Handle handle = handleMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", handle);
		model.addAttribute("openType", "edit");
		model.addAttribute("detailType", "detail");
		//???????????????
		List<Attachment> handleAttaList = attachmentMng.list(handle);
		model.addAttribute("handleList", handleAttaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(handle.getfRecUserId())[0];
		//?????????????????????????????????
		Proposer proposer = new Proposer(handle.getfRecUser(), handle.getfRecDept(), handle.getfReqTime());
		model.addAttribute("proposer", proposer);	
		//??????id
		model.addAttribute("foCode",handle.getBeanCode());
		if("ZCLX-02".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"GDZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(),handle.getJoinTable(), handle.getBeanCodeField(),handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-02");
			model.addAttribute("nodeConf", nodeConfList);
			return "/WEB-INF/view/assets/handle/handle_base_add_fixed";
		}else if("ZCLX-03".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WXZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"WXZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(), handle.getJoinTable(), handle.getBeanCodeField(),handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-03");
			model.addAttribute("nodeConf", nodeConfList);
			return "/WEB-INF/view/assets/handle/handle_base_add_intangible";
		}
		return null;
	}
	
	/**
	 * ????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable String id,ModelMap model){
		try {
			handleMng.delete(id, getUser());;
			return getJsonResult(true, "???????????????");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????List??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/approvalFixedList")
	public String approvalFixedList(String fAssType,ModelMap model){
		model.addAttribute("fAssType", fAssType);
		String roleName=getUser().getRoleName();
		String deptName=getUser().getDepart().getName();
		if(roleName.contains("??????????????????") && "???????????????".equals(deptName)){
			model.addAttribute("djhCode", 1);
		}else{
			model.addAttribute("djhCode", 0);
		}
		return "/WEB-INF/view/assets/handle/approval/handle_approval_fixed_list";
	}
	/**
	 * ???????????????????????????????????????List??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/approvalIntangibleList")
	public String approvalIntangibleList(String fAssType,ModelMap model){
		model.addAttribute("fAssType", fAssType);
		String roleName=getUser().getRoleName();
		String deptName=getUser().getDepart().getName();
		if(roleName.contains("??????????????????") && "???????????????".equals(deptName)){
			model.addAttribute("djhCode", 1);
		}else{
			model.addAttribute("djhCode", 0);
		}
		return "/WEB-INF/view/assets/handle/approval/handle_approval_intangible_list";
	}
	
	/**
	 * ????????????????????????List????????????
	 * @param handle
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/approvalJson")
	@ResponseBody
	public JsonPagination approvalJson(Handle handle,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p=handleMng.approvalList(handle, getUser(), page, rows);
		List<Handle> li= (List<Handle>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/approvalDetail/{id}")
	public String approvalDetail(@PathVariable String id,ModelMap model){
		Handle handle = handleMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", handle);
		
		Handle beanCopy = new Handle();
		BeanUtils.copyProperties(handle,beanCopy);
		
		model.addAttribute("openType", "detail");
		model.addAttribute("detailType", "detail");
		//???????????????
		List<Attachment> handleAttaList = attachmentMng.list(handle);
		model.addAttribute("handleList", handleAttaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(handle.getfRecUserId())[0];
		//?????????????????????????????????
		Proposer proposer = new Proposer(handle.getfRecUser(), handle.getfRecDept(), handle.getfReqTime());
		model.addAttribute("proposer", proposer);	
		
		//????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		if(roleName.contains("???????????????")){
			model.addAttribute("swglg", 1);//???????????????????????????1   ????????????????????????????????????
		}else{
			model.addAttribute("swglg", 0);//???????????????????????????0   ???????????????????????????????????????
		}

		
		//??????id
		model.addAttribute("foCode",handle.getBeanCode());
		if("ZCLX-02".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"GDZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(), handle.getJoinTable(), handle.getBeanCodeField(),handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-02");
			model.addAttribute("nodeConf", nodeConfList);
			
			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			return "/WEB-INF/view/assets/handle/handle_base_detail_fixed";
		}else if("ZCLX-03".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WXZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"WXZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(), handle.getJoinTable(), handle.getBeanCodeField(), handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-03");
			model.addAttribute("nodeConf", nodeConfList);
			
			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			return "/WEB-INF/view/assets/handle/handle_base_detail_intangible";
		}
		return "/WEB-INF/view/assets/handle/handle_base_detail_fixed";
	}
	
	/**
	 * ?????????????????????
	 * @param id
	 * @param model
	 * @return@author ?????????
	 * @createtime 2018-08-18
	 */
	@RequestMapping("/approvalHandle/{id}")
	public String approvalHandle(@PathVariable String id,ModelMap model){
		Handle handle = handleMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", handle);
		Handle beanCopy = new Handle();
		BeanUtils.copyProperties(handle,beanCopy);
		model.addAttribute("openType", "approval");	
		model.addAttribute("detailType", "detail");
		model.addAttribute("fAssType", handle.getfAssType());
		//???????????????
		List<Attachment> handleAttaList = attachmentMng.list(handle);
		model.addAttribute("handleList", handleAttaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(handle.getfRecUserId())[0];
		//?????????????????????????????????
		Proposer proposer = new Proposer(handle.getfRecUser(), handle.getfRecDept(), handle.getfReqTime());
		model.addAttribute("proposer", proposer);	
		//??????id
		model.addAttribute("foCode",handle.getBeanCode());
		
		//????????????????????????????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		String deptName=getUser().getDepart().getName();
		if(roleName.contains("??????????????????") && "???????????????".equals(deptName)){
			model.addAttribute("djhCode", 1);
		}else{
			model.addAttribute("djhCode", 0);
		}
		
		//????????????????????????????????????????????????
		if(roleName.contains("???????????????")){
			model.addAttribute("swglg", 1);//???????????????????????????1   ????????????????????????????????????
		}else{
			model.addAttribute("swglg", 0);//???????????????????????????0   ???????????????????????????????????????
		}
				
		
		if("ZCLX-02".equals(handle.getfAssType())){
			//????????????????????????????????????????????????
			if(roleName.contains("???????????????")){
				model.addAttribute("zcglg", 1);//???????????????????????????1   ????????????????????????????????????
			}else{
				model.addAttribute("zcglg", 0);//???????????????????????????0   ???????????????????????????????????????
			}
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"GDZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(),handle.getJoinTable(),  handle.getBeanCodeField(), handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-02");
			model.addAttribute("nodeConf", nodeConfList);

			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			return "/WEB-INF/view/assets/handle/approval/handle_base_check_fixed";
		}else if("ZCLX-03".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WXZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"WXZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(), handle.getJoinTable(), handle.getBeanCodeField(),handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-03");
			model.addAttribute("nodeConf", nodeConfList);

			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			return "/WEB-INF/view/assets/handle/approval/handle_base_check_intangible";
		}
		return "/WEB-INF/view/assets/handle/approval/handle_base_check_fixed";
	}
	
	/**
	 * ????????????
	 * @param stauts
	 * @param handle
	 * @param ACI
	 * @param model
	 * @return@author ?????????
	 * @createtime 2018-08-18
	 */
	@RequestMapping("/approval/{stauts}")
	@ResponseBody
	public Result approval(@PathVariable String stauts,Handle handle,TProcessCheck checkBean,String spjlFile,ModelMap model,String files01,String files02,String files03,String files04,String planJson){
		try {
			handleMng.updateStauts(stauts, handle, checkBean, getUser(),spjlFile, files01, files02, files03, files04,planJson);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	/**
	 * ?????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 */
	@RequestMapping("/registrationFixedList")
	public String registrationFixedList(String fAssType,ModelMap model){
		model.addAttribute("fAssType", fAssType);
		return "/WEB-INF/view/assets/handle/registration/handle_registration_list";
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 */
	@RequestMapping("/replenishFixedList")
	public String replenishFixedList(String fAssType,ModelMap model){
		model.addAttribute("fAssType", fAssType);
		return "/WEB-INF/view/assets/handle/registration/handle_replenish_list";
	}
	/**
	 * ?????????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 */
	@RequestMapping("/replenishIntangibleList")
	public String replenishIntangibleList(String fAssType,ModelMap model){
		model.addAttribute("fAssType", fAssType);
		return "/WEB-INF/view/assets/handle/registration/handle_replenish_list";
	}
	
	/**
	 * ?????????????????????????????????list????????????
	 * @param handle
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/registrationJson")
	@ResponseBody
	public JsonPagination registrationJson(String fAssType,AssetRegistration assetRegistration,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = registrationMng.registrationList(assetRegistration,fAssType, getUser(), page, rows);
		List<AssetRegistration> li= (List<AssetRegistration>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	*//**
	 * ????????????????????????
	 * @param molde
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/registrationAdd")
	public String registrationAdd(String fAssType,ModelMap model){
		model.addAttribute("openType", "add");
		model.addAttribute("fAssType", fAssType);
		AssetRegistration AR=new AssetRegistration();
		AR.setfAssHandleRegCode(StringUtil.Random("DJ"));
		model.addAttribute("bean", AR);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/assets/handle/registration/handle_registration_add";
	}
	
	*//**
	 * ??????????????????????????????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/HandleList")
	public String HandleList(ModelMap model){
		return "/WEB-INF/view/assets/handle/registration/handle_registration_handlelist";
	}
	
	*//**
	 * ????????????????????????
	 * @param handle
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/handleRegJson")
	@ResponseBody
	public JsonPagination handleRegJson(Handle handle,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = handleMng.handleRegList(handle, getUser(), page, rows);
		List<Handle> li= (List<Handle>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	*//**
	 * ??????????????????
	 * @param assetRegistration
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/registrationSave")
	@ResponseBody
	public Result registrationSave(AssetRegistration assetRegistration){
		try {
			registrationMng.save(assetRegistration, getUser());
			return getJsonResult(true, "???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	*//**
	 * ??????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/registrationEdit/{id}")
	public String registrationEdit(@PathVariable String id,ModelMap model){
		model.addAttribute("openType", "edit");
		AssetRegistration AR=registrationMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", AR);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/assets/handle/registration/handle_registration_add";
	}
	
	*//**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/registrationDetail/{id}")
	public String registrationDetail(@PathVariable String id,ModelMap model){
		model.addAttribute("openType", "detail");
		AssetRegistration AR=registrationMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", AR);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/assets/handle/registration/handle_registration_add";
	}
	
	*//**
	 * ?????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-21
	 *//*
	@RequestMapping("/registrationDelete/{id}")
	@ResponseBody
	public Result registrationDelete(@PathVariable String id,ModelMap model){
		try {
			AssetRegistration AR=registrationMng.findById(Integer.valueOf(id));
			AR.setfRegStauts("99");
			registrationMng.saveOrUpdate(AR);
			return getJsonResult(true, "???????????????");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}*/
	
	/**
	 * ?????????????????????
	 * @param fAssType
	 * @return
	 */
	@RequestMapping("/ledgerList")
	public String ledgerList(String fAssType){
		
		return "/WEB-INF/view/assets/handle/handle_base_ledger_list";
	}
	
	/**
	 * 
	 * @param handle
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ledgerJson")
	public JsonPagination ledgerJson(Handle handle,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		if(page==null){page=1;}
		Pagination p = handleMng.ledgerPagination(handle, getUser(), page, rows);
		List<Handle> li= (List<Handle>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
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
			handleMng.reCall(id);
			return getJsonResult(true,"???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"?????????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param model
	 * @author ?????????
	 * @createtime 2020???7???22???
	 * @updator ?????????
	 * @updatetime 2020???7???22???
	 */
	@RequestMapping("/chooseFixedInfo")
	public String chooseFixedInfo(ModelMap model,String type,String tabId){
		//????????????????????????
		model.addAttribute("splc", "1");
		model.addAttribute("type", type);
		model.addAttribute("tabId", tabId);
		return "/WEB-INF/view/assets/storage/storage_choose_assType";
	}
	
	
	@ResponseBody
	@RequestMapping("/getbyAssetid")
	public JsonPagination getbyAssetid(String assetid,Rece bean,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = new Pagination();
		List<AssetRegistration> li = handleMng.getbyAssetid(assetid);
		p.setPageNo(page);
		p.setPageSize(li.size());
		p.setTotalCount(li.size());
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ????????????????????????????????????List??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020-07-27
	 */
	@RequestMapping("/disposeAcceptList")
	public String disposeAcceptList(ModelMap model,String fAssType){
		model.addAttribute("fAssType", fAssType);
		return "/WEB-INF/view/assets/handle/handle_dispose_accept_list";
	}
	
	/**
	 * ???????????????????????????List????????????
	 * @param handle ????????????
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020-07-28
	 */
	@RequestMapping("/disposeAcceptJson")
	@ResponseBody
	public JsonPagination disposeAcceptJson(Handle handle,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		if(page==null){page=1;}
		Pagination p = handleMng.disposeAcceptJson(handle, getUser(), page, rows);
		List<Handle> li= (List<Handle>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-17
	 */
	@RequestMapping("/editDisposeAccept/{id}")
	public String editDisposeAccept(@PathVariable String id,ModelMap model){
		Handle handle = handleMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", handle);
		model.addAttribute("openType", "accept");
		model.addAttribute("detailType", "detail");
		//???????????????
		List<Attachment> handleAttaList = attachmentMng.list(handle);
		model.addAttribute("handleList", handleAttaList);
		
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		if(roleName.contains("???????????????")||roleName.contains("???????????????")){
			model.addAttribute("swglg", 1);//???????????????????????????1   ????????????????????????????????????
		}else{
			model.addAttribute("swglg", 0);//???????????????????????????0   ???????????????????????????????????????
		}
		//??????????????????id
		String departId=departMng.findDeptByUserId(handle.getfRecUserId())[0];
		//?????????????????????????????????
		Proposer proposer = new Proposer(handle.getfRecUser(), handle.getfRecDept(), handle.getfReqTime());
		model.addAttribute("proposer", proposer);	
		//??????id
		model.addAttribute("foCode",handle.getBeanCode());
		if("ZCLX-02".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"GDZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(),handle.getJoinTable(), handle.getBeanCodeField(),handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-02");
			model.addAttribute("nodeConf", nodeConfList);
			return "/WEB-INF/view/assets/handle/handle_dispose_accept_fixed";
		}
		return null;
	}
	
	/**
	 * ??????????????????
	 * @param id ??????id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020-07-28
	 * @updatetime 2020-07-28
	 */
	@ResponseBody
	@RequestMapping("/updateDisposeAccept")
	public Result updateDisposeAccept(String id,ModelMap model){
		try {
			handleMng.updateDisposeAccept(id);
			return getJsonResult(true,"???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"?????????????????????????????????");
		}
	}
	
	
	/**
	 * ???????????????????????????List????????????
	 * @param handle ????????????
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-08-16
	 */
	@RequestMapping("/replenishJson")
	@ResponseBody
	public JsonPagination replenishJson(Handle handle,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		if(page==null){page=1;}
		Pagination p = handleMng.replenishList(handle, getUser(), page, rows);
		List<Handle> li= (List<Handle>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param id
	 * @param model
	 * @return@author ?????????
	 * @createtime 2020-08-28
	 */
	@RequestMapping("/replenishHandle/{id}")
	public String replenishHandle(@PathVariable String id,ModelMap model){
		Handle handle = handleMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", handle);
		Handle beanCopy = new Handle();
		BeanUtils.copyProperties(handle,beanCopy);
		model.addAttribute("openType", "approval");	
		model.addAttribute("detailType", "detail");
		model.addAttribute("fAssType", handle.getfAssType());
		//???????????????
		List<Attachment> handleAttaList = attachmentMng.list(handle);
		model.addAttribute("handleList", handleAttaList);
		//??????????????????
		model.addAttribute("checkinfo", "1");
		//??????????????????id
		String departId=departMng.findDeptByUserId(handle.getfRecUserId())[0];
		//?????????????????????????????????
		Proposer proposer = new Proposer(handle.getfRecUser(), handle.getfRecDept(), handle.getfReqTime());
		model.addAttribute("proposer", proposer);	
		//??????id
		model.addAttribute("foCode",handle.getBeanCode());
		
		//????????????????????????????????????????????????????????????????????????
		String roleName=getUser().getRoleName();
		String deptName=getUser().getDepart().getName();
		if(roleName.contains("??????????????????") && "???????????????".equals(deptName)){
			model.addAttribute("djhCode", 1);
		}else{
			model.addAttribute("djhCode", 0);
		}
		
		//????????????????????????????????????????????????
		if(roleName.contains("???????????????")){
			model.addAttribute("swglg", 1);//???????????????????????????1   ????????????????????????????????????
		}else{
			model.addAttribute("swglg", 0);//???????????????????????????0   ???????????????????????????????????????
		}
				
		
		if("ZCLX-02".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("GDZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"GDZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(),handle.getJoinTable(),  handle.getBeanCodeField(), handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-02");
			model.addAttribute("nodeConf", nodeConfList);

			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			return "/WEB-INF/view/assets/handle/approval/handle_base_check_fixed_rep";
		}else if("ZCLX-03".equals(handle.getfAssType())){
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WXZCCZ", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(handle.getUserId(),"WXZCCZ", departId,handle.getBeanCode(),handle.getfNextCode(), handle.getJoinTable(), handle.getBeanCodeField(),handle.getfAssHandleCode(),"1");
			model.addAttribute("type", "ZCLX-03");
			model.addAttribute("nodeConf", nodeConfList);

			String times ="";
			if(!"".equals(handle.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy???MM???dd???");
				times =fmt.format(handle.getfReqTime());     // ????????? X???X???X???
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+handle.getfRecUser()+"</td></tr>"
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
			String a0 = "<p style=\"text-align:center;font-family:??????, monospace;font-size:20.0pt;border: #ffffff;\">"+handle.getfHandleTitle()+"</p><br/>";
			a0 +=handle.getfHandleText();
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
			beanCopy.setfHandleText(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			return "/WEB-INF/view/assets/handle/approval/handle_base_check_intangible_rep";
		}
		return "/WEB-INF/view/assets/handle/approval/handle_base_check_fixed_rep";
	}
	
	/**
	 * ????????????????????????????????????
	 * @return@author ?????????
	 * @createtime 2020-08-28
	 */
	@RequestMapping("/replenishHandleFile")
	@ResponseBody
	public Result replenishHandleFile(Handle handle,ModelMap model,String files01,String files02,String files03,String files04){
		try {
			Handle handles = handleMng.findById(handle.getfId());
			attachmentMng.joinEntity(handles, files01);
			attachmentMng.joinEntity(handles, files02);
			attachmentMng.joinEntity(handles, files03);
			attachmentMng.joinEntity(handles, files04);
			if("ZCLX-02".equals(handles.getfAssType())){//????????????
				handles.setfRepStauts("1");
				handles.setfAcceptStauts("0");
				handleMng.saveOrUpdate(handles);
			}else{//????????????   ????????????????????????
				handles.setfRepStauts("1");
				handles.setfAcceptStauts("1");
				handleMng.saveOrUpdate(handles);
				List<AssetRegistration> li = handleMng.inAndFixedHandle(handles.getEntryId(), handles.getfAssType());
				for (int i = 0; i < li.size(); i++) {
					AssetBasicInfo assetBasicInfo = assetBasicInfoMng.findById(li.get(i).getfAssId());
					Lookups zckyzt = lookupsMng.findByLookCode("ZCKYZT-02");
					assetBasicInfo.setfAvailableStauts(zckyzt);
					Lookups zcsyzt = lookupsMng.findByLookCode("ZCSYZT-03");
					assetBasicInfo.setfUsedStauts(zcsyzt);
					assetBasicInfoMng.saveOrUpdate(assetBasicInfo);
					
					//??????????????????
					Lookups fOptType=LookupsUtil.findByLookCode("ZCLSCZLX-04");
					Lookups fAssType=LookupsUtil.findByLookCode(handles.getfAssType());
					User handleUser = userMng.findById(handles.getfRecUserId());
					assetFlowMng.addFlow(handleUser, fOptType, handles.getBeanCode(), li.get(i).getfAssCode(), li.get(i).getfAssName(),fAssType, li.get(i).getfMeasUnit(), 1);
					//??????????????????
					//exportHandleMng.arrangeCheckDetailHandle(handle, handle.getfAssType(), user);
				}
			}
			
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/contractdetail")
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
	
	
	@RequestMapping("/enforcinglist")
	public String enforcinglist(ModelMap model, Integer id){
		model.addAttribute("id", id);
		return "/WEB-INF/view/assets/handle/enforcing_list";
	}
	
	
}