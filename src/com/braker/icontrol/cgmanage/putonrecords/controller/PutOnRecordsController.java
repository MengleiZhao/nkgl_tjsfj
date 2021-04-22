package com.braker.icontrol.cgmanage.putonrecords.controller;

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
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 政采备案控制层
 * @author 赵孟雷
 *
 */
@Controller
@RequestMapping(value="/putOnRecords")
public class PutOnRecordsController extends BaseController {

	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	/**
	 * 采购备案登记
	 */
	@RequestMapping(value = "/cgRegisterList")
	public String cgRegisterList(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_list";
	}
	
	
	/**
	 * 分页查询 采购备案登记
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	@RequestMapping(value = "/cgRegisterPage")
	@ResponseBody
	public JsonPagination cgRegisterPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = cgsqMng.cgRegisterPage(bean, page, rows, getUser(),searchData);
    	List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
    	for(int x=0; x<li.size(); x++) {
    		//序号设置	
    		li.get(x).setNumber((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}	
	/**
	 * 采购备案登记确认
	 */
	@RequestMapping(value = "/cgRegisterAffirmlist")
	public String cgRegisterAffirmlist(ModelMap model,String index) {
		model.addAttribute("index", index);
		return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_affirm_list";
	}
	
	
	/**
	 * 分页采购确认   采购备案登记
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	@RequestMapping(value = "/cgRegisterAffirmPage")
	@ResponseBody
	public JsonPagination cgRegisterAffirmPage(PurchaseApplyBasic bean, Integer page, Integer rows,String searchData,String index){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = cgsqMng.cgRegisterAffirmPage(bean, page, rows, getUser(),searchData,index);
		List<PurchaseApplyBasic> li = (List<PurchaseApplyBasic>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNumber((x+1)+(page-1)*rows);	
			try {
				List<BiddingRegist> biddingRegists = cgProcessMng.findFbIdByFpId(li.get(x).getFpId(),"", "");
				if(biddingRegists.size()>0){
					List<TProcessCheck> check = tProcessCheckMng.checkHistory(li.get(x).getFpId(),biddingRegists.get(0).getFbiddingCode());
					if(check.size()>0){
						String day = StringUtil.getTime(check.get(0).getFcheckTime(), new Date());
						Integer dayNum = 0;
						if(StringUtil.isEmpty(day)){
							dayNum = 0;
						}else{
							dayNum = Integer.valueOf(day);
						}
						li.get(x).setConEarlyWarning(dayNum);
					}else{
						li.get(x).setConEarlyWarning(0);
					}
				}else{
					li.get(x).setConEarlyWarning(0);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return getJsonPagination(p, page);
	}	
	
	
	/**
	 * 采购备案合同登记
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/addRegisterCon")
	public String addRegisterCon(ModelMap model,Integer fpId){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(fpId);
		
		model.addAttribute("bean", bean);
		//当前操作类型
		model.addAttribute("fileUp","add");
		return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_con_add";
	}
	/**
	 * 采购备案验收登记
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/addRegisterCheck")
	public String addRegisterCheck(ModelMap model,Integer fpId){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(fpId);
		
		model.addAttribute("bean", bean);
		//当前操作类型
		model.addAttribute("fileUp","add");
		return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_check_add";
	}
	
	
	/**
	 * 采购备案合同登记   修改
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/editRegisterCon")
	public String editRegisterCon(ModelMap model,String type,Integer fpId){
		try {
			//查询基本信息
			PurchaseApplyBasic bean = cgsqMng.findById(fpId);
			model.addAttribute("bean",bean);
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
			model.addAttribute("fpId","-101");
			//查询附件信息
			List<Attachment> attac = attachmentMng.list(bean);
			model.addAttribute("attac", attac);
			//是否显示退回信息
			if("5".equals(bean.getConPutOnRecordsSts())){
				List<TProcessCheck> check = tProcessCheckMng.checkHistory(-101,bean.getBeanCode());
				String returnReason = "";
				for (TProcessCheck tProcessCheck : check) {
					if("5".equals(tProcessCheck.getFcheckResult())){
						returnReason = tProcessCheck.getFcheckRemake();
					}
				}
				bean.setReturnReason(returnReason);
				model.addAttribute("returnReason","1");
			}else{
				model.addAttribute("returnReason","");
			}
			if("2".equals(type)){
				model.addAttribute("fileUp", "check");
				return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_con_edit";
			}
			if("0".equals(type)){
				model.addAttribute("fileUp", "detail");
				return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_con_edit";
			}
			model.addAttribute("fileUp", "edit");
			return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_con_edit";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
			
	}
	
	/**
	 * 采购备案登记   修改
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/editRegisterCheck")
	public String editRegisterCheck(ModelMap model,String type,Integer fpId){
		try {
			//查询基本信息
			PurchaseApplyBasic bean = cgsqMng.findById(fpId);
			model.addAttribute("bean",bean);
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
			model.addAttribute("fpId","-102");
			//查询附件信息
			List<Attachment> attac = attachmentMng.list(bean);
			model.addAttribute("attac", attac);
			//是否显示退回信息
			if("5".equals(bean.getCheckPutOnRecordsSts())){
				List<TProcessCheck> check = tProcessCheckMng.checkHistory(-102,bean.getBeanCode());
				String returnReason = "";
				for (TProcessCheck tProcessCheck : check) {
					if("5".equals(tProcessCheck.getFcheckResult())){
						returnReason = tProcessCheck.getFcheckRemake();
					}
				}
				bean.setReturnReason(returnReason);
				model.addAttribute("returnReason","1");
			}else{
				model.addAttribute("returnReason","");
			}
			if("2".equals(type)){
				model.addAttribute("fileUp", "check");
				return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_check_edit";
			}
			if("0".equals(type)){
				model.addAttribute("fileUp", "detail");
				return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_check_edit";
			}
			model.addAttribute("fileUp", "edit");
			return "/WEB-INF/view/purchase_manage/putonrecords/cg_register_check_edit";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	/**
	 * 采购备案登记文件下载
	 * @param model
	 * @author 赵孟雷
	 * @createtime 2021年3月17日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月17日
	 */
	@RequestMapping(value = "/downloadFile")
	public String downloadFile(ModelMap model,Integer fpId){
		//查询基本信息
		PurchaseApplyBasic bean = cgsqMng.findById(fpId);
		//查询附件信息
		List<Attachment> attac = attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		return "/WEB-INF/view/purchase_manage/purchase/cg_register_download";
		
	}
	
	/**
	 * 采购备案登记的合同备案状态保存
	 * @param bean
	 * @param files
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/saveContractSts")
	@ResponseBody	
	public Result saveContractSts(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile) {
		try {
			//保存
			cgsqMng.saveContractSts(bean, files,checkBean,spjlFile,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 采购备案登记的验收备案状态保存
	 * @param bean
	 * @param files
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	@RequestMapping(value = "/saveCheckSts")
	@ResponseBody	
	public Result saveCheckSts(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile) {
		try {
			//保存
			cgsqMng.saveCheckSts(bean, files,checkBean,spjlFile,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
}
