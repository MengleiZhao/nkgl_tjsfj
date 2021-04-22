<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div id="dlgdivMap" class="easyui-dialog"
     style="width: 1280px; height: 590px; padding: 10px 20px" closed="true"
     buttons="#dlgdiv-buttons">
        <div id="divlargeMap"></div>
</div>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<table id="tracel_itinerary_trip_tab_id" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#itinerary_toolbar_trip_Id',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/applyTravelPage?gId=${bean.gId}&travelType=GWCX',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRowItineraryTrip,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	onLoadSuccess:maxTimeAndMinTimeTrip,
	">
		<thead>
			<tr>
				<th data-options="field:'trId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
			     <th data-options="field:'travelAttendPeop',width:130,align:'center',editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
			     var row = $('#tracel_itinerary_trip_tab_id').datagrid('getSelected');
			     var index = $('#tracel_itinerary_trip_tab_id').datagrid('getRowIndex',row);
			     selectTravelAttendPeopTrip(index)
			     }}]}}">人员（可多选）</th>
				<th data-options="field:'travelAttendPeopId',align:'center',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelAreaTime',width:110,align:'center',editor:{type:'datebox', options:{onChange:settravelAreaTime,editable:false}}">出行日期</th>
				<th data-options="field:'travelAreaName',width:158,align:'center',editor:{type:'textbox',options:{editable:true}}">目的地</th>
				<th data-options="field:'travelAreaId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options=" field:'areaNames',width:150,
			                        editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'code',
			                                textField:'text',
			                                editable:false,
			                                url:base+'/apply/lookupsJson?selected=CXQY&parentCode=CXQY',
			                                multiple: false,
			                                onHidePanel:setAreaId,
			                            }}">出行区域</th>
				<th data-options="field:'areaCode',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options=" field:'fDriveWay',width:150,
			                        editor:{
			                            type:'combobox',
			                            options:{
			                                valueField:'code',
			                                textField:'text',
			                                editable:false,
			                                url:base+'/apply/lookupsJson?selected=CCFS&parentCode=CCFS',
			                                multiple: false,
			                                onHidePanel:setfDriveWayCode,
			                            }}">乘车方式</th>
				<th data-options="field:'fDriveWayCode',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelPersonnelLevel',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'reason',width:165,editor:{type:'textbox',options:{editable:true}}">主要工作内容</th>
			</tr>
		</thead>
	</table>
	<c:if test="${empty detail}">
	<div id="itinerary_toolbar_trip_Id" style="height:30px;padding-top : 8px">
		<a href="javascript:void(0)" onclick="editItineraryTrip()" id="editId_trip" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a href="javascript:void(0)" onclick="addItineraryTrip()" id="addId_trip" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" id="removeId_trip" onclick="removeitItineraryTrip()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" id="appendId_trip" onclick="appendItineraryTrip()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<%-- <a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" id="" onclick="ylMap('${base}/resource-modality/${themenurl}/map/map.jpg')" style="float: right;"><img src="${base}/resource-modality/${themenurl}/map/ditu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
	</div>
	</c:if>
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="travelTotalDays"  />
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="travelPeopJson" name="travelPeop" />
</div>
<script type="text/javascript">

