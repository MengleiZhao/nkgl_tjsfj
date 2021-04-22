<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="qingdangrid" class="easyui-datagrid"
data-options="
striped:true,
<c:if test="${empty bean.fpId}">
url: '',
</c:if>
<c:if test="${!empty bean.fpId}">
url: '${base}/cgconfplangl/mingxi?id=${bean.fpId}',
</c:if>
method: 'get'
">
<thead>
	<tr>
		<th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'num',align:'center'" style="width: 5%"></th>
		<th data-options="field:'fpurName',align:'center',editor:'textbox'" style="width: 19%">商品名称</th>
		<th data-options="field:'fnum',align:'center',editor:'numberbox'" style="width: 14%">数量</th>
		<th data-options="field:'fmeasureUnit',align:'center',editor:'textbox'" style="width: 15%">单位</th>
		<th data-options="field:'fspecifModel',align:'center',editor:'textbox'" style="width: 15%">规格型号</th>
		<th data-options="field:'funitPrice',align:'right',editor:'numberbox'" style="width: 19%">单价[元]</th>
		<th data-options="field:'fsumMoney',align:'right',editor:'numberbox'" style="width: 19%">预算金额[元]</th>
		<th data-options="field:'fcommProp',align:'center',editor:'textbox'" style="width: 27%">其他要求</th>
	</tr>
</thead>
</table>