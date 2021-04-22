<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<!-- 基本信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
	<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
		<table class="window-table" style="margin-top: 10px;margin-bottom: 5px;" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span> 合同报销单</td>
				<td class="td2" colspan="4">
					<input style="width: 620px; height: 30px;margin-left: 10px" id="f_rCodeshow" class="easyui-textbox"
					value="${bean.rCode}" data-options="required:true" readonly="readonly"/>
				</td>
			</tr>
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span> 合同编号</td>
				<td class="td2" colspan="4">
						<input style="width: 620px;height: 30px;margin-left: 10px" id="f_contCode" name="contCode" class="easyui-textbox"
						value="${bean.contCode}"readonly="readonly" data-options="required:true,editable:false" />
				</td>
			</tr>
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span> 合同名称</td>
				<td class="td2" colspan="4" onclick="viewContract()">
					<input style="width: 620px;height: 30px;" id="f_fcTitle" readonly="readonly" class="easyui-textbox" value="${bean.fcTitle}" />
				</td>
			</tr>
		</table>
		<table id="enfircing_payplan_dg" class="easyui-datagrid" style="width:693px;height:auto;margin-top: 5px;" 
			data-options="striped:true,nowrap:false,checkOnSelect:false,rownumbers:true,scrollbarSize:0,url:'${base}/Change/getReceivPlans?id=${bean.rId}'
			,method:'post'
			">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true,readonly:true"></th>	
					<th data-options="field:'fReceCondition',editor:{type:'textbox',options:{readonly:true}},validType:'length[1,50]',align:'center'"  style="width: 15%">付款条件</th>
					<th data-options="field:'fRecePlanAmount',align:'center',editor:{type:'numberbox',options:{readonly:true,precision:2}}"style="width: 15%">付款金额(元)</th>
					<th data-options="field:'fRecePlanTime',align:'center',editor:{type:'datebox',options:{readonly:true,precision:1}}" style="width: 20%">预计付款时间</th>
					<th data-options="field:'fReceProof',align:'center',editor:{type:'textbox'}" style="width: 25%">付款依据</th>
					<%-- <th data-options="field:'fReceProofs',align:'center',editor:{type:'combobox',editable:false,
							options:{
								readonly:true,
								valueField:'code',
								textField:'text',
								validType:'selectValid',
								method:'post',
								url:'${base}/Expiration/lookupsJson?parentCode=FKPJ',
								onHidePanel:onHidePanelUpt
							}
						}" style="width: 25%">付款依据</th> --%>
					<th data-options="field:'fStauts_R',align:'center',editor:{type:'textbox',options:{readonly:true,precision:2}},formatter:returnfStauts_R"style="width: 20%">付款状态</th>
				</tr>
			</thead>
		</table>
		<table id="checkaccept_tab" hidden="hidden" class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span>选择验收单</td>
				<td class="td2" colspan="4">
					<input id="checkacceptshow" value="${bean.checkacceptName }" class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="">
					
				</td>
			</tr>
		</table>
		<table id="checkFixedAsset_tab" hidden="hidden" class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span>选择固定资产入账单</td>
				<td class="td2" colspan="4">
					<input id="checkFixedAssetshow" value="${bean.checkFixedName }"  class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="">
				</td>
			</tr>
		</table>
		<table id="checkintangibleAsset_tab" hidden="hidden" class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span>无形资产入账单</td>
				<td class="td2" colspan="4">
					<input id="checkintangibleAssetshow" value="${bean.checkintangibleName }" class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="">
				</td>
			</tr>
		</table>
		<table id="checkWarehouseWarrant_tab" hidden="hidden" class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span>选择入库单</td>
				<td class="td2" colspan="4">
					<input id="checkWarehouseWarrantshow" value="${bean.checkWarehouseWarrant }" class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="">
				</td>
			</tr>
		</table>
		<table class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span> 付款金额</td>
				<td class="td2">
					<input class="easyui-numberbox" id="f_amount" name="amount" value="${bean.amount}" style="width: 200px; height: 30px;" readonly="readonly" required="required" data-options="precision:2,validType:'length[1,50]',icons: [{iconCls:'icon-yuan'}]"/>
				</td>
				<td style="width:140px"></td>
				<td style="width: 70px;"></td>
				<td class="td2">
				</td>
			</tr>
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span> 申请部门</td>
				<td class="td2">
					<input class="easyui-textbox" value="${bean.deptName}" style="width: 200px; height: 30px;" required="required" data-options="validType:'length[1,50]'" readonly="readonly"/>
				</td>
				<td style="width:140px"></td>
				<td style="width: 70px;"> 申请人</td>
				<td class="td2">
					<input class="easyui-textbox" value="${bean.userName}" style="width: 200px; height: 30px;" readonly="readonly" required="required" data-options="validType:'length[1,50]'"/>
				</td>
			</tr>
		</table>
	</div>				
