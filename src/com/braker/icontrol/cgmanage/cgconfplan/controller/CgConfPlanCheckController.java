package com.braker.icontrol.cgmanage.cgconfplan.controller;

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
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Proposer;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConfPlanCheckMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;
/**
 * 配置计划审批的控制层
 * 本模块用于配置计划申请的审批及查看
 * @author 冉德茂
 * @createtime 2018-10-17
 * @updatetime 2018-12-03
 */
@Controller
@RequestMapping(value = "/cgconfplancheck")
public class CgConfPlanCheckController extends BaseController{
	
	@Autowired
	private CgConPlanMng confplanMng;
	
	@Autowired
	private CgConfPlanCheckMng cgconfcheckplanMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/*
	 * 跳转到列表页面
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/confplan/confplancheck_list";
	}
	
	/*
	 * 分页数据获得
	 * @author 冉德茂
	 * @createtime 2018-07-16
	 * @updatetime 2018-07-16
	 */
	@RequestMapping(value = "/confplancheckPageData")
	@ResponseBody
	public JsonPagination loanPage(ProcurementPlan bean, String sort, String order, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = confplanMng.checkpageList(bean,getUser(), page, rows);;
    	List<ProcurementPlan> li = (List<ProcurementPlan>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}	
	
	/*	
	 * 跳转审批页面
	 * @author 冉德茂
	 * @createtime 2018-07—16
	 * @updatetime 2018-07—16
	 */
	@RequestMapping(value = "/check")
	public String check(String id ,ModelMap model) {
		//传回来的id是主键 fplId
		//查询基本信息				
		ProcurementPlan bean = confplanMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);		
		
		//查询附件信息
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"CGPLANSQ", bean.getFreqDeptId(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getFlistNum(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("CGPLANSQ", bean.getFreqDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getFreqLinkMen(), bean.getFreqDept(), bean.getFreqTime());
		model.addAttribute("proposer", proposer);
		
		return "/WEB-INF/view/purchase_manage/confplan/confplancheck_apply";

	}

	
	
	/*
	 * 进行审核
	 * @author 冉德茂
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,ProcurementPlan bean,String spjlFile) {
		try {
			cgconfcheckplanMng.saveCheckInfo(checkBean, bean, getUser(), spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	

	
}
