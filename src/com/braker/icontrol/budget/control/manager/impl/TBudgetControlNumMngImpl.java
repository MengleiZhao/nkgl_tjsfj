package com.braker.icontrol.budget.control.manager.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braker.common.entity.DataEntity;
import com.braker.common.entity.TreeEntity;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Finder;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.YearsBasicMng;
import com.braker.core.model.User;
import com.braker.core.model.YearsBasic;
import com.braker.icontrol.budget.control.entity.TBasicExpend;
import com.braker.icontrol.budget.control.entity.TBudgetControlNum;
import com.braker.icontrol.budget.control.entity.TProExpend;
import com.braker.icontrol.budget.control.manager.TBasicExpendMng;
import com.braker.icontrol.budget.control.manager.TBudgetControlNumMng;
import com.braker.icontrol.budget.control.manager.TProExpendMng;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.braker.icontrol.budget.project.manager.TProBasicInfoMng;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class TBudgetControlNumMngImpl extends BaseManagerImpl<TBudgetControlNum> implements TBudgetControlNumMng {
	
	@Autowired
	private YearsBasicMng yearsBasicMng;
	@Autowired
	private TBasicExpendMng tBasicExpendMng;
	@Autowired
	private TProExpendMng tProExpendMng;
	@Autowired
	private TProBasicInfoMng tProBasicInfoMng;

	@Override
	public Pagination pageList(TBudgetControlNum bean, User user,Integer pageNo, Integer pageSize) {
		
		Finder finder = Finder.create(" FROM TBudgetControlNum WHERE 1=1 ");
		if (bean != null) {
			if (!StringUtil.isEmpty(bean.getfBudgetControlBum())) {//??????
				finder.append(" and fBudgetControlBum =:fBudgetControlBum ")
					.setParam("fBudgetControlBum", bean.getfBudgetControlBum());
			}
			if (bean.getfBudgetYear() != null) {//??????
				finder.append(" and YEAR(fBudgetYear)=:fBudgetYear ")
					.setParam("fBudgetYear", Integer.valueOf(new SimpleDateFormat("yyyy").format(bean.getfBudgetYear())));
			}
			/*if (!StringUtil.isEmpty(bean.getNumMin())) {//???????????????
				finder.append(" and FBudgetControlSum >=:min ")
					.setParam("min", Double.valueOf(bean.getNumMin()));
			}
			if (!StringUtil.isEmpty(bean.getNumMax())) {
				finder.append(" and FBudgetControlSum <=:max ")
				.setParam("max", Double.valueOf(bean.getNumMax()));
			}*/
		}
		finder.append(" order by  fTime desc");
		return super.find(finder, pageNo, pageSize);
	}

	@Override
	public void save(User user, TBudgetControlNum bean,
			String personOutJson, String commOutJson, String yearOutJson,
			String leastOutJson) throws JsonParseException, JsonMappingException, IOException {
		
		//?????????
		/*List<TBasicExpend> personOutList = new ArrayList<>();
		List<TBasicExpend> commOutList = new ArrayList<>();*/
		List<TProExpend> yearOutList = new ArrayList<>();
		//List<TProExpend> leastOutList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		//?????????????????????
		TBudgetControlNum controlNum = saveControlNum(bean, user);
		/*//??????????????????
		List<TreeEntity> personTreeList = mapper.readValue(personOutJson, new TypeReference<List<TreeEntity>>(){});//json??????
		analysisAndAdd(personOutList,personTreeList,null,controlNum);//?????????????????????????????????
		tBasicExpendMng.saveBasicOuts(user,personOutList);//??????
		//??????????????????
		List<TreeEntity> commTreeList = mapper.readValue(commOutJson, new TypeReference<List<TreeEntity>>(){});//json??????
		analysisAndAdd(commOutList,commTreeList,null,controlNum);//?????????????????????????????????
		tBasicExpendMng.saveBasicOuts(user,commOutList);//??????*/
		//????????????????????????
		List<TProBasicInfo> yearProList = mapper.readValue(yearOutJson, new TypeReference<List<TProBasicInfo>>(){});
		yearOutList = changeProListToExpendList(yearProList,controlNum);
		//??????????????????????????????????????????????????????
		for (int i = yearOutList.size()-1; i >= 0; i--) {
			if(yearOutList.get(i).getfProBudgetAmount()!=yearOutList.get(i).getfControlAmount()){
				TProBasicInfo tpi = tProBasicInfoMng.findbyCode(yearOutList.get(i).getfProCode());
				//TODO ????????????????????????,?????????????????????
				tpi.setFFlowStauts("-1");
				tpi.setFProLibType("1");
				tpi.setProvideAmount1(yearOutList.get(i).getfControlAmount());
				super.merge(tpi);
			}
		}
		tProExpendMng.saveProOuts(user,yearOutList);
		/*//????????????????????????
		List<TProBasicInfo> leaveProList = mapper.readValue(leastOutJson, new TypeReference<List<TProBasicInfo>>(){});
		leastOutList = changeProListToExpendList(leaveProList,controlNum);
		tProExpendMng.saveProOuts(user,leastOutList);*/
	}

	/**
	 * ?????????????????? ???????????????????????? ????????????
	 * @param container ??????
	 * @param treeList ????????????
	 * @param parent ????????????????????????
	 * @param controlNum ???????????????
	 */
	private void analysisAndAdd(List<TBasicExpend> container, List<TreeEntity> treeList,
			TBasicExpend parent, TBudgetControlNum controlNum) {
		
		if (treeList != null && treeList.size() > 0) {
			for (TreeEntity tree: treeList) {
				//?????????
				TBasicExpend bean = new TBasicExpend();
				//?????????????????????
				bean.setTBudgetControlNum(controlNum);
				//??????????????????
				bean.setFDepartment(tree.getCol1());
				//?????????
				bean.setFControl(!StringUtil.isEmpty(tree.getCol2())?Double.valueOf(tree.getCol2()):null);
				//??????????????????
				bean.setFType(tree.getCol3());
				//??????
				bean.setName(tree.getCol4());
				//??????
				bean.setCode(tree.getCol5());
				//??????
				bean.setFExt1(tree.getCol6());
				//????????????
				bean.setParentNode(parent);
				//????????????
				container.add(bean);
				//??????????????????????????????????????????
				List<TreeEntity> children = tree.getChildren();
				if (children != null && children.size() > 0) {
					analysisAndAdd(container,children,bean,controlNum);
				}
			}
		}
	}

	/**
	 * ???????????????????????????????????????
	 * @param yearProList ????????????
	 * @param controlNum ?????????????????????
	 * @return
	 */
	/**
	 * @param yearProList
	 * @param controlNum
	 * @return
	 */
	private List<TProExpend> changeProListToExpendList(
			List<TProBasicInfo> yearProList, TBudgetControlNum controlNum) {
		
		List<TProExpend> list = new ArrayList<TProExpend>();
		if (yearProList != null && yearProList.size() > 0) {
			for (TProBasicInfo pro: yearProList) {
				TProExpend bean = new TProExpend();
				bean.setfCId(controlNum);
				bean.setfProName(pro.getFProName());
				bean.setfProCode(pro.getFProCode());
				bean.setfLevName(pro.getFirstLevelName());
				bean.setfProType(pro.getFProType().getCode());
				bean.setfFunctionClass(pro.getFunctionType());
				bean.setfProAppliDepart(pro.getFProAppliDepart());
				bean.setfProHead(pro.getFProHead());
				bean.setfProAppTime(pro.getFProAppliTime());
				bean.setfProBudgetAmount(pro.getFProBudgetAmount());
				bean.setfControlAmount(pro.getControlNum());
				list.add(bean);
			}
		}
		return list;
	}

	/**
	 * ???????????????????????????????????????
	 * @param commQuotaList ??????????????????
	 * @param controlNum ?????????????????????
	 * @return
	 */
	private List<TBasicExpend> changeQuotaListToExpendList(
			List<TreeEntity> treeList,TBasicExpend parent,TBudgetControlNum controlNum) {
		
		List<TBasicExpend> list = new ArrayList<TBasicExpend>();
		if (treeList != null && treeList.size() > 0) {
			for (TreeEntity tree: treeList) {
				TBasicExpend bean = new TBasicExpend();
				//?????????????????????
				bean.setTBudgetControlNum(controlNum);
				//????????????
				bean.setFDepartment(tree.getCol1());
				//?????????
				bean.setFControl(!StringUtil.isEmpty(tree.getCol2())?Double.valueOf(tree.getCol2()):null);
				//??????????????????
				bean.setFType(tree.getCol3());
				//??????
				bean.setName(tree.getCol4());
				//??????
				bean.setCode(tree.getCol5());
				//????????????
				bean.setParentNode(parent);
				//??????????????????????????????????????????
				List<TreeEntity> children = tree.getChildren();
				if (children != null && children.size() > 0) {
					changeQuotaListToExpendList(children,null,controlNum);
				}
			}
		}
		return list;
	}

	/**
	 * ?????????????????????
	 * @param bean ???????????????
	 * @param user ?????????
	 */
	private TBudgetControlNum saveControlNum(TBudgetControlNum bean, User user) {

		Date date = new Date();
		
		if (bean != null) {
			if (bean.getfCId() == null) {
				bean.setCreateTime(date);
				bean.setCreator(user.getAccountNo());
				bean.setfUser(user.getAccountNo());
				bean.setfTime(date);
			} else {
				bean.setUpdateTime(date);
				bean.setUpdator(user.getAccountNo());
				bean.setfUser(user.getAccountNo());
				bean.setfTime(date);
			}
			//?????? ??????  ??????????????????
			bean.setfBudgetControlBum("BUD" + new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		}
		return (TBudgetControlNum)saveOrUpdate(bean);
	}

	@Override
	public void logicDeleteById(User user, String conId) {

		/*TBudgetControlNum bean = findById(conId);
		bean.setFDel(0);
		bean.setFUpdateTime(new Date());
		bean.setFUpdateUser(user.getAccountNo());*/
	}

	@Override
	public List<TreeEntity> getPersonOutListAdd( User user, String parentId, String numId) {
		List<TreeEntity> list = new ArrayList<>();
		list = getPersonOutListAdd(parentId,user);
		return list;
	}


	private List<TreeEntity> getPersonOutListAdd(String id, User user) {
		List<TreeEntity> treeList = new ArrayList<>();
		List<Object[]> objList = null;
		//????????????
		if (id == null) {
			//?????????????????????????????????
//			objList = projectMng.getOutComeSubject( null, "0", new SimpleDateFormat("yyyy").format(new Date()));
			objList = tProBasicInfoMng.getOutComeSubject( "personOut", "0", new SimpleDateFormat("yyyy").format(new Date()),null);
		} else {
			//????????????????????????????????????
			objList = tProBasicInfoMng.getOutComeSubject( "personOut", id, new SimpleDateFormat("yyyy").format(new Date()),null);
		}
		//??????tree???
		if (objList != null && objList.size() > 0) {
			for (Object[] array: objList) {
				String name = (String) array[0];
				String code = (String) array[1];
				String key = String.valueOf(array[2]) ;
				String pCode = String.valueOf(array[3]);
				String level = String.valueOf(array[4]);
				String deaprtName = String.valueOf(array[5]);
				TreeEntity node = new TreeEntity();
				node.setText(name);//??????
				node.setCode(code);//????????????
				node.setId(key);//??????id
				node.setParentCode(pCode);//???????????????
				//??????????????????TBudgetControlNumMngImpl.analysisAndAdd
				if(!StringUtil.isEmpty(deaprtName)){
					node.setCol1(deaprtName);
				}else{
					node.setCol1(user.getDepartName());
				}
				node.setCol2("");
				node.setCol3("1");
				node.setCol4(name);
				node.setCol5(code);
				node.setCol6(new SimpleDateFormat("yyyy").format(new Date()));
				if(null != tProBasicInfoMng.getOutComeSubject( "personOut", code, new SimpleDateFormat("yyyy").format(new Date()),null) && 
						tProBasicInfoMng.getOutComeSubject( "personOut", code, new SimpleDateFormat("yyyy").format(new Date()),null).size() > 0){
					node.setState("closed");
				}else{
					node.setLeaf(true);
				}
				treeList.add(node);
			}
		}
		return treeList;
	}

	@Override
	public List<TreeEntity> getCommOutList(String operationType, User user,
			String id) {
		
		List<TreeEntity> list = new ArrayList<>();
		if (!StringUtil.isEmpty(operationType)) {
			if ("add".equals(operationType)) {
				//????????????????????????????????????????????????treegrid
				list = getCommOutListAdd(id,user);
			} else if ("edit".equals(operationType)) {
				//???????????????????????????????????????????????????treegrid
				list = getCommOutListEdit(id,user);
			}
		}
		return list;
	}

	private List<TreeEntity> getCommOutListEdit(String id, User user) {
		
		return null;
	}

	private List<TreeEntity> getCommOutListAdd(String id, User user) {
		
		List<TreeEntity> treeList = new ArrayList<>();
		List<Object[]> objList = null;
		//????????????
		String departId = user.getDepart() != null? user.getDepart().getId():null;
		if (id == null) {
			//?????????????????????????????????
			objList = tProBasicInfoMng.getOutComeSubject( "commOut", "0", new SimpleDateFormat("yyyy").format(new Date()),null);
		} else {
			//????????????????????????????????????
			objList = tProBasicInfoMng.getOutComeSubject( "commOut", id, new SimpleDateFormat("yyyy").format(new Date()),null);
		}
		//??????tree???
		if (objList != null && objList.size() > 0) {
			for (Object[] array: objList) {
				String name = (String) array[0];
				String code = (String) array[1];
				String key = String.valueOf(array[2]) ;
				String pCode = String.valueOf(array[3]);
				String level = String.valueOf(array[4]);
				String deaprtName = String.valueOf(array[5]);
				TreeEntity node = new TreeEntity();
				node.setText(name);//??????
				node.setCode(code);//????????????
				node.setId(key);//??????id
				node.setParentCode(pCode);//???????????????
				//??????????????????TBudgetControlNumMngImpl.analysisAndAdd
				if(!StringUtil.isEmpty(deaprtName)){
					node.setCol1(deaprtName);
				}else{
					node.setCol1(user.getDepartName());
				}
				node.setCol2("");
				node.setCol3("2");
				node.setCol4(name);
				node.setCol5(code);
				node.setCol6(new SimpleDateFormat("yyyy").format(new Date()));
				if(null != tProBasicInfoMng.getOutComeSubject( "commOut", code, new SimpleDateFormat("yyyy").format(new Date()),null) && 
						tProBasicInfoMng.getOutComeSubject( "commOut", code, new SimpleDateFormat("yyyy").format(new Date()),null).size() > 0){
					node.setState("closed");
				}else{
					node.setLeaf(true);
				}
				treeList.add(node);
			}
		}
		return treeList;
	}

	@Override
	public void editSave(User user, TBudgetControlNum bean,
			String personOutJson, String commOutJson, String yearOutJson,
			String leastOutJson) throws JsonParseException,
			JsonMappingException, IOException {
		
		//?????????
		//List<TBasicExpend> personOutList = new ArrayList<>();
		//List<TBasicExpend> commOutList = new ArrayList<>();
		List<TProExpend> yearOutList = new ArrayList<>();
		//List<TProExpend> leastOutList = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		//?????????????????????
		TBudgetControlNum num = findById(bean.getfCId());
		num.setfAllAmount(bean.getfAllAmount());
		num.setfProExpAmount(bean.getfProExpAmount());
		TBudgetControlNum controlNum = saveControlNum(num, user);
		/*//??????????????????
		personOutList = mapper.readValue(personOutJson, new TypeReference<List<TBasicExpend>>(){});//json??????
		tBasicExpendMng.saveBasicOuts(user,personOutList);//??????
		//??????????????????
		commOutList = mapper.readValue(commOutJson, new TypeReference<List<TBasicExpend>>(){});
		tBasicExpendMng.saveBasicOuts(user,commOutList);//??????*/
		//????????????????????????
		yearOutList = mapper.readValue(yearOutJson, new TypeReference<List<TProExpend>>(){});
		//?????????????????????????????????????????????????????????
		for (int i = yearOutList.size()-1; i >= 0; i--) {
			if(yearOutList.get(i).getfProBudgetAmount()!=yearOutList.get(i).getfControlAmount()){
				TProBasicInfo tpi = tProBasicInfoMng.findbyCode(yearOutList.get(i).getfProCode());
				tpi.setFFlowStauts("-1");
				tpi.setFProLibType("1");
				tpi.setProvideAmount1(yearOutList.get(i).getfControlAmount());
				super.merge(tpi);
			}
		}
		tProExpendMng.saveProOuts(user,yearOutList);
		/*//????????????????????????
		leastOutList = mapper.readValue(leastOutJson, new TypeReference<List<TProExpend>>(){});
		tProExpendMng.saveProOuts(user,leastOutList);*/
	}
	

}
