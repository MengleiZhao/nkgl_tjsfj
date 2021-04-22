package com.braker.icontrol.assets.storage.controller;

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
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;

@Controller
@RequestMapping("/AssetBasicInfo")
public class AssetBasicInfoController extends BaseController{

	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private UserMng userMng;
	
	/**
	 * 低值易耗品入库时新增物资品目跳转到新增页面
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2018-08-03
	 */
	@RequestMapping("/lowAddOther")
	public String lowAddOther(ModelMap model){
		//取消右侧流程显示
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/assets/storage/storage_low_basicInfo_add";
	}

	/**
	 * 保存物资品目表
	 * @param model
	 * @param assetBasicInfo
	 * @return
	 * @author 陈睿超
	 * @createtime 2018-08-03
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ModelMap model,AssetBasicInfo assetBasicInfo){
		if(StringUtil.isEmpty(assetBasicInfo.getfAssCode())){
			return getJsonResult(false,"请填写物资编码！");
		}else if(StringUtil.isEmpty(assetBasicInfo.getfAssName())){
			return getJsonResult(false,"请填写物资名称！");
		}else if(StringUtil.isEmpty(assetBasicInfo.getfAssType())){
			return getJsonResult(false,"请填写资产类型！");
		}else if(StringUtil.isEmpty(assetBasicInfo.getfSPModel())){
			return getJsonResult(false,"请填写规格型号！");
		}else{
			assetBasicInfoMng.saveABI(assetBasicInfo, getUser());
			return getJsonResult(true,"操作成功！");
		}
	}
	
	/**
	 * 跳转选择增加固定资产领用页面
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月16日
	 * @updator 陈睿超
	 * @updatetime 2020年7月16日
	 */
	@RequestMapping("/chooseFixedInfo")
	public String chooseFixedInfo(ModelMap model,String fixedType){
		//取消右侧流程显示
		model.addAttribute("splc", "1");
		model.addAttribute("fixedType", fixedType);
		return "/WEB-INF/view/assets/storage/storage_choose_recefixed";
	}

	/**
	 * 获取当前用户固定资产
	 * @param assetBasicInfo
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年7月23日
	 * @updator wanping
	 * @updatetime 2020年7月23日
	 */
	@RequestMapping("/getAssetReturnSelect")
	@ResponseBody
	public JsonPagination getAssetReturnSelect(AssetBasicInfo assetBasicInfo, Integer page, Integer rows, ModelMap model){
		if(page==null){page=1;}
		if(rows==null){rows=99999;}
		assetBasicInfo.setfUseNameID(getUser().getId());
		Pagination p = assetBasicInfoMng.returnchoose(assetBasicInfo, getUser(), page, rows);
		List<AssetBasicInfo> li= (List<AssetBasicInfo>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
		return getJsonPagination(p, page);
	}
	
	/**
	 * 获取当前用户固定资产
	 * @author 沈帆
	 * @createtime 2020年9月15日
	 */
	@RequestMapping("/getPersonalAsset")
	@ResponseBody
	public JsonPagination getPersonalAsset(AssetBasicInfo assetBasicInfo, Integer page, Integer rows, String userId){
		if(page==null){page=1;}
		if(rows==null){rows=10;}
		User user =userMng.findById(userId);
		assetBasicInfo.setfUseNameID(user.getId());
		Pagination p = assetBasicInfoMng.returnchoose(assetBasicInfo, user, page, rows);
		List<AssetBasicInfo> li= (List<AssetBasicInfo>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
		return getJsonPagination(p, page);
	}
}
