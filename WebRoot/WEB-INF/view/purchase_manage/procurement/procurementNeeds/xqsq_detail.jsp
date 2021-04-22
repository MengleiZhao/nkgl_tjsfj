<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<div class="window-div">
		<form id="xqsq_apply_form" name="example" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
			<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
				<div class="win-left-top-div">
					<div class="easyui-accordion" data-options="" id="cc" style="width:715px;margin-left: 20px">
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
										<input class="easyui-textbox" class="dfinput" id="F_authorized" name="authorized" required="required" readonly="readonly" data-options="validType:'length[1,20]'" style="width: 200px;" value="${needsBean.authorized}"/>
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
										<input class="easyui-textbox" class="dfinput" id="F_cgCode" name="cgCode" readonly="readonly" required="required" data-options="" style="width: 200px;" value="${bean.fpCode}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;采购类型:</td>
									<td class="td2">
										<input id="F_cgMethod" class="easyui-textbox" type="text" readonly="readonly" required="required" name="cgMethod" data-options="" style="width: 200px" value="${bean.fpMethod}"/>
									</td>
								</tr>
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;申请处室：</td>
									<td class="td2">
										<input class="easyui-textbox" class="dfinput" id="F_cgDeptName" name="cgDeptName" readonly="readonly" required="required" data-options="" style="width: 200px;" value="${bean.fDeptName}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;申请人:</td>
									<td class="td2">
										<input id="F_cgUserName" class="easyui-textbox" type="text" readonly="readonly" required="required" name="cgUserName" data-options="" style="width: 200px" value="${bean.fUserName}"/>
									</td>
								</tr>
							</table>
						</div>
						
						<c:if test="${openType=='edit' or openType=='detail'}">
							<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
								<jsp:include page="../../../check_history.jsp" />												
							</div> 
						</c:if>
						
					</div>
				</div>
				
				<div class="window-left-bottom-div">
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
</body>