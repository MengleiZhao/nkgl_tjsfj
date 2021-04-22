<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr id="contract_search">
				<td class="top-table-search">合同编号&nbsp;
					<input id="c_fcCode" name="fcCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="c_fcTitle" name="fcTitle" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="javascript:void(0)"  onclick="queryContractSeal();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
					</a>
					<a href="javascript:void(0)"  onclick="clearContractSeal();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
					</a>
				</td>
			</tr>
			<tr id="upt_search" style="display: none;">
				<td class="top-table-search">合同编号&nbsp;
					<input id="upt_fcCode" name="fcCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="upt_fcTitle" name="fcTitle" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="javascript:void(0)"  onclick="queryUptSeal();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
					</a>
					<a href="javascript:void(0)"  onclick="clearUptSeal();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
					</a>
				</td>
			</tr>
		</table>           
	</div>
		
	<div class="list-table-tab">
		<div class="tab-wrapper" id="bzj-reimb-tab">
			<ul class="tab-menu">
				<li class="active" onclick="contractClick();">原合同</li>
			    <li onclick="updateOrendingClick();">变更/终止合同</li>
			</ul>
			<div class="tab-content">
				<div style="height: 440px">
					<table id="contract_seal_list" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/Formulation/JsonPaginationSeal',method:'post',fit:true,pagination:true,singleSelect: true,
						selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
						<thead>
							<tr>
								<th data-options="field:'fcId',hidden:true"></th>
								<th data-options="field:'fMarginAmount',hidden:true"></th>
								<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="15%">序号</th>
								<th data-options="field:'fcCode',align:'center',resizable:false,sortable:true" width="45%">合同编号</th>
								<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="42%">合同名称</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="height: 440px">
					<table id="upt_seal_list" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/GoldPay/JsonPaginationSealUpt',method:'post',fit:true,pagination:true,singleSelect: true,
						selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
						<thead>
							<tr>
								<th data-options="field:'fcId',hidden:true"></th>
								<th data-options="field:'fMarginAmount',hidden:true"></th>
								<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="15%">序号</th>
								<th data-options="field:'fContCode',align:'center',resizable:false,sortable:true" width="45%">合同编号</th>
								<th data-options="field:'fContName',align:'center',resizable:false,sortable:true" width="42%">合同名称</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>	
	</div>
</div>	
<script type="text/javascript">
//加载tab页
flashtab('bzj-reimb-tab');
//双击选择页面
$('#contract_seal_list').datagrid({
	onDblClickRow: function(rowIndex, rowData){

		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/Formulation/findContractByFcId?fcId='+rowData.fcId,
			success:function (data){
				goldpayaccept();
				var goldpayRows = $('#goldpay-edit-tab').datagrid('getRows');
				var payerinfo = $('#payer_info_ext_tab').datagrid('getRows');
				for(var i = goldpayRows.length-1 ; i >= 0 ; i--){
					$('#goldpay-edit-tab').datagrid('deleteRow',i);
				}
				for(var i = payerinfo.length-1 ; i >= 0 ; i--){
					$('#payer_info_ext_tab').datagrid('deleteRow',i);
				}
				$('#goldpay-edit-tab').datagrid('appendRow', {
					costDetail:'合同保证金',
					remibAmount:data[0].fMarginAmount
				});
				$('#payer_info_ext_tab').datagrid('appendRow', {
					payeeName:data[0].fContractor,
					bankAccount:data[1][0].fCardNo,
					bank:data[1][0].fBankName
				});
				$('#rgoldpayeditAmount').html(data[0].fMarginAmount+'[元]');
				$('#fcTitle_show').textbox('setValue',data[0].fcTitle);
				$('#payId').val(data[0].fcId);
				$('#contCode').val(data[0].fcCode);
				$('#fcTitle').val(data[0].fcTitle);
				$('#reimburseAmount').val(data[0].fMarginAmount);
				closeFirstWindow();
			}
		});
	}
}); 
//双击选择页面
$('#upt_seal_list').datagrid({
	onDblClickRow: function(rowIndex, rowData){
				var row = $('#upt_seal_list').datagrid('getSelected');
				goldpayaccept();
				var goldpayRows = $('#goldpay-edit-tab').datagrid('getRows');
				var payerinfo = $('#payer_info_ext_tab').datagrid('getRows');
				for(var i = goldpayRows.length-1 ; i >= 0 ; i--){
					$('#goldpay-edit-tab').datagrid('deleteRow',i);
				}
				for(var i = payerinfo.length-1 ; i >= 0 ; i--){
					$('#payer_info_ext_tab').datagrid('deleteRow',i);
				}
				$('#goldpay-edit-tab').datagrid('appendRow', {
					costDetail:'合同保证金',
					remibAmount:row.fMarginAmount
				});
				$('#payer_info_ext_tab').datagrid('appendRow', {
					payeeName:data[0].fContractor,
					bankAccount:data[1][0].fCardNo,
					bank:data[1][0].fBankName
				});
				$('#rgoldpayeditAmount').html(row.fMarginAmount+'[元]');
				$('#fcTitle_show').textbox('setValue',row.fContName);
				$('#payId').val(row.fId_U);
				$('#contCode').val(row.fContCode);
				$('#fcTitle').val(row.fContName);
				$('#reimburseAmount').val(row.fMarginAmount);
				closeFirstWindow();
			}
}); 


