<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
	<div id="contractTb" style="overflow:auto;">
		<span style="float: left;color: #0000CD;">	
			<input type="hidden" id="totalAmount"/>
			<span style="color: red;"  >合计总额： </span>
			<span style="float: right;"  id="totalAmount_span" ><fmt:formatNumber groupingUsed="true" value="${bean.fPlanTotalAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
		</span>
	</div>
    <table id="filing-edit-plan-dg-detail" class="easyui-datagrid" style="width:697px;height:auto"
			data-options="
				singleSelect: true,
				url: '${base}/Filing/finderReceivPlan?FcId=${bean.fcId}',
				method: 'post',
				<!-- onClickCell: onClickCellPlan, -->
				scrollbarSize:0,
				rownumbers:true,
			">
		<thead>
			<tr>
				<th data-options="field:'fReceCondition',editor:{type:'textbox',options:{readonly:true}},validType:'length[1,50]',align:'center',width:190">付款条件</th>
				<th data-options="field:'fRecePlanTime',align:'center',editor:{type:'datebox',options:{readonly:true,required:true,precision:1}},width:160" >预计付款时间</th>
				<th data-options="field:'fRecePlanAmount',align:'center',formatter:function(value,row,index){return fomatMoney(value,2);},editor:{type:'numberbox',options:{readonly:true,required:true,precision:2,onChange:filingplansetFsumMoney}},width:150" >付款金额(元)</th>
				<th data-options="field:'fReceProof',align:'center',editor:{type:'textbox'}" style="width: 25%">付款依据</th>
				<%-- <th data-options="field:'fReceProofs',align:'center',editor:{type:'combobox'	,editable:false,
						options:{
							readonly:true,
							required:true,
							valueField:'code',
							textField:'text',
							validType:'selectValid',
							method:'post',
							url:'${base}/Expiration/lookupsJson?parentCode=FKPJ',
						}
					},width:110" >付款依据</th> --%>
			</tr>
		</thead>
	</table>
</div>
	<script type="text/javascript">
	function PayStauts(val, row) {
		if (val == "FKXZ-02") {
			return "阶段款";
		} else if (val == "FKXZ-01") {
			return "首款";
		}else if (val == "FKXZ-03") {
			return "验收款";
		}else if (val == "FKXZ-04") {
			return "质保款";
		}
	}
		var editIndex = undefined;
		function endEditingPlan(){
			if (editIndex == undefined){return true}
			if ($('#filing-edit-plan-dg-detail').datagrid('validateRow', editIndex)){
				$('#filing-edit-plan-dg-detail').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickCellPlan(index, field){
			if (editIndex != index){
				if (endEditingPlan()){
					$('#filing-edit-plan-dg-detail').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					var ed = $('#filing-edit-plan-dg-detail').datagrid('getEditor', {index:index,field:field});
					if (ed){
						($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
					}
					editIndex = index;
				} else {
					setTimeout(function(){
						$('#filing-edit-plan-dg-detail').datagrid('selectRow', editIndex);
					},0);
				}
			}
		}
		function appendPlan(){
			if (endEditingPlan()){
				$('#filing-edit-plan-dg-detail').datagrid('appendRow',{});
				editIndex = $('#filing-edit-plan-dg-detail').datagrid('getRows').length-1;
				$('#filing-edit-plan-dg-detail').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		function removeitPlan(){
			if (editIndex == undefined){return}
			$('#filing-edit-plan-dg-detail').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
			setFsumMoney(0,0);
		}
		
		//计算总额
		function filingplansetFsumMoney(newValue,oldValue) {
			if(newValue==undefined || oldValue==undefined){
				return false;
			}
			var totalFsumMoney = 0;
			var fsumMoney = 0;
			var index=$('#filing-edit-plan-dg').datagrid('getRowIndex',$('#filing-edit-plan-dg').datagrid('getSelected'));
			var rows = $('#filing-edit-plan-dg').datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				if(i==index){
					fsumMoney=parseFloat(newValue);
				}else{
					totalFsumMoney+=filingplanaddNumDetail(rows,i);
				}  
			}
		    
			totalFsumMoney=(parseFloat(totalFsumMoney)+parseFloat(fsumMoney));
			totalFsumMoney=parseFloat(totalFsumMoney);
			$('#totalAmount').val(totalFsumMoney);
			$('#F_fPlanTotalAmount').val(totalFsumMoney);
			$('#totalAmount_span').html(totalFsumMoney+'[元]');
		}
		//未编辑或者已经编辑完毕的行
		function filingplanaddNumDetail(rows,index){
			var amount=rows[index]['fRecePlanAmount'];
			return parseFloat(amount); 
		}
		function getPlan(){
			$('#filing-edit-plan-dg-detail').datagrid('acceptChanges');
			var rows = $('#filing-edit-plan-dg-detail').datagrid('getRows');
			var entities= '';
			for(var i = 0;i < rows.length;i++){
			 entities = entities  + JSON.stringify(rows[i]) + ',';  
			}
			 entities = '[' + entities.substring(0,entities.length -1) + ']';
			 return entities;
		}
	</script>