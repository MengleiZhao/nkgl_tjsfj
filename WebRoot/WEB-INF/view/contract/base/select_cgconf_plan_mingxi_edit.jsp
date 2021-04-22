<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%-- <div style="height:30px">
	<span style="float: left;color: #0000CD;">	
		<input type="hidden" name="fcgtotalPrice" id="uptTotalPrice"/>
		<input type="hidden" id="fPlanTotalAmountUptHid" value="${bean.fPlanTotalAmount}"/>
		<span style="color: red;"  >合计金额： </span>
		<span style="float: right;"  id="totalPricespan" ><fmt:formatNumber groupingUsed="true" value="${bean.fPlanTotalAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
		<span style="float: right;"  id="F_fpAmount" >[元]</span>
	</span>
	<c:if test="${uptOpenType != 'Cdetail'}">
	<a href="javascript:void(0)" onclick="removeitCg()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="appendCg()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	</c:if>
</div> --%>
<table id="contract_cgplan_dg_edit" class="easyui-datagrid"
data-options="
singleSelect: true,
rownumbers : true,
striped:true,
<%-- url: '${base}/cgconfplangl/mingxi?id=${bean.fPurchNo}', --%>

<c:if test="${uptOpenType == 'Cadd'}">
	url: '${base}/Change/getContractRegisterList?fcId=${bean.fcId}&fDataType=0',
</c:if>
<c:if test="${uptOpenType != 'Cadd'}">
	url: '${base}/Change/getContractRegisterList?fId_U=${Upt.fId_U}&fDataType=1',
</c:if>
method: 'post',
<c:if test="${uptOpenType != 'Cdetail'}">
onClickRow: onClickRowCg,
</c:if>
onLoadSuccess:setFsumMoneyCg,
">
<thead>
	<tr>
		<th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'newKind',align:'center',editor:{type:'combotree',options:{editable:true}},valueField:'fpKind',textField:'fpKind'" style="width: 20%">品目</th>
		<th data-options="field:'newName',align:'center',editor:'textbox'" style="width: 20%">商品名称</th>
		<th data-options="field:'newNum',align:'center',editor:{type:'numberbox'}" style="width: 15%">数量</th>
		<th data-options="field:'newUnit',align:'center',editor:'textbox'" style="width: 15%">单位</th>
		<th data-options="field:'newIsImp',align:'center',editor:{type:'combobox',options:{valueField:'value',
				textField:'label',editable:false,data: [{label: '是',value: '是'},{label: '否',value: '否'}]
			}
		}" style="width: 15%">是否进口</th>
		<th data-options="field:'newCompro',align:'center',editor:{type:'textbox',options:{required:false}}" style="width: 28%">相关要求</th>
		<!-- <th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'fmType',hidden:true"></th>
		<th data-options="field:'fmName',align:'center',editor:'textbox'" style="width: 20%">商品名称</th>
		<th data-options="field:'fpNum',align:'center',editor:{type:'numberbox',options:{onChange:numChange,required:true}}" style="width: 15%">数量</th>
		<th data-options="field:'fmModel',align:'center',editor:'textbox'" style="width: 15%">单位</th>
		<th data-options="field:'fWhetherImport',align:'center',editor:{type:'combobox',options:{valueField:'value',
				textField:'label',editable:false,data: [{label: '是',value: '是'},{label: '否',value: '否'}]
			}
		}" style="width: 15%">是否进口</th>
		<th data-options="field:'fsignPrice',align:'center',editor:{type:'numberbox',options:{precision:2,onChange:setFsumMoneyCg}}" style="width: 20%">单价(元)</th>
		<th data-options="field:'fBrand',width:120,align:'center',editor:'textbox'" >品牌</th>				
		<th data-options="field:'fmSpecif',width:120,align:'center',editor:'textbox'" >型号</th>	
		<th data-options="field:'famount',align:'center',editor:{type:'numberbox',options:{precision:2}}" style="width: 20%">总价(元)</th> -->
	</tr>
