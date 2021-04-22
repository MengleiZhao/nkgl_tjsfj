<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<%-- <tr class="trbody" id="trFJ03">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian03"></span>
			<input type="file" multiple="multiple" id="f03" onchange="upladFileCGDJ(this,'cgjz01','cggl03')" hidden="hidden">
			<input type="text" id="files03" name="files03" hidden="hidden">
		</td>
		
		<td colspan="4" id="tdf03">
			<a onclick="$('#f03').click()" style="font-weight: bold;" href="#">
				<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="progid03" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="progressNumber03" style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="percent03">0%</font> 
			</div>
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgjz01' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl03" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr> --%>
	<tr class="trbody" id="trFJ04">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian04"></span>
			<input type="file" multiple="multiple" id="f04" onchange="upladFileCGDJ(this,'cgjz02','cggl03')" hidden="hidden">
			<input type="text" id="files04" name="files04" hidden="hidden">
		</td>
		
		<td colspan="4" id="tdf04">
			<a onclick="$('#f04').click()" style="font-weight: bold;" href="#">
				<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="progid04" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="progressNumber04" style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="percent04">0%</font> 
			</div>
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgjz02' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl04" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr class="trbody" id="trFJ05">
		<td class="td1">
			&nbsp;&nbsp;<span id="fujian05" style="width: 200px;"></span>
			<input type="file" multiple="multiple" id="f05" onchange="upladFileCGDJ(this,'cgyq03','cggl03')" hidden="hidden">
			<input type="text" id="files05" name="files05" hidden="hidden">
		</td>
		
		<td colspan="4" id="tdf05">
			<a onclick="$('#f05').click()" style="font-weight: bold;" href="#">
				<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="progid05" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="progressNumber05" style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="percent05">0%</font> 
			</div>
			<c:forEach items="${brAttac}" var="att">
				<c:if test="${att.serviceType=='cgyq03' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl05" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>