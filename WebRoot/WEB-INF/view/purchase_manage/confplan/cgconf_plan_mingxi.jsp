<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="qingdangrid" class="easyui-datagrid"  style="width:660px;height:auto"
data-options="
<c:if test="${empty bean.fplId}">
url: '',
</c:if>
<c:if test="${!empty bean.fplId}">
url: '${base}/cgconfplangl/mingxi?id=${bean.fplId}',
</c:if>
method: 'get'
">
<thead>
	<tr>
		<th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
		<th data-options="field:'fpurName',align:'left',editor:'textbox'" style="width: 10%">采购目录</th>
		<th data-options="field:'fmeasureUnit',align:'left',editor:'textbox'" style="width: 7%">单位</th>
		<th data-options="field:'fpurBrand',align:'left',editor:'textbox'" style="width: 10%">品牌</th>
		<th data-options="field:'fspecifModel',align:'left',editor:'textbox'" style="width: 10%">规格型号</th>
		<th data-options="field:'fnum',align:'left',editor:'numberbox'" style="width: 10%">数量</th>
		<th data-options="field:'funitPrice',align:'left',editor:'numberbox'" style="width:10%">单价[元]</th>
		<th data-options="field:'fsumMoney',align:'left',editor:'numberbox'" style="width: 10%">金额[元]</th>
		<th data-options="field:'fcommProp',align:'left',editor:'textbox'" style="width: 10%">商品属性</th>
		<th data-options="field:'fneedTime',align:'left',editor:'textbox',formatter:ChangeDateFormat" style="width: 20%">需求时间</th>
	</tr>
</thead>
</table>



