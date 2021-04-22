package com.braker.icontrol.incomemanage.disbursement.manager;

import java.util.List;

import com.braker.common.entity.ComboboxBean;
import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.Role;
import com.braker.core.model.User;
import com.braker.icontrol.expend.apply.model.AbroadAppliInfo;
import com.braker.icontrol.expend.apply.model.ApplicationBasicInfo;
import com.braker.icontrol.expend.apply.model.MeetingAppliInfo;
import com.braker.icontrol.expend.apply.model.OfficeCar;
import com.braker.icontrol.expend.apply.model.ReceptionAppliInfo;
import com.braker.icontrol.expend.apply.model.TrainingAppliInfo;
import com.braker.icontrol.expend.apply.model.TravelAppliInfo;
import com.braker.icontrol.incomemanage.disbursement.model.Schedule;
import com.braker.workflow.entity.TProcessCheck;

public interface ScheduleManager extends BaseManager<Schedule> {
	
	/**
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	public Pagination pageList(Schedule bean, int pageNo, int pageSize,User user);
	
	/**
	 * 
	 * @param bean
	 * @param mingxi
	 * @param user
	 * @param recePeop
	 * @param files
	 * @throws Exception
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	public void save(Schedule bean, String mingxi, User user, String files ) throws Exception;
	
	/**
	 * 事前申请删除
	 * @param id
	 * @param user
	 * @param fId
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	public void delete(Integer id,User user,String fId);
	
	/**
	 * 
	 * @Description: 用款计划申请撤回
	 * @param @param id
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author 赵孟雷
	 * @date 2021年2月24日
	 */
	public String  ApplyReCall(Integer id)  throws Exception;
	
	
	/** 以下位置用于用款计划审批使用  */
	
	/**
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	public Pagination pageCheckList(Schedule bean, int pageNo, int pageSize,User user,String searchTitle);
	
	/**
	 * 保存审批信息
	 * @param checkBean
	 * @param bean
	 * @param mingxi
	 * @param user
	 * @param role
	 * @param files
	 * @throws Exception
	 * @author 赵孟雷
	 * @createtime 2021年2月24日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月24日
	 */
	public void saveCheckInfo(TProcessCheck checkBean, Schedule bean,User user,String files) throws Exception;
	
	
	
	/**
	 * 获取台账页面数据
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @author 赵孟雷
	 * @createtime 2021年2月20日
	 * @updator 赵孟雷
	 * @updatetime 2021年2月20日
	 */
	public Pagination pageLedgerList(Schedule bean, int pageNo, int pageSize,User user);

	/**
	 * 用款计划统计
	 * @param bean
	 * @param page
	 * @param rows
	 * @param user
	 * @return
	 * @author wanping
	 * @createtime 2021年2月25日
	 * @updator wanping
	 * @updatetime 2021年2月25日
	 */
	public Pagination scheduleStatistics(Schedule bean, Integer page, Integer rows, User user);

	/**
	 * 获取统计年份
	 * @return
	 * @author wanping
	 * @createtime 2021年2月26日
	 * @updator wanping
	 * @updatetime 2021年2月26日
	 */
	public List<ComboboxBean> getStatisticsYear();
}
