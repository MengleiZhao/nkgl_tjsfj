<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<table id="reimburse_itinerary_tab_id" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	url: '${base}/reimburse/applyTravelPage?rId=${bean.rId}',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'trId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
				<th data-options="field:'travelDateStart',width:110,readonly:true,align:'center',editor:{type:'datebox', options:{onChange:setDays1,editable:false}}">出发日期</th>
				<th data-options="field:'travelDateEnd',width:110,align:'center',editor:{type:'datebox',options:{onChange:setDays2,editable:false}}">撤离日期/抵津日期</th>
				<th data-options="field:'travelAreaName',width:150,align:'center',editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     var row = $('#reimburse_itinerary_tab_id').datagrid('getSelected');
									     var index = $('#reimburse_itinerary_tab_id').datagrid('getRowIndex',row);
									     selectRDetail(index)
									     }}]}}">目的地</th>
				<th data-options="field:'travelAreaId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelAttendPeop',width:125,align:'center',editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     var row = $('#reimburse_itinerary_tab_id').datagrid('getSelected');
									     var index = $('#reimburse_itinerary_tab_id').datagrid('getRowIndex',row);
									     selectTravelAttendPeop(index)
									     }}]}}">人员（可多选）</th>
				<th data-options="field:'travelAttendPeopId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelPersonnelLevel',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'reason',width:175,align:'center',editor:{type:'textbox',options:{editable:true}}">主要工作内容</th>
			</tr>
		</thead>
	</table>
	
</div>
<script type="text/javascript">
var gId = '${applyBean.gId}';
var sign = 0;
if(gId!=''){
	$("#rAddId").hide();
	$("#rEditId").show();
	$("#rrmoveid").hide();
	$("#raddid").hide();
	$("#routsideRemoveitId").show();
	$("#routsideAppendId").show();
	$("#rhotelRemoveId").show();
	$("#rhotelAppendId").show();
	sign =1;
}

/* $(function(){
	rSaveItinerary();
	sign =1;
}); */
function rSaveItinerary(){
	sign =1;
	$("#rAddId").hide();
	$("#rEditId").show();
	$("#rrmoveid").hide();
	$("#raddid").hide();
	$("#routsideRemoveitId").show();
	$("#routsideAppendId").show();
	$("#rhotelRemoveId").show();
	$("#rhotelAppendId").show();
	//addOutInfo();
	addInCityInfo();
	addfoodInfo();
	//addHotelInfo();
}
function editItinerary(){
	sign = 0;
	$("#rAddId").show();
	$("#rEditId").hide();
	$("#rrmoveid").show();
	$("#raddid").show();
	$("#routsideRemoveitId").hide();
	$("#routsideAppendId").hide();
	$("#rhotelRemoveId").hide();
	$("#rhotelAppendId").hide();
}
function addOutInfo(){
	$('#reimburse_itinerary_tab_id').datagrid('acceptChanges');
	var itinerary = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	var outRows = $("#reimburse_outside_tab_id").datagrid('getRows');
	for(var i = outRows.length-1 ; i >= 0 ; i--){
		$('#reimburse_outside_tab_id').datagrid('deleteRow',i);
	}
	for (var i = 0; i < itinerary.length; i++) {
		$('#reimburse_outside_tab_id').datagrid('appendRow', {
			fStartDate:itinerary[i].travelDateStart,
			fEndDate:itinerary[i].travelDateEnd,
			vehicle:'',
			vehicleLevel:'',
			travelAttendPeop:itinerary[i].travelAttendPeop,
			travelAttendPeopId:itinerary[i].travelAttendPeopId,
			applyAmount:parseFloat(0.00),
		});
	}
	calcOutsideTrafficCost();
}


