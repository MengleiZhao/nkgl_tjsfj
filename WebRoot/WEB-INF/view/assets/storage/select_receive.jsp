<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">验收单号&nbsp;
						<input id="CF_add_PN_facpCode" name="fpCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;项目编号&nbsp;
						<input id="CF_add_PN_fpCode" name="fpName" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;合同编号&nbsp;
						<input id="CF_add_PN_fcCode" name="fcCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="queryCF();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						<a href="javascript:void(0)"  onclick="clearCF();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
					</td>
				</tr>
			</table>           
		</div>
		<div class="list-table">
			<!-- <th data-options="field:'ck',checkbox:true"></th> -->
			<table id="CF_add_PN_dg1" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Storage/jsonPaginationReceive?fAssType=${fAssType}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'facpId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'facpCode',align:'center'" style="width: 20%">验收单号</th>
						<th data-options="field:'fpName',align:'center'" style="width: 22%">项目名称</th>
						<th data-options="field:'fcCode',align:'center'" style="width: 15%">合同编号</th>
						<th data-options="field:'cname',align:'center'" style="width: 25%">商品名称</th>
						<th data-options="field:'facpTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">验收时间</th>
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
	$("#CF_add_PN_dg1").datagrid({
		onDblClickRow:function(index, row){
			var row = $('#CF_add_PN_dg1').datagrid('getSelected');
			var selections = $('#CF_add_PN_dg1').datagrid('getSelections');
			if (row != null && selections.length == 1) {
				$("#S_facpCode").textbox('setValue',row.facpCode);
				onSelectCode(row.facpId,row.fcCode);
				closeFirstWindow();
			} else {
				$.messager.alert('系统提示', '请选择一条数据！', 'info');
			} 
		 }
	});
});


function formatPrice(val,row){
	if (val =="1"){
		return "公开招标";
	} else if(val =="2"){
		return "待邀请招标";
	} else if(val =="3"){
		return "竞争性谈判";
	} else if(val =="4"){
		return"单一来源采购";
	}
}
function bidamount(val,row){
	return  val==null?'未填写中标金额':val;
}
function CGCZ(val,row){
	return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="CGCZ_detail(' + row.fpId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a></td></tr></table>';
}	
function CGCZ_detail(id) {
	var win = creatSecondWin('采购单', 1115, 600, 'icon-search',"/cgsqsp/detail?id=" + id+'&openType=contract');
	win.window('open');
}
function PitchOn(id){
	var row = $('#CF_add_PN_dg1').datagrid('getSelected');
	var selections = $('#CF_add_PN_dg1').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		$("#F_fPurchNo").textbox('setValue',row.fpId); 
		$("#F_fcAmount").textbox('setValue',(row.fpAmount)); 
		closeFirstWindow();
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
}
function queryCF(){  
	$('#CF_add_PN_dg1').datagrid('load',{ 
		fpCode:$('#CF_add_PN_fpCode').textbox('getValue'),
		facpCode:$('#CF_add_PN_facpCode').textbox('getValue'),
		fcCode:$('#CF_add_PN_fcCode').textbox('getValue'),
		forgtype:$('#F_fOrgType').val()
	} ); 
}
function clearCF(){
	$("#CF_add_PN_fpCode").textbox('setValue','');
	$("#CF_add_PN_facpCode").textbox('setValue','');
	$("#CF_add_PN_fcCode").textbox('setValue','');
	$('#CF_add_PN_dg1').datagrid('load',{//清空以后，重新查一次
	});
}

</script>
</body>
</html>

