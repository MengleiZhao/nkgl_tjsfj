<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
		<table id="payer_info_ext_tab" class="easyui-datagrid" style="width:695px;height:auto"
		data-options="
		singleSelect: true,
		toolbar: '#payer_info_ext_toolbar',
		<c:if test="${!empty bean.rId}">
		url: '${base}/reimburse/payerInfojson?rId=${bean.rId}&fInnerOrOuter=1',
		</c:if>
		<c:if test="${empty bean.rId}">
		url: '',
		</c:if>
		<c:if test="${empty detail}">
		onClickRow: onClickRowPayerinfoExt,
		</c:if>
		method: 'post',
		striped : true,
		nowrap : false,
		rownumbers:true,
		scrollbarSize:0,
		onLoadSuccess:countAmountExt
		">
			<thead>
				<tr>
					<th data-options="field:'pId',hidden:true"></th>
					<th data-options="field:'rId',hidden:true"></th>
					<th data-options="field:'fInnerOrOuter',hidden:true"></th>
					<th data-options="field:'payeeId',align:'center',hidden:true,editor:{type:'textbox',options:{editable:true}}">收款人ID</th>
					<!-- <th data-options="field:'payeeName',align:'center',editor:{type:'textbox',options:{editable:true,required:true}}" width="20%">收款人</th> -->
					
					<th data-options="field:'payeeName',align:'center',editor:{type:'combobox',
						options:{
							hasDownArrow:false,
							editable:true,
							required:true,
							valueField:'code',
							textField:'text',
							method:'post',
							url:base+'/reimburse/payeelookupsJson?fInnerOrOuter=1',
							onSelect:aftersuccess1
						}
					}" width="20%">收款人</th>
					
					<th data-options="field:'bankAccount',align:'center',editor:{type:'textbox',options:{required:true,editable:true}}" width="40%">银行账号</th>
					<th data-options="field:'bank',align:'center',editor:{type:'textbox',options:{required:true,editable:true}}" width="20%">开户银行</th>
					<th data-options="field:'payeeAmount',align:'center',editor:{type:'numberbox',options:{required:true,editable:true,precision:2,iconCls:'icon-yuan',onChange:payeeAmountsExt}}" width="20%">转账金额</th>
				</tr>
			</thead>
		</table>
		<div id="payer_info_ext_toolbar" style="height:30px;padding-top : 8px">
				<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">单位外部收款人信息</a>
			<a href="javascript:void(0)" onclick="removeInfoExt()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="appendinfoExt()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</div>
</div>
<input type="hidden" id="payeeAmountInner"/>
<input type="hidden" id="payeeAmountExt"/>
<script type="text/javascript">

function nameMouseoverExt(rec) {
	//alert(rec);
    differentindex = layer.tips('点击分项内容，可查看付款信息情况', '.name', {
      tips: [1, '#3595CC'],
      time: 30000
    });
}

