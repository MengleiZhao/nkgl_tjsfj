<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<style>
.under{
	outline: none;
	width:25px;
    border-bottom: 1px solid #000;
    border-top: 0px;
    border-left: 0px;
    border-right: 0px;
    background-color: #F0F5F7;
    text-align:center;
    color:#0000CD;
}
</style>
<table>
	<tr>
		<td>1-以下为综合预算</td>
	</tr>
</table>

<table class="window-table-readonly" cellspacing="0" cellpadding="0">
	<tr>
		<td class="window-table-td1" style="width:15%"><p>费用名称：</p></td>
		<td class="window-table-td2" style="width:27%">
			<p style=" color:#0000CD;">住宿费</p>
		</td>
		
		<td class="window-table-td1"><p>计算标准：</p></td>
		<td class="td2">
			（人数<input class="under" id="personNum1" value="${trainingBean.trDayNum}" readonly="readonly" type="text">*天数
			<input class="under" id="personDay1" value="${trainingBean.trAttendNum+trainingBean.trStaffNum}" readonly="readonly" type="text">*元/人天
			<input class="under" id="hotelStd" name="hotelStd" value="${trainingBean.hotelStd}" readonly="readonly" type="text">）
		</td>
		<!-- <td class="window-table-td2"><font id="p_hotelStd">200</font>元/人天</td> -->
	</tr>
	<!-- <tr>
		<td class="window-table-td1"><p>住宿人数：</p></td>
		<td class="window-table-td2">
			<input id="nb_hotelPerson" class="easyui-numberbox"
			style="height:25px;"/>
		</td>
		
		<td class="window-table-td1"><p>住宿天数：</p></td>
		<td class="window-table-td2">
			<input id="nb_hotelDays" class="easyui-numberbox"
			style="height:25px;"/>
		</td>
	</tr> -->
	<tr>
		<td class="window-table-td1"><p>申请金额：</p></td>
		<td class="window-table-td2">
			<!-- <p id="p_hotelMoney"></p> -->
			<input id="hotelMoney"  name="hotelMoney" value="${trainingBean.hotelMoney}" class="easyui-numberbox"
			 style="height:25px;width:110px" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" <c:if test="${operation!='add'&& operation!='edit'}">readonly="readonly"</c:if>>
		</td>
	</tr>
</table>		
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1" style="width:15%"><p>费用名称：</p></td>
			<td class="window-table-td2" style="width:27%">
				<p style=" color:#0000CD;">伙食费</p>
			</td>
			
			<td class="window-table-td1"><p>计算标准：</p></td>
			<td class="td2">
				（人数<input class="under" id="personNum2" value="${trainingBean.trDayNum}" readonly="readonly" type="text">*天数
				<input class="under" id="personDay2" value="${trainingBean.trAttendNum+trainingBean.trStaffNum}" readonly="readonly" type="text">*元/人天
				<input class="under" id="foodStd" name="foodStd" readonly="readonly" value="${trainingBean.foodStd}" type="text" >）
			</td>
			<!-- <td class="window-table-td2"><font id="p_foodStd">100</font>元/人天</td> -->
		</tr>
		<!-- <tr>
			<td class="window-table-td1"><p>申请人数：</p></td>
			<td class="window-table-td2">
				<input id="nb_foodPerson" class="easyui-numberbox"
				style="height:25px;"/>
			</td>
			
			<td class="window-table-td1"><p>申请天数：</p></td>
			<td class="window-table-td2">
				<input id="nb_foodDays" class="easyui-numberbox"
				style="height:25px;"/>
			</td>
		</tr> -->
		<tr>
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<!-- <p id="p_foodMoney"></p> -->
				<input class="easyui-numberbox" style="height:25px;width:110px" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" 
				id="foodMoney" name="foodMoney" value="${trainingBean.foodMoney}" <c:if test="${operation!='add'&& operation!='edit'}">readonly="readonly"</c:if>>
			</td>
		</tr>
