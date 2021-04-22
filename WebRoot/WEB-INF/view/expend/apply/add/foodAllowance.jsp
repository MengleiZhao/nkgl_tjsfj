<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px;border-top: 1px solid rgba(217,227,231,1);">

	<table id="foodtab" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#foodtool',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/foodJson?gId=${bean.gId}&travelType=GWCC',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	onClickRow: onClickRowfood,
	method: 'post',
	striped : true,
	nowrap : true,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'ffId',hidden:true"></th>
				<th data-options="field:'fname',align:'center',width:160,editor:{type:'textbox',readonly:true,options:{editable:false}}" >姓名</th>
				<th data-options="field:'fDays',align:'center',width:260,editor:{type:'numberbox',precision:2,readonly:false,options:{onChange:changeDays,editable:true}}" >补贴天数</th>
				<th data-options="field:'fApplyAmount',align:'center',width:260,editor:{type:'numberbox',precision:2,options:{editable:false}}" >补贴费用</th>
				<th data-options="field:'foodMoney',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'fnameId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
			</tr>
		</thead>
	</table>
	<c:if test="${empty detail}">
	<div id="foodtool" style="height:20px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">伙食补助费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<%-- <a href="javascript:void(0)" onclick="removefood()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendfood()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
	</div>
		<a style="float: right;color: #666666;font-size:12px;">小计金额：<span id="applyFoodAmount"><fmt:formatNumber groupingUsed="true" value="${bean.foodAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</c:if>
	<input type="hidden" id="foodJson" name="foodJson" />
</div>
<script type="text/javascript">

