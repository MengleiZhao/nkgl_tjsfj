<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body >
<div class="list-div">
<div style="height: 10px;background-color:#f0f5f7 "></div>
<div class="list-top">
	<table class="topTable" style="width: 100%;font-size: 12px;height: 40px;"  cellpadding="0" cellspacing="0">
			<tr id="directly_ledger_top">
				<td class="top-table-search" class="queryth">
					 <input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryDirectlyLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearDirectlyLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
				<td style="text-align: right; padding-right: 10px;">
					<a href="${base}/reimburse/export?rimeType=0">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu1.png')"/>
					</a>
				</td>
			</tr>
				
			<tr id="reimburse_ledger_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<input id="searchDatashenqing" name="searchDatashenqing" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="queryReimburseLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearReimburseLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
				<td style="text-align: right; padding-right: 10px;">
					<a href="${base}/reimburse/export">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu1.png')"/>
					</a>
				</td>
			</tr>
			<tr id="contract_ledger_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<input id="searchDatahetong" name="searchDatahetong" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="querycontractLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearcontractLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
				<td style="text-align: right; padding-right: 10px;">
					<a href="${base}/reimburse/export?rimeType=8">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu1.png')"/>
					</a>
				</td>
			</tr>
			<tr id="current_ledger_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<input id="searchDatawanglai" name="searchDatawanglai" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="querycontractcurrent();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearcontractcurrent();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
				<td style="text-align: right; padding-right: 10px;">
					<a href="${base}/reimburse/export?rimeType=9">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu1.png')"/>
					</a>
				</td>
			</tr>
			<tr id="reimburse_goldPay_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<input id="searchDatabaozheng" name="searchDatahetong" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="querycontractgoldPay();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearcontractgoldPay();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
				<td style="text-align: right; padding-right: 10px;">
					<a href="${base}/reimburse/export?rimeType=11">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu1.png')"/>
					</a>
				</td>
			</tr>
			
	</table>   
</div>
	

