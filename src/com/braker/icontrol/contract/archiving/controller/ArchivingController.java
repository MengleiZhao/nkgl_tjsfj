package com.braker.icontrol.contract.archiving.controller;

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
import com.braker.core.model.CheterInfo;
import com.braker.core.model.User;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.archiving.manager.ArchivingMng;
import com.braker.icontrol.contract.archiving.model.Archiving;
import com.braker.icontrol.contract.dispute.manager.DisputAttacMng;
import com.braker.icontrol.contract.dispute.manager.DisputeMng;
import com.braker.icontrol.contract.dispute.model.Dispute;
import com.braker.icontrol.contract.enforcing.manager.ChangeMng;
import com.braker.icontrol.contract.enforcing.manager.ConclusionAttacMng;
import com.braker.icontrol.contract.enforcing.manager.ConclusionMng;
import com.braker.icontrol.contract.enforcing.manager.UptClauseMng;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.manager.SignInfoMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/Archiving")
public class ArchivingController extends BaseController{

	@Autowired
	private ArchivingMng archivingMng;
	@Autowired
	private SignInfoMng SignInfoMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private ArchivingMng ArchivingMng;
	@Autowired
	private DisputeMng disputeMng;
	@Autowired
	private ChangeMng changeMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private UptClauseMng uptAttacMng;
	@Autowired
	private ConclusionMng conclusionMng;
	@Autowired
	private ConclusionAttacMng conclusionAttacMng;
	@Autowired
	private DisputAttacMng disputAttacMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private ReceivPlanMng receivPlanMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private ContractRegisterListMng contractRegisterListMng;
	
