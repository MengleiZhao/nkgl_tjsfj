<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
</style>


<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 0px;">
	<tr class="trbody">
		<td class="td1" ><span class="style1">*</span> 会议名称</td>
		<td class="td2" colspan="3" >
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="meetingName" 
			value="${reimbMeetingBean.meetingName}" readonly="readonly" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
			
			

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 报到时间</td>
		<td class="td2" style="width: 281px;">
			<input class="easyui-datebox" style="width: 200px; height: 30px;" id="meetingDateStart" name="dateStart"
			data-options="onHidePanel:onHidepanel1" readonly="readonly" value="${reimbMeetingBean.dateStart}" required="required" editable="false"/>
		</td>

		<td class="td1"><span class="style1">*</span> 离开时间</td>
		<td class="td2">
			<input class="easyui-datebox" style="width: 200px; height: 30px;" id="meetingDateEnd" name="dateEnd"
			data-options="onHidePanel:onHidepanel" readonly="readonly" value="${reimbMeetingBean.dateEnd}" required="required" editable="false"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 会议天数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px; height: 30px;" id="duration" name="duration" 
			value="${reimbMeetingBean.duration}" readonly="readonly" required="required" data-options="iconCls:'icon-tian',validType:'length[1,2]'"/>
		</td>
			
		<td class="td1" ><span class="style1">*</span> 会议类型</td>
		<td class="td2" style="width: 200px;">
			<input id="meetingType" class="easyui-combobox" style="width: 200px; height: 30px;" name="meetingType" value="${reimbMeetingBean.meetingType}" readonly="readonly"
				data-options="prompt: '-请选择-' ,valueField: 'meetingType',textField: 'value',editable: false,
				data: [{meetingType: '1',value: '一类会议'},{meetingType: '2',value: '二类会议'},{meetingType: '3',value: '三类会议'},{meetingType: '4',value: '四类会议'}]"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 会议地点</td>
		<td colspan="3">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="meetingPlace" readonly="readonly"
			value="${reimbMeetingBean.meetingPlace}" required="required" data-options="prompt: '填写时地点精确到会议室房间号' ,validType:'length[1,50]'"/> 
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 主要参会人员</td>
		<td colspan="3">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="attendPeople" readonly="readonly"
			value="${reimbMeetingBean.attendPeople}" required="required" data-options="validType:'length[1,100]'"/>
		</td>
	</tr>


	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 参会人数</td>
		<td class="td2"><input class="easyui-numberbox" style="width: 200px; height: 30px;" name="attendNum" id="attendNum" readonly="readonly"
			value="${reimbMeetingBean.attendNum}" required="required" data-options="validType:'length[1,3]'"/>
		</td>

		<td class="td1" style="width:102px"><span class="style1" >*</span>其中工作人员人数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px; height: 30px;" readonly="readonly"
			id="staffNum" name="staffNum"
			value="${reimbMeetingBean.staffNum}" required="required" data-options="validType:'length[1,3]'"/> 
		</td>
	</tr>
		<tr style="height:5px;"></tr>
	<tr class="trbody">
		<td class="td1" valign="top"><p style="margin-top: 8px">会议内容</p></td>
		<td colspan="3">
			 <input class="easyui-textbox" id="contentId" data-options="multiline:true,required:false,validType:'length[0,250]'" readonly="readonly" name="content" style="width:590px;height:70px;" 
			value="${reimbMeetingBean.content}"> 
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" style="width: 80px;"><span class="style1">*</span>是否安排住宿</td>
		<td class="td2">
			<div>
				<input type="radio" value="1" name="fWAHotel" disabled="disabled" <c:if test="${reimbMeetingBean.fWAHotel=='1'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;是
				&nbsp;&nbsp;
				<input type="radio" value="0" name="fWAHotel" disabled="disabled" <c:if test="${reimbMeetingBean.fWAHotel=='0' || empty reimbMeetingBean.fWAHotel}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;否
			</div>
		</td>
		<td class="td1" style="width: 80px;"><span class="style1">*</span>是否安排伙食</td>
		<td class="td2">
			<div>
				<input type="radio" value="1" name="fWAFood" disabled="disabled" <c:if test="${reimbMeetingBean.fWAFood=='1'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;是
				&nbsp;&nbsp;
				<input type="radio" value="0" name="fWAFood" disabled="disabled" <c:if test="${reimbMeetingBean.fWAFood=='0' || empty reimbMeetingBean.fWAFood}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;否
			</div>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1" ><span class="style1">*</span>
			经办人</td>
		<td class="td2"><input class="easyui-textbox" id="userNames"
			name="userNames" readonly="readonly" value="${applyBean.userNames}"
			style="width: 200px;height: 30px;margin-left: 10px "></td>
		<td class="td1" ><span class="style1">*</span>
			部门名称</td>
		<td class="td2"><input class="easyui-textbox" id="deptName"
			name="deptName" readonly="readonly" value="${applyBean.deptName}"
			style="width: 200px;height: 30px;margin-left: 10px "></td>
	</tr>