function addItineraryTrip(){
	acceptItineraryTrip();
	var rows = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
	if(rows==''){
		alert("请添加行程清单明细！");
		return false;
	}
	var travelType = $("#travelType").combobox('getValue');
	if(travelType==''){
		alert("请选择出差类型");
		return;
	}
	for(var i=0;i<rows.length;i++){
		if(rows[i].travelAttendPeop==''){
			alert("请选择出行人员信息！");
			return false;
		}
		if(rows[i].travelAreaTime==''){
			alert("请选择行程清单上的出行日期！");
			return false;
		}
		if(rows[i].travelAreaName==''){
			alert("请选择行程清单上的目的地！");
			return false;
		}
		if(rows[i].areaNames==''){
			alert("请选择行程清单上的出行区域！");
			return false;
		}
		if(rows[i].fDriveWay==''){
			alert("请选择行程清单上的乘车方式！");
			return false;
		}
		if(rows[i].reason==''){
			alert("请填写行程清单上的主要工作内容！");
			return false;
		}
	}
	
	$("#addId_trip").hide();
	$("#removeId_trip").hide();
	$("#appendId_trip").hide();
	$("#editId_trip").show();
	$("#hotelRemoveId_trip").show();
	$("#hotelAppendId_trip").show();
	$('#travelType').combobox('readonly',true);
/* 	$('#tracel_itinerary_trip_tab_id').datagrid('reload');
	$('#hoteltabApplyTrip').datagrid('reload'); */
	editIndexHotelTrip = undefined;
	var hoteltabRows = $('#hoteltabApplyTrip').datagrid('getRows');//住宿费
	for (var i = hoteltabRows.length-1; i >= 0 ; i--) {
		$('#hoteltabApplyTrip').datagrid('deleteRow',i);
	}
	$('#hotelAmountsTrip').html('0.00[元]');
	addInCityAndFoodInfoTrip();
	maxTimeAndMinTimeTrip();//获取最大最小时间
	$("#outsideAmount").val(0);
	$("#cityAmount").val(0);
	$("#hotelAmount").val(0);
	$("#foodAmount").val(0);
	var cityAmount = 0;
	var hotelAmount = 0;
	var foodAmount = 0;
	
	if(cityAmount=='NaN'||cityAmount==''||cityAmount==undefined||cityAmount==null){
		cityAmount=0;
	}
	if(hotelAmount=='NaN'||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
		hotelAmount=0;
	}
	if(foodAmount=='NaN'||foodAmount==''||foodAmount==undefined||foodAmount==null){
		foodAmount=0;
	}
	$("#applyTotalAmountTrip").html(listToFixed((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)))+"元");
	$("#applyAmount").val((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)).toFixed(2));
	sign =1;
}
function editItineraryTrip(){
	sign = 0;
	$("#addId_trip").show();
	$("#removeId_trip").show();
	$("#appendId_trip").show();
	$("#editId_trip").hide();
	$("#hotelRemoveId_trip").hide();
	$("#hotelAppendId_trip").hide();
	$('#travelType').combobox('readonly',false);
	accepthotelTrip();
	acceptfoodTrip();
	acceptInCityTrip();
}
function addOutInfo(){
	$('#tracel_itinerary_trip_tab_id').datagrid('acceptChanges');
	var itinerary = $("#tracel_itinerary_trip_tab_id").datagrid('getRows');
	var outRows = $("#outside_traffic_tab_id").datagrid('getRows');
	for(var i = outRows.length-1 ; i >= 0 ; i--){
		$('#outside_traffic_tab_id').datagrid('deleteRow',i);
	}
	for (var i = 0; i < itinerary.length; i++) {
		$('#outside_traffic_tab_id').datagrid('appendRow', {
			fStartDate:itinerary[i].travelDateStart,
			fEndDate:itinerary[i].travelDateEnd,
			vehicle:'',
			vehicleLevel:'',
			travelPersonnel:itinerary[i].travelAttendPeop,
			travelPersonnelId:itinerary[i].travelAttendPeopId,
			applyAmount:0.00
		});
	}
	calcOut();
}
function addHotelInfo(){
	$('#tracel_itinerary_trip_tab_id').datagrid('acceptChanges');
	var itinerary = $("#tracel_itinerary_trip_tab_id").datagrid('getRows');
	var outRows = $("#hoteltabApplyTrip").datagrid('getRows');
	for(var i = outRows.length-1 ; i >= 0 ; i--){
		$('#hoteltabApplyTrip').datagrid('deleteRow',i);
	}
	for (var i = 0; i < itinerary.length; i++) {
		//console.log(itinerary[i].travelAttendPeopId);
		var days;//天数
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
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+itinerary[i].travelAreaId+'&travelDays='+days+'&hotelDays='+(days-1)+'&userId='+itinerary[i].travelAttendPeopId+'&day1='+itinerary[i].travelDateStart+'&day2='+itinerary[i].travelDateEnd,
			success:function (data){
				$('#hoteltabApplyTrip').datagrid('appendRow', {
					checkInTime:itinerary[i].travelDateStart,
					checkOUTTime:itinerary[i].travelDateEnd,
					CityId:itinerary[i].travelAreaId,
					locationCity:itinerary[i].travelAreaName,
					travelPersonnel:itinerary[i].travelAttendPeop,
					travelPersonnelId:itinerary[i].travelAttendPeopId,
					applyAmount:parseFloat(data[0].standard)*(days-1),
				});
			}
		});
	}
	calcHotel();
}


