<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<c:if test="${type!=1}">
<!-- <div class="window-title">费用明细</div> -->
</c:if>


<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<div id="lessons_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">师资费-讲课费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span ><fmt:formatNumber groupingUsed="true" value="${trainingBean.lessonsMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<c:if test="${operation=='add'}">
		<!-- <a style="float: left;color: #666666;font-size:12px;">汇总金额：<span id="lessonsAmount">[元]</span></a> -->
		<a style="float: left;color: #666666;font-size:12px;">汇总金额：<span id="lessonsAmount"><fmt:formatNumber groupingUsed="true" value="${reimbTrainingBean.lessonsMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		</c:if>
		<c:if test="${operation!='add'}">
		<a style="float: left;color: #666666;font-size:12px;">汇总金额：<span id="lessonsAmount"><fmt:formatNumber groupingUsed="true" value="${reimbTrainingBean.lessonsMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		</c:if>
	</div>
	<table id="mingxi-lessons-dg" class="easyui-datagrid" style="width:693px;height:auto;"
	data-options="
	toolbar: 'lessons_Id',
	<c:if test="${!empty reimbTrainingBean.tId}">
	url: '${base}/apply/teacherMingxi?id=${reimbTrainingBean.tId}&costType=lesson',
	</c:if>
	<c:if test="${empty reimbTrainingBean.tId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow4,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:false,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
				<th data-options="field:'thId',hidden:true"></th>
				<th data-options="field:'lecturerName',required:'required',align:'center',width:170">讲师姓名</th>
				<th data-options="field:'lessonStd',required:'required',align:'center',hidden:true"><!-- 讲课费标准（元/学时） --></th>
				<th data-options="field:'lessonHours',required:'required',align:'center',width:100">学时</th>
				<th data-options="field:'lessonActualStd',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{onChange:countTax,precision:2,groupSeparator:','}}">实发标准（元/学时）</th>
				<th data-options="field:'lessonStdTotal',required:'required',align:'center',hidden:true"><!-- 实发标准总额 --></th>
				<th data-options="field:'lessonTaxAmount',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:',',editable:false}}">个人所得税（元）</th>
				<c:if test="${empty detail}">
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{onChange:addNum2,precision:2,groupSeparator:',',editable:false}}">报销金额[元]</th>
				</c:if>
				<c:if test="${!empty detail}">
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:',',editable:false}}">报销金额[元]</th>
				</c:if>
				<th data-options="field:'isOutside',hidden:true"></th>
		</tr>
	</thead>
	</table>
	
</div>


<script type="text/javascript">
function getLessonJson(){
	accept4();
	var rows = $('#mingxi-lessons-dg').datagrid('getRows');
	var lessonJson = "";
	for (var i = 0; i < rows.length; i++) {
		lessonJson = lessonJson + JSON.stringify(rows[i]) + ",";
	}
	$('#lessonJson').val(lessonJson);
}

//表格添加删除，保存方法
var editIndex4 = undefined;
function endEditing4() {
	if (editIndex4 == undefined) {
		return true
	}
	if ($('#mingxi-lessons-dg').datagrid('validateRow', editIndex4)) {
		$('#mingxi-lessons-dg').datagrid('endEdit', editIndex4);
		editIndex4 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow4(index) {
		if (editIndex4 != index) {
			if (endEditing4()) {
				$('#mingxi-lessons-dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex4 = index;
			} else {
				$('#mingxi-lessons-dg').datagrid('selectRow', editIndex4);
			}
		}
}
function accept4() {
	if (endEditing4()) {
		$('#mingxi-lessons-dg').datagrid('acceptChanges');
	}
}
function countTax(n,o){
	var row = $('#mingxi-lessons-dg').datagrid('getSelected');//获得选择行
	var index=$('#mingxi-lessons-dg').datagrid('getRowIndex',row);//获得选中行的行号
	var tr = $('#mingxi-lessons-dg').datagrid('getEditors', index);
	if(!isNaN(parseFloat(n))){
		if(parseFloat(n)>parseFloat(row.lessonStd)){
			alert('实发标准不能超过制度标准，请重新填写');
			tr[0].target.numberbox('setValue','');
			tr[1].target.numberbox('setValue','');
			tr[2].target.numberbox('setValue','');
			return false;
		}else{
			var totalStd =0;//实发标准总额
			var lessonHours =0;//学时
			if(!isNaN(parseFloat(row.lessonHours))){
				lessonHours=parseFloat(row.lessonHours);
			}
			totalStd =parseFloat(n)*lessonHours;
			var taxAmout =0;//税额
			var applyAmout =0;//申请金额
			if(totalStd<=800){
				applyAmout=totalStd;
			}else if(totalStd>800&&totalStd<=3360){
				applyAmout=(totalStd-160)/0.8;
			}else if(totalStd>3360&&totalStd<=21000){
				applyAmout =totalStd/0.84;
			}else if(totalStd>21000&&totalStd<=49500){
				applyAmout =(totalStd-2000)/0.76;
			}else if(totalStd>49500){
				applyAmout =(totalStd-7000)/0.68;
			}
			taxAmout =applyAmout-totalStd;
			tr[1].target.numberbox('setValue',taxAmout.toFixed(2));
			tr[2].target.numberbox('setValue',applyAmout.toFixed(2));
		}
	}
}
//计算申请金额
function addNum2(newValue,oldValue) {
		var row = $('#mingxi-lessons-dg').datagrid('getSelected');//获得选择行
		var index=$('#mingxi-lessons-dg').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#mingxi-lessons-dg').datagrid('getEditors', index);
		/* if(row.isOutside=='1'){
			var standar= parseFloat(row.lessonStdTotalUp);//获得选中行的开支标准
		}else{
			var standar= parseFloat(row.lessonStdTotal);//获得选中行的开支标准
		}
		if(isNaN(standar)){
			standar=0;
		}
		if(parseFloat(newValue)>parseFloat(standar)){
			
			alert('报销金额不能大于开支标准，请核对！');
			tr[0].target.numberbox('setValue',0);
			newValue=0;
		}
		 */
		var num = 0;
		var rows = $('#mingxi-lessons-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i!=index){
				if(rows[i].applySum!=""&&rows[i].applySum!=null){
					num += parseFloat(rows[i].applySum);
				}
			}
		}
		if(newValue!=""&&newValue!=null) {
			num += parseFloat(newValue);
		}
		$('#lessonsMoney').val(num.toFixed(2));
		$('#lessonsAmount').html(fomatMoney(num,2)+"[元]");
		addTotalAmount()
}
</script>
