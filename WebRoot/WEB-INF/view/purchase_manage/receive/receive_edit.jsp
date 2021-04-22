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
		<!--采购的主键id -->		<input type="hidden" id="F_PId" name="fpId" value="${bean.fpId}"/>
			<!-- 采购验收状态 -->		<input type="hidden" id="F_fMatchStauts" name="fMatchStauts" value="${bean.fMatchStauts}">
			<!-- 采购金额 -->		<input type="hidden" id="F_Amount" name="amount" value="${bean.amount}">
												
			<!-- 验收主键id -->		<input type="hidden" id="F_AcpId" name="facpId" value="${bean.facpId}"/>
			<!-- 验收品目名称 -->		<input type="hidden" id="F_FpItemsName" name="fpItemsName" value="${bean.fpItemsName}"/>
			<!-- 合同主键id -->		<input type="hidden" id="F_FcId" name="fcId" value="${bean.fcId}"/>
			<!-- 验收员id -->		<input type="hidden" id="F_AcpUserId" name="facpUserId" value="${bean.facpUserId}"/>
			<!-- 验收部门id -->		<input type="hidden" id="F_DeptId" name="fDepartId" value="${bean.fDepartId}"/>
			<!-- 验收部门名称 -->		<input type="hidden" id="F_DeptName" name="fDepartName" value="${bean.fDepartName}"/>
			<!-- 删除状态 -->			<input type="hidden" id="F_Stauts" name="fStauts" value="${bean.fStauts}"/>
			<!-- 审批状态 -->			<input type="hidden" id="F_CheckStauts" name="fCheckStauts" value="${bean.fCheckStauts}"/>
			<!-- 下节点审批人id -->	<input type="hidden" name="fuserId" value="${bean.fuserId}"/>
			<!-- 下节点审批人姓名 -->	<input type="hidden" name="userName2" value="${bean.userName2}"/>
			<!-- 下节点编码 -->		<input type="hidden" name="nCode" value="${bean.nCode}"/>
		</td>
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fpName" name="fpName" readonly="readonly" required="required"  style="width: 200px;" value="${bean.fpName}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同编号</td>
		<td class="td2">
			<input class="easyui-combobox " id="F_fcCode" name="fcCode" required="required" data-options="prompt:'请选取合同单号',valueField:'fcCode',textField:'fcCode',url:'${base}/Formulation/findAttacByFPurchNoList?fPurchNo=${bean.fpId}',onSelect:onSelectCode,editable:false"  style="width: 200px" value="${bean.fcCode}"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同名称</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fcTitle" name="fcTitle" readonly="readonly" required="required" style="width: 200px;" value="${bean.fcTitle}"/>
		</td>
	</tr>
</table>