//根据行程单更新伙食补助信息
function addInCityInfo(){
	
	$('#reimburse_itinerary_tab_id').datagrid('acceptChanges');
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	var inCityRows = $('#reimbursein_city_tab_id').datagrid('getRows');
	for(var i = inCityRows.length-1 ; i >= 0 ; i--){
		$('#reimbursein_city_tab_id').datagrid('deleteRow',i);
	}
	for(var i = 0 ; i < rows.length ; i++){
		var days;//天数
		var day1 = Date.parse(new Date(rows[i].travelDateEnd));
		var day2 = Date.parse(new Date(rows[i].travelDateStart));
		days = parseInt((day1-day2)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
		if(!isNaN(day2)&&!isNaN(day1)){
			days+=1;
		}
		var ids = rows[i].travelAttendPeop;
		var idsarray = ids.split(',');
		days=isNaN(days)?0:days;
		var username = rows[i].travelAttendPeop;
		$('#reimbursein_city_tab_id').datagrid('appendRow',{
			fPerson:username,
			fSubsidyDay:days*parseFloat(idsarray.length),
			fApplyAmount:80*parseFloat(days)*parseFloat(idsarray.length),
		});
	}
	calcInCity();
}

//接待人员表格添加删除，保存方法
var editIndexItinerary = undefined;
function endEditingItinerary() {
	if (editIndexItinerary == undefined) {
		return true
	}
	if ($('#reimburse_itinerary_tab_id').datagrid('validateRow', editIndexItinerary)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#reimburse_itinerary_tab_id').datagrid('getEditors', editIndexItinerary);
		$('#reimburse_itinerary_tab_id').datagrid('endEdit', editIndexItinerary);
		userdata();
		editIndexItinerary = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowItinerary(index) {
	if(sign==0){
	if (editIndexItinerary != index) {
		if (endEditingItinerary()) {
			$('#reimburse_itinerary_tab_id').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndexItinerary = index;
			editItinerary();
		} else {
			$('#reimburse_itinerary_tab_id').datagrid('selectRow', editIndexItinerary);
		}
	}
	}else{
		alert("当前状态不能编辑！");
		return false;
	}
}
function rAppendItinerary() {
	if (endEditingItinerary()) {
		$('#reimburse_itinerary_tab_id').datagrid('appendRow', {
		});
		editIndexItinerary = $('#reimburse_itinerary_tab_id').datagrid('getRows').length - 1;
		$('#reimburse_itinerary_tab_id').datagrid('selectRow', editIndexItinerary).datagrid('beginEdit',editIndexItinerary);
	}
}
function rRemoveitItinerary() {
	if (editIndexItinerary == undefined) {
		return
	}
	$('#reimburse_itinerary_tab_id').datagrid('cancelEdit', editIndexItinerary).datagrid('deleteRow',
			editIndexItinerary);
	editIndexItinerary = undefined;
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
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
function acceptItinerary() {
	if (endEditingItinerary()) {
		$('#reimburse_itinerary_tab_id').datagrid('acceptChanges');
	}
}
	
	
//计算天数
function setDays1(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#reimburse_itinerary_tab_id').datagrid('getRowIndex',$('#reimburse_itinerary_tab_id').datagrid('getSelected'));
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
    var editors = $('#reimburse_itinerary_tab_id').datagrid('getEditors', index);  
    var day1 = editors[0]; 
    var day2 = editors[1]; 
    startday = new Date(day1.target.datebox('getValue'));
    endday = new Date(day2.target.datebox('getValue'));
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

function setDays2(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#reimburse_itinerary_tab_id').datagrid('getRowIndex',$('#reimburse_itinerary_tab_id').datagrid('getSelected'));
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
    var editors = $('#reimburse_itinerary_tab_id').datagrid('getEditors', index);  
    var day1 = editors[0]; 
    var day2 = editors[1]; 
    startday = new Date(day1.target.datebox('getValue'));
    endday = new Date(day2.target.datebox('getValue'));
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
    var editors = $('#reimburse_itinerary_tab_id').datagrid('getEditors', index);  
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

function reload(rec){
	var row = $('#reimburse_itinerary_tab_id').datagrid('getSelected');
	var rindex = $('#reimburse_itinerary_tab_id').datagrid('getRowIndex', row); 
	var ed = $('#reimburse_itinerary_tab_id').datagrid('getEditor',{
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

var userdatajson = '';

/**
 * 保存行程中人员信息
 */
function userdata(){
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	if(rows==''){
		return false;
	}else{
		userdatajson='';
		for (var i = 0; i < rows.length; i++) {
			userdatajson=userdatajson+'{label:\''+rows[i].travelAttendPeop+'\',value:\''+rows[i].travelAttendPeopid+'\'},';
		}
		userdatajson = userdatajson.substring(0,userdatajson.length-1);
		userdatajson='['+userdatajson+']';
		return true;
	}
}



function isineraryJson(){
	acceptItinerary();
	var rows2 = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	var travelPeop = "";
	if(rows2==''){
		return false;
	}else{
		for (var i = 0; i < rows2.length; i++) {
			travelPeop = travelPeop + JSON.stringify(rows2[i]) + ",";
		}
		$('#travelPeopJson').val(travelPeop);
		return true;
	}
}

//选择地址
function selectRDetail(index) {
	var win = creatFirstWin('选择-地区', 640, 580, 'icon-search', '/reimburse/choose?index='+index+'&type=travel');
	win.window('open');

}
//选择地址
function selectTravelAttendPeop(index) {
	var win = creatFirstWin('选择-人员', 640, 580, 'icon-search', '/reimburse/chooseUser?index='+index+'&editType=travel');
	win.window('open');

}
//根据行程单更新伙食补助信息
function addfoodInfo(){
	
	$('#reimburse_itinerary_tab_id').datagrid('acceptChanges');
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	var foodrows = $('#reimbursein_foodtab').datagrid('getRows');
	for(var i = foodrows.length-1 ; i >= 0 ; i--){
		$('#reimbursein_foodtab').datagrid('deleteRow',i);
	}
	for(var i = 0 ; i < rows.length ; i++){
		var days;//天数
		var day1 = Date.parse(new Date(rows[i].travelDateEnd));
		var day2 = Date.parse(new Date(rows[i].travelDateStart));
		days = parseInt((day1-day2)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
		if(!isNaN(day2)&&!isNaN(day1)){
			days+=1;
		}
		days=isNaN(days)?0:days;
		var username = rows[i].travelAttendPeop;
		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+rows[i].travelAreaId+'&travelDays='+days+'&hotelDays='+(days-1)+'&userId='+rows[i].travelAttendPeopId,
			success:function (data){
				var ids = rows[i].travelAttendPeop;
				var idsarray = ids.split(',');
				$('#reimbursein_foodtab').datagrid('appendRow',{
					fname:username,
					fDays:days*parseFloat(idsarray.length),
					fApplyAmount:parseFloat(data[1].standard)*parseFloat(idsarray.length),
				});
			}
		});
	}
	if('edit'!='${operation}'){
		$('#reimbursein_foodtab').datagrid('acceptChanges');
	}
	calcFoodCost();
}

function addHotelInfo(){
	$('#reimburse_itinerary_tab_id').datagrid('acceptChanges');
	var itinerary = $("#reimburse_itinerary_tab_id").datagrid('getRows');
	var outRows = $("#reimbursein_hoteltab").datagrid('getRows');
	for(var i = outRows.length-1 ; i >= 0 ; i--){
		$('#reimbursein_hoteltab').datagrid('deleteRow',i);
	}
	for (var i = 0; i < itinerary.length; i++) {
		var days;//天数
		
		console.log(itinerary[i].travelDateEnd);
		var day1 = Date.parse(new Date(itinerary[i].travelDateEnd));//结束时间
		var day2 = Date.parse(new Date(itinerary[i].travelDateStart));//开始时间
		days = parseInt((day1-day2)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
		if(!isNaN(day2)&&!isNaN(day1)){
			days+=1;
		}
		days=isNaN(days)?0:days;
		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+itinerary[i].travelAreaId+'&travelDays='+days+'&hotelDays='+(days-1)+'&userId='+itinerary[i].travelAttendPeopId+'&day1='+day1+'&day2='+day2,
			success:function (data){
				$('#reimbursein_hoteltab').datagrid('appendRow', {
					checkInTime:itinerary[i].travelDateStart,
					checkOUTTime:itinerary[i].travelDateEnd,
					CityId:itinerary[i].travelAreaId,
					locationCity:itinerary[i].travelAreaName,
					travelPersonnel:itinerary[i].travelAttendPeop,
					travelPersonnelId:itinerary[i].travelAttendPeopId,
					applyAmount:parseFloat(data[0].standard),
				});
			}
		});
	}
	calcHotelCost();
}
</script>