	/**
	 * ???????????????
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	@RequestMapping("/list")
	public String list(ModelMap model){
		
		return "/WEB-INF/view/contract/Archiving/archiving_list";
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
	 * @createtime 2018-06-26
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = archivingMng.query_CBI(contractBasicInfo, getUser(),page,rows);
		List<ContractBasicInfo> li= (List<ContractBasicInfo>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param upt
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2020???7???3???
	 * @updator ?????????
	 * @updatetime 2020???7???3???
	 */
	@RequestMapping("/ChangeJsonPagination")
	@ResponseBody
	public JsonPagination ChangeJsonPagination(Upt upt,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = archivingMng.query_upt(upt, getUser(),page,rows);
		List<Upt> li= (List<Upt>) p.getList();
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
	 * @author crc
	 * @createtime 2018-06-28
	 */
	@RequestMapping("/edit/{id}")
	public String archiving(@PathVariable String id,String type,ModelMap model){
		ContractBasicInfo contractBasicInfo = new ContractBasicInfo();
		contractBasicInfo.setfSignTime(new Date());
		model.addAttribute("openType", "Aedit");
		//??????????????????
		Upt upt =new Upt();
		if("1".equals(type)){//??????
			upt = uptMng.findById(Integer.valueOf(id));
			contractBasicInfo = formulationMng.findById(upt.getfContId_U());
			//??????????????????
			List<Attachment> uptAttaList = attachmentMng.list(upt);
			model.addAttribute("changeAttaList", uptAttaList);
		}else if("0".equals(type)){//?????????
			//??????????????????
			contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
			// ?????????????????????
			List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
			model.addAttribute("formulationAttaList", attaList);
		}
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("Upt", upt);
		//??????????????????
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(contractBasicInfo);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//????????????????????????
		List<Attachment> signInfoattaList = attachmentMng.list(sign);
		model.addAttribute("signInfoAttaList", signInfoattaList);
		//??????????????????????????????
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		//?????????????????????????????????
		if("1".equals(upt.getfUptFlowStauts())||"9".equals(upt.getfUptFlowStauts())){
			model.addAttribute("uptOpenType", "Cdetail");
		}else {
			model.addAttribute("uptOpenType", "Cedit");
		}
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
		/*List<Upt> uptList = uptMng.findByFContId_U(id);
		if(uptList!=null&&uptList.size()>0){
			upt = uptList.get(0);
		}*/
		//??????????????????
		List<Attachment> uptAttaList = attachmentMng.list(upt);
		model.addAttribute("changeAttaList", uptAttaList);
		/*//?????????????????????
		if( conclusionAttacMng.findAllFile(id).size()>0){
			model.addAttribute("conclusionattac", conclusionAttacMng.findAllFile(id));
		}	*/
		//??????????????????
		List<Archiving> archivingList = archivingMng.findByContId(id);
		if(archivingList!=null&&archivingList.size()>0){
			model.addAttribute("archiving", archivingList.get(0));
			
			//??????????????????
			List<Attachment> attaListArc = attachmentMng.list(archivingList.get(0));
			model.addAttribute("attaListArc", attaListArc);
		}
		//??????????????????
		Dispute dis =new Dispute();
		List<Dispute> disList = disputeMng.findByContId(id);
		if(disList!=null&&disList.size()>0){
			dis = disList.get(0);
			model.addAttribute("dispute",dis);
		}
		//??????????????????
		List<Attachment> disputeAttaList = attachmentMng.list(dis);
		model.addAttribute("disputeAttaList", disputeAttaList);
		//????????????
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//????????????????????????
		model.addAttribute("splc", "1");
		if("1".equals(type)){//??????
			//?????????????????????????????????id
			String departId=departMng.findDeptByUserId(upt.getfOperatorID())[0];
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCode",upt.getBeanCode());
			return "/WEB-INF/view/contract/Archiving/archiving_upt_edit";
		}else if("0".equals(type)){//?????????
			//?????????????????????????????????id
			String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCode",contractBasicInfo.getBeanCode());
			return "/WEB-INF/view/contract/Archiving/archiving_edit";
		}
		return null;
	}
	
	@RequestMapping("/Save")
	@ResponseBody
	public Result save(ContractBasicInfo contractBasicInfo,Upt upt,String type,User user,Archiving archiving,String files){
		try {
			/*if(StringUtil.isEmpty(archiving.getfToPosition())){
				return getJsonResult(false, "????????????????????????");
			}else if(contractBasicInfo.getfPayStauts().equals("0")){
				return getJsonResult(false, "?????????????????????????????????????????????????????????????????????????????????");
			}else if(receivPlanMng.findUnPay(contractBasicInfo.getFcId()).size()>0){
				return getJsonResult(false, "??????????????????????????????????????????????????????????????????????????????????????????");
			}else {*/
				ArchivingMng.save(contractBasicInfo, upt, type, archiving, getUser(),files);
				return getJsonResult(true, "???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(ModelMap model,@PathVariable String id,String type){
		ContractBasicInfo contractBasicInfo = new ContractBasicInfo();
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("openType", "Adetail");
		model.addAttribute("uptOpenType", "Cdetail");
		//??????????????????
		Upt upt =new Upt();
		if("1".equals(type)){//??????
			upt = uptMng.findById(Integer.valueOf(id));
			contractBasicInfo = formulationMng.findById(upt.getfContId_U());
			//??????????????????
			List<Attachment> uptAttaList = attachmentMng.list(upt);
			model.addAttribute("changeAttaList", uptAttaList);
		}else if("0".equals(type)){//?????????
			//??????????????????
			contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
			// ?????????????????????
			List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
			model.addAttribute("formulationAttaList", attaList);
		}
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("Upt", upt);
		//??????????????????
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(contractBasicInfo);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//????????????????????????
		List<Attachment> signInfoattaList = attachmentMng.list(sign);
		model.addAttribute("signInfoAttaList", signInfoattaList);
		//??????????????????????????????
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		/*//??????????????????
		Upt upt =new Upt();
		List<Upt> uptList = uptMng.findByFContId_U(id);
		if(uptList!=null&&uptList.size()>0){
			upt = uptList.get(0);
			model.addAttribute("Upt", upt);
		}*/
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
		//??????????????????
		List<Archiving> archivingList = archivingMng.findByContId(id);
		if(archivingList!=null&&archivingList.size()>0){
			model.addAttribute("archiving", archivingList.get(0));
			//??????????????????
			List<Attachment> attaListArc = attachmentMng.list(archivingList.get(0));
			model.addAttribute("attaListArc", attaListArc);
		}
		//??????????????????
		Dispute dis =new Dispute();
		List<Dispute> disList = disputeMng.findByContId(id);
		if(disList!=null&&disList.size()>0){
			dis = disList.get(0);
			model.addAttribute("dispute",dis);
		}
		//??????????????????
		List<Attachment> disputeAttaList = attachmentMng.list(dis);
		model.addAttribute("disputeAttaList", disputeAttaList);
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//????????????????????????
		model.addAttribute("splc", "1");
		if("1".equals(type)){//??????
			//????????????
			//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
			//?????????????????????????????????id
			String departId=departMng.findDeptByUserId(upt.getfOperatorID())[0];
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCode",upt.getBeanCode());
			return "/WEB-INF/view/contract/Archiving/archiving_upt_edit";
		}else if("0".equals(type)){//?????????
			//????????????
			//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
			//?????????????????????????????????id
			String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
			//???????????????id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//????????????
			model.addAttribute("foCode",contractBasicInfo.getBeanCode());
			return "/WEB-INF/view/contract/Archiving/archiving_edit";
		}
		return null;
	}
	
	/**
	 * ??????????????????????????????????????????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@RequestMapping("/fProCodejsonPagination")
	@ResponseBody
	public JsonPagination fProCodejsonPagination(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = ArchivingMng.query_CBI_Archiving(contractBasicInfo, getUser(), page, rows);
		return getJsonPagination(p, page);	
	}
	
}
