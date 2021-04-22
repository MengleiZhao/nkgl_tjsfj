<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">&nbsp;&nbsp;
						<input id="payeeName_id" name="payeeName" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="queryPayee();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						<a href="javascript:void(0)"  onclick="clearPayee();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
					</td>
				</tr>
			</table>           
		</div>
		<div class="list-table">
			<table id="payee_tab_id" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimbPayee/reimbPayeePage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'pId',hidden:true"></th>
						<th data-options="field:'payeeId',hidden:true"></th>
						<th data-options="field:'payeeName',align:'center',resizable:false,sortable:true" style="width: 24%">收款人</th>
						<th data-options="field:'bankAccount',align:'center',resizable:false,sortable:true" style="width: 38%">银行账号</th>
						<th data-options="field:'bank',align:'center',resizable:false,sortable:true" style="width: 38%">开户银行</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="text-align: left;height: 20px">
			<span style="color:#ff6800">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;✧温馨提示：双击数据选择</span>
		</div>
	</div>
<script type="text/javascript">
//分页样式调整
$(function(){
	$("#payee_tab_id").datagrid({
		onDblClickRow:function(index, row){
			var tabId = '${id}';
			var indexTab = '${index}';
			var row = $('#payee_tab_id').datagrid('getSelected');
			var selections = $('#payee_tab_id').datagrid('getSelections');
			if (row != null && selections.length == 1) {
				var ed = $('#'+tabId).datagrid('getEditors',indexTab);
				ed[0].target.textbox('setValue',row.payeeId);
				ed[1].target.textbox('setValue',row.payeeName);
				ed[2].target.textbox('setValue',row.bankAccount);
				ed[3].target.textbox('setValue',row.bank);
				closeFirstWindow();
			} else {
				$.messager.alert('系统提示', '请选择一条数据！', 'info');
			} 
		 }
	});
});
function queryPayee(){
	$('#payee_tab_id').datagrid('load',{
		payeeName:$('#payeeName_id').textbox('getValue'),
	} ); 
}
function clearPayee(){
	$("#payeeName_id").textbox('setValue','');
	$('#payee_tab_id').datagrid('load',{//清空以后，重新查一次
	});
}
</script>
</body>
</html>