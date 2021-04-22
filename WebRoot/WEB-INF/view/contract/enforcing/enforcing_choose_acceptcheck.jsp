<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" style="width: 580px;">验收单号&nbsp;
						<input id="CF_add_PN_facpCode" name="fpCode" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;项目编号&nbsp;
						<input id="CF_add_PN_fpCode" name="fpName" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="choose_accept_query();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						<a href="javascript:void(0)"  onclick="choose_accept_clear();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
					</td>
					<td style="width: 8px;"></td>
					<td class="top-table-td1">已选验收单:</td> 
					<td class="top-table-td2">
						<input id="checkaccept" class="easyui-tagbox"  style="width: 300px;height:25px;">
						<input hidden="hidden" id="checkacceptid">
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="choose_accept_save();" style="float: right;">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>	
				</tr>
			</table>           
		</div>
		<div class="list-table">
			<table id="enforcing_choose_accept_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgreceive/acceptCheckPagination?fcId=${id }&fCheckStauts=9',
			method:'post',fit:true,pagination:false,singleSelect: false,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'facpId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'facpCode',align:'center'" style="width: 15%">验收单号</th>
						<th data-options="field:'fpName',align:'center'" style="width: 18%">项目名称</th>
						<th data-options="field:'fpItemsNames',align:'center'" style="width: 10%">品目名称</th>
						<th data-options="field:'amount',align:'center'" style="width: 8%">采购金额(元)</th>
						<th data-options="field:'facpUsername',align:'center',resizable:false,sortable:true" width="8%">申请人</th>
						<th data-options="field:'fDepartName',align:'center',resizable:false,sortable:true" width="10%">申请部门</th>
						<th data-options="field:'facpTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="10%" >申请日期</th>
						<th data-options="field:'fMatchStauts',align:'center',resizable:false,sortable:true,formatter:formatMatch" style="width: 10%">验收状态</th>
						<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:enforce_view" width="6%">操作</th>
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
	
});
function choose_accept_save(){
	var checkacceptid = $('#checkacceptid').val();
	checkacceptid = '['+checkacceptid.substring(0,checkacceptid.length -1)+']';
	checkacceptid = eval('(' + checkacceptid + ')');
	var entities = '';
	for(var i = 0;i < checkacceptid.length;i++){
		entities = entities  + JSON.stringify(checkacceptid[i][0]) + ',';  
	}
	entities = '[' + entities.substring(0,entities.length -1) + ']';
	var checkacceptval = $('#checkaccept').tagbox('getValues');
	$('#checkacceptshow').tagbox().tagbox('setValues',checkacceptval);
	closeSecondWindow();
}
function choose_accept_query(){  
	$('#enforcing_choose_accept_dg').datagrid('load',{ 
		fpCode:$('#CF_add_PN_fpCode').textbox('getValue'),
		facpCode:$('#CF_add_PN_facpCode').textbox('getValue'),
		forgtype:$('#F_fOrgType').val()
	} ); 
}
function choose_accept_clear(){
	$("#CF_add_PN_fpCode").textbox('setValue','');
	$("#CF_add_PN_facpCode").textbox('setValue','');
	$('#enforcing_choose_accept_dg').datagrid('load',{//清空以后，重新查一次
	});
}
//设置验收状态
function formatMatch(val, row) {
	if(val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + "待验收" + '</a>';
	} else if(val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + "已验收" + '</a>';
	}
}

