<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="dg" class="easyui-datagrid"
data-options="
singleSelect: true,
toolbar: '#tb',
rownumbers : true,
striped:true,
<c:if test="${!empty bean.fpId}">
url: '${base}/cgconfplangl/mingxi?id=${bean.fpId}',
</c:if>
method: 'post',
onClickRow: onClickRow
">
<thead>
	<tr>
		<th data-options="field:'mainId',hidden:true"></th>
		<th data-options="field:'fplId',hidden:true"></th>
		<th data-options="field:'fpurCode',hidden:true"></th>
		<th data-options="field:'fpKind',align:'center',editor:{type:'combotree',options:{editable:false,onClick:chooseType,onShowPanel:onShowPanel}},valueField:'fpKind',textField:'fpKind'" style="width: 20%">品目</th>
		<th data-options="field:'fpurName',align:'center',editor:'textbox'" style="width: 20%">名称</th>
		<th data-options="field:'fnum',align:'center',editor:{type:'numberbox',options:{}}" style="width: 15%">数量</th>
		<th data-options="field:'fmeasureUnit',align:'center',editor:'textbox'" style="width: 15%">单位</th>
		<th data-options="field:'fIsImp',align:'center',editor:{type:'combobox',options:{valueField:'value',
				textField:'label',editable:false,data: [{label: '是',value: '是'},{label: '否',value: '否'}]
			}
		}" style="width: 15%">是否进口</th>
		<!-- <th data-options="field:'funitPrice',align:'center',editor:{type:'numberbox',options:{precision:2,onChange:setFsumMoney}}" style="width: 20%">单价(元)</th>
		<th data-options="field:'fsumMoney',align:'center',editor:{type:'numberbox',options:{precision:2,readonly:false,onChange:totalFsumMoney}}" style="width: 20%">申请金额(元)</th> -->
		<th data-options="field:'fcommProp',align:'center',editor:{type:'textbox',options:{required:false}}" style="width: 28%">相关要求</th>
	</tr>
</thead>
</table>
<div id="tb" style="height:30px">
	<a href="javascript:void(0)" onclick="removeit()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="append()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
</div>
<input type="hidden" id="mingxiJson" name="mingxi"/>
<script type="text/javascript">
function chooseType(newValue){
	var row = $('#dg').datagrid('getSelected');//获得选择行
	var index=$('#dg').datagrid('getRowIndex',row);//获得选中行的行号
	var tr = $('#dg').datagrid('getEditors', index);//获取选中行的数据
	
	if(newValue.id.length == 3){
		alert("请选择下级目录的内容");
		tr[0].target.textbox('setValue','');
		return;
	}else{
		tr[0].target.textbox('setValue',newValue.text);
	}
	var str = "其他";
	if(newValue.text.indexOf(str) == '-1'){
		$("#fpMethod").combobox({
			url:'${base}/cgsqsp/cgsqspJson?amount='+666666,
			valueField:'text',
			textField:'text',
		});
	}else{
		amount($("#f_amount").val());
	}
}
var fpItemsName = 1;
//明细表格添加删除，保存方法
var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	if ($('#dg').datagrid('validateRow', editIndex)) {
		var ed = $('#dg').datagrid('getEditor', {
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
		$('#dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index) {
	if (editIndex != index) {
		if (endEditing()) {
			$('#dg').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex = index;
			if(fpItemsName==0){
				var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'fsumMoney'});
				$(ed.target).numberbox('readonly',false); 		
			}
		} else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}
	
}
function onShowPanel(){
	$('#dg').datagrid('getEditors',editIndex)[0].target.combotree({
		url:'${base}/purchaseCatagl/tree',
		onlyLeafCheck:true,
		valueField:'text',
		textField:'text',
		onBeforeExpand:function(node) {
			$('#dg').datagrid('getEditors',editIndex)[0].target.combotree("tree").tree("options").url = '${base}/purchaseCatagl/tree';
		}
	});
}
function append() {//未配置采购类型不可添加采购清单
	 if (endEditing()) {
			$('#dg').datagrid('appendRow', {});
			editIndex = $('#dg').datagrid('getRows').length - 1;
			$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit',editIndex);
			
			$('#dg').datagrid('getEditors',editIndex)[0].target.combotree({
				url:'${base}/purchaseCatagl/tree',
				onlyLeafCheck:true,
				valueField:'text',
				textField:'text',
				onBeforeExpand:function(node) {
					$('#dg').datagrid('getEditors',editIndex)[0].target.combotree("tree").tree("options").url = '${base}/purchaseCatagl/tree';
				}
			});
		} 
document.getElementById("cgqddiv").scrollIntoView();
		
}
function removeit() {
	if (editIndex == undefined) {
		return;
	}
	$('#dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow',editIndex);
	editIndex = undefined;
	//修改申请总额
	/* setFsumMoney(0,0); */
}

function accept() {
	if (endEditing()) {
		$('#dg').datagrid('acceptChanges');
	}
}

//计算总额
/* function setFsumMoney(newValue,oldValue) {
	var totalFsumMoney = 0;
	var fsumMoney = 0;
	var index=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
	var rows = $('#dg').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumMoney=setEditing(rows,i);
		}else{
			totalFsumMoney+=addNum(rows,i);
		}  
	 
	}
	totalFsumMoney=totalFsumMoney+fsumMoney;
	$('#totalPrice').html(totalFsumMoney.toFixed(2)+'元');
	$('#F_fpAmount').val( totalFsumMoney.toFixed(2));
} */
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
    var editors = $('#dg').datagrid('getEditors', index);  
    var fnum = editors[1]; 
    var funitPrice = editors[4];   
    var fsumMoney = editors[5];
    var totalPrice = (fnum.target.val())*(funitPrice.target.val());		
    fsumMoney.target.numberbox("setValue",totalPrice);    
    return totalPrice;
}
 //加载完以后自动计算金额
$('#dg').datagrid({onLoadSuccess : function(data){
	var rows = $('#dg').datagrid('getRows');
	var totalFsumMoney = 0;
	for(var i=0;i<rows.length;i++){
		totalFsumMoney+=parseFloat(rows[i].fsumMoney);
	}
	$('#totalPrice').html(totalFsumMoney.toFixed(2)+'元');
}});


function totalFsumMoney(newValue,oldValue){
	if(fpItemsName==0){
		var totalFsumMoney = 0;
		var fsumMoney = 0;
		var index=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
		var rows = $('#dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i==index){
				var editors = $('#dg').datagrid('getEditors', i);
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
		$('#totalPrice').html(totalFsumMoney.toFixed(2)+'元');
		$('#F_fpAmount').val( totalFsumMoney.toFixed(2));	
	}else {
		var totalFsumMoney = 0;
		var fsumMoney = 0;
		var index=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
		var rows = $('#dg').datagrid('getRows');
	    var editors = $('#dg').datagrid('getEditors', index);  
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
function kindChoose(){
	$("label");
}

</script>