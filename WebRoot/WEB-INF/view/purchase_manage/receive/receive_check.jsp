<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;验收人</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_facpUsername" name="facpUsername" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${acBean.facpUsername}"/>
		</td>
		<td class="td4">
			<!--采购的主键id -->		<input type="hidden" id="F_PId" name="fpId" value="${bean.fpId}"/>
			<!-- 采购验收状态 -->		<input type="hidden" id="F_IsReceive" name="fIsReceive" value="${bean.fIsReceive}">
												
			<!-- 验收主键id -->		<input type="hidden" id="F_AcpId" name="facpId" value="${acBean.facpId}"/>
			<!-- 验收编码 -->			<input type="hidden" id="F_AcpCode" name="facpCode" value="${acBean.facpCode}"/>
			<!-- 验收员id -->		<input type="hidden" id="F_AcpUserId" name="facpUserId" value="${acBean.facpUserId}"/>
			<!-- 验收部门id -->		<input type="hidden" id="F_DeptId" name="fDepartId" value="${acBean.fDepartId}"/>
			<!-- 验收部门名称 -->		<input type="hidden" id="F_DeptName" name="fDepartName" value="${acBean.fDepartName}"/>
			<!-- 删除状态 -->			<input type="hidden" id="F_Stauts" name="fStauts" value="${acBean.fStauts}"/>
													
			<!-- 审批状态 -->			<input type="hidden" id="F_CheckStauts" name="fCheckStauts" value="${acBean.fCheckStauts}"/>
			<!-- 下节点审批人id -->	<input type="hidden" name="fuserId" value="${acBean.fuserId}"/>
			<!-- 下节点审批人姓名 -->	<input type="hidden" name="userName2" value="${acBean.userName2}"/>
			<!-- 下节点编码 -->		<input type="hidden" name="nCode" value="${acBean.nCode}"/>
												
			<!-- 审批结果 -->			<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
			<!-- 审批意见 -->			<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
			<!-- 审批附件 -->			<input type="hidden" name="spjlFile" id="spjlFile" value=""/>
		</td>
		<td class="td1"><span class="style1">*</span>&nbsp;验收时间</td>
		<td class="td2">
			<input class="easyui-datebox" class="dfinput" id="F_facpTime" name="facpTime" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${acBean.facpTime}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;验收地点</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_facpAddr" name="facpAddr" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 555px" value="${acBean.facpAddr}"/>
		</td>
	</tr>
										
	<tr style="height: 80px;">
		<td class="td1" valign="top"><p style="margin-top: 8px"><span class="style1">*</span>&nbsp;验收意见</p></td>
		<td class="td2" colspan="4">
			<input class="easyui-textbox" type="text" id="F_fmatchRemark" readonly="readonly" required="required" name="fmatchRemark" data-options="validType:'length[1,50]',multiline:true"   style="width:555px;height:70px;" value="${acBean.fmatchRemark}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1">
			&nbsp;&nbsp;附件
			<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'cgys','cgys01')" hidden="hidden">
			<input type="text" id="files" name="files" hidden="hidden">
		</td>
		<td colspan="4" id="tdf">
			<c:if test="${!empty acAttac}">
				<c:forEach items="${acAttac}" var="att">
					<c:if test="${att.serviceType=='cgys' }">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${empty acAttac}">
				<span style="color:#999999">暂未上传附件</span>
			</c:if>
		</td>
	</tr>
</table>