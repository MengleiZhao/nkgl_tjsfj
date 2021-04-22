package com.braker.icontrol.cockpit.manager.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braker.common.entity.DataEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.DateUtil;
import com.braker.common.util.SortList;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.DepartMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.budget.manager.entity.TOutcomeLog;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.release.entity.TIndexDetail;
import com.braker.icontrol.cockpit.manager.CockpitMng;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.history.model.CheckHistory;
import com.google.zxing.oned.rss.FinderPattern;

@Service
public class CockpitMngImpl extends BaseManagerImpl<TProBasicInfo> implements CockpitMng {

	@Autowired
	private UserMng userMng;

	@Autowired
	private DepartMng departMng;
	
	@Override
	public String[] getTrainDataCount(Depart depart, String year,User user) {
		String[] result = new String[]{"0","0","0"};
		//未执行
		String sql1 = "select count(*) from t_application_basic_info where F_APP_TYPE='3' and F_REIMB_TYPE in ('0','1') AND YEAR(F_REQ_TIME) ='"+year+"' ";
		//已执行
		String sql2 = "SELECT count(*) FROM t_reimb_appli_basic_info WHERE F_G_CODE IN (SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='3' AND YEAR(F_REQ_TIME) ='"+year+"') AND F_CASHIER_TYPE='1' ";
		if("天津市滨海财政局".equals(depart.getText())){
			
		}else if("局领导".equals(depart.getText())){
			
		}else{
			sql1 += " and F_DEPT = '"+depart.getId()+"'";
			sql2 += " and F_DEPT = '"+depart.getId()+"'";
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
		sql1 += " and F_DEPT in ("+deptIdStr+")";
		sql2 += " and F_DEPT in ("+deptIdStr+")";
		}
		List<Object> objList = getSession().createSQLQuery(sql1 + " union all " + sql2).list();

		if (objList != null && objList.size() > 1) {
			result[0] = String.valueOf(objList.get(0)); //未执行
			result[1] = String.valueOf(objList.get(1)); //已执行
		}
		//计算百分比
		float num0 = Float.valueOf(result[0]);
		float num1 = Float.valueOf(result[1]);
		if (!(num0 == 0 && num1 == 0)) {
			DecimalFormat df = new DecimalFormat("0.00");
			String percent = df.format(num1 * 100 / (num0 + num1));
			result[2] = percent;
		}

		return result;
		//return new String[]{"3","3","50"};
	}

	@Override
	public String[] getTrainDataSum(Depart depart, String year,User user) {
		String[] result = new String[]{"0","0","0.00"};
		//总金额
		String sql1 = "SELECT SUM(F_AMOUNT) amount FROM t_application_basic_info WHERE F_APP_TYPE='3' AND F_REIMB_TYPE  AND YEAR(F_REQ_TIME) ='"+year+"' and F_FLOW_STAUTS='9' ";
		//已执行
		String sql2 = "SELECT SUM(F_AMOUNT) FROM t_reimb_appli_basic_info WHERE F_G_CODE IN(SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='3' AND YEAR(F_REQ_TIME) ='"+year+"') and F_CASHIER_TYPE='1' ";
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sql1 += " and F_DEPT = '"+depart.getId()+"'";
			sql2 += " and F_DEPT = '"+depart.getId()+"'";
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
		sql1 += " and F_DEPT in ("+deptIdStr+")";
		sql2 += " and F_DEPT in ("+deptIdStr+")";
		}	
		List<Object> objList = getSession().createSQLQuery(sql1 + " union all " + sql2).list();

		if (objList != null && objList.size() > 1) {
			String num0 = "0";//未执行
			String num1 = "0";//已执行
			String num2 = "0";//总金额
			String percent = "";//执行百分比
			//计算数据
			if (objList.get(1) != null) {
				num1 = String.valueOf(objList.get(1));
			}
			if (objList.get(0) != null) {
				num2 = String.valueOf(objList.get(0));
			}
			if (!StringUtil.isEmpty(num0) && !StringUtil.isEmpty(num1)) {
				result[0] = String.valueOf(num0); 
				result[1] = String.valueOf(num1); 
				num0 = String.valueOf(Float.valueOf(num2) - Float.valueOf(num1));//未执行
			}
			//计算百分比
			float num0f = 0;
			float num1f = 0;
			if (!StringUtil.isEmpty(result[0]) && !StringUtil.isEmpty(result[1])) {
				num0f = Float.valueOf(result[0]);
				num1f = Float.valueOf(result[1]);
			}
			if (!(num0f == 0 && num1f == 0) ) {
				DecimalFormat df = new DecimalFormat("0.00");
				percent = df.format(num1f * 100 / (num0f + num1f));
				result[2] = percent;
			}
		}

