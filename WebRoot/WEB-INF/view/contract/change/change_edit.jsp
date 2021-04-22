<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="changeInfo" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 合同主键 -->		<input type="hidden" id="F_fcId" name="fcId" value="${bean.fcId}"/>
	    		<!-- 审批状态 -->		<input type="hidden" id="F_fUptFlowStauts" name="fUptFlowStauts" value="${Upt.fUptFlowStauts}"/>
	    		<!-- ID -->		    <input type="hidden" id="F_fId_U" name="fId_U" value="${Upt.fId_U}"/>
	    		<!-- 原合同ID -->     <input type="hidden" id="F_fContId_U" name="fContId_U" value="${Upt.fContId_U}"/>
	    		<!-- 变更单单号 -->     <input type="hidden" id="F_fUptCode" name="fUptCode" value="${Upt.fUptCode}"/>
	    		<!-- 变更单状态 -->     <input type="hidden" id="F_fUptStatus" name="fUptStatus" value="${Upt.fUptStatus}"/>
	    		<!-- 申请人id -->		<input type="hidden" id="F_fOperatorID" name="fOperatorID" value="${Upt.fOperatorID}"/>
	    		<!-- 品目名称 -->		<input type="hidden" id="F_fpItemsName" name="fpItemsName" value="${bean.fpItemsName}"/>
	    		<!-- 申请人 -->		<input type="hidden" id="F_fOperator" name="fOperator" value="${Upt.fOperator}"/>
	    		<!-- 申请部门id -->	<input type="hidden" id="F_fDeptID" name="fDeptID" value="${Upt.fDeptID}"/>
	    		<!-- 申请日期 -->	    <input type="hidden" id="F_fReqTime" name="fReqTime" value="${Upt.fReqTime}"/>
	    		<!-- 下环节处理人姓名 -->	<input type="hidden" id="F_fUserName" name="fUserName" value="${Upt.fUserName}"/>
	    		<!-- 下环节处理人编码 --> <input type="hidden" id="F_fUserCode" name="fUserCode" value="${Upt.fUserCode}"/>
	    		<!-- 下下节点节点编码 --> <input type="hidden" id="F_fNCode" name="fNCode" value="${Upt.fNCode}"/>
	    		<!-- 审批中的付款金额 --> <input type="hidden" id="totalAmoutHid" value="${totalAmount}"/>
	    		<!-- 已发起审批的付款计划 --> <input type="hidden" id="receivPlanIndex" value="${receivPlanIndex}"/>
	    		<!-- 是否有合同报销 -->   <input type="hidden" id="reimbFlag" value="${reimbFlag}"/>
				<div class="tab-wrapper" id="contract-change-edit">
					<ul class="tab-menu">
						<li class="active">变更表</li>
						<li onclick="tabChange()">合同信息</li>
					</ul>
						
					<div class="tab-content">
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
								<div id="select_cgconf_plan" hidden="hidden">
								<div class="easyui-accordion" style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<%@ include file="../base/cgconf_plan_mingxi_detail.jsp" %>
									</div>
								</div>	
								</div>
								<div id="select_recieve_plan" hidden="hidden">
								<div class="easyui-accordion"  style="width: 702px;margin-left: 20px;margin-right: 20px">
									<div title="付款计划" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
										<%@ include file="../base/contract-filing-edit-plan-detail.jsp" %>
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
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<a href="javascript:void(0)" onclick="changeeditFrom();">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="changeEditFormSS()">
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
$("#tdf").remove();
flashtab('contract-change-edit');
$('#filing-edit-plan-dg').datagrid({
	onBeforeLoad : function(data){
		if(loadplan>3){
			return false;
		}else {
			return true;
		}
	}
});
$('#F_fcType').combobox({  
       onChange:function(newValue,oldValue){  
   	var sel2=$('#F_fcType').combobox('getValue');
   	if(sel2=="HTFL-01"){
   		$('#cg1').show();
   		$('#cg2').show();
   		//$('#cg2').hide();
   		//$('#F_fPurchNo').next(".textbox").show();
	}else{
   		$('#cg1').hide();
   		$('#cg2').hide();
   		//$('#cg2').show();
   		//$('#F_fPurchNo').next(".textbox").hide();
	} 
       }
   }); 
   //送审