/* function save(){
	var checkacceptid = $('#checkacceptid').val();
	checkacceptid = '['+checkacceptid.substring(0,checkacceptid.length -1)+']';
	checkacceptid = eval('(' + checkacceptid + ')');
	var entities = '';
	for(var i = 0;i < checkacceptid.length;i++){
		entities = entities  + JSON.stringify(checkacceptid[i][0]) + ',';  
	}
	entities = '[' + entities.substring(0,entities.length -1) + ']';
	var checkacceptval =$('#checkaccept').tagbox('getValues');
	closeFirstWindow();
} */
$('#checkaccept').tagbox().tagbox({
	onRemoveTag:function(value){
		var checkacceptid = $('#checkacceptid').val();
		checkacceptid = '['+checkacceptid.substring(0,checkacceptid.length -1)+']';
		checkacceptid = eval('(' + checkacceptid + ')');
		for (var i = checkacceptid.length-1; i >= 0; i--) {
			if(checkacceptid[i][1]==value){
				checkacceptid.splice(i, 1);
			}else {
				
			}
		}
		var rows = $('#enforcing_choose_accept_dg').datagrid('getRows');
		for (var i = rows.length-1; i >= 0; i--) {
			if(rows[i].fpName==value){
				$('#enforcing_choose_accept_dg').datagrid('uncheckRow',i);
			}
		}
	},
});
$('#enforcing_choose_accept_dg').datagrid({
	onCheck:function(rowIndex,rowData){
		var fAssName = $('#checkaccept').tagbox('getValues');
		if(fAssName.length==0||fAssName==[]){
			fAssName = rowData.fpName;
		}else {
			fAssName = fAssName + ',' + rowData.fpName;
		}
		$('#checkaccept').tagbox('setValues',fAssName);
		var checkacceptid = $('#checkacceptid').val();
		var type=0;
		if(rowData.fpItemsName=='PMMC-4'||rowData.fpItemsName=='PMMC-5'){
			type=1;
		}
		if(checkacceptid==undefined||checkacceptid==''||checkacceptid==null){
			checkacceptid = '[\''+rowData.facpId+"\',\'"+rowData.fpName+"\',\'"+type+'\']'+ ',';
		}else {
			checkacceptid = checkacceptid +'[\''+rowData.facpId+"\',\'"+rowData.fpName+"\',\'"+type+'\']'+ ',';
		}
		//checkacceptid = checkacceptid+rowData.facpId+',';
		$('#checkacceptid').val(checkacceptid);
		$('#f_checkacceptid').val(checkacceptid);
	},
	onUncheck:function(rowIndex,rowData){
		var checkacceptidarr ='';
		var checkacceptid = $('#checkacceptid').val();
		checkacceptid = '['+checkacceptid.substring(0,checkacceptid.length -1)+']';
		checkacceptid = eval('(' + checkacceptid + ')');
		for (var i = checkacceptid.length-1; i >= 0; i--) {
			if(checkacceptid[i][0]==rowData.facpId){
				checkacceptid.splice(i, 1);
			}else {
				checkacceptidarr = checkacceptidarr +'[\''+checkacceptid[i][0]+'\','+'\''+checkacceptid[i][1]+'\']'+',';
			}
		}
		//checkacceptidarr = checkacceptidarr.substring(0,checkacceptidarr.length -1);
		$('#checkacceptid').val(checkacceptidarr);
		$('#f_checkacceptid').val(checkacceptidarr);
		var fAssName = $('#checkaccept').tagbox('getValues');
		//fAssName = fAssName.substring(1,fAssName.length -1).split(',');
		for (var i = fAssName.length-1; i >= 0; i--) {
			if(fAssName[i]==rowData.fpName){
				fAssName.splice(i, 1);
			}
		}
		$('#checkaccept').tagbox('setValues',fAssName);
	},
});

function enforce_view(val, row) {
	return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="receive_detail(' + row.facpId + ',\'' + row.fpItemsName + '\')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			'</a></td></tr></table>';
}

//验收查看
function receive_detail(id,fpItemsName) {
	var win =null;
	if(fpItemsName=='PMMC-4' || fpItemsName=='PMMC-5'){
		win = creatFirstWin('查看', 1070, 580, 'icon-search', "/cgreceive/detail?id="+id);
	}else{
		win = creatFirstWin('查看', 790, 580, 'icon-search', "/cgreceive/detail?id="+id);
	}
	win.window('open');
}
</script>
</body>
</html>

