package com.braker.icontrol.incomemanage.register.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.entity.TreeEntity;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.EconomicMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Economic;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.incomemanage.register.manager.RegisterMng;
import com.braker.icontrol.incomemanage.register.model.IncomeInfo;

/**
 * 收入登记的控制层
 * 本模块用于收入登记的审批及查看
 * @author 冉德茂
 * @createtime 2018-10-08
 * @updatetime 2018-10-08
 */
@Controller
@RequestMapping(value = "/srregister")
public class RegisterController extends BaseController{

	@Autowired
	private RegisterMng registerMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private EconomicMng economicMng;
	@Autowired
	private TProBasicInfoMng projectMng;
	
	/*
	 * 跳转到列表页面
	 * @author 冉德茂
	 * @createtime 2018-10-08
	 * @updatetime 2018-10-08
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/income_manage/register/register_list";
	}
	/*
	 * 跳转到合同选择页面
	 * @author 张迅
	 * @createtime 2019-05-20
	 * @updatetime 2019-05-20
	 */
	@RequestMapping(value = "/choContract")
	public String choContract( ModelMap model) {
		return "/WEB-INF/view/income_manage/register/register_choose_contract";
	}
	/*
	 * 跳转到收入科目选择页面
	 * @author 张迅
	 * @createtime 2019-05-27
	 * @updatetime 2019-05-27
	 */
	@RequestMapping(value = "/choSrkm")
	public String choSrkm( ModelMap model) {
		return "/WEB-INF/view/income_manage/register/register_choose_srkm";
	}
	/*
	 * 分页数据获得
	 * @author 冉德茂
	 * @createtime 2018-10-08
	 * @updatetime 2018-10-08
	 */
	@RequestMapping(value = "/registerPage")
	@ResponseBody
	public JsonPagination loanPage(IncomeInfo bean, String sort, String order, Integer page, Integer rows,String searchData){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = registerMng.pageList(bean, page, rows,searchData);;
    	List<IncomeInfo> li = (List<IncomeInfo>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	/*
	 * 新增收入登记
	 * @author 冉德茂
	 * @createtime 2018-10-08
	 * @updatetime 2018-10-08
	 */
	@RequestMapping("/add")
	public String add(ModelMap model){
		IncomeInfo bean=new IncomeInfo() ;
		//自动生成登记单号
		String str="RG";
		bean.setFincomeNum(StringUtil.Random(str));	
		model.addAttribute("bean", bean);
		//自动生成登记人 登记部门
		bean.setFregisterPerson(getUser().getName());
		bean.setFregisterDepart(getUser().getDepartName());
		
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("收入登记");
		model.addAttribute("cheterInfo", cheterInfo);
		
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/income_manage/register/register_add";
	}
	/*
	 * 弹出选择到账项目页面
	 * @author 冉德茂
	 * @createtime 2018-10-08
	 * @updatetime 2018-10-08
	 */
	@RequestMapping(value = "/registerinfo")
	public String check(ModelMap model) {
		return "/WEB-INF/view/income_manage/register/checkinfo_list";
	}
	/*
	 * 跳转到指标选择页面
	 * @author 冉德茂
	 * @createtime 2018-10-08
	 * @updatetime 2018-10-08
	 
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		return "/WEB-INF/view/income_manage/register/checkIndex";
	}*/
	/*
	 * 
	 * 保存收入信息
	 * @author 冉德茂
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(IncomeInfo bean,ModelMap model) {
		try {
			registerMng.save(bean,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 删除收入信息
	 * @author 冉德茂
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id) {
		try {
			//传回来的id是主键
			registerMng.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	
	/*
	 * 修改收入信息
	 * @author 冉德茂
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	@RequestMapping(value = "/edit")
	public String edit(String id, ModelMap model) {
		// 查询基本信息
		model.addAttribute("bean", registerMng.findById(Integer.valueOf(id)));	
		
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("收入登记");
		model.addAttribute("cheterInfo", cheterInfo);
		
		model.addAttribute("splc", "1");
		
		return "/WEB-INF/view/income_manage/register/register_add";

	}
	
	
	/*
	 * 查看收入详情信息
	 * @author 冉德茂
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	@RequestMapping(value ="/detail")
	public String detail(String id,ModelMap model){
		//查询基本信息
		model.addAttribute("bean", registerMng.findById(Integer.valueOf(id)));	
		
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("收入登记");
		model.addAttribute("cheterInfo", cheterInfo);
		
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/income_manage/register/register_detail";
	}
	
	
	/*
	 * 树形图显示收入科目信息
	 * @author 冉德茂
	 * @createtime 2018-09-07
	 * @updatetime 2018-09-07
	 */
	@RequestMapping(value="/tree")
	@ResponseBody
	public List<TreeEntity> tree(String id,TreeEntity te,String fPeriod,ModelMap model) {
		//id是当前的点击的tree节点的id  自动获得
		//System.out.println(id);
		
		// 内容。取所有列表，找出父菜单。
		List<Object[]> Economic = null;
		List<TreeEntity> list = new ArrayList<TreeEntity>();

		List<Economic> e = new ArrayList<Economic>();
		//PurchasingCatalogues cata = purchasingCataloguesMng.findById(Integer.valueOf(id));
		if(StringUtil.isEmpty(id)){//页面默认加载tree
			e= economicMng.indexTree(null);//查询所有的采购目录信息
			for(int i=0;i<e.size();i++){
				TreeEntity node = new TreeEntity();
				node.setText(e.get(i).getName());//科目名称
				node.setId(e.get(i).getCode());//当前科目的编码
				node.setCol10(e.get(i).getFid().toString());//当前科目的id
				node.setCol9(e.get(i).getLeve());//科目级别
				node.setCol8(e.get(i).getType());//科目类型
				node.setState("closed");
				node.setHaveChild("true");//有下级节点
				list.add(node);
			}
		}else{//点击tree  自动带id(当前的科目编码)
			e= economicMng.indexTree(id);//查询点击的节点的采购目录信息
			for(int i=0;i<e.size();i++){
				TreeEntity node = new TreeEntity();
				node.setText(e.get(i).getName());//科目名称
				node.setId(e.get(i).getCode());//当前科目的编码
				node.setCol10(e.get(i).getFid().toString());//当前科目的id
				node.setCol9(e.get(i).getLeve());//科目级别
				node.setCol8(e.get(i).getType());//科目类型
				if(economicMng.indexTree(e.get(i).getCode().toString()).size()>0){//有下级节点
					node.setState("closed");
					node.setHaveChild("true");//有下级节点
				}else{//没有下级节点
					node.setLeaf(true);
					node.setHaveChild("false");
				}
				list.add(node);
			}
		}
				
		return list;
	}
	
	/*
	 * 收入科目树形图
	 */
	@RequestMapping(value = "/treeListSrkm")
	@ResponseBody
	public List<TreeEntity> treezZckm(String id, String qName, String sCode,
			ModelMap model) {
		return getTree1(id, qName);
	}
	
	// 获得支出项科目树（没有默认值）
	private List<TreeEntity> getTree1(String id, String qName) {

		qName = StringUtil.setUTF8(qName);
		List<TreeEntity> treeList = new ArrayList<>();
		List<Object[]> objList = null;
		// 查询数据
		if (id == null) {
			// 获取本年第一级支出菜单
			objList = registerMng.getInComeSubject(null, "0",
					new SimpleDateFormat("yyyy").format(new Date()), qName);
		} else {
			// 获取本年非第一级支出菜单
			objList = registerMng.getInComeSubject(null, id,
					new SimpleDateFormat("yyyy").format(new Date()), qName);
		}
		// 放入tree值
		if (objList != null && objList.size() > 0) {
			String nodeCodes = "";
			for (Object[] array : objList) {
				if ("".equals(nodeCodes)) {
					nodeCodes = (String) array[2];
				} else {
					nodeCodes = nodeCodes + "," + (String) array[2];
				}
			}
			Map<String, Integer> pidMap = registerMng.getPidMap(null, nodeCodes,
					new SimpleDateFormat("yyyy").format(new Date()), qName);
			for (Object[] array : objList) {
				// 节点id 节点名称 节点代码 父节点代码
				String nodeId = String.valueOf(array[0]);
				String nodeName = (String) array[1];
				String nodeCode = (String) array[2];
				String parentCode = String.valueOf(array[3]);
				TreeEntity node = new TreeEntity();
				node.setText(nodeName);// 名称
				node.setCode(nodeCode);// 节点代码
				node.setId(nodeId);// 节点id
				node.setParentCode(parentCode);// 父节点代码
				if (pidMap.get(nodeCode) != null) {
					node.setState("closed");
				} else {
					node.setLeaf(true);
				}
				treeList.add(node);
			}
		}
		return treeList;

	}


}
