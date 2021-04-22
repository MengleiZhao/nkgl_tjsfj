<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody" id="trFJ01">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian01"></span>
			<input type="file" multiple="multiple" id="f01" onchange="upladFileCGDJ(this,'cgyq01','cggl03')" hidden="hidden">
			<input type="text" id="files01" name="files01" hidden="hidden">
		</td>
		<td colspan="4" id="tdf01">
			<a onclick="$('#f01').click()" style="font-weight: bold;" href="#">
				<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="progid01" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="progressNumber01" style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="percent01">0%</font> 
			</div>
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgyq01' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl01" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr class="trbody" id="trFJ02">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian02"></span>
			<input type="file" multiple="multiple" id="f02" onchange="upladFileCGDJ(this,'cgyq02','cggl03')" hidden="hidden">
			<input type="text" id="files02" name="files02" hidden="hidden">
		</td>
		
		<td colspan="4" id="tdf02">
			<a onclick="$('#f02').click()" style="font-weight: bold;" href="#">
				<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="progid02" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="progressNumber02" style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="percent02">0%</font> 
			</div>
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgyq02' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl02" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>