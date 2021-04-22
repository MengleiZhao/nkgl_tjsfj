<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<form id="CFAddEditForm" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 合同主键 -->		<input type="hidden" id="F_fcId" name="fcId" value="${bean.fcId}"/>
	    		<!-- 申请人id -->		<input type="hidden" id="F_fOperatorId" name="fOperatorId" value="${bean.fOperatorId}"/>
	    		<!-- 申请部门id -->	<input type="hidden" id="F_fDeptId" name="fDeptId" value="${bean.fDeptId}"/>
	    		<!-- 合同状态 -->		<input type="hidden" id="F_fContStauts" name="fContStauts" value="${bean.fContStauts}"/>
	    		<!-- 审批状态 -->		<input type="hidden" id="F_fFlowStauts" name="fFlowStauts" value="${bean.fFlowStauts}"/>
	    		<!-- 签约方信息主键 -->	<input type="hidden" id="F_fSignId" name="fSignId" value="${signInfo.fSignId}"/>
	    		<!-- 采购订单id -->	<input type="hidden" id="F_fPurchNo" name="fPurchNo" value="${bean.fPurchNo}"/>
	    		<!-- 品目名称 -->		<input type="hidden" id="F_fpItemsName" name="fpItemsName" value="${bean.fpItemsName}"/>
	    		<!-- 采购中标金额 -->	<input type="hidden" id="F_bid_Amount" value="${bean.fcAmount}"/>
	    		<!-- 预算指标id -->	<input type="hidden" id="Fc_fBudgetIndexCode" name="fBudgetIndexCode" value="${bean.fBudgetIndexCode}"/>
				<!-- 指标类型 -->		<input type="hidden" id="F_indexType" name="indexType" value="${bean.indexType}"/>
				<!-- 项目支出明细id --><input type="hidden" id="F_proDetailId" name="proDetailId" value="${bean.proDetailId}"/>
				<!-- 合同付款金额合计 --><input type="hidden" id="F_fPlanTotalAmount" name="fPlanTotalAmount" value="${bean.fPlanTotalAmount}"/>
				<!-- 签约方名称 --><input type="hidden" id="fContractor" name="fContractor" value="${bean.fContractor}"/>
				<!-- 合同保证金 --><input type="hidden" id="fMarginAmount" name="fMarginAmount" value="${bean.fMarginAmount}"/>
				<div id="sqsqjbxx" style="overflow:auto;margin-top: 0px;">			
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
						<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
							<%@ include file="../base/contract-formulation-base.jsp" %>
						</div>	
						<div title="签约方信息" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
							<%@ include file="../base/contract-formulation-sign-base.jsp" %>
						</div>
						<div id="select_cgconf_plan" hidden="hidden">
						<div class="easyui-accordion" >
							<div  title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
								<%@ include file="../base/select_cgconf_plan_mingxi_detail.jsp" %>
							</div>
						</div>	
						</div>
						<div id="select_recieve_plan" hidden="hidden">
						<div class="easyui-accordion" >
							<div  title="付款计划" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
								<%@ include file="../base/contract-filing-edit-plan.jsp" %>
							</div>
						</div>	
						</div>
						<c:if test="${openType != 'add'}">
							<div title="审批记录" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
								<jsp:include page="../../check_history.jsp" />												
							</div>
						</c:if>			
					</div>
				</div>
			</div>
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<a href="javascript:void(0)" onclick="CFFormAdd(0);">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="CFFormAdd(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
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
		var bidAmount = $('#F_bid_Amount').val();//中标金额
		if(parseFloat(newValue) > parseFloat(bidAmount)){
			return alert('合同金额不得超过中标金额, 中标金额：'+bidAmount+'元');
		}
	}
});

//合同大写金额
$('#F_fcAmount').numberbox({
    onChange:function(newValue,oldValue){
    	$('#F_fcAmountMax').textbox('setValue', convertCurrency(newValue));
    }
});
   
