.<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="window-div">
<form id="cgsq_apply_form" name="example" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:715px;margin-left: 20px">
					<div title="上传信息" id="cgfjdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;">
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;采购文件:
								</td>
								<td colspan="4" id="tdf">
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='cgwjsc'}">
											<div style="margin-top: 10px">
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
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
</form>
</div>
</body>