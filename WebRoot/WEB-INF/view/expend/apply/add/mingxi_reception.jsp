<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<c:if test="${type!=1}">
<!-- <div class="window-title">费用明细</div> -->
</c:if>

<!-- 住宿费 -->
<div class="window-table" id="rec-hotel-div" style="margin-bottom:10px;">

	<c:if test="${detail!='detail'}">
	<div style="float: left;">
	<span>费用名称：</span>
	<span style=" color:black;">住宿费</span>
	</div>
	</c:if>
	<c:if test="${empty detail}">
		<div id="appli-detail-tb" style="height:30px;padding-top : 8px">
			<a href="javascript:void(0)" onclick="removeitR()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="appendR()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</div>
	</c:if>
	<table id="rec-hotel-dg" class="easyui-datagrid" style="width:707px;height:auto;"
	data-options="
	toolbar: '#appli-detail-tb',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/receptionHotel?id=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRowR,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<th data-options="field:'hID',hidden:true"></th>
			<th data-options="field:'mark',hidden:true"></th>			
			<th data-options="field:'fName',required:'required',align:'center',width:105,editor:'textbox'">姓名</th>
			<th data-options="field:'position',required:'required',align:'center',width:200,editor:{
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
								url:base+'/apply/lookupsJson?parentCode=ZWJBJD',
								onSelect:reloadOut
							}}">职务/级别</th>
			<th data-options="field:'fRoomType',required:'required',align:'center',width:124,editor:{
						type: 'combotree',
						filter: function(q, row){
									var opts = $(this).combotree('options');
									return row[opts.textField].indexOf(q) == 0;
								},options: {
						valueField:'code',
						textField:'text',
	                	prompt:'-请选择-',
	                	panelHeight:'atuo',
	                	editable: false,
	                	onShowPanel:onShowPanelRoomType
	                	}}">住宿房型</th>
			<!-- <th data-options="field:'fCostStd',required:'required',align:'center',width:120,editor:{type:'numberbox',options:{onChange:addNum1,precision:2,groupSeparator:','}}">费用标准(元/天)</th> -->
			<th data-options="field:'fDays',required:'required',align:'center',width:120,editor:{type:'numberbox',options:{onChange:addNum1}}">住宿天数(天)</th>
			<th data-options="field:'fCostHotel',required:'required',align:'center',width:132,editor:{type:'numberbox',options:{onChange:addAmount1,editable: true,precision:2,groupSeparator:','}}">申请金额</th>
			<th data-options="field:'positionCode',hidden:true,align:'center',editor:'textbox'"></th>			
													
		</tr>
	</thead>
	</table>
	<div style="overflow:auto;margin-top: 10px;">
		<span style="float: right;color: #0000CD;">
			<span>小计金额： </span>
			<span style="float: right;"  id="costHotel_span" ><fmt:formatNumber groupingUsed="true" value="${receptionBean.costHotel}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
			<input type="hidden" id="costHotel" name="costHotel" value="${receptionBean.costHotel}"  />
		</span>
	</div>
