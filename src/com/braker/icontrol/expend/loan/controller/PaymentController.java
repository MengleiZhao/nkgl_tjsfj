package com.braker.icontrol.expend.loan.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PaymentMethodInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.expend.loan.manager.LoanMng;
import com.braker.icontrol.expend.loan.manager.PaymentMng;
import com.braker.icontrol.expend.loan.manager.RepaymentHistoryRecordsMng;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.loan.model.LoanPayeeInfo;
import com.braker.icontrol.expend.loan.model.Payment;
import com.braker.icontrol.expend.loan.model.RepaymentHistoryRecords;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimCXInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@Controller
@RequestMapping(value = "/payment")
public class PaymentController extends BaseController {
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private PaymentMng paymentMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private AttachmentMng attachmentMng;

	@Autowired
	private ReimbAppliMng reimbAppliMng;
	@Autowired
	private LoanMng loanMng;
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng;
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	@Autowired
	private RepaymentHistoryRecordsMng repaymentHistoryRecordsMng;
	
	@RequestMapping(value = "/list")
	public String list(String menuType, ModelMap model){
		
		model.addAttribute("menuType", menuType);
		if(menuType.equals("2")){
			return "/WEB-INF/view/expend/loan/payment_checkList";
		}else{
			return "/WEB-INF/view/expend/loan/payment_list";
		}
	}
	
	@RequestMapping(value = "/add")
	public String add(ModelMap model,String id){
		Payment bean =new Payment();
		if (!StringUtil.isEmpty(id)) {
			Payment payment = paymentMng.findByLId(Integer.valueOf(id));
			if (payment != null) {
				bean = payment;
				// 查询附件信息
				List<Attachment> attaList = attachmentMng.list(payment);
				model.addAttribute("attaList", attaList);
			} else {
				bean.setPayTime(new Date());
				bean.setCode(StringUtil.Random("HK"));
			}
		}
		model.addAttribute("bean", bean);
		
		//查询工作流
		//根据资源名称和当前登陆者所属部门查询对应工作流
		//查询工作流
		LoanBasicInfo loan=loanMng.findById(Integer.valueOf(id));
		loan.setUserName(getUser().getName());
		model.addAttribute("loan", loan);
		List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanId(id);
		List<ReimbAppliBasicInfo> applyReimbList =reimbAppliMng.findByLoanId(id);
		List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
		Double totalCxAmount =0.00;
		if(applyReimbList !=null && applyReimbList.size()>0){
			for(int i = 0;i < applyReimbList.size();i++){
				if(applyReimbList.get(i).getCxAmount() !=null){
					totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
				}else{
					totalCxAmount =0+totalCxAmount;
				}
				ReimCXInfo info = new ReimCXInfo();
				info.setrId(applyReimbList.get(i).getrId());
				info.setgName(applyReimbList.get(i).getgName());
				info.setType(1);
				info.setCxAmount(applyReimbList.get(i).getCxAmount());
				info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
				reimbList.add(info);
			}
		}
		if(directlyList !=null && directlyList.size()>0){
			for(int i = 0;i < directlyList.size();i++){
				if(directlyList.get(i).getCxAmount() !=null){
					totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
				}else{
					totalCxAmount =0+totalCxAmount;
				}
				ReimCXInfo info = new ReimCXInfo();
				info.setrId(directlyList.get(i).getDrId());
				info.setgName(directlyList.get(i).getSummary());
				info.setType(0);
				info.setCxAmount(directlyList.get(i).getCxAmount());
				info.setReimburseReqTime(directlyList.get(i).getReqTime());
				reimbList.add(info);
			}
		}
		int a = 0;
		int b = 0;
		if(applyReimbList.isEmpty()){
			a =0;
		}else{
			a=applyReimbList.size();
		}
		if(directlyList.isEmpty()){
			b=0;
		}else{
			b=directlyList.size();
		}
		model.addAttribute("cxNum",  a+b);
		model.addAttribute("totalCxAmount", totalCxAmount);
		model.addAttribute("reimbList", reimbList);
		//查询个人收款信息
		LoanPayeeInfo payeeBean = new LoanPayeeInfo();
		List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(getUser().getId());
		if(infoList.size()>0) {
			payeeBean.setBank(infoList.get(0).getBank());//银行
			payeeBean.setIdCard(infoList.get(0).getIdCard());//银行
			payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
			payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
			payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
			payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
			payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
			payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
			payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
		}
		model.addAttribute("payee", payeeBean);
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(),"HKDJ", getUser().getDpID(),null,null, null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		// 建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(),getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		
		model.addAttribute("opeType", "add");
		return "/WEB-INF/view/expend/loan/payment_add";
	}
	
