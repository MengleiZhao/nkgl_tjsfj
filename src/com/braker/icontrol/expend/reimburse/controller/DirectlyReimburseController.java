package com.braker.icontrol.expend.reimburse.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.DateUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.EconomicMng;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Economic;
import com.braker.core.model.Lookups;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.expend.audit.manager.AuditInfoMng;
import com.braker.icontrol.expend.cashier.manager.CashierMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbAttacMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbDetailMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceCouponMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbPayeeMng;
import com.braker.icontrol.expend.reimburse.model.CostDetailInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbDetail;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbPayeeInfo;
import com.braker.icontrol.expend.reimburse.model.InvoiceCouponInfo;
import com.braker.icontrol.expend.reimburse.model.InvoiceInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 直接报销申请的控制层
 * @author 叶崇晖
 * @createtime 2018-08-03
 * @updatetime 2018-08-03
 */
@Controller
@RequestMapping(value = "/directlyReimburse")
public class DirectlyReimburseController extends BaseController {
	
	@Autowired
	private TProExpendDetailMng detailMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	
	@Autowired
	private DirectlyReimbDetailMng directlyReimbDetailMng;
	
	@Autowired
	private DirectlyReimbPayeeMng directlyReimbPayeeMng;
	
	@Autowired
	private DirectlyReimbAttacMng directlyReimbAttacMng;
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private AuditInfoMng auditInfoMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private CashierMng cashierMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng;
	
	@Autowired
	private InvoiceMng invoiceMng;
	
	@Autowired
	private EconomicMng economicMng;
	
	@Autowired
	private InvoiceCouponMng invoiceCouponMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;

	@Autowired
	private ReimbAppliMng reimbAppliMng;
	
	@Autowired
	private ReimbPayeeMng reimbPayeeMng;
	/*
	 * 跳转到列表页面
	 * @author 叶崇晖
	 * @createtime 2018-08-03
	 * @updatetime 2018-08-03
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/expend/reimburse/directly_reimburse/directly_reimburse_list";
	}
	
	/*
	 * 分页数据获得
	 * @author 叶崇晖
	 * @createtime 2018-08-03
	 * @updatetime 2018-08-03
	 */
	@RequestMapping(value = "/reimbursePage")
	@ResponseBody
	public JsonPagination applyPage(DirectlyReimbAppliBasicInfo bean, ModelMap model, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = directlyReimbMng.pageList(bean, page, rows, getUser(),searchData);
		List<DirectlyReimbAppliBasicInfo> li = (List<DirectlyReimbAppliBasicInfo>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
		return getJsonPagination(p, page);
	}
	
	/*
	 * 跳转新增页面
	 * @author 叶崇晖
	 * @createtime 2018-08-03
	 * @updatetime 2018-08-03
	 */
	@RequestMapping("/add")
	public String add(ModelMap model) {
		//查询基本信息
		DirectlyReimbAppliBasicInfo bean = new DirectlyReimbAppliBasicInfo();
		User user = getUser();
		bean.setSummary(user.getName()+"-直接报销-"+DateUtil.formatDate(new Date()));
		bean.setUserName(user.getName());
		bean.setUser(user.getId());
		bean.setDeptName(user.getDepartName());
		bean.setDept(user.getDpID());
		bean.setReqTime(new Date());
		model.addAttribute("bean", bean);
		
		/*//查询个人收款信息
		DirectlyReimbPayeeInfo payeeBean = new DirectlyReimbPayeeInfo();
		List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
		if(infoList.size()>0) {
			payeeBean.setPayeeIdCard((infoList.get(0).getPayeeIdCard()));//身份证号
			payeeBean.setBank(infoList.get(0).getBank());//银行
			payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
			payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
			payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
			payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
			payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
			payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
			payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
			payeeBean.setPayeeName(infoList.get(0).getPayeeName());//姓名
			
		}
		model.addAttribute("payee", payeeBean);*/
		
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"ZJBXLX-2", user.getDpID(),null,null, null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), user.getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("直接报销");
		model.addAttribute("cheterInfo", cheterInfo);
		model.addAttribute("detail", "add");
		return "/WEB-INF/view/expend/reimburse/directly_reimburse/directly_reimburse_add2";
	}
	

	/*
	 * 查询明细
	 * @author 叶崇晖
	 * @createtime 2018-08-04
	 * @updatetime 2018-08-04
	 */
	@RequestMapping(value = "/mingxi")
	@ResponseBody
	public JsonPagination mingxi(Integer id) {
		Pagination p = new Pagination();
		List<DirectlyReimbDetail> li = directlyReimbDetailMng.getMingxi(id);
		p.setList(li);
		return getJsonPagination(p, 0);
	}
	