</table>
<input type="hidden" id="hotelStd" />
<input type="hidden" id="foodStd" />
<input type="hidden" id="otherStd" />
<script type="text/javascript">
var startday2='${reimbMeetingBean.dateStart}';
var endday2='${reimbMeetingBean.dateEnd}';

/* $("#meetingDateEnd").datebox({
	onChange : function() {
		loadMeetStd();
    },
}); */

function onchange(){
	loadMeetStd();
}

function onHidepanel(){
	endday =  $('#meetingDateEnd').datetimebox('getValue');
	startday = $('#meetingDateStart').datetimebox('getValue');
	endday2 = new Date(endday);
	startday2 = new Date(startday);
	var d = (endday2 - startday2) / 86400000;
	if (d > 0) {
		$('#duration').numberbox("setValue", d+1);
	}else if (d < 0) {
		$('#meetingDateEnd').datetimebox('setValue','')
		alert("离开时间不得早于报道时间，请重新选择");
		$('#duration').numberbox("setValue", '');
	}else {
		$('#duration').numberbox("setValue", '');
	}
}
function onHidepanel1(){
	endday =  $('#meetingDateEnd').datetimebox('getValue');
	startday = $('#meetingDateStart').datetimebox('getValue');
	endday2 = new Date(endday);
	startday2 = new Date(startday);
	var d = (endday2 - startday2) / 86400000;
	if (d > 0) {
		$('#duration').numberbox("setValue", d+1);
	}else if (d < 0) {
		$('#meetingDateStart').datetimebox('setValue','')
		alert("离开时间不得早于报道时间，请重新选择");
		$('#duration').numberbox("setValue", '');
	}else {
		$('#duration').numberbox("setValue", '');
	}
}
$(document).ready(function() {
	loadMeetStd1()
//设置会议天数和参会人员的变更方法
$("#attendNum").numberbox({
	onChange: function(newValue, oldValue) {
		validateMeetStd()
		countStd()
	}
});

$("#staffNum").numberbox({
	onChange: function(newValue, oldValue) {
		validateMeetStd()
		countStd()
	}
});


 $("#meetingType").combobox({
	onChange: function(newValue, oldValue) {
		var Daynum =parseInt($('#duration').numberbox('getValue'));
		if(!isNaN(Daynum)){
			if(newValue=='1'|newValue=='2'|newValue=='3'){
				if(Daynum>2){
					alert('会议天数超过不能超过2天，请重新填写');
				}
			}else if(newValue=='4'){
				if(Daynum>1){
					alert('四类会议天数超过不能超过1天，请重新填写');
					$("#meetingType").combobox('setValue','');
				}
			}
		}
		validateMeetStd()
		loadMeetStd()
		countStd()
	}
}); 
 $('#duration').numberbox({
	 onChange: function(newValue, oldValue) {
		 if(newValue>2){
			 alert('会议天数超过不能超过2天，请重新填写');
			 $('#meetingDateEnd').datetimebox('setValue','');
			 $('#meetingDateStart').datetimebox('setValue','');
			 $('#duration').numberbox('setValue','');
		 }
		 countStd()
	 }
 })	
});


//加载费用标准
function loadMeetStd(){
	//var trainingType = parseInt($('#trainingType').combobox('getValue'));//参会人数
	
	var meetType = $('#meetingType').combobox('getValue');
	$.ajax({
		url : base + '/hotelStandard/getStd?applyType=meet&meetType='+meetType,
		type : 'get',
		async:false,
		dataType : 'json',
		success : function(map){
			for (var key in map) {
			/* 	alert(map['hotel'])
				alert(map['food'])
				alert(map['other'])
				 */
				if(key=='other'){
					$('#otherStd').val(map['other']);
				}if(key=='food'){
					$('#foodStd').val(map['food']);
				}if(key=='hotel'){
					$('#hotelStd').val(map['hotel']);
				}
			}
			
		}
	});
}

