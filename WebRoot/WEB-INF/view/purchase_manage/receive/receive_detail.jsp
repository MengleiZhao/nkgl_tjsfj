<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;验收单号</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_facpCode"  name="facpCode" readonly="readonly" required="required" data-options="validType:'length[1,30]'" style="width: 555px" value="${bean.facpCode}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目编号</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_fpCode" name="fpCode" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fpCode}"/>
		</td>
		<td class="td4">
		<input type="hidden" id="F_AcpId" name="facpId" value="${bean.facpId}"/>
		<!-- 审批结果 --><input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
   		<!-- 审批意见 --><input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
		<!-- 审批附件 --><input type="hidden" name="spjlFile" id="spjlFile" value=""/>
		<!-- 流程id  --><input type="hidden" id="flowId"  value="${fpId}"/>
		</td>
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fpName" name="fpName" readonly="readonly" required="required"  style="width: 200px;" value="${bean.fpName}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同编号</td>
		<td class="td2">
			<input class="easyui-combobox" type="text" id="F_fcCode" name="fcCode" readonly="readonly" required="required" data-options="prompt:'请选取合同单号',valueField:'fcCode',textField:'fcCode',url:'${base}/Formulation/findAttacByFPurchNoList?fPurchNo=${bean.fpId}',onSelect:onSelectCode,editable:false"  style="width: 200px" value="${bean.fcCode}"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同名称</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fcTitle" name="fcTitle" readonly="readonly" required="required" style="width: 200px;" value="${bean.fcTitle}"/>
		</td>
	</tr>
</table>