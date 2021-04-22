<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="changeInfoApproval" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
	    		<input type="hidden" name="fId_U" id="F_fId_U" value="${Upt.fId_U}"/>
	    		<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
			    <input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
			    <input type="hidden" id="receivPlanIndex" value="${receivPlanIndex}"/>
			    <!-- 审批附件 --><input type="hidden" name="spjlFile" id="spjlFile" value=""/>
	    		<div class="tab-wrapper" id="contract-change-edit">
					<ul class="tab-menu">
						<li class="active">变更表</li>
						<li onclick="tabChange()">合同信息</li>
					</ul>
						
					<div class="tab-content">
						<div title="变更表" style="margin-bottom:35px;overflow:auto" data-options="iconCls:'icon-xxlb'" >
							<%@ include file="../../change/change_edit_info.jsp" %>
						</div>
						<div title="合同信息" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<div class="easyui-accordion" style="width: 705px;margin-left: 20px;margin-right: 20px">
								<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
									<%@ include file="../../base/contract-formulation-base2.jsp" %>
								</div>	
								<div title="签约方信息" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<%@ include file="../../base/contract-formulation-sign-base-detail.jsp" %>
								</div>
							</div>	
								<div id="select_recieve_plan" hidden="hidden">
								<div class="easyui-accordion"  style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="付款计划" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
										<%@ include file="../../base/contract-filing-edit-plan-detail.jsp" %>
									</div>
								</div>	
								</div>
								<div id="select_cgconf_plan" hidden="hidden">
								<div class="easyui-accordion" style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<%@ include file="../../base/cgconf_plan_mingxi_detail.jsp" %>
									</div>
								</div>	
								</div>
								<div id="check_history1" >
								<div class="easyui-accordion" style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<jsp:include page="../check_history.jsp" />
									</div>
								</div>	
								</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="win-left-bottom-div">
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
	
		<div class="window-right-div" style="width:254px;height: 591px">
			<jsp:include page="../../../check_system.jsp" />
		</div>
	</div>
</form>
<script type="text/javascript">
flashtab('contract-change-edit');
if ($('#uptOpenType').val() == 'Cdetail') {
	$('#change-upt-datagr-div').show();
	$.parser.parse($('#change-upt-datagr-div').parent());
	$('#change-upt-cgconf-div').show();
	$.parser.parse($('#change-upt-cgconf-div').parent());
}

$('#Upt_fUptReason_edit').attr("readonly", "readonly");
$('#uptUploadBtn').hide();
$('.deleteFlag').hide();
$(':radio').attr('disabled', true);

function tabChange(){
	$.parser.parse('#check_history1');
	$('#filing-edit-plan-dg-detail').datagrid('reload');
	//$('#contract_cgplan_dg_detail1').datagrid('reload');
	$.parser.parse($('#filing-edit-plan-dg-detail').parent());
	$.parser.parse($('#contract_cgplan_dg_detail1').parent());
	$('#check-history-dg-onchange').datagrid('reload');
}

var djhCode = '${djhCode}';
var amountValue = '${Upt.fcAmount}';
if(djhCode == 1){
	$("#f_cgdwh").removeAttr("readonly");
}
if(djhCode == 0){
	$("#button").remove();
}
function check(stauts){
	
	if(stauts == 1){
		if(djhCode==1 && amountValue >= 200000.00){
			var fDZHCode = $("#f_cgdwh").textbox('getValue');
			if(fDZHCode==''){
				alert('请填写党组会会议号！');
				return;
			}
			//附件的路径地址
			var s="";
			$(".HTfileUrl").each(function(){
				s=s+$(this).attr("id")+",";
			});
			$("#htbgjyfiles").val(s);
			if(s == ""){
				alert('请上传会议纪要！');
				return;
			}
		}
	}
	
	var fId_U=$('#F_fId_U').val();
	$('#changeInfoApproval').form('submit', {
			onSubmit: function(){ 
				flag=$(this).form('enableValidation').form('validate');
				if(flag){
					$.messager.progress();
				}
				return flag;
			}, 
			url:'${base}/Change/approve/'+stauts+'?fId='+fId_U,
			success:function(data){
				if(flag){
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if(data.success){
					$.messager.alert('系统提示', data.info, 'info');
					$('#changeInfoApproval').form('clear');
					$("#change_approval_dg").datagrid('reload'); 
					$("#indexdb").datagrid('reload'); 
					closeWindow();
				}else{
					$.messager.alert('系统提示', data.info, 'error');
				}
			} 
		});	
}
</script>
</body>