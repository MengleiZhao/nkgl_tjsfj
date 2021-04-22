package com.braker.icontrol.purchase.apply.manager;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.braker.common.hibernate.BaseManager;
import com.braker.common.page.Pagination;
import com.braker.core.model.User;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TProcessCheck;

/**
 * 采购意向Service
 * 
 * @author wanping
 *
 */
public interface PurchaseIntentionMng extends BaseManager<PurchaseIntentionBasic> {

	/**
	 * 分页查询
	 * @param bean
	 * @param page
	 * @param rows
	 * @param user
	 * @param searchData
	 * @return
	 * @author 陈睿超
	 * @createtime 2021年3月10日
	 * @updator 陈睿超
	 * @updatetime 2021年3月10日
	 */
	Pagination pageList(PurchaseIntentionBasic bean, Integer page, Integer rows, User user, String searchData);

	/**
	 * 根据条件统计数量
	 * @return
	 * @author wanping
	 * @param currentDate 
	 * @createtime 2021年3月10日
	 * @updator wanping
	 * @updatetime 2021年3月10日
	 */
	List<PurchaseIntentionBasic> findByCondition(String currentDate);

	/**
	 * 意向公开申请新增保存
	 * @param bean
	 * @param files
	 * @param user
	 * @author wanping
	 * @createtime 2021年3月10日
	 * @updator wanping
	 * @updatetime 2021年3月10日
	 */
	void save(PurchaseIntentionBasic bean, String files, User user) throws Exception;

	/**
	 * 意向公开申请删除
	 * @param id
	 * @param fId
	 * @param user
	 * @author wanping
	 * @createtime 2021年3月11日
	 * @updator wanping
	 * @updatetime 2021年3月11日
	 */
	void delete(Integer id, String fId, User user);

	/**
	 * 意向公开申请撤回
	 * @param id
	 * @author wanping
	 * @createtime 2021年3月11日
	 * @updator wanping
	 * @updatetime 2021年3月11日
	 */
	void reCall(Integer id);

	/**
	 * 意向公开审批
	 * @param checkBean
	 * @param bean
	 * @param user
	 * @param spjlFile
	 * @author 陈睿超
	 * @createtime 2021年3月11日
	 * @updator 陈睿超
	 * @updatetime 2021年3月11日
	 */
	void check(TProcessCheck checkBean, PurchaseIntentionBasic bean, User user, String spjlFile) throws Exception;

	/**
	 * 意向公开审批分页查询
	 * @param bean
	 * @param page
	 * @param rows
	 * @param user
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月11日
	 * @updator wanping
	 * @updatetime 2021年3月11日
	 */
	Pagination checkPageList(PurchaseIntentionBasic bean, Integer page, Integer rows, User user, String searchData);

	/**
	 * 公开完成确认分页
	 * @param bean
	 * @param page
	 * @param rows
	 * @param user
	 * @param searchData
	 * @return
	 * @author wanping
	 * @createtime 2021年3月12日
	 * @updator wanping
	 * @updatetime 2021年3月12日
	 */
	Pagination pubPageList(PurchaseIntentionBasic bean, Integer page, Integer rows, User user, String searchData);

	/**
	 * 公开确认
	 * @param id
	 * @author wanping
	 * @createtime 2021年3月13日
	 * @updator wanping
	 * @updatetime 2021年3月13日
	 */
	void intentionPublic(Integer id);

	/**
	 * 批量公开确认
	 * @param idlist
	 * @author wanping
	 * @createtime 2021年3月13日
	 * @updator wanping
	 * @updatetime 2021年3月13日
	 */
	void batchPublic(String idlist);

	/**
	 * 根据条件查询采购意向数据
	 * @param searchData
	 * @return
	 * @author wanping
	 * @param ids 
	 * @createtime 2021年3月13日
	 * @updator wanping
	 * @updatetime 2021年3月13日
	 */
	List<PurchaseIntentionBasic> getList(String searchData, String ids);

	/**
	 * 导出Excel
	 * @param list
	 * @param filePath
	 * @return
	 * @author wanping
	 * @createtime 2021年3月13日
	 * @updator wanping
	 * @updatetime 2021年3月13日
	 */
	HSSFWorkbook intentionExport(List<PurchaseIntentionBasic> list, String filePath);

	List<PurchaseIntentionBasic> findByCode(String openObjCode);

}
