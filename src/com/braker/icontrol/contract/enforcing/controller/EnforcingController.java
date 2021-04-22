package com.braker.icontrol.contract.enforcing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Lookups;
import com.braker.core.model.Proposer;
import com.braker.core.model.User;
import com.braker.icontrol.assets.storage.manager.RegistMng;
import com.braker.icontrol.assets.storage.manager.StorageMng;
import com.braker.icontrol.assets.storage.model.Regist;
import com.braker.icontrol.assets.storage.model.Storage;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.manager.EnforcingMng;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.ContPay;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.manager.SignInfoMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.filing.model.SignInfo;
import com.braker.icontrol.expend.reimburse.manager.InvoiceCouponMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.model.CostDetailInfo;
import com.braker.icontrol.expend.reimburse.model.InvoiceCouponInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 合同报销
 *
 */
@Controller
@RequestMapping("/Enforcing")
public class EnforcingController extends BaseController{

	@Autowired
	private ReceivPlanMng receivPlanMng;
	@Autowired
	private EnforcingMng enforcingMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private StorageMng storageMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private ReimbAppliMng reimbAppliMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private InvoiceCouponMng invoiceCouponMng;
	@Autowired
	private SignInfoMng signInfoMng;
	@Autowired
	private CgProcessMng cgProcessMng;
	@Autowired
	private RegistMng registMng;
	/**
	 * 跳转到主页面
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-22
	 */
	@RequestMapping("/list")
	public String list(ModelMap model ){
		return "/WEB-INF/view/contract/enforcing/enforcing_list";
	}
	
	/**
	 * 显示主页面数据
	 * @param contractBasicInfo
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-22
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination JsonPagination(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows, ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = enforcingMng.queryList_E(contractBasicInfo, getUser(), page, rows);
		List<ContractBasicInfo> li=(List<ContractBasicInfo>) p.getList();
		for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p,page);
	}
	
	/**
	 * 付款页面跳转
	 * @return
	 * 
	 */
	@RequestMapping("/plan/{id}")
	public String pay(@PathVariable String id,ModelMap model){
		model.addAttribute("id", id);
		return "/WEB-INF/view/contract/enforcing/enforcing_plan_list";
	}
	
	/**
	 * 付款页面数据加载
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@RequestMapping("/planJsonPagination/{id}")
	@ResponseBody
	public JsonPagination planJsonPagination(@PathVariable String id,ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows, ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = receivPlanMng.allPlan(Integer.valueOf(id), page, rows);
		return getJsonPagination(p,page);
	}
	
	/**
	 * 新增
	 * @param id
	 * @param fPlanId
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月29日
	 * @updator 陈睿超
	 * @updatetime 2020年7月29日
	 */
	@RequestMapping(value = "/add")
	public String add(String id, Integer fPlanId,ModelMap model) {
		ReimbAppliBasicInfo bean = new ReimbAppliBasicInfo();
		bean.setrCode(StringUtil.Random(""));
		User user = getUser();	//获得合同申请人信息
		bean.setType("8");
		bean.setUserName(user.getName());
		bean.setDeptName(user.getDepartName());
		model.addAttribute("bean", bean);
		model.addAttribute("openType", "add");
		//根据申请人得到申请部门id
		String departId=departMng.findDeptByUserId(user.getId())[0];
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTFKSQ", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",bean.getBeanCode());
	
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(user.getId(),"HTFKSQ", departId,bean.getBeanCode(),bean.getnCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getrCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
	
		Proposer proposer =null;
		proposer = new Proposer(user.getName(), user.getDepart().getName(),null);
		model.addAttribute("proposer", proposer);
		return "/WEB-INF/view/contract/enforcing/enforcing_add";
	}
	
	
	/**
	 * 付款申请页面跳转
	 * @param contractBasicInfo
	 * @param model
	 * @return
	 * @author ych
	 * @createtime 2018-08-20
	 */
	@RequestMapping(value = "/edit")
	public String check(String id, Integer fPlanId,ModelMap model) {
		ReimbAppliBasicInfo rabi = reimbAppliMng.findById(Integer.valueOf(id));
		//获取付款计划
		List<ReimbAppliBasicInfo> raList = reimbAppliMng.findByProperty("payId", rabi.getPayId());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < raList.size(); i++) {
			if (!"99".equals(raList.get(i).getStauts())) {
				sb.append(raList.get(i).getReceivplanid()).append(",");
			}
		}
		model.addAttribute("receivplanids", sb.toString());
		//原合同
		ContractBasicInfo contractBasicInfo = formulationMng.findById(Integer.parseInt(rabi.getPayId()));
		model.addAttribute("contractUpdateStatus", contractBasicInfo.getfUpdateStatus());
		//获取变更合同
		List<Upt> uptList = uptMng.findByProperty("fContId_U", Integer.parseInt(rabi.getPayId()));
		if (uptList != null && !uptList.isEmpty()) {
			if (!"99".equals(uptList.get(0).getfUptStatus())) {
				model.addAttribute("upt", uptList.get(0));
			}
		}
		
