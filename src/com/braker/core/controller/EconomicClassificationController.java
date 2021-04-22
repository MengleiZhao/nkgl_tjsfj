package com.braker.core.controller;

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
import com.braker.core.manager.EconomicClassificationMng;
import com.braker.core.model.Economic;
import com.braker.core.model.EconomicClassificationGovernment;

/**
 * 政府支出经济分类Controller
 * @author wanping
 *
 */
@Controller
@RequestMapping("/economicClassification")
public class EconomicClassificationController extends BaseController {
	
	@Autowired
	private EconomicClassificationMng economicClassificationMng;

	/**
	 * 列表跳转
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年2月22日
	 * @updator wanping
	 * @updatetime 2021年2月22日
	 */
	@RequestMapping("/list")
	public String List(ModelMap model){
		return "/WEB-INF/gwideal_core/economicClassification/economicClassification_list";
	}
	
	/**
	 * 列表查询
	 * @param economic
	 * @param departId
	 * @param fPeriod
	 * @param fbId
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param modelmap
	 * @return
	 * @author wanping
	 * @createtime 2021年2月22日
	 * @updator wanping
	 * @updatetime 2021年2月22日
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination JsonPagination(EconomicClassificationGovernment ecg, Integer page,Integer rows){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
    	
    	Pagination p = economicClassificationMng.list(ecg, page, rows);
    	List<EconomicClassificationGovernment> li = (List<EconomicClassificationGovernment>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * 新增跳转
	 * @param departId
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年2月22日
	 * @updator wanping
	 * @updatetime 2021年2月22日
	 */
	@RequestMapping("/add")
	public String ys_add(ModelMap model){
		String[] years = DateUtil.getLastYears(5);
		model.addAttribute("years", years);
		model.addAttribute("openType", "add");
		return "/WEB-INF/gwideal_core/economicClassification/economicClassification_add";
	}
	
	/**
	 * 保存修改或新增的内容
	 * @param economic
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年2月23日
	 * @updator wanping
	 * @updatetime 2021年2月23日
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(EconomicClassificationGovernment ecg) {
		try {
			if (StringUtil.isEmpty(ecg.getCode())) {
				return getJsonResult(false,"请填写科目编号！");
			} else if (StringUtil.isEmpty(ecg.getName())) {
				return getJsonResult(false,"请填写科目名称！");
			} else if (StringUtil.isEmpty(ecg.getLeve())) {
				return getJsonResult(false,"请选择科目级别！");
			} else if (ecg.getPid() == null) {
				return getJsonResult(false,"请填写上级科目编号！");
			} else if (StringUtil.isEmpty(ecg.getType())) {
				return getJsonResult(false,"请填写科目类型！");
			} else if (ecg.getYear() == null){
				return getJsonResult(false,"请选择应用年份！");
			} else {
				//判断科目编号是否存在
				int num = economicClassificationMng.queryEcgByCode(ecg);
				if (num > 0) {
					return getJsonResult(false,"该科目编号已存在！");
				}
			}
			economicClassificationMng.saveEcg(ecg, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 跳转修改页面
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年2月23日
	 * @updator wanping
	 * @updatetime 2021年2月23日
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id,ModelMap model) {
		String[] years = DateUtil.getLastYears(5);
		model.addAttribute("years", years);
		model.addAttribute("bean",economicClassificationMng.findById(Integer.valueOf(id)));
		model.addAttribute("openType", "edit");
		return "/WEB-INF/gwideal_core/economicClassification/economicClassification_add";
	}
	
	/**
	 * 删除
	 * @param id
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年2月23日
	 * @updator wanping
	 * @updatetime 2021年2月23日
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable String id){
		try {
			if(StringUtil.isEmpty(id)){
				return getJsonResult(false,"请选择你要删除的对象！");
			}
			economicClassificationMng.deleteEcg(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败！");
		}
		return getJsonResult(true,"删除成功！");
	}
}
