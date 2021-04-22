package com.braker.icontrol.cgmanage.procurement.controller;

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
import com.braker.core.manager.UserMng;
import com.braker.core.model.Proposer;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.procurement.manager.ProcurementNeedsCheckMng;
import com.braker.icontrol.cgmanage.procurement.manager.ProcurementNeedsMng;
import com.braker.icontrol.cgmanage.procurement.model.ProcurementNeedsInfo;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;


@Controller
@RequestMapping(value = "/procurementNeedsCheck")
public class ProcurementNeedsCheckController extends BaseController{
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private ProcurementNeedsCheckMng procurementNeedsCheckMng;
	
	@Autowired
	private ProcurementNeedsMng procurementNeedsMng;
	
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
	 * @createtime 2021-03-16
	 * @updatetime 2021-03-16
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/procurement/procurementNeeds/xqsq_check_list";
	}
	
	
	/*
	 * 分页数据获得
	 * @author 方淳洲
	 * @createtime 2021-03-16
	 * @updatetime 2021-03-16
	 */
	@RequestMapping(value = "/procurementNeedsCheckPage")
	@ResponseBody
	public JsonPagination procurementNeedsCheckPage(ProcurementNeedsInfo bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = procurementNeedsCheckMng.pageList(bean, page, rows, getUser(),searchData);
    	List<ProcurementNeedsInfo> li = (List<ProcurementNeedsInfo>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		//序号设置	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}	
	
	
	/*
	 * 跳转审批页面
	 * @author 方淳洲
	 * @createtime 2021-03-16
	 * @updatetime 2021-03-16
	 */
	@RequestMapping(value = "/check")
	public String check(String id ,ModelMap model) {
		
		//查询基本信息				
		ProcurementNeedsInfo bean = procurementNeedsMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		
		//查询附件信息
		List<Attachment> attac =attachmentMng.list(bean);
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
		
		return "/WEB-INF/view/purchase_manage/procurement/procurementNeeds/xqsq_check";
	}
	
	
	/*
	 * 进行审核
	 * @author 方淳洲
	 * @createtime 2021-03-16
	 * @updatetime 2021-03-16
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,ProcurementNeedsInfo bean,String spjlFile) {
		try {
			procurementNeedsCheckMng.saveCheckInfo(checkBean, bean, getUser(), spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
}
