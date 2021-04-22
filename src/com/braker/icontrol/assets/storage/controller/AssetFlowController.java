package com.braker.icontrol.assets.storage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.SimplePage;
import com.braker.common.web.BaseController;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.storage.manager.AssetFlowMng;
import com.braker.icontrol.assets.storage.model.AssetFlow;


@Controller
@RequestMapping("/AssetFlow")
public class AssetFlowController extends BaseController{

	@Autowired
	private AssetFlowMng assetFlowMng;
	
	
	
	/**
	 * 资产领用流水记录
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月18日
	 * @updator 陈睿超
	 * @updatetime 2020年7月18日
	 */
	@RequestMapping("/receflow")
	public String receflow(String id ,ModelMap model){
		model.addAttribute("id", id);
		return "/WEB-INF/view/assets/flow/rece_flow_list";
	}
	
	/**
	 * 加载领用资产操作流水记录数据
	 * @param assetid 资产id
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月18日
	 * @updator 陈睿超
	 * @updatetime 2020年7月18日
	 */
	@ResponseBody
	@RequestMapping("/receflowJson")
	public JsonPagination receflowJson(String assetid,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = new Pagination();
		List<AssetFlow> li = assetFlowMng.findbyStockCode(assetid,"'ZCLSCZLX-01,ZCLSCZLX-06'");
		p.setPageNo(page);
		p.setPageSize(li.size());
		p.setTotalCount(li.size());
		p.setList(li);
		return getJsonPagination(p, page);
	}
	
}
