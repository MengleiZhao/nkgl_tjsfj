package com.braker.icontrol.budget.release.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.DateUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Depart;
import com.braker.core.model.User;
import com.braker.icontrol.budget.release.entity.TIndexDetail;
import com.braker.icontrol.budget.release.manager.IndexDetailMng;

@Service
@Transactional
public class IndexDetailMngImpl extends BaseManagerImpl<TIndexDetail> implements IndexDetailMng {
	@Autowired
	private UserMng userMng;
	
	@Override
	public List<TIndexDetail> findByIndexCode(String indexCode) {
		Finder finder = Finder.create(" FROM TIndexDetail WHERE indexCode='"+indexCode+"'");
		return super.find(finder);
	}
	
	@Override
	public Pagination searchByIndexCode(String indexCode,String userName,String time1,String time2,int pageNo, int pageSize) {
		StringBuilder sb=new StringBuilder("select * FROM T_INDEX_DETAIL WHERE F_INDEX_CODE='"+indexCode+"'");
		if(!StringUtil.isEmpty(userName)){
			sb.append(" AND F_USER_ID in (select pid from sys_user where name LIKE('%"+userName+"%'))");
		}
		if(!StringUtil.isEmpty(time1)){
			sb.append(" AND DATE_FORMAT(F_TIME,'%Y-%m-%d')>='"+time1+"'");
		}
		if(!StringUtil.isEmpty(time2)){
			sb.append(" AND DATE_FORMAT(F_TIME,'%Y-%m-%d')<='"+time2+"'");
		}
		sb.append(" order by f_time desc");
		String str=sb.toString();
		Pagination p = super.findBySql(new TIndexDetail(), str, pageNo, pageSize);
			List<TIndexDetail> dataList = (List<TIndexDetail>) p.getList();
			if (dataList != null && dataList.size() > 0) {
				int i = 0;
				for (TIndexDetail log: dataList) {
					log.setNum(pageNo * pageSize + i - (pageSize-1));
					i++;
				}
			}
		return p;
	}
	

