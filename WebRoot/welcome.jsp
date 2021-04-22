<%@ page contentType="text/html;  charset=utf-8" language="java" %>
<%@ include file="/includes/taglibs.jsp" %>
<html>
<head>
<title>${title}</title>
</head>
<body >
<c:if test="${null!=currentUser && currentUser.hasRole('QU_ROLE')==true}">
	<script>
		window.location.href="${base}/index.do";
	</script>
</c:if>
<c:if test="${null!=currentUser && currentUser.hasRole('STREET_ROLE')==true}">
	<script>
		window.location.href="${base}/streetIndex.do";
	</script>
</c:if>
<c:if test="${null!=currentUser && currentUser.hasRole('JWH_ROLE')==true}">
	<script>
		window.location.href="${base}/jwhIndex.do";
	</script>
</c:if>
<c:if test="${null==currentUser}">
	<script>
		window.location.href="${base}/login.jsp";
	</script>
</c:if>
</body> 
</html>