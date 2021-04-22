<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">领用单号&nbsp;
						<input id="rece_fixed_fAssReceCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;审批状态&nbsp;
						<select id="rece_fixed_fFlowStauts_R" name="" style="width: 150px;height:25px;" class="easyui-combobox" editable="false">
							<option value="">--请选择--</option>
							<option value="9">已审批</option>
							<option value="1">待审批</option>
							<option value="-1">已退回</option>
							<option value="0">暂存</option>
						</select>
						&nbsp;&nbsp;申请时间&nbsp;
						<input id="rece_fixed_fReceTimeBegin" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;-&nbsp;
						<input id="rece_fixed_fReceTimeEnd" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_fixed_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_fixed_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
						
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="rece_fixed_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
				
		</div>
		
		<div class="list-table">
			<table id="rece_fixed_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Rece/JsonPagination?fAssType=ZCLX-02',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_R',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssReceCode',align:'center'" width="15%">资产领用单号</th>
						<th data-options="field:'fAssName',align:'center'" width="20%">资产名称</th>
						<!-- <th data-options="field:'fReceDept',align:'center',resizable:false,sortable:true" width="15%">领用部门</th>
						<th data-options="field:'fReceUser',align:'center',resizable:false,sortable:true" width="15%">领用人</th> -->
						<th data-options="field:'fReqTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="15%" >申请时间</th>
						<th data-options="field:'fFlowStauts_R',align:'center',formatter:formatPrice,resizable:false,sortable:true" width="15%">审批状态</th>
						<th data-options="field:'fReceStatus',align:'center',formatter:returnfReceStatus,resizable:false,sortable:true" width="15%">确认状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

<script type="text/javascript">
$("#rece_fixed_fReceTimeBegin").datebox({
    onSelect : function(beginDate){
        $('#rece_fixed_fReceTimeEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
//清除查询条件
function rece_fixed_clearTable() {
	$('#rece_fixed_fAssReceCode').textbox('setValue',null),
	$('#rece_fixed_fFlowStauts_R').combobox('setValue',null),
	$('#rece_fixed_fReceTimeBegin').datebox('setValue',null),
	$('#rece_fixed_fReceTimeEnd').datebox('setValue',null),
	rece_fixed_query();
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
	if(!regu.test($('#"rece_fixed_fAssReceCode"').val()) && flag == true){
    	alert("请输入中文或英文！");
    	flag = false;
    } 	
} 
function returnfReceStatus(val, row) {
	if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 同意" + '</span>';
	} else if (val == ''||val == null) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未确认" + '</span>';
	} else if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 不同意" + '</span>';
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
		} else if (val == -4) {
			return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已撤回" + '</span>';
		}
	}
	function CZ(val, row) {
		if(fs==1){
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="rece_fixed_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '+
				    '</a></td><td style="width: 25px">'+
					'<a href="#" onclick="reCall(\'rece_fixed_dg\',' + row.fId_R + ',\'/Rece/reCall\')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
					'</a></td></tr></table>';
		}
		if(fs==9){
			if(row.fReceStatus==null||row.fReceStatus==''){//未配置或配置了领用人不同意
				return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
						+'<a href="#" onclick="rece_fixed_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
						+ '</td><td style="width: 25px">'
						+ '<a href="#" onclick="rece_config_detail(' + row.fId_R
						+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/qr1.png">'+
						'</a></td></tr></table>';
			}else{
				return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'+
						'<a href="#" onclick="rece_fixed_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + 
					    '</a>'+ '</td><td style="width: 25px">'+
						'<a href="#" onclick="recefixed_exportHtml(' + row.fId_R + ')" class="easyui-linkbutton">'+
						'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dy1.png">'+
					    '</a></td></tr></table>';
			}
		}
		if(fs==0||fs==-1||fs==-4){
			//return '<a href="#" onclick="rece_fixed_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
			return '<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
				+'<a href="#" onclick="rece_fixed_detail(' + row.fId_R
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'
				+'</a>'+ '</td><td style="width: 25px">'
				+ '<a href="#" onclick="Rece_fixed_update(' + row.fId_R
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">' 
				+'</a>'+ '</td><td style="width: 25px">'
				+'<a href="#" onclick="rece_fixed_delete(' + row.fId_R
				+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'
				+ '</a></td></tr></table>';
		}
	}
	//打印
	function recefixed_exportHtml(id) {
		window.open(base+"/exportAssets/receFixed?id="+ id);
	}
	function rece_fixed_query() {
		$('#rece_fixed_dg').datagrid('load', {
			fAssReceCode : $('#rece_fixed_fAssReceCode').val(),
			fFlowStauts_R : $('#rece_fixed_fFlowStauts_R').val(),
			fReceTimeBegin : $('#rece_fixed_fReceTimeBegin').val(),
			fReceTimeEnd : $('#rece_fixed_fReceTimeEnd').val()
		});
	}
	function rece_fixed_add() {
		var win = creatWin('申请', 1115,600, 'icon-search',"/Rece/addFixed");
		win.window('open');
	}
	function rece_fixed_detail(id) {
			var win = creatWin('查看', 1115,600, 'icon-search',"/Rece/detail/" + id);
			win.window('open');
	}
	function rece_config_detail(id) {
			var win = creatWin('查看', 1115,600, 'icon-search',"/Rece/detail/"+id+'?detail=1');
			win.window('open');
	}
	/* function editCF() {
		var row = $('#rece_fixed_dg').datagrid('getSelected');
		var selections = $('#rece_fixed_dg').datagrid('getSelections');
		if (row != null && selections.length == 1) {
			var win = creatWin(' ', 970,580, 'icon-search',
					"/Storage/edit/" + row.fId_R);
			win.window('open');
		} else {
			$.messager.alert('系统提示', '请选择一条数据！', 'info');
		}
	} */
	function Rece_fixed_update(id) {
		var win = creatWin('修改', 1115,600, 'icon-search',"/Rece/edit/" + id);
		win.window('open');
	}
	function rece_fixed_delete(id) {
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/Rece/delete/' + id,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$('#rece_fixed_dg').form('clear');
						$("#rece_fixed_dg").datagrid('reload');
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
		}
	}
	function formatOper(value, row, index) {
		return 'sfsdf';
		//return '<a href="#" onclick="test('+index+')">修改</a>';  
		//    return '<a href="javascript:void(0);" onclick="openviewzfrw(\''+row.person+'\',\''+row.data_status+'\')"><font color="blue">走访</font></a>'; 
	}
</script>
</body>
</html>

