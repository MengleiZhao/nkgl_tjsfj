<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<c:if test="${type!=1}">
<!-- <div class="window-title">费用明细</div> -->
</c:if>


<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<div id="hotel_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">师资费-住宿费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span id="hotelAmount"><fmt:formatNumber groupingUsed="true" value="${trainingBean.hotelMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</div>
	<table id="mingxi-hotel-dg" class="easyui-datagrid" style="width:707px;height:auto;"
	data-options="
	toolbar: 'hotel_Id',
	<c:if test="${!empty trainingBean.tId}">
	url: '${base}/apply/teacherMingxi?id=${trainingBean.tId}&costType=hotel',
	</c:if>
	<c:if test="${empty trainingBean.tId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow5,
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
				<th data-options="field:'hotelStd',required:'required',align:'center',width:180">住宿费标准</th>
				<th data-options="field:'hotelStdTotal',required:'required',align:'center',width:180">总额标准</th>
				<c:if test="${empty detail}">
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{onChange:addNum3,precision:2,groupSeparator:','}}">申请金额[元]</th>
				</c:if>
				<c:if test="${!empty detail}">
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}">申请金额[元]</th>
				</c:if>
		</tr>
	</thead>
	</table>
	
</div>

<input type="hidden" id="hotelJson" name="hotelJson"/>
<script type="text/javascript">
function getHotelJson(){
	accept5();
	var rows = $('#mingxi-hotel-dg').datagrid('getRows');
	var hotelJson = "";
	for (var i = 0; i < rows.length; i++) {
		hotelJson = hotelJson + JSON.stringify(rows[i]) + ",";
	}
	$('#hotelJson').val(hotelJson);
}
//接待人员表格添加删除，保存方法
var editIndex5 = undefined;
function endEditing5() {
	if (editIndex5 == undefined) {
		return true
	}
	if ($('#mingxi-hotel-dg').datagrid('validateRow', editIndex5)) {
		$('#mingxi-hotel-dg').datagrid('endEdit', editIndex5);
		editIndex5 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow5(index) {
		if (editIndex5 != index) {
			if (endEditing5()) {
				$('#mingxi-hotel-dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex5 = index;
			} else {
				$('#mingxi-hotel-dg').datagrid('selectRow', editIndex5);
			}
		}
}
function accept5() {
	if (endEditing5()) {
		$('#mingxi-hotel-dg').datagrid('acceptChanges');
	}
}
//计算申请金额
function addNum3(newValue,oldValue) {
		var row = $('#mingxi-hotel-dg').datagrid('getSelected');//获得选择行
		var index=$('#mingxi-hotel-dg').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#mingxi-hotel-dg').datagrid('getEditors', index);
		var standar= parseFloat(row.hotelStdTotal);//获得选中行的开支标准
		if(isNaN(standar)){
			standar=0;
		}
		if(parseFloat(newValue)>parseFloat(standar)){
			
			alert('申请金额不能大于开支标准，请核对！');
			tr[0].target.numberbox('setValue',0);
			newValue=0;
		}
		
		var num = 0;
		var rows = $('#mingxi-hotel-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i!=index){
				if(rows[i].applySum!=""&&rows[i].applySum!=null){
					num += parseFloat(rows[i].applySum);
				}
			}
		}
		if(newValue!=""&&newValue!=null) {
			num += parseFloat(newValue);
		}
		$('#hotelMoney').val(num.toFixed(2));
		$('#hotelAmount').html(fomatMoney(num,2)+"[元]");
		addTotalAmount()
}
</script>
