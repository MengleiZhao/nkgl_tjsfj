<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
	    		<div class="tab-wrapper" id="contract-change-detail">
	    			<ul class="tab-menu">
						<li class="active">变更合同</li>
						<li onclick="tabChange()">合同信息</li>
					</ul>
					
					<div class="tab-content">
						<input type="hidden" id="receivPlanIndex" value="${receivPlanIndex}"/>
						<div title="变更表" style="margin-bottom:35px;overflow:auto" data-options="iconCls:'icon-xxlb'" >
							<%@ include file="../change/change_edit_info.jsp" %>
						</div>
						<div title="合同信息" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<div class="easyui-accordion" style="width: 702px;margin-left: 20px;margin-right: 20px">
								<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
									<%@ include file="../base/contract-formulation-base2.jsp" %>
								</div>	
								<div title="签约方信息" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<%@ include file="../base/contract-formulation-sign-base-detail.jsp" %>
								</div>
							</div>		
								<div id="select_recieve_plan" hidden="hidden">
								<div class="easyui-accordion"  style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="付款计划" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
										<%@ include file="../base/contract-filing-edit-plan-detail.jsp" %>
									</div>
								</div>	
								</div>
								<div id="select_cgconf_plan" hidden="hidden">
								<div class="easyui-accordion" style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<%@ include file="../base/cgconf_plan_mingxi_detail.jsp" %>
									</div>
								</div>	
								</div>
								<%-- <div title="审批记录" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<jsp:include page="check_history.jsp" />												
								</div> --%>
								<div id="check_history1" >
								<div class="easyui-accordion" style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<jsp:include page="check_history.jsp" />
									</div>
								</div>	
								</div>
							</div>
						</div>
				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<c:if test="${empty contractUpdateStatus}">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				</c:if>
				&nbsp;&nbsp;
				<c:if test="${!empty contractUpdateStatus}">
				<a href="javascript:void(0)" onclick="closeSecondWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				</c:if>
				&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=合同管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="window-right-div" style="width:254px;height: 591px">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
<script type="text/javascript">
if ($('#uptOpenType').val() == 'Cdetail') {
	$('#change-upt-datagr-div').show();
	$.parser.parse($('#change-upt-datagr-div').parent());
	$('#change-upt-cgconf-div').show();
	$.parser.parse($('#change-upt-cgconf-div').parent());
}
flashtab('contract-change-detail');

$('#Upt_fUptReason_edit').attr("readonly", "readonly");
$('#uptUploadBtn').hide();
$('.deleteFlag').hide();
$(':radio').attr('disabled', true);

function tabChange(){
	$('#filing-edit-plan-dg-detail').datagrid('reload');
	$('#contract_cgplan_dg_detail1').datagrid('reload');
	$.parser.parse('#check_history1');
	$('#check-history-dg-onchange').datagrid('reload');
	$('#check-history-dg-onchange').datagrid('reload');
}
</script>
</body>