</div>
<!-- 费用明细 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px;width: 695px">
	<div title="费用明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
			<div style="overflow:hidden;margin-top: 0px">
				<jsp:include page="enforcing_mingxi_detail.jsp" />
			</div>
			<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
			<tr>
				<td style="width:75px;text-align: left">&nbsp;附件
					<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'htbx','zcgl08')" hidden="hidden">
				</td>
				<td colspan="3" id="tdf">
					<c:forEach items="${attaList1}" var="att">
						<div style="">
							<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						</div>
					</c:forEach>
				</td>
			</tr>
		</table>
	</div>
</div>
<!-- 收款人信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
	<div title="收款人信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;padding:0px;">
		<div id="" style="overflow-x:hidden;margin-top: 0px;">
			<c:if test="${empty detailType}">
				<jsp:include page="../../expend/reimburse/reimburse/payee-info_detail.jsp" />
			</c:if>
			<c:if test="${!empty detailType}">
				<jsp:include page="../../expend/reimburse/reimburse/payee-info_detail_enforcing.jsp" />
			</c:if>
		</div>
	</div>
</div>
<!-- 附件信息 -->
<!-- <div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
	<div title="附件信息" data-options="collapsed:false,collapsible:false"
		style="overflow:auto;">		
		
	</div>
</div> -->
<c:if test="${openType != 'add'}">
	<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
		<div title="审批记录" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
			<jsp:include page="../../check_history.jsp" />												
		</div>
	</div>
