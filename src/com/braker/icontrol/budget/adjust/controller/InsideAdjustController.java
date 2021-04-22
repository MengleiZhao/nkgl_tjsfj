package com.braker.icontrol.budget.adjust.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.budget.adjust.entity.ExportExcelLedger;
import com.braker.icontrol.budget.adjust.entity.TIndexAdItf;
import com.braker.icontrol.budget.adjust.entity.TIndexExteAd;
import com.braker.icontrol.budget.adjust.entity.TIndexExteAdLst;
import com.braker.icontrol.budget.adjust.entity.TIndexInnerAd;
import com.braker.icontrol.budget.adjust.manager.InsideAdjustMny;
import com.braker.icontrol.budget.adjust.manager.OutsideAdjustMny;
import com.braker.icontrol.budget.adjust.manager.TIndexAdItfMng;
import com.braker.icontrol.budget.adjust.manager.TIndexExteAdLstMng;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

/**
 * 预算内部修改的控制层
 * 本模块用于预算内部修改
 * @author 叶崇晖
 * @createtime 2018-07-10
 * @updatetime 2018-07-10
 */
@Controller
@RequestMapping(value = "/insideAdjust")
public class InsideAdjustController extends BaseController{
	@Autowired
	private InsideAdjustMny insideAdjustMny;
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	TIndexAdItfMng adItfMng;
	
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	
	
	
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private OutsideAdjustMny adjustMny;
	
