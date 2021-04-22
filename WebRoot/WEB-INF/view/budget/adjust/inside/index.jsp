<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="win-div">
	<div class="easyui-layout" style="height: 535px;">
		<div class="win-left-div" data-options="region:'west',split:true" style="width: 620px">
			<div class="list-top">
				<table class="top-table" cellpadding="0" cellspacing="0">
					<tr>
						<td class="top-table-search" class="queryth">预算指标名称&nbsp;
							<input id="inside_indexNameOut" name="" style="width: 150px; height:25px;" class="easyui-textbox"></input>
							&nbsp;&nbsp;<a href="#" onclick="query();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
							<a href="#" onclick="clearTable();">
								<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="list-table">
				<div class="tab-wrapper" id="choice" style="height: 440px">
					<ul class="tab-menu">
						<li class="active" onclick="$('#basic-choose-tree').datagrid('reload'),tablist=1">基本支出指标</li>
						<li onclick="$('#pro-choose-tree').treegrid('reload'),tablist=2">项目支出指标</li> 
					</ul>
					
					<div class="tab-content">
						<div style="height: 400px">
							<table id="basic-choose-tree"  class="easyui-treegrid" style="width:100%;height: 400px"
					            data-options="
					                url: '${base}/quota/treeIndex?indexType=0',
				               		method: 'get',
					                idField: 'id',
					                treeField: 'text',
					                checkbox: true,
					                singleSelect: false,
					                onClickRow:clickIndex
					            ">
					        	<thead>
					            	<tr>
						               <!--  <th data-options="field:'bId',hidden:true"></th>
						                <th data-options="field:'ck',checkbox:'true',name:'test' "></th>
						                <th data-options="field:'indexName'" width="25%">预算指标名称</th>
						                <th data-options="field:'pfAmount',formatter:pfcolor" width="25%" >指标批复金额[万元]</th>
						                <th data-options="field:'syAmount',formatter:sycolor" width="25%" >指标剩余金额[万元]</th>
						                <th data-options="field:'djAmount',formatter:djcolor" width="24%" >指标冻结金额[万元]</th> -->
						                <th data-options="field:'text',align:'center'" width="44%" >预算指标名称</th>
						                <th data-options="field:'col1',align:'right',formatter:pfcolor" width="20%">批复金额[万元]</th>
						                <th data-options="field:'col2',align:'right',formatter:sycolor" width="20%">可用金额[万元]</th>
						                <th data-options="field:'col3',align:'right',formatter:djcolor" width="20%">冻结金额[万元]</th>
					            	</tr>
					        	</thead>
					    	</table> 
						</div>
						
						<div style="height: 400px">
							<table id="pro-choose-tree"  class="easyui-treegrid" style="width:94%;height: 400px"
					            data-options="
					                url: '${base}/quota/treeIndex?indexType=1',
					                method: 'get',
					                idField: 'id',
					                treeField: 'text',
					                checkbox: true,
					                singleSelect: false,
					               onClickRow:clickIndex
					            ">
						        <thead>
						            <tr>
						                <th data-options="field:'text',align:'center'" width="44%" >预算指标名称</th>
						                <th data-options="field:'col1',align:'right',formatter:pfcolor" width="20%">批复金额[万元]</th>
						                <th data-options="field:'col2',align:'right',formatter:sycolor" width="20%">可用金额[万元]</th>
						                <th data-options="field:'col3',align:'right',formatter:djcolor" width="20%">冻结金额[万元]</th>
						            </tr>
						        </thead>
						    </table> 
						</div>
					</div>
				</div>
			</div>	
			
			<div class="win-left-bottom-div" style="height: 40px;">
				<a href="javascript:void(0)" onclick="confirmKm(${drdc})">
				<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeFirstWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	</div>
</div>
	
<script type="text/javascript">
var tablist=1;
//初始化
$(function(){
	//tab
	flashtab('choice');
	
});

