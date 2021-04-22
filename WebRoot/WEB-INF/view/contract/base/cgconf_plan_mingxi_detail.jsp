<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="contract_cgplan_dg_detail1" class="easyui-datagrid" style="width:697px;height:auto"
data-options="
singleSelect: true,
toolbar: '#cgplantb',
rownumbers : true,
striped:true,
url: '${base}/cgconfplangl/mingxi?id=${bean.fPurchNo}',
method: 'post',
onLoadSuccess:setFsumMoney
">
<thead>
	<tr>
		<th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'fpKind',align:'center',editor:{type:'combotree',options:{editable:true}},valueField:'fpKind',textField:'fpKind'" style="width: 20%">品目</th>
		<th data-options="field:'fpurName',align:'center',editor:'textbox'" style="width: 20%">商品名称</th>
		<th data-options="field:'fnum',align:'center',editor:{type:'numberbox'}" style="width: 15%">数量</th>
		<th data-options="field:'fmeasureUnit',align:'center',editor:'textbox'" style="width: 15%">单位</th>
		<th data-options="field:'fIsImp',align:'center',editor:{type:'combobox',options:{valueField:'value',
				textField:'label',editable:false,data: [{label: '是',value: '是'},{label: '否',value: '否'}]
			}
		}" style="width: 15%">是否进口</th>
		<th data-options="field:'fcommProp',align:'center',editor:{type:'textbox',options:{required:false}}" style="width: 28%">相关要求</th>
		<!-- <th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'fmType',hidden:true"></th>
		<th data-options="field:'fmName',align:'center',editor:'textbox'" style="width: 20%">商品名称</th>
		<th data-options="field:'fpNum',align:'center',editor:{type:'numberbox'}" style="width: 15%">数量</th>
		<th data-options="field:'fmModel',align:'center',editor:'textbox'" style="width: 15%">单位</th>
		<th data-options="field:'fWhetherImport',align:'center',editor:{type:'combobox',options:{valueField:'value',
				textField:'label',editable:false,data: [{label: '是',value: '是'},{label: '否',value: '否'}]
			}
		}" style="width: 15%">是否进口</th>
		<th data-options="field:'fsignPrice',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 20%">单价(元)</th>
		<th data-options="field:'fBrand',width:120,align:'center',editor:'textbox'" >品牌</th>				
		<th data-options="field:'fmSpecif',width:120,align:'center',editor:'textbox'" >型号</th>	
		<th data-options="field:'famount',align:'center',editor:{type:'numberbox',options:{precision:2,readonly:false,onChange:totalFsumMoney}}" style="width: 20%">中标金额(元)</th> -->
	</tr>
</thead>
</table>
<div id="cgplantb" style="height:30px">
	<span style="float: left;color: #0000CD;">	
		<input type="hidden" name="fcgtotalPrice" id="totalPrice"/>
		<span style="color: red;"  >申请总额： </span>
		<span style="float: right;"  id="totalPricespan" ><fmt:formatNumber groupingUsed="true" value="${bean.fPlanTotalAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
	</span>
	<%-- <a href="javascript:void(0)" onclick="removeit()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="append()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
