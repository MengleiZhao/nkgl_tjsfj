<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellpadding="0" cellspacing="0"  border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同编号</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fcCode" name="fcCode" readonly="readonly"  data-options="validType:'length[1,32]'" style="width: 555px" value="${bean.fcCode}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同名称</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fcTitle" name="fcTitle" readonly="readonly"  data-options="validType:'length[1,50]'" style="width: 555px" value="${bean.fcTitle}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同分类</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fcType" name="fcType" readonly="readonly"  data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=HTFL&selected=${bean.fcType}',method:'POST',valueField:'code',textField:'text',editable:false,
			onSelect:function (rec){
				if(rec.code=='HTFL-01'){
					$('.fcType').show();
					$('#fgcHt').hide();
					$('#select_cgconf_plan').show();
					$('#select_recieve_plan').show();
					$.parser.parse('#select_cgconf_plan');
					$.parser.parse('#select_recieve_plan');
				}else {
					$('.fcType').hide();
					$('#fgcHt').show();
					$('#select_cgconf_plan').hide();
					$('#select_recieve_plan').hide();
				}
			}
			" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同份数</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fcNum" name="fcNum" readonly="readonly" data-options="validType:'length[1,2]',precision:0" style="width: 200px" value="${bean.fcNum}"/>
		</td>
	</tr>
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fPurchName" name="fPurchName" readonly="readonly" data-options="prompt:'单击打开选取采购订单',validType:'length[1,50]'" style="width: 555px" value="${bean.fPurchName}"/>
		</td>
	</tr>
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fContractor" readonly="readonly" name="fContractor" value="${bean.fContractor}" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/fContractorlookupsJson?parentCode=${bean.fPurchNo}&selected=${bean.fContractor}',method:'POST',valueField:'code',textField:'text',
			onSelect:function (rec){
				var fpid = $('#F_fPurchNo').val();
				$('#contract_cgplan_dg_detail1').datagrid('reload',base+'/cgconfplangl/mingxilist?code='+rec.code);
				$('#contract_cgplan_dg_detail1').datagrid({
					onLoadSuccess:function(){
						var fpItemsName = $('#F_fpItemsName').val();
						if(fpItemsName=='PMMC-4' || fpItemsName=='PMMC-5'){
							$('#contract_cgplan_dg_detail1').datagrid('hideColumn', 'fpNum');
							$('#contract_cgplan_dg_detail1').datagrid('hideColumn', 'fmModel');
							$('#contract_cgplan_dg_detail1').datagrid('hideColumn', 'fWhetherImport');
							$('#contract_cgplan_dg_detail1').datagrid('hideColumn', 'fsignPrice');
							$('#contract_cgplan_dg_detail1').datagrid('hideColumn', 'fBrand');
							$('#contract_cgplan_dg_detail1').datagrid('hideColumn', 'fmSpecif');
						}else{
							$('#contract_cgplan_dg_detail1').datagrid('showColumn', 'fpNum');
							$('#contract_cgplan_dg_detail1').datagrid('showColumn', 'fmModel');
							$('#contract_cgplan_dg_detail1').datagrid('showColumn', 'fWhetherImport');
							$('#contract_cgplan_dg_detail1').datagrid('showColumn', 'fsignPrice');
							$('#contract_cgplan_dg_detail1').datagrid('showColumn', 'fBrand');
							$('#contract_cgplan_dg_detail1').datagrid('showColumn', 'fmSpecif');
						}
						var cgplanrows = $('#contract_cgplan_dg_detail1').datagrid('getRows');
						var totalAmount = 0.00;
						for(var i = 0;i < cgplanrows.length;i++){
							totalAmount = parseFloat(totalAmount) + parseFloat(cgplanrows[i].famount);
						}
						$('#totalPricespan').html(totalAmount+'[元]');
						//$('#totalPrice').val(totalAmount);
					}
				});
			}" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同保证金</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fMarginAmount"  readonly="readonly"  data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fMarginAmount}"/>
		</td>
	</tr>
	<tr class="trbody" id="fgcHt" >
		<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fContractor1"  readonly="readonly"   data-options="validateOnCreate:false"  value="${bean.fContractor}"
			 style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同保证金</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fMarginAmount1"  readonly="readonly"  data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fMarginAmount}"/>
		</td>
	</tr>
	<tr class="trbody fcType">
		<td class="td1"><span class="style1">*</span>&nbsp;合同金额</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fcAmount" readonly="readonly" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fcAmount}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;大写金额</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fcAmountMax" name="fcAmountMax" readonly="readonly"   data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fcAmountMax}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同开始日期</td>
		<td class="td2">
			<input class="easyui-datebox" id="F_fContStartTime" name="fContStartTime" readonly="readonly"  style="width: 200px" data-options="validType:'length[1,20]',editable:false" value="${bean.fContStartTime}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同结束日期</td>
		<td class="td2">
			<input class="easyui-datebox" id="F_fContEndTime" name="fContEndTime" readonly="readonly" data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${bean.fContEndTime}"/>
		</td>
	</tr>
	<tr class="trbody ">
		<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fOperator" readonly="readonly"   data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fOperator}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fDeptName" readonly="readonly"   data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${bean.fDeptName}"/>
		</td>
	</tr>
	<tr class="trbody fcType">
		<td class="td1"><span class="style1">*</span>&nbsp;付款方式</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fPayType" name="fPayType.code" readonly="readonly" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=FKFS&selected=${bean.fPayType.code}',method:'POST',valueField:'code',textField:'text',editable:false" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><!-- <span class="style1">*</span>&nbsp;验收次数 --></td>
		<td class="td2" >
			<%-- <input class="easyui-numberbox" id="F_AccNumber" readonly="readonly" name="accNumber" value="${bean.accNumber}" style="width: 200px" /> --%>
		</td>
	</tr>
	<tr style="height: 70px;">
		<td class="td1" valign="top">合同情况说明</td>
		<td colspan="4">
			<textarea class="textbox-text" readonly="readonly" id="F_fRemark" name="fRemark" oninput="textareaNum(this,'textareaNum1')" autocomplete="off"  style="width:555px;height:70px">${bean.fRemark}</textarea>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1">
			<span style="color: red">*</span>&nbsp;合同文本
		</td>
		<td colspan="4" id="tdf">
			<c:forEach items="${formulationAttaList}" var="att">
				<c:if test="${att.serviceType=='htnd' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>
<script type="text/javascript">
/* $('#F_fPayType').combobox({
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
}); */
</script>