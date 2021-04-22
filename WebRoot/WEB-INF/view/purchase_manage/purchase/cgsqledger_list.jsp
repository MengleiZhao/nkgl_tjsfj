<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_lendger_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
			<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					<!-- 项目编号&nbsp;
					<input id="ledger_fpCode" name="fpCode" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;项目名称&nbsp;
					<input id="ledger_fpName" name="fpName" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					 -->
					 &nbsp;&nbsp;
					<a href="#" onclick="queryLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearLedger();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="cgledger_list" class="easyui-datagrid" data-options="collapsible:true,url:'${base}/cgsqLedger/cgledgerPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 10%">项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 27%">项目名称</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 10%">申请人</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 11%">申请部门</th>
					<!-- <th data-options="field:'fpAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 8%">采购金额[元]</th> -->
					<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 10%">申报时间</th>
					<th data-options="field:'fCheckStauts',align:'center',resizable:false,sortable:true,formatter:formatPrice" style="width: 10%">申请状态</th>
					<th data-options="field:'fbidStauts',align:'center',resizable:false,sortable:true,formatter:formatbidStauts" style="width: 10%">登记状态</th>
					<!-- <th data-options="field:'fIsReceive',align:'center',resizable:false,sortable:true,formatter:formatIsReceive" style="width: 10%">验收状态</th> -->
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 8%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	//点击查询
	function queryLedger() {
		var searchData="searchData";
		$('#cgledger_list').datagrid('load', {
			/* fpCode:$('#ledger_fpCode').val(),
			fpName:$('#ledger_fpName').val(), */
			searchData:$("#"+searchData).textbox('getValue').trim(),
		});
	}
	//清除查询条件
	function clearLedger() {
		var searchData="searchData";
		/* $('#ledger_fpCode').textbox('setValue','');
		$('#ledger_fpName').textbox('setValue',''); */
		$("#"+searchData).textbox('setValue','');
		$('#cgledger_list').datagrid('load',{//清空以后，重新查一次
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
		} else if (val == 9) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
		} else {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
		}
	}

	//操作栏创建
	function CZ(val, row) {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="cgsq_ledger_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td></tr></table>';
	}
	
	//跳转查看页面
	function cgsq_ledger_detail(id) {
		var win = creatWin('查看', 780, 580, 'icon-search', '/cgsqLedger/ledgerDetail?id='+id);
		win.window('open');
	}	
	function formatIsReceive(val,row){//0-暂存 1-待验收 9-已验收 -1-已退回 -4已撤回	
		if('0'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未验收" + '</a>';
		}else if('1'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 审批中" + '</a>';
		}else if('9'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已验收" + '</a>';
		}else if('-1'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
		}else if('-4'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 已撤回" + '</a>';
		} 
		return '状态异常';
	}
	function formatbidStauts(val,row){
		
		if('0'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未登记" + '</a>';
		}else if('1'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 审批中" + '</a>';
		}else if('9'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已登记" + '</a>';
		}else if('-1'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
		}else if('-4'==val){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 已撤回" + '</a>';
		} 
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未登记" + '</a>';
	}
</script>
</body>
</html>