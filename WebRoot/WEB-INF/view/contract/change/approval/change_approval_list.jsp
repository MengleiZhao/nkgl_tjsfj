<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr >
					<td class="top-table-search" class="queryth">
						<!-- 变更合同编号&nbsp;
						<input id="upt_fContCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;原合同编号&nbsp;
						<input id="upt_fContCodeOld"  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;原合同名称&nbsp;
						<input id="upt_fContNameold" style="width: 150px;height:25px;"  class="easyui-textbox"></input>
						&nbsp;&nbsp; -->
						<input id="upt_searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="change_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png"onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="change_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td> 
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>
		</div>
		
		
		<div class="list-table">
			<table id="change_approval_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Change/approvalJson',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<!-- <th data-options="field:'ck',checkbox:true"></th> -->
						<th data-options="field:'fcId',hidden:true"></th>
						<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
						<th data-options="field:'fContCode',align:'center',resizable:false,sortable:true" width="12%">变更合同编号</th>
						<th data-options="field:'fOperator',align:'center',resizable:false,sortable:true" width="10%">变更申请人</th>
						<th data-options="field:'fContCodeOld',align:'center',resizable:false,sortable:true" width="12%">原合同编号</th>
						<th data-options="field:'fContNameold',align:'center',resizable:false,sortable:true" width="20%">原合同名称</th>
						<th data-options="field:'fcType',align:'center',resizable:false,sortable:true,formatter:formatFcType" width="10%">原合同分类</th>
						<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="10%">承办部门</th>
						<th data-options="field:'fOperatorOld',align:'center',resizable:false,sortable:true" width="10%">原合同申请人</th>
						<th data-options="field:'fReqTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >变更申请时间</th>
						<th data-options="field:'fUptFlowStauts',align:'center',resizable:false,sortable:true,formatter:formatFlowStauts" width="10%">变更审批状态</th>
						<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:CZ" width="11%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">

$("#upt_fReqtIMEStart").datebox({
    onSelect : function(beginDate){
        $('#upt_fReqtIMEEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
//清除查询条件
function change_clearTable() {
	/* $('#upt_fContCode').textbox('setValue',null);
	$('#upt_fContCodeOld').textbox('setValue',null);
	$('#upt_fContNameold').textbox('setValue',null); */
	$('#upt_searchData').textbox('setValue','');
	change_query();
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

function change_add() {
	var win = creatWin('变更申请', 970,580, 'icon-search',"/Change/add");
	win.window('open');
}

function formatFcType(val, row) {
	if (val == 'HTFL-01') {
		return '采购合同';
	}if (val == 'HTFL-03') {
		return '非采购合同';
	}
	return val;
}
function uptType(val, row) {
	if (val == 0) {
		return '<span >' + "" + '</span>';
	} else if (val == 'HTBGLX-01') {
		return '<span >' + "补充合同" + '</span>';
	}else if (val == 'HTBGLX-02') {
		return '<span >' + "变更合同" + '</span>';
	}
}
function CZ(val, row) {
	if(row.fUptFlowStauts==1){
		return 	'<a href="#" onclick="detailInfo(' + row.fId_U
				+ ')" class="easyui-linkbutton"><img onmouseover="changeshowB(this)" onmouseout="changeshowA(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>' 
				+'&nbsp;'
				+'<a href="#" onclick="approvalHTBG(' + row.fId_U
				+ ')" class="easyui-linkbutton"><img onmouseover="changeshowC(this)" onmouseout="changeshowD(this)" src="'+base+'/resource-modality/${themenurl}/list/approval1.png"></a>';
	}else {
		return 	'<a href="#" onclick="detailInfo(' + row.fId_U
				+ ')" class="easyui-linkbutton"><img onmouseover="changeshowB(this)" onmouseout="changeshowA(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>' 
	}
}
function changeshowC(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/approval2.png';
}
function changeshowD(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/approval1.png';
}
function changeshowB(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/select2.png';
}
function changeshowA(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
}
function endingshowC(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/ending2.png';
}
function endingshowD(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/ending1.png';
}
function change_query() {
	$('#change_approval_dg').datagrid('load', {
	/* 	fContCode : $('#upt_fContCode').val(),
		fContCodeOld : $('#upt_fContCodeOld').val(),
		fContNameold : $('#upt_fContNameold').val() */
		searchData:$('#upt_searchData').val()
	});
}
function addCF() {
	var node = $('#change_approval_dg').datagrid('getSelected');
	var win = creatWin('合同变更', 750, 550, 'icon-add', '/Change/add');
	if (null != node) {
		win = creatWin('合同变更', 750, 550, 'icon-add',
				'/Change/add?departId=' + node.id);
	}
	win.window('open');
}
function detailInfo(id) {
	var selections = $('#change_approval_dg').datagrid('getSelections');
	var win = creatWin('合同变更', 1115, 600, 'icon-search',"/Change/detail/" + id);
	win.window('open');
}
function ending_update(id) {
	var win = creatWin('合同终止申请', 1115, 600, 'icon-search',
			"/ending/edit/" + id);
	win.window('open');
}
function editCF() {
	var row = $('#change_approval_dg').datagrid('getSelected');
	var selections = $('#change_approval_dg').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		var win = creatWin('合同变更', 750, 550, 'icon-search',
				"/Change/edit/" + row.fcId);
		win.window('open');
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
}
function approvalHTBG(id) {
	//var row = $('#change_approval_dg').datagrid('getSelected');
	var selections = $('#change_approval_dg').datagrid('getSelections');
	var win = creatWin('合同变更审批', 1115, 600, 'icon-search',
			"/Change/approvalChange/" + id);
	win.window('open');
}
function deleteCF() {
	var row = $('#change_approval_dg').datagrid('getSelected');
	var selections = $('#change_approval_dg').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/Change/delete/' + row.fcId,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$("#change_approval_dg").datagrid('reload');
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
		}
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
}
function CF_delete(id) {
	if (confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : '${base}/Change/delete/' + id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$("#change_approval_dg").datagrid('reload');
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

function formatFlowStauts(val, row) {
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
</script>
</body>
</html>

