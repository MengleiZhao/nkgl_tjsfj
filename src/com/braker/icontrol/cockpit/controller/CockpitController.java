package com.braker.icontrol.cockpit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.entity.TreeEntity;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.Depart;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.cockpit.manager.CockpitMng;

/**
 * 驾驶舱
 * @author zhangxun
 * @createtime 2018-10-24
 * @updatetime 2018-10-24
 */
@Controller
@RequestMapping("/cockpit")
public class CockpitController extends BaseController {

	@Autowired
	public CockpitMng cockpitMng;
	@Autowired
	public DepartMng departMng;
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	/**
	 * 驾驶舱主页面 
	 */
	@RequestMapping("/list")
	public String list(ModelMap model, String year){

		if (StringUtil.isEmpty(year)) year = DateUtil.getCurrentYear();
	/*	//各部门差旅费
		List<String[]> departTravels = cockpitMng.getDepartTravelSum(getUser().getDepart(),"proper",year);
		model.addAttribute("departTravels", departTravels);*/
		//当前年度 第一张图右上角年度
		model.addAttribute("currentYear", DateUtil.getCurrentYear());
		//查询框-当前年至2016年
		int cYear = Integer.valueOf(DateUtil.getCurrentYear());
		List<Integer> yearList = new ArrayList<>();
		for (int i = cYear + 1; i>2015; i--) {
			yearList.add(i);
		}
		model.addAttribute("yearList", yearList);
		/*//查询框-部门(已经替换成下拉树结构)
		model.addAttribute("departList",departMng.getSonCompany(getUser().getDepart()));*/
		//默认查询年度
		model.addAttribute("defaultYear", DateUtil.getCurrentYear());
		//默认查询部门
		
		String userName = getUser().getName();
		if ("梁宣健".contains(userName) || userName.contains("管理员")) {
			model.addAttribute("defaultDepartId", "557743AE-4987-4B5A-ADBC-32E1D981044B");
		} else {
			model.addAttribute("defaultDepartId", getUser().getDepart()!=null? getUser().getDepart().getId():"");
		} 
		Map<String, Double> map = cockpitMng.getAssetsAmount();
		model.addAttribute("assetsamount", cockpitMng.getAssetsAmount());

		Depart depart = new Depart();
		depart.setName("all");
		Map<String, Double[]> projectmap = cockpitMng.getBudgetProjectDataSum(depart ,year,getUser());
		model.addAttribute("projectmap", projectmap);
		
		//采购管理
		List<Object> cgData =  cockpitMng.getCGDataSum(getUser().getDepart(), year, getUser());
		model.addAttribute("cgData", cgData);
		
		return "/WEB-INF/view/cockpitStatistics/cockpitStatistics";
	}
	/**
	 * 主页面（旧版本）
	 */
	@RequestMapping("/list1")
	public String list1(ModelMap model){
		/*cockpitMng.getPublicExpensesDataSum(new String[]{"基本工资","奖金"});*/
		return "/WEB-INF/view/cockpit/Copy of cockpit-list.jsp";
	}
	
	
	
	/**     ---------------以下为返回数据-----------------          **/
	
	/**
	 * 获取单位列表
	 */
	/*public List<TreeEntity> departTree(String id){
		//获得数据
		List<Depart> departList = null;
		if (StringUtil.isEmpty(id)) {
			departList = departMng.getRoots();
		}
		//转换为tree
		List<TreeEntity> treeList = new ArrayList<>();
		return treeList;
	}
	*/
	
	
	/**
	 * 培训、会议执行情况
	 */
	@RequestMapping("/data1")
	@ResponseBody
	public List<String[]> data1(String year, String departId){
		
		if (StringUtil.isEmpty(year)) year = DateUtil.getCurrentYear();
		Depart depart = null;
		if (StringUtil.isEmpty(departId)) {
			depart = getUser().getDepart();
		} else {
			depart = departMng.findById(departId);
		} 
		
		List<String[]> result = new ArrayList<>();
		//培训数量
		String[] num1 = cockpitMng.getTrainDataCount(depart,year,getUser()); 
		//培训金额
		String[] sum1 = cockpitMng.getTrainDataSum(depart,year,getUser());
		//会议数量
		String[] num2 = cockpitMng.getMeetingDataCount(depart,year,getUser());
		//会议金额
		String[] sum2 = cockpitMng.getMeetingDataSum(depart,year,getUser());
		
		result.add(num1);
		result.add(sum1);
		result.add(num2);
		result.add(sum2);
		
		return result;
	}
	
