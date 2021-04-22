<%@ page language="java" pageEncoding="UTF-8"%>
   <table id="fixed_add_plan_intangible" class="easyui-datagrid"  style="height:auto;"
		data-options="
			singleSelect: true,
			rownumbers:true,
			toolbar: '#tb',
			url: '${base}/Storage/cgpmJsonPagination?id=${bean.fId_S}',
			method: 'post',
			onClickRow: onClickRowPlanIntangible
		">
	<thead>
		<tr>
			<th data-options="field:'fListId',hidden:true"></th>
			<th data-options="field:'fId_S',hidden:true"></th>
			<th data-options="field:'acRId',hidden:true"></th>
			<th data-options="field:'fcId',hidden:true"></th>
			<th data-options="field:'fId_U',hidden:true"></th>
			<th data-options="field:'fAssType',hidden:true"></th>
			<th data-options="field:'fAdminOfficial', editor:'textbox',hidden:true"></th>
			<th data-options="field:'fFixedTypeId', editor:'textbox',hidden:true"></th>
			<th data-options="field:'fFixedTypeCode', editor:'textbox',hidden:true"></th>
			<th data-options="field:'fAssCode',align:'center'" style="width: 20%">卡片编号</th>
			<th data-options="field:'fFixedType',align:'center',editor:{type:'textbox',options:{required:true,editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
						var index=$('#fixed_add_plan_intangible').datagrid('getRowIndex',$('#fixed_add_plan_intangible').datagrid('getSelected'));
					     selectTypeDetail(index,'fixed_add_plan_intangible');
					     }}]}}" style="width: 20%">资产分类</th>
			<th data-options="field:'fAssName',align:'center', editor:{type:'textbox',options:{required:true}}" style="width: 15%">资产名称</th>
			<th data-options="field:'fcCode',align:'center'" style="width: 25%">合同编号</th>
			<th data-options="field:'fActionDate',align:'center',editor:{type:'datebox',options:{required:true,editable: false}},formatter:ChangeDateFormat" style="width: 15%">取得日期</th>
			<th data-options="field:'fAdminOfficialShow',align:'center',editor:{
							type:'combobox',
							options:{
								required:true,
								editable:false,
								valueField:'code',
								textField:'text',
								method:'post',
								onShowPanel:onShowPanelAdminOfficial,
								onHidePanel:reloadAdminOfficial
							}}" style="width: 15%">管理部门</th>
			
			<th data-options="field:'fFinancialCode',align:'center'" style="width: 15%">会计凭证号</th>
			<th data-options="field:'fFinancialDate',align:'center'" style="width: 20%">财务入账日期</th>
			<th data-options="field:'fAmortizeStatus',align:'center'" style="width: 10%">摊销状态</th>
			<th data-options="field:'fValueType',align:'center'" style="width: 10%">价值类型</th>
			<th data-options="field:'amount',align:'center'" style="width: 20%">价值（元）</th>
			<th data-options="field:'fAppropriationAmount',align:'center'" style="width: 30%">财政拨款(元)</th>
			<th data-options="field:'fUnappropriationAmount',align:'center'" style="width: 30%">非财政拨款(元)</th>
			
		</tr>
	</thead>
</table>
<div id="tb" style="height:30px;">
	<a href="javascript:void(0)" id="removeitPlanIntangibleId" hidden="hidden" onclick="removeitPlanIntangible()" style="float: right;">
		<img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
	</a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" id="appendPlanIntangibleId" hidden="hidden" onclick="appendPlanIntangible()" style="float: right;">
		<img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
	</a>
	<a style="float: right;">&nbsp;&nbsp;</a>