//送审
function CFFormAdd(stauts) {
	//合同状态
	var fContStauts = $('#F_fContStauts').val(stauts);
	//审批状态
	var fFlowStauts = $('#F_fFlowStauts').val(stauts);
	var fcType = $('#F_fcType').combobox('getValue');
	//签约方
	//合同保证金
	if(fcType=='HTFL-01'){
		$('#fContractor').val($('#F_fContractor').textbox('getValue'));
		$('#fMarginAmount').val($('#F_fMarginAmount').numberbox('getValue'));
	}else{
		$('#fContractor').val($('#F_fContractor1').textbox('getValue'));
		$('#fMarginAmount').val($('#F_fMarginAmount1').numberbox('getValue'));
	}
	//附件
	var s = "";
	$(".fileUrl").each(function() {
		s = s + $(this).attr("id") + ",";
	});
	$("#files").val(s);
	if(s==''||s==null||s==undefined){
		alert('请上传合同文本');
		return;
	}
	var s1 = "";
	$(".sqwtsfileUrl").each(function() {
		s1 = s1 + $(this).attr("id") + ",";
	});
	$("#filessqwts").val(s1);
	var frdb = $('input[name="fIsOfficialUser"]:checked').val();
	var fLegalUser = $('#f_fLegalUser').textbox('getValue');
	if(frdb==0&&(s1==''||s1==null)){
		alert('请上传授权委托书');
		return;
	}
	if(frdb==0&&fLegalUser==''){
		alert('请填写授权代表人！');
		return;
	}
	
	//校验合同金额
	var fcAmount = $('#F_fcAmount').val();//合同金额
	var fAvailableAmount = $('#F_fAvailableAmount').val(); //剩余金额
	if ((parseFloat(fcAmount) / 10000) > parseFloat(fAvailableAmount)) {
		alert('合同金额不能大于指标剩余金额！');
		return;
	}

	
	if(fcType=='HTFL-01'){
		//校验付款计划
		var plan = getPlan();
		var cgConfplan = getcgConfPlan();
		var fpItemsName = $("#F_fpItemsName").val();
		var rows = $('#filing-edit-plan-dg').datagrid('getRows');
		var fReceProof = false;
		 for (var i = 0; i < rows.length; i++) {
			 if(fpItemsName=='PMMC-4' || fpItemsName=='PMMC-5'){
				 if(rows[i].fReceProof=='FKPJ-1'){
					 fReceProof = true; 
				 }
			 }else{
				 if(fpItemsName=='PMMC-1' || fpItemsName=='PMMC-2'){
					 if(rows[i].fReceProof=='FKPJ-2' || rows[i].fReceProof=='FKPJ-3' || rows[i].fReceProof=='FKPJ-4'){
						 fReceProof = true; 
					 }
				 }
				 if(fpItemsName=='PMMC-3'){
					 if(rows[i].fReceProof=='FKPJ-1'){
						 fReceProof = true; 
					 }
				 }
			 }
		}
		if(rows!=''){
			 if(fpItemsName=='PMMC-4' || fpItemsName=='PMMC-5'){
				 if(!fReceProof){
					 alert('付款依据应包含验收单!');
					 return false;
				 }
			 }else{
				 if(fpItemsName=='PMMC-1' || fpItemsName=='PMMC-2'){
					 if(!fReceProof){
							alert('付款依据应包含入账单或入库单');
							return false; 
						}
				 }else{
					 if(fpItemsName=='PMMC-3'){
						 if(!fReceProof){
								alert('付款依据应包含验收单');
								return false; 
							}
					 }
				 }
				
			 }
		}
		var totalAmount = $('#F_fPlanTotalAmount').val();//付款计划总金额
		if (('' == plan || null == plan || '[]' == plan)) {
			alert('请填写合同付款计划！');
			return;
		}
		if (parseFloat(totalAmount) != parseFloat(fcAmount)) {
			alert('付款计划总金额应等于合同金额！');
			return;
		}
	}
	
	$('#CFAddEditForm').form('submit', {
		onSubmit : function(param) {
			if(fcType=='HTFL-01'){
				param.planJson = plan;
				param.cgConfplanJson = cgConfplan;
			}
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				$.messager.progress();
			}
			return flag;
		},
		url : base + '/Formulation/save',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#contract_list').datagrid('reload');
				closeWindow();
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}

//选择指标
function BudgetIndexCode() {
	var win = creatFirstWin('选择指标', 600, 580, 'icon-search', '/apply/choiceIndex');
	win.window('open');
}
//选择采购单
function fPurchNo_DC() {
	var win = creatFirstWin('选择采购项目', 940, 600, 'icon-search','/Formulation/PurchNoList');
	win.window('open');
}
</script>