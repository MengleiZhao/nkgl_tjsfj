<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body >
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
	<table class="topTable" style="width: 100%;font-size: 12px;height: 40px;"  cellpadding="0" cellspacing="0">
			<tr id="reimburse_ledger_top" >
				<td class="top-table-search" >
					&nbsp;&nbsp;报销单编号&nbsp;
					<input id="reimburse_ledger_list_top_code" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销类型&nbsp;
					<select id="reimburse_ledger_list_top_type" class="easyui-combobox" style="width: 150px;height:25px;">
						<option value="">--请选择--</option>
						<option value="1">通用事项报销</option>
						<option value="2">会议报销</option>
						<option value="3">培训报销</option>
						<option value="4">差旅报销</option>
						<option value="5">公务接待报销</option>
						<option value="6">公务用车报销</option>
						<option value="7">公务出国报销</option>
					</select>
					
					&nbsp;&nbsp;报销部门&nbsp;
					<input id="reimburse_ledger_list_top_deptName" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryReimburseLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearReimburseLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				<td align="right" style="padding-right: 10px;width:70px;">
					<a href="javascript:void(0)"  onclick="window.location.href='${base}/cockpit/list';">
						<img src="${base}/resource-modality/${themenurl}/button/tuihui1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				<%-- <td style="text-align: right; padding-right: 10px;">
					<a href="${base}/reimburse/export">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu1.png')"/>
					</a>
				</td> --%>
			</tr>
	</table>   
</div>
	
<div class="list-table-tab">
	<div style="height: 580px">
		<table id="reimburseLedgerTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburseLedger/reimburseCockpitPage?reimburseType=${reimburseType}&type=${reimburseType}&rows=10000&subCode=${subCode }',
			method:'post',fit:true,pagination:false,singleSelect: true,selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'gId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'gCode',align:'center',resizable:false,sortable:true" style="width: 23%">报销单编号</th>
						<th data-options="field:'type',align:'center',formatter:typeSet" style="width: 10%">报销类型</th>
						<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 15%">报销部门</th>
						<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 15%">报销申请人</th>
						<th data-options="field:'reqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">报销时间</th>
						<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 10%">审批状态</th>
						<!-- <th data-options="field:'cashierType',align:'center',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 10%">付款状态</th> -->
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ2" style="width: 8%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">
//加载tab页
flashtab('rc-ledger-tab');

$(function(){
	if('${reimburseType}' != null&&'${reimburseType}' != ''&&'${reimburseType}' != undefined){
		$('#reimburse_ledger_list_top_type').combobox('select',${reimburseType});
		$('#reimburse_ledger_list_top_type').combobox('readonly',true);
		/* queryReimburseLedger(); */
	}
});

//设置申请事项
function typeSet(val, row) {
	if (val == 0) {
		return '<span>' + "直接报销" + '</span>';
	} else if (val == 1) {
		return '<span>' + "通用事项申请" + '</span>';
	} else if (val == 2) {
		return '<span>' + "会议申请" + '</span>';
	} else if (val == 3) {
		return '<span>' + "培训申请" + '</span>';
	} else if (val == 4) {
		return '<span>' + "差旅申请" + '</span>';
	} else if (val == 5) {
		return '<span>' + "公务接待申请" + '</span>';
	} else if (val == 9) {
		return '<span>' + "合同申请" + '</span>';
	} else if (val == 6) {
		return '<span>' + "公务用车申请" + '</span>';
	} else if (val == 7) {
		return '<span>' + "公务出国申请" + '</span>';
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

function CZ2(val, row) {
	if(row.type=='0'){
		return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		   '<a href="#" onclick="editReimburse1(' + row.gId + ',0)" class="easyui-linkbutton">'+
		   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		   '</a></td></tr></table>';
	}else{
	return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	   '<a href="#" onclick="editReimburse2(' + row.gId + ',0,'+row.type+')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td></tr></table>';
	}
}


//查看
function editReimburse1(id,type) {
	/*type为修改或查看1位修改，0位查看  */
	var win = creatWin(' ', 1115,580, 'icon-search', "/directlyReimburse/edit?id="+ id+"&editType="+type);
	win.window('open');
}
function editReimburse2(id,type,reimburseType) {
	/*type为修改或查看1位修改，0位查看  */
	var win = null;
	if(reimburseType=='1'){
		win = creatWin(' ', 1115, 600, 'icon-search', "/apply/edit?id="+ id+"&editType="+type);
	}else {
		win = creatWin(' ', 1115, 580, 'icon-search', "/apply/edit?id="+ id+"&editType="+type);
	}
	win.window('open');
}

/* function directlyLedgerTopClick(){
	$("#directlyReimbLedgerTab").datagrid('reload');
	
	$("#directly_ledger_top").show();
	$("#reimburse_ledger_top").hide();
	$("#contract_ledger_top").hide();
}
function reimburseLedgerTopClick(){
	$("#reimburseLedgerTab").datagrid('reload');
	$("#directly_ledger_top").hide();
	$("#contract_ledger_top").hide();
	$("#reimburse_ledger_top").show();
}
function contractLedgerTopClick(){
	$("#contractreimburseLedgerTab").datagrid('reload');
	$("#directly_ledger_top").hide();
	$("#reimburse_ledger_top").hide();
	$("#contract_ledger_top").show();
} */

//直接报销查询
function queryDirectlyLedger() {
	var drCode="directly_ledger_list_top_code";
	var deptName="directly_ledger_list_top_deptName";
	
	
	$("#directlyReimbLedgerTab").datagrid('load',{
		drCode:$("#"+drCode).textbox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(),
	});
}

//直接报销清除查询条件
function clearDirectlyLedger() {
	var drCode="directly_ledger_list_top_code";
	var deptName="directly_ledger_list_top_deptName";
	
	$("#"+drCode).textbox('setValue','');
	$("#"+deptName).textbox('setValue','');
	
	$("#directlyReimbLedgerTab").datagrid('load',{});
}

//申请报销查询
function queryReimburseLedger() {
	var rCode="reimburse_ledger_list_top_code";
	var type="reimburse_ledger_list_top_type";
	var deptName="reimburse_ledger_list_top_deptName";
	
	
	$("#reimburseLedgerTab").datagrid('load',{
		rCode:$("#"+rCode).textbox('getValue').trim(),
		type:$("#"+type).combobox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(),
	});
}
//申请报销清除查询条件
function clearReimburseLedger() {
	var rCode="reimburse_ledger_list_top_code";
	var type="reimburse_ledger_list_top_type";
	var deptName="reimburse_ledger_list_top_deptName";
	
	$("#"+rCode).textbox('setValue','');
	$("#"+type).textbox('setValue','');
	$("#"+deptName).textbox('setValue','');
	
	$("#reimburseLedgerTab").datagrid('load',{});
}
/* //申请报销查询
function querycontractLedger() {
	var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName";
	
	
	$("#contractreimburseLedgerTab").datagrid('load',{
		rCode:$("#"+rCode).textbox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(),
	});
} */
/* //申请报销清除查询条件
function clearcontractLedger() {
	var rCode="contract_ledger_list_top_code";
	var deptName="contract_ledger_list_top_deptName";
	
	$("#"+rCode).textbox('setValue','');
	$("#"+deptName).textbox('setValue','');
	
	$("#contractreimburseLedgerTab").datagrid('load',{});
} */

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
</script>
</body>
</html>

