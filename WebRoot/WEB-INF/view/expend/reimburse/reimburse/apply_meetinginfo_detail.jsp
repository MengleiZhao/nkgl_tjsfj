<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
	<div title="基本信息" data-options="collapsible:false,collapsible:false">
		<table class="window-table" cellspacing="0" cellpadding="0"
			style="margin-top: 3px;width: 693px;padding-right:4px">
			<tr class="trbody">
				<td class="td1"><span class="style1">*</span> 摘要</td>
				<td colspan="3"><input class="easyui-textbox"
					style="width: 590px;height: 30px; " value="${applyBean.gName}"
					name="gName" readonly="readonly" /></td>
			</tr>
			<tr class="trbody">
				<td class="td1"><span class="style1">*</span>申请事由</td>
				<td colspan="3"><textarea name="reason" id="reason"
						class="textbox-text" readonly="readonly"
						oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
						style="width:584px;height:70px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:4px; margin-bottom:0px;">${applyBean.reason }</textarea>
				</td>
			</tr>

			<!-- 会议信息 -->
			<jsp:include page="apply_meeting_detail.jsp" />
		</table>
	</div>
</div>

<!-- 会议日程 -->
<div class="easyui-accordion"
	style="margin-left: 20px;margin-right: 20px;width: 695px">
	<div title="会议日程" data-options="collapsible:false"
		style="overflow:auto">
		<div style="overflow:auto;margin-top: 0px">
			<jsp:include page="apply_meeting_plan.jsp" />
		</div>
	</div>
</div>

<!-- 费用明细 -->
<div class="easyui-accordion"
	style="margin-left: 20px;margin-right: 20px;width: 695px">
	<div title="费用明细" data-options="collapsible:false"
		style="overflow:auto">
		<div style="overflow:auto;margin-top: 0px">
			<!--  会议申请  明细 -->
			<jsp:include page="apply_mingxi-meeting-detail.jsp" />
			<div style="margin-top: 20px">
				<a style="float: right;">申请金额总计：<span style="color: #D7414E"  ><fmt:formatNumber groupingUsed="true" value="${applyBean.amount}" minFractionDigits="2" maxFractionDigits="2"/></span>[元]</a>
			</div>
		</div>
	</div>
</div>
<!-- 预算信息 -->
<div class="easyui-accordion"
	style="margin-left: 20px;margin-right: 20px;margin-top: 0px;width: 695px">
	<div title="预算信息" data-options="collapsible:false"
		style="overflow:auto;">
		<table class="window-table" cellspacing="0" cellpadding="0"
			style="margin-top: 3px;margin-left: 0px;width:692px;">
			<tr class="trbody">
				<td class="td2" ><span
					style="text-align: left;color: red">*</span> 预算指标:</td>
				<td colspan="3"><input class="easyui-textbox"
					readonly="readonly" style="width: 625px;height: 30px;"
					name="indexName" value="${bean.indexName}" 
					data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}]"
					readonly="readonly" required="required" /></td>
			</tr>
		</table>
		<table class="window-table-readonly-zc" cellspacing="0"
			cellpadding="0" style="margin-left: 0px;width: 693px;height: 50px;">
			<tr>
				<td class="window-table-td1"><p>批复金额：</p></td>
				<td class="window-table-td2"><p id="p_pfAmount">${bean.pfAmount}[元]</p></td>

				<td class="window-table-td1"><p>预算年度：</p></td>
				<td class="window-table-td2"><p id="p_pfDate">${bean.pfDate}</p></td>
			</tr>
			<tr>
				<td class="window-table-td1"><p>可用额度：</p></td>
				<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}[元]</p></td>
			</tr>
		</table>
	</div>
</div>

<!-- 附件信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 693px">
	<div title="附件信息" data-options="collapsible:false" style="overflow:auto;">
		<table class="window-table" cellspacing="0" cellpadding="0"
			style="margin-top: 3px;width:690px;">
			<tr>
				<td class="td1" style="width:65px;text-align: left"><span
					class="style1">*</span> 会议请示:</td>
				<td colspan="3" id="tdf">
					<c:forEach items="${attaList}" var="att">
						<c:if test="${att.serviceType=='null'}">
								<div style="">
									<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</table>

	</div>
</div>
<c:if test="${!empty applyBean.meetingSummaryYear2 or !empty applyBean.meetingSummaryYear1}">
	<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 693px">
		<div title="三重一大会议信息" data-options="collapsible:false" style="overflow:auto; ">
			<jsp:include page="check_meeting_infomation_detail.jsp" />
		</div>
	</div>	
</c:if>

<!-- 审批记录 事前申请 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
	<div title="审批记录" data-options=" collapsible:false" style="overflow:auto;">
		<!-- <div class="window-title"> 审批记录</div> -->
			<jsp:include page="../../../check_history_reim_apply.jsp" />
	</div>
</div>