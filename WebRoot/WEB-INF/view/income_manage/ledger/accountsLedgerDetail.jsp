<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>

	<div class="list-table">
		<table id="accountsDetailTab" class="easyui-datagrid"
		data-options="collapsible:true,url:'${base}/incomeManagerledger/accountsDetailPage?fAcaId=${fAcaId}',
		method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,rownumbers:false,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns:true" >
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'col2',align:'center',resizable:false" style="width: 20%">单号</th>
					<th data-options="field:'col1',align:'center',resizable:false" style="width: 25%">类型</th>
					<th data-options="field:'col3',align:'center',resizable:false" style="width: 20%">对方单位名称</th>
					<th data-options="field:'col4',align:'center',resizable:false" style="width: 18%">单据金额</th>
					<th data-options="field:'name',align:'center',resizable:false,formatter:CZ" style="width:12%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
		<div class="window-left-bottom-div">
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeFirstWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
		</div>
</div>


<script type="text/javascript">
//操作栏创建
function CZ(val, row) {
	return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	   '<a href="#" onclick="editAccounts1(' + row.id + ',0,\''+row.col1+'\')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a></td></tr></table>';
}

//修改
function editAccounts1(id,c,type) {
	var win = null;
	if(type=='收款'){
		win = creatWin('收款', 1115, 600, 'icon-search', "/registerAffirm/detail?id="+ id);
	}else{
		win = creatWin('付款', 1115, 600, 'icon-search', '/reimburse/detailCurrent?id='+id+'&editType=1');
	}
	 win.window('open');	
}
</script>
</body>