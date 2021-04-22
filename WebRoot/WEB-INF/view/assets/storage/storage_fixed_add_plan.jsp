<%@ page language="java" pageEncoding="UTF-8"%>
   <table id="fixed_add_plan1" class="easyui-datagrid"  style="height:auto;"
		data-options="
			singleSelect: true,
			rownumbers:true,
			toolbar: '#tb',
			url: '${base}/Storage/cgpmJsonPagination?id=${bean.fId_S}',
			method: 'post',
			onClickRow: onClickRowPlan
		">
	<thead>
		<tr>
			<th data-options="field:'fListId',hidden:true"></th>
			<th data-options="field:'fId_S',hidden:true"></th>
			<th data-options="field:'acRId',hidden:true"></th>
			<th data-options="field:'fcId',hidden:true"></th>
			<th data-options="field:'fId_U',hidden:true"></th>
			<th data-options="field:'fAssType',hidden:true"></th>
			<th data-options="field:'fmModel',hidden:true"></th>
			<th data-options="field:'fFixedTypeId', editor:'textbox',hidden:true"></th>
			<th data-options="field:'fFixedTypeCode', editor:'textbox',hidden:true"></th>
			<th data-options="field:'fAssCode',align:'center'" style="width: 20%">卡片编号</th>
			<th data-options="field:'fFixedType',align:'center',editor:{type:'textbox',options:{editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
						var index=$('#fixed_add_plan1').datagrid('getRowIndex',$('#fixed_add_plan1').datagrid('getSelected'));
					     selectTypeDetail(index,'fixed_add_plan1');
					     }}]}}" style="width: 20%">资产分类</th>
			<th data-options="field:'fAssName',align:'center', editor:'textbox'" style="width: 15%">资产名称</th>
			<th data-options="field:'fcCode',align:'center'" style="width: 25%">合同编号</th>
			<th data-options="field:'fMMode',align:'center', editor:'textbox'," style="width: 15%">型号</th>
			<th data-options="field:'fActionDate',align:'center',editor:{type:'datebox',options:{editable: false}},formatter:ChangeDateFormat" style="width: 25%">取得日期</th>
			<th data-options="field:'fFinancialCode',align:'center'" style="width: 15%">会计凭证号</th>
			<th data-options="field:'fFinancialDate',align:'center'" style="width: 20%">财务入账日期</th>
			<th data-options="field:'fDepreciationStatus',align:'center'" style="width: 10%">折旧状态</th>
			<th data-options="field:'fValueType',align:'center'" style="width: 10%">价值类型</th>
			<th data-options="field:'amount',align:'center'" style="width: 20%">价值（元）</th>
			<th data-options="field:'fAppropriationAmount',align:'center'" style="width: 30%">财政拨款(元)</th>
			<th data-options="field:'fUnappropriationAmount',align:'center'" style="width: 30%">非财政拨款(元)</th>
		</tr>
	</thead>
</table>
<div id="tb" style="height:30px;">
	<a href="javascript:void(0)" id="removeitPlanId" hidden="hidden" onclick="removeitPlan()" style="float: right;">
		<img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
	</a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" id="appendPlanId" hidden="hidden" onclick="appendPlan()" style="float: right;">
		<img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
	</a>
	<a style="float: right;">&nbsp;&nbsp;</a>
