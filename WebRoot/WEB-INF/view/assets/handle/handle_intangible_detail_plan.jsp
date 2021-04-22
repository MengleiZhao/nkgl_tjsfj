<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<style>
.datagrid-body {
    height: auto;
}
</style>
<table id="fixed_add_plan" class="easyui-datagrid"  style="height:auto;"
	data-options="
		iconCls: 'icon-edit',
		singleSelect: true,
		rownumbers:true,
		toolbar: '#tb',
		url: '${base}/Handle/assetRegJson?fId=${bean.fId }&fAssType=${bean.fAssType}',
		method: 'post',
">
	<thead>
		<tr>
			<th data-options="rowspan:2,field:'fARId',hidden:true"></th>
			<th data-options="rowspan:2,field:'fId',hidden:true"></th>
			<th data-options="rowspan:2,field:'fAssCode',align:'center'" style="width:20%">资产编号</th>
			<th data-options="rowspan:2,field:'fAssName',align:'center'" style="width:20%">资产名称</th>
			<th data-options="rowspan:2,field:'fFixedType',align:'center'" style="width:20%">资产类别明细</th>
			<th data-options="rowspan:2,field:'fFormShow',align:'center'" style="width:20%">资产来源</th>
			<th data-options="rowspan:2,field:'fMeasUnit',align:'center'" style="width:15%">计量单位</th>
			<th data-options="rowspan:2,field:'fHandleNum',align:'center'" style="width:15%">数量（股份%）</th>
			<th data-options="rowspan:2,field:'fActionDate',align:'center'" style="width:20%">取得日期</th>
			<th data-options="rowspan:2,field:'fUsedStautsShow',align:'center'" style="width:15%">使用状态</th>
			<th data-options="colspan:4,align:'center'">价值</th>
			<th data-options="rowspan:2,field:'fRemarkR',align:'center',editor:{type:'textbox',options:{editable:true}}" style="width: 15%">备注</th>
			<th data-options="rowspan:2,field:'name',align:'center',formatter:returncontract" style="width: 15%">操作</th>
		</tr>
			<th data-options="field:'fOldAmount',align:'center'" style="width: 15%">账面原值(元)</th>
			<th data-options="field:'fResidualValue',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 15%">账面净值（元）</th>
			<th data-options="field:'fDepreciationAmount',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 15%">累计摊销（元）</th>
			<th data-options="field:'fAssAmount',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 15%">评估价值（元）</th>
	</thead>
</table>
<c:if test="${openType=='add'||openType=='edit'}">
<div id="tb" style="height:30px;">
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" id="addFixedButton" onclick="appendPlanConfigs('${bean.fAssType}','fixed_add_plan')" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjzc1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
</div>
</c:if>
<script type="text/javascript">
function returncontract(val,row){
	return '<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
	+'<a href="#" onclick="contractdetail(' + row.contractId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/ckht1.png">' + '</a>   '
	+'</td><td style="width: 25px">'
	+ '<a href="#" onclick="enforcingdetail(' + row.contractId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/ckbx1.png">' + '</a>'
	+'</td></tr></table>';
}

function contractdetail(contractId){
	if(contractId==''||contractId==null){
		alert('尚未找到已签订合同');
	}else {
		var win=creatSecondWin('合同',1115,600,'icon-search','/Handle/contractdetail?id='+contractId+'&contractUpdateStatus=contractUpdateStatus');
		win.window('open');
	}
}
function enforcingdetail(contractId){
	if(contractId==''||contractId==null){
		alert('尚未找到已付款的合同报销');
	}else {
		var win=creatSecondWin('合同报销',1115,600,'icon-search','/Handle/enforcinglist?id='+contractId+'&contractUpdateStatus=contractUpdateStatus');
		win.window('open');
	}
}
	function PayStauts(val) {
		$('#fixed_add_plan').datagrid('updateRow',{
			index: editIndexPlan,
			row: {
				fAssName_R: val.text,
				fMeasUnit_R: val.SftjCode,
				fAssCode_R: val.code
			}
		}); 
		if(val.text=='新增'){
			var win=creatFirstWin('新增资产品目',970,580,'icon-search','/AssetBasicInfo/lowAddOther');
			win.window('open');
		}
}
	var editIndexPlan = undefined;
	function endEditingPlan(){
		if (editIndexPlan == undefined){return true}
		if ($('#fixed_add_plan').datagrid('validateRow', editIndexPlan)){
			$('#fixed_add_plan').datagrid('endEdit', editIndexPlan);
			editIndexPlan = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickCellPlans(index, field){
		if (editIndexPlan != index){
			if (endEditingPlan()){
				$('#fixed_add_plan').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				var ed = $('#fixed_add_plan').datagrid('getEditor', {index:index,field:field});
				if (ed){
					($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				}
				editIndexPlan = index;
			} else {
				setTimeout(function(){
					$('#fixed_add_plan').datagrid('selectRow', editIndexPlan);
				},0);
			}
		}
	}
	function appendPlan(){
		if (endEditingPlan()){
			$('#fixed_add_plan').datagrid('appendRow',{});
			editIndexPlan = $('#fixed_add_plan').datagrid('getRows').length-1;
			$('#fixed_add_plan').datagrid('selectRow', editIndexPlan)
					.datagrid('beginEdit', editIndexPlan);
		document.getElementById("fixed_add_plan").scrollIntoView();
		}
	}
	function removePlan(){
		if (editIndexPlan == undefined){return };
		$('#fixed_add_plan').datagrid('cancelEdit', editIndexPlan)
				.datagrid('deleteRow', editIndexPlan);
		editIndexPlan = undefined;
	}
	function acceptPlan(){
		if(endEditingPlan()){
			$('#fixed_add_plan').datagrid('acceptChanges');
		}
	}
	function getPlan(){
		$('#fixed_add_plan').datagrid('acceptChanges');
		var rows = $('#fixed_add_plan').datagrid('getRows');
		var entities= '';
		for(var i = 0;i < rows.length;i++){
		 entities = entities  + JSON.stringify(rows[i]) + ',';  
		}
		 entities = '[' + entities.substring(0,entities.length -1) + ']';
		 return entities;
	}
	
	function appendPlanConfigs(type,tabId) {
		 acceptPlan();
		 var win = creatFirstWin('添加资产', 1115, 600, 'icon-search', '/Handle/chooseFixedInfo?type='+type+'&tabId='+tabId);
		 win.window('open');
		}
	
	function queryHandleListInt(){
		editIndexPlan == undefined;
		var rows = $('#fixed_add_plan').datagrid('getRows');
		var fOldAmount= 0.00;
		for(var i = 0;i < rows.length;i++){
			fOldAmount = parseFloat(fOldAmount)+parseFloat(rows[i].fOldAmount);
		}
		$("#fOldAmount").numberbox('setValue',fOldAmount.toFixed(2));
	}
</script>