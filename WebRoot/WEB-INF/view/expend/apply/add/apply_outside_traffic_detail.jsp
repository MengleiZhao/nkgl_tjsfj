<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<table id="outside_traffic_tab_id_detail" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#outside_traffic_Id_detail',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/applyOutsideTrafficPage?gId=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRowOutsideTraffic,
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
				<th data-options="field:'fStartDate',width:110,align:'center',editor:{type:'datebox', options:{onChange:setDays3,editable:false}}">出发日期</th>
				<th data-options="field:'fEndDate',width:110,align:'center',editor:{type:'datebox',options:{onChange:setDays4,editable:false}}">到达日期</th>
				<th data-options="field:'travelPersonnel',width:142,align:'center',editor:{type:'textbox',options:{editable:true}}">出行人员</th>
				<th data-options="field:'vehicle',width:110,align:'center',editor:{type:'textbox',options:{editable:true}}">交通工具</th>
				<th data-options="field:'vehicleLevel',width:110,align:'center',editor:{type:'textbox',options:{editable:true}}">交通工具级别</th>
				<th data-options="field:'travelPersonnelId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelPersonnelLevel',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'applyAmount',width:100,align:'center',editor:{type:'numberbox',options:{editable:true,precision:2}}">申请费用</th>
			</tr>
		</thead>
	</table>
	<div id="outside_traffic_Id_detail" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">城市间交通费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<%-- <a href="javascript:void(0)" onclick="removeitOutsideTraffic()" hidden="hidden" id="outsideRemoveitId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendOutsideTraffic()" hidden="hidden" id="outsideAppendId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
	</div>
		<a style="float: right;color: #666666;font-size:12px;">申请金额：<span id=""><fmt:formatNumber groupingUsed="true" value="${bean.outsideAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="travelTotalDays"  />
	<input type="hidden" id="hotelTotalDays"  />
	<input type="hidden" id="outsideTrafficJson" name="outsideTraffic" />
</div>
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editIndexOutsideTraffic = undefined;
function endEditingOutsideTraffic() {
	if (editIndexOutsideTraffic == undefined) {
		return true;
	}
	if ($('#outside_traffic_tab_id_detail').datagrid('validateRow', editIndexOutsideTraffic)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#outside_traffic_tab_id_detail').datagrid('getEditors', editIndexOutsideTraffic);
		var text=tr[1].target.combotree('getText');
		if(text!='--请选择--'){
			tr[1].target.textbox('setValue',text);
		}
		var text1=tr[2].target.textbox('getText');
		if(text1!='--请选择--'){
			tr[2].target.textbox('setValue',text1);
		}
		$('#outside_traffic_tab_id_detail').datagrid('endEdit', editIndexOutsideTraffic);
		editIndexOutsideTraffic = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowOutsideTraffic(index) {
	if(sign==1){
	if (editIndexOutsideTraffic != index) {
		if (endEditingOutsideTraffic()) {
			$('#outside_traffic_tab_id_detail').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndexOutsideTraffic = index;
		} else {
			$('#outside_traffic_tab_id_detail').datagrid('selectRow', editIndexOutsideTraffic);
		}
	}
	}else{
		alert("不能编辑！");
		return false;
	}
}
function appendOutsideTraffic() {
	if (endEditingOutsideTraffic()) {
		$('#outside_traffic_tab_id_detail').datagrid('appendRow', {
			
			
			
			
		});
		editIndexOutsideTraffic = $('#outside_traffic_tab_id_detail').datagrid('getRows').length - 1;
		$('#outside_traffic_tab_id_detail').datagrid('selectRow', editIndexOutsideTraffic).datagrid('beginEdit',editIndexOutsideTraffic);
	}
}
function removeitOutsideTraffic() {
	if (editIndexOutsideTraffic == undefined) {
		return
	}
	$('#outside_traffic_tab_id_detail').datagrid('cancelEdit', editIndexOutsideTraffic).datagrid('deleteRow',
			editIndexOutsideTraffic);
	editIndexOutsideTraffic = undefined;
	var rows = $('#outside_traffic_tab_id_detail').datagrid('getRows');
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
function acceptOutsideTraffic() {
	if (endEditingOutsideTraffic()) {
		$('#outside_traffic_tab_id_detail').datagrid('acceptChanges');
	}
}
	
	
//计算天数
function setDays3(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#outside_traffic_tab_id_detail').datagrid('getRowIndex',$('#outside_traffic_tab_id_detail').datagrid('getSelected'));
	var rows = $('#outside_traffic_tab_id_detail').datagrid('getRows');
    var editors = $('#outside_traffic_tab_id_detail').datagrid('getEditors', index);  
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

function setDays4(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#outside_traffic_tab_id_detail').datagrid('getRowIndex',$('#outside_traffic_tab_id_detail').datagrid('getSelected'));
	var rows = $('#outside_traffic_tab_id_detail').datagrid('getRows');
    var editors = $('#outside_traffic_tab_id_detail').datagrid('getEditors', index);  
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
    var editors = $('#outside_traffic_tab_id_detail').datagrid('getEditors', index);  
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


function outsideTrafficJson(){
	acceptOutsideTraffic();
	var rows2 = $('#outside_traffic_tab_id_detail').datagrid('getRows');
	var outsideTraffic = "";
	if(rows2==''){
	}else{
		for (var i = 0; i < rows2.length; i++) {
			outsideTraffic = outsideTraffic + JSON.stringify(rows2[i]) + ",";
			}
		$('#outsideTrafficJson').val(outsideTraffic);
	}
}
</script>