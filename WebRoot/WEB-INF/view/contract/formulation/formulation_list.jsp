<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="javascript:void(0)"  onclick="queryContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
					</a>
					<a href="javascript:void(0)"  onclick="clearContract();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
					</a>
				</td>
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="contract_add()">
						<img src="${base}/resource-modality/${themenurl}/button/htndsq1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/htndsq2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/htndsq1.png')">
					</a>
				</td>
			</tr>
			<tr id="helpTr" style="display: none;"></tr>
		</table>           
	</div>
		
	<div class="list-table">
		<table id="contract_list" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Formulation/JsonPagination',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:false,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<th data-options="field:'fcId',hidden:true"></th>
					<th data-options="field:'number',align:'center',resizable:false,sortable:true" width="5%">序号</th>
					<th data-options="field:'fcCode',align:'center',resizable:false,sortable:true" width="17%">合同编号</th>
					<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="20%">合同名称</th>
					<th data-options="field:'fcType',align:'center',resizable:false,sortable:true,formatter:formatfcType" width="10%">合同分类</th>
					<!-- <th data-options="field:'fOperator',align:'center',resizable:false,sortable:true" width="10%">申请人</th> -->
					<th data-options="field:'fReqtIME',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >申请日期</th>
					<th data-options="field:'fcAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">合同金额(元)</th>
					<th data-options="field:'fFlowStauts',align:'center',resizable:false,sortable:true,formatter:formatCheckStatus" width="10%">审批状态</th>
					<th data-options="field:'fsealedStatus',align:'center',resizable:false,sortable:true,formatter:formatSealedStatus" width="10%">盖章状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>	
<script type="text/javascript">
//查询
function queryContract() {
	$('#contract_list').datagrid('load', {
		/* fcCode: $('#c_fcCode').val(),
		fcTitle: $('#c_fcTitle').val(), */
		/* cAmountBegin: $('#c_cAmountBegin').val(),
		cAmountEnd: $('#c_cAmountEnd').val() */
		searchData:$('#searchData').val()
	});
}
//清除查询条件
function clearContract() {
	/* $('#c_fcCode').textbox('setValue','');
	$('#c_fcTitle').textbox('setValue',''); */
	$('#searchData').textbox('setValue','');
	/* $('#c_cAmountBegin').numberbox('setValue','');
	$('#c_cAmountEnd').numberbox('setValue',''); */
	$('#contract_list').datagrid('load',{//清空以后，重新查一次
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
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未拟定" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 拟定中" + '</span>';
	} else if (val == 9) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已备案" + '</span>';
	} else if (val == 3) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已结项" + '</span>';
	} else if (val == 5) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已归档" + '</span>';
	} else if (val == -1) {
		return '<span style="color:red;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已终止" + '</span>';
	} else if (val == -9) {
		return '<span style="color:green;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已完结" + '</span>';
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
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</span>';
	} else if (val == 1) {
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
function CZ(val, row) {
	//审批状态
	var c = row.fFlowStauts;
	if (c == 9) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="contract_detail(' + row.fcId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			    '</a>'+ '</td><td style="width: 25px">'+
				'<a href="#" onclick="contract_exportHtml(' + row.fcId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
			    '</a></td></tr></table>';
	} else if (c == 1) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="contract_detail(' + row.fcId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="reCall(\'contract_list\' ,'+ row.fcId+',\'/Formulation/reCall\''+')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
				'</a></td></tr></table>';
	} else if (c == 0 || c == -1 || c == -4) {
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="contract_detail(' + row.fcId+ ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="contract_update(' + row.fcId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="contract_delete(' + row.fcId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
				'</a></td></tr></table>';
	}
}
//打印
function contract_exportHtml(id) {
	window.open(base+"/contractExport/formulation?id="+ id);
}
//新增
function contract_add() {
	var win = creatWin('新增', 1115, 600, 'icon-search', '/Formulation/add');
	win.window('open');
}
//查看
function contract_detail(id) {
	var win = creatWin('查看', 1115, 600, 'icon-search', '/Formulation/detail?id='+id);
	win.window('open');
}
//修改
function contract_update(id) {
	var win = creatWin('修改', 1115, 600, 'icon-search', '/Formulation/edit?id='+id);
	win.window('open');
}
//删除
function contract_delete(id) {
	if (confirm('确认删除吗？')) {
		$.ajax({
			type : 'POST',
			url : base + '/Formulation/delete?id='+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#contract_list').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}

/* //金额区间校验
$('#c_cAmountBegin').numberbox({
	onChange : function(newValue,oldValue){
		var amountEnd = $('#c_cAmountEnd').val();
		if(amountEnd!=null||amountEnd!=''){
			if(newValue>amountEnd){
				$('#c_cAmountBegin').numberbox('setValue',null);
				alert("合同金额起始值不得大于截止值");
			}
		}
	}
}) */
</script>
</body>