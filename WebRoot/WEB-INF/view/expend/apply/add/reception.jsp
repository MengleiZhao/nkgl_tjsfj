<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
	<tr class="trbody">
		<td class="td1" style="width: 70px"><span class="style1">*</span> 接待对象单位</td>
		<td class="td2">
			<input class="easyui-textbox" style="width: 230px; height: 30px;" name="receptionObject"
			value="${receptionBean.receptionObject}" data-options="required:true,validType:'length[1,25]'"/>
		</td>
		<td class="td1"><span class="style1">*</span> 接待对象人数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 238px; height: 30px;" name="rePeopNum" id="rePeopNum"
			value="${receptionBean.rePeopNum}" data-options="required:true,validType:'length[1,5]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" style="width: 70px"><span class="style1">*</span>开始日期</td>
		<td class="td2">
			<input style="width: 230px; height: 30px;" id="reDateStart"
			name="reDateStart" class="easyui-datebox"
			data-options="required:true"
			value="${receptionBean.reDateStart}" editable="false"/>
		</td>
		<td class="td1" style="width: 70px">结束日期</td>
		<td class="td2">
			<input style="width: 238px; height: 30px;" id="reDateEnd"
			name="reDateEnd" class="easyui-datebox"
			data-options="onSelect:onSelect8,required:true" value="${receptionBean.reDateEnd}" editable="false"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" style="width: 70px"><span class="style1">*</span> 接待类型</td>
		<td class="td2">
			<select id="receptionLevel" class="easyui-combobox"
			name="receptionLevel" style="width: 230px; height: 30px;" data-options="required:true" editable="false">
				<option value="1" <c:if test="${receptionBean.receptionLevel=='1'}"> selected="selected" </c:if>>出席会议</option>
				<option value="2" <c:if test="${receptionBean.receptionLevel=='2'}"> selected="selected" </c:if>>考察调研</option>
				<option value="3" <c:if test="${receptionBean.receptionLevel=='3'}"> selected="selected" </c:if>>执行任务</option>
				<option value="4" <c:if test="${receptionBean.receptionLevel=='4'}"> selected="selected" </c:if>>学习交流</option>
				<option value="5" <c:if test="${receptionBean.receptionLevel=='5'}"> selected="selected" </c:if>>检查指导</option>
				<option value="6" <c:if test="${receptionBean.receptionLevel=='6'}"> selected="selected" </c:if>>请示汇报工作</option>
				<option value="7" <c:if test="${receptionBean.receptionLevel=='7'}"> selected="selected" </c:if>>其他</option>
			</select>
		</td>
		<td class="td1"><span class="style1">*</span> 是否安排住宿</td>
		<td class="td2">
			<input type="radio" onclick="stayYNCheck1()" value="1" name="box" id="box1" <c:if test="${receptionBean.stayYN=='1'}">checked="checked" </c:if> />是
			<input type="radio" onclick="stayYNCheck2()" value="0" name="box" id="box2" <c:if test="${receptionBean.stayYN=='0'}">checked="checked" </c:if> />否
			<input type="hidden" id="stayYN" name="stayYN"  value="${receptionBean.stayYN}"/>
		</td>
	</tr>

	<%-- <tr class="trbody">
		<td class="td1" style="width: 70px"><span class="style1">*</span> 接待天数</td>
		<td class="td2">
			<input style="width: 230px; height: 30px;" id="reDayNum"
			name="reDayNum" class="easyui-numberbox"
			value="${receptionBean.reDayNum}" readonly="readonly" data-options="required:true,validType:'length[1,2]'"/>
		</td>		
		<td class="td1" style="width: 70px"><span class="style1">*</span> 就餐安排</td>
		<td class="td2">
			<input type="checkbox" onclick="stayYNCheck1()" value="宴请" name="box" id="box1" <c:if test="${receptionBean.diningPlacePlan1=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>宴请
			<input type="hidden" id="diningPlacePlan1" name="diningPlacePlan1" value="${receptionBean.diningPlacePlan1}" />
			<input type="checkbox" onclick="stayYNCheck2()" value="工作餐" name="box" id="box2" <c:if test="${receptionBean.diningPlacePlan2=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>工作餐
			<input type="hidden" id="diningPlacePlan2" name="diningPlacePlan2"  value="${receptionBean.diningPlacePlan2}"/>
		</td>		
	</tr>

	<tr class="trbody">
		<td class="td1" style="width: 70px"><span class="style1">*</span>就餐安排</td>
		<td class="td2" colspan="3" >
			<input type="checkbox" onclick="someCheck1()" value="宴请" name="box" id="box1" <c:if test="${receptionBean.diningPlacePlan1=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>宴请
			<input type="hidden" id="diningPlacePlan1" name="diningPlacePlan1" value="${receptionBean.diningPlacePlan1}" />
			<input type="checkbox" onclick="someCheck2()" value="工作餐" name="box" id="box2" <c:if test="${receptionBean.diningPlacePlan2=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>工作餐
			<input type="hidden" id="diningPlacePlan2" name="diningPlacePlan2"  value="${receptionBean.diningPlacePlan2}"/>
			<input type="checkbox" onclick="someCheck3()" value="自助餐" name="box" id="box3" <c:if test="${receptionBean.diningPlacePlan3=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>自助餐
			<input type="hidden" id="diningPlacePlan3" name="diningPlacePlan3" value="${receptionBean.diningPlacePlan3}" />
			<input type="checkbox" onclick="someCheck4()" value="份饭" name="box" id="box4" <c:if test="${receptionBean.diningPlacePlan4=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>份饭
			<input type="hidden" id="diningPlacePlan4" name="diningPlacePlan4"  value="${receptionBean.diningPlacePlan4}"/>
			<input type="checkbox" onclick="someCheck5()" value="其他" name="box" id="box5" <c:if test="${receptionBean.diningPlacePlan5=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>其他
			<input type="hidden" id="diningPlacePlan5" name="diningPlacePlan5"  value="${receptionBean.diningPlacePlan5}"/>
		</td>

		<!-- <td class="td4" style="width: 67px;"></td> -->
	</tr> --%>
	<tr class="trbody">
		<td class="td1"  style="width: 70px"><span class="style1">*</span> 经办人</td>
		<td class="td2" >
		<input class="easyui-textbox" id="userNames" name="userNames" readonly="readonly" value="${bean.userNames}" style="width: 230px;height: 30px;margin-left: 10px " >
		</td>
		<td class="td1"  style="width: 70px"><span class="style1">*</span> 部门名称</td>
		<td class="td2" >
		<input class="easyui-textbox" id="deptName" name="deptName" readonly="readonly" value="${bean.deptName}" style="width: 238px;height: 30px;margin-left: 10px " >
		</td>
	</tr>
	<tr style="height: 70px;">
		<td class="td1" style="width: 70px;"><span class="style1">*</span>接待内容</td>
		<td colspan="3">
			<textarea name="receptionContent"  id="receptionContent" class="textbox-text"  oninput="textareaNum(this,'textareaNum2')" autocomplete="off" 
			  style="width:600px;height:60px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:5px; margin-bottom:0px;">${receptionBean.receptionContent}</textarea>
		</td>
	</tr>
