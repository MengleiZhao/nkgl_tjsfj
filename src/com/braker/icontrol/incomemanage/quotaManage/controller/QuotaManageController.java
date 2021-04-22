package com.braker.icontrol.incomemanage.quotaManage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.braker.common.page.ComboboxJson;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.EconomicClassificationGovernment;
import com.braker.core.model.Lookups;
import com.braker.icontrol.incomemanage.capital.model.QuotaMangeInfo;
import com.braker.icontrol.incomemanage.quotaManage.manager.QuotaManageMng;
import com.braker.zzww.comm.manager.AttachmentMng;


/**
 * 额度管理的控制层
 * @author 沈帆
 * @createtime 2021-02-20
 * @updatetime 2021-02-20
 */
@Controller
@RequestMapping(value = "/quotaManage")
public class QuotaManageController extends BaseController{

	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private QuotaManageMng quotaManageMng;
	/*
	 * 跳转到列表页面
	 * @author 沈帆
	 * @createtime 2021-02-20
	 * @updatetime 2021-02-20
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/income_manage/quotaManage/quota_regist_list";
	}
	
	/*
	 * 跳转到新增页面
	 * @author 沈帆
	 * @createtime 2021-02-22
	 * @updatetime 2021-02-22
	 */
	@RequestMapping(value = "/add")
	public String add( ModelMap model) {
		QuotaMangeInfo bean = new QuotaMangeInfo();
		bean.setfOperateTime(new Date());
		model.addAttribute("bean", bean);
		return "/WEB-INF/view/income_manage/quotaManage/quota_regist_add";
	}
	
	/*
	 * 跳转到修改页面
	 * @author 沈帆
	 * @createtime 2021-02-22
	 * @updatetime 2021-02-22
	 */
	@RequestMapping(value = "/edit")
	public String edit( ModelMap model,Integer id) {
		QuotaMangeInfo bean = quotaManageMng.findById(id);
		model.addAttribute("bean", bean);
		return "/WEB-INF/view/income_manage/quotaManage/quota_regist_add";
	}
	
