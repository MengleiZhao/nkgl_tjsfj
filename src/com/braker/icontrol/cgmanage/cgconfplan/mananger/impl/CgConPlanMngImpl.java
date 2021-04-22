package com.braker.icontrol.cgmanage.cgconfplan.mananger.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.PersonalWork;
import com.braker.core.model.User;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConPlanMng;
import com.braker.icontrol.cgmanage.cgconfplan.mananger.CgConfPlanCheckMng;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlan;
import com.braker.icontrol.cgmanage.cgconfplan.model.ProcurementPlanList;
import com.braker.workflow.entity.TNodeData;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TNodeDataMng;
import com.braker.workflow.manager.TProcessCheckMng;
import com.braker.workflow.manager.TProcessDefinMng;
import com.braker.zzww.comm.manager.AttachmentMng;

/**
 * 采购计划配置的service实现类
 * @author 冉德茂
 * @createtime 2018-10-11
 * @updatetime 2018-10-11
 */
@Service
@Transactional
public class CgConPlanMngImpl extends BaseManagerImpl<ProcurementPlan> implements CgConPlanMng {
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private CgConfPlanCheckMng confplancheckMng;
	@Autowired
	private CgConPlanMng confplanMng;
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private TProcessCheckMng tProcessCheckMng;
	
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	
	@Autowired
	private TNodeDataMng tNodeDataMng;
	
	@Autowired
	private UserMng userMng;
	/*
	 * 分页查询
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-30
	 */
	@Override
	public Pagination pageList(ProcurementPlan bean,User user,  int pageNo, int pageSize) {

		Finder finder =Finder.create("  FROM ProcurementPlan where fstauts <>"+99+" ");
		if(!StringUtil.isEmpty(bean.getFreqDept())){ //按部门名称模糊查询
			finder.append(" AND freqDept LIKE :freqDept");
			finder.setParam("freqDept", "%"+bean.getFreqDept()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFlistNum())){ //按部门名称模糊查询
			finder.append(" AND flistNum LIKE :flistNum");
			finder.setParam("flistNum", "%"+bean.getFlistNum()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFprocurType()) ){//采购类型
			finder.append(" AND fprocurType = :fprocurType");
			finder.setParam("fprocurType", bean.getFprocurType());
		}
		if(!StringUtil.isEmpty(bean.getFcheckStauts()) ){//审批状态
			if("2".equals(bean.getFcheckStauts())){//审核中
				finder.append(" AND fcheckStauts >0 and fcheckStauts <9");
			}else{
				finder.append(" AND fcheckStauts = :fcheckStauts");
				finder.setParam("fcheckStauts", bean.getFcheckStauts());
			}
			
		}
		if(!StringUtil.isEmpty(String.valueOf(bean.getFreqTime()))){
			finder.append(" and datediff(freqTime,'"+bean.getFreqTime()+"')=0 ");//日期模糊查询
		}
		finder.append(" AND fCombineStauts = 0");
		finder.append(" AND freqUserId ='"+user.getId()+"'"); //只能查询当前登录者自己申请的
		finder.append(" order by fplId desc");
		return super.find(finder,pageNo,pageSize);
	}
	/*
	 * 分页数据获取已经审核通过,采购计划没有选择过的的配置计划信息进行合并
	 * @author 冉德茂
	 * @createtime 2018-10-23
	 * @updatetime 2018-10-23
	 */
	@Override
	public Pagination combinepageList(ProcurementPlan bean, int pageNo, int pageSize) {
		//审批通过  没有选择过  未删除的配置计划
		Finder finder =Finder.create("  FROM ProcurementPlan where fstauts <>99 and fcheckStauts =9 and fisChecked =0 ");
		if(!StringUtil.isEmpty(bean.getFreqDept())){ //按部门名称模糊查询
			finder.append(" AND freqDept LIKE :freqDept");
			finder.setParam("freqDept", "%"+bean.getFreqDept()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFlistNum())){ //按部门名称模糊查询
			finder.append(" AND flistNum LIKE :flistNum");
			finder.setParam("flistNum", "%"+bean.getFlistNum()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFprocurType()) ){//采购类型
			finder.append(" AND fprocurType = :fprocurType");
			finder.setParam("fprocurType", bean.getFprocurType());
		}
		finder.append(" order by  fbrandModel desc, fplId desc");
		Pagination p = super.find(finder,pageNo,pageSize);
		List<ProcurementPlan> list = (List<ProcurementPlan>) p.getList();
		//从采购清单里拿出品牌和数量组装到计划里面
		list=setCombineState(list);
		return p;
	}
	
