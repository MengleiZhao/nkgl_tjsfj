package com.braker.icontrol.incomemanage.disbursement.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.entity.ComboboxBean;
import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.incomemanage.disbursement.manager.ScheduleManager;
import com.braker.icontrol.incomemanage.disbursement.manager.ScheduleProListManager;
import com.braker.icontrol.incomemanage.disbursement.model.Schedule;
import com.braker.icontrol.incomemanage.disbursement.model.ScheduleProList;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

/**
 * 用于用款计划控制层
 * @author 赵孟雷
 */
@Controller
@RequestMapping(value="/schedule")
public class ScheduleController extends BaseController {
	
	
	@Autowired
	private ScheduleManager scheduleManager;
	@Autowired
	private ScheduleProListManager scheduleProListManager;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private TProBasicInfoMng projectMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private DepartMng departMng;
	/**
	 * 跳转到列表页面
	 * @param model
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		model.addAttribute("currentUser", getUser().getId());
		return "/WEB-INF/view/income_manage/disbursement/schedule_list";
	}
	
	
	/**
	 * @param bean
	 * @param page
	 * @param rows
	 * @param sign
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/schedulePage")
	@ResponseBody
	public JsonPagination schedulePage(Schedule bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = scheduleManager.pageList(bean, page, rows, getUser());;
    	return getJsonPagination(p, page);
	}
	
	/**
	 * 跳转新增页面
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping("/add")
	public String add(ModelMap model) {
		Schedule bean = new Schedule();
		//获取当前登录对象获得名称和所属部门
		User user = getUser();
		bean.setScheduleCode(StringUtil.Random("YKJH"));
		bean.setUserName(user.getName());
		bean.setDeptName(user.getDepartName());
		model.addAttribute("bean", bean);
		model.addAttribute("openType", "add");
		
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"YKJH", getUser().getDpID(),null,bean.getnCode(), null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), user.getDepartName(),null);
		model.addAttribute("proposer", proposer);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("收入管理");
		model.addAttribute("cheterInfo", cheterInfo);
		return "/WEB-INF/view/income_manage/disbursement/schedule_add";
	}
	
	
	/**
	 * 用款计划新增
	 * @param bean
	 * @param mingxi
	 * @param model
	 * @param files
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(Schedule bean,String mingxi,ModelMap model,String files) {
		try {
			scheduleManager.save(bean, mingxi, getUser(),files);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
	}
	
	/**
	 * 跳转修改页面
	 * @param id
	 * @param model
	 * @param editType
	 * @author 赵孟雷
	 * @createtime 2021年2月23日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月23日
	 */
	@RequestMapping("/edit")
	public String edit(Integer id, ModelMap model ,String editType) {
		Schedule bean = scheduleManager.findById(id);
		//获取当前登录对象获得名称和所属部门
		if("1".equals(editType)){
			User user = getUser();
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
		}
		model.addAttribute("bean", bean);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"YKJH", bean.getDeptId(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("YKJH", bean.getDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编号
		model.addAttribute("foCode",bean.getBeanCode());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getUserName(), bean.getDeptName(), bean.getReqTime());
		model.addAttribute("proposer", proposer);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("收入管理");
		model.addAttribute("cheterInfo", cheterInfo);
		if("1".equals(editType)){
			model.addAttribute("openType", "edit");
			return "/WEB-INF/view/income_manage/disbursement/schedule_add";
		}
		model.addAttribute("openType", "detail");
		return "/WEB-INF/view/income_manage/disbursement/schedule_detail";
	}
	
	
	/**
	 * @param id
	 * @param fId
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id,String fId) {
		try {
			//传回来的id是主键
			scheduleManager.delete(id,getUser(),fId);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			//传回来的id是主键
			scheduleManager.ApplyReCall(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"撤回失败，请联系管理员！");
		}
		return getJsonResult(true,"撤回成功！");	
	}
	
	/**
	 * 查询新增用款计划时当前部门中的最新项目信息
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/scheduleProQueryNewest")
	@ResponseBody
	public List<TProBasicInfo> scheduleProQueryNewest(String year) {
		List<TProBasicInfo> list = new ArrayList<TProBasicInfo>();
		if(year != null) {
			//查询接待明细
			list = projectMng.yearAndDeptQueryPro(year,getUser().getDepart().getId());
		}
		// 加入排序号
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TProBasicInfo pro = list.get(i);
				List<TBudgetIndexMgr> index = budgetIndexMgrMng.findByProId(String.valueOf(pro.getFProId()));// 根据项目ID查询相应的指标
				BigDecimal pfAmount = BigDecimal.ZERO;
				if(index.size()>0){
					for (int j = 0; j < index.size(); j++) {
						pfAmount = pfAmount.add(BigDecimal.valueOf(index.get(j).getPfAmount()));
					}
				}
				pro.setPfAmount(pfAmount.multiply(new BigDecimal(10000)));
				//序号设置	
				list.get(i).setPageOrder(i+1);
			}
		}
		return list;
	}
	
	/**
	 * 查询新增用款计划时当前部门中的最新项目信息,用于进入修改页面判断是否有变动
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/scheduleProIfChange")
	@ResponseBody
	public Integer scheduleProIfChange(String year) {
		List<TProBasicInfo> list = new ArrayList<TProBasicInfo>();
		if(year != null) {
			//查询接待明细
			list = projectMng.yearAndDeptQueryPro(year,getUser().getDepart().getId());
		}
		return list.size();
	}
	
	/**
	 * 查询用款计划中的项目信息
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/scheduleProQuery")
	@ResponseBody
	public List<ScheduleProList> scheduleProQuery(String sId) {
		List<ScheduleProList> list = new ArrayList<ScheduleProList>();
		if(sId != null) {
			//查询接待明细
			list = scheduleProListManager.getScheduleProList(sId);
		}
		// 加入排序号
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				//序号设置	
				list.get(i).setPageOrder(i+1);
			}
		}
		return list;
	}
	
	/**
	 * 获取指标的年份
	 * @param selected
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping("/getApplyYear")
	@ResponseBody
	public List<ComboboxJson> getApplyYear(String selected) {
		List<ComboboxBean> list = budgetIndexMgrMng.getProAllYear();
		return getComboboxJson(list, selected);
	}
	
	/**
	 * 获取登录用户的所有部门
	 * @param selected
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping("/getApplyDept")
	@ResponseBody
	public List<ComboboxJson> getApplyDept(String selected) {
		if("会计岗".equals(getUser().getRoleName()) || (getUser().getRoleName().contains("处室负责人") && "34".equals(getUser().getDpID()))){
			List<ComboboxBean> list = departMng.getAllDept(getUser());
			return getComboboxJson(list, selected);
		}else{
			List<ComboboxBean> list = departMng.getCurrentUserDept(getUser());
			return getComboboxJson(list, selected);
		}
	}
	
	
	
	/** 以下位置用于用款计划审批使用  */ 
	
	
	/**
	 * 跳转到审批列表页面
	 * @param model
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping(value = "/checkList")
	public String checkList(ModelMap model) {
		return "/WEB-INF/view/income_manage/disbursement/schedule_check_list";
	}
	
	
	
	/**
	 * @param bean
	 * @param page
	 * @param rows
	 * @param sign
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/scheduleCheckPage")
	@ResponseBody
	public JsonPagination scheduleCheckPage(Schedule bean, Integer page, Integer rows,String searchTitle){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = scheduleManager.pageCheckList(bean, page, rows, getUser(),searchTitle);;
    	return getJsonPagination(p, page);
	}	
	
	
	/**
	 * 跳转审批页面
	 * @param id
	 * @param model
	 * @param editType
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping("/check")
	public String check(Integer id, ModelMap model ) {
		Schedule bean = scheduleManager.findById(id);
		//获取当前登录对象获得名称和所属部门
		model.addAttribute("bean", bean);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"YKJH", bean.getDeptId(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("YKJH", bean.getDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编号
		model.addAttribute("foCode",bean.getBeanCode());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getUserName(), bean.getDeptName(), bean.getReqTime());
		model.addAttribute("proposer", proposer);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("收入管理");
		model.addAttribute("cheterInfo", cheterInfo);
		model.addAttribute("openType", "check");
		return "/WEB-INF/view/income_manage/disbursement/schedule_check";
	}	
	
	/**
	 * 审批结果
	 * @param checkBean
	 * @param bean
	 * @param spjlFile
	 * @param xzbgsfiles
	 * @param dwhfiles
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,Schedule bean,String spjlFile) {
		try {
			scheduleManager.saveCheckInfo(checkBean, bean,getUser(),spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	/** 以下位置用于用款计划台账使用  */ 
	
	/**
	 * 跳转到台账列表页面
	 * @param model
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping(value = "/ledgerList")
	public String ledgerList(ModelMap model) {
		return "/WEB-INF/view/income_manage/disbursement/ledger/schedule_ledger_list";
	}
	
	
	/**
	 * 获取台账页面数据
	 * @param bean
	 * @param page
	 * @param rows
	 * @param sign
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	@RequestMapping(value = "/scheduleLedgerPage")
	@ResponseBody
	public JsonPagination scheduleLedgerPage(Schedule bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = scheduleManager.pageLedgerList(bean, page, rows, getUser());
    	return getJsonPagination(p, page);
	}	

	/**
	 * 跳转统计页
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年2月25日
	 * @updator wanping
	 * @updatetime 2021年2月25日
	 */
	@RequestMapping(value = "/statistics")
	public String statistics(ModelMap model) {
		return "/WEB-INF/view/income_manage/disbursement/schedule_statistics_list";
	}
	
	/**
	 * 获取统计数据
	 * @param bean
	 * @param page
	 * @param rows
	 * @return
	 * @author wanping
	 * @createtime 2021年2月25日
	 * @updator wanping
	 * @updatetime 2021年2月25日
	 */
	@RequestMapping(value = "/scheduleStatistics")
	@ResponseBody
	public JsonPagination scheduleStatistics(Schedule bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = 999999;}
    	Pagination p = scheduleManager.scheduleStatistics(bean, page, rows, getUser());;
    	return getJsonPagination(p, page);
	}
	
	/**
	 * 获取所有部门
	 * @param selected
	 * @return
	 * @author wanping
	 * @createtime 2021年2月24日
	 * @updator wanping
	 * @updatetime 2021年2月24日
	 */
	@RequestMapping("/getAllDept")
	@ResponseBody
	public List<ComboboxJson> getAllDept(String selected) {
		List<ComboboxBean> list = departMng.getAllDept(getUser());
		return getComboboxJson(list, selected);
	}
	
	/**
	 * 获取统计年份
	 * @param selected
	 * @return
	 * @author wanping
	 * @createtime 2021年2月26日
	 * @updator wanping
	 * @updatetime 2021年2月26日
	 */
	@RequestMapping("/getStatisticsYear")
	@ResponseBody
	public List<ComboboxJson> getStatisticsYear(String selected) {
		List<ComboboxBean> list = scheduleManager.getStatisticsYear();
		return getComboboxJson(list, selected);
	}
}
