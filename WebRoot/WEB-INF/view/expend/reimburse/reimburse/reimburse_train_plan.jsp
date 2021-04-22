<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<c:if test="${empty detail}">
	<div id="rp1" hidden="hidden" style="height:30px;padding-top : 8px">
		<a href="javascript:void(0)" onclick="editPlan()" id="editId1" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a href="javascript:void(0)" onclick="savePlan()" id="addId1" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="removeit2()" id="removeId1" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="append2()" id="appendId1" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	</c:if>
	<table id="dg_train_plan" class="easyui-datagrid" 
	style="width:693px;height:auto"
	data-options="
	singleSelect: true,
	<c:if test="${!empty reimbTrainingBean.tId}">
	url: '${base}/apply/trainPlan?id=${reimbTrainingBean.tId}',
	</c:if>
	<c:if test="${empty reimbTrainingBean.tId}">
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
				<th data-options="field:'planId',hidden:true"></th>
				<th data-options="field:'date',width:160,align:'center',editor:{type:'datebox',options:{onHidePanel:changeTime1,showSeconds:false}}">日期</th>
				<th data-options="field:'timeStart',width:160,align:'center',editor:{type:'timespinner',options:{onChange:changeTime3,showSeconds:false},formatter:ChangeDateFormatTrain}">起始时间</th>
				<th data-options="field:'timeEnd',width:160,align:'center',editor:{type:'timespinner',options:{onChange:changeTime4,showSeconds:false},formatter:ChangeDateFormatTrain}">结束时间</th>
				<th data-options="field:'lecturerName',width:160,align:'center',editor:{type:'combobox',options:{
                                valueField:'id',
                                textField:'text',
                                multiple: true,
                                onShowPanel:chooseLecturer,
                                editable:false
                            }}">讲师姓名</th>
				<th data-options="field:'lessonTime',width:90,align:'center',
					editor:{type:'textbox',options:{onChange:lessonTime}}">学时</th>
				<th data-options="field:'arrange',width:215,align:'center',editor:'textbox'">培训内容</th>
			</tr>
		</thead>
	</table>
	
	
</div>

	
<script type="text/javascript">
$.fn.datetimebox.defaults.parser = function(s){
	 var t = Date.parse(s);
	 if (!isNaN(t)){
	  return new Date(t);
	 } else {
	  return new Date();
	 }
	};
function savePlan(){
	flag1=1;
	var rows = $('#dg_train_plan').datagrid('getRows');
	if(rows==''){
		alert("请添加会议日程！");
		return false;
	}
	$("#addId1").hide();
	$("#removeId1").hide();
	$("#appendId1").hide();
	$("#editId1").show();
	accept2()
	var arr=lecturerHoursArr()
	updateLessons(arr);
}

function editPlan(){
	flag1 = 0;
	$("#addId1").show();
	$("#removeId1").show();
	$("#appendId1").show();
	$("#editId1").hide();
}

