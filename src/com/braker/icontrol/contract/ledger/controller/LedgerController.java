package com.braker.icontrol.contract.ledger.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.CheterInfo;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.archiving.manager.ArchivingMng;
import com.braker.icontrol.contract.archiving.model.Archiving;
import com.braker.icontrol.contract.dispute.manager.DisputAttacMng;
import com.braker.icontrol.contract.dispute.manager.DisputeMng;
import com.braker.icontrol.contract.dispute.model.Dispute;
import com.braker.icontrol.contract.ending.manager.EndingMng;
import com.braker.icontrol.contract.ending.model.Ending;
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
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/Ledger")
public class LedgerController extends BaseController{

	@Autowired
	private LedgerMng LedgerMng;
	@Autowired
	private SignInfoMng signInfoMng;
	@Autowired
	private ArchivingMng archivingMng;
	@Autowired
	private UptMng uptMng;
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
	private GoldPayMng goldPayMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private EndingMng endingMng;
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	/**
	 * ???????????????
	 * @param model
	 * @return
	 * @createtime 2018-07-03
	 * @author ?????????
	 */
	@RequestMapping("/list")
	public String list(ModelMap model,String proId){
		if(!StringUtil.isEmpty(proId)){
			TProBasicInfo tpbi=tProBasicInfoMng.findById(Integer.valueOf(proId));
			TBudgetIndexMgr bim = budgetIndexMgrMng.findByIndexCode(tpbi.getFProCode());
			
			model.addAttribute("fBudgetIndexCode", bim.getbId());
		}
		return "/WEB-INF/view/contract/ledger/ledger_list";
	}
	
	/**
	 * ??????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		SimpleDateFormat date = new SimpleDateFormat("yyyy???MM???dd???");
		Pagination p = LedgerMng.findAllCBI(contractBasicInfo, true, getUser(), page, rows);
		List<ContractBasicInfo> li= (List<ContractBasicInfo>) p.getList();
		if (li != null && li.size() > 0) {
			int i = 0;
			for (ContractBasicInfo tb: li) {
				Double amount = tb.getfPlanTotalAmount();
				Double allAmount = 0.0;
				if(tb.getFcType().equals("HTFL-01")){
					if("1".equals(tb.getfUpdateStatus())){
						List<Upt> afterUpdateAmountList = LedgerMng.findAfterUpdateAmount(tb.getFcCode());
						if(afterUpdateAmountList.size() > 0){
							for (Upt u:afterUpdateAmountList){
								amount = Double.valueOf(u.getFcAmount());
							}
						}
						tb.setfPlanTotalAmount(amount);
					}
					List<ReimbAppliBasicInfo> allAmountList = LedgerMng.findAllAmount(tb.getFcCode());
					if(allAmountList.size() > 0){
						for (ReimbAppliBasicInfo rabi:allAmountList){
							if(!"11".equals(rabi.getType())){
								allAmount += rabi.getAmount();
							}
						}
					}
					//				tb.setKeepTime(DateUtil.formatDate(tb.getfContStartTime())+"-"+DateUtil.formatDate(tb.getfContEndTime()));


					tb.setfAllAmount(String.valueOf(allAmount));
					tb.setfNotAllAmountL(String.valueOf(amount-allAmount));
				}
				tb.setKeepTime(date.format(tb.getfContStartTime())+"-"+date.format(tb.getfContEndTime()));
				tb.setNumber(page * rows + i - 9);
				i++;
			}
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
	 * @createtime 2020???6???22???
	 * @updator ?????????
	 * @updatetime 2020???6???22???
	 */
	@RequestMapping("/ChangeJsonPagination")
	@ResponseBody
	public JsonPagination ChangeJsonPagination(Upt upt,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = LedgerMng.uptList(upt, getUser(), page, rows);
		List<Upt> li= (List<Upt>) p.getList();
		if (li != null && li.size() > 0) {
			int i = 0;
			for (Upt tb: li) {
				tb.setNumber(page * rows + i - 9);
				i++;
			}
		}
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ??????
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable String id,ModelMap model ){
		ContractBasicInfo contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("openType", "detail");
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
		//?????????????????????
		GoldPay gold =new GoldPay();
		List<GoldPay> goldList = goldPayMng.findByContId(id,"1");
		if(goldList!=null&&goldList.size()>0){
			gold = goldList.get(0);
			model.addAttribute("goldPay",gold);
		}
		List<Attachment> goldAttaList = attachmentMng.list(gold);
		model.addAttribute("goldAttaList", goldAttaList);
		//????????????
		List<Ending> li = endingMng.find1("contractBasicInfo.fcId", String.valueOf(contractBasicInfo.getFcId()), "1");
		Ending end=new Ending();
		if(li!=null&&li.size()>0){
			model.addAttribute("end", li.get(0));
			end=li.get(0);;
		}
		//??????????????????
		List<Attachment> endingattac = attachmentMng.list(end);
		model.addAttribute("endingAttaList", endingattac);
		//????????????
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",contractBasicInfo.getBeanCode());	
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/contract/ledger/ledger_detail";
	}
	
