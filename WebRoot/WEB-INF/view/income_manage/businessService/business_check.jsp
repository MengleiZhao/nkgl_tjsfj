<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="win-div">
<form id="business_check_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" style="width: 780px" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
					<!-- 第一个div -->
					<div title="基本信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;">
						<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
							<tr>
								<td class="td1"><span class="style1">*</span>&nbsp;立项单号</td>
								<td class="td2">
									<input class="easyui-textbox" id="F_fBusiCode" name="fBusiCode" readonly="readonly" required="required" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.fBusiCode }"/>
								</td>
								
								<td class="td4">
									<!-- 隐藏域 --> 
								    <!-- 立项申请id -->		<input type="hidden" id="F_fBusiId" name="fBusiId" value="${bean.fBusiId }"/>
									<!-- 申请人id -->			<input type="hidden" id="F_fOperatorId" name="fOperatorId" value="${bean.fOperatorId }"/>
									<!-- 申请部门id -->		<input type="hidden" id="F_fDeptId" name="fDeptId" value="${bean.fDeptId }"/>
									<!-- 数据状态 -->			<input type="hidden" id="F_fStatus" name="fStatus" value="${bean.fStatus }"/>
									
									<!-- 下节点审批人id -->	<input type="hidden" name="fNextUserId" value="${bean.fNextUserId }"/>
									<!-- 下节点审批人姓名 -->	<input type="hidden" name="fNextUserName" value="${bean.fNextUserName }"/>
									<!-- 下节点编码 -->			<input type="hidden" name="fNextCode" value="${bean.fNextCode }"/>
									<!-- 审批状态 -->			<input type="hidden" id="F_fFlowStatus" name="fFlowStatus" value="${bean.fFlowStatus }"/>
									<!-- 审批结果 -->			<input type="hidden" id="fcheckResult" name="fcheckResult" value=""/>
									<!-- 审批意见 -->			<input type="hidden" id="fcheckRemake" name="fcheckRemake" value=""/>
									<!-- 审批附件 -->			<input type="hidden" id="spjlFile" name="spjlFile" value=""/>
								</td>
								
								<td class="td1"><span class="style1">*</span>&nbsp;立项时间</td>
								<td class="td2">
									<input class="easyui-datebox" id="F_fBusiTime" name="fBusiTime" readonly="readonly" required="required" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.fBusiTime }"/>
								</td>
							</tr>
							
							<tr>
								<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
								<td class="td2">
									<input class="easyui-textbox" id="F_fOperatorName" name="fOperatorName" readonly="readonly" required="required" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.fOperatorName }"/>
								</td>
								
								<td class="td4">&nbsp;</td>
								
								<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
								<td class="td2">
									<input class="easyui-textbox" id="F_fDeptName" name="fDeptName" readonly="readonly" required="required" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.fDeptName }"/>
								</td>
							</tr>
							
							<tr style="height: 5px;">&nbsp;</tr>
							
							<tr>
								<td class="td1" valign="top">&nbsp;&nbsp;说明</td>
								<td colspan="4">
									<textarea class="textbox-text" id="F_fRemark" name="fRemark" readonly="readonly" oninput="textareaNum(this,'textareaNum1')" autocomplete="off" style="width:555px;height:70px;resize:none">${bean.fRemark }</textarea>
								</td>
							</tr>
							
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;附件
									<input type="file" multiple="multiple" id="f_business" onchange="upladFile(this, 'business', 'business01')" hidden="hidden">
									<input type="text" id="files" name="files" hidden="hidden">
								</td>
								<td colspan="4" id="tdf">
									<c:forEach items="${businessAttaList }" var="att">
										<c:if test="${att.serviceType=='business' }">
										<div style="margin-top: 10px;">
											<a href='${base}/attachment/download/${att.id }' style="color: #666666;font-weight: bold;">${att.originalName }</a>
											&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</table>
					</div>
					
					<div title="立项详情表" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<%@ include file="business_select_details.jsp" %>
					</div>
					
					<c:if test="${openType!='add'}">
					<div title="审批记录" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<jsp:include page="../../check_history.jsp" />												
					</div></c:if>
				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
					<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
	var myDate = new Date();
	myDate = myformatter(myDate);
	$('#F_fBusiTime').textbox().textbox('setValue', myDate);
});
	
//送审
function check(result) {
	$('#business_check_form').form('submit', {
		onSubmit : function() {
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				$.messager.progress();
			}
			return flag;
		},
		url : base + '/business/checkResult',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#business_check_form').form('clear');
				$('#business_tab').datagrid('reload');
				$('#indexdb').datagrid('reload');
				closeWindow();
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});  
}
</script>
</body>