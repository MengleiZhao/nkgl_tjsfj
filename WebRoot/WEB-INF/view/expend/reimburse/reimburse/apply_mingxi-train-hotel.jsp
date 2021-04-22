<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>


<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<div id="apply_hotel_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">师资费-住宿费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span id="hotelAmount"><fmt:formatNumber groupingUsed="true" value="${trainingBean.hotelMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</div>
	<table id="apply_mingxi-hotel-dg" class="easyui-datagrid" style="width:693px;height:auto;"
	data-options="
	toolbar: 'apply_hotel_Id',
	<c:if test="${!empty trainingBean.tId}">
	url: '${base}/apply/teacherMingxi?id=${trainingBean.tId}&costType=hotel',
	</c:if>
	<c:if test="${empty trainingBean.tId}">
	url: '',
	</c:if>
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:false,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<th data-options="field:'thId',hidden:true"></th>
				<th data-options="field:'lecturerName',required:'required',align:'center',width:170">讲师姓名</th>
				<th data-options="field:'hotelStd',required:'required',align:'center',width:166">住宿费标准</th>
				<th data-options="field:'hotelStdTotal',required:'required',align:'center',width:180">总额标准</th>
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}">申请金额[元]</th>
		</tr>
	</thead>
	</table>
	
</div>

<script type="text/javascript">
</script>
