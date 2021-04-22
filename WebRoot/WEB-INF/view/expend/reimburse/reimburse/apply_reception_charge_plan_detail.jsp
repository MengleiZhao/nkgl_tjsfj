<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="dg_reception_charge_plan_detail" class="easyui-datagrid" 
	style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	<!-- toolbar: '#rcp', -->
	url: '${base}/apply/receptionChargePlan?id=${applyBean.gId}',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'fSPId',hidden:true"></th>
				<th data-options="field:'fcTime',width:195,align:'center',editor:{type:'datebox',options:{readonly:true,}},formatter:ChangeDateFormat">日期</th>
				<th data-options="field:'breakfastPeopleNum',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:0,groupSeparator:','}}">早餐用餐人数</th>
				<th data-options="field:'lunchPeopleNum',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:0,groupSeparator:','}}">午餐用餐人数</th>
				<th data-options="field:'dinnerPeopleNum',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:0,groupSeparator:','}}">晚餐用餐人数</th>
				<th data-options="field:'halfDayPeopleNum',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:0,groupSeparator:','}}">半天用车人数</th>
				<th data-options="field:'dayPeopleNum',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:0,groupSeparator:','}}">全天用车人数</th>
				<th data-options="field:'totalMeal',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:2,groupSeparator:','}}">当日用餐费用合计</th>
				<th data-options="field:'totalFare',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:2,groupSeparator:','}}">当日用车费用合计</th>
			</tr>
		</thead>
	</table>
	<input type="hidden" id="receptionChargePlanJson" name="chargeJson" />
	<div style="overflow:auto;margin-top: 10px;">
		<span style="float: right;color: #0000CD;">
			<span>合计金额： </span>
			<span style="float: right;"  id="costCharge_span" ><fmt:formatNumber groupingUsed="true" value="${receptionBean.costCharge}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
			<input type="hidden" id="costCharge" name="costCharge" value="${receptionBean.costCharge}"  />
		</span>
	</div>
</div>
	
	
<script type="text/javascript">
	
</script>