<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false"  style="width:200px;" >
		<ul id="fixedTypes" class="easyui-tree" data-options="url:'${base}/assetType/tree',animate:true,lines:true" ></ul>
	</div>
	<div data-options="region:'center'" style="background-color: #f0f5f7">
	  <div style="height: 10px;background-color:#f0f5f7 "></div>
	  
		<div class="list-top" >
			<form action="" id="query_user_form" class="easyui-form" style="margin-bottom: 0px;" onkeydown="if(event.keyCode==13){queryUser();return false; }">
			  <table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-td1">资产名称:</td> 
					<td class="top-table-td2">
						<input id="choose_recefixed_Names" class="easyui-textbox" size="15"  maxlength="10" style="width: 150px;height:25px;"/>
					</td>
					<td style="width: 8px;"></td>
					<td style="width: 26px;">
						<a href="#" onclick="queryUser();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
					<td style="width: 8px;"></td>
					<td class="top-table-td1">已选资产:</td> 
					<td class="top-table-td2">
						<input id="checkAsset" class="easyui-tagbox"  style="width: 300px;height:25px;">
						<input hidden="hidden" id="checkAssetid">
					</td>
					<td style="width: 8px;"></td>
					<td style="width: 26px;">
						<a href="#" onclick="save();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
					<td align="right" style="padding-right: 10px"></td>
				</tr>
			</table>
			</form>

		</div>
		<div class="list-table" style="height: 88%">
		<table id="choose_recefixed_dgs" border="0" class="easyui-datagrid"
				data-options="singleSelect:true,collapsible:true,url:'${base}/assetType/findbyfFixedType?fAssType=${type}',
				method:'post',fit:true,pagination:true,singleSelect: false,
				selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th> 
					<!-- <th data-options="field:'fbId',checkbox:true"></th>-->
					<th data-options="field:'fAssCode',align:'center',resizable:false,sortable:true" width="15%">卡片编号</th>
					<th data-options="field:'fAssName',align:'center',resizable:false,sortable:true" width="30%">资产名称</th>
					<th data-options="field:'fSPModel',align:'center',resizable:false,sortable:true," width="10%">规格型号</th>
					<th data-options="field:'fActionDate',align:'center',resizable:false,formatter: ChangeDateFormat" width="10%">取得日期</th>
					<th data-options="field:'fFinancialDate',align:'center',resizable:false,formatter: ChangeDateFormat" width="10%">财务入账日期</th>
					<th data-options="field:'fUsedStautsShow',align:'center',resizable:false" width="10%">使用状态</th>
					<th data-options="field:'fAvailableStautsShow',align:'center',resizable:false" width="10%">可用状态</th>
					<th data-options="field:'fUseName',align:'center',resizable:false," width="10%">当前使用人</th>
					<th data-options="field:'fReceDate',align:'center',resizable:false,formatter: ChangeDateFormat" width="15%">最近一次领用时间</th>
					<th data-options="field:'name',align:'center',resizable:false,formatter:historyCZ" width="10%">流转记录</th>
				</tr>
			</thead>
		</table>
		</div>
    </div>
</div>
<script type="text/javascript">
function historyCZ(val,row){
	return '<a href="#" onclick="rece_history_detail(' + row.fAssId
			+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>';
};
function rece_history_detail(id) {
	var win = creatSearchDataWin('查看', 900,500, 'icon-search','/AssetFlow/receflow?id=' + id);
	win.window('open');
};
var tabId = '${tabId}';
function save(){
	var checkAssetid = $('#checkAssetid').val();
	checkAssetid = '['+checkAssetid.substring(0,checkAssetid.length -1)+']';
	checkAssetid = eval('(' + checkAssetid + ')');
	var entities = '';
	for(var i = 0;i < checkAssetid.length;i++){
		entities = entities  + JSON.stringify(checkAssetid[i][0]) + ',';  
	}
	entities = '[' + entities.substring(0,entities.length -1) + ']';
	var rows = $('#'+tabId).datagrid('getRows');
	for (var i = rows.length-1; i >= 0; i--) {
		$('#'+tabId).datagrid('deleteRow',i);
	}
	
	var url = base+'/Handle/getbyAssetid?assetid='+entities;
	$('#'+tabId).datagrid('reload',url);
	closeFirstWindow();
}
$('#checkAsset').tagbox().tagbox({
	onRemoveTag:function(value){
		var checkAssetid = $('#checkAssetid').val();
		checkAssetid = '['+checkAssetid.substring(0,checkAssetid.length -1)+']';
		checkAssetid = eval('(' + checkAssetid + ')');
		for (var i = checkAssetid.length-1; i >= 0; i--) {
			if(checkAssetid[i][1]==value){
				checkAssetid.splice(i, 1);
			}else {
			}
		}
		var rows = $('#choose_recefixed_dgs').datagrid('getRows');
		for (var i = rows.length-1; i >= 0; i--) {
			if(rows[i].fAssName==value){
				$('#choose_recefixed_dgs').datagrid('uncheckRow',i);
			}
		}
	},
});
$('#choose_recefixed_dgs').datagrid({
	onCheck:function(rowIndex,rowData){
		var fAssName = $('#checkAsset').tagbox('getValues');
		if(fAssName.length==0||fAssName==[]){
			fAssName = rowData.fAssName;
		}else {
			fAssName = fAssName + ',' + rowData.fAssName;
		}
		$('#checkAsset').tagbox('setValues',fAssName);
		var checkAssetid = $('#checkAssetid').val();
		if(checkAssetid==undefined||checkAssetid==''||checkAssetid==null){
			checkAssetid = '[\''+rowData.fAssId+"\',\'"+rowData.fAssName+'\']'+ ',';
		}else {
			checkAssetid = checkAssetid +'[\''+rowData.fAssId+"\',\'"+rowData.fAssName+'\']'+ ',';
		}
		$('#checkAssetid').val(checkAssetid);
	},
	onUncheck:function(rowIndex,rowData){
		var checkAssetidarr ='';
		var checkAssetid = $('#checkAssetid').val();
		checkAssetid = '['+checkAssetid.substring(0,checkAssetid.length -1)+']';
		checkAssetid = eval('(' + checkAssetid + ')');
		for (var i = checkAssetid.length-1; i >= 0; i--) {
			if(checkAssetid[i][0]==rowData.fAssId){
				checkAssetid.splice(i, 1);
			}else {
				checkAssetidarr = checkAssetidarr +'[\''+checkAssetid[i][0]+'\','+'\''+checkAssetid[i][1]+'\']'+',';
			}
		}
		$('#checkAssetid').val(checkAssetidarr);
		var fAssName = $('#checkAsset').tagbox('getValues');
		for (var i = fAssName.length-1; i >= 0; i--) {
			if(fAssName[i]==rowData.fAssName){
				fAssName.splice(i, 1);
			}
		}
		$('#checkAsset').tagbox('setValues',fAssName);
	},
});

