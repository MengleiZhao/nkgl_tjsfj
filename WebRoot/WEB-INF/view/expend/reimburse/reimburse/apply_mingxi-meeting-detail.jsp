<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="appli-detail-dg1" class="easyui-datagrid" style="width:693px;height:auto;"
	data-options="
	toolbar: '#appli-detail-tb',
	url: '${base}/apply/mingxi?id=${applyBean.gId}',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:false,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<th data-options="field:'cId',hidden:true"></th>
				<th data-options="field:'costDetail',required:'required',align:'center',width:170">费用名称</th>
				<th data-options="field:'standard',required:'required',align:'center',width:180">费用标准（元/人天）</th>
				<th data-options="field:'totalStandard',required:'required',align:'center',width:170">总额标准[元]</th>
				<th data-options="field:'applySum',required:'required',align:'center',width:175">申请金额[元]</th>
		</tr>
	</thead>
	</table>
</div>