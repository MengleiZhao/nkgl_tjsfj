<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="receive_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">采购批次编号&nbsp;
					<input id="receive_fpCode" name="fpCode" style="width: 100px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;采购名称&nbsp;
					<input id="receive_fpName" name="fpName" style="width: 100px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="queryReceive();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearReceive();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">
		<table id="receive_list" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgreceive/ledgerPageList',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 15%">采购批次编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 16%">采购名称</th>
					<th data-options="field:'fpAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 10%">采购金额[元]</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 10%">申请人</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 10%">申报部门</th>
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">申报时间</th>
					<th data-options="field:'fIsReceive',align:'center',resizable:false,sortable:true,formatter:formatRecive" style="width: 10%">验收状态</th>
 					<!-- <th data-options="field:'fevalStauts',align:'center',resizable:false,sortable:true,formatter:formatEval" style="width: 10%">评价状态</th> -->
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:RE_EVAL" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	//点击查询
	function queryReceive() {
		$("#receive_list").datagrid('load', {
			fpCode:$("#receive_fpCode").val(),
			fpName:$("#receive_fpName").val()
		});
	}
	
	//清除查询条件
	function clearReceive() {
		$("#receive_fpCode").textbox('setValue','');
		$("#receive_fpName").textbox('setValue','');
		$("#receive_list").datagrid('load',{//清空以后，重新查一次
		});
	}
	
	//设置验收状态
	function formatPrice(val, row) {
		if(val == 0) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
		} else if(val == -1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
		} else if(val == 9) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已验收" + '</a>';
		} else if(val == 1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待验收" + '</a>';
		} else if(val == -4) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</a>';
		}
	}
	
	//设置评价信息状态
	function formatEval(val, row) {
		if (val == 0) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未评价" + '</a>';
		} else if (val == 1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已评价" + '</a>';
		} 
	}
	
	//操作栏创建
	function CZ(val, row) {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="receive_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td></tr></table>';
	}
	
	//验收台账
	function receive_detail(id) {
		/* var win = creatWin('查看', 970, 580, 'icon-search', "/cgreceive/ledgerDetail?id="+id); */
		var win = creatWin('查看', 970, 580, 'icon-search', "/cgreceive/detail?id="+id);
		win.window('open');
	}		
</script>
</body>
</html>