</table>		
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">培训资料费</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input id="dataMoney" class="easyui-numberbox" name="dataMoney" value="${trainingBean.dataMoney}" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
</table>	
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">培训场地费</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input id="spaceMoney" class="easyui-numberbox" name="spaceMoney" value="${trainingBean.spaceMoney}" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
</table>	
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">交通费</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input id="transportMoney" class="easyui-numberbox" name="transportMoney" value="${trainingBean.transportMoney}" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
</table>	
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">其他费用</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input id="otherMoney" class="easyui-numberbox" name="otherMoney" value="${trainingBean.otherMoney}" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
</table>	
<div style="height:15px;"></div>
<table>
	<tr>
		<td>2-以下为师资费预算</td>
	</tr>
</table>	
<div style="height:5px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>讲师级别：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">院士、全国知名专家</p>
			</td>
			<td class="window-table-td1"><p>是否异地：</p></td>
			<td class="window-table-td2">
				<input id="outsideIsorNo1" name="outsideIsorNo1" value="${trainingBean.outsideIsorNo1}" class="easyui-combobox"
				data-options="valueField: 'trainingType',textField: 'value',editable: false,
				data: [{trainingType: '1',value: '是'},{trainingType: '0',value: '否'}]"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">讲课费</p>
			</td>
			<td class="window-table-td1"><p>计算标准：</p></td>
			<td class="window-table-td2">
				（学时<input class="easyui-numberbox" required="required" id="lessonTime1" name="lessonTime1" style="height:25px;width:30px" value="${trainingBean.lessonTime1}" <c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>>*元/学时
				<input class="under" id="lessonStd1" name="lessonStd1"  style="width:30px" name="lessonStd1" value="1500" readonly="readonly" type="text">）
			</td>
			<!-- <td class="window-table-td2"><font id="p_hotelStd">200</font>元/学时</td> -->
		</tr>
		<tr>
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<!-- <p id="p_lessonMoney1"></p> -->
				<input id="lessonMoney1" name="lessonMoney1" value="${trainingBean.lessonMoney1}"  required="required"
				class="easyui-numberbox" style="height:25px;width:110px" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" <c:if test="${operation!='add'&& operation!='edit'}">readonly="readonly"</c:if>>
			</td>
		</tr>
</table>	
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>讲师级别：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">正高级职业职称讲师</p>
			</td>
			<td class="window-table-td1"><p>是否异地：</p></td>
			<td class="window-table-td2">
				<input id="outsideIsorNo2" name="outsideIsorNo2" value="${trainingBean.outsideIsorNo2}" class="easyui-combobox"
				data-options="valueField: 'trainingType',textField: 'value',editable: false,
				data: [{trainingType: '1',value: '是'},{trainingType: '0',value: '否'}]"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">讲课费</p>
			</td>
			<td class="window-table-td1"><p>计算标准：</p></td>
			<td class="td2">
				（学时<input class="easyui-numberbox" id="lessonTime2"  required="required" value="${trainingBean.lessonTime2}" name="lessonTime2" style="height:25px;width:30px"  <c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>>*元/学时
				<input class="under" id="lessonStd2" name="lessonStd2" style="width:30px" value="1000" readonly="readonly" type="text">）
			</td>
			<!-- <td class="window-table-td1"><p>费用标准：</p></td>
			<td class="window-table-td2"><font id="p_hotelStd">200</font>元/学时</td> -->
		</tr>
		<tr>
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<!-- <p id="p_lessonMoney2"></p> -->
				<input id="lessonMoney2" name="lessonMoney2" value="${trainingBean.lessonMoney2}"  required="required"
				class="easyui-numberbox" style="height:25px;width:110px" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" <c:if test="${operation!='add'&& operation!='edit'}">readonly="readonly"</c:if>>
			</td>
		</tr>