//查询
function queryContractSeal() {
	$('#contract_seal_list').datagrid('load', {
		fcCode: $('#c_fcCode').val(),
		fcTitle: $('#c_fcTitle').val(),
	});
}
//清除查询条件
function clearContractSeal() {
	$('#c_fcCode').textbox('setValue','');
	$('#c_fcTitle').textbox('setValue','');
	$('#contract_seal_list').datagrid('load',{//清空以后，重新查一次
	});
}
function formatfcType(val, row) {
	if (val == 'HTFL-01') {
		return '<span style="color:#666666;">' + " 采购合同" + '</span>';
	}else{
		return '<span style="color:#666666;">' + " 非采购合同" + '</span>';
	}
}

//合同状态
function formatContStauts(val, row) {
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未拟定" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 拟定中" + '</span>';
	} else if (val == 9) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已备案" + '</span>';
	} else if (val == 3) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已结项" + '</span>';
	} else if (val == 5) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已归档" + '</span>';
	} else if (val == -1) {
		return '<span style="color:red;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已终止" + '</span>';
	} else if (val == -9) {
		return '<span style="color:green;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已完结" + '</span>';
	}
}

//合同盖章状态
function formatSealedStatus(val, row) {
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未盖章" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已盖章" + '</span>';
	}
}

//审批状态
function formatCheckStatus(val, row) {
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</span>';
	} else if (val == 9) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</span>';
	} else if (val == -1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</span>';
	} else if (val == -4) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</span>';
	}
}

//操作栏创建
function CZ(val, row) {
	//审批状态
	var c = row.fFlowStauts;
	if (c == 9) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="contract_detail(' + row.fcId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			    '</a>'+ '</td><td style="width: 25px">'+
				'<a href="#" onclick="contract_exportHtml(' + row.fcId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
			    '</a></td></tr></table>';
	} else if (c == 1) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="contract_detail(' + row.fcId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="reCall(\'contract_list\' ,'+ row.fcId+',\'/Formulation/reCall\''+')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
				'</a></td></tr></table>';
	} else if (c == 0 || c == -1 || c == -4) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="contract_detail(' + row.fcId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="contract_update(' + row.fcId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="contract_delete(' + row.fcId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
				'</a></td></tr></table>';
	}
}
//打印
function contract_exportHtml(id) {
	window.open(base+"/contractExport/formulation?id="+ id);
}
//新增
function contract_add() {
	var win = creatWin('新增', 1115, 600, 'icon-search', '/Formulation/add');
	win.window('open');
}
//查看
function contract_detail(id) {
	var win = creatWin('查看', 1115, 600, 'icon-search', '/Formulation/detail?id='+id);
	win.window('open');
}
//修改
function contract_update(id) {
	var win = creatWin('修改', 1115, 600, 'icon-search', '/Formulation/edit?id='+id);
	win.window('open');
}
//删除
function contract_delete(id) {
	if (confirm('确认删除吗？')) {
		$.ajax({
			type : 'POST',
			url : base + '/Formulation/delete?id='+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#contract_list').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}

function contractClick(){
	$("#contract_seal_list").datagrid('reload');
	$("#contract_search").show();
	$("#upt_search").hide();
}
function updateOrendingClick(){
	$("#upt_seal_list").datagrid('reload');
	$("#contract_search").hide();
	$("#upt_search").show();
}
</script>
</body>