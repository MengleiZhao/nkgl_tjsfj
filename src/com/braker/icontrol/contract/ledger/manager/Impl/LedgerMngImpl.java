package com.braker.icontrol.contract.ledger.manager.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.User;
import com.braker.icontrol.budget.data.entity.BudgetData1;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.model.Upt;
import com.braker.icontrol.contract.filing.manager.ReceivPlanMng;
import com.braker.icontrol.contract.filing.model.ReceivPlan;
import com.braker.icontrol.contract.ledger.manager.LedgerMng;
import com.braker.icontrol.expend.reimburse.model.ReimbAppliBasicInfo;

@Service
@Transactional
public class LedgerMngImpl extends BaseManagerImpl<ContractBasicInfo> implements LedgerMng{
	@Autowired
	private DepartMng departMng;
	
	@Autowired
	private FormulationMng formulationMng;
	
	@Autowired
	private ReceivPlanMng receivPlanMng;
	
	@Override
	public Pagination findAllCBI(ContractBasicInfo contractBasicInfo,boolean selfDepart,User user,Integer pageNo, Integer pageSize) {
		Finder finder = Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fContStauts <>'99'");
		if(!StringUtil.isEmpty(contractBasicInfo.getSearchTitle())){
			if("1".equals(contractBasicInfo.getInStatus())){
				finder.append(" AND (F_CONT_CODE LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_CONT_TITLE LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_OPERATOR LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or  to_date(F_REQ_TIME,'yyyy-mm-dd') LIKE '%"+contractBasicInfo.getSearchTitle()+"%') ");
			}else{
				if(Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}").matcher(contractBasicInfo.getSearchTitle()).matches()){
					finder.append(" AND '"+contractBasicInfo.getSearchTitle()+"' BETWEEN F_CONT_START_TIME AND F_CONT_END_TIME OR F_SIGN_TIME LIKE '%"+contractBasicInfo.getSearchTitle()+"%'");
				}else{
					finder.append(" AND (F_CONT_CODE LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_CONT_TITLE LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or to_date(F_SIGN_TIME, 'yyyy-mm-dd') LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_AMOUNT LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or  F_ALL_AMOUNT LIKE '%"+contractBasicInfo.getSearchTitle()+"%' or F_NOT_ALL_AMOUNT LIKE '%"+contractBasicInfo.getSearchTitle()+"%') ");
				}
			}
		}
		/*if (selfDepart) {
			finder.append(" AND fDeptName ='" + user.getDepart().getName() + "' ");
		}*/
//		if (!StringUtil.isEmpty(contractBasicInfo.getFcType())) {
//			finder.append(" AND fcType =:fcType ").setParam("fcType", contractBasicInfo.getFcType());
//		}
//		if (!StringUtil.isEmpty(contractBasicInfo.getfPurchNo())) {
//			finder.append(" AND fPurchNo =:fPurchNo ").setParam("fPurchNo", contractBasicInfo.getfPurchNo());
//		}
//		if(!StringUtil.isEmpty(contractBasicInfo.getFcCode())){
//			finder.append(" AND fcCode LIKE :fcCode ");
//			finder.setParam("fcCode", "%"+contractBasicInfo.getFcCode()+"%");
//		}
//		if(!StringUtil.isEmpty(contractBasicInfo.getFcTitle())){
//			finder.append(" AND fcTitle LIKE :fcTitle ");
//			finder.setParam("fcTitle", "%"+contractBasicInfo.getFcTitle()+"%");
//		}
		/*if(!StringUtil.isEmpty(contractBasicInfo.getfFlowStauts())){
			if(contractBasicInfo.getfFlowStauts().equals("暂存")){
				contractBasicInfo.setfFlowStauts("0");
			}
			if(contractBasicInfo.getfFlowStauts().equals("待审批")){
				contractBasicInfo.setfFlowStauts("1");
			}
			if(contractBasicInfo.getfFlowStauts().equals("已审批")){
				contractBasicInfo.setfFlowStauts("9");
			}
			finder.append("AND fFlowStauts LIKE :fFlowStauts ");
			finder.setParam("fFlowStauts", "%"+contractBasicInfo.getfFlowStauts()+"%");
		}
		if(!StringUtil.isEmpty(contractBasicInfo.getFcAmount())){
			finder.append("AND fcAmount LIKE :fcAmount ");
			finder.setParam("fcAmount", "%"+contractBasicInfo.getFcAmount()+"%");
		}*/
//		if(!StringUtil.isEmpty(contractBasicInfo.getfContStauts())){
//			finder.append(" AND fContStauts = :fContStauts ");
//			finder.setParam("fContStauts", contractBasicInfo.getfContStauts());
//		}
		