	/**
	 * ??????
	 * @param model
	 * @param response
	 * @param request
	 * @param bean
	 * @param currentYear
	 * @return
	 */
	@RequestMapping("/export")
	public String export(ModelMap model,String type , HttpServletResponse response, HttpServletRequest request,String name, String currentYear){
		OutputStream out = null;
		try {
			//??????excel?????????
			if("0".equals(type)){
				//????????????
				ContractBasicInfo basicInfo = new ContractBasicInfo();
				basicInfo.setSearchTitle(StringUtil.setUTF8(name));
				List<ContractBasicInfo> ledgerData = LedgerMng.ledger(basicInfo,getUser());
				//?????????
				response.setHeader("Content-Disposition","attachment; filename="+new String("HTTZ.xls".getBytes("gb2312"), "iso8859-1"));   
				out = new BufferedOutputStream(response.getOutputStream());   
				response.setContentType("application/octet-stream");   
				String path = request.getSession().getServletContext().getRealPath("/resource");
				String filePath=path+"/download/???????????????????????????.xls";
				filePath = filePath.replace("\\","/");
				HSSFWorkbook workbook = LedgerMng.exportExcel(ledgerData, filePath);
				if(workbook==null){
					out.flush();   
					return null;
				}
				workbook.write(out);   
				out.flush(); 
			}else{
				Upt upt = new Upt();
				upt.setSearchTitle(StringUtil.setUTF8(name));
				List<Upt> uptData = LedgerMng.ledgerUpt(upt,getUser());
				//?????????
				response.setHeader("Content-Disposition","attachment; filename="+new String("BGHTTZ.xls".getBytes("gb2312"), "iso8859-1"));   
				out = new BufferedOutputStream(response.getOutputStream());   
				response.setContentType("application/octet-stream");   
				String path = request.getSession().getServletContext().getRealPath("/resource");
				String filePath=path+"/download/???????????????????????????????????????.xls";
				filePath = filePath.replace("\\","/");
				HSSFWorkbook workbook = LedgerMng.exportExcelUpt(uptData, filePath);
				if(workbook==null){
					out.flush();   
					return null;
				}
				workbook.write(out);   
				out.flush(); 
			}
			  
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Title finishContract 
	 * @Description ????????????
	 * @author ??????
	 * @Date 2020???3???10??? 
	 * @param fcIds
	 * @return
	 * @return Result
	 * @throws
	 */
	@RequestMapping(value = "/finishContract")
	@ResponseBody
	public Result finishContract(String fcIds) {
		try {
			boolean flag = LedgerMng.finishContract(fcIds);
			if (flag) {
				return getJsonResult(true, "???????????????");
			} 
			return getJsonResult(false, "????????????????????????????????????????????????");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
