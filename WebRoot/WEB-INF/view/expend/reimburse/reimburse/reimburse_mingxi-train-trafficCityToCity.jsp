<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<c:if test="${type!=1}">
<!-- <div class="window-title">费用明细</div> -->
</c:if>


<div class="window-tab" style="margin-left: 0px;padding-top: 10px">

	<div id="trafficCityToCity_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">师资费-城市间交通费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span ><fmt:formatNumber groupingUsed="true" value="${trainingBean.longTrafficMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<c:if test="${operation=='add'}">
		<a style="float: left;color: #666666;font-size:12px;">汇总金额：<span id="traffic1Amount">[元]</span></a>
		</c:if>
		<c:if test="${operation!='add'}">
		<a style="float: left;color: #666666;font-size:12px;">汇总金额：<span id="traffic1Amount"><fmt:formatNumber groupingUsed="true" value="${reimbTrainingBean.longTrafficMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		</c:if>
	</div>
	<table id="mingxi-trafficCityToCity-dg" class="easyui-datagrid" style="width:693px;height:auto;"
	data-options="
	toolbar: 'trafficCityToCity_Id',
	<c:if test="${!empty reimbTrainingBean.tId}">
	url: '${base}/apply/teacherMingxi?id=${reimbTrainingBean.tId}&costType=traffic1',
	</c:if>
	<c:if test="${empty reimbTrainingBean.tId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow7,
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
				<th data-options="field:'administrativeLevel',required:'required',align:'center',width:180,
					editor:{type:'combobox',options:{onSelect:clearVehicle,valueField:'id',textField:'text',data:[
	                	<!-- {id:'市级及相当职务人员',text:'市级及相当职务人员'}, -->
	                	{id:'局级及相当职务人员',text:'局级及相当职务人员'},
	                	{id:'其他人员',text:'其他人员'}],
	                	prompt:'-请选择-',panelHeight:'atuo',editable: false}}">行政级别</th>
				<th data-options="field:'vehicle',width:180,align:'center',editor:{
							editable:true,
							type:'combotree',
							filter: function(q, row){
									var opts = $(this).combotree('options');
									return row[opts.textField].indexOf(q) == 0;
								},
							options:{
								required:true,
								valueField:'code',
								textField:'text',
								method:'post',
								url:base+'/vehicle/comboboxJson?selected=JTGJ06',
								onSelect:reloadOut
							}}">交通工具</th>
				<th data-options="field:'vehicleLevel',width:180,align:'center',editor:{
							editable:true,
							type:'combotree',
							options:{
								required:true,
								valueField:'code',	
								textField:'text',
								onShowPanel:reloadLevel,
								method:'post',
								url:base+'/vehicle/comboboxJson?selected=JTGJ06&parentCode=JTGJ0602'
							}}">交通工具级别</th>
				<c:if test="${empty detail}">
				<th data-options="field:'reimbSum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{onChange:addNum5,precision:2,groupSeparator:','}}">报销金额[元]</th>
				</c:if>
				<c:if test="${!empty detail}">
				<th data-options="field:'reimbSum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}">报销金额[元]</th>
				</c:if>
				<th data-options="field:'vehicleId',required:'required',hidden:true,editor:{type:'textbox',options:{editable: true}}"></th>
		</tr>
	</thead>
	</table>
	
</div>


<script type="text/javascript">
function getTrafficJson1(){
	accept7();
	var rows = $('#mingxi-trafficCityToCity-dg').datagrid('getRows');
	var trafficJson1 = "";
	for (var i = 0; i < rows.length; i++) {
		trafficJson1 = trafficJson1 + JSON.stringify(rows[i]) + ",";
	}
	$('#trafficJson1').val(trafficJson1);
}

