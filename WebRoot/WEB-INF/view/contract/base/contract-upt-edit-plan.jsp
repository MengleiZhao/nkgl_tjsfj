<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 0px">
	<table id="change-upt-datagrid" class="easyui-datagrid" style="width:700px;height:auto"
			data-options="
				toolbar: '#tb',
				<c:if test="${uptOpenType == 'Cadd'}">
				url: '${base}/Filing/getReceivPlan?fContId_R=${bean.fcId}&dataType=0',
				</c:if>
				<c:if test="${uptOpenType != 'Cadd'}">
				url: '${base}/Filing/getReceivPlan?fUptId_R=${Upt.fId_U}&dataType=1',
				</c:if>
				method: 'post',
				<c:if test="${uptOpenType != 'Cdetail'}">
				onClickRow: onClickRowUpt,
				</c:if>
				rownumbers:true,
				singleSelect:true,
				onLoadSuccess:onLoadSuccessUpt,
			">
		<thead>
			<tr>
				<th data-options="field:'fReceCondition',editor:{type:'textbox',options:{required:true}},validType:'length[1,50]',align:'center'"  style="width: 28%">付款条件</th>
				<th data-options="field:'fRecePlanTime',align:'center',editor:{type:'datebox',options:{required:true,precision:1}}" style="width: 25%">预计付款时间</th>
				<th data-options="field:'fRecePlanAmount',align:'center',formatter:function(value,row,index){return fomatMoney(value,2);},editor:{type:'numberbox',options:{required:true,precision:2,onChange:filingplansetFsumMoneyUpt}}"style="width: 25%">付款金额(元)</th>
				<th data-options="field:'fReceProof',editor:{type:'textbox'}" style="width: 25%">付款依据</th>
				<!-- <th data-options="field:'fReceProofs',align:'center',editor:{type:'combobox',
						options:{
							required:true,
							valueField:'code',
							textField:'text',
							editable:false,
							validType:'selectValid',
							method:'post',
							onShowPanel:onShowPanelFKPJUpt,
							onHidePanel:onHidePanelFKPJUpt
						}
					}" style="width: 25%">付款依据</th> -->
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="overflow:auto;margin-top: 10px;">
		<span style="float: left;color: #0000CD;">	
			<input type="hidden" id="totalAmount"/>
			<input type="hidden" id="fcAmountUpt" value="${bean.fcAmount}"/>
			<span style="color: red;"  >合计金额： </span>
			<span style="float: right;"  id="totalAmount_span" ><fmt:formatNumber groupingUsed="true" value="0" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
		</span>
		<c:if test="${uptOpenType != 'Cdetail'}">
		<a href="javascript:void(0)" onclick="removeitPlanUpt()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendPlanUpt()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</c:if>
	</div>
