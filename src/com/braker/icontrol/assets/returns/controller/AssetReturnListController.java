package com.braker.icontrol.assets.returns.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.Result;
import com.braker.common.util.LookupsUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.AssetTypeMng;
import com.braker.core.model.AssetType;
import com.braker.core.model.Lookups;
import com.braker.icontrol.assets.returns.manager.AssetReturnListMng;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;

@Controller
@RequestMapping("/assetReturnList")
public class AssetReturnListController extends BaseController {
	
	@Autowired
	private AssetReturnListMng assetReturnListMng;
	
	@Autowired
	private AssetTypeMng assetTypeMng;

	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;

	/**
	 * 获取资产交回清单
	 * @param fId
	 * @return
	 * @author wanping
	 * @createtime 2020年7月21日
	 * @updator wanping
	 * @updatetime 2020年7月21日
	 */
	@RequestMapping("/getAssetReturnList")
	@ResponseBody
	public List<AssetReturnList> getAssetReturnList(Integer fId){
		List<AssetReturnList> list = new ArrayList<AssetReturnList>();
		if (fId != null) {
			//查询申请明细
			AssetReturn assetReturn= new AssetReturn();
			assetReturn.setfId_A(fId);
			list = assetReturnListMng.findByCondition(assetReturn);
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getfFixedType_AR()!=null){
					AssetType assetType = assetTypeMng.findbyCode(list.get(i).getfFixedType_AR());
					if (assetType != null) {
						list.get(i).setfFixedTypeName_AR(assetType.getName());
					}
				}
				if (!StringUtil.isEmpty(list.get(i).getfAssCode_AR())) {
					AssetBasicInfo assetBasicInfo = assetBasicInfoMng.findbyCode(list.get(i).getfAssCode_AR());
					list.get(i).setfUseName_AR(assetBasicInfo.getfUseName());
					list.get(i).setfUseDept_AR(assetBasicInfo.getfUseDept());
					if(list.get(i).getfAvailableStauts()!=null){
						
						list.get(i).setfAvailableStauts_AR(list.get(i).getfAvailableStauts().getName());
					}
				}
			}
		}
		return list;
	}

	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 * @author wanping
	 * @createtime 2020年7月24日
	 * @updator wanping
	 * @updatetime 2020年7月24日
	 */
	@RequestMapping("/batchDelete/{ids}")
	@ResponseBody
	public Result batchDelete(@PathVariable String ids){
		try {
			assetReturnListMng.batchDelete(ids, getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"系统错误请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
}
