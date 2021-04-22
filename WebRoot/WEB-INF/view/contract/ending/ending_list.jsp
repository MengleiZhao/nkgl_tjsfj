<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">终止单编号&nbsp;
						<input id="c_fcCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;合同名称&nbsp;
						<input id="c_fcTitle" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;申请时间&nbsp;
						<input id="c_fReqtIME" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ending_queryCF();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="ending_clearTable();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
						
					
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="ending_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td>
					<%-- <td class="top-table-td1">合同编号：</td> 
					<td class="top-table-td2">
						<input id="c_fcCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td style="width: 30px;"></td>
					<td class="top-table-td1">合同名称：</td> 
					<td class="top-table-td2">
						<input id="c_fcTitle" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td style="width: 30px;"></td>
					<td class="top-table-td1">申请时间：</td> 
					<td class="top-table-td2">
						<input id="c_fReqtIME" name="" style="width: 150px;height:25px;" class="easyui-datebox"></input>
					</td>
					<!-- <td style="width: 30px;"></td>
					<td class="top-table-td1">审批状态：</td> 
					<td class="top-table-td2">
						<input id="c_fFlowStauts" name="fFlowStauts" style="width: 150px;height:25px;" data-options="url:'${base}/ending/lookupsJson?parentCode=SPZT',method:'POST',valueField:'code',textField:'text',editable:false" class="easyui-combobox"></input>
					</td>-->
					<td style="width: 30px;"></td>
					<td style="width: 26px;">
						<a href="javascript:void(0)"  onclick="ending_queryCF();"><img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
					</td>
					<td style="width: 8px;"></td>
					<td>
						<a href="javascript:void(0)"  onclick="ending_clearTable();"><img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')"></a>
					</td>
					<td style="width: 8px;"></td>
					
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="ending_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td> --%>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
		</div>
		
		<div class="list-table">
			<table id="ending_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/ending/jsonPagination',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<!-- <th data-options="field:'ck',checkbox:true"></th> -->
						<th data-options="field:'fEndId',hidden:true"></th>
						<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
						<th data-options="field:'fEndCode',align:'center',resizable:false,sortable:true" width="20%">终止单编号</th>
						<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="21%">合同名称</th>
						<th data-options="field:'fEndType',align:'center',resizable:false,sortable:true,formatter:endType" width="10%">终止类型</th>
						<th data-options="field:'createTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >申请日期</th>
						<th data-options="field:'fEndRemark',align:'center',resizable:false,sortable:true" width="15%">终止原因</th>
						<th data-options="field:'stauts',align:'center',resizable:false,sortable:true,formatter:formatPrice" width="10%">审批状态</th>
						<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
</div>	


<script type="text/javascript">
//清除查询条件
function ending_clearTable() {
	$('#c_fcCode').textbox('setValue',null);
	$('#c_fcTitle').textbox('setValue',null);
	$('#c_fReqtIME').datebox('setValue',null);
	$('#c_fFlowStauts').textbox('setValue',null);
	ending_queryCF();
}
//鼠标移入图片替换
function mouseOver(img){
	var src = $(img).attr("src");
	src = src.replace(/1/, "2");
	$(img).attr("src",src);
}
	
