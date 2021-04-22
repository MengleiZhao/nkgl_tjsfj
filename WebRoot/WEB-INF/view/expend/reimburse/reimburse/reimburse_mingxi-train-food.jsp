<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<c:if test="${type!=1}">
<!-- <div class="window-title">费用明细</div> -->
</c:if>


<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<div id="food_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">师资费-伙食费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span ><fmt:formatNumber groupingUsed="true" value="${trainingBean.foodMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<c:if test="${operation=='add'}">
		<a style="float: left;color: #666666;font-size:12px;">汇总金额：<span id="foodAmount">[元]</span></a>
		</c:if>
		<c:if test="${operation!='add'}">
		<a style="float: left;color: #666666;font-size:12px;">汇总金额：<span id="foodAmount"><fmt:formatNumber groupingUsed="true" value="${reimbTrainingBean.foodMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		</c:if>
	</div>
	<table id="mingxi-food-dg" class="easyui-datagrid" style="width:693px;height:auto;"
	data-options="
	toolbar: 'food_Id',
	<c:if test="${!empty reimbTrainingBean.tId}">
	url: '${base}/apply/teacherMingxi?id=${reimbTrainingBean.tId}&costType=food',
	</c:if>
	<c:if test="${empty reimbTrainingBean.tId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow6,
	</c:if>
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
				<th data-options="field:'foodStd',required:'required',align:'center',width:166">伙食费标准</th>
				<th data-options="field:'foodStdTotal',required:'required',align:'center',width:180">总额标准</th>
				<c:if test="${empty detail}">
				<th data-options="field:'reimbSum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{onChange:addNum4,precision:2,groupSeparator:','}}">报销金额[元]</th>
				</c:if>
				<c:if test="${!empty detail}">
				<th data-options="field:'reimbSum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}">报销金额[元]</th>
				</c:if>
		</tr>
	</thead>
	</table>
	
</div>


<script type="text/javascript">
function getFoodJson(){
	accept6();
	var rows = $('#mingxi-food-dg').datagrid('getRows');
	var foodJson = "";
	for (var i = 0; i < rows.length; i++) {
		foodJson = foodJson + JSON.stringify(rows[i]) + ",";
	}
	$('#foodJson').val(foodJson);
}

//接待人员表格添加删除，保存方法
var editIndex6 = undefined;
function endEditing6() {
	if (editIndex6 == undefined) {
		return true
	}
	if ($('#mingxi-food-dg').datagrid('validateRow', editIndex6)) {
		$('#mingxi-food-dg').datagrid('endEdit', editIndex6);
		editIndex6 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow6(index) {
		if (editIndex6 != index) {
			if (endEditing6()) {
				$('#mingxi-food-dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex6 = index;
			} else {
				$('#mingxi-food-dg').datagrid('selectRow', editIndex6);
			}
		}
}
function accept6() {
	if (endEditing6()) {
		$('#mingxi-food-dg').datagrid('acceptChanges');
	}
}
//计算申请金额
function addNum4(newValue,oldValue) {
		var row = $('#mingxi-food-dg').datagrid('getSelected');//获得选择行
		var index=$('#mingxi-food-dg').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#mingxi-food-dg').datagrid('getEditors', index);
		var standar= parseFloat(row.foodStdTotal);//获得选中行的开支标准
		if(isNaN(standar)){
			standar=0;
		}
		if(parseFloat(newValue)>parseFloat(standar)){
			
			alert('报销金额不能大于开支标准，请核对！');
			tr[0].target.numberbox('setValue',0);
			newValue=0;
		}
		
		var num = 0;
		var rows = $('#mingxi-food-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i!=index){
				if(rows[i].reimbSum!=""&&rows[i].reimbSum!=null){
					num += parseFloat(rows[i].reimbSum);
				}
			}
		}
		if(newValue!=""&&newValue!=null) {
			num += parseFloat(newValue);
		}
		$('#foodMoney').val(num.toFixed(2));
		$('#foodAmount').html(fomatMoney(num,2)+"[元]");
		addTotalAmount();
}
</script>