<input  type="hidden" id="totalPerson" />
<script type="text/javascript">

var yanqing ="";
var zhengcan ="";
var zaocan ="";

//支出标准获取
function zcbz(){
	$.ajax({
		url: base+ "/hotelStandard/calcCost?outType=recep",
		type : 'post',
		async : true,
		dataType:'json',
	    contentType:'application/json;charset=UTF-8',
		success : function(json) {
			for (var i = 0; i < json.length; i++) {
				var costDetail = json[i].costDetail;
				if(costDetail=="宴请"){
					yanqing =parseInt(json[i].standard);
				}
				if(costDetail=="早餐"){
					zaocan =parseInt(json[i].standard);
				}
				if(costDetail=="正餐"){
					zhengcan =parseInt(json[i].standard);
				}
			}
		}		
	});
}
function stayYNCheck1(){
	$('#stayYN').val(1);
   	$('#rec-hotel-div').show();
   	$.parser.parse('#rec-hotel-div');
}
function stayYNCheck2(){
	$('#stayYN').val(0);
   	$('#rec-hotel-div').hide();
	
}
/* function stayYNCheck1() {
	
    if($('#box1').is(':checked')){
    	$('#tr1').show();
    	$('#diningPlacePlan1').val(1);
    	
    	var unitFeteNum =parseInt($('#unitFeteNum').numberbox('getValue'));
    	var attendPeopNum =parseInt($('#attendPeopNum').numberbox('getValue'));
    	if (isNaN(unitFeteNum)) {
    		return false;
    	}
    	if (isNaN(attendPeopNum)) {
    		return false;
    	}
    	if (endEditing1()){
        	$('#rec-food-dg').datagrid('insertRow', {
        		index: 2,
        		row:{
        		fFoodType: '宴请',
        		fCostStd: yanqing,
        		fPersonNum:(unitFeteNum+attendPeopNum),
        		//fNum: 1,
        		fCostFood:yanqing*(unitFeteNum+attendPeopNum)
        		}
        	});
        	
    	}
    	
    	
	}else{
		
		$('#tr1').hide();
		var fPersonNumOld = parseInt($('#unitFeteNum').numberbox('getValue'));
		$('#diningPlacePlan1').val(0);
		$('#fFeteTime').datebox('setValue','');//宴请时间
		$('#unitFeteSite').textbox('setValue','');//宴请地点
		$('#unitFeteNum').numberbox('setValue','');//宴请人数
		$('#attendPeopNum').numberbox('setValue','');//陪餐人数
		var rows = $('#rec-food-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(rows[i].fFoodType=="宴请"){
				//如果宴请隐藏，把宴请的人次加入到正餐里面
				if($('#box2').is(':checked')){
		    		var rowsbox2 = $('#rec-food-dg').datagrid('getRows');
		    		for(var j=0;j<rowsbox2.length;j++){
		    			if(rowsbox2[j].fFoodType=="正餐"){
		    				$('#rec-food-dg').datagrid('selectRow', j).datagrid('beginEdit', j);
		    	    		var editors = $('#rec-food-dg').datagrid('getEditors', j); 
		    	    		var old = editors[2].target.numberbox('getValue');
		    	    		$('#rec-food-dg').datagrid('updateRow',{
		    	    			index: j,
		    	    			row: {
		    	    				fPersonNum: parseInt(old)+parseInt(fPersonNumOld),
		    	    				fCostFood: (parseInt(old)+parseInt(fPersonNumOld))*zhengcan,
		    	    			}
		    	    		});
		    	    		$('#rec-food-dg').datagrid('endEdit', j);
		    			}
		    		}
		    	}
				deleteIndex = i;
	    		var index = $('#rec-food-dg').datagrid('getRowIndex',rows[i]);
	             $('#rec-food-dg').datagrid('deleteRow',index);
				editIndex1 = undefined;
			}
		}
		
	}
    var rows = $('#rec-food-dg').datagrid('getRows');
    var num1 = 0;
    for (var i = 0; i < rows.length; i++) {
   	 if (!isNaN(parseFloat(rows[i]['fCostFood']))) {
	    	 num1 += parseFloat(rows[i]['fCostFood']);
   	 }
	}
  //给两个框赋值
	$('#costFood').val(num1.toFixed(2));
	$('#costFood_span').html(fomatMoney(num1,2)+" [元]");
	addTotalNum();
    return;
}

function stayYNCheck2() {
	
//	var personNum = $('#totalPerson').val();
var reDayNum =isNaN(parseInt($('#reDayNum').numberbox('getValue')))?0:parseInt($('#reDayNum').numberbox('getValue'));
var personNum =isNaN(parseInt($('#rePeopNum').numberbox('getValue')))?0:parseInt($('#rePeopNum').numberbox('getValue'));
	var costZAOCAN = "";
	var costZHENGCAN = "";
	if(!isNaN(personNum)){
		costZAOCAN =personNum*parseFloat(zaocan);
		costZHENGCAN =personNum*parseFloat(zhengcan);
    }
    if($('#box2').is(':checked')){
    	$('#tr0').show();
    	$('#diningPlacePlan2').val(1);
    	if (endEditing1()){
        	$('#rec-food-dg').datagrid('insertRow', {
        		index: 1,
        		row:{
        		fFoodType: '早餐',
        		fCostStd: zaocan,
        		fPersonNum:personNum*reDayNum,
        		//fNum: 1,
        		fCostFood:zaocan*personNum*reDayNum
        		}
        	});
        	
        	if($('#box1').is(':checked')){
        		
        		var unitFeteNum =isNaN(parseInt($('#unitFeteNum').numberbox('getValue')))?0:parseInt($('#unitFeteNum').numberbox('getValue'));
            	var attendPeopNum =parseInt($('#attendPeopNum').numberbox('getValue'));
            	$('#rec-food-dg').datagrid('insertRow', {
            		index: 2,
            		row:{
            		fFoodType: '正餐',
            		fCostStd:  zhengcan,
            		fPersonNum:(personNum*reDayNum*2)-unitFeteNum,
            		//fNum: 1,
            		fCostFood:zhengcan*((personNum*reDayNum*2)-(unitFeteNum))
            		}
            	});
        	}else{
        		$('#rec-food-dg').datagrid('insertRow', {
            		index: 2,
            		row:{
            		fFoodType: '正餐',
            		fCostStd: zhengcan,
            		fPersonNum:personNum*2*reDayNum,
            		//fNum: 1,
            		fCostFood:zhengcan*personNum*2*reDayNum
            		}
            	});
        	}
        	
    		}
	}else{
		$('#tr0').hide();
		$('#diningPlacePlan2').val(0);
		$('#diningPlace').textbox('setValue','');
		var rows = $('#rec-food-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(rows[i].fFoodType=="早餐"){
				var index = $('#rec-food-dg').datagrid('getRowIndex',rows[i]);
	             $('#rec-food-dg').datagrid('deleteRow',index);
				editIndex1 = undefined;
			}
			if(rows[i].fFoodType=="正餐"){
				var index = $('#rec-food-dg').datagrid('getRowIndex',rows[i]);
	             $('#rec-food-dg').datagrid('deleteRow',index);
				editIndex1 = undefined;
			}
		}
	}
    var rows = $('#rec-food-dg').datagrid('getRows');
    var num1 = 0;
    for (var i = 0; i < rows.length; i++) {
	   	if (!isNaN(parseFloat(rows[i]['fCostFood']))) {
	    	 num1 += parseFloat(rows[i]['fCostFood']);
	   	}
	}
  //给两个框赋值
	$('#costFood').val(num1.toFixed(2));
	$('#costFood_span').html(fomatMoney(num1,2)+" [元]");
	addTotalNum();
    return;
} */


