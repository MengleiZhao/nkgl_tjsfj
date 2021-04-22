<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="window-div">
<form id="cg_answerCheck_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div title="答复质疑"  id="cgysdiv" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-bottom:35px;">
				 	<div class="easyui-accordion" data-options="" id="" style="width:722px;margin-left: 20px">
				 		<div title="质疑信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
					  		<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
								
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;质疑发起时间</td>
									<td class="td2">
										<input class="easyui-datebox" type="text" id="F_fAskTime" name="fAskTime" readonly="readonly" required="required" data-options="prompt:'系统自动生成'" style="width: 200px" value="${bean.fAskTime}"/>
									</td>
									<td class="td4">
									<!--主键id -->		<input type="hidden" id="F_QId" name="fqId" value="${bean.fqId}"/>								
									<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
									<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
									<!-- 审批附件 --><input type="hidden" name="spjlFile" id="spjlFile" value=""/>
								</tr>
									<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;情况简述</td>
									<td colspan="4">
										<input class="easyui-textbox " id="F_fRemark" name="fRemark" readonly="readonly" required="required" data-options="multiline:true"  style="width: 550px;height:100px" value="${bean.fRemark}"/>
									</td>
								</tr>
								<tr class="trbody">
									<td class="td1">
										&nbsp;质疑函
									</td>
									<td colspan="4" id="">
										<c:forEach items="${attaList}" var="att">
											<c:if test="${att.serviceType=='zyh' }">
												<div style="margin-top: 10px;">
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												</div>
											</c:if>
										</c:forEach>
									</td>
								</tr>
							
							</table>
						</div>
					</div>
					<div class="easyui-accordion" data-options="" id="" style="width:722px;margin-left: 20px">
				 		<div title="答复信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
					  		<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
								
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;答复部门</td>
									<td class="td2">
										<input class="easyui-textbox " id="F_fDeptName" name="fDeptName" readonly="readonly" required="required" data-options="prompt:'系统自动生成'"  style="width: 200px" value="${bean.fDeptName}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;答复时间</td>
									<td class="td2">
										<input class="easyui-datebox" id="F_fAnswerTime" name="fAnswerTime" readonly="readonly" data-options="prompt:'系统自动生成'" required="required" style="width: 200px;" value="${bean.fAnswerTime}"/>
									</td>
								</tr>
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;答复人</td>
									<td class="td2">
										<input class="easyui-textbox " id="F_fUserName" name="fUserName" readonly="readonly" required="required" data-options="prompt:'系统自动生成'"  style="width: 200px" value="${bean.fUserName}"/>
									</td>
									<td class="td4"></td>
								</tr>
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;答复描述</td>
									<td colspan="4">
										<input class="easyui-textbox " id="F_fAnswerRemark" name="fAnswerRemark" readonly="readonly" required="required" data-options="multiline:true"  style="width: 550px;height:100px" value="${bean.fAnswerRemark}"/>
									</td>
								</tr>
								<tr style="height:5px"> </tr>
									<tr class="trbody">
										<td class="td1">
											&nbsp;附件
										</td>
										<td colspan="4" >
											<c:forEach items="${attaList}" var="att">
												<c:if test="${att.serviceType=='file' }">
													<div style="margin-top: 10px;">
														<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
													</div>
												</c:if>
											</c:forEach>
										</td>
									</tr>
							</table>
						</div>
					</div>													
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
					<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<div class="window-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	//提交
	//审批
	function check(result) {
		$('#cg_answerCheck_form').form('submit', {
			onSubmit : function() {
					$.messager.progress();
			},
			url : base + '/cgAsk/checkResult',
			success : function(data) {
				$.messager.progress('close');
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#cg_answerCheck_dg').datagrid('reload');
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