<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab">
	<table id="schedule-detail-dg" class="easyui-datagrid" style="width:695px;height:auto;"
	data-options="
	<c:if test="${!empty bean.sId}">
	url: '${base}/schedule/scheduleProQuery?sId=${bean.sId}',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickCell: onClickCellSchedule,
	</c:if>
	striped : true,
	nowrap : false,
	scrollbarSize:0,
	singleSelect: true,
	showFooter: true,
	onLoadSuccess:onLoadSuccessSchedule
	">
	<thead>
		<tr>
			<th data-options="field:'splId',hidden:true"></th>
			<th data-options="field:'sId',hidden:true"></th>
			<th data-options="field:'fProId',hidden:true"></th>
			<th data-options="field:'deptId',hidden:true"></th>
			<th data-options="field:'fProCode',hidden:true"></th>
			<th data-options="field:'pageOrder',required:'required',align:'center'" style="width: 10%">序号</th>
			<th data-options="field:'fProName',required:'required',align:'center'" style="width: 25%">项目名称</th>
			<th data-options="field:'deptName',required:'required',align:'center'" style="width: 20%">申报部门</th>
			<th data-options="field:'pfAmount',align:'center',resizable:false,sortable:true" style="width: 20%">项目总额[元]</th>
			<th data-options="field:'firstAmount',align:'center',resizable:false,sortable:true,editor:{type:'numberbox',options:{precision:2,onChange:onChangeFirst}}" style="width: 20%">一季度额度[元]</th>
			<th data-options="field:'twoAmount',align:'center',resizable:false,sortable:true,editor:{type:'numberbox',options:{precision:2,onChange:onChangeTwo}}" style="width: 20%">二季度额度[元]</th>
			<th data-options="field:'threeAmount',align:'center',resizable:false,sortable:true,editor:{type:'numberbox',options:{precision:2,onChange:onChangeThree}}" style="width: 20%">三季度额度[元]</th>
			<th data-options="field:'fourAmount',align:'center',resizable:false,sortable:true,editor:{type:'numberbox',options:{precision:2,onChange:onChangeFour}}" style="width: 20%">四季度额度[元]</th>
		</tr>
	</thead>
	</table>
</div>
<input type="hidden" id="mingxiJson" name="mingxi"/>
<script type="text/javascript">
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});
//明细表格添加删除，保存方法
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true;}
	if ($('#schedule-detail-dg').datagrid('validateRow', editIndex)){
		$('#schedule-detail-dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCellSchedule(index, field){
	if(index!=0){
		if (endEditing()){
			$('#schedule-detail-dg').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
			editIndex = index;
		}
	}
}

function accept(){
	if(endEditing()){
		$('#schedule-detail-dg').datagrid('acceptChanges');
	}
}

function onChangeFirst(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	newVal = isNaN(parseFloat(newVal))?0:parseFloat(newVal);
	
	var rows = $('#schedule-detail-dg').datagrid('getRows');
	var index=$('#schedule-detail-dg').datagrid('getRowIndex',$('#schedule-detail-dg').datagrid('getSelected'));
     var num1 = 0;
     var flag = true;
     for(var i=1;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
			flag = firstAmountVerify(rows[i],newVal);
			if(!flag){
				break;
			}
		}else{
			var firstAmount = isNaN(parseFloat(rows[i].firstAmount))?0:parseFloat(rows[i].firstAmount);
			num1+=firstAmount;
		}
	}
     
    if(!flag){
		alert("填写金额超出项目总金额，请重新填写！");
		var tr = $('#schedule-detail-dg').datagrid('getEditors', index);
		tr[0].target.numberbox('setValue','');
		accept();
		return false;
	}
   	$('#schedule-detail-dg').datagrid('updateRow',{
		index: 0,
		row: {
			firstAmount: num1
		}
	});
	mergeListSchedule();
}

function firstAmountVerify(rows,amount){
	
	var pfAmount = isNaN(parseFloat(rows.pfAmount))?0:parseFloat(rows.pfAmount);
	var firstAmount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	var twoAmount = isNaN(parseFloat(rows.twoAmount))?0:parseFloat(rows.twoAmount);
	var threeAmount = isNaN(parseFloat(rows.threeAmount))?0:parseFloat(rows.threeAmount);
	var fourAmount = isNaN(parseFloat(rows.fourAmount))?0:parseFloat(rows.fourAmount);
	if(pfAmount<(firstAmount+twoAmount+threeAmount+fourAmount)){
		return false;
	}
	return true;
}

function onChangeTwo(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	newVal = isNaN(parseFloat(newVal))?0:parseFloat(newVal);
	
	var rows = $('#schedule-detail-dg').datagrid('getRows');
	var index=$('#schedule-detail-dg').datagrid('getRowIndex',$('#schedule-detail-dg').datagrid('getSelected'));
     var num1 = 0;
     var flag = true;
     for(var i=1;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
			flag = twoAmountVerify(rows[i],newVal);
			if(!flag){
				break;
			}
		}else{
			var twoAmount = isNaN(parseFloat(rows[i].twoAmount))?0:parseFloat(rows[i].twoAmount);
			num1+=twoAmount;
		}
	}
    if(!flag){
 		alert("填写金额超出项目总金额，请重新填写！");
		var tr = $('#schedule-detail-dg').datagrid('getEditors', index);
		tr[0].target.numberbox('setValue','');
		accept();
 		return false;
 	}
  	$('#schedule-detail-dg').datagrid('updateRow',{
		index: 0,
		row: {
			twoAmount: num1
		}
	});
	mergeListSchedule();
}

