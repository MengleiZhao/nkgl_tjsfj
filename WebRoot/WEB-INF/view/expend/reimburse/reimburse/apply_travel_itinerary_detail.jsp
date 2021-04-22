<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<table id="tracel_itinerary_tab_id_detail" class="easyui-datagrid" style="width:693px;height:auto"
	data-options="
	singleSelect: true,
	url: '${base}/apply/applyTravelPage?gId=${applyBean.gId}&travelType=GWCC',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'trId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
				<th data-options="field:'travelDateStart',width:110,align:'center',readonly:true,editor:{type:'datebox', options:{editable:false}}">出发日期</th>
				<th data-options="field:'travelDateEnd',width:110,align:'center',readonly:true,editor:{type:'datebox',options:{editable:false}}">撤离日期/抵津日期</th>
				<th data-options="field:'travelAreaName',width:150,align:'center'">目的地</th>
				<th data-options="field:'travelAreaId',hidden:true,readonly:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelAttendPeop',width:123,align:'center',readonly:true,editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     }}]}}">人员（可多选）</th>
				<th data-options="field:'travelAttendPeopId',hidden:true,readonly:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelPersonnelLevel',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'reason',width:175,align:'center',readonly:true,editor:{type:'textbox'}">主要工作内容</th>
			</tr>
		</thead>
	</table>
</div>