//根据行程单更新伙食补助信息
function addInCityAndFoodInfoTrip(){
	var rows = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
	var travelPeop = "";
	if(rows==''){
		return false;
	}else{
		travelPeop = JSON.stringify(rows);
	}
	//travelPeop =encodeURI(travelPeop);
	$.ajax({
		type:'post',
		async:false,
		dataType:'json',
		data:{list:travelPeop},
		url:base+'/apply/getRepetition',
		success:function (data){
			if(data==1){
				$("#hiddenId").show();
				$("#fFoodNum").val(1);
			}else{
				$("#hiddenId").hide();
				$("#fFoodNum").val(0);
			}
			acceptItineraryTrip();
			editIndexTrip = undefined;
			var foodrows = $('#foodtabTrip').datagrid('getRows');
			for(var i = foodrows.length-1 ; i >= 0 ; i--){
				$('#foodtabTrip').datagrid('deleteRow',i);
			}
			$('#applyFoodAmountTrip').html('0.00[元]');
			editIndexInCityTrip = undefined;
			var inCityTabRows = $('#in_city_trip_tab_id').datagrid('getRows');
			for(var i = inCityTabRows.length-1 ; i >= 0 ; i--){
				$('#in_city_trip_tab_id').datagrid('deleteRow',i);
			}
			$('#applyInCityAmountTrip').html('0.00[元]');
			var new_arrs= new_arr_trip();
			for (var i = 0; i < new_arrs.length; i++) {
				
				$('#foodtabTrip').datagrid('appendRow',{
					fname:new_arrs[i].text,
					fDays:0,
					fApplyAmount:0,
					fnameId:new_arrs[i].id
				});
				$('#in_city_trip_tab_id').datagrid('appendRow',{
					fPerson:new_arrs[i].text,
					fAdjacentDay:0,
					fDistanceDay:0,
					fApplyAmount:0
				});
			}
		}
	});
}