function aftersuccess1(rec){
	var row = $('#payer_info_ext_tab').datagrid('getSelected');
	var rindex = $('#payer_info_ext_tab').datagrid('getRowIndex', row); 
	var ed = $('#payer_info_ext_tab').datagrid('getEditors',rindex);
	$.ajax({
		 type: "post",
         url: base + "/reimburse/findbypayeeId?id="+rec.code,
         contentType: "json",
         async : 'false',
         success: function (datas) {
			datas = eval("(" + datas + ")");
			ed[0].target.textbox('setValue',datas.payeeId);
			ed[2].target.textbox('setValue',datas.bankAccount);
			ed[3].target.textbox('setValue',datas.bank);
         }
	});
}
//接待人员表格添加删除，保存方法
var editIndexinfoExt = undefined;
function endEditinginfoExt() {
	if (editIndexinfoExt == undefined) {
		return true;
	}
	if ($('#payer_info_ext_tab').datagrid('validateRow', editIndexinfoExt)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		/* var tr = $('#payer_info_ext_tab').datagrid('getEditors', editIndexinfoExt);
		var text=tr[1].target.combotree('getText');
		if(text!='--请选择--'){
			tr[1].target.textbox('setValue',text);
		}
		var text1=tr[2].target.textbox('getText');
		if(text1!='--请选择--'){
			tr[2].target.textbox('setValue',text1);
		} */
		$('#payer_info_ext_tab').datagrid('endEdit', editIndexinfoExt);
		editIndexinfoExt = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPayerinfoExt(index) {
	if (editIndexinfoExt != index) {
		if (endEditinginfoExt()) {
			$('#payer_info_ext_tab').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndexinfoExt = index;
		} else {
			$('#payer_info_ext_tab').datagrid('selectRow', editIndexinfoExt);
		}
	}
}
function appendinfoExt() {
	if (endEditinginfoExt()) {
		$('#payer_info_ext_tab').datagrid('appendRow', {
		});
		editIndexinfoExt = $('#payer_info_ext_tab').datagrid('getRows').length - 1;
		$('#payer_info_ext_tab').datagrid('selectRow', editIndexinfoExt).datagrid('beginEdit',editIndexinfoExt);
	}
}
function removeInfoExt() {
	if (editIndexinfoExt == undefined) {
		return
	}
	$('#payer_info_ext_tab').datagrid('cancelEdit', editIndexinfoExt).datagrid('deleteRow',
			editIndexinfoExt);
	editIndexinfoExt = undefined;
	countAmountExt();
	/* var rows = $('#payer_info_ext_tab').datagrid('getRows');
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
function acceptpayerinfoExt() {
	if (endEditinginfoExt()) {
		$('#payer_info_ext_tab').datagrid('acceptChanges');
	}
}
//获得json数据
function getpayerinfoJsonExt(){
	acceptpayerinfoExt();
	$('#payer_info_ext_tab').datagrid('acceptChanges');
	var rows = $('#payer_info_ext_tab').datagrid('getRows');
	var entities= '';
	for(var i = 0 ;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	$("#payerinfoJsonExt").val(entities);
}	
// Easyui中textbox中input事件失效的原因是 easy TextBox控件不是修改你的border 而是，将input进行了隐藏。然后用一个框放到了外面。实现所有浏览器效果统一。
$('#bankAccount').textbox({
	inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
	keyup: function(event){
		var tempValue = $(this).val();
        if(null != tempValue && '' != tempValue && undefined != tempValue){
        	 var info=_getBankInfoByCardNo(tempValue);
        	$('#bankName').textbox('setValue',info.bankName); //银行名称 
        }
	}})
});

function payeeAmountsExt(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	if(newVal==''){
		newVal=0.00;
	}
	
	var rows = $('#payer_info_ext_tab').datagrid('getRows');
	var index=$('#payer_info_ext_tab').datagrid('getRowIndex',$('#payer_info_ext_tab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsPayeeExt(rows,i);
		}
	}
    var payeeAmountInner = isNaN(parseFloat($("#payeeAmountInner").val()))?0:parseFloat($("#payeeAmountInner").val());
	$("#payeeAmountExt").val(num1);
	$("#payeeAmount").val(num1+payeeAmountInner);
}

function addNumsPayeeExt(rows,index){
	var num=0;
	if(rows[index].payeeAmount!=''&&rows[index].payeeAmount!='NaN'&&rows[index].payeeAmount!=undefined){
		num = parseFloat(rows[index].payeeAmount);
	}else{
		num =0;
	}
	return num;
}

function countAmountExt(){
	var rows = $('#payer_info_ext_tab').datagrid('getRows');
	var index=$('#payer_info_ext_tab').datagrid('getRowIndex',$('#payer_info_ext_tab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsPayeeExt(rows,i);
		}
	}
    var payeeAmountInner = isNaN(parseFloat($("#payeeAmountInner").val()))?0:parseFloat($("#payeeAmountInner").val());
 	$("#payeeAmountExt").val(num1);
 	$("#payeeAmount").val(num1+payeeAmountInner);
}
</script>