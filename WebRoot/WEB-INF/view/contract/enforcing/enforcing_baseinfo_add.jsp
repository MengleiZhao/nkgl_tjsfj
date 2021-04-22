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
						value="${bean.contCode}" data-options="prompt: '选择合同',required:true,editable:false,icons: [{
						iconCls:'icon-sousuo',
						handler: function(e){
							var win=creatSecondWin('选择',1115,550,'icon-search','/Enforcing/chooseCont'); 
							win.window('open');
						}
					}]" />
					<!-- </a> -->
				</td>
			</tr>
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span> 合同名称</td>
				<td class="td2" colspan="4" onclick="opencont()">
					<input style="width: 620px;height: 30px;" id="f_fcTitle"  readonly="readonly" class="easyui-textbox" value="${bean.fcTitle}" />
				</td>
			</tr>
		</table>
		<table id="enfircing_payplan_dg" class="easyui-datagrid" style="width:693px;height:auto;margin-top: 5px;" 
			data-options="striped:true,nowrap:false,rownumbers:true,scrollbarSize:0,checkOnSelect:false,<c:if test="${!empty bean.rId}">url:'${base}/Change/getReceivPlans?id=${bean.rId}'
			,</c:if>method:'post'
			">
			<thead>
				<tr>	
					<th data-options="field:'ck',checkbox:true"></th>
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
					<input id="checkacceptshow" value="${bean.checkacceptName }" class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="prompt: '选择验收单',editable:false ,icons: [{
						iconCls:'icon-sousuo',
						handler: function(e){
							var f_payId = $('#f_payId').val();
							if(f_payId==null||f_payId==''){
								alert('请先选择合同');
							}else{
								var win=creatSecondWin('选择',1115,560,'icon-search','/cgreceive/chooseAcceptCheck?id='+f_payId); 
								win.window('open');
							}
						}
					}]">
					
				</td>
			</tr>
		</table>
		<table id="checkFixedAsset_tab" hidden="hidden" class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span>选择固定资产入账单</td>
				<td class="td2" colspan="4">
					<input id="checkFixedAssetshow" value="${bean.checkFixedName }" class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="prompt: '选择固定资产入账单',editable:false ,icons: [{
						iconCls:'icon-sousuo',
						handler: function(e){
							var f_payId = $('#f_payId').val();
							var checkacceptid = $('#f_checkacceptid').val();
							var selectUptId = $('#selectUptId').val();
							var selectContId = $('#selectContId').val();
							var win=creatSecondWin('选择',1115,560,'icon-search','/Enforcing/choosefixedstorage?fAssType=ZCLX-02&id='+f_payId+'&checkacceptid='+checkacceptid+'&selectUptId='+selectUptId+'&selectContId='+selectContId); 
							win.window('open');
						}
					}]">
					
				</td>
			</tr>
		</table>
		<table id="checkintangibleAsset_tab" hidden="hidden" class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span>无形资产入账单</td>
				<td class="td2" colspan="4">
					<input id="checkintangibleAssetshow" value="${bean.checkintangibleName }"  class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="prompt: '选择无形资产入账单',editable:false ,icons: [{
						iconCls:'icon-sousuo',
						handler: function(e){
							var f_payId = $('#f_payId').val();
							var checkacceptid = $('#f_checkacceptid').val();
							var selectUptId = $('#selectUptId').val();
							var selectContId = $('#selectContId').val();
							var win=creatSecondWin('选择',1115,560,'icon-search','/Enforcing/chooseintangiblestorage?fAssType=ZCLX-03&id='+f_payId+'&checkacceptid='+checkacceptid+'&selectUptId='+selectUptId+'&selectContId='+selectContId); 
							win.window('open');
						}
					}]">
				</td>
			</tr>
		</table>
		<table id="checkWarehouseWarrant_tab" hidden="hidden" class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span>选择入库单</td>
				<td class="td2" colspan="4">
					<input id="checkWarehouseWarrantshow" value="${bean.checkWarehouseWarrant }"  class="easyui-tagbox"  style="width: 620px;height: 30px;" data-options="prompt: '选择入库单',editable:false ,icons: [{
						iconCls:'icon-sousuo',
						handler: function(e){
						}
					}]">
				</td>
			</tr>
		</table>
		<table class="window-table" style="margin-top: 5px" cellspacing="0" cellpadding="0">
			<input id="selectUptId" type="hidden">
			<input id="selectContId" type="hidden">
			<tr class="trbody">
				<td style="width: 70px;"><span class="style1">*</span> 付款金额</td>
				<td class="td2">
					<input class="easyui-numberbox" id="f_amount" value="${bean.amount}" style="width: 200px; height: 30px;" readonly="readonly" required="required" data-options="precision:2,validType:'length[1,50]',icons: [{iconCls:'icon-yuan'}]"/>
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
		
		<c:if test="${openType=='add'}">		
			<div style="overflow:hidden;margin-top: 0px">
				<!-- 通用事项发票明细 -->
				<jsp:include page="enforcing_mingxi.jsp" />
			</div>
		</c:if>
		<c:if test="${openType=='edit'}">		
			<div style="overflow:hidden;margin-top: 0px">
				<!-- 通用事项发票明细 -->
				<jsp:include page="enforcing_mingxi_edit.jsp" />
			</div>
		</c:if>
		<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
			<tr>
				<td style="width:75px;text-align: right">
					附件
					<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'htbx','zcgl08')" hidden="hidden">
				</td>
				<td colspan="3" id="tdf">
					&nbsp;&nbsp;
					<a onclick="$('#f').click()" style="font-weight: bold;  " href="#">
						<img style="vertical-align:bottom;margin-bottom: 5px;" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
					</a>
					<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
						<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
						 </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
					</div>
					<c:forEach items="${attaList1}" var="att">
						<div>
							<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="${base}/resource-modality/${themenurl}/sccg.png">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
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
			<%-- <jsp:include page="../../expend/reimburse/reimburse/payee-info.jsp" /> --%>
			<jsp:include page="../../expend/reimburse/reimburse/payee-info-enforcing.jsp" />
		</div>
	</div>
