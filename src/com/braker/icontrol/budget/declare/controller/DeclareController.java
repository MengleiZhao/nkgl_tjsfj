package com.braker.icontrol.budget.declare.controller;

import java.text.SimpleDateFormat;
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
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Proposer;
import com.braker.icontrol.budget.declare.manager.DeclareMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.entity.TProGoal;
import com.braker.icontrol.budget.project.entity.TProGoalDetail;
import com.braker.icontrol.budget.project.entity.TProPlan;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProCheckInfoMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.budget.project.manager.TProGoalDetailMng;
import com.braker.icontrol.budget.project.manager.TProGoalMng;
import com.braker.icontrol.budget.project.manager.TProPlanMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;


/**
 * ?????????????????????????????????
 * ????????????????????????????????????????????????????????????
 * @author ?????????
 * @createtime 2018-09-20
 * @updatetime 2018-09-20
 */
@Controller
@RequestMapping(value = "/declare")
public class DeclareController extends BaseController{
	@Autowired
	private RoleMng roleMng;
	
	@Autowired
	private DeclareMng declareMng;
	
	@Autowired
	private TProCheckInfoMng tProCheckInfoMng;
	
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private AttachmentMng attachmentMng;//??????
	
	@Autowired
	private TProExpendDetailMng tProExpendDetailMng;//??????????????????
	
	@Autowired
	private TProPlanMng tProPlanMng;//????????????
	
	@Autowired
	private TProGoalMng tProGoalMng;//??????????????????
	