</table>		
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>讲师级别：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">副高级技术职称讲师</p>
			</td>
			<td class="window-table-td1"><p>是否异地：</p></td>
			<td class="window-table-td2">
				<input id="outsideIsorNo3" name="outsideIsorNo3" value="${trainingBean.outsideIsorNo3}" class="easyui-combobox"
				data-options="valueField: 'trainingType',textField: 'value',editable: false,
				data: [{trainingType: '1',value: '是'},{trainingType: '0',value: '否'}]"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">讲课费</p>
			</td>
			<td class="window-table-td1"><p>计算标准：</p></td>
			<td class="td2">
				（学时<input class="easyui-numberbox" id="lessonTime3" name="lessonTime3"  required="required" style="height:25px;width:30px" value="${trainingBean.lessonTime3}"   
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>>*元/学时
				<input class="under" id="lessonStd3" name="lessonStd3" style="width:30px" value="500" readonly="readonly" type="text">）
			</td>
			<!-- <td class="window-table-td1"><p>费用标准：</p></td>
			<td class="window-table-td2"><font id="p_hotelStd">200</font>元/学时</td> -->
		</tr>
		<tr>
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<!-- <p id="p_lessonMoney3"></p> -->
				<input id="lessonMoney3" name="lessonMoney3" value="${trainingBean.lessonMoney3}"  required="required"
				class="easyui-numberbox" style="height:25px;width:110px" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" <c:if test="${operation!='add'&& operation!='edit'}">readonly="readonly"</c:if>>
			</td>
		</tr>
</table>
<div style="height:10px;"></div>
<table class="window-table-readonly" cellspacing="0" cellpadding="0">
		<tr>
			<td class="window-table-td1"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:#0000CD;">长途交通费</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input id="longTrafficMoney" class="easyui-numberbox" name="longTrafficMoney"  required="required" value="${trainingBean.longTrafficMoney}" data-options="icons: [{iconCls:'icon-yuan'}],precision:2"
				<c:if test="${operation!='add'&& operation!='edit'}">
				readonly="readonly"
				</c:if>
				style="height:25px;"/>
			</td>
		</tr>
</table>

<script type="text/javascript">
$(function(){
	initMeetInput();
});

//计算住宿费总额
function addHotelMoney(){
	var hotelMoney=0;
	var num1= parseFloat($('#personNum1').val());
	var num2= parseFloat($('#personDay1').val());
	var num3= parseFloat($('#hotelStd').val());
	if(!isNaN(num1) && !isNaN(num2) && !isNaN(num3)){
		hotelMoney = num1 * num2 * num3;
	}
	//$('#hotelMoney').val(hotelMoney);
	$('#hotelMoney').numberbox('setValue',hotelMoney.toFixed(2));
	//$('#p_hotelMoney').html(fomatMoney(hotelMoney,2)+" [元]");
	calTotalMoney();
}

//计算餐费总额
function addFoodMoney(){
	var foodMoney=0;
	var num1= parseFloat($('#personNum2').val());
	var num2= parseFloat($('#personDay2').val());
	var num3= parseFloat($('#foodStd').val());
	if(!isNaN(num1) && !isNaN(num2) && !isNaN(num3)){
		foodMoney = num1 * num2 * num3;
	}
	//$('#foodMoney').val(foodMoney);
	$('#foodMoney').numberbox('setValue',foodMoney.toFixed(2));
	//$('#p_foodMoney').html(fomatMoney(foodMoney,2)+" [元]");
	calTotalMoney();
}
//计算讲课费1
$('#lessonTime1').numberbox({
	onChange:function(newValue,oldValue){
		var lessonMoney1=0;
		var num1= newValue;
		var num2= parseFloat($('#lessonStd1').val());
		if(!isNaN(num1) && !isNaN(num2)){
			lessonMoney1 = num1 * num2;
		}
		//console.log(lessonMoney1);
		//$('#lessonMoney1').val(lessonMoney1);
		$('#lessonMoney1').numberbox('setValue',lessonMoney1.toFixed(2));
		//$('#p_lessonMoney1').html(fomatMoney(lessonMoney1,2)+" [元]");
		calTotalMoney();
	}
});

