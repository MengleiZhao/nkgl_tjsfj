package com.braker.icontrol.budget.project.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.braker.icontrol.budget.project.manager.TProCheckInfoMng;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;

/**
 * 项目审批的service实现类
 * @author 叶崇晖
 * @createtime 2018-09-21
 * @updatetime 2018-09-21
 */
@Service
@Transactional
public class TProCheckInfoMngImpl extends BaseManagerImpl<TProcessCheck> implements TProCheckInfoMng {
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	@Autowired
	private TNodeDataMng tNodeDataMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	/**
	 * 一上
	 */
	
	/*
	 * 一上审批
	 * @author 叶崇晖
	 * @createtime 2018-09-25
	 * @updatetime 2018-09-25
	 */
	@Override
	public void firstUpCheck(String fproIdLi, String result, String remake, User user ,Role role,String files)  throws Exception{
		String[] strarray = fproIdLi.split(",");
		TProcessCheck checkBean=new TProcessCheck(result,remake);
		List<TProBasicInfo> indexList = new ArrayList<TProBasicInfo>();
		for (int i = 0; i < strarray.length; i++) {
			TProBasicInfo bean = tProBasicInfoMng.findById(Integer.valueOf(strarray[i]));
			CheckEntity entity=(CheckEntity)bean;
			String checkUrl="/declare/verdict/";
			String lookUrl ="/project/detail/";
			bean=(TProBasicInfo)tProcessCheckMng.checkProcess(checkBean,entity,user,"YSSB",checkUrl,lookUrl,files);
			super.saveOrUpdate(bean);
			indexList.add(bean);
		}
	}

	
	/**
	 * 二上
	 */
	
	/*
	 * 二上审批
	 * @author 叶崇晖
	 * @createtime 2018-09-29
	 * @updatetime 2018-09-29
	 */
	@Override
	public void secondUpCheck(String fproIdLi, String result, String remake, User user, Role role,String files)  throws Exception{
		String[] strarray = fproIdLi.split(",");
		TProcessCheck checkBean=new TProcessCheck(result,remake);
		List<TProBasicInfo> indexList = new ArrayList<TProBasicInfo>();
		String ywfw = null;
		for (int i = 0; i < strarray.length; i++) {
			TProBasicInfo bean = tProBasicInfoMng.findById(Integer.valueOf(strarray[i]));
			CheckEntity entity=(CheckEntity)bean;
			String checkUrl="/declare/verdict2/";
			String lookUrl ="/project/detail/";
			if(bean.getFProOrBasic()==0){
				ywfw = "JBZCESSB";
			}else if(bean.getFProOrBasic()==1){
				ywfw = "ESSB";
			}
			bean=(TProBasicInfo)tProcessCheckMng.checkProcess(checkBean,entity,user,ywfw,checkUrl,lookUrl,files);
			super.saveOrUpdate(bean);
			indexList.add(bean);
		}
		if( "39".equals(indexList.get(0).getFFlowStauts())){
			//生成二下通过的指标
			budgetIndexMgrMng.createIndex(indexList);
		}
	}
	
	
	
	/*
	 * 上报库审批结果数据查询
	 * @author 叶崇晖
	 * @createtime 2018-09-25
	 * @updatetime 2018-09-25
	 */
	@Override
	public Pagination checkInfoPageList(TProcessCheck bean ,int pageNo, int pageSize, User user, String type) {
		List<TProcessDefin> tProcessDefinList= new ArrayList<TProcessDefin>();
		if("2".equals(type)){//一上申报
			tProcessDefinList=tProcessDefinMng.findTProcessDefinsByFBusiArea("YSSB");
		}
		if("3".equals(type)){//二上申报
			tProcessDefinList=tProcessDefinMng.findTProcessDefinsByFBusiAreas("'ESSB','JBZCESSB'");
		}
		String fpids=list2string(tProcessDefinList);
		StringBuilder builder=new StringBuilder();
		builder.append("select * FROM  T_PROCESS_CHECK tpc where F_P_ID in ("+fpids+") and F_USER_ID='"+user.getId()+"' ");
		if(!StringUtil.isEmpty(bean.getFProCode()) && !StringUtil.isEmpty(bean.getFProName())){
			builder.append(" AND  EXISTS (select tpbi.F_PRO_ID FROM   t_pro_basic_info tpbi  where  tpbi.F_PRO_ID= tpc.F_O_ID  and tpbi.F_PRO_CODE like '%"+bean.getFProCode()+"%' "
					+ " and tpbi.F_PRO_NAME like  '%"+bean.getFProName()+"%'   ) ");
		}
		else {
			if(!StringUtil.isEmpty(bean.getFProCode())){ //按项目编号模糊查询
			builder.append(" AND  EXISTS (select tpbi.F_PRO_ID FROM   t_pro_basic_info tpbi  where  tpbi.F_PRO_ID= tpc.F_O_ID  and tpbi.F_PRO_CODE like '%"+bean.getFProCode()+"%' ) ");
			}
			if(!StringUtil.isEmpty(bean.getFProName())){ //按项目名称模糊查询
				builder.append(" and EXISTS (select tpbi.F_PRO_ID from   t_pro_basic_info tpbi where  tpbi.F_PRO_ID= tpc.F_O_ID and tpbi.F_PRO_NAME like  '%"+bean.getFProName()+"%')  ");
			}
			if (!StringUtil.isEmpty(bean.getFProOrBasic())) {//按支出类型查询审批记录
				builder.append(" and EXISTS (select tpbi.F_PRO_ID from   t_pro_basic_info tpbi where  tpbi.F_PRO_ID= tpc.F_O_ID and tpbi.PROORBASIC =  '"+bean.getFProOrBasic()+"')  ");
			}
		}
		
		return super.findBySql(bean,builder.toString(), pageNo, pageSize);
	}
	
	private String list2string(List<TProcessDefin> tProcessDefinList){
		String fpids="";
		for(TProcessDefin process: tProcessDefinList){
			if("".equals(fpids)){
				fpids=process.getFPId()+"";
			}else{
				fpids=fpids+","+process.getFPId();
			}
		}
		return fpids;
		
	}

	@Override
	public List<TProcessCheck> getCheckList(Integer id, String type, String stauts) {
		List<TProcessDefin> tProcessDefinList= new ArrayList<TProcessDefin>();
		if("2".equals(type)){//一上申报
			tProcessDefinList=tProcessDefinMng.findTProcessDefinsByFBusiArea("YSSB");
		}
		if("3".equals(type)){//二上申报
			tProcessDefinList=tProcessDefinMng.findTProcessDefinsByFBusiArea("ESSB");
		}
		TProBasicInfo proBasicInfo=tProBasicInfoMng.findById(id);
		String foCode=proBasicInfo.getBeanCode();
		String fpids=list2string(tProcessDefinList);
		Finder finder = Finder.create(" FROM TProcessCheck WHERE foCode'"+foCode+"' AND FPId in ("+fpids+")");
		if(stauts != null) {
			finder.append(" AND stauts='"+stauts+"'");
		}
		return super.find(finder);
	}

	
}
