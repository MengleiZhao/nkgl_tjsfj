<%@ page language="java" pageEncoding="UTF-8"%>
   <table id="fixed_add_plan2" class="easyui-datagrid"  style="height:auto;"
		data-options="
			singleSelect: true,
			rownumbers:true,
			url: '${base}/Storage/cgpmJsonPagination?id=${bean.fId_S}',
			method: 'post',
			onClickRow: onClickRowPlan2
		">
	<thead>
		<tr>
			<th data-options="field:'fListId',hidden:true"></th>
			<th data-options="field:'fId_S',hidden:true"></th>
			<th data-options="field:'acRId',hidden:true"></th>
			<th data-options="field:'fAssType',hidden:true"></th>
			<th data-options="field:'fFixedTypeId', editor:'textbox',hidden:true"></th>
			<th data-options="field:'fFixedTypeCode', editor:'textbox',hidden:true"></th>
			<th data-options="field:'fDepreciationStatusCode', editor:'textbox',hidden:true" style="width: 20%"></th>
			<th data-options="field:'fValueTypeCode', editor:'textbox',hidden:true" style="width: 20%"></th>
			<th data-options="field:'fAssCode',align:'center'" style="width: 20%">卡片编号</th>
			<th data-options="field:'fFixedType',align:'center'" style="width: 20%">资产分类</th>
			<th data-options="field:'fAssName',align:'center'" style="width: 15%">资产名称</th>
			<th data-options="field:'fcCode',align:'center'" style="width: 25%">合同编号</th>
			<th data-options="field:'fMMode',align:'center'" style="width: 15%">型号</th>
			<th data-options="field:'fActionDate',align:'center'" style="width: 15%">取得日期</th>
			<th data-options="field:'fFinancialCode',align:'center' ,editor:'text'" style="width: 25%">会计凭证号</th>
			<th data-options="field:'fFinancialDate',align:'center' ,editor:{type:'datebox',options:{onChange:setFinancialDateFixed,editable: false}},formatter:ChangeDateFormat3" style="width: 25%">财务入账日期</th>
			<th data-options="field:'fDepreciationStatusShow',align:'center',editor:{
							type:'combobox',
							options:{
								required:true,editable:false,
								valueField:'code',
								textField:'text',
								method:'post',
								onShowPanel:onShowPanelDepStsShow,
								onHidePanel:reloadDepreciationCode
							}}" style="width:20%">折旧状态</th>
			<th data-options="field:'fValueTypeShow',align:'center',editor:{
							type:'combobox',
							options:{
								required:true,editable:false,
								valueField:'code',
								textField:'text',
								method:'post',
								onShowPanel:onShowPanelValueTypeShow,
								onHidePanel:reloadValueCode
							}}" style="width:20%">价值类型</th>
			<th data-options="field:'amount',align:'center' ,editor:{type:'numberbox',options:{precision:2,editable: true,onChange:changeAmount}}" style="width: 20%">价值（元）</th>
			<th data-options="field:'fAppropriationAmount',align:'center' ,editor:{type:'numberbox',options:{precision:2,onChange:changeAppropriation}}" style="width: 25%">财政拨款(元)</th>
			<th data-options="field:'fUnappropriationAmount',align:'center' ,editor:{type:'numberbox',options:{precision:2,onChange:changeUnappropriation}}" style="width: 25%">非财政拨款(元)</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
//接待人员表格添加删除，保存方法
var editIndexPlan2 = undefined;
function endEditingPlan2() {
	if (editIndexPlan2 == undefined) {
		return true
	}
	if ($('#fixed_add_plan2').datagrid('validateRow', editIndexPlan2)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#fixed_add_plan2').datagrid('getEditors', editIndexPlan2);
		var text6=tr[6].target.combobox('getText');
		if(text6!='--请选择--'){
			tr[6].target.combobox('setValues',text6);
		}
		var text7=tr[7].target.combobox('getText');
		if(text7!='--请选择--'){
			tr[7].target.combobox('setValues',text7);
		}
		$('#fixed_add_plan2').datagrid('endEdit', editIndexPlan2);
		editIndexPlan2 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPlan2(index) {
	if (editIndexPlan2 != index) {
		if (endEditingPlan2()) {
			$('#fixed_add_plan2').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndexPlan2 = index;
		} else {
			$('#fixed_add_plan2').datagrid('selectRow', editIndexPlan2);
		}
	}
}
function appendPlan2() {
	if (endEditingPlan2()) {
		$('#fixed_add_plan2').datagrid('appendRow', {
		});
		editIndexPlan2 = $('#fixed_add_plan2').datagrid('getRows').length - 1;
		$('#fixed_add_plan2').datagrid('selectRow', editIndexPlan2).datagrid('beginEdit',editIndexPlan2);
	}
}
function removeitPlan2() {
	if (editIndexPlan2 == undefined) {
		return
	}
	$('#fixed_add_plan2').datagrid('cancelEdit', editIndexPlan2).datagrid('deleteRow',
			editIndexPlan2);
	editIndexPlan2 = undefined;
}
function acceptPlan2() {
	if (endEditingPlan2()) {
		$('#fixed_add_plan2').datagrid('acceptChanges');
	}
}
function getPlan2() {
	acceptPlan2();
	var rows = $('#fixed_add_plan2').datagrid('getRows');
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
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
	var fDepreciationStatusCode = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fDepreciationStatusCode'
	});
	var fDepreciationStatusShow = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fDepreciationStatusShow'
	});
	$(fDepreciationStatusCode.target).textbox('setValue', fDepreciationStatusShow.target.combobox('getValues'));
}
function onShowPanelDepStsShow(){
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
	var eds = $('#fixed_add_plan2').datagrid('getEditor', {index:index,field:'fDepreciationStatusShow'});
	eds.target.combobox('reload',base+'/lookup/lookupsJson?parentCode=ZCZJZT');
}
function onShowPanelValueTypeShow(){
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
	var eds = $('#fixed_add_plan2').datagrid('getEditor', {index:index,field:'fValueTypeShow'});
	eds.target.combobox('reload',base+'/lookup/lookupsJson?parentCode=ZCJZLX');//url:base+'/lookup/lookupsJson?parentCode=ZCJZLX',
}
function reloadValueCode(){
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
	var fValueTypeCode = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fValueTypeCode'
	});
	var fValueTypeShow = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fValueTypeShow'
	});
	$(fValueTypeCode.target).textbox('setValue', fValueTypeShow.target.combobox('getValues'));
}


