<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<!-- 基本信息 -->
		<div id="spbxjbxx" style="overflow:auto; margin-top: 0px;">
		
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
			<div title="差旅事项调整" data-options="collapsed:false,collapsible:false"style="overflow:auto;width: 717px">
				<table class="window-table" style="margin-top: 3px" cellspacing="0" cellpadding="0">
					<tr class="trbody">
						<td class="td1" style="width: 80px;"><span class="style1">*</span>行程是否调整</td>
						<td class="td2" colspan="4" >
							<input type="radio" disabled value="1" onclick="radioyes1()" readonly="readonly" name="fupdateStatus" id="box1" <c:if test="${bean.fupdateStatus=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>&nbsp;&nbsp;是
							&nbsp;&nbsp;
							<input type="radio" disabled value="0" onclick="radiono1()" readonly="readonly" name="fupdateStatus" id="box2" <c:if test="${bean.fupdateStatus=='0'}">checked="checked" </c:if> style="vertical-align: middle;"/>&nbsp;&nbsp;否
						</td>
				
						<!-- <td class="td4" style="width: 67px;"></td> -->
					</tr>
					<tr id="radiofupdate" hidden="hidden" class="trbody">
						<td class="td1" style="width: 80px;"><span class="style1">*</span>行程调整说明</td>
						<td colspan="3" class="td2" >
							<textarea name="fupdateReason"  id="fupdateReason" class="textbox-text"
									oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
									style="width:595px;height:70px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:8px; margin-bottom:0px;">${bean.fupdateReason }</textarea>
						</td>
					</tr>
				</table>
			</div>				
		</div>
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
		<div title="基本信息" data-options="collapsed:false,collapsible:false"
			style="overflow:auto; ">
			<table class="window-table" style="margin-top: 3px;width: 695px" cellspacing="0" cellpadding="0">
				<tr class="trbody">
					<td style="width: 70px;"><span class="style1">*</span>摘要</td>
					<td colspan="3" class="td2" >
						<input  style="width: 635px; height: 30px;margin-left: 10px" id="gName" class="easyui-textbox"
						value="${bean.gName}" readonly="readonly"/>
					</td>
				</tr>
				<tr class="trbody">
					<td style="width: 70px;"><span class="style1">*</span>单据编号</td>
					<td colspan="3" class="td2" >
						<input style="width: 635px;height: 30px;margin-left: 10px" readonly="readonly" name="gCode" class="easyui-textbox"
						value="${bean.gCode}" data-options="prompt: '事前申请选择' ,icons: [{iconCls:'icon-sousuo'}],required:true" readonly="readonly"/>
					</td>
				</tr>
				<tr class="trbody">
					<td style="width: 70px;"><span class="style1">*</span>出差类型</td>
					<td class="td2" colspan="3">
					<input class="easyui-combobox" id="travelType" name="travelType" readonly="readonly" style="width: 635px;height: 30px;" data-options="editable:false,url:'${base}/Formulation/lookupsJson?parentCode=CCLX&selected=${travelBean.travelType}',method:'POST',valueField:'code',textField:'text'">
					</td>
			 	</tr>
			 	<tr class="trbody" style="line-height: 65px;">
					<td><span class="style1">*</span>出差事由</td>
					<td colspan="3">
						<textarea name="reason" id="reason" class="easyui-textbox" readonly="readonly" data-options="multiline:true"
						oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
						style="width:635px;height:60px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:15px; margin-bottom:0px;">${travelBean.reason }</textarea>
					</td>
				</tr>
					<tr class="trbody">
									<td class="td1" style="width: 70px;"><span class="style1">*</span> 经办人</td>
									<td class="td2" >
									<input class="easyui-textbox" id="userNames" name="userNames" readonly="readonly" value="${applyBean.userNames}" style="width: 265px;height: 30px;margin-left: 10px " >
									</td>
									<td class="td1" style="width: 70px;"><span class="style1">*</span> 部门名称</td>
									<td class="td2" >
									<input class="easyui-textbox" id="deptName" name="deptName" readonly="readonly" value="${applyBean.deptName}" style="width: 267px;height: 30px;margin-left: 10px " >
									</td>
								</tr>
					</table>
				</div>				
			</div>
		</div>
		
		<c:if test="${applyBean.travelType=='GWCC' }">
		<!-- 出差人员名单 -->
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
			<div title="行程清单" data-options="collapsed:false,collapsible:false"style="overflow:auto;width: 717px">
				<div style="overflow:auto;margin-top: 0px;">
					<jsp:include page="reimburse_travel_itinerary_detail.jsp" />
				</div>
			</div>
		</div>
		<!-- 费用明细 -->
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px">
			<div title="费用明细" data-options="collapsible:false" style="overflow:auto;">
				 <!-- 城市间交通费 -->
				<div style="overflow:auto;margin-top: 20px;">
					<jsp:include page="reimburse_outside_traffic_detail.jsp" />
				</div>
				<div style="overflow:hidden;margin-top: 0px">
					<!-- 城市间交通费发票明细 -->
					<jsp:include page="mingxi_travel_outside_detail.jsp" />
				</div>
				<!-- 市内交通费 -->
				<div style="overflow:auto;margin-top: 20px;">
					<jsp:include page="reimburse_in_city_detail.jsp" />
				</div>
				<!-- 住宿费 -->
				<div style="overflow:auto;margin-top: 20px;">
					<jsp:include page="reimburse_hotelExpense_detail.jsp" />
				</div>
				<div style="overflow:hidden;margin-top: 0px">
					<!-- 市内交通费发票明细 -->
					<jsp:include page="mingxi_travel_hotel_detail.jsp" />
				</div>
				<div style="overflow:auto;margin-top: 20px;">
					<jsp:include page="reimburse_foodAllowance_detail.jsp" />
				</div>
				<div>
					<a style="float: right;margin-right: 30px;">报销金额总计：<span style="color: #D7414E"  id="rapplyTotalAmount"><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
				</div>
			</div>
		</div>
		</c:if>
		
		
		<c:if test="${applyBean.travelType=='GWCX' }">
		<!-- 出差人员名单 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 717px">
					<div title="行程清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px;">
						<jsp:include page="apply_travel_itinerary_trip_detail_reim.jsp" />
					</div>
					</div>
				</div>
				<!-- 费用明细 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px;width: 717px">
					<div title="费用明细" data-options="collapsible:false" style="overflow:auto;">
						<!-- 市内交通费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="apply_in_city_trip_detail_reim.jsp" />
						</div>
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="foodAllowance_trip_detail_reim.jsp" />
						</div>
						<!-- 住宿费 -->
						<div style="overflow:auto;margin-top: 20px;">
							<jsp:include page="hotelExpense_trip_detail_reim.jsp" />
						</div>
							<div style="overflow:hidden;margin-top: 0px">
								<!-- 住宿费发票明细 -->
								<jsp:include page="mingxi_travel_hotel_detail.jsp" />
							</div>
						<div style="margin-top: 20px">
							<a style="float: right;">报销金额总计：<span style="color: #D7414E"  id="applyTotalAmountTripReim"><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
						</div>
					</div>
				</div>
		
		</c:if>
		<!-- 预算信息 -->
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 50px">
		<div title="预算信息" data-options="collapsed:false,collapsible:false"style="overflow:auto;margin-left: 0px;height: 250px">				
			<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 0px;margin-left: 0px;;margin-bottom: 0px;width: 695px;">					
				<tr class="trbody">
					<td style="width: 60px;"><span class="style1">*</span> 预算指标</td>
					<td colspan="3" style="padding-right: 5px;">
						<input class="easyui-textbox"  style="width: 630px;height: 30px;"
						 value="${bean.indexName}" id="F_fBudgetIndexName"
						 readonly="readonly" required="required"/>
					</td>
				</tr>
			</table>	
			<table class="window-table-readonly-zc" cellspacing="0" cellpadding="0" style="margin-left:0px;;margin-bottom: 0px;width: 695px;height: 50px;">
				<tr>
					<td class="window-table-td1"><p style=" color:#0000CD;">申请金额:</p></td>
					<td class="window-table-td2"><p id="applyAmount_span">${applyBean.amount}[元]</p></td>
					<td class="window-table-td1"><p style="color: red;" >报销金额:</p></td>
					<td class="window-table-td2"><p id="p_amount">${bean.amount}[元]</p></td>
				</tr>
				<tr>
					<td class="window-table-td1"><p>归还预算:</p></td>
					<td class="window-table-td2"><p id="ghAmount">[元]</p></td>
				</tr>
				<tr >
					<td class="window-table-td1"><p>是否冲销借款</p></td>
					<td class="window-table-td2">
						<input id="hotelstd_add_sfwj" name="withLoan" value="1" disabled
							type="radio" onclick="selectCxjk(this)" <c:if test="${bean.withLoan==1 }">checked="checked"</c:if>/>是
						<input id="hotelstd_add_sfwj" name="withLoan" value="0" disabled
							type="radio" onclick="selectCxjk(this)" <c:if test="${bean.withLoan!=1 }">checked="checked"</c:if>/>否
					</td>	
				</tr>
				
				<tbody id="jk">
					<tr>
						<td class="window-table-td1"><p>借款单号</p></td>
						<td class="window-table-td2">
							<a href="#" onclick="chooseJkd()">
								<input class="easyui-textbox" id="input_jkdbh" style="width: 250px;height: 30px;" data-options="prompt: '借款单选择' ,icons: [{iconCls:'icon-sousuo'}]"
								value="${bean.loan.lCode}" readonly="readonly" >
								<input id="input_jkdid" hidden="hidden" value="${bean.loan.lId}" name="loan.lId"/>
								<input id="input_jkdamonut" value="${bean.loan.lAmount}" hidden="hidden"  />
							</a>
						</td>
					</tr>
					<tr>
						<%-- <td class="window-table-td1"><p>使用部门:</p></td>
						<td class="window-table-td2"><p id="p_pfDepartName">${bean.pfDepartName}</p></td> --%>
						<td class="window-table-td1"><p>本次冲销金额:</p></td>
						<td class="window-table-td2"><p id="cxAmount">[元]</p></td>
						<td class="window-table-td1"><p>剩余应还:</p></td>
						<td class="window-table-td2"><p id="syAmount">[元]</p></td>
					</tr>
				</tbody>
				</table>			
			</div>
		</div>
		
		
		<!-- 收款人信息 -->
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
			<div title="收款人信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;padding:0px;">
				<input hidden="hidden" value="${payee.pId}" name="pId" />
				<div id="" style="overflow-x:hidden;margin-top: 0px;">
					<jsp:include page="payee-info_detail.jsp" />	
				</div>
				<div id="" style="overflow-x:hidden;margin-top: 0px;">
					<jsp:include page="payee-info-external-detail.jsp" />
				</div>
			</div>
		</div>
		
	<%-- 	<!-- 附件信息 -->
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
			<div title="附件信息" data-options="collapsed:false,collapsible:false"
				style="overflow:auto;">		
				<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
					<tr class="trbody">
							<td style="width: 60px;text-align: left">附件:</td>
							<td colspan="4">
								<c:if test="${!empty attaList1}">
								<c:forEach items="${attaList1}" var="att">
									<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a><br>
								</c:forEach>
							</c:if>
							<c:if test="${empty attaList1}">
								<span style="color:#999999">暂未上传附件</span>
							</c:if>
							</td>
						</tr>						
				</table>			
			</div>
		</div> --%>
		<!-- 附件信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="附件信息" data-options="collapsible:false"
					style="overflow:auto;">		
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;width:707px;">
						<tr>
							<td class="td1" style="width:60px;text-align: left"><span class="style1">*</span>
								出差批示:
							</td>
							<td colspan="3" id="tdf">
								<c:forEach items="${attaList}" var="att">
									<div style="margin-top: 5px;">
									<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
								</c:forEach>
							</td>
						</tr>
						
						
					</table>
					
				</div>
				</div>
		<!-- 审批记录 -->
		<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
			<div title="审批记录" data-options=" collapsible:false" style="overflow:auto;padding:0px;">
				<!-- <div class="window-title"> 审批记录</div> -->
					<jsp:include page="../../../check_history.jsp" />
			</div>
		</div>		
