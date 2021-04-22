<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

		<!-- 基本信息 -->
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
		<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
			<table class="window-table" style="margin-top: 3px;width: 693px" cellspacing="0" cellpadding="0">
				<tr class="trbody">
					<td style="width: 70px;"><span class="style1">*</span>摘要</td>
					<td colspan="3" class="td2" >
						<input style="width: 635px; height: 30px;margin-left: 10px" readonly="readonly" class="easyui-textbox"
						value="${applyBean.gName}" />
					</td>
				</tr>
				<tr class="trbody">
					<td style="width: 70px;"><span class="style1">*</span>单据编号</td>
					<td colspan="3" class="td2" >
						<input style="width: 635px;height: 30px;" readonly="readonly" class="easyui-textbox"
						value="${applyBean.gCode}" data-options="prompt: '事前申请选择' ,icons: [{iconCls:'icon-sousuo'}],required:true" readonly="readonly"/>
					</td>
				</tr>
				<tr class="trbody">
					<td style="width: 70px;"><span class="style1">*</span>出差类型</td>
					<td class="td2" colspan="3">
					<input class="easyui-combobox" readonly="readonly" style="width: 635px;height: 30px;" data-options="editable:false,url:'${base}/Formulation/lookupsJson?parentCode=CCLX&selected=${applyBean.travelType }',method:'POST',valueField:'code',textField:'text'">
					</td>
				</tr>
				<tr class="trbody" >
					<td style="width: 70px;"><span class="style1">*</span>出差事由</td>
					<td colspan="3" class="td2" >
						<textarea readonly="readonly" name="reason"  readonly="readonly" class="textbox-text"
								oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
								style="width:630px;height:60px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:7px; margin-bottom:0px;">${applyBean.reason }</textarea>
						</td>
					</tr>
					<tr class="trbody">
									<td class="td1" style="width: 70px;"><span class="style1">*</span> 经办人</td>
									<td class="td2" >
									<input class="easyui-textbox" readonly="readonly" value="${applyBean.userNames}" style="width: 265px;height: 30px;margin-left: 10px " >
									</td>
									<td class="td1" style="width: 70px;"><span class="style1">*</span> 部门名称</td>
									<td class="td2" >
									<input class="easyui-textbox" readonly="readonly" value="${applyBean.deptName}" style="width: 267px;height: 30px;margin-left: 10px " >
									</td>
								</tr>
					</table>
				</div>				
			</div>

			<c:if test="${applyBean.travelType=='GWCC' }">
			<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
				<div title="行程清单" data-options="collapsed:false,collapsible:false"style="overflow:auto;width: 695px">
					<div style="overflow:auto;margin-top: 0px;">
						<jsp:include page="apply_travel_itinerary_detail.jsp" />
					</div>
				</div>
			</div>
			<!-- 费用明细 -->
			<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px;width: 695px">
				<div title="费用明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
					 <!-- 城市间交通费 -->
					<div style="overflow:auto;margin-top: 20px;">
						<jsp:include page="apply_outside_traffic_detail.jsp" />
					</div>
					<!-- 市内交通费 -->
					<div style="overflow:auto;margin-top: 20px;">
						<jsp:include page="apply_in_city_detail.jsp" />
					</div>
					<!-- 住宿费 -->
					<div style="overflow:auto;margin-top: 20px;">
						<jsp:include page="hotelExpense_detail.jsp" />
					</div>
					<div style="overflow:auto;margin-top: 20px;">
						<jsp:include page="foodAllowance_detail.jsp" />
					</div>
					<div>
						<a style="float: right;margin-right: 30px;">申请金额总计：<span style="color: #D7414E"  ><fmt:formatNumber groupingUsed="true" value="${applyBean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
					</div>
				</div>
			</div>
			 </c:if>

			<c:if test="${applyBean.travelType=='GWCX' }">
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 717px">
					<div title="行程清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px;">
						<jsp:include page="apply_travel_itinerary_trip_detail.jsp" />
					</div>
					</div>
				</div>
				<!-- 费用明细 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px;width: 717px">
					<div title="费用明细" data-options="collapsible:false" style="overflow:auto;">
						<!-- 市内交通费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="apply_in_city_trip_detail.jsp" />
						</div>
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="foodAllowance_trip_detail.jsp" />
						</div>
						<!-- 住宿费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="hotelExpense_trip_detail.jsp" />
						</div>
						<div style="margin-top: 20px">
							<a style="float: right;">申请金额总计：<span style="color: #D7414E" ><fmt:formatNumber groupingUsed="true" value="${applyBean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
						</div>
					</div>
				</div>
				</c:if>

				<!-- 预算信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 50px;width: 695px">
					<div title="预算信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-left: 0px;height: 250px">				
						<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;margin-left: 0px;width:693px;">
							<tr class="trbody">
								<td style="width: 60px;text-align: right;"><span style="text-align: left;color: red">*</span> 预算指标:</td>
								<td colspan="3" >
									<a onclick="openIndex()" href="#">
									<input class="easyui-textbox" style="width: 630px;height: 30px;"
									name="indexName" value="${bean.indexName}" id="F_fBudgetIndexName"
									data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}]" readonly="readonly" required="required"/>
									</a>
								</td>
							</tr>
						</table>	
						<table class="window-table-readonly-zc" cellspacing="0" cellpadding="0" style="margin-left: 0px;width:693px;height: 50px;">
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
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
					<div title="附件信息" data-options="collapsed:false,collapsible:false" style="overflow:auto; ">		
						<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
							<tr class="trbody">
									<td style="width: 60px">附件</td>
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
				<!-- 审批记录 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
					<div title="审批记录" data-options=" collapsible:false" style="overflow:auto;">
						<!-- <div class="window-title"> 审批记录</div> -->
							<jsp:include page="../../../check_history_reim_apply.jsp" />
					</div>
				</div>	
<script type="text/javascript">
</script>