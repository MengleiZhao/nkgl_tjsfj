<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>



<div class="window-tab" style="margin-left: 0px;padding-top: 10px">


	<table id="apply_mingxi-zonghe-dg" class="easyui-datagrid" style="width:693px;height:auto;"
	data-options="
	toolbar: '#apply_zongheys_Id',
	url: '${base}/apply/mingxi?id=${applyBean.gId}',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:false,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<th data-options="field:'cId',hidden:true"></th>
				<th data-options="field:'costDetail',required:'required',align:'center',width:170">费用名称</th>
				<th data-options="field:'standard',required:'required',align:'center',width:166">费用标准（元/人天）</th>
				<th data-options="field:'totalStandard',required:'required',align:'center',width:180">总额标准[元]</th>
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}">申请金额[元]</th>
		</tr>
	</thead>
	</table>
	<div id="apply_zongheys_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">综合预算</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span id="zongheysAmount"><fmt:formatNumber groupingUsed="true" value="${trainingBean.zongheMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</div>
</div>

<script type="text/javascript">

</script>
