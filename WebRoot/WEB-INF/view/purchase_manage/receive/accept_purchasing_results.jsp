<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="ys_tab_id" class="easyui-datagrid" 
data-options="
singleSelect: true,
rownumbers : true,
striped:true,
<c:if test="${!empty bean.facpId&&openType=='edit'}">
url: '${base}/cgreceive/acceptContractRegisterList?id=${bean.facpId}',
</c:if>
<c:if test="${empty bean.facpId}">
url: '',
</c:if>
method: 'post',
onClickRow: onClickRowYS
">
<thead>
	<tr>
		<th data-options="field:'acRId',hidden:true"></th>
		<th data-options="field:'cRId',hidden:true"></th>
		<th data-options="field:'fcId',hidden:true"></th>
		<th data-options="field:'fmModel',hidden:true"></th>
		<th data-options="field:'fId_U',hidden:true"></th>
		<c:if test="${bean.fpItemsName=='PMMC-4' || bean.fpItemsName=='PMMC-5'}">
		<th data-options="field:'fmName',align:'center'" style="width: 50%">商品名称</th>
		<th data-options="field:'fmatchRemark',align:'center',editor:{type:'textbox'}" style="width: 52%">本次验收内容</th>
		</c:if>
		<c:if test="${bean.fpItemsName!='PMMC-4' && bean.fpItemsName!='PMMC-5'}">
		<th data-options="field:'fmName',align:'center'" style="width: 27%">商品名称</th>
		<th data-options="field:'fmSpecif',align:'center'" style="width: 20%">型号</th>
		<th data-options="field:'fpNum',align:'center'" style="width: 18%">采购数量</th>
		<th data-options="field:'fCheckedNum',align:'center'" style="width: 15%">已验收数量</th>
		<th data-options="field:'fNowCheckedNum',align:'center',editor:{type:'numberbox',options:{required:true}}" style="width: 23%">本次申请验收数量</th>
		</c:if>
	</tr>
</thead>
</table>
<table cellpadding="0" cellspacing="0" border="0" style="margin-top: 30px;">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;验收人</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_facpUsername" name="facpUsername" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.facpUsername}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;验收意见</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_fmatchRemark" name="fmatchRemark" required="required" data-options="" style="width: 555px" value="${bean.fmatchRemark}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1">
			&nbsp;&nbsp;协助验收意见
			<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'cgys','cgys01')" hidden="hidden">
			<input type="text" id="files" name="files" hidden="hidden">
		</td>
		<td colspan="4" id="tdf">
			<a onclick="$('#f').click()" style="font-weight: bold;" href="#">
				<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<c:forEach items="${acAttac}" var="att">
				<c:if test="${att.serviceType=='cgys' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
			<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
				<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" ></div>
				文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
			</div>
		</td>
	</tr>
</table>
<input type="hidden" id="jsonList" name="jsonList"/>
<script type="text/javascript">

//明细表格添加删除，保存方法
var editIndexYS = undefined;
function endEditingYS() {
	if (editIndexYS == undefined) {
		return true;
	}
	if ($('#ys_tab_id').datagrid('validateRow', editIndexYS)) {
		$('#ys_tab_id').datagrid('endEdit', editIndexYS);
		editIndexYS = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowYS(index,id) {
	if (editIndexYS != index) {
		if (endEditingYS()) {
			$('#ys_tab_id').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndexYS = index;
		} else {
			$('#ys_tab_id').datagrid('selectRow', editIndexYS);
		}
	}	
}

function appendYS() {//未配置采购类型不可添加采购清单
	 if (endEditingYS()) {
			$('#ys_tab_id').datagrid('appendRow', {});
			editIndexYS = $('#ys_tab_id').datagrid('getRows').length - 1;
			$('#ys_tab_id').datagrid('selectRow', editIndexYS).datagrid('beginEdit',editIndexYS);
		} 
}
function removeitYS() {
	if (editIndexYS == undefined) {
		return;
	}
	$('#ys_tab_id').datagrid('cancelEdit', editIndexYS).datagrid('deleteRow',editIndexYS);
	editIndexYS = undefined;
	//修改申请总额
	//setfamount(0,0);
}

function acceptYS() {
	if (endEditingYS()) {
		$('#ys_tab_id').datagrid('acceptChanges');
	}
}

</script>