<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab">
	<table id="schedule-detail-dg" class="easyui-datagrid" style="width:695px;height:auto;"
	data-options="
	url: '${base}/schedule/scheduleProQuery?sId=${bean.sId}',
	method: 'post',
	striped : true,
	nowrap : false,
	scrollbarSize:0,
	singleSelect: true,
	showFooter: true,
	onLoadSuccess:onLoadSuccessScheduleDetail
	">
	<thead>
		<tr>
			<th data-options="field:'splId',hidden:true"></th>
			<th data-options="field:'sId',hidden:true"></th>
			<th data-options="field:'fProId',hidden:true"></th>
			<th data-options="field:'deptId',hidden:true"></th>
			<th data-options="field:'fProCode',hidden:true"></th>
			<th data-options="field:'pageOrder',required:'required',align:'center'" style="width: 10%">序号</th>
			<th data-options="field:'fProName',required:'required',align:'center'" style="width: 25%">项目名称</th>
			<th data-options="field:'deptName',required:'required',align:'center'" style="width: 20%">申报部门</th>
			<th data-options="field:'pfAmount',align:'center',resizable:false,sortable:true" style="width: 20%">项目总额[元]</th>
			<th data-options="field:'firstAmount',align:'center',resizable:false,sortable:true" style="width: 20%">一季度额度[元]</th>
			<th data-options="field:'twoAmount',align:'center',resizable:false,sortable:true" style="width: 20%">二季度额度[元]</th>
			<th data-options="field:'threeAmount',align:'center',resizable:false,sortable:true" style="width: 20%">三季度额度[元]</th>
			<th data-options="field:'fourAmount',align:'center',resizable:false,sortable:true" style="width: 20%">四季度额度[元]</th>
		</tr>
	</thead>
	</table>
</div>
<script type="text/javascript">
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});
</script>