<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 0px">
	<table id="filing-edit-plan-dg"  class="easyui-datagrid" style="width:700px;height:auto"
			data-options="
				toolbar: '#contractTb',
				<c:if test="${openType != 'add'}">
				url: '${base}/Filing/finderReceivPlan?FcId=${bean.fcId}',
				</c:if>
				method: 'post',
				onClickRow: onClickRowPlan,
				rownumbers:true,
				singleSelect:true,
				onLoadSuccess:onLoadSuccessMethod,
			">
		<thead>
			<tr>
				<th data-options="field:'fReceCondition',editor:{type:'textbox',options:{required:true}},validType:'length[1,50]',align:'center'"  style="width: 25%">付款条件</th>
				<th data-options="field:'fRecePlanTime',align:'center',editor:{type:'datebox',options:{required:true,precision:1}}" style="width: 25%">预计付款时间</th>
				<th data-options="field:'fRecePlanAmount',align:'center',formatter:function(value,row,index){return fomatMoney(value,2);},editor:{type:'numberbox',options:{required:true,precision:2,onChange:filingplansetFsumMoney}}"style="width: 25%">付款金额(元)</th>
				<th data-options="field:'fReceProof',editor:{type:'textbox'}" style="width: 25%">付款依据</th>
				<!-- <th data-options="field:'fReceProofs',align:'center',editor:{type:'combobox',
						options:{
							required:true,
							valueField:'code',
							textField:'text',
							editable:false,
							validType:'selectValid',
							method:'post',
							onShowPanel:onShowPanelFKPJ,
							onHidePanel:onHidePanelFKPJ
						}
					}" style="width: 25%">付款依据</th> -->
			</tr>
		</thead>
	</table>
	
	<div id="contractTb" style="overflow:auto;margin-top: 10px;">
		<span style="float: left;color: #0000CD;">	
			<input type="hidden" id="totalAmount"/>
			<span style="color: red;"  >合计总额： </span>
			<span style="float: right;"  id="totalAmount_span" ><fmt:formatNumber groupingUsed="true" value="${bean.fPlanTotalAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
		</span>
		<a href="javascript:void(0)" onclick="removeitPlan()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendPlan()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
</div>
<script type="text/javascript">
var comboxdata = null;
function PayStauts(val, row) {
	if (val == "FKXZ-02") {
		return "阶段款";
	} else if (val == "FKXZ-01") {
		return "首款";
	}else if (val == "FKXZ-03") {
		return "验收款";
	}else if (val == "FKXZ-04") {
		return "质保款";
	}
}
//完成后计算总额并设置
var loadplan = 0;
function onLoadSuccessMethod(data){
	var rows = data.rows;
	var amount = 0.00;
	loadplan += 1;
	for(var i = 0 ;i<rows.length; i++){
		amount = parseFloat(amount) + parseFloat(rows[i].col5);
	}
	$('#totalAmount').val(amount);
	
}
var editIndex = undefined;
function endEditingPlan(){
	if (editIndex == undefined){return true}
	if ($('#filing-edit-plan-dg').datagrid('validateRow', editIndex)){
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#filing-edit-plan-dg').datagrid('getEditors', editIndex);
		/* var text4=tr[4].target.combobox('getText');
		if(text4!='--请选择--'){
			tr[4].target.combobox('setValues',text4);
		} */
		$('#filing-edit-plan-dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPlan(index) {
	if (editIndex != index) {
		if (endEditingPlan()) {
			$('#filing-edit-plan-dg').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#filing-edit-plan-dg').datagrid('selectRow', editIndex);
		}
	}
}
function appendPlan(){
	if (endEditingPlan()){
		$('#filing-edit-plan-dg').datagrid('appendRow',{});
		editIndex = $('#filing-edit-plan-dg').datagrid('getRows').length-1;
		$('#filing-edit-plan-dg').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	}
}
function removeitPlan(){
	if (editIndex == undefined){return}
	$('#filing-edit-plan-dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
	editIndex = undefined;
	filingplansetFsumMoney(0,0);
}

//计算总额
function filingplansetFsumMoney(newValue,oldValue) {
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var totalFsumMoney = 0;
	var fsumMoney = 0;
	var index=$('#filing-edit-plan-dg').datagrid('getRowIndex',$('#filing-edit-plan-dg').datagrid('getSelected'));
	var rows = $('#filing-edit-plan-dg').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumMoney=parseFloat(newValue);
		}else{
			totalFsumMoney+=filingplanaddNum(rows,i);
		}  
	}
    
	totalFsumMoney=(parseFloat(totalFsumMoney)+parseFloat(fsumMoney));
	totalFsumMoney=parseFloat(totalFsumMoney);
	$('#totalAmount').val(totalFsumMoney);
	$('#F_fPlanTotalAmount').val(totalFsumMoney);
	$('#totalAmount_span').html(fomatMoney(totalFsumMoney,2)+'[元]');
}
//未编辑或者已经编辑完毕的行
function filingplanaddNum(rows,index){
	var amount=rows[index]['fRecePlanAmount'];
	return parseFloat(amount); 
}
function acceptFiling() {
	if (endEditingPlan()) {
		$('#filing-edit-plan-dg').datagrid('acceptChanges');
	}
}
function getPlan(){
	acceptFiling();
	var rows = $('#filing-edit-plan-dg').datagrid('getRows');
	var entities= '';
	for(var i = 0;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	 entities = '[' + entities.substring(0,entities.length -1) + ']';
	 return entities;
}

function new_arr(){
	var arr = new Array();
	var PeopName = $("#fReceProofs").textbox('getValue').split(',');
	for (var j = 0; j < PeopName.length; j++) {
		var idAndName = {};
		idAndName.id = PeopName[j];
		idAndName.text = PeopName[j];
		arr.push(idAndName);
	}
	return arr;
}

function onHidePanelFKPJ(){
	var index=$('#filing-edit-plan-dg').datagrid('getRowIndex',$('#filing-edit-plan-dg').datagrid('getSelected'));
	var ed = $('#filing-edit-plan-dg').datagrid('getEditor', {index:index,field:'fReceProof'});
	var eds = $('#filing-edit-plan-dg').datagrid('getEditor', {index:index,field:'fReceProofs'});
	ed.target.textbox('setValue',eds.target.combobox('getValues'));
}
function onShowPanelFKPJ(){
	var fpItemsName = $("#F_fpItemsName").val();
	/* if(fpItemsName==''){
		alert('该采购单品目名称为空，请核实！');
		return false;
	} */
	var index=$('#filing-edit-plan-dg').datagrid('getRowIndex',$('#filing-edit-plan-dg').datagrid('getSelected'));
	var eds = $('#filing-edit-plan-dg').datagrid('getEditor', {index:index,field:'fReceProofs'});
	eds.target.combobox('reload',base+'/Formulation/lookupsJsonGist?code='+fpItemsName);
}
</script>