	/*
	 * 循环集合，判断是否可合并
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	public List<ProcurementPlan> setCombineState(List<ProcurementPlan> list ){
		int size=list.size()-1;
		for(int i=0;i<size;i++){
			if(!StringUtil.isEmpty(list.get(i).getFbrandModel()) && !StringUtil.isEmpty(list.get(i+1).getFbrandModel())){
				if(list.get(i).getFbrandModel().equals(list.get(i+1).getFbrandModel())){
					list.get(i).setCombineState("可合并");
					list.get(i+1).setCombineState("可合并");
				}
			}
		}
		return list;
	}
	/*
	 * 页面加载已经审批通过  未选择过的配置计划信息
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@Override
	public Pagination getCheckedConfplan(ProcurementPlan bean, int pageNo, int pageSize) {
		//选择审批通过   未删除  未选择过的数据
		Finder finder = Finder.create(" from ProcurementPlan WHERE fcheckStauts='9' AND fstauts <>"+99+" AND fisChecked ="+0+" ");
		if(!StringUtil.isEmpty(bean.getFreqDept())){ //按部门名称模糊查询
			finder.append(" AND freqDept LIKE :freqDept");
			finder.setParam("freqDept", "%"+bean.getFreqDept()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFprocurType()) ){//采购类型
			finder.append(" AND fprocurType = :fprocurType");
			finder.setParam("fprocurType", bean.getFprocurType());
		}
		finder.append(" order by fplId desc");
		return super.find(finder,pageNo,pageSize);
	}
	
	/*
	 * 查询待审批信息
	 * @author 冉德茂
	 * @createtime 2018-10-17
	 * @updatetime 2018-10-17
	 */
	@Override
	public Pagination checkpageList(ProcurementPlan bean, User user,Integer page,Integer rows) {
		
		Finder finder =Finder.create(" FROM ProcurementPlan WHERE fuserId='"+user.getId()+"'  AND fstauts <>"+99+"   ");
		if(!StringUtil.isEmpty(bean.getFreqDept())){ //按部门名称模糊查询
			finder.append(" AND freqDept LIKE :freqDept");
			finder.setParam("freqDept", "%"+bean.getFreqDept()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFlistNum())){ //按部门名称模糊查询
			finder.append(" AND flistNum LIKE :flistNum");
			finder.setParam("flistNum", "%"+bean.getFlistNum()+"%");
		}
		if(!StringUtil.isEmpty(bean.getFprocurType()) ){//采购类型
			finder.append(" AND fprocurType = :fprocurType");
			finder.setParam("fprocurType", bean.getFprocurType());
		}
		finder.append(" order by fplId desc ");//日期模糊查询
		return super.find(finder,page,rows);
	}
	/*
	 * 新增的保存
	 * @author 冉德茂
	 * @createtime 2018-10-11
	 * @updatetime 2018-10-11
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)//事务处理
	public void save(ProcurementPlan bean,String mingxi, String files, User user)  throws Exception {
		//获取现有的明细
		List mxList = getMingXiJson(mingxi, ProcurementPlanList.class);
		Date d = new Date();
		if (bean.getFplId()==null) {
			//创建人、创建时间、发布时间  
			bean.setFreqDeptId(user.getDpID());
			bean.setFreqDept(user.getDepartName());
			bean.setCreator(user.getName());
			bean.setCreateTime(d);
			bean.setFreqTime(d);;
		} else {
			//修改人、修改时间
			bean.setUpdator(user.getAccountNo());
			bean.setUpdateTime(d);
			bean.setFreqTime(d);;
		}
		bean.setFstauts("0");//数据删除状态
		bean.setFisChecked("0");//设置采购是否已选择
		setBandAndModel(bean,mxList);//把采购明细的品牌和规格写入采购计划里，方便合并的时候排序
		//以下为工作流的节点配置（如果点送审则设置进入工作流1为提交2为审核中）
		if(bean.getFcheckStauts().equals("1") || bean.getFcheckStauts().equals("2")){
			//得到第一个审批节点key
			Integer firstKey=tProcessCheckMng.addProcessCheck(user.getDpID(),bean.getJoinTable(),bean.getBeanCodeField(),bean.getBeanCode(), "CGPLANSQ", user);
			//根据资源名称和当前登陆者所属部门查询对应工作流
			TProcessDefin processDefin=tProcessDefinMng.getByBusiAndDpcode("CGPLANSQ", user.getDpID());
			Integer flowId= processDefin.getFPId();
			TNodeData node=tNodeDataMng.getByFlowIdAndKey(flowId,firstKey);
			User nextUser=userMng.findById(node.getUserId());
			//设置下节点处理人姓名和编号		get(0)的原因是一个角色应该只能审批一级，所以查出来li应该只有一个数据
			bean.setUserName2(nextUser.getName());
			bean.setFuserId(nextUser.getId());
			//设置下节点节点编码
			bean.setnCode(firstKey+"");	
			//把历史审批记录全部设置为1，表示重新审批
			tProcessCheckMng.updateStauts(flowId,bean.getBeanCode());
			//保存基本信息
			
			if(bean.getFplId()==null){
				bean.setFreqUserId(user.getId());
				bean = (ProcurementPlan) super.saveOrUpdate(bean); //新增
			}else{
				super.updateDefault(bean);//修改
			}
			//添加审批人个人首页代办信息
			PersonalWork work = new PersonalWork();
			work.setUserId(nextUser.getId());//任务处理人ID既是下节点处理人ID
			work.setTaskId(bean.getFplId());//申请单ID
			work.setTaskCode(bean.getFlistNum());//为申请单的单号
			work.setTaskName(bean.getFbrandModel()+"配置申请审批");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work.setUrl("/cgconfplancheck/check?id="+bean.getFplId());//为审批页面内容显示的url,需要将数据传入不然无法访问
			work.setUrl1("/cgconfplangl/detail?id="+work.getTaskId());
			work.setTaskStauts("0");//待办
			work.setType("1");//任务类型（1审批）
			work.setBeforeUser(user.getName());//任务提交人姓名
			work.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work.setBeforeTime(d);//任务提交时间
			personalWorkMng.merge(work);
			
			//添加申请人的个人首页已办信息
			PersonalWork work2 = new PersonalWork();
			work2.setUserId(user.getId());//任务处理人ID既是申请人的ID
			work2.setTaskId(bean.getFplId());//申请单ID
			work2.setTaskCode(bean.getFlistNum());//为申请单的单号
			work2.setTaskName("配置申请审批");//任务名称模块（菜单）名称+操作如：合同拟定审批等格式固定
			work2.setUrl("/cgconfplangl/edit?id="+work.getTaskId());//退回修改url
			work2.setUrl1("/cgconfplangl/detail?id="+work.getTaskId());
			work2.setUrl2("/cgconfplangl/delete?id="+work.getTaskId());//删除url
			work2.setTaskStauts("2");//已办
			work2.setType("2");//任务类型（2查看）
			work2.setBeforeUser(user.getName());//任务提交人姓名
			work2.setBeforeDepart(user.getDepartName());//任务提交人所属部门名称
			work2.setBeforeTime(d);//任务提交时间
			work2.setFinishTime(d);
			personalWorkMng.merge(work2);
			
		} else {
			//保存基本信息
			bean.setFreqUserId(user.getId());
			//保存基本信息
			if(bean.getFplId()==null){
				bean = (ProcurementPlan) super.saveOrUpdate(bean); //新增
			}else{
				bean = (ProcurementPlan) super.updateDefault(bean);//修改
			}
		}
		
		//保存附件信息
		attachmentMng.joinEntity(bean,files);
		//获得老的明细
		List<Object> oldmxList = getMingxi("ProcurementPlanList", "fplId", bean.getFplId());
		/*deleteByCgId();
		for(QingDan qingdan:List){
			qingdan.setCgId(cgid);
			super.add(qingdan);
		}*/
		//比较新老明细的不同
		for (int i = oldmxList.size()-1; i >= 0; i--) {
			ProcurementPlanList oldgad = (ProcurementPlanList) oldmxList.get(i);
			for (int j = 0; j < mxList.size(); j++) {
				ProcurementPlanList gad = (ProcurementPlanList) mxList.get(j);
				if(gad.getFpId()!=null){
					if(gad.getFpId()==oldgad.getFpId()){
						oldmxList.remove(i);
					}
				}
			}
		}
		//删除在新明细中没有的老明细
		if(oldmxList.size()>0){
			for (int i = 0; i < oldmxList.size(); i++) {
				ProcurementPlanList oldgad = (ProcurementPlanList) oldmxList.get(i);
				super.delete(oldgad);
			}
		}
		
