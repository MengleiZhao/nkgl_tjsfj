<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<!-- <div class="window-title">审批记录</div> -->
<style>
	 .footer{
        width:100%;
        position:absolute;
        bottom:0;
		text-align: center;
    }

</style>
<div class="window-tab" style="margin-left: 0px;margin-top: 10px">

	<table id="check-history-dg" class="easyui-datagrid"  style="width:707px;height:auto"
	data-options="
	<c:if test="${empty foCode}">
	url: '',
	</c:if>
	<c:if test="${!empty foCode}">
	url: '${base}/wflow/history?fpId=${fpId}&foCode=${foCode}',
	</c:if>
	method: 'post'
	">
	<thead>
		<tr>
			<th data-options="field:'fuserName',required:'required',align:'center',width:'15%'">确认人</th>
			<th data-options="field:'fcheckResult',required:'required',align:'center',width:'15%',formatter: YJ">确认结果</th>
			<th data-options="field:'fcheckTime',required:'required',align:'center',width:'27.5%',formatter: ChangeDateFormat">确认时间</th>
			<th data-options="field:'fcheckRemake',required:'required',align:'left',width:'26%',formatter:formatCellTooltip">确认意见</th>
			<th data-options="field:'filesPid',align:'center',resizable:false,sortable:true,formatter:filesshow" width="20%">确认附件</th>
		</tr>
	</thead>
	</table>
</div>


<script type="text/javascript">
function YJ(val) {
	if(val==9){
		return "通过";
	} else if(val==5){
		return "不通过";
	}
}

function filesshow (val,row){
	if (val=="" || val==null) {
		return '<table><tr style="width: 75px;height:20px"><td><a href="#" class="easyui-linkbutton">无附件' +
			   '</a></td></table>';
	}else if (val!=null) {
		var data = val.split(',');
		if(data.length>0){
			return 	'<table><tr style="width: 75px;height:20px"><td><a href="#" onclick="accessory(\''+val+'\')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fj1.png">' +
					'</a></td></table>';
		}else {
			return 	'<table><tr style="width: 75px;height:20px"><td><a href="#" onclick="downloadFiles(\'' + data[0]+ '\')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fj1.png">' +
					'</a></td></table>';
		}
	}
}

function accessory(val){//查看
	var win = creatFirstWin('审批附件', 500, 300, 'icon-search', "/project/historyAttaListJsp?id="+val);
	win.window('open');
}

function downloadFiles(id){
	if(id==null){
		alert("没有相关附件！");
		return;
	}
	//window.open(base+'/systemcentergl/viewPDF/'+fileName,'open');
	window.open(base+'/attachment/download/'+id,'open');
}
</script>