</div>
<div style="height:10px;"></div>
<!-- 餐费 -->
<div class="window-table" style="margin-bottom:10px">

	<c:if test="${detail!='detail'}">
	<div style="float: left;">
		<span>费用名称：</span>
		<span style=" color:black;">餐费</span>
	</div>
	<%-- <div style="margin-right:40px;">
	<a href="javascript:void(0)" onclick="removeit2()" style="float: right;margin-right: 22px;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="append2()" style="float: right; "><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div> --%>
	
	</c:if>

	<table id="rec-food-dg" class="easyui-datagrid" style="width:707px;height:auto;"
	data-options="
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/receptionFood?id=${bean.gId}',
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
			<th data-options="field:'fId',hidden:true"></th>			
			<!-- <th data-options="field:'costDetail',required:'required',align:'center',width:100,editor:'textbox'">序号</th> -->
			<th data-options="field:'foodTime',required:'required',align:'center',width:120,editor:{type:'datebox',options:{editable: false,onHidePanel:changeTimeCF}}">日期</th>
			<th data-options="field:'address',required:'required',align:'center',width:165,editor:{type:'textbox',options:{editable: true}}">地点</th>
			<th data-options="field:'fFoodType',required:'required',align:'center',width:100,editor:{type:'textbox',options:{editable: false}}">类别</th>
			<th data-options="field:'fCostStd',hidden:true,required:'required',align:'center',width:130,editor:{type:'numberbox',options:{onChange:addNum2,precision:2,groupSeparator:',',editable: false}}">支出标准(元/人)</th>
			<th data-options="field:'fPersonNum',required:'required',align:'center',width:90,editor:{type:'numberbox',options:{precision:0,onChange:addNum2,editable: true}}">接待人数</th>
			<th data-options="field:'attendPeopNum',required:'required',align:'center',width:90,editor:{type:'numberbox',options:{precision:0,onChange:attendPeopNumChange,editable: true}}">陪餐人数</th>
			<th data-options="field:'fCostFood',required:'required',align:'center',width:116,editor:{type:'numberbox',options:{onChange:addAmount2,editable: true,precision:2,groupSeparator:','}}">申请金额(元)</th>
		</tr>
	</thead>
	</table>
	<div style="overflow:auto;margin-top: 10px;">
		<span style="float: right;color: #0000CD;">
			<span>小计金额： </span>
			<span style="float: right;"  id="costFood_span" ><fmt:formatNumber groupingUsed="true" value="${receptionBean.costFood}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
			<input type="hidden" id="costFood" name="costFood" value="${receptionBean.costFood}"  />
		</span>
	</div>
	
</div>
<%-- <div style="height:10px;"></div>
<div class="window-table" style="margin-bottom:10px">
<table class="window-table-readonly" cellspacing="0" cellpadding="0" style="margin-left: 0px;width:707px;">
		<tr>
			<td class="window-table-td1" style="width:66px;"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:black">交通费</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input id="costTraffic" name="costTraffic" class="easyui-numberbox" value="${receptionBean.costTraffic}" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
</table>
</div> --%>

<div style="height:10px;"></div>
<div class="window-table" style="margin-bottom:10px">
<table class="window-table-readonly" cellspacing="0" cellpadding="0" style="margin-left: 0px;width: 707px;">
		<tr>
			<td class="window-table-td1" style="width:66px;"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:black;">会议室租金</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input id="costRent" name="costRent" class="easyui-numberbox" value="${receptionBean.costRent}" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
</table>
</div>
<div style="height:15px;"></div>

<!-- 其他费用 -->
<div class="window-table">
	<c:if test="${detail!='detail'}">
		<div style="float: left;">
			<span>费用名称：</span>
			<span style=" color:black;">其他费用</span>
		</div>
	</c:if>
	<c:if test="${empty detail}">
	<div id="rph" style="height:30px;padding-top : 8px">
		<a href="javascript:void(0)" onclick="removeit2()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="append2()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	</c:if>
	<table id="rec-other-dg" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#rph',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/receptionOther?id=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow2, 
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'oID',hidden:true"></th>
				<th data-options="field:'fCostName',required:'required',align:'center',width:195,editor:'textbox'">费用名称</th>
				<th data-options="field:'fCost',required:'required',align:'center',width:191,editor:{type:'numberbox',options:{onChange:addNumOther,precision:2,groupSeparator:','}}">申请金额[元]</th>
				<th data-options="field:'fRemark',width:293,align:'center',editor:'textbox'">备注</th>
			</tr>
		</thead>
	</table>
	<div style="overflow:auto;margin-top: 10px;">
		<span style="float: right;color: #0000CD;">
			<span>小计金额： </span>
			<span style="float: right;"  id="costOther_span" ><fmt:formatNumber groupingUsed="true" value="${receptionBean.costOther}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
			<input type="hidden" id="costOther" name="costOther" value="${receptionBean.costOther}"  />
		</span>
	</div>
	<input type="hidden" id="otherJson" name="otherJson" />
