package com.braker.icontrol.incomemanage.businessService.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.expend.history.manager.CheckHistoryMng;
import com.braker.icontrol.incomemanage.businessService.manager.BusinessServiceMng;
import com.braker.icontrol.incomemanage.businessService.model.BusinessServiceInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 经营服务性收入立项模块控制层
 * @author 陈睿超
 *
 */
@Controller
@RequestMapping(value = "/business")
public class BusinessServiceController extends BaseController{
	@Autowired
	private BusinessServiceMng businessServiceMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private CheckHistoryMng checkHistoryMng;
	
	/**
	 * 跳转到list申请页面
	 * @return
	 * @author 陈睿超
	 * @createTime2019年11月1日
	 * @updateTime2019年11月1日
	 */
	@RequestMapping("/list")
	public String list(){
		return "/WEB-INF/view/income_manage/businessService/business_list";
	}
	
	/**
	 * 加载申请数据待查询
	 * @param model
	 * @param bean
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @return
	 * @author 陈睿超
	 * @createTime2019年11月1日
	 * @updateTime2019年11月1日
	 */
	@ResponseBody
	@RequestMapping("/jsonPagination")
	public JsonPagination jsonPagination(ModelMap model, BusinessServiceInfo bean, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = businessServiceMng.pageList(bean, page, rows, getUser());
		return getJsonPagination(p, page);
	}
	
	/**
	 * 
	 * @Description: 新增经营服务性收入
	 * @author 汪耀
	 * @param @param model
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/add")
	public String add(ModelMap model){
		BusinessServiceInfo bean = new BusinessServiceInfo();
		//自动生成单号
		String str = "BUSI";
		bean.setfBusiCode(StringUtil.Random(str));
		//自动生成申请人、申请部门
		bean.setfOperatorId(getUser().getId());
		bean.setfOperatorName(getUser().getName());
		bean.setfDeptId(getUser().getDpID());
		bean.setfDeptName(getUser().getDepartName());
		bean.setfBusiTime(new Date());
		model.addAttribute("bean", bean);
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "JYFWXSR", getUser().getDpID(), null, bean.getfNextCode(), null, null, null, null);
		model.addAttribute("nodeConf", nodeConfList);
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/income_manage/businessService/business_add";
	}
	
	/**
	 * 
	 * @Description: 经营服务性收入的详情
	 * @author 汪耀
	 * @param @param id
	 * @param @return    
	 * @return List<Object>
	 */
	@RequestMapping(value = "/addDetails")
	@ResponseBody
	public List<Object> addDetails(Integer id) {
		List<Object> detailsList = new ArrayList<Object>();
		if(id != null) {		
			//查询申请明细
			detailsList = businessServiceMng.getDetailsList("BusinessServiceDetails", "fbId", id);
		}
		return detailsList;
	}
	
	/**
	 * 
	 * @Description: 保存经营服务性收入信息
	 * @author 汪耀
	 * @param @param bean
	 * @param @param files
	 * @param @param mingxi
	 * @param @return    
	 * @return Result
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(BusinessServiceInfo bean, String files, String mingxi){
		try {
			businessServiceMng.save(bean, files, mingxi, getUser());
		} catch (Exception e) {
			return getJsonResult(false, "操作失败，请联系管理员！");
		}
		return getJsonResult(true, "操作成功！");
	}
	
	/**
	 * 
	 * @Description: 修改经营服务性收入信息
	 * @author 汪耀
	 * @param @param model
	 * @param @param id
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/edit")
	public String edit(ModelMap model, String id){
		//经营性服务项目基本信息
		BusinessServiceInfo bean = businessServiceMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		//经营性服务项目附件
		List<Attachment> businessAttaList = attachmentMng.list(bean);
		model.addAttribute("businessAttaList", businessAttaList);
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getfOperatorId(), "JYFWXSR", bean.getfDeptId(), bean.getBeanCode(), bean.getfNextCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("JYFWXSR", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getfOperatorName(), bean.getfDeptName(), bean.getfBusiTime());
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/income_manage/businessService/business_add";
	}
	
	/**
	 * 
	 * @Description: 查看经营服务性收入信息
	 * @author 汪耀
	 * @param @param model
	 * @param @param id
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/detail")
	public String detail(ModelMap model, String id){
		//经营性服务项目基本信息
		BusinessServiceInfo bean = businessServiceMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		//经营性服务项目附件
		List<Attachment> businessAttaList = attachmentMng.list(bean);
		model.addAttribute("businessAttaList", businessAttaList);
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getfOperatorId(), "JYFWXSR", bean.getfDeptId(), bean.getBeanCode(), bean.getfNextCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("JYFWXSR", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getfOperatorName(), bean.getfDeptName(), bean.getfBusiTime());
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/income_manage/businessService/business_detail";
	}
	
	/**
	 * 
	 * @Description: 删除经营服务性收入信息
	 * @author 汪耀
	 * @param @param id
	 * @param @return    
	 * @return Result
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id) {
		try {
			businessServiceMng.delete(id);
		} catch (Exception e) {
			return getJsonResult(false, "删除失败，请联系管理员！");
		}
		return getJsonResult(true, "删除成功！");	
	}
	
	/**
	 * 
	 * @Description: 跳转审批列表页面
	 * @author 汪耀
	 * @param @param model
	 * @param @return    
	 * @return String
	 */
	@RequestMapping(value = "/checkList")
	public String checkList(ModelMap model){
		return "/WEB-INF/view/income_manage/businessService/business_check_list";
	}
	
	/**
	 * 
	 * @Description: 跳转审批页面
	 * @author 汪耀
	 * @param @param model
	 * @param @param id
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/check")
	public String check(ModelMap model, String id){
		//经营性服务项目基本信息
		BusinessServiceInfo bean = businessServiceMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		//经营性服务项目附件
		List<Attachment> businessAttaList = attachmentMng.list(bean);
		model.addAttribute("businessAttaList", businessAttaList);
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getfOperatorId(), "JYFWXSR", bean.getfDeptId(), bean.getBeanCode(), bean.getfNextCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("JYFWXSR", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getfOperatorName(), bean.getfDeptName(), bean.getfBusiTime());
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/income_manage/businessService/business_check";
	}
	
	/**
	 * 
	 * @Description: 进行审核
	 * @author 汪耀
	 * @param @param checkBean
	 * @param @param bean
	 * @param @param spjlFile
	 * @param @return    
	 * @return Result
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean, BusinessServiceInfo bean, String spjlFile){
		try {
			businessServiceMng.saveCheck(checkBean, bean, spjlFile, getUser());
		} catch (Exception e) {
			return getJsonResult(true, "操作失败，请联系管理员！");
		}
		return getJsonResult(true, "操作成功！");
	}
}
