<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 ">
		<!-- 所属库 -->
		<input id="project_list_FProLibType" value="${proLibType }" type="hidden"/>
		<input id="project_list_sbkLx" value="${sbkLx }" type="hidden"/>
	</div>
	
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
			
				<td class="top-table-search" class="queryth">
					&nbsp;&nbsp;项目编号&nbsp;
					<input id="pro_list_query_fproCode_${listid}" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;项目名称&nbsp;
					<input id="pro_list_query_fproName_${listid}" style="width: 150px;height:25px;" class="easyui-textbox"/>
					<!-- <input id="pro_list_query_fproClassName" style="width: 150px;height:25px;" class="easyui-textbox"/> -->
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryProList(${listid});">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearProList(${listid});">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>	
		</table>   
	</div>



	<div class="list-table">
		<table id="knot_check_list" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/projectknot/JsonPagination',
			method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns:true" >
				<thead>
					<tr>
						<th data-options="field:'fkId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" width="5%">序号</th>
						<th data-options="field:'fproCode',align:'center'" width="10%">项目编号</th>
						<th data-options="field:'fproName',align:'center'" width="15%">项目名称</th>
						<th data-options="field:'freqUserName',align:'center'" width="10%">申报人</th>
						<th data-options="field:'freqDept',align:'center'" width="10%">申报部门</th>
						<th data-options="field:'freqTime',align:'center',resizable:false,sortable:true,formatter: ProListDateFormat" width="10%">申报时间</th>
						<th data-options="field:'fproBudgetAmount',align:'center',formatter:listToFixed" width="10%">项目预算（万元）</th>
						<th data-options="field:'fproOutAmount',align:'center',formatter:listToFixed" width="10%">实际使用（万元）</th>
						<th data-options="field:'fproEfficiency',align:'center',formatter:zbzxjd" width="7%">完成率</th>
						<th data-options="field:'flowStauts',align:'center',formatter:format_fflowStauts" width="8%">审批状态</th>
						<th data-options="field:'operation',align:'left',formatter:format_oper" width="5%">操作</th>
					</tr>
				</thead>
			</table>
	</div>
</div>


<script type="text/javascript">



//时间格式化
function ProListDateFormat(val) {
	//alert(val)
    var t, y, m, d, h, i, s;
    if(val==null){
  	  return "";
    }
    t = new Date(val);
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) ;
}
function format_oper(value, row, index){
	
	var btn = "";
	btn = btn + "<table><tr style='width: 105px; height:20px'>";
	//详情
	var btn1 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='knot_detail("+row.fkId+")'>" 
				+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/select1.png'>"
				+ "</a></td>";
	//审批
	btn3 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='knot_check("+row.fkId+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/check1.png'>"
	+ "</a></td>";
	
	btn = btn +  btn1  + btn3;
	
	btn = btn + "</td></tr></table>";
	return btn;
}
function format_fflowStauts(val, row){
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 已退回" + '</a>';
	}
}

//查看
function knot_detail(id) {
	var win = parent.creatWin(' ', 970, 580, 'icon-search',"/projectknot/detail?id=" + id);
	win.window('open'); 
} 
//修改
function knot_check(id) {
	var win =parent.creatWin(' ', 970, 580, 'icon-search',"/projectknot/check?id=" + id);
	win.window('open'); 
}

</script>
</body>

