<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-top:10px;padding-top: 0px;margin-left: 0px;">
		<div style="margin-right:0px;float:right">
			<a href="javascript:void(0)" onclick="editAbroadRime()" id="editAbroadIdReim" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a href="javascript:void(0)" onclick="addAbroadRime()" id="addAbroadIdReim" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="removeit1()" id="removeit1IdReim" hidden="hidden" style="float: right;margin-right: 22px;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="append1()" id="append1IdReim"  hidden="hidden" style="float: right; "><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</div>
	<table id="abroad-plan-dg-reim" class="easyui-datagrid" style="width:695px;height:auto;"
	data-options="
	toolbar: '#dmp',
	<c:if test="${!empty applyBean.gId}">
	url: '${base}/apply/abroadPlan?id=${applyBean.gId}',
	</c:if>
	<c:if test="${!empty abroadEdit.rId&&operation=='edit'}">
	url: '${base}/reimburse/abroadPlan?rId=${abroadEdit.rId}',
	</c:if>
	<c:if test="${empty applyBean.gId}">
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
			<th data-options="field:'abroadDate',required:'required',align:'center',width:140,editor:{type:'datebox',options:{onChange:setDaysPlan1}},formatter:ChangeDateFormat1">????????????</th>
			<th data-options="field:'timeEnd',required:'required',align:'center',width:140,editor:{type:'datebox',options:{onChange:setDaysPlan2}},formatter:ChangeDateFormat1">????????????/????????????</th>
			<th data-options="field:'arriveCountryId',editor:'textbox',hidden:true">?????????ID</th>
			<th data-options="field:'arriveCountry',required:'required',align:'center',width:130,editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     var row = $('#abroad-plan-dg-reim').datagrid('getSelected');
									     var index = $('#abroad-plan-dg-reim').datagrid('getRowIndex',row);
									     selectAbroadDetail(index)
									     }}]}}">?????????/??????</th>
			<th data-options="field:'arriveCity',required:'required',align:'center',width:130,editor:{type:'textbox',options:{editable:false}}">??????</th>
			<th data-options="field:'remark',required:'required',align:'center',width:125,editor:'textbox'">??????</th>
		</tr>
	</thead>
</table>
</div>
<script type="text/javascript">
var sign = 0;

var rId = '${abroadEdit.rId}';