	/*
	 * 查询发票信息
	 * @author 叶崇晖
	 * @createtime 2018-08-07
	 * @updatetime 2018-08-07
	 */
	@RequestMapping(value = "/invoice")
	@ResponseBody
	public JsonPagination invoice(Integer id) {
		Pagination p = new Pagination();
		/*List<InvoiceInfo> list = invoiceMng.findByRID("1", id);
		
		for (int i = 0; i < list.size(); i++) {
			List<InvoiceCouponInfo> couponList = invoiceCouponMng.findByIID(list.get(i).getiId());
			list.get(i).setCouponList(couponList);
		}
		
		
		p.setList(list);*/
		return getJsonPagination(p, 0);
	}
	/*
	 * 跳转修改,查看页面
	 * @author 叶崇晖
	 * @createtime 2018-08-06
	 * @updatetime 2018-08-06
	 */
	@RequestMapping("/edit")
	public String edit(Integer id, ModelMap model ,String editType) {
		//传回来的id是主键
		try {
			DirectlyReimbAppliBasicInfo bean = directlyReimbMng.findById(id);
			User user = userMng.findById(bean.getUser());
			bean.setUserName(user.getName());
			bean.setDeptName(user.getDepartName());
			
			model.addAttribute("bean", bean);
			model.addAttribute("expItemCode", bean.getEconomicCode());
			List<DirectlyReimbPayeeInfo> listDirectlyReimbPayeeInfo = directlyReimbPayeeMng.getByDrId(id);
					
/*		//查询附件信息
			List<DirectlyReimbAttac> attac = directlyReimbAttacMng.getByDrId(id);
			model.addAttribute("attac", attac);*/
			
			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			model.addAttribute("attaList", attaList);
			
			List<InvoiceCouponInfo> list1 = invoiceCouponMng.findByrID(bean.getDrId(),"directly-1");
			model.addAttribute("Invoicelist", list1);//发票
			String strType = "";
			if("ZJBXLX-0".equals(bean.getDirType())){
				strType = "ZJBXLX-0";
			}
			if("ZJBXLX-1".equals(bean.getDirType())){
				strType = "ZJBXLX-1";
			}
			if("ZJBXLX-2".equals(bean.getDirType())){
				strType = "ZJBXLX-2";
			}
			
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType, bean.getDept(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getDrCode(),"1");
			model.addAttribute("nodeConf", nodeConfList);
			
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编号
			model.addAttribute("foCode",bean.getBeanCode());
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);
			//查询制度中心文件
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("直接报销");
			model.addAttribute("cheterInfo", cheterInfo);
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
					//预算年度
					if (index.getAppDate() != null) {
						bean.setPfDate(index.getYears());				
					}
					//使用部门
					bean.setPfDepartName(index.getDeptName());			
					//可用余额
					bean.setSyAmount(detail.getSyAmount());			
				}
			}else if(indexId != null){
				TBudgetIndexMgr index = indexMng.findById(indexId);
				//指标名称
				bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
				//批复金额
				bean.setPfAmount(index.getPfAmount());		
				//预算年度
				if (index.getAppDate() != null) {
					bean.setPfDate(index.getYears());				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(index.getSyAmount());			
			}
			//查询费用明细
			List<CostDetailInfo> cost =reimbAppliMng.findByDrId(bean.getDrId());
			Integer x=0;
			Integer y=0;
			for(int i = 0;i <cost.size();i++){
				y=y+1;
				List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
				for(int j = 0;j <fp.size();j++){
					x=x+1;
					fp.get(j).setNum(x);
				}
				cost.get(i).setCouponList(fp);
				cost.get(i).setNum(y);
			}
			Integer index =cost.size();
			model.addAttribute("x", x);
			model.addAttribute("y", y);
			model.addAttribute("index", index);
			model.addAttribute("cost", cost);
			if(editType.equals("1")){
				model.addAttribute("detail", "2");
				return "/WEB-INF/view/expend/reimburse/directly_reimburse/directly_reimburse_add2";
			} else if(editType.equals("0")){
				model.addAttribute("detail", "1");
				return "/WEB-INF/view/expend/reimburse/directly_reimburse/directly_reimburse_detail";
			} else {
				return null;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return editType; 
	}
	/*
	 * 首页跳转查看页面
	 * @author 赵孟雷
	 * @createtime 2018-08-06
	 * @updatetime 2018-08-06
	 */
	@RequestMapping("/edit1")
	public String edit1(Integer id, ModelMap model ,String editType) {
		//传回来的id是主键
		DirectlyReimbAppliBasicInfo bean = directlyReimbMng.findById(id);
		User user = userMng.findById(bean.getUser());
		bean.setUserName(user.getName());
		bean.setDeptName(user.getDepartName());
		
		model.addAttribute("bean", bean);
		
		//查询收款人信息
		DirectlyReimbPayeeInfo payeeBean = directlyReimbPayeeMng.getByDrId(id).get(0);
		List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
		if(infoList.size()>0) {
			payeeBean.setBank(infoList.get(0).getBank());//银行
			payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
			payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
			payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
			payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
			payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
		}
		model.addAttribute("payee", payeeBean);
		
		/*		//查询附件信息
		List<DirectlyReimbAttac> attac = directlyReimbAttacMng.getByDrId(id);
		model.addAttribute("attac", attac);*/
		
		//查询附件信息
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("attaList", attaList);
		String strType = "";
		if("ZJBXLX-0".equals(bean.getDirType())){
			strType = "ZJBXLX-0";
		}
		if("ZJBXLX-1".equals(bean.getDirType())){
			strType = "ZJBXLX-1";
		}
		if("ZJBXLX-2".equals(bean.getDirType())){
			strType = "ZJBXLX-2";
		}
		
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),strType, bean.getDept(),bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(),bean.getBeanCodeField(), bean.getDrCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(strType, bean.getDept());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编号
		model.addAttribute("foCode",bean.getBeanCode());
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), user.getDepartName(), bean.getReqTime());
		model.addAttribute("proposer", proposer);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("直接报销");
		model.addAttribute("cheterInfo", cheterInfo);
		
		if(editType.equals("1")){
			return "/WEB-INF/view/expend/reimburse/directly_reimburse/directly_reimburse_add2";
		} else if(editType.equals("0")){
			model.addAttribute("detail", "1");
			return "/WEB-INF/view/expend/reimburse/directly_reimburse/directly_reimburse_detail1";
		} else {
			return null;
		}
	}
	
	/*
	 * 直接报销申请新增和修改的保存
	 * @author 叶崇晖
	 * @createtime 2018-08-06
	 * @updatetime 2018-08-06
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(DirectlyReimbAppliBasicInfo bean, DirectlyReimbPayeeInfo payeeBean,String mingxi,String fapiao,
					   String files,String form1,ModelMap model,String arry,String payerinfoJson,	//内部收款人json
																			String payerinfoJsonExt	//外部收款人json
						) {
		try {
			directlyReimbMng.save1(bean, payeeBean,files, getUser(),mingxi,form1,payerinfoJson,payerinfoJsonExt);
		}catch (ServiceException e1) {
			e1.printStackTrace();
			return getJsonResult(false,e1.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 直接报销申请删除
	 * @author 叶崇晖
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id,String fId) {
		try {
			//传回来的id是主键
			directlyReimbMng.delete(id,fId,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	
	
	/**
	 * @Description: 直接报销退回
	 * @param @param id
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author 赵孟雷
	 * @date 2019年10月8日
	 */
	@RequestMapping(value = "/directlyReimburseReCall")
	@ResponseBody
	public Result directlyReimburseReCall(Integer id) {
		try {
			//传回来的id是主键
			directlyReimbMng.directlyReimbReCall(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"撤回失败，请联系管理员！");
		}
		return getJsonResult(true,"撤回成功！");	
	}
	
	/**
	 * 根据报销单id查收款人信息列表
	 * @param payerbean
	 * @param bean
	 * @param reimburseType
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年5月9日
	 * @updator 陈睿超
	 * @updatetime 2020年5月9日
	 */
	@ResponseBody
	@RequestMapping(value = "/payerInfojson")
	public JsonPagination payerInfojson(ReimbPayeeInfo payerbean,ReimbAppliBasicInfo bean, String reimburseType, ModelMap model, Integer page, Integer rows,String fInnerOrOuter) {
		if(page == null){page = 1;}
		Pagination p = new Pagination();
		List<ReimbPayeeInfo> list = reimbPayeeMng.getByDrId(payerbean.getDrId(),fInnerOrOuter);
		p.setList(list);
		p.setPageNo(1);
		p.setPageSize(list.size());
		p.setTotalCount(list.size());
		return getJsonPagination(p, page);
	}
	
	/**
	 * 查询字典里相应数据
	 * @param parentCode
	 * @param selected
	 * @param blanked
	 * @return
	 */
	@RequestMapping(value = "/lookupsJson")
	@ResponseBody
	public List<ComboboxJson> lookJson(String parentCode,String selected,String blanked){
		User user = getUser();
		if("32".equals(user.getDepart().getId())){
			blanked = "ZJBXLX-1";
		}
		if("7".equals(user.getDepart().getId())){
			blanked = "ZJBXLX-0";
		}
		
		List<Lookups> list = directlyReimbMng.getLookupsJson(parentCode, blanked,selected);
		if(!"7".equals(user.getDepart().getId()) && !"32".equals(user.getDepart().getId())){
			for (int i = list.size()-1; i >= 0; i--) {
				if("ZJBXLX-1".equals(list.get(i).getCode()) || "ZJBXLX-0".equals(list.get(i).getCode())){
					list.remove(i);
				}
			}
		}
		return getComboboxJson(list,selected);
	}
}