</div>
<input type="hidden" id="hotelJson" name="hotelJson"/>
<input type="hidden" id="foodJson" name="foodJson"/>
<script type="text/javascript">
$(function(){
	$("#costTraffic").numberbox({
	    onChange : function(newValue,oldValue){
	    	addTotalNum();
	    }
	});
	$("#costRent").numberbox({
	    onChange : function(newValue,oldValue){
	    	addTotalNum();
	    }
	});
});
//计算总金额
function addTotalNum(){
	
	var totalNum =0;
	var num1 =parseFloat($('#costHotel').val());
	var num2 =parseFloat($('#costFood').val());
	var num3 =parseFloat($('#costOther').val());
	/* var num4 =parseFloat($('#costTraffic').numberbox('getValue')); */
	var num5 =parseFloat($('#costRent').numberbox('getValue'));
	if(!isNaN(num1)){
		totalNum = totalNum+num1;
	}if(!isNaN(num2)){
		totalNum = totalNum+num2;
	}if(!isNaN(num3)){
		totalNum = totalNum+num3;
	}/* if(!isNaN(num4)){
		totalNum = totalNum+num4;
	} */if(!isNaN(num5)){
		totalNum = totalNum+num5;
	}
	//给两个总额框赋值
	$('#applyAmount').val(totalNum.toFixed(2));
	$('#applyAmount_span').html(fomatMoney(totalNum,2)+" [元]");
}

function addNum1(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined ||newVal==''){
		return false;
	}
	var reDayNum =parseInt($('#reDayNum').val());
	var index=$('#rec-hotel-dg').datagrid('getRowIndex',$('#rec-hotel-dg').datagrid('getSelected'));
    var editors = $('#rec-hotel-dg').datagrid('getEditors', index); 
	if(isNaN(reDayNum)){
		editors[3].target.numberbox('setValue', '');
		alert('请选择开始时间或结束时间！');
		return false;
	}
	
	if(reDayNum<=parseFloat(newVal)){
		//var index=$('#rec-hotel-dg').datagrid('getRowIndex',$('#rec-hotel-dg').datagrid('getSelected'));
	    //var editors = $('#rec-hotel-dg').datagrid('getEditors', index); 
	    //var ed1 = editors[3]; 
	    //var ed2 = editors[4]; 
	    //var num1 =parseFloat(ed1.target.val());
	    //var num2 =parseFloat(ed2.target.val());
	    //var num3 =num1*num2;
	    //if(!isNaN(num1) &&!isNaN(num2)){
	    //}
	    editors[3].target.numberbox('setValue', '');
	    alert('住宿天数要小于接待天数，请重新填写！');
	    return false;
	}else{
		if(parseFloat(newVal)<=0){
			editors[3].target.numberbox('setValue', '');
			alert('住宿天数应该大于0，请重新填写！');
			return false;
		}
	}
}

