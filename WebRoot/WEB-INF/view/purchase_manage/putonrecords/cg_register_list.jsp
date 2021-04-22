<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_apply_dg" class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					 &nbsp;&nbsp;
					<a href="#" onclick="queryCgPORApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearCgPORApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
				
		</table>
	</div>

	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="cg_apply_register_Tab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/putOnRecords/cgRegisterPage',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 20%">采购项目编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 20%">采购项目名称</th>
					<th data-options="field:'fpMethod',align:'center',resizable:false,sortable:true" style="width: 20%">采购类型</th>
					<th data-options="field:'fpPype',align:'center',resizable:false,sortable:true" style="width: 20%">采购方式</th>
					<th data-options="field:'fOrgName',align:'center',resizable:false,sortable:true" style="width: 20%">中标商名称</th>
					<th data-options="field:'fbidAmount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 15%">投标金额（元）</th>
					<th data-options="field:'fDeptName',align:'center',resizable:false,sortable:true" style="width: 10%">申请部门</th>
					<th data-options="field:'fUserName',align:'center',resizable:false,sortable:true" style="width: 10%">申请人</th>
					<th data-options="field:'conPutOnRecordsSts',align:'center',resizable:false,sortable:true,formatter:formatPrice" style="width: 9%">合同备案状态</th>
					<th data-options="field:'CZ',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
					<th data-options="field:'checkPutOnRecordsSts',align:'center',resizable:false,sortable:true,formatter:formatPrice1" style="width: 9%">验收备案状态</th>
					<th data-options="field:'CZ1',align:'center',resizable:false,sortable:true,formatter:CZ1" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
//点击查询
function queryCgPORApply() {
	var searchData="searchData";
	$('#cg_apply_register_Tab').datagrid('load', {
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//清除查询条件
function clearCgPORApply() {
	var searchData="searchData";
	$("#"+searchData).textbox('setValue','');
	$('#cg_apply_register_Tab').datagrid('load',{//清空以后，重新查一次
	});
}
//合同备案设置审批状态
var c;
function formatPrice(val, row) {
	c = val;
	if (val == 0 || val==null) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 待上传" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 待修改" + '</a>';
	} else if (val ==2) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待确认" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已确认" + '</a>';
	} else if (val == 4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已撤回" + '</a>';
	} else if (val == 5) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	}
}
//验收备案设置审批状态
var b;
function formatPrice1(val, row) {
	b = val;
	if (val == 0 || val==null) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 待上传" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 待修改" + '</a>';
	} else if (val ==2) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待确认" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已确认" + '</a>';
	} else if (val == 4) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已撤回" + '</a>';
	} else if (val == 5) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	}
}


//合同备案操作栏创建
function CZ(val, row) {
	//查看详情按钮
	var button1='<a href="#" onclick="cg_apply_con_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a>';
	//修改详情按钮
	var button2='<a href="#" onclick="cg_apply_con_update(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
	   '</a>';
	//上传按钮
	var button3='<a href="#" onclick="addCgConApply(' + row.fpId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/shangchuan1.png">'+
		'</a>';
	if (c == 2) {
		return button1;
	}else if (c == 9) {
		return button1;
	} else if(c == 0 || c==null) {
		return button3;
	} else if(c == 1) {
		return button2;
	} else if(c == 4) {
		return button2;
	} else if(c == 5) {
		return button2;
	}
}

//验收备案操作栏创建
function CZ1(val, row) {
	//查看详情按钮
	var button1='<a href="#" onclick="cg_apply_check_detail(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
	   '</a>';
	//修改详情按钮
	var button2='<a href="#" onclick="cg_apply_check_update(' + row.fpId + ')" class="easyui-linkbutton">'+
	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
	   '</a>';
	//上传按钮
	var button3='<a href="#" onclick="addCgCheckApply(' + row.fpId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/shangchuan1.png">'+
		'</a>';
	if (b == 2) {
		return button1;
	}else if (b == 9) {
		return button1;
	} else if(b == 0 || b==null) {
		return button3;
	} else if(b == 1) {
		return button2;
	} else if(b == 4) {
		return button2;
	} else if(b == 5) {
		return button2;
	}
}

//合同备案上传
function addCgConApply(id) {
	var win = creatWin('新增', 785, 580, 'icon-search', '/putOnRecords/addRegisterCon?fpId='+id);
	win.window('open');
}
//验收备案上传
function addCgCheckApply(id) {
	var win = creatWin('新增', 785, 580, 'icon-search', '/putOnRecords/addRegisterCheck?fpId='+id);
	win.window('open');
}
//合同备案修改
function cg_apply_con_update(id) {
	var win = creatWin('修改', 785, 580, 'icon-search', '/putOnRecords/editRegisterCon?fpId='+id+'&type=1');
	win.window('open');
}
//验收备案修改
function cg_apply_check_update(id) {
	var win = creatWin('修改', 785, 580, 'icon-search', '/putOnRecords/editRegisterCheck?fpId='+id+'&type=1');
	win.window('open');
}
//合同备案查看
function cg_apply_con_detail(id) {
	var win = creatWin('查看', 785, 580, 'icon-search', '/putOnRecords/editRegisterCon?fpId='+id+'&type=0');
	win.window('open');
}
//验收备案查看
function cg_apply_check_detail(id) {
	var win = creatWin('查看', 785, 580, 'icon-search', '/putOnRecords/editRegisterCheck?fpId='+id+'&type=0');
	win.window('open');
}
/* //撤回
function reCallFileUp(id) {
	if (confirm("确认撤回吗？")) {
		$.ajax({
			type : 'POST',
			url : base+'/cgsqsp/saveFileUp?fpId='+id+'&filesUploadSts=4',
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$("#cg_apply_register_Tab").datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
 */
</script>
</body>
