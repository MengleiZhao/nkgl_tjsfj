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
				<td class="top-table-search-pro" class="queryth" style="padding-left: 5px;">
					<%-- &nbsp;&nbsp;项目编号&nbsp;
					<input id="pro_list_query_fproCode_${listid}" style="width: 150px;height:25px;" class="easyui-textbox"/>
					&nbsp;&nbsp;项目名称&nbsp;
					<input id="pro_list_query_fproName_${listid}" style="width: 150px;height:25px;" class="easyui-textbox"/>
					<c:if test="${listid==2}">
						&nbsp;&nbsp;上报状态&nbsp;
						<input id="pro_list_query_FFlowStauts_${listid}" style="width: 150px;height: 25px" class="easyui-combobox" data-options="editable:false,panelHeight:'auto',url:'${base}/lookup/lookupsJson?parentCode=SPZT',method:'POST',valueField:'code',textField:'text',editable:false"/>
					</c:if>
					
					<c:if test="${listid==5}">
						&nbsp;&nbsp;执行进度区间&nbsp;
						<select id="pro_list_query_efficiency1_${listid}" name="" style="width: 150px;height:25px;" class="easyui-combobox">
							<option value="">--请选择--</option>
							<%@include file="../../percentage.jsp" %>
						</select>
						&nbsp;-&nbsp;
						<select id="pro_list_query_efficiency2_${listid}" name="" style="width: 150px;height:25px;" class="easyui-combobox">
							<option value="">--请选择--</option>
							<%@include file="../../percentage.jsp" %>
						</select>
					</c:if>
					
					<c:if test="${listid==6||listid==8 || listid==9}">
						&nbsp;&nbsp;申报时间&nbsp;
						<input id="pro_list_query_FProAppliTime1_${listid}" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateBegin[pro_list_query_FProAppliTime2_${listid}]"/>
						&nbsp;-&nbsp;
						<input id="pro_list_query_FProAppliTime2_${listid}" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateEnd[pro_list_query_FProAppliTime1_${listid}]"/>
					</c:if> --%>
					<%@include file="project-search-base.jsp" %>
				</td>	
				<td align="right" style="padding-right: 10px">
					<a  id='zk' href="#" onclick="zk()">
						<img style="vertical-align:bottom;text-align:left;"  src="${base}/resource-modality/${themenurl}/button/zk.png" >
					</a>
					<a id='sq' hidden="hidden" href="#" onclick="sq()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/sq.png" >
					</a>	
					&nbsp;&nbsp;
					<a href="#" onclick="queryProList('pro_dg_${listid}');">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearProList('pro_dg_${listid}');">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				<c:if test="${proLibType==1 && sbkLx=='xmsb' }">
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="addPro()">
							<img src="${base}/resource-modality/${themenurl}/button/xmsb1.png"
								onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/xmsb2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/xmsb1.png')"
							/>
						</a>
					</td>
				</c:if>
		</table>   
	</div>

	<div class="list-table">
		<table id="pro_dg_${listid}" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/project/projectPageData?proLibType=${proLibType}&sbkLx=${sbkLx}'<c:if test="${listid==2}">+'&FProOrBasic=1'</c:if>,
			method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,onLoadSuccess:onLoadSuccessMothed,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns:true" >
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'pageOrder',align:'center'" width="5%">序号</th>
						<th data-options="field:'fproCode',align:'center'" width="15%">项目编号</th>
						<th data-options="field:'fproName',align:'center',formatter:formatCellTooltip" width="20%">项目名称</th>
						<c:if test="${sbkLx=='xmsp' }">
							<th data-options="field:'fproOrBasic',align:'center',formatter:transitionType" width="10%">支出类型</th>
						</c:if>
						<th data-options="field:'fproAppliPeople',align:'center'" width="10%">申报人</th>
						<th data-options="field:'fproAppliDepart',align:'center'" width="10%">申报部门</th>
						<th data-options="field:'fproAppliTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" width="10%">申报时间</th>
						<th data-options="field:'fproBudgetAmount',align:'right',formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">项目预算[万元]</th>
						<c:if test="${proLibType==3||proLibType==4||sbkLx=='bmxmjx'||sbkLx=='dwxmjx'}">
							<th data-options="field:'syAmount',align:'right',formatter:function(value,row,index){return fomatMoney(value,2);}" width="10%">剩余金额[万元]</th>
							<th data-options="field:'efficiency',align:'center',formatter:zbzxjd" width="10%">执行进度</th>
						</c:if>
						<c:if test="${sbkLx=='xmsb' }">
							<th data-options="field:'fflowStauts',align:'center',formatter:format_fflowStauts" width="10%">审批状态</th>
						</c:if>
						<c:if test="${sbkLx=='xmtz' }">
							<th data-options="field:'fproStauts',align:'center',formatter:format_fproStauts" width="10%">结项状态</th>
						</c:if>
						<th data-options="field:'operation',align:'center',formatter:format_oper" 
						<c:if test="${sbkLx=='xmsb'}">width="10%"</c:if>
						<c:if test="${sbkLx=='xmsp'}">width="10%"</c:if>
						<c:if test="${proLibType==4}">width="10%"</c:if>
						<c:if test="${proLibType==3||sbkLx=='bmxmjx'||sbkLx=='dwxmjx'}">width="10%"</c:if>>操作</th>
					</tr>
				</thead>
			</table>
	</div>