//计算住宿费总额
function addAmount1(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var rows = $('#rec-hotel-dg').datagrid('getRows');
     var num1 = 0;
     for (var i = 0; i < rows.length; i++) {
    	 if (!isNaN(parseFloat(rows[i]['fCostHotel']))) {
	    	 num1 += parseFloat(rows[i]['fCostHotel']);
    	 }
	}
     var num = parseFloat(newValue);
     var row = $('#rec-hotel-dg').datagrid('getSelected');
     var numOld = parseFloat(row.fCostHotel);
     if(!isNaN(num)){
    	 if(!isNaN(numOld) && isNaN(parseFloat(oldValue))){
				return;
		} else {
			if (!isNaN(numOld)) {
				num1 = num1 + num - numOld;
			}else{
				num1 = num1 + num;
			}
		 }
	 } 
   //给两个框赋值
		$('#costHotel').val(num1.toFixed(2));
		$('#costHotel_span').html(fomatMoney(num1,2)+" [元]");
		addTotalNum();
}
//接待人员表格添加删除，保存方法
	var editIndex = undefined;
	function endEditingR() {
		if (editIndex == undefined) {
			return true
		}
		if ($('#rec-hotel-dg').datagrid('validateRow', editIndex)) {
			//下面三行，是在增加一行的时候，防止原来的一行的值变成code
			var tr = $('#rec-hotel-dg').datagrid('getEditors', editIndex);
			var text2=tr[1].target.combotree('getText');
			if(text2!='--请选择--'){
				tr[1].target.combotree('setValue',text2);
			}
			var text3=tr[2].target.combotree('getText');
			if(text3!='--请选择--'){
				tr[2].target.combotree('setValues',text3);
			}
			$('#rec-hotel-dg').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRowR(index) {
		if (editIndex != index) {
			if (endEditingR()) {
				$('#rec-hotel-dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex = index;
			} else {
				$('#rec-hotel-dg').datagrid('selectRow', editIndex);
			}
		}
	}
	function appendR() {
		if($('#rec-hotel-dg').datagrid('getRows').length >= $('#rePeopNum').val()){
			alert("行数不得超过接待对象人数");
			return;
		};
		if (endEditingR()) {
			$('#rec-hotel-dg').datagrid('appendRow', {});
			editIndex = $('#rec-hotel-dg').datagrid('getRows').length - 1;
			$('#rec-hotel-dg').datagrid('selectRow', editIndex).datagrid('beginEdit',editIndex);
		}
	}
	function removeitR() {
		if (editIndex == undefined) {
			return
		}
		$('#rec-hotel-dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
				editIndex);
		editIndex = undefined;
		var rows = $('#rec-hotel-dg').datagrid('getRows');
		var num1 = 0;
		for(var i = 0; i<rows.length;i++){
			if(rows[i].fCostHotel != null && rows[i].fCostHotel != '' && rows[i].fCostHotel != undefined){
				num1 += parseInt(rows[i].fCostHotel);
			}else{
				num1 += parseInt(0);
			}
		}
		/* var travelDays=0;
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
		calcTravelCost(); */
		$('#costHotel').val(num1.toFixed(2));
		$('#costHotel_span').html(fomatMoney(num1,2)+" [元]");
		addTotalNum();
	}
	function acceptR() {
		if (endEditingR()) {
			$('#rec-hotel-dg').datagrid('acceptChanges');
		}
	}
	
	
	

</script>
<script type="text/javascript">
function addNum2(newValue,oldValue){
	var index=$('#rec-food-dg').datagrid('getRowIndex',$('#rec-food-dg').datagrid('getSelected'));
    var editors = $('#rec-food-dg').datagrid('getEditors', index); 
    var ed1 = editors[1]; 
    var ed2 = editors[2]; 
    //var ed3 = editors[3];
    var ed3 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'fPersonNum'});
   	var ed4 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'attendPeopNum'});
   
   	var unitFeteNum =parseInt($(ed3.target).numberbox('getValue'));//宴请人数
   	var unitFeteNum1 =parseInt($(ed4.target).numberbox('getValue'));//宴请人数
   	if($(ed3.target).numberbox('getValue') == '' || $(ed3.target).numberbox('getValue') == null){
   		alert('请先填写宴请人数');
  		$(ed4.target).numberbox('setValue','');
  		return false;
   	}else{
   		if(parseInt(unitFeteNum)<=10){
   	   		if(parseInt(unitFeteNum1)>3){
   	   			alert('陪餐人数不能超过3人,请重新填写！');
   	   			$(ed4.target).numberbox('setValue','');
   	   			return false;
   	   		}
   	   	}else{
   	   		var unitFeteNumDivide = parseInt(unitFeteNum/3);
   	   		if(parseInt(unitFeteNum1)>unitFeteNumDivide){
   	   			alert('陪餐人数不能超过宴请人1/3,请重新填写！');
   	   			$(ed4.target).numberbox('setValue','');
   	   			return false;
   	   		}
   	   	}
   	}
    
    var num1 =parseFloat(ed1.target.val());
    var num2 =parseFloat(ed2.target.val());
    //var num3 =parseFloat(ed3.target.val());
    if(!isNaN(num1) &&!isNaN(num2)){
    var num4 =num1;
    	editors[3].target.numberbox('setValue', num4);
    }
}

