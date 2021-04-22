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
					<input class="easyui-combobox" id="cgsq_fpItemsName" name="fpMethod" style="width: 150px; height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PMMC',method:'get',valueField:'code',textField:'text',editable:false"/>
					 --%>
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
		<table id="cg_apply_execution_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgsqsp/executioncgsqPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center',resizable:false" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center',resizable:false" style="width: 10%">项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 23.5%">项目名称</th>
					<th data-options="field:'fpItemsName',align:'center',resizable:false,sortable:true,formatter:returnfpItemsName" style="width: 10%">品目名称</th>
					<th data-options="field:'amount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 10%">采购金额[元]</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 10%">申报部门</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 8%">申报人</th>
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 9%">申请时间</th>
					<th data-options="field:'fCheckStauts',align:'center',resizable:false,sortable:true,formatter:formatPrice" style="width: 8%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 8%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgApply() {
	var searchData="searchData";
	$('#cg_apply_execution_Tab').datagrid('load', {
		/* fpCode:$('#cgsq_fpCode').val(),
		fpName:$('#cgsq_fpName').val(),
		fpItemsName:$('#cgsq_fpItemsName').combobox('getValue'), */
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgApply() {
	var searchData="searchData";
	/* $('#cgsq_fpCode').textbox('setValue','');
	$('#cgsq_fpName').textbox('setValue','');
	$('#cgsq_fpItemsName').combobox('setValue',''); */
	$("#"+searchData).textbox('setValue','');
	$('#cg_apply_execution_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//返回采购类型
function fpMethodreturn(val,row){
	if(val=='PURCHASE_METHOD_1'){
		return '政府采购';
	}else  if(val=='PURCHASE_METHOD_2'){
		return '非政府采购';
	}
}
function returnfpItemsName (val,row){
	if(val=='PMMC-1'){
		return '货物类—固定资产';
	}else  if(val=='PMMC-2'){
		return '货物类—无形资产';
	}else  if(val=='PMMC-3'){
		return '货物类—其他资产';
	}else  if(val=='PMMC-4'){
		return '工程类';
	}else  if(val=='PMMC-5'){
		return '服务类';
	}
}
function fpItemsNamereturn(){
	
	
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
	   '<a href="#" onclick="cg_apply_execution_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td>';
	//修改按钮
	var button2='<td style="width: 25px">'+
		'<a href="#" onclick="cg_apply_execution_update(' + row.fpId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/shangchuan1.png">'+
		'</a></td>';
	var titleend='</tr></table>';
	
	if (row.fConfirmStauts == 1) {
		var returnButton=titlebegin+button1;
		return returnButton+titleend;
	} else {
		return titlebegin+button1+button2+titleend;
	}
}

//新增
function addCgApply() {
	var win = creatWin('新增', 970, 580, 'icon-search', '/cgsqsp/add');
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
					$('#cg_apply_execution_Tab').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
//修改
function cg_apply_execution_update(id) {
	var win = creatWin('主管部门意见上传', 1070, 580, 'icon-search', '/cgsqsp/executionEdit?id='+id);
	win.window('open');
 	}
//查看
function cg_apply_execution_detail(id) {
	var win = creatWin('查看', 1070, 580, 'icon-search', '/cgsqsp/detail?id='+id);
	win.window('open');
}
</script>
</body>
</html>