	/**
	 * 部门预算执行排名
	 */
	@RequestMapping("/departProgress")
	@ResponseBody
	public Map<String, Double[]> departProgress(String year, String departId){
		
		Depart depart = null;
		if (StringUtil.isEmpty(departId)) {
			depart = getUser().getDepart();
		} else {
			depart = departMng.findById(departId);
		} 
		if (StringUtil.isEmpty(year)) year = DateUtil.getCurrentYear();
		
		Map<String, Double[]> map = cockpitMng.getBudgetProjectDataSum(depart,year,getUser());
		return map;
	}
	
	/**
	 * 项目执行进度(执行率前五)
	 * @return
	 */
	@RequestMapping("/projectProgressTopFive")
	@ResponseBody
	public List<HashMap<Object, Object>> projectProgressTopFive(String year, String departId){
		
		Depart depart = null;
		if (StringUtil.isEmpty(departId)) {
			depart = getUser().getDepart();
		} else {
			depart = departMng.findById(departId);
		} 
		if (StringUtil.isEmpty(year)) year = DateUtil.getCurrentYear();
		List<HashMap<Object, Object>> list = cockpitMng.getprojectProgressTopOrLastFive("desc",depart,year,getUser());
		return list;
	}
	
	/**
	 * 项目执行进度(执行率后五)
	 * @return
	 */
	@RequestMapping("/projectProgressLastFive")
	@ResponseBody
	public List<HashMap<Object, Object>> projectProgressLastFive(String year, String departId){
		
		Depart depart = null;
		if (StringUtil.isEmpty(departId)) {
			depart = getUser().getDepart();
		} else {
			depart = departMng.findById(departId);
		} 
		if (StringUtil.isEmpty(year)) year = DateUtil.getCurrentYear();
		List<HashMap<Object, Object>> list = cockpitMng.getprojectProgressTopOrLastFive("asc",depart,year,getUser());
		return list;
	}
	
	/**
	 * 表盘显示数据 三公经费
	 * @param indexName 要查的指标名称
	 * @return Map类型
	 */
	@RequestMapping("/dialData")
	@ResponseBody
	public Map<String, Double[]> test(String indexName, String year, String departId){
		//5 公务接待申请 6 公务用车申请 7公务出国申请
		if("因公出国出境费用".equals(indexName)){
			indexName="7";
		}else if("公务接待费".equals(indexName)){
			indexName="5";
		}else if("公务用车购置与运维费".equals(indexName)){
			indexName="6";
		}
		
		Depart depart = null;
		boolean isflag=StringUtil.isEmpty(departId);
		if (StringUtil.isEmpty(departId)) {
			depart = getUser().getDepart();
		} else {
			depart = departMng.findById(departId);
		} 
		if (StringUtil.isEmpty(year)) year = DateUtil.getCurrentYear();
		
		Map<String, Double[]> map = cockpitMng.getPublicExpensesDataSum(new String[]{indexName}, depart,year,getUser());
		List<Map<String, Double[]>> list = new ArrayList<Map<String, Double[]>>();
		list.add(map);
		
		return map;
	}
	
	/**
	 * 差旅费（带排序） 
	 */
	@RequestMapping("/travelFunds")
	@ResponseBody
	public List<String[]> travelFunds(ModelMap model, String orderType, String year, String departId){
		
		if (StringUtil.isEmpty(year)) year = DateUtil.getCurrentYear();
		Depart depart = null;
		if (StringUtil.isEmpty(departId)) {
			depart = getUser().getDepart();
		} else {
			depart = departMng.findById(departId);
		} 
		
		List<String[]> departTravels = cockpitMng.getDepartTravelSum(depart,orderType,year,getUser());
		model.addAttribute("departTravels", departTravels);
		return departTravels;
	}
	
	/**
	 * 单位结构
	 */
	@RequestMapping(value="/departTree")
	@ResponseBody
	public List<TreeEntity> departTree(String id) {
		
		User curretUser = getUser();
		
		List<Role> roleList=curretUser.getRoles();
		List<TreeEntity> treeList = null;
		for(Role role:roleList){
			if(roleList.size()>0){
				if("局长".equals(roleList.get(0).getName())&&"分管局长".equals(roleList.get(1).getName())){
					treeList = departTree1(id);
					return treeList;
				}
			}
		if ("局长".equals(role.getName()) || "系统管理员".equals(role.getName())) {
			treeList = departTree1(id);
		} else if ("分管局长".equals(role.getName())) {
			List<Depart> departList=departMng.findByProperty("manager", curretUser);
			treeList = departTree2(curretUser.getName(),departList);
		}else {
			treeList = departTree3(curretUser.getDepartName());
		}
		}
		return treeList;
	}
	
