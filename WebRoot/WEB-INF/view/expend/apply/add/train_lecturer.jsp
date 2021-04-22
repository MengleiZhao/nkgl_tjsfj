<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="dg_train_lecturer" class="easyui-datagrid" 
	style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#rpl',
	<c:if test="${!empty trainingBean.tId}">
	url: '${base}/apply/trainLecturer?id=${trainingBean.tId}',
	</c:if>
	<c:if test="${empty trainingBean.tId}">
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
	">
		<thead>
			<tr>
				<th data-options="field:'lId',hidden:true"></th>
				<th data-options="field:'lecturerName',width:120,align:'center',editor:{type:'textbox'}">讲师姓名</th>
				<th data-options="field:'unit',width:150,align:'center',editor:{type:'textbox'}">单位名称</th>
				<th data-options="field:'lecturerLevel',width:180,align:'center',editor:{type:'combotree',options:{
								valueField:'code',
								textField:'text',
								method:'post',
								url:base+'/Formulation/lookupsJson?parentCode=JSJB',
								onSelect:setCode
							}}">职称</th>
				<th data-options="field:'lecturerLevelCode',hidden:true,editor:'textbox'"></th>
				<th data-options="field:'idCard',width:150,align:'center',editor:'textbox'">身份证号</th>
				<th data-options="field:'isOutside',width:90,align:'center',
					editor:{type:'combobox',options:{valueField:'id',textField:'text',data:[
	                	{id:'1',text:'是'},
	                	{id:'0',text:'否',selected:true}],
	                	prompt:'-请选择-',panelHeight:'atuo',editable: false}},formatter:isorno">是否异地</th>
				<th data-options="field:'bankCard',width:150,align:'center',editor:'textbox'">讲师银行卡号</th>
				<th data-options="field:'bank',width:180,align:'center',editor:'textbox'">开户行</th>
				<th data-options="field:'phoneNum',width:120,align:'center',editor:'textbox'">手机号</th>
			</tr>
		</thead>
	</table>
	<c:if test="${empty detail}">
	<div id="rpl" style="height:30px;padding-top : 8px">
		<a href="javascript:void(0)" onclick="editLecturer()" id="editId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a href="javascript:void(0)" onclick="saveLecturer()" id="addId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" id="removeId" onclick="removeit1()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" id="appendId" onclick="append1()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	</c:if>
	<input type="hidden" id="trainLecturerJson" name="trainLecturer" />
</div>

	
<script type="text/javascript">
function getTrainLecturerJson(){
	accept1();
	var rows = $('#dg_train_lecturer').datagrid('getRows');
	var trainLecturer = "";
	for (var i = 0; i < rows.length; i++) {
		trainLecturer = trainLecturer + JSON.stringify(rows[i]) + ",";
	}
	$('#trainLecturerJson').val(trainLecturer);
}

