package com.braker.icontrol.contract.goldpay.comtroller;

import java.util.ArrayList;
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
import com.braker.core.manager.ShenTongMng;
import com.braker.core.model.CheterInfo;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
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
import com.braker.icontrol.contract.filing.manager.SignInfoMng;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.contract.goldpay.manager.GoldPayMng;
import com.braker.icontrol.contract.goldpay.model.GoldPay;
import com.braker.icontrol.contract.ledger.manager.LedgerMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/GoldPay")
public class GoldPayController extends BaseController {

	@Autowired
	private GoldPayMng goldPayMng;
	@Autowired
	private SignInfoMng signInfoMng;
	@Autowired
	private DisputeMng disputeMng;
	@Autowired
	private LedgerMng LedgerMng;
	@Autowired
	private ArchivingMng archivingMng;
	@Autowired
	private UptMng uptMng;
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
	private AttachmentMng attachmentMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	/**
	 * ?????????????????????????????????
	 * 
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-07-04
	 */
	@RequestMapping("/list")
	public String list(ModelMap model) {

		return "/WEB-INF/view/contract/goldpay/goldpay_list";
	}

	/**
	 * ?????????????????????????????????????????????
	 * 
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-07-04
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(GoldPay bean,String sort, String order, Integer page, Integer rows,ModelMap model) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = goldPayMng.find(bean, getUser(), page, rows);
		List<GoldPay> li = (List<GoldPay>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			//li.get(i).setNumber(i + 1);
		}
		return getJsonPagination(p, page);
	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-07-04
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable String id, ModelMap model) {
		ContractBasicInfo contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", contractBasicInfo);
		model.addAttribute("openType", "Gdetail");
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
		//??????????????????
		Upt upt =new Upt();
		List<Upt> uptList = uptMng.findByFContId_U(id);
		if(uptList!=null&&uptList.size()>0){
			upt = uptList.get(0);
			model.addAttribute("Upt", upt);
		}
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
		//?????????????????????????????????id
		String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",contractBasicInfo.getBeanCode());
		//?????????????????????
		GoldPay gold =new GoldPay();
		List<GoldPay> goldList = goldPayMng.findByContId(id,"1");
		if(goldList!=null&&goldList.size()>0){
			gold = goldList.get(0);
			model.addAttribute("goldPay",gold);
		}
		List<Attachment> goldAttaList = attachmentMng.list(gold);
		model.addAttribute("goldAttaList", goldAttaList);
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("?????????????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/contract/goldpay/goldpay_detail";
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2018-07-04
	 */
	@RequestMapping("/returnGold/{id}")
	public String returnGold(@PathVariable String id, ModelMap model) {
		ContractBasicInfo contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", contractBasicInfo);
		model.addAttribute("openType", "Gedit");
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
		int n=1;
		//??????????????????
		Upt upt =new Upt();
		List<Upt> uptList = uptMng.findByFContId_U(id);
		if(uptList!=null&&uptList.size()>0){
			upt = uptList.get(0);
			model.addAttribute("Upt", upt);
			n=n+2;
		}
		//??????????????????
		Dispute dis =new Dispute();
		List<Dispute> disList = disputeMng.findByContId(id);
		if(disList!=null&&disList.size()>0){
			dis = disList.get(0);
			model.addAttribute("dispute",dis);
			n=n+2;
		}
		//??????????????????
		List<Attachment> disputeAttaList = attachmentMng.list(dis);
		model.addAttribute("disputeAttaList", disputeAttaList);
		//??????????????????
		List<Archiving> archivingList = archivingMng.findByContId(id);
		if(archivingList!=null&&archivingList.size()>0){
			model.addAttribute("archiving", archivingList.get(0));
		}
		//?????????????????????
		GoldPay gold =new GoldPay();
		List<GoldPay> goldList = goldPayMng.findByContId(id,"1");
		if(goldList!=null&&goldList.size()>0){
			gold = goldList.get(0);
			model.addAttribute("goldPay",gold);
		}
		List<Attachment> goldAttaList = attachmentMng.list(gold);
		model.addAttribute("goldAttaList", goldAttaList);
		
		model.addAttribute("textClassNum", n);
		//????????????
		//model.addAttribute("CheckInfo",checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",contractBasicInfo.getBeanCode());
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("?????????????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/contract/goldpay/goldpay_update";
	}

	/**
	 * ????????????????????????????????????????????????????????????
	 * 
	 * @param goldPay
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(GoldPay goldPay, GoldPay bean,String files,ModelMap model) {
		try {
			goldPayMng.save(bean, getUser(),files);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
		return getJsonResult(true, "???????????????");
	}
	
	
	
	/**
	 * ?????????????????????????????????????????????list??????
	 * @param model
	 * @return 
	 * @author ?????????
	 * @createtime 2021???1???21???
	 * @updator ?????????
	 * @updatetime 2021???1???21???
	 */
	@RequestMapping(value="/incomeRegistrationList")
	public String incomeRegistrationList(ModelMap model){
		return "/WEB-INF/view/income_manage/securityDeposit/income-goldpay-list";
	}

	/**
	 * ?????????????????????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???1???21???
	 * @updator ?????????
	 * @updatetime 2021???1???21???
	 */
	@RequestMapping("/incomeJsonPagination")
	@ResponseBody
	public JsonPagination incomeJsonPagination(ContractBasicInfo contractBasicInfo,String sort, String order, Integer page, Integer rows,ModelMap model) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = formulationMng.incomeGoldPay(contractBasicInfo, getUser(), page, rows);
		List<ContractBasicInfo> li = (List<ContractBasicInfo>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i + 1);
		}
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ??????
	 * @createtime 2021???3???26???
	 * @updator ??????
	 * @updatetime 2021???3???26???
	 */
	@RequestMapping("/incomeUptJsonPagination")
	@ResponseBody
	public JsonPagination incomeUptJsonPagination(Upt upt,String sort, String order, Integer page, Integer rows,ModelMap model) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = formulationMng.incomeUptGoldPay(upt, getUser(), page, rows);
		List<Upt> li = (List<Upt>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i + 1);
		}
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????? 
	 * @param id ????????????ID
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???1???21???
	 * @updator ?????????
	 * @updatetime 2021???1???21???
	 */
	@RequestMapping(value="/addIncomeRegist/{id}")
	public String addIncomeRegist(@PathVariable Integer id, ModelMap model){
		ContractBasicInfo cbi = formulationMng.findById(id);
		GoldPay gold =new GoldPay();
		List<Upt> uptlist = new ArrayList<Upt>();
		if("1".equals(cbi.getfUpdateStatus())){//?????????
			uptlist = uptMng.findByFContId_U(String.valueOf(cbi.getFcId()));
			gold.setfPayAmount(uptlist.get(0).getfMarginAmount());
		}else{
			gold.setfPayAmount(cbi.getfMarginAmount());
		}
		model.addAttribute("bean", cbi);
		gold.setfContId_GP(cbi.getFcId());
		gold.setContType("0");
		model.addAttribute("goldPay",gold);
		return "/WEB-INF/view/income_manage/securityDeposit/income-goldpay-update";
	}
	
	
	/**
	 * ?????????????????????????????? 
	 * @param id ????????????ID
	 * @param model
	 * @return
	 * @author ??????
	 * @createtime 2021???3???26???
	 * @updator ?????????
	 * @updatetime 2021???3???26???
	 */
	@RequestMapping(value="/addIncomeRegistUpt/{id}")
	public String addIncomeRegistUpt(@PathVariable Integer id, ModelMap model){
		Upt upt = uptMng.findById(id);
		GoldPay gold =new GoldPay();
		gold.setContType("1");
		gold.setfPayAmount(upt.getfMarginAmount());
		gold.setfContId_GP(upt.getfId_U());
		model.addAttribute("goldPay",gold);
		return "/WEB-INF/view/income_manage/securityDeposit/income-goldpay-update";
	}
	/**
	 * ??????????????????
	 * @param id ????????????ID
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???1???21???
	 * @updator ?????????
	 * @updatetime 2021???1???21???
	 */
	@RequestMapping(value="/detailIncomeRegist/{id}")
	public String detailIncomeRegist(@PathVariable Integer id, ModelMap model){
		List<GoldPay> list = goldPayMng.findByContId(id.toString(), "0");
		GoldPay gold = new GoldPay();
		if(list.size()>0){
			gold = list.get(0);
		}
		model.addAttribute("goldPay",gold);
		return "/WEB-INF/view/income_manage/securityDeposit/income-goldpay-detail";
	}
	
	/**
	 * ??????????????????
	 * @param id ????????????ID
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???1???21???
	 * @updator ?????????
	 * @updatetime 2021???1???21???
	 */
	@RequestMapping(value="/editIncomeRegist/{id}")
	public String editIncomeRegist(@PathVariable Integer id, ModelMap model){
		List<GoldPay> list = goldPayMng.findByContId(id.toString(), "0");
		GoldPay gold = new GoldPay();
		if(list.size()>0){
			gold = list.get(0);
		}
		model.addAttribute("goldPay",gold);
		return "/WEB-INF/view/income_manage/securityDeposit/income-goldpay-update";
	}
	
	/**
	 * ???????????????????????????
	 * @param goldPay ?????????
	 * @param contractBasicInfo ??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???1???21???
	 * @updator ?????????
	 * @updatetime 2021???1???21???
	 */
	@RequestMapping("/incomeSave")
	@ResponseBody
	public Result incomeSave(GoldPay goldPay, ContractBasicInfo contractBasicInfo,ModelMap model) {
		try {
			if(goldPayMng.queryHasChanged(goldPay.getfContId_GP())){
				return getJsonResult(false, "??????????????????????????????????????????");
			}else{
				goldPayMng.incomeSave(contractBasicInfo, getUser(), goldPay);
				return getJsonResult(true, "???????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "?????????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????list??????
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???1???22???
	 * @updator ?????????
	 * @updatetime 2021???1???22???
	 */
	@RequestMapping(value="/incomeLedger")
	public String incomeRegistLedgerList(ModelMap model){
		return "/WEB-INF/view/income_manage/securityDeposit/income-goldpay-ledger-list";
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
	 * @author ?????????
	 * @createtime 2021???1???22???
	 * @updator ?????????
	 * @updatetime 2021???1???22???
	 */
	@RequestMapping("/incomeRegistLedgerJson")
	@ResponseBody
	public JsonPagination incomeRegistLedgerJson(ContractBasicInfo contractBasicInfo,String sort, String order, Integer page, Integer rows,ModelMap model) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = formulationMng.incomeGoldPayLedger(contractBasicInfo, getUser(), page, rows);
		List<ContractBasicInfo> li = (List<ContractBasicInfo>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			List<ReimbAppliBasicInfo> reimbAppliBasicInfos = reimbAppliMng.findByPayId(li.get(i).getFcId());
			if(reimbAppliBasicInfos.size()>0){
				li.get(i).setCheckSts(reimbAppliBasicInfos.get(0).getFlowStauts());
				li.get(i).setReimAmount(reimbAppliBasicInfos.get(0).getAmount());
				li.get(i).setReimTime(reimbAppliBasicInfos.get(0).getReimburseReqTime());
			}else{
				li.get(i).setCheckSts(null);
				li.get(i).setReimAmount(null);
				li.get(i).setReimTime(null);
			}
			li.get(i).setNumber(i + 1);
		}
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param Upt
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author ?????????
	 * @createtime 2021???1???22???
	 * @updator ?????????
	 * @updatetime 2021???1???22???
	 */
	@RequestMapping("/incomeUptRegistLedgerJson")
	@ResponseBody
	public JsonPagination incomeUptRegistLedgerJson(Upt upt,String sort, String order, Integer page, Integer rows,ModelMap model) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = formulationMng.incomeUptGoldPayLedger(upt, getUser(), page, rows);
		List<Upt> li = (List<Upt>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			List<ReimbAppliBasicInfo> reimbAppliBasicInfos = reimbAppliMng.findByPayId(li.get(i).getfId_U());
			if(reimbAppliBasicInfos.size()>0){
				li.get(i).setCheckSts(reimbAppliBasicInfos.get(0).getFlowStauts());
				li.get(i).setReimAmount(reimbAppliBasicInfos.get(0).getAmount());
				li.get(i).setReimTime(reimbAppliBasicInfos.get(0).getReimburseReqTime());
			}else{
				li.get(i).setCheckSts(null);
				li.get(i).setReimAmount(null);
				li.get(i).setReimTime(null);
			}
			li.get(i).setNumber(i + 1);
		}
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????????????????????????????
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
	@RequestMapping(value = "/JsonPaginationSealUpt")
	@ResponseBody
	public JsonPagination JsonPaginationSealUpt(Upt upt,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p=formulationMng.queryListJsonSealUpt(upt, getUser(), page, rows);
		List<Upt> li= (List<Upt>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
		p.setList(li);
		return getJsonPagination(p, page);
	}
}
