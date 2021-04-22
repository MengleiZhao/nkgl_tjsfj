<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<style type="text/css">
</style> 

<div>
	<table>
		<tr class="trbody">
	   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;总体目标</td>
	     	<td class="td2" colspan="4">
	     		<input class="easyui-textbox" data-options="validType:'length[1,500]'" style="height:30px; width:746px;" required="required"
	     		id="project_add_totalityDescribe" name="totalityDescribe" value="<c:out value="${bean.totalityDescribe}"></c:out>" >
			</td>
		</tr>
	</table>
</div>

<div style="height:30px;width: 870px;margin-top: 5px;">
	<input type="hidden" id="totalityPerformanceJson" name="totalityPerformanceJson"/>
	<a style="float: right;" href="javascript:void(0)" onclick="expenddetailsremoveit()"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a style="float: right;" href="javascript:void(0)" onclick="performanceAppend()"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
</div>
<table id="performance" class="easyui-datagrid" style="width:870px;height:auto"
data-options="
singleSelect: true,
toolbar: '#tb',
rownumbers : true,
url: '${base}/project/proIndexList?fProId=${bean.FProId}',
method: 'post',
onClickRow: onClickRow_performance
">

</table>

<script type="text/javascript">

var expenddetailsCount=0;
var numIndex=0;
$('#performance').datagrid({
	onBeforeLoad : function (param){
		if(expenddetailsCount>3){
			return false;
		}else{
			expenddetailsCount=expenddetailsCount+1;
			return true;
		}
	},
	onLoadSuccess:function(data){
		numIndex++;
		if(numIndex==3&&(data.rows).length==0){
			performanceAppend();
		}
	}
});

var performanceEditIndex = undefined;
//填写清单信息  
$('#performance').datagrid({
	columns:[[
				{field:'tOneCode',title:'一级指标编码',width:0,editor:{type:'textbox'},hidden:true},
		        {field:'tOneName',title:'一级指标',width:"33%",editor:{
  					editable:true,
					type:'combotree',
					options:{
						valueField:'text',//编码
						textField:'text',//名称
						method:'post',
						url: '${base}/lookup/lookupsJson?parentCode=YJZB',
						onSelect:function(item){
							if("-请选择-"==item.text){
								return "";
							}
							var index=$('#performance').datagrid('getRowIndex',$('#performance').datagrid('getSelected'));
							var tr = $('#performance').datagrid('getEditors', index);
							tr[0].target.textbox('setValue', item.code);
						}
					}
				},styler: function (value, row, index) { return 'border-bottom: 1px dashed #ccc;border-right: 1px dashed #ccc;';}},
				{field:'tTwoName',title:'二级指标',width:"34%",editor:{type:'textbox'},styler: function (value, row, index) { return 'border-bottom: 1px dashed #ccc;border-right: 1px dashed #ccc;';}},
				{field:'tIndexVal',title:'指标值',width:"34%",editor:{type:'textbox'},styler: function (value, row, index) { return 'border-bottom: 1px dashed #ccc;border-right: 1px dashed #ccc;';}},
		   ]],

});
//添加一行
function performanceAppend() {
	 if (performanceEndEditing()) {
			$('#performance').datagrid('appendRow', {});
			beginIndex = $('#performance').datagrid('getRows').length - 1;
			$('#performance').datagrid('selectRow', beginIndex).datagrid('beginEdit', beginIndex);
			performanceEditIndex=beginIndex;
		}

	//页面随滚动条置底
	/* var div = document.getElementById('westDiv');
	div.scrollTop = div.scrollHeight; */
}
//删除一行
function expenddetailsremoveit() {
	if (performanceEditIndex == undefined) {
		alert('请点击要删除的行！');
		return;
	}
	$('#performance').datagrid('cancelEdit', performanceEditIndex).datagrid('deleteRow',
			performanceEditIndex);
	performanceEditIndex = undefined;
	
}

//使列表结束编辑状态
function performanceAccept() {
	if (performanceEndEditing()) {
		$('#performance').datagrid('acceptChanges');
	}
}
//结束编辑状态
function performanceEndEditing() {
	if (performanceEditIndex == undefined) {
		return true;
	}
	if ($('#performance').datagrid('validateRow', performanceEditIndex)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#performance').datagrid('getEditors', performanceEditIndex);
		var text=tr[1].target.combotree('getText');
		if(text!='--请选择--'){
			tr[1].target.combotree('setValue',text);
		}
		$('#performance').datagrid('endEdit', performanceEditIndex);
		performanceEditIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//行被点击事件
function onClickRow_performance(index) {
	if (performanceEditIndex != index) {
		if (performanceEndEditing()) {
			$('#performance').datagrid('selectRow', index).datagrid('beginEdit', index);
			performanceEditIndex = index;
		} else {
			$('#performance').datagrid('selectRow', performanceEditIndex);
		}
	}
}

//存入json
function getPerformanceJson(){
	performanceAccept();
	var rows = $('#performance').datagrid('getRows');
	var performance = "";
	for (var j = 0; j < rows.length; j++) {
		performance = performance + JSON.stringify(rows[j]) + ",";
	}
	$('#totalityPerformanceJson').val(performance);
}
</script>
