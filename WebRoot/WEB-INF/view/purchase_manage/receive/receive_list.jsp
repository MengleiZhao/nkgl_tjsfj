<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cgys_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					<!-- 验收单号&nbsp;
					<input id="receive_facpCode" name="facpCode" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;项目名称&nbsp;
					<input id="receive_fpName" name="fpName" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					 -->
					 &nbsp;&nbsp;
					<a href="#" onclick="queryReceive();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearReceive();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					
				</td>
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="addReceive();" style="float: right;">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>								
			</tr>
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">
		<table id="receive_list" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/cgreceive/pageList',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'facpId',hidden:true"></th>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'fpItemsName',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'facpCode',align:'center'" style="width: 15%">验收单号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 22.5%">项目名称</th>
					<th data-options="field:'fpItemsNames',align:'center',resizable:false,sortable:true" style="width: 14%">品目名称</th>
					<th data-options="field:'amount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 8%">采购金额[元]</th>
					<th data-options="field:'facpTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 10%">申报时间</th>
					<th data-options="field:'fMatchStauts',align:'center',resizable:false,sortable:true,formatter:formatMatch" style="width: 8%">验收状态</th>
					<th data-options="field:'fCheckStauts',align:'center',resizable:false,sortable:true,formatter:formatRecive" style="width: 8%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryReceive() {
	var searchData="searchData";
	$("#receive_list").datagrid('load', {
		/* facpCode:$("#receive_facpCode").val(),
		fpName:$("#receive_fpName").val(), */
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
	
//清除查询条件
function clearReceive() {
	var searchData="searchData";
	/* $("#receive_facpCode").textbox('setValue','');
	$("#receive_fpName").textbox('setValue',''); */
	$("#"+searchData).textbox('setValue','');
	$("#receive_list").datagrid('load',{//清空以后，重新查一次
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
//设置验收状态
function formatRecive(val, row) {
	if(val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if(val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if(val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else if(val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	} else if(val == -4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</a>';
	}
}

//设置评价信息状态
function formatEval(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未评价" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已评价" + '</a>';
	} 
}

//操作栏创建
function CZ(val, row) {
	//验收状态
	var c = row.fCheckStauts;
	if (c == 9) {//已验收
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="receive_detail(' + row.facpId + ',\'' + row.fpItemsName + '\')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="exportHtml(' + row.facpId + ',\'' + row.fpItemsName + '\')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
			   '</a></td></tr></table>';
				/* </td><td style="width: 25px">'+
				'<a href="#" onclick="htPro(' + row.fpId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/ht1.png">'+
				'</a></td> */
	} else if (c == 0) {//暂存
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="receive_update(' + row.facpId + ',\'' + row.fpItemsName + '\')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/yanshou1.png">'+
				'</a></td></tr></table>';
	} else if (c == -1 || c == -4) {//已退回、已撤回
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="receive_detail(' + row.facpId + ',\'' + row.fpItemsName + '\')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="receive_update(' + row.facpId + ',\'' + row.fpItemsName + '\')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
				'</a></td></tr></table>';
	} else if (c == 1) {//待验收
		return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="receive_detail(' + row.facpId + ',\'' + row.fpItemsName + '\')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a>'+'</td><td style="width: 25px">'+
				'<a href="#" onclick="reCall(\'receive_list\',' + row.facpId + ',\'/cgreceive/reCall\')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
				'</a></td></tr></table>';
	}
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
//验收新增
function addReceive(id) {
	//选择采购单
	var win = creatWin('选择采购项目', 940, 600, 'icon-search','/cgreceive/add');
	win.window('open');
}
//验收修改
function receive_update(id,fpItemsName) {
	var win =null;
	if(fpItemsName=='PMMC-4' || fpItemsName=='PMMC-5'){
		win = creatFirstWin('修改', 1070, 580, 'icon-search', "/cgreceive/edit?id="+id);
	}else{
		win = creatFirstWin('修改', 790, 580, 'icon-search', "/cgreceive/edit?id="+id);
	}
	win.window('open');
}
/* //评价
function supplier_eval(id) {
	var win = creatWin('评价', 768, 550, 'icon-search', "/evalsupplier/trhisdiscussYi?id="+id);
	win.window('open');
} */
//查看合同
function htPro(fpId) {
	var win = creatFirstWin('合同信息',1300,580,'icon-search',"/cgreceive/receiveContractJSP?fpId="+fpId);
	win.window('open');
}
//打印
function exportHtml(id,fpItemsName) {
	window.open(base+"/exportCg/receive?id="+ id +"&fpItemsName="+fpItemsName);
}
</script>
</body>
</html>