</c:if>	
<script type="text/javascript">
$(function(){
	$('#enfircing_payplan_dg').datagrid({
		onLoadSuccess : function (data){
			var receivplanids = $('#f_receivplanid').val();
			var rows = '['+receivplanids.substring(0,receivplanids.length -0)+']';
			var rows = eval('(' + rows + ')');
			var allRows = $('#enfircing_payplan_dg').datagrid('getRows');
			for (var i = 0; i < allRows.length; i++) {
				for (var j = 0; j < rows.length; j++) {
					if (allRows[i].fPlanId == rows[j]) {
						$('#enfircing_payplan_dg').datagrid('checkRow',i);
						payplanSelect(allRows,i);
						break;
					}
				}
			}
			for (var b = 0; b < data.rows.length+1; b++) {
				 $("input[type='checkbox']")[b].disabled = true;
			}
		}
	});
	$('#checkacceptshow').tagbox({
		onBeforeRemoveTag:function(value){
			return false;
		},
		onClickTag:function(value){
			var checkacceptid = $('#f_checkacceptid').val();
			checkacceptid = '['+checkacceptid.substring(0,checkacceptid.length -1)+']';
			checkacceptid = eval('(' + checkacceptid + ')');
			for (var i = checkacceptid.length-1; i >= 0; i--) {
				if(checkacceptid[i][1]==value){
					//写跳转路径
					if(checkacceptid[i][2]==0){
						var win=creatSecondWin('选择', 780,580,'icon-search','/cgreceive/detail?id='+checkacceptid[i][0]+'&type=BX'); 
						win.window('open');
					}else{
						var win=creatSecondWin('选择',1115,560,'icon-search','/cgreceive/detail?id='+checkacceptid[i][0]+'&type=BX'); 
						win.window('open');
					}
				}
			}
		}
	});
	$('#checkFixedAssetshow').tagbox({
		onBeforeRemoveTag:function(value){
			return false;
		},
		onClickTag:function(value){
			var checkFixedAssetid = $('#f_checkFixedAssetid').val();
			checkFixedAssetid = '['+checkFixedAssetid.substring(0,checkFixedAssetid.length -1)+']';
			checkFixedAssetid = eval('(' + checkFixedAssetid + ')');
			for (var i = checkFixedAssetid.length-1; i >= 0; i--) {
				if(checkFixedAssetid[i][1]==value){
					//写跳转路径
					var win=creatFirstWin('选择', 720,580,'icon-search','/Storage/detail/'+checkFixedAssetid[i][0]+'?ledger=chooseDetail'); 
					win.window('open');
				}
			}
		}
	});
	$('#checkintangibleAssetshow').tagbox({
		onRemoveTag:function(value){
			return false;
		},
		onClickTag:function(value){
			var checkintangibleAssetid = $('#f_checkintangibleAssetid').val();
			checkintangibleAssetid = '['+checkintangibleAssetid.substring(0,checkintangibleAssetid.length -1)+']';
			checkintangibleAssetid = eval('(' + checkintangibleAssetid + ')');
			for (var i = checkintangibleAssetid.length-1; i >= 0; i--) {
				if(checkintangibleAssetid[i][1]==value){
					//写跳转路径
					var win=creatFirstWin('选择', 720,580,'icon-search','/Storage/detail/'+checkintangibleAssetid[i][0]+'?ledger=chooseDetail'); 
					win.window('open');
				}
			}
		}
	});
});


function payplanSelect(rows,index){
	if(rows[index].fReceProof=='FKPJ-1'){//验收单
		$('#checkaccept_tab').show();
	}else if(rows[index].fReceProof=='FKPJ-3'){//固定资产入账单
		$('#checkFixedAsset_tab').show();
	}else if(rows[index].fReceProof=='FKPJ-4'){//无形资产入账单
		$('#checkintangibleAsset_tab').show();
	}else if(rows[index].fReceProof=='FKPJ-2'){//入库单
		$('#checkWarehouseWarrant_tab').show();
	}
}
function onHidePanelUpt(){
	var index=$('#change-upt-datagrid').datagrid('getRowIndex',$('#change-upt-datagrid').datagrid('getSelected'));
	var ed = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:'fReceProof'});
	var eds = $('#change-upt-datagrid').datagrid('getEditor', {index:index,field:'fReceProofs'});
	ed.target.textbox('setValue',eds.target.combobox('getValues'));
}
function choosecont(){
	var win=creatSecondWin('选择',1115,550,'icon-search','/Enforcing/chooseCont'); 
	win.window('open');
}
function opencont(){
	/* var f_payId = $('#f_payId').val();
	var win=creatSecondWin('查看',1115,600,'icon-search','/Formulation/detail?id='+f_payId); 
	win.window('open'); */
}
function returnfStauts_R(val,row){
	if (val == 0) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 未付款" + '</span>';
	} else if (val == 1) {
		return '<span style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已付款" + '</span>';
	}
}
function openAcceptCheck(){
	var f_payId = $('#f_payId').val();
	var win=creatSecondWin('选择',1115,560,'icon-search','/cgreceive/chooseAcceptCheck?id='+f_payId); 
	win.window('open');
}

function viewContract(){
	
	var id = $('#f_payId').val();
	if ($('#contractUpdateStatus').val() == '0') {
		var win = creatSecondWin('查看', 1115, 600, 'icon-search', '/Formulation/detail?id='+id+'&contractUpdateStatus=htbx');
		win.window('open');
	} else if ($('#contractUpdateStatus').val() == '1') {
		var win = creatSecondWin('合同变更明细', 1115, 600, 'icon-search',"/Change/detail/${upt.fId_U}?contractUpdateStatus=htbx");
		win.window('open');
	}
}
</script>