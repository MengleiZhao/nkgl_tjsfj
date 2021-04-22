<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<table id="reimburse_outside_tab_id" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#reimburse_outside_traffic_Id',
	url: '${base}/reimburse/applyOutsideTrafficPage?rId=${bean.rId}',
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
				<th data-options="field:'fStartDate',width:110,align:'center',editor:{type:'datebox', options:{onChange:setDays3,editable:false}},formatter:ChangeDateFormat">出发日期</th>
				<th data-options="field:'fEndDate',width:110,align:'center',editor:{type:'datebox',options:{onChange:setDays4,editable:false}},formatter:ChangeDateFormat">到达日期</th>
				<th data-options="field:'vehicle',width:100,align:'center',editor:{
							editable:true,
							type:'combotree',
							filter: function(q, row){
									var opts = $(this).combotree('options');
									return row[opts.textField].indexOf(q) == 0;
								},
							options:{
								required:true,
								valueField:'code',
								textField:'text',
								method:'post',
								url:base+'/vehicle/comboboxJson?selected=JTGJ06',
								onSelect:reloadOut
							}}">交通工具</th>
				<th data-options="field:'vehicleLevel',width:130,align:'center',editor:{
							editable:true,
							type:'combotree',
							options:{
								required:true,
								valueField:'code',
								textField:'text',
								method:'post',
								url:base+'/vehicle/comboboxJson?selected=JTGJ06&parentCode=JTGJ0602',
							}}">交通工具级别</th>
				<th data-options="field:'travelPersonnel',width:120,align:'center',editor:{type:'textbox',options:{editable:false,icons:[{iconCls:'icon-add',handler: function(e){
									     var row = $('#reimburse_outside_tab_id').datagrid('getSelected');
									     var index = $('#reimburse_outside_tab_id').datagrid('getRowIndex',row);
									     selectTravelAttendPeopsReim(index)
									     }}]}}">出行人员</th>
				<th data-options="field:'travelPersonnelId',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'travelPersonnelLevel',hidden:true,editor:{type:'textbox',options:{editable:false}}"></th>
				<th data-options="field:'applyAmount',width:100,align:'center',editor:{type:'numberbox',options:{editable:true,precision:2,onChange:outAmounts}}">报销金额</th>
			</tr>
		</thead>
	</table>
	<div id="reimburse_outside_traffic_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">城市间交通费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">原申请金额：<span><fmt:formatNumber groupingUsed="true" value="${applyBean.outsideAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">报销金额：<span id="rOutsideTrafficAmount"><fmt:formatNumber groupingUsed="true" value="${bean.outsideAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</div>

</div>
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editIndexOutsideTraffic = undefined;
function endEditingOutsideTraffic() {
	if (editIndexOutsideTraffic == undefined) {
		return true;
	}
	if ($('#reimburse_outside_tab_id').datagrid('validateRow', editIndexOutsideTraffic)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#reimburse_outside_tab_id').datagrid('getEditors', editIndexOutsideTraffic);
		/* var text=tr[1].target.combotree('getText');
		if(text!='--请选择--'){
			tr[1].target.textbox('setValue',text);
		}
		var text1=tr[2].target.textbox('getText');
		if(text1!='--请选择--'){
			tr[2].target.textbox('setValue',text1);
		} */
		$('#reimburse_outside_tab_id').datagrid('endEdit', editIndexOutsideTraffic);
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
			$('#reimburse_outside_tab_id').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndexOutsideTraffic = index;
		} else {
			$('#reimburse_outside_tab_id').datagrid('selectRow', editIndexOutsideTraffic);
		}
	}
	}else{
		alert("不能编辑！");
		return false;
	}
}
function appendOutsideTraffic() {
	if (endEditingOutsideTraffic()) {
		$('#reimburse_outside_tab_id').datagrid('appendRow', {
			
			
			
			
		});
		editIndexOutsideTraffic = $('#reimburse_outside_tab_id').datagrid('getRows').length - 1;
		$('#reimburse_outside_tab_id').datagrid('selectRow', editIndexOutsideTraffic).datagrid('beginEdit',editIndexOutsideTraffic);
	}
}

