<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="dg_detail" class="easyui-datagrid" style="width: 690px"
data-options="
singleSelect: true,
toolbar: '#tb',
rownumbers : true,
striped:true,
<c:if test="${!empty bean.fpId}">
url: '${base}/cgconfplangl/mingxi?id=${bean.fpId}',
</c:if>
method: 'post',
">
<thead>
	<tr>
		<th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'fpKind',align:'center',editor:{type:'combotree',options:{editable:true}},valueField:'fpKind',textField:'fpKind'" style="width: 20%">品目</th>
		<th data-options="field:'fpurName',align:'center',editor:'textbox'" style="width: 20%">商品名称</th>
		<th data-options="field:'fnum',align:'center',editor:{type:'numberbox'}" style="width: 15%">数量</th>
		<th data-options="field:'fmeasureUnit',align:'center',editor:'textbox'" style="width: 15%">单位</th>
		<th data-options="field:'fIsImp',align:'center',editor:{type:'combobox',options:{valueField:'value',
				textField:'label',editable:false,data: [{label: '是',value: '是'},{label: '否',value: '否'}]
			}
		}" style="width: 15%">是否进口</th>
		<!-- <th data-options="field:'funitPrice',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 20%">单价(元)</th>
		<th data-options="field:'fsumMoney',align:'center',editor:{type:'numberbox',options:{precision:2,readonly:false}}" style="width: 20%">申请金额(元)</th> -->
		<th data-options="field:'fcommProp',align:'center',editor:{type:'textbox',options:{required:false}}" style="width: 28%">相关要求</th>
	</tr>
</thead>
</table>
<input type="hidden" id="mingxiJson" name="mingxi"/>
<script type="text/javascript">

var a = '${bean.amount}';
var b = '${cgType}';
if(a != null && a != '' && a != undefined){
	$("#cgReason").show();
	$.ajax({
		url:'${base}/cgsqsp/cgsqspJson?amount='+a+'&parentCode='+b,
		success:function(data){
			var json = eval("("+data+")");
			var html = '';
			var flag = false;
			if(json.length == 1){
				$("#cgReason").hide();
			}
			json.forEach(function(val,index){
				var arr = '${bean.buyInfos}'.split(',');
				
				if(val.text != '--请选择--'){
					for(var i=0;i<arr.length;i++){
						if(arr[i] == val.id){
							flag = true;
						}
					}
					if(flag){
						html += '<input type="checkbox" name="reasonIds" disabled="disabled" checked="checked" value="'+val.id+'"/>&nbsp;'+val.text+'&nbsp;<br>';
						flag = false;
					}else{
						html += '<input type="checkbox" name="reasonIds" disabled="disabled" value="'+val.id+'"/>&nbsp;'+val.text+'&nbsp;<br>';
					}
				}
				$("#reasonChoose").html(html);
			});
		}
	});
}

var fpItemsName = 1;
//加载完以后自动计算金额
$('#dg_detail').datagrid({onLoadSuccess : function(data){
	var rows = $('#dg_detail').datagrid('getRows');
	var totalFsumMoney = 0;
	for(var i=0;i<rows.length;i++){
		totalFsumMoney+=parseFloat(rows[i].fsumMoney);
	}
	$('#totalPrices').html(totalFsumMoney.toFixed(2)+'元');
}});
</script>