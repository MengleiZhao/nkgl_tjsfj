<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-top:10px;padding-top: 0px;margin-left: 0px;">
	<!-- <div style="margin-right:40px;float:left">
		<span class="style1"></span>
	</div> -->
	<c:if test="${empty detail}">
		<div style="margin-right:0px;float:right">
			<a href="javascript:void(0)" onclick="editAbroad()" id="editAbroadId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a href="javascript:void(0)" onclick="addAbroad()" id="addAbroadId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="removeit1()" id="removeit1Id" style="float: right;margin-right: 22px;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="append1()" id="append1Id"  style="float: right; "><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</div>
	</c:if>
	<table id="abroad-plan-dg" class="easyui-datagrid" style="width:707px;height:auto;"
	data-options="
	toolbar: '#dmp',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/abroadPlan?id=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow1,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<th data-options="field:'planId',hidden:true"></th>			
			<th data-options="field:'gId',hidden:true"></th>			
			<th data-options="field:'abroadDate',required:'required',align:'center',width:140,editor:{type:'datebox',options:{onChange:setDaysPlan1}},formatter:ChangeDateFormat1">出发日期</th>
			<th data-options="field:'timeEnd',required:'required',align:'center',width:140,editor:{type:'datebox',options:{onChange:setDaysPlan2}},formatter:ChangeDateFormat1">撤离日期/抵华日期</th>
			<th data-options="field:'arriveCountryId',editor:'textbox',hidden:true">目的国ID</th>
			<th data-options="field:'arriveCountry',required:'required',align:'center',width:130,editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     var row = $('#abroad-plan-dg').datagrid('getSelected');
									     var index = $('#abroad-plan-dg').datagrid('getRowIndex',row);
									     selectAbroadDetail(index)
									     }}]}}">目的国/地区</th>
			<th data-options="field:'arriveCity',required:'required',align:'center',width:130,editor:{type:'textbox',options:{editable:false}}">城市</th>
			<th data-options="field:'remark',required:'required',align:'center',width:138,editor:'textbox'">备注</th>
		</tr>
	</thead>
</table>
</div>
<input type="hidden" id="abroadPlan" name="abroadPlan"/>
<script type="text/javascript">
var sign = 0;

var gId = '${bean.gId}';

