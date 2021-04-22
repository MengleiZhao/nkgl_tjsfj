package com.braker.icontrol.expend.reimburse.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.Lock;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.MatheUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.LookupsMng;
import com.braker.core.manager.PaymentMethodInfoMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.RoleMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.Lookups;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProExpendDetail;
import com.braker.icontrol.budget.project.manager.TProExpendDetailMng;
import com.braker.icontrol.cgmanage.cgprocess.manager.CgProcessMng;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.expend.apply.manager.ApplyMng;
import com.braker.icontrol.expend.apply.manager.OfficeCarMng;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.AbroadPlan;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.ApplicationDetail;
import com.braker.icontrol.expend.apply.model.FeteCostInfo;
import com.braker.icontrol.expend.apply.model.FoodAllowanceInfo;
import com.braker.icontrol.expend.apply.model.GoldPayDetail;
import com.braker.icontrol.expend.apply.model.HotelExpenseInfo;
import com.braker.icontrol.expend.apply.model.InCityTrafficInfo;
import com.braker.icontrol.expend.apply.model.InternationalTravelingExpense;
import com.braker.icontrol.expend.apply.model.LecturerInfo;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.MeetingPlan;
import com.braker.icontrol.expend.apply.model.MiscellaneousFeeInfo;
import com.braker.icontrol.expend.apply.model.OfficeCar;
import com.braker.icontrol.expend.apply.model.OutsideTrafficInfo;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.ReceptionChargeInfo;
import com.braker.icontrol.expend.apply.model.ReceptionFood;
import com.braker.icontrol.expend.apply.model.ReceptionHotel;
import com.braker.icontrol.expend.apply.model.ReceptionOther;
import com.braker.icontrol.expend.apply.model.ReceptionPeopleInfo;
import com.braker.icontrol.expend.apply.model.ReceptionStrokPlan;
import com.braker.icontrol.expend.apply.model.TrainTeacherCost;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.expend.audit.manager.AuditInfoMng;
import com.braker.icontrol.expend.apply.model.MeetPlan;
import com.braker.icontrol.expend.cashier.manager.CashierMng;
import com.braker.icontrol.expend.loan.manager.LoanMng;
import com.braker.icontrol.expend.loan.model.LoanBasicInfo;
import com.braker.icontrol.expend.reimburse.manager.DirectlyReimbMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceCouponMng;
import com.braker.icontrol.expend.reimburse.manager.InvoiceMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAppliMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbAttacMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbDetailMng;
import com.braker.icontrol.expend.reimburse.manager.ReimbInvolMng;
import com.braker.icontrol.expend.reimburse.model.AppInvoiceInfo;
import com.braker.icontrol.expend.reimburse.model.CostDetailInfo;
import com.braker.icontrol.expend.reimburse.model.DirectlyReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.InvoiceCouponInfo;
import com.braker.icontrol.expend.reimburse.model.ReimCXInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;
import com.braker.icontrol.expend.reimburse.model.ReimbDetail;
import com.braker.icontrol.expend.reimburse.model.ReimbPayeeInfo;
import com.braker.icontrol.expend.standard.entity.HotelStandard;
import com.braker.icontrol.expend.standard.manager.HotelStandardMng;
import com.braker.icontrol.purchase.apply.manager.PurchaseApplyMng;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.workflow.manager.TProcessPrintDetailMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 申请报销的service实现类
 * @author 叶崇晖
 * @createtime 2018-08-08
 * @updatetime 2018-08-08
 */
@Service
@Transactional
public class ReimbAppliMngImpl extends BaseManagerImpl<ReimbAppliBasicInfo> implements ReimbAppliMng {
	@Autowired
	private ReimbDetailMng reimbDetailMng;
	@Autowired
	private ReimbInvolMng reimbInvolMng;
	@Autowired
	private ReimbAttacMng reimbAttacMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private AuditInfoMng auditInfoMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private PaymentMethodInfoMng paymentMethodInfoMng; // 个人收款信息
	@Autowired
	private ApplyMng applyMng;
	@Autowired
	private InvoiceMng invoiceMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private RoleMng roleMng;
	@Autowired
	private LoanMng loanMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private TProExpendDetailMng indexDetailMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	@Autowired
	private CashierMng cashierMng;
	@Autowired
	private InvoiceCouponMng invoiceCouponMng;
	@Autowired
	private HotelStandardMng hotelStandardMng;
	@Autowired
	private OfficeCarMng officeCarMng;
	@Autowired
	private TProcessPrintDetailMng tProcessPrintDetailMng;
	@Autowired
	private DirectlyReimbMng directlyReimbMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private RegisterApplyMng registerApplyMng;
	@Autowired
	private CgProcessMng biddingRegistMng;
	@Autowired
	private PurchaseApplyMng purchaseApplyMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private LookupsMng lookupsMng;
	@Autowired
	private ReceivPlanMng receivPlanMng;
	@Autowired
	private ShenTongMng shenTongMng;
	
