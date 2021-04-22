<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="apply_dg_train_lecturer" class="easyui-datagrid" 
	style="width:693px;height:auto"
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
							}}">职称</th>
				<th data-options="field:'lecturerLevelCode',hidden:true,editor:'textbox'"></th>
				<th data-options="field:'unit',width:150,align:'center',editor:{type:'textbox'}">单位名称</th>
				<th data-options="field:'isOutside',width:90,align:'center',
					editor:{type:'combobox',options:{valueField:'id',textField:'text',data:[
	                	{id:'1',text:'是'},
	                	{id:'0',text:'否'}],
	                	prompt:'-请选择-',panelHeight:'atuo',editable: false}},formatter:isorno">是否异地</th>
				<th data-options="field:'bankCard',width:150,align:'center',editor:'textbox'">讲师银行卡号</th>
				<th data-options="field:'bank',width:180,align:'center',editor:'textbox'">开户行</th>
				<th data-options="field:'phoneNum',width:120,align:'center',editor:'textbox'">手机号</th>
			</tr>
		</thead>
	</table>
</div>

	
<script type="text/javascript">

function isorno(val){
	if(val=='1'){
		return '是';
	}else if(val=='0'){
		return '否';
	}else{
		return val;
	}
}
</script>