		String deptIdStr = departMng.getDeptIdStrByUser(user);
		//合同台账查看岗-特定用户可以查看所有部门台账的权限
		//合同管理岗可以查看所有部门的台账
		if(user.getRoleName().contains("合同台账查看岗") ||user.getRoleName().contains("合同管理岗") ||user.getRoleName().contains("印章管理岗")) {
			
		}else if("合同法审岗".equals(user.getRoleName())) {
			finder.append(" and fcAmount >= :fcAmount").setParam("fcAmount", "10");
			finder.append(" and standard = :standard").setParam("standard", 1);
		}else {
	 		if("".equals(deptIdStr)){	//普通岗位只能查看自己的
	 			finder.append(" and fOperatorId = :fOperatorId").setParam("fOperatorId", user.getId());
	 		}else if("all".equals(deptIdStr)){	//局长可以查看所有人
	 			
	 		}else{	//部门主管，可以查看本部门的， 分管局长可以查看自己管辖的部门
	 			finder.append(" and fDeptId in ("+deptIdStr+")");
	 		}
		}
		
		if(!StringUtil.isEmpty(String.valueOf(contractBasicInfo.getfBudgetIndexCode()))){
			finder.append(" AND fBudgetIndexCode = :fBudgetIndexCode ");
			finder.setParam("fBudgetIndexCode", contractBasicInfo.getfBudgetIndexCode());
		}
		if(contractBasicInfo.getfReqtIME()!=null){
			finder.append(" AND datediff(fReqtIME,'"+contractBasicInfo.getfReqtIME()+"')=0");
		}
		finder.append(" ORDER BY fSignTime DESC");
		System.out.println(finder.getOrigHql());
		return super.find(finder, pageNo, pageSize);
	}
	
	@Override
	public Pagination uptList(Upt upt, User user, Integer pageNo, Integer pageSize) {
		Finder finder=Finder.create(" FROM Upt WHERE fUptFlowStauts=9 AND fUptStatus='1'");
		if(!StringUtil.isEmpty(upt.getSearchTitle())){
			if("1".equals(upt.getInStatus())){
				finder.append(" AND (F_CONT_CODE LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_CODE_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_NAME_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+upt.getSearchTitle()+"%' or F_OPERATOR LIKE '%"+upt.getSearchTitle()+"%' or to_date(F_REQ_TIME, 'yyyy-mm-dd') LIKE '%"+upt.getSearchTitle()+"%')  ");
			}else{
				finder.append(" AND (F_CONT_CODE LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_CODE_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_NAME_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+upt.getSearchTitle()+"%' or F_OPERATOR LIKE '%"+upt.getSearchTitle()+"%' or to_date(F_UPT_DATE, 'yyyy-mm-dd') LIKE '%"+upt.getSearchTitle()+"%') ");
			}
		}
		/*finder.append(" AND fDeptID = :fDeptID");
		finder.setParam("fDeptID", user.getDpID());*/
//		
//		if(!StringUtil.isEmpty(upt.getfContNameold())){
//			finder.append(" AND fContNameold LIKE :fContNameold");
//			finder.setParam("fContNameold", "%"+upt.getfContNameold()+"%");
//		}
//		//新合同号
//		if(!StringUtil.isEmpty(upt.getfContCode())){
//			finder.append(" AND fContCode LIKE :fContCode");
//			finder.setParam("fContCode", "%"+upt.getfContCode()+"%");
//		}
//		if(upt.getfReqTimeStart()!=null){
//			finder.append(" AND DATE_FORMAT(fReqTime, '%Y-%m-%d') >='"+upt.getfReqTimeStart()+"'");
//		}
//		if(upt.getfReqTimeEnd()!=null){
//			finder.append(" AND DATE_FORMAT(fReqTime, '%Y-%m-%d') <='"+upt.getfReqTimeEnd()+"'");
//		}
		String deptIdStr = departMng.getDeptIdStrByUser(user);
		//合同台账查看岗-特定用户可以查看所有部门台账的权限
		//合同管理岗可以查看所有部门的台账
		if(user.getRoleName().contains("合同台账查看岗") ||user.getRoleName().contains("合同管理岗") ||user.getRoleName().contains("印章管理岗")) {
			
		}else if("合同法审岗".equals(user.getRoleName())) {
			
		}else {
	 		if("".equals(deptIdStr)){	//普通岗位只能查看自己的
	 			finder.append(" and fOperatorID = :fOperatorID").setParam("fOperatorID", user.getId());
	 		}else if("all".equals(deptIdStr)){	//局长可以查看所有人
	 			
	 		}else{	//部门主管，可以查看本部门的， 分管局长可以查看自己管辖的部门
	 			finder.append(" and fDeptID in ("+deptIdStr+")");
	 		}
		}
		finder.append(" order by fUptdate desc");
		return super.find(finder, pageNo, pageSize);
	}
	
	

	@Override
	public List<ContractBasicInfo> ledger(ContractBasicInfo cb,User user) {
//		Finder finder =Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fContStauts <>'99' ");
//		if(!StringUtil.isEmpty(cb.getFcCode())){
//			finder.append(" AND fcCode LIKE('%"+cb.getFcCode()+"%')");
//		}
//		if(!StringUtil.isEmpty(cb.getFcTitle())){
//			finder.append(" AND fcTitle LIKE('%"+cb.getFcTitle()+"%')");
//		}
//		if(!StringUtil.isEmpty(cb.getfContStauts())){
//			finder.append(" AND fContStauts ="+cb.getfContStauts()+"");
//		}
//		if(!StringUtil.isEmpty(String.valueOf(cb.getfBudgetIndexCode()))){
//			finder.append("AND fBudgetIndexCode = :fBudgetIndexCode ");
//			finder.setParam("fBudgetIndexCode", cb.getfBudgetIndexCode());
//		}
		Finder finder = Finder.create(" FROM ContractBasicInfo WHERE fFlowStauts='9' AND fContStauts <>'99'");
		if(!StringUtil.isEmpty(cb.getSearchTitle())){
			if(Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}").matcher(cb.getSearchTitle()).matches()){
				finder.append(" AND '"+cb.getSearchTitle()+"' BETWEEN F_CONT_START_TIME AND F_CONT_END_TIME OR F_SIGN_TIME LIKE '%"+cb.getSearchTitle()+"%'");
			}else{
				finder.append(" AND (F_CONT_CODE LIKE '%"+cb.getSearchTitle()+"%' or F_CONT_TITLE LIKE '%"+cb.getSearchTitle()+"%' or TO_DATE(F_SIGN_TIME,'yyyy-mm-dd') LIKE '%"+cb.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+cb.getSearchTitle()+"%' or F_AMOUNT LIKE '%"+cb.getSearchTitle()+"%' or F_ALL_AMOUNT LIKE '%"+cb.getSearchTitle()+"%' or F_NOT_ALL_AMOUNT LIKE '%"+cb.getSearchTitle()+"%')");
			}
		}
		if(!user.getRoleName().contains("合同管理岗")&&!user.getRoleName().contains("系统管理员")){
			String deptIdStr=departMng.getDeptIdStrByUser(user);
	 		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
	 			finder.append(" AND fOperatorId =:fOperatorId ").setParam("fOperatorId", user.getId());
	 		}else if("all".equals(deptIdStr)){//局长可以查看所有人的
	 			
	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
	 			finder.append(" AND fDeptId in ("+deptIdStr+")");
	 		}
		}
		finder.append(" order by updateTime desc");
		return super.find(finder);
	}
	@Override
	public List<Upt> ledgerUpt(Upt upt,User user) {
//		Finder finder=Finder.create(" FROM Upt WHERE fUptFlowStauts=9 AND fUptStatus='1'");
//		
//		if(!StringUtil.isEmpty(upt.getfContNameold())){
//			finder.append(" AND fContNameold LIKE :fContNameold");
//			finder.setParam("fContNameold", "%"+upt.getfContNameold()+"%");
//		}
//		//新合同号
//		if(!StringUtil.isEmpty(upt.getfContCode())){
//			finder.append(" AND fContCode LIKE :fContCode");
//			finder.setParam("fContCode", "%"+upt.getfContCode()+"%");
//		}
//		if(upt.getfReqTimeStart()!=null){
//			finder.append(" AND DATE_FORMAT(fReqTime, '%Y-%m-%d') >='"+upt.getfReqTimeStart()+"'");
//		}
//		if(upt.getfReqTimeEnd()!=null){
//			finder.append(" AND DATE_FORMAT(fReqTime, '%Y-%m-%d') <='"+upt.getfReqTimeEnd()+"'");
//		}
		Finder finder=Finder.create(" FROM Upt WHERE fUptFlowStauts=9 AND fUptStatus='1'");
		if(!StringUtil.isEmpty(upt.getSearchTitle())){
			finder.append(" AND (F_CONT_CODE LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_CODE_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_CONT_NAME_OLD LIKE '%"+upt.getSearchTitle()+"%' or F_DEPT_NAME LIKE '%"+upt.getSearchTitle()+"%' or F_OPERATOR LIKE '%"+upt.getSearchTitle()+"%' or TO_DATE(F_UPT_DATE,'yyyy-mm-dd') LIKE '%"+upt.getSearchTitle()+"%')");
		}
		String deptIdStr = departMng.getDeptIdStrByUser(user);
		//合同台账查看岗-特定用户可以查看所有部门台账的权限
		//合同管理岗可以查看所有部门的台账
		if(user.getRoleName().contains("合同台账查看岗") ||user.getRoleName().contains("合同管理岗") ||user.getRoleName().contains("印章管理岗")) {
			
		}else if("合同法审岗".equals(user.getRoleName())) {
			
		}else {
	 		if("".equals(deptIdStr)){	//普通岗位只能查看自己的
	 			finder.append(" and fOperatorID = :fOperatorID").setParam("fOperatorID", user.getId());
	 		}else if("all".equals(deptIdStr)){	//局长可以查看所有人
	 			
	 		}else{	//部门主管，可以查看本部门的， 分管局长可以查看自己管辖的部门
	 			finder.append(" and fDeptID in ("+deptIdStr+")");
	 		}
		}
		finder.append(" order by fReqTime desc");
		return super.find(finder);
	}

	@Override
	public HSSFWorkbook exportExcel(List<ContractBasicInfo> cbi, String filePath) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			sheet0.getRow(1).createCell(1).setCellValue(df1.format(new Date()));//设置导出时间
			if(cbi.size()>0&&cbi!=null){
				HSSFRow row = sheet0.getRow(3);//格式行
				for (int i = 0; i < cbi.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(3+i);
					hssfRow.setHeight(row.getHeight());
					ContractBasicInfo data = cbi.get(i);
					//序号
					HSSFCell cell0 = hssfRow.createCell(0);
					cell0.setCellValue(i + 1);
					//合同编号
					HSSFCell cell1 = hssfRow.createCell(1);
					cell1.setCellValue(data.getFcCode());
					//合同名称
					HSSFCell cell2 = hssfRow.createCell(2);
					cell2.setCellValue(data.getFcTitle());
					//所属部门
					HSSFCell cell3 = hssfRow.createCell(3);
					cell3.setCellValue(data.getfDeptName());
					//申请人
					HSSFCell cell4 = hssfRow.createCell(4);
					cell4.setCellValue(data.getfOperator());
					//合同金额
					HSSFCell cell5 = hssfRow.createCell(5);
					cell5.setCellValue(data.getFcAmount());
					//签署日期
					HSSFCell cell6 = hssfRow.createCell(6);
					if(data.getfSignTime()!=null){
						cell6.setCellValue(df.format(data.getfSignTime()));
					}
					//变更状态
					HSSFCell cell7 = hssfRow.createCell(7);
					if("1".equals(data.getfUpdateStatus())){
						cell7.setCellValue("有变更");
					}else if("0".equals(data.getfUpdateStatus())){
						cell7.setCellValue("未变更");
					}
					//归档状态
					HSSFCell cell8 = hssfRow.createCell(8);
					if("1".equals(String.valueOf(data.getfToFilesStatus()))){
						cell8.setCellValue("已归档");
					}else if("0".equals(String.valueOf(data.getfToFilesStatus()))){
						cell8.setCellValue("未归档");
					}
					HSSFCell cell9 = hssfRow.createCell(9);
					cell9.setCellValue(data.getfAllAmount());
					HSSFCell cell10 = hssfRow.createCell(10);
					cell10.setCellValue(data.getfNotAllAmountL());
					HSSFCell cell11 = hssfRow.createCell(11);
					cell11.setCellValue(data.getKeepTime());
				}
				return wb;
			}
			
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
	public HSSFWorkbook exportExcelUpt(List<Upt> cbi, String filePath) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			sheet0.getRow(1).createCell(1).setCellValue(df1.format(new Date()));//设置导出时间
			if(cbi.size()>0&&cbi!=null){
				HSSFRow row = sheet0.getRow(3);//格式行
				for (int i = 0; i < cbi.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(3+i);
					hssfRow.setHeight(row.getHeight());
					Upt data = cbi.get(i);
					//序号
					HSSFCell cell0 = hssfRow.createCell(0);
					cell0.setCellValue(i + 1);
					//变更合同编号
					HSSFCell cell1 = hssfRow.createCell(1);
					cell1.setCellValue(data.getfContCode());
					//原合同编号
					HSSFCell cell2 = hssfRow.createCell(2);
					cell2.setCellValue(data.getfContCodeOld());
					//原合同名称
					HSSFCell cell3 = hssfRow.createCell(3);
					cell3.setCellValue(data.getfContNameold());
					//所属部门
					HSSFCell cell4 = hssfRow.createCell(4);
					cell4.setCellValue(data.getfDeptName());
					//申请人
					HSSFCell cell5 = hssfRow.createCell(5);
					cell5.setCellValue(data.getfOperator());
					//合同金额
					HSSFCell cell6 = hssfRow.createCell(6);
					cell6.setCellValue(data.getFcAmount());
					//合同签订日期
					HSSFCell cell7 = hssfRow.createCell(7);
					if(data.getfUptdate()!=null){
						cell7.setCellValue(df.format(data.getfUptdate()));
					}
					//归档状态
					HSSFCell cell8 = hssfRow.createCell(8);
					cell8.setCellValue(data.getfToFilesStatus());
					if("1".equals(String.valueOf(data.getfToFilesStatus()))){
						cell8.setCellValue("已归档");
					}else if("0".equals(String.valueOf(data.getfToFilesStatus()))){
						cell8.setCellValue("未归档");
					}
				}
				return wb;
			}
			
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
	public boolean finishContract(String fcIds) {
		//判断标记
		boolean flag = true;
		//选中项收报
		String[] strArr = fcIds.split(",");
		if(strArr.length > 0 && StringUtils.isNotEmpty(strArr[0])){
			for (int i = 0; i < strArr.length; i++) {
				//根据合同主键找到对应付款计划
				List<ReceivPlan> rpList = receivPlanMng.findByFcId(Integer.valueOf(strArr[i]));
				for (int j = 0; j < rpList.size(); j++) {
					//若合同付款计划的付款状态为未付款
					if ("0".equals(rpList.get(j).getfStauts_R())) {
						flag = false;
					}
				}
				if(flag){
					ContractBasicInfo bean = formulationMng.findById(Integer.valueOf(strArr[i]));
					bean.setfContStauts("-9");
					super.saveOrUpdate(bean);
				}
			}
		}
		return flag;
	}

	@Override
	public List<Upt> findAfterUpdateAmount(String fcCode) {
		Finder finder = Finder.create(" FROM Upt WHERE fUptFlowStauts=9 AND fUptStatus<>'99'");
		finder.append(" AND fContCodeOld = :fContCodeOld").setParam("fContCodeOld", fcCode);
		return super.find(finder);
	}

	@Override
	public List<ReimbAppliBasicInfo> findAllAmount(String fcCode) {
		Finder finder = Finder.create(" FROM ReimbAppliBasicInfo WHERE flowStauts=9 AND cashierType=1 AND stauts<>'99'");
		finder.append(" AND contCode = :contCode").setParam("contCode", fcCode);
		return super.find(finder);
	}

}
