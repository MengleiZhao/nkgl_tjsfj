package com.braker.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.entity.TreeEntity;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.AssetTypeMng;
import com.braker.core.model.AssetType;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;

@Controller
@RequestMapping("/assetType")
public class AssetTypeController extends BaseController  {
	
	@Autowired
	private AssetTypeMng assetTypeMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	
	@RequestMapping("/assetTypeList")
	public String assetTypeList(String index, String tabId, String type, String fAssClass, ModelMap model) {
		model.addAttribute("index", index);
		model.addAttribute("tabId", tabId);
		model.addAttribute("type", type);
		model.addAttribute("fAssClass", fAssClass);
		return "/WEB-INF/view/assets/storage/assetTypeList";
	}
	
	@RequestMapping("/assetTypeJson")
	@ResponseBody
	public List<AssetType> assetTypeJson(String parentCode,String id,String leve){
		List<AssetType> list=assetTypeMng.list(parentCode,id, leve);
		return list;
	}
	
	/**
	 * 查询资产类型树节点
	 * @param id
	 * @param parentId
	 * @param code
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年7月16日
	 * @updator 赵孟雷
	 * @updatetime 2020年7月16日
	 */
	@RequestMapping(value="/tree")
	@ResponseBody
	public List<TreeEntity> tree(String id,String parentId,String code,String type,String fAssClass,ModelMap model) {
		// 内容。取所有列表，找出父菜单。
		List<AssetType> listAssetType=null;
		if(null==id&&StringUtil.isEmpty(fAssClass)){
			listAssetType = assetTypeMng.getRoots(type,null,null);
		}else if(null!=id){
			AssetType assetType =  assetTypeMng.findById(id);
			listAssetType = assetTypeMng.getChild(assetType.getCode());
		}else if(null==id&&!StringUtil.isEmpty(fAssClass)){
			Integer leve = null;
			if("ZCLX-02".equals(type)&&(fAssClass.equals("GDZCLB-01")||fAssClass.equals("GDZCLB-02")||fAssClass.equals("GDZCLB-08"))){
				leve=1;
			}
			listAssetType = assetTypeMng.getRoots(type,leve,fAssClass);
		}
		List<TreeEntity> list=new ArrayList<TreeEntity>();
		if(null!=listAssetType && listAssetType.size()>0){
			for(AssetType d:listAssetType){
				TreeEntity ft=new TreeEntity();
				ft.setId(d.getId());
				ft.setText(d.getName());
				ft.setCode(d.getCode());
				if(assetTypeMng.getChild(d.getCode()).size()>0){
					ft.setState("closed");//显示子类型
				}else{
					ft.setLeaf(true);
				}
				list.add(ft);
			}
		}
		return list;
	}
	
	/**
	 * 加载树形点击数据(用于资产领用查询)
	 * @param assetBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月16日
	 * @updator 陈睿超
	 * @updatetime 2020年7月16日
	 */
	@ResponseBody
	@RequestMapping("/allFixedTypeList")
	public JsonPagination allFixedTypeList(AssetBasicInfo assetBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = assetBasicInfoMng.findbyReceFixedType(assetBasicInfo, page, rows);
		return getJsonPagination(p, page);
	}
	/**
	 * 加载树形点击数据(用于资产处置查询)
	 * @param assetBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年9月04日
	 * @updator 赵孟雷
	 * @updatetime 2020年9月04日
	 */
	@ResponseBody
	@RequestMapping("/findbyfFixedType")
	public JsonPagination findbyfFixedType(AssetBasicInfo assetBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = assetBasicInfoMng.findbyfFixedType(assetBasicInfo, page, rows);
		return getJsonPagination(p, page);
	}
}