//计算餐费总额
function addAmount2(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined || newValue==''){
		return false;
	}
	var rows = $('#rec-food-dg').datagrid('getRows');
     var num1 = 0;
     for (var i = 0; i < rows.length; i++) {
    	 if (!isNaN(parseFloat(rows[i]['fCostFood']))) {
	    	 num1 += parseFloat(rows[i]['fCostFood']);
    	 }
	}
     var num = parseFloat(newValue);
     var row = $('#rec-food-dg').datagrid('getSelected');
     var numOld = parseFloat(row.fCostFood);
     var fCostStd = parseFloat(row.fCostStd);
     var fPersonNum = parseFloat(row.fPersonNum);
     var ed1 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'fPersonNum'});
    var ed2 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'attendPeopNum'});
    var ed3 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'fCostFood'});
    var unitFeteNum1 =parseInt($(ed1.target).numberbox('getValue'));//宴请人数
    var unitFeteNum2 =parseInt($(ed2.target).numberbox('getValue'));//陪餐人数
    
    
    if(isNaN(unitFeteNum1) || isNaN(unitFeteNum2)){
    	alert("请先填写宴请人数和陪餐人数");
    	num = 0;
    	$(ed3.target).numberbox('setValue','0');
    }else if(num>(unitFeteNum1+unitFeteNum2)*60){
    	alert("餐费申请金额人均不超过60");
    	num = 0;
    	$(ed3.target).numberbox('setValue','0');
    }
    
     if(num>(fPersonNum*fCostStd)){
    	 var rindex = $('#rec-food-dg').datagrid('getRowIndex', row); 
         var positions = $('#rec-food-dg').datagrid('getEditor',{
     		index:rindex,
     		field : 'fCostFood',
     	});
         var fCostStdString ='当前项报销不能超出'+fPersonNum*fCostStd+'，请重新填写！' ;
         alert(fCostStdString);
    	 $(positions.target).numberbox('setValue', '');
    	 return false;
     }
     if(!isNaN(num)){
    	 if(!isNaN(numOld) && isNaN(parseFloat(oldValue))){
				return;
		} else {
			if (!isNaN(numOld)) {
				num1 = num1 + num - numOld;
			}else{
				num1 = num1 + num;
			}
		 }
	 } 
   //给两个框赋值
		$('#costFood').val(num1.toFixed(2));
		$('#costFood_span').html(fomatMoney(num1,2)+" [元]");
		addTotalNum();
}

