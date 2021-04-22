package com.braker.icontrol.cgmanage.cgconfplan.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.ftp.FileUpload;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.Proposer;
import com.braker.icontrol.cgmanage.cgcheck.model.PurcMaterialRegisterList;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanListMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConfPlanCheckMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 配置计划管理的控制层
 * 
 * @author 冉德茂
 * @createtime 2018-10-11
 * @updatetime 2018-12-03
 */
@Controller               
@RequestMapping(value = "/cgconfplangl")
public class CgConPlanController extends BaseController{
	@Autowired
	private CgConPlanMng confplanMng;
	@Autowired
	private CgConfPlanCheckMng cgconfcheckplanMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private CgConPlanListMng cgConPlanListMng;
	/*
	 * 跳转到采购配置列表页面
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/confplan/confplan_list";
	}

	/*
	 * 跳转到采购类型选择页面
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/confplan/checkTypeIndex";

	}
	
	/*
	 * 分页数据获取配置计划信息
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/confplanPageData")
	@ResponseBody
	public JsonPagination loanPage(ProcurementPlan bean, String sort, String order, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = confplanMng.pageList(bean,getUser(),page, rows);
    	List<ProcurementPlan> li = (List<ProcurementPlan>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	/*
	 * 分页数据获取已经审核通过，未删除，未选择过的的配置计划信息进行合并
	 * @author 冉德茂
	 * @createtime 2018-10-23
	 * @updatetime 2018-10-23
	 */
	@RequestMapping(value = "/confplancombinePageData")
	@ResponseBody
	public JsonPagination combinePage(ProcurementPlan bean, String sort, String order, Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = confplanMng.combinepageList(bean,page, rows);
		List<ProcurementPlan> li = (List<ProcurementPlan>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}
	/*
	 * 获取已经审批通过  未在采购页面选择过的数据进行采购登记   
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@RequestMapping(value = "/pickconfplan")
	@ResponseBody
	public JsonPagination getlistjson(ProcurementPlan bean, String sort, String order, Integer page, Integer rows) {
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = confplanMng.getCheckedConfplan(bean,page, rows);
		List<ProcurementPlan> prolist = (List<ProcurementPlan>) p.getList();
		for(int i=0;i<prolist.size();i++){
			//序号设置
			prolist.get(i).setNum((i+1)+(page-1)*rows);
		}
		return getJsonPagination(p, page);
	}


	/*
	 * 新增功能科目
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping("/add")
	public String add(ModelMap model){
		model.addAttribute("openType", "add");
		ProcurementPlan bean = new ProcurementPlan();
		//获取当前登录对象获得申请部门
		bean.setFreqDept(getUser().getDepartName());
		bean.setFreqDeptId(getUser().getDepart().getId());
		bean.setFreqLinkMen(getUser().getName());	
		//申请日期
		bean.setFreqTime(new Date());
		//自动生成标号
		String str="JH";
		bean.setFlistNum(StringUtil.Random(str));	
		model.addAttribute("bean", bean);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(getUser().getId(),"CGPLANSQ", getUser().getDpID(),null,bean.getnCode(), null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDm(), bean.getFreqTime());
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/purchase_manage/confplan/confplan_add";
	}
	/*
	 * 新增的保存
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(ProcurementPlan bean,String mingxi,String files,ModelMap model) {
		
		try {
			confplanMng.save(bean,mingxi, files,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 查看配置计划信息
	 * @author 冉德茂
	 * @createtime 2018-10-16
	 * @updatetime 2018-10-16
	 */
	@RequestMapping(value ="/detail")
	public String detail(String id,ModelMap model){
		ProcurementPlan bean=confplanMng.findById(Integer.valueOf(id));
		model.addAttribute("bean",bean);
		//查询附件信息
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		if(bean.getFlistNum().startsWith("HB")){
			return "/WEB-INF/view/purchase_manage/confplan/confplan_detail_hb";
		}else{
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"CGPLANSQ", bean.getFreqDeptId(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(),  bean.getBeanCodeField(), bean.getFlistNum(),"1");
			model.addAttribute("nodeConf", nodeConfList);
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getFreqLinkMen(), bean.getFreqDept(), bean.getFreqTime());
			model.addAttribute("proposer", proposer);
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("CGPLANSQ", bean.getFreqDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
			return "/WEB-INF/view/purchase_manage/confplan/confplan_detail";
		}
	}
	/*
	 * 功能科目信息的修改
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/edit")
	public String edit(String id,ModelMap model){
		model.addAttribute("openType", "edit");
		//查询基本信息
		ProcurementPlan bean=confplanMng.findById(Integer.valueOf(id));
		model.addAttribute("bean",bean);
		//查询附件信息
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"CGPLANSQ", bean.getFreqDeptId(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(),  bean.getFlistNum(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getFreqLinkMen(), bean.getFreqDept(), bean.getFreqTime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("CGPLANSQ", bean.getFreqDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());
		
		return "/WEB-INF/view/purchase_manage/confplan/confplan_add";
	}	
	/*
	 * 功能科目信息的删除
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id) {
		try {
			//传回来的id是主键
			confplanMng.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
		
	
	/*
	 * 附件上传AJAX
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/applyFile")
	@ResponseBody
	public boolean applyFile(String fileurl){
		try {
			fileurl = java.net.URLDecoder.decode(fileurl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		//获取文件名
		String[] names = fileurl.split("\\\\");
		String name = names[names.length-1];
		//保存附件文件
		FileUpload fu = new FileUpload();
		String url = fu.getFtpConfig("url");
		int port = Integer.parseInt(fu.getFtpConfig("port"));
		String username = fu.getFtpConfig("username");
		String password = fu.getFtpConfig("password");
		boolean flag = false;
		try {
			String path = "CG/CGSQ";
			String filename = name.trim();
			String input = fileurl.trim();
			flag=fu.upLoadFromProduction(url, port,username,password,path,filename,input);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/*
	 * 查询计划采购品目明细（默认查询 和采购页面选择配置单查询）
	 * @author 冉德茂
	 * @createtime 2018-10-16
	 * @updatetime 2018-10-16
	 */
	@RequestMapping(value = "/mingxi")
	@ResponseBody
	public JsonPagination mingxi(String id,String planid) {
		
		Pagination p = new Pagination();
		Integer fpId;
		if(planid!=null){//页面默认查询清单
			fpId=Integer.valueOf(planid);
		}else{//选择配置单  加载清单信息
			fpId=Integer.valueOf(id);
		}
		//查询采购清单信息
		List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fpId", fpId);
		for(int i=0;i<mingxiList.size();i++){
			ProcurementPlanList bean = (ProcurementPlanList)mingxiList.get(i);
			//bean.setFunitPrice(new BigDecimal(bean.getFunitPrice()).stripTrailingZeros().toPlainString());//去掉多余的o
			//bean.setFamount(new BigDecimal(bean.getFamount()).stripTrailingZeros().toPlainString());//去掉多余的o
			bean.setNum(i+1);
		}
		p.setList(mingxiList);
		return getJsonPagination(p, 0);
		
	}
	
	/**
	 * 根据供应商id查询该供应商的商品清单
	 * @param id
	 * @param planid
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月28日
	 * @updator 陈睿超
	 * @updatetime 2020年6月28日
	 */
	@RequestMapping(value = "/mingxilist")
	@ResponseBody
	public JsonPagination mingxilist(String code,String planid) {
		
		Pagination p = new Pagination();
		/*Integer fpId;
		if(planid!=null){//页面默认查询清单
			fpId=Integer.valueOf(planid);
		}else{//选择配置单  加载清单信息
			fpId=Integer.valueOf(code);
		}*/
		//查询采购清单信息
		List<Object> mingxiList = confplanMng.getMingxi("PurcMaterialRegisterList", "fbiddingCode", code);
		for(int i=0;i<mingxiList.size();i++){
			PurcMaterialRegisterList bean = (PurcMaterialRegisterList)mingxiList.get(i);
			//bean.setFunitPrice(new BigDecimal(bean.getFunitPrice()).stripTrailingZeros().toPlainString());//去掉多余的o
			//bean.setFamount(new BigDecimal(bean.getFamount()).stripTrailingZeros().toPlainString());//去掉多余的o
			bean.setNum(i+1);
		}
		p.setList(mingxiList);
		return getJsonPagination(p, 0);
	}
	
	
	/**
	 * 查询采购清单中的本部门采购清单数据
	 * @param id
	 * @param planid
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月18日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月18日
	 */
	@RequestMapping(value = "/deptCGDetail")
	@ResponseBody
	public JsonPagination deptCGDetail(Integer fpId) {
		
		Pagination p = new Pagination();
		//查询采购清单信息
		List<ProcurementPlanList> mingxiList = cgConPlanListMng.findByProperty("fpId", fpId);
		for(int i=0;i<mingxiList.size();i++){
			ProcurementPlanList bean = (ProcurementPlanList)mingxiList.get(i);
			bean.setNum(i+1);
		}
		p.setList(mingxiList);
		return getJsonPagination(p, 0);
	}
	
	
	
	
	
	
	
	
	
	
	
}
