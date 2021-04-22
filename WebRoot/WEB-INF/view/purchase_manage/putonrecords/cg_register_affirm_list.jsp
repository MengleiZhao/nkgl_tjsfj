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
					<a href="#" onclick="queryCgPORApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearCgPORApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="cg_apply_register_affirm_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/putOnRecords/cgRegisterAffirmPage?index=${index}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 20%">采购项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fpMethod',align:'center',resizable:false,sortable:true" style="width: 20%">采购类型</th>
					<th data-options="field:'fpPype',align:'center',resizable:false,sortable:true" style="width: 20%">采购方式</th>
					<th data-options="field:'fOrgName',align:'center',resizable:false,sortable:true" style="width: 20%">中标商名称</th>
					<th data-options="field:'fbidAmount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">投标金额（元）</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 10%">申请部门</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 10%">申请人</th>
					<th data-options="field:'conPutOnRecordsSts',align:'center',resizable:false,sortable:true,formatter:formatPrice" style="width: 9%">合同备案</th>
					<th data-options="field:'conEarlyWarning',align:'center',resizable:false,sortable:true,formatter:formatConEarlyWarning" style="width: 9%">合同预警</th>
					<th data-options="field:'checkPutOnRecordsSts',align:'center',resizable:false,sortable:true,formatter:formatPrice1" style="width: 9%">验收备案</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgPORApply() {
	var searchData="searchData";
	$('#cg_apply_register_affirm_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgPORApply() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#cg_apply_register_affirm_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//合同备案设置审批状态
var c;
function formatPrice(val, row) {
	c = val;
	if (val == 0 || val==null) {
		return '<a style="color:#666666;">' + " 待上传" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;">' + " 待修改" + '</a>';
	} else if (val ==2) {
		return '<a onclick="cg_apply_con_update(' + row.fpId + ')" style="color:#408ffa;">' + " 待确认" + '</a>';
	} else if (val == 9) {
		return '<a onclick="cg_apply_con_detail(' + row.fpId + ')" style="color:#408ffa;">' + " 查看" + '</a>';
	} else if (val == 4) {
		return '<a style="color:#666666;">' + " 已撤回" + '</a>';
	} else if (val == 5) {
		return '<a style="color:#666666;">' + " 已退回" + '</a>';
	}
}
//合同预警显示
function formatConEarlyWarning(val, row) {
	if (val < 15) {
		return '<a style="color:#66FF33;">' + " 无预警" + '</a>';
	} else if (25 > val >=15) {
		return '<a style="color:#FF9900;">' + " 黄色预警" + '</a>';
	} else if (val >= 25) {
		return '<a style="color:#FF0000">' + " 红色预警" + '</a>';
	} 
	return '<a style="color:#66FF33;">' + " 无预警" + '</a>';
}
//验收备案设置审批状态
var b;
function formatPrice1(val, row) {
	b = val;
	if (val == 0 || val==null) {
		return '<a style="color:#666666;">' + " 待上传" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;">' + " 待修改" + '</a>';
	} else if (val ==2) {
		return '<a onclick="cg_apply_check_update(' + row.fpId + ')" style="color:#408ffa;">' + " 待确认" + '</a>';
	} else if (val == 9) {
		return '<a onclick="cg_apply_check_detail(' + row.fpId + ')" style="color:#408ffa;">' + " 查看" + '</a>';
	} else if (val == 4) {
		return '<a style="color:#666666;">' + " 已撤回" + '</a>';
	} else if (val == 5) {
		return '<a style="color:#666666;">' + " 已退回" + '</a>';
	}
}

//合同备案修改
function cg_apply_con_update(id) {
	var win = creatWin('确认', 785, 580, 'icon-search', '/putOnRecords/editRegisterCon?fpId='+id+'&type=2');
	win.window('open');
}
//验收备案修改
function cg_apply_check_update(id) {
	var win = creatWin('确认', 785, 580, 'icon-search', '/putOnRecords/editRegisterCheck?fpId='+id+'&type=2');
	win.window('open');
}
//合同备案查看
function cg_apply_con_detail(id) {
	var win = creatWin('查看', 785, 580, 'icon-search', '/putOnRecords/editRegisterCon?fpId='+id+'&type=0');
	win.window('open');
}
//验收备案查看
function cg_apply_check_detail(id) {
	var win = creatWin('查看', 785, 580, 'icon-search', '/putOnRecords/editRegisterCheck?fpId='+id+'&type=0');
	win.window('open');
}
</script>
</body>