		return result;
	}

	@Override
	public String[] getMeetingDataCount(Depart depart, String year,User user) {
		String[] result = new String[]{"0","0","0"};
		//未执行
		String sql1 = "select count(*) from t_application_basic_info where F_APP_TYPE='2' and F_REIMB_TYPE in ('0','1') AND YEAR(F_REQ_TIME) ='"+year+"'";
		//已执行
		String sql2 = "SELECT count(*) FROM t_reimb_appli_basic_info WHERE F_G_CODE IN (SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='2' AND YEAR(F_REQ_TIME) ='"+year+"') AND F_CASHIER_TYPE='1'";
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sql1 += " and F_DEPT = '"+depart.getId()+"'";
			sql2 += " and F_DEPT = '"+depart.getId()+"'";
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
		sql1 += " and F_DEPT in ("+deptIdStr+")";
		sql2 += " and F_DEPT in ("+deptIdStr+")";
		}	
		List<Object> objList = getSession().createSQLQuery(sql1 + " union all " + sql2).list();

		if (objList != null && objList.size() > 1) {
			result[0] = String.valueOf(objList.get(0)); //未执行
			result[1] = String.valueOf(objList.get(1)); //已执行
		}
		//计算百分比
		float num0 = Float.valueOf(result[0]);
		float num1 = Float.valueOf(result[1]);
		if (!(num0 == 0 && num1 == 0)) {
			DecimalFormat df = new DecimalFormat("0.00");
			String percent = df.format(num1 * 100 / (num0 + num1));
			result[2] = percent;

		}
		return result;
	}

	@Override
	public String[] getMeetingDataSum(Depart depart, String year,User user) {
		String[] result = new String[]{"0","0","0.00"};
		//总金额
		String sql1 = "SELECT SUM(F_AMOUNT) amount FROM t_application_basic_info WHERE F_APP_TYPE='2' AND F_REIMB_TYPE in ('0','1') AND YEAR(F_REQ_TIME) ='"+year+"' and F_FLOW_STAUTS='9' ";
		//已执行
		String sql2 = "SELECT SUM(F_AMOUNT) FROM t_reimb_appli_basic_info WHERE F_G_CODE IN(SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='2'  AND YEAR(F_REQ_TIME) ='"+year+"') and F_CASHIER_TYPE='1' ";
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sql1 += " and F_DEPT = '"+depart.getId()+"'";
			sql2 += " and F_DEPT = '"+depart.getId()+"'";
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
		sql1 += " and F_DEPT in ("+deptIdStr+")";
		sql2 += " and F_DEPT in ("+deptIdStr+")";
		}		
		List<Object> objList = getSession().createSQLQuery(sql1 + " union all " + sql2).list();

		if (objList != null && objList.size() > 1) {
			String num0 = "0";//未执行
			String num1 = "0";//已执行
			String num2 = "0";//总金额
			String percent = "";//执行百分比
			//计算数据
			if (objList.get(1) != null) {
				num1 = String.valueOf(objList.get(1));
			}
			if (objList.get(0) != null) {
				num2 = String.valueOf(objList.get(0));
			}
			if (!StringUtil.isEmpty(num0) && !StringUtil.isEmpty(num1)) {
				result[0] = String.valueOf(num0); 
				result[1] = String.valueOf(num1); 
				num0 = String.valueOf(Float.valueOf(num2) - Float.valueOf(num1));//未执行
			}
			//计算百分比
			float num0f = 0;
			float num1f = 0;
			if (!StringUtil.isEmpty(result[0]) && !StringUtil.isEmpty(result[1])) {
				num0f = Float.valueOf(result[0]);
				num1f = Float.valueOf(result[1]);
			}
			if (!(num0f == 0 && num1f == 0) ) {
				DecimalFormat df = new DecimalFormat("0.00");
				percent = df.format(num1f * 100 / (num0f + num1f));
				result[2] = percent;
			}
		}

		return result;
	}

	@Override
	public Map<String, Double[]> getBudgetProjectDataSum(Depart depart,String year, User user) {
		//初始化
		Map<String, Double[]> map = new LinkedHashMap<>();
			//查询数据
			StringBuffer sbf = new StringBuffer("SELECT SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8))) AS pf,SUM( CAST( F_SY_AMOUNT AS DECIMAL ( 20, 8 ) ) ) AS sy,SUM( CAST( F_DJ_AMOUNT AS DECIMAL ( 20, 8 ) ) ) AS dj,"
			+ "(SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8)))-SUM(CAST(F_SY_AMOUNT AS DECIMAL(20,8)))-SUM(CAST(F_DJ_AMOUNT AS DECIMAL(20,8)))) AS zc, F_DEPT_NAME ,");
			sbf.append("(SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8)))-SUM(CAST(F_SY_AMOUNT AS DECIMAL(20,8)))-SUM(CAST(F_DJ_AMOUNT AS DECIMAL(20,8))))/SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8))) AS percent");
			sbf.append(" FROM T_BUDGET_INDEX_MGR tbim  WHERE F_YEARS='"+year+"' AND F_RELEASE_STAUTS='1' ");
			sbf.append(" ORDER BY percent DESC");
			sbf.append("");
			List<Object[]> dataList = getSession().createSQLQuery(sbf.toString()).list();
			Double pf = 0.00;//批复金额
			Double sy = 0.00;//剩余金额
			Double dj = 0.00;//冻结金额
			Double zc = 0.00;//支出金额
			//数据和部门整合
			if (dataList != null && dataList.size() > 0) {
				for (Object[] objArr: dataList) {

					Double pfi = objArr[0]==null? 0.0 : Double.valueOf(String.valueOf(objArr[0]));//批复金额
					Double syi = objArr[1]==null? 0.0 : Double.valueOf(String.valueOf(objArr[1]));//剩余金额
					Double dji = objArr[2]==null? 0.0 : Double.valueOf(String.valueOf(objArr[2]));//冻结金额
					Double zci = objArr[3]==null? 0.0 : Double.valueOf(String.valueOf(objArr[3]));//支出金额
					Double percenti = 0.0;
					if (pf != 0) {
						percenti = zci/pfi;
					}
					pf+=pfi;
					sy+=syi;
					dj+=dji;
					zc+=zci;
				}
		}
			//保留6位小数
			BigDecimal bg10000 = new BigDecimal(10000);
			BigDecimal bg1 = new BigDecimal(pf);
			bg1 = bg1.multiply(bg10000);
			pf = bg1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal bg2 = new BigDecimal(sy);
			bg2 = bg2.multiply(bg10000);
			sy = bg2.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal bg3 = new BigDecimal(dj);
			bg3 = bg3.multiply(bg10000);
			dj = bg3.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal bg4 = new BigDecimal(zc);
			bg4 = bg4.multiply(bg10000);
			zc = bg4.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("projectAmount", new Double[]{pf,sy,dj,zc});
		return map;
	}

	@Override
	public Map<String, Double[]> getPublicExpensesDataSum(String[] indexTypeArray, Depart depart,  String year, User user) {

		Map<String, Double[]> map = new LinkedHashMap<>();
		//根据指标名称查询相应的指标类型为1（基本指标）已下达，预算年度为本年度的批复金额、剩余金额、冻结金额
		for (String indexType:indexTypeArray) {
			if("7".equals(indexType)){
				indexType="因公出国（境）费用";
			}else if("5".equals(indexType)){
				indexType="公务接待费";
			}else if("6".equals(indexType)){
				indexType="公务用车运行维护费";
			}
			String indexTypes = "";
			if("因公出国（境）费用".equals(indexType)){
				indexTypes="7";
			}else if("公务接待费".equals(indexType)){
				indexTypes="5";
			}else if("公务用车运行维护费".equals(indexType)){
				indexTypes="6";
			}
			//批复总金额 sql  name代表 5、6、7 三公经费
			String sql1 = "select sum(CAST(F_PF_AMOUNT as DECIMAL(20,8))) as pf from t_budget_index_mgr  "
					+ " where F_B_INDEX_NAME='"+indexType+"'  AND F_YEARS='"+year+"'"
					+ " ";
			//支出总金额 sql
			String sql2 ="select sum(CAST(F_AMOUNT as DECIMAL(20,8))) as yy from t_reimb_appli_basic_info where "
					+ " F_FLOW_STAUTS='9' AND F_STAUTS='1' and F_CASHIER_TYPE=1 AND F_G_CODE"
					+ " in(select F_G_CODE from t_application_basic_info where F_APP_TYPE='"+indexTypes+"' ) "
					+ " AND year(F_REQ_TIME)='"+year+"'";
			//权限控制
			/*if (depart != null) {
				String departType = depart.getType();
				if (Depart.TYPE_COM.equals(departType)) {
					//公司登录，获得所有子部门的
					sql1 += " and F_DEPT_CODE in (select pid from sys_depart where parentid ='" + depart.getId() + "' and type = '" + Depart.TYPE_DEPT + "')";
					sql2 += " and F_DEPT in (select pid from sys_depart where parentid ='" + depart.getId() + "' and type = '" + Depart.TYPE_DEPT + "')";
				} else if (Depart.TYPE_DEPT.equals(departType)) {
					//部门登录，获取所属公司下所有子部门的
					sql1 += " and F_DEPT_CODE in (select pid from sys_depart where parentid ='" + depart.getParent().getId() + "' and type = '" + Depart.TYPE_DEPT + "')";
					sql2 += " and F_DEPT in (select pid from sys_depart where parentid ='" + depart.getParent().getId() + "' and type = '" + Depart.TYPE_DEPT + "')";
					sql1 += " and F_DEPT_CODE = '" + depart.getId() + "'";
					sql2 += " and F_DEPT = '" + depart.getId() + "'";
					
				}
			}*/
			if("天津现代职业技术学院".equals(depart.getText())){
				
			}else if("校领导".equals(depart.getText())){
				
			}else{
				sql1 += " and F_DEPT_CODE = '"+depart.getId()+"'";
				sql2 += " and F_DEPT = '"+depart.getId()+"'";
			}
			//权限控制
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			//公司登录，获得所有子部门的
			if("all".equals(deptIdStr)){
				
			}else{
				sql1 += " and F_DEPT_CODE in ("+deptIdStr+")";
				sql2 += " and F_DEPT in ("+deptIdStr+")";
			}
			Double pf=0.0;    //批复金额
			Double yy=0.0;   //已用金额
			Double bfb=0.0;	  //支出金额百分比形式
			
			//批复金额
			List<BigDecimal> list1 = getSession().createSQLQuery(sql1).list();
			//支出金额
			List<BigDecimal> list2 = getSession().createSQLQuery(sql2).list();
			
			if(list1.get(0)!=null && list1.size() > 0){
				for(BigDecimal str:list1){
					//保留6位小数
					if (str != null) {
						pf = str.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}
			
			if(list2.get(0)!=null&& list1.size() > 0){
				for(BigDecimal str:list2){
					if(pf!=0.0&&str!=null){
						//已用金额/10000  (转换为万元)
						BigDecimal bg2 = str.divide(new BigDecimal(10000),6, RoundingMode.HALF_UP);
						yy = (bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						//转换成百分比形式
						BigDecimal yy1=str.divide(new BigDecimal(pf), 6, RoundingMode.HALF_UP);//已用金额/批复金额=使用百分比
						bfb = (yy1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())/100;
					}
				}
			}
			if("因公出国（境）费用".equals(indexType)){
				indexType="因公出国出境费用";
			}else if("公务接待费".equals(indexType)){
				indexType="公务接待费";
			}else if("公务用车运行维护费".equals(indexType)){
				indexType="公务用车购置与运维费";
			}
			if (pf != 0.0) {
				bfb = yy/pf*100;
				bfb = Double.valueOf(new DecimalFormat("0.00").format(bfb));
			}
			map.put(indexType, new Double[]{pf,yy,bfb});
		}
		return map;
	}

	@Override
	public List<HashMap<Object, Object>> getprojectProgressTopOrLastFive(String desc, Depart depart, String year,User user ) {
		//查询项目执行进度前五的
		String sql = "select F_PF_AMOUNT pf,F_SY_AMOUNT sy,F_DJ_AMOUNT dj,CONCAT(F_B_INDEX_NAME,'@',F_B_INDEX_CODE) xmmc,(F_PF_AMOUNT-F_SY_AMOUNT-F_DJ_AMOUNT) yhje,((F_PF_AMOUNT-F_SY_AMOUNT-F_DJ_AMOUNT)/F_PF_AMOUNT) bfb from T_BUDGET_INDEX_MGR where F_YEARS=" + year + " and F_INDEX_TYPE='1' and F_RELEASE_STAUTS='1' ";
		//权限控制
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sql += " and F_DEPT_CODE = '"+depart.getId()+"'";
		}
		 String deptIdStr=departMng.getDeptIdStrByUser(user);
 		//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
		 if("all".equals(deptIdStr)){//校长可以查看所有人的
	 			
	 		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
	 			sql += " and F_DEPT_CODE in ("+deptIdStr+")";
	 		}
		sql += " ORDER BY bfb "+desc+" limit 5 ";
		SQLQuery query=getSession().createSQLQuery(sql);
		List<Object[]> list = query.list();
		//数据计算
		List<HashMap<Object, Object>> listMap=new ArrayList<HashMap<Object, Object>>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<Object, Object> map=new HashMap<Object, Object>();
			//批复金额
			Double pf= (Double)list.get(i)[0]!=null?(Double)list.get(i)[0]:0.00;
			//保留6位小数
			BigDecimal bg0 = new BigDecimal(pf);
			pf = bg0.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("pf", String.valueOf(pf));
			//剩余金额
			Double sy= (Double)list.get(i)[1]!=null?(Double)list.get(i)[1]:0.00;
			//保留6位小数
			BigDecimal bg1 = new BigDecimal(sy);
			sy = bg1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("sy", String.valueOf(sy));
			//冻结金额
			Double dj= (Double)list.get(i)[2]!=null?(Double)list.get(i)[2]:0.00;
			//保留6位小数
			BigDecimal bg2 = new BigDecimal(dj);
			dj = bg2.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("dj", String.valueOf(dj));
			//项目名称
			map.put("xmmc", String.valueOf(list.get(i)[3]));
			//已花金额
			Double yhje= (Double)list.get(i)[4]!=null?(Double)list.get(i)[4]:0.00;
			//保留6位小数
			BigDecimal bg4 = new BigDecimal(yhje);
			yhje = bg4.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("yhje", String.valueOf(yhje));
			//执行进度百分比（已花金额/批复金额）
			Double bfb= (Double)list.get(i)[5]!=null?(Double)list.get(i)[5]:0.00;
			//保留6位小数
			BigDecimal bg5 = new BigDecimal(bfb);
			bfb = bg5.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("bfb", String.valueOf(bfb));
			listMap.add(map);
		}

		return listMap;
	}

	@Override
	public List<String[]> getDepartTravelSum(Depart depart, String orderType, String year,User user) {

		//初始化
		List<String[]> resList = new ArrayList<>();
		if (depart != null) {
			//获得需要显示的部门
			String sql = "SELECT * FROM SYS_DEPART where pflag!='0' ";
			if("天津现代职业技术学院".equals(depart.getText())){
				
			}else if("校领导".equals(depart.getText())){
				
			}else{
				sql += " and pid = '"+depart.getId()+"'";
			}
			//权限控制
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			//公司登录，获得所有子部门的
			if("all".equals(deptIdStr)){
				
			}else{
				sql += " and pid in ("+deptIdStr+")";
			}
			sql += " order by ORDERNO ";
			List<Depart> departList = getSession().createSQLQuery(sql).addEntity(Depart.class).list();
			for (Depart de: departList) {
				resList.add(new String[]{de.getName(),"0"});
			}
			//查询得到所有数据
			sql = "SELECT F_DEPT_NAME,SUM(F_AMOUNT) "
					+ "FROM t_reimb_appli_basic_info "
					+ "WHERE F_G_CODE IN(SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='4'  AND F_REQ_TIME LIKE('%"+year+"%')) and F_CASHIER_TYPE='1' "
					+ "GROUP BY F_DEPT_NAME";
			List<Object[]> objList = getSession().createSQLQuery(sql).list();
			//将数据匹配入部门
			if (objList != null && objList.size() > 0) {
				for (Object[] arr: objList) {
					String departName = String.valueOf(arr[0]);
					String sum = String.valueOf(arr[1]);
					for (String[] array: resList) {
						if (departName != null && departName.equals(array[0])) {
							array[1] = sum;
						}
					}
				}
			}
			//排序
			if ("proper".equals(orderType)) {
				Collections.sort(resList, new SortByTime1());
			} else {
				Collections.sort(resList, new SortByTime2());
			}
		}
		return resList;
	}

	class SortByTime1 implements Comparator {
		public int compare(Object arg0, Object arg1) {
			// 倒序
			String[] k1 = (String[]) arg0;
			String[] k2 = (String[]) arg1;
			if (Float.valueOf(k1[1]).compareTo(Float.valueOf(k2[1]))>0){
				return -1;
			}else if(Float.valueOf(k1[1]).compareTo(Float.valueOf(k2[1]))<0){
				return 1;
			}
			else{
				return 0;
			}
		}
	}
	class SortByTime2 implements Comparator {
		public int compare(Object arg0, Object arg1) {
			// 正序
			String[] k1 = (String[]) arg0;
			String[] k2 = (String[]) arg1;
			if (Float.valueOf(k1[1]).compareTo(Float.valueOf(k2[1]))>0){
				return 1;
			}else if(Float.valueOf(k1[1]).compareTo(Float.valueOf(k2[1]))<0){
				return -1;
			}
			else{
				return 0;
			}
		}
	} 

	@Override
	public Map<String, Double> getData1(Depart depart, String year,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT F_INDEX_TYPE,SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8))) FROM T_BUDGET_INDEX_MGR ");
		sbf.append("WHERE F_YEARS='" + year + "'");
		sbf.append("AND F_RELEASE_STAUTS='1' ");
		addPms(sbf, depart,user);//判断权限
		sbf.append(" GROUP BY F_INDEX_TYPE");
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		//生成并返回数据
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String type = String.valueOf(arr[0]);
				if ("0".equals(type)) {
					type = "基本支出";
				} else if ("1".equals(type)) {
					type = "项目支出";
				}
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(type,amount);
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData2(Depart depart, String year,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT F_DEPT_NAME,SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8))) FROM T_BUDGET_INDEX_MGR ");
		sbf.append(" WHERE F_YEARS='" + year + "'");
		sbf.append(" AND F_RELEASE_STAUTS='1' ");
		sbf.append("");
		sbf.append("");
		addPms(sbf, depart,user);//判断权限
		sbf.append(" GROUP BY F_DEPT_NAME");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String departName = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(departName,amount);
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData3(Depart depart, String year,User user) {

		//初始化并给默认值
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		if (depart != null) {
			//拼接查询语句
			StringBuffer sbf = new StringBuffer("select F_TYPE,sum(CAST(F_AMOUNT AS DECIMAL(20,8))) from t_index_detail where f_amount>0 "
					+ "and year(F_TIME)="+year+" and F_TYPE!=1 and F_TYPE!=2");
			addPms(sbf, depart,user);//判断权限
			sbf.append(" GROUP BY F_TYPE ");
			//生成并返回数据
			List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
			if (objList != null && objList.size() > 0) {
				for (Object[] arr: objList) {
					String type = String.valueOf(arr[0]);
					//3、直接报销		4、申请报销		5、借款	6、采购支付		7、合同报销
					if ("3".equals(type)) {
						type = "直接报销";
					} else if ("4".equals(type)) {
						type = "申请报销";
					}else if ("5".equals(type)) {
						type = "借款";
					}else if ("6".equals(type)) {
						type = "采购支付";
					}else if ("7".equals(type)) {
						type = "合同报销";
					}
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(type,amount);
				}
			}
		}
		if (!map.containsKey("直接报销")) {
			map.put("直接报销", 0.0);
		}
		if (!map.containsKey("申请报销")) {
			map.put("申请报销", 0.0);
		}
		if (!map.containsKey("借款")) {
			map.put("借款", 0.0);
		}
	/*	if (!map.containsKey("采购支付")) {
			map.put("采购支付", 0.0);
		}*/
		if (!map.containsKey("合同报销")) {
			map.put("合同报销", 0.0);
		}
		return map;
	}

	@Override
	public Map<String, Double> getData4(Depart depart, String year,User user) {

		//初始化
		String[] lastYears = DateUtil.getLastYears1(6,year);//得到最近6年
		Map<String,Double> map = new LinkedHashMap<>();
		if (lastYears != null && lastYears.length > 0) {
			for (String ye: lastYears) {
				map.put(ye, 0.0);
			}
		}
		//开始查询
		if (depart != null) {
			//拼接查询语句 
			StringBuffer sbf = new StringBuffer("SELECT F_YEARS,"
					+ "SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8))) -SUM(CAST(F_SY_AMOUNT AS DECIMAL(20,8))) -SUM(CAST(F_DJ_AMOUNT AS DECIMAL(20,8)))"
					+ " FROM T_BUDGET_INDEX_MGR ");
			sbf.append(" WHERE F_RELEASE_STAUTS='1' ");
			addPms(sbf, depart,user);//判断权限
			sbf.append(" GROUP BY F_YEARS");
			//生成并返回数据 
			List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
			if (objList != null && objList.size() > 0) {
				for (Object[] arr: objList) {
					String ye = String.valueOf(arr[0]);
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(ye, amount);
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData5(Depart depart, String year,User user) {
		//初始化
		List<String> monthList = DateUtil.getCurrentYearMonth(Integer.valueOf(year));
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		Map<String,Double> map = new LinkedHashMap<>();
		if (monthList != null && monthList.size() > 0) {
			for (String month: monthList) {
				map.put(month, 0.0);
			}
		}

		StringBuffer sbf = new StringBuffer("SELECT DATE_FORMAT(t1.F_TIME,'%Y-%m'),SUM(F_AMOUNT) from T_INDEX_DETAIL t1 ");
		sbf.append(" WHERE 1=1 ");
		sbf.append(" AND F_TYPE!=1 and F_TYPE!=2 ");
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sbf.append(" and t1.F_DEPT_CODE = '"+depart.getId()+"'");
		}
		if (depart != null) {
			String departType = depart.getType();
			if (Depart.TYPE_COM.equals(departType)) {
				//公司登录，获得所有子部门的
				sbf.append(" AND t1.F_DEPT_CODE IN ("+deptIdStr+") ");
			} else if (Depart.TYPE_DEPT.equals(departType)) {
				//部门登录，获取所属公司下所有子部门的
				sbf.append(" AND t1.F_DEPT_CODE in ("+deptIdStr+") ");
			}
		}
		sbf.append(" GROUP BY DATE_FORMAT(t1.F_TIME,'%Y-%m')");

		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String yearMonth = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				if (map.containsKey(yearMonth)) {
					map.put(yearMonth, amount);
				}
			}
		} 
		return map;
	}


	@Override
	public Map<String, Double> getData6(Depart depart, String year,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT F_INDEX_TYPE,(SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8)))-SUM(CAST(F_XD_AMOUNT AS DECIMAL(20,8)))+SUM(CAST(F_SY_AMOUNT AS DECIMAL(20,8)))+SUM(CAST(F_DJ_AMOUNT AS DECIMAL(20,8)))) FROM T_BUDGET_INDEX_MGR ");
		sbf.append("WHERE F_YEARS='" + year + "'");
		sbf.append(" AND F_RELEASE_STAUTS='1'");
		addPms(sbf, depart,user);//判断权限
		sbf.append(" GROUP BY F_INDEX_TYPE");
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		//生成并返回数据
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String type = String.valueOf(arr[0]);
				if ("0".equals(type)) {
					type = "基本支出";
				} else if ("1".equals(type)) {
					type = "项目支出";
				}
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(type,amount);
			}
		}
		return map;

	}

	@Override
	public Map<String, Double> getData7(Depart depart, String year,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT F_DEPT_NAME,(SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8)))-SUM(CAST(F_XD_AMOUNT AS DECIMAL(20,8)))+SUM(CAST(F_SY_AMOUNT AS DECIMAL(20,8)))+SUM(CAST(F_DJ_AMOUNT AS DECIMAL(20,8)))) FROM T_BUDGET_INDEX_MGR ");
		sbf.append("WHERE F_YEARS='" + year + "'");
		sbf.append(" AND F_RELEASE_STAUTS='1'");
		addPms(sbf, depart,user);//判断权限
		sbf.append(" GROUP BY F_DEPT_NAME");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String departName = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(departName,amount);
			}
		}
		return map;
	}

	/*@Override
	public Map<String, Double> getData8(String departName, String year) {
		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		if (!StringUtil.isEmpty(departName)) {
			//拼接查询语句 
			StringBuffer sbf = new StringBuffer("SELECT * FROM F_XD_AMOUNT-F_DJ_AMOUNT-F_SY_AMOUNT FROM T_BUDGET_INDEX_MGR ");
			sbf.append(" WHERE F_RELEASE_STAUTS='1' ");
			sbf.append(" AND F_YEARS='" + year + "'");
			sbf.append(" AND F_DEPT_NAME='" + departName + "'");
			//生成并返回数据 
			List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
			if (objList != null && objList.size() > 0) {
				for (Object[] arr: objList) {
					String proName = String.valueOf(arr[0]);
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(proName, amount);
				}
			}
		}
		return map;
	}*/
	@Override
	public Pagination getDepartData(TBudgetIndexMgr bean,String departName, String year, int pageNo, int pageSize) {
			Finder finder = Finder.create(" from TBudgetIndexMgr where releaseStauts='1' ");
			finder.append(" and  years =:years").setParam("years", year);
			finder.append(" and  deptName =:deptName").setParam("deptName", departName);
			if(!StringUtil.isEmpty(bean.getIndexName())){
				finder.append(" and indexName like ('%"+bean.getIndexName()+"%') ");
			}
			finder.append(" order by (xdAmount-syAmount-djAmount) desc");
			Pagination page = super.find(finder, pageNo, pageSize);
			//加入序号
			List<TBudgetIndexMgr> dataList =  (List<TBudgetIndexMgr>) page.getList();
			if (dataList != null && dataList.size() > 0) {
				int i = 0;
				for (TBudgetIndexMgr tb: dataList) {
					tb.setNum(pageNo * pageSize + i - 9);
					i++;
				}
			}
			return page;
	}
	
	@Override
	public Map<String, Double> getData9(Depart depart, String year,User user) {
		
		return null;
	}

	//各类型培训      strType: 次数（typeNumber），金额（typeMoney）
	@Override
	public Map<String, Double> getData10(Depart depart, String year ,String strType,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT B.F_TRAINING_TYPE,SUM(F_AMOUNT),count(F_DEPT_NAME) from t_reimb_appli_basic_info A ");
		sbf.append(" INNER JOIN t_training_appli_info B ");
		sbf.append(" on (SELECT F_G_ID FROM t_application_basic_info WHERE A.F_G_CODE=F_G_CODE)=B.F_G_ID");
		sbf.append(" WHERE A.F_CASHIER_TYPE='1' ");
		sbf.append(" AND A.F_G_CODE IN (");
		sbf.append("SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='3' AND YEAR(F_REQ_TIME) ='" + year + "'");
		sbf.append(")");
		//权限
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sbf.append(" and F_DEPT = '"+depart.getId()+"'");
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
			sbf.append(" and F_DEPT in ("+deptIdStr+")");
		}
		sbf.append(" GROUP BY B.F_TRAINING_TYPE");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String type = String.valueOf(arr[0]);
				if ("1".equals(type)) {
					type = "一类培训";
				} else if ("2".equals(type)) {
					type = "二类培训";
				} else if ("3".equals(type)) {
					type = "三类培训";
				} else if ("4".equals(type)) {
					type = "四类培训";
				}
				if("typeNumber".equals(strType)){
					Double num=Double.valueOf(String.valueOf(arr[2]));
					map.put(type,num);	//次数统计
				}
				if("typeMoney".equals(strType)){
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(type, amount);  //金额统计
				}
			}
		}
		return map;
	}

	//各部门培训
	@Override
	public Map<String, Double> getData11(Depart depart, String year,String strType,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT F_DEPT_NAME,SUM(F_AMOUNT),count(F_DEPT_NAME) from t_reimb_appli_basic_info A ");
		sbf.append(" WHERE A.F_CASHIER_TYPE='1' ");
		sbf.append(" AND A.F_G_CODE IN (");
		sbf.append("SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='3'  AND YEAR(F_REQ_TIME) ='" + year + "'");
		sbf.append(")");
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sbf.append(" and F_DEPT = '"+depart.getId()+"'");
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
			sbf.append(" and F_DEPT in ("+deptIdStr+")");
		}
		sbf.append(" GROUP BY A.F_DEPT_NAME");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String departName = String.valueOf(arr[0]);
				if("typeNumber".equals(strType)){
					Double num=Double.valueOf(String.valueOf(arr[2]));
					map.put(departName,num);	//次数统计
				}
				if("typeMoney".equals(strType)){
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(departName, amount);  //金额统计
				}
			}
		}
		return map;
	}

	//各类型会议
	@Override
	public Map<String, Double> getData10_(Depart depart, String year,String strType,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT B.F_MEETING_TYPE,SUM(F_AMOUNT),count(F_MEETING_TYPE) from t_reimb_appli_basic_info A ");
		sbf.append(" INNER JOIN t_meeting_appli_info B ");
		sbf.append(" on (SELECT F_G_ID FROM t_application_basic_info WHERE A.F_G_CODE=F_G_CODE)=B.F_G_ID");
		sbf.append(" WHERE A.F_CASHIER_TYPE='1' ");
		sbf.append(" AND A.F_G_CODE IN (");
		sbf.append("SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='2' AND YEAR(F_REQ_TIME) ='" + year + "'");
		sbf.append(")");
		if("天津现代职业技术学院".equals(depart.getText())){
					
				}else if("校领导".equals(depart.getText())){
					
				}else{
					sbf.append(" and F_DEPT = '"+depart.getId()+"'");
				}
				//权限控制
				String deptIdStr=departMng.getDeptIdStrByUser(user);
				//公司登录，获得所有子部门的
				if("all".equals(deptIdStr)){
					
				}else{
					sbf.append(" and F_DEPT in ("+deptIdStr+")");
				}
		sbf.append(" GROUP BY B.F_MEETING_TYPE");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String type = String.valueOf(arr[0]);
				if ("1".equals(type)) {
					type = "一类会议";
				} else if ("2".equals(type)) {
					type = "二类会议";
				} else if ("3".equals(type)) {
					type = "三类会议";
				} else if ("4".equals(type)) {
					type = "四类会议";
				}
				if("typeNumber".equals(strType)){
					Double num=Double.valueOf(String.valueOf(arr[2]));
					map.put(type,num);	//次数统计
				}
				if("typeMoney".equals(strType)){
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(type, amount);  //金额统计
				}
			}
		}
		return map;
	}

	//各部门会议
	@Override
	public Map<String, Double> getData11_(Depart depart, String year,String strType,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuffer sbf = new StringBuffer("SELECT F_DEPT_NAME,SUM(F_AMOUNT),count(F_DEPT_NAME) from t_reimb_appli_basic_info A ");
		sbf.append(" WHERE A.F_CASHIER_TYPE='1' ");
		sbf.append(" AND A.F_G_CODE IN (");
		sbf.append("SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='2'  AND YEAR(F_REQ_TIME) ='" + year + "'");
		sbf.append(")");
		//权限
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sbf.append(" and F_DEPT = '"+depart.getId()+"'");
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
			sbf.append(" and F_DEPT in ("+deptIdStr+")");
		}
		sbf.append(" GROUP BY A.F_DEPT_NAME");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String departName = String.valueOf(arr[0]);
				if("typeNumber".equals(strType)){
					Double num=Double.valueOf(String.valueOf(arr[2]));
					map.put(departName,num);	//次数统计
				}
				if("typeMoney".equals(strType)){
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(departName, amount);  //金额统计
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData12(String departName, String year,User user) {
		//初始化
				List<String> monthList = DateUtil.getCurrentYearMonth(Integer.valueOf(year));
				Map<String,Double> map = new LinkedHashMap<>();
				if (monthList != null && monthList.size() > 0) {
					for (String month: monthList) {
						map.put(month, 0.0);
					}
				}
		//开始查询
		StringBuilder sbf = new StringBuilder("select DATE_FORMAT(F_REQ_TIME,'%Y-%m'),sum(F_AMOUNT) FROM t_reimb_appli_basic_info ");
		sbf.append(" WHERE F_G_CODE IN(SELECT F_G_CODE FROM t_application_basic_info WHERE F_APP_TYPE='4' "
				+ " AND YEAR(F_REQ_TIME) ='" + year + "') ");
		sbf.append(" AND F_CASHIER_TYPE='1'");
		sbf.append(" AND F_DEPT_NAME='" + departName + "'");
		sbf.append(" GROUP BY DATE_FORMAT(F_REQ_TIME,'%Y-%m')");
		
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String yearMonth = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(yearMonth,amount);
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData13(Depart depart, String year, String itemName, String dates, String datee,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		StringBuilder sbf=new StringBuilder("select F_DEPT_NAME,sum(CAST(F_AMOUNT as DECIMAL(20,8))) as yy from t_reimb_appli_basic_info where F_G_CODE"
					+ " in(select F_G_CODE from t_application_basic_info where F_APP_TYPE='"+itemName+"' )"
							+" and F_STAUTS=1 and F_CASHIER_TYPE=1");
		//年度、起始结束时间查询条件
		if(!"undefined".equals(datee) && !"undefined".equals(dates)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (!StringUtil.isEmpty(dates)) {
					Date date = sdf.parse(dates);
					date.setHours(0);
					date.setMinutes(0);
					sbf.append(" AND F_REQ_TIME>='"+sdf.format(date) + "'");
				}
				if (!StringUtil.isEmpty(datee)) {
					Date date = sdf.parse(datee);
					date.setHours(23);
					date.setMinutes(59);
					sbf.append(" AND F_REQ_TIME<='"+sdf.format(date) + "'");
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			sbf.append(" AND year(F_REQ_TIME)='"+year + "'");
		}
		
		if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sbf.append(" and F_DEPT = '"+depart.getId()+"'");
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
			sbf.append(" and F_DEPT in ("+deptIdStr+")");
		}
		sbf.append(" GROUP BY F_DEPT");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String departName = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]))/10000;
				map.put(departName,amount);
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData14(Depart depart, String year, String itemName,User user) {

		//初始化
		List<String> monthList = DateUtil.getCurrentYearMonth(Integer.valueOf(year));
		Map<String,Double> map = new LinkedHashMap<>();
		if (monthList != null && monthList.size() > 0) {
			for (String month: monthList) {
				map.put(month, 0.0);
			}
		}
		//开始查询
		StringBuilder sbf=new StringBuilder("select DATE_FORMAT(F_REQ_TIME,'%Y-%m'),sum(CAST(F_AMOUNT as DECIMAL(20,8))) as yy from t_reimb_appli_basic_info where F_G_CODE"
				+ " in(select F_G_CODE from t_application_basic_info where F_APP_TYPE='"+itemName+"')"
						+ " and F_STAUTS=1 and F_CASHIER_TYPE=1");
		
if("天津现代职业技术学院".equals(depart.getText())){
			
		}else if("校领导".equals(depart.getText())){
			
		}else{
			sbf.append(" and F_DEPT = '"+depart.getId()+"'");
		}
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		//公司登录，获得所有子部门的
		if("all".equals(deptIdStr)){
			
		}else{
			sbf.append(" and F_DEPT in ("+deptIdStr+")");
		}
		sbf.append(" GROUP BY DATE_FORMAT(F_REQ_TIME,'%Y-%m')");
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String yearMonth = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]))/10000;
				if (map.containsKey(yearMonth)) {
					map.put(yearMonth,amount);
				}
			}
		}
		return map;
	}

	/**
	 * 本方法使用前提：查询表的“部门id”字段必须是“F_DEPT_CODE”
	 * 根据部门为查询语句加入权限查询
	 * @param sbf 查询语句
	 * @param depart 部门
	 */
	private void addPms(StringBuffer sbf, Depart depart,User user){
		/*if (sbf != null && sbf.length() > 0) {
			if (depart != null) {
				String departType = depart.getType();
				if (Depart.TYPE_COM.equals(departType)) {
					//公司登录，获得所有子部门的
					sbf.append(" AND F_DEPT_CODE IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "') ");
				} else if (Depart.TYPE_DEPT.equals(departType)) {
					//部门登录，获取所属公司下所有子部门的
					sbf.append(" AND F_DEPT_CODE IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getParent().getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "') ");
					//部门登录，获取本部门的
					sbf.append(" AND F_DEPT_CODE = '" + depart.getId() + "' ");
				}
			}
		}*/
		
		if("天津现代职业技术学院".equals(depart.getText())){
				
			}else if("校领导".equals(depart.getText())){
				
			}else{
				sbf.append(" and F_DEPT_CODE = '"+depart.getId()+"'");
			}
			//权限控制
			String deptIdStr=departMng.getDeptIdStrByUser(user);
			//公司登录，获得所有子部门的
			if("all".equals(deptIdStr)){
				
			}else{
				sbf.append(" and F_DEPT_CODE in ("+deptIdStr+")");
			}
		}

	@Override
	public Map<String, Double> getData15(Depart depart, String year, String indexType,User user) {

		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//sql
		StringBuffer sbf = new StringBuffer("SELECT F_B_INDEX_CODE,SUM(CAST(F_PF_AMOUNT AS DECIMAL(20,8))) FROM T_BUDGET_INDEX_MGR WHERE 1=1 ");
		addPms(sbf, depart,user);	//权限
		sbf.append(" AND F_RELEASE_STAUTS='1' ");
		sbf.append(" AND F_YEARS='" + year + "'");

		sbf.append(" AND F_INDEX_TYPE='" + indexType + "'");
		sbf.append(" GROUP BY F_B_INDEX_CODE order by F_APP_DATE desc");

		//返回
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String xName = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(xName,amount);
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData16(String year, String departName,User user) {
		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//sql
		StringBuffer sbf = new StringBuffer("SELECT F_INDEX_TYPE,SUM(F_PF_AMOUNT) FROM T_BUDGET_INDEX_MGR WHERE 1=1 ");
		sbf.append(" AND F_RELEASE_STAUTS='1' ");
		sbf.append(" AND F_YEARS='" + year + "'");

		sbf.append(" AND F_DEPT_NAME='" + departName + "'");
		sbf.append(" GROUP BY F_INDEX_TYPE");

		//返回
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String type = String.valueOf(arr[0]);
				if ("0".equals(type)) {
					type = "基本支出";
				} else if ("1".equals(type)) {
					type = "项目支出";
				}
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(type,amount);
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData17(Depart depart,String indexType,String year,User user) {

		//初始化
		//TODO 加一个参数：年度
		List<String> monthList = DateUtil.getCurrentYearMonth(Integer.valueOf(year));
		Map<String,Double> map = new LinkedHashMap<>();
		if (monthList != null && monthList.size() > 0) {
			for (String month: monthList) {
				map.put(month, 0.0);
			}
		}
		
		//开始查询语句
		StringBuffer sbf = new StringBuffer("SELECT DATE_FORMAT(F_TIME,'%Y-%m'),SUM(F_AMOUNT) from T_INDEX_DETAIL t1 ");
		sbf.append(" WHERE t1.F_TYPE='" + indexType + "' ");
		if (depart != null) {
			String departType = depart.getType();
			if (Depart.TYPE_COM.equals(departType)) {
				//公司登录，获得所有子部门的
				sbf.append(" AND t1.F_DEPT_CODE IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getId() + "') ");
			} else if (Depart.TYPE_DEPT.equals(departType)) {
//				//部门登录，获取所属公司下所有子部门的
//				sbf.append(" AND t1.F_DEPT_CODE IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getParent().getId() + "') ");
				sbf.append(" AND t1.F_DEPT_CODE = '" + depart.getId() + "'");
			}
		}
		sbf.append(" GROUP BY DATE_FORMAT(F_TIME,'%Y-%m')");
		
		//生成并返回数据
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String yearMonth = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				if (map.containsKey(yearMonth)) {
					map.put(yearMonth, amount);
				}
			}
		} 
		return map;
	}

	@Override
	public Map<String, Double> getData18(Depart depart, String year, String indexType,User user) {
		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//sql
		StringBuffer sbf = new StringBuffer("SELECT F_B_IndEX_NAME,SUM(CAST(F_SY_AMOUNT AS DECIMAL(20,8))) FROM T_BUDGET_INDEX_MGR WHERE 1=1 ");
		addPms(sbf, depart,user);	//权限
		sbf.append(" AND F_RELEASE_STAUTS='1' ");
		sbf.append(" AND F_YEARS='" + year + "'");

		sbf.append(" AND F_INDEX_TYPE='" + indexType + "'");
		sbf.append(" GROUP BY F_B_INDEX_NAME ");

		//返回
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String xName = String.valueOf(arr[0]);
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(xName,amount);
			}
		}
		return map;
	}

	@Override
	public Map<String, Double> getData19(String year, String departName,User user) {
		//初始化
		Map<String,Double> map = new LinkedHashMap<>();
		//sql
		StringBuffer sbf = new StringBuffer("SELECT F_INDEX_TYPE,SUM(CAST(F_SY_AMOUNT AS DECIMAL(20,8)))  FROM T_BUDGET_INDEX_MGR WHERE 1=1 ");
		sbf.append(" AND F_RELEASE_STAUTS='1' ");
		sbf.append(" AND F_YEARS='" + year + "'");

		//
		sbf.append(" AND F_DEPT_NAME='" + departName + "'");
		sbf.append(" GROUP BY F_INDEX_TYPE");

		//返回
		List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
		if (objList != null && objList.size() > 0) {
			for (Object[] arr: objList) {
				String type = String.valueOf(arr[0]);
				if ("0".equals(type)) {
					type = "基本支出";
				} else if ("1".equals(type)) {
					type = "项目支出";
				}
				Double amount = Double.valueOf(String.valueOf(arr[1]));
				map.put(type,amount);
			}
		}
			return map;
}
	@Override
	public Map<String, Double> getData20(Depart depart) {
		
		return null;
	}

	@Override
	public Pagination getData26(Depart depart,String itemName, int pageNo, int pageSize) {
		Finder finder = Finder.create(" from T_BUDGET_INDEX_MGR where F_RELEASE_STAUTS='1' ");
		finder.append(" and  F_B_INDEX_NAME =: F_B_INDEX_NAME").setParam(" F_B_INDEX_NAME", itemName);
		finder.append(" and  F_DEPT_NAME =: F_DEPT_NAME").setParam(" F_DEPT_NAME", depart.getName());
		finder.append(" order by F_APP_DATE desc");

		Pagination page = super.find(finder, pageNo, pageSize);
		//加入序号
		List<TBudgetIndexMgr> dataList =  (List<TBudgetIndexMgr>) page.getList();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (TBudgetIndexMgr tb: dataList) {
				tb.setNum(pageNo * pageSize + i - 9);
				i++;
			}
		}
		return page;
	}

	@Override
	public Pagination getData27(Depart depart,String itemName,String month, int pageNo, int pageSize) {
		Finder finder = Finder.create(" FROM T_BUDGET_INDEX_MGR where F_RELEASE_STAUTS='1' ");
		finder.append(" and  F_B_INDEX_NAME =: F_B_INDEX_NAME").setParam(" F_B_INDEX_NAME", itemName);
		finder.append(" and DATE_FORMAT(F_APP_DATE,'%Y-%m') =:month").setParam("month", month);
		finder.append(" order by F_APP_DATE desc");

		Pagination page = super.find(finder, pageNo, pageSize);
		//加入序号
		List<TBudgetIndexMgr> dataList =  (List<TBudgetIndexMgr>) page.getList();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			for (TBudgetIndexMgr tb: dataList) {
				tb.setNum(pageNo * pageSize + i - 9);
				i++;
			}
		}
		return page;
	}

	@Override
	public Pagination getData28(Depart depart,String code, String moneyStart,String moneyEnd,String fType,String userName, int pageNo, int pageSize) {
		/*
		 //------------废弃的sql；
		StringBuilder sb=new StringBuilder("select a.F_TYPE,a.F_USER_ID,a.F_DEPARTMENT,a.F_TIME,a.F_AMOUNT,b.F_R_CODE");
		sb.append(" FROM T_INDEX_DETAIL a left join t_reimb_appli_basic_info b "); 
		sb.append(" on a.F_INDEX_CODE in(select c.F_B_INDEX_CODE from t_budget_index_mgr c where c.F_B_ID=b.F_INDEX_ID) where");
		sb.append(" a.F_INDEX_CODE='"+code+"'");
		*/
		StringBuilder sb=new StringBuilder("select a.F_TYPE,a.F_USER_ID,a.F_DEPARTMENT,a.F_TIME,a.F_AMOUNT,a.F_EXT_1,a.F_EXT_2");
		sb.append(" FROM T_INDEX_DETAIL a where 1=1 ");
		sb.append(" AND (a.F_INDEX_CODE='"+code+"' or F_PROJECT_CODE='"+code+"')");
		if(depart!=null){
			sb.append(" AND a.F_DEPARTMENT='"+depart.getName()+"'");
		}
		if(!StringUtil.isEmpty(fType)){
			sb.append(" AND a.F_TYPE='"+fType+"'");
		}
		if(!StringUtil.isEmpty(userName)){
			sb.append(" AND a.F_USER_ID in (select pid from sys_user where name LIKE('%"+userName+"%'))");
		}
		if(!StringUtil.isEmpty(moneyStart)){
			sb.append(" AND a.F_AMOUNT >='"+moneyStart+"'");
		}
		if(!StringUtil.isEmpty(moneyEnd)){
			sb.append(" AND a.F_AMOUNT <='"+moneyEnd+"'");
		}
		sb.append(" order by a.F_TYPE desc,a.F_AMOUNT desc");
		
		String str=sb.toString();
		Pagination p=super.findObjectListBySql(str, pageNo, pageSize);
		List<Object[]> dataList = (List<Object[]>) p.getList();
		List<TIndexDetail>list=new ArrayList<>();
		if (dataList != null && dataList.size() > 0) {
			int i = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Object[] obj: dataList) {
				TIndexDetail t=new TIndexDetail();
				t.setfType(String.valueOf(obj[0]));
				User user = userMng.findById(String.valueOf(obj[1]));
				t.setUsername(user.getName());
				t.setDepartment(String.valueOf(obj[2]));
				String time=String.valueOf(obj[3]);
				t.setAmount(Double.valueOf((Float) obj[4]));
				t.setUrl(String.valueOf(obj[5]));
				//单据编号
				if(StringUtil.isEmpty(String.valueOf(obj[6]))){
					t.setBillsCode("null");//单据编号
				}else{
					t.setBillsCode(String.valueOf(obj[6]));
				}
					
				try {
					t.setTime(sdf.parse(time));
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				t.setNum(pageNo * pageSize + i - 9);
				i++;
				list.add(t);
			}
			p.setList(list);
		}
		return p;
	}

	@Override
	public Pagination getData29(Depart depart, int pageNo, int pageSize) {
		
		return null;
	}

	@Override
	public Map<String, Double> getExcessData(String code) {

		//初始化并给默认值
		Map<String,Double> map = new LinkedHashMap<>();
		//开始查询
		
			//拼接查询语句
			StringBuffer sbf = new StringBuffer("select F_TYPE,sum(CAST(F_AMOUNT AS DECIMAL(20,8))) from t_index_detail where f_amount>0 "
					+ "and F_INDEX_CODE='"+code+"' and F_TYPE!=1 and F_TYPE!=2");
		
			sbf.append(" GROUP BY F_TYPE ");
			//生成并返回数据
			List<Object[]> objList = getSession().createSQLQuery(sbf.toString()).list();
			if (objList != null && objList.size() > 0) {
				for (Object[] arr: objList) {
					String type = String.valueOf(arr[0]);
					//3、直接报销		4、申请报销		5、借款	6、采购支付		7、合同报销
					if ("3".equals(type)) {
						type = "直接报销";
					} else if ("4".equals(type)) {
						type = "申请报销";
					}else if ("5".equals(type)) {
						type = "借款";
					}else if ("6".equals(type)) {
						type = "采购支付";
					}else if ("7".equals(type)) {
						type = "合同报销";
					}
					Double amount = Double.valueOf(String.valueOf(arr[1]));
					map.put(type,amount);
				}
			}
	
		if (!map.containsKey("直接报销")) {
			map.put("直接报销", 0.0);
		}
		if (!map.containsKey("申请报销")) {
			map.put("申请报销", 0.0);
		}
		if (!map.containsKey("借款")) {
			map.put("借款", 0.0);
		}
		if (!map.containsKey("采购支付")) {
			map.put("采购支付", 0.0);
		}
		if (!map.containsKey("合同报销")) {
			map.put("合同报销", 0.0);
		}
		return map;
	}

	@Override
	public Map<String, Double> getData8(String departName, String year,User user) {
		
		return null;
	}

	@Override
	public Pagination getData8(String departName, String year, int pageNo,
			int pageSize) {
		
		return null;
	}

	@Override
	public Pagination getFrozenAList(User user,String code, int pageNo, int pageSize) {
		try {
		StringBuilder sb=new StringBuilder("SELECT tabi.F_G_ID, tabi.F_G_CODE, tabi.F_APP_TYPE, tabi.F_AMOUNT, tabi.F_G_NAME, tabi.F_INDEX_ID,sd.DEPARTNAME,su.name FROM t_reimb_appli_basic_info trab"
				+ " LEFT JOIN T_APPLICATION_BASIC_INFO tabi ON tabi.F_G_CODE = trab.F_G_CODE left join sys_depart sd on tabi.F_DEPT = sd.PID LEFT JOIN sys_user su on tabi.F_USER = su.PID WHERE tabi.F_INDEX_ID = "+code+" AND tabi.F_STAUTS <> '99'"
				+ " AND trab.F_STAUTS <> '99' AND ( trab.F_CASHIER_TYPE != '1' OR ISNULL( trab.F_CASHIER_TYPE ) ) AND tabi.F_INDEX_TYPE = '1'"
				+ " UNION ALL SELECT td.F_DR_ID, td.F_DR_CODE, td.F_REIMB_TYPE, td.F_AMOUNT, td.F_EXT_1, td.F_INDEX_ID,sd.DEPARTNAME,su.name FROM t_directly_reimb_appli_basic_info td left join sys_depart sd on td.F_DEPT = sd.PID LEFT JOIN sys_user su on td.F_USER = su.PID WHERE F_INDEX_ID = "+code+""
				+ " AND ( F_CASHIER_TYPE != '1' OR ISNULL( F_CASHIER_TYPE ) ) AND F_STAUTS IN ( '1', '2', '9' ) UNION ALL SELECT F_L_ID, F_L_CODE, '10', F_LEAST_AMOUNT, F_REASON, F_INDEX_ID,sd.DEPARTNAME,su.name"
				+ " FROM t_loan_basic_info tl left join sys_depart sd on tl.F_DEPT = sd.PID LEFT JOIN sys_user su on tl.F_USER = su.PID WHERE F_INDEX_ID = "+code+" AND F_STAUTS IN ( '1', '2', '9' ) AND ( F_CASHIER_TYPE != '1' OR ISNULL( F_CASHIER_TYPE ) ) "
				+ " AND F_INDEX_TYPE = '1' UNION ALL SELECT cbi.F_CONT_ID, cbi.F_CONT_CODE, cbi.F_CONT_TYPE, rp.F_RECE_PLAN_AMOUNT, cbi.F_PURCH_NAME, cbi.F_BUDGET_INDEX_CODE,sd.DEPARTNAME,su.name"
				+ " FROM t_contract_basic_info cbi LEFT JOIN t_receiv_plan rp ON cbi.F_CONT_ID = rp.F_CONT_ID left join sys_depart sd on cbi.F_DEPT_ID = sd.PID LEFT JOIN sys_user su on cbi.F_OPERATOR_ID = su.PID WHERE cbi.F_BUDGET_INDEX_CODE = "+code+""
				+ " AND ( cbi.F_CONT_STAUTS != '1' OR cbi.F_CONT_STAUTS != '99' ) AND rp.F_PAY_STAUTS != '99' AND cbi.F_INDEX_TYPE = '1'");
		
		
			String str=sb.toString();
			Pagination p= new Pagination();
			List<Object[]> dataList = getSession().createSQLQuery(str).list();
			List<DataEntity>list=new ArrayList<>();
			if (dataList != null && dataList.size() > 0) {
				int i = 0;
				for (Object[] obj: dataList) {
					DataEntity t=new DataEntity();
					t.setId(String.valueOf(obj[0]));
					t.setCol1(String.valueOf(obj[1]));
					t.setCol2(String.valueOf(obj[2]));
					t.setCol3(String.valueOf(obj[3]));
					t.setCol4(String.valueOf(obj[4]));
					t.setCol5(String.valueOf(obj[5]));
					t.setCol6(String.valueOf(obj[6]));
					t.setCol7(String.valueOf(obj[7]));
					t.setNum(i);
					i++;
					list.add(t);
				}
				p.setPageNo(pageNo);
				p.setPageSize(pageSize);
				p.setTotalCount(pageNo*pageSize);
				p.setList(list);
			}
			return p;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object> getCGDataSum(Depart depart, String year, User user) {
		//已登记
		String sql1 = "select count(*) a,IFNULL(sum(F_AMOUNT), 0) b from t_purchase_apply_basic where F_BID_STAUTS=9 and YEAR(F_REQ_TIME)=YEAR(NOW())";
		//采购中
		String sql2 = "select count(*) a,IFNULL(sum(F_AMOUNT), 0) b from t_purchase_apply_basic where F_BID_STAUTS!=9 and YEAR(F_REQ_TIME)=YEAR(NOW()) and F_CHECK_STAUTS ='9' and F_STAUTS !='99'";
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
			sql1 += " and F_USER ='"+ user.getId()+"'";
			sql2 += " and F_USER ='"+ user.getId()+"'";
		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
			
		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
			sql1 += " and F_DEPT_ID in ("+deptIdStr+")";
			sql2 += " and F_DEPT_ID in ("+deptIdStr+")";
		}
		List<Object> objList = getSession().createSQLQuery(sql1 + " union all " + sql2).list();
		return objList;
	
	}
	/**
	 * 获得采购管理数据
	 * 对应图表：根据采购类型显示柱形图
	 * @return  
	 */
	@Override
	public List<Object> getCGTypeDataSum(Depart depart, String year, User user) {
		List<Object> objects = new ArrayList<Object>();
		objects =getCGTypeZCDataSum(depart, year, user);
		objects.addAll(getCGTypeFZCDataSum(depart, year, user));
		return objects;
	}
	/**
	 * 获得采购管理数据
	 * 对应图表：根据采购类型显示柱形图    政府采购数据
	 * @return  
	 */
	@Override
	public List<Object> getCGTypeZCDataSum(Depart depart, String year, User user) {
		//已登记
		String sql1 = "select count(*) a,IFNULL(sum(F_AMOUNT), 0) b from t_purchase_apply_basic where F_P_METHOD ='PURCHASE_METHOD_1' and YEAR(F_REQ_TIME)=YEAR(NOW()) and F_BID_STAUTS=9";
		//采购中
		String sql2 = "select count(*) a,IFNULL(sum(F_AMOUNT), 0) b from t_purchase_apply_basic where F_P_METHOD ='PURCHASE_METHOD_1' and YEAR(F_REQ_TIME)=YEAR(NOW()) and F_BID_STAUTS!=9 and F_CHECK_STAUTS ='9' and F_STAUTS !='99'";
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
			sql1 += " and F_USER ='"+ user.getId()+"'";
			sql2 += " and F_USER ='"+ user.getId()+"'";
		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
			
		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
			sql1 += " and F_DEPT_ID in ("+deptIdStr+")";
			sql2 += " and F_DEPT_ID in ("+deptIdStr+")";
		}
		List<Object> objList = getSession().createSQLQuery(sql1 + " union all " + sql2).list();
		return objList;
	}
	/**
	 * 获得采购管理数据
	 * 对应图表：根据采购类型显示柱形图     非政府采购数据
	 * @return  
	 */
	@Override
	public List<Object> getCGTypeFZCDataSum(Depart depart, String year,
			User user) {
		//已登记
		String sql1 = "select count(*) a,IFNULL(sum(F_AMOUNT), 0) b from t_purchase_apply_basic where F_P_METHOD ='PURCHASE_METHOD_2' and YEAR(F_REQ_TIME)=YEAR(NOW()) and F_BID_STAUTS=9";
		//采购中
		String sql2 = "select count(*) a,IFNULL(sum(F_AMOUNT), 0) b from t_purchase_apply_basic where F_P_METHOD ='PURCHASE_METHOD_2' and YEAR(F_REQ_TIME)=YEAR(NOW()) and F_BID_STAUTS!=9 and F_CHECK_STAUTS ='9' and F_STAUTS !='99'";
		//权限控制
		String deptIdStr=departMng.getDeptIdStrByUser(user);
		if("".equals(deptIdStr)){ //普通岗位只能查看自己的
			sql1 += " and F_USER ='"+ user.getId()+"'";
			sql2 += " and F_USER ='"+ user.getId()+"'";
		}else if("all".equals(deptIdStr)){//校长可以查看所有人的
			
		}else{//部门主管，可以查看本部门的， 分管校长可以查看自己管辖的部门
			sql1 += " and F_DEPT_ID in ("+deptIdStr+")";
			sql2 += " and F_DEPT_ID in ("+deptIdStr+")";
		}
		List<Object> objList = getSession().createSQLQuery(sql1 + " union all " + sql2).list();
		return objList;
	}

	@Override
	public Integer getExecutingContract(Depart depart, String orderType,
			String year, User user) {
		
		
		StringBuffer sb = new StringBuffer("");
		getSession().createSQLQuery(sb.toString()).list();
		
		
		return null;
	}

	@Override
	public Map<String, Double> getAssetsAmount() {
		Map<String, Double> map = new LinkedHashMap<>();
		StringBuffer str2 = new StringBuffer("SELECT ifnull(SUM(F_AMOUNT),0) from T_ASSET_BASIC_INFO WHERE F_ASS_TYPE = 'ZCLX-02' and F_USED_STAUTS!='ZCSYZT-03'");
		StringBuffer str3 = new StringBuffer("SELECT ifnull(SUM(F_AMOUNT),0) from T_ASSET_BASIC_INFO WHERE F_ASS_TYPE = 'ZCLX-03' and F_USED_STAUTS!='ZCSYZT-03'");
		
		List<Object> list2 = getSession().createSQLQuery(str2.toString()).list();
		List<Object> list3 = getSession().createSQLQuery(str3.toString()).list();
		
		Double d2 = (Double) list2.get(0);
		Double d3 = (Double) list3.get(0);
		
		map.put("all", d2+d3);
		map.put("ZCLX02", d2);
		map.put("ZCLX03", d3);
		return map;
	}

	@Override
	public List<Double> getAssetsAmount(String type) {
		Session session = getSession();
		String str0 = "SELECT ifnull(sum(tabi.F_AMOUNT),0) FROM T_ASSET_BASIC_INFO tabi where  tabi.F_ASS_TYPE='ZCLX-03' and tabi.F_USED_STAUTS='ZCSYZT-03'";
		List<Double> list0 = session.createSQLQuery(str0).list();
		StringBuffer str1 = new StringBuffer("SELECT ifnull(sum(tabi.F_AMOUNT),0) FROM T_ASSET_BASIC_INFO tabi where  tabi.F_ASS_TYPE='ZCLX-03';");
		List<Double> list1 = session.createSQLQuery(str1.toString()).list();

		//map.put("up", (Double) list.get(0));
		//map.put("down", (Double) list1.get(0));
		list1.add(list0.get(0));
		return list1;
	}

	@Override
	public List<Double> getFixedUpOrDownAmount() {
		Session session = getSession();
		String str0 = "SELECT ifnull(sum(tabi.F_AMOUNT),0) FROM T_ASSET_BASIC_INFO tabi where  tabi.F_ASS_TYPE='ZCLX-02' and tabi.F_USED_STAUTS='ZCSYZT-03'";
		List<Double> list0 = session.createSQLQuery(str0).list();
		StringBuffer str1 = new StringBuffer("SELECT ifnull(sum(tabi.F_AMOUNT),0) FROM T_ASSET_BASIC_INFO tabi where  tabi.F_ASS_TYPE='ZCLX-02';");
		List<Double> list1 = session.createSQLQuery(str1.toString()).list();

		//map.put("up", (Double) list.get(0));
		//map.put("down", (Double) list1.get(0));
		list1.add(list0.get(0));
		return list1;
	}

	@Override
	public Map<String, Object> getContractAmount(String department) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 当前年
		String currentYear = DateUtil.getCurrentYear();
		String year = currentYear + "-01-01 00:00:00";
		/*
		 * 新签未付款
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SUM( F_RECE_PLAN_AMOUNT ) AS total_amount FROM ");
		sb.append("( SELECT F_CONT_ID FROM t_contract_basic_info WHERE f_approve_time >= '" + year + "' ) t3 ");
		sb.append("INNER JOIN (");
		sb.append("SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t1 ");
		sb.append("WHERE t1.F_UPT_ID IS NULL ");
		sb.append("AND t1.F_STAUTS = '0' ");
		sb.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ");
		sb.append(") UNION ALL ");
		sb.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL AND t2.F_STAUTS = '0'");
		sb.append(") t4 ON t3.F_CONT_ID = t4.F_CONT_ID");
		String sql = sb.toString();
		Session session = getSession();
		List<Object> arrearageList = session.createSQLQuery(sql).list();
		if (arrearageList.get(0) == null) {
			resultMap.put("arrearage", 0);
		} else {
			resultMap.put("arrearage", arrearageList.get(0));
		}
		
		/*
		 * 新签已付款
		 */
		StringBuilder sb1 = new StringBuilder();
		sb1.append("SELECT SUM( F_RECE_PLAN_AMOUNT ) AS total_amount FROM ");
		sb1.append("( SELECT F_CONT_ID FROM t_contract_basic_info WHERE f_approve_time >= '" + year + "' ) t3 ");
		sb1.append("INNER JOIN (");
		sb1.append("SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t1 ");
		sb1.append("WHERE t1.F_UPT_ID IS NULL ");
		sb1.append("AND t1.F_STAUTS = '1' ");
		sb1.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ");
		sb1.append(") UNION ALL ");
		sb1.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL AND t2.F_STAUTS = '1'");
		sb1.append(") t4 ON t3.F_CONT_ID = t4.F_CONT_ID");
		String sql1 = sb1.toString();
		List<Object> paidList = session.createSQLQuery(sql1).list();
		if (paidList.get(0) == null) {
			resultMap.put("paid", 0);
		} else {
			resultMap.put("paid", paidList.get(0));
		}
		
		/*
		 * 结转未付款carryforward
		 */
		StringBuilder sb2 = new StringBuilder();
		sb2.append("SELECT SUM( F_RECE_PLAN_AMOUNT ) AS total_amount FROM ");
		sb2.append("( SELECT F_CONT_ID FROM t_contract_basic_info WHERE f_approve_time < '" + year + "' ) t3 ");
		sb2.append("INNER JOIN (");
		sb2.append("SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t1 ");
		sb2.append("WHERE t1.F_UPT_ID IS NULL ");
		sb2.append("AND t1.F_STAUTS = '0' ");
		sb2.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ");
		sb2.append(") UNION ALL ");
		sb2.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL AND t2.F_STAUTS = '0'");
		sb2.append(") t4 ON t3.F_CONT_ID = t4.F_CONT_ID");
		String sql2 = sb2.toString();
		List<Object> carryforwardArrearageList = session.createSQLQuery(sql2).list();
		if (carryforwardArrearageList.get(0) == null) {
			resultMap.put("carryforwardArrearage", 0);
		} else {
			resultMap.put("carryforwardArrearage", carryforwardArrearageList.get(0));
		}
		
		/*
		 * 结转已付款
		 */
		StringBuilder sb3 = new StringBuilder();
		sb3.append("SELECT SUM( F_RECE_PLAN_AMOUNT ) AS total_amount FROM ");
		sb3.append("( SELECT F_CONT_ID FROM t_contract_basic_info WHERE f_approve_time < '" + year + "' ) t3 ");
		sb3.append("INNER JOIN (");
		sb3.append("SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t1 ");
		sb3.append("WHERE t1.F_UPT_ID IS NULL ");
		sb3.append("AND t1.F_STAUTS = '1' ");
		sb3.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ");
		sb3.append(") UNION ALL ");
		sb3.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL AND t2.F_STAUTS = '1'");
		sb3.append(") t4 ON t3.F_CONT_ID = t4.F_CONT_ID");
		String sql3 = sb3.toString();
		List<Object> carryforwardPaidList = session.createSQLQuery(sql3).list();
		if (carryforwardPaidList.get(0) == null) {
			resultMap.put("carryforwardPaid", 0);
		} else {
			resultMap.put("carryforwardPaid", carryforwardPaidList.get(0));
		}
		
		/*
		 * 执行中已结算
		 */
		StringBuilder sb5 = new StringBuilder();
		sb5.append("SELECT SUM( F_RECE_PLAN_AMOUNT ) AS total_amount FROM ");
		sb5.append("(SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t1 WHERE t1.F_UPT_ID IS NULL AND t1.F_STAUTS = '1' ");
		sb5.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ");
		sb5.append(") UNION ALL ");
		sb5.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL AND t2.F_STAUTS = '1' ");
		sb5.append(") t4 WHERE t4.F_CONT_ID IN (SELECT DISTINCT ( t.F_CONT_ID ) FROM (");
		sb5.append(" SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT, t1.F_STAUTS FROM t_receiv_plan t1 WHERE t1.F_UPT_ID IS NULL ");
		sb5.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ) UNION ALL ");
		sb5.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT, t2.F_STAUTS FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL ) t ");
		sb5.append("WHERE t.F_STAUTS = '0') AND t4.F_CONT_ID NOT IN (SELECT F_CONT_ID FROM t_contract_basic_info WHERE F_CONT_STAUTS = '99' OR F_APPROVE_TIME IS NULL)");
		String sql5 = sb5.toString();
		List<Object> paidAllList = session.createSQLQuery(sql5).list();
		if (paidAllList.get(0) == null) {
			resultMap.put("paidAll", 0);
		} else {
			resultMap.put("paidAll", paidAllList.get(0));
		}
		
		/*
		 * 执行中未结算
		 */
		StringBuilder sb6 = new StringBuilder();
		sb6.append("SELECT SUM( F_RECE_PLAN_AMOUNT ) AS total_amount FROM ");
		sb6.append("(SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t1 WHERE t1.F_UPT_ID IS NULL AND t1.F_STAUTS = '0' ");
		sb6.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ");
		sb6.append(") UNION ALL ");
		sb6.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL AND t2.F_STAUTS = '0' ");
		sb6.append(") t4 WHERE t4.F_CONT_ID IN (SELECT DISTINCT ( t.F_CONT_ID ) FROM (");
		sb6.append(" SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT, t1.F_STAUTS FROM t_receiv_plan t1 WHERE t1.F_UPT_ID IS NULL ");
		sb6.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL ) UNION ALL ");
		sb6.append("SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT, t2.F_STAUTS FROM t_receiv_plan t2 WHERE t2.F_UPT_ID IS NOT NULL ) t ");
		sb6.append("WHERE t.F_STAUTS = '0') AND t4.F_CONT_ID NOT IN (SELECT F_CONT_ID FROM t_contract_basic_info WHERE F_CONT_STAUTS = '99' OR F_APPROVE_TIME IS NULL)");
		String sql6 = sb6.toString();
		List<Object> arrearageAllList = session.createSQLQuery(sql6).list();
		if (arrearageAllList.get(0) == null) {
			resultMap.put("arrearageAll", 0);
		} else {
			resultMap.put("arrearageAll", arrearageAllList.get(0));
		}
		
		/*
		 * 执行中的合同数
		 */
		StringBuilder sb7 = new StringBuilder();
		sb7.append("SELECT DISTINCT(t1.F_CONT_ID) FROM (SELECT t1.F_CONT_ID, t1.F_RECE_PLAN_AMOUNT, t1.F_STAUTS ");
		sb7.append("FROM t_receiv_plan t1 WHERE t1.F_UPT_ID IS NULL ");
		sb7.append("AND t1.F_CONT_ID NOT IN ( SELECT DISTINCT ( F_CONT_ID ) FROM t_receiv_plan WHERE F_UPT_ID IS NOT NULL)");
		sb7.append(" UNION ALL SELECT t2.F_CONT_ID, t2.F_RECE_PLAN_AMOUNT, t2.F_STAUTS FROM t_receiv_plan t2 ");
		sb7.append("WHERE t2.F_UPT_ID IS NOT NULL ) t1 WHERE t1.F_STAUTS = '0' AND t1.F_CONT_ID NOT IN (SELECT F_CONT_ID FROM t_contract_basic_info WHERE F_CONT_STAUTS = '99' OR F_APPROVE_TIME IS NULL)");
		String sql7 = sb7.toString();
		List<Object> list = session.createSQLQuery(sql7).list();
		int contractCount = list.size();
		resultMap.put("contractCount", contractCount);
		
		return resultMap;
	}

	public List<Object> getSYIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select MAX(CASE type WHEN '1' THEN a.firm_order_num ELSE 0 END)*10000 AS last_firm_order_num,"
				+ " MAX(CASE type WHEN '2' THEN a.firm_order_num ELSE 0 END)*10000 AS ben_firm_order_num"
				+ " from ( select '1' as type ,sum(F_SY_AMOUNT) as firm_order_num from t_budget_index_mgr as a "
				+ " WHERE a.f_index_type='1' union all select '2' as type ,sum(F_SY_AMOUNT) as firm_order_num"
				+ " from t_budget_index_mgr as a  WHERE a.f_index_type='0' )as  a";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}

	@Override
	public List<Object> getDJIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select MAX(CASE type WHEN '1' THEN a.firm_order_num ELSE 0 END)*10000 AS last_firm_order_num,"
				+ " MAX(CASE type WHEN '2' THEN a.firm_order_num ELSE 0 END)*10000 AS ben_firm_order_num"
				+ " from ( select '1' as type ,sum(F_DJ_AMOUNT) as firm_order_num from t_budget_index_mgr as a "
				+ " WHERE a.f_index_type='1' union all select '2' as type ,sum(F_DJ_AMOUNT) as firm_order_num"
				+ " from t_budget_index_mgr as a  WHERE a.f_index_type='0' )as  a";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}
	
	@Override
	public List<Object> getBXIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select MAX(CASE type WHEN '1' THEN a.firm_order_num ELSE 0 END)*10000 AS last_firm_order_num,"
				+ " MAX(CASE type WHEN '2' THEN a.firm_order_num ELSE 0 END)*10000 AS ben_firm_order_num"
				+ " from ( select '1' as type ,ifnull(sum(F_XD_AMOUNT),0)-ifnull(sum(F_SY_AMOUNT),0)-ifnull(sum(F_DJ_AMOUNT),0) as firm_order_num"
				+ " from t_budget_index_mgr as a  WHERE a.f_index_type='1' union all select '2' as type ,ifnull(sum(F_XD_AMOUNT),0)-ifnull(sum(F_SY_AMOUNT),0)-ifnull(sum(F_DJ_AMOUNT),0) as firm_order_num"
				+ " from t_budget_index_mgr as a  WHERE a.f_index_type='0' )as  a";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}

	@Override
	public List<Object> getMeetingIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select ifnull(sum(F_SY_AMOUNT),0) AS SY_AMOUNT,ifnull(sum(F_DJ_AMOUNT),0) AS DJ_AMOUNT,ifnull(sum(F_XD_AMOUNT),0)-ifnull(sum(F_SY_AMOUNT),0)-ifnull(sum(F_DJ_AMOUNT),0) AS RESULT_AMOUNT from t_pro_expend_detail WHERE F_SUB_NUM='30215' and F_SUB_NAME='会议费';";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}

	@Override
	public List<Object> getTrainIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select  ifnull(sum(F_SY_AMOUNT),0) AS SY_AMOUNT,ifnull(sum(F_DJ_AMOUNT),0) AS DJ_AMOUNT,ifnull(sum(F_XD_AMOUNT),0)-ifnull(sum(F_SY_AMOUNT),0)-ifnull(sum(F_DJ_AMOUNT),0) AS R_AMOUNT from t_pro_expend_detail WHERE F_SUB_NUM='30216' and F_SUB_NAME='培训费';";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}

	@Override
	public List<Object> getTravelIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select  ifnull(sum(F_SY_AMOUNT),0) AS SY_AMOUNT,ifnull(sum(F_DJ_AMOUNT),0) AS DJ_AMOUNT,ifnull(sum(F_XD_AMOUNT),0)-ifnull(sum(F_SY_AMOUNT),0)-ifnull(sum(F_DJ_AMOUNT),0) AS R_RESULT from t_pro_expend_detail WHERE F_SUB_NUM='30211' and F_SUB_NAME='差旅费';";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}

	@Override
	public List<Object> getReceptionIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select  ifnull(sum(F_SY_AMOUNT),0) AS SY_AMOUNT,ifnull(sum(F_DJ_AMOUNT),0) AS DJ_AMOUNT,ifnull(sum(F_XD_AMOUNT),0)-ifnull(sum(F_SY_AMOUNT),0)-ifnull(sum(F_DJ_AMOUNT),0) AS R_AMOUNT from t_pro_expend_detail WHERE F_SUB_NUM='30217' and F_SUB_NAME='公务接待费';";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}

	@Override
	public List<Object> getAbroadIndexDataSum(String year, User user) {
		Session session = getSession();
		String str0 = "select  ifnull(sum(F_SY_AMOUNT),0) AS SY_AMOUNT,ifnull(sum(F_DJ_AMOUNT),0) AS DJ_AMOUNT,ifnull(sum(F_XD_AMOUNT),0)-ifnull(sum(F_SY_AMOUNT),0)-ifnull(sum(F_DJ_AMOUNT),0) AS R_AMOUNT from t_pro_expend_detail WHERE F_SUB_NUM='30212' and F_SUB_NAME='因公出国（境）费用';";
		List<Object> list0 = session.createSQLQuery(str0).list();
		return list0;
	}

	@Override
	public List<Object> queryProIndexDataSum(String year, User user) {
		Session session = getSession();
		List<Object> list4 = new ArrayList<Object>();
		String str0 = "select F_B_INDEX_NAME from t_budget_index_mgr WHERE f_index_type='1' ORDER BY F_XD_AMOUNT DESC LIMIT 6;";
		List<Object> list0 = session.createSQLQuery(str0).list();
		String str1 = "select (F_XD_AMOUNT-F_SY_AMOUNT-F_DJ_AMOUNT)*10000 from t_budget_index_mgr WHERE f_index_type='1' ORDER BY F_XD_AMOUNT DESC LIMIT 6;";
		List<Object> list1 = session.createSQLQuery(str1).list();
		String str2 = "select F_SY_AMOUNT*10000 from t_budget_index_mgr WHERE f_index_type='1' ORDER BY F_XD_AMOUNT DESC LIMIT 6;";
		List<Object> list2 = session.createSQLQuery(str2).list();
		String str3 = "select F_DJ_AMOUNT*10000 from t_budget_index_mgr WHERE f_index_type='1' ORDER BY F_XD_AMOUNT DESC LIMIT 6;";
		List<Object> list3 = session.createSQLQuery(str3).list();
		list4.add(list0);
		list4.add(list1);
		list4.add(list2);
		list4.add(list3);
		return list4;
	}


	@Override
	public Map<String, Object[]> getFixedAvailableAmount(String type) {
		
		Map<String, Object[]> map = new LinkedHashMap<>();
		Session session = getSession();
		StringBuffer str = new StringBuffer("SELECT count(tabi.F_FIXED_TYPE_CODE) ftc ,tat.TYPENAME FROM T_ASSET_BASIC_INFO tabi ,t_asset_type tat where tabi.F_FIXED_TYPE_ID=tat.PID and tabi.F_ASS_TYPE='ZCLX-02' and tat.ASSET_TYPE='ZCLX-02' and tabi.F_AVAILABLE_STAUTS='ZCKYZT-01' and tabi.F_CONFIG_STAUTS=0 and tabi.F_USED_STAUTS='ZCSYZT-02' GROUP BY tabi.F_FIXED_TYPE_CODE,tat.TYPENAME ORDER BY ftc desc LIMIT 6");
		List<String[]> list = session.createSQLQuery(str.toString()).list();
		Object[] namestr = new Object[list.size()];
		Object[] amountstr = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Object[] s = list.get(i);
			namestr[i]=s[1];
			amountstr[i]=(Object)s[0];
		}
		
		map.put("namestr", namestr);
		map.put("amountstr", amountstr);
		return map;
	}

	@Override
	public Map<String, Double> getFixedAvailablePercentage() {
		
		Map<String, Double> map = new LinkedHashMap<>();
		Session session = getSession();
		StringBuffer strKY = new StringBuffer("SELECT ifnull(count(tabi.F_AVAILABLE_STAUTS),0) AS AVAILABLE_COUNT,ifnull(tabi.F_AVAILABLE_STAUTS,0) AS AVAILABLE_TYPE FROM T_ASSET_BASIC_INFO tabi where  tabi.F_ASS_TYPE='ZCLX-02' and tabi.F_USED_STAUTS!='ZCSYZT-03'");
		List<Object[]> listKY = session.createSQLQuery(strKY.toString()).list();
		StringBuffer strBKY = new StringBuffer("SELECT ifnull(count(tabi.F_AVAILABLE_STAUTS),0) AS BK1,ifnull(tabi.F_AVAILABLE_STAUTS,0) AS BK2 FROM T_ASSET_BASIC_INFO tabi where  tabi.F_ASS_TYPE='ZCLX-02' and tabi.F_USED_STAUTS='ZCSYZT-03'");
		List<Object[]> listBKY = session.createSQLQuery(strBKY.toString()).list();
		String str0 = String.valueOf(listKY.get(0)[0]);
		String str1 = String.valueOf(listBKY.get(0)[0]);
		
		Double d0 = Double.valueOf(str0);
		Double d1 = Double.valueOf(str1);
		Double d = d0+d1;
		if(d==0.00){
			map.put("ZCKYZT01", 0.00);
			map.put("ZCKYZT02", 0.00);
		}else {
			map.put("ZCKYZT01", (double)Math.round(d0/d*100)/100);
			map.put("ZCKYZT02", (double)Math.round(d1/d*100)/100);
		}
		return map;
	}
}
