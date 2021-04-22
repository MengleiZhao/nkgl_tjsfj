package com.braker.icontrol.purchase.apply.manager.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.CheckEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.purchase.apply.manager.PurchaseIntentionMng;
import com.braker.icontrol.purchase.apply.model.PurchaseIntentionBasic;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessCheck;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 采购意向ServiceImpl
 * 
 * @author wanping
 *
 */
@Service
@Transactional
public class PurchaseIntentionMngImpl extends BaseManagerImpl<PurchaseIntentionBasic> implements PurchaseIntentionMng {
	
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TNodeDataMng tNodeDataMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private PersonalWorkMng personalWorkMng;
	
	@Autowired
	private AttachmentMng attachmentMng;
	
	@Autowired
	private PrivateInforMng privateInforMng;
	
	@Autowired
	private ShenTongMng shenTongMng;

	@Override
	public Pagination pageList(PurchaseIntentionBasic bean, Integer page, Integer rows, User user, String searchData) {
		Finder finder = Finder.create(" FROM PurchaseIntentionBasic WHERE stauts != '99'");
		if (user != null) { //用户
			finder.append(" AND fUser = :fUser");
			finder.setParam("fUser", user.getId());
			finder.append(" AND fDeptId = :fDeptId");
			finder.setParam("fDeptId", user.getDpID());
		}
		
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fIntentionCode like '%"+searchData+"%' or fPurchaseName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		
		finder.append(" order by fReqTime desc");
		return super.find(finder, page, rows);
	}

	@Override
	public List<PurchaseIntentionBasic> findByCondition(String currentDate) {
		Finder finder = Finder.create(" FROM PurchaseIntentionBasic WHERE fIntentionCode like 'GK" + currentDate + "%'");
		return super.find(finder);
	}

	@Override
	public void save(PurchaseIntentionBasic bean, String files, User user) throws Exception {
		Date date = new Date();
		if (bean.getfId() == null) {
			//创建人、创建时间、发布时间 
			bean.setCreator(user.getName());
			bean.setCreateTime(date);
			bean.setfDeptId(user.getDpID());
			bean.setfDeptName(user.getDepartName());
			bean.setfUser(user.getId());
			bean.setfReqTime(date);
			bean.setPublicStatus("0");
			Integer seq = shenTongMng.getSeq("purchase_intention_basic_seq");
			bean.setfId(seq);
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getName());
			bean.setUpdateTime(date);
			bean.setfReqTime(date);
		}
		
		//以下为工作流的节点配置（如果点送审则设置进入工作流1审核中）
		if ("1".equals(bean.getFlowStauts())) {
			//得到第一个审批节点key
			Integer firstKey = tProcessCheckMng.addProcessCheck(user.getDpID(), bean.getJoinTable(), bean.getBeanCodeField(), bean.getBeanCode(), "CGYX", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin = tProcessDefinMng.getByBusiAndDpcode("CGYX", user.getDpID());
			Integer flowId = processDefin.getFPId();
			TNodeData node = tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser = userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号 get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			super.saveOrUpdate(bean);//修改
			
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			Integer seq1 = shenTongMng.getSeq("personal_worktable_seq");
			work.setfId(seq1);
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getfId());//意向公开申请ID
			work.setTaskCode(bean.getfIntentionCode());//为申请单的单号
			work.setTaskName("[意向公开申请]"+bean.getfPurchaseName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/purchaseIntentionCheck/check?id="+bean.getfId());//审批url
			work.setUrl1("/purchaseIntention/edit?id="+ bean.getfId()+"&editType=0");//查看url
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setTaskType("1");//任务归属（1审批人）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(date);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			Integer seq2 = shenTongMng.getSeq("personal_worktable_seq");
			work2.setfId(seq2);
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getfId());//申请单ID
			work2.setTaskCode(bean.getfIntentionCode());//为申请单的单号
			work2.setTaskName("[意向公开申请]"+bean.getfPurchaseName());//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/purchaseIntention/edit?id="+ bean.getfId()+"&editType=1");//退回修改url
			work2.setUrl1("/purchaseIntention/edit?id="+ bean.getfId()+"&editType=0");//查看url
			work2.setUrl2("/purchaseIntention/delete?id="+ bean.getfId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setTaskType("0");//任务归属（0发起人）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(date);//任务提交时间
			work2.setFinishTime(date);
			personalWorkMng.merge(work2);
		}else {
			//保存基本信息
			super.merge(bean);//修改
		}
		
		//保存附件信息
		attachmentMng.joinEntity(bean, files);
	}

