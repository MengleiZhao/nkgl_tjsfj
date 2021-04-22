<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_answer_dg" class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryCgApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearCgApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="cg_answer_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgAsk/answerPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fqId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpName',align:'center',resizable:false" style="width: 17.5%">被质疑项目名称</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 10%">被质疑项目编号</th>
					<th data-options="field:'fAskTime',align:'center',formatter:ChangeDateFormat" style="width: 10%">质疑发起时间</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false" style="width: 10%">答复部门</th>
					<th data-options="field:'fUserName',align:'center',resizable:false" style="width: 10%">答复人</th>
					<th data-options="field:'fAnswerTime',align:'center',resizable:false,formatter:ChangeDateFormat" style="width: 10%">答复时间</th>
					<th data-options="field:'fAnswerStauts',align:'center',resizable:false,formatter:formatAnswerStatus" style="width: 10%">答复状态</th>
					<th data-options="field:'fCheckStauts',align:'center',resizable:false,formatter:formatCheckStatus" style="width: 10%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgApply() {
	var searchData="searchData";
	$('#cg_answer_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgApply() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#cg_answer_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
function formatAnswerStatus(val, row){
	if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已答复" + '</a>';
	}else if (val == 0){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未答复" + '</a>';
	}
}
//审批状态
function formatCheckStatus(val, row) {
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</span>';
	} else if (val == 9) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</span>';
	} else if (val == -1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</span>';
	} else if (val == -4) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</span>';
	}
}

//操作栏创建
function CZ(val, row) {
	//审批状态
	var c = row.fCheckStauts;
	if(c==null){
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="addCgAnswer(' + row.fqId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/df1.png">'+
		'</a></td><td style="width: 25px">'+
		'<a href="#" onclick="detailAsk(' + row.fqId+ ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td></tr></table>';
	}else if(c == 0||c == -1||c == -4){
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailAnswer(' + row.fqId+ ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td><td style="width: 25px">'+
		'<a href="#" onclick="editAnswer(' + row.fqId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
		'</a></td><td style="width: 25px">'+
		'</a></td></tr></table>';
	}else if (c == 1) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailAnswer(' + row.fqId+ ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td><td style="width: 25px">'+
		'<a href="#" onclick="reCall(\'cg_answer_Tab\' ,'+ row.fqId+',\'/cgAsk/reCallAnswer\''+')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
		'</a></td></tr></table>';
	} else  {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailAnswer(' + row.fqId+ ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td></tr></table>';
	} 
}

//新增
function addCgAnswer(id) {
	var win = creatWin('答复质疑', 1080, 580, 'icon-search', '/cgAsk/addAnswer?id='+id);
	win.window('open');
}
//修改
function editAnswer(id) {
	var win = creatWin('答复质疑-修改', 1080, 580, 'icon-search', '/cgAsk/editAnswer?id='+id);
	win.window('open');
}
//查看质疑发起
function detailAsk(id) {
	var win = creatFirstWin('查看', 790, 580, 'icon-search', '/cgAsk/detailAsk?id='+id);
	win.window('open');
}
//查看质疑发起
function detailAnswer(id) {
	var win = creatWin('质疑详情', 1080, 580, 'icon-search', '/cgAsk/detailAnswer?id='+id);
	win.window('open');
}
</script>
</body>
</html>