</div>
<script type="text/javascript">
$("#pro_list_query_FProAppliTime1_"+${listid}).datebox({
    onSelect : function(beginDate){
        $('#pro_list_query_FProAppliTime2_'+${listid}).datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
function onLoadSuccessMothed(){//页面加成之后
	if('${listid}'==2){//申报
		$('#pro_list_query_FProOrBasic').combobox().combobox('readonly','true');
		//$('#pro_list_query_FProOrBasic').combobox().combobox('setValue','项目支出');
		$('#pro_list_query_FProOrBasic').combobox().combobox('select','1');
	}
};
function initCombobox(){
	$('#pro_query_subject1').combobox({
	    onChange:function(newValue,oldValue){
	       //重置二级分类选择
	        $('#pro_query_subject2').combobox('reload','${base}/project/getSubject2?parentCode='+newValue);
	    }
	});
}
/* 
function queryProList(id){ 
	if(id==1){
		$("#pro_dg_"+id).datagrid('load',{
			FProCode:$("#pro_list_query_fproCode_"+id).textbox('getValue').trim(),
			FProName:$("#pro_list_query_fproName_"+id).textbox('getValue').trim(),
			FFlowStauts:11,
			//FProOrBasic:$("#essb_FProOrBasic_"+id).textbox('getValue').trim()
		});
	}
	if(id==2){
		var flowStauts = $("#pro_list_query_FFlowStauts_"+id).textbox('getValue');
		if(flowStauts=="SPZT-01"){
			flowStauts="-11";//已退回-11（备选库）
		} else if(flowStauts=="SPZT-02"){
			flowStauts="0";//暂存0（备选库）
		} else if(flowStauts=="SPZT-03"){
			flowStauts="11";//待审批11（备选库）
		} else if(flowStauts=="SPZT-04"){
			flowStauts="19";//已审批19（备选库）
		} 
		
		$("#pro_dg_"+id).datagrid('load',{
			FProCode:$("#pro_list_query_fproCode_"+id).textbox('getValue').trim(),
			FProName:$("#pro_list_query_fproName_"+id).textbox('getValue').trim(),
			FFlowStauts:flowStauts,
			//FProOrBasic:$("#essb_FProOrBasic_"+id).textbox('getValue').trim()
		});
	}
	if(id==5){
		$("#pro_dg_"+id).datagrid('load',{
			FProCode:$("#pro_list_query_fproCode_"+id).textbox('getValue').trim(),
			FProName:$("#pro_list_query_fproName_"+id).textbox('getValue').trim(),
			efficiency1:$("#pro_list_query_efficiency1_"+id).textbox('getValue'),
			efficiency2:$("#pro_list_query_efficiency2_"+id).textbox('getValue'),
			//FProOrBasic:$("#essb_FProOrBasic_"+id).textbox('getValue').trim()
		});
	}
	if(id==6||id==8||id==9){
		$("#pro_dg_"+id).datagrid('load',{
			FProCode:$("#pro_list_query_fproCode_"+id).textbox('getValue').trim(),
			FProName:$("#pro_list_query_fproName_"+id).textbox('getValue').trim(),
			FProAppliTime1:$("#pro_list_query_FProAppliTime1_"+id).datebox('getValue').trim(),
			FProAppliTime2:$("#pro_list_query_FProAppliTime2_"+id).datebox('getValue').trim(),
			//FProOrBasic:$("#essb_FProOrBasic_"+id).textbox('getValue').trim()
		});
	}
}

function clearProList(id){
		//$("#essb_FProOrBasic_"+id).textbox('setValue','');
	if(id==1){
		$("#pro_list_query_fproCode_"+id).textbox('setValue','');
		$("#pro_list_query_fproName_"+id).textbox('setValue','');
	}
	if(id==2){
		$("#pro_list_query_FFlowStauts_"+id).textbox('setValue','');
		$("#pro_list_query_fproCode_"+id).textbox('setValue','');
		$("#pro_list_query_fproName_"+id).textbox('setValue','');
	}
	if(id==5){
		$("#pro_list_query_fproCode_"+id).textbox('setValue','');
		$("#pro_list_query_fproName_"+id).textbox('setValue','');
		
		 $("#pro_list_query_efficiency1_"+id).combobox('setValue','');
		$("#pro_list_query_efficiency2_"+id).combobox('setValue','');
	}
	if(id==6||id==8||id==9){
		$("#pro_list_query_fproCode_"+id).textbox('setValue','');
		$("#pro_list_query_fproName_"+id).textbox('setValue','');
		$("#pro_list_query_FProAppliTime1_"+id).datebox('setValue','');
		$("#pro_list_query_FProAppliTime2_"+id).datebox('setValue','');
	}
	$("#pro_dg_"+id).datagrid('load',{FFlowStauts:11});
} */

function addPro(){
	 var win = creatWin('项目信息申报', 1230,630, 'icon-search', '/project/add?fproOrBasic=1');
	 win.window('open');
}
function detailPro(proId){
	var win = creatWin('查看-项目信息', 1230,630, 'icon-search', '/project/detail/'+proId+'?sign=1');
	win.window('open');
}
function editPro(id){
	var win = creatWin('修改-项目信息', 1230,630, 'icon-search', '/project/edit/'+id);
	win.window('open');
}
function verdictPro(id){
	var win = creatWin('审批-项目信息', 1230,630, 'icon-search', "/project/verdict/"+id+"?listid=${listid}");
	win.window('open');
}
function deletePro(id){
	if(confirm("确认删除吗？")){
		$.ajax({ 
			type: 'POST', 
			url: '${base}/project/delete/'+id,
			dataType: 'json',  
			success: function(data){ 
				if(data.success) {
					$.messager.alert('系统提示',data.info,'info');
					$("#pro_dg_${listid }").datagrid('reload');
				}else {
					$.messager.alert('系统提示',data.info,'error');
				}
			} 
		}); 
	}
}
function expListlawHelp(){
	 //var win=creatWin('导出-法律服务接待登记表',400,120,'icon-search','/demo/exportList');
	  //win.window('open');
	if(confirm("按当前查询条件导出？")){
	   var queryForm = document.getElementById("lawHelp_list_form");
		queryForm.setAttribute("action","${base}/demo/expList");
		queryForm.submit();
	}
}

function transitionType(val){
	if(val==0){
		return "基本支出";
	}
	if(val==1){
		return "项目支出";
	}
}

function overPro(proId){
	if(confirm("确认将该项目转移至结转库？")){
		//需要弹出修改页面提供项目结转修改
		var win = creatWin('结转-项目信息',1230,630,'icon-search',"/project/over/"+proId+"?listid=${listid}");
		win.window('open');
		
	}
}
function format_oper(value, row, index){
	var proLibType = '${proLibType}';//所属库 1：备选库 2：上报库   3：执行中 4：已完结
	var sbkLx = '${sbkLx }';//申报库类型 项目申报xmsb审批xmsp台账xmtz(页面跳转参数)
	var btn = "";
	btn = btn + "<table><tr style='width: 105px; height:20px'>";
	//详情
	var btn1 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='detailPro("+row.fproId+")'>" 
				+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/select1.png'>"
				+ "</a></td>";
	//删除
	var btn2 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='deletePro("+row.fproId+")'>"
				+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/delete1.png'>"
				+ "</a></td>";
	var btn3 = "";
	if(proLibType=='1' && sbkLx=='xmsb' ){//申报库-项目申报库 + 未提交  显示修改
		//修改
		if(row.fflowStauts=='0' || row.fflowStauts=='-11' || row.fflowStauts=='-14'){
			btn3 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='editPro("+row.fproId+")'>"
					+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/update1.png'>"
					+ "</a></td>";
		}else {//撤回
			btn3 = '<td style="width:25px"><a href="#" onclick="reCall(\'pro_dg_${listid}\','+row.fproId+',\'/project/reCall\')">'
			+ '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'
			+ '</a></td>';
		}
	}else if(proLibType=='1' && sbkLx=='xmsp'){//申报库-项目审批库 显示审批
		//审批
		btn3 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='verdictPro("+row.fproId+")'>"
				+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/check1.png'>"
				+ "</a></td>";
	}else {//申报库-项目审批库 显示审批
		
	}
	
	/* var	btn3 = "<td style='width:35px'><a href='javascript:void(0)' style='color:blue' onclick='verdictPro("+row.fproId+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/verdict2.png'>"
	+ "</a></td>"; */
	
	//结转
	var btn5 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='overPro("+row.fproId+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/over1.png'>"
	+ "</a></td>";
	
	//采购
	var btn6 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='cgPro("+row.fproId+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/cg1.png'>"
	+ "</a></td>";
	
	/* var btn7 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='htPro("+row.fproId+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/ht1.png'>"
	+ "</a></td>"; */
	
	//支出记录
	var btn7 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='zhichuDetail("+"\""+row.fproCode+"\""+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/zcjl1.png'>"
	+ "</a></td>";
	btn = btn +  btn1  + btn3;
	if(proLibType==3 ){//结转 
		btn = btn + " "  + btn6;
	}
	if(proLibType==1 && sbkLx=='xmsb' && (row.fflowStauts==0||row.fflowStauts==-11 || row.fflowStauts=='-4')) {
		btn = btn + btn2;
	}
	if(proLibType==4) {
		btn = btn + btn6 + btn7;
	}
	btn = btn + "</td></tr></table>";
	return btn;
}
function format_fflowStauts(val, row){
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == 11) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	} else if (val == 12) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	} else if (val == 19) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else if (val == -11) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	}else if (val == -14) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</a>';
	}
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
function formatter_libName(val, row){
	if (val == 1) {
		return '申报库';
	} else if (val == 2) {
		return '备选库';
	} else if (val == 3) {
		return '执行库';
	} else if (val == 4) {
		return '结转库';
	}
	return '';
}

function choose_depart_prolist(){
	var win = creatFirstWin('选择-申报部门',600,500,'icon-search','/project/choDepart?inputId=xmsb_query_FProAppliDepart');
	win.window('open');
}

//支出记录
function zhichuDetail(proCode) {
	var win = parent.creatWin('支出记录追踪', 970, 580, 'icon-search', '/project/zhichuDetail?fProCode='+proCode);
	win.window('open');
}
function cgPro(proId) {
	var win = creatFirstWin('采购信息',1300,580,'icon-search','/cgsqsp/list?proId='+proId);
	win.window('open');
}
function htPro(proId) {
	var win = creatFirstWin('合同信息',1300,580,'icon-search','/Ledger/list?proId='+proId);
	win.window('open');
}
</script>
</body>