if(gId!=''){
	$("#editAbroadId").show();
	$("#addAbroadId").hide();
	$("#removeit1Id").hide();
	$("#append1Id").hide();
	$("#travelingExpenseId").show();
	$("#outsideAppendId").show();
	$("#outsideRemoveitId").show();
	$("#travelingAppendId").show();
	$("#hotelRemoveId").show();
	$("#hotelAppendId").show();
	$("#feteCostRemoveitId").show();
	$("#feteCostAppendId").show();
	$("#otherRemoveitId").show();
	$("#otherAppendId").show();
	$('#chooseuserId').removeAttr('onclick');
	sign =1;
}
function addAbroad(){
	sign = 1;
	$('#gName').textbox('readonly',true);
	$('#fTeamName').textbox('readonly',true);
	$('#fAbroadPlace').textbox('readonly',true);
	$('#fTeamLeader').textbox('readonly',true);
	$('#fTeamPersonNum').textbox('readonly',true);
	$('#abroadDateStart').textbox('readonly',true);
	$('#abroadDateEnd').textbox('readonly',true);
	$('#fAbroadPeople').textbox('readonly',true);
	$('#abroadDay').textbox('readonly',true);
	$("#fDiningPlaceId1").attr('disabled','disabled');
	$("#fDiningPlaceId2").attr('disabled','disabled');
	$("#fAbroadPeople").attr('disabled','disabled');
	$('#chooseuserId').removeAttr('onclick');
	$("#editAbroadId").show();
	$("#addAbroadId").hide();
	$("#removeit1Id").hide();
	$("#append1Id").hide();
	$("#travelingExpenseId").show();
	$("#outsideAppendId").show();
	$("#outsideRemoveitId").show();
	$("#travelingAppendId").show();
	$("#hotelRemoveId").show();
	$("#hotelAppendId").show();
	$("#feteCostRemoveitId").show();
	$("#feteCostAppendId").show();
	$("#otherRemoveitId").show();
	$("#otherAppendId").show();
	
	
	var international = $('#international_traveling_expense_id').datagrid('getRows');
	for(var i = international.length-1 ; i >= 0 ; i--){
		$('#international_traveling_expense_id').datagrid('deleteRow',i);
	}
	editIndexTravelingExpense = undefined;
	$('#applyTravelingExpense').html('0.00[元]');
	
	
	var outside = $('#apply_outside_traffic_tab_id').datagrid('getRows');
	for(var i = outside.length-1 ; i >= 0 ; i--){
		$('#apply_outside_traffic_tab_id').datagrid('deleteRow',i);
	}
	editIndexInternational = undefined;
	$('#applyOutsideTrafficAmount').html('0.00[元]');
	
	
	var hoteltab = $('#hoteltabApply').datagrid('getRows');
	for(var i = hoteltab.length-1 ; i >= 0 ; i--){
		$('#hoteltabApply').datagrid('deleteRow',i);
	}
	editIndexHotel = undefined;
	$('#hotelAmounts').html('0.00[元]');
	
	
	var foodrows = $('#foodtab').datagrid('getRows');
	for(var i = foodrows.length-1 ; i >= 0 ; i--){
		$('#foodtab').datagrid('deleteRow',i);
	}
	editIndexFoodAboard = undefined;
	$('#applyFoodAmount').html('0.00[元]');
	
	
	var feeRows = $('#rec-fee-dg').datagrid('getRows');
	for(var i = feeRows.length-1 ; i >= 0 ; i--){
		$('#rec-fee-dg').datagrid('deleteRow',i);
	}
	editIndex4 = undefined;
	$('#feeAmounts').html('0.00[元]');
	
	
	var fete = $('#rec-fete-dg').datagrid('getRows');
	for(var i = fete.length-1 ; i >= 0 ; i--){
		$('#rec-fete-dg').datagrid('deleteRow',i);
	}
	editIndex3 = undefined;
	$('#feteCostAmounts').html('0.00[元]');
	
	
	var other = $('#rec-other-dg').datagrid('getRows');
	for(var i = other.length-1 ; i >= 0 ; i--){
		$('#rec-other-dg').datagrid('deleteRow',i);
	}
	editIndex2 = undefined;
	$('#otherAmounts').html('0.00[元]');
	
	
	addMiscellaneousFeeAndFoodInfo();
}
function editAbroad(){
	sign = 0;
	$('#gName').textbox('readonly',false);
	$('#fTeamName').textbox('readonly',false);
	$('#fAbroadPlace').textbox('readonly',false);
	$('#fTeamLeader').textbox('readonly',false);
	$('#fTeamPersonNum').textbox('readonly',false);
	$('#abroadDateStart').textbox('readonly',false);
	$('#abroadDateEnd').textbox('readonly',false);
	$('#fAbroadPeople').textbox('readonly',false);
	$('#fDiningPlaceId1').removeAttr("disabled");
	$('#fDiningPlaceId2').removeAttr("disabled");
	$('#fAbroadPeople').removeAttr("disabled");
	$("#chooseuserId").attr("onclick","chooseuser();");
	$("#editAbroadId").hide();
	$("#addAbroadId").show();
	$("#removeit1Id").show();
	$("#append1Id").show();
	$("#travelingExpenseId").hide();
	$("#outsideAppendId").hide();
	$("#outsideRemoveitId").hide();
	$("#travelingAppendId").hide();
	$("#hotelRemoveId").hide();
	$("#hotelAppendId").hide();
	$("#feteCostRemoveitId").hide();
	$("#feteCostAppendId").hide();
	$("#otherRemoveitId").hide();
	$("#otherAppendId").hide();
	acceptTravelingExpense();
	acceptInternational();
	accepthotel();
	accept2();
}
//接待人员表格添加删除，保存方法
	var editIndex1 = undefined;
	function endEditing1() {
		if (editIndex1 == undefined) {
			return true
		}
		if ($('#abroad-plan-dg').datagrid('validateRow', editIndex1)) {
			//下面三行，是在增加一行的时候，防止原来的一行的值变成code
			/* var tr = $('#abroad-plan-dg').datagrid('getEditors', editIndex1);
			var text=tr[3].target.textbox('getText');
			if(text!='--请选择--'){
				tr[3].target.textbox('setValue',text);
			} */
			$('#abroad-plan-dg').datagrid('endEdit', editIndex1);
			editIndex1 = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow1(index) {
		if(sign==0){
			if (editIndex1 != index) {
				if (endEditing1()) {
					$('#abroad-plan-dg').datagrid('selectRow', index).datagrid('beginEdit',
							index);
					editIndex1 = index;
				} else {
					$('#abroad-plan-dg').datagrid('selectRow', editIndex1);
				}
			}
		}else{
			alert("请先点击出访计划修改按钮！");
			return false;
		}
	}
	function append1() {
		if (endEditing1()) {
			$('#abroad-plan-dg').datagrid('appendRow', {});
			editIndex1 = $('#abroad-plan-dg').datagrid('getRows').length - 1;
			$('#abroad-plan-dg').datagrid('selectRow', editIndex1).datagrid('beginEdit',editIndex1);
		}
	}
	function removeit1() {
		if (editIndex1 == undefined) {
			return
		}
		$('#abroad-plan-dg').datagrid('cancelEdit', editIndex1).datagrid('deleteRow',
				editIndex1);
		editIndex1 = undefined;
	}
	function accept1() {
		if (endEditing1()) {
			$('#abroad-plan-dg').datagrid('acceptChanges');
		}
	}
	
	
	//计算时间
	function addHour(){
		var index=$('#abroad-plan-dg').datagrid('getRowIndex',$('#abroad-plan-dg').datagrid('getSelected'));
	    var editors = $('#abroad-plan-dg').datagrid('getEditors', index); 
	    var ed1 = editors[6]; 
	    var ed2 = editors[7]; 
	    var time1 =new Date(ed1.target.val());
	    var time2 =new Date(ed2.target.val());
	    if(!isNaN(time1) &&!isNaN(time2) ){
	   	 	var h = ((time2-time1)/3600000).toFixed(2);
	        editors[8].target.textbox('setValue', h);
	        }
	}
	
	function reload(rec){
		var row = $('#abroad-plan-dg').datagrid('getSelected');
		var rindex = $('#abroad-plan-dg').datagrid('getRowIndex', row); 
		var ed = $('#abroad-plan-dg').datagrid('getEditor',{
			index:rindex,
			field : 'vehicleLevel'  
		});
		if(rec.code !='JTGJ06'){
			var url = base+'/vehicle/comboboxJson?selected=${abroad.fVehicle}&parentCode='+rec.code;
	    	$(ed.target).combotree('reload', url);
		}
	}
	
	//选择国家和城市
	function selectAbroadDetail(index) {
		var win = creatFirstWin('选择-地区', 640, 580, 'icon-search', '/apply/chooseAbroad?index='+index);
		win.window('open');

	}
	
	//计算天数
	function setDaysPlan1(newValue,oldValue) {
		var index=$('#abroad-plan-dg').datagrid('getRowIndex',$('#abroad-plan-dg').datagrid('getSelected'));
	    var editors = $('#abroad-plan-dg').datagrid('getEditors', index);  
	    var day2 = editors[1]; 
	    var startday = Date.parse(new Date(newValue));
	    var endday = Date.parse(new Date(day2.target.val()));
	    
	    var maxTime =  Date.parse($("#maxTime").val())+86400000;
	    var minTime =  Date.parse($("#minTime").val())-28800000;
	    if(!isNaN(startday)&&!isNaN(endday)){
	    	if((startday>=minTime &&startday<=maxTime) && endday<=maxTime){
	    		if(endday<startday){
	        		alert("结束日期不能小于开始日期！");
	        		editors[0].target.datebox('setValue', '');
	        	}
	    	}else{
	    		alert("所选时间不在行程范围内请重新选择！");
	    		editors[0].target.datebox('setValue', '');
	    	}
	    	
	    }else{
	    	if(startday>maxTime || startday<minTime){
	    		alert("所选时间不在行程范围内请重新选择！");
	    		editors[0].target.datebox('setValue', '');
	    	}
	    }
		
	}
	
	//计算天数
	function setDaysPlan2(newValue,oldValue) {
		var index=$('#abroad-plan-dg').datagrid('getRowIndex',$('#abroad-plan-dg').datagrid('getSelected'));
	    var editors = $('#abroad-plan-dg').datagrid('getEditors', index);  
	    var day1 = editors[0]; 
	    var startday = Date.parse(new Date(day1.target.val()));
	    var endday = Date.parse(new Date(newValue));
	    
	    var maxTime = Date.parse(new Date($("#abroadDateEnd").datebox('getValue')));
	    var minTime = Date.parse(new Date($("#abroadDateStart").datebox('getValue')));
	    if(!isNaN(startday)&&!isNaN(endday)){
	    	if((startday>=minTime &&startday<=maxTime) && endday<=maxTime){
	    		if(endday<startday){
	        		alert("结束日期不能小于开始日期！");
	        		editors[1].target.datebox('setValue', '');
	        	}
	    	}else{
	    		alert("所选时间不在行程范围内请重新选择！");
	    		editors[1].target.datebox('setValue', '');
	    	}
	    	
	    }else{
	    	if(endday>maxTime || endday<minTime){
	    		alert("所选时间不在行程范围内请重新选择！");
	    		editors[1].target.datebox('setValue', '');
	    	}
	    }
	}
	
	//根据行程单更新伙食和公杂费信息
	function addMiscellaneousFeeAndFoodInfo(){
		accept1();
		var foodrows = $('#foodtab').datagrid('getRows');
		for(var i = foodrows.length-1 ; i >= 0 ; i--){
			$('#foodtab').datagrid('deleteRow',i);
		}
		var feeRows = $('#rec-fee-dg').datagrid('getRows');
		for(var i = feeRows.length-1 ; i >= 0 ; i--){
			$('#rec-fee-dg').datagrid('deleteRow',i);
		}
		var rows = $('#abroad-plan-dg').datagrid('getRows');
		var wayList = "";
		if(rows==''){
			return false;
		}else{
			wayList = JSON.stringify(rows);
		}
		var personNum = $("#fTeamPersonNum").numberbox('getValue');
		wayList =encodeURI(wayList);
		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/hotelStandard/getStandardAbroad?list='+wayList,
			success:function (data){
				for (var i = 0; i < data.length; i++) {
					$('#foodtab').datagrid('appendRow',{
						fAddress:data[i][0],
						standard:data[i][3],
						fDays:data[i][1],
						countStandard:parseFloat(data[i][3])*parseFloat(data[i][1])*parseInt(personNum),
						currency:data[i][5],
						fApplyAmount:'',
					});
					$('#rec-fee-dg').datagrid('appendRow',{
						fAddress:data[i][0],
						standard:data[i][4],
						fDays:data[i][2],
						countStandard:parseFloat(data[i][4])*parseFloat(data[i][2])*parseInt(personNum),
						currency:data[i][5],
						fApplyAmount:'',
					});
				}
				/* calcInCity();
				calcFoodCost(); */
			}
		});
	}
	
	
	function abroadPlanJson(){
		accept1();
		var rows1 = $('#abroad-plan-dg').datagrid('getRows');
		var abroadPlan = "";
		for (var i = 0; i < rows1.length; i++) {
			abroadPlan = abroadPlan + JSON.stringify(rows1[i]) + ",";
		}
		$('#abroadPlanJson').val(abroadPlan);
	}
</script>