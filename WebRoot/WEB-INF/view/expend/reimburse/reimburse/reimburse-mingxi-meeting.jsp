<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<table id="reimb-meeting-mingxi" class="easyui-datagrid" style="width:693px;height:auto;"
	data-options="
	toolbar: '#appli-detail-tb',
	<c:if test="${operation=='add'}">
	url: '${base}/apply/mingxi?id=${applyBean.gId}',
	</c:if>
	<c:if test="${operation!='add'}">
	url: '${base}/apply/reimbmingxi?id=${bean.rId}',
	</c:if>
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:false,
	scrollbarSize:0,
	singleSelect: true,
	onClickRow: onClickRowMeetingReim,
	">
	<thead>
		<tr>
			<c:if test="${operation!='add'}">
				<th data-options="field:'cId',hidden:true"></th>
			</c:if>
				<th data-options="field:'costDetail',required:'required',align:'center',width:170">费用名称</th>
				<th data-options="field:'standard',required:'required',align:'center',width:180">费用标准（元/人天）</th>
				<th data-options="field:'totalStandard',required:'required',align:'center',width:180">总额标准[元]</th>
			<c:if test="${empty detail}">
				<th data-options="field:'remibAmount',required:'required',align:'center',width:166,editor:{type:'numberbox',options:{onChange:addNum,precision:2,groupSeparator:','}}">报销金额[元]</th>
			</c:if>
			<c:if test="${!empty detail}">
				<th data-options="field:'remibAmount',required:'required',align:'center',width:166">报销金额[元]</th>
			</c:if>
		</tr>
	</thead>
	</table>
</div>
<script type="text/javascript">
//明细表格添加删除，保存方法
var editIndexMeetingReim = undefined;
function endEditingMeetingReim() {
	if (editIndexMeetingReim == undefined) {
		return true
	}
	if ($('#reimb-meeting-mingxi').datagrid('validateRow', editIndexMeetingReim)) {
		$('#reimb-meeting-mingxi').datagrid('endEdit', editIndexMeetingReim);
		editIndexMeetingReim = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowMeetingReim(index) {
	var rows = $('#reimb-meeting-mingxi').datagrid('getRows');
	var rowsIndex = rows[index];
	var boxHotel = $("input[name='fWAHotel']:checked").val();
	var boxFood = $("input[name='fWAFood']:checked").val();
	if(rowsIndex.costDetail=='住宿费' && boxHotel=='1'){
		if (editIndexMeetingReim != index) {
			if (endEditingMeetingReim()) {
				$('#reimb-meeting-mingxi').datagrid('selectRow', index).datagrid('beginEdit',index);
				editIndexMeetingReim = index;
			} else {
				$('#reimb-meeting-mingxi').datagrid('selectRow', editIndexMeetingReim);
			}
		}
	}
	if(rowsIndex.costDetail=='伙食费' && boxFood=='1'){
		if (editIndexMeetingReim != index) {
			if (endEditingMeetingReim()) {
				$('#reimb-meeting-mingxi').datagrid('selectRow', index).datagrid('beginEdit',index);
				editIndexMeetingReim = index;
			} else {
				$('#reimb-meeting-mingxi').datagrid('selectRow', editIndexMeetingReim);
			}
		}
	}
	if(rowsIndex.costDetail=='其他费用'){
		if (editIndexMeetingReim != index) {
			if (endEditingMeetingReim()) {
				$('#reimb-meeting-mingxi').datagrid('selectRow', index).datagrid('beginEdit',index);
				editIndexMeetingReim = index;
			} else {
				$('#reimb-meeting-mingxi').datagrid('selectRow', editIndexMeetingReim);
			}
		}
	}
}
function acceptMeetingReim() {
	if (endEditingMeetingReim()) {
		$('#reimb-meeting-mingxi').datagrid('acceptChanges');
	}
}

//计算申请总额
function addNum(newValue,oldValue) {
		var row = $('#reimb-meeting-mingxi').datagrid('getSelected');//获得选择行
		var index=$('#reimb-meeting-mingxi').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#reimb-meeting-mingxi').datagrid('getEditors', index);
		var standar= parseFloat(row.totalStandard);//获得选中行的开支标准
		var reimbAmount= isNaN(parseFloat(newValue))?0:parseFloat(newValue);//获得选中行的报销金额
		/* if(isNaN(standar)){
			standar=0;
		}
		if(parseFloat(reimbAmount)>parseFloat(standar)){
			alert('报销金额不能超出开支标准，请核对！');
			tr[0].target.numberbox('setValue',0);
			return false;
		} */
		
		var num = 0;
		var rows = $('#reimb-meeting-mingxi').datagrid('getRows');
		var totalStd =0;
		for(var i=0;i<rows.length;i++){
			if(i!=index){
				if(rows[i].remibAmount!=""&&rows[i].remibAmount!=null){
					num += parseFloat(rows[i].remibAmount);
				}
			}
			totalStd += parseFloat(rows[i].totalStandard);
		}
		num += parseFloat(reimbAmount);
		if(num>totalStd){
			alert('申请金额超出总额标准！');
			tr[0].target.numberbox('setValue',0);
			return false;
		}
		$('#reimburseAmount').val(num.toFixed(2));
		$('#reimbAmount_span').html(fomatMoney(num,2));
		$('#p_amount').html(fomatMoney(num,2)+"[元]");
				cx();
}

function onLoadSuccessMeetingReim(){
	acceptMeetingReim();
	var rows = $('#reimb-meeting-mingxi').datagrid('getRows');
	var num = 0;
	for(var i=0;i<rows.length;i++){
		num += addNumsReim(rows,i);
	}
	$('#reimburseAmount').val(num.toFixed(2));
	$('#reimbAmount_span').html(fomatMoney(num,2));
	$('#p_amount').html(fomatMoney(num,2)+"[元]");
	cx();
}

//未编辑或者已经编辑完毕的行
function addNumsReim(rows,index){
	var amount=isNaN(parseFloat(rows[index].remibAmount))?0:parseFloat(rows[index].remibAmount);
	return amount;
}
</script>