	@Autowired
	private TIndexExteAdLstMng adLstMng;
	
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/*
	 * 跳转到列表页面
	 * @author 叶崇晖
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/budget/adjust/inside-list";
	}
	

	/*
	 * 分页数据获得
	 * @author 叶崇晖
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@RequestMapping(value = "/adjustPage")
	@ResponseBody
	public JsonPagination adjustPage(TIndexInnerAd bean, Integer page, Integer rows, String type) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = insideAdjustMny.pageList(bean, page, rows, getUser(), type);
    	
    	//序号设置	
    	List<TIndexInnerAd> li = (List<TIndexInnerAd>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
    		li.get(i).setNum((i+1)+(page-1)*rows);
    		//查找调出指标名称、调出金额
    		List<TIndexAdItf> adItfoutList = adItfMng.findByInId(li.get(i).getInId()+"","OUT");
    		String indexNameOut="";
    		Double changeAmountOut=0d;
    		if(adItfoutList!=null && adItfoutList.size()>0){
    			for(int j=0;j<adItfoutList.size();j++){
	    			if("".equals(indexNameOut)){
	    				indexNameOut=adItfoutList.get(j).getIndexName();
	    			}else{
	    				indexNameOut=indexNameOut+" | "+adItfoutList.get(j).getIndexName();
	    			}
	    			if(adItfoutList.get(j).getChangeAmount() !=null){
	    				changeAmountOut+=adItfoutList.get(j).getChangeAmount();
	    			}
    			}
    		}	
    		li.get(i).setIndexNameOut(indexNameOut);
			li.get(i).setChangeAmountOut(changeAmountOut);
			//查找调入指标名称、调入金额
    		List<TIndexAdItf> adItfinList = adItfMng.findByInId(li.get(i).getInId()+"","IN");
    		String indexNameIn="";
    		Double changeAmountIn=0d;
    		if(adItfinList!=null && adItfinList.size()>0){
    			for(int k=0;k<adItfinList.size();k++){
    				if("".equals(indexNameIn)){
        				indexNameIn=adItfinList.get(k).getIndexName();
        			}else{
        				indexNameIn=indexNameIn+"|"+adItfinList.get(k).getIndexName();
        			}
    				if (adItfinList.get(k) != null && adItfinList.get(k).getChangeAmount() != null) {
        				changeAmountIn+=adItfinList.get(k).getChangeAmount();
        			}
    			}
    		}
    		li.get(i).setIndexNameIn(indexNameIn);
			li.get(i).setChangeAmountIn(changeAmountIn);
		}
    	
    	return getJsonPagination(p, page);
	}
	/*
	*导出调整台账
	* @author 赵孟雷
	* @createtime 2019-04-1
	* @updatetime 2018-04-1
	*/
	@RequestMapping("/exportLedger")
	public String exportLedger(ModelMap model, HttpServletResponse response, HttpServletRequest request,String inside_indexNameOut,String inside_indexNameIn,Double outside_changeAmountEnd,Double outside_changeAmountBegin,String outside_indexName){
		OutputStream out = null;
		//初始化
		try {
			response.setHeader("Content-Disposition","attachment; filename="+new String("调整台账".getBytes("gbk"), "iso8859-1")+".xls");   
			out = new BufferedOutputStream(response.getOutputStream());   
			response.setContentType("application/octet-stream");   
			String path = request.getSession().getServletContext().getRealPath("/resource");
			String filePath=path+"\\download\\调整台账导出表.xls";
			//内部台账
			TIndexInnerAd tIndexInnerAd = new TIndexInnerAd();
			tIndexInnerAd.setIndexNameIn(inside_indexNameIn);
			tIndexInnerAd.setIndexNameOut(inside_indexNameOut);
			Pagination pagination1 = insideAdjustMny.pageList(tIndexInnerAd,1,10000,null,"");
			List<TIndexInnerAd> list = (List<TIndexInnerAd>) pagination1.getList();
			
	    	for (int i = 0; i < list.size(); i++) {
	    		//查找调出指标名称、调出金额
	    		List<TIndexAdItf> adItfoutList = adItfMng.findByInId(list.get(i).getInId()+"","OUT");
	    		String indexNameOut="";
	    		Double changeAmountOut=0d;
	    		if(adItfoutList!=null && adItfoutList.size()>0){
	    			for(int j=0;j<adItfoutList.size();j++){
		    			if("".equals(indexNameOut)){
		    				indexNameOut=adItfoutList.get(j).getIndexName();
		    			}else{
		    				indexNameOut=indexNameOut+" | "+adItfoutList.get(j).getIndexName();
		    			}
		    			if(adItfoutList.get(j).getChangeAmount() !=null){
		    				changeAmountOut+=adItfoutList.get(j).getChangeAmount();
		    			}
	    			}
	    		}	
	    		list.get(i).setIndexNameOut(indexNameOut);
	    		list.get(i).setChangeAmountOut(changeAmountOut);
				//查找调入指标名称、调入金额
	    		List<TIndexAdItf> adItfinList = adItfMng.findByInId(list.get(i).getInId()+"","IN");
	    		String indexNameIn="";
	    		Double changeAmountIn=0d;
	    		if(adItfinList!=null && adItfinList.size()>0){
	    			if("".equals(indexNameIn)){
	    				indexNameIn=adItfinList.get(0).getIndexName();
	    			}else{
	    				indexNameIn=indexNameIn+"|"+adItfinList.get(0).getIndexName();
	    			}
	    			if (adItfinList.get(0) != null && adItfinList.get(0).getChangeAmount() != null) {
	    				changeAmountIn+=adItfinList.get(0).getChangeAmount();
	    			}
	    		}
	    		list.get(i).setIndexNameIn(indexNameIn);
	    		list.get(i).setChangeAmountIn(changeAmountIn);
			}
			//外部台账
			TIndexExteAd tIndexExteAd = new TIndexExteAd();
			tIndexExteAd.setIndexName(outside_indexName);
			tIndexExteAd.setChangeAmountEnd(outside_changeAmountEnd);
			tIndexExteAd.setChangeAmountBegin(outside_changeAmountBegin);
			tIndexExteAd.setFlowStauts("9");
			Pagination pagination = adjustMny.pageList(tIndexExteAd,1,10000,null);
			List<TIndexExteAd> list1 = (List<TIndexExteAd>) pagination.getList();
			for (int i = 0; i < list1.size(); i++) {
	    		String indexName="";
	    		Double changeAmount=0d;
	    		List<TIndexExteAdLst> adLstlist = adLstMng.findByAid(list1.get(i).getaId()+"");
	    		if(adLstlist!=null && adLstlist.size()>0){
	    			for(int j=0;j<adLstlist.size();j++){
		    			if("".equals(indexName)){
		    				indexName=adLstlist.get(j).getIndexName();
		    			}else{
		    				indexName=indexName+" | "+adLstlist.get(j).getIndexName();
		    			}
		    			if(adLstlist.get(j).getChangeAmount() !=null){
		    				changeAmount+=adLstlist.get(j).getChangeAmount();
		    			}
	    			}	
	    		}
	    		list1.get(i).setIndexName(indexName);
	    		list1.get(i).setChangeAmount(changeAmount);
			}
			HSSFWorkbook workbook = ExportExcelLedger.exportExcel(list,list1,filePath);
			
			if(workbook==null){
				out.flush();   
				return null;
			}
			workbook.write(out);   
			out.flush();   
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}  
			}
		}
		return null;
	}
	/*
	 * 跳转新增页面
	 * @author 叶崇晖
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@RequestMapping("/add")
	public String add(String insideDeptId, ModelMap model) {
		TIndexInnerAd bean = new TIndexInnerAd();
		bean.setOpUser(getUser().getName());
		bean.setOpTime(new Date());
		String str="IS";
		bean.setInCode(StringUtil.Random(str));
		model.addAttribute("bean", bean);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("内部指标调整");
		model.addAttribute("cheterInfo", cheterInfo);
		//查询工作流
		//如果是部门内部调整，则走配置好的流程
		if(StringUtils.isEmpty(insideDeptId)){
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "NBZBDZ", getUser().getDpID(), null, null, null, null, null, null);
			model.addAttribute("nodeConf", nodeConfList);
		}else {
			List<TNodeData> nodeConfList = tProcessCheckMng.getInSideAdjustNodeConf(getUser().getId(), insideDeptId, null);
			model.addAttribute("nodeConf", nodeConfList);
		}
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		//打开方式
		model.addAttribute("openType", "add");
		return "/WEB-INF/view/budget/adjust/inside-add";
	}
	
	/*
	 * 跳转查看修改页面
	 * @author 叶崇晖
	 * @createtime 2018-07-27
	 * @updatetime 2018-07-27
	 */
	@RequestMapping("/edit")
	public String edit(Integer id, ModelMap model) {
		TIndexInnerAd bean = insideAdjustMny.findById(id);
		model.addAttribute("bean", bean);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("内部指标调整");
		model.addAttribute("cheterInfo", cheterInfo);
		//查询工作流
		//如果是部门内部调整，则走配置好的流程
		if(StringUtils.isEmpty(bean.getInsideDeptId())){
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "NBZBDZ", bean.getDeptCode(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getInCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("NBZBDZ", bean.getDeptCode());
			model.addAttribute("fpId", tProcessDefin.getFPId());
		}else {
			List<TNodeData> nodeConfList = tProcessCheckMng.getInSideAdjustNodeConf(bean.getUserId(), bean.getInsideDeptId(), bean.getInCode());
			model.addAttribute("nodeConf", nodeConfList);
		}
		//建立工作流发起人的信息
		String departName=departMng.findDeptByUserId(bean.getAppUser())[1];
		Proposer proposer = new Proposer(bean.getOpUser(), departName, bean.getOpTime());
		model.addAttribute("proposer", proposer);
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());
		//打开方式
		model.addAttribute("openType", "edit");
		return "/WEB-INF/view/budget/adjust/inside-add";
	}
	
	/**
	 * 
	 * @Description: 跳转查看查看页面
	 * @author 汪耀
	 * @param @param id
	 * @param @param model
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/detail")
	public String detail(Integer id, ModelMap model) {
		TIndexInnerAd bean = insideAdjustMny.findById(id);
		model.addAttribute("bean", bean);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("内部指标调整");
		model.addAttribute("cheterInfo", cheterInfo);
		//查询工作流
		//如果是部门内部调整，则走配置好的流程
		if(StringUtils.isEmpty(bean.getInsideDeptId())){
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getUserId(), "NBZBDZ", bean.getDeptCode(), bean.getBeanCode(), bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getInCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("NBZBDZ", bean.getDeptCode());
			model.addAttribute("fpId", tProcessDefin.getFPId());
		}else {
			List<TNodeData> nodeConfList = tProcessCheckMng.getInSideAdjustNodeConf(bean.getUserId(), bean.getInsideDeptId(), bean.getInCode());
			model.addAttribute("nodeConf", nodeConfList);
		}
		//建立工作流发起人的信息
		String departName = departMng.findDeptByUserId(bean.getAppUser())[1];
		Proposer proposer = new Proposer(bean.getOpUser(), departName, bean.getOpTime());
		model.addAttribute("proposer", proposer);
		//对象编码
		model.addAttribute("foCode", bean.getBeanCode());
		return "/WEB-INF/view/budget/adjust/inside-detail";
	}
	
	/*
	 * 跳转到指标选择页面
	 * @author 叶崇晖
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model, String type) {
		//type为界面弹出类型，0为指标调出，1为指标调入
		
		model.addAttribute("drdc", type);
		return "/WEB-INF/view/budget/adjust/inside/index";
	}
	
	
	/**
	 * 
	* @author:安达
	* @Title: findIndex 
	* @Description: 指标数据查询
	* @param model
	* @param bids 指标id字符串
	* @param pids 支出明细
	* @param inId
	* @param adType
	* @return
	* @return List    返回类型 
	* @date： 2019年6月19日下午8:56:46 
	* @throws
	 */
	@RequestMapping(value = "/findIndex")
	@ResponseBody
	public List findIndex(ModelMap model, String bids, String pids,String inId, String adType){
		List<TIndexAdItf> indexList = new ArrayList<TIndexAdItf>();
		try {
			//查询指标
			if (!StringUtil.isEmpty(pids) || !StringUtil.isEmpty(bids)) {
				if(!StringUtil.isEmpty(pids)){
					indexList=adItfMng.findList(pids);
					for (int i = 0; i < indexList.size(); i++) {
						TIndexAdItf bean = (TIndexAdItf)indexList.get(i);
						bean.setNum(i+1);
					}
				}
				if(!StringUtil.isEmpty(bids)){
					List<TBudgetIndexMgr> indexMgrList=budgetIndexMgrMng.findByBids(bids);
					 for(int i = 0; i < indexMgrList.size(); i++){
						 TBudgetIndexMgr indexMgr=indexMgrList.get(i);
						 TIndexAdItf bean=new TIndexAdItf();
						 bean.setBid(indexMgr.getbId());
						 bean.setIndexName(indexMgr.getIndexName());
						 bean.setPfAmount(indexMgr.getPfAmount());
						 bean.setPid(0);
						 bean.setActivity(indexMgr.getIndexName());
						 bean.setSyAmount(indexMgr.getSyAmount());
						 bean.setNum(i+1);
						 indexList.add(bean);
					 }
				}
			}else {
				indexList= adItfMng.findByInId(inId,adType);
				for(int x=0; x<indexList.size(); x++) {
					//序号设置	
					indexList.get(x).setNum((x+1));	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return indexList;
	}
	
	/*
	 * 内部指标调整保存
	 * @author 叶崇晖
	 * @createtime 2018-07-23
	 * @updatetime 2018-07-23
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(TIndexInnerAd bean, String dc, String dr) {
		String result = "操作成功";
		try {
			//调入调出JSON转化为调整明细对象
			JSONArray array1 = JSONArray.fromObject("["+dc.toString()+"]");
			JSONArray array2 = JSONArray.fromObject("["+dr.toString()+"]");
			List<TIndexAdItf> dcli = JSONArray.toList(array1, TIndexAdItf.class);
			List<TIndexAdItf> drli = JSONArray.toList(array2, TIndexAdItf.class);
			//保存指标内部调整信息和明细
			 result = insideAdjustMny.save(bean, getUser(), dcli, drli);
			 if(!"操作成功".equals(result)){
				 return getJsonResult(false,result);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
		return getJsonResult(true,result);
	}
	/*
	 *  内部指标调整删除
	 * @author 李安达
	 * @createtime 2019-03-13
	 * @updatetime 2019-03-13
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Result delete(Integer id,String fId,ModelMap model) {
		try {
			//传回来的id是主键
			User user = getUser();
			insideAdjustMny.delete(id,user,fId);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"删除失败，请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");	
	}
	
	/**
	 * 
	 * @Description: 根据部门刷新审批流
	 * @author 汪耀
	 * @param @param departId
	 * @param @param model
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/refreshProcess")
	public String refreshProcess(String insideDeptId, ModelMap model) {
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("内部指标调整");
		model.addAttribute("cheterInfo", cheterInfo);
		//获得当前操作人的部门id
		String departId = getUser().getDpID();
		//调增部门id和调减部门id一致，把调减部门设置为空
		if(departId.equals(insideDeptId)){
			insideDeptId = null;
		}
		//查询工作流
		//如果是部门内部调整，则走配置好的流程
		if(StringUtils.isEmpty(insideDeptId)){
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(getUser().getId(), "NBZBDZ", getUser().getDpID(), null, null, null, null, null, null);
			model.addAttribute("nodeConf", nodeConfList);
		}else {
			List<TNodeData> nodeConfList = tProcessCheckMng.getInSideAdjustNodeConf(getUser().getId(), insideDeptId, null);
			model.addAttribute("nodeConf", nodeConfList);
		}
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(getUser().getName(), getUser().getDepart().getName(), null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/check_system";	
	}
}
