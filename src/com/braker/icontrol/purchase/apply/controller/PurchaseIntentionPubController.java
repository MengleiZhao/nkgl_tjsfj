package com.braker.icontrol.purchase.apply.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.purchase.apply.manager.PurchaseIntentionMng;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;

/**
 * 公开完成确认控制层
 * 
 * @author wanping
 *
 */
@Controller
@RequestMapping(value = "/purchaseIntentionPub")
public class PurchaseIntentionPubController extends BaseController {
	
	@Autowired
	private PurchaseIntentionMng purchaseIntentionMng;

	/**
	 * 列表跳转
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年3月12日
	 * @updator wanping
	 * @updatetime 2021年3月12日
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/purchase/intention_pub_list";
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
    	Pagination p = purchaseIntentionMng.pubPageList(bean, page, rows, getUser(), searchData);
    	List<PurchaseIntentionBasic> list = (List<PurchaseIntentionBasic>) p.getList();
    	for (int i = 0; i < list.size(); i++) {
    		//序号设置	
    		list.get(i).setNum((i + 1) + (page - 1) * rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/**
	 * 公开确认
	 * @param id
	 * @return
	 * @author wanping
	 * @createtime 2021年3月13日
	 * @updator wanping
	 * @updatetime 2021年3月13日
	 */
	@RequestMapping(value = "/intentionPublic")
	@ResponseBody
	public Result intentionPublic(Integer id) {
		try {
			purchaseIntentionMng.intentionPublic(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");	
	}
	
	/**
	 * 批量公开确认
	 * @param idlist
	 * @return
	 * @author wanping
	 * @createtime 2021年3月13日
	 * @updator wanping
	 * @updatetime 2021年3月13日
	 */
	@ResponseBody
	@RequestMapping("/batchPublic")
	public Result batchPublic(String idlist){
		try {
			purchaseIntentionMng.batchPublic(idlist);
			return getJsonResult(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "操作失败，请联系管理员！");
		}
	}
	
	/**
	 * 导出
	 * @param response
	 * @param request
	 * @param searchData
	 * @param ids
	 * @return
	 * @author wanping
	 * @createtime 2021年3月13日
	 * @updator wanping
	 * @updatetime 2021年3月13日
	 */
	@RequestMapping("/intentionExport")
	public String intentionExport(HttpServletResponse response, HttpServletRequest request, String searchData, String ids){
		OutputStream out = null;
		try {
			//初始化
			response.setHeader("Content-Disposition","attachment; filename="+new String("ZFCGYXGG.xls".getBytes("gb2312"), "iso8859-1"));   
			out = new BufferedOutputStream(response.getOutputStream());   
			response.setContentType("application/octet-stream");   
			String path = request.getSession().getServletContext().getRealPath("/resource");
			String filePath=path+"/download/政府采购意向公告模板.xls";
			filePath = filePath.replace("\\","/");
			//采购意向数据
			List<PurchaseIntentionBasic> list = purchaseIntentionMng.getList(searchData, ids);
			
			//生成excel并导出
			HSSFWorkbook workbook = purchaseIntentionMng.intentionExport(list, filePath);
			
			if(workbook==null){
				out.flush();   
				return null;
			}
			workbook.write(out);   
			out.flush();   
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return null;
	}
}