<div class="list-table-tab">
		<div class="tab-wrapper" id="rc-ledger-tab">
			<ul class="tab-menu">
				<li class="active" onclick="directlyLedgerTopClick();">直接报销</li>
			    <li onclick="reimburseLedgerTopClick();">申请报销</li>
			    <li onclick="contractLedgerTopClick();">合同报销</li>
			    <li onclick="currentLedgerTopClick();">往来款报销</li>
			    <li onclick="goldPayCashierTopClick();">合同保证金报销</li>
			</ul>
				
			<div class="tab-content">
				<div style="height: 440px">
					<table id="directlyReimbLedgerTab" class="easyui-datagrid"
					data-options="collapsible:true,url:'${base}/reimburseLedger/reimbursePage?reimburseType=0',
					method:'post',fit:true,pagination:true,singleSelect: true,
					selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
						<thead>
							<tr>
								<th data-options="field:'drId',hidden:true"></th>
								<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
								<th data-options="field:'drCode',align:'center',resizable:false,sortable:true" style="width: 18%">单据编号</th>
								<th data-options="field:'type',align:'center',formatter:typeSet" style="width: 10%">报销类型</th>
								<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 16%">报销部门</th>
								<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 10%">报销申请人</th>
								<th data-options="field:'reqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 14%">报销时间</th>
								<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 10%">审批状态</th>
								<th data-options="field:'cashierType',align:'center',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 10%">付款状态</th>
								<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ1" style="width: 8%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div style="height: 440px">
					<table id="reimburseLedgerTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/reimburseLedger/reimbursePage?reimburseType=${reimburseType}',
						method:'post',fit:true,pagination:true,singleSelect: true,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'rId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
									<th data-options="field:'rCode',align:'center',resizable:false,sortable:true" style="width: 18%">单据编号</th>
									<th data-options="field:'type',align:'center',formatter:typeSet" style="width: 10%">报销类型</th>
									<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 15%">报销部门</th>
									<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 10%">报销申请人</th>
									<th data-options="field:'reimburseReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">报销时间</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 10%">审批状态</th>
									<th data-options="field:'cashierType',align:'center',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 10%">付款状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ2" style="width: 8%">操作</th>
								</tr>
							</thead>
						</table>
				</div>
				<div style="height: 440px">
					<table id="contractreimburseLedgerTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/reimburseLedger/reimbursePage?reimburseType=8',
						method:'post',fit:true,pagination:true,singleSelect: true,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'rId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
									<th data-options="field:'rCode',align:'center',resizable:false,sortable:true" style="width: 15%">单据编号</th>
									<th data-options="field:'contCode',align:'center'" width="15%">合同编号</th>
									<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="17%">合同名称</th>
									<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">付款金额(元)</th>
									<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 10%">报销申请人</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 10%">审批状态</th>
									<th data-options="field:'cashierType',align:'center',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 10%">付款状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ2" style="width: 8%">操作</th>
								</tr>
							</thead>
						</table>
				</div>
				
				<div style="height: 440px">
					<table id="current_ledger_dg" class="easyui-datagrid"
							data-options="collapsible:true,url:'${base}/reimburseLedger/reimbursePage?reimburseType=9',
							method:'post',fit:true,pagination:true,singleSelect: true,
							selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
						<thead>
							<tr>
								<th data-options="field:'gId',hidden:true"></th>
								<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
								<th data-options="field:'gCode',align:'center',resizable:false" style="width: 20%">单据编号</th>
								<th data-options="field:'gName',align:'center',resizable:false" style="width: 20%">摘要</th>
								<th data-options="field:'amount',align:'center',resizable:false,formatter: BaoxiaochaoeLedger" style="width: 10%">报销金额(元)</th>
								<th data-options="field:'userName',align:'center',resizable:false" style="width: 10%">报销申请人</th>
								<th data-options="field:'reimburseReqTime',align:'center',resizable:false,formatter: ChangeDateFormat" style="width: 11%">报销日期</th>
								<th data-options="field:'flowStauts',align:'center',resizable:false,formatter:flowStautsSet" style="width: 10%">审批状态</th>
								<th data-options="field:'cashierType',align:'center',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 10%">付款状态</th>
								<th data-options="field:'name',align:'center',resizable:false,formatter:CZ3" style="width: 6.7%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div style="height: 440px">
					<table id="goldPayReimbCashierTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/reimburseLedger/reimbursePage?reimburseType=11',
						method:'post',fit:true,pagination:true,singleSelect: true,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'rId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
									<th data-options="field:'rCode',align:'center',resizable:false,sortable:true" style="width: 15%">单据编号</th>
									<th data-options="field:'contCode',align:'center'" width="15%">合同编号</th>
									<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="20%">合同名称</th>
									<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">付款金额(元)</th>
									<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 10%">报销申请人</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 8%">审批状态</th>
									<th data-options="field:'cashierType',align:'center',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 8%">受理状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ2" style="width: 8%">操作</th>
								</tr>
							</thead>
						</table>
				</div>
				
			</div>
		
		</div>
	</div>
</div>

	<%-- <div class="list-table-tab">
		<div title="直接报销">
		<div style="height: 440px">
			<table id="directlyReimbCheckTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburseLedger/reimbursePage?reimburseType=0',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'drId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'drCode',align:'left',resizable:false,sortable:true" style="width: 15%">报销单编号</th>
						<th data-options="field:'type',align:'left',formatter:typeSet" style="width: 13%">报销类型</th>
						<th data-options="field:'deptName',align:'left',resizable:false,sortable:true" style="width: 13%">报销部门</th>
						<th data-options="field:'userName',align:'left',resizable:false,sortable:true" style="width: 13%">报销请人</th>
						<th data-options="field:'reqTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 13%">报销时间</th>
						<th data-options="field:'flowStauts',align:'left',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 13%">审批状态</th>
						<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:CZ1" style="width: 15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		</div>
		
		<div title="申请报销">
		<div style="height: 440px">
			<table id="reimburseCheckTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburseLedger/reimbursePage?reimburseType=${reimburseType}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'rId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'rCode',align:'left',resizable:false,sortable:true" style="width: 15%">报销单编号</th>
						<th data-options="field:'type',align:'left',formatter:typeSet" style="width: 13%">报销类型</th>
						<th data-options="field:'deptName',align:'left',resizable:false,sortable:true" style="width: 13%">报销部门</th>
						<th data-options="field:'userName',align:'left',resizable:false,sortable:true" style="width: 13%">报销请人</th>
						<th data-options="field:'reimburseReqTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 13%">报销时间</th>
						<th data-options="field:'flowStauts',align:'left',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 13%">审批状态</th>
						<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:CZ2" style="width: 15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		</div>
		
	</div> --%>



<script type="text/javascript">
//加载tab页
flashtab('rc-ledger-tab');

