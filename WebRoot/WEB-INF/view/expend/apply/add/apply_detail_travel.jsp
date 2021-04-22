<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>


<script type="text/javascript">
function ChangeDateFormat1(val) {
	var t, y, m, d, h, i, s;
    if(val==null){
  	  return "";
    }
    t = new Date(val);
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
}
var travelType = '${bean.travelType}';
if(travelType=='GWCX'){
	$("#officialTripId").show();
	$("#officialTravelId").hide();
}else if(travelType=='GWCC'){
	$("#officialTripId").hide();
	$("#officialTravelId").show();
}

var numType1 = 0;
var numType2 = 0;
$('#travelType').combobox({
	onChange: function (newValue, oldValue) {
		
		$('#check_system_div').load('${base}/apply/refreshProcess?fpPype='+newValue);
		if(newValue=='GWCC'){
			
			
			if(numType1==0){
				var editSts = '${operation}';
				if(editSts=='add'){
				deleteAndAdd();
				}
				$.parser.parse("#officialTravelId");
				numType2=2;
			}else{
				deleteAndAdd();
				if(numType1==2){
					$.parser.parse("#officialTravelId");
				}
			}
			editIndexItinerary = undefined;
			var itineraryRows = $('#tracel_itinerary_tab_id').datagrid('getRows');
			for(var i = itineraryRows.length-1 ; i >= 0 ; i--){
				$('#tracel_itinerary_tab_id').datagrid('deleteRow',i);
			}
			editIndexOutsideTraffic = undefined;
			var outsideRows = $('#outside_traffic_tab_id_apply').datagrid('getRows');
			for(var i = outsideRows.length-1 ; i >= 0 ; i--){
				$('#outside_traffic_tab_id_apply').datagrid('deleteRow',i);
			}
			editIndex = undefined;
			var foodrows = $('#foodtab').datagrid('getRows');
			for(var i = foodrows.length-1 ; i >= 0 ; i--){
				$('#foodtab').datagrid('deleteRow',i);
			}
			editIndexInCity = undefined;
			var inCityTabRows = $('#in_city_tab_id').datagrid('getRows');
			for(var i = inCityTabRows.length-1 ; i >= 0 ; i--){
				$('#in_city_tab_id').datagrid('deleteRow',i);
			}
			editIndexHotel = undefined;
			var hoteltabRows = $('#hoteltabApply').datagrid('getRows');//住宿费
			for (var i = hoteltabRows.length-1; i >= 0 ; i--) {
				$('#hoteltabApply').datagrid('deleteRow',i);
			}
			
			var outsideAmount = $("#outsideAmount").val();
			var cityAmount = $("#cityAmount").val();
			var hotelAmount = $("#hotelAmount").val();
			var foodAmount = $("#foodAmount").val();
			if(outsideAmount=='NaN'||outsideAmount==''||outsideAmount==undefined||outsideAmount==null){
				outsideAmount=0;
			}
			if(cityAmount=='NaN'||cityAmount==''||cityAmount==undefined||cityAmount==null){
				cityAmount=0;
			}
			if(hotelAmount=='NaN'||hotelAmount==''||hotelAmount==undefined||hotelAmount==null){
				hotelAmount=0;
			}
			if(foodAmount=='NaN'||foodAmount==''||foodAmount==undefined||foodAmount==null){
				foodAmount=0;
			}
			$("#applyTotalAmount").html(listToFixed((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount)))+"元");
			$("#applyAmount").val((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)+parseFloat(outsideAmount)).toFixed(2));
			
			sign =0;
			numType1 = 1;
		}
		if(newValue=='GWCX'){
			
			$("#officialTripId").show();
			$("#officialTravelId").hide();
			
			if(numType2==0){
				var editSts = '${operation}';
				if(editSts=='add'){
				deleteAndAdd();
				}
				$.parser.parse("#officialTripId");
				numType1=2;
			}else{
				deleteAndAdd();
				if(numType2==2){
					$.parser.parse("#officialTripId");
				}
			}
				editIndexItineraryTrip = undefined;
				var itineraryRows = $('#tracel_itinerary_trip_tab_id').datagrid('getRows');
				for(var i = itineraryRows.length-1 ; i >= 0 ; i--){
					$('#tracel_itinerary_trip_tab_id').datagrid('deleteRow',i);
				}
				editIndexTrip = undefined;
				var foodrows = $('#foodtabTrip').datagrid('getRows');
				for(var i = foodrows.length-1 ; i >= 0 ; i--){
					$('#foodtabTrip').datagrid('deleteRow',i);
				}
				editIndexInCityTrip = undefined;
				var inCityTabRows = $('#in_city_trip_tab_id').datagrid('getRows');
				for(var i = inCityTabRows.length-1 ; i >= 0 ; i--){
					$('#in_city_trip_tab_id').datagrid('deleteRow',i);
				}
				editIndexHotelTrip = undefined;
				var hoteltabRows = $('#hoteltabApplyTrip').datagrid('getRows');//住宿费
				for (var i = hoteltabRows.length-1; i >= 0 ; i--) {
					$('#hoteltabApplyTrip').datagrid('deleteRow',i);
				}
			
			
			
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
			$("#applyTotalAmountTrip").html(listToFixed((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)))+"元");
			$("#applyAmount").val((parseFloat(foodAmount)+parseFloat(hotelAmount)+parseFloat(cityAmount)).toFixed(2));
			
			sign =0;
			numType2 = 1;
		}
		
	}
});

