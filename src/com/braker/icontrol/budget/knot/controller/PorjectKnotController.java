package com.braker.icontrol.budget.knot.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.MatheUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Proposer;
import com.braker.icontrol.budget.knot.entity.TPorjectKnot;
import com.braker.icontrol.budget.knot.manager.PorjectKnotMng;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping("/projectknot")
public class PorjectKnotController extends BaseController{
	@Autowired
	private PorjectKnotMng porjectKnotMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private TProBasicInfoMng projectMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	
	/**
	 * /projectknot/list
	* @author:??????
	* @Title: list 
	* @Description:????????????????????????
	* @param model
	* @param proLibType
	* @param sbkLx
	* @return
	* @return String    ???????????? 
	* @date??? 2019???6???12?????????9:44:06 
	* @throws
	 */
	@RequestMapping("/list")
	public String list(ModelMap model, String proLibType, String sbkLx) {
		return "/WEB-INF/view/budget/project/project-knot-check-list";
	}
	/**
	 * /projectknot/JsonPagination
	* @author:??????
	* @Title: jsonPagination 
	* @Description: ??????
	* @param bean
	* @param sort
	* @param order
	* @param page
	* @param rows
	* @param model
	* @return
	* @return JsonPagination    ???????????? 
	* @date??? 2019???6???11?????????10:12:00 
	* @throws
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(TPorjectKnot bean,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	Pagination pagination = porjectKnotMng.pageList(bean,getUser(), page, rows);
    	List<TPorjectKnot> list=(List<TPorjectKnot>) pagination.getList();
    	for (int i = 0; i < list.size(); i++) {
    		list.get(i).setNum(i+1);
		}
    	pagination.setList(list);
		return getJsonPagination(pagination, page);
	}
	/**
	 * /projectknot/knot
	* @author:??????
	* @Title: add 
	* @Description: ???????????????
	* @param model
	* @param id
	* @param request
	* @return
	* @return String    ???????????? 
	* @date??? 2019???6???11?????????10:11:50 
	* @throws
	 */
	@RequestMapping("/knot")
	public String knot(ModelMap model,String id, HttpServletRequest request){
		try {
			model.addAttribute("operType", "add");
			TProBasicInfo pro = projectMng.findById(Integer.valueOf(id));
			TPorjectKnot bean = new TPorjectKnot();
			//??????????????????????????????????????????
			bean.setFreqDept(getUser().getDepartName());
			bean.setFreqDeptId(getUser().getDepart().getId());
			bean.setFreqUserName(getUser().getName());	
			bean.setFreqUserId(getUser().getId());	
			bean.setFProCode(pro.getFProCode());
			bean.setFProId(pro.getBeanId());
			bean.setFProName(pro.getFProName());
			bean.setFirstLevelCode(pro.getFirstLevelCode());
			bean.setFProBudgetAmount(pro.getFProBudgetAmount());
			//????????????
			bean.setFreqTime(new Date());
			bean.setProEndTime(new Date());
			/*String planStartYear=pro.getPlanStartYear();
			Calendar calendar=Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.parseInt(planStartYear));
			calendar.set(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, 1);
			Calendar.getInstance().getTimeInMillis();
			bean.setProBeginTime(calendar.getTime());*/
			//??????????????????
			String str="JX";
			bean.setFKnotCode(StringUtil.Random(str));	
			TBudgetIndexMgr index = budgetIndexMgrMng
					.findByIndexCode(pro.getFProCode());// ???????????????????????????????????????
			Double pfAmount = index.getPfAmount();
			Double syAmount = index.getSyAmount();
			Double djAmount = index.getDjAmount();
			Double efficiency = (1 - (syAmount / pfAmount)) * 100;

			BigDecimal bg1 = new BigDecimal(efficiency);
			efficiency = bg1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

			bean.setFProEfficiency(efficiency);
			
			Double value1=MatheUtil.sub(pfAmount, syAmount);
			Double outAmount=MatheUtil.sub(value1, djAmount);
			bean.setFProOutAmount(outAmount);
			model.addAttribute("djAmount", 0);
			if(djAmount !=0){
				model.addAttribute("djAmount", djAmount);
			}
			
			model.addAttribute("bean", bean);
			//???????????????
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(getUser().getId(),"JXSQ", getUser().getDpID(),null,bean.getnCode(), null, null, null, "1");
			model.addAttribute("nodeConf", nodeConfList);
			//?????????????????????????????????
			Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
			model.addAttribute("proposer", proposer);
			return "/WEB-INF/view/budget/project/project-knot-add";
		} catch (Exception e) {
			return getException(e);
			//return "/WEB-INF/view/budget/project/project-knot-add";
		}
		
	}
	/**
	 * /projectknot/edit
	* @author:??????
	* @Title: edit 
	* @Description: ??????
	* @param model
	* @param id
	* @param request
	* @return
	* @return String    ???????????? 
	* @date??? 2019???6???11?????????10:11:43 
	* @throws
	 */
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id,String proId, HttpServletRequest request){
		TPorjectKnot bean =new TPorjectKnot();
		if(StringUtil.isEmpty(id)){
			List<TPorjectKnot> knotList=porjectKnotMng.findByProperty("FProId", Integer.parseInt(proId));
			if(knotList!=null && knotList.size()>0){
				bean=knotList.get(0);
			}
		}else{
			bean = porjectKnotMng.findById(Integer.valueOf(id));
		}
		model.addAttribute("openType", "edit");
		model.addAttribute("bean", bean);
		//??????????????????
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"JXSQ", getUser().getDpID(),null,bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getFKnotCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getFreqUserName(), bean.getFreqDept(), bean.getFreqTime());
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/budget/project/project-knot-add";
	}
	/**
	 * /projectknot/check
	* @author:??????
	* @Title: check 
	* @Description: ??????????????????
	* @param id
	* @param model
	* @return
	* @return String    ???????????? 
	* @date??? 2019???6???11?????????10:11:24 
	* @throws
	 */
	@RequestMapping("/check")
	public String check(String id ,ModelMap model) {
		//????????????id????????? fplId
		//??????????????????				
		TPorjectKnot bean = porjectKnotMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);		
		
		//??????????????????
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"JXSQ", bean.getFreqDeptId(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(),  bean.getBeanCodeField(), bean.getFKnotCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("JXSQ", bean.getFreqDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",bean.getBeanCode());
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getFreqUserName(), bean.getFreqDept(), bean.getFreqTime());
		model.addAttribute("proposer", proposer);
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		
		return "/WEB-INF/view/budget/project/project-knot-check";

	}

	
	/**
	 * /projectknot/save
	* @author:??????
	* @Title: save 
	* @Description: ???????????????????????????
	* @param bean
	* @param dc
	* @param dr
	* @return
	* @return Result    ???????????? 
	* @date??? 2019???6???20?????????2:56:23 
	* @throws
	 */
	@RequestMapping("/save")
	@ResponseBody
	//public Result save(TPorjectKnot bean, String operType, String files) {
	public Result save(Integer fkId,Integer FProId,Date proBeginTime,Date proEndTime ,String FProMemo,String flowStauts, String operType, String files) {
		try {
			TProBasicInfo pro = projectMng.findById(FProId);
			TPorjectKnot bean = new TPorjectKnot();
			//??????????????????????????????????????????
			bean.setFreqDept(getUser().getDepartName());
			bean.setFreqDeptId(getUser().getDepart().getId());
			bean.setFreqUserName(getUser().getName());	
			bean.setFreqUserId(getUser().getId());	
			bean.setFProCode(pro.getFProCode());
			bean.setFProId(pro.getBeanId());
			bean.setFProName(pro.getFProName());
			bean.setFirstLevelCode(pro.getFirstLevelCode());
			bean.setFProBudgetAmount(pro.getFProBudgetAmount());
			//????????????
			bean.setFreqTime(new Date());
			//??????????????????
			String str="JX";
			bean.setFKnotCode(StringUtil.Random(str));	
			TBudgetIndexMgr index = budgetIndexMgrMng
					.findByIndexCode(pro.getFProCode());// ???????????????????????????????????????
			Double pfAmount = index.getPfAmount();
			Double syAmount = index.getSyAmount();
			Double djAmount = index.getDjAmount();
			Double efficiency = (1 - (syAmount / pfAmount)) * 100;

			BigDecimal bg1 = new BigDecimal(efficiency);
			efficiency = bg1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

			bean.setFProEfficiency(efficiency);
			bean.setFProOutAmount(pfAmount-syAmount-djAmount);
			bean.setProBeginTime(proBeginTime);
			bean.setProEndTime(proEndTime);
			bean.setFProMemo(FProMemo);
			bean.setFlowStauts(flowStauts);
			porjectKnotMng.save(bean, getUser(),operType,files);
			
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, Result.saveSuccessMessage);
		}
		return getJsonResult(true, Result.saveSuccessMessage);
	}
	/**
	 * /projectknot/checkResult
	* @author:??????
	* @Title: checkResult 
	* @Description: ????????????
	* @param checkBean
	* @param bean
	* @param spjlFile
	* @return
	* @return Result    ???????????? 
	* @date??? 2019???6???11?????????10:11:13 
	* @throws
	 */
	@RequestMapping("/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,Integer fkId,Integer FProId,String spjlFile) {
		try {
			 TPorjectKnot bean = new TPorjectKnot();
			 bean.setFkId(fkId);
			 bean.setFProId(FProId);
			porjectKnotMng.check(checkBean, bean, getUser(), spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "???????????????");
	}
	/**
	 * /projectknot/detail
	* @author:??????
	* @Title: detail 
	* @Description: ??????
	* @param id
	* @param model
	* @return
	* @return String    ???????????? 
	* @date??? 2019???6???11?????????10:15:50 
	* @throws
	 */
	@RequestMapping("/detail")
	public String detail(String id,Integer proId,ModelMap model){
		TPorjectKnot bean =new TPorjectKnot();
		if(StringUtil.isEmpty(id)){
			List<TPorjectKnot> knotList=porjectKnotMng.findByProperty("FProId", proId);
			if(knotList!=null && knotList.size()>0){
				bean=knotList.get(0);
			}
		}else{
			bean = porjectKnotMng.findById(Integer.valueOf(id));
		}
		//??????????????????
		model.addAttribute("bean",bean);
		//??????????????????
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//???????????????
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"JXSQ", getUser().getDpID(),null,bean.getnCode(),bean.getJoinTable(),  bean.getBeanCodeField(), bean.getFKnotCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(bean.getFreqUserName(), bean.getFreqDept(), bean.getFreqTime());
		model.addAttribute("proposer", proposer);
		
		return "/WEB-INF/view/budget/project/project-knot-detail";
	}
}
