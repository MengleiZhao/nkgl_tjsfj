<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-table">
		<table id="enforcing_plan_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Enforcing/planJsonPagination/${id}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<th data-options="field:'fPlanId',hidden:true"></th>
					<th data-options="field:'fContId_R',hidden:true"></th>
					<th data-options="field:'fReceProperty',align:'center',formatter:paytype" width="10%">付款性质</th>
					<th data-options="field:'fRecStage',align:'center',resizable:false" width="10%">付款阶段</th>
					<th data-options="field:'fRecePlanAmount',align:'right',resizable:false" width="16%">预计付款金额(元)</th>
					<th data-options="field:'fRecePlanTime',align:'center',formatter:ChangeDateFormat" width="15%">预计付款日期</th>
					<th data-options="field:'fReceAmount',align:'right',resizable:false" width="16%">实际付款金额(元)</th>
					<th data-options="field:'fReceTime',align:'center',resizable:false,formatter:ChangeDateFormat" width="15%">实际付款时间</th>
					<th data-options="field:'payStauts',align:'center',resizable:false,formatter:RPsatuts" width="10%">付款状态</th>
					<th data-options="field:'name',align:'center',resizable:false,formatter:CZ" width="11%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
		
	<div class="win-left-bottom-div">
		<a href="javascript:void(0)" onclick="closeWindow()">
			<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a>
	</div> 
</div>
<script type="text/javascript">
	//付款性质
	function paytype(val, row) {
		if (val == "FKXZ-01") {
			return '<span >' + "首款" + '</span>';
		} else if (val == "FKXZ-02") {
			return '<span >' + "阶段款" + '</span>';
		}else if (val == "FKXZ-03") {
			return '<span >' + "验收款" + '</span>';
		}else if (val == "FKXZ-04") {
			return '<span >' + "质保款" + '</span>';
		}
	}

	//设置审批状态
	function RPsatuts(val, row) {
		if (val == 0) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "未开始" + '</a>';
		} else if (val == -1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
		} else if (val == 9) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已通过" + '</a>';
		} else if (val == -4) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已撤回" + '</a>';
		} else if (val == 1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "审核中" + '</a>';
		} else if (val == 99) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已付款" + '</a>';
		} 
	}
	
	//操作栏创建
	function CZ(val, row) {
		//付款状态
		var c = row.payStauts;
		if (c == 0 || c == -1 || c == -4) {
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="enforcing_plan_detail(' + ${id} + ',' + row.fPlanId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOver(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
					'</a></td><td style="width: 25px">'+
					'<a href="#" onclick="pay(' + ${id} + ',' + row.fPlanId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOver(this)" src="'+base+'/resource-modality/${themenurl}/list/pay1.png">'+
					'</a></td></tr></table>';
		} else if (c == 9 || c == 2 || c == 99) {
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="enforcing_plan_detail(' + ${id} + ',' + row.fPlanId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOver(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
					'</a></td></tr></table>';
		} else {
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="reCall(\'enforcing_plan_dg\',' + row.fPlanId + ',\'/Enforcing/contractReCall\')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
					'</a></td></tr></table>';
		}
	}
	//付款申请
	function pay(id, fPlanId) {
		var win = creatFirstWin('新增', 1115, 600, 'icon-search', '/Enforcing/edit?id='+id+'&fPlanId='+fPlanId);
		win.window('open');
	}
	//查看
	function enforcing_plan_detail(id, fPlanId) {
		var win = creatFirstWin('查看', 1115, 600, 'icon-search', '/Enforcing/detail?id='+id+'&fPlanId='+fPlanId);
		win.window('open');
	}
</script>
</body>