//接待人员表格添加删除，保存方法
var editIndexItineraryTrip = undefined;
function endEditingItineraryTrip() {
	if (editIndexItineraryTrip == undefined) {
		return true
	}
	if ($('#tracel_itinerary_trip_tab_id').datagrid('validateRow', editIndexItineraryTrip)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#tracel_itinerary_trip_tab_id').datagrid('getEditors', editIndexItineraryTrip);
		var text5=tr[5].target.combobox('getText');
		if(text5!='--请选择--'){
			tr[5].target.combobox('setValues',text5);
		}
		var text7=tr[7].target.combobox('getText');
		if(text7!='--请选择--'){
			tr[7].target.combobox('setValues',text7);
		}
		$('#tracel_itinerary_trip_tab_id').datagrid('endEdit', editIndexItineraryTrip);
		userdata();
		editIndexItineraryTrip = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowItineraryTrip(index) {
	if(sign==0){
	if (editIndexItineraryTrip != index) {
		if (endEditingItineraryTrip()) {
			$('#tracel_itinerary_trip_tab_id').datagrid('selectRow', index).datagrid('beginEdit',index);
			
			editIndexItineraryTrip = index;
			editItineraryTrip();
		} else {
			$('#tracel_itinerary_trip_tab_id').datagrid('selectRow', editIndexItineraryTrip);
		}
	}
	
	}else{
		alert("请先点击行程清单修改按钮！");
		return false;
	}
}
function appendItineraryTrip() {
	if (endEditingItineraryTrip()) {
		$('#tracel_itinerary_trip_tab_id').datagrid('appendRow', {
		});
		editIndexItineraryTrip = $('#tracel_itinerary_trip_tab_id').datagrid('getRows').length - 1;
		$('#tracel_itinerary_trip_tab_id').datagrid('selectRow', editIndexItineraryTrip).datagrid('beginEdit',editIndexItineraryTrip);
	}
}
function removeitItineraryTrip() {
	if (editIndexItineraryTrip == undefined) {
		return
	}
	$('#tracel_itinerary_trip_tab_id').datagrid('cancelEdit', editIndexItineraryTrip).datagrid('deleteRow',
			editIndexItineraryTrip);
	editIndexItineraryTrip = undefined;
	var rows = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
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
function acceptItineraryTrip() {
	if (endEditingItineraryTrip()) {
		$('#tracel_itinerary_trip_tab_id').datagrid('acceptChanges');
	}
}
	
	
//计算天数
function settravelAreaTime(newValue,oldValue) {
	var index=$('#tracel_itinerary_trip_tab_id').datagrid('getRowIndex',$('#tracel_itinerary_trip_tab_id').datagrid('getSelected'));
    var editors = $('#tracel_itinerary_trip_tab_id').datagrid('getEditors', index);  
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
    var editors = $('#tracel_itinerary_trip_tab_id').datagrid('getEditors', index);  
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
	var row = $('#tracel_itinerary_trip_tab_id').datagrid('getSelected');
	var rindex = $('#tracel_itinerary_trip_tab_id').datagrid('getRowIndex', row); 
	var ed = $('#tracel_itinerary_trip_tab_id').datagrid('getEditor',{
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
	var rows = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
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



function isineraryJsonTrip(){
	acceptItineraryTrip();
	var rows2 = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
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
function selectRegionDetailTrip(index) {
	var win = creatFirstWin('选择-地区', 640, 580, 'icon-search', '/apply/choose?index='+index+'&type=travel&tabId=tracel_itinerary_trip_tab_id');
	win.window('open');

}
//选择地址
function selectTravelAttendPeopTrip(index) {
	var win = creatFirstWin('选择-人员', 640, 580, 'icon-search', '/apply/chooseUser?index='+index+'&editType=travel&tabId=tracel_itinerary_trip_tab_id');
	win.window('open');

}
var sign = 0;
//获取行程单里面的最早时间和最晚时间
function maxTimeAndMinTimeTrip(){
	var arr = [];
	var rows = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
	
	for (var i = 0; i < rows.length; i++) {
		var travelAreaTime = Date.parse(new Date(rows[i].travelAreaTime));
		arr.push(travelAreaTime);
	}
	var maxTime = Math.max.apply(null, arr);
	var minTime = Math.min.apply(null, arr);
	$("#maxTime").val(maxTime+86400000);
	$("#minTime").val(minTime);
	var gId = '${bean.gId}';
	if(gId!=''){
		$("#addId_trip").hide();
		$("#removeId_trip").hide();
		$("#appendId_trip").hide();
		$("#editId_trip").show();
		$("#hotelRemoveId_trip").show();
		$("#hotelAppendId_trip").show();
		$('#travelType').combobox('readonly',true);
		sign =1;
		//applyTotalAmount();
	}
}


//选中时给出行区域设置id
function setAreaId(){
	var index=$('#tracel_itinerary_trip_tab_id').datagrid('getRowIndex',$('#tracel_itinerary_trip_tab_id').datagrid('getSelected'));
	var areaCode = $('#tracel_itinerary_trip_tab_id').datagrid('getEditor',{
		index:index,
		field:'areaCode'
	});
	var areaNames = $('#tracel_itinerary_trip_tab_id').datagrid('getEditor',{
		index:index,
		field:'areaNames'
	});
	$(areaCode.target).textbox('setValue', $(areaNames.target).combobox('getValues'));
}
//选中时给出行方式设置id
function setfDriveWayCode(){
	var index=$('#tracel_itinerary_trip_tab_id').datagrid('getRowIndex',$('#tracel_itinerary_trip_tab_id').datagrid('getSelected'));
	var fDriveWayCode = $('#tracel_itinerary_trip_tab_id').datagrid('getEditor',{
		index:index,
		field:'fDriveWayCode'
	});
	var fDriveWay = $('#tracel_itinerary_trip_tab_id').datagrid('getEditor',{
		index:index,
		field:'fDriveWay'
	});
	$(fDriveWayCode.target).textbox('setValue', $(fDriveWay.target).combobox('getValues'));
}



function new_arr_trip(){
	var rows = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
	var arrs = new Array();
	for (var i = 0; i < rows.length; i++) {
	var PeopId = rows[i].travelAttendPeopId.split(',');
	var PeopName = rows[i].travelAttendPeop.split(',');
	if(PeopId.length>1){
		for (var j = 0; j < PeopId.length; j++) {
			var idAndName = {};
			idAndName.id = PeopId[j];
			idAndName.text = PeopName[j];
			arrs.push(idAndName);
		}
	}else{
		var idAndName = {};
		idAndName.id = rows[i].travelAttendPeopId;
		idAndName.text = rows[i].travelAttendPeop;
		arrs.push(idAndName);
	}
	}
	for (var h = 0; h < arrs.length; h++) {
		for (var c =h+1; c <arrs.length; ) {
		    if (arrs[h].id == arrs[c].id ) {//通过id属性进行匹配；
		    	arrs.splice(c, 1);//去除重复的对象；
			}else {
			c++;
			}
		}
		}
	return arrs;
}

</script>