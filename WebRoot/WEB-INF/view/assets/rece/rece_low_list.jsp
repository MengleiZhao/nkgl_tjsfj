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
						<input id="rece_low_fAssReceCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;审批状态&nbsp;
						<select id="rece_low_fFlowStauts_R" name="" style="width: 150px;height:25px;" class="easyui-combobox" editable="false">
							<option value="">--请选择--</option>
							<option value="9">已审批</option>
							<option value="1">待审批</option>
							<option value="-1">已退回</option>
							<option value="0">暂存</option>
						</select>
						&nbsp;&nbsp;申请时间&nbsp;
						<input id="rece_low_fReceTimeBegin" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;-&nbsp;
						<input id="rece_low_fReceTimeEnd" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_low_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="rece_low_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')">
						</a>
						
					</td>
					<td align="right" style="padding-right: 10px">
						<a href="#" onclick="rece_low_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td>
					<%-- <td class="top-table-td1">资产领用单号：</td> 
					<td class="top-table-td2">
						<input id="rece_low_fAssReceCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td class="top-table-td1">领用部门：</td> 
					<td class="top-table-td2">
						<input id="rece_low_fFlowStauts_R" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
					</td>
					<td class="top-table-td1">领用时间：</td> 
					<td class="top-table-td2">
						<input id="rece_fReceTime" name="" style="width: 150px;height:25px;" class="easyui-datebox"></input>
					</td>
					<td class="top-table-td1">领用人：</td> 
					<td class="top-table-td2">
						 <input id="rece_fReceUser" name="" style="width: 150px;height:25px;" data-options="" class="easyui-textbox"></input>
					</td>
					<td style="width: 12px">
					</td>
					<td style="width: 24px;">
						<a href="javascript:void(0)"  onclick="rece_low_query();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
					</td>
					<td style="width: 12px">
					</td>
					<td >
						<a href="javascript:void(0)"  onclick="rece_low_clearTable();"><img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')"></a>
					</td>
					<td style="width: 12px">
					</td>
					<td align="right">
						<a href="#" onclick="rece_low_add()" ><img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)" ></a>
					</td>
					<td style="width: 14px">
					</td> --%>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
				
		</div>
		
		<div class="list-table">
			<table id="rece_low_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Rece/JsonPagination?fAssType=ZCLX-01',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_R',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssReceCode',align:'center'" width="21%">资产领用单号（流水号）</th>
						<!-- <th data-options="field:'fAssName',align:'center'" width="20%">领用物资名称</th> -->
						<th data-options="field:'fReceDept',align:'center',resizable:false,sortable:true" width="15%">领用部门</th>
						<th data-options="field:'fReceUser',align:'center',resizable:false,sortable:true" width="15%">领用人</th>
						<th data-options="field:'fReqTime',align:'center',formatter: ChangeDateFormat,resizable:false,sortable:true" width="15%" >申请时间</th>
						<th data-options="field:'fFlowStauts_R',align:'center',formatter:formatPrice,resizable:false,sortable:true" width="15%">审批状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
//清除查询条件
function rece_low_clearTable() {
	$('#rece_low_fAssReceCode').textbox('setValue',null),
	$('#rece_low_fFlowStauts_R').combobox('setValue','--请选择--'),
	$('#rece_low_fReceTimeBegin').datebox('setValue',null),
	$('#rece_low_fReceTimeEnd').datebox('setValue',null),
	rece_low_query();
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
		 if(!regu.test($('#rece_low_fAssReceCode').val()) && flag == true){
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
		}
	}
	function CZ(val, row) {
		if(fs==9){
			return  '<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="rece_low_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
					+'</td></tr></table>';
		}else if(fs==1){
			return  '<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="rece_low_detail(' + row.fId_R+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>   '
					+'</td></tr></table>';
		}else if(fs=='-1'||fs==0){
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="rece_low_detail(' + row.fId_R
					+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
					+'</td><td style="width: 25px">'
					+ '<a href="#" onclick="rece_low_update(' + row.fId_R
					+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">' + '</a>'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="rece_low_delete(' + row.fId_R
					+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">' + '</a>'
					+'</td></tr></table>';
		}
	}
	function showB(obj){
		obj.src=base+'/resource-modality/${themenurl}/select2.png';
	}
	function showA(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
	}
	function showC(obj){
		obj.src=base+'/resource-modality/${themenurl}/update2.png';
	}
	function showD(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/update1.png';
	}
	/* $(function() {
		//定义双击事件
		$('#rece_low_dg').datagrid({
			onDblClickRow : function(rowIndex, rowData) {
				detailDemo();
			}
		});
	}); */
	function rece_low_query() {
		$('#rece_low_dg').datagrid('load', {
			fAssReceCode : $('#rece_low_fAssReceCode').val(),
			fFlowStauts_R : $('#rece_low_fFlowStauts_R').val(),
			fReceTimeBegin : $('#rece_low_fReceTimeBegin').val(),
			fReceTimeEnd : $('#rece_low_fReceTimeEnd').val(),
		});
	}
	function rece_low_add() {
		var win = creatWin('领用申请', 970, 580, 'icon-search', "/Rece/addlow");
		win.window('open');
	}
	function rece_low_detail(id) {
			var win = creatWin('领用明细', 970, 580, 'icon-search', "/Rece/detail/"+id);
			win.window('open');
	}
	/* function editCF() {
		var row = $('#rece_low_dg').datagrid('getSelected');
		var selections = $('#rece_low_dg').datagrid('getSelections');
		if (row != null && selections.length == 1) {
			var win = creatWin(' ', 970,580, 'icon-search',
					"/Storage/edit/" + row.fId_R);
			win.window('open');
		} else {
			$.messager.alert('系统提示', '请选择一条数据！', 'info');
		}
	} */
	function rece_low_update(id) {
		//var row = $('#rece_low_dg').datagrid('getSelected');
		var win = creatWin('领用-修改', 971, 580, 'icon-search', "/Rece/edit/" + id);
		win.window('open');
	}
	function rece_low_delete(id) {
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/Rece/delete/' + id,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$('#rece_low_dg').form('clear');
						$("#rece_low_dg").datagrid('reload');
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
		}
	}
	function expListlawHelp() {
		//var win=creatWin('导出-法律服务接待登记表',400,120,'icon-search','/demo/exportList');
		//win.window('open');
		if (confirm("按当前查询条件导出？")) {
			var queryForm = document.getElementById("lawHelp_list_form");
			queryForm.setAttribute("action", "${base}/demo/expList");
			queryForm.submit();
		}
	}
	//时间格式化
	function ChangeDateFormat(val) {
		//alert(val)
		var t, y, m, d, h, i, s;
		if (val == null) {
			return "";
		}
		t = new Date(val)
		y = t.getFullYear();
		m = t.getMonth() + 1;
		d = t.getDate();
		h = t.getHours();
		i = t.getMinutes();
		s = t.getSeconds();
		// 可根据需要在这里定义时间格式  
		return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
	}
	
	$("#rece_low_fReceTimeBegin").datebox({
	    onSelect : function(beginDate){
	        $('#rece_low_fReceTimeEnd').datebox().datebox('calendar').calendar({
	            validator: function(date){
	                return beginDate <= date;
	            }
	        });
	    }
	});
</script>
</body>
</html>

