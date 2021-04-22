
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title></title>
<style type="text/css">
.tabletop{margin: 0 10px 10px 10px;background-color: #fff;font-family: "微软雅黑"}
.queryth{text-align: right;}
</style>
</head>
<!-- 配置连线跳转条件页面 -->
<body>
	<div style="width:100%; white-space:nowrap;">
	<div  style="height:30px">
	<input type="hidden" id="ruleJson" name="ruleJson"/>
	<input type="hidden" id="customs" name="customs"/>
	<input type="hidden" id="resultText" name="resultText"/>
	<input type="hidden" id="editLink_FPId" name="FPId"  value="${flowId}" />
	<input type="hidden" id="editLink_fromKey" name="fromKey"  value="${fromKey}" />
	<input type="hidden" id="editLink_toKey" name="toKey"  value="${toKey}" />
	<a href="javascript:void(0)" onclick="editLinkremoveit()" style="float: right;"><img src="${base}/resource-modality/img/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="editLinkappend()" style="float: right;"><img src="${base}/resource-modality/img/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div>	      
	<table id="editLink_table" class="easyui-datagrid" style="width:100%;height:auto"
	data-options="
	singleSelect: true,
	rownumbers : true,
	url: '${base}/wflow/findByFlowIdAndKey?flowId=${flowId}&fromKey=${fromKey}&toKey=${toKey}',
	method: 'post',
	onClickRow: onClickRow,
	onLoadSuccess:setCombotree  
	">
		<thead>
			<tr>
			<th data-options="field:'fieldNameText' ,align:'center',width:'33%',editor:{type: 'combotree',options: {valueField:'id',textField:'text',data:[
                	{id:'F_AMOUNT',text:'金额'},
                	{id:'F_PLAN_CHANGE_STATUS',text:'付款计划变更状态'},
                	{id:'F_SPECIAL_MATTER',text:'直接报销特殊事项'}
                	<c:if test="${bean.FBusiArea=='HTND'}">,
                		{id:'F_ASSIS_DEPT_NAME',text:'协调部门'},
                		{id:'F_STANDARD',text:'制式合同'}
                	</c:if>
                	],
                	prompt:'--请选择--',panelHeight:'atuo',editable: false,
                	onSelect:getFieldName
                	}}">条件字段</th>
			<th data-options="field:'customText' ,align:'center',width:'33%',editor:{type: 'combotree',options: {valueField:'id',textField:'text',
                	prompt:'--请选择--',panelHeight:'atuo',editable: false
                	}}">条件</th>
             <th data-options="field:'custom',width:0,editor:'textbox',hidden:true" ></th>
             <th data-options="field:'fieldName',width:0,editor:'textbox',hidden:true" ></th>
			<th data-options="field:'fieldValue',width:'33%',align:'center',editor:{type:'combotree',options:{precision:2,groupSeparator:',',editable: true}}" >值</th>
			</tr>
		</thead>
	</table>
	
</div>
	<div class="win-left-bottom-div">
			<a href="javascript:void(0)" onclick="save()">
				<img src="${base}/resource-modality/img/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
			</a>&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="closeFirstWindow()">
				<img src="${base}/resource-modality/img/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
			</a>
	</div>
<script type="text/javascript">
var editLinkEditIndex = undefined;

function getcustom(item){
	 var tr = $('#editLink_table').datagrid('getEditors', editLinkEditIndex);
	 tr[2].target.textbox('setValue', item.id);
	 nowcustom=item.id;
}
function getFieldName(item){
	var tr = $('#editLink_table').datagrid('getEditors', editLinkEditIndex);
	var data = [];
    data.push( {id:'14',text:'设备与安技处'},
               {id:'35',text:'总务处'},
               {id:'20',text:'创业就业指导中心'});
	var data3 = [];
    data3.push( {id:'0',text:'是'},
               {id:'1',text:'否'});
	var data6 = [];
    data6.push( {id:'0',text:'否'},
               {id:'1',text:'是'});
	var data5 = [];
    data5.push( {id:'0',text:'未变更'},
               {id:'1',text:'已变更'});
    
    var data1=[];
    data1.push({id:'>',text:'大于'},
      	{id:'>=',text:'大于等于'},
      	{id:'<',text:'小于'},
      	{id:'<=',text:'小于等于'},
      	{id:'=',text:'等于'}
      	);
    var data2=[];
    data2.push(
      	{id:'=',text:'等于'}
      	);
	if(item.id=="F_AMOUNT"){//金额
		$(tr[1].target).combotree('clear');
		$(tr[4].target).combotree('clear');
		$(tr[1].target).combotree('loadData', data1);
	}
	if(item.id=="F_ASSIS_DEPT_NAME"){//协调部门
		$(tr[1].target).combotree('clear');
		$(tr[4].target).combotree('clear');
		$(tr[1].target).combotree('loadData', data2);
		$(tr[4].target).combotree('loadData', data);
	}
	if(item.id=="F_STANDARD"){//制式合同
		
		$(tr[1].target).combotree('clear');
		$(tr[4].target).combotree('clear');
		$(tr[1].target).combotree('loadData', data2);
		$(tr[4].target).combotree('loadData', data3);
	}
	if(item.id=="F_PLAN_CHANGE_STATUS"){//付款计划变更状态
		
		$(tr[1].target).combotree('clear');
		$(tr[4].target).combotree('clear');
		$(tr[1].target).combotree('loadData', data2);
		$(tr[4].target).combotree('loadData', data5);
	}
	if(item.id=="F_SPECIAL_MATTER"){//直接报销特殊事项
		
		$(tr[1].target).combotree('clear');
		$(tr[4].target).combotree('clear');
		$(tr[1].target).combotree('loadData', data2);
		$(tr[4].target).combotree('loadData', data6);
	}
}
function setCombotree() {
	var rows = $(this).datagrid('getRows');
    for(var index=0;index<rows.length ; index++){
    	$('#editLink_table').datagrid('beginEdit', index);
        var tr = $('#editLink_table').datagrid('getEditors', index);
        var fieldtext=tr[3].target.textbox('getValue');
        tr[0].target.combotree('setValue',fieldbol(fieldtext)); 
        var text=tr[2].target.textbox('getValue');
        tr[1].target.combotree('setValue',symbol(text)); 
		$('#editLink_table').datagrid('endEdit', index);
    }
}