//伙食表格添加删除
var editIndex = undefined;
function endEditingfood() {
	if (editIndex == undefined) {
		return true;
	}
	if ($('#foodtab').datagrid('validateRow', editIndex)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#foodtab').datagrid('getEditors', editIndex);
		/* var text=tr[5].target.combotree('getText');
		if(text!='--请选择--'){
			tr[5].target.combotree('setValue',text);
		}
		var text1=tr[4].target.combotree('getText');
		if(text1!='--请选择--'){
			tr[4].target.combotree('setValue',text1);
		} */
		$('#foodtab').datagrid('endEdit', editIndex);
		calcFoodCost();
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowfood(index) {
	$('#tracel_itinerary_tab_id').datagrid('acceptChanges');
	if (editIndex != index) {
		if (endEditingfood()) {
			$('#foodtab').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndex = index;
		} else {
			$('#foodtab').datagrid('selectRow', editIndex);
		}
	}
}
function appendfood() {
	if (endEditingfood()) {
		$('#foodtab').datagrid('appendRow', {});
		editIndex = $('#foodtab').datagrid('getRows').length - 1;
		$('#foodtab').datagrid('selectRow', editIndex).datagrid('beginEdit',editIndex);
	}
}
function removefood() {
	if (editIndex == undefined) {
		return
	}
	$('#foodtab').datagrid('cancelEdit', editIndex).datagrid('deleteRow',editIndex);
	editIndex = undefined;
	calcFoodCost();
}
function acceptfood() {
	if (endEditingfood()) {
		$('#foodtab').datagrid('acceptChanges');
		calcFoodCost();
	}
}
//获得json数据
function getfoodJson(){
	acceptfood();
	$('#foodtab').datagrid('acceptChanges');
	var rows = $('#foodtab').datagrid('getRows');
	var entities= '';
	for(var i = 0 ;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	$("#foodJson").val(entities);
}	
function calcFoodCost(){
	//计算总额
	
	var rows = $('#foodtab').datagrid('getRows');
	var foodAmount=parseFloat(0.00);
	for(var i=0;i<rows.length;i++){
		var money = isNaN(parseFloat(rows[i].fApplyAmount))?0.00:parseFloat(rows[i].fApplyAmount);
		foodAmount=foodAmount+money;
	}
	$('#applyFoodAmount').html(listToFixed(foodAmount)+'元');
	$('#foodAmount').val(foodAmount.toFixed(2));
}	
/* 	//重新计算开支标准
	function countkzbz() {
		accept();
		//获得接待人数（有几行就是接待多少人）
		var rownum = $('#foodtab').datagrid('getRows').length;
		//修改明细表中的开支标准
		var rows = $('#appli-detail-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++) {
			var tr = $('#appli-detail-dg').datagrid('getEditors', i);
			//获得每一行的开支标准
			var kzbz=rows[i].standard;
			//设置开支标准
			onClickRow(i);
			tr[1].target.textbox('setValue', parseFloat(kzbz*rownum));
			accept();
		}
	} */
	
/* //计算天数
function setDays1(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#foodtab').datagrid('getRowIndex',$('#foodtab').datagrid('getSelected'));
	var rows = $('#foodtab').datagrid('getRows');
    var editors = $('#foodtab').datagrid('getEditors', index);  
    var day1 = editors[6]; 
    var day2 = editors[7]; 
    startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    if(day1!=''&&day2!=''){
    	if(endday<startday){
    		alert("结束日期不能小于开始日期！");
    		editors[6].target.datebox('setValue', '');
    	}
    }
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumDays=setEditing(rows,i);
		}else{
			totalDays+=addNum(rows,i);
		}
		totalDays =fsumDays+totalDays;
		//$('#travelTotalDays').val(totalDays);
		//$('#hotelTotalDays').val(totalDays);
		calcFoodCost();
	}
	
}

function setDays2(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#foodtab').datagrid('getRowIndex',$('#foodtab').datagrid('getSelected'));
	var rows = $('#foodtab').datagrid('getRows');
    var editors = $('#foodtab').datagrid('getEditors', index);  
    var day1 = editors[6]; 
    var day2 = editors[7]; 
    startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    if(day1!=''&&day2!=''){
    	if(endday<startday){
    		alert("结束日期不能小于开始日期！");
    		editors[7].target.datebox('setValue', '');
    	}
    }
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumDays=setEditing(rows,i);
		}else{
			totalDays+=addNum(rows,i);
		}
		totalDays =fsumDays+totalDays;
		//$('#travelTotalDays').val(totalDays);
		//$('#hotelTotalDays').val(totalDays);
		calcFoodCost();
	}
	
}
//未编辑或者已经编辑完毕的行，计算天数
function addNum(rows,index){
	var totalDays=0;
	var startday=new Date(rows[index]['startTime']);
	var endday=new Date (rows[index]['endTime']);
	if(startday!="" && startday!=null && endday!="" && endday!=null){
		totalDays = (endday - startday) / 86400000 + 1;
	}
	return totalDays;
}
//对于正在编辑的行，计算天数
function setEditing(rows,index){
    var editors = $('#foodtab').datagrid('getEditors', index);  
    var day1 = editors[6]; 
    var day2 = editors[7]; 
    var travelDays =editors[8];
    var hotelDays =editors[9];
	startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    var totalDays = (endday - startday) / 86400000 + 1;
    travelDays.target.numberbox('setValue', totalDays);
    hotelDays.target.numberbox('setValue', totalDays-1);
    return totalDays;
}
 */
function reload(rec){
	var row = $('#foodtab').datagrid('getSelected');
	var rindex = $('#foodtab').datagrid('getRowIndex', row); 
	var ed = $('#foodtab').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleLevel'  
	});
	if(rec.code !='JTGJ06'){
		var url = base+'/vehicle/comboboxJson?selected=${travelBean.vehicle}&parentCode='+rec.code;
    	$(ed.target).combotree('reload', url);
	}/* else{
		$('#vehicleOther1').css('display','');
		$('#vehicleOther2').css('display','');
		$('#vehicleLevel1').css('display','none');
		$('#vehicleLevel2').css('display','none');
	}
	 $(ed.target).combobox('setValue', '2016');  */
}
function countAmount(){
	var rows = $('#foodtab').datagrid('getRows');
	var num1 = 0;
    for(var i=0;i<rows.length;i++){
			num1+=addNumsFood(rows,i);
		}
		$("#applyFoodAmount").html(listToFixed(num1)+"元");
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
		$("#applyTotalAmount").html(listToFixed((parseFloat(outAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(num1)))+"元");
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
//修改天数
function changeDays(newVal,oldVal){
	
	if(newVal==undefined || oldVal==undefined || oldVal==''){
		return false;
	}
	
	var rows = $('#foodtab').datagrid('getRows');
	var index=$('#foodtab').datagrid('getRowIndex',$('#foodtab').datagrid('getSelected'));
	var editors = $('#foodtab').datagrid('getEditors', index);
	if(parseInt(newVal) > rows[index].fDays){
		editors[1].target.numberbox('setValue', 0);
		alert("补贴天数不能超过上限");
	}else{
	    var adjacentDay = parseInt(newVal);
	    if(isNaN(adjacentDay)){
	    	adjacentDay=0;
	    }
	    var amount = adjacentDay*rows[index].foodMoney;
	    editors[2].target.numberbox('setValue', amount.toFixed(2));
	    rows[index].fApplyAmount = amount.toFixed(2);
	    calcFoodCost();
	    $("#applyTotalAmount").html(listToFixed((parseFloat(foodAmount.value)+parseFloat(hotelAmount.value)+parseFloat(cityAmount.value)))+"元");
	}
	
}
</script>