<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="window-tab" style="margin-left: 0px;padding-top: 10px">
		<table id="payer_info_tab" class="easyui-datagrid" style="width:695px;height:auto"
		data-options="
		singleSelect: true,
		toolbar: '#payer_info_toolbar_detail',
		url: '${base}/reimburse/payerInfojson?rId=${bean.rId}&fInnerOrOuter=0',
		method: 'post',
		striped : true,
		nowrap : false,
		rownumbers:true,
		scrollbarSize:0,
		">
			<thead>
				<tr>
					<th data-options="field:'pId',hidden:true"></th>
					<th data-options="field:'rId',hidden:true"></th>
					<th data-options="field:'fInnerOrOuter',hidden:true"></th>
					<th data-options="field:'payeeId',align:'center',hidden:true,editor:{type:'textbox',options:{editable:true}}">收款人ID</th>
					<th data-options="field:'payeeName',align:'center',editor:{type:'textbox'}" width="20%">收款人</th>
					<th data-options="field:'bankAccount',align:'center',editor:{type:'textbox',options:{required:true,editable:false}}" width="40%">银行账号</th>
					<th data-options="field:'bank',align:'center',editor:{type:'textbox',options:{required:true,editable:false}}" width="20%">开户银行</th>
					<th data-options="field:'payeeAmount',align:'center',editor:{type:'numberbox',options:{required:true,editable:true,precision:2,iconCls:'icon-yuan'}}" width="20%">转账金额</th>
				</tr>
			</thead>
		</table>
		<div id="payer_info_toolbar_detail" style="height:30px;padding-top : 8px">
			<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">单位内部收款人信息</a>
		</div>
</div>