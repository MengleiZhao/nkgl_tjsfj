<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">卡片编号
						<input id="ledger_fixed_fAssCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;卡片名称&nbsp;
						<input id="ledger_fixed_fAssName" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						<!-- &nbsp;&nbsp;品牌简称&nbsp;
						<input id="ledger_fixed_fComptySort" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input> -->
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ledger_fixed_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ledger_fixed_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
						
					</td>
					<%-- <td class="top-table-td1">资产编号：</td> 
					<td class="top-table-td2">
						<input id="ledger_fixed_fAssCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td class="top-table-td1">资产名称：</td> 
					<td class="top-table-td2">
						<input id="ledger_fAssName" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td class="top-table-td1">资产类型：</td> 
					<td class="top-table-td2">
						 <input id="ledger_fAssType" name="" style="width: 150px;height:25px;" data-options="url:'${base}/Handle/lookupsJson?parentCode=ZCLX',method:'POST',valueField:'code',textField:'text',editable:false" class="easyui-combobox"></input>
					</td>
					<td class="top-table-td1">规格型号：</td> 
					<td class="top-table-td2">
						<input id="ledger_fSPModel" name="" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td style="width: 12px">
					</td>
					<td style="width: 24px;">
						<a href="javascript:void(0)"  onclick="ledger_fixed_query();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
					</td>
					<td style="width: 12px">
					</td>
					<td >
						<a href="javascript:void(0)"  onclick="ledger_fixed_clearTable();"><img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')"></a>
					</td>
					<td style="width: 12px">
					</td>
					<td align="right">
						<a href="#" onclick="addCF()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td>
					<td style="width: 14px">
					</td> --%>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
				
		</div>
		
		<div class="list-table">
			<table id="ledger_fixed_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/AssetsLedger/JsonPagination?fAssType=ZCLX-02',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fAssId',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssCode',align:'center',resizable:false,sortable:true" width="9%"> 卡片编号</th>
						<th data-options="field:'fAssName',align:'center',resizable:false,sortable:true" width="15%">资产名称</th>
						<th data-options="field:'fSPModel',align:'center',resizable:false,sortable:true" width="10%">型号</th>
						<th data-options="field:'fActionDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >取得时间</th>
						<th data-options="field:'fFinancialDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >财务入账时间</th>
						<th data-options="field:'fUsedStautsShow',align:'center',resizable:false,sortable:true" width="8%" >使用状态</th>
						<th data-options="field:'fAvailableStautsShow',align:'center',resizable:false,sortable:true" width="8%" >可用状态</th>
						<th data-options="field:'fUseName',align:'center',resizable:false,sortable:true" width="8%" >使用人名称</th>
						<th data-options="field:'fReceDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >最近一次领用时间</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="8%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
//清除查询条件
function ledger_fixed_clearTable() {
	$('#ledger_fixed_fAssCode').textbox('setValue',null);
	$('#ledger_fixed_fAssName').textbox('setValue',null);
	ledger_fixed_query();
}
var fs
function formatPrice(val, row) {
	fs=val;
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</span>';
	}else if (val == 9) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</span>';
	}else if (val == 99) {
		return '<span style="color:#666666;">' + " 已删除" + '</span>';
	} else if (val == -1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</span>';
	}
}
function CZ(val, row) {
	return '<a href="#" onclick="ledger_fixed_detail(' + row.fAssId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
}
function ledger_fixed_query() {
	$('#ledger_fixed_dg').datagrid('load', {
		fAssCode : $('#ledger_fixed_fAssCode').val(),
		fAssName : $('#ledger_fixed_fAssName').val(),
	});
}
function ledger_fixed_detail(id) {
		var win = creatWin('查看', 970,590, 'icon-search',"/AssetsLedger/fixedDetail/" + id);
		win.window('open');
}
function Alloca_update(id) {
	//var row = $('#ledger_fixed_dg').datagrid('getSelected');
	var win = creatWin('修改', 970,580, 'icon-search',
			"/Alloca/edit/" + id);
	win.window('open');
}
</script>
</body>
</html>

