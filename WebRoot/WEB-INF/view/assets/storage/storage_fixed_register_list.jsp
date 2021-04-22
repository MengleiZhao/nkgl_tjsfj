<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth">资产入账单号&nbsp;
						<input id="storage_fixed_accountant_fAssStorageCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;取得方式&nbsp;
						<input id="storage_fixed_accountant_fGainingMethod" name=""  data-options="url:'${base}/lookup/lookupsJson?parentCode=QDFS',method:'get',valueField:'code',textField:'text',editable:false" style="width: 150px;height:25px;"  class="easyui-combobox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="storage_fixed_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="storage_fixed_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					 
					</td>
				</tr>
			</table>           
		</div>
		
		<div class="list-table">
			<table id="storage_fixed_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Storage/assetEnteringList?accountantEntering=1&assetEntering=0&fAssType=${fAssType}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_S',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssStorageCode',align:'center'" width="30%">资产入账单号</th>
						<th data-options="field:'fGainingMethods',align:'center'" width="20%">取得方式</th>
						<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" width="20%">申请时间</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
var fAssType = '${fAssType}';
//清除查询条件
function storage_fixed_clearTable() {
	$('#storage_fixed_accountant_fAssStorageCode').textbox('setValue',null),
	$('#storage_fixed_accountant_fGainingMethod').textbox('setValue',null),
	storage_fixed_query();
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
		 if(!regu.test($('#"storage_fixed_accountant_fAssStorageCode"').val()) && flag == true){
		    	alert("请输入中文或英文！");
		    	flag = false;
		    } 	
	} 
	function CZ(val, row) {
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'<a href="#" onclick="storage_fixed_update(' + row.fId_S
					+ ')" class="easyui-linkbutton"><img <img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">' + '</a>'
					+'</td></tr></table>';
	}
	function storage_fixed_query() {
		$('#storage_fixed_dg').datagrid('load', {
			fAssStorageCode : $('#storage_fixed_accountant_fAssStorageCode').val(),
			fGainingMethod : $('#storage_fixed_accountant_fGainingMethod').val(),
		});
	}
	function storage_fixed_update(id) {
		if(fAssType=='ZCLX-02'){
			var win = creatWin('资产-修改', 720, 580, 'icon-search',"/Storage/registerEdit/" + id);
			win.window('open');
		}else{
			var win = creatWin('资产-修改', 720, 580, 'icon-search',"/Storage/registerIntangibleEdit/" + id);
			win.window('open');
		}
	}
</script>
</body>
</html>