	//普通人员登录
	public List<TreeEntity> departTree3(String departName) {
		
		String id = "557743AE-4987-4B5A-ADBC-32E1D981044B";
		
		
		// 内容。取所有列表，找出父菜单。
		List<Depart> listDepart  = departMng.getChild(id);
		List<TreeEntity> list=new ArrayList<TreeEntity>();
		if(null!=listDepart && listDepart.size()>0){
			for(Depart d:listDepart){
				TreeEntity ft=new TreeEntity();
				ft.setId(d.getId());
				ft.setText(d.getName());
				if(null!=d.getChildren() && d.getChildren().size()>0){
					ft.setState("closed");
				}else{
					ft.setLeaf(true);
				}
				if (departName.equals(ft.getText())) {
					list.add(ft);
				}
			}
		}
		return list;
	}
	
	//高级人员登录
	public List<TreeEntity> departTree2(String userName,List<Depart> departList) {
		
		String id = "557743AE-4987-4B5A-ADBC-32E1D981044B";
		
		Map<String,String> map = new HashMap<>();
		String deptIdStr="";
		//主管校长可以查看自己主管的所有部门
		for(Depart depart:departList){
			if("".equals(deptIdStr)){
				deptIdStr=depart.getName();
			}else{
				deptIdStr=deptIdStr+","+depart.getName();
			}
		}
		map.put(userName, deptIdStr);
		// 内容。取所有列表，找出父菜单。
		List<Depart> listDepart = departMng.getChild(id);
		List<TreeEntity> list=new ArrayList<TreeEntity>();
		if(null!=listDepart && listDepart.size()>0){
			for(Depart d:listDepart){
				TreeEntity ft=new TreeEntity();
				ft.setId(d.getId());
				ft.setText(d.getName());
				if(null!=d.getChildren() && d.getChildren().size()>0){
					ft.setState("closed");
				}else{
					ft.setLeaf(true);
				}
				if (map.get(userName).contains(ft.getText())) {
					list.add(ft);
				}
			}
		}
		return list;
	}
	
	//最高人员登录
	public List<TreeEntity> departTree1(String id) {
		// 内容。取所有列表，找出父菜单。
		List<Depart> listDepart=null;
		if(null==id){
			listDepart = departMng.getRoots();
		}else{
			listDepart = departMng.getChild(id);
		}
		List<TreeEntity> list=new ArrayList<TreeEntity>();
		if(null!=listDepart && listDepart.size()>0){
			for(Depart d:listDepart){
				TreeEntity ft=new TreeEntity();
				ft.setId(d.getId());
				ft.setText(d.getName());
				if(null!=d.getChildren() && d.getChildren().size()>0){
					ft.setState("closed");
				}else{
					ft.setLeaf(true);
				}
				list.add(ft);
			}
		}
		return list;
	}

	
	/**
	 * 获得采购管理数据
	 * 对应图表：根据采购类型显示柱形图
	 * @return  
	 */
	@RequestMapping(value="/getCGTypeDataSum")
	@ResponseBody
	public List<Object> getCGTypeDataSum() {
		List<Object> objects = cockpitMng.getCGTypeDataSum(getUser().getDepart(), "", getUser());
		return objects;
	}
	/**
	 * 获取资产价值（总资产金额，固定资产金额，无形资产金额）
	 * @param year
	 * @param departId
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月11日
	 * @updator 陈睿超
	 * @updatetime 2020年9月11日
	 */
	@RequestMapping("/assetsAmount")
	@ResponseBody
	public Map<String, Double> assetsAmount(String year, String departId){
		Map<String, Double> map = cockpitMng.getAssetsAmount();
		return map;
	}
	