</thead>
</table>

<input type="hidden" id="mingxiJson" name="mingxi"/>
<script type="text/javascript">
var fpItemsName = 1;
//明细表格添加删除，保存方法
var editIndex_cgplan = undefined;
function endEditingCg() {
	if (editIndex_cgplan == undefined) {
		return true;
	}
	if ($('#contract_cgplan_dg_edit').datagrid('validateRow', editIndex_cgplan)) {
		var ed = $('#contract_cgplan_dg_edit').datagrid('getEditor', {
			index : editIndex_cgplan,
			field : 'costDetail'
		});
		/* console.log(ed);
		var fIsImp = ed.target[0].value;
		if(fIsImp==1){
			ed.target[0].textField='cheshi1';
		}else{
			ed.target[0].textField='cheshi2';
		} */
		$('#contract_cgplan_dg_edit').datagrid('endEdit', editIndex_cgplan);
		editIndex_cgplan = undefined;
		return true;
	} else {
		return false;
	}
}

function acceptEditingCg() {
	if (endEditingCg()) {
		$('#contract_cgplan_dg_edit').datagrid('acceptChanges');
	}
}
function getcgConfPlanEdit(){
	acceptEditingCg();
	var rows = $('#contract_cgplan_dg_edit').datagrid('getRows');
	var entities= '';
	for(i = 0;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	 entities = '[' + entities.substring(0,entities.length -1) + ']';
	 return entities;
}
function onClickRowCg(index) {
	if (editIndex_cgplan != index) {
		if (endEditingCg()) {
			$('#contract_cgplan_dg_edit').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex_cgplan = index;
			/* var itemsName = '${bean.fpItemsName}'; */
			var ed1 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'newKind'});
			var ed2 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'newName'});
			var ed3 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'newNum'});
			var ed4 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'newUnit'});
			var ed5 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'newIsImp'});
			var ed6 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'newCompro'});
			/* var ed7 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'fmSpecif'});
			var ed8 = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:editIndex_cgplan,field:'famount'});
			var row = $('#contract_cgplan_dg_edit').datagrid('getSelected', index);
			if (typeof(row.fpId) !== "undefined") {
				$(ed7.target).textbox('disable',false);
				if(itemsName=='PMMC-1' || itemsName=='PMMC-2' || itemsName=='PMMC-3'){
					$(ed8.target).numberbox('disable',false);
				}
				if(itemsName=='PMMC-4' || itemsName=='PMMC-5'){
					$(ed2.target).numberbox('disable',false);
				}	
			} */
				$(ed1.target).combotree('disable',true);
				$(ed2.target).textbox('disable',true);
				$(ed4.target).textbox('disable',true);
				$(ed5.target).combobox('disable',true);
				$(ed6.target).textbox('disable',true);
		} else {
			$('#contract_cgplan_dg_edit').datagrid('selectRow', editIndex_cgplan);
		}
	}
}
function appendCg() {//未配置采购类型不可添加采购清单
	 if (endEditingCg()) {
			$('#contract_cgplan_dg_edit').datagrid('appendRow', {});
			editIndex_cgplan = $('#contract_cgplan_dg_edit').datagrid('getRows').length - 1;
			$('#contract_cgplan_dg_edit').datagrid('selectRow', editIndex_cgplan).datagrid('beginEdit',editIndex_cgplan);
		} 
}
function removeitCg() {
	if (editIndex_cgplan == undefined) {
		return;
	}
	$('#contract_cgplan_dg_edit').datagrid('cancelEdit', editIndex_cgplan).datagrid('deleteRow',editIndex_cgplan);
	editIndex_cgplan = undefined;
	//修改申请总额
	setFsumMoneyCg(0,0);
}

