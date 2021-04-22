<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_ask_dg" class="top-table" cellpadding="0" cellspacing="0">
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
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="addCgAsk()">
						<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="cg_ask_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgAsk/cgQusetionPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 15%">采购项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fpMethod',align:'center',resizable:false" style="width: 11%">采购类型</th>
					<th data-options="field:'fpPype',align:'center',resizable:false" style="width: 11.9%">采购方式</th>
					<th data-options="field:'fUserName',align:'center',resizable:false" style="width: 11%">申请人</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false" style="width: 11.9%">申请部门</th>
					<th data-options="field:'fOrgName',align:'center',resizable:false" style="width: 11%">中标商名称</th>
					<th data-options="field:'fbidAmount',align:'center',resizable:false,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 12%">中标金额</th>
					<th data-options="field:'fAnswerStatus',align:'center',resizable:false,formatter:formatPrice" style="width: 9%">完结状态</th>
					<th data-options="field:'name',align:'center',resizable:false,formatter:CZ" style="width: 6%">质疑记录</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgApply() {
	var searchData="searchData";
	$('#cg_ask_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgApply() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#cg_ask_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//设置审批状态
var c;
function formatPrice(val, row) {
	c = val;
	if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已完结" + '</a>';
	}else if (val == 1){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未完结" + '</a>';
	}else {
		return null;
	}
}

//操作栏创建
function CZ(val, row) {
	var titlebegin='<table><tr style="width: 75px;height:20px">';
	var titleend='</tr></table>';
	//查看质疑记录按钮
	var button1='<td style="width: 25px">'+
	   '<a href="#" onclick="questioning_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td>';
	
	return titlebegin+button1+titleend;
}

//新增
function addCgAsk() {
	var win = creatFirstWin('新增质疑', 790, 580, 'icon-search', '/cgAsk/addAsk');
	win.window('open');
}
//查看
function questioning_detail(id) {
	var win = creatWin('查看质疑记录', 890, 550, 'icon-search', '/cgAsk/detailList?fpId='+id);
	win.window('open');
}

</script>
</body>
</html>