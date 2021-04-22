<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
		<table id="payer_info_ext_dir_tab" class="easyui-datagrid" style="width:695px;height:auto"
		data-options="
		singleSelect: true,
		toolbar: '#payer_info_ext_dir_toolbar',
		<c:if test="${!empty bean.drId}">
		<%-- url: '${base}/reimburse/payerInfojson?drId=${bean.drId}&fInnerOrOuter=1', --%>
		url: '${base}/directlyReimburse/payerInfojson?drId=${bean.drId}&fInnerOrOuter=1',
		</c:if>
		<c:if test="${empty bean.drId}">
		url: '',
		</c:if>
		onClickRow: onClickRowPayerinfoDirExt,
		method: 'post',
		striped : true,
		nowrap : false,
		rownumbers:true,
		scrollbarSize:0,
		onLoadSuccess:countAmountDirExt
		">
			<thead>
				<tr>
					<th data-options="field:'pId',hidden:true"></th>
					<th data-options="field:'drId',hidden:true"></th>
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
							onSelect:aftersuccessDir1
						}
					}" width="25%">收款人</th>
					
					<th data-options="field:'bankAccount',align:'center',editor:{type:'textbox',options:{required:true,editable:true}}" width="40%">银行账号</th>
					<th data-options="field:'bank',align:'center',editor:{type:'textbox',options:{required:true,editable:true}}" width="20%">开户银行</th>
					<th data-options="field:'payeeAmount',align:'center',editor:{type:'numberbox',options:{required:true,editable:true,precision:2,iconCls:'icon-yuan',onChange:payeeAmountsDirExt}}" width="20%">转账金额</th>
				</tr>
			</thead>
		</table>
		<div id="payer_info_ext_dir_toolbar" style="height:30px;padding-top : 8px">
				<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">单位外部收款人信息</a>
			<a href="javascript:void(0)" onclick="removeInfoDirExt()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="appendinfoDirExt()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
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

function aftersuccessDir1(rec){
	var row = $('#payer_info_ext_dir_tab').datagrid('getSelected');
	var rindex = $('#payer_info_ext_dir_tab').datagrid('getRowIndex', row); 
	var ed = $('#payer_info_ext_dir_tab').datagrid('getEditors',rindex);
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
var editIndexinfoDirExt = undefined;
function endEditinginfoDirExt() {
	if (editIndexinfoDirExt == undefined) {
		return true;
	}
	if ($('#payer_info_ext_dir_tab').datagrid('validateRow', editIndexinfoDirExt)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		/* var tr = $('#payer_info_ext_dir_tab').datagrid('getEditors', editIndexinfoDirExt);
		var text=tr[1].target.combotree('getText');
		if(text!='--请选择--'){
			tr[1].target.textbox('setValue',text);
		}
		var text1=tr[2].target.textbox('getText');
		if(text1!='--请选择--'){
			tr[2].target.textbox('setValue',text1);
		} */
		$('#payer_info_ext_dir_tab').datagrid('endEdit', editIndexinfoDirExt);
		editIndexinfoDirExt = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPayerinfoDirExt(index) {
	if (editIndexinfoDirExt != index) {
		if (endEditinginfoDirExt()) {
			$('#payer_info_ext_dir_tab').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndexinfoDirExt = index;
		} else {
			$('#payer_info_ext_dir_tab').datagrid('selectRow', editIndexinfoDirExt);
		}
	}
}
function appendinfoDirExt() {
	if (endEditinginfoDirExt()) {
		$('#payer_info_ext_dir_tab').datagrid('appendRow', {
		});
		editIndexinfoDirExt = $('#payer_info_ext_dir_tab').datagrid('getRows').length - 1;
		$('#payer_info_ext_dir_tab').datagrid('selectRow', editIndexinfoDirExt).datagrid('beginEdit',editIndexinfoDirExt);
	}
}
function removeInfoDirExt() {
	if (editIndexinfoDirExt == undefined) {
		return
	}
	$('#payer_info_ext_dir_tab').datagrid('cancelEdit', editIndexinfoDirExt).datagrid('deleteRow',
			editIndexinfoDirExt);
	editIndexinfoDirExt = undefined;
	countAmountDirExt();
}
function acceptpayerinfoDirExt() {
	if (endEditinginfoDirExt()) {
		$('#payer_info_ext_dir_tab').datagrid('acceptChanges');
	}
}
//获得json数据
function getpayerinfoJsonExt(){
	acceptpayerinfoDirExt();
	$('#payer_info_ext_dir_tab').datagrid('acceptChanges');
	var rows = $('#payer_info_ext_dir_tab').datagrid('getRows');
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

function payeeAmountsDirExt(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	if(newVal==''){
		newVal=0.00;
	}
	
	var rows = $('#payer_info_ext_dir_tab').datagrid('getRows');
	var index=$('#payer_info_ext_dir_tab').datagrid('getRowIndex',$('#payer_info_ext_dir_tab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsPayeeDirExt(rows,i);
		}
	}
    var payeeAmountInner = isNaN(parseFloat($("#payeeAmountInner").val()))?0:parseFloat($("#payeeAmountInner").val());
	$("#payeeAmountExt").val(num1);
	$("#payeeAmount").val(num1+payeeAmountInner);
}

function addNumsPayeeDirExt(rows,index){
	var num=0;
	if(rows[index].payeeAmount!=''&&rows[index].payeeAmount!='NaN'&&rows[index].payeeAmount!=undefined){
		num = parseFloat(rows[index].payeeAmount);
	}else{
		num =0;
	}
	return num;
}

function countAmountDirExt(){
	var rows = $('#payer_info_ext_dir_tab').datagrid('getRows');
	var index=$('#payer_info_ext_dir_tab').datagrid('getRowIndex',$('#payer_info_ext_dir_tab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsPayeeDirExt(rows,i);
		}
	}
    var payeeAmountInner = isNaN(parseFloat($("#payeeAmountInner").val()))?0:parseFloat($("#payeeAmountInner").val());
 	$("#payeeAmountExt").val(num1);
 	$("#payeeAmount").val(num1+payeeAmountInner);
}
</script>