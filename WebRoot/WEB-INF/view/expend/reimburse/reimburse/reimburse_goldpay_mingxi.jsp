<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" id="rgoldpay-edit-div" style="margin-left: 0px;padding-top: 10px">
	<div id="goldpay-edit-tb" style="height:30px;padding-top : 8px">
		<%-- <a style="float: left;color: #666666;font-size:12px;">申请金额：<span ><fmt:formatNumber groupingUsed="true" value="${applyBean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a> --%>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">报销金额：<span id="rgoldpayeditAmount"><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<%-- <a href="javascript:void(0)" onclick="goldpayremoveit()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="goldpayappendRow()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
	</div>
	<table id="goldpay-edit-tab" class="easyui-datagrid" style="width:693px;height:auto"
	data-options="
	toolbar: '#goldpay-edit-tb',
	<c:if test="${operation=='add'}">		
	url: '',
	</c:if>
	<c:if test="${operation=='edit'}">
	url: '${base}/reimburse/mingxiZBJ?rId=${bean.rId}',
	</c:if>
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	onClickRow: goldpayonClickRow,
	scrollbarSize:0,
	singleSelect: true,
	">
		<thead>
			<tr>
				<th data-options="field:'cId',hidden:true"></th>
				<th data-options="field:'costDetail',align:'center',editor:{type:'textbox',options:{required:'true',editable:false}}" width='50%'>费用名称</th>
				<th data-options="field:'remibAmount',align:'center',editor:{type:'numberbox',options:{onChange:rgoldpayaddNum,editable:false,required:'true',precision:2,groupSeparator:',',icons:[{iconCls:'icon-yuan'}]}}" width='49%'>报销金额</th>
			</tr>
		</thead>
	</table>
	
</div>
<script type="text/javascript">
var rgoldpayomey = 0.00;
//明细表格添加删除，保存方法
var editIndex = undefined;
function goldpayendEditing() {
	if (editIndex == undefined) {
		return true
	}
	if ($('#goldpay-edit-tab').datagrid('validateRow', editIndex)) {
		var ed = $('#goldpay-edit-tab').datagrid('getEditor', {
			index : editIndex,
			field : 'costDetail'
		});
		$('#goldpay-edit-tab').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function goldpayonClickRow(index) {
	if (editIndex != index) {
		if (goldpayendEditing()) {
			$('#goldpay-edit-tab').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndex = index;
		} else {
			$('#goldpay-edit-tab').datagrid('selectRow', editIndex);
		}
	}
}
function goldpayappendRow() {
	if (goldpayendEditing()) {
		$('#goldpay-edit-tab').datagrid('appendRow', {});
		editIndex = $('#goldpay-edit-tab').datagrid('getRows').length - 1;
		$('#goldpay-edit-tab').datagrid('selectRow', editIndex).datagrid('beginEdit',
				editIndex);
	}

	/* //页面随滚动条置底
	var div = document.getElementById('rgoldpay-edit-div');
	div.scrollTop = div.scrollHeight; */
}
function goldpayremoveit() {
	if (editIndex == undefined) {
		return
	}
	$('#goldpay-edit-tab').datagrid('cancelEdit', editIndex).datagrid('deleteRow',editIndex);
	editIndex = undefined;
	
	//修改申请总额
	var num = 0;
	var remibAmount = 0;
	var rows = $('#goldpay-edit-tab').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(rows[i].applySum!=""&&rows[i].applySum!=null){
			num += parseFloat(rows[i].applySum);
		}
		if(rows[i].remibAmount!=""&&rows[i].remibAmount!=null){
			remibAmount += parseFloat(rows[i].remibAmount);
		}
	}
	$('#num1').textbox('setValue',num.toFixed(2));
	$('#applyAmount').val(num.toFixed(2));
	$('#rgoldpayeditAmount').html(remibAmount.toFixed(2)+'[元]');
	$('#p_amount').html(remibAmount.toFixed(2)+'[元]');
	$('#reimburseAmount').val(remibAmount.toFixed(2));//报销总额
	jk();
}
function goldpayaccept() {
	if (goldpayendEditing()) {
		$('#goldpay-edit-tab').datagrid('acceptChanges');
	}
}
function rgoldpayaddNum (oldValue,newValue){
	var rgoldpayeditAmount = 0.00;
	var rows = $('#goldpay-edit-tab').datagrid('getRows' );
	for (var i = 0; i < rows.length; i++) {
		if(i==editIndex){
			var row = $('#goldpay-edit-tab').datagrid('getSelected');//获得选择行
			var index=$('#goldpay-edit-tab').datagrid('getRowIndex',row);//获得选中行的行号
			var tr = $('#goldpay-edit-tab').datagrid('getEditors', index);
			var remibAmount = isNaN(tr[1].target.numberbox('getValue'))?0.00:tr[1].target.numberbox('getValue');
			rgoldpayeditAmount = parseFloat(rgoldpayeditAmount) +  parseFloat(remibAmount);
		}else {
			var remibAmount = isNaN(parseFloat(rows[i].remibAmount))?parseFloat(0.00):parseFloat(rows[i].remibAmount);
			rgoldpayeditAmount = parseFloat(rgoldpayeditAmount) +  parseFloat(remibAmount);
		}
	}
	$('#rgoldpayeditAmount').html(rgoldpayeditAmount.toFixed(2)+'[元]');
	$('#p_amount').html(rgoldpayeditAmount.toFixed(2)+'[元]');
	$('#reimburseAmount').val(rgoldpayeditAmount.toFixed(2));//报销总额
	jk();
}
function goldpayjson(){
	goldpayaccept();
	var mingxi = "";
	var rows = $('#goldpay-edit-tab').datagrid('getRows' );
	for (var i = 0; i < rows.length; i++) {
		mingxi = mingxi + JSON.stringify(rows[i]) + ",";
		rgoldpayomey = parseFloat(rgoldpayomey) + parseFloat(rows[i].remibAmount);
	}
	//console.log(mingxi);
	$('#mingxiJson').val(mingxi);
}

</script>