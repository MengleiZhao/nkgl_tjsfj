<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="purchase_intention_dg" class="top-table" cellpadding="0" cellspacing="0">
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
					<a href="#" onclick="addPurchaseIntention()">
						<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="purchase_intention_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/purchaseIntention/pageData',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fIntentionCode',align:'center'" style="width: 20%">意向公开编号</th>
					<th data-options="field:'fPurchaseName',align:'center',resizable:false,sortable:true" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 10%">申请处室</th>
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 12%">申请时间</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">预算金额</th>
					<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:formatStatus" style="width: 9%">审批状态</th>
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
	$('#purchase_intention_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearPurchaseIntention() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#purchase_intention_Tab').datagrid('load',{//清空以后，重新查一次
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
	//查看详情按钮
	var button1='<td style="width: 25px">'+
	   '<a href="#" onclick="purchase_intention_detail(' + row.fId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td>';
	//修改按钮
	var button2='<td style="width: 25px">'+
		'<a href="#" onclick="purchase_intention_update(' + row.fId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
		'</a></td>';
	//删除按钮
	var button3='<td style="width: 25px">'+
		'<a href="#" onclick="purchase_intention_delete(' + row.fId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
		'</a></td>';
	//撤回按钮
	var button4='<td style="width: 25px">'+
		'<a href="#" onclick="reCall(\'purchase_intention_Tab\',' + row.fId + ',\'/purchaseIntention/reCall\')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
		'</a></td>';
	//打印按钮
	/* var button5='<td style="width: 25px">'+
	'<a href="#" onclick="exportHtml(' + row.fpId + ')" class="easyui-linkbutton">'+
	'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
   '</a></td>'; */
	var titleend='</tr></table>';
	
	if (c == 9  || c == 2) {
		var returnButton=titlebegin+button1;//+button5
		return returnButton+titleend;
	}else if (c == 1) {
			var returnButton=titlebegin+button1+button4;
			return returnButton+titleend;
	} else if(c == 0 || c == -1 || c==-4) {
		return titlebegin+button1+button2+button3+titleend;
	}
}

//新增
function addPurchaseIntention() {
	var win = creatWin('新增', 1070, 580, 'icon-search', '/purchaseIntention/add');
	win.window('open');
}
//删除
function purchase_intention_delete(id) {
	if (confirm('确认删除吗？')) {
		$.ajax({
			type : 'POST',
			url : '${base}/purchaseIntention/delete?id='+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#purchase_intention_Tab').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
//修改
function purchase_intention_update(id) {
	var win = creatWin('修改', 1070, 580, 'icon-search', '/purchaseIntention/edit?editType=1&id='+id);
	win.window('open');
 	}
//查看
function purchase_intention_detail(id) {
	var win = creatWin('查看', 1070, 580, 'icon-search', '/purchaseIntention/edit?editType=0&id='+id);
	win.window('open');
}

//打印
function exportHtml(id) {
	window.open(base+"/exportCg/apply?id="+ id);
}
</script>
</body>
</html>