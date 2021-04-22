<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!-- 隐藏域主键 -->
<input type="hidden" name="tId" value="${trainingBean.tId}" />
<input type="hidden" id="hotelStd"  />
<input type="hidden" id="foodStd"  />
<input type="hidden" id="zongheStd"  />
<input type="hidden" id="otherStd"  />
<input type="hidden" id="lesson1Std"  />
<input type="hidden" id="lesson2Std"  />
<input type="hidden" id="lesson3Std"  />
<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 0px;margin-left: 3px;">
	<tr class="trbody">
		<td class="td1" style=""><span class="style1">*</span> 培训名称</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 580px; height: 30px;" name="trainingName" readonly="readonly"
			value="${trainingBean.trainingName}" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" style=""><span class="style1">*</span> 培训目的</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 580px; height: 30px;" name="trContent" readonly="readonly"
			value="${trainingBean.trContent}" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 报到日期</td>
		<td class="td2">
			<input  class="easyui-datebox" style="width: 200px;; height: 30px;" id="trDateStart" name="trDateStart" readonly="readonly"
			data-options="" value="${trainingBean.trDateStart}" required="required" editable="false"/>
		</td>

		<td class="td4" style="width: 67px;"></td>

		<td class="td1"><span class="style1">*</span> 撤离日期</td>
		<td class="td2">
			<input class="easyui-datebox" style="width: 200px;; height: 30px;" id="trDateEnd" name="trDateEnd" readonly="readonly"
			data-options="onSelect:onSelect4" value="${trainingBean.trDateEnd}" required="required" editable="false"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>培训天数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px;; height: 30px;" id="trDayNum" name="trDayNum" 
			value="${trainingBean.trDayNum}" readonly="readonly" required="required" 
			data-options="validType:'length[1,2]',icons: [{iconCls:'icon-tian'}]"/>
		</td>
		<td class="td4" style="width: 67px;"></td>
		<td class="td1"><span class="style1">*</span> 培训类别</td>
		<td class="td2">
			<input id="trainingType" class="easyui-combobox" required="required" readonly="readonly" style="width: 200px; height: 30px;" value="${trainingBean.trainingType}" name="trainingType"
			data-options="valueField: 'trainingType',textField: 'value',editable: false,
				data: [{trainingType: '1',value: '一类培训'},{trainingType: '2',value: '二类培训'},{trainingType: '3',value: '三类培训'}]"/> 
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 培训地点</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 580px; height: 30px;" name="trPlace"readonly="readonly"
			value="${trainingBean.trPlace}" required="required" data-options="prompt: '填写时地点精确到会议室房间号' ,validType:'length[1,50]'"/>
		</td>
	</tr>


	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 主要参训人员</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 580px; height: 30px;" name="trAttendPeop" readonly="readonly"
			value="${trainingBean.trAttendPeop}" required="required" data-options="validType:'length[1,100]'"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 参训人数</td>
		<td class="td2">
			<input  class="easyui-numberbox" style="width: 200px; height: 30px;" name="trAttendNum" id="trAttendNum" readonly="readonly"
			value="${trainingBean.trAttendNum}" required="required" data-options="validType:'length[1,3]'"/>
		</td>

		<td class="td4" style="width: 67px;"></td>

		<td class="td1">工作人员人数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px; height: 30px;" id="trStaffNum" name="trStaffNum" readonly="readonly"
			value="${trainingBean.trStaffNum}" data-options="validType:'length[1,3]'"/>
		</td>
	</tr>
	<tr class="trbody">
			<td class="td1"><span class="style1">*</span>是否安排住宿</td>
			<td class="td2">
				<input  name="isHotel" value="1"
					type="radio"  disabled="disabled" <c:if test="${trainingBean.isHotel==1 }">checked="checked"</c:if>/>是
				<input  name="isHotel" value="0"
					type="radio"   disabled="disabled" <c:if test="${trainingBean.isHotel!=1 }">checked="checked"</c:if>/>否
			</td>	
			<td class="td4" style="width: 67px;"></td>
			<td class="td1"><span class="style1">*</span>是否安排伙食</td>
			<td class="td2">
				<input  name="isFood" value="1"
					type="radio"  disabled="disabled" <c:if test="${trainingBean.isFood==1 }">checked="checked"</c:if>/>是
				<input  name="isFood" value="0"
					type="radio"  disabled="disabled" <c:if test="${trainingBean.isFood!=1 }">checked="checked"</c:if>/>否
			</td>	
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>
			经办人</td>
		<td class="td2"><input class="easyui-textbox" id="userNames"
			name="userNames" readonly="readonly" value="${bean.userNames}"
			style="width: 200px;height: 30px; "></td>
		<td class="td4" style="width: 67px;"></td>
		<td class="td1"><span class="style1">*</span>
			部门名称</td>
		<td class="td2"><input class="easyui-textbox" id="deptName"
			name="deptName" readonly="readonly" value="${bean.deptName}"
			style="width: 200px;height: 30px; "></td>
	</tr>
