<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-table" style="margin-bottom:10px;">

	<table id="dg_reception_people_plan" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#rpp',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/recep?id=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
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
	<div id="rpp" style="height:30px;padding-top : 0px">
		<a href="javascript:void(0)" onclick="removeitRPE()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendRPE()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	<input type="hidden" id="recePeopJson" name="recePeop" />
</div>
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editPeopleIndex = undefined;
function endEditingRPE() {
	if (editPeopleIndex == undefined) {
		return true
	}
	if ($('#dg_reception_people_plan').datagrid('validateRow', editPeopleIndex)) {
		var rpp = $('#dg_reception_people_plan').datagrid('getEditor', {
			index : editPeopleIndex,
			field : 'costDetail'
		});
		$('#dg_reception_people_plan').datagrid('endEdit', editPeopleIndex);
		editPeopleIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowRPE(index) {
	if (editPeopleIndex != index) {
		if (endEditingRPE()) {
			$('#dg_reception_people_plan').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editPeopleIndex = index;
		} else {
			$('#dg_reception_people_plan').datagrid('selectRow', editPeopleIndex);
		}
	}
}
function appendRPE() {
	if($('#dg_reception_people_plan').datagrid('getRows').length >= $('#rePeopNum').val()){
		alert("行数不得超过接待对象人数");
		return;
	};
	if (endEditingRPE()) {
		$('#dg_reception_people_plan').datagrid('appendRow', {});
		editPeopleIndex = $('#dg_reception_people_plan').datagrid('getRows').length - 1;
		$('#dg_reception_people_plan').datagrid('selectRow', editPeopleIndex).datagrid('beginEdit',editPeopleIndex);
	}
}
function removeitRPE() {
	if (editPeopleIndex == undefined) {
		return
	}
	$('#dg_reception_people_plan').datagrid('cancelEdit', editPeopleIndex).datagrid('deleteRow',
			editPeopleIndex);
	editPeopleIndex = undefined;
}
function acceptRPE() {
	if (endEditingRPE()) {
		$('#dg_reception_people_plan').datagrid('acceptChanges');
	}
}
	
	
/* 	//重新计算开支标准
	function countkzbz() {
		accept();
		//获得接待人数（有几行就是接待多少人）
		var rownum = $('#dg_reception_people_plan').datagrid('getRows').length;
		//修改明细表中的开支标准
		var rows = $('#appli-detail-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++) {
			var tr = $('#appli-detail-dg').datagrid('getEditors', i);
			//获得每一行的开支标准
			var kzbz=rows[i].standard;
			//设置开支标准
			onClickRow(i);
			tr[1].target.textbox('setValue', parseFloat(kzbz*rownum));
			accept();
		}
	} */
</script>