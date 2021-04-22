<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<!-- 基本信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
	<table class="window-table" style="margin-top: 10px" cellspacing="0" cellpadding="0">
		<tr class="trbody">
			<td style="width: 90px;"><span class="style1">*</span> 单据编号</td>
			<td class="td2" colspan="4">
				<input style="width: 600px; height: 30px;margin-left: 10px" name="gCode" class="easyui-textbox"
				value="${bean.gCode}" data-options="prompt: '事前申请选择' ,required:true" readonly="readonly"/>
			</td>
		</tr>
		<tr class="trbody">
			<td style="width: 90px;"><span class="style1">*</span> 摘要</td>
			<td class="td2" colspan="4">
				<input style="width: 600px;height: 30px;" name="gName" class="easyui-textbox"
				value="${applyBean.gName}" readonly="readonly"/>
			</td>
		</tr>
		<tr class="trbody" style="margin-top: 10px;">
			<td style="width: 90px;" valign="top"><p style="margin-top: 10px"><span class="style1">*</span> 申请事由</p></td>
			<td class="td2" colspan="4">
				<input style="width: 600px;height: 60px;" name=""  class="easyui-textbox" data-options="multiline:true" readonly="readonly"
				value="${applyBean.reason}" required="required" />
		</tr>
		<tr class="trbody">
			<td style="width: 90px;"><span class="style1">*</span> 申请事项类型</td>
			<td class="td2" >
				<input class="easyui-combobox" id="commonType" readonly="readonly" value="${applyBean.commonType}" style="width: 200px;height: 30px;"
				 data-options="
				 panelHeight:'auto',
				 url:'${base}/Formulation/lookupsJson?parentCode=SPSXLX&selected=${applyBean.commonType}',
				 method:'POST',
				 valueField:'code',
				 textField:'text',
				 editable:false,
				 validType:'selectValid'
					">
			</td>
			<td style="width:110px"></td>
			<td class="td1" style="width: 60px;"></td>
			<td class="td2" >
			</td>
		</tr>
		<tr class="trbody">
			<td style="width: 90px;"><span class="style1">*</span> 经办人</td>
			<td class="td2">
				<input class="easyui-textbox" value="${bean.userName}" style="width: 200px; height: 30px;" readonly="readonly" required="required" data-options="validType:'length[1,50]'"/>
			</td>
			<td style="width:110px"></td>
			<td style="width: 60px;">部门名称</td>
			<td class="td2">
				<input class="easyui-textbox" value="${bean.deptName}" style="width: 200px; height: 30px;" required="required" data-options="validType:'length[1,50]'" readonly="readonly"/>
			</td>
		</tr>
	</table>
	</div>				
</div>
<!-- 费用明细 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px;width: 695px">
	<div title="费用明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
		<div style="overflow:auto;">
			<!--  通用事项申请明细 -->
			<jsp:include page="apply_comm_mingxi_detail.jsp" />
		</div>
	</div>
</div>
<!-- 预算信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 50px;width: 695px">
	<div title="预算信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-left: 0px;height: 150px">				
		<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;margin-left: 0px;width:693px;">
			<tr class="trbody">
				<td style="width: 61px;text-align: right;"><span style="text-align: left;color: red">*</span> 预算指标:</td>
				<td colspan="3" >
					<input class="easyui-textbox" style="width: 630px;height: 30px;"
					name="indexName" value="${bean.indexName}" id="F_fBudgetIndexName"
					data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}]" readonly="readonly" required="required"/>
				</td>
			</tr>
		</table>	
		<table class="window-table-readonly-zc" cellspacing="0" cellpadding="0" style="margin-left: 0px;width:693px;height: 50px;">
			<tr>
				<td class="window-table-td1"><p>批复金额：</p></td>
				<td class="window-table-td2"><p id="p_pfAmount">${bean.pfAmount}元</p></td>
				
				<td class="window-table-td1"><p>预算年度：</p></td>
				<td class="window-table-td2"><p id="p_pfDate">${bean.pfDate}</p></td>
			</tr>
			<tr>
				<td class="window-table-td1"><p>可用额度：</p></td>
				<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}</p></td>
			</tr>
		</table>				
	</div>
</div>
<!-- 附件信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
	<div title="附件信息" data-options="collapsed:false,collapsible:false" style="overflow:auto; ">		
		<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
			<tr class="trbody">
				<td style="width:60px;">附件</td>
				<td colspan="4">
					<c:if test="${!empty attaList}">
					<c:forEach items="${attaList}" var="att">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a><br>
					</c:forEach>
				</c:if>
				<c:if test="${empty attaList}">
					<span style="color:#999999">暂未上传附件</span>
				</c:if>
				</td>
			</tr>
		</table>			
	</div>
</div>
<!-- 审批记录 事前申请 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
	<div title="审批记录" data-options=" collapsible:false" style="overflow:auto;">
		<!-- <div class="window-title"> 审批记录</div> -->
			<jsp:include page="../check/check_history_apply.jsp" />
	</div>
</div>