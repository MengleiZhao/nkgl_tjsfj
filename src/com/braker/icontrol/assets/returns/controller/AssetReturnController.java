package com.braker.icontrol.assets.returns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.assets.returns.manager.AssetReturnListMng;
import com.braker.icontrol.assets.returns.manager.AssetReturnMng;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

@Controller
@RequestMapping("/assetReturn")
public class AssetReturnController extends BaseController {

	@Autowired
	private AssetReturnMng assetReturnMng;

	@Autowired
	private AssetReturnListMng assetReturnListMng;

	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;

	/**
	 * 跳转固定资产交回申请页面
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月20日
	 * @updator wanping
	 * @updatetime 2020年7月20日
	 */
	@RequestMapping("/toAssetReturnList")
	public String toAssetReturnList(ModelMap model) {
		return "/WEB-INF/view/assets/return/return_list";
	}

	/**
	 * 获取交回申请列表
	 * @param assetReturn
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月20日
	 * @updator wanping
	 * @updatetime 2020年7月20日
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination JsonPagination(AssetReturn assetReturn, Integer page, Integer rows, ModelMap model) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = assetReturnMng.getAssetReturnList(assetReturn, getUser(), page, rows);
		List<AssetReturn> list = (List<AssetReturn>) p.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNumber(i + 1);

			List<AssetReturnList> assetReturnList = assetReturnListMng.findByCondition(list.get(i));
			if (assetReturnList != null && !assetReturnList.isEmpty()) {
				StringBuffer fAssNameAll = new StringBuffer();
				for (AssetReturnList al : assetReturnList) {
					fAssNameAll.append(al.getfAssName_AR() + ",");
				}
				String fAssNameAllStr = fAssNameAll.toString();
				list.get(i).setfAssNameAll(fAssNameAllStr.substring(0, fAssNameAllStr.length() - 1));
			}
		}
		return getJsonPagination(p, page);
	}

	/**
	 * 跳转固定资产交回页面
	 * @param fAssType
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月21日
	 * @updator wanping
	 * @updatetime 2020年7月21日
	 */
	@RequestMapping("/toAssetReturnAdd")
	public String toAssetReturnAdd(String fAssType, ModelMap model) {
		model.addAttribute("openType", "add");
		User user = getUser();
		AssetReturn assetReturn = new AssetReturn();
		assetReturn.setfAssReturnCode(StringUtil.Random("ZCJH"));
		assetReturn.setfAssType(fAssType);
		assetReturn.setfOperator(user.getName());
		assetReturn.setfOperatorId(user.getId());
		assetReturn.setfDeptName(user.getDepartName());
		assetReturn.setfDeptId(user.getDpID());
		model.addAttribute("bean", assetReturn);
		// 查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(user.getId(), "GDZCJH", user.getDpID(), null, null,
				null, null, null, "1");
		model.addAttribute("nodeConf", nodeConfList);
		// 建立工作流发起人的信息
		Proposer proposer = new Proposer(user.getName(), user.getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/assets/return/return_list_add";
	}
	
