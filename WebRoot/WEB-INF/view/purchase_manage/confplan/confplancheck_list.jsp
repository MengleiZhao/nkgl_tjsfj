<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="confplan_check_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">单据编号&nbsp;
					<input id="check_flistNum" name="flistNum" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				&nbsp;&nbsp;申请部门
					<input id="check_freqDept" name="freqDept" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				&nbsp;&nbsp;采购类型&nbsp;
					<select class="easyui-combobox" id="check_fprocurType" name="fprocurType"  style="width: 150px; height:25px;" data-options="editable:false,panelHeight:'auto'">
						<option value="">--请选择--</option>
						<option value="A10" >货物</option>
						<option value="A20" >工程采购</option>
						<option value="A30" >服务</option>
					 </select>
					<a href="#" onclick="queryConfplan();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearConfplanTable();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>		
			</tr>
		</table>
	</div>
		
		
		
		<div style="margin: 0 10px 0 10px;height: 445px;">	
			<table id="confplan_check_tab" class="easyui-datagrid"
				data-options="collapsible:true,url:'${base}/cgconfplancheck/confplancheckPageData',
			method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'fplId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" width="5%">序号</th>
						<th data-options="field:'flistNum',align:'left'" width="13%">单据编号</th>
						<th data-options="field:'freqDept',align:'left'" width="10%">申请部门</th>
						<th data-options="field:'freqTime',align:'left',formatter:ChangeDateFormat" width="13%">申请日期</th>
						<th data-options="field:'fprocurType',align:'left',formatter:formatter_confleixing" width="12%">采购类型</th>
						<th data-options="field:'freqLinkMen',align:'left'" width="12%">申请部门联系人</th>
						<th data-options="field:'flinkTel',align:'left'" width="12%">联系电话</th>
						<th data-options="field:'fcheckStauts',align:'left',resizable:false,sortable:true,formatter:formatStauts" style="width: 11%">审批状态</th>
						<th data-options="field:'operation',align:'left',formatter:format_confplancheck" width="12%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	

</div>

	<script type="text/javascript">
	
	//分页样式调整
	$(document).ready(function() {
		var pager = $('#confplan_check_tab').datagrid().datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			layout:['sep','first','prev','links','next','last'/* ,'refresh' */]
		});	
	});
	
	function formatter_confleixing(val,row){
		if (val == "A10") {
			return "货物";
		} else if (val == "A20") {
			return "工程采购" ;
		} else if (val == "A30") {
			return "服务";
		} 
		
	}
		
		
	//点击查询
	function queryConfplan() {
		
		$('#confplan_check_tab').datagrid('load',{
			flistNum : $('#check_flistNum').textbox('getValue'),
			freqDept : $('#check_freqDept').textbox('getValue'),
			fprocurType : $('#check_fprocurType').textbox('getValue'),
		});
	}
	//清除查询条件
	function clearConfplanTable() {
		$("#check_flistNum").textbox('setValue','');
		$("#check_freqDept").textbox('setValue','');
		$("#check_fprocurType").combobox('setValue','');
		$('#confplan_check_tab').datagrid('load',{//清空以后，重新查一次
		});
	}

	//设置审批状态
	var c;
	function formatStauts(val, row) {
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
	function format_confplancheck(val,row) {		
		if (c == 9 ) {
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="confplan_detail(' + row.fplId + ')" class="easyui-linkbutton">'+
	   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   		'</a>'+ '</td></tr></table>';
		} else {
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="confplan_detail(' + row.fplId + ')" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="confplancheck(' + row.fplId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/check1.png">'+
					'</a></td></tr></table>';
		}						
		
	}
	function showA(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/check1.png';
	}
	function showB(obj){
		obj.src=base+'/resource-modality/${themenurl}/check2.png';
	}
	//查看
	 function confplan_detail(id) {
		var win = parent.creatWin(' ', 970, 580, 'icon-search',"/cgconfplangl/detail?id=" + id);
		win.window('open'); 
	}
	//跳转审批页面
	function confplancheck(id) {
		var win = creatWin(' ', 970, 580, 'icon-search', "/cgconfplancheck/check?id="+ id);
		win.window('open');
	}
	 	
	</script>
</body>

