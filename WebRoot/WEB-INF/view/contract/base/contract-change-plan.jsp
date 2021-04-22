<%@ page language="java" pageEncoding="UTF-8"%>
<table id="change-plan-dg" class="easyui-datagrid" style="width:650px;height:auto;"
	data-options="
		iconCls: 'icon-edit',
		singleSelect: true,
		toolbar: '#upttb',
		method: 'post',
		url:'${base}/Change/uptPlanJson?fId_U=${Upt.fId_U}',
		onClickCell: onClickCellUptPlan,
		scrollbarSize:0,
		rownumbers:true,
	">
	<thead>
		<tr>
			<th data-options="field:'fReceProperty',formatter:PayStauts,align:'center',
					editor:{
						editable:true,
						type:'combobox',
						options:{
							required:true,
							valueField:'code',
							textField:'text',
							validType:'selectValid',
							method:'post',
							url:'${base}/Expiration/lookupsJson?parentCode=FKXZ',
						}
					}"style="width: 15%">付款性质</th>
			<th data-options="field:'fRecStage',editor:{type:'textbox',options:{required:true}},validType:'length[1,20]',align:'center'" style="width: 12%">付款阶段</th>
			<th data-options="field:'fReceCondition',editor:{type:'textbox',options:{required:true}},validType:'length[1,50]',align:'center'"style="width: 13%">付款条件</th>
			<th data-options="field:'fRecePlanTime',align:'center',editor:{type:'datebox',options:{required:true,precision:1}}"style="width: 15%">预计付款时间</th>
			<th data-options="field:'fRecePlanAmount',align:'right',editor:{type:'numberbox',options:{required:true,precision:2,onChange:setFsumMoney}}"style="width: 16%">预计付款金额(元)</th>
			<th data-options="field:'fReceTime',align:'center'"style="width: 15%">实际付款时间</th>
			<th data-options="field:'fReceAmount',align:'right'"style="width: 16%">实际付款金额(元)</th>
		</tr>
	</thead>
</table>
<div id="upttb" style="height:30px">
	<span style="margin-left:8px;color: red;">总额:</span>&nbsp;&nbsp;<input class="easyui-numberbox" readonly="readonly" id="totalUptAmountshow" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"  style="margin-left:20px;color: red;width:200px;">
	<input hidden="hidden" id="totalUptAmount"/>
	<a href="javascript:void(0)" onclick="removeituptPlan()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="appenduptPlan()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
</div>
<script type="text/javascript">

function PayStauts(val, row) {
	if (val == "FKXZ-02") {
		return "阶段款";
	} else if (val == "FKXZ-01") {
		return "首款";
	}else if (val == "FKXZ-03") {
		return "验收款";
	}else if (val == "FKXZ-04") {
		return "质保款";
	}
}
//完成后计算总额并设置
var loadplan = 0;
$('#change-plan-dg').datagrid({
	onLoadSuccess : function(data){
		//console.log(data.rows);
		var rows = data.rows;
		var amount = 0.00;
		loadplan += 1;
		for(var i = 0 ;i<rows.length; i++){
			amount = parseFloat(amount) + parseFloat(rows[i].fRecePlanAmount);
		}
		$('#totalUptAmount').val(amount);
		$('#totalUptAmountshow').numberbox('setValue',amount);
	}
})
var editIndex = undefined;
function endEditingPlan(){
	if (editIndex == undefined){return true}
	if ($('#change-plan-dg').datagrid('validateRow', editIndex)){
		$('#change-plan-dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCellUptPlan(index, field){
	if (editIndex != index){
		if (endEditingPlan()){
			$('#change-plan-dg').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			var ed = $('#change-plan-dg').datagrid('getEditor', {index:index,field:field});
			if (ed){
				($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
			}
			editIndex = index;
		} else {
			setTimeout(function(){
				$('#change-plan-dg').datagrid('selectRow', editIndex);
			},0);
		}
	}
}
function appenduptPlan(){
	if (endEditingPlan()){
		$('#change-plan-dg').datagrid('appendRow',{});
		editIndex = $('#change-plan-dg').datagrid('getRows').length-1;
		$('#change-plan-dg').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeituptPlan(){
	if (editIndex == undefined){return}
	$('#change-plan-dg').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
	setFsumMoney(0,0);
}

//计算总额
function setFsumMoney(newValue,oldValue) {
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var totalFsumMoney = 0;
	var fsumMoney = 0;
	var index=$('#change-plan-dg').datagrid('getRowIndex',$('#change-plan-dg').datagrid('getSelected'));
	var rows = $('#change-plan-dg').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumMoney=parseFloat(newValue);
		}else{
			totalFsumMoney+=addNum(rows,i);
		}  
	 
	}
	totalFsumMoney=(parseFloat(totalFsumMoney)+parseFloat(fsumMoney));
	totalFsumMoney=parseFloat(totalFsumMoney);
	$('#totalUptAmount').val(totalFsumMoney);
	$('#totalUptAmountshow').numberbox('setValue',totalFsumMoney);
}
//未编辑或者已经编辑完毕的行
function addNum(rows,index){
	var amount=rows[index]['col5'];
	return parseFloat(amount); 
}
function testPlan(){
	var entities= getPlan()
	 //alert(entities)
}
function getUptPlan(){
	$('#change-plan-dg').datagrid('acceptChanges');
	var rows = $('#change-plan-dg').datagrid('getRows');
	var entities= '';
	for(i = 0;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	 entities = '[' + entities.substring(0,entities.length -1) + ']';
	 return entities;
}
</script>