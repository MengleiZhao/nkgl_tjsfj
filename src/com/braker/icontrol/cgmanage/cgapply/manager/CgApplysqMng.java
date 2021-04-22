package com.braker.icontrol.cgmanage.cgapply.manager;


import java.util.List;



import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgapply.model.BuyInfo;
import com.braker.icontrol.cgmanage.cgreveive.model.AcceptCheck;
import com.braker.icontrol.purchase.apply.model.PurchaseApplyBasic;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TProcessCheck;

/**
 * 采购申请的service抽象类
 * @author 冉德茂
 * @createtime 2018-07-10
 * @updatetime 2018-07-10
 */
public interface CgApplysqMng extends BaseManager<PurchaseApplyBasic>{
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-10
	 * @updatetime 2018-07-10
	 */
	public Pagination pageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData);
	
	/**
	 * 分页采购文件查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	public Pagination cgFileAffirmPage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData);
	/**
	 * 分页采购文件确认
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	public Pagination cgFilePage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData);
	
	
	/**
	 * 采购执行方式确认查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月16日
	 * @updator 陈睿超
	 * @updatetime 2020年6月16日
	 */
	public Pagination executioncgsqPage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData);

	/**
	 * 合同选择采购项目查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年6月19日
	 * @updator 陈睿超
	 * @updatetime 2020年6月19日
	 */
	public Pagination contractcgsqPage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user);
	
	/*
	 * 根据ID删除
	 * @author 冉德茂
	 * @createtime 2018-07-11
	 * @updatetime 2018-07-11
	 */
	public void delete(Integer id,String fId,User user);
	
	
	/*
	 * 采购申请的保存
	 * @author 冉德茂
	 * @createtime 2018-07-13
	 * @updatetime 2018-07-13
	 */
	public void save(PurchaseApplyBasic bean, String files,String jzyjfiles, String mingxi, User user, String czbmyjfiles,String hyzgbmyjfiles,String reasonIds) throws Exception ;
	
	/**
	 * 采购文件上传的保存
	 * @param bean
	 * @param files
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	public void saveFileUp(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile,User user) throws Exception ;
	
	/**
	 * 采购方式确认保存
	 * @param bean
	 * @param hyzgbmyjfiles
	 * @param czbmyjfiles
	 * @throws Exception
	 * @author 陈睿超
	 * @createtime 2020年6月16日
	 * @updator 陈睿超
	 * @updatetime 2020年6月16日
	 */
	public void executionSave(PurchaseApplyBasic bean, String  czbmyjfiles,String hyzgbmyjfiles) throws Exception ;
	
	/*
	 * 采购品目明细查询
	 * @author 冉德茂
	 * @createtime 2018-07-13
	 * @updatetime 2018-07-13
	 */
	public List<Object> getMingxi(String tableName ,String idName ,Integer id);
	
	
	/*
	 * 审批台账分页查询
	 * @author 冉德茂
	 * @createtime 2018-07-20
	 * @updatetime 2018-07-20
	 */
	public Pagination ledgerPageList(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData);
	
	/**
	 * 台账采购验收分页查询
	 * @author 赵孟雷
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @createtime 2020年7月10日
	 * @updator 赵孟雷
	 * @updatetime 2020年7月10日
	 */
	public Pagination ledgerReceiveList(AcceptCheck bean, int pageNo, int pageSize, User user);
	
	/*
	 * 根据fpid查询采购批次编号
	 * @author 冉德茂
	 * @createtime 2018-07-24
	 * @updatetime 2018-07-24
	 * 
	 */
	public List<PurchaseApplyBasic> getCodeById(Integer id);
	
	
	/*
	 * 询比价登记查看采购基本信息
	 * @author 冉德茂
	 * @createtime 2018-08-01
	 * @updatetime 2018-08-01
	 * 
	 */
	public Pagination qingdanpageList(PurchaseApplyBasic bean, Integer page,Integer rows, User user);
	
	/**
	 * 根据采购批次号
	 * @param fpCode
	 * @return
	 */
	PurchaseApplyBasic findbyfpCode(String fpCode);
	/*
	 * 根据采购批次Id获取合同金额
	 * @author 李安达
	 * @createtime 2018-11-29
	 * @updatetime 2018-11-29
	 * 
	 */
	String findFpAmountbyfpId(String fpId);
	
	/**
	 * 采购申请撤回
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String  reCall(Integer id)  throws Exception ;


	public List<BuyInfo> cgsqspJsonList(String parentCode,String amount);
	
	public List<BuyInfo> findByBuyId(Integer id);


	public List<BuyInfo> findByText(String fpPype);


	/**
	 * 列表查询
	 * @param bean
	 * @param page
	 * @param rows
	 * @param user
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月15日
	 * @updator wanping
	 * @updatetime 2021年3月15日
	 */
	public Pagination planPageList(Integer page, Integer rows, User user, String searchData);


	public Pagination pubPageList(PurchaseIntentionBasic bean, Integer page,
			Integer rows, User user, String searchData);


	public Pagination chooseCgPage(PurchaseApplyBasic bean, Integer page,
			Integer rows, String searchData);


	public Pagination cgQusetionPage(PurchaseApplyBasic bean, Integer page,
			Integer rows, String searchData);
	
	/**
	 * 分页采购备案登记查询
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	public Pagination cgRegisterAffirmPage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData,String index);
	/**
	 * 分页采购备案登记确认
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @param searchData
	 * @return
	 * @author 赵孟雷
	 * @createtime 2021年3月15日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月15日
	 */
	public Pagination cgRegisterPage(PurchaseApplyBasic bean, int pageNo, int pageSize, User user,String searchData);
	
	/**
	 * 采购备案登记的合同备案状态保存
	 * @param bean
	 * @param files
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	public void saveContractSts(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile,User user) throws Exception ;
	/**
	 * 采购备案登记的验收备案状态保存
	 * @param bean
	 * @param files
	 * @author 赵孟雷
	 * @createtime 2021年3月16日
	 * @updator 赵孟雷
	 * @updatetime 2021年3月16日
	 */
	public void saveCheckSts(PurchaseApplyBasic bean, String files,TProcessCheck checkBean,String spjlFile,User user) throws Exception ;

	public List<PurchaseApplyBasic> findByCondition(String currentDate);
}
