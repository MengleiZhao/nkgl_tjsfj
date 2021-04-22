<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<form id="ApprovalSealUptForm" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
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
				<!-- 合同付款金额合计 -->	<input type="hidden" id="F_fPlanTotalAmount" name="fPlanTotalAmount" value="${bean.fPlanTotalAmount}"/>
				<div class="tab-wrapper" id="contract-seal-edit">
					<ul class="tab-menu">
						<li >变更合同</li>
						<!-- <li class="active" onclick="tabChange()">合同信息</li> -->
					</ul>
					<div class="tab-content">
						<div title="变更表" style="margin-bottom:35px;overflow:auto" data-options="iconCls:'icon-xxlb'" >
							<%@ include file="../../change/change_edit_info.jsp" %>
						</div>
						<%-- <div id="sqsqjbxx" style="margin-bottom:35px;overflow:auto">			
							<div class="easyui-accordion" data-options="multiple:true"  style="width:680px;margin-left: 20px;margin-right: 0px">
								<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
									<%@ include file="../../base/contract-formulation-base2.jsp" %>
								</div>	
								<div title="签约方信息" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<%@ include file="../../base/contract-formulation-sign-base-detail.jsp" %>
								</div>
								<div title="付款计划" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<%@ include file="../../base/contract-filing-edit-plan-detail.jsp" %>
								</div>
								<div id="select_cgconf_plan_detail" hidden="hidden" title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
									<%@ include file="../../base/cgconf_plan_mingxi_detail.jsp" %>
								</div>
								<div title="审批记录" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<%@ include  file="../../../check_history.jsp" %>												
								</div>
							</div>
						</div> --%>
					</div>
				</div>
			</div>
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveSeal();">
					<img src="${base}/resource-modality/${themenurl}/button/htgz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		
		<%-- <div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../../check_system.jsp" />
		</div> --%>
	</div>
</form>
<script type="text/javascript">

flashtab('contract-seal-edit');
$(function(){
	$('#Upt_fUptReason_edit').attr("readonly", "readonly");
	$('#uptUploadBtn').hide();
	$('.deleteFlag').hide();
	$(':radio').attr('disabled', true);

	if('${signInfo.fIsOfficialUser}'==0){
		fIsOfficialUserNo();
	}else if('${signInfo.fIsOfficialUser}'==1){
		fIsOfficialUserYes();
	}
	
});
function fIsOfficialUserYes(){
	var cxjk = $('input[name="fIsOfficialUser"]:checked').val();
	$('.fIsOfficialUser').hide();
}
function fIsOfficialUserNo(){
	var cxjk = $('input[name="fIsOfficialUser"]:checked').val();
	$('.fIsOfficialUser').show();
}

//保存盖章信息
function saveSeal(){
	var infoid = '';
	if(${type}==0){
		infoid = '${bean.fcId}';
	}else {
		infoid = '${Upt.fId_U}';
	}
	if(confirm("确认盖章？")){
		$('#ApprovalSealUptForm').form('submit', {
			onSubmit: function(){ 
				flag = $(this).form('enableValidation').form('validate');
				if(flag){
					$.messager.progress();
				}
				return flag;
			}, 
			url: base + '/sealInfo/saveSeal?id=' + ${fcId}+'&infoid='+infoid+'&type='+${type},
			success:function(data){
				if(flag){
					$.messager.progress('close');
				}
				data = eval("("+data+")");
				if(data.success){
					$.messager.alert('系统提示', data.info, 'info');
					if(${type}==0){
						$("#contractTabSeal").datagrid('reload');
					}else {
						$("#updateOrendingTabSeal").datagrid('reload');
					}
					closeWindow();
				}else{
					$.messager.alert('系统提示', data.info, 'error');
				}
			} 
		});	
		
	}
}
if ($('#uptOpenType').val() == 'Cdetail') {
	$('#change-upt-datagr-div').show();
	$.parser.parse($('#change-upt-datagr-div').parent());
	$('#change-upt-cgconf-div').show();
	$.parser.parse($('#change-upt-cgconf-div').parent());
}

$('#upt_fContName_edit').attr("readonly", "readonly");
$('#Upt_fUptReason_edit').attr("readonly", "readonly");

function tabChange(){
	$('#filing-edit-plan-dg-detail').datagrid('reload');
	$('#contract_cgplan_dg_detail1').datagrid('reload');
}
</script>