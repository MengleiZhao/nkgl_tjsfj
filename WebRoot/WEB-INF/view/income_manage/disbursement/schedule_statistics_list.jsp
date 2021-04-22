<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search" class="queryth">
				<div class="top-table-search-td">选择年度：
					<input class="easyui-combobox" id="applyYear_show" name="applyYear" style="width: 150px;height: 30px;margin-left: 10px" data-options="valueField:'id',textField:'text',url:'${base}/schedule/getStatisticsYear',editable:false"/>
					&nbsp;&nbsp;&nbsp;&nbsp;选择部门：
					<input class="easyui-combobox" id="applyDept_show" name="deptName" style="width: 150px;height: 30px;margin-left: 10px" data-options="valueField:'id',textField:'text',url:'${base}/schedule/getAllDept',editable:false"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" onclick="queryScheduleStatistics();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearScheduleStatistics();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</div>
				</td>
				<td align="right" style="padding-right: 10px;width:70px;">
					<a href="#" onclick="exportDataAnalysis();">
						<img src="${base}/resource-modality/${themenurl}/button/daochu1.png"  onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>  
	</div>
	<div class="list-table">
		<table id="scheduleStatisticsDg"
		data-options="collapsible:true,url:'${base}/schedule/scheduleStatistics',
		method:'post',fit:true,pagination:false,singleSelect: true,scrollbarSize:0,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<th data-options="field:'splId',hidden:true"></th>
					<th data-options="field:'pageOrder',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fProName',align:'left',resizable:false,sortable:true" style="width: 15%">项目名称</th>
					<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 10%">申报部门</th>
					<th data-options="field:'applyYear',align:'center',resizable:false,sortable:true" style="width: 10%">申报年度</th>
					<th data-options="field:'pfAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 12%">项目总额[元]</th>
					<th data-options="field:'firstAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 12%">一季度额度[元]</th>
					<th data-options="field:'twoAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 12%">二季度额度[元]</th>
					<th data-options="field:'threeAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 12%">三季度额度[元]</th>
					<th data-options="field:'fourAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 12%">四季度额度[元]</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript" src="${base}/resource/office/datagrid-export.js"></script>
<script type="text/javascript">

$('#scheduleStatisticsDg').datagrid({
	onLoadSuccess:function(index,field,value){
		var rows = $("#scheduleStatisticsDg").datagrid("getRows");
		
		$('#scheduleStatisticsDg').datagrid('insertRow', {
			index : 0,
			row : {
				deptName : '合计',
				pfAmount : rows[0].pfAmountTotal,
				firstAmount : rows[0].firstAmountTotal,
				twoAmount : rows[0].twoAmountTotal,
				threeAmount : rows[0].threeAmountTotal,
				fourAmount : rows[0].fourAmountTotal
			}
		});
		
		/* //合并单元格
		var merges = [{
			index:0,
			rowspan:1
		}];
		for(var i=0; i<merges.length; i++)
		$('#scheduleStatisticsDg').datagrid('mergeCells',{
			index:merges[i].index,
			field:'fProName',
			rowspan:merges[i].rowspan
		}); */
	}
});

//查询
function queryScheduleStatistics() {
	$("#scheduleStatisticsDg").datagrid('load',{
		applyYear:$("#applyYear_show").combobox('getValue').trim(),
		deptName:$("#applyDept_show").combobox('getValue').trim(),
	});

}
//清除查询条件
function clearScheduleStatistics() {
	$("#applyYear_show").combobox('setValue',''),
	$("#applyDept_show").combobox('setValue',''),
	$("#scheduleStatisticsDg").datagrid('load',{});
}

//导出
function exportDataAnalysis(){
	var time=ChangeDateFormat(new Date());
	var rows = $('#scheduleStatisticsDg').datagrid('getRows');
		$('#scheduleStatisticsDg').datagrid('toExcel', {
	    filename: '计划统计.xls',
	    rows: rows,
	    worksheet: 'Worksheet'
	}); 
}
</script>
</body>