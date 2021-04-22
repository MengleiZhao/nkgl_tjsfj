<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${openType=='add'||openType=='edit'}">
	<th data-options="field:'ffixedType_RL',editor:{type:'textbox',options:{required:true,editable:false,prompt:'选择资产分类',
		icons:[{iconCls:'icon-add',handler: function(e){
	    var row = $('#Rece_low_add_plan').datagrid('getSelected');
	    var index = $('#Rece_low_add_plan').datagrid('getRowIndex',row);
	    selectTypeDetail(index,'Rece_low_add_plan');
    	}}]}},align:'center'" style="width: 35%">资产分类</th>
	<th data-options="hidden:'ture',field:'fAssTypeRL',editor:{type:'textbox'},align:'center'" >资产分类</th>
	<th data-options="field:'fReceNum_RL',editor:{type:'textbox',options:{required:true}},align:'center'" style="width: 20%">数量</th>
	<th data-options="field:'fRemark_RL',editor:'textbox',align:'center'" style="width: 45%">配置要求</th>
</c:if>
<c:if test="${openType=='detail'}">
	<th data-options="field:'ffixedType_RL',align:'center'" style="width: 35%">资产分类</th>
	<th data-options="field:'fReceNum_RL',align:'center'" style="width: 20%">数量</th>
	<th data-options="field:'fRemark_RL',align:'center'" style="width: 45%">配置要求</th>
</c:if>
<script type="text/javascript">

function selectTypeDetail(index,tabId){
	var win=creatSecondWin('选择-资产类型',900,580,'icon-search',"/assetType/assetTypeList?index="+index+"&tabId="+tabId+"&type=ZCLX-02");
    win.window('open');
}
</script>	
