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
	 * 跳转主页面
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
	 * 显示主页面信息
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
	 * 查询变更合同主页信息数据
	 * @param upt
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月3日
	 * @updator 陈睿超
	 * @updatetime 2020年7月3日
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
	 * 显示合同相关的信息
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
		//合同变更信息
		Upt upt =new Upt();
		if("1".equals(type)){//变更
			upt = uptMng.findById(Integer.valueOf(id));
			contractBasicInfo = formulationMng.findById(upt.getfContId_U());
			//合同变更附件
			List<Attachment> uptAttaList = attachmentMng.list(upt);
			model.addAttribute("changeAttaList", uptAttaList);
		}else if("0".equals(type)){//原合同
			//合同拟定信息
			contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
			// 合同拟定的附件
			List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
			model.addAttribute("formulationAttaList", attaList);
		}
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("Upt", upt);
		//合同备案信息
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(contractBasicInfo);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//签约方信息的附件
		List<Attachment> signInfoattaList = attachmentMng.list(sign);
		model.addAttribute("signInfoAttaList", signInfoattaList);
		//合同备案合同正本附件
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		//判断是不是可以修改状态
		if("1".equals(upt.getfUptFlowStauts())||"9".equals(upt.getfUptFlowStauts())){
			model.addAttribute("uptOpenType", "Cdetail");
		}else {
			model.addAttribute("uptOpenType", "Cedit");
		}
		//付款计划变更
		ReceivPlan receivPlan = new ReceivPlan();
		receivPlan.setfUptId_R(Integer.valueOf(id));
		receivPlan.setDataType(1);
		List<ReceivPlan> rp = filingMng.getReceivPlan(receivPlan);
		model.addAttribute("rpSize",rp.size());
		//采购计划变更
		ContractRegisterList contractRegisterList = new ContractRegisterList();
		contractRegisterList.setfId_U(Integer.valueOf(id));
		contractRegisterList.setfDataType(1);
		List<ContractRegisterList> cr = contractRegisterListMng.getContractRegisterList(contractRegisterList);
		model.addAttribute("crSize",cr.size());
		/*List<Upt> uptList = uptMng.findByFContId_U(id);
		if(uptList!=null&&uptList.size()>0){
			upt = uptList.get(0);
		}*/
		//合同变更附件
		List<Attachment> uptAttaList = attachmentMng.list(upt);
		model.addAttribute("changeAttaList", uptAttaList);
		/*//合同结项的附件
		if( conclusionAttacMng.findAllFile(id).size()>0){
			model.addAttribute("conclusionattac", conclusionAttacMng.findAllFile(id));
		}	*/
		//查询归档信息
		List<Archiving> archivingList = archivingMng.findByContId(id);
		if(archivingList!=null&&archivingList.size()>0){
			model.addAttribute("archiving", archivingList.get(0));
			
			//合同归档附件
			List<Attachment> attaListArc = attachmentMng.list(archivingList.get(0));
			model.addAttribute("attaListArc", attaListArc);
		}
		//查询纠纷记录
		Dispute dis =new Dispute();
		List<Dispute> disList = disputeMng.findByContId(id);
		if(disList!=null&&disList.size()>0){
			dis = disList.get(0);
			model.addAttribute("dispute",dis);
		}
		//合同纠纷附件
		List<Attachment> disputeAttaList = attachmentMng.list(dis);
		model.addAttribute("disputeAttaList", disputeAttaList);
		//审批信息
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("合同归档");
		model.addAttribute("cheterInfo", cheterInfo);
		//取消右侧流程显示
		model.addAttribute("splc", "1");
		if("1".equals(type)){//变更
			//根据申请人得到申请部门id
			String departId=departMng.findDeptByUserId(upt.getfOperatorID())[0];
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编码
			model.addAttribute("foCode",upt.getBeanCode());
			return "/WEB-INF/view/contract/Archiving/archiving_upt_edit";
		}else if("0".equals(type)){//原合同
			//根据申请人得到申请部门id
			String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编码
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
				return getJsonResult(false, "请填写归档位置！");
			}else if(contractBasicInfo.getfPayStauts().equals("0")){
				return getJsonResult(false, "保证金尚未退还，请先退还保证金，退还完成才可进行归档！");
			}else if(receivPlanMng.findUnPay(contractBasicInfo.getFcId()).size()>0){
				return getJsonResult(false, "该合同还有款项未支付，请优先支付款项，支付完成才可进行归档！");
			}else {*/
				ArchivingMng.save(contractBasicInfo, upt, type, archiving, getUser(),files);
				return getJsonResult(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "系统错误请联系管理员！");
		}
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(ModelMap model,@PathVariable String id,String type){
		ContractBasicInfo contractBasicInfo = new ContractBasicInfo();
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("openType", "Adetail");
		model.addAttribute("uptOpenType", "Cdetail");
		//合同变更信息
		Upt upt =new Upt();
		if("1".equals(type)){//变更
			upt = uptMng.findById(Integer.valueOf(id));
			contractBasicInfo = formulationMng.findById(upt.getfContId_U());
			//合同变更附件
			List<Attachment> uptAttaList = attachmentMng.list(upt);
			model.addAttribute("changeAttaList", uptAttaList);
		}else if("0".equals(type)){//原合同
			//合同拟定信息
			contractBasicInfo = formulationMng.findById(Integer.valueOf(id));
			// 合同拟定的附件
			List<Attachment> attaList = attachmentMng.list(contractBasicInfo);
			model.addAttribute("formulationAttaList", attaList);
		}
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("Upt", upt);
		//合同备案信息
		SignInfo sign=new SignInfo();
		List<SignInfo> signInfoList=filingMng.find_Sign(contractBasicInfo);
		if(signInfoList!=null&&signInfoList.size()>0){
			sign = signInfoList.get(0);
			model.addAttribute("signInfo", sign);
		}
		//签约方信息的附件
		List<Attachment> signInfoattaList = attachmentMng.list(sign);
		model.addAttribute("signInfoAttaList", signInfoattaList);
		//合同备案合同正本附件
		if(sign!=null){
			List<Attachment> signattac = attachmentMng.list(sign);
			model.addAttribute("filingattac", signattac);
		}
		/*//合同变更信息
		Upt upt =new Upt();
		List<Upt> uptList = uptMng.findByFContId_U(id);
		if(uptList!=null&&uptList.size()>0){
			upt = uptList.get(0);
			model.addAttribute("Upt", upt);
		}*/
		//付款计划变更
		ReceivPlan receivPlan = new ReceivPlan();
		receivPlan.setfUptId_R(Integer.valueOf(id));
		receivPlan.setDataType(1);
		List<ReceivPlan> rp = filingMng.getReceivPlan(receivPlan);
		model.addAttribute("rpSize",rp.size());
		//采购计划变更
		ContractRegisterList contractRegisterList = new ContractRegisterList();
		contractRegisterList.setfId_U(Integer.valueOf(id));
		contractRegisterList.setfDataType(1);
		List<ContractRegisterList> cr = contractRegisterListMng.getContractRegisterList(contractRegisterList);
		model.addAttribute("crSize",cr.size());
		//查询归档信息
		List<Archiving> archivingList = archivingMng.findByContId(id);
		if(archivingList!=null&&archivingList.size()>0){
			model.addAttribute("archiving", archivingList.get(0));
			//合同归档附件
			List<Attachment> attaListArc = attachmentMng.list(archivingList.get(0));
			model.addAttribute("attaListArc", attaListArc);
		}
		//查询纠纷记录
		Dispute dis =new Dispute();
		List<Dispute> disList = disputeMng.findByContId(id);
		if(disList!=null&&disList.size()>0){
			dis = disList.get(0);
			model.addAttribute("dispute",dis);
		}
		//合同纠纷附件
		List<Attachment> disputeAttaList = attachmentMng.list(dis);
		model.addAttribute("disputeAttaList", disputeAttaList);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("合同归档");
		model.addAttribute("cheterInfo", cheterInfo);
		//取消右侧流程显示
		model.addAttribute("splc", "1");
		if("1".equals(type)){//变更
			//审批信息
			//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
			//根据申请人得到申请部门id
			String departId=departMng.findDeptByUserId(upt.getfOperatorID())[0];
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTBG", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编码
			model.addAttribute("foCode",upt.getBeanCode());
			return "/WEB-INF/view/contract/Archiving/archiving_upt_edit";
		}else if("0".equals(type)){//原合同
			//审批信息
			//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
			//根据申请人得到申请部门id
			String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编码
			model.addAttribute("foCode",contractBasicInfo.getBeanCode());
			return "/WEB-INF/view/contract/Archiving/archiving_edit";
		}
		return null;
	}
	
	/**
	 * 显示添加归档位置时选择合同页面的数据
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
