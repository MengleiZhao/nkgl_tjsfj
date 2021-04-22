package com.braker.icontrol.incomemanage.export.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braker.common.util.MoneyUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.model.CheterInfo;
import com.braker.core.model.Proposer;
import com.braker.icontrol.incomemanage.accountsCurrent.manager.AccountsCurrentMng;
import com.braker.icontrol.incomemanage.accountsCurrent.manager.AccountsRegisterMng;
import com.braker.icontrol.incomemanage.accountsCurrent.manager.ReceiveMoneyDetailMng;
import com.braker.icontrol.incomemanage.accountsCurrent.model.AccountsCurrent;
import com.braker.icontrol.incomemanage.accountsCurrent.model.AccountsRegister;
import com.braker.icontrol.incomemanage.accountsCurrent.model.ReceiveMoneyDetail;
import com.braker.icontrol.incomemanage.businessService.manager.BusinessServiceMng;
import com.braker.icontrol.incomemanage.businessService.model.BusinessServiceInfo;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.entity.TProcessPrintDetail;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 收入打印单控制层
 * @author 赵孟雷
 *
 */
@Controller
@RequestMapping(value = "/incomeExport")
public class IncomeExportController extends BaseController{

	@Autowired
	private AccountsCurrentMng accountsCurrentMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private BusinessServiceMng businessServiceMng;
	
	@Autowired
	private AccountsRegisterMng accountsRegisterMng;
	
	@Autowired
	private CheterMng cheterMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private ReceiveMoneyDetailMng receiveMoneyDetailMng;
	
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	/*
	 * 跳转往来款立项打印页面
	 * @author 赵孟雷
	 * @createtime 2020-12-03
	 * @updatetime 2020-12-03        
	 */
	@RequestMapping("/accounts")
	public String accounts(String id ,ModelMap model) {
		try {
			AccountsCurrent bean = accountsCurrentMng.findById(Integer.valueOf(id));
			//获取默认值
			model.addAttribute("bean", bean);
			//操作类型
			model.addAttribute("operation", "detail");
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"WLKLXSQ", bean.getDeptId(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(),  bean.getBeanCodeField(), bean.getProCode(),"1");
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WLKLXSQ", bean.getDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//根据归口部门修改流程显示数据
			model.addAttribute("nodeConf", nodeConfList);
			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			model.addAttribute("attaList", attaList);
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getUserName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);	
			//查询制度中心文件
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("事前申请");
			model.addAttribute("cheterInfo", cheterInfo);
			
			List<TProcessPrintDetail> listTProcessChecks = tProcessPrintDetailMng.findbytab("AccountsCurrent","fAcaId",bean.getfAcaId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());
			return "/WEB-INF/view/income_manage/export/accounts";
		} catch (Exception e) {
			e.printStackTrace();
			return "/WEB-INF/view/income_manage/export/accounts";
		}
	}
	
	
	/*
	 * 跳转往来款登记打印页面
	 * @author 赵孟雷
	 * @createtime 2020-12-03
	 * @updatetime 2020-12-03        
	 */
	@RequestMapping("/accountsRegister")
	public String accountsRegister(String id ,ModelMap model) {
		try {
			AccountsRegister bean = accountsRegisterMng.findById(Integer.valueOf(id));
			String registerMoneyCapital = MoneyUtil.toChinese(String.valueOf(bean.getRegisterMoney()));
			bean.setRegisterMoneyCapital(registerMoneyCapital);
			//获取默认值
			model.addAttribute("bean", bean);
			
			List<ReceiveMoneyDetail> list = new ArrayList<ReceiveMoneyDetail>();
			ReceiveMoneyDetail moneyDetail = new ReceiveMoneyDetail();
			if(id != null) {
				moneyDetail.setfType("2");
				moneyDetail.setfMSId(bean.getfMSId());
				//查询接待明细
				list = receiveMoneyDetailMng.findByList(moneyDetail, getUser());
			}
			model.addAttribute("list", list);
			
			List<TProcessPrintDetail> listTProcessChecks = tProcessPrintDetailMng.findbytab("AccountsRegister","fMSId",bean.getfMSId());
			model.addAttribute("listTProcessChecks", listTProcessChecks);
			/*//操作类型
			model.addAttribute("operation", "detail");
			//查询工作流
			List<TNodeData> nodeConfList=tProcessCheckMng.getNodeConf(bean.getUserId(),"WLKDJ", bean.getDeptId(),bean.getBeanCode(),bean.getnCode(),bean.getJoinTable(),  bean.getBeanCodeField(), bean.getRegisterCode(),"1");
			//得到工作流id
			TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("WLKDJ", bean.getDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//根据归口部门修改流程显示数据
			model.addAttribute("nodeConf", nodeConfList);
			//查询附件信息
			List<Attachment> attaList = attachmentMng.list(bean);
			model.addAttribute("attaList", attaList);
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getUserName(), bean.getDeptName(), bean.getReqTime());
			model.addAttribute("proposer", proposer);	
			//查询制度中心文件
			List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("事前申请");
			model.addAttribute("cheterInfo", cheterInfo);
			//对象编码
			model.addAttribute("foCode",bean.getBeanCode());*/
			return "/WEB-INF/view/income_manage/export/accountsRegister";
		} catch (Exception e) {
			e.printStackTrace();
			return "/WEB-INF/view/income_manage/export/accountsRegister";
		}
	}
	
	/*
	 * 跳转培训立项打印页面
	 * @author 赵孟雷
	 * @createtime 2020-12-03
	 * @updatetime 2020-12-03        
	 */
	@RequestMapping("/business")
	public String business(String id ,ModelMap model) {
		try {
			//经营性服务项目基本信息
			BusinessServiceInfo bean = businessServiceMng.findById(Integer.valueOf(id));
			model.addAttribute("bean", bean);
			//经营性服务项目附件
			List<Attachment> businessAttaList = attachmentMng.list(bean);
			model.addAttribute("businessAttaList", businessAttaList);
			//查询工作流
			List<TNodeData> nodeConfList = tProcessCheckMng.getNodeConf(bean.getfOperatorId(), "PXSRLXSQ", bean.getfDeptId(), bean.getBeanCode(), bean.getfNextCode(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "1");
			model.addAttribute("nodeConf", nodeConfList);
			//得到工作流id
			TProcessDefin tProcessDefin = tProcessDefinMng.getByBusiAndDpcode("PXSRLXSQ", bean.getfDeptId());
			model.addAttribute("fpId", tProcessDefin.getFPId());
			//对象编码
			model.addAttribute("foCode", bean.getBeanCode());
			//建立工作流发起人的信息
			Proposer proposer = new Proposer(bean.getfOperatorName(), bean.getfDeptName(), bean.getfBusiTime());
			model.addAttribute("proposer", proposer);
			return "/WEB-INF/view/income_manage/export/business";
		} catch (Exception e) {
			e.printStackTrace();
			return "/WEB-INF/view/income_manage/export/business";
		}
	}

	
}