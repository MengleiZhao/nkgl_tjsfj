<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="dg_reception_strok_plan_detail" class="easyui-datagrid" 
	style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	<!-- toolbar: '#rsp', -->
	url: '${base}/apply/receptionStrokPlan?id=${applyBean.gId}',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'fSPId',hidden:true"></th>
				<th data-options="field:'fPro',width:195,align:'center'">项目</th>
				<th data-options="field:'fTime',width:195,align:'center',formatter:ChangeDateFormat">时间</th>
				<th data-options="field:'fAddress',width:290,align:'center'">场所</th>
			</tr>
		</thead>
	</table>
</div>
	
<script type="text/javascript">
	
</script>