	@Override
	public void delete(Integer id, String fId, User user) {
		if (fId != null) {
			personalWorkMng.deleteById(Integer.valueOf(fId));
		}
		PurchaseIntentionBasic bean = super.findById(id);
		bean.setStauts("99");
		super.saveOrUpdate(bean);
		personalWorkMng.sendMessageToUser(user.getId(), 0);
	}

	@Override
	public void reCall(Integer id) {
		//根据id查询对象
		PurchaseIntentionBasic bean = super.findById(id);
		
		//删除待办
		personalWorkMng.deleteDb(bean.getNextCheckUserId(), bean.getBeanCode(), "0");
		
		//给待审批人推送消息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "意向公开申请被撤回消息";
		String msg = "您待审批的  "+bean.getfPurchaseName()+",任务编号：("+bean.getBeanCode()+")于"+sdf.format(new Date())+"被申请人撤回,请及时关注。";
		privateInforMng.setMsg(title, msg, bean.getNextCheckUserId(), "3");
		//撤回
		bean = (PurchaseIntentionBasic) reCall((CheckEntity) bean);
		this.updateDefault(bean);
	}

	@Override
	public void check(TProcessCheck checkBean, PurchaseIntentionBasic bean, User user, String spjlFile) throws Exception {
		bean = this.findById(bean.getfId());
		CheckEntity entity = (CheckEntity)bean;
		String url = "/purchaseIntentionCheck/check?id=";
		String url1 = "/purchaseIntention/edit?editType=0&id=";
		bean = (PurchaseIntentionBasic)tProcessCheckMng.checkProcess(checkBean, entity, user, "CGYX", url, url1, spjlFile);
		super.saveOrUpdate(bean);
	}