function twoAmountVerify(rows,amount){
	var pfAmount = isNaN(parseFloat(rows.pfAmount))?0:parseFloat(rows.pfAmount);
	var firstAmount = isNaN(parseFloat(rows.firstAmount))?0:parseFloat(rows.firstAmount);
	var twoAmount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	var threeAmount = isNaN(parseFloat(rows.threeAmount))?0:parseFloat(rows.threeAmount);
	var fourAmount = isNaN(parseFloat(rows.fourAmount))?0:parseFloat(rows.fourAmount);
	if(pfAmount<(firstAmount+twoAmount+threeAmount+fourAmount)){
		return false;
	}
	return true;
}

function onChangeThree(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	newVal = isNaN(parseFloat(newVal))?0:parseFloat(newVal);
	
	var rows = $('#schedule-detail-dg').datagrid('getRows');
	var index=$('#schedule-detail-dg').datagrid('getRowIndex',$('#schedule-detail-dg').datagrid('getSelected'));
     var num1 = 0;
     var flag = true;
     for(var i=1;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
			flag = threeAmountVerify(rows[i],newVal);
			if(!flag){
				break;
			}
		}else{
			var threeAmount = isNaN(parseFloat(rows[i].threeAmount))?0:parseFloat(rows[i].threeAmount);
			num1+=threeAmount;
		}
	}
    if(!flag){
  		alert("填写金额超出项目总金额，请重新填写！");
		var tr = $('#schedule-detail-dg').datagrid('getEditors', index);
		tr[0].target.numberbox('setValue','');
		accept();
  		return false;
  	}
 	$('#schedule-detail-dg').datagrid('updateRow',{
		index: 0,
		row: {
			threeAmount: num1
		}
	});
 	mergeListSchedule();
}

function threeAmountVerify(rows,amount){
	var pfAmount = isNaN(parseFloat(rows.pfAmount))?0:parseFloat(rows.pfAmount);
	var firstAmount = isNaN(parseFloat(rows.firstAmount))?0:parseFloat(rows.firstAmount);
	var twoAmount = isNaN(parseFloat(rows.twoAmount))?0:parseFloat(rows.twoAmount);
	var threeAmount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	var fourAmount = isNaN(parseFloat(rows.fourAmount))?0:parseFloat(rows.fourAmount);
	if(pfAmount<(firstAmount+twoAmount+threeAmount+fourAmount)){
		return false;
	}
	return true;
}

function onChangeFour(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	newVal = isNaN(parseFloat(newVal))?0:parseFloat(newVal);
	var rows = $('#schedule-detail-dg').datagrid('getRows');
	var index=$('#schedule-detail-dg').datagrid('getRowIndex',$('#schedule-detail-dg').datagrid('getSelected'));
     var num1 = 0;
     var flag = true;
     for(var i=1;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
			flag = threeAmountVerify(rows[i],newVal);
			if(!flag){
				break;
			}
		}else{
			var fourAmount = isNaN(parseFloat(rows[i].fourAmount))?0:parseFloat(rows[i].fourAmount);
			num1+=fourAmount;
		}
	}
    if(!flag){
   		alert("填写金额超出项目总金额，请重新填写！");
		var tr = $('#schedule-detail-dg').datagrid('getEditors', index);
		tr[0].target.numberbox('setValue','');
		accept();
   		return false;
   	}
	$('#schedule-detail-dg').datagrid('updateRow',{
		index: 0,
		row: {
			fourAmount: num1
		}
	});
	mergeListSchedule();
}

function fourAmountVerify(rows,amount){
	var pfAmount = isNaN(parseFloat(rows.pfAmount))?0:parseFloat(rows.pfAmount);
	var firstAmount = isNaN(parseFloat(rows.firstAmount))?0:parseFloat(rows.firstAmount);
	var twoAmount = isNaN(parseFloat(rows.twoAmount))?0:parseFloat(rows.twoAmount);
	var threeAmount = isNaN(parseFloat(rows.threeAmount))?0:parseFloat(rows.threeAmount);
	var fourAmount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	if(pfAmount<(firstAmount+twoAmount+threeAmount+fourAmount)){
		return false;
	}
	return true;
}

function countScheduleAmount(rows){
	var pfAmount = isNaN(parseFloat(rows.pfAmount))?0:parseFloat(rows.pfAmount);
	var firstAmount = isNaN(parseFloat(rows.firstAmount))?0:parseFloat(rows.firstAmount);
	var twoAmount = isNaN(parseFloat(rows.twoAmount))?0:parseFloat(rows.twoAmount);
	var threeAmount = isNaN(parseFloat(rows.threeAmount))?0:parseFloat(rows.threeAmount);
	var fourAmount = isNaN(parseFloat(rows.fourAmount))?0:parseFloat(rows.fourAmount);
	if(pfAmount!=(firstAmount+twoAmount+threeAmount+fourAmount)){
		return false;
	}
	return true;
}

function mergeListSchedule(){
	   $('#schedule-detail-dg').datagrid('mergeCells', {
	       index: 0,
	       field: 'pageOrder',//合并后单元格对应的属性值
	       colspan: 3
	    });
   }
</script>