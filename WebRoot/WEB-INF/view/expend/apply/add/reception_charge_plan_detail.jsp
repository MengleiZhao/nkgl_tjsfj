<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="dg_reception_charge_plan_detail" class="easyui-datagrid" 
	style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	<!-- toolbar: '#rcp', -->
	url: '${base}/apply/receptionChargePlan?id=${bean.gId}',
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
				<th data-options="field:'totalMeal',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:2,groupSeparator:',',onChange:totalMealChange}}">当日用餐费用合计</th>
				<th data-options="field:'totalFare',width:195,align:'center',editor:{type:'numberbox',options:{readonly:true,precision:2,groupSeparator:',',onChange:totalFareChange}}">当日用车费用合计</th>
			</tr>
		</thead>
	</table>
	<%-- <c:if test="${empty detail}">
	<div id="rcp" style="height:30px;padding-top : 8px">
		<a href="javascript:void(0)" onclick="removeitRCP()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendRCP()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	</c:if> --%>
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

//接待人员表格添加删除，保存方法
var editChargeIndex = undefined;
function endEditingRCP() {
	if (editChargeIndex == undefined) {
		return true
	}
	if ($('#dg_reception_charge_plan_detail').datagrid('validateRow', editChargeIndex)) {
		var dmp = $('#dg_reception_charge_plan_detail').datagrid('getEditor', {
			index : editChargeIndex,
			field : 'fPro'
		});
		
		$('#dg_reception_charge_plan_detail').datagrid('endEdit', editChargeIndex);
		editChargeIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowRCP(index) {
	if (editChargeIndex != index) {
		if (endEditingRCP()) {
			$('#dg_reception_charge_plan_detail').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editChargeIndex = index;
		} else {
			$('#dg_reception_charge_plan_detail').datagrid('selectRow', editChargeIndex);
		}
	}
}
function appendRCP() {
	if (endEditingRCP()) {
		$('#dg_reception_charge_plan_detail').datagrid('appendRow', {});
		editChargeIndex = $('#dg_reception_charge_plan_detail').datagrid('getRows').length - 1;
		$('#dg_reception_charge_plan_detail').datagrid('selectRow', editChargeIndex).datagrid('beginEdit',editChargeIndex);
	}
}
function removeitRCP() {
	if (editChargeIndex == undefined) {
		return
	}
	$('#dg_reception_charge_plan_detail').datagrid('cancelEdit', editChargeIndex).datagrid('deleteRow',editChargeIndex);
	editChargeIndex = undefined;
}
function acceptRCP() {
	if (endEditingRCP()) {
		$('#dg_reception_charge_plan_detail').datagrid('acceptChanges');
	}
}
//计算收费总额
function totalFareChange(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var rows = $('#dg_reception_charge_plan_detail').datagrid('getRows');
	var totalnum = 0;
	var totalMealnum = 0;
    var totalFarenum = 0;
    var row = $('#dg_reception_charge_plan_detail').datagrid('getSelected');
    var rowindex = $('#dg_reception_charge_plan_detail').datagrid('getRowIndex',row);
    for (var i = 0; i < rows.length; i++) {
   		 if(rowindex==i){
   			 var ed = $('#dg_reception_charge_plan_detail').datagrid('getEditor', {index:rowindex,field:'totalFare'});
    		 totalFarenum += parseFloat(isNotEmpty($(ed.target).numberbox('getValue'))?$(ed.target).numberbox('getValue'):0.00);
   			 var ed1 = $('#dg_reception_charge_plan_detail').datagrid('getEditor', {index:rowindex,field:'totalMeal'});
   			 totalMealnum += parseFloat(isNotEmpty($(ed1.target).numberbox('getValue'))?$(ed1.target).numberbox('getValue'):0.00);
   		 }else{
    		 totalFarenum += parseFloat(rows[i]['totalFare']);
	   		 totalMealnum += parseFloat(rows[i]['totalMeal']);
   		 }
	}
    totalnum = parseFloat(totalFarenum) + parseFloat(totalMealnum);
   	//给两个框赋值
	$('#costCharge').val(totalnum.toFixed(2));
	$('#costCharge_span').html(fomatMoney(totalnum,2)+" [元]");
}	

function totalMealChange(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var rows = $('#dg_reception_charge_plan_detail').datagrid('getRows');
	var totalnum = 0;
	var totalMealnum = 0;
    var totalFarenum = 0;
    var row = $('#dg_reception_charge_plan_detail').datagrid('getSelected');
    var rowindex = $('#dg_reception_charge_plan_detail').datagrid('getRowIndex',row);
    for (var i = 0; i < rows.length; i++) {
  		 if(rowindex==i){
   			 var ed = $('#dg_reception_charge_plan_detail').datagrid('getEditor', {index:rowindex,field:'totalFare'});
    		 totalFarenum += parseFloat(isNotEmpty($(ed.target).numberbox('getValue'))?$(ed.target).numberbox('getValue'):0.00);
   			 var ed1 = $('#dg_reception_charge_plan_detail').datagrid('getEditor', {index:rowindex,field:'totalMeal'});
   			 totalMealnum += parseFloat(isNotEmpty($(ed1.target).numberbox('getValue'))?$(ed1.target).numberbox('getValue'):0.00);
   		 }else{
    		 totalFarenum += parseFloat(rows[i]['totalFare']);
	   		 totalMealnum += parseFloat(rows[i]['totalMeal']);
   		 }
	}
    totalnum = parseFloat(totalFarenum) + parseFloat(totalMealnum);
   //给两个框赋值
	$('#costCharge').val(totalnum.toFixed(2));
	$('#costCharge_span').html(fomatMoney(totalnum,2)+" [元]");
}	
	
</script>