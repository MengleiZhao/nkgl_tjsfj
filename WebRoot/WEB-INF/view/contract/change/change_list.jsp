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
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="change_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td>
					<%-- <td align="right" style="padding-right: 10px">
						<a href="#" onclick="change_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td> --%>
					<%-- <td class="top-table-td1" class="queryth">合同编号：</td> 
					<td class="top-table-td2">
						<input id="upt_fcCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td class="top-table-td1" class="queryth">合同名称：</td> 
					<td class="top-table-td2">
						<input id="upt_fcTitle" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<!-- <td class="top-table-td1" class="queryth">合同金额：</td> 
					<td class="top-table-td2">
						<input id="upt_fcAmount" name="" style="width: 150px;height:25px;" data-options="icons: [{iconCls:'icon-wanyuan'}]" class="easyui-textbox"></input>
					</td>
					<td class="top-table-td1" class="queryth">申请时间：</td> 
					<td class="top-table-td2">
						<input id="upt_fReqtIME" name="" style="width: 150px;height:25px;"  class="easyui-datebox"></input>
					</td> -->
					<td style="width: 30px">
					</td>
					<td style="width: 24px;">
						<a href="javascript:void(0)" onclick="change_query();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png"onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
					</td>
					<td style="width: 12px">
					</td>
					<td >
						<a href="javascript:void(0)"  onclick="change_clearTable();"><img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
					</td>
					<td style="width: 30px">
					</td> --%>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>
		</div>
		
		
		<div class="list-table">
			<table id="change_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Change/JsonPagination',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<!-- <th data-options="field:'ck',checkbox:true"></th> -->
						<th data-options="field:'fcId',hidden:true"></th>
						<th data-options="field:'number',align:'center',resizable:false" width="5%">序号</th>
						<th data-options="field:'fContCode',align:'center',resizable:false" width="10%">变更合同编号</th>
						<th data-options="field:'fContCodeOld',align:'center',resizable:false" width="10%">原合同编号</th>
						<th data-options="field:'fContNameold',align:'center',resizable:false" width="16%">原合同名称</th>
						<th data-options="field:'fcType',align:'center',resizable:false,formatter:formatFcType" width="10%">原合同分类</th>
						<th data-options="field:'fOperatorOld',align:'center',resizable:false" width="10%">原合同申请人</th>
						<th data-options="field:'fReqTime',align:'center',formatter: ChangeDateFormat,resizable:false" width="10%" >变更申请时间</th>
						<th data-options="field:'fUptFlowStauts',align:'center',resizable:false,formatter:formatPrice" width="10%">变更审批状态</th>
						<th data-options="field:'fsealedStatus',align:'center',resizable:false,formatter:formatSealedStatus" width="10%">盖章状态</th>
						<th data-options="field:'name',align:'left',resizable:false,formatter:CZ" width="11%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">


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

function formatFcType(val, row) {
	if (val == 'HTFL-01') {
		return '采购合同';
	}
	if (val == 'HTFL-03') {
		return '非采购合同';
	}
	return val;
}

//合同盖章状态
function formatSealedStatus(val, row) {
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未盖章" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已盖章" + '</span>';
	}
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
function formatPrice(val, row) {
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
	if(row.fUptFlowStauts=='-1'||row.fUptFlowStauts=='0'||row.fUptFlowStauts=='-4'){
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="detailInfo(' + row.fId_U+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' 
				+'</a>'+ '</td><td style="width: 25px">'
				+'<a href="#" onclick="updateHT(' + row.fId_U
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/change1.png">'
				+'</a>'+ '</td><td style="width: 25px">'
				+'<a href="#" onclick="upt_delete(' + row.fId_U
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'
				+'</a></td></tr></table>';
	}else if(row.fUptFlowStauts=='1'){
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="detailInfo(' + row.fId_U+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' 
				+'</a>'+ '</td><td style="width: 25px">'
				+ '<a href="#" onclick="reCall(\'change_dg\' ,'+ row.fId_U+',\'/Change/reCall\''+')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">' + '</a>'
				+'</td></tr></table>';
	}else {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="detailInfo(' + row.fId_U+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' 
				+'</a>'+ '</td><td style="width: 25px">'
				+'<a href="#" onclick="update_exportHtml(' + row.fId_U
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'
				+'</a></td></tr></table>';
	}
}
//打印
function update_exportHtml(id) {
	window.open(base+"/contractExport/upt?id="+ id);
}
function change_query() {
	$('#change_dg').datagrid('load', {
		/* fContCode : $('#upt_fContCode').val(),
		fContCodeOld : $('#upt_fContCodeOld').val(),
		fContNameold : $('#upt_fContNameold').val() */
		searchData:$('#upt_searchData').val()
	});
}
function addCF() {
	var node = $('#change_dg').datagrid('getSelected');
	var win = creatWin('合同变更', 750, 550, 'icon-add', '/Change/add');
	if (null != node) {
		win = creatWin('合同变更', 750, 550, 'icon-add',
				'/Change/add?departId=' + node.id);
	}
	win.window('open');
}

function change_add() {
	var win = creatFirstWin('合同信息', 970,580, 'icon-search',"/Change/contract");
	win.window('open');
}
function detailInfo(id) {
	var win = creatWin('合同变更明细', 1115, 600, 'icon-search',"/Change/detail/"+ id);
	win.window('open');
	
}

function ending_update(id) {
	var win = creatWin('合同终止申请', 1115, 600, 'icon-search',
			"/ending/edit/" + id);
	win.window('open');
}
function editCF() {
	var row = $('#change_dg').datagrid('getSelected');
	var selections = $('#change_dg').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		var win = creatWin('合同变更', 750, 550, 'icon-search',
				"/Change/edit/" + row.fcId);
		win.window('open');
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
}
function updateHT(id) {
	//var row = $('#change_dg').datagrid('getSelected');
	var selections = $('#change_dg').datagrid('getSelections');
	var win = creatWin('合同变更', 1115, 600, 'icon-search',
			"/Change/edit/" + id);
	win.window('open');
}
function upt_delete(id) {
	if (id!=''||id!=null) {
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/Change/delete/' + id,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$("#change_dg").datagrid('reload');
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
					$("#change_dg").datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
</script>
</body>
</html>

