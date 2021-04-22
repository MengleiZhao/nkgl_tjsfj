package com.braker.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.Result;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.assets.handle.manager.HandleMng;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.icontrol.assets.rece.manager.ReceMng;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.returns.manager.AssetReturnMng;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.budget.adjust.entity.TIndexInnerAd;
import com.braker.icontrol.budget.adjust.manager.InsideAdjustMny;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProCheckInfoMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgcheck.manager.CgCheckMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgreveive.manager.CgReceiveMng;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.contract.Formulation.manager.ApprovalMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.expend.apply.manager.ApplyCheckMng;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.loan.manager.LoanCheckMng;
import com.braker.icontrol.expend.loan.manager.PaymentMng;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.loan.model.Payment;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TProcessCheck;


/**
 * APP审批
 * 作用：接受APP后台发送的审批请求
 * 实现方式：请求转发
 * 返回请求端值的类型：由转发后方法定义
 * @author 张迅
 * @createtime 2020-04-13
 */

@Controller
@RequestMapping(value = "/appCheck")
public class AppCheckController extends BaseController{
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private ApprovalMng approvalMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private ApplyMng applyMng;
	@Autowired
	private ApplyCheckMng applyCheckMng;
	@Autowired
	private CgCheckMng cgcheckMng;
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private CgReceiveMng cgReceiveMng;
	@Autowired
	private RegisterApplyMng registerApplyMng;
	//localhost:8080/nkgl/appCheck/ysbz
	@Autowired
	private TProBasicInfoMng projectMng;
	@Autowired
	private TProCheckInfoMng tProCheckInfoMng;
	@Autowired
	private InsideAdjustMny insideAdjustMny;
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	@Autowired
	private ReceMng receMng;
	@Autowired
	private HandleMng handleMng;
	@Autowired
	private AssetReturnMng assetReturnMng;
	@Autowired
	private LoanCheckMng loanCheckMng;
	@Autowired
	private PaymentMng paymentMng;
	//项目审批
/*	@RequestMapping("/xmsp")
	public String xmsp(){
		return "forward:/project/saveVerdictApp";
//		return "forward:/project/checkResultApp";
	}*/
	@RequestMapping("/check_xmsb")
	@ResponseBody
	public Result check_xmsb(TProBasicInfo newBean, TProcessCheck checkBean, String fileId,
			String acco, String pwd) {
		try {
			User user = userMng.login(acco, pwd);
			//修改前的bean数据
			TProBasicInfo oldBean = projectMng.findById(newBean.getFProId());
//			//比较后返回用于审批的bean
//			TProBasicInfo resultBean = projectMng.compareFields(oldBean, newBean, null, null, null, totalityPerformanceJson, user);
			projectMng.saveCheck(oldBean,user, checkBean, fileId);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}

	//二上项目审批
	/*@RequestMapping("/essp")
	public String essp(){
		return "forward:/declare/esspApp";
	}*/
	
	@RequestMapping("/check_essb")
	@ResponseBody
	public Result check_essb(String fproIdLi,String result, String remake,String spjlFiles, ModelMap model,
			String acco, String pwd){
		try {
			User user = userMng.login(acco, pwd);
			tProCheckInfoMng.secondUpCheck(fproIdLi, result, remake, user, user.getRoles().get(0),spjlFiles);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
	}
	//预算编制
	@RequestMapping("/ysbz")
	public String ysbz(){
		return "forward:/declare/esspApp";
	}
	
/*	//指标调整
	@RequestMapping("/zbtz")
	public String zbtz(String acco, String pwd, String interfaceCode){
		
		return "forward:/insideCheck/checkResultApp";
	}*/
	
	@RequestMapping(value = "/check_zbtz")
	@ResponseBody
	public Result check_zbtz(TProcessCheck checkBean,TIndexInnerAd bean, String fileId,
			String acco, String pwd) {
		try {
			User user = userMng.login(acco, pwd);
			insideAdjustMny.saveCheckInfo(checkBean, bean, user, fileId);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	//事前申请
	/*@RequestMapping("/sqsq")
	public String sqsq(String acco, String pwd, String interfaceCode){
		
		return "forward:/applyCheck/checkResultApp";
	}*/
	/*
	 * 审批结果
	 * @author 叶崇晖
	 * @createtime 2018-07-05
	 * @updatetime 2018-07-05
	 */
	@RequestMapping(value = "/check_sqsq")
	@ResponseBody
	public Result check_sqsq(TProcessCheck checkBean,ApplicationBasicInfo bean,String fileId,
			String acco, String pwd) {
		try {
			User user = userMng.login(acco, pwd);
			List<Role> roleli = userMng.getUserRole(user.getId());
			ApplicationBasicInfo oldbean = applyMng.findById(bean.getgId());
			List<Object> mingxiList = applyMng.getMingxi("ApplicationDetail", "gId", oldbean.getgId());
			applyCheckMng.saveCheckInfo(checkBean, oldbean, mingxiList, user, roleli.get(0), fileId);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	//借款
	/*@RequestMapping("/jk")
	public String jk(String acco, String pwd, String interfaceCode){
		
		return "forward:/loanCheck/checkResultApp";
	}*/
	@RequestMapping(value = "/check_jk")
	@ResponseBody
	public Result check_jk(TProcessCheck checkBean,LoanBasicInfo bean,String fileId,
			String acco, String pwd) {
		try {
			User user = userMng.login(acco, pwd);
			loanCheckMng.saveCheckInfo(checkBean, bean, user, fileId);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	//还款
	@RequestMapping(value = "/check_hk")
	@ResponseBody
	public Result check_hk(TProcessCheck checkBean, Payment bean,String fileId,
			String acco, String pwd) {
		try {
			User user = userMng.login(acco, pwd);
			List<Role> roleli = userMng.getUserRole(user.getId());
			bean = paymentMng.findById(Integer.valueOf(bean.getId()));
			paymentMng.saveCheckPayment(checkBean, bean, user, roleli.get(0), fileId);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			return getJsonResult(false,"操作失败！");
		}
	}
	//报销
	/*@RequestMapping("/bx")
	public String bx(String acco, String pwd, String interfaceCode){
		
		return "forward:/reimburseCheck/checkResultApp";
	}*/
	@RequestMapping(value = "/check_bx")
	@ResponseBody
	public Result checkResultApp(TProcessCheck checkBean,DirectlyReimbAppliBasicInfo drBean, ReimbAppliBasicInfo rBean,String fileId,
			String acco, String pwd) {
		try {
			User user = userMng.login(acco, pwd);
			//直接报销
			if(drBean.getDrId() != null) {
				directlyReimbMng.check(checkBean, drBean, user, fileId);
			}
			//申请报销
			if(rBean.getrId() != null) {
				reimbAppliMng.saveCheckInfo(checkBean, rBean, user, fileId);
			}
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	//报销
	@RequestMapping("/htbx")
	public String htbx(String acco, String pwd, String interfaceCode){
		
		return "forward:/reimburseCheck/contractCheckResultApp";
	}
	
	//采购计划
	/*@RequestMapping("/cgjh")
	public String cgjh(){
		return "forward:/cgcheck/checkResultApp";
	}*/
	@RequestMapping(value = "/check_cgjh")
	@ResponseBody
	public Result check_cgjh(String acco, String pwd,TProcessCheck checkBean,PurchaseApplyBasic bean, String fileId,String hyzgbmyjfiles,String czbmyjfiles) {
		try {
			
			User user = userMng.login(acco, pwd);
			PurchaseApplyBasic oldBean = cgsqMng.findById(bean.getFpId());
			if(!StringUtil.isEmpty(bean.getfDZHCode())){
				oldBean.setfDZHCode(bean.getfDZHCode());
			}
			if(!StringUtil.isEmpty(bean.getFpPype())){
				oldBean.setFpPype(bean.getFpPype());
			}
			if(!StringUtil.isEmpty(bean.getFpMethod())){
				oldBean.setFpMethod(bean.getFpMethod());
			}
			cgcheckMng.saveCheckInfo(checkBean, oldBean, user, fileId,hyzgbmyjfiles,czbmyjfiles);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	//采购登记
	@RequestMapping(value = "/check_cgdj")
	@ResponseBody
	public Result check_cgdj(String acco, String pwd,TProcessCheck checkBean, RegisterApplyBasic bean, String fileId) {
		try {
			
			User user = userMng.login(acco, pwd);
			RegisterApplyBasic oldbean = registerApplyMng.findById(bean.getFrId());
			cgProcessMng.saveCheck(checkBean, oldbean, fileId, user);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	//采购验收
	/*@RequestMapping("/cgys")
	public String cgys(){
		return "forward:/cgreceive/checkResultApp";
	}*/
	
	@RequestMapping(value = "/check_cgys")
	@ResponseBody
	public Result check_cgys(String acco, String pwd,TProcessCheck checkBean, AcceptCheck bean, String fileId) {
		try {
			User user = userMng.login(acco, pwd);
			cgReceiveMng.saveCheck(checkBean, bean, fileId, user);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	//合同拟定
	/*@RequestMapping("/htnd")
	public String htnd(){
		return "forward:/Approval/approveResultApp";
	}*/
	@RequestMapping(value = "/check_htnd")
	@ResponseBody
	public Result check_htnd(ContractBasicInfo bean, TProcessCheck checkBean, String fileId,String acco, String pwd) {
		try {
			User user = userMng.login(acco, pwd);
			//合同拟定的信息
			ContractBasicInfo cbiBean = approvalMng.findById(bean.getFcId());
			if("0".equals(checkBean.getFcheckResult()) && StringUtil.isEmpty(checkBean.getFcheckRemake())){
				return getJsonResult(false, "不通过时请填写审批意见！");
			}
			approvalMng.updatefFlowStauts(cbiBean, checkBean, fileId, user);
			return getJsonResult(true, "操作成功！");
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
	}
	//合同变更
	/*@RequestMapping("/htbg")
	public String htbg(){
		return "forward:/Change/approveApp";
	}*/
	@ResponseBody
	@RequestMapping("/check_htbg")
	public Result check_htbg(String fileId,String acco, String pwd, String status,String fId,TProcessCheck checkBean){
		String result;
		try {
			User user = userMng.login(acco, pwd);
			result = uptMng.updateStatus(fId, status, user, checkBean, fileId,null,null);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, result);
	}
	
	//合同终止
	@RequestMapping("/htzz")
	public String htzz(){
		return "forward:/ending/approveApp";
	}
	
	//耗材新增
	@RequestMapping("/hcxz")
	public String hcxz(){
		//没有该流程
		return "forward:/Storage/approvel";
	}
	
	//耗材领用
	@RequestMapping("/hcly")
	public String hcly(){
		return "forward:/Rece/approvelApp";
	}
	
	//固定资产新增
	@RequestMapping("/gdzcxz")
	public String gdzcxz(){
		//没有该流程
		return "forward:/Storage/approvalStorage";
	}
	
	//固定资产领用
	/*@RequestMapping("/gdzcly")
	public String gdzcly(){
		return "forward:/Rece/approvelApp";
	}*/
	@RequestMapping("/check_gdzcly")
	@ResponseBody
	public Result check_gdzcly(Rece rece,String ConfigPlanjson,TProcessCheck checkBean,String fileId,String acco, String pwd,ModelMap model){
		try {
			User user = userMng.login(acco, pwd);
			receMng.updateStauts(user, rece, checkBean, ConfigPlanjson, fileId);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	//固定资产调拨
	@RequestMapping("/gdzcdb")
	public String gdzcdb(){
		return "forward:/Alloca/approvelApp";
	}
	
	//固定资产交回
	@RequestMapping("/check_gdzcjh")
	@ResponseBody
	public Result check_gdzcjh(AssetReturn assetReturn, TProcessCheck checkBean, String fileId,String acco, String pwd,String getreturnList){
		try {
			User user = userMng.login(acco, pwd);
			assetReturnMng.approve(user, assetReturn, checkBean, fileId, getreturnList);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	//固定资产处置
	/*@RequestMapping("/gdzccz")
	public String gdzccz(){
		return "forward:/Handle/approvalApp";
	}*/
	@RequestMapping("/check_gdzccz")
	@ResponseBody
	public Result check_gdzccz(String acco, String pwd,String stauts,Handle handle,TProcessCheck checkBean,String fileId,ModelMap model,String files01,String files02,String files03,String files04,String planJson){
		try {
			User user = userMng.login(acco, pwd);
			handleMng.updateStauts(stauts, handle, checkBean, user,fileId, files01, files02, files03, files04,planJson);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}

	

	

	
	
}
