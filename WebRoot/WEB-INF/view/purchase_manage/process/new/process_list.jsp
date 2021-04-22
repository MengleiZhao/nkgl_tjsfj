<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_process_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
			<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					<%-- 项目编号&nbsp;
					<input id="process_fpCode" name="fpCode" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;项目名称&nbsp;
					<input id="process_fpName" name="fpName" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;品目名称&nbsp;
					<input class="easyui-combobox" id="process_fpItemsName" name="fpItemsName" style="width: 150px; height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PMMC',method:'get',valueField:'code',textField:'text',editable:false"/>
					 --%>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryProcess();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearProcess();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="process_list" class="easyui-datagrid" data-options="collapsible:true,url:'${base}/cgprocess/pageList',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 20%">采购项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 17.5%">采购项目名称</th>
					<!-- <th data-options="field:'fpItemsNames',align:'center',resizable:false,sortable:true" style="width: 9%">品目名称</th> -->
					<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">采购金额[元]</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 15%">登记部门</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 10%">登记人</th>
					<!-- <th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">申报时间</th> -->
					<th data-options="field:'fbidStauts',align:'center',resizable:false,sortable:true,formatter:formatProcess" style="width: 10%">登记状态</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	//点击查询
	function queryProcess() {
		var searchData="searchData";
		$("#process_list").datagrid('load', {
			/* fpCode:$("#process_fpCode").val(),
			fpName:$("#process_fpName").val(),
			fpItemsName:$("#process_fpItemsName").val()	   */
			searchData:$("#"+searchData).textbox('getValue').trim(),
		});
	}
	//清除查询条件
	function clearProcess() {
		var searchData="searchData";
		/* $("#process_fpCode").textbox('setValue','');
		$("#process_fpName").textbox('setValue','');
		$("#process_fpItemsName").combobox('setValue',''); */
		$("#"+searchData).textbox('setValue','');
		$("#process_list").datagrid('load',{//清空以后，重新查一次
		});
	}
	
	//设置登记状态
	function formatProcess(val, row) {
		if (val == '' || val == null) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未登记" + '</a>';
		} else if (val == -1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
		} else if (val == 9) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已登记" + '</a>';
		} else if (val == 1 ||val == 0) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待登记" + '</a>';
		} else if (val == -4) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</a>';
		}
	}

	//操作栏创建
	function CZ(val, row) {
		//登记状态
		var c = row.fbidStauts;
		if (c == '' || c == null) {//暂存
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="process_add(' + row.fpId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dengji1.png">'+
					'</a></td></tr></table>';
		}else if(c == -1 || c == -4  ||c == 0) {//已退回、已撤回
			return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="process_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
					'</a></td><td style="width: 25px">'+
					'<a href="#" onclick="process_update(' + row.fpId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
					'</a></td></tr></table>';
		}else if(c == 1) {//待审批
			return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="process_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
					'</a></td><td style="width: 25px">'+
					'<a href="#" onclick="reCall(\'process_list\',' + row.fpId + ',\'/cgprocess/reCall\')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
					'</a></td></tr></table>';
		}else if(c == 9) {//已审批
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="process_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
					'</a></td></tr></table>';
		}
	}
	
	//新增
	function process_add(id) {
		var win = creatWin('采购登记', 1070, 580, 'icon-search', "/cgprocess/add?id="+ id);
		win.window('open');
	}
	//查看
	function process_detail(id) {
		var win = creatWin('采购登记', 1070, 580, 'icon-search', "/cgprocess/detail?id=" + id);
		win.window('open');
	}
	//修改
	function process_update(id) {
		var win = creatWin('采购登记', 1070, 580, 'icon-search', "/cgprocess/edit?id=" + id);
		win.window('open');
   	}
</script>
</body>
</html>