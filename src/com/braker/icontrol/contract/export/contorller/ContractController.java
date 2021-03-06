package com.braker.icontrol.contract.export.contorller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braker.common.page.Pagination;
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.contract.Formulation.manager.ContractRegisterListMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.Formulation.model.ContractRegisterList;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * ?????????????????????
 * @author ?????????
 *
 */
@Controller
@RequestMapping(value = "/contractExport")
public class ContractController extends BaseController{
	
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private CgConPlanMng confplanMng;
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private ReceivPlanMng receivPlanMng;
	@Autowired
	private ContractRegisterListMng contractRegisterListMng;
	
	
	/**
	 * ?????????????????????
	 * @param model
	 * @param id ??????
	 * @return
	 * @author ?????????
	 * @createtime 2020???8???10???
	 * @updator ?????????
	 * @updatetime 2020???8???10???
	 */
	@RequestMapping("/formulation")
	public String formulation(ModelMap model,Integer id){
		try {
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
			
			Pagination p=filingMng.find_ReceivPlan(String.valueOf(id),1,1000);
			List<ReceivPlan> RP=(List<ReceivPlan>) p.getList();
//			for (int i = 0; i < RP.size(); i++) {
//				Lookups lookups=lookupsMng.findByLookCode(RP.get(i).getfReceProof());
//				RP.get(i).setfReceProofs(lookups.getName());	
//			}
			model.addAttribute("receivPlanList", RP);
			
			List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fpId", bean.getfPurchNo());
			model.addAttribute("PurcMaterialRegisterList", mingxiList);
			//????????????
			List<TProcessPrintDetail> listTProcessChecks = tProcessPrintDetailMng.findbytab("ContractBasicInfo", "fcId", bean.getFcId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			return "/WEB-INF/view/contract/export/formulation";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "/WEB-INF/view/contract/export/formulation";
		}
	}
	
	/**
	 * ??????????????????
	 * @param model
	 * @param id
	 * @return
	 * @author ?????????
	 * @createtime 2020???8???11???
	 * @updator ?????????
	 * @updatetime 2020???8???11???
	 */
	@RequestMapping("/upt")
	public String upt(ModelMap model,Integer id){
		try {
			model.addAttribute("openType", "detail");
			//??????????????????
			Upt upt =uptMng.findById(Integer.valueOf(id));
			model.addAttribute("upt", upt);
			ContractBasicInfo contractBasicInfo = formulationMng.findById(upt.getfContId_U());
			contractBasicInfo=formulationMng.findById(Integer.valueOf(upt.getfContId_U()));
			model.addAttribute("bean", contractBasicInfo);
			model.addAttribute("uptOpenType", "Cdetail");
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
			//?????????????????????
			List<ReceivPlan> li = receivPlanMng.findbyUptId(upt.getfId_U());
//			for (int i = 0; i < li.size(); i++) {
//				Lookups lookups=lookupsMng.findByLookCode(li.get(i).getfReceProof());
//				li.get(i).setfReceProofs(lookups.getName());	
//			}
			model.addAttribute("receivPlanList", li);
			ContractRegisterList contractRegisterList = new ContractRegisterList();
			contractRegisterList.setfId_U(upt.getfId_U());
			contractRegisterList.setfDataType(1);
			List<ContractRegisterList> crlist = contractRegisterListMng.getContractRegisterList(contractRegisterList );
			model.addAttribute("PurcMaterialRegisterList", crlist);
			
			List<TProcessPrintDetail> listTProcessChecks = tProcessPrintDetailMng.findbytab("Upt", "fId_U", upt.getfId_U());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			return "/WEB-INF/view/contract/export/upt";
		} catch (Exception e) {
			e.printStackTrace();
			return "/WEB-INF/view/contract/export/upt";
		}
	}
	
}
