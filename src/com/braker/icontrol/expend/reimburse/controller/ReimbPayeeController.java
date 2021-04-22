package com.braker.icontrol.expend.reimburse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.SimplePage;
import com.braker.common.web.BaseController;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.User;
import com.braker.icontrol.expend.reimburse.manager.ReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;

/**
 * 报销申请中的收款人信息控制层
 * @author 赵孟雷
 * @createtime 2021-01-22
 * @updatetime 2021-01-22
 * ClassName: ReimbPayeeController 
 */
@Controller
@RequestMapping(value = "/reimbPayee")
public class ReimbPayeeController extends BaseController {

	@Autowired
	private ReimbPayeeMng reimbPayeeMng;
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng;	
	/**
	 * @param model
	 * @author 赵孟雷
	 * @createtime 2021年1月22日
	 * @updator 赵孟雷
	 * @updatetime 2021年1月22日
	 */
	@RequestMapping(value="/payeeListJsp")
	public String payeeListJsp(ModelMap model, String id, Integer index){
		model.addAttribute("id", id);
		model.addAttribute("index", index);
		return "/WEB-INF/view/expend/reimburse/payee_list";
	}
	
	/*
	 * 分页查询
	 * @author 赵孟雷
	 * @createtime 2021-01-22
	 * @updatetime 2021-01-22
	 */
	@RequestMapping(value = "/reimbPayeePage")
	@ResponseBody
	public JsonPagination reimbPayeePage(PaymentMethodInfo bean, ModelMap model, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = paymentMethodInfoMng.paymentMethodPageList(page, rows, bean);
    	return getJsonPagination(p, page);
	}
}