<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
</style>

<!-- 隐藏域主键 -->
<input type="hidden" name="mId" value="${meetingBean.mId}" />

<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 0px;">
	<tr class="trbody">
		<td class="td1" ><span class="style1">*</span> 会议名称</td>
		<td class="td2" colspan="3" >
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="meetingName" 
			value="${meetingBean.meetingName}" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 报到日期</td>
		<td class="td2" style="width: 281px;">
			<input class="easyui-datebox" style="width: 200px; height: 30px;" id="meetingDateStart" name="dateStart"
			data-options="" value="${meetingBean.dateStart}" required="required" editable="false"/>
		</td>

		<td class="td1"><span class="style1">*</span> 离开日期</td>
		<td class="td2">
			<input class="easyui-datebox" style="width: 200px; height: 30px;" id="meetingDateEnd" name="dateEnd"
			data-options="onSelect:onSelect2,onChange:onchange,onHidePanel:onHidepanel,showSeconds:true" value="${meetingBean.dateEnd}" required="required" editable="false"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 会议天数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px; height: 30px;" id="duration" name="duration" 
			value="${meetingBean.duration}" readonly="readonly" required="required" data-options="iconCls:'icon-tian',validType:'length[1,2]'"/>
		</td>
			
		<td class="td1" ><span class="style1">*</span> 会议类型</td>
		<td class="td2" style="width: 200px;">
			<input id="meetingType" class="easyui-combobox" style="width: 200px; height: 30px;" name="meetingType" value="${meetingBean.meetingType}"
				data-options="prompt: '-请选择-' ,valueField: 'meetingType',textField: 'value',editable: false,
				data: [{meetingType: '1',value: '一类会议'},{meetingType: '2',value: '二类会议'},{meetingType: '3',value: '三类会议'},{meetingType: '4',value: '四类会议'}]"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 会议地点</td>
		<td colspan="3">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="meetingPlace" 
			value="${meetingBean.meetingPlace}" required="required" data-options="prompt: '填写时地点精确到会议室房间号' ,validType:'length[1,50]'"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 主要参会人员</td>
		<td colspan="3">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="attendPeople"
			value="${meetingBean.attendPeople}" required="required" data-options="validType:'length[1,100]'"/>
		</td>
	</tr>


	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 参会人数</td>
		<td class="td2"><input class="easyui-numberbox" style="width: 200px; height: 30px;" name="attendNum" id="attendNum"
			value="${meetingBean.attendNum}" required="required" data-options="validType:'length[1,3]'"/>
		</td>

		<td class="td1" style="width:102px"><span class="style1">*</span>其中工作人员人数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px; height: 30px;" 
			id="staffNum" name="staffNum"
			value="${meetingBean.staffNum}" required="required" data-options="validType:'length[1,3]'"/> 
		</td>
	</tr>
	
	<tr style="height:5px;"></tr>

	<tr class="trbody">
		<td class="td1" valign="top"><p style="margin-top: 8px">会议内容</p></td>
		<td colspan="3">
			 <input class="easyui-textbox"data-options="multiline:true,required:false,validType:'length[0,250]'" name="content" style="width:590px;height:70px;" 
			value="${meetingBean.content}"> 
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1" style="width: 80px;"><span class="style1">*</span>是否安排住宿</td>
		<td class="td2">
			<input type="radio" value="1" onclick="reloadStd()" name="fWAHotel" id="boxHotel1" <c:if test="${meetingBean.fWAHotel=='1'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;是
			&nbsp;&nbsp;
			<input type="radio" value="0" onclick="onclickHotel()" name="fWAHotel" id="boxHotel2" <c:if test="${meetingBean.fWAHotel=='0' || empty meetingBean.fWAHotel}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;否
		</td>
		<td class="td1" style="width: 80px;"><span class="style1">*</span>是否安排伙食</td>
		<td class="td2">
			<input type="radio" value="1" onclick="reloadStd()" name="fWAFood" id="boxFood3" <c:if test="${meetingBean.fWAFood=='1'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;是
			&nbsp;&nbsp;
			<input type="radio" value="0" onclick="onclickFood()" name="fWAFood" id="boxFood4" <c:if test="${meetingBean.fWAFood=='0' || empty meetingBean.fWAFood}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;否
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" ><span class="style1">*</span>
			经办人</td>
		<td class="td2"><input class="easyui-textbox" id="userNames"
			name="userNames" readonly="readonly" value="${bean.userNames}"
			style="width: 200px;height: 30px;margin-left: 10px "></td>
		<td class="td1" ><span class="style1">*</span>
			部门名称</td>
		<td class="td2"><input class="easyui-textbox" id="deptName"
			name="deptName" readonly="readonly" value="${bean.deptName}"
			style="width: 200px;height: 30px;margin-left: 10px "></td>
	</tr>
