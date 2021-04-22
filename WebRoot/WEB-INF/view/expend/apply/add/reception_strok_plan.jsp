<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="dg_reception_strok_plan" class="easyui-datagrid" 
	style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#rsp',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/receptionStrokPlan?id=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
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
				<th data-options="field:'fTime',width:195,align:'center',editor:{type:'datebox',options:{onHidePanel:changeTimeXC}},formatter:ChangeDateFormat">时间</th>
				<th data-options="field:'fAddress',width:290,align:'center',editor:{type:'textbox',options:{}}">场所</th>
			</tr>
		</thead>
	</table>
	<c:if test="${empty detail}">
	<div id="rsp" style="height:30px;padding-top : 8px">
		<a href="javascript:void(0)" onclick="removeitRSP()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendRSP()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>
	</c:if>
	<input type="hidden" id="receptionStorkPlanJson" name="storkJson" />
</div>
	
	
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editStrokIndex = undefined;
function endEditingRSP() {
	if (editStrokIndex == undefined) {
		return true
	}
	if ($('#dg_reception_strok_plan').datagrid('validateRow', editStrokIndex)) {
		var dmp = $('#dg_reception_strok_plan').datagrid('getEditor', {
			index : editStrokIndex,
			field : 'fPro'
		});
		$('#dg_reception_strok_plan').datagrid('endEdit', editStrokIndex);
		editStrokIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowRSP(index) {
	if (editStrokIndex != index) {
		if (endEditingRSP()) {
			$('#dg_reception_strok_plan').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editStrokIndex = index;
		} else {
			$('#dg_reception_strok_plan').datagrid('selectRow', editStrokIndex);
		}
	}
}
function appendRSP() {
	if (endEditingRSP()) {
		$('#dg_reception_strok_plan').datagrid('appendRow', {});
		editStrokIndex = $('#dg_reception_strok_plan').datagrid('getRows').length - 1;
		$('#dg_reception_strok_plan').datagrid('selectRow', editStrokIndex).datagrid('beginEdit',editStrokIndex);
	}
}
function removeitRSP() {
	if (editStrokIndex == undefined) {
		return
	}
	$('#dg_reception_strok_plan').datagrid('cancelEdit', editStrokIndex).datagrid('deleteRow',editStrokIndex);
	editStrokIndex = undefined;
}
function accept3() {
	if (endEditingRSP()) {
		$('#dg_reception_strok_plan').datagrid('acceptChanges');
	}
}
	
	
/* 	//重新计算开支标准
	function countkzbz() {
		accept();
		//获得接待人数（有几行就是接待多少人）
		var rownum = $('#dg_reception_strok_plan').datagrid('getRows').length;
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
	function changeTimeXC(){
		var index=$('#dg_reception_strok_plan').datagrid('getRowIndex',$('#dg_reception_strok_plan').datagrid('getSelected'));
		var editors = $('#dg_reception_strok_plan').datagrid('getEditors', index);  
		var endEditor = editors[1]; 
		var startday = Date.parse(new Date(editors[1].target.val()));
		var maxTime = Date.parse(new Date($("#reDateEnd").datebox('getValue')));
	    var minTime = Date.parse(new Date($("#reDateStart").datebox('getValue')));
		var endday = Date.parse(new Date(endEditor.target.val()));
		if(!isNaN(startday)){
	    	if((startday>=minTime &&startday<=maxTime) ){
	    		if(!isNaN(endday)){
		    		if(endday<startday){
		        		alert("结束日期不能小于开始日期！");
		        		editors[1].target.datebox('setValue', '');
		        	}
	    		}
	    	}else{
	    		if(startday>maxTime || startday<minTime){
		    	alert("所选时间不在日程范围内请重新选择！");
	    		editors[1].target.datebox('setValue', '');
	    		}
	    	}
	    	
	    } 
	}
	
	
	/* function changeTime2(){
		var index=$('#dg_reception_strok_plan').datagrid('getRowIndex',$('#dg_reception_strok_plan').datagrid('getSelected'));
		var editors = $('#dg_reception_strok_plan').datagrid('getEditors', index);  
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