</table>


<script type="text/javascript">
/* //初始化-培训类型选择框
initComboTrainingType();
//初始化-报到时间/撤离时间输入框
initComboboxDateStart();
initComboboxDateEnd(); */

//计算总人数
function addPersonNum(){
	var totalNum =0;
	var num1 =parseInt($("#trAttendNum").numberbox('getValue'));
	var num2 =parseInt($("#trStaffNum").numberbox('getValue'));
	if(!isNaN(num1)){
		totalNum =totalNum+num1;
	}
	if(!isNaN(num2)){
		totalNum =totalNum+num2;
	}
	$('#personNum1').val(totalNum);
	$('#personNum2').val(totalNum);
}

var startday3='${trainingBean.trDateStart}';
var endday3='${trainingBean.trDateEnd}';
$("#trDateStart").datebox({
    onSelect : function(beginDate){
    	startday3 = beginDate;
    	endday3 =new Date(endday3);
    	var d = (endday3-startday3)/86400000+1;
    	
    	if(d>0){
    		$('#trDayNum').numberbox("setValue",d);
    		$('#personDay2').val(d);
    		$('#personDay2').val(d);
    	} else {
    		$('#trDayNum').numberbox("setValue", "");
    		$('#personDay2').val("");
    		$('#personDay2').val("");
    	}
        $('#trDateEnd').datebox().datetimebox('calendar').calendar({
            validator: function(date){
	                return beginDate <= date;
            }
        });
    }
}); 


function onSelect4(date){
	endday3 = date;
	startday3 =new Date(startday3);
	var d = (endday3-startday3)/86400000+1;
	if(d>0){
		$('#trDayNum').numberbox("setValue",d);
		$('#personDay1').val(d);
		$('#personDay2').val(d);
	} else {
		$('#trDayNum').numberbox("setValue", "");
		$('#personDay1').val("");
		$('#personDay2').val("");
	}
}

function loadTrainStd(){
	var trainType = $('#trainingType').combobox('getValue');
	$.ajax({
		url : base + '/hotelStandard/getStd?applyType=train&meetType='+trainType,
		type : 'post',
		dataType : 'json',
		success : function(map){
			for (var key in map) {
				if(key=='food'){
				$('#foodStd').val(map['food']);
				}if(key=='hotel'){
				$('#hotelStd').val(map['hotel']);
				}if(key=='zonghe'){
				$('#zongheStd').val(map['zonghe']);
				}if(key=='other'){
				$('#otherStd').val(map['other']);
				}if(key=='lesson1'){
				$('#lesson1Std').val(map['lesson1']);
				}if(key=='lesson2'){
				$('#lesson2Std').val(map['lesson2']);
				}if(key=='lesson3'){
				$('#lesson3Std').val(map['lesson3']);
				}
			}
			countStd()
		}
	});
}