function isorno(val){
	if(val=='1'){
		return '是';
	}else if(val=='0'){
		return '否';
	}else{
		return val;
	}
}
function saveLecturer(){
	
	flag2=1;
		
	var rows = $('#dg_train_lecturer').datagrid('getRows');
	if(rows==''){
		alert("请添加讲师信息明细！");
		return false;
	}
	$("#addId").hide();
	$("#removeId").hide();
	$("#appendId").hide();
	$("#editId").show();
	$("#rph").show();
	accept1();
	for (var i = rows.length-1; i >= 0; i--) {
		if(rows[i].lecturerName==''&&rows[i].lecturerLevel==''&&rows[i].lecturerLevelCode==''&&rows[i].isOutside==''&&rows[i].bankCard==''&&rows[i].bank==''&&rows[i].phoneNum==''){
			$('#dg_train_lecturer').datagrid('deleteRow',i);
		}
	}
	flag1=0;
	$('#trainingType').combobox('readonly',true);
	$('#trDateStart').datebox('readonly',true);
	$('#trDateEnd').datebox('readonly',true);
	/* $('#trAttendNum').numberbox('readonly',true);
	$('#trStaffNum').numberbox('readonly',true); */
	lecturerArr();
	loadDatas();
	/* $("input[name='isFood']").attr("disabled",true);
	$("input[name='isHotel']").attr("disabled",true); */
}
function editLecturer(){
	flag2 = 0;
	$("#addId").show();
	$("#removeId").show();
	$("#appendId").show();
	$("#editId").hide();
	$("#rph").hide();
	$("#addId1").show();
	$("#removeId1").show();
	$("#appendId1").show();
	$("#editId1").hide();
	accept2()
	flag1=1;
	$('#trainingType').combobox('readonly',false);
	$('#trDateStart').datebox('readonly',false);
	$('#trDateEnd').datebox('readonly',false);
	/* $('#trAttendNum').numberbox('readonly',false);
	$('#trStaffNum').numberbox('readonly',false);
	$("input[name='isFood']").attr("disabled",false);
	$("input[name='isHotel']").attr("disabled",false); */
}
//表格添加删除，保存方法
	var editIndex1 = undefined;
	function endEditing1() {
		if (editIndex1 == undefined) {
			return true
		}
		if ($('#dg_train_lecturer').datagrid('validateRow', editIndex1)) {
			var tr = $('#dg_train_lecturer').datagrid('getEditors', editIndex1);
			var text1=tr[2].target.combotree('getText');
			if(text1!='--请选择--'){
				tr[2].target.combotree('setValue',text1);
			}
			$('#dg_train_lecturer').datagrid('endEdit', editIndex1);
			editIndex1 = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow1(index) {
		
		if(flag2==1){
			return false;
		}else{
			if (editIndex1 != index) {
				if (endEditing1()) {
					$('#dg_train_lecturer').datagrid('selectRow', index).datagrid('beginEdit',
							index);
					editIndex1 = index;
				} else {
					$('#dg_train_lecturer').datagrid('selectRow', editIndex1);
				}
			}
		}
	}
	function append1() {
		if (endEditing1()) {
			$('#dg_train_lecturer').datagrid('appendRow', {});
			editIndex1 = $('#dg_train_lecturer').datagrid('getRows').length - 1;
			$('#dg_train_lecturer').datagrid('selectRow', editIndex1).datagrid('beginEdit',editIndex1);
			var tr = $('#dg_train_lecturer').datagrid('getEditors', editIndex1);
			tr[5].target.combobox('setValue',"0");
		}
	}
	function removeit1() {
		if (editIndex1 == undefined) {
			return
		}
		$('#dg_train_lecturer').datagrid('cancelEdit', editIndex1).datagrid('deleteRow',
				editIndex1);
		editIndex1 = undefined;
	}
	function accept1() {
		if (endEditing1()) {
			$('#dg_train_lecturer').datagrid('acceptChanges');
		}
	}
	
	function setCode(rec){
		var row = $('#dg_train_lecturer').datagrid('getSelected');
		var rindex = $('#dg_train_lecturer').datagrid('getRowIndex', row); 
		var ed = $('#dg_train_lecturer').datagrid('getEditor',{
			index:rindex,
			field : 'lecturerLevelCode'  
		});
			$(ed.target).textbox('setValue', rec.code);
	}
	//讲师姓名数组
	function lecturerArr(){
		var rows = $('#dg_train_lecturer').datagrid('getRows');
		var arr = new Array();
		for (var i = 0; i < rows.length; i++) {
		var lecturerName = rows[i].lecturerName;
				var idAndName = {};
				idAndName.id = lecturerName;
				idAndName.text = lecturerName;
				arr.push(idAndName);
		}
		return arr;
	}
	//加载各项费用列表
	function loadDatas(){
		var nameArr = lecturerArr();
		loadLessons();
		loadHotel(nameArr);
		loadFood(nameArr);
		loadTraffic1(nameArr);
		loadTraffic2(nameArr);
		updatePlan(nameArr);
		addTotalAmount();
	}
	//加载住宿费列表
	function loadHotel(nameArr){
		var data = new Array();
		$('#mingxi-hotel-dg').datagrid('loadData',data);
		editIndex5 = undefined;
		var train_num = parseInt($('#trDayNum').numberbox('getValue'));//培训天数
		if(isNaN(train_num)){
			train_num=parseInt(1);
		}
		var hotelStd =parseFloat($('#hotelStd').val())
		if(isNaN(hotelStd)){
			hotelStd=0;
		}
		 var hotelStdTotal= hotelStd*(train_num-1);
		for(var i=0;i<nameArr.length;i++){
			$('#mingxi-hotel-dg').datagrid('appendRow', {
				lecturerName: nameArr[i].text,
				hotelStd:hotelStd,
				hotelStdTotal:hotelStdTotal
			});
		}
		$('#hotelMoney').val(0);
		$('#hotelAmount').html("0.00[元]");
	}
	//加载伙食费列表
	function loadFood(nameArr){
		var data = new Array();
		$('#mingxi-food-dg').datagrid('loadData',data);
		editIndex6 = undefined;
		var train_num = parseInt($('#trDayNum').numberbox('getValue'));//培训天数
		if(isNaN(train_num)){
			train_num=parseInt(0);
		}
		var foodStd =parseFloat($('#foodStd').val())
		if(isNaN(foodStd)){
			foodStd=0;
		}
		var foodStdTotal= foodStd*train_num;
		for(var i=0;i<nameArr.length;i++){
			$('#mingxi-food-dg').datagrid('appendRow', {
				lecturerName: nameArr[i].text,
				foodStd:foodStd,
				foodStdTotal:foodStdTotal
			});
		}
		$('#foodMoney').val(0);
		$('#foodAmount').html("0.00[元]");
	}
	//加载城市间交通费列表
	function loadTraffic1(nameArr){
		var data = new Array();
		$('#mingxi-trafficCityToCity-dg').datagrid('loadData',data);
		editIndex7 = undefined;
		for(var i=0;i<nameArr.length;i++){
			$('#mingxi-trafficCityToCity-dg').datagrid('appendRow', {
				lecturerName: nameArr[i].text,
			});
		}
		$('#longTrafficMoney').val(0);
		$('#traffic1Amount').html("0.00[元]");
	}
	//加载市内交通费列表
	function loadTraffic2(nameArr){
		var data = new Array();
		$('#mingxi-trafficInCity-dg').datagrid('loadData',data);
		editIndex8 = undefined;
		for(var i=0;i<nameArr.length;i++){
			$('#mingxi-trafficInCity-dg').datagrid('appendRow', {
				lecturerName: nameArr[i].text,
			});
		}
		$('#transportMoney').val(0);
		$('#traffic2Amount').html("0.00[元]");
	}
	//加载讲课费列表
	function loadLessons(){
		var data = new Array();
		$('#mingxi-lessons-dg').datagrid('loadData',data);
		editIndex4 = undefined;
		var rows = $('#dg_train_lecturer').datagrid('getRows');
		for (var i = 0; i < rows.length; i++) {
			var lecturerName = rows[i].lecturerName;
			var lecturerLevelCode =rows[i].lecturerLevelCode;
				var lessonStd=0;
				if(lecturerLevelCode=='JSJB-01'){
					lessonStd=$('#lesson1Std').val();
				}else if(lecturerLevelCode=='JSJB-02'){
					lessonStd=$('#lesson2Std').val();
				}else if(lecturerLevelCode=='JSJB-03'){
					lessonStd=$('#lesson3Std').val();
				}
			var isOutside =rows[i].isOutside;
				$('#mingxi-lessons-dg').datagrid('appendRow', {
					lecturerName: lecturerName,
					lessonStd: lessonStd,
					isOutside: isOutside
				});
		}
		$('#lessonsMoney').val(0);
		$('#lessonsAmount').html("0.00[元]");
	}
	//刷新讲课费列表
	function updateLessons(arr){
		var rows = $('#mingxi-lessons-dg').datagrid('getRows');
		for (var i = 0; i < rows.length; i++) {
			for (var j = 0; j < arr.length; j++){
				if(rows[i].lecturerName==arr[j].name){
					var hours =arr[j].hours;
					var isOutside =rows[i].isOutside;
					var lessonStd =rows[i].lessonStd;
					var lessonStdTotal=0;
					if(hours!=''){
					 lessonStdTotal = parseFloat(lessonStd)*parseFloat(hours);
					}
					if(isOutside=='1'){
						 lessonStdTotal = lessonStdTotal*1.2;
						 lessonStd =lessonStd*1.2;
					}
					$('#mingxi-lessons-dg').datagrid('updateRow',{
						index: i,
						row: {
							lessonStd: lessonStd,
							lessonHours:hours
						}
					});
				}
			}
		}
		editIndex4 = undefined;
	}
	/* //刷新培训日程
	function updatePlan(arr){
		var rows = $('#dg_train_plan').datagrid('getRows');
		for (var i = 0; i < rows.length; i++) {
				if( JSON.stringify(arr).indexOf(rows[i].lecturerName) == -1 ) {
					$('#dg_train_plan').datagrid('deleteRow',
							i);
				}
		}
	} */
	//刷新培训日程
	function updatePlan(arr){
		var rows = $('#dg_train_plan').datagrid('getRows');
		var flag = false;
		for (var i = 0; i < rows.length; i++) {
			for(var h = 0;h < arr.length; h++){
				console.log(JSON.parse(JSON.stringify(arr))[h]['text']);
				if(JSON.parse(JSON.stringify(arr))[h]['text'] == rows[i].lecturerName){
					flag = true;
				}
			}
			if(!flag){
					
				$('#dg_train_plan').datagrid('deleteRow',
							i);
				i = i-1;
			}
			flag = false;
		}
	}
	
</script>