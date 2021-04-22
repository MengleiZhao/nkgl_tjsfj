<%@ page language="java" pageEncoding="UTF-8"%>
   <table id="intangible_add_plan3" class="easyui-datagrid"  style="height:auto;"
		data-options="
			singleSelect: true,
			rownumbers:true,
			url: '${base}/Storage/cgpmJsonPagination?id=${bean.fId_S}',
			method: 'post',
			onClickRow: onClickRowPlan3
		">
	<thead>
		<tr>
			<th data-options="field:'fListId',hidden:true"></th>
			<th data-options="field:'fId_S',hidden:true"></th>
			<th data-options="field:'acRId',hidden:true"></th>
			<th data-options="field:'fFixedTypeId',hidden:true"></th>
			<th data-options="field:'fAssType',hidden:true"></th>
			<th data-options="field:'fFixedTypeCode',hidden:true"></th>
			<th data-options="field:'fDepreciationStatusCode',hidden:true" style="width: 20%"></th>
			<th data-options="field:'fValueTypeCode',hidden:true" style="width: 20%"></th>
			<th data-options="field:'fAssCode',align:'center',editor:'text'" style="width: 20%">卡片编号</th>
			<th data-options="field:'fFixedType',align:'center'" style="width: 20%">资产分类</th>
			<th data-options="field:'fAssName',align:'center'" style="width: 15%">资产名称</th>
			<th data-options="field:'fcCode',align:'center'" style="width: 25%">合同编号</th>
			<th data-options="field:'fActionDate',align:'center'" style="width: 15%">取得日期</th>
			<th data-options="field:'fAdminOfficialShow',align:'center'" style="width: 15%">管理部门</th>
			<th data-options="field:'fFinancialCode',align:'center'" style="width: 25%">会计凭证号</th>
			<th data-options="field:'fFinancialDate',align:'center'" style="width: 25%">财务入账日期</th>
			<th data-options="field:'fAmortizeStatusShow',align:'center'" style="width:20%">摊销状态</th>
			<th data-options="field:'fValueTypeShow',align:'center'" style="width:20%">价值类型</th>
			<th data-options="field:'amount',align:'center'" style="width: 20%">价值（元）</th>
			<th data-options="field:'fAppropriationAmount',align:'center'" style="width: 25%">财政拨款(元)</th>
			<th data-options="field:'fUnappropriationAmount',align:'center'" style="width: 25%">非财政拨款(元)</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
//接待人员表格添加删除，保存方法
var editIndexPlan3 = undefined;
function endEditingPlan3() {
	if (editIndexPlan3 == undefined) {
		return true
	}
	if ($('#intangible_add_plan3').datagrid('validateRow', editIndexPlan3)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		/* var tr = $('#intangible_add_plan3').datagrid('getEditors', editIndexPlan3);
		var text6=tr[6].target.combobox('getText');
		if(text6!='--请选择--'){
			tr[6].target.combobox('setValues',text6);
		}
		var text7=tr[7].target.combobox('getText');
		if(text7!='--请选择--'){
			tr[7].target.combobox('setValues',text7);
		} */
		$('#intangible_add_plan3').datagrid('endEdit', editIndexPlan3);
		editIndexPlan3 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPlan3(index) {
	if (editIndexPlan3 != index) {
		if (endEditingPlan3()) {
			$('#intangible_add_plan3').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndexPlan3 = index;
		} else {
			$('#intangible_add_plan3').datagrid('selectRow', editIndexPlan3);
		}
	}
}
function appendPlan3() {
	if (endEditingPlan3()) {
		$('#intangible_add_plan3').datagrid('appendRow', {
		});
		editIndexPlan3 = $('#intangible_add_plan3').datagrid('getRows').length - 1;
		$('#intangible_add_plan3').datagrid('selectRow', editIndexPlan3).datagrid('beginEdit',editIndexPlan3);
	}
}
function removeitPlan3() {
	if (editIndexPlan3 == undefined) {
		return
	}
	$('#intangible_add_plan3').datagrid('cancelEdit', editIndexPlan3).datagrid('deleteRow',
			editIndexPlan3);
	editIndexPlan3 = undefined;
}
function acceptPlan3() {
	if (endEditingPlan3()) {
		$('#intangible_add_plan3').datagrid('acceptChanges');
	}
}
function getPlan3() {
	acceptPlan3();
	var rows = $('#intangible_add_plan3').datagrid('getRows');
	var entities = '';
	for (var i = 0; i < rows.length; i++) {
		entities = entities + JSON.stringify(rows[i]) + ',';
	}
	entities = '[' + entities.substring(0, entities.length - 1) + ']';
	return entities;
}

function selectTypeDetail(index){
	var win=creatSecondWin('选择-资产类型',900,580,'icon-search',"/assetType/assetTypeList?index="+index);
    win.window('open');
}

function reloadDepreciationCode(){
	var index=$('#intangible_add_plan3').datagrid('getRowIndex',$('#intangible_add_plan3').datagrid('getSelected'));
	var fDepreciationStatusCode = $('#intangible_add_plan3').datagrid('getEditor',{
		index:index,
		field:'fDepreciationStatusCode'
	});
	var fDepreciationStatusShow = $('#intangible_add_plan3').datagrid('getEditor',{
		index:index,
		field:'fDepreciationStatusShow'
	});
	$(fDepreciationStatusCode.target).textbox('setValue', fDepreciationStatusShow.target.combobox('getValues'));
}
function reloadValueCode(){
	var index=$('#intangible_add_plan3').datagrid('getRowIndex',$('#intangible_add_plan3').datagrid('getSelected'));
	var fValueTypeCode = $('#intangible_add_plan3').datagrid('getEditor',{
		index:index,
		field:'fValueTypeCode'
	});
	var fValueTypeShow = $('#intangible_add_plan3').datagrid('getEditor',{
		index:index,
		field:'fValueTypeShow'
	});
	$(fValueTypeCode.target).textbox('setValue', fValueTypeShow.target.combobox('getValues'));
}
</script>