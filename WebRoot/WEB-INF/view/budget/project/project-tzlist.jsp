<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 ">
		<!-- 所属库 -->
		<input id="project_list_FProLibType" value="${proLibType}" type="hidden"/>
		<input id="project_list_sbkLx" value="${sbkLx}" type="hidden"/>
	</div>
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search-pro" class="queryth" style="padding-left: 5px;">
					<%@include file="project-search-base.jsp" %>
				</td>	
				<td align="right" style="padding-right: 10px">
					<a  id='zk' href="#" onclick="zk()">
						<img style="vertical-align:bottom;text-align:left;"  src="${base}/resource-modality/${themenurl}/button/zk.png" >
					</a>
					<a id='sq' hidden="hidden" href="#" onclick="sq()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/sq.png" >
					</a>	
					&nbsp;
					<a href="#" onclick="queryProList('pro_tz_dg');">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;
					<a href="#" onclick="clearProList('pro_tz_dg');">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<c:if test="${!empty review}">
			     		<a href="#" onclick="review()">
							<img src="${base}/resource-modality/${themenurl}/button/review2.png"
								onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/review1.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/review2.png')"
							/>
						</a>&nbsp;
						<a href="#" onclick="reviewHistory()">
							<img src="${base}/resource-modality/${themenurl}/button/fhjl2.png"
								onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/fhjl1.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/fhjl2.png')"
							/>
						</a>&nbsp;
				    </c:if>
					<a href="#" onclick="exportXmtz()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daochu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
				</td>
			</tr>
		
		</table>   
	</div>



	<div class="list-table">
		<table id="pro_tz_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/project/projectPageData?proLibType=${proLibType }&sbkLx=${sbkLx}',
			method:'post',fit:true,pagination:true,singleSelect: false,scrollbarSize:0,rownumbers:true,pageSize:100000,
				selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'ck',checkbox:true"></th>
						<!-- <th data-options="field:'pageOrder',align:'center'" width="5%">序号</th> -->
						<th data-options="field:'fproCode',align:'center'" width="12%">项目编号</th>
						<th data-options="field:'fproName',align:'center',formatter:formatCellTooltip" width="12%">项目名称</th>
						<th data-options="field:'fproOrBasic',align:'center',formatter:transitionTypes" width="10%">支出类型</th>
						<!-- <th data-options="field:'firstLevelName',align:'center'" width="12%">所属一级分类名称</th>
						<th data-options="field:'firstLevelCode',align:'center'" width="10%">所属一级分类代码</th>
						<th data-options="field:'secondLevelName',align:'center'" width="12%">所属二级分类名称</th>
						<th data-options="field:'secondLevelCode',align:'center'" width="10%">所属二级分类代码</th> -->
						<th data-options="field:'fproBudgetAmount',align:'right',formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">项目预算[万元]</th>
						<th data-options="field:'fproAppliDepart',align:'center'" width="10%">申报部门</th>
						<th data-options="field:'fproAppliTime',align:'center',resizable:false,sortable:true,formatter: ProListDateFormat" width="10%">申报日期</th>
						<th data-options="field:'fproAppliPeople',align:'center', formatter:isEmpty" width="10%">项目负责人</th>
						<th data-options="field:'headerPhone',align:'center', formatter:isEmpty" width="10%">负责人电话</th>
						<!-- <th data-options="field:'fproClassName',align:'center'" width="10%">项目类别</th>
						<th data-options="field:'functionType',align:'center'" width="10%">所属功能分类</th> -->
						<th data-options="field:'planStartYear',align:'center'" width="10%">开始执行年份</th>
						<!-- <th data-options="field:'fproRollingCycle',align:'center'" width="10%">计划执行周期（年）</th> -->
						<!-- <th data-options="field:'secretLevel',align:'center'" width="10%">保密级别</th>
						<th data-options="field:'secretDeadline',align:'center'" width="10%">保密年限</th> -->
						<c:if test="${sbkLx=='xmtz' }">
							<th data-options="field:'fproStauts',align:'center',formatter:format_fproStauts" width="10%">结项状态</th>
						</c:if>
						<th data-options="field:'fexportStauts',align:'center',formatter: indexStauts" style="width: 10%">收报状态</th>
						<th data-options="field:'operation',align:'center',formatter:format_protzlist" width="10%">操作</th>
					</tr>
				</thead>
			</table>
	</div>
</div>


<form id="form_xmtz_export" method="post" enctype="multipart/form-data">
	<input type="hidden" name="sbkLx" value="xmtz">
	<input id="form_xmtz_export_fproCode" type="hidden" name="FProCode">
	<input id="form_xmtz_export_fproName" type="hidden" name="FProName">
	<input id="form_xmtz_export_fproAppliDepart" type="hidden" name="FProAppliDepart">
	<input id="form_xmtz_export_fproClassName" type="hidden" name="FProClass">
</form>

<script type="text/javascript">
//判断 是否为空
function isEmpty(str){
	if(str==null || str=="null" || str==""){
		return "未填写";
	}
	return str;
}