</div>
<!-- 附件信息 -->
<!-- <div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
	<div title="附件信息" data-options="collapsed:false,collapsible:false"
		style="overflow:auto;">		

	</div>
</div> -->

<script type="text/javascript">
$(function(){
			var checkFlag = true;
	$('#enfircing_payplan_dg').datagrid({
		onCheck:function(rowIndex,rowData){
			var rpId = $('#f_receivplanid').val();
			var rpIdTemp = rpId + ',';
			if (rpIdTemp.indexOf(rowData.fPlanId + ',') == -1 || rpId == '') {
				var receivplanids = $('#f_receivplanids').val();
				if (receivplanids.indexOf(rowData.fPlanId + ',') >= 0) {
					alert('该付款计划已暂存或已发起，不可重复选择');
					checkFlag = false;
					$('#enfircing_payplan_dg').datagrid('uncheckRow',rowIndex);
					return false;
				}else{
					checkFlag = true;
				}
			}
			if(rowData.fStauts_R==1){//已付款
				alert('该付款计划已付款，不可重复选择');
				$('#enfircing_payplan_dg').datagird('uncheckRow',rowIndex);
				return false;
				
			}else{
				var amount = $('#f_amount').numberbox('getValue');
				if(!amount){
					$('#f_amount').numberbox('setValue',rowData.fRecePlanAmount);
				}else {
					amount = parseFloat(amount) + parseFloat(rowData.fRecePlanAmount);
					$('#f_amount').numberbox('setValue',amount);
				}
				setTimeout(function() {
					$('#payer_info_tab').datagrid('updateRow',{
						index: 0,
						row: {
							'payeeAmount':$('#f_amount').numberbox('getValue')
						}
					});
				}, 1500);
			}
			if(rowData.fReceProof=='FKPJ-1' && checkFlag){//验收单
				$('#checkaccept_tab').show();
			}else if(rowData.fReceProof=='FKPJ-3' && checkFlag){//固定资产入账单
				if (rowData.fUptId_R != '' && rowData.fUptId_R != null) {
					$('#selectUptId').val(rowData.fUptId_R);
				} else {
					$('#selectContId').val(rowData.fContId_R);
				}
				$('#checkFixedAsset_tab').show();
			}else if(rowData.fReceProof=='FKPJ-4' && checkFlag){//无形资产入账单
				if (rowData.fUptId_R != '' && rowData.fUptId_R != null) {
					$('#selectUptId').val(rowData.fUptId_R);
				} else {
					$('#selectContId').val(rowData.fContId_R);
				}
				$('#checkintangibleAsset_tab').show();
			}else if(rowData.fReceProof=='FKPJ-2' && checkFlag){//入库单
				if (rowData.fUptId_R != '' && rowData.fUptId_R != null) {
					$('#selectUptId').val(rowData.fUptId_R);
				} else {
					$('#selectContId').val(rowData.fContId_R);
				}
				$('#checkWarehouseWarrant_tab').show();
			}
		},
		onUncheck:function(rowIndex,rowData){
			var amount = $('#f_amount').numberbox('getValue');
			if(rowData.fStauts_R!=1){//已付款
				if(checkFlag){
					$('#f_amount').numberbox('setValue',parseFloat(amount)-parseFloat(rowData.fRecePlanAmount));
				}
			}
			var rows =$('#enfircing_payplan_dg').datagrid('getSelections');
			if(rowData.fReceProof=='FKPJ-1'){//验收单
				$('#f_checkFixedAssetid').val(null);
				var count = 0;
				for (var i = 0; i < rows.length; i++) {
					if(rows[i].fReceProof=='FKPJ-1'){
						count ++;
					}
				}
				if (count == 0) {
					$('#checkacceptshow').tagbox('setValue', '');
					$('#checkaccept_tab').hide();
				}
			}else if(rowData.fReceProof=='FKPJ-3'){//固定资产入账单
				$('#selectContId').val('');
				$('#selectUptId').val('');
				$('#f_checkFixedAssetid').val(null);
				var count = 0;
				for (var i = 0; i < rows.length; i++) {
					if(rows[i].fReceProof=='FKPJ-3'){
						count ++;
					}
				}
				if (count == 0) {
					$('#checkFixedAssetshow').tagbox('setValue', '');
					$('#checkFixedAsset_tab').hide();
				}
			}else if(rowData.fReceProof=='FKPJ-4'){//无形资产入账单
				$('#f_checkintangibleAssetid').val(null);
				var count = 0;
				for (var i = 0; i < rows.length; i++) {
					if(rows[i].fReceProof=='FKPJ-4'){
						count ++;
					}
				}
				if (count == 0) {
					$('#checkintangibleAssetshow').tagbox('setValue', '');
					$('#checkintangibleAsset_tab').hide();
				}
			}else if(rowData.fReceProof=='FKPJ-2'){//入库单
				$('#f_checkWarehouseWarrantid').val(null);
				var count = 0;
				for (var i = 0; i < rows.length; i++) {
					if(rows[i].fReceProof=='FKPJ-2'){
						count ++;
					}
				}
				if (count == 0) {
					$('#checkWarehouseWarrantshow').tagbox('setValue', '');
					$('#checkWarehouseWarrant_tab').hide();
				}
			}
			setTimeout(function() {
				$('#payer_info_tab').datagrid('updateRow',{
					index: 0,
					row: {
						'payeeAmount':$('#f_amount').numberbox('getValue')
					}
				});
			}, 1500);
		},
		onLoadSuccess : function (data){
			
			$('#f_amount').numberbox('setValue',0.00);
			var receivplanids = $('#f_receivplanid').val();
			var rows = '['+receivplanids.substring(0,receivplanids.length -0)+']';
			var rows = eval('(' + rows + ')');
			var allRows = $('#enfircing_payplan_dg').datagrid('getRows');
			for (var i = 0; i < allRows.length; i++) {
				for (var j = 0; j < rows.length; j++) {
					if (allRows[i].fPlanId == rows[j]) {
						$('#enfircing_payplan_dg').datagrid('checkRow',i);
						break;
					}
				}
			}
			
			//禁用全选
			$(".datagrid-header-check").html('');
		}
	});
	$('#checkacceptshow').tagbox({
		onRemoveTag:function(value){
			var checkacceptid = $('#f_checkacceptid').val();
			checkacceptid = '['+checkacceptid.substring(0,checkacceptid.length -1)+']';
			checkacceptid = eval('(' + checkacceptid + ')');
			for (var i = checkacceptid.length-1; i >= 0; i--) {
				if(checkacceptid[i][1]==value){
					checkacceptid.splice(i, 1);
				}else {
					
				}
			}
			var idatt ;
			for (var i = 0; i < checkacceptid.length; i++) {
				if(idatt==undefined||idatt==''||idatt==null){
					idatt = '[\''+checkacceptid[i][0]+"\',\'"+checkacceptid[i][1]+'\']'+ ',';
				}else {
					idatt = idatt +'[\''+checkacceptid[i][0]+"\',\'"+checkacceptid[i][1]+'\']'+ ',';
				}
			}
			$('#f_checkacceptid').val(idatt);
		},
		onClickTag:function(value){
			
			var checkacceptid = $('#f_checkacceptid').val();
			checkacceptid = '['+checkacceptid.substring(0,checkacceptid.length -1)+']';
			checkacceptid = eval('(' + checkacceptid + ')');
			for (var i = checkacceptid.length-1; i >= 0; i--) {
				if(checkacceptid[i][1]==value){
					//写跳转路径
					if(checkacceptid[i][2]==0){
						var win=creatFirstWin('选择', 780,580,'icon-search','/cgreceive/detail?id='+checkacceptid[i][0]); 
						win.window('open');
					}else{
						var win=creatFirstWin('选择',1115,560,'icon-search','/cgreceive/detail?id='+checkacceptid[i][0]); 
						win.window('open');
					}
				}
			}
		}
	});
	$('#checkFixedAssetshow').tagbox({
		onRemoveTag:function(value){
			var checkFixedAssetid = $('#f_checkFixedAssetid').val();
			checkFixedAssetid = '['+checkFixedAssetid.substring(0,checkFixedAssetid.length -1)+']';
			checkFixedAssetid = eval('(' + checkFixedAssetid + ')');
			for (var i = checkFixedAssetid.length-1; i >= 0; i--) {
				if(checkFixedAssetid[i][1]==value){
					checkFixedAssetid.splice(i, 1);
				}else {
					
				}
			}
			var idatt ;
			for (var i = 0; i < checkFixedAssetid.length; i++) {
				if(idatt==undefined||idatt==''||idatt==null){
					idatt = '[\''+checkFixedAssetid[i][0]+"\',\'"+checkFixedAssetid[i][1]+'\']'+ ',';
				}else {
					idatt = idatt +'[\''+checkFixedAssetid[i][0]+"\',\'"+checkFixedAssetid[i][1]+'\']'+ ',';
				}
			}
			$('#f_checkFixedAssetid').val(idatt);
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
			var checkintangibleAssetid = $('#f_checkintangibleAssetid').val();
			checkintangibleAssetid = '['+checkintangibleAssetid.substring(0,checkintangibleAssetid.length -1)+']';
			checkintangibleAssetid = eval('(' + checkintangibleAssetid + ')');
			for (var i = checkintangibleAssetid.length-1; i >= 0; i--) {
				if(checkintangibleAssetid[i][1]==value){
					checkintangibleAssetid.splice(i, 1);
				}
			}
			var idatt ;
			for (var i = 0; i < checkintangibleAssetid.length; i++) {
				if(idatt==undefined||idatt==''||idatt==null){
					idatt = '[\''+checkintangibleAssetid[i][0]+"\',\'"+checkintangibleAssetid[i][1]+'\']'+ ',';
				}else {
					idatt = idatt +'[\''+checkintangibleAssetid[i][0]+"\',\'"+checkintangibleAssetid[i][1]+'\']'+ ',';
				}
			}
			$('#f_checkintangibleAssetid').val(idatt);
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
	var id = $('#f_payId').val();
	if ($('#contractUpdateStatus').val() == '0') {
		var win = creatSecondWin('查看', 1115, 600, 'icon-search', '/Formulation/detail?id='+id+'&contractUpdateStatus=htbx');
		win.window('open');
	} else if ($('#contractUpdateStatus').val() == '1') {
		var uptId;
		if ('${upt.fId_U}' == '') {
			uptId = $('#uptId').val();
		} else {
			uptId = '${upt.fId_U}'
		}
		var win = creatSecondWin('合同变更明细', 1115, 600, 'icon-search',"/Change/detail/" + uptId +"?contractUpdateStatus=htbx");
		win.window('open');
	}
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

</script>