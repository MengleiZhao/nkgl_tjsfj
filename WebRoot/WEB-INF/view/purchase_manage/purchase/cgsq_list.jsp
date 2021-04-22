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
					<%-- 项目编号&nbsp;
					<input id="cgsq_fpCode" name="fpCode" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;项目名称&nbsp;
					<input id="cgsq_fpName" name="fpName" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;品目名称&nbsp;
					<input class="easyui-combobox" id="cgsq_fpItemsName" name="fpItemsName" style="width: 150px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PMMC&selected=${bean.fpItemsName}',method:'get',valueField:'code',textField:'text',editable:false"/>
					 --%>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryCgApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearCgApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="addCgApply()">
						<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="cg_apply_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgsqsp/cgsqPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 20%">采购项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 10%">申请处室</th>
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 12%">申请时间</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">预算金额</th>
					<th data-options="field:'fCheckStauts',align:'center',resizable:false,sortable:true,formatter:formatPrice" style="width: 9%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgApply() {
	var searchData="searchData";
	$('#cg_apply_Tab').datagrid('load', {
		/* fpCode:$('#cgsq_fpCode').val(),
		fpName:$('#cgsq_fpName').val(),
		fpItemsName:$('#cgsq_fpItemsName').combobox('getValue'), */
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgApply() {
	/* $('#cgsq_fpCode').textbox('setValue','');
	$('#cgsq_fpName').textbox('setValue','');
	$('#cgsq_fpItemsName').combobox('setValue',''); */
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#cg_apply_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//设置审批状态
var c;
function formatPrice(val, row) {
	c = val;
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if (val ==-4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</a>';
	} else if (val == 9) {
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
	   '<a href="#" onclick="cg_apply_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td>';
	//修改按钮
	var button2='<td style="width: 25px">'+
		'<a href="#" onclick="cg_apply_update(' + row.fpId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
		'</a></td>';
	//删除按钮
	var button3='<td style="width: 25px">'+
		'<a href="#" onclick="cg_apply_delete(' + row.fpId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
		'</a></td>';
	//撤回按钮
	var button4='<td style="width: 25px">'+
		'<a href="#" onclick="reCall(\'cg_apply_Tab\',' + row.fpId + ',\'/cgsqsp/reCall\')" class="easyui-linkbutton">'+
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
function addCgApply() {
	var win = creatWin('新增', 1070, 580, 'icon-search', '/cgsqsp/add');
	win.window('open');
}
//删除
function cg_apply_delete(id) {
	if (confirm('确认删除吗？')) {
		$.ajax({
			type : 'POST',
			url : '${base}/cgsqsp/delete?id='+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#cg_apply_Tab').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
//修改
function cg_apply_update(id) {
	var win = creatWin('修改', 1070, 580, 'icon-search', '/cgsqsp/edit?id='+id);
	win.window('open');
 	}
//查看
function cg_apply_detail(id) {
	var win = creatWin('查看', 1070, 580, 'icon-search', '/cgsqsp/detail?id='+id);
	win.window('open');
}

//打印
function exportHtml(id) {
	window.open(base+"/exportCg/apply?id="+ id);
}
</script>
</body>
</html>