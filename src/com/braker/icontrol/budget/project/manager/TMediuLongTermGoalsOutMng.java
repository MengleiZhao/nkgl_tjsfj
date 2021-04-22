package com.braker.icontrol.budget.project.manager;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.braker.common.entity.DataEntity;
import com.braker.common.hibernate.BaseManager;
import com.braker.core.model.User;
import com.braker.icontrol.budget.project.entity.TMediuLongTermGoals;
import com.braker.icontrol.budget.project.entity.TMediuLongTermGoalsOut;
import com.braker.icontrol.budget.project.entity.TProBasicInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface TMediuLongTermGoalsOutMng extends BaseManager<TMediuLongTermGoalsOut>{

	/**
	 * 保存中长期指标信息
	 * @param project
	 * @param user
	 * @param list
	 */
	public void saveLongIndexByProject(TProBasicInfo project, User user,List<TMediuLongTermGoalsOut> indexList);
	
	/**
	 * 保存中长期指标信息
	 * @param project
	 * @param user
	 * @param longIndexJson
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void saveLongIndexByProject(TProBasicInfo project, User user, String longIndexJson) throws ParseException, JsonParseException, JsonMappingException, IOException;
	
	/**
	 * 解析数据集合生成中长期指标集合
	 * @param dataList 数据集合
	 * @return
	 * @throws ParseException 
	 */
	public List<TMediuLongTermGoalsOut> generateLongIndexFromDataEntity(List<DataEntity> dataList) throws ParseException;

	/**
	 * 获得某项目的所有中长期指标
	 * @param proId 项目id
	 * @return
	 */
	public List<TMediuLongTermGoalsOut> getGoalOutByPro(String proId);

	/**
	 * 删除某项目的中长期指标
	 * @param proId 项目id
	 * @return editList 不能删除的目标列表
	 */
	public void deleteGoalOutByProject(Integer fProId, List<TMediuLongTermGoalsOut> editList);

	
}
