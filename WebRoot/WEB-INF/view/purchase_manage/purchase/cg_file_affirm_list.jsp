<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_apply_dg" class="top-table" cellpadding="0" cellspacing="0">
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
		<table id="cg_apply_file_affirm_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgsqsp/cgFileAffirmPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 20%">采购项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fpMethod',align:'center',resizable:false,sortable:true" style="width: 20%">采购类型</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">预算金额（元）</th>
					<th data-options="field:'agency',align:'center',resizable:false,sortable:true" style="width: 10%">代理机构</th>
					<th data-options="field:'projectLeaderName',align:'center',resizable:false,sortable:true" style="width: 10%">项目负责人</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 10%">业务发起人</th>
					<th data-options="field:'processLeaderName',align:'center',resizable:false,sortable:true" style="width: 10%">采购流程负责人</th>
					<th data-options="field:'filesUploadSts',align:'center',resizable:false,sortable:true,formatter:formatPrice" style="width: 9%">确认状态</th>
					<th data-options="field:'CZ',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgApply() {
	var searchData="searchData";
	$('#cg_apply_file_affirm_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgApply() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#cg_apply_file_affirm_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//设置审批状态
var c;
function formatPrice(val, row) {
	c = val;
	if (val == 0 || val==null) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 待上传" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 待修改" + '</a>';
	} else if (val ==2) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待确认" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已确认" + '</a>';
	} else if (val == 4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</a>';
	}else if (val == 5) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	}
}


//操作栏创建
function CZ(val, row) {
	var titlebegin='<table><tr style="width: 75px;height:20px">';
	//下载文件按钮
	var button1='<td style="width: 25px">'+
	   '<a href="#" onclick="cg_apply_file_download(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/xz1.png">'+
	   '</a></td>';
	//修改详情按钮
	var button2='<td style="width: 25px">'+
	   '<a href="#" onclick="cg_apply_file_affirm(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/qr1.png">'+
	   '</a></td>';
	var titleend='</tr></table>';
	if(c==2){
		return titlebegin+button1+button2+titleend;
	}
		return titlebegin+button1+titleend;
}
//下载文件
function cg_apply_file_download(id) {
	var win = creatWin('下载文件', 785, 580, 'icon-search', '/cgsqsp/downloadFile?fpId='+id);
	win.window('open');
}
//确认
function cg_apply_file_affirm(id) {
	var win = creatWin('查看', 785, 580, 'icon-search', '/cgsqsp/editFileUp?fpId='+id+'&type=2');
	win.window('open');
}

</script>
</body>