function confirmKm(drdc){
	var bids = '';
	var pids='';
	//
	/* if(tablist==1){
		var basicnodes = $('#basic-choose-tree').datagrid('getSelections');
		for(var i=0; i<basicnodes.length; i++){
			if(bids==''){
				bids=basicnodes[i].col4;
			}else{
				bids=bids+","+basicnodes[i].col4;
			}
		}
	}else if(tablist==2){
		var pronodes = $('#pro-choose-tree').datagrid('getSelections');
		for(var i=0; i<pronodes.length; i++){
			if(pids==''){
				pids=pronodes[i].col4;
			}else{
				pids=pids+","+pronodes[i].col4;
			}
		} 
	} */
	//如果只勾选父节点，会出现取不到子节点的问题
	pids=getDate(pids);
	if(pids==""){
		 //提示信息
	     $.messager.alert('系统提示',"请勾选被调整的指标！",'info');
	     return false;
	}
	//重新加载表格内容
	if(drdc == 0) {
		$('#zbdc').datagrid('reload',{bids:bids,pids:pids});
		//调出金额清空
		$('#snum1').textbox('setValue',0);
		$('#snum2').textbox('setValue',0);
	} else if(drdc == 1) {
		$('#zbdr').datagrid('reload',{bids:bids,pids:pids});
		$('#snum3').textbox('setValue',0);
		//调入指标清空
	}
	//将调入调出表格的焦点清除
	editIndex_outcome = undefined;
	editIndex_outcome2 = undefined;
	//关闭指标选择页面
	closeFirstWindow();
}
function getDate(pids){
	var rows = null;
	if(tablist==1){
		rows = $("#basic-choose-tree").treegrid("getData");
	}else if(tablist==2){
		rows = $("#pro-choose-tree").treegrid("getData");
	}
   	for(var i=0; i<rows.length; i++){
   		var checkState=rows[i].checkState;
   		if(checkState=="checked"||checkState=='indeterminate'){
   			var children = null;
   			if(tablist==1){
	   			children=$('#basic-choose-tree').treegrid("getChildren",rows[i].id);
   			}else if(tablist==2){
	   			children=$('#pro-choose-tree').treegrid("getChildren",rows[i].id);
   			}
   			for(var c=0;c<children.length;c++){
   				if(children[c].checkState=="checked"){
	   				if(pids==''){
						pids=children[c].col4;
					}else{
						pids=pids+","+children[c].col4;
					}
   				}
   			}
   			
   		}
   	}
   	return pids;
}
function clickIndex(node, checked) {
	/* if(tablist==2){ //项目指标 */
		if (node.leaf==false) {//如果是父节点，就展开子节点，并且取消改行被选中状态
			$(this).treegrid("toggle", node.id);//就展开子节点
			//$("#pro-choose-tree").datagrid("unselectRow", node.id);//取消改行被选中状态
	       // return false; 
	   /*  }else{
	    	if(node.checkState=="checked"){
	    		$("#pro-choose-tree").treegrid("uncheckNode",node.id);  //根据ID取消勾选节点	 
	    	}else{
	    		$("#pro-choose-tree").treegrid("checkNode",node.id);  //根据ID勾选节点
	    	}
		} */
		/*  var childNode =$(this).treegrid('getChildren', node.id);
		 if(childNode.length > 0) {
	        for (var i = 0; i < childNode.length; i++) {
	        	$(this).treegrid('check', childNode[i].id);//子节点勾选
	        }
	    } */
	}
	
}
function childrenCheck(node, checked){
	 var childNode =$(this).treegrid('getChildren', node.target);
	 if(childNode.length > 0) {
        for (var i = 0; i < childNode.length; i++) {
        	$(this).treegrid('check', childNode[i].target);//子节点勾选
        }
    }
}


//批复金额颜色
function pfcolor(val, row) {
	val = listToFixed(val);
	return '<span style="color:#5196d4">'+val+'</span>';
}

//剩余金额颜色
function sycolor(val, row) {
	val = listToFixed(val);
	return '<span style="color:#97cd5c">'+val+'</span>';
}

//冻结金额颜色
function djcolor(val, row) {
	val = listToFixed(val);
	return '<span style="color:#999999">'+val+'</span>';
}


//点击查询
function query() {
	$('#choice').datagrid('load', {
		indexNameOut:$('#inside_indexNameOut').val(),
	});
}
//清除查询条件
function clearTable() {
	$("#inside_indexNameOut").textbox('setValue','');
	$('#choice').datagrid('load',{});	//清空以后，重新查一次
}
</script>
</body>