//设置申请事项
function typeSet(val, row) {
	if (val == 0) {
		return '<span>' + "直接报销" + '</span>';
	} else if (val == 1) {
		return '<span>' + "通用事项报销" + '</span>';
	} else if (val == 2) {
		return '<span>' + "会议报销" + '</span>';
	} else if (val == 3) {
		return '<span>' + "培训报销" + '</span>';
	} else if (val == 4) {
		return '<span>' + "差旅报销" + '</span>';
	} else if (val == 5) {
		return '<span>' + "公务接待报销" + '</span>';
	} else if (val == 9) {
		return '<span>' + "合同报销" + '</span>';
	} else if (val == 6) {
		return '<span>' + "公务用车报销" + '</span>';
	} else if (val == 7) {
		return '<span>' + "公务出国报销" + '</span>';
	}
}


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
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审批" + '</a>';
	}
}

//操作栏创建
function CZ1(val, row) {
	return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	   '<a href="#" onclick="editReimburse1(' + row.drId + ',0)" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td></tr></table>';
}
function CZ2(val, row) {
	return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	   '<a href="#" onclick="editReimburse2(' + row.rId + ',0,'+row.type+')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td></tr></table>';
}
function showA(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
}
function showB(obj){
	obj.src=base+'/resource-modality/${themenurl}/select2.png';
}


//查看
function editReimburse1(id,type) {
	/*type为修改或查看1位修改，0位查看  */
	var win = creatWin(' ', 1070,580, 'icon-search', "/directlyReimburse/edit?id="+ id+"&editType="+type);
	win.window('open');
}
function editReimburse2(id,type,reimburseType) {
	/*type为修改或查看1位修改，0位查看  */
	var win = null;
	if(reimburseType=='1'){
		win = creatWin(' ', 1095, 580, 'icon-search', "/reimburse/edit?id="+ id+"&editType="+type);
	}else {
		win = creatWin(' ', 1095, 580, 'icon-search', "/reimburse/edit?id="+ id+"&editType="+type);
	}
	win.window('open');
}

function directlyLedgerTopClick(){
	$("#directlyReimbLedgerTab").datagrid('reload');
	
	$("#directly_ledger_top").show();
	$("#reimburse_ledger_top").hide();
	$("#contract_ledger_top").hide();
	$("#reimburse_goldPay_top").hide();
	$("#current_ledger_top").hide();
}
function reimburseLedgerTopClick(){
	$("#reimburseLedgerTab").datagrid('reload');
	$("#directly_ledger_top").hide();
	$("#contract_ledger_top").hide();
	$("#current_ledger_top").hide();
	$("#reimburse_ledger_top").show();
	$("#reimburse_goldPay_top").hide();
}
function contractLedgerTopClick(){
	$("#contractreimburseLedgerTab").datagrid('reload');
	$("#directly_ledger_top").hide();
	$("#reimburse_ledger_top").hide();
	$("#current_ledger_top").hide();
	$("#contract_ledger_top").show();
	$("#reimburse_goldPay_top").hide();
}
function currentLedgerTopClick(){
	$("#current_ledger_dg").datagrid('reload');
	$("#directly_ledger_top").hide();
	$("#reimburse_ledger_top").hide();
	$("#contract_ledger_top").hide();
	$("#current_ledger_top").show();
	$("#reimburse_goldPay_top").hide();
}