function format_protzlist(value, row, index){
	var rows = row.fexportStauts;
	var btn ="";
	if(rows==0 || rows==2){
	 btn =  "<table><tr style='width: 105px; height:20px'>"
				+"<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='detailPro("+row.fproId+")'>" 
				+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/select1.png'>"
				+ "</a></td></tr></table>";
	}else{
		 btn =  "<table><tr style='width: 105px; height:20px'>"
			+"<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='detailPro("+row.fproId+")'>" 
			+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/select1.png'>"
			+ "</a></td></tr></table>";
	}
	
	return btn;
}

function format_yesno(val, row){
	if (val == '1') {
		return '是';
	} else if (val == '0') {
		return '否';
	}
	return '';
}

function detailPro(proId){
	var win=creatWin('查看-项目信息',1230,630,'icon-search','/project/detail/'+proId);
	win.window('open');
}

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

//设置结项审批状态
function format_fproStauts(val, row) {
	if (val == "0") {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == "-1") {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if (val == "9") {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已完结" + '</a>';
	} else  if (val == "1"){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	}else  {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未结项" + '</a>';
	}
}
/* 
//项目台账查询
function queryProLedger(){
	var fproName="pro_tzlist_query_fproName";
	var FProAppliDepart="pro_tzlist_query_FProAppliDepart";
	var fproClass="pro_tzlist_query_fproClass";
	
	$("#pro_tz_dg").datagrid('load',{
		FProName:$("#"+fproName).textbox('getValue').trim(),
		FProAppliDepart:$("#"+FProAppliDepart).textbox('getValue').trim(),
		//FProClass:$("#"+fproClass).textbox('getValue').trim(),
		FProOrBasic:$("#essb_FProOrBasic").combobox('getValue').trim(),
	});
}

//项目台账查询条件清除
function clearProTable(){
	var fproName="pro_tzlist_query_fproName";
	var FProAppliDepart="pro_tzlist_query_FProAppliDepart";
	var fproClass="pro_tzlist_query_fproClass";
	
	$("#"+fproName).textbox('setValue','');
	$("#"+FProAppliDepart).textbox('setValue','');
	//$("#"+fproClass).combobox('setValue','');
	$("#essb_FProOrBasic").combobox('setValue','');
	$("#pro_tz_dg").datagrid('load',{});
}
 */

function choose_depart_prolist(){
	var win = creatFirstWin('选择-申报部门',600,500,'icon-search','/project/choDepart?inputId=pro_query_FProAppliDepart');
	win.window('open');
}

function exportXmtz(){
	if(confirm('是否按查询条件导出？')){
		var fproName=$("#pro_tzlist_query_fproName").val();
		var FProAppliDepart=$("#pro_tzlist_query_FProAppliDepart").val();
		var fproClass=$("#pro_tzlist_query_fproClass").val();
		
		$("#form_xmtz_export_fproName").val(fproName);
		$("#form_xmtz_export_fproAppliDepart").val(FProAppliDepart);
		$("#form_xmtz_export_fproClassName").val(fproClass);
		
		$('#form_xmtz_export').attr('action','${base}/project/export');
		$('#form_xmtz_export').submit();
	}
}
function review(){
	var checkedItems = $('#pro_tz_dg').datagrid('getChecked');
	var data='';
	$.each(checkedItems, function(index, item){
		if(data==""||data==null){
			data= item.fproId ;
		}else{
			data =data+","+ item.fproId;
		}
    });
	if(data==""||data==null) {
		alert("请勾选需要复核的项目");
		return;
	}
	var win=creatWin('修改-项目信息',1230,630,'icon-search',"/project/review?id="+data);
	win.window('open');
}

//复核记录
function reviewHistory(){
	var win=creatWin('复核记录',900,630,'icon-search',"/project/reviewHistory");
	win.window('open');
}
function transitionTypes(val){
	if(val==0){
		return "基本支出";
	}
	if(val==1){
		return "项目支出";
	}
}

//报表收报状态设置
function indexStauts(val, row) {
	 if(val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已收报" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "未收报" + '</a>';
	}
}
//报表收报	2-未收报	1-已收报
function receiveReport(stauts){
	//收报状态设置
	var fExportStauts = stauts;
	//选中的id
	var receiveData = "";
	//重复标记
	var chongfuflag = 0;
	var checkedItems = $('#pro_tz_dg').datagrid('getChecked');
	for (var i = 0; i < checkedItems.length; i++) {
		if(checkedItems[i].fexportStauts == 1 && status==1){
		 	alert(checkedItems[i].fproName + "报表已收报，请不要重复操作！");
			 chongfuflag = 1;
			 return false;
		 }
		receiveData += checkedItems[i].fproId + ',';
	}
	if(chongfuflag == 1){
		return false;
	}
	if(receiveData == "") {
		alert("请选择需要收报的报表!");
		return false;
	}
	$.ajax({ 
		type: 'POST', 
		url: base+'/project/receiveReport?receiveIdList='+ receiveData +'&fExportStauts='+ fExportStauts,
		dataType: 'json',
		success: function(data){ 
			if(data.success){
				$.messager.alert('系统提示',data.info,'info');
				$('#pro_tz_dg').datagrid('reload');
				closeWindow();
			}else{
				$.messager.alert('系统提示',data.info,'error');
				closeWindow();
			}
		}
	});
}
</script>
</body>