	/*
	 * 跳转到查看页面
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@RequestMapping(value = "/detail")
	public String detail( ModelMap model,Integer id) {
		QuotaMangeInfo bean = quotaManageMng.findById(id);
		model.addAttribute("bean", bean);
		return "/WEB-INF/view/income_manage/quotaManage/quota_regist_detail";
	}

	/*
	 * 跳转到审批窗口
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@RequestMapping(value = "/check")
	public String check( ModelMap model) {
		return "/WEB-INF/view/income_manage/quotaManage/check_win";
	}

	
	/*
	 * 额度登记分页数据获得
	 * @author 沈帆
	 * @createtime 2021-02-20
	 * @updatetime 2021-02-20
	 */
	@RequestMapping(value = "/quotaPage")
	@ResponseBody
	public JsonPagination quotaPage(QuotaMangeInfo bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = quotaManageMng.quotaPage(bean, page, rows,getUser(),searchData);
    	List<QuotaMangeInfo> li = (List<QuotaMangeInfo>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * 额度复核分页数据获得
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@RequestMapping(value = "/quotaCheckPage")
	@ResponseBody
	public JsonPagination quotaCheckPage(QuotaMangeInfo bean, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = quotaManageMng.quotaCheckPage(bean, page, rows,getUser(),searchData);
    	List<QuotaMangeInfo> li = (List<QuotaMangeInfo>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	
	/*
	 * 跳转复核页面  
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@RequestMapping(value = "/checkList")
	public String checkList(ModelMap model) {
		return "/WEB-INF/view/income_manage/quotaManage/quota_check_list";
	}
	
	/*
	 * 文件导入页面显示
	 * @author 沈帆
	 * @createtime 2021-02-24
	 * @updatetime 2021-02-24
	 */
	@RequestMapping(value = "/imput")
	public String imput(ModelMap model) {
		return "/WEB-INF/view/income_manage/quotaManage/quota_imput";
	}
	
	/*
	 * 跳转到可用额度列表
	 * @author 沈帆
	 * @createtime 2021-02-24
	 * @updatetime 2021-02-24
	 */
	@RequestMapping(value = "/dataList")
	public String dataList(ModelMap model) {
		return "/WEB-INF/view/income_manage/quotaManage/quota_data_list";
	}
	
	/*
	 * 可用额度数据获得
	 * @author 沈帆
	 * @createtime 2021-02-24
	 * @updatetime 2021-02-24
	 */
	@RequestMapping(value = "/dataListJson")
	@ResponseBody
	public List<QuotaMangeInfo> dataListJson(){
		//获取当年所有政府支出经济分类
		List<EconomicClassificationGovernment> ecomList = quotaManageMng.getEconomicList(null);
		List<QuotaMangeInfo> li =quotaManageMng.getCurrencyYearData(ecomList) ;
		QuotaMangeInfo quota = new QuotaMangeInfo();
		BigDecimal totalSkAmount =new BigDecimal("0.00"); 
		BigDecimal totalBxAmount =new BigDecimal("0.00"); 
    	for(int x=0; x<li.size(); x++) {
    		totalSkAmount =totalSkAmount.add(li.get(x).getSkAmount()); 
    		totalBxAmount =totalBxAmount.add(li.get(x).getBxAmount());  
			//序号设置	
			li.get(x).setNum(x+1);	
		}
    	quota.setfSubName("合计");
    	quota.setSkAmount(totalSkAmount);
    	quota.setBxAmount(totalBxAmount);
    	Collections.reverse(li);
    	li.add(quota);
    	Collections.reverse(li);
    	return li;
	}
	
	/**
	 * 加载政府经济分类信息
	 * @return 
	 * @author 沈帆
	 * @createtime 2021年2月22日
	 */
	@RequestMapping(value = "/lookupsJson")
	@ResponseBody
	public List<ComboboxJson> lookupsJson(String parentCode,String selected,String blanked){
		List<Lookups> list = quotaManageMng.getLookupsJson(parentCode, blanked,selected);
		return getComboboxJson(list,selected);
	}
	
	/*
	 * 
	 * 保存额度登记
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(QuotaMangeInfo bean) {
		try {
			quotaManageMng.save(bean,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 
	 * 额度登记删除
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	
	@RequestMapping(value = "/delete")
	@ResponseBody	
	public Result delete(Integer id) {
		try {
			quotaManageMng.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 
	 * 撤回额度登记
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	
	@RequestMapping(value = "/recall")
	@ResponseBody	
	public Result recall(Integer id) {
		try {
			quotaManageMng.recall(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 
	 * 保存额度复核
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	
	@RequestMapping(value = "/saveCheckResult")
	@ResponseBody	
	public Result saveCheckResult(String ids,String result) {
		try {
			quotaManageMng.saveCheckResult(ids,result,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	
	/**
	 * 模板下载
	 * @author 沈帆
	 * @param response
	 * @return
	 */
	@RequestMapping("/download")
	@ResponseBody
	public Result download(HttpServletResponse response){
		OutputStream out = null;
		InputStream in = null;
		try {
			String path = request.getSession().getServletContext()
					.getRealPath("/resource");
			String filePath = path + "/download/eddjdrmb.xls";
			filePath = filePath.replace("\\","/");
			File file = new File(filePath);
			/*File file = new File("D:/TZMB_预算管理_部门基本指标模板.xlsx");*/
			if(file.exists()){
				in = new FileInputStream(file);
				response.setContentType("application/octet-stream");
				response.setContentLength(in.available());
				//response.setHeader("Content-Disposition","attachment; filename=\""+ new String("额度登记导入模板.xls".getBytes("gbk"), "iso8859-1")+ "\"");
				response.setHeader("Content-Disposition","attachment;filename="+ new String("demo.xls".getBytes("gb2312"), "iso8859-1"));
				out = response.getOutputStream();
				byte[] bt = new byte[1000];
				int a; 
				while ((a = in.read(bt, 0, 1000)) > 0) {
					out.write(bt, 0, a);
					out.flush();
				}
			}else{
				return getJsonResult(false,"文件不存在！");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false,"出现错误,请联系管理员！");
		} finally {
			try {
				if (out != null){
					out.close();
				}
				if (in != null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 读取导入的模板文件
	 * @author 沈帆
	 * @createtime 2021-02-21
	 * @updatetime 2021-02-21
	 */
	@RequestMapping(value = "/collect")
	@ResponseBody
	public Result collect(MultipartFile xlsx) {
		InputStream ins =null;
		OutputStream os =null;
		try {
			File f = null;
			if(xlsx.equals("")||xlsx.getSize()<=0){
				xlsx = null;
			}else{
				ins = xlsx.getInputStream();
			    f=new File(xlsx.getOriginalFilename());
			    os = new FileOutputStream(f);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				File file = new File(f.toURI());
				quotaManageMng.saveFile(file, getUser());
			}
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(ins!=null){
				try {
					ins.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return getJsonResult(true,"操作成功！");
		
	}
}
