<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
		<table id="payer_info_base_tab" class="easyui-datagrid" style="width:695px;height:auto"
		data-options="
		singleSelect: true,
		toolbar: '#payer_info_base_toolbar',
		<c:if test="${!empty bean.rId}">
		url: '${base}/reimburse/payerInfojson?rId=${bean.rId}',
		</c:if>
		<c:if test="${empty bean.rId}">
		url: '',
		</c:if>
		<c:if test="${empty detail}">
		onClickRow: onClickRowPayerinfoBase,
		</c:if>
		method: 'post',
		striped : true,
		nowrap : false,
		rownumbers:true,
		scrollbarSize:0,
		onLoadSuccess:countAmountBase
		">
			<thead>
				<tr>
					<th data-options="field:'pId',hidden:true"></th>
					<th data-options="field:'rId',hidden:true"></th>
					<th data-options="field:'fInnerOrOuter',hidden:true"></th>
					<th data-options="field:'payeeId',align:'center',hidden:true,editor:{type:'tbasebox',options:{editable:true}}">收款人ID</th>
					<th data-options="field:'payeeName',align:'center',editor:{type:'tbasebox',options:{editable:true,required:true}}" width="20%">收款人</th>
					<th data-options="field:'bankAccount',align:'center',editor:{type:'tbasebox',options:{required:true,editable:true}}" width="40%">银行账号</th>
					<th data-options="field:'bank',align:'center',editor:{type:'tbasebox',options:{required:true,editable:true}}" width="20%">开户银行</th>
					<th data-options="field:'payeeAmount',align:'center',editor:{type:'numberbox',options:{required:true,editable:true,precision:2,iconCls:'icon-yuan',onChange:payeeAmountsBase}}" width="20%">转账金额</th>
				</tr>
			</thead>
		</table>
		<div id="payer_info_base_toolbar" style="height:30px;padding-top : 8px">
			<a href="javascript:void(0)" onclick="removeInfoBase()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="appendinfoBase()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</div>
</div>
<script type="text/javascript">
function nameMouseoverBase(rec) {
	//alert(rec);
    differentindex = layer.tips('点击分项内容，可查看付款信息情况', '.name', {
      tips: [1, '#3595CC'],
      time: 30000
    });
}

//接待人员表格添加删除，保存方法
var editIndexinfoBase = undefined;
function endEditinginfoBase() {
	if (editIndexinfoBase == undefined) {
		return true;
	}
	if ($('#payer_info_base_tab').datagrid('validateRow', editIndexinfoBase)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		/* var tr = $('#payer_info_base_tab').datagrid('getEditors', editIndexinfoBase);
		var tbase=tr[1].target.combotree('getTbase');
		if(tbase!='--请选择--'){
			tr[1].target.tbasebox('setValue',tbase);
		}
		var tbase1=tr[2].target.tbasebox('getTbase');
		if(tbase1!='--请选择--'){
			tr[2].target.tbasebox('setValue',tbase1);
		} */
		$('#payer_info_base_tab').datagrid('endEdit', editIndexinfoBase);
		editIndexinfoBase = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPayerinfoBase(index) {
	if (editIndexinfoBase != index) {
		if (endEditinginfoBase()) {
			$('#payer_info_base_tab').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndexinfoBase = index;
		} else {
			$('#payer_info_base_tab').datagrid('selectRow', editIndexinfoBase);
		}
	}
}
function appendinfoBase() {
	if (endEditinginfoBase()) {
		$('#payer_info_base_tab').datagrid('appendRow', {
		});
		editIndexinfoBase = $('#payer_info_base_tab').datagrid('getRows').length - 1;
		$('#payer_info_base_tab').datagrid('selectRow', editIndexinfoBase).datagrid('beginEdit',editIndexinfoBase);
	}
}
function removeInfoBase() {
	if (editIndexinfoBase == undefined) {
		return
	}
	$('#payer_info_base_tab').datagrid('cancelEdit', editIndexinfoBase).datagrid('deleteRow',
			editIndexinfoBase);
	editIndexinfoBase = undefined;
	countAmountBase();
	/* var rows = $('#payer_info_base_tab').datagrid('getRows');
	var travelDays=0;
	var hotelDays=0;
	for(var i=0;i<rows.length;i++){
		if(rows[i].travelDays!=""&&rows[i].travelDays!=null){
			travelDays += parseInt(rows[i].travelDays);
		}
		if(rows[i].hotelDays!=""&&rows[i].hotelDays!=null){
			hotelDays += parseInt(rows[i].hotelDays);
		}
	}
	$('#travelTotalDays').val(travelDays);
	$('#hotelTotalDays').val(hotelDays); */
}
function acceptpayerinfoBase() {
	if (endEditinginfoBase()) {
		$('#payer_info_base_tab').datagrid('acceptChanges');
	}
}
//获得json数据
function getpayerinfoJsonBase(){
	acceptpayerinfoBase();
	$('#payer_info_base_tab').datagrid('acceptChanges');
	var rows = $('#payer_info_base_tab').datagrid('getRows');
	var entities= '';
	for(var i = 0 ;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	$("#payerinfoJsonBase").val(entities);
}	
// Easyui中tbasebox中input事件失效的原因是 easy TbaseBox控件不是修改你的border 而是，将input进行了隐藏。然后用一个框放到了外面。实现所有浏览器效果统一。
$('#bankAccount').tbasebox({
	inputEvents: $.baseend({},$.fn.tbasebox.defaults.inputEvents,{
	keyup: function(event){
		var tempValue = $(this).val();
        if(null != tempValue && '' != tempValue && undefined != tempValue){
        	 var info=_getBankInfoByCardNo(tempValue);
        	$('#bankName').tbasebox('setValue',info.bankName); //银行名称 
        }
	}})
});

function payeeAmountsBase(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	if(newVal==''){
		newVal=0.00;
	}
	
	var rows = $('#payer_info_base_tab').datagrid('getRows');
	var index=$('#payer_info_base_tab').datagrid('getRowIndex',$('#payer_info_base_tab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsPayeeBase(rows,i);
		}
	}
    var payeeAmountInner = isNaN(parseFloat($("#payeeAmountInner").val()))?0:parseFloat($("#payeeAmountInner").val());
	$("#payeeAmountBase").val(num1);
	$("#payeeAmount").val(num1+payeeAmountInner);
}

function addNumsPayeeBase(rows,index){
	var num=0;
	if(rows[index].payeeAmount!=''&&rows[index].payeeAmount!='NaN'&&rows[index].payeeAmount!=undefined){
		num = parseFloat(rows[index].payeeAmount);
	}else{
		num =0;
	}
	return num;
}

function countAmountBase(){
	var rows = $('#payer_info_base_tab').datagrid('getRows');
	var index=$('#payer_info_base_tab').datagrid('getRowIndex',$('#payer_info_base_tab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsPayeeBase(rows,i);
		}
	}
    var payeeAmountInner = isNaN(parseFloat($("#payeeAmountInner").val()))?0:parseFloat($("#payeeAmountInner").val());
 	$("#payeeAmountBase").val(num1);
 	$("#payeeAmount").val(num1+payeeAmountInner);
}
</script>