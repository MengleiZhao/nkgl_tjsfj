package com.braker.core.manager.impl;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.SysLogMng;
import com.braker.core.model.SysLog;
@SuppressWarnings("unchecked")
@Transactional
@Service
public class SysLogMngImpl extends BaseManagerImpl<SysLog> implements SysLogMng{

	@Override
	public Pagination list(SysLog log, String sort, String order,
			int pageIndex, int pageSize) {
		
		Finder f=Finder.create("from SysLog Where flag='1'");
		if(null!=log){
			if(!StringUtil.isEmpty(log.getLogName())){
				f.append(" and creator.name like :name");
				f.setParam("name","%"+log.getLogName()+"%");
			}
			if(!StringUtil.isEmpty(log.getOperateContent())){
				f.append(" and operateContent like :operateContent");
				f.setParam("operateContent", "%"+log.getOperateContent()+"%");
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				//操作居委
				if (!StringUtil.isEmpty(log.getJwhId())) {
					f.append(" and creator.jwh.id =:jwhId").setParam("jwhId", log.getJwhId());
				}
				//操作时间
				if(!StringUtil.isEmpty(log.getStartTime())){
					f.append(" and createTime >=:startTime").setParam("startTime",sdf.parse(log.getStartTime()));
				}
				if(!StringUtil.isEmpty(log.getEndTime())){
					f.append(" and createTime <=:endTime").setParam("endTime",sdf.parse(log.getEndTime()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		f.append(" order by createTime desc");
		return super.find(f, pageIndex, pageSize);
	}

	@Override
	public void archive(String archiveDate) {
		
		StringBuffer sql=new StringBuffer("insert into SYS_OPERATE_LOG_HIS");
		sql.append("(pflag,creator,createtime,pupdater,pupdatetime,operate_url,operate_content,archive_time)");
		sql.append(" select pflag,creator,createtime,pupdater,pupdatetime,operate_url,operate_content,'"+archiveDate+"' from SYS_OPERATE_LOG");
		sql.append(" Where convert(nvarchar(10),createtime,20)<'"+archiveDate+"';");
		sql.append(" delete from SYS_OPERATE_LOG Where convert(nvarchar(10),createtime,20)<'"+archiveDate+"';");
		super.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}

}
