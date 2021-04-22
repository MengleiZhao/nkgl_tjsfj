package com.braker.icontrol.cgmanage.procurement.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import com.braker.core.manager.UserMng;
import com.braker.core.model.Proposer;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.procurement.manager.ProcurementNeedsMng;
import com.braker.icontrol.cgmanage.procurement.model.ProcurementNeedsInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;




/**
 * 需求申请的控制层
 * @author 方淳洲
 * @createtime 2021-03-13
 * @updatetime 2021-03-13
 */
@Controller
@RequestMapping(value = "/procurementNeeds")
public class ProcurementNeedsController extends BaseController{
	
	@Autowired
	private ProcurementNeedsMng procurementNeedsMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
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
		return "/WEB-INF/view/purchase_manage/procurement/procurementNeeds/xqsq_list";
	}
	
	
	
	/*
	 * 分页数据获得
	 * @author 方淳洲
	 * @createtime 2021-03-13
	 * @updatetime 2021-03-13
	 */
	@RequestMapping(value = "/procurementNeedsPage")
	@ResponseBody
	public JsonPagination loanPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = procurementNeedsMng.pageList(bean, page, rows, getUser(),searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	
    	for(int x=0; x<li.size(); x++) {
    		//序号设置	
    		li.get(x).setNumber((x+1)+(page-1)*rows);
    		//采购需求申请审批状态设置
    		List<ProcurementNeedsInfo> list = procurementNeedsMng.findByCgId(li.get(x).getFpId());
    		if(list.size() > 0){
    			li.get(x).setNeedsStatus(list.get(0).getFlowStatus());
    			procurementNeedsMng.updateDefault(li.get(x));
    		}
		}
    	return getJsonPagination(p, page);
	}
	
	
	
	/*
	 * 跳转新增页面
	 * @author 方淳洲
	 * @createtime 2021-03-15
	 * @updatetime 2021-03-15
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model,String id){
		ProcurementNeedsInfo pr = new ProcurementNeedsInfo();
		//自动生成编号
		String str="XQ";
		//当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		//查询当天数据
		List<ProcurementNeedsInfo> list = procurementNeedsMng.findByCondition(currentDate);
		int dataSize = list.size() + 1;
		String dataCode;
		if(dataSize < 10){
			dataCode ="00" + dataSize;
		}else{
			if(dataSize < 100){
				dataCode ="0" + dataSize;
			}else{
				dataCode = String.valueOf(dataSize);
			}
		}
		pr.setpCode(str + currentDate + dataCode);
		
		
		model.addAttribute("bean", pr);
		
		
		
		model.addAttribute("needsBean",pr);
		
		PurchaseApplyBasic p = cgsqMng.findById(Integer.parseInt(id));
		model.addAttribute("bean",p);
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "CGXQ", getUser().getDpID(), null, null, null, null, null, null);
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/purchase_manage/procurement/procurementNeeds/xqsq_add";
	}
	
	
	
	/*
	 * 采购需求申请的保存
	 * @author 方淳洲
	 * @createtime 2021-03-15
	 * @updatetime 2021-03-15
	 */
	
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(ProcurementNeedsInfo bean, String xqsfiles ,String wtsfiles) {
		try {
			//保存
			procurementNeedsMng.save(bean, xqsfiles, wtsfiles,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	
	/*
	 * 采购需求申请 修改
	 * @author 方淳洲
	 * @createtime 2021-03-15
	 * @updatetime 2021-03-15
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, Integer id){
		//查询基本信息
		ProcurementNeedsInfo bean = procurementNeedsMng.findByCgId(id).get(0);
		model.addAttribute("needsBean", bean);
		PurchaseApplyBasic p = cgsqMng.findById(id);
		model.addAttribute("bean", p);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(),"CGXQ", bean.getXqDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getpCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGXQ", bean.getXqDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getXqUserName(), bean.getXqDeptName(), bean.getCreateTime());
		model.addAttribute("proposer", proposer);
		
		//历史审批节点
		String historyNodes = tProcessCheckMng.getHistoryNodes("CGXQ", bean.getXqDeptId(), bean.getBeanCode());
		model.addAttribute("historyNodes", historyNodes);
		
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		model.addAttribute("openType", "edit");
		
		return "/WEB-INF/view/purchase_manage/procurement/procurementNeeds/xqsq_add";
	}
	
	
	/*
	 * 采购需求申请 查看详情
	 * @author 方淳洲
	 * @createtime 2021-03-16
	 * @updatetime 2021-03-16
	 */
	@RequestMapping(value ="/detail")
	public String detail(ModelMap model, Integer id){
		
		//查询基本信息
		PurchaseApplyBasic p = cgsqMng.findById(id);
		model.addAttribute("bean", p);
		ProcurementNeedsInfo bean = procurementNeedsMng.findByCgId(id).get(0);
		model.addAttribute("needsBean", bean);
		
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGXQ", bean.getXqDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getpCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//得到工作流id
		TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGXQ", bean.getXqDeptId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getXqUserName(), bean.getXqDeptName(), bean.getCreateTime());
		model.addAttribute("proposer", proposer);
		
		//历史审批节点
		String historyNodes = tProcessCheckMng.getHistoryNodes("CGXQ", bean.getXqDeptId(), bean.getBeanCode());
		model.addAttribute("historyNodes", historyNodes);
		
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());
		
		model.addAttribute("openType", "detail");
		return "/WEB-INF/view/purchase_manage/procurement/procurementNeeds/xqsq_detail";
	}
	
	
	/**
	 * 采购需求申请 撤回
	 * @author 方淳洲
	 * @createtime 2021-03-15
	 * @updatetime 2021-03-15
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			procurementNeedsMng.reCall(id);
			return getJsonResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}

	}
	
	
	/*
	 * 采购需求申请删除
	 * @author 方淳洲
	 * @createtime 2021-03-15
	 * @updatetime 2021-03-15
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id,String fId,ModelMap model) {
		try {
			//传回来的id是主键
			procurementNeedsMng.delete(id,fId,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
}
