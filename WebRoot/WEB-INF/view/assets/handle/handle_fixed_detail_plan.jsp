<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<style>
.datagrid-body {
    height: auto;
}
</style>
<table id="fixed_add_plan1" class="easyui-datagrid"  style="height:auto;"
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
			<th data-options="rowspan:2,field:'fUsedStauts',hidden:true"></th>
			<th data-options="rowspan:2,field:'fForm',hidden:true"></th>
			<th data-options="rowspan:2,field:'fAssCode',align:'center'" style="width: 20%">资产编号</th>
			<th data-options="rowspan:2,field:'fAssName',align:'center'" style="width: 20%">资产名称</th>
			<th data-options="rowspan:2,field:'fFixedType',align:'center'" style="width: 20%">资产类别明细</th>
			<th data-options="rowspan:2,field:'fFormShow',align:'center'" style="width: 20%">资产来源</th>
			<th data-options="rowspan:2,field:'fAssModel',align:'center'" style="width: 25%">规格型号/房产证号</th>
			<th data-options="rowspan:2,field:'fMeasUnit',align:'center'" style="width: 15%">计量单位</th>
			<th data-options="rowspan:2,field:'fHandleNum',align:'center'" style="width: 15%">数量（股份%）</th>
			<th data-options="rowspan:2,field:'fActionDate',align:'center'" style="width: 20%">取得日期</th>
			<th data-options="rowspan:2,field:'fUsedStautsShow',align:'center'" style="width: 15%">使用状态</th>
			<th data-options="colspan:4">价值</th>
			<th data-options="rowspan:2,field:'fRemarkR',align:'center',editor:{type:'textbox',options:{editable:true}}" style="width: 15%">备注</th>
			<th data-options="rowspan:2,field:'name',align:'center',formatter:returncontract" style="width: 15%">操作</th>
		</tr>
		<tr>
			<th data-options="field:'fOldAmount',align:'center'" style="width: 15%">账面原值(元)</th>
			<th data-options="field:'fResidualValue',align:'center',editor:{type:'numberbox',options:{precision:2,onChange:residualValueOnChanges}}" style="width: 15%">账面净值（元）</th>
			<th data-options="field:'fDepreciationAmount',align:'center',editor:{type:'numberbox',options:{precision:2,onChange:depreciationAmountOnChanges}}" style="width: 15%">累计折旧（元）</th>
			<th data-options="field:'fAssAmount',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 15%">评估价值（元）</th>
		</tr>
	</thead>
