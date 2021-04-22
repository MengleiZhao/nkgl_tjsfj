<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search" class="queryth">合同编号&nbsp;
					<input id="e_fcCode" name="fcCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="e_fcTitle" name="fcTitle" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="enforcing_query();">
						<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)"  onclick="enforcing_clear();">
						<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				<%-- <td align="right" style="padding-right: 10px">
					<a href="#" onclick="enforcing_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
				</td> --%>
			</tr>
			<tr id="helpTr" style="display: none;"></tr>
		</table>
	</div>
		
	<div class="list-table">
		<table id="enforcing_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburse/handleReimbursePage?reimburseType=8&payId=${id }',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<th data-options="field:'fcId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" width="5%">序号</th>
					<th data-options="field:'rCode',align:'center'" width="15%">单据编号</th>
					<th data-options="field:'contCode',align:'center'" width="15%">合同编号</th>
					<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="20%">合同名称</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="15%">付款金额(元)</th>
					<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 15%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 15%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//设置审批状态
var c;
function flowStautsSet(val, row) {
	c = val;
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已审批" + '</a>';
	} else if (val == -4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已撤回" + '</a>';
	}  else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审批" + '</a>';
	}
}
//查询
function enforcing_query() {
	$('#enforcing_dg').datagrid('load', {
		fcCode : $('#e_fcCode').val(),
		fcTitle : $('#e_fcTitle').val(),
		/* percentageTempStart : $('#e_percentageTempStart').val(),
		percentageTempEnd : $('#e_percentageTempEnd').val() */
	});
}
//清除查询条件
function enforcing_clear() {
	$('#e_fcCode').textbox('setValue','');
	$('#e_fcTitle').textbox('setValue','');
	/* $('#e_percentageTempStart').combobox('setValue',0.0);
	$('#e_percentageTempEnd').combobox('setValue',1); */
	$('#enforcing_dg').datagrid('load',{//清空以后，重新查一次
	});
}

//操作栏创建
function CZ(val, row) {
	if (c == 1 || c == 2) {
		return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			   '<a href="#" onclick="enforcing_detail(' + row.rId +')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   '</a></td><td style="width: 25px">'+
			   '<a href="#" onclick="reCall(\'enforcing_dg\',' + row.rId + ',\'/reimburse/reimburseReCall\')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
			   '</a></td></tr></table>';
	} else if(c == 0 || c == -1 || c == -4) {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="enforcing_detail(' + row.rId + ','+row.type+')" class="easyui-linkbutton">'+
		   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		   		'</a>'+ '</td><td style="width: 25px">'+
				'<a href="#" onclick="enforcing_edit(' + row.rId + ','+row.type+')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
				'</a>' + '</td><td style="width: 25px">'+
				'<a href="#" onclick="deleteEnforcingList(' + row.rId + ',' + row.type + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
				'</a></td></tr></table>';
	}else{
		//c==9审批通过无法撤回
		return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			   '<a href="#" onclick="enforcing_detail(' + row.rId + ',0,'+row.type+')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		   		'</a>'+ '</td><td style="width: 25px">'+
				'<a href="#" onclick="contract_exportHtml(' + row.rId
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'
				+'</a></td></tr></table>';
	}
}
//打印
function contract_exportHtml(id) {
	window.open(base+"/exportApplyAndReim/contractExport?id="+ id);
}
//付款计划
function plan(id) {
	var win = creatWin('付款计划', 1115, 600, 'icon-search', '/Enforcing/plan/'+id);
	win.window('open'); 
}
//付款新增
function enforcing_add() {
	var win = creatWin('新增', 1115, 600, 'icon-search', '/Enforcing/add');
	win.window('open'); 
}
function enforcing_edit(id) {
	var win = creatWin('修改', 1115, 600, 'icon-search', '/Enforcing/edit?id='+id);
	win.window('open'); 
}
function enforcing_detail(id) {
	var win = creatSearchDataWin('查看', 1115, 600, 'icon-search', '/Enforcing/detail?id='+id+'&detailType=htbx&handledetail=121312');
	win.window('open'); 
}
//删除
function deleteEnforcingList(id,type) {
	if (confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : '${base}/reimburse/deleteEnforcingList?id=' + id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#enforcing_dg').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
</script>
</body>