<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<form id="CFAddEditForm" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 合同主键 -->		<input type="hidden" id="F_fcId" name="fcId" value="${bean.fcId}"/>
	    		<!-- 申请人id -->		<input type="hidden" id="F_fOperatorId" name="fOperatorId" value="${bean.fOperatorId}"/>
	    		<!-- 申请部门id -->		<input type="hidden" id="F_fDeptId" name="fDeptId" value="${bean.fDeptId}"/>
	    		<!-- 合同状态 -->		<input type="hidden" id="F_fContStauts" name="fContStauts" value="${bean.fContStauts}"/>
	    		<!-- 审批状态 -->		<input type="hidden" id="F_fFlowStauts" name="fFlowStauts" value="${bean.fFlowStauts}"/>
	    		<!-- 签约方信息主键 -->	<input type="hidden" id="F_fSignId" name="fSignId" value="${signInfo.fSignId}"/>
	    		<!-- 采购订单id -->		<input type="hidden" id="F_fPurchNo" name="fPurchNo" value="${bean.fPurchNo}"/>
	    		<!-- 品目名称 -->		<input type="hidden" id="F_fpItemsName" name="fpItemsName" value="${bean.fpItemsName}"/>
	    		<!-- 采购中标金额 -->		<input type="hidden" id="F_bid_Amount" value="${bean.fcAmount}"/>
	    		<!-- 预算指标id -->		<input type="hidden" id="Fc_fBudgetIndexCode" name="fBudgetIndexCode" value="${bean.fBudgetIndexCode}"/>
				<!-- 指标类型 -->		<input type="hidden" id="F_indexType" name="indexType" value="${bean.indexType}"/>
				<!-- 项目支出明细id -->	<input type="hidden" id="F_proDetailId" name="proDetailId" value="${bean.proDetailId}"/>
				<div id="sqsqjbxx" style="overflow:auto;margin-top: 0px;">			
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
						<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
							<%@ include file="../base/contract-formulation-base2.jsp" %>
						</div>	
						<div title="签约方信息" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
							<%@ include file="../base/contract-formulation-sign-base-detail.jsp" %>
						</div>
						<div id="select_cgconf_plan" hidden="hidden">
							<div class="easyui-accordion" >
								<div id="select_cgconf_plan_detail" hidden="hidden" title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
									<%@ include file="../base/cgconf_plan_mingxi_detail.jsp" %>
								</div>
							</div>	
						</div>
						<div id="select_recieve_plan" hidden="hidden">
							<div class="easyui-accordion" >
								<div title="付款计划" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<%@ include file="../base/contract-filing-edit-plan-detail.jsp" %>
								</div>
							</div>	
						</div>
						<div title="审批记录" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
							<jsp:include page="../../check_history.jsp" />												
						</div>
					</div>
				</div>
			</div>
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<%-- <c:if test="${bean.fsealedStatus == '1' }">
					<a href="javascript:void(0)" onclick="contract_seal_detail(${bean.fcId})">
						<img src="${base}/resource-modality/${themenurl}/button/htgz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;&nbsp;
				</c:if> --%>
				<c:if test="${empty contractUpdateStatus}">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				</c:if>
				<c:if test="${!empty contractUpdateStatus}">
				<a href="javascript:void(0)" onclick="closeSecondWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				</c:if>
			</div>
			
		</div>
		<div class="window-right-div" style="width:254px;height: 591px">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
<script type="text/javascript">
$(function(){
	if('${signInfo.fIsOfficialUser}'==0){
		fIsOfficialUserNo();
	}else if('${signInfo.fIsOfficialUser}'==1){
		fIsOfficialUserYes();
	}
});
//校验合同金额
$('#F_fcAmount').numberbox({
	onChange : function(newValue, oldValue){
    	$('#F_fcAmountMax').textbox('setValue', convertCurrency(newValue));
		var bidAmount = $('#F_bid_Amount').val();//中标金额
		if(parseFloat(newValue) > parseFloat(bidAmount)){
			return alert('合同金额不得超过中标金额, 中标金额：'+bidAmount+'元');
		}
	}
});

/* //合同大写金额
$('#F_fcAmount').numberbox({
    onChange:function(newValue,oldValue){
    }
}); */
   
</script>