package com.braker.icontrol.cgmanage.cginquiries.controller;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

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
import com.braker.core.manager.CheterMng;
import com.braker.core.model.CheterInfo;
import com.braker.icontrol.cgmanage.cgapply.manager.CgApplysqMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanListMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;
import com.braker.icontrol.cgmanage.cginquiries.manager.CgInquiriesListMng;
import com.braker.icontrol.cgmanage.cginquiries.manager.CgInquiriesMng;
import com.braker.icontrol.cgmanage.cginquiries.model.Inq;
import com.braker.icontrol.cgmanage.cginquiries.model.InqWinningList;
import com.braker.icontrol.cgmanage.cginquiries.model.InqWinningRef;
import com.braker.icontrol.cgmanage.cginquiries.model.Sel;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgBidWinRefMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgSelMng;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;


/**
 * 询比价登记的控制层
 * 本模块用于采购过程登记的操作
 * @author 冉德茂
 * @createtime 2018-07-31
 * @updatetime 2018-07-31
 */
@Controller
@RequestMapping(value = "/cginquiries")
public class CgInquiriesController extends BaseController{

	@Autowired
	private CgInquiriesMng inquiriesMng;
	
	@Autowired
	private CgInquiriesListMng inquiriesListMng;

	@Autowired
	private CgSelMng cgselMng;
	
	@Autowired
	private CgBidWinRefMng cgbwrefMng;
	
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private CgConPlanMng confplanMng;
	@Autowired
	private CgApplysqMng cgsqMng;
	@Autowired
	private CgConPlanListMng cgConPlanListMng;
	/*
	 * 跳转到列表页面
	 * @author 冉德茂
	 * @createtime 2018-07-31
	 * @updatetime 2018-07-31
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/inquiries/inquiries_list";
	}
	

	
	/*
	 * 新增供应商报价信息
	 * @author 冉德茂
	 * @createtime 2018-07-31
	 * @updatetime 2018-07-31
	 */
	@RequestMapping("/add")
	public String add(ModelMap model,String fpid){	
		
		model.addAttribute("fpid", fpid);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("询比价登记");
		model.addAttribute("cheterInfo", cheterInfo);
		//取消右侧流程显示
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/purchase_manage/inquiries/inquiries_add";
	}
	
