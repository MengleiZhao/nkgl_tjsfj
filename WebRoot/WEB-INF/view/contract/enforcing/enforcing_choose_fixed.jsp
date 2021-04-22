<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth" style="width: 610px;">资产入账单号&nbsp;
						<input id="storage_fixed_fAssStorageCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;取得方式&nbsp;
						<input id="storage_fixed_fGainingMethod" name=""  data-options="url:'${base}/lookup/lookupsJson?parentCode=QDFS',method:'get',valueField:'code',textField:'text',editable:false" style="width: 150px;height:25px;"  class="easyui-combobox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="enforcing_fixed_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="enforcing_fixed_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
					<td class="top-table-td1" style="padding-left: 10px;width: 70px;">已选入账单</td> 
					<td class="top-table-td2" >
						<input id="checkFixedAsset" class="easyui-tagbox"  style="width: 300px;height:25px;">
						<input hidden="hidden" id="checkFixedAssetid">
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="choose_fixed_save()" >
							<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
		</div>
		
		<div class="list-table">
			<table id="choose_fixed_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Enforcing/fixedstorageJsonPagination?fAssType=${fAssType}&id=${id}&selectUptId=${selectUptId}&selectContId=${selectContId}&assetEntering=1',
			method:'post',fit:true,pagination:true,singleSelect: false,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_S',hidden:true"></th>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssStorageCode',align:'center'" width="25%">资产入账单号</th>
						<th data-options="field:'fGainingMethods',align:'center'" width="15%">取得方式</th>
						<th data-options="field:'fFixedType',align:'center'" width="15%">资产分类</th>
						<th data-options="field:'fMModeShow',align:'center'" width="15%">型号</th>
						<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" width="15%">申请时间</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:ec_view" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