function getTrainPlanJson(){
	accept2();
	var rows = $('#dg_train_plan').datagrid('getRows');
	var trainPlan = "";
	for (var i = 0; i < rows.length; i++) {
		trainPlan = trainPlan + JSON.stringify(rows[i]) + ",";
	}
	$('#trainPlan').val(trainPlan);
}
//接待人员表格添加删除，保存方法
	var editIndex2 = undefined;
	function endEditing2() {
		if (editIndex2 == undefined) {
			return true
		}
		if ($('#dg_train_plan').datagrid('validateRow', editIndex2)) {
			var rph = $('#dg_train_plan').datagrid('getEditor', {
				index : editIndex2,
				field : 'costDetail'
			});
			$('#dg_train_plan').datagrid('endEdit', editIndex2);
			editIndex2 = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow2(index) {
		if(flag1==1){
			return false;
		}else{
		if (editIndex2 != index) {
			if (endEditing2()) {
				$('#dg_train_plan').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex2 = index;
			} else {
				$('#dg_train_plan').datagrid('selectRow', editIndex2);
			}
		}
	}
}
	function append2() {
		if (endEditing2()) {
			$('#dg_train_plan').datagrid('appendRow', {});
			editIndex2 = $('#dg_train_plan').datagrid('getRows').length - 1;
			$('#dg_train_plan').datagrid('selectRow', editIndex2).datagrid('beginEdit',editIndex2);
		}
	}
	function removeit2() {
		if (editIndex2 == undefined) {
			return
		}
		$('#dg_train_plan').datagrid('cancelEdit', editIndex2).datagrid('deleteRow',
				editIndex2);
		editIndex2 = undefined;
	}
	function accept2() {
		if (endEditing2()) {
			$('#dg_train_plan').datagrid('acceptChanges');
		}
	}
	//计算学时
	function addHour(){
		var index=$('#dg_train_plan').datagrid('getRowIndex',$('#dg_train_plan').datagrid('getSelected'));
	    var editors = $('#dg_train_plan').datagrid('getEditors', index); 
	    var ed1 = editors[0]; 
	    var ed2 = editors[1]; 
	    var time1 =new Date(ed1.target.val());
	    var time2 =new Date(ed2.target.val());
	    if(!isNaN(time1) &&!isNaN(time2) ){
	   	 	var h = ((time2-time1)/3600000).toFixed(1);
	        	editors[3].target.textbox('setValue', h);
	        }
	}
	
	//计算学时合计
	function addStuHour(newValue,oldValue){
		var price = parseFloat(newValue);
		var row = $('#dg_train_plan').datagrid('getSelected');//获得选择行
		var index=$('#dg_train_plan').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#dg_train_plan').datagrid('getEditors', index);
		
		var num = 0;
		var rows = $('#dg_train_plan').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i!=index){
				if(rows[i].lessonTime!=""&&rows[i].lessonTime!=null){
					num += parseFloat(rows[i].lessonTime);
				}
			}
		}
		if(newValue!=""&&newValue!=null) {
			num += parseFloat(newValue);
		}
	}
	function changeTime3(){
		var index=$('#dg_train_plan').datagrid('getRowIndex',$('#dg_train_plan').datagrid('getSelected'));
		var editors = $('#dg_train_plan').datagrid('getEditors', index);  
		var endEditor = editors[1]; 
		var startday = Date.parse(new Date(editors[0].target.val()));
		var maxTime = Date.parse(new Date($("#trDateEnd").datebox('getValue')))+86400000;
	    var minTime = Date.parse(new Date($("#trDateStart").datebox('getValue')))-28800000;
		var endday = Date.parse(new Date(endEditor.target.val()));
		if(!isNaN(startday)){
	    	if(startday>=minTime &&startday<=maxTime){
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
	
	
	function changeTime4(){
		var index=$('#dg_train_plan').datagrid('getRowIndex',$('#dg_train_plan').datagrid('getSelected'));
		var editors = $('#dg_train_plan').datagrid('getEditors', index);  
		var endEditor = editors[1]; 
		var startday = Date.parse(new Date(editors[0].target.val()));
		var maxTime = Date.parse(new Date($("#trDateEnd").datebox('getValue')))+86400000;
	    var minTime = Date.parse(new Date($("#trDateStart").datebox('getValue')))-28800000;
		var endday = Date.parse(new Date(endEditor.target.val()));
		if(!isNaN(endday)){
	    	if((endday>=minTime &&endday<=maxTime) ){
	    		if(!isNaN(startday)){
		    		if(endday<startday){
		        		alert("结束日期不能小于开始日期！");
		        		editors[1].target.datebox('setValue', '');
		        	}
	    		}
	    	}else{
	    		if(endday>maxTime || endday<minTime){
		    	alert("所选时间不在日程范围内请重新选择！");
	    		editors[1].target.datebox('setValue', '');
	    		}
	    	}
	    	
	    } 
	}
	
	function chooseLecturer(){
		
		$('#dg_train_plan').datagrid('selectRow', index).datagrid('beginEdit',
				index);
		var index=$('#dg_train_plan').datagrid('getRowIndex',$('#dg_train_plan').datagrid('getSelected'));
			var new_arrs= lecturerArr();
			var lecturer = $('#dg_train_plan').datagrid('getEditor',{
				index:index,
				field:'lecturerName'
			});
			$(lecturer.target).combobox({
	            data: new_arrs,
	            valueField: 'id',
	            multiple: false,
	            textField: 'text',
			});
	}
	
	//讲师姓名、学时数组
	function lecturerHoursArr(){
		var rows = $('#dg_train_plan').datagrid('getRows');
		var arr = new Array();
		for (var i = 0; i < rows.length; i++) {
		var lecturerName = rows[i].lecturerName;
		var lessonTime = rows[i].lessonTime;
				var idAndName = {};
				idAndName.name = lecturerName;
				idAndName.hours = lessonTime;
				arr.push(idAndName);
		}
		var b = []//记录数组a中的id 相同的下标
        for(var i = 0; i < arr.length;i++){
            for(var j = arr.length-1;j>i;j--){
                if(arr[i].name == arr[j].name){
                	arr[i].hours = (parseFloat(arr[i].hours) + parseFloat(arr[j].hours)).toString()
                    b.push(j)
                }
            }
 
        }
        for(var k = 0; k<b.length;k++){
            arr.splice(b[k],1)
        }
		return arr;
	}
	//时间校验
	function changeTime3(){
		var index=$('#dg_train_plan').datagrid('getRowIndex',$('#dg_train_plan').datagrid('getSelected'));
		var editors = $('#dg_train_plan').datagrid('getEditors', index);  
		var startday = editors[1].target.val();
		var endday = editors[2].target.val();
		if(startday !='' && endday != ''){
			if(endday<startday){
        		alert("开始日期不能大于结束日期！");
        		editors[1].target.timespinner('setValue', '');
        	}
		}
	}
	function changeTime4(){
		var index=$('#dg_train_plan').datagrid('getRowIndex',$('#dg_train_plan').datagrid('getSelected'));
		var editors = $('#dg_train_plan').datagrid('getEditors', index);  
		var startday = editors[1].target.val();
		var endday = editors[2].target.val();
		if(startday !='' && endday != ''){
			if(endday<startday){
        		alert("结束日期不能小于开始日期！");
        		editors[2].target.timespinner('setValue', '');
        	}
		}
	}
	//时间格式化
	function ChangeDateFormatTrain(val) {
		var t, y, m, d, h, i, s;
		if (val == null || val == "") {
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
		return (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i);
	}
	
	//学时校验
	function lessonTime(newValue,oldValue){
		var row = $('#dg_train_plan').datagrid('getSelected');//获得选择行
		var index=$('#dg_train_plan').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#dg_train_plan').datagrid('getEditors', index);
		
		arr1=tr[1].target.val().split(":");
		arr2=tr[2].target.val().split(":");
		
		var beginTime = parseInt(arr1[0])+parseInt(arr1[1])/60;
		var endTime = parseInt(arr2[0])+parseInt(arr2[1])/60;
		
		if(parseInt(newValue)>parseInt(endTime-beginTime)){
			alert("请填写正确的学时！");
			tr[4].target.textbox('setValue',0);
			return;
		}
		var rows = $('#dg_train_plan').datagrid('getRows');
		var lessonNum = 0;
		for(var i =0 ;i<rows.length;i++){
			if(i!=index){
				if(rows[i].date == tr[0].target.val()){
					lessonNum += parseInt(rows[i].lessonTime);
				} 
			}
		}
		lessonNum += parseInt(newValue);
		if(lessonNum > 8){
			alert("请填写正确的学时！");
			tr[4].target.textbox('setValue',0);
			return;
		}
	}
</script>