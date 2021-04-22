package com.braker.icontrol.cgmanage.cgprocess.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgprocess.model.BiddingRegist;
import com.braker.icontrol.cgmanage.cgsupplier.model.WinningBidder;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;
import com.braker.workflow.entity.TProcessCheck;

/**
 * 采购过程登记的service抽象类
 * @author 冉德茂
 * @createtime 2018-07-23
 * @updatetime 2018-07-23
 */
public interface CgProcessMng extends BaseManager<BiddingRegist>{
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-23
	 * @updatetime 2018-07-23
	 */
	public Pagination pageList(PurchaseApplyBasic bean,User user, int pageNo, int pageSize,String searchData);
	
	/*
	 * 招标保存
	 * @author 冉德茂
	 * @createtime 2018-07-24
	 * @updatetime 2018-07-24
	 
	public void save(BiddingRegist bean, WinningBidder bean2, String files, User user) throws Exception;*/
	
	/**
	 *获取原有的中标和供应商信息
	 * @author 焦广兴
	 * @createtime 2019-05-30
	 * @updatetime 2019-05-30
	 */
	public List<Object> getMingxi(String tableName, String idName, Integer pid);
	
	/**
	 * 
	 * @Title save 
	 * @Description 采购过程登记保存
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param brBean
	 * @param pabBean
	 * @param files
	 * @param user
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	public void save(RegisterApplyBasic rBean, PurchaseApplyBasic pabBean,User user,String jsonList,String supJsonList,String files01,String files02,String files03,String files04,String files05) throws Exception;
	
	/**
	 * 
	 * @Title checkPageList 
	 * @Description 审批分页查询
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @return Pagination
	 * @throws
	 */
	public Pagination checkPageList(PurchaseApplyBasic bean, Integer pageNo, Integer pageSize, User user,String searchData);
	
	/**
	 * 
	 * @Title saveCheck 
	 * @Description 保存审批信息
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param checkBean
	 * @param bean
	 * @param files
	 * @param user
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveCheck(TProcessCheck checkBean, RegisterApplyBasic bean, String files, User user) throws Exception;
	
	/**
	 * 
	 * @Title findFbIdByFpId 
	 * @Description 根据采购申请主键查询过程登记主键
	 * @author 汪耀
	 * @Date 2020年3月10日 
	 * @param id
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer findFbIdByFpId(Integer id);
	
	/**
	 * 
	 * @Title reCall 
	 * @Description 采购过程登记撤回
	 * @author 汪耀
	 * @Date 2020年2月24日 
	 * @param id
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public String reCall(Integer id) throws Exception;
	
	/**
	 * 根据采购羡慕主键id和供应商id查询供应商信息
	 * @param FpId
	 * @param code
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月19日
	 * @updator 陈睿超
	 * @updatetime 2020年6月19日
	 */
	public List<BiddingRegist> findFbIdByFpId(Integer FpId,String id,String fContractstatus);

	/**
	 * 
	 * @param code
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月22日
	 * @updator 陈睿超
	 * @updatetime 2020年7月22日
	 */
	BiddingRegist findbycode(String code,String fContractstatus);

}
