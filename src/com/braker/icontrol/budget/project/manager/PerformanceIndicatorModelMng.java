package com.braker.icontrol.budget.project.manager;

import java.util.List;

import com.braker.common.hibernate.BaseManager;
import com.braker.icontrol.budget.project.entity.PerformanceIndicatorModel;
import com.braker.icontrol.budget.project.entity.TProBasicFunds;
/**
 * 
 * <p>Description: 项目绩效指标接口</p>
 * @author:焦广兴
 * @date： 2019年10月09日
 */
public interface PerformanceIndicatorModelMng extends BaseManager<PerformanceIndicatorModel>{

	  public int save(Integer fProId, List<PerformanceIndicatorModel> list) throws Exception;
}
