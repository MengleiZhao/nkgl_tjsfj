<%@ page language="java" pageEncoding="UTF-8"%>
   <table id="return_list_info_detail_dg" class="easyui-datagrid" style="height:auto;"
		data-options="
			rownumbers:true,
			url: '${base}/assetReturnList/getAssetReturnList?fId=${bean.fId_A}',
			method: 'post',
			onClickRow: onClickreturnList,
			singleSelect:true,
		">
	<thead>
		<tr>
			<th data-options="field:'fListId',hidden:true"></th>
			<th data-options="field:'fId_A',hidden:true"></th>
			<th data-options="field:'fAssCode_AR',align:'center'" style="width: 20%">卡片编号</th>
			<th data-options="field:'fFixedTypeName_AR',align:'center'" style="width: 20%">资产分类</th>
			<th data-options="field:'fAssName_AR',align:'center'" style="width: 25%">资产名称</th>
			<th data-options="field:'fAssSpecif_AR',align:'center'" style="width: 15%">型号</th>
			<c:if test="${openType=='approval'&&openuser=='实物管理岗'}">
				<th data-options="field:'fAvailableStauts_code',editor:{type:'textbox'},hidden:true,align:'center'"></th>
				<th data-options="field:'fAvailableStauts_AR',align:'center',editor:{type:'combobox',
						options:{
							required:true,
							valueField:'code',
							textField:'text',
							editable:false,
							validType:'selectValid',
							data: [
								{'code':'ZCKYZT-03', 'text':'--请选择--'},
								{'code':'ZCKYZT-01', 'text':'可用'},
								{'code':'ZCKYZT-02', 'text':'不可用'}
							],
							onSelect:function (rec){
								var tr = $('#return_list_info_detail_dg').datagrid('getEditors', editIndexReturnList);
								tr[0].target.textbox('setValue',rec.code);
								tr[1].target.combobox('setValue',rec.text);
							}
						}
					}" style="width: 20%">可用状态</th>
			</c:if>
			<c:if test="${openType=='approval'&& openuser!='实物管理岗'}">
				<th data-options="field:'fAvailableStauts.name',align:'center'" style="width: 20%">可用状态</th>
			</c:if>
			<c:if test="${openType=='detail'}">
				<th data-options="field:'fAvailableStauts_AR',align:'center'" style="width: 20%">可用状态</th>
			</c:if>
			<c:if test="${openType=='approval'}">
				<th data-options="field:'fUseName_AR',align:'center'" style="width: 10%">领用人</th>
				<th data-options="field:'fUseDept_AR',align:'center'" style="width: 15%">领用部门</th>
			</c:if>
			<th data-options="field:'fReceDate',align:'center',formatter: ChangeDateFormat" style="width: 20%">领用日期</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
var editIndexReturnList = undefined;
function endEditingPlanReturnList(){
	if (editIndexReturnList == undefined){return true}
	if ($('#return_list_info_detail_dg').datagrid('validateRow', editIndexReturnList)){
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#return_list_info_detail_dg').datagrid('getEditors', editIndexReturnList);
		var text4=tr[1].target.combobox('getText');
		if(text4!='--请选择--'){
			tr[1].target.combobox('setValues',text4);
		}
		$('#return_list_info_detail_dg').datagrid('endEdit', editIndexReturnList);
		editIndexReturnList = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickreturnList(index) {
	if (editIndexReturnList != index) {
		if (endEditingPlanReturnList()) {
			editIndexReturnList = index;
			$('#return_list_info_detail_dg').datagrid('selectRow', index).datagrid('beginEdit', index);
		} else {
			$('#return_list_info_detail_dg').datagrid('selectRow', editIndexReturnList);
		}
	}
}
function getreturnList(){
	acceptreturnList();
	var rows = $('#return_list_info_detail_dg').datagrid('getRows');
	var entities= '';
	for(var i = 0;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	 entities = '[' + entities.substring(0,entities.length -1) + ']';
	 return entities;
}
function acceptreturnList() {
	if (endEditingPlanReturnList()) {
		$('#return_list_info_detail_dg').datagrid('acceptChanges');
	}
}
</script>