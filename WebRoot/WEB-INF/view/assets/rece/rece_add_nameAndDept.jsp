<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<th width="90px" class="queryth">姓名：</th> 
					<td width="100px">
						<input id="alloca_nameAndDept_name" name="" style="width: 90px;height:25px;" class="easyui-textbox"></input>
					</td>
					<th width="90px" class="queryth">部门名称：</th> 
					<td width="90px">
						<input id="alloca_nameAndDept_departName" name=""  style="width: 90px;height:25px;" class="easyui-textbox"></input>
					</td>
					<th width="90px" class="queryth">账户：</th> 
					<td width="100px">
						<input id="alloca_nameAndDept_accountNo" name="" style="width: 90px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td >
						<a href="javascript:void(0)" onclick="queryCF();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
					</td>
					<td >
						<a href="javascript:void(0)"  onclick="PitchOn();"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
					</td>
				</tr>
			</table>           
		</div>
			<div class="list-table">
			<table id="allcoa_nameAndDept_dg" 
			data-options="collapsible:true,url:'${base}/Alloca/allocaNameAndDeptList',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'Id',hidden:true"></th>
						<th data-options="field:'accountNo',align:'center'" width="25%">账号</th>
						<th data-options="field:'name',align:'center'" width="25%">姓名</th>
						<th data-options="field:'departName',align:'center',resizable:false,sortable:true" width="25%">部门</th>
						<th data-options="field:'dpcode',align:'center',resizable:false,sortable:true" width="25%">部门编码</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
//分页样式调整
$(function(){
	var pager = $('#allcoa_nameAndDept_dg').datagrid().datagrid('getPager');	// get the pager of datagrid
	pager.pagination({
		layout:['sep','first','prev','links','next','last'/* ,'refresh' */]
	});			
});


function formatPrice(val,row){
	if (val =="1"){
		return "公开招标";
	} else if(val =="2"){
		return "待邀请招标";
	} else if(val =="3"){
		return "竞争性谈判";
	} else if(val =="4"){
		return"单一来源采购";
	}
}
function CZ(val,row){
	return '<a href="#" onclick="PitchOn('+row.fpId+')" class="easyui-linkbutton">'+"选取"+'</a>';
}
function PitchOn(id){
	var row = $('#allcoa_nameAndDept_dg').datagrid('getSelected');
	var selections = $('#allcoa_nameAndDept_dg').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		$("#R_fReceUser").textbox('setValue',row.authAccount); 
		$("#R_fReceDept").textbox('setValue',row.departName); 
		$("#R_fReceTel").textbox('setValue',row.mobileNo); 
		closeFirstWindow();
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
}
function queryCF(){  
	$('#allcoa_nameAndDept_dg').datagrid('load',{ 
		name:$('#alloca_nameAndDept_name').textbox('getValue'),
		mobileNo:$('#alloca_nameAndDept_departName').textbox('getValue'),
		accountNo:$('#alloca_nameAndDept_accountNo').textbox('getValue'),
	} ); 
}
//时间格式化
function ChangeDateFormat(val) {
	//alert(val)
    var t, y, m, d, h, i, s;
    if(val==null){
  	  return "";
    }
    t = new Date(val)
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) ;
}
</script>
</body>
</html>