function numChange(newValue,oldValue){
	var selectIndex=$('#contract_cgplan_dg_edit').datagrid('getRowIndex',$('#contract_cgplan_dg_edit').datagrid('getSelected'));
	var selectRows = $('#contract_cgplan_dg_edit').datagrid('getRows');
	if (selectRows[selectIndex]['fCheckedNum'] > newValue && newValue != '') {
		var ed = $('#contract_cgplan_dg_edit').datagrid('getEditor', {index:selectIndex,field:'fpNum'});
		alert('更新后数量不可低于已验收数量');
		$(ed.target).numberbox('reset');
	}
	setFsumMoneyCg(newValue,oldValue);
}

//计算总额
function setFsumMoneyCg(newValue,oldValue) {
	//服务、工程类采购清单不显示单价、数量
	var itemsName = '${bean.fpItemsName}';
	if(itemsName=='PMMC-4' || itemsName=='PMMC-5'){
		$('#contract_cgplan_dg_edit').datagrid('hideColumn', 'fpNum');
		$('#contract_cgplan_dg_edit').datagrid('hideColumn', 'fsignPrice');
	}
	
	var totalFsumMoney = 0;
	var fsumMoney = 0;
	var index=$('#contract_cgplan_dg_edit').datagrid('getRowIndex',$('#contract_cgplan_dg_edit').datagrid('getSelected'));
	var rows = $('#contract_cgplan_dg_edit').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumMoney=setEditingCg(rows,i);
		}else{
			totalFsumMoney+=addNumCg(rows,i);
		}  
	 
	}
	totalFsumMoney=totalFsumMoney+fsumMoney;
	//$('#uptTotalPrice').numberbox('setValue',totalFsumMoney.toFixed(2));
	$('#F_fpAmount').html( totalFsumMoney.toFixed(2)+'[元]');
	
	if (uptOpenType == 'Cdetail') {
		if (rows.length > 0) {
			$("input[name=shoppingList]:eq(0)").prop("checked",'checked');
		} else {
			$("input[name=shoppingList]:eq(1)").prop("checked",'checked');
		}
	}
}
//未编辑或者已经编辑完毕的行，计算优惠后总价
function addNumCg(rows,index){
	var totalPrice=0;
	var famount=rows[index]['famount'];
	if(famount!="" && famount!=null){
		totalPrice= parseFloat(famount);
	}
	return totalPrice;
}
//对于正在编辑的行，计算优惠后总价
function setEditingCg(rows,index){
    var editors = $('#contract_cgplan_dg_edit').datagrid('getEditors', index);  
    var fnum = editors[1]; 
    var funitPrice = editors[4];   
    var fsumMoney = editors[7];
    var totalPrice = (fnum.target.val())*(funitPrice.target.val());		
    fsumMoney.target.numberbox("setValue",totalPrice);
    return totalPrice;
}

function totalFsumMoneyCg(newValue,oldValue){
	if(fpItemsName==0){
		var totalFsumMoney = 0;
		var fsumMoney = 0;
		var index=$('#contract_cgplan_dg_edit').datagrid('getRowIndex',$('#contract_cgplan_dg_edit').datagrid('getSelected'));
		var rows = $('#contract_cgplan_dg_edit').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i==index){
				var editors = $('#contract_cgplan_dg_edit').datagrid('getEditors', i);
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
		//$('#uptTotalPrice').numberbox('setValue',totalFsumMoney.toFixed(2));
		$('#F_fpAmount').html( totalFsumMoney.toFixed(2)+'[元]');	
	}else {
		var totalFsumMoney = 0;
		var fsumMoney = 0;
		var index=$('#contract_cgplan_dg_edit').datagrid('getRowIndex',$('#contract_cgplan_dg_edit').datagrid('getSelected'));
		var rows = $('#contract_cgplan_dg_edit').datagrid('getRows');
	    var editors = $('#contract_cgplan_dg_edit').datagrid('getEditors', index);  
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