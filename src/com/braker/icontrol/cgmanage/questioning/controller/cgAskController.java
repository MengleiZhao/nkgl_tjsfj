package com.braker.icontrol.cgmanage.questioning.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
import com.braker.core.model.Proposer;
import com.braker.core.model.Role;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.questioning.manager.PurchasingQueryMng;
import com.braker.icontrol.cgmanage.questioning.model.PurchasingQuery;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping(value = "/cgAsk")
public class cgAskController extends BaseController{
	
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private PurchasingQueryMng purchasingQueryMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	/*
	 * 跳转到列表页面
	 * @author 方淳洲
	 * @createtime 2021-03-13
	 * @updatetime 2021-03-13
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/questioning/cgAsk_list";
	}
	
	/*
	 * 跳转到答复列表页面
	 * @author 沈帆
	 * @createtime 2021-03-22
	 * @updatetime 2021-03-22
	 */
	@RequestMapping(value = "/AnswerList")
	public String AnswerList(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/questioning/cgAnswer/cgAnswerApply_list";
	}

	/*
	 * 跳转到答复审批列表页面
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@RequestMapping(value = "/AnswerCheckList")
	public String AnswerCheckList(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/questioning/cgAnswer/cgAnswerApplyCheck_list";
	}
	
	/*
	 * 跳转到采购列表选择页面
	 * @author 沈帆
	 * @createtime 2021-03-17
	 * @updatetime 2021-03-17
	 */
	@RequestMapping(value = "/chooseCgList")
	public String chooseCgList(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/questioning/cgChoose_list";
	}
	
	/*
	 * 跳转到质疑记录列表页面
	 * @author 沈帆
	 * @createtime 2021-03-19
	 * @updatetime 2021-03-19
	 */
	@RequestMapping(value = "/detailList")
	public String list(ModelMap model,Integer fpId) {
		model.addAttribute("fpId", fpId);
		return "/WEB-INF/view/purchase_manage/questioning/cgAsk_detail_list";
	}
	
