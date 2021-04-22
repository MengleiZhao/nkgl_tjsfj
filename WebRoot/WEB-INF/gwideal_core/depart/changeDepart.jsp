<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-table" style="height: 350px;">
			<table id="user_choose_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/user/changeDepartdata',
			method:'post',fit:true,pagination:false,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'fOperator',align:'center',resizable:false" width="50%">部门名称</th>
						<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" width="50%">姓名</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="text-align: left;height: 20px">
			<span style="color:#ff6800">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;✧温馨提示：双击数据选择</span>
		</div>
</div>	


<script type="text/javascript">

$(function(){
	initDg_user_add();
});

//初始化datagrid点击事件
function initDg_user_add(){
	$('#user_choose_dg').datagrid({
		onDblClickRow:function(index,row){
			 $.ajax({
				async:true,
				url:base+'/relogin?id='+row.fOperatorId,
				type:'POST',
				success:function(data){
					data = eval("(" + data + ")");
					if(data.success){
						window.location.reload();
						closeFirstWindow();
					}
				}
			}); 
		}
	});
}


</script>
</body>

