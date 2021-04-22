<%@ page language="java"  pageEncoding="UTF-8"%>
<div class="easyui-accordion" data-options="multiple:true" style="width:662px;margin-left: 20px;">
	<div title="变更信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
		<table cellpadding="0" cellspacing="0"  class="ourtable">
			<tr class="trbody">
				<td class="td1" ><span class="style1">*</span>&nbsp;变更描述</td>
				<td colspan="4">
					<%-- <input class="easyui-textbox" required="required" type="text" id="Upt_fUptReason" name="fUptReason" data-options="validType:'length[1,200]',multiline:true"   style="width:555px;height:100px" value="${Upt.fUptReason}"/> --%>
					<textarea name="fUptReason"  id="Upt_fUptReason" readonly="readonly" class="textbox-text" oninput="textareaNum(this,'textareaNum1')" autocomplete="off"  style="width:555px;height:100px">${Upt.fUptReason }</textarea>
					<input type="hidden" id=fContName name="fContName" value="${Upt.fContName}"/>
				</td>
			</tr>
			<tr class="trbody">
				<td class="td1" ><span class="style1">*</span>&nbsp;变更类型</td>
				<td>
					<input class="easyui-combobox" id="Upt_fContUptType" readonly="readonly" name="fContUptType" style="width: 200px;" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=HTBGLX&selected=${Upt.fContUptType}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'">
				</td>
				<td class="td4">&nbsp;</td>
				<td class="td1" ><span class="style1">*</span>&nbsp;变更日期</td>
				<td>
					<input class="easyui-datebox" id="Upt_fUptdate" readonly="readonly" value="${Upt.fUptdate }" name="fUptdate" style="width: 200px;" data-options="editable:false,required:true">
				</td>
			</tr>
			<tr class="trbody">
				<td class="td1">
					<span class="style1">*</span>&nbsp;附件
					<input type="file" multiple="multiple" id="fhtbg" onchange="upladFileParams(this,'htbg','htgl01','progressNumberhtbg','percenthtbg','tdhtbg','htbgfiles','progidhtbg')" hidden="hidden">
					<input type="text" id="htbgfiles" name="htbgfiles" hidden="hidden">
				</td>
				<td colspan="4" id="tdhtbg">
					<div id="progidhtbg" style="background:#EFF5F7;width:300px;height:10px;margin-top:0px;display: none" >
					<div id="progressNumberhtbg" style="background:#3AF960;width:0px;height:10px" >
					</div>文件上传中...&nbsp;&nbsp;<font id="percenthtbg">0%</font></div></br>
					<c:forEach items="${changeAttaList}" var="att">
						<c:if test="${att.serviceType=='htbg' }">
							<div class="htbg" style="margin-top: 0px;margin-bottom: 10px;">
								<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
							</div>
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</table>
	</div>
	<div id="change-upt-datagr-div" hidden="hidden" style="overflow:auto;margin-top:10px;">
		<div class="window-title" style="width: 650px;">变更付款计划</div>
		<%@ include file="../base/contract-change-plan-detail.jsp" %>
	</div>
</div>
<script type="text/javascript">
$('#Upt_fContUptType').combobox({
	onSelect : function(record){
		if(record.code=='HTBGLX-01'){
			$('#change-upt-datagr-div').show();
		}else {
			$('#change-upt-datagr-div').hide();
		}
		
	}
});

function onClickCellPlan(index, field){
	if (editIndex != index){
		if (endEditingPlan()){
			$('#change-upt-datagrid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			var ed = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:field});
			if (ed){
				($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
			}
			editIndex = index;
		} else {
			setTimeout(function(){
				$('#change-upt-datagrid').datagrid('selectRow', editIndex);
			},0);
		}
	}
}
function appendPlan1(){
	if (endEditingPlan()){
		$('#change-upt-datagrid').datagrid('appendRow',{});
		editIndex = $('#change-upt-datagrid').datagrid('getRows').length-1;
		$('#change-upt-datagrid').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeitPlan1(){
	if (editIndex == undefined){return}
	$('#change-upt-datagrid').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
var editIndex = undefined;
function endEditingPlan(){
	if (editIndex == undefined){return true}
	if ($('#change-upt-datagrid').datagrid('validateRow', editIndex)){
		$('#change-upt-datagrid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function getPlan1(){
	$('#change-upt-datagrid').datagrid('acceptChanges');
	var rows = $('#change-upt-datagrid').datagrid('getRows');
	var entities= '';
	for(i = 0;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	entities = '[' + entities.substring(0,entities.length -1) + ']';
	return entities;
}
</script>