<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr id="contract_search">
					<td class="top-table-search" >
						<input id="income-goldpay-fcCode" name="" onchange="CheckYou()" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="goldpay_query();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="goldpay_clearTable();"><img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
					</td> 
				</tr>
				<tr id="upt_search" style="display: none;">
					<td class="top-table-search" >
						<input id="income-goldpay-fcCode-upt" name="" onchange="CheckYouUpt()" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="goldpay_queryUpt();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="goldpay_clearTableUpt();"><img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
					</td> 
				</tr>
			</table>           
		</div>
		<div class="list-table-tab">
		<div class="tab-wrapper" id="contract-bzj-tab">
			<ul class="tab-menu">
				<li class="active" onclick="contractClick();">原合同</li>
			    <li onclick="updateOrendingClick();">变更/终止合同</li>
			</ul>
			<div class="tab-content">
				<div style="height: 440px">
			<table id="income-goldpay-dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/GoldPay/incomeJsonPagination',
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
						<th data-options="field:'fIncomeStauts',align:'center',formatter:fIncomeStauts,resizable:false,sortable:true" width="10%">确认状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:incomeGoldpayCZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="height: 440px">
			<table id="income-goldpay-upt-dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/GoldPay/incomeUptJsonPagination',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_U',hidden:true"></th>
						<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
						<th data-options="field:'fContCode',align:'center',resizable:false,sortable:true" width="15%">合同编号</th>
						<th data-options="field:'fContName',align:'center',resizable:false,sortable:true" width="25%">合同名称</th>
						<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="15%">申请部门</th>
						<th data-options="field:'fMarginAmount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="12%">保证金金额(元)</th>
						<th data-options="field:'fIncomeDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >到账日期</th>
						<th data-options="field:'fIncomeStauts',align:'center',formatter:fIncomeStauts,resizable:false,sortable:true" width="10%">确认状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:incomeGoldpayCZ1" width="10%">操作</th>
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
flashtab('contract-bzj-tab');

//清除查询条件
function goldpay_clearTable() {
	$('#income-goldpay-fcCode').textbox('setValue',null);
	goldpay_query();
}
function goldpay_clearTableUpt() {
	$('#income-goldpay-fcCode-upt').textbox('setValue',null);
	goldpay_queryUpt();
}
var fs
function fIncomeStauts(val, row) {
	fs=val;
	if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">'  + " 已确认" + '</span>';
	}else if (val == 0||!isNotEmpty(fs)) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">'  + " 未确认" + '</span>';
	}
}
function incomeGoldpayCZ(val, row) {
	if(fs==1){
		return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="derailIncomeRegist(' + row.fcId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'
				+'</a>'+ '</td></tr></table>';
	}else if(fs==0||!isNotEmpty(fs)){
		return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="derailIncomeRegist(' + row.fcId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'
				+'</a>'+ '</td><td style="width: 25px">'
				+'<a href="#" onclick="addIncomeRegist(' + row.fcId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/qr1.png">' 
				+'</a></td></tr></table>';
	}
		
}
function incomeGoldpayCZ1(val, row) {
	if(fs==1){
		return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="derailIncomeRegist(' + row.fId_U+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'
				+'</a>'+ '</td></tr></table>';
	}else if(fs==0||!isNotEmpty(fs)){
		return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="derailIncomeRegist(' + row.fId_U+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'
				+'</a>'+ '</td><td style="width: 25px">'
				+'<a href="#" onclick="addIncomeRegistUpt(' + row.fId_U+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/qr1.png">' 
				+'</a></td></tr></table>';
	}
		
}
function goldpay_query() {
	$('#income-goldpay-dg').datagrid('load', {
		fcCode : $('#income-goldpay-fcCode').val(),
	});
}

function goldpay_queryUpt() {
	$('#income-goldpay-upt-dg').datagrid('load', {
		fUptCode : $('#income-goldpay-fcCode-upt').val(),
	});
}
//新增
function addIncomeRegist(id) {
	var win = creatWin('确认', 780, 570, 'icon-search',"/GoldPay/addIncomeRegist/" + id);
	win.window('open');
}
function addIncomeRegistUpt(id) {
	var win = creatWin('确认', 780, 570, 'icon-search',"/GoldPay/addIncomeRegistUpt/" + id);
	win.window('open');
}
//查看
function derailIncomeRegist(id) {
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
	$("#contract_search").show();
	$("#upt_search").hide();
}
function updateOrendingClick(){
	$("#income-goldpay-upt-dg").datagrid('reload');
	$("#contract_search").hide();
	$("#upt_search").show();
}
</script>
</body>
</html>

