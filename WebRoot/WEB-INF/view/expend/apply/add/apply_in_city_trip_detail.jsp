<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px;">

	<table id="in_city_trip_tab_id" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#in_city_trip_id',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/applyInCityPage?gId=${bean.gId}&travelType=GWCX',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'ftId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
				<th data-options="field:'fPerson',width:160,align:'center',editor:{type:'textbox',options:{editable:false}}">姓名</th>
				<!-- <th data-options="field:'fAdjacentDay',align:'center',width:180,editor:{type:'numberbox',options:{editable:false}}" >相邻区补贴天数</th> -->
				<!-- <th data-options="field:'fDistanceDay',align:'center',width:180,editor:{type:'numberbox',options:{editable:false }}" >补贴天数</th> -->
				<th data-options="field:'fAdjacentDay',width:260,align:'center',editor:{type:'textbox',options:{editable:true}}">补贴天数</th>
				<th data-options="field:'fApplyAmount',width:255,align:'center',editor:{type:'numberbox',options:{editable:false,precision:2 }}">申请金额</th>
			</tr>
		</thead>
	</table>
	<div id="in_city_trip_id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">市内交通费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
	</div>
	<a style="float: right;color: #666666;font-size:12px;">申请费用：<span id=""><fmt:formatNumber groupingUsed="true" value="${bean.cityAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
</div>
<script type="text/javascript">
</script>