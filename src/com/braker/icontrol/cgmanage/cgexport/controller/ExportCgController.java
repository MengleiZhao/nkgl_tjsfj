package com.braker.icontrol.cgmanage.cgexport.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.LookupsMng;
import com.braker.core.model.Lookups;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgexport.manager.ExportCgMng;
import com.braker.icontrol.cgmanage.cgreveive.manager.AcceptContractRegisterListMng;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptContractRegisterList;
import com.braker.icontrol.expend.loan.manager.PaymentMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 导出支出管理中的事前申请和事后报销的报销单
 * @author 赵孟雷
 * @createtime 2020-06-02
 * @updatetime 2020-06-02
 *
 */
@Controller
@RequestMapping(value = "/exportCg")
public class ExportCgController extends BaseController{

	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TProExpendDetailMng detailMng;
	
	@Autowired
	private BudgetIndexMgrMng indexMng;
	
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	
	@Autowired
	private PaymentMng paymentMng;
	
	@Autowired
	private CgReceiveMng cgReceiveMng;
	
	@Autowired
	private CgApplysqMng cgApplysqMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private CgConPlanMng confplanMng;
	
	@Autowired
	private LookupsMng lookupsMng;
	
	@Autowired
	private AcceptContractRegisterListMng registerListMng;
	
	@Autowired
	private ExportCgMng exportCgMng;
	
	
	/*
	 * 采购申请打印
	 * @author 赵孟雷
	 * @createtime 2020-08-10
	 * @updatetime 2020-08-10
	 */
	@RequestMapping(value = "/apply")
	public String apply(ModelMap model, Integer id){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		model.addAttribute("bean", bean);
		if(StringUtil.isEmpty(bean.getFpItemsName())){
			bean.setFpItemsNames("-");
		}else{
			Lookups fpItemsNames=lookupsMng.findByLookCode(bean.getFpItemsName());
			bean.setFpItemsNames(fpItemsNames.getName());
		}
		if(StringUtil.isEmpty(bean.getBudgetPriceBasis())){
			bean.setBudgetPriceBasisShow("-");
		}else{
			Lookups budgetPriceBasis=lookupsMng.findByLookCode(bean.getBudgetPriceBasis());
			bean.setBudgetPriceBasisShow(budgetPriceBasis.getName());
		}
		if(StringUtil.isEmpty(bean.getFpMethod())){
			bean.setFpMethods("-");
		}else{
			Lookups fpMethods=lookupsMng.findByLookCode(bean.getFpMethod());
			bean.setFpMethods(fpMethods.getName());
		}
		if(StringUtil.isEmpty(bean.getFpPype())){
			bean.setFpPypes("-");
		}else{
			Lookups fpPypes=lookupsMng.findByLookCode(bean.getFpPype());
			bean.setFpPypes(fpPypes.getName());
		}
		
		List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fpId", bean.getFpId());
		//采购清单
		model.addAttribute("mingxiList", mingxiList);
		return "/WEB-INF/view/purchase_manage/cgexport/cg_apply";
	}
	/*
	 * 采购申请打印签报
	 * @author 赵孟雷
	 * @createtime 2020-08-28
	 * @updatetime 2020-08-28
	 */
	@RequestMapping(value = "/applySign")
	public String applySign(ModelMap model, Integer id){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(id);
		String times ="";
		if(!"".equals(bean.getfReqTime())){
			DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
			times =fmt.format(bean.getfReqTime());     // 转换成 X年X月X日
		}
		bean.setfReqTimes(times);
		model.addAttribute("bean", bean);
		List<TProcessPrintDetail> listTProcessChecks= new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("PurchaseApplyBasic", "fpId", bean.getFpId());
		model.addAttribute("listTProcessChecks", listTProcessChecks);
		return "/WEB-INF/view/purchase_manage/cgexport/applySign";
	}
	
	
	/*
	 * 采购验收打印
	 * @author 赵孟雷
	 * @createtime 2020-08-10
	 * @updatetime 2020-08-10
	 */
	@RequestMapping(value = "/receive")
	public String receive(ModelMap model, Integer id,String fpItemsName) throws Exception{
		//查询采购验收基本信息
		AcceptCheck bean = cgReceiveMng.findById(id);
		model.addAttribute("bean", bean);
		if("PMMC-4".equals(fpItemsName)||"PMMC-5".equals(fpItemsName)){
			List<AcceptContractRegisterList> list = new ArrayList<AcceptContractRegisterList>();
			if(!"".equals(String.valueOf(id))) {
				//查询采购明细
				list = registerListMng.findFpIdbyMingxi(String.valueOf(id));
			}
			List<TProcessPrintDetail> listTProcessChecks=  new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("AcceptCheck", "facpId", bean.getFacpId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			//验收采购清单
			model.addAttribute("mingxiList", list);
			return "/WEB-INF/view/purchase_manage/cgexport/cg_receive_check";
		}else{
			List<AcceptContractRegisterList> list = new ArrayList<AcceptContractRegisterList>();
			if(!"".equals(String.valueOf(id))) {
				//查询采购明细
				list = registerListMng.findFpIdbyMingxi(String.valueOf(id));
			}
			//验收采购清单
			model.addAttribute("mingxiList", list);
			return "/WEB-INF/view/purchase_manage/cgexport/cg_receive";
		}
		
	}
}
