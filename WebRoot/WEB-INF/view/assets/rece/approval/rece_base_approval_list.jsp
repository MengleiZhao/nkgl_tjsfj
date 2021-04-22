<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">领用单号&nbsp;
						<input id="rece_low_approval_fAssReceCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;申请人&nbsp;
						<input id="rece_low_approval_fReqUser" name="" style="width: 150px;height:25px;" class="easyui-textbox" ></input>
						&nbsp;&nbsp;领用部门&nbsp;
						<input id="rece_low_approval_fReceDept" name="" style="width: 150px;height:25px;" data-options="" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_approval_query('${fAssType }');">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_approval_clearTable('${fAssType }');">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
						
					</td>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
				
		</div>
		
		<div class="list-table">
			<table id="rece_base_approval_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Rece/approvalLow?fAssType=${fAssType }',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_R',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssReceCode',align:'center'" width="21%">资产领用单号</th>
						<!-- <th data-options="field:'fAssName',align:'center'" width="20%">领用物资名称</th> -->
						<th data-options="field:'fReqDept',align:'center',resizable:false,sortable:true" width="15%">申请部门</th>
						<th data-options="field:'fReqUser',align:'center',resizable:false,sortable:true" width="10%">申请人</th>
						<th data-options="field:'fReqTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="15%" >申请时间</th>
						<th data-options="field:'fFlowStauts_R',align:'center',formatter:formatPrice,resizable:false,sortable:true" width="10%">审批状态</th>
						<th data-options="field:'fReceStatus',align:'center',formatter:returnappfReceStatus,resizable:false,sortable:true" width="10%">确认状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
<script type="text/javascript">
function returnappfReceStatus (val,row){
	console.log(val);
	console.log(row);
	if(val==0){
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 不同意" + '</span>';
	}else if(val==null||val==''){
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未确认" + '</span>';
	}else if(val==1){
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 同意" + '</span>';
	}
	
}
//清除查询条件
function rece_approval_clearTable(val) {
	
	$('#rece_low_approval_fAssReceCode').textbox('setValue',null),
	$('#rece_low_approval_fReqUser').textbox('setValue',null),
	$('#rece_low_approval_fReceDept').textbox('setValue',null),
	rece_approval_query(val);
}
//鼠标移入图片替换
function mouseOver(img){
	var src = $(img).attr("src");
	src = src.replace(/1/, "2");
	$(img).attr("src",src);
}
	
function mouseOut(img) {
	var src = $(img).attr("src");
	src = src.replace(/2/, "1");
	$(img).attr("src",src);
}
 function CheckYou() {
	var flag = true;
	var regu = "^[a-zA-Z\u4e00-\u9fa5]+$";
	 if(!regu.test($('#rece_low_approval_fAssReceCode'+${fAssType }).val()) && flag == true){
	    	alert("请输入中文或英文！");
	    	flag = false;
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
	if(fs==9){
		if(row.fReceStatus==0){//未配置或配置了领用人不同意
			return '<a href="#" onclick="rece_approval_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
			+'&nbsp;&nbsp;'+ '<a href="#" onclick="rece_approval_config(' + row.fId_R
			+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/pz1.png">' + '</a>';
		}else{
			return '<a href="#" onclick="rece_approval_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   ';
		}
	}
	if(fs==1){
		return '<a href="#" onclick="rece_approval_detail(' + row.fId_R
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
				+'&nbsp;&nbsp;'+ '<a href="#" onclick="rece_approval_sp(' + row.fId_R
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/approval1.png">' + '</a>';
	}
}
/* $(function() {
	//定义双击事件
	$('#rece_base_approval_dg').datagrid({
		onDblClickRow : function(rowIndex, rowData) {
			detailDemo();
		}
	});
}); */
function rece_approval_query(val) {
	$('#rece_base_approval_dg').datagrid('load', {
		fAssReceCode : $('#rece_low_approval_fAssReceCode').textbox('getValue'),
		fReqUser : $('#rece_low_approval_fReqUser').textbox('getValue'),
		fReqDept : $('#rece_low_approval_fReceDept').textbox('getValue'),
	});
}
function rece_approval_add() {
	var win = creatWin('申请', 1115,600, 'icon-search',"/Rece/addlow");
	win.window('open');
}
function rece_approval_detail(id) {
		var win = creatWin('查看', 1115,600, 'icon-search','/Rece/detail/' + id);
		win.window('open');
}
/* function editCF() {
	var row = $('#rece_base_approval_dg').datagrid('getSelected');
	var selections = $('#rece_base_approval_dg').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		var win = creatWin(' ', 1115,600, 'icon-search',
				"/Storage/edit/" + row.fId_R);
		win.window('open');
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
} */
function rece_approval_sp(id) {
	var win=creatWin('审批',1115,600,'icon-search','/Rece/approvalRece/'+id);
	win.window('open');
}
function rece_approval_config(id) {
	var win=creatWin('配置',1115,600,'icon-search','/Rece/configAsset/'+id);
	win.window('open');
}

$("#rece_low_approval_fReceTimeBegin").datebox({
    onSelect : function(beginDate){
        $('#rece_low_approval_fReceTimeEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
</script>
</body>
</html>

