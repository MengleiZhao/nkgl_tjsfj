<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr id="contract_ledger_search">
					<td class="top-table-search" >
						<input id="income-goldpay-ledger-fcCode" name="" onchange="CheckYou()" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="goldpay_ledger_query();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="goldpay_ledger_clearTable();"><img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
					</td> 
				</tr>
				<tr id="upt_ledger_search" style="display: none;">
					<td class="top-table-search" >
						<input id="income-goldpay-ledger-fcCode-upt" name="" onchange="CheckYouUpt()" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="goldpay_ledger_queryUpt();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="goldpay_ledger_clearTableUpt();"><img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
					</td> 
				</tr>
			</table>           
		</div>
		<div class="list-table-tab">
		<div class="tab-wrapper" id="contract-bzjtz-tab">
			<ul class="tab-menu">
				<li class="active" onclick="contractClick();">原合同</li>
			    <li onclick="updateOrendingClick();">变更/终止合同</li>
			</ul>
			<div class="tab-content">
			<div style="height: 440px">
			<table id="income-goldpay-dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/GoldPay/incomeRegistLedgerJson',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fcId',hidden:true"></th>
						<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
						<th data-options="field:'fcCode',align:'center',resizable:false,sortable:true" width="15%">合同编号</th>
						<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="25%">合同名称</th>
						<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="15%">申请部门</th>
						<th data-options="field:'fMarginAmount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="12%">保证金金额(元)</th>
						<th data-options="field:'fIncomeDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >到账日期</th>
						<th data-options="field:'checkSts',align:'center',formatter:flowStautsSet,resizable:false,sortable:true" width="10%">报销状态</th>
						<th data-options="field:'reimAmount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="12%">报销金额(元)</th>
						<th data-options="field:'reimTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >报销时间</th>
						<th data-options="field:'fIncomeStauts',align:'center',formatter:fIncomeStauts,resizable:false,sortable:true" width="10%">确认状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:incomeGoldpayLedgerCZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="height: 440px">
			<table id="income-goldpay-upt-dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/GoldPay/incomeUptRegistLedgerJson',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_U',hidden:true"></th>
						<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
						<th data-options="field:'fContCode',align:'center',resizable:false,sortable:true" width="15%">变更合同编号</th>
						<th data-options="field:'fContName',align:'center',resizable:false,sortable:true" width="25%">变更合同名称</th>
						<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="15%">申请部门</th>
						<th data-options="field:'fMarginAmount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="12%">保证金金额(元)</th>
						<th data-options="field:'fIncomeDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >到账日期</th>
						<th data-options="field:'checkSts',align:'center',formatter:flowStautsSet,resizable:false,sortable:true" width="10%">报销状态</th>
						<th data-options="field:'reimAmount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="12%">报销金额(元)</th>
						<th data-options="field:'reimTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >报销时间</th>
						<th data-options="field:'fIncomeStauts',align:'center',formatter:fIncomeStauts,resizable:false,sortable:true" width="10%">确认状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:incomeGoldpayLedgerCZ1" width="10%">操作</th>
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
flashtab('contract-bzjtz-tab');


//清除查询条件
function goldpay_ledger_clearTable() {
	$('#income-goldpay-ledger-fcCode').textbox('setValue',null);
	goldpay_ledger_query();
}
function goldpay_ledger_clearTableUpt() {
	$('#income-goldpay-ledger-fcCode-upt').textbox('setValue',null);
	goldpay_ledger_queryUpt();
}


var fs
function fIncomeStauts(val, row) {
	fs=val;
	if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">'  + " 已确认" + '</span>';
	}else if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">'  + " 未确认" + '</span>';
	}
}

//设置审批状态
var c;
function flowStautsSet(val, row) {
	c = val;
	if (val == null) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "未报销" + '</a>';
	}else if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "未报销" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "未报销" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已报销" + '</a>';
	} else if (val == -4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "未报销" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "报销中" + '</a>';
	}
}

function incomeGoldpayLedgerCZ(val, row) {
	return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
			+'<a href="#" onclick="derailIncomeRegistLedger(' + row.fcId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'
			+'</a></td></tr></table>';
		
}
function incomeGoldpayLedgerCZ1(val, row) {
	return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
			+'<a href="#" onclick="derailIncomeRegistLedger(' + row.fId_U+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'
			+'</a></td></tr></table>';
		
}
function goldpay_ledger_query() {
	$('#income-goldpay-dg').datagrid('load', {
		fcCode : $('#income-goldpay-ledger-fcCode').val(),
	});
}
function goldpay_ledger_queryUpt() {
	$('#income-goldpay-upt-dg').datagrid('load', {
		fUptCode : $('#income-goldpay-ledger-fcCode-upt').val(),
	});
}
//新增
function addIncomeRegist(id) {
	var win = creatWin('确认', 780, 570, 'icon-search',"/GoldPay/addIncomeRegist/" + id);
	win.window('open');
}
//查看
function derailIncomeRegistLedger(id) {
	var win = creatWin('查看', 780, 570, 'icon-search',"/GoldPay/detailIncomeRegist/" + id);
	win.window('open');
}
//修改
function updateIncomeRegist(id) {
	var win = creatWin('修改', 780, 570, 'icon-search',"/GoldPay/editIncomeRegist/" + id);
	win.window('open');
}
function contractClick(){
	$("#income-goldpay-dg").datagrid('reload');
	$("#contract_ledger_search").show();
	$("#upt_ledger_search").hide();
}
function updateOrendingClick(){
	$("#income-goldpay-upt-dg").datagrid('reload');
	$("#contract_ledger_search").hide();
	$("#upt_ledger_search").show();
}
</script>
</body>
</html>