if(rId!=''){
	$("#editAbroadIdReim").show();
	$("#addAbroadIdReim").hide();
	$("#removeit1IdReim").hide();
	$("#append1IdReim").hide();
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
function addAbroadRime(){
	var personNum = $("#fTeamPersonNum").numberbox('getValue');
	if(personNum==''){
		alert('?????????????????????!');
		return false;
	}
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
	$("#editAbroadIdReim").show();
	$("#addAbroadIdReim").hide();
	$("#removeit1IdReim").hide();
	$("#append1IdReim").hide();
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
	
	var international = $('#international_traveling_expense_id-reim').datagrid('getRows');
	for(var i = international.length-1 ; i >= 0 ; i--){
		$('#international_traveling_expense_id-reim').datagrid('deleteRow',i);
	}
	$('#applyTravelingExpense').html('0.00[???]');
	var outside = $('#apply_outside_traffic_tab_id-reim').datagrid('getRows');
	for(var i = outside.length-1 ; i >= 0 ; i--){
		$('#apply_outside_traffic_tab_id-reim').datagrid('deleteRow',i);
	}
	$('#applyOutsideTrafficAmount').html('0.00[???]');
	var hoteltab = $('#hoteltabApply-reim').datagrid('getRows');
	for(var i = hoteltab.length-1 ; i >= 0 ; i--){
		$('#hoteltabApply-reim').datagrid('deleteRow',i);
	}
	$('#hotelAmounts').html('0.00[???]');
	var foodrows = $('#foodtab-reim').datagrid('getRows');
	for(var i = foodrows.length-1 ; i >= 0 ; i--){
		$('#foodtab-reim').datagrid('deleteRow',i);
	}
	$('#applyFoodAmount').html('0.00[???]');
	var feeRows = $('#rec-fee-dg-reim').datagrid('getRows');
	for(var i = feeRows.length-1 ; i >= 0 ; i--){
		$('#rec-fee-dg-reim').datagrid('deleteRow',i);
	}
	$('#feeAmounts').html('0.00[???]');
	var fete = $('#rec-fete-dg-reim').datagrid('getRows');
	for(var i = fete.length-1 ; i >= 0 ; i--){
		$('#rec-fete-dg-reim').datagrid('deleteRow',i);
	}
	$('#feteCostAmounts').html('0.00[???]');
	var other = $('#rec-other-dg-reim').datagrid('getRows');
	for(var i = other.length-1 ; i >= 0 ; i--){
		$('#rec-other-dg-reim').datagrid('deleteRow',i);
	}
	$('#otherAmounts').html('0.00[???]');
	addMiscellaneousFeeAndFoodInfo();
}
function editAbroadRime(){
	sign = 0;
	$('#gName').textbox('readonly',false);
	$('#fTeamName').textbox('readonly',false);
	$('#fAbroadPlace').textbox('readonly',false);
	$('#fTeamLeader').textbox('readonly',false);
	$('#fTeamPersonNum').textbox('readonly',false);
	$('#abroadDateStart').textbox('readonly',false);
	$('#abroadDateEnd').textbox('readonly',false);
	$('#abroadDay').textbox('readonly',false);
	$('#fAbroadPeople').textbox('readonly',false);
	$('#fDiningPlaceId1').removeAttr("disabled");
	$('#fDiningPlaceId2').removeAttr("disabled");
	$('#fAbroadPeople').removeAttr("disabled");
	$("#chooseuserId").attr("onclick","chooseuser();");
	$("#editAbroadIdReim").hide();
	$("#addAbroadIdReim").show();
	$("#removeit1IdReim").show();
	$("#append1IdReim").show();
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
	acceptTravelingExpenseRime();
	acceptInternational();
	accepthotel();
	accept2();
}
//?????????????????????????????????????????????
	var editIndex1 = undefined;
	function endEditing1() {
		if (editIndex1 == undefined) {
			return true
		}
		if ($('#abroad-plan-dg-reim').datagrid('validateRow', editIndex1)) {
			//??????????????????????????????????????????????????????????????????????????????code
			/* var tr = $('#abroad-plan-dg-reim').datagrid('getEditors', editIndex1);
			var text=tr[3].target.textbox('getText');
			if(text!='--?????????--'){
				tr[3].target.textbox('setValue',text);
			} */
			$('#abroad-plan-dg-reim').datagrid('endEdit', editIndex1);
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
					$('#abroad-plan-dg-reim').datagrid('selectRow', index).datagrid('beginEdit',
							index);
					editIndex1 = index;
				} else {
					$('#abroad-plan-dg-reim').datagrid('selectRow', editIndex1);
				}
			}
		}else{
			alert("???????????????????????????????????????");
			return false;
		}
	}
	function append1() {
		if (endEditing1()) {
			$('#abroad-plan-dg-reim').datagrid('appendRow', {});
			editIndex1 = $('#abroad-plan-dg-reim').datagrid('getRows').length - 1;
			$('#abroad-plan-dg-reim').datagrid('selectRow', editIndex1).datagrid('beginEdit',editIndex1);
		}
	}
	function removeit1() {
		if (editIndex1 == undefined) {
			return
		}
		$('#abroad-plan-dg-reim').datagrid('cancelEdit', editIndex1).datagrid('deleteRow',
				editIndex1);
		editIndex1 = undefined;
	}
	function accept1() {
		if (endEditing1()) {
			$('#abroad-plan-dg-reim').datagrid('acceptChanges');
		}
	}
	
	
	//????????????
	function addHour(){
		var index=$('#abroad-plan-dg-reim').datagrid('getRowIndex',$('#abroad-plan-dg-reim').datagrid('getSelected'));
	    var editors = $('#abroad-plan-dg-reim').datagrid('getEditors', index); 
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
		var row = $('#abroad-plan-dg-reim').datagrid('getSelected');
		var rindex = $('#abroad-plan-dg-reim').datagrid('getRowIndex', row); 
		var ed = $('#abroad-plan-dg-reim').datagrid('getEditor',{
			index:rindex,
			field : 'vehicleLevel'  
		});
		if(rec.code !='JTGJ06'){
			var url = base+'/vehicle/comboboxJson?selected=${abroad.fVehicle}&parentCode='+rec.code;
	    	$(ed.target).combotree('reload', url);
		}
	}
	
	//?????????????????????
	function selectAbroadDetail(index) {
		var win = creatFirstWin('??????-??????', 640, 580, 'icon-search', '/apply/chooseAbroad?index='+index);
		win.window('open');

	}
	
	//????????????
	function setDaysPlan1(newValue,oldValue) {
		var index=$('#abroad-plan-dg-reim').datagrid('getRowIndex',$('#abroad-plan-dg-reim').datagrid('getSelected'));
	    var editors = $('#abroad-plan-dg-reim').datagrid('getEditors', index);  
	    var day2 = editors[1]; 
	    var startday = Date.parse(new Date(newValue));
	    var endday = Date.parse(new Date(day2.target.val()));
	    
	    var maxTime =  Date.parse($("#maxTime").val())+86400000;
	    var minTime =  Date.parse($("#minTime").val())-28800000;
	    if(!isNaN(startday)&&!isNaN(endday)){
	    	if((startday>=minTime &&startday<=maxTime) && endday<=maxTime){
	    		if(endday<startday){
	        		alert("???????????????????????????????????????");
	        		editors[0].target.datebox('setValue', '');
	        	}
	    	}else{
	    		alert("???????????????????????????????????????????????????");
	    		editors[0].target.datebox('setValue', '');
	    	}
	    	
	    }else{
	    	if(startday>maxTime || startday<minTime){
	    		alert("???????????????????????????????????????????????????");
	    		editors[0].target.datebox('setValue', '');
	    	}
	    }
		
	}
	
	//????????????
	function setDaysPlan2(newValue,oldValue) {
		var index=$('#abroad-plan-dg-reim').datagrid('getRowIndex',$('#abroad-plan-dg-reim').datagrid('getSelected'));
	    var editors = $('#abroad-plan-dg-reim').datagrid('getEditors', index);  
	    var day1 = editors[0]; 
	    var startday = Date.parse(new Date(day1.target.val()));
	    var endday = Date.parse(new Date(newValue));
	    
	    var maxTime = Date.parse(new Date($("#abroadDateEnd").datebox('getValue')));
	    var minTime = Date.parse(new Date($("#abroadDateStart").datebox('getValue')));
	    if(!isNaN(startday)&&!isNaN(endday)){
	    	if((startday>=minTime &&startday<=maxTime) && endday<=maxTime){
	    		if(endday<startday){
	        		alert("???????????????????????????????????????");
	        		editors[1].target.datebox('setValue', '');
	        	}
	    	}else{
	    		alert("???????????????????????????????????????????????????");
	    		editors[1].target.datebox('setValue', '');
	    	}
	    	
	    }else{
	    	if(endday>maxTime || endday<minTime){
	    		alert("???????????????????????????????????????????????????");
	    		editors[1].target.datebox('setValue', '');
	    	}
	    }
	}
	
	//?????????????????????????????????????????????
	function addMiscellaneousFeeAndFoodInfo(){
		accept1();
		var foodrows = $('#foodtab-reim').datagrid('getRows');
		for(var i = foodrows.length-1 ; i >= 0 ; i--){
			$('#foodtab-reim').datagrid('deleteRow',i);
		}
		var feeRows = $('#rec-fee-dg-reim').datagrid('getRows');
		for(var i = feeRows.length-1 ; i >= 0 ; i--){
			$('#rec-fee-dg-reim').datagrid('deleteRow',i);
		}
		var rows = $('#abroad-plan-dg-reim').datagrid('getRows');
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
					$('#foodtab-reim').datagrid('appendRow',{
						fAddress:data[i][0],
						standard:data[i][3],
						fDays:data[i][1],
						countStandard:parseFloat(data[i][3])*parseFloat(data[i][1])*parseInt(personNum),
						currency:data[i][5],
						fApplyAmount:'',
					});
					$('#rec-fee-dg-reim').datagrid('appendRow',{
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
				cx();
			}
		});
	}
	
	
	function abroadPlanJson(){
		accept1();
		var rows1 = $('#abroad-plan-dg-reim').datagrid('getRows');
		var abroadPlan = "";
		for (var i = 0; i < rows1.length; i++) {
			abroadPlan = abroadPlan + JSON.stringify(rows1[i]) + ",";
		}
		$('#abroadPlanJson').val(abroadPlan);
	}
</script>