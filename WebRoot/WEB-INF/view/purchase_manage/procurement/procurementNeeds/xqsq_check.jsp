<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<div class="window-div">
		<form id="xqsq_check_form" name="example" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
			<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
				<div class="win-left-top-div">
					<div class="easyui-accordion" data-options="" id="cc" style="width:715px;margin-left: 20px">
						<!-- 隐藏域 --> 
						<input type="hidden" name="pId"  value="${bean.pId}"/><!-- 需求单id --> 
						<input type="hidden" name="pCode"  value="${bean.pCode}"/><!-- 需求单Code --> 
						<input type="hidden" name="cgId"  value="${bean.cgId}"/><!-- 采购单id --> 
						<input type="hidden" name="cgAmount"  value="${bean.cgAmount}"/><!-- 采购单金额 --> 
						<input type="hidden" name="cgType"  value="${bean.cgType}"/><!-- 采购方式 -->
						<input type="hidden" name="cgDeptId"  value="${bean.cgDeptId}"/><!-- 采购申请人处室id -->
						<input type="hidden" name="cgUserId"  value="${bean.cgUserId}"/><!-- 采购申请人id -->
						<input type="hidden" name="cgName"  value="${bean.cgName}"/><!-- 采购项目名 -->
						<input type="hidden" name="status"  value="${bean.status}"/><!-- 需求单状态 --> 
						<input type="hidden" name="xqUserId"  value="${bean.xqUserId}"/><!-- 需求单申请人id --> 
						<input type="hidden" name="xqUserName"  value="${bean.xqUserName}"/><!-- 需求单申请人名字 --> 
						<input type="hidden" name="xqDeptId"  value="${bean.xqDeptId}"/><!-- 需求单申请人处室id --> 
						<input type="hidden" name="xqDeptName"  value="${bean.xqDeptName}"/><!-- 需求单申请人处室名字 --> 
						<input type="hidden" name="spjlFile" id="spjlFile" value=""/><!-- 审批附件 -->
						<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/><!-- 审批结果 -->
						<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/><!-- 审批备注-->
						<input type="hidden" name="nCode" value="${bean.nCode}" /><!-- 下节点节点编码 -->
						<input type="hidden" name="fuserId" value="${bean.fuserId}" /><!-- 下环节处理人编码 -->
					
						<div  title="申请信息" id="cgxxdiv" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
							
								<tr>
									<td class="td1" style="width:60px;text-align: right">
									<span class="style1">*</span> 上传需求书：</td>
									<td colspan="3" id="tdf">
										<c:forEach items="${attac}" var="att">
											<c:if test="${att.serviceType=='xqs'}">
												<div>
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												</div>
											</c:if>
										</c:forEach>
									</td>
								</tr>
								
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;授权代表:</td>
									<td class="td2" colspan="4">
										<input class="easyui-textbox" class="dfinput" id="F_authorized" name="authorized" required="required" readonly="readonly" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.authorized}"/>
									</td>
								</tr>
								
								<tr>
									<td class="td1" style="width:60px;text-align: right">
									<span class="style1">*</span> 上传委托书：</td>
									<td colspan="3" id="tdf">
										<c:forEach items="${attac}" var="att">
											<c:if test="${att.serviceType=='wts'}">
												<div>
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												</div>
											</c:if>
										</c:forEach>
									</td>
								</tr>
								
							</table>
						</div>
						
						<div  title="基本信息" id="cgxxdiv" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;采购项目编号:</td>
									<td class="td2">
										<input class="easyui-textbox" class="dfinput" id="F_cgCode" name="cgCode" readonly="readonly" required="required" data-options="" style="width: 200px;" value="${bean.cgCode}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;采购类型:</td>
									<td class="td2">
										<input id="F_cgMethod" class="easyui-textbox" type="text" readonly="readonly" required="required" name="cgMethod" data-options="" style="width: 200px" value="${bean.cgMethod}"/>
									</td>
								</tr>
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;申请处室：</td>
									<td class="td2">
										<input class="easyui-textbox" class="dfinput" id="F_cgDeptName" name="cgDeptName" readonly="readonly" required="required" data-options="" style="width: 200px;" value="${bean.cgDeptName}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;申请人:</td>
									<td class="td2">
										<input id="F_cgUserName" class="easyui-textbox" type="text" readonly="readonly" required="required" name="cgUserName" data-options="" style="width: 200px" value="${bean.cgUserName}"/>
									</td>
								</tr>
							</table>
						</div>
						
						<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
							<jsp:include page="../../../check_history.jsp" />												
						</div> 
						
					</div>
				</div>
				
				<div class="window-left-bottom-div">
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
			
			<div class="window-right-div" id="check_system_div" style="margin: 20px 20px 30px 0px;" data-options="region:'east',split:true">
				<jsp:include page="../../../check_system.jsp" />
			</div>
			
		</form>
	</div>
	
<script type="text/javascript">
//审核
function check(checkResult) {
 	$('#xqsq_check_form').form('submit', {
		onSubmit : function() {
			$.messager.progress();
		},
		url : base + '/procurementNeedsCheck/checkResult',
		success : function(data) {
			$.messager.progress('close');
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#cgxq_check_Tab').datagrid('reload');
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