	/**
	 * 跳转固定资产选择页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAssetReturnSelect")
	public String toAssetReturnInfo(ModelMap model){
		return "/WEB-INF/view/assets/return/return_list_select";
	}

	/**
	 * 固定资产交回申请保存
	 * @param assetReturn
	 * @param assetReturnJson
	 * @param user
	 * @return
	 * @author wanping
	 * @createtime 2020年7月23日
	 * @updator wanping
	 * @updatetime 2020年7月23日
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(AssetReturn assetReturn, String assetReturnJson, User user) {
		try {
			List<AssetReturnList> assetReturnList = JSON.parseObject(assetReturnJson,new TypeReference<List<AssetReturnList>>(){});
			assetReturnMng.save(assetReturn, assetReturnList, getUser());
			return getJsonResult(true,"操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
		
	}
	
	/**
	 * 跳转到查看页面
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月23日
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable String id, String detail, String ledger, ModelMap model){
		AssetReturn assetReturn = new AssetReturn();
		if("detailLedger".equals(ledger)){
			assetReturn = assetReturnMng.findByCode(id);
			model.addAttribute("ledger", "ledger");
		}else{
			assetReturn = assetReturnMng.findById(Integer.valueOf(id));
			model.addAttribute("ledger", "detail");
		}
		model.addAttribute("openType", "detail");
		model.addAttribute("bean", assetReturn);
		//显示审批记录
		String str=null;
		if("ZCLX-01".equals(assetReturn.getfAssType())){
//			str="DZYHPLY";
		}else if("ZCLX-02".equals(assetReturn.getfAssType())){
			str="GDZCJH";
		}
		if("9".equals(assetReturn.getfFlowStauts_A())){
			model.addAttribute("configList", "1");
		}
		model.addAttribute("detailType", detail);
		//根据申请人id获得部门
		String departName = departMng.findDeptByUserId(assetReturn.getfOperatorId())[0];
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(assetReturn.getUserId(),str, departName,assetReturn.getBeanCode(),assetReturn.getfNextCode(), assetReturn.getJoinTable(), assetReturn.getBeanCodeField(), assetReturn.getfAssReturnCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(assetReturn.getfOperator(), assetReturn.getfDeptName(), assetReturn.getfReqTime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(str,departName);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编号
		model.addAttribute("foCode",assetReturn.getBeanCode());
		return "/WEB-INF/view/assets/return/return_detail";
	}
	
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月23日
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id,ModelMap model){
		model.addAttribute("openType", "edit");
		AssetReturn assetReturn = assetReturnMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", assetReturn);
		//显示审批记录
		String str=null;
		if("ZCLX-01".equals(assetReturn.getfAssType())){
//			str="DZYHPLY";
		}else if("ZCLX-02".equals(assetReturn.getfAssType())){
			str="GDZCJH";
		}
		//根据申请人id获得部门
		String departName=departMng.findDeptByUserId(assetReturn.getfOperatorId())[0];
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(assetReturn.getUserId(),str, departName,assetReturn.getBeanCode(),assetReturn.getfNextCode(),assetReturn.getJoinTable(), assetReturn.getBeanCodeField(), assetReturn.getfAssReturnCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(assetReturn.getfOperator(), assetReturn.getfDeptName(), assetReturn.getfReqTime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(str,departName);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编号
		model.addAttribute("foCode",assetReturn.getBeanCode());
		return "/WEB-INF/view/assets/return/return_list_add";
	}
	
	/**
	 * 跳转固定资产交回审批页面
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月23日
	 * @updator wanping
	 * @updatetime 2020年7月23日
	 */
	@RequestMapping("/approvalAssetReturn/{id}")
	public String approvalAssetReturn(@PathVariable String id,ModelMap model){
		model.addAttribute("openType", "approval");
		AssetReturn assetReturn = assetReturnMng.findById(Integer.valueOf(id));
		Boolean roleBoolean = getUser().getRoleName().contains("实物管理岗");
		if(roleBoolean){
			model.addAttribute("openuser", "实物管理岗");
		}
		model.addAttribute("bean", assetReturn);
		String str=null;
		if("ZCLX-01".equals(assetReturn.getfAssType())){
//			str="DZYHPLY";
		}else if("ZCLX-02".equals(assetReturn.getfAssType())){
			str="GDZCJH";
		}
//		User user = getUser();
//		if(user.getRoleName().contains("实物管理岗")){
//			//页面需要让实物管理岗配置领用物品信息
//			model.addAttribute("configList", "1");
//		}
		//显示审批记录
//		model.addAttribute("checkinfo", "1");
		//根据申请人id获得部门
		String departName=departMng.findDeptByUserId(assetReturn.getfOperatorId())[0];
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(assetReturn.getUserId(),str, departName,assetReturn.getBeanCode(),assetReturn.getfNextCode(), assetReturn.getJoinTable(), assetReturn.getBeanCodeField(), assetReturn.getfAssReturnCode(),"1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(assetReturn.getfOperator(), assetReturn.getfDeptName(), assetReturn.getfReqTime());
		model.addAttribute("proposer", proposer);
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode(str,departName);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编号
		model.addAttribute("foCode",assetReturn.getBeanCode());
		return "/WEB-INF/view/assets/return/approval/return_approval_add";
	}
	
	/**
	 * 删除
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable String id,ModelMap model){
		try {
			assetReturnMng.delete(id, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}

	/**
	 * 撤回
	 * @param id
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	@ResponseBody
	@RequestMapping("/reCall")
	public Result reCall(String id){
		try {
			assetReturnMng.reCall(id);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
	}
	
	/**
	 * 跳转固定资产交回审批页面
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	@RequestMapping("/toAssetReturnApprovalList")
	public String toAssetReturnApprovalList(ModelMap model) {
		return "/WEB-INF/view/assets/return/approval/return_approval_list";
	}
	
	/**
	 * 获取交回审批列表
	 * @param assetReturn
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	@RequestMapping("/approvalJsonPagination")
	@ResponseBody
	public JsonPagination approvalJsonPagination(AssetReturn assetReturn, Integer page, Integer rows, ModelMap model) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = assetReturnMng.getApprovalAssetReturn(assetReturn, getUser(), page, rows);
		List<AssetReturn> list = (List<AssetReturn>) p.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNumber(i + 1);

			List<AssetReturnList> assetReturnList = assetReturnListMng.findByCondition(list.get(i));
			if (assetReturnList != null && !assetReturnList.isEmpty()) {
				StringBuffer fAssNameAll = new StringBuffer();
				for (AssetReturnList al : assetReturnList) {
					fAssNameAll.append(al.getfAssName_AR() + ",");
				}
				String fAssNameAllStr = fAssNameAll.toString();
				list.get(i).setfAssNameAll(fAssNameAllStr.substring(0, fAssNameAllStr.length() - 1));
			}
		}
		return getJsonPagination(p, page);
	}

	/**
	 * 固定资产审批
	 * @param assetReturn
	 * @param checkBean
	 * @param spjlFile
	 * @return
	 * @author wanping
	 * @createtime 2020年7月27日
	 * @updator wanping
	 * @updatetime 2020年7月27日
	 */
	@RequestMapping("/approve")
	@ResponseBody
	public Result approve(AssetReturn assetReturn, TProcessCheck checkBean, String spjlFile,String getreturnList){
		try {
			assetReturnMng.approve(getUser(), assetReturn, checkBean, spjlFile, getreturnList);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	/**
	 * 跳转固定资产交回资产岗受理页面
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	@RequestMapping("/toAssetReturnAcceptList")
	public String toAssetReturnAcceptList(ModelMap model) {
		return "/WEB-INF/view/assets/return/return_accept_list";
	}
	
	/**
	 * 获取交回审批列表
	 * @param assetReturn
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	@RequestMapping("/acceptJsonPagination")
	@ResponseBody
	public JsonPagination acceptJsonPagination(AssetReturn assetReturn, Integer page, Integer rows) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = assetReturnMng.getAssetReturnList(assetReturn, getUser(), page, rows);
		List<AssetReturn> list = (List<AssetReturn>) p.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNumber(i + 1);

			List<AssetReturnList> assetReturnList = assetReturnListMng.findByCondition(list.get(i));
			if (assetReturnList != null && !assetReturnList.isEmpty()) {
				StringBuffer fAssNameAll = new StringBuffer();
				for (AssetReturnList al : assetReturnList) {
					fAssNameAll.append(al.getfAssName_AR() + ",");
				}
				String fAssNameAllStr = fAssNameAll.toString();
				list.get(i).setfAssNameAll(fAssNameAllStr.substring(0, fAssNameAllStr.length() - 1));
			}
		}
		return getJsonPagination(p, page);
	}

	/**
	 * 固定资产交回登记
	 * @param id
	 * @return
	 * @author wanping
	 * @createtime 2020年7月27日
	 * @updator wanping
	 * @updatetime 2020年7月27日
	 */
	@RequestMapping("/accept/{id}")
	@ResponseBody
	public Result accept(@PathVariable String id){
		try {
			assetReturnMng.accept(id, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	@ResponseBody
	@RequestMapping("/acceptStautsJsonPagination")
	public JsonPagination acceptStautsJsonPagination(AssetReturn assetReturn, Integer page, Integer rows) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = SimplePage.DEF_COUNT;
		}
		Pagination p = assetReturnMng.getacceptAssetReturn(assetReturn, getUser(), page, rows);
		List<AssetReturn> list = (List<AssetReturn>) p.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNumber(i + 1);

			List<AssetReturnList> assetReturnList = assetReturnListMng.findByCondition(list.get(i));
			if (assetReturnList != null && !assetReturnList.isEmpty()) {
				StringBuffer fAssNameAll = new StringBuffer();
				for (AssetReturnList al : assetReturnList) {
					fAssNameAll.append(al.getfAssName_AR() + ",");
				}
				String fAssNameAllStr = fAssNameAll.toString();
				list.get(i).setfAssNameAll(fAssNameAllStr.substring(0, fAssNameAllStr.length() - 1));
			}
		}
		return getJsonPagination(p, page);
	}
	
	
}