var type = '${type}';
//分页样式调整
$(function(){
	 var addr_tree = $("#fixedTypes").tree({  
	        url:'${base}/assetType/tree?type='+type,  
	        method:"post",  
	        onSelect:function(node){
	        	var node=$('#fixedTypes').tree('getSelected');
				$('#choose_recefixed_dgs').datagrid('load',{ 
					fAssName:$('#choose_recefixed_Names').val(),
					fFixedTypeCode:node.code,
				});
	        },
	        onLoadSuccess:function(node,data){  
	        	//找到第一条数据
	        	var n = $('#fixedTypes').tree('find', data[0].id);
		        //调用选中事件
		        $('#fixedTypes').tree('select', n.target);
	         }  
	    }); 
});
//清除查询条件
function clearTable() {
	$('#YB_ftName').textbox('setValue',null);
	$('#YB_fPeriod').textbox('setValue',null);
}
function view(){
	var row = $('#choose_recefixed_dgs').datagrid('getSelected');
	var selections = $('#choose_recefixed_dgs').datagrid('getSelections');
	if(null!=row && selections.length==1){
	    var win=creatWin('查看-经济分类科目',850,250,'icon-edit','/economic/view/'+row.fid);
	    win.window('open');
	}else{
		 $.messager.alert('系统提示','请选择一条要查看的数据！','info');
	}
}
function deleteEconomic(){
	var node=$('#fixedTypes').tree('getSelected');
	var row =$('#choose_recefixed_dgs').datagrid('getChecked');
	var selections = $('#choose_recefixed_dgs').datagrid('getSelections');
	var da=$('#choose_recefixed_dgs').datagrid('getChecked');
	var fids=[15];
	var str;
	for(var i=0;i<da.length;i++){
		fids[i]=(da[i].fid);
		str=str+','+da[i].fid;
	}
	if(null!=row){
		$.messager.confirm('系统提示','确认删除吗?',function(r){
			if(r){
				$.ajax({ 
					type: 'POST', 
					url: '${base}/economic/delete/'+row[0].fid,
					dataType: 'json',  
					data:{'fid':row[0].fid},
					success: function(data){ 
						if(data.success){
							$.messager.alert('系统提示',data.info,'info');
							$("#choose_recefixed_dgs").datagrid('reload');
						}else{
							$.messager.alert('系统提示',data.info,'error');
						}
					} 
				});
			}
		});
	}else{
		 $.messager.alert('系统提示','请选择一条数据！','info');
	}
}
function queryUser(){
	//var node=$('#fixedTypes').tree('getSelected');
	$('#choose_recefixed_dgs').datagrid('load',{ 
		fAssName:$('#choose_recefixed_Names').val(),
		//fFixedTypeCode:node.code,
	}); 
}
function addEconomic(){
	var node=$('#fixedTypes').tree('getSelected');
	if(null!=node){
		win=creatWin('新增-经济分类科目',760,380,'icon-search','/economic/add?departId='+node.id);
	}
	win.window('open');
}
function editUser(){
	var row = $('#choose_recefixed_dgs').datagrid('getSelected');
	var selections = $('#choose_recefixed_dgs').datagrid('getSelections');
	if(null!=row && selections.length==1){
	    var win=creatWin('修改-经济分类科目',740,580,'icon-search','/economic/edit/'+row.fid);
	    win.window('open');
	}else{
		 $.messager.alert('系统提示','请选择一条数据！','info');  
	}
}
$('#fixedTypes').tree({
 	onClick: function(node){
		$('#choose_recefixed_dgs').datagrid('load',{ 
			fFixedTypeCode:node.code
		}); 
 	}
}); 
  /*  function da(){
   	var node=$('#fixedTypes').tree('getSelected');
   	console.log(node)
   } */
/* $('#fixedTypes').tree({
	onClick: function(node){
		console.log(node)
		$('#choose_recefixed_dgs').datagrid('load',{ 
			fPeriod:node.text,
			fbId:node.id
			
		}); 
	}
}); */
function streetChange(streetCode){
	$('#streetJwh').combobox('reload',"${base}/street/jwh?streetCode="+streetCode);
}
</script>