//更新支出标准
function countStd(){
	var trainType=$('#trainingType').combobox('getValue');
	if(trainType!=''){
		var train_num = parseInt($('#trDayNum').numberbox('getValue'));//培训天数
		var train_personNum = parseInt($('#trAttendNum').numberbox('getValue'));//参会人数
		var train_workNum = parseInt($('#trStaffNum').numberbox('getValue'));//工作人数
		var num=0;
		if(isNaN(train_workNum)){
			train_workNum=parseInt(0);
		}
		if(isNaN(train_num)){
			train_num=parseInt(0);
		}
		if(isNaN(train_personNum)){
			train_personNum=parseInt(0);
		}
		if(!isNaN(train_personNum)){
			num=num+train_personNum;
		}
		if(!isNaN(train_workNum)){
			num=num+train_workNum;
		}
		var otherStd =parseFloat($('#otherStd').val());
		 var totalStandard1= otherStd*num*train_num;
		 $('#mingxi-zonghe-dg').datagrid('updateRow',{
				index: 3,
				row: {
					costDetail: '其他费用',
					standard: otherStd,
					totalStandard: totalStandard1
				}
			});
		 var foodStd =parseFloat($('#foodStd').val())
		 var totalStandard2= foodStd*num*train_num;
		 $('#mingxi-zonghe-dg').datagrid('updateRow',{
					index: 1,
					row: {
						costDetail: '伙食费',
						standard: foodStd,
						totalStandard: totalStandard2
					}
				});
		 var hotelStd =parseFloat($('#hotelStd').val())
		 if(train_num==0){
			var totalStandard3= hotelStd*num*train_num;
		 }else{
		 var totalStandard3= hotelStd*num*(train_num-1);
		 }
		 $('#mingxi-zonghe-dg').datagrid('updateRow',{
					index: 0,
					row: {
						costDetail: '住宿费',
						standard: hotelStd,
						totalStandard: totalStandard3
					}
				});
		 var zongheStd =parseFloat($('#zongheStd').val())
		 var totalStandard4= zongheStd*num*train_num;
		 $('#mingxi-zonghe-dg').datagrid('updateRow',{
					index: 2,
					row: {
						costDetail: '场地、资料、交通费',
						standard: zongheStd,
						totalStandard: totalStandard4
					}
				});
	}
}

$(document).ready(function() {
});



//选择培训类型后，触发事件
function initComboTrainingType(){
	//trainingType
	$('#trainingType').combobox({
		onSelect : function(rec) {
			var trainingType = rec.trainingType;
			if (trainingType==1) {
				alert("系统提示：一类会议是指参训人员主要为市级干部及响应人员的培训项目");
			} else if (trainingType==2) {
				alert("系统提示：二类会议是指参训人员主要为局级干部的培训项目");
			} else if (trainingType==3) {
				alert("系统提示：三类会议是指参训人员主要为处级以下干部的培训项目");
			}
		}
	});
}
//trDateStart trDateEnd
function initComboboxDateStart(){
	$("#trDateStart").datetimebox({
		onSelect : function(value) {
			var dates = new Date(value);
			var datee = new Date($('#trDateEnd').datetimebox('getValue'));
		}
	});
}
function initComboboxDateEnd(){
	$("#trDateEnd").datetimebox({
		onSelect : function(value) {
			var dates = new Date($('#trDateStart').datetimebox('getValue'));
			var datee = new Date(value);
			//alert(dates)
			//alert(datee)
			var d = (datee-dates)/86400000+1;
			$('#dayLong').numberbox('setValue',d);
		}
	});
}
$("#trainingType").combobox({
	onChange : function(newValue, oldValue) {
		
		loadTrainStd();
	}
});


$("#trAttendNum").numberbox({
	onChange: function(newValue, oldValue) {
		countStd()
	}
});

$("#trStaffNum").numberbox({
	onChange: function(newValue, oldValue) {
		countStd()
	}
});