//接待人员表格添加删除，保存方法
var editIndex1 = undefined;
function endEditing1() {
	if (editIndex1 == undefined) {
		return true
	}
	if ($('#rec-food-dg').datagrid('validateRow', editIndex1)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		$('#rec-food-dg').datagrid('endEdit', editIndex1);
		editIndex1 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow1(index) {
	if (editIndex1 != index) {
		if (endEditing1()) {
			$('#rec-food-dg').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndex1 = index;
		} else {
			$('#rec-food-dg').datagrid('selectRow', editIndex1);
		}
	}
}
function append1() {
	if (endEditing1()) {
		$('#rec-food-dg').datagrid('appendRow', {});
		editIndex1 = $('#rec-food-dg').datagrid('getRows').length - 1;
		$('#rec-food-dg').datagrid('selectRow', editIndex1).datagrid('beginEdit',editIndex1);
	}
}
function removeit1() {
	if (editIndex1 == undefined) {
		return
	}
	$('#rec-food-dg').datagrid('cancelEdit', editIndex1).datagrid('deleteRow',
			editIndex1);
	editIndex1 = undefined;
	/* var travelDays=0;
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
	calcTravelCost(); */
}
function accept1() {
	if (endEditing1()) {
		$('#rec-food-dg').datagrid('acceptChanges');
	}
}
//陪餐人数
function attendPeopNumChange(newValue,oldValue){
   	if(newValue==undefined || oldValue==undefined || newValue==''){
   		return false;
   	}
   	
   	var ed1 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'fPersonNum'});
   	var ed2 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'attendPeopNum'});
   
   	var unitFeteNum =parseInt($(ed1.target).numberbox('getValue'));//宴请人数
   	if($(ed1.target).numberbox('getValue') == '' || $(ed1.target).numberbox('getValue') == null){
   		alert('请先填写宴请人数');
  		$(ed2.target).numberbox('setValue','');
  		return false;
   	}else{
   		if(parseInt(unitFeteNum)<=10){
   	   		if(parseInt(newValue)>3){
   	   			alert('陪餐人数不能超过3人,请重新填写！');
   	   			$(ed2.target).numberbox('setValue','');
   	   			return false;
   	   		}
   	   	}else{
   	   		var unitFeteNumDivide = parseInt(unitFeteNum/3);
   	   		if(parseInt(newValue)>unitFeteNumDivide){
   	   			alert('陪餐人数不能超过宴请人1/3,请重新填写！');
   	   			$(ed2.target).numberbox('setValue','');
   	   			return false;
   	   		}
   	   	}
   	}
}
</script>
<script type="text/javascript">
//接待人员表格添加删除，保存方法
var editIndex2 = undefined;
function endEditing2() {
	if (editIndex2 == undefined) {
		return true
	}
	if ($('#rec-other-dg').datagrid('validateRow', editIndex2)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		$('#rec-other-dg').datagrid('endEdit', editIndex2);
		editIndex2 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow2(index) {
	if (editIndex2 != index) {
		if (endEditing2()) {
			$('#rec-other-dg').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndex2 = index;
		} else {
			$('#rec-other-dg').datagrid('selectRow', editIndex2);
		}
	}
}
function append2() {
	if (endEditing2()) {
		$('#rec-other-dg').datagrid('appendRow', {});
		editIndex2 = $('#rec-other-dg').datagrid('getRows').length - 1;
		$('#rec-other-dg').datagrid('selectRow', editIndex2).datagrid('beginEdit',editIndex2);
	}
}
function removeit2() {
	if (editIndex2 == undefined) {
		return
	}
	$('#rec-other-dg').datagrid('cancelEdit', editIndex2).datagrid('deleteRow',
			editIndex2);
	editIndex2 = undefined;
	var rows = $('#rec-other-dg').datagrid('getRows');
    var num1 = 0;
    for (var i = 0; i < rows.length; i++) {
   	 if (!isNaN(parseFloat(rows[i]['fCost']))) {
	    	 num1 += parseFloat(rows[i]['fCost']);
   	 }
	}
    
    //给两个总额框赋值
	$('#costOther').val(num1.toFixed(2));
	$('#costOther_span').html(fomatMoney(num1,2)+" [元]");
	addTotalNum();
}
function accept2() {
	if (endEditing2()) {
		$('#rec-other-dg').datagrid('acceptChanges');
	}
}

//计算金额
function addNumOther(newValue,oldValue){
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var rows = $('#rec-other-dg').datagrid('getRows');
     var num1 = 0;
     for (var i = 0; i < rows.length; i++) {
    	 if (!isNaN(parseFloat(rows[i]['fCost']))) {
	    	 num1 += parseFloat(rows[i]['fCost']);
    	 }
	}
     //
     var num = parseFloat(newValue);
     var row = $('#rec-other-dg').datagrid('getSelected');
     var numOld = parseFloat(row.fCost);
     if(!isNaN(num)){
    	 if(!isNaN(numOld) && isNaN(parseFloat(oldValue))){
				return;
		} else {
			if (!isNaN(numOld)) {
				num1 = num1 + num - numOld;
			}else{
				num1 = num1 + num;
			}
		 }
	 } 
     
   //给两个框赋值
		$('#costOther').val(num1.toFixed(2));
		$('#costOther_span').html(fomatMoney(num1,2)+" [元]");
		addTotalNum();
}


function reloadOut(rec){
	
	var row = $('#rec-hotel-dg').datagrid('getSelected');
	var rindex = $('#rec-hotel-dg').datagrid('getRowIndex', row); 
	var ed = $('#rec-hotel-dg').datagrid('getEditor',{
		index:rindex,
		field : 'fRoomType'  
	});
	var positionCode = $('#rec-hotel-dg').datagrid('getEditor',{
		index:rindex,
		field : 'positionCode'  
	});
	
		$(positionCode.target).textbox('setValue', rec.code);
		$(ed.target).combotree('setValue', '');
		var url = base+'/apply/lookupsJson?parentCode='+rec.code;
    	$(ed.target).combotree('reload', url);
	 /* $(ed.target).combobox('setValue', '2016'); */
}

function onClickCellType(){
	var row = $('#rec-hotel-dg').datagrid('getSelected');
	var rindex = $('#rec-hotel-dg').datagrid('getRowIndex', row); 
	var positions = $('#rec-hotel-dg').datagrid('getEditor',{
		index:rindex,
		field : 'position'  
	});
	var ed = $('#rec-hotel-dg').datagrid('getEditor',{
		index:rindex,
		field : 'fRoomType'  
	});
	
		var position = $(positions.target).combotree('getValues');
		$(ed.target).combotree('setValue', '');
		var url = base+'/apply/lookupsJson?parentCode='+position;
    	$(ed.target).combotree('reload', url);
}
function changeTimeCF(){
	
	var index=$('#rec-food-dg').datagrid('getRowIndex',$('#rec-food-dg').datagrid('getSelected'));
	var editors = $('#rec-food-dg').datagrid('getEditors', index);  
	var endEditor = editors[1]; 
	var startday = Date.parse(new Date(editors[0].target.val()));
	var maxTime = Date.parse(new Date($("#reDateEnd").datebox('getValue')));
    var minTime = Date.parse(new Date($("#reDateStart").datebox('getValue')));
	var endday = Date.parse(new Date(endEditor.target.val()));
	if(!isNaN(startday)){
    	if((startday>=minTime &&startday<=maxTime) ){
    		if(!isNaN(endday)){
	    		if(endday<startday){
	        		alert("结束日期不能小于开始日期！");
	        		editors[0].target.datebox('setValue', '');
	        	}
    		}
    	}else{
    		if(startday>maxTime || startday<minTime){
	    	alert("所选时间不在日程范围内请重新选择！");
    		editors[0].target.datebox('setValue', '');
    		}
    	}
    	
    } 
}

function onShowPanelRoomType(){
	
	var row = $('#rec-hotel-dg').datagrid('getSelected');
	var rindex = $('#rec-hotel-dg').datagrid('getRowIndex', row); 
	var ed = $('#rec-hotel-dg').datagrid('getEditor',{
		index:rindex,
		field : 'fRoomType'  
	});
	var positionCode = $('#rec-hotel-dg').datagrid('getEditor',{
		index:rindex,
		field : 'positionCode'  
	});
	
		var code = $(positionCode.target).textbox('getValue');
		$(ed.target).combotree('setValue', '');
		var url = base+'/apply/lookupsJson?parentCode='+code;
    	$(ed.target).combotree('reload', url);
}
</script>