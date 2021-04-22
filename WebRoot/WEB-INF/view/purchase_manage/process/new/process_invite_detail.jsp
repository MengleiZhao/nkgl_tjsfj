<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody" id="trFJ01">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian01"></span>
		</td>
		<td colspan="4" id="tdf01">
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgyq01' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr class="trbody" id="trFJ02">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian02"></span>
		</td>
		
		<td colspan="4" id="tdf02">
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgyq02' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>