	/*
	 * 分页查询
	 * @author 叶崇晖
	 * @createtime 2018-08-08
	 * @updatetime 2018-08-08
	 */
	@Override
	public Pagination pageList(ReimbAppliBasicInfo bean, String reimburseType,int pageNo, int pageSize, User user,String searchData) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE stauts in('1','0') AND user='"+user.getId()+"' AND type='"+reimburseType+"'");
		
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND rCode like '%"+searchData+"%' or contCode like '%"+searchData+"%' or fcTitle like '%"+searchData+"%' or DATE(reimburseReqTime) like '%"+searchData+"%'");
		}
		
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getrCode()))) {
			finder.append(" AND rCode LIKE '%"+bean.getrCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getContCode()))) {//合同编号
			finder.append(" AND contCode LIKE '%"+bean.getContCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getPayId()))) {//合同Id
			finder.append(" AND payId = '"+bean.getPayId()+"'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getFcTitle()))) {//合同名称
			finder.append(" AND fcTitle LIKE '%"+bean.getFcTitle()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime1()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') >= '"+bean.getReimburseReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime2()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') <= '"+bean.getReimburseReqTime2()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(bean.getFlowStauts())) {
			if(bean.getFlowStauts().equals("1")) {
				finder.append(" and flowStauts in('1','2','3','4','5','6','7','8')");
			} else {
				finder.append(" and flowStauts = '"+bean.getFlowStauts()+"'");
			}
		}*/
		finder.append(" order by updateTime desc ");
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 报销申请新增和修改的保存
	 * @author 叶崇晖
	 * @createtime 2018-08-10
	 * @updatetime 2018-08-10
	 */
	@Override
	public void save(ReimbAppliBasicInfo bean, ReimbPayeeInfo payeeBean,String mingxi, String fapiao, String files, User user)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		
		
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
				throw new ServiceException("该借款单正在还款审批中！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			if("1".equals(bean.getType())){
				nextUser = userMng.findById(bean.getFuserId());
			}else {
				//得到第一个审批节点key
				Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), "BXSQ", user);
				//根据资源名称和当前登陆者所属部门查询对应工作流
				TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("BXSQ", user.getDpID());
				flowId= processDefin.getFPId();
				TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
				nextUser=userMng.findById(node.getUserId());
				//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
				bean.setUserName2(nextUser.getName());
				bean.setFuserId(nextUser.getId());
				//设置下节点节点编码
				bean.setnCode(firstKey+"");	
			}
			
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			
			String taskName=applyBean.getgName();//申请名称
			if(bean.getApplyAmount()>0){
				if("1".equals(bean.getType())) {
					taskName = "[通用事项报销]" + taskName + "[超额报销]";
				} else if("2".equals(bean.getType())) {
					taskName = "[会议报销]" + taskName + "[超额报销]";
				} else if("3".equals(bean.getType())) {
					taskName = "[培训报销]" + taskName + "[超额报销]";
				} else if("4".equals(bean.getType())) {
					taskName = "[差旅报销]" + taskName + "[超额报销]";
				} else if("5".equals(bean.getType())) {
					taskName = "[公务接待报销]" + taskName + "[超额报销]";
				} else if("6".equals(bean.getType())) {
					taskName = "[公务用车报销]" + taskName + "[超额报销]";
				} else if("7".equals(bean.getType())) {
					taskName = "[公务出国报销]" + taskName + "[超额报销]";
				}
			}else{
				if("1".equals(bean.getType())) {
					taskName = "[通用事项报销]" + taskName;
				} else if("2".equals(bean.getType())) {
					taskName = "[会议报销]" + taskName;
				} else if("3".equals(bean.getType())) {
					taskName = "[培训报销]" + taskName;
				} else if("4".equals(bean.getType())) {
					taskName = "[差旅报销]" + taskName;
				} else if("5".equals(bean.getType())) {
					taskName = "[公务接待报销]" + taskName;
				} else if("6".equals(bean.getType())) {
					taskName = "[公务用车报销]" + taskName;
				} else if("7".equals(bean.getType())) {
					taskName = "[公务出国报销]" + taskName;
				}
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			
			if("1".equals(bean.getType())) {
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1&applyType=tysx");//为审批页面内容显示的url,需要将数据传入不然无法访问
				work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0&applyType=tysx");//查看url
			}else{
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
				work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			}
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			if("1".equals(bean.getType())) {
				work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1&applyType=tysx");//退回修改url
				work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0&applyType=tysx");//查看url
			}else{
				work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
				work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			}
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			//计算明细申请金额总数
			JSONArray array =JSONArray.fromObject("["+mingxi.toString()+"]");
			List<ReimbDetail> newmxs = (List<ReimbDetail>)JSONArray.toList(array, ReimbDetail.class);
			Double num = 0.0;
			Double nums = 0.0;
			for (int i = 0; i < newmxs.size(); i++) {
				ReimbDetail mx = (ReimbDetail) newmxs.get(i);
				if(mx.getApplySum() != null) {
					num += mx.getApplySum();
				}
			}
			for (int j = 0; j < newmxs.size(); j++) {
				ReimbDetail mx = (ReimbDetail) newmxs.get(j);
				if(mx.getReimbSum() != null) {
					nums += mx.getReimbSum();
				}
			}
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		//旧明细
		List<ReimbDetail> oldmx = reimbDetailMng.getMingxi(bean.getrId());
		
		//新明细
		JSONArray array =JSONArray.fromObject("["+mingxi.toString()+"]");
		List<ReimbDetail> newmx = (List<ReimbDetail>)JSONArray.toList(array, ReimbDetail.class);
		
		//比较新老明细的不同
		for (int i = oldmx.size()-1; i >= 0; i--) {
			ReimbDetail oldrd = oldmx.get(i);
			for (int j = 0; j < newmx.size(); j++) {
				ReimbDetail newrd = newmx.get(j);
				if(newrd.getcId()!=null){
					if(newrd.getcId()==oldrd.getcId()){
						oldmx.remove(i);
					}
				}
			}
		}
		
		//删除在新明细中没有的老明细
		if(oldmx.size()>0){
			for (int i = 0; i < oldmx.size(); i++) {
				ReimbDetail oldrd = oldmx.get(i);
				super.delete(oldrd);
			}
		}
		
		for (int i = 0; i < newmx.size(); i++) {
			ReimbDetail newrd = newmx.get(i);
			newrd.setrId(bean.getrId());
			newrd.setCreator(user.getAccountNo());
			newrd.setCreateTime(d);
			//保存新的明细
			super.merge(newrd);
		}
		
		
		/*//旧发票
		List<InvoiceInfo> oldfp = invoiceMng.findByRID("1", bean.getrId());
		
		Map classMap = new HashMap();
		classMap.put("couponList",InvoiceCouponInfo.class);
		
		//新发票
		JSONArray array2 =JSONArray.fromObject("["+fapiao.toString()+"]");
		
		List<InvoiceInfo> newfp = (List<InvoiceInfo>)JSONArray.toList(array2, InvoiceInfo.class, classMap);
		
		
		for (int i = oldfp.size()-1; i >= 0; i--) {
			InvoiceInfo olddrid = oldfp.get(i);
			for (int j = 0; j < newfp.size(); j++) {
				InvoiceInfo newdrid = newfp.get(j);
				if(newdrid.getiId()!=null) {
					if(newdrid.getiId().equals(olddrid.getiId())){
						oldfp.remove(i);
					}
				}
			}
		}*/
		
		/*//删除在新发票中没有的老发票
		if(oldfp.size()>0){
			for (int i = 0; i < oldfp.size(); i++) {
				InvoiceInfo olddrid = oldfp.get(i);
				//删除发票信息
				super.delete(olddrid);
			}
		}
		
		for (int i = 0; i < newfp.size(); i++) {
			InvoiceInfo newdrid = newfp.get(i);
			newdrid.setrId(bean.getrId());//所属类型单据id
			newdrid.setType("2");//所属类型（1、申请报销）
			
			newdrid.setInvoiceTime(newfp.get(i).getInvoiceTime());
			
			
			//获得发票的票面信息
			List<InvoiceCouponInfo> couponList = newdrid.getCouponList();
			
			//保存新的发票信息
			String id = newdrid.getFileId();
			newdrid=(InvoiceInfo) super.merge(newdrid);
			
			//保存发票票面信息
			for (int j = 0; j < couponList.size(); j++) {
				InvoiceCouponInfo coupon = couponList.get(j);
				
				coupon.setiId(newdrid.getiId());//给发票票面信息设置副键
				super.merge(coupon);
			}
			
			//保存发票附件信息
			attachmentMng.joinEntity(newdrid,id);
		}*/
		
		
		//保存收款人信息
		payeeBean.setrId(bean.getrId());//关联申请报销单
		payeeBean.setPayeeId(user.getId());//收款人id
		payeeBean.setPayeeName(user.getName());//搜款人姓名
		payeeBean.setPayeeAmount(bean.getAmount());//应付金额
		super.merge(payeeBean);
		
		//保存或修改个人收款信息
		paymentMethodInfoMng.saveInfo(payeeBean, user);
		//保存收款人信息
		super.merge(payeeBean);
	}

	
	public List getMingXiJson(String mingxi, Class tableClass) {
		//获取明细的Json对象
		List mx = null;
		JSONArray array =JSONArray.fromObject("["+mingxi.toString()+"]");
		mx = (List)JSONArray.toList(array, tableClass);
		return mx;
	}
	
	
	/*
	 * 审批分页查询
	 * @author 叶崇晖
	 * @createtime 2018-08-13
	 * @updatetime 2018-08-13
	 */
	@Override
	public Pagination checkPageList(ReimbAppliBasicInfo bean, int pageNo,int pageSize, User user,String searchData) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE fuserId='"+user.getId()+"' AND stauts in('1','0')");
		if (!StringUtil.isEmpty(String.valueOf(bean.getType()))) {
			finder.append(" AND type = '"+bean.getType()+"'");
		} else {
			finder.append(" AND type not in('8','9','11')");
		}
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getrCode()))) {
			finder.append(" AND rCode LIKE '%"+bean.getrCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime1()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') >= '"+bean.getReimburseReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime2()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') <= '"+bean.getReimburseReqTime2()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getDeptName()))) {
			finder.append(" AND deptName LIKE '%"+bean.getDeptName()+"%'");
		}*/
		
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (rCode like '%"+searchData+"%' or deptName like '%"+searchData+"%' or contCode like '%"+searchData+"%' or fcTitle like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(reimburseReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		
		finder.append(" order by reimburseReqTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 台账分页查询
	 * @author 叶崇晖
	 * @createtime 2018-08-14
	 * @updatetime 2018-08-14
	 */
	@Override
	public Pagination ledgerPageList(String type, String applyString,ReimbAppliBasicInfo bean, int pageNo, int pageSize, User user,String searchData) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE flowStauts='9' AND stauts='1' and type in("+type+")");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (rCode like '%"+searchData+"%' or type like '%"+searchData+"%' or deptName like '%"+searchData+"%')");
		}
 		/*if (!StringUtil.isEmpty(String.valueOf(bean.getrCode()))) {
			finder.append(" AND rCode LIKE '%"+bean.getrCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getType()))) {
			finder.append(" AND type LIKE '%"+bean.getType()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getDeptName()))) {
			finder.append(" AND deptName LIKE '%"+bean.getDeptName()+"%'");
		}*/
		String deptIdStr=departMng.getDeptIdStrByUsercn(user);
 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
 			finder.append(" and user = :user").setParam("user", user.getId());
 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
 			
 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
 			finder.append(" and dept in ("+deptIdStr+")");
 		}
		if ("0".equals(applyString)) {
			finder.append(" and YEAR(F_REQ_TIME)=YEAR(NOW())");
		}
		finder.append(" order by reimburseReqTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 财务审定分页查询
	 * @author 叶崇晖
	 * @createtime 2018-08-16
	 * @updatetime 2018-08-16
	 */
	@Override
	public Pagination auditPageList(ReimbAppliBasicInfo bean, int pageNo,int pageSize, User user) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE fuserId='"+user.getId()+"' AND stauts in('1','0')");
		finder.append(" AND fuserId='"+user.getId()+"'");
		
		if (!StringUtil.isEmpty(String.valueOf(bean.getrCode()))) {
			finder.append(" AND rCode LIKE '%"+bean.getrCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime1()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') >= '"+bean.getReimburseReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime2()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') <= '"+bean.getReimburseReqTime2()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getDeptName()))) {
			finder.append(" AND deptName LIKE '%"+bean.getDeptName()+"%'");
		}
		
		return super.find(finder, pageNo, pageSize);
	}

	/*
	 * 报销申请删除
	 * @author 叶崇晖
	 * @createtime 2018-08-17
	 * @updatetime 2018-08-17
	 */
	@Override
	public void delete(Integer id,String fid,User user) {
		//修改申请单的报销状态为0（未报销）
		if(fid!=null){
			personalWorkMng.deleteById(Integer.valueOf(fid));
		}
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=0 WHERE F_G_CODE=(SELECT F_G_CODE FROM t_reimb_appli_basic_info WHERE F_R_ID="+id+")").executeUpdate();
		//修改报销单单的状态为99（删除）
		getSession().createSQLQuery("UPDATE t_reimb_appli_basic_info SET F_STAUTS=99 WHERE F_R_ID="+id).executeUpdate();
		personalWorkMng.sendMessageToUser(user.getId(), 0);
	}

	@Override
	public void deleteEnforcingList(Integer id, User user) {
		//修改报销单单的状态为99（删除）
		getSession().createSQLQuery("UPDATE t_reimb_appli_basic_info SET F_STAUTS=99 WHERE F_R_ID="+id).executeUpdate();
		personalWorkMng.sendMessageToUser(user.getId(), 0);
	}
	
	/*
	 * 出纳受理分页查询
	 * @author 叶崇晖
	 * @createtime 2018-08-22
	 * @updatetime 2018-08-22
	 */
	@Override
	public Pagination cashierPageList(ReimbAppliBasicInfo bean, int pageNo, int pageSize, User user,String applyString) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE stauts in('1','0') AND flowStauts=9 ");
		//finder.append(" AND fuserId='"+user.getId()+"'");
		
		if (!StringUtil.isEmpty(String.valueOf(bean.getType()))) {
			finder.append(" AND type = '"+bean.getType()+"'");
		}else{
			finder.append(" AND type not in('8','9','11')");
		}
		if(!"8".equals(bean.getType()) && !"11".equals(bean.getType())){
			if (!StringUtil.isEmpty(String.valueOf(bean.getrCode()))) {
				finder.append(" AND rCode LIKE '%"+bean.getrCode()+"%'");
			}
			if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime1()))) {
				finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') >= '"+bean.getReimburseReqTime1()+"'");//日期去时分秒函数
			}
			if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime2()))) {
				finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') <= '"+bean.getReimburseReqTime2()+"'");//日期去时分秒函数
			}
			if (!StringUtil.isEmpty(String.valueOf(bean.getDeptName()))) {
				finder.append(" AND deptName LIKE '%"+bean.getDeptName()+"%'");
			}
		}else{
			if (!StringUtil.isEmpty(applyString)) {
				finder.append(" AND (rCode like '%"+applyString+"%' or contCode like '%"+applyString+"%' or fcTitle like '%"+applyString+"%' or userName like '%"+applyString+"%' or amount like '%"+applyString+"%' or TO_DATE(reimburseReqTime,'yyyy-mm-dd') like '%"+applyString+"%') ");
			}
		}
		finder.append(" order by cashierType nulls first,reimburseReqTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	/**
	 * 查询需要导出的全部台账信息
	 * @author 叶崇晖
	 * @return
	 */
	@Override
	public List<ReimbAppliBasicInfo> ledgerList(String applyString,String userId,String rimeType) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE flowStauts='9' AND stauts='1'");
		String deptIdStr=departMng.getDeptIdStrByUser(userMng.findById(userId));
 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
 			finder.append(" and user = :user").setParam("user", userId);
 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
 			
 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
 			finder.append(" and dept in ("+deptIdStr+")");
 		}
		if ("0".equals(applyString)) {
			finder.append(" and YEAR(F_REQ_TIME)=YEAR(NOW())");
		}
		if(StringUtil.isEmpty(rimeType)){
			finder.append(" and type not in ('8','9','11')");
		}else{
			finder.append(" and type='"+rimeType+"'");
		}
		return super.find(finder);
	}

	/**
	 * 生成需要导出的台账HSSFWorkbook
	 * @author 叶崇晖
	 * @param drLedgerData
	 * @param rLedgerData
	 * @param filePath
	 * @return
	 */
	@Override
	public HSSFWorkbook exportLedger(List<DirectlyReimbAppliBasicInfo> drLedgerData,List<ReimbAppliBasicInfo> rLedgerData, String filePath) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet1 = wb.getSheetAt(0);
			
			sheet1.getRow(1).createCell(1).setCellValue(df1.format(new Date()));//设置导出时间
			
			if(drLedgerData.size()>0&&drLedgerData!=null){
				HSSFRow row = sheet1.getRow(3);//格式行
				for (int i = 0; i < drLedgerData.size(); i++) {
					HSSFRow hssfRow = sheet1.createRow(3+i);
					if(row != null) {
						hssfRow.setHeight(row.getHeight());
					}
					//序号
					hssfRow.createCell(0).setCellValue(i +1);
					//报销单编号
					hssfRow.createCell(1).setCellValue(drLedgerData.get(i).getDrCode());
					//付款状态
					String cash = "";
					switch(drLedgerData.get(i).getCashierType()){
						case "0" :cash = "未付讫";break;
						case "1" :cash = "已付讫";break;
					}
					hssfRow.createCell(2).setCellValue("直接报销");
					//报销金额
					hssfRow.createCell(3).setCellValue(drLedgerData.get(i).getAmount()!=null?drLedgerData.get(i).getAmount():0.0);
					//所属部门
					hssfRow.createCell(4).setCellValue(drLedgerData.get(i).getDeptName());
					//报销人
					User user = userMng.findById(drLedgerData.get(i).getUser());
					hssfRow.createCell(5).setCellValue(user!=null?user.getName():"");
					//报销时间
					if(drLedgerData.get(i).getReqTime() != null) {
						hssfRow.createCell(6).setCellValue(df.format(drLedgerData.get(i).getReqTime()));
					}
					//报销事由
					//hssfRow.createCell(7).setCellValue(drLedgerData.get(i).getReason());
					hssfRow.createCell(7).setCellValue(cash);
				}
				}
				if(rLedgerData.size()>0&&rLedgerData!=null){
					HSSFRow row = sheet1.getRow(3);//格式行
					for (int i = 0; i < rLedgerData.size(); i++) {
						HSSFRow hssfRow = sheet1.createRow(3+i);
						if(row != null) {
							hssfRow.setHeight(row.getHeight());
						}
						//序号
						hssfRow.createCell(0).setCellValue(i +1);
						//报销单编号
						hssfRow.createCell(1).setCellValue(rLedgerData.get(i).getrCode());
						//付款状态
						String cash = "";
						switch(rLedgerData.get(i).getCashierType()){
							case "0" :cash = "未付讫";break;
							case "1" :cash = "已付讫";break;
						}
						if("8".equals(rLedgerData.get(i).getType()) || "11".equals(rLedgerData.get(i).getType())){//合同报销单    合同保证金报销单
							hssfRow.createCell(2).setCellValue(rLedgerData.get(i).getContCode());
							hssfRow.createCell(3).setCellValue(rLedgerData.get(i).getFcTitle());
							hssfRow.createCell(4).setCellValue(rLedgerData.get(i).getAmount());
							hssfRow.createCell(5).setCellValue(rLedgerData.get(i).getUserName());
							hssfRow.createCell(6).setCellValue(cash);
						}else if("9".equals(rLedgerData.get(i).getType())){//往来款报销单
							hssfRow.createCell(2).setCellValue(rLedgerData.get(i).getgName());
							hssfRow.createCell(3).setCellValue(rLedgerData.get(i).getAmount());
							hssfRow.createCell(4).setCellValue(rLedgerData.get(i).getUserName());
							if(rLedgerData.get(i).getReimburseReqTime() != null) {
								hssfRow.createCell(5).setCellValue(df.format(rLedgerData.get(i).getReimburseReqTime()));
							}
							hssfRow.createCell(6).setCellValue(cash);
						}else{//事情申请报销单
							//报销类型
							String type="";
							switch (rLedgerData.get(i).getType()) {
							case "1":type = "通用事项报销";break;
							case "2":type = "会议报销";break;
							case "3":type = "培训报销";break;
							case "4":type = "差旅报销";break;
							case "5":type = "公务接待报销";break;
							case "6":type = "公务用车报销";break;
							case "7":type = "公务出国报销";break;
							case "8":type = "合同报销";break;
							case "9":type = "往来款报销";break;
							case "11":type = "合同保证金报销";break;
							case "":type = "直接报销";break;
							}
							hssfRow.createCell(2).setCellValue(type);
							//报销金额
							hssfRow.createCell(3).setCellValue(rLedgerData.get(i).getAmount()!=null?rLedgerData.get(i).getAmount():0.0);
		//					//预算指标
		//					hssfRow.createCell(4).setCellValue(rLedgerData.get(i).getIndexName());
							//所属部门
							hssfRow.createCell(4).setCellValue(rLedgerData.get(i).getDeptName());
							//报销人
							User user = userMng.findById(rLedgerData.get(i).getUser());
							hssfRow.createCell(5).setCellValue(user!=null?user.getName():"");
							//报销时间
							if(rLedgerData.get(i).getReimburseReqTime() != null) {
								hssfRow.createCell(6).setCellValue(df.format(rLedgerData.get(i).getReimburseReqTime()));
							}
							//报销事由
							//hssfRow.createCell(7).setCellValue(rLedgerData.get(i).getReimburseReason());
							hssfRow.createCell(7).setCellValue(cash);
						}
					}
			}
				return wb;	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 各类型会议支出列表
	 * @author 焦广兴
	 * @param  indexType 类型
	 * @createtime 2019-03-？
	 * @updatetime 2019-03-27
	 */
	@Override
	public Pagination getData21(Depart depart,String indexType, String year, int pageNo,int pageSize,
			String searchName,String searchContent) {
		StringBuilder sb=new StringBuilder("SELECT A.*");
		sb.append(" FROM T_REIMB_APPLI_BASIC_INFO A INNER JOIN T_MEETING_APPLI_INFO B ON");
		sb.append(" (SELECT F_G_ID FROM t_application_basic_info WHERE A.F_G_CODE=F_G_CODE)");
		sb.append(" =B.F_G_ID WHERE A.F_CASHIER_TYPE='1'");
		sb.append(" AND A.F_G_CODE IN");
		sb.append(" (SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='2' ");
		sb.append(" AND YEAR(F_REQ_TIME) ='"+year+"')");
		sb.append(" AND B.F_MEETING_TYPE='"+indexType+"'");
		if (depart != null) {
			String departType = depart.getType();
			if (Depart.TYPE_COM.equals(departType)) {
				//公司登录，获得所有子部门的
				sb.append(" AND F_DEPT IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "')");
			} else if (Depart.TYPE_DEPT.equals(departType)) {
				//部门登录，获取所属公司下所有子部门的
				sb.append(" AND F_DEPT IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getParent().getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "')");
			}
		}
		//按会议名称模糊搜索
		if(!StringUtil.isEmpty(searchName)){
			sb.append(" AND F_MEETING_NAME LIKE('%"+searchName+"%')");
		}
		//按会议内容模糊搜索
		if(!StringUtil.isEmpty(searchContent)){
			sb.append(" AND F_MEETING_CONTENT LIKE('%"+searchContent+"%')");
		}
		 
		sb.append(" ORDER BY F_MEETING_DATE_START DESC");
		 
		String str=sb.toString();
		
		Pagination p = super.findBySql(new ReimbAppliBasicInfo(), str, pageNo, pageSize);
		List<ReimbAppliBasicInfo> dataList = (List<ReimbAppliBasicInfo>) p.getList();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (ReimbAppliBasicInfo log: dataList) {
				log.setNum(pageNo * pageSize + i - (pageSize-1));
				i++;
			}
		}
		return p;
	}
	
	/*
	 * 会议部门列表信息
	 * @author 焦广兴
	 * @createtime 2019-03-21
	 */
	@Override
	public Pagination getData22(Depart depart, String year, int pageNo,int pageSize,
			String searchName,String searchContent) {
		StringBuilder sb=new StringBuilder("SELECT A.*");
		sb.append(" FROM T_REIMB_APPLI_BASIC_INFO A INNER JOIN T_MEETING_APPLI_INFO B ON");
		sb.append(" (SELECT F_G_ID FROM t_application_basic_info WHERE A.F_G_CODE=F_G_CODE)");
		sb.append(" =B.F_G_ID WHERE A.F_CASHIER_TYPE='1'");
		sb.append(" AND A.F_G_CODE IN");
		sb.append(" (SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='2' ");
		sb.append(" AND YEAR(F_REQ_TIME) ='"+year+"')");
		sb.append(" AND A.F_DEPT_NAME='"+depart.getName()+"'");
		//按会议名称模糊搜索
		if(!StringUtil.isEmpty(searchName)){
			sb.append(" AND F_MEETING_NAME LIKE('%"+searchName+"%')");
		}
		//按会议内容模糊搜索
		if(!StringUtil.isEmpty(searchContent)){
			sb.append(" AND F_MEETING_CONTENT LIKE('%"+searchContent+"%')");
		}
		 
		sb.append(" ORDER BY F_MEETING_DATE_START DESC");
		 
		String str=sb.toString();
		
		Pagination p = super.findBySql(new ReimbAppliBasicInfo(), str, pageNo, pageSize);
		List<ReimbAppliBasicInfo> dataList = (List<ReimbAppliBasicInfo>) p.getList();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (ReimbAppliBasicInfo log: dataList) {
				log.setNum(pageNo * pageSize + i - (pageSize-1));
				i++;
			}
		}
		return p;
	}

	/**
	 * 各类型培训支出列表
	 * @author 焦广兴
	 * @param  indexType 类型
	 * @param  searchName 搜索条件 （培训名字）
	 * @param  searchContent （培训内容）
	 * @createtime 2019-03-？
	 * @updatetime 2019-04-2
	 */
	@Override
	public Pagination getData23(Depart depart,String indexType, String year, int pageNo,int pageSize, 
			String searchName,String searchContent) {
		StringBuilder sb=new StringBuilder("SELECT A.*");
		sb.append(" FROM T_REIMB_APPLI_BASIC_INFO A INNER JOIN T_TRAINING_APPLI_INFO B ON");
		sb.append(" (SELECT F_G_ID FROM t_application_basic_info WHERE A.F_G_CODE=F_G_CODE)");
		sb.append(" =B.F_G_ID WHERE A.F_CASHIER_TYPE='1'");
		sb.append(" AND A.F_G_CODE IN");
		sb.append(" (SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='3' ");
		sb.append(" AND YEAR(F_REQ_TIME) ='"+year+"')");
		sb.append(" AND F_TRAINING_TYPE='"+indexType+"'");
		if (depart != null) {
			String departType = depart.getType();
			if (Depart.TYPE_COM.equals(departType)) {
				//公司登录，获得所有子部门的
				sb.append(" AND F_DEPT IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "')");
			} else if (Depart.TYPE_DEPT.equals(departType)) {
				//部门登录，获取所属公司下所有子部门的
				sb.append(" AND F_DEPT IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getParent().getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "')");
			}
		}
		//按培训名称模糊搜索
		if(!StringUtil.isEmpty(searchName)){
			sb.append(" AND F_TRAINING_NAME LIKE('%"+searchName+"%')");
		}
		//按培训内容模糊搜索
		if(!StringUtil.isEmpty(searchContent)){
			sb.append(" AND F_TRAINING_CONTENT LIKE('%"+searchContent+"%')");
		}
		sb.append(" ORDER BY F_TRAINING_DATE_START DESC");
		 
		String str=sb.toString();
		Pagination p = super.findBySql(new ReimbAppliBasicInfo(), str, pageNo, pageSize);
		List<ReimbAppliBasicInfo> dataList = (List<ReimbAppliBasicInfo>) p.getList();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (ReimbAppliBasicInfo log: dataList) {
				log.setNum(pageNo * pageSize + i - (pageSize-1));
				i++;
			}
		}
		return p;
	}
	
	/*
	 * 培训部门列表信息
	 * @author 焦广兴
	 * @createtime 2019-03-21
	 */
	@Override
	public Pagination getData24(Depart depart,String year, int pageNo,int pageSize,String searchName,String searchContent) {
		try{
			StringBuilder sbf=new StringBuilder("SELECT A.*");
			sbf.append(" FROM T_REIMB_APPLI_BASIC_INFO A INNER JOIN T_TRAINING_APPLI_INFO B ON");
			sbf.append(" (SELECT F_G_ID FROM t_application_basic_info WHERE A.F_G_CODE=F_G_CODE)");
			sbf.append(" =B.F_G_ID WHERE A.F_CASHIER_TYPE='1'");
			sbf.append(" AND A.F_G_CODE IN");
			sbf.append(" (SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='3' ");
			sbf.append(" AND YEAR(F_REQ_TIME) ='"+year+"')");
			sbf.append(" AND F_DEPT_NAME='"+depart.getName()+"'");
		sbf.append(" AND year(a.F_REQ_TIME)='"+year+"'");
		//按培训名称模糊搜索
		if(!StringUtil.isEmpty(searchName)){
			sbf.append(" AND a.F_G_NAME LIKE('%"+searchName+"%')");
		}
		//按培训内容模糊搜索
		if(!StringUtil.isEmpty(searchContent)){
			sbf.append(" AND a.F_REASON LIKE('%"+searchContent+"%')");
		}
		sbf.append(" order by a.F_REQ_TIME desc");
		String str=sbf.toString();
		Pagination p = super.findBySql(new ReimbAppliBasicInfo(), str, pageNo, pageSize);
		List<ReimbAppliBasicInfo> dataList = (List<ReimbAppliBasicInfo>) p.getList();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (ReimbAppliBasicInfo log: dataList) {
				log.setNum(pageNo * pageSize + i - (pageSize-1));
				i++;
			}
		}
		return p;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 部门经费使用情况列表
	 * @author 焦广兴
	 * @createtime 2019-03-22
	 * @updatetime 2019-04-02
	 */
	@Override
	public Pagination getData26(Depart depart,String itemName,String year, String dates, String datee, int pageNo, int pageSize,
			String searchCode,String searchName,User user) {
		try {
			StringBuilder sb=new StringBuilder("select a.* from"
					+ " t_reimb_appli_basic_info a where a.F_INDEX_ID in (select b.F_INDEX_ID from t_application_basic_info b where");
			sb.append(" b.F_APP_TYPE='"+itemName+"')");
			sb.append(" and a.F_DEPT_NAME = '"+depart.getName()+"'"); 
			sb.append(" and a.F_STAUTS=1 and a.F_AMOUNT>0");
			sb.append(" and F_REIMB_TYPE='"+itemName+"' and F_CASHIER_TYPE = '1'");
			//年度、起始结束时间查询条件
			if(!"undefined".equals(datee) && !"undefined".equals(dates)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					if (!StringUtil.isEmpty(dates)) {
						Date date = sdf.parse(dates);
						date.setHours(0);
						date.setMinutes(0);
						sb.append(" AND F_REQ_TIME>='"+sdf.format(date) + "'");
					}
					if (!StringUtil.isEmpty(datee)) {
						Date date = sdf.parse(datee);
						date.setHours(23);
						date.setMinutes(59);
						sb.append(" AND F_REQ_TIME<='"+sdf.format(date) + "'");
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}else{
				sb.append(" AND year(F_REQ_TIME)='"+year + "'");
			}
			
			//权限
			if("天津市滨海财政局".equals(depart.getText())){
				
			}else if("局领导".equals(depart.getText())){
				
			}else{
				sb.append(" and a.F_DEPT = '"+depart.getId()+"'");
			}
			//权限控制
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			//公司登录，获得所有子部门的
			if("all".equals(deptIdStr)){
				
			}else{
				sb.append(" and a.F_DEPT in ("+deptIdStr+")");
			}
			//按报销单编号查询
			if(!StringUtil.isEmpty(searchCode)){
				sb.append(" AND a.F_R_CODE LIKE('%"+searchCode+"%')");
			}
			//按指标名字查询
			if(!StringUtil.isEmpty(searchName)){
				sb.append(" AND a.F_INDEX_NAME LIKE('%"+searchName+"%')");
			}
			sb.append(" order by a.f_req_time desc");
			String str=sb.toString();
			
			Pagination p = super.findBySql(new ReimbAppliBasicInfo(), str, pageNo, pageSize);
			List<ReimbAppliBasicInfo> dataList = (List<ReimbAppliBasicInfo>) p.getList();
			if (dataList != null && dataList.size() > 0) {
				int i = 0;
				for (ReimbAppliBasicInfo log: dataList) {
					log.setNum(pageNo * pageSize + i - (pageSize-1));
					i++;
				}
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 月份经费使用情况列表
	 * @author 焦广兴
	 * @createtime 2019-03-22
	 * @updatetime 2019-03-22
	 */
	@Override
	public Pagination getData27(Depart depart, String itemName, String year, int pageNo, int pageSize,String searchCode,String searchName,User user) {
		StringBuilder sb=new StringBuilder("select a.* from"
				+ " t_reimb_appli_basic_info a where a.F_INDEX_ID in (select b.F_INDEX_ID from t_application_basic_info b where");
		sb.append(" b.F_APP_TYPE='"+itemName+"')");
		//年月来查
		sb.append(" and DATE_FORMAT(a.f_req_time,'%Y-%m')='"+year+"' ");		
		sb.append(" and a.F_STAUTS=1 and a.F_AMOUNT>0");
		sb.append("  and a.F_REIMB_TYPE='"+itemName+"' and a.F_CASHIER_TYPE = '1'");
		//权限
		if("天津市滨海财政局".equals(depart.getText())){
			
		}else if("局领导".equals(depart.getText())){
			
		}else{
			sb.append(" and a.F_DEPT = '"+depart.getId()+"'");
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
			sb.append(" and a.F_DEPT in ("+deptIdStr+")");
		}
		//按报销单编号查询
		if(!StringUtil.isEmpty(searchCode)){
			sb.append(" AND a.F_R_CODE LIKE('%"+searchCode+"%')");
		}
		//按指标名字查询
		if(!StringUtil.isEmpty(searchName)){
			sb.append(" AND a.F_INDEX_NAME LIKE('%"+searchName+"%')");
		}
		sb.append(" order by a.f_req_time desc");
		String str=sb.toString();
		
		Pagination p = super.findBySql(new ReimbAppliBasicInfo(), str, pageNo, pageSize);
		List<ReimbAppliBasicInfo> dataList = (List<ReimbAppliBasicInfo>) p.getList();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (ReimbAppliBasicInfo log: dataList) {
				log.setNum(pageNo * pageSize + i - (pageSize-1));
				i++;
			}
		}
		return p;
	}

	@Override
	public void saveCheckInfo(TProcessCheck checkBean,ReimbAppliBasicInfo bean, User user,String files) throws Exception {
		bean=this.findById(bean.getrId());
		CheckEntity entity=(CheckEntity)bean;
		String url=null;;
		String url1=null;;
		if(!"8".equals(bean.getType()) && !"11".equals(bean.getType())){
			url="/reimburseCheck/check?reimburseType=1&id=";
			url1 ="/reimburse/edit?editType=0&id=";
		}else if("11".equals(bean.getType())){
			url="/reimburseCheck/check?reimburseType=11&id=";
			url1 ="/reimburse/edit?editType=0&id=";
		}else{
			url="/reimburseCheck/check?reimburseType=2&id=";
			url1 ="/Enforcing/detail?id=";
		}
		//String url2 ="/audit/auditReimburse?reimburseType=1&cashier=1&id=";
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
		if("GWCX".equals(bean.getTravelType())){
			strType = "GWCXBX";
		}
		if("1".equals(bean.getType())){
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
		}
		//保存支出审批信息
		bean=(ReimbAppliBasicInfo)tProcessCheckMng.checkProcess(checkBean, entity, user, strType, url, url1,files);
		//如果选择冲销借款  出纳点击付讫以后，报销的原有逻辑不变，新增判断，判断是否有冲销借款，如果有，则执行如下逻辑
		if("9".equals(bean.getCheckStauts())){//审批通过情况下
//			if ("1".equals(String.valueOf(bean.getWithLoan()))){
//				cashierMng.withLoan(bean.getLoan(),bean.getAmount());
//			}
			bean.setCashierType("0");
			if(!"WLKBX".equals(strType) && !"BZJBX".equals(strType) && !"HTFKSQ".equals(strType)){
				//查询报销单，获取事前申请的金额， 如果报销金额小于事前申请金额，多出来的冻结金额要归还到可用金额
				ApplicationBasicInfo basicInfo=applyMng.findByCode(bean.getgCode());
				
				if(bean.getAmount()>=basicInfo.getAmount()){
					//审批全部通过,修改冻结金额
					cashierMng.deleteDjAmount(bean.getIndexId(),bean.getProDetailId(),bean.getAmount(),bean.getAmount(),bean.getDept(),bean.getUser(),url,bean.getrCode(),"4");
				}else{
					//审批全部通过,修改冻结金额
					cashierMng.deleteDjAmount(bean.getIndexId(),bean.getProDetailId(),bean.getAmount(),basicInfo.getAmount(),bean.getDept(),bean.getUser(),url,bean.getrCode(),"4");
				}
				
			}
			//LoanBasicInfo loan = bean.getLoan();//借款单
			//super.merge(basicInfo);
			//生成审签信息
			tProcessPrintDetailMng.arrangeCheckDetail(null, bean, "1", user);
		}
		super.saveOrUpdate(bean);
	}
	@Override
	public void saveCheckInfoTYSXBX(TProcessCheck checkBean,ReimbAppliBasicInfo bean, User user,String files) throws Exception {
		ReimbAppliBasicInfo oldbean = this.findById(bean.getrId());
		oldbean.setUserName2(bean.getUserName2());
		oldbean.setFuserId(bean.getFuserId());
		CheckEntity entity=(CheckEntity)oldbean;
		String url="/reimburseCheck/check?reimburseType=1&id=";
		String url1 ="/reimburse/edit?editType=0&id=";
		//String url2 ="/audit/auditReimburse?reimburseType=1&cashier=1&id=";
		//保存支出审批信息
		oldbean=(ReimbAppliBasicInfo)tProcessCheckMng.checkProcessTYSXBX(checkBean, entity, user, "BXSQ", url, url1,files,oldbean);
		super.updateDefault(oldbean);
		/*if("9".equals(oldbean.getCheckStauts())){//审批通过情况下
			//查询报销单，获取事前申请的金额， 如果报销金额小于事前申请金额，多出来的冻结金额要归还到可用金额
			//如果选择冲销借款  ，报销的原有逻辑不变，新增判断，判断是否有冲销借款，如果有，则执行如下逻辑
			if ("1".equals(String.valueOf(oldbean.getWithLoan()))){
				LoanBasicInfo loan = oldbean.getLoan();
				cashierMng.withLoan(loan,oldbean.getAmount());
				//修改指标冻结金额
				Double num = oldbean.getAmount();//报销金额
				budgetIndexMgrMng.deleteDjAmount(loan.getIndexId(),loan.getProDetailId(),num);
			}
			ApplicationBasicInfo basicInfo=applyMng.findByCode(oldbean.getgCode());
			basicInfo.setCashierType("0");
			if(oldbean.getAmount()>=basicInfo.getAmount()){
				//审批全部通过,修改冻结金额
				cashierMng.deleteDjAmount(oldbean.getIndexId(),oldbean.getProDetailId(),oldbean.getAmount(),oldbean.getAmount(),oldbean.getDept(),oldbean.getUser(),url,oldbean.getrCode(),"4");
			}else{
				//审批全部通过,修改冻结金额
				cashierMng.deleteDjAmount(oldbean.getIndexId(),oldbean.getProDetailId(),oldbean.getAmount(),basicInfo.getAmount(),oldbean.getDept(),oldbean.getUser(),url,oldbean.getrCode(),"4");
				
			}
			basicInfo = (ApplicationBasicInfo) super.merge(basicInfo);
		}*/
	}

	@Override
	public String reimburseReCall(Integer id) throws Exception {
		//根据id查询对象
		ReimbAppliBasicInfo bean=(ReimbAppliBasicInfo)super.findById(id);
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId() , bean.getBeanCode(), "0");
		//给待审批人发送消息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title="申请被撤回消息";
		String msg="您待审批的  "+bean.getReimburseReason() +",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注.";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//归还冻结金额
		if(!"8".equals(bean.getType()) && !"11".equals(bean.getType())&& !"9".equals(bean.getType())){
			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			if(bean.getAmount()-applyBean.getAmount()>0){
				synchronized (this) {
					budgetIndexMgrMng.deleteDjAmount(bean.getIndexId(),bean.getProDetailId(),bean.getAmount()-applyBean.getAmount());
				}
			}
		}
		if("11".equals(bean.getType())){
			//撤回时  设置质保金是否退还    0:未退还     1:已退还       2:退还中
			if(bean.getContCode().contains("HT")){
				ContractBasicInfo basicInfo = formulationMng.findById(Integer.valueOf(bean.getPayId()));
				basicInfo.setfPayStauts("0");
				formulationMng.saveOrUpdate(basicInfo);
			}else{
				Upt upt = uptMng.findById(Integer.valueOf(bean.getPayId()));
				upt.setfPayStauts("0");
				uptMng.saveOrUpdate(upt);
			}
			
		}
		//撤回
		bean=(ReimbAppliBasicInfo) reCall((CheckEntity) bean);
		this.updateDefault(bean);
		return "操作成功";
	}

	@Override
	public List<ReimbAppliBasicInfo> findByLoanId(String id) {
		Finder finder = Finder.create(" FROM  ReimbAppliBasicInfo WHERE loan.lId="+Integer.valueOf(id)+" and stauts!='99' ");
		List<ReimbAppliBasicInfo> list = super.find(finder);
		return list;
	}
	
	@Override
	public List<ReimbAppliBasicInfo> findByApplyCode(String code){
		Finder finder = Finder.create(" FROM  ReimbAppliBasicInfo WHERE gCode='"+code+"' and stauts!='99'");
		List<ReimbAppliBasicInfo> list = super.find(finder);
		return list;
	}
	/*
	 * 会议报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-24
	 * @updatetime 2020-02-25
	 */
	@Override
	public void saveMeeting(ReimbAppliBasicInfo bean, MeetingAppliInfo meetingBean,
			String form1, String payerinfoJson, String files, User user,String meetPlan,String payerinfoJsonExt,String mingxi,String bxtzfiles,String bxqdbfiles)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		Boolean isFirst;
		if (bean.getrId()==null) {
			//获取序列
			int seq = shenTongMng.getSeq("reimb_appli_basic_info_seq");
			bean.setrId(seq);
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			isFirst=true; 
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			isFirst=false; 
		}
		

		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
				throw new ServiceException("该借款单正在还款审批中！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//String ywlx =getType(applyBean.getType());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			//获取t_personal_worktable表序列
			int seq = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
					taskName = "[会议报销]" + taskName + "[超额报销]";
			}else{
					taskName = "[会议报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			//获取t_personal_worktable表序列
			int seq2 = shenTongMng.getSeq("personal_worktable_seq");
			work2.setfId(seq2);
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getgCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			//计算明细申请金额总数
			synchronized (this) {
				Boolean b=budgetIndexMgrMng.checkAmount(bean);
				if(b){
					Double num = bean.getApplyAmount();
					Double nums =bean.getAmount();
					if(nums>num){
						budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
					}
				}
			}
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		attachmentMng.joinEntity(bean,bxtzfiles);
		attachmentMng.joinEntity(bean,bxqdbfiles);
		//保存修改后会议内容
		if(meetingBean != null) {
			if (meetingBean.getmId()==null) {
				//获取序列
				int seq = shenTongMng.getSeq("t_meeting_appli_info_seq");
				meetingBean.setmId(seq);
				//创建人、创建时间、发布时间
				meetingBean.setCreator(user.getAccountNo());
				meetingBean.setCreateTime(d);
			} else {
				//修改人、修改时间
				meetingBean.setUpdator(user.getAccountNo());
				meetingBean.setUpdateTime(d);
			}
			meetingBean.setrId(bean.getrId());
			meetingBean = (MeetingAppliInfo) super.saveOrUpdate(meetingBean);
		}
		/**保存调整后会议日程*/
		/** 保存明细信息 **/
		//删除数据库中   申请中的会议日程信息
		getSession().createSQLQuery("delete from T_MEETING_PLAN where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获得新的会议日程信息
		if(!StringUtil.isEmpty(meetPlan)){
			//获取会议日程信息的Json对象
			List<MeetingPlan> meet = JSON.parseObject("["+meetPlan.toString().substring(0, meetPlan.length()-1)+"]",new TypeReference<List<MeetingPlan>>(){});
			for (MeetingPlan meetingPlan : meet) {
				MeetingPlan info = new MeetingPlan();
				info.setPlanId(shenTongMng.getSeq("t_meeting_plan_seq"));
				info.setrId(bean.getrId());
				info.setTimes(meetingPlan.getTimes());
				info.setTimee(meetingPlan.getTimee());
				info.setContent(meetingPlan.getContent());
				super.merge(info);
			}
		}
		//删除数据库中   申请中的费用明细信息
		getSession().createSQLQuery("delete from T_APPLI_DETAIL where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获得新的费用明细信息
		if(!StringUtil.isEmpty(mingxi)){
			//获取费用明细信息的Json对象
			List<ApplicationDetail> app = JSON.parseObject("["+mingxi.toString().substring(0, mingxi.length()-1)+"]",new TypeReference<List<ApplicationDetail>>(){});
			for (ApplicationDetail applicationDetail : app) {
				ApplicationDetail info = new ApplicationDetail();
				//获取序列
				int cId = shenTongMng.getSeq("appli_detail_seq");
				info.setcId(cId);
				info.setrId(bean.getrId());
				info.setCostDetail(applicationDetail.getCostDetail());
				info.setStandard(applicationDetail.getStandard());
				info.setTotalStandard(applicationDetail.getTotalStandard());
				info.setRemibAmount(applicationDetail.getRemibAmount()); 
				info.setfStatus(1);
				super.merge(info);
			}
		}
		

		
		/**保存发票**/
		//旧发票
		List<InvoiceCouponInfo> oldfp1 = findrId(bean.getrId());
		//新发票
		List<InvoiceCouponInfo> newfp1 =  JSON.parseObject(form1, new TypeReference<List<InvoiceCouponInfo>>(){});
		//旧发票
		//删除旧发票
		for (int h = 0; h < oldfp1.size(); h++) {
			if(!StringUtil.isEmpty(oldfp1.get(h).getFileId())&&!"null".equals(oldfp1.get(h).getFileId())){
				Attachment attachment=attachmentMng.findById(oldfp1.get(h).getFileId());
				if(attachment!=null){
					attachment.setFlag("0");
					attachment.setUpdator(user);
					attachment.setUpdateTime(new Date());
					attachmentMng.updateDefault(attachment);
				}
			}
			invoiceCouponMng.delete(oldfp1.get(h));
		}
			//新增发票
			for (int i = 0; i < newfp1.size(); i++) {
				InvoiceCouponInfo newrd = newfp1.get(i);
				if (newrd.getIcId() == null) {
					//获取序列
					int seq = shenTongMng.getSeq("invoice_coupon_info");
					newrd.setIcId(seq);
				}
				newrd.setrId(bean.getrId());
				newrd.setfDataType("meeting");
				//保存新的明细
				super.merge(newrd);
			}
			//保存内部收款人信息
			getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+" and F_INNER_OR_OUTER='0'").executeUpdate();
			//收款人信息的Json对象
			if(!StringUtil.isEmpty(payerinfoJson)){
					List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
					for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
						ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
						//获取序列
						int seq = shenTongMng.getSeq("reimb_payee_info_seq");
						payeeBean.setpId(seq);
						payeeBean.setrId(bean.getrId());//关联申请报销单
						payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
						payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
						payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
						payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
						payeeBean.setfInnerOrOuter("0");
						//保存或修改个人收款信息
						paymentMethodInfoMng.saveInfo(payeeBean, user);
						//保存收款人信息
						super.merge(payeeBean);
					}	
			}
			//保存外部收款人信息
			getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+" and F_INNER_OR_OUTER='1'").executeUpdate();
			//收款人信息的Json对象
			if(!StringUtil.isEmpty(payerinfoJsonExt)){
				List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJsonExt.toString().substring(0, payerinfoJsonExt.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
				for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
					ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
					payeeBean.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
					payeeBean.setrId(bean.getrId());//关联申请报销单
					payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
					payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
					payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
					payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
					payeeBean.setfInnerOrOuter("1");
					//保存或修改个人收款信息
					//paymentMethodInfoMng.saveInfo(payeeBean, user);
					//保存收款人信息
					super.merge(payeeBean);
				}	
			}
	}
	
	@Override
	public List<InvoiceCouponInfo> findfp(Integer cId) {
		Finder finder = Finder.create(" FROM InvoiceCouponInfo WHERE cId="+cId);
		return super.find(finder);
	}
	
	@Override
	public List<CostDetailInfo> findByRId(Integer rId) {
		Finder finder = Finder.create(" FROM CostDetailInfo WHERE rId="+rId);
		return super.find(finder);
	}
	
	@Override
	public List<CostDetailInfo> findByDrId(Integer dRId) {
		Finder finder = Finder.create(" FROM CostDetailInfo WHERE dRId="+dRId);
		return super.find(finder);
	}
	
	
	/*
	 * 会议报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-02-24
	 * @updatetime 2020-02-25
	 */
	@Override
	public void saveTravel(ReimbAppliBasicInfo bean, ReimbPayeeInfo payeeBean,
			String travelPeop,
			String outsideTraffic,						//城市间交通费Json
			String inCity,						//市内交通费Json
			String hotelJson,						//住宿费Json
			String foodJson,						//伙食费Json
			String payerinfoJson,
			String form1, String form2, String form3, String form4, String form5, String files, User user,String payerinfoJsonExt)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		if (bean.getrId()==null) {
			int seq = shenTongMng.getSeq("reimb_appli_basic_info_seq");
			bean.setrId(seq);
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		

		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
				throw new ServiceException("该借款单正在还款审批中！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		bean.setTravelType(applyBean.getTravelType());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			
//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
					taskName = "[差旅报销]" + taskName + "[超额报销]";
			}else{
					taskName = "[差旅报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
		
		//保存附件信息
		//attachmentMng.joinEntity(bean,files);
		
		/** 保存申请扩展信息 **/
		
		//保存差旅信息
		//删除数据库中   报销中的出差行程单
		getSession().createSQLQuery("delete from T_TRAVEL_APPLI_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(travelPeop)){
			//获取出差行程单的Json对象
			List<TravelAppliInfo> rp = JSON.parseObject("["+travelPeop.toString().substring(0, travelPeop.length()-1)+"]",new TypeReference<List<TravelAppliInfo>>(){});
			for (TravelAppliInfo travelAppliInfo : rp) {
				TravelAppliInfo info = new TravelAppliInfo();
				info.setTrId(shenTongMng.getSeq("T_TRAVEL_APPLI_INFO_SEQ"));
				info.setrId(bean.getrId());
				info.setTravelDateStart(travelAppliInfo.getTravelDateStart());
				info.setTravelDateEnd(travelAppliInfo.getTravelDateEnd());
				if("GWCC".equals(bean.getTravelType())){
					info.setTravelArea(String.valueOf(travelAppliInfo.getTravelAreaId()));
				}
				info.setTravelAreaName(travelAppliInfo.getTravelAreaName());
				info.setTravelAttendPeopId(travelAppliInfo.getTravelAttendPeopId());
				info.setTravelAttendPeop(travelAppliInfo.getTravelAttendPeop());
				info.setTravelPersonnelLevel(travelAppliInfo.getTravelPersonnelLevel());
				info.setTravelAreaTime(travelAppliInfo.getTravelAreaTime());
				info.setAreaNames(travelAppliInfo.getAreaNames());
				info.setAreaCode(travelAppliInfo.getAreaCode());
				info.setfDriveWay(travelAppliInfo.getfDriveWay());
				info.setfDriveWayCode(travelAppliInfo.getfDriveWayCode());
				info.setReason(travelAppliInfo.getReason());
				info.setTravelType(bean.getTravelType());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		//删除数据库中   报销中的城市间交通费
		getSession().createSQLQuery("delete from T_OUTSIDE_TRADDIC_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		//获取出差行程单的Json对象
		if(!StringUtil.isEmpty(outsideTraffic)){
			List<OutsideTrafficInfo> outside = JSON.parseObject("["+outsideTraffic.toString().substring(0, outsideTraffic.length()-1)+"]",new TypeReference<List<OutsideTrafficInfo>>(){});
			for (OutsideTrafficInfo outsideTrafficInfo : outside) {
				OutsideTrafficInfo info = new OutsideTrafficInfo();
				info.setfOId(shenTongMng.getSeq("T_OUTSIDE_TRADDIC_INFO_SEQ"));
				info.setrId(bean.getrId());
				info.setfStartDate(outsideTrafficInfo.getfStartDate());
				info.setfEndDate(outsideTrafficInfo.getfEndDate());
				info.setVehicle(outsideTrafficInfo.getVehicle());
				info.setTravelPersonnelLevel(outsideTrafficInfo.getTravelPersonnelLevel());
				info.setVehicleLevel(outsideTrafficInfo.getVehicleLevel());
				info.setTravelPersonnel(outsideTrafficInfo.getTravelPersonnel());
				info.setApplyAmount(outsideTrafficInfo.getApplyAmount());
				info.setSts("1");
				super.merge(info);
			}	
		}

		//删除数据库中   报销中的城内交通费
		getSession().createSQLQuery("delete from T_INCITY_TRAFFIC_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(inCity)){
			//获取城内交通费的Json对象
			List<InCityTrafficInfo> inCityList = JSON.parseObject("["+inCity.toString().substring(0, inCity.length()-1)+"]",new TypeReference<List<InCityTrafficInfo>>(){});
			for (InCityTrafficInfo inCityTrafficInfo : inCityList) {
				InCityTrafficInfo info = new InCityTrafficInfo();
				info.setFtId(shenTongMng.getSeq("T_INCITY_TRAFFIC_INFO_SEQ"));
				info.setrId(bean.getrId());
				info.setfPerson(inCityTrafficInfo.getfPerson());
				info.setfSubsidyDay(inCityTrafficInfo.getfSubsidyDay());
				info.setfAdjacentDay(inCityTrafficInfo.getfAdjacentDay());
				info.setfDistanceDay(inCityTrafficInfo.getfDistanceDay());
				info.setfApplyAmount(inCityTrafficInfo.getfApplyAmount());
				info.setTravelType(bean.getTravelType());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		//删除数据库中   报销中的住宿费
		getSession().createSQLQuery("delete from T_HOTEL_EXPENSE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		if(!StringUtil.isEmpty(hotelJson)){
			//获取城内交通费的Json对象
			List<HotelExpenseInfo> hotelExpenseList = JSON.parseObject("["+hotelJson.toString().substring(0, hotelJson.length()-1)+"]",new TypeReference<List<HotelExpenseInfo>>(){});
			for (HotelExpenseInfo hotelExpenseInfo : hotelExpenseList) {
				HotelExpenseInfo info = new HotelExpenseInfo();
				info.setfHId(shenTongMng.getSeq("T_HOTEL_EXPENSE_INFO_SEQ"));
				info.setrId(bean.getrId());
				info.setCheckInTime(hotelExpenseInfo.getCheckInTime());
				info.setCheckOUTTime(hotelExpenseInfo.getCheckOUTTime());
				info.setCityId(hotelExpenseInfo.getCityId());
				info.setLocationCity(hotelExpenseInfo.getLocationCity());
				info.setTravelPersonnel(hotelExpenseInfo.getTravelPersonnel());
				info.setTravelPersonnelId(hotelExpenseInfo.getTravelPersonnelId());
				info.setApplyAmount(hotelExpenseInfo.getApplyAmount());
				info.setTravelType(bean.getTravelType());
				info.setSts("1");
				super.merge(info);
			}
		}
		//删除数据库中   报销中的伙食补助费
		getSession().createSQLQuery("delete from T_FOOD_ALLOWANCE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
			if(!StringUtil.isEmpty(foodJson)){
			//获取城内交通费的Json对象
			List<FoodAllowanceInfo> foodAllowanceList = JSON.parseObject("["+foodJson.toString().substring(0, foodJson.length()-1)+"]",new TypeReference<List<FoodAllowanceInfo>>(){});
			for (FoodAllowanceInfo foodAllowanceInfo : foodAllowanceList) {
				FoodAllowanceInfo info = new FoodAllowanceInfo();
				info.setFfId(shenTongMng.getSeq("T_FOOD_ALLOWANCE_INFO_SEQ"));
				info.setrId(bean.getrId());
				info.setFname(foodAllowanceInfo.getFname());
				info.setFnameId(foodAllowanceInfo.getFnameId());
				info.setfDays(foodAllowanceInfo.getfDays());
				info.setfApplyAmount(foodAllowanceInfo.getfApplyAmount());
				info.setTravelType(bean.getTravelType());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()+" and F_INNER_OR_OUTER='0'").executeUpdate();
			if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getPayeeName());
				info.setBank(reimbPayeeInfo.getBank());
				info.setBankAccount(reimbPayeeInfo.getBankAccount());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				info.setfInnerOrOuter("0");
				//保存或修改个人收款信息
				paymentMethodInfoMng.saveInfo(info, user);
				super.merge(info);
			}
		}
		//保存外部收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+" and F_INNER_OR_OUTER='1'").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJsonExt)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJsonExt.toString().substring(0, payerinfoJsonExt.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				info.setrId(bean.getrId());//关联申请报销单
				info.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				info.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				info.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				info.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				info.setfInnerOrOuter("1");
				//保存收款人信息
				super.merge(info);
			}	
		}
			
			//删除城市间交通费用中的旧发票
			getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='traveloutside'").executeUpdate();
			//保存城市间交通费费用发票
			if(!StringUtil.isEmpty(form1)){
				JSONArray array1 =JSONArray.fromObject(form1.toString());
				for(int i=0;i<=array1.size()-1;i++) {
					String a= array1.get(i).toString();
					//String b=a.substring(1, a.length()-1);
					List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						if (newrd.getIcId() == null) {
							int seq = shenTongMng.getSeq("invoice_coupon_info");
							newrd.setIcId(seq);
						}
						newrd.setrId(bean.getrId());
						newrd.setfDataType("traveloutside");
						//保存新的明细
						super.merge(newrd);
					}
				}
			}
			//删除交通费用中的旧发票
			getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='travelhotel'").executeUpdate();
			//保存交通费用发票
			if(!StringUtil.isEmpty(form2)){
				JSONArray array1 =JSONArray.fromObject(form2.toString());
				for(int i=0;i<=array1.size()-1;i++) {
					String a= array1.get(i).toString();
					//String b=a.substring(1, a.length()-1);
					List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						if (newrd.getIcId() == null) {
							int seq = shenTongMng.getSeq("invoice_coupon_info");
							newrd.setIcId(seq);
						}
						newrd.setrId(bean.getrId());
						newrd.setfDataType("travelhotel");
						//保存新的明细
						super.merge(newrd);
					}
				}
			}
			
			
	}
	
	/*
	 * 公务接待报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-03-03
	 * @updatetime 2020-03-03
	 */
	@Override
	public void saveReception(ReimbAppliBasicInfo bean,
			String form1, String form2, String form3, String form4, String form5, String files, User user,
			ReceptionAppliInfo receptionAppliInfo,
			String hotelJson,						//住宿费Json
			String foodJson,                        //餐费或伙食费
			String otherJson,                       //其他费用
			String payerinfoJson,                   //收款人json
			String payerinfoJsonExt,				//外部收款人json
			String recePeop,						//接待人信息json
			String storkJson,						//主要行程信息json
			String chargeJson						//收费明细json
			)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		
		if (bean.getrId()==null) {
			int seq = shenTongMng.getSeq("reimb_appli_basic_info_seq");
			bean.setrId(seq);
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		

		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
				throw new ServiceException("该借款单正在还款审批中！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		//String ywlx =getType(applyBean.getType());
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
		if("GWCX".equals(applyBean.getTravelType())){
			strType = "GWCXBX";
		}
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			
			
//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
					taskName = "[公务接待报销]" + taskName + "[超额报销]";
			}else{
					taskName = "[公务接待报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			synchronized (this) {
				Boolean b=budgetIndexMgrMng.checkAmount(bean);
				if(b){
					//计算明细申请金额总数
					Double num = bean.getApplyAmount();
					Double nums =bean.getAmount();
					if(nums>num){
						budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
					}
				}
			}
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存修改后接待内容
		if(receptionAppliInfo != null) {
			if (receptionAppliInfo.getjId()==null) {
				receptionAppliInfo.setjId(shenTongMng.getSeq("T_RECEPTION_APPLI_INFO_SEQ"));
				//创建人、创建时间、发布时间
				receptionAppliInfo.setCreator(user.getAccountNo());
				receptionAppliInfo.setCreateTime(d);
			} else {
				//修改人、修改时间
				receptionAppliInfo.setUpdator(user.getAccountNo());
				receptionAppliInfo.setUpdateTime(d);
			}
			receptionAppliInfo.setrId(bean.getrId());
			receptionAppliInfo =(ReceptionAppliInfo) super.saveOrUpdate(receptionAppliInfo);
		}
			
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);

		//删除数据库中   报销中的费用明细中的住宿费明细
		getSession().createSQLQuery("delete from T_RECEPTION_HOTEL where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取费用明细的Json对象
		if(!StringUtil.isEmpty(hotelJson)){
			List<ReceptionHotel> hotelList = JSON.parseObject("["+hotelJson.toString().substring(0, hotelJson.length()-1)+"]",new TypeReference<List<ReceptionHotel>>(){});
			for (int i = 0; i < hotelList.size(); i++) {
				ReceptionHotel hotel = new ReceptionHotel();
				hotel.sethID(shenTongMng.getSeq("T_RECEPTION_HOTEL_SEQ"));
				hotel.setrId(bean.getrId());
				hotel.setfName(hotelList.get(i).getfName());
				hotel.setPosition(hotelList.get(i).getPosition());
				hotel.setPositionCode(hotelList.get(i).getPositionCode());
				hotel.setfRoomType(hotelList.get(i).getfRoomType());
				hotel.setfDays(hotelList.get(i).getfDays());
				hotel.setfCostHotel(hotelList.get(i).getfCostHotel());
				super.saveOrUpdate(hotel);
			}
		}
		//删除住宿费中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='travelhotel'").executeUpdate();
		//保存住宿费用发票
		if(!StringUtil.isEmpty(form1)){
			JSONArray array1 =JSONArray.fromObject(form1.toString());
			for(int i=0;i<=array1.size()-1;i++) {
				String a=array1.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						if (newrd.getIcId() == null) {
							int seq = shenTongMng.getSeq("invoice_coupon_info");
							newrd.setIcId(seq);
						}
						newrd.setrId(bean.getrId());
						newrd.setfDataType("travelhotel");
						//保存新的明细
						super.merge(newrd);
					}
			}
		}
		//删除数据库中   报销中的费用明细中的餐费明细
		getSession().createSQLQuery("delete from T_RECEPTION_FOOD where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取费用明细的Json对象
		if(!StringUtil.isEmpty(foodJson)){
			List<ReceptionFood> foodList = JSON.parseObject("["+foodJson.toString().substring(0, foodJson.length()-1)+"]",new TypeReference<List<ReceptionFood>>(){});
			for (int i = 0; i < foodList.size(); i++) {
				ReceptionFood food = new ReceptionFood();
				food.setfID(shenTongMng.getSeq("T_RECEPTION_FOOD_SEQ"));
				food.setrId(bean.getrId());
				food.setfFoodType(foodList.get(i).getfFoodType());
				food.setfCostStd(foodList.get(i).getfCostStd());
				food.setfPersonNum(foodList.get(i).getfPersonNum());
				food.setfCostFood(foodList.get(i).getfCostFood());
				super.saveOrUpdate(food);
			}
		}
		//删除餐费中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='receptionfood'").executeUpdate();
		//保存餐费费用发票
		if(!StringUtil.isEmpty(form2)){
			JSONArray array2 =JSONArray.fromObject(form2.toString());
			for(int i=0;i<=array2.size()-1;i++) {
				String a=array2.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						if (newrd.getIcId() == null) {
							int seq = shenTongMng.getSeq("invoice_coupon_info");
							newrd.setIcId(seq);
						}
						newrd.setrId(bean.getrId());
						newrd.setfDataType("receptionfood");
						//保存新的明细
						super.merge(newrd);
					}
			}
		}
		/*//删除交通费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='hotel'").executeUpdate();
		//保存交通费用发票
		if(!StringUtil.isEmpty(form3)){
			JSONArray array3 =JSONArray.fromObject(form3.toString());
			for(int i=0;i<=array3.size()-1;i++) {
				String a= array3.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					newrd.setrId(bean.getrId());
					newrd.setfDataType("hotel");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}*/
		//删除会议室租金费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='costRent'").executeUpdate();
		//保存会议室租金费用发票
		if(!StringUtil.isEmpty(form4)){
			JSONArray array4 =JSONArray.fromObject(form4.toString());
			for(int i=0;i<=array4.size()-1;i++) {
				String a= array4.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					if (newrd.getIcId() == null) {
						int seq = shenTongMng.getSeq("invoice_coupon_info");
						newrd.setIcId(seq);
					}
					newrd.setrId(bean.getrId());
					newrd.setfDataType("costRent");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}
		//删除数据库中   报销中的费用明细中的其他费用明细
		getSession().createSQLQuery("delete from T_RECEPTION_OTEHR where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取费用明细的Json对象
		if(!StringUtil.isEmpty(otherJson)){
			List<ReceptionOther> otherList = JSON.parseObject("["+otherJson.toString().substring(0, otherJson.length()-1)+"]",new TypeReference<List<ReceptionOther>>(){});
			for (int i = 0; i < otherList.size(); i++) {
				ReceptionOther other = new ReceptionOther();
				other.setoID(shenTongMng.getSeq("T_RECEPTION_OTEHR_SEQ"));
				other.setrId(bean.getrId());
				other.setfCostName(otherList.get(i).getfCostName());
				other.setfCost(otherList.get(i).getfCost());
				other.setfRemark(otherList.get(i).getfRemark());
				super.saveOrUpdate(other);
			}
		}
		//删除其他费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='receptionother'").executeUpdate();
		//保存其他费用发票
		if(!StringUtil.isEmpty(form5)){
			JSONArray array5 =JSONArray.fromObject(form5.toString());
			for(int i=0;i<=array5.size()-1;i++) {
				String a= array5.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					if (newrd.getIcId() == null) {
						int seq = shenTongMng.getSeq("invoice_coupon_info");
						newrd.setIcId(seq);
					}
					newrd.setrId(bean.getrId());
					newrd.setfDataType("receptionother");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}
		//保存内部收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_INNER_OR_OUTER=0 and F_R_ID="+bean.getrId()+"").executeUpdate();
		//内部收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
				payeeBean.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				payeeBean.setrId(bean.getrId());//关联申请报销单
				payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				payeeBean.setfInnerOrOuter("0");//内部
				//保存或修改个人收款信息
				paymentMethodInfoMng.saveInfo(payeeBean, user);
				//保存收款人信息
				super.merge(payeeBean);
			}	
		}
		//保存外部收款人信息
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_INNER_OR_OUTER=1 and F_R_ID="+bean.getrId()+"").executeUpdate();
		//外部收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJsonExt)){
			List<ReimbPayeeInfo> reimbPayeeInfoExtList = JSON.parseObject("["+payerinfoJsonExt.toString().substring(0, payerinfoJsonExt.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoExtList.size(); i++) {
				ReimbPayeeInfo payeeBeanExt = new ReimbPayeeInfo();
				payeeBeanExt.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				payeeBeanExt.setrId(bean.getrId());//关联申请报销单
				payeeBeanExt.setPayeeName(reimbPayeeInfoExtList.get(i).getPayeeName());//搜款人姓名
				payeeBeanExt.setBankAccount(reimbPayeeInfoExtList.get(i).getBankAccount());//银行账户
				payeeBeanExt.setBank(reimbPayeeInfoExtList.get(i).getBank());//银行账户
				payeeBeanExt.setPayeeAmount(reimbPayeeInfoExtList.get(i).getPayeeAmount());//应付转账金额
				payeeBeanExt.setfInnerOrOuter("1");//外部
				//保存或修改个人收款信息
				//paymentMethodInfoMng.saveInfo(payeeBeanExt, user);
				//保存收款人信息
				super.merge(payeeBeanExt);
			}	
		}
		
		//删除数据库中   报销中接待人信息
		getSession().createSQLQuery("delete from T_RECEPTION_APPLI_PEOPLE_INFO where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取接待人员信息的Json对象
		if(!StringUtil.isEmpty(recePeop)){
			List<ReceptionPeopleInfo> recePeopList = JSON.parseObject("["+recePeop.toString().substring(0, recePeop.length()-1)+"]",new TypeReference<List<ReceptionPeopleInfo>>(){});
			for (int i = 0; i < recePeopList.size(); i++) {
				ReceptionPeopleInfo peopleInfo = new ReceptionPeopleInfo();
				BeanUtils.copyProperties(recePeopList.get(i), peopleInfo);
				peopleInfo.setjId(shenTongMng.getSeq("T_RECEPTION_APPLI_PEOPLE_INFO_SEQ"));
				peopleInfo.setrId(bean.getrId());
				super.saveOrUpdate(peopleInfo);
			}
		}
		
		//删除数据库中   报销中主要行程信息
		getSession().createSQLQuery("delete from T_RECEPTION_STROK_PLAN where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取接待主要行程信息Json对象
		if(!StringUtil.isEmpty(storkJson)){
			List<ReceptionStrokPlan> receStrokList = JSON.parseObject("["+storkJson.toString().substring(0, storkJson.length()-1)+"]",new TypeReference<List<ReceptionStrokPlan>>(){});
			for (int i = 0; i < receStrokList.size(); i++) {
				ReceptionStrokPlan strokInfo = new ReceptionStrokPlan();
				BeanUtils.copyProperties(receStrokList.get(i), strokInfo);
				strokInfo.setfSPId(shenTongMng.getSeq("T_RECEPTION_STROK_PLAN_SEQ"));
				strokInfo.setrId(bean.getrId());
				super.saveOrUpdate(strokInfo);
			}
		}
		
		//删除数据库中   报销中收费信息
		getSession().createSQLQuery("delete from T_CHARGE_INFO where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取接待收费信息Json对象
		if(!StringUtil.isEmpty(chargeJson)){
			List<ReceptionChargeInfo> receChargeList = JSON.parseObject("["+chargeJson.toString().substring(0, chargeJson.length()-1)+"]",new TypeReference<List<ReceptionChargeInfo>>(){});
			for (int i = 0; i < receChargeList.size(); i++) {
				ReceptionChargeInfo chargeInfo = new ReceptionChargeInfo();
				BeanUtils.copyProperties(receChargeList.get(i), chargeInfo);
				chargeInfo.setcId(shenTongMng.getSeq("T_CHARGE_INFO_SEQ"));
				chargeInfo.setrId(bean.getrId());
				super.saveOrUpdate(chargeInfo);
			}
		}
		
	}
	
	/*
	 * 培训报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-03-03
	 * @updatetime 2020-03-04
	 */
	@Override
	public void saveTrain(ReimbAppliBasicInfo bean,
			TrainingAppliInfo trainingBean, String form1, String form2,
			String form3, String form4, String form5, String trainLecturer,
			String trainPlan, String zongheJson, String lessonJson, String hotelJson, String foodJson,
			String trafficJson1, String trafficJson2, String payerinfoJson,
			String files, User user,String payerinfoJsonExt, String bxtzfiles, String bxqdbfiles)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		Boolean isFirst;
		if (bean.getrId()==null) {
			int seq = shenTongMng.getSeq("reimb_appli_basic_info_seq");
			bean.setrId(seq);
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			isFirst=true; 
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			isFirst=false; 
		}
		

		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
				throw new ServiceException("该借款单正在还款审批中！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核

			Integer flowId =0;
			User nextUser = new User();
			//String ywlx =getType(applyBean.getType());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getgCode());//为申请单的单号


			//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());

			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){//报销金额-申请金额>0
				taskName = "[培训报销]" + taskName + "[超额报销]";
			}else{
				taskName = "[培训报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);

			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}

		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();

		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		attachmentMng.joinEntity(bean,bxtzfiles);
		attachmentMng.joinEntity(bean,bxqdbfiles);
		//保存培训信息
		if(trainingBean != null) {
			if (trainingBean.gettId()==null) {
				trainingBean.settId(shenTongMng.getSeq("T_TRAINING_APPLI_INFO_SEQ"));
				//创建人、创建时间、发布时间
				trainingBean.setCreator(user.getAccountNo());
				trainingBean.setCreateTime(d);
			} else {
				//修改人、修改时间
				trainingBean.setUpdator(user.getAccountNo());
				trainingBean.setUpdateTime(d);
			}
			trainingBean.setrId(bean.getrId());
			trainingBean = (TrainingAppliInfo) super.saveOrUpdate(trainingBean);
		}

		/**保存调整后讲师信息*/
		JSONArray array =JSONArray.fromObject("["+trainLecturer.toString()+"]");
		List newlec = (List)JSONArray.toList(array, LecturerInfo.class);
		if(isFirst==false){
			//获得老的信息
			List<Object> oldlec = applyMng.getObjectList("LecturerInfo", "tId", trainingBean.gettId());
			//获取现有的明细
			/*//比较新老明细的不同
			for (int i = oldlec.size()-1; i >= 0; i--) {
				LecturerInfo oldgad = (LecturerInfo) oldlec.get(i);
				for (int j = 0; j < newlec.size(); j++) {
					LecturerInfo gad = (LecturerInfo) newlec.get(j);
					if(gad.getlId()!=null){
						if(gad.getlId()==oldgad.getlId()){
							oldlec.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldlec.size()>0){
				for (int i = 0; i < oldlec.size(); i++) {
					LecturerInfo oldgad = (LecturerInfo) oldlec.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newlec.size(); j++) {
				LecturerInfo gad = (LecturerInfo) newlec.get(j);
				if (gad.getlId() == null) {
					gad.setlId(shenTongMng.getSeq("T_LECTURER_INFO_SEQ"));
				}
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				super.merge(gad);
			}
		}else{
			for (int i = 0; i < newlec.size(); i++) {
				LecturerInfo gad = (LecturerInfo) newlec.get(i);
				LecturerInfo lec =new LecturerInfo();
				BeanUtils.copyProperties(gad,lec);
				lec.setlId(shenTongMng.getSeq("T_LECTURER_INFO_SEQ"));
				lec.settId(trainingBean.gettId());
				lec.setCreator(user.getAccountNo());
				lec.setCreateTime(d);
				super.merge(lec);
			}
		}
		//保存培训日程
		//获得新的培训日程信息

		List<MeetPlan> oc = JSON.parseObject("["+trainPlan.toString()+"]",new TypeReference<List<MeetPlan>>(){});
		if(isFirst==false){
			//获得老的信息
			List<Object> oldrp = applyMng.getObjectList("MeetPlan", "tId", trainingBean.gettId());
			/*List<Object> oldrp1 = new ArrayList();
			for (int i = 0; i < oldrp.size(); i++) {
				MeetPlan oldrpi = (MeetPlan) oldrp.get(i);
				for (int j = 0; j < oc.size(); j++) {		
					MeetPlan rpi = (MeetPlan) oc.get(j);
					if(rpi.gettId()!=null){
						if(rpi.gettId()!=oldrpi.gettId()){
							oldrp1.add(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldrp.size()>0){
				for (int i = 0; i < oldrp.size(); i++) {
					MeetPlan oldrpi = (MeetPlan) oldrp.get(i);
					super.delete(oldrpi);
				}
			}

			for (int i = 0; i < oc.size(); i++) {		
				MeetPlan rpi = (MeetPlan) oc.get(i);
				if (rpi.getPlanId() == null) {
					rpi.setPlanId(shenTongMng.getSeq("t_meet_plan_seq"));
				}
				rpi.settId(trainingBean.gettId());
				rpi.setCreator(user.getAccountNo());
				rpi.setCreateTime(d);
				//保存培训日程信息
				super.merge(rpi);
			}
		}else{
			for (int i = 0; i < oc.size(); i++) {		
				MeetPlan rpi = (MeetPlan) oc.get(i);
				MeetPlan plan =new MeetPlan();
				BeanUtils.copyProperties(rpi,plan);
				plan.setPlanId(shenTongMng.getSeq("t_meet_plan_seq"));
				plan.settId(trainingBean.gettId());
				plan.setCreator(user.getAccountNo());
				plan.setCreateTime(d);
				//保存会议计划信息
				super.merge(plan);
			}
		}
		//保存综合预算明细
		//获得老的信息
		List<Object> oldmx =applyMng.getMingxi("ApplicationDetail", "rId", bean.getrId());
		//获取现有的明细
		List mx = getMingXiJson(zongheJson, ApplicationDetail.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldmx.size()-1; i >= 0; i--) {
				ApplicationDetail oldgad = (ApplicationDetail) oldmx.get(i);
				for (int j = 0; j < mx.size(); j++) {
					ApplicationDetail gad = (ApplicationDetail) mx.get(j);
					if(gad.getcId()!=null){
						if(gad.getcId()==oldgad.getcId()){
							oldmx.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldmx.size()>0){
				for (int i = 0; i < oldmx.size(); i++) {
					ApplicationDetail oldgad = (ApplicationDetail) oldmx.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < mx.size(); j++) {
				ApplicationDetail gad = (ApplicationDetail) mx.get(j);
				if (gad.getcId() == null) {
					gad.setcId(shenTongMng.getSeq("appli_detail_seq"));
				}
				gad.setrId(bean.getrId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < mx.size(); j++) {
				ApplicationDetail gad = (ApplicationDetail) mx.get(j);
				ApplicationDetail newMingxi =new ApplicationDetail();
				BeanUtils.copyProperties(gad,newMingxi);
				newMingxi.setcId(shenTongMng.getSeq("appli_detail_seq"));
				newMingxi.setgId(null);
				newMingxi.setrId(bean.getrId());
				newMingxi.setCreator(user.getAccountNo());
				newMingxi.setCreateTime(d);
				super.merge(newMingxi);
			}
		}
		//保存讲课费
		//获得老的信息
		List<TrainTeacherCost> oldlesson = applyMng.getTeacherMingxi(trainingBean.gettId(),"lesson");
		//获取现有的明细
		JSONArray array1 =JSONArray.fromObject("["+lessonJson.toString()+"]");
		List newlesson = (List)JSONArray.toList(array1, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldlesson.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldlesson.get(i);
				for (int j = 0; j < newlesson.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldlesson.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldlesson.size()>0){
				for (int i = 0; i < oldlesson.size(); i++) {
					TrainTeacherCost oldgad = oldlesson.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newlesson.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
				if (gad.getThId() == null) {
					gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				}
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("lesson");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newlesson.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
				TrainTeacherCost lesson =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,lesson);
				lesson.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				lesson.settId(trainingBean.gettId());
				lesson.setCreator(user.getAccountNo());
				lesson.setCreateTime(d);
				lesson.setCostType("lesson");
				super.merge(lesson);
			}
		}
		//保存住宿费
		//获得老的信息
		List<TrainTeacherCost> oldhotel = applyMng.getTeacherMingxi(trainingBean.gettId(),"hotel");
		//获取现有的明细
		JSONArray array2 =JSONArray.fromObject("["+hotelJson.toString()+"]");
		List newhotel = (List)JSONArray.toList(array2, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldhotel.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldhotel.get(i);
				for (int j = 0; j < newhotel.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldhotel.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldhotel.size()>0){
				for (int i = 0; i < oldhotel.size(); i++) {
					TrainTeacherCost oldgad = oldhotel.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newhotel.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
				if (gad.getThId() == null) {
					gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				}
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("hotel");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newhotel.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
				TrainTeacherCost hotel =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,hotel);
				hotel.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				hotel.settId(trainingBean.gettId());
				hotel.setCreator(user.getAccountNo());
				hotel.setCreateTime(d);
				hotel.setCostType("hotel");
				super.merge(hotel);
			}
		}
		//保存伙食费
		//获得老的信息
		List<TrainTeacherCost> oldfood = applyMng.getTeacherMingxi(trainingBean.gettId(),"food");
		//获取现有的明细
		JSONArray array3 =JSONArray.fromObject("["+foodJson.toString()+"]");
		List newfood = (List)JSONArray.toList(array3, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldfood.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldfood.get(i);
				for (int j = 0; j < newfood.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldfood.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldfood.size()>0){
				for (int i = 0; i < oldfood.size(); i++) {
					TrainTeacherCost oldgad = oldfood.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newfood.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
				if (gad.getThId() == null) {
					gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				}
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("food");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newfood.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
				TrainTeacherCost food =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,food);
				food.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				food.settId(trainingBean.gettId());
				food.setCreator(user.getAccountNo());
				food.setCreateTime(d);
				food.setCostType("food");
				super.merge(food);
			}
		}
		//保存城市间交通费
		//获得老的信息
		List<TrainTeacherCost> oldtraffic1 = applyMng.getTeacherMingxi(trainingBean.gettId(),"traffic1");
		//获取现有的明细
		JSONArray array4 =JSONArray.fromObject("["+trafficJson1.toString()+"]");
		List newtraffic1 = (List)JSONArray.toList(array4, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldtraffic1.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldtraffic1.get(i);
				for (int j = 0; j < newtraffic1.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldtraffic1.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldtraffic1.size()>0){
				for (int i = 0; i < oldtraffic1.size(); i++) {
					TrainTeacherCost oldgad = oldtraffic1.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newtraffic1.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
				if (gad.getThId() == null) {
					gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				}
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("traffic1");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newtraffic1.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
				TrainTeacherCost traffic1 =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,traffic1);
				traffic1.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				traffic1.settId(trainingBean.gettId());
				traffic1.setCreator(user.getAccountNo());
				traffic1.setCreateTime(d);
				traffic1.setCostType("traffic1");
				super.merge(traffic1);
			}
		}
		//保存市内交通费
		//获得老的信息
		List<TrainTeacherCost> oldtraffic2 = applyMng.getTeacherMingxi(trainingBean.gettId(),"traffic2");
		//获取现有的明细
		JSONArray array5 =JSONArray.fromObject("["+trafficJson2.toString()+"]");
		List newtraffic2 = (List)JSONArray.toList(array5, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldtraffic2.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldtraffic2.get(i);
				for (int j = 0; j < newtraffic2.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldtraffic2.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldtraffic2.size()>0){
				for (int i = 0; i < oldtraffic2.size(); i++) {
					TrainTeacherCost oldgad = oldtraffic2.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newtraffic2.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
				if (gad.getThId() == null) {
					gad.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				}
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("traffic2");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newtraffic2.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
				TrainTeacherCost traffic2 =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,traffic2);
				traffic2.setThId(shenTongMng.getSeq("T_TRAIN_TEACHER_COST_SEQ"));
				traffic2.settId(trainingBean.gettId());
				traffic2.setCreator(user.getAccountNo());
				traffic2.setCreateTime(d);
				traffic2.setCostType("traffic2");
				super.merge(traffic2);
			}
		}
		//删除综合预算费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='trainZonghe'").executeUpdate();
		//保存综合费用发票
		if(!StringUtil.isEmpty(form1)){
			JSONArray zongheArray =JSONArray.fromObject(form1.toString());
			for(int i=0;i<=zongheArray.size()-1;i++) {
				String a=zongheArray.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					if (newrd.getIcId() == null) {
						int seq = shenTongMng.getSeq("invoice_coupon_info");
						newrd.setIcId(seq);
					}
					newrd.setrId(bean.getrId());
					newrd.setfDataType("trainZonghe");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}
		//删除住宿费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='trainHotel'").executeUpdate();
		//保存住宿费用发票
		if(!StringUtil.isEmpty(form2)){
			JSONArray hotelArray =JSONArray.fromObject(form2.toString());
			for(int i=0;i<=hotelArray.size()-1;i++) {
				String a=hotelArray.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					if (newrd.getIcId() == null) {
						int seq = shenTongMng.getSeq("invoice_coupon_info");
						newrd.setIcId(seq);
					}
					newrd.setrId(bean.getrId());
					newrd.setfDataType("trainHotel");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}
		//删除伙食费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='trainFood'").executeUpdate();
		//保存伙食费用发票
		if(!StringUtil.isEmpty(form3)){
			JSONArray foodArray =JSONArray.fromObject(form3.toString());
			for(int i=0;i<=foodArray.size()-1;i++) {
				String a=foodArray.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					if (newrd.getIcId() == null) {
						int seq = shenTongMng.getSeq("invoice_coupon_info");
						newrd.setIcId(seq);
					}
					newrd.setrId(bean.getrId());
					newrd.setfDataType("trainFood");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}
		//删除城市间交通费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='trainTraffic1'").executeUpdate();
		//保存城市间交通费发票
		if(!StringUtil.isEmpty(form4)){
			JSONArray trafficArray1 =JSONArray.fromObject(form4.toString());
			for(int i=0;i<=trafficArray1.size()-1;i++) {
				String a=trafficArray1.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					if (newrd.getIcId() == null) {
						int seq = shenTongMng.getSeq("invoice_coupon_info");
						newrd.setIcId(seq);
					}
					newrd.setrId(bean.getrId());
					newrd.setfDataType("trainTraffic1");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}
		//删除城市间交通费用中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='trainTraffic2'").executeUpdate();
		//保存城市间交通费发票
		if(!StringUtil.isEmpty(form5)){
			JSONArray trafficArray2 =JSONArray.fromObject(form5.toString());
			for(int i=0;i<=trafficArray2.size()-1;i++) {
				String a=trafficArray2.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					if (newrd.getIcId() == null) {
						int seq = shenTongMng.getSeq("invoice_coupon_info");
						newrd.setIcId(seq);
					}
					newrd.setrId(bean.getrId());
					newrd.setfDataType("trainTraffic2");
					//保存新的明细
					super.merge(newrd);
				}
			}
		}
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+" and F_INNER_OR_OUTER='0'").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
				List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
				for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
					ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
					payeeBean.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
					payeeBean.setrId(bean.getrId());//关联申请报销单
					payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
					payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
					payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
					payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
					payeeBean.setfInnerOrOuter("0");
					//保存或修改个人收款信息
					paymentMethodInfoMng.saveInfo(payeeBean, user);
					//保存收款人信息
					super.merge(payeeBean);
				}	
		}
		//保存外部收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+" and F_INNER_OR_OUTER='1'").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJsonExt)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJsonExt.toString().substring(0, payerinfoJsonExt.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
				payeeBean.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				payeeBean.setrId(bean.getrId());//关联申请报销单
				payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				payeeBean.setfInnerOrOuter("1");
				//保存或修改个人收款信息
				//paymentMethodInfoMng.saveInfo(payeeBean, user);
				//保存收款人信息
				super.merge(payeeBean);
			}	
		}
	}
	
	/*
	 * 公务出国报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-03-03
	 * @updatetime 2020-03-04
	 */
	@Override
	public void saveAbroad(ReimbAppliBasicInfo bean,String payerinfoJson,
			String form1, String form2, String form3, String form4, String form5,
			String travelJson,	                    //国际旅费json
			String trafficJson1,					//城市间交通费和国外城市间交通费
			String hotelJson,	                    //住宿费json
			String foodJson,						//伙食费json
			String feeJson,							//公杂费json
			String feteJson,						//宴请费json
			String otherJson,						//其他收入json
			String abroadPlanJson,					//出访计划
			AbroadAppliInfo abroadBean,             //出国信息
			String files, User user)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		

		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
				if("1".equals(String.valueOf(bean.getWithLoan()))){
					List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
					List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
					List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
					Double totalCxAmount =0.00;
					if(applyReimbList !=null && applyReimbList.size()>0){
						for(int i = 0;i < applyReimbList.size();i++){
							if(applyReimbList.get(i).getCxAmount() !=null){
								totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
							}else{
								totalCxAmount =0+totalCxAmount;
							}
							ReimCXInfo info = new ReimCXInfo();
							info.setrId(applyReimbList.get(i).getrId());
							info.setgName(applyReimbList.get(i).getgName());
							info.setType(1);
							info.setCxAmount(applyReimbList.get(i).getCxAmount());
							info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
							reimbList.add(info);
						}
					}
					if(directlyList !=null && directlyList.size()>0){
						for(int i = 0;i < directlyList.size();i++){
							if(directlyList.get(i).getCxAmount() !=null){
								totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
							}else{
								totalCxAmount =0+totalCxAmount;
							}
							ReimCXInfo info = new ReimCXInfo();
							info.setrId(directlyList.get(i).getDrId());
							info.setgName(directlyList.get(i).getSummary());
							info.setType(0);
							info.setCxAmount(directlyList.get(i).getCxAmount());
							info.setReimburseReqTime(directlyList.get(i).getReqTime());
							reimbList.add(info);
						}
					}
					LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
					if(totalCxAmount>loanBasicInfo.getlAmount()){
						throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
					}
					if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
						throw new ServiceException("该借款单正在还款审批中！");
					}
					Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
					if(CE<bean.getCxAmount()){
						throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
					}
					bean.setWithLoan(1);
				}else{
					bean.setLoan(null);
					bean.setWithLoan(0);
				}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
		if("GWCX".equals(applyBean.getTravelType())){
			strType = "GWCXBX";
		}
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(),strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			
			
//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
					taskName = "[公务出国报销]" + taskName + "[超额报销]";
			}else{
					taskName = "[公务出国报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		
		
		if (abroadBean.getFaId()==null) {
			//创建人、创建时间、发布时间
			abroadBean.setCreator(user.getAccountNo());
			abroadBean.setCreateTime(d);
		} else {
			//修改人、修改时间
			abroadBean.setUpdator(user.getAccountNo());
			abroadBean.setUpdateTime(d);
		}
		abroadBean.setrId(bean.getrId());
		abroadBean = (AbroadAppliInfo) super.merge(abroadBean);
		
		
		
		
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		//删除数据库中   申请中的出访计划信息
		getSession().createSQLQuery("delete from t_abroad_plan where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获得新的出访计划信息
		if(!StringUtil.isEmpty(abroadPlanJson)){
			//获取出访计划信息的Json对象
			List<AbroadPlan> rp = JSON.parseObject("["+abroadPlanJson.toString().substring(0, abroadPlanJson.length()-1)+"]",new TypeReference<List<AbroadPlan>>(){});
			for (AbroadPlan abroadPlan : rp) {
				AbroadPlan info = new AbroadPlan();
				info.setrId(bean.getrId());
				info.setAbroadDate(abroadPlan.getAbroadDate());
				info.setTimeEnd(abroadPlan.getTimeEnd());
				info.setArriveCountryId(abroadPlan.getArriveCountryId());
				info.setArriveCountry(abroadPlan.getArriveCountry());
				info.setArriveCity(abroadPlan.getArriveCity());
				info.setRemark(abroadPlan.getRemark());
				info.setStatus(1);
				super.merge(info);
			}
		}
		
		
		//删除数据库中   申请中的国际旅费
		getSession().createSQLQuery("delete from T_INTERNATIONAL_TRAVELING_EXPENSE where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获得新的国际旅费
		if(!StringUtil.isEmpty(travelJson)){
			//获取国际旅费的Json对象
			List<InternationalTravelingExpense> rp = JSON.parseObject("["+travelJson.toString().substring(0, travelJson.length()-1)+"]",new TypeReference<List<InternationalTravelingExpense>>(){});
			for (InternationalTravelingExpense internationalTravelingExpense : rp) {
				InternationalTravelingExpense info = new InternationalTravelingExpense();
				info.setrId(bean.getrId());
				info.setTimeStart(internationalTravelingExpense.getTimeStart());
				info.setTimeEnd(internationalTravelingExpense.getTimeEnd());
				info.setStartCity(internationalTravelingExpense.getStartCity());
				info.setArriveCity(internationalTravelingExpense.getArriveCity());
				info.setVehicle(internationalTravelingExpense.getVehicle());
				info.setApplyAmount(internationalTravelingExpense.getApplyAmount());
				info.setTrainSubsidies(internationalTravelingExpense.getTrainSubsidies());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//删除国际旅费中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='mix'").executeUpdate();
		//保存国际旅费发票
		if(!StringUtil.isEmpty(form1)){
			JSONArray array1 =JSONArray.fromObject(form1.toString());
			for(int i=0;i<=array1.size()-1;i++) {
				String a=array1.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						newrd.setrId(bean.getrId());
						newrd.setfDataType("mix");
						//保存新的明细
						super.merge(newrd);
					}
			}
		}
		
		//删除数据库中   申请中的城市间交通费
		getSession().createSQLQuery("delete from T_OUTSIDE_TRADDIC_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		//获取出城市间交通费的Json对象
		if(!StringUtil.isEmpty(trafficJson1)){
			List<OutsideTrafficInfo> outside = JSON.parseObject("["+trafficJson1.toString().substring(0, trafficJson1.length()-1)+"]",new TypeReference<List<OutsideTrafficInfo>>(){});
			for (OutsideTrafficInfo outsideTrafficInfo : outside) {
				OutsideTrafficInfo info = new OutsideTrafficInfo();
				info.setrId(bean.getrId());
				info.setTravelPersonnel(outsideTrafficInfo.getTravelPersonnel());
				info.setApplyAmount(outsideTrafficInfo.getApplyAmount());
				info.setSts("1");
				super.merge(info);
			}	
		}
		
		//删除国际城市间交通费中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='traveloutside'").executeUpdate();
		//保存国际城市间交通费发票
		if(!StringUtil.isEmpty(form2)){
			JSONArray array1 =JSONArray.fromObject(form2.toString());
			for(int i=0;i<=array1.size()-1;i++) {
				String a=array1.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						newrd.setrId(bean.getrId());
						newrd.setfDataType("traveloutside");
						//保存新的明细
						super.merge(newrd);
					}
			}
		}
		
		//删除数据库中   申请中的住宿费
		getSession().createSQLQuery("delete from T_HOTEL_EXPENSE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		if(!StringUtil.isEmpty(hotelJson)){
			//获取住宿费的Json对象
			List<HotelExpenseInfo> rp = JSON.parseObject("["+hotelJson.toString().substring(0, hotelJson.length()-1)+"]",new TypeReference<List<HotelExpenseInfo>>(){});
			for (HotelExpenseInfo hotelExpenseInfo : rp) {
				HotelExpenseInfo info = new HotelExpenseInfo();
				info.setrId(bean.getrId());
				info.setLocationCity(hotelExpenseInfo.getLocationCity());
				info.setCheckInTime(hotelExpenseInfo.getCheckInTime());
				info.setCheckOUTTime(hotelExpenseInfo.getCheckOUTTime());
				info.setCityId(hotelExpenseInfo.getCityId());
				info.setStandard(hotelExpenseInfo.getStandard());
				info.setHotelDay(hotelExpenseInfo.getHotelDay());
				info.setCountStandard(hotelExpenseInfo.getCountStandard());
				info.setCurrency(hotelExpenseInfo.getCurrency());
				info.setApplyAmount(hotelExpenseInfo.getApplyAmount());
				info.setSts("1");
				super.merge(info);
			}
		}
		
		//删除住宿费中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='travelhotel'").executeUpdate();
		//保存住宿费发票
		if(!StringUtil.isEmpty(form3)){
			JSONArray array1 =JSONArray.fromObject(form3.toString());
			for(int i=0;i<=array1.size()-1;i++) {
				String a=array1.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						newrd.setrId(bean.getrId());
						newrd.setfDataType("travelhotel");
						//保存新的明细
						super.merge(newrd);
					}
			}
		}
				
		//删除数据库中   申请中的伙食费
		getSession().createSQLQuery("delete from T_FOOD_ALLOWANCE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(foodJson)){
			//获取伙食费的Json对象
			List<FoodAllowanceInfo> rp = JSON.parseObject("["+foodJson.toString().substring(0, foodJson.length()-1)+"]",new TypeReference<List<FoodAllowanceInfo>>(){});
			for (FoodAllowanceInfo foodAllowanceInfo : rp) {
				FoodAllowanceInfo info = new FoodAllowanceInfo();
				info.setrId(bean.getrId());
				info.setfAddress(foodAllowanceInfo.getfAddress());
				info.setStandard(foodAllowanceInfo.getStandard());
				info.setfDays(foodAllowanceInfo.getfDays());
				info.setCountStandard(foodAllowanceInfo.getCountStandard());
				info.setCurrency(foodAllowanceInfo.getCurrency());
				info.setfApplyAmount(foodAllowanceInfo.getfApplyAmount());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//删除数据库中   申请中的公杂费
		getSession().createSQLQuery("delete from T_MISCELLANEOUS_FEE where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(feeJson)){
			//获取公杂费的Json对象
			List<MiscellaneousFeeInfo> rp = JSON.parseObject("["+feeJson.toString().substring(0, feeJson.length()-1)+"]",new TypeReference<List<MiscellaneousFeeInfo>>(){});
			for (MiscellaneousFeeInfo miscellaneousFeeInfo : rp) {
				MiscellaneousFeeInfo info = new MiscellaneousFeeInfo();
				info.setrId(bean.getrId());
				info.setfAddress(miscellaneousFeeInfo.getfAddress());
				info.setStandard(miscellaneousFeeInfo.getStandard());
				info.setfDays(miscellaneousFeeInfo.getfDays());
				info.setCountStandard(miscellaneousFeeInfo.getCountStandard());
				info.setCurrency(miscellaneousFeeInfo.getCurrency());
				info.setfApplyAmount(miscellaneousFeeInfo.getfApplyAmount());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//删除数据库中   申请中的宴请费
		getSession().createSQLQuery("delete from T_FETE_COST_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(feteJson)){
			//获取宴请费的Json对象
			List<FeteCostInfo> rp = JSON.parseObject("["+feteJson.toString().substring(0, feteJson.length()-1)+"]",new TypeReference<List<FeteCostInfo>>(){});
			for (FeteCostInfo feteCostInfo : rp) {
				FeteCostInfo info = new FeteCostInfo();
				info.setrId(bean.getrId());
				info.setfAddress(feteCostInfo.getfAddress());
				info.setfAddressId(feteCostInfo.getfAddressId());
				info.setStandard(feteCostInfo.getStandard());
				info.setfFeeNum(feteCostInfo.getfFeeNum());
				info.setfAccompanyNum(feteCostInfo.getfAccompanyNum());
				info.setCountStandard(feteCostInfo.getCountStandard());
				info.setCurrency(feteCostInfo.getCurrency());
				info.setfApplyAmount(feteCostInfo.getfApplyAmount());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//删除宴请费中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='travelfete'").executeUpdate();
		//保存宴请费发票
		if(!StringUtil.isEmpty(form4)){
			JSONArray array1 =JSONArray.fromObject(form4.toString());
			for(int i=0;i<=array1.size()-1;i++) {
				String a=array1.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						newrd.setrId(bean.getrId());
						newrd.setfDataType("travelfete");
						//保存新的明细
						super.merge(newrd);
					}
			}
		}
		
		//删除数据库中   申请中的其他费
		getSession().createSQLQuery("delete from T_RECEPTION_OTEHR where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(otherJson)){
			//获取其他费的Json对象
			List<ReceptionOther> rp = JSON.parseObject("["+otherJson.toString().substring(0, otherJson.length()-1)+"]",new TypeReference<List<ReceptionOther>>(){});
			for (ReceptionOther receptionOther : rp) {
				ReceptionOther info = new ReceptionOther();
				info.setrId(bean.getrId());
				info.setfCostName(receptionOther.getfCostName());
				info.setfCost(receptionOther.getfCost());
				info.setfRemark(receptionOther.getfRemark());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		//删除宴请费中的旧发票
		getSession().createSQLQuery("delete from T_INVOICE_COUPON_INFO where F_R_ID ="+bean.getrId()+" and F_DATA_TYPE='travelother'").executeUpdate();
		//保存宴请费发票
		if(!StringUtil.isEmpty(form5)){
			JSONArray array1 =JSONArray.fromObject(form5.toString());
			for(int i=0;i<=array1.size()-1;i++) {
				String a=array1.get(i).toString();
				//String b=a.substring(1, a.length()-1);
				List<InvoiceCouponInfo> newfp = JSON.parseObject("["+a+"]",new TypeReference<List<InvoiceCouponInfo>>(){});
					for (int x = 0; x < newfp.size(); x++) {
						InvoiceCouponInfo newrd = newfp.get(x);
						newrd.setrId(bean.getrId());
						newrd.setfDataType("travelother");
						//保存新的明细
						super.merge(newrd);
					}
			}
		}
		
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+"").executeUpdate();
		
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
				List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
				for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
					ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
					payeeBean.setrId(bean.getrId());//关联申请报销单
					payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
					payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
					payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
					payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
					//保存或修改个人收款信息
					paymentMethodInfoMng.saveInfo(payeeBean, user);
					//保存收款人信息
					super.merge(payeeBean);
				}	
		}
		
		
		/*int a = 0;
		if(a==0){
			throw new ServiceException("错了");
		}*/
	}
	
	/*
	 * 公车运维报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-03-09
	 * @updatetime 2020-03-09
	 */
	@Override 
	public void saveCar(ReimbAppliBasicInfo bean, String reimburseCartJson,String payerinfoJson,
			String arry, String files, User user)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		bean.setrCode(bean.getgCode());//申请单的code设置成报销单code
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		String StrIndex="";
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			StrIndex="0";
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			StrIndex="1";
		}
		

		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
				if("1".equals(String.valueOf(bean.getWithLoan()))){
					List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
					List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
					List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
					Double totalCxAmount =0.00;
					if(applyReimbList !=null && applyReimbList.size()>0){
						for(int i = 0;i < applyReimbList.size();i++){
							if(applyReimbList.get(i).getCxAmount() !=null){
								totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
							}else{
								totalCxAmount =0+totalCxAmount;
							}
							ReimCXInfo info = new ReimCXInfo();
							info.setrId(applyReimbList.get(i).getrId());
							info.setgName(applyReimbList.get(i).getgName());
							info.setType(1);
							info.setCxAmount(applyReimbList.get(i).getCxAmount());
							info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
							reimbList.add(info);
						}
					}
					if(directlyList !=null && directlyList.size()>0){
						for(int i = 0;i < directlyList.size();i++){
							if(directlyList.get(i).getCxAmount() !=null){
								totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
							}else{
								totalCxAmount =0+totalCxAmount;
							}
							ReimCXInfo info = new ReimCXInfo();
							info.setrId(directlyList.get(i).getDrId());
							info.setgName(directlyList.get(i).getSummary());
							info.setType(0);
							info.setCxAmount(directlyList.get(i).getCxAmount());
							info.setReimburseReqTime(directlyList.get(i).getReqTime());
							reimbList.add(info);
						}
					}
					LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
					if(totalCxAmount>loanBasicInfo.getlAmount()){
						throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
					}
					if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
						throw new ServiceException("该借款单正在还款审批中！");
					}
					Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
					if(CE<bean.getCxAmount()){
						throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
					}
					bean.setWithLoan(1);
				}else{
					bean.setLoan(null);
					bean.setWithLoan(0);
				}
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
		if("GWCX".equals(applyBean.getTravelType())){
			strType = "GWCXBX";
		}
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			
			
			
			
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
					taskName = "[公车运维报销]" + taskName + "[超额报销]";
			}else{
					taskName = "[公车运维报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);

		
		//删除数据库中   报销中的费用明细
		getSession().createSQLQuery("delete from T_OFFICE_CAR where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获取费用明细的Json对象
		List<OfficeCar> officeCar = JSON.parseObject("["+reimburseCartJson.toString().substring(0, reimburseCartJson.length()-1)+"]",new TypeReference<List<OfficeCar>>(){});
		for (int i = 0; i < officeCar.size(); i++) {
			OfficeCar car = new OfficeCar();
			car.setrId(bean.getrId());
			car.setfExpenseName(officeCar.get(i).getfExpenseName());
			car.setfUseType(officeCar.get(i).getfUseType());
			car.setfCarNum(officeCar.get(i).getfCarNum());
			car.setfCarType(officeCar.get(i).getfCarType());
			car.setfUseAmount(officeCar.get(i).getfUseAmount());
			car.setfRemark(officeCar.get(i).getfRemark());
			car.setfStatus(1);
			officeCarMng.saveOrUpdate(car);
		}
		//保存费用明细
		JSONArray array =JSONArray.fromObject(arry.toString());
		for(int i=0;i<=array.size()-1;i++) {
			String a=(String) array.get(i);
			String b=a.substring(1, a.length()-1);
			List<InvoiceCouponInfo> newfp = JSON.parseObject(b,new TypeReference<List<InvoiceCouponInfo>>(){});
			if(StrIndex.equals("0")){
				CostDetailInfo cost =new CostDetailInfo();
				cost.setCostName(newfp.get(0).getCostName());
				cost.setCostAmount(newfp.get(0).getCostAmount());
				cost.setrId(bean.getrId());
				super.saveOrUpdate(cost);
				for (int x = 0; x < newfp.size(); x++) {
					InvoiceCouponInfo newrd = newfp.get(x);
					newrd.setcId(cost.getcId());
					newrd.setrId(bean.getrId());
					newrd.setfDataType("car");
					//保存新的明细
					super.merge(newrd);
				}
			}else{
				List<CostDetailInfo> costList = findByRId(bean.getrId());
					costList.get(i).setCostName(newfp.get(0).getCostName());
					super.saveOrUpdate(costList.get(i));
					//旧发票
					List<InvoiceCouponInfo> oldfp = findfp(costList.get(i).getcId());
					
					//删除旧发票
					for (int h = 0; h < oldfp.size(); h++) {
						if(!StringUtil.isEmpty(oldfp.get(h).getFileId())&&!"null".equals(oldfp.get(h).getFileId())){
							Attachment attachment=attachmentMng.findById(oldfp.get(h).getFileId());
							if(attachment!=null){
								attachment.setFlag("0");
								attachment.setUpdator(user);
								attachment.setUpdateTime(new Date());
								attachmentMng.updateDefault(attachment);
							}
						}
						invoiceCouponMng.delete(oldfp.get(h));
					}
					//新增发票
					for (int j = 0; j < newfp.size(); j++) {
						InvoiceCouponInfo newrd = newfp.get(j);
						newrd.setcId(costList.get(i).getcId());
						newrd.setrId(bean.getrId());
						newrd.setfDataType("car");
						//保存新的明细
						super.merge(newrd);
					}
					costList.get(i).setCouponList(newfp);
			}
		}
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+"").executeUpdate();
		
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
				List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
				for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
					ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
					payeeBean.setrId(bean.getrId());//关联申请报销单
					payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
					payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
					payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
					payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
					//保存或修改个人收款信息
					paymentMethodInfoMng.saveInfo(payeeBean, user);
					//保存收款人信息
					super.merge(payeeBean);
				}	
		}
	}
	
	/*
	 * 通用事项报销申请新增和修改的保存
	 * @author 沈帆
	 * @createtime 2020-03-09
	 * @updatetime 2020-03-09
	 */
	@Override 
	public void saveCommon(ReimbAppliBasicInfo bean, ReimbPayeeInfo payeeBean,
			String arry, String files, User user,String form1,String payerinfoJson, String payerinfoJsonExt)  throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		
		String StrIndex="";
		if (bean.getrId()==null) {
			//获取序列
			int seq = shenTongMng.getSeq("reimb_appli_basic_info_seq");
			bean.setrId(seq);
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			StrIndex="0";
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			StrIndex="1";
		}
		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
				throw new ServiceException("该借款单正在还款审批中！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setIndexType(applyBean.getIndexType());
		bean.setProDetailId(applyBean.getProDetailId());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			/*Integer flowId =0;
			User nextUser = new User();
				nextUser = userMng.findById(bean.getFuserId());*/

			Integer flowId =0;
			User nextUser = new User();
			//String ywlx =getType(applyBean.getType());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			if(StringUtil.isEmpty(nextUser.getId())){
				throw new ServiceException("请选择下级审批人");
			}else {
				//获取t_personal_worktable表序列
				int seq = shenTongMng.getSeq("personal_worktable_seq");
				work.setfId(seq);
				work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
				work.setTaskId(bean.getrId());//申请单ID
				work.setTaskCode(bean.getrCode());//为申请单的单号
			}
			
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
					taskName = "[通用事项报销]" + taskName + "[超额报销]";
			}else{
					taskName = "[通用事项报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			if("1".equals(bean.getType())) {
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1&applyType=tysx");//为审批页面内容显示的url,需要将数据传入不然无法访问
				work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0&applyType=tysx");//查看url
			}else{
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
				work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			}
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			//获取t_personal_worktable表序列
			int seq2 = shenTongMng.getSeq("personal_worktable_seq");
			work2.setfId(seq2);
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			if("1".equals(bean.getType())) {
				work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1&applyType=tysx");//退回修改url
				work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0&applyType=tysx");//查看url
			}else{
				work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
				work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			}
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			Boolean b=budgetIndexMgrMng.checkAmount(bean);
			if(b){
				//计算明细申请金额总数
				Double num = bean.getApplyAmount();
				Double nums =bean.getAmount();
				if(nums>num){
					budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
				}
			}else {
				throw new ServiceException("报销金额超出可用金额，不允许提交！");
			}
		
			//修改申请单的报销状态为1（报销中）使其不可再次被选择
			getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
/*
		//保存费用明细
		List<ReimbDetail> mingxiList = JSON.parseObject("["+arry.toString().substring(0, arry.length()-1)+"]",new TypeReference<List<ReimbDetail>>(){});
		for(int i=0;i<=mingxiList.size()-1;i++) {
			ReimbDetail reimbDetail =new ReimbDetail();
			reimbDetail = mingxiList.get(i);
			reimbDetail.setrId(bean.getrId());
			super.merge(reimbDetail);
		}
		*/
		//删除数据库中   申请中的伙食补助费
		getSession().createSQLQuery("delete from T_APPLI_DETAIL where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
			if(!StringUtil.isEmpty(arry)){
			//获取城内交通费的Json对象
			List<ApplicationDetail> foodAllowanceList = JSON.parseObject("["+arry.toString().substring(0, arry.length()-1)+"]",new TypeReference<List<ApplicationDetail>>(){});
			for (ApplicationDetail applicationDetail: foodAllowanceList) {
				ApplicationDetail info = new ApplicationDetail();
				int cId = shenTongMng.getSeq("appli_detail_seq");
				info.setcId(cId);
				info.setrId(bean.getrId());
				info.setCostDetail(applicationDetail.getCostDetail());
				info.setRemibAmount(applicationDetail.getRemibAmount());
				info.setRemark(applicationDetail.getRemark());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()).executeUpdate();
			if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				//获取序列
				int seq = shenTongMng.getSeq("reimb_payee_info_seq");
				info.setpId(seq);
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getPayeeName());
				info.setBank(reimbPayeeInfo.getBank());
				info.setBankAccount(reimbPayeeInfo.getBankAccount());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				info.setfInnerOrOuter("0");
				super.merge(info);
			}
		}
		
		//保存外部收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+" and F_INNER_OR_OUTER='1'").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJsonExt)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJsonExt.toString().substring(0, payerinfoJsonExt.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				info.setrId(bean.getrId());//关联申请报销单
				info.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				info.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				info.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				info.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				info.setfInnerOrOuter("1");
				//保存收款人信息
				super.merge(info);
			}	
		}
		
		//旧发票
		List<InvoiceCouponInfo> oldfp1 = invoiceCouponMng.findByrID(bean.getrId(),"comm-1");
		//新发票
		List<InvoiceCouponInfo> newfp1 =  JSON.parseObject(form1, new TypeReference<List<InvoiceCouponInfo>>(){});
		//旧发票
		//删除旧发票
		for (int h = 0; h < oldfp1.size(); h++) {
			if(!StringUtil.isEmpty(oldfp1.get(h).getFileId())&&!"null".equals(oldfp1.get(h).getFileId())){
				Attachment attachment=attachmentMng.findById(oldfp1.get(h).getFileId());
				if(attachment!=null){
					attachment.setFlag("0");
					attachment.setUpdator(user);
					attachment.setUpdateTime(new Date());
					attachmentMng.updateDefault(attachment);
				}
			}
			invoiceCouponMng.delete(oldfp1.get(h));
		}
		//新增发票
		for (int i = 0; i < newfp1.size(); i++) {
			InvoiceCouponInfo newrd = newfp1.get(i);
			if (newrd.getIcId() == null) {
				//获取序列
				int seq = shenTongMng.getSeq("invoice_coupon_info");
				newrd.setIcId(seq);
			}
			newrd.setrId(bean.getrId());
			newrd.setfDataType("comm-1");
			//保存新的明细
			super.merge(newrd);
		}	
		
	}
	
	
	private String getType(String type){
		String ywlx = "BXSQ";
		if("1".equals(type)){
			//通用事项报销
			//ywlx = "ZJSQ";
		}else if("2".equals(type)){
			//查询会议信息
			ywlx = "HYBX";
		} else if ("3".equals(type)) {
			//查询培训信息
			ywlx = "PXBX";
		}else if("4".equals(type)){
			//查询差旅信息
			ywlx = "CLBX";
		}else if("5".equals(type)){
			//查询接待信息
			ywlx = "GWJDBX";
		}else if("6".equals(type)){
			//查询公务用车信息
			ywlx = "GWYCBX";
		}else if("7".equals(type)){
			//查询公务出国信息
			ywlx = "GWCGBX";
		}
		return ywlx;
	}
	
	@Override
	public void updateCashier(ReimbAppliBasicInfo rBean) {
		rBean = super.findById(rBean.getrId());
		if(rBean.getWithLoan()!=null){
			if ("1".equals(String.valueOf(rBean.getWithLoan()))){
				cashierMng.withLoan(rBean.getLoan(),rBean.getAmount());
			}
		}
		if(!"8".equals(rBean.getType()) && !"11".equals(rBean.getType()) && !"9".equals(rBean.getType())){//非合同报销
			String url="/reimburse/edit?id="+rBean.getrId()+"&editType=0";
			//查询报销单，获取事前申请的金额， 如果报销金额小于事前申请金额，多出来的冻结金额要归还到可用金额
			ApplicationBasicInfo basicInfo=applyMng.findByCode(rBean.getgCode());
				if(rBean.getAmount()>=basicInfo.getAmount()){
					//审批全部通过,修改冻结金额
					cashierMng.deleteDjAmount(rBean.getIndexId(),rBean.getProDetailId(),rBean.getAmount(),rBean.getAmount(),rBean.getDept(),rBean.getUser(),url,rBean.getrCode(),"4");
				}else{
					//审批全部通过,修改冻结金额
					cashierMng.deleteDjAmount(rBean.getIndexId(),rBean.getProDetailId(),rBean.getAmount(),basicInfo.getAmount(),rBean.getDept(),rBean.getUser(),url,rBean.getrCode(),"4");
				}
		}else{
			//合同报销
			if("8".equals(rBean.getType())){
				String url="/reimburse/edit?id="+rBean.getrId()+"&editType=0";
				cashierMng.deleteDjAmount(rBean.getIndexId(),rBean.getProDetailId(),rBean.getAmount(),rBean.getAmount(),rBean.getDept(),rBean.getUser(),url,rBean.getrCode(),"4");
				if(!"".equals(rBean.getPayId())){
					//付款计划
					ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(rBean.getPayId()));
					ReceivPlan receivPlan = new ReceivPlan();
					if("1".equals(cbi.getfUpdateStatus())){//有变更
						Upt upt = uptMng.findByFContId_U(String.valueOf(cbi.getFcId())).get(0);
						if(upt.getfPlanChangeStatus()==1){
							receivPlan.setDataType(1);
							receivPlan.setfUptId_R(upt.getfId_U());
						}
					}else{//无变更
						receivPlan.setfContId_R(cbi.getFcId());
						receivPlan.setDataType(0);
					}
					String[] a = rBean.getReceivplanid().split(",");
					for (int h = 0; h < a.length; h++) {
						ReceivPlan receivPlan2 = receivPlanMng.findById(Integer.valueOf(a[h]));
						receivPlan2.setfStauts_R("1");
						receivPlanMng.saveOrUpdate(receivPlan2);
					}
				}
			}
			if("11".equals(rBean.getType())){
				//撤回时  设置质保金是否退还    0:未退还     1:已退还       2:退还中
				if(rBean.getContCode().contains("HT")){
					ContractBasicInfo basicInfo = formulationMng.findById(Integer.valueOf(rBean.getPayId()));
					basicInfo.setfPayStauts("1");
					formulationMng.saveOrUpdate(basicInfo);
				}else{
					Upt upt = uptMng.findById(Integer.valueOf(rBean.getPayId()));
					upt.setfPayStauts("1");
					uptMng.saveOrUpdate(upt);
				}
				
			}
		}
		rBean.setCashierType("1");
		rBean.setCashierTime(new Date());
		super.updateDefault(rBean);
		String msg = "您的编号："+rBean.getrCode()+"的申请报销单据已经支付完成，请查收";
		privateInforMng.setMsg("[报销付讫]", msg, rBean.getUserId(), "3");
		personalWorkMng.sendMessageToUser(rBean.getUserId(), 0);
	}

	@Override
	public List<InvoiceCouponInfo> findrId(Integer rId) {
		Finder finder = Finder.create(" FROM InvoiceCouponInfo WHERE rId="+rId);
		return super.find(finder);
	}

	@Override
	public List<ReimbAppliBasicInfo> findByLoanIdCX(String id,String code) {
		Finder finder = Finder.create(" FROM  ReimbAppliBasicInfo WHERE loan.lId="+id+" and stauts!='99' and rCode!='"+code+"' and flowStauts in('1','9') ");
		List<ReimbAppliBasicInfo> list = super.find(finder);
		return list;
	}

	@Override
	public void saveContract(ReimbAppliBasicInfo bean,ReimbPayeeInfo payeeBean,String arry,
			String files, User user, String form1, String payerinfoJson)
			throws Exception {
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		String StrIndex="";
		if (bean.getrId()==null) {
			bean.setrId(shenTongMng.getSeq("reimb_appli_basic_info_seq"));
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			StrIndex="0";
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			StrIndex="1";
		}
		/*//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()));
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()));
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}*/
		bean.setLoan(null);
		bean.setWithLoan(0);
		
		//获得关联合同
		ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(bean.getPayId()));
		bean.setContCode(cbi.getFcCode());
		bean.setFcTitle(cbi.getFcTitle());
		//采购过程登记
		//BiddingRegist br = biddingRegistMng.findById(Integer.valueOf(cbi.getfPurchNo()));
		//采购计划
		PurchaseApplyBasic pab = purchaseApplyMng.findById(Integer.valueOf(cbi.getfPurchNo()));
		
		bean.setIndexType(pab.getIndexType());
		bean.setProDetailId(pab.getProDetailId());
		bean.setIndexId(pab.getIndexId());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), "HTFKSQ", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("HTFKSQ", user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			String taskName=user.getName()+"的"+bean.getFcTitle()+"-合同付款";//申请名称
			taskName = "[合同报销]" + taskName;
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			//为审批页面内容显示的url,需要将数据传入不然无法访问
			if("8".equals(bean.getType())){
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=2");
			}else {
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");
			}
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			/*Boolean b=budgetIndexMgrMng.checkAmount(bean);
			if(b){
				//计算明细申请金额总数
				Double num = bean.getApplyAmount();
				Double nums =bean.getAmount();
				if(nums>num){
					budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
				}
			}else {
				throw new ServiceException("报销金额超出可用金额，不允许提交！");
			}*/
			//修改申请单的报销状态为1（报销中）使其不可再次被选择
			getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()).executeUpdate();
			if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getBiddingName());
				info.setBank(reimbPayeeInfo.getfBankName());
				info.setBankAccount(reimbPayeeInfo.getfCardNo());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				info.setfInnerOrOuter("1");
				super.merge(info);
			}
		}
		
			
			
		//旧发票
		List<InvoiceCouponInfo> oldfp1 = invoiceCouponMng.findByrID(bean.getrId(),"contract-1");
		//新发票
		List<InvoiceCouponInfo> newfp1 =  JSON.parseObject(form1, new TypeReference<List<InvoiceCouponInfo>>(){});
		//旧发票
		//删除旧发票
		for (int h = 0; h < oldfp1.size(); h++) {
			if(!StringUtil.isEmpty(oldfp1.get(h).getFileId())&&!"null".equals(oldfp1.get(h).getFileId())){
				Attachment attachment=attachmentMng.findById(oldfp1.get(h).getFileId());
				if(attachment!=null){
					attachment.setFlag("0");
					attachment.setUpdator(user);
					attachment.setUpdateTime(new Date());
					attachmentMng.updateDefault(attachment);
				}
			}
			invoiceCouponMng.delete(oldfp1.get(h));
		}
		//新增发票
		for (int i = 0; i < newfp1.size(); i++) {
			InvoiceCouponInfo newrd = newfp1.get(i);
			newrd.setIcId(shenTongMng.getSeq("invoice_coupon_info"));
			newrd.setrId(bean.getrId());
			newrd.setfDataType("contract-1");
			//保存新的明细
			super.merge(newrd);
		}	
		
		
	}
	
	public void saveAppCommon(ReimbAppliBasicInfo bean, String fpIds,
			String mingxi, String files, User user, String form1,
			String payerinfoJson)throws Exception {
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		
		String StrIndex="";
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			StrIndex="0";
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			StrIndex="1";
		}
		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			if("1".equals(loanBasicInfo.getPayflowStauts()) || "9".equals(loanBasicInfo.getPayflowStauts())){
				throw new ServiceException("该借款单正在还款审批中！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}

		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setIndexType(applyBean.getIndexType());
		bean.setProDetailId(applyBean.getProDetailId());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核

			/*Integer flowId =0;
					User nextUser = new User();
						nextUser = userMng.findById(bean.getFuserId());*/

			Integer flowId =0;
			User nextUser = new User();
			//String ywlx =getType(applyBean.getType());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("FLFSQ".equals(applyBean.getCommonType())){
				strType = "TYSXFLBX";
			}
			if("SPPSSQ".equals(applyBean.getCommonType())){
				strType = "TYSXSPPSBX";
			}
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			if(StringUtil.isEmpty(nextUser.getId())){
				throw new ServiceException("请选择下级审批人");
			}else {
				work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
				work.setTaskId(bean.getrId());//申请单ID
				work.setTaskCode(bean.getrCode());//为申请单的单号
			}

			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
				taskName = "[通用事项报销]" + taskName + "[超额报销]";
			}else{
				taskName = "[通用事项报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			if("1".equals(bean.getType())) {
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1&applyType=tysx");//为审批页面内容显示的url,需要将数据传入不然无法访问
				work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0&applyType=tysx");//查看url
			}else{
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
				work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			}
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			if("1".equals(bean.getType())) {
				work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1&applyType=tysx");//退回修改url
				work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0&applyType=tysx");//查看url
			}else{
				work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
				work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			}
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			Boolean b=budgetIndexMgrMng.checkAmount(bean);
			if(b){
				//计算明细申请金额总数
				Double num = bean.getApplyAmount();
				Double nums =bean.getAmount();
				if(nums>num){
					budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
				}
			}else {
				throw new ServiceException("报销金额超出可用金额，不允许提交！");
			}

			//修改申请单的报销状态为1（报销中）使其不可再次被选择
			getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();

		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		//删除数据库中   申请中的伙食补助费
		getSession().createSQLQuery("delete from T_APPLI_DETAIL where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(mingxi)){
			//获取城内交通费的Json对象
			List<ApplicationDetail> foodAllowanceList = JSON.parseObject("["+mingxi.toString()+"]",new TypeReference<List<ApplicationDetail>>(){});
			for (ApplicationDetail applicationDetail: foodAllowanceList) {
				ApplicationDetail info = new ApplicationDetail();
				info.setrId(bean.getrId());
				info.setCostDetail(applicationDetail.getCostDetail());
				info.setRemibAmount(applicationDetail.getRemibAmount());
				info.setRemark(applicationDetail.getRemark());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()).executeUpdate();
			if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString()+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getPayeeName());
				info.setBank(reimbPayeeInfo.getBank());
				info.setBankAccount(reimbPayeeInfo.getBankAccount());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				super.merge(info);
			}
		}
		
			//取消之前关联的发票
			List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
			if(oldInvoList!=null&&oldInvoList.size()>0){
				for (AppInvoiceInfo oldInvo : oldInvoList) {
					oldInvo.setfRId(null);
					oldInvo.setfInvoiceStatus("0");
				}
			}
			//关联发票
			if (!StringUtil.isEmpty(fpIds) && bean != null) {
				fpIds = fpIds.substring(0,fpIds.length() - 1);
				String[] ids = fpIds.split(",");
				if (ids.length > 0) {
					for (String id: ids) {
						AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
						if (invo != null ) {
							invo.setfRId(bean.getrId());
							invo.setfInvoiceStatus("1");
							invo.setReimbType("1");
							super.saveOrUpdate(invo);
						}
					}
				}
			}
	}
	
	public void saveAppCar(ReimbAppliBasicInfo bean, String fpIds,
			String mingxi, String files, User user, 
			String payerinfoJson) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		bean.setrCode(bean.getgCode());//申请单的code设置成报销单code
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		String StrIndex="";
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			StrIndex="0";
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			StrIndex="1";
		}


		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核

			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号




			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
				taskName = "[公车运维报销]" + taskName + "[超额报销]";
			}else{
				taskName = "[公车运维报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);

			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}

		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();

		//保存附件信息
		attachmentMng.joinEntity(bean,files);


		//删除数据库中   报销中的费用明细
		getSession().createSQLQuery("delete from T_OFFICE_CAR where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获取费用明细的Json对象
		List<OfficeCar> officeCar = JSON.parseObject("["+mingxi.toString()+"]",new TypeReference<List<OfficeCar>>(){});
		for (int i = 0; i < officeCar.size(); i++) {
			OfficeCar car = new OfficeCar();
			car.setrId(bean.getrId());
			car.setfExpenseName(officeCar.get(i).getfExpenseName());
			car.setfUseType(officeCar.get(i).getfUseType());
			car.setfCarNum(officeCar.get(i).getfCarNum());
			car.setfCarType(officeCar.get(i).getfCarType());
			car.setfUseAmount(officeCar.get(i).getfUseAmount());
			car.setfRemark(officeCar.get(i).getfRemark());
			car.setfStatus(1);
			officeCarMng.saveOrUpdate(car);
		}
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+"").executeUpdate();

		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString()+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
				payeeBean.setrId(bean.getrId());//关联申请报销单
				payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				//保存或修改个人收款信息
				paymentMethodInfoMng.saveInfo(payeeBean, user);
				//保存收款人信息
				super.merge(payeeBean);
			}	
		}
		//取消之前关联的发票
		List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
		if(oldInvoList!=null&&oldInvoList.size()>0){
			for (AppInvoiceInfo oldInvo : oldInvoList) {
				oldInvo.setfRId(null);
				oldInvo.setfInvoiceStatus("0");
			}
		}
		//关联发票
		if (!StringUtil.isEmpty(fpIds) && bean != null) {
			fpIds = fpIds.substring(0,fpIds.length() - 1);
			String[] ids = fpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						super.saveOrUpdate(invo);
					}
				}
			}
		}

	}

	public void saveAppMeeting(ReimbAppliBasicInfo bean,
			MeetingAppliInfo meetingBean, String fpIds, String mingxi,
			String files, User user, String meetPlan, String payerinfoJson) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		Boolean isFirst;
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			isFirst=true; 
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			isFirst=false; 
		}


		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核

			Integer flowId =0;
			User nextUser = new User();
			//String ywlx =getType(applyBean.getType());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
				taskName = "[会议报销]" + taskName + "[超额报销]";
			}else{
				taskName = "[会议报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);

			//计算明细申请金额总数
			synchronized (this) {
				Boolean b=budgetIndexMgrMng.checkAmount(bean);
				if(b){
					Double num = bean.getApplyAmount();
					Double nums =bean.getAmount();
					if(nums>num){
						budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
					}
				}
			}

		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();

		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		//保存修改后会议内容
		if(meetingBean != null) {
			if (meetingBean.getmId()==null) {
				//创建人、创建时间、发布时间
				meetingBean.setCreator(user.getAccountNo());
				meetingBean.setCreateTime(d);
			} else {
				//修改人、修改时间
				meetingBean.setUpdator(user.getAccountNo());
				meetingBean.setUpdateTime(d);
			}
			meetingBean.setrId(bean.getrId());
			meetingBean = (MeetingAppliInfo) super.saveOrUpdate(meetingBean);
		}
		/**保存调整后会议日程*/
		/** 保存明细信息 **/
		//删除数据库中   申请中的会议日程信息
		getSession().createSQLQuery("delete from T_MEETING_PLAN where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获得新的会议日程信息
		if(!StringUtil.isEmpty(meetPlan)){
			//获取会议日程信息的Json对象
			List<MeetingPlan> meet = JSON.parseObject("["+meetPlan.toString()+"]",new TypeReference<List<MeetingPlan>>(){});
			for (MeetingPlan meetingPlan : meet) {
				MeetingPlan info = new MeetingPlan();
				info.setrId(bean.getrId());
				info.setTimes(meetingPlan.getTimes());
				info.setTimee(meetingPlan.getTimee());
				info.setContent(meetingPlan.getContent());
				super.merge(info);
			}
		}
		//删除数据库中   申请中的费用明细信息
		getSession().createSQLQuery("delete from T_APPLI_DETAIL where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获得新的费用明细信息
		if(!StringUtil.isEmpty(mingxi)){
			//获取费用明细信息的Json对象
			List<ApplicationDetail> app = JSON.parseObject("["+mingxi.toString()+"]",new TypeReference<List<ApplicationDetail>>(){});
			for (ApplicationDetail applicationDetail : app) {
				ApplicationDetail info = new ApplicationDetail();
				info.setrId(bean.getrId());
				info.setCostDetail(applicationDetail.getCostDetail());
				info.setStandard(applicationDetail.getStandard());
				info.setTotalStandard(applicationDetail.getTotalStandard());
				info.setRemibAmount(applicationDetail.getRemibAmount()); 
				info.setfStatus(1);
				super.merge(info);
			}
		}



		/**保存发票**/
		//取消之前关联的发票
		List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
		if(oldInvoList!=null&&oldInvoList.size()>0){
			for (AppInvoiceInfo oldInvo : oldInvoList) {
				oldInvo.setfRId(null);
				oldInvo.setfInvoiceStatus("0");
			}
		}
		//关联发票
		if (!StringUtil.isEmpty(fpIds) && bean != null) {
			fpIds = fpIds.substring(0,fpIds.length() - 1);
			String[] ids = fpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+"").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString()+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
				payeeBean.setrId(bean.getrId());//关联申请报销单
				payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				//保存或修改个人收款信息
				paymentMethodInfoMng.saveInfo(payeeBean, user);
				//保存收款人信息
				super.merge(payeeBean);
			}	
		}
		
		
	}
	
	public void saveAppTrain(ReimbAppliBasicInfo bean,
			TrainingAppliInfo trainingBean, String zhysFpIds,
			String hotelFpIds, String foodFpIds, String traffic1FpIds,
			String traffic2FpIds, String trainLecturer, String trainPlan,
			String zongheJson, String lessonJson, String hotelJson,
			String foodJson, String trafficJson1, String trafficJson2,
			String payerinfoJson, String files, User user) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		Boolean isFirst;
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
			isFirst=true; 
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			isFirst=false; 
		}


		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核

			Integer flowId =0;
			User nextUser = new User();
			//String ywlx =getType(applyBean.getType());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号


			//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());

			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){//报销金额-申请金额>0
				taskName = "[培训报销]" + taskName + "[超额报销]";
			}else{
				taskName = "[培训报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);

			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}

		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();

		//保存附件信息
		attachmentMng.joinEntity(bean,files);

		//保存培训信息
		if(trainingBean != null) {
			if (trainingBean.gettId()==null) {
				//创建人、创建时间、发布时间
				trainingBean.setCreator(user.getAccountNo());
				trainingBean.setCreateTime(d);
			} else {
				//修改人、修改时间
				trainingBean.setUpdator(user.getAccountNo());
				trainingBean.setUpdateTime(d);
			}
			trainingBean.setrId(bean.getrId());
			trainingBean = (TrainingAppliInfo) super.saveOrUpdate(trainingBean);
		}

		/**保存调整后讲师信息*/
		JSONArray array =JSONArray.fromObject("["+trainLecturer.toString()+"]");
		List newlec = (List)JSONArray.toList(array, LecturerInfo.class);
		if(isFirst==false){
			//获得老的信息
			List<Object> oldlec = applyMng.getObjectList("LecturerInfo", "tId", trainingBean.gettId());
			//获取现有的明细
			/*//比较新老明细的不同
			for (int i = oldlec.size()-1; i >= 0; i--) {
				LecturerInfo oldgad = (LecturerInfo) oldlec.get(i);
				for (int j = 0; j < newlec.size(); j++) {
					LecturerInfo gad = (LecturerInfo) newlec.get(j);
					if(gad.getlId()!=null){
						if(gad.getlId()==oldgad.getlId()){
							oldlec.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldlec.size()>0){
				for (int i = 0; i < oldlec.size(); i++) {
					LecturerInfo oldgad = (LecturerInfo) oldlec.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newlec.size(); j++) {
				LecturerInfo gad = (LecturerInfo) newlec.get(j);
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				super.merge(gad);
			}
		}else{
			for (int i = 0; i < newlec.size(); i++) {
				LecturerInfo gad = (LecturerInfo) newlec.get(i);
				LecturerInfo lec =new LecturerInfo();
				BeanUtils.copyProperties(gad,lec);
				lec.setlId(null);
				lec.settId(trainingBean.gettId());
				lec.setCreator(user.getAccountNo());
				lec.setCreateTime(d);
				super.merge(lec);
			}
		}
		//保存培训日程
		//获得新的培训日程信息

		List<MeetPlan> oc = JSON.parseObject("["+trainPlan.toString()+"]",new TypeReference<List<MeetPlan>>(){});
		if(isFirst==false){
			//获得老的信息
			List<Object> oldrp = applyMng.getObjectList("MeetPlan", "tId", trainingBean.gettId());
			/*List<Object> oldrp1 = new ArrayList();
			for (int i = 0; i < oldrp.size(); i++) {
				MeetPlan oldrpi = (MeetPlan) oldrp.get(i);
				for (int j = 0; j < oc.size(); j++) {		
					MeetPlan rpi = (MeetPlan) oc.get(j);
					if(rpi.gettId()!=null){
						if(rpi.gettId()!=oldrpi.gettId()){
							oldrp1.add(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldrp.size()>0){
				for (int i = 0; i < oldrp.size(); i++) {
					MeetPlan oldrpi = (MeetPlan) oldrp.get(i);
					super.delete(oldrpi);
				}
			}

			for (int i = 0; i < oc.size(); i++) {		
				MeetPlan rpi = (MeetPlan) oc.get(i);
				rpi.settId(trainingBean.gettId());
				rpi.setCreator(user.getAccountNo());
				rpi.setCreateTime(d);
				//保存培训日程信息
				super.merge(rpi);
			}
		}else{
			for (int i = 0; i < oc.size(); i++) {		
				MeetPlan rpi = (MeetPlan) oc.get(i);
				MeetPlan plan =new MeetPlan();
				BeanUtils.copyProperties(rpi,plan);
				plan.setPlanId(null);
				plan.settId(trainingBean.gettId());
				plan.setCreator(user.getAccountNo());
				plan.setCreateTime(d);
				//保存会议计划信息
				super.merge(plan);
			}
		}
		//保存综合预算明细
		//获得老的信息
		List<Object> oldmx =applyMng.getMingxi("ApplicationDetail", "rId", bean.getrId());
		//获取现有的明细
		List mx = getMingXiJson(zongheJson, ApplicationDetail.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldmx.size()-1; i >= 0; i--) {
				ApplicationDetail oldgad = (ApplicationDetail) oldmx.get(i);
				for (int j = 0; j < mx.size(); j++) {
					ApplicationDetail gad = (ApplicationDetail) mx.get(j);
					if(gad.getcId()!=null){
						if(gad.getcId()==oldgad.getcId()){
							oldmx.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldmx.size()>0){
				for (int i = 0; i < oldmx.size(); i++) {
					ApplicationDetail oldgad = (ApplicationDetail) oldmx.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < mx.size(); j++) {
				ApplicationDetail gad = (ApplicationDetail) mx.get(j);
				gad.setrId(bean.getrId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < mx.size(); j++) {
				ApplicationDetail gad = (ApplicationDetail) mx.get(j);
				ApplicationDetail newMingxi =new ApplicationDetail();
				BeanUtils.copyProperties(gad,newMingxi);
				newMingxi.setcId(null);
				newMingxi.setgId(null);
				newMingxi.setrId(bean.getrId());
				newMingxi.setCreator(user.getAccountNo());
				newMingxi.setCreateTime(d);
				super.merge(newMingxi);
			}
		}
		//保存讲课费
		//获得老的信息
		List<TrainTeacherCost> oldlesson = applyMng.getTeacherMingxi(trainingBean.gettId(),"lesson");
		//获取现有的明细
		JSONArray array1 =JSONArray.fromObject("["+lessonJson.toString()+"]");
		List newlesson = (List)JSONArray.toList(array1, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldlesson.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldlesson.get(i);
				for (int j = 0; j < newlesson.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldlesson.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldlesson.size()>0){
				for (int i = 0; i < oldlesson.size(); i++) {
					TrainTeacherCost oldgad = oldlesson.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newlesson.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("lesson");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newlesson.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newlesson.get(j);
				TrainTeacherCost lesson =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,lesson);
				lesson.setThId(null);
				lesson.settId(trainingBean.gettId());
				lesson.setCreator(user.getAccountNo());
				lesson.setCreateTime(d);
				lesson.setCostType("lesson");
				super.merge(lesson);
			}
		}
		//保存住宿费
		//获得老的信息
		List<TrainTeacherCost> oldhotel = applyMng.getTeacherMingxi(trainingBean.gettId(),"hotel");
		//获取现有的明细
		JSONArray array2 =JSONArray.fromObject("["+hotelJson.toString()+"]");
		List newhotel = (List)JSONArray.toList(array2, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldhotel.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldhotel.get(i);
				for (int j = 0; j < newhotel.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldhotel.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldhotel.size()>0){
				for (int i = 0; i < oldhotel.size(); i++) {
					TrainTeacherCost oldgad = oldhotel.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newhotel.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("hotel");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newhotel.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newhotel.get(j);
				TrainTeacherCost hotel =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,hotel);
				hotel.setThId(null);
				hotel.settId(trainingBean.gettId());
				hotel.setCreator(user.getAccountNo());
				hotel.setCreateTime(d);
				hotel.setCostType("hotel");
				super.merge(hotel);
			}
		}
		//保存伙食费
		//获得老的信息
		List<TrainTeacherCost> oldfood = applyMng.getTeacherMingxi(trainingBean.gettId(),"food");
		//获取现有的明细
		JSONArray array3 =JSONArray.fromObject("["+foodJson.toString()+"]");
		List newfood = (List)JSONArray.toList(array3, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldfood.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldfood.get(i);
				for (int j = 0; j < newfood.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldfood.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldfood.size()>0){
				for (int i = 0; i < oldfood.size(); i++) {
					TrainTeacherCost oldgad = oldfood.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newfood.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("food");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newfood.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newfood.get(j);
				TrainTeacherCost food =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,food);
				food.setThId(null);
				food.settId(trainingBean.gettId());
				food.setCreator(user.getAccountNo());
				food.setCreateTime(d);
				food.setCostType("food");
				super.merge(food);
			}
		}
		//保存城市间交通费
		//获得老的信息
		List<TrainTeacherCost> oldtraffic1 = applyMng.getTeacherMingxi(trainingBean.gettId(),"traffic1");
		//获取现有的明细
		JSONArray array4 =JSONArray.fromObject("["+trafficJson1.toString()+"]");
		List newtraffic1 = (List)JSONArray.toList(array4, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldtraffic1.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldtraffic1.get(i);
				for (int j = 0; j < newtraffic1.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldtraffic1.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldtraffic1.size()>0){
				for (int i = 0; i < oldtraffic1.size(); i++) {
					TrainTeacherCost oldgad = oldtraffic1.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newtraffic1.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("traffic1");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newtraffic1.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic1.get(j);
				TrainTeacherCost traffic1 =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,traffic1);
				traffic1.setThId(null);
				traffic1.settId(trainingBean.gettId());
				traffic1.setCreator(user.getAccountNo());
				traffic1.setCreateTime(d);
				traffic1.setCostType("traffic1");
				super.merge(traffic1);
			}
		}
		//保存市内交通费
		//获得老的信息
		List<TrainTeacherCost> oldtraffic2 = applyMng.getTeacherMingxi(trainingBean.gettId(),"traffic2");
		//获取现有的明细
		JSONArray array5 =JSONArray.fromObject("["+trafficJson2.toString()+"]");
		List newtraffic2 = (List)JSONArray.toList(array5, TrainTeacherCost.class);
		if(isFirst==false){
			/*//比较新老明细的不同
			for (int i = oldtraffic2.size()-1; i >= 0; i--) {
				TrainTeacherCost oldgad =  oldtraffic2.get(i);
				for (int j = 0; j < newtraffic2.size(); j++) {
					TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
					if(gad.getThId()!=null){
						if(gad.getThId()==oldgad.getThId()){
							oldtraffic2.remove(i);
						}
					}
				}
			}*/
			//删除在新明细中没有的老明细
			if(oldtraffic2.size()>0){
				for (int i = 0; i < oldtraffic2.size(); i++) {
					TrainTeacherCost oldgad = oldtraffic2.get(i);
					super.delete(oldgad);
				}
			}
			//保存新的明细
			for (int j = 0; j < newtraffic2.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
				gad.settId(trainingBean.gettId());
				gad.setCreator(user.getAccountNo());
				gad.setCreateTime(d);
				gad.setCostType("traffic2");
				super.merge(gad);
			}
		}else{
			for (int j = 0; j < newtraffic2.size(); j++) {
				TrainTeacherCost gad = (TrainTeacherCost) newtraffic2.get(j);
				TrainTeacherCost traffic2 =new TrainTeacherCost();
				BeanUtils.copyProperties(gad,traffic2);
				traffic2.setThId(null);
				traffic2.settId(trainingBean.gettId());
				traffic2.setCreator(user.getAccountNo());
				traffic2.setCreateTime(d);
				traffic2.setCostType("traffic2");
				super.merge(traffic2);
			}
		}
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+"").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
				payeeBean.setrId(bean.getrId());//关联申请报销单
				payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				//保存或修改个人收款信息
				paymentMethodInfoMng.saveInfo(payeeBean, user);
				//保存收款人信息
				super.merge(payeeBean);
			}	
		}
		/**保存发票**/
		//取消之前关联的发票
		List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
		if(oldInvoList!=null&&oldInvoList.size()>0){
			for (AppInvoiceInfo oldInvo : oldInvoList) {
				oldInvo.setfRId(null);
				oldInvo.setfInvoiceStatus("0");
			}
		}
		//关联发票-综合预算
		if (!StringUtil.isEmpty(zhysFpIds) && bean != null) {
			zhysFpIds = zhysFpIds.substring(0,zhysFpIds.length() - 1);
			String[] ids = zhysFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("zhys");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-住宿费
		if (!StringUtil.isEmpty(hotelFpIds) && bean != null) {
			hotelFpIds = hotelFpIds.substring(0,hotelFpIds.length() - 1);
			String[] ids = hotelFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("hotel");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-伙食费
		if (!StringUtil.isEmpty(foodFpIds) && bean != null) {
			foodFpIds = foodFpIds.substring(0,foodFpIds.length() - 1);
			String[] ids = foodFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("food");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-城市间交通费
		if (!StringUtil.isEmpty(traffic1FpIds) && bean != null) {
			traffic1FpIds = traffic1FpIds.substring(0,traffic1FpIds.length() - 1);
			String[] ids = traffic1FpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("traffic1");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-市内交通费
		if (!StringUtil.isEmpty(traffic2FpIds) && bean != null) {
			traffic2FpIds = traffic2FpIds.substring(0,traffic2FpIds.length() - 1);
			String[] ids = traffic2FpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("traffic2");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
	}
	
	
	public void saveAppReception(ReimbAppliBasicInfo bean, String hotelFpIds,
			String foodFpIds, String trafficFpIds, String rentFpIds,
			String otherFpIds, String files, User user,
			ReceptionAppliInfo receptionAppliInfo, String hotelJson,
			String foodJson, String otherJson, String payerinfoJson) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称

		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}


		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		//String ywlx =getType(applyBean.getType());
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核

			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号


			//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());

			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
				taskName = "[公务接待报销]" + taskName + "[超额报销]";
			}else{
				taskName = "[公务接待报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);

			synchronized (this) {
				Boolean b=budgetIndexMgrMng.checkAmount(bean);
				if(b){
					//计算明细申请金额总数
					Double num = bean.getApplyAmount();
					Double nums =bean.getAmount();
					if(nums>num){
						budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
					}
				}
			}

		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存修改后接待内容
		if(receptionAppliInfo != null) {
			if (receptionAppliInfo.getjId()==null) {
				//创建人、创建时间、发布时间
				receptionAppliInfo.setCreator(user.getAccountNo());
				receptionAppliInfo.setCreateTime(d);
			} else {
				//修改人、修改时间
				receptionAppliInfo.setUpdator(user.getAccountNo());
				receptionAppliInfo.setUpdateTime(d);
			}
			receptionAppliInfo.setrId(bean.getrId());
			receptionAppliInfo =(ReceptionAppliInfo) super.saveOrUpdate(receptionAppliInfo);
		}

		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();

		//保存附件信息
		attachmentMng.joinEntity(bean,files);

		//删除数据库中   报销中的费用明细中的住宿费明细
		getSession().createSQLQuery("delete from T_RECEPTION_HOTEL where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取费用明细的Json对象
		if(!StringUtil.isEmpty(hotelJson)){
			List<ReceptionHotel> hotelList = JSON.parseObject("["+hotelJson.toString()+"]",new TypeReference<List<ReceptionHotel>>(){});
			for (int i = 0; i < hotelList.size(); i++) {
				ReceptionHotel hotel = new ReceptionHotel();
				hotel.setrId(bean.getrId());
				hotel.setfName(hotelList.get(i).getfName());
				hotel.setPosition(hotelList.get(i).getPosition());
				hotel.setfRoomType(hotelList.get(i).getfRoomType());
				hotel.setfDays(hotelList.get(i).getfDays());
				hotel.setfCostHotel(hotelList.get(i).getfCostHotel());
				super.saveOrUpdate(hotel);
			}
		}
		//删除数据库中   报销中的费用明细中的餐费明细
		getSession().createSQLQuery("delete from T_RECEPTION_FOOD where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取费用明细的Json对象
		if(!StringUtil.isEmpty(foodJson)){
			List<ReceptionFood> foodList = JSON.parseObject("["+foodJson.toString()+"]",new TypeReference<List<ReceptionFood>>(){});
			for (int i = 0; i < foodList.size(); i++) {
				ReceptionFood food = new ReceptionFood();
				food.setrId(bean.getrId());
				food.setfFoodType(foodList.get(i).getfFoodType());
				food.setfCostStd(foodList.get(i).getfCostStd());
				food.setfPersonNum(foodList.get(i).getfPersonNum());
				food.setfCostFood(foodList.get(i).getfCostFood());
				super.saveOrUpdate(food);
			}
		}
		//删除数据库中   报销中的费用明细中的其他费用明细
		getSession().createSQLQuery("delete from T_RECEPTION_OTEHR where F_R_ID ="+bean.getrId()+"").executeUpdate();
		//获取费用明细的Json对象
		if(!StringUtil.isEmpty(otherJson)){
			List<ReceptionOther> otherList = JSON.parseObject("["+otherJson.toString()+"]",new TypeReference<List<ReceptionOther>>(){});
			for (int i = 0; i < otherList.size(); i++) {
				ReceptionOther other = new ReceptionOther();
				other.setrId(bean.getrId());
				other.setfCostName(otherList.get(i).getfCostName());
				other.setfCost(otherList.get(i).getfCost());
				other.setfRemark(otherList.get(i).getfRemark());
				super.saveOrUpdate(other);
			}
		}
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+"").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString()+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
				payeeBean.setrId(bean.getrId());//关联申请报销单
				payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				//保存或修改个人收款信息
				paymentMethodInfoMng.saveInfo(payeeBean, user);
				//保存收款人信息
				super.merge(payeeBean);
			}	
		}
		/**保存发票**/
		//取消之前关联的发票
		List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
		if(oldInvoList!=null&&oldInvoList.size()>0){
			for (AppInvoiceInfo oldInvo : oldInvoList) {
				oldInvo.setfRId(null);
				oldInvo.setfInvoiceStatus("0");
			}
		}
		//关联发票-住宿费
		if (!StringUtil.isEmpty(hotelFpIds) && bean != null) {
			hotelFpIds = hotelFpIds.substring(0,hotelFpIds.length() - 1);
			String[] ids = hotelFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("hotel");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-伙食费
		if (!StringUtil.isEmpty(foodFpIds) && bean != null) {
			foodFpIds = foodFpIds.substring(0,foodFpIds.length() - 1);
			String[] ids = foodFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("food");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-交通费
		if (!StringUtil.isEmpty(trafficFpIds) && bean != null) {
			trafficFpIds = trafficFpIds.substring(0,trafficFpIds.length() - 1);
			String[] ids = trafficFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("traffic");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-会议室租金
		if (!StringUtil.isEmpty(rentFpIds) && bean != null) {
			rentFpIds = rentFpIds.substring(0,rentFpIds.length() - 1);
			String[] ids = rentFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("rent");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-其他费用
		if (!StringUtil.isEmpty(otherFpIds) && bean != null) {
			otherFpIds = otherFpIds.substring(0,otherFpIds.length() - 1);
			String[] ids = otherFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("other");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
	}
	
	public void saveAppAbroad(ReimbAppliBasicInfo bean, String payerinfoJson,
			String travelFpIds, String hotelFpIds, String feteFpIds,
			String traffic1FpIds, String otherFpIds, String travelJson,
			String trafficJson1, String hotelJson, String foodJson,
			String feeJson, String feteJson, String otherJson,
			String abroadPlanJson, AbroadAppliInfo abroadBean, String files,
			User user) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		

		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
				if("1".equals(String.valueOf(bean.getWithLoan()))){
					List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
					List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
					List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
					Double totalCxAmount =0.00;
					if(applyReimbList !=null && applyReimbList.size()>0){
						for(int i = 0;i < applyReimbList.size();i++){
							if(applyReimbList.get(i).getCxAmount() !=null){
								totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
							}else{
								totalCxAmount =0+totalCxAmount;
							}
							ReimCXInfo info = new ReimCXInfo();
							info.setrId(applyReimbList.get(i).getrId());
							info.setgName(applyReimbList.get(i).getgName());
							info.setType(1);
							info.setCxAmount(applyReimbList.get(i).getCxAmount());
							info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
							reimbList.add(info);
						}
					}
					if(directlyList !=null && directlyList.size()>0){
						for(int i = 0;i < directlyList.size();i++){
							if(directlyList.get(i).getCxAmount() !=null){
								totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
							}else{
								totalCxAmount =0+totalCxAmount;
							}
							ReimCXInfo info = new ReimCXInfo();
							info.setrId(directlyList.get(i).getDrId());
							info.setgName(directlyList.get(i).getSummary());
							info.setType(0);
							info.setCxAmount(directlyList.get(i).getCxAmount());
							info.setReimburseReqTime(directlyList.get(i).getReqTime());
							reimbList.add(info);
						}
					}
					LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
					if(totalCxAmount>loanBasicInfo.getlAmount()){
						throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
					}
					Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
					if(CE<bean.getCxAmount()){
						throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
					}
					bean.setWithLoan(1);
				}else{
					bean.setLoan(null);
					bean.setWithLoan(0);
				}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(),strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			
			
//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
			
			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
					taskName = "[公务出国报销]" + taskName + "[超额报销]";
			}else{
					taskName = "[公务出国报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		
		
		if (abroadBean.getFaId()==null) {
			//创建人、创建时间、发布时间
			abroadBean.setCreator(user.getAccountNo());
			abroadBean.setCreateTime(d);
		} else {
			//修改人、修改时间
			abroadBean.setUpdator(user.getAccountNo());
			abroadBean.setUpdateTime(d);
		}
		abroadBean.setrId(bean.getrId());
		abroadBean = (AbroadAppliInfo) super.merge(abroadBean);
		
		
		
		
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		//删除数据库中   申请中的出访计划信息
		getSession().createSQLQuery("delete from t_abroad_plan where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获得新的出访计划信息
		if(!StringUtil.isEmpty(abroadPlanJson)){
			//获取出访计划信息的Json对象
			List<AbroadPlan> rp = JSON.parseObject("["+abroadPlanJson.toString()+"]",new TypeReference<List<AbroadPlan>>(){});
			for (AbroadPlan abroadPlan : rp) {
				AbroadPlan info = new AbroadPlan();
				info.setrId(bean.getrId());
				info.setAbroadDate(abroadPlan.getAbroadDate());
				info.setTimeEnd(abroadPlan.getTimeEnd());
				info.setArriveCountryId(abroadPlan.getArriveCountryId());
				info.setArriveCountry(abroadPlan.getArriveCountry());
				info.setArriveCity(abroadPlan.getArriveCity());
				info.setRemark(abroadPlan.getRemark());
				info.setStatus(1);
				super.merge(info);
			}
		}
		
		
		//删除数据库中   申请中的国际旅费
		getSession().createSQLQuery("delete from T_INTERNATIONAL_TRAVELING_EXPENSE where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		//获得新的国际旅费
		if(!StringUtil.isEmpty(travelJson)){
			//获取国际旅费的Json对象
			List<InternationalTravelingExpense> rp = JSON.parseObject("["+travelJson.toString()+"]",new TypeReference<List<InternationalTravelingExpense>>(){});
			for (InternationalTravelingExpense internationalTravelingExpense : rp) {
				InternationalTravelingExpense info = new InternationalTravelingExpense();
				info.setrId(bean.getrId());
				info.setTimeStart(internationalTravelingExpense.getTimeStart());
				info.setTimeEnd(internationalTravelingExpense.getTimeEnd());
				info.setStartCity(internationalTravelingExpense.getStartCity());
				info.setArriveCity(internationalTravelingExpense.getArriveCity());
				info.setVehicle(internationalTravelingExpense.getVehicle());
				info.setApplyAmount(internationalTravelingExpense.getApplyAmount());
				info.setTrainSubsidies(internationalTravelingExpense.getTrainSubsidies());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		
		//删除数据库中   申请中的城市间交通费
		getSession().createSQLQuery("delete from T_OUTSIDE_TRADDIC_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		//获取出城市间交通费的Json对象
		if(!StringUtil.isEmpty(trafficJson1)){
			List<OutsideTrafficInfo> outside = JSON.parseObject("["+trafficJson1.toString()+"]",new TypeReference<List<OutsideTrafficInfo>>(){});
			for (OutsideTrafficInfo outsideTrafficInfo : outside) {
				OutsideTrafficInfo info = new OutsideTrafficInfo();
				info.setrId(bean.getrId());
				info.setTravelPersonnel(outsideTrafficInfo.getTravelPersonnel());
				info.setApplyAmount(outsideTrafficInfo.getApplyAmount());
				info.setSts("1");
				super.merge(info);
			}	
		}
		//删除数据库中   申请中的住宿费
		getSession().createSQLQuery("delete from T_HOTEL_EXPENSE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		if(!StringUtil.isEmpty(hotelJson)){
			//获取住宿费的Json对象
			List<HotelExpenseInfo> rp = JSON.parseObject("["+hotelJson.toString()+"]",new TypeReference<List<HotelExpenseInfo>>(){});
			for (HotelExpenseInfo hotelExpenseInfo : rp) {
				HotelExpenseInfo info = new HotelExpenseInfo();
				info.setrId(bean.getrId());
				info.setLocationCity(hotelExpenseInfo.getLocationCity());
				info.setCheckInTime(hotelExpenseInfo.getCheckInTime());
				info.setCheckOUTTime(hotelExpenseInfo.getCheckOUTTime());
				info.setCityId(hotelExpenseInfo.getCityId());
				info.setStandard(hotelExpenseInfo.getStandard());
				info.setHotelDay(hotelExpenseInfo.getHotelDay());
				info.setCountStandard(hotelExpenseInfo.getCountStandard());
				info.setCurrency(hotelExpenseInfo.getCurrency());
				info.setApplyAmount(hotelExpenseInfo.getApplyAmount());
				info.setSts("1");
				super.merge(info);
			}
		}
		
				
		//删除数据库中   申请中的伙食费
		getSession().createSQLQuery("delete from T_FOOD_ALLOWANCE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(foodJson)){
			//获取伙食费的Json对象
			List<FoodAllowanceInfo> rp = JSON.parseObject("["+foodJson.toString()+"]",new TypeReference<List<FoodAllowanceInfo>>(){});
			for (FoodAllowanceInfo foodAllowanceInfo : rp) {
				FoodAllowanceInfo info = new FoodAllowanceInfo();
				info.setrId(bean.getrId());
				info.setfAddress(foodAllowanceInfo.getfAddress());
				info.setStandard(foodAllowanceInfo.getStandard());
				info.setfDays(foodAllowanceInfo.getfDays());
				info.setCountStandard(foodAllowanceInfo.getCountStandard());
				info.setCurrency(foodAllowanceInfo.getCurrency());
				info.setfApplyAmount(foodAllowanceInfo.getfApplyAmount());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//删除数据库中   申请中的公杂费
		getSession().createSQLQuery("delete from T_MISCELLANEOUS_FEE where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(feeJson)){
			//获取公杂费的Json对象
			List<MiscellaneousFeeInfo> rp = JSON.parseObject("["+feeJson.toString()+"]",new TypeReference<List<MiscellaneousFeeInfo>>(){});
			for (MiscellaneousFeeInfo miscellaneousFeeInfo : rp) {
				MiscellaneousFeeInfo info = new MiscellaneousFeeInfo();
				info.setrId(bean.getrId());
				info.setfAddress(miscellaneousFeeInfo.getfAddress());
				info.setStandard(miscellaneousFeeInfo.getStandard());
				info.setfDays(miscellaneousFeeInfo.getfDays());
				info.setCountStandard(miscellaneousFeeInfo.getCountStandard());
				info.setCurrency(miscellaneousFeeInfo.getCurrency());
				info.setfApplyAmount(miscellaneousFeeInfo.getfApplyAmount());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//删除数据库中   申请中的宴请费
		getSession().createSQLQuery("delete from T_FETE_COST_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(feteJson)){
			//获取宴请费的Json对象
			List<FeteCostInfo> rp = JSON.parseObject("["+feteJson.toString()+"]",new TypeReference<List<FeteCostInfo>>(){});
			for (FeteCostInfo feteCostInfo : rp) {
				FeteCostInfo info = new FeteCostInfo();
				info.setrId(bean.getrId());
				info.setfAddress(feteCostInfo.getfAddress());
				info.setfAddressId(feteCostInfo.getfAddressId());
				info.setStandard(feteCostInfo.getStandard());
				info.setfFeeNum(feteCostInfo.getfFeeNum());
				info.setfAccompanyNum(feteCostInfo.getfAccompanyNum());
				info.setCountStandard(feteCostInfo.getCountStandard());
				info.setCurrency(feteCostInfo.getCurrency());
				info.setfApplyAmount(feteCostInfo.getfApplyAmount());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		
		//删除数据库中   申请中的其他费
		getSession().createSQLQuery("delete from T_RECEPTION_OTEHR where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(otherJson)){
			//获取其他费的Json对象
			List<ReceptionOther> rp = JSON.parseObject("["+otherJson.toString()+"]",new TypeReference<List<ReceptionOther>>(){});
			for (ReceptionOther receptionOther : rp) {
				ReceptionOther info = new ReceptionOther();
				info.setrId(bean.getrId());
				info.setfCostName(receptionOther.getfCostName());
				info.setfCost(receptionOther.getfCost());
				info.setfRemark(receptionOther.getfRemark());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//保存收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+"").executeUpdate();
		
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJson)){
				List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString()+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
				for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
					ReimbPayeeInfo payeeBean = new ReimbPayeeInfo();
					payeeBean.setrId(bean.getrId());//关联申请报销单
					payeeBean.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
					payeeBean.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
					payeeBean.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
					payeeBean.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
					//保存或修改个人收款信息
					paymentMethodInfoMng.saveInfo(payeeBean, user);
					//保存收款人信息
					super.merge(payeeBean);
				}	
		}
		/**保存发票**/
		//取消之前关联的发票
		List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
		if(oldInvoList!=null&&oldInvoList.size()>0){
			for (AppInvoiceInfo oldInvo : oldInvoList) {
				oldInvo.setfRId(null);
				oldInvo.setfInvoiceStatus("0");
			}
		}
		//关联发票-住宿费
		if (!StringUtil.isEmpty(hotelFpIds) && bean != null) {
			hotelFpIds = hotelFpIds.substring(0,hotelFpIds.length() - 1);
			String[] ids = hotelFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("hotel");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-宴请费
		if (!StringUtil.isEmpty(feteFpIds) && bean != null) {
			feteFpIds = feteFpIds.substring(0,feteFpIds.length() - 1);
			String[] ids = feteFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("fete");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-交通费
		if (!StringUtil.isEmpty(traffic1FpIds) && bean != null) {
			traffic1FpIds = traffic1FpIds.substring(0,traffic1FpIds.length() - 1);
			String[] ids = traffic1FpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("traffic");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-国际旅费
		if (!StringUtil.isEmpty(travelFpIds) && bean != null) {
			travelFpIds = travelFpIds.substring(0,travelFpIds.length() - 1);
			String[] ids = travelFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("travel");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-其他费用
		if (!StringUtil.isEmpty(otherFpIds) && bean != null) {
			otherFpIds = otherFpIds.substring(0,otherFpIds.length() - 1);
			String[] ids = otherFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("other");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
	}

	public void saveAppContract(ReimbAppliBasicInfo bean, String files,
			User user, String fpIds, String payerinfoJson) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		String StrIndex="";
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			StrIndex="0";
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			StrIndex="1";
		}

		bean.setLoan(null);
		bean.setWithLoan(0);
		
		//获得关联合同
		ContractBasicInfo cbi = formulationMng.findById(Integer.valueOf(bean.getPayId()));
		bean.setContCode(cbi.getFcCode());
		bean.setFcTitle(cbi.getFcTitle());
		//采购过程登记
		//BiddingRegist br = biddingRegistMng.findById(Integer.valueOf(cbi.getfPurchNo()));
		//采购计划
		PurchaseApplyBasic pab = purchaseApplyMng.findById(Integer.valueOf(cbi.getfPurchNo()));
		
		bean.setIndexType(pab.getIndexType());
		bean.setProDetailId(pab.getProDetailId());
		bean.setIndexId(pab.getIndexId());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), "HTFKSQ", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("HTFKSQ", user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			String taskName=user.getName()+"的"+bean.getFcTitle()+"-合同付款";//申请名称
			taskName = "[合同报销]" + taskName;
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			//为审批页面内容显示的url,需要将数据传入不然无法访问
			if("8".equals(bean.getType())){
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=2");
			}else {
				work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");
			}
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			/*Boolean b=budgetIndexMgrMng.checkAmount(bean);
			if(b){
				//计算明细申请金额总数
				Double num = bean.getApplyAmount();
				Double nums =bean.getAmount();
				if(nums>num){
					budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
				}
			}else {
				throw new ServiceException("报销金额超出可用金额，不允许提交！");
			}*/
			//修改申请单的报销状态为1（报销中）使其不可再次被选择
			getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		
		
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()).executeUpdate();
			if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject(payerinfoJson.toString(),new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getBiddingName());
				info.setBank(reimbPayeeInfo.getfBankName());
				info.setBankAccount(reimbPayeeInfo.getfCardNo());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				super.merge(info);
			}
		}



		/**保存发票**/
		//取消之前关联的发票
		List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
		if(oldInvoList!=null&&oldInvoList.size()>0){
			for (AppInvoiceInfo oldInvo : oldInvoList) {
				oldInvo.setfRId(null);
				oldInvo.setfInvoiceStatus("0");
			}
		}
		//关联发票
		if (!StringUtil.isEmpty(fpIds) && bean != null) {
			fpIds = fpIds.substring(0,fpIds.length() - 1);
			String[] ids = fpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						super.saveOrUpdate(invo);
					}
				}
			}
		}

	}
	
	public void saveAppTravel(ReimbAppliBasicInfo bean, String files,
			User user, String trafficFpIds, String hotelFpIds,
			String payerinfoJson, String travelPeop, String outsideTraffic,
			String inCity, String hotelJson, String foodJson) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");//申请人所属部门名称
		if (bean.getrId()==null) {
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setrCode(bean.getgCode());	//申请报销的编码==事前申请的编码
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}


		//如果需要冲销  判断冲销金额是否超出借款金额和已经在执行的（剩余金额-冲销金）
		if("1".equals(String.valueOf(bean.getWithLoan()))){
			List<DirectlyReimbAppliBasicInfo> directlyList = directlyReimbMng.findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimbAppliBasicInfo> applyReimbList =findByLoanIdCX(String.valueOf(bean.getLoan().getlId()),bean.getrCode());
			List<ReimCXInfo> reimbList = new ArrayList<ReimCXInfo>();
			Double totalCxAmount =0.00;
			if(applyReimbList !=null && applyReimbList.size()>0){
				for(int i = 0;i < applyReimbList.size();i++){
					if(applyReimbList.get(i).getCxAmount() !=null){
						totalCxAmount =applyReimbList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(applyReimbList.get(i).getrId());
					info.setgName(applyReimbList.get(i).getgName());
					info.setType(1);
					info.setCxAmount(applyReimbList.get(i).getCxAmount());
					info.setReimburseReqTime(applyReimbList.get(i).getReimburseReqTime());
					reimbList.add(info);
				}
			}
			if(directlyList !=null && directlyList.size()>0){
				for(int i = 0;i < directlyList.size();i++){
					if(directlyList.get(i).getCxAmount() !=null){
						totalCxAmount =directlyList.get(i).getCxAmount()+totalCxAmount;
					}else{
						totalCxAmount =0+totalCxAmount;
					}
					ReimCXInfo info = new ReimCXInfo();
					info.setrId(directlyList.get(i).getDrId());
					info.setgName(directlyList.get(i).getSummary());
					info.setType(0);
					info.setCxAmount(directlyList.get(i).getCxAmount());
					info.setReimburseReqTime(directlyList.get(i).getReqTime());
					reimbList.add(info);
				}
			}
			LoanBasicInfo loanBasicInfo = loanMng.findById(bean.getLoan().getlId());
			if(totalCxAmount>loanBasicInfo.getlAmount()){
				throw new ServiceException("累计冲销的金额超出了借款金额请注意查看！");
			}
			Double CE = loanBasicInfo.getlAmount()-totalCxAmount;//获取到借款金额-已冲销金额（包括审核中的和已审核通过的冲销金额）=剩余还款金额
			if(CE<bean.getCxAmount()){
				throw new ServiceException("冲销金额超出了借款剩余还款金额请注意查看！");
			}
			bean.setWithLoan(1);
		}else{
			bean.setLoan(null);
			bean.setWithLoan(0);
		}
		ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());
		bean.setProDetailId(applyBean.getProDetailId());
		bean.setIndexType(applyBean.getIndexType());
		bean.setTravelType(applyBean.getTravelType());
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核

			Integer flowId =0;
			User nextUser = new User();
			//String ywlx = tProcessCheckMng.JudgmentProcessOff(String.valueOf(reimburseType));
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(applyBean.getType()));
			if("GWCX".equals(applyBean.getTravelType())){
				strType = "GWCXBX";
			}
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	

			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号

			//			ApplicationBasicInfo applyBean = applyMng.findByCode(bean.getgCode());

			String taskName=applyBean.getgName();//申请名称
			if((bean.getAmount()-bean.getApplyAmount())>0){
				taskName = "[差旅报销]" + taskName + "[超额报销]";
			}else{
				taskName = "[差旅报销]" + taskName;
			}
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=1");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);

			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			//计算明细申请金额总数
			Double num = bean.getApplyAmount();
			Double nums =bean.getAmount();
			if(nums>num){
				budgetIndexMgrMng.addDjAmount(bean.getIndexId(),bean.getProDetailId(),nums-num);
			}
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		//修改申请单的报销状态为1（报销中）使其不可再次被选择
		getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();

		//保存附件信息
		//attachmentMng.joinEntity(bean,files);

		/** 保存申请扩展信息 **/

		//保存差旅信息
		//删除数据库中   报销中的出差行程单
		getSession().createSQLQuery("delete from T_TRAVEL_APPLI_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(travelPeop)){
			//获取出差行程单的Json对象
			List<TravelAppliInfo> rp = JSON.parseObject("["+travelPeop.toString()+"]",new TypeReference<List<TravelAppliInfo>>(){});
			for (TravelAppliInfo travelAppliInfo : rp) {
				TravelAppliInfo info = new TravelAppliInfo();
				info.setrId(bean.getrId());
				info.setTravelDateStart(travelAppliInfo.getTravelDateStart());
				info.setTravelDateEnd(travelAppliInfo.getTravelDateEnd());
				if("GWCC".equals(bean.getTravelType())){
					info.setTravelArea(String.valueOf(travelAppliInfo.getTravelAreaId()));
				}
				info.setTravelAreaName(travelAppliInfo.getTravelAreaName());
				info.setTravelAttendPeopId(travelAppliInfo.getTravelAttendPeopId());
				info.setTravelAttendPeop(travelAppliInfo.getTravelAttendPeop());
				info.setTravelAreaTime(travelAppliInfo.getTravelAreaTime());
				info.setAreaNames(travelAppliInfo.getAreaNames());
				info.setAreaCode(travelAppliInfo.getAreaCode());
				info.setfDriveWay(travelAppliInfo.getfDriveWay());
				info.setfDriveWayCode(travelAppliInfo.getfDriveWayCode());
				info.setReason(travelAppliInfo.getReason());
				info.setTravelType(bean.getTravelType());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		//删除数据库中   报销中的城市间交通费
		getSession().createSQLQuery("delete from T_OUTSIDE_TRADDIC_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		//获取出差行程单的Json对象
		if(!StringUtil.isEmpty(outsideTraffic)){
			List<OutsideTrafficInfo> outside = JSON.parseObject("["+outsideTraffic.toString()+"]",new TypeReference<List<OutsideTrafficInfo>>(){});
			for (OutsideTrafficInfo outsideTrafficInfo : outside) {
				OutsideTrafficInfo info = new OutsideTrafficInfo();
				info.setrId(bean.getrId());
				info.setfStartDate(outsideTrafficInfo.getfStartDate());
				info.setfEndDate(outsideTrafficInfo.getfEndDate());
				info.setVehicle(outsideTrafficInfo.getVehicle());
				info.setVehicleLevel(outsideTrafficInfo.getVehicleLevel());
				info.setTravelPersonnel(outsideTrafficInfo.getTravelPersonnel());
				info.setApplyAmount(outsideTrafficInfo.getApplyAmount());
				info.setSts("1");
				super.merge(info);
			}	
		}

		//删除数据库中   报销中的城内交通费
		getSession().createSQLQuery("delete from T_INCITY_TRAFFIC_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(inCity)){
			//获取城内交通费的Json对象
			List<InCityTrafficInfo> inCityList = JSON.parseObject("["+inCity.toString()+"]",new TypeReference<List<InCityTrafficInfo>>(){});
			for (InCityTrafficInfo inCityTrafficInfo : inCityList) {
				InCityTrafficInfo info = new InCityTrafficInfo();
				info.setrId(bean.getrId());
				info.setfPerson(inCityTrafficInfo.getfPerson());
				info.setfSubsidyDay(inCityTrafficInfo.getfSubsidyDay());
				info.setfAdjacentDay(inCityTrafficInfo.getfAdjacentDay());
				info.setfDistanceDay(inCityTrafficInfo.getfDistanceDay());
				info.setfApplyAmount(inCityTrafficInfo.getfApplyAmount());
				info.setTravelType(bean.getTravelType());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		//删除数据库中   报销中的住宿费
		getSession().createSQLQuery("delete from T_HOTEL_EXPENSE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS='1'").executeUpdate();
		if(!StringUtil.isEmpty(hotelJson)){
			//获取城内交通费的Json对象
			List<HotelExpenseInfo> hotelExpenseList = JSON.parseObject("["+hotelJson.toString()+"]",new TypeReference<List<HotelExpenseInfo>>(){});
			for (HotelExpenseInfo hotelExpenseInfo : hotelExpenseList) {
				HotelExpenseInfo info = new HotelExpenseInfo();
				info.setrId(bean.getrId());
				info.setCheckInTime(hotelExpenseInfo.getCheckInTime());
				info.setCheckOUTTime(hotelExpenseInfo.getCheckOUTTime());
				info.setCityId(hotelExpenseInfo.getCityId());
				info.setLocationCity(hotelExpenseInfo.getLocationCity());
				info.setTravelPersonnel(hotelExpenseInfo.getTravelPersonnel());
				info.setTravelPersonnelId(hotelExpenseInfo.getTravelPersonnelId());
				info.setApplyAmount(hotelExpenseInfo.getApplyAmount());
				info.setTravelType(bean.getTravelType());
				info.setSts("1");
				super.merge(info);
			}
		}
		//删除数据库中   报销中的伙食补助费
		getSession().createSQLQuery("delete from T_FOOD_ALLOWANCE_INFO where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
		if(!StringUtil.isEmpty(foodJson)){
			//获取城内交通费的Json对象
			List<FoodAllowanceInfo> foodAllowanceList = JSON.parseObject("["+foodJson.toString()+"]",new TypeReference<List<FoodAllowanceInfo>>(){});
			for (FoodAllowanceInfo foodAllowanceInfo : foodAllowanceList) {
				FoodAllowanceInfo info = new FoodAllowanceInfo();
				info.setrId(bean.getrId());
				info.setFname(foodAllowanceInfo.getFname());
				info.setFnameId(foodAllowanceInfo.getFnameId());
				info.setfDays(foodAllowanceInfo.getfDays());
				info.setfApplyAmount(foodAllowanceInfo.getfApplyAmount());
				info.setTravelType(bean.getTravelType());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()).executeUpdate();
		if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString()+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getPayeeName());
				info.setBank(reimbPayeeInfo.getBank());
				info.setBankAccount(reimbPayeeInfo.getBankAccount());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				super.merge(info);
			}
		}

		/**保存发票**/
		//取消之前关联的发票
		List<AppInvoiceInfo> oldInvoList = invoiceMng.findByRID("1", bean.getrId());
		if(oldInvoList!=null&&oldInvoList.size()>0){
			for (AppInvoiceInfo oldInvo : oldInvoList) {
				oldInvo.setfRId(null);
				oldInvo.setfInvoiceStatus("0");
			}
		}
		//关联发票-住宿费
		if (!StringUtil.isEmpty(hotelFpIds) && bean != null) {
			hotelFpIds = hotelFpIds.substring(0,hotelFpIds.length() - 1);
			String[] ids = hotelFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("hotel");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
		//关联发票-城市间交通费费
		if (!StringUtil.isEmpty(trafficFpIds) && bean != null) {
			trafficFpIds = trafficFpIds.substring(0,trafficFpIds.length() - 1);
			String[] ids = trafficFpIds.split(",");
			if (ids.length > 0) {
				for (String id: ids) {
					AppInvoiceInfo invo = invoiceMng.findById(Integer.valueOf(id));
					if (invo != null ) {
						invo.setfRId(bean.getrId());
						invo.setfInvoiceStatus("1");
						invo.setReimbType("1");
						invo.setCostType("traffic");
						super.saveOrUpdate(invo);
					}
				}
			}
		}
	}
	
	
	
	@Override
	public Pagination handlePageList(ReimbAppliBasicInfo bean, String reimburseType,int pageNo, int pageSize, User user) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE flowStauts='9' and stauts='1' AND type=8");
		
		if (!StringUtil.isEmpty(String.valueOf(bean.getrCode()))) {
			finder.append(" AND rCode LIKE '%"+bean.getrCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getContCode()))) {//合同编号
			finder.append(" AND contCode LIKE '%"+bean.getContCode()+"%'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getPayId()))) {//合同Id
			finder.append(" AND payId = '"+bean.getPayId()+"'");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getFcTitle()))) {//合同名称
			finder.append(" AND fcTitle LIKE '%"+bean.getFcTitle()+"%'");
		}
		/*if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime1()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') >= '"+bean.getReimburseReqTime1()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getReimburseReqTime2()))) {
			finder.append(" AND DATE_FORMAT(reimburseReqTime,'%Y-%m-%d') <= '"+bean.getReimburseReqTime2()+"'");//日期去时分秒函数
		}
		if (!StringUtil.isEmpty(bean.getFlowStauts())) {
			if(bean.getFlowStauts().equals("1")) {
				finder.append(" and flowStauts in('1','2','3','4','5','6','7','8')");
			} else {
				finder.append(" and flowStauts = '"+bean.getFlowStauts()+"'");
			}
		}*/
		finder.append(" order by updateTime desc ");
		return super.find(finder, pageNo, pageSize);
	}
	/*
	 * 驾驶舱台账分页查询
	 * @author 叶崇晖
	 * @createtime 2020-10-22
	 * @updatetime 2020-10-22
	 */
	@Override
	public Pagination reimburseCockpitPage(String type, String applyString,ReimbAppliBasicInfo bean, int pageNo,int pageSize, User user,String subCode) {
		
		Finder finder = Finder.create(" FROM  TProExpendDetail WHERE subCode='"+subCode+"'");
		List<TProExpendDetail> list = super.find(finder);
		HashSet<Integer> hashSet  = new HashSet<Integer>();
		for (int i =0; i<list.size(); i++){ //外循环是循环的次数 
			hashSet.add(list.get(i).getPid());
			}
		String text = "";
		for (Integer tProExpendDetail : hashSet) {
			text = text + tProExpendDetail +",";
		}
		for (int i = 0; i < hashSet.size(); i++) {
			
		}
		if("".equals(text)){
			return new Pagination();
		}else{
			String a = text.substring(0, text.length()-1);
			Finder finders = Finder.create(" FROM ApplicationBasicInfo WHERE stauts !='99' and flowStauts in(1,9) and proDetailId in("+a+")");
			
			/*String deptIdStr=departMng.getDeptIdStrByUser(user);
			if("".equals(deptIdStr)){ //普通岗位只能查看自己的
				finders.append(" and user = :user").setParam("user", user.getId());
			}else if("all".equals(deptIdStr)){//校长可以查看所有人的
				
			}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
				finders.append(" and dept in ("+deptIdStr+")");
			}*/
			finders.append(" order by reqTime desc");
			Pagination paginations = super.find(finders, pageNo, pageSize);
			List<ApplicationBasicInfo> listR = (List<ApplicationBasicInfo>) paginations.getList();
			
			Finder finder1 = Finder.create(" FROM DirectlyReimbAppliBasicInfo WHERE stauts !='99' and flowStauts in(1,9) and proDetailId in("+a+")");	
	 		/*if("".equals(deptIdStr)){ //普通岗位只能查看自己的
	 			finder1.append(" and user = :user").setParam("user", user.getId());
	 		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
	 			
	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
	 			finder1.append(" and dept in ("+deptIdStr+")");
	 		}*/
			if ("0".equals(applyString)) {
				finder1.append(" and YEAR(F_REQ_TIME)=YEAR(NOW())");
			}
			finder1.append(" order by reqTime desc");
			Pagination pagination1 = super.find(finder1, pageNo, pageSize);
			List<DirectlyReimbAppliBasicInfo> listD = (List<DirectlyReimbAppliBasicInfo>) pagination1.getList();
			
			for (DirectlyReimbAppliBasicInfo directlyReimbAppliBasicInfo : listD) {
				ApplicationBasicInfo applicationBasicInfo = new ApplicationBasicInfo();
				applicationBasicInfo.setgId(directlyReimbAppliBasicInfo.getDrId());
				applicationBasicInfo.setType(directlyReimbAppliBasicInfo.getType());
				applicationBasicInfo.setgCode(directlyReimbAppliBasicInfo.getDrCode());
				applicationBasicInfo.setDeptName(directlyReimbAppliBasicInfo.getDeptName());
				applicationBasicInfo.setUser(directlyReimbAppliBasicInfo.getUser());
				applicationBasicInfo.setUserName(directlyReimbAppliBasicInfo.getUserName());
				applicationBasicInfo.setReqTime(directlyReimbAppliBasicInfo.getReqTime());
				applicationBasicInfo.setFlowStauts(directlyReimbAppliBasicInfo.getFlowStauts());
				applicationBasicInfo.setCashierType(directlyReimbAppliBasicInfo.getCashierType());
				listR.add(applicationBasicInfo);
			}
			Pagination pagination = new Pagination();
			pagination.setList(listR);
			return pagination;
		}
	}

	@Override
	public void saveGoldPay(ReimbAppliBasicInfo bean, User user, String files,
			String mingxiJson,String payJson,String form1) throws Exception {
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setUser(user.getId());//申请人id
		bean.setUserName(user.getName());//申请人名称
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setLoan(null);
		if (bean.getrId()==null) {
			bean.setrId(shenTongMng.getSeq("reimb_appli_basic_info_seq"));
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			String code = StringUtil.Random("BZJ");
			bean.setrCode(code);	//申请报销的编码==事前申请的编码
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
		}
		
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			//送审时  设置质保金是否退还    0:未退还     1:已退还       2:退还中
			if(bean.getContCode().contains("HT")){
				ContractBasicInfo basicInfo = formulationMng.findById(Integer.valueOf(bean.getPayId()));
				if(!"0".equals(basicInfo.getfPayStauts())){
					throw new ServiceException("该合同保证金已报销，请勿重复操作！");
				}
				basicInfo.setfPayStauts("2");
				formulationMng.saveOrUpdate(basicInfo);
			}else{
				Upt upt = uptMng.findById(Integer.valueOf(bean.getPayId()));
				if("2".equals(upt.getfPayStauts())){
					throw new ServiceException("该合同保证金已报销，请勿重复操作！");
				}
				upt.setfPayStauts("2");
				uptMng.saveOrUpdate(upt);
			}
			
			
			
			Integer flowId =0;
			User nextUser = new User();
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), "BZJBX", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("BZJBX", user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");
			
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getrId());//申请单ID
			work.setTaskCode(bean.getrCode());//为申请单的单号
			String taskName=bean.getgName();//申请名称
			taskName = "[保证金报销]" + taskName;
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/check?id="+bean.getrId()+"&reimburseType=11");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/edit?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/edit?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		//保存费用明细
		//删除数据库中   费用明细
		getSession().createSQLQuery("delete from T_GOLDPAY_DETAIL where F_R_ID ="+bean.getrId()+"").executeUpdate();
		if(!StringUtil.isEmpty(mingxiJson)){
			//获取城内交通费的Json对象
			List<GoldPayDetail> allowanceList = JSON.parseObject("["+mingxiJson.toString().substring(0, mingxiJson.length()-1)+"]",new TypeReference<List<GoldPayDetail>>(){});
			for (GoldPayDetail applicationDetail: allowanceList) {
				GoldPayDetail info = new GoldPayDetail();
				info.setGoId(shenTongMng.getSeq("T_GOLDPAY_DETAIL_SEQ"));
				info.setrId(bean.getrId());
				info.setCostDetail(applicationDetail.getCostDetail());
				info.setRemibAmount(applicationDetail.getRemibAmount());
				info.setRemark(applicationDetail.getRemark());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()).executeUpdate();
			if(!StringUtil.isEmpty(payJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject("["+payJson.toString().substring(0, payJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getPayeeName());
				info.setPaymentType(reimbPayeeInfo.getPaymentType());//付款方式
				info.setBank(reimbPayeeInfo.getBank());
				info.setBankAccount(reimbPayeeInfo.getBankAccount());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				info.setfInnerOrOuter("1");
				super.merge(info);
			}
		}
			
			
		//旧发票
		List<InvoiceCouponInfo> oldfp1 = invoiceCouponMng.findByrID(bean.getrId(),"goldPay");
		//新发票
		List<InvoiceCouponInfo> newfp1 =  JSON.parseObject(form1, new TypeReference<List<InvoiceCouponInfo>>(){});
		//旧发票
		//删除旧发票
		for (int h = 0; h < oldfp1.size(); h++) {
			if(!StringUtil.isEmpty(oldfp1.get(h).getFileId())&&!"null".equals(oldfp1.get(h).getFileId())){
				Attachment attachment=attachmentMng.findById(oldfp1.get(h).getFileId());
				if(attachment!=null){
					attachment.setFlag("0");
					attachment.setUpdator(user);
					attachment.setUpdateTime(new Date());
					attachmentMng.updateDefault(attachment);
				}
			}
			invoiceCouponMng.delete(oldfp1.get(h));
		}
		//新增发票
		for (int i = 0; i < newfp1.size(); i++) {
			InvoiceCouponInfo newrd = newfp1.get(i);
			newrd.setIcId(shenTongMng.getSeq("invoice_coupon_info"));
			newrd.setrId(bean.getrId());
			newrd.setfDataType("goldPay");
			//保存新的明细
			super.merge(newrd);
		}
	}
	
	/**
	 * 生成需要导出的HSSFWorkbook
	 * @author 沈帆
	 * @param lectureList
	 * @param trainPlanList
	 * @param filePath
	 * @return
	 */
	@Override
	public HSSFWorkbook exportTrain(List<LecturerInfo> lectureList,
			List<MeetPlan> trainPlanList, String filePath) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			HSSFSheet sheet1 = wb.getSheetAt(1);
			
			
			if(lectureList.size()>0&&lectureList!=null){
				HSSFRow row = sheet0.getRow(2);//格式行
				for (int i = 0; i < lectureList.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(2+i);
					if(row != null) {
						hssfRow.setHeight(row.getHeight());
					}
					//序号
					hssfRow.createCell(0).setCellValue(i + 1);
					//讲师姓名
					hssfRow.createCell(1).setCellValue(lectureList.get(i).getLecturerName());
					//单位名称
					hssfRow.createCell(2).setCellValue(lectureList.get(i).getUnit());
					//职称
					hssfRow.createCell(3).setCellValue(lectureList.get(i).getLecturerLevel());
					//身份证号
					hssfRow.createCell(4).setCellValue(lectureList.get(i).getIdCard());
					//是否异地
					if(!StringUtil.isEmpty(lectureList.get(i).getIsOutside())){
						if("1".equals(lectureList.get(i).getIsOutside())){
							hssfRow.createCell(5).setCellValue("是");	
						}else{
							hssfRow.createCell(5).setCellValue("否");
						}
					}
					//讲师银行卡号
					hssfRow.createCell(6).setCellValue(lectureList.get(i).getBankCard());
					//开户行
					hssfRow.createCell(7).setCellValue(lectureList.get(i).getBank());
					//手机号
					hssfRow.createCell(8).setCellValue(lectureList.get(i).getPhoneNum());
				}
				}
				if(trainPlanList.size()>0&&trainPlanList!=null){
				for (int i = 0; i < trainPlanList.size(); i++) {
					HSSFRow row = sheet1.getRow(2);//格式行
					HSSFRow hssfRow = sheet1.createRow(2+i);
					if(row != null) {
						hssfRow.setHeight(row.getHeight());
					}
					//序号
					hssfRow.createCell(0).setCellValue(i +1);
					//起始时间
					if(trainPlanList.get(i).getTimeStart() != null) {
						hssfRow.createCell(1).setCellValue(trainPlanList.get(i).getTimeStart());
					}
					//结束时间
					if(trainPlanList.get(i).getTimeEnd() != null) {
						hssfRow.createCell(2).setCellValue(trainPlanList.get(i).getTimeEnd());
					}
					//讲师姓名
					hssfRow.createCell(3).setCellValue(trainPlanList.get(i).getLecturerName());
					//学时
					hssfRow.createCell(4).setCellValue(trainPlanList.get(i).getLessonTime());
					//培训内容
					hssfRow.createCell(5).setCellValue(trainPlanList.get(i).getArrange());
				}
			}
				return wb;	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Override 
	public void saveCurrent(ReimbAppliBasicInfo bean, ReimbPayeeInfo payeeBean,
			String arry, String files, User user,String form1,String payerinfoJson, String payerinfoJsonExt) throws Exception{
		Date d = new Date();
		bean.setReimburseReqTime(d);//申请报销时间
		bean.setReqTime(d);
		bean.setUser(user.getId());//申请人id
		bean.setDept(user.getDepart().getId());//申请人所属部门id
		bean.setDeptName(user.getDepartName());//申请人所属部门名称
		bean.setRegister("0");
		bean.setLoan(null);
		String StrIndex="";
		if (bean.getrId()==null) {
			bean.setrId(shenTongMng.getSeq("reimb_appli_basic_info_seq"));
			//创建人、创建时间
			bean.setCreator(user.getAccountNo());
			bean.setCreateTime(d);
			bean.setgCode(StringUtil.Random(""));
			StrIndex="0";
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			StrIndex="1";
		}
		
		//工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFlowStauts().equals("1") || bean.getFlowStauts().equals("2")){//1是申请人送审、2是审批人审核
			
			Integer flowId =0;
			User nextUser = new User();
			//String ywlx =getType(applyBean.getType());
			String strType = tProcessCheckMng.JudgmentProcessOff(String.valueOf(bean.getType()));
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), strType, user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode(strType, user.getDpID());
			flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			bean.setrCode(bean.getgCode());
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.saveOrUpdate(bean);
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			if(StringUtil.isEmpty(nextUser.getId())){
				throw new ServiceException("请选择下级审批人");
			}else {
				work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
				work.setTaskId(bean.getrId());//申请单ID
				work.setTaskCode(bean.getrCode());//为申请单的单号
			}
			
			String taskName=bean.getgName();//申请名称
			taskName = "[往来款报销]" + taskName;
			work.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/reimburseCheck/checkCurrent?id="+bean.getrId()+"&reimburseType=9");//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/reimburse/detailCurrent?id="+ bean.getrId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setfId(shenTongMng.getSeq("personal_worktable_seq"));
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getrId());//申请单ID
			work2.setTaskCode(bean.getrCode());//为申请单的单号
			work2.setTaskName(taskName);//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/reimburse/editCurrent?id="+ bean.getrId()+"&editType=1");//退回修改url
			work2.setUrl1("/reimburse/detailCurrent?id="+ bean.getrId()+"&editType=0");//查看url
			work2.setUrl2("/reimburse/delete?id="+ bean.getrId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
		
			//修改申请单的报销状态为1（报销中）使其不可再次被选择
			getSession().createSQLQuery("UPDATE t_application_basic_info SET F_REIMB_TYPE=1 WHERE F_G_CODE='"+bean.getgCode()+"'").executeUpdate();
			
		} else {
			//保存基本信息
			bean = (ReimbAppliBasicInfo) super.merge(bean);
		}
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
/*
		//保存费用明细
		List<ReimbDetail> mingxiList = JSON.parseObject("["+arry.toString().substring(0, arry.length()-1)+"]",new TypeReference<List<ReimbDetail>>(){});
		for(int i=0;i<=mingxiList.size()-1;i++) {
			ReimbDetail reimbDetail =new ReimbDetail();
			reimbDetail = mingxiList.get(i);
			reimbDetail.setrId(bean.getrId());
			super.merge(reimbDetail);
		}
		*/
		//删除数据库中   申请中的伙食补助费
		getSession().createSQLQuery("delete from T_APPLI_DETAIL where F_R_ID ="+bean.getrId()+" and F_STATUS=1").executeUpdate();
			if(!StringUtil.isEmpty(arry)){
			//获取城内交通费的Json对象
			List<ApplicationDetail> foodAllowanceList = JSON.parseObject("["+arry.toString().substring(0, arry.length()-1)+"]",new TypeReference<List<ApplicationDetail>>(){});
			for (ApplicationDetail applicationDetail: foodAllowanceList) {
				ApplicationDetail info = new ApplicationDetail();
				info.setcId(shenTongMng.getSeq("appli_detail_seq"));
				info.setrId(bean.getrId());
				info.setCostDetail(applicationDetail.getCostDetail());
				info.setRemibAmount(applicationDetail.getRemibAmount());
				info.setRemark(applicationDetail.getRemark());
				info.setfStatus(1);
				super.merge(info);
			}
		}
		
		
		//删除数据库中   报销中的收款人
		getSession().createSQLQuery("delete from T_REIMB_PAYEE_INFO where F_R_ID ="+bean.getrId()).executeUpdate();
			if(!StringUtil.isEmpty(payerinfoJson)){
			List<ReimbPayeeInfo> ReimbPayeeInfoList = JSON.parseObject("["+payerinfoJson.toString().substring(0, payerinfoJson.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (ReimbPayeeInfo reimbPayeeInfo : ReimbPayeeInfoList) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				info.setrId(bean.getrId());
				info.setPayeeId(reimbPayeeInfo.getPayeeId());
				info.setPayeeName(reimbPayeeInfo.getPayeeName());
				info.setPaymentType(reimbPayeeInfo.getPaymentType());//付款方式
				info.setBank(reimbPayeeInfo.getBank());
				info.setBankAccount(reimbPayeeInfo.getBankAccount());
				info.setPayeeAmount(reimbPayeeInfo.getPayeeAmount());
				info.setfInnerOrOuter("0");
				super.merge(info);
			}
		}
			
		//保存外部收款人信息
		getSession().createSQLQuery("delete from  T_REIMB_PAYEE_INFO where F_R_ID="+bean.getrId()+" and F_INNER_OR_OUTER='1'").executeUpdate();
		//收款人信息的Json对象
		if(!StringUtil.isEmpty(payerinfoJsonExt)){
			List<ReimbPayeeInfo> reimbPayeeInfoList = JSON.parseObject("["+payerinfoJsonExt.toString().substring(0, payerinfoJsonExt.length()-1)+"]",new TypeReference<List<ReimbPayeeInfo>>(){});
			for (int i = 0; i < reimbPayeeInfoList.size(); i++) {
				ReimbPayeeInfo info = new ReimbPayeeInfo();
				info.setpId(shenTongMng.getSeq("reimb_payee_info_seq"));
				info.setrId(bean.getrId());//关联申请报销单
				info.setPayeeName(reimbPayeeInfoList.get(i).getPayeeName());//搜款人姓名
				info.setBankAccount(reimbPayeeInfoList.get(i).getBankAccount());//银行账户
				info.setBank(reimbPayeeInfoList.get(i).getBank());//银行账户
				info.setPayeeAmount(reimbPayeeInfoList.get(i).getPayeeAmount());//应付转账金额
				info.setfInnerOrOuter("1");
				//保存收款人信息
				super.merge(info);
			}	
		}
		
		//旧发票
		List<InvoiceCouponInfo> oldfp1 = invoiceCouponMng.findByrID(bean.getrId(),"comm-1");
		//新发票
		List<InvoiceCouponInfo> newfp1 =  JSON.parseObject(form1, new TypeReference<List<InvoiceCouponInfo>>(){});
		//旧发票
		//删除旧发票
		for (int h = 0; h < oldfp1.size(); h++) {
			if(!StringUtil.isEmpty(oldfp1.get(h).getFileId())&&!"null".equals(oldfp1.get(h).getFileId())){
				Attachment attachment=attachmentMng.findById(oldfp1.get(h).getFileId());
				if(attachment!=null){
					attachment.setFlag("0");
					attachment.setUpdator(user);
					attachment.setUpdateTime(new Date());
					attachmentMng.updateDefault(attachment);
				}
			}
			invoiceCouponMng.delete(oldfp1.get(h));
		}
		//新增发票
		for (int i = 0; i < newfp1.size(); i++) {
			InvoiceCouponInfo newrd = newfp1.get(i);
			newrd.setIcId(shenTongMng.getSeq("invoice_coupon_info"));
			newrd.setrId(bean.getrId());
			newrd.setfDataType("comm-1");
			//保存新的明细
			super.merge(newrd);
		}	
		
	}
	/*
	 * 通过借款单查找报销单
	 * @author 方淳洲
	 * @createtime 2021-03-05
	 * @updatetime 2021-03-05
	 */
	@Override
	public List<ReimbAppliBasicInfo> findByLid(Integer getlId) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE loan="+getlId+" and (cashierType <> 1 or cashierType is null) and flowStauts <> 0 and flowStauts <> -1 and flowStauts <> -4");
		return super.find(finder);
	}
	/**
	 * 根据项目编号查询报销单
	 * @param proName
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月22日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月22日
	 */
	public List<ReimbAppliBasicInfo> findByProCode(String proCode){
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE flowStauts='9' and stauts !='99' and proCode='"+proCode+"'");
		return super.find(finder);
	}
	
	/**
	 * 根据合同Id查询报销单
	 * @param payId
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月22日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月22日
	 */
	public List<ReimbAppliBasicInfo> findByPayId(Integer payId){
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE flowStauts='9' and stauts !='99' and payId='"+payId+"'");
		return super.find(finder);
	}
	
	/**
	 * 生成需要导出的HSSFWorkbook
	 * @author 崔敬贤
	 * @param lectureList
	 * @param trainPlanList
	 * @param filePath
	 * @return
	 */
	@Override
	public HSSFWorkbook exportjsf(List<LecturerInfo> lectureList,
			List<TrainTeacherCost> mingxiList, String filePath) {
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			if(lectureList.size()>0&&lectureList!=null){
				HSSFRow row = sheet0.getRow(2);//格式行
				for (int i = 0; i < lectureList.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(2+i);
					if(row != null) {
						hssfRow.setHeight(row.getHeight());
					}
					//讲师姓名
					hssfRow.createCell(0).setCellValue(lectureList.get(i).getLecturerName());
					//单位名称
					hssfRow.createCell(1).setCellValue(lectureList.get(i).getUnit());
					//职称
					hssfRow.createCell(2).setCellValue(lectureList.get(i).getLecturerLevel());
					//身份证号
					hssfRow.createCell(3).setCellValue(lectureList.get(i).getIdCard());
					//是否异地
					if(!StringUtil.isEmpty(lectureList.get(i).getIsOutside())){
						if("1".equals(lectureList.get(i).getIsOutside())){
							hssfRow.createCell(4).setCellValue("是");	
						}else{
							hssfRow.createCell(4).setCellValue("否");
						}
					}
					//讲师银行卡号
					hssfRow.createCell(5).setCellValue(lectureList.get(i).getBankCard());
					//开户行
					hssfRow.createCell(6).setCellValue(lectureList.get(i).getBank());
					//手机号
					hssfRow.createCell(7).setCellValue(lectureList.get(i).getPhoneNum());
					if (mingxiList.size()>0&&mingxiList!=null) {
						for (int j = 0; j < mingxiList.size(); j++) {
						if (lectureList.get(i).getLecturerName().equals(mingxiList.get(j).getLecturerName())) {
							hssfRow.createCell(8).setCellValue(mingxiList.get(j).getLessonHours());
							hssfRow.createCell(9).setCellValue(mingxiList.get(j).getLessonActualStd());
							hssfRow.createCell(10).setCellValue(mingxiList.get(j).getLessonTaxAmount());
							hssfRow.createCell(11).setCellValue(mingxiList.get(j).getApplySum());
						}
					}
					}
				}
				}
				return wb;	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
