<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px;">

	<table id="in_city_trip_tab_id" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#in_city_trip_id',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/applyInCityPage?gId=${bean.gId}&travelType=GWCX',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	onClickRow:onClickRowInCity,
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'ftId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
				<th data-options="field:'fPerson',width:160,align:'center',editor:{type:'textbox',options:{editable:true}}">姓名</th>
				<th data-options="field:'fAdjacentDay',align:'center',width:260,editor:{type:'numberbox',options:{editable:true,onChange:adjacentDayCount}}" ><!-- 相邻区 -->补贴天数</th>
				<!-- <th data-options="field:'fDistanceDay',align:'center',width:180,editor:{type:'numberbox',options:{editable:true,onChange:distanceDayCount}}" >相隔区补贴天数</th> -->
				<th data-options="field:'fApplyAmount',width:255,align:'center',editor:{type:'numberbox',options:{editable:false,precision:2,onChange:fApplyAmountCity}}">申请金额</th>
			</tr>
		</thead>
	</table>
	<c:if test="${empty detail}">
	<div id="in_city_trip_id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">市内交通费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<%-- <a href="javascript:void(0)" onclick="removeitInCity()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendInCityTrip()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
	</div>
		<a style="float: right;color: #666666;font-size:12px;">小计金额：<span id="applyInCityAmountTrip"><fmt:formatNumber groupingUsed="true" value="${bean.cityAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</c:if>
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="travelTotalDays"  />
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="inCityJson" name="inCity" />
</div>
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editIndexInCityTrip = undefined;
function endEditingInCityTrip() {
	if (editIndexInCityTrip == undefined) {
		return true
	}
	if ($('#in_city_trip_tab_id').datagrid('validateRow', editIndexInCityTrip)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#in_city_trip_tab_id').datagrid('getEditors', editIndexInCityTrip);
		var text=tr[1].target.combotree('getText');
		if(text!='--请选择--'){
			tr[1].target.textbox('setValue',text);
		}
		var text1=tr[2].target.textbox('getText');
		if(text1!='--请选择--'){
			tr[2].target.textbox('setValue',text1);
		}
		$('#in_city_trip_tab_id').datagrid('endEdit', editIndexInCityTrip);
		editIndexInCityTrip = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowInCity(index) {
	if(sign==1){
		if (editIndexInCityTrip != index) {
			if (endEditingInCityTrip()) {
				$('#in_city_trip_tab_id').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndexInCityTrip = index;
			} else {
				$('#in_city_trip_tab_id').datagrid('selectRow', editIndexInCityTrip);
			}
		}
	}else{
		alert("请先保存行程清单！");
		return false;
	}
}
function appendInCityTrip() {
	if (endEditingInCityTrip()) {
		$('#in_city_trip_tab_id').datagrid('appendRow', {
			
			
			
			
		});
		editIndexInCityTrip = $('#in_city_trip_tab_id').datagrid('getRows').length - 1;
		$('#in_city_trip_tab_id').datagrid('selectRow', editIndexInCityTrip).datagrid('beginEdit',editIndexInCityTrip);
	}
}
function removeitInCity() {
	if (editIndexInCityTrip == undefined) {
		return
	}
	$('#in_city_trip_tab_id').datagrid('cancelEdit', editIndexInCityTrip).datagrid('deleteRow',
			editIndexInCityTrip);
	editIndexInCityTrip = undefined;
	var rows = $('#in_city_trip_tab_id').datagrid('getRows');
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
	$('#hotelTotalDays').val(hotelDays);
}
function acceptInCityTrip() {
	if (endEditingInCityTrip()) {
		$('#in_city_trip_tab_id').datagrid('acceptChanges');
	}
}
	
	
//计算天数
function setDays5(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#in_city_trip_tab_id').datagrid('getRowIndex',$('#in_city_trip_tab_id').datagrid('getSelected'));
	var rows = $('#in_city_trip_tab_id').datagrid('getRows');
    var editors = $('#in_city_trip_tab_id').datagrid('getEditors', index);  
    var day1 = editors[0]; 
    var day2 = editors[1]; 
    startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    if(day1!=''&&day2!=''){
    	if(endday<startday){
    		alert("结束日期不能小于开始日期！");
    		editors[0].target.datebox('setValue', '');
    	}
    }
	/* for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumDays=setEditing(rows,i);
		}else{
			totalDays+=addNum(rows,i);
		}
		totalDays =fsumDays+totalDays;
		//$('#travelTotalDays').val(totalDays);
		//$('#hotelTotalDays').val(totalDays);
	} */
	
}

function setDays6(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#in_city_trip_tab_id').datagrid('getRowIndex',$('#in_city_trip_tab_id').datagrid('getSelected'));
	var rows = $('#in_city_trip_tab_id').datagrid('getRows');
    var editors = $('#in_city_trip_tab_id').datagrid('getEditors', index);  
    var day1 = editors[0]; 
    var day2 = editors[1]; 
    startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    if(day1!=''&&day2!=''){
    	if(endday<startday){
    		alert("结束日期不能小于开始日期！");
    		editors[1].target.datebox('setValue', '');
    	}
    }
	/* for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumDays=setEditing(rows,i);
		}else{
			totalDays+=addNum(rows,i);
		}
		totalDays =fsumDays+totalDays;
		//$('#travelTotalDays').val(totalDays);
		//$('#hotelTotalDays').val(totalDays);
	} */
	
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
/* function setEditing(rows,index){
    var editors = $('#in_city_trip_tab_id').datagrid('getEditors', index);  
    var day1 = editors[0]; 
    var day2 = editors[1]; 
    var travelDays =editors[8];
    var hotelDays =editors[9];
	startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    var totalDays = (endday - startday) / 86400000 + 1;
    travelDays.target.numberbox('setValue', totalDays);
    hotelDays.target.numberbox('setValue', totalDays-1);
    return totalDays;
} */


function inCityJsonTrip(){
	acceptInCityTrip();
	var rows2 = $('#in_city_trip_tab_id').datagrid('getRows');
	var inCity = "";
	if(rows2==''){
	}else{
		for (var i = 0; i < rows2.length; i++) {
			inCity = inCity + JSON.stringify(rows2[i]) + ",";
			}
		$('#inCityJson').val(inCity);
	}
}


	/* function calcInCity(){
		//计算总额
		var rows = $('#in_city_trip_tab_id').datagrid('getRows');
		var inCityAmount=parseFloat(0.00);
		for(var i=0;i<rows.length;i++){
			var money = isNaN(parseFloat(rows[i].fApplyAmount))?0.00:parseFloat(rows[i].fApplyAmount);
			inCityAmount=inCityAmount+money;
		}
		$('#applyInCityAmountTrip').html(listToFixed(inCityAmount)+'元');
		$('#cityAmount').val(inCityAmount);
	} */


	function adjacentDayCount(newVal,oldVal){
		if(newVal==undefined || oldVal==undefined){
			return false;
		}
		var index=$('#in_city_trip_tab_id').datagrid('getRowIndex',$('#in_city_trip_tab_id').datagrid('getSelected'));
	    var editors = $('#in_city_trip_tab_id').datagrid('getEditors', index);
	    var adjacentDay = parseInt(newVal);
	   /*  var distanceDay = parseInt(editors[2].target.val()); */
	    if(isNaN(adjacentDay)){
	    	adjacentDay=0;
	    }
	  /*   if(isNaN(distanceDay)){
	    	distanceDay=0;
	    } */
	    var amount = adjacentDay*50;
	    editors[2].target.numberbox('setValue', amount.toFixed(2));
	}
	function distanceDayCount(newVal,oldVal){
		if(newVal==undefined || oldVal==undefined){
			return false;
		}
		var index=$('#in_city_trip_tab_id').datagrid('getRowIndex',$('#in_city_trip_tab_id').datagrid('getSelected'));
	    var editors = $('#in_city_trip_tab_id').datagrid('getEditors', index);
	    var adjacentDay = parseInt(editors[1].target.val());
	    var distanceDay = parseInt(newVal);
	    if(isNaN(adjacentDay)){
	    	adjacentDay=0;
	    }
	    if(isNaN(distanceDay)){
	    	distanceDay=0;
	    }
	    var amount = (adjacentDay*50)+(distanceDay*100);
	    editors[3].target.numberbox('setValue', amount);
	}
	
	function fApplyAmountCity(newVal,oldVal){
		if(newVal==undefined || oldVal==undefined){
			return false;
		}
		if(newVal==''){
			newVal=0.00;
		}
		
		var rows = $('#in_city_trip_tab_id').datagrid('getRows');
		var index=$('#in_city_trip_tab_id').datagrid('getRowIndex',$('#in_city_trip_tab_id').datagrid('getSelected'));
	     var num1 = 0;
	     for(var i=0;i<rows.length;i++){
			if(i==index){
				num1+=parseFloat(newVal);
			}else{
				num1+=addNumsCity(rows,i);
			}
		}
			$("#applyInCityAmountTrip").html(num1.toFixed(2)+"[元]");
			$("#cityAmount").val(num1.toFixed(2));
			var hotelAmount = $("#hotelAmount").val();
			var foodAmount = $("#foodAmount").val();
			if(hotelAmount=='NaN'||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
				hotelAmount=0;
			}
			if(foodAmount=='NaN'||foodAmount==''||foodAmount==undefined||foodAmount==null){
				foodAmount=0;
			}
			$("#applyTotalAmountTrip").html(listToFixed((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(num1)))+"元");
			$("#applyAmount").val((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(num1)).toFixed(2));
	}
	
	function addNumsCity(rows,index){
		var num=0;
		if(rows[index].fApplyAmount!=''&&rows[index].fApplyAmount!='NaN'&&rows[index].fApplyAmount!=undefined){
			num = parseFloat(rows[index].fApplyAmount);
		}else{
			num =0;
		}
		return num;
	}
</script>