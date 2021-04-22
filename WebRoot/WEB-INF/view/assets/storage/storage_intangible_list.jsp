<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth">资产入账单号&nbsp;
						<input id="storage_intorage_fAssStorageCode" name="" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;取得方式&nbsp;
						<input id="storage_intorage_fGainingMethod" name="" data-options="url:'${base}/lookup/lookupsJson?parentCode=WXZCQDFS',method:'get',valueField:'code',textField:'text',editable:false" style="width: 150px;height:25px;"  class="easyui-combobox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="storage_intorage_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="storage_intorage_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="storage_intorage_add('${fAssType}')" >
							<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
		</div>
		
		<div class="list-table">
			<table id="storage_intorage_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Storage/JsonPagination?fAssType=${fAssType}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_S',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssStorageCode',align:'center'" width="30%">资产入账单号</th>
						<th data-options="field:'fGainingMethods',align:'center'" width="15%">取得方式</th>
						<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" width="20%">申请时间</th>
						<th data-options="field:'accountantEntering',align:'center',formatter: returnaccountant" width="10%" >会计补录状态</th>
						<th data-options="field:'assetEntering',align:'center',formatter: returnassetEntering" width="10%" >卡片编号登记状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
var fAssType = '${fAssType}';
$("#storage_intorage_fPurchaseDateStart").datebox({
    onSelect : function(beginDate){
        $('#storage_intorage_fPurchaseDateEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
//清除查询条件
function storage_intorage_clearTable() {
	$('#storage_intorage_fAssStorageCode').textbox('setValue',null),
	$('#storage_intorage_fGainingMethod').textbox('setValue',null),
	storage_intorage_query();
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
function CZ(val, row) {
	var user = '${user}';
	if(row.fFlowStatus=='-1'){
		return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
		+'<a href="#" onclick="storage_intorage_update(' + row.fId_S+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">' + '</a>'
		+ '</a></td><td style="width: 25px">'
		+'<a href="#" onclick="storage_intorage_delete(' + row.fId_S+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">' + '</a>'
		+ '</a>'
		+'</td></tr></table>';
	}else{
		if(row.assetEntering==0){
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
			+'<a href="#" onclick="storage_intorage_detail(' + row.fId_S+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
			+ '</a></td><td style="width: 25px">'
			+'<a href="#" onclick="storage_intorage_updateRegister(' + row.fId_S
			+ ')" class="easyui-linkbutton"><img <img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dengji1.png">' + '</a>'
			+'</td></tr></table>';
		}else{
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'<a href="#" onclick="storage_intorage_detail(' + row.fId_S+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
					+ '</a>'
					+'</td></tr></table>';
		}
	}
}
function storage_intorage_updateRegister(id) {
	if(fAssType=='ZCLX-02'){
		var win = creatWin('资产-修改', 720, 580, 'icon-search',"/Storage/registerEdit/" + id);
		win.window('open');
	}else{
		var win = creatWin('资产-修改', 720, 580, 'icon-search',"/Storage/registerIntangibleEdit/" + id);
		win.window('open');
	}
}
function returnaccountant(val, row) {
	if (val == 0||val == ''||val == null) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未补录" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 已补录" + '</span>';
	}
}
function returnassetEntering(val, row) {
	if (val == 0||val == ''||val == null) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未登记" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 已登记" + '</span>';
	}
}
function storage_intorage_query() {
	$('#storage_intorage_dg').datagrid('load', {
		fAssStorageCode : $('#storage_intorage_fAssStorageCode').val(),
		fGainingMethod : $('#storage_intorage_fGainingMethod').val(),
	});
}
function storage_intorage_add(type) {
	var win = creatWin('资产申请', 720,580, 'icon-search', '/Storage/intorageadd?forgtype='+type);
	win.window('open');
}
function storage_intorage_detail(id) {
		var win = creatWin('资产-查看', 720 ,580, 'icon-search',"/Storage/detailIntangible/" + id);
		win.window('open');
}
function storage_intorage_update(id) {
	var win = creatWin('资产-修改', 720,580, 'icon-search',
			"/Storage/editIntangible/" + id);
	win.window('open');
}
function storage_intorage_delete(id) {
	if (confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : '${base}/Storage/delete/' + id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$("#storage_intorage_dg").datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
</script>
</body>
</html>

