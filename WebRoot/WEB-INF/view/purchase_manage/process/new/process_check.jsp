<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;中标商名称</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fbiddingName" name="fbiddingName" readonly="readonly" required="required" style="width:555px;" data-options="" value="${brBean.fbiddingName}"/>
		</td>
	</tr>										
	
	<tr>
		<td class="td1"><span class="style1">*</span>&nbsp;中标金额</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fbidAmount" name="fbidAmount" readonly="readonly" required="required" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${brBean.fbidAmount}"/>
		</td>
		<td class="td4">
			<!-- 隐藏域 -->
			<!-- 采购申请主键id -->		<input type="hidden" id="F_PId" name="fpId" value="${bean.fpId}"/>
			<!-- 采购过程登记状态  -->   	<input type="hidden" id="F_BidStauts" name="fbidStauts" value="${bean.fbidStauts}"/>
			
			<!-- 采购过程登记主键id -->	<input type="hidden" id="F_BId" name="fbId" value="${brBean.fbId}"/>
			<!-- 采购过程登记编码 -->		<input type="hidden" id="F_BiddingCode" name="fbiddingCode" value="${brBean.fbiddingCode}"/>
			<!-- 登记员id -->			<input type="hidden" id="F_RegUserId" name="fregUserId" value="${brBean.fregUserId}"/>
			<!-- 登记员名称 -->			<input type="hidden" id="F_RegUserNAME" name="fregUserNAME" value="${brBean.fregUserNAME}"/>
			<!-- 登记部门id -->			<input type="hidden" id="F_RegDeptId" name="fregDeptId" value="${brBean.fregDeptId}"/>
			<!-- 登记部门名称 -->			<input type="hidden" id="F_RegDept" name="fregDept" value="${brBean.fregDept}"/>
			<!-- 数据状态 -->				<input type="hidden" id="F_Stauts" name="fstatus" value="${brBean.fstatus}"/>
			
			<!-- 审批状态 -->				<input type="hidden" id="F_CheckStauts" name="fCheckStauts" value="${brBean.fCheckStauts}"/>
			<!-- 下节点审批人id -->		<input type="hidden" name="fuserId" value="${brBean.fuserId}"/>
			<!-- 下节点审批人姓名 -->		<input type="hidden" name="userName2" value="${brBean.userName2}"/>
			<!-- 下节点编码 -->			<input type="hidden" name="nCode" value="${brBean.nCode}"/>
			
			<!-- 审批结果 -->				<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
			<!-- 审批意见 -->				<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
			<!-- 审批附件 -->				<input type="hidden" name="spjlFile" id="spjlFile" value=""/>
		</td>
		<td class="td1"><span class="style1">*</span>&nbsp;法人代表</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_flegal" name="flegal" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${brBean.flegal}"/>
		</td>
	</tr>
	
	<tr>
		<td class="td1"><span class="style1">*</span>&nbsp;联系人</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_flinkman" name="flinkman" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${brBean.flinkman}"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;联系人电话</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_fphone" name="fphone" readonly="readonly" required="required" data-options="validType:'tel'" style="width: 200px" value="${brBean.fphone}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1">&nbsp;&nbsp;办公地址</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_faddress" name="faddress" readonly="readonly" data-options="validType:'length[1,200]'" style="width:555px;" value="${brBean.faddress}"/>
		</td>
	</tr>
	
	<tr style="height: 78px;">
		<td class="td1" valign="top">&nbsp;&nbsp;备注</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_fremark" name="fremark" readonly="readonly" data-options="validType:'length[1,200]',multiline:true" style="width:555px;height:70px;" value="${brBean.fremark}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1">&nbsp;&nbsp;附件</td>
		<td colspan="4">
			<c:if test="${!empty brAttac}">
				<c:forEach items="${brAttac}" var="att">
					<c:if test="${att.serviceType=='cgzbdj' }">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${empty brAttac}">
				<span style="color:#999999">暂未上传附件</span>
			</c:if>
		</td>
	</tr>
</table>