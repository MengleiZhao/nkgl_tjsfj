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
import com.braker.core.model.Proposer;
import com.braker.icontrol.cgmanage.procurement.manager.PurchasePlanMng;
import com.braker.icontrol.cgmanage.procurement.model.PurchasePlanBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

/**
 * 政采计划审批控制层
 * 
 * @author wanping
 *
 */
@Controller
@RequestMapping(value = "/purchasePlanCheck")
public class PurchasePlanCheckController extends BaseController {
	
	@Autowired
	private PurchasePlanMng purchasePlanMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	/**
	 * 跳转政采计划审批列表
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/procurement/purchasePlan/plan_check_list";
	}

	/**
	 * 分页数据获得
	 * @param bean
	 * @param page
	 * @param rows
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/pageData")
	@ResponseBody
	public JsonPagination pageData(PurchasePlanBasic bean, Integer page, Integer rows, String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = purchasePlanMng.checkPageList(bean, page, rows, getUser(), searchData);
    	List<PurchasePlanBasic> li = (List<PurchasePlanBasic>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);
		}
		return getJsonPagination(p, page);
	}

	/**
	 * 跳转审核页面
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/check")
	public String check(Integer id, ModelMap model) {
		try {
			// 传回来的id是主键
			PurchasePlanBasic bean = purchasePlanMng.findById(id);
			model.addAttribute("bean", bean);

			// 查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGJH", bean.getfDeptId(),
					bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getfPurchasePlanCode(),
					"1");
			model.addAttribute("nodeConf", nodeConfList);
			// 建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			// 得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGJH", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			// 对象编号
			model.addAttribute("foCode", bean.getBeanCode());
			// 当前用户
			model.addAttribute("currentUser", getUser());

			return "/WEB-INF/view/purchase_manage/procurement/purchasePlan/plan_check";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 政采计划审批
	 * @param checkBean
	 * @param bean
	 * @param spjlFile
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean, PurchasePlanBasic bean, String spjlFile) {
		try {
			purchasePlanMng.check(checkBean, bean, getUser(), spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
}
