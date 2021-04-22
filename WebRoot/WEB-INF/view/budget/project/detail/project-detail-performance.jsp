<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<style type="text/css">
</style> 

<div>
	<table>
		<tr class="trbody">
	   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;总体目标</td>
	     	<td class="td2" colspan="4">
	     		<input id="project_add_totalityDescribe" name="totalityDescribe" class="easyui-textbox" style="height:30px; width:746px"  readonly="readonly" value="<c:out value="${bean.totalityDescribe}"></c:out>" >
	     		
		</td>
		</tr>
	</table>
</div>
<table id="detail_performance" class="easyui-datagrid" style="width:870px;height:auto"
	data-options="singleSelect: true,rownumbers : true,url: '${base}/project/proIndexList?fProId=${bean.FProId}',
		method: 'post',striped:true, ">
	<thead>
		<tr>
			<th data-options="align:'left',field:'tOneName'" style="width: 33%">一级指标</th>
			<th data-options="align:'left',field:'tTwoName'" style="width: 34%">二级指标</th>
			<th data-options="align:'left',field:'tIndexVal'" style="width: 34%">指标值</th>
		</tr>
	</thead>
</table>

