<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="outside_traffic_tab_id_detail" class="easyui-datagrid" style="width:693px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#outside_traffic_reim_Id_detail',
	<c:if test="${!empty applyBean.gId}">
	url: '${base}/apply/applyOutsideTrafficPage?gId=${applyBean.gId}&travelType=GWCC',
	</c:if>
	<c:if test="${empty applyBean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<%-- <c:if test="${empty detail}">
	onClickRow: onClickRowOutsideTraffic,
	</c:if> --%>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'trId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
				<th data-options="field:'fStartDate',width:110,align:'center',editor:{type:'datebox', options:{editable:false}},formatter:ChangeDateFormat">出发日期</th>
				<th data-options="field:'fEndDate',width:110,align:'center',editor:{type:'datebox',options:{editable:false}},formatter:ChangeDateFormat">到达日期</th>
				<th data-options="field:'travelPersonnel',width:130,align:'center',editor:{type:'textbox',options:{editable:false}}">出行人员</th>
				<th data-options="field:'vehicle',width:110,align:'center',editor:{type:'textbox',options:{editable:false}}">交通工具</th>
				<th data-options="field:'vehicleLevel',width:110,align:'center',editor:{type:'textbox',options:{editable:false}}">交通工具级别</th>
				<th data-options="field:'travelAttendPeopId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelPersonnelLevel',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'applyAmount',width:100,align:'center',editor:{type:'numberbox',options:{editable:false,precision:2}}">申请费用</th>
			</tr>
		</thead>
	</table>
	<div id="outside_traffic_reim_Id_detail" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">城市间交通费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span id="applyOutsideTrafficAmount"><fmt:formatNumber groupingUsed="true" value="${applyBean.outsideAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</div>
</div>