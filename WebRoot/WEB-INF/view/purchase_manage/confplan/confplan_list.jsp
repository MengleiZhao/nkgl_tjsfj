<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="confplan_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">单据编号&nbsp;
					<input id="sq_flistNum" name="flistNum" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				&nbsp;&nbsp;申请部门
					<input id="sq_freqDept" name="freqDept" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				&nbsp;&nbsp;采购类型&nbsp;
					<select class="easyui-combobox" id="sq_fprocurType" name="fprocurType"  style="width: 150px; height:25px;" data-options="editable:false,panelHeight:'auto'">
						<option value="">--请选择--</option>
						<option value="A10" >货物</option>
						<option value="A20" >工程采购</option>
						<option value="A30" >服务</option>
					 </select>
				&nbsp;&nbsp;审批状态&nbsp;
					 <select class="easyui-combobox" id="sq_fcheckStauts" name="fcheckStauts"  style="width: 150px; height:25px;" data-options="editable:false,panelHeight:'auto'">
						<option value="">--请选择--</option>
						<option value="0" >暂存</option>
						<option value="-1" >已退回</option>
						<option value="9" >已审批</option>
						<option value="2" >待审批</option>
					 </select>
					&nbsp;&nbsp;<a href="#" onclick="queryConfplan();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearConfplanTable();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>		
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="addConfplan()">
						<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>
	</div>
		
		
		
		<div style="margin: 0 10px 0 10px;height: 445px;">	
			<table id="confplan_tab" class="easyui-datagrid"
				data-options="collapsible:true,url:'${base}/cgconfplangl/confplanPageData',
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
						<th data-options="field:'fcheckStauts',align:'left',resizable:false,sortable:true,formatter:formatPrice" style="width: 11%">审批状态</th>
						<th data-options="field:'operation',align:'left',formatter:format_confplan" width="12%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	

</div>

	<script type="text/javascript">
	
	//分页样式调整
	$(document).ready(function() {
		var pager = $('#confplan_tab').datagrid().datagrid('getPager');	// get the pager of datagrid
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
		
		$('#confplan_tab').datagrid('load',{
			flistNum : $('#sq_flistNum').textbox('getValue'),
			freqDept : $('#sq_freqDept').textbox('getValue'),
			fprocurType : $('#sq_fprocurType').textbox('getValue'),
			fcheckStauts : $('#sq_fcheckStauts').textbox('getValue')
		});
	}
		//清除查询条件
		function clearConfplanTable() {
			$("#sq_flistNum").textbox('setValue','');
			$("#sq_freqDept").textbox('setValue','');
			$("#sq_fprocurType").combobox('setValue','');
			$("#sq_fcheckStauts").combobox('setValue','');
			$('#confplan_tab').datagrid('load',{//清空以后，重新查一次
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
	function format_confplan(val,row) {		
					if (c == 9 || c == 1 || c == 2) {
						return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
							   '<a href="#" onclick="confplan_detail(' + row.fplId + ')" class="easyui-linkbutton">'+
							   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
							   '</a></td></tr></table>';
					} else if(c == 0 || c == -1) {
						return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
								'<a href="#" onclick="confplan_detail(' + row.fplId + ')" class="easyui-linkbutton">'+
						   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
						   		'</a>'+ '</td><td style="width: 25px">'+
								'<a href="#" onclick="confplan_update(' + row.fplId + ')" class="easyui-linkbutton">'+
								'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
								'</a>' + '</td><td style="width: 25px">'+
								'<a href="#" onclick="confplan_delete(' + row.fplId + ')" class="easyui-linkbutton">'+
								'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
								'</a></td></tr></table>';
					}			
		
	}
	function showA(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
	}
	function showB(obj){
		obj.src=base+'/resource-modality/${themenurl}/select2.png';
	}
	function showC(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/update1.png';
	}
	function showD(obj){
		obj.src=base+'/resource-modality/${themenurl}/update2.png';
	}
	function showE(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/delete1.png';
	}
	function showF(obj){
		obj.src=base+'/resource-modality/${themenurl}/delete2.png';
	}
	
	
	//新增页面
	function addConfplan() {
		var win = parent.creatWin('新增', 970, 580, 'icon-search', '/cgconfplangl/add');
		win.window('open');
	}
	
	 //查看
	 function confplan_detail(id) {
		var win = parent.creatWin(' ', 970, 580, 'icon-search',"/cgconfplangl/detail?id=" + id);
		win.window('open'); 
	} 
	//修改
	function confplan_update(id) {
		var win =parent.creatWin(' ', 970, 580, 'icon-search',"/cgconfplangl/edit?id=" + id);
		win.window('open'); 
  }
	
	 
	
	 //删除
	function confplan_delete(id) {
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/cgconfplangl/delete?id='+id,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$('#confplan_tab').datagrid("reload");
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
		}
	}
	
	
	</script>
</body>

