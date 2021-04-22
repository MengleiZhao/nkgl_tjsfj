package com.braker.icontrol.cgmanage.cgcheck.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.budget.release.manager.TBasicItfMng;
import com.braker.icontrol.budget.release.manager.TProItfMng;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgapply.model.BuyInfo;
import com.braker.icontrol.cgmanage.cgcheck.manager.CgCheckMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;
/**
 * 采购申请审批的控制层
 * 本模块用于采购申请的审批及查看
 * @author 冉德茂
 * @createtime 2018-07-10
 * @updatetime 2018-07-10
 */
@Controller
@RequestMapping(value = "/cgcheck")
public class CgCheckController extends BaseController{
	
	@Autowired
	private CgCheckMng cgcheckMng;
	
	@Autowired
	private CgApplysqMng cgsqMng;
	
	@Autowired
	private TBasicItfMng basicItfMng;
	
	@Autowired
	private TProItfMng itfMng;
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private CheterMng cheterMng;
	
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TProExpendDetailMng detailMng;
	@Autowired
	private BudgetIndexMgrMng indexMng;
	@Autowired
	private LookupsMng lookupsMng;
	/*
	 * 跳转到列表页面
	 * @author 冉德茂
	 * @createtime 2018-07-16
	 * @updatetime 2018-07-16
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		String roleName=getUser().getRoleName();
		String deptName=getUser().getDepart().getName();
		if(roleName.contains("会议号录入岗") && "党建办公室".equals(deptName)){
			model.addAttribute("djhCode", 1);
		}else{
			model.addAttribute("djhCode", 0);
		}
		return "/WEB-INF/view/purchase_manage/check/purchase_check_list";
	}
	
	/*
	 * 分页数据获得
	 * @author 冉德茂
	 * @createtime 2018-07-16
	 * @updatetime 2018-07-16
	 */
	@RequestMapping(value = "/cgcheckPage")
	@ResponseBody
	public JsonPagination loanPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgcheckMng.pageList(bean, page, rows, getUser(),searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		Lookups lookups=lookupsMng.findByLookCode(li.get(x).getFpItemsName());
//    		li.get(x).setFpItemsNames(lookups.getName());
    		//序号设置	
    		li.get(x).setNumber((x+1)+(page-1)*rows);
		}
    	return getJsonPagination(p, page);
	}	
	

	
	/*
	 * 跳转审批页面
	 * @author 冉德茂
	 * @createtime 2018-07—16
	 * @updatetime 2018-07—16
	 */
	@RequestMapping(value = "/check")
	public String check(String id ,ModelMap model) {
		//查询基本信息				
		PurchaseApplyBasic bean = cgsqMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", bean);
		//查询采购方式id
		List<BuyInfo> list = cgsqMng.findByText(bean.getFpPype());
		if(list.size()>0){
			for(int i = 0;i<list.size();i++){
				if("政府采购".equals(bean.getFpMethod())){
					if(list.get(i).getParentId() == 1){
						model.addAttribute("cgType", list.get(i).getId());
					}
				}
				if("委托代理机构采购".equals(bean.getFpMethod())){
					if(list.get(i).getParentId() == 2){
						model.addAttribute("cgType", list.get(i).getId());
					}
				}else{
					model.addAttribute("cgType", list.get(0).getId());
				}
			}
		}else{
			model.addAttribute("cgType", "");
		}
		PurchaseApplyBasic beanCopy = new PurchaseApplyBasic();
		BeanUtils.copyProperties(bean, beanCopy);
		//查询附件信息
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		//查询四级指标信息
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
				//批复时间
				if (index.getAppDate() != null) {
					bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
				}
				//使用部门
				bean.setPfDepartName(index.getDeptName());			
				//可用余额
				bean.setSyAmount(detail.getSyAmount());	
				//资金渠道
				if("0".equals(index.getIndexType())){
					bean.setPfIndexType("基本预算");		
				}else{
					bean.setPfIndexType("项目预算");		
				}
			}
		}else if(indexId != null){
			TBudgetIndexMgr index = indexMng.findById(indexId);
			//指标名称
			bean.setIndexName(index.getIndexName()+"【 "+index.getIndexCode()+" 】");
			//批复金额
			bean.setPfAmount(index.getPfAmount()*10000);		
			//批复时间
			if (index.getAppDate() != null) {
				bean.setPfDate(new SimpleDateFormat("yyyy-MM-dd").format(index.getAppDate()));				
			}
			//使用部门
			bean.setPfDepartName(index.getDeptName());			
			//可用余额
			bean.setSyAmount(index.getSyAmount()*10000);	
			//资金渠道
			if("0".equals(index.getIndexType())){
				bean.setPfIndexType("基本预算");		
			}else{
				bean.setPfIndexType("项目预算");		
			}
		}
		
		//只有党建办公室会议号录入岗才可以填写党组会会议号
		String roleName=getUser().getRoleName();
		String deptName=getUser().getDepart().getName();
