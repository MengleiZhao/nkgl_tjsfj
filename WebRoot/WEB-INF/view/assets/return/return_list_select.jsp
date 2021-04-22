<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">卡片编号&nbsp;
						<input id="fAssCode_select" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="queryReturnList();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						<a href="javascript:void(0)"  onclick="clearReturnList();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
						<a href="javascript:void(0)"  onclick="getReturnListSelect();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/baocun1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/baocun2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/baocun1.png')">
						</a>
					</td>
				</tr>
			</table>           
		</div>
		<div class="list-table" >
			<table id="return_list_select_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/AssetBasicInfo/getAssetReturnSelect',
			method:'post',fit:true,pagination:true,selectOnCheck: true,checkOnSelect:true,
			remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'fAssId',hidden:true"></th>
						<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'fAssCode',align:'center'" style="width: 20%">卡片编号</th>
						<th data-options="field:'fFixedType',align:'center'" style="width: 20%">资产分类</th>
						<th data-options="field:'fAssName',align:'center'" style="width: 20%">资产名称</th>
						<th data-options="field:'fSPModel',align:'center'" style="width: 20%">型号</th>
						<th data-options="field:'fReceDate',align:'center',resizable:false,sortable:true" style="width: 15%">领用时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

<script type="text/javascript">
$(function(){
	
});

function queryReturnList(){  
	$('#return_list_select_dg').datagrid('load',{ 
		fAssCode:$('#fAssCode_select').textbox('getValue')
	} ); 
}

function clearReturnList(){
	$("#fAssCode_select").textbox('setValue','');
	$('#return_list_select_dg').datagrid('load',{//清空以后，重新查一次
	});
}

function getReturnListSelect(){
	var selections = $('#return_list_select_dg').datagrid('getSelections');
	if (selections.length == 0) {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
		return;
	}
	for(i = 0; i < selections.length; i++){
		$('#return_list_info_dg').datagrid('appendRow',{
			fAssCode_AR: selections[i].fAssCode,
			fFixedTypeName_AR: selections[i].fFixedType,
			fFixedType_AR: selections[i].fFixedTypeCode,//fFixedTypeId
			fAssName_AR: selections[i].fAssName,
			fAssSpecif_AR: selections[i].fSPModel,
			fReceDate: selections[i].fReceDate
		});
	}
	closeFirstWindow();
}
</script>
</body>
</html>