package com.braker.icontrol.assets.export.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.AssetTypeMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.AssetType;
import com.braker.core.model.Depart;
import com.braker.core.model.Lookups;
import com.braker.core.model.User;
import com.braker.icontrol.assets.export.manager.ExportHandleMng;
import com.braker.icontrol.assets.handle.manager.HandleMng;
import com.braker.icontrol.assets.handle.model.AssetRegistration;
import com.braker.icontrol.assets.handle.model.Handle;
import com.braker.icontrol.assets.rece.manager.ReceConfigListMng;
import com.braker.icontrol.assets.rece.manager.ReceListMng;
import com.braker.icontrol.assets.rece.manager.ReceMng;
import com.braker.icontrol.assets.rece.model.Rece;
import com.braker.icontrol.assets.rece.model.ReceConfigList;
import com.braker.icontrol.assets.rece.model.ReceList;
import com.braker.icontrol.assets.returns.manager.AssetReturnListMng;
import com.braker.icontrol.assets.returns.manager.AssetReturnMng;
import com.braker.icontrol.assets.returns.model.AssetReturn;
import com.braker.icontrol.assets.returns.model.AssetReturnList;
import com.braker.icontrol.assets.storage.manager.AssetBasicInfoMng;
import com.braker.icontrol.assets.storage.manager.RegistMng;
import com.braker.icontrol.assets.storage.manager.StorageMng;
import com.braker.icontrol.assets.storage.model.AssetBasicInfo;
import com.braker.icontrol.assets.storage.model.Regist;
import com.braker.icontrol.assets.storage.model.Storage;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 打印资产管理中的数据
 * @author 赵孟雷
 * @createtime 2020-06-02
 * @updatetime 2020-06-02
 *
 */
@Controller
@RequestMapping(value = "/exportAssets")
public class ExportAssetsController extends BaseController{

	@Autowired
	private StorageMng storageMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private RegistMng registMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private AssetReturnMng assetReturnMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private AssetReturnListMng assetReturnListMng;
	@Autowired
	private AssetTypeMng assetTypeMng;
	@Autowired
	private AssetBasicInfoMng assetBasicInfoMng;
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	@Autowired
	private HandleMng handleMng;
	@Autowired
	private ExportHandleMng exportHandleMng;
	@Autowired
	private ReceMng receMng;
	@Autowired
	private ReceListMng receListMng;
	@Autowired
	private ReceConfigListMng receConfigListMng;
	@Autowired
	private UserMng userMng;
	
