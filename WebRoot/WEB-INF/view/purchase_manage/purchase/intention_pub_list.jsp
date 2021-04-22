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
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="intentionExport();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/daochu1.png')"/>
					</a>
					<a href="#" onclick="intentionPub()">
						<img src="${base}/resource-modality/${themenurl}/button/gk1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="purchase_intention_pub_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/purchaseIntentionPub/pageData',
			method:'post',fit:true,pagination:true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'fId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fIntentionCode',align:'center'" style="width: 20%">意向公开编号</th>
					<th data-options="field:'fPurchaseName',align:'center',resizable:false,sortable:true" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 10%">申请处室</th>
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 12%">申请时间</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">预算金额</th>
					<th data-options="field:'publicStatus',align:'center',resizable:false,sortable:true,formatter:formatStatus" style="width: 9%">公开状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
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

//操作栏创建
function CZ(val, row) {
	var titlebegin='<table><tr style="width: 75px;height:20px">';
	//查看按钮
	var button1='<td style="width: 25px">'+
	   '<a href="#" onclick="purchase_intention_detail(' + row.fId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td>';
	//公开按钮
	var button2='<td style="width: 25px">'+
		'<a href="#" onclick="purchase_intention_pub(' + row.fId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/gk1.png">'+
		'</a></td>';
	var titleend='</tr></table>';

	var publicStatus = row.publicStatus;
	var returnButton=titlebegin+button1+titleend;
	if(publicStatus == '0'){
		returnButton=titlebegin+button1+button2+titleend;//+button5
	}
	return returnButton;
}

//批量公开
function intentionPub() {
	$.messager.progress();
	var rows = $('#purchase_intention_pub_Tab').datagrid('getChecked');
	if(rows.length==0){
		$.messager.progress('close');
		alert('请选择需要公开的数据！');
		return;
	}else {
		var entities= '';
		for( var i = 0;i < rows.length;i++){
			entities = entities  + rows[i].fId + ',';  
		}
		entities =entities.substring(0,entities.length -1);
		if (confirm('是否公开所选中的项目？')) {
			$.ajax({
				type : 'POST',
				url : '${base}/purchaseIntentionPub/batchPublic',
				data: {
					idlist:entities
				},
				//dataType : 'json',
				success : function(data) {
					data=eval("("+data+")");
					if (data.success) {
						$.messager.progress('close');
						$("#purchase_intention_pub_Tab").datagrid('reload');
					} else {
						$.messager.progress('close');
						$.messager.alert('系统提示', data.info, 'error');
					}
				},
				error:function () {
					$.messager.progress('close');
				}
			});
		}
	}
}
//公开确认
function purchase_intention_pub(id) {
	if (confirm('是否公开？')) {
		$.ajax({
			type : 'POST',
			url : '${base}/purchaseIntentionPub/intentionPublic?id='+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#purchase_intention_pub_Tab').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
//查看
function purchase_intention_detail(id) {
	var win = creatWin('查看', 1070, 580, 'icon-search', '/purchaseIntention/edit?editType=0&id='+id);
	win.window('open');
}
//导出 
function intentionExport() {
	var rows = $('#purchase_intention_pub_Tab').datagrid('getChecked');
	var entities= '';
	if(rows.length != 0){
		for( var i = 0;i < rows.length;i++){
			entities = entities  + rows[i].fId + ',';  
		}
		entities =entities.substring(0,entities.length -1);
	}
	
	var searchData = $('#searchData').textbox('getValue').trim()
	var url=base+"/purchaseIntentionPub/intentionExport?searchData="+searchData + "&ids=" + entities;
	window.location.href=url;
}
</script>
</body>
</html>