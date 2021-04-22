<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<c:if test="${empty detail}">
		<div id="r_rsp" style="display:none;height:30px;padding-top : 8px">
			<a href="javascript:void(0)" onclick="removeitRSP()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
			<a style="float: right;">&nbsp;&nbsp;</a>
			<a href="javascript:void(0)" onclick="appendRSP()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		</div>
	</c:if>
	<table id="r_reception_strok_plan" class="easyui-datagrid" 
	style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	<c:if test="${!empty applyBean.gId}">
	url: '${base}/apply/receptionStrokPlan?id=${applyBean.gId}',
	</c:if>
	<c:if test="${!empty bean.rId&&operation=='edit'}">
	url: '${base}/reimburse/receptionStrokPlan?rId=${bean.rId}',
	</c:if>
	<c:if test="${!empty bean.rId&&operation=='detail'}">
	url: '${base}/reimburse/receptionStrokPlan?rId=${bean.rId}',
	</c:if>
	<c:if test="${empty applyBean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRowRSP,
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'fSPId',hidden:true"></th>
				<th data-options="field:'fPro',width:195,align:'center',editor:{type:'textbox',options:{}}">项目</th>
				<th data-options="field:'fTime',width:195,align:'center',editor:{type:'datebox',options:{}},formatter:ChangeDateFormat">时间</th>
				<th data-options="field:'fAddress',width:290,align:'center',editor:{type:'textbox',options:{}}">场所</th>
			</tr>
		</thead>
	</table>

</div>
	
	
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editStrokIndex = undefined;
function endEditingRSP() {
	if (editStrokIndex == undefined) {
		return true
	}
	if ($('#r_reception_strok_plan').datagrid('validateRow', editStrokIndex)) {
		var dmp = $('#r_reception_strok_plan').datagrid('getEditor', {
			index : editStrokIndex,
			field : 'fPro'
		});
		$('#r_reception_strok_plan').datagrid('endEdit', editStrokIndex);
		editStrokIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowRSP(index) {
	if(updateradio==0){
		if (editStrokIndex != index) {
			if (endEditingRSP()) {
				$('#r_reception_strok_plan').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editStrokIndex = index;
			} else {
				$('#r_reception_strok_plan').datagrid('selectRow', editStrokIndex);
			}
		}
	}else{
		alert('住宿费无法编辑,请点击是否调整按钮进行编辑！');
	}
}
function appendRSP() {
	if (endEditingRSP()) {
		$('#r_reception_strok_plan').datagrid('appendRow', {});
		editStrokIndex = $('#r_reception_strok_plan').datagrid('getRows').length - 1;
		$('#r_reception_strok_plan').datagrid('selectRow', editStrokIndex).datagrid('beginEdit',editStrokIndex);
	}
}
function removeitRSP() {
	if (editStrokIndex == undefined) {
		return
	}
	$('#r_reception_strok_plan').datagrid('cancelEdit', editStrokIndex).datagrid('deleteRow',editStrokIndex);
	editStrokIndex = undefined;
}
function accept3() {
	if (endEditingRSP()) {
		$('#r_reception_strok_plan').datagrid('acceptChanges');
	}
}
function receptionStrokJson(){
	accept3();
	var rowsstrok = $('#r_reception_strok_plan').datagrid('getRows');
	var strokJson = "";
	if(rowsstrok==''){
		return false;
	}else{
		for (var i = 0; i < rowsstrok.length; i++) {
			strokJson = strokJson + JSON.stringify(rowsstrok[i]) + ",";
		}
		$('#receptionStorkPlanJson').val(strokJson);
		return true;
	}
}
	
/* 	//重新计算开支标准
	function countkzbz() {
		accept();
		//获得接待人数（有几行就是接待多少人）
		var rownum = $('#r_reception_strok_plan').datagrid('getRows').length;
		//修改明细表中的开支标准
		var rows = $('#appli-detail-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++) {
			var tr = $('#appli-detail-dg').datagrid('getEditors', i);
			//获得每一行的开支标准
			var kzbz=rows[i].standard;
			//设置开支标准
			onClickRow(i);
			tr[1].target.textbox('setValue', parseFloat(kzbz*rownum));
			accept();
		}
	} */
	/* function changeTime1(){
		var index=$('#r_reception_strok_plan').datagrid('getRowIndex',$('#r_reception_strok_plan').datagrid('getSelected'));
		var editors = $('#r_reception_strok_plan').datagrid('getEditors', index);  
		var endEditor = editors[1]; 
		var startday = Date.parse(new Date(editors[0].target.val()));
		var maxTime = Date.parse(new Date($("#meetingDateEnd").datebox('getValue')));
	    var minTime = Date.parse(new Date($("#meetingDateStart").datebox('getValue')));
	    var meetType = $('#meetingType').combobox('getValue');
		var endday = Date.parse(new Date(endEditor.target.val()));
		if(!isNaN(startday)){
	    	if((startday>=minTime &&startday<=maxTime) ){
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
	
	
	function changeTime2(){
		var index=$('#r_reception_strok_plan').datagrid('getRowIndex',$('#r_reception_strok_plan').datagrid('getSelected'));
		var editors = $('#r_reception_strok_plan').datagrid('getEditors', index);  
		var endEditor = editors[1]; 
		var startday = Date.parse(new Date(editors[0].target.val()));
		var maxTime = Date.parse(new Date($("#meetingDateEnd").datebox('getValue')));
	    var minTime = Date.parse(new Date($("#meetingDateStart").datebox('getValue')));
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
	} */
	
</script>