	@RequestMapping(value = "/detailHK")
	public String detailHK(ModelMap model,String id,String cxIndex){
		
		//查询工作流
		//根据资源名称和当前登陆者所属部门查询对应工作流
		//查询工作流
		LoanBasicInfo loan=loanMng.findById(Integer.valueOf(id));
		
		Payment bean = paymentMng.findByLId(Integer.valueOf(id));
		if(bean==null){
			bean = new Payment();
		}
		model.addAttribute("bean", bean);
		
		loan.setUserName(getUser().getName());
		model.addAttribute("loan", loan);
		List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanId(id);
		List<ReimbAppliBasicInfo> applyReimbList =reimbAppliMng.findByLoanId(id);
		List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
		Double totalCxAmount =0.00;
		if(applyReimbList !=null && applyReimbList.size()>0){
			for(int i = 0;i < applyReimbList.size();i++){
				if(applyReimbList.get(i).getCxAmount() !=null){
					totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
				}else{
					totalCxAmount =0+totalCxAmount;
				}
				ReimCXInfo info = new ReimCXInfo();
				info.setrId(applyReimbList.get(i).getrId());
				info.setgName(applyReimbList.get(i).getgName());
				info.setType(1);
				info.setCxAmount(applyReimbList.get(i).getCxAmount());
				info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
				reimbList.add(info);
			}
		}
		if(directlyList !=null && directlyList.size()>0){
			for(int i = 0;i < directlyList.size();i++){
				if(directlyList.get(i).getCxAmount() !=null){
					totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
				}else{
					totalCxAmount =0+totalCxAmount;
				}
				ReimCXInfo info = new ReimCXInfo();
				info.setrId(directlyList.get(i).getDrId());
				info.setgName(directlyList.get(i).getSummary());
				info.setType(0);
				info.setCxAmount(directlyList.get(i).getCxAmount());
				info.setReimburseReqTime(directlyList.get(i).getReqTime());
				reimbList.add(info);
			}
		}
		int a = 0;
		int b = 0;
		if(applyReimbList.isEmpty()){
			a =0;
		}else{
			a=applyReimbList.size();
		}
		if(directlyList.isEmpty()){
			b=0;
		}else{
			b=directlyList.size();
		}
		model.addAttribute("cxNum",  a+b);
		model.addAttribute("totalCxAmount", totalCxAmount);
		model.addAttribute("reimbList", reimbList);
		//查询个人收款信息
		LoanPayeeInfo payeeBean = new LoanPayeeInfo();
		List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(getUser().getId());
		if(infoList.size()>0) {
			payeeBean.setBank(infoList.get(0).getBank());//银行
			payeeBean.setIdCard(infoList.get(0).getIdCard());//银行
			payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
			payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
			payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
			payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
			payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
			payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
			payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
		}
		model.addAttribute("payee", payeeBean);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"HKDJ",bean.getApplyDepId(),bean.getBeanCode(),bean.getNextNodeCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getApplier(), bean.getApplyDepName(), bean.getCreatetime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HKDJ", bean.getApplyDepId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());	
		//附件信息
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("attaList", attaList);
		
		model.addAttribute("opeType", "detail");
		model.addAttribute("cxIndex", cxIndex);
		return "/WEB-INF/view/expend/loan/payment_detail";
	}
	