</div>
<input type="hidden" id="mingxiJson" name="mingxi"/>
<script type="text/javascript">
var fpItemsName = 1;
//明细表格添加删除，保存方法
var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	if ($('#contract_cgplan_dg_detail1').datagrid('validateRow', editIndex)) {
		var ed = $('#contract_cgplan_dg_detail1').datagrid('getEditor', {
			index : editIndex,
			field : 'costDetail'
		});
		/* console.log(ed);
		var fIsImp = ed.target[0].value;
		if(fIsImp==1){
			ed.target[0].textField='cheshi1';
		}else{
			ed.target[0].textField='cheshi2';
		} */
		$('#contract_cgplan_dg_detail1').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function getcgConfPlan(){
	$('#contract_cgplan_dg_detail1').datagrid('acceptChanges');
	var rows = $('#contract_cgplan_dg_detail1').datagrid('getRows');
	var entities= '';
	for(i = 0;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	 entities = '[' + entities.substring(0,entities.length -1) + ']';
	 return entities;
}
function onClickRow(index) {
	if (editIndex != index) {
		if (endEditing()) {
			$('#contract_cgplan_dg_detail1').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex = index;
			if(fpItemsName==0){
				var ed = $('#contract_cgplan_dg_detail1').datagrid('getEditor', {index:editIndex,field:'fsumMoney'});
				$(ed.target).numberbox('readonly',false); 		
			}
		} else {
			$('#contract_cgplan_dg_detail1').datagrid('selectRow', editIndex);
		}
	}
}
function append() {//未配置采购类型不可添加采购清单
	 if (endEditing()) {
			$('#contract_cgplan_dg_detail1').datagrid('appendRow', {});
			editIndex = $('#contract_cgplan_dg_detail1').datagrid('getRows').length - 1;
			$('#contract_cgplan_dg_detail1').datagrid('selectRow', editIndex).datagrid('beginEdit',editIndex);
		} 
document.getElementById("cgqddiv").scrollIntoView();
		
}
function removeit() {
	if (editIndex == undefined) {
		return;
	}
	$('#contract_cgplan_dg_detail1').datagrid('cancelEdit', editIndex).datagrid('deleteRow',editIndex);
	editIndex = undefined;
	//修改申请总额
	setFsumMoney(0,0);
}

function accept() {
	if (endEditing()) {
		$('#contract_cgplan_dg_detail1').datagrid('acceptChanges');
	}
}

//计算总额
function setFsumMoney(newValue,oldValue) {
	var totalFsumMoney = 0;
	var fsumMoney = 0;
	var index=$('#contract_cgplan_dg_detail1').datagrid('getRowIndex',$('#contract_cgplan_dg_detail1').datagrid('getSelected'));
	var rows = $('#contract_cgplan_dg_detail1').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumMoney=setEditing(rows,i);
		}else{
			totalFsumMoney+=addNum(rows,i);
		}  
	 
	}
	totalFsumMoney=totalFsumMoney+fsumMoney;
	$('#totalPrice').numberbox('setValue',totalFsumMoney.toFixed(2));
	$('#F_fpAmount').val( totalFsumMoney.toFixed(2));
}
//未编辑或者已经编辑完毕的行，计算优惠后总价
function addNum(rows,index){
	var totalPrice=0;
	var fnum=rows[index]['fnum'];
	var funitPrice=rows[index]['funitPrice'];
	if(fnum!="" && fnum!=null && funitPrice!="" && funitPrice!=null){
		totalPrice= parseFloat(fnum)*(parseFloat(funitPrice));
	}
	return totalPrice;
}
//对于正在编辑的行，计算优惠后总价
function setEditing(rows,index){
    var editors = $('#contract_cgplan_dg_detail1').datagrid('getEditors', index);  
    var fnum = editors[1]; 
    var funitPrice = editors[4];   
    var fsumMoney = editors[5];
    var totalPrice = (fnum.target.val())*(funitPrice.target.val());		
    fsumMoney.target.numberbox("setValue",totalPrice);    
    return totalPrice;
}

function totalFsumMoney(newValue,oldValue){
	if(fpItemsName==0){
		var totalFsumMoney = 0;
		var fsumMoney = 0;
		var index=$('#contract_cgplan_dg_detail1').datagrid('getRowIndex',$('#contract_cgplan_dg_detail1').datagrid('getSelected'));
		var rows = $('#contract_cgplan_dg_detail1').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i==index){
				var editors = $('#contract_cgplan_dg_detail1').datagrid('getEditors', i);
			    fsumMoney = editors[5].target[0].value;
			}else{
				var fsumMoney1=rows[i]['fsumMoney'];
				if(fsumMoney1=="" ||fsumMoney1==null){
					fsumMoney1= 0;
				}
				totalFsumMoney+=parseFloat(fsumMoney1);
			}  
		}
		totalFsumMoney=parseFloat(totalFsumMoney)+parseFloat(fsumMoney);
		$('#totalPrice').numberbox('setValue',totalFsumMoney.toFixed(2));
		$('#F_fpAmount').val( totalFsumMoney.toFixed(2));	
	}else {
		var totalFsumMoney = 0;
		var fsumMoney = 0;
		var index=$('#contract_cgplan_dg_detail1').datagrid('getRowIndex',$('#contract_cgplan_dg_detail1').datagrid('getSelected'));
		var rows = $('#contract_cgplan_dg_detail1').datagrid('getRows');
	    var editors = $('#contract_cgplan_dg_detail1').datagrid('getEditors', index);  
	    var fnum = editors[1]; 
	    var funitPrice = editors[4];   
	    var fsumMoney = editors[5];
	    var totalPrice = (fnum.target.val())*(funitPrice.target.val());		
		if(totalPrice!=fsumMoney.target.val()){
			fsumMoney.target.numberbox('setValue',totalPrice);
			alert('申请金额不等于数量X单价，请正确填写');
			return false;
		}
		
	}
} 
 
</script>