	/*
	 * 对比供应商的报价信息
	 * @author 冉德茂
	 * @createtime 2018-08-02
	 * @updatetime 2018-08-02
	 */
	@RequestMapping("/compare")
	public String compare(ModelMap model,String data,Integer fplid){	
		//传过来的data是报价清单表里的外键id  mainid  映射表的主id
		String[] datas = new String[]{};
		datas = data.split(",");
		//查询采购清单信息
		PurchaseApplyBasic purchaseApplyBasic=cgsqMng.findById(fplid);
		Integer fplId=purchaseApplyBasic.getFplId();
		List<Object> qdList = confplanMng.getMingxi("ProcurementPlanList", "fplId", fplId);
		List<Sel> selList = new ArrayList<Sel>();
		for (int i = 0; i < datas.length; i++) {
			List<InqWinningList> list = new ArrayList<InqWinningList>();
			//获取报价清单信息
			list = inquiriesListMng.getQingdan(Integer.valueOf(datas[i]));
			//获取总价
			InqWinningRef inqwinref=inquiriesMng.findById(Integer.valueOf(datas[i]));
			String ffinalPrice = inqwinref.getFfinalPrice();
			//获取映射信息用于查询供应商的id	
			InqWinningRef refbean = inquiriesMng.findById(Integer.valueOf(datas[i]));	
			 //查询供应商表
			WinningBidder wbbean=cgselMng.findById(refbean.getFwId());
			
			//获得供应商名称和总报价
			Sel sel = new Sel();
			sel.setSelName(wbbean.getFwName());//供应商名称
			sel.setSelUser(wbbean.getFwuserName());//联系人
			sel.setSelTel(wbbean.getFwTel());//联系电话
			sel.setTotalPrice(ffinalPrice);
			//获得详细品目信息
			
//			List<Inq> inqList = new ArrayList<Inq>();
//			for (int j = 0; j < list.size(); j++) {
//				Inq inq = new Inq();
//				inq.setProName(list.get(j).getFproName());//商品名称
//				inq.setFfactoryPrice(list.get(j).getFfactoryPrice());//单价
//				inq.setFdiscountPrice(list.get(j).getFdiscountPrice());//优惠后价格
//				inq.setPrice(list.get(j).getFfinalPrice());//优惠后总价
//				inq.setIsImpe(list.get(j).getFisImpe());//是否进口
//				inq.setProVendor(list.get(j).getFproVendor());//生厂商名称
//				inq.setProArea(list.get(j).getFproArea());//商品产地
//				inq.setProVersion(list.get(j).getFproVerdsion());//商品型号
//				inq.setRemark(list.get(j).getFremark());//备注
//				inqList.add(inq);
//			}
			sel.setInqList(list);
			selList.add(sel);
		}
		model.addAttribute("qdList", qdList);
		model.addAttribute("sel", selList);
		return "/WEB-INF/view/purchase_manage/inquiries/compare";
	}
	
	
	/*
	 * 采购  供应商   报价清单的保存
	 * @author 冉德茂
	 * @createtime 2018-08-01
	 * @updatetime 2018-08-01
	 */
	
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(InqWinningRef bean,String mingxi,String files,ModelMap model,String fpid,String fwid,String fmainid) {
		
		try {
			String result=inquiriesMng.save(bean, mingxi, files,getUser(), fpid, fwid,fmainid);
			if("操作成功".equals(result)){
				return getJsonResult(true,result);
			}else{
				return getJsonResult(false,result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
	}
	
	/*
	 * 分页获取报价清单信息
	 * @author 冉德茂
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@RequestMapping(value = "/cginquiriesPage")
	@ResponseBody
	public JsonPagination loanPage(InqWinningRef bean, String sort, String order, Integer page, Integer rows,String pid){
		if(page == null){page = 1;}
		if(rows == null){rows = SimplePage.DEF_COUNT;}
		Pagination p = inquiriesMng.pageList(bean, page, rows, getUser(), pid);
		List<InqWinningRef> li = (List<InqWinningRef>) p.getList();
		for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
			//通过wid查询供应商信息  给页面传值
			List<WinningBidder> wblist=cgselMng.findByWid(li.get(x).getFwId());
			li.get(x).setSelName(wblist.get(0).getFwName());
			li.get(x).setSelUser(wblist.get(0).getFwName());
			li.get(x).setSelTel(wblist.get(0).getFwTel());
			li.get(x).setSelAddr(wblist.get(0).getFwAddr());
			li.get(x).setSelRemark(wblist.get(0).getFwRemark());
		}		
		
    	return getJsonPagination(p, page);
	}
	
	/*
	 * 查看供应商的报价单信息
	 * @author 冉德茂
	 * @createtime 2018-08-01
	 * @updatetime 2018-08-01
	 */
	@RequestMapping("/detail")
	public String detail(ModelMap model, String wid, String mainid) {
		// 查询供应商信息 wid是供应商的id
		model.addAttribute("fwbean", cgselMng.findById(Integer.valueOf(wid)));
		//页面间传递报价表的id用于查看报价清单信息
		model.addAttribute("mainid", mainid);
		//取消右侧流程显示
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/purchase_manage/inquiries/inquiries_detail";

	}
	/*
	 * 修改供应商的报价单信息
	 * @author 冉德茂
	 * @createtime 2018-08-01
	 * @updatetime 2018-08-01
	 */
	@RequestMapping("/edit")
	public String edit(ModelMap model, String fpid, String wid, String mainid) {
		
		model.addAttribute("fpid", fpid);
		// 查询供应商信息 wid是供应商的id
		model.addAttribute("fwbean", cgselMng.findById(Integer.valueOf(wid)));
		//页面间传递报价表的id用于查看报价清单信息
		model.addAttribute("mainid", mainid);
		//取消右侧流程显示
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/purchase_manage/inquiries/inquiries_add";
		
	}
	
	
	/*
	 * 点击查看供应商的报价清单信息
	 * @author 冉德茂
	 * @createtime 2018-08-01
	 * @updatetime 2018-08-01
	 */
	@RequestMapping(value = "/findIndex")
	@ResponseBody
	public JsonPagination findIndex(ModelMap model, String mainid) {
		Pagination p = new Pagination();
		List<InqWinningList>  list=inquiriesListMng.getQingdan(Integer.valueOf(mainid));	
		for(InqWinningList iwl:list){
			ProcurementPlanList planl=cgConPlanListMng.findById(iwl.getFplId());
			iwl.setFplId(planl.getMainId());
			iwl.setFpurName(planl.getFpurName());
			iwl.setFpurBrand(planl.getFpurBrand());
			iwl.setFspecifModel(planl.getFspecifModel());
			iwl.setFnum(planl.getFnum());
		}
		p.setList(list);
		return getJsonPagination(p, 0);

	}
	/*
	 * 把采购清单的品牌规格加载到供货清单列表里
	 * @author 安达
	 * @createtime 2018-10-31
	 * @updatetime 2018-10-31
	 */
	@RequestMapping(value = "/findcgqd")
	@ResponseBody
	public JsonPagination findcgqd(ModelMap model, String fpid) {
		Pagination p = new Pagination();
		//查询采购清单信息
		PurchaseApplyBasic purchaseApplyBasic=cgsqMng.findById(Integer.parseInt(fpid));
		Integer fplId=purchaseApplyBasic.getFplId();
		List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fplId", fplId);
		List<InqWinningList> list=new ArrayList<InqWinningList>();
		for(Object o:mingxiList){
			ProcurementPlanList planl=(ProcurementPlanList)o;
			InqWinningList inq=new InqWinningList();
			inq.setFplId(planl.getMainId());
			inq.setFpurName(planl.getFpurName());
			inq.setFpurBrand(planl.getFpurBrand());
			inq.setFspecifModel(planl.getFspecifModel());
			inq.setFnum(planl.getFnum());
			list.add(inq);
		}
		
		p.setList(list);
		return getJsonPagination(p, 0);

	}
	/*
	 * 删除采购和供应商的映射信息及报价清单信息
	 * @author 冉德茂
	 * @createtime 2018-08-02
	 * @updatetime 2018-08-02
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id) {
		try {
			//传回来的id是主键
			inquiriesMng.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	
	
	/*
	 * 弹出供应商信息页面
	 * @author 冉德茂
	 * @createtime 2018-08-04
	 * @updatetime 2018-08-04
	 */
	@RequestMapping(value = "/selinfo")
	public String sel(ModelMap model,String fpid) {
		//System.out.println(fpid);
		model.addAttribute("fpid", fpid);
		return "/WEB-INF/view/purchase_manage/inquiries/selinfo";
	}
	/*
	 * 
	 * 判断同一个采购批次下的供应商是否已经登记
	 * @author 安达
	 * @createtime 2018-10-31
	 * @updatetime 2018-10-31
	 */
	@RequestMapping(value = "/isexit")
	@ResponseBody
	public String exit(ModelMap model,String fpid ,String fwid) {
		List<InqWinningRef> list= inquiriesMng.findByPidWid(Integer.valueOf(fpid), Integer.valueOf(fwid));
		if(list.size()>=1){
			return "1";
		}else{
			return "0";
		}
	}
	
}
