<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth">
					<input id="searchDataReim" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					<%-- <a id='zk' href="#" onclick="zk()">
						<img style="vertical-align:bottom;text-align:left;"  src="${base}/resource-modality/${themenurl}/button/zk.png" >
					</a>
					<a id='sq' hidden="hidden" href="#" onclick="sq()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/sq.png" >
					</a> --%>
					&nbsp;&nbsp;
						<a href="#" onclick="goldpay_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
						&nbsp;&nbsp;
						<a href="#" onclick="goldpay_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					<div id='othersearchtd' class="top-table-search-td" hidden="hidden" style="margin-bottom: 10px;">
						&nbsp;&nbsp;单据编号&nbsp;
						<input id="reimburse_list_top_rCode_11" style="width: 150px;height:25px;" class="easyui-textbox"/>
						&nbsp;&nbsp;合同名称&nbsp;
						<input id="reimburse_list_top_rName_11" style="width: 150px;height:25px;" class="easyui-textbox"/>
						&nbsp;&nbsp;
					</div>
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="addgoldpay()">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="list-table">
			<table id="goldpay_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburse/reimbursePage?reimburseType=11',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'gId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'rCode',align:'center',resizable:false" style="width: 12%">单据编号</th>
						<th data-options="field:'contCode',align:'center',resizable:false" style="width: 18%">合同编号</th>
						<th data-options="field:'fcTitle',align:'center',resizable:false" style="width: 19%">合同名称</th>
						<th data-options="field:'reimburseReqTime',align:'center',resizable:false,formatter: ChangeDateFormat" style="width: 11%">报销日期</th>
						<th data-options="field:'deptName',align:'center',resizable:false" style="width: 10%">申请部门</th>
						<th data-options="field:'userName',align:'center',resizable:false" style="width: 8%">申请人</th>
						<th data-options="field:'flowStauts',align:'center',resizable:false,formatter:flowStautsSet" style="width: 8%">审批状态</th>
						<th data-options="field:'name',align:'center',resizable:false,formatter:CZ" style="width: 10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
<script type="text/javascript">
$('#e_percentageTempEnd').val(1);
//清除查询条件
function goldpay_clearTable() {
	$('#searchDataReim').textbox('setValue','');
	$('#reimburse_list_top_rCode_11').textbox('setValue',null);
	$('#reimburse_list_top_rName_11').textbox('setValue',null);
	goldpay_query();
}
function Baoxiaochaoe(val, row){
	
	var a = row.amount;
	if(val>a){
		return '<span style="color:red">'+fomatMoney(val,2)+"【报销超额】"+'</span>';
	}else{
		return fomatMoney(val,2);
	}
}
//设置审批状态
var c;
function flowStautsSet(val, row) {
	c = val;
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已审批" + '</a>';
	} else if (val == -4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已撤回" + '</a>';
	}  else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审批" + '</a>';
	}
}
//操作栏创建
function CZ(val, row) {
	console.log(row.reimbFlowStauts);
	if (c == 1 || c == 2) {
		return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			   '<a href="#" onclick="detailCurrent(' + row.rId + ')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   '</a></td><td style="width: 25px">'+
			   '<a href="#" onclick="reCall(\'goldpay_dg\',' + row.rId + ',\'/reimburse/reimburseReCall\')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
			   '</a></td></tr></table>';
	}else if(c == 0 || c == -1 || c == -4) {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="detailCurrent(' + row.rId + ')" class="easyui-linkbutton">'+
		   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		   		'</a>'+ '</td><td style="width: 25px">'+
				'<a href="#" onclick="editgoldpay(' + row.rId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
				'</a>' + '</td><td style="width: 25px"><a href="#" onclick="deleteReimburseGold(' + row.rId + ',' + row.type + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
				'</a></td></tr></table>';
			/* 	
				'</tr></table>'; */
	}else {
		//c==9审批通过无法撤回
		return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			   '<a href="#" onclick="detailCurrent(' + row.rId + ')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   '</a></td><td style="width: 25px">'+
				'<a href="#" onclick="exportgoldpayHtml(' + row.rId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
			   '</a></td></tr></table>';
	}
}
//新增方法
function addgoldpay(){
	win = creatWin('新增', 1115, 600, 'icon-search', '/reimburse/addGoldPay');
	win.window('open');
}
//修改方法
function editgoldpay(id){
	win = creatWin('修改', 1115, 600, 'icon-search', '/reimburse/edit?id='+id+'&editType=1');
	win.window('open');
}
//查看
function detailCurrent(id){
	win = creatWin('查看', 1115, 600, 'icon-search', '/reimburse/edit?id='+id+'&editType=0');
	win.window('open');
}
//打印
function exportgoldpayHtml(id) {
	window.open(base+"/exportApplyAndReim/reimburseExprotGoldpay?id="+ id);//事后报销单
}
function goldpay_query() {
	sq();
	$("#goldpay_dg").datagrid('load',{
		searchData:$("#searchDataReim").textbox('getValue').trim(),
		rCode:$("#reimburse_list_top_rCode_11").textbox('getValue').trim(),
		gCode:$("#reimburse_list_top_rName_11").textbox('getValue').trim(),
	});
}
//删除
function deleteReimburseGold(id,type) {
	if (confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : '${base}/reimburse/delete?id=' + id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#goldpay_dg').datagrid('reload');
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