function editLinkappend() {//
	 var row= {
			 fieldNameText:'',
			fieldName:'',
			customText:'',
			custom:'',
			fieldValue:null
		}; 
	 if (editLinkEndEditing()) {
			$('#editLink_table').datagrid('appendRow', row);
			editLinkEditIndex = $('#editLink_table').datagrid('getRows').length - 1;
			$('#editLink_table').datagrid('selectRow', editLinkEditIndex).datagrid('beginEdit',
					editLinkEditIndex);
		}
}
//删除一行
function editLinkremoveit() {
	if (editLinkEditIndex == undefined) {
		return
	}
	$('#editLink_table').datagrid('cancelEdit', editLinkEditIndex).datagrid('deleteRow',
			editLinkEditIndex);
	editLinkEditIndex = undefined;
}
//设计规则
function setRule() {
	var resultcustoms = '';
	var resultText='';
	
	//获取所有行
	var rows = $('#editLink_table').datagrid('getRows');
	var customArr = new Array(rows.length);//条件符号数组
	var fieldValueArr = new Array(rows.length);//值数组
	for(var index=0;index<rows.length;index++){
		//把 条件字段、条件、值组装起来
		var fieldName=rows[index]['fieldName'];//条件
		var custom=rows[index]['custom'];//条件
		var fieldValue=rows[index]['fieldValue'];//值
		customArr[index]=custom;
		fieldValueArr[index]=fieldValue;
		// 项目申报和合同拟定合同金额是万元,需要除以10000
		// 得到结果 例如  F_AMOUNT>10000
		var text = null;
		var customs = null;
		var fBusiArea ='${bean.FBusiArea}';
		if(fieldName=='F_AMOUNT'){ //如果条件字段是金额
			if(fBusiArea=='XMSB'){
				text=fieldbol(fieldName)+symbol(custom)+fieldValue/10000+'万元';
				customs=fieldName+custom+fieldValue/10000; 
			}else{
				text=fieldbol(fieldName)+symbol(custom)+fieldValue+'元';
				customs=fieldName+custom+fieldValue; 
			}
		}else if(fieldName=='F_ASSIS_DEPT_NAME'){// //如果条件字段是协调部门
			text=fieldbol(fieldName)+symbol(custom)+fieldValue;
			customs=fieldName+custom+valuebol(fieldValue);
		}else if(fieldName=='F_STANDARD'){// //如果条件字段是制式合同
			text=fieldbol(fieldName)+symbol(custom)+fieldValue;
			customs=fieldName+custom+valuebol(fieldValue);
		}else if(fieldName=='F_PLAN_CHANGE_STATUS'){// 付款计划变更状态
			text=fieldbol(fieldName)+symbol(custom)+fieldValue;
			customs=fieldName+custom+valuebol(fieldValue);
		}else if(fieldName=='F_SPECIAL_MATTER'){// 直接报销特殊事项
			text=fieldbol(fieldName)+symbol(custom)+fieldValue;
			customs=fieldName+custom+valuebol(fieldValue);
		}
		
		
		if(resultcustoms==''){
	 		resultcustoms=customs;
	 		resultText=text;
	 	}else{
	 		resultcustoms=resultcustoms+' and '+customs; 
	 		resultText=resultText+' 且 '+text;
	 	}
	}
	
/* 	if(customArr.length>2){
		 $.messager.alert('系统提示', '配置条件不符合逻辑！', 'info');
	     return false;
	}
	if(customArr.length==2){
		var customSortArr=customArr.slice().sort();
		for(var i=0;i<customSortArr.length;i++){
		    if (customSortArr[i]==customSortArr[i+1]){
		        $.messager.alert('系统提示', '配置条件不符合逻辑！', 'info');
		        return false;
		    }  
		}
		
		if(customArr[0]=='>' || customArr=='>='){
			if(fieldValueArr[0]>fieldValueArr[1]){
				 $.messager.alert('系统提示', '配置条件不符合逻辑！', 'info');
			     return false;
			}
		}
		if(customArr[0]=='<' || customArr=='<='){
			if(fieldValueArr[0]<fieldValueArr[1]){
				 $.messager.alert('系统提示', '配置条件不符合逻辑！', 'info');
			     return false;
			}
		}
	} */
	
	$('#customs').val(resultcustoms);
	$('#resultText').val(resultText);
	return true;
}