		//保存新的明细
		for (int j = 0; j < mxList.size(); j++) {
			ProcurementPlanList gad = (ProcurementPlanList) mxList.get(j);
			gad.setFpId(bean.getFplId());
			/*gad.setCreator(user.getAccountNo());
			gad.setCreateTime(d);*/
			super.merge(gad);
		}
			
	}
	/*
	 * 把采购明细的品牌和规格写入采购计划里，方便合并的时候排序
	 * @author 安达
	 * @createtime 2018-10-25
	 * @updatetime 2018-10-25
	 */
	public ProcurementPlan setBandAndModel(ProcurementPlan plan,List mxList){
		String fbrandModel="";
		String fbrandAndNum="";
		if(mxList.size()>0){
			for(int j=0;j<mxList.size();j++){//
				ProcurementPlanList mingxi=(ProcurementPlanList)mxList.get(j);
				if("".equals(fbrandModel)){
					fbrandModel=mingxi.getFpurBrand()+"("+mingxi.getFspecifModel()+")"+mingxi.getFunitPrice();
				}else{
					fbrandModel=fbrandModel+"|"+mingxi.getFpurBrand()+"("+mingxi.getFspecifModel()+")"+mingxi.getFunitPrice();
				}
				if("".equals(fbrandAndNum)){
					fbrandAndNum=mingxi.getFpurBrand()+"("+mingxi.getFnum()+")";
				}else{
					fbrandAndNum=fbrandAndNum+"|"+mingxi.getFpurBrand()+"("+mingxi.getFnum()+")";
				}
			}
		}
		plan.setFbrandAndNum(fbrandAndNum);
		plan.setFbrandModel(fbrandModel);
		return plan;
	}
	/*
	 * 根据ID删除
	 * @author 冉德茂
	 * @createtime 2018-10-16
	 * @updatetime 2018-10-16
	 */
	@Override
	public void delete(Integer id) {
		ProcurementPlan bean = super.findById(id);
		bean.setFstauts("99");
		super.saveOrUpdate(bean);
	}
	/*
	 * 采购计划品目明细查询
	 * @author 冉德茂
	 * @createtime 2018-10-13
	 * @updatetime 2018-10-13
	 */
	public List<Object> getMingxi(String tableName ,String idName ,Integer pid) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "="+pid);
		return super.find(finder);
	}
	
	public List<Object> getMingxi(String tableName ,String idName ,String code) {
		Finder finder = Finder.create(" FROM " + tableName + " WHERE " + idName + "='"+code+"'");
		return super.find(finder);
	}
	/*
	 * 获取明细的Json对象
	 * @author 冉德茂
	 * @createtime 2018-10-13
	 * @updatetime 2018-10-13
	 */
	public List getMingXiJson(String mingxi, Class tableClass) {
		//获取明细的Json对象
		List mx = null;
		JSONArray array =JSONArray.fromObject("["+mingxi.toString()+"]");
		mx = (List)JSONArray.toList(array, tableClass);
		return mx;
	}
	
	/*
	 * 根据页面选择的计划id数组，获得所有计划下的每个清单map
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	public Map<Integer,List<Object>> getQdMapByPlids(String ids){
		String[] plids = ids.split(",");
		Map<Integer,List<Object>> qdMap = new HashMap<Integer,List<Object>>();//清单Map
		for (int i = 0; i < plids.length; i++) {//获取所有的采购清单
			Integer plid=Integer.valueOf(plids[i]);
			List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fplId",plid);
			qdMap.put(plid, mingxiList);
		}
		return qdMap;
	}
	
	/*
	 * 根据页面选择的计划id数组，获得所有计划下的采购清单集合
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	@Override
	public List<List<Object>> getQdListByPlids(String ids){
		String[] plids = ids.split(",");
		List<List<Object>> list=new ArrayList<List<Object>>();
		Map<Integer,List<Object>> qdMap = new HashMap<Integer,List<Object>>();//清单Map
		for (int i = 0; i < plids.length; i++) {//获取所有的采购清单
			Integer plid=Integer.valueOf(plids[i]);
			List<Object> mingxiList = confplanMng.getMingxi("ProcurementPlanList", "fplId",plid);
			list.add(mingxiList);
		}
		return list;
	}
	/*
	 * 获得采购计划集合
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	@Override
	public List<ProcurementPlan> getPlanListByPlids(String ids) {
		String[] plids = ids.split(",");
		Finder finder = Finder.create(" FROM ProcurementPlan WHERE fplId in ("+ids+") ");
		return super.find(finder);
	}
	/*
	 * 检查目录、品牌、规格 是否符合合并要求
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	@Override
	public String checkCombineFlag(List<List<Object>> list){
		int count=list.size()-1;
		for(int i=0;i<count;i++){//清单数量
			if(list.get(i).size() != list.get(i+1).size()){
				return "物品清单数量不一致";
			}
		}
		for(int i=0;i<count;i++){
			List<Object> list1=list.get(i);
			List<Object> list2=list.get(i+1);
			StringBuilder fpurCode1=new StringBuilder(); //目录
			StringBuilder fpurBrand1=new StringBuilder();//品牌
			StringBuilder fspecifModel=new StringBuilder();//规格
			double[] price1=new double[list1.size()]; //价格
			int k=0;
			for(Object o:list1){
				ProcurementPlanList planl=(ProcurementPlanList) o;
				fpurCode1.append(planl.getFpurCode());
				fpurBrand1.append(planl.getFpurBrand());
				fspecifModel.append(planl.getFspecifModel());
				price1[k]=planl.getFunitPrice();
				k++;
			}
			StringBuilder fpurCode2=new StringBuilder();
			StringBuilder fpurBrand2=new StringBuilder();
			StringBuilder fspecifMode2=new StringBuilder();
			double[] price2=new double[list2.size()]; //价格
			int j=0;
			for(Object o:list2){
				ProcurementPlanList planl=(ProcurementPlanList) o;
				fpurCode2.append(planl.getFpurCode());
				fpurBrand2.append(planl.getFpurBrand());
				fspecifMode2.append(planl.getFspecifModel());
				price2[j]=planl.getFunitPrice();
				j++;
			}
			if(!fpurCode1.toString().equals(fpurCode2.toString())){
				return "目录不一致";
			} else if(!fpurBrand1.toString().equals(fpurBrand2.toString())){
				return "品牌不一致";
			} else if(!fspecifModel.toString().equals(fspecifMode2.toString())){
				return "规格不一致";
			}
			for(int m=0;m<price1.length;m++){
				if(price1[m]!=price2[m]){
					return "价格不一致";
				}
			}
		}
		return "1";
	}
	/*
	 * 合并计划采购单生成新的采购单对象
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	@Override
	public ProcurementPlan getCombinePlan(List<ProcurementPlan> list){
		//合并计划单   部门以|分割，其它字段取第一条
		StringBuffer listcode = new StringBuffer();
		for(int i=0;i<list.size();i++){
			if(i==0){
				listcode.append(list.get(i).getFreqDept());
			}else{
				listcode.append("|"+list.get(i).getFreqDept());
			}
			
		}
		ProcurementPlan newbean=new ProcurementPlan();
		String str="HB";
		newbean.setFlistNum(StringUtil.Random(str));//合并单号
		newbean.setFreqDept(listcode.toString());//申请部门
		newbean.setFreqDeptId(list.get(0).getFreqDeptId());
		newbean.setFreqTime(list.get(0).getFreqTime());//申请日期
		newbean.setFprocurType(list.get(0).getFprocurType());//采购类型
		newbean.setFreqLinkMen(list.get(0).getFreqLinkMen());//申请人
		newbean.setFlinkTel(list.get(0).getFlinkTel());//联系电话
		newbean.setFreqContent(list.get(0).getFreqContent());//内容
		newbean.setFremark(list.get(0).getFremark());//备注
		newbean.setFcheckStauts("9");//设置审批状态为已审批	
		newbean.setfCombineStauts(1);//设置合并状态为1 被合并过
		return newbean;
	}
	
	/*
	 * 获得合并后的采购清单
	 * @author 安达
	 * @createtime 2018-10-29
	 * @updatetime 2018-10-29
	 */
	@Override
	public List<ProcurementPlanList> getCombineQdList(List<List<Object>> list){
		List<ProcurementPlanList> newList=new ArrayList<ProcurementPlanList>();
		//把采购清单的所有集合全部合并成一个集合
		List<Object> totalList=new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			List<Object> qdlist=list.get(i);
			totalList.addAll(qdlist);
		}
		Map<String,Integer> numMap=new HashMap<String,Integer>();
		Map<String,Double> moneyMap=new HashMap<String,Double>();
		//循环集合，把品牌规格一样的数量和总价钱累加起来
		for(Object o:totalList){
			ProcurementPlanList planlistBean=(ProcurementPlanList)o;
			String key=planlistBean.getFpurCode()+planlistBean.getFcommProp()+planlistBean.getFspecifModel();
			if(numMap.get(key)==null){
				numMap.put(key, planlistBean.getFnum());
			}else{
				numMap.put(key, numMap.get(key)+planlistBean.getFnum());
			}
			if(moneyMap.get(key)==null){
				moneyMap.put(key, planlistBean.getFsumMoney());
			}else{
				moneyMap.put(key, moneyMap.get(key)+planlistBean.getFsumMoney());
			}
		}
		List<Object> list0=list.get(0);
		for(Object o:list0){
			ProcurementPlanList planlistBean=(ProcurementPlanList)o;
			planlistBean.setFnum(numMap.get(planlistBean.getFpurCode()+planlistBean.getFcommProp()+planlistBean.getFspecifModel()));
			planlistBean.setFsumMoney(moneyMap.get(planlistBean.getFpurCode()+planlistBean.getFcommProp()+planlistBean.getFspecifModel()));
			newList.add(planlistBean);
		}
		return newList;
	}
	
	/*
	 * 合并计划单的保存
	 * @author 冉德茂
	 * @createtime 2018-10-24
	 * @updatetime 2018-10-24
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)//事务处理
	public void saveCombine(ProcurementPlan bean,List<ProcurementPlanList> mxList, String[] plids, User user) {		
		//bean是合并之后的计划单 没有主键id   listbean是合并之后的采购清单 没有主键id和外键id（bean的id） plids是合并前的采购单的主键 id  合并之后删除 （包括配置单  审批记录  采购清单）
		//1.保存合并计划单 
		Date d = new Date();
		bean.setCreator(user.getName());
		bean.setCreateTime(d);
		bean.setFstauts("0");//数据的删除状态
		bean.setFisChecked("0");//采购选择状态
		setBandAndModel(bean,mxList);
		bean = (ProcurementPlan) super.saveOrUpdate(bean);
		
		//2.获取刚保存的合并计划的主键id  保存清单信息
		Integer fplId = bean.getFplId();
		for(ProcurementPlanList planlist:mxList){
			planlist.setFpId(fplId);
			super.merge(planlist);
		}
		
		
		//3.删除原有采购清单   
		for (int i = 0; i < plids.length; i++) {
			
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     Integer plid=Integer.valueOf(plids[i]);
			List<Object> oldmx = getMingxi("ProcurementPlanList", "fplId", plid);//采购清单
			if(oldmx.size()!=0){
				for(int j=0;j<oldmx.size();j++){//删除采购清单
					ProcurementPlanList obean=(ProcurementPlanList)oldmx.get(j);
					super.delete(obean);
				}
			}
		}
		
		//4.删除配置计划
		for (int i = 0; i < plids.length; i++) {
			Integer plid=Integer.valueOf(plids[i]);
			ProcurementPlan planbean = super.findById(plid);//配置计划单
			super.delete(planbean);//删除配置计划单	
		}
		
		//6.删除附件信息？？				
		
	}



}

	
	
	


