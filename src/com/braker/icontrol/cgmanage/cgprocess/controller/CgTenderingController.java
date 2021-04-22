package com.braker.icontrol.cgmanage.cgprocess.controller;



import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.braker.common.ftp.FileUpload;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.FileUpLoadUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.SystemCenterAttac;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgcheck.manager.PurcMaterialRegisterListMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgBidWinRefMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgSelMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BidRegist;
import com.braker.icontrol.cgmanage.cgprocess.model.BidWinningRef;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 采购过程登记的控制层
 * 本模块用于采购过程登记的操作
 * @author 冉德茂
 * @createtime 2018-07-20
 * @updatetime 2018-07-20
 */
@Controller
@RequestMapping(value = "/cgprocess")
public class CgTenderingController extends BaseController{
	@Autowired
	private CgProcessMng cgProcessMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private CgSelMng cgselMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private CgBidWinRefMng cgbwrefMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;

	@Autowired
	private ApplyMng applyMng;
	
	@Autowired
	private RegisterApplyMng registerApplyMng;
	
	@Autowired
	private PurcMaterialRegisterListMng purcMaterialRegisterListMng;
	
	@Autowired
	private LookupsMng lookupsMng;

	@Autowired
	private TProExpendDetailMng detailMng;
	
	@Autowired
	private BudgetIndexMgrMng indexMng;
	/**
	 * 采购过程登记列表页面
	 * @author 焦广兴
	 * @createtime 2019-05-28
	 * @updatetime 2019-05-28
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/process/new/process_list";
	}
	
	/*
	 * 分页数据获得
	 * @author 冉德茂
	 * @createtime 2018-07-23
	 * @updatetime 2018-07-23
	 */
	@RequestMapping(value = "/pageList")
	@ResponseBody
	public JsonPagination pageList(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgProcessMng.pageList(bean,getUser(), page, rows,searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		/*Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
    		li.get(x).setFpItemsNames(lookups.getName());*/
			//序号设置	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/**
	 * 采购过程登记保存
	 * @author 焦广兴
	 * @createtime 2019-05-28
	 * @updatetime 2019-05-28
	 */
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(RegisterApplyBasic rBean, PurchaseApplyBasic pabBean, String files,String jsonList,String supJsonList,String files01,String files02,String files03,String files04,String files05) {
		try {
			/*cgProcessMng.save(bean, bean2, files, getUser());*/
			synchronized (this) {
				cgProcessMng.save(rBean, pabBean,getUser(),jsonList,supJsonList,files01,files02,files03,files04,files05);
			}
		}catch (ServiceException es) {
			es.printStackTrace();
			return getJsonResult(false, es.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "操作失败，请联系管理员！");
		}
		return getJsonResult(true, "操作成功！");
	}
	
	/**
	 * 采购过程登记新增
	 * @author 焦广兴
	 * @createtime 2019-05-28
	 * @updatetime 2019-05-28
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model, Integer id){
		//查询采购基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		User user = getUser();
		
//		if(!user.getId().equals(bean.getfUser())){//当登记人不为申请人时，申请人（登记人）为具体的登记个人，不要出现部门
//			model.addAttribute("deptshow", true);
//		}
		//查询过程登记基本信息
		RegisterApplyBasic rBean = new RegisterApplyBasic();
		//查询采购单附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//操作类型
		model.addAttribute("openType", "add");
		
		//登记人、登记部门、登记时间
		model.addAttribute("brBean", rBean);
		
		//查询四级指标信息
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());		
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);	
			//资金渠道
			if("0".equals(index.getIndexType())){
				bean.setPfIndexType("基本预算");		
			}else{
				bean.setPfIndexType("项目预算");		
			}
		}
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(user.getId(), "CGDJ",user.getDepart().getId(), rBean.getBeanCode(), rBean.getnCode(), rBean.getJoinTable(), rBean.getBeanCodeField(), rBean.getFbiddingCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGDJ",user.getDepart().getId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), user.getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		
		//对象编码
		model.addAttribute("foCode", rBean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/process/new/process_main_add";
	}
	/**
	 * 采购过程登记修改
	 * @author 焦广兴
	 * @createtime 2019-05-28
	 * @updatetime 2019-05-28
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, Integer id){
		//查询采购基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		User user = getUser();
		if(!user.getId().equals(bean.getfUser())){//当登记人不为申请人时，申请人（登记人）为具体的登记个人，不要出现部门
			model.addAttribute("deptshow", true);
		}
		//查询过程登记基本信息
		RegisterApplyBasic rBean = new RegisterApplyBasic();
		
		//操作类型
		model.addAttribute("openType", "edit");
		
		rBean = registerApplyMng.findByProperty("fpId", id).get(0);
		//查询采购单附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//查询登记过程附件信息
		List<Attachment> brAttac = attachmentMng.list(rBean);
		model.addAttribute("brAttac", brAttac);
		model.addAttribute("brBean", rBean);
		
		//查询四级指标信息
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());		
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);	
			//资金渠道
			if("0".equals(index.getIndexType())){
				bean.setPfIndexType("基本预算");		
			}else{
				bean.setPfIndexType("项目预算");		
			}
		}
		//查询采购登记中的中标商明细
		List<Object> listBiddingRegist = applyMng.getMingxi("BiddingRegist", "fpId", id);
		model.addAttribute("list", listBiddingRegist);
		model.addAttribute("listIndex", listBiddingRegist.size());
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(user.getId(), "CGDJ",user.getDepart().getId(), rBean.getBeanCode(), rBean.getnCode(), rBean.getJoinTable(), rBean.getBeanCodeField(), rBean.getFbiddingCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGDJ",user.getDepart().getId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), user.getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		
		//对象编码
		model.addAttribute("foCode", rBean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/process/new/process_main_add";
	}
	
	/**
	 * 采购过程登记查看
	 * @author 焦广兴
	 * @createtime 2019-05-28
	 * @updatetime 2019-05-28
	 */
	@RequestMapping(value = "/detail")
	public String detail(ModelMap model, Integer id){
		//查询采购基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		
		//查询过程登记基本信息
		RegisterApplyBasic rBean = new RegisterApplyBasic();
		
		//操作类型
		model.addAttribute("openType", "detail");
		
		rBean = registerApplyMng.findByProperty("fpId", id).get(0);
		
		if(!rBean.getfUser().equals(bean.getfUser())){//当登记人不为申请人时，申请人（登记人）为具体的登记个人，不要出现部门
			model.addAttribute("deptshow", true);
		}
		//查询采购单附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//查询登记过程附件信息
		List<Attachment> brAttac = attachmentMng.list(rBean);
		model.addAttribute("brAttac", brAttac);
		model.addAttribute("brBean", rBean);
		//查询四级指标信息
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());		
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);	
			//资金渠道
			if("0".equals(index.getIndexType())){
				bean.setPfIndexType("基本预算");		
			}else{
				bean.setPfIndexType("项目预算");		
			}
		}
		//查询采购登记中的中标商明细
		List<Object> listBiddingRegist = applyMng.getMingxi("BiddingRegist", "fpId", id);
		model.addAttribute("list", listBiddingRegist);
		model.addAttribute("listIndex", listBiddingRegist.size());
		/*//查询供应商信息  id是中标表里的主键  是中标供应商映射表的字段
		List<BidWinningRef> bwrList = cgbwrefMng.findByBidid(brBean.getFbId());
		Integer wid = bwrList.get(0).getFwId();
		model.addAttribute("fwBean", cgselMng.findById(wid));*/
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(rBean.getfUser(), "CGDJ",rBean.getfDeptID(), rBean.getBeanCode(), rBean.getnCode(), rBean.getJoinTable(), rBean.getBeanCodeField(), rBean.getFbiddingCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGDJ",rBean.getfDeptID());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(rBean.getfUserName(), rBean.getfDeptName(), rBean.getfReqTime());
		model.addAttribute("proposer", proposer);
		
		//对象编码
		model.addAttribute("foCode", rBean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/process/new/process_main_detail";
	}
	
	/**
	 * 
	 * @Title checkList 
	 * @Description 审批列表页面
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param model
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/checkList")
	public String checkList(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/process/new/process_check_list";
	}
	
	/**
	 * 
	 * @Title checkPageList 
	 * @Description 审批分页数据查询
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param bean
	 * @param page
	 * @param rows
	 * @return 
	 * @return JsonPagination
	 * @throws
	 */
	@RequestMapping(value = "/checkPageList")
	@ResponseBody
	public JsonPagination checkPageList(PurchaseApplyBasic pabBean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgProcessMng.checkPageList(pabBean, page, rows, getUser(),searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
//    		Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
//    		li.get(x).setFpItemsNames(lookups.getName());
			//序号设置	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/**
	 * 
	 * @Title check 
	 * @Description 审批页面
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param model
	 * @param id
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/check")
	public String check(ModelMap model, Integer id){
		//查询采购基本信息
		RegisterApplyBasic rBean = registerApplyMng.findById(id);
		
		//查询过程登记基本信息
		PurchaseApplyBasic bean = new PurchaseApplyBasic();
		
		//操作类型
		model.addAttribute("openType", "check");
		
		bean = cgsqMng.findById(rBean.getFpId());
		//查询采购单附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//查询登记过程附件信息
		List<Attachment> brAttac = attachmentMng.list(rBean);
		model.addAttribute("brAttac", brAttac);
		model.addAttribute("brBean", rBean);
		model.addAttribute("bean", bean);
		//查询四级指标信息
		Integer detailId = bean.getProDetailId();
		Integer indexId = bean.getIndexId();
		if (detailId != null && indexId != null) {
			TProExpendDetail detail = detailMng.findById(detailId);
			TBudgetIndexMgr index = indexMng.findById(indexId);
			if (detail != null && index != null) {
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+detail.getSubName()+" 】");
				//批复金额
				bean.setPfAmount(detail.getOutAmount());		
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());		
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);	
			//资金渠道
			if("0".equals(index.getIndexType())){
				bean.setPfIndexType("基本预算");		
			}else{
				bean.setPfIndexType("项目预算");		
			}
		}
		//查询采购登记中的中标商明细
		List<Object> listBiddingRegist = applyMng.getMingxi("BiddingRegist", "fpId", bean.getFpId());
		model.addAttribute("list", listBiddingRegist);
		model.addAttribute("listIndex", listBiddingRegist.size());
		/*//查询供应商信息  id是中标表里的主键  是中标供应商映射表的字段
		List<BidWinningRef> bwrList = cgbwrefMng.findByBidid(brBean.getFbId());
		Integer wid = bwrList.get(0).getFwId();
		model.addAttribute("fwBean", cgselMng.findById(wid));*/
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(rBean.getfUser(), "CGDJ",rBean.getfDeptID(), rBean.getBeanCode(), rBean.getnCode(), rBean.getJoinTable(), rBean.getBeanCodeField(), rBean.getFbiddingCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGDJ",rBean.getfDeptID());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(rBean.getfUserName(), rBean.getfDeptName(), rBean.getfReqTime());
		model.addAttribute("proposer", proposer);
		//对象编码
		model.addAttribute("foCode", rBean.getBeanCode());
		return "/WEB-INF/view/purchase_manage/process/new/process_main_check";
	}
	
	/**
	 * 
	 * @Title checkResult 
	 * @Description 进行审批
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param checkBean
	 * @param bean
	 * @param spjlFile
	 * @return
	 * @return Result
	 * @throws
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean, RegisterApplyBasic bean, String spjlFile) {
		try {
			cgProcessMng.saveCheck(checkBean, bean, spjlFile, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	/**
	 * 
	 * @Title reCall 
	 * @Description 采购过程登记撤回
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param id
	 * @return
	 * @return Result
	 * @throws
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			cgProcessMng.reCall(id);
			return getJsonResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}
	}
	
	
	/**
	 * 查询中标商明细
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月16日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月16日
	 */
	@RequestMapping(value = "/purchasing")
	@ResponseBody
	public List<Object> purchasing(Integer id) {
		List<Object> list = new ArrayList<Object>();
		if(id != null) {
			//查询接待明细
			list = applyMng.getObjectList("BiddingRegist", "fpId", id);
		}
		return list;
	}
	/**
	 * 查询中标商采购清单明细
	 * @param id
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月23日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月23日
	 */
	@RequestMapping(value = "/purchasingMX")
	@ResponseBody
	public List<Object> purchasingMX(String code) {
		List<Object> list = new ArrayList<Object>();
		if(!"".equals(code)) {
			//查询接待明细
			list = purcMaterialRegisterListMng.getMingxi("PurcMaterialRegisterList", "fbiddingCode", code);
		}
		return list;
	}
	
	
	
	/**
	 * 采购清单页面跳转
	 * @author 赵孟雷
	 * @createtime 2020-06-18
	 * @updatetime 2020-06-18
	 */
	@RequestMapping(value = "/cgDetailedAccount")
	public String cgDetailedAccount(ModelMap model,String index,String fpId,String tabId,String fbiddingCode) {
		model.addAttribute("index", index);
		model.addAttribute("tabId", tabId);
		model.addAttribute("fpId", fpId);
		model.addAttribute("fbiddingCode", fbiddingCode);
		return "/WEB-INF/view/purchase_manage/process/new/process_detailed_account";
	}
}
