<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<table id="reimburse_itinerary_tab_id" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#reimburse_itinerary_toolbar_Id',
	<c:if test="${!empty applyBean.gId&&operation=='add'}">
	url: '${base}/apply/applyTravelPage?gId=${applyBean.gId}&travelType=GWCC',
	</c:if>
	<c:if test="${!empty bean.rId&&operation=='edit'}">
	url: '${base}/reimburse/applyTravelPage?rId=${bean.rId}&travelType=GWCC',
	</c:if>
	method: 'post',
	<c:if test="${!empty operation}">
	onClickRow: onClickRowItinerary,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	onLoadSuccess:maxTimeAndMinTimeReim,
	">
		<thead>
			<tr>
				<th data-options="field:'trId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
				<th data-options="field:'travelDateStart',width:110,align:'center',editor:{type:'datebox', options:{onChange:setDays1,editable:false}}">出发日期</th>
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
	<c:if test="${!empty operation}">
	<div id="reimburse_itinerary_toolbar_Id" style="height:30px;padding-top : 8px">
		<a href="javascript:void(0)" onclick="editItinerary()" id="rEditId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a href="javascript:void(0)" onclick="rSaveItinerary()" id="rAddId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="rRemoveitItinerary()" id="rrmoveid" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="rAppendItinerary()" id="raddid" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	</c:if>
	
</div>
<script type="text/javascript">
var gId = '${applyBean.gId}';
var sign = 0;
if(gId!=''){
	
	$("#rAddId").hide();
	$("#rEditId").hide();
	$("#rrmoveid").hide();
	$("#raddid").hide();
	$("#routsideRemoveitId").hide();
	$("#routsideAppendId").hide();
	$("#rhotelRemoveId").hide();
	$("#rhotelAppendId").hide();
	$("#hotelRemoveId_trip_reim").hide();
	$("#hotelAppendId_trip_reim").hide();
	
	sign =1;
}