</div>
<script type="text/javascript">
var uptOpenType = '${uptOpenType}';
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
function onLoadSuccessUpt(data){
	var rows = data.rows;
	var amount = 0.00;
	loadplan += 1;
	for(var i = 0 ;i<rows.length; i++){
		amount = parseFloat(amount) + parseFloat(rows[i].fRecePlanAmount);
	}
	$('#totalAmount_span').html(fomatMoney(amount,2)+'[元]');
	$('#upt_fcAmount').html(fomatMoney(amount,2)+'[元]');
	$('#upt_fcAmount').val(amount);
	
	if (uptOpenType == 'Cdetail') {
		if (rows.length > 0) {
			$("input[name=collectionPlan]:eq(0)").prop("checked",'checked');
			filingplansetFsumMoneyUpt(0,0);
		} else {
			$("input[name=collectionPlan]:eq(1)").prop("checked",'checked');
		}
	}
}
var editIndex = undefined;
function endEditingPlanUpt(){
	if (editIndex == undefined){return true}
	if ($('#change-upt-datagrid').datagrid('validateRow', editIndex)){
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#change-upt-datagrid').datagrid('getEditors', editIndex);
		/* if (tr.length != 0) {
			var text4=tr[4].target.combobox('getText');
			if(text4!='--请选择--'){
				tr[4].target.combobox('setValues',text4);
			}
			var amountText = tr[2].target.combobox('getText');
			if(amountText == '0.00'){
				alert('付款金额不能为0！');
				return false;
			}
		} */
		$('#change-upt-datagrid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowUpt(index) {
	if (editIndex != index) {
		if (endEditingPlanUpt()) {
			var arr = $('#receivPlanIndex').val() + ',';
			var selectRow = $('#change-upt-datagrid').datagrid('getSelected');
			if (selectRow.fStauts_R != '1' && arr.indexOf(selectRow.fPlanId + ',') == -1) {
				$('#change-upt-datagrid').datagrid('selectRow', index).datagrid('beginEdit', index);
			}
			editIndex = index;
		} else {
			$('#change-upt-datagrid').datagrid('selectRow', editIndex);
		}
	}
}
function appendPlanUpt(){
	if (endEditingPlanUpt()){
		$('#change-upt-datagrid').datagrid('appendRow',{});
		editIndex = $('#change-upt-datagrid').datagrid('getRows').length-1;
		$('#change-upt-datagrid').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	}
}
function removeitPlanUpt(){
	if (editIndex == undefined){return}
	var arr = $('#receivPlanIndex').val() + ',';
	var selectRow = $('#change-upt-datagrid').datagrid('getSelected');
	if (selectRow.fStauts_R == '1') {
		alert('该付款计划已付款，不可删除');
		return;
	}
	if (arr.indexOf(selectRow.fPlanId + ',') >= 0) {
		alert('该付款计划已发起审批，不可删除');
		return;
	}
	$('#change-upt-datagrid').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
	editIndex = undefined;
	filingplansetFsumMoneyUpt(0,0);
}

//计算总额
function filingplansetFsumMoneyUpt(newValue,oldValue) {
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var totalFsumMoney = 0;
	var fsumMoney = 0;
	var index=$('#change-upt-datagrid').datagrid('getRowIndex',$('#change-upt-datagrid').datagrid('getSelected'));
	var rows = $('#change-upt-datagrid').datagrid('getRows');
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
	$('#upt_fcAmount').val(totalFsumMoney);
	$('#upt_fcAmount').numberbox('setValue',totalFsumMoney);
	$('#totalAmount_span').html(fomatMoney(totalFsumMoney)+'[元]');
	$('#upt_fcAmount').html(totalFsumMoney+'[元]');
}
//未编辑或者已经编辑完毕的行
function filingplanaddNum(rows,index){
	var amount=rows[index]['fRecePlanAmount'];
	return parseFloat(amount); 
}
function acceptFilingPlan() {
	if (endEditingPlanUpt()) {
		$('#change-upt-datagrid').datagrid('acceptChanges');
	}
}
function getUptPlanEdit(){
	acceptFilingPlan();
	var rows = $('#change-upt-datagrid').datagrid('getRows');
	var entities= '';
	for(i = 0;i < rows.length;i++){
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

function onHidePanelUpt(){
	var index=$('#change-upt-datagrid').datagrid('getRowIndex',$('#change-upt-datagrid').datagrid('getSelected'));
	var ed = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:'fReceProof'});
	var eds = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:'fReceProofs'});
	ed.target.textbox('setValue',eds.target.combobox('getValues'));
}
function onHidePanelFKPJUpt(){
	var index=$('#change-upt-datagrid').datagrid('getRowIndex',$('#change-upt-datagrid').datagrid('getSelected'));
	var ed = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:'fReceProof'});
	var eds = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:'fReceProofs'});
	ed.target.textbox('setValue',eds.target.combobox('getValues'));
}
function onShowPanelFKPJUpt(){
	var fpItemsName = $("#F_fpItemsName").val();
	if(fpItemsName==''){
		alert('该采购单品目名称为空，请核实！');
		return false;
	}
	var index=$('#change-upt-datagrid').datagrid('getRowIndex',$('#change-upt-datagrid').datagrid('getSelected'));
	var eds = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:'fReceProofs'});
	eds.target.combobox('reload',base+'/Formulation/lookupsJsonGist?code='+fpItemsName);
}
</script>