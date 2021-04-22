<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>采购项目编号</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fpCode" readonly="readonly" name="fpCode"  style="width:200px;" value="${bean.fpCode}"/>
		</td>
		<td class="td4">
			<!-- 采购过程登记主键id -->		<input type="hidden" id="F_RId" name="frId" value="${brBean.frId}"/>
			<!-- 审批状态 -->				<input type="hidden" id="F_CheckStauts" name="fCheckStauts" value="${brBean.fCheckStauts}"/>
			<!-- 下节点审批人id -->		<input type="hidden" name="fuserId" value="${brBean.fuserId}"/>
			<!-- 下节点审批人姓名 -->		<input type="hidden" name="userName2" value="${brBean.userName2}"/>
			<!-- 下节点编码 -->			<input type="hidden" name="nCode" value="${brBean.nCode}"/>
			<!--当前申报人id  -->			<input type="hidden" name="fUser" value="${bean.fUser}" />
			<!-- 审批结果 -->				<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
    		<!-- 审批意见 -->				<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
			<!-- 审批附件 -->				<input type="hidden" name="spjlFile" id="spjlFile" value=""/>
			<!-- 流程id  -->				<input type="hidden" id="flowId"  value="${fpId}"/>
			<!-- 下一级审批节点  -->		<input type="hidden" id="nextKey"  value="${brBean.nCode}"/>
		</td>
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
		<td class="td2">
			<input id="" class="easyui-textbox" type="text" required="required" readonly="readonly" name="fpName" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}"/>
		</td>
	</tr>

	<%-- <tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;品目名称</td>
		<td class="td2" colspan="4">
			<input class="easyui-combobox" id="" name="fpItemsName" readonly="readonly" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PMMC&selected=${bean.fpItemsName}',method:'get',valueField:'code',textField:'text',editable:false"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>党组会会议号</td>
		<td class="td2">
			<input class="easyui-textbox"  name="fDZHCode" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fDZHCode}"/>
		</td>
	</tr> --%>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;采购类型</td>
		<td class="td2">
			<input class="easyui-textbox" id="" name="fpMethod" readonly="readonly" style="width: 200px;" value="${bean.fpMethod}" data-options="url:'${base}/lookup/lookupsJson?parentCode=PURCHASE_METHOD&selected=${bean.fpMethod}',method:'get',valueField:'code',textField:'text',editable:false"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;采购方式</td>
		<td class="td2">
			<input class="easyui-textbox" id="" name="fpPype" readonly="readonly" style="width: 200px;" value="${bean.fpPype}" data-options="url:'${base}/lookup/lookupsJson?parentCode=${bean.fpMethod}&selected=${bean.fpPype}',method:'get',valueField:'code',textField:'text',editable:false"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="" readonly="readonly" required="required" name="fDeptName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fDeptName}"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
		<td class="td2">
			<input id="" class="easyui-textbox" type="text" readonly="readonly" required="required" name="fUserName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fUserName}"/>
		</td>
	</tr>
</table>