function changeEditFormSS(){
	var reimbFlag = $('#reimbFlag').val();
	if (reimbFlag == 'true' || reimbFlag == '"true"') {
		$.messager.alert('系统提示', '该合同正在进行报销，暂时无法保存！', 'info');
		return;
	}

	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	var fileUrl = $('#htbgfiles').val(s);
	if(s==''){
		alert("请上传补充协议");
		return false;
	}
	var fPlanChangeStatus = $('input[name="collectionPlan"]:checked').val();
	var fShoppingChangeStatus = $('input[name="shoppingList"]:checked').val();
	
	var uptplan ='';
	var cgConfPlan ='';
	var fcType=$('#F_fcType').combobox('getValue');
	if(fcType=='HTFL-01'){
		
		/* if (parseFloat($('#totalAmount_span').html().replace('[元]','')).toFixed(2) != $('#F_fpAmount').html().replace('[元]','')){
			alert("付款计划总额和采购清单金额不相等！");
			return false;
		} */
		uptplan = getUptPlanEdit();//变更付款计划表
		cgConfPlan = getcgConfPlanEdit();//采购清单
		
		var fpItemsName = $("#F_fpItemsName").val();
		var rows = $('#change-upt-datagrid').datagrid('getRows');
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
		/* var collectionPlan = $("input[name='collectionPlan']:checked").val();
		var shoppingList =  $("input[name='shoppingList']:checked").val();
		if(collectionPlan==1){
			// 付款金额总额不大于原合同金额110%
			if ($('#totalAmount').val() > $('#fcAmountUpt').val()*1.1) {
				$.messager.alert('系统提示', '付款金额总额不大于原合同金额110%', 'info');
				return;
			}
		}
		if(shoppingList==1){
			// 小计金额不大于原合同小计金额110%
			var xjAmount = $('#F_fpAmount').html().replace('[元]', '');
			if (xjAmount > $('#fPlanTotalAmountUptHid').val()*1.1) {
				$.messager.alert('系统提示', '小计金额不大于原合同小计金额110%', 'info');
				return;
			}
			
		} */
		var upt_fcAmount = $('#upt_fcAmount').val();
		var totalAmount = $('#totalAmount').val();
		if(upt_fcAmount < totalAmount){
			$.messager.alert('系统提示', '付款金额大于合同金额，请修改后提交!', 'info');
			return;
		}
	}
	
	
	
	$('#F_fUptFlowStauts').val("1");
	
	

	var fUptReason = $('#Upt_fUptReason_edit').val();
	if (fUptReason == '') {
		$.messager.alert('系统提示', '变更说明不能为空', 'info');
		return;
	}
	var flag = $('#changeInfo').form('enableValidation').form('validate');
	if (flag) {
		$.messager.progress();
		$.ajax({
	        type : "POST",
	        url : base+'/Change/Save',
	        async : 'false',
	        //data : JSON.stringify(list),
	        data : {
	        	uptplanJson: uptplan,
	        	cgConfPlanJson: cgConfPlan,
	        	fcId: $('#F_fcId').val(),
	        	fUptFlowStauts: '1',
	        	fId_U: $('#F_fId_U').val(),
	        	fContId_U: $('#F_fContId_U').val(),
	        	fContCode: $('#upt_fContCode_edit').val(),
	        	fContName: $('#upt_fContName_edit').val(),
	        	fContCodeOld: $('#upt_fContCodeOld_edit').val(),
	        	fContNameold: $('#upt_fContNameold_edit').val(),
	        	fOperator: $('#upt_fOperator_edit').val(),
	        	fOperatorID: $('#upt_fOperatorID_edit').val(),
	        	fDeptName: $('#upt_fDeptName_edit').val(),
	        	fDeptID: $('#upt_fDeptID_edit').val(),
	        	fUserName: $('#F_fUserName').val(),
	        	fUserCode: $('#F_fUserCode').val(),
	        	fNCode: $('#F_fNCode').val(),
	        	fUptReason: fUptReason,
	        	htbgfiles: $('#htbgfiles').val(),
	        	fPlanChangeStatus: fPlanChangeStatus,
	        	fShoppingChangeStatus: fShoppingChangeStatus,
	        	fcAmount: $('#upt_fcAmount').val(),
	        	fcAmountMax: $('#upt_fcAmountMax').val(),
	        	fContractor:$('#upt_fContractor').val(),
	        	fMarginAmount:$('#upt_fMarginAmount').val(),
	        	fContStartTime:$('#upt_fContStartTime').val(),
	        	fContEndTime:$('#upt_fContEndTime').val()
	        },
	        success : function(data) {
				if(flag){
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if(data.success){
					$('#changeInfo').form('clear');
					$.messager.alert('系统提示', data.info, 'info');
					$('#change_dg').datagrid('reload');
					$('#indexdb').datagrid('reload');
					closeWindow();
				}else{
					$.messager.alert('系统提示', data.info, 'error');
				}
	        },
	        error : function(e){
	            console.log(e.status);
	            console.log(e.responseText);
	        }
	    });
		
	}
	
	
	/* if(fileUrl==undefined){
		alert("请上传附件");
	}else {
   		$('#changeInfo').form('submit', {
			onSubmit:function(param){ 
			//	param.planJson=plan;
				param.uptplanJson=uptplan;
				param.cgConfPlanJson=cgConfPlan;
				flag=$(this).form('enableValidation').form('validate');
				if(flag){
					$.messager.progress();
				}
				return flag;
			}, 
			url:base+'/Change/Save',
			type:'post',
			success:function(data){
				if(flag){
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if(data.success){
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$('#changeInfo').form('clear');
					$('#change_dg').datagrid('reload');
					$('#indexdb').datagrid('reload'); 
				}else{
					$.messager.alert('系统提示', data.info, 'error');
				}
			} 
		});	
   	
	} */
}
//暂存
function changeeditFrom(){
	var reimbFlag = $('#reimbFlag').val();
	if (reimbFlag == 'true' || reimbFlag == '"true"') {
		$.messager.alert('系统提示', '该合同正在进行报销，暂时无法保存！', 'info');
		return;
	}
	
	var fPlanChangeStatus = $('input[name="collectionPlan"]:checked').val();
	var fShoppingChangeStatus = $('input[name="shoppingList"]:checked').val();
	var uptplan = '';//合同付款计划
	/* if (fPlanChangeStatus == '0') {
		uptplan = '';
	} */
	var cgConfPlan = '';//采购清单
	/* if (fShoppingChangeStatus == '0') {
		cgConfPlan = '';
	} */
	$('#F_fUptFlowStauts').val("0");
	var fcType=$('#F_fcType').combobox('getValue');
	if(fcType=='HTFL-01'){
		/* // 付款金额总额不大于原合同金额110%
		if ($('#totalAmount').val() > $('#fcAmountUpt').val()*1.1) {
			$.messager.alert('系统提示', '付款金额总额不大于原合同金额110%', 'info');
			return;
		}
		// 小计金额不大于原合同小计金额110%
		var xjAmount = $('#F_fpAmount').html().replace('[元]', '');
		if (xjAmount > $('#fPlanTotalAmountUptHid').val()*1.1) {
			$.messager.alert('系统提示', '小计金额不大于原合同小计金额110%', 'info');
			return;
		}
		// 付款计划总额不小于审批中的付款金额
		var totalAmoutHid = $('#totalAmoutHid').val();
		var fcAmount = $('#totalAmount_span').html().replace('[元]', '');
		if (Number(fcAmount) < Number(totalAmoutHid)) {
			$.messager.alert('系统提示', '付款计划总额不小于合同报销审批中的付款金额！', 'info');
			return;
		} */
		//
		var upt_fcAmount = $('#upt_fcAmount').val();
		var totalAmount = $('#totalAmount').val();
		if(upt_fcAmount < totalAmount){
			$.messager.alert('系统提示', '付款金额大于合同金额，请修改后提交!', 'info');
			return;
		}
		uptplan = getUptPlanEdit();
		cgConfPlan = getcgConfPlanEdit();
	}
	var fUptReason = $('#Upt_fUptReason_edit').val();
	if (fUptReason == '') {
		$.messager.alert('系统提示', '变更说明不能为空', 'info');
		return;
	}
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	var fileUrl = $('#htbgfiles').val(s);
	if(s==''){
		alert("请上传补充协议");
		return false;
	}
	var flag = $('#changeInfo').form('enableValidation').form('validate');
	if (flag) {
		$.messager.progress();
		$.ajax({
	        type : "POST",
	        url : base+'/Change/Save',
	        async : 'false',
	        //data : JSON.stringify(list),
	        data : {
	        	uptplanJson: uptplan,
	        	cgConfPlanJson: cgConfPlan,
	        	fcId: $('#F_fcId').val(),
	        	fUptFlowStauts: '0',
	        	fId_U: $('#F_fId_U').val(),
	        	fContId_U: $('#F_fContId_U').val(),
	        	fContCode: $('#upt_fContCode_edit').val(),
	        	fContName: $('#upt_fContName_edit').val(),
	        	fContCodeOld: $('#upt_fContCodeOld_edit').val(),
	        	fContNameold: $('#upt_fContNameold_edit').val(),
	        	fOperator: $('#upt_fOperator_edit').val(),
	        	fOperatorID: $('#upt_fOperatorID_edit').val(),
	        	fDeptName: $('#upt_fDeptName_edit').val(),
	        	fDeptID: $('#upt_fDeptID_edit').val(),
	        	fUserName: $('#F_fUserName').val(),
	        	fUserCode: $('#F_fUserCode').val(),
	        	fNCode: $('#F_fNCode').val(),
	        	fUptReason: fUptReason,
	        	htbgfiles: $('#htbgfiles').val(),
	        	fPlanChangeStatus: fPlanChangeStatus,
	        	fShoppingChangeStatus: fShoppingChangeStatus,
	        	fcAmount: $('#upt_fcAmount').val(),
	        	fcAmountMax: $('#upt_fcAmountMax').val(),
	        	fContractor:$('#upt_fContractor').val(),
	        	fMarginAmount:$('#upt_fMarginAmount').val(),
	        	fContStartTime:$('#upt_fContStartTime').val(),
	        	fContEndTime:$('#upt_fContEndTime').val()
	        },
	        success : function(data) {
	        	if (flag) {
					$.messager.progress('close');
				}
	        	data = eval("(" + data + ")");
				if (data.success) {
					$('#changeInfo').form('clear');
					$.messager.alert('系统提示', data.info, 'info');
					$("#change_dg").datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
	        },
	        error : function(e){
	            console.log(e.status);
	            console.log(e.responseText);
	        }
	    });
		
	}


	
	
	
	/* $('#changeInfo').form('submit', {
		onSubmit : function(param) {
			param.uptplanJson=uptplan;
			param.cgConfPlanJson=cgConfPlan;
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				$.messager.progress();
			}
			return flag;
		},
		url:base+'/Change/Save',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#changeInfo').form('clear');
				$("#change_dg").datagrid('reload');
				closeWindow();
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	}); */
}
function fPurchNo_DC(){
	//var node=$('#c_c_dg').#check-history-dgd('getSelected');
	var win=creatFirstWin('选择-采购订单号',750,550,'icon-add','/PurchaseApply/PurchNoList');
	win.window('open');
}
function quota_DC(){
	//var node=$('#c_c_dg').datagrid('getSelected');
	var win=creatFirstWin('选择-预算指标编号',750,550,'icon-add','/BudgetIndexMgr/contract_list');
	win.window('open');
}
/* var c =0;
function upt_upFile() {
	 console.log(document.getElementById("upt_fFileSrc1"));
	var src = getFilePath();
	alert(getFilePath()); 
	c ++;
	var src = $('#upt_fFileSrc1').val();
	 var srcs =src+','+$('#upt_fi1').val();
	$('#upt_fi1').val(srcs); 
	$('#upt__f1').append("<div id='c"+c+"'><a href='#' class='file_U' style='color: #3f7f7f;font-weight: bold;'>"+src+"</a><a style='color: red;' href='#' onclick='deleteF1(c"+c+")'>删除</a></div>");
}  */
function deleteF1(val){
	var child=document.getElementById(val.id);
	child.parentNode.removeChild(child);
}

function tabChange(){
	$.parser.parse('#select_cgconf_plan');
	$.parser.parse('#select_recieve_plan');
	$.parser.parse('#check_history1');
	$('#filing-edit-plan-dg-detail').datagrid('reload');
	$('#contract_cgplan_dg_detail1').datagrid('reload');
	$('#check-history-dg-onchange').datagrid('reload');
}
</script>
</body>