var startday5='${receptionBean.reDateStart}';
var endday5='${receptionBean.reDateEnd}';

$("#reDateStart").datebox({
    onSelect : function(beginDate){
    	startday5 = beginDate;
    	endday5 = new Date(endday5);
    	var d = (endday5 - startday5) / 86400000 + 1;
    	if (d > 0) {
    		$('#reDayNum').val(d);
    	} else {
    		$('#reDayNum').val("");
    	}
        $('#reDateEnd').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});


	
	function onSelect8(date) {
		endday5 = date;
		startday5 = new Date(startday5);
		var d = (endday5 - startday5) / 86400000 + 1;
		if (d > 0) {
			$('#reDayNum').val(d);
		} else {
			$('#reDayNum').val("");
		}
	}

	$(document).ready(function() {
		//设值复选框的值
		var h = $("#receptionLevelHi").textbox().textbox('getValue');
		if (h != "") {
			$('#receptionLevel').textbox().textbox('setValue', h);
		}
	});

	
	
	
$(function(){
	zcbz();
	$("#rePeopNum").numberbox({
	    onChange : function(newValue,oldValue){
	    	//动态添加住宿费列表行和删除行
	    	var newRowNum =isNaN(parseInt(newValue))?0:parseInt(newValue);
	    	var oldRowNum =isNaN(parseInt(oldValue))?0:parseInt(oldValue);
	    	if(!isNaN(newRowNum) && isNaN(oldRowNum)){
	    	for(var i=0;i<=newRowNum-1;i++){
	    		if (endEditingR()){
	        	$('#rec-hotel-dg').datagrid('appendRow', {});
	    			}
	    		}
	    	}
	    	if(!isNaN(newRowNum) &&!isNaN(oldRowNum)){
	    		if(newRowNum<oldRowNum){
	    			for(var j=oldRowNum;j>newRowNum;j--){
	    				$('#rec-hotel-dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
	    						j-1);
	    				$('#dg_reception_people_plan').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
	    						j-1);
	    				editIndex = undefined;
	    				var rows = $('#rec-hotel-dg').datagrid('getRows');
	    			     var num1 = 0;
	    			     for (var i = 0; i < rows.length; i++) {
	    			    	 if (!isNaN(parseFloat(rows[i]['fCostHotel']))) {
	    				    	 num1 += parseFloat(rows[i]['fCostHotel']);
	    			    	 }
	    				}
	    			     //给两个框赋值
	    					$('#costHotel').val(num1.toFixed(2));
	    					$('#costHotel_span').html(fomatMoney(num1,2)+" [元]");
	    					addTotalNum();
			        }
			    }else if(newRowNum>oldRowNum){
			    	for(var i=0;i<=newRowNum-oldRowNum-1;i++){
			    		if (endEditingR()){
			        	$('#rec-hotel-dg').datagrid('appendRow', {});
			        	$('#dg_reception_people_plan').datagrid('appendRow', {});
			    		}
			    	}
			    }
	    	}
	    	//计算总人数
	    	//addPersonNum();
	    	// 得到rows对象
	    	var rows = $('#rec-food-dg').datagrid('getRows'); // 这段代码是
	    	var copyRows = [];
	        for ( var j= 0; j < rows.length; j++) {
	         copyRows.push(rows[j]);
	        }
	    	
	    	var stayYN = $('#stayYN').val();
	    	if(1==stayYN){//是否安排住宿:是
		    	stayYNCheck1();
		    	$.parser.parse('#rec-food-dg');
	    	}else{
		    	stayYNCheck2();
	    	}
	    	/*someCheck3();
	    	someCheck4();
	    	someCheck5(); */
	    }
	});
		
		/* $("#reDayNum").numberbox({
		    onChange : function(newValue,oldValue){
		    	if(newValue==undefined || oldValue==undefined || newValue==''){
		    		return false;
		    	}
		    	var rows = $('#rec-food-dg').datagrid('getRows'); // 这段代码是
		    	var copyRows = [];
		        for ( var j= 0; j < rows.length; j++) {
		         copyRows.push(rows[j]);
		        }
		    	for (var i = 0; i < copyRows.length; i++){
		    		var index = $('#rec-food-dg').datagrid('getRowIndex',copyRows[j]);
		             $('#rec-food-dg').datagrid('deleteRow',index);
					editIndex1 = undefined;
		    	}
		    	stayYNCheck1();
		    	stayYNCheck2();
		    }
		}); */
	});
		
	//宴请人数计算
	$("#unitFeteNum").numberbox({
	    onChange : function(newValue,oldValue){
	    	if(newValue==undefined || oldValue==undefined || newValue==''){
	    		return false;
	    	}
	    	stayYNCheck1();
	    	stayYNCheck2();
	    }
	});



function requiredValidatebox(){
	var fFeteTime = $("#fFeteTime").datebox('getValue');
	var unitFeteSite = $("#unitFeteSite").textbox('getValue');
	var unitFeteNum = $("#unitFeteNum").numberbox('getValue');
	var attendPeopNum = $("#attendPeopNum").numberbox('getValue');
	if(fFeteTime=='' || unitFeteSite=='' || unitFeteNum=='' || attendPeopNum==''){
		return false;
	}
	return true;
}
		
		
function fFeteTimeAmong(newVal,oldVal){
	var reDateStart = $("#reDateStart").datebox('getValue');
	var reDateEnd = $("#reDateEnd").datebox('getValue');
    var maxTime = Date.parse(new Date(reDateEnd));
    var minTime = Date.parse(new Date(reDateStart));
    var startday = Date.parse(new Date(newVal));
    if(!isNaN(startday)){
    	if((startday>=minTime &&startday<=maxTime)){
    	}else{
    		alert("所选时间不在接待时间范围内请重新选择！");
    		 $("#fFeteTime").datebox('setValue',"");
    	}
    	
    }
		}
</script>