$("#trDayNum").numberbox({
	onChange: function(newValue, oldValue) {
		countStd()
	}
});
</script>
<!-- 校验专用js 通过返回true 不通过返回false -->
<script type="text/javascript">
//校验培训计划
function validateTrainPlan(){
	//1、时间安排不能超出撤离和报到时间 2、半天按4小时算
	//acceptChanges
	var times = $('#trDateStart').datetimebox('getValue');
	var timee = $('#trDateEnd').datetimebox('getValue');
	$('#dg_train_plan').datagrid('acceptChanges');
	var rows = $('#dg_train_plan').datagrid('getRows');
	if (rows.length > 0) {
		for(var i = 0 ; i < rows.length; i++){
			var time1 = rows[i].times;
			var time2 = rows[i].timee;
			if (time1 > time2) {
				alert("计划起始时间不能大于结束时间，请核对培训计划！");
				beginAllEdit();
				return false;
			}
			if (time1 < times || time1 > timee ) {
				alert('培训计划时间安排应在"报到时间"和"撤离时间"之间，请核对培训计划！');
				beginAllEdit();
				return false;
			}
			if (time2 < times || time2 > timee ) {
				alert('培训计划时间安排应在"报到时间"和"撤离时间"之间，请核对培训计划！');
				beginAllEdit();
				return false;
			} 
		}
	}
	return true;
}
//培训资料费、培训场地费、交通费 限额控制 培训类型  1、2 80元 3 60元/人天
function validateTrainCost(){
	alert(1)
	var trainType = $('#trainingType').combobox('getValue');//培训类型
	var personNum = 0;//培训总人数
	var costTrain = 0;//培训资料费
	var costPlace = 0;//场地费
	var costTraffic = 0;//交通费
	//计算人数，各项费用
	var cost1 = parseFloat($('#bookCost').numberbox('getValue'));
	var cost2 = parseFloat($('#placeCost').numberbox('getValue'));
	var cost3 = parseFloat($('#trafficCost').numberbox('getValue'));
	var personNum1 = parseInt($('#trAttendNum').numberbox('getValue'));
	var personNum2 = parseInt($('#trStaffNum').numberbox('getValue'));
	if (!isNaN(cost1)) {costTrain = cost1;}
	if (!isNaN(cost2)) {costPlace = cost2;}
	if (!isNaN(cost3)) {costTraffic = cost3;}
	if (!isNaN(personNum1)) {personNum = personNum + personNum1;}
	if (!isNaN(personNum2)) {personNum = personNum + personNum2;}
	//计算标准
	var standard = 0;
	if(trainType==1 || trainType==2){
		standard = 80;
	} else if (trainType == 3) {
		standard = 60;
	}
	if (standard == 0) {
		alert("请选择培训类别！");
		return false;
	}
	var total1 = standard * personNum;
	var total2 = costTrain + costPlace + costTraffic;
	if (total2 > total1) {
		alert("‘培训资料费、培训场地费、交通费’三项费用的合计金额不能超过限额，请再次确认！（一、二类培训限额为80 元/人天，三类培训限额为60 元/人天）");
		return false;
	}
	return true;
}
//校验“其他费用”
function validateTrainCost(){
	var costOther = parseFloat($('#otherCost').numberbox('getValue'));
	var personNum = 0;//培训总人数
	var personNum1 = parseInt($('#trAttendNum').numberbox('getValue'));
	var personNum2 = parseInt($('#trStaffNum').numberbox('getValue'));
	if (!isNaN(personNum1)) {personNum = personNum + personNum1;}
	if (!isNaN(personNum2)) {personNum = personNum + personNum2;}
	if (personNum*30 < costOther) {
		alert("‘其他费用’的标准限制额度为30元/人天，请再次确认！");
		return false;
	}
	return true;
}
//校验申请总额 人均合计金额，一类650，二类550，三类500
//让datagrid恢复编辑状态
function validateTrainTotal(){
	//培训类型
	var trainType = $('#trainingType').combobox('getValue');
	//申请总额
	var totalMoney = 0;
	var money1 = parseFloat($('#applyAmount_span').html());
	if (!isNaN(money1)) {
		totalMoney = money1;
	}
	//培训总人数
	var personNum = 0;
	var personNum1 = parseInt($('#trAttendNum').numberbox('getValue'));
	var personNum2 = parseInt($('#trStaffNum').numberbox('getValue'));
	if (!isNaN(personNum1)) {personNum = personNum + personNum1;}
	if (!isNaN(personNum2)) {personNum = personNum + personNum2;}
	if (trainType == 1) {
		if (totalMoney > personNum*650) {
			alert("申请总金额不可超过人均合计金额，请再次核对！");
			return false;
		}
	} else if (trainType == 2) {
		if (totalMoney > personNum*550) {
			alert("申请总金额不可超过人均合计金额，请再次核对！");
			return false;
		}
	} else if (trainType == 3) {
		if (totalMoney > personNum*500) {
			alert("申请总金额不可超过人均合计金额，请再次核对！");
			return false;
		}
	} else {
		alert("请选择培训类别！");
		return false;
	}
	return true;
}
</script>
