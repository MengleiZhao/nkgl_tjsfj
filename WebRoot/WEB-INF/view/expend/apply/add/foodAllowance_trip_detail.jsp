<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px;border-top: 1px solid rgba(217,227,231,1);">

	<table id="foodtabTrip" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#foodtoolTrip',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/foodJson?gId=${bean.gId}&travelType=GWCX',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	striped : true,
	nowrap : true,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'ffId',hidden:true"></th>
				<th data-options="field:'fname',align:'center',width:160,editor:{type:'textbox',readonly:true}" >姓名</th>
				<th data-options="field:'fDays',align:'center',width:260,editor:{type:'numberbox',options:{editable:true,onChange:dayCountFood}}" >补贴天数</th>
				<th data-options="field:'fApplyAmount',align:'center',width:255,editor:{type:'numberbox',options:{editable:false,precision:2,onChange:fApplyAmountFood}}" >申请金额</th>
				<th data-options="field:'fnameId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
			</tr>
		</thead>
	</table>
	<div id="foodtoolTrip" style="height:20px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">伙食补助费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
	</div>
		<a style="float: right;color: #666666;font-size:12px;">申请金额：<span id=""><fmt:formatNumber groupingUsed="true" value="${bean.foodAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	<input type="hidden" id="foodJson" name="foodJson" />
</div>
<script type="text/javascript">

//伙食表格添加删除
var editIndexTrip = undefined;
function endEditingfoodTrip() {
	if (editIndexTrip == undefined) {
		return true;
	}
	if ($('#foodtabTrip').datagrid('validateRow', editIndexTrip)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#foodtabTrip').datagrid('getEditors', editIndexTrip);
		/* var text=tr[5].target.combotree('getText');
		if(text!='--请选择--'){
			tr[5].target.combotree('setValue',text);
		}
		var text1=tr[4].target.combotree('getText');
		if(text1!='--请选择--'){
			tr[4].target.combotree('setValue',text1);
		} */
		$('#foodtabTrip').datagrid('endEdit', editIndexTrip);
		calcFoodCost();
		editIndexTrip = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowfoodTrip(index) {
	$('#tracel_itinerary_tab_id').datagrid('acceptChanges');
	if (editIndexTrip != index) {
		if (endEditingfoodTrip()) {
			$('#foodtabTrip').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndexTrip = index;
		} else {
			$('#foodtabTrip').datagrid('selectRow', editIndexTrip);
		}
	}
}
function appendfoodTrip() {
	if (endEditingfoodTrip()) {
		$('#foodtabTrip').datagrid('appendRow', {});
		editIndexTrip = $('#foodtabTrip').datagrid('getRows').length - 1;
		$('#foodtabTrip').datagrid('selectRow', editIndexTrip).datagrid('beginEdit',editIndexTrip);
	}
}
function removefoodTrip() {
	if (editIndexTrip == undefined) {
		return
	}
	$('#foodtabTrip').datagrid('cancelEdit', editIndexTrip).datagrid('deleteRow',editIndexTrip);
	editIndexTrip = undefined;
	calcFoodCost();
}
function acceptfoodTrip() {
	if (endEditingfoodTrip()) {
		$('#foodtabTrip').datagrid('acceptChanges');
		calcFoodCost();
	}
}
//获得json数据
function getfoodJsonTrip(){
	acceptfoodTrip();
	$('#foodtabTrip').datagrid('acceptChanges');
	var rows = $('#foodtabTrip').datagrid('getRows');
	var entities= '';
	for(var i = 0 ;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	$("#foodJson").val(entities);
}	
function calcFoodCost(){
	//计算总额
	var rows = $('#foodtabTrip').datagrid('getRows');
	var foodAmount=parseFloat(0.00);
	for(var i=0;i<rows.length;i++){
		var money = isNaN(parseFloat(rows[i].fApplyAmount))?0.00:parseFloat(rows[i].fApplyAmount);
		foodAmount=foodAmount+money;
	}
	$('#applyFoodAmountTrip').html(listToFixed(foodAmount)+'[元]');
	$('#foodAmount').val(foodAmount.toFixed(2));
}	
function reload(rec){
	var row = $('#foodtabTrip').datagrid('getSelected');
	var rindex = $('#foodtabTrip').datagrid('getRowIndex', row); 
	var ed = $('#foodtabTrip').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleLevel'  
	});
	if(rec.code !='JTGJ06'){
		var url = base+'/vehicle/comboboxJson?selected=${travelBean.vehicle}&parentCode='+rec.code;
    	$(ed.target).combotree('reload', url);
	}
}
function countAmount(){
	var rows = $('#foodtabTrip').datagrid('getRows');
	var num1 = 0;
    for(var i=0;i<rows.length;i++){
			num1+=addNumsFood(rows,i);
		}
		$("#applyFoodAmountTrip").html(listToFixed(num1)+"[元]");
		$("#foodAmount").val(num1.toFixed(2));
		
		
		var cityAmount = $("#cityAmount").val();
		var hotelAmount = $("#hotelAmount").val();
		var outAmount = $("#outsideAmount").val();
		if(cityAmount=='NaN'||cityAmount==''||cityAmount==undefined||cityAmount==null){
			cityAmount=0;
		}
		if(hotelAmount=='NaN'||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
			hotelAmount=0;
		}
		if(outAmount=='NaN'||outAmount==''||outAmount==undefined||outAmount==null){
			outAmount=0;
		}
		$("#applyTotalAmountTrip").html(listToFixed((parseFloat(outAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(num1)))+"元");
		$("#applyAmount").val((parseFloat(outAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2));
}
function addNumsFood(rows,index){
	var num=0;
	if(rows[index].fApplyAmount!=''&&rows[index].fApplyAmount!='NaN'&&rows[index].fApplyAmount!=undefined){
		num = parseFloat(rows[index].fApplyAmount);
	}else{
		num =0;
	}
	return num;
}


function dayCountFood(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	var index=$('#foodtabTrip').datagrid('getRowIndex',$('#foodtabTrip').datagrid('getSelected'));
    var editors = $('#foodtabTrip').datagrid('getEditors', index);
    var fDays = parseInt(newVal);
    if(isNaN(fDays)){
    	fDays=0;
    }
    editors[2].target.numberbox('setValue', fDays*50);
}

function fApplyAmountFood(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	if(newVal==''){
		newVal=0.00;
	}
	
	var rows = $('#foodtabTrip').datagrid('getRows');
	var index=$('#foodtabTrip').datagrid('getRowIndex',$('#foodtabTrip').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsFood(rows,i);
		}
	}
		$("#applyFoodAmountTrip").html(num1.toFixed(2)+"[元]");
		$("#foodAmount").val(num1.toFixed(2));
		var hotelAmount = $("#hotelAmount").val();
		var cityAmount = $("#cityAmount").val();
		if(hotelAmount=='NaN'||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
			hotelAmount=0;
		}
		if(cityAmount=='NaN'||cityAmount==''||cityAmount==undefined||cityAmount==null){
			cityAmount=0;
		}
		$("#applyTotalAmountTrip").html(listToFixed((parseFloat(cityAmount)+parseFloat(hotelAmount)+parseFloat(num1)))+"元");
		$("#applyAmount").val((parseFloat(cityAmount)+parseFloat(hotelAmount)+parseFloat(num1)).toFixed(2));
}

</script>