<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		

	<div style="margin: 0 10px 0 10px;height: 455px;">	
		<table id="ask_detail_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgAsk/cgAskPage?fpId=${fpId}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fAskTime',align:'center',formatter:ChangeDateFormat" style="width: 14%">质疑发起时间</th>
					<th data-options="field:'fRemark',align:'center',resizable:false,formatter:formatCellTooltip" style="width: 25%">质疑内容</th>
					<th data-options="field:'fAnswerStauts',align:'center',resizable:false,formatter:formatAnswerStatus" style="width: 15%">答复状态</th>
					<th data-options="field:'fUserName',align:'center',resizable:false" style="width: 15%">答复人</th>
					<th data-options="field:'fAnswerTime',align:'center',resizable:false,formatter:ChangeDateFormat" style="width: 14%">答复时间</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="15%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//操作栏创建
function CZ(val, row) {
	//审批状态
	var c = row.fAnswerStauts;
	if (c == 1) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="detailAnswer(' + row.fqId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			    '</a></td></tr></table>';
	} else  {
		if(row.fAskStauts==1){
			if(row.fCheckStauts==''||row.fCheckStauts==null||row.fCheckStauts==0){
				return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
						'<a href="#" onclick="detailAsk(' + row.fqId+ ')" class="easyui-linkbutton">'+
						'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
						'</a></td><td style="width: 25px">'+
						'<a href="#" onclick="reCallAsk(' + row.fqId+ ')" class="easyui-linkbutton">'+
						'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
						'</a></td></tr></table>';
			}else{
				return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="detailAsk(' + row.fqId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'</a></td></tr></table>';
			}
		}else{
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="detailAsk(' + row.fqId+ ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
					'</a></td><td style="width: 25px">'+
					'<a href="#" onclick="editAsk(' + row.fqId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
					'</a></td><td style="width: 25px">'+
					'<a href="#" onclick="deleteAsk(' + row.fqId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
					'</a></td></tr></table>';
		}
	} 
}

function formatAnswerStatus(val, row){
	if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已答复" + '</a>';
	}else if (val == 0){
		if(row.fAskStauts==1){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未答复" + '</a>';
		}else {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
		}
	}
}

//查看质疑发起
function detailAsk(id) {
	var win = creatFirstWin('查看', 790, 580, 'icon-search', '/cgAsk/detailAsk?id='+id);
	win.window('open');
}
//修改
function editAsk(id) {
	var win = creatFirstWin('修改', 790, 580, 'icon-search', '/cgAsk/editAsk?id='+id);
	win.window('open');
}
//删除
function deleteAsk(id) {
	if (confirm('确认删除吗？')) {
		$.ajax({
			type : 'POST',
			url : base + '/cgAsk/delete?id='+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#ask_detail_Tab').datagrid('reload');
					$('#cg_ask_Tab').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}

//撤回
function reCallAsk(id) {
	if (confirm('确认撤回吗？')) {
		$.ajax({
			type : 'POST',
			url : base + '/cgAsk/reCallAsk?id='+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#ask_detail_Tab').datagrid('reload');
					$('#cg_ask_Tab').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
//查看质疑发起
function detailAnswer(id) {
	var win = creatWin('质疑详情', 1080, 580, 'icon-search', '/cgAsk/detailAnswer?id='+id);
	win.window('open');
}
</script>
</body>
</html>