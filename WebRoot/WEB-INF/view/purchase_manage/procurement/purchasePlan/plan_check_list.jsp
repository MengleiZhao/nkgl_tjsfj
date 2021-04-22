<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="purchase_plan_check_dg" class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryPurchasePlan();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearPurchasePlan();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="purchase_plan_check_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/purchasePlanCheck/pageData',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fPurchaseCode',align:'center'" style="width: 10%">采购项目编号</th>
					<th data-options="field:'fPurchaseName',align:'center',resizable:false,sortable:true" style="width: 15%">采购项目名称</th>
					<th data-options="field:'fPurchaseWay',align:'center',resizable:false,sortable:true" style="width: 10%">采购方式</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 10%">预算金额</th>
					<th data-options="field:'agency',align:'center',resizable:false,sortable:true" style="width: 10%">代理机构</th>
					<th data-options="field:'projectLeaderName',align:'center',resizable:false,sortable:true" style="width: 8%">项目负责人</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 8%">业务发起人</th>
					<th data-options="field:'processLeaderName',align:'center',resizable:false,sortable:true" style="width: 8%">采购流程负责人</th>
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 10%">申请时间</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 10%">申请人</th>
					<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:formatStatus" style="width: 9%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryPurchasePlan() {
	var searchData="searchData";
	$('#purchase_plan_check_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearPurchasePlan() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#purchase_plan_check_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//设置审批状态
var c;
function formatStatus(val, row) {
	c = val;
	if (val == '0') {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == '-1') {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if (val =='-4') {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</a>';
	} else if (val == '9') {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	}
}

//操作栏创建
function CZ(val, row) {
	var titlebegin='<table><tr style="width: 75px;height:20px">';
	//查看按钮
	var button1='<td style="width: 25px">'+
	   '<a href="#" onclick="purchase_plan_detail(\'' + row.fpCode + '\')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td>';
	//审核按钮
	var button2='<td style="width: 25px">'+
		'<a href="#" onclick="purchase_plan_check(' + row.fId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/check1.png">'+
		'</a></td>';
	//打印按钮
	/* var button5='<td style="width: 25px">'+
	'<a href="#" onclick="exportHtml(' + row.fpId + ')" class="easyui-linkbutton">'+
	'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
   '</a></td>'; */
	var titleend='</tr></table>';
	if (c == 9) {
		var returnButton=titlebegin+button1+titleend;
		return returnButton;
	} else {
		return titlebegin+button1+button2+titleend;
	}
}

//修改
function purchase_plan_check(id) {
	var win = creatWin('审批', 1070, 580, 'icon-search', '/purchasePlanCheck/check?id='+id);
	win.window('open');
}
//查看
function purchase_plan_detail(fpCode) {
	var win = creatWin('查看', 1070, 580, 'icon-search', '/purchasePlan/edit?editType=0&fpCode='+fpCode);
	win.window('open');
}

//打印
function exportHtml(id) {
	window.open(base+"/exportCg/apply?id="+ id);
}
</script>
</body>
</html>