</table>
<input type="hidden" id="hotelStd" />
<input type="hidden" id="foodStd" />
<input type="hidden" id="otherStd" />
<script type="text/javascript">
function reloadStd(){
	countStd();
	accept();
	editIndex = undefined;
}

function onclickHotel(){
	accept();
	var rows = $('#appli-detail-dg1').datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].costDetail=='住宿费'){
			$('#appli-detail-dg1').datagrid('updateRow',{
				index: i,
				row: {
					applySum:''
				}
			});
			editIndex = undefined;
		}
	}
	onLoadSuccessMeeting();
	countStd();
}
function onclickFood(){
	accept();
	var rows = $('#appli-detail-dg1').datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].costDetail=='伙食费'){
			$('#appli-detail-dg1').datagrid('updateRow',{
				index: i,
				row: {
					applySum:''
				}
			});
			editIndex = undefined;
		}
	}
	onLoadSuccessMeeting();
	countStd();
}
var startday2='${meetingBean.dateStart}';
var endday2='${meetingBean.dateEnd}';

$("#meetingDateStart").datebox({
    onSelect : function(beginDate){
    	startday2 = beginDate;
        $('#meetingDateEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    },
});
function onSelect2(date) {
	endday2 = date;
	startday2 = new Date(startday2);
}

function onchange(){
	loadMeetStd();
}

function onHidepanel(){
	endday =  $('#meetingDateEnd').datebox('getValue');
	startday = $('#meetingDateStart').datebox('getValue');
	endday2 = new Date(endday);
	startday2 = new Date(startday);
	var d = (endday2 - startday2) / 86400000;
	if(d == 0){
		$('#duration').numberbox("setValue", 1);
	}else if (d > 0) {
		$('#duration').numberbox("setValue", d+1);
	}else if (d < 0) {
		$('#meetingDateEnd').datebox().datebox('calendar')
		alert("离开时间不得早于报道时间，请重新选择");
	}else {
		$('#duration').numberbox("setValue", 0);
	}
}

$(document).ready(function() {
	loadMeetStd1();
//设置会议天数和参会人员的变更方法
$("#attendNum").numberbox({
	onChange: function(newValue, oldValue) {
		validateMeetStd();
		countStd();
	}
});

$("#staffNum").numberbox({
	onChange: function(newValue, oldValue) {
		validateMeetStd();
		countStd();
	}
});


 $("#meetingType").combobox({
	onChange: function(newValue, oldValue) {
		var Daynum =parseInt($('#duration').numberbox('getValue'));
		if(!isNaN(Daynum)){
			if(newValue=='2'||newValue=='3'||newValue=='4'){
				if(Daynum>2){
					alert('二、三、四类会议天数超过不能超过2天，请重新填写');
					$("#meetingType").combobox('setValue','');
				}
			}
		}
		validateMeetStd();
		loadMeetStd();
		countStd();
	}
}); 
 $('#duration').numberbox({
	 onChange: function(newValue, oldValue) {
		 var meetType = $('#meetingType').combobox('getValue');
		 if(meetType=='2'||meetType=='3'||meetType=='4'){
			 if(newValue>2){
				 alert('二、三、四类会议天数超过不能超过2天，请重新填写');
				 $('#meetingDateEnd').datebox('setValue','');
				 $('#meetingDateStart').datebox('setValue','');
				 $('#duration').numberbox('setValue','');
			 }
		 }
		 countStd();
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
	var meetType = '${meetingBean.meetingType}';
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
	if(!isNaN(meetPerson)&&!isNaN(meetType)){
		if (meetType==2) {
			//二类会议参会人数不能超过200人，工作人员数不超过15%;
			if (meetPerson > 200) {
				alert("二类会议参会人数，不能超过200人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} 
		} else if (meetType==3) {
			//三类会议参会人数不能超过150人，工作人员不超过10%
			if (meetPerson > 150) {
				alert("三类会议参会人数，不能超过150人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} 
		} else if (meetType==4) {
			//四类会议参会人数不能超过50人，工作人员不超过10%；
			if (meetPerson > 50) {
				alert("四类会议参会人数，不能超过50人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} 
		}
	}
	//工作人员数量
	var meetWorker = parseInt($('#staffNum').numberbox('getValue'));
	if (!isNaN(meetType) && !isNaN(meetPerson) && !isNaN(meetWorker)) {
		if (meetType==2) {
			//二类会议参会人数不能超过200人，工作人员数不超过15%;
			if (meetPerson > 200) {
				alert("二类会议参会人数，不能超过200人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} else if (meetWorker > meetPerson * 0.15 ) {
				alert("二类会议工作人员人数，不能超过参会人员的15%");
				$('#staffNum').numberbox('setValue','')
				return false;
			}
		} else if (meetType==3) {
			//三类会议参会人数不能超过150人，工作人员不超过10%
			if (meetPerson > 150) {
				alert("三类会议参会人数，不能超过150人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} else if (meetWorker > meetPerson * 0.1 ) {
				alert("三类会议工作人员人数，不能超过参会人员的10%");
				$('#staffNum').numberbox('setValue','')
				return false;
			}
		} else if (meetType==4) {
			//四类会议参会人数不能超过50人，工作人员不超过10%；
			if (meetPerson > 50) {
				alert("四类会议参会人数，不能超过50人!");
				$('#attendNum').numberbox('setValue','')
				return false;
			} else if (meetWorker > meetPerson * 0.1 ) {
				alert("四类会议工作人员人数，不能超过参会人员的10%");
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
	var num=0;
	if(isNaN(meet_num)){
		meet_num=parseInt(0);
	}
	if(isNaN(meet_personNum)){
		meet_personNum=parseInt(0);
	}
	if(!isNaN(meet_personNum)){
		num=num+meet_personNum;
	}
	var otherStd =parseFloat($('#otherStd').val());
	var totalStandard1= otherStd*num*meet_num;
	var isHotel = $('input[name="fWAHotel"]:checked').val();
	var isFood = $('input[name="fWAFood"]:checked').val();
	$('#appli-detail-dg1').datagrid('updateRow',{
			index: 2,
			row: {
				costDetail: '其他费用',
				standard: otherStd,
				totalStandard: totalStandard1
			}
	});
	var foodStd =parseFloat($('#foodStd').val());
	var totalStandard2= foodStd*num*meet_num;
	if(isFood==1){
		
		$('#appli-detail-dg1').datagrid('updateRow',{
					index: 1,
					row: {
						costDetail: '伙食费',
						standard: foodStd,
						totalStandard: totalStandard2
					}
		});
	}else{
		$('#appli-detail-dg1').datagrid('updateRow',{
			index: 1,
			row: {
				costDetail: '伙食费',
				standard: 0,
				totalStandard: 0
			}
		});
	}
	var hotelStd =parseFloat($('#hotelStd').val());
	var totalStandard3= hotelStd*num*(meet_num-1);
	if(isHotel==1){
		
		$('#appli-detail-dg1').datagrid('updateRow',{
					index: 0,
					row: {
						costDetail: '住宿费',
						standard: hotelStd,
						totalStandard: totalStandard3
					}
		});
	}else{
		$('#appli-detail-dg1').datagrid('updateRow',{
			index: 0,
			row: {
				costDetail: '住宿费',
				standard: 0,
				totalStandard: 0
			}
		});
	}
	editIndex = undefined;
}
	
</script>
