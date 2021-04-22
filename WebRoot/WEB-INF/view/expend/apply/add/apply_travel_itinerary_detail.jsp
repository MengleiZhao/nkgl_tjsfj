<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<table id="tracel_itinerary_tab_id_detail" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#itinerary_toolbar_Id_detail',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/applyTravelPage?gId=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRowItinerary,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'trId',hidden:true"></th>
				<th data-options="field:'gId',hidden:true"></th>
				<th data-options="field:'travelDateStart',width:110,align:'center',editor:{type:'datebox', options:{onChange:setDays1,editable:false}}">出发日期</th>
				<th data-options="field:'travelDateEnd',width:120,align:'center',editor:{type:'datebox',options:{onChange:setDays2,editable:false}}">撤离日期/抵津日期</th>
				<th data-options="field:'travelAreaName',width:150,align:'center',editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     var row = $('#tracel_itinerary_tab_id_detail').datagrid('getSelected');
									     var index = $('#tracel_itinerary_tab_id_detail').datagrid('getRowIndex',row);
									     selectRegionDetail(index)
									     }}]}}">目的地</th>
				<th data-options="field:'travelAreaId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelAttendPeop',width:137,align:'center',editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     var row = $('#tracel_itinerary_tab_id_detail').datagrid('getSelected');
									     var index = $('#tracel_itinerary_tab_id_detail').datagrid('getRowIndex',row);
									     selectTravelAttendPeop(index)
									     }}]}}">人员（可多选）</th>
				<th data-options="field:'travelAttendPeopId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelPersonnelLevel',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'reason',width:165,align:'center',editor:{type:'textbox',options:{editable:true}}">主要工作内容</th>
			</tr>
		</thead>
	</table>
		<c:if test="${empty detail}">
		<div id="itinerary_toolbar_Id_detail" style="height:30px;padding-top : 8px">
			<a href="javascript:void(0)" onclick="removeitItinerary()"  hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="appendItinerary()" hidden="hidden"  style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="editItinerary()" id="editId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a href="javascript:void(0)" onclick="addItinerary()" id="addId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</div>
	</c:if>
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="travelTotalDays"  />
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="travelPeopJson" name="travelPeop" />
</div>
<script type="text/javascript">
var gId = '${bean.gId}';
var sign = 0;

$(function(){
	addItinerary();
	sign =1;
});
function addItinerary(){
	//$('#tracel_itinerary_tab_id').datagrid('acceptChanges');
	sign =1;
	$("#addId").hide();
	$("#editId").show();
	$("#outsideRemoveitId").show();
	$("#outsideAppendId").show();
	$("#hotelRemoveId").show();
	$("#hotelAppendId").show();
}
function editItinerary(){
	sign = 0;
	$("#addId").show();
	$("#editId").hide();
	$("#outsideRemoveitId").hide();
	$("#outsideAppendId").hide();
	$("#hotelRemoveId").hide();
	$("#hotelAppendId").hide();
}
function addOutInfo(){
	$('#tracel_itinerary_tab_id').datagrid('acceptChanges');
	var itinerary = $("#tracel_itinerary_tab_id").datagrid('getRows');
	var outRows = $("#outside_traffic_tab_id").datagrid('getRows');
	for(var i = outRows.length-1 ; i >= 0 ; i--){
		$('#outside_traffic_tab_id').datagrid('deleteRow',i);
	}
	$("#outside_traffic_tab_id").datagrid('reload');
	for (var i = 0; i < itinerary.length; i++) {
		$('#outside_traffic_tab_id').datagrid('appendRow', {
			fStartDate:itinerary[i].travelDateStart,
			fEndDate:itinerary[i].travelDateEnd,
			vehicle:'',
			vehicleLevel:'',
			travelAttendPeop:itinerary[i].travelAttendPeop,
			travelAttendPeopId:itinerary[i].travelAttendPeopId,
			applyAmount:''
		});
	}
}

