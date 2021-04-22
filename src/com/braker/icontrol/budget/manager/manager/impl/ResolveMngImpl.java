package com.braker.icontrol.budget.manager.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.model.PrivateInformation;
import com.braker.core.model.User;
import com.braker.icontrol.budget.manager.manager.BudgetIndexMgrMng;
import com.braker.icontrol.budget.manager.manager.ResolveMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;

/**
 * 控制数分解的service实现类
 * @author 叶崇晖
 * @createtime 2018-09-26
 * @updatetime 2018-09-26
 */
@Service
@Transactional
public class ResolveMngImpl extends BaseManagerImpl<TProBasicInfo> implements ResolveMng {
	@Autowired
	private BudgetIndexMgrMng budgetIndexMgrMng;
	@Autowired
	private PrivateInforMng privateInforMng;
	/*
	 * 控制数分解数据查询
	 * @author 叶崇晖
	 * @createtime 2018-09-26
	 * @updatetime 2018-09-26
	 */
	@Override
	public List<TProBasicInfo> pageList(TProBasicInfo bean, int pageNo, int pageSize, User user, String type) {
		Finder finder = Finder.create(" from TProBasicInfo where (FExt1 IS NULL OR FExt1!='0')");
		if("0".equals(type)) {
			//待分解项目
			finder.append(" and  FFlowStauts ='29' and FExt4 ='11' and FExt5 is null");
		} else if("1".equals(type)) {
			//已分解项目
			finder.append(" and  FExt5 in('0','1')");
		}
		if(bean.getPlanStartYear() != null) {
			String y = bean.getPlanStartYear();
			if(!"".equals(y)){
				finder.append(" and planStartYear='"+y+"'");
			}
			
		} else {
			String y = new Date().toString();
			y = y.substring(y.length()-4);
			finder.append(" and planStartYear='"+(Integer.valueOf(y)+1)+"'");
		}
		
		//查询条件
		if (!StringUtil.isEmpty(bean.getFProCode())) {//项目编码
			finder.append(" and FProCode like :fProCode").setParam("fProCode", "%" + bean.getFProCode() + "%");
		}
		if (!StringUtil.isEmpty(bean.getFProName())) {//名称
			finder.append(" and FProName like :name").setParam("name", "%" + bean.getFProName() + "%");
		}
		if (!StringUtil.isEmpty(String.valueOf(bean.getFProOrBasic()))) {//支出类型
			finder.append(" and FProOrBasic = :FProOrBasic").setParam("FProOrBasic",  bean.getFProOrBasic() );
		}
		
		finder.append(" order by updateTime desc");
		List<TProBasicInfo> list=super.find(finder);
		return list;
	}

	/*
	 * 控制数分解数据保存
	 * @author 叶崇晖
	 * @createtime 2018-09-26
	 * @updatetime 2018-09-26
	 */
	@Override
	public void save(String fproId, String provideAmount1, String fext12) {
		String[] id = fproId.split(",");
		String[] amount = provideAmount1.split(",");
		String[] remake = fext12.split(",");
		List<TProBasicInfo> indexli = new ArrayList<TProBasicInfo>();
		
		for (int i = 0; i < id.length; i++) {
			//新建一个消息推送信息
			TProBasicInfo bean = super.findById(Integer.valueOf(id[i]));
			String fext5 = "0";
			/*
			 * history：根据控制数数量判断是否进行二上审批 or 下达指标
			 * String fext5 = null;
			if(bean.getFProBudgetAmount() <= Double.valueOf(amount[i])) {
				fext5 = "1";
				//控制数多发，直接下达
				indexli.add(bean);
			} else {
				fext5 = "0";
				//控制数少发，推送二上审批
			}*/
			String remark = "";
			if (remake != null && remake.length > i) {
				remark = remake[i];
			}
			getSession().createSQLQuery("UPDATE t_pro_basic_info SET F_KZCB_1='"+Double.valueOf(amount[i])+"',F_EXT_12='"+remark+"',F_FLOW_STAUTS='0',F_EXT_4='20',F_EXT_5='"+fext5+"' WHERE F_PRO_ID='"+id[i]+"'").executeUpdate();
			String title=bean.getFProName()+"预算分解消息";
			String msg="您申请的  "+bean.getFProName()+"项目,项目编号：("+bean.getBeanCode()+")已分解完毕。请前往预算管理->项目库管理->上报库->二上申报菜单中查看分解结果。";
			privateInforMng.setMsg(title, msg, bean.getUserId(),"2");
		}
		/*//生成二下通过的指标
		budgetIndexMgrMng.createIndex(indexli);*/
	}

}
