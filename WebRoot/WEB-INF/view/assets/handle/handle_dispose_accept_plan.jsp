<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
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
			<th data-options="colspan:4,align:'center'">价值</th>
			<th data-options="rowspan:2,field:'fRemarkR',align:'center',editor:{type:'textbox',options:{editable:true}}" style="width: 15%">备注</th>
		</tr>
			<th data-options="field:'fOldAmount',align:'center'" style="width: 15%">账面原值(元)</th>
			<th data-options="field:'fResidualValue',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 15%">账面净值（元）</th>
			<th data-options="field:'fDepreciationAmount',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 15%">累计折旧（元）</th>
			<th data-options="field:'fAssAmount',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 15%">评估价值（元）</th>
	</thead>
</table>
<c:if test="${openType=='add'||openType=='edit'}">
<div id="tb" style="height:30px;">
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" id="addFixedButton" onclick="appendPlanConfig('${bean.fAssType}','fixed_add_plan1')" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjzc1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
</div>
</c:if>
<script type="text/javascript">
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
	function getPlan1(){
		$('#fixed_add_plan1').datagrid('acceptChanges');
		var rows = $('#fixed_add_plan1').datagrid('getRows');
		var entities= '';
		for(var i = 0;i < rows.length;i++){
		 entities = entities  + JSON.stringify(rows[i]) + ',';  
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
</script>