function setFinancialDateFixed(newValue,oldValue){
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
    var editors = $('#fixed_add_plan2').datagrid('getEditors', index);  
    var rows = $('#fixed_add_plan2').datagrid('getRows');
    var day2 = editors[5]; 
    var ysday = Date.parse(rows[index].fActionDate);//验收时间
    var rzday = Date.parse(new Date(day2.target.val()));
    if(!isNaN(ysday)&&!isNaN(rzday)){
   		if(rzday<ysday){
       		alert("入账日期不得早于取得日期！");
       		editors[5].target.datebox('setValue', '');
       	}
    }
}
function changeAmount(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined ||newValue==''){
		return false;
	}
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
	var fUnappropriationAmount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fUnappropriationAmount'
	});
	var fAppropriationAmount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fAppropriationAmount'
	});
	var amounts = parseFloat(newValue);
	var fUnappropriationAmounts = parseFloat(fUnappropriationAmount.target.numberbox('getValue'));
	var fAppropriationAmounts = parseFloat(fAppropriationAmount.target.numberbox('getValue'));
	if(isNaN(fAppropriationAmounts)){
		return false;
	}
	if(isNaN(fUnappropriationAmounts)){
		return false;
	}
	if((fAppropriationAmounts+fUnappropriationAmounts)!=amounts){
		values = false;
		alert('资产价值等于财政拨款+非财政拨款,请重新填写！');
		$(fUnappropriationAmount.target).numberbox('setValue','');
		$(fAppropriationAmount.target).numberbox('setValue','');
		return false;
	}else{
		values = true;
	}
}
function changeAppropriation(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined ||newValue==''){
		return false;
	}
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
	var amount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'amount'
	});
	var fAppropriationAmount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fAppropriationAmount'
	});
	var fUnappropriationAmount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fUnappropriationAmount'
	});
	var fAppropriationAmounts = parseFloat(newValue);
	var fUnappropriationAmounts = parseFloat(fUnappropriationAmount.target.numberbox('getValue'));
	var amounts = parseFloat(amount.target.numberbox('getValue'));
	if(isNaN(amounts)){
		return false;
	}
	if(isNaN(fUnappropriationAmounts)){
		return false;
	}
	if((fAppropriationAmounts+fUnappropriationAmounts)!=amounts){
		values = false;
		alert('资产价值等于财政拨款+非财政拨款,请重新填写！');
		$(fAppropriationAmount.target).numberbox('setValue','');
		return false;
	}else{
		values = true;
	}
}
function changeUnappropriation(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined ||newValue==''){
		return false;
	}
	var index=$('#fixed_add_plan2').datagrid('getRowIndex',$('#fixed_add_plan2').datagrid('getSelected'));
	var amount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'amount'
	});
	var fUnappropriationAmount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fUnappropriationAmount'
	});
	var fAppropriationAmount = $('#fixed_add_plan2').datagrid('getEditor',{
		index:index,
		field:'fAppropriationAmount'
	});
	var fAppropriationAmounts = parseFloat(fAppropriationAmount.target.numberbox('getValue'));
	var fUnappropriationAmounts = parseFloat(newValue);
	var amounts = parseFloat(amount.target.numberbox('getValue'));
	if(isNaN(fAppropriationAmounts)){
		return false;
	}
	if(isNaN(amounts)){
		return false;
	}
	if((fAppropriationAmounts+fUnappropriationAmounts)!=amounts){
		values = false;
		alert('资产价值等于财政拨款+非财政拨款,请重新填写！');
		$(fUnappropriationAmount.target).numberbox('setValue','');
		return false;
	}else{
		values = true;
	}
}
</script>