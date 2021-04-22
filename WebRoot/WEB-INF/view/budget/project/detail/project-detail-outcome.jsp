<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/includes/taglibs.jsp"%>
<style type="text/css">
.pro_outcome_th {
	/* border-left:1px solid black;
	border-top:1px solid black;
	border-bottom:1px solid black;
	margin: 0;
	padding: 0 */
	
	background: #c1e3f2;
	font-weight: bold;
	color: #333333;
	text-align: center;
}
#pro_outcome_table td{text-align: center;}
.pro_outcome_input{
	width: 263px;
	border: 0;
	background-color: #f6f6f6;
	vertical-align: middle;
}
.accordion .accordion-header {
	height: 20px;
	width: 910px;
}
textarea {
	height: 60px;
	resize:none;
	padding: 8px;
}
/* .ystable{border:1px solid #999999;} */
</style>	
	<table id="pro_outcome_table" style="width: 1100px;margin-left: 20px" cellpadding="1" cellspacing="1">
		<tr>
			<th class="pro_outcome_th" style="width: 30px">序号</th>
			<th class="pro_outcome_th">活动</th>
			<th class="pro_outcome_th">经济分类科目</th>
			<th class="pro_outcome_th">支出金额（元）</th>
			<th class="pro_outcome_th">摘要</th>
			<!-- <th class="pro_outcome_th">子活动</th> -->
			<!-- <th class="pro_outcome_th">对子活动的描述</th> -->
		</tr>
		<c:forEach var="i" items="${expDetailList}" varStatus="status">
		<tr>
			<td><textarea disabled="disabled" class="pro_outcome_input" style="width:30px">${status.index+1}</textarea></td>
			<td><textarea disabled="disabled" class="pro_outcome_input">${i.activity}</textarea></td>
			<td><textarea disabled="disabled" class="pro_outcome_input">${i.subName}</textarea></td>
			<td><textarea disabled="disabled" class="pro_outcome_input"><fmt:formatNumber type="number" value="${i.outAmount}" maxFractionDigits="2" /></textarea></td>
			<td><textarea disabled="disabled" class="pro_outcome_input">${i.actDesc}</textarea></td>
			<%-- <td><textarea disabled="disabled" class="pro_outcome_input">${i.sonActivity}</textarea></td> --%>
			<%-- <td><textarea disabled="disabled" class="pro_outcome_input">${i.sonActDesc}</textarea></td> --%>
		</tr>
		</c:forEach>
	</table>
