<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_choose_dg" class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryCgApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearCgApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="cg_choose_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgAsk/chooseCgPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 15%">采购项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fpMethod',align:'center',resizable:false" style="width: 10%">采购类型</th>
					<th data-options="field:'fpPype',align:'center',resizable:false" style="width: 10%">采购方式</th>
					<th data-options="field:'fUserName',align:'center',resizable:false" style="width: 10%">申请人</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false" style="width: 10%">申请部门</th>
					<th data-options="field:'fOrgName',align:'center',resizable:false" style="width: 10%">中标商名称</th>
					<th data-options="field:'fbidAmount',align:'center',resizable:false,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 10%">中标金额</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="text-align: left;height: 20px">
			<span style="color:#ff6800">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;✧温馨提示：双击数据选择</span>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgApply() {
	var searchData="searchData";
	$('#cg_choose_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgApply() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#cg_choose_Tab').datagrid('load',{//清空以后，重新查一次
	});
}


$(function(){
	$("#cg_choose_Tab").datagrid({
		onDblClickRow:function(index, row){
			
			var row = $('#cg_choose_Tab').datagrid('getSelected');
			var selections = $('#cg_choose_Tab').datagrid('getSelections');
			if (row != null && selections.length == 1) {
				$("#F_PId").val(row.fpId); 
				$("#F_fpCode").textbox('setValue',row.fpCode); 
				$("#F_fpName").textbox('setValue',row.fpName); 
				$('#F_fpPype').textbox('setValue',row.fpPype);
				$('#F_fpMethod').textbox('setValue',row.fpMethod);
				$('#F_fDeptName').textbox('setValue',row.fDeptName);
				$('#F_fUserName').textbox('setValue',row.fUserName);
				$('#F_fOrgName').textbox('setValue',row.fOrgName);
				$("#F_fbidAmount").numberbox('setValue',(row.fbidAmount==null?0.00:row.fbidAmount)); 
				closeSecondWindow();
			} else {
				$.messager.alert('系统提示', '请选择一条数据！', 'info');
			} 
		 }
	});
});
</script>
</body>
</html>