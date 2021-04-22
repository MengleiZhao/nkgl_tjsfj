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
<form id="cg_answer_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
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
									<!-- 质疑答复审批状态 -->	<input type="hidden" id="F_fCheckStauts" name="fCheckStauts" value="${bean.fCheckStauts}"/>
									<!--答复编号 -->		<input type="hidden" id="F_fQueryCode" name="fQueryCode" value="${bean.fQueryCode}"/>								
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
										<input class="easyui-textbox " id="F_fAnswerRemark" name="fAnswerRemark" <c:if test="${operation == 'detail' }"> readonly="readonly"</c:if> required="required" data-options="multiline:true"  style="width: 550px;height:100px" value="${bean.fAnswerRemark}"/>
									</td>
								</tr>
								<tr style="height:5px"> </tr>
								<c:if test="${operation != 'detail' }">
								<tr class="trbody">
									<td class="td1">
										&nbsp;附件
										<input type="file" multiple="multiple" id="fzydf" onchange="upladFile(this,'file','cggl01')" hidden="hidden">
										<input type="text" id="files" name="files" hidden="hidden">
									</td>
									<td colspan="4" id="tdf">
										<a onclick="$('#fzydf').click()" style="font-weight: bold;" href="#">
											<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
										</a>
										<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
											<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
											</div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
										</div>
										<c:forEach items="${attaList}" var="att">
											<c:if test="${att.serviceType=='file' }">
												<div style="margin-top: 5px;">
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
													&nbsp;&nbsp;&nbsp;&nbsp;
													<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
													&nbsp;&nbsp;&nbsp;&nbsp;
													<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
												</div>
											</c:if>
										</c:forEach>
									</td>
								</tr>
								</c:if>
								<c:if test="${operation == 'detail' }">
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
								</c:if>	
							</table>
						</div>
					</div>													
				</div>
			</div>
			
			<div class="window-left-bottom-div">
			<c:if test="${operation != 'detail' }">
				<a href="javascript:void(0)" onclick="saveAnswer(0);">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveAnswer(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
			</c:if>	
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
	function saveAnswer(status) {
		$("#F_fCheckStauts").val(status);
		//附件的路径地址
		var s="";
		$(".fileUrl").each(function(){
			s=s+$(this).attr("id")+",";
		});
		$("#files").val(s);
		//提交
		$('#cg_answer_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}else{
					//校验不通过，就打开第一个校验失败的手风琴
					openAccordion();
				}
				return flag;
			},
			url : base + '/cgAsk/saveAnswer',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#cg_answer_Tab').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
	
	
</script>
</body>