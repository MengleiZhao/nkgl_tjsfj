<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同编号</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fcCode" name="fcCode" readonly="readonly" required="required" data-options="validType:'length[1,32]'" style="width: 555px" value="${bean.fcCode}"/>
		</td>
	</tr>
			
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同名称</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fcTitle" name="fcTitle" required="required" data-options="validType:'length[1,50]'" style="width: 555px" value="${bean.fcTitle}"/>
		</td>
	</tr>
			
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同分类</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fcType" name="fcType" required="required" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=HTFL&selected=${bean.fcType}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid',
			
			onSelect:onSelectFcType
			" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同份数</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fcNum" name="fcNum" required="required" data-options="validType:'length[1,2]',precision:0" style="width: 200px" value="${bean.fcNum}"/>
		</td>
	</tr>
			
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目</td>
		<td colspan="4">
			<a onclick="fPurchNo_DC()">
				<input class="easyui-textbox" id="F_fPurchName" name="fPurchName" required="required" data-options="validateOnCreate:false,prompt:'单击打开选取采购订单',validType:'length[1,50]',editable:false" style="width: 555px" value="${bean.fPurchName}"/>
			</a>
		</td>
	</tr>
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fContractor" readonly="readonly" required="required" value="${bean.fContractor}" data-options="validateOnCreate:false,editable:false,panelHeight:'auto',url:'${base}/Formulation/fContractorlookupsJson?blanked=0&parentCode=${bean.fPurchNo}&selected=${bean.fContractor}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid',
			onSelect:function (rec){
				$.ajax({
					url:base+'/Formulation/findBiddingRegist',
					type:'POST',
					data:{'code':rec.code,'fContractstatus':0},
					success : function(data) {
						data = eval('(' + data + ')');
						$('#f_fOfficialUser').textbox('setValue',data.flegal);
					}
				});
				var fpid = $('#F_fPurchNo').val();
				$('#contract_cgplan_dg_detail').datagrid('reload',base+'/cgconfplangl/mingxilist?code='+rec.code);
				$('#contract_cgplan_dg_detail').datagrid({
					onLoadSuccess:function(){
					var fpItemsName = $('#F_fpItemsName').val();
						/*if(fpItemsName=='PMMC-4' || fpItemsName=='PMMC-5'){
							$('#contract_cgplan_dg_detail').datagrid('hideColumn', 'fpNum');
							$('#contract_cgplan_dg_detail').datagrid('hideColumn', 'fmModel');
							$('#contract_cgplan_dg_detail').datagrid('hideColumn', 'fWhetherImport');
							$('#contract_cgplan_dg_detail').datagrid('hideColumn', 'fsignPrice');
							$('#contract_cgplan_dg_detail').datagrid('hideColumn', 'fBrand');
							$('#contract_cgplan_dg_detail').datagrid('hideColumn', 'fmSpecif');
						}else{
							$('#contract_cgplan_dg_detail').datagrid('showColumn', 'fpNum');
							$('#contract_cgplan_dg_detail').datagrid('showColumn', 'fmModel');
							$('#contract_cgplan_dg_detail').datagrid('showColumn', 'fWhetherImport');
							$('#contract_cgplan_dg_detail').datagrid('showColumn', 'fsignPrice');
							$('#contract_cgplan_dg_detail').datagrid('showColumn', 'fBrand');
							$('#contract_cgplan_dg_detail').datagrid('showColumn', 'fmSpecif');
						}*/
						var cgplanrows = $('#contract_cgplan_dg_detail').datagrid('getRows');
						var totalAmount = 0.00;
						for(var i = 0;i < cgplanrows.length;i++){
							totalAmount = parseFloat(totalAmount) + parseFloat(cgplanrows[i].famount);
						}
						$('#totalPricespan').html(totalAmount+'[元]');
						$('#totalPrice').val(totalAmount);
						var fcType = $('#F_fcType').combobox('getValue');
						if(fcType=='HTFL-01'){
							$('#F_fcAmount').numberbox('setValue',totalAmount);
						}
					}
				});
			}" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同保证金</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fMarginAmount"  required="required" data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fMarginAmount}"/>
		</td>
	</tr>
	
	<tr class="trbody " id="fgcHt" >
		<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fContractor1"  required="required"  data-options="validateOnCreate:false"  value="${bean.fContractor}"
			 style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同保证金</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fMarginAmount1"  required="required" data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fMarginAmount}"/>
		</td>
	</tr>
	
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;合同金额</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fcAmount" readonly="readonly" name="fcAmount" required="required" data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fcAmount}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;大写金额</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fcAmountMax" name="fcAmountMax" readonly="readonly" required="required" data-options="validateOnCreate:false,validType:'length[1,25]'" style="width: 200px" value="${bean.fcAmountMax}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同开始日期</td>
		<td class="td2">
			<input class="easyui-datebox" id="F_fContStartTime" name="fContStartTime" required="required"  style="width: 200px" data-options="validType:'length[1,20]',editable:false,onHidePanel:onHidepanel1" value="${bean.fContStartTime}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同结束日期</td>
		<td class="td2">
			<input class="easyui-datebox" id="F_fContEndTime" name="fContEndTime"  required="required" data-options="validType:'length[1,20]',editable:false,onHidePanel:onHidepanel" style="width: 200px" value="${bean.fContEndTime}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fOperator" name="fOperator" readonly="readonly" required="required" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fOperator}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fDeptName" name="fDeptName" readonly="readonly" required="required" data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${bean.fDeptName}"/>
		</td>
	</tr>
			
	<%-- <tr class="trbody" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;预算指标名称</td>
		<td colspan="4">
			<a onclick="BudgetIndexCode()">
				<input class="easyui-textbox" id="F_fBudgetIndexName" name="fBudgetIndexName" required="required" data-options="prompt:'单击打开选取指标',validType:'length[1,50]'" style="width: 555px" value="${bean.fBudgetIndexName}"/>
			</a>
		</td>
	</tr>
			
	<tr class="trbody" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;预算指标金额</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fAvailableAmount" name="fAvailableAmount" readonly="readonly" required="required" data-options="icons: [{iconCls:'icon-yuan'}],precision:2,validType:'length[0,20]'" style="width: 200px" value="${bean.fAvailableAmount}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;所属部门</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fDeptName" name="fDeptName" readonly="readonly" data-options="validType:'length[1,50]'" style="width: 200px" value="${bean.fDeptName}"/>
		</td>
	</tr> --%>
			
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;付款方式</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fPayType" name="fPayType.code" required="required" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=FKFS&selected=${bean.fPayType.code}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1">&nbsp;&nbsp;</td>
		<td class="td2" >
			<input class="easyui-textbox" id="F_payTypeRemark" hidden="hidden" data-options="hidden:true" name="payTypeRemark" value="${bean.payTypeRemark }" style="width: 200px" />
		</td>
	</tr>
	<%-- <tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;质保期</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fWarrantyPeriod" name="fWarrantyPeriod" required="required" data-options="icons: [{iconCls:'icon-yue'}],validType:'length[1,20]'" style="width: 200px" value="${bean.fWarrantyPeriod}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1">是否制式合同</td>
		<td class="td2">
			<input type="radio" name="standard" value="1" <c:if test="${bean.standard==1}">checked="checked"</c:if> />是
			<input type="radio" name="standard" value="0" <c:if test="${bean.standard==0}">checked="checked"</c:if> />否
		</td>
	</tr> --%>
	<tr style="height: 70px;">
		<td class="td1" valign="top">合同情况说明</td>
		<td colspan="4">
			<textarea class="textbox-text"  id="F_fRemark" name="fRemark" oninput="textareaNum(this,'textareaNum1')" autocomplete="off"  style="width:555px;height:70px">${bean.fRemark}</textarea>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1">
			<span style="color: red">*</span>&nbsp;合同文本
			<input type="file" multiple="multiple" id="fhtnd" onchange="upladFile(this,'htnd','htgl01')" hidden="hidden">
			<input type="text" id="files" name="files" hidden="hidden">
		</td>
		<td colspan="4" id="tdf">
			<a onclick="$('#fhtnd').click()" style="font-weight: bold;" href="#">
				<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
				<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
				</div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
			</div>
			<c:forEach items="${formulationAttaList}" var="att">
				<c:if test="${att.serviceType=='htnd' }">
					<div style="margin-top: 5px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>
