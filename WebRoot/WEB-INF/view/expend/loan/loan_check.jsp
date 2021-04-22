<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="win-div">
<form id="loan_check_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;"">
			<div class="win-left-top-div">
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
					<div title="基本信息" data-options="collapsible:false,collapsible:false" style="overflow:auto;">
						<table class="ourtable" cellpadding="0" cellspacing="0">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span> 借款单号</td>
								<td colspan="4">
									<input style="width: 558px;height: 30px;" name="gCode" class="easyui-textbox"
									value="${bean.lCode}" data-options="required:true" readonly="readonly"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span> 借款单摘要</td>
								<td colspan="4">
									<input style="width: 558px;"
									id="" name="loanPurpose" class="easyui-textbox"
									value="${bean.loanPurpose}" readonly="readonly"/>
								</td>
							</tr>
							

							<tr class="trbody">
							
								<td class="td1"><span class="style1">*</span> 借款金额</td>
								<td class="td2">
									<input style="width: 200px;" id="lAmount" name="lAmount" class="easyui-numberbox" 
									value="${bean.lAmount}" precision="2" data-options="icons: [{iconCls:'icon-yuan'}],formatter:function(value,row,index){return fomatMoney(value,2);}" readonly="readonly"/>
								</td>
								<%--  <td class="td1"><span class="style1">*</span> 借款时间</td>
								<td class="td2">
									<input style="width: 200px;"
									id="loanReqTime" name="reqTime" class="easyui-datebox"
									value="${bean.reqTime}" readonly="readonly"/>
								</td>  --%>

								<td class="td4"></td>

								<td class="td1"><span class="style1">*</span> 计划还款时间</td>

								<td class="td2">
									<input style="width: 200px;"
									name="estChargeTime" class="easyui-datebox"
									value="${bean.estChargeTime}" readonly="readonly"/>
								</td>
							</tr>

							<%-- <tr class="trbody">
								<td class="td1"><span class="style1">*</span> 预算指标</td>
								<td class="td2">
									<input style="width: 200px;" name="indexName" class="easyui-textbox"
									value="${bean.indexName}" readonly="readonly"/>
								</td>
								
								<td class="td4"></td>
								
								<td class="td1"><span class="style1">*</span> 申请金额</td>
								<td class="td2">
									<input style="width: 200px;" id="lAmount" name="lAmount" class="easyui-numberbox" 
									value="${bean.lAmount}" precision="2" data-options="icons: [{iconCls:'icon-yuan'}],formatter:function(value,row,index){return fomatMoney(value,2);}" readonly="readonly"/>
								</td>
							</tr> --%>
							
							<%-- <tr class="trbody">
							
								<td class="td1"><span class="style1">*</span> 借款时间</td>
								<td class="td2">
									<input style="width: 200px;"
									id="loanReqTime" name="reqTime" class="easyui-datebox"
									value="${bean.reqTime}" readonly="readonly"/>
								</td>
								 <td class="td1"><span class="style1">*</span> 借款时间</td>
								<td class="td2">
									<input style="width: 200px;"
									id="loanReqTime" name="reqTime" class="easyui-datebox"
									value="${bean.reqTime}" readonly="readonly"/>
								</td> 

								<td class="td4"></td>

								<td class="td1"><span class="style1">*</span> 剩余还款金额</td>

								<td class="td2">
									<input style="width: 200px;" id="leastAmount" name="leastAmount" class="easyui-numberbox"
									value="${bean.leastAmount}" readonly="readonly"
									data-options="icons: [{iconCls:'icon-yuan'}],required:true,formatter:function(value,row,index){return fomatMoney(value,2);}" />
								</td>
							</tr> --%>
							
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span> 借款人</td>
								<td class="td2">
									<input style="width: 200px;" name="userName" class="easyui-textbox"
									value="${bean.userName}" readonly="readonly"/>
									<!-- 隐藏域 --> 
									<!-- 主键ID --><input type="hidden" name="lId" value="${bean.lId}" />
									<!-- 借款单编号 --><input type="hidden" name="lCode" value="${bean.lCode}" />
									<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="loanflowStauts"/> 
									<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="loanStauts" />
									<!-- 下环节处理人姓名 --><input type="hidden" name="userName2" value="${bean.userName2}"/>
									<!-- 下环节处理人编码 --><input type="hidden" name="userId" value="${bean.userId}" />
									<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
									<!-- 指标ID --><input type="hidden" name="indexId" value="${bean.indexId}" id="F_fBudgetIndexCode"/>
									<!-- 指标类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="F_indexType"/>
									<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
			    					<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
			    					<!-- 审批附件 --><input type="hidden" name="spjlFile" id="spjlFile" value=""/>
									
									
								</td>
								<td class="td4"></td>

								<td class="td1"><span class="style1">*</span> 所属部门</td>
								<td class="td2">
									<input style="width: 200px;"
									value="${bean.deptName}" readonly="readonly"
									name="deptName" class="easyui-textbox" readonly="readonly"/>
								</td>
							</tr>
							
							<tr class="trbody">
							
								<td class="td1"><span class="style1">*</span> 借款时间</td>
								<td class="td2">
									<input style="width: 200px;"
									id="loanReqTime" name="reqTime" class="easyui-datebox"
									value="${bean.reqTime}" readonly="readonly"/>
								</td>
							</tr>
							
							<tr style="height:5px;"></tr>

							<tr style="height: 70px;">
								<td class="td1" valign="top"><span class="style1">*</span> 借款事由</td>
								<td colspan="4">
									<input class="easyui-textbox"
									data-options="multiline:true" name="loanReason"
									style="width:555px;height:70px" value="${bean.loanReason}" readonly="readonly"/>
								</td>
							</tr>
						</table>
					</div>
					
					<div title="收款人信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<jsp:include page="../payee-info-detail-loan.jsp" />
					</div>
					
					<div title="附件" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">	
						<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;width:707px;">
							<tr class="trbody">
								<td class="td1" style="width:55px;text-align: left">附件</td>
								<td colspan="4">
									<c:if test="${!empty loanList}">
									<c:forEach items="${loanList}" var="att">
										<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a><br>
									</c:forEach>
								</c:if>
								<c:if test="${empty loanList}">
									<span style="color:#999999">暂未上传附件</span>
								</c:if>
								</td>
							</tr>
						</table>
					</div>
					
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<jsp:include page="../../check_history.jsp" />												
					</div>
					
					<!-- <div title="审批" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<table class="ourtable">
							<tr class="trbody">
								<td class="td1">审批结果</td>
	
								<td colspan="4">
									<input checked="checked" type="radio" value="1" name="outCheckResult"/>通过
									<input type="radio" value="0" name="outCheckResult"/>不通过
								</td>
							</tr>
						
							<tr style="height: 70px;">
								<td class="td1" valign="top"><p style="margin-top: 8px">审批内容</p></td>
								<td colspan="4">
									<input class="easyui-textbox"
									data-options="multiline:true" name="checkRemake"
									style="width:555px;height:70px">
								</td>
							</tr>
						</table>
					</div> -->
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

		<div class="window-right-div" style="width:254px;height: 591px">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>
<script type="text/javascript">
//审批
function check(result) {
	
	$('#loan_check_form').form('submit', {
		onSubmit : function() {
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				$.messager.progress();
			}
			return flag;
		},
		url : base + '/loanCheck/checkResult',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				closeWindow();
				$('#loan_check_form').form('clear');
				$('#loanCheckTab').datagrid('reload'); 
				$("#indexdb").datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.info, 'error');
				closeWindow();
				$('#loan_check_form').form('clear');
			}
		}
	});
}
</script>
</body>