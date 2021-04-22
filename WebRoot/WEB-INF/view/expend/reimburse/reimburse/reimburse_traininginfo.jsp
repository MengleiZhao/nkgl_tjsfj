<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!-- 隐藏域主键 -->
<input type="hidden" id="hotelStd"  />
<input type="hidden" id="foodStd"  />
<input type="hidden" id="zongheStd"  />
<input type="hidden" id="otherStd"  />
<input type="hidden" id="lesson1Std"  />
<input type="hidden" id="lesson2Std"  />
<input type="hidden" id="lesson3Std"  />

<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 0px;">
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
			<input  class="easyui-datebox" style="width: 200px;; height: 30px;" id="trDateStart" name="trDateStart"
			data-options="" value="${reimbTrainingBean.trDateStart}" required="required" editable="false"/>
		</td>

		<td class="td4" style="width: 67px;"></td>

		<td class="td1"><span class="style1">*</span> 撤离日期</td>
		<td class="td2">
			<input class="easyui-datebox" style="width: 206px;; height: 30px;" id="trDateEnd" name="trDateEnd"
			data-options="onChange:onSelect4" value="${reimbTrainingBean.trDateEnd}" required="required" editable="false"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>培训天数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px;; height: 30px;" id="trDayNum" name="trDayNum" 
			value="${reimbTrainingBean.trDayNum}" readonly="readonly" required="required" 
			data-options="validType:'length[1,2]',icons: [{iconCls:'icon-tian'}]"/>
		</td>
		<td class="td4" style="width: 67px;"></td>
		<td class="td1"><span class="style1">*</span> 培训类别</td>
		<td class="td2">
			<input id="trainingType" class="easyui-combobox" required="required" style="width: 206px; height: 30px;" value="${reimbTrainingBean.trainingType}" name="trainingType"
			data-options="valueField: 'trainingType',textField: 'value',editable: false,
				data: [{trainingType: '1',value: '一类培训'},{trainingType: '2',value: '二类培训'},{trainingType: '3',value: '三类培训'}]"/> 
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 培训地点</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="trPlace" id="trPlace"
			value="${reimbTrainingBean.trPlace}" required="required" data-options="prompt: '填写时地点精确到会议室房间号' ,validType:'length[1,50]'"/>
		</td>
	</tr>


	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 主要参训人员</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="trAttendPeop" id="trAttendPeop"
			value="${reimbTrainingBean.trAttendPeop}" required="required" data-options="validType:'length[1,100]'"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 参训人数</td>
		<td class="td2">
			<input  class="easyui-numberbox" style="width: 200px; height: 30px;" name="trAttendNum" id="trAttendNum"
			value="${reimbTrainingBean.trAttendNum}" required="required" data-options="validType:'length[1,3]'"/>
		</td>

		<td class="td4" style="width: 67px;"></td>

		<td class="td1">工作人员人数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 206px; height: 30px;" id="trStaffNum" name="trStaffNum"
			value="${reimbTrainingBean.trStaffNum}" data-options="validType:'length[1,3]'" />
		</td>
	</tr>

	<tr class="trbody">
			<td class="td1"><span class="style1">*</span>是否安排住宿</td>
			<td class="td2">
				<input  name="isHotel1" value="1"
					type="radio" onclick="selectHotel('1')" disabled="disabled" <c:if test="${reimbTrainingBean.isHotel==1 }">checked="checked"</c:if>/>是
				<input  name="isHotel1" value="0"
					type="radio" onclick="selectHotel('0')" disabled="disabled" <c:if test="${reimbTrainingBean.isHotel!=1 }">checked="checked"</c:if>/>否
			</td>	
			<td class="td4" style="width: 67px;"></td>
			<td class="td1"><span class="style1">*</span>是否安排伙食</td>
			<td class="td2">
				<input  name="isFood1" value="1"
					type="radio" onclick="selectFood('1')" disabled="disabled" <c:if test="${reimbTrainingBean.isFood==1 }">checked="checked"</c:if>/>是
				<input  name="isFood1" value="0"
					type="radio" onclick="selectFood('0')" disabled="disabled" <c:if test="${reimbTrainingBean.isFood!=1 }">checked="checked"</c:if>/>否
			</td>	
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>
			经办人</td>
		<td class="td2"><input class="easyui-textbox" id="userNames"
			name="userNames" readonly="readonly" value="${applyBean.userNames}"
			style="width: 200px;height: 30px; "></td>
		<td class="td4" style="width: 67px;"></td>
		<td class="td1"><span class="style1">*</span>
			部门名称</td>
		<td class="td2"><input class="easyui-textbox" id="deptName"
			name="deptName" readonly="readonly" value="${applyBean.deptName}"
			style="width: 206px;height: 30px; "></td>
	</tr>
