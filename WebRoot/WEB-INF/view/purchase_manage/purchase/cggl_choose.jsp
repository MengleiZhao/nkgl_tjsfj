<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="purchase_intention_pub_dg" class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryPurchaseIntention();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearPurchaseIntention();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="purchase_intention_pub_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgsqsp/pageData',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'fId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fIntentionCode',align:'center'" style="width: 20%">意向公开项目编号</th>
					<th data-options="field:'fPurchaseName',align:'center',resizable:false,sortable:true" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 15%">申请处室</th>
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 12%">申请时间</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">预算金额</th>
					<th data-options="field:'publicStatus',align:'center',resizable:false,sortable:true,formatter:formatStatus" style="width: 14%">公开状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryPurchaseIntention() {
	var searchData="searchData";
	$('#purchase_intention_pub_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearPurchaseIntention() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#purchase_intention_pub_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//设置公开状态
function formatStatus(val, row) {
	if (val == '0') {
		return '未公开';
	} else if (val == '1') {
		return '已公开';
	} else {
		return val;
	}
}

$(function(){
	initDg_hotel_add();
});

//初始化datagrid点击事件
function initDg_hotel_add(){
	$('#purchase_intention_pub_Tab').datagrid({
		onDblClickRow:function(index,row){
			if ('fromBxsq'=='${menuType}') {
				//申请报销-选择借款单
			}
			$('#f_openObjCode').textbox('setValue',row.fIntentionCode);
			$('#F_fpNameTwo').textbox('setValue',row.fPurchaseName);
			closeFirstWindow();
		}
	});
}
</script>
</body>
</html>