//显示详细信息手风琴页面
$(document).ready(function() {
	/* //设值时间
	if ($("#applyReqTime").textbox().textbox('getValue') == "") {
		$("#applyReqTime").textbox().textbox('setValue', 'date');
	} */
	//设置时间
	if($("#applyReqTime").val()==""||$("#applyReqTime").val()==null){
		var date = new Date();
		date=ChangeDateFormat1(date);
		$("#req_time").html(date);
		$("#applyReqTime").val(date);
	} else {
		var date = $("#applyReqTime").val();
		date=ChangeDateFormat1(date);
		$("#req_time").html(date);
	}
	
	//设置支出申请扩展信息
	var h = $("#applyTypeHi").textbox().textbox('getValue');
	if (h != "") {
		$('#applyType').val(h);
	}
	
	//批复金额
	var pfAmount = $("#pfAmount").val();
	if(pfAmount !=""){
		$('#p_pfAmount').html(fomatMoney(pfAmount,2)+" [元]");
	}
	//可用金额
	var syAmount = $("#syAmount").val();
	if(syAmount !=""){
		$('#p_syAmount').html(fomatMoney(syAmount,2)+" [元]");
	}
	//批复时间
	var applyAmount = $("#applyAmount").val();
	if(applyAmount !=""){
		$('#applyAmount_span').html(fomatMoney(applyAmount,2)+" [元]");
	}
});

$('#travel_add_placeEnd').textbox({
	onChange:function(newValue,oldValue){
		calcTravelCost();
	}
});

