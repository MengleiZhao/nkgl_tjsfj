package com.braker.icontrol.cgmanage.cgconfplan.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.ftp.FileUpload;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.PurchasingCataloguesMng;
import com.braker.icontrol.budget.performancemanage.selfeval.model.SelfEvaluation;
import com.braker.icontrol.budget.performancemanage.selfrule.model.SelfProRef;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;
import com.braker.icontrol.cgmanage.cginquiries.model.InqWinningRef;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 配置计划合并的控制层
 * 
 * @author 冉德茂
 * @createtime 2018-10-23
 * @updatetime 2018-10-23
 */
@Controller               
@RequestMapping(value = "/cgconfplancombine")
public class CgConPlanCombineController extends BaseController{
	@Autowired
	private CgConPlanMng confplanMng;
	@Autowired
	private AttachmentMng attachmentMng;
	/*
	 * 跳转到配置计划合并的页面
	 * @author 冉德茂
	 * @createtime 2018-10-23
	 * @updatetime 2018-10-23
	 */
	@RequestMapping(value = "/list")
	public String list( ModelMap model) {
		return "/WEB-INF/view/purchase_manage/confplan/confplancombine_list";
	}
	/*
	 * 判断选择的同种类型的单据  的采购清单是否完全相同  相同进行合并操作
	 * @author 冉德茂
	 * @createtime 2018-10-23
	 * @updatetime 2018-10-23
	 */
	@RequestMapping(value = "/issame")
	@ResponseBody
	public String issame(ModelMap model,String ids) {
		//根据页面选择的计划id数组，获得所有计划下的采购清单集合
		List<List<Object>> list=confplanMng.getQdListByPlids( ids);
		//先判断是否能进行合并
		String flag=confplanMng.checkCombineFlag(list);
		if( !"1".equals(flag)){ 
			//如果不能合并，则返回不能合并的原因
			return flag;  
		}
		//获得采购计划集合
		List<ProcurementPlan> planlist=confplanMng.getPlanListByPlids(ids);
		//合并计划采购单生成新的采购单对象		
		ProcurementPlan newbean=confplanMng.getCombinePlan(planlist);
		//获得合并后的采购清单
		List<ProcurementPlanList> qdlist=confplanMng.getCombineQdList(list);
		//保存合并单信息
		String[] plids = ids.split(",");
		confplanMng.saveCombine(newbean,qdlist, plids, getUser());
		return "合并成功";
	}
	/*
	 * 判断选择的同种类型的单据  的采购清单是否完全相同  相同进行合并操作
	 * @author 冉德茂
	 * @createtime 2018-10-23
	 * @updatetime 2018-10-23
	 
	@RequestMapping(value = "/issame")
	@ResponseBody
	public String exit(ModelMap model,String ids) {
		//保存规避清单     获取规避表的所有id
		String[] plids = new String[]{};
		plids = ids.split(",");
		List<ProcurementPlan> confbean = new ArrayList<ProcurementPlan>();//计划单
		List<ProcurementPlanList> qdlist = new ArrayList<ProcurementPlanList>();//清单
		List<String> code=new ArrayList<String>();
		List<String> pinpai=new ArrayList<String>();
		List<String> guige=new ArrayList<String>();
		List<String> danjia=new ArrayList<String>();
		boolean flage1=true;
		boolean flage2=true;
		boolean flage3=true;
		boolean flage4=true;
		for (int i = 0; i < plids.length; i++) {//获取所有的采购清单
			Integer plid=Integer.valueOf(plids[i]);
			ProcurementPlan planbean = confplanMng.findById(plid);
			confbean.add(planbean);
			List<Object> mingxi = confplanMng.getMingxi("ProcurementPlanList", "fplId",plid);
			for(int j=0;j<mingxi.size();j++){
				ProcurementPlanList mingxibean =(ProcurementPlanList) mingxi.get(j);
				qdlist.add(mingxibean);
			}
		}
		for(int i=0;i<qdlist.size();i++){//取出目录  品牌  规格  单价
			code.add(qdlist.get(i).getFpurCode());
			pinpai.add(qdlist.get(i).getFpurBrand());
			guige.add(qdlist.get(i).getFspecifModel());
			danjia.add(String.valueOf(qdlist.get(i).getFunitPrice()));
		}
		for(int i=0;i<code.size();i++){//目录
			String firstcode=code.get(0);
			if(!code.get(i).equals(firstcode)){
				flage1=false;
			}
		}
		for(int i=0;i<pinpai.size();i++){//品牌
			String firstpinpai=pinpai.get(0);
			if(!pinpai.get(i).equals(firstpinpai)){
				flage2=false;
			}
		}
		for(int i=0;i<guige.size();i++){//规格
			String firstguige=guige.get(0);
			if(!guige.get(i).equals(firstguige)){
				flage3=false;
			}
		}
		for(int i=0;i<danjia.size();i++){//单价
			String firstdanjia=danjia.get(0);
			if(!danjia.get(i).equals(firstdanjia)){
				flage4=false;
			}
		}
		
		if(flage1==true && flage2==true && flage3==true && flage4==true){//满足合并条件进行合并
			//1.合并采购单
			StringBuffer listcode = new StringBuffer();
			Date retime = null;
			String type = null;
			String user = null;
			String tel = null;
			String content = null;
			String remark = null;
			//合并计划单   部门以逗号分割，其它字段取第一条
			for(int i=0;i<confbean.size();i++){
				if(i==0){
					listcode.append(confbean.get(i).getFreqDept());
					retime=confbean.get(i).getFreqTime();
					type=confbean.get(i).getFprocurType();
					user=confbean.get(i).getFreqLinkMen();
					tel=confbean.get(i).getFlinkTel();
					content=confbean.get(i).getFreqContent();
					remark=confbean.get(i).getFremark();
				}else{
					listcode.append("|"+confbean.get(i).getFreqDept());
				}
				
			}
			
			ProcurementPlan newbean=new ProcurementPlan();
			String str="HB";
			newbean.setFlistNum(StringUtil.Random(str));//合并单号
			newbean.setFreqDept(listcode.toString());//申请部门
			newbean.setFreqTime(retime);//申请日期
			newbean.setFprocurType(type);//采购类型
			newbean.setFreqLinkMen(user);//申请人
			newbean.setFlinkTel(tel);//联系电话
			newbean.setFreqContent(content);//内容
			newbean.setFremark(remark);//备注
			newbean.setFcheckStauts("9");//设置审批状态为已审批			
			
			//2.合并采购清单
			String purcode=null;
			String purname=null;
			String unit=null;
			String brand=null;
			String xinghao=null;
			int num=0;
			double unitprice=0;
			double money=0;
			String prop=null;
			Date needtime=null;
			for(int i=0;i<qdlist.size();i++){//合并数量和金额  其它字段取第一条
				purcode=qdlist.get(0).getFpurCode();
				purname=qdlist.get(0).getFpurName();
				unit=qdlist.get(0).getFmeasureUnit();
				brand=qdlist.get(0).getFpurBrand();
				xinghao=qdlist.get(0).getFspecifModel();
				num+=qdlist.get(i).getFnum();//总数量
				unitprice=qdlist.get(0).getFunitPrice();
				money+=qdlist.get(i).getFsumMoney();//总价格
				prop=qdlist.get(0).getFcommProp();
				needtime=qdlist.get(0).getFneedTime();
			}
						
			ProcurementPlanList listbean=new ProcurementPlanList();
			listbean.setFpurCode(purcode);
			listbean.setFpurName(purname);
			listbean.setFmeasureUnit(unit);
			listbean.setFpurBrand(brand);
			listbean.setFspecifModel(xinghao);
			listbean.setFnum(num);//总数量
			listbean.setFunitPrice(unitprice);
			listbean.setFsumMoney(money);//总价格
			listbean.setFcommProp(prop);
			listbean.setFneedTime(needtime);
						
			confplanMng.saveCombine(newbean,listbean, plids, getUser());//保存合并单信息
			System.out.println("合并成功");
			return "1";
						
		}else if(flage1==false || flage2==false || flage3==false || flage4==false){//不满足合并条件
			
			return "0";
		} else {//系统错误
			
			return "-1";
		}		
	}*/