//		if(roleName.contains("党建会会议纪要录入人") && "党建办公室".equals(deptName)){
		if(roleName.contains("局长办公会会议纪要岗") && "办公室".equals(deptName)){
			model.addAttribute("djhCode", 1);
		}else{
			model.addAttribute("djhCode", 0);
		}
		//只有采购管理岗才可以填写采购方式
		if(roleName.contains("采购管理岗")&&"1".equals(bean.getfCheckStauts())){
			model.addAttribute("cgglg", 1);//如果是采购管理岗为1   可以编辑页面上的采购方式确认
		}else{
			model.addAttribute("cgglg", 0);//如果是采购管理岗为0   不可以编辑页面上的采购方式确认
		}
		if("1".equals(bean.getfIsPers())){
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "SMCGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			String times ="";
			if(!"".equals(bean.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
				times =fmt.format(bean.getfReqTime());     // 转换成 X年X月X日
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+bean.getfUserName()+"</td></tr>"
					+ "<tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"148\" style=\"border: #ffffff !important;text-align:left;\">"+times+"</td></tr>";
			String a4 ="<tr height=\"10px\" style=\"\"><td colspan=\"3\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\">";
			String a5 ="</td></tr><tr height=\"10px\"></tr><tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\">";
			String a6 ="</td><td  width=\"148\" style=\"border: #ffffff !important;text-align:left;\">";
			String a7 ="</td></tr>";
			String i = "</tbody></table>";
			List<TNodeData> datas = new ArrayList<TNodeData>();
			for (int z = 0; z < nodeConfList.size(); z++) {
				datas.add(nodeConfList.get(z));
			}
			Collections.reverse(datas); //倒序排列
			String a0 = "<p style=\"text-align:center;font-family:黑体, monospace;font-size:20.0pt;border: #ffffff;\">"+bean.getProSignName()+"</p><br/>";
			a0 +=bean.getProSignContent();
			a0 +=aa;
			for (int j = 0; j < datas.size(); j++) {
				if("1".equals(datas.get(j).getCheckInfo().getFcheckResult())){
					a0 +=a4;
					a0 +=datas.get(j).getCheckInfo().getFcheckRemake();
					a0 +=a5;
					a0 +="<img  width=\"150\" src=\""+request.getContextPath()+"/attachment/downloadQZ/"+datas.get(j).getUser().getId()+"\"/>";
					a0 +=a6;
					String time ="";
					if(!"".equals(datas.get(j).getCheckInfo().getFcheckTime())){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						time =fmt.format(datas.get(j).getCheckInfo().getFcheckTime());     // 转换成 X年X月X日
					}
					a0 +=time;
					a0 +=a7;
				}
			}
			a0 +=i;
			beanCopy.setProSignContent(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("SMCGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("SMCGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
		}else{
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "CGSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getFpCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			String times ="";
			if(!"".equals(bean.getfReqTime())){
				DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
				times =fmt.format(bean.getfReqTime());     // 转换成 X年X月X日
			}
			String aa ="<table width=\"638\" style=\"border: #ffffff !important;\"><tbody><tr height=\"10px\"><td colspan=\"4\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\"></td></tr><tr height=\"10px\"></tr>"
					+ "<tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"128\" style=\"border: #ffffff !important;text-align:center;\">"+bean.getfUserName()+"</td></tr>"
					+ "<tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\"></td>"
					+ "<td  width=\"148\" style=\"border: #ffffff !important;text-align:left;\">"+times+"</td></tr>";
			String a4 ="<tr height=\"10px\" style=\"\"><td colspan=\"3\" rowspan=\"3\" style=\"border: #ffffff !important;text-align: center;\">";
			String a5 ="</td></tr><tr height=\"10px\"></tr><tr height=\"10px\"></tr><tr height=\"10px\"><td colspan=\"3\" style=\"border: #ffffff !important;text-align:right;\">";
			String a6 ="</td><td  width=\"148\" style=\"border: #ffffff !important;text-align:left;\">";
			String a7 ="</td></tr>";
			String i = "</tbody></table>";
			List<TNodeData> datas = new ArrayList<TNodeData>();
			for (int z = 0; z < nodeConfList.size(); z++) {
				datas.add(nodeConfList.get(z));
			}
			Collections.reverse(datas); //倒序排列
			String a0 = "<p style=\"text-align:center;font-family:黑体, monospace;font-size:20.0pt;border: #ffffff;\">"+bean.getProSignName()+"</p><br/>";
			a0 +=bean.getProSignContent();
			a0 +=aa;
			for (int j = 0; j < datas.size(); j++) {
				if("1".equals(datas.get(j).getCheckInfo().getFcheckResult())){
					a0 +=a4;
					a0 +=datas.get(j).getCheckInfo().getFcheckRemake();
					a0 +=a5;
					a0 +="<img  width=\"150\" src=\""+request.getContextPath()+"/attachment/downloadQZ/"+datas.get(j).getUser().getId()+"\"/>";
					a0 +=a6;
					String time ="";
					if(!"".equals(datas.get(j).getCheckInfo().getFcheckTime())){
						DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
						time =fmt.format(datas.get(j).getCheckInfo().getFcheckTime());     // 转换成 X年X月X日
					}
					a0 +=time;
					a0 +=a7;
				}
			}
			a0 +=i;
			beanCopy.setProSignContent(a0);
			model.addAttribute("beanCopy", beanCopy);
			
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("CGSQ", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
		}
		return "/WEB-INF/view/purchase_manage/check/check_cgapply";
	}

	
	/*
	 * 历史审批记录
	 * @author 冉德茂
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	@RequestMapping(value = "/history")
	@ResponseBody
	public JsonPagination checkHistory(String id) {
		Pagination p = new Pagination();
		if(id != null) {
			p.setList(cgcheckMng.checkHistory(Integer.valueOf(id),null));
		}
		return getJsonPagination(p, 0);
	}
	
	/*
	 * 进行审核
	 * @author 冉德茂
	 * @createtime 2018-07-17
	 * @updatetime 2018-07-17
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,PurchaseApplyBasic bean,String spjlFile,String hyjyfiles,String czbmyjfiles) {
		try {
			//List<Role> roleli = userMng.getUserRole(getUser().getId());
			cgcheckMng.saveCheckInfo(checkBean, bean, getUser(), spjlFile,hyjyfiles,czbmyjfiles);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
	
	
}