//使列表结束编辑状态
function editLinkaccept() {
	if (editLinkEndEditing()) {
		$('#editLink_table').datagrid('acceptChanges');
	}
}

//表格结束编辑状态
function editLinkEndEditing() {
	if (editLinkEditIndex == undefined) {
		return true;
	}
	if ($('#editLink_table').datagrid('validateRow', editLinkEditIndex)) {
		
		 //下面5行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#editLink_table').datagrid('getEditors', editLinkEditIndex);
		var fieldName=tr[0].target.combotree('getValue');
		tr[3].target.textbox('setValue',fieldName); 
		var stom=tr[1].target.combotree('getValue');
		tr[2].target.textbox('setValue',stom); 
		
		var fieldText=tr[0].target.combotree('getText');
		tr[0].target.combotree('setValue',fieldText); 
		var text=tr[1].target.combotree('getText');
		tr[1].target.combotree('setValue',text); 
		var text=tr[4].target.combotree('getText');
		tr[4].target.combotree('setValue',text);  
		
		$('#editLink_table').datagrid('endEdit', editLinkEditIndex);
		editLinkEditIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//行被点击事件
function onClickRow(index) {
	if (editLinkEditIndex != index) {
		if (editLinkEndEditing()) {
			$('#editLink_table').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editLinkEditIndex = index;
		} else {
			$('#editLink_table').datagrid('selectRow', editLinkEditIndex);
		}
	}
}
function symbol(value){
	if(value=='>'){
		return '大于';
	}else if(value=='>='){
		return '大于等于';
	}else if(value=='<'){
		return '小于';
	}else if(value=='<='){
		return '小于等于';
	}else if(value=='='){
		return '等于';
	}
}
function fieldbol(value){
	if(value=='F_AMOUNT'){
		return '金额';
	}
	if(value=='F_ASSIS_DEPT_NAME'){
		return '协调部门';
	}
	if(value=='F_STANDARD'){
		return '制式合同';
	}
	if(value=='F_PLAN_CHANGE_STATUS'){
		return '付款计划变更状态';
	}
	if(value=='F_SPECIAL_MATTER'){
		return '直接报销特殊事项';
	}
}
function valuebol(value){
	if(value=='设备与安技处'){
		return '14';
	}
	if(value=='总务处'){
		return '35';
	}
	if(value=='创业就业指导中心'){
		return '20';
	}
	if(value=='否'){
		return '0';
	}
	if(value=='是'){
		return '1';
	}
	if(value=='未变更'){
		return '0';
	}
	if(value=='已变更'){
		return '1';
	}
}
function save(){
	editLinkaccept();
	var editLinkrows = $('#editLink_table').datagrid('getRows');
	var ruleJson = "";
	for (var j = 0; j < editLinkrows.length; j++) {
		ruleJson = ruleJson + JSON.stringify(editLinkrows[j]) + ",";
	}
	$('#ruleJson').val(ruleJson);
	if(!setRule()){ //如果配置条件不正确，无法提交
		return false;
	}
	var custom=$('#customs').val();
	var text=$('#resultText').val();
	var fromKey=$('#editLink_fromKey').val();
	var toKey=$('#editLink_toKey').val();
	var flowId=$('#editLink_FPId').val();
	
	if (custom !='') {
		myDesigner.updateLinkDataByKey(fromKey,toKey,text,custom);
		$.ajax({
			async:false,
			type:"POST",
	        url:base+"/wflow/saveRule",
	        data:{flowId:flowId,fromKey:fromKey,toKey:toKey,ruleJson:ruleJson},
	        success:function(data){
	        	closeFirstWindow();
	        }
	    });
	} else {
		$.messager.alert('系统提示', '请填写跳转条件！', 'info');
	}
}

</script>
</body>
</html>

