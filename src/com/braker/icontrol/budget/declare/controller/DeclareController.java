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
 * 预算管理申报库的控制层
 * 本模块用于一上申报审批二上申报审批的控制
 * @author 叶崇晖
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
	private AttachmentMng attachmentMng;//附件
	
	@Autowired
	private TProExpendDetailMng tProExpendDetailMng;//项目支出明细
	
	@Autowired
	private TProPlanMng tProPlanMng;//项目计划
	
	@Autowired
	private TProGoalMng tProGoalMng;//项目绩效总表
	
	@Autowired
	private TProGoalDetailMng tProGoalDetailMng;//项目绩效明细
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/*
	 * 跳转到列表页面
	 * @author 叶崇晖
	 * @createtime 2018-09-20
	 * @updatetime 2018-09-20
	 */
	@RequestMapping(value = "/list")
	public String list(String type, ModelMap model) {
		//type为页面跳转类型（yssb为一上申报、yssp为一上审批、二上申报为essb、二上审批为essp）
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
	 * 审批意见页面跳转
	 * @author 叶崇晖
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
	 * 一上审批页面跳转
	 * @author zhangxun
	 * @createtime 2018-11-09
	 * @updatetime 2018-11-09
	 */
	@RequestMapping("/verdict/{id}")
	public String verdict(@PathVariable String id, ModelMap model){
		
		// 查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("项目申报");
		model.addAttribute("cheterInfo", cheterInfo);
		// 操作类型
		model.addAttribute("operation", "verdict");
		//当前用户
		model.addAttribute("currentUser", getUser());

		TProBasicInfo pro = tProBasicInfoMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", pro);// 项目信息
		model.addAttribute("sbr", pro.getFProAppliPeople());// 申报人
		model.addAttribute("sbbm", pro.getFProAppliDepart());// 申报部门
		if (pro.getFProAppliTime() != null)
			model.addAttribute("sbsj", new SimpleDateFormat("yyyy").format(pro.getFProAppliTime()));// 申报时间
		// 查询附件信息
		List<Attachment> attaList = attachmentMng.list(pro);
		model.addAttribute("attaList", attaList);
		
		// 查询项目支出明细
		List<TProExpendDetail> expDetailList = tProExpendDetailMng
				.getByProId(Integer.valueOf(id));
		model.addAttribute("expDetailList", expDetailList);
		
		/*// 查询项目计划
		List<TProPlan> planList = tProPlanMng.getTProPlansByPro(id);
		model.addAttribute("planList", planList);
		
		// 查询项目绩效目标总表
		List<TProGoal> goalList = tProGoalMng.getTProGoalByPro(id);
		model.addAttribute("goalList", goalList);
		if(goalList.size()>0){
			pro.setGoal(goalList.get(0));
		}
		
		// 查询项目绩效目标明细
		List<TProGoalDetail> goaDetailList = tProGoalDetailMng
				.getMingxi(Integer.valueOf(id));
		model.addAttribute("goaDetailList", goaDetailList);*/
		// 查询历史审批记录
		//List<TProCheckInfo> history = tProCheckInfoMng.getCheckList(pro.getFProId(), "2", "1");
		
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(pro.getUserId(),"YSSB", pro.getFProAppliDepartId(),pro.getBeanCode(),pro.getFExt11(),pro.getJoinTable(),  pro.getBeanCodeField(), pro.getFProCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(pro.getFProAppliPeople(),pro.getFProAppliDepart(), pro.getFProAppliTime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("YSSB", pro.getFProAppliDepartId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",pro.getBeanCode());
		
		return "/WEB-INF/view/budget/project/project-yssp-verdict";
	}
	
	/*
	 * 二上审批页面跳转
	 * @author zhangxun
	 * @createtime 2018-11-12
	 * @updatetime 2018-11-12
	 */
	@RequestMapping("/verdict2/{id}")
	public String verdict2(@PathVariable Integer id, ModelMap model){
		TProBasicInfo pro = tProBasicInfoMng.findById(Integer.valueOf(id));
		// 操作类型
		model.addAttribute("operation", "edit");

		// 查询附件信息
		List<Attachment> attaList = attachmentMng.list(pro);
		model.addAttribute("attaList", attaList);
		// 字段信息
		model.addAttribute("projectCode", pro.getFProCode());// 项目编号
		model.addAttribute("sbr", pro.getFProAppliPeople());// 申报人
		model.addAttribute("sbbm", pro.getFProAppliDepart());// 申报部门
		if (pro.getFProAppliTime() != null)
			model.addAttribute("sbsj",
					new SimpleDateFormat("yyyy").format(pro.getFProAppliTime()));// 申报时间
		model.addAttribute("processCode", TProcessDefin.PROAPPLY);// 流程code
		model.addAttribute("level", pro.getFFlowStauts());// 当前项目审批进度

		// 查询项目支出明细
		List<TProExpendDetail> expDetailList = tProExpendDetailMng
				.getByProId(Integer.valueOf(id));
		model.addAttribute("expDetailList", expDetailList);
	/*	// 查询项目计划
		List<TProPlan> planList = tProPlanMng.getTProPlansByPro(id.toString());
		model.addAttribute("planList", planList);
		// 查询项目绩效目标总表
		List<TProGoal> goalList = tProGoalMng.getTProGoalByPro(id.toString());
		model.addAttribute("goalList", goalList);
		pro.setGoal(goalList.get(0));
		// 查询项目绩效目标明细
		List<TProGoalDetail> goaDetailList = tProGoalDetailMng
				.getMingxi(Integer.valueOf(id));
		model.addAttribute("goaDetailList", goaDetailList);*/

		model.addAttribute("bean", pro);// 项目实体
		String ywfw = null;
		if(pro.getFProOrBasic()==0){
			ywfw = "JBZCESSB";
		}else if(pro.getFProOrBasic()==1){
			ywfw = "ESSB";
		}
		// 查询历史审批记录
		//List<TProCheckInfo> history = tProCheckInfoMng.getCheckList(pro.getFProId(), "3", "1");
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(pro.getUserId(),ywfw, pro.getFProAppliDepartId(),pro.getBeanCode(),pro.getFExt11(),pro.getJoinTable(),  pro.getBeanCodeField(), pro.getFProCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(pro.getFProAppliPeople(),pro.getFProAppliDepart(), pro.getFProAppliTime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(ywfw, pro.getFProAppliDepartId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",pro.getBeanCode());
		// 查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("项目申报");
		model.addAttribute("cheterInfo", cheterInfo);
		return "/WEB-INF/view/budget/project/detail/project-essp-verdict";
	}
	
	/*
	 * 一上申报列表数据查询
	 * @author 叶崇晖
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
	 * 一上批量上报
	 * @author 叶崇晖
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
			return getJsonResult(true,"项目申报成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
	}
	
	/*
	 * 一上审批列表数据查询
	 * @author 叶崇晖
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
	 * 一上审批结果录入
	 * @author 叶崇晖
	 * @createtime 2018-09-21
	 * @updatetime 2018-09-21
	 */
	@RequestMapping("/yssp")
	@ResponseBody
	public Result yssp(String fproIdLi,String result, String remake,String spjlFiles, ModelMap model) {
		try {
			tProCheckInfoMng.firstUpCheck(fproIdLi, result, remake, getUser(), getUser().getRoles().get(0),spjlFiles);
			return getJsonResult(true,"项目审批完成！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
	}
	
	/*
	 * 二上申报列表数据查询
	 * @author 叶崇晖
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
	 * 二上申报数据保存
	 * @author 叶崇晖
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
			
			// 保存项目支出明细
			tProExpendDetailMng.save(bean.getExpendList(), project, currentUser);
			// 保存项目计划
			tProPlanMng.save(bean.getPlanList(), currentUser, project);
			// 保存项目绩效目标总表
			TProGoal goal = tProGoalMng.save(bean.getGoal(), project,currentUser);
			// 保存项目绩效目标明细
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
	 * 二上审批列表数据查询
	 * @author 叶崇晖
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
	 * 二上审批结果录入
	 * @author 叶崇晖
	 * @createtime 2018-09-29
	 * @updatetime 2018-09-29
	 */
	@RequestMapping("/essp")
	@ResponseBody
	public Result essp(String fproIdLi,String result, String remake,String spjlFiles, ModelMap model) {
		try {
			tProCheckInfoMng.secondUpCheck(fproIdLi, result, remake, getUser(), getUser().getRoles().get(0),spjlFiles);
			return getJsonResult(true,"项目审批完成！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
	}
	
	/*
	 * 上报库审批结果信息查询
	 * @author 叶崇晖
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
	    			//序号设置	
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
	* @author:安达
	* @Title: reCall 
	* @Description: 撤回
	* @param id
	* @return
	* @return Result    返回类型 
	* @date： 2019年10月8日下午10:12:32 
	* @throws
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			declareMng.reCall(id);
			return getJsonResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}

	}
}