	/*
	 * 跳转到固定资产入账打印页面
	 * @author 赵孟雷
	 * @createtime 2020-08-11
	 * @updatetime 2020-08-11
	 */
	@RequestMapping(value = "/storageFixed")
	public String storageFixed(Integer id, ModelMap model) throws Exception {
		Storage storage=storageMng.findById(Integer.valueOf(id));
		Lookups fGainingMethod=lookupsMng.findByLookCode(storage.getfGainingMethod());
		storage.setfGainingMethods(fGainingMethod.getName());
		model.addAttribute("bean", storage);
		List<Regist> list =  registMng.findByProperty("fId_S", id);
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getfDepreciationStatus()!=null){
					if(StringUtils.isNotEmpty(list.get(i).getfDepreciationStatus().getCode())){
						list.get(i).setfDepreciationStatusCode(list.get(i).getfDepreciationStatus().getCode());
					}
					if(StringUtils.isNotEmpty(list.get(i).getfDepreciationStatus().getName())){
						list.get(i).setfDepreciationStatusShow(list.get(i).getfDepreciationStatus().getName());
					}
				}
				if(list.get(i).getfValueType()!=null){
					if(StringUtils.isNotEmpty(list.get(i).getfValueType().getCode())){
						list.get(i).setfValueTypeCode(list.get(i).getfValueType().getCode());
					}
					if(StringUtils.isNotEmpty(list.get(i).getfValueType().getName())){
						list.get(i).setfValueTypeShow(list.get(i).getfValueType().getName());
					}
				}
				if(list.get(i).getfAmortizeStatus()!=null){
					if(StringUtils.isNotEmpty(list.get(i).getfAmortizeStatus().getName())){
						list.get(i).setfAmortizeStatusShow(list.get(i).getfAmortizeStatus().getName());
					}
				}
			}
		}
		model.addAttribute("list", list);
		return "/WEB-INF/view/assets/export/storageFixed";
	}
	/*
	 * 跳转到无形资产入账打印页面
	 * @author 赵孟雷
	 * @createtime 2020-08-11
	 * @updatetime 2020-08-11
	 */
	@RequestMapping(value = "/storageIntangible")
	public String storageIntangible(Integer id, ModelMap model) throws Exception {
		Storage storage=storageMng.findById(Integer.valueOf(id));
		Lookups fGainingMethod=lookupsMng.findByLookCode(storage.getfGainingMethod());
		storage.setfGainingMethods(fGainingMethod.getName());
		model.addAttribute("bean", storage);
		List<Regist> list =  registMng.findByProperty("fId_S", id);
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getfDepreciationStatus()!=null){
					if(StringUtils.isNotEmpty(list.get(i).getfDepreciationStatus().getCode())){
						list.get(i).setfDepreciationStatusCode(list.get(i).getfDepreciationStatus().getCode());
					}
					if(StringUtils.isNotEmpty(list.get(i).getfDepreciationStatus().getName())){
						list.get(i).setfDepreciationStatusShow(list.get(i).getfDepreciationStatus().getName());
					}
				}
				if(list.get(i).getfValueType()!=null){
					if(StringUtils.isNotEmpty(list.get(i).getfValueType().getCode())){
						list.get(i).setfValueTypeCode(list.get(i).getfValueType().getCode());
					}
					if(StringUtils.isNotEmpty(list.get(i).getfValueType().getName())){
						list.get(i).setfValueTypeShow(list.get(i).getfValueType().getName());
					}
				}
				if(list.get(i).getfAmortizeStatus()!=null){
					if(StringUtils.isNotEmpty(list.get(i).getfAmortizeStatus().getName())){
						list.get(i).setfAmortizeStatusShow(list.get(i).getfAmortizeStatus().getName());
					}
				}
				if(list.get(i).getfAdminOfficial()!=null){
					if(StringUtils.isNotEmpty(list.get(i).getfAdminOfficial())){
						Depart depart = departMng.findById(list.get(i).getfAdminOfficial());
						list.get(i).setfAdminOfficialShow(depart.getName());
					}
				}
			}
		}
		model.addAttribute("list", list);
		return "/WEB-INF/view/assets/export/storageIntangible";
	}
	
	
	/*
	 * 跳转到固定资产处置打印页面
	 * @author 赵孟雷
	 * @createtime 2020-08-11
	 * @updatetime 2020-08-11
	 */
	@RequestMapping(value = "/handleFixed")
	public String handleFixed(Integer id, ModelMap model) throws Exception {
		Handle handle = handleMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", handle);
		List<AssetRegistration> list = handleMng.inAndFixedHandle(String.valueOf(id), "");
		for (int i = 0; i < list.size(); i++) {
			if("".equals(list.get(i).getfActionDate())){
				list.get(i).setfActionDate(null);
			}else{
				list.get(i).setfActionDate(list.get(i).getfActionDate());
			}
		}
		model.addAttribute("list", list);
		
		List<TProcessPrintDetail> checkList = new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("Handle", "fId", handle.getfId()); 
		model.addAttribute("checkList", checkList);
		
		return "/WEB-INF/view/assets/export/handleFixed";
	}
	/*
	 * 跳转到固定资产处置打印页面
	 * @author 赵孟雷
	 * @createtime 2020-08-11
	 * @updatetime 2020-08-11
	 */
	@RequestMapping(value = "/handleSign")
	public String handleSign(Integer id, ModelMap model) throws Exception {
		Handle handle = handleMng.findById(Integer.valueOf(id));
		String times ="";
		if(!"".equals(handle.getfReqTime())){
			DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
			times =fmt.format(handle.getfReqTime());     // 转换成 X年X月X日
		}
		handle.setfReqTimes(times);
		model.addAttribute("bean", handle);
		List<TProcessPrintDetail> checkList = new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("Handle", "fId", handle.getfId()); 
		model.addAttribute("checkList", checkList);
		
		return "/WEB-INF/view/assets/export/handleSign";
	}
	/*
	 * 跳转到无形资产处置打印页面
	 * @author 赵孟雷
	 * @createtime 2020-08-11
	 * @updatetime 2020-08-11
	 */
	@RequestMapping(value = "/handleIntangible")
	public String handleIntangible(Integer id, ModelMap model) throws Exception {
		Handle handle = handleMng.findById(Integer.valueOf(id));
		model.addAttribute("bean", handle);
		List<AssetRegistration> list = handleMng.inAndFixedHandle(String.valueOf(id), "");
		for (int i = 0; i < list.size(); i++) {
			if("".equals(list.get(i).getfActionDate())){
				list.get(i).setfActionDate(null);
			}else{
				list.get(i).setfActionDate(list.get(i).getfActionDate());
			}
		}
		model.addAttribute("list", list);
		
		List<TProcessPrintDetail> checkList = new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("Handle", "fId", handle.getfId()); 
		model.addAttribute("checkList", checkList);
		return "/WEB-INF/view/assets/export/handleIntangible";
	}
	
	
	/**
	 * 资产交回打印
	 * @param id
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年8月11日
	 * @updator 陈睿超
	 * @updatetime 2020年8月11日
	 */
	@RequestMapping("/returns")
	public String returns(String id ,ModelMap model){
		try {
			AssetReturn assetReturn = new AssetReturn();
			assetReturn = assetReturnMng.findById(Integer.valueOf(id));
			model.addAttribute("ledger", "detail");
			model.addAttribute("openType", "detail");
			model.addAttribute("bean", assetReturn);
			
			List<AssetReturnList> list = new ArrayList<AssetReturnList>();
			//查询申请明细
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
					list.get(i).setfAvailableStauts_AR(assetBasicInfo.getfAvailableStauts().getName());
				}
			}
			model.addAttribute("assetReturnList", list);
			
			//审签信息
			List<TProcessPrintDetail> listTProcessChecks = new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("AssetReturn", "fId_A", assetReturn.getfId_A());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			
			User userBM = userMng.getUserByRoleNameAndDepartName("实物管理岗", "办公室");
			
			for (TProcessPrintDetail tProcessPrintDetail : listTProcessChecks) {
				if(tProcessPrintDetail.getFuserId().equals(userBM.getId())){
					assetReturn.setfRealityReturnTime(tProcessPrintDetail.getFcheckTime());
				}
			}
			return "/WEB-INF/view/assets/export/returns";
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			return "/WEB-INF/view/assets/export/returns";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "/WEB-INF/view/assets/export/returns";
		}
	}
	
	/**
	 * 领用单打印
	 * @param id
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年8月12日
	 * @updator 陈睿超
	 * @updatetime 2020年8月12日
	 */
	@RequestMapping("/receFixed")
	public String receFixed(String id,ModelMap model ){
		try {
			Rece bean = new Rece();
			bean = receMng.findById(Integer.valueOf(id));
			model.addAttribute("bean", bean);
			//申领清单
			String fAssReceCode = bean.getfAssReceCode();
			Pagination p=receListMng.findByfAssReceCode(fAssReceCode ,1,1000);
			List<ReceList> receList = (List<ReceList>) p.getList();
			model.addAttribute("receList", receList);
			//配置清单
			Pagination p1 = receConfigListMng.findbyrece_CL(null,bean, 1, 1000);
			List<ReceConfigList> receConfigList = (List<ReceConfigList>) p1.getList();
			for (int i = 0; i < receConfigList.size(); i++) {
				ReceConfigList receConfig = receConfigList.get(i);
				AssetType assetType = assetTypeMng.findbyCode(receConfig.getFfixedType_CL());
				receConfigList.get(i).setFfixedType_show(assetType.getName());
			}
			model.addAttribute("receConfigList", receConfigList);
			//审签信息
			List<TProcessPrintDetail> listTProcessChecks = new ArrayList<TProcessPrintDetail>();//tProcessPrintDetailMng.findbytab("Rece", "fId_R", bean.getfId_R());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			
			return "/WEB-INF/view/assets/export/rece";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "/WEB-INF/view/assets/export/rece";
		} catch (Exception e) {
			e.printStackTrace();
			return "/WEB-INF/view/assets/export/rece";
		}
	}
	
	
	
	
	
}
