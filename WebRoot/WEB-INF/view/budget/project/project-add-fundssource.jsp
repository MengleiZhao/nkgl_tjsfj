<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>


<div  style="height:0px">
	<input type="hidden" id="fundsJson" name="fundsJson"/>
	<%-- <a style="float: left;"><img src="${base}/resource-modality/${themenurl}/button/zjly.png"></a>
	<a href="javascript:void(0)" onclick="fundsremoveit()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="fundsappend()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
</div>
<table id="fundssource" class="easyui-datagrid" style="width:870px;height:auto"
data-options="
singleSelect: true,
toolbar: '#tb',
rownumbers : true,
url: '${base}/project/fundssource?FProId=${bean.FProId}',
method: 'post',
onClickRow: onClickRow
">

</table>

<script type="text/javascript">
var fundsEditIndex = undefined;
var selectVal = null;
var selectCode = null;
var numFund=0;
$('#fundssource').datagrid({
	onLoadSuccess:function(data){
		numFund++;
		if(numFund==3&&(data.rows).length==0){
			if (fundsEndEditing()) {
				$('#fundssource').datagrid('appendRow', {fundsSource:'0',fundsSourceText:'经费拨款',amount:0});
				$('#fundssource').datagrid('appendRow', {fundsSource:'1',fundsSourceText:'纳入预算管理行政事业性收费拨款',amount:0});
				$('#fundssource').datagrid('appendRow', {fundsSource:'2',fundsSourceText:'政府性基金拨款',amount:0});
				fundsEditIndex = $('#fundssource').datagrid('getRows').length - 1;
				$('#fundssource').datagrid('selectRow', fundsEditIndex).datagrid('beginEdit',
						fundsEditIndex);
			}
		}
	}
});
//填写清单信息  
$('#fundssource').datagrid({
	columns:[[
				{field:'fundsSource',title:'资金来源编码',width:0,editor:'textbox',hidden:true,formatter:showcode},
		        {field:'fundsSourceText',title:'资金来源名称',width:"51%",editable:'false',readonly:'true',formatter:showtext},
				{field:'amount',title:'金额[元]',width:"51%",editor:{type:'numberbox',options:{precision:2,onChange:setFsumMoney}}}
		    ]]

});

function showtext(row){
	//console.log(row);
	return row;
}
function showcode(){
	return selectCode;
}

function fundsappend() {//未配置采购类型不可添加采购清单
	if (fundsEndEditing()) {
		$('#fundssource').datagrid('appendRow', {fundsSource:selectCode,fundsSourceText:selectVal,amount:0});
		fundsEditIndex = $('#fundssource').datagrid('getRows').length - 1;
		$('#fundssource').datagrid('selectRow', fundsEditIndex).datagrid('beginEdit',
				fundsEditIndex);
	}

	//页面随滚动条置底
	/* var div = document.getElementById('westDiv');
	div.scrollTop = div.scrollHeight; */
}

//计算总额
function setFsumMoney(newValue,oldValue) {
	if(newValue==undefined || oldValue==undefined){
		return false;
	}
	//总金额
	var totalFsumMoney = 0;
	//编辑列金额
	var fsumMoney = 0;
	//获取编辑行
	var index = $('#fundssource').datagrid('getRowIndex',$('#fundssource').datagrid('getSelected'));
	//获取总行数
	var rows = $('#fundssource').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(i==index){	//若当前循环行为编辑行，将修改值赋予编辑列金额
			fsumMoney=parseFloat(newValue);
		}else{			//若当前循环行不为编辑行，合计之前编辑列的金额
			totalFsumMoney+=addNum(rows,i);
		}  
	 
	}
	totalFsumMoney=(parseFloat(totalFsumMoney)+parseFloat(fsumMoney))/10000;
	totalFsumMoney=parseFloat(totalFsumMoney);
	$('#pro_add_FProBudgetAmount').textbox('setValue',totalFsumMoney.toFixed(6));
}
//未编辑或者已经编辑完毕的行
function addNum(rows,index){
	//获取资金来源中金额列的值
	var amount = rows[index]['amount'];
	//若转换后金额列的值为NaN
	if(!isNaN(parseFloat(amount))){
		return parseFloat(amount); 
	}
	return 0.00;
}


//删除一行
function fundsremoveit() {
	if (fundsEditIndex == undefined) {
		return
	}
	$('#fundssource').datagrid('cancelEdit', fundsEditIndex).datagrid('deleteRow',
			fundsEditIndex);
	fundsEditIndex = undefined;
	setFsumMoney(0,0);
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
		
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#fundssource').datagrid('getEditors', fundsEditIndex);
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
