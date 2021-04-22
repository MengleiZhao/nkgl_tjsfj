<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">领用单号&nbsp;
						<input id="rece_fixed_fAssReceCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;申请人&nbsp;
						<input id="rece_fixed_fReqUser" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;申请部门&nbsp;
						<input id="rece_fixed_fReqDept" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_fixed_accept_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_fixed_accept_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
					</td>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
				
		</div>
		
		<div class="list-table">
			<table id="rece_fixed_accept_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Rece/acceptJsonPagination?fAssType=ZCLX-02',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_R',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssReceCode',align:'center'" width="15%">资产领用单号</th>
						<th data-options="field:'fAssName',align:'center'" width="20%">资产名称</th>
						<th data-options="field:'fReqDept',align:'center',resizable:false,sortable:true" width="10%">申请部门</th>
						<th data-options="field:'fReqUser',align:'center',resizable:false,sortable:true" width="10%">申请人</th>
						<th data-options="field:'fReqTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >申请时间</th>
						<th data-options="field:'fFlowStauts_R',align:'center',formatter:formatPrice,resizable:false,sortable:true" width="10%">审批状态</th>
						<th data-options="field:'fAcceptStatus',align:'center',formatter:returnfAcceptStatus,resizable:false,sortable:true" width="10%">登记状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

<script type="text/javascript">
$("#rece_fixed_fReceTimeBegin").datebox({
    onSelect : function(beginDate){
        $('#rece_fixed_fReceTimeEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
//清除查询条件
function rece_fixed_accept_clearTable() {
	$('#rece_fixed_fAssReceCode').textbox('setValue',null),
	$('#rece_fixed_fReqUser').textbox('setValue',null),
	$('#rece_fixed_fReqDept').textbox('setValue',null),
	rece_fixed_accept_query();
}
function returnfAcceptStatus(val, row) {
	if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已登记" + '</span>';
	} else if (val == 0||val == ''||val == null) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 未登记" + '</span>';
	}
}
var fs
function formatPrice(val, row) {
	fs=val;
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</span>';
	}else if (val == 9) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</span>';
	}else if (val == 99) {
		return '<span style="color:#666666;">' + " 已删除" + '</span>';
	} else if (val == -1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</span>';
	}
}
function CZ(val, row) {
	if(row.fAcceptStatus==1){
		return '<a href="#" onclick="rece_fixed_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
	}
	if(row.fAcceptStatus==0||row.fAcceptStatus==''||row.fAcceptStatus==null){
		return '<a href="#" onclick="rece_fixed_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
		+'&nbsp;&nbsp;'+ '<a href="#" onclick="rece_Accept_detail(' + row.fId_R
		+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/shouli1.png">' + '</a>';
	}
}
function rece_fixed_accept_query() {
	$('#rece_fixed_accept_dg').datagrid('load', {
		fAssReceCode : $('#rece_fixed_fAssReceCode').val(),
		fReqDept : $('#rece_fixed_fReqDept').val(),
		fReqUser : $('#rece_fixed_fReqUser').val(),
	});
}
function rece_fixed_detail(id) {
	var win = creatWin('查看', 1115,600, 'icon-search',"/Rece/detail/" + id);
	win.window('open');
}

function rece_Accept_detail(id) {
	var win = creatWin('补录', 1115,600, 'icon-search',"/Rece/detail/"+id+'?detail=2');
	win.window('open');
}

function Rece_fixed_update(id) {
	var win = creatWin('修改', 1115,600, 'icon-search',"/Rece/edit/" + id);
	win.window('open');
}
function formatOper(value, row, index) {
	return 'sfsdf';
	//return '<a href="#" onclick="test('+index+')">修改</a>';  
	//    return '<a href="javascript:void(0);" onclick="openviewzfrw(\''+row.person+'\',\''+row.data_status+'\')"><font color="blue">走访</font></a>'; 
}
</script>
</body>
</html>