//保存
function saveApply(flowStauts) {
	if($('#applyAmount_span').html()=='&nbsp;'||parseInt($('#applyAmount_span').html())<=0){
		alert('请注意填写费用明细金额！');
		return ;
	}
	// 在后台反序列化成明细Json的对象集合
	var type = '${type}';
	var rows;
	
	//附件的路径地址
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files").val(s);
	//在后台反序列话成被接待人员Json的对象集合
	
	isineraryJson();
	
	//获得城市间交通费的json
	outsideTrafficJson();
	//获得住宿费的json
	getHotelJson();
	//获得市内交通费json
	inCityJson();
	//获得伙食补助费的json
	getfoodJson();
	//设置审批状态
	$('#applyflowStauts').val(flowStauts);
	//设置申请状态
	$('#applyStauts').val(flowStauts);
/* 
	if($('#applyAmount').val()==""||$('#applyAmount').val()=="0.00") {
		alert('请填写费用明细申请金额');
		return;
	} */
	
	/* //校验
	//预算指标
	var budgetIndexName = $('#F_fBudgetIndexName').val();
	if(budgetIndexName == ''){
		alert('请选择预算指标！');
		
		return;
	}
	//出差事由
	var reason = $('#reason').textbox('getValue');
	if(isNaN(reason)) {
		alert('请填写出差事由！');
		return;
	}
	
	//申请金额
	var applyAmount = $('#applyAmount').val();
	if(applyAmount == '' || applyAmount == '0.00') {
		alert('请填写费用明细申请金额！');
		return;
	}
	var syAmount = $('#syAmount').val();
	if(parseFloat(syAmount)<parseFloat(applyAmount)){
		alert('预算可用金额不足,请重新选择预算指标！');
		return;
	} */
	//提交
	$('#apply_save_form').form('submit', {
		onSubmit : function() {
			flag = $(this).form('enableValidation').form('validate');
			 if (flag) {
				//如果校验通过，则进行下一步
				$.messager.progress();
			}else{
				//校验不通过，就打开第一个校验失败的手风琴
				openAccordion();
			}
			return flag;
		},
		url : base + '/apply/saveTravel',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#applyTab'+type).datagrid('reload');
				$('#indexdb').datagrid('reload');
				closeWindow();
				
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}

//打开指标选择页面
function openIndex() {
	//var win=creatFirstWin('选择指标',860,580,'icon-search','/quota/choiceIndex');
	var win=creatFirstWin('选择指标',860,580,'icon-search','/apply/choiceIndex?menuType=beforeApply'); 
	win.window('open');
}

function chooseArea(){
	var win = creatFirstWin('选择-出差地区', 640, 580, 'icon-search', '/hotelStandard/choose');
	win.window('open');
}

//自动获得费用明细
function calcTravelCost(){
	var configId = $('#travel_add_placeEnd_id').textbox('getValue');
	var travelDays = $('#travelTotalDays').val();
	var hotelDays = $('#hotelTotalDays').val();
	if(configId=='' || travelDays=='' ||isNaN(travelDays)|| hotelDays==''||isNaN(hotelDays)){
		return;
	}
		$.ajax({
			url :base+'/hotelStandard/calcCost?outType=travel',
			data : {
				configId: configId,
				travelDays: travelDays,
				hotelDays: hotelDays
			},
			type : 'post',
			dataType : 'json',
			success : function(data){
				for (var i=0;i<data.length;i++){
                	var name = data[i].costDetail;
                	if(name=="住宿费"){
              			//$('#p_hotelStd').html(data[i].standard+"元");
              			$('#hotelAmount').numberbox('setValue',parseFloat(data[i].standard));
                	}
                	if(name=="伙食补助费"){
	              		//$('#p_foodStd').html(data[i].standard+"元");
	              		$('#foodAmount').numberbox('setValue',parseFloat(data[i].standard));
	               	}
			 	}
				var hotelAmount=parseFloat($('#hotelAmount').numberbox('getValue'));
				var foodAmount=parseFloat($('#foodAmount').numberbox('getValue'));
				var loongTavelAmount = parseFloat($('#loongTavelAmount').numberbox('getValue'));
				var cityTavelAmount =parseFloat($('#cityTavelAmount').numberbox('getValue'));
				var otherAmount =parseFloat($('#otherAmount').numberbox('getValue'));
				var totalPrice=parseFloat(hotelAmount+foodAmount);
				if(!isNaN(loongTavelAmount)){
					totalPrice=totalPrice+loongTavelAmount;
				}
				if(!isNaN(cityTavelAmount)){
					totalPrice=totalPrice+cityTavelAmount;
				}
				if(!isNaN(otherAmount)){
					totalPrice=totalPrice+otherAmount;
				}
				//给两个总额框赋值
				$('#applyAmount').val(totalPrice.toFixed(2));
				$('#applyAmount_span').html(fomatMoney(totalPrice,2)+" [元]");
			}
		});
		
}

function changeHotel(n,o){
	if(n==undefined || o==undefined){
		return false;
	}
	var days1 = 0;
	var days2 = 0;
	var days3 = parseInt(n);
	var index=$('#rpt').datagrid('getRowIndex',$('#rpt').datagrid('getSelected'));
	var rows = $('#rpt').datagrid('getRows');
	var row = $('#rpt').datagrid('getSelected');//获得选择行
	var tr = $('#rpt').datagrid('getEditors', index);
	var daysOld = parseInt(row.hotelDays);//判断之前是否有数据
	var traveldays = tr[8].target.numberbox('getValue');;
	for(var i=0;i<rows.length;i++){
		if(i==index){
			days1=parseInt(n);
		}else{
			days2+=addDays(rows,i);
		}
	}
	//3-两类数值相加得到总额
	if(!isNaN(days3)){	
		if(parseInt(n) > traveldays){
		alert('住宿天数不能大于出差天数，请核对！');
		tr[9].target.numberbox('setValue','0');
		n=0;
		} else{
			//原先有数据且未改动的，不能进入总额合计
			if(!isNaN(daysOld) && isNaN(parseInt(o))){
				return;
			} else {
				days2 = days2 + days3;
			}
		}
	}
	$('#hotelTotalDays').val(days2);
	calcTravelCost();
	return;
}

//未编辑或者已经编辑完毕的行
function addDays(rows,index){
	var hoteldays=rows[index]['hotelDays'];
	if(hoteldays==null){
		hoteldays=0;
		return parseInt(hoteldays);
	}
	return parseInt(hoteldays); 
}

function changeTravel(n,o){
	if(n==undefined || o==undefined){
		return false;
	}
	var days4 = 0;
	var days5 = 0;
	var days6 = parseInt(n);
	var index=$('#rpt').datagrid('getRowIndex',$('#rpt').datagrid('getSelected'));
	var rows = $('#rpt').datagrid('getRows');
	var row = $('#rpt').datagrid('getSelected');//获得选择行
	var daysOld = parseInt(row.travelDays);//判断之前是否有数据
	for(var i=0;i<rows.length;i++){
		if(i==index){
			days4=parseInt(n);
		}else{
			days5+=addDays1(rows,i);
		}
	}
	//3-两类数值相加得到总额
	//原先有数据且未改动的，不能进入总额合计
	if(!isNaN(daysOld) && isNaN(parseInt(o))){
		return;
	} else {
			days5 = days5 + days6;
		}
	$('#travelTotalDays').val(days5);
	calcTravelCost();
	return;
}

//未编辑或者已经编辑完毕的行
function addDays1(rows,index){
	var traveldays=rows[index]['travelDays'];
	if(traveldays==null){
		traveldays=0;
		return parseInt(traveldays);
	}
	return parseInt(traveldays); 
}



</script>

<form id="apply_save_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
	<div <c:if test="${bean.fFoodNum!=1 || empty bean.fFoodNum}"> hidden="hidden" </c:if>>
					<a style="display: block; text-align: center;border: 1px solid red;color: red;margin-left: 220px;width: 360px;border-radius: 4px;">您当前出行日期中，伙食费已申报,请核对!</a>
				</div>
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 主键ID --><input type="hidden" name="gId" value="${bean.gId}" />
				<!-- 申请单流水号 --><input type="hidden" name="gCode" value="${bean.gCode}" />
				<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="applyflowStauts" />
				<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="applyStauts" />
				<%-- <!-- 下环节处理人姓名 --><input type="hidden" name="userName2" value="${bean.userName2}" />
				<!-- 下环节处理人编码 --><input type="hidden" name="userId" value="${bean.userId}" /> --%>
				<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
				<!-- 申请类型 --><input type="hidden" id="applyTypeHi" value="${bean.type}" />
				<!-- 指标ID --><input type="hidden" name="indexId" value="${bean.indexId}" id="F_fBudgetIndexCode"/>
				<!-- 项目支出明细ID --><input type="hidden" name="proDetailId" value="${bean.proDetailId}" id="F_proDetailId"/>
				<!-- 指标类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="F_indexType"/>
				<!-- 申请时间  --><input type="hidden" id="applyReqTime" name="reqTime" value="${bean.reqTime}"/>
				<!-- 申请事项  --><input type="hidden" id="applyType" name="type" value="${bean.type}"/>
				<!-- 申请总额  --><input type="text" id="applyAmount" hidden="hidden" name="amount" value="${bean.amount}"/>
				<%-- <!-- 住宿费  --><input type="hidden" id="hotelAmount" name="hotelAmount" value="${travelBean.hotelAmount}"/>
				<!-- 伙食补助费  --><input type="hidden" id="foodAmount" name="foodAmount" value="${travelBean.foodAmount}"/> --%>
				<!-- 可用金额  --><input type="hidden" id="syAmount" value="${bean.syAmount}"/>
				<!-- 批复金额  --><input type="hidden" id="pfAmount" value="${bean.pfAmount}"/>
				<!-- 批复时间  --><input type="hidden" id="pfDate"  value="${bean.pfDate}"/>
				<!-- 流程id  --><input type="hidden" id="flowId"  value="${fpId}"/>
				<!--历史审批节点  --><input type="hidden" id="historyNodes"  value="${historyNodes}"/>
				<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.nCode}"/>
				<!-- 各项明细金额 -->
				<input type="hidden" id="outsideAmount" name="OutsideAmount" value="${bean.outsideAmount}" />
				<input type="hidden" id="cityAmount" name="cityAmount" value="${bean.cityAmount}" />
				<input type="hidden" id="hotelAmount" name="hotelAmount" value="${bean.hotelAmount}" />
				<input type="hidden" id="foodAmount" name="foodAmount" value="${bean.foodAmount}" />
				<!-- 隐藏域 -->
				<input type="hidden" name="trId" value="${travelBean.trId}" />
				<input type="hidden" name="travelRId" value="${tPeopBean.travelRId}" />
				<input type="hidden" id="travelTypeHi" value="${travelBean.travelType}" />
				<input type="hidden" id="wagesPlanHi" value="${travelBean.wagesPlan}" />
				<input type="hidden" id="expensePlanHi" value="${travelBean.expensePlan}" />
				<c:if test="${!empty travelBean.placeStart}">
				<tr class="trbody">
					<td colspan="5"><span style="color:#ff6800">系统已智能为您匹配常用的出差地区</span></td>
				</tr>
				</c:if>
				<!-- 基本信息 -->	
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;width: 707px;">
						<tr class="trbody">
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 摘要</td>
							<td colspan="3">
								<c:if test="${operation=='add' }">
									<input class="easyui-textbox" style="width: 635px;height: 30px; " readonly="readonly" value="${draftAdd}" name="gName" required="required" data-options="validType:'length[1,50]'"/>
								</c:if>
								<c:if test="${operation!='add' }">
									<input class="easyui-textbox" style="width: 635px;height: 30px; " readonly="readonly" value="${bean.gName}" name="gName" required="required" data-options="validType:'length[1,50]'"/>
								</c:if>
							</td>
						</tr>
						<tr class="trbody">
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 出差类型</td>
							<td class="td2" colspan="3">
							<input class="easyui-combobox" id="travelType" name="travelType" readonly="readonly" style="width: 635px;height: 30px;" data-options="editable:false,url:'${base}/Formulation/lookupsJson?parentCode=CCLX&selected=${bean.travelType}',method:'POST',valueField:'code',textField:'text'">
							</td>
						</tr>
						<tr class="trbody" style="line-height: 65px;">
							<td class="td1"><span class="style1">*</span>出差事由</td>
							<td colspan="3">
								<textarea name="reason" id="reason" class="easyui-textbox" readonly="readonly" data-options="multiline:true"
										oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
										style="width:635px;height:60px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:15px; margin-bottom:0px;">${bean.reason }</textarea>
							</td>
						</tr>
						<tr class="trbody">
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 经办人</td>
							<td class="td2" >
							<input class="easyui-textbox" id="userNames" name="userNames" readonly="readonly" value="${bean.userNames}" style="width: 265px;height: 30px;margin-left: 10px " >
							</td>
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 部门名称</td>
							<td class="td2" >
							<input class="easyui-textbox" id="deptName" name="deptName" readonly="readonly" value="${bean.deptName}" style="width: 267px;height: 30px;margin-left: 10px " >
							</td>
						</tr>
					</table>
				</div>				
				</div>
				<c:if test="${bean.travelType=='GWCC'}">
				<!-- 出差人员名单 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
					<div title="行程清单" data-options="collapsed:false,collapsible:false"style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px;">
						<jsp:include page="apply_travel_itinerary_detail.jsp" />
					</div>
					</div>
				</div>
				<!-- 费用明细 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px">
					<div title="费用明细" data-options="collapsible:false" style="overflow:auto;">
						
						 <!-- 城市间交通费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="apply_outside_traffic_detail.jsp" />
						</div>
						<!-- 市内交通费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="apply_in_city_detail.jsp" />
						</div>
						<!-- 住宿费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="hotelExpense_detail.jsp" />
						</div>
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="foodAllowance_detail.jsp" />
						</div>
						<div style="margin-top: 20px;">
							<a style="float: right;">申请总额：<span style="color: #D7414E"  id=""><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
						</div>
					</div>
				</div>
				</c:if>
				<c:if test="${bean.travelType=='GWCX'}">
				<!-- 出差人员名单 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 717px">
					<div title="行程清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px;">
						<jsp:include page="apply_travel_itinerary_trip_detail.jsp" />
					</div>
					</div>
				</div>
				<!-- 费用明细 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px;width: 717px">
					<div title="费用明细" data-options="collapsible:false" style="overflow:auto;">
						<!-- 市内交通费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="apply_in_city_trip_detail.jsp" />
						</div>
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="foodAllowance_trip_detail.jsp" />
						</div>
						<!-- 住宿费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="hotelExpense_trip_detail.jsp" />
						</div>
						<div style="margin-top: 20px">
							<a style="float: right;">申请总额：<span style="color: #D7414E"  id=""><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
						</div>
					</div>
				</div>
				</c:if>
				<!-- 预算信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 50px">
				<div title="预算信息" data-options="collapsible:false" style="overflow:auto;">				
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;margin-left: 0px;width:707px;">
						<tr class="trbody">
							<td class="td1" style="width: 61px;"><span style="text-align: left;color: red">*</span> 预算指标:</td>
							<td colspan="3" >
								<input class="easyui-textbox" readonly="readonly" style="width: 637px;height: 30px;"
								name="indexName" value="${bean.indexName}" id="F_fBudgetIndexName"
								data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}]" readonly="readonly" required="required"/>
							</td>
						</tr>
					</table>	
					<table class="window-table-readonly-zc" cellspacing="0" cellpadding="0" style="margin-left: 0px;width: 707px;height: 50px;">
							<tr>
								<td class="window-table-td1"><p>批复金额：</p></td>
								<td class="window-table-td2"><p id="p_pfAmount">${bean.pfAmount}元</p></td>
								
								<td class="window-table-td1"><p>预算年度：</p></td>
								<td class="window-table-td2"><p id="p_pfDate">${bean.pfDate}</p></td>
							</tr>
							<tr>
								<td class="window-table-td1"><p>可用额度：</p></td>
								<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}</p></td>
							</tr>
					</table>				
				</div>
				</div>
				
				<!-- 附件信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="附件信息" data-options="collapsible:false"
					style="overflow:auto;">		
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;width:707px;">
						<tr>
							<td class="td1" style="width:61px;text-align: left"><span class="style1">*</span>
								出差批示:
							</td>
							<td colspan="3" id="tdf">
								<c:forEach items="${attaList}" var="att">
									<div style="margin-top: 5px;">
									<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
								</c:forEach>
							</td>
						</tr>
						
						
					</table>
					
				</div>
				</div>
				
				<!-- 三重一大会议信息 -->
				<c:if test="${!empty bean.meetingSummaryYear1 or !empty bean.meetingSummaryYear2}">
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
						<div title="三重一大会议信息" data-options="collapsible:false" style="overflow:auto; ">
							<jsp:include page="../check/check_meeting_infomation_detail.jsp" />
						</div>
					</div>
				</c:if>
				<!-- 审批记录 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
						<!-- <div class="window-title"> 审批记录</div> -->
							<jsp:include page="check_history.jsp" />
					</div>
				</div>
			</div>
			
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<a href="javascript:void(0)" onclick="closeWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=支出管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
			
		</div>
		<c:if test="${type!=1 }">
			<div class="window-right-div" style="width:254px;height: 591px">
				<jsp:include page="../../../check_system.jsp" />
			</div>
		</c:if>
	</div>