var fAssType = '${fAssType}';
/* $("#storage_fixed_fPurchaseDateStart").datebox({
    onSelect : function(beginDate){
        $('#storage_fixed_fPurchaseDateEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
}); */
//清除查询条件
function enforcing_fixed_clearTable() {
	$('#storage_fixed_fAssStorageCode').textbox('setValue',null),
	$('#storage_fixed_fGainingMethod').textbox('setValue',null),
	/* $('#storage_fixed_fPurchaseDateStart').datebox('setValue',null),
	$('#storage_fixed_fPurchaseDateEnd').datebox('setValue',null); */
	enforcing_fixed_query();
}
/* $(function() {
	//定义双击事件
	$('#storage_fixed_dg').datagrid({
		onDblClickRow : function(rowIndex, rowData) {
			detailDemo();
		}
	});
}); */
function enforcing_fixed_query(){
	$('#storage_fixed_dg').datagrid('load', {
		fAssStorageCode : $('#storage_fixed_fAssStorageCode').val(),
		fGainingMethod : $('#storage_fixed_fGainingMethod').val(),
		/* fPurchaseDateStart : $('#storage_fixed_fPurchaseDateStart').val(),
		fPurchaseDateEnd : $('#storage_fixed_fPurchaseDateEnd').val() */
	});
}
function choose_fixed_save(){
	var checkFixedAssetid = $('#checkFixedAssetid').val();
	checkFixedAssetid = '['+checkFixedAssetid.substring(0,checkFixedAssetid.length -1)+']';
	checkFixedAssetid = eval('(' + checkFixedAssetid + ')');
	var entities = '';
	for(var i = 0;i < checkFixedAssetid.length;i++){
		entities = entities  + JSON.stringify(checkFixedAssetid[i][0]) + ',';  
	}
	entities = '[' + entities.substring(0,entities.length -1) + ']';
	var checkFixedAssetval = $('#checkFixedAsset').tagbox('getValues');
	$('#checkFixedAssetshow').tagbox().tagbox('setValues',checkFixedAssetval);
	closeSecondWindow();
}
$('#checkFixedAsset').tagbox().tagbox({
	onRemoveTag:function(value){
		var checkFixedAssetid = $('#checkFixedAssetid').val();
		checkFixedAssetid = '['+checkFixedAssetid.substring(0,checkFixedAssetid.length -1)+']';
		checkFixedAssetid = eval('(' + checkFixedAssetid + ')');
		for (var i = checkFixedAssetid.length-1; i >= 0; i--) {
			if(checkFixedAssetid[i][1]==value){
				checkFixedAssetid.splice(i, 1);
			}else {
				
			}
		}
		var rows = $('#choose_fixed_dg').datagrid('getRows');
		for (var i = rows.length-1; i >= 0; i--) {
			if(rows[i].fAssStorageCode==value){
				$('#choose_fixed_dg').datagrid('uncheckRow',i);
			}
		}
	},
});
$('#choose_fixed_dg').datagrid({
	onCheck:function(rowIndex,rowData){
		var fAssName = $('#checkFixedAsset').tagbox('getValues');
		if(fAssName.length==0||fAssName==[]){
			fAssName = rowData.fAssStorageCode;
		}else {
			fAssName = fAssName + ',' + rowData.fAssStorageCode;
		}
		$('#checkFixedAsset').tagbox('setValues',fAssName);
		var checkFixedAssetid = $('#checkFixedAssetid').val();
		if(checkFixedAssetid==undefined||checkFixedAssetid==''||checkFixedAssetid==null){
			checkFixedAssetid = '[\''+rowData.fId_S+"\',\'"+rowData.fAssStorageCode+'\']'+ ',';
		}else {
			checkFixedAssetid = checkFixedAssetid +'[\''+rowData.fId_S+"\',\'"+rowData.fAssStorageCode+'\']'+ ',';
		}
		//checkFixedAssetid = checkFixedAssetid+rowData.fId_S+',';
		$('#checkFixedAssetid').val(checkFixedAssetid);
		$('#f_checkFixedAssetid').val(checkFixedAssetid);
	},
	onUncheck:function(rowIndex,rowData){
		var checkacceptidarr ='';
		var checkFixedAssetid = $('#checkFixedAssetid').val();
		checkFixedAssetid = '['+checkFixedAssetid.substring(0,checkFixedAssetid.length -1)+']';
		checkFixedAssetid = eval('(' + checkFixedAssetid + ')');
		for (var i = checkFixedAssetid.length-1; i >= 0; i--) {
			if(checkFixedAssetid[i][0]==rowData.fId_S){
				checkFixedAssetid.splice(i, 1);
			}else {
				checkacceptidarr = checkacceptidarr +'[\''+checkFixedAssetid[i][0]+'\','+'\''+checkFixedAssetid[i][1]+'\']'+',';
			}
		}
		//checkacceptidarr = checkacceptidarr.substring(0,checkacceptidarr.length -1);
		$('#checkFixedAssetid').val(checkacceptidarr);
		$('#f_checkFixedAssetid').val(checkacceptidarr);
		var fAssName = $('#checkFixedAsset').tagbox('getValues');
		//fAssName = fAssName.substring(1,fAssName.length -1).split(',');
		for (var i = fAssName.length-1; i >= 0; i--) {
			if(fAssName[i]==rowData.fAssStorageCode){
				fAssName.splice(i, 1);
			}
		}
		$('#checkFixedAsset').tagbox('setValues',fAssName);
	},
});

function ec_view(val, row) {
	return 	'<table><tr style="width: 105px;height:20px">'
			+'<td style="width: 25px">'
			+'<a href="#" onclick="storage_fixed_detail_e(' + row.fId_S+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
			+ '</a>'
			+'</td></tr></table>';
}

function storage_fixed_detail_e(id){
	var win = creatSearchDataWin('资产-查看', 720 ,580, 'icon-search',"/Storage/detail/" + id + '?ledger=ecDetail');
	win.window('open');
}
</script>
</body>
</html>

