<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">原合同编号&nbsp;
						<input id="c_fcCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;原合同名称&nbsp;
						<input id="c_fcTitle" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						<!-- &nbsp;&nbsp;合同金额&nbsp;
						<input id="c_cAmountBegin"  name="" style="width: 150px;height:25px;" data-options="icons: [{iconCls:'icon-wanyuan'}],precision:2" validType="numberBegin[c_cAmountEnd]" class="easyui-numberbox"></input>
						&nbsp;-&nbsp;
						<input id="c_cAmountEnd" name="" style="width: 150px;height:25px;" data-options="icons: [{iconCls:'icon-wanyuan'}],precision:2" class="easyui-numberbox"></input>
						&nbsp;&nbsp; -->
						<a href="javascript:void(0)"  onclick="queryCF();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="clearTable();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')"></a>
					</td> 
				</tr>
			</table>           
		</div>
		<div class="list-table" style="height:450px">
			<table id="change_contract_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Change/contractList?fsealedStatus=1',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_R',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fcCode',align:'center'" width="25%">原合同编号</th>
						<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="27%">原合同名称</th>
						<th data-options="field:'fcType',align:'center',resizable:false,sortable:true,formatter:formatfcType" width="20%">原合同分类</th>
						<th data-options="field:'fcAmount',align:'center',resizable:false,sortable:true,formatter:formatfcAmount" width="25%">原合同金额(元)</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="text-align: left;height: 20px">
			<span style="color:#ff6800">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;✧温馨提示：双击数据选择</span>
		</div>
	</div>
	


<script type="text/javascript">

$("#change_contract_dg").datagrid({
	 onDblClickRow:function(index, row){
		var row = $('#change_contract_dg').datagrid('getSelected');
		var selections = $('#change_contract_dg').datagrid('getSelections');
		if (row != null && selections.length == 1) {
			var win = creatWin('合同变更申请', 1115, 600, 'icon-search','/Change/add/'+row.fcId);
			win.window('open');
			setTimeout(function() {
				$.ajax({
			        type : "POST",
			        url : base+'/Change/getReceivplanid',
			        async : 'false',
			        data : {
			        	payId: row.fcId
			        },
			        success : function(data) {
						$('#reimbFlag').val(data);
			        },
			        error : function(e){
			            console.log(e.status);
			            console.log(e.responseText);
			        }
			    });
			}, 5000);
			
			
			closeFirstWindow();
		} else {
			$.messager.alert('系统提示', '请选择一条数据！', 'info');
		}
	}
});
function formatfcType(val, row) {
	if (val == 'HTFL-01') {
		return '<span style="color:#666666;">' + " 采购合同" + '</span>';
	}else{
		return '<span style="color:#666666;">' + " 非采购合同" + '</span>';
	}
}
function formatfcAmount(val , row){
	if(val == null || val == ''){
		return '<span style="color:#666666;">' + " 0.00" + '</span>';
	}else{
		return '<span style="color:#666666;">' + val + '</span>';
	}
}
function formatPrice(val,row){
	if (val =="1"){
		return "公开招标";
	} else if(val =="2"){
		return "待邀请招标";
	} else if(val =="3"){
		return "竞争性谈判";
	} else if(val =="4"){
		return"单一来源采购";
	}
}
function CZ(val,row){
	return '<a href="#" onclick="PitchOn('+row.fpId+')" class="easyui-linkbutton">'+"选取"+'</a>';
}
function PitchOn(id){
	var row = $('#change_contract_dg').datagrid('getSelected');
	var selections = $('#change_contract_dg').datagrid('getSelections');
	if (row != null && selections.length == 1) {
		$("#A_fReceCode").textbox('setValue',row.fAssReceCode); 
		$("#A_fTransUser").textbox('setValue',row.fReceUser); 
		$("#A_fTransDept").textbox('setValue',row.fReceDept); 
		$("#A_fTransTel").textbox('setValue',row.fReceTel); 
		$("#A_fTransTime").textbox('setValue',ChangeDateFormat(row.fReceTime)); 
		closeFirstWindow();
	} else {
		$.messager.alert('系统提示', '请选择一条数据！', 'info');
	}
}
function queryCF(){  
	$('#change_contract_dg').datagrid('load',{ 
		fcCode:$('#c_fcCode').textbox('getValue'),
		fcTitle:$('#c_fcTitle').textbox('getValue'),
	} ); 
}

/* //合同分类
function FcType(val){
	$.ajax({
		url: base + '/Formulation/lookupsJson?parentCode=HTFL&selected=' + val ,
		type : 'post',
		async : false,
		dataType :'json',
	    contentType:'application/json;charset=UTF-8',
		success : function(data) {
		}
	});
	
} */

</script>
</body>
</html>