//接待人员表格添加删除，保存方法
var editIndex7 = undefined;
function endEditing7() {
	if (editIndex7 == undefined) {
		return true
	}
	if ($('#mingxi-trafficCityToCity-dg').datagrid('validateRow', editIndex7)) {
		var tr = $('#mingxi-trafficCityToCity-dg').datagrid('getEditors', editIndex7);
		var text1=tr[1].target.combotree('getText');
		if(text1!='--请选择--'){
			tr[1].target.combotree('setValue',text1);
		}
		var text2=tr[2].target.combotree('getText');
		if(text2!='--请选择--'){
			tr[2].target.combotree('setValue',text2);
		}
		$('#mingxi-trafficCityToCity-dg').datagrid('endEdit', editIndex7);
		editIndex7 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow7(index) {
		if (editIndex7 != index) {
			if (endEditing7()) {
				$('#mingxi-trafficCityToCity-dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex7 = index;
			} else {
				$('#mingxi-trafficCityToCity-dg').datagrid('selectRow', editIndex7);
			}
		}
}
function accept7() {
	if (endEditing7()) {
		$('#mingxi-trafficCityToCity-dg').datagrid('acceptChanges');
	}
}
function reloadOut(rec){
	var row = $('#mingxi-trafficCityToCity-dg').datagrid('getSelected');
	var rindex = $('#mingxi-trafficCityToCity-dg').datagrid('getRowIndex', row); 
	var ed = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleLevel'  
	});
	var ed1 = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
		index:rindex,
		field : 'administrativeLevel'  
	});
	var levelText =$(ed1.target).combobox('getValue');
	var level='';
	/* if(levelText=='市级及相当职务人员'){
		level='1';
	} */
	if(levelText=='局级及相当职务人员'){
		level='1';
	
	}if(levelText=='其他人员'){
		level='2';
	}
	$(ed.target).combotree('setValue', '');
	var vehicleId = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
    	index:rindex,
    	field : 'vehicleId'  
    });
    $(vehicleId.target).textbox('setValue', rec.code);
	var url = base+'/vehicle/comboboxJson?selected=${travelBean.vehicle}&parentCode='+rec.code+'&level='+level;
    $(ed.target).combotree('reload', url);
}
function clearVehicle(){
	var row = $('#mingxi-trafficCityToCity-dg').datagrid('getSelected');
	var rindex = $('#mingxi-trafficCityToCity-dg').datagrid('getRowIndex', row); 
	var ed = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
		index:rindex,
		field : 'vehicle'  
	});
	var ed1 = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleLevel'  
	});
	$(ed.target).combotree('setValue', '');
	$(ed1.target).combotree('setValue', '');
}
function reloadLevel(){
	var row = $('#mingxi-trafficCityToCity-dg').datagrid('getSelected');
	var rindex = $('#mingxi-trafficCityToCity-dg').datagrid('getRowIndex', row); 
	var ed = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleLevel'  
	});
	var ed1 = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
		index:rindex,
		field : 'administrativeLevel'  
	});
	var levelText =$(ed1.target).combobox('getValue');
	var level='';
	/* if(levelText=='市级及相当职务人员'){
		level='1';
	} */
	if(levelText=='局级及相当职务人员'){
		level='1';
	
	}if(levelText=='其他人员'){
		level='2';
	}
	var vehicleId = $('#mingxi-trafficCityToCity-dg').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleId'  
	});
	var url = base+'/vehicle/comboboxJsons?parentCode='+$(vehicleId.target).textbox('getValue')+'&level='+level;
   	$(ed.target).combotree('reload', url);
}
//计算申请金额
function addNum5(newValue,oldValue) {
		var num = 0;
		var rows = $('#mingxi-trafficCityToCity-dg').datagrid('getRows');
		var row = $('#mingxi-trafficCityToCity-dg').datagrid('getSelected');//获得选择行
		var index=$('#mingxi-trafficCityToCity-dg').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#mingxi-trafficCityToCity-dg').datagrid('getEditors', index);
		for(var i=0;i<rows.length;i++){
			if(i!=index){
				if(rows[i].reimbSum!=""&&rows[i].reimbSum!=null){
					num += parseFloat(rows[i].reimbSum);
				}
			}
		}
		if(newValue!=""&&newValue!=null) {
			num += parseFloat(newValue);
		}
		$('#longTrafficMoney').val(num.toFixed(2));
		$('#traffic1Amount').html(fomatMoney(num,2)+"[元]");
		addTotalAmount()
}
</script>
