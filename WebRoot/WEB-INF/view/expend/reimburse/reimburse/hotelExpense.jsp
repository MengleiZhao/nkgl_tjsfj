<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px;border-top: 1px solid rgba(217,227,231,1);">

	<table id="reimbursein_hoteltab" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#reimburse_hoteltool',
	<c:if test="${!empty bean.rId&&operation=='edit'}">
	url: '${base}/reimburse/hotelJson?rId=${bean.rId}&travelType=GWCC',
	</c:if>
	<c:if test="${!empty applyBean.gId&&operation=='add'}">
	url: '${base}/apply/hotelJson?gId=${applyBean.gId}&travelType=GWCC',
	</c:if>
	<c:if test="${empty applyBean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${!empty operation}">
	onClickRow: onClickRowhotel,
	</c:if>
	striped : true,
	nowrap : true,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'ffId',hidden:true"></th>
				<th data-options="field:'checkInTime',align:'center',editor:{type:'datebox', options:{editable:false,onChange:hotelStartTime}},formatter:ChangeDateFormat"width="18%">入住时间</th>
				<th data-options="field:'checkOUTTime',align:'center',editor:{type:'datebox',options:{editable:false,onChange:hotelEndTime}},formatter:ChangeDateFormat"width="18%">退房时间</th>
				<th data-options="field:'cityId',align:'center',hidden:true,editor:{type:'textbox',options:{editable:false,onChange:hotelCity}}">城市id</th>
				<th data-options="field:'travelPersonnelId',hidden:true,editor:{type:'textbox',options:{editable:false,onChange:hotelAtandard}}">住宿人员id</th>
				<th data-options="field:'locationCity',align:'center',editor:{
                            type:'combobox',
                            options:{
                                valueField:'id',
                                textField:'text',
                                multiple: true,
                                onHidePanel:cityHotelId,
                                onShowPanel:clickCityName,
                            }}" width="20%">所在城市</th>
				<th data-options="field:'travelPersonnel',
                        editor:{
                            type:'combobox',
                            options:{
                                valueField:'id',
                                textField:'text',
                                multiple: true,
                                onHidePanel:personnerHotelIdReim,
                                onShowPanel:clickPersonnelReim,
                            }}" width="33%">住宿人员</th>
				<th data-options="field:'applyAmount',align:'center',editor:{type:'numberbox',options:{precision:2,onChange:hotelAmounts}}" width="12%">报销金额</th>
			</tr>
		</thead>
	</table>
	<c:if test="${!empty operation}">
	<div id="reimburse_hoteltool" style="height:20px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">住宿费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">原申请金额：<span ><fmt:formatNumber groupingUsed="true" value="${applyBean.hotelAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<c:if test="${operation=='add'}">
		<a style="float: left;color: #666666;font-size:12px;">报销金额：<span id="rhotelAmount"><fmt:formatNumber groupingUsed="true" value="${applyBean.hotelAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		</c:if>
		<c:if test="${operation=='edit'}">
		<a style="float: left;color: #666666;font-size:12px;">报销金额：<span id="rhotelAmount"><fmt:formatNumber groupingUsed="true" value="${bean.hotelAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		</c:if>
		<a href="javascript:void(0)" onclick="removehotel()" id="rhotelRemoveId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendhotel()" id="rhotelAppendId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	</c:if>

</div>
<script type="text/javascript">

//伙食表格添加删除
var editIndex = undefined;
function endEditinghotel() {
	if (editIndex == undefined) {
		return true;
	}
	if ($('#reimbursein_hoteltab').datagrid('validateRow', editIndex)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#reimbursein_hoteltab').datagrid('getEditors', editIndex);
		var text=tr[4].target.combobox('getText');
		if(text!='--请选择--'){
			tr[4].target.combobox('setValue',text);
		}
		var text=tr[5].target.combobox('getText');
		if(text!='--请选择--'){
			tr[5].target.combobox('setValue',text);
		}
		$('#reimbursein_hoteltab').datagrid('endEdit', editIndex);
		calcHotelCost();
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowhotel(index) {
	if(sign==1){
	if (editIndex != index) {
		if (endEditinghotel()) {
			$('#reimbursein_hoteltab').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndex = index;
		} else {
			$('#reimbursein_hoteltab').datagrid('selectRow', editIndex);
		}
	}
	}else{
		alert("请先保存行程清单！");
		return false;
	}
}
function appendhotel() {
	if (endEditinghotel()) {
		$('#reimbursein_hoteltab').datagrid('appendRow', {});
		editIndex = $('#reimbursein_hoteltab').datagrid('getRows').length - 1;
		$('#reimbursein_hoteltab').datagrid('selectRow', editIndex).datagrid('beginEdit',editIndex);
		var new_arrs= new_arr_hotel();
		var travelPersonnel = $('#reimbursein_hoteltab').datagrid('getEditor',{
			index:editIndex,
			field:'travelPersonnel'
		});
		$(travelPersonnel.target).combobox({
            data: new_arrs,
            valueField: 'id',
            textField: 'text',
		});
	}
}
function removehotel() {
	if (editIndex == undefined) {
		return
	}
	$('#reimbursein_hoteltab').datagrid('cancelEdit', editIndex).datagrid('deleteRow',editIndex);
	editIndex = undefined;
	calcHotelCost();
	huizong();

	var cxjk = $('input[name="withLoan"]:checked').val();
	if(cxjk==1){
		var lCode = $("#input_jkdbh").textbox('getValue');
		if(lCode!=''){
			cx();
		}
	}
}
function accepthotel() {
	if (endEditinghotel()) {
		$('#reimbursein_hoteltab').datagrid('acceptChanges');
		calcHotelCost();
	}
}
//获得json数据
function getHotelJson(){
	accepthotel();
	$('#reimbursein_hoteltab').datagrid('acceptChanges');
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
	var entities= '';
	for(var i = 0 ;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	$("#hotelJson").val(entities);
}	
function calcHotelCost(){
	//计算总额
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
	var HotelAmount=parseFloat(0.00);
	for(var i=0;i<rows.length;i++){
		var money = isNaN(parseFloat(rows[i].applyAmount))?0.00:parseFloat(rows[i].applyAmount);
		HotelAmount=HotelAmount+money;
	}
	$('#rhotelAmount').html(HotelAmount.toFixed(2)+'[元]');
	$('#hotelAmount').html(HotelAmount.toFixed(2));
}	
/* 	//重新计算开支标准
	function countkzbz() {
		accept();
		//获得接待人数（有几行就是接待多少人）
		var rownum = $('#reimbursein_hoteltab').datagrid('getRows').length;
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
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
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
		calcHotelCost();
	}
	
}

function setDays2(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
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
		calcHotelCost();
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
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
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
	var row = $('#reimbursein_hoteltab').datagrid('getSelected');
	var rindex = $('#reimbursein_hoteltab').datagrid('getRowIndex', row); 
	var ed = $('#reimbursein_hoteltab').datagrid('getEditor',{
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

//选择出差地区
function chooseArea(index,editType){
	var win = creatFirstWin('选择-出差地区', 640, 580, 'icon-search', '/hotelStandard/choose?index='+index+'&editType=4&tabId=reimbursein_hoteltab');
	win.window('open');
}
//选择人员
function chooseUser(index,editType){
	var win = creatFirstWin('选择-出差人员', 640, 580, 'icon-search', '/hotelStandard/chooseUser?index='+index+'&editType=4&tabId=reimbursein_hoteltab');
	win.window('open');
}


function hotelAmounts(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsHotel(rows,i);
		}
	}
		$("#rhotelAmount").html(num1.toFixed(2)+"[元]");
		$("#hotelAmount").val(num1.toFixed(2));
		var cityAmount = $("#cityAmount").val();
		var outsideAmount = $("#outsideAmount").val();
		var foodAmount = $("#foodAmount").val();
		if(cityAmount=='NaN'||cityAmount==''||cityAmount==undefined||cityAmount==null){
			cityAmount=0;
		}
		if(outsideAmount=='NaN'||outsideAmount==''||outsideAmount==undefined||outsideAmount==null){
			outsideAmount=0;
		}
		if(foodAmount=='NaN'||foodAmount==''||foodAmount==undefined||foodAmount==null){
			foodAmount=0;
		}
		$("#rapplyTotalAmount").html((parseFloat(foodAmount)+parseFloat(outsideAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2)+"元");
		$("#p_amount").html((parseFloat(foodAmount)+parseFloat(outsideAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2)+"元");
		$("#reimburseAmount").val((parseFloat(foodAmount)+parseFloat(outsideAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2));
		cx();
}

function addNumsHotel(rows,index){
	var num=0;
	if(rows[index].applyAmount!=''&&rows[index].applyAmount!='NaN'&&rows[index].applyAmount!=undefined){
		num = parseFloat(rows[index].applyAmount);
	}else{
		num =0;
	}
	return num;
}




function hotelAtandard(newVal,oldVal){
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
	
    var day0 = editors[0]; //入住时间
    var day1 = editors[1]; //退房时间
    var city = editors[2].target[0].value; //所在城市
    var userId = newVal; //出行人员
    var startday = Date.parse(new Date(day0.target[0].value));//入住时间
    var endday = Date.parse(new Date(day1.target[0].value));//退房时间
    if(startday==''||endday==''||isNaN(startday) ||isNaN(endday)){
    	//alert("请选择入住时间和退房时间！");
    	return false;
    }
    if(city==''||userId==''){
    	//alert("请选择所在城市和入住人员！");
    	return false;
    }
		var days;//天数
		days = parseInt((endday-startday)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
		if(!isNaN(startday)&&!isNaN(endday)){
			days+=1;
		}
		days=isNaN(days)?0:days;
		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+city+'&travelDays='+days+'&hotelDays='+(parseFloat(days)-1)+'&userId='+userId+'&day1='+startday+'&day2='+endday,
			success:function (data){
				
				var ed = $('#reimbursein_hoteltab').datagrid('getEditor',{
					index:index,
					field : 'applyAmount'  
				});
				
				$(ed.target).numberbox('setValue', data[0].standard);
			}
});
}


function hotelStartTime(newVal,oldVal){
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
    var day1 = editors[0]; 
    var day2 = editors[1];
    
    startday = Date.parse(new Date(newVal));
    endday = Date.parse(new Date(day2.target.val()));
    var maxTime = $("#maxTime").val();
    var minTime = $("#minTime").val();
    if(!isNaN(startday)&&!isNaN(endday)){
    	if((startday>=minTime && startday<=maxTime) && endday<=maxTime){
    		if(endday<startday){
	    		alert("到达日期不能小于出发日期！");
	    		editors[0].target.datebox('setValue', '');
	    		editors[6].target.numberbox('setValue', 0.00);
    		}
    	}else{
    		alert("所选时间不在行程范围内请重新选择！");
    		editors[0].target.datebox('setValue', '');
    		editors[6].target.numberbox('setValue', 0.00);
    	}
    }else{
    	if(startday>maxTime || startday<minTime){
    		alert("所选时间不在行程范围内请重新选择！");
    		editors[0].target.datebox('setValue', '');
    	}
    }
    var day1 = editors[1]; //所在城市
    var city = editors[2].target[0].value; //所在城市
    var userId = editors[3].target[0].value; //出行人员
    var startday = Date.parse(new Date(newVal));//入住时间
    var endday = Date.parse(new Date(day1.target[0].value));//退房时间
    if(startday==''||endday==''||isNaN(startday) ||isNaN(endday)){
    	return false;
    }
    if(city==''||userId==''){
    	return false;
    }
		var days;//天数
		days = parseInt((endday-startday)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
		if(!isNaN(startday)&&!isNaN(endday)){
			days+=1;
		}
		days=isNaN(days)?0:days;
		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+city+'&travelDays='+days+'&hotelDays='+(parseFloat(days)-1)+'&userId='+userId+'&day1='+startday+'&day2='+endday,
			success:function (data){
				
				var ed = $('#reimbursein_hoteltab').datagrid('getEditor',{
					index:index,
					field : 'applyAmount'  
				});
				
				$(ed.target).numberbox('setValue', data[0].standard);
			}
});
}
function hotelEndTime(newVal,oldVal){
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
    
    
    var day1 = editors[0]; 
    var day2 = editors[1]; 
    startday = Date.parse(new Date(day1.target.val()));
    endday = Date.parse(new Date(newVal));
    var maxTime = $("#maxTime").val();
    var minTime = $("#minTime").val();
    if(!isNaN(startday)&&!isNaN(endday)){
    	if((startday>=minTime && startday<=maxTime) && endday<=maxTime){
    		if(endday<startday){
	    		alert("到达日期不能小于出发日期！");
	    		editors[1].target.datebox('setValue', '');
	    		editors[6].target.numberbox('setValue', 0.00);
    		}
    	}else{
    		alert("所选时间不在行程范围内请重新选择！");
    		editors[1].target.datebox('setValue', '');
    		editors[6].target.numberbox('setValue', 0.00);
    	}
    }else{
    	if(endday>maxTime || endday<minTime){
    		alert("所选时间不在行程范围内请重新选择！");
    		editors[1].target.datebox('setValue', '');
    		editors[6].target.numberbox('setValue', 0.00);
    	}
    }
    var day0 = editors[0]; //入住时间
    var city = editors[2].target[0].value; //所在城市
    var userId = editors[3].target[0].value; //入住人员
    var startday = Date.parse(new Date(day0.target[0].value));//入住时间
    var endday = Date.parse(new Date(newVal));//退房时间
    if(startday==''||endday==''||isNaN(startday) ||isNaN(endday)){
    	//alert("请选择入住时间和退房时间！");
    	return false;
    }
    if(city==''||userId==''){
    	//alert("请选择所在城市和入住人员！");
    	return false;
    }
		var days;//天数
		days = parseInt((endday-startday)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
		if(!isNaN(startday)&&!isNaN(endday)){
			days+=1;
		}
		days=isNaN(days)?0:days;
		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+city+'&travelDays='+days+'&hotelDays='+(parseFloat(days)-1)+'&userId='+userId+'&day1='+startday+'&day2='+endday,
			success:function (data){
				
				var ed = $('#reimbursein_hoteltab').datagrid('getEditor',{
					index:index,
					field : 'applyAmount'  
				});
				
				$(ed.target).numberbox('setValue', data[0].standard);
			}
});
}


function hotelCity(newVal,oldVal){
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
	
    var day0 = editors[0]; //入住时间
    var day1 = editors[1]; //退房时间
    var city = newVal; //所在城市
    var userId = editors[3].target[0].value; //入住人员
    var startday = Date.parse(new Date(day0.target[0].value));//入住时间
    var endday = Date.parse(new Date(day1.target[0].value));//退房时间
    if(startday==''||endday==''||isNaN(startday) ||isNaN(endday)){
    	//alert("请选择入住时间和退房时间！");
    	return false;
    }
    if(city==''||userId==''){
    	//alert("请选择所在城市和入住人员！");
    	return false;
    }
		var days;//天数
		days = parseInt((endday-startday)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
		if(!isNaN(startday)&&!isNaN(endday)){
			days+=1;
		}
		days=isNaN(days)?0:days;
		$.ajax({
			type:'post',
			async:false,
			dataType:'json',
			url:base+'/hotelStandard/calcCost?outType=travel&configId='+city+'&travelDays='+days+'&hotelDays='+(parseFloat(days)-1)+'&userId='+userId+'&day1='+startday+'&day2='+endday,
			success:function (data){
				
				var ed = $('#reimbursein_hoteltab').datagrid('getEditor',{
					index:index,
					field : 'applyAmount'  
				});
				
				$(ed.target).numberbox('setValue', data[0].standard);
			}
});
}
function clickPersonnelReim(){
	
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	$('#reimbursein_hoteltab').datagrid('selectRow', index).datagrid('beginEdit',index);
		var new_arrs= new_arr_hotel();
		var travelPersonnel = $('#reimbursein_hoteltab').datagrid('getEditor',{
			index:index,
			field:'travelPersonnel'
		});
		$(travelPersonnel.target).combobox({
            data: new_arrs,
            valueField: 'id',
            multiple: true,
            textField: 'text',
		});
}

function new_arr_hotel(){
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
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

//选中时给出行人员设置id
function personnerHotelIdReim(newVal,oldVal){
	
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	var travelPersonnelId = $('#reimbursein_hoteltab').datagrid('getEditor',{
		index:index,
		field:'travelPersonnelId'
	});
	var travelPersonnel = $('#reimbursein_hoteltab').datagrid('getEditor',{
		index:index,
		field:'travelPersonnel'
	});
	$(travelPersonnelId.target).textbox('setValue', travelPersonnel.target.combobox('getValues'));
}


//选中时给出行人员设置id
function cityHotelId(){
	
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	var cityId = $('#reimbursein_hoteltab').datagrid('getEditor',{
		index:index,
		field:'cityId'
	});
	var locationCity = $('#reimbursein_hoteltab').datagrid('getEditor',{
		index:index,
		field:'locationCity'
	});
	$(cityId.target).textbox('setValue', locationCity.target.combobox('getValues'));
}
function clickCityName(){
	
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	$('#reimbursein_hoteltab').datagrid('selectRow', index).datagrid('beginEdit',index);
		var new_arrs= new_arr_city();
		var locationCity = $('#reimbursein_hoteltab').datagrid('getEditor',{
			index:index,
			field:'locationCity'
		});
		$(locationCity.target).combobox({
            data: new_arrs,
            valueField: 'id',
            multiple: false,
            textField: 'text',
		});
}

function new_arr_city(){
	var rows = $('#reimburse_itinerary_tab_id').datagrid('getRows');
	var arrs = new Array();
	for (var i = 0; i < rows.length; i++) {
		var idAndName = {};
		idAndName.id = rows[i].travelAreaId;
		idAndName.text = rows[i].travelAreaName;
		arrs.push(idAndName);
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