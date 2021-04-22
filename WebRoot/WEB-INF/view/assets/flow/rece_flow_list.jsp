<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<%-- <div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search" class="queryth">
					&nbsp;&nbsp;申请单编号&nbsp;
					<input id="apply_list_top_gCode_" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;申请摘要名称&nbsp;
					<input id="apply_list_top_gName_" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;申请时间&nbsp;
					<input id="apply_list_top_reqTime1_" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateBegin[apply_list_top_reqTime2_]"/>
					&nbsp;-&nbsp;
					<input id="apply_list_top_reqTime2_" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateEnd[apply_list_top_reqTime1_]"/>
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearTable();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>   
	</div> --%>

	<div class="list-table">
		<table id="receFlowdg" class="easyui-datagrid"
		data-options="collapsible:true,url:'${base}/AssetFlow/receflowJson?assetid=${id }',
		method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,rownumbers:true,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns:true" >
			<thead>
				<tr>
					<th data-options="field:'gId',hidden:true"></th>
					<th data-options="field:'fReceTime',align:'center',resizable:false,formatter: ChangeDateFormat" style="width: 25%">领用时间</th>
					<th data-options="field:'fBackTime',align:'center',resizable:false,formatter: ChangeDateFormat" style="width: 25%">退回时间</th>
					<th data-options="field:'flowDeptName',align:'center',resizable:false" style="width: 25%">使用部门</th>
					<th data-options="field:'flowUser',align:'center',resizable:false" style="width: 25%">使用人</th>
				</tr>
			</thead>
		</table>
	</div>
</div>


<script type="text/javascript">
//查询
function queryApply() {
	var tableid="receFlowdg";
	
	var gCode="apply_list_top_gCode_";
	var gName="apply_list_top_gName_";
	var reqTime1="apply_list_top_reqTime1_";
	var reqTime2="apply_list_top_reqTime2_";
	
	$("#receFlowdg").datagrid('load',{
		gCode:$("#"+gCode).textbox('getValue').trim(),
		gName:$("#"+gName).textbox('getValue').trim(),
		reqTime1:$("#"+reqTime1).datebox('getValue').trim(),
		reqTime2:$("#"+reqTime2).datebox('getValue').trim(),
	});
}
//清除查询条件
function clearTable() {
	var gCode="apply_list_top_gCode_";
	var gName="apply_list_top_gName_";
	var reqTime1="apply_list_top_reqTime1_";
	var reqTime2="apply_list_top_reqTime2_";
	
	$("#"+gCode).textbox('setValue','');
	$("#"+gName).textbox('setValue','');
	$("#"+reqTime1).datebox('setValue','');
	$("#"+reqTime2).datebox('setValue','');
	
	var tableid="receFlowdg";
	$("#receFlowdg").datagrid('load',{});
}
</script>
</body>
