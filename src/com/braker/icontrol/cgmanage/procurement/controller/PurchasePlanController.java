package com.braker.icontrol.cgmanage.procurement.controller;

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
import com.braker.common.web.BaseController;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.procurement.manager.PurchasePlanMng;
import com.braker.icontrol.cgmanage.procurement.model.PurchasePlanBasic;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 采购计划控制层
 * 
 * @author wanping
 *
 */
@Controller
@RequestMapping(value = "/purchasePlan")
public class PurchasePlanController extends BaseController {
	
	@Autowired
	private PurchasePlanMng purchasePlanMng;
	
	@Autowired
	private AttachmentMng attachmentMng;

	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private UserMng userMng;

	/**
	 * 列表跳转
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年3月15日
	 * @updator wanping
	 * @updatetime 2021年3月15日
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/procurement/purchasePlan/plan_list";
	}
	
	/**
	 * 新增、修改、查看跳转
	 * @param model
	 * @param fpCode
	 * @param editType
	 * @return
	 * @author wanping
	 * @createtime 2021年3月16日
	 * @updator wanping
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, String fpCode, String editType){
		try {
			//查询采购信息
			PurchaseApplyBasic purchaseApplyBasic = cgsqMng.findUniqueByProperty("fpCode", fpCode);
			model.addAttribute("purchaseApplyBasic", purchaseApplyBasic);
			//查询基本信息
			PurchasePlanBasic bean = new PurchasePlanBasic();
			if ("2".equals(editType)) {
				bean.setfUserName(getUser().getName());
				bean.setfUser(getUser().getId());
				bean.setfDeptName(getUser().getDepartName());
				bean.setfDeptId(getUser().getDpID());
				bean.setfReqTime(new Date());
				String fPurchasePlanCode = fpCode.replace("CG", "CGJH");
				bean.setfPurchasePlanCode(fPurchasePlanCode);
				//项目负责人
	    		User userBM = userMng.getUserByRoleNameAndDepartName("处室负责人", purchaseApplyBasic.getfDeptName());
	    		if (userBM != null) {
	    			bean.setProjectLeaderId(userBM.getId());
	    			bean.setProjectLeaderName(userBM.getName());
	    		}
			} else {
				bean = purchasePlanMng.findUniqueByProperty("fPurchaseCode", fpCode);
			}
			model.addAttribute("bean", bean);
			//查询附件信息
			List<Attachment> attac = attachmentMng.list(bean);
			model.addAttribute("attac", attac);
			
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(),"CGJH", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getfPurchasePlanCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGJH", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), null);
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("CGJH", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode", bean.getBeanCode());
			//当前操作类型
			if ("1".equals(editType)) {
				model.addAttribute("openType", "edit");
			}
			if ("1".equals(editType) || "2".equals(editType)) {
				return "/WEB-INF/view/purchase_manage/procurement/purchasePlan/plan_add";
			} else if ("0".equals(editType)) {
				return "/WEB-INF/view/purchase_manage/procurement/purchasePlan/plan_detail";
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取采购管理岗用户
	 * @param roleName
	 * @param selected
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/getUserByRole")
	@ResponseBody
	public List<ComboboxJson> getUserByRole(String roleName, String selected){
		List<User> list = userMng.getUserByRole("采购管理岗", selected);
		return getComboboxJson(list, selected);
	}
	
	/**
	 * 政采计划申请新增保存
	 * @param bean
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(PurchasePlanBasic bean) {
		try {
			//保存
			purchasePlanMng.save(bean, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 政采计划申请撤回
	 * @param id
	 * @return
	 * @author wanping
	 * @createtime 2021年3月17日
	 * @updator wanping
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			purchasePlanMng.reCall(id);
			return getJsonResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}

	}
}