//接待人员表格添加删除，保存方法
var editIndexItinerary = undefined;
function endEditingItinerary() {
	if (editIndexItinerary == undefined) {
		return true
	}
	if ($('#tracel_itinerary_tab_id_detail').datagrid('validateRow', editIndexItinerary)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#tracel_itinerary_tab_id_detail').datagrid('getEditors', editIndexItinerary);
		$('#tracel_itinerary_tab_id_detail').datagrid('endEdit', editIndexItinerary);
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
			$('#tracel_itinerary_tab_id_detail').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndexItinerary = index;
			editItinerary();
		} else {
			$('#tracel_itinerary_tab_id_detail').datagrid('selectRow', editIndexItinerary);
		}
	}
	
	}else{
		alert("当前状态不能编辑！");
		return false;
	}
}
function appendItinerary() {
	if (endEditingItinerary()) {
		$('#tracel_itinerary_tab_id_detail').datagrid('appendRow', {
		});
		editIndexItinerary = $('#tracel_itinerary_tab_id_detail').datagrid('getRows').length - 1;
		$('#tracel_itinerary_tab_id_detail').datagrid('selectRow', editIndexItinerary).datagrid('beginEdit',editIndexItinerary);
	}
}
function removeitItinerary() {
	if (editIndexItinerary == undefined) {
		return
	}
	$('#tracel_itinerary_tab_id_detail').datagrid('cancelEdit', editIndexItinerary).datagrid('deleteRow',
			editIndexItinerary);
	editIndexItinerary = undefined;
	var rows = $('#tracel_itinerary_tab_id_detail').datagrid('getRows');
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
		$('#tracel_itinerary_tab_id_detail').datagrid('acceptChanges');
	}
}
	
	
//计算天数
function setDays1(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#tracel_itinerary_tab_id_detail').datagrid('getRowIndex',$('#tracel_itinerary_tab_id_detail').datagrid('getSelected'));
	var rows = $('#tracel_itinerary_tab_id_detail').datagrid('getRows');
    var editors = $('#tracel_itinerary_tab_id_detail').datagrid('getEditors', index);  
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

function setDays2(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#tracel_itinerary_tab_id_detail').datagrid('getRowIndex',$('#tracel_itinerary_tab_id_detail').datagrid('getSelected'));
	var rows = $('#tracel_itinerary_tab_id_detail').datagrid('getRows');
    var editors = $('#tracel_itinerary_tab_id_detail').datagrid('getEditors', index);  
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
    var editors = $('#tracel_itinerary_tab_id_detail').datagrid('getEditors', index);  
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
	var row = $('#tracel_itinerary_tab_id_detail').datagrid('getSelected');
	var rindex = $('#tracel_itinerary_tab_id_detail').datagrid('getRowIndex', row); 
	var ed = $('#tracel_itinerary_tab_id_detail').datagrid('getEditor',{
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
	var rows = $('#tracel_itinerary_tab_id_detail').datagrid('getRows');
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
	var rows2 = $('#tracel_itinerary_tab_id_detail').datagrid('getRows');
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
function selectRegionDetail(index) {
	var win = creatFirstWin('填写-支出明细', 640, 580, 'icon-search', '/apply/choose?index='+index+'&type=travel');
	win.window('open');

}
//选择地址
function selectTravelAttendPeop(index) {
	var win = creatFirstWin('填写-支出明细', 640, 580, 'icon-search', '/apply/chooseUser?index='+index+'&editType=travel');
	win.window('open');

}
//根据行程单更新伙食补助信息
function addfoodInfo(){
	
	$('#tracel_itinerary_tab_id_detail').datagrid('acceptChanges');
	var rows = $('#tracel_itinerary_tab_id_detail').datagrid('getRows');
	var foodrows = $('#foodtab').datagrid('getRows');
	for(var i = foodrows.length-1 ; i >= 0 ; i--){
		$('#foodtab').datagrid('deleteRow',i);
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
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+rows[i].travelAreaId+'&travelDays='+days+'&hotelDays='+(days-1),
			success:function (data){
				$('#foodtab').datagrid('appendRow',{
					fname:username,
					fDays:days,
					fApplyAmount:parseFloat(data[1].standard)*parseFloat(days),
				});
			}
		});
	}
	if('edit'!='${operation}'){
		$('#foodtab').datagrid('acceptChanges');
	}
	calcFoodCost();
}
</script>