<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="apply_dg_train_plan" class="easyui-datagrid" 
	style="width:693px;height:auto"
	data-options="
	singleSelect: true,
	<c:if test="${!empty trainingBean.tId}">
	url: '${base}/apply/trainPlan?id=${trainingBean.tId}',
	</c:if>
	<c:if test="${empty trainingBean.tId}">
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
				<th data-options="field:'planId',hidden:true"></th>
				<th data-options="field:'date',width:160,align:'center',editor:{type:'datebox',options:{onHidePanel:changeTime1,showSeconds:false}}">日期</th>
				<th data-options="field:'timeStart',width:160,align:'center',editor:{type:'timespinner',options:{onChange:changeTime3,showSeconds:false},formatter:ChangeDateFormatTrain}">起始时间</th>
				<th data-options="field:'timeEnd',width:160,align:'center',editor:{type:'timespinner',options:{onChange:changeTime4,showSeconds:false},formatter:ChangeDateFormatTrain}">结束时间</th>
				<th data-options="field:'lecturerName',width:160,align:'center',editor:{type:'combobox',options:{
                                valueField:'id',
                                textField:'text',
                                multiple: true,
                                editable:false
                            }}">讲师姓名</th>
				<th data-options="field:'lessonTime',width:90,align:'center',
					editor:{type:'textbox',options:{}}">学时</th>
				<th data-options="field:'arrange',width:215,align:'center',editor:'textbox'">培训内容</th>
			</tr>
		</thead>
	</table>
	
</div>

	
<script type="text/javascript">
</script>