<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<html>
<style type="text/css">
.tabletop{background-color: #fff;font-family: "微软雅黑"}
</style>
<body>
	<style type="text/css">
	.textbox-text:read-only{background-color: #f6f6f6;color: #999999} 
	.textbox-readonly{background-color: #f6f6f6;color: #999999}
	.textbox-text{color:#666666;height: 25px; line-height: 25px}
	.style1{color: red;height: 40px;}
	.numberbox .textbox-text {text-align: left;}
	.tabDiv{padding:10px;}
	.ourtable{font-size: 12px;width: 550px;color: #666666;font-family: "微软雅黑"}
	.ourtable2{font-size: 12px;color: #666666;font-family: "微软雅黑"}
	.td1{width: 100px;}
	.td2{height: 30px;width: 150px;}
	.trtop{height: 10px;}
	.trbody{height: 30px;}
	
	/*---滚动条默认显示样式--*/
	::-webkit-scrollbar-thumb {
		background-color: #f0f5f7;
		height: 50px;
		outline-offset: -2px;
		outline: 2px solid #fff;
		-webkit-border-radius: 4px;
		border: 2px solid #fff;
	}
	
	/*---鼠标点击滚动条显示样式--*/
	::-webkit-scrollbar-thumb:hover {
		background-color: #f0f5f7;
		height: 50px;
		-webkit-border-radius: 4px;
	}
	
	/*---滚动条大小--*/
	::-webkit-scrollbar {
		width: 8px;
		height: 8px;
	}
	
	/*---滚动框背景样式--*/
	::-webkit-scrollbar-track-piece {
		background-color: #fff;
		-webkit-border-radius: 0;
	}
	
	</style>
	
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:true" style="width:600px;border-color: #dce5e9" >
		<div class="easyui-tabs" style="width:95%;" id="tree">
		
			<div title="基本支出指标">
				<table id="basic-choose-tree"  class="easyui-treegrid" style="width:100%;"
		            data-options="
		                url: '${base}/insideAdjust/insideTree',
		                method: 'post',
		                idField: 'id',
		                treeField: 'text',
						onClickRow: showIndex,
		            ">
		        	<thead>
		            	<tr>
		                <th data-options="field:'id',hidden:true"></th>
		                <th data-options="field:'haveChild',hidden:true"></th>
		                <th data-options="field:'text'" width="44%">支出预算事项</th>
		                <th data-options="field:'departName'" width="20%" >所属部门</th>
		                <th data-options="field:'amount'" width="20%" >预算总额</th>
		                <th data-options="field:'residAmount'" width="20%" >预算可用余额</th>
		            	</tr>
		        	</thead>
		    	</table> 
		   	</div>
		   	
		   	<div title="项目支出指标">
		   		<table id="pro-choose-tree"  class="easyui-treegrid" style="width:100%;"
		            data-options="
		                url: '${base}/insideAdjust/projectTree',
		                method: 'post',
		                idField: 'id',
		                treeField: 'text',
						onClickRow: showIndex,
						singleSelect:true,
		            ">
		        	<thead>
		            	<tr>
		                <th data-options="field:'id',hidden:true"></th>
		                <th data-options="field:'haveChild',hidden:true"></th>
		                <th data-options="field:'text'" width="44%">支出预算事项</th>
		                <th data-options="field:'departName'" width="20%" >所属部门</th>
		                <th data-options="field:'amount'" width="20%" >预算总额</th>
		                <th data-options="field:'residAmount'" width="20%" >预算可用余额</th>
		            	</tr>
		        	</thead>
		    	</table> 
		   	
		   	</div>
		</div>
	</div>
	
	<div data-options="region:'center',split:true" style="width: 8xp;height: 100%;background-color: #f0f5f7;border-color: #f0f5f7"></div>



	<div data-options="region:'east',split:true" style="width:190px;border-color: #dce5e9">
		<table class="ourtable2" style="width: 150px;margin-left: 20px;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="height: 28px;"><span style="color: ff6800">指标使用情况</span></td>
			</tr>
			<tr>
				<td valign="top">
					<img src="${base}/resource-modality/${themenurl}/skin_/hengxian.png" style="width: 150px">
				</td>
			</tr>
			<tr>
				<td style="height: 31px;" id="bfb">
					
				</td>
			</tr>
			<c:forEach items="${cheterInfo}" var="li">
				<tr style="height: 30px;">
					<td>
						<a style="color: #666666" id="file${li.systemId}" href="#" onclick="findSystemFile(${li.systemId})">${li.fileName}</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
			
	<div data-options="region:'south'" style="height: 51px;width: 100%; border: 0px;">
		<div style="width:598px;height: 50px;text-align: center;float: left;border:1px solid #dce5e9;border-top: 0px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="confirmKm()">确认选择</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeFirstWindow()">取消</a> 
		</div>
		<div style="width: 8px;height:50px;border: 1px solid #f0f5f7;border-left:0px;border-right:0px; border-top:0px ;background-color: #f0f5f7;float: left;"></div>
		<div style="width: 188px;height:50px;border:1px solid #dce5e9;float: left;border-top: 0px"></div>
	</div>
</div>

<script type="text/javascript">
	function confirmKm(){
		var tab = $('#tree').tabs('getSelected');
		var index = $('#tree').tabs('getTabIndex',tab);
		if(index == 0) {
			var nodes = $('#basic-choose-tree').treegrid('getSelections');
			if(nodes[0].haveChild == 'false') {
				$('#F_indexName').textbox('setValue',nodes[0].text);//科目名称
				$('#arriveIndexId').val(nodes[0].id);//科目ID
				$('#arriveIndexType').val(0);//科目类型
				closeFirstWindow();
			} else {
				alert("请选择最底层指标");
			}
			
		} else if (index == 1) {
			var nodes = $('#pro-choose-tree').treegrid('getSelections');
			if(nodes[0].haveChild == 'false') {
				$('#F_indexName').textbox('setValue',nodes[0].text);//科目名称
				$('#arriveIndexId').val(nodes[0].id);//科目ID
				$('#arriveIndexType').val(1);//科目类型
				closeFirstWindow();
			} else {
				alert("请选择最底层指标");
			}
		}
	}
	

	
	function showIndex(val) {
		/* var bfb = accDiv(val.residAmount,parseFloat(val.amount));
		$('#bfb').append('<p>'+bfb+'%    之后用进度条代替</p>'); */
	}
</script>
</body>

</html>
	