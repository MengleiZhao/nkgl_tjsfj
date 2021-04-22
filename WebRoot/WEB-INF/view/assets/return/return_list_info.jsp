<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp" %>
   <table id="return_list_info_dg" class="easyui-datagrid" style="height:auto;"
		data-options="
			rownumbers:true,
			url: '${base}/assetReturnList/getAssetReturnList?fId=${bean.fId_A}',
			method: 'post',
			singleSelect:true
		">
	<thead>
		<tr>
			<th data-options="field:'fListId',hidden:true"></th>
			<th data-options="field:'fId_A',hidden:true"></th>
			<th data-options="field:'fAssCode_AR',align:'center'" style="width: 24%">卡片编号</th>
			<th data-options="field:'fFixedTypeName_AR',align:'center'" style="width: 20%">资产分类</th>
			<th data-options="field:'fAssName_AR',align:'center'" style="width: 25%">资产名称</th>
			<th data-options="field:'fAssSpecif_AR',align:'center'" style="width: 15%">型号</th>
			<c:if test="${openType=='approval'}">
				<th data-options="field:'fUseName_AR',align:'center'" style="width: 10%">领用人</th>
				<th data-options="field:'fUseDept_AR',align:'center'" style="width: 15%">领用部门</th>
			</c:if>
			<th data-options="field:'fReceDate',align:'center',formatter: ChangeDateFormat" style="width: 18%">领用日期</th>
			<c:if test="${openType=='approval'}">
				<th data-options="field:'fAvailableStauts_AR',align:'center'" style="width: 10%">可用状态</th>
			</c:if>
		</tr>
	</thead>
</table>
<script type="text/javascript">

</script>