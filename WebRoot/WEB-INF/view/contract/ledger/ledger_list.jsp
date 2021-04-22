<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>

<body >
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
		<table class="topTable" style="width: 100%;font-size: 12px;height: 40px;"  cellpadding="0" cellspacing="0">
			<tr id="contract_top">
				<td class="top-table-search" class="queryth">
					<input id="contract_name" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"/>
					&nbsp;&nbsp;
					<a href="#" onclick="queryContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="exportContract(0)">
						<img src="${base}/resource-modality/${themenurl}/button/daochu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			<tr id="UpdateOrending_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<input id="UpdateOrending_name" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"/>
					<!-- 
					&nbsp;&nbsp;报销时间&nbsp;
					<input id="reimburse_cashier_list_top_reqTime1" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateBegin[reimburse_cashier_list_top_reqTime2]"/>
					&nbsp;-&nbsp;
					<input id="reimburse_cashier_list_top_reqTime2" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateEnd[reimburse_cashier_list_top_reqTime1]"/>
					 -->
					&nbsp;&nbsp;
					<a href="#" onclick="queryUpdateOrending();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearUpdateOrending();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="exportContract(1)">
						<img src="${base}/resource-modality/${themenurl}/button/daochu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>   
	</div>
	<div class="list-table-tab">
		<div class="tab-wrapper" id="contract-ledger-tab">
			<ul class="tab-menu">
				<li class="active" onclick="contractClick();">原合同</li>
			    <li onclick="updateOrendingClick();">变更/终止合同</li>
			</ul>
			<div class="tab-content">
				<div style="height: 440px">
					<table id="contractTab" class="easyui-datagrid"
					data-options="collapsible:true,url:'${base}/Ledger/JsonPagination',method:'post',
					method:'post',fit:true,pagination:true,singleSelect: true,selectOnCheck: true,
					checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true">
						<thead>
							<tr>
								<!-- <th data-options="field:'ck',checkbox:true"></th> -->
								<th data-options="field:'fcId',hidden:true"></th>
								<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
								<th data-options="field:'fcCode',align:'center',resizable:false,sortable:true" width="15%">合同编号</th>
								<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="15%">合同名称</th>
								<th data-options="field:'fSignTime',align:'center',formatter:ChangeDateFormat,resizable:false,sortable:true" width="10%" >合同签订日期</th>
								<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="10%">申请部门</th>
								<th data-options="field:'fOperator',align:'center',resizable:false,sortable:true" width="10%">申请人</th>
								<th data-options="field:'fPlanTotalAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">最新合同金额(元)</th>
								<th data-options="field:'fAllAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">已付款金额(元)</th>
								<th data-options="field:'fNotAllAmountL',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">剩余金额(元)</th>
								<th data-options="field:'keepTime',align:'center',resizable:false,sortable:true" width="20%" >合同履约期</th>
								<th data-options="field:'fUpdateStatus',align:'center',resizable:false,sortable:true,formatter:HTupdateStatus" width="10%">变更状态</th>
								<th data-options="field:'fToFilesStatus',align:'center',resizable:false,sortable:true,formatter:archiving1" width="10%">归档状态</th>
								<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZRaw" width="10%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="height: 440px">
					<table id="updateOrendingTab" class="easyui-datagrid"
					data-options="collapsible:true,url:'${base}/Ledger/ChangeJsonPagination',
					method:'post',fit:true,pagination:true,singleSelect: true,selectOnCheck: true,
					checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true">
						<thead>
							<tr>
								<!-- <th data-options="field:'ck',checkbox:true"></th> -->
								<th data-options="field:'fId_U',hidden:true"></th>
								<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
								<th data-options="field:'fContCode',align:'center',resizable:false,sortable:true" width="12%">变更合同编号</th>
								<th data-options="field:'fContCodeOld',align:'center',resizable:false,sortable:true" width="13%">原合同编号</th>
								<th data-options="field:'fContNameold',align:'center',resizable:false,sortable:true" width="15%">原合同名称</th>
								<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="15%">申请部门</th>
								<th data-options="field:'fOperator',align:'center',resizable:false,sortable:true" width="10%">申请人</th>
								<th data-options="field:'fUptdate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >合同签订日期</th>
								<th data-options="field:'fToFilesStatus',align:'center',resizable:false,sortable:true,formatter:archiving2" width="10%">归档状态</th>
								<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZChange" width="10%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<form id="form_contract_export" method="post" enctype="multipart/form-data">
	<input type="hidden" name="sbkLx" value="xmtz">
	<input id="form_contract_export_fcCode" type="hidden" name="fcCode">
	<input id="form_contract_export_fcTitle" type="hidden" name="fcTitle">
	<input id="form_idjson" type="hidden" name="idjson">
</form>
<script type="text/javascript">
//加载tab页
flashtab('contract-ledger-tab');