</div>
<script type="text/javascript">
//接待人员表格添加删除，保存方法
var editIndexPlanIntangible = undefined;
function endEditingPlanIntangible() {
	
	if (editIndexPlanIntangible == undefined) {
		return true
	}
	if ($('#fixed_add_plan_intangible').datagrid('validateRow', editIndexPlanIntangible)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		/* var tr = $('#fixed_add_plan_intangible').datagrid('getEditors', editIndexPlanIntangible);
		var text5=tr[5].target.combobox('getText');
		if(text5!='--请选择--'){
			tr[5].target.combobox('setValues',text5);
		}
		var text7=tr[7].target.combobox('getText');
		if(text7!='--请选择--'){
			tr[7].target.combobox('setValues',text7);
		} */
		$('#fixed_add_plan_intangible').datagrid('endEdit', editIndexPlanIntangible);
		editIndexPlanIntangible = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPlanIntangible(index) {
	
	if (editIndexPlanIntangible != index) {
		if (endEditingPlanIntangible()) {
			$('#fixed_add_plan_intangible').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndexPlanIntangible = index;
		} else {
			$('#fixed_add_plan_intangible').datagrid('selectRow', editIndexPlanIntangible);
		}
	}
}
function appendPlanIntangible() {
	if (endEditingPlanIntangible()) {
		$('#fixed_add_plan_intangible').datagrid('appendRow', {
		});
		editIndexPlanIntangible = $('#fixed_add_plan_intangible').datagrid('getRows').length - 1;
		$('#fixed_add_plan_intangible').datagrid('selectRow', editIndexPlanIntangible).datagrid('beginEdit',editIndexPlanIntangible);
	}
}
function removeitPlanIntangible() {
	if (editIndexPlanIntangible == undefined) {
		return
	}
	$('#fixed_add_plan_intangible').datagrid('cancelEdit', editIndexPlanIntangible).datagrid('deleteRow',
			editIndexPlanIntangible);
	editIndexPlanIntangible = undefined;
}
function acceptPlanIntangible() {
	if (endEditingPlanIntangible()) {
		$('#fixed_add_plan_intangible').datagrid('acceptChanges');
	}
}
function getPlanIntangible() {
	acceptPlanIntangible();
	var rows = $('#fixed_add_plan_intangible').datagrid('getRows');
	var entities = '';
	for (var i = 0; i < rows.length; i++) {
		entities = entities + JSON.stringify(rows[i]) + ',';
	}
	entities = '[' + entities.substring(0, entities.length - 1) + ']';
	return entities;
}
	
function onSelectCode(id,code){
	acceptPlanIntangible();
	$("#S_facpId").val(id);
	var rows = $('#fixed_add_plan_intangible').datagrid('getRows');
	for (var i = rows.length-1; i >= 0; i--) {
		$('#fixed_add_plan_intangible').datagrid('deleteRow',i);
	}
	editIndexPlanIntangible = undefined;
	$.ajax({
		type:'post',
		async:false,
		dataType:'json',
		url:base+'/cgreceive/acceptContractRegisterList?id='+id+'&code='+code,
		success:function (data){
			for (var i = 0; i < data.length; i++) {
				for (var j = 0; j < data[i].fNowCheckedNum; j++) {
					var updateTime=myformatter(new Date(data[i].updateTime));
					$('#fixed_add_plan_intangible').datagrid('appendRow',{
						acRId: data[i].acRId,
						fAssName: data[i].fmName,
						fMMode: data[i].fmSpecif,
						fActionDate: updateTime,
						fcId:data[i].fcId,
						fId_U:data[i].fId_U,
						fcCode:data[i].fcCode,
					});
				}
			}
			acceptPlanIntangible();
		}
	});
	
}

function selectTypeDetail(index,tabId){
	var fAssClass =  $('#storage_intangible_fAssClass').combobox('getValue');
	var win=creatSecondWin('选择-资产类型',900,580,'icon-search',"/assetType/assetTypeList?index="+index+"&tabId="+tabId+"&type=ZCLX-03&fAssClass="+fAssClass);
    win.window('open');
}


function onShowPanelAdminOfficial(){
	var index=$('#fixed_add_plan_intangible').datagrid('getRowIndex',$('#fixed_add_plan_intangible').datagrid('getSelected'));
	var eds = $('#fixed_add_plan_intangible').datagrid('getEditor', {index:index,field:'fAdminOfficialShow'});
	eds.target.combobox('reload',base+'/apply/chooseDepart');//url:base+'/lookup/lookupsJson?parentCode=ZCJZLX',
}
function reloadAdminOfficial(){
	var index=$('#fixed_add_plan_intangible').datagrid('getRowIndex',$('#fixed_add_plan_intangible').datagrid('getSelected'));
	var fAdminOfficial = $('#fixed_add_plan_intangible').datagrid('getEditor',{
		index:index,
		field:'fAdminOfficial'
	});
	var fAdminOfficialShow = $('#fixed_add_plan_intangible').datagrid('getEditor',{
		index:index,
		field:'fAdminOfficialShow'
	});
	$(fAdminOfficial.target).textbox('setValue', fAdminOfficialShow.target.combobox('getValues'));
}
</script>