function mouseOut(img) {
	var src = $(img).attr("src");
	src = src.replace(/2/, "1");
	$(img).attr("src",src);
}
function endType(val,row){
	if(val=='HTZZ-02'){
		return "不可抗力";
	}else if(val=='HTZZ-01'){
		return "违约";
	}
}
var fs
function formatPrice(val, row) {
	fs=val;
	console.log(val)
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</span>';
	}else if (val == 9) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</span>';
	}else if (val == 99) {
		return '<span style="color:#666666;">' + " 已删除" + '</span>';
	} else if (val == -1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</span>';
	}else if (val == -4) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</span>';
	}
}
function CZ(val, row) {
	if(fs==9){
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="ending_detail(' + row.fEndId+ ')" class="easyui-linkbutton"><img onmouseover="endingshowB(this)" onmouseout="endingshowA(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a></td></tr></table>';
	}
	if(fs==1){
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="ending_detail(' + row.fEndId+ ')" class="easyui-linkbutton"><img onmouseover="endingshowB(this)" onmouseout="endingshowA(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
				+'</td><td style="width: 25px">'+ '<a href="#" onclick="reCall(\'ending_dg\' ,'+ row.fEndId+',\'/ending/reCall\''+')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">' + '</a>'
				+'</td></tr></table>';
	}
	return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="ending_detail(' + row.fEndId+ ')" class="easyui-linkbutton"><img onmouseover="endingshowB(this)" onmouseout="endingshowA(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
			+'</td><td style="width: 25px">'+ '<a href="#" onclick="ending_update(' + row.fEndId
			+ ')" class="easyui-linkbutton"><img onmouseover="endingshowC(this)" onmouseout="endingshowD(this)" src="'+base+'/resource-modality/${themenurl}/list/ending1.png">' + '</a>'
			+'</td><td style="width: 25px">'+'<a href="#" onclick="CF_delete(' + row.fEndId
			+ ')" class="easyui-linkbutton"><img onmouseover="endingshowE(this)" onmouseout="endingshowF(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">' + '</a>'
			+'</td></tr></table>';
}
function endingshowB(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/select2.png';
}
function endingshowA(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
}
function endingshowC(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/ending2.png';
}
function endingshowD(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/ending1.png';
}
function endingshowE(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/delete2.png';
}
function endingshowF(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/delete1.png';
}
function ending_queryCF() {
	var fs=$('#c_fFlowStauts').val();
	if(fs=="SPZT-01"){
		fs="-1";
	}else if(fs=="SPZT-02"){
		fs="0";
	}else if(fs=="SPZT-03"){
		fs="1";
	}else if(fs=="SPZT-04"){
		fs="9";
	}
	$('#ending_dg').datagrid('load', {
		fEndCode : $('#c_fcCode').val(),
		title : $('#c_fcTitle').val(),
		fEndTime : $('#c_fReqtIME').val(),
		fFlowStauts : fs
	});
}

function ending_add() {
	var node = $('#ending_dg').datagrid('getSelected');
	//先打开合同页面，让用户选择
	var win = creatFirstWin('合同信息', 1115, 600, 'icon-search', "/ending/endingContract");
	win.window('open');
}
function ending_detail(id) {
		var win = creatWin('合同终止明细', 1115, 600, 'icon-search',"/ending/detail/" + id);
		win.window('open');
}
/* function editCF() {
	var row = $('#ending_dg').datagrid('getSelected');
	var selections = $('#ending_dg').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		var win = creatWin(' ', 1256, 700, 'icon-search',
				"/ending/edit/" + row.fEndId);
		win.window('open');
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
} */
function ending_update(id) {
	//var row = $('#ending_dg').datagrid('getSelected');
	var selections = $('#ending_dg').datagrid('getSelections');
	var win = creatWin('合同终止申请', 1115, 600, 'icon-search',
			"/ending/edit/" + id);
	win.window('open');
}
function CF_delete(id) {
	if (confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : '${base}/ending/delete/' + id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#CFAddEditForm').form('clear');
					$("#ending_dg").datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
function expListlawHelp() {
	//var win=creatWin('导出-法律服务接待登记表',400,120,'icon-search','/demo/exportList');
	//win.window('open');
	if (confirm("按当前查询条件导出？")) {
		var queryForm = document.getElementById("lawHelp_list_form");
		queryForm.setAttribute("action", "${base}/demo/expList");
		queryForm.submit();
	}
}
//时间格式化
function ChangeDateFormat(val) {
	//alert(val)
	var t, y, m, d, h, i, s;
	if (val == null) {
		return "";
	}
	t = new Date(val)
	y = t.getFullYear();
	m = t.getMonth() + 1;
	d = t.getDate();
	h = t.getHours();
	i = t.getMinutes();
	s = t.getSeconds();
	// 可根据需要在这里定义时间格式  
	return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
}
function formatOper(value, row, index) {
	return 'sfsdf';
	//return '<a href="#" onclick="test('+index+')">修改</a>';  
	//    return '<a href="javascript:void(0);" onclick="openviewzfrw(\''+row.person+'\',\''+row.data_status+'\')"><font color="blue">走访</font></a>'; 
}
</script>
</body>