</table>


<script type="text/javascript">
function selectHotel(type){
	$('#isHotel').val(type);
	countStd();
	var isHotel = $('input[name="isHotel1"]:checked').val();
	if(isHotel=='0'){
		 $('#mingxi-zonghe-dg').datagrid('updateRow',{
				index: 0,
				row: {
					costDetail: '住宿费',
					standard: 0,
					totalStandard: 0,
					remibAmount:0
				}
			});
	 };
}
function selectFood(type){
	$('#isFood').val(type);
	countStd();
	var isFood = $('input[name="isFood1"]:checked').val();
	 if(isFood=='0'){
		 $('#mingxi-zonghe-dg').datagrid('updateRow',{
				index: 1,
				row: {
					costDetail: '伙食费',
					standard: 0,
					totalStandard: 0,
					remibAmount:0
				}
			});
	 };
}
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

var startday3='${reimbTrainingBean.trDateStart}';
var endday3='${reimbTrainingBean.trDateEnd}';
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
        $('#trDateEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
	                return beginDate <= date;
            }
        });
    }
}); 


function onSelect4(){
	endday3 = new Date($("#trDateEnd").datebox('getValue'));
	startday3 =new Date(startday3);
	var d = (endday3-startday3)/86400000+1;
	if(d<=92&&d>0){
		$('#trDayNum').numberbox("setValue",d);
		$('#personDay1').val(d);
		$('#personDay2').val(d);
	}else if(d>92){
		alert('培训天数不能超过92天');
		$("#trDateStart").datebox('setValue','')
		$("#trDateEnd").datebox('setValue','')
		$("#trDayNum").numberbox('setValue','')
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
//修改时进页面加载标准
function loadTrainStd1(){
	
	var trainType = '${reimbTrainingBean.trainingType}';
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
		var totalStandard1= 0;
		if(train_num>30){
			  totalStandard1= otherStd*num*30+otherStd*num*(train_num-30)*0.7
		}else{
			 totalStandard1= otherStd*num*train_num;
		}
		var isHotel = $('#isHotel').val();
		var isFood = $('#isFood').val();
		 $('#mingxi-zonghe-dg').datagrid('updateRow',{
				index: 3,
				row: {
					costDetail: '其他费用',
					standard: otherStd,
					totalStandard: totalStandard1
				}
			});
		 var foodStd =parseFloat($('#foodStd').val());
		 var totalStandard2=0;
		 if(train_num>30){
			  totalStandard2= foodStd*num*30+foodStd*num*(train_num-30)*0.7
		}else{
			 totalStandard2= foodStd*num*train_num;
		}
		 if(isFood=='0'){
			 $('#mingxi-zonghe-dg').datagrid('updateRow',{
					index: 1,
					row: {
						costDetail: '伙食费',
						standard: 0,
						totalStandard: 0
					}
				});
		 }else{
			 
			 $('#mingxi-zonghe-dg').datagrid('updateRow',{
						index: 1,
						row: {
							costDetail: '伙食费',
							standard: foodStd,
							totalStandard: totalStandard2
						}
					});
		 }
		 var hotelStd =parseFloat($('#hotelStd').val());
		 var totalStandard3=0;
		 if(train_num==0){
			 totalStandard3= hotelStd*num*train_num;
		 }else if(train_num<=30){
		      totalStandard3= hotelStd*num*(train_num-1);
		 }else if(train_num>30){
			   totalStandard3= hotelStd*num*30+hotelStd*num*(train_num-31)*0.7;
		 }
		 if(isHotel=='0'){
			 $('#mingxi-zonghe-dg').datagrid('updateRow',{
					index: 0,
					row: {
						costDetail: '住宿费',
						standard: 0,
						totalStandard: 0
					}
				});
		 }else{
			 
			 $('#mingxi-zonghe-dg').datagrid('updateRow',{
						index: 0,
						row: {
							costDetail: '住宿费',
							standard: hotelStd,
							totalStandard: totalStandard3
						}
					});
		 }
		 var zongheStd =parseFloat($('#zongheStd').val());
		 var totalStandard4=0;
		 if(train_num>30){
			  totalStandard4= zongheStd*num*30+zongheStd*num*(train_num-30)*0.7
		}else{
			 totalStandard4= zongheStd*num*train_num;
		}
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
	loadTrainStd1()
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
		var data = new Array();
		$('#dg_train_plan').datagrid('loadData',data);
	}
});
</script>