function reloadOut(rec){
	var row = $('#reimburse_outside_tab_id').datagrid('getSelected');
	var rindex = $('#reimburse_outside_tab_id').datagrid('getRowIndex', row); 
	var ed = $('#reimburse_outside_tab_id').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleLevel'  
	});
		var url = base+'/vehicle/comboboxJson?selected=${travelBean.vehicle}&parentCode='+rec.code;
    	$(ed.target).combotree('reload', url);
	 /* $(ed.target).combobox('setValue', '2016'); */
}
function removeitOutsideTraffic() {
	if (editIndexOutsideTraffic == undefined) {
		return
	}
	$('#reimburse_outside_tab_id').datagrid('cancelEdit', editIndexOutsideTraffic).datagrid('deleteRow',
			editIndexOutsideTraffic);
	editIndexOutsideTraffic = undefined;
	var rows = $('#reimburse_outside_tab_id').datagrid('getRows');
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
		$('#reimburse_outside_tab_id').datagrid('acceptChanges');
	}
}
	
	
//计算天数
function setDays3(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#reimburse_outside_tab_id').datagrid('getRowIndex',$('#reimburse_outside_tab_id').datagrid('getSelected'));
	var rows = $('#reimburse_outside_tab_id').datagrid('getRows');
    var editors = $('#reimburse_outside_tab_id').datagrid('getEditors', index);  
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
	var index=$('#reimburse_outside_tab_id').datagrid('getRowIndex',$('#reimburse_outside_tab_id').datagrid('getSelected'));
	var rows = $('#reimburse_outside_tab_id').datagrid('getRows');
    var editors = $('#reimburse_outside_tab_id').datagrid('getEditors', index);  
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
    var editors = $('#reimburse_outside_tab_id').datagrid('getEditors', index);  
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
function calcOutsideTrafficCost(){
	//计算总额
	var rows = $('#reimburse_outside_tab_id').datagrid('getRows');
	var applyAmount=parseFloat(0.00);
	for(var i=0;i<rows.length;i++){
		var money = isNaN(parseFloat(rows[i].applyAmount))?0.00:parseFloat(rows[i].applyAmount);
		applyAmount=applyAmount+money;
	}
	$('#rOutsideTrafficAmount').html(applyAmount.toFixed(2)+'[元]');
}	

function outsideTrafficJson(){
	acceptOutsideTraffic();
	var rows2 = $('#reimburse_outside_tab_id').datagrid('getRows');
	var outsideTraffic = "";
	if(rows2==''){
	}else{
		for (var i = 0; i < rows2.length; i++) {
			outsideTraffic = outsideTraffic + JSON.stringify(rows2[i]) + ",";
		}
		$('#outsideTrafficJson').val(outsideTraffic);
	}
}

function outAmounts(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	var rows = $('#reimburse_outside_tab_id').datagrid('getRows');
	var index=$('#reimburse_outside_tab_id').datagrid('getRowIndex',$('#reimburse_outside_tab_id').datagrid('getSelected'));
    var num1 = 0;
    for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsOut(rows,i);
		}
	}
	$("#rOutsideTrafficAmount").html(num1.toFixed(2)+"[元]");
	$("#outsideAmount").val(num1);
	var cityAmount = $("#cityAmount").val();
	var hotelAmount = $("#hotelAmount").val();
	var foodAmount = $("#foodAmount").val();
	if(cityAmount=='NaN'||cityAmount==''||cityAmount==undefined||cityAmount==null){
		cityAmount=0;
	}
	if(hotelAmount=='NaN'||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
		hotelAmount=0;
	}
	if(foodAmount=='NaN'||foodAmount==''||foodAmount==undefined||foodAmount==null){
		foodAmount=0;
	}
	$("#rapplyTotalAmount").html((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2)+"元");
	$("#applyAmount").val((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2));
}

function addNumsOut(rows,index){
	var num=0;
	if(rows[index].applyAmount!=''&&rows[index].applyAmount!='NaN'&&rows[index].applyAmount!=undefined){
		num = parseFloat(rows[index].applyAmount);
	}else{
		num =0;
	}
	return num;
}
</script>