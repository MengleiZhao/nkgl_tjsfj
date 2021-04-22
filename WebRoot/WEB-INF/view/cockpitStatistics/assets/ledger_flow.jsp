<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<%@ include file="../divbase.jsp"%>
<style type="text/css">
.textbox-readonly{
background-color: #ffffff;
    color: #999999;
}
.cstd1 {
    padding-left: 6px;
    font-size: 12px;
    font-weight: 400;
}
.cstd2 {
    padding-left: 6px;
    font-size: 12px;
    font-weight: 400;
}
.cstd3 {
    padding-left: 6px;
    font-size: 12px;
    font-weight: 400;
}
.cstd4 {
    padding-left: 6px;
    font-size: 12px;
    font-weight: 400;
}
.td1 {
    font-size: 12px;
}
</style>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr id="fixedquery" >
					<td class="top-table-search">资产编号
						<input id="ledger_fixed_fAssCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;资产名称&nbsp;
						<input id="ledger_fixed_fAssName" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;品牌简称&nbsp;
						<input id="ledger_fixed_fComptySort" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ledger_fixed_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ledger_fixed_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
						<%-- &nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="window.location.href='${base}/cockpit/list';">
							<img src="${base}/resource-modality/${themenurl}/button/tuihui1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a> --%>
					</td>
					<td align="right" style="padding-right: 10px;width:70px;">
						<a href="javascript:void(0)"  onclick="window.location.href='${base}/cockpit/list';">
							<img src="${base}/resource-modality/${themenurl}/button/tuihui1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
				<tr id="intangiblequery" hidden="hidden">
					<td class="top-table-search">资产编号
						<input id="ledger_intangible_fAssCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;资产名称&nbsp;
						<input id="ledger_intangible_fAssName" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;品牌简称&nbsp;
						<input id="ledger_intangible_fComptySort" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ledger_intangible_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ledger_intangible_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
						<%-- &nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="window.location.href='${base}/cockpit/list';">
							<img src="${base}/resource-modality/${themenurl}/button/tuihui1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a> --%>
					</td>
					<td align="right" style="padding-right: 10px;width:70px;">
						<a href="javascript:void(0)"  onclick="window.location.href='${base}/cockpit/list';">
							<img src="${base}/resource-modality/${themenurl}/button/tuihui1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
			</table>           
		</div>
		<div class="list-table" style="height:450px">
			<div class="tab-wrapper" id="ledger-flow-tab">
				<ul class="tab-menu">
					<li class="active" onclick="fixedClick();">固定资产</li>
				    <li onclick="intangibleClick();">无形资产</li>
				</ul>
				<div class="tab-content">
					<div style="height: 440px">
						<table id="ledger_fixed_flow" class="easyui-datagrid"
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
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ_fixed" width="8%">操作</th>
								</tr>
							</thead>
						</table>
					</div>
					<div style="height: 440px">
						<table id="ledger_intangible_flow" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/AssetsLedger/JsonPagination?fAssType=ZCLX-03',
						method:'post',fit:true,pagination:true,singleSelect: true,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'fAssId',hidden:true"></th>
									<th data-options="field:'number',align:'center'" width="5%">序号</th>
									<th data-options="field:'fAssCode',align:'left'" width="15%">卡片编号</th>
									<th data-options="field:'fAssName',align:'left',resizable:false,sortable:true" width="20%">资产名称</th>
									<th data-options="field:'fActionDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="15%" >取得时间</th>
									<th data-options="field:'fAdminOfficialShow',align:'center'" width="15%" >管理部门</th>
									<th data-options="field:'fFinancialDate',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="15%" >财务入账时间</th>
									<th data-options="field:'fUsedStautsShow',align:'center',resizable:false,sortable:true" width="10%" >使用状态</th>
									<!-- <th data-options="field:'fAvailableStautsShow',align:'center',resizable:false,sortable:true" width="10%" >可用状态</th> -->
									<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:CZ_intangible" width="11%">操作</th>
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
flashtab('ledger-flow-tab');

function fixedClick(){
	$('#intangiblequery').hide();
	$('#fixedquery').show();
	$('#ledger_fixed_flow').datagrid('reload');
}
function intangibleClick(){
	$('#intangiblequery').show();
	$('#fixedquery').hide();
	$('#ledger_intangible_flow').datagrid('reload');
}

/* 固定资产 */
function ledger_fixed_query() {
	$('#ledger_fixed_flow').datagrid('load', {
		fAssCode : $('#ledger_fixed_fAssCode').val(),
		fAssName : $('#ledger_fixed_fAssName').val(),
		fSPModel : $('#ledger_fixed_fComptySort').val(),
	});
}
function ledger_fixed_detail(id) {
	var win = creatWin('查看', 970,590, 'icon-search',"/AssetsLedger/fixedDetail/" + id);
	win.window('open');
}
//清除查询条件
function ledger_fixed_clearTable() {
	$('#ledger_fixed_fAssCode').textbox('setValue',null);
	$('#ledger_fixed_fAssName').textbox('setValue',null);
	$('#ledger_fixed_fComptySort').textbox('setValue',null);
	ledger_fixed_query();
}
function CZ_fixed(val, row) {
	return '<a href="#" onclick="ledger_fixed_detail(' + row.fAssId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>';
}

/* 无形资产 */

//清除查询条件
function ledger_intangible_clearTable() {
	$('#ledger_intangible_fAssCode').textbox('setValue',null);
	$('#ledger_intangible_fAssName').textbox('setValue',null);
	$('#ledger_intangible_fComptySort').textbox('setValue',null);
	ledger_intangible_query();
}
function CZ_intangible(val, row) {
	return '<a href="#" onclick="ledger_intangible_detail(' + row.fAssId+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>';
}
function ledger_intangible_query() {
	$('#ledger_intangible_flow').datagrid('load', {
		fAssCode : $('#ledger_intangible_fAssCode').val(),
		fAssName : $('#ledger_intangible_fAssName').val(),
		fComptySort : $('#ledger_intangible_fComptySort').val(),
	});
}
function ledger_intangible_detail(id) {
		var win = creatWin('查看', 970,580, 'icon-search',"/AssetsLedger/intangDetail/" + id);
		win.window('open');
}


</script>
</body>
</html>