		User user = userMng.findById(rabi.getUser());
		rabi.setUserName(user.getName());
		model.addAttribute("bean", rabi);
		//获取报销人信息
		model.addAttribute("operation", "edit");
		model.addAttribute("openType", "edit");
		//查询费用明细
		List<CostDetailInfo> cost =reimbAppliMng.findByRId(rabi.getrId());
		//判断申请类型
		String type = rabi.getType();
		Integer x=0;
		for(int i = 0;i <cost.size();i++){
			List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
			for(int j = 0;j <fp.size();j++){
				x=x+1;
				fp.get(j).setNum(x);
			}
			cost.get(i).setCouponList(fp);
			cost.get(i).setNum(x);
		}
		Integer index =cost.size();
		model.addAttribute("x", x);
		model.addAttribute("index", index);
		model.addAttribute("cost", cost);
		model.addAttribute("userid", getUser().getId());
		List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(rabi.getrId(),"contract-1");
		model.addAttribute("Invoicelist", list);//发票
		model.addAttribute("a", list.size());//发票数量
		
		//查询附件信息
		List<Attachment> attaList1 = attachmentMng.list(rabi);
		model.addAttribute("attaList1", attaList1);
		
		//根据申请人得到申请部门id
		String departId=departMng.findDeptByUserId(rabi.getUserId())[0];
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTFKSQ", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",rabi.getBeanCode());
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(rabi.getUserId(),"HTFKSQ", departId,rabi.getBeanCode(),rabi.getnCode(), rabi.getJoinTable(), rabi.getBeanCodeField(), rabi.getrCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(rabi.getUserName(), rabi.getDeptName(), rabi.getReimburseReqTime());
		model.addAttribute("proposer", proposer);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("申请报销");
		model.addAttribute("cheterInfo", cheterInfo);
		return "/WEB-INF/view/contract/enforcing/enforcing_add";
	}
	
	/**
	 * 事前申请新增和修改的保存
	 * @author ych
	 * @createtime 2018-08-20
	 * @updatetime 2018-08-20
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Result save(ContPay bean, ReceivPlan receivPlanBean, Integer fPlanId,String fhtzxFiles, String fReceAmount) {
		try {
			//设置实际付款金额
			bean.setfReceAmount(fReceAmount);
			//保存付款计划
			receivPlanBean = receivPlanMng.findById(fPlanId);
			enforcingMng.save(bean, receivPlanBean, getUser(),fhtzxFiles);
			return getJsonResult(true,"操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"操作失败，请联系管理员！");
		}
	}
	
	/**
	 * 跳转到查看界面
	 * @param id
	 * @param fPlanId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String detail(String id, Integer fPlanId,ModelMap model, String detailType, String handledetail) {
		if ("htbx".equals(detailType)) {
			model.addAttribute("detailType", "htbx");//合同报销查看
		}
		if (!StringUtil.isEmpty(handledetail)) {
			model.addAttribute("handledetail", "open");//在资产处置的时候合同报销查看
		}
		ReimbAppliBasicInfo rabi = reimbAppliMng.findById(Integer.valueOf(id));
		ContractBasicInfo contractBasicInfo = formulationMng.findById(Integer.parseInt(rabi.getPayId()));
		model.addAttribute("contractUpdateStatus", contractBasicInfo.getfUpdateStatus());
		if("1".equals(contractBasicInfo.getfUpdateStatus())){
			Upt upt = uptMng.findByFContId_U(String.valueOf(contractBasicInfo.getFcId())).get(0);
			model.addAttribute("upt", upt);
			model.addAttribute("contractUpdateStatus", "1");
		}else{
			model.addAttribute("contractUpdateStatus", "0");
		}
		
		User user = userMng.findById(rabi.getUser());
		rabi.setUserName(user.getName());
		model.addAttribute("bean", rabi);
		//获取报销人信息
		model.addAttribute("operation", "edit");
		model.addAttribute("openType", "detail");
		//查询费用明细
		List<CostDetailInfo> cost =reimbAppliMng.findByRId(rabi.getrId());
		//判断申请类型
		String type = rabi.getType();
		Integer x=0;
		for(int i = 0;i <cost.size();i++){
			List<InvoiceCouponInfo> fp=reimbAppliMng.findfp(cost.get(i).getcId());
			for(int j = 0;j <fp.size();j++){
				x=x+1;
				fp.get(j).setNum(x);
			}
			cost.get(i).setCouponList(fp);
			cost.get(i).setNum(x);
		}
		Integer index =cost.size();
		model.addAttribute("x", x);
		model.addAttribute("index", index);
		model.addAttribute("cost", cost);
		model.addAttribute("userid", getUser().getId());
		List<InvoiceCouponInfo> list = invoiceCouponMng.findByrID(rabi.getrId(),"contract-1");
		model.addAttribute("Invoicelist", list);//发票
		//查询附件信息
		List<Attachment> attaList1 = attachmentMng.list(rabi);
		model.addAttribute("attaList1", attaList1);
		//根据申请人得到申请部门id
		String departId=departMng.findDeptByUserId(rabi.getUserId())[0];
		//得到工作流id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTFKSQ", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//对象编码
		model.addAttribute("foCode",rabi.getBeanCode());
		//查询工作流
		List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(rabi.getUserId(),"HTFKSQ", departId,rabi.getBeanCode(),rabi.getnCode(), rabi.getJoinTable(), rabi.getBeanCodeField(), rabi.getrCode(), "1");
		model.addAttribute("nodeConf", nodeConfList);
		//建立工作流发起人的信息
		Proposer proposer = new Proposer(rabi.getUserName(), rabi.getDeptName(), rabi.getReimburseReqTime());
		model.addAttribute("proposer", proposer);
		//查询制度中心文件
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("申请报销");
		model.addAttribute("cheterInfo", cheterInfo);
		return "/WEB-INF/view/contract/enforcing/enforcing_detail";
	}
	
	/**
	 * @Description: 合同付款申请退回
	 * @param @param id
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author 赵孟雷
	 * @date 2019年10月8日
	 */
	@RequestMapping(value = "/contractReCall")
	@ResponseBody
	public Result applyReCall(Integer id) {
		try {
			//传回来的id是主键
			enforcingMng.contractReCall(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"撤回失败，请联系管理员！");
		}
		return getJsonResult(true,"撤回成功！");	
	}
	
	/**
	 * 选择合同
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月29日
	 * @updator 陈睿超
	 * @updatetime 2020年7月29日
	 */
	@RequestMapping("/chooseCont")
	public String chooseCont(ModelMap model){
		return "/WEB-INF/view/contract/enforcing/enforcing_choose_contract";
	}
	
	/**
	 * 付款时选择合同
	 * @param ContractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param mode
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月29日
	 * @updator 陈睿超
	 * @updatetime 2020年7月29日
	 */
	@RequestMapping("/contractList")
	@ResponseBody
	public JsonPagination contractList(ContractBasicInfo ContractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap mode){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = formulationMng.queryForEnforcing(ContractBasicInfo,getUser(), page, rows);
		List<ContractBasicInfo> cbi = (List<ContractBasicInfo>) p.getList();
		for (int i = 0; i < cbi.size(); i++) {
			cbi.get(i).setNumber(i+1);
		}
		p.setList(cbi);
		return getJsonPagination(p, page);
	}
	
	/**
	 * 跳转到选择固定资产入账单页面
	 * @param model
	 * @param fAssType
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月31日
	 * @updator 陈睿超
	 * @updatetime 2020年7月31日
	 */
	@RequestMapping("/choosefixedstorage")
	public String list_fixed(ModelMap model,String fAssType,String id,String checkacceptid,String selectContId,String selectUptId){
		model.addAttribute("fAssType", fAssType);
		model.addAttribute("id", id);
		model.addAttribute("checkacceptid", checkacceptid);
		model.addAttribute("selectContId", selectContId);
		model.addAttribute("selectUptId", selectUptId);
		return "/WEB-INF/view/contract/enforcing/enforcing_choose_fixed";
	}
	
	/**
	 * 跳转到选择无形资产入账单页面
	 * @param model
	 * @param fAssType
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月31日
	 * @updator 陈睿超
	 * @updatetime 2020年7月31日
	 */
	@RequestMapping("/chooseintangiblestorage")
	public String list_intangible(ModelMap model,String fAssType,String id,String checkacceptid,String selectContId,String selectUptId){
		model.addAttribute("fAssType", fAssType);
		model.addAttribute("id", id);
		model.addAttribute("checkacceptid", checkacceptid);
		model.addAttribute("selectContId", selectContId);
		model.addAttribute("selectUptId", selectUptId);
		return "/WEB-INF/view/contract/enforcing/enforcing_choose_intangible";
	}
	
	/**
	 * 加载选择固定资产入账单的数据
	 * @param storage
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月31日
	 * @updator 陈睿超
	 * @updatetime 2020年7月31日
	 */
	@RequestMapping("/fixedstorageJsonPagination")
	@ResponseBody
	public JsonPagination JsonPagination(Storage storage,String id,String checkacceptid,String sort,String order,Integer page,Integer rows,ModelMap model,String selectContId,String selectUptId){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = storageMng.chooseStoragelist(storage, id, checkacceptid, getUser(), page, rows,selectContId,selectUptId);
		if (p == null) {
			return null;
		}
		List<Storage> li= (List<Storage>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
    		Lookups lookups=lookupsMng.findByLookCode(li.get(i).getfGainingMethod());
    		List<Regist> registList = registMng.findByProperty("fId_S", li.get(i).getfId_S());
    		if (registList != null && !registList.isEmpty()) {
    			li.get(i).setfFixedType(registList.get(0).getfFixedType());
    		}
    		String fAssNameShows = "";
    		String fMModeShows = "";
    		for (int j = 0; j < registList.size(); j++) {
    			fAssNameShows += registList.get(i).getfAssName()+"、";
    			fMModeShows += registList.get(i).getfMMode()+"、";
			}
    		li.get(i).setfAssNameShow(fAssNameShows);
    		li.get(i).setfMModeShow(fMModeShows);
    		li.get(i).setfGainingMethods(lookups.getName());	
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}

	/**
	 * 根据条件查询合同签约信息
	 * @param bean
	 * @param page
	 * @return
	 * @author wanping
	 * @createtime 2020年8月31日
	 * @updator wanping
	 * @updatetime 2020年8月31日
	 */
	@ResponseBody
	@RequestMapping(value = "/getSignInfoByCondition")
	public JsonPagination getSignInfoByCondition(SignInfo bean, Integer page) {
		if(page == null){page = 1;}
		Pagination p = new Pagination();
		ContractBasicInfo contractBasicInfo = formulationMng.findById(bean.getfContId());
//		String fbiddingName = "";
		/*if (contractBasicInfo.getfContractor() != null) {
			List<BiddingRegist> brlist = cgProcessMng.findByProperty("fbiddingCode", contractBasicInfo.getfContractor());
			fbiddingName = brlist.get(0).getFbiddingName();
		}*/
		List<SignInfo> list = signInfoMng.findByProperty("fContId", bean.getfContId());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBiddingName(contractBasicInfo.getfContractor());
		}
		p.setList(list);
		p.setPageNo(1);
		p.setPageSize(list.size());
		p.setTotalCount(list.size());
		return getJsonPagination(p, page);
	}

	/**
	 * 根据合同ID获取付款计划
	 * @param payId
	 * @return
	 * @author wanping
	 * @createtime 2020年9月8日
	 * @updator wanping
	 * @updatetime 2020年9月8日
	 */
	@RequestMapping("/getReceivplanid")
	@ResponseBody
	private String getReceivplanid (String payId) {
		//获取付款计划
		List<ReimbAppliBasicInfo> raList = reimbAppliMng.findByProperty("payId", payId);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < raList.size(); i++) {
			if (!"99".equals(raList.get(i).getStauts())) {
				sb.append(raList.get(i).getReceivplanid()).append(",");
			}
		}
		return sb.toString();
	}
}
