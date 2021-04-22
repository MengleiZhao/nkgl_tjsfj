<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search" >
					<!-- 合同编号&nbsp;
					<input id="c_app_fcCode" name="fcCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="c_app_fcTitle" name="fcTitle"  style="width: 150px;height:25px;" class="easyui-textbox"></input> -->
					
					<!-- &nbsp;&nbsp;合同金额&nbsp;
					<input id="c_app_cAmountBegin" name="" style="width: 150px;height:25px;" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" class="easyui-numberbox"></input>
					&nbsp;-&nbsp;
					<input id="c_app_cAmountEnd" name="" style="width: 150px;height:25px;" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" class="easyui-numberbox"></input> -->
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="queryContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
					</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)"  onclick="clearContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
					</a>
				</td> 
			</tr>
			<tr id="helpTr" style="display: none;"></tr>
		</table>           
	</div>
		
	<div class="list-table">
		<table id="contract_app_list" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Approval/JsonPagination',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<th data-options="field:'fcId',hidden:true"></th>
					<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
					<th data-options="field:'fcCode',align:'center',resizable:false,sortable:true" width="15%">合同编号</th>
					<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="15%">合同名称</th>
					<th data-options="field:'fcType',align:'center',resizable:false,sortable:true,formatter:formatfcType" width="10%">合同分类</th>	
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="10%">申请部门</th>
					<th data-options="field:'fReqtIME',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >申请日期</th>
					<th data-options="field:'fcAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">合同金额(元)</th>
					<!-- <th data-options="field:'fContStauts',align:'center',resizable:false,sortable:true,formatter:formatContStauts" width="8%">合同状态</th> -->
					<th data-options="field:'fsealedStatus',align:'center',resizable:false,sortable:true,formatter:formatSealedStatus" width="9.8%">盖章状态</th>
					<th data-options="field:'fFlowStauts',align:'center',resizable:false,sortable:true,formatter:formatCheckStatus" width="8%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	//查询
	function queryContract(){  
		$('#contract_app_list').datagrid('load',{ 
			/* fcCode:$('#c_app_fcCode').val(),
			fcTitle:$('#c_app_fcTitle').val(), */
			/* cAmountBegin:$('#c_app_cAmountBegin').val(),
			cAmountEnd:$('#c_app_cAmountEnd').val() */
			searchData:$('#searchData').val()
		} ); 
	}
	//清除查询条件
	function clearContract() {
		/* $('#c_app_fcCode').textbox('setValue','');
		$('#c_app_fcTitle').textbox('setValue',''); */
		/* $('#c_app_cAmountBegin').numberbox('setValue','');
		$('#c_app_cAmountEnd').numberbox('setValue',''); */
		$('#searchData').textbox('setValue','');
		$('#contract_app_list').datagrid('load',{//清空以后，重新查一次
		});
	}
	function formatfcType(val, row) {
		if (val == 'HTFL-01') {
			return '<span style="color:#666666;">' + " 采购合同" + '</span>';
		}else{
			return '<span style="color:#666666;">' + " 非采购合同" + '</span>';
		}
	}
	//合同状态
	function formatContStauts(val, row) {
		if (val == 1) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 拟定中" + '</span>';
		} else if (val == 9) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已备案" + '</span>';
		}
	}
	
	//合同盖章状态
	function formatSealedStatus(val, row) {
		if (val == 0) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未盖章" + '</span>';
		} else if (val == 1) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已盖章" + '</span>';
		}
	}
	
	//审批状态
	function formatCheckStatus(val, row) {
		if (val == 1) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</span>';
		} else if (val == 9) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</span>';
		} else if (val == -1) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</span>';
		} else if (val == -4) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</span>';
		}
	}
	
	//操作栏创建
	function CZ(val,row){
		var c = row.fFlowStauts;
		if (c == 9) {
			return null;
		} else {
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="contract_detail(' + row.fcId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
					'</a></td><td style="width: 25px">'+
					'<a href="#" onclick="contract_check(' + row.fcId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)"" src="'+base+'/resource-modality/${themenurl}/list/approval1.png">'+
					'</a></td></tr></table>';
		}
	}
	
	//审批
	function contract_check(id){
		var win = creatWin('审批', 1115, 600, 'icon-search', '/Approval/approve?id='+id);
		win.window('open');
	}
	//查看
	function contract_detail(id) {
		var win = creatWin('查看', 1115, 600, 'icon-search', '/Formulation/detail?id='+id);
		win.window('open');
	}
</script>
</body>