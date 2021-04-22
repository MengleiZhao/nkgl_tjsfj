<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   <table id="Rece_config_plan" class="easyui-datagrid"  style="height:auto;"
		data-options="
			singleSelect: true,
			toolbar: '#tb',
			url: '${base}/Rece/lowJsonPagination?fAssReceCode=${bean.fAssReceCode }',
			method: 'post',
			onClickCell: onClickCellPlanConfig
		">
	<thead>
		<tr>
			<%@include file="rece_config_plan.jsp" %>
		</tr>
	</thead>
</table>
<c:if test="${openType=='add'||openType=='edit'}">
	<div id="tb" style="height:30px;margin-top: 5px;margin-right: 10px;">
		<a href="javascript:void(0)" onclick="removePlanConfig()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendPlanPlanConfig()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
</c:if>

<script type="text/javascript">

	/* function PayStauts(val) {
		$('#Rece_config_plan').datagrid('updateRow',{
			index: editIndex,
			row: {
				fAssName_RL: val.text,
				fMeasUnit_RL: val.SftjCode,
				fAssCode_RL: val.code
			}
		}); 
		if(val.text=='新增'){
			var win=creatFirstWin('新增资产品目',840,450,'icon-search','/AssetBasicInfo/lowAddOther');
			win.window('open');
		}
} */
	var editIndex = undefined;
	function endEditingPlanConfig(){
		if (editIndex == undefined){return true}
		if ($('#Rece_config_plan').datagrid('validateRow', editIndex)){
			$('#Rece_config_plan').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickCellPlanConfig(index, field){
		if (editIndex != index){
			if (endEditingPlanConfig()){
				$('#Rece_config_plan').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				var ed = $('#Rece_config_plan').datagrid('getEditor', {index:index,field:field});
				if (ed){
					($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				}
				editIndex = index;
			} else {
				setTimeout(function(){
					$('#Rece_config_plan').datagrid('selectRow', editIndex);
				},0);
			}
		}
	}
	function appendPlanPlanConfig(){
		if (endEditingPlanConfig()){
			$('#Rece_config_plan').datagrid('appendRow',{});
			editIndex = $('#Rece_config_plan').datagrid('getRows').length-1;
			$('#Rece_config_plan').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex);
		}
		document.getElementById("Rece_config_plan").scrollIntoView();
	}
	function removePlanConfig(){
		if (editIndex == undefined){return };
		$('#Rece_config_plan').datagrid('cancelEdit', editIndex)
				.datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}
	function getConfigPlan(){
		$('#Rece_config_plan').datagrid('acceptChanges');
		var rows = $('#Rece_config_plan').datagrid('getRows');
		var entities= '';
		for(i = 0;i < rows.length;i++){
		 entities = entities  + JSON.stringify(rows[i]) + ',';  
		}
		 entities = '[' + entities.substring(0,entities.length -1) + ']';
		 return entities;
	}
</script>