function cashierTypeSet(val, row) {
	if (val == "0"||val == null) {
		return '<span >' + "未付款" + '</span>';
	}else if (val == "1") {
		return '<span >' + "已付款" + '</span>';
	}
}
//合同付款名称（什么阶段的款）
function paytype(val, row) {
	if (val == "FKXZ-01") {
		return '<span >' + "首款" + '</span>';
	} else if (val == "FKXZ-02") {
		return '<span >' + "阶段款" + '</span>';
	}else if (val == "FKXZ-03") {
		return '<span >' + "验收款" + '</span>';
	}else if (val == "FKXZ-04") {
		return '<span >' + "质保款" + '</span>';
	}
}
//操作栏创建
function CZRaw(val, row) {
	return	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="ledger_detail(' + row.fcId + ')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			'</a></td></tr></table>';
}
//操作栏创建
function CZChange(val, row) {
	return	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="ledger_detailUpt(' + row.fId_U + ')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			'</a></td></tr></table>';
}


//设置审批状态
function flowStautsSet(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已审批" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审批" + '</a>';
	}
}

//设置采购审批状态
function formatPrice(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	}
}
//设置付款申请状态
function formatPay(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	}
}
//设置验收状态
function formatRecive(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未验收" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已验收" + '</a>';
	}
}

/** 查看操作 **/

//跳转报销查看页面
function detailReimburse(id,reimburseType) {
	//0为直接报销1位申请报销2位采购支付3位合同支付
	var win = creatWin('出纳受理', 970, 580, 'icon-search', '/directlyReimburse/edit?id='+id+'&editType=0');
	win.window('open');
}
function detailReimburseOpen(id,reimburseType) {
	//0为直接报销1位申请报销2位采购支付3位合同支付
	var win = creatWin('出纳受理', 1070, 580, 'icon-search', '/directlyReimburse/edit?id='+id+'&editType=0');
	win.window('open');
}
//跳转申请报销查看页面
function detail(id,reimburseType) {
	
	if(reimburseType==1){
	var win = creatWin('出纳受理', 770, 580, 'icon-search', '/reimburse/edit?id='+id+'&editType=0');
	win.window('open');
	}else{
		var win = creatWin('出纳受理',1060, 580, 'icon-search', '/reimburse/edit?id='+id+'&editType=0');
		win.window('open');
	}
}
//跳转合同付款查看页面	
function detailContract(id,fPlanId,payId) {
	var win = creatWin('出纳受理', 970, 580, 'icon-search', '/Enforcing/detail?id='+id+'&fPlanId='+fPlanId+'&payId='+payId+'&cashier=1');
	win.window('open');
}

function contractClick(){
	$("#contractTab").datagrid('reload');
	$("#contract_top").show();
	$("#UpdateOrending_top").hide();
}
function updateOrendingClick(){
	$("#updateOrendingTab").datagrid('reload');
	$("#contract_top").hide();
	$("#UpdateOrending_top").show();
}
//直接报销查询
function queryContract() {
	var deptName="contract_name";
	$("#contractTab").datagrid('load',{
		searchTitle:$("#"+deptName).textbox('getValue').trim(),
		inStatus:2,
	});
}
//直接报销清除查询条件
function clearContract() {
	var fcCode="contract_code";
	var fcTitle="contract_name";
	$("#"+fcCode).textbox('setValue','');
	$("#"+fcTitle).textbox('setValue','');
	$("#contractTab").datagrid('load',{});
}

//申请报销查询
function queryUpdateOrending() {
	var fcTitle="UpdateOrending_name";
	$("#updateOrendingTab").datagrid('load',{
		searchTitle:$("#"+fcTitle).textbox('getValue').trim(),
		inStatus:2,
	});
}
//申请报销清除查询条件
function clearUpdateOrending() {
	var rCode="UpdateOrending_code";
	var fcTitle="UpdateOrending_name";
	$("#"+rCode).textbox('setValue','');
	$("#"+fcTitle).textbox('setValue','');
	$("#updateOrendingTab").datagrid('load',{});
}
//是否归档
function archiving1(val, row) {
	if (val == '0') {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未归档" + '</span>';
	} else if (val == '1') {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已归档" + '</span>';
	}
}
function archiving2(val, row) {
	if (val == '0') {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未归档" + '</span>';
	} else if (val == '1') {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已归档" + '</span>';
	}
}

//台账查看
function ledger_detail(id) {
	var win = creatWin('查看', 1100, 580, 'icon-search', '/Formulation/detail?id='+id);
	win.window('open');
}
//台账查看
function ledger_detailUpt(id) {
	var win = creatWin('查看', 1100, 580, 'icon-search', '/Change/detail/'+id);
	win.window('open');
}

//合同台账导出
function exportContract(type) {
	if(confirm('是否按查询条件导出？')){
	var name = "";
		if(type=='0'){
			name=$("#contract_name").textbox('getValue').trim();
		}else{
			name=$("#UpdateOrending_name").textbox('getValue').trim();
		}
		$('#form_contract_export').attr('action','${base}/Ledger/export?type='+type+'&name='+name);
		$('#form_contract_export').submit();
	}
}
function theNew(){
	
}

</script>
</body>
</html>

