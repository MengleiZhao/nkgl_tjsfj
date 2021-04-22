<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${openType=='add'||openType=='edit'||openType=='detail'||openType=='app'||openType=='config'}">
	<th data-options="field:'ffixedType_RL',align:'center'" style="width: 35%">资产分类</th>
	<th data-options="field:'fReceNum_RL',align:'center'" style="width: 20%">数量</th>
	<th data-options="field:'fRemark_RL',align:'center'" style="width: 45%">配置要求</th>
</c:if>