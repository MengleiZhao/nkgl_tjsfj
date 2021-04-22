package com.braker.icontrol.incomemanage.quotaManage.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.PoiUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.impl.DepartMngImpl;
import com.braker.core.model.Depart;
import com.braker.core.model.EconomicClassificationGovernment;
import com.braker.core.model.Lookups;
import com.braker.core.model.User;
import com.braker.exception.ServiceException;
import com.braker.icontrol.budget.manager.entity.TBudgetIndexMgr;
import com.braker.icontrol.expend.loan.manager.LoanMng;
import com.braker.icontrol.incomemanage.capital.model.QuotaMangeInfo;
import com.braker.icontrol.incomemanage.quotaManage.manager.QuotaManageMng;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 额度管理的service实现类
 * @author 沈帆
 * @createtime 2021-02-20
 * @updatetime 2021-02-20
 */
@Service
@Transactional
public class QuotaManageMngImpl extends BaseManagerImpl<QuotaMangeInfo> implements QuotaManageMng {
	
	@Autowired
	private LoanMng loanMng;
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private ShenTongMng shenTongMng;
	/*
	 * 分页查询
	 * @author 沈帆
	 * @createtime 2021-02-20
	 * @updatetime 2021-02-20
	 */
	@Override
	public Pagination quotaPage(QuotaMangeInfo bean, Integer pageNo, Integer pageSize, User user,String searchData) {

		Finder finder =Finder.create("  FROM QuotaMangeInfo WHERE fStauts <>"+99+" ");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fSubName like '%"+searchData+"%' or fOperateUser like '%"+searchData+"%' or fAmount like '%"+searchData+"%' or TO_DATE(fOperateTime,'yyyy-mm-dd') like '%"+searchData+"%' or IFNULL(fCheckUser,'') like '%"+searchData+"%' or IFNULL(DATE(fChcekTime),'') like '%"+searchData+"%') ");
		}
		finder.append(" order by fCheckStauts,fOperateTime desc");
		return super.find(finder,pageNo,pageSize);
	}
	
	/*
	 * 复核分页查询
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@Override
	public Pagination quotaCheckPage(QuotaMangeInfo bean, Integer pageNo, Integer pageSize, User user,String searchData) {

		Finder finder =Finder.create("  FROM QuotaMangeInfo WHERE fStauts <>"+99+" ");
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fSubName like '%"+searchData+"%' or fOperateUser like '%"+searchData+"%' or fAmount like '%"+searchData+"%' or TO_DATE(fOperateTime,'yyyy-mm-dd') like '%"+searchData+"%' or IFNULL(fCheckUser,'') || IFNULL(DATE(fChcekTime),'') like '%"+searchData+"%') ");
		}
		finder.append(" and fCheckStauts not in ('0','-4') ");
		finder.append(" order by fOperateTime desc");
		return super.find(finder,pageNo,pageSize);
	}
	/*
	 * 保存额度登记信息
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@Override
	public void save(QuotaMangeInfo bean, User user) {
		if(bean.getfQId()==null){
			bean.setfQId(shenTongMng.getSeq("quota_manage_info_seq"));
			bean.setCreator(user.getName());//创建人
			bean.setCreateTime(new Date());//创建时间
		}
		bean.setfOperateUser(user.getName());
		bean.setfStauts("1");//数据删除状态
		
		super.merge(bean);
		
		
		
}
	/*
	 * 根据id删除
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@Override
	public void delete(Integer id) {
		QuotaMangeInfo bean = super.findById(id);
		bean.setfStauts("99");
		super.saveOrUpdate(bean);
	}
	
	/*
	 * 撤回
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@Override
	public void recall(Integer id) {
		QuotaMangeInfo bean = super.findById(id);
		bean.setfCheckStauts("-4");
		super.saveOrUpdate(bean);
	}
	
	@Override
	public List<Lookups> getLookupsJson(String categoryCode,String blanked, String selected) {
		List<Lookups> list = new ArrayList<Lookups>();
		selected = StringUtil.isEmpty(selected)?null:selected;
		
		List<EconomicClassificationGovernment> ecomList = this.getEconomicList(selected);
		for (int i = 0; i < ecomList.size(); i++) {
			Lookups lookups = new Lookups();
			EconomicClassificationGovernment ec = ecomList.get(i);
			lookups.setCode(ec.getCode());
			lookups.setName("["+ec.getCode()+"]"+ec.getName());
			list.add(lookups);
		}
		return list;
	}
	
	@Override
	public List<EconomicClassificationGovernment> getEconomicList(String code) {
		Finder finder = Finder.create(" FROM EconomicClassificationGovernment WHERE 1=1");
		finder.append(" and status = '1' and year = year(now) ");
		if(!StringUtil.isEmpty(code)){
			finder.append(" AND code ='"+code+"'");
		}
		return super.find(finder);
	}
	
	
	/*
	 * 保存额度复核
	 * @author 沈帆
	 * @createtime 2021-02-23
	 * @updatetime 2021-02-23
	 */
	@Override
	public void saveCheckResult(String ids, String result, User user) {
		String[] qIds = ids.split(",");
		for (String id: qIds) {
			QuotaMangeInfo quota =  super.findById(Integer.valueOf(id));
			if("0".equals(result)){
				quota.setfCheckStauts("-1");
			}else{
				quota.setfCheckStauts("9");
			}
			quota.setfChcekTime(new Date());
			quota.setfCheckUser(user.getName());
			super.saveOrUpdate(quota);
		}
		
	}
	
	/*
	 * 保存导入的模板文件
	 * @author 沈帆
	 * @createtime 2021-02-24
	 * @updatetime 2021-02-24
	 */
	@Override
	public void saveFile(File file, User user) throws Exception {
		PoiUtil pu = new PoiUtil();
		if (file.exists()) {
			FileInputStream fis = null;
			Workbook workBook = null;
			try {
				fis = new FileInputStream(file);
				workBook = WorkbookFactory.create(fis);
				org.apache.poi.ss.usermodel.Sheet sheetAt = workBook.getSheetAt(0);
				int rowsOfSheet = sheetAt.getPhysicalNumberOfRows(); // 获取当前Sheet的总列数

				
				for (int r = 1; r < rowsOfSheet; r++) { // 从第二行开始
					QuotaMangeInfo bean = new QuotaMangeInfo();
					bean.setfQId(shenTongMng.getSeq("quota_manage_info_seq"));
					bean.setCreator(user.getName());//创建人
					bean.setCreateTime(new Date());//创建时间
					bean.setfOperateTime(new Date());
					bean.setfOperateUser(user.getName());
					bean.setfStauts("1");//数据删除状态
					bean.setfCheckStauts("1");//默认为提交
					Row row = sheetAt.getRow(r);
					if (row == null) {
						continue;
					} else {
						int numberOfCells = row.getPhysicalNumberOfCells();
						for (int c = 0; c < numberOfCells; c++) { // 总列(格)
							Cell cell = row.getCell(c);
							if(c==1) {
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								String subCode =stringCellValue.substring(stringCellValue.indexOf("[") + 1, stringCellValue.lastIndexOf("]"));
								bean.setfSubCode(subCode);
								bean.setfSubName(stringCellValue);
							}
							else if(c==2) {
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String stringCellValue = pu.getCellValue(cell);
								if(StringUtil.isEmpty(stringCellValue)){
									continue;
								}
								bean.setfAmount(new BigDecimal(stringCellValue));
							}
						}
					}
					super.merge(bean);
				}
				
				file.delete();//删除临时文件
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("读取excel文件报错,请检查excel格式");
				//e.printStackTrace();
			}finally{
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new ServiceException("文件不存在");
		}
	}
	
	@Override
	public List<QuotaMangeInfo> getCurrencyYearData(List<EconomicClassificationGovernment> ecomList){
		List<QuotaMangeInfo> li =new ArrayList<QuotaMangeInfo>();
		if(ecomList!=null&&ecomList.size()>0){
			for (EconomicClassificationGovernment ec : ecomList) {
				QuotaMangeInfo quota = new QuotaMangeInfo();
				//统计政府支出经济分类总额度
				String sql="select sum(F_AMOUNT) from T_QUOTA_MANAGE_INFO where F_SUB_CODE = '"+ec.getCode()+"' and F_CHECK_STAUTS ='9'  and year(F_CHECK_TIME) = year(now) ";
				Query query = getSession().createSQLQuery(sql);
				List<Object[]> list = query.list();
				BigDecimal skAmount =new BigDecimal("0.00");
				if(list.get(0)!=null){
					skAmount = new BigDecimal(String.valueOf(list.get(0)));
				}
				//统计报销额度
				String bxSql="SELECT SUM(AMOUNT) FROM ( SELECT SUM( F_AMOUNT ) AS AMOUNT FROM T_REIMB_APPLI_BASIC_INFO";
				bxSql=bxSql+" WHERE F_PRO_DETAIL_ID IN (SELECT F_EXP_ID FROM T_PRO_EXPEND_DETAIL";
				bxSql=bxSql+" WHERE F_SUB_NUM IN (SELECT F_EC_CODE FROM T_ECONOMIC_SUBJECT_LIB";
				bxSql=bxSql+" WHERE F_GOVEMENT_CODE = '"+ec.getCode()+"' AND F_Y_B_ID IN (SELECT F_Y_B_ID FROM T_YEARS_BASIC WHERE F_PERIOD = YEAR(NOW))))";
				bxSql=bxSql+" AND F_CASHIER_TYPE = '1' AND YEAR(F_CASHIER_TIME) = YEAR(NOW)";
				bxSql=bxSql+" UNION SELECT SUM( F_AMOUNT ) AS AMOUNT FROM T_DIRECTLY_REIMB_APPLI_BASIC_INFO";
				bxSql=bxSql+" WHERE F_PRO_DETAIL_ID IN (SELECT F_EXP_ID FROM T_PRO_EXPEND_DETAIL";
				bxSql=bxSql+" WHERE F_SUB_NUM IN (SELECT F_EC_CODE FROM T_ECONOMIC_SUBJECT_LIB";
				bxSql=bxSql+" WHERE F_GOVEMENT_CODE = '"+ec.getCode()+"' AND F_Y_B_ID IN (SELECT F_Y_B_ID FROM T_YEARS_BASIC WHERE F_PERIOD = YEAR(NOW))))";
				bxSql=bxSql+" AND F_CASHIER_TYPE = '1' AND YEAR(F_CASHIER_TIME) = YEAR(NOW) )";
				Query bxQuery = getSession().createSQLQuery(bxSql);
				List<Object[]> bxlist = bxQuery.list();
				BigDecimal bxAmount =new BigDecimal("0.00");
				if(bxlist.get(0)!=null){
					bxAmount = new BigDecimal(String.valueOf(bxlist.get(0)));
				}
				//组装数据
				quota.setfSubCode(ec.getCode());
				quota.setfSubName("["+ec.getCode()+"]"+ec.getName());
				quota.setSkAmount(skAmount);
				quota.setBxAmount(bxAmount);
				li.add(quota);
			}
		}
	
		
		return li;
	}
}


