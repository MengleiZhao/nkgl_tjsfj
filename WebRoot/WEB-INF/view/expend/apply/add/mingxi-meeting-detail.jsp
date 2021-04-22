<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="appli-detail-dg1" class="easyui-datagrid" style="width:707px;height:auto;"
	data-options="
	toolbar: '#appli-detail-tb',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/mingxi?id=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow,
	</c:if>
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
			<th data-options="field:'totalStandard',required:'required',align:'center',width:180">总额标准[元]</th>
			<th data-options="field:'applySum',required:'required',align:'center',width:180">申请金额[元]</th>
		</tr>
	</thead>
	</table>
</div>
<input type="hidden" id="mingxiJson" name="mingxi"/>