package com.braker.icontrol.budget.adjust.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Proposer;
import com.braker.icontrol.budget.adjust.entity.TIndexAdItf;
import com.braker.icontrol.budget.adjust.entity.TIndexInnerAd;
import com.braker.icontrol.budget.adjust.manager.InsideAdjustMny;
import com.braker.icontrol.budget.adjust.manager.TIndexAdItfMng;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeLinkMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

/**
 * 预算内部修改审批的控制层
 * @author 叶崇晖
 * @createtime 2018-10-30
 * @updatetime 2018-10-30
 */
@Controller
@RequestMapping(value = "/insideCheck")
public class InsideCheckController extends BaseController {
	@Autowired
	private InsideAdjustMny insideAdjustMny;
	
	
	@Autowired
	private DepartMng departMng;
	
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private UserMng userMng;
	
	
	@Autowired
	TIndexAdItfMng adItfMng;
	
	@Autowired
	private TNodeLinkMng tNodeLinkMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/*
	 * 跳转到列表页面
	 * @author 叶崇晖
	 * @createtime 2018-10-30
	 * @updatetime 2018-10-30
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap model) {
		return "/WEB-INF/view/budget/adjust/inside-check-list";
	}
	
	/*
	 * 分页数据获得
	 * @author 叶崇晖
	 * @createtime 2018-10-30
	 * @updatetime 2018-10-30
	 */
	@RequestMapping(value = "/adjustPage")
	@ResponseBody
	public JsonPagination adjustPage(TIndexInnerAd bean, Integer page, Integer rows) {
		if(page == null){page = 1;}
    	if(rows == null){rows = SimplePage.DEF_COUNT;}
    	Pagination p = insideAdjustMny.pageList(bean, page, rows , getUser(), null);
    	
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
    			if("".equals(indexNameIn)){
    				indexNameIn=adItfinList.get(0).getIndexName();
    			}else{
    				indexNameIn=indexNameIn+"|"+adItfinList.get(0).getIndexName();
    			}
    			if (adItfinList.get(0) != null && adItfinList.get(0).getChangeAmount() != null) {
    				changeAmountIn+=adItfinList.get(0).getChangeAmount();
    			}
    		}
    		li.get(i).setIndexNameIn(indexNameIn);
			li.get(i).setChangeAmountIn(changeAmountIn);
		}
    	
    	return getJsonPagination(p, page);
	}
	
	/*
	 * /insideCheck/check
	 * 跳转审批页面
	 * @author 叶崇晖
	 * @createtime 2018-10-30
	 * @updatetime 2018-10-30
	 */
	@RequestMapping("/check")
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
		model.addAttribute("foCode",bean.getBeanCode());
		return "/WEB-INF/view/budget/adjust/inside-check";
		
	}
	
	/*
	 * /insideCheck/checkResult
	 * 审批结果
	 * @author 叶崇晖
	 * @createtime 2018-07-26
	 * @updatetime 2018-07-26
	 */
	@RequestMapping(value = "/checkResult")
	@ResponseBody
	public Result checkResult(TProcessCheck checkBean,TIndexInnerAd bean, String spjlFile) {
		try {
			insideAdjustMny.saveCheckInfo(checkBean, bean, getUser(), spjlFile);
		} catch (Exception e) {
			return getJsonResult(false, getException(e));
		}
		return getJsonResult(true, "操作成功！");
	}
}