	/*
	 * 跳转到采购类型选择页面
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		return "/WEB-INF/view/purchase_manage/confplan/checkTypeIndex";

	}
	
	/*
	 * 分页数据获取配置计划信息
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/confplanPageData")
	@ResponseBody
	public JsonPagination loanPage(ProcurementPlan bean, String sort, String order, Integer page, Integer rows){
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = confplanMng.pageList(bean,getUser(),page, rows);;
    	List<ProcurementPlan> li = (List<ProcurementPlan>) p.getList();
    	for(int x=0; x<li.size(); x++) {
			//序号设置	
			li.get(x).setNum((x+1)+(page-1)*rows);	
		}
    	return getJsonPagination(p, page);
	}
	/*
	 * 获取已经审批通过  未在采购页面选择过的数据进行采购登记   
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@RequestMapping(value = "/pickconfplan")
	@ResponseBody
	public JsonPagination getlistjson(ProcurementPlan bean, String sort, String order, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = confplanMng.getCheckedConfplan(bean,page, rows);
		List<ProcurementPlan> prolist = (List<ProcurementPlan>) p.getList();
		for(int i=0;i<prolist.size();i++){
			//序号设置
			prolist.get(i).setNum((i+1)+(page-1)*rows);
		}
		return getJsonPagination(p, page);
	}
	
	
	/*
	 * 新增功能科目
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping("/add")
	public String add(ModelMap model){
		ProcurementPlan bean = new ProcurementPlan();
		//获取当前登录对象获得申请部门
		bean.setFreqDept(getUser().getDepartName());
		bean.setFreqDeptId(getUser().getDepart().getId());
		//申请日期
		bean.setFreqTime(new Date());
		//自动生成标号
		String str="JH";
		bean.setFlistNum(StringUtil.Random(str));	
		model.addAttribute("bean", bean);
		
		return "/WEB-INF/view/purchase_manage/confplan/confplan_add";
	}
	/*
	 * 新增的保存
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/save")
	@ResponseBody	
	public Result save(ProcurementPlan bean,String mingxi,String files,ModelMap model) {
		
		try {
			confplanMng.save(bean,mingxi, files,getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/*
	 * 查看配置计划信息
	 * @author 冉德茂
	 * @createtime 2018-10-16
	 * @updatetime 2018-10-16
	 */
	@RequestMapping(value ="/detail")
	public String detail(String id,ModelMap model){
		//id是配置计划表的主键id   fmId
		//查询基本信息
		ProcurementPlan bean=confplanMng.findById(Integer.valueOf(id));
		model.addAttribute("bean",bean);
		//查询附件信息
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		return "/WEB-INF/view/purchase_manage/confplan/confplan_detail";
	}
	/*
	 * 功能科目信息的修改
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/edit")
	public String edit(String id,ModelMap model){
		//查询基本信息
		ProcurementPlan bean=confplanMng.findById(Integer.valueOf(id));
		model.addAttribute("bean",bean);
		//查询附件信息
		List<Attachment> attac =attachmentMng.list(bean);
		model.addAttribute("attac", attac);
		
		return "/WEB-INF/view/purchase_manage/confplan/confplan_add";
	}	
	/*
	 * 功能科目信息的删除
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id) {
		try {
			//传回来的id是主键
			confplanMng.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
		
	
	/*
	 * 附件上传AJAX
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@RequestMapping(value = "/applyFile")
	@ResponseBody
	public boolean applyFile(String fileurl){
		try {
			fileurl = java.net.URLDecoder.decode(fileurl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		//获取文件名
		String[] names = fileurl.split("\\\\");
		String name = names[names.length-1];
		//保存附件文件
		FileUpload fu = new FileUpload();
		String url = fu.getFtpConfig("url");
		int port = Integer.parseInt(fu.getFtpConfig("port"));
		String username = fu.getFtpConfig("username");
		String password = fu.getFtpConfig("password");
		boolean flag = false;
		try {
			String path = "CG/CGSQ";
			String filename = name.trim();
			String input = fileurl.trim();
			flag=fu.upLoadFromProduction(url, port,username,password,path,filename,input);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/*
	 * 查询计划采购品目明细（默认查询 和采购页面选择配置单查询）
	 * @author 冉德茂
	 * @createtime 2018-10-16
	 * @updatetime 2018-10-16
	 */
	@RequestMapping(value = "/mingxi")
	@ResponseBody
	public JsonPagination mingxi(String id,String planid) {
		
		Pagination p = new Pagination();
		Integer fplId;
		if(planid!=null){//页面默认查询清单
			 fplId=Integer.valueOf(planid);
		}else{//选择配置单  加载清单信息
			fplId=Integer.valueOf(id);
		}
		//查询采购清单信息
		List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fplId", fplId);
		for(int i=0;i<mingxiList.size();i++){
			ProcurementPlanList bean = (ProcurementPlanList)mingxiList.get(i);
			//bean.setFunitPrice(new BigDecimal(bean.getFunitPrice()).stripTrailingZeros().toPlainString());//去掉多余的o
			//bean.setFamount(new BigDecimal(bean.getFamount()).stripTrailingZeros().toPlainString());//去掉多余的o
			bean.setNum(i+1);
		}
		p.setList(mingxiList);
		return getJsonPagination(p, 0);
	}
	
	
	
	
	
	
	
	
	
	
	
}
