<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-table" style="margin-bottom:10px;">

	<table id="dg_reception_people_plan_detail" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	<!-- toolbar: '#rpp', -->
	url: '${base}/apply/recep?id=${applyBean.gId}',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'travelRId',hidden:true"></th>
				<th data-options="field:'government',align:'center'" style="width: 35%">单位</th>
				<th data-options="field:'receptionPeopName',align:'center'" style="width: 30%">姓名</th>
				<th data-options="field:'position',align:'center'" style="width: 35%">职务</th>
			</tr>
		</thead>
	</table>
</div>
<script type="text/javascript">
	
</script>