	/**
	 * 跳转到选择借款单页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/choicePayMent")
	public String choicePayMent(ModelMap model){
		
		return "/WEB-INF/view/expend/loan/payment_choiceLoan_list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, ModelMap model){
		
		Payment bean =  paymentMng.findByLId(Integer.valueOf(id));;
		LoanBasicInfo loan=loanMng.findById(Integer.valueOf(bean.getlId()));
		//查询申请人信息
		User user = userMng.findById(loan.getUser());
		loan.setUserName(user.getName());
		model.addAttribute("loan", loan);
		List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanId(id);
		List<ReimbAppliBasicInfo> applyReimbList =reimbAppliMng.findByLoanId(id);
		List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
		Double totalCxAmount =0.00;
		if(applyReimbList !=null && applyReimbList.size()>0){
			for(int i = 0;i < applyReimbList.size();i++){
				if(applyReimbList.get(i).getCxAmount() !=null){
					totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
				}else{
					totalCxAmount =0+totalCxAmount;
				}
				ReimCXInfo info = new ReimCXInfo();
				info.setrId(applyReimbList.get(i).getrId());
				info.setgName(applyReimbList.get(i).getgName());
				info.setType(1);
				info.setCxAmount(applyReimbList.get(i).getCxAmount());
				info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
				reimbList.add(info);
			}
		}
		if(directlyList !=null && directlyList.size()>0){
			for(int i = 0;i < directlyList.size();i++){
				if(directlyList.get(i).getCxAmount() !=null){
					totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
				}else{
					totalCxAmount =0+totalCxAmount;
				}
				ReimCXInfo info = new ReimCXInfo();
				info.setrId(directlyList.get(i).getDrId());
				info.setgName(directlyList.get(i).getSummary());
				info.setType(0);
				info.setCxAmount(directlyList.get(i).getCxAmount());
				info.setReimburseReqTime(directlyList.get(i).getReqTime());
				reimbList.add(info);
			}
		}
		int a = 0;
		int b = 0;
		if(applyReimbList.isEmpty()){
			a =0;
		}else{
			a=applyReimbList.size();
		}
		if(directlyList.isEmpty()){
			b=0;
		}else{
			b=directlyList.size();
		}
		model.addAttribute("cxNum",  a+b);
		model.addAttribute("totalCxAmount", totalCxAmount);
		model.addAttribute("reimbList", reimbList);
		//查询个人收款信息
		LoanPayeeInfo payeeBean = new LoanPayeeInfo();
		List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
		if(infoList.size()>0) {
			payeeBean.setBank(infoList.get(0).getBank());//银行
			payeeBean.setIdCard(infoList.get(0).getIdCard());//银行
			payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
			payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
			payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
			payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
			payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
			payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
			payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
		}
		model.addAttribute("payee", payeeBean);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"HKDJ",bean.getApplyDepId(),bean.getBeanCode(),bean.getNextNodeCode(),bean.getJoinTable(), bean.getBeanCodeField(),  bean.getCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getApplier(), bean.getApplyDepName(), bean.getCreatetime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HKDJ", bean.getApplyDepId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());	
		
		model.addAttribute("bean", bean);
		model.addAttribute("opeType", "edit");
		
		// 查询附件信息
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("attaList", attaList);
		return "/WEB-INF/view/expend/loan/payment_add";
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable Integer id,String fId) {

		try {
			paymentMng.deletePayment(id,getUser(),fId);
			return getJsonResult(true, "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "删除失败，请联系管理员！");
		}
	}
	
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable String id, ModelMap model){
		
		try {
			Payment bean = paymentMng.findById(Integer.valueOf(id));
			LoanBasicInfo loan=loanMng.findById(Integer.valueOf(bean.getlId()));
			//查询申请人信息
			User user = userMng.findById(loan.getUser());
			loan.setUserName(user.getName());
			model.addAttribute("loan", loan);
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanId(String.valueOf(loan.getlId()));
			List<ReimbAppliBasicInfo> applyReimbList =reimbAppliMng.findByLoanId(String.valueOf(loan.getlId()));
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			int a = 0;
			int b = 0;
			if(applyReimbList.isEmpty()){
				a =0;
			}else{
				a=applyReimbList.size();
			}
			if(directlyList.isEmpty()){
				b=0;
			}else{
				b=directlyList.size();
			}
			model.addAttribute("cxNum",  a+b);
			model.addAttribute("totalCxAmount", totalCxAmount);
			model.addAttribute("reimbList", reimbList);
			//查询个人收款信息
			LoanPayeeInfo payeeBean = new LoanPayeeInfo();
			List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
			if(infoList.size()>0) {
				payeeBean.setBank(infoList.get(0).getBank());//银行
				payeeBean.setIdCard(infoList.get(0).getIdCard());//银行
				payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
				payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
				payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
				payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
				payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
				payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
				payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
			}
			model.addAttribute("payee", payeeBean);
			/*//查询工作流
			//根据资源名称和当前登陆者所属部门查询对应工作流
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf("HKDJ", getUser().getDpID(),null,null);
			model.addAttribute("nodeConf", nodeConfList);
			// 建立工作流发起人的信息
			Proposer proposer = new Proposer(getUser().getName(),getUser().getDepartName(), null);
			model.addAttribute("proposer", proposer);*/
			
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"HKDJ",bean.getApplyDepId(),bean.getBeanCode(),bean.getNextNodeCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getCode(),"1");
			model.addAttribute("nodeConf", nodeConfList);
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getApplier(), bean.getApplyDepName(), bean.getCreatetime());
			model.addAttribute("proposer", proposer);
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HKDJ", bean.getApplyDepId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());	
			
			
			model.addAttribute("bean", bean);
			model.addAttribute("opeType", "detail");
			model.addAttribute("basicInfo", loan);
			// 查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			model.addAttribute("attaList", attaList);
			
			return "/WEB-INF/view/expend/loan/payment_add";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping(value = "/pageList")
	@ResponseBody
	public JsonPagination noticePage(Payment bean, String menuType, Integer page, Integer pageSize){
		
		if(page == null){page = 1;}
    	if(pageSize == null){pageSize = SimplePage.DEF_COUNT;}
    	
    	Pagination p = paymentMng.pageList(bean, getUser(), menuType, page, pageSize);
    	
    	return getJsonPagination(p, page);
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(Payment bean,String files,ModelMap model) {
		
		try {
			LoanBasicInfo loanBean = loanMng.findById(bean.getlId());
			List<ReimbAppliBasicInfo> reimburseList = reimbAppliMng.findByLid(bean.getlId());
			if(reimburseList.size()>0){
				return getJsonResult(false,"该借款单正在冲销！");
			}
			int datenum = DateUtil.getDaySpanNoAbs(bean.getPayTime(),loanBean.getReqTime());
			if(datenum>0){
				return getJsonResult(false,"还款时间必须大于借款时间！");
			}
			bean = paymentMng.savePayment(bean, files, getUser());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	@RequestMapping(value = "/check/{id}")
	public String check(@PathVariable Integer id, ModelMap model){
		
		Payment bean = paymentMng.findById(Integer.valueOf(id));
		LoanBasicInfo loan=loanMng.findById(Integer.valueOf(bean.getlId()));
		//查询申请人信息
		User user = userMng.findById(loan.getUser());
		loan.setUserName(user.getName());
		model.addAttribute("loan", loan);
		List<ReimbAppliBasicInfo> reimbList =reimbAppliMng.findByLoanId(String.valueOf(loan.getlId()));
		Double totalCxAmount =0d;
		if(reimbList !=null && reimbList.size()>0){
			model.addAttribute("cxNum",  reimbList.size());
			for(int i = 0;i < reimbList.size();i++){
				Double cxMon = 0.00;
				if(reimbList.get(i).getCxAmount()==null){
					cxMon = 0.00;
				}else{
					cxMon = reimbList.get(i).getCxAmount();
				}
				totalCxAmount =cxMon+totalCxAmount;
			}
		}
		model.addAttribute("totalCxAmount", totalCxAmount);
		model.addAttribute("reimbList", reimbList);
		//查询个人收款信息
				LoanPayeeInfo payeeBean = new LoanPayeeInfo();
				List<PaymentMethodInfo> infoList = paymentMethodInfoMng.findByPayeeId(user.getId());
				if(infoList.size()>0) {
					payeeBean.setBank(infoList.get(0).getBank());//银行
					payeeBean.setBankAccount(infoList.get(0).getBankAccount());//银行账户
					payeeBean.setBankName(infoList.get(0).getBankName());//银行名称
					payeeBean.setZfbAccount(infoList.get(0).getZfbAccount());//支付宝账户
					payeeBean.setZfbQR(infoList.get(0).getZfbQR());//支付宝二维码地址
					payeeBean.setWxAccount(infoList.get(0).getWxAccount());//微信账户
					payeeBean.setWxQR(infoList.get(0).getWxQR());//微信二维码地址
					payeeBean.setIdCard(infoList.get(0).getIdCard());//身份证号
				}
				model.addAttribute("payee", payeeBean);
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"HKDJ",bean.getApplyDepId(),bean.getBeanCode(),bean.getNextNodeCode(),bean.getJoinTable(), bean.getBeanCodeField(), bean.getCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(bean.getApplier(), bean.getApplyDepName(), bean.getCreatetime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HKDJ", bean.getApplyDepId());
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());	
		
		model.addAttribute("bean", bean);
		model.addAttribute("opeType", "edit");
		
		// 查询附件信息
		List<Attachment> attaList = attachmentMng.list(bean);
		model.addAttribute("attaList", attaList);
		return "/WEB-INF/view/expend/loan/payment_check";
	}
	
	@RequestMapping(value = "/checkSave")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean, Payment bean,String spjlFile) {
		
		try {
			List<Role> roleli = userMng.getUserRole(getUser().getId());
			bean = paymentMng.findById(Integer.valueOf(bean.getId()));
			
			paymentMng.saveCheckPayment(checkBean, bean, getUser(), roleli.get(0), spjlFile);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			return getJsonResult(false,"操作失败！");
		}
	}
	
	
	/**
	 * @Description: 还款退回
	 * @param @param id
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author 赵孟雷
	 * @date 2019年10月8日
	 */
	@RequestMapping(value = "/paymentReCall")
	@ResponseBody
	public Result paymentReCall(Integer id) {
		try {
			//传回来的id是主键
			paymentMng.paymentReCall(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"撤回失败，请联系管理员！");
		}
		return getJsonResult(true,"撤回成功！");	
	}
	
	
	@RequestMapping(value = "/RepaymentHistoryRecordsPageList")
	@ResponseBody
	public JsonPagination RepaymentHistoryRecordsPageList(Integer lId){
		
    	Pagination p = repaymentHistoryRecordsMng.pageList(lId);
    	
    	return getJsonPagination(p, 1);
	}
}
