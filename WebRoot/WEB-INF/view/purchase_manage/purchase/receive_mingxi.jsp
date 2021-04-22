<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="receive_list_ledger" class="easyui-datagrid" style="width: 690px;height: auto;"
			data-options="collapsible:true,url:'${base}/cgsqLedger/cgledgerReceive?fpId=${bean.fpId }',
			method:'post',pagination:false,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
	<thead>
		<tr>
			<th data-options="field:'num',align:'center'" style="width: 10%">序号</th>
			<th data-options="field:'facpCode',align:'center'" style="width: 40%">验收单号</th>
			<th data-options="field:'facpTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 40%">申报时间</th>
			<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
//操作栏创建
function CZ(val, row) {
	return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="openAccept(' + row.facpId+ ')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		    '</a></td></tr></table>';
}
function openAccept(id){
	var win = creatFirstWin('查看', 805, 600, 'icon-search', '/cgreceive/detail?id='+id);
	win.window('open');
}
</script>