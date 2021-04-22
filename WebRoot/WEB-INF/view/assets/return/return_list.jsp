<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth">资产交回单号&nbsp;
						<input id="assReturnCodeSearch" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="asset_return_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="asset_return_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="asset_return_add()" >
							<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>
		</div>
		
		<div class="list-table">
			<table id="asset_return_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/assetReturn/JsonPagination?fAssType=ZCLX-02',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_A',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssReturnCode',align:'center'" width="20%">资产交回单号</th>
						<th data-options="field:'fAssNameAll',align:'center'" width="30%">资产名称</th>
						<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true" width="20%">申请日期</th>
						<th data-options="field:'fFlowStauts_A',align:'center',formatter: formatPrice" width="15%" >审批状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
	//清除查询条件
	function asset_return_clearTable() {
		$('#assReturnCodeSearch').textbox('setValue',null),
		asset_return_query();
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
		if(!regu.test($('#"assReturnCodeSearch"').val()) && flag == true){
			alert("请输入中文或英文！");
			flag = false;
		} 	
	}
	var fs
	function formatPrice(val, row) {
		fs=val;
		if (val == 0) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</span>';
		} else if (val == 1) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</span>';
		}else if (val == 9) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</span>';
		}else if (val == 99) {
			return '<span style="color:#666666;">' + " 已删除" + '</span>';
		} else if (val == -1) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</span>';
		}else if (val == -4) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</span>';
		}
	}
	function CZ(val, row) {
		if(row.fFlowStauts_A==0||row.fFlowStauts_A==-1){
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_detail(' + row.fId_A + ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_update(' + row.fId_A
					+ ')" class="easyui-linkbutton"><img <img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">' + '</a>'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_delete(' + row.fId_A
					+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">' + '</a>'
					+'</td></tr></table>';
		}else if(row.fFlowStauts_A==1){
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_detail(' + row.fId_A+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
					+ '</a>'+'</td><td style="width: 25px">'
					+ '<a href="#" onclick="reCall(\'asset_return_dg\' ,'+ row.fId_A+',\'/assetReturn/reCall\''+')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">' + '</a>'
					+'</td></tr></table>';
		}else if(row.fFlowStauts_A==-4){
					return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_detail(' + row.fId_A+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_update(' + row.fId_A
					+ ')" class="easyui-linkbutton"><img <img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">' + '</a>'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_delete(' + row.fId_A
					+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">' + '</a>'
					+'</td></tr></table>';
		}else {
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="asset_return_detail(' + row.fId_A+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
				    +'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="returns_exportHtml(' + row.fId_A + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
				    '</a></td></tr></table>';
		}
	}
	//打印
	function returns_exportHtml(id) {
		window.open(base+"/exportAssets/returns?id="+ id);
	}
	function asset_return_query() {
		$('#asset_return_dg').datagrid('load', {
			fAssReturnCode : $('#assReturnCodeSearch').val()
		});
	}
	function asset_return_add() {
		var win = creatWin('固定资产交回单', 1115 ,600, 'icon-search', '/assetReturn/toAssetReturnAdd?fAssType=ZCLX-02');
		win.window('open');
	}
	function asset_return_detail(id) {
			var win = creatWin('固定资产交回', 1115 ,600, 'icon-search',"/assetReturn/detail/" + id);
			win.window('open');
	}
	
	function asset_return_update(id) {
		var win = creatWin('固定资产交回单', 1115 ,600, 'icon-search',"/assetReturn/edit/" + id);
		win.window('open');
	}
	function asset_return_delete(id) {
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/assetReturn/delete/' + id,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$("#asset_return_dg").datagrid('reload');
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