function goldPayCashierTopClick(){
	$("#goldPayReimbCashierTab").datagrid('reload');
	
	$("#directly_ledger_top").hide();
	$("#reimburse_ledger_top").hide();
	$("#contract_ledger_top").hide();
	$("#current_ledger_top").hide();
	$("#reimburse_goldPay_top").show();
}
//直接报销查询
function queryDirectlyLedger() {
	/* var drCode="directly_ledger_list_top_code";
	var deptName="directly_ledger_list_top_deptName"; */
	var searchData="searchData";
	
	$("#directlyReimbLedgerTab").datagrid('load',{
		/* drCode:$("#"+drCode).textbox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(), */
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}

//直接报销清除查询条件
function clearDirectlyLedger() {
	/* var drCode="directly_ledger_list_top_code";
	var deptName="directly_ledger_list_top_deptName"; */
	var searchData="searchData";
	/* $("#"+drCode).textbox('setValue','');
	$("#"+deptName).textbox('setValue',''); */
	$("#"+searchData).textbox('setValue','');
	$("#directlyReimbLedgerTab").datagrid('load',{});
}

//申请报销查询
function queryReimburseLedger() {
	/* var rCode="reimburse_ledger_list_top_code";
	var type="reimburse_ledger_list_top_type";
	var deptName="reimburse_ledger_list_top_deptName"; */
	var searchDatashenqing="searchDatashenqing";
	
	$("#reimburseLedgerTab").datagrid('load',{
		/* rCode:$("#"+rCode).textbox('getValue').trim(),
		type:$("#"+type).combobox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(), */
		searchData:$("#"+searchDatashenqing).textbox('getValue').trim(),
	});
}

//申请报销清除查询条件
function clearReimburseLedger() {
	/* var rCode="reimburse_ledger_list_top_code";
	var type="reimburse_ledger_list_top_type";
	var deptName="reimburse_ledger_list_top_deptName"; */
	var searchDatashenqing="searchDatashenqing";
	/*$ ("#"+rCode).textbox('setValue','');
	$("#"+type).textbox('setValue','');
	$("#"+deptName).textbox('setValue',''); */
	$("#"+searchDatashenqing).textbox('setValue','');
	$("#reimburseLedgerTab").datagrid('load',{});
}
//合同报销查询
function querycontractLedger() {
	/* var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName"; */
	var searchDatahetong="searchDatahetong";
	$("#contractreimburseLedgerTab").datagrid('load',{
		/* rCode:$("#"+rCode).textbox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(), */
		searchData:$("#"+searchDatahetong).textbox('getValue').trim(),
	});
}
//合同报销清除查询条件
function clearcontractLedger() {
	/* var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName"; */
	var searchDatahetong="searchDatahetong";
	/* $("#"+rCode).textbox('setValue','');
	$("#"+deptName).textbox('setValue',''); */
	$("#"+searchDatahetong).textbox('setValue','');
	$("#contractreimburseLedgerTab").datagrid('load',{});
}
//往来款报销查询
function querycontractcurrent() {
	/* var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName"; */
	var searchDatahetong="searchDatawanglai";
	$("#current_ledger_dg").datagrid('load',{
		/* rCode:$("#"+rCode).textbox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(), */
		searchData:$("#"+searchDatahetong).textbox('getValue').trim(),
	});
}
//往来款报销清除查询条件
function clearcontractcurrent() {
	/* var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName"; */
	var searchDatahetong="searchDatawanglai";
	/* $("#"+rCode).textbox('setValue','');
	$("#"+deptName).textbox('setValue',''); */
	$("#"+searchDatahetong).textbox('setValue','');
	$("#current_ledger_dg").datagrid('load',{});
}

//保证金报销查询
function querycontractgoldPay() {
	/* var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName"; */
	var applyString="searchDatabaozheng";
	$("#goldPayReimbCashierTab").datagrid('load',{
		/* rCode:$("#"+rCode).textbox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(), */
		applyString:$("#"+applyString).textbox('getValue').trim(),
	});
}
//保证金报销清除查询条件
function clearcontractgoldPay() {
	/* var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName"; */
	var applyString="searchDatabaozheng";
	/* $("#"+rCode).textbox('setValue','');
	$("#"+deptName).textbox('setValue',''); */
	$("#"+applyString).textbox('setValue','');
	$("#goldPayReimbCashierTab").datagrid('load',{});
}
//导出 
function reimburseLedgerDochu() {
	var url=base+"/reimburse/export";
	
	
	
}
function cashierTypeSet(val, row) {
	if (val == 0||val == ''||val == null) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;'
				+ "未付讫" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;'
				+ "已付讫" + '</a>';
	}
}

//往来款报销台账

function BaoxiaochaoeLedger(val, row){
	
	var a = row.amount;
	if(val>a){
		return '<span style="color:red">'+fomatMoney(val,2)+"【报销超额】"+'</span>';
	}else{
		return fomatMoney(val,2);
	}
}
//操作栏创建
function CZ3(val, row) {
		return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			   '<a href="#" onclick="detailCurrentLedger(' + row.rId + ')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   '</a></td></tr></table>';
}
//查看
function detailCurrentLedger(id){
	win = creatWin('查看', 1115, 600, 'icon-search', '/reimburse/detailCurrent?id='+id+'&editType=1');
	win.window('open');
}
</script>
</body>
</html>