	@Autowired
	private TProGoalDetailMng tProGoalDetailMng;//??????????????????
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/*
	 * ?????????????????????
	 * @author ?????????
	 * @createtime 2018-09-20
	 * @updatetime 2018-09-20
	 */
	@RequestMapping(value = "/list")
	public String list(String type, ModelMap model) {
		//type????????????????????????yssb??????????????????yssp?????????????????????????????????essb??????????????????essp???
		model.addAttribute("year",Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date()))+1);
		if("yssb".equals(type)) {
			return "/WEB-INF/view/budget/project/project-yssb-list";
		} else if("yssp".equals(type)) {
			return "/WEB-INF/view/budget/project/project-yssp-list";
		} else if("essb".equals(type)) {
			return "/WEB-INF/view/budget/project/project-essb-list";
		} else if("essp".equals(type)) {
			return "/WEB-INF/view/budget/project/project-essp-list";
		} else {
			return "";
		}
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-26
	 * @updatetime 2018-09-26
	 */
	@RequestMapping("/checkRemake")
	public String checkRemake(String type, String result, String data1, ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("result", result);
		model.addAttribute("data1", data1);
		model.addAttribute("time",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		if("yssp".equals(type)) {
			return "/WEB-INF/view/budget/project/detail/check-remake";
		}
		if("essp".equals(type)) {
			return "/WEB-INF/view/budget/project/detail/check-remake";
		}
		return "";
	}
	
	/*
	 * ????????????????????????
	 * @author zhangxun
	 * @createtime 2018-11-09
	 * @updatetime 2018-11-09
	 */
	@RequestMapping("/verdict/{id}")
	public String verdict(@PathVariable String id, ModelMap model){
		
		// ????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		// ????????????
		model.addAttribute("operation", "verdict");
		//????????????
		model.addAttribute("currentUser", getUser());

		TProBasicInfo pro = tProBasicInfoMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", pro);// ????????????
		model.addAttribute("sbr", pro.getFProAppliPeople());// ?????????
		model.addAttribute("sbbm", pro.getFProAppliDepart());// ????????????
		if (pro.getFProAppliTime() != null)
			model.addAttribute("sbsj", new SimpleDateFormat("yyyy").format(pro.getFProAppliTime()));// ????????????
		// ??????????????????
		List<Attachment> attaList = attachmentMng.list(pro);
		model.addAttribute("attaList", attaList);
		
		// ????????????????????????
		List<TProExpendDetail> expDetailList = tProExpendDetailMng
				.getByProId(Integer.valueOf(id));
		model.addAttribute("expDetailList", expDetailList);
		
		/*// ??????????????????
		List<TProPlan> planList = tProPlanMng.getTProPlansByPro(id);
		model.addAttribute("planList", planList);
		
		// ??????????????????????????????
		List<TProGoal> goalList = tProGoalMng.getTProGoalByPro(id);
		model.addAttribute("goalList", goalList);
		if(goalList.size()>0){
			pro.setGoal(goalList.get(0));
		}
		
		// ??????????????????????????????
		List<TProGoalDetail> goaDetailList = tProGoalDetailMng
				.getMingxi(Integer.valueOf(id));
		model.addAttribute("goaDetailList", goaDetailList);*/
		// ????????????????????????
		//List<TProCheckInfo> history = tProCheckInfoMng.getCheckList(pro.getFProId(), "2", "1");
		
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(pro.getUserId(),"YSSB", pro.getFProAppliDepartId(),pro.getBeanCode(),pro.getFExt11(),pro.getJoinTable(),  pro.getBeanCodeField(), pro.getFProCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(pro.getFProAppliPeople(),pro.getFProAppliDepart(), pro.getFProAppliTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("YSSB", pro.getFProAppliDepartId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",pro.getBeanCode());
		
		return "/WEB-INF/view/budget/project/project-yssp-verdict";
	}
	
	/*
	 * ????????????????????????
	 * @author zhangxun
	 * @createtime 2018-11-12
	 * @updatetime 2018-11-12
	 */
	@RequestMapping("/verdict2/{id}")
	public String verdict2(@PathVariable Integer id, ModelMap model){
		TProBasicInfo pro = tProBasicInfoMng.findById(Integer.valueOf(id));
		// ????????????
		model.addAttribute("operation", "edit");

		// ??????????????????
		List<Attachment> attaList = attachmentMng.list(pro);
		model.addAttribute("attaList", attaList);
		// ????????????
		model.addAttribute("projectCode", pro.getFProCode());// ????????????
		model.addAttribute("sbr", pro.getFProAppliPeople());// ?????????
		model.addAttribute("sbbm", pro.getFProAppliDepart());// ????????????
		if (pro.getFProAppliTime() != null)
			model.addAttribute("sbsj",
					new SimpleDateFormat("yyyy").format(pro.getFProAppliTime()));// ????????????
		model.addAttribute("processCode", TProcessDefin.PROAPPLY);// ??????code
		model.addAttribute("level", pro.getFFlowStauts());// ????????????????????????

		// ????????????????????????
		List<TProExpendDetail> expDetailList = tProExpendDetailMng
				.getByProId(Integer.valueOf(id));
		model.addAttribute("expDetailList", expDetailList);
	/*	// ??????????????????
		List<TProPlan> planList = tProPlanMng.getTProPlansByPro(id.toString());
		model.addAttribute("planList", planList);
		// ??????????????????????????????
		List<TProGoal> goalList = tProGoalMng.getTProGoalByPro(id.toString());
		model.addAttribute("goalList", goalList);
		pro.setGoal(goalList.get(0));
		// ??????????????????????????????
		List<TProGoalDetail> goaDetailList = tProGoalDetailMng
				.getMingxi(Integer.valueOf(id));
		model.addAttribute("goaDetailList", goaDetailList);*/

		model.addAttribute("bean", pro);// ????????????
		String ywfw = null;
		if(pro.getFProOrBasic()==0){
			ywfw = "JBZCESSB";
		}else if(pro.getFProOrBasic()==1){
			ywfw = "ESSB";
		}
		// ????????????????????????
		//List<TProCheckInfo> history = tProCheckInfoMng.getCheckList(pro.getFProId(), "3", "1");
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(pro.getUserId(),ywfw, pro.getFProAppliDepartId(),pro.getBeanCode(),pro.getFExt11(),pro.getJoinTable(),  pro.getBeanCodeField(), pro.getFProCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//?????????????????????????????????
		Proposer proposer = new Proposer(pro.getFProAppliPeople(),pro.getFProAppliDepart(), pro.getFProAppliTime());
		model.addAttribute("proposer", proposer);
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(ywfw, pro.getFProAppliDepartId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",pro.getBeanCode());
		// ????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		return "/WEB-INF/view/budget/project/detail/project-essp-verdict";
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-21
	 * @updatetime 2018-09-21
	 */
	@RequestMapping(value = "/yssbProjectPage")
	@ResponseBody
	public List<TProBasicInfo> yssbProjectPage(TProBasicInfo bean, ModelMap model, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	/*Pagination p = declareMng.yssbPageList(bean, page, rows, getUser());*/
    	List<TProBasicInfo> list = declareMng.yssbPageList(bean, page, rows, getUser());
    	if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TProBasicInfo pro = list.get(i);
				pro.setPageOrder(page*rows+i-9);
			}
		}
    	return list;
	}
	
	/*
	 * ??????????????????
	 * @author ?????????
	 * @createtime 2018-09-20
	 * @updatetime 2018-09-20
	 */
	@RequestMapping("/yssb")
	@ResponseBody
	public Result yssb(String fproIdLi,ModelMap model) {
		try {
			if(fproIdLi != null) {
				declareMng.firstUpApply(fproIdLi, getUser());
			}
			return getJsonResult(true,"?????????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-21
	 * @updatetime 2018-09-21
	 */
	@RequestMapping(value = "/ysspProjectPage")
	@ResponseBody
	public JsonPagination ysspProjectPage(TProBasicInfo bean, ModelMap model, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = declareMng.ysspPageList(bean, page, rows, getUser());
    	List<TProBasicInfo> list = (List<TProBasicInfo>) p.getList();
    	if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TProBasicInfo pro = list.get(i);
				pro.setPageOrder(page*rows+i-9);
			}
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-21
	 * @updatetime 2018-09-21
	 */
	@RequestMapping("/yssp")
	@ResponseBody
	public Result yssp(String fproIdLi,String result, String remake,String spjlFiles, ModelMap model) {
		try {
			tProCheckInfoMng.firstUpCheck(fproIdLi, result, remake, getUser(), getUser().getRoles().get(0),spjlFiles);
			return getJsonResult(true,"?????????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-27
	 * @updatetime 2018-09-27
	 */
	@RequestMapping(value = "/essbProjectPage")
	@ResponseBody
	public JsonPagination  essbProjectPage(TProBasicInfo bean, ModelMap model, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = declareMng.essbPageList(bean, page, rows, getUser());
    	List<TProBasicInfo> list = (List<TProBasicInfo>) p.getList();
    	if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TProBasicInfo pro = list.get(i);
				pro.setPageOrder(i+1);
			}
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-28
	 * @updatetime 2018-09-28
	 */
	@RequestMapping("/essb")
	@ResponseBody
	public Result essb(TProBasicInfo bean, ModelMap model, String saveType,
			String fundsJson, String lxyjFiles, String ssfaFiles,String jxmbFiles,String delIndex) {
		try {
			declareMng.secondUpApply(bean, getUser(), saveType, lxyjFiles, ssfaFiles,jxmbFiles,fundsJson,delIndex);
			
			/*User currentUser = userMng.findById(project.getFProAppliPeopleId());
			
			// ????????????????????????
			tProExpendDetailMng.save(bean.getExpendList(), project, currentUser);
			// ??????????????????
			tProPlanMng.save(bean.getPlanList(), currentUser, project);
			// ??????????????????????????????
			TProGoal goal = tProGoalMng.save(bean.getGoal(), project,currentUser);
			// ??????????????????????????????
			JSONArray array = JSONArray.fromObject("[" + jxmingxi.toString()+ "]");
			List<TProGoalDetail> li = JSONArray.toList(array,TProGoalDetail.class);
			tProGoalDetailMng.save(li, goal, currentUser);*/
			
			return getJsonResult(true,Result.saveSuccessMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,Result.saveFailureMessage);
		}
		
	}
	
	/*
	 * ??????????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-28
	 * @updatetime 2018-09-28
	 */
	@RequestMapping(value = "/esspProjectPage")
	@ResponseBody
	public JsonPagination  esspProjectPage(TProBasicInfo bean, ModelMap model, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = declareMng.esspPageList(bean, page, rows, getUser());
    	List<TProBasicInfo> list = (List<TProBasicInfo>) p.getList();
    	if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TProBasicInfo pro = list.get(i);
				pro.setPageOrder(page*rows+i-9);
			}
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * ????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-29
	 * @updatetime 2018-09-29
	 */
	@RequestMapping("/essp")
	@ResponseBody
	public Result essp(String fproIdLi,String result, String remake,String spjlFiles, ModelMap model) {
		try {
			tProCheckInfoMng.secondUpCheck(fproIdLi, result, remake, getUser(), getUser().getRoles().get(0),spjlFiles);
			return getJsonResult(true,"?????????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"????????????????????????????????????");
		}
	}
	
	/*
	 * ?????????????????????????????????
	 * @author ?????????
	 * @createtime 2018-09-25
	 * @updatetime 2018-09-25
	 */
	@RequestMapping(value = "/checkInfoPage")
	@ResponseBody
	public JsonPagination checkInfoPage(TProcessCheck bean , Integer page, Integer rows, String type) {
		try {
			if(page == null){page = 1;}
	    	if(rows == null){rows = SimplePage.DEF_COUNT;}
	    	Pagination p = tProCheckInfoMng.checkInfoPageList(bean,page, rows, getUser(), type);
	    	List<TProcessCheck> li = (List<TProcessCheck>) p.getList();
	    	for(int x=0; x<li.size(); x++) {
	    		TProBasicInfo pro = tProBasicInfoMng.findbyCode(li.get(x).getFoCode());
	    		if(pro!=null) {
	    			//????????????	
	    			li.get(x).setNum((x+1)+(page-1)*rows);
	    			li.get(x).setFProCode(pro.getFProCode());
	    			li.get(x).setFProName(pro.getFProName());
	    			li.get(x).setFProHead(pro.getFProHead());
	    			li.get(x).setFProAppliTime(pro.getFProAppliTime());
	    		}
			}
	    	p.setList(li);
	    	return getJsonPagination(p, page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 
	* @author:??????
	* @Title: reCall 
	* @Description: ??????
	* @param id
	* @return
	* @return Result    ???????????? 
	* @date??? 2019???10???8?????????10:12:32 
	* @throws
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			declareMng.reCall(id);
			return getJsonResult(true, "????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}

	}
}