	@Override
	public Pagination checkPageList(PurchaseIntentionBasic bean, Integer page, Integer rows, User user,
			String searchData) {
		Finder finder = Finder.create(" FROM PurchaseIntentionBasic WHERE fuserId='"+user.getId()+"' AND stauts in('1','0')");	
		if (!StringUtil.isEmpty(searchData)) {
			finder.append(" AND (fIntentionCode like '%"+searchData+"%' or fPurchaseName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		finder.append(" order by fReqTime desc");
		return super.find(finder, page, rows);
	}

	@Override
	public Pagination pubPageList(PurchaseIntentionBasic bean, Integer page, Integer rows, User user,
			String searchData) {
		Finder finder = Finder.create(" FROM PurchaseIntentionBasic WHERE flowStauts = '9' AND amount >= 500000");
		
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fIntentionCode like '%"+searchData+"%' or fPurchaseName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		
		finder.append(" order by publicStatus,fReqTime desc");
		return super.find(finder, page, rows);
	}

	@Override
	public void intentionPublic(Integer id) {
		PurchaseIntentionBasic bean = super.findById(id);
		//设为已公开
		bean.setPublicStatus("1");
		bean.setIsUsed("0");
		bean.setPublicTime(new Date());
		super.saveOrUpdate(bean);
	}

	@Override
	public void batchPublic(String idlist) {
		String[] idStr = idlist.split(",");
		for (int i = 0; i < idStr.length; i++) {
			PurchaseIntentionBasic bean = super.findById(Integer.valueOf(idStr[i]));
			//设为已公开
			bean.setPublicStatus("1");
			bean.setIsUsed("0");
			bean.setPublicTime(new Date());
			super.saveOrUpdate(bean);
		}
	}

	@Override
	public List<PurchaseIntentionBasic> getList(String searchData, String ids) {
		Finder finder = Finder.create(" FROM PurchaseIntentionBasic WHERE flowStauts = '9' AND amount >= 500000");
		
		if(!StringUtil.isEmpty(searchData)){
			finder.append(" AND (fIntentionCode like '%"+searchData+"%' or fPurchaseName like '%"+searchData+"%' or fDeptName like '%"+searchData+"%' or amount like '%"+searchData+"%' or TO_DATE(fReqTime,'yyyy-mm-dd') like '%"+searchData+"%') ");
		}
		//获取导出选中的数据
		if (!StringUtil.isEmpty(ids)) {
			finder.append(" AND fId in(" + ids + ")");
		}
		
		finder.append(" order by fReqTime desc");
		return super.find(finder);
	}

	@Override
	public HSSFWorkbook intentionExport(List<PurchaseIntentionBasic> list, String filePath) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		FileInputStream fis =null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet sheet0 = wb.getSheetAt(0);
			sheet0.getRow(1).createCell(1).setCellValue(df.format(new Date()));//设置导出时间
			
			HSSFRow row = sheet0.getRow(3);//格式行
			
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					HSSFRow hssfRow = sheet0.createRow(3+i);
					if(row != null) {
						hssfRow.setHeight(row.getHeight());
					}
					
					//序号
					hssfRow.createCell(0).setCellValue(i + 1);
					//意向公开编号
					hssfRow.createCell(1).setCellValue(list.get(i).getfIntentionCode());
					//采购项目名称
					hssfRow.createCell(2).setCellValue(list.get(i).getfPurchaseName());
					//采购需求概况
					hssfRow.createCell(3).setCellValue(list.get(i).getfPurchaseDemand());
					//预算金额
					hssfRow.createCell(4).setCellValue(list.get(i).getAmount()!=null?list.get(i).getAmount():0.0);
					//预计采购时间
					hssfRow.createCell(5).setCellValue(df1.format(list.get(i).getfPurchaseTime()));
					//是否专门面向中小企业
					String isForSmes = "";
					if (list.get(i).getIsForSmes() == 0) {
						isForSmes = "是";
					}
					if (list.get(i).getIsForSmes() == 1) {
						isForSmes = "否";
					}
					hssfRow.createCell(6).setCellValue(isForSmes);
					//申请部门
					hssfRow.createCell(7).setCellValue(list.get(i).getfDeptName());
					//申请人
					hssfRow.createCell(8).setCellValue(list.get(i).getfUserName());
					//申请时间
					hssfRow.createCell(9).setCellValue(df.format(list.get(i).getfReqTime()));
					//公开状态
					String publicStatus = "";
					if ("0".equals(list.get(i).getPublicStatus())) {
						publicStatus = "未公开";
					}
					if ("1".equals(list.get(i).getPublicStatus())) {
						publicStatus = "已公开";
					}
					hssfRow.createCell(10).setCellValue(publicStatus);
					//公开确认时间
					if (list.get(i).getPublicTime() != null) {
						hssfRow.createCell(11).setCellValue(df.format(list.get(i).getPublicTime()));
					}
					//备注
					hssfRow.createCell(12).setCellValue(list.get(i).getRemark());
				}
				return wb;
			} else {
				return null;
			}
			
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

	@Override
	public List<PurchaseIntentionBasic> findByCode(String openObjCode) {
		Finder finder = Finder.create(" FROM PurchaseIntentionBasic WHERE flowStauts = '9' AND fIntentionCode = '"+openObjCode+"'");
		return super.find(finder);
	}

}
