<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody" id="trFJ03">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian03"></span>
		</td>
		<td colspan="4" id="tdf03">
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgjz01' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr class="trbody" id="trFJ04">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian04"></span>
		</td>
		<td colspan="4" id="tdf04">
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgjz02' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr class="trbody" id="trFJ05">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian05"></span>
		</td>
		<td colspan="4" id="tdf05">
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgyq03' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>