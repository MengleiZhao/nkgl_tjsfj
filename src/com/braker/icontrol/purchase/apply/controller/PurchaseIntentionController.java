package com.braker.icontrol.purchase.apply.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.braker.icontrol.purchase.apply.manager.PurchaseIntentionMng;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 采购意向控制层
 * 
 * @author wanping
 *
 */
@Controller
@RequestMapping("/purchaseIntention")
public class PurchaseIntentionController extends BaseController {
	
	@Autowired
	private PurchaseIntentionMng purchaseIntentionMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;

	/**
	 * 列表跳转
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年3月10日
	 * @updator wanping
	 * @updatetime 2021年3月10日
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/intention_list";
	}
	
	/**
	 * 分页查询
	 * @param bean
	 * @param page
	 * @param rows
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月10日
	 * @updator wanping
	 * @updatetime 2021年3月10日
	 */
	@RequestMapping(value = "/pageData")
	@ResponseBody
	public JsonPagination pageData(PurchaseIntentionBasic bean, Integer page, Integer rows, String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = purchaseIntentionMng.pageList(bean, page, rows, getUser(), searchData);
    	List<PurchaseIntentionBasic> list = (List<PurchaseIntentionBasic>) p.getList();
    	for (int i = 0; i < list.size(); i++) {
    		//序号设置	
    		list.get(i).setNum((i + 1) + (page - 1) * rows);	
		}
    	return getJsonPagination(p, page);
	}	
	
	/**
	 * 新增页面跳转
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年3月10日
	 * @updator wanping
	 * @updatetime 2021年3月10日
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model){
		//查询基本信息
		PurchaseIntentionBasic bean = new PurchaseIntentionBasic();
		//获取当前登录对象获得名称和所属部门
		bean.setfUserName(getUser().getName());
		bean.setfUser(getUser().getId());
		bean.setfDeptName(getUser().getDepartName());
		bean.setfDeptId(getUser().getDpID());
		bean.setfReqTime(new Date());
		//自动生成编号
		String str="GK";
		//当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		//查询当天数据
		List<PurchaseIntentionBasic> list = purchaseIntentionMng.findByCondition(currentDate);
		int dataSize = list.size() + 1;
		String dataCode;
		if(dataSize < 10){
			dataCode ="00" + dataSize;
		}else{
			if(dataSize < 100){
				dataCode ="0" + dataSize;
			}else{
				dataCode = String.valueOf(dataSize);
			}
		}
		bean.setfIntentionCode(str + currentDate + dataCode);	
		model.addAttribute("bean", bean);
		
		//查询工作流
		List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "CGYX", getUser().getDpID(), null, null, null, null, null, null);
		model.addAttribute("nodeConf", nodeConfList);
		
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepartName(), null);
		model.addAttribute("proposer", proposer);
		
		//当前操作类型
		model.addAttribute("openType","add");
		return "/WEB-INF/view/purchase_manage/purchase/intention_add";
	}
	
	/**
	 * 意向公开申请新增保存
	 * @param bean
	 * @param files
	 * @return
	 * @author wanping
	 * @createtime 2021年3月10日
	 * @updator wanping
	 * @updatetime 2021年3月10日
	 */
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(PurchaseIntentionBasic bean, String files) {
		try {
			//保存
			purchaseIntentionMng.save(bean, files, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 修改和查看跳转
	 * @param model
	 * @param id
	 * @param editType
	 * @return
	 * @author wanping
	 * @createtime 2021年3月11日
	 * @updator wanping
	 * @updatetime 2021年3月11日
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, Integer id, String editType){
		try {
			//查询基本信息
			PurchaseIntentionBasic bean = purchaseIntentionMng.findById(id);
			model.addAttribute("bean", bean);
			//查询附件信息
			List<Attachment> attac = attachmentMng.list(bean);
			model.addAttribute("attac", attac);
			
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(),"CGYX", bean.getfDeptId(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getfIntentionCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("CGYX", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfUserName(), bean.getfDeptName(), bean.getfReqTime());
			model.addAttribute("proposer", proposer);
			
			//历史审批节点
			String historyNodes = tProcessCheckMng.getHistoryNodes("CGYX", bean.getfDeptId(), bean.getBeanCode());
			model.addAttribute("historyNodes", historyNodes);
			
			//对象编码
			model.addAttribute("foCode", bean.getBeanCode());
			//当前操作类型
			model.addAttribute("czlx","edit");
			if ("1".equals(editType)) {
				model.addAttribute("operation", "edit");
				return "/WEB-INF/view/purchase_manage/purchase/intention_add";
			} else if ("0".equals(editType)) {
				model.addAttribute("operation", "detail");
				return "/WEB-INF/view/purchase_manage/purchase/intention_detail";
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 意向公开申请撤回
	 * @param id
	 * @return
	 * @author wanping
	 * @createtime 2021年3月11日
	 * @updator wanping
	 * @updatetime 2021年3月11日
	 */
	@RequestMapping(value = "/reCall")
	@ResponseBody
	public Result reCall(Integer id) {
		try {
			purchaseIntentionMng.reCall(id);
			return getJsonResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, getException(e));
		}

	}
	
	/**
	 * 意向公开申请删除
	 * @param id
	 * @param fId
	 * @return
	 * @author wanping
	 * @createtime 2021年3月11日
	 * @updator wanping
	 * @updatetime 2021年3月11日
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id, String fId) {
		try {
			//传回来的id是主键
			purchaseIntentionMng.delete(id, fId, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
}