/* $(function(){
	rSaveItinerary();
	sign =1;
}); */
function rSaveItinerary(){
	acceptItinerary();
	sign =1;
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	if(rows==''){
		alert("请添加行程清单明细！");
		return false;
	}
	for(var i=0;i<rows.length;i++){
		if(rows[i].travelDateStart==''){
			alert("请填写行程清单上的出发日期！");
			return false;
		}
		if(rows[i].travelDateEnd==''){
			alert("请填写行程清单上的撤离日期/抵津日期！");
			return false;
		}
		if(rows[i].travelAreaName==''){
			alert("请填写行程清单上的目的地！");
			return false;
		}
		if(rows[i].travelAttendPeop==''){
			alert("请填写行程清单上的人员信息！");
			return false;
		}
		if(rows[i].reason==''){
			alert("请填写行程清单上的主要工作内容！");
			return false;
		}
	}
	$("#rAddId").hide();
	$("#rEditId").show();
	$("#rrmoveid").hide();
	$("#raddid").hide();
	$("#routsideAppendId").show();
	$("#routsideRemoveitId").show();
	$("#rhotelRemoveId").show();
	$("#rhotelAppendId").show();
	
	var hoteltabRows = $('#reimbursein_hoteltab').datagrid('getRows');//住宿费
	for (var i = hoteltabRows.length-1; i >= 0 ; i--) {
		$('#reimbursein_hoteltab').datagrid('deleteRow',i);
	}
	editIndex = undefined;
	$('#rhotelAmount').html('0.00[元]');
	var outsideTrafficRows = $('#reimburse_outside_tab_id').datagrid('getRows');//城市间交通费
	for (var i = outsideTrafficRows.length-1; i >= 0 ; i--) {
		$('#reimburse_outside_tab_id').datagrid('deleteRow',i);
	}
	editIndexOutsideTraffic = undefined;
	$('#rOutsideTrafficAmount').html('0.00[元]');
	addInCityAndFoodInfoReimburse();
	maxTimeAndMinTimeReim();
	var outsideAmount = 0.00;
	var cityAmount = $("#cityAmount").val();
	var hotelAmount = 0.00;
	var foodAmount = $("#foodAmount").val();
	if(isNaN(outsideAmount)||outsideAmount==''||outsideAmount==undefined||outsideAmount==null){
		outsideAmount=0;
	}
	if(isNaN(cityAmount) ||cityAmount==''||cityAmount==undefined||cityAmount==null){
		cityAmount=0;
	}
	if(isNaN(hotelAmount) ||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
		hotelAmount=0;
	}
	if(isNaN(foodAmount) ||foodAmount==''||foodAmount==undefined||foodAmount==null){
		foodAmount=0;
	}
	$("#rapplyTotalAmount").html(listToFixed((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount)))+"元");
	$("#p_amount").html(listToFixed((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount)))+"元");
	$("#reimburseAmount").val((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount).toFixed(2)));
	cx();

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
	accepthotel();
	acceptOutsideTraffic();
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
				$('#reimburse_itinerary_tab_id').datagrid('selectRow', index).datagrid('beginEdit',index);
				editIndexItinerary = index;
				editItinerary();
			} else {
				$('#reimburse_itinerary_tab_id').datagrid('selectRow', editIndexItinerary);
			}
		}
	}else{
		/* alert("请先点击行程清单修改按钮！"); */
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


//根据行程单更新伙食补助信息
function addInCityAndFoodInfoReimburse(){
	var travleType ='${applyBean.travelType}';
	acceptItinerary();
	var foodrows = $('#reimbursein_foodtab').datagrid('getRows');
	for(var i = foodrows.length-1 ; i >= 0 ; i--){
		$('#reimbursein_foodtab').datagrid('deleteRow',i);
	}
	var inCityTabRows = $('#reimbursein_city_tab_id').datagrid('getRows');
	for(var i = inCityTabRows.length-1 ; i >= 0 ; i--){
		$('#reimbursein_city_tab_id').datagrid('deleteRow',i);
	}
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	var travelPeop = "";
	if(rows==''){
		return false;
	}else{
		travelPeop = JSON.stringify(rows);
	}
	travelPeop =encodeURI(travelPeop);
	$.ajax({
		type:'post',
		async:false,
		dataType:'json',
		url:base+'/hotelStandard/getStandard?list='+travelPeop,
		success:function (data){
			for (var i = 0; i < data.length; i++) {
				if(travleType=='HWCXXLQ'){
					$('#reimbursein_foodtab').datagrid('appendRow',{
						fname:data[i][0],
						fDays:data[i][1],
						fApplyAmount:parseFloat(50)*parseFloat(data[i][1]),
					});
					$('#reimbursein_city_tab_id').datagrid('appendRow',{
						fPerson:data[i][0],
						fSubsidyDay:data[i][1],
						fApplyAmount:parseInt(50)*parseFloat(data[i][1]),
					});
				}else{
					if(travleType=='HWCXXGQ'){
						$('#reimbursein_foodtab').datagrid('appendRow',{
							fname:data[i][0],
							fDays:data[i][1],
							fApplyAmount:parseFloat(50)*parseFloat(data[i][1]),
						});
						
						$('#reimbursein_city_tab_id').datagrid('appendRow',{
							fPerson:data[i][0],
							fSubsidyDay:data[i][1],
							fApplyAmount:parseInt(100)*parseFloat(data[i][1]),
						});
						
					}else{
						$('#reimbursein_foodtab').datagrid('appendRow',{
							fname:data[i][0],
							fDays:data[i][1],
							fApplyAmount:parseFloat(data[i][3]),
						});
						
						$('#reimbursein_city_tab_id').datagrid('appendRow',{
							fPerson:data[i][0],
							fSubsidyDay:data[i][1],
							fApplyAmount:parseInt(data[i][2]),
						});
					}
				}
			}
			calcInCity();
			calcFoodCost();
		}
	});
}

//用于删除汇总报销金额
function huizong(){
	var outsideAmount = $("#outsideAmount").val();
	var cityAmount = $("#cityAmount").val();
	var hotelAmount = $("#hotelAmount").val();
	var foodAmount = $("#foodAmount").val();
	if(isNaN(outsideAmount)||outsideAmount==''||outsideAmount==undefined||outsideAmount==null){
		outsideAmount=0;
	}
	if(isNaN(cityAmount)||cityAmount==''||cityAmount==undefined||cityAmount==null){
		cityAmount=0;
	}
	if(isNaN(hotelAmount)||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
		hotelAmount=0;
	}
	if(isNaN(foodAmount)||foodAmount==''||foodAmount==undefined||foodAmount==null){
		foodAmount=0;
	}
	$("#rapplyTotalAmount").html((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount)).toFixed(2)+"元");
	$("#p_amount").html((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount)).toFixed(2)+"元");
	$("#reimburseAmount").val((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount)).toFixed(2));
}

//获取行程单里面的最早时间和最晚时间
function maxTimeAndMinTimeReim(){
	
	var arr = [];
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		var dayStart = Date.parse(new Date(rows[i].travelDateStart));
		var dayEnd = Date.parse(new Date(rows[i].travelDateEnd));
		arr.push(dayStart);
		arr.push(dayEnd);
	}
	
	var maxTime = Math.max.apply(null, arr);
	var minTime = Math.min.apply(null, arr);
	$("#maxTime").val(maxTime);
	$("#minTime").val(minTime);
}





</script>