</div>
<script type="text/javascript">
//总值校验是否正确
function sumAcount(newValue,oldValue){
	var row = $('#fixed_add_plan1').datagrid('getSelected');//获得选择行
	var index=$('#fixed_add_plan1').datagrid('getRowIndex',row);//获得选中行的行号
	var tr = $('#fixed_add_plan1').datagrid('getEditors', index);//获取选中行的数据
	var num = tr[7].target.numberbox('getValue');//获得数量
	var price = tr[8].target.numberbox('getValue');//获得单价
	if((num*price)!=newValue){
		tr[9].target.numberbox('setValue',num*price);//设置值一个正确的值给总值
	}
	
	//设置总额
	var rows = $('#fixed_add_plan1').datagrid('getRows');//获得选择行
	var num1 = 0;
	for (var i = 0; i < rows.length; i++) {
	  if (!isNaN(parseFloat(rows[i]['fAmount']))) {
	  	num1 += parseFloat(rows[i]['fAmount']);
	  }
	}
	var num = parseFloat(newValue);
	var numOld = parseFloat(row.fAmount);
	if (!isNaN(num)) {
		if (!isNaN(numOld) && isNaN(parseFloat(oldValue))) {
			return;
		} else {
			if (!isNaN(numOld)) {
				num1 = num1 + num - numOld;
			} else {
				num1 = num1 + num;
			}
		}
	}
	$('#S_fSumAmount').numberbox('setValue', num1);
}
//接待人员表格添加删除，保存方法
var editIndexPlan = undefined;
function endEditingPlan() {
	
	if (editIndexPlan == undefined) {
		return true
	}
	if ($('#fixed_add_plan1').datagrid('validateRow', editIndexPlan)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		/* var tr = $('#fixed_add_plan1').datagrid('getEditors', editIndexPlan);
		var text5=tr[5].target.combobox('getText');
		if(text5!='--请选择--'){
			tr[5].target.combobox('setValues',text5);
		}
		var text7=tr[7].target.combobox('getText');
		if(text7!='--请选择--'){
			tr[7].target.combobox('setValues',text7);
		} */
		$('#fixed_add_plan1').datagrid('endEdit', editIndexPlan);
		editIndexPlan = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPlan(index) {
	
	if (editIndexPlan != index) {
		if (endEditingPlan()) {
			$('#fixed_add_plan1').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndexPlan = index;
		} else {
			$('#fixed_add_plan1').datagrid('selectRow', editIndexPlan);
		}
	}
}
function appendPlan() {
	if (endEditingPlan()) {
		$('#fixed_add_plan1').datagrid('appendRow', {
		});
		editIndexPlan = $('#fixed_add_plan1').datagrid('getRows').length - 1;
		$('#fixed_add_plan1').datagrid('selectRow', editIndexPlan).datagrid('beginEdit',editIndexPlan);
	}
}
function removeitPlan() {
	if (editIndexPlan == undefined) {
		return
	}
	$('#fixed_add_plan1').datagrid('cancelEdit', editIndexPlan).datagrid('deleteRow',
			editIndexPlan);
	editIndexPlan = undefined;
}
function acceptPlan() {
	if (endEditingPlan()) {
		$('#fixed_add_plan1').datagrid('acceptChanges');
	}
}
function getPlan() {
	acceptPlan();
	var rows = $('#fixed_add_plan1').datagrid('getRows');
	var entities = '';
	for (var i = 0; i < rows.length; i++) {
		entities = entities + JSON.stringify(rows[i]) + ',';
	}
	entities = '[' + entities.substring(0, entities.length - 1) + ']';
	return entities;
}
	
function onSelectCode(id,code){
	$("#S_facpId").val(id);
	acceptPlan();
	var rows = $('#fixed_add_plan1').datagrid('getRows');
	for (var i = rows.length-1; i >= 0; i--) {
		$('#fixed_add_plan1').datagrid('deleteRow',i);
	}
	editIndexPlan = undefined;
	$.ajax({
		type:'post',
		async:false,
		dataType:'json',
		url:base+'/cgreceive/acceptContractRegisterList?id='+id+'&code='+code,
		success:function (data){
			for (var i = 0; i < data.length; i++) {
				for (var j = 0; j < data[i].fNowCheckedNum; j++) {
					$('#fixed_add_plan1').datagrid('appendRow',{
						acRId: data[i].acRId,
						fAssName: data[i].fmName,
						fMMode: data[i].fmSpecif,
						fActionDate: data[i].fActionDate,
						fmModel:data[i].fmModel,
						fActionDate:data[i].facpTime,
						fcId:data[i].fcId,
						fId_U:data[i].fId_U,
						fcCode:data[i].fcCode,
					});
				}
			}
			acceptPlan();
		}
	});
	
}

function selectTypeDetail(index,tabId){
	var fAssClass =  $('#storage_fixed_fAssClass').combobox('getValue');
	var win=creatSecondWin('选择-资产类型',900,580,'icon-search',"/assetType/assetTypeList?index="+index+"&tabId="+tabId+"&type=ZCLX-02"+'&fAssClass='+fAssClass);
    win.window('open');
}

</script>