package com.braker.icontrol.expend.reimburse.controller;

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
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.manager.EnforcingMng;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;


/**
 * 报销台账的控制层
 * @author 叶崇晖
 * @createtime 2018-08-14
 * @updatetime 2018-08-14
 */
@Controller
@RequestMapping(value = "/reimburseLedger")
public class ReimburseLedgerController extends BaseController {
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	
	@Autowired
	private EnforcingMng enforcingMng;
	
	@Autowired 
	private UserMng userMng;
	
	@Autowired 
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	/*
	 * 跳转到列表页面
	 * @author 叶崇晖
	 * @createtime 2018-08-14
	 * @updatetime 2018-08-14
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/expend/reimburse/ledger/reimburse_ledger_list";
	}
	
	@RequestMapping(value = "/cashierList")
	public String cashierList( ModelMap model) {
		return "/WEB-INF/view/expend/reimburse/ledger/cashier_list";
	}
	
	/*
	 * 跳转到首页待办事项页面
	 * @author 赵孟雷
	 * @createtime 2018-08-14
	 * @updatetime 2018-08-14
	 */
	@RequestMapping(value = "/list1")
	public String list1( ModelMap model) {
		return "/WEB-INF/view/expend/reimburse/ledger/reimburse_ledger_list1";
	}
	
	/*
	 * 分页数据获得
	 * @author 叶崇晖
	 * @createtime 2018-08-13
	 * @updatetime 2018-08-13
	 */
	@RequestMapping(value = "/reimbursePage")
	@ResponseBody
	public JsonPagination reimbursePage(String applyString, DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean, ContractBasicInfo cBean, String reimburseType,Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = null;
    	//0位直接报销1到6位申请报销
    	if("0".equals(reimburseType)) {//0-直接报销
    		p = directlyReimbMng.ledgerPageList(applyString,drBean, page, rows, getUser(),searchData);
    		List<DirectlyReimbAppliBasicInfo> li = (List<DirectlyReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//序号设置	
    			li.get(x).setNum((x+1)+(page-1)*rows);	
    			//设置申请人姓名（id查姓名）,申请人所属部门
    			User user = userMng.findById(li.get(x).getUser());
    			if(user!=null){
    				li.get(x).setUserName(user.getName());
    			}
    		}
    	}else if("8".equals(reimburseType)) {//8-合同报销
    		p = reimbAppliMng.ledgerPageList("'8'",applyString,rBean, page, rows, getUser(),searchData);
    		List<ReimbAppliBasicInfo> li = (List<ReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//序号设置	
    			li.get(x).setNum((x+1)+(page-1)*rows);	
    		}
    	}else if("9".equals(reimburseType)) {//9-往来款报销
    		p = reimbAppliMng.ledgerPageList("'9'",applyString,rBean, page, rows, getUser(),searchData);
    		List<ReimbAppliBasicInfo> li = (List<ReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//序号设置	
    			li.get(x).setNum((x+1)+(page-1)*rows);	
    		}
    	}else if("11".equals(reimburseType)) {//11-保证金报销
    		p = reimbAppliMng.ledgerPageList("'11'",applyString,rBean, page, rows, getUser(),searchData);
    		List<ReimbAppliBasicInfo> li = (List<ReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//序号设置	
    			li.get(x).setNum((x+1)+(page-1)*rows);	
    		}
    	}else {//1-7 申请报销
    		p = reimbAppliMng.ledgerPageList("'1','2','3','4','5','6','7'",applyString,rBean, page, rows, getUser(),searchData);
    		List<ReimbAppliBasicInfo> li = (List<ReimbAppliBasicInfo>) p.getList();
    		for(int x=0; x<li.size(); x++) {
    			//序号设置	
    			li.get(x).setNum((x+1)+(page-1)*rows);	
    		}
    	}
		return getJsonPagination(p, page);
	}
	/*
	 * 分页数据获得
	 * @author 叶崇晖
	 * @createtime 2018-08-13
	 * @updatetime 2018-08-13
	 */
	@RequestMapping(value = "/reimburseCockpitPage")
	@ResponseBody
	public JsonPagination reimburseCockpitPage(String applyString, DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean, ContractBasicInfo cBean, String reimburseType,Integer page, Integer rows,String subCode){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = null;
		p = reimbAppliMng.reimburseCockpitPage("", applyString, rBean, page, rows, getUser(), subCode);
		List<ApplicationBasicInfo> li = (List<ApplicationBasicInfo>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
			//设置申请人姓名（id查姓名）,申请人所属部门
			User user = userMng.findById(li.get(x).getUser());
			if(user!=null){
				li.get(x).setUserName(user.getName());
			}
		}
		return getJsonPagination(p, page);
	}
	/**
	 * 出纳受理
	 * @param applyString
	 * @param drBean
	 * @param rBean
	 * @param reimburseType
	 * @param page
	 * @param rows
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年1月9日
	 */
	@RequestMapping(value = "/cashierPage")
	@ResponseBody
	public JsonPagination cashierPage(String applyString, DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean, String reimburseType,Integer page, Integer rows){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = null;
		//0位直接报销1到5位申请报销
		if(reimburseType.equals("0")) {
			p = directlyReimbMng.cashierPageList(drBean, page, rows, getUser());
			List<DirectlyReimbAppliBasicInfo> li = (List<DirectlyReimbAppliBasicInfo>) p.getList();
			for(int x=0; x<li.size(); x++) {
				//序号设置	
				li.get(x).setNum((x+1)+(page-1)*rows);	
				//设置申请人姓名（id查姓名）,申请人所属部门
				User user = userMng.findById(li.get(x).getUser());
				if(user!=null){
					li.get(x).setUserName(user.getName());
				}
			}
		} else {
			p = reimbAppliMng.cashierPageList(rBean, page, rows, getUser(),applyString);
			List<ReimbAppliBasicInfo> li = (List<ReimbAppliBasicInfo>) p.getList();
			for(int x=0; x<li.size(); x++) {
				//序号设置	
				li.get(x).setNum((x+1)+(page-1)*rows);	
			}
		}
		return getJsonPagination(p, page);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateCashier")
	public Result updateCashier(String applyString, DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean, String reimburseType){
		try {
			synchronized (this) {
				//0位直接报销1到5位申请报销
				if(reimburseType.equals("0")) {
					directlyReimbMng.updateCashier(drBean);
				} else {
					reimbAppliMng.updateCashier(rBean);
				}
			}
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false,"操作失败！");
		}
	}
	
}
