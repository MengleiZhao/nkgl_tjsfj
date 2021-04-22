<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>


<div  style="height:30px">
	<input type="hidden" id="fundsJson" name="fundsJson"/>
	<a style="float: left;"><img src="${base}/resource-modality/${themenurl}/button/zjly.png"></a>
</div>
<table id="fundssource" class="easyui-datagrid" style="width:870px;height:auto"
data-options="
singleSelect: true,
toolbar: '#tb',
rownumbers : true,
 <c:if test="${empty bean.FProId}">
url: '',
</c:if>
<c:if test="${!empty bean.FProId}">
url: '${base}/project/fundssource?FProId=${bean.FProId}',
</c:if>
method: 'post',
onClickRow: onClickRow
">

</table>

<script type="text/javascript">
var fundsEditIndex = undefined;
//填写清单信息   设置列固定  左右滚动
$('#fundssource').datagrid({
columns:[[
			{field: 'fundsSource',title:'资金来源编码',width:0,editor:'textbox',hidden:true},
			{field: 'fundsSourceText', title: '资金来源名称', width: "50%"  ,editor:{type:'textbox',options:{precision:2,readonly:true}}},
	        {field:'amount',title:'金额[元]',width:"50%",editor:{type:'numberbox',options:{precision:2,readonly:true}},formatter:function(value,row,index){
	        	return fomatMoney(value,2);
			}}
	    ]]
}); 
function fundsappend() {//未配置采购类型不可添加采购清单
	 if (fundsEndEditing()) {
			$('#fundssource').datagrid('appendRow', {
				status : 'P'
			});
			fundsEditIndex = $('#fundssource').datagrid('getRows').length - 1;
			$('#fundssource').datagrid('selectRow', fundsEditIndex).datagrid('beginEdit',
					fundsEditIndex);
		} 
	//页面随滚动条置底
	/* var div = document.getElementById('westDiv');
	div.scrollTop = div.scrollHeight; */
}

//计算配置计划总额
function setFsumMoney(newValue,oldValue) {
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	var totalFsumMoney = 0;
	var fsumMoney = 0;
	var index=$('#fundssource').datagrid('getRowIndex',$('#fundssource').datagrid('getSelected'));
	var rows = $('#fundssource').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumMoney=parseFloat(newValue);
		}else{
			totalFsumMoney+=addNum(rows,i);
		}  
	 
	}
	totalFsumMoney=(parseFloat(totalFsumMoney)+parseFloat(fsumMoney))/10000;
	$('#pro_add_FProBudgetAmount').textbox('setValue',totalFsumMoney.toFixed(6));
}
//未编辑或者已经编辑完毕的行
function addNum(rows,index){
	var amount=rows[index]['amount'];
	return parseFloat(amount); 
}

//加载完以后自动计算金额
$('#fundssource').datagrid({onLoadSuccess : function(data){
	setFsumMoney();
}});

//删除一行
function fundsremoveit() {
	if (fundsEditIndex == undefined) {
		return
	}
	$('#fundssource').datagrid('cancelEdit', fundsEditIndex).datagrid('deleteRow',
			fundsEditIndex);
	fundsEditIndex = undefined;
}
//使列表结束编辑状态
function fundsaccept() {
	if (fundsEndEditing()) {
		$('#fundssource').datagrid('acceptChanges');
	}
}
//资金来源表格结束编辑状态
function fundsEndEditing() {
	if (fundsEditIndex == undefined) {
		return true;
	}
	if ($('#fundssource').datagrid('validateRow', fundsEditIndex)) {
		$('#fundssource').datagrid('endEdit', fundsEditIndex);
		fundsEditIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//行被点击事件
function onClickRow(index) {
	if (fundsEditIndex != index) {
		if (fundsEndEditing()) {
			$('#fundssource').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			fundsEditIndex = index;
		} else {
			$('#fundssource').datagrid('selectRow', fundsEditIndex);
		}
	}
}
</script>
