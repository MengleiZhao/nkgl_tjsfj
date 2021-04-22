<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<form action="" class="easyui-form" style="margin-bottom: 0px;" onkeydown="if(event.keyCode==13){queryEconomic();return false; }">
			  <table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-td1">科目名称:</td> 
					<td class="top-table-td2">
						<input id="ec_name" class="easyui-textbox" size="15"  maxlength="10" style="width: 150px;height:25px;"/>
					</td>
					
					<td class="top-table-td1">科目编号:</td> 
					<td class="top-table-td2">
						<input id="ec_code" class="easyui-textbox" size="15"  maxlength="10" style="width: 120px;height:25px;"/>
					</td>
					
					<td class="top-table-td1">上级编号:</td> 
					<td class="top-table-td2">
						<input id="ec_pid" class="easyui-textbox" size="15"  maxlength="10" style="width: 120px;height:25px;"/>
					</td>
					
					<td class="top-table-td1">应用年份:</td> 
					<td class="top-table-td2">
						<input id="ec_year" class="easyui-textbox" size="15"  maxlength="10" style="width: 120px;height:25px;"/>
					</td>
					
					<td style="width: 26px;">
						<a href="#" onclick="queryEconomic();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
					
					<td style="width: 8px;"></td>
					
					<td style="width: 26px;">
						<a href="#" onclick="addEconomic();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
		<div class="list-table" style="height: 90%">
		<table id="ecg_dg" border="0" class="easyui-datagrid"
				data-options="singleSelect:true,collapsible:true,url:'${base}/economicClassification/JsonPagination',
				method:'post',fit:true,pagination:true,singleSelect: true,
				selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<!-- <th data-options="field:'ck',checkbox:true"></th> 
					<th data-options="field:'fbId',checkbox:true"></th>-->
					<th data-options="field:'fid',hidden:true"></th>
					<th data-options="field:'number',align:'center'" width="5%">序号</th>
					<th data-options="field:'code',align:'center',resizable:false,sortable:true" width="15%">科目编号</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true" width="25%">科目名称</th>
					<th data-options="field:'leve',align:'center',resizable:false,sortable:true,formatter:kmjb " width="10%">科目级别</th>
					<th data-options="field:'pid',align:'center',resizable:false" width="15%">上级科目编号</th>
					<th data-options="field:'year',align:'center',resizable:false" width="10%">应用年份</th>
					<th data-options="field:'type',align:'center',resizable:false,formatter:kmlx" width="10%">科目类型</th>
					<th data-options="field:'operation',align:'left',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
				</tr>
			</thead>
		</table>
		</div>
	  
   
</div>
<script type="text/javascript">
//分页样式调整
$(function(){
	
});
//清除查询条件
function clearTable() {
	$('#YB_ftName').textbox('setValue',null);
	$('#YB_fPeriod').textbox('setValue',null);
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
//科目级别
function kmjb(val, row){
	if(row.leve=='KMJB-01'){
		return '一级科目';
	}else if(row.leve=='KMJB-02'){
		return '二级科目';
	}
}

//科目类型
function kmlx(val, row){
	if(row.type=='KMLX-01'){
		return '支出科目';
	}else if(row.type=='KMLX-09'){
		return '收入科目';
	}
}
	function view(){
		var row = $('#ecg_dg').datagrid('getSelected');
		var selections = $('#ecg_dg').datagrid('getSelections');
		if(null!=row && selections.length==1){
		    var win=creatWin('查看-经济分类科目',850,250,'icon-edit','/economic/view/'+row.fid);
		    win.window('open');
		}else{
			 $.messager.alert('系统提示','请选择一条要查看的数据！','info');
		}
	}
	
	function deleteEconomic(id){
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/economicClassification/delete/' + id,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$("#ecg_dg").datagrid('reload');
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
		}
	}
	
	function queryEconomic(){
		$('#ecg_dg').datagrid('load',{ 
			name:$('#ec_name').val(),
			code:$('#ec_code').val(),
			year:$('#ec_year').val(),
			pid:$('#ec_pid').val()
		}); 
	}
	function addEconomic(){
		var win=creatWin('新增-政府支出经济分类科目',760,380,'icon-search','/economicClassification/add');
		win.window('open');
	}
	function editEconomic(id){
		var win=creatWin('修改-政府支出经济分类科目',760,380,'icon-search','/economicClassification/edit/'+id);
		win.window('open');
	}
	
	function CZ(val, row) {
		return '<a href="#" onclick="editEconomic(' + row.fid
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">' + '</a>'
				+'&nbsp;&nbsp;'+'<a href="#" onclick="deleteEconomic(' + row.fid
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">' + '</a>';
	}
</script>
</body>
</html>

