package com.braker.quartz.task;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.braker.common.util.DateUtil;
import com.braker.common.util.SpringContextUtil;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.EconomicMng;
import com.braker.core.manager.YearsBasicMng;
import com.braker.core.model.YearsBasic;

@Service
@Transactional
public class EconomicTask {

	
	private EconomicMng economicMng;
	
	private YearsBasicMng yearsBasicMng;
	
	/**
	 * 自动复制下一年度经济分类科目
	 * 
	 * @author 陈睿超
	 * @createtime 2021年2月2日
	 * @updator 陈睿超
	 * @updatetime 2021年2月2日
	 */
	public void copyYearEconomic(){
		try {
			//从IOC容器中获取相关抽象类
			ApplicationContext ac = SpringContextUtil.getApplicationContext();
			economicMng = ac.getBean(EconomicMng.class);
			yearsBasicMng = ac.getBean(YearsBasicMng.class);
			List<YearsBasic> yearBasiclist = yearsBasicMng.findByYear(String.valueOf(Integer.valueOf(DateUtil.getCurrentYear())+1));
			YearsBasic currentYearBasic = yearsBasicMng.findByYearBasic(DateUtil.getCurrentYear());
			if(yearBasiclist.size()<=0){//原来已经有该年度的经济分类数据模板了
				YearsBasic yb=new YearsBasic();
				yb.setfRemark("系统自动生成");
				yb.setFtName("系统自动生成"+(DateUtil.getCurrentYear()+1)+"年经济分类模板");
				yb.setfPeriod(String.valueOf(Integer.valueOf(DateUtil.getCurrentYear())+1));
				yb.setCreateTime(new Date());
				yb.setCreator("admin");
				yb.setFbId(StringUtil.random8());
				economicMng.copyEconomic(currentYearBasic.getFbId(),yb);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