</table>
<c:if test="${openType=='add'||openType=='edit'}">
<div id="tb" style="height:30px;">
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" id="addFixedButton" onclick="appendPlanConfig('${bean.fAssType}','fixed_add_plan1')" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjzc1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
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
	var win=creatSecondWin('合同',1115,600,'icon-search','/Handle/contractdetail?id='+contractId+'&contractUpdateStatus=contractUpdateStatus');
	win.window('open');
}
function enforcingdetail(contractId){
	var win=creatSecondWin('报销',1115,600,'icon-search','/Handle/enforcinglist?id='+contractId+'&contractUpdateStatus=contractUpdateStatus');
	win.window('open');
}
	function PayStauts(val) {
		$('#fixed_add_plan1').datagrid('updateRow',{
			index: editIndexPlan1,
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
	var editIndexPlan1 = undefined;
	function endEditingPlan1(){
		if (editIndexPlan1 == undefined){return true}
		if ($('#fixed_add_plan1').datagrid('validateRow', editIndexPlan1)){
			$('#fixed_add_plan1').datagrid('endEdit', editIndexPlan1);
			editIndexPlan1 = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickCellPlan1(index, field){
		if (editIndexPlan1 != index){
			if (endEditingPlan1()){
				$('#fixed_add_plan1').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				var ed = $('#fixed_add_plan1').datagrid('getEditor', {index:index,field:field});
				if (ed){
					($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				}
				editIndexPlan1 = index;
			} else {
				setTimeout(function(){
					$('#fixed_add_plan1').datagrid('selectRow', editIndexPlan1);
				},0);
			}
		}
	}
	function appendPlan1(){
		if (endEditingPlan1()){
			$('#fixed_add_plan1').datagrid('appendRow',{});
			editIndexPlan1 = $('#fixed_add_plan1').datagrid('getRows').length-1;
			$('#fixed_add_plan1').datagrid('selectRow', editIndexPlan1)
					.datagrid('beginEdit', editIndexPlan1);
		document.getElementById("fixed_add_plan1").scrollIntoView();
		}
	}
	function removePlan1(){
		if (editIndexPlan1 == undefined){return };
		$('#fixed_add_plan1').datagrid('cancelEdit', editIndexPlan1)
				.datagrid('deleteRow', editIndexPlan1);
		editIndexPlan1 = undefined;
	}
	function acceptPlan1(){
		if(endEditingPlan1()){
			$('#fixed_add_plan1').datagrid('acceptChanges');
		}
	}
	function fixedPlan1(){
		acceptPlan1();
		var rows = $('#fixed_add_plan1').datagrid('getRows');
		var entities= '';
		for(var i = 0;i < rows.length;i++){
		 entities = entities  + JSON.stringify(rows[i]) + ','; 
		 if(rows[i].fResidualValue==''||rows[i].fDepreciationAmount==''||rows[i].fAssAmount==''||rows[i].fResidualValue==null||rows[i].fDepreciationAmount==null||rows[i].fAssAmount==null||rows[i].fResidualValue==undefined||rows[i].fDepreciationAmount==undefined||rows[i].fAssAmount==undefined ){
			 return false;
		 }
		}
		 entities = '[' + entities.substring(0,entities.length -1) + ']';
		 return entities;
	}
	

	//时间格式化
	function ChangeDateFormat12(val) {
		var t, y, m, d, h, i, s;
		if (val == null || val == "" || isNaN(val)) {
			return "";
		}
		t = new Date(val);
		y = t.getFullYear();
		m = t.getMonth() + 1;
		d = t.getDate();
		h = t.getHours();
		i = t.getMinutes();
		s = t.getSeconds();
		// 可根据需要在这里定义时间格式  
		return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
	}
	
	function appendPlanConfig(type,tabId) {
		 acceptPlan1();
		 var win = creatFirstWin('添加资产', 1115, 600, 'icon-search', '/Handle/chooseFixedInfo?type='+type+'&tabId='+tabId);
		 win.window('open');
		}
	
	function queryHandleList(){
		editIndexPlan1 == undefined;
		var rows = $('#fixed_add_plan1').datagrid('getRows');
		var fOldAmount= 0.00;
		for(var i = 0;i < rows.length;i++){
			fOldAmount = parseFloat(fOldAmount)+parseFloat(rows[i].fOldAmount);
		}
		$("#fOldAmount").numberbox('setValue',fOldAmount.toFixed(2));
		
	}
	
	function residualValueOnChanges(newVal,oldVal){
		if(newVal==undefined || oldVal==undefined|| newVal==''){
			return false;
		}
		var row = $('#fixed_add_plan1').datagrid('getSelected');
		var index=$('#fixed_add_plan1').datagrid('getRowIndex',row);
		var oldAmount =parseFloat(row.fOldAmount);
		if(isNaN(oldAmount)||oldAmount==''){
			oldAmount=0;
		}
		var residualValue = $('#fixed_add_plan1').datagrid('getEditor',{
			index:index,
			field:'fResidualValue'
		});
		var depreciationAmount = $('#fixed_add_plan1').datagrid('getEditor',{
			index:index,
			field:'fDepreciationAmount'
		});
		var residualValues =parseFloat(newVal);
		if(isNaN(residualValues)||residualValues==''){
			residualValues=0;
		}
		var depreciationAmounts =parseFloat(depreciationAmount.target.numberbox('getValue'));
		if(isNaN(depreciationAmounts)||depreciationAmounts==''){
			depreciationAmounts=0;
		}
		if((parseFloat(oldAmount)-parseFloat(depreciationAmounts))<parseFloat(residualValues)){
			alert('账面原值数要等于账面净值+累计摊销!');
			$(residualValue.target).numberbox('setValue', '');
			$(depreciationAmount.target).numberbox('setValue', '');
			return false;
		}
		$(depreciationAmount.target).numberbox('setValue', parseFloat(oldAmount)-parseFloat(residualValues));
	}
	function depreciationAmountOnChanges(newVal,oldVal){
		if(newVal==undefined || oldVal==undefined|| newVal==''){
			return false;
		}
		var row = $('#fixed_add_plan1').datagrid('getSelected');
		var index=$('#fixed_add_plan1').datagrid('getRowIndex',row);
		var oldAmount =parseFloat(row.fOldAmount);
		if(isNaN(oldAmount)||oldAmount==''){
			oldAmount=0;
		}
		var residualValue = $('#fixed_add_plan1').datagrid('getEditor',{
			index:index,
			field:'fResidualValue'
		});
		var depreciationAmount = $('#fixed_add_plan1').datagrid('getEditor',{
			index:index,
			field:'fDepreciationAmount'
		});
		var residualValues =parseFloat(residualValue.target.numberbox('getValue'));
		if(isNaN(residualValues)||residualValues==''){
			residualValues=0;
		}
		var depreciationAmounts =parseFloat(newVal);
		if(isNaN(depreciationAmounts)||depreciationAmounts==''){
			depreciationAmounts=0;
		}
		if((parseFloat(oldAmount)-parseFloat(residualValues))<parseFloat(depreciationAmounts)){
			alert('账面原值数要等于账面净值+累计摊销!');
			$(depreciationAmount.target).numberbox('setValue', '');
			$(residualValue.target).numberbox('setValue', '');
			return false;
		}
		$(residualValue.target).numberbox('setValue', parseFloat(oldAmount)-parseFloat(depreciationAmounts));
	}
</script>