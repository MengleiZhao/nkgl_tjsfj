<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
    <div class="easyui-layout" fit="true">
		<div data-options="region:'west',split:false"  style="width:250px;">
			<ul id="lookupsAssetTree" class="easyui-tree" data-options="url:'${base}/assetType/tree?type=${type}&fAssClass=${fAssClass }',animate:true,lines:true"></ul>
		</div>
	</div>
    
    <a href="javascript:void(0)" onclick="closeSecondWindow();">
		<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
	</a>
<script type="text/javascript">
var index = '${index}';
var tabId = '${tabId}';
$('#lookupsAssetTree').tree({
	onDblClick: function(node){
		if(node.state!="open"){
			alert("请选择下级分类！");
			return false;
		}
		if(node.state=="open"&&node.children!=null){
			alert("请选择下级分类！");
			return false;
		}
		 $('#'+tabId).datagrid('updateRow',{
				index: index,
				row: {
					fFixedType: node.text,
					fFixedTypeCode: node.code,
					fFixedTypeId: node.id,
					ffixedType_RL: node.text,
				}
			});
		 editIndex = undefined;
		 editIndexPlan = undefined;
		 editIndexPlanIntangible = undefined;
		 closeSecondWindow();
	}
});
</script>