	@Override
	public HSSFWorkbook exportExcel(List<TIndexDetail> index_dataList, String filePath) {
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb  = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			//按指标统计
			if (index_dataList != null && index_dataList.size() > 0) {
				HSSFRow row = sheet0.getRow(2);//格式行
				for (int i = 0 ; i < index_dataList.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(2+i);
					hssfRow.setHeight(row.getHeight());
					TIndexDetail data = index_dataList.get(i);
					
					HSSFCell cell0 = hssfRow.createCell(0);
					HSSFCell cell1 = hssfRow.createCell(1);
					HSSFCell cell2 = hssfRow.createCell(2);
					HSSFCell cell3 = hssfRow.createCell(3);
					HSSFCell cell4 = hssfRow.createCell(4);
					HSSFCell cell5 = hssfRow.createCell(5);
					HSSFCell cell6 = hssfRow.createCell(6);
					HSSFCell cell7 = hssfRow.createCell(7);
					
					cell0.setCellValue(i + 1);
					cell1.setCellValue((data.getTime()==null?0:data.getTime()).toString());
					User user = userMng.findById(data.getUserId());
					cell2.setCellValue(user.getName()==null?"":user.getName());
					cell3.setCellValue(data.getAmount()==null?0.00:-data.getAmount());
					if ("1".equals(data.getfType())) {
						cell4.setCellValue("收入");
					} else if ("2".equals(data.getfType())) {
						cell4.setCellValue("还款");
					}else if ("3".equals(data.getfType())) {
						cell4.setCellValue("直接报销");
					}else if ("4".equals(data.getfType())) {
						cell4.setCellValue("申请报销");
					}else if ("5".equals(data.getfType())) {
						cell4.setCellValue("借款");
					}else if ("6".equals(data.getfType())) {
						cell4.setCellValue("采购支付");
					}else if ("7".equals(data.getfType())) {
						cell4.setCellValue("合同报销");
					}
					cell5.setCellValue(data.getDepartment()==null?"":data.getDepartment());
					cell6.setCellValue(data.getIndexCode()==null?"":data.getIndexCode());
					cell7.setCellValue(data.getIndexName()==null?"":data.getIndexName());
					
					cell0.setCellStyle(row.getCell(0).getCellStyle());
					cell1.setCellStyle(row.getCell(1).getCellStyle());
					cell2.setCellStyle(row.getCell(2).getCellStyle());
					cell3.setCellStyle(row.getCell(3).getCellStyle());
					cell4.setCellStyle(row.getCell(4).getCellStyle());
					cell5.setCellStyle(row.getCell(5).getCellStyle());
					cell6.setCellStyle(row.getCell(6).getCellStyle());
					cell7.setCellStyle(row.getCell(7).getCellStyle());
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


	
	
	//fType:费用类型     1、收入	2、还款	3、直接报销	4、申请报销	5、借款	6、采购支付	7、合同报销
	@Override
	public Pagination indexDetailData1(Depart depart, String year,String fType,String type, int pageNo, int pageSize,String searchIndexName, String searchDeptName) {
		StringBuilder sb=new StringBuilder("SELECT t1.* FROM T_INDEX_DETAIL t1 WHERE");
		sb.append(" t1.F_AMOUNT>0 AND t1.F_TYPE!=1 and t1.F_TYPE!=2");
		
		if(!StringUtil.isEmpty(fType)){
			sb.append(" AND t1.F_TYPE='"+fType+"'"); //按费用类型
		}
		if(!StringUtil.isEmpty(type)){
			sb.append(" AND t1.F_INDEX_TYPE='"+type+"'"); //按指标类型 F_INDEX_TYPE
		}
		//判断是按 年份查询 还是按 年月查询
		if(year.length()<5){
			sb.append(" AND year(t1.F_TIME)='"+year+"'");
		}else{
			sb.append(" AND DATE_FORMAT(t1.F_TIME,'%Y-%m')='"+year+"'");
		}
		//验证权限
		if (depart != null) {
			String departType = depart.getType();
			if (Depart.TYPE_COM.equals(departType)) {
				//公司登录，获得所有子部门的
				sb.append(" AND t1.F_DEPT_CODE IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "') ");
			} else if (Depart.TYPE_DEPT.equals(departType)) {
				//部门登录，获取所属公司下所有子部门的
				sb.append(" AND t1.F_DEPT_CODE IN (SELECT PID FROM SYS_DEPART WHERE PARENTID='" + depart.getParent().getId() + "' AND TYPE='" + Depart.TYPE_DEPT + "') ");
			}
		}
		if(searchIndexName!=null && searchIndexName.length()>0){
			sb.append(" AND t1.F_INDEX_NAME LIKE ('%"+searchIndexName+"%')");
		}
		if(searchDeptName!=null && searchDeptName.length()>0){
			sb.append(" AND t1.F_DEPARTMENT LIKE ('%"+searchDeptName+"%')");
		}
		sb.append(" ORDER BY t1.F_TIME DESC");
		String str=sb.toString();
		Pagination p = super.findBySql(new TIndexDetail(), str, pageNo, pageSize);
			List<TIndexDetail> dataList = (List<TIndexDetail>) p.getList();
			if (dataList != null && dataList.size() > 0) {
				int i = 0;
				for (TIndexDetail log: dataList) {
					log.setNum(pageNo * pageSize + i - (pageSize-1));
					i++;
				}
			}
		return p;
	}
	@Override
	public Pagination searchByFtype(String indexCode,String userName,String time1,String time2,int pageNo, int pageSize,String fType ) {
		StringBuilder sb=new StringBuilder("select * FROM T_INDEX_DETAIL WHERE F_INDEX_CODE='"+indexCode+"' and F_TYPE ='"+fType+"'" );
		if(!StringUtil.isEmpty(userName)){
			sb.append(" AND F_USER_ID in (select pid from sys_user where name LIKE('%"+userName+"%'))");
		}
		if(!StringUtil.isEmpty(time1)){
			sb.append(" AND DATE_FORMAT(F_TIME,'%Y-%m-%d')>='"+time1+"'");
		}
		if(!StringUtil.isEmpty(time2)){
			sb.append(" AND DATE_FORMAT(F_TIME,'%Y-%m-%d')<='"+time2+"'");
		}
		sb.append(" order by f_time desc");
		String str=sb.toString();
		Pagination p = super.findBySql(new TIndexDetail(), str, pageNo, pageSize);
			List<TIndexDetail> dataList = (List<TIndexDetail>) p.getList();
			if (dataList != null && dataList.size() > 0) {
				int i = 0;
				for (TIndexDetail log: dataList) {
					log.setNum(pageNo * pageSize + i - (pageSize-1));
					i++;
				}
			}
		return p;
	}

	@Override
	public Map<String, Object> abroadamount(Integer year, Integer month) {
		
		Map<String, Object> map = new HashMap<>();
		//出国
		Double amount= amountbyYearAndMonth(year, month, "7");
		Integer count= countbyYearAndMonth(year, month, "7");
		Integer teamPersonNum= teamPersonNumbyYearAndMonth(year, month, "7");
		Double lastamount= amountbyYearAndMonth(year-1, month, "7");
		Integer lastcount= countbyYearAndMonth(year-1, month, "7");
		Integer lastteamPersonNum= teamPersonNumbyYearAndMonth(year-1, month, "7");
		
		map.put("amount", amount);
		map.put("lastamount", lastamount);
		map.put("count", count);
		map.put("lastcount", lastcount);
		map.put("teamPersonNum", teamPersonNum);
		map.put("lastteamPersonNum", lastteamPersonNum);
		
		return map;
	}
	
	/**
	 * 根据年度，月份，类型查询经费支出（元）
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	private Double amountbyYearAndMonth(Integer year, Integer month, String type){
		
		StringBuilder sb=new StringBuilder("SELECT IFNULL(sum( t1.F_AMOUNT),0) FROM t_index_detail t1, "
				+ "T_REIMB_APPLI_BASIC_INFO t2 WHERE t1.F_EXT_2 = t2.F_R_CODE ");
		if(!StringUtil.isEmpty(String.valueOf(year))&&!StringUtil.isEmpty(String.valueOf(month))){
			int lastDay = DateUtil.getLastDayOfMonth(year, month);
			sb.append("AND date( t1.F_TIME ) BETWEEN '"+year+"-"+month+"-01' and '"+year+"-"+month+"-" + lastDay + "'");
		}
		if(!StringUtil.isEmpty(type)){
			sb.append("AND T2.F_REIMB_TYPE IN ('"+type+"') ");
		}
		String sql=sb.toString();
		Query query = getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		return Double.valueOf(String.valueOf(list.get(0)));
	}
	
	/**
	 * 根据年度，月份，类型查询次数
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	private Integer countbyYearAndMonth(Integer year, Integer month, String type){
		
		StringBuilder sb=new StringBuilder("SELECT IFNULL(count(t2.F_R_ID),0) FROM t_index_detail t1, "
				+ "T_REIMB_APPLI_BASIC_INFO t2 WHERE t1.F_EXT_2 = t2.F_R_CODE ");
		if(!StringUtil.isEmpty(String.valueOf(year))&&!StringUtil.isEmpty(String.valueOf(month))){
			int lastDay = DateUtil.getLastDayOfMonth(year, month);
			sb.append("AND date( t1.F_TIME ) BETWEEN '"+year+"-"+month+"-01' and '"+year+"-"+month+"-"+lastDay+"'");
		}
		if(!StringUtil.isEmpty(type)){
			sb.append("AND T2.F_REIMB_TYPE IN ( "+type+" ) ");
		}
		String sql=sb.toString();
		Query query = getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		return Integer.valueOf(String.valueOf(list.get(0)));
	}
	
	/**
	 * 统计已付讫出国（境）人数（人次）
	 * @param year 月
	 * @param month 年
	 * @param type 类型
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年11月24日
	 * @updator 陈睿超
	 * @updatetime 2020年11月24日
	 */
	private Integer teamPersonNumbyYearAndMonth(Integer year, Integer month, String type){
		StringBuilder sb=new StringBuilder("SELECT TRUNCATE(IFNULL(SUM(taai.F_TEAM_PERSON_NUM),0),0) FROM T_ABROAD_APPLI_INFO taai LEFT JOIN T_REIMB_APPLI_BASIC_INFO trabi "
				+ "on taai.F_R_ID=trabi.F_R_ID LEFT JOIN t_index_detail t1 ON t1.F_EXT_2=trabi.F_R_CODE WHERE 1=1");
		if(!StringUtil.isEmpty(String.valueOf(year))&&!StringUtil.isEmpty(String.valueOf(month))){
			int lastDay = DateUtil.getLastDayOfMonth(year, month);
			sb.append(" AND date( t1.F_TIME ) BETWEEN '"+year+"-"+month+"-01' and '"+year+"-"+month+"-" + lastDay +"'");
		}
		if(!StringUtil.isEmpty(type)){
			sb.append(" AND trabi.F_REIMB_TYPE IN ('"+type+"') ");
		}
		String sql=sb.toString();
		Query query = getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		return Integer.valueOf(String.valueOf(list.get(0)));
	}

	private Integer rePeopNumbyYearAndMonth(Integer year, Integer month, String type){
		StringBuilder sb=new StringBuilder("SELECT TRUNCATE(IFNULL(SUM(trai.F_RECEPTION_PEOPLE_NUM),0),0) FROM T_RECEPTION_APPLI_INFO trai LEFT JOIN T_REIMB_APPLI_BASIC_INFO trabi "
				+ "on trai.F_R_ID=trabi.F_R_ID LEFT JOIN t_index_detail t1 ON t1.F_EXT_2=trabi.F_R_CODE WHERE 1=1");
		if(!StringUtil.isEmpty(String.valueOf(year))&&!StringUtil.isEmpty(String.valueOf(month))){
			int lastDay = DateUtil.getLastDayOfMonth(year, month);
			sb.append(" AND date( t1.F_TIME ) BETWEEN '"+year+"-"+month+"-01' and '"+year+"-"+month+"-"+lastDay+"'");
		}
		if(!StringUtil.isEmpty(type)){
			sb.append(" AND trabi.F_REIMB_TYPE IN ('"+type+"') ");
		}
		String sql=sb.toString();
		Query query = getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		return Integer.valueOf(String.valueOf(list.get(0)));
	}
	
	private Integer trAttendNumbyYearAndMonth(Integer year, Integer month, String type){
		StringBuilder sb=new StringBuilder("SELECT TRUNCATE(IFNULL(SUM(ttai.F_ATTEND_NUM),0),0) FROM T_TRAINING_APPLI_INFO ttai LEFT JOIN T_REIMB_APPLI_BASIC_INFO trabi "
				+ "on ttai.F_R_ID=trabi.F_R_ID LEFT JOIN t_index_detail t1 ON t1.F_EXT_2=trabi.F_R_CODE WHERE 1=1");
		if(!StringUtil.isEmpty(String.valueOf(year))&&!StringUtil.isEmpty(String.valueOf(month))){
			int lastDay = DateUtil.getLastDayOfMonth(year, month);
			sb.append(" AND date( t1.F_TIME ) BETWEEN '"+year+"-"+month+"-01' and '"+year+"-"+month+"-"+lastDay+"'");
		}
		if(!StringUtil.isEmpty(type)){
			sb.append(" AND trabi.F_REIMB_TYPE IN ('"+type+"') ");
		}
		String sql=sb.toString();
		Query query = getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		return Integer.valueOf(String.valueOf(list.get(0)));
	}
	
	private Integer travelPeopbyYearAndMonth(Integer year, Integer month, String type){
		StringBuilder sb=new StringBuilder("SELECT  count(ici.F_I_C_ID) FROM T_INVOICE_COUPON_INFO ici LEFT JOIN T_REIMB_APPLI_BASIC_INFO trabi "
				+ "on ici.F_R_ID=trabi.F_R_ID LEFT JOIN t_index_detail t1 ON t1.F_EXT_2=trabi.F_R_CODE WHERE 1=1");
		if(!StringUtil.isEmpty(String.valueOf(year))&&!StringUtil.isEmpty(String.valueOf(month))){
			int lastDay = DateUtil.getLastDayOfMonth(year, month);
			sb.append(" AND date( t1.F_TIME ) BETWEEN '"+year+"-"+month+"-01' and '"+year+"-"+month+"-"+lastDay+"'");
		}
		if(!StringUtil.isEmpty(type)){
			sb.append(" AND trabi.F_REIMB_TYPE IN ( "+type+" ) ");
		}
		String sql=sb.toString();
		Query query = getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		
		return Integer.valueOf(String.valueOf(list.get(0)));
	}
	
	@Override
	public Map<String, Object> receptionamount(Integer year, Integer month) {
		Map<String, Object> map = new HashMap<>();

		Double amount = amountbyYearAndMonth(year, month, "5");
		Integer count = countbyYearAndMonth(year, month, "5");
		Integer rePeopNum = rePeopNumbyYearAndMonth(year, month, "5");
		Double lastamount = amountbyYearAndMonth(year-1, month, "5");
		Integer lastcount = countbyYearAndMonth(year-1, month, "5");
		Integer lastrePeopNum = rePeopNumbyYearAndMonth(year-1, month, "5");
		
		map.put("amount", amount);
		map.put("lastamount", lastamount);
		map.put("count", count);
		map.put("lastcount", lastcount);
		map.put("teamPersonNum", rePeopNum);
		map.put("lastteamPersonNum", lastrePeopNum);
		return map;
	}

	@Override
	public Map<String, Object> meetingamount(Integer year, Integer month) {
		Map<String, Object> map = new HashMap<>();

		Double amount = amountbyYearAndMonth(year, month, "2");
		Double lastamount = amountbyYearAndMonth(year-1, month, "2");
		
		map.put("amount", amount);
		map.put("lastamount", lastamount);
		return map;
	}

	@Override
	public Map<String, Object> trainingamount(Integer year, Integer month) {
		Map<String, Object> map = new HashMap<>();

		Double amount = amountbyYearAndMonth(year, month, "3");
		Integer trAttendNum = trAttendNumbyYearAndMonth(year, month, "3");
		Double lastamount = amountbyYearAndMonth(year-1, month, "3");
		Integer lasttrAttendNum = trAttendNumbyYearAndMonth(year-1, month, "3");
		
		map.put("amount", amount);
		map.put("lastamount", lastamount);
		map.put("trAttendNum", trAttendNum);
		map.put("lasttrAttendNum", lasttrAttendNum);
		return map;
	}

	@Override
	public Map<String, Object> travelamount(Integer year, Integer month) {
		Map<String, Object> map = new HashMap<>();

		Double amount = amountbyYearAndMonth(year, month, "4");
		Integer travelPeop = travelPeopbyYearAndMonth(year, month, "4");
		Double lastamount = amountbyYearAndMonth(year-1, month, "4");
		Integer lasttravelPeop = travelPeopbyYearAndMonth(year-1, month, "4");
		
		map.put("amount", amount);
		map.put("lastamount", lastamount);
		map.put("travelPeop", travelPeop);
		map.put("lasttravelPeop", lasttravelPeop);
		return map;
	}
	
	
}
