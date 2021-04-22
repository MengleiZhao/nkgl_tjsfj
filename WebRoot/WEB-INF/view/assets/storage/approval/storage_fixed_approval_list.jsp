<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth">资产入账单号&nbsp;
						<input id="storage_fixed_fAssStorageCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;申请人&nbsp;
						<input id="storage_fixed_fOperator" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;申请日期&nbsp;
						<input id="storage_fixed_fPurchaseDateStart" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;-&nbsp;
						<input id="storage_fixed_fPurchaseDateEnd" name="" style="width: 150px;height:25px;" class="easyui-datebox" editable="false"></input>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="storage_fixed_query();">
							<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="storage_fixed_clearTable();">
							<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					 
					</td>
				</tr>
				<tr id="helpTr" style="display: none;">
				</tr>
			</table>           
		</div>
		
		<div class="list-table">
			<table id="storage_fixed_approval_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Storage/approvalJson?fAssType=${fAssType}',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_S',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fAssStorageCode',align:'center'" width="30%">资产入账单号</th>
						<th data-options="field:'fGainingMethods',align:'center'" width="20%">取得方式</th>
						<th data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" width="20%">申请时间</th>
						<th data-options="field:'fFlowStatus',align:'center',formatter: formatPrice" width="15%" >审批状态</th>
						<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">
$("#storage_fixed_fPurchaseDateStart").datebox({
    onSelect : function(beginDate){
        $('#storage_fixed_fPurchaseDateEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
//清除查询条件
function storage_fixed_clearTable() {
	$('#storage_fixed_fAssStorageCode').textbox('setValue',null),
	$('#storage_fixed_fOperator').textbox('setValue',null),
	$('#storage_fixed_fPurchaseDateStart').datebox('setValue',null),
	$('#storage_fixed_fPurchaseDateEnd').datebox('setValue',null);
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
		 if(!regu.test($('#"storage_fixed_fAssStorageCode"').val()) && flag == true){
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
		if(row.fFlowStatus==1){
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'<a href="#" onclick="storage_fixed_detail(' + row.fId_S+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="storage_fixed_sp(' + row.fId_S
					+ ')" class="easyui-linkbutton"><img <img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/approval1.png">' + '</a>'
					+'</td></tr></table>';
		}else {
			return 	'<table><tr style="width: 105px;height:20px"><td style="width: 25px">'
					+'</td><td style="width: 25px">'
					+'<a href="#" onclick="storage_fixed_detail(' + row.fId_S+ ')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">' + '</a>'
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
	function showE(obj){
		obj.src=base+'/resource-modality/${themenurl}/delete2.png';
	}
	function showF(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/delete1.png';
	}
	/* $(function() {
		//定义双击事件
		$('#storage_fixed_approval_dg').datagrid({
			onDblClickRow : function(rowIndex, rowData) {
				detailDemo();
			}
		});
	}); */
	function storage_fixed_query() {
		$('#storage_fixed_approval_dg').datagrid('load', {
			fAssStorageCode : $('#storage_fixed_fAssStorageCode').val(),
			fOperator : $('#storage_fixed_fOperator').val(),
			fPurchaseDateStart : $('#storage_fixed_fPurchaseDateStart').val(),
			fPurchaseDateEnd : $('#storage_fixed_fPurchaseDateEnd').val()
		});
	}
	function storage_fixed_add() {
		var node = $('#storage_fixed_approval_dg').datagrid('getSelected');
		var win = creatWin('资产增加', 1000,580, 'icon-search', '/Storage/fixedadd');
		if (null != node) {
			win = creatWin('资产增加', 1000,580, 'icon-search',
					'/Storage/fixedadd');
		}
		win.window('open');
	}
	function storage_fixed_detail(id) {
			var win = creatWin('查看', 1000 ,580, 'icon-search',"/Storage/detail/" + id);
			win.window('open');
	}
	/* function editCF() {
		var row = $('#storage_fixed_approval_dg').datagrid('getSelected');
		var selections = $('#storage_fixed_approval_dg').datagrid('getSelections');
		if (row != null && selections.length == 1) {
			var win = creatWin(' ', 970,580, 'icon-search',
					"/Storage/edit/" + row.fcId);
			win.window('open');
		} else {
			$.messager.alert('系统提示', '请选择一条数据！', 'info');
		}
	} */
	function storage_fixed_sp(id) {
		//var row = $('#storage_fixed_approval_dg').datagrid('getSelected');
		var selections = $('#storage_fixed_approval_dg').datagrid('getSelections');
		var win = creatWin('审批', 1000, 580, 'icon-search',
				"/Storage/approvel/" + id);
		win.window('open');
	}
	function storage_fixed_delete(id) {
		if (confirm("确认删除吗？")) {
			$.ajax({
				type : 'POST',
				url : '${base}/Storage/delete/' + id,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$('#CFAddEditForm').form('clear');
						$("#storage_fixed_approval_dg").datagrid('reload');
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
</script>
</body>
</html>

