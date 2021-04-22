<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!-- 其他费用 -->
<div class="window-tab" style="margin-left: 0px;padding-top: 0px;">
	<table id="register_tab_id" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	<c:if test="${operation=='accounts_current_detail'}">
	url: '${base}/accountsCurrent/registerMX?id=${bean.proCode}&type=2',
	</c:if>
	<c:if test="${operation!='add' and operation!='accounts_current_detail'}">
	url: '${base}/accountsRegister/registerMX?id=${bean.fMSId}&type=2',
	</c:if>
	<c:if test="${operation=='add'}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${operation!='detail' && operation!='check'}">
	onClickRow: onClickRow2, 
	</c:if>
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'frId',hidden:true"></th>
				<th data-options="field:'fMSId',hidden:true"></th>
				<th data-options="field:'oppositeUnit',required:'required',align:'center',width:195,editor:{type:'textbox'}">对方单位名称</th>
				<th data-options="field:'planMoney',required:'required',align:'center',width:191,editor:{type:'numberbox',options:{onChange:onChangeMoney,precision:2,groupSeparator:','}}">金额（元）</th>
				<th data-options="field:'planTime',width:140,align:'center',editor:{type:'datebox', options:{editable:false,onHidePanel:onSelectPlanTime}}">预计来款日期</th>
				<th data-options="field:'invoiceKindShow',width:150, editor:{
                 type:'combobox',
                 options:{
                 	editable:false,
                     valueField:'code',
                     textField:'text',
                     multiple: false,
                     url:base+'/Formulation/lookupsJson?parentCode=KPZL',
                     onHidePanel:invoiceKindSetCode
                 }}">开票种类</th>
				<th data-options="field:'invoiceKind',required:'required',align:'center',width:195,hidden:true,editor:{type:'textbox'}"></th>
			</tr>
		</thead>
	</table>
	<%-- <div id="register_id" style="height:20px;">
		<a href="javascript:void(0)" onclick="removeit2()" id="otherRemoveitId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="append2()" id="otherAppendId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</div> --%>
</div>
<input type="hidden" id="registerJson" name="registerJson"/>
<script type="text/javascript">

//接待人员表格添加删除，保存方法
var editIndex2 = undefined;
function endEditing2() {
	if (editIndex2 == undefined) {
		return true;
	}
	if ($('#register_tab_id').datagrid('validateRow', editIndex2)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#register_tab_id').datagrid('getEditors', editIndex2);
		var text3=tr[3].target.combobox('getText');
		if(text3!='--请选择--'){
			tr[3].target.combobox('setValue',text3);
		}
		$('#register_tab_id').datagrid('endEdit', editIndex2);
		editIndex2 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow2(index) {
	if (editIndex2 != index) {
		if (endEditing2()) {
			var rows = $('#register_tab_id').datagrid('getRows');
			var index=$('#register_tab_id').datagrid('getRowIndex',$('#register_tab_id').datagrid('getSelected'));
		     if(rows[index].fCostName!='市内交通费' && rows[index].fCostName!='伙食补助费'){
				$('#register_tab_id').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex2 = index;
		     }
		} else {
			$('#register_tab_id').datagrid('selectRow', editIndex2);
		}
	}
}
function append2() {
	if (endEditing2()) {
		$('#register_tab_id').datagrid('appendRow', {});
		editIndex2 = $('#register_tab_id').datagrid('getRows').length - 1;
		$('#register_tab_id').datagrid('selectRow', editIndex2).datagrid('beginEdit',editIndex2);
	}
}
function removeit2() {
	if (editIndex2 == undefined) {
		return
	}
	$('#register_tab_id').datagrid('cancelEdit', editIndex2).datagrid('deleteRow',
			editIndex2);
	editIndex2 = undefined;
}
function accept2() {
	if (endEditing2()) {
		$('#register_tab_id').datagrid('acceptChanges');
	}
}

function getRegisterJson(){
	accept2();
	var rows3 = $('#register_tab_id').datagrid('getRows');
	var registerJson = "";
	var lkmxFlag = true;
	for (var y = 0; y < rows3.length; y++) {
		if(rows3[y].oppositeUnit=='' || rows3[y].planMoney=='' || rows3[y].planTime=='' || rows3[y].invoiceKindShow==''){
			lkmxFlag = false;
			break;
		}
	}
	for (var i = 0; i < rows3.length; i++) {
		registerJson = registerJson + JSON.stringify(rows3[i]) + ",";
	}
	$('#registerJson').val(registerJson);
	return lkmxFlag;
}

function invoiceKindSetCode(){
	var index=$('#register_tab_id').datagrid('getRowIndex',$('#register_tab_id').datagrid('getSelected'));
	var invoiceKind = $('#register_tab_id').datagrid('getEditor',{
		index:index,
		field:'invoiceKind'
	});
	var invoiceKindShow = $('#register_tab_id').datagrid('getEditor',{
		index:index,
		field:'invoiceKindShow'
	});
	$(invoiceKind.target).textbox('setValue', $(invoiceKindShow.target).combobox('getValues'));
}
function ChangeDateFormat1(val) {
	var t, y, m, d, h, i, s;
    if(val==null){
  	  return "";
    }
    t = new Date(val);
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
}

function onChangeMoney(newVal,oldVal){
	$('#registerMoney').val(newVal);
}
function onLoadSuccessAppend(){
	if(${operation=='add'}){
		$('#register_tab_id').datagrid('appendRow',{});
	}
}
function onSelectPlanTime(){
	var index=$('#register_tab_id').datagrid('getRowIndex',$('#register_tab_id').datagrid('getSelected'));
	var editors = $('#register_tab_id').datagrid('getEditors', index);  
 	var myDate = new Date;
    var year = myDate.getFullYear(); //获取当前年
    var mon = myDate.getMonth() + 1; //获取当前月
    var date = myDate.getDate(); //获取当前日
    var cuTime = year+"-"+mon+"-"+date;
    var time = Date.parse(cuTime)-28800000;
	var endEditors = Date.parse(new Date(editors[2].target.val()));
	if(!isNaN(endEditors)){
    	if(time>endEditors){
       		alert("结束日期不能小于开始日期！");
       		editors[2].target.datebox('setValue', '');
   		}
    } 
}
</script>