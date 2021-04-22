<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="window-div">
<form id="ApprovalAddEditForm" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<!-- 合同主键 -->			<input type="hidden" id="C_fcId" name="fcId" value="${bean.fcId}"/>
				<!-- 签约方信息主键 -->	<input type="hidden" id="F_fSignId" name="fSignId" value="${signInfo.fSignId}"/>
				<!-- 品目名称 -->		<input type="hidden" id="F_fpItemsName" name="fpItemsName" value="${bean.fpItemsName}"/>
				<!-- 审批结果 -->			<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
				<!-- 审批意见 -->			<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
				<!-- 审批附件 -->			<input type="hidden" name="spjlFile" id="spjlFile" value=""/>
				<div id="sqsqjbxx" style="overflow:auto;margin-top: 0px;">			
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
						<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
							<%@ include file="../../base/contract-formulation-base2.jsp" %>
						</div>	
						<div title="签约方信息" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
							<%@ include file="../../base/contract-formulation-sign-base-detail.jsp" %>
						</div>
						<div id="select_cgconf_plan" hidden="hidden">
						<div class="easyui-accordion" >	
							<div  hidden="hidden" title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
								<%@ include file="../../base/cgconf_plan_mingxi_detail.jsp" %>
							</div>
						</div>	
						</div>	
						<div id="select_recieve_plan" hidden="hidden">
						<div class="easyui-accordion" >
							<div title="付款计划" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
								<%@ include file="../../base/contract-filing-edit-plan-detail.jsp" %>
							</div>
						</div>	
						</div>
						<div title="审批记录" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
							<jsp:include page="../../../check_history.jsp" />												
						</div>
					</div>
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<%-- <c:if test="${flag == 'yes' }">
					<a href="javascript:void(0)" onclick="contract_seal_edit(${bean.fcId})">
						<img src="${base}/resource-modality/${themenurl}/button/htgz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;&nbsp;
				</c:if> --%>
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
					<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=合同管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="window-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">
flashtab('contract-approval-add');
$(function(){
	if('${signInfo.fIsOfficialUser}'==0){
		fIsOfficialUserNo();
	}else if('${signInfo.fIsOfficialUser}'==1){
		fIsOfficialUserYes();
	}
	
});
var plannumber = 0;
function onBeforeLoadMethod(){
	plannumber++;
	//alert(plannumber);
	if(plannumber == 2){
		return true;
	}else {
		return false;
	}
}
//审批
function check(result){
	//合同信息id
	/* var fcId = $('#C_fcId').val();
	//盖章信息id
	var fsId = 0;
	$.ajax({ 
		type: 'POST',
		async: false, //取消异步
		url: '${base}/Approval/queryFsId?id='+fcId,
		dataType: 'json',
		contentType: "application/json;charset=UTF-8",
		success: function(data){
			if(data.success){
				fsId = 1;	
			}
		} 
	});
//校验盖章信息是否保存
if(${flag == 'yes'} && fsId == 0) {
	alert('请保存盖章信息后再提交审批！');
	return;
} */

$('#ApprovalAddEditForm').form('submit', {
	onSubmit: function(){ 
		flag = $(this).form('enableValidation').form('validate');
		if(flag){
			$.messager.progress();
		}
		return flag;
	}, 
	url: base + '/Approval/approveResult',
	success:function(data){
		if(flag){
			$.messager.progress('close');
		}
		data = eval("("+data+")");
		if(data.success){
			$.messager.alert('系统提示', data.info, 'info');
			$('#contract_app_list').datagrid('reload'); 
			$('#indexdb').datagrid('reload'); 
			closeWindow();
		}else{
			$.messager.alert('系统提示', data.info, 'error');
		}
	} 
});	
}

//盖章
/* function contract_seal_edit(id){
	var win = creatFirstWin('盖章', 780, 580, 'icon-search', '/Approval/sealEdit?id='+id);
	win.window('open');
} */
</script>
</body>