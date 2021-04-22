<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="returnApprovalAddEditForm" action="" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<input type="hidden" name="fId_A" id="fId_A_approval" value="${bean.fId_A}"/>
				<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
			    <input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
					<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<table class="ourtable" cellpadding="0" cellspacing="0" style="margin-top: 3px;width:707px;">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;交回单号</td>
								<td class="td2"><input id="fAssReturnCode"
									readonly="readonly" required="required"
									class="easyui-textbox" name="fAssReturnCode"
									data-options="prompt:'',validType:'length[1,40]'"
									value="${bean.fAssReturnCode}" style="width: 200px" /></td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;预计交回时间</td>
								<td class="td2"><input class="easyui-datebox"
									class="dfinput" id="fReturnTime" name="fReturnTime" required="required"
									data-options="editable:false" style="width: 200px" readonly="readonly"
									value="${bean.fReturnTime}" /></td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
								<td class="td2"><input id="fOperator"
									class="easyui-textbox" readonly="readonly"
									required="required" name="fOperator"
									data-options="validType:'length[1,20]'" style="width: 200px"
									value="${bean.fOperator}" /></td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
								<td class="td2"><input id="fDeptName"
									class="easyui-textbox" readonly="readonly"
									required="required" name="fDeptName"
									data-options="prompt:' ',validType:'length[1,20]'"
									style="width: 200px" value="${bean.fDeptName}" /></td>
							</tr>
							<tr class="trbody">
								<td class="td1" valign="top"><p style="margin-top: 8px">
										<span class="style1">*</span>&nbsp;交回说明
									</p></td>
								<td colspan="4"><textarea name="fRemark" id="fRemark"
										class="textbox-text" required="required" readonly
										oninput="textareaNum(this,'textareaNum1')"
										autocomplete="off"
										style="width: 570px; height: 62px; resize: none">${bean.fRemark }</textarea>
								</td>
							</tr>
						</table>	
					</div>
					<div title="交回清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<%@ include file="../return_list_info_detail.jsp"%>
					</div>
					<c:if test="${checkinfo==1}">
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<jsp:include page="../../../check_history.jsp" />												
					</div>
					</c:if>
				</div>
			</div>
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
					<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<div class="window-right-div"  style="width:254px;height: 591px">
			 <jsp:include page="../../../check_system.jsp" /> 
		</div>
	</div>
</form>
<script type="text/javascript">
function check(stauts){
	acceptreturnList();
	var fId=$('#fId_A_approval').val;
	//var getreturnListjson = getreturnList();
	var rows = $('#return_list_info_detail_dg').datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].fAvailableStauts_AR=='--请选择--'||rows[i].fAvailableStauts_AR==null||rows[i].fAvailableStauts_AR==''||rows[i].fAvailableStauts_AR==undefined){
			alert('请选择可用状态！');
			return false;
		}
	}
	$('#returnApprovalAddEditForm').form('submit', {
			onSubmit: function(param){ 
				param.getreturnList = getreturnList();
				flag=$(this).form('enableValidation').form('validate');
				if(flag){
					$.messager.progress();
				}
				return flag;
			}, 
			url:'${base}/assetReturn/approve/',
			data:{
				'fId_R':fId
			},
			success:function(data){
				if(flag){
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if(data.success){
					$.messager.alert('系统提示', data.info, 'info');
					$('#returnApprovalAddEditForm').form('clear');
					$("#asset_return_dg").datagrid('reload');
					$("#indexdb").datagrid('reload');
					closeWindow();
				}else{
					$.messager.alert('系统提示', data.info, 'error');
				}
			} 
		});	
}
</script>
</body>