<script type="text/javascript">
function onHidepanel1(){
	$('#F_fContEndTime').datebox('setValue','');
}
function onHidepanel(){
	endday =  $('#F_fContStartTime').datebox('getValue');
	startday = $('#F_fContEndTime').datebox('getValue');
	endday2 = new Date(endday);
	startday2 = new Date(startday);
	var d = (endday2 - startday2) / 86400000;
	if(d > 0){
		$('#F_fContEndTime').datebox('setValue','');
		alert("合同结束日期必须大于合同开始日期！");
	}
}

$('#F_fPayType').combobox({
	onChange:function(newValue,oldValue){
		if(newValue=='FKFS-04'){
			$("#F_payTypeRemark").next().show();
		}else {
			$("#F_payTypeRemark").next().hide();
		}
	},
	onSelect:function (record){
		if(record.text=="--请选择--"){
			$("#F_payTypeRemark").next().hide();
		}
	}
});


	function onSelectFcType(rec) {
		if (rec.code == 'HTFL-01') {
			$('.fcType').show();
			$('#f_fCardNo').textbox({required:true});
			$('#f_fBankName').textbox({required:true});
			$('#fgcHt').hide();
			$('#select_cgconf_plan').show();
			$('#select_recieve_plan').show();
			$.parser.parse('#select_cgconf_plan');
			$.parser.parse('#select_recieve_plan');
			$('#F_fPurchName').textbox({required:true});
			$('#F_fcAmount').numberbox({required:true});
			$('#F_fcAmountMax').textbox({required:true});
			$('#F_fContractor').combobox({required:true});
			$('#F_fPayType').combobox({required:true});
			$('#F_fMarginAmount').numberbox({required:true});
			$('#F_fContractor1').textbox({required:false});
			$('#F_fMarginAmount1').numberbox({required:false});
		} else {
			$('.fcType').hide();
			$('#f_fCardNo').textbox({required:false});
			$('#f_fCardNo').textbox('setValue','');
			$('#f_fBankName').textbox({required:false});
			$('#f_fBankName').textbox('setValue','');
			$('#fgcHt').show();
			$('#select_cgconf_plan').hide();
			$('#select_recieve_plan').hide();
			$('#F_fPurchNo').val('');
			$('#F_fPurchName').textbox({required:false});
			$('#F_fPurchName').textbox('setValue','');
			$('#F_fcAmount').numberbox({required:false});
			$('#F_fcAmount').numberbox('setValue','');
			$('#F_fcAmountMax').textbox({required:false});
			$('#F_fcAmountMax').textbox('setValue','');
			$('#F_fPayType').combobox({required:false});
			$('#F_fPayType').combobox({validType:''});
			$('#F_fPayType').combobox('setValue','');
			$('#F_fContractor').combobox({required:false});
			$('#F_fContractor').combobox({validType:''});
			$('#F_fMarginAmount').numberbox({required:false});
			$('#F_fContractor1').textbox({required:true});
			$('#F_fMarginAmount1').numberbox({required:true});
		}
	}
</script>