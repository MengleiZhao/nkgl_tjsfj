<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search" class="queryth">
				<div class="top-table-search-td">选择年度：
					<input class="easyui-combobox" id="applyYear_show" name="applyYear" style="width: 150px;height: 30px;margin-left: 10px" data-options="valueField:'id',textField:'text',url:'${base}/schedule/getApplyYear',editable:false"/>
					&nbsp;&nbsp;&nbsp;&nbsp;选择部门：
					<input class="easyui-combobox" id="applyDept_show" name="deptName" style="width: 150px;height: 30px;margin-left: 10px" data-options="valueField:'id',textField:'text',url:'${base}/schedule/getApplyDept',editable:false"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" onclick="queryScheduleLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearScheduleLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</div>
				</td>
			</tr>
		</table>  
	</div>
	<div class="list-table">
		<table id="scheduleTabLedger" class="easyui-datagrid"
		data-options="collapsible:true,url:'${base}/schedule/scheduleLedgerPage',
		method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<th data-options="field:'sId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 10%">序号</th>
					<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 20%">申报部门</th>
					<th data-options="field:'applyYear',align:'center',resizable:false,sortable:true" style="width: 10%">申报年度</th>
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 30%">项目总额[元]</th>
					<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSetLedger" style="width: 20%">审批状态</th>
					<th data-options="field:'CZ',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//设置审批状态
var c;
function flowStautsSetLedger(val, row) {
	c = val;
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已审批" + '</a>';
	} else if (val == -4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已撤回" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审批" + '</a>';
	}
}

//操作栏创建
function CZ(val, row) {
	
	return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	   '<a href="#" onclick="editScheduleLedger(' + row.sId + ',0)" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td></tr></table>';
}

//修改
function editScheduleLedger(id,type) {
	/*type为修改或查看1位修改，0位查看  */
	var title = "计划申请";
	var win = creatWin(title,1115, 600, 'icon-search', "/schedule/edit?id="+ id +"&editType="+ type);
	win.window('open');
}

//查询
function queryScheduleLedger() {
	$("#scheduleTabLedger").datagrid('load',{
		applyYear:$("#applyYear_show").combobox('getValue').trim(),
		deptName:$("#applyDept_show").combobox('getValue').trim(),
	});

}
//清除查询条件
function clearScheduleLedger() {
	$("#applyYear_show").combobox('setValue',''),
	$("#applyDept_show").combobox('setValue',''),
	$("#scheduleTabLedger").datagrid('load',{});
}

</script>
</body>