<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top">
		<table class="topTable" style="width: 100%;font-size: 12px;height: 40px;"  cellpadding="0" cellspacing="0">
			<tr id="Archiving_contract_top">
				<td class="top-table-search" class="queryth">
					<input id="Archiving_contract_name" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"/>
					&nbsp;&nbsp;
					<a href="#" onclick="ArchivingQueryContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="ArchivingClearContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			<tr id="Archiving_UpdateOrending_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<input id="Archiving_UpdateOrending_name" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"/>
					&nbsp;&nbsp;
					<a href="#" onclick="ArchivingQueryUpdateOrending();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="ArchivingClearUpdateOrending();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>   
	</div>
	<div class="list-table-tab">
			<div class="tab-wrapper" id="contract-archiving-tab">
				<ul class="tab-menu">
					<li class="active" onclick="ArchivingContractClick();">原合同</li>
				    <li onclick="ArchivingUpdateOrendingClick();">变更/终止合同</li>
				</ul>
				<div class="tab-content">
					<div style="height: 440px">
						<table id="ArchivingcontractTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/Archiving/JsonPagination',method:'post',
						method:'post',fit:true,pagination:true,singleSelect: true,selectOnCheck: true,
						checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true">
							<thead>
								<tr>
									<!-- <th data-options="field:'ck',checkbox:true"></th> -->
									<th data-options="field:'fcId',hidden:true"></th>
									<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
									<th data-options="field:'fcCode',align:'center',resizable:false,sortable:true" width="17%">合同编号</th>
									<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="25%">合同名称</th>
									<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="10%">申请部门</th>
									<th data-options="field:'fOperator',align:'center',resizable:false,sortable:true" width="10%">申请人</th>
									<th data-options="field:'fReqtIME',align:'center',formatter:ChangeDateFormat,resizable:false,sortable:true" width="15%" >申请时间</th>
									<th data-options="field:'fToFilesStatus',align:'center',resizable:false,sortable:true,formatter:archiving" width="10%">归档状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ1" width="10%">操作</th>
								</tr>
							</thead>
						</table>
					</div>
					<div style="height: 440px">
						<table id="ArchivingUpdateOrendingTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/Archiving/ChangeJsonPagination',
						method:'post',fit:true,pagination:true,singleSelect: true,selectOnCheck: true,
						checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true">
							<thead>
								<tr>
									<th data-options="field:'ck',checkbox:true"></th>
									<th data-options="field:'fcId',hidden:true"></th>
									<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
									<th data-options="field:'fContCode',align:'center',resizable:false,sortable:true" width="15%">变更合同编号</th>
									<th data-options="field:'fContCodeOld',align:'center',resizable:false,sortable:true" width="15%">原合同编号</th>
									<th data-options="field:'fContNameold',align:'center',resizable:false,sortable:true" width="15%">原合同名称</th>
									<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="10%">申请部门</th>
									<th data-options="field:'fOperator',align:'center',resizable:false,sortable:true" width="10%">申请人</th>
									<th data-options="field:'fReqTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >合同签订日期</th>
									<th data-options="field:'fToFilesStatus',align:'center',resizable:false,sortable:true,formatter:archiving" width="10%">归档状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ2" width="10%">操作</th>
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
flashtab('contract-archiving-tab');
//操作栏创建
function CZ(val, row) {
	return	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="ledger_detail(' + row.fcId + ')" class="easyui-linkbutton">'+
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

//操作栏创建
function CZ1(val, row) {
	if(row.fToFilesStatus==0){
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailInfo(' + row.fcId + ',0)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		//归档操作
		'<td style="width: 25px">'+
		'<a href="#" onclick="ArchivingHT(' + row.fcId + ',0)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/archiving1.png">'+
		'</a></td>'+
		'</tr></table>';
	}else {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailInfo(' + row.fcId + ',0)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td></tr></table>';
	}
}
function CZ2(val, row) {
	if(row.fToFilesStatus==0){
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailInfo(' + row.fId_U + ',1)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		//归档操作
		'<td style="width: 25px">'+
		'<a href="#" onclick="ArchivingHT(' + row.fId_U + ',1)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/archiving1.png">'+
		'</a></td>'+
		'</tr></table>';
	}else {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailInfo(' + row.fId_U + ',1)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td></tr></table>';
	}
}
function ArchivingContractClick(){
	$("#ArchivingcontractTab").datagrid('reload');
	$("#Archiving_contract_top").show();
	$("#Archiving_UpdateOrending_top").hide();
}
function ArchivingUpdateOrendingClick(){
	$("#ArchivingUpdateOrendingTab").datagrid('reload');
	$("#Archiving_contract_top").hide();
	$("#Archiving_UpdateOrending_top").show();
}
function ArchivingQueryContract() {
	var deptName="Archiving_contract_name";
	$("#ArchivingcontractTab").datagrid('load',{
		searchTitle:$("#"+deptName).textbox('getValue').trim(),
	});
}
function ArchivingClearContract() {
	var fcCode="Archiving_contract_code";
	var fcTitle="Archiving_contract_name";
	$("#"+fcCode).textbox('setValue','');
	$("#"+fcTitle).textbox('setValue','');
	$("#ArchivingcontractTab").datagrid('load',{});
}
function ArchivingQueryUpdateOrending() {
	var fcTitle="Archiving_UpdateOrending_name";
	$("#ArchivingUpdateOrendingTab").datagrid('load',{
		searchTitle:$("#"+fcTitle).textbox('getValue').trim(),
	});
}
function ArchivingClearUpdateOrending() {
	var rCode="Archiving_UpdateOrending_code";
	var fcTitle="Archiving_UpdateOrending_name";
	$("#"+rCode).textbox('setValue','');
	$("#"+fcTitle).textbox('setValue','');
	$("#ArchivingUpdateOrendingTab").datagrid('load',{});
}
//是否归档
function archiving(val, row) {
	var c = row.fToFilesStatus;
	if (c == '1') {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已归档" + '</span>';
	} else if (c == '0') {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未归档" + '</span>';
	}
}
function addCF() {
	var win = creatWin('合同归档', 930, 650, 'icon-search',"/Archiving/fProCodejsonPagination/");
	win.window('open');
}
function detailInfo(id,type) {
	var win = creatWin('合同归档明细', 800, 650, 'icon-search',"/Archiving/detail/" + id+'?type='+type);
	win.window('open');
}
function ArchivingHT(id,type) {
	//var row = $('#archiving_dg').datagrid('getSelected');
	//var selections = $('#archiving_dg').datagrid('getSelections');
	var win = creatWin('合同归档申请', 800, 650, 'icon-search',"/Archiving/edit/" + id+'?type='+type);
	win.window('open');
}
</script>
</body>
</html>

