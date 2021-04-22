<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<style type="text/css">
.under{
	outline: none;
	width:75px;
    border-bottom: 1px solid #000;
    border-top: 0px;
    border-left: 0px;
    border-right: 0px;
    text-align:center;
    color:#0000CD;
}
</style>
<!-- 局长办公会会议纪要 -->
<div class="window-table" style="margin-bottom:10px;margin-left: 10px;">
	<div style="width:600px;margin-top:10px">
		<div >
			<label>附</label> <input type="text" name="meetingSummaryYear1"
				class="under" id="meetingSummaryYear1" <c:if test="${empty xzbgsFile}"> readonly="readonly" </c:if>
				value="${travelBean.meetingSummaryYear1}" />
				<label>年第 </label> <input type="text" id="meetingSummaryTime1" name="meetingSummaryTime1" <c:if test="${empty xzbgsFile}"> readonly="readonly" </c:if>
				class="under" required="required"
				value="${travelBean.meetingSummaryTime1}"/>
				<label>次局长办公会会议纪要&nbsp;</label> 
		</div>
	</div>
</div>
<table class="window-table">
<tr>
	<%-- <c:if test="${!empty xzbgsFile}"> --%>
		<td class="td1" style="width:0.1px;">
			<input type="file" multiple="multiple" id="chalv"
			onchange="upladFileMoreParams(this,'chalv','chalvl01','chalvprogressNumber','chalvpercent','chalvtd','chalvfiles','chalvprogid','chalvfileUrl')" hidden="hidden"> <input
			type="text" id="chalvfiles" name="chalvfiles" hidden="hidden"></td>
		<td colspan="3" id="chalvtd">&nbsp;&nbsp; <a onclick="$('#chalv').click()"
			style="font-weight: bold;  " href="#"> <img
				style="vertical-align:bottom;margin-bottom: 5px;"
				src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"
				onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a>
			<div id="chalvprogid"
				style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="chalvprogressNumber"
					style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="chalvpercent">0%</font>
			</div> <c:forEach items="${businessAttaList}" var="att">
			<c:if test="${att.serviceType=='chalv' }">
				<div style="margin-top: 5px;">
					<a href='${base}/attachment/download/${att.id}'
						style="color: #666666;font-weight: bold;">${att.originalName}</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <img style="margin-top: 5px;"
						src="${base}/resource-modality/${themenurl}/sccg.png">
					&nbsp;&nbsp;&nbsp;&nbsp; <a id="${att.id}" class="chalvfileUrl" href="#"
						style="color:red" onclick="deleteAttac(this)">删除</a>
				</div>
			</c:if>
			</c:forEach>
		</td>
		<%-- </c:if> --%>
		<c:if test="${empty xzbgsFile}">
			<td class="td1" style="width:0.1px;"></td>
			<td colspan="4">
				<c:if test="${!empty businessAttaList}">
					<c:forEach items="${businessAttaList}" var="att">
						<c:if test="${att.serviceType=='chalv'}">
							<a href='${base}/attachment/download/${att.id}'
								style="color: #666666;font-weight: bold;">${att.originalName}</a>
							<br>
						</c:if>
					</c:forEach>
				</c:if>
			</td>	
		</c:if>
	</tr>
</table> 
<!-- 党委会会议纪要 -->
<div class="window-table" style="margin-bottom:10px;margin-left: 10px;">
	<div style="width:600px;margin-top:10px">
		<div >
			<label>附</label> <input type="text" id="meetingSummaryYear2" name="meetingSummaryYear2"
				class="under" <c:if test="${empty dwhFile}"> readonly="readonly" </c:if>
				value="${travelBean.meetingSummaryYear2}" />
				<label>年第 </label> <input type="text" id="meetingSummaryTime2" name="meetingSummaryTime2"
				class="under" <c:if test="${empty dwhFile}"> readonly="readonly" </c:if>
				value="${travelBean.meetingSummaryTime2}"/>
				<label>次党委会会议纪要&nbsp;</label> 
		</div>
	</div>
</div>

 <table class="window-table">
<tr>
	<c:if test="${!empty dwhFile}">
		<td class="td1" style="width:0.1px;">
			<input type="file" multiple="multiple" id="chuxing"
			onchange="upladFileMoreParams(this,'chuxing','chalvl01','chuxingprogressNumber','chuxingpercent','chuxingtdf','chuxingfiles','chuxingprogid','chuxingfileUrl')" hidden="hidden"> <input
			type="text" id="chuxingfiles" name="chuxingfiles" hidden="hidden"></td>
		<td colspan="3" id="chuxingtdf">&nbsp;&nbsp; <a onclick="$('#chuxing').click()"
			style="font-weight: bold;  " href="#"> <img
				style="vertical-align:bottom;margin-bottom: 5px;"
				src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"
				onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a>
			<div id="chuxingprogid"
				style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="chuxingprogressNumber"
					style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="chuxingpercent">0%</font>
			</div> <c:forEach items="${businessAttaList}" var="att">
			<c:if test="${att.serviceType=='chuxing' }">
				<div style="margin-top: 5px;">
					<a href='${base}/attachment/download/${att.id}'
						style="color: #666666;font-weight: bold;">${att.originalName}</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <img style="margin-top: 5px;"
						src="${base}/resource-modality/${themenurl}/sccg.png">
					&nbsp;&nbsp;&nbsp;&nbsp; <a id="${att.id}" class="chuxingfileUrl" href="#"
						style="color:red" onclick="deleteAttac(this)">删除</a>
				</div>
			</c:if>
			</c:forEach>
		</td>
		</c:if>
		
		<c:if test="${empty dwhFile}">
		<td class="td1" style="width:0.1px;"></td>
		<td colspan="4">
			<c:if test="${!empty businessAttaList}">
				<c:forEach items="${businessAttaList}" var="att">
					<c:if test="${att.serviceType=='dwh'}">
						<a href='${base}/attachment/download/${att.id}'
							style="color: #666666;font-weight: bold;">${att.originalName}</a>
						<br>
					</c:if>
				</c:forEach>
			</c:if>
		</td>	
		</c:if>
	</tr>
</table> 
<script type="text/javascript">
</script>