//计算讲课费2
$('#lessonTime2').numberbox({
	onChange:function(newValue,oldValue){
		var lessonMoney2=0;
		var num1= newValue;
		var num2= parseFloat($('#lessonStd2').val());
		if(!isNaN(num1) && !isNaN(num2)){
			lessonMoney2 = num1 * num2;
		}
		//$('#lessonMoney2').val(lessonMoney2);
		$('#lessonMoney2').numberbox('setValue',lessonMoney2.toFixed(2));
		//$('#p_lessonMoney2').html(fomatMoney(lessonMoney2,2)+" [元]");
		calTotalMoney();
	}
});
//计算讲课费3
$('#lessonTime3').numberbox({
	onChange:function(newValue,oldValue){
		var lessonMoney3=0;
		var num1= newValue;
		var num2= parseFloat($('#lessonStd3').val());
		if(!isNaN(num1) && !isNaN(num2)){
			lessonMoney3 = num1 * num2;
		}
		//$('#lessonMoney3').val(lessonMoney3);
		$('#lessonMoney3').numberbox('setValue',lessonMoney3.toFixed(2));
		//$('#p_lessonMoney3').html(fomatMoney(lessonMoney3,2)+" [元]");
		calTotalMoney();
	}
});
//计算总额
function calTotalMoney(){
	var totalMoney=0;
	var num1= parseFloat($('#hotelMoney').val());
	if(!isNaN(num1)){
		totalMoney = totalMoney + num1;
	}
	var num2= parseFloat($('#foodMoney').val());
	if(!isNaN(num2)){
		totalMoney = totalMoney + num2;
	}
	var num3= parseFloat($('#dataMoney').numberbox('getValue'));
	if(!isNaN(num3)){
		totalMoney = totalMoney + num3;
	}
	var num4= parseFloat($('#spaceMoney').numberbox('getValue'));
	if(!isNaN(num4)){
		totalMoney = totalMoney + num4;
	}
	var num5= parseFloat($('#transportMoney').numberbox('getValue'));
	if(!isNaN(num5)){
		totalMoney = totalMoney + num5;
	}
	var num6= parseFloat($('#otherMoney').numberbox('getValue'));
	if(!isNaN(num6)){
		totalMoney = totalMoney + num6;
	}
	var num7= parseFloat($('#lessonMoney1').numberbox('getValue'));
	if(!isNaN(num7)){
		totalMoney = totalMoney + num7;
	}
	var num8= parseFloat($('#lessonMoney2').val());
	if(!isNaN(num8)){
		totalMoney = totalMoney + num8;
	}
	var num9= parseFloat($('#lessonMoney3').val());
	if(!isNaN(num9)){
		totalMoney = totalMoney + num9;
	}
	var num10= parseFloat($('#longTrafficMoney').numberbox('getValue'));
	if(!isNaN(num10)){
		totalMoney = totalMoney + num10;
	}
	//给两个总额框赋值
	$('#applyAmount').val(totalMoney);
	$('#applyAmount_span').html(fomatMoney(totalMoney,2)+" [元]");
}
//初始化费用明细的输入框
function initMeetInput(){
	$("#dataMoney").numberbox({
		onChange: function(newValue, oldValue) {
			calTotalMoney();
		}
	});
	$("#spaceMoney").numberbox({
		onChange: function(newValue, oldValue) {
			calTotalMoney();
		}
	});
	$("#transportMoney").numberbox({
		onChange: function(newValue, oldValue) {
			calTotalMoney();
		}
	});
	$("#otherMoney").numberbox({
		onChange: function(newValue, oldValue) {
			calTotalMoney();
		}
	});
	$("#longTrafficMoney").numberbox({
		onChange: function(newValue, oldValue) {
			calTotalMoney();
		}
	});
}
</script>
