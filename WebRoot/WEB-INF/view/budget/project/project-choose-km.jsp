<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<html>
<body>
<div class="easyui-layout" fit="true">
	<div data-options="region:'north'" border="false" style="padding: 5px;">
		<span style="margin-left: 20px">支出预算事项：</span><input style="height: 30px; width:180px;" class="easyui-textbox" id="ckm_query_qName"/>
		<a href="javascript:void(0)" onclick="chooseKmQuery()">
			<img style="vertical-align:middle" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a> 
		<a href="javascript:void(0)" onclick="clearKmQuery()">
			<img style="vertical-align:middle" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a> 
	</div>
	<div data-options="region:'center'" border="false" >
		<table id="project-choose-km-tree"  class="easyui-treegrid" style="width:100%;"
            data-options="
            	<c:if test="${fProOrBasic==0}">
				url: '${base}/project/treezJbZckm',
				</c:if>
				<c:if test="${fProOrBasic==1}">
				url: '${base}/project/treezZckm?sCode=${sCode }',
				</c:if>
                method: 'get',
                rownumbers: true,
                idField: 'code',
                treeField: 'text',
                method: 'get',
                onDblClickRow:function(row) { 
                	confirmOutcome1(row);
				}
            ">
        <thead>
            <tr>
                <th data-options="field:'text'" width="50%">支出预算事项</th>
                <th data-options="field:'code'" width="50%">科目代码</th>
                <!-- <th data-options="field:'id'" width="15%" align="right">科目id</th>
                <th data-options="field:'parentCode'" width="20%" align="right">上级科目代码</th> -->
            </tr>
        </thead>
    </table> 
	</div>
	
	
	<div data-options="region:'south'" border="false" style="height: 80px;line-height: 80px;text-align: center;">
		<div style="text-align: left;height: 20px">
			<span style="color:#ff6800">&nbsp;&nbsp;&nbsp;&nbsp;✧温馨提示：请双击完成选择</span>
		</div>
		<div style="height: 20px;">
		<a href="javascript:void(0)" onclick="closeFirstWindow()">
			<img style="vertical-align:middle" src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a>
		</div>
	</div>
</div>

    
    

<script type="text/javascript">
	
	/* $(function(){
		alert(11111)
		$('#project-choose-km-tree').treegrid('select',30202);
	}); */

	function chooseKmQuery(){
		var name = $('#ckm_query_qName').textbox('getValue');
		if(name!=''){
			$('#project-choose-km-tree').treegrid('load', {
				qName: name,
				id: ''
			});
		}
		
	}
	function confirmOutcome(rIndex){
		var node = $('#project-choose-km-tree').treegrid('getSelected');
		var id1 = 'outcome7_'+ rIndex;
		var id2 = 'outcome8_'+ rIndex;
		var id4 = $("#outcome2_"+ rIndex).val();
		if(id4==""){
		$("#outcome2_"+ rIndex).text(row.text);
		}
		$('#'+id1).val(node.text);
		$('#'+id2).val(node.code);
		closeFirstWindow();
	}
	function confirmOutcome1(row){
		 if(!row.leaf){
			alert("请选择子节点");
			return;
		}; 
		var rIndex = '${rIndex}';
		var id1 = 'outcome7_'+ rIndex;
		var id2 = 'outcome8_'+ rIndex;
		var id3 = 'outcome12_'+rIndex;
		var id4 = $("#outcome2_"+ rIndex).val();
		if(id4==""){
		$("#outcome2_"+ rIndex).text(row.text);
		}
		$('#'+id1).val(row.text);
		$('#'+id2).val(row.code);
		$('#'+id3).text(row.text);
		closeFirstWindow();
	}
	
	//清除查询条件
	function clearKmQuery() {
		
	}
</script>
</body>

</html>
	