<script type="text/javascript">
var updateradio = 0;
function radiono1(){
	updateradio=1;
	$('#radiofupdate').hide();
}
function radioyes1(){
	updateradio=0;
	$('#radiofupdate').show();
}



//冲销借款
function cx(){
	
	var num1=parseFloat($('#cxAmounts').val());
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	var num6=parseFloat($('#input_jkdamonut').val());
	if(isNaN(num1)&&!isNaN(num2)){
		 $('#skAmount').val('setValue',num2);
	}
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			var num4=num1-num2;
			 $('#cxAmount').html(fomatMoney(num2,2)+" [元]");
			 $('#cxAmounts').val(num2.toFixed(2));
			 $('#syAmount').html(fomatMoney(num4,2)+" [元]");
			 $('#skAmount').numberbox('setValue',0);
		}else{
			$('#cxAmount').html(fomatMoney(num1,2)+" [元]");
			$('#cxAmounts').val(num1.toFixed(2));
			$('#syAmount').html((num6-num2).toFixed(2)+" [元]");
			$('#skAmount').numberbox('setValue',(num2-num1).toFixed(2));
		}
	}
	if(!isNaN(num2)){
		if(num2<num3){
			var num5=num3-num2;
			$('#ghAmount').html(fomatMoney(num5,2)+" [元]");
		}else{
			$('#ghAmount').html(fomatMoney(0,2)+" [元]");
		}
	}
}

function jk(){
	
	var cxjk = $('input[name="withLoan"]:checked').val();
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	var num1=parseFloat($('#cxAmounts').val());
	var num6=parseFloat($('#input_jkdamonut').val());
	if(cxjk==1){
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			var num4=num1-num2;
			 $('#cxAmount').html(fomatMoney(num2,2)+" [元]");
			 $('#cxAmounts').val(num2.toFixed(2));
			 $('#syAmount').html((num6-num2).toFixed(2)+" [元]");
		}else{
			$('#cxAmount').html(fomatMoney(num1,2)+" [元]");
			$('#cxAmounts').val(num1.toFixed(2));
			$('#syAmount').html((num6-num2).toFixed(2)+" [元]");
		}
	}
	}
	if(!isNaN(num2)){
		if(num2<num3){
			var num5=num3-num2;
			$('#ghAmount').html(fomatMoney(num5,2)+" [元]");
		}else{
			$('#ghAmount').html(fomatMoney(0,2)+" [元]");
		}
	}
}
</script>