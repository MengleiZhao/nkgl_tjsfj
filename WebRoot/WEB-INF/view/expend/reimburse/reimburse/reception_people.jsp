<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-table" style="margin-bottom:10px;">
	<div id="r_rpp" hidden="hidden" style="height:30px;padding-top : 0px">
		<a href="javascript:void(0)" onclick="removeitRPE()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendRPE()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	<table id="r_reception_people_plan" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	<c:if test="${!empty applyBean.gId}">
	url: '${base}/apply/recep?id=${applyBean.gId}',
	</c:if>
	<c:if test="${!empty bean.rId&&operation=='edit'}">
	url: '${base}/reimburse/recep?rId=${bean.rId}',
	</c:if>
	<c:if test="${!empty bean.rId&&operation=='detail'}">
	url: '${base}/reimburse/recep?rId=${bean.rId}',
	</c:if>
	<c:if test="${empty applyBean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRowRPE,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'travelRId',hidden:true"></th>
				<th data-options="field:'government',align:'center',editor:'textbox'" style="width: 35%">单位</th>
				<th data-options="field:'receptionPeopName',align:'center',editor:'textbox'" style="width: 30%">姓名</th>
				<th data-options="field:'position',align:'center',editor:'textbox'" style="width: 35%">职务</th>
			</tr>
		</thead>
	</table>
</div>
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editPeopleIndex = undefined;
function endEditingRPE() {
	if (editPeopleIndex == undefined) {
		return true
	}
	if ($('#r_reception_people_plan').datagrid('validateRow', editPeopleIndex)) {
		var rpp = $('#r_reception_people_plan').datagrid('getEditor', {
			index : editPeopleIndex,
			field : 'costDetail'
		});
		$('#r_reception_people_plan').datagrid('endEdit', editPeopleIndex);
		editPeopleIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowRPE(index) {
	if(updateradio==0){
		if (editPeopleIndex != index) {
			if (endEditingRPE()) {
				$('#r_reception_people_plan').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editPeopleIndex = index;
			} else {
				$('#r_reception_people_plan').datagrid('selectRow', editPeopleIndex);
			}
		}
	}else{
		alert('住宿费无法编辑,请点击是否调整按钮进行编辑！');
	}
}
function appendRPE() {
	if (endEditingRPE()) {
		$('#r_reception_people_plan').datagrid('appendRow', {});
		editPeopleIndex = $('#r_reception_people_plan').datagrid('getRows').length - 1;
		$('#r_reception_people_plan').datagrid('selectRow', editPeopleIndex).datagrid('beginEdit',editPeopleIndex);
	}
}
function removeitRPE() {
	if (editPeopleIndex == undefined) {
		return
	}
	$('#r_reception_people_plan').datagrid('cancelEdit', editPeopleIndex).datagrid('deleteRow',
			editPeopleIndex);
	editPeopleIndex = undefined;
}
function acceptRPE() {
	if (endEditingRPE()) {
		$('#r_reception_people_plan').datagrid('acceptChanges');
	}
}
function receptionPeopleJson(){
	acceptRPE();
	var rowsRPE = $('#r_reception_people_plan').datagrid('getRows');
	var peopleJson = "";
	if(rowsRPE==''){
		return false;
	}else{
		for (var i = 0; i < rowsRPE.length; i++) {
			peopleJson = peopleJson + JSON.stringify(rowsRPE[i]) + ",";
		}
		$('#recePeopJson').val(peopleJson);
		return true;
	}
}	
	
</script>