//修改时初始加载费用标准
function loadMeetStd1(){
	var meetType = '${reimbMeetingBean.meetingType}';
	$.ajax({
		url : base + '/hotelStandard/getStd?applyType=meet&meetType='+meetType,
		type : 'get',
		async:false,
		dataType : 'json',
		success : function(map){
			for (var key in map) {
			/* 	alert(map['hotel'])
				alert(map['food'])
				alert(map['other'])
				 */
				if(key=='other'){
					$('#otherStd').val(map['other']);
				}if(key=='food'){
					$('#foodStd').val(map['food']);
				}if(key=='hotel'){
					$('#hotelStd').val(map['hotel']);
				}
			}
			
		}
	});
}
//校验 会议人数标准
function validateMeetStd(){
	
	//会议类型
	var meetType = parseInt($('#meetingType').combobox('getValue'));
	//参会人数
	var meetPerson = parseInt($('#attendNum').numberbox('getValue'));
	//工作人员数量
	var meetWorker = parseInt($('#staffNum').numberbox('getValue'));
	if (!isNaN(meetType) && !isNaN(meetPerson) && !isNaN(meetWorker)) {
		if (meetType==2) {
			//二类会议参会人数不能超过200人，工作人员数不超过15%;
			if (meetPerson > 200) {
				alert("二类会议参会人数，不能超过200人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} else if (meetWorker > meetPerson * 0.15 && meetWorker > 9) {
				alert("二类会议工作人员人数，不能超过15%");
				$('#staffNum').numberbox('setValue','')
				return false;
			}
		} else if (meetType==3) {
			//三类会议参会人数不能超过150人，工作人员不超过10%
			if (meetPerson > 150) {
				alert("三类会议参会人数，不能超过150人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} else if (meetWorker > meetPerson * 0.1 && meetWorker > 9) {
				alert("三类会议工作人员人数，不能超过10%");
				$('#staffNum').numberbox('setValue','')
				return false;
			}
		} else if (meetType==4) {
			//四类会议参会人数不能超过50人，工作人员不超过10%；
			if (meetPerson > 50) {
				alert("四类会议参会人数，不能超过50人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} else if (meetWorker > meetPerson * 0.1 && meetWorker > 9) {
				alert("四类会议工作人员人数，不能超过10%");
				$('#staffNum').numberbox('setValue','')
				return false;
			}
		}
	}
	return true;
}
//校验 总金额 true代表通过
function validateTotalMoney(){
	var duration = $('#duration').numberbox('getValue');//天数
	var personNum = 0;//总人数
	var std1 = parseInt($('#p_hotelStd').html());
	var std2 = parseInt($('#p_foodStd').html());
	var std3 = parseInt($('#p_otherStd').html());
	var meetPerson = parseInt($('#attendNum').numberbox('getValue'));
	var meetWorker = parseInt($('#staffNum').numberbox('getValue'));
	if (!isNaN(meetPerson)) {
		personNum = personNum + meetPerson;
	}
	if (!isNaN(meetWorker)) {
		personNum = personNum + meetWorker;
	}
	var applyMoney = parseInt($('#applyAmount_span').html());
	if (!isNaN(applyMoney)) {
		if (!isNaN(std1) && !isNaN(std2) 
				&& !isNaN(personNum) 
				&& !isNaN(duration)) {
			var stdMoney = duration * personNum * (std1 + std2 + std3);
			if (applyMoney > stdMoney) {
				alert("申请总额大于支出标准，请重新核对！");
				return false;
			}
		}
	}
	return true;
}
//更新支出标准
function countStd(){
	var meet_num = parseInt($('#duration').numberbox('getValue'));//会议天数
	var meet_personNum = parseInt($('#attendNum').numberbox('getValue'));//参会人数
	var meet_workNum = parseInt($('#staffNum').numberbox('getValue'));//工作人数
	var num=0;
	if(isNaN(meet_workNum)){
		meet_workNum=parseInt(0);
	}
	if(isNaN(meet_num)){
		meet_num=parseInt(0);
	}
	if(isNaN(meet_personNum)){
		meet_personNum=parseInt(0);
	}
	if(!isNaN(meet_personNum)){
		num=num+meet_personNum;
	}
	if(!isNaN(meet_workNum)){
		num=num+meet_workNum;
	}
	var otherStd =parseFloat($('#otherStd').val());
	 var totalStandard1= otherStd*num*meet_num;
	 $('#reimb-meeting-mingxi').datagrid('updateRow',{
			index: 2,
			row: {
				costDetail: '其他费用',
				standard: otherStd,
				totalStandard: totalStandard1
			}
		});
	 var foodStd =parseFloat($('#foodStd').val())
	 var totalStandard2= foodStd*num*meet_num;
	 $('#reimb-meeting-mingxi').datagrid('updateRow',{
				index: 1,
				row: {
					costDetail: '伙食费',
					standard: foodStd,
					totalStandard: totalStandard2
				}
			});
	 var hotelStd =parseFloat($('#hotelStd').val())
	 var totalStandard3= hotelStd*num*meet_num;
	 $('#reimb-meeting-mingxi').datagrid('updateRow',{
				index: 0,
				row: {
					costDetail: '住宿费',
					standard: hotelStd,
					totalStandard: totalStandard3
				}
			});
}
	
</script>