	/**
	 * 无形资产价值
	 * @param type
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/intangibleStorageAmount")
	public List<Double> intangibleAmount(String type){
		List<Double> list = cockpitMng.getAssetsAmount(type);
		return list;
	}
	
	/**
	 * 固定资产价值增加减少
	 * @param type
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/fixedStorageAmount")
	public List<Double> fixedStorageAmount(String type){
		List<Double> list = cockpitMng.getFixedUpOrDownAmount();
		return list;
	}
	
	/**
	 * 合同管理驾驶舱
	 * @param department
	 * @return
	 * @author wanping
	 * @createtime 2020年9月14日
	 * @updator wanping
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/getContractAmount")
	public Map<String, Object> getContractAmount(String department) {
		Map<String, Object> map = cockpitMng.getContractAmount(department);
		return map;
	}

	/**
	 * 查询基本和项目预算
	 * @param type
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年9月14日
	 * @updator 赵孟雷
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/queryIndex")
	public List<Object> queryIndex(String type){
		List<Object> list0 = new ArrayList<Object>();
		List<Object> list1 = cockpitMng.getSYIndexDataSum("", getUser());
		List<Object> list2 = cockpitMng.getDJIndexDataSum("", getUser());
		List<Object> list3 = cockpitMng.getBXIndexDataSum("", getUser());
		list0.add(list1);
		list0.add(list2);
		list0.add(list3);
		return list0;
	}
	/**
	 * 查询五项常用费用
	 * @param type
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年9月14日
	 * @updator 赵孟雷
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/getAllIndexDataSum")
	public List<Object> getAllIndexDataSum(String type){
		List<Object> list0 = new ArrayList<Object>();
		List<Object> list1 = cockpitMng.getMeetingIndexDataSum("", getUser());
		List<Object> list2 = cockpitMng.getTrainIndexDataSum("", getUser());
		List<Object> list3 = cockpitMng.getTravelIndexDataSum("", getUser());
		List<Object> list4 = cockpitMng.getReceptionIndexDataSum("", getUser());
		List<Object> list5 = cockpitMng.getAbroadIndexDataSum("", getUser());
		list0.add(list1);
		list0.add(list2);
		list0.add(list3);
		list0.add(list4);
		list0.add(list5);
		return list0;
	}
	/**
	 * 查询项目指标前六数据
	 * @param type
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年9月14日
	 * @updator 赵孟雷
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/queryProIndexDataSum")
	public List<Object> queryProIndexDataSum(String type){
		List<Object> list1 = cockpitMng.queryProIndexDataSum("", getUser());
		return list1;
	}
	
	/**
	 * 固定资产可用在库资产数量
	 * @param type
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/fixedAvailableAmount")
	public Map<String, Object[]> fixedAvailableAmount(String type){
		Map<String, Object[]> map = cockpitMng.getFixedAvailableAmount(type);
		return map;
	}
	
	/**
	 * 待报废占比
	 * @param type
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	@ResponseBody
	@RequestMapping("/fixedAvailablePercentage")
	public Map<String, Double> fixedAvailablePercentage(String type){
		try {
			Map<String, Double> map = cockpitMng.getFixedAvailablePercentage();
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 跳转到合同台账（驾驶舱）
	 * @param type 资产类型
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月14日
	 * @updator 陈睿超
	 * @updatetime 2020年9月14日
	 */
	@RequestMapping(value="/assetsLedger")
	public String assetsLedger(String type){
		
		return "/WEB-INF/view/cockpitStatistics/assets/ledger_flow";
	}
	
	
	/**
	 * 驾驶舱项目跳转-指标执行进度总表
	 */
	@RequestMapping("/indexPlanForm")
	public String indexPlanForm(ModelMap model, String indexType){
		model.addAttribute("currentYear", new SimpleDateFormat("yyyy").format(new Date()));
		model.addAttribute("indexTypeCockpit", indexType);
		return "/WEB-INF/view/cockpitStatistics/budget/data-list2";
	}
	
	
	/**
	 * 跳转到采购台账
	 * @param model
	 * @param proId
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年9月15日
	 * @updator 陈睿超
	 * @updatetime 2020年9月15日
	 */
	@RequestMapping(value = "/cgsqledgerlist")
	public String cgsqledgerlist( ModelMap model,String proId) {
		if(!StringUtil.isEmpty(proId)){
			TProBasicInfo tpbi=tProBasicInfoMng.findById(Integer.valueOf(proId));
			TBudgetIndexMgr bim = budgetIndexMgrMng.findByIndexCode(tpbi.getFProCode());
			
			model.addAttribute("indexCode", bim.getbId());
		}
		return "/WEB-INF/view/cockpitStatistics/register/cgsqledger_list";
	}
	
	
	/*
	 * 跳转到列表页面
	 * @author 叶崇晖
	 * @createtime 2018-08-14
	 * @updatetime 2018-08-14
	 */
	@RequestMapping(value = "/reimburselist")
	public String reimburselist(String type, ModelMap model,String subCode) {
		model.addAttribute("reimburseType", type);
		model.addAttribute("subCode", subCode);
		return "/WEB-INF/view/cockpitStatistics/budget/reimburse_ledger_list";
	}
	
	/**
	 * 跳转到报销台账页面
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2020年9月16日
	 * @updator wanping
	 * @updatetime 2020年9月16日
	 */
	@RequestMapping(value = "/contractList")
	public String contractList( ModelMap model) {
		return "/WEB-INF/view/expend/reimburse/ledger/reimburse_ledger_list_cockpit";
	}
}