</form>
<script type="text/javascript">


/* applyTotalAmount();

function applyTotalAmount(){
	//城市间交通费
	//acceptOutsideTraffic();
	var rowsOut = $('#outside_traffic_tab_id').datagrid('getRows');
	var outsideAmount =0;
	for(var i=0; i<rowsOut.length; i++){
		if(rowsOut[i].applyAmount=='' || rowsOut[i].applyAmount==undefined || rowsOut[i].applyAmount=='NaN'){
			outsideAmount =outsideAmount+0;	
		}else{
			outsideAmount =parseFloat(outsideAmount)+parseFloat(rowsOut[i].applyAmount);
		}
	}
			$("#applyOutsideTrafficAmount").html(outsideAmount.toFixed(2)+"元");
	//市内交通费
	//acceptInCity();
	var rowsInCity = $('#in_city_tab_id').datagrid('getRows');
	var inCityAmount =0;
	for(var j=0; j<rowsInCity.length; j++){
		if(rowsInCity[j].fApplyAmount=='' || rowsInCity[j].fApplyAmount==undefined || rowsInCity[j].fApplyAmount=='NaN'){
			inCityAmount =inCityAmount+0;	
		}else{
			inCityAmount =parseFloat(inCityAmount)+parseFloat(rowsInCity[j].fApplyAmount);
		}
	}
			$("#applyInCityAmount").html(inCityAmount.toFixed(2)+"元");
	
	
	//住宿费
	//accepthotel();
	var rowsHotel = $('#hoteltab').datagrid('getRows');
	var hotelAmount =0;
	for(var j=0; j<rowsHotel.length; j++){
		if(rowsHotel[j].applyAmount=='' || rowsHotel[j].applyAmount==undefined || rowsHotel[j].applyAmount=='NaN'){
			hotelAmount =hotelAmount+0;	
		}else{
			hotelAmount =parseFloat(hotelAmount)+parseFloat(rowsHotel[j].applyAmount);
		}
	}
			$("#hotelAmount").html(inCityAmount.toFixed(2)+"元");
	
	//伙食费
	//acceptfood();
	
	var rowsFood = $('#foodtab').datagrid('getRows');
	var foodAmount =0;
	for(var j=0; j<rowsFood.length; j++){
		if(rowsFood[j].fApplyAmount=='' || rowsFood[j].fApplyAmount==undefined || rowsFood[j].fApplyAmount=='NaN'){
			foodAmount =foodAmount+0;	
		}else{
			foodAmount =parseFloat(foodAmount)+parseFloat(rowsFood[j].fApplyAmount);
		}
	}
			$("#foodAmount").html(foodAmount.toFixed(2)+"元");
	
}

 */
</script>