	/*
	 * 分页数据获得
	 * @author 方淳洲
	 * @createtime 2021-03-13
	 * @updatetime 2021-03-13
	 */
	@RequestMapping(value = "/cgAskPage")
	@ResponseBody
	public JsonPagination cgAskPage(PurchasingQuery bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = purchasingQueryMng.pageList(bean, page, rows, getUser(),searchData);
    	List<PurchasingQuery> li = (List<PurchasingQuery>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * 分页数据获得
	 * @author 方淳洲
	 * @createtime 2021-03-13
	 * @updatetime 2021-03-13
	 */
	@RequestMapping(value = "/cgQusetionPage")
	@ResponseBody
	public JsonPagination cgQusetionPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.cgQusetionPage(bean, page, rows,searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	
	/*
	 * 答复申请分页数据获得
	 * @author 沈帆
	 * @createtime 2021-03-22
	 * @updatetime 2021-03-22
	 */
	@RequestMapping(value = "/answerPage")
	@ResponseBody
	public JsonPagination answerPage(PurchasingQuery bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = purchasingQueryMng.answerPageList(bean, page, rows, getUser(),searchData);
    	List<PurchasingQuery> li = (List<PurchasingQuery>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * 答复审批分页数据获得
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@RequestMapping(value = "/answerCheckPage")
	@ResponseBody
	public JsonPagination answerCheckPage(PurchasingQuery bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = purchasingQueryMng.answerCheckPageList(bean, page, rows, getUser(),searchData);
    	List<PurchasingQuery> li = (List<PurchasingQuery>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * 选择采购项目分页数据获得
	 * @author 沈帆
	 * @createtime 2021-03-17
	 * @updatetime 2021-03-17
	 */
	@RequestMapping(value = "/chooseCgPage")
	@ResponseBody
	public JsonPagination chooseCgPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.chooseCgPage(bean, page, rows,searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/**
	 * 质疑发起 新增按钮
	 * @author 方淳洲
	 * @createtime 2021-03-13
	 * @updatetime 2021-03-13
	 */
	@RequestMapping(value = "/addAsk")
	public String addAsk(ModelMap model){
		model.addAttribute("operation", "add");
		return "/WEB-INF/view/purchase_manage/questioning/cgAsk_add";
	}
	
	/**
	 * 质疑发起 修改
	 * @author 沈帆
	 * @createtime 2021-03-19
	 * @updatetime 2021-03-19
	 */
	@RequestMapping(value = "/editAsk")
	public String editAsk(ModelMap model,Integer id){
		PurchasingQuery bean =purchasingQueryMng.findById(id);
		model.addAttribute("bean", bean);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attaList", attac);
		model.addAttribute("operation", "edit");
		return "/WEB-INF/view/purchase_manage/questioning/cgAsk_add";
	}
	
	/**
	 * 质疑发起查看
	 * @author 沈帆
	 * @createtime 2021-03-19
	 * @updatetime 2021-03-19
	 */
	@RequestMapping(value = "/detailAsk")
	public String detaiAsk(ModelMap model,Integer id){
		PurchasingQuery bean =purchasingQueryMng.findById(id);
		model.addAttribute("bean", bean);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attaList", attac);
		model.addAttribute("operation", "detail");
		return "/WEB-INF/view/purchase_manage/questioning/cgAsk_add";
	}
	
	/**
	 * 质疑发起
	 * @return
	 */
	@RequestMapping(value = "/saveAsk")
	@ResponseBody
	public Result save(PurchasingQuery bean, String files){
		try {
			purchasingQueryMng.saveAsk(bean, files, getUser());
			return getJsonResult(true, Result.saveSuccessMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "系统错误请联系管理员！");
		}
	}
	
	/**
	 * 质疑发起删除
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id){
		try {
			purchasingQueryMng.delete(id, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 质疑答复新增
	 * @author 沈帆
	 * @createtime 2021-03-22
	 * @updatetime 2021-03-22
	 */
	@RequestMapping(value = "/addAnswer")
	public String addAnswer(ModelMap model,Integer id){
		PurchasingQuery bean =purchasingQueryMng.findById(id);
		PurchasingQuery newBean = new PurchasingQuery();
		BeanUtils.copyProperties(bean, newBean);
		newBean.setfAnswerTime(new Date());
		model.addAttribute("bean", newBean);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attaList", attac);
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "ZYDF", getUser().getDpID(), null, null, null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		model.addAttribute("operation", "add");
		return "/WEB-INF/view/purchase_manage/questioning/cgAnswer/cgAnswer_add";
	}
	
	/**
	 * 质疑答复修改
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@RequestMapping(value = "/editAnswer")
	public String editAnswer(ModelMap model,Integer id){
		PurchasingQuery bean =purchasingQueryMng.findById(id);
		model.addAttribute("bean", bean);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attaList", attac);
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "ZYDF", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("ZYDF", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
		model.addAttribute("proposer", proposer);
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		model.addAttribute("operation", "edit");
		return "/WEB-INF/view/purchase_manage/questioning/cgAnswer/cgAnswer_add";
	}
	
	/**
	 * 质疑答复查看
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@RequestMapping(value = "/detailAnswer")
	public String detailAnswer(ModelMap model,Integer id){
		PurchasingQuery bean =purchasingQueryMng.findById(id);
		model.addAttribute("bean", bean);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attaList", attac);
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "ZYDF", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("ZYDF", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
		model.addAttribute("proposer", proposer);
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		model.addAttribute("operation", "detail");
		return "/WEB-INF/view/purchase_manage/questioning/cgAnswer/cgAnswer_add";
	}
	
	
	/**
	 * 质疑答复审批
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@RequestMapping(value = "/check")
	public String check(ModelMap model,Integer id){
		PurchasingQuery bean =purchasingQueryMng.findById(id);
		model.addAttribute("bean", bean);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attaList", attac);
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "ZYDF", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("ZYDF", bean.getfDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
		model.addAttribute("proposer", proposer);
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/questioning/cgAnswer/cgAnswer_check";
	}
	/**
	 * 质疑答复
	 * @return
	 */
	@RequestMapping(value = "/saveAnswer")
	@ResponseBody
	public Result saveAnswer(PurchasingQuery bean, String files){
		try {
			purchasingQueryMng.saveAnswer(bean, files, getUser());
			return getJsonResult(true, Result.saveSuccessMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "系统错误请联系管理员！");
		}
	}
	
	/*
	 * 保存采购质疑审批结果
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,PurchasingQuery bean,String spjlFile) {
		try {
			PurchasingQuery oldBean =purchasingQueryMng.findById(bean.getFqId());
			purchasingQueryMng.saveCheckInfo(checkBean, oldBean, getUser(), spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	/**
	 * 撤回质疑发起
	 * @param id 主键id
	 * @param model
	 * @return
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@ResponseBody
	@RequestMapping(value = "/reCallAsk")
	public Result reCallAsk(String id){
		try {
			purchasingQueryMng.reCallAsk(id);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
	}
	
	/**
	 * 撤回答复申请
	 * @param id 主键id
	 * @param model
	 * @return
	 * @author 沈帆
	 * @createtime 2021-03-23
	 * @updatetime 2021-03-23
	 */
	@ResponseBody
	@RequestMapping(value = "/reCallAnswer")
	public Result reCallAnswer(String id){
		try {
			purchasingQueryMng.reCallAnswer(id);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
	}
}
